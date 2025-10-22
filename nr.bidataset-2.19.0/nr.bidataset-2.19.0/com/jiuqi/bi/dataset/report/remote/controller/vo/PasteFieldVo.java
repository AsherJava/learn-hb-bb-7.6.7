/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.dataset.report.remote.controller.vo;

import com.jiuqi.bi.dataset.report.model.ReportDsParameter;
import com.jiuqi.bi.dataset.report.model.ReportExpField;
import java.util.List;

public class PasteFieldVo {
    private List<ReportDsParameter> dsParameters;
    private List<ReportExpField> fields;

    public List<ReportDsParameter> getDsParameters() {
        return this.dsParameters;
    }

    public void setDsParameters(List<ReportDsParameter> dsParameters) {
        this.dsParameters = dsParameters;
    }

    public List<ReportExpField> getFields() {
        return this.fields;
    }

    public void setFields(List<ReportExpField> fields) {
        this.fields = fields;
    }
}

