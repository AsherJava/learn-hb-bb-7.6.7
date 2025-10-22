/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.analysisreport.vo;

import java.util.Map;

public class ReportFormulaVo {
    String formula;
    Map<String, Object> ext;

    public String getFormula() {
        return this.formula;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }

    public Map<String, Object> getExt() {
        return this.ext;
    }

    public void setExt(Map<String, Object> ext) {
        this.ext = ext;
    }
}

