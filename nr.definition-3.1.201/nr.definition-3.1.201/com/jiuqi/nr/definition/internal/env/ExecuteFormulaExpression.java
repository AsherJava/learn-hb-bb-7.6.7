/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IExpression
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 *  com.jiuqi.np.dataengine.query.QueryContext
 *  com.jiuqi.np.definition.controller.IDataDefinitionDesignTimeController
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 */
package com.jiuqi.nr.definition.internal.env;

import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.definition.controller.IDataDefinitionDesignTimeController;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.nr.definition.internal.env.DesignReportFmlExecEnvironment;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExecuteFormulaExpression {
    private static final Logger logger = LoggerFactory.getLogger(ExecuteFormulaExpression.class);
    private IDataDefinitionDesignTimeController designTimeController;
    private IDataDefinitionRuntimeController runtimeController;

    public ExecuteFormulaExpression(IDataDefinitionDesignTimeController designTimeController) {
        this.designTimeController = designTimeController;
    }

    public ExecuteFormulaExpression(IDataDefinitionRuntimeController runtimeController) {
        this.runtimeController = runtimeController;
    }

    public int executeFormula(DesignReportFmlExecEnvironment env, String formulaExpression) {
        int type = -1;
        try {
            ExecutorContext context = new ExecutorContext(null);
            context.setDesignTimeData(true, this.designTimeController);
            context.setEnv((IFmlExecEnvironment)env);
            QueryContext qContext = new QueryContext(context, null);
            IExpression exp = context.getCache().getFormulaParser(context).parseEval(formulaExpression, (IContext)qContext);
            type = exp.getType((IContext)qContext);
        }
        catch (SyntaxException e) {
            logger.error("\u516c\u5f0f\u53d8\u91cf\u516c\u5f0f\u7c7b\u578b\u6821\u9a8c\u5931\u8d25", e);
        }
        return type;
    }

    public int executeFormula(ReportFmlExecEnvironment env, String formulaExpression) {
        int type = -1;
        try {
            ExecutorContext context = new ExecutorContext(this.runtimeController);
            context.setEnv((IFmlExecEnvironment)env);
            QueryContext qContext = new QueryContext(context, null);
            IExpression exp = context.getCache().getFormulaParser(context).parseEval(formulaExpression, (IContext)qContext);
            type = exp.getType((IContext)qContext);
        }
        catch (SyntaxException e) {
            logger.error("\u516c\u5f0f\u53d8\u91cf\u516c\u5f0f\u7c7b\u578b\u6821\u9a8c\u5931\u8d25", e);
        }
        return type;
    }
}

