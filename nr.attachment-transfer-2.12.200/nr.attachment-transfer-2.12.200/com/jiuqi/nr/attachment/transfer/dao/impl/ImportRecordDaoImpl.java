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

import com.jiuqi.nr.attachment.transfer.dao.IImportRecordDao;
import com.jiuqi.nr.attachment.transfer.dao.impl.GenerateRecordDaoImpl;
import com.jiuqi.nr.attachment.transfer.domain.ImportRecordDO;
import java.util.ArrayList;
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

@Repository
public class ImportRecordDaoImpl
implements IImportRecordDao {
    private static final String TABLE_NAME = "NR_ATTRIO_IMPORT_RECORD";
    public static final String FIELD_AR_KEY = "AR_KEY";
    private static final String FIELD_AR_OWNER = "AR_OWNER";
    private static final String FIELD_AR_FILENAME = "AR_FILENAME";
    private static final String FIELD_AR_SIZE = "AR_SIZE";
    public static final String FIELD_AR_EXE_NUM = "AR_EXE_NUM";
    public static final String FIELD_AR_START_TIME = "AR_START_TIME";
    public static final String FIELD_AR_END_TIME = "AR_END_TIME";
    public static final String FIELD_AR_CONTENT = "AR_CONTENT";
    public static final String FIELD_AR_STATUS = "AR_STATUS";
    private static final String FIELD_AR_ORDINAL = "AR_ORDINAL";
    static final RowMapper<ImportRecordDO> ROW_MAPPER = (rs, rowNum) -> {
        ImportRecordDO importRecordDO = new ImportRecordDO();
        importRecordDO.setKey(rs.getString(FIELD_AR_KEY));
        importRecordDO.setOwner(rs.getString(FIELD_AR_OWNER));
        importRecordDO.setFileName(rs.getString(FIELD_AR_FILENAME));
        importRecordDO.setSize(rs.getLong(FIELD_AR_SIZE));
        importRecordDO.setExecuteNum(rs.getInt(FIELD_AR_EXE_NUM));
        importRecordDO.setStartTime(rs.getTimestamp(FIELD_AR_START_TIME));
        importRecordDO.setEndTime(rs.getTimestamp(FIELD_AR_END_TIME));
        importRecordDO.setContent(rs.getString(FIELD_AR_CONTENT));
        importRecordDO.setStatus(rs.getInt(FIELD_AR_STATUS));
        importRecordDO.setOrder(rs.getDouble(FIELD_AR_ORDINAL));
        return importRecordDO;
    };
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<ImportRecordDO> list() {
        String sql = String.format("SELECT * FROM %s ORDER BY %s", TABLE_NAME, FIELD_AR_ORDINAL);
        return this.jdbcTemplate.query(sql, ROW_MAPPER);
    }

    @Override
    public List<ImportRecordDO> listByStatus(int ... status) {
        StringBuilder sbs = new StringBuilder();
        String sql = String.format("SELECT * FROM %s WHERE ", TABLE_NAME);
        sbs.append(sql);
        Object[] arg = new Object[status.length];
        for (int i = 0; i < status.length; ++i) {
            sbs.append(FIELD_AR_STATUS).append(" = ").append(" ? ");
            if (i != status.length - 1) {
                sbs.append(" OR ");
            }
            arg[i] = status[i];
        }
        return this.jdbcTemplate.query(sbs.toString(), ROW_MAPPER, arg);
    }

    @Override
    public List<ImportRecordDO> listByFilter(int status, int start, int end) {
        StringBuilder sbs = new StringBuilder("SELECT A.* FROM ( SELECT T.*, ROWNUM RN FROM ");
        ArrayList<Integer> args = new ArrayList<Integer>();
        sbs.append(" ( SELECT ER.* ").append(" FROM ");
        sbs.append(TABLE_NAME).append(" ER ").append(" WHERE ").append(" 1 = 1 ");
        if (status != -1) {
            sbs.append(" AND ").append(FIELD_AR_STATUS).append(" = ?");
            args.add(status);
        }
        sbs.append(" ORDER BY ").append(FIELD_AR_ORDINAL);
        sbs.append(" ) T ");
        sbs.append(" WHERE ROWNUM <= ").append(end).append(" ) A");
        sbs.append(" WHERE A.RN > ").append(start);
        return this.jdbcTemplate.query(sbs.toString(), ROW_MAPPER, args.toArray());
    }

    @Override
    public ImportRecordDO get(String key) {
        String sql = String.format("SELECT * FROM %s WHERE %s = ?", TABLE_NAME, FIELD_AR_KEY);
        Object[] arg = new Object[]{key};
        List query = this.jdbcTemplate.query(sql, ROW_MAPPER, arg);
        return !query.isEmpty() ? (ImportRecordDO)query.get(0) : null;
    }

    @Override
    public void insert(List<ImportRecordDO> list) {
        String sql = String.format("INSERT INTO %s (%s, %s, %s, %s, %s, %s,%s) VALUES (?, ?, ?, ?, ?, ?, ?)", TABLE_NAME, FIELD_AR_KEY, FIELD_AR_SIZE, FIELD_AR_OWNER, FIELD_AR_FILENAME, FIELD_AR_EXE_NUM, FIELD_AR_STATUS, FIELD_AR_ORDINAL);
        ArrayList<Object[]> args = new ArrayList<Object[]>();
        for (ImportRecordDO recordDO : list) {
            Object[] arg = new Object[]{recordDO.getKey(), recordDO.getSize(), recordDO.getOwner(), recordDO.getFileName(), 0, 0, recordDO.getOrder()};
            args.add(arg);
        }
        this.jdbcTemplate.batchUpdate(sql, args);
    }

    @Override
    public int update(ImportRecordDO recordDO) {
        String sql = String.format("UPDATE %s set %s = ?, %s = ?, %s = ?, %s = ?, %s = ? WHERE %s = ?", TABLE_NAME, FIELD_AR_SIZE, FIELD_AR_EXE_NUM, FIELD_AR_CONTENT, FIELD_AR_END_TIME, FIELD_AR_STATUS, FIELD_AR_KEY);
        Object[] arg = new Object[]{recordDO.getSize(), recordDO.getExecuteNum(), recordDO.getContent(), recordDO.getEndTime(), recordDO.getStatus(), recordDO.getKey()};
        return this.jdbcTemplate.update(sql, arg);
    }

    @Override
    public void updateField(String key, Map<String, Object> param) {
        StringBuilder templateSql = new StringBuilder(" SET ");
        param.forEach((field, value) -> templateSql.append((String)field).append(" = :").append((String)field).append(","));
        param.put(FIELD_AR_KEY, key);
        int i = templateSql.lastIndexOf(",");
        templateSql.delete(i, templateSql.length());
        String updateSql = String.format("UPDATE %s  %s  WHERE %s = :%s", TABLE_NAME, templateSql, FIELD_AR_KEY, FIELD_AR_KEY);
        NamedParameterJdbcTemplate namedJdbc = new NamedParameterJdbcTemplate((JdbcOperations)this.jdbcTemplate);
        namedJdbc.update(updateSql, param);
    }

    @Override
    public int updateStatus(String key, int status) {
        String sql = String.format("UPDATE %s set %s = ? WHERE %s = ?", TABLE_NAME, FIELD_AR_STATUS, FIELD_AR_KEY);
        Object[] arg = new Object[]{status, key};
        return this.jdbcTemplate.update(sql, arg);
    }

    @Override
    public int updateFileInfo(ImportRecordDO record) {
        String sql = String.format("UPDATE %s set %s = ?, %s = ?, %s = ?, %s = ? WHERE %s = ?", TABLE_NAME, FIELD_AR_FILENAME, FIELD_AR_STATUS, FIELD_AR_SIZE, FIELD_AR_END_TIME, FIELD_AR_KEY);
        Object[] arg = new Object[]{record.getFileName(), record.getStatus(), record.getSize(), record.getEndTime(), record.getKey()};
        return this.jdbcTemplate.update(sql, arg);
    }

    @Override
    public void batchUpdateStatus(int status, String ... keys) {
        String sql = String.format("UPDATE %s set %s = :%s WHERE %s IN (:%s) ?", TABLE_NAME, FIELD_AR_STATUS, FIELD_AR_STATUS, FIELD_AR_KEY, FIELD_AR_KEY);
        HashMap<String, String[]> param = new HashMap<String, String[]>();
        param.put(FIELD_AR_KEY, keys);
        NamedParameterJdbcTemplate namedJdbc = new NamedParameterJdbcTemplate((JdbcOperations)this.jdbcTemplate);
        MapSqlParameterSource[] parameterSources = new MapSqlParameterSource[keys.length];
        for (int i = 0; i < keys.length; ++i) {
            MapSqlParameterSource parameterSource = new MapSqlParameterSource();
            parameterSource.addValue(FIELD_AR_KEY, (Object)keys[i]);
            parameterSource.addValue(FIELD_AR_STATUS, (Object)status);
            parameterSources[i] = parameterSource;
        }
        namedJdbc.batchUpdate(sql, (SqlParameterSource[])parameterSources);
    }

    @Override
    public void deleteAll() {
        String sql = String.format("DELETE FROM %s ", TABLE_NAME);
        this.jdbcTemplate.update(sql);
    }

    private void batchUpdateField(String fieldName, String[] keys, GenerateRecordDaoImpl.UpdateFieldValueHandle handle) {
        String querySql = String.format("SELECT %s, %s FROM %s where %s in (:%s)", FIELD_AR_KEY, fieldName, TABLE_NAME, FIELD_AR_KEY, FIELD_AR_KEY);
        NamedParameterJdbcTemplate namedJdbc = new NamedParameterJdbcTemplate((JdbcOperations)this.jdbcTemplate);
        HashMap<String, String[]> params = new HashMap<String, String[]>();
        params.put(FIELD_AR_KEY, keys);
        ArrayList args = new ArrayList();
        namedJdbc.query(querySql, params, rs -> {
            while (rs.next()) {
                String key = rs.getString(FIELD_AR_KEY);
                int value = rs.getInt(fieldName);
                args.add(new Object[]{handle.handle(value), key});
            }
        });
        String sql = String.format("UPDATE %s set %s = ? WHERE %s = ?", TABLE_NAME, fieldName, FIELD_AR_KEY);
        this.jdbcTemplate.batchUpdate(sql, args);
    }

    public static interface UpdateFieldValueHandle {
        public Object handle(Object var1);
    }
}

