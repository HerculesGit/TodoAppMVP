package br.com.herco.todoappmvp.adapters;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.List;

import br.com.herco.todoappmvp.R;
import br.com.herco.todoappmvp.models.TaskModel;

public class TaskAdapterViewPage extends RecyclerView.Adapter<TaskViewHolderPageView> {

    private List<TaskModel> tasks;
    private final Context context;
    private final RecyclerView recyclerViewTask;
    private OnTaskListener onClickListener;
    private View mView;

    public TaskAdapterViewPage(Context context, RecyclerView recyclerView, List<TaskModel> tasks) {
        this.context = context;
        this.recyclerViewTask = recyclerView;

        this.tasks = tasks;
        if (tasks == null) this.tasks = new ArrayList<>();
    }

    @NonNull
    @Override
    public TaskViewHolderPageView onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.task_row_view_pager_item, viewGroup, false);
        return new TaskViewHolderPageView(view);
    }

    private class ImageAdapter extends PagerAdapter {
        final Context context;
        private int currentIndex;

        private ImageAdapter(Context context, int currentIndex) {
            this.context = context;
            this.currentIndex = currentIndex;
        }


        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            int layoutId;
            if (position == 0) {
                layoutId = R.layout.task_row_item;

            } else {
                layoutId = R.layout.task_deleted_row_item;
            }
            mView = LayoutInflater.from(container.getContext())
                    .inflate(layoutId, container, false);

            this.createTaskLayout(container, position);
            container.addView(mView);
            if (position == 0) setClickListener(mView, currentIndex);

            return mView;
        }

        //
        private void createTaskLayout(@NonNull ViewGroup container, int position) {
            if (position == 1) {
                LinearLayout nextChild = (LinearLayout) (container).getChildAt(0);
                LinearLayout root = (LinearLayout) nextChild.getChildAt(0);

                final TaskModel taskModel = tasks.get(currentIndex);
                TextView tvTaskName = (TextView) root.getChildAt(1);

                LinearLayout rbIsDone = (LinearLayout) root.getChildAt(0);
                Drawable radioButton;

                if (taskModel.isDone()) {
                    radioButton = context.getResources().getDrawable(R.drawable.custom_radio_button_is_checked);
                    tvTaskName.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                } else {
                    radioButton = context.getResources().getDrawable(R.drawable.custom_radio_button);
                    tvTaskName.setPaintFlags(Paint.ANTI_ALIAS_FLAG);
                }

                rbIsDone.setBackground(radioButton);
                tvTaskName.setText(taskModel.getName());
            }
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position,
                                Object object) {
            container.removeView((View) object);
        }
    }

    List<String> getTasks = new ArrayList<>();

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolderPageView taskViewHolderPageView, int index) {
        if (!tasks.isEmpty()) {
            boolean contains = getTasks.contains(tasks.get(index).getId());
            ImageAdapter mImageAdapter = new ImageAdapter(context, index);
            taskViewHolderPageView.getViewPager().setAdapter(mImageAdapter);
            if (true) {
                getTasks.add(tasks.get(index).getId());
                taskViewHolderPageView.getViewPager().clearOnPageChangeListeners();
                taskViewHolderPageView.getViewPager().addOnPageChangeListener(
                        new ViewPager.OnPageChangeListener() {
                            @Override
                            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//                            System.out.println("=1> " + positionOffset);
//                            System.out.println("=2>" + positionOffset);
//                            System.out.println("=3> " + positionOffsetPixels);

                            }

                            @Override
                            public void onPageSelected(int position) {
                                System.out.println("=>>>>>>>" + position + "  |  index:" + index);
                                deleteTask(index);

                            }

                            @Override
                            public void onPageScrollStateChanged(int state) {
//                            System.out.println("=>" + state);
//                            System.out.println("=<" + taskViewHolderPageView.getViewPager().getCurrentItem());
//                            System.out.println();
//                            if (state == 2) {
//                                deleteTask(index);
//
//                            }
                            }
                        }
                );
            }

        }
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    private void setClickListener(@NonNull View view, int index) {
        view.setOnClickListener(v -> {
            final TaskModel taskModel = tasks.get(index);
            taskModel.setDone(!taskModel.isDone());

            if (onClickListener != null) {
                this.onClickListener.onTaskClicked(index, taskModel);
            }

            // TODO: Remove it, because this code would is on the updateSuccess
            notifyDataSetChanged();
        });
    }

    public void setOnClickListener(OnTaskListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public void addTask(TaskModel taskModel) {
        tasks.add(0, taskModel);
        notifyDataSetChanged();
    }

    public void addAllTasks(List<TaskModel> taskModels) {
        tasks = taskModels;
        notifyDataSetChanged();
    }

    private void deleteTask(int position) {
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            tasks.remove(position);
//            notifyItemRemoved(position);
            notifyDataSetChanged();
            recyclerViewTask.setItemViewCacheSize(tasks.size());
        }, 5000);
    }
}