/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.nr.context.cxt.DsContext
 *  com.jiuqi.nr.context.cxt.DsContextHolder
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationBuilder
 *  com.jiuqi.nr.dataservice.core.dimension.FixedDimensionValue
 *  com.jiuqi.nr.definition.controller.IEntityViewController
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.entity.engine.executors.ExecutorContext
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.service.IEntityDataService
 */
package com.jiuqi.nr.datacrud.impl.service.impl;

import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.nr.context.cxt.DsContext;
import com.jiuqi.nr.context.cxt.DsContextHolder;
import com.jiuqi.nr.datacrud.IMetaData;
import com.jiuqi.nr.datacrud.ParamRelation;
import com.jiuqi.nr.datacrud.impl.out.CrudException;
import com.jiuqi.nr.datacrud.impl.service.impl.DefaultEntityTableWrapper;
import com.jiuqi.nr.datacrud.spi.IEntityTableFactory;
import com.jiuqi.nr.datacrud.spi.entity.EntityQueryMode;
import com.jiuqi.nr.datacrud.spi.entity.IEntityTableWrapper;
import com.jiuqi.nr.datacrud.spi.entity.QueryMode;
import com.jiuqi.nr.datacrud.spi.entity.QueryModeImpl;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationBuilder;
import com.jiuqi.nr.dataservice.core.dimension.FixedDimensionValue;
import com.jiuqi.nr.definition.controller.IEntityViewController;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.entity.engine.executors.ExecutorContext;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.service.IEntityDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

public class DefaultEntityTableFactory
implements IEntityTableFactory {
    @Autowired
    protected IEntityDataService entityDataService;
    @Autowired
    protected IEntityViewRunTimeController entityViewRunTimeController;
    @Autowired
    protected IRunTimeViewController runTimeViewController;
    @Autowired
    protected IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    @Autowired
    protected IEntityViewController entityViewController;
    protected static final Logger LOGGER = LoggerFactory.getLogger(DefaultEntityTableFactory.class);

    @Override
    public IEntityTableWrapper getEntityTable(ParamRelation regionRelation, IMetaData metaData) {
        return this.initTableWrapper(regionRelation, null, metaData);
    }

    @Override
    public void reBuildEntityTable(ParamRelation regionRelation, IMetaData metaData, IEntityTableWrapper preEntityTable) {
        String cacheKey = this.buildParamCacheKey(regionRelation, null, metaData);
        if (cacheKey == null) {
            IEntityTable entityTable = this.initTable(regionRelation, null, metaData);
            preEntityTable.putIEntityTable(null, entityTable);
            preEntityTable.resetIEntityTable(null);
        } else {
            boolean exist = preEntityTable.resetIEntityTable(cacheKey);
            if (!exist) {
                IEntityTable entityTable = this.initTable(regionRelation, null, metaData);
                preEntityTable.putIEntityTable(cacheKey, entityTable);
                preEntityTable.resetIEntityTable(cacheKey);
            }
        }
    }

    @Override
    public IEntityTableWrapper getEntityTable(ParamRelation regionRelation, DimensionCombination masterKey, IMetaData metaData) {
        return this.initTableWrapper(regionRelation, masterKey, metaData);
    }

    @Override
    public void reBuildEntityTable(ParamRelation regionRelation, DimensionCombination masterKey, IMetaData metaData, IEntityTableWrapper preEntityTable) {
        String cacheKey = this.buildParamCacheKey(regionRelation, masterKey, metaData);
        if (cacheKey == null) {
            IEntityTable entityTable = this.initTable(regionRelation, masterKey, metaData);
            preEntityTable.putIEntityTable(null, entityTable);
            preEntityTable.resetIEntityTable(null);
        } else {
            boolean exist = preEntityTable.resetIEntityTable(cacheKey);
            if (!exist) {
                IEntityTable entityTable = this.initTable(regionRelation, masterKey, metaData);
                preEntityTable.putIEntityTable(cacheKey, entityTable);
                preEntityTable.resetIEntityTable(cacheKey);
            }
        }
    }

    protected IEntityTableWrapper initTableWrapper(ParamRelation regionRelation, DimensionCombination masterKey, IMetaData metaData) {
        return this.initTableWrapper(regionRelation, masterKey, metaData, EntityQueryMode.DEFAULT.getValue());
    }

    protected IEntityTableWrapper initTableWrapper(ParamRelation regionRelation, DimensionCombination masterKey, IMetaData metaData, int mode) {
        return this.initTableWrapper(regionRelation, masterKey, metaData, QueryModeImpl.create(mode));
    }

    protected IEntityTableWrapper initTableWrapper(ParamRelation regionRelation, DimensionCombination masterKey, IMetaData metaData, QueryMode mode) {
        try {
            IEntityTable entityTable = this.initTable(regionRelation, masterKey, metaData, mode);
            String cacheKey = this.buildParamCacheKey(regionRelation, masterKey, metaData, mode.getEntityQueryMode());
            DefaultEntityTableWrapper defaultEntityTableWrapper = new DefaultEntityTableWrapper();
            defaultEntityTableWrapper.putIEntityTable(cacheKey, entityTable);
            defaultEntityTableWrapper.resetIEntityTable(cacheKey);
            return defaultEntityTableWrapper;
        }
        catch (Exception e) {
            LOGGER.error("\u5b9e\u4f53\u6570\u636e\u53d6\u6570\u9519\u8bef", e);
            throw new CrudException(4201, "\u5b9e\u4f53\u6570\u636e\u53d6\u6570\u9519\u8bef");
        }
    }

    protected IEntityTable initTable(ParamRelation regionRelation, DimensionCombination masterKey, IMetaData metaData) {
        return this.initTable(regionRelation, masterKey, metaData, QueryModeImpl.create(EntityQueryMode.DEFAULT.getValue()));
    }

    protected IEntityTable initTable(ParamRelation regionRelation, DimensionCombination masterKey, IMetaData metaData, int mode) {
        return this.initTable(regionRelation, masterKey, metaData, QueryModeImpl.create(mode));
    }

    protected IEntityTable initTable(ParamRelation regionRelation, DimensionCombination masterKey, IMetaData metaData, QueryMode mode) {
        IEntityQuery entityQuery = this.buildEntityQuery(regionRelation, masterKey, metaData, mode);
        ExecutorContext executorContext = this.buildExecutorContext(regionRelation, masterKey, metaData, mode.getEntityQueryMode());
        executorContext.setAutoDataMasking(mode.isDesensitized());
        try {
            return entityQuery.executeReader((IContext)executorContext);
        }
        catch (Exception e) {
            LOGGER.error("\u5b9e\u4f53\u6570\u636e\u53d6\u6570\u9519\u8bef", e);
            throw new CrudException(4201, "\u5b9e\u4f53\u6570\u636e\u53d6\u6570\u9519\u8bef");
        }
    }

    protected IEntityQuery buildEntityQuery(ParamRelation regionRelation, DimensionCombination masterKey, IMetaData metaData) {
        return this.buildEntityQuery(regionRelation, masterKey, metaData, QueryModeImpl.create(EntityQueryMode.DEFAULT.getValue()));
    }

    protected IEntityQuery buildEntityQuery(ParamRelation regionRelation, DimensionCombination masterKey, IMetaData metaData, int mode) {
        return this.buildEntityQuery(regionRelation, masterKey, metaData, QueryModeImpl.create(mode));
    }

    protected IEntityQuery buildEntityQuery(ParamRelation regionRelation, DimensionCombination masterKey, IMetaData metaData, QueryMode mode) {
        FormSchemeDefine formScheme;
        String formSchemeKey;
        String entityId;
        EntityViewDefine entityViewDefine;
        DataLinkDefine dataLinkDefine = metaData.getDataLinkDefine();
        if (dataLinkDefine != null) {
            String linkDefineKey = dataLinkDefine.getKey();
            entityViewDefine = this.runTimeViewController.getViewByLinkDefineKey(linkDefineKey);
        } else {
            DataField dataField = metaData.getDataField();
            if (dataField == null) {
                LOGGER.warn("\u53c2\u6570\u9519\u8bef,\u6307\u6807\u672a\u627e\u5230 {}", (Object)metaData);
                throw new IllegalArgumentException("\u53c2\u6570\u9519\u8bef,\u6307\u6807\u672a\u627e\u5230");
            }
            entityId = dataField.getRefDataEntityKey();
            entityViewDefine = this.entityViewRunTimeController.buildEntityView(entityId);
        }
        DsContext dsContext = DsContextHolder.getDsContext();
        entityId = dsContext.getContextEntityId();
        if (StringUtils.hasLength(entityId) && StringUtils.hasLength(formSchemeKey = regionRelation.getFormSchemeKey()) && (formScheme = this.runTimeViewController.getFormScheme(formSchemeKey)) != null && entityViewDefine.getEntityId().equals(formScheme.getDw())) {
            String filterExpression = dsContext.getContextFilterExpression();
            if (!StringUtils.hasLength(filterExpression)) {
                filterExpression = entityViewDefine.getRowFilterExpression();
            }
            entityViewDefine = this.entityViewRunTimeController.buildEntityView(entityId, filterExpression);
        }
        IEntityQuery entityQuery = this.entityDataService.newEntityQuery();
        entityQuery.setEntityView(entityViewDefine);
        if (masterKey != null) {
            DimensionCombination rebuildMasterKey = this.rebuildMasterKey(regionRelation, masterKey, metaData);
            entityQuery.setMasterKeys(rebuildMasterKey.toDimensionValueSet());
            String dwValue = this.getDwValue(regionRelation, masterKey);
            if ((mode.getEntityQueryMode() & EntityQueryMode.IGNORE_ISOLATE_CONDITION.getValue()) == 0) {
                entityQuery.setIsolateCondition(dwValue);
            }
            if (mode.isDesensitized()) {
                entityQuery.maskedData();
            }
        }
        return entityQuery;
    }

    protected DimensionCombination rebuildMasterKey(ParamRelation regionRelation, DimensionCombination masterKey, IMetaData metaData) {
        FixedDimensionValue periodDimensionValue;
        String dimName;
        String entityId = metaData.getEntityId();
        try {
            dimName = this.entityViewController.getDimensionNameByViewKey(entityId);
        }
        catch (Exception e) {
            LOGGER.error("\u7ef4\u5ea6\u67e5\u8be2\u5931\u8d25,\u8bf7\u68c0\u67e5\u53c2\u6570 {}", (Object)entityId, (Object)e);
            throw new CrudException(4302, "\u7ef4\u5ea6\u67e5\u8be2\u5931\u8d25,\u8bf7\u68c0\u67e5\u53c2\u6570" + entityId);
        }
        String dwDimName = regionRelation.getDwDimName();
        DimensionCombinationBuilder combinationBuilder = new DimensionCombinationBuilder();
        if (!dwDimName.equals(dimName) && masterKey.hasValue(dwDimName)) {
            FixedDimensionValue dwDimensionValue = masterKey.getDWDimensionValue();
            if (dwDimensionValue == null) {
                FixedDimensionValue dwFixValue = masterKey.getFixedDimensionValue(dwDimName);
                if (dwFixValue != null) {
                    combinationBuilder.setValue(dwFixValue);
                }
            } else {
                combinationBuilder.setValue(dwDimensionValue);
            }
        }
        if ((periodDimensionValue = masterKey.getPeriodDimensionValue()) != null) {
            combinationBuilder.setValue(periodDimensionValue);
        }
        return combinationBuilder.getCombination();
    }

    protected ExecutorContext buildExecutorContext(ParamRelation regionRelation, DimensionCombination masterKey, IMetaData metaData, int mode) {
        ExecutorContext executorContext = new ExecutorContext(this.dataDefinitionRuntimeController);
        executorContext.setJQReportModel(true);
        if (masterKey != null) {
            executorContext.setVarDimensionValueSet(masterKey.toDimensionValueSet());
            FixedDimensionValue periodDimensionValue = masterKey.getPeriodDimensionValue();
            if (periodDimensionValue != null) {
                String entityID = periodDimensionValue.getEntityID();
                executorContext.setPeriodView(entityID);
            }
        }
        executorContext.setEnv(regionRelation.getReportFmlExecEnvironment());
        executorContext.setDefaultGroupName(regionRelation.getDefaultGroupName());
        return executorContext;
    }

    protected ExecutorContext buildExecutorContext(ParamRelation regionRelation, DimensionCombination masterKey, IMetaData metaData) {
        return this.buildExecutorContext(regionRelation, masterKey, metaData, EntityQueryMode.DEFAULT.getValue());
    }

    protected String buildParamCacheKey(ParamRelation regionRelation, DimensionCombination masterKey, IMetaData metaData, int mode) {
        return null;
    }

    protected String buildParamCacheKey(ParamRelation regionRelation, DimensionCombination masterKey, IMetaData metaData) {
        return this.buildParamCacheKey(regionRelation, masterKey, metaData, EntityQueryMode.DEFAULT.getValue());
    }

    protected String getPeriodValue(DimensionCombination masterKey) {
        Object value;
        if (masterKey == null) {
            return null;
        }
        String periodValue = null;
        FixedDimensionValue periodDimensionValue = masterKey.getPeriodDimensionValue();
        if (periodDimensionValue != null && (value = periodDimensionValue.getValue()) != null) {
            periodValue = value.toString();
        }
        return periodValue;
    }

    protected String getDwValue(ParamRelation regionRelation, DimensionCombination masterKey) {
        if (masterKey == null) {
            return null;
        }
        boolean hasDw = masterKey.hasValue(regionRelation.getDwDimName());
        if (hasDw) {
            Object value = masterKey.getValue(regionRelation.getDwDimName());
            if (value == null) {
                return null;
            }
            return value.toString();
        }
        return null;
    }

    @Override
    public IEntityTableWrapper getEntityTable(ParamRelation regionRelation, DimensionCombination masterKey, IMetaData metaData, int mode) {
        return this.getEntityTable(regionRelation, masterKey, metaData, QueryModeImpl.create(mode));
    }

    @Override
    public void reBuildEntityTable(ParamRelation regionRelation, DimensionCombination masterKey, IMetaData metaData, IEntityTableWrapper preEntityTable, int mode) {
        this.reBuildEntityTable(regionRelation, masterKey, metaData, preEntityTable, QueryModeImpl.create(mode));
    }

    @Override
    public IEntityTableWrapper getEntityTable(ParamRelation regionRelation, DimensionCombination masterKey, IMetaData metaData, QueryMode mode) {
        return this.initTableWrapper(regionRelation, masterKey, metaData, mode);
    }

    @Override
    public void reBuildEntityTable(ParamRelation regionRelation, DimensionCombination masterKey, IMetaData metaData, IEntityTableWrapper preEntityTable, QueryMode mode) {
        String cacheKey = this.buildParamCacheKey(regionRelation, masterKey, metaData, mode.getEntityQueryMode());
        if (cacheKey == null) {
            IEntityTable entityTable = this.initTable(regionRelation, masterKey, metaData, mode);
            preEntityTable.putIEntityTable(null, entityTable);
            preEntityTable.resetIEntityTable(null);
        } else {
            boolean exist = preEntityTable.resetIEntityTable(cacheKey);
            if (!exist) {
                IEntityTable entityTable = this.initTable(regionRelation, masterKey, metaData, mode);
                preEntityTable.putIEntityTable(cacheKey, entityTable);
                preEntityTable.resetIEntityTable(cacheKey);
            }
        }
    }
}

