/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.dataset.report.remote.controller.vo;

import com.jiuqi.bi.dataset.report.model.ReportDsParameter;
import com.jiuqi.bi.dataset.report.remote.controller.vo.ExpFieldVo;
import java.util.List;

public class ReportExpFieldVo {
    private List<ReportDsParameter> dsParameters;
    private List<ExpFieldVo> expFieldVos;

    public List<ReportDsParameter> getDsParameters() {
        return this.dsParameters;
    }

    public void setDsParameters(List<ReportDsParameter> dsParameters) {
        this.dsParameters = dsParameters;
    }

    public List<ExpFieldVo> getExpFieldVos() {
        return this.expFieldVos;
    }

    public void setExpFieldVos(List<ExpFieldVo> expFieldVos) {
        this.expFieldVos = expFieldVos;
    }
}

