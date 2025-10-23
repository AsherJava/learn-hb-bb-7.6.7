/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.database.DatabaseManager
 *  com.jiuqi.bi.database.IDatabase
 *  com.jiuqi.bi.database.metadata.ISQLMetadata
 *  com.jiuqi.bi.database.metadata.LogicField
 *  com.jiuqi.bi.database.metadata.LogicTable
 *  com.jiuqi.bi.database.metadata.SQLMetadataException
 *  com.jiuqi.bi.util.OrderGenerator
 *  com.jiuqi.nr.datascheme.internal.deploy.DataSchemeDeployHelper
 *  com.jiuqi.nr.datascheme.internal.entity.DataFieldDeployInfoDO
 *  com.jiuqi.nvwa.definition.common.NrdbHelper
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.facade.design.DesignTableModelDefine
 *  com.jiuqi.nvwa.definition.interval.dao.NvwaDeployDao
 *  com.jiuqi.nvwa.definition.interval.deploy.DeployContext
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.jdbc.datasource.DataSourceUtils
 */
package com.jiuqi.nr.datascheme.fix.utils;

import com.jiuqi.bi.database.DatabaseManager;
import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.bi.database.metadata.ISQLMetadata;
import com.jiuqi.bi.database.metadata.LogicField;
import com.jiuqi.bi.database.metadata.LogicTable;
import com.jiuqi.bi.database.metadata.SQLMetadataException;
import com.jiuqi.bi.util.OrderGenerator;
import com.jiuqi.nr.datascheme.fix.exception.DataSchemeDeployFixException;
import com.jiuqi.nr.datascheme.fix.utils.Tools;
import com.jiuqi.nr.datascheme.internal.deploy.DataSchemeDeployHelper;
import com.jiuqi.nr.datascheme.internal.entity.DataFieldDeployInfoDO;
import com.jiuqi.nvwa.definition.common.NrdbHelper;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.facade.design.DesignTableModelDefine;
import com.jiuqi.nvwa.definition.interval.dao.NvwaDeployDao;
import com.jiuqi.nvwa.definition.interval.deploy.DeployContext;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Component;

@Component
public class DeployFixUtils {
    @Autowired
    private NvwaDeployDao nvwaDeployDao;
    @Autowired
    protected JdbcTemplate jdbcTemplate;
    @Autowired
    protected Tools tools;
    @Autowired
    private DataSource dataSource;
    @Autowired
    private NrdbHelper nrdbHelper;

    public void deleteLogicTable(String tableName) {
        try (Connection connection = this.dataSource.getConnection();){
            IDatabase database = DatabaseManager.getInstance().findDatabaseByConnection(connection);
            DeployContext deployContext = new DeployContext(connection, database, null);
            deployContext.setNrdbHelper(this.nrdbHelper);
            this.nvwaDeployDao.dropTable(deployContext, tableName);
        }
        catch (Exception e) {
            throw new DataSchemeDeployFixException("delete Table failed", e);
        }
    }

    public String renameLogicTable(String tableName) {
        if (tableName == null) {
            return null;
        }
        SecureRandom rand = new SecureRandom();
        int tableIndex = rand.nextInt(10000);
        String deleteTableName = String.format("%s_%s_%s", "FIX", OrderGenerator.newOrder(), tableIndex);
        try (Connection connection = this.dataSource.getConnection();){
            IDatabase database = DatabaseManager.getInstance().findDatabaseByConnection(connection);
            DeployContext deployContext = new DeployContext(connection, database, null);
            deployContext.setNrdbHelper(this.nrdbHelper);
            this.nvwaDeployDao.renameTable(deployContext, tableName, deleteTableName);
        }
        catch (Exception e) {
            throw new DataSchemeDeployFixException("rename table failed", e);
        }
        return deleteTableName;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public LogicTable getLogicTable(String tableModelKey) {
        LogicTable logicTable;
        TableModelDefine tableModel = this.tools.getTableModelByTableModelKey(tableModelKey);
        DesignTableModelDefine desTableModel = this.tools.getDesTableModelByTableModelKey(tableModelKey);
        List<DataFieldDeployInfoDO> dataFieldDeployInfos = this.tools.getDeployInfoByTableModelKey(tableModelKey);
        if (tableModel != null) {
            Connection conn = null;
            try {
                conn = DataSourceUtils.getConnection((DataSource)this.dataSource);
                IDatabase database = DatabaseManager.getInstance().findDatabaseByConnection(conn);
                ISQLMetadata isqlMetadata = database.createMetadata(conn);
                logicTable = isqlMetadata.getTableByName(tableModel.getName());
            }
            catch (SQLMetadataException | SQLException e) {
                logicTable = null;
            }
            finally {
                if (conn != null) {
                    DataSourceUtils.releaseConnection((Connection)conn, (DataSource)this.dataSource);
                }
            }
        } else if (desTableModel != null) {
            Connection conn = null;
            try {
                conn = DataSourceUtils.getConnection((DataSource)this.dataSource);
                IDatabase database = DatabaseManager.getInstance().findDatabaseByConnection(conn);
                ISQLMetadata isqlMetadata = database.createMetadata(conn);
                logicTable = isqlMetadata.getTableByName(desTableModel.getName());
            }
            catch (SQLMetadataException | SQLException e) {
                logicTable = null;
            }
            finally {
                if (conn != null) {
                    DataSourceUtils.releaseConnection((Connection)conn, (DataSource)this.dataSource);
                }
            }
        } else if (dataFieldDeployInfos != null) {
            ArrayList<String> tableNames = new ArrayList<String>();
            for (DataFieldDeployInfoDO dataFieldDeployInfo : dataFieldDeployInfos) {
                String tableName = dataFieldDeployInfo.getTableName();
                tableNames.add(tableName);
            }
            List tableName = tableNames.stream().distinct().collect(Collectors.toList());
            Connection conn = null;
            try {
                conn = DataSourceUtils.getConnection((DataSource)this.dataSource);
                IDatabase database = DatabaseManager.getInstance().findDatabaseByConnection(conn);
                ISQLMetadata isqlMetadata = database.createMetadata(conn);
                logicTable = isqlMetadata.getTableByName((String)tableName.get(0));
            }
            catch (SQLMetadataException | SQLException e) {
                logicTable = null;
            }
            finally {
                if (conn != null) {
                    DataSourceUtils.releaseConnection((Connection)conn, (DataSource)this.dataSource);
                }
            }
        } else {
            logicTable = null;
        }
        return logicTable;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public List<LogicField> getLogicField(String tableModelKey, String tableName) {
        List logicFields;
        Connection conn = null;
        if (tableName == null) {
            LogicTable logicTable = this.getLogicTable(tableModelKey);
            if (logicTable != null) {
                try {
                    conn = DataSourceUtils.getConnection((DataSource)this.dataSource);
                    IDatabase database = DatabaseManager.getInstance().findDatabaseByConnection(conn);
                    ISQLMetadata isqlMetadata = database.createMetadata(conn);
                    logicFields = isqlMetadata.getFieldsByTableName(logicTable.getName());
                }
                catch (SQLMetadataException | SQLException throwables) {
                    logicFields = null;
                }
                finally {
                    if (conn != null) {
                        DataSourceUtils.releaseConnection((Connection)conn, (DataSource)this.dataSource);
                    }
                }
            } else {
                logicFields = null;
            }
        } else {
            try {
                conn = DataSourceUtils.getConnection((DataSource)this.dataSource);
                IDatabase database = DatabaseManager.getInstance().findDatabaseByConnection(conn);
                ISQLMetadata isqlMetadata = database.createMetadata(conn);
                logicFields = isqlMetadata.getFieldsByTableName(tableName);
            }
            catch (SQLMetadataException | SQLException throwables) {
                logicFields = null;
            }
            finally {
                if (conn != null) {
                    DataSourceUtils.releaseConnection((Connection)conn, (DataSource)this.dataSource);
                }
            }
        }
        return logicFields;
    }

    public void transferData(String oldTableName, String newTableName) {
        List<LogicField> newLogicFields = this.getLogicField(null, oldTableName);
        List<LogicField> oldLogicField = this.getLogicField(null, newTableName);
        Map<String, LogicField> newLogicFieldMap = newLogicFields.stream().collect(Collectors.toMap(LogicField::getFieldName, v -> v));
        Map<String, LogicField> oldLogicFieldMap = oldLogicField.stream().collect(Collectors.toMap(LogicField::getFieldName, v -> v));
        Set<String> newLogicFieldKeySet = newLogicFieldMap.keySet();
        Set<String> oldLogicFieldKeySet = oldLogicFieldMap.keySet();
        Set unionFieldKeySet = DataSchemeDeployHelper.union(newLogicFieldKeySet, oldLogicFieldKeySet);
        StringBuffer insertSQL = new StringBuffer("insert into ");
        StringBuffer selectSQL = new StringBuffer(" select ");
        insertSQL.append(oldTableName).append(" (");
        String[] fields = unionFieldKeySet.toArray(new String[unionFieldKeySet.size()]);
        int i = 0;
        for (String field : fields) {
            if (i > 0) {
                insertSQL.append(",");
                selectSQL.append(",");
            }
            insertSQL.append(field.toUpperCase());
            selectSQL.append(field.toUpperCase());
            ++i;
        }
        if (i > 0) {
            insertSQL.append(") ");
        }
        selectSQL.append(" from ").append(newTableName).append(" ");
        insertSQL.append(selectSQL);
        this.jdbcTemplate.update(insertSQL.toString());
    }
}

