package br.com.herco.todoappmvp.viewholders;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import br.com.herco.todoappmvp.R;

public class CategoryViewHolder extends RecyclerView.ViewHolder {
    private final TextView tvAmountTasks;
    private final TextView tvCategoryName;

    public CategoryViewHolder(View view) {
        super(view);
        this.tvAmountTasks = view.findViewById(R.id.tv_amount_tasks);
        this.tvCategoryName = view.findViewById(R.id.tv_category_name);
    }

    public TextView getTvAmountTasks() {
        return tvAmountTasks;
    }

    public TextView getTvCategoryName() {
        return tvCategoryName;
    }
}
