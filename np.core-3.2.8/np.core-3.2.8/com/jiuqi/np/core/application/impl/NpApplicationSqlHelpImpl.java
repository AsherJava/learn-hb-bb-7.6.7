/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.database.DatabaseManager
 *  com.jiuqi.bi.database.IDatabase
 *  com.jiuqi.bi.database.sql.parser.SQLInterpretException
 *  com.jiuqi.bi.database.sql.parser.SQLParser
 *  com.jiuqi.bi.database.sql.parser.SQLParserException
 *  com.jiuqi.bi.database.statement.SqlStatement
 *  org.springframework.dao.DataAccessException
 *  org.springframework.jdbc.core.ResultSetExtractor
 *  org.springframework.jdbc.core.RowMapper
 *  org.springframework.jdbc.core.namedparam.MapSqlParameterSource
 *  org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
 *  org.springframework.jdbc.core.namedparam.SqlParameterSource
 *  org.springframework.jdbc.datasource.DataSourceUtils
 */
package com.jiuqi.np.core.application.impl;

import com.jiuqi.bi.database.DatabaseManager;
import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.bi.database.sql.parser.SQLInterpretException;
import com.jiuqi.bi.database.sql.parser.SQLParser;
import com.jiuqi.bi.database.sql.parser.SQLParserException;
import com.jiuqi.bi.database.statement.SqlStatement;
import com.jiuqi.np.core.application.NpApplicationSqlHelp;
import com.jiuqi.np.core.model.InSqlModel;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.util.StringUtils;

public class NpApplicationSqlHelpImpl
implements NpApplicationSqlHelp {
    private static final String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    public static final Logger LOGGER = LoggerFactory.getLogger(NpApplicationSqlHelpImpl.class);
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public InSqlModel buildInSql(String prefix, String alias, List<String> values, String column, String arg, boolean addWhere, String orderBySql) {
        InSqlModel inSqlBuild = new InSqlModel();
        inSqlBuild.setPrefix(prefix);
        inSqlBuild.setAlias(alias);
        inSqlBuild.setValues(values);
        inSqlBuild.setColumn(column);
        inSqlBuild.setArg(arg);
        inSqlBuild.setAddWhere(addWhere);
        inSqlBuild.setAddAnd(!addWhere);
        inSqlBuild.setOrderBySql(orderBySql);
        return inSqlBuild;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Unable to fully structure code
     */
    @Override
    public <T> T query(String sql, Map<String, Object> values, InSqlModel inModel, ResultSetExtractor<T> rse) throws DataAccessException {
        block31: {
            if (null == values) {
                values = new HashMap<String, Object>();
            }
            stringBuilder = new StringBuilder();
            stringBuilder.append(sql);
            if (inModel.isAddWhere()) {
                stringBuilder.append(" where ");
            }
            inValues = inModel.getValues();
            tempTableName = null;
            connection = null;
            result = null;
            if (inValues.size() > 1000) {
                valueSet = new HashSet<String>();
                valueSet.addAll(inValues);
                inValues = valueSet.stream().collect(Collectors.toList());
                if (inValues.size() > 1000) {
                    if (inModel.isAddAnd()) {
                        stringBuilder.append(" and ");
                    }
                    tempTableName = this.getTempTableName(inModel.getPrefix());
                    connection = DataSourceUtils.getConnection((DataSource)this.jdbcTemplate.getJdbcTemplate().getDataSource());
                    this.createTempTable(connection, tempTableName);
                    this.insertIntoTempTable(connection, tempTableName, inValues);
                    stringBuilder.append(" EXISTS(select TEM_CODE from ").append(tempTableName);
                    stringBuilder.append(" TempTable where ").append(inModel.getAlias()).append(".").append(inModel.getColumn());
                    stringBuilder.append("= TempTable.TEM_CODE) ");
                } else {
                    if (inModel.isAddAnd()) {
                        stringBuilder.append(" and ");
                    }
                    stringBuilder.append(String.format(" %s.%s in (:%s)", new Object[]{inModel.getAlias(), inModel.getColumn(), inModel.getArg()}));
                    values.put(inModel.getArg(), inValues);
                }
            } else {
                if (inModel.isAddAnd()) {
                    stringBuilder.append(" and ");
                }
                stringBuilder.append(String.format(" %s.%s in (:%s)", new Object[]{inModel.getAlias(), inModel.getColumn(), inModel.getArg()}));
                values.put(inModel.getArg(), inValues);
            }
            if (StringUtils.hasLength(inModel.getOrderBySql())) {
                stringBuilder.append(inModel.getOrderBySql());
            }
            parameterSource = new MapSqlParameterSource();
            parameterSource.addValues(values);
            result = this.jdbcTemplate.query(stringBuilder.toString(), (SqlParameterSource)parameterSource, rse);
            ** try [egrp 1[TRYBLOCK] [0 : 419->438)] { 
lbl-1000:
            // 1 sources

            {
                if (StringUtils.hasLength(tempTableName)) {
                    this.dropTempTable(connection, tempTableName);
                }
            }
lbl61:
            // 1 sources

            catch (Exception e) {
                NpApplicationSqlHelpImpl.LOGGER.error("\u67e5\u8be2\u62a5\u9519\uff01", e);
            }
            try {
                if (connection != null) {
                    DataSourceUtils.releaseConnection((Connection)connection, (DataSource)this.jdbcTemplate.getJdbcTemplate().getDataSource());
                }
                break block31;
            }
            catch (Exception e) {
                NpApplicationSqlHelpImpl.LOGGER.error("\u67e5\u8be2\u62a5\u9519\uff01", e);
            }
            break block31;
            catch (Exception e) {
                NpApplicationSqlHelpImpl.LOGGER.error("\u67e5\u8be2\u62a5\u9519\uff01", e);
                break block31;
            }
            finally {
                try {
                    if (StringUtils.hasLength(tempTableName)) {
                        this.dropTempTable(connection, tempTableName);
                    }
                }
                catch (Exception e) {
                    NpApplicationSqlHelpImpl.LOGGER.error("\u67e5\u8be2\u62a5\u9519\uff01", e);
                }
                try {
                    if (connection != null) {
                        DataSourceUtils.releaseConnection(connection, (DataSource)this.jdbcTemplate.getJdbcTemplate().getDataSource());
                    }
                }
                catch (Exception e) {
                    NpApplicationSqlHelpImpl.LOGGER.error("\u67e5\u8be2\u62a5\u9519\uff01", e);
                }
            }
        }
        return (T)result;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Unable to fully structure code
     */
    @Override
    public <T> List<T> query(String sql, Map<String, Object> values, InSqlModel inModel, RowMapper<T> rowMapper) throws DataAccessException {
        block31: {
            if (null == values) {
                values = new HashMap<String, Object>();
            }
            stringBuilder = new StringBuilder();
            stringBuilder.append(sql);
            if (inModel.isAddWhere()) {
                stringBuilder.append(" where ");
            }
            inValues = inModel.getValues();
            tempTableName = null;
            connection = null;
            result = null;
            if (inValues.size() > 1000) {
                valueSet = new HashSet<String>();
                valueSet.addAll(inValues);
                inValues = valueSet.stream().collect(Collectors.toList());
                if (inValues.size() > 1000) {
                    if (inModel.isAddAnd()) {
                        stringBuilder.append(" and ");
                    }
                    tempTableName = this.getTempTableName(inModel.getPrefix());
                    connection = DataSourceUtils.getConnection((DataSource)this.jdbcTemplate.getJdbcTemplate().getDataSource());
                    this.createTempTable(connection, tempTableName);
                    this.insertIntoTempTable(connection, tempTableName, inValues);
                    stringBuilder.append(" EXISTS(select TEM_CODE from ").append(tempTableName);
                    stringBuilder.append(" TempTable where ").append(inModel.getAlias()).append(".").append(inModel.getColumn());
                    stringBuilder.append("= TempTable.TEM_CODE) ");
                } else {
                    if (inModel.isAddAnd()) {
                        stringBuilder.append(" and ");
                    }
                    stringBuilder.append(String.format(" %s.%s in (:%s)", new Object[]{inModel.getAlias(), inModel.getColumn(), inModel.getArg()}));
                    values.put(inModel.getArg(), inValues);
                }
            } else {
                if (inModel.isAddAnd()) {
                    stringBuilder.append(" and ");
                }
                stringBuilder.append(String.format(" %s.%s in (:%s)", new Object[]{inModel.getAlias(), inModel.getColumn(), inModel.getArg()}));
                values.put(inModel.getArg(), inValues);
            }
            if (StringUtils.hasLength(inModel.getOrderBySql())) {
                stringBuilder.append(inModel.getOrderBySql());
            }
            parameterSource = new MapSqlParameterSource();
            parameterSource.addValues(values);
            result = this.jdbcTemplate.query(stringBuilder.toString(), (SqlParameterSource)parameterSource, rowMapper);
            ** try [egrp 1[TRYBLOCK] [0 : 419->438)] { 
lbl-1000:
            // 1 sources

            {
                if (StringUtils.hasLength(tempTableName)) {
                    this.dropTempTable(connection, tempTableName);
                }
            }
lbl61:
            // 1 sources

            catch (Exception e) {
                NpApplicationSqlHelpImpl.LOGGER.error("\u67e5\u8be2\u62a5\u9519\uff01", e);
            }
            try {
                if (connection != null) {
                    DataSourceUtils.releaseConnection((Connection)connection, (DataSource)this.jdbcTemplate.getJdbcTemplate().getDataSource());
                }
                break block31;
            }
            catch (Exception e) {
                NpApplicationSqlHelpImpl.LOGGER.error("\u67e5\u8be2\u62a5\u9519\uff01", e);
            }
            break block31;
            catch (Exception e) {
                NpApplicationSqlHelpImpl.LOGGER.error("\u67e5\u8be2\u62a5\u9519\uff01", e);
                break block31;
            }
            finally {
                try {
                    if (StringUtils.hasLength(tempTableName)) {
                        this.dropTempTable(connection, tempTableName);
                    }
                }
                catch (Exception e) {
                    NpApplicationSqlHelpImpl.LOGGER.error("\u67e5\u8be2\u62a5\u9519\uff01", e);
                }
                try {
                    if (connection != null) {
                        DataSourceUtils.releaseConnection(connection, (DataSource)this.jdbcTemplate.getJdbcTemplate().getDataSource());
                    }
                }
                catch (Exception e) {
                    NpApplicationSqlHelpImpl.LOGGER.error("\u67e5\u8be2\u62a5\u9519\uff01", e);
                }
            }
        }
        return result;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Unable to fully structure code
     */
    @Override
    public int update(String sql, Map<String, Object> values, InSqlModel inModel) {
        block30: {
            if (null == values) {
                values = new HashMap<String, Object>();
            }
            stringBuilder = new StringBuilder();
            stringBuilder.append(sql);
            if (inModel.isAddWhere()) {
                stringBuilder.append(" where ");
            }
            inValues = inModel.getValues();
            tempTableName = null;
            connection = null;
            result = 0;
            if (inValues.size() > 1000) {
                valueSet = new HashSet<String>();
                valueSet.addAll(inValues);
                inValues = valueSet.stream().collect(Collectors.toList());
                if (inValues.size() > 1000) {
                    if (inModel.isAddAnd()) {
                        stringBuilder.append(" and ");
                    }
                    tempTableName = this.getTempTableName(inModel.getPrefix());
                    connection = DataSourceUtils.getConnection((DataSource)this.jdbcTemplate.getJdbcTemplate().getDataSource());
                    this.createTempTable(connection, tempTableName);
                    this.insertIntoTempTable(connection, tempTableName, inValues);
                    stringBuilder.append(" EXISTS(select TEM_CODE from ").append(tempTableName);
                    stringBuilder.append(" TempTable where ").append(inModel.getAlias()).append(".").append(inModel.getColumn());
                    stringBuilder.append("= TempTable.TEM_CODE) ");
                } else {
                    if (inModel.isAddAnd()) {
                        stringBuilder.append(" and ");
                    }
                    stringBuilder.append(String.format(" %s.%s in (:%s)", new Object[]{inModel.getAlias(), inModel.getColumn(), inModel.getArg()}));
                    values.put(inModel.getArg(), inValues);
                }
            } else {
                if (inModel.isAddAnd()) {
                    stringBuilder.append(" and ");
                }
                stringBuilder.append(String.format(" %s.%s in (:%s)", new Object[]{inModel.getAlias(), inModel.getColumn(), inModel.getArg()}));
                values.put(inModel.getArg(), inValues);
            }
            parameterSource = new MapSqlParameterSource();
            parameterSource.addValues(values);
            result = this.jdbcTemplate.update(stringBuilder.toString(), (SqlParameterSource)parameterSource);
            ** try [egrp 1[TRYBLOCK] [0 : 397->416)] { 
lbl-1000:
            // 1 sources

            {
                if (StringUtils.hasLength(tempTableName)) {
                    this.dropTempTable(connection, tempTableName);
                }
            }
lbl58:
            // 1 sources

            catch (Exception e) {
                NpApplicationSqlHelpImpl.LOGGER.error("\u67e5\u8be2\u62a5\u9519\uff01", e);
            }
            try {
                if (connection != null) {
                    DataSourceUtils.releaseConnection((Connection)connection, (DataSource)this.jdbcTemplate.getJdbcTemplate().getDataSource());
                }
                break block30;
            }
            catch (Exception e) {
                NpApplicationSqlHelpImpl.LOGGER.error("\u67e5\u8be2\u62a5\u9519\uff01", e);
            }
            break block30;
            catch (Exception e) {
                NpApplicationSqlHelpImpl.LOGGER.error("\u66f4\u65b0\u62a5\u9519\uff01", e);
                break block30;
            }
            finally {
                try {
                    if (StringUtils.hasLength(tempTableName)) {
                        this.dropTempTable(connection, tempTableName);
                    }
                }
                catch (Exception e) {
                    NpApplicationSqlHelpImpl.LOGGER.error("\u67e5\u8be2\u62a5\u9519\uff01", e);
                }
                try {
                    if (connection != null) {
                        DataSourceUtils.releaseConnection(connection, (DataSource)this.jdbcTemplate.getJdbcTemplate().getDataSource());
                    }
                }
                catch (Exception e) {
                    NpApplicationSqlHelpImpl.LOGGER.error("\u67e5\u8be2\u62a5\u9519\uff01", e);
                }
            }
        }
        return result;
    }

    private String getTempTableName(String prefix) {
        SecureRandom random = new SecureRandom();
        int tableIndex = random.nextInt(10000);
        String tableName = NpApplicationSqlHelpImpl.getRandomString(5);
        String gatherTable = String.format("%s_%s_%s", prefix, tableName, tableIndex);
        return gatherTable.toUpperCase();
    }

    public static String getRandomString(int length) {
        SecureRandom random = new SecureRandom();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; ++i) {
            int number = random.nextInt(62);
            sb.append(chars.charAt(number));
        }
        return sb.toString();
    }

    private boolean createTempTable(Connection connection, String tableName) {
        try {
            StringBuilder sqlBuilder = new StringBuilder();
            sqlBuilder.append("CREATE TABLE ").append(tableName).append(" (");
            sqlBuilder.append("TEM_CODE").append(" ");
            sqlBuilder.append("VARCHAR(256)");
            sqlBuilder.append(" NOT NULL,");
            sqlBuilder.append("CONSTRAINT PK_").append(tableName).append(" PRIMARY KEY (TEM_CODE)");
            sqlBuilder.append(");");
            IDatabase database = DatabaseManager.getInstance().findDatabaseByConnection(connection);
            SQLParser sqlParser = new SQLParser();
            List statements = sqlParser.parse(sqlBuilder.toString());
            for (SqlStatement statement : statements) {
                List sqls = statement.interpret(database, connection);
                for (String sql : sqls) {
                    PreparedStatement prep = connection.prepareStatement(sql);
                    Throwable throwable = null;
                    try {
                        prep.execute();
                    }
                    catch (Throwable throwable2) {
                        throwable = throwable2;
                        throw throwable2;
                    }
                    finally {
                        if (prep == null) continue;
                        if (throwable != null) {
                            try {
                                prep.close();
                            }
                            catch (Throwable throwable3) {
                                throwable.addSuppressed(throwable3);
                            }
                            continue;
                        }
                        prep.close();
                    }
                }
            }
            return true;
        }
        catch (SQLInterpretException | SQLParserException | SQLException e) {
            LOGGER.error("\u521b\u5efa\u4e34\u65f6\u8868\u51fa\u9519\uff01", e);
            return false;
        }
    }

    private void insertIntoTempTable(Connection connection, String tableName, List<String> filterValues) throws SQLException {
        StringBuilder insertSql = new StringBuilder();
        insertSql.append(" Insert Into ").append(tableName).append("(");
        insertSql.append("TEM_CODE");
        insertSql.append(")").append(" values (?)");
        ArrayList<Object[]> batchValues = new ArrayList<Object[]>();
        for (String filterValue : filterValues) {
            if (null != filterValue && !"".equals(filterValue)) {
                Object[] batchArray = new Object[]{filterValue};
                batchValues.add(batchArray);
                continue;
            }
            LOGGER.error(" null \u65e0\u6cd5\u4f7f\u7528 \u4e34\u65f6\u8868\u5173\u8054\uff01");
        }
        this.batchUpdate(connection, insertSql.toString(), batchValues);
    }

    private void batchUpdate(Connection conn, String sql, List<Object[]> batchValues) throws SQLException {
        PreparedStatement prep = null;
        try {
            prep = conn.prepareStatement(sql);
            int batchSize = 1000;
            int count = 0;
            for (Object[] batchValue : batchValues) {
                for (int i = 0; i < batchValue.length; ++i) {
                    prep.setObject(i + 1, batchValue[i]);
                }
                prep.addBatch();
                if (++count % 1000 != 0) continue;
                prep.executeBatch();
            }
            prep.executeBatch();
        }
        catch (Exception e) {
            StringBuilder msg = new StringBuilder();
            msg.append("sql: ").append(sql).append("\n");
            msg.append("argsCount: ").append(batchValues.size()).append("\n");
            msg.append(e.getMessage());
            LOGGER.error(msg.toString(), e);
            throw new SQLException(msg.toString(), e);
        }
        finally {
            try {
                if (prep != null) {
                    prep.close();
                }
            }
            catch (SQLException e) {
                LOGGER.error("\u4e34\u65f6\u8868\u63d2\u5165\u6570\u636e\u5931\u8d25", e);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void dropTempTable(Connection connection, String tableName) throws SQLException {
        Statement prep = null;
        try {
            StringBuilder deleteSql = new StringBuilder();
            deleteSql.append("DROP TABLE ").append(tableName);
            prep = connection.prepareStatement(deleteSql.toString());
            prep.execute();
        }
        catch (SQLException e) {
            LOGGER.error("\u5220\u9664\u4e34\u65f6\u8868\u5931\u8d25", e);
        }
        finally {
            try {
                if (prep != null) {
                    prep.close();
                }
            }
            catch (SQLException e) {
                LOGGER.error("\u5220\u9664\u4e34\u65f6\u8868\u5931\u8d25", e);
            }
        }
    }
}

