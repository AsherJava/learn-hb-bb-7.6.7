/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.period.modal.IPeriodRow
 */
package com.jiuqi.nr.period.select.service.data;

import com.jiuqi.nr.period.modal.IPeriodRow;
import com.jiuqi.nr.period.select.page.Page;
import com.jiuqi.nr.period.select.service.data.DataContext;
import com.jiuqi.nr.period.select.service.data.DataVistor;
import com.jiuqi.nr.period.select.service.data.FictitiousVistor;
import com.jiuqi.nr.period.select.service.data.ThreeVistor;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MonthVistor
implements DataVistor {
    DataContext dataContext;
    private int year;
    private int month;
    private int loadYear;
    private DataVistor parent;
    private Page page;
    private List<IPeriodRow> panelData = new ArrayList<IPeriodRow>();
    private List<IPeriodRow> calcData = new ArrayList<IPeriodRow>();
    private List<DataVistor> children = new ArrayList<DataVistor>();
    private List<String> fict = new ArrayList<String>();

    public MonthVistor(DataContext dataContext) {
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
        this.getDataByYearAndMonth();
        this.page.setTitle(this.month + "\u6708");
        this.page.setCode(this.year + "N" + this.month + "Y");
        this.recCalcPanelData();
        for (IPeriodRow panelDatum : this.calcData) {
            if (!this.dataContext.getSelectRegions().contains(panelDatum.getCode())) continue;
            this.page.setSelectable(true);
            break;
        }
        switch (this.dataContext.getPeriodEntity().getPeriodType()) {
            case WEEK: 
            case DAY: 
            case TENDAY: {
                if (!this.fict.isEmpty()) {
                    for (String s : this.fict) {
                        FictitiousVistor fictitiousVistor = new FictitiousVistor(this.dataContext);
                        this.children.add(fictitiousVistor);
                        fictitiousVistor.setYear(this.year);
                        fictitiousVistor.setMonth(this.month);
                        fictitiousVistor.setLoadYear(this.loadYear);
                        fictitiousVistor.setParent(this);
                        fictitiousVistor.setData(s);
                        fictitiousVistor.build();
                    }
                }
                for (IPeriodRow panelDatum : this.calcData) {
                    ThreeVistor threeVistor = new ThreeVistor(this.dataContext);
                    this.children.add(threeVistor);
                    threeVistor.setYear(this.year);
                    threeVistor.setMonth(this.month);
                    threeVistor.setLoadYear(this.loadYear);
                    threeVistor.setParent(this);
                    threeVistor.setPanelData(panelDatum);
                    threeVistor.build();
                }
                break;
            }
        }
    }

    private void recCalcPanelData() {
        switch (this.dataContext.getPeriodEntity().getPeriodType()) {
            case WEEK: {
                ArrayList<IPeriodRow> weekDatas = new ArrayList<IPeriodRow>();
                for (IPeriodRow iPeriodRow : this.dataContext.getiPeriodRowList()) {
                    Calendar start = Calendar.getInstance();
                    start.setTime(iPeriodRow.getStartDate());
                    Calendar end = Calendar.getInstance();
                    end.setTime(iPeriodRow.getEndDate());
                    if ((start.get(1) != this.year || start.get(2) + 1 != this.month) && (end.get(1) != this.year || end.get(2) + 1 != this.month)) continue;
                    weekDatas.add(iPeriodRow);
                }
                this.calcData = weekDatas;
                break;
            }
            case DAY: {
                ArrayList<IPeriodRow> dayDatas = new ArrayList<IPeriodRow>();
                IPeriodRow monthFirstDay = this.panelData.get(0);
                Calendar monthFirstDayCalendar = Calendar.getInstance();
                monthFirstDayCalendar.setTime(monthFirstDay.getStartDate());
                int dayOfWeek = monthFirstDayCalendar.get(7);
                int dayOfWeekWithMondayFirst = (dayOfWeek + 5) % 7 + 1;
                int index = this.dataContext.getiPeriodRowList().indexOf(monthFirstDay);
                if (dayOfWeekWithMondayFirst != 1) {
                    index = index - dayOfWeekWithMondayFirst + 1;
                }
                if (index < 0) {
                    for (int j = index; j < 0; ++j) {
                        this.fict.add("EMPTY" + Math.abs(j));
                    }
                }
                for (int i = 0; i < this.dataContext.getiPeriodRowList().size(); ++i) {
                    if (i < index || i >= index + 42) continue;
                    dayDatas.add(this.dataContext.getiPeriodRowList().get(i));
                }
                this.calcData = dayDatas;
                break;
            }
            case TENDAY: {
                this.calcData = this.panelData;
            }
        }
    }

    private void getDataByYearAndMonth() {
        this.panelData = this.dataContext.getDataByYearAndMonth(this.year, this.month);
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
        for (DataVistor child : this.children) {
            this.page.addData(child.writeData());
        }
        return this.page;
    }
}

