/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.context.NpContextHolder
 *  org.springframework.jdbc.core.JdbcOperations
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.jdbc.core.namedparam.MapSqlParameterSource
 *  org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
 *  org.springframework.jdbc.core.namedparam.SqlParameterSource
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.nr.workflow2.engine.dflt.process.design;

import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.nr.workflow2.engine.dflt.process.design.DefaultProcessDO;
import com.jiuqi.nr.workflow2.engine.dflt.process.design.DefaultProcessDesignDao;
import com.jiuqi.nr.workflow2.engine.dflt.process.design.utils.NamedParameterSqlBuilder;
import java.sql.Timestamp;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class DefaultProcessDesignDaoImpl
implements DefaultProcessDesignDao {
    private static final String TABLE_NAME = "NR_DEFAULT_PROCESS_CONFIG";
    private static final String COLUMN_ID = "ID";
    private static final String COLUMN_CONFIG = "CONFIG";
    private static final String COLUMN_CREATE_TIME = "CREATE_TIME";
    private static final String COLUMN_UPDATE_TIME = "UPDATE_TIME";
    private static final String COLUMN_OPERATOR = "OPERATOR";
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    @Transactional
    public boolean addDefaultProcessConfig(String processId, String config) {
        NamedParameterSqlBuilder sqlBuilder = new NamedParameterSqlBuilder(TABLE_NAME);
        sqlBuilder.insertSQL(COLUMN_ID, COLUMN_CONFIG, COLUMN_CREATE_TIME, COLUMN_UPDATE_TIME, COLUMN_OPERATOR);
        MapSqlParameterSource source = this.buildInsertMapSource(processId, config);
        return this.executeUpdate(sqlBuilder.toString(), source) > 0;
    }

    @Override
    @Transactional
    public boolean deleteDefaultProcessConfig(String processId) {
        NamedParameterSqlBuilder sqlBuilder = new NamedParameterSqlBuilder(TABLE_NAME);
        sqlBuilder.deleteSQL().andWhere(COLUMN_ID);
        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue(COLUMN_ID, (Object)processId);
        return this.executeUpdate(sqlBuilder.toString(), source) > 0;
    }

    @Override
    @Transactional
    public boolean updateDefaultProcessConfig(String processId, String config) {
        NamedParameterSqlBuilder sqlBuilder = new NamedParameterSqlBuilder(TABLE_NAME);
        sqlBuilder.updateSQL(COLUMN_CONFIG, COLUMN_UPDATE_TIME, COLUMN_OPERATOR).andWhere(COLUMN_ID);
        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue(COLUMN_ID, (Object)processId);
        source.addValue(COLUMN_CONFIG, (Object)config);
        source.addValue(COLUMN_UPDATE_TIME, (Object)new Timestamp(new Date().getTime()));
        String userId = NpContextHolder.getContext().getUserId();
        source.addValue(COLUMN_OPERATOR, (Object)(userId == null || userId.isEmpty() ? "UPGRADE_USER" : userId));
        return this.executeUpdate(sqlBuilder.toString(), source) > 0;
    }

    @Override
    public DefaultProcessDO queryDefaultProcessConfig(String processId) {
        NamedParameterSqlBuilder sqlBuilder = new NamedParameterSqlBuilder(TABLE_NAME);
        sqlBuilder.selectSQL(COLUMN_ID, COLUMN_CONFIG, COLUMN_CREATE_TIME, COLUMN_UPDATE_TIME, COLUMN_OPERATOR).andWhere(COLUMN_ID);
        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue(COLUMN_ID, (Object)processId);
        return this.executeQuery(sqlBuilder.toString(), source);
    }

    private MapSqlParameterSource buildInsertMapSource(String processId, String config) {
        Timestamp timestamp = new Timestamp(new Date().getTime());
        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue(COLUMN_ID, (Object)processId);
        source.addValue(COLUMN_CONFIG, (Object)config);
        source.addValue(COLUMN_CREATE_TIME, (Object)timestamp);
        source.addValue(COLUMN_UPDATE_TIME, (Object)timestamp);
        String userId = NpContextHolder.getContext().getUserId();
        source.addValue(COLUMN_OPERATOR, (Object)(userId == null || userId.isEmpty() ? "UPGRADE_USER" : userId));
        return source;
    }

    private int executeUpdate(String sql, MapSqlParameterSource source) {
        NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate((JdbcOperations)this.jdbcTemplate);
        return template.update(sql, (SqlParameterSource)source);
    }

    private DefaultProcessDO executeQuery(String sql, MapSqlParameterSource source) {
        NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate((JdbcOperations)this.jdbcTemplate);
        return (DefaultProcessDO)template.query(sql, (SqlParameterSource)source, rs -> {
            if (!rs.next()) {
                return null;
            }
            DefaultProcessDO defaultProcessDO = new DefaultProcessDO();
            defaultProcessDO.setId(rs.getString(COLUMN_ID));
            defaultProcessDO.setConfig(rs.getString(COLUMN_CONFIG));
            defaultProcessDO.setCreateTime(rs.getString(COLUMN_CREATE_TIME));
            defaultProcessDO.setUpdateTime(rs.getString(COLUMN_UPDATE_TIME));
            defaultProcessDO.setOperator(rs.getString(COLUMN_OPERATOR));
            return defaultProcessDO;
        });
    }
}

