/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 */
package com.jiuqi.gcreport.conversion.common;

import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import java.util.Map;

public class GcConversionOrgAndFormContextEnv {
    private boolean afterConversionRealTimeOffset;
    private boolean conversionInputData;
    private String taskId;
    private String schemeId;
    private String rateSchemeCode;
    private String orgId;
    private String orgTitle;
    private String orgTypeId;
    private String orgVersionType;
    private String periodStr;
    private String adjtypeId;
    private String beforeCurrencyCode;
    private String afterCurrencyCode;
    private String selectAdjustCode;
    private FormDefine formDefine;
    private TableModelDefine tableDefine;
    private String formulaSchemeKeys;
    private Map<String, DimensionValue> dimensionSet;
    private DataRegionDefine dataRegionDefine;
    private GcOrgCacheVO org;

    public String getOrgTypeId() {
        return this.orgTypeId;
    }

    public void setOrgTypeId(String orgTypeId) {
        this.orgTypeId = orgTypeId;
    }

    public String getAdjtypeId() {
        return this.adjtypeId;
    }

    public void setAdjtypeId(String adjtypeId) {
        this.adjtypeId = adjtypeId;
    }

    public String getSchemeId() {
        return this.schemeId;
    }

    public void setSchemeId(String schemeId) {
        this.schemeId = schemeId;
    }

    public String getTaskId() {
        return this.taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getPeriodStr() {
        return this.periodStr;
    }

    public void setPeriodStr(String periodStr) {
        this.periodStr = periodStr;
    }

    public String getBeforeCurrencyCode() {
        return this.beforeCurrencyCode;
    }

    public void setBeforeCurrencyCode(String beforeCurrencyCode) {
        this.beforeCurrencyCode = beforeCurrencyCode;
    }

    public String getAfterCurrencyCode() {
        return this.afterCurrencyCode;
    }

    public void setAfterCurrencyCode(String afterCurrencyCode) {
        this.afterCurrencyCode = afterCurrencyCode;
    }

    public String getOrgId() {
        return this.orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public Map<String, DimensionValue> getDimensionSet() {
        return this.dimensionSet;
    }

    public void setDimensionSet(Map<String, DimensionValue> dimensionSet) {
        this.dimensionSet = dimensionSet;
    }

    public String getOrgTitle() {
        return this.orgTitle;
    }

    public void setOrgTitle(String orgTitle) {
        this.orgTitle = orgTitle;
    }

    public String getFormulaSchemeKeys() {
        return this.formulaSchemeKeys;
    }

    public void setFormulaSchemeKeys(String formulaSchemeKeys) {
        this.formulaSchemeKeys = formulaSchemeKeys;
    }

    public FormDefine getFormDefine() {
        return this.formDefine;
    }

    public void setFormDefine(FormDefine formDefine) {
        this.formDefine = formDefine;
    }

    public TableModelDefine getTableDefine() {
        return this.tableDefine;
    }

    public void setTableDefine(TableModelDefine tableDefine) {
        this.tableDefine = tableDefine;
    }

    public DataRegionDefine getDataRegionDefine() {
        return this.dataRegionDefine;
    }

    public void setDataRegionDefine(DataRegionDefine dataRegionDefine) {
        this.dataRegionDefine = dataRegionDefine;
    }

    public void setOrg(GcOrgCacheVO org) {
        this.org = org;
    }

    public GcOrgCacheVO getOrg() {
        return this.org;
    }

    public String getRateSchemeCode() {
        return this.rateSchemeCode;
    }

    public void setRateSchemeCode(String rateSchemeCode) {
        this.rateSchemeCode = rateSchemeCode;
    }

    public boolean isAfterConversionRealTimeOffset() {
        return this.afterConversionRealTimeOffset;
    }

    public void setAfterConversionRealTimeOffset(boolean afterConversionRealTimeOffset) {
        this.afterConversionRealTimeOffset = afterConversionRealTimeOffset;
    }

    public boolean isConversionInputData() {
        return this.conversionInputData;
    }

    public void setConversionInputData(boolean conversionInputData) {
        this.conversionInputData = conversionInputData;
    }

    public String getSelectAdjustCode() {
        return this.selectAdjustCode;
    }

    public void setSelectAdjustCode(String selectAdjustCode) {
        this.selectAdjustCode = selectAdjustCode;
    }

    public String getOrgVersionType() {
        return this.orgVersionType;
    }

    public void setOrgVersionType(String orgVersionType) {
        this.orgVersionType = orgVersionType;
    }
}

