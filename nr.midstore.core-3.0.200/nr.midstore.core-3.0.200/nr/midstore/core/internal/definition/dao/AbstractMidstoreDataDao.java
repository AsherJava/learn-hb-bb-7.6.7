/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.dao.DataAccessException
 *  org.springframework.jdbc.core.RowMapper
 *  org.springframework.jdbc.core.namedparam.MapSqlParameterSource
 *  org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
 *  org.springframework.jdbc.core.namedparam.SqlParameterSource
 */
package nr.midstore.core.internal.definition.dao;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import nr.midstore.core.definition.dao.IMidstoreDataDao;
import nr.midstore.core.definition.db.BaseDao;
import nr.midstore.core.internal.definition.MidstoreDataDO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.util.Assert;

public abstract class AbstractMidstoreDataDao<DO extends MidstoreDataDO>
extends BaseDao
implements IMidstoreDataDao<DO> {
    protected final Logger logger = LoggerFactory.getLogger(AbstractMidstoreDataDao.class);
    @Autowired
    protected NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public String insert(DO MidstoreDataDO2) throws DataAccessException {
        Assert.notNull(MidstoreDataDO2, "MidstoreDataDO must not be null.");
        if (((MidstoreDataDO)MidstoreDataDO2).getKey() == null) {
            ((MidstoreDataDO)MidstoreDataDO2).setKey(UUID.randomUUID().toString());
        }
        if (((MidstoreDataDO)MidstoreDataDO2).getUpdateTime() == null) {
            ((MidstoreDataDO)MidstoreDataDO2).setUpdateTime(Instant.now());
        }
        super.insert(MidstoreDataDO2);
        return ((MidstoreDataDO)MidstoreDataDO2).getKey();
    }

    @Override
    public void delete(String key) throws DataAccessException {
        Assert.notNull((Object)key, "key must not be null.");
        super.delete(key);
    }

    @Override
    public void update(DO MidstoreDataDO2) throws DataAccessException {
        Assert.notNull(MidstoreDataDO2, "MidstoreDataDO must not be null.");
        Assert.notNull((Object)((MidstoreDataDO)MidstoreDataDO2).getKey(), "MidstoreSchemeKey must not be null.");
        super.update(MidstoreDataDO2);
    }

    @Override
    public DO get(String key) throws DataAccessException {
        Assert.notNull((Object)key, "key must not be null.");
        return (DO)((MidstoreDataDO)super.getByKey(key, this.getClz()));
    }

    @Override
    public void batchInsert(List<DO> MidstoreDataDO2) throws DataAccessException {
        Assert.notNull(MidstoreDataDO2, "MidstoreDataDO must not be null.");
        for (MidstoreDataDO schemeDO : MidstoreDataDO2) {
            Assert.notNull((Object)schemeDO, "Collection should not contain null.");
            if (schemeDO.getKey() == null) {
                schemeDO.setKey(UUID.randomUUID().toString());
            }
            if (schemeDO.getUpdateTime() != null) continue;
            schemeDO.setUpdateTime(Instant.now());
        }
        super.insert(MidstoreDataDO2.toArray());
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
    public void batchUpdate(List<DO> MidstoreDataDO2) throws DataAccessException {
        Assert.notNull(MidstoreDataDO2, "MidstoreDataDO must not be null.");
        for (MidstoreDataDO schemeDO : MidstoreDataDO2) {
            Assert.notNull((Object)schemeDO, "Collection should not contain null.");
            Assert.notNull((Object)schemeDO.getKey(), "dataKey must not be null.");
        }
        super.update(MidstoreDataDO2.toArray());
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
        String sql = this.selectSQL + " where " + "MDD_KEY" + " in (:keys) ";
        this.logger.debug(sql);
        return this.namedParameterJdbcTemplate.query(sql, (SqlParameterSource)sqlParameterSource, rowMapper);
    }

    @Override
    public List<DO> getAll() throws DataAccessException {
        return super.list(this.getClz());
    }

    @Override
    public List<DO> getByCode(String code) throws DataAccessException {
        return null;
    }

    @Override
    public List<DO> getByParentKey(String schemeKey, String parentKey) throws DataAccessException {
        return null;
    }

    @Override
    public void deleteByParentKey(String schemeKey, String parentKey) throws DataAccessException {
    }

    @Override
    public List<DO> getBySchemeKey(String schemeKey) throws DataAccessException {
        return null;
    }

    @Override
    public void deleteBySchemeKey(String schemeKey) throws DataAccessException {
    }

    @Override
    public List<DO> getByParentKey(String parentKey) throws DataAccessException {
        return null;
    }

    @Override
    public void deleteByParentKey(String parentKey) throws DataAccessException {
    }

    @Override
    public List<DO> getByField(String fieldName, String code) throws DataAccessException {
        Assert.notNull((Object)code, "code must not be null.");
        Assert.notNull((Object)fieldName, "fieldName must not be null.");
        return super.list(new String[]{fieldName}, new Object[]{code}, this.getClz());
    }

    public abstract Class<DO> getClz();
}

