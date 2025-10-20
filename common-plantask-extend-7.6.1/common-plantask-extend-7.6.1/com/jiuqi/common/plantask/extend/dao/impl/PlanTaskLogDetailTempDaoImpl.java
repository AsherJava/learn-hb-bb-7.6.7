/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.extension.ILogGenerator$LogItem
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.gcreport.i18n.util.GcI18nUtil
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.common.plantask.extend.dao.impl;

import com.jiuqi.bi.core.jobs.extension.ILogGenerator;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.plantask.extend.common.PlanTaskExtendI18Const;
import com.jiuqi.common.plantask.extend.dao.PlanTaskLogDetailTempDao;
import com.jiuqi.gcreport.i18n.util.GcI18nUtil;
import java.io.UnsupportedEncodingException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class PlanTaskLogDetailTempDaoImpl
implements PlanTaskLogDetailTempDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<ILogGenerator.LogItem> getLogItemByInstanceId(String id) {
        String sql = "SELECT L_ID,L_MESSAGE,L_HASDETAIL,L_TIMESTAMP,L_LEVEL  FROM BI_JOBS_LOGS WHERE L_INSTANCEID = ? order by L_TIMESTAMP ";
        return this.jdbcTemplate.query(sql, (rs, row) -> {
            String levelStr;
            ILogGenerator.LogItem logItem = new ILogGenerator.LogItem();
            logItem.setId(rs.getLong(1));
            logItem.setMessage(rs.getString(2));
            logItem.setHasDetail(rs.getBoolean(3));
            logItem.setTimestamp(rs.getLong(4));
            int level = rs.getInt(5);
            switch (level) {
                case 0: {
                    levelStr = GcI18nUtil.getMessage((String)PlanTaskExtendI18Const.LEVEL_TRACK);
                    break;
                }
                case 10: {
                    levelStr = GcI18nUtil.getMessage((String)PlanTaskExtendI18Const.LEVEL_DEBUG);
                    break;
                }
                case 30: {
                    levelStr = GcI18nUtil.getMessage((String)PlanTaskExtendI18Const.LEVEL_WARN);
                    break;
                }
                case 40: {
                    levelStr = GcI18nUtil.getMessage((String)PlanTaskExtendI18Const.LEVEL_ERROR);
                    break;
                }
                default: {
                    levelStr = GcI18nUtil.getMessage((String)PlanTaskExtendI18Const.LEVEL_INFO);
                }
            }
            logItem.setLevel(levelStr);
            return logItem;
        }, new Object[]{id});
    }

    @Override
    public String getDetailById(long id) {
        String sql = "SELECT L_DETAIL  FROM BI_JOBS_LOGDETAIL WHERE L_ID = ? ";
        return (String)this.jdbcTemplate.query(sql, rs -> {
            if (rs.next()) {
                try {
                    return new String(rs.getBytes(1), "UTF-8");
                }
                catch (UnsupportedEncodingException e) {
                    throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)PlanTaskExtendI18Const.QUERY_EXCEPTION_MESSAGE) + ":" + e.getMessage(), (Throwable)e);
                }
            }
            return "";
        }, new Object[]{id});
    }
}

