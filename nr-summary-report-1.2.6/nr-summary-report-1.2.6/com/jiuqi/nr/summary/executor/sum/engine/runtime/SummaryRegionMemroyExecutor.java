/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.parser.ParseException
 *  com.jiuqi.np.dataengine.exception.ExpressionException
 */
package com.jiuqi.nr.summary.executor.sum.engine.runtime;

import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.np.dataengine.exception.ExpressionException;
import com.jiuqi.nr.summary.executor.sum.SumContext;
import com.jiuqi.nr.summary.executor.sum.engine.model.RuntimeSummaryCell;
import com.jiuqi.nr.summary.executor.sum.engine.model.RuntimeSummaryRegion;
import com.jiuqi.nr.summary.executor.sum.engine.network.EvalExprExecNetwork;
import com.jiuqi.nr.summary.executor.sum.engine.network.EvalExprRegionCreator;
import com.jiuqi.nr.summary.executor.sum.engine.runtime.SummaryCondition;
import com.jiuqi.nr.summary.executor.sum.engine.runtime.SummaryRegionExecutor;

public class SummaryRegionMemroyExecutor
extends SummaryRegionExecutor {
    public SummaryRegionMemroyExecutor(RuntimeSummaryRegion region) {
        super(region);
    }

    @Override
    public void execute(SumContext context) throws Exception {
        EvalExprRegionCreator regionCreator = new EvalExprRegionCreator(context, false, context.getQueryParam());
        EvalExprExecNetwork execNetwork = this.createEvalExecNetwork(context, regionCreator);
        execNetwork.initialize(context.getMonitor());
        execNetwork.checkRunTask(context.getMonitor());
        context.getSummaryDataSet().save(context);
    }

    private EvalExprExecNetwork createEvalExecNetwork(SumContext context, EvalExprRegionCreator regionCreator) throws ExpressionException, SyntaxException, ParseException {
        EvalExprExecNetwork execNetwork = new EvalExprExecNetwork(context, regionCreator);
        for (RuntimeSummaryCell cell : context.getCurrentRegion().getCells()) {
            execNetwork.arrangeEvalExpression(cell.getEvalExpression());
        }
        for (SummaryCondition condition : context.getCurrentRegion().getConditions().values()) {
            execNetwork.arrangeEvalExpression(condition.getExpression());
        }
        return execNetwork;
    }
}

