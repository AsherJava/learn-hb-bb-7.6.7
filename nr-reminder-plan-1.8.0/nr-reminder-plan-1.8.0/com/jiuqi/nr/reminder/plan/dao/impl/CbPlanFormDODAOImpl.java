/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.BatchPreparedStatementSetter
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.jdbc.core.RowMapper
 */
package com.jiuqi.nr.reminder.plan.dao.impl;

import com.jiuqi.nr.reminder.plan.dao.CbPlanFDAO;
import com.jiuqi.nr.reminder.plan.dao.impl.CbPlanFormDO;
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
public class CbPlanFormDODAOImpl
implements CbPlanFDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CbPlanFormDODAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int insert(CbPlanFormDO cbPlanFDO) {
        String sql = "INSERT INTO NR_CB_PLAN_F(P_ID, F_ID, F_TYPE) VALUES (?, ?, ?)";
        return this.jdbcTemplate.update(sql, new Object[]{cbPlanFDO.getPlanId(), cbPlanFDO.getFormId(), cbPlanFDO.getFormType()});
    }

    @Override
    public void batchInsert(final List<CbPlanFormDO> cbPlanFDOS) {
        if (CollectionUtils.isEmpty(cbPlanFDOS)) {
            return;
        }
        String sql = "INSERT INTO NR_CB_PLAN_F(P_ID, F_ID, F_TYPE) VALUES (?, ?, ?)";
        this.jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter(){

            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setString(1, ((CbPlanFormDO)cbPlanFDOS.get(i)).getPlanId());
                ps.setString(2, ((CbPlanFormDO)cbPlanFDOS.get(i)).getFormId());
                ps.setInt(3, ((CbPlanFormDO)cbPlanFDOS.get(i)).getFormType());
            }

            public int getBatchSize() {
                return cbPlanFDOS.size();
            }
        });
    }

    @Override
    public void update(CbPlanFormDO cbPlanFDO) {
        String sql = "UPDATE NR_CB_PLAN_F SET F_TYPE=? WHERE P_ID=? AND F_ID=?";
        this.jdbcTemplate.update(sql, new Object[]{cbPlanFDO.getFormType(), cbPlanFDO.getPlanId(), cbPlanFDO.getFormId()});
    }

    @Override
    public void deleteByPlanId(String planId) {
        String sql = "DELETE FROM NR_CB_PLAN_F WHERE P_ID=?";
        this.jdbcTemplate.update(sql, new Object[]{planId});
    }

    @Override
    public List<CbPlanFormDO> queryByPlanId(String planId) {
        String sql = "SELECT %s, %s, %s FROM %s WHERE %s = ?";
        return this.jdbcTemplate.query(String.format(sql, "P_ID", "F_ID", "F_TYPE", "NR_CB_PLAN_F", "P_ID"), (RowMapper)new CbPlanFormDO(), new Object[]{planId});
    }
}

