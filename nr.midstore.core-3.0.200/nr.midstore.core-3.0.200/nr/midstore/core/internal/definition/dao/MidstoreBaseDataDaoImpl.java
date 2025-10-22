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
import nr.midstore.core.internal.definition.MidstoreBaseDataDO;
import nr.midstore.core.internal.definition.dao.AbstractMidstoreDataDao;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

@Repository
public class MidstoreBaseDataDaoImpl
extends AbstractMidstoreDataDao<MidstoreBaseDataDO> {
    @Override
    public Class<MidstoreBaseDataDO> getClz() {
        return MidstoreBaseDataDO.class;
    }

    @Override
    public List<MidstoreBaseDataDO> batchGet(List<String> keys) throws DataAccessException {
        Assert.notNull(keys, "keys must not be null.");
        for (String key : keys) {
            Assert.notNull((Object)key, "Collection should not contain null.");
        }
        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource();
        sqlParameterSource.addValue("keys", keys);
        RowMapper<MidstoreBaseDataDO> rowMapper = super.getRowMapper(this.getClz());
        String sql = this.selectSQL + " where " + "MDB_KEY" + " in (:keys) ";
        this.logger.debug(sql);
        return this.namedParameterJdbcTemplate.query(sql, (SqlParameterSource)sqlParameterSource, rowMapper);
    }

    @Override
    public List<MidstoreBaseDataDO> getByParentKey(String schemeKey, String parentKey) throws DataAccessException {
        return null;
    }

    @Override
    public void deleteByParentKey(String schemeKey, String parentKey) throws DataAccessException {
    }

    @Override
    public List<MidstoreBaseDataDO> getBySchemeKey(String schemeKey) throws DataAccessException {
        Assert.notNull((Object)schemeKey, "parentKey must not be null.");
        return super.list(new String[]{"MDB_SCHEMEKEY"}, new Object[]{schemeKey}, this.getClz());
    }

    @Override
    public void deleteBySchemeKey(String schemeKey) throws DataAccessException {
        Assert.notNull((Object)schemeKey, "schemeKey must not be null.");
        super.deleteBy(new String[]{"MDB_SCHEMEKEY"}, new Object[]{schemeKey});
    }
}

