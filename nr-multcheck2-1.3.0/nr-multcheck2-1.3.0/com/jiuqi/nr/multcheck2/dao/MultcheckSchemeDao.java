/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.nr.multcheck2.dao;

import com.jiuqi.nr.multcheck2.bean.MultcheckScheme;
import com.jiuqi.nr.multcheck2.common.OrgType;
import com.jiuqi.nr.multcheck2.common.SchemeType;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class MultcheckSchemeDao {
    private static final String TABLE_NAME = "NR_MULTCHECK_SCHEME";
    private static final String KEY = "MS_KEY";
    private static final String CODE = "MS_CODE";
    private static final String TITLE = "MS_TITLE";
    private static final String TASK = "MS_TASK";
    private static final String FORM_SCHEME = "MS_FORMSCHEME";
    private static final String ORG_TYPE = "MS_ORGTYPE";
    private static final String ORG_FML = "MS_ORGFML";
    private static final String TYPE = "MS_TYPE";
    private static final String ORDER = "MS_ORDER";
    private static final String UPDATE_TIME = "MS_UPDATE_TIME";
    private static final String DESC = "MS_DESC";
    private static final String LEVEL = "MS_LEVEL";
    private static final String ORG = "MS_ORG";
    private static final String REPORT_DIM = "MS_REPORT_DIM";
    private static final String MULTCHECK_SCHEME;
    private static final Function<ResultSet, MultcheckScheme> ENTITY_READER_MULTCHECK_SCHEME;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void add(MultcheckScheme ms) {
        String SQL_INSERT = String.format("INSERT INTO %s (%s) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", TABLE_NAME, MULTCHECK_SCHEME);
        this.jdbcTemplate.update(SQL_INSERT, new Object[]{ms.getKey(), ms.getCode(), ms.getTitle(), ms.getTask(), ms.getFormScheme(), ms.getOrgType().value(), ms.getOrgFml(), ms.getType().value(), ms.getOrder(), ms.getDesc(), new Timestamp(System.currentTimeMillis()), ms.getLevel(), ms.getOrg()});
    }

    public void modify(MultcheckScheme ms) {
        String SQL_UPDATE = String.format("UPDATE %s SET %s = ?, %s = ?, %s = ?, %s = ?, %s = ?, %s = ?, %s = ?, %s = ?, %s = ?, %s = ?, %s = ?, %s = ? WHERE %s = ? ", TABLE_NAME, CODE, TITLE, TASK, FORM_SCHEME, ORG_TYPE, ORG_FML, TYPE, ORDER, DESC, UPDATE_TIME, LEVEL, ORG, KEY);
        this.jdbcTemplate.update(SQL_UPDATE, new Object[]{ms.getCode(), ms.getTitle(), ms.getTask(), ms.getFormScheme(), ms.getOrgType().value(), ms.getOrgFml(), ms.getType().value(), ms.getOrder(), ms.getDesc(), new Timestamp(System.currentTimeMillis()), ms.getLevel(), ms.getOrg(), ms.getKey()});
    }

    public void deleteByKey(String key) {
        String SQL_DELETE = String.format("DELETE FROM %s WHERE %s = ?", TABLE_NAME, KEY);
        this.jdbcTemplate.update(SQL_DELETE, new Object[]{key});
    }

    public void deleteByFormScheme(String formScheme) {
        String SQL_DELETE = String.format("DELETE FROM %s WHERE %s = ?", TABLE_NAME, FORM_SCHEME);
        this.jdbcTemplate.update(SQL_DELETE, new Object[]{formScheme});
    }

    public MultcheckScheme getByKey(String key) {
        String SQL_QUERY = String.format("SELECT %s FROM %s WHERE %s = ?", MULTCHECK_SCHEME, TABLE_NAME, KEY);
        return (MultcheckScheme)this.jdbcTemplate.query(SQL_QUERY, rs -> {
            if (rs.next()) {
                return ENTITY_READER_MULTCHECK_SCHEME.apply(rs);
            }
            return null;
        }, new Object[]{key});
    }

    public List<MultcheckScheme> getByKeys(List<String> keys) {
        StringBuilder sb = new StringBuilder();
        for (String key : keys) {
            sb.append("'").append(key).append("'").append(",");
        }
        sb.deleteCharAt(sb.length() - 1);
        String SQL_QUERY = String.format("SELECT %s FROM %s WHERE %s in (%s) ORDER BY %s", MULTCHECK_SCHEME, TABLE_NAME, KEY, sb, ORDER);
        return this.jdbcTemplate.query(SQL_QUERY, (rs, row) -> ENTITY_READER_MULTCHECK_SCHEME.apply(rs));
    }

    public List<MultcheckScheme> getByFormScheme(String formScheme) {
        String SQL_QUERY = String.format("SELECT %s FROM %s WHERE %s = ? ORDER BY %s", MULTCHECK_SCHEME, TABLE_NAME, FORM_SCHEME, ORDER);
        return this.jdbcTemplate.query(SQL_QUERY, (rs, row) -> ENTITY_READER_MULTCHECK_SCHEME.apply(rs), new Object[]{formScheme});
    }

    public List<MultcheckScheme> getByFSAndOrg(String formScheme, String org) {
        String SQL_QUERY = String.format("SELECT %s FROM %s WHERE %s = ? AND %s = ? ORDER BY %s", MULTCHECK_SCHEME, TABLE_NAME, FORM_SCHEME, ORG, ORDER);
        return this.jdbcTemplate.query(SQL_QUERY, (rs, row) -> ENTITY_READER_MULTCHECK_SCHEME.apply(rs), new Object[]{formScheme, org});
    }

    public List<MultcheckScheme> getByFSAndCode(String formScheme, String code) {
        String SQL_QUERY = String.format("SELECT %s FROM %s WHERE %s = ? AND %s = ? ORDER BY %s", MULTCHECK_SCHEME, TABLE_NAME, FORM_SCHEME, CODE, ORDER);
        return this.jdbcTemplate.query(SQL_QUERY, (rs, row) -> ENTITY_READER_MULTCHECK_SCHEME.apply(rs), new Object[]{formScheme, code});
    }

    public Map<String, List<MultcheckScheme>> getByTask(String task) {
        String SQL_QUERY = String.format("SELECT %s FROM %s WHERE %s = ? ORDER BY %s", MULTCHECK_SCHEME, TABLE_NAME, TASK, ORDER);
        return this.jdbcTemplate.query(SQL_QUERY, (rs, row) -> ENTITY_READER_MULTCHECK_SCHEME.apply(rs), new Object[]{task}).stream().collect(Collectors.groupingBy(MultcheckScheme::getFormScheme));
    }

    public List<MultcheckScheme> fuzzySearchTitle(String fuzzyKey) {
        String SQL_QUERY = String.format("SELECT %s FROM %s WHERE %s LIKE ? ORDER BY %s DESC, %s", MULTCHECK_SCHEME, TABLE_NAME, TITLE, TYPE, ORDER);
        return this.jdbcTemplate.query(SQL_QUERY, (rs, row) -> ENTITY_READER_MULTCHECK_SCHEME.apply(rs), new Object[]{"%" + fuzzyKey + "%"});
    }

    public List<MultcheckScheme> fuzzySearchCode(String fuzzyKey) {
        String SQL_QUERY = String.format("SELECT %s FROM %s WHERE %s LIKE ? ORDER BY %s DESC, %s", MULTCHECK_SCHEME, TABLE_NAME, CODE, TYPE, ORDER);
        return this.jdbcTemplate.query(SQL_QUERY, (rs, row) -> ENTITY_READER_MULTCHECK_SCHEME.apply(rs), new Object[]{"%" + fuzzyKey.toUpperCase() + "%"});
    }

    public void move(MultcheckScheme source, MultcheckScheme target) {
        String SQL_UPDATE = String.format("UPDATE %s SET %s = ? WHERE %s = ? ", TABLE_NAME, ORDER, KEY);
        ArrayList<Object[]> args = new ArrayList<Object[]>();
        args.add(new Object[]{target.getOrder(), source.getKey()});
        args.add(new Object[]{source.getOrder(), target.getKey()});
        this.jdbcTemplate.batchUpdate(SQL_UPDATE, args);
    }

    public List<String> getAllTask() {
        String SQL_QUERY = String.format("SELECT %s FROM %s", TASK, TABLE_NAME);
        return this.jdbcTemplate.queryForList(SQL_QUERY, String.class);
    }

    public List<MultcheckScheme> getAllSchemes() {
        String SQL_QUERY = String.format("SELECT %s FROM %s ", MULTCHECK_SCHEME, TABLE_NAME);
        return this.jdbcTemplate.query(SQL_QUERY, (rs, row) -> ENTITY_READER_MULTCHECK_SCHEME.apply(rs));
    }

    public void batchModifyOrg(List<MultcheckScheme> schemes) {
        String SQL_UPDATE = String.format("UPDATE %s SET %s = ? WHERE %s = ? ", TABLE_NAME, ORG, KEY);
        ArrayList<Object[]> args = new ArrayList<Object[]>();
        for (MultcheckScheme scheme : schemes) {
            Object[] param = new Object[]{scheme.getOrg(), scheme.getKey()};
            args.add(param);
        }
        this.jdbcTemplate.batchUpdate(SQL_UPDATE, args);
    }

    public void saveReportDim(String key, String dim) {
        String SQL_UPDATE = String.format("UPDATE %s SET %s = ? WHERE %s = ?", TABLE_NAME, REPORT_DIM, KEY);
        this.jdbcTemplate.update(SQL_UPDATE, new Object[]{dim, key});
    }

    public String getReportDim(String key) {
        String SQL_QUERY = String.format("SELECT %s FROM %s WHERE %s = ?", REPORT_DIM, TABLE_NAME, KEY);
        return (String)this.jdbcTemplate.queryForObject(SQL_QUERY, String.class, new Object[]{key});
    }

    static {
        StringBuilder builder = new StringBuilder();
        MULTCHECK_SCHEME = builder.append(KEY).append(",").append(CODE).append(",").append(TITLE).append(",").append(TASK).append(",").append(FORM_SCHEME).append(",").append(ORG_TYPE).append(",").append(ORG_FML).append(",").append(TYPE).append(",").append(ORDER).append(",").append(DESC).append(",").append(UPDATE_TIME).append(",").append(LEVEL).append(",").append(ORG).toString();
        ENTITY_READER_MULTCHECK_SCHEME = rs -> {
            MultcheckScheme multcheckScheme = new MultcheckScheme();
            int index = 1;
            try {
                multcheckScheme.setKey(rs.getString(index++));
                multcheckScheme.setCode(rs.getString(index++));
                multcheckScheme.setTitle(rs.getString(index++));
                multcheckScheme.setTask(rs.getString(index++));
                multcheckScheme.setFormScheme(rs.getString(index++));
                multcheckScheme.setOrgType(OrgType.fromValue(rs.getInt(index++)));
                multcheckScheme.setOrgFml(rs.getString(index++));
                multcheckScheme.setType(SchemeType.fromValue(rs.getInt(index++)));
                multcheckScheme.setOrder(rs.getString(index++));
                multcheckScheme.setDesc(rs.getString(index++));
                multcheckScheme.setUpdateTime(rs.getTimestamp(index++));
                multcheckScheme.setLevel(rs.getString(index++));
                multcheckScheme.setOrg(rs.getString(index++));
            }
            catch (SQLException e) {
                throw new RuntimeException("read multcheckScheme error.", e);
            }
            return multcheckScheme;
        };
    }
}

