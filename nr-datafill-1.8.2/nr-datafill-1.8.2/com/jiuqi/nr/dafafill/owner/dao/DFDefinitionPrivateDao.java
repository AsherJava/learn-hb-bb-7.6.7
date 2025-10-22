/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.nr.dafafill.owner.dao;

import com.jiuqi.nr.dafafill.model.enums.ModelType;
import com.jiuqi.nr.dafafill.owner.entity.DataFillDefinitionPrivate;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.function.Function;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class DFDefinitionPrivateDao {
    private static final String TABLE_NAME = "NR_DATAFILL_DEFINITION_PRI";
    private static final String ID = "DDP_ID";
    private static final String TITLE = "DDP_TITLE";
    private static final String GROUP_ID = "DGP_ID";
    private static final String SOURCE_TYPE = "DDP_SOURCETYPE";
    private static final String TASK = "DDP_TASK";
    private static final String MODIFY_TIME = "DDP_MODIFY_TIME";
    private static final String CREATE_USER = "DDP_CREATE_USER";
    private static final String QUERY_F_DATAFILL_DEFINITION_PRI;
    private static final Function<ResultSet, DataFillDefinitionPrivate> ENTITY_READER_DATAFILL_DEFINITION_PRI;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void add(DataFillDefinitionPrivate data) {
        String SQL_ADD = String.format("INSERT INTO %s (%s, %s, %s, %s, %s, %s, %s) VALUES ( ?, ?, ?, ?, ?, ?, ?)", TABLE_NAME, ID, TITLE, GROUP_ID, SOURCE_TYPE, TASK, MODIFY_TIME, CREATE_USER);
        this.jdbcTemplate.update(SQL_ADD, new Object[]{data.getKey(), data.getTitle(), data.getGroupId(), data.getSourceType().value(), data.getTask(), new Timestamp(System.currentTimeMillis()), data.getCreateUser()});
    }

    public List<DataFillDefinitionPrivate> getByParentAndUser(String parentId, String user) {
        String SQL_QUERY_BY_PARENT = String.format("SELECT %s FROM %s WHERE %s = ? AND %s = ?", QUERY_F_DATAFILL_DEFINITION_PRI, TABLE_NAME, GROUP_ID, CREATE_USER);
        return this.jdbcTemplate.query(SQL_QUERY_BY_PARENT, (rs, rowNum) -> ENTITY_READER_DATAFILL_DEFINITION_PRI.apply(rs), new Object[]{parentId, user});
    }

    public List<DataFillDefinitionPrivate> getAll() {
        String SQL_QUERY_BY_PARENT = String.format("SELECT %s FROM %s ", QUERY_F_DATAFILL_DEFINITION_PRI, TABLE_NAME);
        return this.jdbcTemplate.query(SQL_QUERY_BY_PARENT, (rs, rowNum) -> ENTITY_READER_DATAFILL_DEFINITION_PRI.apply(rs));
    }

    public DataFillDefinitionPrivate getByKey(String key) {
        String QUERY_BY_ID = String.format("SELECT %s FROM %s WHERE %s = ?", QUERY_F_DATAFILL_DEFINITION_PRI, TABLE_NAME, ID);
        return (DataFillDefinitionPrivate)this.jdbcTemplate.query(QUERY_BY_ID, rs -> {
            if (rs.next()) {
                return ENTITY_READER_DATAFILL_DEFINITION_PRI.apply(rs);
            }
            return null;
        }, new Object[]{key});
    }

    public void deleteByKey(String key) {
        String SQL_DELETE = String.format("DELETE FROM %s WHERE %s = ?", TABLE_NAME, ID);
        this.jdbcTemplate.update(SQL_DELETE, new Object[]{key});
    }

    public void modify(DataFillDefinitionPrivate data) {
        String SQL_MODIFY = String.format("UPDATE %s SET %s=?, %s=?, %s=?, %s=? WHERE %s = ?", TABLE_NAME, TITLE, TASK, GROUP_ID, MODIFY_TIME, ID);
        this.jdbcTemplate.update(SQL_MODIFY, new Object[]{data.getTitle(), data.getTask(), data.getGroupId(), data.getModifyTime(), data.getKey()});
    }

    static {
        StringBuilder builder = new StringBuilder();
        QUERY_F_DATAFILL_DEFINITION_PRI = builder.append(ID).append(",").append(TITLE).append(",").append(GROUP_ID).append(",").append(SOURCE_TYPE).append(",").append(TASK).append(",").append(MODIFY_TIME).append(",").append(CREATE_USER).toString();
        ENTITY_READER_DATAFILL_DEFINITION_PRI = rs -> {
            DataFillDefinitionPrivate data = new DataFillDefinitionPrivate();
            int index = 1;
            try {
                data.setKey(rs.getString(index++));
                data.setTitle(rs.getString(index++));
                data.setGroupId(rs.getString(index++));
                data.setSourceType(ModelType.valueOf(rs.getInt(index++)));
                data.setTask(rs.getString(index++));
                data.setModifyTime(rs.getTimestamp(index++));
                data.setCreateUser(rs.getString(index));
            }
            catch (SQLException e) {
                throw new RuntimeException("read DataFillDefinitionPrivate error.", e);
            }
            return data;
        };
    }
}

