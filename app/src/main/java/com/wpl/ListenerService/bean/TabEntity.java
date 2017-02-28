package com.wpl.ListenerService.bean;

/**
 * Created by 培龙 on 2017/2/27.
 */
public class TabEntity implements com.flyco.tablayout.listener.CustomTabEntity {
    private String title;
    private int iconSelected;
    private int iconUnSelected;

    public TabEntity(String title, int iconSelected, int iconUnSelected) {
        this.title = title;
        this.iconSelected = iconSelected;
        this.iconUnSelected = iconUnSelected;
    }

    @Override
    public String getTabTitle() {
        return title;
    }

    @Override
    public int getTabSelectedIcon() {
        return iconSelected;
    }

    @Override
    public int getTabUnselectedIcon() {
        return iconUnSelected;
    }
}
