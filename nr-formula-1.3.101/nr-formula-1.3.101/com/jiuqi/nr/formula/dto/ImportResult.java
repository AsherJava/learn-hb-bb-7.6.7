/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.formula.dto;

import com.jiuqi.nr.formula.dto.FormulaExtDTO;
import java.util.List;
import java.util.Map;

public class ImportResult {
    private List<String> repeatCode;
    private List<Integer> levelCheck;
    private Map<String, List<FormulaExtDTO>> formulas;
    private String formulaSchemeKey;

    public String getFormulaSchemeKey() {
        return this.formulaSchemeKey;
    }

    public void setFormulaSchemeKey(String formulaSchemeKey) {
        this.formulaSchemeKey = formulaSchemeKey;
    }

    public List<String> getRepeatCode() {
        return this.repeatCode;
    }

    public void setRepeatCode(List<String> repeatCode) {
        this.repeatCode = repeatCode;
    }

    public List<Integer> getLevelCheck() {
        return this.levelCheck;
    }

    public void setLevelCheck(List<Integer> levelCheck) {
        this.levelCheck = levelCheck;
    }

    public Map<String, List<FormulaExtDTO>> getFormulas() {
        return this.formulas;
    }

    public void setFormulas(Map<String, List<FormulaExtDTO>> formulas) {
        this.formulas = formulas;
    }
}

