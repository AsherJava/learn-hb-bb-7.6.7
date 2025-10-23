/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.nr.period.common.utils.PeriodUtils
 *  com.jiuqi.nr.period.modal.IPeriodRow
 */
package com.jiuqi.nr.period.select.service.data;

import com.jiuqi.np.period.PeriodType;
import com.jiuqi.nr.period.common.utils.PeriodUtils;
import com.jiuqi.nr.period.modal.IPeriodRow;
import com.jiuqi.nr.period.select.page.Page;
import com.jiuqi.nr.period.select.service.data.CustomVistor;
import com.jiuqi.nr.period.select.service.data.DataContext;
import com.jiuqi.nr.period.select.service.data.DataVistor;
import com.jiuqi.nr.period.select.service.data.YearVistor;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RootVistor
implements DataVistor {
    DataContext dataContext;
    private int loadYear;
    private DataVistor parent;
    private Page page;
    private int start;
    private int end;
    private List<DataVistor> children = new ArrayList<DataVistor>();

    public void setStart(int start) {
        this.start = start;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public RootVistor(DataContext dataContext) {
        this.dataContext = dataContext;
    }

    @Override
    public Page getPage() {
        return this.page;
    }

    @Override
    public DataVistor getParent() {
        return this.parent;
    }

    public void setLoadYear(int loadYear) {
        this.loadYear = loadYear;
    }

    public void build() {
        this.page = new Page();
        if (this.dataContext.getPeriodEntity().getPeriodType().equals((Object)PeriodType.CUSTOM) && !this.dataContext.isYearGroup()) {
            this.page.setPanelTitle(this.dataContext.getPeriodEntity().getTitle());
            this.page.setPanelType(PeriodUtils.getPeriodType((PeriodType)PeriodType.CUSTOM));
            for (IPeriodRow iPeriodRow : this.dataContext.getiPeriodRowList()) {
                CustomVistor customVistor = new CustomVistor(this.dataContext);
                this.children.add(customVistor);
                customVistor.setPanelData(iPeriodRow);
                customVistor.setParent(this);
                customVistor.build();
            }
        } else {
            this.page.setPanelTitle(this.start + "-" + this.end);
            this.page.setPanelType(PeriodUtils.getPeriodType((PeriodType)PeriodType.YEAR));
            List dbDataYear = this.dataContext.getiPeriodRowList().stream().map(e -> e.getYear()).distinct().collect(Collectors.toList());
            for (int y = this.start; y <= this.end; ++y) {
                if (!dbDataYear.contains(y)) continue;
                YearVistor dataVistor = new YearVistor(this.dataContext);
                this.children.add(dataVistor);
                dataVistor.setLoadYear(this.loadYear);
                dataVistor.setYear(y);
                dataVistor.setParent(this);
                dataVistor.build();
            }
        }
    }

    @Override
    public Page writeData() {
        for (DataVistor child : this.children) {
            this.page.addData(child.writeData());
        }
        return this.page;
    }
}

