/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.util.NpRollbackException
 *  com.jiuqi.nr.tag.management.util.NamedParameterSqlBuilder
 *  com.jiuqi.util.StringUtils
 *  javax.annotation.Resource
 *  org.springframework.jdbc.core.JdbcOperations
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.jdbc.core.namedparam.MapSqlParameterSource
 *  org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
 *  org.springframework.jdbc.core.namedparam.SqlParameterSource
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.nr.batch.gather.gzw.service.daoImpl;

import com.jiuqi.np.util.NpRollbackException;
import com.jiuqi.nr.batch.gather.gzw.service.dao.IGatherEntityCodeMappingDao;
import com.jiuqi.nr.batch.gather.gzw.service.entity.GatherEntityCodeMapping;
import com.jiuqi.nr.batch.gather.gzw.service.enumeration.GatherEntityCodeMappingTableEnum;
import com.jiuqi.nr.tag.management.util.NamedParameterSqlBuilder;
import com.jiuqi.util.StringUtils;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
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
public class GatherEntityCodeMappingDaoImpl
implements IGatherEntityCodeMappingDao {
    private static final String TABLE_NAME = "NR_GATHER_ENTITY_CODE_MAPPING";
    private static final String[] columns = new String[]{"ECM_ENTITY_CODE", "ECM_TASK", "ECM_PERIOD", "ECM_GATHER_SCHEME_KEY", "ECM_CUSTOMIZED_CONDITION_CODE", "ECM_EXECUTE_DATETIME", "ECM_ENTITY_ID"};
    @Resource
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<GatherEntityCodeMapping> queryCodeMapping(String gatherSchemeKey, String entityCode, String task, String period) {
        NamedParameterSqlBuilder sqlBuilder = new NamedParameterSqlBuilder(TABLE_NAME);
        sqlBuilder.selectSQL(columns).andWhere(new String[]{GatherEntityCodeMappingTableEnum.ECM_ENTITY_CODE.column, GatherEntityCodeMappingTableEnum.ECM_TASK.column, GatherEntityCodeMappingTableEnum.ECM_PERIOD.column, GatherEntityCodeMappingTableEnum.ECM_GATHER_SCHEME_KEY.column});
        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue(GatherEntityCodeMappingTableEnum.ECM_GATHER_SCHEME_KEY.column, (Object)gatherSchemeKey);
        source.addValue(GatherEntityCodeMappingTableEnum.ECM_ENTITY_CODE.column, (Object)entityCode);
        source.addValue(GatherEntityCodeMappingTableEnum.ECM_TASK.column, (Object)task);
        source.addValue(GatherEntityCodeMappingTableEnum.ECM_PERIOD.column, (Object)period);
        return this.executeQuery(sqlBuilder.toString(), source);
    }

    @Override
    public List<GatherEntityCodeMapping> queryCodeMappingByDw(String contextEntityId, String entityCode, String task, String period) {
        NamedParameterSqlBuilder sqlBuilder = new NamedParameterSqlBuilder(TABLE_NAME);
        sqlBuilder.selectSQL(columns);
        MapSqlParameterSource source = new MapSqlParameterSource();
        if (StringUtils.isEmpty((String)contextEntityId)) {
            sqlBuilder.andWhere(new String[]{GatherEntityCodeMappingTableEnum.ECM_TASK.column, GatherEntityCodeMappingTableEnum.ECM_PERIOD.column, GatherEntityCodeMappingTableEnum.ECM_ENTITY_CODE.column});
        } else {
            source.addValue(GatherEntityCodeMappingTableEnum.ECM_ENTITY_ID.column, (Object)contextEntityId);
            sqlBuilder.andWhere(new String[]{GatherEntityCodeMappingTableEnum.ECM_ENTITY_ID.column, GatherEntityCodeMappingTableEnum.ECM_TASK.column, GatherEntityCodeMappingTableEnum.ECM_PERIOD.column, GatherEntityCodeMappingTableEnum.ECM_ENTITY_CODE.column});
        }
        source.addValue(GatherEntityCodeMappingTableEnum.ECM_ENTITY_CODE.column, (Object)entityCode);
        source.addValue(GatherEntityCodeMappingTableEnum.ECM_TASK.column, (Object)task);
        source.addValue(GatherEntityCodeMappingTableEnum.ECM_PERIOD.column, (Object)period);
        return this.executeQuery(sqlBuilder.toString(), source);
    }

    @Override
    public List<GatherEntityCodeMapping> queryCodeMapping(String gatherSchemeKey) {
        NamedParameterSqlBuilder sqlBuilder = new NamedParameterSqlBuilder(TABLE_NAME);
        sqlBuilder.selectSQL(columns).andWhere(new String[]{GatherEntityCodeMappingTableEnum.ECM_GATHER_SCHEME_KEY.column});
        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue(GatherEntityCodeMappingTableEnum.ECM_GATHER_SCHEME_KEY.column, (Object)gatherSchemeKey);
        List<GatherEntityCodeMapping> mappings = this.executeQuery(sqlBuilder.toString(), source);
        return mappings.size() > 0 ? mappings : null;
    }

    @Override
    @Transactional(rollbackFor={NpRollbackException.class})
    public int[] insertCodeMappings(List<GatherEntityCodeMapping> gatherEntityCodeMappings) {
        NamedParameterSqlBuilder sqlBuilder = new NamedParameterSqlBuilder(TABLE_NAME);
        sqlBuilder.insertSQL(columns);
        MapSqlParameterSource[] batchMapSource = this.buildBatchMapSource(gatherEntityCodeMappings);
        return this.executeBatchUpdate(sqlBuilder.toString(), batchMapSource);
    }

    @Override
    public int deleteCodeMappings(String gatherSchemeKey, String period) {
        NamedParameterSqlBuilder sqlBuilder = new NamedParameterSqlBuilder(TABLE_NAME);
        sqlBuilder.deleteSQL().andWhere(new String[]{GatherEntityCodeMappingTableEnum.ECM_GATHER_SCHEME_KEY.column, GatherEntityCodeMappingTableEnum.ECM_PERIOD.column});
        MapSqlParameterSource deleteMapSource = new MapSqlParameterSource();
        deleteMapSource.addValue(GatherEntityCodeMappingTableEnum.ECM_GATHER_SCHEME_KEY.column, (Object)gatherSchemeKey);
        deleteMapSource.addValue(GatherEntityCodeMappingTableEnum.ECM_PERIOD.column, (Object)period);
        return this.executeUpdate(sqlBuilder.toString(), deleteMapSource);
    }

    private List<GatherEntityCodeMapping> executeQuery(String sql, MapSqlParameterSource source) {
        NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate((JdbcOperations)this.jdbcTemplate);
        return template.query(sql, (SqlParameterSource)source, this::CodeMappingMapper);
    }

    private int executeUpdate(String sql, MapSqlParameterSource source) {
        NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate((JdbcOperations)this.jdbcTemplate);
        return template.update(sql, (SqlParameterSource)source);
    }

    private int[] executeBatchUpdate(String sql, MapSqlParameterSource[] sources) {
        NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate((JdbcOperations)this.jdbcTemplate);
        return template.batchUpdate(sql, (SqlParameterSource[])sources);
    }

    private MapSqlParameterSource[] buildBatchMapSource(List<GatherEntityCodeMapping> rows) {
        MapSqlParameterSource[] sources = new MapSqlParameterSource[rows.size()];
        for (int i = 0; i < rows.size(); ++i) {
            GatherEntityCodeMapping row = rows.get(i);
            sources[i] = new MapSqlParameterSource();
            sources[i].addValue(GatherEntityCodeMappingTableEnum.ECM_ENTITY_CODE.column, (Object)row.getEntityCode());
            sources[i].addValue(GatherEntityCodeMappingTableEnum.ECM_TASK.column, (Object)row.getTask());
            sources[i].addValue(GatherEntityCodeMappingTableEnum.ECM_PERIOD.column, (Object)row.getPeriod());
            sources[i].addValue(GatherEntityCodeMappingTableEnum.ECM_GATHER_SCHEME_KEY.column, (Object)row.getGatherSchemeKey());
            sources[i].addValue(GatherEntityCodeMappingTableEnum.ECM_CUSTOMIZED_CONDITION_CODE.column, (Object)row.getCustomizedConditionCode());
            Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
            sources[i].addValue(GatherEntityCodeMappingTableEnum.ECM_EXECUTE_DATETIME.column, (Object)currentTimestamp);
            sources[i].addValue(GatherEntityCodeMappingTableEnum.ECM_ENTITY_ID.column, (Object)row.getEntityId());
        }
        return sources;
    }

    private GatherEntityCodeMapping CodeMappingMapper(ResultSet rs, int rowIdx) throws SQLException {
        GatherEntityCodeMapping mapping = new GatherEntityCodeMapping();
        mapping.setEntityCode(rs.getString(GatherEntityCodeMappingTableEnum.ECM_ENTITY_CODE.column));
        mapping.setTask(rs.getString(GatherEntityCodeMappingTableEnum.ECM_TASK.column));
        mapping.setPeriod(rs.getString(GatherEntityCodeMappingTableEnum.ECM_PERIOD.column));
        mapping.setGatherSchemeKey(rs.getString(GatherEntityCodeMappingTableEnum.ECM_GATHER_SCHEME_KEY.column));
        mapping.setCustomizedConditionCode(rs.getString(GatherEntityCodeMappingTableEnum.ECM_CUSTOMIZED_CONDITION_CODE.column));
        mapping.setExecuteDatetime(rs.getString(GatherEntityCodeMappingTableEnum.ECM_EXECUTE_DATETIME.column));
        mapping.setEntityId(rs.getString(GatherEntityCodeMappingTableEnum.ECM_ENTITY_ID.column));
        return mapping;
    }
}

