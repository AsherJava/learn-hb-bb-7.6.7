/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.finalaccountsaudit.enumcheck.common;

import java.util.Date;

public class EnumCheckResultDefine {
    private String key;
    private String groupKey;
    private String asyncTaskId;
    private Integer viewType;
    private Integer unitOrder;
    private String groupDetail;
    private String operator;
    private Date updateDate;

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getGroupKey() {
        return this.groupKey;
    }

    public void setGroupKey(String groupKey) {
        this.groupKey = groupKey;
    }

    public String getAsyncTaskId() {
        return this.asyncTaskId;
    }

    public void setAsyncTaskId(String asyncTaskId) {
        this.asyncTaskId = asyncTaskId;
    }

    public Integer getViewType() {
        return this.viewType;
    }

    public void setViewType(Integer viewType) {
        this.viewType = viewType;
    }

    public Integer getUnitOrder() {
        return this.unitOrder;
    }

    public void setUnitOrder(Integer unitOrder) {
        this.unitOrder = unitOrder;
    }

    public String getGroupDetail() {
        return this.groupDetail;
    }

    public void setGroupDetail(String groupDetail) {
        this.groupDetail = groupDetail;
    }

    public String getOperator() {
        return this.operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public Date getUpdateDate() {
        return this.updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
}

