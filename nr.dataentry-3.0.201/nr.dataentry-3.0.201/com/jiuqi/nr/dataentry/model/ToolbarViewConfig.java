/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataentry.model;

import com.jiuqi.nr.dataentry.model.TreeNodeItem;
import java.util.List;

public class ToolbarViewConfig {
    private String title;
    private String toolStyle;
    private List<TreeNodeItem> chooseButtons;

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getToolStyle() {
        return this.toolStyle;
    }

    public void setToolStyle(String toolStyle) {
        this.toolStyle = toolStyle;
    }

    public List<TreeNodeItem> getChooseButtons() {
        return this.chooseButtons;
    }

    public void setChooseButtons(List<TreeNodeItem> chooseButtons) {
        this.chooseButtons = chooseButtons;
    }
}

