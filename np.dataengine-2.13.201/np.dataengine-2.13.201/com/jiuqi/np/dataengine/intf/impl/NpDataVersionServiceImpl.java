/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.database.IDatabase
 *  com.jiuqi.bi.database.sql.loader.ILoadListener
 *  com.jiuqi.bi.database.sql.loader.ITableLoader
 *  com.jiuqi.bi.database.sql.loader.LoadFieldMap
 *  com.jiuqi.bi.database.sql.model.ISQLField
 *  com.jiuqi.bi.database.sql.model.ISQLTable
 *  com.jiuqi.bi.database.sql.model.tables.InnerTable
 *  com.jiuqi.np.definition.common.FieldType
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.nr.common.db.DatabaseInstance
 *  javax.annotation.Resource
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.jdbc.datasource.DataSourceUtils
 */
package com.jiuqi.np.dataengine.intf.impl;

import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.bi.database.sql.loader.ILoadListener;
import com.jiuqi.bi.database.sql.loader.ITableLoader;
import com.jiuqi.bi.database.sql.loader.LoadFieldMap;
import com.jiuqi.bi.database.sql.model.ISQLField;
import com.jiuqi.bi.database.sql.model.ISQLTable;
import com.jiuqi.bi.database.sql.model.tables.InnerTable;
import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.dataengine.common.DataEngineUtil;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.DataVersionService;
import com.jiuqi.np.dataengine.intf.IDataAssist;
import com.jiuqi.np.dataengine.intf.IMonitor;
import com.jiuqi.np.dataengine.intf.impl.LoggingTableLoadListener;
import com.jiuqi.np.definition.common.FieldType;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.nr.common.db.DatabaseInstance;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.annotation.Resource;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Service;

@Service
public class NpDataVersionServiceImpl
implements DataVersionService {
    @Resource
    private JdbcTemplate jdbcTemplate;
    @Resource
    private IDataDefinitionRuntimeController controller;
    @Resource
    private IDataAccessProvider dataAccessProvider;
    private IDatabase database;
    private static final Double DELETE_PROGRESS = 0.5;
    private static final Double COPY_PROGRESS = 0.8;
    private static final Double END_PROGRESS = 1.0;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void createDataVersion(List<String> tableKeys, DimensionValueSet dimValueSet, String oldVersion, String newVersion, IMonitor iMonitor, boolean isForOverwrite) throws Exception {
        this.database = DatabaseInstance.getDatabase();
        Connection connection = DataSourceUtils.getConnection((DataSource)this.jdbcTemplate.getDataSource());
        Double process = 0.0;
        process = isForOverwrite ? Double.valueOf(END_PROGRESS - DELETE_PROGRESS / (double)tableKeys.size()) : Double.valueOf(COPY_PROGRESS / (double)tableKeys.size());
        try {
            for (int i = 0; i < tableKeys.size(); ++i) {
                ITableLoader tableLoader = this.database.createInsertLoader(connection);
                tableLoader.setListener((ILoadListener)new LoggingTableLoadListener());
                ArrayList<ISQLField> desFields = new ArrayList<ISQLField>();
                ArrayList<ISQLField> srcFields = new ArrayList<ISQLField>();
                String sql = this.buildSqlForOldVersion(tableKeys.get(i), dimValueSet, tableLoader, desFields, oldVersion, newVersion);
                if (null == sql) {
                    return;
                }
                srcFields.addAll(desFields);
                InnerTable srcTable = new InnerTable(sql);
                srcTable.fields().addAll(srcFields);
                tableLoader.setSourceTable((ISQLTable)srcTable);
                List fieldMaps = tableLoader.getFieldMaps();
                Integer index = 0;
                while (index < desFields.size()) {
                    fieldMaps.add(new LoadFieldMap((ISQLField)srcFields.get(index), (ISQLField)desFields.get(index)));
                    Integer n = index;
                    Integer n2 = index = Integer.valueOf(index + 1);
                }
                tableLoader.execute();
                if (null == iMonitor) continue;
                iMonitor.onProgress(process * (double)(i + 1));
                iMonitor.message("\u521b\u5efa\u7248\u672c, \u603b\u8fdb\u5ea6" + process * (double)(i + 1) * 100.0 + "%", this);
            }
        }
        finally {
            if (connection != null) {
                DataSourceUtils.releaseConnection((Connection)connection, (DataSource)this.jdbcTemplate.getDataSource());
            }
        }
    }

    private String buildSqlForOldVersion(String tableKey, DimensionValueSet dimValueSet, ITableLoader tableLoader, List<ISQLField> desFields, String oldVersion, String newVersion) throws Exception {
        StringBuilder sqlBuilder = new StringBuilder();
        return sqlBuilder.toString();
    }

    private HashMap<String, FieldDefine> getTargetDimFields(String tableName, DimensionValueSet dimValueSet) {
        ExecutorContext executorContext = new ExecutorContext(this.controller);
        IDataAssist dataAssiset = this.dataAccessProvider.newDataAssist(executorContext);
        HashMap<String, FieldDefine> fieldDefines = new HashMap<String, FieldDefine>();
        for (int index = 0; index < dimValueSet.size(); ++index) {
            String dimName = dimValueSet.getName(index);
            FieldDefine dimField = dataAssiset.getDimensionField(tableName, dimName);
            if (dimField == null) continue;
            fieldDefines.put(dimName, dimField);
        }
        return fieldDefines;
    }

    private void buildWhereDimensions(StringBuilder sqlBuilder, HashMap<String, FieldDefine> dimFields, DimensionValueSet dimValueSet, boolean andPrev) {
        for (Map.Entry<String, FieldDefine> dimPair : dimFields.entrySet()) {
            Object argValue = dimValueSet.getValue(dimPair.getKey());
            if (argValue instanceof List) {
                List listValue = (List)argValue;
                sqlBuilder.append(" and ").append(String.format("%s in (", dimPair.getValue().getCode()));
                boolean addDot = false;
                for (int i = 0; i < listValue.size(); ++i) {
                    if (addDot) {
                        sqlBuilder.append(",");
                    }
                    addDot = true;
                    if (dimPair.getValue().getType() == FieldType.FIELD_TYPE_UUID) {
                        sqlBuilder.append(DataEngineUtil.buildGUIDValueSql(this.database, UUID.fromString(listValue.get(i).toString())));
                        continue;
                    }
                    sqlBuilder.append(String.format("'%s'", listValue.get(i).toString()));
                }
                sqlBuilder.append(") ").append(andPrev ? "" : " and ");
                continue;
            }
            if (dimPair.getValue().getType() == FieldType.FIELD_TYPE_UUID) {
                sqlBuilder.append(String.format("%s%s=%s %s", andPrev ? " and " : "", dimPair.getValue().getCode(), DataEngineUtil.buildGUIDValueSql(this.database, UUID.fromString(dimValueSet.getValue(dimPair.getKey()).toString())), andPrev ? "" : " and "));
                continue;
            }
            sqlBuilder.append(String.format("%s%s='%s' %s", andPrev ? " and " : "", dimPair.getValue().getCode(), dimValueSet.getValue(dimPair.getKey()), andPrev ? "" : " and "));
        }
    }
}

