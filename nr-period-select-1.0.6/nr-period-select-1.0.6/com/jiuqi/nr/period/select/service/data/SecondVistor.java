/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.AdjustPeriod
 *  com.jiuqi.nr.period.modal.IPeriodRow
 */
package com.jiuqi.nr.period.select.service.data;

import com.jiuqi.nr.datascheme.api.AdjustPeriod;
import com.jiuqi.nr.period.modal.IPeriodRow;
import com.jiuqi.nr.period.select.common.AdjustUtil;
import com.jiuqi.nr.period.select.common.Mode;
import com.jiuqi.nr.period.select.page.Adjust;
import com.jiuqi.nr.period.select.page.Page;
import com.jiuqi.nr.period.select.service.data.DataContext;
import com.jiuqi.nr.period.select.service.data.DataVistor;
import java.util.ArrayList;
import java.util.List;

public class SecondVistor
implements DataVistor {
    DataContext dataContext;
    private int year;
    private int month;
    private int loadYear;
    private DataVistor parent;
    private Page page;
    private IPeriodRow panelData = null;
    private List<DataVistor> children = new ArrayList<DataVistor>();

    public void setPanelData(IPeriodRow panelData) {
        this.panelData = panelData;
    }

    public SecondVistor(DataContext dataContext) {
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
        this.page.setPanelTitle(this.panelData.getTitle());
        this.page.setPanelType(this.getNextPanelType());
        if (this.isLastPanel()) {
            this.page.setTitle(this.panelData.getSimpleTitle());
            this.page.setCode(this.panelData.getCode());
            this.loadAdjust();
            if (this.dataContext.getSelectRegions().contains(this.panelData.getCode())) {
                this.page.setSelectable(true);
            }
            if (Mode.R.equals((Object)this.dataContext.getMode())) {
                if (this.panelData.getCode().equals(this.dataContext.getRangeStart())) {
                    this.page.setRangeState("NODE");
                } else if (this.panelData.getCode().equals(this.dataContext.getRangeEnd())) {
                    this.page.setRangeState("NODE");
                }
            }
            if (this.dataContext.getSelectedPeriod().contains(this.panelData.getCode())) {
                this.page.setSelected(true);
                this.setSelected(this.parent);
            }
        }
    }

    private void setSelected(DataVistor dataVistor) {
        if (null != dataVistor) {
            dataVistor.getPage().setSelected(true);
            this.setSelected(dataVistor.getParent());
        }
    }

    @Override
    public Page getPage() {
        return this.page;
    }

    @Override
    public DataVistor getParent() {
        return this.parent;
    }

    private boolean isLastPanel() {
        List<String> depth = this.dataContext.panelDepth();
        return depth.indexOf(this.parent.getPage().getPanelType()) == depth.size() - 1;
    }

    private String getNextPanelType() {
        List<String> integers = this.dataContext.panelDepth();
        if (integers.indexOf(this.parent.getPage().getPanelType()) != integers.size() - 1) {
            return integers.get(integers.indexOf(this.parent.getPage().getPanelType()) + 1);
        }
        return "";
    }

    private void loadAdjust() {
        List<AdjustPeriod> adjustPeriodList = this.dataContext.getAdjustPeriodList();
        ArrayList<Adjust> adjustData = new ArrayList<Adjust>();
        for (Adjust item : AdjustUtil.toAdjust(adjustPeriodList)) {
            if (!item.getPeriod().equals(this.panelData.getCode())) continue;
            if (item.getCode().equals(this.dataContext.getAdjust())) {
                this.dataContext.setAdjust(item.getCode());
                item.setSelected(true);
            }
            adjustData.add(item);
        }
        List<Adjust> adjusts = AdjustUtil.sortNoAdjust(adjustData);
        AdjustUtil.calcNoAdjust(adjustData);
        this.page.setAdjustData(adjusts);
    }

    @Override
    public Page writeData() {
        for (DataVistor child : this.children) {
            this.page.addData(child.writeData());
        }
        return this.page;
    }
}

