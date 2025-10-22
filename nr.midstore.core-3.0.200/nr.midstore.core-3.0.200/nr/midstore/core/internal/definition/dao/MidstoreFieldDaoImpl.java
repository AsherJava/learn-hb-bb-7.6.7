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
import nr.midstore.core.internal.definition.MidstoreFieldDO;
import nr.midstore.core.internal.definition.dao.AbstractMidstoreDataDao;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

@Repository
public class MidstoreFieldDaoImpl
extends AbstractMidstoreDataDao<MidstoreFieldDO> {
    @Override
    public Class<MidstoreFieldDO> getClz() {
        return MidstoreFieldDO.class;
    }

    @Override
    public List<MidstoreFieldDO> getByParentKey(String schemeKey, String parentKey) throws DataAccessException {
        Assert.notNull((Object)schemeKey, "schemeKey must not be null.");
        Assert.notNull((Object)parentKey, "parentKey must not be null.");
        return super.list(new String[]{"MDF_SCHEMEKEY", "MDF_SRC_TABLEKEY"}, new Object[]{schemeKey, parentKey}, this.getClz());
    }

    @Override
    public void deleteByParentKey(String schemeKey, String parentKey) throws DataAccessException {
        Assert.notNull((Object)schemeKey, "schemeKey must not be null.");
        Assert.notNull((Object)parentKey, "parentKey must not be null.");
        super.deleteBy(new String[]{"MDF_SCHEMEKEY", "MDF_SRC_TABLEKEY"}, new Object[]{schemeKey, parentKey});
    }

    @Override
    public List<MidstoreFieldDO> batchGet(List<String> keys) throws DataAccessException {
        Assert.notNull(keys, "keys must not be null.");
        for (String key : keys) {
            Assert.notNull((Object)key, "Collection should not contain null.");
        }
        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource();
        sqlParameterSource.addValue("keys", keys);
        RowMapper<MidstoreFieldDO> rowMapper = super.getRowMapper(this.getClz());
        String sql = this.selectSQL + " where " + "MDF_KEY" + " in (:keys) ";
        this.logger.debug(sql);
        return this.namedParameterJdbcTemplate.query(sql, (SqlParameterSource)sqlParameterSource, rowMapper);
    }

    @Override
    public List<MidstoreFieldDO> getBySchemeKey(String schemeKey) throws DataAccessException {
        Assert.notNull((Object)schemeKey, "schemeKey must not be null.");
        return super.list(new String[]{"MDF_SCHEMEKEY"}, new Object[]{schemeKey}, this.getClz());
    }

    @Override
    public void deleteBySchemeKey(String schemeKey) throws DataAccessException {
        Assert.notNull((Object)schemeKey, "schemeKey must not be null.");
        super.deleteBy(new String[]{"MDF_SCHEMEKEY"}, new Object[]{schemeKey});
    }
}

