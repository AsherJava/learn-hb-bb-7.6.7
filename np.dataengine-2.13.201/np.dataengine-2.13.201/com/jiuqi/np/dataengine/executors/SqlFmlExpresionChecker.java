/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.ast.IExpression
 *  com.jiuqi.bi.syntax.interpret.Language
 *  com.jiuqi.bi.syntax.interpret.SQLInfoDescr
 *  com.jiuqi.bi.syntax.operator.BinaryOperator
 *  com.jiuqi.bi.syntax.operator.IfThenElse
 *  com.jiuqi.bi.syntax.operator.OperatorNode
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.syntax.parser.ParseException
 *  com.jiuqi.np.period.IPeriodAdapter
 *  com.jiuqi.np.period.PeriodModifier
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 */
package com.jiuqi.np.dataengine.executors;

import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.bi.syntax.interpret.Language;
import com.jiuqi.bi.syntax.interpret.SQLInfoDescr;
import com.jiuqi.bi.syntax.operator.BinaryOperator;
import com.jiuqi.bi.syntax.operator.IfThenElse;
import com.jiuqi.bi.syntax.operator.OperatorNode;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.np.dataengine.QueryParam;
import com.jiuqi.np.dataengine.common.DataEngineUtil;
import com.jiuqi.np.dataengine.common.DataTypesConvert;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.common.QueryField;
import com.jiuqi.np.dataengine.common.QueryTable;
import com.jiuqi.np.dataengine.common.SqlQueryHelper;
import com.jiuqi.np.dataengine.definitions.DefinitionsCache;
import com.jiuqi.np.dataengine.definitions.Formula;
import com.jiuqi.np.dataengine.definitions.TableModelRunInfo;
import com.jiuqi.np.dataengine.exception.ExpressionException;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.executors.SqlCheckItem;
import com.jiuqi.np.dataengine.intf.IMonitor;
import com.jiuqi.np.dataengine.intf.impl.FormulaCheckEventImpl;
import com.jiuqi.np.dataengine.node.DynamicDataNode;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.dataengine.reader.ICheckEventInfoReader;
import com.jiuqi.np.dataengine.reader.JdbcCheckDynamicNodeReader;
import com.jiuqi.np.dataengine.reader.JdbcCheckValueReader;
import com.jiuqi.np.dataengine.reader.JdbcRowIDReader;
import com.jiuqi.np.period.IPeriodAdapter;
import com.jiuqi.np.period.PeriodModifier;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SqlFmlExpresionChecker {
    private static final Logger logger = LoggerFactory.getLogger(SqlFmlExpresionChecker.class);
    private final DimensionValueSet dimensions;
    private String compiledExp;
    private int errorMaxSize;
    private final QueryParam queryParam;
    private final List<Object> args = new ArrayList<Object>();

    public SqlFmlExpresionChecker(DimensionValueSet dimensions, QueryParam queryParam) {
        this.dimensions = dimensions;
        this.queryParam = queryParam;
    }

    public void doCheck(QueryContext qContext, Formula formula, IExpression exp, IMonitor monitor) throws Exception {
        SQLInfoDescr conditionSqlINfo = new SQLInfoDescr(this.queryParam.getDatabase(), true, 0, 0);
        IASTNode mainNode = exp.getChild(0);
        this.compiledExp = exp.interpret((IContext)qContext, Language.FORMULA, (Object)conditionSqlINfo);
        ArrayList<SqlCheckItem> checkItems = new ArrayList<SqlCheckItem>();
        if (mainNode instanceof IfThenElse) {
            IfThenElse ifThenElseNode = (IfThenElse)mainNode;
            IASTNode ifNode = ifThenElseNode.getChild(0);
            String conditionSqlPart = "(" + ifNode.interpret(null, Language.SQL, (Object)conditionSqlINfo) + ")";
            IASTNode thenNode = ifThenElseNode.getChild(1);
            IASTNode elseNode = null;
            if (ifThenElseNode.childrenSize() == 3) {
                elseNode = ifThenElseNode.getChild(2);
            }
            SqlCheckItem thenItem = this.buildSqlItem(qContext, formula, conditionSqlINfo, ifNode, conditionSqlPart, thenNode, exp);
            checkItems.add(thenItem);
            if (elseNode != null) {
                String notConditionSqlPart = "not " + conditionSqlPart;
                SqlCheckItem elseItem = this.buildSqlItem(qContext, formula, conditionSqlINfo, ifNode, notConditionSqlPart, elseNode, exp);
                checkItems.add(elseItem);
            }
        } else {
            SqlCheckItem thenItem = this.buildSqlItem(qContext, formula, conditionSqlINfo, null, null, mainNode, exp);
            checkItems.add(thenItem);
        }
        for (SqlCheckItem item : checkItems) {
            this.checkOneItem(qContext, monitor, item);
        }
    }

    private void checkOneItem(QueryContext qContext, IMonitor monitor, SqlCheckItem checkItem) throws Exception {
        boolean byNpSql = qContext.getExeContext().isUseDnaSql();
        StringBuilder sql = this.buildCheckSql(qContext, byNpSql, checkItem);
        this.checkByJdbcSql(qContext, monitor, checkItem, sql);
        if (monitor.isCancel()) {
            return;
        }
    }

    private void checkByJdbcSql(QueryContext qContext, IMonitor monitor, SqlCheckItem checkItem, StringBuilder sql) throws Exception {
        ArrayList<ICheckEventInfoReader> readers = new ArrayList<ICheckEventInfoReader>();
        Connection connection = this.queryParam.getConnection();
        try (SqlQueryHelper sqlHelper = DataEngineUtil.createSqlQueryHelper();
             ResultSet resultSet = sqlHelper.executeQuery(connection, sql.toString());){
            while (resultSet.next()) {
                this.processRow(qContext, resultSet, readers, checkItem, monitor);
            }
        }
        this.queryParam.closeConnection();
    }

    private void processRow(QueryContext qContext, ResultSet rs, List<ICheckEventInfoReader> readers, SqlCheckItem checkItem, IMonitor monitor) {
        if (readers.isEmpty()) {
            try {
                this.buildCheckEventReaders(qContext, checkItem, rs, readers);
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
        if (monitor.isCancel()) {
            return;
        }
        FormulaCheckEventImpl event = new FormulaCheckEventImpl();
        event.setCompliedFormulaExpression(this.compiledExp);
        event.setFormulaObj(checkItem.getFormula());
        event.setFormulaExpression(checkItem.getFormula().getFormula());
        event.setFormulaMeaning(checkItem.getFormulaMeanning());
        event.setWildcardCol(checkItem.getWildcardCol());
        event.setWildcardRow(checkItem.getWildcardRow());
        for (ICheckEventInfoReader reader : readers) {
            try {
                reader.readToEvent(event);
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
        monitor.error(event);
    }

    private void buildCheckEventReaders(QueryContext qContext, SqlCheckItem checkItem, ResultSet rs, List<ICheckEventInfoReader> readers) throws SyntaxException {
        readers.add(new JdbcCheckValueReader(rs, checkItem.getLeftValueIndex(), checkItem.getLeftNode().getType((IContext)qContext), checkItem.getRightValueIndex()));
        JdbcRowIDReader rowIdReader = new JdbcRowIDReader(rs, checkItem.getMainTable().getTableDimensions());
        rowIdReader.getKeyFields().addAll(checkItem.getKeyFieldIndexes());
        readers.add(rowIdReader);
        for (int i = 0; i < checkItem.getNodeList().size(); ++i) {
            DynamicDataNode node = checkItem.getNodeList().get(i);
            int fieldIndex = i + checkItem.nodeStartIndex;
            JdbcCheckDynamicNodeReader reader = new JdbcCheckDynamicNodeReader(rs, fieldIndex, node.getType((IContext)qContext), node);
            readers.add(reader);
        }
    }

    private SqlCheckItem buildSqlItem(QueryContext qContext, Formula formula, SQLInfoDescr conditionSqlINfo, IASTNode ifNode, String conditionSqlPart, IASTNode thenNode, IExpression source) throws ExpressionException, SyntaxException {
        SqlCheckItem thenItem = new SqlCheckItem();
        source.setWildcardPos(source.getWildcardRow(), source.getWildcardCol());
        if (ifNode != null) {
            this.collectDataNodes(ifNode, thenItem.getNodeInfosByTable());
        }
        this.collectDataNodes(thenNode, thenItem.getNodeInfosByTable());
        if (thenNode instanceof BinaryOperator) {
            OperatorNode operNode = (OperatorNode)thenNode;
            thenItem.setLeftNode(operNode.getChild(0));
            thenItem.setRightNode(operNode.getChild(1));
        } else {
            thenItem.setRightNode(thenNode);
        }
        StringBuilder buffer = new StringBuilder();
        thenItem.setFormulaMeanning(buffer.toString());
        thenItem.setConditionSqlPart(conditionSqlPart);
        String expSql = thenNode.interpret((IContext)qContext, Language.SQL, (Object)conditionSqlINfo);
        thenItem.setCheckSqlPart("not (" + expSql + ")");
        thenItem.setFormula(formula);
        return thenItem;
    }

    private void collectDataNodes(IASTNode mainNode, Map<QueryTable, List<DynamicDataNode>> nodeInfosByTable) {
        for (int i = 0; i < mainNode.childrenSize(); ++i) {
            IASTNode childNode = mainNode.getChild(i);
            if (childNode instanceof DynamicDataNode) {
                DynamicDataNode fmlNode = (DynamicDataNode)childNode;
                QueryTable table = fmlNode.getQueryField().getTable();
                List<DynamicDataNode> nodeList = nodeInfosByTable.get(table);
                if (nodeList == null) {
                    nodeList = new ArrayList<DynamicDataNode>();
                    nodeInfosByTable.put(table, nodeList);
                }
                nodeList.add(fmlNode);
                continue;
            }
            if (childNode.childrenSize() <= 0) continue;
            this.collectDataNodes(childNode, nodeInfosByTable);
        }
    }

    private StringBuilder buildCheckSql(QueryContext qContext, boolean byNpSql, SqlCheckItem checkItem) throws SyntaxException {
        StringBuilder sql = new StringBuilder();
        if (byNpSql) {
            sql.append("define query sqlCheck()\n");
            sql.append("begin\n");
        }
        sql.append(" select ");
        IASTNode leftNode = checkItem.getLeftNode();
        SQLInfoDescr evalSqlINfo = new SQLInfoDescr(this.queryParam.getDatabase(), false, 0, 0);
        QueryTable mainTable = null;
        if (leftNode != null) {
            this.appendValueSql(qContext, sql, leftNode, evalSqlINfo);
            sql.append(" as ").append("leftValue,");
            checkItem.setLeftValueIndex(checkItem.nodeStartIndex);
            ++checkItem.nodeStartIndex;
            if (leftNode instanceof DynamicDataNode) {
                QueryField leftField = ((DynamicDataNode)leftNode).getQueryField();
                mainTable = leftField.getTable();
            } else {
                HashMap<QueryTable, List<DynamicDataNode>> nodeInfosByTable = new HashMap<QueryTable, List<DynamicDataNode>>();
                this.collectDataNodes(leftNode, nodeInfosByTable);
                Iterator iterator = nodeInfosByTable.keySet().iterator();
                if (iterator.hasNext()) {
                    QueryTable table;
                    mainTable = table = (QueryTable)iterator.next();
                }
            }
        }
        checkItem.setMainTable(mainTable);
        IASTNode rightNode = checkItem.getRightNode();
        if (rightNode != null) {
            this.appendValueSql(qContext, sql, rightNode, evalSqlINfo);
            sql.append(" as ").append("rightValue,");
            checkItem.setRightValueIndex(checkItem.nodeStartIndex);
            ++checkItem.nodeStartIndex;
        }
        this.buildTablesSql(qContext, byNpSql, mainTable, sql, checkItem);
        if (byNpSql) {
            sql.append("\nend");
        }
        return sql;
    }

    private void appendValueSql(QueryContext qContext, StringBuilder sql, IASTNode node, SQLInfoDescr evalSqlINfo) throws SyntaxException {
        if (node.getType((IContext)qContext) == 1) {
            sql.append("CASE WHEN ");
            sql.append(node.interpret((IContext)qContext, Language.SQL, (Object)evalSqlINfo));
            sql.append(" THEN 1 ELSE 0 END");
        } else {
            sql.append(node.interpret((IContext)qContext, Language.SQL, (Object)evalSqlINfo));
        }
    }

    private void buildTablesSql(QueryContext qContext, boolean byNpSql, QueryTable mainTable, StringBuilder sql, SqlCheckItem checkItem) throws ParseException {
        StringBuilder fromPart = new StringBuilder();
        ExecutorContext exeContext = qContext.getExeContext();
        DefinitionsCache cache = exeContext.getCache();
        TableModelRunInfo mainTableInfo = cache.getDataModelDefinitionsCache().getTableInfo(mainTable.getTableName());
        List<DynamicDataNode> nodes = checkItem.getNodeInfosByTable().get(mainTable);
        for (int i = 0; i < mainTableInfo.getDimensions().size(); ++i) {
            String dimension = mainTableInfo.getDimensions().get(i);
            String dimensionFieldName = DataEngineUtil.getDimensionFieldName(mainTableInfo, dimension);
            sql.append(mainTable.getAlias()).append(".").append(dimensionFieldName).append(" as ").append(dimension).append(",");
            checkItem.getKeyFieldIndexes().add(checkItem.nodeStartIndex);
            ++checkItem.nodeStartIndex;
        }
        for (DynamicDataNode node : nodes) {
            String fieldName = node.getQueryField().getFieldName();
            sql.append(mainTable.getAlias()).append(".").append(fieldName).append(" as ").append("c_").append(checkItem.nodeStartIndex).append(",");
            if (checkItem.getNodeList().contains((Object)node)) continue;
            checkItem.getNodeList().add(node);
        }
        fromPart.append(mainTable.getTableName());
        if (byNpSql) {
            fromPart.append(" as ");
        } else {
            fromPart.append(" ");
        }
        fromPart.append(mainTable.getAlias());
        for (QueryTable table : checkItem.getNodeInfosByTable().keySet()) {
            if (table.equals(mainTable)) continue;
            TableModelRunInfo tableInfo = cache.getDataModelDefinitionsCache().getTableInfo(table.getTableName());
            List<DynamicDataNode> tableNodes = checkItem.getNodeInfosByTable().get(table);
            for (DynamicDataNode tableNode : tableNodes) {
                String fieldName = tableNode.getQueryField().getFieldName();
                sql.append(table.getAlias()).append(".").append(fieldName).append(" as ").append("c_").append(checkItem.nodeStartIndex).append(",");
                checkItem.getNodeList().add(tableNode);
            }
            if (table.getTableDimensions().equals(mainTable.getTableDimensions())) {
                fromPart.append(" full ");
            }
            fromPart.append(" join ");
            fromPart.append(table.getTableName());
            if (byNpSql) {
                fromPart.append(" as ");
            } else {
                fromPart.append(" ");
            }
            fromPart.append(table.getAlias());
            fromPart.append(" on ");
            boolean needAnd = false;
            PeriodModifier periodModifier = table.getPeriodModifier();
            if (periodModifier != null) {
                ColumnModelDefine dimensionField = tableInfo.getDimensionField("DATATIME");
                PeriodWrapper oldPeriodWrapper = qContext.getPeriodWrapper();
                PeriodWrapper currentPeriodWrapper = new PeriodWrapper(oldPeriodWrapper);
                IPeriodAdapter periodAdapter = exeContext.getPeriodAdapter();
                periodAdapter.modify(currentPeriodWrapper, periodModifier);
                String period = currentPeriodWrapper.toString();
                this.appendToCondition(fromPart, mainTable.getAlias(), dimensionField.getName(), period, 6);
                needAnd = true;
            }
            for (int i = 0; i < table.getTableDimensions().size(); ++i) {
                String dimension = table.getTableDimensions().get(i);
                if (table.getDimensionRestriction() != null && table.getDimensionRestriction().hasValue(dimension)) {
                    ColumnModelDefine dimensionField = tableInfo.getDimensionField(dimension);
                    if (dimensionField == null) continue;
                    if (needAnd) {
                        fromPart.append(" and ");
                    }
                    Object dimValue = table.getDimensionRestriction().getValue(dimension);
                    int dimDataType = DataTypesConvert.fieldTypeToDataType(dimensionField.getColumnType());
                    this.appendToCondition(fromPart, table.getAlias(), dimensionField.getName(), dimValue, dimDataType);
                    needAnd = true;
                    continue;
                }
                if (!mainTable.getTableDimensions().contains(dimension)) continue;
                if (needAnd) {
                    fromPart.append(" and ");
                }
                fromPart.append(mainTable.getAlias()).append(".").append(DataEngineUtil.getDimensionFieldName(mainTableInfo, dimension));
                fromPart.append("=");
                fromPart.append(table.getAlias()).append(".").append(DataEngineUtil.getDimensionFieldName(tableInfo, dimension));
                needAnd = true;
            }
        }
        sql.setLength(sql.length() - 1);
        sql.append(" from ").append((CharSequence)fromPart).append(" where ");
        this.args.clear();
        for (int j = 0; j < this.dimensions.size(); ++j) {
            Object dimValue;
            String dimName = this.dimensions.getName(j);
            ColumnModelDefine dimensionField = mainTableInfo.getDimensionField(dimName);
            if (dimensionField == null) continue;
            if (j > 0) {
                sql.append(" and ");
            }
            if ((dimValue = this.dimensions.getValue(j)) instanceof List) {
                List batchValues = (List)dimValue;
                if (batchValues.size() >= 500) continue;
                sql.append(mainTable.getAlias()).append(".").append(dimensionField.getName()).append(" in(");
                for (Object value : batchValues) {
                    sql.append("?,");
                    this.args.add(value);
                }
                sql.setLength(sql.length() - 1);
                sql.append(")");
                continue;
            }
            sql.append(mainTable.getAlias()).append(".").append(dimensionField.getName()).append("=?");
            this.args.add(dimValue);
        }
        sql.append(" and ").append("(").append(checkItem.getSqlPart()).append(")");
    }

    private void appendToCondition(StringBuilder sql, String tableAlias, String name, Object value, int dataType) {
        sql.append(tableAlias).append(".").append(name);
        sql.append("=");
        this.appendValue(sql, value, dataType);
    }

    private void appendValue(StringBuilder sql, Object value, int dataType) {
        if (dataType == 6) {
            sql.append("'").append(value).append("'");
        } else {
            sql.append(value);
        }
    }

    public int getErrorMaxSize() {
        return this.errorMaxSize;
    }

    public void setErrorMaxSize(int errorMaxSize) {
        this.errorMaxSize = errorMaxSize;
    }
}

