/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.jdbc.core.RowMapper
 */
package com.jiuqi.nr.reminder.plan.dao.impl;

import com.jiuqi.nr.reminder.plan.dao.CbExecLogDAO;
import com.jiuqi.nr.reminder.plan.dao.impl.CbExecLogDO;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class CbExecLogDODAOImpl
implements CbExecLogDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CbExecLogDODAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int insert(CbExecLogDO execLogDO) {
        String sql = "INSERT INTO NR_CB_EXEC_LOG(LOG_ID, P_ID, LOG_START_TIME, LOG_END_TIME, P_STATUS, LOG_EXEC_USER, LOG_MESSAGE) VALUES (?, ?, ?, ?, ?, ?, ?)";
        return this.jdbcTemplate.update(sql, new Object[]{execLogDO.getLogId(), execLogDO.getPlanId(), execLogDO.getStartTime(), execLogDO.getEndTime(), execLogDO.getStatus(), execLogDO.getExecUser(), execLogDO.getMessage()});
    }

    @Override
    public void update(CbExecLogDO execLogDO) {
        String sql = "UPDATE NR_CB_EXEC_LOG SET P_STATUS=?, LOG_END_TIME=?, LOG_MESSAGE=? WHERE LOG_ID=?";
        this.jdbcTemplate.update(sql, new Object[]{execLogDO.getStatus(), execLogDO.getEndTime(), execLogDO.getMessage(), execLogDO.getLogId()});
    }

    @Override
    public void deleteByPlanId(String planId) {
        String sql = "DELETE FROM NR_CB_EXEC_LOG WHERE P_ID=?";
        this.jdbcTemplate.update(sql, new Object[]{planId});
    }

    @Override
    public CbExecLogDO queryLatestLogByPlanId(String planId) {
        Optional first = this.queryLogByPlanId(planId).stream().findFirst();
        return first.orElse(null);
    }

    @Override
    public List<CbExecLogDO> queryLogByPlanId(String planId) {
        String sql = "SELECT * FROM NR_CB_EXEC_LOG WHERE P_ID=? ORDER BY LOG_START_TIME DESC ";
        return this.jdbcTemplate.query(sql, (RowMapper)new CbExecLogDO(), new Object[]{planId});
    }

    @Override
    public int countByPlanId(String planId) {
        String sql = "SELECT COUNT(1) FROM NR_CB_EXEC_LOG WHERE P_ID=? ";
        Integer count = (Integer)this.jdbcTemplate.queryForObject(sql, Integer.class, new Object[]{planId});
        if (count == null) {
            count = 0;
        }
        return count;
    }
}

