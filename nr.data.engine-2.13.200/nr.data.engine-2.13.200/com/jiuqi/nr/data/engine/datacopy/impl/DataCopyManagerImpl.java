/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.database.DatabaseManager
 *  com.jiuqi.bi.database.IDatabase
 *  com.jiuqi.bi.database.sql.loader.ITableLoader
 *  com.jiuqi.bi.database.sql.loader.LoadFieldMap
 *  com.jiuqi.bi.database.sql.model.ISQLField
 *  com.jiuqi.bi.database.sql.model.ISQLTable
 *  com.jiuqi.bi.database.sql.model.tables.InnerTable
 *  com.jiuqi.bi.database.sql.model.tables.SimpleTable
 *  com.jiuqi.bi.syntax.parser.ParseException
 *  com.jiuqi.np.dataengine.IDataAccessProvider
 *  com.jiuqi.np.dataengine.common.DataEngineUtil
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.definitions.DataModelDefinitionsCache
 *  com.jiuqi.np.dataengine.definitions.DefinitionsCache
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IDataAssist
 *  com.jiuqi.np.dataengine.intf.IMonitor
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.nr.common.util.DimensionValueSetUtil
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DataTableType
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 *  com.jiuqi.nvwa.definition.common.ColumnModelType
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  javax.annotation.Resource
 *  org.apache.commons.lang3.StringUtils
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.nr.data.engine.datacopy.impl;

import com.jiuqi.bi.database.DatabaseManager;
import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.bi.database.sql.loader.ITableLoader;
import com.jiuqi.bi.database.sql.loader.LoadFieldMap;
import com.jiuqi.bi.database.sql.model.ISQLField;
import com.jiuqi.bi.database.sql.model.ISQLTable;
import com.jiuqi.bi.database.sql.model.tables.InnerTable;
import com.jiuqi.bi.database.sql.model.tables.SimpleTable;
import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.dataengine.common.DataEngineUtil;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.definitions.DataModelDefinitionsCache;
import com.jiuqi.np.dataengine.definitions.DefinitionsCache;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IDataAssist;
import com.jiuqi.np.dataengine.intf.IMonitor;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.nr.common.util.DimensionValueSetUtil;
import com.jiuqi.nr.data.engine.datacopy.DataCopyManager;
import com.jiuqi.nr.data.engine.datacopy.param.DataCopyParam;
import com.jiuqi.nr.data.engine.util.DataEngineEntityUtil;
import com.jiuqi.nr.data.engine.util.SubDataBaseUtil;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DataTableType;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nvwa.definition.common.ColumnModelType;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class DataCopyManagerImpl
implements DataCopyManager {
    private static final Logger logger = LoggerFactory.getLogger(DataCopyManagerImpl.class);
    @Resource
    private IRunTimeViewController runTimeViewController;
    @Resource
    private IDataDefinitionRuntimeController dataRunTimeController;
    @Resource
    private IDataAccessProvider dataAccessProvider;
    @Resource
    private JdbcTemplate jdbcTemplate;
    @Autowired
    IDataDefinitionRuntimeController npRunController;
    @Autowired
    private DataEngineEntityUtil d2eUtil;
    @Autowired
    private DataModelService dataModelService;
    @Autowired
    private IRuntimeDataSchemeService runtimeDataSchemeService;
    private IDatabase database;
    @Autowired
    SubDataBaseUtil subDataBaseUtil;
    private static final DecimalFormat df = new DecimalFormat("#.00");
    private static Double startProgress = 0.2;
    private static Double endProgress = 0.95;
    private static final String PERIOD_ZB = "DATATIME";
    private static final String ADJUST_ZB = "ADJUST";
    private static final String BIZKEYORDER = "BIZKEYORDER";

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void dataCopy(List<String> formKeys, DimensionValueSet dimValueSet, String oldTimeZone, String newTimeZone, IMonitor iMonitor) {
        iMonitor.onProgress(startProgress.doubleValue());
        iMonitor.message("\u5f00\u59cb\u590d\u5236\u65f6\u671f\u6570\u636e", (Object)this);
        iMonitor.start();
        List<DataTable> dataTables = this.getTableKeysByForm(formKeys);
        DimensionValueSet sourceDim = new DimensionValueSet();
        sourceDim.setValue(PERIOD_ZB, (Object)oldTimeZone);
        Connection conn = null;
        try {
            conn = this.jdbcTemplate.getDataSource().getConnection();
            this.database = DatabaseManager.getInstance().findDatabaseByConnection(conn);
            if (!dataTables.isEmpty()) {
                if (this.useSQL()) {
                    this.executeBySQl(null, dataTables, conn, dimValueSet, sourceDim, iMonitor);
                } else {
                    this.execute(null, dataTables, conn, dimValueSet, sourceDim, iMonitor);
                }
            }
        }
        catch (Exception e) {
            iMonitor.error("\u83b7\u53d6\u6570\u636e\u5e93\u8fde\u63a5\u5931\u8d25\uff01", (Object)this);
            iMonitor.exception(e);
        }
        finally {
            if (null != conn) {
                try {
                    conn.close();
                }
                catch (SQLException e) {
                    iMonitor.error("\u6570\u636e\u5e93\u8fde\u63a5\u5173\u95ed\u5f02\u5e38", (Object)this);
                    iMonitor.exception((Exception)e);
                }
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void dataCopy(DataCopyParam dataCopyParam, IMonitor iMonitor) {
        String taskKey = dataCopyParam.getTaskKey();
        DimensionValueSet currDimensionValueSet = dataCopyParam.getCurrDimValueSet();
        DimensionValueSet sourceDim = dataCopyParam.getSourceDimValueSet();
        List<String> formKeys = dataCopyParam.getFormKeys();
        iMonitor.onProgress(startProgress.doubleValue());
        iMonitor.message("\u5f00\u59cb\u590d\u5236\u65f6\u671f\u6570\u636e", (Object)this);
        iMonitor.start();
        List<DataTable> dataTables = this.getTableKeysByForm(formKeys);
        Connection conn = null;
        try {
            conn = this.jdbcTemplate.getDataSource().getConnection();
            this.database = DatabaseManager.getInstance().findDatabaseByConnection(conn);
            if (!dataTables.isEmpty()) {
                if (this.useSQL()) {
                    this.executeBySQl(taskKey, dataTables, conn, currDimensionValueSet, sourceDim, iMonitor);
                } else {
                    this.execute(taskKey, dataTables, conn, currDimensionValueSet, sourceDim, iMonitor);
                }
            }
        }
        catch (Exception e) {
            iMonitor.error("\u83b7\u53d6\u6570\u636e\u5e93\u8fde\u63a5\u5931\u8d25\uff01", (Object)this);
            iMonitor.exception(e);
        }
        finally {
            if (null != conn) {
                try {
                    conn.close();
                }
                catch (SQLException e) {
                    iMonitor.error("\u6570\u636e\u5e93\u8fde\u63a5\u5173\u95ed\u5f02\u5e38", (Object)this);
                    iMonitor.exception((Exception)e);
                }
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void dataCopy(DataCopyParam dataCopyParam, List<DataRegionDefine> noFilterFloatRegionDefines, IMonitor iMonitor, double startProgress, double endProgress) {
        String taskKey = dataCopyParam.getTaskKey();
        DimensionValueSet currDimensionValueSet = dataCopyParam.getCurrDimValueSet();
        DimensionValueSet sourceDim = dataCopyParam.getSourceDimValueSet();
        DataCopyManagerImpl.startProgress = startProgress;
        DataCopyManagerImpl.endProgress = endProgress;
        iMonitor.onProgress(startProgress);
        iMonitor.message("\u5f00\u59cb\u590d\u5236\u65f6\u671f\u6570\u636e", (Object)this);
        iMonitor.start();
        List<DataTable> noRegionFilterDataTables = this.getTableKeysByRegion(noFilterFloatRegionDefines);
        Connection conn = null;
        try {
            conn = this.jdbcTemplate.getDataSource().getConnection();
            this.database = DatabaseManager.getInstance().findDatabaseByConnection(conn);
            if (!noRegionFilterDataTables.isEmpty()) {
                if (this.useSQL()) {
                    this.executeBySQl(taskKey, noRegionFilterDataTables, conn, currDimensionValueSet, sourceDim, iMonitor);
                } else {
                    this.execute(taskKey, noRegionFilterDataTables, conn, currDimensionValueSet, sourceDim, iMonitor);
                }
            }
        }
        catch (Exception e) {
            iMonitor.error("\u83b7\u53d6\u6570\u636e\u5e93\u8fde\u63a5\u5931\u8d25\uff01", (Object)this);
            iMonitor.exception(e);
        }
        finally {
            if (null != conn) {
                try {
                    conn.close();
                }
                catch (SQLException e) {
                    iMonitor.error("\u6570\u636e\u5e93\u8fde\u63a5\u5173\u95ed\u5f02\u5e38", (Object)this);
                    iMonitor.exception((Exception)e);
                }
            }
        }
    }

    private void execute(String taskKey, List<DataTable> dataTables, Connection conn, DimensionValueSet dimValueSet, DimensionValueSet sourceDim, IMonitor iMonitor) throws ParseException {
        Double process = (endProgress - startProgress) / (double)dataTables.size();
        ExecutorContext executorContext = new ExecutorContext(this.dataRunTimeController);
        DefinitionsCache cache = new DefinitionsCache(executorContext);
        Integer in = 0;
        while (in < dataTables.size()) {
            TableModelDefine tableModel = null;
            DataTable dataTable = null;
            try {
                dataTable = dataTables.get(in);
                ITableLoader mergeLoader = this.database.createMergeLoader(conn, false);
                ArrayList<ISQLField> desFields = new ArrayList<ISQLField>();
                ArrayList<ISQLField> srcFields = new ArrayList<ISQLField>();
                DataModelDefinitionsCache tableCache = cache.getDataModelDefinitionsCache();
                List allColumns = tableCache.getColumnModelFinder().getAllColumnModelsByTableKey(executorContext, dataTable.getKey());
                Map<String, List<ColumnModelDefine>> allfieldsMap = allColumns.stream().collect(Collectors.groupingBy(e -> e.getTableID()));
                for (String tableId : allfieldsMap.keySet()) {
                    List<ColumnModelDefine> allFieldsInTable = allfieldsMap.get(tableId);
                    tableModel = this.dataModelService.getTableModelDefineById(tableId);
                    String sql = this.createSqlForTimeZone(taskKey, allFieldsInTable, dimValueSet, desFields, srcFields, mergeLoader, sourceDim, tableCache, false);
                    if (null == sql) {
                        iMonitor.error("\u672a\u627e\u5230\u65f6\u671f\u6307\u6807!", (Object)this);
                        return;
                    }
                    tableModel = tableCache.getTableModel(allFieldsInTable.get(0));
                    String tableName = this.getRealTableName(taskKey, tableModel.getName());
                    InnerTable sourceTable = new InnerTable(sql);
                    srcFields.forEach(field -> sourceTable.addField(field));
                    mergeLoader.setSourceTable((ISQLTable)sourceTable);
                    sourceTable.setAlias(this.getRealTableName(taskKey, tableName));
                    List fieldMaps = mergeLoader.getFieldMaps();
                    List<String> keyFields = Arrays.asList(tableModel.getBizKeys().split(";"));
                    List keys = allFieldsInTable.stream().filter(key -> keyFields.contains(key.getID())).map(ColumnModelDefine::getName).collect(Collectors.toList());
                    Integer index = 0;
                    while (index < desFields.size()) {
                        fieldMaps.add(new LoadFieldMap((ISQLField)srcFields.get(index), (ISQLField)desFields.get(index), keys.contains(((ISQLField)srcFields.get(index)).fieldName())));
                        Integer n = index;
                        Integer n2 = index = Integer.valueOf(index + 1);
                    }
                    mergeLoader.execute();
                }
                iMonitor.onProgress(process * (double)(in + 1) + startProgress);
                iMonitor.message("\u5f53\u524d\u8868\u4e3a" + this.getRealTableName(taskKey, tableModel.getCode()) + "\u8fdb\u884c\u4e2d,\u8fdb\u5ea6" + (process * (double)(in + 1) + startProgress) * 100.0 + "%", (Object)this);
            }
            catch (Exception e2) {
                iMonitor.error(this.getRealTableName(taskKey, dataTable.getCode()) + "\u8868\u590d\u5236\u6570\u636e\u51fa\u73b0\u5f02\u5e38\uff01tableKey:" + dataTable.getKey(), (Object)this);
                iMonitor.exception(e2);
                logger.error(e2.getMessage(), e2);
            }
            Integer n = in;
            Integer n3 = in = Integer.valueOf(in + 1);
        }
        iMonitor.onProgress(endProgress.doubleValue());
        iMonitor.message("\u590d\u5236\u65f6\u671f\u6570\u636e\u5df2\u5b8c\u6210!", (Object)this);
        iMonitor.finish();
    }

    private void executeBySQl(String taskKey, List<DataTable> dataTables, Connection conn, DimensionValueSet dimValueSet, DimensionValueSet sourceDimValueSet, IMonitor iMonitor) throws ParseException {
        Double process = (endProgress - startProgress) / (double)dataTables.size();
        ExecutorContext executorContext = new ExecutorContext(this.dataRunTimeController);
        DefinitionsCache cache = new DefinitionsCache(executorContext);
        Integer in = 0;
        while (in < dataTables.size()) {
            DataTable tableDefine = null;
            try {
                tableDefine = dataTables.get(in);
                DataTableType dataTableType = tableDefine.getDataTableType();
                List dimValueSetSplit = DimensionValueSetUtil.getDimensionSetList((DimensionValueSet)dimValueSet);
                DataModelDefinitionsCache tableCache = cache.getDataModelDefinitionsCache();
                List deployInfos = this.runtimeDataSchemeService.getDeployInfoByDataTableKey(tableDefine.getKey());
                List allColumnFields = deployInfos.stream().map(e -> this.dataModelService.getColumnModelDefineByID(e.getColumnModelKey())).collect(Collectors.toList());
                Map<String, List<ColumnModelDefine>> allfieldsMap = allColumnFields.stream().collect(Collectors.groupingBy(e -> e.getTableID()));
                for (String key : allfieldsMap.keySet()) {
                    List<ColumnModelDefine> allFieldsInTable = allfieldsMap.get(key);
                    ArrayList desFields = new ArrayList();
                    ArrayList srcFields = new ArrayList();
                    TableModelDefine table = this.dataModelService.getTableModelDefineById(key);
                    for (DimensionValueSet dimensionValueSet : dimValueSetSplit) {
                        String originTableName = table.getName();
                        String tableName = this.getRealTableName(taskKey, table.getName());
                        HashMap<String, ColumnModelDefine> dimFields = this.getTargetDimFields(table, dimValueSet, tableCache);
                        this.copy(tableName, allFieldsInTable, tableDefine, sourceDimValueSet, dimFields, conn, dimensionValueSet, tableCache);
                    }
                }
                iMonitor.onProgress(process * (double)(in + 1) + startProgress);
                iMonitor.message("\u5f53\u524d\u8868\u4e3a" + this.getRealTableName(taskKey, tableDefine.getCode()) + "\u8fdb\u884c\u4e2d,\u8fdb\u5ea6" + (process * (double)(in + 1) + startProgress) * 100.0 + "%", (Object)this);
            }
            catch (Exception e2) {
                iMonitor.error(this.getRealTableName(taskKey, tableDefine.getCode()) + "\u8868\u590d\u5236\u6570\u636e\u51fa\u73b0\u5f02\u5e38\uff01tableKey:" + tableDefine.getKey(), (Object)this);
                iMonitor.exception(e2);
                logger.error(e2.getMessage(), e2);
            }
            Integer n = in;
            Integer n2 = in = Integer.valueOf(in + 1);
        }
        iMonitor.onProgress(endProgress.doubleValue());
        iMonitor.message("\u590d\u5236\u65f6\u671f\u6570\u636e\u5df2\u5b8c\u6210!", (Object)this);
        iMonitor.finish();
    }

    private void copy(String tableName, List<ColumnModelDefine> allFieldsInTable, DataTable dataTable, DimensionValueSet oldTimeZone, HashMap<String, ColumnModelDefine> dimFields, Connection conn, DimensionValueSet dimensionValueSet, DataModelDefinitionsCache tableCache) throws Exception {
        boolean isFloatTable = dataTable.getDataTableType() == DataTableType.DETAIL;
        boolean isRepeat = dataTable.isRepeatCode();
        int index = 0;
        String sql = this.buildDeleteSql(tableName, dimFields);
        Object[] args = new Object[dimFields.size() + 1];
        args[index++] = dimensionValueSet.getValue(PERIOD_ZB);
        for (Map.Entry<String, ColumnModelDefine> entry : dimFields.entrySet()) {
            args[index++] = dimensionValueSet.getValue(entry.getKey());
        }
        logger.debug(String.format("sql:%s,args:%s", sql, args));
        DataEngineUtil.executeUpdate((Connection)conn, (String)sql, (Object[])args);
        this.insert(allFieldsInTable, tableName, dimensionValueSet, conn, oldTimeZone, dimFields);
    }

    private void insert(List<ColumnModelDefine> allColumn, String tableName, DimensionValueSet dimValueSet, Connection connection, DimensionValueSet oldTimeZone, HashMap<String, ColumnModelDefine> dimFields) throws Exception {
        Object[] args = new Object[dimFields.size() + 1];
        args[0] = oldTimeZone.getValue(PERIOD_ZB);
        int index = 1;
        for (Map.Entry<String, ColumnModelDefine> entry : dimFields.entrySet()) {
            String key = entry.getKey();
            if (ADJUST_ZB.equals(key)) {
                args[index++] = oldTimeZone.getValue(key);
                continue;
            }
            args[index++] = dimValueSet.getValue(key);
        }
        String sql = this.buildInsertSql(allColumn, tableName, dimValueSet, dimFields);
        DataEngineUtil.executeUpdate((Connection)connection, (String)sql, (Object[])args);
    }

    private String buildInsertSql(List<ColumnModelDefine> allColumn, String tableName, DimensionValueSet currDim, HashMap<String, ColumnModelDefine> dimFields) throws Exception {
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("insert into ").append(tableName);
        sqlBuilder.append(" (");
        boolean addFalag = false;
        for (ColumnModelDefine column : allColumn) {
            if (addFalag) {
                sqlBuilder.append(",");
            }
            sqlBuilder.append(column.getName());
            addFalag = true;
        }
        sqlBuilder.append(")");
        sqlBuilder.append(" select ");
        for (ColumnModelDefine field : allColumn) {
            if (PERIOD_ZB.equals(field.getCode())) {
                sqlBuilder.append(String.format("'%s' as %s,", currDim.getValue(PERIOD_ZB), field.getName()));
                continue;
            }
            if (ADJUST_ZB.equals(field.getCode())) {
                sqlBuilder.append(String.format("'%s' as %s,", currDim.getValue(ADJUST_ZB), field.getName()));
                continue;
            }
            if (field.getName().equals(BIZKEYORDER)) {
                sqlBuilder.append(String.format("%s as %s,", DataEngineUtil.buildcreateUUIDSql((IDatabase)this.database, (boolean)false), field.getName()));
                continue;
            }
            sqlBuilder.append(String.format("%s,", field.getName()));
        }
        sqlBuilder.setLength(sqlBuilder.length() - 1);
        sqlBuilder.append(" from ");
        sqlBuilder.append(tableName);
        sqlBuilder.append(" where ").append(PERIOD_ZB).append("=?");
        sqlBuilder.append(" and ");
        this.buildDimCondition(sqlBuilder, dimFields);
        return sqlBuilder.toString();
    }

    private boolean adjustDimensionValueSet(DimensionValueSet currDim) {
        Object adjust = currDim.getValue(ADJUST_ZB);
        return !Objects.equals(adjust, "0");
    }

    private String buildUpdateSql(List<ColumnModelDefine> filterFields, String tableName, HashMap<String, ColumnModelDefine> dimFields) {
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("update ").append(tableName);
        sqlBuilder.append(" set ");
        boolean addFalag = false;
        for (ColumnModelDefine column : filterFields) {
            if (addFalag) {
                sqlBuilder.append(",");
            }
            sqlBuilder.append(column.getName()).append("=?");
            addFalag = true;
        }
        sqlBuilder.append(" where ");
        sqlBuilder.append(PERIOD_ZB).append("=?");
        sqlBuilder.append(" and ");
        this.buildDimCondition(sqlBuilder, dimFields);
        return sqlBuilder.toString();
    }

    private String buildDeleteSql(String tableName, HashMap<String, ColumnModelDefine> dimFields) {
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("delete from ").append(tableName);
        sqlBuilder.append(" where ");
        sqlBuilder.append(PERIOD_ZB).append("=?");
        sqlBuilder.append(" and ");
        this.buildDimCondition(sqlBuilder, dimFields);
        return sqlBuilder.toString();
    }

    private void buildDimCondition(StringBuilder stringBuilder, Map<String, ColumnModelDefine> dimFields) {
        boolean addFalag = false;
        for (Map.Entry<String, ColumnModelDefine> entry : dimFields.entrySet()) {
            ColumnModelDefine column = entry.getValue();
            if (column.getCode().equals(PERIOD_ZB)) continue;
            if (addFalag) {
                stringBuilder.append(" and ");
            }
            stringBuilder.append(column.getName()).append("=?");
            addFalag = true;
        }
    }

    private List<RowData> parseResultSet(ResultSet resultSet, List<ColumnModelDefine> allFieldsInTable, String originTableName, DimensionValueSet dimValueSet, DataModelDefinitionsCache tableCache) throws SQLException {
        ArrayList<RowData> res = new ArrayList<RowData>();
        ResultSetMetaData metadata = resultSet.getMetaData();
        int count = metadata.getColumnCount();
        HashMap keyMap = new HashMap();
        allFieldsInTable.forEach(item -> keyMap.put(item.getName().toUpperCase(), item));
        List<ColumnModelDefine> dimFiled = this.getTargetDimColumn(originTableName, dimValueSet, tableCache);
        List dimFiledIds = dimFiled.stream().map(e -> e.getName().toUpperCase()).collect(Collectors.toList());
        while (resultSet.next()) {
            RowData row = new RowData();
            DimensionValueSet rowKey = new DimensionValueSet();
            LinkedHashMap<String, Object> rowDatas = new LinkedHashMap<String, Object>();
            for (int i = 1; i <= count; ++i) {
                String colName = metadata.getColumnName(i).toUpperCase();
                Object value = resultSet.getObject(i);
                rowDatas.put(colName, value);
                if (!dimFiledIds.contains(colName)) continue;
                String dimName = tableCache.getDimensionName((ColumnModelDefine)keyMap.get(colName));
                rowKey.setValue(dimName, value);
            }
            row.setRowData(rowDatas);
            row.setRowKeys(rowKey);
            res.add(row);
        }
        return res;
    }

    private String createSqlForTimeZone(String taskKey, List<ColumnModelDefine> allFieldsInTable, DimensionValueSet dimValueSet, List<ISQLField> desFields, List<ISQLField> srcFields, ITableLoader mergeLoader, DimensionValueSet sourceDim, DataModelDefinitionsCache tableCache, boolean useOldBziKey) throws Exception {
        if (null == allFieldsInTable || allFieldsInTable.isEmpty()) {
            return null;
        }
        TableModelDefine tableModel = tableCache.getTableModel(allFieldsInTable.get(0));
        String tableName = this.getRealTableName(taskKey, tableModel.getName());
        SimpleTable desTable = new SimpleTable(tableName);
        if (mergeLoader != null) {
            mergeLoader.setDestTable(desTable);
        }
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("select ");
        String timeField = null;
        String timeParam = null;
        for (ColumnModelDefine field : allFieldsInTable) {
            ISQLField sqlField = desTable.addField(field.getName());
            desFields.add(sqlField);
            if (PERIOD_ZB.equals(field.getCode())) {
                Object newTimeZone = dimValueSet.getValue(PERIOD_ZB);
                if (mergeLoader != null) {
                    timeField = String.format("'%s' as %s", newTimeZone, field.getName());
                    timeParam = String.format(" where %s = '%s'", field.getName(), sourceDim.getValue(PERIOD_ZB));
                    continue;
                }
                timeParam = String.format(" where %s = '%s'", field.getName(), newTimeZone);
                continue;
            }
            if (ADJUST_ZB.equals(field.getCode())) {
                Object adjust = dimValueSet.getValue(ADJUST_ZB);
                if (mergeLoader == null) continue;
                timeField = String.format("'%s' as %s", adjust, field.getName());
                continue;
            }
            if (!useOldBziKey && field.getName().equals(BIZKEYORDER)) {
                sqlBuilder.append(String.format("%s as %s,", DataEngineUtil.buildcreateUUIDSql((IDatabase)this.database, (boolean)true), field.getName()));
                continue;
            }
            sqlBuilder.append(String.format("%s,", field.getName()));
        }
        srcFields.addAll(desFields);
        if (StringUtils.isNotEmpty(timeField)) {
            sqlBuilder.append(timeField);
        } else {
            sqlBuilder.setLength(sqlBuilder.length() - 1);
        }
        sqlBuilder.append(" from ").append(tableName);
        sqlBuilder.append(timeParam);
        if (dimValueSet != null && dimValueSet.size() > 0) {
            HashMap<String, ColumnModelDefine> dimFields = this.getTargetDimFields(tableModel, dimValueSet, tableCache);
            this.buildWhereDimensions(sqlBuilder, dimFields, dimValueSet, sourceDim, true);
        }
        return sqlBuilder.toString();
    }

    private void buildWhereDimensions(StringBuilder sqlBuilder, HashMap<String, ColumnModelDefine> dimFields, DimensionValueSet dimValueSet, DimensionValueSet sourceDim, boolean andPrev) {
        for (Map.Entry<String, ColumnModelDefine> dimPair : dimFields.entrySet()) {
            String key = dimPair.getKey();
            Object argValue = dimValueSet.getValue(key);
            if (argValue instanceof List) {
                List listValue = (List)argValue;
                sqlBuilder.append(" and ").append(String.format("%s in (", dimPair.getValue().getName()));
                boolean addDot = false;
                for (int i = 0; i < listValue.size(); ++i) {
                    if (addDot) {
                        sqlBuilder.append(",");
                    }
                    addDot = true;
                    if (dimPair.getValue().getColumnType() == ColumnModelType.UUID) {
                        sqlBuilder.append(DataEngineUtil.buildGUIDValueSql((IDatabase)this.database, (UUID)UUID.fromString(listValue.get(i).toString())));
                        continue;
                    }
                    sqlBuilder.append(String.format("'%s'", listValue.get(i).toString()));
                }
                sqlBuilder.append(") ").append(andPrev ? "" : " and ");
                continue;
            }
            if (ADJUST_ZB.equals(key)) {
                sqlBuilder.append(String.format("%s%s='%s' %s", andPrev ? " and " : "", dimPair.getValue().getName(), sourceDim.getValue(dimPair.getKey()), andPrev ? "" : " and "));
                continue;
            }
            if (dimPair.getValue().getColumnType() == ColumnModelType.UUID) {
                sqlBuilder.append(String.format("%s%s=%s %s", andPrev ? " and " : "", dimPair.getValue().getName(), DataEngineUtil.buildGUIDValueSql((IDatabase)this.database, (UUID)UUID.fromString(dimValueSet.getValue(dimPair.getKey()).toString())), andPrev ? "" : " and "));
                continue;
            }
            sqlBuilder.append(String.format("%s%s='%s' %s", andPrev ? " and " : "", dimPair.getValue().getName(), dimValueSet.getValue(dimPair.getKey()), andPrev ? "" : " and "));
        }
    }

    private HashMap<String, ColumnModelDefine> getTargetDimFields(TableModelDefine table, DimensionValueSet dimValueSet, DataModelDefinitionsCache tableCache) {
        IDataAssist assiset = this.dataAccessProvider.newDataAssist(new ExecutorContext(this.dataRunTimeController));
        HashMap<String, ColumnModelDefine> fieldMap = new HashMap<String, ColumnModelDefine>();
        for (int i = 0; i < dimValueSet.size(); ++i) {
            ColumnModelDefine field;
            String dimName = dimValueSet.getName(i);
            if (ADJUST_ZB.equals(dimName)) {
                ColumnModelDefine col = this.dataModelService.getColumnModelDefineByCode(table.getID(), ADJUST_ZB);
                fieldMap.put(dimName, col);
                continue;
            }
            FieldDefine dimField = assiset.getDimensionField(table.getName(), dimName);
            if (dimField == null || (field = tableCache.getColumnModel(dimField)).getName().equals(PERIOD_ZB)) continue;
            fieldMap.put(dimName, field);
        }
        return fieldMap;
    }

    private List<ColumnModelDefine> getTargetDimColumn(String tableName, DimensionValueSet dimValueSet, DataModelDefinitionsCache tableCache) {
        IDataAssist assiset = this.dataAccessProvider.newDataAssist(new ExecutorContext(this.dataRunTimeController));
        ArrayList<ColumnModelDefine> dimList = new ArrayList<ColumnModelDefine>();
        for (int i = 0; i < dimValueSet.size(); ++i) {
            ColumnModelDefine column;
            String dimName = dimValueSet.getName(i);
            FieldDefine dimField = assiset.getDimensionField(tableName, dimName);
            if (dimField == null || (column = tableCache.getColumnModel(dimField)).getName().equals(BIZKEYORDER)) continue;
            dimList.add(column);
        }
        return dimList;
    }

    private boolean useSQL() {
        return true;
    }

    private List<DataTable> getTableKeysByForm(List<String> formKeys) {
        HashSet fieldSet = new HashSet();
        for (String formKey : formKeys) {
            List fieldKeys = this.runTimeViewController.getFieldKeysInForm(formKey);
            fieldSet.addAll(fieldKeys);
        }
        List dataFields = this.runtimeDataSchemeService.getDataFields(new ArrayList(fieldSet));
        if (null == dataFields || dataFields.isEmpty()) {
            return Collections.emptyList();
        }
        Set tableKeySet = dataFields.stream().map(DataField::getDataTableKey).collect(Collectors.toSet());
        List dataTables = this.runtimeDataSchemeService.getDataTables(new ArrayList(tableKeySet));
        return dataTables;
    }

    private List<DataTable> getTableKeysByRegion(List<DataRegionDefine> dataRegionDefines) {
        HashSet fieldSet = new HashSet();
        for (DataRegionDefine dataRegionDefine : dataRegionDefines) {
            List fieldKeys = this.runTimeViewController.getFieldKeysInRegion(dataRegionDefine.getKey());
            fieldSet.addAll(fieldKeys);
        }
        List dataFields = this.runtimeDataSchemeService.getDataFields(new ArrayList(fieldSet));
        if (null == dataFields || dataFields.isEmpty()) {
            return Collections.emptyList();
        }
        Set tableKeySet = dataFields.stream().map(DataField::getDataTableKey).collect(Collectors.toSet());
        return this.runtimeDataSchemeService.getDataTables(new ArrayList(tableKeySet));
    }

    private String getRealTableName(String taskKey, String tableName) {
        if (Objects.isNull(taskKey)) {
            return tableName;
        }
        return this.subDataBaseUtil.getRealTableName(tableName, taskKey);
    }

    static class RowData {
        private DimensionValueSet rowKeys;
        private LinkedHashMap<String, Object> rowData = new LinkedHashMap();

        RowData() {
        }

        public DimensionValueSet getRowKeys() {
            return this.rowKeys;
        }

        public void setRowKeys(DimensionValueSet rowKeys) {
            this.rowKeys = rowKeys;
        }

        public LinkedHashMap<String, Object> getRowData() {
            return this.rowData;
        }

        public void setRowData(LinkedHashMap<String, Object> rowData) {
            this.rowData = rowData;
        }
    }
}

