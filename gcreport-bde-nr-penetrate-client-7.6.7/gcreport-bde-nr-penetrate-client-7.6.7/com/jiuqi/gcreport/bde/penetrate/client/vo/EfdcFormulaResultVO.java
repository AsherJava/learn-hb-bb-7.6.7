/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.bde.penetrate.client.vo;

import java.util.List;
import java.util.Map;

public class EfdcFormulaResultVO {
    private String formula;
    private List<String> subjectCodes;
    private Map<String, List<String>> assistMap;

    public String getFormula() {
        return this.formula;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }

    public List<String> getSubjectCodes() {
        return this.subjectCodes;
    }

    public void setSubjectCodes(List<String> subjectCodes) {
        this.subjectCodes = subjectCodes;
    }

    public Map<String, List<String>> getAssistMap() {
        return this.assistMap;
    }

    public void setAssistMap(Map<String, List<String>> assistMap) {
        this.assistMap = assistMap;
    }
}

