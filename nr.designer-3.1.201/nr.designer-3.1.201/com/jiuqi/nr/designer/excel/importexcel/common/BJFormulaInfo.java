/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.facade.DesignFormulaSchemeDefine
 */
package com.jiuqi.nr.designer.excel.importexcel.common;

import com.jiuqi.nr.definition.facade.DesignFormulaSchemeDefine;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BJFormulaInfo {
    private Map<String, String> oldNameAndNewName = new HashMap<String, String>();
    private Map<String, List<String>> bJFormulas = new HashMap<String, List<String>>();
    private DesignFormulaSchemeDefine designFormulaSchemeDefine;
    private String lastFormulaCode;

    public Map<String, List<String>> getbJFormulas() {
        return this.bJFormulas;
    }

    public void setbJFormulas(Map<String, List<String>> bJFormulas) {
        this.bJFormulas = bJFormulas;
    }

    public DesignFormulaSchemeDefine getDesignFormulaSchemeDefine() {
        return this.designFormulaSchemeDefine;
    }

    public void setDesignFormulaSchemeDefine(DesignFormulaSchemeDefine designFormulaSchemeDefine) {
        this.designFormulaSchemeDefine = designFormulaSchemeDefine;
    }

    public String getLastFormulaCode() {
        return this.lastFormulaCode;
    }

    public void setLastFormulaCode(String lastFormulaCode) {
        this.lastFormulaCode = lastFormulaCode;
    }

    public Map<String, String> getOldNameAndNewName() {
        return this.oldNameAndNewName;
    }

    public void setOldNameAndNewName(Map<String, String> oldNameAndNewName) {
        this.oldNameAndNewName = oldNameAndNewName;
    }
}

