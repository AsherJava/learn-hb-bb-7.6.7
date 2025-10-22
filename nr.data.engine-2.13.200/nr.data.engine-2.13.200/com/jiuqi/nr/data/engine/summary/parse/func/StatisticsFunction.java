/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.ast.IExpression
 *  com.jiuqi.bi.syntax.function.Function
 *  com.jiuqi.bi.syntax.function.FunctionProvider
 *  com.jiuqi.bi.syntax.function.IFunction
 *  com.jiuqi.bi.syntax.function.IFunctionProvider
 *  com.jiuqi.bi.syntax.function.Parameter
 *  com.jiuqi.bi.syntax.interpret.ISQLInfo
 *  com.jiuqi.bi.syntax.interpret.InterpretException
 *  com.jiuqi.bi.syntax.interpret.Language
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.syntax.reportparser.IReportDynamicNodeProvider
 *  com.jiuqi.bi.syntax.reportparser.ReportFormulaParser
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 */
package com.jiuqi.nr.data.engine.summary.parse.func;

import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.bi.syntax.function.Function;
import com.jiuqi.bi.syntax.function.FunctionProvider;
import com.jiuqi.bi.syntax.function.IFunction;
import com.jiuqi.bi.syntax.function.IFunctionProvider;
import com.jiuqi.bi.syntax.function.Parameter;
import com.jiuqi.bi.syntax.interpret.ISQLInfo;
import com.jiuqi.bi.syntax.interpret.InterpretException;
import com.jiuqi.bi.syntax.interpret.Language;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.syntax.reportparser.IReportDynamicNodeProvider;
import com.jiuqi.bi.syntax.reportparser.ReportFormulaParser;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.nr.data.engine.summary.define.IRelationInfoProvider;
import com.jiuqi.nr.data.engine.summary.define.RelationInfo;
import com.jiuqi.nr.data.engine.summary.parse.SumContext;
import com.jiuqi.nr.data.engine.summary.parse.SumNode;
import com.jiuqi.nr.data.engine.summary.parse.SumNodeProvider;
import com.jiuqi.nr.data.engine.summary.parse.func.IsCharOrNumFunction;
import com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StatisticsFunction
extends Function {
    private static final Logger logger = LoggerFactory.getLogger(StatisticsFunction.class);
    private static final long serialVersionUID = -4562771098865625223L;

    public StatisticsFunction() {
        this.parameters().add(new Parameter("data", 0, "\u8981\u7edf\u8ba1\u7684\u6307\u6807"));
        this.parameters().add(new Parameter("type", 6, "\u7edf\u8ba1\u65b9\u5f0f,\u652f\u6301SUM,COUNT,MAX,MIN"));
        this.parameters().add(new Parameter("condition", 6, "\u8fc7\u6ee4\u6761\u4ef6", true));
    }

    public String name() {
        return "Statistics";
    }

    public String title() {
        return "\u7edf\u8ba1\u5b50\u8868\u6570\u636e";
    }

    public int getResultType(IContext paramIContext, List<IASTNode> paramList) throws SyntaxException {
        return 3;
    }

    public boolean support(Language lang) {
        return true;
    }

    public String category() {
        return "\u7edf\u8ba1\u51fd\u6570";
    }

    public Object evalute(IContext paramIContext, List<IASTNode> paramList) throws SyntaxException {
        return null;
    }

    protected void toSQL(IContext paramIContext, List<IASTNode> paramList, StringBuilder paramStringBuilder, ISQLInfo paramISQLInfo) throws InterpretException {
        try {
            String condition;
            SumContext sumContext = (SumContext)paramIContext;
            IRelationInfoProvider relationInfoProvider = sumContext.getBaseDefineFactory().getRelationInfoProvider();
            SumNode node = (SumNode)paramList.get(0);
            String tableName = node.getTable().getTableName();
            String mainTableName = relationInfoProvider.findMainTableName(sumContext.getExecutorContext(), tableName);
            RelationInfo relationInfo = relationInfoProvider.findRelationInfo(sumContext.getExecutorContext(), tableName, mainTableName);
            paramStringBuilder.append("(select ");
            paramStringBuilder.append(paramList.get(1).evaluate(paramIContext));
            paramStringBuilder.append("(");
            paramStringBuilder.append(node.interpret(paramIContext, Language.SQL, paramISQLInfo));
            paramStringBuilder.append(")");
            paramStringBuilder.append(" from ").append(tableName);
            paramStringBuilder.append(" where ");
            for (String srcFieldName : relationInfo.getRelationFieldMap().keySet()) {
                String destFieldName = relationInfo.getRelationFieldMap().get(srcFieldName);
                paramStringBuilder.append(relationInfo.getSrcTableName()).append(".").append(srcFieldName);
                paramStringBuilder.append("=");
                paramStringBuilder.append(relationInfo.getDestTableName()).append(".").append(destFieldName);
            }
            if (paramList.size() == 3 && paramList.get(2) != null && (condition = (String)paramList.get(2).evaluate(paramIContext)) != null && condition.length() > 0) {
                ReportFormulaParser parser = new ReportFormulaParser();
                FunctionProvider SumFunProvider = new FunctionProvider();
                SumFunProvider.add((IFunction)new IsCharOrNumFunction());
                parser.registerFunctionProvider((IFunctionProvider)SumFunProvider);
                SumNodeProvider nodeProvider = new SumNodeProvider();
                parser.registerDynamicNodeProvider((IReportDynamicNodeProvider)nodeProvider);
                IExpression parsedCond = parser.parseCond(condition, paramIContext);
                paramStringBuilder.append(" and ").append(parsedCond.interpret(paramIContext, Language.SQL, (Object)paramISQLInfo));
            }
            paramStringBuilder.append(")");
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    private DataFieldDeployInfo getDeployInfo(String dataFieldKey) {
        IRuntimeDataSchemeService dataSchemeService = (IRuntimeDataSchemeService)SpringBeanUtils.getBean(IRuntimeDataSchemeService.class);
        List deployInfoByDataFieldKeys = dataSchemeService.getDeployInfoByDataFieldKeys(new String[]{dataFieldKey});
        DataFieldDeployInfo deployInfo = (DataFieldDeployInfo)deployInfoByDataFieldKeys.get(0);
        return deployInfo;
    }
}

