/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.dc.taskscheduling.core.jdbc;

import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.dc.taskscheduling.api.TaskHandlerFactory;
import com.jiuqi.dc.taskscheduling.core.jdbc.RecordSqlJdbcTemplate;
import org.springframework.jdbc.core.JdbcTemplate;

public class RecordSqlJdbcTemplateFactory {
    private static TaskHandlerFactory taskHandlerFactory;
    private static JdbcTemplate defaultJdbcTemplate;
    private static RecordSqlJdbcTemplate defaultRecordSqlJdbcTemplate;

    public static RecordSqlJdbcTemplate getDefaultRecordSqlJdbcTemplate() {
        if (defaultRecordSqlJdbcTemplate == null) {
            defaultRecordSqlJdbcTemplate = new RecordSqlJdbcTemplate(RecordSqlJdbcTemplateFactory.getDefaultJdbcTemplate(), RecordSqlJdbcTemplateFactory.getTaskHandlerFactory());
        }
        return defaultRecordSqlJdbcTemplate;
    }

    public static RecordSqlJdbcTemplate getRecordSqlJdbcTemplate(JdbcTemplate jdbcTemplate) {
        return new RecordSqlJdbcTemplate(jdbcTemplate, RecordSqlJdbcTemplateFactory.getTaskHandlerFactory());
    }

    private static TaskHandlerFactory getTaskHandlerFactory() {
        if (taskHandlerFactory == null) {
            taskHandlerFactory = (TaskHandlerFactory)SpringContextUtils.getBean(TaskHandlerFactory.class);
        }
        return taskHandlerFactory;
    }

    private static JdbcTemplate getDefaultJdbcTemplate() {
        if (defaultJdbcTemplate == null) {
            defaultJdbcTemplate = (JdbcTemplate)SpringContextUtils.getBean(JdbcTemplate.class);
        }
        return defaultJdbcTemplate;
    }
}

