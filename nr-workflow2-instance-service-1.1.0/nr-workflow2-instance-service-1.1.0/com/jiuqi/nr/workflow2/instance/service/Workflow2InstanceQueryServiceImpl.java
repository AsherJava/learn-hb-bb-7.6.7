/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.nr.bpm.instance.bean.GridDataItem
 *  com.jiuqi.nr.bpm.instance.bean.GridDataResult
 *  com.jiuqi.nr.bpm.instance.bean.QueryGridDataParam
 *  com.jiuqi.nr.bpm.instance.bean.ReportDataParam
 *  com.jiuqi.nr.bpm.instance.bean.ReportDataResult
 *  com.jiuqi.nr.bpm.instance.bean.WorkflowDefine
 *  com.jiuqi.nr.bpm.instance.bean.WorkflowRelation
 *  com.jiuqi.nr.bpm.instance.service.WorkflowInstanceService
 *  com.jiuqi.nr.context.cxt.DsContextHolder
 *  com.jiuqi.nr.datascheme.api.DataDimension
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.definition.api.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormGroupDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.facade.TaskOrgLinkDefine
 *  com.jiuqi.nr.definition.internal.impl.WorkFlowType
 *  com.jiuqi.nr.definition.internal.stream.param.TaskOrgLinkListStream
 *  com.jiuqi.nr.entity.engine.intf.IEntityItem
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.nr.period.modal.IPeriodEntity
 *  com.jiuqi.nr.workflow2.engine.core.process.defintion.IProcessDefinition
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBizObjectOperateResult
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKeyCollection
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObject
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IFormGroupObject
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IFormObject
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IOperateResult
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IProcessInstance
 *  com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsDO
 *  com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsService
 *  com.jiuqi.nr.workflow2.engine.core.settings.enumeration.WorkflowObjectType
 *  com.jiuqi.nr.workflow2.engine.core.settings.utils.DefaultEngineVersionJudge
 *  com.jiuqi.nr.workflow2.service.IProcessMetaDataService
 *  com.jiuqi.nr.workflow2.service.IProcessQueryService
 *  com.jiuqi.nr.workflow2.service.dimension.EProcessDimensionName
 *  com.jiuqi.nr.workflow2.service.helper.IProcessDimensionsBuilder
 *  com.jiuqi.nr.workflow2.service.para.EProcessRangeDimType
 *  com.jiuqi.nr.workflow2.service.para.IProcessRunPara
 *  com.jiuqi.nr.workflow2.service.para.ProcessBatchRunPara
 *  com.jiuqi.nr.workflow2.service.para.ProcessRangeDims
 */
package com.jiuqi.nr.workflow2.instance.service;

import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.nr.bpm.instance.bean.GridDataItem;
import com.jiuqi.nr.bpm.instance.bean.GridDataResult;
import com.jiuqi.nr.bpm.instance.bean.QueryGridDataParam;
import com.jiuqi.nr.bpm.instance.bean.ReportDataParam;
import com.jiuqi.nr.bpm.instance.bean.ReportDataResult;
import com.jiuqi.nr.bpm.instance.bean.WorkflowDefine;
import com.jiuqi.nr.bpm.instance.bean.WorkflowRelation;
import com.jiuqi.nr.bpm.instance.service.WorkflowInstanceService;
import com.jiuqi.nr.context.cxt.DsContextHolder;
import com.jiuqi.nr.datascheme.api.DataDimension;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.definition.api.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormGroupDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.facade.TaskOrgLinkDefine;
import com.jiuqi.nr.definition.internal.impl.WorkFlowType;
import com.jiuqi.nr.definition.internal.stream.param.TaskOrgLinkListStream;
import com.jiuqi.nr.entity.engine.intf.IEntityItem;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nr.period.modal.IPeriodEntity;
import com.jiuqi.nr.workflow2.engine.core.process.defintion.IProcessDefinition;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBizObjectOperateResult;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKeyCollection;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObject;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IFormGroupObject;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IFormObject;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IOperateResult;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IProcessInstance;
import com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsDO;
import com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsService;
import com.jiuqi.nr.workflow2.engine.core.settings.enumeration.WorkflowObjectType;
import com.jiuqi.nr.workflow2.engine.core.settings.utils.DefaultEngineVersionJudge;
import com.jiuqi.nr.workflow2.instance.context.InstanceDetailDataContext;
import com.jiuqi.nr.workflow2.instance.context.InstanceTableDataContext;
import com.jiuqi.nr.workflow2.instance.entity.InfoItem;
import com.jiuqi.nr.workflow2.instance.entity.InstanceFormDetailData;
import com.jiuqi.nr.workflow2.instance.entity.InstanceFormDetailDataImpl;
import com.jiuqi.nr.workflow2.instance.entity.InstanceTableData;
import com.jiuqi.nr.workflow2.instance.entity.InstanceTableDataImpl;
import com.jiuqi.nr.workflow2.instance.entity.PageInfo;
import com.jiuqi.nr.workflow2.instance.entity.PeriodComponentParam;
import com.jiuqi.nr.workflow2.instance.entity.StatusData;
import com.jiuqi.nr.workflow2.instance.entity.TableDataFilterInfo;
import com.jiuqi.nr.workflow2.instance.entity.WorkflowInfo;
import com.jiuqi.nr.workflow2.instance.enumeration.TableDataQueryMode;
import com.jiuqi.nr.workflow2.instance.service.Workflow2InstanceQueryService;
import com.jiuqi.nr.workflow2.instance.util.InstanceUtil;
import com.jiuqi.nr.workflow2.instance.vo.InstanceInitDataVO;
import com.jiuqi.nr.workflow2.instance.vo.InstanceInitDataVOImpl;
import com.jiuqi.nr.workflow2.instance.vo.InstanceTableDataVO;
import com.jiuqi.nr.workflow2.instance.vo.InstanceTableDataVOImpl;
import com.jiuqi.nr.workflow2.service.IProcessMetaDataService;
import com.jiuqi.nr.workflow2.service.IProcessQueryService;
import com.jiuqi.nr.workflow2.service.dimension.EProcessDimensionName;
import com.jiuqi.nr.workflow2.service.helper.IProcessDimensionsBuilder;
import com.jiuqi.nr.workflow2.service.para.EProcessRangeDimType;
import com.jiuqi.nr.workflow2.service.para.IProcessRunPara;
import com.jiuqi.nr.workflow2.service.para.ProcessBatchRunPara;
import com.jiuqi.nr.workflow2.service.para.ProcessRangeDims;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.slf4j.LoggerFactory;

public class Workflow2InstanceQueryServiceImpl
implements Workflow2InstanceQueryService {
    private static final int STATUS_HAS_START = 1;
    private static final int STATUS_NO_START = 2;
    private static final int STATUS_PART_START = 3;
    private IRunTimeViewController runTimeViewController;
    private WorkflowSettingsService workflowSettingsService;
    private IProcessQueryService processQueryService;
    private IProcessDimensionsBuilder processDimensionsBuilder;
    private IProcessMetaDataService processMetaDataService;
    private PeriodEngineService periodEngineService;
    private IRuntimeDataSchemeService dataSchemeService;
    private IEntityMetaService entityMetaService;
    private InstanceUtil instanceUtil;
    private WorkflowInstanceService workflowInstanceService;
    private DefaultEngineVersionJudge defaultEngineVersionJudge;
    private boolean isTaskDesignVersion2_0;

    public void setRunTimeViewController(IRunTimeViewController runTimeViewController) {
        this.runTimeViewController = runTimeViewController;
    }

    public void setWorkflowSettingsService(WorkflowSettingsService workflowSettingsService) {
        this.workflowSettingsService = workflowSettingsService;
    }

    public void setProcessQueryService(IProcessQueryService processQueryService) {
        this.processQueryService = processQueryService;
    }

    public void setProcessDimensionsBuilder(IProcessDimensionsBuilder processDimensionsBuilder) {
        this.processDimensionsBuilder = processDimensionsBuilder;
    }

    public void setProcessMetaDataService(IProcessMetaDataService processMetaDataService) {
        this.processMetaDataService = processMetaDataService;
    }

    public void setPeriodEngineService(PeriodEngineService periodEngineService) {
        this.periodEngineService = periodEngineService;
    }

    public void setDataSchemeService(IRuntimeDataSchemeService dataSchemeService) {
        this.dataSchemeService = dataSchemeService;
    }

    public void setEntityMetaService(IEntityMetaService entityMetaService) {
        this.entityMetaService = entityMetaService;
    }

    public void setDefaultEngineVersionUtil(DefaultEngineVersionJudge defaultEngineVersionJudge) {
        this.defaultEngineVersionJudge = defaultEngineVersionJudge;
    }

    public void setWorkflowInstanceService(WorkflowInstanceService workflowInstanceService) {
        this.workflowInstanceService = workflowInstanceService;
    }

    public void setInstanceUtil(InstanceUtil instanceUtil) {
        this.instanceUtil = instanceUtil;
    }

    public void setTaskDesignVersion2_0(boolean taskDesignVersion2_0) {
        this.isTaskDesignVersion2_0 = taskDesignVersion2_0;
    }

    @Override
    public InstanceTableDataVO queryTableData(InstanceTableDataContext context) {
        if (this.defaultEngineVersionJudge.isTaskAndEngineVersion_1_0(context.getTaskKey())) {
            QueryGridDataParam param = new QueryGridDataParam();
            param.setContextEntityId(context.getContextEntityId());
            param.setContextFilterExpression(context.getContextFilterExpression());
            param.setTaskKey(context.getTaskKey());
            param.setPeriod(context.getPeriod());
            param.setAdjust(context.getAdjust());
            param.setCurrentUnitKey(context.getFilterInfo().getLocateUnitKey());
            param.setSearchKeys(context.getFilterInfo().getFilterTitle());
            param.setStates(context.getFilterInfo().getFilterState());
            param.setAllSubordinate(context.getFilterInfo().getQueryMode().equals((Object)TableDataQueryMode.ALL));
            param.setPageNum(context.getPageInfo().getCurrentPage());
            param.setPageSize(context.getPageInfo().getPageSize());
            GridDataResult gridDataResult = this.workflowInstanceService.queryGridDatas(param);
            List gridData = gridDataResult.getGridDatas();
            ArrayList<InstanceTableData> tableData = new ArrayList<InstanceTableData>();
            for (GridDataItem gridDataItem : gridData) {
                InstanceTableDataImpl instanceTableData = new InstanceTableDataImpl();
                instanceTableData.setKey(gridDataItem.getKey());
                instanceTableData.setCode(gridDataItem.getCode());
                instanceTableData.setTitle(gridDataItem.getTitle());
                instanceTableData.setState(gridDataItem.getState());
                Date startTime = gridDataItem.getStartTime();
                if (startTime != null) {
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(gridDataItem.getStartTime());
                    instanceTableData.setStartTime(calendar);
                }
                tableData.add(instanceTableData);
            }
            InstanceTableDataVOImpl instanceTableDataVO = new InstanceTableDataVOImpl();
            instanceTableDataVO.setCount(gridDataResult.getCount());
            instanceTableDataVO.setTableData(tableData);
            return instanceTableDataVO;
        }
        List<IEntityRow> rangeEntityRows = this.getRangeEntityRows(context);
        PageInfo pageInfo = context.getPageInfo();
        int currentPage = pageInfo.getCurrentPage();
        int pageSize = pageInfo.getPageSize();
        List<IEntityRow> pageRangeEntityRows = rangeEntityRows.subList(currentPage * pageSize, Math.min(currentPage * pageSize + pageSize, rangeEntityRows.size()));
        ProcessBatchRunPara batchRunPara = new ProcessBatchRunPara();
        batchRunPara.setTaskKey(context.getTaskKey());
        batchRunPara.setPeriod(context.getPeriod());
        batchRunPara.setReportDimensions(this.buildUnitReportDimension(context, pageRangeEntityRows));
        IBusinessKeyCollection businessKeyCollection = this.processDimensionsBuilder.buildBusinessKeyCollection(batchRunPara);
        IBizObjectOperateResult instanceOperateResult = this.processQueryService.queryInstances((IProcessRunPara)batchRunPara, businessKeyCollection);
        InstanceTableDataVOImpl tableDataVO = this.buildTableData((IBizObjectOperateResult<IProcessInstance>)instanceOperateResult, context.getTaskKey(), pageRangeEntityRows);
        tableDataVO.setCount(rangeEntityRows.size());
        return this.filterTableData(tableDataVO, context.getFilterInfo());
    }

    @Override
    public List<InstanceFormDetailData> queryFormDetailData(InstanceDetailDataContext context) {
        if (this.defaultEngineVersionJudge.isTaskAndEngineVersion_1_0(context.getTaskKey())) {
            ReportDataParam param = new ReportDataParam();
            param.setContextEntityId(context.getContextEntityId());
            param.setContextFilterExpression(context.getContextFilterExpression());
            param.setTaskKey(context.getTaskKey());
            param.setPeriod(context.getPeriod());
            param.setUnitId(context.getLocateUnitKey());
            List reportDataResults = this.workflowInstanceService.queryReportDataResult(param);
            ArrayList<InstanceFormDetailData> instanceFormDetailDataList = new ArrayList<InstanceFormDetailData>();
            for (ReportDataResult reportDataResult : reportDataResults) {
                InstanceFormDetailDataImpl data = new InstanceFormDetailDataImpl();
                data.setKey(reportDataResult.getId());
                data.setCode(reportDataResult.getId());
                data.setTitle(reportDataResult.getTitle());
                data.setState(reportDataResult.getStartStatus());
                Date startTime = reportDataResult.getStartTime();
                if (startTime != null) {
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(reportDataResult.getStartTime());
                    data.setStartTime(calendar);
                } else {
                    data.setStartTime(null);
                }
                data.setChildren(this.transferToFormChildrenData(reportDataResult.getChildren()));
                instanceFormDetailDataList.add(data);
            }
            return instanceFormDetailDataList;
        }
        ArrayList<InstanceFormDetailData> detailDataList = new ArrayList<InstanceFormDetailData>();
        WorkflowObjectType workflowObjectType = this.workflowSettingsService.queryTaskWorkflowObjectType(context.getTaskKey());
        if (workflowObjectType == null) {
            LoggerFactory.getLogger(this.getClass()).error("queryReportDataResult()\u65b9\u6cd5\u4e2d workflowObjectType is null");
            return detailDataList;
        }
        String formSchemeKey = this.instanceUtil.getFormSchemeKey(context.getTaskKey(), context.getPeriod());
        if (formSchemeKey == null) {
            LoggerFactory.getLogger(this.getClass()).error("queryReportDataResult()\u65b9\u6cd5\u4e2d formSchemeKey is null");
            return detailDataList;
        }
        List formDefines = this.runTimeViewController.listFormByFormScheme(formSchemeKey);
        List formGroupDefines = this.runTimeViewController.listFormGroupByFormScheme(formSchemeKey);
        if (workflowObjectType.equals((Object)WorkflowObjectType.FORM)) {
            ProcessBatchRunPara batchRunPara = new ProcessBatchRunPara();
            batchRunPara.setTaskKey(context.getTaskKey());
            batchRunPara.setPeriod(context.getPeriod());
            batchRunPara.setReportDimensions(this.buildFormReportDimension(context, formDefines));
            IBusinessKeyCollection businessKeyCollection = this.processDimensionsBuilder.buildBusinessKeyCollection(batchRunPara);
            IBizObjectOperateResult instanceOperateResult = this.processQueryService.queryInstances((IProcessRunPara)batchRunPara, businessKeyCollection);
            Map<String, StatusData> formStatusMap = this.buildFormStatusMap((IBizObjectOperateResult<IProcessInstance>)instanceOperateResult);
            for (FormGroupDefine formGroupDefine : formGroupDefines) {
                InstanceFormDetailDataImpl detailData = new InstanceFormDetailDataImpl();
                detailData.setKey(formGroupDefine.getKey());
                detailData.setTitle(formGroupDefine.getTitle());
                detailData.setChildren(this.buildFormChildrenData(formStatusMap, formGroupDefine.getKey(), formSchemeKey));
                detailDataList.add(detailData);
            }
        } else if (workflowObjectType.equals((Object)WorkflowObjectType.FORM_GROUP)) {
            ProcessBatchRunPara batchRunPara = new ProcessBatchRunPara();
            batchRunPara.setTaskKey(context.getTaskKey());
            batchRunPara.setPeriod(context.getPeriod());
            batchRunPara.setReportDimensions(this.buildFormGroupReportDimension(context, formGroupDefines));
            IBusinessKeyCollection businessKeyCollection = this.processDimensionsBuilder.buildBusinessKeyCollection(batchRunPara);
            IBizObjectOperateResult instanceOperateResult = this.processQueryService.queryInstances((IProcessRunPara)batchRunPara, businessKeyCollection);
            Iterable instanceKeys = instanceOperateResult.getInstanceKeys();
            for (IBusinessObject instanceKey : instanceKeys) {
                int status;
                IFormGroupObject formGroupObject = (IFormGroupObject)instanceKey;
                String formGroupKey = formGroupObject.getFormGroupKey();
                IOperateResult processInstance = instanceOperateResult.getResult((Object)formGroupObject);
                Calendar startTime = null;
                if (processInstance.isSuccessful()) {
                    status = 1;
                    startTime = ((IProcessInstance)processInstance.getResult()).getStartTime();
                } else {
                    status = 2;
                }
                Optional<FormGroupDefine> targetFormGroup = formGroupDefines.stream().filter(formGroup -> formGroup.getKey().equals(formGroupKey)).findFirst();
                if (!targetFormGroup.isPresent()) continue;
                InstanceFormDetailDataImpl detailData = new InstanceFormDetailDataImpl();
                detailData.setKey(targetFormGroup.get().getKey());
                detailData.setTitle(targetFormGroup.get().getTitle());
                detailData.setState(status);
                detailData.setStartTime(startTime);
                detailDataList.add(detailData);
            }
        } else {
            return detailDataList;
        }
        return detailDataList;
    }

    @Override
    public InstanceInitDataVO initInstanceData(String taskKey, String period) {
        Optional<SchemePeriodLinkDefine> target;
        InstanceInitDataVOImpl initData = new InstanceInitDataVOImpl();
        TaskDefine taskDefine = this.runTimeViewController.getTask(taskKey);
        TaskOrgLinkListStream taskOrgLinkListStream = this.runTimeViewController.listTaskOrgLinkStreamByTask(taskKey);
        List taskOrgLinkDefines = taskOrgLinkListStream.auth().getList();
        Optional entityCaliber = taskOrgLinkDefines.stream().findFirst();
        String dimensionName = this.instanceUtil.getDimensionName(taskDefine.getDw());
        String curPeriod = period;
        if (curPeriod != null && !curPeriod.isEmpty()) {
            PeriodWrapper periodWrapper = new PeriodWrapper(curPeriod);
            IPeriodEntity periodEntity = this.periodEngineService.getPeriodAdapter().getPeriodEntity(taskDefine.getDateTime());
            if (periodWrapper.getType() != periodEntity.getType().type()) {
                curPeriod = this.periodEngineService.getPeriodAdapter().getPeriodProvider(taskDefine.getDateTime()).getCurPeriod().getCode();
            }
        } else {
            curPeriod = this.periodEngineService.getPeriodAdapter().getPeriodProvider(taskDefine.getDateTime()).getCurPeriod().getCode();
        }
        String formSchemeKey = this.instanceUtil.getFormSchemeKey(taskKey, curPeriod);
        if (formSchemeKey == null || formSchemeKey.isEmpty()) {
            curPeriod = this.periodEngineService.getPeriodAdapter().getPeriodProvider(taskDefine.getDateTime()).getCurPeriod().getCode();
            formSchemeKey = this.instanceUtil.getFormSchemeKey(taskKey, curPeriod);
        }
        if ((formSchemeKey == null || formSchemeKey.isEmpty()) && (target = this.runTimeViewController.listSchemePeriodLinkByTask(taskKey).stream().max(Comparator.comparing(SchemePeriodLinkDefine::getPeriodKey))).isPresent()) {
            SchemePeriodLinkDefine schemePeriodLinkDefine = target.get();
            curPeriod = schemePeriodLinkDefine.getPeriodKey();
            formSchemeKey = schemePeriodLinkDefine.getSchemeKey();
        }
        String adjust = null;
        List dataSchemeDimension = this.dataSchemeService.getDataSchemeDimension(taskDefine.getDataScheme());
        if (dataSchemeDimension != null && !dataSchemeDimension.isEmpty()) {
            for (DataDimension dimension : dataSchemeDimension) {
                String dimName = this.instanceUtil.getDimensionName(dimension.getDimKey());
                if (!"ADJUST".equals(dimName)) continue;
                adjust = dimension.getDimKey();
                break;
            }
        }
        WorkflowInfo workflow = new WorkflowInfo();
        workflow.setShowEditWorkflowButton(this.defaultEngineVersionJudge.isTaskVersion_1_0(taskKey));
        if (this.defaultEngineVersionJudge.isTaskAndEngineVersion_1_0(taskKey)) {
            List workflowDefines = this.workflowInstanceService.queryWorkflowDefines();
            WorkflowRelation workflowRelation = new WorkflowRelation();
            workflowRelation.setTaskKey(taskKey);
            workflowRelation.setPeriod(curPeriod);
            String workflowDefineKey = this.workflowInstanceService.queryWorkflowKey(workflowRelation);
            String workflowDefineTitle = "\u9ed8\u8ba4\u6d41\u7a0b";
            if (workflowDefines != null && !workflowDefines.isEmpty()) {
                for (WorkflowDefine workflowDefine : workflowDefines) {
                    if (!workflowDefine.getKey().equals(workflowDefineKey)) continue;
                    workflowDefineTitle = workflowDefine.getTitle();
                    break;
                }
            }
            InfoItem workflowDefine = new InfoItem();
            workflowDefine.setKey(workflowDefineKey);
            workflowDefine.setTitle(workflowDefineTitle);
            workflow.setWorkflowDefine(workflowDefine);
            workflow.setWorkflowObjectType(this.transferToWorkflowObjectType(taskDefine.getFlowsSetting().getWordFlowType()));
        } else {
            WorkflowSettingsDO workflowSettings = this.workflowSettingsService.queryWorkflowSettings(taskKey);
            IProcessDefinition processDefinition = this.processMetaDataService.getProcessDefinition(taskKey);
            InfoItem workflowDefine = new InfoItem();
            workflowDefine.setKey(processDefinition.getId());
            workflowDefine.setTitle(processDefinition.getTitle());
            workflow.setWorkflowDefine(workflowDefine);
            workflow.setWorkflowObjectType(workflowSettings.getWorkflowObjectType());
        }
        FormSchemeDefine formSchemeDefine = this.runTimeViewController.getFormScheme(formSchemeKey);
        PeriodComponentParam periodComponentParam = new PeriodComponentParam();
        periodComponentParam.setFormSchemeKey(formSchemeKey);
        periodComponentParam.setPeriodType(taskDefine.getPeriodType().name());
        if (formSchemeDefine != null) {
            periodComponentParam.setFromPeriod(formSchemeDefine.getFromPeriod() == null ? "1970Y0001" : formSchemeDefine.getFromPeriod());
            periodComponentParam.setToPeriod(formSchemeDefine.getToPeriod() == null ? "9999Y0001" : formSchemeDefine.getToPeriod());
        }
        initData.setEntityCaliber(entityCaliber.map(TaskOrgLinkDefine::getEntity).orElse(null));
        initData.setEntityDimensionName(dimensionName);
        initData.setCurPeriod(curPeriod);
        initData.setAdjust(adjust);
        initData.setWorkflow(workflow);
        initData.setPeriodComponentParam(periodComponentParam);
        return initData;
    }

    private List<IEntityRow> getRangeEntityRows(InstanceTableDataContext context) {
        TaskDefine taskDefine = this.runTimeViewController.getTask(context.getTaskKey());
        IEntityTable entityTable = this.instanceUtil.getEntityTable(this.getEntityCaliber(context.getTaskKey()), context.getPeriod(), taskDefine.getDateTime(), taskDefine.getFilterExpression());
        TableDataFilterInfo filterInfo = context.getFilterInfo();
        boolean isAllSubordinate = filterInfo.getQueryMode().equals((Object)TableDataQueryMode.ALL);
        String locateUnitKey = filterInfo.getLocateUnitKey();
        IEntityRow currentEntityRow = entityTable.findByEntityKey(locateUnitKey);
        ArrayList<IEntityRow> rangeEntityRows = new ArrayList<IEntityRow>();
        rangeEntityRows.add(currentEntityRow);
        rangeEntityRows.addAll(isAllSubordinate ? entityTable.getAllChildRows(locateUnitKey) : entityTable.getChildRows(locateUnitKey));
        return rangeEntityRows;
    }

    private Set<ProcessRangeDims> buildUnitReportDimension(InstanceTableDataContext context, List<IEntityRow> rangeEntityRows) {
        String entityId = this.getEntityCaliber(context.getTaskKey());
        LinkedHashSet<ProcessRangeDims> rangeDims = new LinkedHashSet<ProcessRangeDims>();
        String dimensionName = this.entityMetaService.queryEntity(entityId).getDimensionName();
        ProcessRangeDims entityRangeDims = new ProcessRangeDims();
        entityRangeDims.setDimensionName(dimensionName);
        entityRangeDims.setDimensionKey(entityId);
        entityRangeDims.setRangeType(EProcessRangeDimType.RANGE);
        entityRangeDims.setRangeDims(rangeEntityRows.stream().map(IEntityItem::getEntityKeyData).collect(Collectors.toList()));
        rangeDims.add(entityRangeDims);
        WorkflowObjectType workflowObjectType = this.workflowSettingsService.queryTaskWorkflowObjectType(context.getTaskKey());
        if (workflowObjectType.equals((Object)WorkflowObjectType.FORM) || workflowObjectType.equals((Object)WorkflowObjectType.MD_WITH_SFR)) {
            ProcessRangeDims formRangeDims = new ProcessRangeDims();
            formRangeDims.setDimensionName(EProcessDimensionName.PROCESS_FORM.dimName);
            formRangeDims.setDimensionKey(EProcessDimensionName.PROCESS_FORM.dimName);
            formRangeDims.setRangeType(EProcessRangeDimType.ALL);
            rangeDims.add(formRangeDims);
        } else if (workflowObjectType.equals((Object)WorkflowObjectType.FORM_GROUP)) {
            ProcessRangeDims formGroupRangeDims = new ProcessRangeDims();
            formGroupRangeDims.setDimensionName(EProcessDimensionName.PROCESS_GROUP.dimName);
            formGroupRangeDims.setDimensionKey(EProcessDimensionName.PROCESS_GROUP.dimName);
            formGroupRangeDims.setRangeType(EProcessRangeDimType.ALL);
            rangeDims.add(formGroupRangeDims);
        }
        String adjust = context.getAdjust();
        if (adjust != null && !adjust.isEmpty()) {
            ProcessRangeDims adjustOneDims = new ProcessRangeDims();
            adjustOneDims.setDimensionName("ADJUST");
            adjustOneDims.setDimensionKey("ADJUST");
            adjustOneDims.setRangeType(EProcessRangeDimType.ONE);
            adjustOneDims.setDimensionValue(context.getAdjust());
            rangeDims.add(adjustOneDims);
        }
        return rangeDims;
    }

    private Set<ProcessRangeDims> buildFormReportDimension(InstanceDetailDataContext context, List<FormDefine> formDefines) {
        String entityId = this.getEntityCaliber(context.getTaskKey());
        LinkedHashSet<ProcessRangeDims> rangeDims = new LinkedHashSet<ProcessRangeDims>();
        String dimensionName = this.entityMetaService.queryEntity(entityId).getDimensionName();
        ProcessRangeDims entityRangeDims = new ProcessRangeDims();
        entityRangeDims.setDimensionName(dimensionName);
        entityRangeDims.setDimensionKey(entityId);
        entityRangeDims.setRangeType(EProcessRangeDimType.ONE);
        entityRangeDims.setDimensionValue(context.getLocateUnitKey());
        rangeDims.add(entityRangeDims);
        ProcessRangeDims formRangeDims = new ProcessRangeDims();
        formRangeDims.setDimensionName(EProcessDimensionName.PROCESS_FORM.dimName);
        formRangeDims.setDimensionKey(EProcessDimensionName.PROCESS_FORM.dimName);
        formRangeDims.setRangeType(EProcessRangeDimType.RANGE);
        formRangeDims.setRangeDims(formDefines.stream().map(IBaseMetaItem::getKey).collect(Collectors.toList()));
        rangeDims.add(formRangeDims);
        String adjust = context.getAdjust();
        if (adjust != null && !adjust.isEmpty()) {
            ProcessRangeDims adjustOneDims = new ProcessRangeDims();
            adjustOneDims.setDimensionName("ADJUST");
            adjustOneDims.setDimensionKey("ADJUST");
            adjustOneDims.setRangeType(EProcessRangeDimType.ONE);
            adjustOneDims.setDimensionValue(context.getAdjust());
            rangeDims.add(adjustOneDims);
        }
        return rangeDims;
    }

    private Set<ProcessRangeDims> buildFormGroupReportDimension(InstanceDetailDataContext context, List<FormGroupDefine> formGroupDefines) {
        String entityId = this.getEntityCaliber(context.getTaskKey());
        LinkedHashSet<ProcessRangeDims> rangeDims = new LinkedHashSet<ProcessRangeDims>();
        String dimensionName = this.entityMetaService.queryEntity(entityId).getDimensionName();
        ProcessRangeDims entityRangeDims = new ProcessRangeDims();
        entityRangeDims.setDimensionName(dimensionName);
        entityRangeDims.setDimensionKey(entityId);
        entityRangeDims.setRangeType(EProcessRangeDimType.ONE);
        entityRangeDims.setDimensionValue(context.getLocateUnitKey());
        rangeDims.add(entityRangeDims);
        ProcessRangeDims formRangeDims = new ProcessRangeDims();
        formRangeDims.setDimensionName(EProcessDimensionName.PROCESS_GROUP.dimName);
        formRangeDims.setDimensionKey(EProcessDimensionName.PROCESS_GROUP.dimName);
        formRangeDims.setRangeType(EProcessRangeDimType.RANGE);
        formRangeDims.setRangeDims(formGroupDefines.stream().map(IBaseMetaItem::getKey).collect(Collectors.toList()));
        rangeDims.add(formRangeDims);
        String adjust = context.getAdjust();
        if (adjust != null && !adjust.isEmpty()) {
            ProcessRangeDims adjustOneDims = new ProcessRangeDims();
            adjustOneDims.setDimensionName("ADJUST");
            adjustOneDims.setDimensionKey("ADJUST");
            adjustOneDims.setRangeType(EProcessRangeDimType.ONE);
            adjustOneDims.setDimensionValue(context.getAdjust());
            rangeDims.add(adjustOneDims);
        }
        return rangeDims;
    }

    private InstanceTableDataVOImpl buildTableData(IBizObjectOperateResult<IProcessInstance> instanceOperateResult, String taskKey, List<IEntityRow> rangeEntityRows) {
        Map infoMap = rangeEntityRows.stream().collect(Collectors.toMap(IEntityItem::getEntityKeyData, Function.identity(), (v1, v2) -> v1));
        InstanceTableDataVOImpl tableData = new InstanceTableDataVOImpl();
        Iterable instanceKeys = instanceOperateResult.getInstanceKeys();
        ArrayList<InstanceTableData> tableDataList = new ArrayList<InstanceTableData>();
        WorkflowObjectType workflowObjectType = this.workflowSettingsService.queryTaskWorkflowObjectType(taskKey);
        if (workflowObjectType.equals((Object)WorkflowObjectType.MAIN_DIMENSION) || workflowObjectType.equals((Object)WorkflowObjectType.MD_WITH_SFR)) {
            for (IBusinessObject businessObject : instanceKeys) {
                int status;
                DimensionCombination dimensionCombination = businessObject.getDimensions();
                String unitValue = (String)dimensionCombination.getDWDimensionValue().getValue();
                IOperateResult processInstance = instanceOperateResult.getResult((Object)businessObject);
                Calendar startTime = null;
                if (processInstance.isSuccessful()) {
                    status = 1;
                    startTime = ((IProcessInstance)processInstance.getResult()).getStartTime();
                } else {
                    status = 2;
                }
                IEntityRow entityRow = (IEntityRow)infoMap.get(unitValue);
                if (entityRow == null) continue;
                InstanceTableDataImpl dataItem = new InstanceTableDataImpl();
                dataItem.setKey(entityRow.getEntityKeyData());
                dataItem.setCode(entityRow.getCode());
                dataItem.setTitle(entityRow.getTitle());
                dataItem.setState(status);
                dataItem.setStartTime(startTime);
                tableDataList.add(dataItem);
            }
        } else {
            LinkedHashMap<String, List> classifyMap = new LinkedHashMap<String, List>();
            for (IBusinessObject iBusinessObject : instanceKeys) {
                DimensionCombination dimensions = iBusinessObject.getDimensions();
                String unitValue = (String)dimensions.getDWDimensionValue().getValue();
                List resultList = classifyMap.computeIfAbsent(unitValue, k -> new ArrayList());
                resultList.add(instanceOperateResult.getResult((Object)iBusinessObject));
            }
            for (Map.Entry entry : ((HashMap)classifyMap).entrySet()) {
                String unitValue = (String)entry.getKey();
                List resultList = (List)entry.getValue();
                Calendar startTime = null;
                int total = resultList.size();
                int success = 0;
                for (IOperateResult result : resultList) {
                    if (!result.isSuccessful()) continue;
                    ++success;
                    startTime = ((IProcessInstance)result.getResult()).getStartTime();
                }
                int status = success == total ? 1 : (success > 0 ? 3 : 2);
                Optional<IEntityRow> targetEntityRow = rangeEntityRows.stream().filter(row -> row.getEntityKeyData().equals(unitValue)).findFirst();
                if (!targetEntityRow.isPresent()) continue;
                IEntityRow entityRow = targetEntityRow.get();
                InstanceTableDataImpl dataItem = new InstanceTableDataImpl();
                dataItem.setKey(entityRow.getEntityKeyData());
                dataItem.setCode(entityRow.getCode());
                dataItem.setTitle(entityRow.getTitle());
                dataItem.setState(status);
                dataItem.setStartTime(startTime);
                tableDataList.add(dataItem);
            }
        }
        tableData.setTableData(tableDataList);
        return tableData;
    }

    private InstanceTableDataVO filterTableData(InstanceTableDataVOImpl tableDataVO, TableDataFilterInfo filterInfo) {
        List<InstanceTableData> tableData = tableDataVO.getTableData();
        List<Integer> filterState = filterInfo.getFilterState();
        String filterTitle = filterInfo.getFilterTitle();
        if ((filterTitle == null || filterTitle.isEmpty()) && (filterState == null || filterState.isEmpty())) {
            return tableDataVO;
        }
        Iterator<InstanceTableData> iterator = tableData.iterator();
        while (iterator.hasNext()) {
            InstanceTableData tableDataItem = iterator.next();
            String title = tableDataItem.getTitle();
            if (filterTitle != null && !filterTitle.isEmpty() && !title.contains(filterTitle)) {
                iterator.remove();
                tableDataVO.setCount(tableDataVO.getCount() - 1);
            }
            int state = tableDataItem.getState();
            if (filterState == null || filterState.isEmpty() || filterState.contains(state)) continue;
            iterator.remove();
            tableDataVO.setCount(tableDataVO.getCount() - 1);
        }
        return tableDataVO;
    }

    private Map<String, StatusData> buildFormStatusMap(IBizObjectOperateResult<IProcessInstance> instanceOperateResult) {
        HashMap<String, StatusData> formStatusMap = new HashMap<String, StatusData>();
        Iterable instanceKeys = instanceOperateResult.getInstanceKeys();
        for (IBusinessObject instanceKey : instanceKeys) {
            int status;
            IFormObject formObject = (IFormObject)instanceKey;
            String formKey = formObject.getFormKey();
            IOperateResult processInstance = instanceOperateResult.getResult((Object)formObject);
            Calendar startTime = null;
            if (processInstance.isSuccessful()) {
                status = 1;
                startTime = ((IProcessInstance)processInstance.getResult()).getStartTime();
            } else {
                status = 2;
            }
            StatusData statusData = new StatusData();
            statusData.setStartStatus(status);
            statusData.setStartTime(startTime);
            formStatusMap.put(formKey, statusData);
        }
        return formStatusMap;
    }

    private List<InstanceFormDetailData> buildFormChildrenData(Map<String, StatusData> formStatusMap, String formGroupKey, String formSchemeKey) {
        ArrayList<InstanceFormDetailData> childrenData = new ArrayList<InstanceFormDetailData>();
        List formDefines = this.runTimeViewController.listFormByGroup(formGroupKey, formSchemeKey);
        if (formDefines == null || formDefines.isEmpty()) {
            return childrenData;
        }
        for (FormDefine formDefine : formDefines) {
            if (!formStatusMap.containsKey(formDefine.getKey())) continue;
            StatusData statusData = formStatusMap.get(formDefine.getKey());
            InstanceFormDetailDataImpl formDataResult = new InstanceFormDetailDataImpl();
            formDataResult.setKey(formDefine.getKey());
            formDataResult.setTitle(formDefine.getTitle());
            formDataResult.setState(statusData.getStartStatus());
            formDataResult.setStartTime(statusData.getStartTime());
            childrenData.add(formDataResult);
        }
        return childrenData;
    }

    private List<InstanceFormDetailData> transferToFormChildrenData(List<ReportDataResult> reportDataResults) {
        ArrayList<InstanceFormDetailData> childrenData = new ArrayList<InstanceFormDetailData>();
        if (reportDataResults == null || reportDataResults.isEmpty()) {
            return childrenData;
        }
        for (ReportDataResult reportDataResult : reportDataResults) {
            InstanceFormDetailDataImpl formDataResult = new InstanceFormDetailDataImpl();
            formDataResult.setKey(reportDataResult.getId());
            formDataResult.setCode(reportDataResult.getId());
            formDataResult.setTitle(reportDataResult.getTitle());
            formDataResult.setState(reportDataResult.getStartStatus());
            Date startTime = reportDataResult.getStartTime();
            if (startTime != null) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(startTime);
                formDataResult.setStartTime(calendar);
            }
            formDataResult.setChildren(this.transferToFormChildrenData(reportDataResult.getChildren()));
            childrenData.add(formDataResult);
        }
        return childrenData;
    }

    private WorkflowObjectType transferToWorkflowObjectType(WorkFlowType workFlowType) {
        switch (workFlowType) {
            case ENTITY: {
                return WorkflowObjectType.MAIN_DIMENSION;
            }
            case FORM: {
                return WorkflowObjectType.FORM;
            }
            case GROUP: {
                return WorkflowObjectType.FORM_GROUP;
            }
        }
        return WorkflowObjectType.MAIN_DIMENSION;
    }

    private String getEntityCaliber(String taskKey) {
        String entityCaliber = DsContextHolder.getDsContext().getContextEntityId();
        if (entityCaliber == null || entityCaliber.isEmpty()) {
            TaskDefine taskDefine = this.runTimeViewController.getTask(taskKey);
            entityCaliber = taskDefine.getDw();
        }
        return entityCaliber;
    }
}

