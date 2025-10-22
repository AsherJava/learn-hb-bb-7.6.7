/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.logic.facade.param.input;

import java.io.Serializable;
import java.util.List;

public class CheckMax
implements Serializable {
    private static final long serialVersionUID = -7789907772233600505L;
    private List<Integer> formulaCheckType;
    private int max = -1;

    public List<Integer> getFormulaCheckType() {
        return this.formulaCheckType;
    }

    public void setFormulaCheckType(List<Integer> formulaCheckType) {
        this.formulaCheckType = formulaCheckType;
    }

    public int getMax() {
        return this.max;
    }

    public void setMax(int max) {
        this.max = max;
    }
}

