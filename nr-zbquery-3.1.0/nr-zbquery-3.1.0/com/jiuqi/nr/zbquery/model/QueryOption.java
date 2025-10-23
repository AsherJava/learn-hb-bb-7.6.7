/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.quickreport.model.ValueConvertMode
 */
package com.jiuqi.nr.zbquery.model;

import com.jiuqi.bi.quickreport.model.ValueConvertMode;
import com.jiuqi.nr.zbquery.model.HeaderMode;
import com.jiuqi.nr.zbquery.model.HiddenDimension;
import com.jiuqi.nr.zbquery.model.NullRowDisplayMode;
import com.jiuqi.nr.zbquery.model.OrgFilterItem;
import com.jiuqi.nr.zbquery.model.SumPosition;
import java.util.ArrayList;
import java.util.List;

public class QueryOption {
    private int pageSize = 50;
    private boolean lockRowHead = false;
    private HeaderMode rowHeaderMode = HeaderMode.MERGE;
    private SumPosition sumPosition = SumPosition.BOTTOM;
    private boolean displayTreeLevel = false;
    private int defaultExpandLevel = 1;
    private boolean queryDetailRecord = true;
    private boolean expendInnerDimension = false;
    private NullRowDisplayMode nullRowDisplayMode = NullRowDisplayMode.DEFAULT;
    private ValueConvertMode zeroDisplayMode = ValueConvertMode.NONE;
    private ValueConvertMode nullDisplayMode = ValueConvertMode.NONE;
    private String reportTask;
    private String reportScheme;
    private String orgFilter;
    private List<OrgFilterItem> allOrgFilter = new ArrayList<OrgFilterItem>();
    private boolean displayZBCode = false;
    private boolean displayRowsColumn = false;
    private boolean displayRowCheck = false;
    private List<HiddenDimension> hiddenDimensions = new ArrayList<HiddenDimension>();

    public int getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public boolean isLockRowHead() {
        return this.lockRowHead;
    }

    public void setLockRowHead(boolean lockRowHead) {
        this.lockRowHead = lockRowHead;
    }

    public HeaderMode getRowHeaderMode() {
        return this.rowHeaderMode;
    }

    public void setRowHeaderMode(HeaderMode rowHeaderMode) {
        this.rowHeaderMode = rowHeaderMode;
    }

    public SumPosition getSumPosition() {
        return this.sumPosition;
    }

    public void setSumPosition(SumPosition sumPosition) {
        this.sumPosition = sumPosition;
    }

    public boolean isDisplayTreeLevel() {
        return this.displayTreeLevel;
    }

    public void setDisplayTreeLevel(boolean displayTreeLevel) {
        this.displayTreeLevel = displayTreeLevel;
    }

    public int getDefaultExpandLevel() {
        return this.defaultExpandLevel;
    }

    public void setDefaultExpandLevel(int defaultExpandLevel) {
        this.defaultExpandLevel = defaultExpandLevel;
    }

    public boolean isQueryDetailRecord() {
        return this.queryDetailRecord;
    }

    public void setQueryDetailRecord(boolean queryDetailRecord) {
        this.queryDetailRecord = queryDetailRecord;
    }

    public boolean isExpendInnerDimension() {
        return this.expendInnerDimension;
    }

    public void setExpendInnerDimension(boolean expendInnerDimension) {
        this.expendInnerDimension = expendInnerDimension;
    }

    public NullRowDisplayMode getNullRowDisplayMode() {
        return this.nullRowDisplayMode;
    }

    public void setNullRowDisplayMode(NullRowDisplayMode nullRowDisplayMode) {
        this.nullRowDisplayMode = nullRowDisplayMode;
    }

    public String getReportTask() {
        return this.reportTask;
    }

    public void setReportTask(String reportTask) {
        this.reportTask = reportTask;
    }

    public String getReportScheme() {
        return this.reportScheme;
    }

    public void setReportScheme(String reportScheme) {
        this.reportScheme = reportScheme;
    }

    public String getOrgFilter() {
        return this.orgFilter;
    }

    public void setOrgFilter(String orgFilter) {
        this.orgFilter = orgFilter;
    }

    public List<OrgFilterItem> getAllOrgFilter() {
        return this.allOrgFilter;
    }

    public void setAllOrgFilter(List<OrgFilterItem> allOrgFilter) {
        this.allOrgFilter = allOrgFilter;
    }

    public boolean isDisplayZBCode() {
        return this.displayZBCode;
    }

    public void setDisplayZBCode(boolean displayZBCode) {
        this.displayZBCode = displayZBCode;
    }

    public List<HiddenDimension> getHiddenDimensions() {
        return this.hiddenDimensions;
    }

    public void setHiddenDimensions(List<HiddenDimension> hiddenDimensions) {
        this.hiddenDimensions = hiddenDimensions;
    }

    public boolean isDisplayRowsColumn() {
        return this.displayRowsColumn;
    }

    public void setDisplayRowsColumn(boolean displayRowsColumn) {
        this.displayRowsColumn = displayRowsColumn;
    }

    public boolean isDisplayRowCheck() {
        return this.displayRowCheck;
    }

    public void setDisplayRowCheck(boolean displayRowCheck) {
        this.displayRowCheck = displayRowCheck;
    }

    public ValueConvertMode getZeroDisplayMode() {
        return this.zeroDisplayMode;
    }

    public void setZeroDisplayMode(ValueConvertMode zeroDisplayMode) {
        this.zeroDisplayMode = zeroDisplayMode;
    }

    public ValueConvertMode getNullDisplayMode() {
        return this.nullDisplayMode;
    }

    public void setNullDisplayMode(ValueConvertMode nullDisplayMode) {
        this.nullDisplayMode = nullDisplayMode;
    }
}

