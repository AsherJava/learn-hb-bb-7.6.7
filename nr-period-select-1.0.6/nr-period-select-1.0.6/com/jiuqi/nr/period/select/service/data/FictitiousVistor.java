/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.period.select.service.data;

import com.jiuqi.nr.period.select.page.Page;
import com.jiuqi.nr.period.select.service.data.DataContext;
import com.jiuqi.nr.period.select.service.data.DataVistor;
import java.util.List;

public class FictitiousVistor
implements DataVistor {
    DataContext dataContext;
    private int year;
    private int month;
    private int loadYear;
    private DataVistor parent;
    private Page page;
    private String data;

    public void setData(String data) {
        this.data = data;
    }

    public FictitiousVistor(DataContext dataContext) {
        this.dataContext = dataContext;
    }

    public void setParent(DataVistor parent) {
        this.parent = parent;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public void setLoadYear(int loadYear) {
        this.loadYear = loadYear;
    }

    public void build() {
        this.page = new Page();
        this.page.setPanelTitle(this.year + "-" + this.month + "");
        this.page.setPanelType(this.getNextPanelType());
        this.page.setTitle(null);
        this.page.setCode(this.data);
        this.page.setSelected(false);
        this.page.setSelectable(false);
        this.page.setNoCurrentMonth(true);
    }

    @Override
    public Page getPage() {
        return this.page;
    }

    @Override
    public DataVistor getParent() {
        return this.parent;
    }

    private String getNextPanelType() {
        List<String> integers = this.dataContext.panelDepth();
        if (integers.indexOf(this.parent.getPage().getPanelType()) != integers.size() - 1) {
            return integers.get(integers.indexOf(this.parent.getPage().getPanelType()) + 1);
        }
        return "";
    }

    @Override
    public Page writeData() {
        return this.page;
    }
}

