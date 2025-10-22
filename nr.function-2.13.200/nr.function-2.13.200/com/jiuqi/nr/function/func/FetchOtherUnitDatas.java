/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.data.DataNode
 *  com.jiuqi.bi.syntax.function.Function
 *  com.jiuqi.bi.syntax.function.Parameter
 *  com.jiuqi.bi.syntax.interpret.InterpretException
 *  com.jiuqi.bi.syntax.interpret.Language
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.dataengine.IDataAccessProvider
 *  com.jiuqi.np.dataengine.common.DataEngineConsts$FormulaShowType
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IExpressionEvaluator
 *  com.jiuqi.np.dataengine.node.FormulaShowInfo
 *  com.jiuqi.np.dataengine.parse.IReportFunction
 *  com.jiuqi.np.dataengine.query.QueryContext
 *  com.jiuqi.np.definition.common.SpringBeanProvider
 *  org.apache.commons.lang3.StringUtils
 */
package com.jiuqi.nr.function.func;

import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.data.DataNode;
import com.jiuqi.bi.syntax.function.Function;
import com.jiuqi.bi.syntax.function.Parameter;
import com.jiuqi.bi.syntax.interpret.InterpretException;
import com.jiuqi.bi.syntax.interpret.Language;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.dataengine.common.DataEngineConsts;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IExpressionEvaluator;
import com.jiuqi.np.dataengine.node.FormulaShowInfo;
import com.jiuqi.np.dataengine.parse.IReportFunction;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.definition.common.SpringBeanProvider;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

public class FetchOtherUnitDatas
extends Function
implements IReportFunction {
    private static final long serialVersionUID = -6943252075973792335L;

    public FetchOtherUnitDatas() {
        this.parameters().add(new Parameter("unitCode", 6, "\u5355\u4f4d\u4ee3\u7801"));
        this.parameters().add(new Parameter("zbExp", 0, "\u53d6\u503c\u6307\u6807"));
    }

    public String category() {
        return "\u5176\u5b83\u51fd\u6570";
    }

    public int validate(IContext context, List<IASTNode> parameters) throws SyntaxException {
        QueryContext qContext = (QueryContext)context;
        try {
            ExecutorContext exeContext = qContext.getExeContext();
            IASTNode p1 = parameters.get(1);
            if (p1 instanceof DataNode && p1.getType(context) == 6) {
                String zbExp = (String)((DataNode)p1).evaluate(context);
                exeContext.getCache().getFormulaParser(exeContext).parseEval(zbExp, context);
            } else {
                FormulaShowInfo formulaShowInfo = new FormulaShowInfo(DataEngineConsts.FormulaShowType.DATA);
                p1.interpret(context, Language.FORMULA, (Object)formulaShowInfo);
            }
            return this.getResultType(context, parameters);
        }
        catch (Exception e) {
            throw new SyntaxException("\u51fd\u6570" + this.name() + "\u53c2\u6570\u6821\u9a8c\u51fa\u9519" + e.getMessage(), (Throwable)e);
        }
    }

    public Object evalute(IContext context, List<IASTNode> parameters) throws SyntaxException {
        Object result = null;
        if (context instanceof QueryContext) {
            QueryContext qContext = (QueryContext)context;
            try {
                ExecutorContext exeContext = qContext.getExeContext();
                String unitDim = exeContext.getEnv().getUnitDimesion(exeContext);
                String unitCode = this.getUnitCode(qContext, parameters);
                if (StringUtils.isEmpty((CharSequence)unitCode)) {
                    return null;
                }
                if (qContext.isZbCalcMode()) {
                    String cacheKey = unitCode + ":" + parameters.get(1).toString();
                    result = qContext.getCache().get(cacheKey);
                    if (result == null) {
                        result = this.getResult(context, parameters, qContext, exeContext, unitDim, unitCode);
                        qContext.getCache().put(cacheKey, result == null ? "nullValue" : result);
                    } else if ("nullValue".equals(result)) {
                        result = null;
                    }
                } else {
                    result = this.getResult(context, parameters, qContext, exeContext, unitDim, unitCode);
                }
            }
            catch (Exception e) {
                throw new SyntaxException(e.getMessage(), (Throwable)e);
            }
        }
        return result;
    }

    protected String getUnitCode(QueryContext qContext, List<IASTNode> parameters) throws SyntaxException {
        String unitCode = (String)parameters.get(0).evaluate((IContext)qContext);
        return unitCode;
    }

    private Object getResult(IContext context, List<IASTNode> parameters, QueryContext qContext, ExecutorContext exeContext, String unitDim, String unitCode) throws Exception {
        DimensionValueSet dim = new DimensionValueSet(qContext.getCurrentMasterKey());
        dim.setValue(unitDim, (Object)unitCode);
        IASTNode p1 = parameters.get(1);
        String zbExp = null;
        if (p1 instanceof DataNode && p1.getType(context) == 6) {
            zbExp = (String)((DataNode)p1).evaluate(context);
        } else {
            FormulaShowInfo formulaShowInfo = new FormulaShowInfo(DataEngineConsts.FormulaShowType.DATA);
            zbExp = p1.interpret(context, Language.FORMULA, (Object)formulaShowInfo);
        }
        IDataAccessProvider dataAccessProvider = (IDataAccessProvider)SpringBeanProvider.getBean(IDataAccessProvider.class);
        IExpressionEvaluator expressionEvaluator = dataAccessProvider.newExpressionEvaluator();
        Object result = expressionEvaluator.evalValue(zbExp, exeContext, dim);
        return result;
    }

    public int getResultType(IContext arg0, List<IASTNode> arg1) throws SyntaxException {
        return 0;
    }

    public String name() {
        return "FetchOtherUnitDatas";
    }

    public String title() {
        return "\u8de8\u5355\u4f4d\u53d6\u6570";
    }

    protected void toExplain(IContext context, List<IASTNode> parameters, StringBuilder buffer, Object info) throws InterpretException {
        buffer.append("\u53d6\u5355\u4f4d");
        parameters.get(0).interpret(context, buffer, Language.EXPLAIN, info);
        buffer.append("\u7684");
        parameters.get(1).interpret(context, buffer, Language.EXPLAIN, info);
    }
}

