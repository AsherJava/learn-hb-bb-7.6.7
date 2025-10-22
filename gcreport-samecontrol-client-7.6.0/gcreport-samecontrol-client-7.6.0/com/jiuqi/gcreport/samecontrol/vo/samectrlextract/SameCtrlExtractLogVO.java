/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.samecontrol.vo.samectrlextract;

import com.jiuqi.gcreport.samecontrol.enums.SameCtrlExtractOperateEnum;
import com.jiuqi.gcreport.samecontrol.enums.SameCtrlExtractTaskStateEnum;

public class SameCtrlExtractLogVO {
    private String Id;
    private SameCtrlExtractOperateEnum operate;
    private String taskId;
    private String schemeId;
    private String periodStr;
    private String orgType;
    private String changedCode;
    private String virtualParentCode;
    private String changedParentCode;
    private Long beginTime;
    private Long endTime;
    private SameCtrlExtractTaskStateEnum taskState;
    private String userName;
    private Integer latestFlag;
    private String info;
    private String virtualOrgType;

    public String getVirtualOrgType() {
        return this.virtualOrgType;
    }

    public void setVirtualOrgType(String virtualOrgType) {
        this.virtualOrgType = virtualOrgType;
    }

    public String getId() {
        return this.Id;
    }

    public void setId(String id) {
        this.Id = id;
    }

    public SameCtrlExtractOperateEnum getOperate() {
        return this.operate;
    }

    public void setOperate(SameCtrlExtractOperateEnum operate) {
        this.operate = operate;
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

    public String getPeriodStr() {
        return this.periodStr;
    }

    public void setPeriodStr(String periodStr) {
        this.periodStr = periodStr;
    }

    public String getOrgType() {
        return this.orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }

    public String getChangedCode() {
        return this.changedCode;
    }

    public void setChangedCode(String changedCode) {
        this.changedCode = changedCode;
    }

    public String getVirtualParentCode() {
        return this.virtualParentCode;
    }

    public void setVirtualParentCode(String virtualParentCode) {
        this.virtualParentCode = virtualParentCode;
    }

    public String getChangedParentCode() {
        return this.changedParentCode;
    }

    public void setChangedParentCode(String changedParentCode) {
        this.changedParentCode = changedParentCode;
    }

    public Long getBeginTime() {
        return this.beginTime;
    }

    public void setBeginTime(Long beginTime) {
        this.beginTime = beginTime;
    }

    public Long getEndTime() {
        return this.endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public SameCtrlExtractTaskStateEnum getTaskState() {
        return this.taskState;
    }

    public void setTaskState(SameCtrlExtractTaskStateEnum taskState) {
        this.taskState = taskState;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getLatestFlag() {
        return this.latestFlag;
    }

    public void setLatestFlag(Integer latestFlag) {
        this.latestFlag = latestFlag;
    }

    public String getInfo() {
        return this.info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}

