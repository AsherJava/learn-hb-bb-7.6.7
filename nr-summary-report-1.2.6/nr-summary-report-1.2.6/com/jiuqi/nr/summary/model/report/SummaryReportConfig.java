/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.summary.model.report;

import com.jiuqi.nr.summary.model.cell.GuestCell;
import com.jiuqi.nr.summary.model.cell.MainCell;
import com.jiuqi.nr.summary.model.report.FilterInfo;
import com.jiuqi.nr.summary.model.report.SequenceType;
import com.jiuqi.nr.summary.model.report.SummaryFloatRegion;
import java.io.Serializable;
import java.util.List;

public class SummaryReportConfig
implements Serializable {
    private String filter;
    private SequenceType sequenceType = SequenceType.NONE;
    private List<SummaryFloatRegion> regions;
    private List<MainCell> mainCells;
    private List<GuestCell> guestCells;
    private List<FilterInfo> rowFilters;
    private List<FilterInfo> colFilters;

    public String getFilter() {
        return this.filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public SequenceType getSequenceType() {
        return this.sequenceType;
    }

    public void setSequenceType(SequenceType sequenceType) {
        this.sequenceType = sequenceType;
    }

    public List<SummaryFloatRegion> getRegions() {
        return this.regions;
    }

    public void setRegions(List<SummaryFloatRegion> regions) {
        this.regions = regions;
    }

    public List<MainCell> getMainCells() {
        return this.mainCells;
    }

    public void setMainCells(List<MainCell> mainCells) {
        this.mainCells = mainCells;
    }

    public List<GuestCell> getGuestCells() {
        return this.guestCells;
    }

    public void setGuestCells(List<GuestCell> guestCells) {
        this.guestCells = guestCells;
    }

    public List<FilterInfo> getRowFilters() {
        return this.rowFilters;
    }

    public void setRowFilters(List<FilterInfo> rowFilters) {
        this.rowFilters = rowFilters;
    }

    public List<FilterInfo> getColFilters() {
        return this.colFilters;
    }

    public void setColFilters(List<FilterInfo> colFilters) {
        this.colFilters = colFilters;
    }
}

