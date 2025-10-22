/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.nr.attachment.transfer.dao.impl;

import com.jiuqi.nr.attachment.transfer.dao.IImportTaskDao;
import com.jiuqi.nr.attachment.transfer.domain.ImportTaskDO;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ImportTaskDaoImpl
implements IImportTaskDao {
    private static final String TABLE_NAME = "NR_ATTRIO_IMPORT_INFO";
    private static final String FIELD_KEY = "AI_KEY";
    private static final String FIELD_SERVE_ID = "AI_SERVE_ID";
    private static final String FIELD_PARAM = "AI_PARAM";
    private static final String FIELD_STATUS = "AI_STATUS";
    private static final String FIELD_TOTAL = "AI_TOTAL";
    private static final String FIELD_CREATE_TIME = "AI_CREATETIME";
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public ImportTaskDO query(String key) {
        String sql = String.format("SELECT * FROM %s WHERE %s = ?", TABLE_NAME, FIELD_KEY);
        Object[] arg = new Object[]{key};
        return (ImportTaskDO)this.jdbcTemplate.query(sql, arg, rs -> {
            ImportTaskDO importTaskDO = null;
            while (rs.next()) {
                importTaskDO = new ImportTaskDO();
                importTaskDO.setKey(rs.getString(FIELD_KEY));
                importTaskDO.setParamConfig(rs.getString(FIELD_PARAM));
                importTaskDO.setCreateTime(rs.getTimestamp(FIELD_CREATE_TIME));
                importTaskDO.setServerId(rs.getString(FIELD_SERVE_ID));
                importTaskDO.setStatus(rs.getInt(FIELD_STATUS));
                importTaskDO.setTotal(rs.getLong(FIELD_TOTAL));
            }
            return importTaskDO;
        });
    }

    @Override
    public ImportTaskDO queryByServe(String serverId) {
        String sql = String.format("SELECT * FROM %s WHERE %s = ?", TABLE_NAME, FIELD_SERVE_ID);
        Object[] arg = new Object[]{serverId};
        return (ImportTaskDO)this.jdbcTemplate.query(sql, arg, rs -> {
            ImportTaskDO importTaskDO = null;
            while (rs.next()) {
                importTaskDO = new ImportTaskDO();
                importTaskDO.setKey(rs.getString(FIELD_KEY));
                importTaskDO.setParamConfig(rs.getString(FIELD_PARAM));
                importTaskDO.setCreateTime(rs.getTimestamp(FIELD_CREATE_TIME));
                importTaskDO.setServerId(rs.getString(FIELD_SERVE_ID));
                importTaskDO.setStatus(rs.getInt(FIELD_STATUS));
                importTaskDO.setTotal(rs.getLong(FIELD_TOTAL));
            }
            return importTaskDO;
        });
    }

    @Override
    public int insert(ImportTaskDO importTaskDO) {
        String sql = String.format("INSERT INTO %s(%s, %s, %s, %s, %s, %s) VALUES(?, ?, ?, ?, ?, ?)", TABLE_NAME, FIELD_KEY, FIELD_SERVE_ID, FIELD_STATUS, FIELD_TOTAL, FIELD_PARAM, FIELD_CREATE_TIME);
        Date time = new Date();
        Object[] arg = new Object[]{importTaskDO.getKey(), importTaskDO.getServerId(), importTaskDO.getStatus(), importTaskDO.getTotal(), importTaskDO.getParamConfig(), time};
        return this.jdbcTemplate.update(sql, arg);
    }

    @Override
    public int update(ImportTaskDO importTaskDO) {
        String sql = String.format("UPDATE %s SET  %s = ?, %s = ? WHERE %s = ?", TABLE_NAME, FIELD_PARAM, FIELD_STATUS, FIELD_KEY);
        Object[] arg = new Object[]{importTaskDO.getParamConfig(), importTaskDO.getStatus(), importTaskDO.getKey()};
        return this.jdbcTemplate.update(sql, arg);
    }

    @Override
    public int updateStatus(String key, int status) {
        String sql = String.format("UPDATE %s SET %s = ? WHERE %s = ?", TABLE_NAME, FIELD_STATUS, FIELD_KEY);
        Object[] arg = new Object[]{status, key};
        return this.jdbcTemplate.update(sql, arg);
    }

    @Override
    public int deleteByServer(String serverId) {
        String sql = String.format("DELETE FROM %s WHERE %s = ?", TABLE_NAME, FIELD_SERVE_ID);
        Object[] arg = new Object[]{serverId};
        return this.jdbcTemplate.update(sql, arg);
    }
}

