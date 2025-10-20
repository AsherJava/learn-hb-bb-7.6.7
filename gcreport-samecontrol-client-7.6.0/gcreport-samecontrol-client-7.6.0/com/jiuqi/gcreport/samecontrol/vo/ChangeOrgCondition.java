/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFormat
 */
package com.jiuqi.gcreport.samecontrol.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jiuqi.gcreport.samecontrol.vo.SameCtrlChgOrgVO;
import java.util.Date;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;

public class ChangeOrgCondition {
    private String taskId;
    private String schemeId;
    private String orgType;
    private String periodStr;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @JsonFormat(pattern="yyyy-MM-dd", timezone="GMT+8")
    private Date periodDate;
    private String orgCode;
    private String currencyId;
    private String selectAdjustCode;
    private Boolean allChildren;
    private Boolean extract;
    private Boolean administrator = false;
    private SameCtrlChgOrgVO sameCtrlChgOrgVO;
    private List<SameCtrlChgOrgVO> sameCtrlChgOrgVOList;

    public Date getPeriodDate() {
        return this.periodDate;
    }

    public void setPeriodDate(Date periodDate) {
        this.periodDate = periodDate;
    }

    public List<SameCtrlChgOrgVO> getSameCtrlChgOrgVOList() {
        return this.sameCtrlChgOrgVOList;
    }

    public void setSameCtrlChgOrgVOList(List<SameCtrlChgOrgVO> sameCtrlChgOrgVOList) {
        this.sameCtrlChgOrgVOList = sameCtrlChgOrgVOList;
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

    public String getOrgCode() {
        return this.orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public Boolean getAllChildren() {
        return this.allChildren;
    }

    public void setAllChildren(Boolean allChildren) {
        this.allChildren = allChildren;
    }

    public Boolean getAdministrator() {
        return this.administrator;
    }

    public void setAdministrator(Boolean administrator) {
        this.administrator = administrator;
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

    public String getCurrencyId() {
        return this.currencyId;
    }

    public void setCurrencyId(String currencyId) {
        this.currencyId = currencyId;
    }

    public SameCtrlChgOrgVO getSameCtrlChgOrgVO() {
        return this.sameCtrlChgOrgVO;
    }

    public void setSameCtrlChgOrgVO(SameCtrlChgOrgVO sameCtrlChgOrgVO) {
        this.sameCtrlChgOrgVO = sameCtrlChgOrgVO;
    }

    public Boolean getExtract() {
        return this.extract;
    }

    public void setExtract(Boolean extract) {
        this.extract = extract;
    }

    public String getSelectAdjustCode() {
        return this.selectAdjustCode;
    }

    public void setSelectAdjustCode(String selectAdjustCode) {
        this.selectAdjustCode = selectAdjustCode;
    }
}

