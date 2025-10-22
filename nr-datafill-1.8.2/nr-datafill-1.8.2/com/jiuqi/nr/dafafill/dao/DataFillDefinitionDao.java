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

import com.jiuqi.nr.dafafill.entity.DataFillDefinition;
import com.jiuqi.nr.dafafill.model.enums.ModelType;
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
public class DataFillDefinitionDao {
    private static final String TABLE_NAME = "NR_DATAFILL_DEFINITION";
    private static final String ID = "DFD_ID";
    private static final String TITLE = "DFD_TITLE";
    private static final String CODE = "DFD_CODE";
    private static final String SOURCETYPE = "DFD_SOURCETYPE";
    private static final String PARENTID = "DFG_ID";
    private static final String TASK_KEY = "DFD_TASK_KEY";
    private static final String DESCRIPTION = "DFD_DESCRIPTION";
    private static final String CREATE_USER = "DFD_CREATE_USER";
    private static final String CREATE_TIME = "DFD_CREATE_TIME";
    private static final String MODIFY_USER = "DFD_MODIFY_USER";
    private static final String MODIFY_TIME = "DFD_MODIFY_TIME";
    private static final String QUERY_F_DATAFILL_DEFINITION;
    private static final Function<ResultSet, DataFillDefinition> ENTITY_READER_DATAFILL_DEFINITION;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void add(DataFillDefinition definition) {
        String SQL_ADD = String.format("INSERT INTO %s (%s) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?)", TABLE_NAME, QUERY_F_DATAFILL_DEFINITION);
        this.jdbcTemplate.update(SQL_ADD, new Object[]{definition.getId(), definition.getTitle(), definition.getCode(), definition.getSourceType().value(), definition.getParentId(), definition.getTaskKey(), definition.getDescription(), definition.getCreateTime(), definition.getModifyTime()});
    }

    public String modify(DataFillDefinition definition) {
        String SQL_UPDATE = String.format("UPDATE %s SET %s = ?, %s = ?, %s = ?, %s = ?, %s = ?, %s = ?, %s = ? WHERE %s = ? ", TABLE_NAME, TITLE, CODE, SOURCETYPE, PARENTID, TASK_KEY, DESCRIPTION, MODIFY_TIME, ID);
        this.jdbcTemplate.update(SQL_UPDATE, new Object[]{definition.getTitle(), definition.getCode(), definition.getSourceType().value(), definition.getParentId(), definition.getTaskKey(), definition.getDescription(), definition.getModifyTime(), definition.getId()});
        return definition.getId();
    }

    public void deleteById(String definitionId) {
        String SQL_DELETE = String.format("DELETE FROM %s WHERE %s = ?", TABLE_NAME, ID);
        this.jdbcTemplate.update(SQL_DELETE, new Object[]{definitionId});
    }

    public List<DataFillDefinition> findByParentId(String parentId) {
        String SQL_QUERY = String.format("SELECT %s FROM %s WHERE %s = ? order by %s", QUERY_F_DATAFILL_DEFINITION, TABLE_NAME, PARENTID, TITLE);
        return this.jdbcTemplate.query(SQL_QUERY, (rs, row) -> ENTITY_READER_DATAFILL_DEFINITION.apply(rs), new Object[]{parentId});
    }

    public DataFillDefinition findByCode(String code) {
        String SQL_QUERY = String.format("SELECT %s FROM %s WHERE %s = ?", QUERY_F_DATAFILL_DEFINITION, TABLE_NAME, CODE);
        return (DataFillDefinition)this.jdbcTemplate.query(SQL_QUERY, rs -> {
            if (rs.next()) {
                return ENTITY_READER_DATAFILL_DEFINITION.apply(rs);
            }
            return null;
        }, new Object[]{code});
    }

    public DataFillDefinition findById(String id) {
        String SQL_QUERY = String.format("SELECT %s FROM %s WHERE %s = ?", QUERY_F_DATAFILL_DEFINITION, TABLE_NAME, ID);
        return (DataFillDefinition)this.jdbcTemplate.query(SQL_QUERY, rs -> {
            if (rs.next()) {
                return ENTITY_READER_DATAFILL_DEFINITION.apply(rs);
            }
            return null;
        }, new Object[]{id});
    }

    public List<DataFillDefinition> fuzzyTitle(String fuzzyKey) {
        String SQL_QUERY = String.format("SELECT %s FROM %s WHERE %s like ? order by %s", QUERY_F_DATAFILL_DEFINITION, TABLE_NAME, TITLE, TITLE);
        return this.jdbcTemplate.query(SQL_QUERY, (rs, row) -> ENTITY_READER_DATAFILL_DEFINITION.apply(rs), new Object[]{"%" + fuzzyKey + "%"});
    }

    public List<DataFillDefinition> fuzzyCode(String fuzzyKey) {
        String SQL_QUERY = String.format("SELECT %s FROM %s WHERE %s like ? order by %s", QUERY_F_DATAFILL_DEFINITION, TABLE_NAME, CODE, TITLE);
        return this.jdbcTemplate.query(SQL_QUERY, (rs, row) -> ENTITY_READER_DATAFILL_DEFINITION.apply(rs), new Object[]{"%" + fuzzyKey + "%"});
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

    public List<String> batchFindByParentIds(List<String> ids) {
        String SQL_BATCH_QUERY_PARENTID = String.format("SELECT %s FROM %s WHERE %s in (:ids)", ID, TABLE_NAME, PARENTID);
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("ids", ids);
        NamedParameterJdbcTemplate givenParamJdbcTemp = new NamedParameterJdbcTemplate((JdbcOperations)this.jdbcTemplate);
        return givenParamJdbcTemp.query(SQL_BATCH_QUERY_PARENTID, (SqlParameterSource)parameters, (rs, row) -> rs.getString(1));
    }

    public List<DataFillDefinition> findAll() {
        String SQL_QUERY = String.format("SELECT %s FROM %s order by %s", QUERY_F_DATAFILL_DEFINITION, TABLE_NAME, TITLE);
        return this.jdbcTemplate.query(SQL_QUERY, (rs, row) -> ENTITY_READER_DATAFILL_DEFINITION.apply(rs));
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
        QUERY_F_DATAFILL_DEFINITION = builder.append(ID).append(",").append(TITLE).append(",").append(CODE).append(",").append(SOURCETYPE).append(",").append(PARENTID).append(",").append(TASK_KEY).append(",").append(DESCRIPTION).append(",").append(CREATE_TIME).append(",").append(MODIFY_TIME).toString();
        ENTITY_READER_DATAFILL_DEFINITION = rs -> {
            DataFillDefinition definition = new DataFillDefinition();
            int index = 1;
            try {
                definition.setId(rs.getString(index++));
                definition.setTitle(rs.getString(index++));
                definition.setCode(rs.getString(index++));
                definition.setSourceType(ModelType.valueOf(rs.getInt(index++)));
                definition.setParentId(rs.getString(index++));
                definition.setTaskKey(rs.getString(index++));
                definition.setDescription(rs.getString(index++));
                definition.setCreateTime(rs.getTimestamp(index++));
                definition.setModifyTime(rs.getTimestamp(index++));
            }
            catch (SQLException e) {
                throw new RuntimeException("read DataFillDefinition error.", e);
            }
            return definition;
        };
    }
}

