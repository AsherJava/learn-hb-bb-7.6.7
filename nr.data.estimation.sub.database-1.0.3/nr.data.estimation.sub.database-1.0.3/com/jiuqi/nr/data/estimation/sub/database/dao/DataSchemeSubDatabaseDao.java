/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.util.NpRollbackException
 *  javax.annotation.Resource
 *  org.springframework.jdbc.core.JdbcOperations
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.jdbc.core.namedparam.MapSqlParameterSource
 *  org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
 *  org.springframework.jdbc.core.namedparam.SqlParameterSource
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.nr.data.estimation.sub.database.dao;

import com.jiuqi.np.util.NpRollbackException;
import com.jiuqi.nr.data.estimation.sub.database.dao.IDataSchemeSubDatabaseDao;
import com.jiuqi.nr.data.estimation.sub.database.dao.NamedParameterSqlBuilder;
import com.jiuqi.nr.data.estimation.sub.database.entity.DataSchemeSubDatabaseDefine;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class DataSchemeSubDatabaseDao
implements IDataSchemeSubDatabaseDao {
    public static final String tableName = "nr_data_estimation_sub_db_rcd";
    public static final String[] columns = new String[]{"DSR_DATA_SCHEME", "DSR_DATABASE_CODE", "DSR_CREATE_TIME", "DSR_DATABASE_TITLE"};
    @Resource
    private JdbcTemplate jdbcTemplate;

    @Override
    public DataSchemeSubDatabaseDefine findRecord(String dataSchemeKey, String databaseCode) {
        NamedParameterSqlBuilder sqlBuilder = new NamedParameterSqlBuilder(tableName);
        sqlBuilder.selectSQL(columns).andWhere(columns[0], columns[1]);
        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue(columns[0], (Object)dataSchemeKey);
        source.addValue(columns[1], (Object)databaseCode);
        NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate((JdbcOperations)this.jdbcTemplate);
        List schemes = template.query(sqlBuilder.toString(), (SqlParameterSource)source, this::readRecord);
        return schemes.size() == 1 ? (DataSchemeSubDatabaseDefine)schemes.get(0) : null;
    }

    @Override
    @Transactional(rollbackFor={NpRollbackException.class})
    public int insertRecord(DataSchemeSubDatabaseDefine record) {
        NamedParameterSqlBuilder sqlBuilder = new NamedParameterSqlBuilder(tableName);
        sqlBuilder.insertSQL(columns);
        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue(columns[0], (Object)record.getDataSchemeKey());
        source.addValue(columns[1], (Object)record.getDatabaseCode());
        source.addValue(columns[2], (Object)new Timestamp(record.getCreateTime().getTime()));
        source.addValue(columns[3], (Object)record.getDatabaseTitle());
        NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate((JdbcOperations)this.jdbcTemplate);
        return template.update(sqlBuilder.toString(), (SqlParameterSource)source);
    }

    @Override
    @Transactional(rollbackFor={NpRollbackException.class})
    public int deleteRecord(DataSchemeSubDatabaseDefine record) {
        NamedParameterSqlBuilder sqlBuilder = new NamedParameterSqlBuilder(tableName);
        sqlBuilder.deleteSQL().andWhere(columns[0], columns[1]);
        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue(columns[0], (Object)record.getDataSchemeKey());
        source.addValue(columns[1], (Object)record.getDatabaseCode());
        NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate((JdbcOperations)this.jdbcTemplate);
        return template.update(sqlBuilder.toString(), (SqlParameterSource)source);
    }

    private DataSchemeSubDatabaseDefine readRecord(ResultSet rs, int rowIdx) throws SQLException {
        DataSchemeSubDatabaseDefine impl = new DataSchemeSubDatabaseDefine();
        impl.setDataSchemeKey(rs.getString(columns[0]));
        impl.setDatabaseCode(rs.getString(columns[1]));
        impl.setCreateTime(this.translate2Date(rs.getTimestamp(columns[2])));
        impl.setDatabaseTitle(rs.getString(columns[3]));
        return impl;
    }

    private Date translate2Date(Timestamp timestamp) {
        if (timestamp != null) {
            return new Date(timestamp.getTime());
        }
        return null;
    }
}

