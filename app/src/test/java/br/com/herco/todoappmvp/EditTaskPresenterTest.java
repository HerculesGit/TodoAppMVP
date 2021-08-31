package br.com.herco.todoappmvp;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import br.com.herco.todoappmvp.edit_task.EditTaskPresenter;
import br.com.herco.todoappmvp.edit_task.IEditTaskContract;
import br.com.herco.todoappmvp.exceptions.TaskException;
import br.com.herco.todoappmvp.models.TaskModel;
import br.com.herco.todoappmvp.repositories.task.ITaskRestRepository;
import br.com.herco.todoappmvp.repositories.task.TaskRepository;
import io.reactivex.Observable;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EditTaskPresenterTest {

    private EditTaskPresenter presenter;

    @Mock
    private ITaskRestRepository repository;

    @Mock
    private IEditTaskContract viewContract;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        presenter = Mockito.spy(new EditTaskPresenter(viewContract, repository));
    }

    @Test(expected = IllegalArgumentException.class)
    public void test1() throws Throwable {
        assertFalse(throwException());
    }

    private boolean throwException() {
        throw new IllegalArgumentException();
    }

    @Test()
    public void shouldThrowExceptionToAddingANullTask() throws TaskException {
        when(repository.createTask(null)).thenThrow(new TaskException("Task cannot null!"));

        // really verify if the method throws TaskException
        Exception exception = assertThrows(TaskException.class, () -> repository.createTask(null));

        assertEquals(exception.getMessage(), "Task cannot null!");
    }

    @Test()
    public void shouldThrowsExceptionToAddANullTaskName() throws TaskException {
        final TaskModel taskModel = new TaskModel(null);

        when(repository.createTask(taskModel)).thenThrow(new TaskException("Task's name cannot null!"));

        Exception exception = assertThrows(TaskException.class, () -> repository.createTask(taskModel));
        assertEquals(exception.getMessage(), "Task's name cannot null!");
    }

    @Test()
    public void shouldAddANameToTheTask() throws TaskException {
        final String taskName = "Learn unit test to android with java";
        final TaskModel taskModel = new TaskModel(taskName);

        when(repository.createTask(taskModel)).thenReturn(Observable.just(taskModel));

        assertNotNull(taskModel.getName());
        assertEquals(taskModel.getName(), taskName);
    }
}
