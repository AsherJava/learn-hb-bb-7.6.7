/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.internal.dao;

import com.jiuqi.nr.definition.facade.DesignDimensionFilter;
import com.jiuqi.nr.definition.internal.dao.AbstractDimensionFilterDao;
import com.jiuqi.nr.definition.internal.impl.DesignDimensionFilterImpl;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

@Repository
public class DesignDimensionFilterDao
extends AbstractDimensionFilterDao<DesignDimensionFilterImpl> {
    private static final Class<DesignDimensionFilterImpl> aClass = DesignDimensionFilterImpl.class;
    private static final String TASK_KEY_MESSAGE = "DimensionFilter task key must not be empty";
    private static final String ENTITY_ID_MESSAGE = "DimensionFilter entity id must not be empty";
    private static final String LIST_MESSAGE = "DimensionFilter Collection must contain elements";
    private static final String KEY_MESSAGE = "DimensionFilter key must not be empty";
    private static final String OBJ_MESSAGE = "DimensionFilter must not be null";
    private static final String TYPE_MESSAGE = "DimensionFilter filter type not be null";
    private static final String LIST_TYPE_MESSAGE = "DimensionFilter filter list type not be null";
    private static final String UPDATE_SQL = "UPDATE " + "NR_PARAM_DIMFILTER_DES" + " SET " + "DF_LIST_TYPE" + "=?," + "DF_TYPE" + "=?," + "DF_VALUE" + "=? WHERE " + "DF_TASK_KEY" + "=? and " + "DF_ENTITY_ID" + "=?";

    @Override
    public Class<DesignDimensionFilterImpl> getClz() {
        return aClass;
    }

    public List<DesignDimensionFilter> getByTaskKey(String taskKey) {
        Assert.hasLength(taskKey, TASK_KEY_MESSAGE);
        return super.list(new String[]{"DF_TASK_KEY"}, (Object[])new String[]{taskKey}, this.getClz());
    }

    public DesignDimensionFilter getByTaskKeyAndEntityId(String taskKey, String entityId) {
        Assert.hasLength(taskKey, TASK_KEY_MESSAGE);
        Assert.hasLength(entityId, ENTITY_ID_MESSAGE);
        return super.list(new String[]{"DF_TASK_KEY", "DF_ENTITY_ID"}, (Object[])new String[]{taskKey, entityId}, this.getClz()).stream().findFirst().orElse(null);
    }

    public void insert(DesignDimensionFilter designDimensionFilter) {
        this.beforeDML(designDimensionFilter);
        super.insert((Object)designDimensionFilter);
    }

    public void batchInsert(List<DesignDimensionFilter> list) {
        for (DesignDimensionFilter designDimensionFilter : list) {
            this.beforeDML(designDimensionFilter);
        }
        super.insert(list.toArray());
    }

    public void delete(String key) {
        Assert.hasLength(key, KEY_MESSAGE);
        super.delete((Object)key);
    }

    public void deleteByEntityId(String entityId) {
        Assert.hasLength(entityId, ENTITY_ID_MESSAGE);
        super.deleteBy(new String[]{"DF_ENTITY_ID"}, (Object[])new String[]{entityId});
    }

    public void deleteByTaskKey(String taskKey) {
        Assert.hasLength(taskKey, TASK_KEY_MESSAGE);
        super.deleteBy(new String[]{"DF_TASK_KEY"}, (Object[])new String[]{taskKey});
    }

    public int update(DesignDimensionFilter filter) {
        this.beforeDML(filter);
        Object[] objects = this.fillObjectArr(filter);
        return this.jdbcTemplate.update(UPDATE_SQL, objects);
    }

    public void batchUpdate(List<DesignDimensionFilter> designDimensionFilter) {
        ArrayList<Object[]> list = new ArrayList<Object[]>();
        for (DesignDimensionFilter filter : designDimensionFilter) {
            this.beforeDML(filter);
            list.add(this.fillObjectArr(filter));
        }
        this.jdbcTemplate.batchUpdate(UPDATE_SQL, list);
    }

    private void beforeDML(DesignDimensionFilter designDimensionFilter) {
        this.check(designDimensionFilter);
        if (designDimensionFilter.getKey() == null) {
            designDimensionFilter.setKey(UUID.randomUUID().toString());
        }
    }

    private void check(DesignDimensionFilter designDimensionFilter) {
        Assert.notNull((Object)designDimensionFilter, OBJ_MESSAGE);
        Assert.hasLength(designDimensionFilter.getTaskKey(), TASK_KEY_MESSAGE);
        Assert.hasLength(designDimensionFilter.getEntityId(), ENTITY_ID_MESSAGE);
        Assert.notNull((Object)designDimensionFilter.getListType(), LIST_TYPE_MESSAGE);
        Assert.notNull((Object)designDimensionFilter.getType(), TYPE_MESSAGE);
    }

    private Object[] fillObjectArr(DesignDimensionFilter filter) {
        return new Object[]{filter.getListType().ordinal(), filter.getType().ordinal(), filter.getValue(), filter.getTaskKey(), filter.getEntityId()};
    }
}

