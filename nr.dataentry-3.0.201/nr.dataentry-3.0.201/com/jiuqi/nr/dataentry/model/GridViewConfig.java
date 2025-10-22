/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataentry.model;

import com.jiuqi.nr.dataentry.model.TreeNodeItem;
import java.util.List;

public class GridViewConfig {
    private String title;
    private Boolean menuEnable;
    private List<TreeNodeItem> menus;

    public List<TreeNodeItem> getMenus() {
        return this.menus;
    }

    public void setMenus(List<TreeNodeItem> menus) {
        this.menus = menus;
    }

    public Boolean getMenuEnable() {
        return this.menuEnable;
    }

    public void setMenuEnable(Boolean menuEnable) {
        this.menuEnable = menuEnable;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

