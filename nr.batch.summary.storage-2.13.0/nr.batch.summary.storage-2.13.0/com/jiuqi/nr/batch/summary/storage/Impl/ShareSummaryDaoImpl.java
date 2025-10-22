/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Resource
 *  org.springframework.jdbc.core.JdbcOperations
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.jdbc.core.namedparam.MapSqlParameterSource
 *  org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
 *  org.springframework.jdbc.core.namedparam.SqlParameterSource
 */
package com.jiuqi.nr.batch.summary.storage.Impl;

import com.jiuqi.nr.batch.summary.storage.NamedParameterSqlBuilder;
import com.jiuqi.nr.batch.summary.storage.ShareSummaryDao;
import com.jiuqi.nr.batch.summary.storage.entity.ShareSummaryGroup;
import com.jiuqi.nr.batch.summary.storage.entity.ShareSummaryScheme;
import com.jiuqi.nr.batch.summary.storage.entity.impl.ShareSummaryGroupDefine;
import com.jiuqi.nr.batch.summary.storage.entity.impl.ShareSummarySchemeDefine;
import com.jiuqi.nr.batch.summary.storage.utils.BatchSummaryUtils;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import javax.annotation.Resource;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

@Repository
public class ShareSummaryDaoImpl
implements ShareSummaryDao {
    private static final String BSS_TASK = "BSS_TASK";
    private static final String TABLE_NAME = "NR_BS_SHARE";
    private static final String[] columns = new String[]{"BSS_SCHEME", "BSS_FROM_USER", "BSS_TO_USER", "BSS_SHARE_TIME", "BSS_TASK"};
    private static final String[] groupColumns = new String[]{"BSS_FROM_USER", "BSS_TASK"};
    private static final String SCHEME_TABLE_NAME = "NR_BS_SCHEME";
    private static final String BS_CODE = "BS_KEY";
    private static final String[] searchColumns = new String[]{"BS_TITLE", "BSS_SCHEME", "BSS_FROM_USER", "BSS_TO_USER", "BSS_SHARE_TIME", "BSS_TASK"};
    @Resource
    private JdbcTemplate jdbcTemplate;

    @Override
    public int findScheme(String schemeKey, String fromUser, String toUser) {
        NamedParameterSqlBuilder sqlBuilder = new NamedParameterSqlBuilder(TABLE_NAME);
        sqlBuilder.selectSQL(columns).andWhere(columns[0], columns[1], columns[2]);
        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue(columns[0], (Object)schemeKey);
        source.addValue(columns[1], (Object)fromUser);
        source.addValue(columns[2], (Object)toUser);
        return this.executeQueryHalf(sqlBuilder.toString(), source).size();
    }

    @Override
    public int findScheme(String schemeKey) {
        NamedParameterSqlBuilder sqlBuilder = new NamedParameterSqlBuilder(TABLE_NAME);
        sqlBuilder.selectSQL(columns).andWhere(columns[0]);
        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue(columns[0], (Object)schemeKey);
        return this.executeQueryHalf(sqlBuilder.toString(), source).size();
    }

    @Override
    public List<ShareSummaryScheme> findSchemes(String task) {
        NamedParameterSqlBuilder sqlBuilder = new NamedParameterSqlBuilder(TABLE_NAME);
        sqlBuilder.selectJoinOn(searchColumns, SCHEME_TABLE_NAME, BS_CODE, searchColumns[1]).andWhere(BSS_TASK, "BSS_FROM_USER").orderBy(searchColumns[0]);
        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue(BSS_TASK, (Object)task);
        source.addValue("BSS_FROM_USER", (Object)BatchSummaryUtils.getCurrentUserID());
        return this.executeQuery(sqlBuilder.toString(), source);
    }

    @Override
    public List<ShareSummaryScheme> findSchemes(List<String> schemeKeys) {
        return null;
    }

    @Override
    public List<ShareSummaryScheme> findGroupSchemes(String task, String fromUser) {
        if (Objects.equals(fromUser, "00000000-0000-0000-0000-000000000000")) {
            NamedParameterSqlBuilder sqlBuilder = new NamedParameterSqlBuilder(TABLE_NAME);
            sqlBuilder.selectJoinOn(searchColumns, SCHEME_TABLE_NAME, BS_CODE, searchColumns[1]).andWhere(BSS_TASK, "BSS_TO_USER").orderBy(searchColumns[0]);
            MapSqlParameterSource source = new MapSqlParameterSource();
            source.addValue(BSS_TASK, (Object)task);
            source.addValue("BSS_TO_USER", (Object)BatchSummaryUtils.getCurrentUserID());
            return this.executeQuery(sqlBuilder.toString(), source);
        }
        NamedParameterSqlBuilder sqlBuilder = new NamedParameterSqlBuilder(TABLE_NAME);
        sqlBuilder.selectJoinOn(searchColumns, SCHEME_TABLE_NAME, BS_CODE, searchColumns[1]).andWhere(BSS_TASK, "BSS_FROM_USER", "BSS_TO_USER").orderBy(searchColumns[0]);
        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue(BSS_TASK, (Object)task);
        source.addValue("BSS_FROM_USER", (Object)fromUser);
        source.addValue("BSS_TO_USER", (Object)BatchSummaryUtils.getCurrentUserID());
        return this.executeQuery(sqlBuilder.toString(), source);
    }

    @Override
    public List<ShareSummaryScheme> findGroupSchemes(String task, List<String> groupKeys) {
        return null;
    }

    @Override
    public ShareSummaryGroup findGroup(String groupKey) {
        return null;
    }

    @Override
    public List<ShareSummaryGroup> findChildGroups(String task, String fromUser) {
        if (Objects.equals(fromUser, "00000000-0000-0000-0000-000000000000")) {
            NamedParameterSqlBuilder sqlBuilder = new NamedParameterSqlBuilder(TABLE_NAME);
            sqlBuilder.selectSQL(groupColumns).andWhere(columns[2], columns[4]).groupBy(columns[1], columns[4]);
            MapSqlParameterSource source = new MapSqlParameterSource();
            source.addValue(columns[2], (Object)BatchSummaryUtils.getCurrentUserID());
            source.addValue(columns[4], (Object)task);
            return this.executeQueryToGroup(sqlBuilder.toString(), source);
        }
        return new ArrayList<ShareSummaryGroup>();
    }

    @Override
    public List<ShareSummaryGroup> findAllChildGroups(String task, String groupKey) {
        return null;
    }

    @Override
    public int removeShareSummaryScheme(ShareSummarySchemeDefine shareSummarySchemeDefine) {
        NamedParameterSqlBuilder sqlBuilder = new NamedParameterSqlBuilder(TABLE_NAME);
        sqlBuilder.deleteSQL().andWhere(columns[0], columns[1], columns[2]);
        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue(columns[0], (Object)shareSummarySchemeDefine.getCode());
        source.addValue(columns[1], (Object)shareSummarySchemeDefine.getFromUser());
        source.addValue(columns[2], (Object)shareSummarySchemeDefine.getToUser());
        return this.executeUpdate(sqlBuilder.toString(), source);
    }

    @Override
    public int removeShareSummaryScheme(String key) {
        NamedParameterSqlBuilder sqlBuilder = new NamedParameterSqlBuilder(TABLE_NAME);
        sqlBuilder.deleteSQL().andWhere(columns[0]);
        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue(columns[0], (Object)key);
        return this.executeUpdate(sqlBuilder.toString(), source);
    }

    @Override
    public int addShareSummaryScheme(ShareSummarySchemeDefine shareSummarySchemeDefine) {
        NamedParameterSqlBuilder sqlBuilder = new NamedParameterSqlBuilder(TABLE_NAME);
        sqlBuilder.insertSQL(columns);
        MapSqlParameterSource source = this.buildMapSource(shareSummarySchemeDefine);
        return this.executeUpdate(sqlBuilder.toString(), source);
    }

    @Override
    public Set<String> findToUsers(String task, String scheme) {
        NamedParameterSqlBuilder sqlBuilder = new NamedParameterSqlBuilder(TABLE_NAME);
        sqlBuilder.selectSQL(columns).andWhere(columns[0], columns[1], columns[4]);
        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue(columns[0], (Object)scheme);
        source.addValue(columns[1], (Object)BatchSummaryUtils.getCurrentUserID());
        source.addValue(columns[4], (Object)task);
        return new HashSet<String>(this.executeQueryToUser(sqlBuilder.toString(), source));
    }

    private MapSqlParameterSource buildMapSource(ShareSummaryScheme SSScheme) {
        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue(columns[0], (Object)SSScheme.getCode());
        source.addValue(columns[1], (Object)SSScheme.getFromUser());
        source.addValue(columns[2], (Object)SSScheme.getToUser());
        source.addValue(columns[3], (Object)SSScheme.getShareTime());
        source.addValue(columns[4], (Object)SSScheme.getTask());
        return source;
    }

    private int executeUpdate(String sql, MapSqlParameterSource source) {
        NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate((JdbcOperations)this.jdbcTemplate);
        return template.update(sql, (SqlParameterSource)source);
    }

    private List<ShareSummaryScheme> executeQueryHalf(String sql, MapSqlParameterSource source) {
        NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate((JdbcOperations)this.jdbcTemplate);
        return template.query(sql, (SqlParameterSource)source, this::readSummarySchemeSharehalf);
    }

    private List<ShareSummaryScheme> executeQuery(String sql, MapSqlParameterSource source) {
        NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate((JdbcOperations)this.jdbcTemplate);
        return template.query(sql, (SqlParameterSource)source, this::readSummarySchemeShare);
    }

    private List<ShareSummaryGroup> executeQueryToGroup(String sql, MapSqlParameterSource source) {
        NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate((JdbcOperations)this.jdbcTemplate);
        return template.query(sql, (SqlParameterSource)source, this::readSummarySchemeGroupShare);
    }

    private List<String> executeQueryToUser(String sql, MapSqlParameterSource source) {
        NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate((JdbcOperations)this.jdbcTemplate);
        return template.query(sql, (SqlParameterSource)source, this::readSummaryUsers);
    }

    private ShareSummaryGroup readSummarySchemeGroupShare(ResultSet rs, int rowIdx) throws SQLException {
        ShareSummaryGroupDefine impl = new ShareSummaryGroupDefine();
        impl.setTitle(rs.getString(columns[1]));
        impl.setCode(rs.getString(columns[1]));
        impl.setTask(rs.getString(columns[4]));
        return impl;
    }

    private ShareSummaryScheme readSummarySchemeShare(ResultSet rs, int rowIdx) throws SQLException {
        ShareSummarySchemeDefine impl = new ShareSummarySchemeDefine();
        impl.setTitle(rs.getString(searchColumns[0]));
        impl.setCode(rs.getString(searchColumns[1]));
        impl.setFromUser(rs.getString(searchColumns[2]));
        impl.setToUser(rs.getString(searchColumns[3]));
        impl.setShareTime(this.translate2Date(rs.getTimestamp(searchColumns[4])));
        impl.setTask(rs.getString(searchColumns[5]));
        return impl;
    }

    private ShareSummaryScheme readSummarySchemeSharehalf(ResultSet rs, int rowIdx) throws SQLException {
        ShareSummarySchemeDefine impl = new ShareSummarySchemeDefine();
        impl.setCode(rs.getString(columns[0]));
        impl.setFromUser(rs.getString(columns[1]));
        impl.setToUser(rs.getString(columns[2]));
        impl.setShareTime(this.translate2Date(rs.getTimestamp(columns[3])));
        impl.setTask(rs.getString(columns[4]));
        return impl;
    }

    private String readSummaryUsers(ResultSet rs, int rowIdx) throws SQLException {
        String impl = rs.getString(columns[2]);
        return impl;
    }

    private Date translate2Date(Timestamp timestamp) {
        if (timestamp != null) {
            return new Date(timestamp.getTime());
        }
        return null;
    }
}

