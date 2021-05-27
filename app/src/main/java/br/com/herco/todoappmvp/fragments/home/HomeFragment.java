package br.com.herco.todoappmvp.fragments.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import br.com.herco.todoappmvp.R;
import br.com.herco.todoappmvp.adapters.CategoryAdapter;
import br.com.herco.todoappmvp.adapters.OnTaskListener;
import br.com.herco.todoappmvp.adapters.TaskAdapter;
import br.com.herco.todoappmvp.adapters.swiped.SwipeController;
import br.com.herco.todoappmvp.constants.Constants;
import br.com.herco.todoappmvp.dto.TaskDTO;
import br.com.herco.todoappmvp.edit_task.EditTaskActivity;
import br.com.herco.todoappmvp.fragments.BaseFragment;
import br.com.herco.todoappmvp.listeners.OnNavDrawerListener;
import br.com.herco.todoappmvp.models.CategoryModel;
import br.com.herco.todoappmvp.models.TaskModel;
import br.com.herco.todoappmvp.repositories.task.TaskRepository;
import br.com.herco.todoappmvp.services.database.preferences.DataBasePreferences;
import br.com.herco.todoappmvp.viewholders.TaskViewHolder;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends BaseFragment<HomeFragmentPresenter> implements IHomeContractView, OnTaskListener {

    private OnNavDrawerListener listener;

    private RecyclerView recyclerViewCategory;
    private List<CategoryModel> categories;

    private RecyclerView recyclerViewTask;
    private SwipeController taskSwipeController;
    private ItemTouchHelper itemTouchHelper;

    private TaskAdapter taskAdapter;
    private FloatingActionButton fabNewTask;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public View inflateFragment(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home_tasks, container, false);
    }

    @Override
    public void initUI() {
        ImageView navHamburgerButton = findViewById(R.id.img_nav_hamburger_button);
        navHamburgerButton.setOnClickListener(v -> {
            listener.onHamburgerDrawerPressed();
        });

        this.getUser();
        this.buildRecyclerViewCategories();
        this.buildRecyclerViewTasks();
        this.getFloatingActionButtonBNewTask();
        this.presenter.loadTasks();
    }

    @Override
    public HomeFragmentPresenter loadPresenter() {
        return new HomeFragmentPresenter(this, new TaskRepository(
                new DataBasePreferences(getActivity().getSharedPreferences(
                        Constants.Database.DATABASE_PREFERENCES, Context.MODE_PRIVATE))));
    }

    private void getFloatingActionButtonBNewTask() {
        fabNewTask = findViewById(R.id.fab_create_task);
        fabNewTask.setOnClickListener(view -> {
            goToNewTask();
        });
    }

    private void getUser() {
        final TextView textView = findViewById(R.id.tv_whats_up_user);
        textView.setText(getString(R.string.whats_up_user, "Joy"));
    }

    private void buildRecyclerViewCategories() {
        recyclerViewCategory = findViewById(R.id.rc_category);
        recyclerViewCategory.setLayoutManager(new LinearLayoutManager(getActivity()) {
            @Override
            public boolean checkLayoutParams(RecyclerView.LayoutParams lp) {
                // force height of viewHolder here, this will override layout_height from xml
                lp.width = getWidth() / 2;
                return true;
            }

            @Override
            public void setOrientation(int orientation) {
                super.setOrientation(LinearLayoutManager.HORIZONTAL);
            }
        });

        // temp
        categories = new ArrayList<>();
        categories.add(new CategoryModel("Business", 40));
        categories.add(new CategoryModel("Personal", 18));
        // end-temp

        final CategoryAdapter categoryAdapter = new CategoryAdapter(categories);

        recyclerViewCategory.setAdapter(categoryAdapter);
    }

    private void buildRecyclerViewTasks() {
        recyclerViewTask = findViewById(R.id.rv_tasks);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerViewTask.setLayoutManager(linearLayoutManager);
        taskAdapter = new TaskAdapter(getActivity(), recyclerViewTask, null);
        taskAdapter.setOnClickListener(this);

        recyclerViewTask.setAdapter(taskAdapter);

        //Attached the ItemTouchHelper
        taskSwipeController = new SwipeController(0, ItemTouchHelper.LEFT);
        itemTouchHelper = new ItemTouchHelper(taskSwipeController);
        itemTouchHelper.attachToRecyclerView(recyclerViewTask);

        taskSwipeController.setListener(this);
    }

    // You can do the assignment inside onAttach or onCreate, i.e, before the activity is displayed
    ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    Intent data = result.getData();
                    if (result.getResultCode() == Constants.Keys.ACTIVITY_FROM_RESULT_CODE_TASK && data != null) {
                        TaskDTO taskDTOReturned = (TaskDTO) data.getSerializableExtra(Constants.Keys.TASK_DTO);
                        if (taskDTOReturned != null) {
                            taskAdapter.addTask(taskDTOReturned.getTaskModel());
                        }
                    }
                }
            });

    private void goToNewTask() {
        Intent intent = new Intent(getActivity(), EditTaskActivity.class);
        someActivityResultLauncher.launch(intent);
    }

    @Override
    public void onTasksLoad(List<TaskModel> tasks) {
        Log.d("onTasksLoad", tasks.toString());
        taskAdapter.addAllTasks(tasks);
    }

    @Override
    public void onTaskLoadError(String messageError) {
        Log.d("onTaskLoadError", "");
    }

    @Override
    public void updateTaskSuccess() {
        Log.d("updateTaskSuccess", "updateTaskSuccess");
    }

    @Override
    public void updateTaskError(String messageError) {
        Log.d("onTasksLoad", "messageError");
        showToast(messageError);
    }

    @Override
    public void onTaskClicked(TaskModel taskModel) {
        presenter.updateTask(taskModel);
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        final View view = ((TaskViewHolder) viewHolder).getBackgroundView();
        animateDeletingTask(view);
        deleteTask(position);
    }

    private void animateDeletingTask(View view) {
        view.animate()
                .translationX(view.getWidth())
                .alpha(0.0f)
                .setDuration(100);

        showSnackBar(view, getString(R.string.task_removed), getString(R.string.undo), onPressed -> {
            restoreTask();
        });
    }

    private void deleteTask(int position) {
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            taskAdapter.deleteTask(position);
        }, 100);
    }

    private void restoreTask() {
        taskAdapter.restoreTask();
    }

    public void setListener(OnNavDrawerListener listener) {
        this.listener = listener;
    }
}