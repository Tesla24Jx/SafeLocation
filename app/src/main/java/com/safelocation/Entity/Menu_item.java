package com.safelocation.Entity;

/**
 * Created by Juliet on 2017/3/1.
 */

public class Menu_item {
    int item_icon;
    String item_name;
    public  Menu_item(String item_name,int item_icon){
        this.item_icon = item_icon;
        this.item_name = item_name;
    }

    public int getItem_icon() {
        return item_icon;
    }

    public void setItem_icon(int item_icon) {
        this.item_icon = item_icon;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }
}
