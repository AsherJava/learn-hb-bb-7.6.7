/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.dataengine.common.DimensionSet
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.np.logging.LogFactory
 *  com.jiuqi.np.logging.Logger
 *  com.jiuqi.np.period.PeriodConsts
 *  com.jiuqi.np.period.PeriodModifier
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.np.period.PeriodUtil
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.nr.common.log.LogModuleEnum
 *  com.jiuqi.nr.common.util.DimensionValueSetUtil
 *  com.jiuqi.nr.data.access.param.AccessFormParam
 *  com.jiuqi.nr.data.access.param.DimensionAccessFormInfo
 *  com.jiuqi.nr.data.access.param.DimensionAccessFormInfo$AccessFormInfo
 *  com.jiuqi.nr.data.access.service.IDataAccessFormService
 *  com.jiuqi.nr.data.access.util.DimensionValueSetUtil
 *  com.jiuqi.nr.data.engine.condition.IConditionCache
 *  com.jiuqi.nr.data.engine.condition.IFormConditionService
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.definition.api.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormGroupDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.facade.TaskOrgLinkDefine
 *  com.jiuqi.nr.definition.internal.impl.FlowsType
 *  com.jiuqi.nr.definition.internal.impl.WorkFlowType
 *  com.jiuqi.nr.definition.internal.stream.param.TaskOrgLinkListStream
 *  com.jiuqi.nr.entity.engine.intf.IEntityItem
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.nr.period.internal.adapter.IPeriodProvider
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.nr.period.modal.IPeriodEntity
 *  com.jiuqi.nr.period.modal.IPeriodRow
 *  com.jiuqi.nr.workflow2.todo.service.TodoManipulationServiceImpl
 *  com.jiuqi.util.StringUtils
 */
package com.jiuqi.nr.bpm.instance.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.dataengine.common.DimensionSet;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.log.LogHelper;
import com.jiuqi.np.logging.LogFactory;
import com.jiuqi.np.logging.Logger;
import com.jiuqi.np.period.PeriodConsts;
import com.jiuqi.np.period.PeriodModifier;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.np.period.PeriodUtil;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.nr.bpm.ProcessEngine;
import com.jiuqi.nr.bpm.businesskey.BusinessKey;
import com.jiuqi.nr.bpm.common.ProcessInstance;
import com.jiuqi.nr.bpm.common.Task;
import com.jiuqi.nr.bpm.common.UploadStateNew;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowDefine;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowTreeNode;
import com.jiuqi.nr.bpm.custom.service.CustomWorkFolwService;
import com.jiuqi.nr.bpm.custom.service.CustomWorkflowConfigService;
import com.jiuqi.nr.bpm.dataflow.service.IQueryUploadStateService;
import com.jiuqi.nr.bpm.de.dataflow.bean.WorkflowParam;
import com.jiuqi.nr.bpm.de.dataflow.common.WorkflowParamBase;
import com.jiuqi.nr.bpm.de.dataflow.service.IWorkflow;
import com.jiuqi.nr.bpm.de.dataflow.util.BusinessGenerator;
import com.jiuqi.nr.bpm.de.dataflow.util.DimensionUtil;
import com.jiuqi.nr.bpm.de.dataflow.util.IWorkFlowDimensionBuilder;
import com.jiuqi.nr.bpm.impl.common.BusinessKeyFormatter;
import com.jiuqi.nr.bpm.impl.common.NrParameterUtils;
import com.jiuqi.nr.bpm.impl.upload.modeling.ProcessBuilderUtils;
import com.jiuqi.nr.bpm.instance.bean.CorporateData;
import com.jiuqi.nr.bpm.instance.bean.GridDataItem;
import com.jiuqi.nr.bpm.instance.bean.GridDataResult;
import com.jiuqi.nr.bpm.instance.bean.PageUtil;
import com.jiuqi.nr.bpm.instance.bean.QueryGridDataParam;
import com.jiuqi.nr.bpm.instance.bean.ReportDataParam;
import com.jiuqi.nr.bpm.instance.bean.ReportDataResult;
import com.jiuqi.nr.bpm.instance.bean.StartStateParam;
import com.jiuqi.nr.bpm.instance.bean.TaskNode;
import com.jiuqi.nr.bpm.instance.bean.WorkflowBaseInfoResult;
import com.jiuqi.nr.bpm.instance.bean.WorkflowBaseOtherInfo;
import com.jiuqi.nr.bpm.instance.bean.WorkflowDefine;
import com.jiuqi.nr.bpm.instance.bean.WorkflowDefineResult;
import com.jiuqi.nr.bpm.instance.bean.WorkflowRelation;
import com.jiuqi.nr.bpm.instance.service.WorkflowInstanceService;
import com.jiuqi.nr.bpm.service.HistoryService;
import com.jiuqi.nr.bpm.service.RunTimeService;
import com.jiuqi.nr.bpm.setting.bean.WorkflowSettingDefine;
import com.jiuqi.nr.bpm.setting.dao.impl.WorkflowSettingDao;
import com.jiuqi.nr.bpm.setting.pojo.CustomPeriodData;
import com.jiuqi.nr.bpm.setting.pojo.StateChangeObj;
import com.jiuqi.nr.bpm.setting.pojo.StatusData;
import com.jiuqi.nr.bpm.setting.pojo.UnBindResult;
import com.jiuqi.nr.bpm.setting.pojo.WorkflowSettingPojo;
import com.jiuqi.nr.bpm.setting.service.IBulidParam;
import com.jiuqi.nr.bpm.setting.service.SettingContextStrategy;
import com.jiuqi.nr.bpm.setting.service.WorkflowSettingService;
import com.jiuqi.nr.bpm.setting.utils.BpmQueryEntityData;
import com.jiuqi.nr.bpm.setting.utils.SettingUtil;
import com.jiuqi.nr.common.log.LogModuleEnum;
import com.jiuqi.nr.common.util.DimensionValueSetUtil;
import com.jiuqi.nr.data.access.param.AccessFormParam;
import com.jiuqi.nr.data.access.param.DimensionAccessFormInfo;
import com.jiuqi.nr.data.access.service.IDataAccessFormService;
import com.jiuqi.nr.data.engine.condition.IConditionCache;
import com.jiuqi.nr.data.engine.condition.IFormConditionService;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.definition.api.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormGroupDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.facade.TaskOrgLinkDefine;
import com.jiuqi.nr.definition.internal.impl.FlowsType;
import com.jiuqi.nr.definition.internal.impl.WorkFlowType;
import com.jiuqi.nr.definition.internal.stream.param.TaskOrgLinkListStream;
import com.jiuqi.nr.entity.engine.intf.IEntityItem;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nr.period.internal.adapter.IPeriodProvider;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nr.period.modal.IPeriodEntity;
import com.jiuqi.nr.period.modal.IPeriodRow;
import com.jiuqi.nr.workflow2.todo.service.TodoManipulationServiceImpl;
import com.jiuqi.util.StringUtils;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class WorkflowInstanceServiceImpl
implements WorkflowInstanceService {
    private static final Logger logger = LogFactory.getLogger(WorkflowInstanceServiceImpl.class);
    private static final String DEFAULT_KEY = "00000000-0000-0000-0000-000000000000";
    @Value(value="${jiuqi.nr.task2.enable:false}")
    private boolean mulCheckVersion;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private IPeriodEntityAdapter periodEntityAdapter;
    @Autowired(required=false)
    private List<WorkflowParamBase> workflowParamList;
    @Autowired
    private CustomWorkFolwService customWorkFolwService;
    @Autowired
    private WorkflowSettingDao workflowSettingDao;
    @Autowired
    private IQueryUploadStateService queryUploadStateService;
    @Autowired
    private DimensionUtil dimensionUtil;
    @Autowired
    private SettingUtil settingUtil;
    @Autowired
    private NrParameterUtils nrParameterUtils;
    @Autowired
    private IWorkflow workflow;
    @Autowired
    private IFormConditionService formConditionService;
    @Autowired
    private PeriodEngineService periodEngineService;
    @Autowired
    private WorkflowSettingService workflowSettingService;
    @Autowired
    private IDataAccessFormService dataAccessFormService;
    @Autowired
    private IRuntimeDataSchemeService runtimeDataSchemeService;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private IWorkFlowDimensionBuilder workFlowDimensionBuilder;
    @Autowired
    private BusinessGenerator businessGenerator;
    @Autowired
    private CustomWorkflowConfigService customWorkflowConfigService;
    @Autowired
    private SettingContextStrategy settingContextStrategy;
    @Autowired
    private TodoManipulationServiceImpl todoManipulationServiceImpl;

    @Override
    public WorkflowBaseInfoResult queryBaseInfo(String taskKey, String periodParam) {
        WorkflowBaseInfoResult workflowBaseInfoResult = new WorkflowBaseInfoResult();
        List allTask = this.runTimeViewController.listAllTask();
        if (allTask == null || allTask.size() == 0) {
            return null;
        }
        TaskDefine taskDefine = null;
        taskDefine = StringUtils.isEmpty((String)taskKey) ? (TaskDefine)allTask.get(0) : allTask.stream().filter(e -> e.getKey().equals(taskKey)).findFirst().get();
        workflowBaseInfoResult.setTaskKey(taskDefine.getKey());
        String dateTime = taskDefine.getDateTime();
        PeriodType periodType = taskDefine.getPeriodType();
        workflowBaseInfoResult.setPeriodType(periodType.name());
        this.getPeriod(periodParam, taskDefine, workflowBaseInfoResult);
        String formSchemeKey = this.queryFormSchemeKey(workflowBaseInfoResult.getTaskKey(), workflowBaseInfoResult.getPeriod());
        if (formSchemeKey != null) {
            workflowBaseInfoResult.setFormSchemeKey(formSchemeKey);
        }
        String dwMainDimName = this.dimensionUtil.getDwMainDimNameByTaskKey(workflowBaseInfoResult.getTaskKey());
        workflowBaseInfoResult.setDwMainDim(dwMainDimName);
        ArrayList<CustomPeriodData> customPeriodList = new ArrayList<CustomPeriodData>();
        CustomPeriodData customPeriodData = null;
        List periodItems = this.periodEntityAdapter.getPeriodProvider(dateTime).getPeriodItems();
        for (IPeriodRow period : periodItems) {
            customPeriodData = new CustomPeriodData();
            customPeriodData.setCode(period.getCode());
            customPeriodData.setTitle(period.getTitle());
            customPeriodList.add(customPeriodData);
        }
        workflowBaseInfoResult.setCustomPeriodDataList(customPeriodList);
        workflowBaseInfoResult.setShowEditWorkflowButton(this.mulCheckVersion);
        return workflowBaseInfoResult;
    }

    private void getPeriod(String periodParam, TaskDefine taskDefine, WorkflowBaseInfoResult workflowBaseInfoResult) {
        IPeriodRow curPeriod;
        PeriodWrapper periodPw = new PeriodWrapper(periodParam);
        IPeriodEntity periodEntity = this.periodEntityAdapter.getPeriodEntity(taskDefine.getDateTime());
        PeriodType type = periodEntity.getType();
        String currPeriod = null;
        if (periodPw.getType() == type.type()) {
            if (periodParam != null && !"".equals(periodParam)) {
                currPeriod = periodParam;
            } else {
                curPeriod = this.periodEntityAdapter.getPeriodProvider(taskDefine.getDateTime()).getCurPeriod();
                currPeriod = curPeriod.getCode();
            }
        } else {
            curPeriod = this.periodEntityAdapter.getPeriodProvider(taskDefine.getDateTime()).getCurPeriod();
            currPeriod = curPeriod.getCode();
        }
        try {
            List<SchemePeriodLinkDefine> schemePeriod = this.querySchemePeriodLinkByTask(taskDefine);
            if (schemePeriod != null && schemePeriod.size() > 0) {
                SchemePeriodLinkDefine fromPeriod = schemePeriod.get(0);
                SchemePeriodLinkDefine toPeriod = schemePeriod.get(schemePeriod.size() - 1);
                if (StringUtils.isEmpty((String)fromPeriod.getPeriodKey())) {
                    workflowBaseInfoResult.setFormPeriod("1970Y0001");
                } else {
                    workflowBaseInfoResult.setFormPeriod(fromPeriod.getPeriodKey());
                }
                if (StringUtils.isEmpty((String)toPeriod.getPeriodKey())) {
                    workflowBaseInfoResult.setToPeriod("9999Y0001");
                } else {
                    workflowBaseInfoResult.setToPeriod(toPeriod.getPeriodKey());
                }
                PeriodWrapper currentPeriodPw = new PeriodWrapper(currPeriod);
                PeriodWrapper fromPeriodPw = new PeriodWrapper(fromPeriod.getPeriodKey());
                PeriodWrapper toPeriodPw = new PeriodWrapper(toPeriod.getPeriodKey());
                if (StringUtils.isNotEmpty((String)fromPeriod.getPeriodKey()) || StringUtils.isNotEmpty((String)toPeriod.getPeriodKey())) {
                    if (StringUtils.isNotEmpty((String)toPeriod.getPeriodKey()) && currentPeriodPw.compareTo((Object)toPeriodPw) > 0) {
                        currPeriod = toPeriod.getPeriodKey();
                    } else if (StringUtils.isNotEmpty((String)fromPeriod.getPeriodKey()) && currentPeriodPw.compareTo((Object)fromPeriodPw) < 0) {
                        currPeriod = fromPeriod.getPeriodKey();
                    } else {
                        IPeriodRow curPeriod2 = this.periodEntityAdapter.getPeriodProvider(taskDefine.getDateTime()).getCurPeriod();
                        currPeriod = curPeriod2.getCode();
                    }
                } else {
                    IPeriodRow curPeriod3 = this.periodEntityAdapter.getPeriodProvider(taskDefine.getDateTime()).getCurPeriod();
                    currPeriod = curPeriod3.getCode();
                }
            }
            IPeriodProvider periodProvider = this.periodEntityAdapter.getPeriodProvider(taskDefine.getDateTime());
            PeriodModifier modifier = new PeriodModifier();
            modifier.setPeriodModifier(taskDefine.getTaskPeriodOffset());
            String modifyPeriod = periodProvider.modify(currPeriod, modifier);
            workflowBaseInfoResult.setPeriod(modifyPeriod);
        }
        catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    @Override
    public List<TaskNode> queryTasks() {
        ArrayList<TaskNode> nodes = new ArrayList<TaskNode>();
        List allTask = this.runTimeViewController.listAllTask();
        if (allTask == null || allTask.size() == 0) {
            return null;
        }
        for (TaskDefine taskDefine : allTask) {
            TaskNode taskNode = new TaskNode();
            taskNode.setKey(taskDefine.getKey());
            taskNode.setCode(taskDefine.getTaskCode());
            taskNode.setTitle(taskDefine.getTitle());
            nodes.add(taskNode);
        }
        return nodes;
    }

    @Override
    public List<WorkflowDefine> queryWorkflowDefines() {
        List<WorkflowParam> designWorkflow;
        ArrayList<WorkflowDefine> paramList = new ArrayList<WorkflowDefine>();
        WorkflowDefine workflowDefine = null;
        workflowDefine = new WorkflowDefine();
        workflowDefine.setKey(DEFAULT_KEY);
        workflowDefine.setTitle("\u9ed8\u8ba4\u6d41\u7a0b");
        workflowDefine.setCode("DEFAULT_WORKFLOW");
        paramList.add(workflowDefine);
        List<WorkFlowDefine> workflowByState = this.customWorkFolwService.getWorkflowByState();
        if (workflowByState.size() > 0) {
            for (WorkFlowDefine workFlowDefine : workflowByState) {
                workflowDefine = new WorkflowDefine();
                workflowDefine.setKey(workFlowDefine.getId());
                workflowDefine.setTitle(workFlowDefine.getTitle());
                paramList.add(workflowDefine);
            }
        }
        if (this.workflowParamList != null && this.workflowParamList.size() > 0 && (designWorkflow = this.workflowParamList.get(0).getDesignWorkflow()) != null && designWorkflow.size() > 0) {
            for (WorkFlowDefine workFlowDefine2 : workflowByState) {
                workflowDefine = new WorkflowDefine();
                workflowDefine.setKey(workFlowDefine2.getId());
                workflowDefine.setTitle(workFlowDefine2.getTitle());
                paramList.add(workflowDefine);
            }
        }
        return paramList;
    }

    @Override
    public GridDataResult queryGridDatas(QueryGridDataParam queryGridDataParam) {
        GridDataResult gridDataResult = new GridDataResult();
        if (queryGridDataParam.getStates() != null && queryGridDataParam.getStates().size() > 0) {
            List<GridDataItem> allGridData = this.queryAllGridDataByFliter(queryGridDataParam);
            List<GridDataItem> startPage = PageUtil.subList(allGridData, queryGridDataParam.getPageSize(), queryGridDataParam.getPageNum());
            gridDataResult.setGridDatas(startPage);
            gridDataResult.setCount(allGridData != null ? allGridData.size() : 0);
        } else {
            gridDataResult = this.queryChild(queryGridDataParam);
        }
        return gridDataResult;
    }

    private List<GridDataItem> queryAllGridDataByFliter(QueryGridDataParam queryGridDataParam) {
        List<GridDataItem> childrenData = this.queryChildrenData(queryGridDataParam);
        if (StringUtils.isNotEmpty((String)queryGridDataParam.getSearchKeys())) {
            childrenData = this.fliterByKeys(queryGridDataParam, childrenData);
        }
        if (queryGridDataParam.getStates() != null && queryGridDataParam.getStates().size() > 0) {
            childrenData = this.fliterByStates(queryGridDataParam, childrenData);
        }
        return childrenData;
    }

    private List<GridDataItem> fliterByStates(QueryGridDataParam queryGridDataParam, List<GridDataItem> childrenData) {
        ArrayList<GridDataItem> gridDatas = new ArrayList<GridDataItem>();
        List<Integer> states = queryGridDataParam.getStates();
        if (states == null || states.size() == 0) {
            return gridDatas;
        }
        for (GridDataItem gridDataItem : childrenData) {
            if (!states.contains(gridDataItem.getState())) continue;
            gridDatas.add(gridDataItem);
        }
        return gridDatas;
    }

    private List<GridDataItem> fliterByKeys(QueryGridDataParam queryGridDataParam, List<GridDataItem> childrenData) {
        String searchKeys = queryGridDataParam.getSearchKeys();
        if (StringUtils.isEmpty((String)queryGridDataParam.getSearchKeys())) {
            return childrenData;
        }
        List datasByCode = childrenData.stream().filter(e -> e.getCode().contains(searchKeys)).collect(Collectors.toList());
        List datasByTitle = childrenData.stream().filter(e -> e.getTitle().contains(searchKeys)).collect(Collectors.toList());
        datasByCode.addAll(datasByTitle);
        List<GridDataItem> collect = datasByCode.stream().filter(WorkflowInstanceServiceImpl.distinctByKey(GridDataItem::getKey)).collect(Collectors.toList());
        return collect;
    }

    public static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor) {
        ConcurrentHashMap seen = new ConcurrentHashMap();
        return object -> seen.putIfAbsent(keyExtractor.apply(object), Boolean.TRUE) == null;
    }

    private List<IEntityRow> getChildren(QueryGridDataParam queryGridDataParam, String formSchemeKey) {
        List<Object> childrenData = new ArrayList<IEntityRow>();
        BpmQueryEntityData bpmQueryEntityData = new BpmQueryEntityData();
        if (queryGridDataParam.isAllChecked()) {
            List<IEntityRow> entityData = bpmQueryEntityData.getEntityData(formSchemeKey, queryGridDataParam.getPeriod());
            if (entityData != null && entityData.size() > 0) {
                childrenData.addAll(entityData);
            }
        } else {
            IEntityRow currentData = bpmQueryEntityData.getCurrentData(formSchemeKey, queryGridDataParam.getCurrentUnitKey(), queryGridDataParam.getPeriod());
            if (currentData != null) {
                childrenData.add(currentData);
            }
            if (queryGridDataParam.isAllSubordinate()) {
                List<IEntityRow> allChildrenData = bpmQueryEntityData.getAllChildrenData(formSchemeKey, queryGridDataParam.getPeriod(), queryGridDataParam.getCurrentUnitKey());
                if (allChildrenData != null && allChildrenData.size() > 0) {
                    childrenData.addAll(allChildrenData);
                }
            } else {
                List<IEntityRow> directChildrenData = bpmQueryEntityData.getDirectChildrenData(formSchemeKey, queryGridDataParam.getPeriod(), queryGridDataParam.getCurrentUnitKey());
                if (directChildrenData != null && directChildrenData.size() > 0) {
                    childrenData.addAll(directChildrenData);
                }
            }
        }
        List<String> searchUnitKeys = queryGridDataParam.getSearchUnitKeys();
        if (searchUnitKeys != null && searchUnitKeys.size() > 0) {
            childrenData = childrenData.stream().filter(e -> searchUnitKeys.contains(e.getEntityKeyData())).collect(Collectors.toList());
        }
        return childrenData;
    }

    private Map<String, StatusData> queryStateMap(String formSchemeKey, Map<String, DimensionValueSet> dimensionValueSetMap, String dwDimName) {
        HashMap<String, StatusData> statusDataMap = new HashMap<String, StatusData>();
        WorkFlowType queryStartType = this.settingUtil.queryStartType(formSchemeKey);
        ArrayList<DimensionValueSet> dims = new ArrayList<DimensionValueSet>();
        for (Map.Entry<String, DimensionValueSet> entry : dimensionValueSetMap.entrySet()) {
            dims.add(entry.getValue());
        }
        DimensionValueSet dimensionValueSet = com.jiuqi.nr.data.access.util.DimensionValueSetUtil.mergeDimensionValueSet(dims);
        IConditionCache conditionCache = this.formConditionService.getConditionForms(dimensionValueSet, formSchemeKey);
        List<UploadStateNew> uploadStateList = this.queryUploadStateService.queryUploadStates(formSchemeKey, dimensionValueSet, new ArrayList<String>(), new ArrayList<String>());
        String period = dimensionValueSet.getValue("DATATIME").toString();
        Object value = dimensionValueSet.getValue(dwDimName);
        List<String> unitKeys = new ArrayList<String>();
        if (value != null) {
            if (value instanceof ArrayList) {
                unitKeys = (List)value;
            } else if (value instanceof String) {
                unitKeys.add(value.toString());
            }
        }
        Map<String, Map<String, Boolean>> stopDatas = this.queryStopDatas(formSchemeKey, period, unitKeys, new ArrayList<String>(), queryStartType);
        for (Map.Entry<String, DimensionValueSet> entry : dimensionValueSetMap.entrySet()) {
            String unitKey = entry.getKey();
            DimensionValueSet currentDim = entry.getValue();
            List<UploadStateNew> stateList = this.filterSatateByUnitKey(currentDim, uploadStateList);
            if (WorkFlowType.ENTITY.equals((Object)queryStartType)) {
                StatusData statusData = new StatusData();
                if (stateList != null && stateList.size() > 0) {
                    UploadStateNew uploadStateNew = stateList.get(0);
                    if (uploadStateNew != null && uploadStateNew.getTaskId() != null) {
                        statusData.setStartStatus(GridDataItem.StartState.SUCCESS.getState());
                        statusData.setStartTime(uploadStateNew.getStartTime());
                        statusDataMap.put(unitKey, statusData);
                        continue;
                    }
                    Map<String, Boolean> booleanMap = stopDatas.get(unitKey);
                    if (booleanMap != null && booleanMap.size() > 0) {
                        statusData.setStartStatus(GridDataItem.StartState.STOP.getState());
                        statusDataMap.put(unitKey, statusData);
                        continue;
                    }
                    statusData.setStartStatus(GridDataItem.StartState.FAIL.getState());
                    statusDataMap.put(unitKey, statusData);
                    continue;
                }
                Map<String, Boolean> booleanMap = stopDatas.get(unitKey);
                if (booleanMap != null && booleanMap.size() > 0) {
                    statusData.setStartStatus(GridDataItem.StartState.STOP.getState());
                    statusDataMap.put(unitKey, statusData);
                    continue;
                }
                statusData.setStartStatus(GridDataItem.StartState.FAIL.getState());
                statusDataMap.put(unitKey, statusData);
                continue;
            }
            if (!WorkFlowType.FORM.equals((Object)queryStartType) && !WorkFlowType.GROUP.equals((Object)queryStartType)) continue;
            ArrayList<String> keyList = new ArrayList<String>();
            if (WorkFlowType.FORM.equals((Object)queryStartType)) {
                List forms = conditionCache.getSeeForms(currentDim);
                for (String formKey : forms) {
                    keyList.add(formKey);
                }
            } else if (WorkFlowType.GROUP.equals((Object)queryStartType)) {
                List formGroups = conditionCache.getSeeFormGroups(currentDim);
                for (String formGroupKey : formGroups) {
                    keyList.add(formGroupKey);
                }
            }
            StatusData statusData = new StatusData();
            List collect = stateList.stream().filter(e -> keyList.contains(e.getFormId())).collect(Collectors.toList());
            if (collect != null && collect.size() > 0) {
                if (collect.size() < keyList.size()) {
                    statusData.setStartStatus(GridDataItem.StartState.PART_SUCESS.getState());
                    statusData.setStartTime(uploadStateList.get(0).getStartTime());
                    statusDataMap.put(unitKey, statusData);
                    continue;
                }
                statusData.setStartStatus(GridDataItem.StartState.SUCCESS.getState());
                statusData.setStartTime(uploadStateList.get(0).getStartTime());
                statusDataMap.put(unitKey, statusData);
                continue;
            }
            ArrayList<Boolean> isBind = new ArrayList<Boolean>();
            List<String> ids = this.settingUtil.queryFormOrGroupByFormSchemeKey(formSchemeKey);
            for (String formOrGroupKey : ids) {
                Map<String, Boolean> stringBooleanMap = stopDatas.get(unitKey);
                if (stringBooleanMap == null || stringBooleanMap.size() <= 0) continue;
                Boolean b = stringBooleanMap.get(formOrGroupKey);
                isBind.add(b);
            }
            if (isBind.contains(true)) {
                statusData.setStartStatus(GridDataItem.StartState.STOP.getState());
                statusDataMap.put(unitKey, statusData);
                continue;
            }
            statusData.setStartStatus(GridDataItem.StartState.FAIL.getState());
            statusDataMap.put(unitKey, statusData);
        }
        return statusDataMap;
    }

    private List<UploadStateNew> filterSatateByUnitKey(DimensionValueSet currentDim, List<UploadStateNew> uploadStateList) {
        ArrayList<UploadStateNew> uploadStates = new ArrayList<UploadStateNew>();
        for (UploadStateNew uploadStateNew : uploadStateList) {
            String currentEntityStr;
            DimensionValueSet entities = uploadStateNew.getEntities();
            String entityStr = this.customToString(entities);
            if (!entityStr.equals(currentEntityStr = this.customToString(currentDim))) continue;
            uploadStates.add(uploadStateNew);
        }
        return uploadStates;
    }

    private String customToString(DimensionValueSet entities) {
        StringBuffer sb = new StringBuffer();
        sb.append("'");
        DimensionSet dimensionSet = entities.getDimensionSet();
        for (int i = 0; i < dimensionSet.size(); ++i) {
            String dimension = dimensionSet.get(i);
            if (dimension.equals("FORMID")) continue;
            sb.append(entities.getValue(dimension).toString()).append(";");
        }
        sb.append("'");
        return sb.toString();
    }

    private StatusData queryStatus(String formSchemeKey, DimensionValueSet dimensionValueSet, String taskKey) {
        block14: {
            StatusData statusData = new StatusData();
            try {
                List<UploadStateNew> uploadStateList;
                FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
                WorkFlowType queryStartType = this.settingUtil.queryStartType(formSchemeKey);
                if (WorkFlowType.ENTITY.equals((Object)queryStartType)) {
                    UploadStateNew uploadStateNew = this.queryUploadStateService.queryUploadState(formScheme.getKey(), dimensionValueSet);
                    if (uploadStateNew != null && uploadStateNew.getTaskId() != null) {
                        statusData.setStartStatus(GridDataItem.StartState.SUCCESS.getState());
                        statusData.setStartTime(uploadStateNew.getStartTime());
                        return statusData;
                    }
                    String formOrGroupKey = this.nrParameterUtils.getDefaultFormId(formSchemeKey);
                    boolean bindProcess = this.workflow.bindProcess(formSchemeKey, dimensionValueSet, formOrGroupKey);
                    if (bindProcess) {
                        statusData.setStartStatus(GridDataItem.StartState.FAIL.getState());
                        return statusData;
                    }
                    statusData.setStartStatus(GridDataItem.StartState.STOP.getState());
                    return statusData;
                }
                if (!WorkFlowType.FORM.equals((Object)queryStartType) && !WorkFlowType.GROUP.equals((Object)queryStartType)) break block14;
                IConditionCache conditionCache = this.formConditionService.getConditionForms(dimensionValueSet, formSchemeKey);
                ArrayList<String> keyList = new ArrayList<String>();
                if (WorkFlowType.FORM.equals((Object)queryStartType)) {
                    List forms = conditionCache.getSeeForms(dimensionValueSet);
                    for (String formKey : forms) {
                        keyList.add(formKey);
                    }
                } else if (WorkFlowType.GROUP.equals((Object)queryStartType)) {
                    List formGroups = conditionCache.getSeeFormGroups(dimensionValueSet);
                    for (String formGroupKey : formGroups) {
                        keyList.add(formGroupKey);
                    }
                }
                if ((uploadStateList = this.queryUploadStateService.queryUploadStates(formScheme.getKey(), dimensionValueSet, keyList, keyList)) != null && uploadStateList.size() > 0) {
                    if (uploadStateList.size() < keyList.size()) {
                        statusData.setStartStatus(GridDataItem.StartState.PART_SUCESS.getState());
                        statusData.setStartTime(uploadStateList.get(0).getStartTime());
                        return statusData;
                    }
                    statusData.setStartStatus(GridDataItem.StartState.SUCCESS.getState());
                    statusData.setStartTime(uploadStateList.get(0).getStartTime());
                    return statusData;
                }
                ArrayList<Boolean> isBind = new ArrayList<Boolean>();
                List<String> ids = this.settingUtil.queryFormOrGroupByFormSchemeKey(formSchemeKey);
                for (String formOrGroupKey : ids) {
                    boolean bindProcess = this.workflow.bindProcess(formSchemeKey, dimensionValueSet, formOrGroupKey);
                    isBind.add(bindProcess);
                }
                if (isBind.contains(true)) {
                    statusData.setStartStatus(GridDataItem.StartState.FAIL.getState());
                    return statusData;
                }
                statusData.setStartStatus(GridDataItem.StartState.STOP.getState());
                return statusData;
            }
            catch (Exception e) {
                logger.error(e.getMessage(), (Throwable)e);
            }
        }
        return null;
    }

    private Map<String, DimensionValueSet> getDimensions(String formSchemeKey, List<String> unitIds, QueryGridDataParam queryGridDataParam) {
        HashMap<String, DimensionValueSet> map = new HashMap<String, DimensionValueSet>();
        DimensionValueSet dim = new DimensionValueSet();
        String dwDimensionName = this.dimensionUtil.getDwMainDimName(formSchemeKey);
        if (unitIds != null && unitIds.size() > 0) {
            if (unitIds.size() == 1) {
                dim.setValue(dwDimensionName, (Object)unitIds.get(0));
            } else {
                dim.setValue(dwDimensionName, unitIds);
            }
        } else {
            dim.setValue(dwDimensionName, (Object)"");
        }
        dim.setValue("DATATIME", (Object)queryGridDataParam.getPeriod());
        dim.setValue("ADJUST", (Object)queryGridDataParam.getAdjust());
        Map dimensionValueMap = com.jiuqi.nr.data.access.util.DimensionValueSetUtil.getDimensionSet((DimensionValueSet)dim);
        DimensionCollection dimensionCollection = this.workFlowDimensionBuilder.buildDimensionCollection(queryGridDataParam.getTaskKey(), dimensionValueMap);
        List dimensionCombinations = dimensionCollection.getDimensionCombinations();
        for (DimensionCombination dimensionCombination : dimensionCombinations) {
            DimensionValueSet dimensionValueSet = dimensionCombination.toDimensionValueSet();
            String unitKey = dimensionValueSet.getValue(dwDimensionName).toString();
            map.put(unitKey, dimensionValueSet);
        }
        return map;
    }

    @Override
    public void operateWorkflowInstance(StartStateParam startStateParam, AsyncTaskMonitor asyncTaskMonitor) {
        int operateType = startStateParam.getOperateType();
        String formSchemeKey = this.queryFormSchemeKey(startStateParam.getTaskKey(), startStateParam.getPeriod());
        if (formSchemeKey != null) {
            asyncTaskMonitor.progressAndMessage(0.1, "\u53c2\u6570\u89e3\u6790\u5f00\u59cb");
            Set<String> operatorParam = this.getOperatorParam(startStateParam);
            asyncTaskMonitor.progressAndMessage(0.3, "\u53c2\u6570\u89e3\u6790\u7ed3\u675f");
            if (StartStateParam.Type.START.getType() == operateType) {
                this.startProcess(formSchemeKey, operatorParam, startStateParam, asyncTaskMonitor);
            }
            if (StartStateParam.Type.CLEAR.getType() == operateType) {
                this.clearProcess(formSchemeKey, operatorParam, startStateParam, asyncTaskMonitor);
            }
            if (StartStateParam.Type.STOP.getType() == operateType) {
                this.stopProcess(formSchemeKey, operatorParam, startStateParam, asyncTaskMonitor);
            }
        }
    }

    private void startProcess(String formSchemeKey, Set<String> operatorParam, StartStateParam startStateParam, AsyncTaskMonitor asyncTaskMonitor) {
        StateChangeObj stateChangeObj = new StateChangeObj();
        stateChangeObj.setFormSchemeId(formSchemeKey);
        stateChangeObj.setDataObj(operatorParam);
        stateChangeObj.setSelectAll(startStateParam.getQueryGridDataParam().isAllChecked());
        stateChangeObj.setPeriod(startStateParam.getPeriod());
        Set<String> formOrGroupKeys = startStateParam.getFormOrGroupKeys();
        if ((formOrGroupKeys == null || formOrGroupKeys.size() == 0) && startStateParam.getQueryGridDataParam().isAllChecked()) {
            formOrGroupKeys = this.settingUtil.queryFormOrGroupKeyByFormSchemeKey(formSchemeKey);
            stateChangeObj.setReportAll(true);
        } else {
            stateChangeObj.setReportAll(false);
        }
        stateChangeObj.setReportList(formOrGroupKeys);
        stateChangeObj.setSettingId(DEFAULT_KEY);
        stateChangeObj.setStart(true);
        this.setAdjust(formSchemeKey, startStateParam, stateChangeObj);
        this.workflowSettingService.startDataObjs(stateChangeObj, asyncTaskMonitor);
    }

    private void clearProcess(String formSchemeKey, Set<String> operatorParam, StartStateParam startStateParam, AsyncTaskMonitor asyncTaskMonitor) {
        StateChangeObj stateChangeObj = new StateChangeObj();
        stateChangeObj.setFormSchemeId(formSchemeKey);
        stateChangeObj.setDataObj(operatorParam);
        stateChangeObj.setSelectAll(startStateParam.getQueryGridDataParam().isAllChecked());
        stateChangeObj.setPeriod(startStateParam.getPeriod());
        Set<String> formOrGroupKeys = startStateParam.getFormOrGroupKeys();
        if ((formOrGroupKeys == null || formOrGroupKeys.size() == 0) && startStateParam.getQueryGridDataParam().isAllChecked()) {
            formOrGroupKeys = this.settingUtil.queryFormOrGroupKeyByFormSchemeKey(formSchemeKey);
            stateChangeObj.setReportAll(true);
        } else {
            stateChangeObj.setReportAll(false);
        }
        stateChangeObj.setReportList(formOrGroupKeys);
        stateChangeObj.setSettingId(DEFAULT_KEY);
        stateChangeObj.setStart(false);
        this.setAdjust(formSchemeKey, startStateParam, stateChangeObj);
        this.workflowSettingService.clearDataObjs(stateChangeObj, asyncTaskMonitor);
    }

    private void stopProcess(String formSchemeKey, Set<String> operatorParam, StartStateParam startStateParam, AsyncTaskMonitor asyncTaskMonitor) {
        StateChangeObj stateChangeObj = new StateChangeObj();
        stateChangeObj.setFormSchemeId(formSchemeKey);
        WorkflowSettingDefine workflowSetting = this.workflowSettingDao.getWorkflowSettingByFormSchemeKey(formSchemeKey);
        if (workflowSetting != null && workflowSetting.getId() != null) {
            stateChangeObj.setSettingId(workflowSetting.getId());
        } else {
            stateChangeObj.setSettingId(DEFAULT_KEY);
        }
        stateChangeObj.setDataObj(operatorParam);
        stateChangeObj.setSelectAll(startStateParam.getQueryGridDataParam().isAllChecked());
        stateChangeObj.setPeriod(startStateParam.getPeriod());
        Set<String> formOrGroupKeys = startStateParam.getFormOrGroupKeys();
        if ((formOrGroupKeys == null || formOrGroupKeys.size() == 0) && startStateParam.getQueryGridDataParam().isAllChecked()) {
            formOrGroupKeys = this.settingUtil.queryFormOrGroupKeyByFormSchemeKey(formSchemeKey);
            stateChangeObj.setReportAll(true);
        } else {
            stateChangeObj.setReportAll(false);
        }
        stateChangeObj.setReportList(formOrGroupKeys);
        stateChangeObj.setStart(false);
        this.setAdjust(formSchemeKey, startStateParam, stateChangeObj);
        this.workflowSettingService.unBindProcess(stateChangeObj, asyncTaskMonitor);
    }

    private void setAdjust(String formSchemeKey, StartStateParam startStateParam, StateChangeObj stateChangeObj) {
        FormSchemeDefine formScheme = this.settingUtil.getFormScheme(formSchemeKey);
        TaskDefine tasks = this.settingUtil.queryTasks(formScheme.getTaskKey());
        boolean enableAdjust = this.runtimeDataSchemeService.enableAdjustPeriod(tasks.getDataScheme());
        if (enableAdjust) {
            if (startStateParam.getAdjust() != null) {
                stateChangeObj.setAdjust(startStateParam.getAdjust());
            } else {
                stateChangeObj.setAdjust("0");
            }
        }
    }

    @Override
    public boolean savaWorkflowRelation(WorkflowRelation savaWorkflowRelation) {
        String formSchemeKey = this.queryFormSchemeKey(savaWorkflowRelation.getTaskKey(), savaWorkflowRelation.getPeriod());
        if (formSchemeKey != null) {
            String settingId = "";
            try {
                FormSchemeDefine formScheme = this.settingUtil.getFormScheme(formSchemeKey);
                String taskKey = formScheme.getTaskKey();
                TaskDefine taskDefine = this.settingUtil.queryTasks(taskKey);
                PeriodWrapper currPeriod = this.getCurrPeriod(taskDefine);
                this.deleteWorkflowSetting(formSchemeKey, currPeriod.toString(), savaWorkflowRelation.getAdjust());
                try {
                    WorkflowSettingPojo workflowSetting = new WorkflowSettingPojo();
                    workflowSetting.setDataId(formSchemeKey);
                    workflowSetting.setDataType("task_report");
                    workflowSetting.setWorkflowId(savaWorkflowRelation.getWorkflowKey());
                    workflowSetting.setTitle(settingId);
                    settingId = this.workflowSettingDao.insertData(workflowSetting);
                    String taskName = taskDefine.getTitle();
                    String formSchemeName = formScheme.getTitle();
                    LogHelper.info((String)LogModuleEnum.NRPROCESS.getTitle(), (String)"\u65b0\u589e\u6d41\u7a0b\u65b9\u6848", (String)("\u65b0\u589e\u6d41\u7a0b\u65b9\u6848\u6210\u529f,  \u4efb\u52a1\u540d\u79f0\uff1a" + taskName + ", \u62a5\u8868\u65b9\u6848\u540d\u79f0\uff1a" + formSchemeName + ", \u5de5\u4f5c\u6d41\uff1a" + savaWorkflowRelation.getWorkflowKey()));
                    return true;
                }
                catch (Exception e) {
                    LogHelper.info((String)LogModuleEnum.NRPROCESS.getTitle(), (String)"\u65b0\u589e\u6d41\u7a0b\u65b9\u6848", (String)"\u65b0\u589e\u6d41\u7a0b\u65b9\u6848\u5931\u8d25");
                    logger.error(e.getMessage(), (Throwable)e);
                }
            }
            catch (Exception e) {
                logger.error(e.getMessage(), (Throwable)e);
            }
        }
        return false;
    }

    private String deleteWorkflowSetting(String formSchemeKey, String period, String adjust) {
        String message = "";
        WorkflowSettingDefine workflowSetting = this.workflowSettingDao.getWorkflowSettingByFormSchemeKey(formSchemeKey);
        try {
            FormSchemeDefine formScheme = this.settingUtil.getFormScheme(formSchemeKey);
            List<BusinessKey> buildBusinessKey = this.buildBusinessKey(formScheme, period, adjust);
            if (buildBusinessKey != null && buildBusinessKey.size() > 0) {
                for (BusinessKey businessKey : buildBusinessKey) {
                    this.deleteWorkflowProcess(formSchemeKey, businessKey);
                }
            } else {
                message = "\u6d41\u7a0b\u5b9e\u4f8b\u672a\u5220\u9664\uff01";
            }
            if (message.isEmpty()) {
                this.workflowSettingDao.delWorkFlowSettingByID(workflowSetting.getId());
                String taskKey = formScheme.getTaskKey();
                TaskDefine queryTaskDefine = this.settingUtil.queryTasks(taskKey);
                String taskName = queryTaskDefine.getTitle();
                LogHelper.info((String)LogModuleEnum.NRPROCESS.getTitle(), (String)"\u5220\u9664\u6d41\u7a0b\u65b9\u6848", (String)("\u5220\u9664\u6d41\u7a0b\u65b9\u6848\u6210\u529f, \u4efb\u52a1\u540d\u79f0\uff1a" + taskName + ", \u62a5\u8868\u65b9\u6848\u540d\u79f0\uff1a" + formScheme.getTitle() + ", \u5de5\u4f5c\u6d41\uff1a" + workflowSetting.getWorkflowId()));
            }
            return message;
        }
        catch (Exception e) {
            LogHelper.info((String)LogModuleEnum.NRPROCESS.getTitle(), (String)"\u5220\u9664", (String)"\u5220\u9664\u5931\u8d25");
            logger.error(e.getMessage(), (Throwable)e);
            return "";
        }
    }

    private List<BusinessKey> buildBusinessKey(FormSchemeDefine formScheme, String period, String adjust) {
        ArrayList<BusinessKey> businessKeyList = new ArrayList<BusinessKey>();
        List<String> formOrGroupKeys = this.settingUtil.queryFormOrGroupByFormSchemeKey(formScheme.getKey());
        DimensionValueSet dimensionValueSet = this.dimensionUtil.buildDimension(formScheme.getKey(), new HashSet<String>(), period, adjust, true);
        Map dimensionValueMap = DimensionValueSetUtil.getDimensionSet((DimensionValueSet)dimensionValueSet);
        DimensionCollection dimensionValueSets = this.workFlowDimensionBuilder.buildDimensionCollection(formScheme.getTaskKey(), dimensionValueMap);
        List dimensionCombinations = dimensionValueSets.getDimensionCombinations();
        for (DimensionCombination dimensionCombination : dimensionCombinations) {
            DimensionValueSet dimensionValue = dimensionCombination.toDimensionValueSet();
            if (formOrGroupKeys != null && formOrGroupKeys.size() > 0) {
                for (String formOrGroupKey : formOrGroupKeys) {
                    BusinessKey businessKey = this.businessGenerator.buildBusinessKey(formScheme.getKey(), dimensionValue, formOrGroupKey, formOrGroupKey);
                    businessKeyList.add(businessKey);
                }
                continue;
            }
            BusinessKey businessKey = this.businessGenerator.buildBusinessKey(formScheme.getKey(), dimensionValue, null, null);
            businessKeyList.add(businessKey);
        }
        return businessKeyList;
    }

    private void deleteWorkflowProcess(String fromSchemeKey, BusinessKey businessKey) {
        Optional<ProcessEngine> processEngine = this.workflow.getProcessEngine(fromSchemeKey);
        RunTimeService runTimeService = processEngine.map(engine -> engine.getRunTimeService()).orElse(null);
        Optional<ProcessInstance> instance = runTimeService.queryInstanceByBusinessKey(BusinessKeyFormatter.formatToString(businessKey));
        if (!Optional.empty().equals(instance)) {
            runTimeService.deleteProcessInstanceById(instance.get().getId());
            HistoryService historyService = processEngine.map(engine -> engine.getHistoryService()).orElse(null);
            historyService.deleteHistoricProcessInstance(BusinessKeyFormatter.formatToString(businessKey), instance.get().getId());
        }
        this.queryUploadStateService.deleteUploadState(businessKey);
        this.queryUploadStateService.deleteUploadRecord(businessKey);
    }

    public PeriodWrapper getCurrPeriod(TaskDefine taskDefine) {
        PeriodType periodType = taskDefine.getPeriodType();
        int periodOffset = taskDefine.getTaskPeriodOffset();
        String fromPeriod = taskDefine.getFromPeriod();
        String toPeriod = taskDefine.getToPeriod();
        if (StringUtils.isEmpty((String)fromPeriod) || StringUtils.isEmpty((String)toPeriod)) {
            char typeToCode = (char)PeriodConsts.typeToCode((int)periodType.type());
            fromPeriod = "1970" + typeToCode + "0001";
            toPeriod = "9999" + typeToCode + "0001";
        }
        return WorkflowInstanceServiceImpl.getCurrPeriod(periodType.type(), periodOffset, fromPeriod, toPeriod);
    }

    private static PeriodWrapper getCurrPeriod(int periodType, int periodOffset, String fromPeriod, String toPeriod) {
        GregorianCalendar calendar = new GregorianCalendar();
        PeriodWrapper currentPeriod = PeriodUtil.currentPeriod((GregorianCalendar)calendar, (int)periodType, (int)periodOffset);
        return currentPeriod;
    }

    @Override
    public WorkflowBaseOtherInfo queryBaseOtherInfo(String taskKey, String period) {
        WorkflowBaseOtherInfo workflowBaseOtherInfo = new WorkflowBaseOtherInfo();
        String formSchemeKey = this.queryFormSchemeKey(taskKey, period);
        if (formSchemeKey != null) {
            workflowBaseOtherInfo.setFormSchemeKey(formSchemeKey);
            WorkflowSettingDefine workflowSetting = this.workflowSettingDao.getWorkflowSettingByFormSchemeKey(formSchemeKey);
            if (workflowSetting != null && workflowSetting.getId() != null) {
                WorkFlowDefine workFlowDefine = this.customWorkFolwService.getRunWorkFlowDefineByID(workflowSetting.getWorkflowId(), 1);
                if (workFlowDefine != null && workFlowDefine.getId() != null) {
                    workflowBaseOtherInfo.setWorkflowTitle(workFlowDefine.getTitle());
                    workflowBaseOtherInfo.setWorkflowKey(workFlowDefine.getId());
                } else {
                    workflowBaseOtherInfo.setWorkflowTitle("\u9ed8\u8ba4\u6d41\u7a0b");
                    workflowBaseOtherInfo.setWorkflowKey(DEFAULT_KEY);
                }
            } else {
                workflowBaseOtherInfo.setWorkflowTitle("\u9ed8\u8ba4\u6d41\u7a0b");
                workflowBaseOtherInfo.setWorkflowKey(DEFAULT_KEY);
            }
            WorkFlowType workFlowType = WorkFlowType.ENTITY;
            FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
            if (formScheme != null) {
                workFlowType = formScheme.getFlowsSetting().getWordFlowType();
            }
            if (WorkFlowType.ENTITY.equals((Object)workFlowType)) {
                workflowBaseOtherInfo.setWorkflowTypeTitle("\u6309\u4e3b\u4f53\u4e0a\u62a5");
            } else if (WorkFlowType.FORM.equals((Object)workFlowType)) {
                workflowBaseOtherInfo.setWorkflowTypeTitle("\u6309\u62a5\u8868\u4e0a\u62a5");
            } else if (WorkFlowType.GROUP.equals((Object)workFlowType)) {
                workflowBaseOtherInfo.setWorkflowTypeTitle("\u6309\u62a5\u8868\u5206\u7ec4\u4e0a\u62a5");
            }
            workflowBaseOtherInfo.setWorkflowType(workFlowType.toString());
        }
        return workflowBaseOtherInfo;
    }

    private List<SchemePeriodLinkDefine> querySchemePeriodLinkByTask(TaskDefine taskDefine) {
        ArrayList<SchemePeriodLinkDefine> schemePeriodLinks = new ArrayList<SchemePeriodLinkDefine>();
        try {
            List schemePeriodLinkByTask = this.runTimeViewController.listSchemePeriodLinkByTask(taskDefine.getKey());
            IPeriodProvider periodProvider = this.periodEngineService.getPeriodAdapter().getPeriodProvider(taskDefine.getDateTime());
            List periodItems = periodProvider.getPeriodItems();
            HashMap tempMap = new HashMap();
            ArrayList<SchemePeriodLinkDefine> sortDefines = new ArrayList<SchemePeriodLinkDefine>();
            schemePeriodLinkByTask.stream().forEach(e -> tempMap.put(e.getPeriodKey(), e));
            for (IPeriodRow periodItem : periodItems) {
                if (!tempMap.keySet().contains(periodItem.getCode())) continue;
                sortDefines.add((SchemePeriodLinkDefine)tempMap.get(periodItem.getCode()));
            }
            return sortDefines;
        }
        catch (Exception e2) {
            logger.error(e2.getMessage(), (Throwable)e2);
            return schemePeriodLinks;
        }
    }

    @Override
    public List<ReportDataResult> queryReportDataResult(ReportDataParam reportDataParam) {
        ArrayList<ReportDataResult> results = new ArrayList<ReportDataResult>();
        String formSchemeKey = this.queryFormSchemeKey(reportDataParam.getTaskKey(), reportDataParam.getPeriod());
        if (formSchemeKey != null) {
            String dwMainDimName = this.dimensionUtil.getDwMainDimName(formSchemeKey);
            Map<String, List<String>> authForm = this.getAuthForm(reportDataParam.getTaskKey(), formSchemeKey, reportDataParam.getPeriod(), reportDataParam.getUnitId(), dwMainDimName);
            Map<String, List<String>> authGroup = this.getAuthGroup(authForm, formSchemeKey);
            List<FormGroupDefine> formGroupDefines = this.settingUtil.queryFormGroupByFormSchemeKey(formSchemeKey);
            List<String> collect = formGroupDefines.stream().map(e -> e.getKey()).collect(Collectors.toList());
            Map<String, UploadStateNew> uploadStates = this.queryUploadStates(formSchemeKey, reportDataParam.getUnitId(), reportDataParam.getPeriod(), collect);
            List<String> hasAuthFormKeys = authForm.get(reportDataParam.getUnitId());
            List<String> hasAuthGroupKeys = authGroup.get(reportDataParam.getUnitId());
            ReportDataResult reportDataResult = null;
            for (FormGroupDefine formGroupDefine : formGroupDefines) {
                if (!hasAuthGroupKeys.contains(formGroupDefine.getKey())) continue;
                reportDataResult = new ReportDataResult();
                String key = formGroupDefine.getKey();
                reportDataResult.setId(formGroupDefine.getKey());
                reportDataResult.setTitle(formGroupDefine.getTitle());
                UploadStateNew uploadStateNew = uploadStates.get(key);
                if (uploadStateNew != null && uploadStateNew.getTaskId() != null) {
                    Date startTime = uploadStateNew.getStartTime();
                    reportDataResult.setStartStatus(GridDataItem.StartState.SUCCESS.getState());
                    reportDataResult.setStartTime(startTime);
                } else {
                    DimensionValueSet dim = new DimensionValueSet();
                    dim.setValue("DATATIME", (Object)reportDataParam.getPeriod());
                    dim.setValue(this.settingUtil.getDwMainDimName(formSchemeKey), (Object)reportDataParam.getUnitId());
                    boolean bindProcess = this.workflow.bindProcess(formSchemeKey, dim, formGroupDefine.getKey());
                    if (bindProcess) {
                        reportDataResult.setStartStatus(GridDataItem.StartState.FAIL.getState());
                        reportDataResult.setStartTime(null);
                    } else {
                        reportDataResult.setStartStatus(GridDataItem.StartState.STOP.getState());
                        reportDataResult.setStartTime(null);
                    }
                }
                reportDataResult.setChildren(this.queryFormData(formSchemeKey, reportDataParam.getUnitId(), reportDataParam.getPeriod(), key, hasAuthFormKeys));
                results.add(reportDataResult);
            }
        }
        return results;
    }

    private List<ReportDataResult> queryFormData(String formSchemeKey, String unitId, String period, String groupKey, List<String> hasAuthFormKeys) {
        ArrayList<ReportDataResult> results = new ArrayList<ReportDataResult>();
        List<FormDefine> formByGroupKey = this.settingUtil.queryFormByGroupKey(groupKey);
        List<String> collect = formByGroupKey.stream().map(e -> e.getKey()).collect(Collectors.toList());
        Map<String, UploadStateNew> uploadStates = this.queryUploadStates(formSchemeKey, unitId, period, collect);
        ReportDataResult reportDataResult = null;
        for (FormDefine formGroupDefine : formByGroupKey) {
            if (!hasAuthFormKeys.contains(formGroupDefine.getKey())) continue;
            reportDataResult = new ReportDataResult();
            String key = formGroupDefine.getKey();
            reportDataResult.setId(formGroupDefine.getKey());
            reportDataResult.setTitle(formGroupDefine.getTitle());
            UploadStateNew uploadStateNew = uploadStates.get(key);
            if (uploadStateNew != null && uploadStateNew.getTaskId() != null) {
                Date startTime = uploadStateNew.getStartTime();
                reportDataResult.setStartStatus(GridDataItem.StartState.SUCCESS.getState());
                reportDataResult.setStartTime(startTime);
            } else {
                DimensionValueSet dim = new DimensionValueSet();
                dim.setValue("DATATIME", (Object)period);
                dim.setValue(this.settingUtil.getDwMainDimName(formSchemeKey), (Object)unitId);
                boolean bindProcess = this.workflow.bindProcess(formSchemeKey, dim, formGroupDefine.getKey());
                if (bindProcess) {
                    reportDataResult.setStartStatus(GridDataItem.StartState.FAIL.getState());
                    reportDataResult.setStartTime(null);
                } else {
                    reportDataResult.setStartStatus(GridDataItem.StartState.STOP.getState());
                    reportDataResult.setStartTime(null);
                }
            }
            results.add(reportDataResult);
        }
        return results;
    }

    private Map<String, UploadStateNew> queryUploadStates(String formSchemeKey, String unitId, String period, List<String> formOrGroupKeys) {
        HashMap<String, UploadStateNew> states = new HashMap<String, UploadStateNew>();
        try {
            DimensionValueSet dimensionValueSet = new DimensionValueSet();
            dimensionValueSet.setValue(this.settingUtil.getDwMainDimName(formSchemeKey), (Object)unitId);
            dimensionValueSet.setValue("DATATIME", (Object)period);
            DimensionCombination combination = this.workFlowDimensionBuilder.buildDimensionCombination(formSchemeKey, dimensionValueSet);
            List<UploadStateNew> uploadStates = this.queryUploadStateService.queryUploadStates(formSchemeKey, combination.toDimensionValueSet(), formOrGroupKeys, formOrGroupKeys);
            if (uploadStates != null && uploadStates.size() > 0) {
                for (UploadStateNew uploadStateNew : uploadStates) {
                    String formId = uploadStateNew.getFormId();
                    states.put(formId, uploadStateNew);
                }
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), (Throwable)e);
        }
        return states;
    }

    @Override
    public List<WorkflowDefineResult> queryWorkflows(String taskId) {
        ArrayList<WorkflowDefineResult> results = new ArrayList<WorkflowDefineResult>();
        WorkflowDefineResult defaultWorkflow = new WorkflowDefineResult();
        defaultWorkflow.setId("default");
        defaultWorkflow.setTitle("\u9ed8\u8ba4\u6d41\u7a0b");
        defaultWorkflow.setGrouped(true);
        ArrayList<WorkflowDefineResult> defaultChild = new ArrayList<WorkflowDefineResult>();
        WorkflowDefineResult workflowDefine = new WorkflowDefineResult();
        workflowDefine.setId(DEFAULT_KEY);
        workflowDefine.setTitle("\u9ed8\u8ba4\u6d41\u7a0b");
        defaultChild.add(workflowDefine);
        defaultWorkflow.setChildren(defaultChild);
        results.add(defaultWorkflow);
        WorkflowDefineResult workflow = new WorkflowDefineResult();
        workflow.setId("workflow");
        workflow.setTitle("\u5de5\u4f5c\u6d41");
        workflow.setGrouped(true);
        ArrayList<WorkflowDefineResult> workflowChild = new ArrayList<WorkflowDefineResult>();
        ObjectMapper mapper = new ObjectMapper();
        List<WorkFlowTreeNode> customWorkflowTreeNode = this.customWorkflowConfigService.queryPublishedWorkflowByTaskKey(taskId);
        for (WorkFlowTreeNode node : customWorkflowTreeNode) {
            try {
                String objStr = mapper.writeValueAsString(node.getData());
                WorkFlowDefine workFlowDefine = (WorkFlowDefine)mapper.readValue(objStr, WorkFlowDefine.class);
                WorkflowDefineResult workflowDefineResult = new WorkflowDefineResult();
                workflowDefineResult.setId(workFlowDefine.getId());
                workflowDefineResult.setTitle(workFlowDefine.getTitle());
                workflowChild.add(workflowDefineResult);
            }
            catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
        workflow.setChildren(workflowChild);
        results.add(workflow);
        WorkflowDefineResult customWorkflow = new WorkflowDefineResult();
        customWorkflow.setId("workflow");
        customWorkflow.setTitle("\u5b9a\u5236\u6d41\u7a0b");
        customWorkflow.setGrouped(true);
        ArrayList<WorkflowDefineResult> customChild = new ArrayList<WorkflowDefineResult>();
        if (this.workflowParamList != null && this.workflowParamList.size() > 0) {
            WorkflowDefineResult workflowDefineResult = null;
            List<WorkflowParam> designWorkflow = this.workflowParamList.get(0).getDesignWorkflow();
            for (WorkflowParam design : designWorkflow) {
                workflowDefineResult = new WorkflowDefineResult();
                workflowDefineResult.setId(design.getId());
                workflowDefineResult.setTitle(design.getTitle());
                customChild.add(workflowDefineResult);
            }
        }
        customWorkflow.setChildren(customChild);
        results.add(customWorkflow);
        return results;
    }

    @Override
    public void refreshStrategicPartici(String taskKey, String period) {
        String formSchemeKey = this.queryFormSchemeKey(taskKey, period);
        if (formSchemeKey != null) {
            this.workflowSettingService.refreshStrategicPartici(formSchemeKey, period);
            this.workflowSettingService.getStrategicParticiLog(formSchemeKey);
        }
    }

    @Override
    public String queryWorkflowKey(WorkflowRelation workflowRelation) {
        String formSchemeKey = this.queryFormSchemeKey(workflowRelation.getTaskKey(), workflowRelation.getPeriod());
        if (formSchemeKey != null) {
            WorkflowSettingDefine workflowSetting = this.workflowSettingDao.getWorkflowSettingByFormSchemeKey(formSchemeKey);
            if (workflowSetting != null && workflowSetting.getId() != null) {
                return workflowSetting.getWorkflowId();
            }
            return DEFAULT_KEY;
        }
        return null;
    }

    private String queryFormSchemeKey(String taskKey, String period) {
        try {
            SchemePeriodLinkDefine schemePeriodLink = this.runTimeViewController.getSchemePeriodLinkByPeriodAndTask(period, taskKey);
            return schemePeriodLink.getSchemeKey();
        }
        catch (Exception e) {
            logger.error("\u6ca1\u6709\u67e5\u8be2\u5230\u6307\u5b9a\u7684\u65f6\u671f\u6620\u5c04\u65b9\u6848,\u8bf7\u6838\u5b9e\u6570\u636e: \u4efb\u52a1key=" + taskKey + ",\u65f6\u671f=" + period);
            return null;
        }
    }

    private Map<String, List<String>> getAuthForm(String taskKey, String formSchemeKey, String period, String unitId, String dwMainDim) {
        HashMap<String, List<String>> map = new HashMap<String, List<String>>();
        DimensionValueSet dimensionValue = new DimensionValueSet();
        dimensionValue.setValue(dwMainDim, (Object)unitId);
        dimensionValue.setValue("DATATIME", (Object)period);
        AccessFormParam accessFormParam = new AccessFormParam();
        accessFormParam.setTaskKey(taskKey);
        accessFormParam.setFormSchemeKey(formSchemeKey);
        accessFormParam.setFormKeys(new ArrayList());
        DimensionCollection dimensionCollection = com.jiuqi.nr.data.access.util.DimensionValueSetUtil.buildDimensionCollection((DimensionValueSet)dimensionValue, (String)formSchemeKey);
        accessFormParam.setCollectionMasterKey(dimensionCollection);
        DimensionAccessFormInfo batchAccessForms = this.dataAccessFormService.getBatchAccessForms(accessFormParam);
        List accessForms = batchAccessForms.getAccessForms();
        for (DimensionAccessFormInfo.AccessFormInfo accessFormInfo : accessForms) {
            List formKeys = accessFormInfo.getFormKeys();
            Map dimensions = accessFormInfo.getDimensions();
            List dimensionValueSetList = com.jiuqi.nr.data.access.util.DimensionValueSetUtil.getDimensionValueSetList((Map)dimensions);
            for (DimensionValueSet dimensionValueSet : dimensionValueSetList) {
                String unitKey = dimensionValueSet.getValue(dwMainDim).toString();
                map.put(unitKey, formKeys);
            }
        }
        return map;
    }

    private Map<String, List<String>> getAuthGroup(Map<String, List<String>> hasAuthForm, String formSchemeKey) {
        HashMap<String, List<String>> map = new HashMap<String, List<String>>();
        for (Map.Entry<String, List<String>> entry : hasAuthForm.entrySet()) {
            String unitKey = entry.getKey();
            List<String> formKeys = entry.getValue();
            ArrayList<String> groups = new ArrayList<String>();
            for (String formKey : formKeys) {
                List groupDefines = this.runTimeViewController.listFormGroupByForm(formKey, formSchemeKey);
                for (FormGroupDefine groupDefine : groupDefines) {
                    if (!groupDefine.getFormSchemeKey().equals(formSchemeKey)) continue;
                    groups.add(groupDefine.getKey());
                }
            }
            map.put(unitKey, groups);
        }
        return map;
    }

    @Override
    public List<CorporateData> queryCorporateList(String taskKey) {
        if (StringUtils.isEmpty((String)taskKey)) {
            return new ArrayList<CorporateData>();
        }
        ArrayList<CorporateData> list = new ArrayList<CorporateData>();
        TaskOrgLinkListStream taskOrgLinkListStream = this.runTimeViewController.listTaskOrgLinkStreamByTask(taskKey);
        List taskOrgLinkDefines = taskOrgLinkListStream.auth().i18n().getList();
        if (taskOrgLinkDefines == null || taskOrgLinkDefines.isEmpty()) {
            return new ArrayList<CorporateData>();
        }
        for (TaskOrgLinkDefine taskOrgLinkDefine : taskOrgLinkDefines) {
            CorporateData corporateData = new CorporateData();
            corporateData.setKey(taskOrgLinkDefine.getEntity());
            corporateData.setCode(taskOrgLinkDefine.getKey());
            String entityAlias = taskOrgLinkDefine.getEntityAlias();
            if (entityAlias != null) {
                corporateData.setTitle(entityAlias);
            } else {
                IEntityDefine iEntityDefine = this.entityMetaService.queryEntity(taskOrgLinkDefine.getEntity());
                corporateData.setTitle(iEntityDefine.getTitle());
            }
            list.add(corporateData);
        }
        return list;
    }

    @Override
    public void refreshStrategicPartici(StartStateParam startStateParam, AsyncTaskMonitor asyncTaskMonitor) {
        String formSchemeKey = this.queryFormSchemeKey(startStateParam.getTaskKey(), startStateParam.getPeriod());
        if (formSchemeKey != null) {
            StateChangeObj stateChangeObj = this.buildStateChangeObj(startStateParam, formSchemeKey);
            WorkFlowType workFlowType = this.workflow.queryStartType(formSchemeKey);
            IBulidParam bulidParam = this.settingContextStrategy.bulidParam(workFlowType);
            Map<BusinessKey, String> buildBusinessKeyMap = bulidParam.buildBusinessKeyMap(stateChangeObj, false);
            asyncTaskMonitor.progressAndMessage(0.15, "");
            ArrayList<String> messageIds = new ArrayList<String>();
            for (Map.Entry<BusinessKey, String> entry : buildBusinessKeyMap.entrySet()) {
                BusinessKey businessKey = entry.getKey();
                String unitKey = entry.getValue();
                DimensionValueSet dimensionValueSet = this.dimensionUtil.buildDimension(businessKey);
                String period = dimensionValueSet.getValue("DATATIME").toString();
                Object adjustObj = dimensionValueSet.getValue("ADJUST");
                String adjust = adjustObj == null ? "" : adjustObj.toString();
                String formKey = businessKey.getFormKey();
                String corporateValue = this.workFlowDimensionBuilder.getCorporateValue(startStateParam.getTaskKey(), businessKey);
                List<Task> tasks = this.workflow.queryTasks(businessKey.getFormSchemeKey(), businessKey);
                String userTaskId = null;
                if (tasks != null && tasks.size() > 0) {
                    Task task = tasks.get(0);
                    userTaskId = task.getUserTaskId();
                }
                String messageId = this.workflow.getMessageId(formSchemeKey, period, unitKey, adjust, formKey, formKey, workFlowType, userTaskId, corporateValue);
                messageIds.add(ProcessBuilderUtils.produceUUIDKey(messageId));
            }
            this.todoManipulationServiceImpl.batchClearTodo(messageIds);
            asyncTaskMonitor.progressAndMessage(0.75, "");
            this.workflowSettingService.refreshStrategicPartici(formSchemeKey, startStateParam.getPeriod());
            this.workflowSettingService.getStrategicParticiLog(formSchemeKey);
            asyncTaskMonitor.progressAndMessage(1.0, "");
        }
    }

    private StateChangeObj buildStateChangeObj(StartStateParam startStateParam, String formSchemeKey) {
        Set<String> operatorParam = this.getOperatorParam(startStateParam);
        StateChangeObj stateChangeObj = new StateChangeObj();
        stateChangeObj.setFormSchemeId(formSchemeKey);
        stateChangeObj.setDataObj(operatorParam);
        stateChangeObj.setSelectAll(true);
        stateChangeObj.setPeriod(startStateParam.getPeriod());
        Set<String> formOrGroupKeys = startStateParam.getFormOrGroupKeys();
        if (formOrGroupKeys == null || formOrGroupKeys.size() == 0) {
            formOrGroupKeys = this.settingUtil.queryFormOrGroupKeyByFormSchemeKey(formSchemeKey);
            stateChangeObj.setReportAll(true);
        } else {
            stateChangeObj.setReportAll(false);
        }
        stateChangeObj.setReportList(formOrGroupKeys);
        stateChangeObj.setSettingId(DEFAULT_KEY);
        stateChangeObj.setStart(false);
        return stateChangeObj;
    }

    private Map<String, Map<String, Boolean>> queryStopDatas(String formSchemeKey, String period, List<String> unitKeys, List<String> formKeys, WorkFlowType workFlowType) {
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
        HashMap<String, Map<String, Boolean>> stopMap = new HashMap<String, Map<String, Boolean>>();
        HashMap<String, Boolean> innerMap = new HashMap<String, Boolean>();
        List<UnBindResult> queryDataByType = this.workflowSettingDao.queryDataByType(formSchemeKey, period, unitKeys, formKeys, workFlowType.getValue());
        if (queryDataByType != null && queryDataByType.size() > 0) {
            for (UnBindResult unBindResult : queryDataByType) {
                if (unBindResult == null || unBindResult.getUnitId() == null || !FlowsType.DEFAULT.equals((Object)formScheme.getFlowsSetting().getFlowsType()) || unBindResult == null || unBindResult.getUnitId() == null) continue;
                if (stopMap.get(unBindResult.getUnitId()) == null) {
                    innerMap = new HashMap();
                    innerMap.put(unBindResult.getReportId(), true);
                    stopMap.put(unBindResult.getUnitId(), innerMap);
                    continue;
                }
                Map booleanMap = (Map)stopMap.get(unBindResult.getUnitId());
                if (booleanMap.get(unBindResult.getReportId()) == null) {
                    booleanMap.put(unBindResult.getReportId(), true);
                }
                stopMap.put(unBindResult.getUnitId(), booleanMap);
            }
        }
        return stopMap;
    }

    private GridDataResult queryChild(QueryGridDataParam queryGridDataParam) {
        GridDataResult gridDataResult = new GridDataResult();
        String formSchemeKey = this.queryFormSchemeKey(queryGridDataParam.getTaskKey(), queryGridDataParam.getPeriod());
        if (formSchemeKey != null) {
            String dwMainDimName = this.dimensionUtil.getDwMainDimName(formSchemeKey);
            ArrayList<GridDataItem> gridDataItems = new ArrayList<GridDataItem>();
            List<IEntityRow> childrenData = this.getChildrenByFilter(queryGridDataParam, formSchemeKey);
            if (childrenData != null && childrenData.size() > 0) {
                List<String> unitKeys = childrenData.stream().map(e -> e.getEntityKeyData()).collect(Collectors.toList());
                List<String> startPage = PageUtil.pageList(unitKeys, queryGridDataParam.getPageSize(), queryGridDataParam.getPageNum());
                Map<String, DimensionValueSet> dimensionValueSetMap = this.getDimensions(formSchemeKey, startPage, queryGridDataParam);
                Map<String, StatusData> statusDataMap = this.queryStateMap(formSchemeKey, dimensionValueSetMap, dwMainDimName);
                List filterEntityRow = childrenData.stream().filter(e -> startPage.contains(e.getEntityKeyData())).collect(Collectors.toList());
                for (IEntityRow iEntityRow : filterEntityRow) {
                    if (iEntityRow == null) continue;
                    GridDataItem gridDataItem = this.buildGridDataItem(iEntityRow, queryGridDataParam, statusDataMap);
                    gridDataItems.add(gridDataItem);
                }
                gridDataResult.setGridDatas(gridDataItems);
                gridDataResult.setCount(unitKeys != null ? unitKeys.size() : 0);
            }
        }
        return gridDataResult;
    }

    private List<IEntityRow> getChildrenByFilter(QueryGridDataParam queryGridDataParam, String formSchemeKey) {
        List<IEntityRow> childrenData = this.getChildren(queryGridDataParam, formSchemeKey);
        if (StringUtils.isEmpty((String)queryGridDataParam.getSearchKeys())) {
            return childrenData;
        }
        if (StringUtils.isNotEmpty((String)queryGridDataParam.getSearchKeys())) {
            String searchKeys = queryGridDataParam.getSearchKeys();
            List datasByCode = childrenData.stream().filter(e -> e.getCode().contains(searchKeys)).collect(Collectors.toList());
            List datasByTitle = childrenData.stream().filter(e -> e.getTitle().contains(searchKeys)).collect(Collectors.toList());
            datasByCode.addAll(datasByTitle);
            List<IEntityRow> collect = datasByCode.stream().filter(WorkflowInstanceServiceImpl.distinctByKey(IEntityItem::getEntityKeyData)).collect(Collectors.toList());
            return collect;
        }
        return childrenData;
    }

    private Set<String> getOperatorParam(StartStateParam startStateParam) {
        Set<String> selectUnitKeys = new HashSet<String>();
        QueryGridDataParam gridDataParam = startStateParam.getQueryGridDataParam();
        if (gridDataParam != null) {
            boolean allChecked = gridDataParam.isAllChecked();
            List<String> selectKeys = gridDataParam.getSelectKeys();
            List<String> cancelSelectKeys = gridDataParam.getCancelSelectKeys();
            List<String> allGridDataByFliter = this.queryAllChildKeyByFliter(gridDataParam);
            if (allChecked) {
                if (cancelSelectKeys != null && cancelSelectKeys.size() > 0) {
                    allGridDataByFliter = allGridDataByFliter.stream().filter(e -> !cancelSelectKeys.contains(e)).collect(Collectors.toList());
                }
            } else {
                if (selectKeys != null && selectKeys.size() > 0) {
                    allGridDataByFliter = allGridDataByFliter.stream().filter(e -> selectKeys.contains(e)).collect(Collectors.toList());
                }
                if (cancelSelectKeys != null && cancelSelectKeys.size() > 0) {
                    allGridDataByFliter = allGridDataByFliter.stream().filter(e -> !cancelSelectKeys.contains(e)).collect(Collectors.toList());
                }
            }
            if (allGridDataByFliter != null && allGridDataByFliter.size() > 0) {
                selectUnitKeys = allGridDataByFliter.stream().map(e -> e).collect(Collectors.toSet());
            }
        }
        return selectUnitKeys;
    }

    private List<GridDataItem> queryChildrenData(QueryGridDataParam queryGridDataParam) {
        ArrayList<GridDataItem> gridDataItems = new ArrayList<GridDataItem>();
        String formSchemeKey = this.queryFormSchemeKey(queryGridDataParam.getTaskKey(), queryGridDataParam.getPeriod());
        if (formSchemeKey != null) {
            List<IEntityRow> childrenData = this.getChildren(queryGridDataParam, formSchemeKey);
            String dwMainDimName = this.dimensionUtil.getDwMainDimName(formSchemeKey);
            if (childrenData != null && childrenData.size() > 0) {
                List<String> unitIds = childrenData.stream().map(e -> e.getEntityKeyData()).collect(Collectors.toList());
                Map<String, DimensionValueSet> dimensionValueSetMap = this.getDimensions(formSchemeKey, unitIds, queryGridDataParam);
                Map<String, StatusData> statusDataMap = this.queryStateMap(formSchemeKey, dimensionValueSetMap, dwMainDimName);
                for (IEntityRow iEntityRow : childrenData) {
                    if (iEntityRow == null) continue;
                    GridDataItem gridDataItem = this.buildGridDataItem(iEntityRow, queryGridDataParam, statusDataMap);
                    gridDataItems.add(gridDataItem);
                }
            }
        }
        return gridDataItems;
    }

    private GridDataItem buildGridDataItem(IEntityRow iEntityRow, QueryGridDataParam queryGridDataParam, Map<String, StatusData> statusDataMap) {
        GridDataItem gridDataItem = new GridDataItem();
        gridDataItem.setKey(iEntityRow.getEntityKeyData());
        gridDataItem.setCode(iEntityRow.getCode());
        gridDataItem.setTitle(iEntityRow.getTitle());
        List<String> selectKeys = queryGridDataParam.getSelectKeys();
        List<String> cancelSelectKeys = queryGridDataParam.getCancelSelectKeys();
        if (queryGridDataParam.isAllChecked()) {
            if (cancelSelectKeys != null && cancelSelectKeys.size() > 0) {
                if (cancelSelectKeys.contains(iEntityRow.getEntityKeyData())) {
                    gridDataItem.setChecked(false);
                } else {
                    gridDataItem.setChecked(true);
                }
            } else {
                gridDataItem.setChecked(true);
            }
        } else if (selectKeys != null && selectKeys.size() > 0 && selectKeys.contains(iEntityRow.getEntityKeyData())) {
            gridDataItem.setChecked(true);
        }
        StatusData queryStatus = statusDataMap.get(iEntityRow.getEntityKeyData());
        if (queryStatus != null) {
            gridDataItem.setState(queryStatus.getStartStatus());
            gridDataItem.setStartTime(queryStatus.getStartTime());
        }
        return gridDataItem;
    }

    private List<String> queryAllChildKeyByFliter(QueryGridDataParam queryGridDataParam) {
        List<GridDataItem> childrenData = this.queryChildren(queryGridDataParam);
        if (StringUtils.isNotEmpty((String)queryGridDataParam.getSearchKeys())) {
            childrenData = this.fliterByKeys(queryGridDataParam, childrenData);
        }
        if (queryGridDataParam.getStates() != null && queryGridDataParam.getStates().size() > 0) {
            childrenData = this.fliterByStates(queryGridDataParam, childrenData);
        }
        List<String> keys = childrenData.stream().map(e -> e.getKey()).collect(Collectors.toList());
        return keys;
    }

    private List<GridDataItem> queryChildren(QueryGridDataParam queryGridDataParam) {
        ArrayList<GridDataItem> gridDataItems = new ArrayList<GridDataItem>();
        BpmQueryEntityData bpmQueryEntityData = new BpmQueryEntityData();
        String formSchemeKey = this.queryFormSchemeKey(queryGridDataParam.getTaskKey(), queryGridDataParam.getPeriod());
        if (formSchemeKey != null) {
            List<Object> childrenData = this.getChildren(queryGridDataParam, formSchemeKey);
            List<String> searchUnitKeys = queryGridDataParam.getSearchUnitKeys();
            if (searchUnitKeys != null && searchUnitKeys.size() > 0) {
                childrenData = childrenData.stream().filter(e -> searchUnitKeys.contains(e.getEntityKeyData())).collect(Collectors.toList());
            }
            if (childrenData != null && childrenData.size() > 0) {
                List<String> unitIds = childrenData.stream().map(e -> e.getEntityKeyData()).collect(Collectors.toList());
                Map<String, DimensionValueSet> dimensionValueSetMap = this.getDimensions(formSchemeKey, unitIds, queryGridDataParam);
                GridDataItem gridDataItem = null;
                for (IEntityRow iEntityRow : childrenData) {
                    gridDataItem = new GridDataItem();
                    if (iEntityRow == null) continue;
                    gridDataItem.setKey(iEntityRow.getEntityKeyData());
                    gridDataItem.setCode(iEntityRow.getCode());
                    gridDataItem.setTitle(iEntityRow.getTitle());
                    List<String> selectKeys = queryGridDataParam.getSelectKeys();
                    List<String> cancelSelectKeys = queryGridDataParam.getCancelSelectKeys();
                    if (queryGridDataParam.isAllChecked()) {
                        if (cancelSelectKeys != null && cancelSelectKeys.size() > 0) {
                            if (cancelSelectKeys.contains(iEntityRow.getEntityKeyData())) {
                                gridDataItem.setChecked(false);
                            } else {
                                gridDataItem.setChecked(true);
                            }
                        } else {
                            gridDataItem.setChecked(true);
                        }
                    } else if (selectKeys != null && selectKeys.size() > 0 && selectKeys.contains(iEntityRow.getEntityKeyData())) {
                        gridDataItem.setChecked(true);
                    }
                    DimensionValueSet dimensionValueSet = dimensionValueSetMap.get(iEntityRow.getEntityKeyData());
                    StatusData queryStatus = this.queryStatus(formSchemeKey, dimensionValueSet, queryGridDataParam.getTaskKey());
                    gridDataItem.setState(queryStatus.getStartStatus());
                    gridDataItem.setStartTime(queryStatus.getStartTime());
                    gridDataItems.add(gridDataItem);
                }
            }
        }
        return gridDataItems;
    }
}

