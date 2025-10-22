/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.dataengine.definitions;

import com.jiuqi.np.dataengine.definitions.Formula;
import com.jiuqi.np.dataengine.node.IParsedExpression;
import java.util.ArrayList;
import java.util.List;

public class FormulaCallBack {
    private List<Formula> formulas;
    private List<IParsedExpression> parsedExpressions;

    public List<Formula> getFormulas() {
        if (this.formulas == null) {
            this.formulas = new ArrayList<Formula>();
        }
        return this.formulas;
    }

    public List<IParsedExpression> getParsedExpressions() {
        if (this.parsedExpressions == null) {
            this.parsedExpressions = new ArrayList<IParsedExpression>();
        }
        return this.parsedExpressions;
    }
}

