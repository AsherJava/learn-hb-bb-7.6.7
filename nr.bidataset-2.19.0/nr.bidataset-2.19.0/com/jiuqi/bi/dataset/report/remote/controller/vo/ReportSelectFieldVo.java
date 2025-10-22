/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.dataset.report.remote.controller.vo;

import com.jiuqi.bi.dataset.report.model.ReportDsParameter;
import com.jiuqi.bi.dataset.report.remote.controller.vo.SelectFieldVo;
import java.util.List;

public class ReportSelectFieldVo {
    private List<ReportDsParameter> dsParameters;
    private List<SelectFieldVo> selectFields;

    public List<ReportDsParameter> getDsParameters() {
        return this.dsParameters;
    }

    public void setDsParameters(List<ReportDsParameter> dsParameters) {
        this.dsParameters = dsParameters;
    }

    public List<SelectFieldVo> getSelectFields() {
        return this.selectFields;
    }

    public void setSelectFields(List<SelectFieldVo> selectFields) {
        this.selectFields = selectFields;
    }
}

