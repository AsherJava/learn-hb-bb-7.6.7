/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.BatchPreparedStatementSetter
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.jdbc.core.RowMapper
 */
package com.jiuqi.nr.reminder.plan.dao.impl;

import com.jiuqi.nr.reminder.plan.dao.CbPlanToDAO;
import com.jiuqi.nr.reminder.plan.dao.impl.CbPlanToDO;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

@Repository
public class CbPlanToDODAOImpl
implements CbPlanToDAO {
    private final JdbcTemplate jdbcTemplate;

    public CbPlanToDODAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void batchInsert(final List<CbPlanToDO> cbPlanToDOS) {
        if (CollectionUtils.isEmpty(cbPlanToDOS)) {
            return;
        }
        String sql = "INSERT INTO NR_CB_PLAN_TO(P_ID, TO_ID,TO_TYPE, TO_ID_TYPE) VALUES (?, ?, ?,?)";
        this.jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter(){

            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setString(1, ((CbPlanToDO)cbPlanToDOS.get(i)).getPlanId());
                ps.setString(2, ((CbPlanToDO)cbPlanToDOS.get(i)).getToId());
                ps.setInt(3, ((CbPlanToDO)cbPlanToDOS.get(i)).getToType());
                ps.setInt(4, ((CbPlanToDO)cbPlanToDOS.get(i)).getToIdType());
            }

            public int getBatchSize() {
                return cbPlanToDOS.size();
            }
        });
    }

    @Override
    public void deleteByPlanId(String planId) {
        String sql = "DELETE FROM NR_CB_PLAN_TO WHERE P_ID=?";
        this.jdbcTemplate.update(sql, new Object[]{planId});
    }

    @Override
    public List<CbPlanToDO> queryByPlanId(String planId) {
        String sql = "SELECT %s, %s, %s, %s FROM %s WHERE %s = ?";
        return this.jdbcTemplate.query(String.format(sql, "P_ID", "TO_ID", "TO_TYPE", "TO_ID_TYPE", "NR_CB_PLAN_TO", "P_ID"), (RowMapper)new CbPlanToDO(), new Object[]{planId});
    }
}

