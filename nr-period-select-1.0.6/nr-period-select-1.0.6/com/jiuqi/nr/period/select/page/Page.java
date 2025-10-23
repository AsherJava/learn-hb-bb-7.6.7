/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.period.select.page;

import com.jiuqi.nr.period.select.page.Adjust;
import java.util.ArrayList;
import java.util.List;

public class Page {
    private String code;
    private String title;
    private boolean selectable = false;
    private boolean noCurrentMonth = false;
    private String rangeState = "";
    private boolean selected = false;
    private List<Adjust> adjustData;
    private String mark = "";
    private String panelTitle;
    private String panelType;
    private List<Page> data;

    public void addData(Page page) {
        if (null == this.data) {
            this.data = new ArrayList<Page>();
        }
        this.data.add(page);
    }

    public String getRangeState() {
        return this.rangeState;
    }

    public void setRangeState(String rangeState) {
        this.rangeState = rangeState;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isSelectable() {
        return this.selectable;
    }

    public void setSelectable(boolean selectable) {
        this.selectable = selectable;
    }

    public boolean isNoCurrentMonth() {
        return this.noCurrentMonth;
    }

    public void setNoCurrentMonth(boolean noCurrentMonth) {
        this.noCurrentMonth = noCurrentMonth;
    }

    public boolean isSelected() {
        return this.selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public List<Adjust> getAdjustData() {
        return this.adjustData;
    }

    public void setAdjustData(List<Adjust> adjustData) {
        this.adjustData = adjustData;
    }

    public String getMark() {
        return this.mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getPanelTitle() {
        return this.panelTitle;
    }

    public void setPanelTitle(String panelTitle) {
        this.panelTitle = panelTitle;
    }

    public String getPanelType() {
        return this.panelType;
    }

    public void setPanelType(String panelType) {
        this.panelType = panelType;
    }

    public List<Page> getData() {
        return this.data;
    }

    public void setData(List<Page> data) {
        this.data = data;
    }
}

