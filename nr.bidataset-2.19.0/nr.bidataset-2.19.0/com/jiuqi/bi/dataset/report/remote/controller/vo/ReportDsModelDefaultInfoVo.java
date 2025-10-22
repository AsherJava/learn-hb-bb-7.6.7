/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.dataset.report.remote.controller.vo;

import com.jiuqi.bi.dataset.report.model.AdvancedConfig;
import com.jiuqi.bi.dataset.report.model.PeriodChangeMode;
import com.jiuqi.bi.dataset.report.model.ReportDsParameter;
import com.jiuqi.bi.dataset.report.model.ReportExpField;
import com.jiuqi.bi.dataset.report.model.UnitChangeMode;
import java.util.List;

public class ReportDsModelDefaultInfoVo {
    private List<ReportExpField> defaultFields;
    private List<ReportDsParameter> defaultParameters;
    private AdvancedConfig advancedConfig = new AdvancedConfig();

    public ReportDsModelDefaultInfoVo() {
        this.advancedConfig.setUnitChangeMode(UnitChangeMode.SELECTED);
        this.advancedConfig.setPeriodChangeMode(PeriodChangeMode.SELECTED);
    }

    public List<ReportExpField> getDefaultFields() {
        return this.defaultFields;
    }

    public void setDefaultFields(List<ReportExpField> defaultFields) {
        this.defaultFields = defaultFields;
    }

    public List<ReportDsParameter> getDefaultParameters() {
        return this.defaultParameters;
    }

    public void setDefaultParameters(List<ReportDsParameter> defaultParameters) {
        this.defaultParameters = defaultParameters;
    }

    public AdvancedConfig getAdvancedConfig() {
        return this.advancedConfig;
    }

    public void setAdvancedConfig(AdvancedConfig advancedConfig) {
        this.advancedConfig = advancedConfig;
    }
}

