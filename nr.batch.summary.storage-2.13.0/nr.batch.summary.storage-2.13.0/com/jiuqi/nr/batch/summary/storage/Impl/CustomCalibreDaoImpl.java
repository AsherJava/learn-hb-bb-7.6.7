/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.common.GetClobDataUtil
 *  com.jiuqi.np.util.NpRollbackException
 *  javax.annotation.Resource
 *  org.springframework.jdbc.core.JdbcOperations
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.jdbc.core.namedparam.MapSqlParameterSource
 *  org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
 *  org.springframework.jdbc.core.namedparam.SqlParameterSource
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.nr.batch.summary.storage.Impl;

import com.jiuqi.np.definition.common.GetClobDataUtil;
import com.jiuqi.np.util.NpRollbackException;
import com.jiuqi.nr.batch.summary.storage.CustomCalibreDao;
import com.jiuqi.nr.batch.summary.storage.NamedParameterSqlBuilder;
import com.jiuqi.nr.batch.summary.storage.entity.CustomCalibreRow;
import com.jiuqi.nr.batch.summary.storage.entity.SummaryScheme;
import com.jiuqi.nr.batch.summary.storage.entity.impl.CustomCalibreRowDefine;
import com.jiuqi.nr.batch.summary.storage.entity.impl.CustomCalibreValue;
import com.jiuqi.nr.batch.summary.storage.enumeration.ConditionValueType;
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
public class CustomCalibreDaoImpl
implements CustomCalibreDao {
    private static final String BSC_KEY = "BSC_KEY";
    private static final String BSC_CODE = "BSC_CODE";
    private static final String BSC_SCHEME = "BSC_SCHEME";
    private static final String BSC_PARENT_CODE = "BSC_PARENT_CODE";
    private static final String TABLE_NAME = "NR_BS_CALIBRE_DATA";
    private static final String[] columns = new String[]{"BSC_KEY", "BSC_CODE", "BSC_NAME", "BSC_SCHEME", "BSC_VALUE", "BSC_VALUE_TYPE", "BSC_PARENT_CODE", "BSC_ORDINAL"};
    @Resource
    private JdbcTemplate jdbcTemplate;

    @Override
    @Transactional(rollbackFor={NpRollbackException.class})
    public int[] insertRows(SummaryScheme scheme, List<CustomCalibreRow> rows) {
        NamedParameterSqlBuilder sqlBuilder = new NamedParameterSqlBuilder(TABLE_NAME);
        sqlBuilder.insertSQL(columns);
        MapSqlParameterSource[] batchMapSource = this.buildBatchMapSource(rows);
        NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate((JdbcOperations)this.jdbcTemplate);
        return template.batchUpdate(sqlBuilder.toString(), (SqlParameterSource[])batchMapSource);
    }

    @Override
    @Transactional(rollbackFor={NpRollbackException.class})
    public int[] updateRows(SummaryScheme scheme, List<CustomCalibreRow> rows) {
        NamedParameterSqlBuilder sqlBuilder = new NamedParameterSqlBuilder(TABLE_NAME);
        sqlBuilder.updateSQL((String[])Arrays.stream(columns).filter(e -> !e.equals(BSC_KEY)).toArray(String[]::new)).andWhere(BSC_KEY);
        MapSqlParameterSource[] batchMapSource = this.buildBatchMapSource(rows);
        NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate((JdbcOperations)this.jdbcTemplate);
        return template.batchUpdate(sqlBuilder.toString(), (SqlParameterSource[])batchMapSource);
    }

    @Override
    @Transactional(rollbackFor={NpRollbackException.class})
    public int deleteRows(List<String> rowKeys) {
        NamedParameterSqlBuilder sqlBuilder = new NamedParameterSqlBuilder(TABLE_NAME);
        sqlBuilder.deleteSQL().inWhere(BSC_KEY);
        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue(BSC_KEY, rowKeys);
        NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate((JdbcOperations)this.jdbcTemplate);
        return template.update(sqlBuilder.toString(), (SqlParameterSource)source);
    }

    @Override
    public int deleteRows(String schemeKey) {
        NamedParameterSqlBuilder sqlBuilder = new NamedParameterSqlBuilder(TABLE_NAME);
        sqlBuilder.deleteSQL().andWhere(BSC_SCHEME);
        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue(BSC_SCHEME, (Object)schemeKey);
        NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate((JdbcOperations)this.jdbcTemplate);
        return template.update(sqlBuilder.toString(), (SqlParameterSource)source);
    }

    @Override
    public List<CustomCalibreRow> findConditionRow(String schemeKey) {
        NamedParameterSqlBuilder sqlBuilder = new NamedParameterSqlBuilder(TABLE_NAME);
        sqlBuilder.selectSQL(columns).andWhere(BSC_SCHEME).orderBy(columns[7], BSC_CODE);
        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue(BSC_SCHEME, (Object)schemeKey);
        NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate((JdbcOperations)this.jdbcTemplate);
        return template.query(sqlBuilder.toString(), (SqlParameterSource)source, this::readCustomCalibreData);
    }

    private MapSqlParameterSource[] buildBatchMapSource(List<CustomCalibreRow> rows) {
        MapSqlParameterSource[] sources = new MapSqlParameterSource[rows.size()];
        for (int i = 0; i < rows.size(); ++i) {
            CustomCalibreRow row = rows.get(i);
            sources[i] = this.buildSqlParameterSource(row);
        }
        return sources;
    }

    private MapSqlParameterSource buildSqlParameterSource(CustomCalibreRow row) {
        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue(columns[0], (Object)row.getKey());
        source.addValue(columns[1], (Object)row.getCode());
        source.addValue(columns[2], (Object)row.getTitle());
        source.addValue(columns[3], (Object)row.getScheme());
        source.addValue(columns[4], (Object)row.getValue().valueToClob());
        source.addValue(columns[5], (Object)row.getValue().getValueType().value);
        source.addValue(columns[6], (Object)row.getParentCode());
        source.addValue(columns[7], (Object)row.getOrdinal());
        return source;
    }

    private CustomCalibreRow readCustomCalibreData(ResultSet rs, int rowIdx) throws SQLException {
        CustomCalibreRowDefine impl = new CustomCalibreRowDefine();
        impl.setKey(rs.getString(columns[0]));
        impl.setCode(rs.getString(columns[1]));
        impl.setTitle(rs.getString(columns[2]));
        impl.setScheme(rs.getString(columns[3]));
        impl.setValue(this.readCustomCalibreValue(GetClobDataUtil.getClobFieldData((ResultSet)rs, (String)columns[4]), rs.getInt(columns[5])));
        impl.setParentCode(rs.getString(columns[6]));
        impl.setOrdinal(rs.getString(columns[7]));
        return impl;
    }

    private CustomCalibreValue readCustomCalibreValue(String jsonStr, int type) {
        CustomCalibreValue impl = new CustomCalibreValue();
        impl.setValueType(ConditionValueType.valueOf(type));
        impl.transformAndSetCheckList(jsonStr);
        return impl;
    }
}

