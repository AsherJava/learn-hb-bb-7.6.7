/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.bde.bill.setting.client.dto;

import java.util.List;
import java.util.Map;

public class FieldAdaptSettingDTO {
    private String adaptFormula;
    private String wildcardFormula;
    private String logicFormula;
    private String memo;
    private Map<String, List<Map<String, Object>>> bizModelFormula;

    public String getAdaptFormula() {
        return this.adaptFormula;
    }

    public void setAdaptFormula(String adaptFormula) {
        this.adaptFormula = adaptFormula;
    }

    public String getWildcardFormula() {
        return this.wildcardFormula;
    }

    public void setWildcardFormula(String wildcardFormula) {
        this.wildcardFormula = wildcardFormula;
    }

    public String getLogicFormula() {
        return this.logicFormula;
    }

    public void setLogicFormula(String logicFormula) {
        this.logicFormula = logicFormula;
    }

    public String getMemo() {
        return this.memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Map<String, List<Map<String, Object>>> getBizModelFormula() {
        return this.bizModelFormula;
    }

    public void setBizModelFormula(Map<String, List<Map<String, Object>>> bizModelFormula) {
        this.bizModelFormula = bizModelFormula;
    }

    public String toString() {
        return "FieldAdaptSettingDTO [adaptFormula=" + this.adaptFormula + ", wildcardFormula=" + this.wildcardFormula + ", logicFormula=" + this.logicFormula + ", memo=" + this.memo + ", bizModelFormula=" + this.bizModelFormula + "]";
    }
}

