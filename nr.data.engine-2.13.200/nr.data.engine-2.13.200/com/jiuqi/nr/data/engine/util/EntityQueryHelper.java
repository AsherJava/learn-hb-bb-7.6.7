/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IDimensionProvider
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 *  com.jiuqi.np.dataengine.query.QueryContext
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.nr.entity.engine.executors.ExecutorContext
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityDataService
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 */
package com.jiuqi.nr.data.engine.util;

import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IDimensionProvider;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityDataService;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EntityQueryHelper {
    @Autowired
    private IEntityDataService entityDataService;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private IEntityViewRunTimeController entityViewRunTimeController;
    @Autowired
    private IDimensionProvider dimensionProvider;
    private static final String CONTEXT_CACHE_KEY = "cache.dimEntityTables";

    public IEntityTable queryEntityTreeByDimensionName(QueryContext qContext, String dimensionName, Object values, String period) throws Exception {
        Map<String, IEntityTable> cache = this.getCache(qContext);
        if (cache == null) {
            return null;
        }
        String cacheKeyPrefix = values == null ? "full.tree." : "byMasterKeys.tree." + values;
        String cacheKey = "D" + cacheKeyPrefix + dimensionName;
        IEntityTable dimTable = cache.get(cacheKey);
        ExecutorContext executorContext = qContext.getExeContext();
        if (dimTable == null) {
            EntityViewDefine entityViewDefine;
            DimensionValueSet marsterKeys = new DimensionValueSet();
            if (values != null) {
                marsterKeys.setValue(dimensionName, values);
            }
            if (period != null) {
                marsterKeys.setValue("DATATIME", (Object)period);
            }
            if ((entityViewDefine = executorContext.getEnv().getEntityViewDefine(executorContext, dimensionName)) == null) {
                return null;
            }
            EntityViewDefine defaultEntityView = this.entityViewRunTimeController.buildEntityView(entityViewDefine.getEntityId());
            dimTable = this.doQuery(executorContext, marsterKeys, cache, defaultEntityView, cacheKeyPrefix, true);
        }
        return dimTable;
    }

    public IEntityTable queryEntityTreeByTableCode(QueryContext qContext, String tableCode, Object values, String period) throws Exception {
        Map<String, IEntityTable> cache = this.getCache(qContext);
        if (cache == null) {
            return null;
        }
        String cacheKeyPrefix = values == null ? "full.tree." : "byMasterKeys.tree." + values;
        String cacheKey = "T" + cacheKeyPrefix + tableCode;
        IEntityTable dimTable = cache.get(cacheKey);
        ExecutorContext executorContext = qContext.getExeContext();
        if (dimTable == null) {
            String dimensionName = this.dimensionProvider.getDimensionNameByEntityTableCode(executorContext, tableCode);
            if (dimensionName == null) {
                return null;
            }
            DimensionValueSet marsterKeys = this.buildMasterkeys(values, period, dimensionName);
            String entityId = this.dimensionProvider.getEntityIdByEntityTableCode(executorContext, tableCode);
            if (entityId == null) {
                return null;
            }
            EntityViewDefine defaultEntityView = this.entityViewRunTimeController.buildEntityView(entityId);
            dimTable = this.doQuery(executorContext, marsterKeys, cache, defaultEntityView, cacheKeyPrefix, true);
        }
        return dimTable;
    }

    public IEntityTable queryEntityTreeByEntityId(QueryContext qContext, String entityId, Object values, String period) throws Exception {
        Map<String, IEntityTable> cache = this.getCache(qContext);
        if (cache == null) {
            return null;
        }
        String cacheKeyPrefix = values == null ? "full.tree." : "byMasterKeys.tree." + values;
        String cacheKey = "I" + cacheKeyPrefix + entityId;
        IEntityTable dimTable = cache.get(cacheKey);
        ExecutorContext executorContext = qContext.getExeContext();
        if (dimTable == null) {
            String dimensionName = this.dimensionProvider.getDimensionNameByEntityId(entityId);
            if (dimensionName == null) {
                return null;
            }
            DimensionValueSet marsterKeys = this.buildMasterkeys(values, period, dimensionName);
            EntityViewDefine defaultEntityView = this.entityViewRunTimeController.buildEntityView(entityId);
            dimTable = this.doQuery(executorContext, marsterKeys, cache, defaultEntityView, cacheKeyPrefix, true);
        }
        return dimTable;
    }

    private Map<String, IEntityTable> getCache(QueryContext qContext) {
        HashMap cache = (HashMap)qContext.getCache().get(CONTEXT_CACHE_KEY);
        if (cache == null) {
            cache = new HashMap();
            qContext.getCache().put(CONTEXT_CACHE_KEY, cache);
        }
        return cache;
    }

    private DimensionValueSet buildMasterkeys(Object values, String period, String dimensionName) {
        DimensionValueSet marsterKeys = new DimensionValueSet();
        if (values != null) {
            marsterKeys.setValue(dimensionName, values);
        }
        if (period != null) {
            marsterKeys.setValue("DATATIME", (Object)period);
        }
        return marsterKeys;
    }

    private IEntityTable doQuery(ExecutorContext executorContext, DimensionValueSet marsterKeys, Map<String, IEntityTable> cache, EntityViewDefine entityView, String cacheKeyPrefix, boolean fullBuild) throws Exception {
        IEntityQuery iEntityQuery = this.entityDataService.newEntityQuery();
        com.jiuqi.nr.entity.engine.executors.ExecutorContext entityExecutorContext = new com.jiuqi.nr.entity.engine.executors.ExecutorContext(executorContext.getRuntimeController());
        if (executorContext.getPeriodView() != null) {
            entityExecutorContext.setPeriodView(executorContext.getPeriodView());
        } else {
            EntityViewDefine periodView;
            IFmlExecEnvironment env = executorContext.getEnv();
            if (env != null && (periodView = env.getEntityViewDefine(executorContext, "DATATIME")) != null) {
                entityExecutorContext.setPeriodView(periodView.getEntityId());
            }
        }
        IEntityDefine entityDefine = this.entityMetaService.queryEntity(entityView.getEntityId());
        if (entityDefine == null) {
            return null;
        }
        iEntityQuery.setEntityView(entityView);
        iEntityQuery.setMasterKeys(marsterKeys);
        IEntityTable dimTable = fullBuild ? iEntityQuery.executeFullBuild((IContext)entityExecutorContext) : iEntityQuery.executeReader((IContext)entityExecutorContext);
        cache.put("D" + cacheKeyPrefix + entityDefine.getDimensionName(), dimTable);
        cache.put("T" + cacheKeyPrefix + entityDefine.getCode(), dimTable);
        cache.put("I" + cacheKeyPrefix + entityDefine.getId(), dimTable);
        return dimTable;
    }

    public IEntityDataService getEntityDataService() {
        return this.entityDataService;
    }

    public IEntityMetaService getEntityMetaService() {
        return this.entityMetaService;
    }

    public IEntityViewRunTimeController getEntityViewRunTimeController() {
        return this.entityViewRunTimeController;
    }

    public IDimensionProvider getDimensionProvider() {
        return this.dimensionProvider;
    }
}

