/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.common;

import com.jiuqi.nr.definition.facade.formula.DesignFormulaCondition;

public class FormulaConditionDTO {
    private String formulaKey;
    private final DesignFormulaCondition formulaCondition;
    private String newExpress;
    private String oldExpress;
    private boolean needUpdate;

    public FormulaConditionDTO(String formulaKey, DesignFormulaCondition formulaCondition, String oldExpress) {
        this.formulaKey = formulaKey;
        this.formulaCondition = formulaCondition;
        this.oldExpress = oldExpress;
    }

    public boolean isNeedUpdate() {
        return this.needUpdate;
    }

    public DesignFormulaCondition getFormulaCondition() {
        return this.formulaCondition;
    }

    public String getNewExpress() {
        return this.newExpress;
    }

    public void setNewExpress(String newExpress) {
        if (newExpress == null) {
            this.needUpdate = false;
        } else {
            this.newExpress = newExpress;
            String condition = this.formulaCondition.getFormulaCondition();
            if (condition.equals(newExpress)) {
                this.needUpdate = false;
            } else {
                this.needUpdate = true;
                this.formulaCondition.setFormulaCondition(newExpress);
            }
        }
    }

    public String getOldExpress() {
        return this.oldExpress;
    }

    public void setOldExpress(String oldExpress) {
        this.oldExpress = oldExpress;
    }

    public String getFormulaKey() {
        return this.formulaKey;
    }

    public void setFormulaKey(String formulaKey) {
        this.formulaKey = formulaKey;
    }
}

