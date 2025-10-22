/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.internal.dao.impl.BaseDao
 *  org.springframework.dao.DataAccessException
 *  org.springframework.jdbc.core.namedparam.MapSqlParameterSource
 *  org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
 *  org.springframework.jdbc.core.namedparam.SqlParameterSource
 */
package com.jiuqi.nr.dataresource.dao.impl;

import com.jiuqi.nr.dataresource.dao.IDimAttributeDao;
import com.jiuqi.nr.dataresource.entity.DimAttributeDO;
import com.jiuqi.nr.datascheme.internal.dao.impl.BaseDao;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class DimAttributeDaoImpl
extends BaseDao
implements IDimAttributeDao {
    @Autowired
    protected NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final Logger logger = LoggerFactory.getLogger(DimAttributeDaoImpl.class);

    @Override
    public void insert(List<DimAttributeDO> entity) throws DataAccessException {
        Assert.notNull(entity, "entity must not be null.");
        for (DimAttributeDO treeDO : entity) {
            Assert.notNull((Object)treeDO, "Collection should not contain null.");
        }
        super.insert(entity.toArray());
    }

    @Override
    public void update(List<DimAttributeDO> entity) throws DataAccessException {
        Assert.notNull(entity, "entity must not be null.");
        ArrayList<Object[]> args = new ArrayList<Object[]>(entity.size());
        String sql = this.updateSQL + " where " + "DM_KEY" + " = ? AND " + "DD_KEY" + " =?";
        for (DimAttributeDO dimAttributeDO : entity) {
            Object[] arg = new Object[4];
            args.add(arg);
            arg[0] = dimAttributeDO.isHidden();
            arg[1] = dimAttributeDO.getOrder();
            arg[2] = dimAttributeDO.getKey();
            arg[3] = dimAttributeDO.getResourceDefineKey();
        }
        this.logger.debug(sql);
        this.namedParameterJdbcTemplate.getJdbcTemplate().batchUpdate(sql, args);
    }

    @Override
    public void delete(String resourceDefineKey, String ... dimKey) throws DataAccessException {
        Assert.notNull((Object)resourceDefineKey, "resourceDefineKey must not be null.");
        Assert.notNull((Object)dimKey, "dimKey must not be null.");
        String sql = "delete from NR_DATARESOURCE_DIMATTR where DD_KEY = :dd_key AND DIM_KEY in (:key)";
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("dd_key", (Object)resourceDefineKey);
        parameterSource.addValue("key", Arrays.asList(dimKey));
        this.logger.debug(sql);
        this.namedParameterJdbcTemplate.update(sql, (SqlParameterSource)parameterSource);
    }

    @Override
    public void delete(String resourceDefineKey) throws DataAccessException {
        Assert.notNull((Object)resourceDefineKey, "resourceDefineKey must not be null.");
        String sql = "delete from NR_DATARESOURCE_DIMATTR where DD_KEY = :dd_key";
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("dd_key", (Object)resourceDefineKey);
        this.logger.debug(sql);
        this.namedParameterJdbcTemplate.update(sql, (SqlParameterSource)parameterSource);
    }

    @Override
    public List<DimAttributeDO> get(String resourceDefineKey, String dimKey) throws DataAccessException {
        Assert.notNull((Object)resourceDefineKey, "resourceDefineKey must not be null.");
        Assert.notNull((Object)dimKey, "dimKey must not be null.");
        return super.list(new String[]{"DD_KEY", "DIM_KEY"}, new Object[]{resourceDefineKey, dimKey}, this.getClz());
    }

    @Override
    public List<DimAttributeDO> getByDefineKey(String resourceDefineKey) throws DataAccessException {
        Assert.notNull((Object)resourceDefineKey, "resourceDefineKey must not be null.");
        return super.list(new String[]{"DD_KEY"}, new Object[]{resourceDefineKey}, this.getClz());
    }

    public Class<DimAttributeDO> getClz() {
        return DimAttributeDO.class;
    }
}

