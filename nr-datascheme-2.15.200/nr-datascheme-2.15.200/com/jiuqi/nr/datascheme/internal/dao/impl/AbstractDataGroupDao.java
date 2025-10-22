/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.type.DataGroupKind
 *  org.springframework.dao.DataAccessException
 *  org.springframework.jdbc.core.RowMapper
 *  org.springframework.jdbc.core.namedparam.MapSqlParameterSource
 *  org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
 *  org.springframework.jdbc.core.namedparam.SqlParameterSource
 */
package com.jiuqi.nr.datascheme.internal.dao.impl;

import com.jiuqi.nr.datascheme.api.type.DataGroupKind;
import com.jiuqi.nr.datascheme.internal.dao.IDataGroupDao;
import com.jiuqi.nr.datascheme.internal.dao.impl.BaseDao;
import com.jiuqi.nr.datascheme.internal.entity.DataGroupDO;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
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
import org.springframework.util.CollectionUtils;

public abstract class AbstractDataGroupDao<DO extends DataGroupDO>
extends BaseDao
implements IDataGroupDao<DO> {
    @Autowired
    protected NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private static final Logger logger = LoggerFactory.getLogger(AbstractDataGroupDao.class);

    @Override
    public String insert(DO dataGroupDO) throws DataAccessException {
        Assert.notNull(dataGroupDO, "dataGroupDO must not be null.");
        if (((DataGroupDO)dataGroupDO).getKey() == null) {
            ((DataGroupDO)dataGroupDO).setKey(UUID.randomUUID().toString());
        }
        if (((DataGroupDO)dataGroupDO).getUpdateTime() == null) {
            ((DataGroupDO)dataGroupDO).setUpdateTime(Instant.now());
        }
        super.insert(dataGroupDO);
        return ((DataGroupDO)dataGroupDO).getKey();
    }

    @Override
    public void delete(String key) throws DataAccessException {
        Assert.notNull((Object)key, "key must not be null.");
        super.delete(key);
    }

    @Override
    public void deleteByDataScheme(String schemeKey) throws DataAccessException {
        Assert.notNull((Object)schemeKey, "schemeKey must not be null.");
        super.deleteBy(new String[]{"DG_DS_KEY"}, new String[]{schemeKey});
    }

    @Override
    public void update(DO dataGroupDO) throws DataAccessException {
        Assert.notNull(dataGroupDO, "dataGroupDO must not be null.");
        Assert.notNull((Object)((DataGroupDO)dataGroupDO).getKey(), "dataGroupKey must not be null.");
        if (((DataGroupDO)dataGroupDO).getUpdateTime() == null) {
            ((DataGroupDO)dataGroupDO).setUpdateTime(Instant.now());
        }
        super.update(dataGroupDO);
    }

    @Override
    public DO get(String key) throws DataAccessException {
        Assert.notNull((Object)key, "key must not be null.");
        return (DO)((DataGroupDO)super.getByKey(key, this.getClz()));
    }

    @Override
    public String[] batchInsert(List<DO> dataGroupDO) throws DataAccessException {
        Assert.notNull(dataGroupDO, "dataGroupDO must not be null.");
        String[] keys = new String[dataGroupDO.size()];
        for (int i = 0; i < dataGroupDO.size(); ++i) {
            DataGroupDO dataGroup = (DataGroupDO)dataGroupDO.get(i);
            Assert.notNull((Object)dataGroup, "Collection should not contain null.");
            if (dataGroup.getKey() == null) {
                dataGroup.setKey(UUID.randomUUID().toString());
            }
            if (dataGroup.getUpdateTime() == null) {
                dataGroup.setUpdateTime(Instant.now());
            }
            keys[i] = dataGroup.getKey();
        }
        super.insert(dataGroupDO.toArray());
        return keys;
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
    public void batchUpdate(List<DO> dataGroupDO) throws DataAccessException {
        Assert.notNull(dataGroupDO, "dataGroupDO must not be null.");
        for (DataGroupDO dataGroup : dataGroupDO) {
            Assert.notNull((Object)dataGroup, "Collection should not contain null.");
            Assert.notNull((Object)dataGroup.getKey(), "dataGroupKey must not be null.");
            if (dataGroup.getUpdateTime() != null) continue;
            dataGroup.setUpdateTime(Instant.now());
        }
        super.update(dataGroupDO.toArray());
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
        String sql = this.selectSQL + " where " + "DG_KEY" + " in (:keys) ";
        logger.debug(sql);
        return this.namedParameterJdbcTemplate.query(sql, (SqlParameterSource)sqlParameterSource, rowMapper);
    }

    @Override
    public List<DO> getAll() throws DataAccessException {
        return super.list(this.getClz());
    }

    @Override
    public List<DO> getByKind(int kind) throws DataAccessException {
        return super.list(new String[]{"DG_KIND"}, new Object[]{kind}, this.getClz());
    }

    @Override
    public List<DO> getByParent(String parentKey) throws DataAccessException {
        Assert.notNull((Object)parentKey, "parentKey must not be null.");
        return super.list(new String[]{"DG_PARENT_KEY"}, (Object[])new String[]{parentKey}, this.getClz());
    }

    @Override
    public List<DO> getByScheme(String dataSchemeKey) throws DataAccessException {
        Assert.notNull((Object)dataSchemeKey, "dataSchemeKey must not be null.");
        return super.list(new String[]{"DG_DS_KEY"}, (Object[])new String[]{dataSchemeKey}, this.getClz());
    }

    @Override
    public List<DO> getByCondition(String dataSchemeKey, String parentKey) throws DataAccessException {
        return super.list(new String[]{"DG_DS_KEY", "DG_PARENT_KEY"}, (Object[])new String[]{dataSchemeKey, parentKey}, this.getClz());
    }

    @Override
    public List<DO> searchBy(String scheme, String title, int kind) {
        Assert.notNull((Object)title, "title must not be null.");
        List<Integer> kinds = this.getKindValues(kind);
        title = "%" + title.toUpperCase() + "%";
        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource();
        if (scheme != null) {
            sqlParameterSource.addValue("scheme", (Object)scheme);
        }
        sqlParameterSource.addValue("title", (Object)title);
        sqlParameterSource.addValue("kinds", kinds);
        RowMapper<DO> rowMapper = super.getRowMapper(this.getClz());
        if (scheme == null) {
            String sql = this.selectSQL + " WHERE UPPER(" + "DG_TITLE" + ") LIKE :title AND " + "DG_KIND" + " IN ( :kinds )";
            logger.debug(sql);
            return this.namedParameterJdbcTemplate.query(sql, (SqlParameterSource)sqlParameterSource, rowMapper);
        }
        String sql = this.selectSQL + " WHERE " + "DG_DS_KEY" + " = :scheme AND UPPER(" + "DG_TITLE" + ") LIKE :title AND " + "DG_KIND" + " IN ( :kinds )";
        logger.debug(sql);
        return this.namedParameterJdbcTemplate.query(sql, (SqlParameterSource)sqlParameterSource, rowMapper);
    }

    @Override
    public List<DO> searchBy(List<String> schemes, String title, int kind) {
        Assert.notNull((Object)title, "title must not be null.");
        List<Integer> kinds = this.getKindValues(kind);
        title = "%" + title.toUpperCase() + "%";
        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource();
        sqlParameterSource.addValue("title", (Object)title);
        sqlParameterSource.addValue("kinds", kinds);
        RowMapper<DO> rowMapper = super.getRowMapper(this.getClz());
        if (CollectionUtils.isEmpty(schemes)) {
            String sql = this.selectSQL + " WHERE UPPER(" + "DG_TITLE" + ") LIKE :title AND " + "DG_KIND" + " IN ( :kinds )";
            logger.debug(sql);
            return this.namedParameterJdbcTemplate.query(sql, (SqlParameterSource)sqlParameterSource, rowMapper);
        }
        sqlParameterSource.addValue("schemes", schemes);
        String sql = this.selectSQL + " WHERE " + "DG_DS_KEY" + " IN (:schemes) AND UPPER(" + "DG_TITLE" + ") LIKE :title AND " + "DG_KIND" + " IN ( :kinds )";
        logger.debug(sql);
        return this.namedParameterJdbcTemplate.query(sql, (SqlParameterSource)sqlParameterSource, rowMapper);
    }

    private List<Integer> getKindValues(int kind) {
        DataGroupKind[] dataGroupKinds = DataGroupKind.interestType((int)kind);
        ArrayList<Integer> kinds = new ArrayList<Integer>(dataGroupKinds.length);
        for (DataGroupKind value : dataGroupKinds) {
            kinds.add(value.getValue());
        }
        if (kinds.isEmpty()) {
            throw new IllegalArgumentException("type \u4e0d\u5408\u6cd5");
        }
        return kinds;
    }

    @Override
    public List<DO> getBy(String scheme, String title, String parentKey) throws DataAccessException {
        Assert.notNull((Object)title, "title must not be null.");
        return super.list(new String[]{"DG_DS_KEY", "DG_TITLE", "DG_PARENT_KEY"}, new Object[]{scheme, title, parentKey}, this.getClz());
    }

    public abstract Class<DO> getClz();
}

