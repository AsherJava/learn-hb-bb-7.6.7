/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.exception.DBParaException
 *  com.jiuqi.np.definition.internal.db.BaseDao
 *  com.jiuqi.util.OrderGenerator
 */
package com.jiuqi.nr.filterTemplate.dao;

import com.jiuqi.np.definition.exception.DBParaException;
import com.jiuqi.np.definition.internal.db.BaseDao;
import com.jiuqi.nr.filterTemplate.exception.FilterTemplateRunTimeException;
import com.jiuqi.nr.filterTemplate.facade.FilterTemplateDO;
import com.jiuqi.nr.filterTemplate.util.TimeConvert;
import com.jiuqi.util.OrderGenerator;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

@Repository
public class FilterTemplateDao
extends BaseDao {
    private Class<FilterTemplateDO> implClass = FilterTemplateDO.class;
    public static final String ENTITY_KEY = "entityID";

    public Class<FilterTemplateDO> getClz() {
        return this.implClass;
    }

    public Class<?> getExternalTransCls() {
        return TimeConvert.class;
    }

    public String insert(FilterTemplateDO filterTemplateDO) {
        Assert.notNull((Object)filterTemplateDO, "filterTemplateDO must not be null.");
        this.checkTitle(filterTemplateDO);
        if (filterTemplateDO.getFilterTemplateID() == null) {
            filterTemplateDO.setFilterTemplateID(UUID.randomUUID().toString());
        }
        if (filterTemplateDO.getOrder() == null) {
            filterTemplateDO.setOrder(OrderGenerator.newOrder());
        }
        if (filterTemplateDO.getUpdateTime() == null) {
            filterTemplateDO.setUpdateTime(Instant.now());
        }
        try {
            super.insert((Object)filterTemplateDO);
        }
        catch (Exception e) {
            throw new FilterTemplateRunTimeException(e.getMessage());
        }
        return filterTemplateDO.getFilterTemplateID();
    }

    public void batchInsert(Object[] filterTemplates) {
        try {
            this.insert(filterTemplates);
        }
        catch (Exception e) {
            throw new FilterTemplateRunTimeException(e.getMessage());
        }
    }

    public void delete(String key) {
        Assert.notNull((Object)key, "key must not be null.");
        try {
            super.delete((Object)key);
        }
        catch (DBParaException e) {
            throw new FilterTemplateRunTimeException(e.getMessage());
        }
    }

    public void batchDelete(Object[] key) {
        Assert.notNull((Object)key, "key must not be null.");
        try {
            super.delete(key);
        }
        catch (DBParaException e) {
            throw new FilterTemplateRunTimeException(e.getMessage());
        }
    }

    public void update(FilterTemplateDO filterTemplateDO) {
        Assert.notNull((Object)filterTemplateDO, "filterTemplateDO must not be null.");
        Assert.notNull((Object)filterTemplateDO.getFilterTemplateID(), "FilterTemplateID must not be null.");
        this.checkTitle(filterTemplateDO);
        if (filterTemplateDO.getUpdateTime() == null) {
            filterTemplateDO.setUpdateTime(Instant.now());
        }
        try {
            super.update((Object)filterTemplateDO);
        }
        catch (DBParaException e) {
            throw new FilterTemplateRunTimeException(e.getMessage());
        }
    }

    public void batchUpdate(Object[] filterTemplateDO) {
        Assert.notNull((Object)filterTemplateDO, "filterTemplateDO must not be null.");
        try {
            super.update(filterTemplateDO);
        }
        catch (DBParaException e) {
            throw new FilterTemplateRunTimeException(e.getMessage());
        }
    }

    public FilterTemplateDO get(String key) {
        Assert.notNull((Object)key, "key must not be null.");
        return (FilterTemplateDO)super.getByKey((Object)key, this.getClz());
    }

    public List<FilterTemplateDO> ListAll() {
        return super.list(this.getClz());
    }

    public List<FilterTemplateDO> getByEntity(String entityID) {
        Assert.notNull((Object)entityID, "key must not be null.");
        return this.list(new String[]{ENTITY_KEY}, new Object[]{entityID.toString()}, this.implClass);
    }

    private void checkTitle(FilterTemplateDO entityViewDefineData) {
        List<FilterTemplateDO> entityViewDefines = this.getByEntity(entityViewDefineData.getEntityID());
        boolean flag = entityViewDefines.stream().filter(t -> !t.getFilterTemplateID().equals(entityViewDefineData.getFilterTemplateID())).anyMatch(t -> t.getFilterTemplateTitle().equals(entityViewDefineData.getFilterTemplateTitle()));
        if (flag) {
            throw new FilterTemplateRunTimeException("\u540d\u79f0\u91cd\u590d,\u8bf7\u4fee\u6539\u540d\u79f0");
        }
    }
}

