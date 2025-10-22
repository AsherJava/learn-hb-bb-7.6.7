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
package nr.single.para.compare.internal.defintion.dao.impl;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import nr.single.para.compare.definition.common.CompareDataType;
import nr.single.para.compare.internal.defintion.CompareDataDO;
import nr.single.para.compare.internal.defintion.dao.ICompareDataDao;
import nr.single.para.compare.internal.defintion.dao.impl.BaseDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.util.Assert;

public abstract class AbstractCompareDataDao<DO extends CompareDataDO>
extends BaseDao
implements ICompareDataDao<DO> {
    protected final Logger logger = LoggerFactory.getLogger(AbstractCompareDataDao.class);
    @Autowired
    protected NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public String insert(DO compareInfoDO) throws DataAccessException {
        Assert.notNull(compareInfoDO, "compareInfoDO must not be null.");
        if (((CompareDataDO)compareInfoDO).getKey() == null) {
            ((CompareDataDO)compareInfoDO).setKey(UUID.randomUUID().toString());
        }
        if (((CompareDataDO)compareInfoDO).getUpdateTime() == null) {
            ((CompareDataDO)compareInfoDO).setUpdateTime(Instant.now());
        }
        super.insert(compareInfoDO);
        return ((CompareDataDO)compareInfoDO).getKey();
    }

    @Override
    public void delete(String key) throws DataAccessException {
        Assert.notNull((Object)key, "key must not be null.");
        super.delete(key);
    }

    @Override
    public void deleteByInfoKey(String infoKey) throws DataAccessException {
        Assert.notNull((Object)infoKey, "infoKey must not be null.");
        super.deleteBy(new String[]{"CD_INFOKEY"}, new Object[]{infoKey});
    }

    @Override
    public void update(DO compareInfoDO) throws DataAccessException {
        Assert.notNull(compareInfoDO, "compareInfoDO must not be null.");
        Assert.notNull((Object)((CompareDataDO)compareInfoDO).getKey(), "dataSchemeKey must not be null.");
        super.update(compareInfoDO);
    }

    @Override
    public DO get(String key) throws DataAccessException {
        Assert.notNull((Object)key, "key must not be null.");
        return (DO)((CompareDataDO)super.getByKey(key, this.getClz()));
    }

    @Override
    public List<DO> getByInfoKey(String infoKey) throws DataAccessException {
        Assert.notNull((Object)infoKey, "infoKey must not be null.");
        return super.list(new String[]{"CD_INFOKEY"}, new Object[]{infoKey}, this.getClz());
    }

    @Override
    public List<DO> getByDataTypeInInfo(String infoKey, CompareDataType dataType) throws DataAccessException {
        Assert.notNull((Object)infoKey, "infoKey must not be null.");
        Assert.notNull((Object)dataType, "dataType must not be null.");
        return super.list(new String[]{"CD_INFOKEY", "CD_DATATYPE"}, new Object[]{infoKey, dataType}, this.getClz());
    }

    @Override
    public List<DO> getByParentInInfo(String infoKey, String parentKey) throws DataAccessException {
        Assert.notNull((Object)infoKey, "infoKey must not be null.");
        Assert.notNull((Object)parentKey, "dataType must not be null.");
        return super.list(new String[]{"CD_INFOKEY"}, new Object[]{infoKey}, this.getClz());
    }

    @Override
    public void batchInsert(List<DO> compareInfoDO) throws DataAccessException {
        Assert.notNull(compareInfoDO, "compareInfoDO must not be null.");
        for (CompareDataDO schemeDO : compareInfoDO) {
            Assert.notNull((Object)schemeDO, "Collection should not contain null.");
            if (schemeDO.getKey() == null) {
                schemeDO.setKey(UUID.randomUUID().toString());
            }
            if (schemeDO.getUpdateTime() != null) continue;
            schemeDO.setUpdateTime(Instant.now());
        }
        super.insert(compareInfoDO.toArray());
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
    public void batchUpdate(List<DO> compareInfoDO) throws DataAccessException {
        Assert.notNull(compareInfoDO, "compareInfoDO must not be null.");
        for (CompareDataDO schemeDO : compareInfoDO) {
            Assert.notNull((Object)schemeDO, "Collection should not contain null.");
            Assert.notNull((Object)schemeDO.getKey(), "dataSchemeKey must not be null.");
        }
        super.update(compareInfoDO.toArray());
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
        String sql = this.selectSQL + " where " + "CI_KEY" + " in (:keys) ";
        this.logger.debug(sql);
        return this.namedParameterJdbcTemplate.query(sql, (SqlParameterSource)sqlParameterSource, rowMapper);
    }

    @Override
    public List<DO> getAll() throws DataAccessException {
        return super.list(this.getClz());
    }

    public abstract Class<DO> getClz();
}

