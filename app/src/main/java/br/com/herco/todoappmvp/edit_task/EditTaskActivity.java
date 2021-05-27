package br.com.herco.todoappmvp.edit_task;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import com.google.android.material.textfield.TextInputEditText;

import br.com.herco.todoappmvp.R;
import br.com.herco.todoappmvp.constants.Constants;
import br.com.herco.todoappmvp.dto.TaskDTO;
import br.com.herco.todoappmvp.models.TaskModel;
import br.com.herco.todoappmvp.mvp.BaseActivity;
import br.com.herco.todoappmvp.repositories.task.TaskRepository;
import br.com.herco.todoappmvp.services.database.preferences.DataBasePreferences;

public class EditTaskActivity extends BaseActivity<EditTaskPresenter> implements IEditTaskContract {
    private TaskDTO taskDTO;
    private RadioButton rbIsDone;
    private boolean isChecked = false;

    @Override
    public int getLayoutId() {
        return R.layout.activity_edit_task;
    }

    @Override
    public EditTaskPresenter loadPresenter() {
        return new EditTaskPresenter(this, new TaskRepository(
                new DataBasePreferences(getSharedPreferences(
                        Constants.Database.DATABASE_PREFERENCES, Context.MODE_PRIVATE))));
    }

    @Override
    public void onCreateTask(TaskModel task) {
        returnToTaskList();
    }

    @Override
    public void onCreateTaskError(String errorMessage) {
        showToast(errorMessage);
    }

    @Override
    public void initUI() {
        final LinearLayout linearLayoutNewTask = findViewById(R.id.linear_layout_new_task);
        linearLayoutNewTask.setOnClickListener(view -> this.createTaskDTO());

        final FrameLayout frameLayoutBackToList = findViewById(R.id.frame_layout_back_to_list);
        frameLayoutBackToList.setOnClickListener(view -> this.returnToTaskList());

        rbIsDone = findViewById(R.id.rb_is_done);
        rbIsDone.setOnClickListener((v) -> {
            isChecked = !isChecked;
            rbIsDone.setChecked(isChecked);
        });
    }

    private void createTaskDTO() {
        TextInputEditText textInputEditTextTaskName = findViewById(R.id.tie_task_name);
        String taskName = textInputEditTextTaskName.getText().toString().trim();
        if (!taskName.isEmpty()) {
            taskDTO = new TaskDTO();
            TaskModel taskModel = new TaskModel(taskName, rbIsDone.isChecked());
            taskDTO.setTaskModel(taskModel);

            presenter.createTask(taskModel);
        }
    }

    private void returnToTaskList() {
        Intent returnIntent = new Intent();

        if (taskDTO != null) {
            returnIntent.putExtra(Constants.Keys.TASK_DTO, taskDTO);
            setResult(Constants.Keys.ACTIVITY_FROM_RESULT_CODE_TASK, returnIntent);

        } else {
            setResult(Activity.RESULT_CANCELED, returnIntent);
        }
        finish();
    }
}