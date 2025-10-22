/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 */
package com.jiuqi.nr.finalaccountsaudit.multcheck.common;

import com.jiuqi.nr.jtable.params.base.JtableContext;

public class MultCheckResultItem {
    private JtableContext context;
    private String asyncTask;
    private String checkItemAsyncTask;
    private String name;
    private String key;
    private String dimStr;
    private String checkType;
    private String checkResult;
    private String checkDetail;
    private String operator;
    private String updateTime;
    private String checkParams;
    private String checkStatus;
    private String multCheckItemKey;

    public String getOperator() {
        return this.operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getDimStr() {
        return this.dimStr;
    }

    public void setDimStr(String dimStr) {
        this.dimStr = dimStr;
    }

    public String getCheckType() {
        return this.checkType;
    }

    public void setCheckType(String checkType) {
        this.checkType = checkType;
    }

    public String getCheckResult() {
        return this.checkResult;
    }

    public void setCheckResult(String checkResult) {
        this.checkResult = checkResult;
    }

    public String getCheckDetail() {
        return this.checkDetail;
    }

    public void setCheckDetail(String checkDetail) {
        this.checkDetail = checkDetail;
    }

    public String getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public JtableContext getContext() {
        return this.context;
    }

    public void setContext(JtableContext context) {
        this.context = context;
    }

    public String getMuleCheckItemKey() {
        return this.multCheckItemKey;
    }

    public void setMuleCheckItemKey(String muleCheckItemKey) {
        this.multCheckItemKey = muleCheckItemKey;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCheckParams() {
        return this.checkParams;
    }

    public void setCheckParams(String checkParams) {
        this.checkParams = checkParams;
    }

    public String getAsyncTask() {
        return this.asyncTask;
    }

    public void setAsyncTask(String asyncTask) {
        this.asyncTask = asyncTask;
    }

    public String getCheckItemAsyncTask() {
        return this.checkItemAsyncTask;
    }

    public void setCheckItemAsyncTask(String checkItemAsyncTask) {
        this.checkItemAsyncTask = checkItemAsyncTask;
    }

    public String getCheckStatus() {
        return this.checkStatus;
    }

    public void setCheckStatus(String checkStatus) {
        this.checkStatus = checkStatus;
    }
}

