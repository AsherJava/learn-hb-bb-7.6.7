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
package com.jiuqi.nr.mapping.dao;

import com.jiuqi.nr.mapping.bean.JIOConfig;
import java.nio.charset.StandardCharsets;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

@Repository
public class JIOConfigDao {
    protected static final String TABLENAME = "NR_MAPPING_JIO_CONFIG";
    private static final String KEY = "JC_KEY";
    protected static final String MAPPINGSCHEME = "JC_MAPPINGSCHEME";
    private static final String CONFIG = "JC_JIOCONFIG";
    private static final String FILE = "JC_JIOFILE";
    private static final String CONTENT = "JC_JIOCONTENT";
    private static final String JIO_CONFIG;
    private static final Function<ResultSet, JIOConfig> ENTITY_READER_JIO_CONFIG;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void addFile(byte[] file, String msKey, String key) {
        String fileStr = Base64.getEncoder().encodeToString(file);
        String SQL_INSERT = String.format("INSERT INTO %s (%s, %s, %s) VALUES (?, ?, ?)", TABLENAME, FILE, MAPPINGSCHEME, KEY);
        this.jdbcTemplate.update(SQL_INSERT, new Object[]{fileStr, msKey, key});
    }

    public void updateFile(byte[] file, String msKey) {
        String fileStr = Base64.getEncoder().encodeToString(file);
        String SQL_INSERT = String.format("UPDATE %s SET %s = ? WHERE %s = ?", TABLENAME, FILE, MAPPINGSCHEME);
        this.jdbcTemplate.update(SQL_INSERT, new Object[]{fileStr, msKey});
    }

    public void addConfig(byte[] config, String msKey, String key) {
        String configStr = new String(config, StandardCharsets.UTF_8);
        String SQL_INSERT = String.format("INSERT INTO %s (%s, %s, %s) VALUES (?, ?, ?)", TABLENAME, CONFIG, MAPPINGSCHEME, KEY);
        this.jdbcTemplate.update(SQL_INSERT, new Object[]{configStr, msKey, key});
    }

    public void updateConfig(byte[] config, String msKey) {
        String configStr = new String(config, StandardCharsets.UTF_8);
        String SQL_INSERT = String.format("UPDATE %s SET %s = ? WHERE %s = ?", TABLENAME, CONFIG, MAPPINGSCHEME);
        this.jdbcTemplate.update(SQL_INSERT, new Object[]{configStr, msKey});
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

    public byte[] findFileByMS(String msKey) {
        String SQL_QUERY = String.format("SELECT %s FROM %s WHERE %s = ?", FILE, TABLENAME, MAPPINGSCHEME);
        String file = (String)this.jdbcTemplate.query(SQL_QUERY, (rs, row) -> rs.getString(FILE), new Object[]{msKey}).get(0);
        if (StringUtils.hasText(file)) {
            return Base64.getDecoder().decode(file);
        }
        return null;
    }

    public byte[] findConfigByMS(String msKey) {
        String SQL_QUERY = String.format("SELECT %s FROM %s WHERE %s = ?", CONFIG, TABLENAME, MAPPINGSCHEME);
        String config = (String)this.jdbcTemplate.query(SQL_QUERY, rs -> {
            if (rs.next()) {
                return rs.getString(CONFIG);
            }
            return null;
        }, new Object[]{msKey});
        if (StringUtils.hasText(config)) {
            return config.getBytes(StandardCharsets.UTF_8);
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
        if (jioConfig.getFile() != null && jioConfig.getFile().length > 0) {
            file = Base64.getEncoder().encodeToString(jioConfig.getFile());
        }
        if (jioConfig.getContent() != null && jioConfig.getContent().length > 0) {
            content = new String(jioConfig.getContent(), StandardCharsets.UTF_8);
        }
        this.jdbcTemplate.update(SQL_INSERT, new Object[]{jioConfig.getKey(), jioConfig.getMsKey(), config, file, content});
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
        if (jioConfig.getFile() != null && jioConfig.getFile().length > 0) {
            file = Base64.getEncoder().encodeToString(jioConfig.getFile());
        }
        if (jioConfig.getContent() != null && jioConfig.getContent().length > 0) {
            content = new String(jioConfig.getContent(), StandardCharsets.UTF_8);
        }
        String SQL_UPDATE = String.format("UPDATE %s SET %s = ?, %s = ?, %s = ? WHERE %s = ?", TABLENAME, CONFIG, FILE, CONTENT, MAPPINGSCHEME);
        this.jdbcTemplate.update(SQL_UPDATE, new Object[]{config, file, content, mskey});
    }

    static {
        StringBuilder builder = new StringBuilder();
        JIO_CONFIG = builder.append(KEY).append(",").append(MAPPINGSCHEME).append(",").append(CONFIG).append(",").append(FILE).append(",").append(CONTENT).toString();
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
                    config.setFile(Base64.getDecoder().decode(file));
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

