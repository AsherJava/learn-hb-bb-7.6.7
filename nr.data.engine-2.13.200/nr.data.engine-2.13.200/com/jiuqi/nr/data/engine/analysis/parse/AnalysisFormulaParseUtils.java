/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.ast.IExpression
 *  com.jiuqi.bi.syntax.ast.Token
 *  com.jiuqi.bi.syntax.dynamic.DynamicNodeException
 *  com.jiuqi.bi.syntax.function.Function
 *  com.jiuqi.bi.syntax.function.IFunction
 *  com.jiuqi.bi.syntax.operator.Equal
 *  com.jiuqi.bi.syntax.operator.IfThenElse
 *  com.jiuqi.bi.syntax.operator.LinkPlus
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.syntax.parser.ParseException
 *  com.jiuqi.bi.syntax.reportparser.ReportFormulaParser
 *  com.jiuqi.np.dataengine.common.DataEngineConsts$DataEngineRunType
 *  com.jiuqi.np.dataengine.common.DataModelLinkColumn
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.common.QueryField
 *  com.jiuqi.np.dataengine.common.ReportInfo
 *  com.jiuqi.np.dataengine.definitions.Formula
 *  com.jiuqi.np.dataengine.exception.ExpressionException
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IMonitor
 *  com.jiuqi.np.dataengine.intf.impl.AbstractMonitor
 *  com.jiuqi.np.dataengine.node.DynamicDataNode
 *  com.jiuqi.np.dataengine.node.ExpressionUtils
 *  com.jiuqi.np.dataengine.node.IParsedExpression
 *  com.jiuqi.np.dataengine.node.NodeShowInfo
 *  com.jiuqi.np.dataengine.node.RestrictInfo
 *  com.jiuqi.np.dataengine.query.QueryContext
 *  com.jiuqi.np.period.PeriodModifier
 */
package com.jiuqi.nr.data.engine.analysis.parse;

import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.bi.syntax.ast.Token;
import com.jiuqi.bi.syntax.dynamic.DynamicNodeException;
import com.jiuqi.bi.syntax.function.Function;
import com.jiuqi.bi.syntax.function.IFunction;
import com.jiuqi.bi.syntax.operator.Equal;
import com.jiuqi.bi.syntax.operator.IfThenElse;
import com.jiuqi.bi.syntax.operator.LinkPlus;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.bi.syntax.reportparser.ReportFormulaParser;
import com.jiuqi.np.dataengine.common.DataEngineConsts;
import com.jiuqi.np.dataengine.common.DataModelLinkColumn;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.common.QueryField;
import com.jiuqi.np.dataengine.common.ReportInfo;
import com.jiuqi.np.dataengine.definitions.Formula;
import com.jiuqi.np.dataengine.exception.ExpressionException;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IMonitor;
import com.jiuqi.np.dataengine.intf.impl.AbstractMonitor;
import com.jiuqi.np.dataengine.node.DynamicDataNode;
import com.jiuqi.np.dataengine.node.ExpressionUtils;
import com.jiuqi.np.dataengine.node.IParsedExpression;
import com.jiuqi.np.dataengine.node.NodeShowInfo;
import com.jiuqi.np.dataengine.node.RestrictInfo;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.period.PeriodModifier;
import com.jiuqi.nr.data.engine.analysis.exe.AnalysisContext;
import com.jiuqi.nr.data.engine.analysis.parse.AnalysisCellDataNode;
import com.jiuqi.nr.data.engine.analysis.parse.AnalysisDynamicDataNode;
import com.jiuqi.nr.data.engine.analysis.parse.AnalysisExpression;
import com.jiuqi.nr.data.engine.analysis.parse.AnalysisFunctionNode;
import java.util.ArrayList;
import java.util.List;

public class AnalysisFormulaParseUtils {
    public static List<IParsedExpression> parseFormula(AnalysisContext aContext, ReportFormulaParser parser, List<String> formulas, String reportName, IMonitor monitor) throws ParseException {
        ArrayList<IParsedExpression> parsedExpressions = new ArrayList<IParsedExpression>();
        boolean needFinishMonitor = false;
        if (monitor == null) {
            monitor = new AbstractMonitor(DataEngineConsts.DataEngineRunType.PARSE);
            needFinishMonitor = true;
        }
        for (String formula : formulas) {
            if (formula == null || formula.trim().startsWith("//")) continue;
            Formula formulaSource = new Formula();
            formulaSource.setFormula(formula);
            formulaSource.setReportName(reportName);
            aContext.getExeContext().setDefaultGroupName(reportName);
            aContext.setDefaultGroupName(reportName);
            try {
                IExpression expression = parser.parseAssign(formula, (IContext)aContext);
                if (aContext.getExeContext().isJQReportModel()) {
                    List wildcardsExpresions = expression.expandWildcards((IContext)aContext);
                    if (wildcardsExpresions != null) {
                        for (IExpression exp : wildcardsExpresions) {
                            AnalysisFormulaParseUtils.addParsedExpression(aContext, parsedExpressions, formulaSource, exp);
                        }
                        continue;
                    }
                    AnalysisFormulaParseUtils.addParsedExpression(aContext, parsedExpressions, formulaSource, expression);
                    continue;
                }
                AnalysisFormulaParseUtils.addParsedExpression(aContext, parsedExpressions, formulaSource, expression);
            }
            catch (Exception e) {
                monitor.exception(e);
            }
        }
        if (needFinishMonitor) {
            monitor.finish();
        }
        return parsedExpressions;
    }

    public static void tryExpandLinkPlus(QueryContext context, IASTNode nodeExp) {
        for (IASTNode node : nodeExp) {
            if (!(node instanceof LinkPlus)) continue;
            LinkPlus linkPlus = (LinkPlus)node;
            linkPlus.tryExpand((IContext)context);
            if (linkPlus.getChild(1).childrenSize() <= 0 || !(linkPlus.getChild(1).getChild(0) instanceof DynamicDataNode)) continue;
            DynamicDataNode dataNode = (DynamicDataNode)linkPlus.getChild(1).getChild(0);
            for (IASTNode leftChildNode : linkPlus.getChild(0)) {
                if (!(leftChildNode instanceof DynamicDataNode)) continue;
                DynamicDataNode lDataNode = (DynamicDataNode)leftChildNode;
                if (dataNode.getStatisticInfo() != null) {
                    lDataNode.setStatistic(dataNode.getStatisticInfo().condNode, dataNode.getStatisticInfo().statKind);
                }
                NodeShowInfo showInfo = lDataNode.getShowInfo();
                showInfo.setSheetName(dataNode.getShowInfo().getSheetName());
                showInfo.setInnerAppend(dataNode.getShowInfo().getInnerAppend());
                showInfo.setEndAppend(dataNode.getShowInfo().getEndAppend());
                showInfo.setZBExpression(dataNode.getShowInfo().isZBExpression());
            }
        }
    }

    private static void addParsedExpression(QueryContext context, List<IParsedExpression> parsedExpressions, Formula formulaSource, IExpression expression) throws ParseException {
        DataModelLinkColumn datalink;
        AnalysisDynamicDataNode dataNode;
        String reportName = formulaSource.getReportName();
        AnalysisFormulaParseUtils.tryExpandLinkPlus(context, (IASTNode)expression);
        AnalysisDynamicDataNode assignNode = AnalysisFormulaParseUtils.getAssignNode((IASTNode)expression);
        IASTNode rightNode = null;
        Equal equal = AnalysisFormulaParseUtils.getAssignEqual((IASTNode)expression);
        if (equal != null) {
            rightNode = equal.getChild(1);
        }
        AnalysisExpression analysisExpression = new AnalysisExpression(expression, formulaSource, assignNode, rightNode, parsedExpressions.size());
        boolean hasSrc = false;
        if (rightNode != null) {
            for (IASTNode child : rightNode) {
                AnalysisDynamicDataNode dataNode2;
                if (!(child instanceof AnalysisDynamicDataNode) || (dataNode2 = (AnalysisDynamicDataNode)child).getDataLink() == null) continue;
                DataModelLinkColumn datalink2 = dataNode2.getDataModelLink();
                ReportInfo reportInfo = datalink2.getReportInfo();
                if (reportInfo != null && reportName.equals(reportInfo.getReportName())) {
                    analysisExpression.setCalc(true);
                    continue;
                }
                hasSrc = true;
            }
            analysisExpression.setDependsOther(analysisExpression.isCalc() && hasSrc);
        }
        if (assignNode != null && (dataNode = assignNode).getDataLink() != null && !reportName.equals((datalink = dataNode.getDataModelLink()).getReportInfo().getReportName())) {
            dataNode.setDest(false);
        }
        for (IASTNode node : expression) {
            for (int i = 0; i < node.childrenSize(); ++i) {
                IASTNode child = node.getChild(i);
                if (!(child instanceof AnalysisFunctionNode)) continue;
                AnalysisFunctionNode funNode = (AnalysisFunctionNode)child;
                Function func = (Function)funNode.getDefine();
                AnalysisFunctionNode newNode = new AnalysisFunctionNode(funNode.getToken(), (IFunction)func.clone(), funNode.getParameters());
                node.setChild(i, (IASTNode)newNode);
            }
        }
        parsedExpressions.add((IParsedExpression)analysisExpression);
    }

    private static AnalysisDynamicDataNode getAssignNode(IASTNode node) {
        for (IASTNode child : node) {
            if (child instanceof IfThenElse) {
                return AnalysisFormulaParseUtils.getAssignNode(child.getChild(1));
            }
            if (!(child instanceof Equal)) continue;
            IASTNode leftNode = child.getChild(0);
            if (leftNode instanceof AnalysisCellDataNode && leftNode.getChild(0) instanceof AnalysisDynamicDataNode) {
                return (AnalysisDynamicDataNode)leftNode.getChild(0);
            }
            if (leftNode instanceof DynamicDataNode) {
                return (AnalysisDynamicDataNode)leftNode;
            }
            return null;
        }
        return null;
    }

    public static IASTNode createNodeByDataLinkColumn(QueryContext qContext, Token token, DataModelLinkColumn column, RestrictInfo restrictInfo) throws DynamicNodeException, ParseException, ExpressionException {
        ExecutorContext exeContext = qContext.getExeContext();
        if (column.getColumModel() != null) {
            QueryField queryField;
            PeriodModifier periodModifier = column.getPeriodModifier();
            DimensionValueSet dimensionRestriction = column.getDimensionRestriction();
            if (restrictInfo != null) {
                DimensionValueSet restriction;
                if (restrictInfo.periodModifier != null) {
                    periodModifier = restrictInfo.periodModifier;
                    column.setPeriodModifier(periodModifier);
                }
                if ((restriction = restrictInfo.getDimensionRestriction(qContext, column.getColumModel())) != null && !restriction.isAllNull()) {
                    dimensionRestriction = restriction;
                    column.setDimensionRestriction(restriction);
                }
            }
            if ((queryField = exeContext.getCache().extractQueryField(exeContext, column.getColumModel(), periodModifier, dimensionRestriction)) != null) {
                AnalysisDynamicDataNode dynamicDataNode = new AnalysisDynamicDataNode(queryField);
                dynamicDataNode.setDataModelLink(column);
                queryField.setRegion(column.getRegion());
                if (restrictInfo != null) {
                    ExpressionUtils.bindRestrictToNode((IContext)qContext, (RestrictInfo)restrictInfo, (DynamicDataNode)dynamicDataNode);
                }
                return dynamicDataNode;
            }
        } else if (column.getExpression() != null) {
            IExpression expression = exeContext.getCache().getFormulaParser(exeContext).parseEval(column.getExpression(), (IContext)qContext);
            return expression;
        }
        return null;
    }

    public static Equal getAssignEqual(IASTNode node) {
        for (IASTNode child : node) {
            if (child instanceof IfThenElse) {
                return AnalysisFormulaParseUtils.getAssignEqual(child.getChild(1));
            }
            if (!(child instanceof Equal)) continue;
            return (Equal)child;
        }
        return null;
    }
}

