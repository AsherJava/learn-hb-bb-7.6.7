/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.nr.bpm.common.UploadAllFormSumInfo
 *  com.jiuqi.nr.bpm.common.UploadRecordDetail
 *  com.jiuqi.nr.bpm.common.UploadRecordNew
 *  com.jiuqi.nr.bpm.common.UploadStatusDetail
 *  com.jiuqi.nr.bpm.common.UploadSumNew
 *  com.jiuqi.nr.bpm.dataflow.service.IQueryUploadStateService
 *  com.jiuqi.nr.bpm.de.dataflow.bean.ActionState
 *  com.jiuqi.nr.bpm.de.dataflow.bean.ActionStateBean
 *  com.jiuqi.nr.bpm.de.dataflow.bean.DataEntryParam
 *  com.jiuqi.nr.bpm.de.dataflow.constont.UploadStateEnum
 *  com.jiuqi.nr.bpm.de.dataflow.service.IDataentryFlowService
 *  com.jiuqi.nr.bpm.de.dataflow.service.IWorkflow
 *  com.jiuqi.nr.bpm.de.dataflow.tree.TreeWorkflow
 *  com.jiuqi.nr.bpm.setting.constant.SettingError
 *  com.jiuqi.nr.bpm.setting.pojo.ShowNodeParam
 *  com.jiuqi.nr.bpm.setting.pojo.ShowResult
 *  com.jiuqi.nr.bpm.setting.service.WorkflowSettingService
 *  com.jiuqi.nr.bpm.upload.UploadState
 *  com.jiuqi.nr.common.itree.INode
 *  com.jiuqi.nr.common.itree.ITree
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.dataentity.service.DataEntityFullService
 *  com.jiuqi.nr.definition.api.IRunTimeViewController
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DesignTaskGroupDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormGroupDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.facade.TaskFlowsDefine
 *  com.jiuqi.nr.definition.facade.TaskGroupDefine
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 *  com.jiuqi.nr.definition.internal.impl.WorkFlowType
 *  com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeFormGroupService
 *  com.jiuqi.nr.definition.internal.service.DesignTaskGroupDefineService
 *  com.jiuqi.nr.entity.engine.executors.ExecutorContext
 *  com.jiuqi.nr.entity.engine.intf.IEntityItem
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.engine.var.LevelRangeQuery
 *  com.jiuqi.nr.entity.engine.var.RangeQuery
 *  com.jiuqi.nr.entity.engine.var.TreeRangeQuery
 *  com.jiuqi.nr.entity.service.IEntityDataService
 *  com.jiuqi.nr.jtable.params.base.EntityViewData
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  com.jiuqi.nr.jtable.params.input.EntityQueryByKeyInfo
 *  com.jiuqi.nr.jtable.params.input.EntityQueryByViewInfo
 *  com.jiuqi.nr.jtable.params.output.EntityByKeyReturnInfo
 *  com.jiuqi.nr.jtable.params.output.EntityData
 *  com.jiuqi.nr.jtable.params.output.EntityReturnInfo
 *  com.jiuqi.nr.jtable.service.IJtableEntityService
 *  com.jiuqi.nr.jtable.service.IJtableParamService
 *  com.jiuqi.nr.jtable.util.DimensionValueSetUtil
 *  com.jiuqi.nr.time.setting.de.DeSetTimeProvide
 *  javax.annotation.Resource
 *  javax.servlet.ServletOutputStream
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 */
package com.jiuqi.nr.dataentry.web;

import com.google.gson.Gson;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.nr.bpm.common.UploadAllFormSumInfo;
import com.jiuqi.nr.bpm.common.UploadRecordDetail;
import com.jiuqi.nr.bpm.common.UploadRecordNew;
import com.jiuqi.nr.bpm.common.UploadStatusDetail;
import com.jiuqi.nr.bpm.common.UploadSumNew;
import com.jiuqi.nr.bpm.dataflow.service.IQueryUploadStateService;
import com.jiuqi.nr.bpm.de.dataflow.bean.ActionState;
import com.jiuqi.nr.bpm.de.dataflow.bean.ActionStateBean;
import com.jiuqi.nr.bpm.de.dataflow.bean.DataEntryParam;
import com.jiuqi.nr.bpm.de.dataflow.constont.UploadStateEnum;
import com.jiuqi.nr.bpm.de.dataflow.service.IDataentryFlowService;
import com.jiuqi.nr.bpm.de.dataflow.service.IWorkflow;
import com.jiuqi.nr.bpm.de.dataflow.tree.TreeWorkflow;
import com.jiuqi.nr.bpm.setting.constant.SettingError;
import com.jiuqi.nr.bpm.setting.pojo.ShowNodeParam;
import com.jiuqi.nr.bpm.setting.pojo.ShowResult;
import com.jiuqi.nr.bpm.setting.service.WorkflowSettingService;
import com.jiuqi.nr.bpm.upload.UploadState;
import com.jiuqi.nr.common.itree.INode;
import com.jiuqi.nr.common.itree.ITree;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.dataentity.service.DataEntityFullService;
import com.jiuqi.nr.dataentry.asynctask.ITaskTreeNode;
import com.jiuqi.nr.dataentry.asynctask.TaskTreeObj;
import com.jiuqi.nr.dataentry.bean.DataEntryContext;
import com.jiuqi.nr.dataentry.bean.FormsQueryResult;
import com.jiuqi.nr.dataentry.bean.QueryUploadStateInfo;
import com.jiuqi.nr.dataentry.internal.overview.OverviewUtil;
import com.jiuqi.nr.dataentry.internal.service.FormGroupProvider;
import com.jiuqi.nr.dataentry.paramInfo.ActionInfo;
import com.jiuqi.nr.dataentry.paramInfo.BatchQueryUpload;
import com.jiuqi.nr.dataentry.paramInfo.BatchQueryWorkFlowType;
import com.jiuqi.nr.dataentry.paramInfo.ChangeEntitys;
import com.jiuqi.nr.dataentry.paramInfo.ExportExcelState;
import com.jiuqi.nr.dataentry.paramInfo.FormGroupData;
import com.jiuqi.nr.dataentry.paramInfo.TaskGroupParam;
import com.jiuqi.nr.dataentry.paramInfo.UploadActionInfo;
import com.jiuqi.nr.dataentry.paramInfo.UploadSumInfo;
import com.jiuqi.nr.dataentry.service.BatchExportUploadService;
import com.jiuqi.nr.dataentry.service.ExportExcelNameService;
import com.jiuqi.nr.dataentry.service.IFuncExecuteService;
import com.jiuqi.nr.dataentry.service.IOverViewBaseService;
import com.jiuqi.nr.dataentry.tree.FormTree;
import com.jiuqi.nr.definition.api.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DesignTaskGroupDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormGroupDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.facade.TaskFlowsDefine;
import com.jiuqi.nr.definition.facade.TaskGroupDefine;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import com.jiuqi.nr.definition.internal.impl.WorkFlowType;
import com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeFormGroupService;
import com.jiuqi.nr.definition.internal.service.DesignTaskGroupDefineService;
import com.jiuqi.nr.entity.engine.executors.ExecutorContext;
import com.jiuqi.nr.entity.engine.intf.IEntityItem;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.engine.var.LevelRangeQuery;
import com.jiuqi.nr.entity.engine.var.RangeQuery;
import com.jiuqi.nr.entity.engine.var.TreeRangeQuery;
import com.jiuqi.nr.entity.service.IEntityDataService;
import com.jiuqi.nr.jtable.params.base.EntityViewData;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.params.input.EntityQueryByKeyInfo;
import com.jiuqi.nr.jtable.params.input.EntityQueryByViewInfo;
import com.jiuqi.nr.jtable.params.output.EntityByKeyReturnInfo;
import com.jiuqi.nr.jtable.params.output.EntityData;
import com.jiuqi.nr.jtable.params.output.EntityReturnInfo;
import com.jiuqi.nr.jtable.service.IJtableEntityService;
import com.jiuqi.nr.jtable.service.IJtableParamService;
import com.jiuqi.nr.jtable.util.DimensionValueSetUtil;
import com.jiuqi.nr.time.setting.de.DeSetTimeProvide;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.DefaultIndexedColorMap;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component(value="BatchUploadStateController")
public class BatchUploadStateController {
    private static final Logger logger = LoggerFactory.getLogger(BatchUploadStateController.class);
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    protected com.jiuqi.nr.definition.controller.IRunTimeViewController runtimeView;
    @Resource
    protected IQueryUploadStateService queryUploadStateService;
    @Autowired
    protected IJtableParamService jtableParamService;
    @Autowired
    protected IJtableEntityService jtableEntityService;
    @Autowired
    protected IDataentryFlowService dataentryFlowService;
    @Autowired
    protected IRuntimeFormGroupService runtimeFormGroupService;
    @Autowired
    protected TreeWorkflow treeWorkFlow;
    @Autowired
    protected DesignTaskGroupDefineService designTaskGroupDefineService;
    @Autowired
    protected BatchExportUploadService batchExportUploadService;
    @Autowired
    protected FormGroupProvider formGroupProvider;
    @Autowired
    protected IWorkflow workflow;
    @Autowired
    protected IFuncExecuteService funcExecuteService;
    @Autowired
    protected IOverViewBaseService overViewBaseService;
    @Autowired
    protected IEntityViewRunTimeController iEntityViewRunTimeController;
    @Autowired
    protected IEntityDataService entityDataService;
    @Autowired
    protected IDataDefinitionRuntimeController runtimeController;
    @Autowired
    protected WorkflowSettingService workflowSettingService;
    @Autowired
    protected DataEntityFullService dataEntityFullService;
    @Autowired
    protected DeSetTimeProvide deSetTimeProvide;
    @Autowired
    private ExportExcelNameService exportExcelNameService;
    @Autowired
    private OverviewUtil overviewUtil;

    public FormsQueryResult getFormsReadable(DataEntryContext context) {
        FormSchemeDefine formScheme = this.runtimeView.getFormScheme(context.getFormSchemeKey());
        Map<String, DimensionValue> dimensionSet = context.getDimensionSet();
        WorkFlowType wordFlowType = formScheme.getFlowsSetting().getWordFlowType();
        this.setDefaultValueNEW(dimensionSet, wordFlowType, context.getFormSchemeKey());
        JtableContext jtableContext = new JtableContext();
        jtableContext.setDimensionSet(dimensionSet);
        jtableContext.setFormGroupKey(context.getFormGroupKey());
        jtableContext.setFormKey(context.getFormKey());
        jtableContext.setFormSchemeKey(context.getFormSchemeKey());
        jtableContext.setFormulaSchemeKey(context.getFormulaSchemeKey());
        jtableContext.setTaskKey(context.getTaskKey());
        List<FormGroupData> formGroupList = this.formGroupProvider.getFormGroupListByDWAndDatatime(jtableContext, false);
        FormsQueryResult result = new FormsQueryResult();
        FormTree formTree = this.funcExecuteService.getFormTree(formGroupList);
        result.setTree(formTree);
        return result;
    }

    /*
     * WARNING - void declaration
     */
    public List<UploadSumInfo> batchUploadState(BatchQueryUpload batchQueryUpload) {
        List<IEntityRow> childEntity;
        FormSchemeDefine formScheme = null;
        try {
            formScheme = this.runtimeView.getFormScheme(batchQueryUpload.getFormSchemeKey());
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
        boolean defaultWorkflow = this.workflow.isDefaultWorkflow(formScheme.getKey());
        ArrayList<UploadSumInfo> uploadSums = new ArrayList<UploadSumInfo>();
        EntityViewData dwEntity = this.jtableParamService.getDwEntity(formScheme.getKey());
        EntityViewData dataTimeEntity = this.jtableParamService.getDataTimeEntity(formScheme.getKey());
        DimensionValueSet dimensionValueSet = DimensionValueSetUtil.getDimensionValueSet(batchQueryUpload.getDimensionSet());
        String dwId = dimensionValueSet.getValue(dwEntity.getDimensionName()).toString();
        String period = dimensionValueSet.getValue(dataTimeEntity.getDimensionName()).toString();
        WorkFlowType queryStartType = this.dataentryFlowService.queryStartType(formScheme.getKey());
        JtableContext context = new JtableContext();
        context.setDimensionSet(batchQueryUpload.getDimensionSet());
        context.setFormKey(batchQueryUpload.getFormKey());
        context.setFormSchemeKey(batchQueryUpload.getFormSchemeKey());
        context.setTaskKey(batchQueryUpload.getTaskKey());
        IEntityTable iEntityTable = null;
        iEntityTable = !batchQueryUpload.isOnlyDirectChild() ? this.getEntityDataList(batchQueryUpload.getFormSchemeKey(), batchQueryUpload.getDimensionSet(), dwId) : this.getEntityDataList1(batchQueryUpload.getFormSchemeKey(), batchQueryUpload.getDimensionSet(), dwId);
        IEntityRow selfRow = iEntityTable.findByEntityKey(dwId);
        ArrayList<UploadActionInfo> uploadActions = new ArrayList<UploadActionInfo>();
        Calendar abortTime = this.deSetTimeProvide.queryAllowDelayDeadlineTime(formScheme, period);
        if (selfRow == null) {
            return uploadSums;
        }
        boolean confirmBrforeUpload = this.overviewUtil.confirmBrforeUpload(formScheme.getKey());
        this.getUploadActions(selfRow, dimensionValueSet, batchQueryUpload, formScheme, uploadActions, dwEntity, iEntityTable);
        this.getUploadSum(selfRow, dimensionValueSet, batchQueryUpload.getFormKey(), formScheme, uploadSums, batchQueryUpload.getSummaryScope(), context, uploadActions, defaultWorkflow, queryStartType, iEntityTable, batchQueryUpload.isLeafEntity(), batchQueryUpload.isFilterDiffUnit(), batchQueryUpload.isOnlyDirectChild(), abortTime, true, confirmBrforeUpload);
        List<Object> entitypage = new ArrayList();
        String formKey = batchQueryUpload.getFormKey();
        String filter = batchQueryUpload.getFilter();
        List delayUploadRecord = Collections.emptyList();
        if (batchQueryUpload.getSummaryScope() > 1) {
            void var22_26;
            childEntity = this.getChildEntitys(iEntityTable, batchQueryUpload.getSummaryScope(), dwId);
            ArrayList arrayList = new ArrayList();
            if (!StringUtils.isEmpty((String)formKey)) {
                List<IEntityRow> list = this.overViewBaseService.filterAuthByEntity(context, childEntity, formKey, queryStartType);
            } else {
                List<IEntityRow> list = childEntity;
            }
            if (StringUtils.isNotEmpty((String)batchQueryUpload.getFilter())) {
                List<IEntityRow> filterEntity = new ArrayList<IEntityRow>();
                if (filter.equals(UploadStateEnum.DELAY.getCode())) {
                    this.filterDelayUnitData((List<IEntityRow>)var22_26, filterEntity, formScheme, dwEntity.getDimensionName(), formKey, abortTime);
                    if (this.isDelay(abortTime)) {
                        batchQueryUpload.setFilter(UploadStateEnum.ORIGINAL_UPLOAD.getCode());
                        this.filterUnitData((List<IEntityRow>)var22_26, dimensionValueSet, dwEntity, formScheme, batchQueryUpload, queryStartType, defaultWorkflow, filterEntity);
                        filterEntity = this.mergeDelayUnit(filterEntity);
                        batchQueryUpload.setFilter(UploadStateEnum.DELAY.getCode());
                    }
                } else {
                    this.filterUnitData((List<IEntityRow>)var22_26, dimensionValueSet, dwEntity, formScheme, batchQueryUpload, queryStartType, defaultWorkflow, filterEntity);
                }
                entitypage = this.subList(filterEntity, batchQueryUpload.getPageIndex(), batchQueryUpload.getSize());
            } else {
                entitypage = this.subList((List<IEntityRow>)var22_26, batchQueryUpload.getPageIndex(), batchQueryUpload.getSize());
            }
        } else {
            childEntity = iEntityTable.getChildRows(dwId);
            ArrayList<IEntityRow> arrayList = new ArrayList<IEntityRow>(childEntity);
            List<Object> acessChildEntity = new ArrayList();
            if (childEntity.size() == 0) {
                arrayList.add(selfRow);
            }
            if ((acessChildEntity = !StringUtils.isEmpty((String)formKey) ? this.overViewBaseService.filterAuthByEntity(context, arrayList, formKey, queryStartType) : arrayList) != null && acessChildEntity.isEmpty()) {
                acessChildEntity.add(selfRow);
            }
            if (StringUtils.isNotEmpty((String)batchQueryUpload.getFilter())) {
                List<IEntityRow> filterEntity = new ArrayList<IEntityRow>();
                if (filter.equals(UploadStateEnum.DELAY.getCode())) {
                    this.filterDelayUnitData(acessChildEntity, filterEntity, formScheme, dwEntity.getDimensionName(), formKey, abortTime);
                    if (this.isDelay(abortTime)) {
                        batchQueryUpload.setFilter(UploadStateEnum.ORIGINAL_UPLOAD.getCode());
                        this.filterUnitData(acessChildEntity, dimensionValueSet, dwEntity, formScheme, batchQueryUpload, queryStartType, defaultWorkflow, filterEntity);
                        filterEntity = this.mergeDelayUnit(filterEntity);
                        batchQueryUpload.setFilter(UploadStateEnum.DELAY.getCode());
                    }
                } else {
                    this.filterUnitData(acessChildEntity, dimensionValueSet, dwEntity, formScheme, batchQueryUpload, queryStartType, defaultWorkflow, filterEntity);
                }
                entitypage = this.subList(filterEntity, batchQueryUpload.getPageIndex(), batchQueryUpload.getSize());
            } else {
                entitypage = this.subList(acessChildEntity, batchQueryUpload.getPageIndex(), batchQueryUpload.getSize());
            }
        }
        for (IEntityRow iEntityRow : entitypage) {
            ArrayList<UploadActionInfo> uploadActionInfos = new ArrayList<UploadActionInfo>();
            this.getUploadActions(iEntityRow, dimensionValueSet, batchQueryUpload, formScheme, uploadActionInfos, dwEntity, iEntityTable);
            this.getUploadSum(iEntityRow, dimensionValueSet, batchQueryUpload.getFormKey(), formScheme, uploadSums, batchQueryUpload.getSummaryScope(), context, uploadActionInfos, defaultWorkflow, queryStartType, iEntityTable, batchQueryUpload.isLeafEntity(), batchQueryUpload.isFilterDiffUnit(), batchQueryUpload.isOnlyDirectChild(), abortTime, false, confirmBrforeUpload);
        }
        if (uploadSums.size() > 0) {
            ((UploadSumInfo)uploadSums.get(0)).setKeyTitle(this.overViewBaseService.getTitleMap(batchQueryUpload.getFormSchemeKey(), period, defaultWorkflow));
        }
        return uploadSums;
    }

    public void filterDelayUnitData(List<IEntityRow> acessChildEntity, List<IEntityRow> filterEntity, FormSchemeDefine formScheme, String mainDim, String formKey, Calendar abortTime) {
        DimensionValueSet dimensionAuth = new DimensionValueSet();
        List accessChilds = acessChildEntity.stream().map(IEntityItem::getEntityKeyData).collect(Collectors.toList());
        dimensionAuth.setValue(mainDim, accessChilds);
        List delayUploadRecord = this.queryUploadStateService.queryUploadDelay(formScheme, dimensionAuth, formKey, abortTime);
        List delayUnit = delayUploadRecord.stream().map(e -> e.getEntities().getValue(mainDim).toString()).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(delayUnit)) {
            filterEntity.addAll(acessChildEntity.stream().filter(e -> delayUnit.contains(e.getEntityKeyData())).collect(Collectors.toList()));
        }
    }

    public void filterUnitData(List<IEntityRow> acessChildEntity, DimensionValueSet dimensionValueSet, EntityViewData entityViewData, FormSchemeDefine formScheme, BatchQueryUpload batchQueryUpload, WorkFlowType queryStartType, boolean defaultWorkflow, List<IEntityRow> filterEntity) {
        for (IEntityRow entity : acessChildEntity) {
            dimensionValueSet.setValue(entityViewData.getDimensionName(), (Object)entity.getEntityKeyData());
            DataEntryParam dataEntryParam = new DataEntryParam();
            dataEntryParam.setDim(dimensionValueSet);
            if (WorkFlowType.FORM.equals((Object)queryStartType)) {
                dataEntryParam.setFormKey(batchQueryUpload.getFormKey());
                ArrayList<String> form = new ArrayList<String>();
                form.add(batchQueryUpload.getFormKey());
                dataEntryParam.setFormKeys(form);
            } else if (WorkFlowType.GROUP.equals((Object)queryStartType)) {
                dataEntryParam.setGroupKey(batchQueryUpload.getFormKey());
                ArrayList<String> group = new ArrayList<String>();
                group.add(batchQueryUpload.getFormKey());
                dataEntryParam.setGroupKeys(group);
            }
            dataEntryParam.setFormSchemeKey(formScheme.getKey());
            ActionState actionStates = this.dataentryFlowService.queryState(dataEntryParam);
            ActionStateBean actionState = null;
            switch (queryStartType) {
                case FORM: {
                    actionState = actionStates.getFormState();
                    break;
                }
                case GROUP: {
                    actionState = actionStates.getGroupState();
                    break;
                }
                default: {
                    actionState = actionStates.getUnitState();
                }
            }
            String filter = batchQueryUpload.getFilter();
            if (batchQueryUpload.isForceUpoload()) {
                if (!actionState.isForceUpload()) continue;
                if (!defaultWorkflow && actionState != null && filter.equals(UploadStateEnum.ORIGINAL_UPLOAD.getCode()) && actionState.getCode().equals(UploadStateEnum.SUBMITED.getCode())) {
                    filterEntity.add(entity);
                }
                if (actionState == null && (UploadStateEnum.ORIGINAL_SUBMIT.getCode().equals(filter) || UploadStateEnum.ORIGINAL_UPLOAD.getCode().equals(filter))) {
                    filterEntity.add(entity);
                }
                if ((actionState == null || !actionState.getCode().equals(filter)) && !actionState.getCode().equals(filter.equals(UploadState.ORIGINAL_UPLOAD.name()) ? UploadState.PART_START.name() : filter) && !actionState.getCode().equals(filter.equals(UploadState.UPLOADED.name()) ? UploadState.PART_UPLOADED.name() : filter)) continue;
                filterEntity.add(entity);
                continue;
            }
            if (!defaultWorkflow && filter.equals(UploadStateEnum.ORIGINAL_UPLOAD.getCode()) && actionState != null && (actionState.getCode().equals(UploadState.ORIGINAL_UPLOAD.name()) || actionState.getCode().equals(UploadState.SUBMITED.name()) || actionState.getCode().equals(UploadState.RETURNED.name()) || actionState.getCode().equals(UploadState.ORIGINAL_SUBMIT.name()))) {
                filterEntity.add(entity);
            }
            if (actionState == null && (UploadStateEnum.ORIGINAL_SUBMIT.getCode().equals(filter) || UploadStateEnum.ORIGINAL_UPLOAD.getCode().equals(filter))) {
                filterEntity.add(entity);
            }
            if (!(actionState != null && actionState.getCode().equals(filter) || actionState != null && actionState.getCode().equals(filter.equals(UploadState.ORIGINAL_UPLOAD.name()) ? UploadState.PART_START.name() : filter)) && (actionState == null || !actionState.getCode().equals(filter.equals(UploadState.UPLOADED.name()) ? UploadState.PART_UPLOADED.name() : filter))) continue;
            filterEntity.add(entity);
        }
    }

    public IEntityTable getEntityDataList(String formSchemeKey, Map<String, DimensionValue> dimensionValueSet) {
        IEntityTable iEntityTable = null;
        try {
            FormSchemeDefine formSchemeDefine = this.runtimeView.getFormScheme(formSchemeKey);
            EntityViewData dwEntity = this.jtableParamService.getDwEntity(formSchemeDefine.getKey());
            EntityViewDefine entityViewDefine = dwEntity.getEntityViewDefine();
            IEntityQuery iEntityQuery = this.entityDataService.newEntityQuery();
            iEntityQuery.setEntityView(entityViewDefine);
            HashMap<String, DimensionValue> dimensionValueMap = new HashMap<String, DimensionValue>();
            dimensionValueMap.put("DATATIME", dimensionValueSet.get("DATATIME"));
            iEntityQuery.setMasterKeys(DimensionValueSetUtil.getDimensionValueSet(dimensionValueMap));
            ExecutorContext executorContext = new ExecutorContext(this.runtimeController);
            ReportFmlExecEnvironment environment = new ReportFmlExecEnvironment(this.runtimeView, this.runtimeController, this.iEntityViewRunTimeController, formSchemeKey);
            executorContext.setEnv((IFmlExecEnvironment)environment);
            executorContext.setPeriodView(formSchemeDefine.getDateTime());
            iEntityQuery.sorted(true);
            iEntityTable = this.dataEntityFullService.executeEntityFullBuild(iEntityQuery, executorContext, entityViewDefine, formSchemeKey).getEntityTable();
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
        return iEntityTable;
    }

    public IEntityTable getEntityDataList(String formSchemeKey, Map<String, DimensionValue> dimensionValueSet, String root) {
        IEntityTable iEntityTable = null;
        try {
            FormSchemeDefine formSchemeDefine = this.runtimeView.getFormScheme(formSchemeKey);
            EntityViewData dwEntity = this.jtableParamService.getDwEntity(formSchemeDefine.getKey());
            EntityViewDefine entityViewDefine = this.runtimeView.getViewByFormSchemeKey(formSchemeDefine.getKey());
            IEntityQuery iEntityQuery = this.entityDataService.newEntityQuery();
            iEntityQuery.setEntityView(entityViewDefine);
            HashMap<String, DimensionValue> dimensionValueMap = new HashMap<String, DimensionValue>();
            dimensionValueMap.put("DATATIME", dimensionValueSet.get("DATATIME"));
            iEntityQuery.setMasterKeys(DimensionValueSetUtil.getDimensionValueSet(dimensionValueMap));
            ExecutorContext executorContext = new ExecutorContext(this.runtimeController);
            ReportFmlExecEnvironment environment = new ReportFmlExecEnvironment(this.runtimeView, this.runtimeController, this.iEntityViewRunTimeController, formSchemeKey);
            executorContext.setEnv((IFmlExecEnvironment)environment);
            executorContext.setPeriodView(formSchemeDefine.getDateTime());
            TreeRangeQuery rangeQuery = new TreeRangeQuery();
            ArrayList<String> parentKey = new ArrayList<String>();
            parentKey.add(root);
            rangeQuery.setParentKey(parentKey);
            iEntityQuery.sorted(true);
            iEntityTable = this.dataEntityFullService.executeEntityRangeBuild(iEntityQuery, executorContext, entityViewDefine, (RangeQuery)rangeQuery, formSchemeDefine.getKey()).getEntityTable();
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
        return iEntityTable;
    }

    public IEntityTable getEntityDataList1(String formSchemeKey, Map<String, DimensionValue> dimensionValueSet, String root) {
        IEntityTable iEntityTable = null;
        try {
            FormSchemeDefine formSchemeDefine = this.runtimeView.getFormScheme(formSchemeKey);
            EntityViewData dwEntity = this.jtableParamService.getDwEntity(formSchemeDefine.getKey());
            EntityViewDefine entityViewDefine = this.runtimeView.getViewByFormSchemeKey(formSchemeKey);
            IEntityQuery iEntityQuery = this.entityDataService.newEntityQuery();
            iEntityQuery.setEntityView(entityViewDefine);
            HashMap<String, DimensionValue> dimensionValueMap = new HashMap<String, DimensionValue>();
            dimensionValueMap.put("DATATIME", dimensionValueSet.get("DATATIME"));
            iEntityQuery.setMasterKeys(DimensionValueSetUtil.getDimensionValueSet(dimensionValueMap));
            ExecutorContext executorContext = new ExecutorContext(this.runtimeController);
            ReportFmlExecEnvironment environment = new ReportFmlExecEnvironment(this.runtimeView, this.runtimeController, this.iEntityViewRunTimeController, formSchemeKey);
            executorContext.setEnv((IFmlExecEnvironment)environment);
            executorContext.setPeriodView(formSchemeDefine.getDateTime());
            ArrayList<String> rootKeys = new ArrayList<String>();
            rootKeys.add(root);
            iEntityQuery.sorted(true);
            LevelRangeQuery rangeQuery = new LevelRangeQuery(3, rootKeys);
            iEntityTable = this.dataEntityFullService.executeEntityRangeBuild(iEntityQuery, executorContext, entityViewDefine, (RangeQuery)rangeQuery, formSchemeDefine.getKey()).getEntityTable();
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
        return iEntityTable;
    }

    public void setDefaultValue(Map<String, DimensionValue> newDimensionSet, WorkFlowType wordFlowType) {
        Iterator<String> iterator = newDimensionSet.keySet().iterator();
        while (iterator.hasNext()) {
            String key;
            switch (key = iterator.next()) {
                case "MD_CURRENCY_CODE": {
                    newDimensionSet.get(key).setValue("CNY");
                    break;
                }
                case "MD_GCADJTYPE_CODE": {
                    newDimensionSet.get(key).setValue("BEFOREADJ");
                    break;
                }
                case "MD_GCORGTYPE_CODE": {
                    if (wordFlowType.equals((Object)WorkFlowType.ENTITY)) break;
                    newDimensionSet.get(key).setValue("MD_ORG_CORPORATE");
                }
            }
        }
    }

    private void setDefaultValueNEW(Map<String, DimensionValue> newDimensionSet, WorkFlowType wordFlowType, String formSchemekey) {
        if (!wordFlowType.equals((Object)WorkFlowType.ENTITY)) {
            List entityList = this.jtableParamService.getEntityList(formSchemekey);
            for (int i = 0; i < entityList.size(); ++i) {
                EntityViewData entityViewData = (EntityViewData)entityList.get(i);
                String name = entityViewData.getDimensionName();
                DimensionValue dimensionValue = newDimensionSet.get(name);
                if (!StringUtils.isEmpty((String)dimensionValue.getValue())) continue;
                dimensionValue.setValue(entityViewData.getKey());
            }
        }
    }

    public List<UploadAllFormSumInfo> batchQueryState(BatchQueryUpload batchQueryUpload) {
        FormSchemeDefine formScheme = null;
        try {
            formScheme = this.runtimeView.getFormScheme(batchQueryUpload.getFormSchemeKey());
        }
        catch (Exception e1) {
            logger.error(e1.getMessage(), e1);
        }
        boolean defaultWorkflow = this.workflow.isDefaultWorkflow(formScheme.getKey());
        EntityViewData dwEntity = this.jtableParamService.getDwEntity(formScheme.getKey());
        EntityViewData dataTimeEntity = this.jtableParamService.getDataTimeEntity(formScheme.getKey());
        DimensionValueSet dimensionValueSet = DimensionValueSetUtil.getDimensionValueSet(batchQueryUpload.getDimensionSet());
        String dwId = dimensionValueSet.getValue(dwEntity.getDimensionName()).toString();
        String period = dimensionValueSet.getValue(dataTimeEntity.getDimensionName()).toString();
        JtableContext context = new JtableContext();
        context.setDimensionSet(batchQueryUpload.getDimensionSet());
        context.setFormSchemeKey(batchQueryUpload.getFormSchemeKey());
        context.setTaskKey(batchQueryUpload.getTaskKey());
        String formKeys = batchQueryUpload.getFormKey();
        IEntityTable iEntityTable = this.readerEntityTable(dwEntity.getKey(), dimensionValueSet, formScheme);
        List<String> entityIds = this.getChildEntityIDs(iEntityTable, batchQueryUpload.getSummaryScope(), dwId);
        if (entityIds.size() == 0) {
            entityIds.add(dwId);
        }
        DimensionValueSet dimension = new DimensionValueSet(dimensionValueSet);
        dimension.setValue(dwEntity.getDimensionName(), entityIds);
        Map dimensionSet = DimensionValueSetUtil.getDimensionSet((DimensionValueSet)dimension);
        JtableContext jtableContext = new JtableContext();
        jtableContext.setDimensionSet(dimensionSet);
        jtableContext.setFormSchemeKey(batchQueryUpload.getFormSchemeKey());
        jtableContext.setTaskKey(batchQueryUpload.getTaskKey());
        WorkFlowType queryStartType = this.dataentryFlowService.queryStartType(formScheme.getKey());
        Map<String, String> filterForm = this.overViewBaseService.getFilterForm(queryStartType, dwId, dwEntity.getDimensionName(), jtableContext);
        ArrayList<UploadAllFormSumInfo> queryAllFormState = new ArrayList<UploadAllFormSumInfo>();
        if (WorkFlowType.FORM.equals((Object)queryStartType)) {
            ArrayList<FormDefine> queryAllFormDefinesByFormScheme = new ArrayList<FormDefine>();
            if (StringUtils.isNotEmpty((String)formKeys) && !formKeys.equals("allForm")) {
                for (String formKey : formKeys.split(";")) {
                    FormDefine queryFormById = this.runTimeViewController.getForm(formKey, formScheme.getKey());
                    queryAllFormDefinesByFormScheme.add(queryFormById);
                }
            } else {
                List formGroupDefines = this.runTimeViewController.listFormGroupByFormScheme(formScheme.getKey());
                for (FormGroupDefine formGroupDefine : formGroupDefines) {
                    String key = formGroupDefine.getKey();
                    List formDefines = this.runTimeViewController.listFormByGroup(key, formScheme.getKey());
                    queryAllFormDefinesByFormScheme.addAll(formDefines);
                }
            }
            for (FormDefine form : queryAllFormDefinesByFormScheme) {
                String formKeysInFilter = filterForm.get(form.getKey());
                int length = 0;
                if (!StringUtils.isNotEmpty((String)formKeysInFilter)) continue;
                length = filterForm.get(form.getKey()).split(";").length;
                LinkedHashMap<String, UploadAllFormSumInfo> formToSum = new LinkedHashMap<String, UploadAllFormSumInfo>();
                UploadAllFormSumInfo uploadSum = new UploadAllFormSumInfo();
                uploadSum.setMasterSum(length > 0 ? length : 1);
                formToSum.put(form.getKey(), uploadSum);
                DimensionValueSet dimensionByForm = new DimensionValueSet(dimension);
                String[] split = filterForm.get(form.getKey()).split(";");
                List<String> asList = Arrays.asList(split);
                dimensionByForm.setValue(dwEntity.getDimensionName(), asList);
                queryAllFormState.addAll(this.queryUploadStateService.queryAllFormState(dimensionByForm, form.getKey(), formScheme, entityIds, queryStartType, formToSum));
            }
        } else if (WorkFlowType.GROUP.equals((Object)queryStartType)) {
            List<String> authFormGroup = this.overViewBaseService.getAuthFormGroup(context);
            for (String formGroup : authFormGroup) {
                int length = filterForm.get(formGroup).split(";").length;
                UploadAllFormSumInfo uploadSum = new UploadAllFormSumInfo();
                if (filterForm.get(formGroup) != null && length > 0) {
                    uploadSum.setMasterSum(length);
                } else {
                    uploadSum.setMasterSum(1);
                }
                LinkedHashMap<String, UploadAllFormSumInfo> formToSum = new LinkedHashMap<String, UploadAllFormSumInfo>();
                formToSum.put(formGroup, uploadSum);
                DimensionValueSet dimensionByFormGroup = new DimensionValueSet(dimension);
                String[] split = filterForm.get(formGroup).split(";");
                List<String> asList = Arrays.asList(split);
                dimensionByFormGroup.setValue(dwEntity.getDimensionName(), asList);
                queryAllFormState.addAll(this.queryUploadStateService.queryAllFormState(dimensionByFormGroup, formGroup, formScheme, entityIds, queryStartType, formToSum));
            }
        }
        if (queryAllFormState.size() > 0) {
            ((UploadAllFormSumInfo)queryAllFormState.get(0)).setKeyTitle(this.overViewBaseService.getTitleMap(batchQueryUpload.getFormSchemeKey(), period, defaultWorkflow));
        }
        return queryAllFormState;
    }

    public String getPeriod(String period, String formSchemeKey) {
        StringBuilder periodTitle = new StringBuilder();
        if (period.contains(",")) {
            String[] periodArray;
            for (String periodInfo : periodArray = period.split(",")) {
                periodTitle.append(this.exportExcelNameService.getPeriodTitle(formSchemeKey, periodInfo));
                periodTitle.append(",");
            }
            return periodTitle.substring(0, periodTitle.lastIndexOf(","));
        }
        if (period.contains("-")) {
            String[] periodArray;
            for (String periodInfo : periodArray = period.split("-")) {
                periodTitle.append(this.exportExcelNameService.getPeriodTitle(formSchemeKey, periodInfo));
                periodTitle.append("-");
            }
            return periodTitle.substring(0, periodTitle.lastIndexOf("-"));
        }
        return this.exportExcelNameService.getPeriodTitle(formSchemeKey, period);
    }

    public List<ITree<TaskTreeObj>> getAllTaskGroupDataS() {
        List<ITree<TaskTreeObj>> tree_Task = this.getTaskTreeList();
        return tree_Task;
    }

    public List<ITree<TaskTreeObj>> queryTaskByKey(TaskGroupParam taskGroupParam) {
        List<ITree<TaskTreeObj>> tree_Task = this.queryTaskList(taskGroupParam);
        return tree_Task;
    }

    public List<ITaskTreeNode> getAllTaskGroup() {
        ArrayList<ITaskTreeNode> taskTreeObjs = new ArrayList<ITaskTreeNode>();
        List<ITree<TaskTreeObj>> tree_Task = this.getTaskTree();
        for (ITree<TaskTreeObj> tree : tree_Task) {
            ITaskTreeNode taskTree = new ITaskTreeNode();
            taskTree.setKey(tree.getKey());
            taskTree.setCode(tree.getCode());
            taskTree.setTitle(tree.getTitle());
            List children = tree.getChildren();
            this.buildChidren(taskTree, children);
            taskTreeObjs.add(taskTree);
        }
        return taskTreeObjs;
    }

    private void buildChidren(ITaskTreeNode taskTree, List<ITree<TaskTreeObj>> children) {
        if (children != null && children.size() > 0) {
            ArrayList<ITaskTreeNode> childrenTaskTrees = new ArrayList<ITaskTreeNode>();
            for (ITree<TaskTreeObj> tree : children) {
                ITaskTreeNode childrenTaskTree = new ITaskTreeNode();
                childrenTaskTree.setKey(tree.getKey());
                childrenTaskTree.setCode(tree.getCode());
                childrenTaskTree.setTitle(tree.getTitle());
                List children1 = tree.getChildren();
                this.buildChidren(childrenTaskTree, children1);
                childrenTaskTrees.add(childrenTaskTree);
            }
            taskTree.setChildren(childrenTaskTrees);
        }
    }

    public void getUploadSum(IEntityRow entityRow, DimensionValueSet dimensionValueSet, String formKey, FormSchemeDefine formScheme, List<UploadSumInfo> uploadSums, int summaryScope, JtableContext jtableContext, List<UploadActionInfo> uploadActions, boolean flowsType, WorkFlowType queryStartType, IEntityTable iEntityTable, boolean leafEntity, boolean filterDiffUnit, boolean onlyDirectChild, Calendar abortTime, boolean self, boolean confirmBrforeUpload) {
        List<String> entityIds;
        String entityKeyData = entityRow.getEntityKeyData();
        int directChildCount = iEntityTable.getDirectChildCount(entityKeyData);
        UploadSumInfo uploadSumInfo = new UploadSumInfo(entityKeyData, entityRow.getCode(), entityRow.getTitle(), directChildCount);
        uploadSumInfo.setConfirmBeforeUpload(confirmBrforeUpload);
        EntityViewData dwEntity = this.jtableParamService.getDwEntity(formScheme.getKey());
        int showChildCount_summaryScope = summaryScope;
        if (!onlyDirectChild && !self) {
            showChildCount_summaryScope = 2;
        }
        if (!onlyDirectChild) {
            summaryScope = 2;
        }
        if ((entityIds = this.getChildEntityIDs(iEntityTable, summaryScope, entityKeyData)).size() == 0) {
            entityIds.add(entityKeyData);
        }
        DimensionValueSet dimension = new DimensionValueSet(dimensionValueSet);
        dimension.setValue(dwEntity.getDimensionName(), entityIds);
        Map dimensionSet = DimensionValueSetUtil.getDimensionSet((DimensionValueSet)dimension);
        jtableContext.setDimensionSet(dimensionSet);
        DimensionValueSet dimensionAuth = new DimensionValueSet(dimensionValueSet);
        List<String> filterAuth = entityIds;
        filterAuth = this.overViewBaseService.filterAuth(jtableContext, entityIds, formKey, dwEntity, queryStartType);
        if (filterAuth != null && filterAuth.isEmpty()) {
            dimensionAuth.setValue(dwEntity.getDimensionName(), (Object)entityKeyData);
        } else {
            dimensionAuth.setValue(dwEntity.getDimensionName(), filterAuth);
        }
        if (WorkFlowType.ENTITY.equals((Object)queryStartType)) {
            formKey = null;
        }
        EntityViewDefine entityViewDefine = dwEntity.getEntityViewDefine();
        UploadSumNew uploadSum = null;
        try {
            uploadSum = this.queryUploadStateService.queryUploadSumNew(dimensionAuth, formKey, formScheme, flowsType, entityKeyData, dwEntity.getDimensionName(), entityViewDefine, iEntityTable, leafEntity, filterDiffUnit, onlyDirectChild, abortTime);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        if (uploadActions != null && uploadActions.size() > 0 && (e = uploadActions.iterator()).hasNext()) {
            UploadActionInfo uploadActionInfo = e.next();
            uploadActionInfo.getId().equals(entityKeyData);
            uploadSum.setRejectedCount(uploadActionInfo.getRejectedCount());
            uploadSum.setUploadNums(uploadActionInfo.getUploadedCount());
        }
        if ((entityIds = this.getChildEntityIDs(iEntityTable, showChildCount_summaryScope, entityKeyData)).size() == 0) {
            entityIds.add(entityKeyData);
        }
        DimensionValueSet dimension1 = new DimensionValueSet(dimensionValueSet);
        dimension1.setValue(dwEntity.getDimensionName(), entityIds);
        Map dimensionSet1 = DimensionValueSetUtil.getDimensionSet((DimensionValueSet)dimension1);
        JtableContext context = new JtableContext(jtableContext);
        context.setDimensionSet(dimensionSet1);
        filterAuth = entityIds;
        filterAuth = this.overViewBaseService.filterAuth(context, entityIds, formKey, dwEntity, queryStartType);
        uploadSumInfo.setSum(uploadSum);
        uploadSumInfo.setChildCount(filterAuth.size());
        uploadSums.add(uploadSumInfo);
    }

    public List<String> getChildEntityIDs(IEntityTable entityTable, int summaryScope, String entityKey) {
        ArrayList<String> entityIds = new ArrayList<String>();
        List allChildRows = new ArrayList();
        if (entityTable != null) {
            allChildRows = summaryScope > 1 ? entityTable.getAllChildRows(entityKey) : entityTable.getChildRows(entityKey);
        }
        for (IEntityRow childrenRow : allChildRows) {
            entityIds.add(childrenRow.getEntityKeyData());
        }
        return entityIds;
    }

    private List<IEntityRow> getChildEntitys(IEntityTable entityTable, int summaryScope, String entityKey) {
        List allRows = new ArrayList();
        allRows = summaryScope > 1 ? entityTable.getAllChildRows(entityKey) : entityTable.getChildRows(entityKey);
        return allRows;
    }

    public List<String> getChildEntityIDs(EntityData entitydata, int summaryScope, String tableName) {
        ArrayList<String> entityIds = new ArrayList<String>();
        for (EntityData entityChildData : entitydata.getChildren()) {
            entityIds.add(entityChildData.getId());
            if (summaryScope <= 1) continue;
            entityIds.addAll(this.getChildEntityIDs(entityChildData, summaryScope, tableName));
        }
        return entityIds;
    }

    private List<ITree<TaskTreeObj>> getTaskTreeList() {
        List designTaskGroupDefines = new ArrayList();
        try {
            designTaskGroupDefines = this.designTaskGroupDefineService.queryAllTaskGroupDefine();
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
        ArrayList<ITree<TaskTreeObj>> tree_Task = new ArrayList<ITree<TaskTreeObj>>();
        if (designTaskGroupDefines.size() > 0) {
            designTaskGroupDefines.forEach(taskGroupDefine -> tree_Task.add(this.getGroupTreeNode((DesignTaskGroupDefine)taskGroupDefine, false, false)));
        }
        return tree_Task;
    }

    private List<ITree<TaskTreeObj>> queryTaskList(TaskGroupParam taskGroupParam) {
        List<TaskGroupDefine> taskGroupDefines = new ArrayList();
        try {
            if (StringUtils.isNotEmpty((String)taskGroupParam.getGroupKey())) {
                TaskGroupDefine taskGroup = this.runTimeViewController.getTaskGroup(taskGroupParam.getGroupKey());
                if (taskGroup != null) {
                    taskGroupDefines.add(taskGroup);
                    this.queryParentGroup(taskGroup, taskGroupDefines);
                }
            } else {
                taskGroupDefines = this.runTimeViewController.listAllTaskGroup();
            }
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
        ArrayList<ITree<TaskTreeObj>> tree_Task = new ArrayList<ITree<TaskTreeObj>>();
        if (taskGroupDefines.size() > 0) {
            taskGroupDefines.forEach(taskGroupDefine -> tree_Task.add(this.getGroupTreeNode((TaskGroupDefine)taskGroupDefine, false, false)));
        }
        return tree_Task;
    }

    private void queryParentGroup(TaskGroupDefine taskGroup, List<TaskGroupDefine> taskGroupDefines) {
        TaskGroupDefine parentTaskGroup;
        if (taskGroup != null && taskGroup.getParentKey() != null && (parentTaskGroup = this.runTimeViewController.getTaskGroup(taskGroup.getParentKey())) != null) {
            taskGroupDefines.add(parentTaskGroup);
            this.queryParentGroup(parentTaskGroup, taskGroupDefines);
        }
    }

    private ITree<TaskTreeObj> getGroupTreeNode(DesignTaskGroupDefine taskGroupDefine, boolean isChecked, boolean isExpanded) {
        ITree node = new ITree((INode)new TaskTreeObj(taskGroupDefine));
        node.setLeaf(false);
        node.setNoDrop(true);
        node.setNoDrag(false);
        if (isChecked) {
            node.setChecked(true);
        }
        if (isExpanded) {
            node.setExpanded(true);
        }
        return node;
    }

    private ITree<TaskTreeObj> getGroupTreeNode(TaskGroupDefine taskGroupDefine, boolean isChecked, boolean isExpanded) {
        ITree node = new ITree((INode)new TaskTreeObj(taskGroupDefine));
        node.setLeaf(false);
        node.setNoDrop(true);
        node.setNoDrag(false);
        if (isChecked) {
            node.setChecked(true);
        }
        if (isExpanded) {
            node.setExpanded(true);
        }
        return node;
    }

    public List<EntityData> getChildEntitys(EntityData entitydata, int summaryScope) {
        ArrayList<EntityData> entitys = new ArrayList<EntityData>();
        for (EntityData entityChildData : entitydata.getChildren()) {
            entitys.add(entityChildData);
            if (summaryScope <= 1) continue;
            entitys.addAll(this.getChildEntitys(entityChildData, summaryScope));
        }
        return entitys;
    }

    public List<UploadActionInfo> batchUploadActions(BatchQueryUpload batchQueryUpload) {
        FormSchemeDefine formScheme = null;
        try {
            formScheme = this.runtimeView.getFormScheme(batchQueryUpload.getFormSchemeKey());
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
        ArrayList<UploadActionInfo> uploadActions = new ArrayList<UploadActionInfo>();
        EntityViewData dwEntity = this.jtableParamService.getDwEntity(formScheme.getKey());
        DimensionValueSet dimensionValueSet = DimensionValueSetUtil.getDimensionValueSet(batchQueryUpload.getDimensionSet());
        String dwId = dimensionValueSet.getValue(dwEntity.getDimensionName()).toString();
        JtableContext context = new JtableContext();
        context.setDimensionSet(batchQueryUpload.getDimensionSet());
        context.setFormKey(batchQueryUpload.getFormKey());
        context.setFormSchemeKey(batchQueryUpload.getFormSchemeKey());
        context.setTaskKey(batchQueryUpload.getTaskKey());
        DimensionValueSet dimensionValueSet1 = new DimensionValueSet();
        dimensionValueSet1.setValue("DATATIME", (Object)batchQueryUpload.getDimensionSet().get("DATATIME").getValue());
        IEntityTable entityTable = this.rangeQueryEntityTable(dwEntity.getKey(), dwId, formScheme, dimensionValueSet1);
        IEntityRow selfEntityRow = entityTable.findByEntityKey(dwId);
        UploadActionInfo uploadActionInfo = new UploadActionInfo(dwId, selfEntityRow.getCode(), selfEntityRow.getTitle(), entityTable.getAllChildCount(dwId) <= 0);
        DimensionValueSet dimension = new DimensionValueSet(dimensionValueSet);
        dimension.setValue(dwEntity.getDimensionName(), (Object)dwId);
        List uploadHistoryStates = this.queryUploadStateService.queryUploadHistoryStates(formScheme.getKey(), dimension, batchQueryUpload.getFormKey(), batchQueryUpload.getFormKey());
        uploadActionInfo.setActions(uploadHistoryStates);
        uploadActions.add(uploadActionInfo);
        ShowResult nodes = new ShowResult();
        try {
            ShowNodeParam nodeParam = new ShowNodeParam();
            nodeParam.setDimensionSetMap(batchQueryUpload.getDimensionSet());
            nodeParam.setFormKey(batchQueryUpload.getFormKey());
            nodeParam.setFormSchemeKey(batchQueryUpload.getFormSchemeKey());
            nodeParam.setGroupKey(batchQueryUpload.getFormKey());
            nodeParam.setPeriod(dimension.getValue("DATATIME").toString());
            nodes = this.workflowSettingService.showWorkflow(nodeParam);
            uploadActionInfo.setShowResult(nodes);
            boolean flowsType = this.workflow.isDefaultWorkflow(formScheme.getKey());
            uploadActionInfo.setDefaultFlow(flowsType);
        }
        catch (Exception e) {
            logger.info(SettingError.S_ERROR + "\uff1a\u52a0\u8f7d\u6570\u636e\u5931\u8d25");
        }
        return uploadActions;
    }

    public List<ActionInfo> batchWorkFlow(QueryUploadStateInfo queryUploadStateInfo) {
        String formSchemeKey = queryUploadStateInfo.getFormSchemeKey();
        Map actionInfo = this.treeWorkFlow.getActionCodeAndStateName(formSchemeKey);
        Map actionCodeAndActionName = this.treeWorkFlow.getActionCodeAndActionName(formSchemeKey);
        LinkedList<ActionInfo> tableInfo = new LinkedList<ActionInfo>();
        FormSchemeDefine formScheme = null;
        try {
            TaskFlowsDefine flowsSetting;
            formScheme = this.runtimeView.getFormScheme(formSchemeKey);
            boolean flowsType = this.workflow.isDefaultWorkflow(formScheme.getKey());
            ActionInfo entity = new ActionInfo();
            entity.setKey("entity");
            entity.setTitle("\u5355\u4f4d\u540d\u79f0");
            entity.setType("1");
            tableInfo.add(entity);
            ActionInfo unitCode = new ActionInfo();
            unitCode.setKey("unitCode");
            unitCode.setTitle("\u5355\u4f4d\u4ee3\u7801");
            unitCode.setType("2");
            tableInfo.add(unitCode);
            ActionInfo masterSum = new ActionInfo();
            masterSum.setKey("masterSum");
            masterSum.setTitle("\u603b\u6570");
            masterSum.setType("1");
            tableInfo.add(masterSum);
            if (actionInfo.containsKey("start")) {
                if (actionInfo.containsKey("act_submit") || actionInfo.containsKey("cus_submit") && flowsType) {
                    ActionInfo unSubmmit = new ActionInfo();
                    unSubmmit.setKey("unSubmitedNum");
                    unSubmmit.setTitle((String)actionInfo.get("start"));
                    unSubmmit.setType("1");
                    tableInfo.add(unSubmmit);
                } else {
                    ActionInfo original = new ActionInfo();
                    original.setKey("originalNum");
                    if (!flowsType) {
                        original.setTitle("\u672a\u4e0a\u62a5");
                    } else {
                        original.setTitle((String)actionInfo.get("start"));
                    }
                    original.setType("1");
                    tableInfo.add(original);
                }
            }
            if (flowsType) {
                if (actionInfo.containsKey("act_submit") || actionInfo.containsKey("cus_submit")) {
                    ActionInfo submmited = new ActionInfo();
                    submmited.setKey("submitedNum");
                    if (actionInfo.containsKey("act_submit")) {
                        submmited.setTitle((String)actionInfo.get("act_submit"));
                    } else {
                        submmited.setTitle((String)actionInfo.get("cus_submit"));
                    }
                    submmited.setType("1");
                    tableInfo.add(submmited);
                }
                if (actionInfo.containsKey("act_return") || actionInfo.containsKey("cus_return")) {
                    ActionInfo returnInfo = new ActionInfo();
                    returnInfo.setKey("returnedNum");
                    if (actionInfo.containsKey("act_return")) {
                        returnInfo.setTitle((String)actionInfo.get("act_return"));
                    } else {
                        returnInfo.setTitle((String)actionInfo.get("cus_return"));
                    }
                    returnInfo.setType("1");
                    tableInfo.add(returnInfo);
                }
            }
            if ((flowsSetting = formScheme.getFlowsSetting()).isUnitSubmitForCensorship()) {
                ActionInfo submmitPer = new ActionInfo();
                submmitPer.setKey("submitedrate");
                String title = actionCodeAndActionName.containsKey("act_submit") ? (String)actionCodeAndActionName.get("act_submit") + "\u7387" : "\u9001\u5ba1\u7387";
                submmitPer.setTitle(title);
                submmitPer.setType("1");
                tableInfo.add(submmitPer);
                ActionInfo commitedTime = new ActionInfo();
                commitedTime.setKey("submitedTime");
                String titleTime = actionCodeAndActionName.containsKey("act_submit") ? (String)actionCodeAndActionName.get("act_submit") + "\u65f6\u95f4" : "\u9001\u5ba1\u65f6\u95f4";
                commitedTime.setTitle(titleTime);
                commitedTime.setType("2");
                tableInfo.add(commitedTime);
                String returnTitleTime = actionCodeAndActionName.containsKey("act_return") ? (String)actionCodeAndActionName.get("act_return") + "\u65f6\u95f4" : "\u9000\u5ba1\u65f6\u95f4";
                ActionInfo retrunTime = new ActionInfo();
                retrunTime.setKey("returnedTime");
                retrunTime.setTitle(returnTitleTime);
                retrunTime.setType("2");
                tableInfo.add(retrunTime);
            }
            ActionInfo rejectedTime = new ActionInfo();
            rejectedTime.setKey("rejectTime");
            String rejectTitleTime = actionCodeAndActionName.containsKey("act_reject") ? (String)actionCodeAndActionName.get("act_reject") + "\u65f6\u95f4" : "\u9000\u56de\u65f6\u95f4";
            rejectedTime.setTitle(rejectTitleTime);
            rejectedTime.setType("2");
            tableInfo.add(rejectedTime);
            ActionInfo uploadNums = new ActionInfo();
            uploadNums.setKey("uploadNums");
            uploadNums.setTitle("\u4e0a\u62a5\u6b21\u6570");
            uploadNums.setType("2");
            tableInfo.add(uploadNums);
            ActionInfo firstUploadExplain = new ActionInfo();
            firstUploadExplain.setKey("firstUploadExplain");
            firstUploadExplain.setTitle("\u9996\u6b21\u4e0a\u62a5\u8bf4\u660e");
            firstUploadExplain.setType("2");
            tableInfo.add(firstUploadExplain);
            ActionInfo uploadExplain = new ActionInfo();
            uploadExplain.setKey("uploadExplain");
            uploadExplain.setTitle("\u6700\u65b0\u4e0a\u62a5\u8bf4\u660e");
            uploadExplain.setType("2");
            tableInfo.add(uploadExplain);
            ActionInfo rejectedExplain = new ActionInfo();
            rejectedExplain.setKey("rejectedExplain");
            rejectedExplain.setTitle("\u9000\u56de\u8bf4\u660e");
            rejectedExplain.setType("2");
            tableInfo.add(rejectedExplain);
            ActionInfo rejectedCount = new ActionInfo();
            rejectedCount.setKey("rejectedCount");
            rejectedCount.setTitle("\u9000\u56de\u6b21\u6570");
            rejectedCount.setType("2");
            tableInfo.add(rejectedCount);
            ActionInfo endUploadTime = new ActionInfo();
            endUploadTime.setKey("endUploadTime");
            String fisrsUploadTime = actionCodeAndActionName.containsKey("act_upload") ? "\u6700\u65b0" + (String)actionCodeAndActionName.get("act_upload") + "\u65f6\u95f4" : "\u6700\u65b0\u4e0a\u62a5\u65f6\u95f4";
            endUploadTime.setTitle(fisrsUploadTime);
            endUploadTime.setType("2");
            tableInfo.add(endUploadTime);
            ActionInfo firstUploadTime = new ActionInfo();
            firstUploadTime.setKey("firstUploadTime");
            String eUploadTime = actionCodeAndActionName.containsKey("act_upload") ? "\u9996\u6b21" + (String)actionCodeAndActionName.get("act_upload") + "\u65f6\u95f4" : "\u9996\u6b21\u4e0a\u62a5\u65f6\u95f4";
            firstUploadTime.setTitle(eUploadTime);
            firstUploadTime.setType("2");
            tableInfo.add(firstUploadTime);
            if (actionInfo.containsKey("act_reject") || actionInfo.containsKey("cus_reject")) {
                ActionInfo rejectedNum = new ActionInfo();
                rejectedNum.setKey("rejectedNum");
                if (actionInfo.containsKey("act_reject")) {
                    rejectedNum.setTitle((String)actionInfo.get("act_reject"));
                } else {
                    rejectedNum.setTitle((String)actionInfo.get("cus_reject"));
                }
                rejectedNum.setType("1");
                tableInfo.add(rejectedNum);
            }
            if (actionInfo.containsKey("act_upload") || actionInfo.containsKey("cus_upload")) {
                ActionInfo uploadedNum = new ActionInfo();
                uploadedNum.setKey("uploadedNum");
                if (actionInfo.containsKey("act_upload")) {
                    uploadedNum.setTitle((String)actionInfo.get("act_upload"));
                } else {
                    uploadedNum.setTitle((String)actionInfo.get("cus_upload"));
                }
                uploadedNum.setType("1");
                tableInfo.add(uploadedNum);
            }
            ActionInfo uploadedper = new ActionInfo();
            uploadedper.setKey("uploadedper");
            String UploadTitle = actionCodeAndActionName.containsKey("act_upload") ? (String)actionCodeAndActionName.get("act_upload") + "\u7387" : "\u4e0a\u62a5\u7387";
            uploadedper.setTitle(UploadTitle);
            uploadedper.setType("1");
            tableInfo.add(uploadedper);
            if (actionInfo.containsKey("act_confirm") || actionInfo.containsKey("cus_confirm")) {
                ActionInfo confirmedNum = new ActionInfo();
                confirmedNum.setKey("confirmedNum");
                if (actionInfo.containsKey("act_confirm")) {
                    confirmedNum.setTitle((String)actionInfo.get("act_confirm"));
                } else {
                    confirmedNum.setTitle((String)actionInfo.get("cus_confirm"));
                }
                confirmedNum.setType("1");
                tableInfo.add(confirmedNum);
                ActionInfo comfirmedTime = new ActionInfo();
                comfirmedTime.setKey("comfirmedTime");
                String cTime = actionCodeAndActionName.containsKey("act_confirm") ? (String)actionCodeAndActionName.get("act_confirm") + "\u65f6\u95f4" : "\u786e\u8ba4\u65f6\u95f4";
                comfirmedTime.setTitle(cTime);
                comfirmedTime.setType("2");
                tableInfo.add(comfirmedTime);
                ActionInfo confirmedper = new ActionInfo();
                confirmedper.setKey("confirmedper");
                String cper = actionCodeAndActionName.containsKey("act_confirm") ? (String)actionCodeAndActionName.get("act_confirm") + "\u7387" : "\u786e\u8ba4\u7387";
                confirmedper.setTitle(cper);
                confirmedper.setType("1");
                tableInfo.add(confirmedper);
            }
            if (actionInfo.containsKey("act_cancel_confirm")) {
                ActionInfo returnInfo = new ActionInfo();
                returnInfo.setKey("cancelConfirmNum");
                returnInfo.setTitle((String)actionInfo.get("act_cancel_confirm"));
                returnInfo.setType("1");
                tableInfo.add(returnInfo);
            }
            ActionInfo details = new ActionInfo();
            details.setKey("details");
            details.setTitle("\u8be6\u60c5");
            details.setType("1");
            tableInfo.add(details);
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
        return tableInfo;
    }

    public void getUploadAction(IEntityRow entityRow, DimensionValueSet dimensionValueSet, BatchQueryUpload batchQueryUpload, FormSchemeDefine formScheme, List<UploadActionInfo> uploadActions, EntityViewData entityViewData, IEntityTable iEntityTable) {
        UploadActionInfo uploadActionInfo = new UploadActionInfo(entityRow.getEntityKeyData(), entityRow.getCode(), entityRow.getTitle(), iEntityTable.getDirectChildCount(entityRow.getEntityKeyData()) <= 0);
        DimensionValueSet dimension = new DimensionValueSet(dimensionValueSet);
        dimension.setValue(entityViewData.getDimensionName(), (Object)entityRow.getEntityKeyData());
        List uploadHistoryStates = this.queryUploadStateService.queryUploadHistoryStates(formScheme.getKey(), dimension, batchQueryUpload.getFormKey(), batchQueryUpload.getFormKey());
        uploadActionInfo.setActions(uploadHistoryStates);
        uploadActions.add(uploadActionInfo);
        if (batchQueryUpload.getSummaryScope() > 1) {
            List allChildRows = iEntityTable.getAllChildRows(entityRow.getEntityKeyData());
            for (IEntityRow childRow : allChildRows) {
                UploadActionInfo childrenInfo = new UploadActionInfo(childRow.getEntityKeyData(), childRow.getCode(), childRow.getTitle(), iEntityTable.getDirectChildCount(childRow.getEntityKeyData()) <= 0);
                dimension.setValue(entityViewData.getDimensionName(), (Object)childRow.getEntityKeyData());
                List uploadHistoryStates1 = this.queryUploadStateService.queryUploadHistoryStates(formScheme.getKey(), dimension, batchQueryUpload.getFormKey(), batchQueryUpload.getFormKey());
                childrenInfo.setActions(uploadHistoryStates1);
                uploadActions.add(childrenInfo);
            }
        }
    }

    public void getUploadActions(IEntityRow entityRow, DimensionValueSet dimensionValueSet, BatchQueryUpload batchQueryUpload, FormSchemeDefine formScheme, List<UploadActionInfo> uploadActions, EntityViewData entityViewData, IEntityTable entityTable) {
        String entityKeyData = entityRow.getEntityKeyData();
        if (batchQueryUpload.getSummaryScope() > 1) {
            List<IEntityRow> childEntitys = this.getChildEntitys(entityTable, 2, entityKeyData);
            List collect = childEntitys.stream().map(item -> item.getEntityKeyData()).collect(Collectors.toList());
            DimensionValueSet dimension = new DimensionValueSet(dimensionValueSet);
            dimension.setValue(entityViewData.getDimensionName(), collect);
            List queryUploadActionsNew = this.queryUploadStateService.queryUploadHistoryStates(formScheme.getKey(), dimension, batchQueryUpload.getFormKey(), batchQueryUpload.getFormKey());
            for (UploadRecordNew uploadRe : queryUploadActionsNew) {
                JtableContext context = new JtableContext();
                context.setDimensionSet(batchQueryUpload.getDimensionSet());
                context.setFormSchemeKey(batchQueryUpload.getFormSchemeKey());
                context.setTaskKey(batchQueryUpload.getTaskKey());
                Object unitKey = uploadRe.getEntities().getValue(entityViewData.getDimensionName());
                if (unitKey == null) continue;
                EntityQueryByKeyInfo entityQueryByKeyInfo = new EntityQueryByKeyInfo();
                entityQueryByKeyInfo.setEntityViewKey(entityViewData.getKey());
                entityQueryByKeyInfo.setEntityKey(unitKey.toString());
                entityQueryByKeyInfo.setContext(context);
                EntityByKeyReturnInfo entityDataByKey = this.jtableEntityService.queryEntityDataByKey(entityQueryByKeyInfo);
                EntityData entitySelf = entityDataByKey.getEntity();
                UploadActionInfo uploadActionInfo = new UploadActionInfo(entitySelf);
                DimensionValueSet dimension1 = new DimensionValueSet(dimensionValueSet);
                dimension1.setValue(entityViewData.getDimensionName(), (Object)entityKeyData);
                List uploadHistoryStates = this.queryUploadStateService.queryUploadHistoryStates(formScheme.getKey(), dimension1, batchQueryUpload.getFormKey(), batchQueryUpload.getFormKey());
                uploadActionInfo.setActions(uploadHistoryStates);
                uploadActionInfo.getActions().add(uploadRe);
                uploadActions.add(uploadActionInfo);
            }
            if (childEntitys == null || childEntitys.isEmpty()) {
                UploadActionInfo uploadActionInfo = new UploadActionInfo(entityRow.getEntityKeyData(), entityRow.getCode(), entityRow.getTitle(), entityTable.getDirectChildCount(entityKeyData) <= 0);
                DimensionValueSet dimension1 = new DimensionValueSet(dimensionValueSet);
                dimension1.setValue(entityViewData.getDimensionName(), (Object)entityKeyData);
                List uploadHistoryStates = this.queryUploadStateService.queryUploadHistoryStates(formScheme.getKey(), dimension1, batchQueryUpload.getFormKey(), batchQueryUpload.getFormKey());
                uploadActionInfo.setActions(uploadHistoryStates);
                uploadActions.add(uploadActionInfo);
            }
        } else {
            UploadActionInfo uploadActionInfo = new UploadActionInfo(entityRow.getEntityKeyData(), entityRow.getCode(), entityRow.getTitle(), entityTable.getDirectChildCount(entityKeyData) <= 0);
            DimensionValueSet dimension = new DimensionValueSet(dimensionValueSet);
            dimension.setValue(entityViewData.getDimensionName(), (Object)entityKeyData);
            List uploadHistoryStates = this.queryUploadStateService.queryUploadHistoryStates(formScheme.getKey(), dimension, batchQueryUpload.getFormKey(), batchQueryUpload.getFormKey());
            uploadActionInfo.setActions(uploadHistoryStates);
            uploadActions.add(uploadActionInfo);
        }
    }

    public List<IEntityRow> subList(List<IEntityRow> entityList, int pageIndex, int pageSize) {
        if (pageSize == 0) {
            return entityList;
        }
        int fromIndex = (pageIndex - 1) * pageSize;
        if (fromIndex > entityList.size() || fromIndex < 0) {
            return new ArrayList<IEntityRow>();
        }
        int toIndex = pageIndex * pageSize;
        if (toIndex >= entityList.size()) {
            toIndex = entityList.size();
        }
        return entityList.subList(fromIndex, toIndex);
    }

    public IEntityTable rangeQueryEntityTable(String entityId, String entityKey, FormSchemeDefine formSchemeDefine, DimensionValueSet dimensionValueSet1) {
        EntityViewDefine entityViewDefine = this.iEntityViewRunTimeController.buildEntityView(entityId);
        IEntityQuery entityQuery = this.entityDataService.newEntityQuery();
        entityQuery.setEntityView(entityViewDefine);
        entityQuery.setMasterKeys(dimensionValueSet1);
        entityQuery.sorted(true);
        ExecutorContext context = new ExecutorContext(this.runtimeController);
        context.setPeriodView(formSchemeDefine.getDateTime());
        TreeRangeQuery range = new TreeRangeQuery();
        ArrayList<String> keys = new ArrayList<String>();
        keys.add(entityKey);
        range.setParentKey(keys);
        IEntityTable iEntityTable = null;
        try {
            iEntityTable = entityQuery.executeRangeBuild((IContext)context, (RangeQuery)range);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return iEntityTable;
    }

    private IEntityTable readerEntityTable(String entityId, DimensionValueSet dimensionValueSet, FormSchemeDefine formSchemeDefine) {
        EntityViewDefine entityViewDefine = this.iEntityViewRunTimeController.buildEntityView(entityId);
        IEntityQuery entityQuery = this.entityDataService.newEntityQuery();
        entityQuery.setEntityView(entityViewDefine);
        DimensionValueSet newDimensionValueSet = new DimensionValueSet();
        newDimensionValueSet.setValue("DATATIME", dimensionValueSet.getValue("DATATIME"));
        entityQuery.setMasterKeys(newDimensionValueSet);
        ExecutorContext context = new ExecutorContext(this.runtimeController);
        context.setPeriodView(formSchemeDefine.getDateTime());
        IEntityTable iEntityTable = null;
        try {
            iEntityTable = this.dataEntityFullService.executeEntityReader(entityQuery, context, entityViewDefine, formSchemeDefine.getKey()).getEntityTable();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return iEntityTable;
    }

    public EntityData getEntityData1(String entityId, EntityViewData entityViewData, JtableContext context) {
        EntityQueryByKeyInfo entityQueryByKeyInfo = new EntityQueryByKeyInfo();
        entityQueryByKeyInfo.setEntityViewKey(entityViewData.getKey());
        entityQueryByKeyInfo.setEntityKey(entityId);
        entityQueryByKeyInfo.setContext(context);
        EntityByKeyReturnInfo entityDataByKey = this.jtableEntityService.queryEntityDataByKey(entityQueryByKeyInfo);
        EntityData entitySelf = entityDataByKey.getEntity();
        EntityQueryByViewInfo entityQueryInfo = new EntityQueryByViewInfo();
        entityQueryInfo.setEntityViewKey(entityViewData.getKey());
        entityQueryInfo.setParentKey(entityId);
        entityQueryInfo.setAllChildren(true);
        entityQueryInfo.setContext(context);
        EntityReturnInfo entityReturnInfo = this.jtableEntityService.queryEntityData(entityQueryInfo);
        entitySelf.setChildren(entityReturnInfo.getEntitys());
        entitySelf.setChildrenCount(entityReturnInfo.getEntitys().size());
        return entitySelf;
    }

    public List<ChangeEntitys> getTableCols() {
        ArrayList<ChangeEntitys> result = new ArrayList<ChangeEntitys>();
        ChangeEntitys uploadNums = new ChangeEntitys();
        uploadNums.setKey("uploadNums");
        uploadNums.setTitle("\u62a5\u9001\u6b21\u6570");
        ChangeEntitys rejectedCount = new ChangeEntitys();
        rejectedCount.setKey("rejectedCount");
        rejectedCount.setTitle("\u9000\u56de\u6b21\u6570");
        ChangeEntitys endUploadTime = new ChangeEntitys();
        endUploadTime.setKey("endUploadTime");
        endUploadTime.setTitle("\u6700\u65b0\u4e0a\u62a5\u65f6\u95f4");
        ChangeEntitys firstUploadExplain = new ChangeEntitys();
        firstUploadExplain.setKey("firstUploadExplain");
        firstUploadExplain.setTitle("\u9996\u6b21\u4e0a\u62a5\u8bf4\u660e");
        ChangeEntitys uploadExplain = new ChangeEntitys();
        uploadExplain.setKey("uploadExplain");
        uploadExplain.setTitle("\u6700\u65b0\u4e0a\u62a5\u8bf4\u660e");
        ChangeEntitys firstUploadTime = new ChangeEntitys();
        firstUploadTime.setKey("firstUploadTime");
        firstUploadTime.setTitle("\u9996\u6b21\u4e0a\u62a5\u65f6\u95f4");
        ChangeEntitys comfirmedTime = new ChangeEntitys();
        comfirmedTime.setKey("comfirmedTime");
        comfirmedTime.setTitle("\u786e\u8ba4\u65f6\u95f4");
        ChangeEntitys rejectedExplain = new ChangeEntitys();
        rejectedExplain.setKey("rejectedExplain");
        rejectedExplain.setTitle("\u9000\u56de\u8bf4\u660e");
        ChangeEntitys rejectedTime = new ChangeEntitys();
        rejectedTime.setKey("rejectTime");
        rejectedTime.setTitle("\u9000\u56de\u65f6\u95f4");
        ChangeEntitys commitedTime = new ChangeEntitys();
        commitedTime.setKey("submitedTime");
        commitedTime.setTitle("\u9001\u5ba1\u65f6\u95f4");
        ChangeEntitys retrunTime = new ChangeEntitys();
        retrunTime.setKey("returnedTime");
        retrunTime.setTitle("\u9000\u5ba1\u65f6\u95f4");
        ChangeEntitys unitCode = new ChangeEntitys();
        unitCode.setKey("unitCode");
        unitCode.setTitle("\u5355\u4f4d\u4ee3\u7801");
        result.add(retrunTime);
        result.add(commitedTime);
        result.add(rejectedTime);
        result.add(comfirmedTime);
        result.add(firstUploadTime);
        result.add(endUploadTime);
        result.add(firstUploadExplain);
        result.add(uploadExplain);
        result.add(rejectedExplain);
        result.add(uploadNums);
        result.add(rejectedCount);
        result.add(unitCode);
        return result;
    }

    public void exportUploadState(ExportExcelState exportExcelState, HttpServletResponse response, HttpServletRequest request) throws IOException {
        FormSchemeDefine formScheme = null;
        try {
            formScheme = this.runtimeView.getFormScheme(exportExcelState.getFormSchemeKey());
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
        boolean flowsType = this.workflow.isDefaultWorkflow(formScheme.getKey());
        ArrayList<UploadSumInfo> uploadSums = new ArrayList<UploadSumInfo>();
        EntityViewData dwEntity = this.jtableParamService.getDwEntity(formScheme.getKey());
        EntityViewData dataTimeEntity = this.jtableParamService.getDataTimeEntity(formScheme.getKey());
        DimensionValueSet dimensionValueSet = DimensionValueSetUtil.getDimensionValueSet(exportExcelState.getDimensionSet());
        String dwId = dimensionValueSet.getValue(dwEntity.getDimensionName()).toString();
        String period = dimensionValueSet.getValue(dataTimeEntity.getDimensionName()).toString();
        Calendar abortTime = this.deSetTimeProvide.queryAllowDelayDeadlineTime(formScheme, period);
        JtableContext context = new JtableContext();
        context.setDimensionSet(exportExcelState.getDimensionSet());
        context.setFormKey(exportExcelState.getFormKey());
        context.setFormSchemeKey(exportExcelState.getFormSchemeKey());
        context.setTaskKey(exportExcelState.getTaskKey());
        DimensionValueSet dimensionValueSet1 = new DimensionValueSet();
        dimensionValueSet1.setValue("DATATIME", (Object)exportExcelState.getDimensionSet().get("DATATIME").getValue());
        IEntityTable entitySelf = this.rangeQueryEntityTable(dwEntity.getKey(), dwId, formScheme, dimensionValueSet1);
        List<IEntityRow> childEntitys = this.getChildEntitys(entitySelf, exportExcelState.getSummaryScope(), dwId);
        ArrayList<Object> entitypage = new ArrayList();
        List<Object> acessChildEntity = new ArrayList();
        String formKey = exportExcelState.getFormKey();
        IEntityTable iEntityTable = this.getEntityDataList(exportExcelState.getFormSchemeKey(), exportExcelState.getDimensionSet());
        if (exportExcelState.getSummaryScope() > 1) {
            childEntitys = this.getChildEntitys(iEntityTable, exportExcelState.getSummaryScope(), dwId);
        }
        if (childEntitys.size() == 0) {
            IEntityRow findEntity = entitySelf.findByEntityKey(dwId);
            childEntitys.add(findEntity);
        }
        WorkFlowType queryStartType = this.dataentryFlowService.queryStartType(formScheme.getKey());
        acessChildEntity = !StringUtils.isEmpty((String)formKey) ? this.overViewBaseService.filterAuthByEntity(context, childEntitys, formKey, queryStartType) : childEntitys;
        String filter = exportExcelState.getFilter();
        if (StringUtils.isNotEmpty((String)filter)) {
            List<IEntityRow> filterEntity = new ArrayList<IEntityRow>();
            if (filter.equals(UploadStateEnum.DELAY.getCode())) {
                this.filterDelayUnitData(acessChildEntity, filterEntity, formScheme, dwEntity.getDimensionName(), formKey, abortTime);
                if (this.isDelay(abortTime)) {
                    exportExcelState.setFilter(UploadStateEnum.ORIGINAL_UPLOAD.getCode());
                    this.filterUnitExportData(acessChildEntity, dimensionValueSet, dwEntity, formScheme, exportExcelState, queryStartType, flowsType, filterEntity, filter);
                    filterEntity = this.mergeDelayUnit(filterEntity);
                    exportExcelState.setFilter(UploadStateEnum.DELAY.getCode());
                }
            } else {
                this.filterUnitExportData(acessChildEntity, dimensionValueSet, dwEntity, formScheme, exportExcelState, queryStartType, flowsType, filterEntity, filter);
            }
            entitypage = filterEntity;
        } else {
            entitypage = acessChildEntity;
        }
        boolean confirmBrforeUpload = this.overviewUtil.confirmBrforeUpload(formScheme.getKey());
        List iEntityRows = iEntityTable.getAllRows();
        for (IEntityRow entityChildData : entitypage) {
            ArrayList<UploadActionInfo> uploadActions = new ArrayList<UploadActionInfo>();
            this.getUploadAction(entityChildData, dimensionValueSet, exportExcelState, formScheme, uploadActions, dwEntity, iEntityTable);
            this.getUploadSum(entityChildData, dimensionValueSet, formKey, formScheme, uploadSums, exportExcelState.getSummaryScope(), context, uploadActions, flowsType, queryStartType, iEntityTable, exportExcelState.isLeafEntity(), exportExcelState.isFilterDiffUnit(), exportExcelState.isOnlyDirectChild(), abortTime, false, confirmBrforeUpload);
        }
        this.exportExcel(exportExcelState, uploadSums, response);
    }

    public List<IEntityRow> mergeDelayUnit(List<IEntityRow> filterEntity) {
        Map filterMap = filterEntity.stream().collect(Collectors.toMap(IEntityItem::getEntityKeyData, Function.identity(), (o, n) -> n));
        return new ArrayList<IEntityRow>(filterMap.values());
    }

    public boolean isDelay(Calendar abortTime) {
        return abortTime.getTime().compareTo(new Date()) < 0;
    }

    public void filterUnitExportData(List<IEntityRow> acessChildEntity, DimensionValueSet dimensionValueSet, EntityViewData entityViewData, FormSchemeDefine formScheme, ExportExcelState exportExcelState, WorkFlowType queryStartType, boolean flowsType, List<IEntityRow> filterEntity, String filter) {
        for (IEntityRow entity : acessChildEntity) {
            dimensionValueSet.setValue(entityViewData.getDimensionName(), (Object)entity.getEntityKeyData());
            DataEntryParam dataEntryParam = new DataEntryParam();
            dataEntryParam.setDim(dimensionValueSet);
            if (WorkFlowType.FORM.equals((Object)queryStartType)) {
                dataEntryParam.setFormKey(exportExcelState.getFormKey());
                ArrayList<String> form = new ArrayList<String>();
                form.add(exportExcelState.getFormKey());
                dataEntryParam.setFormKeys(form);
            } else if (WorkFlowType.GROUP.equals((Object)queryStartType)) {
                dataEntryParam.setGroupKey(exportExcelState.getFormKey());
                ArrayList<String> group = new ArrayList<String>();
                group.add(exportExcelState.getFormKey());
                dataEntryParam.setGroupKeys(group);
            }
            dataEntryParam.setFormSchemeKey(formScheme.getKey());
            ActionState actionStates = this.dataentryFlowService.queryState(dataEntryParam);
            ActionStateBean actionState = null;
            switch (queryStartType) {
                case FORM: {
                    actionState = actionStates.getFormState();
                    break;
                }
                case GROUP: {
                    actionState = actionStates.getGroupState();
                    break;
                }
                default: {
                    actionState = actionStates.getUnitState();
                }
            }
            if (exportExcelState.isForceUpoload()) {
                if (!actionState.isForceUpload()) continue;
                if (!flowsType && actionState != null && filter.equals(UploadStateEnum.ORIGINAL_UPLOAD.getCode()) && actionState.getCode().equals(UploadStateEnum.SUBMITED.getCode())) {
                    filterEntity.add(entity);
                }
                if ((actionState != null || !UploadStateEnum.ORIGINAL_SUBMIT.getCode().equals(filter) && !UploadStateEnum.ORIGINAL_UPLOAD.getCode().equals(filter)) && (actionState == null || !actionState.getCode().equals(filter))) continue;
                filterEntity.add(entity);
                continue;
            }
            if (!flowsType && actionState != null && filter.equals(UploadStateEnum.ORIGINAL_UPLOAD.getCode()) && actionState.getCode().equals(UploadStateEnum.SUBMITED.getCode())) {
                filterEntity.add(entity);
            }
            if ((actionState != null || !UploadStateEnum.ORIGINAL_SUBMIT.getCode().equals(filter) && !UploadStateEnum.ORIGINAL_UPLOAD.getCode().equals(filter)) && (actionState == null || !actionState.getCode().equals(filter))) continue;
            filterEntity.add(entity);
        }
    }

    public void exportAllFormState(ExportExcelState exportExcelState, HttpServletResponse response, HttpServletRequest request) throws IOException {
        List queryAllFormState;
        FormSchemeDefine formScheme = null;
        try {
            formScheme = this.runtimeView.getFormScheme(exportExcelState.getFormSchemeKey());
        }
        catch (Exception e1) {
            logger.error(e1.getMessage(), e1);
        }
        boolean flowsType = this.workflow.isDefaultWorkflow(formScheme.getKey());
        EntityViewData dwEntity = this.jtableParamService.getDwEntity(formScheme.getKey());
        EntityViewData dataTimeEntity = this.jtableParamService.getDataTimeEntity(formScheme.getKey());
        DimensionValueSet dimensionValueSet = DimensionValueSetUtil.getDimensionValueSet(exportExcelState.getDimensionSet());
        String dwId = dimensionValueSet.getValue(dwEntity.getDimensionName()).toString();
        String period = dimensionValueSet.getValue(dataTimeEntity.getDimensionName()).toString();
        JtableContext context = new JtableContext();
        context.setDimensionSet(exportExcelState.getDimensionSet());
        context.setFormKey(exportExcelState.getFormKey());
        context.setFormSchemeKey(exportExcelState.getFormSchemeKey());
        context.setTaskKey(exportExcelState.getTaskKey());
        DimensionValueSet dimensionValueSet1 = new DimensionValueSet();
        dimensionValueSet1.setValue("DATATIME", (Object)exportExcelState.getDimensionSet().get("DATATIME").getValue());
        IEntityTable iEntityTable = this.rangeQueryEntityTable(dwEntity.getKey(), dwId, formScheme, dimensionValueSet1);
        List<String> entityIds = this.getChildEntityIDs(iEntityTable, exportExcelState.getSummaryScope(), dwId);
        if (entityIds.size() == 0) {
            entityIds.add(dwId);
        }
        DimensionValueSet dimension = new DimensionValueSet(dimensionValueSet);
        dimension.setValue(dwEntity.getDimensionName(), entityIds);
        LinkedHashMap<String, UploadAllFormSumInfo> formToSum = new LinkedHashMap<String, UploadAllFormSumInfo>();
        WorkFlowType queryStartType = this.dataentryFlowService.queryStartType(formScheme.getKey());
        JtableContext jtableContext = new JtableContext();
        HashMap<String, DimensionValue> dm = new HashMap<String, DimensionValue>();
        dm.put("DATATIME", exportExcelState.getDimensionSet().get("DATATIME"));
        DimensionValue value = new DimensionValue();
        value.setName(dwEntity.getDimensionName());
        String v = "";
        for (String i : entityIds) {
            v = v + i + ";";
        }
        value.setValue(v);
        dm.put(dwEntity.getDimensionName(), value);
        jtableContext.setDimensionSet(dm);
        jtableContext.setFormKey(exportExcelState.getFormKey());
        jtableContext.setFormSchemeKey(exportExcelState.getFormSchemeKey());
        jtableContext.setTaskKey(exportExcelState.getTaskKey());
        Map<String, String> filterForm = this.overViewBaseService.getFilterForm(queryStartType, dwId, dwEntity.getDimensionName(), jtableContext);
        String formKeys = exportExcelState.getFormKey();
        if (WorkFlowType.FORM.equals((Object)queryStartType)) {
            List<FormDefine> queryAllFormDefinesByFormScheme = new ArrayList();
            if (StringUtils.isNotEmpty((String)formKeys) && !formKeys.equals("allForm")) {
                for (String formKey : formKeys.split(";")) {
                    String formKeysInFilter = filterForm.get(formKey);
                    if (!StringUtils.isNotEmpty((String)formKeysInFilter)) continue;
                    FormDefine queryFormById = this.runtimeView.queryFormById(formKey);
                    queryAllFormDefinesByFormScheme.add(queryFormById);
                }
                for (FormDefine form : queryAllFormDefinesByFormScheme) {
                    UploadAllFormSumInfo uploadSum = new UploadAllFormSumInfo();
                    uploadSum.setMasterSum(entityIds.size() > 0 ? entityIds.size() : 1);
                    formToSum.put(form.getKey(), uploadSum);
                }
            } else {
                queryAllFormDefinesByFormScheme = this.runtimeView.queryAllFormDefinesByFormScheme(formScheme.getKey());
                for (FormDefine form : queryAllFormDefinesByFormScheme) {
                    String formKeysInFilter = filterForm.get(form.getKey());
                    int length = 0;
                    if (!StringUtils.isNotEmpty((String)formKeysInFilter)) continue;
                    length = filterForm.get(form.getKey()).split(";").length;
                    UploadAllFormSumInfo uploadSum = new UploadAllFormSumInfo();
                    uploadSum.setMasterSum(length > 0 ? length : 1);
                    formToSum.put(form.getKey(), uploadSum);
                }
            }
        } else if (WorkFlowType.GROUP.equals((Object)queryStartType)) {
            List<String> authFormGroup = this.overViewBaseService.getAuthFormGroup(context);
            for (String formGroup : authFormGroup) {
                int length = filterForm.get(formGroup).split(";").length;
                UploadAllFormSumInfo uploadSum = new UploadAllFormSumInfo();
                if (filterForm.get(formGroup) != null && length > 0) {
                    uploadSum.setMasterSum(length);
                } else {
                    uploadSum.setMasterSum(1);
                }
                formToSum.put(formGroup, uploadSum);
            }
        }
        if ((queryAllFormState = this.queryUploadStateService.queryAllFormState(dimension, exportExcelState.getFormKey(), formScheme, entityIds, queryStartType, formToSum)).size() > 0) {
            ((UploadAllFormSumInfo)queryAllFormState.get(0)).setKeyTitle(this.overViewBaseService.getTitleMap(exportExcelState.getFormSchemeKey(), period, flowsType));
        }
        this.exportAllFormExcel(exportExcelState, queryAllFormState, response, queryStartType);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void exportAllFormExcel(ExportExcelState exportExcelState, List<UploadAllFormSumInfo> uploadSums, HttpServletResponse response, WorkFlowType queryStartType) throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet();
        XSSFRow row = sheet.createRow(0);
        int i = 0;
        int j = 1;
        Map stringToMap = new LinkedHashMap();
        Gson gson = new Gson();
        stringToMap = (Map)gson.fromJson(exportExcelState.getTableHeader(), LinkedHashMap.class);
        XSSFCellStyle style = workbook.createCellStyle();
        DefaultIndexedColorMap defaultIndexedColorMap = new DefaultIndexedColorMap();
        XSSFColor clr = new XSSFColor(defaultIndexedColorMap);
        clr.setARGBHex("F1F1F1");
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setFillForegroundColor(clr);
        style.setBorderRight(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBottomBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
        style.setRightBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
        XSSFCellStyle styleData = workbook.createCellStyle();
        styleData.setAlignment(HorizontalAlignment.LEFT);
        for (Object key : stringToMap.keySet()) {
            if (((String)key).equals("details")) continue;
            sheet.setColumnWidth(i, stringToMap.get(key).toString().getBytes().length * 256);
            XSSFCell createCell = row.createCell(i);
            createCell.setCellValue(stringToMap.get(key).toString());
            createCell.setCellStyle(style);
            ++i;
        }
        int maxLength = 10;
        for (UploadAllFormSumInfo uploadSum : uploadSums) {
            XSSFRow rowData = sheet.createRow(j);
            int k = 0;
            block53: for (String header : stringToMap.keySet()) {
                XSSFCell createCell = rowData.createCell(k);
                createCell.setCellStyle(styleData);
                switch (header) {
                    case "formTitle": {
                        if (WorkFlowType.FORM.equals((Object)queryStartType)) {
                            createCell.setCellValue(uploadSum.getFormTitle());
                            maxLength = uploadSum.getFormTitle().getBytes().length * 256;
                        } else if (WorkFlowType.GROUP.equals((Object)queryStartType)) {
                            createCell.setCellValue(uploadSum.getFormGroupTitle());
                            maxLength = uploadSum.getFormGroupTitle().getBytes().length * 256;
                        }
                        maxLength = maxLength > 8000 ? 8000 : maxLength;
                        sheet.setColumnWidth(0, maxLength);
                        ++k;
                        continue block53;
                    }
                    case "masterSum": {
                        createCell.setCellValue(uploadSum.getMasterSum());
                        ++k;
                        continue block53;
                    }
                    case "unSubmitedNum": {
                        createCell.setCellValue(uploadSum.getUnSubmitedNum());
                        ++k;
                        continue block53;
                    }
                    case "submitedNum": {
                        createCell.setCellValue(uploadSum.getSubmitedNum());
                        ++k;
                        continue block53;
                    }
                    case "originalNum": {
                        createCell.setCellValue(uploadSum.getOriginalNum());
                        ++k;
                        continue block53;
                    }
                    case "uploadedNum": {
                        createCell.setCellValue(uploadSum.getUploadedNum());
                        ++k;
                        continue block53;
                    }
                    case "rejectedCount": {
                        createCell.setCellValue(uploadSum.getRejectedCount() == 0 ? "\u2014\u2014\u2014\u2014" : uploadSum.getRejectedCount() + "");
                        ++k;
                        continue block53;
                    }
                    case "rejectedNum": {
                        createCell.setCellValue(uploadSum.getRejectedNum());
                        ++k;
                        continue block53;
                    }
                    case "returnedNum": {
                        createCell.setCellValue(uploadSum.getReturnedNum());
                        ++k;
                        continue block53;
                    }
                    case "submitedTime": {
                        createCell.setCellValue(uploadSum.getSubmitedTime());
                        ++k;
                        continue block53;
                    }
                    case "rejectTime": {
                        createCell.setCellValue(uploadSum.getRejectTime());
                        ++k;
                        continue block53;
                    }
                    case "returnedTime": {
                        createCell.setCellValue(uploadSum.getReturnedTime());
                        ++k;
                        continue block53;
                    }
                    case "endUploadTime": {
                        createCell.setCellValue(uploadSum.getEndUploadTime());
                        ++k;
                        continue block53;
                    }
                    case "firstUploadTime": {
                        createCell.setCellValue(uploadSum.getFirstUploadTime());
                        ++k;
                        continue block53;
                    }
                    case "comfirmedTime": {
                        createCell.setCellValue(uploadSum.getComfirmedTime());
                        ++k;
                        continue block53;
                    }
                    case "uploadNums": {
                        createCell.setCellValue(uploadSum.getUploadNums() == 0 ? "\u2014\u2014\u2014\u2014" : uploadSum.getUploadNums() + "");
                        ++k;
                        continue block53;
                    }
                    case "confirmedNum": {
                        createCell.setCellValue(uploadSum.getConfirmedNum());
                        ++k;
                        continue block53;
                    }
                    case "cancelConfirmTime": {
                        createCell.setCellValue(uploadSum.getCancelConfirmTime());
                        ++k;
                        continue block53;
                    }
                    case "submitedrate": {
                        double submitedrate = ((double)uploadSum.getSubmitedNum() + (double)uploadSum.getUploadedNum() + (double)uploadSum.getConfirmedNum()) / (double)uploadSum.getMasterSum() * 100.0;
                        int submitedger = (int)submitedrate;
                        createCell.setCellValue(submitedger + "%");
                        ++k;
                        continue block53;
                    }
                    case "uploadedper": {
                        double uploadedper = ((double)uploadSum.getUploadedNum() + (double)uploadSum.getConfirmedNum()) / (double)uploadSum.getMasterSum() * 100.0;
                        int uploadedger = (int)uploadedper;
                        createCell.setCellValue(uploadedger + "%");
                        ++k;
                        continue block53;
                    }
                    case "confirmedper": {
                        double confirmedper = (double)uploadSum.getConfirmedNum() / (double)uploadSum.getMasterSum() * 100.0;
                        int confirmedger = (int)confirmedper;
                        createCell.setCellValue(confirmedger + "%");
                        ++k;
                        continue block53;
                    }
                }
                ++k;
            }
            ++j;
        }
        workbook.setSheetName(0, "\u4e0a\u62a5\u7edf\u8ba1");
        try {
            response.setContentType("text/html;charset=UTF-8");
            ServletOutputStream outputStream = response.getOutputStream();
            workbook.write((OutputStream)outputStream);
            outputStream.flush();
            outputStream.close();
            response.flushBuffer();
        }
        catch (FileNotFoundException e) {
            logger.error(e.getMessage(), e);
        }
        finally {
            workbook.close();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void exportExcel(ExportExcelState exportExcelState, List<UploadSumInfo> uploadSums, HttpServletResponse response) throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet();
        XSSFRow row = sheet.createRow(0);
        int i = 0;
        int j = 1;
        Map stringToMap = new LinkedHashMap();
        Gson gson = new Gson();
        stringToMap = (Map)gson.fromJson(exportExcelState.getTableHeader(), LinkedHashMap.class);
        XSSFCellStyle style = workbook.createCellStyle();
        DefaultIndexedColorMap defaultIndexedColorMap = new DefaultIndexedColorMap();
        XSSFColor clr = new XSSFColor(defaultIndexedColorMap);
        clr.setARGBHex("F1F1F1");
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setFillForegroundColor(clr);
        style.setBorderRight(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBottomBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
        style.setRightBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
        XSSFCellStyle styleData = workbook.createCellStyle();
        styleData.setAlignment(HorizontalAlignment.LEFT);
        for (Object key : stringToMap.keySet()) {
            if (((String)key).equals("details")) continue;
            sheet.setColumnWidth(i, stringToMap.get(key).toString().getBytes().length * 256);
            XSSFCell createCell = row.createCell(i);
            createCell.setCellValue(stringToMap.get(key).toString());
            createCell.setCellStyle(style);
            ++i;
        }
        int maxLength = 10;
        for (UploadSumInfo uploadSum : uploadSums) {
            XSSFRow rowData = sheet.createRow(j);
            int k = 0;
            block63: for (String header : stringToMap.keySet()) {
                XSSFCell createCell = rowData.createCell(k);
                createCell.setCellStyle(styleData);
                switch (header) {
                    case "entity": {
                        createCell.setCellValue(uploadSum.getTitle());
                        maxLength = uploadSum.getTitle().getBytes().length * 256;
                        maxLength = maxLength > 8000 ? 8000 : maxLength;
                        sheet.setColumnWidth(0, maxLength);
                        ++k;
                        continue block63;
                    }
                    case "unitCode": {
                        createCell.setCellValue(uploadSum.getCode());
                        ++k;
                        continue block63;
                    }
                    case "submitedNum": {
                        createCell.setCellValue(uploadSum.getSum().getSubmitedNum());
                        ++k;
                        continue block63;
                    }
                    case "masterSum": {
                        createCell.setCellValue(uploadSum.getSum().getMasterSum());
                        ++k;
                        continue block63;
                    }
                    case "unSubmitedNum": {
                        createCell.setCellValue(uploadSum.getSum().getUnSubmitedNum());
                        ++k;
                        continue block63;
                    }
                    case "originalNum": {
                        createCell.setCellValue(uploadSum.getSum().getOriginalNum());
                        ++k;
                        continue block63;
                    }
                    case "uploadedNum": {
                        createCell.setCellValue(uploadSum.getSum().getUploadedNum());
                        ++k;
                        continue block63;
                    }
                    case "rejectedCount": {
                        createCell.setCellValue(uploadSum.getSum().getRejectedCount() == 0 ? "\u2014\u2014\u2014\u2014" : uploadSum.getSum().getRejectedCount() + "");
                        ++k;
                        continue block63;
                    }
                    case "firstUploadExplain": {
                        createCell.setCellValue(uploadSum.getSum().getFirstUploadExplain());
                        ++k;
                        continue block63;
                    }
                    case "uploadExplain": {
                        createCell.setCellValue(uploadSum.getSum().getUploadExplain());
                        ++k;
                        continue block63;
                    }
                    case "rejectedExplain": {
                        createCell.setCellValue(uploadSum.getSum().getRejectedExplain());
                        ++k;
                        continue block63;
                    }
                    case "rejectedNum": {
                        createCell.setCellValue(uploadSum.getSum().getRejectedNum());
                        ++k;
                        continue block63;
                    }
                    case "returnedNum": {
                        createCell.setCellValue(uploadSum.getSum().getReturnedNum());
                        ++k;
                        continue block63;
                    }
                    case "submitedTime": {
                        createCell.setCellValue(uploadSum.getSum().getSubmitedTime());
                        ++k;
                        continue block63;
                    }
                    case "rejectTime": {
                        createCell.setCellValue(uploadSum.getSum().getRejectTime());
                        ++k;
                        continue block63;
                    }
                    case "returnedTime": {
                        createCell.setCellValue(uploadSum.getSum().getReturnedTime());
                        ++k;
                        continue block63;
                    }
                    case "endUploadTime": {
                        createCell.setCellValue(uploadSum.getSum().getEndUploadTime());
                        ++k;
                        continue block63;
                    }
                    case "firstUploadTime": {
                        createCell.setCellValue(uploadSum.getSum().getFirstUploadTime());
                        ++k;
                        continue block63;
                    }
                    case "comfirmedTime": {
                        createCell.setCellValue(uploadSum.getSum().getComfirmedTime());
                        ++k;
                        continue block63;
                    }
                    case "uploadNums": {
                        createCell.setCellValue(uploadSum.getSum().getUploadNums() == 0 ? "\u2014\u2014\u2014\u2014" : uploadSum.getSum().getUploadNums() + "");
                        ++k;
                        continue block63;
                    }
                    case "confirmedNum": {
                        createCell.setCellValue(uploadSum.getSum().getConfirmedNum());
                        ++k;
                        continue block63;
                    }
                    case "cancelConfirmTime": {
                        createCell.setCellValue(uploadSum.getSum().getCancelConfirmTime());
                        ++k;
                        continue block63;
                    }
                    case "submitedrate": {
                        double submitedrate = ((double)uploadSum.getSum().getSubmitedNum() + (double)uploadSum.getSum().getUploadedNum() + (double)uploadSum.getSum().getConfirmedNum()) / (double)uploadSum.getSum().getMasterSum() * 100.0;
                        int submitedger = (int)submitedrate;
                        createCell.setCellValue(submitedger + "%");
                        ++k;
                        continue block63;
                    }
                    case "uploadedper": {
                        double uploadedper = ((double)uploadSum.getSum().getUploadedNum() + (double)uploadSum.getSum().getConfirmedNum()) / (double)uploadSum.getSum().getMasterSum() * 100.0;
                        int uploadedger = (int)uploadedper;
                        createCell.setCellValue(uploadedger + "%");
                        ++k;
                        continue block63;
                    }
                    case "confirmedper": {
                        double confirmedper = (double)uploadSum.getSum().getConfirmedNum() / (double)uploadSum.getSum().getMasterSum() * 100.0;
                        int confirmedger = (int)confirmedper;
                        createCell.setCellValue(confirmedger + "%");
                        ++k;
                        continue block63;
                    }
                    case "delayRate": {
                        double delayRate = (double)uploadSum.getSum().getDelayNum() / (double)uploadSum.getSum().getMasterSum() * 100.0;
                        int delayRateInt = (int)delayRate;
                        createCell.setCellValue(delayRateInt + "%");
                        ++k;
                        continue block63;
                    }
                }
                ++k;
            }
            ++j;
        }
        workbook.setSheetName(0, "\u4e0a\u62a5\u7edf\u8ba1");
        try {
            response.setContentType("text/html;charset=UTF-8");
            ServletOutputStream outputStream = response.getOutputStream();
            workbook.write((OutputStream)outputStream);
            outputStream.flush();
            outputStream.close();
            response.flushBuffer();
        }
        catch (FileNotFoundException e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
        finally {
            workbook.close();
        }
    }

    public WorkFlowType queryWorkType(BatchQueryWorkFlowType batchQueryWorkFlowType) {
        return this.dataentryFlowService.queryStartType(batchQueryWorkFlowType.getFormSchemeKey());
    }

    public void exportUploadState2(ExportExcelState exportExcelState, HttpServletResponse response, HttpServletRequest request) throws Exception {
        ArrayList<UploadStatusDetail> uploadDeatils = new ArrayList<UploadStatusDetail>();
        ArrayList<String> formGroupNames = new ArrayList<String>();
        FormSchemeDefine formScheme = this.runtimeView.getFormScheme(exportExcelState.getFormSchemeKey());
        WorkFlowType wordFlowType = formScheme.getFlowsSetting().getWordFlowType();
        this.setDefaultValue(exportExcelState.getDimensionSet(), wordFlowType);
        if (wordFlowType.equals((Object)WorkFlowType.GROUP)) {
            String groupKey = exportExcelState.getFormKey();
            if (groupKey.equals("allForm")) {
                JtableContext jtableContext = new JtableContext();
                jtableContext.setFormSchemeKey(exportExcelState.getFormSchemeKey());
                jtableContext.setDimensionSet(exportExcelState.getDimensionSet());
                List<String> formGroupList = this.overViewBaseService.getAuthFormGroup(jtableContext);
                for (String key : formGroupList) {
                    exportExcelState.setFormKey(key);
                    UploadStatusDetail uploadDeatil = this.batchExportUploadService.getUploadDeatils(exportExcelState);
                    uploadDeatils.add(uploadDeatil);
                    FormGroupDefine formGroupDefine = this.runtimeFormGroupService.queryFormGroup(key);
                    formGroupNames.add(formGroupDefine.getTitle());
                }
            } else {
                FormGroupDefine group = this.runtimeFormGroupService.queryFormGroup(groupKey);
                formGroupNames.add(group.getTitle());
                exportExcelState.setFormKey(group.getKey());
                UploadStatusDetail uploadDeatil = this.batchExportUploadService.getUploadDeatils(exportExcelState);
                uploadDeatils.add(uploadDeatil);
            }
            this.exportDetail(exportExcelState, uploadDeatils, formGroupNames, response);
        } else if (wordFlowType.equals((Object)WorkFlowType.FORM)) {
            String formKey = exportExcelState.getFormKey();
            List<FormDefine> formDefines = null;
            if (formKey.equals("allForm")) {
                formDefines = this.runtimeView.queryAllFormDefinesByFormScheme(exportExcelState.getFormSchemeKey());
            } else {
                String[] forms;
                formDefines = new ArrayList();
                for (String fm : forms = formKey.split(";")) {
                    FormDefine form = this.runtimeView.queryFormById(fm);
                    formDefines.add(form);
                }
            }
            int index = 0;
            EntityViewData dwEntity = this.jtableParamService.getDwEntity(formScheme.getKey());
            EntityViewData dataTimeEntity = this.jtableParamService.getDataTimeEntity(formScheme.getKey());
            DimensionValueSet dimensionValueSet = DimensionValueSetUtil.getDimensionValueSet(exportExcelState.getDimensionSet());
            String dwId = dimensionValueSet.getValue(dwEntity.getDimensionName()).toString();
            String period = dimensionValueSet.getValue(dataTimeEntity.getDimensionName()).toString();
            JtableContext context = new JtableContext();
            context.setDimensionSet(exportExcelState.getDimensionSet());
            context.setFormKey(exportExcelState.getFormKey());
            context.setFormSchemeKey(exportExcelState.getFormSchemeKey());
            context.setTaskKey(exportExcelState.getTaskKey());
            DimensionValueSet dimensionValueSet1 = new DimensionValueSet();
            dimensionValueSet1.setValue("DATATIME", (Object)exportExcelState.getDimensionSet().get("DATATIME").getValue());
            IEntityTable iEntityTable = this.rangeQueryEntityTable(dwEntity.getKey(), dwId, formScheme, dimensionValueSet1);
            List<String> entityIds = this.getChildEntityIDs(iEntityTable, exportExcelState.getSummaryScope(), dwId);
            if (entityIds.size() == 0) {
                entityIds.add(dwId);
            }
            DimensionValueSet dimension = new DimensionValueSet(dimensionValueSet);
            dimension.setValue(dwEntity.getDimensionName(), entityIds);
            LinkedHashMap formToSum = new LinkedHashMap();
            WorkFlowType queryStartType = this.dataentryFlowService.queryStartType(formScheme.getKey());
            JtableContext jtableContext = new JtableContext();
            HashMap<String, DimensionValue> dm = new HashMap<String, DimensionValue>();
            dm.put("DATATIME", exportExcelState.getDimensionSet().get("DATATIME"));
            DimensionValue value = new DimensionValue();
            value.setName(dwEntity.getDimensionName());
            String v = "";
            for (String i : entityIds) {
                v = v + i + ";";
            }
            value.setValue(v);
            dm.put(dwEntity.getDimensionName(), value);
            jtableContext.setDimensionSet(dm);
            jtableContext.setFormKey(exportExcelState.getFormKey());
            jtableContext.setFormSchemeKey(exportExcelState.getFormSchemeKey());
            jtableContext.setTaskKey(exportExcelState.getTaskKey());
            Map<String, String> filterForm = this.overViewBaseService.getFilterForm(queryStartType, dwId, dwEntity.getDimensionName(), jtableContext);
            for (FormDefine formDefine : formDefines) {
                String formKeysInFilter = filterForm.get(formDefine.getKey());
                if (!StringUtils.isNotEmpty((String)formKeysInFilter)) continue;
                exportExcelState.setFormKey(formDefine.getKey());
                UploadStatusDetail uploadDeatil = this.batchExportUploadService.getUploadDeatils(exportExcelState);
                uploadDeatils.add(uploadDeatil);
                if (formDefine.getTitle().length() >= 20 || formGroupNames.indexOf(formDefine.getTitle()) != -1) {
                    formGroupNames.add("(" + index + ")" + formDefine.getTitle());
                } else {
                    formGroupNames.add(formDefine.getTitle());
                }
                ++index;
            }
            this.exportDetail(exportExcelState, uploadDeatils, formGroupNames, response);
        } else {
            UploadStatusDetail uploadDeatil = this.batchExportUploadService.getUploadDeatils(exportExcelState);
            uploadDeatils.add(uploadDeatil);
            formGroupNames.add("\u4e3b\u4f53");
            this.exportDetail(exportExcelState, uploadDeatils, formGroupNames, response);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void exportDetail(ExportExcelState exportExcelState, List<UploadStatusDetail> uploadDeatils, List<String> formGroupNames, HttpServletResponse response) throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook();
        Boolean exportUnitCode = false;
        Map stringToMap = new LinkedHashMap();
        Gson gson = new Gson();
        stringToMap = (Map)gson.fromJson(exportExcelState.getTableHeader(), LinkedHashMap.class);
        if (stringToMap.containsKey("unitCode")) {
            exportUnitCode = true;
        }
        XSSFCellStyle style = workbook.createCellStyle();
        DefaultIndexedColorMap defaultIndexedColorMap = new DefaultIndexedColorMap();
        XSSFColor clr = new XSSFColor(defaultIndexedColorMap);
        clr.setARGBHex("F1F1F1");
        style.setFillForegroundColor(clr);
        style.setBorderRight(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontName("\u9ed1\u4f53");
        style.setFont(font);
        XSSFCellStyle styleData = workbook.createCellStyle();
        styleData.setAlignment(HorizontalAlignment.LEFT);
        styleData.setFillForegroundColor(clr);
        styleData.setBorderRight(BorderStyle.THIN);
        styleData.setBorderBottom(BorderStyle.THIN);
        styleData.setBorderLeft(BorderStyle.THIN);
        styleData.setBorderTop(BorderStyle.THIN);
        for (int i = 0; i < uploadDeatils.size(); ++i) {
            this.buildSheet(workbook, style, styleData, uploadDeatils.get(i), formGroupNames.get(i), exportUnitCode);
        }
        try {
            response.setContentType("text/html;charset=UTF-8");
            ServletOutputStream outputStream = response.getOutputStream();
            workbook.write((OutputStream)outputStream);
            outputStream.flush();
            outputStream.close();
            response.flushBuffer();
        }
        catch (FileNotFoundException e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
        finally {
            workbook.close();
        }
    }

    private void buildSheet(XSSFWorkbook workbook, XSSFCellStyle style, XSSFCellStyle styleData, UploadStatusDetail uploadDeatils, String formGroupName, Boolean exportUnitCode) {
        String suffix = "";
        if (!formGroupName.equals("\u4e3b\u4f53")) {
            suffix = "-" + formGroupName;
        }
        XSSFSheet sheet = workbook.createSheet("\u4e0a\u62a5\u60c5\u51b5\u6c47\u603b\u4fe1\u606f" + suffix);
        XSSFRow row = sheet.createRow(0);
        XSSFRow row2 = sheet.createRow(1);
        String[] columns = new String[]{"\u72b6\u6001", "\u5e94\u62a5", "\u672a\u4e0a\u62a5", "\u5df2\u4e0a\u62a5", "\u5df2\u9001\u5ba1", "\u5df2\u9000\u5ba1", "\u5df2\u786e\u8ba4", "\u5df2\u9000\u56de"};
        int[] stateValues = new int[]{0, uploadDeatils.getUnitCount(), uploadDeatils.getOriginalNum(), uploadDeatils.getUploadedNum(), uploadDeatils.getSubmitedNum(), uploadDeatils.getReturnedNum(), uploadDeatils.getConfirmedNum(), uploadDeatils.getRejectedNum()};
        int v = 0;
        for (int i = 0; i < columns.length; ++i) {
            XSSFCell createCell2;
            XSSFCell createCell;
            if (i != 0 && stateValues[i] == 0) continue;
            if (i == 0) {
                sheet.autoSizeColumn(v);
                sheet.setColumnWidth(v, sheet.getColumnWidth(v) * 17 / 10);
                createCell = row.createCell(v);
                createCell.setCellValue(columns[i].toString());
                createCell.setCellStyle(style);
                createCell2 = row2.createCell(v);
                createCell2.setCellStyle(styleData);
                createCell2.setCellValue("\u5355\u4f4d\u6237\u6570");
                ++v;
                continue;
            }
            sheet.setColumnWidth(v, columns[i].getBytes().length * 256);
            createCell = row.createCell(v);
            createCell.setCellValue(columns[i]);
            createCell.setCellStyle(style);
            createCell2 = row2.createCell(v);
            createCell2.setCellStyle(styleData);
            createCell2.setCellValue(stateValues[i]);
            ++v;
        }
        List uploadRecordDetails = uploadDeatils.getUploadRecordDetail();
        XSSFSheet sheetDetail = workbook.createSheet("\u4e0a\u62a5\u60c5\u51b5\u660e\u7ec6" + suffix);
        String[] headerColumns = exportUnitCode != false ? new String[]{"\u5355\u4f4d\u540d\u79f0", "\u5355\u4f4d\u4ee3\u7801", "\u72b6\u6001", "\u4e0a\u62a5\u6b21\u6570", "\u9000\u56de\u6b21\u6570", "\u6700\u521d\u4e0a\u62a5\u65f6\u95f4", "\u6700\u521d\u4e0a\u62a5\u4eba", "\u6700\u540e\u4e00\u6b21\u4e0a\u62a5\u65f6\u95f4", "\u6700\u540e\u4e0a\u62a5\u4eba", "\u6700\u521d\u9000\u56de\u65f6", "\u6700\u521d\u9000\u56de\u4eba", "\u6700\u540e\u4e00\u6b21\u9000\u56de\u65f6\u95f4", "\u6700\u540e\u9000\u56de\u4eba"} : new String[]{"\u5355\u4f4d\u540d\u79f0", "\u72b6\u6001", "\u4e0a\u62a5\u6b21\u6570", "\u9000\u56de\u6b21\u6570", "\u6700\u521d\u4e0a\u62a5\u65f6\u95f4", "\u6700\u521d\u4e0a\u62a5\u4eba", "\u6700\u540e\u4e00\u6b21\u4e0a\u62a5\u65f6\u95f4", "\u6700\u540e\u4e0a\u62a5\u4eba", "\u6700\u521d\u9000\u56de\u65f6", "\u6700\u521d\u9000\u56de\u4eba", "\u6700\u540e\u4e00\u6b21\u9000\u56de\u65f6\u95f4", "\u6700\u540e\u9000\u56de\u4eba"};
        XSSFRow headerRow = sheetDetail.createRow(0);
        for (int i = 0; i < headerColumns.length; ++i) {
            sheet.autoSizeColumn(i);
            sheet.setColumnWidth(i, headerColumns[i].getBytes().length * 256 + 2);
            XSSFCell createCell = headerRow.createCell(i);
            createCell.setCellValue(headerColumns[i]);
            createCell.setCellStyle(style);
        }
        int dataRowIndex = 1;
        for (UploadRecordDetail rd : uploadRecordDetails) {
            XSSFRow dataRow = sheetDetail.createRow(dataRowIndex);
            String[] detailValues = exportUnitCode != false ? new String[]{rd.getUnit(), rd.getUnitKey(), rd.getState(), String.valueOf(rd.getUploadCount()), String.valueOf(rd.getRejectCount()), rd.getInitialUpdateTime(), rd.getInitialUpdateProcessor(), rd.getLastUpdateTime(), rd.getLastUpdateProcessor(), rd.getInitialRejectTime(), rd.getInitialRejectProcessor(), rd.getLastRejectTime(), rd.getLastRejectProcessor()} : new String[]{rd.getUnit(), rd.getState(), String.valueOf(rd.getUploadCount()), String.valueOf(rd.getRejectCount()), rd.getInitialUpdateTime(), rd.getInitialUpdateProcessor(), rd.getLastUpdateTime(), rd.getLastUpdateProcessor(), rd.getInitialRejectTime(), rd.getInitialRejectProcessor(), rd.getLastRejectTime(), rd.getLastRejectProcessor()};
            for (int i = 0; i < headerColumns.length; ++i) {
                XSSFCell createCell = dataRow.createCell(i);
                createCell.setCellValue(detailValues[i]);
                createCell.setCellStyle(styleData);
            }
            ++dataRowIndex;
        }
    }

    public List<UploadRecordDetail> queryUploadState(ExportExcelState exportExcelState, HttpServletResponse response, HttpServletRequest request) throws Exception {
        ArrayList<UploadStatusDetail> uploadDeatils = new ArrayList<UploadStatusDetail>();
        ArrayList<String> formGroupNames = new ArrayList<String>();
        FormSchemeDefine formScheme = this.runtimeView.getFormScheme(exportExcelState.getFormSchemeKey());
        WorkFlowType wordFlowType = formScheme.getFlowsSetting().getWordFlowType();
        if (wordFlowType.equals((Object)WorkFlowType.GROUP)) {
            String groupKey = exportExcelState.getFormKey();
            if (groupKey.equals("allForm")) {
                JtableContext jtableContext = new JtableContext();
                jtableContext.setFormSchemeKey(exportExcelState.getFormSchemeKey());
                jtableContext.setDimensionSet(exportExcelState.getDimensionSet());
                List<String> formGroupList = this.overViewBaseService.getAuthFormGroup(jtableContext);
                for (String key : formGroupList) {
                    exportExcelState.setFormKey(key);
                    UploadStatusDetail uploadDeatil = this.batchExportUploadService.getUploadDeatils(exportExcelState);
                    uploadDeatils.add(uploadDeatil);
                    FormGroupDefine formGroupDefine = this.runtimeFormGroupService.queryFormGroup(key);
                    formGroupNames.add(formGroupDefine.getTitle());
                }
            } else {
                FormGroupDefine group = this.runtimeFormGroupService.queryFormGroup(groupKey);
                formGroupNames.add(group.getTitle());
                exportExcelState.setFormKey(group.getKey());
                UploadStatusDetail uploadDeatil = this.batchExportUploadService.getUploadDeatils(exportExcelState);
                uploadDeatils.add(uploadDeatil);
            }
        } else if (wordFlowType.equals((Object)WorkFlowType.FORM)) {
            String formKey = exportExcelState.getFormKey();
            List<FormDefine> formDefines = null;
            if (formKey.equals("allForm")) {
                formDefines = this.runtimeView.queryAllFormDefinesByFormScheme(exportExcelState.getFormSchemeKey());
            } else {
                formDefines = new ArrayList();
                String[] forms = formKey.split(";");
                for (String fm : forms) {
                    FormDefine form = this.runtimeView.queryFormById(fm);
                    formDefines.add(form);
                }
            }
            int index = 0;
            for (FormDefine formDefine : formDefines) {
                exportExcelState.setFormKey(formDefine.getKey());
                UploadStatusDetail uploadDeatil = this.batchExportUploadService.getUploadDeatils(exportExcelState);
                uploadDeatils.add(uploadDeatil);
                if (formDefine.getTitle().length() >= 20 || formGroupNames.indexOf(formDefine.getTitle()) != -1) {
                    formGroupNames.add("(" + index + ")" + formDefine.getTitle());
                } else {
                    formGroupNames.add(formDefine.getTitle());
                }
                ++index;
            }
        } else {
            UploadStatusDetail uploadDeatil = this.batchExportUploadService.getUploadDeatils(exportExcelState);
            uploadDeatils.add(uploadDeatil);
            formGroupNames.add("\u4e3b\u4f53");
        }
        ArrayList<UploadRecordDetail> uploadRecordDetails = new ArrayList<UploadRecordDetail>();
        for (UploadStatusDetail uploadStatusDetail : uploadDeatils) {
            uploadRecordDetails.addAll(uploadStatusDetail.getUploadRecordDetail());
        }
        return uploadRecordDetails;
    }

    public Map<String, String> getWorkFlowActionTitle(BatchQueryUpload param) {
        FormSchemeDefine formScheme = null;
        try {
            formScheme = this.runtimeView.getFormScheme(param.getFormSchemeKey());
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
        if (formScheme != null) {
            boolean defaultWorkflow = this.workflow.isDefaultWorkflow(formScheme.getKey());
            EntityViewData dataTimeEntity = this.jtableParamService.getDataTimeEntity(formScheme.getKey());
            DimensionValueSet dimensionValueSet = DimensionValueSetUtil.getDimensionValueSet(param.getDimensionSet());
            String period = dimensionValueSet.getValue(dataTimeEntity.getDimensionName()).toString();
            return this.overViewBaseService.getActionTitleMap(param.getFormSchemeKey(), period, defaultWorkflow);
        }
        return null;
    }

    private List<ITree<TaskTreeObj>> getTaskTree() {
        List taskGroupDefines = this.runTimeViewController.listAllTaskGroup();
        ArrayList<ITree<TaskTreeObj>> tree_Task = new ArrayList<ITree<TaskTreeObj>>();
        ArrayList<String> taskKeysByGroupKey = new ArrayList<String>();
        if (taskGroupDefines.size() > 0) {
            for (TaskGroupDefine taskGroupDefine : taskGroupDefines) {
                if (!StringUtils.isEmpty((String)taskGroupDefine.getParentKey())) continue;
                tree_Task.add(this.getTreeNode(taskGroupDefine, false, false));
                List taskDefines = this.runTimeViewController.listTaskByTaskGroup(taskGroupDefine.getKey());
                if (taskDefines == null || taskDefines.size() <= 0) continue;
                for (TaskDefine taskDefine : taskDefines) {
                    taskKeysByGroupKey.add(taskDefine.getKey());
                }
            }
        }
        List taskDefines = this.runTimeViewController.listAllTask();
        if (taskKeysByGroupKey.size() > 0) {
            taskDefines = taskDefines.stream().filter(e -> !taskKeysByGroupKey.contains(e.getKey())).collect(Collectors.toList());
        }
        if (taskDefines != null && taskDefines.size() > 0) {
            for (TaskDefine taskDefine : taskDefines) {
                tree_Task.add(this.getTreeNode(taskDefine, false, false));
            }
        }
        return tree_Task;
    }

    private ITree<TaskTreeObj> getTreeNode(TaskGroupDefine taskGroupDefine, boolean isChecked, boolean isExpanded) {
        ITree node = new ITree((INode)new TaskTreeObj(taskGroupDefine));
        node.setLeaf(false);
        if (isChecked) {
            node.setChecked(true);
        }
        if (isExpanded) {
            node.setExpanded(true);
        }
        this.groupChildren((ITree<TaskTreeObj>)node, taskGroupDefine.getKey());
        return node;
    }

    private ITree<TaskTreeObj> getTreeNode(TaskDefine taskDefine, boolean isChecked, boolean isExpanded) {
        ITree node = new ITree((INode)new TaskTreeObj(taskDefine));
        node.setLeaf(false);
        if (isChecked) {
            node.setChecked(true);
        }
        if (isExpanded) {
            node.setExpanded(true);
        }
        return node;
    }

    private void groupChildren(ITree<TaskTreeObj> node, String taskGroup) {
        List taskGroupDefines = this.runTimeViewController.listTaskGroupByParentGroup(taskGroup);
        if (taskGroupDefines != null && taskGroupDefines.size() > 0) {
            for (TaskGroupDefine taskGroupDefine : taskGroupDefines) {
                ITree groupNode = new ITree((INode)new TaskTreeObj(taskGroupDefine));
                node.setLeaf(false);
                node.setChecked(false);
                node.setExpanded(false);
                node.appendChild(groupNode);
                this.taskChildren(node, taskGroup);
                this.groupChildren(node, taskGroupDefine.getKey());
            }
        } else {
            ITree<TaskTreeObj> childrenNode;
            List children = node.getChildren();
            if (children != null && children.size() > 0 && (childrenNode = this.getChildrenNode(children, taskGroup)) != null) {
                node = childrenNode;
                taskGroup = childrenNode.getKey();
            }
            this.taskChildren(node, taskGroup);
        }
    }

    private ITree<TaskTreeObj> getChildrenNode(List<ITree<TaskTreeObj>> children, String taskGroupKey) {
        if (children != null) {
            for (ITree<TaskTreeObj> child : children) {
                if (taskGroupKey.equals(child.getKey())) {
                    return child;
                }
                List children1 = child.getChildren();
                this.getChildrenNode(children1, child.getKey());
            }
        }
        return null;
    }

    private void taskChildren(ITree<TaskTreeObj> node, String taskGroup) {
        List taskDefines = this.runTimeViewController.listTaskByTaskGroup(taskGroup);
        if (taskDefines != null && taskDefines.size() > 0) {
            for (TaskDefine taskDefine : taskDefines) {
                ITree itree = new ITree();
                itree.setKey(taskDefine.getKey());
                itree.setTitle(taskDefine.getTitle());
                itree.setCode(taskDefine.getTaskCode());
                itree.setChildren(null);
                node.appendChild(itree);
            }
        }
    }

    public boolean isTaskGroup(String selectKey) {
        Optional<TaskGroupDefine> taskGroupDefine;
        List taskGroupDefines = this.runTimeViewController.listAllTaskGroup();
        return taskGroupDefines != null && taskGroupDefines.size() > 0 && (taskGroupDefine = taskGroupDefines.stream().filter(e -> e.getKey().equals(selectKey)).findFirst()).isPresent();
    }

    public List<TaskDefine> getTasksByGroupKey(String groupKey) {
        return this.runTimeViewController.listTaskByTaskGroup(groupKey);
    }
}

