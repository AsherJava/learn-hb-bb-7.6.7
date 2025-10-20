/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.OuterDataSourceUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 */
package com.jiuqi.dc.base.common.jdbc.service.impl;

import com.jiuqi.common.base.util.OuterDataSourceUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.dc.base.common.jdbc.dao.SqlRecordDao;
import com.jiuqi.dc.base.common.jdbc.domain.SqlExecuteLogDO;
import com.jiuqi.dc.base.common.jdbc.service.SqlRecordService;
import com.jiuqi.dc.base.common.utils.SqlUtil;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class SqlRecordServiceImpl
implements SqlRecordService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private SqlRecordDao sqlRecordDao;

    @Override
    @Async(value="sql-record-executor")
    public void recordSql(String sqlLogId, String taskLogId, String sql, String argsJson, Date startTime) {
        String sqlId = this.recordSqlInfo(sql);
        SqlExecuteLogDO log = new SqlExecuteLogDO(sqlLogId, taskLogId, sqlId, argsJson, startTime);
        this.sqlRecordDao.insertSqlExecuteLog(log);
    }

    @Override
    @Async(value="sql-record-executor")
    public void recordEndTime(String logId, Date endTime) {
        this.sqlRecordDao.recordEndTime(logId, endTime);
    }

    private String recordSqlInfo(String sql) {
        String sqlId = UUIDUtils.nameUUIDFromBytes((byte[])sql.getBytes());
        if (!this.sqlRecordDao.existsSqlInfo(sqlId)) {
            try {
                this.sqlRecordDao.insertSqlInfo(sqlId, sql);
            }
            catch (Exception e) {
                String dbType = OuterDataSourceUtils.getJdbcTemplate().getDbType();
                if (SqlUtil.isSQLUniqueException(dbType, e)) {
                    this.logger.info("\u65b0\u589e\u6267\u884cSQL\u4fe1\u606f\u91cd\u590d\uff0cSQLID={}", (Object)sqlId);
                }
                this.logger.error("\u65b0\u589e\u6267\u884cSQL\u4fe1\u606f\u51fa\u73b0\u5f02\u5e38", e);
            }
        }
        return sqlId;
    }
}

