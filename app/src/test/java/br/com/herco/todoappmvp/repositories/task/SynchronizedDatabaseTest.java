package br.com.herco.todoappmvp.repositories.task;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;


import br.com.herco.todoappmvp.models.TaskModel;
import br.com.herco.todoappmvp.services.synchronize.ISynchronizedDatabase;
import br.com.herco.todoappmvp.services.synchronize.SynchronizedDatabase;
import io.reactivex.Observable;

@RunWith(MockitoJUnitRunner.class)
public class SynchronizedDatabaseTest {
    @Mock
    private ISynchronizedDatabase synchronizedDatabase;


    private TaskModel taskModel = new TaskModel(
            "2fd0a019-628d-4437-9e93-323c4f800cc9", false
    );

    private String userId = "bf9a0dda-f972-11eb-9a03-0242ac130003";

    private Observable<TaskModel> taskModelObservable;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        taskModel.setUserId(userId);
        synchronizedDatabase = new SynchronizedDatabase(null);
    }

    @Test
    public void shouldCreateATask() {
        taskModel.setUserId(userId);
        Mockito.when(synchronizedDatabase.createTask(taskModel)).thenReturn(Observable.just(taskModel));
    }
}
