/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.database.DatabaseManager
 *  com.jiuqi.bi.database.IDatabase
 *  com.jiuqi.bi.database.paging.IPagingSQLBuilder
 *  com.jiuqi.bi.database.paging.OrderField
 *  org.springframework.jdbc.core.BatchPreparedStatementSetter
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.jdbc.core.RowMapper
 */
package com.jiuqi.nr.reminder.plan.dao.impl;

import com.jiuqi.bi.database.DatabaseManager;
import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.bi.database.paging.IPagingSQLBuilder;
import com.jiuqi.bi.database.paging.OrderField;
import com.jiuqi.nr.reminder.plan.dao.CbPlanLogDAO;
import com.jiuqi.nr.reminder.plan.dao.impl.CbPlanLogDO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

@Repository
public class CbPlanLogDODAOImpl
implements CbPlanLogDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CbPlanLogDODAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void insert(CbPlanLogDO execLogDO) {
        String sql = "INSERT INTO NR_CB_PLAN_LOG(LOG_ID,ID,P_UNIT,P_UNIT_CODE,P_FORM,P_FORM_KEY,P_DATA_TIME,RECIPIENT_ID,P_CHANNEL,P_SEND_TIME,SEND_STATUS,LOG_DETAIL_MESSAGE,P_ORDER) VALUES (?, ?, ?, ?, ?,?, ?, ?, ?, ?, ?, ?, ?)";
        this.jdbcTemplate.update(sql, new Object[]{execLogDO.getLogId(), execLogDO.getId(), execLogDO.getUnit(), execLogDO.getUnitCode(), execLogDO.getForm(), execLogDO.getFormKey(), execLogDO.getDataTime(), execLogDO.getRecipientId(), execLogDO.getChannel(), execLogDO.getSendTime(), execLogDO.getStatus(), execLogDO.getMessage(), execLogDO.getOrder()});
    }

    @Override
    public void batchInsert(final List<CbPlanLogDO> execLogDos) {
        if (CollectionUtils.isEmpty(execLogDos)) {
            return;
        }
        String sql = "INSERT INTO NR_CB_PLAN_LOG(LOG_ID,ID,P_UNIT,P_FORM,P_DATA_TIME,RECIPIENT_ID,P_CHANNEL,P_SEND_TIME,SEND_STATUS,LOG_DETAIL_MESSAGE,P_UNIT_CODE,P_FORM_KEY,P_ORDER) VALUES (?, ?, ?, ?, ?,?, ?, ?, ?, ?, ?, ?, ?)";
        this.jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter(){

            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setString(1, ((CbPlanLogDO)execLogDos.get(i)).getLogId());
                ps.setString(2, ((CbPlanLogDO)execLogDos.get(i)).getId());
                ps.setString(3, ((CbPlanLogDO)execLogDos.get(i)).getUnit());
                ps.setString(4, ((CbPlanLogDO)execLogDos.get(i)).getForm());
                ps.setString(5, ((CbPlanLogDO)execLogDos.get(i)).getDataTime());
                ps.setString(6, ((CbPlanLogDO)execLogDos.get(i)).getRecipientId());
                ps.setString(7, ((CbPlanLogDO)execLogDos.get(i)).getChannel());
                ps.setTimestamp(8, ((CbPlanLogDO)execLogDos.get(i)).getSendTime());
                ps.setInt(9, ((CbPlanLogDO)execLogDos.get(i)).getStatus());
                ps.setString(10, ((CbPlanLogDO)execLogDos.get(i)).getMessage());
                ps.setString(11, ((CbPlanLogDO)execLogDos.get(i)).getUnitCode());
                ps.setString(12, ((CbPlanLogDO)execLogDos.get(i)).getFormKey());
                ps.setString(13, ((CbPlanLogDO)execLogDos.get(i)).getOrder());
            }

            public int getBatchSize() {
                return execLogDos.size();
            }
        });
    }

    @Override
    public void deleteByLogId(String logId) {
        String sql = "DELETE FROM NR_CB_PLAN_LOG WHERE LOG_ID = ?";
        this.jdbcTemplate.update(sql, new Object[]{logId});
    }

    @Override
    public List<CbPlanLogDO> queryLogByLogId(String logId) {
        String sql = "SELECT LOG_ID,ID,P_UNIT,P_FORM,P_DATA_TIME,RECIPIENT_ID,P_CHANNEL,P_SEND_TIME,SEND_STATUS,LOG_DETAIL_MESSAGE,P_UNIT_CODE,P_FORM_KEY,P_ORDER FROM NR_CB_PLAN_LOG WHERE LOG_ID = ? ORDER BY P_ORDER";
        return this.jdbcTemplate.query(sql, (RowMapper)new CbPlanLogDO(), new Object[]{logId});
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public List<CbPlanLogDO> queryLogByLogId(String logId, int startRow, int endRow) {
        String sql = "SELECT LOG_ID,ID,P_UNIT,P_FORM,P_DATA_TIME,RECIPIENT_ID,P_CHANNEL,P_SEND_TIME,SEND_STATUS,LOG_DETAIL_MESSAGE,P_UNIT_CODE,P_FORM_KEY,P_ORDER FROM NR_CB_PLAN_LOG WHERE LOG_ID = ?";
        DataSource dataSource = this.jdbcTemplate.getDataSource();
        Assert.notNull((Object)dataSource, "dataSource must not be null.");
        try (Connection connection = dataSource.getConnection();){
            IDatabase iDatabase = DatabaseManager.getInstance().findDatabaseByConnection(connection);
            IPagingSQLBuilder sqlBuilder = iDatabase.createPagingSQLBuilder();
            sqlBuilder.setRawSQL(sql);
            OrderField orderField = new OrderField("P_ORDER");
            sqlBuilder.getOrderFields().add(orderField);
            sql = sqlBuilder.buildSQL(startRow, endRow);
            List list = this.jdbcTemplate.query(sql, (RowMapper)new CbPlanLogDO(), new Object[]{logId});
            return list;
        }
        catch (Exception e) {
            throw new RuntimeException("\u67e5\u8be2\u5931\u8d25", e);
        }
    }

    @Override
    public int count(String logId) {
        String sql = "SELECT COUNT(1) FROM NR_CB_PLAN_LOG WHERE LOG_ID = ?";
        Integer count = (Integer)this.jdbcTemplate.queryForObject(sql, Integer.class, new Object[]{logId});
        if (count == null) {
            count = 0;
        }
        return count;
    }
}

