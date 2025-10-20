/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.dto.fetch.request.FetchRequestDTO
 *  com.jiuqi.bde.common.dto.fetch.result.FetchResultDTO
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.bde.fetchsetting.impl.dao.FetchSettingDao
 *  com.jiuqi.gcreport.bde.fetchsetting.impl.service.FetchFloatSettingService
 *  com.jiuqi.gcreport.bde.fetchsetting.impl.service.FloatDimensionSettingService
 *  com.jiuqi.gcreport.nrextracteditctrl.intf.INrExtractSchemeConfigService
 *  com.jiuqi.np.dataengine.IDataAccessProvider
 *  com.jiuqi.np.dataengine.QueryEnvironment
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IDataQuery
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.datacrud.api.IDataQueryService
 *  com.jiuqi.nr.definition.common.FormulaSyntaxStyle
 *  com.jiuqi.nr.definition.controller.IFormulaRunTimeController
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 *  com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeDataLinkService
 *  com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeDataRegionService
 *  com.jiuqi.nr.definition.internal.runtime.controller.RuntimeViewController
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  com.jiuqi.nr.jtable.service.IJtableEntityService
 *  com.jiuqi.nr.jtable.util.DimensionValueSetUtil
 *  com.jiuqi.va.domain.common.JSONUtil
 */
package com.jiuqi.gcreport.bde.fetch.impl.handler;

import com.jiuqi.bde.common.dto.fetch.request.FetchRequestDTO;
import com.jiuqi.bde.common.dto.fetch.result.FetchResultDTO;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.bde.fetch.impl.handler.IFetchResultHandler;
import com.jiuqi.gcreport.bde.fetchsetting.impl.dao.FetchSettingDao;
import com.jiuqi.gcreport.bde.fetchsetting.impl.service.FetchFloatSettingService;
import com.jiuqi.gcreport.bde.fetchsetting.impl.service.FloatDimensionSettingService;
import com.jiuqi.gcreport.nrextracteditctrl.intf.INrExtractSchemeConfigService;
import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.dataengine.QueryEnvironment;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IDataQuery;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.datacrud.api.IDataQueryService;
import com.jiuqi.nr.definition.common.FormulaSyntaxStyle;
import com.jiuqi.nr.definition.controller.IFormulaRunTimeController;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeDataLinkService;
import com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeDataRegionService;
import com.jiuqi.nr.definition.internal.runtime.controller.RuntimeViewController;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.service.IJtableEntityService;
import com.jiuqi.nr.jtable.util.DimensionValueSetUtil;
import com.jiuqi.va.domain.common.JSONUtil;
import java.util.List;
import java.util.Map;

public abstract class AbstractFetchResultHandler
implements IFetchResultHandler {
    IDataDefinitionRuntimeController dataDefinitionRuntimeController = (IDataDefinitionRuntimeController)SpringContextUtils.getBean(IDataDefinitionRuntimeController.class);
    RuntimeViewController runTimeViewController = (RuntimeViewController)SpringContextUtils.getBean(RuntimeViewController.class);
    IEntityViewRunTimeController entityViewRunTimeController = (IEntityViewRunTimeController)SpringContextUtils.getBean(IEntityViewRunTimeController.class);
    IRuntimeDataLinkService runtimeDataLinkService = (IRuntimeDataLinkService)SpringContextUtils.getBean(IRuntimeDataLinkService.class);
    IRuntimeDataRegionService runtimeDataRegionService = (IRuntimeDataRegionService)SpringContextUtils.getBean(IRuntimeDataRegionService.class);
    IDataAccessProvider dataAccessProvider = (IDataAccessProvider)SpringContextUtils.getBean(IDataAccessProvider.class);
    FetchSettingDao fetchSettingDao = (FetchSettingDao)SpringContextUtils.getBean(FetchSettingDao.class);
    FetchFloatSettingService fetchFloatSettingService = (FetchFloatSettingService)SpringContextUtils.getBean(FetchFloatSettingService.class);
    FloatDimensionSettingService floatDimensionSettingService = (FloatDimensionSettingService)SpringContextUtils.getBean(FloatDimensionSettingService.class);
    IFormulaRunTimeController iFormulaRunTimeController = (IFormulaRunTimeController)SpringContextUtils.getBean(IFormulaRunTimeController.class);
    IDataQueryService iDataQueryService = (IDataQueryService)SpringContextUtils.getBean(IDataQueryService.class);
    INrExtractSchemeConfigService formulaSchemeConfigService = (INrExtractSchemeConfigService)SpringContextUtils.getBean(INrExtractSchemeConfigService.class);
    IEntityMetaService entityMetaService = (IEntityMetaService)SpringContextUtils.getBean(IEntityMetaService.class);
    IJtableEntityService entityService = (IJtableEntityService)SpringContextUtils.getBean(IJtableEntityService.class);
    private String formSchemeId;
    private ExecutorContext executorContext;
    private FormDefine formDefine;
    private DimensionValueSet dimensionValueSet;
    private DataRegionDefine dataRegion;
    private String formulaSchemeKeys;

    public AbstractFetchResultHandler(FetchRequestDTO fetchRequestDTO, FetchResultDTO fetchResponseDTO) {
        this.dataRegion = this.runtimeDataRegionService.queryDataRegion(fetchRequestDTO.getFetchContext().getRegionId());
        this.formSchemeId = fetchRequestDTO.getFetchContext().getFormSchemeId();
        this.formulaSchemeKeys = fetchRequestDTO.getFetchContext().getFormulaSchemeKeys();
        this.formDefine = this.runTimeViewController.queryFormById(fetchRequestDTO.getFetchContext().getFormId());
        this.executorContext = this.newExecutorContext(fetchRequestDTO.getFetchContext().getTaskId(), fetchRequestDTO.getFetchContext().getFormSchemeId());
        this.dimensionValueSet = this.newDimensionValueSet(fetchRequestDTO.getFetchContext().getDimensionSetStr());
    }

    public IDataQuery newDataQuery(String regionKey, List<FieldDefine> fetchFields) {
        QueryEnvironment queryEnvironment = this.newQueryEnvironment(regionKey);
        IDataQuery dataQuery = this.dataAccessProvider.newDataQuery(queryEnvironment);
        dataQuery.setMasterKeys(this.dimensionValueSet);
        if (!StringUtils.isEmpty((String)this.getDataRegion().getFilterCondition())) {
            dataQuery.setRowFilter(this.getDataRegion().getFilterCondition());
        }
        for (FieldDefine fieldDefine : fetchFields) {
            dataQuery.addColumn(fieldDefine);
        }
        return dataQuery;
    }

    private QueryEnvironment newQueryEnvironment(String regionId) {
        QueryEnvironment queryEnvironment = new QueryEnvironment();
        queryEnvironment.setFormSchemeKey(this.formSchemeId);
        queryEnvironment.setFormKey(this.getFormDefine().getKey());
        queryEnvironment.setFormCode(this.getFormDefine().getFormCode());
        queryEnvironment.setFormulaSchemeKey(this.formulaSchemeKeys);
        queryEnvironment.setRegionKey(regionId);
        return queryEnvironment;
    }

    private ExecutorContext newExecutorContext(String taskId, String formSchemeId) {
        ExecutorContext executorContext = new ExecutorContext(this.dataDefinitionRuntimeController);
        try {
            TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(taskId);
            executorContext.setJQReportModel(taskDefine.getFormulaSyntaxStyle() == FormulaSyntaxStyle.FORMULA_SYNTAX_STYLE_TRADITION);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        ReportFmlExecEnvironment environment = new ReportFmlExecEnvironment((IRunTimeViewController)this.runTimeViewController, this.dataDefinitionRuntimeController, this.entityViewRunTimeController, formSchemeId);
        executorContext.setEnv((IFmlExecEnvironment)environment);
        return executorContext;
    }

    private DimensionValueSet newDimensionValueSet(String dimensionSetStr) {
        Map dimensionSet = JSONUtil.parseMap((String)dimensionSetStr, String.class, DimensionValue.class);
        JtableContext jtableContext = new JtableContext();
        jtableContext.setFormSchemeKey(this.formSchemeId);
        jtableContext.setFormKey(this.getFormDefine().getKey());
        jtableContext.setDimensionSet(dimensionSet);
        return DimensionValueSetUtil.getDimensionValueSet((JtableContext)jtableContext);
    }

    public FormDefine getFormDefine() {
        return this.formDefine;
    }

    public String getFormSchemeId() {
        return this.formSchemeId;
    }

    public ExecutorContext getExecutorContext() {
        return this.executorContext;
    }

    public DimensionValueSet getDimensionValueSet() {
        return this.dimensionValueSet;
    }

    public DataRegionDefine getDataRegion() {
        return this.dataRegion;
    }
}

