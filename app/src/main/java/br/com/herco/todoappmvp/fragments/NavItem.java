package br.com.herco.todoappmvp.fragments;

public class NavItem {
    private int icon;
    private int navName;

    public NavItem() {
    }

    public NavItem(int icon, int navName) {
        this.icon = icon;
        this.navName = navName;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public int getNavName() {
        return navName;
    }

    public void setNavName(int navName) {
        this.navName = navName;
    }
}
