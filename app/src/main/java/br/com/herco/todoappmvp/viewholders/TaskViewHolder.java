package br.com.herco.todoappmvp.viewholders;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import br.com.herco.todoappmvp.R;

public class TaskViewHolder extends RecyclerView.ViewHolder {
    private final TextView tvTaskName;
    private final LinearLayout rbIsDone;
    private final RelativeLayout backgroundView;
    private final RelativeLayout foregroundView;

    public TaskViewHolder(View view) {
        super(view);
        this.tvTaskName = view.findViewById(R.id.tv_task_name);
        this.rbIsDone = view.findViewById(R.id.custom_rb_task_is_done);
        this.backgroundView = view.findViewById(R.id.background_item_deleted_task);
        this.foregroundView = view.findViewById(R.id.foreground);
//        this.viewPager = view.findViewById(R.id.viewPager);
    }

    public TextView getTvTaskName() {
        return tvTaskName;
    }

    public LinearLayout getRbIsDone() {
        return rbIsDone;
    }

    public RelativeLayout getBackgroundView() {
        return backgroundView;
    }

    public RelativeLayout getForegroundView() {
        return foregroundView;
    }
}
