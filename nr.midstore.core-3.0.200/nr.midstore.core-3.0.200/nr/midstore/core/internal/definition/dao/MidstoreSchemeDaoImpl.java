/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.dao.DataAccessException
 *  org.springframework.jdbc.core.RowMapper
 *  org.springframework.jdbc.core.namedparam.MapSqlParameterSource
 *  org.springframework.jdbc.core.namedparam.SqlParameterSource
 */
package nr.midstore.core.internal.definition.dao;

import java.util.List;
import nr.midstore.core.internal.definition.MidstoreSchemeDO;
import nr.midstore.core.internal.definition.dao.AbstractMidstoreDataDao;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

@Repository
public class MidstoreSchemeDaoImpl
extends AbstractMidstoreDataDao<MidstoreSchemeDO> {
    @Override
    public Class<MidstoreSchemeDO> getClz() {
        return MidstoreSchemeDO.class;
    }

    @Override
    public List<MidstoreSchemeDO> batchGet(List<String> keys) throws DataAccessException {
        Assert.notNull(keys, "keys must not be null.");
        for (String key : keys) {
            Assert.notNull((Object)key, "Collection should not contain null.");
        }
        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource();
        sqlParameterSource.addValue("keys", keys);
        RowMapper<MidstoreSchemeDO> rowMapper = super.getRowMapper(this.getClz());
        String sql = this.selectSQL + " where " + "MDS_KEY" + " in (:keys) ";
        this.logger.debug(sql);
        return this.namedParameterJdbcTemplate.query(sql, (SqlParameterSource)sqlParameterSource, rowMapper);
    }

    @Override
    public List<MidstoreSchemeDO> getByParentKey(String parentKey) throws DataAccessException {
        Assert.notNull((Object)parentKey, "parentKey must not be null.");
        return super.list(new String[]{"MDS_GROUPKEY"}, new Object[]{parentKey}, this.getClz());
    }

    @Override
    public void deleteByParentKey(String parentKey) throws DataAccessException {
        Assert.notNull((Object)parentKey, "parentKey must not be null.");
        super.deleteBy(new String[]{"MDS_GROUPKEY"}, new Object[]{parentKey});
    }

    @Override
    public List<MidstoreSchemeDO> getBySchemeKey(String schemeKey) throws DataAccessException {
        Assert.notNull((Object)schemeKey, "schemeKey must not be null.");
        return super.list(new String[]{"MDS_KEY"}, new Object[]{schemeKey}, this.getClz());
    }

    @Override
    public void deleteBySchemeKey(String schemeKey) throws DataAccessException {
        Assert.notNull((Object)schemeKey, "schemeKey must not be null.");
        super.deleteBy(new String[]{"MDS_KEY"}, new Object[]{schemeKey});
    }

    @Override
    public List<MidstoreSchemeDO> getByCode(String code) throws DataAccessException {
        Assert.notNull((Object)code, "code must not be null.");
        return super.list(new String[]{"MDS_CODE"}, new Object[]{code}, this.getClz());
    }
}

