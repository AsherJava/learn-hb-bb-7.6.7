/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.validation.constraints.Min
 *  javax.validation.constraints.NotNull
 */
package com.jiuqi.gcreport.clbr.vo;

import java.util.List;
import java.util.Map;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class ClbrDataQueryConditon {
    @NotNull(message="\u67e5\u8be2\u660e\u7ec6\u7c7b\u578b\u4e0d\u80fd\u4e3a\u7a7a")
    private @NotNull(message="\u67e5\u8be2\u660e\u7ec6\u7c7b\u578b\u4e0d\u80fd\u4e3a\u7a7a") String queryDataType;
    private String relation;
    @NotNull(message="\u67e5\u8be2\u4e0d\u80fd\u4e3a\u7a7a")
    private @NotNull(message="\u67e5\u8be2\u4e0d\u80fd\u4e3a\u7a7a") Integer clbrBillType;
    @Min(value=1L, message="\u6bcf\u9875\u6570\u4e0d\u80fd\u5c0f\u4e8e1")
    private @Min(value=1L, message="\u6bcf\u9875\u6570\u4e0d\u80fd\u5c0f\u4e8e1") int pageSize = 50;
    @Min(value=1L, message="\u9875\u7801\u53c2\u6570\u4e0d\u6b63\u786e")
    private @Min(value=1L, message="\u9875\u7801\u53c2\u6570\u4e0d\u6b63\u786e") int pageNum = 1;
    private boolean orgParentsFlag;
    private boolean pageSelect = true;
    private String initiatorRelation;
    private String receiverRelation;
    private String filterRelation;
    private String filterOppRelation;
    private String clbrCode;
    private String clbrBillCode;
    private String clbrType;
    private Double amountMin;
    private Double amountMax;
    private Double verifyedAmountMin;
    private Double verifyedAmountMax;
    private Double noverifyAmountMin;
    private Double noverifyAmountMax;
    private Map<String, Object> otherColumnsCondition;
    private List<String> userCodes;

    public String getQueryDataType() {
        return this.queryDataType;
    }

    public void setQueryDataType(String queryDataType) {
        this.queryDataType = queryDataType;
    }

    public String getRelation() {
        return this.relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public Integer getClbrBillType() {
        return this.clbrBillType;
    }

    public void setClbrBillType(Integer clbrBillType) {
        this.clbrBillType = clbrBillType;
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

    public boolean isPageSelect() {
        return this.pageSelect;
    }

    public void setPageSelect(boolean pageSelect) {
        this.pageSelect = pageSelect;
    }

    public boolean isOrgParentsFlag() {
        return this.orgParentsFlag;
    }

    public void setOrgParentsFlag(boolean orgParentsFlag) {
        this.orgParentsFlag = orgParentsFlag;
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

    public String getFilterRelation() {
        return this.filterRelation;
    }

    public void setFilterRelation(String filterRelation) {
        this.filterRelation = filterRelation;
    }

    public String getFilterOppRelation() {
        return this.filterOppRelation;
    }

    public void setFilterOppRelation(String filterOppRelation) {
        this.filterOppRelation = filterOppRelation;
    }

    public String getClbrCode() {
        return this.clbrCode;
    }

    public void setClbrCode(String clbrCode) {
        this.clbrCode = clbrCode;
    }

    public String getClbrType() {
        return this.clbrType;
    }

    public void setClbrType(String clbrType) {
        this.clbrType = clbrType;
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

    public String getClbrBillCode() {
        return this.clbrBillCode;
    }

    public void setClbrBillCode(String clbrBillCode) {
        this.clbrBillCode = clbrBillCode;
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
}

