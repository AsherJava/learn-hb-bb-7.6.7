/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.database.DatabaseManager
 *  com.jiuqi.bi.database.IDatabase
 *  com.jiuqi.bi.database.paging.IPagingSQLBuilder
 *  com.jiuqi.bi.database.paging.OrderField
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.jdbc.core.RowMapper
 */
package com.jiuqi.nr.reminder.plan.dao.impl;

import com.jiuqi.bi.database.DatabaseManager;
import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.bi.database.paging.IPagingSQLBuilder;
import com.jiuqi.bi.database.paging.OrderField;
import com.jiuqi.nr.reminder.plan.dao.CbPlanDAO;
import com.jiuqi.nr.reminder.plan.dao.impl.CbPlanDO;
import java.sql.Connection;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

@Repository
public class CbPlanDODAOImpl
implements CbPlanDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CbPlanDODAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int insertCbPlan(CbPlanDO cbPlanDO) {
        String sql = String.format("INSERT INTO %s (%s) VALUES (%s)", "NR_CB_PLAN", String.join((CharSequence)",", CbPlanDO.SELECT_COLUMNS), CbPlanDO.SELECT_COLUMNS.stream().map(r -> "?").collect(Collectors.joining(",")));
        Object[] args = new Object[]{cbPlanDO.getId(), cbPlanDO.getTitle(), cbPlanDO.getSource(), cbPlanDO.getEnabled(), cbPlanDO.getUserType(), cbPlanDO.getSendChannel(), cbPlanDO.getExecUser(), cbPlanDO.getEffectiveStartTime(), cbPlanDO.getEffectiveEndTime(), cbPlanDO.getKind(), cbPlanDO.getContent(), cbPlanDO.getTaskId(), cbPlanDO.getFormSchemeId(), cbPlanDO.getWorkFlowType(), cbPlanDO.getExecUnit(), cbPlanDO.getExecPeriod(), cbPlanDO.getUnitScop(), cbPlanDO.getFormScop(), cbPlanDO.getCreateUser(), cbPlanDO.getUpdateUser(), cbPlanDO.getCreateTime(), cbPlanDO.getUpdateTime(), cbPlanDO.getDw()};
        return this.jdbcTemplate.update(sql, args);
    }

    @Override
    public int updateCbPlanById(CbPlanDO cbPlanDO) {
        String sql = String.format("UPDATE %s SET %s = ?, %s = ?, %s = ?, %s = ?, %s = ?, %s = ?, %s = ?, %s = ?, %s = ?, %s = ?, %s = ?, %s = ?, %s = ?, %s = ?, %s = ?, %s = ?, %s = ?, %s = ? WHERE %s = ?", "NR_CB_PLAN", "P_TITLE", "P_ENABLED", "P_USER_TYPE", "P_SEND_CHANNEL", "P_EXEC_USER", "P_EFFECTIVE_START_TIME", "P_EFFECTIVE_END_TIME", "P_KIND", "P_CONTENT", "P_TASK_ID", "P_FORMSCHEME_ID", "P_WORKFLOW_TYPE", "P_EXEC_UNIT", "P_UNIT_SCOP", "P_FORM_SCOP", "P_UPDATE_USER", "P_UPDATE_TIME", "P_DW", "P_ID");
        Object[] args = new Object[]{cbPlanDO.getTitle(), cbPlanDO.getEnabled(), cbPlanDO.getUserType(), cbPlanDO.getSendChannel(), cbPlanDO.getExecUser(), cbPlanDO.getEffectiveStartTime(), cbPlanDO.getEffectiveEndTime(), cbPlanDO.getKind(), cbPlanDO.getContent(), cbPlanDO.getTaskId(), cbPlanDO.getFormSchemeId(), cbPlanDO.getWorkFlowType(), cbPlanDO.getExecUnit(), cbPlanDO.getUnitScop(), cbPlanDO.getFormScop(), cbPlanDO.getUpdateUser(), Timestamp.from(Instant.now()), cbPlanDO.getDw(), cbPlanDO.getId()};
        return this.jdbcTemplate.update(sql, args);
    }

    @Override
    public CbPlanDO queryCbPlanById(String id) {
        if (!StringUtils.hasLength(id)) {
            return null;
        }
        String sql = String.format("SELECT %s FROM %s WHERE %s = ?", String.join((CharSequence)",", CbPlanDO.SELECT_COLUMNS), "NR_CB_PLAN", "P_ID");
        List list = this.jdbcTemplate.query(sql, (RowMapper)new CbPlanDO(), new Object[]{id});
        return list.isEmpty() ? null : (CbPlanDO)list.get(0);
    }

    @Override
    public List<CbPlanDO> queryAllCbPlans() {
        String sql = String.format("SELECT %s FROM %s ORDER BY %s DESC", String.join((CharSequence)",", CbPlanDO.SELECT_COLUMNS), "NR_CB_PLAN", "P_CREATE_TIME");
        return this.jdbcTemplate.query(sql, (RowMapper)new CbPlanDO());
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public List<CbPlanDO> queryCbPlans(int startRow, int endRow) {
        String sql = String.format("SELECT %s FROM %s", String.join((CharSequence)",", CbPlanDO.SELECT_COLUMNS), "NR_CB_PLAN");
        DataSource dataSource = this.jdbcTemplate.getDataSource();
        Assert.notNull((Object)dataSource, "dataSource must not be null.");
        try (Connection connection = dataSource.getConnection();){
            IDatabase iDatabase = DatabaseManager.getInstance().findDatabaseByConnection(connection);
            IPagingSQLBuilder sqlBuilder = iDatabase.createPagingSQLBuilder();
            sqlBuilder.setRawSQL(sql);
            OrderField orderField = new OrderField("P_CREATE_TIME");
            orderField.setOrderMode("DESC");
            sqlBuilder.getOrderFields().add(orderField);
            sql = sqlBuilder.buildSQL(startRow, endRow);
            List list = this.jdbcTemplate.query(sql, (RowMapper)new CbPlanDO());
            return list;
        }
        catch (Exception e) {
            throw new RuntimeException("\u67e5\u8be2\u5931\u8d25", e);
        }
    }

    @Override
    public int deleteCbPlanById(String id) {
        String sql = String.format("DELETE FROM %s WHERE %s = ?", "NR_CB_PLAN", "P_ID");
        return this.jdbcTemplate.update(sql, new Object[]{id});
    }

    @Override
    public int countPlan() {
        String sql = "SELECT COUNT(1) FROM NR_CB_PLAN";
        Integer count = (Integer)this.jdbcTemplate.queryForObject(sql, Integer.class);
        if (count == null) {
            count = 0;
        }
        return count;
    }
}

