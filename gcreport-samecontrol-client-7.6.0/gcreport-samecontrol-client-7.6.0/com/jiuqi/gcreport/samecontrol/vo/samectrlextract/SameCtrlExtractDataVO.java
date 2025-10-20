/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.samecontrol.vo.samectrlextract;

import com.jiuqi.gcreport.samecontrol.vo.SameCtrlChgOrgVO;

public class SameCtrlExtractDataVO {
    private String systemId;
    private String taskId;
    private String schemeId;
    private String orgType;
    private String periodStr;
    private String currencyId;
    private String adjTypeId;
    private SameCtrlChgOrgVO sameCtrlChgOrg;
    private String selectAdjustCode;
    private String sn;

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

    public SameCtrlChgOrgVO getSameCtrlChgOrg() {
        return this.sameCtrlChgOrg;
    }

    public void setSameCtrlChgOrg(SameCtrlChgOrgVO sameCtrlChgOrg) {
        this.sameCtrlChgOrg = sameCtrlChgOrg;
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

    public String getSn() {
        return this.sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }
}

