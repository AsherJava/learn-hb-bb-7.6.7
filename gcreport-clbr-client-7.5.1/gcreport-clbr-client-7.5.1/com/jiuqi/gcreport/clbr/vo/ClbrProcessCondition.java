/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFormat
 *  javax.validation.constraints.Min
 */
package com.jiuqi.gcreport.clbr.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jiuqi.gcreport.clbr.enums.ClbrTabEnum;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.validation.constraints.Min;
import org.springframework.format.annotation.DateTimeFormat;

public class ClbrProcessCondition {
    private String clbrBillCode;
    private String relation;
    private String oppRelation;
    private String initiatorRelation;
    private String receiverRelation;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @JsonFormat(pattern="yyyy-MM-dd", timezone="GMT+8")
    private Date startTime;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @JsonFormat(pattern="yyyy-MM-dd", timezone="GMT+8")
    private Date endTime;
    @Min(value=1L, message="\u6bcf\u9875\u6570\u4e0d\u80fd\u5c0f\u4e8e1")
    private @Min(value=1L, message="\u6bcf\u9875\u6570\u4e0d\u80fd\u5c0f\u4e8e1") int pageSize = 50;
    @Min(value=1L, message="\u9875\u7801\u53c2\u6570\u4e0d\u6b63\u786e")
    private @Min(value=1L, message="\u9875\u7801\u53c2\u6570\u4e0d\u6b63\u786e") int pageNum = 1;
    private String clbrCode;
    private String clbrType;
    private Double amountMin;
    private Double amountMax;
    private Double verifyedAmountMin;
    private Double verifyedAmountMax;
    private Double noverifyAmountMin;
    private Double noverifyAmountMax;
    private String sysCode;
    private String roleCode;
    private String userName;
    private String throughRelation;
    private Map<String, Set<String>> receiveClbrType2Relations;
    private ClbrTabEnum tabFlag;
    private Map<String, Object> otherColumnsCondition;
    private List<String> userCodes;
    private String mode;

    public String getClbrBillCode() {
        return this.clbrBillCode;
    }

    public void setClbrBillCode(String clbrBillCode) {
        this.clbrBillCode = clbrBillCode;
    }

    public String getRelation() {
        return this.relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public String getOppRelation() {
        return this.oppRelation;
    }

    public void setOppRelation(String oppRelation) {
        this.oppRelation = oppRelation;
    }

    public Date getStartTime() {
        return this.startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return this.endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public int getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNum() {
        return this.pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

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

    public Double getVerifyedAmountMin() {
        return this.verifyedAmountMin;
    }

    public void setVerifyedAmountMin(Double verifyedAmountMin) {
        this.verifyedAmountMin = verifyedAmountMin;
    }

    public Double getVerifyedAmountMax() {
        return this.verifyedAmountMax;
    }

    public void setVerifyedAmountMax(Double verifyedAmountMax) {
        this.verifyedAmountMax = verifyedAmountMax;
    }

    public Double getNoverifyAmountMin() {
        return this.noverifyAmountMin;
    }

    public void setNoverifyAmountMin(Double noverifyAmountMin) {
        this.noverifyAmountMin = noverifyAmountMin;
    }

    public Double getNoverifyAmountMax() {
        return this.noverifyAmountMax;
    }

    public void setNoverifyAmountMax(Double noverifyAmountMax) {
        this.noverifyAmountMax = noverifyAmountMax;
    }

    public String getClbrType() {
        return this.clbrType;
    }

    public void setClbrType(String clbrType) {
        this.clbrType = clbrType;
    }

    public String getSysCode() {
        return this.sysCode;
    }

    public void setSysCode(String sysCode) {
        this.sysCode = sysCode;
    }

    public String getRoleCode() {
        return this.roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getThroughRelation() {
        return this.throughRelation;
    }

    public void setThroughRelation(String throughRelation) {
        this.throughRelation = throughRelation;
    }

    public Map<String, Set<String>> getReceiveClbrType2Relations() {
        return this.receiveClbrType2Relations;
    }

    public void setReceiveClbrType2Relations(Map<String, Set<String>> receiveClbrType2Relations) {
        this.receiveClbrType2Relations = receiveClbrType2Relations;
    }

    public ClbrTabEnum getTabFlag() {
        return this.tabFlag;
    }

    public void setTabFlag(ClbrTabEnum tabFlag) {
        this.tabFlag = tabFlag;
    }

    public String getInitiatorRelation() {
        return this.initiatorRelation;
    }

    public void setInitiatorRelation(String initiatorRelation) {
        this.initiatorRelation = initiatorRelation;
    }

    public String getReceiverRelation() {
        return this.receiverRelation;
    }

    public void setReceiverRelation(String receiverRelation) {
        this.receiverRelation = receiverRelation;
    }

    public Map<String, Object> getOtherColumnsCondition() {
        return this.otherColumnsCondition;
    }

    public void setOtherColumnsCondition(Map<String, Object> otherColumnsCondition) {
        this.otherColumnsCondition = otherColumnsCondition;
    }

    public List<String> getUserCodes() {
        return this.userCodes;
    }

    public void setUserCodes(List<String> userCodes) {
        this.userCodes = userCodes;
    }

    public String getMode() {
        return this.mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }
}

