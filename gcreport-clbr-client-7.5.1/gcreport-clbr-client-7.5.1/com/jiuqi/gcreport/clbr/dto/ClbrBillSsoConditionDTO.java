/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.clbr.dto;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class ClbrBillSsoConditionDTO {
    private String sysCode;
    private String userName;
    private String roleCode;
    private String relation;
    private String conditionRelation;
    private String conditionOppRelation;
    private String conditionClbrType;
    private String clbrCode;
    private Double amountMin;
    private Double amountMax;
    private String remark;
    private String arbitrationClbrBillCode;
    private Integer billState;
    private Integer pageNum;
    private Integer pageSize;
    private List<String> userCodes;
    private List<String> confirmUserCodes;
    private String clbrBillCode;
    private Map<String, Set<String>> receiveClbrType2Relations;
    private String mode;
    private List<String> sysRelation;
    private List<String> sysClbrType;

    public String getClbrCode() {
        return this.clbrCode;
    }

    public void setClbrCode(String clbrCode) {
        this.clbrCode = clbrCode;
    }

    public Double getAmountMin() {
        return this.amountMin;
    }

    public void setAmountMin(Double amountMin) {
        this.amountMin = amountMin;
    }

    public Double getAmountMax() {
        return this.amountMax;
    }

    public void setAmountMax(Double amountMax) {
        this.amountMax = amountMax;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getArbitrationClbrBillCode() {
        return this.arbitrationClbrBillCode;
    }

    public void setArbitrationClbrBillCode(String arbitrationClbrBillCode) {
        this.arbitrationClbrBillCode = arbitrationClbrBillCode;
    }

    public String getSysCode() {
        return this.sysCode;
    }

    public void setSysCode(String sysCode) {
        this.sysCode = sysCode;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRoleCode() {
        return this.roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public String getRelation() {
        return this.relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
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

    public String getConditionOppRelation() {
        return this.conditionOppRelation;
    }

    public void setConditionOppRelation(String conditionOppRelation) {
        this.conditionOppRelation = conditionOppRelation;
    }

    public String getConditionClbrType() {
        return this.conditionClbrType;
    }

    public void setConditionClbrType(String conditionClbrType) {
        this.conditionClbrType = conditionClbrType;
    }

    public String getConditionRelation() {
        return this.conditionRelation;
    }

    public void setConditionRelation(String conditionRelation) {
        this.conditionRelation = conditionRelation;
    }

    public List<String> getUserCodes() {
        return this.userCodes;
    }

    public void setUserCodes(List<String> userCodes) {
        this.userCodes = userCodes;
    }

    public String getClbrBillCode() {
        return this.clbrBillCode;
    }

    public void setClbrBillCode(String clbrBillCode) {
        this.clbrBillCode = clbrBillCode;
    }

    public Map<String, Set<String>> getReceiveClbrType2Relations() {
        return this.receiveClbrType2Relations;
    }

    public void setReceiveClbrType2Relations(Map<String, Set<String>> receiveClbrType2Relations) {
        this.receiveClbrType2Relations = receiveClbrType2Relations;
    }

    public String getMode() {
        return this.mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public List<String> getConfirmUserCodes() {
        return this.confirmUserCodes;
    }

    public void setConfirmUserCodes(List<String> confirmUserCodes) {
        this.confirmUserCodes = confirmUserCodes;
    }

    public Integer getBillState() {
        return this.billState;
    }

    public void setBillState(Integer billState) {
        this.billState = billState;
    }

    public List<String> getSysRelation() {
        return this.sysRelation;
    }

    public void setSysRelation(List<String> sysRelation) {
        this.sysRelation = sysRelation;
    }

    public List<String> getSysClbrType() {
        return this.sysClbrType;
    }

    public void setSysClbrType(List<String> sysClbrType) {
        this.sysClbrType = sysClbrType;
    }
}

