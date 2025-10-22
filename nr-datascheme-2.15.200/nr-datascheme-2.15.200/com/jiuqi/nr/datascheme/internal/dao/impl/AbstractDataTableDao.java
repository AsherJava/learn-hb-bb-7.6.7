/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.type.DataTableType
 *  org.springframework.dao.DataAccessException
 *  org.springframework.jdbc.core.RowMapper
 *  org.springframework.jdbc.core.namedparam.MapSqlParameterSource
 *  org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
 *  org.springframework.jdbc.core.namedparam.SqlParameterSource
 */
package com.jiuqi.nr.datascheme.internal.dao.impl;

import com.jiuqi.nr.datascheme.api.type.DataTableType;
import com.jiuqi.nr.datascheme.internal.dao.IDataTableDao;
import com.jiuqi.nr.datascheme.internal.dao.impl.BaseDao;
import com.jiuqi.nr.datascheme.internal.entity.DataTableDO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

public abstract class AbstractDataTableDao<DO extends DataTableDO>
extends BaseDao
implements IDataTableDao<DO> {
    @Autowired
    protected NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private Logger logger = LoggerFactory.getLogger(AbstractDataTableDao.class);

    @Override
    public String insert(DO dataTableDO) throws DataAccessException {
        Assert.notNull(dataTableDO, "dataTableDO must not be null.");
        if (((DataTableDO)dataTableDO).getKey() == null) {
            ((DataTableDO)dataTableDO).setKey(UUID.randomUUID().toString());
        }
        if (((DataTableDO)dataTableDO).getUpdateTime() == null) {
            ((DataTableDO)dataTableDO).setUpdateTime(Instant.now());
        }
        super.insert(dataTableDO);
        return ((DataTableDO)dataTableDO).getKey();
    }

    @Override
    public void delete(String key) throws DataAccessException {
        Assert.notNull((Object)key, "key must not be null.");
        super.delete(key);
    }

    @Override
    public void deleteByDataScheme(String schemeKey) throws DataAccessException {
        Assert.notNull((Object)schemeKey, "schemeKey must not be null.");
        super.deleteBy(new String[]{"DT_DS_KEY"}, new String[]{schemeKey});
    }

    @Override
    public void update(DO dataTableDO) throws DataAccessException {
        Assert.notNull(dataTableDO, "dataTableDO must not be null.");
        Assert.notNull((Object)((DataTableDO)dataTableDO).getKey(), "dataTableKey must not be null.");
        if (((DataTableDO)dataTableDO).getUpdateTime() == null) {
            ((DataTableDO)dataTableDO).setUpdateTime(Instant.now());
        }
        super.update(dataTableDO);
    }

    @Override
    public DO get(String key) throws DataAccessException {
        Assert.notNull((Object)key, "key must not be null.");
        return (DO)((DataTableDO)super.getByKey(key, this.getClz()));
    }

    @Override
    public String[] batchInsert(List<DO> dataTableDO) throws DataAccessException {
        Assert.notNull(dataTableDO, "dataTableDO must not be null.");
        String[] dataTableKeys = new String[dataTableDO.size()];
        for (int i = 0; i < dataTableDO.size(); ++i) {
            DataTableDO dataTable = (DataTableDO)dataTableDO.get(i);
            Assert.notNull((Object)dataTable, "Collection should not contain null.");
            if (dataTable.getKey() == null) {
                dataTable.setKey(UUID.randomUUID().toString());
            }
            if (dataTable.getUpdateTime() == null) {
                dataTable.setUpdateTime(Instant.now());
            }
            dataTableKeys[i] = dataTable.getKey();
        }
        super.insert(dataTableDO.toArray());
        return dataTableKeys;
    }

    @Override
    public void batchDelete(List<String> keys) throws DataAccessException {
        Assert.notNull(keys, "keys must not be null.");
        for (String key : keys) {
            Assert.notNull((Object)key, "Collection should not contain null.");
        }
        super.delete(keys.toArray());
    }

    @Override
    public void batchUpdate(List<DO> dataTableDO) throws DataAccessException {
        Assert.notNull(dataTableDO, "dataSchemeDO must not be null.");
        for (DataTableDO dataTable : dataTableDO) {
            Assert.notNull((Object)dataTable, "Collection should not contain null.");
            Assert.notNull((Object)dataTable.getKey(), "dataSchemeKey must not be null.");
            if (dataTable.getUpdateTime() != null) continue;
            dataTable.setUpdateTime(Instant.now());
        }
        super.update(dataTableDO.toArray());
    }

    @Override
    public List<DO> batchGet(List<String> keys) throws DataAccessException {
        Assert.notNull(keys, "keys must not be null.");
        if (keys.isEmpty()) {
            return Collections.emptyList();
        }
        for (String key : keys) {
            Assert.notNull((Object)key, "Collection should not contain null.");
        }
        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource();
        sqlParameterSource.addValue("keys", keys);
        RowMapper<DO> rowMapper = super.getRowMapper(this.getClz());
        String sql = this.selectSQL + " where " + "DT_KEY" + " in (:keys) ";
        this.logger.debug(sql);
        return this.namedParameterJdbcTemplate.query(sql, (SqlParameterSource)sqlParameterSource, rowMapper);
    }

    @Override
    public List<DO> getAll() throws DataAccessException {
        return super.list(this.getClz());
    }

    @Override
    public List<DO> getByGroup(String parentKey) {
        Assert.notNull((Object)parentKey, "parentKey must not be null.");
        return super.list(new String[]{"DT_DG_KEY"}, (Object[])new String[]{parentKey}, this.getClz());
    }

    @Override
    public List<DO> getByDataScheme(String schemeKey) throws DataAccessException {
        Assert.notNull((Object)schemeKey, "schemeKey must not be null.");
        return super.list(new String[]{"DT_DS_KEY"}, (Object[])new String[]{schemeKey}, this.getClz());
    }

    @Override
    public List<DO> getByDataSchemeAndTypes(String schemeKey, DataTableType ... types) throws DataAccessException {
        if (null == types) {
            return this.getByDataScheme(schemeKey);
        }
        Assert.notNull((Object)schemeKey, "schemeKey must not be null.");
        String whereSql = String.format(" %s=? and %s in (%s) ", "DT_DS_KEY", "DT_TYPE", Arrays.stream(types).map(t -> "?").collect(Collectors.joining(",")));
        Object[] params = new Object[types.length + 1];
        params[0] = schemeKey;
        for (int i = 0; i < types.length; ++i) {
            params[i + 1] = types[i].getValue();
        }
        return super.list(whereSql, params, this.getClz());
    }

    @Override
    public List<DO> getByCondition(String schemeKey, String parentKey) throws DataAccessException {
        return super.list(new String[]{"DT_DS_KEY", "DT_DG_KEY"}, (Object[])new String[]{schemeKey, parentKey}, this.getClz());
    }

    @Override
    public DO getByCode(String code) {
        Assert.notNull((Object)code, "code must not be null.");
        return (DO)((DataTableDO)super.list(new String[]{"DT_CODE"}, (Object[])new String[]{code}, this.getClz()).stream().findFirst().orElse(null));
    }

    @Override
    public List<DO> getLatestDataTableByScheme(String scheme) {
        Assert.notNull((Object)scheme, "scheme must not be null.");
        return super.list("DT_DS_KEY = ? AND DT_UPDATE_TIME = (SELECT MAX(DT_UPDATE_TIME) FROM " + this.tablename + " WHERE " + "DT_DS_KEY" + " = ? )", new Object[]{scheme, scheme}, this.getClz());
    }

    @Override
    public Instant getLatestDataTableUpdateTime(String scheme) {
        if (!StringUtils.hasLength(scheme)) {
            String sql = "SELECT MAX(DT_UPDATE_TIME) FROM " + this.tablename;
            return (Instant)this.jdbcTemplate.queryForObject(sql, (RowMapper)new RowMapper<Instant>(){

                public Instant mapRow(ResultSet rs, int rowNum) throws SQLException {
                    return rs.getTimestamp(1).toInstant();
                }
            });
        }
        String sql = "SELECT MAX(DT_UPDATE_TIME) FROM " + this.tablename + " WHERE " + "DT_DS_KEY" + " =? ";
        return (Instant)this.jdbcTemplate.queryForObject(sql, (RowMapper)new RowMapper<Instant>(){

            public Instant mapRow(ResultSet rs, int rowNum) throws SQLException {
                return rs.getTimestamp(1).toInstant();
            }
        }, new Object[]{scheme});
    }

    @Override
    public void refreshUpdateTime(String key) {
        Assert.notNull((Object)key, "key must not be null.");
        String sql = String.format("UPDATE %s SET %s=? WHERE %s=? ", this.tablename, "DT_UPDATE_TIME", "DT_KEY");
        this.jdbcTemplate.update(sql, new Object[]{Timestamp.from(Instant.now()), key});
    }

    @Override
    public List<DO> searchBy(String scheme, String keyword, int type) {
        Assert.notNull((Object)keyword, "title must not be null.");
        List<Integer> types = this.getTypeValues(type);
        keyword = "%" + keyword.toUpperCase() + "%";
        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource();
        String sql = this.selectSQL + " where ( UPPER(" + "DT_TITLE" + ") like :keyword OR UPPER(" + "DT_CODE" + ") like :keyword ) AND " + "DT_TYPE" + " IN ( :types )";
        if (scheme != null) {
            sql = sql + " AND " + "DT_DS_KEY" + " = :scheme";
            sqlParameterSource.addValue("scheme", (Object)scheme);
        }
        sqlParameterSource.addValue("keyword", (Object)keyword);
        sqlParameterSource.addValue("types", types);
        RowMapper<DO> rowMapper = super.getRowMapper(this.getClz());
        this.logger.debug(sql);
        return this.namedParameterJdbcTemplate.query(sql, (SqlParameterSource)sqlParameterSource, rowMapper);
    }

    @Override
    public List<DO> searchBy(List<String> schemes, String keyword, int type) {
        Assert.notNull((Object)keyword, "title must not be null.");
        List<Integer> types = this.getTypeValues(type);
        keyword = "%" + keyword.toUpperCase() + "%";
        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource();
        String sql = this.selectSQL + " where ( UPPER(" + "DT_TITLE" + ") like :keyword OR UPPER(" + "DT_CODE" + ") like :keyword ) AND " + "DT_TYPE" + " IN ( :types )";
        if (!CollectionUtils.isEmpty(schemes)) {
            sql = sql + " AND " + "DT_DS_KEY" + " IN (:schemes) ";
            sqlParameterSource.addValue("schemes", schemes);
        }
        sqlParameterSource.addValue("keyword", (Object)keyword);
        sqlParameterSource.addValue("types", types);
        RowMapper<DO> rowMapper = super.getRowMapper(this.getClz());
        this.logger.debug(sql);
        return this.namedParameterJdbcTemplate.query(sql, (SqlParameterSource)sqlParameterSource, rowMapper);
    }

    private List<Integer> getTypeValues(int type) {
        DataTableType[] values = DataTableType.interestType((int)type);
        ArrayList<Integer> kinds = new ArrayList<Integer>(values.length);
        for (DataTableType value : values) {
            kinds.add(value.getValue());
        }
        if (kinds.isEmpty()) {
            throw new IllegalArgumentException("type \u4e0d\u5408\u6cd5");
        }
        return kinds;
    }

    @Override
    public List<DO> getBy(String scheme, String title, String dataGroupKey) {
        Assert.notNull((Object)title, "title must not be null.");
        Assert.notNull((Object)scheme, "scheme must not be null.");
        return super.list(new String[]{"DT_DS_KEY", "DT_TITLE", "DT_DG_KEY"}, new Object[]{scheme, title, dataGroupKey}, this.getClz());
    }

    @Override
    public void deleteByDataSchemeAndType(String scheme, int tableType) {
        Assert.notNull((Object)scheme, "scheme must not be null.");
        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource();
        sqlParameterSource.addValue("scheme", (Object)scheme);
        sqlParameterSource.addValue("types", this.getTypeValues(tableType));
        String sql = "DELETE " + this.tablename + " WHERE " + "DT_DS_KEY" + " = :scheme AND " + "DT_TYPE" + " IN ( :types )";
        this.logger.debug(sql);
        this.namedParameterJdbcTemplate.update(sql, (SqlParameterSource)sqlParameterSource);
    }

    public abstract Class<DO> getClz();
}

