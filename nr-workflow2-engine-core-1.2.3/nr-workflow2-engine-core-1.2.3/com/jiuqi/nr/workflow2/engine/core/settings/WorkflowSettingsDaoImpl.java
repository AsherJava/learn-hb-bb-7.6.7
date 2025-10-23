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
package com.jiuqi.nr.workflow2.engine.core.settings;

import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsDO;
import com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsDOImpl;
import com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsDTO;
import com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsDao;
import com.jiuqi.nr.workflow2.engine.core.settings.enumeration.WorkflowObjectType;
import com.jiuqi.nr.workflow2.engine.core.settings.utils.NamedParameterSqlBuilder;
import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.transaction.annotation.Transactional;

public class WorkflowSettingsDaoImpl
implements WorkflowSettingsDao {
    private static final String TABLE_NAME = "NR_WORKFLOW_SETTING";
    private static final String COLUMN_ID = "ID";
    private static final String COLUMN_TASK_ID = "TASK_ID";
    private static final String COLUMN_WORKFLOW_ENGINE = "WORKFLOW_ENGINE";
    private static final String COLUMN_WORKFLOW_DEFINE = "WORKFLOW_DEFINE";
    private static final String COLUMN_WORKFLOW_ENABLE = "WORKFLOW_ENABLE";
    private static final String COLUMN_TODO_ENABLE = "TODO_ENABLE";
    private static final String COLUMN_WORKFLOW_OBJECT = "WORKFLOW_OBJECT";
    private static final String COLUMN_OTHER_CONFIG = "OTHER_CONFIG";
    private static final String COLUMN_CREATE_TIME = "CREATE_TIME";
    private static final String COLUMN_UPDATE_TIME = "UPDATE_TIME";
    private static final String COLUMN_OPERATOR = "OPERATOR";
    private final JdbcTemplate jdbcTemplate;

    public WorkflowSettingsDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Transactional
    public boolean addWorkflowSettings(WorkflowSettingsDTO settingsDTO) {
        NamedParameterSqlBuilder sqlBuilder = new NamedParameterSqlBuilder(TABLE_NAME);
        sqlBuilder.insertSQL(COLUMN_ID, COLUMN_TASK_ID, COLUMN_WORKFLOW_DEFINE, COLUMN_WORKFLOW_ENGINE, COLUMN_WORKFLOW_ENABLE, COLUMN_TODO_ENABLE, COLUMN_WORKFLOW_OBJECT, COLUMN_OTHER_CONFIG, COLUMN_CREATE_TIME, COLUMN_UPDATE_TIME, COLUMN_OPERATOR);
        MapSqlParameterSource source = this.buildInsertMapSource(settingsDTO);
        return this.executeUpdate(sqlBuilder.toString(), source) > 0;
    }

    @Override
    @Transactional
    public boolean deleteWorkflowSettings(String taskId) {
        NamedParameterSqlBuilder sqlBuilder = new NamedParameterSqlBuilder(TABLE_NAME);
        sqlBuilder.deleteSQL().andWhere(COLUMN_TASK_ID);
        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue(COLUMN_TASK_ID, (Object)taskId);
        return this.executeUpdate(sqlBuilder.toString(), source) > 0;
    }

    @Override
    @Transactional
    public boolean updateWorkflowSettings(WorkflowSettingsDTO settingsDTO) {
        NamedParameterSqlBuilder sqlBuilder = new NamedParameterSqlBuilder(TABLE_NAME);
        sqlBuilder.updateSQL(COLUMN_WORKFLOW_DEFINE, COLUMN_WORKFLOW_ENGINE, COLUMN_WORKFLOW_ENABLE, COLUMN_TODO_ENABLE, COLUMN_WORKFLOW_OBJECT, COLUMN_OTHER_CONFIG, COLUMN_UPDATE_TIME, COLUMN_OPERATOR).andWhere(COLUMN_TASK_ID);
        MapSqlParameterSource source = this.buildUpdateMapSource(settingsDTO);
        return this.executeUpdate(sqlBuilder.toString(), source) > 0;
    }

    @Override
    public WorkflowSettingsDO queryWorkflowSettings(String taskId) {
        NamedParameterSqlBuilder sqlBuilder = new NamedParameterSqlBuilder(TABLE_NAME);
        sqlBuilder.selectSQL(COLUMN_ID, COLUMN_TASK_ID, COLUMN_WORKFLOW_DEFINE, COLUMN_WORKFLOW_ENGINE, COLUMN_WORKFLOW_ENABLE, COLUMN_TODO_ENABLE, COLUMN_WORKFLOW_OBJECT, COLUMN_OTHER_CONFIG, COLUMN_CREATE_TIME, COLUMN_UPDATE_TIME, COLUMN_OPERATOR).andWhere(COLUMN_TASK_ID);
        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue(COLUMN_TASK_ID, (Object)taskId);
        return this.executeQuery(sqlBuilder.toString(), source);
    }

    private MapSqlParameterSource buildInsertMapSource(WorkflowSettingsDTO settingsDTO) {
        Timestamp timestamp = new Timestamp(new Date().getTime());
        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue(COLUMN_ID, (Object)UUID.randomUUID().toString());
        source.addValue(COLUMN_TASK_ID, (Object)settingsDTO.getTaskId());
        source.addValue(COLUMN_WORKFLOW_DEFINE, (Object)settingsDTO.getWorkflowDefine());
        source.addValue(COLUMN_WORKFLOW_ENGINE, (Object)settingsDTO.getWorkflowEngine());
        source.addValue(COLUMN_WORKFLOW_ENABLE, (Object)(settingsDTO.isWorkflowEnable() ? 1 : 0));
        source.addValue(COLUMN_TODO_ENABLE, (Object)(settingsDTO.isTodoEnable() ? 1 : 0));
        source.addValue(COLUMN_WORKFLOW_OBJECT, (Object)settingsDTO.getWorkflowObjectType().name());
        source.addValue(COLUMN_OTHER_CONFIG, (Object)settingsDTO.getOtherConfig());
        source.addValue(COLUMN_CREATE_TIME, (Object)timestamp);
        source.addValue(COLUMN_UPDATE_TIME, (Object)timestamp);
        String userId = NpContextHolder.getContext().getUserId();
        source.addValue(COLUMN_OPERATOR, (Object)(userId == null || userId.isEmpty() ? "UPGRADE_USER" : userId));
        return source;
    }

    private MapSqlParameterSource buildUpdateMapSource(WorkflowSettingsDTO settingsDTO) {
        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue(COLUMN_TASK_ID, (Object)settingsDTO.getTaskId());
        if (settingsDTO.getWorkflowDefine() != null) {
            source.addValue(COLUMN_WORKFLOW_DEFINE, (Object)settingsDTO.getWorkflowDefine());
        }
        if (settingsDTO.getWorkflowEngine() != null) {
            source.addValue(COLUMN_WORKFLOW_ENGINE, (Object)settingsDTO.getWorkflowEngine());
        }
        source.addValue(COLUMN_WORKFLOW_ENABLE, (Object)(settingsDTO.isWorkflowEnable() ? 1 : 0));
        source.addValue(COLUMN_TODO_ENABLE, (Object)(settingsDTO.isTodoEnable() ? 1 : 0));
        if (settingsDTO.getWorkflowObjectType() != null) {
            source.addValue(COLUMN_WORKFLOW_OBJECT, (Object)settingsDTO.getWorkflowObjectType().name());
        }
        if (settingsDTO.getOtherConfig() != null) {
            source.addValue(COLUMN_OTHER_CONFIG, (Object)settingsDTO.getOtherConfig());
        }
        source.addValue(COLUMN_UPDATE_TIME, (Object)new Timestamp(new Date().getTime()));
        String userId = NpContextHolder.getContext().getUserId();
        source.addValue(COLUMN_OPERATOR, (Object)(userId == null || userId.isEmpty() ? "UPGRADE_USER" : userId));
        return source;
    }

    private int executeUpdate(String sql, MapSqlParameterSource source) {
        NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate((JdbcOperations)this.jdbcTemplate);
        return template.update(sql, (SqlParameterSource)source);
    }

    private WorkflowSettingsDO executeQuery(String sql, MapSqlParameterSource source) {
        NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate((JdbcOperations)this.jdbcTemplate);
        return (WorkflowSettingsDO)template.query(sql, (SqlParameterSource)source, rs -> {
            if (!rs.next()) {
                return null;
            }
            WorkflowSettingsDOImpl workflowSetting = new WorkflowSettingsDOImpl();
            workflowSetting.setId(rs.getString(COLUMN_ID));
            workflowSetting.setTaskId(rs.getString(COLUMN_TASK_ID));
            workflowSetting.setWorkflowDefine(rs.getString(COLUMN_WORKFLOW_DEFINE));
            workflowSetting.setWorkflowEngine(rs.getString(COLUMN_WORKFLOW_ENGINE));
            workflowSetting.setWorkflowEnable(rs.getInt(COLUMN_WORKFLOW_ENABLE) == 1);
            workflowSetting.setTodoEnable(rs.getInt(COLUMN_TODO_ENABLE) == 1);
            workflowSetting.setWorkflowObjectType(WorkflowObjectType.valueOf(rs.getString(COLUMN_WORKFLOW_OBJECT)));
            workflowSetting.setOtherConfig(rs.getString(COLUMN_OTHER_CONFIG));
            workflowSetting.setCreateTime(rs.getString(COLUMN_CREATE_TIME));
            workflowSetting.setUpdateTime(rs.getString(COLUMN_UPDATE_TIME));
            workflowSetting.setOperator(rs.getString(COLUMN_OPERATOR));
            return workflowSetting;
        });
    }
}

