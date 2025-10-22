/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.nr.definition.option.dao;

import com.jiuqi.nr.definition.option.core.TaskOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

@Repository
public class TaskOptionDao {
    public static final String TABLE_NAME = "NR_TASK_DEFINE_OPTION";
    public static final String SO_KEY = "SO_KEY";
    public static final String SO_TASK_KEY = "SO_TASK_KEY";
    public static final String SO_VALUE = "SO_VALUE";
    public static final String COL = "SO_KEY,SO_TASK_KEY,SO_VALUE";
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private static final String SQL_INSERT = String.format("INSERT INTO %s (%s, %s, %s) VALUES (?, ?, ?)", "NR_TASK_DEFINE_OPTION", "SO_KEY", "SO_TASK_KEY", "SO_VALUE");
    private static final String SQL_DELETE = String.format("DELETE FROM %s WHERE %s = ? AND %s = ?", "NR_TASK_DEFINE_OPTION", "SO_KEY", "SO_TASK_KEY");
    private static final String SQL_DELETE_BY_TASK = String.format("DELETE FROM %s WHERE %s = ?", "NR_TASK_DEFINE_OPTION", "SO_TASK_KEY");
    private static final String SQL_QUERY_BY_TASK = String.format("SELECT %s FROM %s WHERE %s = ?", "SO_KEY,SO_TASK_KEY,SO_VALUE", "NR_TASK_DEFINE_OPTION", "SO_TASK_KEY");
    private static final String SQL_QUERY_BY_KEY = String.format("SELECT %s FROM %s WHERE %s = ? AND %s = ?", "SO_KEY,SO_TASK_KEY,SO_VALUE", "NR_TASK_DEFINE_OPTION", "SO_TASK_KEY", "SO_KEY");

    public void setOption(TaskOption entity) {
        Assert.notNull((Object)entity, "this argument is required; it must not be null");
        this.setOptions(Collections.singletonList(entity));
    }

    public void setOptions(List<TaskOption> entities) {
        Assert.notNull(entities, "this argument is required; it must not be null");
        ArrayList<Object[]> args = new ArrayList<Object[]>(entities.size());
        ArrayList<Object[]> args2 = new ArrayList<Object[]>(entities.size());
        for (TaskOption entity : entities) {
            Assert.notNull((Object)entity, "this argument is required; it must not be null");
            String key = entity.getKey();
            String taskKey = entity.getTaskKey();
            Assert.notNull((Object)key, "this argument is required; it must not be null");
            Assert.notNull((Object)taskKey, "this argument is required; it must not be null");
            args.add(new Object[]{key, taskKey});
            args2.add(new Object[]{key, taskKey, entity.getValue()});
        }
        this.jdbcTemplate.batchUpdate(SQL_DELETE, args);
        this.jdbcTemplate.batchUpdate(SQL_INSERT, args2);
    }

    public void resetOptions(String taskKey) {
        Assert.notNull((Object)taskKey, "this argument is required; it must not be null");
        this.jdbcTemplate.update(SQL_DELETE_BY_TASK, new Object[]{taskKey});
    }

    public TaskOption getOption(String taskKey, String key) {
        Assert.notNull((Object)taskKey, "this argument is required; it must not be null");
        Assert.notNull((Object)key, "this argument is required; it must not be null");
        return (TaskOption)this.jdbcTemplate.query(SQL_QUERY_BY_KEY, rs -> {
            if (rs.next()) {
                TaskOption entity = new TaskOption();
                entity.setKey(rs.getString(SO_KEY));
                entity.setTaskKey(rs.getString(SO_TASK_KEY));
                entity.setValue(rs.getString(SO_VALUE));
                return entity;
            }
            return null;
        }, new Object[]{taskKey, key});
    }

    public List<TaskOption> getOptions(String taskKey) {
        Assert.notNull((Object)taskKey, "this argument is required; it must not be null");
        return (List)this.jdbcTemplate.query(SQL_QUERY_BY_TASK, rs -> {
            ArrayList<TaskOption> list = new ArrayList<TaskOption>();
            while (rs.next()) {
                TaskOption entity = new TaskOption();
                entity.setKey(rs.getString(SO_KEY));
                entity.setTaskKey(rs.getString(SO_TASK_KEY));
                entity.setValue(rs.getString(SO_VALUE));
                list.add(entity);
            }
            return list;
        }, new Object[]{taskKey});
    }
}

