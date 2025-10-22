/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.nr.dafafill.owner.dao;

import com.jiuqi.nr.dafafill.owner.entity.DataFillGroupPrivate;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class DFGroupPrivateDao {
    private static final String TABLE_NAME = "NR_DATAFILL_GROUP_PRI";
    private static final String ID = "DGP_ID";
    private static final String TITLE = "DGP_TITLE";
    private static final String PARENT_ID = "DGP_PARENTID";
    private static final String MODIFY_TIME = "DGP_MODIFY_TIME";
    private static final String CREATE_USER = "DGP_CREATE_USER";
    private static final String QUERY_F_DATAFILL_GROUP_PRI;
    private static final Function<ResultSet, DataFillGroupPrivate> ENTITY_READER_DATAFILL_GROUP_PRI;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void add(DataFillGroupPrivate data) {
        String SQL_ADD = String.format("INSERT INTO %s (%s, %s, %s, %s, %s) VALUES ( ?, ?, ?, ?, ?)", TABLE_NAME, ID, TITLE, PARENT_ID, MODIFY_TIME, CREATE_USER);
        this.jdbcTemplate.update(SQL_ADD, new Object[]{data.getKey(), data.getTitle(), data.getParentId(), new Timestamp(System.currentTimeMillis()), data.getCreateUser()});
    }

    public void modify(String key, String title) {
        String SQL_MODIFY = String.format("UPDATE %s SET %s=?, %s=? WHERE %s = ?", TABLE_NAME, TITLE, MODIFY_TIME, ID);
        this.jdbcTemplate.update(SQL_MODIFY, new Object[]{title, new Timestamp(System.currentTimeMillis()), key});
    }

    public DataFillGroupPrivate getByKey(String key) {
        String QUERY_BY_ID = String.format("SELECT %s FROM %s WHERE %s = ?", QUERY_F_DATAFILL_GROUP_PRI, TABLE_NAME, ID);
        return (DataFillGroupPrivate)this.jdbcTemplate.query(QUERY_BY_ID, rs -> {
            if (rs.next()) {
                return ENTITY_READER_DATAFILL_GROUP_PRI.apply(rs);
            }
            return null;
        }, new Object[]{key});
    }

    public List<DataFillGroupPrivate> getByParentKeyAndUser(String parentId, String user) {
        String QUERY_BY_PARENT_ID = String.format("SELECT %s FROM %s WHERE %s = ? AND %s = ?", QUERY_F_DATAFILL_GROUP_PRI, TABLE_NAME, PARENT_ID, CREATE_USER);
        return (List)this.jdbcTemplate.query(QUERY_BY_PARENT_ID, rs -> {
            ArrayList<DataFillGroupPrivate> list = new ArrayList<DataFillGroupPrivate>();
            while (rs.next()) {
                list.add(ENTITY_READER_DATAFILL_GROUP_PRI.apply(rs));
            }
            return list;
        }, new Object[]{parentId, user});
    }

    public void deleteByKey(String key) {
        String SQL_DELETE = String.format("DELETE FROM %s WHERE %s = ?", TABLE_NAME, ID);
        this.jdbcTemplate.update(SQL_DELETE, new Object[]{key});
    }

    static {
        StringBuilder builder = new StringBuilder();
        QUERY_F_DATAFILL_GROUP_PRI = builder.append(ID).append(",").append(TITLE).append(",").append(PARENT_ID).append(",").append(MODIFY_TIME).append(",").append(CREATE_USER).toString();
        ENTITY_READER_DATAFILL_GROUP_PRI = rs -> {
            DataFillGroupPrivate data = new DataFillGroupPrivate();
            int index = 1;
            try {
                data.setKey(rs.getString(index++));
                data.setTitle(rs.getString(index++));
                data.setParentId(rs.getString(index++));
                data.setModifyTime(rs.getTimestamp(index++));
                data.setCreateUser(rs.getString(index));
            }
            catch (SQLException e) {
                throw new RuntimeException("read DataFillGroupPrivate error.", e);
            }
            return data;
        };
    }
}

