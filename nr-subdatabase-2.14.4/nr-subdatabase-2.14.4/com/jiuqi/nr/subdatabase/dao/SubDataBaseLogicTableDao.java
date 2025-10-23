/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.database.DatabaseManager
 *  com.jiuqi.bi.database.IDatabase
 *  com.jiuqi.bi.database.metadata.ISQLMetadata
 *  com.jiuqi.bi.database.metadata.LogicField
 *  com.jiuqi.bi.database.metadata.LogicPrimaryKey
 *  com.jiuqi.bi.database.metadata.LogicTable
 *  com.jiuqi.bi.database.metadata.SQLMetadataException
 *  com.jiuqi.bi.database.statement.SqlStatement
 *  org.springframework.jdbc.datasource.DataSourceUtils
 */
package com.jiuqi.nr.subdatabase.dao;

import com.jiuqi.bi.database.DatabaseManager;
import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.bi.database.metadata.ISQLMetadata;
import com.jiuqi.bi.database.metadata.LogicField;
import com.jiuqi.bi.database.metadata.LogicPrimaryKey;
import com.jiuqi.bi.database.metadata.LogicTable;
import com.jiuqi.bi.database.metadata.SQLMetadataException;
import com.jiuqi.bi.database.statement.SqlStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;

@Repository
public class SubDataBaseLogicTableDao {
    private static final Logger logger = LoggerFactory.getLogger(SubDataBaseLogicTableDao.class);
    @Autowired
    private DataSource dataSource;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public LogicTable getLogicTable(String tableName) {
        LogicTable logicTable;
        Connection conn = null;
        try {
            conn = DataSourceUtils.getConnection((DataSource)this.dataSource);
            IDatabase database = DatabaseManager.getInstance().findDatabaseByConnection(conn);
            ISQLMetadata isqlMetadata = database.createMetadata(conn);
            logicTable = isqlMetadata.getTableByName(tableName);
        }
        catch (SQLMetadataException | SQLException e) {
            logicTable = null;
        }
        finally {
            if (conn != null) {
                DataSourceUtils.releaseConnection((Connection)conn, (DataSource)this.dataSource);
            }
        }
        return logicTable;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public List<LogicField> getLogicFields(String tableName) {
        List logicFields;
        Connection conn = null;
        try {
            conn = DataSourceUtils.getConnection((DataSource)this.dataSource);
            IDatabase database = DatabaseManager.getInstance().findDatabaseByConnection(conn);
            ISQLMetadata isqlMetadata = database.createMetadata(conn);
            logicFields = isqlMetadata.getFieldsByTableName(tableName);
        }
        catch (SQLMetadataException | SQLException e) {
            logicFields = null;
        }
        finally {
            if (conn != null) {
                DataSourceUtils.releaseConnection((Connection)conn, (DataSource)this.dataSource);
            }
        }
        return logicFields;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public LogicPrimaryKey getLogicPrimaryKey(String tableName) {
        LogicPrimaryKey logicPrimaryKey;
        Connection conn = null;
        try {
            conn = DataSourceUtils.getConnection((DataSource)this.dataSource);
            IDatabase database = DatabaseManager.getInstance().findDatabaseByConnection(conn);
            ISQLMetadata isqlMetadata = database.createMetadata(conn);
            logicPrimaryKey = isqlMetadata.getPrimaryKeyByTableName(tableName);
        }
        catch (SQLMetadataException | SQLException e) {
            logicPrimaryKey = null;
        }
        finally {
            if (conn != null) {
                DataSourceUtils.releaseConnection((Connection)conn, (DataSource)this.dataSource);
            }
        }
        return logicPrimaryKey;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void createTable(SqlStatement sqlStmt) throws Exception {
        Connection conn = null;
        try {
            conn = DataSourceUtils.getConnection((DataSource)this.dataSource);
            IDatabase database = DatabaseManager.getInstance().findDatabaseByConnection(conn);
            List sqls = sqlStmt.interpret(database, conn);
            for (String sql : sqls) {
                this.executeSql(conn, database, sql);
            }
        }
        finally {
            DataSourceUtils.releaseConnection((Connection)conn, (DataSource)this.dataSource);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void dropTable(String tableName) throws Exception {
        String sql = "drop table " + tableName;
        Connection conn = null;
        try {
            conn = DataSourceUtils.getConnection((DataSource)this.dataSource);
            IDatabase database = DatabaseManager.getInstance().findDatabaseByConnection(conn);
            this.executeSql(conn, database, sql);
        }
        finally {
            if (conn != null) {
                DataSourceUtils.releaseConnection((Connection)conn, (DataSource)this.dataSource);
            }
        }
    }

    private void executeSql(Connection conn, IDatabase database, String sql) throws Exception {
        PreparedStatement prep = null;
        try {
            prep = conn.prepareStatement(sql);
            prep.execute();
            if ((database.isDatabase("POSTGRESQL") || database.isDatabase("KINGBASE")) && !conn.getAutoCommit()) {
                conn.commit();
            }
        }
        catch (Exception e) {
            if (conn != null && (database.isDatabase("POSTGRESQL") || database.isDatabase("KINGBASE")) && !conn.getAutoCommit()) {
                conn.rollback();
            }
            throw new RuntimeException(String.format("sql[%s]\u6267\u884c\u5931\u8d25\uff1a" + e.getMessage(), sql));
        }
        finally {
            try {
                if (prep != null) {
                    prep.close();
                }
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
    }
}

