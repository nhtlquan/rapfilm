package com.example.lequan.film.model;

public class ItemMenu {
    private int icon;
    private int iconSelected;
    private boolean selected = false;
    private int title;

    public ItemMenu(int icon, int iconSelected, int title) {
        this.icon = icon;
        this.iconSelected = iconSelected;
        this.title = title;
    }

    public int getIcon() {
        return this.icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public int getIconSelected() {
        return this.iconSelected;
    }

    public void setIconSelected(int iconSelected) {
        this.iconSelected = iconSelected;
    }

    public int getTitle() {
        return this.title;
    }

    public void setTitle(int title) {
        this.title = title;
    }

    public boolean isSelected() {
        return this.selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
