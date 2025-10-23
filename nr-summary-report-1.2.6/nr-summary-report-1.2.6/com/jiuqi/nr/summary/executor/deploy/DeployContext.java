/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DataGroup
 *  com.jiuqi.nr.datascheme.api.DataScheme
 *  com.jiuqi.nr.datascheme.api.DataTable
 */
package com.jiuqi.nr.summary.executor.deploy;

import com.jiuqi.nr.datascheme.api.DataGroup;
import com.jiuqi.nr.datascheme.api.DataScheme;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.summary.model.cell.DataCell;
import com.jiuqi.nr.summary.model.report.SummaryFloatRegion;
import com.jiuqi.nr.summary.model.report.SummaryReport;
import com.jiuqi.nr.summary.model.report.SummaryReportModel;
import com.jiuqi.nr.summary.model.soulution.SummarySolutionModel;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.lang.Nullable;

public class DeployContext {
    private SummarySolutionModel solutionModel;
    private DataScheme dataScheme;
    private DataGroup currDataGroup;
    private DataTable currDataTable;
    private boolean refreshDataScheme = false;
    private SummaryReport currReport;
    @Nullable
    private SummaryReportModel designReportModel;
    @Nullable
    private SummaryReportModel runTimeReportModel;
    private Map<Integer, String> dataTableMap = new HashMap<Integer, String>();
    private SummaryFloatRegion currentFloatRegion;
    private List<DataCell> currentRegionDataCells;
    private double tableStep;

    public DataScheme getDataScheme() {
        return this.dataScheme;
    }

    public void setDataScheme(DataScheme dataScheme) {
        this.dataScheme = dataScheme;
    }

    public boolean isRefreshDataScheme() {
        return this.refreshDataScheme;
    }

    public void setRefreshDataScheme(boolean refreshDataScheme) {
        this.refreshDataScheme = refreshDataScheme;
    }

    public SummaryReport getCurrReport() {
        return this.currReport;
    }

    public void setCurrReport(SummaryReport currReport) {
        this.currReport = currReport;
    }

    public Map<Integer, String> getDataTableMap() {
        return this.dataTableMap;
    }

    public void setDataTableMap(Map<Integer, String> dataTableMap) {
        this.dataTableMap = dataTableMap;
    }

    public void putDataTableMap(Integer key, String dataTableName) {
        if (this.dataTableMap == null) {
            this.dataTableMap = new HashMap<Integer, String>();
        }
        this.dataTableMap.put(key, dataTableName);
    }

    public void resetDataTableMap() {
        this.dataTableMap.clear();
    }

    @Nullable
    public SummaryReportModel getDesignReportModel() {
        return this.designReportModel;
    }

    public void setDesignReportModel(@Nullable SummaryReportModel designReportModel) {
        this.designReportModel = designReportModel;
    }

    @Nullable
    public SummaryReportModel getRunTimeReportModel() {
        return this.runTimeReportModel;
    }

    public void setRunTimeReportModel(@Nullable SummaryReportModel runTimeReportModel) {
        this.runTimeReportModel = runTimeReportModel;
    }

    public SummarySolutionModel getSolutionModel() {
        return this.solutionModel;
    }

    public void setSolutionModel(@Nullable SummarySolutionModel solutionModel) {
        this.solutionModel = solutionModel;
    }

    public DataGroup getCurrDataGroup() {
        return this.currDataGroup;
    }

    public void setCurrDataGroup(DataGroup currDataGroup) {
        this.currDataGroup = currDataGroup;
    }

    public SummaryFloatRegion getCurrentFloatRegion() {
        return this.currentFloatRegion;
    }

    public void setCurrentFloatRegion(SummaryFloatRegion currentFloatRegion) {
        this.currentFloatRegion = currentFloatRegion;
    }

    public List<DataCell> getCurrentRegionDataCells() {
        return this.currentRegionDataCells;
    }

    public void setCurrentRegionDataCells(List<DataCell> currentRegionDataCells) {
        this.currentRegionDataCells = currentRegionDataCells;
    }

    public DataTable getCurrDataTable() {
        return this.currDataTable;
    }

    public void setCurrDataTable(DataTable currDataTable) {
        this.currDataTable = currDataTable;
    }

    public double getTableStep() {
        return this.tableStep;
    }

    public void setTableStep(double tableStep) {
        this.tableStep = tableStep;
    }
}

