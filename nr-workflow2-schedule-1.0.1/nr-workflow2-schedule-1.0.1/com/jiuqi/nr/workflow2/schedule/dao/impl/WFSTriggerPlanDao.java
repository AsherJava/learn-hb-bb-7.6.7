/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.util.StringUtils
 *  org.springframework.jdbc.core.JdbcOperations
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.jdbc.core.namedparam.MapSqlParameterSource
 *  org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
 *  org.springframework.jdbc.core.namedparam.SqlParameterSource
 */
package com.jiuqi.nr.workflow2.schedule.dao.impl;

import com.jiuqi.nr.workflow2.schedule.dao.IWFSTriggerPlanDao;
import com.jiuqi.nr.workflow2.schedule.dao.WFSTriggerEntity;
import com.jiuqi.nr.workflow2.schedule.dao.impl.WFSTriggerEntityImpl;
import com.jiuqi.nr.workflow2.schedule.enumeration.WFSTriggerStatus;
import com.jiuqi.util.StringUtils;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

@Repository
public class WFSTriggerPlanDao
implements IWFSTriggerPlanDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public int[] insertRows(List<WFSTriggerEntity> entities) {
        if (entities != null && !entities.isEmpty()) {
            List<String> valueColumnNames = this.getAllColumnNames();
            StringBuilder insertedSQL = new StringBuilder();
            insertedSQL.append(" INSERT INTO ");
            insertedSQL.append("NR_WF_STARTUP_TRIGGER").append(" ( ");
            insertedSQL.append(String.join((CharSequence)",", valueColumnNames));
            insertedSQL.append(" ) ");
            insertedSQL.append(" VALUES ");
            insertedSQL.append(" ( ");
            insertedSQL.append(valueColumnNames.stream().map(e -> ":" + e).collect(Collectors.joining(",")));
            insertedSQL.append(" ) ");
            MapSqlParameterSource[] sourceMaps = this.buildParameterSourceMaps(entities);
            NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate((JdbcOperations)this.jdbcTemplate);
            return template.batchUpdate(insertedSQL.toString(), (SqlParameterSource[])sourceMaps);
        }
        return new int[0];
    }

    @Override
    public int updateRow(List<WFSTriggerEntity> entities) {
        int count = 0;
        if (entities != null && !entities.isEmpty()) {
            for (WFSTriggerEntity entity : entities) {
                count += this.updateRow(entity);
            }
        }
        return count;
    }

    @Override
    public int updateRow(WFSTriggerEntity entity) {
        if (entity != null && StringUtils.isNotEmpty((String)entity.getTaskKey()) && StringUtils.isNotEmpty((String)entity.getPeriod())) {
            StringBuilder updateSQL = new StringBuilder();
            updateSQL.append(" UPDATE ").append("NR_WF_STARTUP_TRIGGER");
            updateSQL.append(" SET ");
            updateSQL.append("EXEC_START_TIME").append("=:").append("EXEC_START_TIME").append(", ");
            updateSQL.append("EXEC_END_TIME").append("=:").append("EXEC_END_TIME").append(", ");
            updateSQL.append("EXEC_ACTUAL_TIME").append("=:").append("EXEC_ACTUAL_TIME").append(", ");
            updateSQL.append("EXEC_STATUS").append("=:").append("EXEC_STATUS").append(", ");
            updateSQL.append("EXEC_COUNT").append("=:").append("EXEC_COUNT");
            updateSQL.append(" WHERE ");
            updateSQL.append("EXEC_TASK_KEY").append("=:").append("EXEC_TASK_KEY");
            updateSQL.append(" AND ").append("EXEC_PERIOD").append("=:").append("EXEC_PERIOD");
            MapSqlParameterSource sourceMap = this.buildParameterSourceMap(entity);
            NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate((JdbcOperations)this.jdbcTemplate);
            return template.update(updateSQL.toString(), (SqlParameterSource)sourceMap);
        }
        return 0;
    }

    @Override
    public int deleteRows(String taskKey) {
        if (StringUtils.isNotEmpty((String)taskKey)) {
            StringBuilder deleteSQL = new StringBuilder();
            deleteSQL.append(" DELETE FROM ");
            deleteSQL.append("NR_WF_STARTUP_TRIGGER");
            deleteSQL.append(" WHERE ");
            deleteSQL.append("EXEC_TASK_KEY").append(" =:").append("EXEC_TASK_KEY");
            MapSqlParameterSource sourceMap = new MapSqlParameterSource();
            sourceMap.addValue("EXEC_TASK_KEY", (Object)taskKey);
            NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate((JdbcOperations)this.jdbcTemplate);
            return template.update(deleteSQL.toString(), (SqlParameterSource)sourceMap);
        }
        return 0;
    }

    @Override
    public WFSTriggerEntity queryRowByTaskAndPeriod(String taskKey, String period) {
        if (StringUtils.isNotEmpty((String)taskKey) && StringUtils.isNotEmpty((String)period)) {
            StringBuilder selectSQL = this.getSelectSqlHeaderBuilder();
            selectSQL.append(" WHERE ");
            selectSQL.append("EXEC_TASK_KEY").append("=:").append("EXEC_TASK_KEY");
            selectSQL.append(" AND ").append("EXEC_PERIOD").append("=:").append("EXEC_PERIOD");
            MapSqlParameterSource sourceMap = new MapSqlParameterSource();
            sourceMap.addValue("EXEC_TASK_KEY", (Object)taskKey);
            sourceMap.addValue("EXEC_PERIOD", (Object)period);
            NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate((JdbcOperations)this.jdbcTemplate);
            List query = template.query(selectSQL.toString(), (SqlParameterSource)sourceMap, this::buildRecordData);
            return !query.isEmpty() ? (WFSTriggerEntity)query.get(0) : null;
        }
        return null;
    }

    @Override
    public List<WFSTriggerEntity> queryRowsByTask(String taskKey) {
        if (StringUtils.isNotEmpty((String)taskKey)) {
            StringBuilder selectSQL = this.getSelectSqlHeaderBuilder();
            selectSQL.append(" WHERE ");
            selectSQL.append("EXEC_TASK_KEY").append("=:").append("EXEC_TASK_KEY");
            selectSQL.append(" ORDER BY ").append("EXEC_PERIOD");
            MapSqlParameterSource sourceMap = new MapSqlParameterSource();
            sourceMap.addValue("EXEC_TASK_KEY", (Object)taskKey);
            NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate((JdbcOperations)this.jdbcTemplate);
            return template.query(selectSQL.toString(), (SqlParameterSource)sourceMap, this::buildRecordData);
        }
        return Collections.emptyList();
    }

    protected StringBuilder getSelectSqlHeaderBuilder() {
        List<String> selectColumnNames = this.getAllColumnNames();
        StringBuilder selectSQL = new StringBuilder();
        selectSQL.append(" SELECT ");
        selectSQL.append(String.join((CharSequence)",", selectColumnNames));
        selectSQL.append(" FROM ").append("NR_WF_STARTUP_TRIGGER");
        return selectSQL;
    }

    protected WFSTriggerEntity buildRecordData(ResultSet rs, int rowIdx) throws SQLException {
        WFSTriggerEntityImpl entity = new WFSTriggerEntityImpl();
        entity.setTaskKey(rs.getString("EXEC_TASK_KEY"));
        entity.setPeriod(rs.getString("EXEC_PERIOD"));
        entity.setPlanedStartTime(rs.getTimestamp("EXEC_START_TIME"));
        entity.setPlanedEndTime(rs.getTimestamp("EXEC_END_TIME"));
        entity.setActualTime(rs.getTimestamp("EXEC_ACTUAL_TIME"));
        entity.setStatus(WFSTriggerStatus.valueOf(rs.getString("EXEC_STATUS")));
        entity.setExecCount(rs.getInt("EXEC_COUNT"));
        return entity;
    }

    protected List<String> getAllColumnNames() {
        ArrayList<String> columnNames = new ArrayList<String>();
        columnNames.add("EXEC_TASK_KEY");
        columnNames.add("EXEC_PERIOD");
        columnNames.add("EXEC_START_TIME");
        columnNames.add("EXEC_END_TIME");
        columnNames.add("EXEC_ACTUAL_TIME");
        columnNames.add("EXEC_STATUS");
        columnNames.add("EXEC_COUNT");
        return columnNames;
    }

    protected MapSqlParameterSource[] buildParameterSourceMaps(List<WFSTriggerEntity> entities) {
        MapSqlParameterSource[] sources = new MapSqlParameterSource[entities.size()];
        for (int i = 0; i < entities.size(); ++i) {
            WFSTriggerEntity entity = entities.get(i);
            sources[i] = this.buildParameterSourceMap(entity);
        }
        return sources;
    }

    protected MapSqlParameterSource buildParameterSourceMap(WFSTriggerEntity entity) {
        MapSqlParameterSource sourceMap = new MapSqlParameterSource();
        sourceMap.addValue("EXEC_TASK_KEY", (Object)entity.getTaskKey());
        sourceMap.addValue("EXEC_PERIOD", (Object)entity.getPeriod());
        sourceMap.addValue("EXEC_START_TIME", (Object)entity.getPlanedStartTime());
        sourceMap.addValue("EXEC_END_TIME", (Object)entity.getPlanedEndTime());
        sourceMap.addValue("EXEC_ACTUAL_TIME", (Object)entity.getActualTime());
        sourceMap.addValue("EXEC_STATUS", (Object)entity.getStatus().toString());
        sourceMap.addValue("EXEC_COUNT", (Object)entity.getExecCount());
        return sourceMap;
    }
}

