/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.datasource.GcBizJdbcTemplate
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.OuterDataSourceUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.dc.base.common.jdbc.sqlhandler.IDbSqlHandler
 *  com.jiuqi.dc.base.common.jdbc.sqlhandler.SqlHandlerUtil
 *  com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService
 *  com.jiuqi.va.query.datasource.dao.DataSourceInfoDao
 *  com.jiuqi.va.query.datasource.enumerate.DataSourceEnum
 *  com.jiuqi.va.query.datasource.service.DynamicDataSourceService
 *  org.apache.shiro.util.ThreadContext
 *  org.springframework.jdbc.core.BatchPreparedStatementSetter
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.jdbc.core.ParameterizedPreparedStatementSetter
 *  org.springframework.jdbc.core.ResultSetExtractor
 *  org.springframework.jdbc.core.RowMapper
 */
package com.jiuqi.dc.base.common.jdbc.dao.impl;

import com.jiuqi.common.base.datasource.GcBizJdbcTemplate;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.OuterDataSourceUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.dc.base.common.jdbc.dao.BaseDataCenterDao;
import com.jiuqi.dc.base.common.jdbc.datasource.IDynamicDataSourceProvider;
import com.jiuqi.dc.base.common.jdbc.service.SqlRecordService;
import com.jiuqi.dc.base.common.jdbc.sqlhandler.IDbSqlHandler;
import com.jiuqi.dc.base.common.jdbc.sqlhandler.SqlHandlerUtil;
import com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService;
import com.jiuqi.va.query.datasource.dao.DataSourceInfoDao;
import com.jiuqi.va.query.datasource.enumerate.DataSourceEnum;
import com.jiuqi.va.query.datasource.service.DynamicDataSourceService;
import java.sql.Connection;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;
import org.apache.shiro.util.ThreadContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ParameterizedPreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

@AutoConfigureAfter(value={IDynamicDataSourceProvider.class, DynamicDataSourceService.class, DataSourceInfoDao.class, SqlRecordService.class})
public class BaseDataCenterDaoImpl
implements BaseDataCenterDao {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private IDynamicDataSourceProvider dynamicDataSourceProvider;
    @Autowired
    private DynamicDataSourceService dynamicDataSourceService;
    @Autowired
    private DataSourceInfoDao dataSourceInfoDao;
    @Autowired
    private SqlRecordService sqlRecordService;
    @Autowired
    private INvwaSystemOptionService sysOptionService;

    @Override
    public Connection getConnection() {
        return this.dynamicDataSourceProvider.getConnection();
    }

    @Override
    public void closeConnection(Connection conn) {
        this.dynamicDataSourceProvider.closeConnection(conn);
    }

    @Override
    public JdbcTemplate getJdbcTemplate() {
        return this.dynamicDataSourceProvider.getJdbcTemplate();
    }

    @Override
    public JdbcTemplate getJdbcTemplate(String dataSourceCode) {
        return this.dynamicDataSourceService.getJdbcTemplate(dataSourceCode);
    }

    @Override
    public IDbSqlHandler getDbSqlHandler() {
        return this.dynamicDataSourceProvider.getDbSqlHandler();
    }

    @Override
    public IDbSqlHandler getDbSqlHandler(String dataSourceCode) {
        if (StringUtils.isEmpty((String)dataSourceCode) || DataSourceEnum.CURRENT.getName().equals(dataSourceCode)) {
            return this.getDbSqlHandler();
        }
        return SqlHandlerUtil.getDbSqlHandler((String)this.dataSourceInfoDao.getDataSourceInfoByCode(dataSourceCode).getDataBaseType());
    }

    @Override
    public int update(String sql, Object ... args) {
        int rs = this.recordSql(sql, JsonUtils.writeValueAsString((Object)args), () -> {
            GcBizJdbcTemplate jdbcTemplate = OuterDataSourceUtils.getJdbcTemplate();
            return jdbcTemplate.update(sql, args);
        });
        return rs;
    }

    @Override
    public int[] batchUpdate(String sql, List<Object[]> batchArgs) {
        int[] rs = this.recordSql(sql, null, () -> {
            GcBizJdbcTemplate jdbcTemplate = OuterDataSourceUtils.getJdbcTemplate();
            return jdbcTemplate.batchUpdate(sql, batchArgs);
        });
        return rs;
    }

    @Override
    public int[] batchUpdate(String sql, BatchPreparedStatementSetter pss) {
        int[] rs = this.recordSql(sql, null, () -> {
            GcBizJdbcTemplate jdbcTemplate = OuterDataSourceUtils.getJdbcTemplate();
            return jdbcTemplate.batchUpdate(sql, pss);
        });
        return rs;
    }

    @Override
    public <T> int[][] batchUpdate(String sql, Collection<T> batchArgs, int batchSize, ParameterizedPreparedStatementSetter<T> pss) {
        int[][] rs = this.recordSql(sql, null, () -> {
            GcBizJdbcTemplate jdbcTemplate = OuterDataSourceUtils.getJdbcTemplate();
            return jdbcTemplate.batchUpdate(sql, batchArgs, batchSize, pss);
        });
        return rs;
    }

    @Override
    public <T> List<T> query(String sql, RowMapper<T> rse, Object ... args) {
        List rs = this.recordSql(sql, JsonUtils.writeValueAsString((Object)args), () -> {
            GcBizJdbcTemplate jdbcTemplate = OuterDataSourceUtils.getJdbcTemplate();
            return jdbcTemplate.query(sql, rse, args);
        });
        return rs;
    }

    @Override
    public <T> T query(String sql, ResultSetExtractor<T> rse, Object ... args) {
        Object rs = this.recordSql(sql, JsonUtils.writeValueAsString((Object)args), () -> {
            GcBizJdbcTemplate jdbcTemplate = OuterDataSourceUtils.getJdbcTemplate();
            return jdbcTemplate.query(sql, rse, args);
        });
        return (T)rs;
    }

    @Override
    public int update(String dataSourceCode, String sql, Object[] args) {
        int rs = this.recordSql(sql, JsonUtils.writeValueAsString((Object)args), () -> {
            JdbcTemplate jdbcTemplate = this.getJdbcTemplate(dataSourceCode);
            return jdbcTemplate.update(sql, args);
        });
        return rs;
    }

    @Override
    public int[] batchUpdate(String dataSourceCode, String sql, List<Object[]> batchArgs) {
        int[] rs = this.recordSql(sql, null, () -> {
            JdbcTemplate jdbcTemplate = this.getJdbcTemplate(dataSourceCode);
            return jdbcTemplate.batchUpdate(sql, batchArgs);
        });
        return rs;
    }

    @Override
    public int[] batchUpdate(String dataSourceCode, String sql, BatchPreparedStatementSetter pss) {
        int[] rs = this.recordSql(sql, null, () -> {
            JdbcTemplate jdbcTemplate = this.getJdbcTemplate(dataSourceCode);
            return jdbcTemplate.batchUpdate(sql, pss);
        });
        return rs;
    }

    @Override
    public <T> int[][] batchUpdate(String dataSourceCode, String sql, Collection<T> batchArgs, int batchSize, ParameterizedPreparedStatementSetter<T> pss) {
        int[][] rs = this.recordSql(sql, null, () -> {
            JdbcTemplate jdbcTemplate = this.getJdbcTemplate(dataSourceCode);
            return jdbcTemplate.batchUpdate(sql, batchArgs, batchSize, pss);
        });
        return rs;
    }

    @Override
    public <T> List<T> query(String dataSourceCode, String sql, RowMapper<T> rse, Object ... args) {
        List rs = this.recordSql(sql, JsonUtils.writeValueAsString((Object)args), () -> {
            JdbcTemplate jdbcTemplate = this.getJdbcTemplate(dataSourceCode);
            return jdbcTemplate.query(sql, rse, args);
        });
        return rs;
    }

    @Override
    public <T> T query(String dataSourceCode, String sql, ResultSetExtractor<T> rse, Object ... args) {
        Object rs = this.recordSql(sql, JsonUtils.writeValueAsString((Object)args), () -> {
            JdbcTemplate jdbcTemplate = this.getJdbcTemplate(dataSourceCode);
            return jdbcTemplate.query(sql, rse, args);
        });
        return (T)rs;
    }

    private <T> T recordSql(String sql, String argsJson, Supplier<T> func) {
        boolean sqlRecordEnable = "1".equals(this.sysOptionService.findValueById("JDBC_SQL_RECORD"));
        if (!sqlRecordEnable) {
            return func.get();
        }
        String sqlLogId = UUIDUtils.newHalfGUIDStr();
        Date startTime = new Date();
        String taskLogId = null;
        try {
            if (!Objects.isNull(ThreadContext.get((Object)"SQLLOGID_KEY"))) {
                taskLogId = String.valueOf(ThreadContext.get((Object)"SQLLOGID_KEY"));
                this.sqlRecordService.recordSql(sqlLogId, taskLogId, sql, argsJson, startTime);
            }
        }
        catch (Exception e) {
            this.logger.error("\u8bb0\u5f55\u6267\u884cSQL\u65e5\u5fd7\u51fa\u73b0\u5f02\u5e38", e);
        }
        T rs = func.get();
        Date endTime = new Date();
        try {
            if (!Objects.isNull(taskLogId)) {
                this.sqlRecordService.recordEndTime(sqlLogId, endTime);
            }
        }
        catch (Exception e) {
            this.logger.error("\u8bb0\u5f55\u6267\u884cSQL\u7ed3\u675f\u65f6\u95f4\u51fa\u73b0\u5f02\u5e38", e);
        }
        return rs;
    }
}

