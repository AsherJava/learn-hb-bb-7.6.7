/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.internal.runtime.parse;

import com.jiuqi.nr.definition.facade.FormulaDefine;
import com.jiuqi.nr.definition.facade.FormulaSchemeDefine;
import com.jiuqi.nr.definition.facade.formula.FormulaCondition;
import java.util.List;
import java.util.Map;

public class FormulaParserConfigDTO {
    private FormulaSchemeDefine formulaSchemeDefine;
    private List<FormulaDefine> formulaDefines;
    private Map<String, List<FormulaCondition>> conditionsMap;

    public FormulaParserConfigDTO() {
    }

    public FormulaParserConfigDTO(FormulaSchemeDefine formulaSchemeDefine, List<FormulaDefine> formulaDefines, Map<String, List<FormulaCondition>> conditionsMap) {
        this.formulaSchemeDefine = formulaSchemeDefine;
        this.formulaDefines = formulaDefines;
        this.conditionsMap = conditionsMap;
    }

    public FormulaSchemeDefine getFormulaSchemeDefine() {
        return this.formulaSchemeDefine;
    }

    public void setFormulaSchemeDefine(FormulaSchemeDefine formulaSchemeDefine) {
        this.formulaSchemeDefine = formulaSchemeDefine;
    }

    public List<FormulaDefine> getFormulaDefines() {
        return this.formulaDefines;
    }

    public void setFormulaDefines(List<FormulaDefine> formulaDefines) {
        this.formulaDefines = formulaDefines;
    }

    public Map<String, List<FormulaCondition>> getConditionsMap() {
        return this.conditionsMap;
    }

    public void setConditionsMap(Map<String, List<FormulaCondition>> conditionsMap) {
        this.conditionsMap = conditionsMap;
    }
}

