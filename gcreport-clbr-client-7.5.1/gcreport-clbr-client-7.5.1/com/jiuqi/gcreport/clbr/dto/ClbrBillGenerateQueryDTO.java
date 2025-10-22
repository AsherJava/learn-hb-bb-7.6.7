/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.clbr.dto;

import java.util.List;
import java.util.Set;

public class ClbrBillGenerateQueryDTO {
    private String clbrType;
    private Set<String> relations;
    private Set<String> oppRelations;
    private boolean queryConfirmFlag;
    private Integer pageNum;
    private Integer pageSize;
    private List<String> userCodes;
    private String clbrBillCode;
    private String clbrCode;
    private Double amountMin;
    private Double amountMax;
    private String remark;

    public String getClbrBillCode() {
        return this.clbrBillCode;
    }

    public void setClbrBillCode(String clbrBillCode) {
        this.clbrBillCode = clbrBillCode;
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

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getClbrType() {
        return this.clbrType;
    }

    public void setClbrType(String clbrType) {
        this.clbrType = clbrType;
    }

    public Set<String> getRelations() {
        return this.relations;
    }

    public void setRelations(Set<String> relations) {
        this.relations = relations;
    }

    public Set<String> getOppRelations() {
        return this.oppRelations;
    }

    public void setOppRelations(Set<String> oppRelations) {
        this.oppRelations = oppRelations;
    }

    public boolean isQueryConfirmFlag() {
        return this.queryConfirmFlag;
    }

    public void setQueryConfirmFlag(boolean queryConfirmFlag) {
        this.queryConfirmFlag = queryConfirmFlag;
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

    public List<String> getUserCodes() {
        return this.userCodes;
    }

    public void setUserCodes(List<String> userCodes) {
        this.userCodes = userCodes;
    }
}

