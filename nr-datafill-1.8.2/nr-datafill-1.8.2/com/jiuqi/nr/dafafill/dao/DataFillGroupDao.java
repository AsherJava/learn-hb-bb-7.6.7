/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.JdbcOperations
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.jdbc.core.namedparam.MapSqlParameterSource
 *  org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
 *  org.springframework.jdbc.core.namedparam.SqlParameterSource
 */
package com.jiuqi.nr.dafafill.dao;

import com.jiuqi.nr.dafafill.entity.DataFillGroup;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

@Repository
public class DataFillGroupDao {
    private static final String TABLE_NAME = "NR_DATAFILL_GROUP";
    private static final String ID = "DFG_ID";
    private static final String TITLE = "DFG_TITLE";
    private static final String PARENTID = "DFG_PARENTID";
    private static final String DESCRIPTION = "DFG_DESCRIPTION";
    private static final String CREATE_USER = "DFG_CREATE_USER";
    private static final String CREATE_TIME = "DFG_CREATE_TIME";
    private static final String MODIFY_USER = "DFG_MODIFY_USER";
    private static final String MODIFY_TIME = "DFG_MODIFY_TIME";
    private static final String QUERY_F_DATAFILL_GROUP;
    private static final Function<ResultSet, DataFillGroup> ENTITY_READER_DATAFILL_GROUP;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public String add(DataFillGroup group) {
        String SQL_ADD = String.format("INSERT INTO %s (%s) VALUES ( ?, ?, ?, ?, ?, ?)", TABLE_NAME, QUERY_F_DATAFILL_GROUP);
        this.jdbcTemplate.update(SQL_ADD, new Object[]{group.getId(), group.getTitle(), group.getParentId(), group.getDescription(), group.getCreateTime(), group.getModifyTime()});
        return group.getId();
    }

    public String modify(DataFillGroup group) {
        String SQL_UPDATE = String.format("UPDATE %s SET %s = ?, %s = ?, %s = ?, %s = ? WHERE %s = ? ", TABLE_NAME, TITLE, PARENTID, DESCRIPTION, MODIFY_TIME, ID);
        this.jdbcTemplate.update(SQL_UPDATE, new Object[]{group.getTitle(), group.getParentId(), group.getDescription(), group.getModifyTime(), group.getId()});
        return group.getId();
    }

    public void deleteById(String groupId) {
        String SQL_DELETE = String.format("DELETE FROM %s WHERE %s = ?", TABLE_NAME, ID);
        this.jdbcTemplate.update(SQL_DELETE, new Object[]{groupId});
    }

    public DataFillGroup findById(String id) {
        String SQL_QUERY = String.format("SELECT %s FROM %s WHERE %s = ?", QUERY_F_DATAFILL_GROUP, TABLE_NAME, ID);
        return (DataFillGroup)this.jdbcTemplate.query(SQL_QUERY, rs -> {
            if (rs.next()) {
                return ENTITY_READER_DATAFILL_GROUP.apply(rs);
            }
            return null;
        }, new Object[]{id});
    }

    public List<DataFillGroup> findByParentId(String parentId) {
        String SQL_QUERY = String.format("SELECT %s FROM %s WHERE %s = ? order by %s", QUERY_F_DATAFILL_GROUP, TABLE_NAME, PARENTID, TITLE);
        return this.jdbcTemplate.query(SQL_QUERY, (rs, row) -> ENTITY_READER_DATAFILL_GROUP.apply(rs), new Object[]{parentId});
    }

    public List<DataFillGroup> fuzzySearch(String fuzzyKey) {
        String SQL_QUERY = String.format("SELECT %s FROM %s WHERE %s like ? order by %s", QUERY_F_DATAFILL_GROUP, TABLE_NAME, TITLE, TITLE);
        return this.jdbcTemplate.query(SQL_QUERY, (rs, row) -> ENTITY_READER_DATAFILL_GROUP.apply(rs), new Object[]{"%" + fuzzyKey + "%"});
    }

    public List<DataFillGroup> query() {
        String SQL_QUERY = String.format("SELECT %s FROM %s", QUERY_F_DATAFILL_GROUP, TABLE_NAME);
        return this.jdbcTemplate.query(SQL_QUERY, (rs, row) -> ENTITY_READER_DATAFILL_GROUP.apply(rs));
    }

    public void batchDelete(List<String> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return;
        }
        String SQL_DELETE_ = String.format("DELETE FROM %s WHERE %s = ?", TABLE_NAME, ID);
        ArrayList<Object[]> args = new ArrayList<Object[]>();
        for (String id : ids) {
            Object[] param = new Object[]{id};
            args.add(param);
        }
        this.jdbcTemplate.batchUpdate(SQL_DELETE_, args);
    }

    public List<String> getAllParentId() {
        String SQL_QUERY_PARENDID = String.format("SELECT %s FROM %s", PARENTID, TABLE_NAME);
        return this.jdbcTemplate.queryForList(SQL_QUERY_PARENDID, String.class);
    }

    public List<String> batchFindByParentIds(List<String> ids) {
        String SQL_BATCH_QUERY_PARENTID = String.format("SELECT %s FROM %s WHERE %s in (:ids)", ID, TABLE_NAME, PARENTID);
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("ids", ids);
        NamedParameterJdbcTemplate givenParamJdbcTemp = new NamedParameterJdbcTemplate((JdbcOperations)this.jdbcTemplate);
        return givenParamJdbcTemp.query(SQL_BATCH_QUERY_PARENTID, (SqlParameterSource)parameters, (rs, row) -> rs.getString(1));
    }

    public void batchModifyParentId(List<String> ids, String parentId) {
        String SQL_UPDATE = String.format("UPDATE %s SET %s = ?, %s = ? WHERE %s = ? ", TABLE_NAME, PARENTID, MODIFY_TIME, ID);
        Timestamp modifyTime = new Timestamp(System.currentTimeMillis());
        ArrayList<Object[]> args = new ArrayList<Object[]>();
        for (String id : ids) {
            Object[] param = new Object[]{parentId, modifyTime, id};
            args.add(param);
        }
        this.jdbcTemplate.batchUpdate(SQL_UPDATE, args);
    }

    static {
        StringBuilder builder = new StringBuilder();
        QUERY_F_DATAFILL_GROUP = builder.append(ID).append(",").append(TITLE).append(",").append(PARENTID).append(",").append(DESCRIPTION).append(",").append(CREATE_TIME).append(",").append(MODIFY_TIME).toString();
        ENTITY_READER_DATAFILL_GROUP = rs -> {
            DataFillGroup group = new DataFillGroup();
            int index = 1;
            try {
                group.setId(rs.getString(index++));
                group.setTitle(rs.getString(index++));
                group.setParentId(rs.getString(index++));
                group.setDescription(rs.getString(index++));
                group.setCreateTime(rs.getTimestamp(index++));
                group.setModifyTime(rs.getTimestamp(index++));
            }
            catch (SQLException e) {
                throw new RuntimeException("read DataFillGroup error.", e);
            }
            return group;
        };
    }
}

