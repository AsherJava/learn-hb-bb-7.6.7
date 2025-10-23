/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.core.exception.BusinessException
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.np.definition.facade.IMetaGroup
 *  com.jiuqi.np.user.User
 *  com.jiuqi.np.user.service.UserManagerService
 *  com.jiuqi.nr.bpm.common.UploadAllFormSumInfo
 *  com.jiuqi.nr.bpm.common.UploadRecordNew
 *  com.jiuqi.nr.bpm.common.UploadSumNew
 *  com.jiuqi.nr.bpm.custom.bean.WorkFlowDefine
 *  com.jiuqi.nr.bpm.custom.bean.WorkFlowLine
 *  com.jiuqi.nr.bpm.custom.service.CustomWorkFolwService
 *  com.jiuqi.nr.bpm.de.dataflow.constont.UploadStateEnum
 *  com.jiuqi.nr.bpm.de.dataflow.service.IWorkflow
 *  com.jiuqi.nr.bpm.de.dataflow.tree.TreeWorkflow
 *  com.jiuqi.nr.bpm.setting.bean.WorkflowSettingDefine
 *  com.jiuqi.nr.bpm.setting.pojo.ShowNodeResult
 *  com.jiuqi.nr.bpm.setting.pojo.ShowResult
 *  com.jiuqi.nr.bpm.setting.service.WorkflowSettingService
 *  com.jiuqi.nr.bpm.upload.UploadState
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.context.cxt.DsContextHolder
 *  com.jiuqi.nr.dataentry.bean.QueryUploadStateInfo
 *  com.jiuqi.nr.dataentry.internal.overview.OverviewUtil
 *  com.jiuqi.nr.dataentry.internal.overview.UploadOverviewConverter
 *  com.jiuqi.nr.dataentry.paramInfo.ActionInfo
 *  com.jiuqi.nr.dataentry.paramInfo.BatchQueryUpload
 *  com.jiuqi.nr.dataentry.paramInfo.ExportExcelState
 *  com.jiuqi.nr.dataentry.paramInfo.UploadActionInfo
 *  com.jiuqi.nr.dataentry.paramInfo.UploadSumInfo
 *  com.jiuqi.nr.datascheme.api.DataDimension
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.definition.api.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormGroupDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.entity.engine.executors.ExecutorContext
 *  com.jiuqi.nr.entity.engine.intf.IEntityItem
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.engine.setting.AuthorityType
 *  com.jiuqi.nr.entity.engine.var.RangeQuery
 *  com.jiuqi.nr.entity.engine.var.TreeRangeQuery
 *  com.jiuqi.nr.entity.service.IEntityDataService
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.jtable.params.base.EntityViewData
 *  com.jiuqi.nr.jtable.service.IJtableParamService
 *  com.jiuqi.nr.jtable.util.DimensionValueSetUtil
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.nr.workflow2.engine.core.exception.ErrorCode
 *  com.jiuqi.nr.workflow2.engine.core.exception.InstanceNotFoundException
 *  com.jiuqi.nr.workflow2.engine.core.exception.UserTaskNotFoundException
 *  com.jiuqi.nr.workflow2.engine.core.process.defintion.IProcessStatus
 *  com.jiuqi.nr.workflow2.engine.core.process.defintion.IUserAction
 *  com.jiuqi.nr.workflow2.engine.core.process.defintion.IUserTask
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBizObjectOperateResult
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKey
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKeyCollection
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObject
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IOperateResult
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IProcessInstance
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IProcessOperation
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IProcessStatusWithOperation
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.common.BizObjectOperateResult
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.common.FormGroupObject
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.common.FormObject
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.common.IDimensionObjectMapping
 *  com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsService
 *  com.jiuqi.nr.workflow2.engine.core.settings.enumeration.WorkflowObjectType
 *  com.jiuqi.nr.workflow2.engine.dflt.process.design.DefaultProcessConfig
 *  com.jiuqi.nr.workflow2.engine.dflt.process.design.DefaultProcessDesignService
 *  com.jiuqi.nr.workflow2.engine.dflt.process.design.config.action.ActionInfo
 *  com.jiuqi.nr.workflow2.engine.dflt.process.design.config.enumeration.WorkflowDefineTemplate
 *  com.jiuqi.nr.workflow2.engine.dflt.process.design.config.property.AuditProperty
 *  com.jiuqi.nr.workflow2.service.IProcessMetaDataService
 *  com.jiuqi.nr.workflow2.service.IProcessQueryService
 *  com.jiuqi.nr.workflow2.service.dimension.EProcessDimensionName
 *  com.jiuqi.nr.workflow2.service.enumeration.IProcessFormRejectAttrKeys
 *  com.jiuqi.nr.workflow2.service.helper.IReportDimensionHelper
 *  com.jiuqi.nr.workflow2.service.helper.ProcessDimensionsBuilder
 *  com.jiuqi.nr.workflow2.service.para.EProcessRangeDimType
 *  com.jiuqi.nr.workflow2.service.para.IProcessRunPara
 *  com.jiuqi.nr.workflow2.service.para.ProcessBatchRunPara
 *  com.jiuqi.nr.workflow2.service.para.ProcessOneDim
 *  com.jiuqi.nr.workflow2.service.para.ProcessOneRunPara
 *  com.jiuqi.nr.workflow2.service.para.ProcessRangeDims
 *  com.jiuqi.nr.workflow2.service.para.ProcessRunPara
 *  javax.servlet.ServletOutputStream
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 *  javax.validation.Valid
 *  org.json.JSONObject
 *  org.springframework.web.bind.annotation.RequestBody
 */
package com.jiuqi.nr.workflow2.converter.workflow.overview;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.core.exception.BusinessException;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.np.definition.facade.IMetaGroup;
import com.jiuqi.np.user.User;
import com.jiuqi.np.user.service.UserManagerService;
import com.jiuqi.nr.bpm.common.UploadAllFormSumInfo;
import com.jiuqi.nr.bpm.common.UploadRecordNew;
import com.jiuqi.nr.bpm.common.UploadSumNew;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowDefine;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowLine;
import com.jiuqi.nr.bpm.custom.service.CustomWorkFolwService;
import com.jiuqi.nr.bpm.de.dataflow.constont.UploadStateEnum;
import com.jiuqi.nr.bpm.de.dataflow.service.IWorkflow;
import com.jiuqi.nr.bpm.de.dataflow.tree.TreeWorkflow;
import com.jiuqi.nr.bpm.setting.bean.WorkflowSettingDefine;
import com.jiuqi.nr.bpm.setting.pojo.ShowNodeResult;
import com.jiuqi.nr.bpm.setting.pojo.ShowResult;
import com.jiuqi.nr.bpm.setting.service.WorkflowSettingService;
import com.jiuqi.nr.bpm.upload.UploadState;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.context.cxt.DsContextHolder;
import com.jiuqi.nr.dataentry.bean.QueryUploadStateInfo;
import com.jiuqi.nr.dataentry.internal.overview.OverviewUtil;
import com.jiuqi.nr.dataentry.internal.overview.UploadOverviewConverter;
import com.jiuqi.nr.dataentry.paramInfo.ActionInfo;
import com.jiuqi.nr.dataentry.paramInfo.BatchQueryUpload;
import com.jiuqi.nr.dataentry.paramInfo.ExportExcelState;
import com.jiuqi.nr.dataentry.paramInfo.UploadActionInfo;
import com.jiuqi.nr.dataentry.paramInfo.UploadSumInfo;
import com.jiuqi.nr.datascheme.api.DataDimension;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.definition.api.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormGroupDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.entity.engine.executors.ExecutorContext;
import com.jiuqi.nr.entity.engine.intf.IEntityItem;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.engine.setting.AuthorityType;
import com.jiuqi.nr.entity.engine.var.RangeQuery;
import com.jiuqi.nr.entity.engine.var.TreeRangeQuery;
import com.jiuqi.nr.entity.service.IEntityDataService;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.jtable.params.base.EntityViewData;
import com.jiuqi.nr.jtable.service.IJtableParamService;
import com.jiuqi.nr.jtable.util.DimensionValueSetUtil;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nr.workflow2.converter.dataentry.track.WorkflowTrackConverter;
import com.jiuqi.nr.workflow2.converter.unittree.DataEntryUnitTreeStateConverter;
import com.jiuqi.nr.workflow2.engine.core.exception.ErrorCode;
import com.jiuqi.nr.workflow2.engine.core.exception.InstanceNotFoundException;
import com.jiuqi.nr.workflow2.engine.core.exception.UserTaskNotFoundException;
import com.jiuqi.nr.workflow2.engine.core.process.defintion.IProcessStatus;
import com.jiuqi.nr.workflow2.engine.core.process.defintion.IUserAction;
import com.jiuqi.nr.workflow2.engine.core.process.defintion.IUserTask;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBizObjectOperateResult;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKey;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKeyCollection;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObject;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IOperateResult;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IProcessInstance;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IProcessOperation;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IProcessStatusWithOperation;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.common.BizObjectOperateResult;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.common.FormGroupObject;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.common.FormObject;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.common.IDimensionObjectMapping;
import com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsService;
import com.jiuqi.nr.workflow2.engine.core.settings.enumeration.WorkflowObjectType;
import com.jiuqi.nr.workflow2.engine.dflt.process.design.DefaultProcessConfig;
import com.jiuqi.nr.workflow2.engine.dflt.process.design.DefaultProcessDesignService;
import com.jiuqi.nr.workflow2.engine.dflt.process.design.config.enumeration.WorkflowDefineTemplate;
import com.jiuqi.nr.workflow2.engine.dflt.process.design.config.property.AuditProperty;
import com.jiuqi.nr.workflow2.service.IProcessMetaDataService;
import com.jiuqi.nr.workflow2.service.IProcessQueryService;
import com.jiuqi.nr.workflow2.service.dimension.EProcessDimensionName;
import com.jiuqi.nr.workflow2.service.enumeration.IProcessFormRejectAttrKeys;
import com.jiuqi.nr.workflow2.service.helper.IReportDimensionHelper;
import com.jiuqi.nr.workflow2.service.helper.ProcessDimensionsBuilder;
import com.jiuqi.nr.workflow2.service.para.EProcessRangeDimType;
import com.jiuqi.nr.workflow2.service.para.IProcessRunPara;
import com.jiuqi.nr.workflow2.service.para.ProcessBatchRunPara;
import com.jiuqi.nr.workflow2.service.para.ProcessOneDim;
import com.jiuqi.nr.workflow2.service.para.ProcessOneRunPara;
import com.jiuqi.nr.workflow2.service.para.ProcessRangeDims;
import com.jiuqi.nr.workflow2.service.para.ProcessRunPara;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
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
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

@Component
public class UploadOverviewConverterImpl
implements UploadOverviewConverter {
    private static final Logger logger = LoggerFactory.getLogger(UploadOverviewConverterImpl.class);
    private static final String ALL_UNIT_KEY = "all-unit";
    private static final String ALL_FORM_OR_FROM_GROUP_KEY = "allForm";
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private DefaultProcessDesignService defaultProcessDesignService;
    @Autowired
    private WorkflowSettingService workflowSettingService_1_0;
    @Autowired
    private WorkflowSettingsService workflowSettingsService_2_0;
    @Autowired
    private CustomWorkFolwService customWorkFolwService;
    @Autowired
    private OverviewUtil overviewUtil;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    @Autowired
    private IEntityDataService entityDataService;
    @Autowired
    private IEntityViewRunTimeController entityViewRunTimeController;
    @Autowired
    protected IJtableParamService jTableParamService;
    @Autowired
    private IReportDimensionHelper reportDimensionHelper;
    @Autowired
    private PeriodEngineService periodEngineService;
    @Autowired
    private IProcessQueryService processQueryService;
    @Autowired
    private ProcessDimensionsBuilder processDimensionsBuilder;
    @Autowired
    private TreeWorkflow treeWorkFlow;
    @Autowired
    private IWorkflow workflow;
    @Autowired
    private WorkflowTrackConverter workflowTrackConverter;
    @Autowired
    private UserManagerService userManagerService;
    @Autowired
    private IProcessMetaDataService processMetaDataService;
    @Autowired
    private WorkflowTrackConverter trackConverter;

    public List<UploadAllFormSumInfo> batchQueryState(BatchQueryUpload batchQueryUpload) {
        TaskDefine taskDefine = this.runTimeViewController.getTask(batchQueryUpload.getTaskKey());
        String formSchemeKey = batchQueryUpload.getFormSchemeKey();
        EntityViewData dwEntity = this.jTableParamService.getDwEntity(batchQueryUpload.getFormSchemeKey());
        EntityViewData dataTimeEntity = this.jTableParamService.getDataTimeEntity(batchQueryUpload.getFormSchemeKey());
        DimensionValueSet dimensionValueSet = DimensionValueSetUtil.getDimensionValueSet((Map)batchQueryUpload.getDimensionSet());
        String unitCode = dimensionValueSet.getValue(dwEntity.getDimensionName()).toString();
        String period = dimensionValueSet.getValue(dataTimeEntity.getDimensionName()).toString();
        FormSchemeDefine paramFormSchemeDefine = this.runTimeViewController.getFormScheme(formSchemeKey);
        if (!paramFormSchemeDefine.getTaskKey().equals(batchQueryUpload.getTaskKey())) {
            period = this.periodEngineService.getPeriodAdapter().getPeriodProvider(taskDefine.getDateTime()).getCurPeriod().getCode();
            String newFormSchemeKey = this.getFormSchemeKey(batchQueryUpload.getTaskKey(), period);
            if (newFormSchemeKey == null || newFormSchemeKey.isEmpty()) {
                Optional<SchemePeriodLinkDefine> target = this.runTimeViewController.listSchemePeriodLinkByTask(batchQueryUpload.getTaskKey()).stream().max(Comparator.comparing(SchemePeriodLinkDefine::getPeriodKey));
                if (target.isPresent()) {
                    SchemePeriodLinkDefine schemePeriodLinkDefine = target.get();
                    period = schemePeriodLinkDefine.getPeriodKey();
                    formSchemeKey = schemePeriodLinkDefine.getSchemeKey();
                }
            } else {
                formSchemeKey = newFormSchemeKey;
                batchQueryUpload.setFormSchemeKey(formSchemeKey);
            }
            DimensionValue newPeriod = new DimensionValue();
            newPeriod.setValue(period);
            newPeriod.setName(dataTimeEntity.getDimensionName());
            batchQueryUpload.getDimensionSet().put(dataTimeEntity.getDimensionName(), newPeriod);
        }
        FormSchemeDefine formSchemeDefine = this.runTimeViewController.getFormScheme(formSchemeKey);
        WorkflowObjectType workflowObjectType = this.workflowSettingsService_2_0.queryTaskWorkflowObjectType(batchQueryUpload.getTaskKey());
        if (batchQueryUpload.getFormKey() == null || batchQueryUpload.getFormKey().isEmpty()) {
            if (workflowObjectType.equals((Object)WorkflowObjectType.FORM)) {
                Optional firstFormDefine = this.runTimeViewController.listFormByFormScheme(formSchemeKey).stream().findFirst();
                firstFormDefine.ifPresent(formDefine -> batchQueryUpload.setFormKey(formDefine.getKey()));
            } else if (workflowObjectType.equals((Object)WorkflowObjectType.FORM_GROUP)) {
                Optional firstFormGroupDefine = this.runTimeViewController.listFormGroupByFormScheme(formSchemeKey).stream().findFirst();
                firstFormGroupDefine.ifPresent(formGroupDefine -> batchQueryUpload.setFormKey(formGroupDefine.getKey()));
            }
        }
        ArrayList<UploadAllFormSumInfo> result = new ArrayList<UploadAllFormSumInfo>();
        IEntityTable entityTable = this.getRangeEntityTable(this.getEntityCaliber(formSchemeDefine), period, taskDefine.getDateTime(), unitCode);
        boolean isCountDirect = batchQueryUpload.isOnlyDirectChild();
        IEntityRow rootRow = entityTable.findByEntityKey(unitCode);
        List statisticRangeRows = isCountDirect ? entityTable.getChildRows(rootRow.getEntityKeyData()) : entityTable.getAllChildRows(rootRow.getEntityKeyData());
        List<String> rangeRowKeys = statisticRangeRows.stream().map(IEntityItem::getEntityKeyData).collect(Collectors.toList());
        String formKeyInfo = batchQueryUpload.getFormKey();
        if (workflowObjectType.equals((Object)WorkflowObjectType.FORM)) {
            List formDefines = this.runTimeViewController.listFormByFormScheme(batchQueryUpload.getFormSchemeKey());
            Map<String, String> formKeyTitleMap = formDefines.stream().collect(Collectors.toMap(IBaseMetaItem::getKey, IBaseMetaItem::getTitle, (v1, v2) -> v1));
            List<String> rangeFormKeys = ALL_FORM_OR_FROM_GROUP_KEY.equals(formKeyInfo) ? formDefines.stream().sorted(Comparator.comparing(IBaseMetaItem::getOrder)).map(IBaseMetaItem::getKey).collect(Collectors.toList()) : Arrays.asList(formKeyInfo.split(";"));
            List<String> hasAuthorityFormKeyList = this.getHasAuthorityFormOrFormGroupKeyList(batchQueryUpload, rangeFormKeys);
            rangeFormKeys = rangeFormKeys.stream().filter(hasAuthorityFormKeyList::contains).collect(Collectors.toList());
            IBizObjectOperateResult<IProcessStatus> rangeRowWithFormStatusResult = this.getRangeRowWithFormStatus(rangeRowKeys, rangeFormKeys, batchQueryUpload);
            Iterable businessObjects = rangeRowWithFormStatusResult.getBusinessObjects();
            HashMap<String, List> filterByFormKeyMap = new HashMap<String, List>();
            for (IBusinessObject businessObject : businessObjects) {
                FormObject formObject = (FormObject)businessObject;
                List filterUnitStatusList = filterByFormKeyMap.computeIfAbsent(formObject.getFormKey(), k -> new ArrayList());
                filterUnitStatusList.add(rangeRowWithFormStatusResult.getResult((Object)businessObject));
            }
            for (String formKey : rangeFormKeys) {
                FormGroupDefine formGroupDefine2 = this.runTimeViewController.listFormGroupByForm(formKey, batchQueryUpload.getFormSchemeKey()).stream().findFirst().orElse(null);
                if (formGroupDefine2 == null) {
                    LoggerFactory.getLogger(UploadOverviewConverterImpl.class).error(String.format("Form group not found for formKey: %s", formKey));
                    continue;
                }
                UploadAllFormSumInfo userFormSumInfo = new UploadAllFormSumInfo();
                userFormSumInfo.setKeyTitle(this.getKeyTitle(batchQueryUpload));
                userFormSumInfo.setFormId(formKey);
                userFormSumInfo.setFormTitle(formKeyTitleMap.get(formKey));
                userFormSumInfo.setFormGroupId(formGroupDefine2.getKey());
                userFormSumInfo.setFormGroupTitle(formGroupDefine2.getTitle());
                userFormSumInfo.setMasterSum(rangeRowKeys.size());
                this.doStatistic_formORFormGroup(batchQueryUpload, rangeRowKeys, (List)filterByFormKeyMap.get(formKey), userFormSumInfo);
                result.add(userFormSumInfo);
            }
        } else {
            List formGroupDefines = this.runTimeViewController.listFormGroupByFormScheme(batchQueryUpload.getFormSchemeKey());
            Map<String, String> formGroupKeyTitleMap = formGroupDefines.stream().collect(Collectors.toMap(IBaseMetaItem::getKey, IMetaGroup::getTitle, (v1, v2) -> v1));
            List<String> rangeFormGroupKeys = ALL_FORM_OR_FROM_GROUP_KEY.equals(formKeyInfo) ? formGroupDefines.stream().map(IBaseMetaItem::getKey).collect(Collectors.toList()) : Arrays.asList(formKeyInfo.split(";"));
            List<String> hasAuthorityFormGroupKeyList = this.getHasAuthorityFormOrFormGroupKeyList(batchQueryUpload, rangeFormGroupKeys);
            rangeFormGroupKeys = rangeFormGroupKeys.stream().filter(hasAuthorityFormGroupKeyList::contains).collect(Collectors.toList());
            IBizObjectOperateResult<IProcessStatus> rangeRowWithFormGroupStatusResult = this.getRangeRowWithFormGroupStatus(rangeRowKeys, rangeFormGroupKeys, batchQueryUpload);
            Iterable businessObjects = rangeRowWithFormGroupStatusResult.getBusinessObjects();
            HashMap<String, List> filterByFormGroupKeyMap = new HashMap<String, List>();
            for (IBusinessObject businessObject : businessObjects) {
                FormGroupObject formGroupObject = (FormGroupObject)businessObject;
                List filterUnitStatusList = filterByFormGroupKeyMap.computeIfAbsent(formGroupObject.getFormGroupKey(), k -> new ArrayList());
                filterUnitStatusList.add(rangeRowWithFormGroupStatusResult.getResult((Object)businessObject));
            }
            for (String formGroupKey : rangeFormGroupKeys) {
                UploadAllFormSumInfo userFormSumInfo = new UploadAllFormSumInfo();
                userFormSumInfo.setKeyTitle(this.getKeyTitle(batchQueryUpload));
                userFormSumInfo.setFormGroupId(formGroupKey);
                userFormSumInfo.setFormGroupTitle(formGroupKeyTitleMap.get(formGroupKey));
                userFormSumInfo.setMasterSum(rangeRowKeys.size());
                this.doStatistic_formORFormGroup(batchQueryUpload, rangeRowKeys, (List)filterByFormGroupKeyMap.get(formGroupKey), userFormSumInfo);
                result.add(userFormSumInfo);
            }
        }
        return result;
    }

    public List<UploadSumInfo> batchUploadState(BatchQueryUpload batchQueryUpload) {
        UploadSumInfo rootSumInfo;
        IEntityRow rootRow;
        TaskDefine taskDefine = this.runTimeViewController.getTask(batchQueryUpload.getTaskKey());
        String formSchemeKey = batchQueryUpload.getFormSchemeKey();
        EntityViewData dwEntity = this.jTableParamService.getDwEntity(batchQueryUpload.getFormSchemeKey());
        EntityViewData dataTimeEntity = this.jTableParamService.getDataTimeEntity(batchQueryUpload.getFormSchemeKey());
        DimensionValueSet dimensionValueSet = DimensionValueSetUtil.getDimensionValueSet((Map)batchQueryUpload.getDimensionSet());
        String unitCode = dimensionValueSet.getValue(dwEntity.getDimensionName()).toString();
        String period = dimensionValueSet.getValue(dataTimeEntity.getDimensionName()).toString();
        FormSchemeDefine paramFormSchemeDefine = this.runTimeViewController.getFormScheme(formSchemeKey);
        if (!paramFormSchemeDefine.getTaskKey().equals(batchQueryUpload.getTaskKey())) {
            period = this.periodEngineService.getPeriodAdapter().getPeriodProvider(taskDefine.getDateTime()).getCurPeriod().getCode();
            String newFormSchemeKey = this.getFormSchemeKey(batchQueryUpload.getTaskKey(), period);
            if (newFormSchemeKey == null || newFormSchemeKey.isEmpty()) {
                Optional<SchemePeriodLinkDefine> target = this.runTimeViewController.listSchemePeriodLinkByTask(batchQueryUpload.getTaskKey()).stream().max(Comparator.comparing(SchemePeriodLinkDefine::getPeriodKey));
                if (target.isPresent()) {
                    SchemePeriodLinkDefine schemePeriodLinkDefine = target.get();
                    period = schemePeriodLinkDefine.getPeriodKey();
                    formSchemeKey = schemePeriodLinkDefine.getSchemeKey();
                }
            } else {
                formSchemeKey = newFormSchemeKey;
                batchQueryUpload.setFormSchemeKey(formSchemeKey);
            }
            DimensionValue newPeriod = new DimensionValue();
            newPeriod.setValue(period);
            newPeriod.setName(dataTimeEntity.getDimensionName());
            batchQueryUpload.getDimensionSet().put(dataTimeEntity.getDimensionName(), newPeriod);
        }
        FormSchemeDefine formSchemeDefine = this.runTimeViewController.getFormScheme(formSchemeKey);
        if (batchQueryUpload.getFormKey() == null || batchQueryUpload.getFormKey().isEmpty()) {
            WorkflowObjectType workflowObjectType = this.workflowSettingsService_2_0.queryTaskWorkflowObjectType(batchQueryUpload.getTaskKey());
            if (workflowObjectType.equals((Object)WorkflowObjectType.FORM)) {
                Optional firstFormDefine = this.runTimeViewController.listFormByFormScheme(formSchemeKey).stream().findFirst();
                firstFormDefine.ifPresent(formDefine -> batchQueryUpload.setFormKey(formDefine.getKey()));
            } else if (workflowObjectType.equals((Object)WorkflowObjectType.FORM_GROUP)) {
                Optional firstFormGroupDefine = this.runTimeViewController.listFormGroupByFormScheme(formSchemeKey).stream().findFirst();
                firstFormGroupDefine.ifPresent(formGroupDefine -> batchQueryUpload.setFormKey(formGroupDefine.getKey()));
            }
        }
        ArrayList<UploadSumInfo> result = new ArrayList<UploadSumInfo>();
        IEntityTable entityTable = this.getRangeEntityTable(this.getEntityCaliber(formSchemeDefine), period, taskDefine.getDateTime(), unitCode);
        UploadSumNew rootSumNew = new UploadSumNew();
        if (ALL_UNIT_KEY.equals(unitCode)) {
            rootRow = null;
            rootSumInfo = this.makeStatistics(rootSumNew, rootRow, entityTable, formSchemeDefine, batchQueryUpload);
            rootSumInfo.setKeyTitle(this.getKeyTitle(batchQueryUpload));
            result.add(rootSumInfo);
        } else {
            rootRow = entityTable.findByEntityKey(unitCode);
            if (rootRow == null) {
                return new ArrayList<UploadSumInfo>();
            }
            this.buildTimeAndComment(rootSumNew, rootRow, formSchemeDefine, period, batchQueryUpload);
            rootSumInfo = this.makeStatistics(rootSumNew, rootRow, entityTable, formSchemeDefine, batchQueryUpload);
            rootSumInfo.setKeyTitle(this.getKeyTitle(batchQueryUpload));
            result.add(rootSumInfo);
        }
        List<Object> showRangeRows = rootRow == null ? entityTable.getRootRows() : entityTable.getChildRows(rootRow.getEntityKeyData());
        if (showRangeRows == null || showRangeRows.isEmpty()) {
            result.add(rootSumInfo);
        } else {
            showRangeRows = this.filterShowRangeRows(batchQueryUpload, showRangeRows, entityTable);
            for (IEntityRow row : showRangeRows) {
                UploadSumNew sumNew = new UploadSumNew();
                this.buildTimeAndComment(sumNew, row, formSchemeDefine, period, batchQueryUpload);
                result.add(this.makeStatistics(sumNew, row, entityTable, formSchemeDefine, batchQueryUpload));
            }
        }
        return result;
    }

    public List<ActionInfo> batchWorkFlow(QueryUploadStateInfo queryUploadStateInfo) {
        String formSchemeKey = queryUploadStateInfo.getFormSchemeKey();
        Map actionInfo = this.treeWorkFlow.getActionCodeAndStateName(formSchemeKey);
        Map actionCodeAndActionName = this.treeWorkFlow.getActionCodeAndActionName(formSchemeKey);
        FormSchemeDefine formSchemeDefine = this.runTimeViewController.getFormScheme(formSchemeKey);
        LinkedList<ActionInfo> tableInfo = new LinkedList<ActionInfo>();
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
        String workflowEngine = this.workflowSettingsService_2_0.queryTaskWorkflowEngine(formSchemeDefine.getTaskKey());
        if (workflowEngine.equals("jiuqi.nr.default")) {
            String workflowDefine = this.workflowSettingsService_2_0.queryTaskWorkflowDefine(formSchemeDefine.getTaskKey());
            DefaultProcessConfig defaultProcessConfig = this.defaultProcessDesignService.queryDefaultProcessConfig(workflowDefine);
            WorkflowDefineTemplate workflowDefineTemplate = defaultProcessConfig.getWorkflowDefineTemplate();
            AuditProperty auditProperty = defaultProcessConfig.getAuditNodeConfig().getProperty();
            Map reportNodeActions = defaultProcessConfig.getReportNodeConfig().getActions();
            Map auditNodeActions = defaultProcessConfig.getAuditNodeConfig().getActions();
            if (workflowDefineTemplate.equals((Object)WorkflowDefineTemplate.SUBMIT_WORKFLOW)) {
                Map submitNodeActions = defaultProcessConfig.getSubmitNodeConfig().getActions();
                String submitButtonRename = ((com.jiuqi.nr.workflow2.engine.dflt.process.design.config.action.ActionInfo)submitNodeActions.get("act_submit")).getButtonName();
                String submitStateRename = ((com.jiuqi.nr.workflow2.engine.dflt.process.design.config.action.ActionInfo)submitNodeActions.get("act_submit")).getStateName();
                String backButtonName = ((com.jiuqi.nr.workflow2.engine.dflt.process.design.config.action.ActionInfo)reportNodeActions.get("act_return")).getButtonName();
                String backStateName = ((com.jiuqi.nr.workflow2.engine.dflt.process.design.config.action.ActionInfo)reportNodeActions.get("act_return")).getStateName();
                ActionInfo unSubmmit = new ActionInfo();
                unSubmmit.setKey("unSubmitedNum");
                unSubmmit.setTitle("\u672a" + submitButtonRename);
                unSubmmit.setType("1");
                tableInfo.add(unSubmmit);
                ActionInfo submmited = new ActionInfo();
                submmited.setKey("submitedNum");
                submmited.setTitle(submitStateRename);
                submmited.setType("1");
                tableInfo.add(submmited);
                ActionInfo submmitPer = new ActionInfo();
                submmitPer.setKey("submitedrate");
                submmitPer.setTitle(submitButtonRename + "\u7387");
                submmitPer.setType("1");
                tableInfo.add(submmitPer);
                ActionInfo returnInfo = new ActionInfo();
                returnInfo.setKey("returnedNum");
                returnInfo.setTitle(backStateName);
                returnInfo.setType("1");
                tableInfo.add(returnInfo);
            }
            String reportButtonRename = ((com.jiuqi.nr.workflow2.engine.dflt.process.design.config.action.ActionInfo)reportNodeActions.get("act_upload")).getButtonName();
            String reportStateRename = ((com.jiuqi.nr.workflow2.engine.dflt.process.design.config.action.ActionInfo)reportNodeActions.get("act_upload")).getStateName();
            String rejectButtonRename = ((com.jiuqi.nr.workflow2.engine.dflt.process.design.config.action.ActionInfo)auditNodeActions.get("act_reject")).getButtonName();
            String rejectStateRename = ((com.jiuqi.nr.workflow2.engine.dflt.process.design.config.action.ActionInfo)auditNodeActions.get("act_reject")).getStateName();
            String confirmButtonRename = ((com.jiuqi.nr.workflow2.engine.dflt.process.design.config.action.ActionInfo)auditNodeActions.get("act_confirm")).getButtonName();
            String confirmStateRename = ((com.jiuqi.nr.workflow2.engine.dflt.process.design.config.action.ActionInfo)auditNodeActions.get("act_confirm")).getStateName();
            if (workflowDefineTemplate.equals((Object)WorkflowDefineTemplate.STANDARD_WORKFLOW)) {
                ActionInfo original = new ActionInfo();
                original.setKey("originalNum");
                original.setTitle("\u672a" + reportButtonRename);
                original.setType("1");
                tableInfo.add(original);
            }
            ActionInfo uploadedNum = new ActionInfo();
            uploadedNum.setKey("uploadedNum");
            uploadedNum.setTitle(reportStateRename);
            uploadedNum.setType("1");
            tableInfo.add(uploadedNum);
            ActionInfo uploadedper = new ActionInfo();
            uploadedper.setKey("uploadedper");
            uploadedper.setTitle(reportButtonRename + "\u7387");
            uploadedper.setType("1");
            tableInfo.add(uploadedper);
            ActionInfo rejectedNum = new ActionInfo();
            rejectedNum.setKey("rejectedNum");
            rejectedNum.setTitle(rejectStateRename);
            rejectedNum.setType("1");
            tableInfo.add(rejectedNum);
            if (auditProperty.isConfirmEnable()) {
                ActionInfo confirmedNum = new ActionInfo();
                confirmedNum.setKey("confirmedNum");
                confirmedNum.setTitle(confirmStateRename);
                confirmedNum.setType("1");
                tableInfo.add(confirmedNum);
                ActionInfo comfirmedTime = new ActionInfo();
                comfirmedTime.setKey("comfirmedTime");
                comfirmedTime.setTitle(confirmButtonRename + "\u65f6\u95f4");
                comfirmedTime.setType("2");
                tableInfo.add(comfirmedTime);
                ActionInfo confirmedper = new ActionInfo();
                confirmedper.setKey("confirmedper");
                confirmedper.setTitle(confirmButtonRename + "\u7387");
                confirmedper.setType("1");
                tableInfo.add(confirmedper);
            }
            ActionInfo rejectedTime = new ActionInfo();
            rejectedTime.setKey("rejectTime");
            rejectedTime.setTitle(rejectButtonRename + "\u65f6\u95f4");
            rejectedTime.setType("2");
            tableInfo.add(rejectedTime);
            ActionInfo uploadNums = new ActionInfo();
            uploadNums.setKey("uploadNums");
            uploadNums.setTitle(reportButtonRename + "\u6b21\u6570");
            uploadNums.setType("2");
            tableInfo.add(uploadNums);
            ActionInfo firstUploadExplain = new ActionInfo();
            firstUploadExplain.setKey("firstUploadExplain");
            firstUploadExplain.setTitle("\u9996\u6b21" + reportButtonRename + "\u8bf4\u660e");
            firstUploadExplain.setType("2");
            tableInfo.add(firstUploadExplain);
            ActionInfo uploadExplain = new ActionInfo();
            uploadExplain.setKey("uploadExplain");
            uploadExplain.setTitle("\u6700\u65b0" + reportButtonRename + "\u8bf4\u660e");
            uploadExplain.setType("2");
            tableInfo.add(uploadExplain);
            ActionInfo rejectedExplain = new ActionInfo();
            rejectedExplain.setKey("rejectedExplain");
            rejectedExplain.setTitle(rejectButtonRename + "\u8bf4\u660e");
            rejectedExplain.setType("2");
            tableInfo.add(rejectedExplain);
            ActionInfo rejectedCount = new ActionInfo();
            rejectedCount.setKey("rejectedCount");
            rejectedCount.setTitle(rejectButtonRename + "\u6b21\u6570");
            rejectedCount.setType("2");
            tableInfo.add(rejectedCount);
            ActionInfo endUploadTime = new ActionInfo();
            endUploadTime.setKey("endUploadTime");
            endUploadTime.setTitle("\u6700\u65b0" + reportButtonRename + "\u65f6\u95f4");
            endUploadTime.setType("2");
            tableInfo.add(endUploadTime);
            ActionInfo firstUploadTime = new ActionInfo();
            firstUploadTime.setKey("firstUploadTime");
            firstUploadTime.setTitle("\u9996\u6b21" + reportButtonRename + "\u65f6\u95f4");
            firstUploadTime.setType("2");
            tableInfo.add(firstUploadTime);
        } else if (workflowEngine.equals("jiuqi.nr.customprocessengine")) {
            if (actionInfo.containsKey("start") && actionInfo.containsKey("cus_submit")) {
                ActionInfo unSubmmit = new ActionInfo();
                unSubmmit.setKey("unSubmitedNum");
                unSubmmit.setTitle((String)actionInfo.get("start"));
                unSubmmit.setType("1");
                tableInfo.add(unSubmmit);
            }
            if (actionInfo.containsKey("cus_submit")) {
                ActionInfo submmited = new ActionInfo();
                submmited.setKey("submitedNum");
                submmited.setTitle((String)actionInfo.get("cus_submit"));
                submmited.setType("1");
                tableInfo.add(submmited);
            }
            if (actionInfo.containsKey("cus_return")) {
                ActionInfo returnInfo = new ActionInfo();
                returnInfo.setKey("returnedNum");
                returnInfo.setTitle((String)actionInfo.get("cus_return"));
                returnInfo.setType("1");
                tableInfo.add(returnInfo);
            }
            ActionInfo rejectedTime = new ActionInfo();
            rejectedTime.setKey("rejectTime");
            rejectedTime.setTitle((String)actionCodeAndActionName.get("cus_reject") + "\u65f6\u95f4");
            rejectedTime.setType("2");
            tableInfo.add(rejectedTime);
            ActionInfo uploadNums = new ActionInfo();
            uploadNums.setKey("uploadNums");
            uploadNums.setTitle((String)actionCodeAndActionName.get("cus_upload") + "\u6b21\u6570");
            uploadNums.setType("2");
            tableInfo.add(uploadNums);
            ActionInfo firstUploadExplain = new ActionInfo();
            firstUploadExplain.setKey("firstUploadExplain");
            firstUploadExplain.setTitle("\u9996\u6b21" + (String)actionCodeAndActionName.get("cus_upload") + "\u8bf4\u660e");
            firstUploadExplain.setType("2");
            tableInfo.add(firstUploadExplain);
            ActionInfo uploadExplain = new ActionInfo();
            uploadExplain.setKey("uploadExplain");
            uploadExplain.setTitle("\u6700\u65b0" + (String)actionCodeAndActionName.get("cus_upload") + "\u8bf4\u660e");
            uploadExplain.setType("2");
            tableInfo.add(uploadExplain);
            ActionInfo rejectedExplain = new ActionInfo();
            rejectedExplain.setKey("rejectedExplain");
            rejectedExplain.setTitle((String)actionCodeAndActionName.get("cus_reject") + "\u8bf4\u660e");
            rejectedExplain.setType("2");
            tableInfo.add(rejectedExplain);
            ActionInfo rejectedCount = new ActionInfo();
            rejectedCount.setKey("rejectedCount");
            rejectedCount.setTitle((String)actionCodeAndActionName.get("cus_reject") + "\u6b21\u6570");
            rejectedCount.setType("2");
            tableInfo.add(rejectedCount);
            ActionInfo endUploadTime = new ActionInfo();
            endUploadTime.setKey("endUploadTime");
            endUploadTime.setTitle("\u6700\u65b0" + (String)actionCodeAndActionName.get("cus_upload") + "\u65f6\u95f4");
            endUploadTime.setType("2");
            tableInfo.add(endUploadTime);
            ActionInfo firstUploadTime = new ActionInfo();
            firstUploadTime.setKey("firstUploadTime");
            firstUploadTime.setTitle("\u9996\u6b21" + (String)actionCodeAndActionName.get("cus_upload") + "\u65f6\u95f4");
            firstUploadTime.setType("2");
            tableInfo.add(firstUploadTime);
            if (actionInfo.containsKey("cus_reject")) {
                ActionInfo rejectedNum = new ActionInfo();
                rejectedNum.setKey("rejectedNum");
                rejectedNum.setTitle((String)actionInfo.get("cus_reject"));
                rejectedNum.setType("1");
                tableInfo.add(rejectedNum);
            }
            if (actionInfo.containsKey("cus_upload")) {
                ActionInfo uploadedNum = new ActionInfo();
                uploadedNum.setKey("uploadedNum");
                uploadedNum.setTitle((String)actionInfo.get("cus_upload"));
                uploadedNum.setType("1");
                tableInfo.add(uploadedNum);
            }
            ActionInfo uploadedper = new ActionInfo();
            uploadedper.setKey("uploadedper");
            uploadedper.setTitle((String)actionCodeAndActionName.get("cus_upload") + "\u7387");
            uploadedper.setType("1");
            tableInfo.add(uploadedper);
            if (actionInfo.containsKey("cus_confirm")) {
                ActionInfo confirmedNum = new ActionInfo();
                confirmedNum.setKey("confirmedNum");
                confirmedNum.setTitle((String)actionInfo.get("cus_confirm"));
                confirmedNum.setType("1");
                tableInfo.add(confirmedNum);
                ActionInfo comfirmedTime = new ActionInfo();
                comfirmedTime.setKey("comfirmedTime");
                comfirmedTime.setTitle((String)actionCodeAndActionName.get("cus_confirm") + "\u65f6\u95f4");
                comfirmedTime.setType("2");
                tableInfo.add(comfirmedTime);
                ActionInfo confirmedper = new ActionInfo();
                confirmedper.setKey("confirmedper");
                confirmedper.setTitle((String)actionCodeAndActionName.get("cus_confirm") + "\u7387");
                confirmedper.setType("1");
                tableInfo.add(confirmedper);
            }
        }
        ActionInfo details = new ActionInfo();
        details.setKey("details");
        details.setTitle("\u8be6\u60c5");
        details.setType("1");
        tableInfo.add(details);
        return tableInfo;
    }

    public List<UploadActionInfo> batchUploadActions(@Valid @RequestBody BatchQueryUpload batchQueryUpload) {
        ArrayList processOperations;
        TaskDefine taskDefine = this.runTimeViewController.getTask(batchQueryUpload.getTaskKey());
        FormSchemeDefine formSchemeDefine = this.runTimeViewController.getFormScheme(batchQueryUpload.getFormSchemeKey());
        EntityViewData dwEntity = this.jTableParamService.getDwEntity(batchQueryUpload.getFormSchemeKey());
        EntityViewData dataTimeEntity = this.jTableParamService.getDataTimeEntity(batchQueryUpload.getFormSchemeKey());
        DimensionValueSet dimensionValueSet = DimensionValueSetUtil.getDimensionValueSet((Map)batchQueryUpload.getDimensionSet());
        String unitCode = dimensionValueSet.getValue(dwEntity.getDimensionName()).toString();
        String period = dimensionValueSet.getValue(dataTimeEntity.getDimensionName()).toString();
        HashSet<ProcessOneDim> oneDims = new HashSet<ProcessOneDim>();
        ProcessOneDim unitOneDim = new ProcessOneDim();
        unitOneDim.setDimensionName(dwEntity.getDimensionName());
        unitOneDim.setDimensionKey(this.getEntityCaliber(formSchemeDefine));
        unitOneDim.setDimensionValue(unitCode);
        oneDims.add(unitOneDim);
        WorkflowObjectType workflowObjectType = this.workflowSettingsService_2_0.queryTaskWorkflowObjectType(batchQueryUpload.getTaskKey());
        if (workflowObjectType.equals((Object)WorkflowObjectType.FORM)) {
            String formKey = batchQueryUpload.getFormKey();
            ProcessOneDim formOneDim = new ProcessOneDim();
            formOneDim.setDimensionName(EProcessDimensionName.PROCESS_FORM.dimName);
            formOneDim.setDimensionKey(EProcessDimensionName.PROCESS_FORM.dimName);
            formOneDim.setDimensionValue(formKey);
            oneDims.add(formOneDim);
        } else if (workflowObjectType.equals((Object)WorkflowObjectType.FORM_GROUP)) {
            String formGroupKey = batchQueryUpload.getFormKey();
            ProcessOneDim formGroupOneDim = new ProcessOneDim();
            formGroupOneDim.setDimensionName(EProcessDimensionName.PROCESS_GROUP.dimName);
            formGroupOneDim.setDimensionKey(EProcessDimensionName.PROCESS_GROUP.dimName);
            formGroupOneDim.setDimensionValue(formGroupKey);
            oneDims.add(formGroupOneDim);
        }
        ProcessOneRunPara oneRunPara = new ProcessOneRunPara();
        oneRunPara.setTaskKey(batchQueryUpload.getTaskKey());
        oneRunPara.setPeriod(period);
        oneRunPara.setReportDimensions(oneDims);
        IBusinessKey businessKey = this.processDimensionsBuilder.buildBusinessKey(oneRunPara);
        try {
            processOperations = this.processQueryService.queryProcessOperations((IProcessRunPara)oneRunPara, businessKey);
        }
        catch (InstanceNotFoundException e2) {
            processOperations = new ArrayList();
        }
        String workflowEngine = this.workflowSettingsService_2_0.queryTaskWorkflowEngine(batchQueryUpload.getTaskKey());
        String workflowDefine = this.workflowSettingsService_2_0.queryTaskWorkflowDefine(batchQueryUpload.getTaskKey());
        DefaultProcessConfig defaultProcessConfig = this.defaultProcessDesignService.queryDefaultProcessConfig(workflowDefine);
        boolean isConfirmEnable = defaultProcessConfig.getAuditNodeConfig().getProperty().isConfirmEnable();
        boolean isDefaultEngine = workflowEngine.equals("jiuqi.nr.default");
        ArrayList<UploadRecordNew> processTrack = new ArrayList<UploadRecordNew>();
        ShowResult showResult = new ShowResult();
        showResult.setConfrimed(isConfirmEnable);
        showResult.setDefaultWorkflow(isDefaultEngine);
        ArrayList<ShowNodeResult> nodeList = new ArrayList<ShowNodeResult>();
        for (IProcessOperation processOperation : processOperations) {
            UploadRecordNew processRecord = new UploadRecordNew();
            processRecord.setOperationid(processOperation.getId());
            processRecord.setFormKey(batchQueryUpload.getFormKey());
            processRecord.setTaskId(processOperation.getFromNode());
            processRecord.setAction(processOperation.getAction());
            processRecord.setOperator(this.workflowTrackConverter.getUserName(processOperation.getOperator()));
            processRecord.setReturnType(this.workflowTrackConverter.getReturnTypeTitle(formSchemeDefine.getKey(), processOperation.getOperateType()));
            processRecord.setCmt(processOperation.getComment());
            processRecord.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(processOperation.getOperateTime().getTime()));
            processTrack.add(processRecord);
            IUserTask userTask = this.processMetaDataService.getUserTask(formSchemeDefine.getTaskKey(), processOperation.getFromNode());
            IUserAction userAction = this.processMetaDataService.queryAction(formSchemeDefine.getTaskKey(), processOperation.getFromNode(), processOperation.getAction());
            ShowNodeResult nodeResult = new ShowNodeResult();
            nodeResult.setNodeId(processOperation.getFromNode());
            nodeResult.setNodeName(userTask.getAlias());
            nodeResult.setUser(this.workflowTrackConverter.getUserName(processOperation.getOperator()));
            nodeResult.setActionCode(processOperation.getAction());
            nodeResult.setActionName(userAction.getAlias());
            nodeResult.setReturnType(this.workflowTrackConverter.getReturnTypeTitle(batchQueryUpload.getFormSchemeKey(), processOperation.getOperateType()));
            nodeResult.setDesc(processOperation.getComment());
            nodeResult.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(processOperation.getOperateTime().getTime()));
            nodeList.add(nodeResult);
        }
        IProcessInstance instance = this.processQueryService.queryInstances((IProcessRunPara)oneRunPara, businessKey);
        if (instance != null) {
            List<Object> actorList;
            IUserTask userTask = this.processMetaDataService.getUserTask(formSchemeDefine.getTaskKey(), instance.getCurrentUserTask());
            try {
                actorList = this.processQueryService.queryMatchingActors((IProcessRunPara)oneRunPara, businessKey).stream().map(this.workflowTrackConverter::getUserLoginName).filter(e -> this.userManagerService.findByUsername(e).isPresent()).map(e -> {
                    Optional targetUser = this.userManagerService.findByUsername(e);
                    return targetUser.isPresent() ? ((User)targetUser.get()).getFullname() : "\u672a\u627e\u5230\u8be5\u7528\u6237\u4fe1\u606f";
                }).collect(Collectors.toList());
            }
            catch (UserTaskNotFoundException e3) {
                actorList = new ArrayList();
            }
            String actorShowStr = String.join((CharSequence)",", actorList);
            ShowNodeResult waitForExecuteNode = new ShowNodeResult();
            waitForExecuteNode.setNodeId(userTask.getCode());
            waitForExecuteNode.setNodeName(userTask.getAlias());
            waitForExecuteNode.setActors(actorShowStr);
            nodeList.add(waitForExecuteNode);
            showResult.setNodeList(nodeList);
        }
        IEntityTable rangeEntityTable = this.getRangeEntityTable(this.getEntityCaliber(formSchemeDefine), period, taskDefine.getDateTime(), unitCode);
        IEntityRow curEntityRow = rangeEntityTable.findByEntityKey(unitCode);
        UploadActionInfo uploadActionInfo = new UploadActionInfo(curEntityRow.getEntityKeyData(), curEntityRow.getCode(), curEntityRow.getTitle(), curEntityRow.isLeaf());
        uploadActionInfo.setDefaultFlow(isDefaultEngine);
        uploadActionInfo.setActions(processTrack);
        uploadActionInfo.setShowResult(showResult);
        ArrayList<UploadActionInfo> result = new ArrayList<UploadActionInfo>();
        result.add(uploadActionInfo);
        return result;
    }

    public void exportUploadState(ExportExcelState exportExcelState, HttpServletResponse response, HttpServletRequest request) {
        try (XSSFWorkbook workbook = new XSSFWorkbook();){
            XSSFSheet sheet = workbook.createSheet();
            ObjectMapper objectMapper = new ObjectMapper();
            Map tableHeader = (Map)objectMapper.readValue(exportExcelState.getTableHeader(), (TypeReference)new TypeReference<LinkedHashMap<String, String>>(){});
            DefaultIndexedColorMap defaultIndexedColorMap = new DefaultIndexedColorMap();
            XSSFColor clr = new XSSFColor(defaultIndexedColorMap);
            clr.setARGBHex("F1F1F1");
            XSSFCellStyle headerCellStyle = workbook.createCellStyle();
            headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            headerCellStyle.setFillForegroundColor(clr);
            headerCellStyle.setBorderRight(BorderStyle.THIN);
            headerCellStyle.setBorderBottom(BorderStyle.THIN);
            headerCellStyle.setBottomBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
            headerCellStyle.setRightBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
            XSSFCellStyle dataCellStyle = workbook.createCellStyle();
            dataCellStyle.setAlignment(HorizontalAlignment.LEFT);
            int indexOfRow = 0;
            int indexOfColumn = 0;
            XSSFRow headerRow = sheet.createRow(indexOfRow++);
            for (String key : tableHeader.keySet()) {
                if (key.equals("details")) continue;
                sheet.setColumnWidth(indexOfColumn, ((String)tableHeader.get(key)).getBytes().length * 256);
                XSSFCell createCell = headerRow.createCell(indexOfColumn);
                createCell.setCellValue((String)tableHeader.get(key));
                createCell.setCellStyle(headerCellStyle);
                ++indexOfColumn;
            }
            List<UploadSumInfo> uploadSumInfos = this.batchUploadState((BatchQueryUpload)exportExcelState);
            EntityViewData dwEntity = this.jTableParamService.getDwEntity(exportExcelState.getFormSchemeKey());
            DimensionValueSet dimensionValueSet = DimensionValueSetUtil.getDimensionValueSet((Map)exportExcelState.getDimensionSet());
            String unitCode = dimensionValueSet.getValue(dwEntity.getDimensionName()).toString();
            for (UploadSumInfo uploadSum : uploadSumInfos) {
                if (uploadSum.getCode().equals(unitCode)) continue;
                XSSFRow dataRow = sheet.createRow(indexOfRow);
                indexOfColumn = 0;
                block69: for (String header : tableHeader.keySet()) {
                    XSSFCell createCell = dataRow.createCell(indexOfColumn);
                    createCell.setCellStyle(dataCellStyle);
                    switch (header) {
                        case "entity": {
                            createCell.setCellValue(uploadSum.getTitle());
                            int maxLength = uploadSum.getTitle().getBytes().length * 256;
                            maxLength = Math.min(maxLength, 8000);
                            sheet.setColumnWidth(0, maxLength);
                            ++indexOfColumn;
                            continue block69;
                        }
                        case "unitCode": {
                            createCell.setCellValue(uploadSum.getCode());
                            ++indexOfColumn;
                            continue block69;
                        }
                        case "submitedNum": {
                            createCell.setCellValue(uploadSum.getSum().getSubmitedNum());
                            ++indexOfColumn;
                            continue block69;
                        }
                        case "masterSum": {
                            createCell.setCellValue(uploadSum.getSum().getMasterSum());
                            ++indexOfColumn;
                            continue block69;
                        }
                        case "unSubmitedNum": {
                            createCell.setCellValue(uploadSum.getSum().getUnSubmitedNum());
                            ++indexOfColumn;
                            continue block69;
                        }
                        case "originalNum": {
                            createCell.setCellValue(uploadSum.getSum().getOriginalNum());
                            ++indexOfColumn;
                            continue block69;
                        }
                        case "uploadedNum": {
                            createCell.setCellValue(uploadSum.getSum().getUploadedNum());
                            ++indexOfColumn;
                            continue block69;
                        }
                        case "rejectedCount": {
                            createCell.setCellValue(uploadSum.getSum().getRejectedCount() == 0 ? "\u2014\u2014\u2014\u2014" : uploadSum.getSum().getRejectedCount() + "");
                            ++indexOfColumn;
                            continue block69;
                        }
                        case "firstUploadExplain": {
                            createCell.setCellValue(uploadSum.getSum().getFirstUploadExplain());
                            ++indexOfColumn;
                            continue block69;
                        }
                        case "uploadExplain": {
                            createCell.setCellValue(uploadSum.getSum().getUploadExplain());
                            ++indexOfColumn;
                            continue block69;
                        }
                        case "rejectedExplain": {
                            createCell.setCellValue(uploadSum.getSum().getRejectedExplain());
                            ++indexOfColumn;
                            continue block69;
                        }
                        case "rejectedNum": {
                            createCell.setCellValue(uploadSum.getSum().getRejectedNum());
                            ++indexOfColumn;
                            continue block69;
                        }
                        case "returnedNum": {
                            createCell.setCellValue(uploadSum.getSum().getReturnedNum());
                            ++indexOfColumn;
                            continue block69;
                        }
                        case "submitedTime": {
                            createCell.setCellValue(uploadSum.getSum().getSubmitedTime());
                            ++indexOfColumn;
                            continue block69;
                        }
                        case "rejectTime": {
                            createCell.setCellValue(uploadSum.getSum().getRejectTime());
                            ++indexOfColumn;
                            continue block69;
                        }
                        case "returnedTime": {
                            createCell.setCellValue(uploadSum.getSum().getReturnedTime());
                            ++indexOfColumn;
                            continue block69;
                        }
                        case "endUploadTime": {
                            createCell.setCellValue(uploadSum.getSum().getEndUploadTime());
                            ++indexOfColumn;
                            continue block69;
                        }
                        case "firstUploadTime": {
                            createCell.setCellValue(uploadSum.getSum().getFirstUploadTime());
                            ++indexOfColumn;
                            continue block69;
                        }
                        case "comfirmedTime": {
                            createCell.setCellValue(uploadSum.getSum().getComfirmedTime());
                            ++indexOfColumn;
                            continue block69;
                        }
                        case "uploadNums": {
                            createCell.setCellValue(uploadSum.getSum().getUploadNums() == 0 ? "\u2014\u2014\u2014\u2014" : uploadSum.getSum().getUploadNums() + "");
                            ++indexOfColumn;
                            continue block69;
                        }
                        case "confirmedNum": {
                            createCell.setCellValue(uploadSum.getSum().getConfirmedNum());
                            ++indexOfColumn;
                            continue block69;
                        }
                        case "cancelConfirmTime": {
                            createCell.setCellValue(uploadSum.getSum().getCancelConfirmTime());
                            ++indexOfColumn;
                            continue block69;
                        }
                        case "submitedrate": {
                            double submitedrate = ((double)uploadSum.getSum().getSubmitedNum() + (double)uploadSum.getSum().getUploadedNum() + (double)uploadSum.getSum().getConfirmedNum()) / (double)uploadSum.getSum().getMasterSum() * 100.0;
                            int submitedger = (int)submitedrate;
                            createCell.setCellValue(submitedger + "%");
                            ++indexOfColumn;
                            continue block69;
                        }
                        case "uploadedper": {
                            double uploadedper = ((double)uploadSum.getSum().getUploadedNum() + (double)uploadSum.getSum().getConfirmedNum()) / (double)uploadSum.getSum().getMasterSum() * 100.0;
                            int uploadedger = (int)uploadedper;
                            createCell.setCellValue(uploadedger + "%");
                            ++indexOfColumn;
                            continue block69;
                        }
                        case "confirmedper": {
                            double confirmedper = (double)uploadSum.getSum().getConfirmedNum() / (double)uploadSum.getSum().getMasterSum() * 100.0;
                            int confirmedger = (int)confirmedper;
                            createCell.setCellValue(confirmedger + "%");
                            ++indexOfColumn;
                            continue block69;
                        }
                        case "delayRate": {
                            double delayRate = (double)uploadSum.getSum().getDelayNum() / (double)uploadSum.getSum().getMasterSum() * 100.0;
                            int delayRateInt = (int)delayRate;
                            createCell.setCellValue(delayRateInt + "%");
                            ++indexOfColumn;
                            continue block69;
                        }
                    }
                    ++indexOfColumn;
                }
                ++indexOfRow;
            }
            workbook.setSheetName(0, "\u4e0a\u62a5\u7edf\u8ba1");
            response.setContentType("text/html;charset=UTF-8");
            ServletOutputStream outputStream = response.getOutputStream();
            workbook.write((OutputStream)outputStream);
            outputStream.flush();
            outputStream.close();
            response.flushBuffer();
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    public void exportUploadState2(ExportExcelState exportExcelState, HttpServletResponse response, HttpServletRequest request) {
        TaskDefine taskDefine = this.runTimeViewController.getTask(exportExcelState.getTaskKey());
        FormSchemeDefine formSchemeDefine = this.runTimeViewController.getFormScheme(exportExcelState.getFormSchemeKey());
        EntityViewData dwEntity = this.jTableParamService.getDwEntity(exportExcelState.getFormSchemeKey());
        EntityViewData dataTimeEntity = this.jTableParamService.getDataTimeEntity(exportExcelState.getFormSchemeKey());
        DimensionValueSet dimensionValueSet = DimensionValueSetUtil.getDimensionValueSet((Map)exportExcelState.getDimensionSet());
        String unitCode = dimensionValueSet.getValue(dwEntity.getDimensionName()).toString();
        String period = dimensionValueSet.getValue(dataTimeEntity.getDimensionName()).toString();
        WorkflowObjectType workflowObjectType = this.workflowSettingsService_2_0.queryTaskWorkflowObjectType(exportExcelState.getTaskKey());
        try (XSSFWorkbook workbook = new XSSFWorkbook();){
            DefaultIndexedColorMap defaultIndexedColorMap = new DefaultIndexedColorMap();
            XSSFColor clr = new XSSFColor(defaultIndexedColorMap);
            XSSFCellStyle headerCellStyle = workbook.createCellStyle();
            clr.setARGBHex("F1F1F1");
            headerCellStyle.setFillForegroundColor(clr);
            headerCellStyle.setBorderRight(BorderStyle.THIN);
            headerCellStyle.setBorderBottom(BorderStyle.THIN);
            headerCellStyle.setBorderLeft(BorderStyle.THIN);
            headerCellStyle.setBorderTop(BorderStyle.THIN);
            XSSFFont font = workbook.createFont();
            font.setBold(true);
            font.setFontName("\u9ed1\u4f53");
            headerCellStyle.setFont(font);
            XSSFCellStyle dataCellStyle = workbook.createCellStyle();
            dataCellStyle.setAlignment(HorizontalAlignment.LEFT);
            dataCellStyle.setFillForegroundColor(clr);
            dataCellStyle.setBorderRight(BorderStyle.THIN);
            dataCellStyle.setBorderBottom(BorderStyle.THIN);
            dataCellStyle.setBorderLeft(BorderStyle.THIN);
            dataCellStyle.setBorderTop(BorderStyle.THIN);
            List<Object> exportFormORFormGroupKeys = new ArrayList();
            String contextFormKey = exportExcelState.getFormKey();
            if (ALL_FORM_OR_FROM_GROUP_KEY.equals(contextFormKey)) {
                if (workflowObjectType.equals((Object)WorkflowObjectType.FORM)) {
                    List<String> formKeys = this.runTimeViewController.listFormByFormScheme(exportExcelState.getFormSchemeKey()).stream().sorted(Comparator.comparing(IBaseMetaItem::getOrder)).map(IBaseMetaItem::getKey).collect(Collectors.toList());
                    exportFormORFormGroupKeys = this.getHasAuthorityFormOrFormGroupKeyList((BatchQueryUpload)exportExcelState, formKeys);
                } else if (workflowObjectType.equals((Object)WorkflowObjectType.FORM_GROUP)) {
                    List formGroupKeys = this.runTimeViewController.listFormGroupByFormScheme(exportExcelState.getFormSchemeKey()).stream().sorted(Comparator.comparing(IBaseMetaItem::getOrder)).map(IBaseMetaItem::getKey).collect(Collectors.toList());
                    exportFormORFormGroupKeys = this.getHasAuthorityFormOrFormGroupKeyList((BatchQueryUpload)exportExcelState, formGroupKeys);
                }
            } else {
                exportFormORFormGroupKeys = Collections.singletonList(contextFormKey);
            }
            for (String string : exportFormORFormGroupKeys) {
                List<IEntityRow> statisticRangeRow;
                exportExcelState.setFormKey(string);
                String statisticSheetName = "\u4e0a\u62a5\u60c5\u51b5\u6c47\u603b\u4fe1\u606f";
                String detailSheetName = "\u4e0a\u62a5\u60c5\u51b5\u660e\u7ec6";
                if (workflowObjectType.equals((Object)WorkflowObjectType.FORM)) {
                    FormDefine formDefine = this.runTimeViewController.getForm(string, exportExcelState.getFormSchemeKey());
                    statisticSheetName = statisticSheetName + "-" + formDefine.getTitle();
                    detailSheetName = detailSheetName + "-" + formDefine.getTitle();
                } else if (workflowObjectType.equals((Object)WorkflowObjectType.FORM_GROUP)) {
                    FormGroupDefine formGroupDefine = this.runTimeViewController.getFormGroup(string, exportExcelState.getFormSchemeKey());
                    statisticSheetName = statisticSheetName + "-" + formGroupDefine.getTitle();
                    detailSheetName = detailSheetName + "-" + formGroupDefine.getTitle();
                }
                QueryUploadStateInfo queryUploadStateInfo = new QueryUploadStateInfo();
                queryUploadStateInfo.setPeriod(period);
                queryUploadStateInfo.setFormSchemeKey(exportExcelState.getFormSchemeKey());
                queryUploadStateInfo.setContextEntityId(exportExcelState.getContextEntityId());
                queryUploadStateInfo.setContextFilterExpression(exportExcelState.getContextFilterExpression());
                queryUploadStateInfo.setVariableStr(exportExcelState.getVariableStr());
                XSSFSheet statisticSheet = workbook.createSheet(statisticSheetName);
                String entityId = this.getEntityCaliber(formSchemeDefine);
                IEntityTable entityTable = this.getRangeEntityTable(entityId, period, taskDefine.getDateTime(), unitCode);
                if (ALL_UNIT_KEY.equals(unitCode)) {
                    statisticRangeRow = entityTable.getRootRows();
                } else {
                    IEntityRow rootRow = entityTable.findByEntityKey(unitCode);
                    statisticRangeRow = entityTable.getChildRows(rootRow.getEntityKeyData());
                }
                statisticRangeRow = this.filterShowRangeRows((BatchQueryUpload)exportExcelState, statisticRangeRow, entityTable);
                Map<String, List<String>> filterByStatusMap = this.getFilterByStatusMap((BatchQueryUpload)exportExcelState, statisticRangeRow);
                XSSFRow statisticHeaderRow = statisticSheet.createRow(0);
                XSSFRow statisticRow = statisticSheet.createRow(1);
                int indexOfColumn_static = 0;
                statisticSheet.autoSizeColumn(indexOfColumn_static);
                statisticSheet.setColumnWidth(indexOfColumn_static, statisticSheet.getColumnWidth(indexOfColumn_static) * 17 / 10);
                XSSFCell startHeaderCell = statisticHeaderRow.createCell(indexOfColumn_static);
                startHeaderCell.setCellValue("\u72b6\u6001");
                startHeaderCell.setCellStyle(headerCellStyle);
                XSSFCell startContentCell = statisticRow.createCell(indexOfColumn_static);
                startContentCell.setCellStyle(dataCellStyle);
                startContentCell.setCellValue("\u5355\u4f4d\u6237\u6570");
                statisticSheet.setColumnWidth(++indexOfColumn_static, "\u5e94\u62a5".getBytes().length * 256);
                XSSFCell totalHeaderCell = statisticHeaderRow.createCell(indexOfColumn_static);
                totalHeaderCell.setCellValue("\u5e94\u62a5");
                totalHeaderCell.setCellStyle(headerCellStyle);
                XSSFCell totalContentCell = statisticRow.createCell(indexOfColumn_static);
                totalContentCell.setCellStyle(dataCellStyle);
                totalContentCell.setCellValue(statisticRangeRow.size());
                ++indexOfColumn_static;
                String workflowDefine = this.workflowSettingsService_2_0.queryTaskWorkflowDefine(formSchemeDefine.getTaskKey());
                DefaultProcessConfig defaultProcessConfig = this.defaultProcessDesignService.queryDefaultProcessConfig(workflowDefine);
                String reportButtonName = ((com.jiuqi.nr.workflow2.engine.dflt.process.design.config.action.ActionInfo)defaultProcessConfig.getReportNodeConfig().getActions().get("act_upload")).getButtonName();
                ArrayList filterUnitList = filterByStatusMap.containsKey(UploadState.UPLOADED.toString()) ? filterByStatusMap.get(UploadState.UPLOADED.toString()) : new ArrayList();
                String unReportHeaderTitle = "\u672a" + reportButtonName;
                statisticSheet.setColumnWidth(indexOfColumn_static, unReportHeaderTitle.getBytes().length * 256);
                XSSFCell unReportHeaderCell = statisticHeaderRow.createCell(indexOfColumn_static);
                unReportHeaderCell.setCellValue(unReportHeaderTitle);
                unReportHeaderCell.setCellStyle(headerCellStyle);
                XSSFCell unReportContentCell = statisticRow.createCell(indexOfColumn_static);
                unReportContentCell.setCellStyle(dataCellStyle);
                unReportContentCell.setCellValue(statisticRangeRow.size() - filterUnitList.size());
                String reportHeaderTitle = "\u5df2" + reportButtonName;
                statisticSheet.setColumnWidth(++indexOfColumn_static, reportHeaderTitle.getBytes().length * 256);
                XSSFCell reportHeaderCell = statisticHeaderRow.createCell(indexOfColumn_static);
                reportHeaderCell.setCellValue(reportHeaderTitle);
                reportHeaderCell.setCellStyle(headerCellStyle);
                XSSFCell reportContentCell = statisticRow.createCell(indexOfColumn_static);
                reportContentCell.setCellStyle(dataCellStyle);
                reportContentCell.setCellValue(filterUnitList.size());
                ++indexOfColumn_static;
                XSSFSheet detailSheet = workbook.createSheet(detailSheetName);
                int indexOfRow_detail = 0;
                int indexOfColumn_detail = 0;
                XSSFRow headerRow = detailSheet.createRow(indexOfRow_detail++);
                List<String> outputColumn = Arrays.asList("\u5355\u4f4d\u540d\u79f0", "\u72b6\u6001", "\u4e0a\u62a5\u6b21\u6570", "\u9000\u56de\u6b21\u6570", "\u6700\u521d\u4e0a\u62a5\u65f6\u95f4", "\u6700\u521d\u4e0a\u62a5\u4eba", "\u6700\u540e\u4e00\u6b21\u4e0a\u62a5\u65f6\u95f4", "\u6700\u540e\u4e0a\u62a5\u4eba", "\u6700\u521d\u9000\u56de\u65f6\u95f4", "\u6700\u521d\u9000\u56de\u4eba", "\u6700\u540e\u4e00\u6b21\u9000\u56de\u65f6\u95f4", "\u6700\u540e\u9000\u56de\u4eba");
                for (String column : outputColumn) {
                    detailSheet.autoSizeColumn(indexOfColumn_detail);
                    detailSheet.setColumnWidth(indexOfColumn_detail, column.getBytes().length * 256);
                    XSSFCell createCell = headerRow.createCell(indexOfColumn_detail);
                    createCell.setCellValue(column);
                    createCell.setCellStyle(headerCellStyle);
                    ++indexOfColumn_detail;
                }
                IBizObjectOperateResult<IProcessStatusWithOperation> statisticRowStatusResult = this.getRangeRowStatus(statisticRangeRow.stream().map(IEntityItem::getEntityKeyData).collect(Collectors.toList()), (BatchQueryUpload)exportExcelState);
                Iterable businessObjects = statisticRowStatusResult.getBusinessObjects();
                HashMap<String, IOperateResult> rowStatusMap = new HashMap<String, IOperateResult>();
                for (IBusinessObject businessObject : businessObjects) {
                    String statisticUnitCode = businessObject.getDimensions().getDWDimensionValue().getValue().toString();
                    rowStatusMap.put(statisticUnitCode, statisticRowStatusResult.getResult((Object)businessObject));
                }
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                for (IEntityRow row : statisticRangeRow) {
                    ArrayList processOperations;
                    String dimensionName = this.entityMetaService.queryEntity(entityId).getDimensionName();
                    HashSet<ProcessOneDim> oneDims = new HashSet<ProcessOneDim>();
                    ProcessOneDim unitOneDim = new ProcessOneDim();
                    unitOneDim.setDimensionName(dimensionName);
                    unitOneDim.setDimensionKey(entityId);
                    unitOneDim.setDimensionValue(row.getEntityKeyData());
                    oneDims.add(unitOneDim);
                    if (workflowObjectType.equals((Object)WorkflowObjectType.FORM) || workflowObjectType.equals((Object)WorkflowObjectType.MD_WITH_SFR)) {
                        ProcessOneDim formOneDim = new ProcessOneDim();
                        formOneDim.setDimensionName(EProcessDimensionName.PROCESS_FORM.dimName);
                        formOneDim.setDimensionKey(EProcessDimensionName.PROCESS_FORM.dimName);
                        formOneDim.setDimensionValue(string);
                        oneDims.add(formOneDim);
                    } else if (workflowObjectType.equals((Object)WorkflowObjectType.FORM_GROUP)) {
                        ProcessOneDim formGroupOneDim = new ProcessOneDim();
                        formGroupOneDim.setDimensionName(EProcessDimensionName.PROCESS_GROUP.dimName);
                        formGroupOneDim.setDimensionKey(EProcessDimensionName.PROCESS_GROUP.dimName);
                        formGroupOneDim.setDimensionValue(string);
                        oneDims.add(formGroupOneDim);
                    }
                    ProcessOneRunPara oneRunPara = new ProcessOneRunPara();
                    oneRunPara.setTaskKey(formSchemeDefine.getTaskKey());
                    oneRunPara.setPeriod(period);
                    oneRunPara.setReportDimensions(oneDims);
                    JSONObject envVariable = new JSONObject();
                    envVariable.put(IProcessFormRejectAttrKeys.use_form_reject_query.attrKey, false);
                    oneRunPara.setEnvVariables(envVariable);
                    IBusinessKey businessKey = this.processDimensionsBuilder.buildBusinessKey(oneRunPara);
                    HashMap<String, List> filterOperationMap = new HashMap<String, List>();
                    try {
                        processOperations = this.processQueryService.queryProcessOperations((IProcessRunPara)oneRunPara, businessKey);
                    }
                    catch (InstanceNotFoundException e) {
                        processOperations = new ArrayList();
                    }
                    for (IProcessOperation processOperation : processOperations) {
                        String actionCode = processOperation.getAction();
                        List filterOperationList = filterOperationMap.computeIfAbsent(actionCode, k -> new ArrayList());
                        filterOperationList.add(processOperation);
                    }
                    List reportOperationList = filterOperationMap.containsKey("act_upload") ? (List)filterOperationMap.get("act_upload") : new ArrayList();
                    List rejectOperationList = filterOperationMap.containsKey("act_reject") ? (List)filterOperationMap.get("act_reject") : new ArrayList();
                    XSSFRow dataRow = detailSheet.createRow(indexOfRow_detail++);
                    indexOfColumn_detail = 0;
                    XSSFCell unitNameCell = dataRow.createCell(indexOfColumn_detail);
                    unitNameCell.setCellStyle(dataCellStyle);
                    unitNameCell.setCellValue(row.getTitle());
                    int maxLength = row.getTitle().getBytes().length * 256;
                    maxLength = Math.min(maxLength, 8000);
                    detailSheet.setColumnWidth(0, maxLength);
                    ++indexOfColumn_detail;
                    IOperateResult status = (IOperateResult)rowStatusMap.get(row.getEntityKeyData());
                    String statusTitle = "";
                    if (status.isSuccessful()) {
                        statusTitle = ((IProcessStatusWithOperation)status.getResult()).getAlias();
                    } else {
                        ErrorCode errorCode = status.getErrorCode();
                        if (errorCode.equals((Object)ErrorCode.INSTANCE_NOT_FOUND)) {
                            statusTitle = "\u6d41\u7a0b\u5b9e\u4f8b\u672a\u542f\u52a8";
                        }
                    }
                    XSSFCell statusCell = dataRow.createCell(indexOfColumn_detail);
                    statusCell.setCellStyle(dataCellStyle);
                    statusCell.setCellValue(statusTitle);
                    XSSFCell reportNumCell = dataRow.createCell(++indexOfColumn_detail);
                    reportNumCell.setCellStyle(dataCellStyle);
                    reportNumCell.setCellValue(reportOperationList.size());
                    XSSFCell rejectCell = dataRow.createCell(++indexOfColumn_detail);
                    rejectCell.setCellStyle(dataCellStyle);
                    rejectCell.setCellValue(rejectOperationList.size());
                    Optional<IProcessOperation> earliestReport = reportOperationList.stream().min(Comparator.comparing(IProcessOperation::getOperateTime));
                    XSSFCell earliestReportTimeCell = dataRow.createCell(++indexOfColumn_detail);
                    earliestReportTimeCell.setCellStyle(dataCellStyle);
                    Optional<Calendar> earliestReportOperateTimeCell = earliestReport.map(IProcessOperation::getOperateTime);
                    earliestReportTimeCell.setCellValue((String)earliestReportOperateTimeCell.map(calendar -> simpleDateFormat.format(calendar.getTime())).orElse(null));
                    XSSFCell earliestReportOperatorCell = dataRow.createCell(++indexOfColumn_detail);
                    earliestReportOperatorCell.setCellStyle(dataCellStyle);
                    Optional<String> earliestReportOperator = earliestReport.map(IProcessOperation::getOperator);
                    earliestReportOperatorCell.setCellValue((String)earliestReportOperator.map(s -> this.trackConverter.getUserName((String)s)).orElse(null));
                    Optional<IProcessOperation> latestReport = reportOperationList.stream().max(Comparator.comparing(IProcessOperation::getOperateTime));
                    XSSFCell latestReportTimeCell = dataRow.createCell(++indexOfColumn_detail);
                    latestReportTimeCell.setCellStyle(dataCellStyle);
                    Optional<Calendar> latestReportOperateTimeCell = latestReport.map(IProcessOperation::getOperateTime);
                    latestReportTimeCell.setCellValue((String)latestReportOperateTimeCell.map(calendar -> simpleDateFormat.format(calendar.getTime())).orElse(null));
                    XSSFCell latestReportOperatorCell = dataRow.createCell(++indexOfColumn_detail);
                    latestReportOperatorCell.setCellStyle(dataCellStyle);
                    Optional<String> latestReportOperator = latestReport.map(IProcessOperation::getOperator);
                    latestReportOperatorCell.setCellValue((String)latestReportOperator.map(s -> this.trackConverter.getUserName((String)s)).orElse(null));
                    Optional<IProcessOperation> earliestReject = rejectOperationList.stream().min(Comparator.comparing(IProcessOperation::getOperateTime));
                    XSSFCell earliestRejectTimeCell = dataRow.createCell(++indexOfColumn_detail);
                    earliestRejectTimeCell.setCellStyle(dataCellStyle);
                    Optional<Calendar> earliestRejectOperateTimeCell = earliestReject.map(IProcessOperation::getOperateTime);
                    earliestRejectTimeCell.setCellValue((String)earliestRejectOperateTimeCell.map(calendar -> simpleDateFormat.format(calendar.getTime())).orElse(null));
                    XSSFCell earliestRejectOperatorCell = dataRow.createCell(++indexOfColumn_detail);
                    earliestRejectOperatorCell.setCellStyle(dataCellStyle);
                    Optional<String> earliestRejectOperator = earliestReject.map(IProcessOperation::getOperator);
                    earliestRejectOperatorCell.setCellValue((String)earliestRejectOperator.map(s -> this.trackConverter.getUserName((String)s)).orElse(null));
                    Optional<IProcessOperation> latestReject = rejectOperationList.stream().max(Comparator.comparing(IProcessOperation::getOperateTime));
                    XSSFCell latestRejectTimeCell = dataRow.createCell(++indexOfColumn_detail);
                    latestRejectTimeCell.setCellStyle(dataCellStyle);
                    Optional<Calendar> latestRejectOperateTimeCell = latestReject.map(IProcessOperation::getOperateTime);
                    latestRejectTimeCell.setCellValue((String)latestRejectOperateTimeCell.map(calendar -> simpleDateFormat.format(calendar.getTime())).orElse(null));
                    XSSFCell latestRejectOperatorCell = dataRow.createCell(++indexOfColumn_detail);
                    latestRejectOperatorCell.setCellStyle(dataCellStyle);
                    Optional<String> latestRejectOperator = latestReject.map(IProcessOperation::getOperator);
                    latestRejectOperatorCell.setCellValue((String)latestRejectOperator.map(s -> this.trackConverter.getUserName((String)s)).orElse(null));
                    ++indexOfColumn_detail;
                }
            }
            response.setContentType("text/html;charset=UTF-8");
            ServletOutputStream outputStream = response.getOutputStream();
            workbook.write((OutputStream)outputStream);
            outputStream.flush();
            outputStream.close();
            response.flushBuffer();
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    private Map<String, String> getKeyTitle(BatchQueryUpload batchQueryUpload) {
        LinkedHashMap<String, String> result = new LinkedHashMap<String, String>();
        String workflowEngine = this.workflowSettingsService_2_0.queryTaskWorkflowEngine(batchQueryUpload.getTaskKey());
        if (workflowEngine.equals("jiuqi.nr.default")) {
            String workflowDefine = this.workflowSettingsService_2_0.queryTaskWorkflowDefine(batchQueryUpload.getTaskKey());
            DefaultProcessConfig defaultProcessConfig = this.defaultProcessDesignService.queryDefaultProcessConfig(workflowDefine);
            WorkflowDefineTemplate workflowDefineTemplate = defaultProcessConfig.getWorkflowDefineTemplate();
            AuditProperty auditProperty = defaultProcessConfig.getAuditNodeConfig().getProperty();
            Map reportNodeActions = defaultProcessConfig.getReportNodeConfig().getActions();
            Map auditNodeActions = defaultProcessConfig.getAuditNodeConfig().getActions();
            if (workflowDefineTemplate.equals((Object)WorkflowDefineTemplate.SUBMIT_WORKFLOW)) {
                Map submitNodeActions = defaultProcessConfig.getSubmitNodeConfig().getActions();
                String submitButtonRename = ((com.jiuqi.nr.workflow2.engine.dflt.process.design.config.action.ActionInfo)submitNodeActions.get("act_submit")).getButtonName();
                String submitStateRename = ((com.jiuqi.nr.workflow2.engine.dflt.process.design.config.action.ActionInfo)submitNodeActions.get("act_submit")).getStateName();
                String backButtonName = ((com.jiuqi.nr.workflow2.engine.dflt.process.design.config.action.ActionInfo)reportNodeActions.get("act_return")).getButtonName();
                String backStateRename = ((com.jiuqi.nr.workflow2.engine.dflt.process.design.config.action.ActionInfo)reportNodeActions.get("act_return")).getStateName();
                result.put("unSubmitedNum", "\u672a" + submitButtonRename);
                result.put("submitedNum", submitStateRename);
                result.put("submitedrate", submitButtonRename + "\u7387");
                result.put("returnedNum", backStateRename);
            }
            String reportButtonRename = ((com.jiuqi.nr.workflow2.engine.dflt.process.design.config.action.ActionInfo)reportNodeActions.get("act_upload")).getButtonName();
            String reportStateRename = ((com.jiuqi.nr.workflow2.engine.dflt.process.design.config.action.ActionInfo)reportNodeActions.get("act_upload")).getStateName();
            String rejectButtonRename = ((com.jiuqi.nr.workflow2.engine.dflt.process.design.config.action.ActionInfo)auditNodeActions.get("act_reject")).getButtonName();
            String rejectStateRename = ((com.jiuqi.nr.workflow2.engine.dflt.process.design.config.action.ActionInfo)auditNodeActions.get("act_reject")).getStateName();
            String confirmButtonRename = ((com.jiuqi.nr.workflow2.engine.dflt.process.design.config.action.ActionInfo)auditNodeActions.get("act_confirm")).getButtonName();
            String confirmStateRename = ((com.jiuqi.nr.workflow2.engine.dflt.process.design.config.action.ActionInfo)auditNodeActions.get("act_confirm")).getStateName();
            result.put("originalNum", "\u672a" + reportButtonRename);
            result.put("uploadedNum", reportStateRename);
            result.put("ReportRate", reportButtonRename + "\u7387");
            result.put("rejectedNum", rejectStateRename);
            if (auditProperty.isConfirmEnable()) {
                result.put("confirmedNum", confirmStateRename);
                result.put("confirmrate", confirmButtonRename + "\u7387");
            }
        } else if (workflowEngine.equals("jiuqi.nr.customprocessengine")) {
            Map actionInfo = this.treeWorkFlow.getActionCodeAndStateName(batchQueryUpload.getFormSchemeKey());
            Map actionCodeAndActionName = this.treeWorkFlow.getActionCodeAndActionName(batchQueryUpload.getFormSchemeKey());
            if (actionInfo.containsKey("start") && (actionInfo.containsKey("act_submit") || actionInfo.containsKey("cus_submit"))) {
                result.put("unSubmitedNum", (String)actionInfo.get("start"));
            }
            if (actionInfo.containsKey("cus_submit")) {
                result.put("submitedNum", (String)actionInfo.get("cus_submit"));
            }
            if (actionInfo.containsKey("cus_return")) {
                result.put("returnedNum", (String)actionInfo.get("cus_return"));
            }
            if (actionCodeAndActionName.containsKey("cus_submit")) {
                result.put("submitedrate", (String)actionCodeAndActionName.get("act_submit") + "\u7387");
            } else {
                result.put("submitedrate", "\u9001\u5ba1\u7387");
            }
            if (actionInfo.containsKey("cus_reject")) {
                result.put("rejectedNum", (String)actionInfo.get("cus_reject"));
            }
            if (actionInfo.containsKey("cus_upload")) {
                result.put("uploadedNum", (String)actionInfo.get("cus_upload"));
            }
            if (actionInfo.containsKey("cus_confirm")) {
                result.put("confirmedNum", (String)actionInfo.get("cus_confirm"));
            }
            if (actionCodeAndActionName.containsKey("cus_upload")) {
                result.put("ReportRate", (String)actionCodeAndActionName.get("cus_upload") + "\u7387");
            } else {
                result.put("ReportRate", "\u4e0a\u62a5\u7387");
            }
            if (actionCodeAndActionName.containsKey("cus_confirm")) {
                result.put("confirmrate", (String)actionCodeAndActionName.get("cus_confirm") + "\u7387");
            } else {
                result.put("confirmrate", "\u786e\u8ba4\u7387");
            }
            HashMap stateToColor = new HashMap();
            Map statisticalStates = this.workflow.getStatisticalStates(batchQueryUpload.getFormSchemeKey(), stateToColor);
            for (Map.Entry entry : statisticalStates.entrySet()) {
                result.put("custom@" + (String)entry.getKey(), (String)entry.getKey());
            }
        }
        result.put("delayRate", "\u5ef6\u8fdf\u7387");
        result.put("delayNum", "\u5df2\u5ef6\u8fdf");
        return result;
    }

    private String transferToUploadStateEnum(String processStatus) {
        switch (processStatus) {
            case "unsubmited": {
                return UploadStateEnum.ORIGINAL_SUBMIT.getCode();
            }
            case "submited": {
                return UploadStateEnum.SUBMITED.getCode();
            }
            case "backed": {
                return UploadStateEnum.RETURNED.getCode();
            }
            case "unreported": {
                return UploadStateEnum.ORIGINAL_UPLOAD.getCode();
            }
            case "reported": {
                return UploadStateEnum.UPLOADED.getCode();
            }
            case "rejected": {
                return UploadStateEnum.REJECTED.getCode();
            }
            case "confirmed": {
                return UploadStateEnum.CONFIRMED.getCode();
            }
        }
        return UploadStateEnum.ORIGINAL.getCode();
    }

    private IProcessRunPara getRowBusinessKey(IEntityRow row, FormSchemeDefine formSchemeDefine, String period, BatchQueryUpload batchQueryUpload) {
        EntityViewData dwEntity = this.jTableParamService.getDwEntity(batchQueryUpload.getFormSchemeKey());
        WorkflowObjectType workflowObjectType = this.workflowSettingsService_2_0.queryTaskWorkflowObjectType(batchQueryUpload.getTaskKey());
        HashSet<ProcessOneDim> oneDims = new HashSet<ProcessOneDim>();
        ProcessOneDim unitOneDim = new ProcessOneDim();
        unitOneDim.setDimensionName(dwEntity.getDimensionName());
        unitOneDim.setDimensionKey(this.getEntityCaliber(formSchemeDefine));
        unitOneDim.setDimensionValue(row.getCode());
        oneDims.add(unitOneDim);
        if (workflowObjectType.equals((Object)WorkflowObjectType.FORM)) {
            String formKey = batchQueryUpload.getFormKey();
            ProcessOneDim formOneDim = new ProcessOneDim();
            formOneDim.setDimensionName(EProcessDimensionName.PROCESS_FORM.dimName);
            formOneDim.setDimensionKey(EProcessDimensionName.PROCESS_FORM.dimName);
            formOneDim.setDimensionValue(formKey);
            oneDims.add(formOneDim);
        } else if (workflowObjectType.equals((Object)WorkflowObjectType.FORM_GROUP)) {
            String formGroupKey = batchQueryUpload.getFormKey();
            ProcessOneDim formGroupOneDim = new ProcessOneDim();
            formGroupOneDim.setDimensionName(EProcessDimensionName.PROCESS_GROUP.dimName);
            formGroupOneDim.setDimensionKey(EProcessDimensionName.PROCESS_GROUP.dimName);
            formGroupOneDim.setDimensionValue(formGroupKey);
            oneDims.add(formGroupOneDim);
        }
        ProcessOneRunPara oneRunPara = new ProcessOneRunPara();
        oneRunPara.setTaskKey(formSchemeDefine.getTaskKey());
        oneRunPara.setPeriod(period);
        oneRunPara.setReportDimensions(oneDims);
        JSONObject envVariable = new JSONObject();
        envVariable.put(IProcessFormRejectAttrKeys.use_form_reject_query.attrKey, false);
        oneRunPara.setEnvVariables(envVariable);
        return oneRunPara;
    }

    private List<IEntityRow> filterShowRangeRows(BatchQueryUpload batchQueryUpload, List<IEntityRow> showRangeRows, IEntityTable entityTable) {
        boolean isCountDirect = batchQueryUpload.isOnlyDirectChild();
        int showType = batchQueryUpload.getSummaryScope();
        ArrayList<IEntityRow> tempRangeRows = new ArrayList<IEntityRow>();
        if (showType == 2) {
            for (IEntityRow row2 : showRangeRows) {
                tempRangeRows.add(row2);
                tempRangeRows.addAll(isCountDirect ? entityTable.getChildRows(row2.getEntityKeyData()) : entityTable.getAllChildRows(row2.getEntityKeyData()));
            }
            showRangeRows = tempRangeRows;
        }
        List<String> hasAuthorityEntityKeyDataList = this.getHasAuthorityEntityKeyDataList(batchQueryUpload, showRangeRows.stream().map(IEntityItem::getEntityKeyData).collect(Collectors.toList()));
        showRangeRows = showRangeRows.stream().filter(row -> hasAuthorityEntityKeyDataList.contains(row.getEntityKeyData())).collect(Collectors.toList());
        String filterStatus = batchQueryUpload.getFilter();
        if (filterStatus != null && !filterStatus.isEmpty()) {
            Map<String, List<String>> filterByStatusMap = this.getFilterByStatusMap(batchQueryUpload, showRangeRows);
            if (!filterByStatusMap.containsKey(filterStatus)) {
                return new ArrayList<IEntityRow>();
            }
            List<String> filterUnitList = filterByStatusMap.get(filterStatus);
            showRangeRows = showRangeRows.stream().filter(row -> filterUnitList.contains(row.getEntityKeyData())).collect(Collectors.toList());
        }
        return showRangeRows;
    }

    private List<String> getHasAuthorityEntityKeyDataList(BatchQueryUpload batchQueryUpload, List<String> rangeRowKeys) {
        ArrayList<String> hasAuthEntityKeyDataList = new ArrayList();
        FormSchemeDefine formSchemeDefine = this.runTimeViewController.getFormScheme(batchQueryUpload.getFormSchemeKey());
        EntityViewData dwEntity = this.jTableParamService.getDwEntity(batchQueryUpload.getFormSchemeKey());
        EntityViewData dataTimeEntity = this.jTableParamService.getDataTimeEntity(batchQueryUpload.getFormSchemeKey());
        DimensionValueSet dimensionValueSet = DimensionValueSetUtil.getDimensionValueSet((Map)batchQueryUpload.getDimensionSet());
        String period = dimensionValueSet.getValue(dataTimeEntity.getDimensionName()).toString();
        WorkflowObjectType workflowObjectType = this.workflowSettingsService_2_0.queryTaskWorkflowObjectType(batchQueryUpload.getTaskKey());
        if (workflowObjectType.equals((Object)WorkflowObjectType.FORM)) {
            HashSet<ProcessRangeDims> rangeDims = new HashSet<ProcessRangeDims>();
            ProcessRangeDims rangeDim = new ProcessRangeDims();
            rangeDim.setDimensionName(dwEntity.getDimensionName());
            rangeDim.setDimensionKey(this.getEntityCaliber(formSchemeDefine));
            rangeDim.setRangeType(EProcessRangeDimType.RANGE);
            rangeDim.setRangeDims(rangeRowKeys);
            rangeDims.add(rangeDim);
            ProcessBatchRunPara queryParam = new ProcessBatchRunPara();
            queryParam.setTaskKey(batchQueryUpload.getTaskKey());
            queryParam.setPeriod(period);
            queryParam.setReportDimensions(rangeDims);
            DimensionCollection dimensionCollection = this.processDimensionsBuilder.buildDimensionCollection(queryParam);
            IDimensionObjectMapping hasAuthFormKeysMap = this.processDimensionsBuilder.processDimToFormDefinesMap(formSchemeDefine, dimensionCollection, Collections.singleton(batchQueryUpload.getFormKey()));
            for (DimensionCombination dimensionCombination : dimensionCollection.getDimensionCombinations()) {
                Collection formGroupKeys = hasAuthFormKeysMap.getObject(dimensionCombination);
                if (formGroupKeys == null || formGroupKeys.isEmpty()) continue;
                hasAuthEntityKeyDataList.add(dimensionCombination.getDWDimensionValue().getValue().toString());
            }
        } else if (workflowObjectType.equals((Object)WorkflowObjectType.FORM_GROUP)) {
            HashSet<ProcessRangeDims> rangeDims = new HashSet<ProcessRangeDims>();
            ProcessRangeDims rangeDim = new ProcessRangeDims();
            rangeDim.setDimensionName(dwEntity.getDimensionName());
            rangeDim.setDimensionKey(this.getEntityCaliber(formSchemeDefine));
            rangeDim.setRangeType(EProcessRangeDimType.RANGE);
            rangeDim.setRangeDims(rangeRowKeys);
            rangeDims.add(rangeDim);
            ProcessBatchRunPara queryParam = new ProcessBatchRunPara();
            queryParam.setTaskKey(batchQueryUpload.getTaskKey());
            queryParam.setPeriod(period);
            queryParam.setReportDimensions(rangeDims);
            DimensionCollection dimensionCollection = this.processDimensionsBuilder.buildDimensionCollection(queryParam);
            IDimensionObjectMapping hasAuthFormGroupKeysMap = this.processDimensionsBuilder.processDimToGroupDefinesMap(formSchemeDefine, dimensionCollection, Collections.singleton(batchQueryUpload.getFormKey()));
            for (DimensionCombination dimensionCombination : dimensionCollection.getDimensionCombinations()) {
                Collection formGroupKeys = hasAuthFormGroupKeysMap.getObject(dimensionCombination);
                if (formGroupKeys == null || formGroupKeys.isEmpty()) continue;
                hasAuthEntityKeyDataList.add(dimensionCombination.getDWDimensionValue().getValue().toString());
            }
        } else {
            hasAuthEntityKeyDataList = rangeRowKeys;
        }
        return hasAuthEntityKeyDataList;
    }

    private List<String> getHasAuthorityFormOrFormGroupKeyList(BatchQueryUpload batchQueryUpload, List<String> rangeFormOrFormGroupKeys) {
        ArrayList<String> hasAuthFormOrFormGroupKeyList = new ArrayList<String>();
        FormSchemeDefine formSchemeDefine = this.runTimeViewController.getFormScheme(batchQueryUpload.getFormSchemeKey());
        EntityViewData dwEntity = this.jTableParamService.getDwEntity(batchQueryUpload.getFormSchemeKey());
        EntityViewData dataTimeEntity = this.jTableParamService.getDataTimeEntity(batchQueryUpload.getFormSchemeKey());
        DimensionValueSet dimensionValueSet = DimensionValueSetUtil.getDimensionValueSet((Map)batchQueryUpload.getDimensionSet());
        String unitCode = dimensionValueSet.getValue(dwEntity.getDimensionName()).toString();
        String period = dimensionValueSet.getValue(dataTimeEntity.getDimensionName()).toString();
        HashSet<ProcessRangeDims> rangeDims = new HashSet<ProcessRangeDims>();
        ProcessRangeDims rangeDim = new ProcessRangeDims();
        rangeDim.setDimensionName(dwEntity.getDimensionName());
        rangeDim.setDimensionKey(this.getEntityCaliber(formSchemeDefine));
        rangeDim.setRangeType(EProcessRangeDimType.ONE);
        rangeDim.setDimensionValue(unitCode);
        rangeDims.add(rangeDim);
        ProcessBatchRunPara queryParam = new ProcessBatchRunPara();
        queryParam.setTaskKey(batchQueryUpload.getTaskKey());
        queryParam.setPeriod(period);
        queryParam.setReportDimensions(rangeDims);
        DimensionCollection dimensionCollection = this.processDimensionsBuilder.buildDimensionCollection(queryParam);
        WorkflowObjectType workflowObjectType = this.workflowSettingsService_2_0.queryTaskWorkflowObjectType(batchQueryUpload.getTaskKey());
        IDimensionObjectMapping hasAuthFormOrFormGroupKeysMap = workflowObjectType.equals((Object)WorkflowObjectType.FORM) ? this.processDimensionsBuilder.processDimToFormDefinesMap(formSchemeDefine, dimensionCollection, rangeFormOrFormGroupKeys) : this.processDimensionsBuilder.processDimToGroupDefinesMap(formSchemeDefine, dimensionCollection, rangeFormOrFormGroupKeys);
        if (dimensionCollection.getDimensionCombinations() != null && !dimensionCollection.getDimensionCombinations().isEmpty()) {
            DimensionCombination dimensionCombination = (DimensionCombination)dimensionCollection.getDimensionCombinations().get(0);
            return new ArrayList<String>(hasAuthFormOrFormGroupKeysMap.getObject(dimensionCombination));
        }
        return hasAuthFormOrFormGroupKeyList;
    }

    private Map<String, List<String>> getFilterByStatusMap(BatchQueryUpload batchQueryUpload, List<IEntityRow> rangeRows) {
        HashMap<String, List<String>> filterByStatusMap;
        block5: {
            IBizObjectOperateResult<IProcessStatusWithOperation> operateResult;
            WorkflowDefineTemplate workflowDefineTemplate;
            block3: {
                block4: {
                    filterByStatusMap = new HashMap<String, List<String>>();
                    String workflowDefine = this.workflowSettingsService_2_0.queryTaskWorkflowDefine(batchQueryUpload.getTaskKey());
                    DefaultProcessConfig defaultProcessConfig = this.defaultProcessDesignService.queryDefaultProcessConfig(workflowDefine);
                    workflowDefineTemplate = defaultProcessConfig.getWorkflowDefineTemplate();
                    operateResult = this.getRangeRowStatus(rangeRows.stream().map(IEntityItem::getEntityKeyData).collect(Collectors.toList()), batchQueryUpload);
                    if (operateResult != null && operateResult.size() != 0) break block3;
                    if (!workflowDefineTemplate.equals((Object)WorkflowDefineTemplate.SUBMIT_WORKFLOW)) break block4;
                    filterByStatusMap.put(UploadState.ORIGINAL_SUBMIT.toString(), rangeRows.stream().map(IEntityItem::getEntityKeyData).collect(Collectors.toList()));
                    break block5;
                }
                if (!workflowDefineTemplate.equals((Object)WorkflowDefineTemplate.STANDARD_WORKFLOW)) break block5;
                filterByStatusMap.put(UploadState.ORIGINAL_UPLOAD.toString(), rangeRows.stream().map(IEntityItem::getEntityKeyData).collect(Collectors.toList()));
                break block5;
            }
            Iterable businessObjects = operateResult.getBusinessObjects();
            for (IBusinessObject businessObject : businessObjects) {
                List filterUnitList;
                IOperateResult statusResult = operateResult.getResult((Object)businessObject);
                if (statusResult.isSuccessful()) {
                    String oldStateCode = DataEntryUnitTreeStateConverter.transferToOldStateCode(((IProcessStatusWithOperation)statusResult.getResult()).getCode());
                    if (oldStateCode.equals(UploadState.UPLOADED.toString()) && batchQueryUpload.isForceUpoload() && !((IProcessStatusWithOperation)statusResult.getResult()).getOperation().isForceReport()) continue;
                    filterUnitList = filterByStatusMap.computeIfAbsent(oldStateCode, key -> new ArrayList());
                    filterUnitList.add(businessObject.getDimensions().getDWDimensionValue().getValue().toString());
                    continue;
                }
                ErrorCode errorCode = statusResult.getErrorCode();
                if (!errorCode.equals((Object)ErrorCode.INSTANCE_NOT_FOUND)) continue;
                if (workflowDefineTemplate.equals((Object)WorkflowDefineTemplate.SUBMIT_WORKFLOW)) {
                    filterUnitList = filterByStatusMap.computeIfAbsent(UploadState.ORIGINAL_SUBMIT.toString(), key -> new ArrayList());
                    filterUnitList.add(businessObject.getDimensions().getDWDimensionValue().getValue().toString());
                    continue;
                }
                if (!workflowDefineTemplate.equals((Object)WorkflowDefineTemplate.STANDARD_WORKFLOW)) continue;
                filterUnitList = filterByStatusMap.computeIfAbsent(UploadState.ORIGINAL_UPLOAD.toString(), key -> new ArrayList());
                filterUnitList.add(businessObject.getDimensions().getDWDimensionValue().getValue().toString());
            }
        }
        return filterByStatusMap;
    }

    private void buildTimeAndComment(UploadSumNew uploadSumNew, IEntityRow row, FormSchemeDefine formSchemeDefine, String period, BatchQueryUpload batchQueryUpload) {
        ArrayList processOperations;
        IProcessRunPara oneRunPara = this.getRowBusinessKey(row, formSchemeDefine, period, batchQueryUpload);
        IBusinessKey businessKey = this.processDimensionsBuilder.buildBusinessKey((ProcessOneRunPara)oneRunPara);
        try {
            processOperations = this.processQueryService.queryProcessOperations(oneRunPara, businessKey);
        }
        catch (InstanceNotFoundException e) {
            processOperations = new ArrayList();
        }
        HashMap<String, List> filterByStatusMap = new HashMap<String, List>();
        for (IProcessOperation processOperation : processOperations) {
            List filterProcessOperationList = filterByStatusMap.computeIfAbsent(processOperation.getAction(), key -> new ArrayList());
            filterProcessOperationList.add(processOperation);
        }
        String filterStatus = batchQueryUpload.getFilter();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (String actionCode : filterByStatusMap.keySet()) {
            List filterProcessOperationList = (List)filterByStatusMap.get(actionCode);
            switch (actionCode) {
                case "act_submit": {
                    Optional<IProcessOperation> latestSubmitOperation = filterProcessOperationList.stream().max(Comparator.comparing(IProcessOperation::getOperateTime));
                    if (!latestSubmitOperation.isPresent()) {
                        throw new BusinessException("\u9001\u5ba1\u52a8\u4f5c\u5386\u53f2\u4e0d\u5b58\u5728\uff01\u6570\u636e\u5f02\u5e38\uff01");
                    }
                    uploadSumNew.setSubmitedTime(simpleDateFormat.format(latestSubmitOperation.get().getOperateTime().getTime()));
                    break;
                }
                case "act_return": {
                    Optional<IProcessOperation> latestBackOperation = filterProcessOperationList.stream().max(Comparator.comparing(IProcessOperation::getOperateTime));
                    if (!latestBackOperation.isPresent()) {
                        throw new BusinessException("\u9000\u5ba1\u52a8\u4f5c\u5386\u53f2\u4e0d\u5b58\u5728\uff01\u6570\u636e\u5f02\u5e38\uff01");
                    }
                    if (UploadState.RETURNED.toString().equals(filterStatus)) {
                        uploadSumNew.setCmt(latestBackOperation.get().getComment());
                    }
                    uploadSumNew.setReturnedTime(simpleDateFormat.format(latestBackOperation.get().getOperateTime().getTime()));
                    break;
                }
                case "act_upload": {
                    Optional<IProcessOperation> latestUploadOperation = filterProcessOperationList.stream().max(Comparator.comparing(IProcessOperation::getOperateTime));
                    if (!latestUploadOperation.isPresent()) {
                        throw new BusinessException("\u4e0a\u62a5\u52a8\u4f5c\u5386\u53f2\u4e0d\u5b58\u5728\uff01\u6570\u636e\u5f02\u5e38\uff01");
                    }
                    uploadSumNew.setUploadNums(filterProcessOperationList.size());
                    uploadSumNew.setUploadExplain(latestUploadOperation.get().getComment());
                    if (UploadState.UPLOADED.toString().equals(filterStatus)) {
                        uploadSumNew.setCmt(latestUploadOperation.get().getComment());
                    }
                    String latestTime = simpleDateFormat.format(latestUploadOperation.get().getOperateTime().getTime());
                    uploadSumNew.setEndUploadTime(latestTime);
                    uploadSumNew.setUploadDate(latestTime);
                    uploadSumNew.setTime(latestTime);
                    Optional<IProcessOperation> earliestUploadOperation = filterProcessOperationList.stream().min(Comparator.comparing(IProcessOperation::getOperateTime));
                    if (!earliestUploadOperation.isPresent()) {
                        throw new BusinessException("\u4e0a\u62a5\u52a8\u4f5c\u5386\u53f2\u4e0d\u5b58\u5728\uff01\u6570\u636e\u5f02\u5e38\uff01");
                    }
                    uploadSumNew.setFirstUploadExplain(earliestUploadOperation.get().getComment());
                    uploadSumNew.setFirstUploadTime(simpleDateFormat.format(earliestUploadOperation.get().getOperateTime().getTime()));
                    break;
                }
                case "act_reject": {
                    Optional<IProcessOperation> latestRejectOperation = filterProcessOperationList.stream().max(Comparator.comparing(IProcessOperation::getOperateTime));
                    if (!latestRejectOperation.isPresent()) {
                        throw new BusinessException("\u9000\u56de\u52a8\u4f5c\u5386\u53f2\u4e0d\u5b58\u5728\uff01\u6570\u636e\u5f02\u5e38\uff01");
                    }
                    uploadSumNew.setRejectedCount(filterProcessOperationList.size());
                    uploadSumNew.setRejectedExplain(latestRejectOperation.get().getComment());
                    if (UploadState.REJECTED.toString().equals(filterStatus)) {
                        uploadSumNew.setCmt(latestRejectOperation.get().getComment());
                    }
                    uploadSumNew.setReturnType(latestRejectOperation.get().getOperateType());
                    uploadSumNew.setRejectTime(simpleDateFormat.format(latestRejectOperation.get().getOperateTime().getTime()));
                    break;
                }
                case "act_confirm": {
                    Optional<IProcessOperation> latestConfirmOperation = filterProcessOperationList.stream().max(Comparator.comparing(IProcessOperation::getOperateTime));
                    if (!latestConfirmOperation.isPresent()) {
                        throw new BusinessException("\u786e\u8ba4\u52a8\u4f5c\u5386\u53f2\u4e0d\u5b58\u5728\uff01\u6570\u636e\u5f02\u5e38\uff01");
                    }
                    uploadSumNew.setComfirmedTime(simpleDateFormat.format(latestConfirmOperation.get().getOperateTime().getTime()));
                }
            }
        }
    }

    private UploadSumInfo makeStatistics(UploadSumNew sumInfo, IEntityRow rootRow, IEntityTable entityTable, FormSchemeDefine formSchemeDefine, BatchQueryUpload batchQueryUpload) {
        UploadSumInfo uploadSumInfo;
        int rowChildCount;
        boolean confirmBeforeUpload = this.isConfirmBeforeUpload(formSchemeDefine);
        boolean isCountDirect = batchQueryUpload.isOnlyDirectChild();
        if (rootRow == null) {
            rowChildCount = isCountDirect ? entityTable.getRootRows().size() : entityTable.getAllRows().size();
            uploadSumInfo = new UploadSumInfo(ALL_UNIT_KEY, ALL_UNIT_KEY, "\u5168\u90e8\u5355\u4f4d", rowChildCount);
            uploadSumInfo.setConfirmBeforeUpload(confirmBeforeUpload);
            uploadSumInfo.setLeaf(false);
        } else {
            rowChildCount = isCountDirect ? entityTable.getDirectChildCount(rootRow.getEntityKeyData()) : entityTable.getAllChildCount(rootRow.getEntityKeyData());
            uploadSumInfo = new UploadSumInfo(rootRow.getEntityKeyData(), rootRow.getCode(), rootRow.getTitle(), rowChildCount);
            uploadSumInfo.setConfirmBeforeUpload(confirmBeforeUpload);
            uploadSumInfo.setLeaf(rootRow.isLeaf());
        }
        List rangeRows = rootRow == null ? (isCountDirect ? entityTable.getRootRows() : entityTable.getAllRows()) : (rootRow.isLeaf() ? Collections.singletonList(rootRow) : (isCountDirect ? entityTable.getChildRows(rootRow.getEntityKeyData()) : entityTable.getAllChildRows(rootRow.getEntityKeyData())));
        ArrayList<IEntityRow> queryRows = new ArrayList<IEntityRow>(rangeRows);
        if (rootRow != null) {
            queryRows.add(rootRow);
        }
        List<String> hasAuthorityEntityKeyDataList = this.getHasAuthorityEntityKeyDataList(batchQueryUpload, queryRows.stream().map(IEntityItem::getEntityKeyData).collect(Collectors.toList()));
        List<String> rangeRowKeys = rangeRows.stream().map(IEntityItem::getEntityKeyData).filter(hasAuthorityEntityKeyDataList::contains).collect(Collectors.toList());
        if (rangeRowKeys.isEmpty()) {
            uploadSumInfo.setChildCount(0);
            uploadSumInfo.setLeaf(true);
            if (rootRow == null) {
                sumInfo.setFormKey(batchQueryUpload.getFormKey());
                return uploadSumInfo;
            }
            if (hasAuthorityEntityKeyDataList.contains(rootRow.getEntityKeyData())) {
                rangeRowKeys = Collections.singletonList(rootRow.getEntityKeyData());
            }
        }
        IBizObjectOperateResult<IProcessStatusWithOperation> rangeRowStatus = rangeRowKeys.isEmpty() ? new IBizObjectOperateResult<IProcessStatusWithOperation>() : this.getRangeRowStatus(rangeRowKeys, batchQueryUpload);
        sumInfo.setFormKey(batchQueryUpload.getFormKey());
        sumInfo.setMasterSum(rangeRowKeys.size());
        this.doStatistics(batchQueryUpload, rangeRowKeys, rangeRowStatus, sumInfo);
        uploadSumInfo.setSum(sumInfo);
        return uploadSumInfo;
    }

    private void doStatistics(BatchQueryUpload batchQueryUpload, List<String> rangeRowKeys, IBizObjectOperateResult<IProcessStatusWithOperation> rangeRowStatus, UploadSumNew uploadSumNew) {
        int confirmedNum;
        int rejectedNum;
        int reportedNum;
        int unReportedNum;
        int backedNum;
        int submittedNum;
        int unSubmittedNum;
        block23: {
            WorkflowDefineTemplate workflowDefineTemplate;
            block21: {
                block22: {
                    unSubmittedNum = 0;
                    submittedNum = 0;
                    backedNum = 0;
                    unReportedNum = 0;
                    reportedNum = 0;
                    rejectedNum = 0;
                    confirmedNum = 0;
                    String workflowDefine = this.workflowSettingsService_2_0.queryTaskWorkflowDefine(batchQueryUpload.getTaskKey());
                    DefaultProcessConfig defaultProcessConfig = this.defaultProcessDesignService.queryDefaultProcessConfig(workflowDefine);
                    workflowDefineTemplate = defaultProcessConfig.getWorkflowDefineTemplate();
                    if (rangeRowStatus != null && rangeRowStatus.size() != 0) break block21;
                    if (!workflowDefineTemplate.equals((Object)WorkflowDefineTemplate.SUBMIT_WORKFLOW)) break block22;
                    unSubmittedNum = rangeRowKeys.size();
                    break block23;
                }
                if (!workflowDefineTemplate.equals((Object)WorkflowDefineTemplate.STANDARD_WORKFLOW)) break block23;
                unReportedNum = rangeRowKeys.size();
                break block23;
            }
            Iterable businessObjects = rangeRowStatus.getBusinessObjects();
            for (IBusinessObject businessObject : businessObjects) {
                IOperateResult statusResult = rangeRowStatus.getResult((Object)businessObject);
                if (statusResult.isSuccessful()) {
                    String processStatusCode;
                    switch (processStatusCode = ((IProcessStatusWithOperation)statusResult.getResult()).getCode()) {
                        case "unsubmited": {
                            ++unSubmittedNum;
                            break;
                        }
                        case "submited": {
                            ++submittedNum;
                            break;
                        }
                        case "backed": {
                            ++backedNum;
                            break;
                        }
                        case "unreported": {
                            ++unReportedNum;
                            break;
                        }
                        case "reported": {
                            ++reportedNum;
                            break;
                        }
                        case "rejected": {
                            ++rejectedNum;
                            break;
                        }
                        case "confirmed": {
                            ++confirmedNum;
                        }
                    }
                    continue;
                }
                ErrorCode errorCode = statusResult.getErrorCode();
                if (!errorCode.equals((Object)ErrorCode.INSTANCE_NOT_FOUND)) continue;
                if (workflowDefineTemplate.equals((Object)WorkflowDefineTemplate.SUBMIT_WORKFLOW)) {
                    ++unSubmittedNum;
                    continue;
                }
                if (!workflowDefineTemplate.equals((Object)WorkflowDefineTemplate.STANDARD_WORKFLOW)) continue;
                ++unReportedNum;
            }
        }
        uploadSumNew.setUnSubmitedNum(unSubmittedNum);
        uploadSumNew.setSubmitedNum(submittedNum);
        uploadSumNew.setReturnedNum(backedNum);
        uploadSumNew.setOriginalNum(unReportedNum);
        uploadSumNew.setUploadedNum(reportedNum);
        uploadSumNew.setRejectedNum(rejectedNum);
        uploadSumNew.setConfirmedNum(confirmedNum);
    }

    private void doStatistic_formORFormGroup(BatchQueryUpload batchQueryUpload, List<String> rangeRowKeys, List<IOperateResult<IProcessStatus>> filterUnitStatusList, UploadAllFormSumInfo uploadAllFormSumInfo) {
        int confirmedNum;
        int rejectedNum;
        int reportedNum;
        int unReportedNum;
        int backedNum;
        int submittedNum;
        int unSubmittedNum;
        block23: {
            WorkflowDefineTemplate workflowDefineTemplate;
            block21: {
                block22: {
                    unSubmittedNum = 0;
                    submittedNum = 0;
                    backedNum = 0;
                    unReportedNum = 0;
                    reportedNum = 0;
                    rejectedNum = 0;
                    confirmedNum = 0;
                    String workflowDefine = this.workflowSettingsService_2_0.queryTaskWorkflowDefine(batchQueryUpload.getTaskKey());
                    DefaultProcessConfig defaultProcessConfig = this.defaultProcessDesignService.queryDefaultProcessConfig(workflowDefine);
                    workflowDefineTemplate = defaultProcessConfig.getWorkflowDefineTemplate();
                    if (filterUnitStatusList != null && !filterUnitStatusList.isEmpty()) break block21;
                    if (!workflowDefineTemplate.equals((Object)WorkflowDefineTemplate.SUBMIT_WORKFLOW)) break block22;
                    unSubmittedNum = rangeRowKeys.size();
                    break block23;
                }
                if (!workflowDefineTemplate.equals((Object)WorkflowDefineTemplate.STANDARD_WORKFLOW)) break block23;
                unReportedNum = rangeRowKeys.size();
                break block23;
            }
            for (IOperateResult<IProcessStatus> statusResult : filterUnitStatusList) {
                if (statusResult.isSuccessful()) {
                    String processStatusCode;
                    switch (processStatusCode = ((IProcessStatus)statusResult.getResult()).getCode()) {
                        case "unsubmited": {
                            ++unSubmittedNum;
                            break;
                        }
                        case "submited": {
                            ++submittedNum;
                            break;
                        }
                        case "backed": {
                            ++backedNum;
                            break;
                        }
                        case "unreported": {
                            ++unReportedNum;
                            break;
                        }
                        case "reported": {
                            ++reportedNum;
                            break;
                        }
                        case "rejected": {
                            ++rejectedNum;
                            break;
                        }
                        case "confirmed": {
                            ++confirmedNum;
                        }
                    }
                    continue;
                }
                ErrorCode errorCode = statusResult.getErrorCode();
                if (!errorCode.equals((Object)ErrorCode.INSTANCE_NOT_FOUND)) continue;
                if (workflowDefineTemplate.equals((Object)WorkflowDefineTemplate.SUBMIT_WORKFLOW)) {
                    ++unSubmittedNum;
                    continue;
                }
                if (!workflowDefineTemplate.equals((Object)WorkflowDefineTemplate.STANDARD_WORKFLOW)) continue;
                ++unReportedNum;
            }
        }
        uploadAllFormSumInfo.setUnSubmitedNum(unSubmittedNum);
        uploadAllFormSumInfo.setSubmitedNum(submittedNum);
        uploadAllFormSumInfo.setReturnedNum(backedNum);
        uploadAllFormSumInfo.setOriginalNum(unReportedNum);
        uploadAllFormSumInfo.setUploadedNum(reportedNum);
        uploadAllFormSumInfo.setRejectedNum(rejectedNum);
        uploadAllFormSumInfo.setConfirmedNum(confirmedNum);
    }

    private IBizObjectOperateResult<IProcessStatusWithOperation> getRangeRowStatus(List<String> rangeRowKeys, BatchQueryUpload batchQueryUpload) {
        if (rangeRowKeys == null || rangeRowKeys.isEmpty()) {
            return new BizObjectOperateResult();
        }
        EntityViewData dataTimeEntity = this.jTableParamService.getDataTimeEntity(batchQueryUpload.getFormSchemeKey());
        DimensionValueSet dimensionValueSet = DimensionValueSetUtil.getDimensionValueSet((Map)batchQueryUpload.getDimensionSet());
        String period = dimensionValueSet.getValue(dataTimeEntity.getDimensionName()).toString();
        WorkflowObjectType workflowObjectType = this.workflowSettingsService_2_0.queryTaskWorkflowObjectType(batchQueryUpload.getTaskKey());
        if (workflowObjectType.equals((Object)WorkflowObjectType.MAIN_DIMENSION) || workflowObjectType.equals((Object)WorkflowObjectType.MD_WITH_SFR)) {
            ProcessRunPara processRunPara = new ProcessRunPara();
            processRunPara.setTaskKey(batchQueryUpload.getTaskKey());
            processRunPara.setPeriod(period);
            return this.processQueryService.queryInstanceStateWithOperation((IProcessRunPara)processRunPara, this.buildUnitBusinessCollection(batchQueryUpload.getTaskKey(), period, dimensionValueSet, rangeRowKeys));
        }
        if (workflowObjectType.equals((Object)WorkflowObjectType.FORM)) {
            ProcessBatchRunPara batchRunPara = new ProcessBatchRunPara();
            batchRunPara.setTaskKey(batchQueryUpload.getTaskKey());
            batchRunPara.setPeriod(period);
            String formKey = batchQueryUpload.getFormKey();
            batchRunPara.setReportDimensions(this.buildFormReportDimension(rangeRowKeys, batchQueryUpload.getFormSchemeKey(), dimensionValueSet, formKey));
            IBusinessKeyCollection businessKeyCollection = this.processDimensionsBuilder.buildBusinessKeyCollection(batchRunPara);
            return this.processQueryService.queryInstanceStateWithOperation((IProcessRunPara)batchRunPara, businessKeyCollection);
        }
        if (workflowObjectType.equals((Object)WorkflowObjectType.FORM_GROUP)) {
            ProcessBatchRunPara batchRunPara = new ProcessBatchRunPara();
            batchRunPara.setTaskKey(batchQueryUpload.getTaskKey());
            batchRunPara.setPeriod(period);
            String formGroupKey = batchQueryUpload.getFormKey();
            batchRunPara.setReportDimensions(this.buildFormGroupReportDimension(rangeRowKeys, batchQueryUpload.getFormSchemeKey(), dimensionValueSet, formGroupKey));
            IBusinessKeyCollection businessKeyCollection = this.processDimensionsBuilder.buildBusinessKeyCollection(batchRunPara);
            return this.processQueryService.queryInstanceStateWithOperation((IProcessRunPara)batchRunPara, businessKeyCollection);
        }
        return new BizObjectOperateResult();
    }

    private IBusinessKeyCollection buildUnitBusinessCollection(String taskKey, String period, DimensionValueSet dimensionValueSet, List<String> rangeRowKeys) {
        DataDimension periodDimension = this.reportDimensionHelper.getReportPeriodDimension(taskKey);
        String periodDimensionName = this.reportDimensionHelper.getDimensionName(periodDimension);
        ProcessBatchRunPara runPara = new ProcessBatchRunPara();
        runPara.setTaskKey(taskKey);
        runPara.setPeriod(dimensionValueSet.getValue("DATATIME").toString());
        runPara.setReportDimensions(new HashSet());
        DataDimension unitDimension = this.reportDimensionHelper.getReportUnitDimension(taskKey);
        String unitDimensionName = this.reportDimensionHelper.getDimensionName(unitDimension);
        ProcessRangeDims unitDims = new ProcessRangeDims();
        unitDims.setDimensionName(unitDimensionName);
        unitDims.setRangeDims(rangeRowKeys);
        unitDims.setRangeType(EProcessRangeDimType.RANGE);
        unitDims.setDimensionKey(unitDimension.getDimKey());
        runPara.getReportDimensions().add(unitDims);
        ProcessRangeDims periodDim = new ProcessRangeDims();
        periodDim.setDimensionKey(periodDimension.getDimKey());
        periodDim.setRangeType(EProcessRangeDimType.ONE);
        periodDim.setDimensionValue(dimensionValueSet.getValue(periodDimensionName).toString());
        periodDim.setDimensionName(periodDimensionName);
        runPara.getReportDimensions().add(periodDim);
        return this.processDimensionsBuilder.buildBusinessKeyCollection(runPara);
    }

    private IBizObjectOperateResult<IProcessStatus> getRangeRowWithFormStatus(List<String> rangeRowKeys, List<String> rangeFormKeys, BatchQueryUpload batchQueryUpload) {
        if (rangeRowKeys == null || rangeRowKeys.isEmpty() || rangeFormKeys == null || rangeFormKeys.isEmpty()) {
            return new BizObjectOperateResult();
        }
        EntityViewData dataTimeEntity = this.jTableParamService.getDataTimeEntity(batchQueryUpload.getFormSchemeKey());
        DimensionValueSet dimensionValueSet = DimensionValueSetUtil.getDimensionValueSet((Map)batchQueryUpload.getDimensionSet());
        String period = dimensionValueSet.getValue(dataTimeEntity.getDimensionName()).toString();
        ProcessBatchRunPara batchRunPara = new ProcessBatchRunPara();
        batchRunPara.setTaskKey(batchQueryUpload.getTaskKey());
        batchRunPara.setPeriod(period);
        batchRunPara.setReportDimensions(this.buildFormsReportDimension(rangeRowKeys, batchQueryUpload.getFormSchemeKey(), dimensionValueSet, rangeFormKeys));
        IBusinessKeyCollection businessKeyCollection = this.processDimensionsBuilder.buildBusinessKeyCollection(batchRunPara);
        return this.processQueryService.queryInstanceState((IProcessRunPara)batchRunPara, businessKeyCollection);
    }

    private IBizObjectOperateResult<IProcessStatus> getRangeRowWithFormGroupStatus(List<String> rangeRowKeys, List<String> rangeFormGroupKeys, BatchQueryUpload batchQueryUpload) {
        EntityViewData dataTimeEntity = this.jTableParamService.getDataTimeEntity(batchQueryUpload.getFormSchemeKey());
        DimensionValueSet dimensionValueSet = DimensionValueSetUtil.getDimensionValueSet((Map)batchQueryUpload.getDimensionSet());
        String period = dimensionValueSet.getValue(dataTimeEntity.getDimensionName()).toString();
        ProcessBatchRunPara batchRunPara = new ProcessBatchRunPara();
        batchRunPara.setTaskKey(batchQueryUpload.getTaskKey());
        batchRunPara.setPeriod(period);
        batchRunPara.setReportDimensions(this.buildFormGroupsReportDimension(rangeRowKeys, batchQueryUpload.getFormSchemeKey(), dimensionValueSet, rangeFormGroupKeys));
        IBusinessKeyCollection businessKeyCollection = this.processDimensionsBuilder.buildBusinessKeyCollection(batchRunPara);
        return this.processQueryService.queryInstanceState((IProcessRunPara)batchRunPara, businessKeyCollection);
    }

    private Set<ProcessRangeDims> buildFormReportDimension(List<String> rangeRowKeys, String formSchemeKey, DimensionValueSet dimensionValueSet, String formKey) {
        FormSchemeDefine formSchemeDefine = this.runTimeViewController.getFormScheme(formSchemeKey);
        String entityId = this.getEntityCaliber(formSchemeDefine);
        LinkedHashSet<ProcessRangeDims> rangeDims = new LinkedHashSet<ProcessRangeDims>();
        String dimensionName = this.entityMetaService.queryEntity(entityId).getDimensionName();
        ProcessRangeDims entityRangeDims = new ProcessRangeDims();
        entityRangeDims.setDimensionName(dimensionName);
        entityRangeDims.setDimensionKey(entityId);
        entityRangeDims.setRangeType(EProcessRangeDimType.RANGE);
        entityRangeDims.setRangeDims(rangeRowKeys);
        rangeDims.add(entityRangeDims);
        ProcessRangeDims formRangeDims = new ProcessRangeDims();
        formRangeDims.setDimensionName(EProcessDimensionName.PROCESS_FORM.dimName);
        formRangeDims.setDimensionKey(EProcessDimensionName.PROCESS_FORM.dimName);
        formRangeDims.setRangeType(EProcessRangeDimType.ONE);
        formRangeDims.setDimensionValue(formKey);
        rangeDims.add(formRangeDims);
        Object adjustDim = dimensionValueSet.getValue("ADJUST");
        if (adjustDim != null) {
            ProcessRangeDims adjustOneDims = new ProcessRangeDims();
            adjustOneDims.setDimensionName("ADJUST");
            adjustOneDims.setDimensionKey("ADJUST");
            adjustOneDims.setRangeType(EProcessRangeDimType.ONE);
            adjustOneDims.setDimensionValue(adjustDim.toString());
            rangeDims.add(adjustOneDims);
        }
        return rangeDims;
    }

    private Set<ProcessRangeDims> buildFormsReportDimension(List<String> rangeRowKeys, String formSchemeKey, DimensionValueSet dimensionValueSet, List<String> rangeFormKeys) {
        FormSchemeDefine formSchemeDefine = this.runTimeViewController.getFormScheme(formSchemeKey);
        String entityId = this.getEntityCaliber(formSchemeDefine);
        LinkedHashSet<ProcessRangeDims> rangeDims = new LinkedHashSet<ProcessRangeDims>();
        String dimensionName = this.entityMetaService.queryEntity(entityId).getDimensionName();
        ProcessRangeDims entityRangeDims = new ProcessRangeDims();
        entityRangeDims.setDimensionName(dimensionName);
        entityRangeDims.setDimensionKey(entityId);
        entityRangeDims.setRangeType(EProcessRangeDimType.RANGE);
        entityRangeDims.setRangeDims(rangeRowKeys);
        rangeDims.add(entityRangeDims);
        ProcessRangeDims formRangeDims = new ProcessRangeDims();
        formRangeDims.setDimensionName(EProcessDimensionName.PROCESS_FORM.dimName);
        formRangeDims.setDimensionKey(EProcessDimensionName.PROCESS_FORM.dimName);
        formRangeDims.setRangeType(EProcessRangeDimType.RANGE);
        formRangeDims.setRangeDims(rangeFormKeys);
        rangeDims.add(formRangeDims);
        Object adjustDim = dimensionValueSet.getValue("ADJUST");
        if (adjustDim != null) {
            ProcessRangeDims adjustOneDims = new ProcessRangeDims();
            adjustOneDims.setDimensionName("ADJUST");
            adjustOneDims.setDimensionKey("ADJUST");
            adjustOneDims.setRangeType(EProcessRangeDimType.ONE);
            adjustOneDims.setDimensionValue(adjustDim.toString());
            rangeDims.add(adjustOneDims);
        }
        return rangeDims;
    }

    private Set<ProcessRangeDims> buildFormGroupReportDimension(List<String> rangeRowKeys, String formSchemeKey, DimensionValueSet dimensionValueSet, String formGroupKey) {
        FormSchemeDefine formSchemeDefine = this.runTimeViewController.getFormScheme(formSchemeKey);
        String entityId = this.getEntityCaliber(formSchemeDefine);
        LinkedHashSet<ProcessRangeDims> rangeDims = new LinkedHashSet<ProcessRangeDims>();
        String dimensionName = this.entityMetaService.queryEntity(entityId).getDimensionName();
        ProcessRangeDims entityRangeDims = new ProcessRangeDims();
        entityRangeDims.setDimensionName(dimensionName);
        entityRangeDims.setDimensionKey(entityId);
        entityRangeDims.setRangeType(EProcessRangeDimType.RANGE);
        entityRangeDims.setRangeDims(rangeRowKeys);
        rangeDims.add(entityRangeDims);
        ProcessRangeDims formRangeDims = new ProcessRangeDims();
        formRangeDims.setDimensionName(EProcessDimensionName.PROCESS_GROUP.dimName);
        formRangeDims.setDimensionKey(EProcessDimensionName.PROCESS_GROUP.dimName);
        formRangeDims.setRangeType(EProcessRangeDimType.ONE);
        formRangeDims.setDimensionValue(formGroupKey);
        rangeDims.add(formRangeDims);
        Object adjustDim = dimensionValueSet.getValue("ADJUST");
        if (adjustDim != null) {
            ProcessRangeDims adjustOneDims = new ProcessRangeDims();
            adjustOneDims.setDimensionName("ADJUST");
            adjustOneDims.setDimensionKey("ADJUST");
            adjustOneDims.setRangeType(EProcessRangeDimType.ONE);
            adjustOneDims.setDimensionValue(adjustDim.toString());
            rangeDims.add(adjustOneDims);
        }
        return rangeDims;
    }

    private Set<ProcessRangeDims> buildFormGroupsReportDimension(List<String> rangeRowKeys, String formSchemeKey, DimensionValueSet dimensionValueSet, List<String> formGroupKeys) {
        FormSchemeDefine formSchemeDefine = this.runTimeViewController.getFormScheme(formSchemeKey);
        String entityId = this.getEntityCaliber(formSchemeDefine);
        LinkedHashSet<ProcessRangeDims> rangeDims = new LinkedHashSet<ProcessRangeDims>();
        String dimensionName = this.entityMetaService.queryEntity(entityId).getDimensionName();
        ProcessRangeDims entityRangeDims = new ProcessRangeDims();
        entityRangeDims.setDimensionName(dimensionName);
        entityRangeDims.setDimensionKey(entityId);
        entityRangeDims.setRangeType(EProcessRangeDimType.RANGE);
        entityRangeDims.setRangeDims(rangeRowKeys);
        rangeDims.add(entityRangeDims);
        ProcessRangeDims formRangeDims = new ProcessRangeDims();
        formRangeDims.setDimensionName(EProcessDimensionName.PROCESS_GROUP.dimName);
        formRangeDims.setDimensionKey(EProcessDimensionName.PROCESS_GROUP.dimName);
        formRangeDims.setRangeType(EProcessRangeDimType.RANGE);
        formRangeDims.setRangeDims(formGroupKeys);
        rangeDims.add(formRangeDims);
        Object adjustDim = dimensionValueSet.getValue("ADJUST");
        if (adjustDim != null) {
            ProcessRangeDims adjustOneDims = new ProcessRangeDims();
            adjustOneDims.setDimensionName("ADJUST");
            adjustOneDims.setDimensionKey("ADJUST");
            adjustOneDims.setRangeType(EProcessRangeDimType.ONE);
            adjustOneDims.setDimensionValue(adjustDim.toString());
            rangeDims.add(adjustOneDims);
        }
        return rangeDims;
    }

    private IEntityTable getRangeEntityTable(String entityId, String period, String periodView, String unitCode) {
        String dimensionName = this.entityMetaService.queryEntity(entityId).getDimensionName();
        DimensionValueSet dimensionValueSet = new DimensionValueSet();
        dimensionValueSet.setValue("DATATIME", (Object)period);
        ExecutorContext executorContext = new ExecutorContext(this.dataDefinitionRuntimeController);
        executorContext.setPeriodView(periodView);
        IEntityQuery entityQuery = this.entityDataService.newEntityQuery();
        entityQuery.setEntityView(this.entityViewRunTimeController.buildEntityView(entityId));
        entityQuery.setMasterKeys(dimensionValueSet);
        entityQuery.setAuthorityOperations(AuthorityType.Read);
        entityQuery.markLeaf();
        entityQuery.lazyQuery();
        entityQuery.sorted(true);
        try {
            if (!ALL_UNIT_KEY.equals(unitCode)) {
                TreeRangeQuery rangeQuery = new TreeRangeQuery();
                rangeQuery.setParentKey(Collections.singletonList(unitCode));
                return entityQuery.executeRangeBuild((IContext)executorContext, (RangeQuery)rangeQuery);
            }
            return entityQuery.executeReader((IContext)executorContext);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isConfirmBeforeUpload(FormSchemeDefine formSchemeDefine) {
        String workflowId;
        WorkFlowDefine workFlowDefine;
        WorkflowSettingDefine settingDefine;
        LinkedHashMap nodeIdToActions = new LinkedHashMap();
        String workflowEngine = this.workflowSettingsService_2_0.queryTaskWorkflowEngine(formSchemeDefine.getTaskKey());
        if (workflowEngine.equals("jiuqi.nr.customprocessengine") && (settingDefine = this.workflowSettingService_1_0.getWorkflowDefineByFormSchemeKey(formSchemeDefine.getKey())) != null && settingDefine.getId() != null && (workFlowDefine = this.customWorkFolwService.getWorkFlowDefineByID(workflowId = settingDefine.getWorkflowId(), 1)) != null && workFlowDefine.getId() != null) {
            List workflowLines = this.customWorkFolwService.getWorkflowLinesByLinkid(workFlowDefine.getLinkid());
            if (workflowLines != null && workflowLines.size() > 0) {
                WorkFlowLine startEvent = workflowLines.stream().filter(e -> e.getBeforeNodeID().contains("StartEvent")).findAny().get();
                String afterNodeID = startEvent.getAfterNodeID();
                int i = 0;
                this.overviewUtil.buildNodeToActions(workflowLines, afterNodeID, workFlowDefine.getLinkid(), nodeIdToActions, i);
            }
            return this.overviewUtil.calculateOrder(nodeIdToActions);
        }
        return false;
    }

    public String getFormSchemeKey(String taskId, String period) {
        SchemePeriodLinkDefine schemePeriodLinkDefine = this.runTimeViewController.getSchemePeriodLinkByPeriodAndTask(period, taskId);
        return schemePeriodLinkDefine == null ? null : schemePeriodLinkDefine.getSchemeKey();
    }

    private String getEntityCaliber(FormSchemeDefine formSchemeDefine) {
        String entityCaliber = DsContextHolder.getDsContext().getContextEntityId();
        if (entityCaliber == null) {
            entityCaliber = formSchemeDefine.getDw();
        }
        return entityCaliber;
    }
}

