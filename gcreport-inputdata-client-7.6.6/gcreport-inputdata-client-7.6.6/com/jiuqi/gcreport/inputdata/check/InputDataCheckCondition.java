/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 */
package com.jiuqi.gcreport.inputdata.check;

import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import java.util.List;
import java.util.Map;

public class InputDataCheckCondition {
    private String sn;
    private String taskId;
    private String dataTime;
    private GcOrgCacheVO unitCode;
    private String checkLevel;
    private List<GcOrgCacheVO> oppUnitId;
    private List<String> checkRule;
    private String currencyCode;
    private String selectAdjustCode;
    private String unitDefine;
    private String exportTabType;
    private String currentTab;
    private List<String> selectExportTabs;
    public int manualCheckAmtType;
    private Map<String, Object> filterCondition;
    private List<String> otherShowColumns;
    private List<String> checkGatherColumns;
    private List<String> recordDataIds;
    private List<Map<String, Object>> recordDatas;
    private Integer pageNum;
    private Integer pageSize;

    public String getSn() {
        return this.sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getTaskId() {
        return this.taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getDataTime() {
        return this.dataTime;
    }

    public void setDataTime(String dataTime) {
        this.dataTime = dataTime;
    }

    public GcOrgCacheVO getUnitCode() {
        return this.unitCode;
    }

    public void setUnitCode(GcOrgCacheVO unitCode) {
        this.unitCode = unitCode;
    }

    public String getCheckLevel() {
        return this.checkLevel;
    }

    public void setCheckLevel(String checkLevel) {
        this.checkLevel = checkLevel;
    }

    public List<GcOrgCacheVO> getOppUnitId() {
        return this.oppUnitId;
    }

    public void setOppUnitId(List<GcOrgCacheVO> oppUnitId) {
        this.oppUnitId = oppUnitId;
    }

    public List<String> getCheckRule() {
        return this.checkRule;
    }

    public void setCheckRule(List<String> checkRule) {
        this.checkRule = checkRule;
    }

    public String getCurrencyCode() {
        return this.currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getSelectAdjustCode() {
        return this.selectAdjustCode;
    }

    public void setSelectAdjustCode(String selectAdjustCode) {
        this.selectAdjustCode = selectAdjustCode;
    }

    public String getExportTabType() {
        return this.exportTabType;
    }

    public void setExportTabType(String exportTabType) {
        this.exportTabType = exportTabType;
    }

    public String getCurrentTab() {
        return this.currentTab;
    }

    public void setCurrentTab(String currentTab) {
        this.currentTab = currentTab;
    }

    public List<String> getSelectExportTabs() {
        return this.selectExportTabs;
    }

    public void setSelectExportTabs(List<String> selectExportTabs) {
        this.selectExportTabs = selectExportTabs;
    }

    public int getManualCheckAmtType() {
        return this.manualCheckAmtType;
    }

    public void setManualCheckAmtType(int manualCheckAmtType) {
        this.manualCheckAmtType = manualCheckAmtType;
    }

    public Map<String, Object> getFilterCondition() {
        return this.filterCondition;
    }

    public void setFilterCondition(Map<String, Object> filterCondition) {
        this.filterCondition = filterCondition;
    }

    public Integer getPageNum() {
        return this.pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public List<String> getOtherShowColumns() {
        return this.otherShowColumns;
    }

    public List<String> getCheckGatherColumns() {
        return this.checkGatherColumns;
    }

    public void setCheckGatherColumns(List<String> checkGatherColumns) {
        this.checkGatherColumns = checkGatherColumns;
    }

    public void setOtherShowColumns(List<String> otherShowColumns) {
        this.otherShowColumns = otherShowColumns;
    }

    public List<String> getRecordDataIds() {
        return this.recordDataIds;
    }

    public void setRecordDataIds(List<String> recordDataIds) {
        this.recordDataIds = recordDataIds;
    }

    public List<Map<String, Object>> getRecordDatas() {
        return this.recordDatas;
    }

    public void setRecordDatas(List<Map<String, Object>> recordDatas) {
        this.recordDatas = recordDatas;
    }

    public String getUnitDefine() {
        return this.unitDefine;
    }

    public void setUnitDefine(String unitDefine) {
        this.unitDefine = unitDefine;
    }
}

