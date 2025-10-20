/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.OuterDataSourceUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.dc.base.common.jdbc.service.SqlRecordService
 *  org.apache.shiro.util.ThreadContext
 *  org.springframework.jdbc.BadSqlGrammarException
 *  org.springframework.jdbc.core.ResultSetExtractor
 */
package com.jiuqi.bde.bizmodel.impl.adaptor.service.impl;

import com.jiuqi.bde.bizmodel.impl.adaptor.service.OuterDataSourceService;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.OuterDataSourceUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.dc.base.common.jdbc.service.SqlRecordService;
import java.util.Date;
import java.util.Objects;
import java.util.function.Supplier;
import org.apache.shiro.util.ThreadContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Service;

@Service
public class OuterDataSourceServiceImpl
implements OuterDataSourceService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private SqlRecordService sqlRecordService;

    @Override
    public <T> T query(String sql, Object[] params, ResultSetExtractor<T> rse) {
        Assert.isNotEmpty((String)sql);
        Assert.isNotNull(rse);
        Object rs = this.recordSql(sql, JsonUtils.writeValueAsString((Object)params), () -> {
            try {
                return OuterDataSourceUtils.getJdbcTemplate().query(sql, params, rse);
            }
            catch (BadSqlGrammarException e) {
                throw new RuntimeException(e.getCause().getMessage(), e);
            }
        });
        return (T)rs;
    }

    protected <T> T recordSql(String sql, String argsJson, Supplier<T> func) {
        String sqlLogId = UUIDUtils.newHalfGUIDStr();
        Date startTime = new Date();
        try {
            if (!Objects.isNull(ThreadContext.get((Object)"SQLLOGID_KEY"))) {
                String taskLogId = String.valueOf(ThreadContext.get((Object)"SQLLOGID_KEY"));
                this.sqlRecordService.recordSql(sqlLogId, taskLogId, sql, argsJson, startTime);
            }
        }
        catch (Exception e) {
            this.logger.error("\u8bb0\u5f55\u6267\u884cSQL\u65e5\u5fd7\u51fa\u73b0\u5f02\u5e38", e);
        }
        T rs = func.get();
        Date endTime = new Date();
        try {
            if (!Objects.isNull(sqlLogId)) {
                this.sqlRecordService.recordEndTime(sqlLogId, endTime);
            }
        }
        catch (Exception e) {
            this.logger.error("\u8bb0\u5f55\u6267\u884cSQL\u7ed3\u675f\u65f6\u95f4\u51fa\u73b0\u5f02\u5e38", e);
        }
        return rs;
    }
}

