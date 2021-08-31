package br.com.herco.todoappmvp.fragments.home;


import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.Arrays;
import java.util.List;

import br.com.herco.todoappmvp.R;
import br.com.herco.todoappmvp.application.TodoApp;
import br.com.herco.todoappmvp.mocks.ApiTaskRestServiceMock;
import br.com.herco.todoappmvp.models.TaskModel;
import br.com.herco.todoappmvp.models.UserModel;
import br.com.herco.todoappmvp.repositories.task.ITaskRestRepository;
import br.com.herco.todoappmvp.repositories.task.TaskRestRepositoryImpl;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class HomeTaskFragmentTest {

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
            = new UserModel("1", "Hermanoteu");

    @Before
    public void setUp() {
        taskRepository = new TaskRestRepositoryImpl(apiTaskRestServiceMock, TodoApp.getInstance());
    }

    @Test
    public void shouldDeleteTask() {

    }

    @Test
    public void shouldUpdateTaskWhenChangesOnIsDoneValue() {
        final TaskModel taskModel = new TaskModel("", false);
        presenter.updateTask(taskModel, 1);
        verify(view, times(0)).onUpdatedTaskError(R.string.error_updating_task);
        verify(view, times(1)).onUpdatedTask(taskModel);
    }

    @Test
    public void shouldShowDeletedMessageErrorWhenTheTaskIsNotFoundOnDatabase() {
        final TaskModel taskModel = new TaskModel("", false);
        presenter.updateTask(taskModel, -1);
        verify(view, times(0)).onDeletedTaskError(R.string.error_deleting_task);
        verify(view, times(0)).onDeletedTask();
    }

    @Test
    public void shouldShowUpdatedMessageErrorWhenTheTaskIsNotFoundOnDatabase() {
        final TaskModel taskModel = new TaskModel("", false);
        presenter.updateTask(taskModel, -1);
        verify(view, times(1)).onUpdatedTaskError(R.string.error_updating_task);
        verify(view, times(0)).onUpdatedTask(taskModel);
    }


    @Test
    public void shouldLoadAllTasksByUser() {
        presenter.loadAllTasks(userModelMocked.getId());
        List<TaskModel> taskByUser = Arrays.asList(
                new TaskModel("t1", true),
                new TaskModel("t2", false)
        );
        verify(view, times(1)).onLoadTaskSuccess(taskByUser);
        verify(view, times(0)).onUpdatedTaskError(0);
        verify(view, times(0)).onDeletedTaskError(0);
        verify(view, times(0)).onDeletedTask();
    }


    @Test
    public void shouldShowNoTasksFoundLayout() {
        presenter.loadAllTasks(userModelMocked.getId());
        List<TaskModel> taskByUser = Arrays.asList();

        verify(view, times(0)).onLoadTaskSuccess(taskByUser);
        verify(view, times(0)).onUpdatedTaskError(0);
        verify(view, times(0)).onDeletedTaskError(0);
        verify(view, times(0)).onDeletedTask();

        verify(view, times(1)).noTasksFound();
    }


    @Test
    public void shouldShowNoInternetConnectionWhenTheDeviceIsNotConnected() {
        final UserModel userModel
                = new UserModel("1", "Hermanoteu");

        presenter.loadAllTasks(userModel.getId());
        List<TaskModel> taskByUser = Arrays.asList();

        verify(view, times(0)).onLoadTaskSuccess(taskByUser);
        verify(view, times(0)).onUpdatedTaskError(0);
        verify(view, times(0)).onDeletedTaskError(0);
        verify(view, times(0)).onDeletedTask();

        verify(view, times(0)).noTasksFound();

        verify(view, times(1)).noInternetConnection();
    }
}
