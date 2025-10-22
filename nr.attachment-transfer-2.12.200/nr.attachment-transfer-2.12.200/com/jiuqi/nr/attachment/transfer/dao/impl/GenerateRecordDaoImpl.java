/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.JdbcOperations
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.jdbc.core.RowMapper
 *  org.springframework.jdbc.core.namedparam.MapSqlParameterSource
 *  org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
 *  org.springframework.jdbc.core.namedparam.SqlParameterSource
 */
package com.jiuqi.nr.attachment.transfer.dao.impl;

import com.jiuqi.nr.attachment.transfer.common.Constant;
import com.jiuqi.nr.attachment.transfer.dao.IGenerateRecordDao;
import com.jiuqi.nr.attachment.transfer.domain.AttachmentRecordDO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

@Repository
public class GenerateRecordDaoImpl
implements IGenerateRecordDao {
    private static final String TABLE_NAME = "NR_ATTRIO_EXPORT_RECORD";
    public static final String FIELD_AR_KEY = "AR_KEY";
    private static final String FIELD_AR_ENTITY = "AR_ENTITY";
    private static final String FIELD_AR_FILENAME = "AR_FILENAME";
    public static final String FIELD_AR_STATUS = "AR_STATUS";
    public static final String FIELD_AR_SIZE = "AR_SIZE";
    public static final String FIELD_AR_CONTENT = "AR_CONTENT";
    public static final String FIELD_AR_DOWN_NUM = "AR_DOWN_NUM";
    private static final String FIELD_AR_ORDINAL = "AR_ORDINAL";
    public static final String FIELD_AR_START_TIME = "AR_START_TIME";
    public static final String FIELD_AR_END_TIME = "AR_END_TIME";
    public static final String FIELD_AR_HISTORY = "AR_HISTORY";
    static final RowMapper<AttachmentRecordDO> ROW_MAPPER = new RowMapper<AttachmentRecordDO>(){

        public AttachmentRecordDO mapRow(ResultSet rs, int rowNum) throws SQLException {
            AttachmentRecordDO attachmentRecordDO = new AttachmentRecordDO();
            attachmentRecordDO.setKey(rs.getString(GenerateRecordDaoImpl.FIELD_AR_KEY));
            String entity = rs.getString(GenerateRecordDaoImpl.FIELD_AR_ENTITY);
            if (StringUtils.hasText(entity)) {
                String[] split = entity.split(";");
                attachmentRecordDO.setEntityKey(new ArrayList<String>(Arrays.asList(split)));
            }
            attachmentRecordDO.setFileName(rs.getString(GenerateRecordDaoImpl.FIELD_AR_FILENAME));
            attachmentRecordDO.setStatus(rs.getInt(GenerateRecordDaoImpl.FIELD_AR_STATUS));
            attachmentRecordDO.setSize(rs.getLong(GenerateRecordDaoImpl.FIELD_AR_SIZE));
            attachmentRecordDO.setDownloadNum(rs.getInt(GenerateRecordDaoImpl.FIELD_AR_DOWN_NUM));
            attachmentRecordDO.setHistory(rs.getBoolean(GenerateRecordDaoImpl.FIELD_AR_HISTORY));
            attachmentRecordDO.setHistory(rs.getBoolean(GenerateRecordDaoImpl.FIELD_AR_HISTORY));
            attachmentRecordDO.setStartTime(rs.getTimestamp(GenerateRecordDaoImpl.FIELD_AR_START_TIME));
            attachmentRecordDO.setEndTime(rs.getTimestamp(GenerateRecordDaoImpl.FIELD_AR_END_TIME));
            attachmentRecordDO.setOrder(rs.getLong(GenerateRecordDaoImpl.FIELD_AR_ORDINAL));
            attachmentRecordDO.setContent(rs.getString(GenerateRecordDaoImpl.FIELD_AR_CONTENT));
            return attachmentRecordDO;
        }
    };
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<AttachmentRecordDO> list() {
        String sql = String.format("SELECT * FROM %s", TABLE_NAME);
        return this.jdbcTemplate.query(sql, ROW_MAPPER);
    }

    @Override
    public List<AttachmentRecordDO> list(List<String> keys) {
        String sql = String.format("SELECT * FROM %s WHERE %s in(:%s) ", TABLE_NAME, FIELD_AR_KEY, FIELD_AR_KEY);
        HashMap<String, List<String>> params = new HashMap<String, List<String>>(1);
        params.put(FIELD_AR_KEY, keys);
        NamedParameterJdbcTemplate namedJdbc = new NamedParameterJdbcTemplate((JdbcOperations)this.jdbcTemplate);
        return namedJdbc.query(sql, params, ROW_MAPPER);
    }

    @Override
    public AttachmentRecordDO get(String key) {
        String sql = String.format("SELECT * FROM %s WHERE %s = ?", TABLE_NAME, FIELD_AR_KEY);
        Object[] arg = new Object[]{key};
        List query = this.jdbcTemplate.query(sql, ROW_MAPPER, arg);
        return !query.isEmpty() ? (AttachmentRecordDO)query.get(0) : null;
    }

    @Override
    public List<AttachmentRecordDO> listByFilter(int status, int download, int history, int start, int end) {
        StringBuilder sbs = new StringBuilder("SELECT A.* FROM ( SELECT T.*, ROWNUM RN FROM ");
        ArrayList<Integer> args = new ArrayList<Integer>();
        sbs.append(" ( SELECT ER.* ").append(" FROM ");
        sbs.append(TABLE_NAME).append(" ER ").append(" WHERE ").append(" 1 = 1 ");
        if (status != -1) {
            sbs.append(" AND ").append(FIELD_AR_STATUS).append(" = ?");
            args.add(status);
        }
        if (download == 0) {
            sbs.append(" AND ").append(FIELD_AR_DOWN_NUM).append(" = ?");
            args.add(0);
        } else if (download == 1) {
            sbs.append(" AND ").append(FIELD_AR_DOWN_NUM).append(" > ?");
            args.add(0);
        }
        if (history != -1) {
            sbs.append(" AND ").append(FIELD_AR_HISTORY).append(" = ?");
            args.add(history);
        }
        sbs.append(" ORDER BY ").append(FIELD_AR_ORDINAL);
        sbs.append(" ) T ");
        sbs.append(" WHERE ROWNUM <= ").append(end).append(" ) A");
        sbs.append(" WHERE A.RN > ").append(start);
        return this.jdbcTemplate.query(sbs.toString(), ROW_MAPPER, args.toArray());
    }

    @Override
    public List<AttachmentRecordDO> listByStatus(int ... status) {
        StringBuilder sbs = new StringBuilder("SELECT * FROM ");
        sbs.append(TABLE_NAME).append(" WHERE ");
        Object[] arg = new Object[status.length];
        for (int i = 0; i < status.length; ++i) {
            sbs.append(FIELD_AR_STATUS).append(" = ?");
            arg[i] = status[i];
            if (i == status.length - 1) continue;
            sbs.append(" OR ");
        }
        sbs.append(" ORDER BY ").append(FIELD_AR_ORDINAL).append(" ASC ");
        return this.jdbcTemplate.query(sbs.toString(), ROW_MAPPER, arg);
    }

    @Override
    public void insert(List<AttachmentRecordDO> list) {
        String sql = String.format("INSERT INTO %s (%s, %s, %s, %s, %s, %s, %s) VALUES (?, ?, ?, ?, ?, ?, ?)", TABLE_NAME, FIELD_AR_KEY, FIELD_AR_ENTITY, FIELD_AR_FILENAME, FIELD_AR_STATUS, FIELD_AR_DOWN_NUM, FIELD_AR_ORDINAL, FIELD_AR_HISTORY);
        ArrayList<Object[]> args = new ArrayList<Object[]>();
        for (AttachmentRecordDO recordDO : list) {
            Object[] arg = new Object[]{recordDO.getKey(), String.join((CharSequence)";", recordDO.getEntityKey()), recordDO.getFileName(), recordDO.getStatus(), 0, recordDO.getOrder(), 0};
            args.add(arg);
        }
        this.jdbcTemplate.batchUpdate(sql, args);
    }

    @Override
    public int update(AttachmentRecordDO recordDO) {
        String sql = String.format("UPDATE %s set %s = ?, %s = ?, %s = ?, %s = ?, %s = ?, %s = ?, %s = ? WHERE %s = ?", TABLE_NAME, FIELD_AR_FILENAME, FIELD_AR_STATUS, FIELD_AR_SIZE, FIELD_AR_DOWN_NUM, FIELD_AR_START_TIME, FIELD_AR_END_TIME, FIELD_AR_HISTORY, FIELD_AR_KEY);
        Object[] arg = new Object[]{recordDO.getFileName(), recordDO.getStatus(), recordDO.getSize(), recordDO.getDownloadNum(), recordDO.getStartTime(), recordDO.getEndTime(), recordDO.isHistory(), recordDO.getKey()};
        return this.jdbcTemplate.update(sql, arg);
    }

    @Override
    public void deleteAll() {
        String sql = String.format("DELETE FROM %s", TABLE_NAME);
        this.jdbcTemplate.update(sql);
    }

    @Override
    public int updateFileInfo(AttachmentRecordDO record) {
        String sql = String.format("UPDATE %s set %s = ?, %s = ?, %s = ?, %s = ? WHERE %s = ?", TABLE_NAME, FIELD_AR_FILENAME, FIELD_AR_STATUS, FIELD_AR_SIZE, FIELD_AR_END_TIME, FIELD_AR_KEY);
        Object[] arg = new Object[]{record.getFileName(), record.getStatus(), record.getSize(), record.getEndTime(), record.getKey()};
        return this.jdbcTemplate.update(sql, arg);
    }

    @Override
    public void batchUpdateStatus(int status, boolean markHistory, String ... keys) {
        StringBuilder sbs = new StringBuilder();
        sbs.append("SET ").append(FIELD_AR_STATUS).append(" = :").append(FIELD_AR_STATUS);
        if (markHistory) {
            boolean bl = markHistory = status == Constant.GenerateStatus.DESTROYED.getStatus();
        }
        if (markHistory) {
            sbs.append(",").append(FIELD_AR_HISTORY).append(" = :").append(FIELD_AR_HISTORY);
        }
        String sql = String.format("UPDATE %s %s WHERE %s IN (:%s) ", TABLE_NAME, sbs, FIELD_AR_KEY, FIELD_AR_KEY);
        NamedParameterJdbcTemplate namedJdbc = new NamedParameterJdbcTemplate((JdbcOperations)this.jdbcTemplate);
        MapSqlParameterSource[] parameterSources = new MapSqlParameterSource[keys.length];
        for (int i = 0; i < keys.length; ++i) {
            MapSqlParameterSource parameterSource = new MapSqlParameterSource();
            parameterSource.addValue(FIELD_AR_KEY, (Object)keys[i]);
            parameterSource.addValue(FIELD_AR_STATUS, (Object)status);
            if (markHistory) {
                parameterSource.addValue(FIELD_AR_HISTORY, (Object)1);
            }
            parameterSources[i] = parameterSource;
        }
        namedJdbc.batchUpdate(sql, (SqlParameterSource[])parameterSources);
    }

    @Override
    public void updateStatus(String key, int status) {
        String sql = String.format("UPDATE %s set %s = ?WHERE %s = ? ", TABLE_NAME, FIELD_AR_STATUS, FIELD_AR_KEY);
        Object[] arg = new Object[]{status, key};
        this.jdbcTemplate.update(sql, arg);
    }

    @Override
    public void updateField(String key, Map<String, Object> param) {
        StringBuilder templateSql = new StringBuilder("SET ");
        param.forEach((field, value) -> templateSql.append((String)field).append(" = :").append((String)field).append(","));
        param.put(FIELD_AR_KEY, key);
        int i = templateSql.lastIndexOf(",");
        templateSql.delete(i, templateSql.length());
        String updateSql = String.format("UPDATE %s  %s  WHERE %s = :%s", TABLE_NAME, templateSql, FIELD_AR_KEY, FIELD_AR_KEY);
        NamedParameterJdbcTemplate namedJdbc = new NamedParameterJdbcTemplate((JdbcOperations)this.jdbcTemplate);
        namedJdbc.update(updateSql, param);
    }

    @Override
    public void updateDownloadInfo(String key, int downLoadNum) {
        Object[] arg = new Object[]{downLoadNum, key};
        String sql = String.format("UPDATE %s set %s = ? WHERE %s = ?", TABLE_NAME, FIELD_AR_DOWN_NUM, FIELD_AR_KEY);
        this.jdbcTemplate.update(sql, arg);
    }

    public static interface UpdateFieldValueHandle {
        public Object handle(Object var1);
    }
}

