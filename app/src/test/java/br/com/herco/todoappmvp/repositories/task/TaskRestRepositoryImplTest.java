package br.com.herco.todoappmvp.repositories.task;
//
//import org.junit.Before;
//import org.junit.Test;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import br.com.herco.todoappmvp.models.TaskModel;
//import br.com.herco.todoappmvp.services.database.retrofit.TaskRestService;
//import io.reactivex.rxjava3.core.Observable;
//import io.reactivex.rxjava3.functions.Consumer;
//import io.reactivex.rxjava3.subscribers.TestSubscriber;
//
//import static org.junit.Assert.assertEquals;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//public class TaskRestRepositoryImplTest {
//    @Mock
//    TaskRestService taskRestService;
//
//    private TaskRestRepositoryImpl taskRestRepository;
//
//    @Before
//    public void setUp() throws Exception {
//        MockitoAnnotations.initMocks(this);
//        taskRestRepository = new TaskRestRepositoryImpl(taskRestService);
//    }
//
//    /**
//     * [Name of method under test]_[Conditions of test case]_[Expected Result]
//     */
//
//    @Test
//    public void searchUsers_200OkResponse_InvokesCorrectApiCalls() {
//        //Given
//        when(taskRestService.listTasks()).thenReturn(Observable.just(tasksMethodList()));
////        when(githubUserRestService.getUser(anyString()))
////                .thenReturn(Observable.just(user1FullDetails()), Observable.just(user2FullDetails()));
//
//        //When
//        TestSubscriber<List<TaskModel>> subscriber = new TestSubscriber<>();
//        taskRestRepository.getAllTasks(1).subscribe(new Consumer<List<TaskModel>>() {
//            @Override
//            public void accept(List<TaskModel> taskModels) throws Throwable {
//
//            }
//        });
//
//        //Then
//        subscriber.onComplete();
//        subscriber.assertNoErrors();
//
////        List<List<User>> onNextEvents = subscriber.getOnNextEvents();
////        List<User> users = onNextEvents.get(0);
//        assertEquals(1, users.get(0).getLogin());
//        assertEquals(USER_LOGIN_2_REBECCA, users.get(1).getLogin());
//        verify(githubUserRestService).searchGithubUsers(USER_LOGIN_RIGGAROO);
//        verify(githubUserRestService).getUser(USER_LOGIN_RIGGAROO);
//        verify(githubUserRestService).getUser(USER_LOGIN_2_REBECCA);
//    }
//
//    private List<TaskModel> tasksMethodList() {
////        User user = new User();
////        user.setLogin(USER_LOGIN_RIGGAROO);
////
////        User user2 = new User();
////        user2.setLogin(USER_LOGIN_2_REBECCA);
//
//        List<TaskModel> tasksList = new ArrayList<>();
//        // tasksList.add(user);
//        // tasksList.add(user2);
////        UsersList usersList = new UsersList();
////        usersList.setItems(githubUsers);
//        return tasksList;
//    }
//
//    private User user1FullDetails() {
//        User user = new User();
//        user.setLogin(USER_LOGIN_RIGGAROO);
//        user.setName("Rigs Franks");
//        user.setAvatarUrl("avatar_url");
//        user.setBio("Bio1");
//        return user;
//    }
//
//    private User user2FullDetails() {
//        User user = new User();
//        user.setLogin(USER_LOGIN_2_REBECCA);
//        user.setName("Rebecca Franks");
//        user.setAvatarUrl("avatar_url2");
//        user.setBio("Bio2");
//        return user;
//    }
//
//}