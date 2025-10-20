/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.workflow.domain.plus.approval;

import java.math.BigDecimal;
import java.util.Date;

public class WithdrawPlusApprovalVO {
    private String bizCode;
    private String bizDefine;
    private String bizDefineName;
    private String plusApprovalUserId;
    private String plusApprovalUserName;
    private Date plusApprovalTime;
    private String staffName;
    private String positionName;
    private String processId;
    private String nodeId;
    private String nodeCode;
    private BigDecimal counterSignFlag;
    private String approvalUserUnitCode;

    public String getBizCode() {
        return this.bizCode;
    }

    public void setBizCode(String bizCode) {
        this.bizCode = bizCode;
    }

    public String getBizDefine() {
        return this.bizDefine;
    }

    public void setBizDefine(String bizDefine) {
        this.bizDefine = bizDefine;
    }

    public String getBizDefineName() {
        return this.bizDefineName;
    }

    public void setBizDefineName(String bizDefineName) {
        this.bizDefineName = bizDefineName;
    }

    public String getPlusApprovalUserId() {
        return this.plusApprovalUserId;
    }

    public void setPlusApprovalUserId(String plusApprovalUserId) {
        this.plusApprovalUserId = plusApprovalUserId;
    }

    public String getPlusApprovalUserName() {
        return this.plusApprovalUserName;
    }

    public void setPlusApprovalUserName(String plusApprovalUserName) {
        this.plusApprovalUserName = plusApprovalUserName;
    }

    public Date getPlusApprovalTime() {
        return this.plusApprovalTime;
    }

    public void setPlusApprovalTime(Date plusApprovalTime) {
        this.plusApprovalTime = plusApprovalTime;
    }

    public String getStaffName() {
        return this.staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getPositionName() {
        return this.positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public String getProcessId() {
        return this.processId;
    }

    public void setProcessId(String processId) {
        this.processId = processId;
    }

    public String getNodeId() {
        return this.nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getNodeCode() {
        return this.nodeCode;
    }

    public void setNodeCode(String nodeCode) {
        this.nodeCode = nodeCode;
    }

    public BigDecimal getCounterSignFlag() {
        return this.counterSignFlag;
    }

    public void setCounterSignFlag(BigDecimal counterSignFlag) {
        this.counterSignFlag = counterSignFlag;
    }

    public String getApprovalUserUnitCode() {
        return this.approvalUserUnitCode;
    }

    public void setApprovalUserUnitCode(String approvalUserUnitCode) {
        this.approvalUserUnitCode = approvalUserUnitCode;
    }
}

