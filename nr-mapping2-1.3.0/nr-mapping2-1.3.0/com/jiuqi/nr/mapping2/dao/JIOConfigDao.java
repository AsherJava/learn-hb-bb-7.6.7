/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.dao.DataAccessException
 *  org.springframework.jdbc.core.JdbcOperations
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.jdbc.core.namedparam.MapSqlParameterSource
 *  org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
 *  org.springframework.jdbc.core.namedparam.SqlParameterSource
 *  org.springframework.jdbc.support.lob.DefaultLobHandler
 *  org.springframework.jdbc.support.lob.LobHandler
 */
package com.jiuqi.nr.mapping2.dao;

import com.jiuqi.nr.mapping2.bean.JIOConfig;
import java.nio.charset.StandardCharsets;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.lob.DefaultLobHandler;
import org.springframework.jdbc.support.lob.LobHandler;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

@Repository
public class JIOConfigDao {
    protected static final String TABLENAME = "NVWA_MAPPING_NR_JIO";
    private static final String KEY = "MJ_KEY";
    protected static final String MAPPINGSCHEME = "MJ_MAPPINGSCHEME";
    private static final String FILE = "MJ_JIOFILE";
    private static final String CONFIG = "MJ_JIOCONFIG";
    private static final String CONTENT = "MJ_JIOCONTENT";
    private static final String MAPPINGCONFIG = "MJ_MAPPINGCONFIG";
    private static final String JIO_CONFIG;
    private static final Function<ResultSet, JIOConfig> ENTITY_READER_JIO_CONFIG;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void addFile(String file, String msKey, String key) {
        String SQL_INSERT = String.format("INSERT INTO %s (%s, %s, %s) VALUES (?, ?, ?)", TABLENAME, FILE, MAPPINGSCHEME, KEY);
        this.jdbcTemplate.update(SQL_INSERT, new Object[]{file, msKey, key});
    }

    public void updateFile(String file, String msKey) {
        String SQL_INSERT = String.format("UPDATE %s SET %s = ? WHERE %s = ?", TABLENAME, FILE, MAPPINGSCHEME);
        this.jdbcTemplate.update(SQL_INSERT, new Object[]{file, msKey});
    }

    public void addConfig(byte[] config, String msKey, String key) {
        String configStr = new String(config, StandardCharsets.UTF_8);
        String SQL_INSERT = String.format("INSERT INTO %s (%s, %s, %s) VALUES (?, ?, ?)", TABLENAME, CONFIG, MAPPINGSCHEME, KEY);
        this.jdbcTemplate.update(SQL_INSERT, new Object[]{configStr, msKey, key});
    }

    public void updateConfig(String msKey, String config) {
        String SQL_INSERT = String.format("UPDATE %s SET %s = ? WHERE %s = ?", TABLENAME, CONFIG, MAPPINGSCHEME);
        this.jdbcTemplate.update(SQL_INSERT, new Object[]{config, msKey});
    }

    public void addContent(byte[] content, String msKey, String key) {
        String contentStr = new String(content, StandardCharsets.UTF_8);
        String SQL_INSERT = String.format("INSERT INTO %s (%s, %s, %s) VALUES (?, ?, ?)", TABLENAME, CONTENT, MAPPINGSCHEME, KEY);
        this.jdbcTemplate.update(SQL_INSERT, new Object[]{contentStr, msKey, key});
    }

    public void updateContent(byte[] content, String msKey) {
        String contentStr = new String(content, StandardCharsets.UTF_8);
        String SQL_INSERT = String.format("UPDATE %s SET %s = ? WHERE %s = ?", TABLENAME, CONTENT, MAPPINGSCHEME);
        this.jdbcTemplate.update(SQL_INSERT, new Object[]{contentStr, msKey});
    }

    public String findFileByMS(String msKey) {
        String file;
        String SQL_QUERY = String.format("SELECT %s FROM %s WHERE %s = ?", FILE, TABLENAME, MAPPINGSCHEME);
        List result = this.jdbcTemplate.query(SQL_QUERY, (rs, row) -> rs.getString(FILE), new Object[]{msKey});
        if (result.size() > 0 && StringUtils.hasText(file = (String)result.get(0))) {
            return file;
        }
        return null;
    }

    public String findConfigByMS(String msKey) {
        String SQL_QUERY = String.format("SELECT %s FROM %s WHERE %s = ?", CONFIG, TABLENAME, MAPPINGSCHEME);
        String config = (String)this.jdbcTemplate.query(SQL_QUERY, rs -> {
            if (rs.next()) {
                return rs.getString(CONFIG);
            }
            return null;
        }, new Object[]{msKey});
        if (StringUtils.hasText(config)) {
            return new String(config.getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8);
        }
        return null;
    }

    public byte[] findContentByMS(String msKey) {
        String SQL_QUERY = String.format("SELECT %s FROM %s WHERE %s = ?", CONTENT, TABLENAME, MAPPINGSCHEME);
        String content = (String)this.jdbcTemplate.query(SQL_QUERY, rs -> {
            if (rs.next()) {
                return rs.getString(CONTENT);
            }
            return null;
        }, new Object[]{msKey});
        if (StringUtils.hasText(content)) {
            return content.getBytes(StandardCharsets.UTF_8);
        }
        return null;
    }

    public List<byte[]> batchFindContentByMS(List<String> msKeys) {
        String SQL_BATCH_QUERY = String.format("SELECT %s FROM %s WHERE %s in (:keys)", CONTENT, TABLENAME, MAPPINGSCHEME);
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("keys", msKeys);
        NamedParameterJdbcTemplate givenParamJdbcTemp = new NamedParameterJdbcTemplate((JdbcOperations)this.jdbcTemplate);
        return givenParamJdbcTemp.query(SQL_BATCH_QUERY, (SqlParameterSource)parameters, (rs, row) -> rs.getString(1)).stream().map(content -> {
            if (StringUtils.hasText(content)) {
                return content.getBytes(StandardCharsets.UTF_8);
            }
            return null;
        }).collect(Collectors.toList());
    }

    public String add(JIOConfig jioConfig) {
        String SQL_INSERT = String.format("INSERT INTO %s (%s) VALUES (?, ?, ?, ?, ?)", TABLENAME, JIO_CONFIG);
        String config = null;
        String file = null;
        String content = null;
        if (jioConfig.getConfig() != null && jioConfig.getConfig().length > 0) {
            config = new String(jioConfig.getConfig(), StandardCharsets.UTF_8);
        }
        if (StringUtils.hasText(jioConfig.getFile())) {
            file = jioConfig.getFile();
        }
        if (jioConfig.getContent() != null && jioConfig.getContent().length > 0) {
            content = new String(jioConfig.getContent(), StandardCharsets.UTF_8);
        }
        this.jdbcTemplate.update(SQL_INSERT, new Object[]{jioConfig.getKey(), jioConfig.getMsKey(), file, config, content});
        return jioConfig.getKey();
    }

    public void deleteByMS(String msKey) {
        String SQL_DELETE = String.format("DELETE FROM %s WHERE %s = ?", TABLENAME, MAPPINGSCHEME);
        this.jdbcTemplate.update(SQL_DELETE, new Object[]{msKey});
    }

    public void batchDeleteByMS(List<String> keys) {
        String SQL_BATCH_DELETE = String.format("DELETE FROM %s WHERE %s = ?", TABLENAME, MAPPINGSCHEME);
        ArrayList<Object[]> args = new ArrayList<Object[]>();
        for (String key : keys) {
            Object[] param = new Object[]{key};
            args.add(param);
        }
        this.jdbcTemplate.batchUpdate(SQL_BATCH_DELETE, args);
    }

    public JIOConfig findByMS(String mskey) {
        String SQL_QUERY = String.format("SELECT %s FROM %s WHERE %s = ?", JIO_CONFIG, TABLENAME, MAPPINGSCHEME);
        return (JIOConfig)this.jdbcTemplate.query(SQL_QUERY, rs -> {
            if (rs.next()) {
                return ENTITY_READER_JIO_CONFIG.apply(rs);
            }
            return null;
        }, new Object[]{mskey});
    }

    public boolean isExist(String msKey) {
        String SQL_QUERY = String.format("SELECT %s FROM %s WHERE %s = ?", KEY, TABLENAME, MAPPINGSCHEME);
        return Boolean.TRUE.equals(this.jdbcTemplate.query(SQL_QUERY, ResultSet::next, new Object[]{msKey}));
    }

    public void updateByMS(String mskey, JIOConfig jioConfig) {
        String config = null;
        String file = null;
        String content = null;
        if (jioConfig.getConfig() != null && jioConfig.getConfig().length > 0) {
            config = new String(jioConfig.getConfig(), StandardCharsets.UTF_8);
        }
        if (StringUtils.hasText(jioConfig.getFile())) {
            file = jioConfig.getFile();
        }
        if (jioConfig.getContent() != null && jioConfig.getContent().length > 0) {
            content = new String(jioConfig.getContent(), StandardCharsets.UTF_8);
        }
        String SQL_UPDATE = String.format("UPDATE %s SET %s = ?, %s = ?, %s = ? WHERE %s = ?", TABLENAME, CONFIG, FILE, CONTENT, MAPPINGSCHEME);
        this.jdbcTemplate.update(SQL_UPDATE, new Object[]{config, file, content, mskey});
    }

    public String getMappingConfig(String key) {
        String SQL_QUERY = String.format("SELECT %s FROM %s WHERE %s = ?", MAPPINGCONFIG, TABLENAME, MAPPINGSCHEME);
        DefaultLobHandler lobHandler = new DefaultLobHandler();
        return (String)this.jdbcTemplate.query(SQL_QUERY, arg_0 -> JIOConfigDao.lambda$getMappingConfig$7((LobHandler)lobHandler, arg_0), new Object[]{key});
    }

    public void updateMappingConfig(String key, String mappingConfig) {
        String SQL_UPDATE = String.format("UPDATE %s SET %s = ? WHERE %s = ?", TABLENAME, MAPPINGCONFIG, MAPPINGSCHEME);
        this.jdbcTemplate.update(SQL_UPDATE, new Object[]{mappingConfig, key});
    }

    public void addMappingConfig(String key, String msKey, String file, String mappingConfig) {
        String SQL_INSERT = String.format("INSERT INTO %s (%s, %s, %s, %s) VALUES (?, ?, ?, ?)", TABLENAME, KEY, MAPPINGSCHEME, FILE, MAPPINGCONFIG);
        this.jdbcTemplate.update(SQL_INSERT, new Object[]{key, msKey, file, mappingConfig});
    }

    private static /* synthetic */ String lambda$getMappingConfig$7(LobHandler lobHandler, ResultSet rs) throws SQLException, DataAccessException {
        if (rs.next()) {
            return lobHandler.getClobAsString(rs, MAPPINGCONFIG);
        }
        return null;
    }

    static {
        StringBuilder builder = new StringBuilder();
        JIO_CONFIG = builder.append(KEY).append(",").append(MAPPINGSCHEME).append(",").append(FILE).append(",").append(CONFIG).append(",").append(CONTENT).toString();
        ENTITY_READER_JIO_CONFIG = rs -> {
            JIOConfig config = new JIOConfig();
            int index = 1;
            try {
                String content;
                String file;
                config.setKey(rs.getString(index++));
                config.setMsKey(rs.getString(index++));
                String configString = rs.getString(index++);
                if (StringUtils.hasText(configString)) {
                    config.setConfig(configString.getBytes(StandardCharsets.UTF_8));
                }
                if (StringUtils.hasText(file = rs.getString(index++))) {
                    config.setFile(file);
                }
                if (StringUtils.hasText(content = rs.getString(index))) {
                    config.setContent(content.getBytes(StandardCharsets.UTF_8));
                }
            }
            catch (Exception e) {
                throw new RuntimeException("read NR_JIO_CONFIG error.", e);
            }
            return config;
        };
    }
}

