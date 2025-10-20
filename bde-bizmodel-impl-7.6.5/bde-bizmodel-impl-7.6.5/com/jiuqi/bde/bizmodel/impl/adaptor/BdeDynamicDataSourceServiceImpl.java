/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.log.utils.BdeLogUtil
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.dc.base.common.jdbc.service.SqlRecordService
 *  com.jiuqi.va.query.common.service.QuerySqlInterceptorUtil
 *  com.jiuqi.va.query.datasource.enumerate.DataSourceEnum
 *  com.jiuqi.va.query.datasource.service.impl.DynamicDataSourceServiceImpl
 *  com.jiuqi.va.query.exception.DefinedQueryRuntimeException
 *  org.apache.shiro.util.ThreadContext
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.jdbc.core.ResultSetExtractor
 *  org.springframework.jdbc.core.RowMapper
 */
package com.jiuqi.bde.bizmodel.impl.adaptor;

import com.jiuqi.bde.log.utils.BdeLogUtil;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.dc.base.common.jdbc.service.SqlRecordService;
import com.jiuqi.va.query.common.service.QuerySqlInterceptorUtil;
import com.jiuqi.va.query.datasource.enumerate.DataSourceEnum;
import com.jiuqi.va.query.datasource.service.impl.DynamicDataSourceServiceImpl;
import com.jiuqi.va.query.exception.DefinedQueryRuntimeException;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;
import org.apache.shiro.util.ThreadContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

@Service
@Primary
public class BdeDynamicDataSourceServiceImpl
extends DynamicDataSourceServiceImpl {
    private final Logger logger = LoggerFactory.getLogger(((Object)((Object)this)).getClass());
    @Autowired
    private SqlRecordService sqlRecordService;

    public <T> T query(String dataSourceCode, String sql, Object[] args, ResultSetExtractor<T> rse) {
        Object rs = this.recordSql(sql, JsonUtils.writeValueAsString((Object)args), () -> {
            JdbcTemplate jdbcTemplate = this.getJdbcTemplate(dataSourceCode);
            try {
                return jdbcTemplate.query(QuerySqlInterceptorUtil.getInterceptorSqlString((String)sql), rse, args);
            }
            catch (Exception e) {
                this.logger.error("SQL\u67e5\u8be2\u51fa\u73b0\u9519\u8bef\uff1a{}", (Object)e.getMessage(), (Object)e);
                if (BdeLogUtil.isDebug()) {
                    throw new RuntimeException(e.getMessage(), e);
                }
                throw new DefinedQueryRuntimeException("\u67e5\u8be2\u5f02\u5e38\uff0c\u8bf7\u8054\u7cfb\u7ba1\u7406\u5458");
            }
        });
        return (T)rs;
    }

    public <T> List<T> query(String dataSourceCode, String sql, Object[] args, RowMapper<T> rowMapper) {
        List rs = this.recordSql(sql, JsonUtils.writeValueAsString((Object)args), () -> {
            JdbcTemplate jdbcTemplate = this.getJdbcTemplate(dataSourceCode);
            try {
                return jdbcTemplate.query(QuerySqlInterceptorUtil.getInterceptorSqlString((String)sql), rowMapper, args);
            }
            catch (Exception e) {
                this.logger.error("SQL\u67e5\u8be2\u51fa\u73b0\u9519\u8bef\uff1a{}", (Object)e.getMessage(), (Object)e);
                if (BdeLogUtil.isDebug()) {
                    throw new RuntimeException(e.getCause().getMessage(), e);
                }
                throw new DefinedQueryRuntimeException("\u67e5\u8be2\u5f02\u5e38\uff0c\u8bf7\u8054\u7cfb\u7ba1\u7406\u5458");
            }
        });
        return rs;
    }

    public int[] batchUpdate(String dataSourceCode, String sql, List<Object[]> batchArgs) {
        int[] rs = this.recordSql(sql, null, () -> super.batchUpdate(this.getDataSourceCodeByDefault(dataSourceCode), sql, batchArgs));
        return rs;
    }

    public void execute(String dataSourceCode, String sql) {
        this.recordSql(sql, null, () -> {
            super.execute(this.getDataSourceCodeByDefault(dataSourceCode), sql);
            return null;
        });
    }

    private String getDataSourceCodeByDefault(String dataSourceCode) {
        return StringUtils.isEmpty((String)dataSourceCode) ? DataSourceEnum.CURRENT.getName() : dataSourceCode;
    }

    private <T> T recordSql(String sql, String argsJson, Supplier<T> func) {
        String sqlLogId = UUIDUtils.newHalfGUIDStr();
        Date startTime = new Date();
        String taskLogId = null;
        Object sqlLogObj = ThreadContext.get((Object)"SQLLOGID_KEY");
        try {
            if (!Objects.isNull(sqlLogObj)) {
                taskLogId = (String)sqlLogObj;
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

