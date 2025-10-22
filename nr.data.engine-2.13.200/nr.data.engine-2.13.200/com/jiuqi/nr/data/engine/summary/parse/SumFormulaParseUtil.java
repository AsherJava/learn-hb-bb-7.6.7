/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.ast.IExpression
 *  com.jiuqi.bi.syntax.function.FunctionProvider
 *  com.jiuqi.bi.syntax.function.IFunction
 *  com.jiuqi.bi.syntax.function.IFunctionProvider
 *  com.jiuqi.bi.syntax.function.logic.InList
 *  com.jiuqi.bi.syntax.interpret.Language
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.syntax.parser.ParseException
 *  com.jiuqi.bi.syntax.reportparser.ReportFormulaParser
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.query.QueryContext
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 */
package com.jiuqi.nr.data.engine.summary.parse;

import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.bi.syntax.function.FunctionProvider;
import com.jiuqi.bi.syntax.function.IFunction;
import com.jiuqi.bi.syntax.function.IFunctionProvider;
import com.jiuqi.bi.syntax.function.logic.InList;
import com.jiuqi.bi.syntax.interpret.Language;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.bi.syntax.reportparser.ReportFormulaParser;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.nr.data.engine.summary.parse.func.COUNTFunction;
import com.jiuqi.nr.data.engine.summary.parse.func.InCollectionFunction;
import com.jiuqi.nr.data.engine.summary.parse.func.LevelPrefixFunction;
import com.jiuqi.nr.data.engine.summary.parse.func.MAXFunction;
import com.jiuqi.nr.data.engine.summary.parse.func.MINFunction;
import com.jiuqi.nr.data.engine.summary.parse.func.SUMFunction;
import com.jiuqi.nr.data.engine.summary.parse.func.StartWithFunction;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;

public class SumFormulaParseUtil {
    public static final String CACHE_KEY = "ROW";

    public static String transFilterFormula(ExecutorContext executorContext, IEntityRow entityRow, String filter) throws Exception {
        ReportFormulaParser parser = executorContext.getCache().getFormulaParser(executorContext);
        FunctionProvider SumFunProvider = SumFormulaParseUtil.createFunctionProvider();
        parser.registerFunctionProvider((IFunctionProvider)SumFunProvider);
        QueryContext context = new QueryContext(executorContext, null);
        context.getCache().put(CACHE_KEY, entityRow);
        IExpression exp = parser.parseCond(filter, (IContext)context);
        return exp.interpret((IContext)context, Language.FORMULA, null);
    }

    public static IExpression parseFormula(ExecutorContext executorContext, String filter) throws ParseException {
        ReportFormulaParser parser = executorContext.getCache().getFormulaParser(executorContext);
        FunctionProvider SumFunProvider = SumFormulaParseUtil.createFunctionProvider();
        parser.registerFunctionProvider((IFunctionProvider)SumFunProvider);
        QueryContext context = new QueryContext(executorContext, null);
        IExpression exp = parser.parseCond(filter, (IContext)context);
        return exp;
    }

    private static FunctionProvider createFunctionProvider() {
        FunctionProvider SumFunProvider = new FunctionProvider();
        SumFunProvider.add((IFunction)new LevelPrefixFunction());
        SumFunProvider.add((IFunction)new SUMFunction());
        SumFunProvider.add((IFunction)new COUNTFunction());
        SumFunProvider.add((IFunction)new MINFunction());
        SumFunProvider.add((IFunction)new MAXFunction());
        SumFunProvider.add((IFunction)new InCollectionFunction());
        SumFunProvider.add((IFunction)new InList());
        SumFunProvider.add((IFunction)new StartWithFunction());
        return SumFunProvider;
    }
}

