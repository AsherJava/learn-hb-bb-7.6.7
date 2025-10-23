/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.BatchPreparedStatementSetter
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.jdbc.core.RowMapper
 */
package com.jiuqi.nr.reminder.plan.dao.impl;

import com.jiuqi.nr.reminder.plan.dao.CbPlanTimeDAO;
import com.jiuqi.nr.reminder.plan.dao.impl.CbPlanTimeDO;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

@Repository
public class CbPlanTimeDODAOImpl
implements CbPlanTimeDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CbPlanTimeDODAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int insert(CbPlanTimeDO cbPlanTimeDO) {
        String sql = "INSERT INTO NR_CB_PLAN_TIME(P_ID, ID, P_PERIOD_TYPE, P_PERIOD_VALUE, P_TIME) VALUES (?, ?, ?, ?, ?)";
        return this.jdbcTemplate.update(sql, new Object[]{cbPlanTimeDO.getPlanId(), cbPlanTimeDO.getId(), cbPlanTimeDO.getPeriodType(), cbPlanTimeDO.getPeriodValue(), cbPlanTimeDO.getTime()});
    }

    @Override
    public void batchInsert(final List<CbPlanTimeDO> cbPlanTimeDOS) {
        if (CollectionUtils.isEmpty(cbPlanTimeDOS)) {
            return;
        }
        String sql = "INSERT INTO NR_CB_PLAN_TIME(P_ID, ID, P_PERIOD_TYPE, P_PERIOD_VALUE, P_TIME) VALUES (?, ?, ?, ?, ?)";
        this.jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter(){

            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setString(1, ((CbPlanTimeDO)cbPlanTimeDOS.get(i)).getPlanId());
                ps.setString(2, ((CbPlanTimeDO)cbPlanTimeDOS.get(i)).getId());
                ps.setInt(3, ((CbPlanTimeDO)cbPlanTimeDOS.get(i)).getPeriodType());
                ps.setInt(4, ((CbPlanTimeDO)cbPlanTimeDOS.get(i)).getPeriodValue());
                ps.setTimestamp(5, ((CbPlanTimeDO)cbPlanTimeDOS.get(i)).getTime());
            }

            public int getBatchSize() {
                return cbPlanTimeDOS.size();
            }
        });
    }

    @Override
    public void deleteByPlanId(String planId) {
        String sql = "DELETE FROM NR_CB_PLAN_TIME WHERE P_ID = ?";
        this.jdbcTemplate.update(sql, new Object[]{planId});
    }

    @Override
    public List<CbPlanTimeDO> queryByPlanId(String planId) {
        String sql = "SELECT P_ID, ID, P_PERIOD_TYPE, P_PERIOD_VALUE, P_TIME FROM NR_CB_PLAN_TIME WHERE P_ID = ? ORDER BY ID";
        return this.jdbcTemplate.query(sql, (RowMapper)new CbPlanTimeDO(), new Object[]{planId});
    }
}

