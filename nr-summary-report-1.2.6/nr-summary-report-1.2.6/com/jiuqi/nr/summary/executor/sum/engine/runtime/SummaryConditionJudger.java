/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IExpression
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.syntax.parser.ParseException
 */
package com.jiuqi.nr.summary.executor.sum.engine.runtime;

import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.nr.summary.executor.sum.SumContext;
import com.jiuqi.nr.summary.executor.sum.engine.runtime.SummaryCondition;
import java.util.ArrayList;
import java.util.List;

public class SummaryConditionJudger {
    private List<SummaryCondition> conditions = new ArrayList<SummaryCondition>();
    private Boolean[] results;

    public SummaryCondition addCondition(SumContext context, String type, String formula) throws ParseException {
        int index = this.conditions.size();
        try {
            IExpression exp = context.getFormulaParser().parseCond(formula, (IContext)context);
            SummaryCondition condition = new SummaryCondition(type, index, formula, exp);
            this.conditions.add(condition);
            return condition;
        }
        catch (ParseException e) {
            context.getLogger().error(formula + "\u89e3\u6790\u51fa\u9519:" + e.getMessage(), e);
            return null;
        }
    }

    public SummaryCondition addCondition(SumContext context, String type, String formula, IExpression exp) throws ParseException {
        int index = this.conditions.size();
        SummaryCondition condition = new SummaryCondition(type, index, formula, exp);
        this.conditions.add(condition);
        return condition;
    }

    public void judge(SumContext context) {
        this.results = new Boolean[this.conditions.size()];
        int index = 0;
        for (SummaryCondition condition : this.conditions) {
            try {
                this.results[index] = condition.getExpression().judge((IContext)context);
            }
            catch (SyntaxException e) {
                this.results[index] = false;
                context.getMonitor().exception((Exception)((Object)e));
            }
            ++index;
        }
    }

    public boolean getResult(int index) {
        return this.results[index];
    }

    public boolean getResult(SummaryCondition condition) {
        return this.getResult(condition.getIndex());
    }

    public boolean getResult(List<SummaryCondition> conditions) {
        for (SummaryCondition condition : conditions) {
            if (this.getResult(condition)) continue;
            return false;
        }
        return true;
    }
}

