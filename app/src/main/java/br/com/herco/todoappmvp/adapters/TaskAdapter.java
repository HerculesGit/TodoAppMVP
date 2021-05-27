package br.com.herco.todoappmvp.adapters;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.com.herco.todoappmvp.R;
import br.com.herco.todoappmvp.models.TaskModel;
import br.com.herco.todoappmvp.viewholders.TaskViewHolder;

public class TaskAdapter extends RecyclerView.Adapter<TaskViewHolder> {
    private List<TaskModel> tasks;
    private final Context context;
    private final RecyclerView recyclerViewTask;
    private OnTaskListener onClickListener;
    private TaskModel lastTaskDeleted;
    private int lastPositionDeleted;

    public TaskAdapter(Context context, RecyclerView recyclerViewTask, List<TaskModel> tasks) {
        this.tasks = tasks;
        this.context = context;
        this.recyclerViewTask = recyclerViewTask;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.task_row_item, viewGroup, false);

        setClickListener(view);
        return new TaskViewHolder(view);
    }

    //    @Override
//    public void onBindViewHolder(@NonNull TaskViewHolder taskViewHolder, int position) {
//        if (!tasks.isEmpty()) {
//            final TaskModel taskModel = tasks.get(position);
//
//            taskViewHolder.getTvTaskName().setText(taskModel.getName());
//            TextView tvTaskName = taskViewHolder.getTvTaskName();
//            Drawable radioButton;
//
//            if (taskModel.isDone()) {
//                radioButton = context.getResources().getDrawable(R.drawable.custom_radio_button_is_checked);
//                tvTaskName.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
//            } else {
//                radioButton = context.getResources().getDrawable(R.drawable.custom_radio_button);
//                tvTaskName.setPaintFlags(Paint.ANTI_ALIAS_FLAG);
//            }
//
//            taskViewHolder.getRbIsDone().setBackground(radioButton);
//        }
//    }
    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder taskViewHolder, int position) {
        if (!tasks.isEmpty()) {
            final TaskModel taskModel = tasks.get(position);

            taskViewHolder.getTvTaskName().setText(taskModel.getName());
            TextView tvTaskName = taskViewHolder.getTvTaskName();
            Drawable radioButton;

            if (taskModel.isDone()) {
                radioButton = context.getResources().getDrawable(R.drawable.custom_radio_button_is_checked);
                tvTaskName.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            } else {
                radioButton = context.getResources().getDrawable(R.drawable.custom_radio_button);
                tvTaskName.setPaintFlags(Paint.ANTI_ALIAS_FLAG);
            }

            taskViewHolder.getRbIsDone().setBackground(radioButton);
        }
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public void setOnClickListener(OnTaskListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    private void setClickListener(@NonNull View view) {
        view.setOnClickListener(v -> {
            int index = recyclerViewTask.getChildLayoutPosition(v);
            final TaskModel taskModel = tasks.get(index);
            taskModel.setDone(!taskModel.isDone());

            if (onClickListener != null) {
                this.onClickListener.onTaskClicked(taskModel);
            }

            // TODO: Remove it, because this code would is on the updateSuccess
            notifyDataSetChanged();
        });
    }

    public void addTask(TaskModel taskModel) {
        tasks.add(0, taskModel);
        notifyDataSetChanged();
    }

    public void addAllTasks(List<TaskModel> taskModels) {
        tasks = taskModels;
        notifyDataSetChanged();
    }

    public void deleteTask(int position) {
        lastTaskDeleted = tasks.remove(position);
        lastPositionDeleted = position;
        notifyItemRemoved(position);
    }

    public void restoreTask() {
        if (lastTaskDeleted != null) {
            tasks.add(lastPositionDeleted, lastTaskDeleted);
            notifyItemInserted(lastPositionDeleted);
        }
    }
}

//public class TaskAdapter extends RecyclerView.Adapter<TaskViewHolder> {
//
//    private List<TaskModel> tasks;
//    private final Context context;
//    private final RecyclerView recyclerViewTask;
//    private OnTaskListener onClickListener;
//
//    public TaskAdapter(Context context, RecyclerView recyclerView, List<TaskModel> tasks) {
//        this.context = context;
//        this.recyclerViewTask = recyclerView;
//
//        this.tasks = tasks;
//        if (tasks == null) this.tasks = new ArrayList<>();
//    }
//
//    @NonNull
//    @Override
//    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
//        View view = LayoutInflater.from(viewGroup.getContext())
//                .inflate(R.layout.task_row_view_pager_item, viewGroup, false);
//
//        setClickListener(view);
//        return new TaskViewHolder(view);
//    }
//
//    private class ImageAdapter extends PagerAdapter {
//        final Context context;
//
//        private ImageAdapter(Context context) {
//            this.context = context;
//        }
//
//        @NonNull
//        @Override
//        public Object instantiateItem(@NonNull ViewGroup container, int position) {
//            int layout;
//            // if (position == 0){
//            layout = R.layout.task_deleted_row_item;
////           // } else if (position == 1){
////                layout = R.layout.layout_pagina2;
////            } else {
////                layout = R.layout.layout_pagina3;
////            }
//            View view = LayoutInflater.from(container.getContext())
//                    .inflate(layout, container, false);
//            container.addView(view);
//            return view;
//        }
//
//        @Override
//        public int getCount() {
//            return 3;
//        }
//
//        @Override
//        public boolean isViewFromObject(View view, Object object) {
//            return view == object;
//        }
//
//        @Override
//        public void destroyItem(ViewGroup container, int position,
//                                Object object) {
//            container.removeView((View) object);
//        }
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull TaskViewHolder taskViewHolder, int position) {
//        if (!tasks.isEmpty()) {
//            final TaskModel taskModel = tasks.get(position);
//
//            taskViewHolder.getTvTaskName().setText(taskModel.getName());
//            TextView tvTaskName = taskViewHolder.getTvTaskName();
//            Drawable radioButton;
//
//            if (taskModel.isDone()) {
//                radioButton = context.getResources().getDrawable(R.drawable.custom_radio_button_is_checked);
//                tvTaskName.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
//            } else {
//                radioButton = context.getResources().getDrawable(R.drawable.custom_radio_button);
//                tvTaskName.setPaintFlags(Paint.ANTI_ALIAS_FLAG);
//            }
//
//            taskViewHolder.getRbIsDone().setBackground(radioButton);
//
//            ImageAdapter mImageAdapter = new ImageAdapter(context);
//            taskViewHolder.getViewPager().setAdapter(mImageAdapter);
//        }
//    }
//
//    @Override
//    public int getItemCount() {
//        return tasks.size();
//    }
//
//    private void setClickListener(@NonNull View view) {
//        view.setOnClickListener(v -> {
//            int index = recyclerViewTask.getChildLayoutPosition(v);
//            final TaskModel taskModel = tasks.get(index);
//            taskModel.setDone(!taskModel.isDone());
//
//            if (onClickListener != null) {
//                this.onClickListener.onTaskClicked(taskModel);
//            }
//
//            // TODO: Remove it, because this code would is on the updateSuccess
//            notifyDataSetChanged();
//        });
//    }
//
//    public void setOnClickListener(OnTaskListener onClickListener) {
//        this.onClickListener = onClickListener;
//    }
//
//    public void addTask(TaskModel taskModel) {
//        tasks.add(0, taskModel);
//        notifyDataSetChanged();
//    }
//
//    public void addAllTasks(List<TaskModel> taskModels) {
//        tasks = taskModels;
//        notifyDataSetChanged();
//    }
//}