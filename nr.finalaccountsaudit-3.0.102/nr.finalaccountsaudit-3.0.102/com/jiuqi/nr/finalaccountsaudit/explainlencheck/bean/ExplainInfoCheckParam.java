/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 */
package com.jiuqi.nr.finalaccountsaudit.explainlencheck.bean;

import com.jiuqi.nr.jtable.params.base.JtableContext;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ExplainInfoCheckParam
implements Serializable {
    private static final long serialVersionUID = 1L;
    private JtableContext context;
    private List<String> unitKeys = new ArrayList<String>();
    private boolean onlyCurFormulaSheme = true;
    private String mainAsynTaskID;
    private String itemKey = "";
    private boolean impactReport;
    private String formulaSchemeKey;

    public String getItemKey() {
        return this.itemKey;
    }

    public void setItemKey(String itemKey) {
        this.itemKey = itemKey;
    }

    public String getMainAsynTaskID() {
        return this.mainAsynTaskID;
    }

    public void setMainAsynTaskID(String mainAsynTaskID) {
        this.mainAsynTaskID = mainAsynTaskID;
    }

    public JtableContext getContext() {
        return this.context;
    }

    public void setContext(JtableContext context) {
        this.context = context;
    }

    public boolean getOnlyCurFormulaSheme() {
        return this.onlyCurFormulaSheme;
    }

    public void setOnlyCurFormulaSheme(boolean onlyCurFormulaSheme) {
        this.onlyCurFormulaSheme = onlyCurFormulaSheme;
    }

    public List<String> getUnitKeys() {
        return this.unitKeys;
    }

    public void setUnitKeys(List<String> unitKeys) {
        this.unitKeys = unitKeys;
    }

    public boolean getImpactReport() {
        return this.impactReport;
    }

    public void setImpactReport(boolean impactReport) {
        this.impactReport = impactReport;
    }

    public String getFormulaSchemeKey() {
        return this.formulaSchemeKey;
    }

    public void setFormulaSchemeKey(String formulaSchemeKey) {
        this.formulaSchemeKey = formulaSchemeKey;
    }
}

