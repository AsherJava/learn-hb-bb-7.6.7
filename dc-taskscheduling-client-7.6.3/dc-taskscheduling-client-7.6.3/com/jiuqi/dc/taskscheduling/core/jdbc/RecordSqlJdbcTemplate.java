/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  org.apache.shiro.util.ThreadContext
 *  org.springframework.jdbc.core.BatchPreparedStatementSetter
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.jdbc.core.ParameterizedPreparedStatementSetter
 *  org.springframework.jdbc.core.ResultSetExtractor
 *  org.springframework.jdbc.core.RowMapper
 */
package com.jiuqi.dc.taskscheduling.core.jdbc;

import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.dc.taskscheduling.api.TaskHandlerClient;
import com.jiuqi.dc.taskscheduling.api.TaskHandlerFactory;
import com.jiuqi.dc.taskscheduling.core.data.SqlExecuteLogDTO;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;
import org.apache.shiro.util.ThreadContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ParameterizedPreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

public class RecordSqlJdbcTemplate {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final JdbcTemplate jdbcTemplate;
    private final TaskHandlerFactory taskHandlerFactory;

    public RecordSqlJdbcTemplate(JdbcTemplate jdbcTemplate, TaskHandlerFactory taskHandlerFactory) {
        this.jdbcTemplate = jdbcTemplate;
        this.taskHandlerFactory = taskHandlerFactory;
    }

    public int update(String sql, Object ... args) {
        return this.recordSql(sql, JsonUtils.writeValueAsString((Object)args), () -> this.jdbcTemplate.update(sql, args));
    }

    public int[] batchUpdate(String sql, List<Object[]> batchArgs) {
        return this.recordSql(sql, null, () -> this.jdbcTemplate.batchUpdate(sql, batchArgs));
    }

    public int[] batchUpdate(String sql, BatchPreparedStatementSetter pss) {
        return this.recordSql(sql, null, () -> this.jdbcTemplate.batchUpdate(sql, pss));
    }

    public <T> int[][] batchUpdate(String sql, Collection<T> batchArgs, int batchSize, ParameterizedPreparedStatementSetter<T> pss) {
        return this.recordSql(sql, null, () -> this.jdbcTemplate.batchUpdate(sql, batchArgs, batchSize, pss));
    }

    public <T> List<T> query(String sql, RowMapper<T> rse, Object ... args) {
        return this.recordSql(sql, JsonUtils.writeValueAsString((Object)args), () -> this.jdbcTemplate.query(sql, rse, args));
    }

    public <T> T query(String sql, ResultSetExtractor<T> rse, Object ... args) {
        return (T)this.recordSql(sql, JsonUtils.writeValueAsString((Object)args), () -> this.jdbcTemplate.query(sql, rse, args));
    }

    private <T> T recordSql(String sql, String argsJson, Supplier<T> func) {
        if (Objects.isNull(ThreadContext.get((Object)"SQLLOGID_KEY"))) {
            return func.get();
        }
        if (!"1".equals(ThreadContext.get((Object)"SQL_RECORD_ENABLE_KEY"))) {
            return func.get();
        }
        TaskHandlerClient mainTaskHandlerClient = this.taskHandlerFactory.getMainTaskHandlerClient();
        String sqlLogId = UUIDUtils.newHalfGUIDStr();
        try {
            String taskLogId = String.valueOf(ThreadContext.get((Object)"SQLLOGID_KEY"));
            mainTaskHandlerClient.recordSql(new SqlExecuteLogDTO(sqlLogId, taskLogId, sql, argsJson, new Date()));
        }
        catch (Exception e) {
            this.logger.error("\u8bb0\u5f55\u6267\u884cSQL\u65e5\u5fd7\u51fa\u73b0\u5f02\u5e38", e);
        }
        T rs = func.get();
        try {
            mainTaskHandlerClient.recordSqlEndTime(sqlLogId, new Date());
        }
        catch (Exception e) {
            this.logger.error("\u8bb0\u5f55\u6267\u884cSQL\u7ed3\u675f\u65f6\u95f4\u51fa\u73b0\u5f02\u5e38", e);
        }
        return rs;
    }
}

