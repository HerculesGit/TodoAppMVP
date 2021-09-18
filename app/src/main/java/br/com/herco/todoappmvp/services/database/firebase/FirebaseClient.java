package br.com.herco.todoappmvp.services.database.firebase;

import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import br.com.herco.todoappmvp.models.TaskModel;
import br.com.herco.todoappmvp.models.UserSynchronizedDateModel;
import br.com.herco.todoappmvp.services.database.preferences.PreferencesHelper;
import io.reactivex.Observable;

public class FirebaseClient implements IFirebaseClient {
    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    final String TASKS = "tasks";

    @Override
    public Observable<List<TaskModel>> listTasks(String userId) {
        AtomicReference<ListenerRegistration> listener = new AtomicReference<>();
        return Observable.create(
                emitter -> {
                    listener.set(firebaseFirestore.collection(TASKS)
                            .whereEqualTo("userId", userId)
                            .whereEqualTo("deletedAt", null)
                            .orderBy("updatedAt", Query.Direction.DESCENDING)
                            .addSnapshotListener((snapshot, error) -> {
                                if (error != null) {
                                    error.printStackTrace();
                                    emitter.onError(error);
                                }

                                if (snapshot != null) {
                                    List<TaskModel> tasks = snapshot.toObjects(TaskModel.class);
                                    emitter.onNext(tasks);
                                }
                                listener.get().remove();
                            }));
                });
    }

    @Override
    public Observable<UserSynchronizedDateModel> synchronizeTasks(String userId, List<TaskModel> task) {
        return null;
    }

    @Override
    public Observable<TaskModel> postTask(TaskModel task) {
        String taskId = PreferencesHelper.getUUID();
        task.setId(taskId);
        return Observable.create(emitter -> {
            firebaseFirestore.collection(TASKS).document(taskId).set(task)
                    .addOnCompleteListener(command -> {
                        emitter.onNext(task);
                    })
                    .addOnFailureListener(error -> {
                        error.printStackTrace();
                        emitter.onError(error);
                    });
        });
    }

    @Override
    public Observable<TaskModel> updateTask(final String uuid, TaskModel task) {
        task.setUpdatedAt(new Date());
        return Observable.create(emitter -> {
            firebaseFirestore.collection(TASKS).document(task.getId()).update(task.toMap())
                    .addOnCompleteListener(command -> {
                        System.out.println(command);
                        emitter.onNext(task);
                    })
                    .addOnFailureListener(error -> {
                        error.printStackTrace();
                        System.out.println(error);
                        emitter.onError(error);
                    });
        });
    }

    @Override
    public Observable<TaskModel> deleteTask(String uuid) {
        Map<String, Object> map = new HashMap<>();
        map.put("deletedAt", FieldValue.serverTimestamp());
        return Observable.create(emitter -> {
            firebaseFirestore.collection(TASKS).document(uuid).update(map)
                    .addOnCompleteListener(command -> {
                        emitter.onNext(new TaskModel());
                    })
                    .addOnFailureListener(error -> {
                        error.printStackTrace();
                        emitter.onError(error);
                    });
        });
    }
}
