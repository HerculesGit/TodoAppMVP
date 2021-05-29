package br.com.herco.todoappmvp.adapters;

import androidx.recyclerview.widget.RecyclerView;

import br.com.herco.todoappmvp.listeners.RecyclerItemTouchHelperListener;
import br.com.herco.todoappmvp.models.TaskModel;

public interface OnTaskListener extends RecyclerItemTouchHelperListener {
    void onTaskClicked(int index, TaskModel taskModel);

    @Override
    void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position);
}
