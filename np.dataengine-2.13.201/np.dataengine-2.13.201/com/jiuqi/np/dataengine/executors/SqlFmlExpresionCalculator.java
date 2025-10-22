/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.ast.IExpression
 *  com.jiuqi.bi.syntax.interpret.InterpretException
 *  com.jiuqi.bi.syntax.interpret.Language
 *  com.jiuqi.bi.syntax.interpret.SQLInfoDescr
 *  com.jiuqi.bi.syntax.operator.IfThenElse
 *  com.jiuqi.bi.syntax.operator.OperatorNode
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.syntax.parser.ParseException
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 */
package com.jiuqi.np.dataengine.executors;

import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.bi.syntax.interpret.InterpretException;
import com.jiuqi.bi.syntax.interpret.Language;
import com.jiuqi.bi.syntax.interpret.SQLInfoDescr;
import com.jiuqi.bi.syntax.operator.IfThenElse;
import com.jiuqi.bi.syntax.operator.OperatorNode;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.np.dataengine.QueryParam;
import com.jiuqi.np.dataengine.common.DataEngineUtil;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.common.QueryField;
import com.jiuqi.np.dataengine.common.QueryTable;
import com.jiuqi.np.dataengine.definitions.TableModelRunInfo;
import com.jiuqi.np.dataengine.definitions.TableRunInfo;
import com.jiuqi.np.dataengine.executors.SqlCalcItem;
import com.jiuqi.np.dataengine.intf.IMonitor;
import com.jiuqi.np.dataengine.node.CellDataNode;
import com.jiuqi.np.dataengine.node.DynamicDataNode;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SqlFmlExpresionCalculator {
    private static final Logger logger = LoggerFactory.getLogger(SqlFmlExpresionCalculator.class);
    private final QueryParam queryParam;
    private static final String UPDATE_SQL_HEAD = " define update calcUpdate()\n begin\n";
    private static final String UPDATE_SQL_END = " \nend";
    private final DimensionValueSet dimensionValueSet;

    public SqlFmlExpresionCalculator(DimensionValueSet dimensionValueSet, QueryParam queryParam) {
        this.dimensionValueSet = dimensionValueSet;
        this.queryParam = queryParam;
    }

    public void doCalculate(QueryContext qContext, IExpression exp, IMonitor monitor) throws Exception {
        List<SqlCalcItem> calcItems = this.getCalcItems((IContext)qContext, exp);
        for (SqlCalcItem item : calcItems) {
            this.runByJdbcSql(qContext, item);
        }
    }

    private void runByJdbcSql(QueryContext qContext, SqlCalcItem item) throws SQLException, ParseException {
        StringBuilder sql = new StringBuilder();
        QueryField leftField = item.getLeftNode().getQueryField();
        QueryTable table = leftField.getTable();
        TableModelRunInfo tableInfo = qContext.getExeContext().getCache().getDataModelDefinitionsCache().getTableInfo(table.getTableName());
        sql.append(" update ").append(leftField.getTableName()).append(" ").append(table.getAlias());
        sql.append(" set ").append(item.getAssignSqlPart()).append(" where ");
        ArrayList<Object> args = new ArrayList<Object>();
        boolean needAnd = false;
        for (int j = 0; j < this.dimensionValueSet.size(); ++j) {
            Object dimValue = this.dimensionValueSet.getValue(j);
            String dimName = this.dimensionValueSet.getName(j);
            ColumnModelDefine dimensionField = tableInfo.getDimensionField(dimName);
            if (dimensionField == null) continue;
            if (needAnd) {
                sql.append(" and ");
            }
            if (dimValue instanceof List) {
                List batchValues = (List)dimValue;
                if (batchValues.size() < 500) {
                    sql.append(table.getAlias()).append(".").append(dimensionField.getName()).append(" in(");
                    for (Object value : batchValues) {
                        sql.append("?,");
                        args.add(value);
                    }
                    sql.setLength(sql.length() - 1);
                    sql.append(")");
                }
            } else {
                sql.append(table.getAlias()).append(".").append(dimensionField.getName()).append("=?");
                args.add(dimValue);
            }
            needAnd = true;
        }
        if (item.getConditionSqlPart() != null) {
            sql.append(" and ").append(item.getConditionSqlPart());
        }
        DataEngineUtil.executeUpdate(this.queryParam, sql.toString(), args.toArray(), qContext.getMonitor());
    }

    private List<SqlCalcItem> getCalcItems(IContext context, IExpression exp) throws InterpretException {
        IASTNode mainNode = exp.getChild(0);
        ArrayList<SqlCalcItem> calcItems = new ArrayList<SqlCalcItem>();
        ArrayList<DynamicDataNode> allNodes = new ArrayList<DynamicDataNode>();
        this.collectDataNodes(mainNode, allNodes);
        if (mainNode instanceof IfThenElse) {
            IfThenElse ifThenElseNode = (IfThenElse)mainNode;
            IASTNode ifNode = ifThenElseNode.getChild(0);
            IASTNode thenNode = ifThenElseNode.getChild(1);
            DynamicDataNode leftNode = this.getLeftNode(thenNode);
            for (DynamicDataNode node : allNodes) {
                node.setInnerSelect(leftNode.getQueryField().getTable());
            }
            SQLInfoDescr conditionSqlINfo = new SQLInfoDescr(this.queryParam.getDatabase(), true, 0, 0);
            String conditionSqlPart = "(" + ifNode.interpret(context, Language.SQL, (Object)conditionSqlINfo) + ")";
            IASTNode elseNode = ifThenElseNode.getChild(2);
            SqlCalcItem thenItem = this.buildCalcItem(context, leftNode, thenNode, conditionSqlPart);
            calcItems.add(thenItem);
            if (elseNode != null) {
                String notIfSqlPart = "not " + conditionSqlPart;
                DynamicDataNode leftLeftNode = this.getLeftNode(elseNode);
                SqlCalcItem elseItem = this.buildCalcItem(context, leftLeftNode, elseNode, notIfSqlPart);
                calcItems.add(elseItem);
            }
        } else {
            DynamicDataNode leftNode = this.getLeftNode(mainNode);
            for (DynamicDataNode node : allNodes) {
                node.setInnerSelect(leftNode.getQueryField().getTable());
            }
            SqlCalcItem item = this.buildCalcItem(context, leftNode, mainNode, null);
            calcItems.add(item);
        }
        return calcItems;
    }

    private SqlCalcItem buildCalcItem(IContext context, DynamicDataNode leftNode, IASTNode thenNode, String ifSqlPart) throws InterpretException {
        SqlCalcItem item = new SqlCalcItem(leftNode);
        item.setConditionSqlPart(ifSqlPart);
        SQLInfoDescr sqlInfo = new SQLInfoDescr(this.queryParam.getDatabase(), false, 0, 0);
        item.setAssignSqlPart(thenNode.interpret(context, Language.SQL, (Object)sqlInfo));
        return item;
    }

    private DynamicDataNode getLeftNode(IASTNode thenNode) {
        OperatorNode operNode = (OperatorNode)thenNode;
        DynamicDataNode leftNode = null;
        if (operNode.getChild(0) instanceof DynamicDataNode) {
            leftNode = (DynamicDataNode)operNode.getChild(0);
        } else if (operNode.getChild(0) instanceof CellDataNode) {
            leftNode = (DynamicDataNode)operNode.getChild(0).getChild(0);
        }
        if (leftNode != null) {
            leftNode.setAssgin(true);
        }
        return leftNode;
    }

    private void collectDataNodes(IASTNode mainNode, List<DynamicDataNode> nodes) {
        for (int i = 0; i < mainNode.childrenSize(); ++i) {
            IASTNode childNode = mainNode.getChild(i);
            if (childNode instanceof DynamicDataNode) {
                DynamicDataNode fmlNode = (DynamicDataNode)childNode;
                nodes.add(fmlNode);
                continue;
            }
            if (childNode.childrenSize() <= 0) continue;
            this.collectDataNodes(childNode, nodes);
        }
    }

    private String creatJdbcDimSqlPart(String tableAlias, TableModelRunInfo tableInfo) {
        StringBuilder sql = new StringBuilder();
        for (int j = 0; j < this.dimensionValueSet.size(); ++j) {
            if (j > 0) {
                sql.append(" and ");
            }
            String dimensionFieldName = DataEngineUtil.getDimensionFieldName(tableInfo, this.dimensionValueSet.getName(j));
            sql.append(tableAlias).append(".").append(dimensionFieldName).append("=?");
        }
        return sql.toString();
    }

    private Object creatNpDimSqlPart(String alias, TableRunInfo tableInfo) {
        return null;
    }
}

