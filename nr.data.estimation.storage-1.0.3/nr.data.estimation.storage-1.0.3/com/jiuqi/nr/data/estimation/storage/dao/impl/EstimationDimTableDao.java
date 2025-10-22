/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.util.NpRollbackException
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  javax.annotation.Resource
 *  org.springframework.jdbc.core.JdbcOperations
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.jdbc.core.namedparam.MapSqlParameterSource
 *  org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
 *  org.springframework.jdbc.core.namedparam.SqlParameterSource
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.nr.data.estimation.storage.dao.impl;

import com.jiuqi.np.util.NpRollbackException;
import com.jiuqi.nr.data.estimation.storage.dao.IEstimationDimTableDao;
import com.jiuqi.nr.data.estimation.storage.dao.NamedParameterSqlBuilder;
import com.jiuqi.nr.data.estimation.storage.entity.impl.DimTableRecord;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
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
public class EstimationDimTableDao
implements IEstimationDimTableDao {
    public static final String tableName = "nr_data_estimation_dim_tables";
    public static final String[] columns = new String[]{"EDT_DATA_SCHEME", "EDT_DIM_TABLE_NAME", "EDT_DIM_TABLE_FIELDS"};
    @Resource
    private JdbcTemplate jdbcTemplate;
    @Resource
    private IRunTimeViewController runTimeViewController;

    @Override
    @Transactional(rollbackFor={NpRollbackException.class})
    public int insertDimTableRecord(DimTableRecord record) {
        NamedParameterSqlBuilder sqlBuilder = new NamedParameterSqlBuilder(tableName);
        sqlBuilder.insertSQL(columns);
        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue(columns[0], (Object)record.getDataScheme());
        source.addValue(columns[1], (Object)record.getTableName());
        source.addValue(columns[2], (Object)String.join((CharSequence)";", record.getFiledCodes()));
        NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate((JdbcOperations)this.jdbcTemplate);
        return template.update(sqlBuilder.toString(), (SqlParameterSource)source);
    }

    @Override
    @Transactional(rollbackFor={NpRollbackException.class})
    public int deleteDimTableRecord(String dataScheme) {
        NamedParameterSqlBuilder sqlBuilder = new NamedParameterSqlBuilder(tableName);
        sqlBuilder.deleteSQL().inWhere(columns[0]);
        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue(columns[0], (Object)dataScheme);
        NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate((JdbcOperations)this.jdbcTemplate);
        return template.update(sqlBuilder.toString(), (SqlParameterSource)source);
    }

    @Override
    public DimTableRecord findDimTableRecord(String dataScheme) {
        NamedParameterSqlBuilder sqlBuilder = new NamedParameterSqlBuilder(tableName);
        sqlBuilder.selectSQL(columns).andWhere(columns[0]);
        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue(columns[0], (Object)dataScheme);
        NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate((JdbcOperations)this.jdbcTemplate);
        List query = template.query(sqlBuilder.toString(), (SqlParameterSource)source, this::readDimTableRecord);
        return query.size() == 1 ? (DimTableRecord)query.get(0) : null;
    }

    @Override
    public DimTableRecord findDimTableRecordByFormScheme(String formSchemeKey) {
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
        if (formScheme == null) {
            return null;
        }
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(formScheme.getTaskKey());
        return this.findDimTableRecord(taskDefine.getDataScheme());
    }

    private DimTableRecord readDimTableRecord(ResultSet rs, int rowIdx) throws SQLException {
        DimTableRecord impl = new DimTableRecord();
        impl.setDataScheme(rs.getString(columns[0]));
        impl.setTableName(rs.getString(columns[1]));
        impl.setFiledCodes(Arrays.asList(rs.getString(columns[2]).split(";")));
        return impl;
    }
}

