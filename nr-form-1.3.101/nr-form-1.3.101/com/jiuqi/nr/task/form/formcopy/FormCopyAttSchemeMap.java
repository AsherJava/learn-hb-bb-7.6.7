/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.task.form.formcopy;

import java.util.HashMap;
import java.util.Map;

public class FormCopyAttSchemeMap {
    protected Map<String, String> formulaSchemeMap = new HashMap<String, String>();
    protected Map<String, String> printSchemeMap = new HashMap<String, String>();

    public Map<String, String> getFormulaSchemeMap() {
        return this.formulaSchemeMap;
    }

    public void setFormulaSchemeMap(Map<String, String> formulaSchemeMap) {
        this.formulaSchemeMap = formulaSchemeMap;
    }

    public Map<String, String> getPrintSchemeMap() {
        return this.printSchemeMap;
    }

    public void setPrintSchemeMap(Map<String, String> printSchemeMap) {
        this.printSchemeMap = printSchemeMap;
    }
}

