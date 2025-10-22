/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.security.inject.SqlInjection
 */
package com.jiuqi.gcreport.clbr.dto;

import com.jiuqi.common.base.security.inject.SqlInjection;
import java.util.List;

public class ClbrBillGenerateQueryParamDTO {
    @SqlInjection(message="\u5b58\u5728\u975e\u6cd5\u5b57\u7b26")
    private String clbrType;
    @SqlInjection(message="\u5b58\u5728\u975e\u6cd5\u5b57\u7b26")
    private String relation;
    @SqlInjection(message="\u5b58\u5728\u975e\u6cd5\u5b57\u7b26")
    private String oppRelation;
    private Integer pageNum;
    private Integer pageSize;
    @SqlInjection(message="\u5b58\u5728\u975e\u6cd5\u5b57\u7b26")
    private List<String> userCodes;
    private String mode;
    @SqlInjection(message="\u5b58\u5728\u975e\u6cd5\u5b57\u7b26")
    private List<String> confirmUserCodes;
    @SqlInjection(message="\u5b58\u5728\u975e\u6cd5\u5b57\u7b26")
    private String clbrBillCode;
    @SqlInjection(message="\u5b58\u5728\u975e\u6cd5\u5b57\u7b26")
    private String clbrCode;
    private Double amountMin;
    private Double amountMax;
    @SqlInjection(message="\u5b58\u5728\u975e\u6cd5\u5b57\u7b26")
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
}

