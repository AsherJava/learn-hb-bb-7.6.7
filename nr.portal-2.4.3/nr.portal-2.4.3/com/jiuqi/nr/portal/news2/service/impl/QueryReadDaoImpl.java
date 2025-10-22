/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.nr.portal.news2.service.impl;

import com.jiuqi.nr.portal.news2.service.IQueryReadDao;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class QueryReadDaoImpl
implements IQueryReadDao {
    public static final String TABLENAME = "PORTAL_READ_RELATION";
    public static final String ITEM_ID = "ITEM_ID";
    public static final String USER_ID = "USER_ID";
    public static final String INFO_ID = "INFO_ID";
    public static final String INFO_TYPE = "INFO_TYPE";
    public static final String ALL_FIELD = "ITEM_ID,USER_ID,INFO_ID,INFO_TYPE";
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<String> queryReadList(String userId, String type) {
        String sql = String.format("select INFO_ID from %s where %s=? and %s=?", TABLENAME, USER_ID, INFO_TYPE);
        Object[] args = new Object[]{userId, type};
        return this.jdbcTemplate.queryForList(sql.toUpperCase(), args, String.class);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public Boolean addReadItem(String userId, String infoId, String type) {
        Object[] args = new Object[]{UUID.randomUUID().toString(), userId, infoId, type};
        int result = 0;
        Class<QueryReadDaoImpl> clazz = QueryReadDaoImpl.class;
        synchronized (QueryReadDaoImpl.class) {
            Boolean exists = this.queryReadItem(userId, infoId, type);
            if (!exists.booleanValue()) {
                String sql = String.format("insert into %s (%s) values(?,?,?,?)", TABLENAME, ALL_FIELD);
                result = this.jdbcTemplate.update(sql.toUpperCase(), args);
            }
            // ** MonitorExit[var6_6] (shouldn't be in output)
            return result > 0;
        }
    }

    @Override
    public Boolean batchInsertReadItem(String userId, List<String> infoIds, String type) {
        ArrayList<Object[]> list = new ArrayList<Object[]>();
        for (String infoId : infoIds) {
            Object[] args = new Object[]{UUID.randomUUID().toString(), userId, infoId, type};
            list.add(args);
        }
        String sql = String.format("insert into %s (%s) values(?,?,?,?)", TABLENAME, ALL_FIELD);
        this.jdbcTemplate.batchUpdate(sql.toUpperCase(), list);
        return true;
    }

    @Override
    public Boolean queryReadItem(String userId, String infoId, String type) {
        Object[] args = new Object[]{userId, infoId, type};
        String sql = String.format("  SELECT count(*)  FROM %s  WHERE %s = ? AND %s = ? AND %s = ?", TABLENAME, USER_ID, INFO_ID, INFO_TYPE);
        Integer integer = (Integer)this.jdbcTemplate.queryForObject(sql, args, Integer.class);
        return integer > 0;
    }

    @Override
    public Boolean deleteItemByInfoId(String infoId) {
        String sql = String.format("delete from %s where %s=?", TABLENAME, INFO_ID);
        Object[] args = new Object[]{infoId};
        this.jdbcTemplate.update(sql.toUpperCase(), args);
        return true;
    }

    @Override
    public Boolean batchDeleteItemByInfoIds(List<String> infoIds) {
        for (String id : infoIds) {
            this.deleteItemByInfoId(id);
        }
        return true;
    }
}

