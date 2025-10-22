/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.ast.IExpression
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.syntax.parser.ParseException
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.common.DataEngineConsts
 *  com.jiuqi.np.dataengine.common.DimensionSet
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.definitions.TableRunInfo
 *  com.jiuqi.np.dataengine.exception.IncorrectQueryException
 *  com.jiuqi.np.dataengine.intf.IDataQuery
 *  com.jiuqi.np.definition.facade.FieldDefine
 */
package com.jiuqi.nr.data.engine.summary.exe.impl.group;

import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.common.DataEngineConsts;
import com.jiuqi.np.dataengine.common.DimensionSet;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.definitions.TableRunInfo;
import com.jiuqi.np.dataengine.exception.IncorrectQueryException;
import com.jiuqi.np.dataengine.intf.IDataQuery;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.nr.data.engine.summary.define.ISumBaseZBProvider;
import com.jiuqi.nr.data.engine.summary.exe.SumCell;
import com.jiuqi.nr.data.engine.summary.exe.impl.CellsSumExecuter;
import com.jiuqi.nr.data.engine.summary.exe.impl.group.GroupSumCell;
import com.jiuqi.nr.data.engine.summary.exe.impl.group.GroupSumQueryTable;
import com.jiuqi.nr.data.engine.summary.exe.impl.group.SumGroup;
import com.jiuqi.nr.data.engine.summary.parse.SumNode;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GroupCellsSumExecuter
extends CellsSumExecuter {
    private static final Logger logger = LoggerFactory.getLogger(GroupCellsSumExecuter.class);
    private List<CellsSumExecuter> executers = new ArrayList<CellsSumExecuter>();
    private SumNode groupField;

    @Override
    public void run() {
        this.context.setBaseDefineFactory(this.baseDefineFactory);
        this.context.setExecutorContext(this.executorContext);
        this.context.setLogger(logger);
        ISumBaseZBProvider zbProvider = this.baseDefineFactory.getZBProvider();
        ArrayList<SumCell> constValueCells = new ArrayList<SumCell>();
        List<GroupSumQueryTable> tables = this.splitCellsByTable(constValueCells);
        try (Connection conn = this.dataSource.getConnection();){
            this.context.setConnection(conn);
            this.initDestDataRow(constValueCells);
            for (GroupSumQueryTable sumQueryTable : tables) {
                sumQueryTable.prepare();
                List<GroupSumCell> cells = sumQueryTable.getSumGroupList().get(0).getGroupSumCells();
                StringBuilder querySql = this.buildQuerySql(conn, zbProvider, cells, sumQueryTable);
                if (querySql == null) continue;
                this.debugLogger(querySql);
                try {
                    PreparedStatement ps = conn.prepareStatement(querySql.toString());
                    Throwable throwable = null;
                    try {
                        ResultSet rs = ps.executeQuery();
                        Throwable throwable2 = null;
                        try {
                            sumQueryTable.runGroupSum(this.context, conn, rs, this.row);
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
            this.updator.commitChanges(true);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    private void initDestDataRow(List<SumCell> constValueCells) throws IncorrectQueryException, Exception {
        IDataQuery query = this.dataAccessProvider.newDataQuery();
        for (CellsSumExecuter executer : this.executers) {
            for (SumCell cell : executer.getCells()) {
                FieldDefine destField = cell.getDestField();
                query.addColumn(destField);
            }
        }
        query.setMasterKeys(this.exeInfo.getDestDimension());
        this.updator = query.openForUpdate(this.executorContext);
        DimensionValueSet rowKey = new DimensionValueSet(this.exeInfo.getDestDimension());
        this.row = this.updator.addModifiedRow(rowKey);
        for (SumCell constValueCell : constValueCells) {
            this.row.setValue(constValueCell.getDestField(), constValueCell.getValue());
        }
    }

    private void debugLogger(StringBuilder querySql) {
        if (DataEngineConsts.DATA_ENGINE_DEBUG) {
            StringBuilder buff = new StringBuilder();
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

    private List<GroupSumQueryTable> splitCellsByTable(List<SumCell> constValueCells) {
        HashMap<GroupSumQueryTable, GroupSumQueryTable> tables = new HashMap<GroupSumQueryTable, GroupSumQueryTable>();
        for (CellsSumExecuter executer : this.executers) {
            for (SumCell cell : executer.getCells()) {
                SumGroup group;
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
                GroupSumQueryTable key = new GroupSumQueryTable(cellTableName, cell.getPeriodOffSet(), this.groupField);
                GroupSumQueryTable table = (GroupSumQueryTable)tables.get(key);
                if (table == null) {
                    table = key;
                    tables.put(key, table);
                }
                if ((group = table.getSumGroups().get(executer.getKey())) == null) {
                    group = new SumGroup(executer.getConditionExp(), executer.getKey());
                    table.getSumGroups().put(executer.getKey(), group);
                }
                GroupSumCell groupSumCell = new GroupSumCell(cell, group.getGroupSumCells().size() + 1);
                group.getGroupSumCells().add(groupSumCell);
            }
        }
        return new ArrayList<GroupSumQueryTable>(tables.values());
    }

    private String getTableName(FieldDefine fieldDefine) {
        try {
            return this.executorContext.getCache().getDataModelDefinitionsCache().getTableName(fieldDefine);
        }
        catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<CellsSumExecuter> getExecuters() {
        return this.executers;
    }

    public SumNode getGroupField() {
        return this.groupField;
    }

    public void setGroupField(SumNode groupField) {
        this.groupField = groupField;
    }
}

