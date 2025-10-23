/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.task.form.dto;

import com.jiuqi.nr.task.form.dto.FormSyncFormParamsVO;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class FormSyncParamsVO
implements Serializable {
    private String srcFormSchemeKey;
    private String desFormSchemeKey;
    private Map<String, String> formulaAttMap;
    private Map<String, String> printAttMap;
    private List<FormSyncFormParamsVO> formParams;

    public String getSrcFormSchemeKey() {
        return this.srcFormSchemeKey;
    }

    public void setSrcFormSchemeKey(String srcFormSchemeKey) {
        this.srcFormSchemeKey = srcFormSchemeKey;
    }

    public String getDesFormSchemeKey() {
        return this.desFormSchemeKey;
    }

    public void setDesFormSchemeKey(String desFormSchemeKey) {
        this.desFormSchemeKey = desFormSchemeKey;
    }

    public List<FormSyncFormParamsVO> getFormParams() {
        return this.formParams;
    }

    public void setFormParams(List<FormSyncFormParamsVO> formParams) {
        this.formParams = formParams;
    }

    public Map<String, String> getPrintAttMap() {
        return this.printAttMap;
    }

    public void setPrintAttMap(Map<String, String> printAttMap) {
        this.printAttMap = printAttMap;
    }

    public Map<String, String> getFormulaAttMap() {
        return this.formulaAttMap;
    }

    public void setFormulaAttMap(Map<String, String> formulaAttMap) {
        this.formulaAttMap = formulaAttMap;
    }
}

