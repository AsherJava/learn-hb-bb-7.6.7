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
package com.jiuqi.nr.zbquery.dao;

import com.jiuqi.nr.zbquery.bean.ZBQueryInfo;
import java.nio.charset.StandardCharsets;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
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
public class ZBQueryInfoDao {
    private static final String TABLE_NAME = "NR_ZBQUERY_INFO";
    private static final String ID = "QI_ID";
    private static final String TITLE = "QI_TITLE";
    private static final String DESCRIPTION = "QI_DESCRIPTION";
    private static final String DATA = "QI_DATA";
    private static final String MODIFYTIME = "QI_MODIFYTIME";
    private static final String LEVEL = "QI_LEVEL";
    private static final String QUERY_F_ZBQUERY_INFO;
    private static final Function<ResultSet, ZBQueryInfo> ENTITY_READER_ZBQUERY_INFO;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void addQueryInfo(ZBQueryInfo info) {
        String SQL_ADD = String.format("INSERT INTO %s (%s, %s, %s, %s, %s) VALUES ( ?, ?, ?, ?, ?)", TABLE_NAME, ID, TITLE, DESCRIPTION, MODIFYTIME, LEVEL);
        this.jdbcTemplate.update(SQL_ADD, new Object[]{info.getId(), info.getTitle(), info.getDescription(), new Timestamp(System.currentTimeMillis()), info.getLevel()});
    }

    public void modifyQueryInfo(ZBQueryInfo info) {
        String SQL_MODIFY = String.format("UPDATE %s SET %s=?, %s=?, %s=?, %s=? WHERE %s = ? ", TABLE_NAME, TITLE, DESCRIPTION, MODIFYTIME, LEVEL, ID);
        this.jdbcTemplate.update(SQL_MODIFY, new Object[]{info.getTitle(), info.getDescription(), new Timestamp(System.currentTimeMillis()), info.getLevel(), info.getId()});
    }

    public void deleteQueryInfoByIds(List<String> id) {
        String SQL_DELETE = String.format("DELETE FROM %s WHERE %s = ?", TABLE_NAME, ID);
        ArrayList<Object[]> args = new ArrayList<Object[]>();
        for (String s : id) {
            Object[] param = new Object[]{s};
            args.add(param);
        }
        this.jdbcTemplate.batchUpdate(SQL_DELETE, args);
    }

    public void deleteQueryInfoById(String id) {
        String SQL_DELETE = String.format("DELETE FROM %s WHERE %s = ?", TABLE_NAME, ID);
        this.jdbcTemplate.update(SQL_DELETE, new Object[]{id});
    }

    public void deleteAll() {
        String SQL_DELETE = String.format("DELETE FROM %s", TABLE_NAME);
        this.jdbcTemplate.update(SQL_DELETE);
    }

    public ZBQueryInfo getQueryInfoById(String id) {
        String SQL_QUERY_BY_ID = String.format("SELECT %s FROM %s WHERE %s = ?", QUERY_F_ZBQUERY_INFO, TABLE_NAME, ID);
        return (ZBQueryInfo)this.jdbcTemplate.query(SQL_QUERY_BY_ID, rs -> {
            if (rs.next()) {
                return ENTITY_READER_ZBQUERY_INFO.apply(rs);
            }
            return null;
        }, new Object[]{id});
    }

    public List<ZBQueryInfo> getQueryInfoByGroup(String groupId) {
        String SQL_QUERY_BY_GROUPID = String.format("SELECT %s FROM %s WHERE QG_ID = ? ORDER BY %s", QUERY_F_ZBQUERY_INFO, TABLE_NAME, TITLE);
        return this.jdbcTemplate.query(SQL_QUERY_BY_GROUPID, (rs, row) -> ENTITY_READER_ZBQUERY_INFO.apply(rs), new Object[]{groupId});
    }

    public List<ZBQueryInfo> getQueryInfoByIds(List<String> queryInfoIds) {
        String SQL_QUERY_BY_GROUPID = String.format("SELECT %s FROM %s WHERE %s in (:ids)", QUERY_F_ZBQUERY_INFO, TABLE_NAME, ID);
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("ids", queryInfoIds);
        NamedParameterJdbcTemplate givenParamJdbcTemp = new NamedParameterJdbcTemplate((JdbcOperations)this.jdbcTemplate);
        return givenParamJdbcTemp.query(SQL_QUERY_BY_GROUPID, (SqlParameterSource)parameters, (rs, row) -> ENTITY_READER_ZBQUERY_INFO.apply(rs));
    }

    public List<String> getQueryInfoByGroups(List<String> groupIds) {
        String SQL_QUERY_BY_GROUPID = String.format("SELECT %s FROM %s WHERE QG_ID in (:ids)", QUERY_F_ZBQUERY_INFO, TABLE_NAME);
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("ids", groupIds);
        NamedParameterJdbcTemplate givenParamJdbcTemp = new NamedParameterJdbcTemplate((JdbcOperations)this.jdbcTemplate);
        List zbQueryInfoList = givenParamJdbcTemp.query(SQL_QUERY_BY_GROUPID, (SqlParameterSource)parameters, (rs, row) -> ENTITY_READER_ZBQUERY_INFO.apply(rs));
        return zbQueryInfoList.stream().map(ZBQueryInfo::getId).collect(Collectors.toList());
    }

    public void saveQueryInfoData(byte[] data, String id) {
        String SQL_MODIFY = String.format("UPDATE %s SET %s=?, %s=? WHERE %s = ? ", TABLE_NAME, MODIFYTIME, DATA, ID);
        String dataStr = new String(data, StandardCharsets.UTF_8);
        this.jdbcTemplate.update(SQL_MODIFY, new Object[]{new Timestamp(System.currentTimeMillis()), dataStr, id});
    }

    public byte[] getQueryInfoDataById(String id) {
        String SQL_QUERY_DATA_BY_ID = String.format("SELECT %s FROM %s WHERE %s = ?", DATA, TABLE_NAME, ID);
        List qiData = this.jdbcTemplate.query(SQL_QUERY_DATA_BY_ID, (rs, row) -> rs.getString(DATA), new Object[]{id});
        if (qiData.isEmpty()) {
            return null;
        }
        String qi_data = (String)qiData.get(0);
        if (StringUtils.hasText(qi_data)) {
            return qi_data.getBytes(StandardCharsets.UTF_8);
        }
        return null;
    }

    public List<ZBQueryInfo> getAllQueryInfo() {
        String SQL_QUERY_BY_GROUPID = String.format("SELECT %s FROM %s ORDER BY %s", QUERY_F_ZBQUERY_INFO, TABLE_NAME, TITLE);
        return this.jdbcTemplate.query(SQL_QUERY_BY_GROUPID, (rs, row) -> ENTITY_READER_ZBQUERY_INFO.apply(rs));
    }

    static {
        StringBuilder builder = new StringBuilder();
        QUERY_F_ZBQUERY_INFO = builder.append(ID).append(",").append(TITLE).append(",").append(DESCRIPTION).append(",").append(LEVEL).toString();
        ENTITY_READER_ZBQUERY_INFO = rs -> {
            ZBQueryInfo zbQueryInfo = new ZBQueryInfo();
            int index = 1;
            try {
                zbQueryInfo.setId(rs.getString(index));
                zbQueryInfo.setTitle(rs.getString(++index));
                zbQueryInfo.setDescription(rs.getString(++index));
                zbQueryInfo.setLevel(rs.getString(++index));
            }
            catch (SQLException e) {
                throw new RuntimeException("read ZBQueryGroup group error.", e);
            }
            return zbQueryInfo;
        };
    }
}

