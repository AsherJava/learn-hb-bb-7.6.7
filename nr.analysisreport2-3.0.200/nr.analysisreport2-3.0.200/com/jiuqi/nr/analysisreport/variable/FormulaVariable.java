/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.analysisreport.variable;

import com.jiuqi.nr.analysisreport.variable.Variable;
import com.jiuqi.nr.analysisreport.variable.VariableType;

public class FormulaVariable
extends Variable {
    private static final long serialVersionUID = -7492802390810958648L;

    public FormulaVariable() {
        this.variableType = VariableType.FORMULA;
    }
}

