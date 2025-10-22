/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.context.NpContextHolder
 *  org.springframework.dao.DataAccessException
 *  org.springframework.jdbc.core.RowMapper
 *  org.springframework.jdbc.core.namedparam.MapSqlParameterSource
 *  org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
 *  org.springframework.jdbc.core.namedparam.SqlParameterSource
 */
package com.jiuqi.nr.datascheme.internal.dao.impl;

import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.nr.datascheme.internal.dao.IDataSchemeDao;
import com.jiuqi.nr.datascheme.internal.dao.impl.BaseDao;
import com.jiuqi.nr.datascheme.internal.entity.DataSchemeDO;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

public abstract class AbstractDataSchemeDao<DO extends DataSchemeDO>
extends BaseDao
implements IDataSchemeDao<DO> {
    private final Logger logger = LoggerFactory.getLogger(AbstractDataSchemeDao.class);
    @Autowired
    protected NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public String insert(DO dataSchemeDO) throws DataAccessException {
        Assert.notNull(dataSchemeDO, "dataSchemeDO must not be null.");
        if (((DataSchemeDO)dataSchemeDO).getKey() == null) {
            ((DataSchemeDO)dataSchemeDO).setKey(UUID.randomUUID().toString());
        }
        if (((DataSchemeDO)dataSchemeDO).getUpdateTime() == null) {
            ((DataSchemeDO)dataSchemeDO).setUpdateTime(Instant.now());
        }
        this.setCreator(dataSchemeDO);
        super.insert(dataSchemeDO);
        return ((DataSchemeDO)dataSchemeDO).getKey();
    }

    private void setCreator(DO dataSchemeDO) {
        String userName = NpContextHolder.getContext().getUserName();
        if (StringUtils.hasLength(userName)) {
            ((DataSchemeDO)dataSchemeDO).setCreator(userName);
        }
    }

    @Override
    public void delete(String key) throws DataAccessException {
        Assert.notNull((Object)key, "key must not be null.");
        super.delete(key);
    }

    @Override
    public void update(DO dataSchemeDO) throws DataAccessException {
        Assert.notNull(dataSchemeDO, "dataSchemeDO must not be null.");
        Assert.notNull((Object)((DataSchemeDO)dataSchemeDO).getKey(), "dataSchemeKey must not be null.");
        if (((DataSchemeDO)dataSchemeDO).getUpdateTime() == null) {
            ((DataSchemeDO)dataSchemeDO).setUpdateTime(Instant.now());
        }
        super.update(dataSchemeDO);
    }

    @Override
    public DO get(String key) throws DataAccessException {
        Assert.notNull((Object)key, "key must not be null.");
        return (DO)((DataSchemeDO)super.getByKey(key, this.getClz()));
    }

    @Override
    public DO getByCode(String code) throws DataAccessException {
        Assert.notNull((Object)code, "code must not be null.");
        return (DO)((DataSchemeDO)super.list(new String[]{"DS_CODE"}, new Object[]{code}, this.getClz()).stream().findFirst().orElse(null));
    }

    @Override
    public void batchInsert(List<DO> dataSchemeDO) throws DataAccessException {
        Assert.notNull(dataSchemeDO, "dataSchemeDO must not be null.");
        for (DataSchemeDO schemeDO : dataSchemeDO) {
            Assert.notNull((Object)schemeDO, "Collection should not contain null.");
            if (schemeDO.getKey() == null) {
                schemeDO.setKey(UUID.randomUUID().toString());
            }
            if (schemeDO.getUpdateTime() == null) {
                schemeDO.setUpdateTime(Instant.now());
            }
            this.setCreator(schemeDO);
        }
        super.insert(dataSchemeDO.toArray());
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
    public void batchUpdate(List<DO> dataSchemeDO) throws DataAccessException {
        Assert.notNull(dataSchemeDO, "dataSchemeDO must not be null.");
        for (DataSchemeDO schemeDO : dataSchemeDO) {
            Assert.notNull((Object)schemeDO, "Collection should not contain null.");
            Assert.notNull((Object)schemeDO.getKey(), "dataSchemeKey must not be null.");
            if (schemeDO.getUpdateTime() != null) continue;
            schemeDO.setUpdateTime(Instant.now());
        }
        super.update(dataSchemeDO.toArray());
    }

    @Override
    public List<DO> batchGet(List<String> keys) throws DataAccessException {
        Assert.notNull(keys, "keys must not be null.");
        for (String key : keys) {
            Assert.notNull((Object)key, "Collection should not contain null.");
        }
        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource();
        sqlParameterSource.addValue("keys", keys);
        RowMapper<DO> rowMapper = super.getRowMapper(this.getClz());
        String sql = this.selectSQL + " where " + "DS_KEY" + " in (:keys) ";
        this.logger.debug(sql);
        return this.namedParameterJdbcTemplate.query(sql, (SqlParameterSource)sqlParameterSource, rowMapper);
    }

    @Override
    public List<DO> getAll() throws DataAccessException {
        return super.list(this.getClz());
    }

    @Override
    public List<DO> getByParent(String parent) throws DataAccessException {
        Assert.notNull((Object)parent, "parent must not be null.");
        return super.list(new String[]{"DS_DG_KEY"}, new Object[]{parent}, this.getClz());
    }

    @Override
    public DO getByPrefix(String prefix) throws DataAccessException {
        Assert.notNull((Object)prefix, "prefix must not be null.");
        return (DO)((DataSchemeDO)super.list(new String[]{"DS_PREFIX"}, new Object[]{prefix}, this.getClz()).stream().findFirst().orElse(null));
    }

    @Override
    public List<DO> getBy0(String code, String parent) throws DataAccessException {
        Assert.notNull((Object)code, "code must not be null.");
        Assert.notNull((Object)parent, "parent must not be null.");
        return super.list(new String[]{"DS_CODE", "DS_DG_KEY"}, new Object[]{code, parent}, this.getClz());
    }

    @Override
    public List<DO> getBy(String title, String parent) throws DataAccessException {
        Assert.notNull((Object)title, "title must not be null.");
        Assert.notNull((Object)parent, "parent must not be null.");
        return super.list(new String[]{"DS_TITLE", "DS_DG_KEY"}, new Object[]{title, parent}, this.getClz());
    }

    @Override
    public DO getByBizCode(String bizCode) throws DataAccessException {
        Assert.notNull((Object)bizCode, "bizCode must not be null.");
        return (DO)((DataSchemeDO)super.list(new String[]{"DS_BIZCODE"}, new Object[]{bizCode}, this.getClz()).stream().findFirst().orElse(null));
    }

    @Override
    public List<DO> searchBy(String keyword) {
        Assert.notNull((Object)keyword, "title must not be null.");
        keyword = "%" + keyword.toUpperCase() + "%";
        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource();
        sqlParameterSource.addValue("keyword", (Object)keyword);
        RowMapper<DO> rowMapper = super.getRowMapper(this.getClz());
        String sql = this.selectSQL + " WHERE UPPER(" + "DS_TITLE" + ") LIKE :keyword OR UPPER(" + "DS_CODE" + ") LIKE :keyword";
        this.logger.debug(sql);
        return this.namedParameterJdbcTemplate.query(sql, (SqlParameterSource)sqlParameterSource, rowMapper);
    }

    public abstract Class<DO> getClz();
}

