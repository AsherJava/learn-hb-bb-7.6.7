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

import java.util.List;
import java.util.UUID;
import nr.single.para.compare.internal.defintion.CompareMapFieldDO;
import nr.single.para.compare.internal.defintion.dao.ICompareMapFieldDao;
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

public abstract class AbstractCompareMapFieldDao<DO extends CompareMapFieldDO>
extends BaseDao
implements ICompareMapFieldDao<DO> {
    private final Logger logger = LoggerFactory.getLogger(AbstractCompareMapFieldDao.class);
    @Autowired
    protected NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public String insert(DO compareMapFieldDO) throws DataAccessException {
        Assert.notNull(compareMapFieldDO, "compareMapFieldDO must not be null.");
        if (((CompareMapFieldDO)compareMapFieldDO).getKey() == null) {
            ((CompareMapFieldDO)compareMapFieldDO).setKey(UUID.randomUUID().toString());
        }
        super.insert(compareMapFieldDO);
        return ((CompareMapFieldDO)compareMapFieldDO).getKey();
    }

    @Override
    public void delete(String key) throws DataAccessException {
        Assert.notNull((Object)key, "key must not be null.");
        super.delete(key);
    }

    @Override
    public void update(DO compareMapFieldDO) throws DataAccessException {
        Assert.notNull(compareMapFieldDO, "compareMapFieldDO must not be null.");
        Assert.notNull((Object)((CompareMapFieldDO)compareMapFieldDO).getKey(), "dataSchemeKey must not be null.");
        super.update(compareMapFieldDO);
    }

    @Override
    public DO get(String key) throws DataAccessException {
        Assert.notNull((Object)key, "key must not be null.");
        return (DO)((CompareMapFieldDO)super.getByKey(key, this.getClz()));
    }

    @Override
    public DO getByFieldKey(String fieldKey) throws DataAccessException {
        Assert.notNull((Object)fieldKey, "fieldKey must not be null.");
        return (DO)((CompareMapFieldDO)super.list(new String[]{"MF_FIELDKEY"}, new Object[]{fieldKey}, this.getClz()).stream().findFirst().orElse(null));
    }

    @Override
    public List<DO> getByDataSchemeKey(String dataSchemeKey) throws DataAccessException {
        Assert.notNull((Object)dataSchemeKey, "dataScchemeKey must not be null.");
        return super.list(new String[]{"MF_DATASCHEME_KEY"}, new Object[]{dataSchemeKey}, this.getClz());
    }

    @Override
    public List<DO> getByTitleInDataScheme(String dataSchemeKey, String matchTitle) throws DataAccessException {
        Assert.notNull((Object)dataSchemeKey, "dataScchemeKey must not be null.");
        Assert.notNull((Object)matchTitle, "dataScchemeKey must not be null.");
        return super.list(new String[]{"MF_DATASCHEME_KEY", "MF_MATCHTITLE"}, new Object[]{dataSchemeKey, matchTitle}, this.getClz());
    }

    @Override
    public void batchInsert(List<DO> compareMapFieldDO) throws DataAccessException {
        Assert.notNull(compareMapFieldDO, "compareMapFieldDO must not be null.");
        for (CompareMapFieldDO schemeDO : compareMapFieldDO) {
            Assert.notNull((Object)schemeDO, "Collection should not contain null.");
            if (schemeDO.getKey() != null) continue;
            schemeDO.setKey(UUID.randomUUID().toString());
        }
        super.insert(compareMapFieldDO.toArray());
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
    public void batchUpdate(List<DO> compareMapFieldDO) throws DataAccessException {
        Assert.notNull(compareMapFieldDO, "compareMapFieldDO must not be null.");
        for (CompareMapFieldDO schemeDO : compareMapFieldDO) {
            Assert.notNull((Object)schemeDO, "Collection should not contain null.");
            Assert.notNull((Object)schemeDO.getKey(), "dataSchemeKey must not be null.");
        }
        super.update(compareMapFieldDO.toArray());
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

