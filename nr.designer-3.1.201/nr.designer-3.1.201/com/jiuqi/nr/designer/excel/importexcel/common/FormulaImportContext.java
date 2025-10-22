/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.facade.DesignFormulaDefine
 *  com.jiuqi.nr.definition.facade.DesignFormulaSchemeDefine
 */
package com.jiuqi.nr.designer.excel.importexcel.common;

import com.jiuqi.nr.definition.facade.DesignFormulaDefine;
import com.jiuqi.nr.definition.facade.DesignFormulaSchemeDefine;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class FormulaImportContext {
    private DesignFormulaSchemeDefine designFormulaSchemeDefine;
    private Map<String, String> tmpFormulasMap = new HashMap<String, String>();
    private List<DesignFormulaDefine> formulaDefineList = new ArrayList<DesignFormulaDefine>();
    private List<String> bJFormulaList = new ArrayList<String>();
    private LinkedList<String> formulaCode = new LinkedList();

    public DesignFormulaSchemeDefine getDesignFormulaSchemeDefine() {
        return this.designFormulaSchemeDefine;
    }

    public void setDesignFormulaSchemeDefine(DesignFormulaSchemeDefine designFormulaSchemeDefine) {
        this.designFormulaSchemeDefine = designFormulaSchemeDefine;
    }

    public List<DesignFormulaDefine> getFormulaDefineList() {
        return this.formulaDefineList;
    }

    public void setFormulaDefineList(List<DesignFormulaDefine> formulaDefineList) {
        this.formulaDefineList = formulaDefineList;
    }

    public LinkedList<String> getFormulaCode() {
        return this.formulaCode;
    }

    public void setFormulaCode(LinkedList<String> formulaCode) {
        this.formulaCode = formulaCode;
    }

    public Map<String, String> getTmpFormulasMap() {
        return this.tmpFormulasMap;
    }

    public void setTmpFormulasMap(Map<String, String> tmpFormulasMap) {
        this.tmpFormulasMap = tmpFormulasMap;
    }

    public List<String> getBJFormulaList() {
        return this.bJFormulaList;
    }

    public void String(List<String> bJFormulaList) {
        this.bJFormulaList = bJFormulaList;
    }
}

