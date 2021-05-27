package br.com.herco.todoappmvp.adapters;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import br.com.herco.todoappmvp.R;

public class TaskViewHolderPageView extends RecyclerView.ViewHolder {
    private final ViewPager viewPager;

    public TaskViewHolderPageView(View view) {
        super(view);
        this.viewPager = view.findViewById(R.id.viewPager);
    }

    public ViewPager getViewPager() {
        return viewPager;
    }
}
