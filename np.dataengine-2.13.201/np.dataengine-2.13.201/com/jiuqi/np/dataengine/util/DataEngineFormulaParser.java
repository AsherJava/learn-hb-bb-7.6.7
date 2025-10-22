/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.ast.IExpression
 *  com.jiuqi.bi.syntax.cell.Position
 *  com.jiuqi.bi.syntax.cell.WildcardCellNode
 *  com.jiuqi.bi.syntax.cell.WildcardRange
 *  com.jiuqi.bi.syntax.cell.WildcardRange$PosRange
 *  com.jiuqi.bi.syntax.function.FunctionNode
 *  com.jiuqi.bi.syntax.function.IFunction
 *  com.jiuqi.bi.syntax.interpret.InterpretException
 *  com.jiuqi.bi.syntax.interpret.Language
 *  com.jiuqi.bi.syntax.operator.Equal
 *  com.jiuqi.bi.syntax.operator.IfThenElse
 *  com.jiuqi.bi.syntax.operator.LinkPlus
 *  com.jiuqi.bi.syntax.operator.Minus
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.syntax.parser.ParseException
 *  com.jiuqi.bi.syntax.reportparser.ReportFormulaParser
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 */
package com.jiuqi.np.dataengine.util;

import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.bi.syntax.cell.Position;
import com.jiuqi.bi.syntax.cell.WildcardCellNode;
import com.jiuqi.bi.syntax.cell.WildcardRange;
import com.jiuqi.bi.syntax.function.FunctionNode;
import com.jiuqi.bi.syntax.function.IFunction;
import com.jiuqi.bi.syntax.interpret.InterpretException;
import com.jiuqi.bi.syntax.interpret.Language;
import com.jiuqi.bi.syntax.operator.Equal;
import com.jiuqi.bi.syntax.operator.IfThenElse;
import com.jiuqi.bi.syntax.operator.LinkPlus;
import com.jiuqi.bi.syntax.operator.Minus;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.bi.syntax.reportparser.ReportFormulaParser;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.common.CalcCellInfo;
import com.jiuqi.np.dataengine.common.DataColumn;
import com.jiuqi.np.dataengine.common.DataEngineConsts;
import com.jiuqi.np.dataengine.common.DataLinkColumn;
import com.jiuqi.np.dataengine.common.DataModelLinkColumn;
import com.jiuqi.np.dataengine.common.QueryField;
import com.jiuqi.np.dataengine.common.ReportInfo;
import com.jiuqi.np.dataengine.definitions.Formula;
import com.jiuqi.np.dataengine.exception.FormulaParseException;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IMonitor;
import com.jiuqi.np.dataengine.intf.impl.AbstractMonitor;
import com.jiuqi.np.dataengine.intf.impl.FmlEngineBaseMonitor;
import com.jiuqi.np.dataengine.node.CalcExpression;
import com.jiuqi.np.dataengine.node.CellDataNode;
import com.jiuqi.np.dataengine.node.CheckExpression;
import com.jiuqi.np.dataengine.node.DynamicDataNode;
import com.jiuqi.np.dataengine.node.ExpressionUtils;
import com.jiuqi.np.dataengine.node.FormulaShowInfo;
import com.jiuqi.np.dataengine.node.FunctionCalcExpression;
import com.jiuqi.np.dataengine.node.IParsedExpression;
import com.jiuqi.np.dataengine.node.NodeShowInfo;
import com.jiuqi.np.dataengine.node.WildcardCellDataNode;
import com.jiuqi.np.dataengine.parse.AdvanceFunction;
import com.jiuqi.np.dataengine.parse.ParseContext;
import com.jiuqi.np.dataengine.parse.link.ReportFormulaParser_link;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.dataengine.var.Variable;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataEngineFormulaParser {
    private static final Logger logger = LoggerFactory.getLogger("com.jiuqi.nr.data.logic.fml");
    public static final Set<String> innerFunctions = Stream.of("ABS", "Round", "RoundUp", "RoundDown", "Int", "Mod", "Quotient", "Sqrt", "Rand", "LN", "Log10", "Log", "Power", "EXP", "Left", "Right", "Mid", "TRIM", "LeftTrim", "RightTrim", "UPPER", "LOWER", "Str", "Val", "Text", "FormatNum", "Concatenate", "LeftPad", "RightPad", "REPLACE", "ReplaceAll", "Rept", "TextJoin", "Date", "DateValue", "Now", "Today", "YEAR", "MONTH", "DateAdd", "DateDiff", "FormatDate", "DaysInMonth", "EOMONTH", "IfThen", "IfNull", "IsNull", "IsNotNull", "AND", "OR", "NOT", "IFError", "GetEnv", "CharRange", "ChineseDigitUnit", "ChineseStr", "EDate", "ExistChinese", "FALSE", "Hour", "IDC", "IDCard18", "IDCardDate", "IDCL", "IsDigit", "IsIDCard", "L$", "Len", "Length", "M$", "Minute", "MonthDay", "Pos", "Position", "R$", "Second", "Substr", "TRUE", "UUID", "WeekDay", "YearDay", "SUM", "MAX", "MIN", "AVG", "COUNT").collect(Collectors.toSet());

    public static List<IParsedExpression> parseFormula(ExecutorContext context, List<Formula> formulas, DataEngineConsts.FormulaType formulaType) throws ParseException {
        return DataEngineFormulaParser.parseFormula(context, formulas, formulaType, null);
    }

    public static Map<DataEngineConsts.FormulaType, List<IParsedExpression>> parseFormula(ExecutorContext context, List<Formula> formulas, List<DataEngineConsts.FormulaType> formulaTypes, IMonitor monitor) throws ParseException {
        boolean needFinishMonitor = false;
        if (monitor == null) {
            monitor = new AbstractMonitor(DataEngineConsts.DataEngineRunType.PARSE);
            needFinishMonitor = true;
        }
        ParseContext qContext = new ParseContext(context, monitor);
        qContext.setNeedTableRegion(true);
        ReportFormulaParser parser = qContext.getExeContext().getCache().getFormulaParser(qContext.getExeContext());
        for (Formula formula : formulas) {
            if (formula.getFormula() == null || formula.getFormula().trim().startsWith("//")) continue;
            if (qContext.isDebug()) {
                monitor.debug("\u89e3\u6790\u516c\u5f0f\uff1a" + formula, DataEngineConsts.DebugLogType.FORMULA);
            }
            context.setDefaultGroupName(formula.getReportName());
            qContext.setDefaultGroupName(formula.getReportName());
            try {
                IExpression expression = parser.parseEval(formula.getFormula(), (IContext)qContext);
                IExpression balanceZbExp = null;
                if (StringUtils.isNotEmpty((String)formula.getBalanceZBExp())) {
                    balanceZbExp = DataEngineFormulaParser.parseFormula(qContext, parser, formula.getBalanceZBExp(), DataEngineConsts.FormulaType.EXPRESSION);
                }
                if (qContext.getExeContext().isJQReportModel()) {
                    List wildcardsExpresions = expression.expandWildcards((IContext)qContext);
                    if (wildcardsExpresions != null) {
                        List balanceWildcardsExpresions = null;
                        if (balanceZbExp != null) {
                            List balanceWildcardRanges = balanceZbExp.getWildcardRanges();
                            if (!balanceWildcardRanges.isEmpty()) {
                                balanceWildcardRanges.clear();
                            }
                            balanceWildcardRanges.addAll(expression.getWildcardRanges());
                            balanceWildcardsExpresions = balanceZbExp.expandWildcards((IContext)qContext);
                        }
                        for (int i = 0; i < wildcardsExpresions.size(); ++i) {
                            IExpression exp = (IExpression)wildcardsExpresions.get(i);
                            IExpression balanceWildcardsExpresion = null;
                            if (balanceWildcardsExpresions != null) {
                                balanceWildcardsExpresion = (IExpression)balanceWildcardsExpresions.get(i);
                            }
                            DataEngineFormulaParser.addParsedExpressionByTypes(formulaTypes, qContext, formula, exp, balanceWildcardsExpresion);
                        }
                        continue;
                    }
                    DataEngineFormulaParser.addParsedExpressionByTypes(formulaTypes, qContext, formula, expression, balanceZbExp);
                    continue;
                }
                DataEngineFormulaParser.addParsedExpressionByTypes(formulaTypes, qContext, formula, expression, balanceZbExp);
            }
            catch (Exception e) {
                String msg = "\u516c\u5f0f  " + formula + " \u89e3\u6790\u51fa\u9519\uff1a" + e.getMessage();
                FormulaParseException se = new FormulaParseException(msg, e);
                se.setErrorFormua(formula);
                monitor.exception((Exception)((Object)se));
            }
        }
        if (needFinishMonitor) {
            monitor.finish();
        }
        return qContext.getParsedFomlulasByType();
    }

    private static void addParsedExpressionByTypes(List<DataEngineConsts.FormulaType> formulaTypes, ParseContext qContext, Formula formula, IExpression exp, IExpression balanceWildcardsExpresion) throws ParseException, InterpretException {
        CheckExpression anyExp = null;
        for (int index = 0; index < formulaTypes.size(); ++index) {
            DataEngineConsts.FormulaType type = formulaTypes.get(index);
            if (anyExp == null) {
                anyExp = DataEngineFormulaParser.addParsedExpression(qContext, formula, type, exp, (IASTNode)balanceWildcardsExpresion);
                continue;
            }
            DataEngineFormulaParser.addParsedExpression(qContext, formula, type, exp, (IASTNode)balanceWildcardsExpresion, anyExp.getCompliedFormulaExp(), anyExp.getExplain());
        }
    }

    public static List<IParsedExpression> parseFormula(ExecutorContext context, List<Formula> formulas, DataEngineConsts.FormulaType formulaType, IMonitor monitor) throws ParseException {
        boolean needFinishMonitor = false;
        if (monitor == null) {
            monitor = new AbstractMonitor(DataEngineConsts.DataEngineRunType.PARSE);
            needFinishMonitor = true;
        }
        ParseContext qContext = new ParseContext(context, monitor);
        qContext.setNeedTableRegion(true);
        ReportFormulaParser parser = qContext.getExeContext().getCache().getFormulaParser(qContext.getExeContext());
        for (Formula formula : formulas) {
            if (formula.getFormula() == null || formula.getFormula().trim().startsWith("//")) continue;
            if (qContext.isDebug()) {
                monitor.debug("\u89e3\u6790\u516c\u5f0f\uff1a" + formula, DataEngineConsts.DebugLogType.FORMULA);
            }
            context.setDefaultGroupName(formula.getReportName());
            qContext.setDefaultGroupName(formula.getReportName());
            try {
                IExpression expression = DataEngineFormulaParser.parseFormula(qContext, parser, formula.getFormula(), formulaType);
                IExpression balanceZbExp = null;
                if (StringUtils.isNotEmpty((String)formula.getBalanceZBExp())) {
                    balanceZbExp = DataEngineFormulaParser.parseFormula(qContext, parser, formula.getBalanceZBExp(), DataEngineConsts.FormulaType.EXPRESSION);
                }
                if (qContext.getExeContext().isJQReportModel()) {
                    List wildcardsExpresions = expression.expandWildcards((IContext)qContext);
                    if (wildcardsExpresions != null) {
                        List balanceWildcardsExpresions = null;
                        if (balanceZbExp != null) {
                            List balanceWildcardRanges = balanceZbExp.getWildcardRanges();
                            if (!balanceWildcardRanges.isEmpty()) {
                                balanceWildcardRanges.clear();
                            }
                            balanceWildcardRanges.addAll(expression.getWildcardRanges());
                            balanceWildcardsExpresions = balanceZbExp.expandWildcards((IContext)qContext);
                        }
                        for (int i = 0; i < wildcardsExpresions.size(); ++i) {
                            IExpression exp = (IExpression)wildcardsExpresions.get(i);
                            IExpression balanceWildcardsExpresion = null;
                            if (balanceWildcardsExpresions != null) {
                                balanceWildcardsExpresion = (IExpression)balanceWildcardsExpresions.get(i);
                            }
                            DataEngineFormulaParser.addParsedExpression(qContext, formula, formulaType, exp, (IASTNode)balanceWildcardsExpresion);
                        }
                        continue;
                    }
                    DataEngineFormulaParser.addParsedExpression(qContext, formula, formulaType, expression, (IASTNode)balanceZbExp);
                    continue;
                }
                DataEngineFormulaParser.addParsedExpression(qContext, formula, formulaType, expression, (IASTNode)balanceZbExp);
            }
            catch (Exception e) {
                String msg = "\u516c\u5f0f  " + formula + " \u89e3\u6790\u51fa\u9519\uff1a" + e.getMessage();
                FormulaParseException se = new FormulaParseException(msg, e);
                se.setErrorFormua(formula);
                monitor.exception((Exception)((Object)se));
            }
        }
        if (needFinishMonitor) {
            monitor.finish();
        }
        return qContext.getParsedExpressions();
    }

    public static IParsedExpression parseConditionFormula(ParseContext context, Formula formula) throws ParseException {
        ExecutorContext exeContext = context.getExeContext();
        ReportFormulaParser parser = exeContext.getCache().getFormulaParser(exeContext);
        IParsedExpression parsedExpression = null;
        try {
            parsedExpression = context.findconditionExp(formula.getId());
            if (parsedExpression == null) {
                if (formula.getFormula() == null || formula.getFormula().trim().startsWith("//")) {
                    return null;
                }
                IExpression expression = DataEngineFormulaParser.parseFormula(context, parser, formula.getFormula(), DataEngineConsts.FormulaType.EXPRESSION);
                parsedExpression = new CheckExpression(expression, formula);
                context.putConditionExp(formula.getId(), parsedExpression);
            }
        }
        catch (Exception e) {
            String msg = "\u9002\u5e94\u6761\u4ef6\u516c\u5f0f  " + formula + " \u89e3\u6790\u51fa\u9519\uff1a" + e.getMessage();
            FormulaParseException se = new FormulaParseException(msg, e);
            se.setErrorFormua(formula);
            context.getMonitor().exception((Exception)((Object)se));
        }
        return parsedExpression;
    }

    public static List<IParsedExpression> parseDataLinkFormula(ExecutorContext context, List<Formula> formulas, DataEngineConsts.FormulaType formulaType, IMonitor monitor) throws ParseException {
        boolean needFinishMonitor = false;
        if (monitor == null) {
            monitor = new AbstractMonitor(DataEngineConsts.DataEngineRunType.PARSE);
            needFinishMonitor = true;
        }
        ParseContext qContext = new ParseContext(context, monitor);
        qContext.setNeedTableRegion(true);
        ReportFormulaParser_link parser = context.getCache().getDataLinkFormulaParser(context);
        for (Formula formula : formulas) {
            if (formula.getFormula() == null || formula.getFormula().trim().startsWith("//")) continue;
            if (qContext.isDebug()) {
                monitor.debug("\u89e3\u6790\u516c\u5f0f\uff1a" + formula, DataEngineConsts.DebugLogType.FORMULA);
            }
            context.setDefaultGroupName(formula.getReportName());
            qContext.setDefaultGroupName(formula.getReportName());
            try {
                IExpression expression = DataEngineFormulaParser.parseDatalinkFormula(qContext, parser, formula.getFormula(), formulaType);
                IExpression balanceZbExp = null;
                if (StringUtils.isNotEmpty((String)formula.getBalanceZBExp())) {
                    balanceZbExp = DataEngineFormulaParser.parseDatalinkFormula(qContext, parser, formula.getBalanceZBExp(), DataEngineConsts.FormulaType.EXPRESSION);
                }
                if (qContext.getExeContext().isJQReportModel()) {
                    List wildcardsExpresions = expression.expandWildcards((IContext)qContext);
                    if (wildcardsExpresions != null) {
                        List balanceWildcardsExpresions = null;
                        if (balanceZbExp != null) {
                            List balanceWildcardRanges = balanceZbExp.getWildcardRanges();
                            if (!balanceWildcardRanges.isEmpty()) {
                                balanceWildcardRanges.clear();
                            }
                            balanceWildcardRanges.addAll(expression.getWildcardRanges());
                            balanceWildcardsExpresions = balanceZbExp.expandWildcards((IContext)qContext);
                        }
                        for (int i = 0; i < wildcardsExpresions.size(); ++i) {
                            IExpression exp = (IExpression)wildcardsExpresions.get(i);
                            IExpression balanceWildcardsExpresion = null;
                            if (balanceWildcardsExpresions != null) {
                                balanceWildcardsExpresion = (IExpression)balanceWildcardsExpresions.get(i);
                            }
                            DataEngineFormulaParser.addParsedExpression(qContext, formula, formulaType, exp, (IASTNode)balanceWildcardsExpresion);
                        }
                        continue;
                    }
                    DataEngineFormulaParser.addParsedExpression(qContext, formula, formulaType, expression, (IASTNode)balanceZbExp);
                    continue;
                }
                DataEngineFormulaParser.addParsedExpression(qContext, formula, formulaType, expression, (IASTNode)balanceZbExp);
            }
            catch (Exception e) {
                String msg = "\u516c\u5f0f  " + formula + " \u89e3\u6790\u51fa\u9519\uff1a" + e.getMessage();
                FormulaParseException se = new FormulaParseException(msg, e);
                se.setErrorFormua(formula);
                monitor.exception((Exception)((Object)se));
            }
        }
        if (needFinishMonitor) {
            monitor.finish();
        }
        return qContext.getParsedExpressions();
    }

    public static List<IParsedExpression> parseFormulaByCustomPaser(ExecutorContext context, ReportFormulaParser parser, List<Formula> formulas, DataEngineConsts.FormulaType formulaType) throws ParseException {
        FmlEngineBaseMonitor monitor = new FmlEngineBaseMonitor(DataEngineConsts.DataEngineRunType.PARSE);
        monitor.start();
        ParseContext qContext = new ParseContext(context, monitor);
        qContext.setNeedTableRegion(true);
        for (Formula formula : formulas) {
            if (formula.getFormula() == null || formula.getFormula().trim().startsWith("//")) continue;
            context.setDefaultGroupName(formula.getReportName());
            qContext.setDefaultGroupName(formula.getReportName());
            try {
                IExpression expression = DataEngineFormulaParser.parseFormula(qContext, parser, formula.getFormula(), formulaType);
                if (qContext.getExeContext().isJQReportModel()) {
                    List wildcardsExpresions = expression.expandWildcards((IContext)qContext);
                    if (wildcardsExpresions != null) {
                        for (IExpression exp : wildcardsExpresions) {
                            DataEngineFormulaParser.addParsedExpression(qContext, formula, formulaType, exp, null);
                        }
                        continue;
                    }
                    DataEngineFormulaParser.addParsedExpression(qContext, formula, formulaType, expression, null);
                    continue;
                }
                DataEngineFormulaParser.addParsedExpression(qContext, formula, formulaType, expression, null);
            }
            catch (Exception e) {
                String msg = "\u516c\u5f0f  " + formula + " \u89e3\u6790\u51fa\u9519\uff1a" + e.getMessage();
                FormulaParseException se = new FormulaParseException(msg, e);
                se.setErrorFormua(formula);
                monitor.exception((Exception)((Object)se));
            }
        }
        monitor.finish();
        return qContext.getParsedExpressions();
    }

    public static List<String> getFormulaMeannings(ExecutorContext context, List<Formula> formulas, DataEngineConsts.FormulaType formulaType, IMonitor monitor) throws ParseException {
        ArrayList<String> meannings = new ArrayList<String>();
        boolean needFinishMonitor = false;
        if (monitor == null) {
            monitor = new AbstractMonitor(DataEngineConsts.DataEngineRunType.PARSE);
            needFinishMonitor = true;
        }
        QueryContext qContext = new QueryContext(context, monitor);
        qContext.setNeedTableRegion(true);
        ReportFormulaParser parser = qContext.getExeContext().getCache().getFormulaParser(qContext.getExeContext());
        for (Formula formula : formulas) {
            if (formula.getFormula() == null || formula.getFormula().trim().startsWith("//")) {
                meannings.add(null);
                continue;
            }
            if (qContext.isDebug()) {
                monitor.debug("\u89e3\u6790\u516c\u5f0f-\u751f\u6210\u516c\u5f0f\u8bf4\u660e:" + formula, DataEngineConsts.DebugLogType.FORMULA);
            }
            context.setDefaultGroupName(formula.getReportName());
            qContext.setDefaultGroupName(formula.getReportName());
            try {
                IExpression expression = DataEngineFormulaParser.parseFormula(qContext, parser, formula.getFormula(), formulaType);
                DataEngineFormulaParser.setWildcardRageModel(expression);
                String meanning = expression.interpret((IContext)qContext, Language.EXPLAIN, null);
                meannings.add(meanning);
            }
            catch (Exception e) {
                meannings.add(null);
                String msg = "\u516c\u5f0f  " + formula + " \u89e3\u6790\u751f\u6210\u516c\u5f0f\u8bf4\u660e\u51fa\u9519\uff1a" + e.getMessage();
                FormulaParseException se = new FormulaParseException(msg, e);
                se.setErrorFormua(formula);
                monitor.exception((Exception)((Object)se));
            }
        }
        if (needFinishMonitor) {
            monitor.finish();
        }
        return meannings;
    }

    private static void setWildcardRageModel(IExpression expression) {
        List wildcardRanges = expression.getWildcardRanges();
        if (wildcardRanges != null) {
            if (wildcardRanges.size() == 2) {
                ((WildcardRange)wildcardRanges.get(0)).setModel(16);
                ((WildcardRange)wildcardRanges.get(1)).setModel(32);
            } else if (wildcardRanges.size() == 1) {
                for (IASTNode node : expression) {
                    if (!(node instanceof WildcardCellNode)) continue;
                    WildcardCellNode wildcardCellNode = (WildcardCellNode)node;
                    if (wildcardCellNode.getCellPosition().isColWildcard()) {
                        ((WildcardRange)wildcardRanges.get(0)).setModel(32);
                        break;
                    }
                    if (!wildcardCellNode.getCellPosition().isRowWildcard()) continue;
                    ((WildcardRange)wildcardRanges.get(0)).setModel(16);
                    break;
                }
            }
        }
    }

    public static String tansToDataLinkStyle(ExecutorContext context, String formulaExp, String reportName) throws ParseException {
        return DataEngineFormulaParser.transFormulaStyle(context, formulaExp, reportName, DataEngineConsts.FormulaShowType.DATALINK);
    }

    public static String tansToJQStyle(ExecutorContext context, String formulaExp, String reportName) throws ParseException {
        return DataEngineFormulaParser.transFormulaStyle(context, formulaExp, reportName, DataEngineConsts.FormulaShowType.JQ);
    }

    public static String tansToExcelStyle(ExecutorContext context, String formulaExp, String reportName) throws ParseException {
        return DataEngineFormulaParser.transFormulaStyle(context, formulaExp, reportName, DataEngineConsts.FormulaShowType.EXCEL);
    }

    public static String transFormulaStyle(ExecutorContext context, String formulaExp, String reportName, DataEngineConsts.FormulaShowType showType) throws ParseException {
        QueryContext qContext = new QueryContext(context, new AbstractMonitor());
        qContext.setNeedTableRegion(true);
        ReportFormulaParser parser = qContext.getExeContext().getCache().getFormulaParser(qContext.getExeContext());
        if (formulaExp.startsWith("//")) {
            return formulaExp;
        }
        context.setDefaultGroupName(reportName);
        qContext.setDefaultGroupName(reportName);
        try {
            List wildcardsExpresions;
            IExpression expression = DataEngineFormulaParser.parseFormula(qContext, parser, formulaExp, DataEngineConsts.FormulaType.CHECK);
            if (qContext.getExeContext().isJQReportModel() && showType == DataEngineConsts.FormulaShowType.DATALINK && (wildcardsExpresions = expression.expandWildcards((IContext)qContext)) != null) {
                ArrayList<WildcardCellDataNode> wildcardNodes = new ArrayList<WildcardCellDataNode>();
                WildcardCellDataNode expandNode = null;
                for (IASTNode node : expression) {
                    if (!(node instanceof WildcardCellDataNode)) continue;
                    WildcardCellDataNode wildcadNode = (WildcardCellDataNode)node;
                    wildcardNodes.add(wildcadNode);
                    if (expandNode == null) {
                        expandNode = wildcadNode;
                        continue;
                    }
                    if (wildcadNode.getExpandRangeCount() <= expandNode.getExpandRangeCount()) continue;
                    expandNode = wildcadNode;
                }
                WildcardRange[] wildcardRanges = DataEngineFormulaParser.getGlobalRanges(expression, wildcardNodes);
                expression.getWildcardRanges().clear();
                if (wildcardRanges[0] != null) {
                    DataEngineFormulaParser.resetWildcardRange(context, expression, expandNode, wildcardRanges[0], 16);
                }
                if (wildcardRanges[1] != null) {
                    DataEngineFormulaParser.resetWildcardRange(context, expression, expandNode, wildcardRanges[1], 32);
                }
            }
            DataEngineFormulaParser.tryExpandLinkPlus(qContext, (IASTNode)expression);
            return expression.interpret((IContext)qContext, Language.FORMULA, (Object)new FormulaShowInfo(showType));
        }
        catch (Exception e) {
            throw new ParseException(e.getMessage(), (Throwable)e);
        }
    }

    public static String transFormulaStyleFromDataLink(ExecutorContext context, String formulaExp, String reportName, DataEngineConsts.FormulaShowType showType) throws ParseException {
        FmlEngineBaseMonitor monitor = new FmlEngineBaseMonitor(DataEngineConsts.DataEngineRunType.PARSE);
        monitor.start();
        QueryContext qContext = new QueryContext(context, monitor);
        ReportFormulaParser_link parser = context.getCache().getDataLinkFormulaParser(context);
        if (formulaExp.startsWith("//")) {
            return formulaExp;
        }
        context.setDefaultGroupName(reportName);
        qContext.setDefaultGroupName(reportName);
        try {
            List wildcardsExpresions;
            IExpression expression = DataEngineFormulaParser.parseDatalinkFormula(qContext, parser, formulaExp, DataEngineConsts.FormulaType.CHECK);
            if (qContext.getExeContext().isJQReportModel() && showType == DataEngineConsts.FormulaShowType.DATALINK && (wildcardsExpresions = expression.expandWildcards((IContext)qContext)) != null) {
                ArrayList<WildcardCellDataNode> wildcardNodes = new ArrayList<WildcardCellDataNode>();
                WildcardCellDataNode expandNode = null;
                for (IASTNode node : expression) {
                    if (!(node instanceof WildcardCellDataNode)) continue;
                    WildcardCellDataNode wildcadNode = (WildcardCellDataNode)node;
                    wildcardNodes.add(wildcadNode);
                    if (expandNode == null) {
                        expandNode = wildcadNode;
                        continue;
                    }
                    if (wildcadNode.getExpandRangeCount() <= expandNode.getExpandRangeCount()) continue;
                    expandNode = wildcadNode;
                }
                WildcardRange[] wildcardRanges = DataEngineFormulaParser.getGlobalRanges(expression, wildcardNodes);
                expression.getWildcardRanges().clear();
                if (wildcardRanges[0] != null) {
                    DataEngineFormulaParser.resetWildcardRange(context, expression, expandNode, wildcardRanges[0], 16);
                }
                if (wildcardRanges[1] != null) {
                    DataEngineFormulaParser.resetWildcardRange(context, expression, expandNode, wildcardRanges[1], 32);
                }
            }
            DataEngineFormulaParser.tryExpandLinkPlus(qContext, (IASTNode)expression);
            String result = expression.interpret((IContext)qContext, Language.FORMULA, (Object)new FormulaShowInfo(showType));
            monitor.finish();
            return result;
        }
        catch (Exception e) {
            throw new ParseException(e.getMessage(), (Throwable)e);
        }
    }

    public static void tryExpandLinkPlus(QueryContext context, IASTNode nodeExp) {
        for (IASTNode node : nodeExp) {
            if (!(node instanceof LinkPlus)) continue;
            LinkPlus linkPlus = (LinkPlus)node;
            linkPlus.tryExpand((IContext)context);
            if (linkPlus.getChild(1).childrenSize() <= 0 || !(linkPlus.getChild(1).getChild(0) instanceof DynamicDataNode)) continue;
            DynamicDataNode dataNode = (DynamicDataNode)linkPlus.getChild(1).getChild(0);
            for (IASTNode leftChildNode : linkPlus.getChild(0)) {
                QueryField queryField;
                if (!(leftChildNode instanceof DynamicDataNode)) continue;
                DynamicDataNode lDataNode = (DynamicDataNode)leftChildNode;
                lDataNode.setRelateTaskItem(dataNode.getRelateTaskItem());
                if (dataNode.getStatisticInfo() != null) {
                    lDataNode.setStatistic(dataNode.getStatisticInfo().condNode, dataNode.getStatisticInfo().statKind);
                }
                if ((queryField = dataNode.getQueryField()).getPeriodModifier() != null) {
                    lDataNode.getQueryField().getTable().setPeriodModifier(queryField.getPeriodModifier());
                }
                if (queryField.getDimensionRestriction() != null) {
                    lDataNode.getQueryField().getTable().setDimensionRestriction(queryField.getDimensionRestriction());
                }
                NodeShowInfo showInfo = lDataNode.getShowInfo();
                showInfo.setSheetName(dataNode.getShowInfo().getSheetName());
                showInfo.setInnerAppend(dataNode.getShowInfo().getInnerAppend());
                showInfo.setEndAppend(dataNode.getShowInfo().getEndAppend());
                showInfo.setZBExpression(dataNode.getShowInfo().isZBExpression());
            }
        }
    }

    private static void resetWildcardRange(ExecutorContext context, IExpression expression, WildcardCellDataNode expandNode, WildcardRange wildcardRange, int model) {
        HashMap<Integer, String> posMap = new HashMap<Integer, String>();
        for (CellDataNode cellDataNode : expandNode.getExpandCells()) {
            Position cellPosition = cellDataNode.getCellPosition();
            DataModelLinkColumn dataLinkColumn = cellDataNode.getDataModelLinkColumn();
            if (model == 16) {
                int row = cellPosition.row() - expandNode.getRowOffset();
                if (expandNode.getRowMultiply() != 0.0) {
                    row = (int)((double)row / expandNode.getRowMultiply());
                }
                if (row != cellPosition.row()) {
                    dataLinkColumn = context.getDataModelLinkFinder().findDataColumn(dataLinkColumn.getReportInfo(), row, cellPosition.col(), false);
                }
                posMap.put(row, dataLinkColumn.getReportInfo().getReportName() + "_" + dataLinkColumn.getDataLinkCode());
                continue;
            }
            if (model != 32) continue;
            int col = cellPosition.col() - expandNode.getColOffset();
            if (expandNode.getColMultiply() != 0.0) {
                col = (int)((double)col / expandNode.getColMultiply());
            }
            if (col != cellPosition.col()) {
                dataLinkColumn = context.getDataModelLinkFinder().findDataColumn(dataLinkColumn.getReportInfo(), cellPosition.row(), col, false);
            }
            posMap.put(col, dataLinkColumn.getReportInfo().getReportName() + "_" + dataLinkColumn.getDataLinkCode());
        }
        WildcardRange newWildcardRange = new WildcardRange();
        newWildcardRange.setModel(model);
        for (int i = 0; i < wildcardRange.size(); ++i) {
            Object o = wildcardRange.get(i);
            if (o instanceof Integer) {
                String cellId = (String)posMap.get(o);
                if (cellId == null) continue;
                newWildcardRange.add(cellId);
                continue;
            }
            if (!(o instanceof WildcardRange.PosRange)) continue;
            WildcardRange.PosRange range = (WildcardRange.PosRange)o;
            String startCellId = (String)posMap.get(range.start);
            String endCellId = (String)posMap.get(range.end);
            if (startCellId == null || endCellId == null) continue;
            newWildcardRange.add(startCellId, endCellId);
        }
        expression.getWildcardRanges().add(newWildcardRange);
    }

    public static List<IParsedExpression> findRelatedExpressions(String dataLinkCode, List<IParsedExpression> parsedExpressions) {
        ArrayList<IParsedExpression> relatedExpressions = new ArrayList<IParsedExpression>();
        for (IParsedExpression parsedExpression : parsedExpressions) {
            try {
                for (IASTNode node : parsedExpression.getRealExpression()) {
                    DynamicDataNode dataNode;
                    DataLinkColumn column;
                    if (!(node instanceof DynamicDataNode) || (column = (dataNode = (DynamicDataNode)node).getDataLink()) == null || !dataLinkCode.equals(column.getDataLinkCode())) continue;
                    relatedExpressions.add(parsedExpression);
                }
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
        return relatedExpressions;
    }

    private static CheckExpression addParsedExpression(ParseContext context, Formula formula, DataEngineConsts.FormulaType formulaType, IExpression expression, IASTNode balanceZBExpresion) throws ParseException, InterpretException {
        return DataEngineFormulaParser.addParsedExpression(context, formula, formulaType, expression, balanceZBExpresion, null, null);
    }

    private static CheckExpression addParsedExpression(ParseContext context, Formula formula, DataEngineConsts.FormulaType formulaType, IExpression expression, IASTNode balanceZBExpresion, String compliedFormulaExp, String explain) throws ParseException, InterpretException {
        DataEngineFormulaParser.tryExpandLinkPlus(context, (IASTNode)expression);
        Object parsedExpression = null;
        if (formulaType == DataEngineConsts.FormulaType.CALCULATE) {
            DynamicDataNode assignNode = ExpressionUtils.getAssignNode((IASTNode)expression);
            int index = context.getParsedExpressions().size();
            Object calcExpression = new CalcExpression(expression, formula, assignNode, index);
            ExpressionUtils.bindExtnedRWKey((CheckExpression)calcExpression);
            if (((CalcExpression)calcExpression).getAssignNode() == null && ((CalcExpression)calcExpression).getExtendAssignKey() == null) {
                if (expression.getChild(0) instanceof FunctionNode) {
                    FunctionCalcExpression functionCalcExpression;
                    FunctionNode funcNode = (FunctionNode)expression.getChild(0);
                    if (funcNode.getDefine() instanceof AdvanceFunction && (functionCalcExpression = new FunctionCalcExpression(context, expression, formula, funcNode, index)).getWriteTable() != null) {
                        calcExpression = functionCalcExpression;
                    }
                } else if (expression.getChild(0) instanceof IfThenElse) {
                    Iterator ifthen = (IfThenElse)expression.getChild(0);
                    if (!(ifthen.getChild(1) instanceof FunctionNode) && ExpressionUtils.getAssignNode(ifthen.getChild(1)) == null) {
                        throw new ParseException("\u8868\u8fbe\u5f0f\u4e0d\u662f\u6709\u6548\u7684\u8d4b\u503c\u8868\u8fbe\u5f0f\uff01");
                    }
                } else {
                    throw new ParseException("\u8868\u8fbe\u5f0f\u4e0d\u662f\u6709\u6548\u7684\u8d4b\u503c\u8868\u8fbe\u5f0f\uff01");
                }
            }
            for (IASTNode node : expression) {
                DataModelLinkColumn datalink;
                DynamicDataNode dataNode;
                if (!(node instanceof DynamicDataNode) || (dataNode = (DynamicDataNode)node).getQueryField().getDimensionRestriction() != null || dataNode.getStatisticInfo() != null || dataNode.getDataModelLink() == null || (datalink = dataNode.getDataModelLink()).getExpandDims() == null || datalink.getExpandDims().size() <= 0) continue;
                ((CalcExpression)calcExpression).setNeedExpand(true);
            }
            parsedExpression = calcExpression;
        } else {
            parsedExpression = new CheckExpression(expression, formula);
            ExpressionUtils.bindExtnedRWKey((CheckExpression)parsedExpression);
        }
        if (balanceZBExpresion != null) {
            try {
                IExpression balanceExpresion = expression;
                boolean supportBalance = true;
                if (supportBalance) {
                    for (IASTNode node : balanceZBExpresion) {
                        if (!(node instanceof DynamicDataNode)) continue;
                        DynamicDataNode dataNode = (DynamicDataNode)node;
                        ((CheckExpression)parsedExpression).setBalanceField(dataNode.getQueryField());
                    }
                    IASTNode root = expression.getChild(0);
                    if (root instanceof Equal) {
                        balanceExpresion = new Minus(null, root.getChild(0), root.getChild(1));
                    }
                    String evalFormula = balanceExpresion.interpret((IContext)context, Language.FORMULA, (Object)new FormulaShowInfo(DataEngineConsts.FormulaShowType.DATA));
                    ((CheckExpression)parsedExpression).setBalanceFormula(evalFormula);
                } else {
                    ((CheckExpression)parsedExpression).setBalanceField(null);
                    ((CheckExpression)parsedExpression).setBalanceFormula("0");
                }
            }
            catch (InterpretException e) {
                context.getMonitor().exception((Exception)((Object)e));
            }
        }
        ((CheckExpression)parsedExpression).initCompliedInfo(context, compliedFormulaExp, explain);
        IASTNode root = expression.getChild(0);
        if (root instanceof Equal && root.getChild(0) instanceof CellDataNode && root.getChild(1) instanceof CellDataNode) {
            CellDataNode left = (CellDataNode)root.getChild(0);
            CellDataNode right = (CellDataNode)root.getChild(1);
            if (left.getCellPosition() != null && left.getCellPosition().equals((Object)right.getCellPosition()) && left.interpret((IContext)context, Language.FORMULA, null).equals(right.interpret((IContext)context, Language.FORMULA, null))) {
                ((CheckExpression)parsedExpression).setValidate(false);
            }
        }
        context.addParsedExpression((IParsedExpression)parsedExpression, formulaType);
        if (formula.hasConditions()) {
            for (Formula conditionFormula : formula.getConditions()) {
                IParsedExpression conditionExp = DataEngineFormulaParser.parseConditionFormula(context, conditionFormula);
                if (conditionExp == null) continue;
                ((CheckExpression)parsedExpression).getConditions().add(conditionExp);
            }
        }
        return parsedExpression;
    }

    private static IExpression parseDatalinkFormula(QueryContext qContext, ReportFormulaParser_link parser, String formula, DataEngineConsts.FormulaType formulaType) throws ParseException {
        if (formulaType == DataEngineConsts.FormulaType.CHECK) {
            return parser.parseCond(formula, (IContext)qContext);
        }
        if (formulaType == DataEngineConsts.FormulaType.CALCULATE) {
            return parser.parseAssign(formula, (IContext)qContext);
        }
        return parser.parseEval(formula, (IContext)qContext);
    }

    private static IExpression parseFormula(QueryContext qContext, ReportFormulaParser parser, String formula, DataEngineConsts.FormulaType formulaType) throws ParseException {
        if (formulaType == DataEngineConsts.FormulaType.CHECK) {
            return parser.parseCond(formula, (IContext)qContext);
        }
        if (formulaType == DataEngineConsts.FormulaType.CALCULATE) {
            return parser.parseAssign(formula, (IContext)qContext);
        }
        return parser.parseEval(formula, (IContext)qContext);
    }

    private static void addColumnsByExpression(ExecutorContext context, Formula formula, IExpression exp, Map<ReportInfo, List<DataColumn>> calcColumnsMap) throws ParseException {
        Equal equalNode = DataEngineFormulaParser.findEqualNode(exp.getChild(0));
        if (equalNode != null) {
            IASTNode child;
            IASTNode leftNode = equalNode.getChild(0);
            DynamicDataNode dataNode = null;
            if (leftNode instanceof DynamicDataNode) {
                dataNode = (DynamicDataNode)leftNode;
            } else if (leftNode instanceof CellDataNode && (child = leftNode.getChild(0)) instanceof DynamicDataNode) {
                dataNode = (DynamicDataNode)child;
            }
            if (dataNode != null) {
                if (dataNode.getDataLink() != null) {
                    DataLinkColumn column = dataNode.getDataLink();
                    if (column.getDataLinkCode() != null) {
                        ReportInfo reportInfo = column.getReportInfo();
                        List<DataColumn> dataColumns = DataEngineFormulaParser.getDataColumns(calcColumnsMap, reportInfo);
                        dataColumns.add(column);
                    } else {
                        FieldDefine fieldDefine = column.getField();
                        ReportInfo reportInfo = column.getReportInfo();
                        DataColumn dataColumn = new DataColumn(fieldDefine, dataNode.getQueryField().getPeriodModifier(), dataNode.getQueryField().getDimensionRestriction());
                        List<DataColumn> dataColumns = DataEngineFormulaParser.getDataColumns(calcColumnsMap, reportInfo);
                        dataColumns.add(dataColumn);
                    }
                } else {
                    ColumnModelDefine columnModelDefine = context.getCache().getDataModelDefinitionsCache().findField(dataNode.getQueryField().getUID());
                    FieldDefine fieldDefine = context.getCache().getDataModelDefinitionsCache().getFieldDefine(columnModelDefine);
                    DataColumn column = new DataColumn(fieldDefine, dataNode.getQueryField().getPeriodModifier(), dataNode.getQueryField().getDimensionRestriction());
                    ReportInfo reportInfo = context.getDataLinkFinder().findReportInfo(formula.getReportName());
                    List<DataColumn> dataColumns = DataEngineFormulaParser.getDataColumns(calcColumnsMap, reportInfo);
                    dataColumns.add(column);
                }
            }
        }
    }

    private static List<DataColumn> getDataColumns(Map<ReportInfo, List<DataColumn>> calcColumnsMap, ReportInfo reportInfo) {
        List<DataColumn> dataColumns = calcColumnsMap.get(reportInfo);
        if (dataColumns == null) {
            dataColumns = new ArrayList<DataColumn>();
            calcColumnsMap.put(reportInfo, dataColumns);
        }
        return dataColumns;
    }

    public static Equal findEqualNode(IASTNode mainNode) {
        if (mainNode instanceof Equal) {
            return (Equal)mainNode;
        }
        if (mainNode instanceof IfThenElse) {
            return DataEngineFormulaParser.findEqualNode(mainNode.getChild(1));
        }
        return null;
    }

    private static ReportFormulaParser getFormulaParser(ExecutorContext executorContext) throws ParseException {
        ReportFormulaParser parser = executorContext.getCache().getFormulaParser(executorContext);
        return parser;
    }

    private static WildcardRange[] getGlobalRanges(IExpression expression, List<WildcardCellDataNode> wildcardNodes) {
        WildcardRange rowRange = null;
        WildcardRange colRange = null;
        if (!expression.getWildcardRanges().isEmpty()) {
            int mode = 0;
            for (WildcardCellNode wildcardCellNode : wildcardNodes) {
                mode |= wildcardCellNode.getWildcardMode();
            }
            if ((mode & 0x10) != 0) {
                rowRange = (WildcardRange)expression.getWildcardRanges().get(0);
                if (expression.getWildcardRanges().size() >= 2 && (mode & 0x20) != 0) {
                    colRange = (WildcardRange)expression.getWildcardRanges().get(1);
                }
            } else if ((mode & 0x20) != 0) {
                colRange = (WildcardRange)expression.getWildcardRanges().get(0);
            }
        }
        return new WildcardRange[]{rowRange, colRange};
    }

    @Deprecated
    public static Map<ReportInfo, List<DataColumn>> getCalcColumns(ExecutorContext context, List<Formula> formulas) throws ParseException {
        ReportFormulaParser parser = DataEngineFormulaParser.getFormulaParser(context);
        QueryContext queryContext = new QueryContext(context, null);
        HashMap<ReportInfo, List<DataColumn>> calcColumnsMap = new HashMap<ReportInfo, List<DataColumn>>();
        for (Formula formula : formulas) {
            if (formula.getFormula().startsWith("//")) continue;
            context.setDefaultGroupName(formula.getReportName());
            queryContext.setDefaultGroupName(formula.getReportName());
            try {
                IExpression expression = parser.parseAssign(formula.getFormula(), (IContext)queryContext);
                if (queryContext.getExeContext().isJQReportModel()) {
                    List wildcardsExpresions = expression.expandWildcards((IContext)queryContext);
                    if (wildcardsExpresions != null) {
                        for (IExpression exp : wildcardsExpresions) {
                            DataEngineFormulaParser.addColumnsByExpression(context, formula, exp, calcColumnsMap);
                        }
                        continue;
                    }
                    DataEngineFormulaParser.addColumnsByExpression(context, formula, expression, calcColumnsMap);
                    continue;
                }
                DataEngineFormulaParser.addColumnsByExpression(context, formula, expression, calcColumnsMap);
            }
            catch (Exception e) {
                logger.error("\u516c\u5f0f\u89e3\u6790\u51fa\u9519\uff1a{}-{}", formula.getFormula(), e.getMessage(), e);
            }
        }
        return calcColumnsMap;
    }

    public static List<DynamicDataNode> getDynamicDataNodes(IASTNode astNode) {
        ArrayList<DynamicDataNode> dataNodes = new ArrayList<DynamicDataNode>();
        DataEngineFormulaParser.collectDataNodes(astNode, dataNodes);
        return dataNodes;
    }

    public static List<IFunction> getAllFunctions(ExecutorContext context) throws ParseException {
        ReportFormulaParser parser = DataEngineFormulaParser.getFormulaParser(context);
        ArrayList<IFunction> allFunctions = new ArrayList<IFunction>(parser.allFunctions());
        return allFunctions;
    }

    public static List<Variable> getAllVariables(ExecutorContext context) {
        return null;
    }

    public static Map<ReportInfo, List<CalcCellInfo>> getCalcCellInfos(ExecutorContext context, List<IParsedExpression> expressions) {
        HashMap<ReportInfo, List<CalcCellInfo>> calcCellInfos = new HashMap<ReportInfo, List<CalcCellInfo>>();
        FmlEngineBaseMonitor monitor = new FmlEngineBaseMonitor(DataEngineConsts.DataEngineRunType.PARSE);
        monitor.start();
        for (IParsedExpression expression : expressions) {
            try {
                DataLinkColumn column;
                DynamicDataNode assignNode = expression.getAssignNode();
                if (assignNode == null || (column = assignNode.getDataLink()) == null) continue;
                ArrayList<CalcCellInfo> cellInfoList = (ArrayList<CalcCellInfo>)calcCellInfos.get(column.getReportInfo());
                if (cellInfoList == null) {
                    cellInfoList = new ArrayList<CalcCellInfo>();
                    calcCellInfos.put(column.getReportInfo(), cellInfoList);
                }
                CalcCellInfo cellInfo = new CalcCellInfo();
                cellInfo.setDataLinkColumn(column);
                IASTNode rootNode = expression.getRealExpression().getChild(0);
                if (rootNode instanceof IfThenElse) {
                    if (rootNode.childrenSize() < 3) {
                        cellInfo.setReadOnly(false);
                    } else {
                        IASTNode thenNode = rootNode.getChild(2);
                        DynamicDataNode thenAssignNode = ExpressionUtils.getAssignNode(thenNode);
                        if (thenAssignNode == null || !thenAssignNode.equals((Object)assignNode)) {
                            cellInfo.setReadOnly(false);
                        }
                    }
                }
                cellInfo.getCalcFormulas().add(expression.getKey());
                cellInfoList.add(cellInfo);
            }
            catch (Exception e) {
                Formula formula = expression.getSource();
                String msg = "\u89e3\u6790\u8ba1\u7b97\u5355\u5143\u683c\u516c\u5f0f[" + formula.getCode() + "]" + formula.getFormula() + " \u51fa\u9519\uff1a" + e.getMessage();
                FormulaParseException se = new FormulaParseException(msg, e);
                se.setErrorFormua(formula);
                monitor.exception((Exception)((Object)se));
            }
        }
        monitor.finish();
        return calcCellInfos;
    }

    private static void collectDataNodes(IASTNode astNode, List<DynamicDataNode> nodeList) {
        for (int i = 0; i < astNode.childrenSize(); ++i) {
            DynamicDataNode fmlNode;
            IASTNode childNode = astNode.getChild(i);
            if (childNode instanceof DynamicDataNode) {
                fmlNode = (DynamicDataNode)childNode;
                if (DataEngineFormulaParser.containsDataNode(nodeList, fmlNode)) continue;
                nodeList.add(fmlNode);
                continue;
            }
            if (childNode instanceof CellDataNode && childNode.getChild(0) instanceof DynamicDataNode) {
                fmlNode = (DynamicDataNode)childNode.getChild(0);
                if (DataEngineFormulaParser.containsDataNode(nodeList, fmlNode)) continue;
                nodeList.add(fmlNode);
                continue;
            }
            if (childNode.childrenSize() <= 0) continue;
            DataEngineFormulaParser.collectDataNodes(childNode, nodeList);
        }
    }

    private static boolean containsDataNode(List<DynamicDataNode> nodeList, DynamicDataNode fmlNode) {
        for (DynamicDataNode currentNode : nodeList) {
            if (!DataEngineFormulaParser.nodeEquals(fmlNode, currentNode)) continue;
            return true;
        }
        return false;
    }

    private static boolean nodeEquals(DynamicDataNode fmlNode, DynamicDataNode currentNode) {
        DynamicDataNode dataNode = currentNode;
        if (dataNode.equals((Object)fmlNode)) {
            if (dataNode.getDataLink() == null && fmlNode.getDataLink() == null) {
                return true;
            }
            return dataNode.getDataLink() != null && dataNode.getDataLink().equals(fmlNode.getDataLink());
        }
        return false;
    }
}

