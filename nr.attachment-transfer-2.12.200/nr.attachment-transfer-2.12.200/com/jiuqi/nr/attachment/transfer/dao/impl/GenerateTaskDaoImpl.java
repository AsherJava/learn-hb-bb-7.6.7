/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.nr.attachment.transfer.dao.impl;

import com.jiuqi.nr.attachment.transfer.dao.IGenerateTaskDao;
import com.jiuqi.nr.attachment.transfer.domain.GenerateTaskDO;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class GenerateTaskDaoImpl
implements IGenerateTaskDao {
    private static final String TABLE_NAME = "NR_ATTRIO_EXPORT_INFO";
    public static final String FIELD_KEY = "AI_KEY";
    public static final String FIELD_SERVE_ID = "AI_SERVE_ID";
    private static final String FIELD_PARAM = "AI_PARAM";
    public static final String FIELD_STATUS = "AI_STATUS";
    public static final String FIELD_TOTAL = "AI_TOTAL";
    private static final String FIELD_CREATE_TIME = "AI_CREATETIME";
    private static final String FIELD_UPDATE_TIME = "AI_UPDATETIME";
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public GenerateTaskDO query(String serverId) {
        String sql = String.format("SELECT * FROM %s WHERE %s = ?", TABLE_NAME, FIELD_SERVE_ID);
        Object[] arg = new Object[]{serverId};
        return (GenerateTaskDO)this.jdbcTemplate.query(sql, rs -> {
            GenerateTaskDO generateTaskDO = null;
            while (rs.next()) {
                generateTaskDO = new GenerateTaskDO();
                generateTaskDO.setKey(rs.getString(FIELD_KEY));
                generateTaskDO.setParamConfig(rs.getString(FIELD_PARAM));
                generateTaskDO.setCreateTime(rs.getTimestamp(FIELD_CREATE_TIME));
                generateTaskDO.setUpdateTime(rs.getTimestamp(FIELD_UPDATE_TIME));
                generateTaskDO.setServerId(serverId);
                generateTaskDO.setStatus(rs.getInt(FIELD_STATUS));
                generateTaskDO.setTotal(rs.getLong(FIELD_TOTAL));
            }
            return generateTaskDO;
        }, arg);
    }

    @Override
    public int insert(GenerateTaskDO generateTaskDO) {
        String sql = String.format("INSERT INTO %s ( %s, %s, %s, %s, %s, %s, %s) VALUES(?, ?, ?, ?, ?, ?, ?)", TABLE_NAME, FIELD_KEY, FIELD_SERVE_ID, FIELD_STATUS, FIELD_TOTAL, FIELD_PARAM, FIELD_CREATE_TIME, FIELD_UPDATE_TIME);
        Date time = new Date();
        Object[] arg = new Object[]{generateTaskDO.getKey(), generateTaskDO.getServerId(), generateTaskDO.getStatus(), generateTaskDO.getTotal(), generateTaskDO.getParamConfig(), time, time};
        return this.jdbcTemplate.update(sql, arg);
    }

    @Override
    public int update(GenerateTaskDO generateTaskDO) {
        String sql = String.format("UPDATE %s SET %s = ?, %s = ?, %s = ? WHERE %s = ?", TABLE_NAME, FIELD_PARAM, FIELD_STATUS, FIELD_UPDATE_TIME, FIELD_KEY);
        Object[] arg = new Object[]{generateTaskDO.getParamConfig(), generateTaskDO.getStatus(), new Date(), generateTaskDO.getKey()};
        return this.jdbcTemplate.update(sql, arg);
    }

    @Override
    public int delete(String serverId) {
        String sql = String.format("DELETE FROM %s where %s = ?", TABLE_NAME, FIELD_SERVE_ID);
        Object[] arg = new Object[]{serverId};
        return this.jdbcTemplate.update(sql, arg);
    }

    @Override
    public int updateStatus(String key, int status) {
        String sql = String.format("UPDATE %s SET %s = ? WHERE %s = ?", TABLE_NAME, FIELD_STATUS, FIELD_KEY);
        Object[] arg = new Object[]{status, key};
        return this.jdbcTemplate.update(sql, arg);
    }
}

