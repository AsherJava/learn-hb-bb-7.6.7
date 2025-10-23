/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.definitions.Formula
 *  com.jiuqi.nr.definition.formulamapping.facade.Data
 */
package com.jiuqi.nr.formula.dto;

import com.jiuqi.np.dataengine.definitions.Formula;
import com.jiuqi.nr.definition.formulamapping.facade.Data;
import java.util.List;

public class FormulaCycleDataDTO
extends Data {
    private List<Formula> formulas;

    public List<Formula> getFormulas() {
        return this.formulas;
    }

    public void setFormulas(List<Formula> formulas) {
        this.formulas = formulas;
    }
}

