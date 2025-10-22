/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.form.analysis.dto;

import com.jiuqi.nr.form.analysis.dto.CaliberParamDTO;
import com.jiuqi.nr.form.analysis.dto.FloatRegionParamDTO;
import java.util.List;

public class AnalysisFormParamDTO {
    private String formKey;
    private String formCode;
    private String condition;
    private List<CaliberParamDTO> colCalibers;
    private List<CaliberParamDTO> rowCalibers;
    private List<FloatRegionParamDTO> floatRegionSettings;

    public String getFormKey() {
        return this.formKey;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }

    public String getCondition() {
        return this.condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public List<CaliberParamDTO> getColCalibers() {
        return this.colCalibers;
    }

    public void setColCalibers(List<CaliberParamDTO> colCalibers) {
        this.colCalibers = colCalibers;
    }

    public List<CaliberParamDTO> getRowCalibers() {
        return this.rowCalibers;
    }

    public void setRowCalibers(List<CaliberParamDTO> rowCalibers) {
        this.rowCalibers = rowCalibers;
    }

    public List<FloatRegionParamDTO> getFloatRegionSettings() {
        return this.floatRegionSettings;
    }

    public void setFloatRegionSettings(List<FloatRegionParamDTO> floatRegionSettings) {
        this.floatRegionSettings = floatRegionSettings;
    }

    public String getFormCode() {
        return this.formCode;
    }

    public void setFormCode(String formCode) {
        this.formCode = formCode;
    }
}

