package br.com.herco.todoappmvp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.com.herco.todoappmvp.R;
import br.com.herco.todoappmvp.models.CategoryModel;
import br.com.herco.todoappmvp.viewholders.CategoryViewHolder;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryViewHolder> {

    private final List<CategoryModel> categories;

    public CategoryAdapter(List<CategoryModel> categories) {
        this.categories = categories;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.category_row_item, viewGroup, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder categoryViewHolder, int position) {
        if (!categories.isEmpty()) {
            final CategoryModel categoryModel = categories.get(position);
            categoryViewHolder.getTvCategoryName().setText(categoryModel.getCategoryName());
            categoryViewHolder.getTvAmountTasks()
                    .setText(categoryModel.getAmountTask() + " tasks");
        }
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }
}
