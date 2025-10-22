/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.database.DBException
 *  com.jiuqi.bi.database.DatabaseManager
 *  com.jiuqi.bi.database.IDatabase
 *  com.jiuqi.bi.database.paging.IPagingSQLBuilder
 *  com.jiuqi.np.dataengine.common.DataEngineUtil
 *  com.jiuqi.nr.common.temptable.ITempTable
 *  com.jiuqi.nr.common.temptable.ITempTableManager
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.jdbc.datasource.DataSourceUtils
 *  org.springframework.jdbc.support.rowset.SqlRowSet
 */
package com.jiuqi.nr.dataentity_ext.internal.db;

import com.jiuqi.bi.database.DBException;
import com.jiuqi.bi.database.DatabaseManager;
import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.bi.database.paging.IPagingSQLBuilder;
import com.jiuqi.np.dataengine.common.DataEngineUtil;
import com.jiuqi.nr.common.temptable.ITempTable;
import com.jiuqi.nr.common.temptable.ITempTableManager;
import com.jiuqi.nr.dataentity_ext.dto.EntityDataType;
import com.jiuqi.nr.dataentity_ext.dto.PageInfo;
import com.jiuqi.nr.dataentity_ext.internal.db.EntityDataDO;
import com.jiuqi.nr.dataentity_ext.internal.db.QueryModel;
import com.jiuqi.nr.dataentity_ext.internal.db.TypeCount;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Repository
public class EntityDataDao {
    private static final Logger logger = LoggerFactory.getLogger(EntityDataDao.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private DataSource dataSource;
    @Autowired
    private ITempTableManager tempTableManager;
    private static final String MAIN_TABLE_ALIA = "t0";
    private static final String TMP_TABLE_ALIA = "tt";
    private static final String COUNT_ALIA = "RR";
    private static final String GROUP_ALIA = "GG";

    public void insertTableInfo(String tableName) {
        String sql = "insert into NR_ENTITY_EXT (TABLE_NAME,CREATE_TIME) values(?,?)";
        this.jdbcTemplate.update(sql, new Object[]{tableName, System.currentTimeMillis()});
    }

    public List<String> listTableNamesByTime(long time) {
        String sql = "select TABLE_NAME from NR_ENTITY_EXT where CREATE_TIME <= ?";
        long currentTimeMillis = System.currentTimeMillis();
        long timeArg = currentTimeMillis - time;
        return this.jdbcTemplate.queryForList(sql, String.class, new Object[]{timeArg});
    }

    public void deleteTableInfo(List<String> tableNames) {
        String sql;
        if (CollectionUtils.isEmpty(tableNames)) {
            return;
        }
        if (tableNames.size() == 1) {
            sql = "delete from NR_ENTITY_EXT where TABLE_NAME = ?";
        } else {
            StringBuilder sb = new StringBuilder("delete from NR_ENTITY_EXT where TABLE_NAME in(");
            tableNames.forEach(tableName -> sb.append("?,"));
            sb.deleteCharAt(sb.length() - 1);
            sb.append(")");
            sql = sb.toString();
        }
        this.jdbcTemplate.update(sql, tableNames.toArray());
    }

    public void dropEntityTables(List<String> tableNames) {
        if (CollectionUtils.isEmpty(tableNames)) {
            return;
        }
        for (String tableName : tableNames) {
            String sql = "drop table " + tableName;
            this.jdbcTemplate.execute(sql);
        }
    }

    public void batchInsert(String tableName, List<EntityDataDO> list) {
        if (CollectionUtils.isEmpty(list)) {
            return;
        }
        String sql = "insert into " + tableName + "(" + "E_KEY" + "," + "E_CODE" + "," + "E_TITLE" + "," + "E_PARENT" + "," + "E_PATH" + "," + "E_TYPE" + "," + "E_ORDER" + "," + "E_LEAF" + "," + "E_CHILDCOUNT" + "," + "E_ALLCHILDCOUNT" + ") values(?,?,?,?,?,?,?,?,?,?)";
        this.jdbcTemplate.batchUpdate(sql, list, 1000, (ps, argument) -> {
            ps.setString(1, argument.getKey());
            ps.setString(2, argument.getCode());
            ps.setString(3, argument.getTitle());
            ps.setString(4, argument.getParent());
            ps.setString(5, argument.getPath());
            ps.setInt(6, argument.getType());
            ps.setBigDecimal(7, argument.getOrder());
            ps.setInt(8, argument.getLeaf());
            ps.setInt(9, argument.getChildCount());
            ps.setInt(10, argument.getAllChildCount());
        });
    }

    public EntityDataDO selectByKey(String tableName, String key) {
        String sql = "select " + EntityDataDao.buildAliaColumn("E_KEY") + "," + EntityDataDao.buildAliaColumn("E_CODE") + "," + EntityDataDao.buildAliaColumn("E_TITLE") + "," + EntityDataDao.buildAliaColumn("E_PARENT") + "," + EntityDataDao.buildAliaColumn("E_PATH") + "," + EntityDataDao.buildAliaColumn("E_TYPE") + "," + EntityDataDao.buildAliaColumn("E_ORDER") + "," + EntityDataDao.buildAliaColumn("E_LEAF") + "," + EntityDataDao.buildAliaColumn("E_CHILDCOUNT") + "," + EntityDataDao.buildAliaColumn("E_ALLCHILDCOUNT") + " from " + EntityDataDao.buildAliaTable(tableName) + " where " + EntityDataDao.buildAliaColumn("E_KEY") + " = ?";
        return (EntityDataDO)this.jdbcTemplate.query(sql, rs -> {
            if (rs.next()) {
                EntityDataDO entityDataDO = new EntityDataDO();
                entityDataDO.setKey(rs.getString(1));
                entityDataDO.setCode(rs.getString(2));
                entityDataDO.setTitle(rs.getString(3));
                entityDataDO.setParent(rs.getString(4));
                entityDataDO.setPath(rs.getString(5));
                entityDataDO.setType(rs.getInt(6));
                entityDataDO.setOrder(rs.getBigDecimal(7));
                entityDataDO.setLeaf(rs.getInt(8));
                entityDataDO.setChildCount(rs.getInt(9));
                entityDataDO.setAllChildCount(rs.getInt(10));
                return entityDataDO;
            }
            return null;
        }, new Object[]{key});
    }

    public int selectDirChildCount(String tableName, String key) {
        String sql = "select " + EntityDataDao.buildAliaColumn("E_CHILDCOUNT") + " from " + EntityDataDao.buildAliaTable(tableName) + " where " + EntityDataDao.buildAliaColumn("E_KEY") + " = ?";
        return (Integer)this.jdbcTemplate.queryForObject(sql, Integer.class, new Object[]{key});
    }

    public int selectAllChildCount(String tableName, String key) {
        String sql = "select " + EntityDataDao.buildAliaColumn("E_ALLCHILDCOUNT") + " from " + EntityDataDao.buildAliaTable(tableName) + " where " + EntityDataDao.buildAliaColumn("E_KEY") + " = ?";
        return (Integer)this.jdbcTemplate.queryForObject(sql, Integer.class, new Object[]{key});
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public List<EntityDataDO> list(QueryModel queryModel) throws SQLException, DBException {
        List list;
        ITempTable tempTable;
        block5: {
            Connection conn = null;
            tempTable = null;
            try {
                conn = DataSourceUtils.getConnection((DataSource)this.dataSource);
                IDatabase database = EntityDataDao.getDatabase(conn);
                SqlInfo sqlInfo = this.buildSelectSql(queryModel, database);
                tempTable = sqlInfo.tempTable;
                String sql = sqlInfo.sql;
                if (queryModel.isSort()) {
                    sql = sql + " order by " + EntityDataDao.buildAliaColumn("E_ORDER");
                }
                if (queryModel.getPageInfo() != null) {
                    sql = EntityDataDao.appendPageIfNeed(new StringBuilder(sql), queryModel.getPageInfo(), database);
                }
                list = this.jdbcTemplate.query(sql, sqlInfo.sqlArgs.stream().map(o -> o.value).toArray(), sqlInfo.sqlArgs.stream().mapToInt(o -> o.type).toArray(), (rs, rowNum) -> {
                    EntityDataDO entityDataDO = new EntityDataDO();
                    entityDataDO.setKey(rs.getString(1));
                    entityDataDO.setCode(rs.getString(2));
                    entityDataDO.setTitle(rs.getString(3));
                    entityDataDO.setParent(rs.getString(4));
                    entityDataDO.setPath(rs.getString(5));
                    entityDataDO.setType(rs.getInt(6));
                    entityDataDO.setOrder(rs.getBigDecimal(7));
                    entityDataDO.setLeaf(rs.getInt(8));
                    entityDataDO.setChildCount(rs.getInt(9));
                    entityDataDO.setAllChildCount(rs.getInt(10));
                    return entityDataDO;
                });
                if (conn == null) break block5;
            }
            catch (Throwable throwable) {
                if (conn != null) {
                    DataSourceUtils.releaseConnection((Connection)conn, (DataSource)this.dataSource);
                }
                EntityDataDao.closeTmpTable(tempTable);
                throw throwable;
            }
            DataSourceUtils.releaseConnection((Connection)conn, (DataSource)this.dataSource);
        }
        EntityDataDao.closeTmpTable(tempTable);
        return list;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public List<String> listKeys(QueryModel queryModel) throws SQLException, DBException {
        List list;
        ITempTable tempTable;
        block5: {
            Connection conn = null;
            tempTable = null;
            try {
                conn = DataSourceUtils.getConnection((DataSource)this.dataSource);
                IDatabase database = EntityDataDao.getDatabase(conn);
                SqlInfo sqlInfo = this.buildSelectKeySql(queryModel, database);
                tempTable = sqlInfo.tempTable;
                String sql = sqlInfo.sql;
                if (queryModel.isSort()) {
                    sql = EntityDataDao.appendOrder(sql, false);
                }
                if (queryModel.getPageInfo() != null) {
                    sql = EntityDataDao.appendPageIfNeed(new StringBuilder(sql), queryModel.getPageInfo(), database);
                }
                list = this.jdbcTemplate.query(sql, sqlInfo.sqlArgs.stream().map(o -> o.value).toArray(), sqlInfo.sqlArgs.stream().mapToInt(o -> o.type).toArray(), (rs, rowNum) -> rs.getString(1));
                if (conn == null) break block5;
            }
            catch (Throwable throwable) {
                if (conn != null) {
                    DataSourceUtils.releaseConnection((Connection)conn, (DataSource)this.dataSource);
                }
                EntityDataDao.closeTmpTable(tempTable);
                throw throwable;
            }
            DataSourceUtils.releaseConnection((Connection)conn, (DataSource)this.dataSource);
        }
        EntityDataDao.closeTmpTable(tempTable);
        return list;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public List<TypeCount> countType(QueryModel queryModel) throws SQLException {
        ArrayList<TypeCount> arrayList;
        ITempTable tempTable;
        block4: {
            Connection conn = null;
            tempTable = null;
            try {
                conn = DataSourceUtils.getConnection((DataSource)this.dataSource);
                IDatabase database = EntityDataDao.getDatabase(conn);
                SqlInfo sqlInfo = this.buildCountGroupSql(queryModel, database);
                tempTable = sqlInfo.tempTable;
                SqlRowSet sqlRowSet = this.jdbcTemplate.queryForRowSet(sqlInfo.sql, sqlInfo.sqlArgs.stream().map(o -> o.value).toArray(), sqlInfo.sqlArgs.stream().mapToInt(o -> o.type).toArray());
                ArrayList<TypeCount> typeCounts = new ArrayList<TypeCount>();
                while (sqlRowSet.next()) {
                    typeCounts.add(new TypeCount(sqlRowSet.getInt(2), sqlRowSet.getInt(1)));
                }
                arrayList = typeCounts;
                if (conn == null) break block4;
            }
            catch (Throwable throwable) {
                if (conn != null) {
                    DataSourceUtils.releaseConnection((Connection)conn, (DataSource)this.dataSource);
                }
                EntityDataDao.closeTmpTable(tempTable);
                throw throwable;
            }
            DataSourceUtils.releaseConnection((Connection)conn, (DataSource)this.dataSource);
        }
        EntityDataDao.closeTmpTable(tempTable);
        return arrayList;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public int count(QueryModel queryModel) throws SQLException {
        int n;
        ITempTable tempTable;
        block3: {
            Connection conn = null;
            tempTable = null;
            try {
                conn = DataSourceUtils.getConnection((DataSource)this.dataSource);
                IDatabase database = EntityDataDao.getDatabase(conn);
                SqlInfo sqlInfo = this.buildCountSql(queryModel, database);
                tempTable = sqlInfo.tempTable;
                Integer r = (Integer)this.jdbcTemplate.queryForObject(sqlInfo.sql, sqlInfo.sqlArgs.stream().map(o -> o.value).toArray(), sqlInfo.sqlArgs.stream().mapToInt(o -> o.type).toArray(), Integer.class);
                int n2 = n = r == null ? 0 : r;
                if (conn == null) break block3;
            }
            catch (Throwable throwable) {
                if (conn != null) {
                    DataSourceUtils.releaseConnection((Connection)conn, (DataSource)this.dataSource);
                }
                EntityDataDao.closeTmpTable(tempTable);
                throw throwable;
            }
            DataSourceUtils.releaseConnection((Connection)conn, (DataSource)this.dataSource);
        }
        EntityDataDao.closeTmpTable(tempTable);
        return n;
    }

    private SqlInfo buildCountSql(QueryModel queryModel, IDatabase database) throws SQLException {
        SqlInfo sqlInfo = this.buildSelectSql(queryModel, database);
        return new SqlInfo(EntityDataDao.buildItemCountSql(sqlInfo.sql).toString(), sqlInfo.sqlArgs, sqlInfo.tempTable);
    }

    private SqlInfo buildCountGroupSql(QueryModel queryModel, IDatabase database) throws SQLException {
        SqlInfo sqlInfo = this.buildSelectSql(queryModel, database);
        String sql = "select count(1),E_TYPE from (" + sqlInfo.sql + ") " + GROUP_ALIA + " group by " + GROUP_ALIA + "." + "E_TYPE";
        return new SqlInfo(sql, sqlInfo.sqlArgs, sqlInfo.tempTable);
    }

    private static void closeTmpTable(ITempTable tempTable) {
        if (tempTable != null) {
            try {
                tempTable.close();
            }
            catch (IOException e) {
                String msg = "\u5220\u9664\u4e34\u65f6\u8868\u5f02\u5e38:" + e.getMessage();
                logger.error(msg, e);
            }
        }
    }

    private SqlInfo buildSelectSql(QueryModel queryModel, IDatabase database) throws SQLException {
        String sql = "select " + EntityDataDao.buildAliaColumn("E_KEY") + "," + EntityDataDao.buildAliaColumn("E_CODE") + "," + EntityDataDao.buildAliaColumn("E_TITLE") + "," + EntityDataDao.buildAliaColumn("E_PARENT") + "," + EntityDataDao.buildAliaColumn("E_PATH") + "," + EntityDataDao.buildAliaColumn("E_TYPE") + "," + EntityDataDao.buildAliaColumn("E_ORDER") + "," + EntityDataDao.buildAliaColumn("E_LEAF") + "," + EntityDataDao.buildAliaColumn("E_CHILDCOUNT") + "," + EntityDataDao.buildAliaColumn("E_ALLCHILDCOUNT") + " from " + EntityDataDao.buildAliaTable(queryModel.getTableName());
        SqlInfo sqlInfo = this.buildWhereSql(queryModel, database);
        return new SqlInfo(sql + sqlInfo.sql, sqlInfo.sqlArgs, sqlInfo.tempTable);
    }

    private SqlInfo buildSelectKeySql(QueryModel queryModel, IDatabase database) throws SQLException {
        String sql = "select " + EntityDataDao.buildAliaColumn("E_KEY") + " from " + EntityDataDao.buildAliaTable(queryModel.getTableName());
        SqlInfo sqlInfo = this.buildWhereSql(queryModel, database);
        return new SqlInfo(sql + sqlInfo.sql, sqlInfo.sqlArgs, sqlInfo.tempTable);
    }

    private SqlInfo buildWhereSql(QueryModel queryModel, IDatabase database) throws SQLException {
        SqlInfo sqlInfo = this.appendKeysCon(queryModel.getKeys(), database).appendCon(EntityDataDao.appendParentCon(queryModel.getParents()).appendCon(EntityDataDao.appendTypeCon(queryModel.getTypes() == null ? null : queryModel.getTypes().stream().mapToInt(EntityDataType::getCode).toArray())).appendCon(EntityDataDao.appendLeafCon(queryModel.getLeaf())).appendCon(EntityDataDao.appendKeywordsCon(queryModel.getKeyWords())));
        if (!StringUtils.hasText(sqlInfo.sql)) {
            return new SqlInfo();
        }
        String sb = " where " + sqlInfo.sql;
        return new SqlInfo(sb, sqlInfo.sqlArgs, sqlInfo.tempTable);
    }

    private SqlInfo appendKeysCon(List<String> keys, IDatabase database) throws SQLException {
        if (CollectionUtils.isEmpty(keys)) {
            return new SqlInfo();
        }
        StringBuilder sb = new StringBuilder();
        sb.append(" ").append(EntityDataDao.buildAliaColumn("E_KEY"));
        if (keys.size() == 1) {
            sb.append("=?");
            return new SqlInfo(sb.toString(), Collections.singletonList(new SqlArg(keys.get(0), 12)));
        }
        int maxInSize = DataEngineUtil.getMaxInSize((IDatabase)database);
        if (keys.size() < maxInSize) {
            EntityDataDao.buildInSql(sb, keys.size());
            ArrayList<SqlArg> args = new ArrayList<SqlArg>(keys.size());
            for (String key : keys) {
                args.add(new SqlArg(key, 12));
            }
            return new SqlInfo(sb.toString(), args);
        }
        ITempTable tempTable = this.initTemp(keys);
        return new SqlInfo(EntityDataDao.getExistsSelectSql(tempTable, EntityDataDao.buildAliaColumn("E_KEY")), null, tempTable);
    }

    private ITempTable initTemp(List<String> keys) throws SQLException {
        ITempTable tempTable = this.tempTableManager.getOneKeyTempTable();
        ArrayList<Object[]> batchValues = new ArrayList<Object[]>();
        for (String s : keys) {
            Object[] batchArr = new Object[]{s};
            batchValues.add(batchArr);
        }
        tempTable.insertRecords(batchValues);
        return tempTable;
    }

    private static String getExistsSelectSql(ITempTable tempTable, String relationCol) {
        return " exists (Select 1 From " + tempTable.getTableName() + " " + TMP_TABLE_ALIA + " where " + TMP_TABLE_ALIA + "." + "TEMP_KEY" + "=" + relationCol + ")";
    }

    private static StringBuilder buildItemCountSql(String selectSql) {
        StringBuilder sql = new StringBuilder();
        sql.append("select count(1) AS RECORD_COUNT from (").append(selectSql).append(") ").append(COUNT_ALIA);
        return sql;
    }

    private static String buildAliaTable(String tableName) {
        return tableName + " " + MAIN_TABLE_ALIA;
    }

    private static String buildAliaColumn(String columnName) {
        return "t0." + columnName;
    }

    private static SqlInfo appendTypeCon(int[] types) {
        if (types == null || types.length == 0) {
            return new SqlInfo();
        }
        StringBuilder sql = new StringBuilder();
        sql.append(" ").append(EntityDataDao.buildAliaColumn("E_TYPE"));
        if (types.length == 1) {
            sql.append("=?");
            return new SqlInfo(sql.toString(), Collections.singletonList(new SqlArg(types[0], 4)));
        }
        EntityDataDao.buildInSql(sql, types.length);
        ArrayList<SqlArg> args = new ArrayList<SqlArg>(types.length);
        for (int type : types) {
            args.add(new SqlArg(type, 4));
        }
        return new SqlInfo(sql.toString(), args);
    }

    private static SqlInfo appendLeafCon(Boolean leaf) {
        if (leaf == null) {
            return new SqlInfo();
        }
        String sql = " " + EntityDataDao.buildAliaColumn("E_LEAF") + "=?";
        return new SqlInfo(sql, Collections.singletonList(new SqlArg(leaf != false ? 1 : 0, 4)));
    }

    private static SqlInfo appendParentCon(List<String> parents) {
        if (CollectionUtils.isEmpty(parents)) {
            return new SqlInfo();
        }
        StringBuilder sql = new StringBuilder();
        sql.append(" ").append(EntityDataDao.buildAliaColumn("E_PARENT"));
        if (parents.size() == 1) {
            sql.append("=?");
            return new SqlInfo(sql.toString(), Collections.singletonList(new SqlArg(parents.get(0), 12)));
        }
        EntityDataDao.buildInSql(sql, parents.size());
        ArrayList<SqlArg> args = new ArrayList<SqlArg>(parents.size());
        for (String parent : parents) {
            args.add(new SqlArg(parent, 12));
        }
        return new SqlInfo(sql.toString(), args);
    }

    private static SqlInfo appendKeywordsCon(List<String> keywords) {
        if (CollectionUtils.isEmpty(keywords)) {
            return new SqlInfo();
        }
        StringBuilder sql = new StringBuilder();
        sql.append(" (");
        for (int i = 0; i < keywords.size(); ++i) {
            if (i > 0) {
                sql.append(" or ");
            }
            sql.append(" ").append(EntityDataDao.buildAliaColumn("E_CODE")).append(" like ?");
            sql.append(" or ").append(EntityDataDao.buildAliaColumn("E_TITLE")).append(" like ?");
        }
        sql.append(")");
        ArrayList<SqlArg> args = new ArrayList<SqlArg>(keywords.size());
        for (String keyword : keywords) {
            args.add(new SqlArg("%" + keyword + "%", 12));
            args.add(new SqlArg("%" + keyword + "%", 12));
        }
        return new SqlInfo(sql.toString(), args);
    }

    private static void buildInSql(StringBuilder sb, int inSize) {
        sb.append(" in(");
        for (int i = 0; i < inSize; ++i) {
            sb.append("?,");
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append(")");
    }

    private static String appendOrder(String sql, boolean desc) {
        return sql + " order by " + (desc ? EntityDataDao.buildAliaColumn("E_ORDER") + " desc" : EntityDataDao.buildAliaColumn("E_ORDER"));
    }

    private static String appendPageIfNeed(StringBuilder sql, PageInfo pageInfo, IDatabase database) throws DBException {
        int start = pageInfo.getPageIndex() * pageInfo.getRowsPerPage();
        int end = start + pageInfo.getRowsPerPage();
        if (start < 0 || end < start) {
            return sql.toString();
        }
        IPagingSQLBuilder sqlBuilder = database.createPagingSQLBuilder();
        sqlBuilder.setRawSQL(sql.toString());
        return sqlBuilder.buildSQL(start, end);
    }

    private static IDatabase getDatabase(Connection conn) throws SQLException {
        return DatabaseManager.getInstance().findDatabaseByConnection(conn);
    }

    private static class SqlArg {
        Object value;
        int type;

        public SqlArg(Object value, int type) {
            this.value = value;
            this.type = type;
        }
    }

    private static class SqlInfo {
        String sql = "";
        List<SqlArg> sqlArgs = new ArrayList<SqlArg>();
        ITempTable tempTable = null;

        public SqlInfo() {
        }

        public SqlInfo(String sql, List<SqlArg> sqlArgs) {
            this.sql = sql == null ? "" : sql;
            this.sqlArgs = sqlArgs == null ? new ArrayList<SqlArg>() : new ArrayList<SqlArg>(sqlArgs);
        }

        public SqlInfo(String sql, List<SqlArg> sqlArgs, ITempTable tempTable) {
            this.sql = sql;
            this.sqlArgs = sqlArgs == null ? new ArrayList<SqlArg>() : new ArrayList<SqlArg>(sqlArgs);
            this.tempTable = tempTable;
        }

        SqlInfo appendCon(SqlInfo sqlInfo) {
            if (!StringUtils.hasText(sqlInfo.sql)) {
                return this;
            }
            if (StringUtils.hasText(this.sql)) {
                this.sql = this.sql + " and ";
            }
            this.sql = this.sql + sqlInfo.sql;
            this.sqlArgs.addAll(sqlInfo.sqlArgs);
            return this;
        }
    }
}

