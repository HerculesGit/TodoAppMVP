package br.com.herco.todoappmvp.fragments.home;


import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.herco.todoappmvp.R;
import br.com.herco.todoappmvp.application.TodoApp;
import br.com.herco.todoappmvp.mocks.ApiTaskRestServiceMock;
import br.com.herco.todoappmvp.models.TaskModel;
import br.com.herco.todoappmvp.models.UserModel;
import br.com.herco.todoappmvp.repositories.task.ITaskRestRepository;
import br.com.herco.todoappmvp.repositories.task.TaskRestRepositoryImpl;
import br.com.herco.todoappmvp.test_utils.RemoveLooperSchedulerErrorUtils;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class HomeTaskFragmentTest {

    @BeforeClass
    public static void afterClass() {
        RemoveLooperSchedulerErrorUtils.execute();
    }

    @Mock
    private HomeTaskContract.IHomeTaskFragmentView view;

    @Mock
    private HomeTaskContract.IHomeTaskFragmentPresenter presenter;

    @Mock
    private ITaskRestRepository taskRepository;

    private final String USERNAME = "hermanoteu";
    private final String PASSWORD = "123";
    private final ApiTaskRestServiceMock apiTaskRestServiceMock = new ApiTaskRestServiceMock();

    final UserModel userModelMocked
            = new UserModel(apiTaskRestServiceMock.userId, "Hermanoteu");

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        taskRepository = new TaskRestRepositoryImpl(apiTaskRestServiceMock, TodoApp.getInstance());
        presenter = new HomeFragmentPresenter(view, taskRepository);
        apiTaskRestServiceMock.userId = "112233";
    }

    @Test
    public void shouldUpdateTaskWhenChangesOnIsDoneValue() {
        int index = 0;

        final TaskModel taskModel = apiTaskRestServiceMock.tasks.get(index);

        presenter.updateTask(taskModel, index);

        verify(view, times(0)).onUpdatedTaskError(R.string.error_updating_task, index);
        verify(view, times(1)).onUpdatedTask(taskModel, index);
    }

    @Test
    public void shouldShowUpdateMessageError_WhenTheTaskIsNotFoundOnDatabase() {
        int index = 0;
        final TaskModel taskModel = apiTaskRestServiceMock.tasks.get(0);

        apiTaskRestServiceMock.removeAllTasks();
        presenter.updateTask(taskModel, index);
        verify(view, times(1)).onUpdatedTaskError(R.string.not_found_tasks, 0);
    }

    @Test
    public void shouldLoadAllTasksByUser() {
        presenter.loadAllTasks(apiTaskRestServiceMock.userId);
        verify(view, times(1)).onLoadTaskSuccess(apiTaskRestServiceMock.tasks);
        verify(view, times(1)).hideNotFoundTasks();
        verify(view, times(1)).showTasksLayout();

        verify(view, times(0)).onDeletedTaskError(anyInt(), anyInt());
        verify(view, times(0)).onUpdatedTaskError(anyInt(), anyInt());
        verify(view, times(0)).onUpdatedTaskError(anyInt(), anyInt());
        verify(view, times(0)).onLoadTaskError(anyInt());
    }

    @Test
    public void shouldShowDeletedMessageErrorWhenTheTaskIsNotFoundOnDatabase() {
        int index = 2;
        final TaskModel taskModel = apiTaskRestServiceMock.tasks.get(index);

        apiTaskRestServiceMock.removeAllTasks();

        presenter.deleteTask(taskModel, index);
        verify(view, times(0)).onDeletedTask(index);
        verify(view, times(1)).onDeletedTaskError(R.string.not_found_tasks, index);
    }

    @Test
    public void shouldDeleteTaskOnDatabase() {
        int index = 2;
        final TaskModel taskModel = apiTaskRestServiceMock.tasks.get(index);

        presenter.deleteTask(taskModel, index);
        verify(view, times(1)).onDeletedTask(index);
        verify(view, times(0)).onDeletedTaskError(R.string.not_found_tasks, index);
    }

    @Test
    public void shouldShowNoFoundTasksLayout_WhenTasksListIsEmpty() {
        presenter.loadAllTasks("idNotExistsOnDatabase");

        verify(view, times(0)).onLoadTaskSuccess(apiTaskRestServiceMock.tasks);
        verify(view, times(0)).onDeletedTask(anyInt());
        verify(view, times(0)).onUpdatedTask(any(), anyInt());

        verify(view, times(0)).onUpdatedTaskError(anyInt(), anyInt());
        verify(view, times(0)).onDeletedTaskError(anyInt(), anyInt());

        verify(view, times(1)).noTasksFound();
    }

    @Test
    public void shouldShowServerError_WhenTheServerReturnStatus500OnTryLoadTask() {

        apiTaskRestServiceMock.forceServerError = true;
        presenter.loadAllTasks(apiTaskRestServiceMock.userId);

        verify(view, times(0)).onLoadTaskSuccess(apiTaskRestServiceMock.tasks);
        verify(view, times(0)).onDeletedTask(anyInt());
        verify(view, times(0)).onUpdatedTask(any(), anyInt());

        verify(view, times(0)).onUpdatedTaskError(anyInt(), anyInt());
        verify(view, times(0)).onDeletedTaskError(anyInt(), anyInt());

        verify(view, times(1)).onLoadTaskError(R.string.server_error_500);
    }

    @Test
    public void shouldShowEmptyTaskLayout_AfterCreateOneTask() {
        presenter.loadAllTasks(apiTaskRestServiceMock.userId);
    }
}
