/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.efdc.extract.impl.request;

import com.jiuqi.nr.efdc.extract.impl.request.SyntaxCheckFormula;
import java.util.ArrayList;
import java.util.List;

public class SyntaxCheckRequester {
    private List<SyntaxCheckFormula> formulas = new ArrayList<SyntaxCheckFormula>();

    public void setFormulas(List<SyntaxCheckFormula> formulas) {
        this.formulas = formulas;
    }

    public List<SyntaxCheckFormula> getFormulas() {
        return this.formulas;
    }
}

