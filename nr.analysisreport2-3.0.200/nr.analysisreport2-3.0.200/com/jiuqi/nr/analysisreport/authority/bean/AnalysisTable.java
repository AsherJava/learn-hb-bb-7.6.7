/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.analysisreport.authority.bean;

import com.jiuqi.nr.analysisreport.authority.bean.RequestTypeEnum;
import com.jiuqi.nr.analysisreport.biservice.chart.ChartTreeNode;

public class AnalysisTable
extends ChartTreeNode {
    private RequestTypeEnum requestTypeEnum;
    private String type;

    public RequestTypeEnum getRequestTypeEnum() {
        return this.requestTypeEnum;
    }

    public void setRequestTypeEnum(RequestTypeEnum requestTypeEnum) {
        this.requestTypeEnum = requestTypeEnum;
    }

    public AnalysisTable(String key, String title, String code, String type, RequestTypeEnum requestTypeEnum) {
        this.requestTypeEnum = requestTypeEnum;
        this.setKey(key);
        this.setTitle(title);
        this.setType(type);
        this.setCode(code);
    }

    public AnalysisTable() {
    }

    @Override
    public String getType() {
        return this.type;
    }

    @Override
    public void setType(String type) {
        this.type = type;
    }
}

