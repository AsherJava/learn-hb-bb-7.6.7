/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.ast.ASTNode
 *  com.jiuqi.bi.syntax.ast.Expression
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.ast.IExpression
 *  com.jiuqi.bi.syntax.ast.Token
 *  com.jiuqi.bi.syntax.data.DataNode
 *  com.jiuqi.bi.syntax.data.DimensionNode
 *  com.jiuqi.bi.syntax.dynamic.DynamicNodeException
 *  com.jiuqi.bi.syntax.function.FunctionNode
 *  com.jiuqi.bi.syntax.function.IFunction
 *  com.jiuqi.bi.syntax.operator.Equal
 *  com.jiuqi.bi.syntax.operator.IfThenElse
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.syntax.parser.ParseException
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.period.CustomPeriodModifier
 *  com.jiuqi.np.period.PeriodConsts
 *  com.jiuqi.np.period.PeriodModifier
 *  com.jiuqi.nvwa.dataengine.common.Convert
 *  com.jiuqi.nvwa.definition.common.ColumnModelType
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 */
package com.jiuqi.np.dataengine.node;

import com.jiuqi.bi.syntax.ast.ASTNode;
import com.jiuqi.bi.syntax.ast.Expression;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.bi.syntax.ast.Token;
import com.jiuqi.bi.syntax.data.DataNode;
import com.jiuqi.bi.syntax.data.DimensionNode;
import com.jiuqi.bi.syntax.dynamic.DynamicNodeException;
import com.jiuqi.bi.syntax.function.FunctionNode;
import com.jiuqi.bi.syntax.function.IFunction;
import com.jiuqi.bi.syntax.operator.Equal;
import com.jiuqi.bi.syntax.operator.IfThenElse;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.common.DataEngineConsts;
import com.jiuqi.np.dataengine.common.DataEngineUtil;
import com.jiuqi.np.dataengine.common.DataModelLinkColumn;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.common.LookupItem;
import com.jiuqi.np.dataengine.common.QueryField;
import com.jiuqi.np.dataengine.common.QueryFields;
import com.jiuqi.np.dataengine.common.QueryTable;
import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.definitions.TableModelRunInfo;
import com.jiuqi.np.dataengine.exception.UnknownReadWriteException;
import com.jiuqi.np.dataengine.executors.EvalItem;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.executors.StatItem;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.dataengine.node.CalcExpression;
import com.jiuqi.np.dataengine.node.CellDataNode;
import com.jiuqi.np.dataengine.node.CheckExpression;
import com.jiuqi.np.dataengine.node.DynamicDataNode;
import com.jiuqi.np.dataengine.node.NodeShowInfo;
import com.jiuqi.np.dataengine.node.PeriodConditionNode;
import com.jiuqi.np.dataengine.node.RestrictInfo;
import com.jiuqi.np.dataengine.node.StatisticInfo;
import com.jiuqi.np.dataengine.node.StringValueNodeConcat;
import com.jiuqi.np.dataengine.node.VariableDataNode;
import com.jiuqi.np.dataengine.parse.IReportFunction;
import com.jiuqi.np.dataengine.parse.ReadWriteInfo;
import com.jiuqi.np.dataengine.parse.link.ReportFormulaParser_link;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.dataengine.util.DataEngineFormulaParser;
import com.jiuqi.np.period.CustomPeriodModifier;
import com.jiuqi.np.period.PeriodConsts;
import com.jiuqi.np.period.PeriodModifier;
import com.jiuqi.nvwa.dataengine.common.Convert;
import com.jiuqi.nvwa.definition.common.ColumnModelType;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public final class ExpressionUtils {
    public static QueryFields getQueryFields(IASTNode node) {
        QueryFields queryFields = new QueryFields();
        if (node == null) {
            return queryFields;
        }
        ExpressionUtils.recursiveGetQueryFields(null, node, queryFields, null, null);
        return queryFields;
    }

    public static QueryFields getQueryFields(QueryContext context, IASTNode node) {
        QueryFields queryFields = new QueryFields();
        if (node == null) {
            return queryFields;
        }
        ExpressionUtils.recursiveGetQueryFields(context, node, queryFields, null, null);
        return queryFields;
    }

    public static QueryFields getQueryFields(IASTNode node, List<StatisticInfo> statNodes, List<LookupItem> lookupItems) {
        QueryFields queryFields = new QueryFields();
        if (node == null) {
            return queryFields;
        }
        ExpressionUtils.recursiveGetQueryFields(null, node, queryFields, statNodes, lookupItems);
        return queryFields;
    }

    public static void recursiveGetQueryFields(QueryContext context, IASTNode node, QueryFields queryFields, List<StatisticInfo> statNodes, List<LookupItem> lookupItems) {
        if (node instanceof DynamicDataNode) {
            DynamicDataNode dataNode = (DynamicDataNode)node;
            if (dataNode.getStatisticInfo() != null) {
                if (statNodes != null) {
                    statNodes.add(dataNode.getStatisticInfo());
                }
            } else {
                dataNode.getQueryFields(queryFields);
            }
            if (dataNode.getRelatedNode() != null && lookupItems != null) {
                ExpressionUtils.getLookupItems(dataNode.getRelatedNode(), dataNode.getQueryField(), lookupItems);
            }
            if (context != null && dataNode.getRelateTaskItem() != null) {
                context.getTableLinkAliaMap().put(dataNode.getQueryField().getTable(), dataNode.getRelateTaskItem());
            }
        }
        int count = node.childrenSize();
        for (int index = 0; index < count; ++index) {
            IASTNode child = node.getChild(index);
            ExpressionUtils.recursiveGetQueryFields(context, child, queryFields, statNodes, lookupItems);
        }
    }

    private static void getLookupItems(ASTNode relatedNode, QueryField queryField, List<LookupItem> lookupItems) {
        if (relatedNode instanceof DynamicDataNode) {
            DynamicDataNode dataNode = (DynamicDataNode)relatedNode;
            LookupItem lookupItem = new LookupItem(queryField, dataNode.getQueryField());
            lookupItems.add(lookupItem);
        } else if (relatedNode instanceof StringValueNodeConcat) {
            StringValueNodeConcat concatNode = (StringValueNodeConcat)relatedNode;
            List<ASTNode> concatNodes = concatNode.getConcatNodes();
            for (ASTNode astNode : concatNodes) {
                if (!(astNode instanceof DynamicDataNode)) continue;
                DynamicDataNode dataNode = (DynamicDataNode)astNode;
                LookupItem lookupItem = new LookupItem(queryField, dataNode.getQueryField());
                lookupItems.add(lookupItem);
            }
        }
    }

    public static String extractValueUID(IASTNode node) {
        QueryField queryField;
        DynamicDataNode dynamicDataNode;
        if (node instanceof Expression) {
            node = node.getChild(0);
        }
        if ((dynamicDataNode = (DynamicDataNode)node) == null) {
            return null;
        }
        if (dynamicDataNode.getStatisticInfo() == null && (queryField = dynamicDataNode.getQueryField()) != null) {
            return queryField.getUID();
        }
        return null;
    }

    public static QueryField extractQueryField(IASTNode node) {
        for (IASTNode child : node) {
            if (!(child instanceof DynamicDataNode)) continue;
            DynamicDataNode dataNode = (DynamicDataNode)child;
            return dataNode.getQueryField();
        }
        return null;
    }

    public static IASTNode createNodeByDataModelLinkColumn(QueryContext qContext, Token token, DataModelLinkColumn column, RestrictInfo restrictInfo) throws Exception {
        ExecutorContext exeContext = qContext.getExeContext();
        if (column.getColumModel() != null) {
            QueryField queryField;
            PeriodModifier periodModifier = column.getPeriodModifier();
            DimensionValueSet dimensionRestriction = column.getDimensionRestriction();
            boolean supportLocate = true;
            if (restrictInfo != null) {
                DimensionValueSet restriction = restrictInfo.getDimensionRestriction(qContext, column.getColumModel());
                if (restriction != null && !restriction.isAllNull()) {
                    if (restrictInfo.hasDimensionNodes()) {
                        supportLocate = false;
                    }
                    if (dimensionRestriction == null) {
                        dimensionRestriction = restriction;
                        column.setDimensionRestriction(restriction);
                    } else {
                        dimensionRestriction.combine(restriction);
                    }
                }
                if (restrictInfo.periodModifier != null) {
                    supportLocate = false;
                    periodModifier = restrictInfo.periodModifier;
                    if (dimensionRestriction != null) {
                        dimensionRestriction.clearValue("DATATIME");
                    }
                    if (periodModifier != null && StringUtils.isEmpty((String)restrictInfo.relateItem)) {
                        IFmlExecEnvironment env = exeContext.getEnv();
                        int periodType = env.getPeriodType();
                        if (periodModifier.getPeriodType() != 0 && periodModifier.getPeriodType() != periodType) {
                            ColumnModelDefine columnModel = qContext.getColumnModelFinder().findColumnModelDefine(exeContext, column.getColumModel().getCode(), periodModifier.getPeriodType());
                            column.setColumnModel(columnModel);
                        }
                    }
                    column.setPeriodModifier(periodModifier);
                }
            }
            if ((queryField = exeContext.getCache().extractQueryField(exeContext, column.getColumModel(), periodModifier, dimensionRestriction)) != null) {
                DynamicDataNode dynamicDataNode = new DynamicDataNode(token, queryField);
                dynamicDataNode.setDataModelLink(column);
                if (exeContext.getEnv() != null && exeContext.getEnv().getTableNameFinder() != null) {
                    queryField.setRegion(column.getRegion());
                } else if (qContext.isNeedTableRegion() && !column.getShowInfo().isZBExpression() && exeContext.getDataModelLinkFinder().hasRegionCondition(exeContext, column.getRegion())) {
                    queryField.setRegion(column.getRegion());
                }
                if (restrictInfo != null) {
                    ExpressionUtils.bindRestrictToNode((IContext)qContext, restrictInfo, dynamicDataNode);
                    dynamicDataNode.setSupportLocate(supportLocate);
                }
                return dynamicDataNode;
            }
        } else if (column.getExpression() != null) {
            IExpression expression = exeContext.getCache().getFormulaParser(exeContext).parseEval(column.getExpression(), (IContext)qContext);
            return expression;
        }
        return null;
    }

    public static void bindRestrictToNode(IContext context, RestrictInfo restrictInfo, DynamicDataNode dynamicDataNode) {
        if (restrictInfo.isLJ) {
            QueryTable table = dynamicDataNode.getQueryField().getTable();
            table.setIsLj(true);
            if (restrictInfo.conditionNode != null) {
                QueryContext qContext = (QueryContext)context;
                for (IASTNode node : restrictInfo.conditionNode) {
                    if (!(node instanceof DynamicDataNode)) continue;
                    DynamicDataNode dataNode = (DynamicDataNode)node;
                    try {
                        IASTNode evalNode = ExpressionUtils.createEntityVarNode(qContext, dataNode.getQueryField());
                        dataNode.setRelatedNode((ASTNode)evalNode);
                    }
                    catch (ParseException e) {
                        qContext.getMonitor().exception((Exception)((Object)e));
                    }
                }
                PeriodConditionNode filterNode = new PeriodConditionNode(restrictInfo.conditionFormula, restrictInfo.conditionNode);
                DimensionValueSet dim = new DimensionValueSet();
                dim.setValue("DATATIME", filterNode);
                table.setDimensionRestriction(dim);
            }
        } else if (restrictInfo.statKind > 0) {
            dynamicDataNode.setStatistic(restrictInfo.conditionNode, restrictInfo.statKind);
        } else if (restrictInfo.conditionNode != null) {
            dynamicDataNode.setStatistic(restrictInfo.conditionNode, 1);
        }
        if (restrictInfo.showDictTitle) {
            dynamicDataNode.setShowDictTitle(true);
        }
        if (restrictInfo.relateItem != null) {
            dynamicDataNode.setRelateItem(context, restrictInfo.relateItem);
        }
    }

    public static RestrictInfo parseRestrictInfo(QueryContext qContext, String groupName, NodeShowInfo showInfo, List<IASTNode> specExprs) throws DynamicNodeException, ParseException {
        RestrictInfo restrictInfo = new RestrictInfo();
        if (specExprs == null || specExprs.isEmpty()) {
            return restrictInfo;
        }
        ExecutorContext executorContext = qContext.getExeContext();
        String statCondition = null;
        for (IASTNode spec : specExprs) {
            if (spec instanceof DimensionNode) {
                DimensionNode dimesionNode = (DimensionNode)spec;
                restrictInfo.getDimesionNodes().add(dimesionNode);
                if (showInfo == null) continue;
                showInfo.addInnerAppend(dimesionNode.toString());
                continue;
            }
            if (!(spec instanceof DataNode)) continue;
            boolean isEnd = false;
            try {
                String text;
                DataNode dataNode = (DataNode)spec;
                String qoutText = text = (String)dataNode.evaluate((IContext)qContext);
                if (text.startsWith("'") && text.endsWith("'") || text.startsWith("\"") && text.endsWith("\"")) {
                    text = text.substring(1, text.length() - 1);
                }
                int statKind = -1;
                statKind = StatItem.tryParseStatKind(text);
                if (statKind >= 0) {
                    if (statKind == 8) {
                        restrictInfo.isLJ = true;
                    } else {
                        restrictInfo.statKind = statKind == 0 ? 1 : statKind;
                    }
                } else {
                    PeriodModifier pm = null;
                    if (text.startsWith(".")) {
                        isEnd = true;
                        if ((text = text.substring(1)).matches("^(\\-|\\+)?\\d+")) {
                            char periodType = (char)PeriodConsts.typeToCode((int)executorContext.getPeriodType());
                            pm = PeriodModifier.parse((String)(text + periodType));
                        } else if (executorContext.getPeriodType() == 8) {
                            pm = new CustomPeriodModifier(text);
                        }
                    }
                    if (pm == null) {
                        pm = PeriodModifier.parse((String)text);
                    }
                    if (pm != null && !pm.isEmpty()) {
                        if (restrictInfo.periodModifier == null) {
                            restrictInfo.periodModifier = pm;
                        } else {
                            restrictInfo.periodModifier.union(pm);
                        }
                    } else if (text.startsWith("@")) {
                        isEnd = true;
                        restrictInfo.relateItem = text.substring(1);
                    } else if (text.equals("$")) {
                        isEnd = true;
                        restrictInfo.showDictTitle = true;
                    } else if (!text.startsWith("-") && !text.startsWith("0")) {
                        statCondition = text;
                    }
                }
                if (showInfo == null) continue;
                if (qoutText != null) {
                    text = qoutText;
                }
                if (isEnd) {
                    showInfo.addEndAppend(text);
                    continue;
                }
                showInfo.addInnerAppend(text);
            }
            catch (Exception e) {
                throw new DynamicNodeException("\u89e3\u6790\u6570\u636e\u9650\u5b9a\u4fe1\u606f\u51fa\u9519:" + spec + "\n" + e.getMessage(), (Throwable)e);
            }
        }
        try {
            if (statCondition != null) {
                Object formulaParser;
                if (restrictInfo.relateItem != null) {
                    qContext.setDefaultLinkAlias(restrictInfo.relateItem);
                }
                String contextGroupName = qContext.getDefaultGroupName();
                if (groupName != null) {
                    qContext.setDefaultGroupName(groupName);
                }
                IExpression expression = null;
                if (!executorContext.getCache().hasDataLinkFormulaParser()) {
                    formulaParser = qContext.getFormulaParser();
                    expression = formulaParser.parseCond(statCondition, (IContext)qContext);
                } else {
                    formulaParser = executorContext.getCache().getDataLinkFormulaParser(executorContext);
                    expression = ((ReportFormulaParser_link)formulaParser).parseCond(statCondition, (IContext)qContext);
                }
                qContext.setDefaultGroupName(contextGroupName);
                if (expression != null) {
                    IASTNode left;
                    restrictInfo.conditionNode = expression;
                    restrictInfo.conditionFormula = statCondition;
                    IASTNode root = expression.getChild(0);
                    if (root instanceof Equal && (left = root.getChild(0)).toString().equals("YF")) {
                        restrictInfo.conditionNode = null;
                        restrictInfo.statKind = -1;
                        IASTNode right = root.getChild(1);
                        QueryField queryField = ExpressionUtils.extractQueryField(right);
                        if (queryField != null) {
                            if (restrictInfo.relateItem != null) {
                                qContext.setDefaultLinkAlias(null);
                            }
                            right = ExpressionUtils.createEntityVarNode(qContext, queryField);
                        }
                        DimensionNode dimNode = new DimensionNode(null, "DATATIME", 6, (Object)new PeriodConditionNode(right.toString(), right));
                        restrictInfo.getDimesionNodes().add(dimNode);
                    }
                }
            }
        }
        catch (Exception e) {
            throw new DynamicNodeException("\u89e3\u6790\u6570\u636e\u9650\u5b9a\u4fe1\u606f\u51fa\u9519:" + statCondition + "\n" + e.getMessage(), (Throwable)e);
        }
        finally {
            if (restrictInfo.relateItem != null) {
                qContext.setDefaultLinkAlias(null);
            }
        }
        return restrictInfo;
    }

    public static QueryFields getAllQueryFields(IASTNode node) {
        QueryFields queryFields = new QueryFields();
        if (node == null) {
            return queryFields;
        }
        try {
            ExpressionUtils.recursiveGetAllQueryFields(null, node, queryFields);
        }
        catch (ParseException e) {
            DataEngineUtil.logger.error(e.getMessage(), e);
        }
        return queryFields;
    }

    public static QueryFields getAllQueryFields(QueryContext qContext, IASTNode node) throws UnknownReadWriteException {
        QueryFields queryFields = new QueryFields();
        if (node == null) {
            return queryFields;
        }
        ExpressionUtils.recursiveGetAllQueryFields(qContext, node, queryFields);
        return queryFields;
    }

    private static IASTNode createEntityVarNode(QueryContext qContext, QueryField queryField) throws ParseException {
        String entityTableName = queryField.getTableName();
        String fieldName = queryField.getFieldCode();
        IASTNode right = qContext.getFormulaParser().parseEval("[" + entityTableName + "_" + fieldName + "]", (IContext)qContext).getChild(0);
        return right;
    }

    public static void recursiveGetAllQueryFields(QueryContext qContext, IASTNode node, QueryFields queryFields) throws UnknownReadWriteException {
        if (qContext != null && node instanceof FunctionNode) {
            FunctionNode funcNode = (FunctionNode)node;
            IFunction func = funcNode.getDefine();
            DataEngineConsts.FuncReadWriteType funcType = DataEngineConsts.FuncReadWriteType.UNKNOWN;
            if (func instanceof IReportFunction) {
                IReportFunction reportFunc = (IReportFunction)func;
                funcType = reportFunc.getReadWriteType();
                if (funcType == DataEngineConsts.FuncReadWriteType.CUSTOM) {
                    ReadWriteInfo readWriteInfo = reportFunc.getReadWriteInfo(qContext, funcNode.getParameters());
                    if (readWriteInfo == null) {
                        throw new UnknownReadWriteException(func);
                    }
                    queryFields.addAll(readWriteInfo.getReadQueryFields());
                }
            } else if (DataEngineFormulaParser.innerFunctions.contains(func.name())) {
                funcType = DataEngineConsts.FuncReadWriteType.AUTO;
            }
            if (funcType == DataEngineConsts.FuncReadWriteType.UNKNOWN) {
                throw new UnknownReadWriteException(func);
            }
        } else if (node instanceof DynamicDataNode) {
            DynamicDataNode dataNode = (DynamicDataNode)node;
            if (dataNode.getStatisticInfo() == null) {
                dataNode.getQueryFields(queryFields);
            } else {
                QueryFields qFields = dataNode.getStatisticInfo().getQueryFields();
                queryFields.addAll(qFields);
            }
            if (dataNode.getRelatedNode() != null) {
                ExpressionUtils.recursiveGetAllQueryFields(qContext, (IASTNode)dataNode.getRelatedNode(), queryFields);
            }
        }
        int count = node.childrenSize();
        for (int index = 0; index < count; ++index) {
            IASTNode child = node.getChild(index);
            ExpressionUtils.recursiveGetAllQueryFields(qContext, child, queryFields);
        }
    }

    public static void expandDims(DimensionValueSet dim, Collection<DimensionValueSet> dimSet) {
        String batchDim = null;
        for (int i = 0; i < dim.size(); ++i) {
            Object dimValue = dim.getValue(i);
            if (!(dimValue instanceof List)) continue;
            batchDim = dim.getName(i);
            List values = (List)dimValue;
            for (Object value : values) {
                DimensionValueSet newDim = new DimensionValueSet(dim);
                newDim.setValue(batchDim, value);
                ExpressionUtils.expandDims(newDim, dimSet);
            }
        }
        if (batchDim == null) {
            dimSet.add(dim);
        }
    }

    public static void expandDims(DimensionValueSet dim, Collection<DimensionValueSet> dimSet, TableModelRunInfo tableInfo) {
        String batchDim = null;
        for (int i = 0; i < dim.size(); ++i) {
            Object dimValue = dim.getValue(i);
            if (!(dimValue instanceof List)) continue;
            List values = (List)dimValue;
            batchDim = dim.getName(i);
            ColumnModelDefine keyField = tableInfo.getDimensionField(batchDim);
            if (keyField == null) continue;
            ColumnModelType type = keyField.getColumnType();
            for (Object value : values) {
                value = ExpressionUtils.convertValue(type, value);
                DimensionValueSet newDim = new DimensionValueSet(dim);
                newDim.setValue(batchDim, value);
                ExpressionUtils.expandDims(newDim, dimSet);
            }
        }
        if (batchDim == null) {
            dimSet.add(dim);
        }
    }

    private static Object convertValue(ColumnModelType type, Object value) {
        if (type == ColumnModelType.STRING) {
            value = value.toString();
        } else if (type == ColumnModelType.UUID) {
            value = Convert.toUUID((Object)value);
        } else if (type == ColumnModelType.DATETIME) {
            value = new Timestamp(Convert.toDate((Object)value));
        } else if (type == ColumnModelType.INTEGER) {
            value = Convert.toInt((Object)value);
        }
        return value;
    }

    public static DynamicDataNode getAssignNode(IASTNode node) {
        for (IASTNode child : node) {
            if (child instanceof IfThenElse) {
                return ExpressionUtils.getAssignNode(child.getChild(1));
            }
            if (!(child instanceof Equal)) continue;
            IASTNode leftNode = child.getChild(0);
            if (leftNode instanceof CellDataNode && leftNode.getChild(0) instanceof DynamicDataNode) {
                return (DynamicDataNode)leftNode.getChild(0);
            }
            if (leftNode instanceof DynamicDataNode) {
                return (DynamicDataNode)leftNode;
            }
            return null;
        }
        return null;
    }

    public static Equal findAssignOperator(IASTNode node) {
        for (IASTNode child : node) {
            if (child instanceof IfThenElse) {
                return ExpressionUtils.findAssignOperator(child.getChild(1));
            }
            if (!(child instanceof Equal)) continue;
            return (Equal)child;
        }
        return null;
    }

    public static void bindExtnedRWKey(CheckExpression exp) {
        IASTNode leftNode;
        CalcExpression calcExp;
        VariableDataNode varNode;
        Iterator<IASTNode> iterator = exp.iterator();
        while (iterator.hasNext()) {
            IASTNode child = iterator.next();
            if (!(child instanceof VariableDataNode)) continue;
            varNode = (VariableDataNode)child;
            exp.addExtendReadKey(varNode.getVariable().getVarName().toUpperCase());
        }
        if (exp instanceof CalcExpression && (calcExp = (CalcExpression)exp).getAssignNode() == null && exp.getChild(0) instanceof Equal && (leftNode = exp.getChild(0).getChild(0)) instanceof VariableDataNode) {
            varNode = (VariableDataNode)leftNode;
            calcExp.setExtendAssignKey(varNode.getVariable().getVarName().toUpperCase());
        }
    }

    public static boolean judgeByConditionEvals(List<EvalItem> conditionEvals) {
        boolean succ = true;
        if (conditionEvals != null) {
            for (EvalItem eval : conditionEvals) {
                AbstractData reault = eval.getReault();
                if (reault != null && reault.getAsBool()) continue;
                succ = false;
                break;
            }
        }
        return succ;
    }
}

