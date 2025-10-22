/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.internal.anno.DBAnno$DBField
 *  com.jiuqi.nr.datascheme.internal.dao.impl.BaseDao
 *  org.springframework.dao.DataAccessException
 *  org.springframework.jdbc.core.RowMapper
 *  org.springframework.jdbc.core.namedparam.MapSqlParameterSource
 *  org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
 *  org.springframework.jdbc.core.namedparam.SqlParameterSource
 */
package com.jiuqi.nr.dataresource.dao.impl;

import com.jiuqi.nr.dataresource.DataBasic;
import com.jiuqi.nr.dataresource.DataResourceKind;
import com.jiuqi.nr.dataresource.NodeType;
import com.jiuqi.nr.dataresource.entity.DataResourceDO;
import com.jiuqi.nr.dataresource.entity.ResourceTreeDO;
import com.jiuqi.nr.dataresource.entity.ResourceTreeGroup;
import com.jiuqi.nr.dataresource.i18n.ResourceTreeI18NService;
import com.jiuqi.nr.datascheme.internal.anno.DBAnno;
import com.jiuqi.nr.datascheme.internal.dao.impl.BaseDao;
import java.lang.reflect.Field;
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

public abstract class BaseDataResourceDao<DO extends DataBasic>
extends BaseDao {
    @Autowired
    private ResourceTreeI18NService i18NService;
    @Autowired
    protected NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final Logger logger = LoggerFactory.getLogger(BaseDataResourceDao.class);

    public String insert(DO entity) throws DataAccessException {
        Assert.notNull(entity, "entity must not be null.");
        if (entity.getKey() == null) {
            entity.setKey(UUID.randomUUID().toString());
        }
        super.insert(entity);
        return entity.getKey();
    }

    public String[] insert(List<DO> entity) throws DataAccessException {
        Assert.notNull(entity, "entity must not be null.");
        String[] dataTableKeys = new String[entity.size()];
        for (int i = 0; i < entity.size(); ++i) {
            DataBasic dataTable = (DataBasic)entity.get(i);
            Assert.notNull((Object)dataTable, "Collection should not contain null.");
            if (dataTable.getKey() == null) {
                dataTable.setKey(UUID.randomUUID().toString());
            }
            dataTableKeys[i] = dataTable.getKey();
        }
        super.insert(entity.toArray());
        return dataTableKeys;
    }

    public void delete(String key) throws DataAccessException {
        Assert.notNull((Object)key, "key must not be null.");
        super.delete((Object)key);
    }

    public void delete(List<String> keys) throws DataAccessException {
        Assert.notNull(keys, "keys must not be null.");
        for (String key : keys) {
            Assert.notNull((Object)key, "Collection should not contain null.");
        }
        super.delete(keys.toArray());
    }

    public void update(DO entity) throws DataAccessException {
        Assert.notNull(entity, "entity must not be null.");
        Assert.notNull((Object)entity.getKey(), "entity must not be null.");
        super.update(entity);
    }

    public DO get(String key) throws DataAccessException {
        Assert.notNull((Object)key, "key must not be null.");
        DataBasic aDo = (DataBasic)super.getByKey((Object)key, this.getClz());
        if (!this.i18NService.isOpenLanaguage()) {
            return (DO)aDo;
        }
        if (aDo instanceof DataResourceDO) {
            if (((DataResourceDO)aDo).getResourceKind() == DataResourceKind.DIM_GROUP) {
                aDo.setTitle(this.i18NService.getI18NDimTitle(((DataResourceDO)aDo).getDimKey(), aDo.getTitle()));
            } else {
                String prefix = NodeType.valueOf(((DataResourceDO)aDo).getResourceKind().getValue()).name();
                aDo.setTitle(this.i18NService.getI18NTitle(prefix + aDo.getKey(), aDo.getTitle()));
            }
        } else if (aDo instanceof ResourceTreeDO) {
            aDo.setTitle(this.i18NService.getI18NTitle(NodeType.TREE.name() + aDo.getKey(), aDo.getTitle()));
        } else if (aDo instanceof ResourceTreeGroup) {
            aDo.setTitle(this.i18NService.getI18NTitle(NodeType.TREE_GROUP.name() + aDo.getKey(), aDo.getTitle()));
        }
        return (DO)aDo;
    }

    public void update(List<DO> resourceGroup) throws DataAccessException {
        Assert.notNull(resourceGroup, "resource must not be null.");
        for (DataBasic groupDO : resourceGroup) {
            Assert.notNull((Object)groupDO, "Collection should not contain null.");
            Assert.notNull((Object)groupDO.getKey(), "resourceKey must not be null.");
        }
        super.update(resourceGroup.toArray());
    }

    public List<DO> get(List<String> keys) throws DataAccessException {
        Assert.notNull(keys, "keys must not be null.");
        if (keys.isEmpty()) {
            return Collections.emptyList();
        }
        for (String key : keys) {
            Assert.notNull((Object)key, "Collection should not contain null.");
        }
        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource();
        sqlParameterSource.addValue("keys", keys);
        RowMapper rowMapper = super.getRowMapper(this.getClz());
        Field field = (Field)this.fieldMap.get(this.pk);
        DBAnno.DBField dbField = field.getAnnotation(DBAnno.DBField.class);
        String sql = this.selectSQL + " where " + dbField.dbField().toUpperCase() + " in (:keys) ";
        this.logger.debug(sql);
        return this.namedParameterJdbcTemplate.query(sql, (SqlParameterSource)sqlParameterSource, rowMapper);
    }

    public List<DO> getAll() throws DataAccessException {
        return super.list(this.getClz());
    }

    public abstract Class<DO> getClz();
}

