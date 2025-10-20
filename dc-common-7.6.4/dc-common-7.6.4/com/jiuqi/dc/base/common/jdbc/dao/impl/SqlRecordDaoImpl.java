/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.OuterDataSourceUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.jdbc.core.ResultSetExtractor
 */
package com.jiuqi.dc.base.common.jdbc.dao.impl;

import com.jiuqi.common.base.util.OuterDataSourceUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.dc.base.common.jdbc.dao.SqlRecordDao;
import com.jiuqi.dc.base.common.jdbc.domain.SqlExecuteLogDO;
import com.jiuqi.dc.base.common.jdbc.extractor.IntegerResultSetExtractor;
import java.util.Date;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

@Repository
public class SqlRecordDaoImpl
implements SqlRecordDao {
    private JdbcTemplate getJdbcTemplate() {
        return OuterDataSourceUtils.getJdbcTemplate();
    }

    @Override
    public boolean existsSqlInfo(String sqlId) {
        return (Integer)this.getJdbcTemplate().query("SELECT COUNT(1) FROM GC_LOG_SQLINFO WHERE ID = ?", (ResultSetExtractor)new IntegerResultSetExtractor(), new Object[]{sqlId}) > 0;
    }

    @Override
    public int insertSqlInfo(String sqlId, String sqlText) {
        return this.getJdbcTemplate().update("INSERT INTO GC_LOG_SQLINFO (ID, SQLFULLTEXT) VALUES(?, ?)", new Object[]{sqlId, sqlText});
    }

    @Override
    public int insertSqlExecuteLog(SqlExecuteLogDO sqlExecuteLog) {
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO GC_LOG_SQLEXECUTE (ID, LOGID, SQLINFOID, EXECUTEPARAM, STARTTIME) \n");
        sql.append("VALUES(?, ?, ?, ?, ?) \n");
        if (!StringUtils.isEmpty((String)sqlExecuteLog.getExecuteParam()) && sqlExecuteLog.getExecuteParam().length() > 1000) {
            sqlExecuteLog.setExecuteParam(sqlExecuteLog.getExecuteParam().substring(0, 1000));
        }
        Object[] args = new Object[]{sqlExecuteLog.getId(), sqlExecuteLog.getLogId(), sqlExecuteLog.getSqlInfoId(), sqlExecuteLog.getExecuteParam(), sqlExecuteLog.getStartTime().getTime()};
        return this.getJdbcTemplate().update(sql.toString(), args);
    }

    @Override
    public void recordEndTime(String logId, Date endTime) {
        this.getJdbcTemplate().update("UPDATE GC_LOG_SQLEXECUTE SET ENDTIME = ? WHERE ID = ? ", new Object[]{endTime.getTime(), logId});
    }
}

