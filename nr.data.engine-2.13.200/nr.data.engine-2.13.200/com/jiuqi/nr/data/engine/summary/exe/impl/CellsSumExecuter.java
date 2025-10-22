/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.ast.IExpression
 *  com.jiuqi.bi.syntax.function.FunctionNode
 *  com.jiuqi.bi.syntax.interpret.ISQLInfo
 *  com.jiuqi.bi.syntax.interpret.Language
 *  com.jiuqi.bi.syntax.operator.And
 *  com.jiuqi.bi.syntax.operator.IfThenElse
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.syntax.parser.ParseException
 *  com.jiuqi.bi.syntax.reportparser.ReportFormulaParser
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.IDataAccessProvider
 *  com.jiuqi.np.dataengine.common.DataEngineConsts
 *  com.jiuqi.np.dataengine.common.DimensionSet
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.common.TempAssistantTable
 *  com.jiuqi.np.dataengine.definitions.DataModelDefinitionsCache
 *  com.jiuqi.np.dataengine.definitions.TableModelRunInfo
 *  com.jiuqi.np.dataengine.definitions.TableRunInfo
 *  com.jiuqi.np.dataengine.exception.IncorrectQueryException
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IDataQuery
 *  com.jiuqi.np.dataengine.intf.IDataRow
 *  com.jiuqi.np.dataengine.intf.IDataUpdator
 *  com.jiuqi.np.definition.common.FieldType
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.nvwa.dataengine.common.Convert
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 */
package com.jiuqi.nr.data.engine.summary.exe.impl;

import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.bi.syntax.function.FunctionNode;
import com.jiuqi.bi.syntax.interpret.ISQLInfo;
import com.jiuqi.bi.syntax.interpret.Language;
import com.jiuqi.bi.syntax.operator.And;
import com.jiuqi.bi.syntax.operator.IfThenElse;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.bi.syntax.reportparser.ReportFormulaParser;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.dataengine.common.DataEngineConsts;
import com.jiuqi.np.dataengine.common.DimensionSet;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.common.TempAssistantTable;
import com.jiuqi.np.dataengine.definitions.DataModelDefinitionsCache;
import com.jiuqi.np.dataengine.definitions.TableModelRunInfo;
import com.jiuqi.np.dataengine.definitions.TableRunInfo;
import com.jiuqi.np.dataengine.exception.IncorrectQueryException;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IDataQuery;
import com.jiuqi.np.dataengine.intf.IDataRow;
import com.jiuqi.np.dataengine.intf.IDataUpdator;
import com.jiuqi.np.definition.common.FieldType;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.nr.data.engine.summary.common.SumEngineConsts;
import com.jiuqi.nr.data.engine.summary.define.ISumBaseDefineFactory;
import com.jiuqi.nr.data.engine.summary.define.ISumBaseZBProvider;
import com.jiuqi.nr.data.engine.summary.exe.SumCell;
import com.jiuqi.nr.data.engine.summary.exe.impl.SumDataSetBuilder;
import com.jiuqi.nr.data.engine.summary.exe.impl.SumExeInfo;
import com.jiuqi.nr.data.engine.summary.exe.impl.SumQueryTable;
import com.jiuqi.nr.data.engine.summary.exe.impl.group.GroupSumQueryTable;
import com.jiuqi.nr.data.engine.summary.parse.SumContext;
import com.jiuqi.nr.data.engine.summary.parse.SumDBSQLInfo;
import com.jiuqi.nr.data.engine.summary.parse.SumNode;
import com.jiuqi.nr.data.engine.summary.parse.func.IStatistic;
import com.jiuqi.nvwa.dataengine.common.Convert;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CellsSumExecuter
extends Thread {
    private static final Logger logger = LoggerFactory.getLogger(CellsSumExecuter.class);
    protected SumContext context = new SumContext();
    protected DataSource dataSource;
    protected ReportFormulaParser parser;
    protected ISumBaseDefineFactory baseDefineFactory;
    protected ExecutorContext executorContext;
    protected SumExeInfo exeInfo;
    protected IDataUpdator updator;
    protected IDataRow row;
    protected IDataAccessProvider dataAccessProvider;
    protected Map<String, TempAssistantTable> tempAssistantTables;
    private String condition;
    private IExpression conditionExp;
    private String key;

    @Override
    public void run() {
        this.context.setBaseDefineFactory(this.baseDefineFactory);
        this.context.setExecutorContext(this.executorContext);
        String filter = this.exeInfo.getFilter();
        if (StringUtils.isNotEmpty((String)this.condition)) {
            filter = StringUtils.isNotEmpty((String)filter) ? filter + " and (" + this.condition + ")" : this.condition;
        }
        this.exeInfo.setFilter(filter);
        ISumBaseZBProvider zbProvider = this.baseDefineFactory.getZBProvider();
        HashMap<SumQueryTable, List<SumCell>> cellsByTableMap = new HashMap<SumQueryTable, List<SumCell>>();
        List<SumCell> constValueCells = this.splitCellsByTable(cellsByTableMap);
        try (Connection conn = this.dataSource.getConnection();){
            FieldDefine orderField = this.exeInfo.getOrderField();
            boolean needCommit = this.initDestDataRow(constValueCells, orderField);
            for (SumQueryTable sumQueryTable : cellsByTableMap.keySet()) {
                List cells = (List)cellsByTableMap.get(sumQueryTable);
                StringBuilder querySql = this.buildQuerySql(conn, zbProvider, cells, sumQueryTable);
                if (querySql == null) continue;
                this.debugLogger(cells, querySql);
                try {
                    PreparedStatement ps = conn.prepareStatement(querySql.toString());
                    Throwable throwable = null;
                    try {
                        ResultSet rs = ps.executeQuery();
                        Throwable throwable2 = null;
                        try {
                            if (!rs.next()) continue;
                            this.setResultToUpdator(rs, cells, this.row);
                        }
                        catch (Throwable throwable3) {
                            throwable2 = throwable3;
                            throw throwable3;
                        }
                        finally {
                            if (rs == null) continue;
                            if (throwable2 != null) {
                                try {
                                    rs.close();
                                }
                                catch (Throwable throwable4) {
                                    throwable2.addSuppressed(throwable4);
                                }
                                continue;
                            }
                            rs.close();
                        }
                    }
                    catch (Throwable throwable5) {
                        throwable = throwable5;
                        throw throwable5;
                    }
                    finally {
                        if (ps == null) continue;
                        if (throwable != null) {
                            try {
                                ps.close();
                            }
                            catch (Throwable throwable6) {
                                throwable.addSuppressed(throwable6);
                            }
                            continue;
                        }
                        ps.close();
                    }
                }
                catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
            if (needCommit) {
                this.updator.commitChanges(true);
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    protected void debugLogger(List<SumCell> cells, StringBuilder querySql) {
        if (DataEngineConsts.DATA_ENGINE_DEBUG) {
            StringBuilder buff = new StringBuilder();
            buff.append("sumCells:\n");
            for (SumCell cell : cells) {
                buff.append(cell).append("\n");
            }
            if (this.exeInfo.getFilter() != null) {
                buff.append("filter: ").append(this.exeInfo.getFilter()).append("\n");
            }
            buff.append("srcDimension: ").append(this.exeInfo.getSrcDimension()).append("\n");
            buff.append("destDimension: ").append(this.exeInfo.getDestDimension()).append("\n");
            buff.append("sumSql:\n");
            buff.append((CharSequence)querySql);
            logger.debug(buff.toString());
        }
    }

    protected boolean initDestDataRow(List<SumCell> constValueCells, FieldDefine orderField) throws Exception {
        boolean needCommit = false;
        if (this.updator != null) {
            if (this.row == null) {
                DimensionValueSet rowKey = new DimensionValueSet(this.exeInfo.getDestDimension());
                rowKey.setValue("RECORDKEY", (Object)UUID.randomUUID());
                this.row = this.updator.addInsertedRow(rowKey);
            }
        } else {
            needCommit = true;
            IDataQuery query = this.dataAccessProvider.newDataQuery();
            for (SumCell cell : this.getCells()) {
                FieldDefine destField = cell.getDestField();
                query.addColumn(destField);
            }
            if (orderField != null) {
                query.addColumn(orderField);
            }
            query.setMasterKeys(this.exeInfo.getDestDimension());
            this.updator = query.openForUpdate(this.executorContext);
            DimensionValueSet rowKey = new DimensionValueSet(this.exeInfo.getDestDimension());
            if (orderField == null) {
                this.row = this.updator.addModifiedRow(rowKey);
            } else {
                rowKey.setValue("RECORDKEY", (Object)UUID.randomUUID());
                this.row = this.updator.addInsertedRow(rowKey);
            }
        }
        if (orderField != null) {
            this.row.setValue(orderField, (Object)this.getCells().get(0).getRow());
        }
        for (SumCell constValueCell : constValueCells) {
            this.row.setValue(constValueCell.getDestField(), constValueCell.getValue());
        }
        return needCommit;
    }

    protected void setResultToUpdator(ResultSet rs, List<SumCell> cells, IDataRow row) throws IncorrectQueryException, SQLException {
        for (SumCell cell : cells) {
            if (rs == null) continue;
            Object value = rs.getObject(cell.getAlias());
            if (cell.getDivider() != 0.0) {
                double doubleValue = Convert.toDouble((Object)value);
                value = doubleValue / cell.getDivider();
            }
            row.setValue(cell.getDestField(), value);
        }
    }

    /*
     * WARNING - void declaration
     */
    protected StringBuilder buildQuerySql(Connection connection, ISumBaseZBProvider zbProvider, List<? extends SumCell> tableCells, SumQueryTable sumQueryTable) throws Exception {
        void var7_10;
        boolean needQuery = false;
        for (SumCell sumCell : tableCells) {
            if (sumCell.getSrcField() == null && sumCell.getSumExp() == null && sumCell.getCustomizeFormula() == null) continue;
            needQuery = true;
        }
        String tableName = sumQueryTable.getTableName();
        if (!needQuery) {
            return null;
        }
        Object var7_8 = null;
        if (sumQueryTable instanceof GroupSumQueryTable) {
            SumNode sumNode = ((GroupSumQueryTable)sumQueryTable).getGroupField();
        }
        String mainTableName = this.baseDefineFactory.getRelationInfoProvider().findMainTableName(this.executorContext, tableName);
        this.context.setMainTable(mainTableName);
        this.context.getTables().clear();
        StringBuilder querySql = new StringBuilder();
        querySql.append("select ");
        ExecutorContext executorContext = this.context.getExecutorContext();
        TableModelRunInfo tableInfo = executorContext.getCache().getDataModelDefinitionsCache().getTableInfo(tableName);
        this.context.getEntityTableNames().clear();
        for (ColumnModelDefine field : tableInfo.getDimFields()) {
            if (field.getReferColumnID() == null) continue;
            ColumnModelDefine referColumn = executorContext.getCache().getDataModelDefinitionsCache().findField(field.getReferColumnID());
            TableModelDefine tableModel = executorContext.getCache().getDataModelDefinitionsCache().getTableModel(referColumn);
            this.context.getEntityTableNames().add(tableModel.getName());
        }
        this.appendSumColumns(connection, querySql, tableCells);
        if (var7_10 != null) {
            this.context.addNode((SumNode)var7_10, var7_10.getTable().getTableName());
            querySql.append(",").append("t.").append(var7_10.getZBAlias()).append(" as groupKey ");
        }
        SumDBSQLInfo dbInfo = new SumDBSQLInfo(connection, true, false);
        SumDataSetBuilder dataSetBuilder = new SumDataSetBuilder(executorContext, (ISQLInfo)dbInfo, this.exeInfo.getSrcDimension(), sumQueryTable, this.baseDefineFactory.getRelationInfoProvider(), connection);
        dataSetBuilder.setTempAssistantTables(this.tempAssistantTables);
        String filterCondition = null;
        if (this.exeInfo.getFilter() != null && this.exeInfo.getFilter().length() > 0) {
            try {
                IExpression filterExpression = this.parser.parseCond(this.exeInfo.getFilter(), (IContext)this.context);
                filterCondition = filterExpression.interpret((IContext)this.context, Language.SQL, (Object)dbInfo);
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
        dataSetBuilder.setFilter(filterCondition);
        dataSetBuilder.getNodeMap().putAll(this.context.getTables());
        querySql.append(" from (").append(dataSetBuilder.buildDataSetSql()).append(") t");
        if (var7_10 != null) {
            querySql.append(" group by t.").append(var7_10.getZBAlias());
        }
        return querySql;
    }

    private void appendSumColumns(Connection connection, StringBuilder querySql, List<? extends SumCell> tableCells) throws ParseException {
        DataModelDefinitionsCache dataModelDefinitionsCache = this.executorContext.getCache().getDataModelDefinitionsCache();
        for (SumCell sumCell : tableCells) {
            String sumTypeName;
            String columnSql = null;
            Object colConditionExp = null;
            String colConditionSql = null;
            boolean sumByType = true;
            try {
                String condition = sumCell.getCondition();
                if (condition != null && condition.length() > 0) {
                    colConditionExp = this.parser.parseCond(condition, (IContext)this.context);
                }
                if (sumCell.getSrcField() != null) {
                    ColumnModelDefine columnModel = dataModelDefinitionsCache.getColumnModel(sumCell.getSrcField());
                    TableModelDefine tableModel = dataModelDefinitionsCache.getTableModel(columnModel);
                    SumNode node = new SumNode(null, columnModel);
                    this.context.addNode(node, tableModel.getName());
                    columnSql = "t." + node.getZBAlias();
                } else if (sumCell.getSumExp() != null) {
                    FunctionNode funcNode;
                    IExpression colEvalExpression = this.parser.parseEval(sumCell.getSumExp(), (IContext)this.context);
                    IASTNode root = colEvalExpression.getChild(0);
                    if (root instanceof IfThenElse) {
                        IASTNode ifNode = root.getChild(0);
                        colEvalExpression = root.getChild(1);
                        colConditionExp = colConditionExp != null ? new And(null, (IASTNode)colConditionExp, ifNode) : ifNode;
                    } else if (root instanceof FunctionNode && (funcNode = (FunctionNode)root).getDefine() instanceof IStatistic) {
                        sumByType = false;
                    }
                    columnSql = colEvalExpression.interpret((IContext)this.context, Language.SQL, (Object)new SumDBSQLInfo(connection, false, true));
                }
                if (columnSql == null) continue;
                if (colConditionExp != null) {
                    colConditionSql = colConditionExp.interpret((IContext)this.context, Language.SQL, (Object)new SumDBSQLInfo(connection, true, true));
                }
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
            if (colConditionSql != null) {
                if (sumCell.getSumType() == SumEngineConsts.SumType.COUNT.getValue()) {
                    querySql.append(SumEngineConsts.SumType.SUM.getName()).append("(").append(" case when ").append(colConditionSql).append(" then 1 else 0 end)");
                } else {
                    String string = sumTypeName = sumByType ? this.getSumTypeName(sumCell) : null;
                    if (sumTypeName != null) {
                        querySql.append(sumTypeName).append("(");
                        querySql.append(" case when ").append(colConditionSql).append(" then ").append(columnSql).append(" else 0 end)");
                    } else {
                        querySql.append(" case when ").append(colConditionSql).append(" then ").append(columnSql).append(" else 0 end");
                    }
                }
            } else {
                String string = sumTypeName = sumByType ? this.getSumTypeName(sumCell) : null;
                if (sumTypeName != null) {
                    querySql.append(sumTypeName).append("(").append(columnSql).append(")");
                } else {
                    querySql.append(columnSql);
                }
            }
            querySql.append(" as ").append(sumCell.getAlias()).append(",");
        }
        querySql.setLength(querySql.length() - 1);
    }

    protected String getSumTypeName(SumCell tableCell) {
        FieldType fieldDataType;
        int sumType = tableCell.getSumType();
        if (sumType == SumEngineConsts.SumType.UNKNOWN.getValue()) {
            return "min";
        }
        String name = SumEngineConsts.SumType.valueOf(sumType).getName();
        if (tableCell.getSrcField() != null && (sumType == SumEngineConsts.SumType.SUM.getValue() || sumType == SumEngineConsts.SumType.AVG.getValue()) && (fieldDataType = tableCell.getSrcField().getType()) != FieldType.FIELD_TYPE_FLOAT && fieldDataType != FieldType.FIELD_TYPE_INTEGER && fieldDataType != FieldType.FIELD_TYPE_DECIMAL) {
            name = "min";
        }
        return name;
    }

    private List<SumCell> splitCellsByTable(Map<SumQueryTable, List<SumCell>> cellsByTableMap) {
        ArrayList<SumCell> constValueCells = new ArrayList<SumCell>();
        for (SumCell cell : this.getCells()) {
            if (cell.getValue() != null) {
                constValueCells.add(cell);
                continue;
            }
            String cellTableName = null;
            if (cell.getSrcField() != null) {
                cellTableName = this.getTableName(cell.getSrcField());
            } else if (cell.getSumExp() != null) {
                this.context.getTables().clear();
                try {
                    IExpression parseEval = this.parser.parseEval(cell.getSumExp(), (IContext)this.context);
                    cell.setParsedExp((IASTNode)parseEval);
                    if (this.context.getTables().isEmpty() && !StringUtils.isEmpty((String)this.exeInfo.getFilter())) {
                        this.parser.parseCond(cell.getSumExp(), (IContext)this.context);
                    }
                    if (!this.context.getTables().isEmpty()) {
                        DimensionSet mainTableDimensions = null;
                        for (String tableName : this.context.getTables().keySet()) {
                            TableRunInfo tableInfo = this.executorContext.getCache().getDataDefinitionsCache().getTableInfo(tableName);
                            if (cellTableName != null && tableInfo.getDimensions().size() <= mainTableDimensions.size()) continue;
                            cellTableName = tableName;
                            mainTableDimensions = tableInfo.getDimensions();
                        }
                    }
                }
                catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
            if (cellTableName == null) continue;
            SumQueryTable sumQueryTable = new SumQueryTable(cellTableName, cell.getPeriodOffSet());
            List<SumCell> tableCells = cellsByTableMap.get(sumQueryTable);
            if (tableCells == null) {
                tableCells = new ArrayList<SumCell>();
                cellsByTableMap.put(sumQueryTable, tableCells);
            }
            tableCells.add(cell);
        }
        return constValueCells;
    }

    public void setBaseDefineFactory(ISumBaseDefineFactory baseDefineFactory) {
        this.baseDefineFactory = baseDefineFactory;
    }

    public List<SumCell> getCells() {
        return this.exeInfo.getCells();
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void setExeInfo(SumExeInfo exeInfo) {
        this.exeInfo = exeInfo;
    }

    public void setExecutorContext(ExecutorContext executorContext) {
        this.executorContext = executorContext;
    }

    public void setUpdator(IDataUpdator updator) {
        this.updator = updator;
    }

    public void setDataAccessProvider(IDataAccessProvider dataAccessProvider) {
        this.dataAccessProvider = dataAccessProvider;
    }

    public void setRow(IDataRow row) {
        this.row = row;
    }

    public void setTempAssistantTables(Map<String, TempAssistantTable> tempAssistantTables) {
        this.tempAssistantTables = tempAssistantTables;
    }

    private String getTableName(FieldDefine fieldDefine) {
        if (this.executorContext == null) {
            return null;
        }
        String tableName = null;
        try {
            tableName = this.executorContext.getCache().getDataModelDefinitionsCache().getTableName(fieldDefine);
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
        return tableName;
    }

    public void setParser(ReportFormulaParser parser) {
        this.parser = parser;
    }

    public SumExeInfo getExeInfo() {
        return this.exeInfo;
    }

    public String getCondition() {
        return this.condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public IExpression getConditionExp() {
        return this.conditionExp;
    }

    public void setConditionExp(IExpression conditionExp) {
        this.conditionExp = conditionExp;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}

