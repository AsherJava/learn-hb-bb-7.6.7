/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.nr.dataservice.core.dimension.ArrayDimensionValue
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollectionBuilder
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollectionUtil
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationBuilder
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionValue
 *  com.jiuqi.nr.dataservice.core.dimension.FixedDimensionValue
 *  com.jiuqi.nr.dataservice.core.dimension.VariableDimensionValue
 *  com.jiuqi.nr.dataservice.core.dimension.VariableDimensionValueProvider
 *  com.jiuqi.nr.dataservice.core.dimension.provider.DimensionProviderData
 *  com.jiuqi.nr.dataservice.core.dimension.provider.DimensionProviderFactory
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 *  com.jiuqi.nr.entity.engine.executors.ExecutorContext
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.engine.setting.AuthorityType
 *  com.jiuqi.nr.entity.engine.var.ReferRelation
 *  com.jiuqi.nr.entity.service.IEntityDataService
 */
package com.jiuqi.nr.data.access.service.impl;

import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.nr.data.access.exception.AccessException;
import com.jiuqi.nr.data.access.param.EntityDimData;
import com.jiuqi.nr.data.access.service.ITaskDimensionFilter;
import com.jiuqi.nr.data.access.service.impl.EntityDimDataCache;
import com.jiuqi.nr.data.access.util.DataAccesslUtil;
import com.jiuqi.nr.dataservice.core.dimension.ArrayDimensionValue;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollectionBuilder;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollectionUtil;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationBuilder;
import com.jiuqi.nr.dataservice.core.dimension.DimensionValue;
import com.jiuqi.nr.dataservice.core.dimension.FixedDimensionValue;
import com.jiuqi.nr.dataservice.core.dimension.VariableDimensionValue;
import com.jiuqi.nr.dataservice.core.dimension.VariableDimensionValueProvider;
import com.jiuqi.nr.dataservice.core.dimension.provider.DimensionProviderData;
import com.jiuqi.nr.dataservice.core.dimension.provider.DimensionProviderFactory;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import com.jiuqi.nr.entity.engine.executors.ExecutorContext;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.engine.setting.AuthorityType;
import com.jiuqi.nr.entity.engine.var.ReferRelation;
import com.jiuqi.nr.entity.service.IEntityDataService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

public class DefaultTaskDimensionFilter
implements ITaskDimensionFilter {
    private IRunTimeViewController runTimeViewController;
    private DataAccesslUtil dataAccesslUtil;
    private IEntityDataService entityDataService;
    private IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    private IEntityViewRunTimeController entityViewRunTimeController;
    private DimensionProviderFactory dimensionProviderFactory;
    private EntityDimDataCache cache = null;
    private boolean filterByTask = false;

    public void setFilterByTask(boolean filterByTask) {
        this.filterByTask = filterByTask;
    }

    @Override
    public boolean exist(String taskKey, DimensionCombination masterKey) {
        List<EntityDimData> dimsData;
        if (this.cache == null) {
            this.cache = new EntityDimDataCache(this.dataAccesslUtil);
        }
        if (CollectionUtils.isEmpty(dimsData = this.cache.getDimsEntityDimDataByTaskKey(taskKey))) {
            return true;
        }
        EntityDimData dwDimData = this.filterByTask ? this.cache.getDwEntityDimDataByTaskKey(taskKey) : this.cache.getSrcDwEntityDimDataByTaskKey(taskKey);
        FixedDimensionValue dwDimensionValue = masterKey.getDWDimensionValue();
        if (dwDimensionValue == null) {
            dwDimensionValue = masterKey.getFixedDimensionValue(dwDimData.getDimensionName());
        }
        if (dwDimensionValue == null || ObjectUtils.isEmpty(dwDimensionValue.getValue())) {
            throw new AccessException("\u7ef4\u5ea6\u7f3a\u5c11\u5355\u4f4d\u4fe1\u606f:" + masterKey);
        }
        EntityDimData periodView = this.cache.getPeriodEntityDimDataByTaskKey(taskKey);
        String periodName = periodView.getDimensionName();
        Object periodValue = masterKey.getValue(periodName);
        if (ObjectUtils.isEmpty(periodValue)) {
            throw new AccessException("\u7f3a\u5c11\u65f6\u671f\u4fe1\u606f" + masterKey);
        }
        EntityViewDefine viewByTaskDefineKey = this.runTimeViewController.getViewByTaskDefineKey(taskKey);
        IEntityQuery dwExistQuery = this.entityDataService.newEntityQuery();
        dwExistQuery.setEntityView(viewByTaskDefineKey);
        DimensionValueSet dwSet = new DimensionValueSet();
        dwSet.setValue(periodName, periodValue);
        dwSet.setValue(dwDimData.getDimensionName(), dwDimensionValue.getValue());
        dwExistQuery.setMasterKeys(dwSet);
        dwExistQuery.setAuthorityOperations(AuthorityType.None);
        ExecutorContext executorContext = new ExecutorContext(this.dataDefinitionRuntimeController);
        executorContext.setJQReportModel(true);
        ReportFmlExecEnvironment environment = new ReportFmlExecEnvironment(this.runTimeViewController, this.dataDefinitionRuntimeController, this.entityViewRunTimeController);
        executorContext.setEnv((IFmlExecEnvironment)environment);
        executorContext.setPeriodView(periodView.getEntityId());
        try {
            IEntityTable table = dwExistQuery.executeReader((IContext)executorContext);
            IEntityRow row = table.findByEntityKey(dwDimensionValue.getValue().toString());
            if (row == null || !dwDimensionValue.getValue().toString().equals(row.getEntityKeyData())) {
                return false;
            }
        }
        catch (Exception e) {
            throw new AccessException("\u5b9e\u4f53\u67e5\u8be2\u51fa\u9519", e);
        }
        DimensionCombinationBuilder builder = new DimensionCombinationBuilder();
        for (FixedDimensionValue fixedDimensionValue : masterKey) {
            if (fixedDimensionValue.getName().equals(dwDimensionValue.getName())) {
                builder.setDWValue(dwDimData.getDimensionName(), dwDimData.getEntityId(), fixedDimensionValue.getValue());
                continue;
            }
            builder.setValue(fixedDimensionValue.getName(), fixedDimensionValue.getEntityID(), fixedDimensionValue.getValue());
        }
        masterKey = builder.getCombination();
        Object value = dwDimensionValue.getValue();
        ReferRelation referRelation = new ReferRelation();
        referRelation.setViewDefine(viewByTaskDefineKey);
        referRelation.setRange(Collections.singletonList(value.toString()));
        for (EntityDimData dimsDatum : dimsData) {
            IEntityTable table;
            String dimensionName = dimsDatum.getDimensionName();
            Object dimValue = masterKey.getValue(dimensionName);
            if (dimValue == null) continue;
            DimensionValueSet dimensionValueSet = new DimensionValueSet();
            dimensionValueSet.setValue(periodName, periodValue);
            dimensionValueSet.setValue(dimensionName, dimValue);
            IEntityQuery entityQuery = this.entityDataService.newEntityQuery();
            EntityViewDefine dimView = this.runTimeViewController.getDimensionViewByTaskAndEntity(taskKey, dimsDatum.getEntityId());
            entityQuery.setEntityView(dimView);
            entityQuery.setMasterKeys(dimensionValueSet);
            entityQuery.addReferRelation(referRelation);
            entityQuery.setAuthorityOperations(AuthorityType.None);
            entityQuery.sorted(false);
            try {
                table = entityQuery.executeReader((IContext)executorContext);
            }
            catch (Exception e) {
                throw new AccessException("\u5b9e\u4f53\u67e5\u8be2\u51fa\u9519", e);
            }
            IEntityRow row = table.findByEntityKey(dimValue.toString());
            if (row != null && dimValue.toString().equals(row.getEntityKeyData())) continue;
            return false;
        }
        return true;
    }

    @Override
    public List<DimensionCombination> filter(String taskKey, DimensionCollection masterKeys) {
        return this.replace(taskKey, masterKeys).getDimensionCombinations();
    }

    @Override
    public DimensionCollection replace(String taskKey, DimensionCollection masterKeys) {
        EntityDimData dwDimData;
        DimensionValue dimensionValue;
        if (this.cache == null) {
            this.cache = new EntityDimDataCache(this.dataAccesslUtil);
        }
        if ((dimensionValue = DimensionCollectionUtil.getDimensionValue((DimensionCollection)masterKeys, (String)(dwDimData = this.filterByTask ? this.cache.getDwEntityDimDataByTaskKey(taskKey) : this.cache.getSrcDwEntityDimDataByTaskKey(taskKey)).getDimensionName())) == null) {
            throw new AccessException("\u7ef4\u5ea6\u7f3a\u5c11\u5355\u4f4d\u4fe1\u606f:" + masterKeys);
        }
        EntityDimData periodView = this.cache.getPeriodEntityDimDataByTaskKey(taskKey);
        DimensionValue periodDimValue = DimensionCollectionUtil.getDimensionValue((DimensionCollection)masterKeys, (String)periodView.getDimensionName());
        if (periodDimValue == null) {
            throw new AccessException("\u7f3a\u5c11\u65f6\u671f\u4fe1\u606f" + masterKeys);
        }
        DimensionCollectionBuilder builder = new DimensionCollectionBuilder();
        if (periodDimValue instanceof FixedDimensionValue) {
            builder.setEntityValue(periodDimValue.getName(), periodView.getEntityId(), new Object[]{((FixedDimensionValue)periodDimValue).getValue()});
        } else if (periodDimValue instanceof ArrayDimensionValue) {
            builder.setEntityValue(periodDimValue.getName(), periodView.getEntityId(), ((ArrayDimensionValue)periodDimValue).getValue());
        } else if (periodDimValue instanceof VariableDimensionValue) {
            VariableDimensionValueProvider provider = ((VariableDimensionValue)periodDimValue).getProvider();
            builder.addVariableDimension(periodDimValue.getName(), periodView.getEntityId(), provider);
        } else {
            throw new AccessException("\u4e0d\u652f\u6301\u7684\u7ef4\u5ea6\u7c7b\u578b" + dimensionValue);
        }
        ArrayList<EntityDimData> dwAndDims = new ArrayList<EntityDimData>();
        dwAndDims.add(dwDimData);
        List<EntityDimData> dimsData = this.cache.getDimsEntityDimDataByTaskKey(taskKey);
        if (!CollectionUtils.isEmpty(dimsData)) {
            dwAndDims.addAll(dimsData);
        }
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(taskKey);
        String dataSchemeKey = taskDefine.getDataScheme();
        boolean dw = true;
        for (EntityDimData dimsDatum : dwAndDims) {
            VariableDimensionValueProvider dimensionProvider;
            DimensionValue dimValue = DimensionCollectionUtil.getDimensionValue((DimensionCollection)masterKeys, (String)dimsDatum.getDimensionName());
            if (dimValue == null) {
                throw new AccessException("\u7f3a\u5c11\u7ef4\u5ea6\uff1a" + dimsDatum.getDimensionName());
            }
            ArrayList<String> choosedValues = new ArrayList<String>();
            DimensionProviderData copy = new DimensionProviderData();
            copy.setChoosedValues(choosedValues);
            if (dimValue instanceof FixedDimensionValue) {
                Object value = ((FixedDimensionValue)dimValue).getValue();
                if (value != null) {
                    choosedValues.add(value.toString());
                }
            } else if (dimValue instanceof ArrayDimensionValue) {
                Object[] data;
                for (Object value : data = ((ArrayDimensionValue)dimValue).getValue()) {
                    if (value == null) continue;
                    choosedValues.add(value.toString());
                }
            } else if (dimValue instanceof VariableDimensionValue) {
                VariableDimensionValueProvider provider = ((VariableDimensionValue)dimValue).getProvider();
                copy = provider.getDimensionProviderData();
            } else {
                throw new AccessException("\u4e0d\u652f\u6301\u7684\u7ef4\u5ea6\u7c7b\u578b" + dimensionValue);
            }
            copy.setDataSchemeKey(dataSchemeKey);
            EntityViewDefine viewByTaskDefineKey = this.runTimeViewController.getDimensionViewByTaskAndEntity(taskKey, dimsDatum.getEntityId());
            copy.setFilter(viewByTaskDefineKey.getRowFilterExpression());
            if (viewByTaskDefineKey.getFilterRowByAuthority()) {
                copy.setAuthorityType(AuthorityType.Read);
            } else {
                copy.setAuthorityType(AuthorityType.None);
            }
            if (dw) {
                dimensionProvider = this.dimensionProviderFactory.getDimensionProvider("PROVIDER_FILTERDWBYVERSION", copy);
                builder.addVariableDW(dimsDatum.getDimensionName(), dimsDatum.getEntityId(), dimensionProvider);
            } else {
                dimensionProvider = this.dimensionProviderFactory.getDimensionProvider("PROVIDER_FILTERDIMBYDW", copy);
                builder.addVariableDimension(dimsDatum.getDimensionName(), dimsDatum.getEntityId(), dimensionProvider);
            }
            dw = false;
        }
        return builder.getCollection();
    }

    public void setRunTimeViewController(IRunTimeViewController runTimeViewController) {
        this.runTimeViewController = runTimeViewController;
    }

    public void setDataAccessUtil(DataAccesslUtil dataAccesslUtil) {
        this.dataAccesslUtil = dataAccesslUtil;
    }

    public void setEntityDataService(IEntityDataService entityDataService) {
        this.entityDataService = entityDataService;
    }

    public void setDataDefinitionRuntimeController(IDataDefinitionRuntimeController dataDefinitionRuntimeController) {
        this.dataDefinitionRuntimeController = dataDefinitionRuntimeController;
    }

    public void setEntityViewRunTimeController(IEntityViewRunTimeController entityViewRunTimeController) {
        this.entityViewRunTimeController = entityViewRunTimeController;
    }

    public void setDimensionProviderFactory(DimensionProviderFactory dimensionProviderFactory) {
        this.dimensionProviderFactory = dimensionProviderFactory;
    }
}

