/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.dto.FixedFieldDefineSettingVO
 *  com.jiuqi.bde.common.dto.FloatRegionConfigVO
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.context.infc.INRContext
 */
package com.jiuqi.gcreport.bde.fetchsetting.client.vo;

import com.jiuqi.bde.common.dto.FixedFieldDefineSettingVO;
import com.jiuqi.bde.common.dto.FloatRegionConfigVO;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.context.infc.INRContext;
import java.util.List;
import java.util.Map;

public class BDEQueryDataCond
implements INRContext {
    private String bblx;
    private boolean filterAdaptCondi;
    private String unitCode;
    private String periodStr;
    private String beginDate;
    private String endDate;
    private Boolean includeUncharged;
    private Boolean includeAdjustVchr;
    private String fetchSchemeId;
    private String formSchemeId;
    private String taskId;
    private String formId;
    private String regionId;
    private String dataLinkId;
    private Map<String, DimensionValue> dimensionSet;
    private FloatRegionConfigVO floatSetting;
    private List<FixedFieldDefineSettingVO> fixedSettingDatas;
    private List<Map<String, Object>> rowDataZbId2valueMapList;
    @Deprecated
    private Map<String, String> currentRowMap;
    public String contextEntityId;
    public String contextFilterExpression;

    public String getBblx() {
        return this.bblx;
    }

    public void setBblx(String bblx) {
        this.bblx = bblx;
    }

    public String getUnitCode() {
        return this.unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    public String getPeriodStr() {
        return this.periodStr;
    }

    public void setPeriodStr(String periodStr) {
        this.periodStr = periodStr;
    }

    public String getBeginDate() {
        return this.beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public String getEndDate() {
        return this.endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Boolean getIncludeUncharged() {
        return this.includeUncharged;
    }

    public void setIncludeUncharged(Boolean includeUncharged) {
        this.includeUncharged = includeUncharged;
    }

    public String getFetchSchemeId() {
        return this.fetchSchemeId;
    }

    public void setFetchSchemeId(String fetchSchemeId) {
        this.fetchSchemeId = fetchSchemeId;
    }

    public String getFormSchemeId() {
        return this.formSchemeId;
    }

    public void setFormSchemeId(String formSchemeId) {
        this.formSchemeId = formSchemeId;
    }

    public String getTaskId() {
        return this.taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getFormId() {
        return this.formId;
    }

    public void setFormId(String formId) {
        this.formId = formId;
    }

    public Map<String, DimensionValue> getDimensionSet() {
        return this.dimensionSet;
    }

    public void setDimensionSet(Map<String, DimensionValue> dimensionSet) {
        this.dimensionSet = dimensionSet;
    }

    public String getRegionId() {
        return this.regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    public String getDataLinkId() {
        return this.dataLinkId;
    }

    public void setDataLinkId(String dataLinkId) {
        this.dataLinkId = dataLinkId;
    }

    public FloatRegionConfigVO getFloatSetting() {
        return this.floatSetting;
    }

    public void setFloatSetting(FloatRegionConfigVO floatSetting) {
        this.floatSetting = floatSetting;
    }

    public List<FixedFieldDefineSettingVO> getFixedSettingDatas() {
        return this.fixedSettingDatas;
    }

    public void setFixedSettingDatas(List<FixedFieldDefineSettingVO> fixedSettingDatas) {
        this.fixedSettingDatas = fixedSettingDatas;
    }

    public List<Map<String, Object>> getRowDataZbId2valueMapList() {
        return this.rowDataZbId2valueMapList;
    }

    public void setRowDataZbId2valueMapList(List<Map<String, Object>> rowDataZbId2valueMapList) {
        this.rowDataZbId2valueMapList = rowDataZbId2valueMapList;
    }

    public Map<String, String> getCurrentRowMap() {
        return this.currentRowMap;
    }

    public void setCurrentRowMap(Map<String, String> currentRowMap) {
        this.currentRowMap = currentRowMap;
    }

    public boolean isFilterAdaptCondi() {
        return this.filterAdaptCondi;
    }

    public void setFilterAdaptCondi(boolean filterAdaptCondi) {
        this.filterAdaptCondi = filterAdaptCondi;
    }

    public String getContextEntityId() {
        return this.contextEntityId;
    }

    public void setContextEntityId(String contextEntityId) {
        this.contextEntityId = contextEntityId;
    }

    public String getContextFilterExpression() {
        return this.contextFilterExpression;
    }

    public void setContextFilterExpression(String contextFilterExpression) {
        this.contextFilterExpression = contextFilterExpression;
    }

    public Boolean getIncludeAdjustVchr() {
        return this.includeAdjustVchr;
    }

    public void setIncludeAdjustVchr(Boolean includeAdjustVchr) {
        this.includeAdjustVchr = includeAdjustVchr;
    }

    public String toString() {
        return "BDEQueryDataCond [filterAdaptCondi=" + this.filterAdaptCondi + ", unitCode=" + this.unitCode + ", periodStr=" + this.periodStr + ", beginDate=" + this.beginDate + ", endDate=" + this.endDate + ", includeUncharged=" + this.includeUncharged + ", includeAdjustVchr=" + this.includeAdjustVchr + ", fetchSchemeId=" + this.fetchSchemeId + ", formSchemeId=" + this.formSchemeId + ", taskId=" + this.taskId + ", formId=" + this.formId + ", regionId=" + this.regionId + ", dataLinkId=" + this.dataLinkId + ", dimensionSet=" + this.dimensionSet + ", floatSetting=" + this.floatSetting + ", fixedSettingDatas=" + this.fixedSettingDatas + ", rowDataZbId2valueMapList=" + this.rowDataZbId2valueMapList + ", currentRowMap=" + this.currentRowMap + ", contextEntityId=" + this.contextEntityId + ", contextFilterExpression=" + this.contextFilterExpression + "]";
    }
}

