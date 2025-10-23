/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.database.DatabaseManager
 *  com.jiuqi.bi.database.IDatabase
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.np.sql.CustomClassExecutor
 *  com.jiuqi.nr.datascheme.api.event.RefreshCache
 *  com.jiuqi.nvwa.definition.common.event.DataModelCacheRefreshListener
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.interval.dao.NvwaDeployDao
 *  com.jiuqi.nvwa.definition.interval.deploy.DeployContext
 *  com.jiuqi.nvwa.definition.interval.deploy.FieldInfo
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  org.springframework.jdbc.datasource.DataSourceUtils
 */
package com.jiuqi.nr.datascheme.update;

import com.jiuqi.bi.database.DatabaseManager;
import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.np.sql.CustomClassExecutor;
import com.jiuqi.nr.datascheme.api.event.RefreshCache;
import com.jiuqi.nr.datascheme.internal.service.impl.cache.DefaultCacheRefreshService;
import com.jiuqi.nvwa.definition.common.event.DataModelCacheRefreshListener;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.interval.dao.NvwaDeployDao;
import com.jiuqi.nvwa.definition.interval.deploy.DeployContext;
import com.jiuqi.nvwa.definition.interval.deploy.FieldInfo;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

public class OrgFieldNameUpgrade
implements CustomClassExecutor {
    private final String OLD_DB_ORG_FIELD_NAME = "DW";
    private final String DB_FIELD_NAME_TABLEID = "TABLE_ID";
    private final String DB_FIELD_NAME_TABLENAME = "TABLE_NAME";
    private final String DB_FIELD_NAME_TABLEKEYS = "TABLE_KEYS";
    private final String DB_FIELD_NAME_FIELDID = "FIELD_ID";
    private final String DB_FIELD_NAME_FIELDCODE = "FIELD_CODE";
    private final String DB_FIELD_NAME_FIELDNAME = "FIELD_NAME";
    private final String DB_TABLE_NAME_TABLE = "NVWA_TABLEMODEL";
    private final String DB_TABLE_NAME_COLUMN = "NVWA_COLUMNMODEL";
    private final Logger logger = LoggerFactory.getLogger(OrgFieldNameUpgrade.class);

    private List<FieldBaseInfo> getUpgradeFieldBaseInfo(DataSource dataSource) throws SQLException {
        ArrayList<FieldBaseInfo> resultList = new ArrayList<FieldBaseInfo>();
        String selectColumnSql = String.format("SELECT t.%s %s, t.%s %s, c.%s %s, c.%s %s, c.%s %s FROM %s c INNER JOIN %s t ON t.%s=c.%s AND c.%s=? AND (c.%s IS NULL OR c.%s=? OR c.%s=?)", "TABLE_NAME", "TABLE_NAME", "TABLE_KEYS", "TABLE_KEYS", "FIELD_ID", "FIELD_ID", "FIELD_CODE", "FIELD_CODE", "FIELD_NAME", "FIELD_NAME", "NVWA_COLUMNMODEL", "NVWA_TABLEMODEL", "TABLE_ID", "TABLE_ID", "FIELD_CODE", "FIELD_NAME", "FIELD_NAME", "FIELD_NAME");
        try (Connection connection = DataSourceUtils.getConnection((DataSource)dataSource);
             PreparedStatement selectStatement = connection.prepareStatement(selectColumnSql);){
            selectStatement.setString(1, "DW");
            selectStatement.setString(2, "");
            selectStatement.setString(3, "DW");
            try (ResultSet resultSet = selectStatement.executeQuery();){
                FieldBaseInfo fieldBaseInfo = null;
                while (resultSet.next()) {
                    fieldBaseInfo = new FieldBaseInfo();
                    fieldBaseInfo.tableName = resultSet.getString("TABLE_NAME");
                    fieldBaseInfo.keys = resultSet.getString("TABLE_KEYS");
                    fieldBaseInfo.fieldId = resultSet.getString("FIELD_ID");
                    fieldBaseInfo.fieldCode = resultSet.getString("FIELD_CODE");
                    fieldBaseInfo.fieldName = resultSet.getString("FIELD_NAME");
                    resultList.add(fieldBaseInfo);
                }
            }
        }
        return resultList;
    }

    public void execute(DataSource dataSource) throws Exception {
        this.logger.info("\u6570\u636e\u65b9\u6848\uff1a\u4e3b\u7ef4\u5ea6\u5b57\u6bb5\u6807\u8bc6\u5347\u7ea7\u5f00\u59cb");
        this.batchUpdate(dataSource);
        List<FieldBaseInfo> successFields = this.renameFields(dataSource);
        this.batchUpdateRun(dataSource, successFields);
        this.cleanCache();
        this.logger.info("\u6570\u636e\u65b9\u6848\uff1a\u4e3b\u7ef4\u5ea6\u5b57\u6bb5\u6807\u8bc6\u5347\u7ea7\u7ed3\u675f");
    }

    private void cleanCache() {
        this.logger.info("\u6570\u636e\u65b9\u6848\uff1a\u4e3b\u7ef4\u5ea6\u5b57\u6bb5\u6807\u8bc6\u5347\u7ea7\u6e05\u7406\u7f13\u5b58\u5f00\u59cb");
        DataModelCacheRefreshListener dataModelCacheRefreshListener = (DataModelCacheRefreshListener)SpringBeanUtils.getBean(DataModelCacheRefreshListener.class);
        dataModelCacheRefreshListener.onClearCache();
        DefaultCacheRefreshService cacheRefreshService = (DefaultCacheRefreshService)SpringBeanUtils.getBean(DefaultCacheRefreshService.class);
        RefreshCache refreshCache = new RefreshCache();
        refreshCache.setRefreshAll(true);
        cacheRefreshService.onClearCache(refreshCache);
        this.logger.info("\u6570\u636e\u65b9\u6848\uff1a\u4e3b\u7ef4\u5ea6\u5b57\u6bb5\u6807\u8bc6\u5347\u7ea7\u6e05\u7406\u7f13\u5b58\u7ed3\u675f");
    }

    private void batchUpdateRun(DataSource dataSource, List<FieldBaseInfo> fieldBaseInfos) throws SQLException {
        this.logger.info("\u6570\u636e\u65b9\u6848\uff1a\u4e3b\u7ef4\u5ea6\u5b57\u6bb5\u6807\u8bc6\u5347\u7ea7\uff0c\u66f4\u65b0NVWA\u8fd0\u884c\u671f\u53c2\u6570");
        Connection connection = null;
        Statement updateStatement = null;
        String upgradeColumnSql = String.format("UPDATE %s SET %s=?, %s=? WHERE %s=? ", "NVWA_COLUMNMODEL", "FIELD_CODE", "FIELD_NAME", "FIELD_ID");
        try {
            connection = DataSourceUtils.getConnection((DataSource)dataSource);
            connection.setAutoCommit(false);
            updateStatement = connection.prepareStatement(upgradeColumnSql);
            FieldBaseInfo fieldBaseInfo = null;
            for (int i = 0; i < fieldBaseInfos.size(); ++i) {
                fieldBaseInfo = fieldBaseInfos.get(i);
                updateStatement.setString(1, "MDCODE");
                updateStatement.setString(2, null == fieldBaseInfo.fieldName ? null : "MDCODE");
                updateStatement.setString(3, fieldBaseInfo.fieldId);
                updateStatement.addBatch();
                if (i % 500 != 0) continue;
                updateStatement.executeBatch();
            }
            updateStatement.executeBatch();
            connection.commit();
        }
        catch (SQLException e) {
            this.logger.error("\u6570\u636e\u65b9\u6848\uff1a\u4e3b\u7ef4\u5ea6\u5b57\u6bb5\u6807\u8bc6\u5347\u7ea7\uff0c\u66f4\u65b0NVWA\u8fd0\u884c\u671f\u53c2\u6570\u5931\u8d25", e);
            if (connection != null && !connection.isClosed()) {
                connection.rollback();
            }
            throw e;
        }
        finally {
            if (null != updateStatement) {
                updateStatement.close();
            }
            if (connection != null) {
                try {
                    connection.setAutoCommit(true);
                }
                catch (SQLException sQLException) {}
                DataSourceUtils.releaseConnection((Connection)connection, (DataSource)dataSource);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private List<FieldBaseInfo> renameFields(DataSource dataSource) throws SQLException {
        this.logger.info("\u6570\u636e\u65b9\u6848\uff1a\u4e3b\u7ef4\u5ea6\u5b57\u6bb5\u6807\u8bc6\u5347\u7ea7\uff0c\u91cd\u547d\u540d\u7269\u7406\u8868\u5b57\u6bb5\u540d\u79f0");
        ArrayList<FieldBaseInfo> successFieldInfos = new ArrayList<FieldBaseInfo>();
        List<FieldBaseInfo> upgradeFieldBaseInfos = this.getUpgradeFieldBaseInfo(dataSource);
        if (CollectionUtils.isEmpty(upgradeFieldBaseInfos)) {
            return successFieldInfos;
        }
        Map<String, FieldBaseInfo> fieldBaseInfoMap = upgradeFieldBaseInfos.stream().collect(Collectors.toMap(info -> info.fieldId, info -> info));
        DataModelService dataModelService = (DataModelService)SpringBeanUtils.getBean(DataModelService.class);
        List columnModelDefines = dataModelService.getColumnModelDefinesByIDs(fieldBaseInfoMap.keySet());
        NvwaDeployDao nvwaDeployDao = (NvwaDeployDao)SpringBeanUtils.getBean(NvwaDeployDao.class);
        Connection connection = null;
        try {
            connection = DataSourceUtils.getConnection((DataSource)dataSource);
            connection.setAutoCommit(false);
            IDatabase database = DatabaseManager.getInstance().findDatabaseByConnection(connection);
            DeployContext deployContext = new DeployContext(connection, database, null);
            FieldInfo fieldInfo = null;
            FieldBaseInfo fieldBaseInfo = null;
            for (ColumnModelDefine columnModelDefine : columnModelDefines) {
                fieldInfo = new FieldInfo(columnModelDefine);
                fieldBaseInfo = fieldBaseInfoMap.get(columnModelDefine.getID());
                if (fieldInfo.isCanNull() && StringUtils.hasLength(fieldBaseInfo.keys)) {
                    fieldInfo.setCanNull(!fieldBaseInfo.keys.contains(columnModelDefine.getID()));
                }
                try {
                    nvwaDeployDao.renameField(deployContext, fieldBaseInfo.tableName, fieldBaseInfo.fieldCode, "MDCODE", fieldInfo);
                    successFieldInfos.add(fieldBaseInfo);
                    this.logger.info("\u6570\u636e\u65b9\u6848\uff1a\u4e3b\u7ef4\u5ea6\u5b57\u6bb5\u6807\u8bc6\u5347\u7ea7\uff0c\u91cd\u547d\u540d\u7269\u7406\u8868\u5b57\u6bb5\u540d\u79f0{}[{}->{}]\u6210\u529f", fieldBaseInfo.tableName, fieldBaseInfo.fieldCode, "MDCODE");
                }
                catch (Exception e) {
                    this.logger.error("\u6570\u636e\u65b9\u6848\uff1a\u4e3b\u7ef4\u5ea6\u5b57\u6bb5\u6807\u8bc6\u5347\u7ea7\uff0c\u91cd\u547d\u540d\u7269\u7406\u8868\u5b57\u6bb5\u540d\u79f0{}[{}->{}]\u5931\u8d25", fieldBaseInfo.tableName, fieldBaseInfo.fieldCode, "MDCODE", e);
                }
            }
        }
        catch (Exception e) {
            this.logger.error("\u6570\u636e\u65b9\u6848\uff1a\u4e3b\u7ef4\u5ea6\u5b57\u6bb5\u6807\u8bc6\u5347\u7ea7\u5f02\u5e38", e);
        }
        finally {
            if (connection != null) {
                try {
                    connection.setAutoCommit(true);
                }
                catch (SQLException sQLException) {}
                DataSourceUtils.releaseConnection((Connection)connection, (DataSource)dataSource);
            }
        }
        return successFieldInfos;
    }

    private void batchUpdate(DataSource dataSource) throws SQLException {
        this.logger.info("\u6570\u636e\u65b9\u6848\uff1a\u4e3b\u7ef4\u5ea6\u5b57\u6bb5\u6807\u8bc6\u5347\u7ea7\uff0c\u4fee\u6539\u6570\u636e\u65b9\u6848\u8bbe\u8ba1\u671f\u3001\u8fd0\u884c\u671f\u3001NVWA\u8bbe\u8ba1\u671f\u53c2\u6570");
        Connection connection = null;
        String updateSqlFormte = "UPDATE %s SET %s=? WHERE %s=? ";
        String upgradeColumnDesSql = "UPDATE NVWA_COLUMNMODEL_DES SET FIELD_CODE=? WHERE FIELD_CODE=? AND (FIELD_NAME IS NULL OR FIELD_NAME=?)";
        String upgradeColumnDesSql2 = "UPDATE NVWA_COLUMNMODEL_DES SET FIELD_CODE=?, FIELD_NAME=? WHERE FIELD_CODE=? AND FIELD_NAME=?";
        try {
            connection = DataSourceUtils.getConnection((DataSource)dataSource);
            connection.setAutoCommit(false);
            try (PreparedStatement preparedStatement = connection.prepareStatement(String.format(updateSqlFormte, "NR_DATASCHEME_FIELD_DES", "DF_CODE", "DF_CODE"));){
                preparedStatement.setString(1, "MDCODE");
                preparedStatement.setString(2, "DW");
                preparedStatement.execute();
            }
            preparedStatement = connection.prepareStatement(String.format(updateSqlFormte, "NR_DATASCHEME_FIELD", "DF_CODE", "DF_CODE"));
            var7_9 = null;
            try {
                preparedStatement.setString(1, "MDCODE");
                preparedStatement.setString(2, "DW");
                preparedStatement.execute();
            }
            catch (Throwable throwable) {
                var7_9 = throwable;
                throw throwable;
            }
            finally {
                if (preparedStatement != null) {
                    if (var7_9 != null) {
                        try {
                            preparedStatement.close();
                        }
                        catch (Throwable throwable) {
                            var7_9.addSuppressed(throwable);
                        }
                    } else {
                        preparedStatement.close();
                    }
                }
            }
            preparedStatement = connection.prepareStatement(String.format(updateSqlFormte, "NR_DATASCHEME_DEPLOY_INFO", "DS_FIELD_NAME", "DS_FIELD_NAME"));
            var7_9 = null;
            try {
                preparedStatement.setString(1, "MDCODE");
                preparedStatement.setString(2, "DW");
                preparedStatement.execute();
            }
            catch (Throwable throwable) {
                var7_9 = throwable;
                throw throwable;
            }
            finally {
                if (preparedStatement != null) {
                    if (var7_9 != null) {
                        try {
                            preparedStatement.close();
                        }
                        catch (Throwable throwable) {
                            var7_9.addSuppressed(throwable);
                        }
                    } else {
                        preparedStatement.close();
                    }
                }
            }
            preparedStatement = connection.prepareStatement("UPDATE NVWA_COLUMNMODEL_DES SET FIELD_CODE=? WHERE FIELD_CODE=? AND (FIELD_NAME IS NULL OR FIELD_NAME=?)");
            var7_9 = null;
            try {
                preparedStatement.setString(1, "MDCODE");
                preparedStatement.setString(2, "DW");
                preparedStatement.setString(3, "");
                preparedStatement.execute();
            }
            catch (Throwable throwable) {
                var7_9 = throwable;
                throw throwable;
            }
            finally {
                if (preparedStatement != null) {
                    if (var7_9 != null) {
                        try {
                            preparedStatement.close();
                        }
                        catch (Throwable throwable) {
                            var7_9.addSuppressed(throwable);
                        }
                    } else {
                        preparedStatement.close();
                    }
                }
            }
            preparedStatement = connection.prepareStatement("UPDATE NVWA_COLUMNMODEL_DES SET FIELD_CODE=?, FIELD_NAME=? WHERE FIELD_CODE=? AND FIELD_NAME=?");
            var7_9 = null;
            try {
                preparedStatement.setString(1, "MDCODE");
                preparedStatement.setString(2, "MDCODE");
                preparedStatement.setString(3, "DW");
                preparedStatement.setString(4, "DW");
                preparedStatement.execute();
            }
            catch (Throwable throwable) {
                var7_9 = throwable;
                throw throwable;
            }
            finally {
                if (preparedStatement != null) {
                    if (var7_9 != null) {
                        try {
                            preparedStatement.close();
                        }
                        catch (Throwable throwable) {
                            var7_9.addSuppressed(throwable);
                        }
                    } else {
                        preparedStatement.close();
                    }
                }
            }
            connection.commit();
        }
        catch (Exception e) {
            this.logger.error("\u6570\u636e\u65b9\u6848\uff1a\u4e3b\u7ef4\u5ea6\u5b57\u6bb5\u6807\u8bc6\u5347\u7ea7\uff0c\u4fee\u6539\u6570\u636e\u65b9\u6848\u8bbe\u8ba1\u671f\u3001\u8fd0\u884c\u671f\u3001NVWA\u8bbe\u8ba1\u671f\u53c2\u6570\u5931\u8d25", e);
            if (connection != null && !connection.isClosed()) {
                connection.rollback();
            }
            throw e;
        }
        finally {
            if (connection != null) {
                try {
                    connection.setAutoCommit(true);
                }
                catch (SQLException sQLException) {}
                DataSourceUtils.releaseConnection((Connection)connection, (DataSource)dataSource);
            }
        }
    }

    private class FieldBaseInfo {
        String tableName;
        String keys;
        String fieldId;
        String fieldCode;
        String fieldName;

        private FieldBaseInfo() {
        }
    }
}

