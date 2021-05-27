package br.com.herco.todoappmvp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import br.com.herco.todoappmvp.R;
import br.com.herco.todoappmvp.fragments.NavItem;

public class NavAdapter extends ArrayAdapter<NavItem> {

    private final Context context;
    private final List<NavItem> navItems;

    public NavAdapter(@NonNull Context context, List<NavItem> navItems) {
        super(context, 0, navItems);

        this.context = context;
        this.navItems = navItems;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {

        NavItem navItem = navItems.get(position);

        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.nav_item_fragment, null);
        }

        TextView tvNavName = view.findViewById(R.id.tv_nav_name);
        tvNavName.setText(navItem.getNavName());

        ImageView imgNavIcon = view.findViewById(R.id.img_nav_icon);
        imgNavIcon.setImageResource(navItem.getIcon());
        return view;
    }
}
