package com.camartin.farmadrid.model;

/**
 * Created by Ravi on 29/07/15.
 */
public class NavDrawerItem {
    private boolean showNotify;
    private String title;
    private int iconMenu;

    public NavDrawerItem() {

    }

    public NavDrawerItem(boolean showNotify, String title, int iconMenu) {
        this.showNotify = showNotify;
        this.title = title;
        this.iconMenu = iconMenu;
    }

    public boolean isShowNotify() {
        return showNotify;
    }

    public void setShowNotify(boolean showNotify) {
        this.showNotify = showNotify;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getIconMenu() {
        return iconMenu;
    }

    public void setIconMenu(int iconMenu) {
        this.iconMenu = iconMenu;
    }
}
