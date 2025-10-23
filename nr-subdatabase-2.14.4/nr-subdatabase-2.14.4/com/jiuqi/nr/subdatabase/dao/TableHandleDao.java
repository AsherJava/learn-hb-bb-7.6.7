/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.database.DatabaseManager
 *  com.jiuqi.bi.database.IDatabase
 *  com.jiuqi.bi.database.metadata.ISQLMetadata
 *  com.jiuqi.bi.database.metadata.SQLMetadataException
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.jdbc.datasource.DataSourceUtils
 */
package com.jiuqi.nr.subdatabase.dao;

import com.jiuqi.bi.database.DatabaseManager;
import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.bi.database.metadata.ISQLMetadata;
import com.jiuqi.bi.database.metadata.SQLMetadataException;
import com.jiuqi.nr.subdatabase.facade.SubDataBase;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Set;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;

@Repository
public class TableHandleDao {
    private static final Logger logger = LoggerFactory.getLogger(TableHandleDao.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void doCopyTable(Set<String> tableNames, SubDataBase subDataBase) throws Exception {
        for (String tableName : tableNames) {
            StringBuilder sql = new StringBuilder();
            sql.append("CREATE TABLE ").append(subDataBase.getCode()).append(tableName).append(" AS SELECT * FROM ").append(tableName).append(" WHERE 1=2 ");
            this.jdbcTemplate.execute(sql.toString());
        }
    }

    public void deleteCopyTable(Set<String> tableNames, SubDataBase subDataBase) {
        for (String tableName : tableNames) {
            StringBuilder sql = new StringBuilder();
            sql.append("DROP TABLE ").append(subDataBase.getCode()).append(tableName);
            try {
                this.jdbcTemplate.execute(sql.toString());
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
    }

    public boolean tableExist(Set<String> tableNames, String code) throws SQLException, SQLMetadataException {
        for (String tableName : tableNames) {
            boolean b = this.tableExist(code + tableName);
            if (!b) continue;
            return true;
        }
        return false;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public boolean tableExist(String tableName) throws SQLException, SQLMetadataException {
        Connection conn = null;
        try {
            conn = DataSourceUtils.getConnection((DataSource)this.jdbcTemplate.getDataSource());
            IDatabase database = DatabaseManager.getInstance().findDatabaseByConnection(conn);
            ISQLMetadata sqlMetadata = database.createMetadata(conn);
            boolean bl = sqlMetadata.getTableByName(tableName, sqlMetadata.getDefaultSchema()) != null;
            return bl;
        }
        finally {
            if (conn != null) {
                DataSourceUtils.releaseConnection((Connection)conn, (DataSource)this.jdbcTemplate.getDataSource());
            }
        }
    }

    public void DropTable(String orgCategoryName) {
        String sql_1 = "DROP TABLE %s";
        this.jdbcTemplate.execute(String.format(sql_1, orgCategoryName));
        String sql_2 = "DROP TABLE %s";
        this.jdbcTemplate.execute(String.format(sql_2, orgCategoryName) + "_SUBLIST");
    }
}

