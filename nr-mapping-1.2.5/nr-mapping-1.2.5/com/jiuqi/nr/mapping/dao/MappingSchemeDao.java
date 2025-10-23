/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.dao.DataAccessException
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.jdbc.support.lob.DefaultLobHandler
 *  org.springframework.jdbc.support.lob.LobHandler
 */
package com.jiuqi.nr.mapping.dao;

import com.jiuqi.nr.mapping.bean.MappingScheme;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.lob.DefaultLobHandler;
import org.springframework.jdbc.support.lob.LobHandler;
import org.springframework.stereotype.Repository;

@Repository
public class MappingSchemeDao {
    private static final String TABLENAME = "NR_MAPPING_SCHEME";
    private static final String KEY = "MS_KEY";
    private static final String TITLE = "MS_TITLE";
    private static final String CODE = "MS_CODE";
    private static final String TASK = "MS_TASK";
    private static final String FORMSCHEME = "MS_FORMSCHEME";
    private static final String UPDATETIME = "MS_UPDATE_TIME";
    private static final String CREATETIME = "MS_CREATE_TIME";
    private static final String CONFIG = "MS_CONFIG";
    private static final String MAPPING_SCHEME;
    private static final Function<ResultSet, MappingScheme> ENTITY_READER_MAPPING_SCHEME;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public String add(MappingScheme mappingScheme) {
        String SQL_INSERT = String.format("INSERT INTO %s (%s) VALUES (?, ?, ?, ?, ?, ?, ?)", TABLENAME, MAPPING_SCHEME);
        this.jdbcTemplate.update(SQL_INSERT, new Object[]{mappingScheme.getKey(), mappingScheme.getTitle(), mappingScheme.getCode(), mappingScheme.getTask(), mappingScheme.getFormScheme(), new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis())});
        return mappingScheme.getKey();
    }

    public void rename(String key, String title) {
        String SQL_UPDATE = String.format("UPDATE %s SET %s = ?, %s = ? WHERE %s = ?", TABLENAME, TITLE, UPDATETIME, KEY);
        this.jdbcTemplate.update(SQL_UPDATE, new Object[]{title, new Timestamp(System.currentTimeMillis()), key});
    }

    public void updateTime(String key) {
        String SQL_UPDATE = String.format("UPDATE %s SET %s = ? WHERE %s = ?", TABLENAME, UPDATETIME, KEY);
        this.jdbcTemplate.update(SQL_UPDATE, new Object[]{new Timestamp(System.currentTimeMillis()), key});
    }

    public void delete(String key) {
        String SQL_DELETE = String.format("DELETE FROM %s WHERE %s = ?", TABLENAME, KEY);
        this.jdbcTemplate.update(SQL_DELETE, new Object[]{key});
    }

    public MappingScheme findByKey(String key) {
        String SQL_QUERY = String.format("SELECT %s FROM %s WHERE %s = ?", MAPPING_SCHEME, TABLENAME, KEY);
        return (MappingScheme)this.jdbcTemplate.query(SQL_QUERY, rs -> {
            if (rs.next()) {
                return ENTITY_READER_MAPPING_SCHEME.apply(rs);
            }
            return null;
        }, new Object[]{key});
    }

    public MappingScheme findByCode(String code) {
        String SQL_QUERY = String.format("SELECT %s FROM %s WHERE %s = ?", MAPPING_SCHEME, TABLENAME, CODE);
        return (MappingScheme)this.jdbcTemplate.query(SQL_QUERY, rs -> {
            if (rs.next()) {
                return ENTITY_READER_MAPPING_SCHEME.apply(rs);
            }
            return null;
        }, new Object[]{code});
    }

    public List<MappingScheme> findByTask(String task) {
        String SQL_QUERY = String.format("SELECT %s FROM %s WHERE %s = ? ORDER BY %s", MAPPING_SCHEME, TABLENAME, TASK, TITLE);
        return this.jdbcTemplate.query(SQL_QUERY, (rs, row) -> ENTITY_READER_MAPPING_SCHEME.apply(rs), new Object[]{task});
    }

    public List<MappingScheme> findByTaskForm(String task, String formscheme) {
        String SQL_QUERY = String.format("SELECT %s FROM %s WHERE %s = ? AND %s = ? ORDER BY %s", MAPPING_SCHEME, TABLENAME, TASK, FORMSCHEME, TITLE);
        return this.jdbcTemplate.query(SQL_QUERY, (rs, row) -> ENTITY_READER_MAPPING_SCHEME.apply(rs), new Object[]{task, formscheme});
    }

    public List<MappingScheme> findByTaskFormJIO(String task, String formscheme) {
        String SQL_QUERY = String.format("SELECT %s FROM %s s JOIN %s j ON s.%s = j.%s WHERE s.%s = ? AND s.%s = ?", MAPPING_SCHEME, TABLENAME, "NR_MAPPING_JIO_CONFIG", KEY, "JC_MAPPINGSCHEME", TASK, FORMSCHEME);
        return this.jdbcTemplate.query(SQL_QUERY, (rs, row) -> ENTITY_READER_MAPPING_SCHEME.apply(rs), new Object[]{task, formscheme});
    }

    public List<MappingScheme> findAll() {
        String SQL_QUERY = String.format("SELECT %s FROM %s", MAPPING_SCHEME, TABLENAME);
        return this.jdbcTemplate.query(SQL_QUERY, (rs, row) -> ENTITY_READER_MAPPING_SCHEME.apply(rs));
    }

    public List<MappingScheme> fuzzySearchTitle(String fuzzyKey) {
        String SQL_QUERY = String.format("SELECT %s FROM %s WHERE %s LIKE ?", MAPPING_SCHEME, TABLENAME, TITLE);
        return this.jdbcTemplate.query(SQL_QUERY, (rs, row) -> ENTITY_READER_MAPPING_SCHEME.apply(rs), new Object[]{"%" + fuzzyKey + "%"});
    }

    public List<MappingScheme> fuzzySearchCode(String fuzzyKey) {
        String SQL_QUERY = String.format("SELECT %s FROM %s WHERE %s LIKE ?", MAPPING_SCHEME, TABLENAME, CODE);
        return this.jdbcTemplate.query(SQL_QUERY, (rs, row) -> ENTITY_READER_MAPPING_SCHEME.apply(rs), new Object[]{"%" + fuzzyKey.toUpperCase() + "%"});
    }

    public void batchDelete(List<String> keys) {
        String SQL_BATCH_DELETE = String.format("DELETE FROM %s WHERE %s = ?", TABLENAME, KEY);
        ArrayList<Object[]> args = new ArrayList<Object[]>();
        for (String key : keys) {
            Object[] param = new Object[]{key};
            args.add(param);
        }
        this.jdbcTemplate.batchUpdate(SQL_BATCH_DELETE, args);
    }

    public String getConfig(String key) {
        String SQL_QUERY = String.format("SELECT %s FROM %s WHERE %s = ?", CONFIG, TABLENAME, KEY);
        DefaultLobHandler lobHandler = new DefaultLobHandler();
        return (String)this.jdbcTemplate.query(SQL_QUERY, arg_0 -> MappingSchemeDao.lambda$getConfig$9((LobHandler)lobHandler, arg_0), new Object[]{key});
    }

    public void updateConfig(String key, String config) {
        String SQL_UPDATE = String.format("UPDATE %s SET %s = ?, %s = ? WHERE %s = ?", TABLENAME, CONFIG, UPDATETIME, KEY);
        this.jdbcTemplate.update(SQL_UPDATE, new Object[]{config, new Timestamp(System.currentTimeMillis()), key});
    }

    private static /* synthetic */ String lambda$getConfig$9(LobHandler lobHandler, ResultSet rs) throws SQLException, DataAccessException {
        if (rs.next()) {
            return lobHandler.getClobAsString(rs, CONFIG);
        }
        return null;
    }

    static {
        StringBuilder builder = new StringBuilder();
        MAPPING_SCHEME = builder.append(KEY).append(",").append(TITLE).append(",").append(CODE).append(",").append(TASK).append(",").append(FORMSCHEME).append(",").append(UPDATETIME).append(",").append(CREATETIME).toString();
        ENTITY_READER_MAPPING_SCHEME = rs -> {
            MappingScheme mappingScheme = new MappingScheme();
            int index = 1;
            try {
                mappingScheme.setKey(rs.getString(index++));
                mappingScheme.setTitle(rs.getString(index++));
                mappingScheme.setCode(rs.getString(index++));
                mappingScheme.setTask(rs.getString(index++));
                mappingScheme.setFormScheme(rs.getString(index++));
                mappingScheme.setUpdateTime(rs.getTimestamp(index++));
                mappingScheme.setCreateTime(rs.getTimestamp(index++));
            }
            catch (SQLException e) {
                throw new RuntimeException("read MappingScheme error.", e);
            }
            return mappingScheme;
        };
    }
}

