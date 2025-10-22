/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.nr.batch.summary.storage.condition.CustomConditionHelper
 *  com.jiuqi.nr.batch.summary.storage.condition.CustomConditionRowProvider
 *  com.jiuqi.nr.batch.summary.storage.entity.SchemeTargetDim
 *  com.jiuqi.nr.batch.summary.storage.entity.SummaryScheme
 *  com.jiuqi.nr.calibre2.ICalibreDataRegionService
 *  com.jiuqi.nr.calibre2.ICalibreDataService
 *  com.jiuqi.nr.calibre2.ICalibreDefineService
 *  com.jiuqi.nr.calibre2.common.Result
 *  com.jiuqi.nr.calibre2.domain.CalibreDataDO
 *  com.jiuqi.nr.calibre2.domain.CalibreDataRegion
 *  com.jiuqi.nr.calibre2.domain.CalibreDefineDO
 *  com.jiuqi.nr.calibre2.domain.CalibreDefineDTO
 *  com.jiuqi.nr.context.cxt.DsContext
 *  com.jiuqi.nr.context.cxt.DsContextHolder
 *  com.jiuqi.nr.data.access.util.DataAccesslUtil
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 *  com.jiuqi.nr.entity.engine.executors.ExecutorContext
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.engine.setting.AuthorityType
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.model.IEntityRefer
 *  com.jiuqi.nr.entity.service.IEntityDataService
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.batch.summary.service.targetdim;

import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.nr.batch.summary.service.targetdim.BaseDataTargetDimProvider;
import com.jiuqi.nr.batch.summary.service.targetdim.CalibreDataTargetDimProvider;
import com.jiuqi.nr.batch.summary.service.targetdim.ConditionTargetDimProvider;
import com.jiuqi.nr.batch.summary.service.targetdim.RangeOfAllEntities;
import com.jiuqi.nr.batch.summary.service.targetdim.RangeOfCheckEntities;
import com.jiuqi.nr.batch.summary.service.targetdim.RangeOfExpressFilter;
import com.jiuqi.nr.batch.summary.service.targetdim.TargetDimProvider;
import com.jiuqi.nr.batch.summary.service.targetdim.TargetDimProviderFactory;
import com.jiuqi.nr.batch.summary.service.targetdim.TargetRangeUnitProvider;
import com.jiuqi.nr.batch.summary.storage.condition.CustomConditionHelper;
import com.jiuqi.nr.batch.summary.storage.condition.CustomConditionRowProvider;
import com.jiuqi.nr.batch.summary.storage.entity.SchemeTargetDim;
import com.jiuqi.nr.batch.summary.storage.entity.SummaryScheme;
import com.jiuqi.nr.calibre2.ICalibreDataRegionService;
import com.jiuqi.nr.calibre2.ICalibreDataService;
import com.jiuqi.nr.calibre2.ICalibreDefineService;
import com.jiuqi.nr.calibre2.common.Result;
import com.jiuqi.nr.calibre2.domain.CalibreDataDO;
import com.jiuqi.nr.calibre2.domain.CalibreDataRegion;
import com.jiuqi.nr.calibre2.domain.CalibreDefineDO;
import com.jiuqi.nr.calibre2.domain.CalibreDefineDTO;
import com.jiuqi.nr.context.cxt.DsContext;
import com.jiuqi.nr.context.cxt.DsContextHolder;
import com.jiuqi.nr.data.access.util.DataAccesslUtil;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import com.jiuqi.nr.entity.engine.executors.ExecutorContext;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.engine.setting.AuthorityType;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.model.IEntityRefer;
import com.jiuqi.nr.entity.service.IEntityDataService;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TargetDimProviderFactoryImpl
implements TargetDimProviderFactory {
    @Resource
    public IEntityDataService entityDataService;
    @Resource
    public IEntityMetaService entityMetaService;
    @Resource
    public IRunTimeViewController runTimeViewController;
    @Resource
    public IEntityViewRunTimeController entityViewRunTimeController;
    @Resource
    public IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    @Resource
    public IPeriodEntityAdapter periodAdapter;
    @Resource
    public ICalibreDataService calibreDataService;
    @Resource
    public ICalibreDefineService calibreDefineService;
    @Resource
    public ICalibreDataRegionService calibreDataRegionService;
    @Resource
    public CustomConditionHelper customConditionHelper;
    @Autowired
    private DataAccesslUtil dataAccesslUtil;

    @Override
    public TargetDimProvider getTargetDimProvider(SummaryScheme summaryScheme) {
        TargetRangeUnitProvider rangeUnitProvider = new RangeOfAllEntities();
        switch (summaryScheme.getRangeUnit().getRangeUnitType()) {
            case ALL: {
                break;
            }
            case CHECK_UNIT: {
                rangeUnitProvider = new RangeOfCheckEntities(summaryScheme, this);
                break;
            }
            case EXPRESSION: {
                rangeUnitProvider = new RangeOfExpressFilter(summaryScheme, this);
            }
        }
        switch (summaryScheme.getTargetDim().getTargetDimType()) {
            case BASE_DATA: {
                return this.makeBaseDataTargetDim(summaryScheme, rangeUnitProvider);
            }
            case CALIBRE: {
                return this.makeCalibreTargetDim(summaryScheme, rangeUnitProvider);
            }
            case CONDITION: {
                return this.makeConditionTargetDim(summaryScheme, rangeUnitProvider);
            }
        }
        return null;
    }

    private TargetDimProvider makeBaseDataTargetDim(SummaryScheme summaryScheme, TargetRangeUnitProvider rangeUnitProvider) {
        SchemeTargetDim targetDimConf = summaryScheme.getTargetDim();
        String referEntityId = targetDimConf.getDimValue();
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(summaryScheme.getTask());
        IEntityDefine entityDefine = this.entityMetaService.queryEntity(this.dataAccesslUtil.contextEntityId(taskDefine.getDw()));
        List entityRefers = this.entityMetaService.getEntityRefer(entityDefine.getId());
        IEntityRefer entityRefer = entityRefers.stream().filter(refer -> refer.getReferEntityId().equals(referEntityId)).findFirst().get();
        return new BaseDataTargetDimProvider(this, rangeUnitProvider, summaryScheme, entityRefer, entityDefine);
    }

    private TargetDimProvider makeCalibreTargetDim(SummaryScheme summaryScheme, TargetRangeUnitProvider rangeUnitProvider) {
        return new CalibreDataTargetDimProvider(this, rangeUnitProvider, summaryScheme);
    }

    private TargetDimProvider makeConditionTargetDim(SummaryScheme summaryScheme, TargetRangeUnitProvider rangeUnitProvider) {
        CustomConditionRowProvider conditionRowProvider = this.customConditionHelper.getTreeProvider(summaryScheme.getKey());
        return new ConditionTargetDimProvider(summaryScheme, conditionRowProvider, rangeUnitProvider, this);
    }

    public FormSchemeDefine queryFormSchemeDefine(String taskId, String period) {
        try {
            SchemePeriodLinkDefine schemePeriodLinkDefine = this.runTimeViewController.querySchemePeriodLinkByPeriodAndTask(period, taskId);
            if (schemePeriodLinkDefine != null) {
                return this.runTimeViewController.getFormScheme(schemePeriodLinkDefine.getSchemeKey());
            }
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public CalibreDefineDTO queryCalibreDefine(String calibreDefineId) {
        CalibreDefineDTO calibreDefineDTO = new CalibreDefineDTO();
        calibreDefineDTO.setCode(calibreDefineId);
        Result rs = this.calibreDefineService.get(calibreDefineDTO);
        return (CalibreDefineDTO)rs.getData();
    }

    public List<CalibreDataDO> listCalibreValues(String calibreDefineId) {
        Result listResult = this.calibreDataService.listAll(calibreDefineId);
        return (List)listResult.getData();
    }

    public List<CalibreDataRegion> listCalibreDataItems(ExecutorContext executorContext, CalibreDefineDO calibreDefine, DimensionValueSet masterKeys) {
        try {
            return this.calibreDataRegionService.getList((com.jiuqi.np.dataengine.executors.ExecutorContext)executorContext, calibreDefine, masterKeys);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public IEntityQuery newEntityQuery(String entityId) {
        IEntityQuery entityQuery = this.entityDataService.newEntityQuery();
        entityQuery.setEntityView(this.entityViewRunTimeController.buildEntityView(entityId));
        return entityQuery;
    }

    public IEntityQuery newEntityQuery(String taskKey, String period) {
        FormSchemeDefine formSchemeDefine = this.queryFormSchemeDefine(taskKey, period);
        IEntityQuery query = this.entityDataService.newEntityQuery();
        query.setEntityView(this.getEntityViewDefine(formSchemeDefine.getKey()));
        DimensionValueSet dimensionValueSet = new DimensionValueSet();
        dimensionValueSet.setValue(this.getPeriodDimName(formSchemeDefine.getDateTime()), (Object)period);
        query.setMasterKeys(dimensionValueSet);
        query.setAuthorityOperations(AuthorityType.Read);
        return query;
    }

    private EntityViewDefine getEntityViewDefine(String formSchemeKey) {
        EntityViewDefine entityView = this.runTimeViewController.getViewByFormSchemeKey(formSchemeKey);
        DsContext dsContext = DsContextHolder.getDsContext();
        String entityId = dsContext.getContextEntityId();
        String filterExpression = dsContext.getContextFilterExpression();
        if (StringUtils.isNotEmpty((String)entityId)) {
            if (StringUtils.isEmpty((String)filterExpression)) {
                filterExpression = entityView.getRowFilterExpression();
            }
            return this.entityViewRunTimeController.buildEntityView(entityId, filterExpression, entityView.getFilterRowByAuthority());
        }
        return entityView;
    }

    public ExecutorContext newEntityQueryContext(String taskKey, String period) {
        FormSchemeDefine formSchemeDefine = this.queryFormSchemeDefine(taskKey, period);
        String periodEntityId = formSchemeDefine.getDateTime();
        String formSchemeKey = formSchemeDefine.getKey();
        ExecutorContext executorContext = new ExecutorContext(this.dataDefinitionRuntimeController);
        executorContext.setPeriodView(periodEntityId);
        if (executorContext.getEnv() == null) {
            executorContext.setEnv(this.newEntityQueryEnvironment(formSchemeKey));
        }
        return executorContext;
    }

    public IEntityTable executeWithReader(IEntityQuery query, IContext context) {
        try {
            return query.executeReader(context);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String getPeriodDimName(String periodEntityId) {
        return this.periodAdapter.getPeriodEntity(periodEntityId).getDimensionName();
    }

    public String getPeriodDimName(String taskKey, String period) {
        FormSchemeDefine formSchemeDefine = this.queryFormSchemeDefine(taskKey, period);
        return this.periodAdapter.getPeriodEntity(formSchemeDefine.getDateTime()).getDimensionName();
    }

    public String getMainDimName(String entityId) {
        return this.entityMetaService.queryEntity(entityId).getDimensionName();
    }

    public String getMainDimName(String taskKey, String period) {
        FormSchemeDefine formSchemeDefine = this.queryFormSchemeDefine(taskKey, period);
        return this.entityMetaService.queryEntity(this.dataAccesslUtil.contextEntityId(formSchemeDefine.getDw())).getDimensionName();
    }

    public ExecutorContext newEntityQueryContext() {
        return new ExecutorContext(this.dataDefinitionRuntimeController);
    }

    public IFmlExecEnvironment newEntityQueryEnvironment(String formSchemeKey) {
        return new ReportFmlExecEnvironment(this.runTimeViewController, this.dataDefinitionRuntimeController, this.entityViewRunTimeController, formSchemeKey);
    }
}

