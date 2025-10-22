/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.nr.attachment.transfer.dao.impl;

import com.jiuqi.nr.attachment.transfer.dao.IWorkSpaceDao;
import com.jiuqi.nr.attachment.transfer.domain.WorkSpaceDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class WorkSpaceDaoImpl
implements IWorkSpaceDao {
    private static final String TABLE_NAME = "NR_ATTRIO_CONFIG";
    private static final String FIELD_THREAD = "AC_THREAD";
    private static final String FIELD_SPACE_SIZE = "AC_SPACE_SIZE";
    private static final String FIELD_FILE_PATH = "AC_FILE_PATH";
    private static final String FIELD_SERVE_ID = "AC_SERVE_ID";
    private static final String FIELD_TYPE = "AC_TYPE";
    private static final String FIELD_URL = "AC_URL";
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public int insert(WorkSpaceDO envConfigDO) {
        String sql = String.format("INSERT INTO %s(%s,%s,%s,%s,%s,%s) VALUES (?,?,?,?,?,?)", TABLE_NAME, FIELD_SERVE_ID, FIELD_TYPE, FIELD_THREAD, FIELD_SPACE_SIZE, FIELD_FILE_PATH, FIELD_URL);
        Object[] value = new Object[]{envConfigDO.getServerId(), envConfigDO.getType(), envConfigDO.getThread(), envConfigDO.getSpaceSize(), envConfigDO.getFilePath(), envConfigDO.getUrl()};
        return this.jdbcTemplate.update(sql, value);
    }

    @Override
    public WorkSpaceDO get(int type) {
        String sql = String.format("SELECT * FROM %s WHERE %s = ?", TABLE_NAME, FIELD_TYPE);
        Object[] arg = new Object[]{type};
        return (WorkSpaceDO)this.jdbcTemplate.query(sql, rs -> {
            WorkSpaceDO workSpaceDO = null;
            while (rs.next()) {
                workSpaceDO = new WorkSpaceDO();
                workSpaceDO.setServerId(rs.getString(FIELD_SERVE_ID));
                workSpaceDO.setThread(rs.getInt(FIELD_THREAD));
                workSpaceDO.setSpaceSize(rs.getInt(FIELD_SPACE_SIZE));
                workSpaceDO.setFilePath(rs.getString(FIELD_FILE_PATH));
                workSpaceDO.setType(rs.getInt(FIELD_TYPE));
                workSpaceDO.setUrl(rs.getString(FIELD_URL));
            }
            return workSpaceDO;
        }, arg);
    }

    @Override
    public int update(WorkSpaceDO envConfigDO) {
        String sql = String.format("UPDATE %s SET %s = ?, %s = ?, %s = ? , %s = ? WHERE %s = ?", TABLE_NAME, FIELD_THREAD, FIELD_SPACE_SIZE, FIELD_FILE_PATH, FIELD_URL, FIELD_TYPE);
        Object[] arg = new Object[]{envConfigDO.getThread(), envConfigDO.getSpaceSize(), envConfigDO.getFilePath(), envConfigDO.getUrl(), envConfigDO.getType()};
        return this.jdbcTemplate.update(sql, arg);
    }
}

