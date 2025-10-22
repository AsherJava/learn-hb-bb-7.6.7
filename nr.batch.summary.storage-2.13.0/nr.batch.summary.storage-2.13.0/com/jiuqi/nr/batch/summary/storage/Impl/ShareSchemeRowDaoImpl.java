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
import com.jiuqi.nr.batch.summary.storage.ShareSchemeRowDao;
import com.jiuqi.nr.batch.summary.storage.entity.ShareSchemeRow;
import com.jiuqi.nr.batch.summary.storage.entity.impl.ShareSchemeRowDefine;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

@Repository
public class ShareSchemeRowDaoImpl
implements ShareSchemeRowDao {
    private static final String BSS_TASK = "BSS_TASK";
    private static final String TABLE_NAME = "NR_BS_SHARE";
    private static final String[] columns = new String[]{"BSS_SCHEME", "BSS_FROM_USER", "BSS_TO_USER", "BSS_SHARE_TIME", "BSS_TASK"};
    @Resource
    private JdbcTemplate jdbcTemplate;

    @Override
    public int[] insertRows(List<ShareSchemeRowDefine> rows) {
        NamedParameterSqlBuilder sqlBuilder = new NamedParameterSqlBuilder(TABLE_NAME);
        sqlBuilder.insertSQL(columns);
        MapSqlParameterSource[] batchMapSource = this.buildBatchMapSource(rows);
        NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate((JdbcOperations)this.jdbcTemplate);
        return template.batchUpdate(sqlBuilder.toString(), (SqlParameterSource[])batchMapSource);
    }

    @Override
    public int[] updateRows(List<ShareSchemeRowDefine> rows) {
        return new int[0];
    }

    @Override
    public int removeRows(List<String> rowKeys) {
        return 0;
    }

    private MapSqlParameterSource[] buildBatchMapSource(List<ShareSchemeRowDefine> rows) {
        MapSqlParameterSource[] sources = new MapSqlParameterSource[rows.size()];
        for (int i = 0; i < rows.size(); ++i) {
            ShareSchemeRow row = rows.get(i);
            sources[i] = this.buildSqlParameterSource(row);
        }
        return sources;
    }

    private MapSqlParameterSource buildSqlParameterSource(ShareSchemeRow row) {
        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue(columns[0], (Object)row.getScheme());
        source.addValue(columns[1], (Object)row.getFromUser());
        source.addValue(columns[2], (Object)row.getToUser());
        source.addValue(columns[3], (Object)row.getShareTime());
        source.addValue(columns[4], (Object)row.getTask());
        return source;
    }

    private ShareSchemeRow readShareSchemeRow(ResultSet rs, int rowIdx) throws SQLException {
        ShareSchemeRowDefine impl = new ShareSchemeRowDefine();
        impl.setScheme(rs.getString(columns[0]));
        impl.setFromUser(rs.getString(columns[1]));
        impl.setToUser(rs.getString(columns[2]));
        impl.setShareTime(this.translate2Date(rs.getTimestamp(columns[3])));
        impl.setTask(rs.getString(columns[4]));
        return impl;
    }

    private Date translate2Date(Timestamp timestamp) {
        if (timestamp != null) {
            return new Date(timestamp.getTime());
        }
        return null;
    }
}

