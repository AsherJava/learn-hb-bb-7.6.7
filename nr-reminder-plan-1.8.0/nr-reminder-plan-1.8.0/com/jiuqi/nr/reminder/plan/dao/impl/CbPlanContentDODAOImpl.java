/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.jdbc.core.RowMapper
 */
package com.jiuqi.nr.reminder.plan.dao.impl;

import com.jiuqi.nr.reminder.plan.dao.CbPlanContentDAO;
import com.jiuqi.nr.reminder.plan.dao.impl.CbPlanContentDO;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class CbPlanContentDODAOImpl
implements CbPlanContentDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CbPlanContentDODAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int insert(CbPlanContentDO contentDO) {
        String sql = String.format("INSERT INTO %s (%s) VALUES (%s)", "NR_CB_PLAN_CONTENT", String.join((CharSequence)",", CbPlanContentDO.SELECT_COLUMNS), CbPlanContentDO.SELECT_COLUMNS.stream().map(r -> "?").collect(Collectors.joining(",")));
        Object[] args = new Object[]{contentDO.getId(), contentDO.getTitle(), contentDO.getContent(), contentDO.getCreateUser(), contentDO.getUpdateUser(), contentDO.getCreateTime(), contentDO.getUpdateTime()};
        return this.jdbcTemplate.update(sql, args);
    }

    @Override
    public int updateById(CbPlanContentDO contentDO) {
        String sql = String.format("UPDATE %s SET %s = ?, %s = ?, %s = ?, %s = ? WHERE %s = ?", "NR_CB_PLAN_CONTENT", "TITLE", "P_CONTENT", "P_UPDATE_USER", "P_UPDATE_TIME", "ID");
        Object[] args = new Object[]{contentDO.getContent(), contentDO.getUpdateUser(), contentDO.getUpdateTime(), contentDO.getId()};
        return this.jdbcTemplate.update(sql, args);
    }

    @Override
    public List<CbPlanContentDO> queryAll() {
        String sql = String.format("SELECT %s FROM %s ORDER BY %s DESC", String.join((CharSequence)",", CbPlanContentDO.SELECT_COLUMNS), "NR_CB_PLAN_CONTENT", "P_CREATE_TIME");
        return this.jdbcTemplate.query(sql, (RowMapper)new CbPlanContentDO());
    }

    @Override
    public void delete(String contentId) {
        String sql = String.format("DELETE FROM %s WHERE %s = ?", "NR_CB_PLAN_CONTENT", "ID");
        this.jdbcTemplate.update(sql, new Object[]{contentId});
    }
}

