/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datacheck.fmlcheck;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FmlCheckConfig
implements Serializable {
    private static final long serialVersionUID = 1340106802143985059L;
    private String executeId;
    private boolean beforeReport;
    private String formulaSchemeKey;
    private Map<String, List<String>> formulas;
    private boolean desCheckPass;
    private Map<Integer, Integer> checkRequires = new HashMap<Integer, Integer>();

    public String getExecuteId() {
        return this.executeId;
    }

    public void setExecuteId(String executeId) {
        this.executeId = executeId;
    }

    public String getFormulaSchemeKey() {
        return this.formulaSchemeKey;
    }

    public void setFormulaSchemeKey(String formulaSchemeKey) {
        this.formulaSchemeKey = formulaSchemeKey;
    }

    public Map<String, List<String>> getFormulas() {
        return this.formulas;
    }

    public void setFormulas(Map<String, List<String>> formulas) {
        this.formulas = formulas;
    }

    public boolean isDesCheckPass() {
        return this.desCheckPass;
    }

    public void setDesCheckPass(boolean desCheckPass) {
        this.desCheckPass = desCheckPass;
    }

    public Map<Integer, Integer> getCheckRequires() {
        return this.checkRequires;
    }

    public void setCheckRequires(Map<Integer, Integer> checkRequires) {
        this.checkRequires = checkRequires;
    }

    public boolean isBeforeReport() {
        return this.beforeReport;
    }

    public void setBeforeReport(boolean beforeReport) {
        this.beforeReport = beforeReport;
    }
}

