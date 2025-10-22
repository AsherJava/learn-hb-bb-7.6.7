/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.database.DatabaseManager
 *  com.jiuqi.bi.database.IDatabase
 *  com.jiuqi.bi.database.metadata.ISQLMetadata
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.jdbc.datasource.DataSourceUtils
 */
package com.jiuqi.nr.integritycheck.utils;

import com.jiuqi.bi.database.DatabaseManager;
import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.bi.database.metadata.ISQLMetadata;
import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Component;

@Component
public class DBTypeUtil {
    private static final Logger logger = LoggerFactory.getLogger(DBTypeUtil.class);
    private static volatile DbType dbType;
    @Autowired
    private JdbcTemplate jdbcTpl;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public DbType getDbType() {
        if (dbType != null) return dbType;
        Class<DBTypeUtil> clazz = DBTypeUtil.class;
        synchronized (DBTypeUtil.class) {
            if (dbType != null) return dbType;
            dbType = this.detectDbType();
            // ** MonitorExit[var1_1] (shouldn't be in output)
            return dbType;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private DbType detectDbType() {
        Connection conn = null;
        try {
            conn = DataSourceUtils.getConnection((DataSource)this.jdbcTpl.getDataSource());
            IDatabase database = DatabaseManager.getInstance().findDatabaseByConnection(conn);
            ISQLMetadata isqlMetadata = database.createMetadata(conn);
            String metadataClassName = isqlMetadata.getClass().getName().toLowerCase();
            if (metadataClassName.contains("mysql")) {
                DbType dbType = DbType.MYSQL;
                return dbType;
            }
            if (metadataClassName.contains("derby")) {
                DbType dbType = DbType.DERBY;
                return dbType;
            }
            if (metadataClassName.contains("postgresql")) {
                DbType dbType = DbType.POSTGRESQL;
                return dbType;
            }
            if (metadataClassName.contains("dm")) {
                DbType dbType = DbType.DM;
                return dbType;
            }
            if (metadataClassName.contains("oracle")) {
                DbType dbType = DbType.ORACLE;
                return dbType;
            }
            if (metadataClassName.contains("kingbase")) {
                DbType dbType = DbType.KINGBASE;
                return dbType;
            }
            if (metadataClassName.contains("oscar")) {
                DbType dbType = DbType.OSCAR;
                return dbType;
            }
            DbType dbType = DbType.UNKNOWN;
            return dbType;
        }
        catch (SQLException e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            DbType dbType = DbType.UNKNOWN;
            return dbType;
        }
        finally {
            if (conn != null) {
                try {
                    conn.close();
                }
                catch (SQLException e) {
                    logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                }
            }
        }
    }

    public static enum DbType {
        MYSQL,
        DERBY,
        POSTGRESQL,
        DM,
        ORACLE,
        KINGBASE,
        OSCAR,
        UNKNOWN;

    }
}

