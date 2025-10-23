/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.facade.formula.DesignFormulaConditionLink
 */
package com.jiuqi.nr.param.transfer.definition.dto.formula;

import com.jiuqi.nr.definition.facade.formula.DesignFormulaConditionLink;

public class FormulaConditionLinkDTO {
    private String conditionKey;
    private String formulaKey;
    private String formulaSchemeKey;

    public static FormulaConditionLinkDTO toDto(DesignFormulaConditionLink link) {
        if (link == null) {
            return null;
        }
        FormulaConditionLinkDTO dto = new FormulaConditionLinkDTO();
        dto.setConditionKey(link.getConditionKey());
        dto.setFormulaKey(link.getFormulaKey());
        dto.setFormulaSchemeKey(link.getFormulaSchemeKey());
        return dto;
    }

    public void toDefine(DesignFormulaConditionLink link) {
        link.setConditionKey(this.conditionKey);
        link.setFormulaKey(this.formulaKey);
        link.setFormulaSchemeKey(this.formulaSchemeKey);
    }

    public String getConditionKey() {
        return this.conditionKey;
    }

    public void setConditionKey(String conditionKey) {
        this.conditionKey = conditionKey;
    }

    public String getFormulaKey() {
        return this.formulaKey;
    }

    public void setFormulaKey(String formulaKey) {
        this.formulaKey = formulaKey;
    }

    public String getFormulaSchemeKey() {
        return this.formulaSchemeKey;
    }

    public void setFormulaSchemeKey(String formulaSchemeKey) {
        this.formulaSchemeKey = formulaSchemeKey;
    }
}

