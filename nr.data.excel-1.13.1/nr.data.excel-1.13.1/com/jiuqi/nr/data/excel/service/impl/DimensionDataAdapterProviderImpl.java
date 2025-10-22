/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.nr.data.access.util.DataAccesslUtil
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nr.definition.service.IFormSchemeService
 *  com.jiuqi.nr.entity.engine.executors.ExecutorContext
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.engine.setting.AuthorityType
 *  com.jiuqi.nr.entity.service.IEntityDataService
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.internal.adapter.IPeriodProvider
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 */
package com.jiuqi.nr.data.excel.service.impl;

import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.nr.data.access.util.DataAccesslUtil;
import com.jiuqi.nr.data.excel.service.impl.AdjustDataAdapterImpl;
import com.jiuqi.nr.data.excel.service.impl.EntityDataAdapterImpl;
import com.jiuqi.nr.data.excel.service.internal.IDimensionDataAdapter;
import com.jiuqi.nr.data.excel.service.internal.IDimensionDataAdapterProvider;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.definition.service.IFormSchemeService;
import com.jiuqi.nr.entity.engine.executors.ExecutorContext;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.engine.setting.AuthorityType;
import com.jiuqi.nr.entity.service.IEntityDataService;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.period.internal.adapter.IPeriodProvider;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

public class DimensionDataAdapterProviderImpl
implements IDimensionDataAdapterProvider {
    private static final Logger logger = LoggerFactory.getLogger(DimensionDataAdapterProviderImpl.class);
    private final FormSchemeDefine formSchemeDefine;
    private final String period;
    private DimensionValueSet queryDim;
    private final Map<String, IDimensionDataAdapter> mapByName = new HashMap<String, IDimensionDataAdapter>();
    private final Map<String, IDimensionDataAdapter> mapById = new HashMap<String, IDimensionDataAdapter>();
    private IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    private IEntityViewRunTimeController entityViewRunTimeController;
    private IEntityMetaService entityMetaService;
    private IEntityDataService entityDataService;
    private PeriodEngineService periodEngineService;
    private DataAccesslUtil dataAccesslUtil;

    public DimensionDataAdapterProviderImpl(FormSchemeDefine formSchemeDefine, String period) {
        this.formSchemeDefine = formSchemeDefine;
        this.period = period;
        this.initBeans();
        this.init(null);
    }

    public DimensionDataAdapterProviderImpl(FormSchemeDefine formSchemeDefine, String period, DimensionValueSet queryDim) {
        this.formSchemeDefine = formSchemeDefine;
        this.period = period;
        this.queryDim = queryDim;
        this.initBeans();
        this.init(null);
    }

    public DimensionDataAdapterProviderImpl(FormSchemeDefine formSchemeDefine, String period, List<String> provideDimIds) {
        this.formSchemeDefine = formSchemeDefine;
        this.period = period;
        this.initBeans();
        this.init(provideDimIds);
    }

    private void initBeans() {
        this.dataDefinitionRuntimeController = (IDataDefinitionRuntimeController)BeanUtil.getBean(IDataDefinitionRuntimeController.class);
        this.entityViewRunTimeController = (IEntityViewRunTimeController)BeanUtil.getBean(IEntityViewRunTimeController.class);
        this.entityMetaService = (IEntityMetaService)BeanUtil.getBean(IEntityMetaService.class);
        this.entityDataService = (IEntityDataService)BeanUtil.getBean(IEntityDataService.class);
        this.periodEngineService = (PeriodEngineService)BeanUtil.getBean(PeriodEngineService.class);
        this.dataAccesslUtil = (DataAccesslUtil)BeanUtil.getBean(DataAccesslUtil.class);
    }

    private void init(List<String> provideDimIds) {
        String dateTime = this.formSchemeDefine.getDateTime();
        ArrayList<String> entityIds = new ArrayList<String>();
        entityIds.add(this.dataAccesslUtil.contextEntityId(this.formSchemeDefine.getDw()));
        String dims = this.formSchemeDefine.getDims();
        if (StringUtils.hasText(dims)) {
            String[] split = dims.split(";");
            entityIds.addAll(Arrays.asList(split));
        }
        if (provideDimIds == null) {
            for (String entityId : entityIds) {
                this.addEntityDataAdapter(dateTime, entityId);
            }
            this.addAdjustDataProvider();
        } else {
            for (String entityId : entityIds) {
                if (!provideDimIds.contains(entityId)) continue;
                this.addEntityDataAdapter(dateTime, entityId);
            }
            if (provideDimIds.contains("ADJUST")) {
                this.addAdjustDataProvider();
            }
        }
    }

    private void addAdjustDataProvider() {
        IFormSchemeService formSchemeService = (IFormSchemeService)BeanUtil.getBean(IFormSchemeService.class);
        boolean enableAdjustPeriod = formSchemeService.enableAdjustPeriod(this.formSchemeDefine.getKey());
        if (enableAdjustPeriod) {
            IRunTimeViewController runTimeViewController = (IRunTimeViewController)BeanUtil.getBean(IRunTimeViewController.class);
            String dataScheme = runTimeViewController.queryTaskDefine(this.formSchemeDefine.getTaskKey()).getDataScheme();
            AdjustDataAdapterImpl adjustDataAdapter = new AdjustDataAdapterImpl(dataScheme, this.period);
            this.mapByName.put("ADJUST", adjustDataAdapter);
            this.mapById.put("ADJUST", adjustDataAdapter);
        }
    }

    private void addEntityDataAdapter(String dateTime, String entityId) {
        EntityDataAdapterImpl dwDataAdapter = this.getEntityDataAdapterImpl(entityId, dateTime, this.period);
        String dwDimensionName = this.entityMetaService.getDimensionName(entityId);
        this.mapById.put(entityId, dwDataAdapter);
        this.mapByName.put(dwDimensionName, dwDataAdapter);
    }

    private EntityDataAdapterImpl getEntityDataAdapterImpl(String entityId, String periodEntityId, String period) {
        Date entityQueryVersionDate = this.getEntityQueryVersionDate(periodEntityId, period);
        IEntityQuery query = this.getEntityQuery(entityId, entityQueryVersionDate);
        ExecutorContext context = new ExecutorContext(this.dataDefinitionRuntimeController);
        try {
            IEntityTable entityTable = query.executeReader((IContext)context);
            return new EntityDataAdapterImpl(entityTable);
        }
        catch (Exception e) {
            logger.error("\u5b9e\u4f53ID\u4e3a" + entityId + "\u7684\u5b9e\u4f53\u672a\u627e\u5230:" + e.getMessage(), e);
            return null;
        }
    }

    private Date getEntityQueryVersionDate(String periodEntityId, String period) {
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

    private IEntityQuery getEntityQuery(String entityId, Date queryVersionDate) {
        EntityViewDefine entityViewDefine = this.entityViewRunTimeController.buildEntityView(entityId);
        IEntityQuery query = this.entityDataService.newEntityQuery();
        query.setEntityView(entityViewDefine);
        if (queryVersionDate != null) {
            query.setQueryVersionDate(queryVersionDate);
        }
        if (this.queryDim != null) {
            query.setMasterKeys(this.queryDim);
        }
        query.setAuthorityOperations(AuthorityType.Read);
        query.maskedData();
        return query;
    }

    @Override
    public IDimensionDataAdapter getDimensionDataAdapterByName(String dimensionName) {
        return this.mapByName.get(dimensionName);
    }

    @Override
    public IDimensionDataAdapter getDimensionDataAdapterById(String dimensionId) {
        return this.mapById.get(dimensionId);
    }
}

