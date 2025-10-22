/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFormat
 *  com.jiuqi.common.base.util.JsonUtils
 */
package com.jiuqi.gcreport.samecontrol.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jiuqi.common.base.util.JsonUtils;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

public class SameCtrlChgOrgVO {
    private String id;
    private String changedCode;
    private String changedName;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @JsonFormat(pattern="yyyy-MM-dd", timezone="GMT+8")
    private Date changeDate;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @JsonFormat(pattern="yyyy-MM-dd", timezone="GMT+8")
    private Date disposalDate;
    private String virtualCode;
    private String virtualName;
    private String virtualParentCode;
    private String virtualParentName;
    private Boolean virtualParentAuth = true;
    private String changedParentCode;
    private String changedParentName;
    private Boolean changedParentAuth = true;
    private String sameParentCode;
    private String sameParentName;
    private Boolean sameParentAuth = true;
    private Integer changedOrgTypeCode;
    private String changedOrgTypeName;
    private Boolean deleteFlag;
    private String logState;
    private String mRecid;
    private String correspondVirtualCode;
    private String correspondVirtualName;
    private String virtualCodeType;
    private String virtualCodeTypeName;
    private Integer disableFlag;

    public Integer getDisableFlag() {
        return this.disableFlag;
    }

    public void setDisableFlag(Integer disableFlag) {
        this.disableFlag = disableFlag;
    }

    public String getCorrespondVirtualName() {
        return this.correspondVirtualName;
    }

    public void setCorrespondVirtualName(String correspondVirtualName) {
        this.correspondVirtualName = correspondVirtualName;
    }

    public String getVirtualName() {
        return this.virtualName;
    }

    public void setVirtualName(String virtualName) {
        this.virtualName = virtualName;
    }

    public String getVirtualCodeTypeName() {
        return this.virtualCodeTypeName;
    }

    public void setVirtualCodeTypeName(String virtualCodeTypeName) {
        this.virtualCodeTypeName = virtualCodeTypeName;
    }

    public String getmRecid() {
        return this.mRecid;
    }

    public void setmRecid(String mRecid) {
        this.mRecid = mRecid;
    }

    public String getCorrespondVirtualCode() {
        return this.correspondVirtualCode;
    }

    public void setCorrespondVirtualCode(String correspondVirtualCode) {
        this.correspondVirtualCode = correspondVirtualCode;
    }

    public String getVirtualCodeType() {
        return this.virtualCodeType;
    }

    public void setVirtualCodeType(String virtualCodeType) {
        this.virtualCodeType = virtualCodeType;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getVirtualCode() {
        return this.virtualCode;
    }

    public void setVirtualCode(String virtualCode) {
        this.virtualCode = virtualCode;
    }

    public String getVirtualParentCode() {
        return this.virtualParentCode;
    }

    public void setVirtualParentCode(String virtualParentCode) {
        this.virtualParentCode = virtualParentCode;
    }

    public String getVirtualParentName() {
        return this.virtualParentName;
    }

    public void setVirtualParentName(String virtualParentName) {
        this.virtualParentName = virtualParentName;
    }

    public String getChangedParentCode() {
        return this.changedParentCode;
    }

    public void setChangedParentCode(String changedParentCode) {
        this.changedParentCode = changedParentCode;
    }

    public String getChangedParentName() {
        return this.changedParentName;
    }

    public void setChangedParentName(String changedParentName) {
        this.changedParentName = changedParentName;
    }

    public String getSameParentCode() {
        return this.sameParentCode;
    }

    public void setSameParentCode(String sameParentCode) {
        this.sameParentCode = sameParentCode;
    }

    public String getSameParentName() {
        return this.sameParentName;
    }

    public void setSameParentName(String sameParentName) {
        this.sameParentName = sameParentName;
    }

    public Boolean getVirtualParentAuth() {
        return this.virtualParentAuth;
    }

    public void setVirtualParentAuth(Boolean virtualParentAuth) {
        this.virtualParentAuth = virtualParentAuth;
    }

    public Boolean getChangedParentAuth() {
        return this.changedParentAuth;
    }

    public void setChangedParentAuth(Boolean changedParentAuth) {
        this.changedParentAuth = changedParentAuth;
    }

    public Boolean getSameParentAuth() {
        return this.sameParentAuth;
    }

    public void setSameParentAuth(Boolean sameParentAuth) {
        this.sameParentAuth = sameParentAuth;
    }

    public Boolean getDeleteFlag() {
        return this.deleteFlag;
    }

    public void setDeleteFlag(Boolean deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public String getLogState() {
        return this.logState;
    }

    public void setLogState(String logState) {
        this.logState = logState;
    }

    public Integer getChangedOrgTypeCode() {
        return this.changedOrgTypeCode;
    }

    public void setChangedOrgTypeCode(Integer changedOrgTypeCode) {
        this.changedOrgTypeCode = changedOrgTypeCode;
    }

    public String getChangedOrgTypeName() {
        return this.changedOrgTypeName;
    }

    public void setChangedOrgTypeName(String changedOrgTypeName) {
        this.changedOrgTypeName = changedOrgTypeName;
    }

    public String toString() {
        return "SameCtrlChgOrgVO{id='" + this.id + '\'' + ", changedCode='" + this.changedCode + '\'' + ", changedName='" + this.changedName + '\'' + ", changeDate=" + this.changeDate + ", disposalDate=" + this.disposalDate + ", virtualCode='" + this.virtualCode + '\'' + ", virtualParentCode='" + this.virtualParentCode + '\'' + ", virtualParentName='" + this.virtualParentName + '\'' + ", changedParentCode='" + this.changedParentCode + '\'' + ", changedParentName='" + this.changedParentName + '\'' + ", sameParentCode='" + this.sameParentCode + '\'' + ", sameParentName='" + this.sameParentName + '\'' + '}';
    }

    public static void main(String[] args) {
        SameCtrlChgOrgVO sameCtrlChgOrgVO = new SameCtrlChgOrgVO();
        sameCtrlChgOrgVO.setChangeDate(new Date());
        sameCtrlChgOrgVO.setDisposalDate(new Date());
        System.out.println(JsonUtils.writeValueAsString((Object)sameCtrlChgOrgVO));
    }
}

