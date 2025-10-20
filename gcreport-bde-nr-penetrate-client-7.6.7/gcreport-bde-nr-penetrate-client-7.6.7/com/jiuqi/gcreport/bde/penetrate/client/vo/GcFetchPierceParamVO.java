/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.dto.FloatRegionConfigVO
 */
package com.jiuqi.gcreport.bde.penetrate.client.vo;

import com.jiuqi.bde.common.dto.FloatRegionConfigVO;
import com.jiuqi.gcreport.bde.penetrate.client.vo.NrLinkInfoVO;
import java.util.List;
import java.util.Map;

public class GcFetchPierceParamVO {
    private String bblx;
    private String prodLine;
    private String formPeriod;
    private String fetchSchemeId;
    private String taskId;
    private String formId;
    private String taskTitle;
    private String periodStr;
    private int periodType;
    private String orgType;
    private String currency;
    private String orgId;
    private String regionId;
    private int acctYear;
    private int acctPeriod;
    private List<NrLinkInfoVO> linkInfos;
    private List<Map<String, Object>> rowDataZbId2valueMapList;
    private String selectedId;
    private FloatRegionConfigVO floatSetting;

    public String getProdLine() {
        return this.prodLine;
    }

    public void setProdLine(String prodLine) {
        this.prodLine = prodLine;
    }

    public String getFormPeriod() {
        return this.formPeriod;
    }

    public void setFormPeriod(String formPeriod) {
        this.formPeriod = formPeriod;
    }

    public String getFetchSchemeId() {
        return this.fetchSchemeId;
    }

    public void setFetchSchemeId(String fetchSchemeId) {
        this.fetchSchemeId = fetchSchemeId;
    }

    public String getTaskId() {
        return this.taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getBblx() {
        return this.bblx;
    }

    public void setBblx(String bblx) {
        this.bblx = bblx;
    }

    public String getPeriodStr() {
        return this.periodStr;
    }

    public void setPeriodStr(String periodStr) {
        this.periodStr = periodStr;
    }

    public int getPeriodType() {
        return this.periodType;
    }

    public void setPeriodType(int periodType) {
        this.periodType = periodType;
    }

    public String getOrgType() {
        return this.orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }

    public String getCurrency() {
        return this.currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getOrgId() {
        return this.orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public int getAcctYear() {
        return this.acctYear;
    }

    public void setAcctYear(int acctYear) {
        this.acctYear = acctYear;
    }

    public int getAcctPeriod() {
        return this.acctPeriod;
    }

    public void setAcctPeriod(int acctPeriod) {
        this.acctPeriod = acctPeriod;
    }

    public List<NrLinkInfoVO> getLinkInfos() {
        return this.linkInfos;
    }

    public void setLinkInfos(List<NrLinkInfoVO> linkInfos) {
        this.linkInfos = linkInfos;
    }

    public String getTaskTitle() {
        return this.taskTitle;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    public List<Map<String, Object>> getRowDataZbId2valueMapList() {
        return this.rowDataZbId2valueMapList;
    }

    public void setRowDataZbId2valueMapList(List<Map<String, Object>> rowDataZbId2valueMapList) {
        this.rowDataZbId2valueMapList = rowDataZbId2valueMapList;
    }

    public String getRegionId() {
        return this.regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    public String getSelectedId() {
        return this.selectedId;
    }

    public void setSelectedId(String selectedId) {
        this.selectedId = selectedId;
    }

    public String getFormId() {
        return this.formId;
    }

    public void setFormId(String formId) {
        this.formId = formId;
    }

    public FloatRegionConfigVO getFloatSetting() {
        return this.floatSetting;
    }

    public void setFloatSetting(FloatRegionConfigVO floatSetting) {
        this.floatSetting = floatSetting;
    }
}

