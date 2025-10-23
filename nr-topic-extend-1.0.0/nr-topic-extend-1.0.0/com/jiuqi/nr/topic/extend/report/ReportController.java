/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.DataType
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.data.access.util.DimensionValueSetUtil
 *  com.jiuqi.nr.data.common.param.CommonParams
 *  com.jiuqi.nr.data.excel.obj.ExportOps
 *  com.jiuqi.nr.data.excel.param.SingleExpPar
 *  com.jiuqi.nr.data.excel.service.IDataExportService
 *  com.jiuqi.nr.data.logic.internal.util.DimensionCollectionUtil
 *  com.jiuqi.nr.definition.api.IRunTimeFormulaController
 *  com.jiuqi.nr.definition.api.IRunTimePrintController
 *  com.jiuqi.nr.definition.api.IRunTimeViewController
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.FormulaSchemeDefine
 *  com.jiuqi.nr.definition.facade.PrintTemplateSchemeDefine
 *  com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.facade.TaskOrgLinkDefine
 *  com.jiuqi.nr.definition.internal.dao.RunTimeFormDefineDao
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 *  com.jiuqi.nr.definition.internal.stream.param.FormStream
 *  com.jiuqi.nr.definition.internal.stream.param.FormulaSchemeStream
 *  com.jiuqi.nr.definition.internal.stream.param.PrintSchemeListStream
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
 *  com.jiuqi.nr.period.select.common.RunType
 *  com.jiuqi.nr.period.select.service.IRuntimePeriodModuleService
 *  com.jiuqi.nvwa.framework.parameter.ParameterCalculator
 *  com.jiuqi.nvwa.framework.parameter.datasource.ParameterDataSourceManager
 *  com.jiuqi.nvwa.framework.parameter.model.AbstractParameterDataSourceModel
 *  com.jiuqi.nvwa.framework.parameter.model.ParameterDependMember
 *  com.jiuqi.nvwa.framework.parameter.model.ParameterModel
 *  com.jiuqi.nvwa.framework.parameter.model.ParameterWidgetType
 *  com.jiuqi.nvwa.framework.parameter.model.config.AbstractParameterValueConfig
 *  com.jiuqi.nvwa.framework.parameter.model.config.ParameterValueConfig
 *  com.jiuqi.nvwa.framework.parameter.model.value.AbstractParameterValue
 *  com.jiuqi.nvwa.framework.parameter.model.value.FixedMemberParameterValue
 *  com.jiuqi.nvwa.framework.parameter.tools.ParameterConvertor
 *  io.swagger.annotations.ApiOperation
 *  javax.servlet.http.HttpServletResponse
 *  org.json.JSONObject
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.nr.topic.extend.report;

import com.jiuqi.bi.dataset.DataType;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.np.log.LogHelper;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.data.access.util.DimensionValueSetUtil;
import com.jiuqi.nr.data.common.param.CommonParams;
import com.jiuqi.nr.data.excel.obj.ExportOps;
import com.jiuqi.nr.data.excel.param.SingleExpPar;
import com.jiuqi.nr.data.excel.service.IDataExportService;
import com.jiuqi.nr.data.logic.internal.util.DimensionCollectionUtil;
import com.jiuqi.nr.definition.api.IRunTimeFormulaController;
import com.jiuqi.nr.definition.api.IRunTimePrintController;
import com.jiuqi.nr.definition.api.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.FormulaSchemeDefine;
import com.jiuqi.nr.definition.facade.PrintTemplateSchemeDefine;
import com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.facade.TaskOrgLinkDefine;
import com.jiuqi.nr.definition.internal.dao.RunTimeFormDefineDao;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import com.jiuqi.nr.definition.internal.stream.param.FormStream;
import com.jiuqi.nr.definition.internal.stream.param.FormulaSchemeStream;
import com.jiuqi.nr.definition.internal.stream.param.PrintSchemeListStream;
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
import com.jiuqi.nr.period.select.common.RunType;
import com.jiuqi.nr.period.select.service.IRuntimePeriodModuleService;
import com.jiuqi.nr.topic.extend.report.bean.ReportConfig;
import com.jiuqi.nr.topic.extend.report.bean.ReportParam;
import com.jiuqi.nr.topic.extend.report.bean.ReportResult;
import com.jiuqi.nvwa.framework.parameter.ParameterCalculator;
import com.jiuqi.nvwa.framework.parameter.datasource.ParameterDataSourceManager;
import com.jiuqi.nvwa.framework.parameter.model.AbstractParameterDataSourceModel;
import com.jiuqi.nvwa.framework.parameter.model.ParameterDependMember;
import com.jiuqi.nvwa.framework.parameter.model.ParameterModel;
import com.jiuqi.nvwa.framework.parameter.model.ParameterWidgetType;
import com.jiuqi.nvwa.framework.parameter.model.config.AbstractParameterValueConfig;
import com.jiuqi.nvwa.framework.parameter.model.config.ParameterValueConfig;
import com.jiuqi.nvwa.framework.parameter.model.value.AbstractParameterValue;
import com.jiuqi.nvwa.framework.parameter.model.value.FixedMemberParameterValue;
import com.jiuqi.nvwa.framework.parameter.tools.ParameterConvertor;
import io.swagger.annotations.ApiOperation;
import java.io.BufferedOutputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/api/topic/report"})
public class ReportController {
    @Autowired
    RunTimeFormDefineDao formDao;
    @Autowired
    IRunTimeViewController runTimeViewController_task;
    @Autowired
    PeriodEngineService periodEngineService;
    @Autowired
    IRuntimePeriodModuleService runtimePeriodModuleService;
    @Autowired
    IRunTimeFormulaController runTimeFormulaController;
    @Autowired
    IEntityMetaService entityMetaService;
    @Autowired
    IDataExportService dataExportService;
    @Autowired
    DimensionCollectionUtil dimensionCollectionUtil;
    @Autowired
    IRunTimePrintController runTimePrintController;
    @Autowired
    IEntityDataService entityDataService;
    @Autowired
    IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    @Autowired
    IEntityViewRunTimeController entityViewRunTimeController;
    @Autowired
    private com.jiuqi.nr.definition.controller.IRunTimeViewController runTimeViewController;
    @Autowired
    private IPeriodEntityAdapter periodEntityAdapter;

    @PostMapping(value={"/initParam"})
    @ApiOperation(value="\u9875\u9762\u521d\u59cb\u5316\u65f6\u8c03\u7528")
    public ReportResult getParamByInit(@RequestBody ReportParam param) throws Exception {
        StringBuilder sb = new StringBuilder();
        ReportConfig reportConfig = new ReportConfig();
        FormDefine formDefine = this.formDao.getDefineByKey(param.getFormKey());
        FormSchemeDefine formScheme = this.runTimeViewController_task.getFormScheme(formDefine.getFormScheme());
        reportConfig.setTaskKey(formScheme.getTaskKey());
        TaskDefine taskDefine = this.runTimeViewController_task.getTask(reportConfig.getTaskKey());
        ArrayList<ParameterModel> parameterModels = new ArrayList<ParameterModel>();
        if (StringUtils.hasText(param.getOrg()) && StringUtils.hasText(param.getPeriod())) {
            boolean correctOrg;
            String period = param.getPeriod();
            boolean correctPeriod = this.isCorrectPeriod(reportConfig.getTaskKey(), period);
            if (!correctPeriod) {
                sb.append("\u65f6\u671f").append(period).append("\u4e0d\u5408\u6cd5\n");
                String tempPeriod = StringUtils.hasText(param.getLastPeriod()) ? param.getLastPeriod() : this.runtimePeriodModuleService.queryOffsetPeriod(reportConfig.getTaskKey(), RunType.RUNTIME);
                this.buildConfigPeriod(reportConfig, taskDefine, tempPeriod);
                parameterModels.add(this.buildPeriodModelJson(reportConfig));
            } else {
                this.buildConfigPeriod(reportConfig, taskDefine, period);
                if (param.isPeriodJson()) {
                    parameterModels.add(this.buildPeriodModelJson(reportConfig));
                }
            }
            this.buildConfigOther(reportConfig, formScheme.getKey(), formDefine, sb);
            ArrayList<String> orgEntityList = new ArrayList<String>();
            boolean correctOrgEntity = this.isCorrectOrgEntity(reportConfig, param.getOrgEntity(), param.getOrg(), orgEntityList);
            if (!correctOrgEntity) {
                sb.append(this.buildOrgErrorMsg(orgEntityList));
                reportConfig.setOrgEntity(taskDefine.getDw());
                reportConfig.setOrgEntityTitle(this.entityMetaService.queryEntity(taskDefine.getDw()).getTitle());
            }
            if (!(correctOrg = this.buildConfigOrgValue(reportConfig, taskDefine, param.getOrg(), param.getLastOrg()))) {
                parameterModels.add(this.buildOrgModelJson(reportConfig));
            } else if (param.isOrgJson()) {
                parameterModels.add(this.buildOrgModelJson(reportConfig));
            }
            this.buildConfigDimensionSet(reportConfig);
        } else if (StringUtils.hasText(param.getOrg())) {
            boolean correctOrg;
            this.buildConfigPeriod(reportConfig, taskDefine, this.runtimePeriodModuleService.queryOffsetPeriod(reportConfig.getTaskKey(), RunType.RUNTIME));
            parameterModels.add(this.buildPeriodModelJson(reportConfig));
            this.buildConfigOther(reportConfig, formScheme.getKey(), formDefine, sb);
            ArrayList<String> orgEntityList = new ArrayList<String>();
            boolean correctOrgEntity = this.isCorrectOrgEntity(reportConfig, param.getOrgEntity(), param.getOrg(), orgEntityList);
            if (!correctOrgEntity) {
                sb.append(this.buildOrgErrorMsg(orgEntityList));
                reportConfig.setOrgEntity(taskDefine.getDw());
                reportConfig.setOrgEntityTitle(this.entityMetaService.queryEntity(taskDefine.getDw()).getTitle());
            }
            if (!(correctOrg = this.buildConfigOrgValue(reportConfig, taskDefine, param.getOrg(), param.getLastOrg()))) {
                parameterModels.add(this.buildOrgModelJson(reportConfig));
            } else if (param.isOrgJson()) {
                parameterModels.add(this.buildOrgModelJson(reportConfig));
            }
            this.buildConfigDimensionSet(reportConfig);
        } else if (StringUtils.hasText(param.getPeriod())) {
            String period = param.getPeriod();
            boolean correctPeriod = this.isCorrectPeriod(reportConfig.getTaskKey(), period);
            if (!correctPeriod) {
                sb.append("\u65f6\u671f").append(period).append("\u4e0d\u5408\u6cd5\n");
                this.buildConfigPeriod(reportConfig, taskDefine, this.runtimePeriodModuleService.queryOffsetPeriod(reportConfig.getTaskKey(), RunType.RUNTIME));
                parameterModels.add(this.buildPeriodModelJson(reportConfig));
            } else {
                this.buildConfigPeriod(reportConfig, taskDefine, period);
                if (param.isPeriodJson()) {
                    parameterModels.add(this.buildPeriodModelJson(reportConfig));
                }
            }
            this.buildConfigOther(reportConfig, formScheme.getKey(), formDefine, sb);
            reportConfig.setOrgEntity(taskDefine.getDw());
            reportConfig.setOrgEntityTitle(this.entityMetaService.queryEntity(taskDefine.getDw()).getTitle());
            this.buildConfigOrgValue(reportConfig, taskDefine, null, null);
            parameterModels.add(this.buildOrgModelJson(reportConfig));
            this.buildConfigDimensionSet(reportConfig);
        } else {
            this.buildConfigPeriod(reportConfig, taskDefine, this.runtimePeriodModuleService.queryOffsetPeriod(reportConfig.getTaskKey(), RunType.RUNTIME));
            parameterModels.add(this.buildPeriodModelJson(reportConfig));
            this.buildConfigOther(reportConfig, formScheme.getKey(), formDefine, sb);
            boolean correctOrgEntity = false;
            List taskOrgLinkDefines = this.runTimeViewController_task.listTaskOrgLinkByTask(reportConfig.getTaskKey());
            for (TaskOrgLinkDefine define : taskOrgLinkDefines) {
                if (!StringUtils.hasText(param.getOrgEntity()) || !define.getEntity().contains(param.getOrgEntity())) continue;
                correctOrgEntity = true;
                reportConfig.setOrgEntity(define.getEntity());
                reportConfig.setOrgEntityTitle(this.entityMetaService.queryEntity(define.getEntity()).getTitle());
                sb.append("\u83b7\u53d6\u5230\u7684\u7ec4\u7ec7\u673a\u6784\u4e3a\u7a7a\uff0c\u8bf7\u68c0\u67e5\u6d88\u606f\u522b\u540d\u662f\u5426\u4e3a\uff1aP_MD_ORG\n");
                break;
            }
            if (!correctOrgEntity) {
                reportConfig.setOrgEntity(taskDefine.getDw());
                reportConfig.setOrgEntityTitle(this.entityMetaService.queryEntity(taskDefine.getDw()).getTitle());
            }
            this.buildConfigOrgValue(reportConfig, taskDefine, null, null);
            parameterModels.add(this.buildOrgModelJson(reportConfig));
            this.buildConfigDimensionSet(reportConfig);
        }
        if (!CollectionUtils.isEmpty(parameterModels)) {
            ParameterCalculator calculator = new ParameterCalculator(NpContextHolder.getContext().getUserName(), parameterModels);
            for (ParameterModel parameterModel : parameterModels) {
                reportConfig.addPmJsonList(ParameterConvertor.toJson((ParameterCalculator)calculator, (ParameterModel)parameterModel, (boolean)false).toString());
            }
        }
        return new ReportResult(true, sb.toString(), reportConfig);
    }

    private String buildOrgErrorMsg(List<String> orgEntityList) {
        StringBuilder sb = new StringBuilder();
        StringBuilder orgEntityTitle = new StringBuilder();
        for (String orgEntity : orgEntityList) {
            IEntityDefine entity = this.entityMetaService.queryEntity(orgEntity);
            orgEntityTitle.append(entity.getCode()).append("|").append(entity.getTitle()).append("\u3001");
        }
        sb.append("\u5173\u8054\u6a21\u677f\u7684\u7ec4\u7ec7\u673a\u6784\u4e0e\u5f53\u524d\u8868\u7684\u7ec4\u7ec7\u673a\u6784\u4e0d\u4e00\u81f4\uff0c\u5f53\u524d\u8868\u7684\u7ec4\u7ec7\u673a\u6784\u5b9a\u4e49\uff1a").append(orgEntityTitle.substring(0, orgEntityTitle.length() - 1)).append("\n");
        return sb.toString();
    }

    @PostMapping(value={"/export"})
    @ApiOperation(value="\u5bfc\u51faexcel")
    public void export(@RequestBody ReportConfig reportConfig, HttpServletResponse response) throws Exception {
        response.setCharacterEncoding("UTF-8");
        response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("\u57fa\u7840\u8868.xlsx", "UTF-8"));
        response.setContentType("application/octet-stream");
        BufferedOutputStream outputStream = new BufferedOutputStream((OutputStream)response.getOutputStream());
        SingleExpPar dataExpPar = new SingleExpPar();
        dataExpPar.setFormSchemeKey(reportConfig.getFormSchemeKey());
        DimensionValueSet dimensionValueSet = DimensionValueSetUtil.getDimensionValueSet(reportConfig.getDimensionSet());
        dataExpPar.setDimensionCombination(DimensionValueSetUtil.buildDimensionCombination((DimensionValueSet)dimensionValueSet, (String)reportConfig.getFormSchemeKey()));
        ArrayList<String> forms = new ArrayList<String>();
        forms.add(reportConfig.getFormKey());
        dataExpPar.setForms(forms);
        ExportOps ops = new ExportOps();
        PrintSchemeListStream printScheme = this.runTimePrintController.listPrintTemplateSchemeByFormScheme(reportConfig.getFormSchemeKey());
        if (printScheme != null && printScheme.getList() != null && !printScheme.getList().isEmpty()) {
            ops.setPrintSchemeKey(((PrintTemplateSchemeDefine)printScheme.getList().get(0)).getKey());
        }
        ops.setFormulaSchemeKey(reportConfig.getFormulaSchemeKey());
        ops.setExp0Form(true);
        ops.setExpCellBColor(true);
        ops.setExpEnumDropDown(Boolean.valueOf(false));
        dataExpPar.setOps(ops);
        CommonParams commonParams = new CommonParams();
        this.dataExportService.expSingleFile(dataExpPar, (OutputStream)outputStream, commonParams);
        ((OutputStream)outputStream).flush();
        TaskDefine taskDefine = this.runTimeViewController_task.getTask(reportConfig.getTaskKey());
        FormDefine form = this.runTimeViewController_task.getForm(reportConfig.getFormKey(), reportConfig.getFormSchemeKey());
        DimensionValueSet dimensionValueSet1 = new DimensionValueSet();
        dimensionValueSet1.setValue("DATATIME", (Object)reportConfig.getPeriod());
        String dimensionName = this.entityMetaService.queryEntity(reportConfig.getOrgEntity()).getDimensionName();
        dimensionValueSet1.setValue(dimensionName, (Object)reportConfig.getOrg());
        EntityViewDefine entityView = this.runTimeViewController.getViewByTaskDefineKey(taskDefine.getKey());
        entityView = this.entityViewRunTimeController.buildEntityView(reportConfig.getOrgEntity(), entityView.getRowFilterExpression(), entityView.getFilterRowByAuthority());
        IEntityQuery query = this.entityDataService.newEntityQuery();
        query.sorted(true);
        query.setEntityView(entityView);
        query.setMasterKeys(dimensionValueSet1);
        query.setAuthorityOperations(AuthorityType.Read);
        query.sortedByQuery(false);
        ReportFmlExecEnvironment environment = new ReportFmlExecEnvironment(this.runTimeViewController, this.dataDefinitionRuntimeController, this.entityViewRunTimeController, reportConfig.getFormSchemeKey());
        ExecutorContext context = new ExecutorContext(this.dataDefinitionRuntimeController);
        context.setEnv((IFmlExecEnvironment)environment);
        context.setVarDimensionValueSet(dimensionValueSet1);
        context.setPeriodView(taskDefine.getDateTime());
        IEntityTable entityTable = query.executeReader((IContext)context);
        IEntityRow row = CollectionUtils.isEmpty(entityTable.getAllRows()) ? null : (IEntityRow)entityTable.getAllRows().get(0);
        String sb = "\u57fa\u7840\u8868\u5bfc\u51fa\u6210\u529f\uff1a\n\u4efb\u52a1\uff1a" + taskDefine.getTitle() + "\n\u65f6\u671f\uff1a" + reportConfig.getPeriodTitle() + "\n" + (row != null ? "\u5355\u4f4d\uff1a" + row.getEntityKeyData() + "|" + row.getTitle() + "\n" : "") + "\u62a5\u8868\uff1a" + form.getFormCode() + "|" + form.getTitle() + "\n";
        LogHelper.info((String)"\u4e3b\u9898\u5bfc\u822a\u6811", (String)"\u6267\u884c\u5bfc\u51fa\u57fa\u7840\u8868Excel", (String)sb);
    }

    private boolean isCorrectPeriod(String taskKey, String period) {
        boolean correctPeriod = false;
        List schemePeriodLinkDefines = this.runTimeViewController_task.listSchemePeriodLinkByTask(taskKey);
        for (SchemePeriodLinkDefine periodItem : schemePeriodLinkDefines) {
            if (!period.equals(periodItem.getPeriodKey())) continue;
            correctPeriod = true;
            break;
        }
        return correctPeriod;
    }

    private boolean isCorrectOrgEntity(ReportConfig reportConfig, String orgEntity, String org, List<String> orgEntityList) {
        boolean correctOrgEntity = false;
        List taskOrgLinkDefines = this.runTimeViewController_task.listTaskOrgLinkByTask(reportConfig.getTaskKey());
        for (TaskOrgLinkDefine define : taskOrgLinkDefines) {
            if (StringUtils.hasText(orgEntity) && define.getEntity().contains(orgEntity)) {
                correctOrgEntity = true;
                reportConfig.setOrgEntity(define.getEntity());
                reportConfig.setOrgEntityTitle(this.entityMetaService.queryEntity(define.getEntity()).getTitle());
                break;
            }
            orgEntityList.add(define.getEntity());
        }
        return correctOrgEntity;
    }

    private boolean buildConfigOrgValue(ReportConfig reportConfig, TaskDefine taskDefine, String org, String defaultOrg) throws Exception {
        DimensionValueSet dimensionValueSet = new DimensionValueSet();
        dimensionValueSet.setValue("DATATIME", (Object)reportConfig.getPeriod());
        EntityViewDefine entityView = this.runTimeViewController.getViewByTaskDefineKey(taskDefine.getKey());
        entityView = this.entityViewRunTimeController.buildEntityView(reportConfig.getOrgEntity(), entityView.getRowFilterExpression(), entityView.getFilterRowByAuthority());
        IEntityQuery query = this.entityDataService.newEntityQuery();
        query.sorted(true);
        query.setEntityView(entityView);
        query.setMasterKeys(dimensionValueSet);
        query.setAuthorityOperations(AuthorityType.Read);
        query.sortedByQuery(false);
        ReportFmlExecEnvironment environment = new ReportFmlExecEnvironment(this.runTimeViewController, this.dataDefinitionRuntimeController, this.entityViewRunTimeController, reportConfig.getFormSchemeKey());
        ExecutorContext context = new ExecutorContext(this.dataDefinitionRuntimeController);
        context.setEnv((IFmlExecEnvironment)environment);
        context.setVarDimensionValueSet(dimensionValueSet);
        context.setPeriodView(taskDefine.getDateTime());
        IEntityTable entityTable = query.executeReader((IContext)context);
        if (StringUtils.hasText(org)) {
            IEntityRow row = entityTable.findByEntityKey(org);
            if (row == null) {
                if (StringUtils.hasText(defaultOrg)) {
                    reportConfig.setOrg(defaultOrg);
                } else {
                    List rows = entityTable.getAllRows();
                    reportConfig.setOrg(!rows.isEmpty() ? ((IEntityRow)rows.get(0)).getEntityKeyData() : "");
                }
                return false;
            }
            reportConfig.setOrg(org);
            return true;
        }
        List rows = entityTable.getAllRows();
        reportConfig.setOrg(!rows.isEmpty() ? ((IEntityRow)rows.get(0)).getEntityKeyData() : "");
        return false;
    }

    private void buildConfigPeriod(ReportConfig reportConfig, TaskDefine taskDefine, String period) {
        IPeriodProvider periodProvider = this.periodEngineService.getPeriodAdapter().getPeriodProvider(taskDefine.getDateTime());
        reportConfig.setPeriodEntity(periodProvider.getPeriodEntity().getID());
        reportConfig.setPeriodEntityTitle(periodProvider.getPeriodEntity().getTitle());
        reportConfig.setFromPeriod(StringUtils.hasText(taskDefine.getFromPeriod()) ? taskDefine.getFromPeriod() : periodProvider.getPeriodCodeRegion()[0]);
        reportConfig.setToPeriod(StringUtils.hasText(taskDefine.getToPeriod()) ? taskDefine.getToPeriod() : periodProvider.getPeriodCodeRegion()[periodProvider.getPeriodCodeRegion().length - 1]);
        reportConfig.setPeriod(period);
        reportConfig.setPeriodTitle(periodProvider.getPeriodTitle(period));
    }

    private void buildConfigOther(ReportConfig reportConfig, String formSchemeKey, FormDefine formDefine, StringBuilder sb) {
        SchemePeriodLinkDefine linkDefine = this.runTimeViewController_task.getSchemePeriodLinkByPeriodAndTask(reportConfig.getPeriod(), reportConfig.getTaskKey());
        if (linkDefine != null && !formSchemeKey.equals(linkDefine.getSchemeKey())) {
            FormStream formStream = this.runTimeViewController_task.listFormByCodeAndFormSchemeStream(formDefine.getFormCode(), linkDefine.getSchemeKey());
            if (formStream != null && formStream.get() != null) {
                reportConfig.setFormSchemeKey(linkDefine.getSchemeKey());
                reportConfig.setFormKey(((FormDefine)formStream.get()).getKey());
            } else {
                sb.append("\u5f53\u524d\u65f6\u671f\uff1a").append(reportConfig.getPeriodTitle()).append(" \u4e0d\u5b58\u5728\u57fa\u7840\u8868\uff1a").append(formDefine.getTitle()).append("[").append(formDefine.getFormCode()).append("]\n");
                reportConfig.setFormSchemeKey(formDefine.getFormScheme());
                reportConfig.setFormKey(formDefine.getKey());
                reportConfig.setInitJTable(false);
            }
        } else {
            reportConfig.setFormSchemeKey(formDefine.getFormScheme());
            reportConfig.setFormKey(formDefine.getKey());
        }
        FormulaSchemeStream formulaScheme = this.runTimeFormulaController.getDefaultFormulaSchemeByFormScheme(reportConfig.getFormSchemeKey());
        reportConfig.setFormulaSchemeKey(formulaScheme.get() != null ? ((FormulaSchemeDefine)formulaScheme.get()).getKey() : "");
    }

    private void buildConfigDimensionSet(ReportConfig reportConfig) {
        HashMap<String, DimensionValue> dimensionSet = new HashMap<String, DimensionValue>();
        DimensionValue orgDim = new DimensionValue();
        orgDim.setName("MD_ORG");
        orgDim.setValue(reportConfig.getOrg());
        dimensionSet.put("MD_ORG", orgDim);
        DimensionValue periodDim = new DimensionValue();
        periodDim.setName("DATATIME");
        periodDim.setValue(reportConfig.getPeriod());
        dimensionSet.put("DATATIME", periodDim);
        reportConfig.setDimensionSet(dimensionSet);
    }

    private ParameterModel buildOrgModelJson(ReportConfig reportConfig) throws Exception {
        ParameterModel orgPM = new ParameterModel();
        orgPM.setName(reportConfig.getOrgEntity().split("@")[0]);
        orgPM.setTitle(reportConfig.getOrgEntityTitle());
        AbstractParameterDataSourceModel oDataSourceModel = ParameterDataSourceManager.getInstance().getFactory("com.jiuqi.publicparam.datasource.dimension").newInstance();
        JSONObject oModelJson = new JSONObject();
        oDataSourceModel.toJson(oModelJson);
        oModelJson.put("entityViewId", (Object)reportConfig.getOrgEntity());
        oModelJson.put("formSchemeKey", (Object)reportConfig.getFormSchemeKey());
        oDataSourceModel.fromJson(oModelJson);
        oDataSourceModel.setDataType(DataType.STRING.value());
        orgPM.setDatasource(oDataSourceModel);
        orgPM.setWidgetType(ParameterWidgetType.DROPDOWN.value());
        orgPM.setShowCode(false);
        orgPM.setSwitchShowCode(true);
        orgPM.setValueConfig((AbstractParameterValueConfig)new ParameterValueConfig());
        if (StringUtils.hasText(reportConfig.getOrg())) {
            orgPM.getValueConfig().setDefaultValueMode("appoint");
            orgPM.getValueConfig().setDefaultValue((AbstractParameterValue)new FixedMemberParameterValue(Collections.singletonList(reportConfig.getOrg())));
        } else {
            orgPM.getValueConfig().setDefaultValueMode("first");
        }
        orgPM.setNullable(false);
        IEntityDefine orgEntity = this.entityMetaService.queryEntity(reportConfig.getOrgEntity());
        if (orgEntity != null && orgEntity.getVersion() == 1) {
            orgPM.getValueConfig().getDepends().add(new ParameterDependMember("NR_PERIOD_" + reportConfig.getPeriodEntity(), null));
        }
        reportConfig.setOrgJson(true);
        return orgPM;
    }

    private ParameterModel buildPeriodModelJson(ReportConfig reportConfig) throws Exception {
        ParameterModel periodPM = new ParameterModel();
        periodPM.setName("NR_PERIOD_" + reportConfig.getPeriodEntity());
        periodPM.setTitle(reportConfig.getPeriodEntityTitle());
        AbstractParameterDataSourceModel pDataSourceModel = ParameterDataSourceManager.getInstance().getFactory("com.jiuqi.publicparam.datasource.date").newInstance();
        JSONObject pModelJson = new JSONObject();
        pDataSourceModel.toJson(pModelJson);
        pModelJson.put("entityViewId", (Object)reportConfig.getPeriodEntity());
        IPeriodEntity periodEntity = this.periodEntityAdapter.getPeriodEntity(reportConfig.getPeriodEntity());
        pModelJson.put("periodType", periodEntity.getType().type());
        pModelJson.put("periodStartEnd", (Object)(reportConfig.getFromPeriod() + "-" + reportConfig.getToPeriod()));
        periodPM.setOnlyLeafSelectable(true);
        pDataSourceModel.fromJson(pModelJson);
        pDataSourceModel.setDataType(DataType.STRING.value());
        periodPM.setDatasource(pDataSourceModel);
        periodPM.setWidgetType(ParameterWidgetType.DATEPICKER.value());
        periodPM.setValueConfig((AbstractParameterValueConfig)new ParameterValueConfig());
        periodPM.getValueConfig().setDefaultValueMode("appoint");
        periodPM.getValueConfig().setDefaultValue((AbstractParameterValue)new FixedMemberParameterValue(Collections.singletonList(reportConfig.getPeriod())));
        periodPM.setNullable(false);
        reportConfig.setPeriodJson(true);
        return periodPM;
    }
}

