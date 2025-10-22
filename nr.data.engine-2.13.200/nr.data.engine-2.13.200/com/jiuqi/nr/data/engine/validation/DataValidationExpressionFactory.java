/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.ast.IExpression
 *  com.jiuqi.bi.syntax.data.ArrayData
 *  com.jiuqi.bi.syntax.function.FunctionNode
 *  com.jiuqi.bi.syntax.operator.And
 *  com.jiuqi.bi.syntax.operator.Equal
 *  com.jiuqi.bi.syntax.operator.LessThan
 *  com.jiuqi.bi.syntax.operator.LessThanOrEqual
 *  com.jiuqi.bi.syntax.operator.MoreThan
 *  com.jiuqi.bi.syntax.operator.MoreThanOrEqual
 *  com.jiuqi.bi.syntax.operator.NotEqual
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.syntax.reportparser.ReportFormulaParser
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.node.DynamicDataNode
 *  com.jiuqi.np.dataengine.query.QueryContext
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 */
package com.jiuqi.nr.data.engine.validation;

import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.bi.syntax.data.ArrayData;
import com.jiuqi.bi.syntax.function.FunctionNode;
import com.jiuqi.bi.syntax.operator.And;
import com.jiuqi.bi.syntax.operator.Equal;
import com.jiuqi.bi.syntax.operator.LessThan;
import com.jiuqi.bi.syntax.operator.LessThanOrEqual;
import com.jiuqi.bi.syntax.operator.MoreThan;
import com.jiuqi.bi.syntax.operator.MoreThanOrEqual;
import com.jiuqi.bi.syntax.operator.NotEqual;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.syntax.reportparser.ReportFormulaParser;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.node.DynamicDataNode;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.nr.data.engine.validation.CompareType;
import com.jiuqi.nr.data.engine.validation.DataValidationExpression;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import java.util.List;

public class DataValidationExpressionFactory {
    public static DataValidationExpression createExpression(FieldDefine fieldDefine, CompareType compareType, Object value) {
        return new DataValidationExpression(fieldDefine, compareType, value);
    }

    public static DataValidationExpression createExpression(FieldDefine fieldDefine, CompareType compareType, Object min, Object max) {
        return new DataValidationExpression(fieldDefine, compareType, min, max);
    }

    public static DataValidationExpression createExpression(FieldDefine fieldDefine, CompareType compareType, Object[] values) {
        return new DataValidationExpression(fieldDefine, compareType, values);
    }

    public static DataValidationExpression createExpression(ExecutorContext context, String formula) throws SyntaxException {
        QueryContext qContext = new QueryContext(context, null);
        return DataValidationExpressionFactory.createExpression(qContext, formula);
    }

    public static DataValidationExpression createExpression(QueryContext qContext, String formula) throws SyntaxException {
        if (formula.startsWith("//")) {
            return null;
        }
        ReportFormulaParser parser = qContext.getExeContext().getCache().getFormulaParser(qContext.getExeContext());
        IExpression expression = parser.parseCond(formula, (IContext)qContext);
        if (expression != null) {
            FunctionNode functionNode;
            ColumnModelDefine fieldDefine = null;
            CompareType compareType = null;
            IASTNode root = expression.getChild(0);
            if (root instanceof And) {
                IASTNode minNode = root.getChild(0);
                IASTNode maxNode = root.getChild(1);
                compareType = minNode instanceof MoreThanOrEqual ? CompareType.BETWEEN : CompareType.NOT_BETWEEN;
                DynamicDataNode dataNode = (DynamicDataNode)minNode.getChild(0);
                fieldDefine = qContext.getExeContext().getCache().extractFieldDefine((IASTNode)dataNode);
                Object min = minNode.getChild(1).evaluate((IContext)qContext);
                Object max = maxNode.getChild(1).evaluate((IContext)qContext);
                FieldDefine field = qContext.getExeContext().getCache().getDataModelDefinitionsCache().getFieldDefine(fieldDefine);
                return DataValidationExpressionFactory.createExpression(field, compareType, min, max);
            }
            IASTNode leftNode = root.getChild(0);
            IASTNode rightNode = root.getChild(1);
            Object value = null;
            if (leftNode instanceof DynamicDataNode) {
                DynamicDataNode dataNode = (DynamicDataNode)leftNode;
                fieldDefine = qContext.getExeContext().getCache().extractFieldDefine((IASTNode)dataNode);
                value = rightNode.evaluate((IContext)qContext);
                if (value instanceof ArrayData) {
                    List valueList = ((ArrayData)value).toList();
                    Object[] values = valueList.toArray();
                    FieldDefine field = qContext.getExeContext().getCache().getDataModelDefinitionsCache().getFieldDefine(fieldDefine);
                    return DataValidationExpressionFactory.createExpression(field, compareType, values);
                }
            } else if (leftNode instanceof FunctionNode && (functionNode = (FunctionNode)leftNode).childrenSize() == 2) {
                value = functionNode.getChild(0).evaluate((IContext)qContext);
                DynamicDataNode dataNode = (DynamicDataNode)functionNode.getChild(1);
                fieldDefine = qContext.getExeContext().getCache().extractFieldDefine((IASTNode)dataNode);
                CompareType compareType2 = compareType = root instanceof MoreThanOrEqual || root instanceof MoreThan ? CompareType.CONTAINS : CompareType.NOT_CONTAINS;
            }
            if (compareType == null) {
                if (root instanceof Equal) {
                    compareType = CompareType.EQUAL;
                } else if (root instanceof NotEqual) {
                    compareType = CompareType.NOT_EQUAL;
                } else if (root instanceof LessThan) {
                    compareType = CompareType.LESS_THAN;
                } else if (root instanceof MoreThan) {
                    compareType = CompareType.MORE_THAN;
                } else if (root instanceof LessThanOrEqual) {
                    compareType = CompareType.LESS_THAN_OR_EQUAL;
                } else if (root instanceof MoreThanOrEqual) {
                    compareType = CompareType.MORE_THAN_OR_EQUAL;
                }
            }
            FieldDefine field = qContext.getExeContext().getCache().getDataModelDefinitionsCache().getFieldDefine(fieldDefine);
            return DataValidationExpressionFactory.createExpression(field, compareType, value);
        }
        return null;
    }
}

