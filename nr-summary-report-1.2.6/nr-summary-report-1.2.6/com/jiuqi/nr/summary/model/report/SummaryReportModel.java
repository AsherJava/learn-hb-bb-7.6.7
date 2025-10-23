/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.grid.GridData
 */
package com.jiuqi.nr.summary.model.report;

import com.jiuqi.bi.grid.GridData;
import com.jiuqi.nr.summary.executor.query.QueryPageConfig;
import com.jiuqi.nr.summary.model.cell.DataCell;
import com.jiuqi.nr.summary.model.report.SummaryReportConfig;
import com.jiuqi.nr.summary.model.report.SummaryReportData;
import java.io.Serializable;
import java.util.List;

public class SummaryReportModel
implements Serializable {
    private String key;
    private String name;
    private String title;
    private SummaryReportConfig config;
    private SummaryReportData reportData;
    private List<DataCell> dataCells;
    private QueryPageConfig pageConfig;

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public SummaryReportConfig getConfig() {
        return this.config;
    }

    public void setConfig(SummaryReportConfig config) {
        this.config = config;
    }

    public SummaryReportData getReportData() {
        return this.reportData;
    }

    public void setReportData(SummaryReportData reportData) {
        this.reportData = reportData;
    }

    public List<DataCell> getDataCells() {
        return this.dataCells;
    }

    public void setDataCells(List<DataCell> dataCells) {
        this.dataCells = dataCells;
    }

    public GridData getGridData() {
        return this.reportData != null ? this.reportData.getGridData() : null;
    }

    public void setGridData(GridData gridData) {
        if (this.reportData == null) {
            this.reportData = new SummaryReportData();
        }
        this.reportData.setGridData(gridData);
    }

    public QueryPageConfig getPageConfig() {
        return this.pageConfig;
    }

    public void setPageConfig(QueryPageConfig pageConfig) {
        this.pageConfig = pageConfig;
    }
}

