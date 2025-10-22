/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.dataengine.parse;

import com.jiuqi.np.dataengine.definitions.Formula;
import com.jiuqi.np.dataengine.parse.SortExpression;
import java.util.ArrayList;
import java.util.List;

public class ParsedFormula {
    private Formula source;
    private List<SortExpression> globbingExpressions;
    private SortExpression expression;

    public ParsedFormula(Formula source) {
        this.source = source;
    }

    public SortExpression getExpression() {
        return this.expression;
    }

    public void setExpression(SortExpression expression) {
        this.expression = expression;
    }

    public List<SortExpression> getGlobbingExpressions() {
        if (this.globbingExpressions == null) {
            this.globbingExpressions = new ArrayList<SortExpression>();
        }
        return this.globbingExpressions;
    }

    public Formula getSource() {
        return this.source;
    }

    public void setSource(Formula source) {
        this.source = source;
    }
}

