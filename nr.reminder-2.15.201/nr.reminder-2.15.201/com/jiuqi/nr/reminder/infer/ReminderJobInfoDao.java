/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.nr.reminder.infer;

import com.jiuqi.nr.reminder.bean.ReminderJobEntity;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class ReminderJobInfoDao {
    private static final Logger log = LoggerFactory.getLogger(ReminderJobInfoDao.class);
    private final String SYS_REMINDER_JOB_PARAM = "SYS_REMINDER_JOB_PARAM";
    private final String ID = "ID_";
    private final String JOB_ID = "JOB_ID_";
    private final String JOB_GROUP_ID = "JOB_GROUP_ID_";
    private final String JOB_CRON_EXPRESSION = "JOB_CRON_EXPRESSION_";
    private final String JOB_PARAM = "JOB_PARAM_";
    private final String REMINDER_ID = "REMINDER_ID_";
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public String create(ReminderJobEntity jobEntity) {
        String sql = String.format("insert into %s (%s, %s, %s, %s ,%s,%s)values (?, ?, ?, ? ,?,?)", "SYS_REMINDER_JOB_PARAM", "ID_", "JOB_ID_", "JOB_GROUP_ID_", "JOB_CRON_EXPRESSION_", "JOB_PARAM_", "REMINDER_ID_");
        if (jobEntity.getId() == null) {
            jobEntity.setId(UUID.randomUUID().toString());
        }
        this.jdbcTemplate.update(sql, new Object[]{jobEntity.getId(), jobEntity.getJobId(), jobEntity.getJobGroupId(), jobEntity.getJobCronExpression(), jobEntity.getJobParamJsonString(), jobEntity.getReminderId()});
        return jobEntity.getId();
    }

    public void update(ReminderJobEntity jobEntity) {
        String sql = String.format("update %s set %s = ?, %s = ?, %s = ?, %s = ?,%s = ? where %s = ?", "SYS_REMINDER_JOB_PARAM", "JOB_ID_", "JOB_GROUP_ID_", "JOB_CRON_EXPRESSION_", "JOB_PARAM_", "REMINDER_ID_", "ID_");
        this.jdbcTemplate.update(sql, new Object[]{jobEntity.getJobId(), jobEntity.getJobGroupId(), jobEntity.getJobCronExpression(), jobEntity.getJobParamJsonString(), jobEntity.getReminderId(), jobEntity.getId()});
    }

    public ReminderJobEntity get(String id) {
        String selectSql = "select %s, %s, %s, %s, %s, %s from %s where %s = ?";
        String sql = String.format(selectSql, "ID_", "JOB_ID_", "JOB_GROUP_ID_", "JOB_CRON_EXPRESSION_", "JOB_PARAM_", "REMINDER_ID_", "SYS_REMINDER_JOB_PARAM", "REMINDER_ID_");
        Optional first = this.jdbcTemplate.query(sql, (rs, row) -> this.createJobEntity(rs), new Object[]{id}).stream().findFirst();
        return first.orElse(null);
    }

    public List<ReminderJobEntity> getAll() {
        String selectSql = "select %s, %s, %s, %s, %s, %s from %s";
        String sql = String.format(selectSql, "ID_", "JOB_ID_", "JOB_GROUP_ID_", "JOB_CRON_EXPRESSION_", "JOB_PARAM_", "REMINDER_ID_", "SYS_REMINDER_JOB_PARAM");
        return this.jdbcTemplate.query(sql, (rs, row) -> this.createJobEntity(rs));
    }

    public List<ReminderJobEntity> getAllById(String id) {
        String selectSql = "select %s, %s, %s, %s, %s, %s from %s where %s = ?";
        String sql = String.format(selectSql, "ID_", "JOB_ID_", "JOB_GROUP_ID_", "JOB_CRON_EXPRESSION_", "JOB_PARAM_", "REMINDER_ID_", "SYS_REMINDER_JOB_PARAM", "REMINDER_ID_");
        return this.jdbcTemplate.query(sql, (rs, row) -> this.createJobEntity(rs), new Object[]{id});
    }

    public void delete(String id) {
        String deleteSql = "delete from %s where %s = ?";
        String sql = String.format(deleteSql, "SYS_REMINDER_JOB_PARAM", "ID_");
        this.jdbcTemplate.update(sql, new Object[]{id});
    }

    public void deleteById(List<String> ids) {
        String sql = String.format("delete from %s where %s = ?", "SYS_REMINDER_JOB_PARAM", "ID_");
        List<Object[]> batchArgs = this.getBatchArgs(ids);
        try {
            this.jdbcTemplate.batchUpdate(sql.toString(), batchArgs);
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    private List<Object[]> getBatchArgs(List<String> formKeys) {
        ArrayList<Object[]> batchArgs = new ArrayList<Object[]>();
        for (String formKey : formKeys) {
            String[] params = new String[]{formKey};
            batchArgs.add(params);
        }
        return batchArgs;
    }

    private ReminderJobEntity createJobEntity(ResultSet rs) throws SQLException {
        ReminderJobEntity entity = new ReminderJobEntity();
        entity.setId(rs.getString("ID_"));
        entity.setJobId(rs.getString("JOB_ID_"));
        entity.setJobGroupId(rs.getString("JOB_GROUP_ID_"));
        entity.setJobCronExpression(rs.getString("JOB_CRON_EXPRESSION_"));
        entity.setJobParamByJsonString(rs.getString("JOB_PARAM_"));
        entity.setReminderId(rs.getString("REMINDER_ID_"));
        return entity;
    }
}

