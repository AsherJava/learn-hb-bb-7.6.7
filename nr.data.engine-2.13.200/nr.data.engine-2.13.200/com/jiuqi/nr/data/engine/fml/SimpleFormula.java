/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.definitions.Formula
 */
package com.jiuqi.nr.data.engine.fml;

import com.jiuqi.np.dataengine.definitions.Formula;

public class SimpleFormula
extends Formula {
    private static final long serialVersionUID = -8921628472220484929L;
    private String reportName;
    private String formulaStr;
    protected int index;

    public SimpleFormula(String reportName, String formulaStr, int index) {
        this.reportName = reportName;
        this.formulaStr = formulaStr;
        this.index = index;
    }

    public String getId() {
        return "EXP" + this.index;
    }

    public String getCode() {
        return this.getId();
    }

    public String getFormula() {
        return this.formulaStr;
    }

    public String getOrder() {
        return this.getId();
    }

    public String getReportName() {
        return this.reportName;
    }

    public String getFormKey() {
        return this.getReportName();
    }

    public String toString() {
        return this.reportName + "[" + this.getCode() + "]:" + this.getFormula();
    }
}

