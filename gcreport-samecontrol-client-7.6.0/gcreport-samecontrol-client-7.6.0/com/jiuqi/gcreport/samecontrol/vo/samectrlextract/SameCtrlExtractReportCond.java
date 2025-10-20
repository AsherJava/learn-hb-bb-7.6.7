/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFormat
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 */
package com.jiuqi.gcreport.samecontrol.vo.samectrlextract;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import java.util.Date;
import java.util.Map;
import org.springframework.format.annotation.DateTimeFormat;

public class SameCtrlExtractReportCond {
    private String systemId;
    private String taskId;
    private String schemeId;
    private String orgType;
    private String periodStr;
    private String selectAdjustCode;
    private String changedCode;
    private String changedName;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @JsonFormat(pattern="yyyy-MM-dd", timezone="GMT+8")
    private Date changeDate;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @JsonFormat(pattern="yyyy-MM-dd", timezone="GMT+8")
    private Date disposalDate;
    private Integer changedOrgType;
    private String virtualCode;
    private String currencyId;
    private String adjTypeId;
    private String formulaSchemeKey;
    private Map<String, DimensionValue> dimensionSet;
    private JtableContext jtableContext;
    private boolean isGoBack = false;
    private boolean isVirtual = false;
    private GcOrgCenterService gcOrgCenterService;

    public GcOrgCenterService getGcOrgCenterService() {
        return this.gcOrgCenterService;
    }

    public void setGcOrgCenterService(GcOrgCenterService gcOrgCenterService) {
        this.gcOrgCenterService = gcOrgCenterService;
    }

    public boolean isVirtual() {
        return this.isVirtual;
    }

    public void setVirtual(boolean virtual) {
        this.isVirtual = virtual;
    }

    public boolean isGoBack() {
        return this.isGoBack;
    }

    public void setGoBack(boolean goBack) {
        this.isGoBack = goBack;
    }

    public String getSystemId() {
        return this.systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    public String getTaskId() {
        return this.taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getSchemeId() {
        return this.schemeId;
    }

    public void setSchemeId(String schemeId) {
        this.schemeId = schemeId;
    }

    public String getOrgType() {
        return this.orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }

    public String getPeriodStr() {
        return this.periodStr;
    }

    public void setPeriodStr(String periodStr) {
        this.periodStr = periodStr;
    }

    public String getChangedCode() {
        return this.changedCode;
    }

    public void setChangedCode(String changedCode) {
        this.changedCode = changedCode;
    }

    public String getChangedName() {
        return this.changedName;
    }

    public void setChangedName(String changedName) {
        this.changedName = changedName;
    }

    public Date getChangeDate() {
        return this.changeDate;
    }

    public void setChangeDate(Date changeDate) {
        this.changeDate = changeDate;
    }

    public Date getDisposalDate() {
        return this.disposalDate;
    }

    public void setDisposalDate(Date disposalDate) {
        this.disposalDate = disposalDate;
    }

    public Integer getChangedOrgType() {
        return this.changedOrgType;
    }

    public void setChangedOrgType(Integer changedOrgType) {
        this.changedOrgType = changedOrgType;
    }

    public String getVirtualCode() {
        return this.virtualCode;
    }

    public void setVirtualCode(String virtualCode) {
        this.virtualCode = virtualCode;
    }

    public String getCurrencyId() {
        return this.currencyId;
    }

    public void setCurrencyId(String currencyId) {
        this.currencyId = currencyId;
    }

    public String getAdjTypeId() {
        return this.adjTypeId;
    }

    public void setAdjTypeId(String adjTypeId) {
        this.adjTypeId = adjTypeId;
    }

    public String getSelectAdjustCode() {
        return this.selectAdjustCode;
    }

    public void setSelectAdjustCode(String selectAdjustCode) {
        this.selectAdjustCode = selectAdjustCode;
    }

    public String getFormulaSchemeKey() {
        return this.formulaSchemeKey;
    }

    public void setFormulaSchemeKey(String formulaSchemeKey) {
        this.formulaSchemeKey = formulaSchemeKey;
    }

    public Map<String, DimensionValue> getDimensionSet() {
        return this.dimensionSet;
    }

    public void setDimensionSet(Map<String, DimensionValue> dimensionSet) {
        this.dimensionSet = dimensionSet;
    }

    public JtableContext getJtableContext() {
        return this.jtableContext;
    }

    public void setJtableContext(JtableContext jtableContext) {
        this.jtableContext = jtableContext;
    }
}

