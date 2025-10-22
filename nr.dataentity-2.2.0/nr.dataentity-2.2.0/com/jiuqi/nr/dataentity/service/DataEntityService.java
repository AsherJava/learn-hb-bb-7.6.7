/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 *  com.jiuqi.np.definition.common.Consts
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.nr.context.cxt.DsContext
 *  com.jiuqi.nr.context.cxt.DsContextHolder
 *  com.jiuqi.nr.datascheme.api.DataDimension
 *  com.jiuqi.nr.datascheme.api.service.IEntityDataQueryAssist
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DimensionType
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationBuilder
 *  com.jiuqi.nr.dataservice.core.dimension.FixedDimensionValue
 *  com.jiuqi.nr.dataservice.core.dimension.provider.DimensionProviderData
 *  com.jiuqi.nr.dataservice.core.dimension.provider.DimensionProviderUtil
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.env.ExectuteFormula
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 *  com.jiuqi.nr.definition.service.IFormSchemeService
 *  com.jiuqi.nr.entity.engine.executors.ExecutorContext
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.engine.setting.AuthorityType
 *  com.jiuqi.nr.entity.engine.var.RangeQuery
 *  com.jiuqi.nr.entity.engine.var.ReferRelation
 *  com.jiuqi.nr.entity.internal.model.impl.EntityReferImpl
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.model.IEntityModel
 *  com.jiuqi.nr.entity.model.IEntityRefer
 *  com.jiuqi.nr.entity.service.IEntityDataService
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.nr.period.internal.adapter.IPeriodProvider
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.nr.period.modal.IPeriodEntity
 */
package com.jiuqi.nr.dataentity.service;

import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.definition.common.Consts;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.nr.context.cxt.DsContext;
import com.jiuqi.nr.context.cxt.DsContextHolder;
import com.jiuqi.nr.dataentity.entity.AdjustDimensionTable;
import com.jiuqi.nr.dataentity.entity.DataEntityType;
import com.jiuqi.nr.dataentity.entity.EntityDimensionTable;
import com.jiuqi.nr.dataentity.entity.IDataEntity;
import com.jiuqi.nr.dataentity.entity.IDimensionTable;
import com.jiuqi.nr.dataentity.entity.PeriodDimensionTable;
import com.jiuqi.nr.dataentity.entity.VariableExectuteFormula;
import com.jiuqi.nr.dataentity.entity.data.DataEntity;
import com.jiuqi.nr.dataentity.param.DataEntityContext;
import com.jiuqi.nr.datascheme.api.DataDimension;
import com.jiuqi.nr.datascheme.api.service.IEntityDataQueryAssist;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DimensionType;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationBuilder;
import com.jiuqi.nr.dataservice.core.dimension.FixedDimensionValue;
import com.jiuqi.nr.dataservice.core.dimension.provider.DimensionProviderData;
import com.jiuqi.nr.dataservice.core.dimension.provider.DimensionProviderUtil;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.env.ExectuteFormula;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import com.jiuqi.nr.definition.service.IFormSchemeService;
import com.jiuqi.nr.entity.engine.executors.ExecutorContext;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.engine.setting.AuthorityType;
import com.jiuqi.nr.entity.engine.var.RangeQuery;
import com.jiuqi.nr.entity.engine.var.ReferRelation;
import com.jiuqi.nr.entity.internal.model.impl.EntityReferImpl;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.entity.model.IEntityRefer;
import com.jiuqi.nr.entity.service.IEntityDataService;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nr.period.internal.adapter.IPeriodProvider;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nr.period.modal.IPeriodEntity;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class DataEntityService {
    private static final Logger log = LoggerFactory.getLogger(DataEntityService.class);
    @Autowired
    private IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    @Autowired
    private IEntityDataService entityDataService;
    @Autowired
    private IEntityViewRunTimeController entityViewRunTimeController;
    @Autowired
    private IPeriodEntityAdapter periodEntityAdapter;
    @Autowired
    private PeriodEngineService periodEngineService;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private IFormSchemeService formSchemeService;
    @Autowired
    private IEntityDataQueryAssist entityDataQueryAssist;
    @Autowired
    private DimensionProviderUtil providerUtil;
    @Autowired
    private IRuntimeDataSchemeService dataSchemeService;

    public IEntityDefine getEntityDefineInContext(String entityId) {
        String contextEntityId = this.getContextEntityId();
        return this.entityMetaService.queryEntity(com.jiuqi.bi.util.StringUtils.isEmpty((String)contextEntityId) ? entityId : contextEntityId);
    }

    public IEntityModel getEntityModelInContext(String entityId) {
        String contextEntityId = this.getContextEntityId();
        return this.entityMetaService.getEntityModel(com.jiuqi.bi.util.StringUtils.isEmpty((String)contextEntityId) ? entityId : contextEntityId);
    }

    public IDimensionTable getDimensionList(String formSchemeKey, EntityViewDefine entityViewDefine, DimensionCombination dimensionCombination, ExecutorContext executorContext) {
        FixedDimensionValue datatime = dimensionCombination.getFixedDimensionValue("DATATIME");
        if (this.periodEntityAdapter.isPeriodEntity(entityViewDefine.getEntityId())) {
            IPeriodProvider periodProvider = this.periodEntityAdapter.getPeriodProvider(entityViewDefine.getEntityId());
            List periodItems = periodProvider.getPeriodItems();
            return new PeriodDimensionTable(entityViewDefine.getEntityId(), periodItems);
        }
        if (entityViewDefine.getEntityId().equals("ADJUST")) {
            if (datatime.getValue() != null) {
                List adjustPeriods = this.formSchemeService.queryAdjustPeriods(formSchemeKey, datatime.getValue().toString());
                return new AdjustDimensionTable(adjustPeriods);
            }
            return new AdjustDimensionTable(Collections.emptyList());
        }
        IEntityQuery entityQuery = this.getEntityQuery(entityViewDefine, dimensionCombination);
        String entityId = entityViewDefine.getEntityId();
        if (com.jiuqi.bi.util.StringUtils.isNotEmpty((String)formSchemeKey)) {
            String dwDimensionName;
            FixedDimensionValue dwDimension;
            FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
            TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(formScheme.getTaskKey());
            List dimensions = this.dataSchemeService.getDataSchemeDimension(taskDefine.getDataScheme());
            Optional<DataDimension> dimensionOptional = dimensions.stream().filter(d -> d.getDimKey().equalsIgnoreCase(entityId)).findFirst();
            Optional<DataDimension> dwOptional = dimensions.stream().filter(d -> d.getDimensionType() == DimensionType.UNIT).findFirst();
            if (dimensionOptional.isPresent() && dwOptional.isPresent() && (dwDimension = dimensionCombination.getFixedDimensionValue(dwDimensionName = this.entityMetaService.getDimensionName(dwOptional.get().getDimKey()))) != null) {
                String dwValue = (String)dwDimension.getValue();
                ReferRelation relation = this.entityDataQueryAssist.buildReferRelation(taskDefine.getDataScheme(), entityId);
                relation.addRange(dwValue);
                entityQuery.addReferRelation(relation);
            }
        }
        try {
            IEntityTable entityTable = entityQuery.executeFullBuild((IContext)executorContext);
            List rows = entityTable.getAllRows();
            return new EntityDimensionTable(entityId, this.entityMetaService.getDimensionName(entityId), rows);
        }
        catch (Exception e) {
            log.error("\u67e5\u8be2\u5b9e\u4f53\u6570\u636e\u51fa\u9519\u3002", e);
            return new EntityDimensionTable(entityId, this.entityMetaService.getDimensionName(entityId), Collections.emptyList());
        }
    }

    public IDataEntity getIEntityTable(EntityViewDefine entityView, ExecutorContext context, DimensionValueSet dataTime, String formSchemeKey) throws Exception {
        return this.getIEntityTable(entityView, context, dataTime, formSchemeKey, null);
    }

    public IDataEntity getIEntityRangeTable(EntityViewDefine entityView, ExecutorContext context, DimensionValueSet dataTime, String formSchemeKey, RangeQuery rangeQuery) throws Exception {
        if (this.periodEntityAdapter.isPeriodEntity(entityView.getEntityId())) {
            return this.queryPeriodEntity(entityView.getEntityId());
        }
        if (entityView.getEntityId().equals("ADJUST")) {
            if (dataTime.getValue("DATATIME") != null) {
                return this.queryAdjustPeriods(formSchemeKey, dataTime.getValue("DATATIME").toString());
            }
            return this.queryAdjust(formSchemeKey);
        }
        entityView = this.getNrContextEntityId(entityView, formSchemeKey);
        IEntityQuery entityQuery = this.entityDataService.newEntityQuery();
        entityQuery.setEntityView(entityView);
        entityQuery.setAuthorityOperations(AuthorityType.None);
        entityQuery.setMasterKeys(dataTime);
        IEntityTable entityTables = entityQuery.executeRangeBuild((IContext)context, rangeQuery);
        return new DataEntity(entityTables, DataEntityType.DataEntity);
    }

    public IDataEntity getIEntityTable(EntityViewDefine entityView, ExecutorContext context, DimensionValueSet dataTime, String formSchemeKey, String entityIsolateCondition) throws Exception {
        if (this.periodEntityAdapter.isPeriodEntity(entityView.getEntityId())) {
            return this.queryPeriodEntity(entityView.getEntityId());
        }
        if (entityView.getEntityId().equals("ADJUST")) {
            if (dataTime.getValue("DATATIME") != null) {
                return this.queryAdjustPeriods(formSchemeKey, dataTime.getValue("DATATIME").toString());
            }
            return this.queryAdjust(formSchemeKey);
        }
        entityView = this.getNrContextEntityId(entityView, formSchemeKey);
        IEntityQuery entityQuery = this.entityDataService.newEntityQuery();
        entityQuery.setEntityView(entityView);
        entityQuery.setAuthorityOperations(AuthorityType.None);
        entityQuery.setMasterKeys(dataTime);
        entityQuery.setIsolateCondition(entityIsolateCondition);
        IEntityTable entityTables = entityQuery.executeFullBuild((IContext)context);
        return new DataEntity(entityTables, DataEntityType.DataEntity);
    }

    public IDataEntity getIEntityTable(EntityViewDefine entityView, ExecutorContext context, DimensionValueSet dataTime, String formSchemeKey, String entityIsolateCondition, boolean readAuth) throws Exception {
        if (this.periodEntityAdapter.isPeriodEntity(entityView.getEntityId())) {
            return this.queryPeriodEntity(entityView.getEntityId());
        }
        if (entityView.getEntityId().equals("ADJUST")) {
            if (dataTime.getValue("DATATIME") != null) {
                return this.queryAdjustPeriods(formSchemeKey, dataTime.getValue("DATATIME").toString());
            }
            return this.queryAdjust(formSchemeKey);
        }
        entityView = this.getNrContextEntityId(entityView, formSchemeKey);
        IEntityQuery entityQuery = this.entityDataService.newEntityQuery();
        entityQuery.setEntityView(entityView);
        entityQuery.setAuthorityOperations(readAuth ? AuthorityType.Read : AuthorityType.None);
        entityQuery.setMasterKeys(dataTime);
        entityQuery.setIsolateCondition(entityIsolateCondition);
        IEntityTable entityTables = entityQuery.executeFullBuild((IContext)context);
        return new DataEntity(entityTables, DataEntityType.DataEntity);
    }

    public IDataEntity queryEntity(EntityViewDefine entityView, DataEntityContext context, boolean readAuth, String entityIsolateCondition) {
        return this.queryEntity(entityView, context, readAuth, new DimensionValueSet(), entityIsolateCondition);
    }

    public IDataEntity queryEntityWithDimVal(EntityViewDefine entityView, DataEntityContext context, boolean readAuth, DimensionValueSet dimensionValueSet, String entityIsolateCondition) {
        return this.queryEntity(entityView, context, readAuth, dimensionValueSet, entityIsolateCondition);
    }

    protected IDataEntity queryPeriodEntity(String entityId) {
        IPeriodEntityAdapter periodAdapter = this.periodEngineService.getPeriodAdapter();
        EntityViewDefine viewDefine = this.entityViewRunTimeController.buildEntityView(entityId);
        IPeriodEntity periodEntity = periodAdapter.getPeriodEntity(viewDefine.getEntityId());
        DataEntity dataEntry = new DataEntity(DataEntityType.DataEntityPeriod);
        dataEntry.setPeriodEntity(periodEntity);
        return dataEntry;
    }

    protected IDataEntity queryAdjust(String formSchemeKey) {
        List queryAdjustPeriods = this.formSchemeService.queryAdjustPeriods(formSchemeKey);
        return new DataEntity(null, queryAdjustPeriods, null, DataEntityType.DataEntityAdjust);
    }

    protected IDataEntity queryAdjustPeriods(String formSchemeKey, String period) {
        List queryAdjustPeriods = this.formSchemeService.queryAdjustPeriods(formSchemeKey, period);
        return new DataEntity(null, queryAdjustPeriods, null, DataEntityType.DataEntityAdjust);
    }

    private IDataEntity queryEntity(EntityViewDefine entityView, DataEntityContext context, boolean readAuth, DimensionValueSet dimensionValueSet, String entityIsolateCondition) {
        if (context == null) {
            throw new IllegalArgumentException("DataEntityContext is null");
        }
        boolean queryByKey = context.isQueryByKey();
        boolean sorted = context.isSorted();
        if (this.periodEntityAdapter.isPeriodEntity(entityView.getEntityId())) {
            return this.queryPeriodEntity(entityView.getEntityId());
        }
        if (entityView.getEntityId().equals("ADJUST")) {
            if (dimensionValueSet.getValue("DATATIME") != null) {
                return this.queryAdjustPeriods(context.getFormSchemeKey(), dimensionValueSet.getValue("DATATIME").toString());
            }
            return null;
        }
        boolean queryDim = context.isQueryDim();
        if (!queryDim) {
            entityView = this.getNrContextEntityId(entityView, context.getFormSchemeKey());
        }
        ExecutorContext oldContext = this.getExecutorContext(context, dimensionValueSet);
        ExecutorContext executorContext = new ExecutorContext(oldContext.getRuntimeController());
        executorContext.setDefaultGroupName(oldContext.getDefaultGroupName());
        executorContext.setJQReportModel(oldContext.isJQReportModel());
        executorContext.setVarDimensionValueSet(oldContext.getVarDimensionValueSet());
        executorContext.setEnv(oldContext.getEnv());
        EntityViewDefine entityViewDefine = this.entityViewRunTimeController.buildEntityView(entityView.getEntityId(), entityView.getRowFilterExpression(), entityView.getFilterRowByAuthority());
        IEntityQuery entityQuery = this.entityDataService.newEntityQuery();
        entityQuery.sorted(sorted);
        entityQuery.setEntityView(entityViewDefine);
        entityQuery.setAuthorityOperations(readAuth ? AuthorityType.Read : AuthorityType.None);
        if (com.jiuqi.bi.util.StringUtils.isNotEmpty((String)entityView.getRowFilterExpression())) {
            entityQuery.setRowFilter(entityView.getRowFilterExpression());
        }
        if (context.getTaskKey() == null) {
            FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(context.getFormSchemeKey());
            if (formScheme == null) {
                throw new IllegalArgumentException("FormSchemeDefine is null");
            }
            context.setFormSchemeKey(formScheme.getKey());
        }
        TaskDefine queryTaskDefine = null;
        if (context.getTaskKey() != null) {
            queryTaskDefine = this.runTimeViewController.queryTaskDefine(context.getTaskKey());
        } else {
            FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(context.getFormSchemeKey());
            queryTaskDefine = this.runTimeViewController.queryTaskDefine(formScheme.getTaskKey());
        }
        String dimensionName = this.entityMetaService.getDimensionName(entityView.getEntityId());
        if (dimensionName != null && "MD_CURRENCY".equals(dimensionName)) {
            DataDimension schemeDWDimension;
            String dwDimensionName;
            FixedDimensionValue dwDimension;
            DimensionProviderData dimensionProviderData = new DimensionProviderData(null, queryTaskDefine.getDataScheme());
            DimensionCombinationBuilder builder = new DimensionCombinationBuilder(dimensionValueSet);
            builder.setValue(dimensionName, dimensionValueSet.getValue(dimensionName));
            DimensionCombination dimensionCombination = builder.getCombination();
            List dimensions = this.dataSchemeService.getDataSchemeDimension(dimensionProviderData.getDataSchemeKey());
            Optional<DataDimension> dwOptional = dimensions.stream().filter(d -> d.getDimensionType() == DimensionType.UNIT).findFirst();
            if (dwOptional.isPresent() && (dwDimension = dimensionCombination.getFixedDimensionValue(dwDimensionName = this.entityMetaService.getDimensionName((schemeDWDimension = dwOptional.get()).getDimKey()))) != null) {
                ReferRelation relation = new ReferRelation();
                EntityReferImpl entityRefer = new EntityReferImpl();
                entityRefer.setReferEntityId("MD_CURRENCY@BASE");
                String entityID = "";
                entityID = !StringUtils.hasText(dwDimension.getEntityID()) ? schemeDWDimension.getDimKey() : dwDimension.getEntityID();
                entityRefer.setOwnEntityId(entityID);
                entityRefer.setReferEntityField("OBJECTCODE");
                entityRefer.setOwnField("CURRENCYID");
                relation.setRefer((IEntityRefer)entityRefer);
                relation.addRange(dwDimension.getValue().toString());
                relation.setViewDefine(this.entityViewRunTimeController.buildEntityView(entityID, dimensionProviderData.getFilter()));
                entityQuery.addReferRelation(relation);
            }
        } else {
            ReferRelation buildReferRelation = this.entityDataQueryAssist.buildReferRelation(queryTaskDefine.getDataScheme(), entityView.getEntityId());
            if (buildReferRelation != null) {
                entityQuery.addReferRelation(buildReferRelation);
            }
        }
        entityQuery.setMasterKeys(new DimensionValueSet());
        if (com.jiuqi.bi.util.StringUtils.isNotEmpty((String)context.getFormSchemeKey())) {
            String dwEntity = this.getDwEntity(context.getFormSchemeKey());
            String dwEntityDimensionName = null;
            if (this.periodEntityAdapter.isPeriodEntity(dwEntity)) {
                IPeriodEntity periodEntity = this.periodEntityAdapter.getPeriodEntity(dwEntity);
                dwEntityDimensionName = periodEntity.getDimensionName();
            } else {
                IEntityDefine entityDefine = this.entityMetaService.queryEntity(dwEntity);
                dwEntityDimensionName = entityDefine.getDimensionName();
            }
            Object o = dimensionValueSet.getValue(dwEntityDimensionName);
            if (o != null) {
                EntityViewDefine dwEntityViewDefine = this.entityViewRunTimeController.buildEntityView(dwEntity);
                executorContext.setMainView(dwEntityViewDefine);
            }
        }
        if (dimensionValueSet.getValue("DATATIME") != null) {
            String period = dimensionValueSet.getValue("DATATIME").toString();
            String dataTimeEntity = this.getDataTimeEntity(context.getFormSchemeKey());
            IPeriodProvider periodProvider = this.periodEngineService.getPeriodAdapter().getPeriodProvider(dataTimeEntity);
            Date versionDate = null;
            try {
                versionDate = periodProvider.getPeriodDateRegion(period)[1];
            }
            catch (ParseException e) {
                log.error("\u83b7\u53d6\u65f6\u671f\u5931\u8d25" + e.getMessage());
            }
            entityQuery.setQueryVersionDate(versionDate);
        }
        IEntityTable entityTable = null;
        try {
            entityTable = queryByKey ? entityQuery.executeReader((IContext)executorContext) : entityQuery.executeFullBuild((IContext)executorContext);
        }
        catch (Exception e) {
            StringBuilder logInfo = this.getEntityQueryErrorInfo(entityView.getEntityId(), e.getMessage());
            log.error(logInfo.toString(), e);
            throw new IllegalArgumentException("\u5b9e\u4f53\u67e5\u8be2\u51fa\u9519");
        }
        ArrayList<IEntityTable> enittyTableList = new ArrayList<IEntityTable>();
        enittyTableList.add(entityTable);
        return new DataEntity(entityTable, DataEntityType.DataEntity);
    }

    private EntityViewDefine getNrContextEntityId(EntityViewDefine entityView, String formSchemeKey) {
        DsContext dsContext = DsContextHolder.getDsContext();
        String entityId = dsContext.getContextEntityId();
        String filterExpression = dsContext.getContextFilterExpression();
        if (com.jiuqi.bi.util.StringUtils.isNotEmpty((String)entityId) && !this.isQueryDim(entityView, formSchemeKey)) {
            if (com.jiuqi.bi.util.StringUtils.isEmpty((String)filterExpression)) {
                filterExpression = entityView.getRowFilterExpression();
            }
            return this.entityViewRunTimeController.buildEntityView(entityId, filterExpression, entityView.getFilterRowByAuthority());
        }
        return entityView;
    }

    private boolean isQueryDim(EntityViewDefine entityView, String formSchemeKey) {
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
        String dims = formScheme.getDims();
        if (com.jiuqi.bi.util.StringUtils.isNotEmpty((String)dims)) {
            return dims.contains(entityView.getEntityId());
        }
        return false;
    }

    private StringBuilder getEntityQueryErrorInfo(String entityTitle, String message) {
        StringBuilder errorInfo = new StringBuilder();
        errorInfo.append("\u5b9e\u4f53[").append(entityTitle).append("]\u67e5\u8be2\u51fa\u9519\uff0c\u51fa\u9519\u539f\u56e0\uff1a").append(message);
        return errorInfo;
    }

    private ExecutorContext getExecutorContext(DataEntityContext context, DimensionValueSet dimensionValueSet) {
        FormDefine form;
        ExecutorContext executorContext = new ExecutorContext(this.dataDefinitionRuntimeController);
        if (com.jiuqi.bi.util.StringUtils.isNotEmpty((String)context.getFormKey()) && !context.getFormKey().contains(";") && (form = this.runTimeViewController.queryFormById(context.getFormKey())) != null) {
            executorContext.setDefaultGroupName(form.getFormCode());
        }
        executorContext.setJQReportModel(true);
        if (dimensionValueSet == null) {
            throw new IllegalArgumentException("dimensionValueSet is null");
        }
        DimensionValueSet varDimensionValueSet = new DimensionValueSet();
        for (int i = 0; i < dimensionValueSet.size(); ++i) {
            String dimensionName = dimensionValueSet.getName(i);
            Object dimensionValue = dimensionValueSet.getValue(i);
            if (dimensionValue instanceof List) continue;
            varDimensionValueSet.setValue(dimensionName, dimensionValue);
        }
        executorContext.setVarDimensionValueSet(varDimensionValueSet);
        VariableExectuteFormula variableExectuteFormula = new VariableExectuteFormula(context.getFormSchemeKey(), context.getFormKey(), varDimensionValueSet);
        ReportFmlExecEnvironment environment = new ReportFmlExecEnvironment(this.runTimeViewController, this.dataDefinitionRuntimeController, this.entityViewRunTimeController, context.getFormSchemeKey(), (ExectuteFormula)variableExectuteFormula, null);
        executorContext.setEnv((IFmlExecEnvironment)environment);
        return executorContext;
    }

    private String getDwEntity(String formSchemeKey) {
        FormSchemeDefine formSchemeDefine = null;
        try {
            formSchemeDefine = this.runTimeViewController.getFormScheme(formSchemeKey);
        }
        catch (Exception e) {
            log.error("\u62a5\u8868\u65b9\u6848\u672a\u627e\u5230:" + formSchemeKey, e);
            throw new IllegalArgumentException(formSchemeKey);
        }
        if (formSchemeDefine == null) {
            return null;
        }
        return formSchemeDefine.getDw();
    }

    private String getDataTimeEntity(String formSchemeKey) {
        FormSchemeDefine formSchemeDefine = null;
        try {
            formSchemeDefine = this.runTimeViewController.getFormScheme(formSchemeKey);
        }
        catch (Exception e) {
            log.error("\u62a5\u8868\u65b9\u6848\u672a\u627e\u5230:" + formSchemeKey, e);
            throw new IllegalArgumentException(formSchemeKey);
        }
        if (formSchemeDefine == null) {
            return null;
        }
        return formSchemeDefine.getDateTime();
    }

    private IEntityQuery getEntityQuery(EntityViewDefine entityViewDefine, DimensionCombination dimensionCombination) {
        IEntityQuery entityQuery = this.entityDataService.newEntityQuery();
        entityQuery.setEntityView(entityViewDefine);
        FixedDimensionValue periodDimension = dimensionCombination.getFixedDimensionValue("DATATIME");
        if (periodDimension == null) {
            entityQuery.setQueryVersionDate(Consts.DATE_VERSION_FOR_ALL);
        } else {
            try {
                IPeriodProvider periodProvider = this.periodEntityAdapter.getPeriodProvider(periodDimension.getEntityID());
                entityQuery.setQueryVersionDate(periodProvider.getPeriodDateRegion((String)periodDimension.getValue())[1]);
            }
            catch (Exception e) {
                entityQuery.setQueryVersionDate(Consts.DATE_VERSION_FOR_ALL);
                log.error("\u83b7\u53d6\u65f6\u671f\u5931\u8d25" + e.getMessage());
            }
        }
        entityQuery.setAuthorityOperations(AuthorityType.None);
        return entityQuery;
    }

    private String getContextEntityId() {
        DsContext dsContext = DsContextHolder.getDsContext();
        return dsContext.getContextEntityId();
    }
}

