package br.com.herco.todoappmvp;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import br.com.herco.todoappmvp.adapters.OnTaskListener;
import br.com.herco.todoappmvp.adapters.swiped.SwipeController;
import br.com.herco.todoappmvp.activities.edit_task.EditTaskActivity;
import br.com.herco.todoappmvp.adapters.CategoryAdapter;
import br.com.herco.todoappmvp.adapters.TaskAdapter;
import br.com.herco.todoappmvp.constants.Constants;
import br.com.herco.todoappmvp.dto.TaskDTO;
import br.com.herco.todoappmvp.models.CategoryModel;
import br.com.herco.todoappmvp.models.TaskModel;
import br.com.herco.todoappmvp.mvp.BaseActivity;
import br.com.herco.todoappmvp.repositories.task.TaskRepository;
import br.com.herco.todoappmvp.services.database.preferences.DataBasePreferences;
import br.com.herco.todoappmvp.viewholders.TaskViewHolder;

public class MainActivity extends BaseActivity<MainPresenter> implements IMainContractView, OnTaskListener {

    private RecyclerView recyclerViewCategory;
    private List<CategoryModel> categories;

    private RecyclerView recyclerViewTask;
    private SwipeController taskSwipeController;
    private ItemTouchHelper itemTouchHelper;

    private TaskAdapter taskAdapter;
    private FloatingActionButton fabNewTask;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initUI() {

        this.getUser();
        this.buildRecyclerViewCategories();
        this.buildRecyclerViewTasks();
        this.getFloatingActionButtonBNewTask();
        this.presenter.loadTasks();
    }

    @Override
    public MainPresenter loadPresenter() {
        return new MainPresenter(this, new TaskRepository(
                new DataBasePreferences(getSharedPreferences(
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
        recyclerViewCategory.setLayoutManager(new LinearLayoutManager(this) {
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

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerViewTask.setLayoutManager(linearLayoutManager);
        taskAdapter = new TaskAdapter(this, recyclerViewTask, null);
        taskAdapter.setOnClickListener(this);

        recyclerViewTask.setAdapter(taskAdapter);

        //Attached the ItemTouchHelper
        taskSwipeController = new SwipeController(0, ItemTouchHelper.LEFT);
        itemTouchHelper = new ItemTouchHelper(taskSwipeController);
        itemTouchHelper.attachToRecyclerView(recyclerViewTask);

        taskSwipeController.setListener(this);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.Keys.ACTIVITY_FROM_RESULT_CODE_TASK && data != null) {
            TaskDTO taskDTOReturned = (TaskDTO) data.getSerializableExtra(Constants.Keys.TASK_DTO);
            if (taskDTOReturned != null) {
                // TODO: NotifyDataChange
                taskAdapter.addTask(taskDTOReturned.getTaskModel());
            }
        }
    }

    private void goToNewTask() {
        Intent intent = new Intent(this, EditTaskActivity.class);
        startActivityForResult(intent, Constants.Keys.ACTIVITY_FROM_RESULT_CODE_TASK);
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
    public void onTaskClicked(int index, TaskModel taskModel) {
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

        showSnackBar(view, "Task removed", "UNDO", onPressed -> {
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


}