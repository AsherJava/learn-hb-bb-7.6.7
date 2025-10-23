/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.nr.datascheme.api.AdjustPeriod
 *  com.jiuqi.nr.period.common.utils.PeriodUtils
 *  com.jiuqi.nr.period.modal.IPeriodRow
 */
package com.jiuqi.nr.period.select.service.data;

import com.jiuqi.np.period.PeriodType;
import com.jiuqi.nr.datascheme.api.AdjustPeriod;
import com.jiuqi.nr.period.common.utils.PeriodUtils;
import com.jiuqi.nr.period.modal.IPeriodRow;
import com.jiuqi.nr.period.select.common.AdjustUtil;
import com.jiuqi.nr.period.select.common.Mode;
import com.jiuqi.nr.period.select.page.Adjust;
import com.jiuqi.nr.period.select.page.Page;
import com.jiuqi.nr.period.select.service.data.CustomVistor;
import com.jiuqi.nr.period.select.service.data.DataContext;
import com.jiuqi.nr.period.select.service.data.DataVistor;
import com.jiuqi.nr.period.select.service.data.FictitiousVistor;
import com.jiuqi.nr.period.select.service.data.MonthVistor;
import com.jiuqi.nr.period.select.service.data.SecondVistor;
import java.util.ArrayList;
import java.util.List;

public class YearVistor
implements DataVistor {
    DataContext dataContext;
    private int year;
    private int month;
    private int loadYear;
    private DataVistor parent;
    private Page page;
    private List<IPeriodRow> panelData = new ArrayList<IPeriodRow>();
    private List<DataVistor> children = new ArrayList<DataVistor>();
    private List<String> fict = new ArrayList<String>();

    public YearVistor(DataContext dataContext) {
        this.dataContext = dataContext;
    }

    public void setParent(DataVistor parent) {
        this.parent = parent;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setLoadYear(int loadYear) {
        this.loadYear = loadYear;
    }

    private String nextLevel() {
        return null;
    }

    /*
     * WARNING - void declaration
     */
    public void build() {
        this.page = new Page();
        this.page.setPanelTitle(this.year + "");
        this.page.setPanelType(this.getNextPanelType());
        this.getDataByYear();
        if (this.isLastPanel()) {
            IPeriodRow iPeriodRow = this.panelData.get(0);
            this.page.setTitle(iPeriodRow.getSimpleTitle());
            this.page.setCode(iPeriodRow.getCode());
            this.loadAdjust();
            for (IPeriodRow panelDatum : this.panelData) {
                if (!this.dataContext.getSelectRegions().contains(panelDatum.getCode())) continue;
                this.page.setSelectable(true);
            }
            if (Mode.R.equals((Object)this.dataContext.getMode())) {
                if (this.panelData.get(0).equals(this.dataContext.getRangeStart())) {
                    this.page.setRangeState("NODE");
                } else if (this.panelData.get(0).equals(this.dataContext.getRangeEnd())) {
                    this.page.setRangeState("NODE");
                }
            }
            if (this.dataContext.getSelectedPeriod().contains(iPeriodRow.getCode())) {
                this.page.setSelected(true);
                this.setSelected(this.parent);
            }
        } else {
            this.page.setTitle(this.year + "");
            this.page.setCode(this.year + "");
            for (IPeriodRow iPeriodRow : this.panelData) {
                if (!this.dataContext.getSelectRegions().contains(iPeriodRow.getCode())) continue;
                this.page.setSelectable(true);
                break;
            }
            if (this.isSelectYear()) {
                this.page.setSelected(true);
            }
            if (this.loadYear == this.year) {
                if (PeriodUtils.isPeriod13((String)this.dataContext.getPeriodEntity().getCode(), (PeriodType)this.dataContext.getPeriodEntity().getPeriodType())) {
                    boolean has0 = false;
                    for (IPeriodRow iPeriodRow : this.dataContext.getiPeriodRowList()) {
                        int i = Integer.parseInt(iPeriodRow.getCode().substring(5));
                        if (i != 0) continue;
                        has0 = true;
                    }
                    if (has0) {
                        void var2_12;
                        boolean bl = true;
                        while (var2_12 <= 2) {
                            this.fict.add("EMPTY" + (int)var2_12);
                            ++var2_12;
                        }
                    }
                }
                switch (this.dataContext.getPeriodEntity().getPeriodType()) {
                    case HALFYEAR: 
                    case SEASON: 
                    case MONTH: {
                        if (!this.fict.isEmpty()) {
                            for (String string : this.fict) {
                                FictitiousVistor fictitiousVistor = new FictitiousVistor(this.dataContext);
                                this.children.add(fictitiousVistor);
                                fictitiousVistor.setYear(this.year);
                                fictitiousVistor.setMonth(this.month);
                                fictitiousVistor.setLoadYear(this.loadYear);
                                fictitiousVistor.setParent(this);
                                fictitiousVistor.setData(string);
                                fictitiousVistor.build();
                            }
                        }
                        for (IPeriodRow iPeriodRow : this.panelData) {
                            SecondVistor secondVistor = new SecondVistor(this.dataContext);
                            this.children.add(secondVistor);
                            secondVistor.setYear(iPeriodRow.getYear());
                            secondVistor.setLoadYear(this.loadYear);
                            secondVistor.setParent(this);
                            secondVistor.setPanelData(iPeriodRow);
                            secondVistor.build();
                        }
                        break;
                    }
                    case TENDAY: 
                    case WEEK: 
                    case DAY: {
                        for (int i = 1; i <= 12; ++i) {
                            MonthVistor monthVistor = new MonthVistor(this.dataContext);
                            this.children.add(monthVistor);
                            monthVistor.setYear(this.year);
                            monthVistor.setMonth(i);
                            monthVistor.setLoadYear(this.loadYear);
                            monthVistor.setParent(this);
                            monthVistor.build();
                        }
                        break;
                    }
                    case CUSTOM: {
                        for (IPeriodRow iPeriodRow : this.panelData) {
                            CustomVistor customVistor = new CustomVistor(this.dataContext);
                            this.children.add(customVistor);
                            customVistor.setParent(this);
                            customVistor.setPanelData(iPeriodRow);
                            customVistor.build();
                        }
                        break;
                    }
                }
            }
        }
    }

    private boolean isSelectYear() {
        ArrayList<Integer> selectedYears = new ArrayList<Integer>();
        for (IPeriodRow iPeriodRow : this.dataContext.getiPeriodRowList()) {
            if (!this.dataContext.getSelectedPeriod().contains(iPeriodRow.getCode())) continue;
            selectedYears.add(iPeriodRow.getYear());
        }
        return selectedYears.contains(this.year);
    }

    private void getDataByYear() {
        this.panelData = this.dataContext.getDataByYear(this.year);
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
        return depth.indexOf(this.calcPanelType()) == depth.size() - 1;
    }

    private String calcPanelType() {
        if (null == this.parent) {
            switch (this.dataContext.getPeriodEntity().getPeriodType()) {
                case HALFYEAR: 
                case SEASON: 
                case MONTH: 
                case YEAR: {
                    return PeriodUtils.getPeriodType((PeriodType)this.dataContext.getPeriodEntity().getPeriodType());
                }
                case TENDAY: 
                case WEEK: 
                case DAY: {
                    return PeriodUtils.getPeriodType((PeriodType)PeriodType.MONTH);
                }
                case CUSTOM: {
                    return PeriodUtils.getPeriodType((PeriodType)PeriodType.CUSTOM);
                }
            }
        }
        return this.parent.getPage().getPanelType();
    }

    private String getNextPanelType() {
        List<String> integers = this.dataContext.panelDepth();
        if (integers.indexOf(this.calcPanelType()) != integers.size() - 1) {
            return integers.get(integers.indexOf(this.calcPanelType()) + 1);
        }
        return "";
    }

    private void loadAdjust() {
        List<AdjustPeriod> adjustPeriodList = this.dataContext.getAdjustPeriodList();
        ArrayList<Adjust> adjustData = new ArrayList<Adjust>();
        for (Adjust item : AdjustUtil.toAdjust(adjustPeriodList)) {
            if (!item.getPeriod().equals(this.panelData.get(0).getCode())) continue;
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

