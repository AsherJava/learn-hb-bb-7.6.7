/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.mapper.domain.TenantDO
 */
package com.jiuqi.gcreport.clbr.dto;

import com.jiuqi.va.mapper.domain.TenantDO;
import java.util.Map;

public class ClbrBillPushItemParamDTO
extends TenantDO {
    private String operate;
    private Integer clbrBillType;
    private String srcId;
    private String userName;
    private String userTitle;
    private String relation;
    private String clbrType;
    private String clbrBillCode;
    private String currency = "CNY";
    private Double amount;
    private String oppRelation;
    private String oppClbrBillCode;
    private String oppSrcId;
    private String orgCode;
    private String oppOrgCode;
    private String nextUserName;
    private String oppUserName;
    private String sn;
    private String sysCode;
    private Map<String, Object> extendInfo;
    private String groupId;
    private String clbrCode;
    private String arbitrationUserName;

    public String getArbitrationUserName() {
        return this.arbitrationUserName;
    }

    public void setArbitrationUserName(String arbitrationUserName) {
        this.arbitrationUserName = arbitrationUserName;
    }

    public String getGroupId() {
        return this.groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getClbrCode() {
        return this.clbrCode;
    }

    public void setClbrCode(String clbrCode) {
        this.clbrCode = clbrCode;
    }

    public String getSrcId() {
        return this.srcId;
    }

    public void setSrcId(String srcId) {
        this.srcId = srcId;
    }

    public Integer getClbrBillType() {
        return this.clbrBillType;
    }

    public void setClbrBillType(Integer clbrBillType) {
        this.clbrBillType = clbrBillType;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserTitle() {
        return this.userTitle;
    }

    public void setUserTitle(String userTitle) {
        this.userTitle = userTitle;
    }

    public String getRelation() {
        return this.relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public String getClbrType() {
        return this.clbrType;
    }

    public void setClbrType(String clbrType) {
        this.clbrType = clbrType;
    }

    public String getClbrBillCode() {
        return this.clbrBillCode;
    }

    public void setClbrBillCode(String clbrBillCode) {
        this.clbrBillCode = clbrBillCode;
    }

    public String getCurrency() {
        return this.currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Double getAmount() {
        return this.amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getOppRelation() {
        return this.oppRelation;
    }

    public void setOppRelation(String oppRelation) {
        this.oppRelation = oppRelation;
    }

    public String getOppClbrBillCode() {
        return this.oppClbrBillCode;
    }

    public void setOppClbrBillCode(String oppClbrBillCode) {
        this.oppClbrBillCode = oppClbrBillCode;
    }

    public String getNextUserName() {
        return this.nextUserName;
    }

    public void setNextUserName(String nextUserName) {
        this.nextUserName = nextUserName;
    }

    public String getSn() {
        return this.sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getSysCode() {
        return this.sysCode;
    }

    public void setSysCode(String sysCode) {
        this.sysCode = sysCode;
    }

    public String getOperate() {
        return this.operate;
    }

    public void setOperate(String operate) {
        this.operate = operate;
    }

    public String getOppSrcId() {
        return this.oppSrcId;
    }

    public void setOppSrcId(String oppSrcId) {
        this.oppSrcId = oppSrcId;
    }

    public String getOrgCode() {
        return this.orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getOppOrgCode() {
        return this.oppOrgCode;
    }

    public void setOppOrgCode(String oppOrgCode) {
        this.oppOrgCode = oppOrgCode;
    }

    public String getOppUserName() {
        return this.oppUserName;
    }

    public void setOppUserName(String oppUserName) {
        this.oppUserName = oppUserName;
    }

    public Map<String, Object> getExtendInfo() {
        return this.extendInfo;
    }

    public void setExtendInfo(Map<String, Object> extendInfo) {
        this.extendInfo = extendInfo;
    }
}

