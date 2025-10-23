/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.jdbc.core.RowMapper
 */
package com.jiuqi.nr.reminder.plan.dao.impl;

import com.jiuqi.nr.reminder.plan.dao.CbPlanDwDAO;
import com.jiuqi.nr.reminder.plan.dao.impl.CbPlanDwDO;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

@Repository
public class CbPlanDwDODAOImpl
implements CbPlanDwDAO {
    private final JdbcTemplate jdbcTemplate;
    private static final String INSERT_SQL = String.format("INSERT INTO %s (%s, %s, %s) VALUES (?, ?, ?)", "NR_CB_PLAN_DW", "P_ID", "P_UNIT_ID", "P_END_TIME");

    @Autowired
    public CbPlanDwDODAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void insert(CbPlanDwDO cbPlanDwDO) {
        this.jdbcTemplate.update(INSERT_SQL, new Object[]{cbPlanDwDO.getPlanId(), cbPlanDwDO.getUnitId(), cbPlanDwDO.getEndTime()});
    }

    @Override
    public void batchInsert(List<CbPlanDwDO> cbPlanDwList) {
        if (CollectionUtils.isEmpty(cbPlanDwList)) {
            return;
        }
        ArrayList<Object[]> batchArgs = new ArrayList<Object[]>();
        for (CbPlanDwDO cbPlanDw : cbPlanDwList) {
            Object[] args = new Object[]{cbPlanDw.getPlanId(), cbPlanDw.getUnitId(), cbPlanDw.getEndTime()};
            batchArgs.add(args);
        }
        this.jdbcTemplate.batchUpdate(INSERT_SQL, batchArgs);
    }

    @Override
    public void deleteByPlanId(String planId) {
        String sql = "DELETE FROM %s WHERE %s = ?";
        this.jdbcTemplate.update(String.format(sql, "NR_CB_PLAN_DW", "P_ID"), new Object[]{planId});
    }

    @Override
    public List<CbPlanDwDO> queryByPlanId(String planId) {
        String sql = "SELECT %s, %s, %s FROM %s WHERE %s = ?";
        return this.jdbcTemplate.query(String.format(sql, "P_ID", "P_UNIT_ID", "P_END_TIME", "NR_CB_PLAN_DW", "P_ID"), (RowMapper)new CbPlanDwDO(), new Object[]{planId});
    }

    @Override
    public List<CbPlanDwDO> batchQueryByPlanId(List<String> planIdList) {
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("SELECT ").append("P_ID").append(", ").append("P_UNIT_ID").append(", ").append("P_END_TIME").append(" FROM ").append("NR_CB_PLAN_DW").append(" WHERE ").append("P_ID").append(" IN (");
        for (int i = 0; i < planIdList.size(); ++i) {
            sqlBuilder.append("?");
            if (i >= planIdList.size() - 1) continue;
            sqlBuilder.append(",");
        }
        sqlBuilder.append(")");
        return this.jdbcTemplate.query(sqlBuilder.toString(), (RowMapper)new CbPlanDwDO(), planIdList.toArray());
    }
}

