/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.unit.uselector.web.response;

import com.jiuqi.nr.unit.uselector.web.response.IMenuItem;
import java.util.List;

public class QuickMenuInfo {
    private String checkMode = "single";
    private List<IMenuItem> menuItems;

    public String getCheckMode() {
        return this.checkMode;
    }

    public void setCheckMode(String checkMode) {
        this.checkMode = checkMode;
    }

    public List<IMenuItem> getMenuItems() {
        return this.menuItems;
    }

    public void setMenuItems(List<IMenuItem> menuItems) {
        this.menuItems = menuItems;
    }
}

