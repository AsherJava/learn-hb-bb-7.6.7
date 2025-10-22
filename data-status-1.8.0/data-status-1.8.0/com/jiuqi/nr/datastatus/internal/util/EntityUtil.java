/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.context.cxt.DsContext
 *  com.jiuqi.nr.context.cxt.DsContextHolder
 *  com.jiuqi.nr.datascheme.adjustment.util.AdjustUtils
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.service.IFormSchemeService
 *  com.jiuqi.nr.entity.engine.executors.ExecutorContext
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.engine.setting.AuthorityType
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityDataService
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.nr.period.internal.adapter.IPeriodProvider
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.nr.period.modal.IPeriodEntity
 */
package com.jiuqi.nr.datastatus.internal.util;

import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.context.cxt.DsContext;
import com.jiuqi.nr.context.cxt.DsContextHolder;
import com.jiuqi.nr.datascheme.adjustment.util.AdjustUtils;
import com.jiuqi.nr.datastatus.internal.obj.EntityData;
import com.jiuqi.nr.datastatus.internal.obj.EntityKind;
import com.jiuqi.nr.datastatus.internal.util.entity.AdjustDataLoader;
import com.jiuqi.nr.datastatus.internal.util.entity.EntityDataLoader;
import com.jiuqi.nr.datastatus.internal.util.entity.IDimDataLoader;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.service.IFormSchemeService;
import com.jiuqi.nr.entity.engine.executors.ExecutorContext;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.engine.setting.AuthorityType;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityDataService;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nr.period.internal.adapter.IPeriodProvider;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nr.period.modal.IPeriodEntity;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class EntityUtil {
    private static final Logger logger = LoggerFactory.getLogger(EntityUtil.class);
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private IPeriodEntityAdapter periodEntityAdapter;
    @Autowired
    private IEntityViewRunTimeController entityViewRunTimeController;
    @Autowired
    private IEntityDataService entityDataService;
    @Autowired
    private PeriodEngineService periodEngineService;
    @Autowired
    private IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private IFormSchemeService formSchemeService;

    public String getContextMainDimId(String dw) {
        DsContext dsContext = DsContextHolder.getDsContext();
        String entityId = dsContext.getContextEntityId();
        return StringUtils.isEmpty((String)entityId) ? dw : entityId;
    }

    public List<EntityData> getDepOnDwDims(FormSchemeDefine formSchemeDefine) {
        if (StringUtils.isNotEmpty((String)formSchemeDefine.getDims())) {
            String[] split = formSchemeDefine.getDims().split(";");
            ArrayList<String> referDims = new ArrayList<String>();
            for (String s : split) {
                boolean refer;
                if ("ADJUST".equals(s) || !(refer = StringUtils.isNotEmpty((String)this.formSchemeService.getDimAttributeByReportDim(formSchemeDefine.getKey(), s)))) continue;
                referDims.add(s);
            }
            if (!CollectionUtils.isEmpty(referDims)) {
                ArrayList<EntityData> result = new ArrayList<EntityData>();
                referDims.forEach(o -> result.add(this.getEntity((String)o)));
                return result;
            }
        }
        return Collections.emptyList();
    }

    public EntityData getEntity(String entityID) {
        if (AdjustUtils.isAdjust((String)entityID).booleanValue()) {
            return new EntityData(EntityKind.ADJUST_ENTITY_KIND.getKind());
        }
        IEntityDefine entityDefine = this.entityMetaService.queryEntity(entityID);
        if (entityDefine != null) {
            return new EntityData(entityDefine);
        }
        return new EntityData();
    }

    public EntityData getPeriodEntity(String entityID) {
        IPeriodEntity periodEntity = this.periodEntityAdapter.getPeriodEntity(entityID);
        if (periodEntity != null) {
            return new EntityData(periodEntity);
        }
        return new EntityData();
    }

    public List<EntityData> getFmSchemeEntities(FormSchemeDefine formSchemeDefine) {
        String periodEntityId = formSchemeDefine.getDateTime();
        ArrayList<EntityData> result = new ArrayList<EntityData>(this.getDwDimEntities(formSchemeDefine));
        result.add(this.getPeriodEntity(periodEntityId));
        return result;
    }

    public List<EntityData> getDwDimEntities(FormSchemeDefine formSchemeDefine) {
        String dwEntityId = this.getContextMainDimId(formSchemeDefine.getDw());
        ArrayList<EntityData> result = new ArrayList<EntityData>();
        result.add(this.getEntity(dwEntityId));
        result.addAll(this.getDimEntities(formSchemeDefine));
        return result;
    }

    public List<EntityData> getDimEntities(FormSchemeDefine formSchemeDefine) {
        ArrayList<EntityData> result = new ArrayList<EntityData>();
        String dims = formSchemeDefine.getDims();
        List<Object> dimEntityIds = new ArrayList();
        if (StringUtils.isNotEmpty((String)dims)) {
            dimEntityIds = Arrays.asList(dims.split(";"));
            for (String string : dimEntityIds) {
                result.add(this.getEntity(string));
            }
        }
        if (!dimEntityIds.contains("ADJUST")) {
            this.appendAdjustIfExist(result, formSchemeDefine.getKey());
        }
        return result;
    }

    public void appendAdjustIfExist(List<EntityData> entityData, String formSchemeKey) {
        if (this.formSchemeService.enableAdjustPeriod(formSchemeKey)) {
            EntityData adjust = new EntityData(EntityKind.ADJUST_ENTITY_KIND.getKind());
            entityData.add(adjust);
        }
    }

    public Date period2Date(String periodEntityId, String period) {
        IPeriodProvider periodProvider = this.periodEngineService.getPeriodAdapter().getPeriodProvider(periodEntityId);
        Date versionDate = null;
        try {
            versionDate = periodProvider.getPeriodDateRegion(period)[1];
        }
        catch (ParseException e) {
            logger.error("\u83b7\u53d6\u65f6\u671f\u5931\u8d25" + e.getMessage());
        }
        return versionDate;
    }

    public Map<String, IDimDataLoader> getDimDataLoaders(FormSchemeDefine formScheme, DimensionValueSet masterKeys, List<EntityData> entityData) {
        HashMap<String, IDimDataLoader> entityLoaderMap = new HashMap<String, IDimDataLoader>();
        for (EntityData entityDatum : entityData) {
            if (AdjustUtils.isAdjust((String)entityDatum.getKey()).booleanValue()) {
                EntityData periodEntity = this.getPeriodEntity(formScheme.getDateTime());
                String periodDimensionName = periodEntity.getPeriodEntity().getDimensionName();
                String dataScheme = this.runTimeViewController.queryTaskDefine(formScheme.getTaskKey()).getDataScheme();
                try {
                    AdjustDataLoader adjustDataLoader = new AdjustDataLoader(dataScheme, (String)masterKeys.getValue(periodDimensionName));
                    entityLoaderMap.put(entityDatum.getDimensionName(), adjustDataLoader);
                }
                catch (Exception e) {
                    logger.error("\u83b7\u53d6\u8c03\u6574\u671f\u6570\u636e\u96c6\u5f02\u5e38\uff1a" + e.getMessage(), e);
                }
                continue;
            }
            EntityDataLoader entityDataLoader = this.getEntityDataLoader(entityDatum.getKey(), formScheme.getDateTime(), masterKeys, null);
            entityLoaderMap.put(entityDatum.getDimensionName(), entityDataLoader);
        }
        return entityLoaderMap;
    }

    public EntityDataLoader getEntityDataLoader(String entityId, String periodEntityId, DimensionValueSet masterKeys, String filter) {
        Date entityQueryVersionDate = this.getEntityQueryVersionDate(periodEntityId, masterKeys);
        IEntityQuery query = this.getEntityQuery(entityId, entityQueryVersionDate, masterKeys, filter);
        ExecutorContext context = new ExecutorContext(this.dataDefinitionRuntimeController);
        try {
            IEntityTable entityTable = query.executeReader((IContext)context);
            return new EntityDataLoader(entityTable, filter);
        }
        catch (Exception e) {
            logger.error("\u5b9e\u4f53ID\u4e3a" + entityId + "\u7684\u5b9e\u4f53\u672a\u627e\u5230:" + e.getMessage(), e);
            return null;
        }
    }

    public IEntityQuery getEntityQuery(String entityId, Date queryVersionDate, DimensionValueSet masterKeys, String filter) {
        EntityViewDefine entityViewDefine = this.entityViewRunTimeController.buildEntityView(entityId);
        return this.getEntityQuery(entityViewDefine, queryVersionDate, masterKeys, filter);
    }

    public IEntityQuery getEntityQuery(EntityViewDefine entityViewDefine, Date queryVersionDate, DimensionValueSet masterKeys, String filter) {
        IEntityQuery query = this.entityDataService.newEntityQuery();
        query.setEntityView(entityViewDefine);
        if (queryVersionDate != null) {
            query.setQueryVersionDate(queryVersionDate);
        }
        if (StringUtils.isNotEmpty((String)filter)) {
            query.setRowFilter(filter);
        }
        if (masterKeys != null) {
            query.setMasterKeys(masterKeys);
        }
        query.setAuthorityOperations(AuthorityType.Read);
        return query;
    }

    public Date getEntityQueryVersionDate(String periodEntityId, DimensionValueSet masterKeys) {
        EntityData periodEntity;
        String periodDimensionName;
        if (StringUtils.isNotEmpty((String)periodEntityId) && masterKeys.hasValue(periodDimensionName = (periodEntity = this.getPeriodEntity(periodEntityId)).getPeriodEntity().getDimensionName())) {
            String period = (String)masterKeys.getValue(periodDimensionName);
            return this.period2Date(periodEntity.getPeriodEntity().getKey(), period);
        }
        return null;
    }

    public List<IEntityRow> getAllEntityRows(String entityId, Date queryVersionDate, String filter) {
        IEntityQuery query = this.getEntityQuery(entityId, queryVersionDate, null, filter);
        ExecutorContext context = new ExecutorContext(this.dataDefinitionRuntimeController);
        return this.getAllEntityRows(query, context);
    }

    public List<IEntityRow> getFSFilteredEntityRows(FormSchemeDefine formSchemeDefine, String entityId, Date queryVersionDate) {
        EntityViewDefine entityViewDefine = entityId.equals(formSchemeDefine.getDw()) ? this.runTimeViewController.getViewByFormSchemeKey(formSchemeDefine.getKey()) : this.runTimeViewController.getDimensionViewByFormSchemeAndEntity(formSchemeDefine.getKey(), entityId);
        IEntityQuery query = this.getEntityQuery(entityViewDefine, queryVersionDate, null, null);
        ExecutorContext context = new ExecutorContext(this.dataDefinitionRuntimeController);
        return this.getAllEntityRows(query, context);
    }

    private List<IEntityRow> getAllEntityRows(IEntityQuery entityQuery, ExecutorContext context) {
        try {
            IEntityTable entityTable = entityQuery.executeReader((IContext)context);
            return entityTable.getAllRows();
        }
        catch (Exception e) {
            logger.error("\u5b9e\u4f53\u67e5\u8be2\u51fa\u9519:" + e.getMessage(), e);
            return Collections.emptyList();
        }
    }

    public Map<String, DimensionValue> getOnlyDimCartesian(Map<String, DimensionValue> dimensionSet, FormSchemeDefine formSchemeDefine) {
        List<EntityData> dimEntities = this.getDimEntities(formSchemeDefine);
        if (dimEntities == null || dimEntities.isEmpty()) {
            return new HashMap<String, DimensionValue>();
        }
        List dimName = dimEntities.stream().map(EntityData::getDimensionName).collect(Collectors.toList());
        HashMap<String, DimensionValue> result = new HashMap<String, DimensionValue>();
        for (Map.Entry<String, DimensionValue> entry : dimensionSet.entrySet()) {
            String key = entry.getKey();
            DimensionValue value = entry.getValue();
            if (!dimName.contains(key)) continue;
            result.put(key, value);
        }
        return result;
    }
}

