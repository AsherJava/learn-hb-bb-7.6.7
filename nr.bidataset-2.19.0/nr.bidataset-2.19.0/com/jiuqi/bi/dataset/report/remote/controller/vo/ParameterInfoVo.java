/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.dataset.report.remote.controller.vo;

import com.jiuqi.bi.dataset.report.model.ReportDsParameter;

public class ParameterInfoVo {
    private ReportDsParameter parameter;
    private String taskId;
    private String summarySchemeCode;
    private boolean useMaxValue;

    public ReportDsParameter getParameter() {
        return this.parameter;
    }

    public void setParameter(ReportDsParameter parameter) {
        this.parameter = parameter;
    }

    public String getTaskId() {
        return this.taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getSummarySchemeCode() {
        return this.summarySchemeCode;
    }

    public void setSummarySchemeCode(String summarySchemeCode) {
        this.summarySchemeCode = summarySchemeCode;
    }

    public boolean isUseMaxValue() {
        return this.useMaxValue;
    }

    public void setUseMaxValue(boolean useMaxValue) {
        this.useMaxValue = useMaxValue;
    }
}

