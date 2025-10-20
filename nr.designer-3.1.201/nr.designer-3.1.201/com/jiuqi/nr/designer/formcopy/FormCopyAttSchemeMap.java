/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnoreProperties
 */
package com.jiuqi.nr.designer.formcopy;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.HashMap;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown=true)
public class FormCopyAttSchemeMap {
    protected Map<String, String> formulaSchemeMap = new HashMap<String, String>();
    protected Map<String, String> fiFormulaSchemeMap = new HashMap<String, String>();
    protected Map<String, String> printSchemeMap = new HashMap<String, String>();

    public Map<String, String> getFormulaSchemeMap() {
        return this.formulaSchemeMap;
    }

    public void setFormulaSchemeMap(Map<String, String> formulaSchemeMap) {
        this.formulaSchemeMap = formulaSchemeMap;
    }

    public Map<String, String> getFiFormulaSchemeMap() {
        return this.fiFormulaSchemeMap;
    }

    public void setFiFormulaSchemeMap(Map<String, String> fiFormulaSchemeMap) {
        this.fiFormulaSchemeMap = fiFormulaSchemeMap;
    }

    public Map<String, String> getPrintSchemeMap() {
        return this.printSchemeMap;
    }

    public void setPrintSchemeMap(Map<String, String> printSchemeMap) {
        this.printSchemeMap = printSchemeMap;
    }
}

