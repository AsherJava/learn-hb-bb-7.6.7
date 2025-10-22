/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataentry.model;

import com.jiuqi.nr.dataentry.gather.InfoViewItem;
import java.util.ArrayList;
import java.util.List;

public class InfoViewConfig {
    private String title;
    private String[] showConfig;
    private List<InfoViewItem> chooseViews;

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String[] getShowConfig() {
        return this.showConfig;
    }

    public void setShowConfig(String[] showConfig) {
        this.showConfig = showConfig;
    }

    public List<InfoViewItem> getChooseViews() {
        return this.chooseViews;
    }

    public void setChooseViews(List<InfoViewItem> chooseViews) {
        this.chooseViews = chooseViews;
    }

    public void setDefaultChooseViews() {
        ArrayList<InfoViewItem> chooseViews = new ArrayList<InfoViewItem>();
        this.chooseViews = chooseViews;
    }
}

