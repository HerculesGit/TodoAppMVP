package br.com.herco.todoappmvp.adapters;


import android.graphics.Canvas;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import br.com.herco.todoappmvp.models.TaskModel;

import static androidx.recyclerview.widget.ItemTouchHelper.Callback.getDefaultUIUtil;

public class Adapter {

}
//public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {
//
//    private List<TaskModel> products = Arrays.asList();
//    private SwipeHandler swipeHandler;
//
//    Adapter(List<TaskModel> products) {
//        this.products = products;
//    }
//
//    class MyViewHolder extends RecyclerView.ViewHolder {
//
//        private ItemRecyclerBinding binding;
//        private View backgroundView;
//        private View foregroundView;
//
//        MyViewHolder(@NonNull ItemRecyclerBinding itemRecyclerBinding) {
//            super(itemRecyclerBinding.getRoot());
//            binding = itemRecyclerBinding;
//            backgroundView = binding.background;
//            foregroundView = binding.foreground;
//        }
//
//        void bind(ViewModel viewModel) {
//            binding.setViewModel(viewModel);
//            binding.executePendingBindings();
//        }
//    }
//
//    static class SwipeHandler extends ItemTouchHelper.Callback {
//
//        private Adapter adapter;
//        private MyViewHolder swipedViewHolder;
//
//        SwipeHandler(Adapter adapter) {
//            this.adapter = adapter;
//        }
//
//        @Override
//        public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
//            MyViewHolder myViewHolder = (MyViewHolder) viewHolder;
//            if (swipedViewHolder != myViewHolder) {
//                return makeMovementFlags(0, ItemTouchHelper.LEFT);
//            } else {
//                return 0;
//            }
//        }
//
//        @Override
//        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
//                              RecyclerView.ViewHolder target) {
//            return false;
//        }
//
//        @Override
//        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
//            undo();
//            swipedViewHolder = (MyViewHolder) viewHolder;
//            adapter.notifyDataSetChanged();
//        }
//
//        @Override
//        public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
//                                float dX, float dY, int actionState, boolean isCurrentlyActive) {
//            if (dX < 0) {
//                MyViewHolder myViewHolder = (MyViewHolder) viewHolder;
//                getDefaultUIUtil().onDraw(c, recyclerView, myViewHolder.foregroundView, dX / 4, dY, actionState, isCurrentlyActive);
//            }
//        }
//
//        @Override
//        public void onChildDrawOver(Canvas c, RecyclerView recyclerView,
//                                    RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState,
//                                    boolean isCurrentlyActive) {
//            if (dX < 0) {
//                MyViewHolder myViewHolder = (MyViewHolder) viewHolder;
//                getDefaultUIUtil().onDrawOver(c, recyclerView, myViewHolder.foregroundView, dX / 4, dY, actionState, isCurrentlyActive);
//            }
//        }
//
//        @Override
//        public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
//            if (viewHolder != null) {
//                MyViewHolder myViewHolder = (MyViewHolder) viewHolder;
//                getDefaultUIUtil().onSelected(myViewHolder.foregroundView);
//            }
//        }
//
//        void undo() {
//            if (swipedViewHolder != null) {
//                getDefaultUIUtil().clearView(swipedViewHolder.foregroundView);
//                adapter.notifyDataSetChanged();
//                swipedViewHolder = null;
//            }
//        }
//
//        void remove() {
//            if (swipedViewHolder != null) {
//                getDefaultUIUtil().clearView(swipedViewHolder.foregroundView);
//                adapter.remove(swipedViewHolder.getAdapterPosition());
//                adapter.notifyDataSetChanged();
//                swipedViewHolder = null;
//            }
//        }
//    }
//
//    @Override
//    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        return new MyViewHolder(
//                ItemRecyclerBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
//    }
//
//    @Override
//    public void onBindViewHolder(MyViewHolder holder, int position) {
//        holder.bind(new ViewModel(products.get(position), swipeHandler));
//    }
//
//    @Override
//    public int getItemCount() {
//        return products.size();
//    }
//
//    public void setSwipeHandler(SwipeHandler swipeHandler) {
//        this.swipeHandler = swipeHandler;
//    }
//
//    public void remove(int position) {
//        products.remove(position);
//    }
//}