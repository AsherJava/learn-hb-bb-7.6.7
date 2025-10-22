/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.ast.IExpression
 *  com.jiuqi.bi.syntax.operator.BinaryOperator
 *  com.jiuqi.bi.syntax.operator.IfThenElse
 */
package com.jiuqi.np.dataengine.executors;

import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.bi.syntax.operator.BinaryOperator;
import com.jiuqi.bi.syntax.operator.IfThenElse;
import com.jiuqi.np.dataengine.common.DataEngineConsts;
import com.jiuqi.np.dataengine.common.QueryFields;
import com.jiuqi.np.dataengine.common.QueryTable;
import com.jiuqi.np.dataengine.definitions.TableModelRunInfo;
import com.jiuqi.np.dataengine.exception.ExpressionException;
import com.jiuqi.np.dataengine.executors.CalcExeExpression;
import com.jiuqi.np.dataengine.executors.CalcExecutor;
import com.jiuqi.np.dataengine.executors.CheckExeExpression;
import com.jiuqi.np.dataengine.executors.CheckExecutor;
import com.jiuqi.np.dataengine.executors.ConditionExecutor;
import com.jiuqi.np.dataengine.executors.EvalExecutor;
import com.jiuqi.np.dataengine.executors.ExecutorCenter;
import com.jiuqi.np.dataengine.executors.ExprExecRegion;
import com.jiuqi.np.dataengine.executors.StatItem;
import com.jiuqi.np.dataengine.node.CalcExpression;
import com.jiuqi.np.dataengine.node.CheckExpression;
import com.jiuqi.np.dataengine.node.DynamicDataNode;
import com.jiuqi.np.dataengine.node.ExpressionUtils;
import com.jiuqi.np.dataengine.node.IParsedExpression;
import com.jiuqi.np.dataengine.node.StatisticInfo;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.dataengine.query.QuerySqlBuilder;
import java.util.ArrayList;

public abstract class ExprExecNetworkBase
extends ExecutorCenter {
    public ExprExecNetworkBase(QueryContext context) {
        super(context);
    }

    public abstract ExprExecRegion setupExepression(QueryFields var1, ArrayList<ExprExecRegion> var2, boolean var3);

    public boolean canAccept(ExprExecRegion execCenter, ArrayList<ExprExecRegion> precursors, boolean hasStatExecutor) {
        if (this.context.getRunnerType() == DataEngineConsts.DataEngineRunType.CALCULATE && hasStatExecutor != execCenter.hasStatExecutor()) {
            return false;
        }
        if (precursors == null) {
            return true;
        }
        for (int i = 0; i < precursors.size(); ++i) {
            if (execCenter.canAddPrecursor(precursors.get(i))) continue;
            return false;
        }
        return true;
    }

    public ArrayList<ExprExecRegion> createStatItems(IASTNode exprNode) throws SyntaxException {
        ArrayList<ExprExecRegion> result = new ArrayList<ExprExecRegion>();
        this.createStatItems(exprNode, result);
        return result;
    }

    private void createStatItems(IASTNode node, ArrayList<ExprExecRegion> exprStatInfo) throws SyntaxException {
        int count = node.childrenSize();
        for (int i = 0; i < count; ++i) {
            DynamicDataNode dataNode;
            IASTNode child = node.getChild(i);
            if (child instanceof DynamicDataNode && (dataNode = (DynamicDataNode)child).getStatisticInfo() != null) {
                exprStatInfo.add(this.arrangeStatItem(dataNode.getStatisticInfo()));
            }
            this.createStatItems(child, exprStatInfo);
        }
    }

    private ExprExecRegion arrangeStatItem(StatisticInfo statInfo) throws SyntaxException {
        StatItem statItem = this.context.getStatItemCollection().creatStatItem(this.context, statInfo);
        QueryFields queryFields = statInfo.getQueryFields();
        ExprExecRegion statCenter = this.setupExepression(queryFields, this.createStatItems((IASTNode)statInfo), true);
        QuerySqlBuilder sqlBuilder = statCenter.queryRegion.getSqlBuilder();
        if (sqlBuilder != null) {
            sqlBuilder.setPrimaryTableName(queryFields.getItem(0).getTableName());
        }
        int index = statCenter.getEvalExecutor().add((IASTNode)statInfo.valueNode);
        statItem.setValue(statCenter.getEvalExecutor(), index);
        if (statInfo.condNode != null) {
            index = statCenter.getEvalExecutor().add(statInfo.condNode);
            statItem.setCondition(statCenter.getEvalExecutor(), index);
        }
        statCenter.getStatExecutor().add(statItem);
        return statCenter;
    }

    public EvalExecutor arrangeEvalExpression(IExpression exprNode) throws ExpressionException, SyntaxException {
        ExprExecRegion execCenter = this.setupExepression(ExpressionUtils.getQueryFields((IASTNode)exprNode), this.createStatItems((IASTNode)exprNode), false);
        execCenter.getEvalExecutor().add((IASTNode)exprNode);
        return execCenter.getEvalExecutor();
    }

    public EvalExecutor arrangeCondExpression(IExpression exprNode) throws ExpressionException, SyntaxException {
        ExprExecRegion execCenter = this.setupExepression(ExpressionUtils.getQueryFields((IASTNode)exprNode), this.createStatItems((IASTNode)exprNode), false);
        execCenter.getEvalExecutor().add((IASTNode)exprNode);
        return execCenter.getEvalExecutor();
    }

    public CalcExecutor arrangeCalcExpression(IExpression exprNode) throws ExpressionException, SyntaxException {
        QueryTable table;
        QuerySqlBuilder sqlBuilder;
        CalcExpression calcExpression = (CalcExpression)exprNode;
        ExprExecRegion execCenter = this.setupExepression(this.getQueryFields(this.context, calcExpression), this.createStatItems((IASTNode)exprNode), false);
        this.addToCalcExetutor(calcExpression, execCenter);
        if (calcExpression.getAssignNode() != null && (sqlBuilder = execCenter.queryRegion.getSqlBuilder()) != null && !(table = calcExpression.getAssignNode().getQueryField().getTable()).isDimensionTable()) {
            sqlBuilder.setPrimaryTable(table);
        }
        return execCenter.getCalcExecutor();
    }

    protected void addToCalcExetutor(CalcExpression calcExpression, ExprExecRegion execCenter) {
        CalcExeExpression exeExpression = new CalcExeExpression(calcExpression);
        CalcExecutor calcExecutor = execCenter.getCalcExecutor();
        calcExecutor.add(exeExpression);
        if (calcExpression.hasConditions()) {
            ConditionExecutor conditionExecutor = execCenter.getConditionExecutor();
            for (IParsedExpression condition : calcExpression.getConditions()) {
                CheckExpression conditionExpression = (CheckExpression)condition;
                int index = conditionExecutor.add(conditionExpression.getSource().getId(), (IASTNode)conditionExpression);
                exeExpression.getConditionEvals().add(conditionExecutor.getItem(index));
            }
        }
    }

    protected void addToCheckExetutor(CheckExpression checkExpression, ExprExecRegion execCenter) {
        CheckExeExpression exeExpression = new CheckExeExpression(checkExpression);
        execCenter.getCheckExecutor().add(exeExpression);
        if (checkExpression.hasConditions()) {
            ConditionExecutor conditionExecutor = execCenter.getConditionExecutor();
            for (IParsedExpression condition : checkExpression.getConditions()) {
                CheckExpression conditionExpression = (CheckExpression)condition;
                int index = conditionExecutor.add(conditionExpression.getSource().getId(), (IASTNode)conditionExpression);
                exeExpression.getConditionEvals().add(conditionExecutor.getItem(index));
            }
        }
    }

    protected QueryFields getQueryFields(QueryContext qContext, CheckExpression expression) {
        QueryFields queryFields = ExpressionUtils.getQueryFields(qContext, (IASTNode)expression);
        if (expression.hasConditions()) {
            for (IParsedExpression condition : expression.getConditions()) {
                ExpressionUtils.recursiveGetQueryFields(null, (IASTNode)condition.getRealExpression(), queryFields, null, null);
            }
        }
        return queryFields;
    }

    public CheckExecutor arrangeCheckExpression(IExpression exprNode) throws ExpressionException, SyntaxException {
        DynamicDataNode leftDataNode;
        CheckExpression checkExpression = (CheckExpression)exprNode;
        ExprExecRegion execCenter = this.setupExepression(this.getQueryFields(this.context, checkExpression), this.createStatItems((IASTNode)exprNode), false);
        this.addToCheckExetutor(checkExpression, execCenter);
        BinaryOperator binaryOperator = this.getBinaryOperator((IASTNode)checkExpression.getRealExpression());
        IASTNode leftNode = null;
        if (binaryOperator != null) {
            leftNode = binaryOperator.getChild(0);
        }
        if (leftNode != null && (leftDataNode = this.getDynamicDataNode(leftNode)) != null && leftDataNode.getStatisticInfo() == null) {
            QueryTable table = leftDataNode.getQueryField().getTable();
            TableModelRunInfo tableInfo = this.context.getExeContext().getCache().getDataModelDefinitionsCache().getTableInfo(table.getTableName());
            if (tableInfo.getOrderField() != null) {
                QuerySqlBuilder sqlBuilder;
                DynamicDataNode rightDataNode;
                IASTNode rightNode;
                if (tableInfo.getDimensionField("RECORDKEY") != null && (rightNode = binaryOperator.getChild(1)) != null && (rightDataNode = this.getDynamicDataNode(rightNode)) != null) {
                    QueryTable rigthTable = rightDataNode.getQueryField().getTable();
                    TableModelRunInfo rightTableInfo = this.context.getExeContext().getCache().getDataModelDefinitionsCache().getTableInfo(rigthTable.getTableName());
                    if (rightTableInfo.getOrderField() != null && rightTableInfo.getDimensionField("RECORDKEY") == null) {
                        table = rigthTable;
                    }
                }
                if ((sqlBuilder = execCenter.queryRegion.getSqlBuilder()) != null && sqlBuilder.getPrimaryTable() == null) {
                    sqlBuilder.setPrimaryTable(table);
                }
            }
        }
        return execCenter.getCheckExecutor();
    }

    private BinaryOperator getBinaryOperator(IASTNode node) {
        for (IASTNode child : node) {
            if (child instanceof IfThenElse) {
                return this.getBinaryOperator(child.getChild(1));
            }
            if (!(child instanceof BinaryOperator)) continue;
            return (BinaryOperator)child;
        }
        return null;
    }

    private DynamicDataNode getDynamicDataNode(IASTNode node) {
        for (IASTNode child : node) {
            if (!(child instanceof DynamicDataNode)) continue;
            return (DynamicDataNode)child;
        }
        return null;
    }
}

