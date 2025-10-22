/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.np.logging.LogFactory
 *  com.jiuqi.np.logging.Logger
 *  com.jiuqi.np.period.PeriodConsts
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.np.period.PeriodUtil
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.nr.common.log.LogModuleEnum
 *  com.jiuqi.nr.common.util.DimensionValueSetUtil
 *  com.jiuqi.nr.context.annotation.NRContextBuild
 *  com.jiuqi.nr.data.engine.condition.IConditionCache
 *  com.jiuqi.nr.data.engine.condition.IFormConditionService
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormGroupDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.facade.TaskFlowsDefine
 *  com.jiuqi.nr.definition.facade.TaskGroupDefine
 *  com.jiuqi.nr.definition.internal.impl.FlowsType
 *  com.jiuqi.nr.definition.internal.impl.WorkFlowType
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.nr.period.modal.IPeriodRow
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.util.StringUtils
 *  javax.servlet.http.HttpServletResponse
 */
package com.jiuqi.nr.bpm.setting.service.impl;

import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.np.log.LogHelper;
import com.jiuqi.np.logging.LogFactory;
import com.jiuqi.np.logging.Logger;
import com.jiuqi.np.period.PeriodConsts;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.np.period.PeriodUtil;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.nr.bpm.Actor.ActorStrategyProvider;
import com.jiuqi.nr.bpm.ProcessEngine;
import com.jiuqi.nr.bpm.businesskey.BusinessKey;
import com.jiuqi.nr.bpm.common.ProcessInstance;
import com.jiuqi.nr.bpm.common.UploadStateNew;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowDefine;
import com.jiuqi.nr.bpm.dataflow.service.IQueryUploadStateService;
import com.jiuqi.nr.bpm.de.dataflow.bean.WorkflowParam;
import com.jiuqi.nr.bpm.de.dataflow.common.WorkflowParamBase;
import com.jiuqi.nr.bpm.de.dataflow.service.IWorkflow;
import com.jiuqi.nr.bpm.de.dataflow.util.BusinessGenerator;
import com.jiuqi.nr.bpm.de.dataflow.util.DimensionUtil;
import com.jiuqi.nr.bpm.de.dataflow.util.IWorkFlowDimensionBuilder;
import com.jiuqi.nr.bpm.impl.common.BusinessKeyFormatter;
import com.jiuqi.nr.bpm.impl.common.NrParameterUtils;
import com.jiuqi.nr.bpm.impl.upload.modeling.DefaultProcessBuilder;
import com.jiuqi.nr.bpm.service.HistoryService;
import com.jiuqi.nr.bpm.service.RunTimeService;
import com.jiuqi.nr.bpm.setting.bean.ChangeProcessEvent;
import com.jiuqi.nr.bpm.setting.bean.WorkflowSettingDefine;
import com.jiuqi.nr.bpm.setting.dao.impl.WorkflowSettingDao;
import com.jiuqi.nr.bpm.setting.pojo.BaseData;
import com.jiuqi.nr.bpm.setting.pojo.CustomPeriodData;
import com.jiuqi.nr.bpm.setting.pojo.DataParam;
import com.jiuqi.nr.bpm.setting.pojo.Entites;
import com.jiuqi.nr.bpm.setting.pojo.EntitryCount;
import com.jiuqi.nr.bpm.setting.pojo.ITreeNode;
import com.jiuqi.nr.bpm.setting.pojo.ProcessExcelParam;
import com.jiuqi.nr.bpm.setting.pojo.ProcessTrackExcelInfo;
import com.jiuqi.nr.bpm.setting.pojo.ProcessTrackPrintData;
import com.jiuqi.nr.bpm.setting.pojo.ReportData;
import com.jiuqi.nr.bpm.setting.pojo.ReportParam;
import com.jiuqi.nr.bpm.setting.pojo.SearchResult;
import com.jiuqi.nr.bpm.setting.pojo.ShowNodeParam;
import com.jiuqi.nr.bpm.setting.pojo.ShowResult;
import com.jiuqi.nr.bpm.setting.pojo.StartState;
import com.jiuqi.nr.bpm.setting.pojo.StateChangeObj;
import com.jiuqi.nr.bpm.setting.pojo.StatusData;
import com.jiuqi.nr.bpm.setting.pojo.UnBindResult;
import com.jiuqi.nr.bpm.setting.pojo.WorkflowSettingPojo;
import com.jiuqi.nr.bpm.setting.service.WorkflowSettingService;
import com.jiuqi.nr.bpm.setting.service.impl.OperateWorkflow;
import com.jiuqi.nr.bpm.setting.service.impl.ShowProcess;
import com.jiuqi.nr.bpm.setting.tree.grid.pojo.GridDataResult;
import com.jiuqi.nr.bpm.setting.tree.grid.pojo.IGridParam;
import com.jiuqi.nr.bpm.setting.tree.pojo.WorkflowData;
import com.jiuqi.nr.bpm.setting.tree.pojo.WorkflowTree;
import com.jiuqi.nr.bpm.setting.utils.BpmQueryEntityData;
import com.jiuqi.nr.bpm.setting.utils.SettingUtil;
import com.jiuqi.nr.bpm.upload.WorkflowStatus;
import com.jiuqi.nr.common.log.LogModuleEnum;
import com.jiuqi.nr.common.util.DimensionValueSetUtil;
import com.jiuqi.nr.context.annotation.NRContextBuild;
import com.jiuqi.nr.data.engine.condition.IConditionCache;
import com.jiuqi.nr.data.engine.condition.IFormConditionService;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormGroupDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.facade.TaskFlowsDefine;
import com.jiuqi.nr.definition.facade.TaskGroupDefine;
import com.jiuqi.nr.definition.internal.impl.FlowsType;
import com.jiuqi.nr.definition.internal.impl.WorkFlowType;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nr.period.modal.IPeriodRow;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.util.StringUtils;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class WorkflowSettingServiceImpl
implements WorkflowSettingService {
    private static final Logger logger = LogFactory.getLogger(WorkflowSettingServiceImpl.class);
    private static final int ALL = 0;
    private static final int SUCESS = 1;
    private static final int FAIL = 2;
    private static final int PART_SUCESS = 3;
    private static final int STOP = 4;
    private static final String DEFAULT_KEY = "00000000-0000-0000-0000-000000000000";
    private static final String ALLGROUP = "all";
    private static final String ALLGROUP_TITLE = "\u5168\u90e8\u5206\u7ec4";
    @Autowired
    WorkflowSettingDao workflowSettingDao;
    @Autowired
    private IQueryUploadStateService queryUploadStateService;
    @Autowired
    private ActorStrategyProvider actorStrategyProvider;
    @Autowired(required=false)
    private List<WorkflowParamBase> workflowParamList;
    @Autowired
    private SettingUtil settingUtil;
    @Autowired
    private OperateWorkflow operateWorkflow;
    @Autowired
    private IWorkflow workflow;
    @Autowired
    private ShowProcess showProcess;
    @Autowired
    private IFormConditionService formConditionService;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private IPeriodEntityAdapter periodEntityAdapter;
    @Autowired
    private NrParameterUtils nrParameterUtils;
    @Autowired
    private DimensionUtil dimensionUtil;
    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private IWorkFlowDimensionBuilder workFlowDimensionBuilder;
    @Autowired
    private BusinessGenerator businessGenerator;

    @Override
    public String saveWorkFlowSettingData(WorkflowSettingPojo workflowSetting) {
        String settingId = "";
        try {
            FormSchemeDefine formScheme = this.settingUtil.getFormScheme(workflowSetting.getDataId());
            String taskKey = formScheme.getTaskKey();
            TaskDefine taskDefine = this.settingUtil.queryTasks(taskKey);
            PeriodWrapper currPeriod = this.getCurrPeriod(taskDefine);
            this.deleteWorkflowSetting(workflowSetting.getKey(), workflowSetting.getDataId(), currPeriod.toString());
            try {
                settingId = this.workflowSettingDao.insertData(workflowSetting);
                String taskName = taskDefine.getTitle();
                String formSchemeName = formScheme.getTitle();
                LogHelper.info((String)LogModuleEnum.NRPROCESS.getTitle(), (String)"\u65b0\u589e\u6d41\u7a0b\u65b9\u6848", (String)("\u65b0\u589e\u6d41\u7a0b\u65b9\u6848\u6210\u529f, \u6d41\u7a0b\u65b9\u6848\u540d\u79f0\uff1a" + workflowSetting.getTitle() + ", \u4efb\u52a1\u540d\u79f0\uff1a" + taskName + ", \u62a5\u8868\u65b9\u6848\u540d\u79f0\uff1a" + formSchemeName + ", \u5de5\u4f5c\u6d41\uff1a" + workflowSetting.getWorkflowId()));
            }
            catch (Exception e) {
                LogHelper.info((String)LogModuleEnum.NRPROCESS.getTitle(), (String)"\u65b0\u589e\u6d41\u7a0b\u65b9\u6848", (String)"\u65b0\u589e\u6d41\u7a0b\u65b9\u6848\u5931\u8d25");
                logger.error(e.getMessage(), (Throwable)e);
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), (Throwable)e);
        }
        try {
            ChangeProcessEvent event = new ChangeProcessEvent();
            event.setFormSchemeKey(workflowSetting.getDataId());
            event.setProcessDefineKey(workflowSetting.getWorkflowId());
            this.applicationContext.publishEvent(event);
        }
        catch (Exception e) {
            logger.error("\u6269\u5c55\u7684\u4e8b\u4ef6\u62a5\u9519", (Throwable)e);
        }
        return settingId;
    }

    @Override
    public List<ITreeNode> getTaskByCondition() throws Exception {
        ArrayList<ITreeNode> nodeList = new ArrayList<ITreeNode>();
        List<TaskDefine> taskDefines = this.getTaskDefines();
        if (taskDefines != null) {
            for (TaskDefine taskDefine : taskDefines) {
                ITreeNode treeNode = new ITreeNode();
                treeNode.setKey(taskDefine.getKey());
                treeNode.setTitle(taskDefine.getTitle());
                treeNode.setExpand(false);
                List<ITreeNode> formSchemeList = this.getFormSchemeList(taskDefine);
                if (formSchemeList.size() <= 0) {
                    treeNode.setChildren(null);
                    continue;
                }
                treeNode.setChildren(formSchemeList);
                nodeList.add(treeNode);
            }
        }
        return nodeList;
    }

    private List<TaskDefine> getTaskDefines() {
        ArrayList<TaskDefine> taskDefineResult = new ArrayList<TaskDefine>();
        try {
            List<TaskDefine> allTaskDefines = this.settingUtil.queryTasks();
            if (allTaskDefines != null && !allTaskDefines.isEmpty()) {
                for (TaskDefine taskDefine : allTaskDefines) {
                    FlowsType flowsType = taskDefine.getFlowsSetting().getFlowsType();
                    if (FlowsType.DEFAULT.equals((Object)flowsType)) {
                        taskDefineResult.add(taskDefine);
                        continue;
                    }
                    List<FormSchemeDefine> formSchemeByTask = this.settingUtil.queryFormSchemeByTaskKey(taskDefine.getKey());
                    for (FormSchemeDefine formSchemeDefine : formSchemeByTask) {
                        if (!FlowsType.DEFAULT.equals((Object)formSchemeDefine.getFlowsSetting().getFlowsType())) continue;
                        taskDefineResult.add(taskDefine);
                    }
                }
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), (Throwable)e);
        }
        return taskDefineResult;
    }

    private List<FormSchemeDefine> getFormSchemes(String taskKey) throws Exception {
        ArrayList<FormSchemeDefine> schemeList = new ArrayList<FormSchemeDefine>();
        if (taskKey == null) {
            return null;
        }
        List<FormSchemeDefine> queryFormSchemeByTask = this.settingUtil.queryFormSchemeByTaskKey(taskKey);
        if (queryFormSchemeByTask != null && !queryFormSchemeByTask.isEmpty()) {
            for (FormSchemeDefine formSchemeDefine : queryFormSchemeByTask) {
                WorkflowSettingDefine workflowSettingDefine;
                FlowsType flowsType = formSchemeDefine.getFlowsSetting().getFlowsType();
                if (FlowsType.DEFAULT.equals((Object)flowsType) && (workflowSettingDefine = this.getWorkflowDefineByFormSchemeKey(formSchemeDefine.getKey())) != null) continue;
                schemeList.add(formSchemeDefine);
            }
        }
        return schemeList;
    }

    private List<ITreeNode> getFormSchemeList(TaskDefine taskDefine) throws Exception {
        ArrayList<ITreeNode> nodeList = new ArrayList<ITreeNode>();
        if (taskDefine.getKey() == null) {
            return null;
        }
        List<FormSchemeDefine> formSchemes = this.getFormSchemes(taskDefine.getKey());
        if (formSchemes.size() > 0) {
            for (FormSchemeDefine formSchemeDefine : formSchemes) {
                ITreeNode treeNode = new ITreeNode();
                treeNode.setKey(formSchemeDefine.getKey());
                treeNode.setTitle(formSchemeDefine.getTitle());
                treeNode.setChildren(null);
                treeNode.setExpand(false);
                String entitiesKey = formSchemeDefine.getMasterEntitiesKey();
                String[] entitiesKeyArr = entitiesKey.split(";");
                ArrayList<Entites> entitiesList = new ArrayList<Entites>();
                Entites entites = null;
                boolean isFirst = true;
                for (int i = 0; i < entitiesKeyArr.length; ++i) {
                    entites = new Entites();
                    EntityViewDefine entityView = this.settingUtil.getEntityView(entitiesKeyArr[i]);
                    if (this.periodEntityAdapter.isPeriodEntity(entitiesKeyArr[i])) {
                        TableModelDefine periodEntityTableModel = this.periodEntityAdapter.getPeriodEntityTableModel(entitiesKeyArr[i]);
                        entites.setKey(entityView.getEntityId());
                        entites.setChecked(true);
                        if (entityView == null) continue;
                        if (periodEntityTableModel != null) {
                            entites.setTitle(periodEntityTableModel.getTitle());
                        }
                        entitiesList.add(entites);
                        continue;
                    }
                    TableModelDefine table = this.settingUtil.getTableByEntityId(entitiesKeyArr[i]);
                    if (isFirst) {
                        treeNode.setEntityKey(entitiesKeyArr[i]);
                        isFirst = false;
                        entites.setKey(entityView.getEntityId());
                        entites.setChecked(true);
                        if (entityView == null) continue;
                        if (table != null) {
                            entites.setTitle(table.getTitle());
                        }
                        entitiesList.add(entites);
                        continue;
                    }
                    entites.setKey(entityView.getEntityId());
                    entites.setChecked(false);
                    if (entityView == null) continue;
                    if (table != null) {
                        entites.setTitle(table.getTitle());
                    }
                    entitiesList.add(entites);
                }
                treeNode.setEntityList(entitiesList);
                PeriodWrapper currPeriod = this.getCurrPeriod(taskDefine);
                treeNode.setPeriod(currPeriod.toString());
                nodeList.add(treeNode);
            }
        }
        return nodeList;
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
        return WorkflowSettingServiceImpl.getCurrPeriod(periodType.type(), periodOffset, fromPeriod, toPeriod);
    }

    private static PeriodWrapper getCurrPeriod(int periodType, int periodOffset, String fromPeriod, String toPeriod) {
        GregorianCalendar calendar = new GregorianCalendar();
        PeriodWrapper currentPeriod = PeriodUtil.currentPeriod((GregorianCalendar)calendar, (int)periodType, (int)periodOffset);
        return currentPeriod;
    }

    @Override
    public void refreshStrategicPartici(String formSchemeKey, String period) {
        try {
            this.actorStrategyProvider.refreshActorsByFormSchemeKey(formSchemeKey, period);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), (Throwable)e);
        }
    }

    @Override
    public StartState startDataObjs(StateChangeObj stateChange) {
        StartState operateProcess = this.operateWorkflow.operateProcess(stateChange, this.queryStartType(stateChange.getFormSchemeId()), true);
        return operateProcess;
    }

    @Override
    public boolean clearDataObjs(StateChangeObj stateChange) {
        StartState operateProcess = this.operateWorkflow.operateProcess(stateChange, this.queryStartType(stateChange.getFormSchemeId()), true);
        return operateProcess.getStarted();
    }

    public Set<String> getDataObjs(Set<String> dataObj, String period, String formSchemeKey) {
        HashSet<String> ids = new HashSet<String>();
        BpmQueryEntityData queryEntityData = new BpmQueryEntityData();
        List<IEntityRow> allRows = queryEntityData.getEntityData(formSchemeKey, period);
        for (IEntityRow iEntityRow : allRows) {
            String entityKeyData = iEntityRow.getEntityKeyData();
            ids.add(entityKeyData);
        }
        if (null != dataObj) {
            ids.removeAll(dataObj);
        }
        return ids;
    }

    @Override
    public BaseData queryBaseParam(DataParam paramData) {
        BaseData baseDataParam = new BaseData();
        ArrayList<CustomPeriodData> customPeriodList = new ArrayList<CustomPeriodData>();
        CustomPeriodData customPeriodData = null;
        try {
            FormSchemeDefine formScheme = this.settingUtil.getFormScheme(paramData.getFormSchemeKey());
            TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(formScheme.getTaskKey());
            String dateTime = taskDefine.getDateTime();
            IPeriodRow curPeriod = this.periodEntityAdapter.getPeriodProvider(dateTime).getCurPeriod();
            String currPeriod = curPeriod.getCode();
            baseDataParam.setPeriod(currPeriod);
            List schemePeriod = this.runTimeViewController.querySchemePeriodLinkBySchemeSort(paramData.getFormSchemeKey());
            SchemePeriodLinkDefine fromPeriod = null;
            SchemePeriodLinkDefine toPeriod = null;
            if (schemePeriod != null && schemePeriod.size() > 0) {
                fromPeriod = (SchemePeriodLinkDefine)schemePeriod.get(0);
                toPeriod = (SchemePeriodLinkDefine)schemePeriod.get(schemePeriod.size() - 1);
                if (StringUtils.isEmpty((String)fromPeriod.getPeriodKey())) {
                    baseDataParam.setFromPeriod("1970Y0001");
                } else {
                    baseDataParam.setFromPeriod(fromPeriod.getPeriodKey());
                }
                if (StringUtils.isEmpty((String)toPeriod.getPeriodKey())) {
                    baseDataParam.setToPeriod("9999Y0001");
                } else {
                    baseDataParam.setToPeriod(toPeriod.getPeriodKey());
                }
                PeriodWrapper currentPeriodPw = new PeriodWrapper(currPeriod);
                PeriodWrapper fromPeriodPw = new PeriodWrapper(fromPeriod.getPeriodKey());
                PeriodWrapper toPeriodPw = new PeriodWrapper(toPeriod.getPeriodKey());
                if (StringUtils.isNotEmpty((String)fromPeriod.getPeriodKey()) || StringUtils.isNotEmpty((String)toPeriod.getPeriodKey())) {
                    if (StringUtils.isNotEmpty((String)toPeriod.getPeriodKey()) && currentPeriodPw.compareTo((Object)toPeriodPw) > 0) {
                        baseDataParam.setPeriod(toPeriod.getPeriodKey());
                    }
                    if (StringUtils.isNotEmpty((String)fromPeriod.getPeriodKey()) && currentPeriodPw.compareTo((Object)fromPeriodPw) < 0) {
                        baseDataParam.setPeriod(fromPeriod.getPeriodKey());
                    }
                }
            }
            List periodItems = this.periodEntityAdapter.getPeriodProvider(dateTime).getPeriodItems();
            for (IPeriodRow period : periodItems) {
                customPeriodData = new CustomPeriodData();
                customPeriodData.setCode(period.getCode());
                customPeriodData.setTitle(period.getTitle());
                customPeriodList.add(customPeriodData);
            }
            baseDataParam.setCustomPeriodDataList(customPeriodList);
            baseDataParam.setTaskDefine(taskDefine);
            baseDataParam.setFormSchemeDefine(formScheme);
            if (DEFAULT_KEY.equals(paramData.getWorkflowId())) {
                baseDataParam.setWorkflowTitle("\u9ed8\u8ba4\u6d41\u7a0b");
            } else {
                WorkFlowDefine workFlowDefine = this.settingUtil.getWorkFlowDefine(paramData.getWorkflowId());
                if (workFlowDefine != null && workFlowDefine.getId() != null) {
                    baseDataParam.setWorkflowTitle(workFlowDefine.getTitle());
                } else {
                    baseDataParam.setWorkflowTitle("\u9ed8\u8ba4\u6d41\u7a0b");
                }
            }
            WorkFlowType startType = this.queryStartType(paramData.getFormSchemeKey());
            if (WorkFlowType.ENTITY.equals((Object)startType)) {
                baseDataParam.setCommitType("\u6309\u4e3b\u4f53\u4e0a\u62a5");
            } else if (WorkFlowType.FORM.equals((Object)startType)) {
                baseDataParam.setCommitType("\u6309\u62a5\u8868\u4e0a\u62a5");
            } else if (WorkFlowType.GROUP.equals((Object)startType)) {
                baseDataParam.setCommitType("\u6309\u62a5\u8868\u5206\u7ec4\u4e0a\u62a5");
            }
            EntityViewDefine entityViewDefine = this.settingUtil.getEntityViewDefine(paramData.getFormSchemeKey());
            baseDataParam.setEntityKey(entityViewDefine.getEntityId());
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        return baseDataParam;
    }

    public boolean getCustomCurrPeriod(String fromPeriod, String toPeriod) {
        GregorianCalendar calendar = new GregorianCalendar();
        PeriodWrapper currentPeriod = PeriodUtil.currentPeriod((GregorianCalendar)calendar, (int)6, (int)0);
        GregorianCalendar currentCalendar = PeriodUtil.period2Calendar((PeriodWrapper)currentPeriod);
        GregorianCalendar fromCalendar = PeriodUtil.period2Calendar((String)fromPeriod);
        GregorianCalendar toCalendar = PeriodUtil.period2Calendar((String)toPeriod);
        return currentCalendar.compareTo(fromCalendar) >= 0 && currentCalendar.compareTo(toCalendar) <= 0;
    }

    @Override
    public String deleteWorkflowSetting(String settingId, String formSchemeKey, String period) {
        String message = "";
        if (null != settingId && !settingId.isEmpty()) {
            this.workflowSettingDao.delWorkFlowSettingBySchemeKey(formSchemeKey);
            try {
                FormSchemeDefine formScheme = this.settingUtil.getFormScheme(formSchemeKey);
                List<BusinessKey> buildBusinessKey = this.buildBusinessKey(formScheme, period, "");
                if (buildBusinessKey != null && buildBusinessKey.size() > 0) {
                    for (BusinessKey businessKey : buildBusinessKey) {
                        this.deleteWorkflowProcess(formSchemeKey, businessKey);
                    }
                } else {
                    message = "\u6d41\u7a0b\u5b9e\u4f8b\u672a\u5220\u9664\uff01";
                }
                if (message.isEmpty()) {
                    String taskKey = formScheme.getTaskKey();
                    TaskDefine queryTaskDefine = this.settingUtil.queryTasks(taskKey);
                    String taskName = queryTaskDefine.getTitle();
                    WorkflowSettingDefine workflowSettingDefine = this.workflowSettingDao.getWorkflowSettingDefineById(settingId);
                    if (null == workflowSettingDefine.getId()) {
                        LogHelper.info((String)LogModuleEnum.NRPROCESS.getTitle(), (String)"\u5220\u9664\u6d41\u7a0b\u65b9\u6848", (String)("\u5220\u9664\u6d41\u7a0b\u65b9\u6848\u6210\u529f, \u6d41\u7a0b\u65b9\u6848\u540d\u79f0\uff1a\u9ed8\u8ba4\u6d41\u7a0b, \u4efb\u52a1\u540d\u79f0\uff1a" + taskName + ", \u62a5\u8868\u65b9\u6848\u540d\u79f0\uff1a" + formScheme.getTitle()));
                    } else {
                        LogHelper.info((String)LogModuleEnum.NRPROCESS.getTitle(), (String)"\u5220\u9664\u6d41\u7a0b\u65b9\u6848", (String)("\u5220\u9664\u6d41\u7a0b\u65b9\u6848\u6210\u529f, \u6d41\u7a0b\u65b9\u6848\u540d\u79f0\uff1a" + workflowSettingDefine.getTitle() + ", \u4efb\u52a1\u540d\u79f0\uff1a" + taskName + ", \u62a5\u8868\u65b9\u6848\u540d\u79f0\uff1a" + formScheme.getTitle() + ", \u5de5\u4f5c\u6d41\uff1a" + workflowSettingDefine.getWorkflowId()));
                    }
                }
                return message;
            }
            catch (Exception e) {
                LogHelper.info((String)LogModuleEnum.NRPROCESS.getTitle(), (String)"\u5220\u9664", (String)"\u5220\u9664\u5931\u8d25");
                logger.error(e.getMessage(), (Throwable)e);
            }
        }
        return "";
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

    @Override
    public void deleteWorkflowProcess(String fromSchemeKey, BusinessKey businessKey) {
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

    @Override
    public List<WorkflowSettingDefine> queryWorkflowSettings() {
        ArrayList<WorkflowSettingDefine> workflowSettingDefines = new ArrayList();
        try {
            workflowSettingDefines = this.workflowSettingDao.getWorkflowSettingList();
        }
        catch (Exception e) {
            logger.error(e.getMessage(), (Throwable)e);
        }
        return workflowSettingDefines;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public void updateSettingState(String settingId, boolean state) {
        try {
            if (settingId != null && settingId.isEmpty()) return;
        }
        catch (Exception e) {
            logger.error(e.getMessage(), (Throwable)e);
        }
    }

    @Override
    @Deprecated
    public int entityQuerySetCount(EntitryCount entitryCount) {
        int count = 0;
        BpmQueryEntityData queryEntityData = new BpmQueryEntityData();
        try {
            EntityViewDefine entityView = this.settingUtil.getEntityViewDefine(entitryCount.getFormSchemeKey());
            if (entitryCount.getDefaultWorkflow().booleanValue()) {
                IEntityTable entityQuerySet = queryEntityData.entityQuerySet(entityView, entitryCount.getPeriod(), entitryCount.getFormSchemeKey());
                if (entityQuerySet != null) {
                    List allRows = entityQuerySet.getAllRows();
                    count = allRows.size();
                }
            } else if (null != entitryCount.getSettingId()) {
                List<String> unitList = this.workflowSettingDao.getUnitListBySetingId(entitryCount.getSettingId());
                if (unitList.size() > 0) {
                    String formSchemeKey = entitryCount.getFormSchemeKey();
                    String string = this.settingUtil.getDwMainDimName(formSchemeKey);
                } else {
                    IEntityTable entityQuerySet = queryEntityData.entityQuerySet(entityView, entitryCount.getPeriod(), entitryCount.getFormSchemeKey());
                    if (entityQuerySet != null) {
                        List allRows = entityQuerySet.getAllRows();
                        count = allRows.size();
                    }
                }
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), (Throwable)e);
        }
        return count;
    }

    @Override
    @NRContextBuild
    public ShowResult showWorkflow(ShowNodeParam nodeParam) {
        return this.showProcess.showWorkflow(nodeParam);
    }

    @Override
    public void exportExcel(HttpServletResponse response, ProcessExcelParam processExcelParam) {
        this.showProcess.exportExcel(response, processExcelParam);
    }

    @Override
    public ProcessTrackPrintData printProcessTrack(List<ProcessTrackExcelInfo> list) {
        return this.showProcess.printProcessTrack(list);
    }

    @Override
    public WorkflowSettingDefine getWorkflowDefineByFormSchemeKey(String formSchemeKey) {
        WorkflowSettingDefine workflowSetting = null;
        try {
            if (formSchemeKey == null) {
                return workflowSetting;
            }
            workflowSetting = this.workflowSettingDao.getWorkflowIdByFormSchemeKey(formSchemeKey);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), (Throwable)e);
        }
        return workflowSetting.getId() != null ? workflowSetting : null;
    }

    private List<WorkflowTree<WorkflowData>> getWorkflowByFromSchemeKey(FormSchemeDefine formScheme, String searchId) {
        ArrayList<WorkflowTree<WorkflowData>> workflowList = new ArrayList<WorkflowTree<WorkflowData>>();
        WorkflowSettingDefine workflowsetting = this.getWorkflowDefineByFormSchemeKey(formScheme.getKey());
        if (workflowsetting != null && workflowsetting.getId() != null && !DEFAULT_KEY.equals(workflowsetting.getWorkflowId())) {
            WorkflowData customWorkflowData = this.getCustomWorkflow(formScheme.getKey());
            if (customWorkflowData != null && customWorkflowData.getKey() != null) {
                WorkflowTree<WorkflowData> workflow = this.workflowData(formScheme, searchId, customWorkflowData);
                workflowList.add(workflow);
            }
        } else {
            WorkflowData defaultworkflowData = this.getDefaultWorkflow(formScheme);
            if (defaultworkflowData != null && defaultworkflowData.getKey() != null) {
                WorkflowTree<WorkflowData> workflow = this.workflowData(formScheme, searchId, defaultworkflowData);
                workflowList.add(workflow);
            }
        }
        return workflowList;
    }

    private WorkflowTree<WorkflowData> workflowData(FormSchemeDefine formScheme, String searchId, WorkflowData workflowData) {
        WorkflowTree<WorkflowData> workflow = new WorkflowTree<WorkflowData>();
        workflow.setData(workflowData);
        workflow.setKey(workflowData.getKey());
        workflow.setTitle(workflowData.getTitle());
        workflow.setExpand(false);
        if (searchId != null && !searchId.isEmpty() && workflowData.getWorkflowId().equals(searchId)) {
            workflow.setExpand(true);
            workflow.setSelected(true);
        }
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(formScheme.getTaskKey());
        PeriodWrapper currPeriod = this.getCurrPeriod(taskDefine);
        workflow.setPeriod(currPeriod.toString());
        return workflow;
    }

    private WorkflowData getDefaultWorkflow(FormSchemeDefine formScheme) {
        WorkflowData workflowData = new WorkflowData();
        String defaultProcessId = DefaultProcessBuilder.generateDefaultProcessId(formScheme.getFormSchemeCode());
        workflowData.setKey(defaultProcessId);
        workflowData.setTitle("\u9ed8\u8ba4\u6d41\u7a0b");
        workflowData.setDefaultWorkflow(true);
        workflowData.setDataId(formScheme.getKey());
        workflowData.setWorkflowId(DEFAULT_KEY);
        workflowData.setType(0);
        return workflowData;
    }

    private WorkflowData getCustomWorkflow(String formSchemeKey) {
        WorkflowData workflowData = new WorkflowData();
        WorkflowSettingDefine workflowsetting = this.getWorkflowDefineByFormSchemeKey(formSchemeKey);
        if (workflowsetting != null) {
            workflowData.setKey(workflowsetting.getId());
            workflowData.setTitle(workflowsetting.getTitle());
            workflowData.setDataId(formSchemeKey);
            workflowData.setDataType(workflowsetting.getDataType());
            workflowData.setWorkflowId(workflowsetting.getWorkflowId());
            workflowData.setDefaultWorkflow(false);
            workflowData.setType(1);
        }
        return workflowData;
    }

    private List<FormSchemeDefine> getFormSchemeList(String taskKey) throws Exception {
        ArrayList<FormSchemeDefine> schemeList = new ArrayList<FormSchemeDefine>();
        if (taskKey == null) {
            return null;
        }
        List<FormSchemeDefine> queryFormSchemeByTask = this.settingUtil.queryFormSchemeByTaskKey(taskKey);
        if (queryFormSchemeByTask != null && !queryFormSchemeByTask.isEmpty() && queryFormSchemeByTask != null && !queryFormSchemeByTask.isEmpty()) {
            for (FormSchemeDefine formSchemeDefine : queryFormSchemeByTask) {
                schemeList.add(formSchemeDefine);
            }
        }
        return schemeList;
    }

    @Override
    public List<GridDataResult> queryGridData(IGridParam gridParam) {
        BpmQueryEntityData queryEntityData = new BpmQueryEntityData();
        List<GridDataResult> gridList = new ArrayList<GridDataResult>();
        try {
            List<Object> allRows = new ArrayList();
            if (gridParam.getFormSchemeKey() != null) {
                if (gridParam.getSelectId().size() > 0 || gridParam.getStartState() != 0) {
                    return this.filterData(gridParam);
                }
                DimensionValueSet dimension = new DimensionValueSet();
                dimension.setValue("DATATIME", (Object)gridParam.getPeriod());
                dimension.setValue(this.settingUtil.getDwMainDimName(gridParam.getFormSchemeKey()), (Object)gridParam.getParentId());
                if ("".equals(gridParam.getParentId()) && gridParam.getParentId().isEmpty() && gridParam.isDirectChildren()) {
                    allRows = queryEntityData.getRootData(dimension, gridParam.getFormSchemeKey());
                    gridList = this.gridDataResultNoChildren(allRows, gridParam, gridParam.isSelected());
                } else if (gridParam.getParentId() != null && gridParam.isSelectCurrent()) {
                    IEntityRow currentData = queryEntityData.getCurrentData(gridParam.getFormSchemeKey(), gridParam.getPeriod(), gridParam.getParentId());
                    allRows.add(currentData);
                    gridList = this.gridDataResultNoChildren(allRows, gridParam, gridParam.isSelected());
                } else if (gridParam.getParentId() != null && gridParam.isDirectChildren()) {
                    allRows = queryEntityData.getDirectChildrenData(gridParam.getFormSchemeKey(), gridParam.getPeriod(), gridParam.getParentId());
                    gridList = this.gridDataResultNoChildren(allRows, gridParam, gridParam.isSelected());
                } else if (gridParam.getParentId() != null && gridParam.isAllChildren()) {
                    allRows = queryEntityData.getEntityData(gridParam.getFormSchemeKey(), gridParam.getPeriod());
                    gridList = this.gridDataResultNoChildren(allRows, gridParam, gridParam.isSelected());
                }
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), (Throwable)e);
        }
        return gridList;
    }

    private List<GridDataResult> filterData(IGridParam gridParam) {
        BpmQueryEntityData queryEntityData = new BpmQueryEntityData();
        ArrayList<GridDataResult> gridList = new ArrayList();
        ArrayList<IEntityRow> allRows = new ArrayList();
        DimensionValueSet dimension = new DimensionValueSet();
        dimension.setValue("DATATIME", (Object)gridParam.getPeriod());
        String dwMainDimName = this.settingUtil.getDwMainDimName(gridParam.getFormSchemeKey());
        if (gridParam.getSelectId() != null && gridParam.getSelectId().size() > 0) {
            dimension.setValue(dwMainDimName, gridParam.getSelectId());
        }
        allRows = queryEntityData.getEntityData(gridParam.getFormSchemeKey(), dimension);
        gridList = this.gridDataResult(allRows, dimension, gridParam, dwMainDimName);
        return gridList;
    }

    private List<GridDataResult> gridDataResult(List<IEntityRow> allRows, DimensionValueSet dimension, IGridParam gridParam, String dwMainDimName) {
        ArrayList<GridDataResult> gridList = new ArrayList<GridDataResult>();
        if (allRows != null && allRows.size() > 0) {
            for (IEntityRow rowData : allRows) {
                StatusData queryStatus = this.queryStatus(gridParam.getFormSchemeKey(), rowData.getEntityKeyData(), gridParam.getPeriod());
                GridDataResult gridTree = new GridDataResult();
                gridTree.setId(rowData.getEntityKeyData());
                gridTree.setTitle(rowData.getTitle());
                gridTree.setStartStatus(queryStatus.getStartStatus());
                Date startTime = queryStatus.getStartTime();
                if (startTime != null) {
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String time = simpleDateFormat.format(startTime);
                    gridTree.setStartTime(time);
                }
                gridTree.setLeafNode(true);
                gridTree.set_checked(gridParam.isSelected());
                gridList.add(gridTree);
            }
        }
        return gridList;
    }

    private Map<String, Boolean> getUnbindData(IGridParam gridParam, List<String> unitIds, WorkFlowType startType) {
        HashMap<String, Boolean> dataMap = new HashMap<String, Boolean>();
        ArrayList<String> reportIds = new ArrayList<String>();
        String formOrGroupKey = this.nrParameterUtils.getDefaultFormId(gridParam.getFormSchemeKey());
        reportIds.add(formOrGroupKey);
        reportIds = this.settingUtil.queryFormOrGroupByFormSchemeKey(gridParam.getFormSchemeKey());
        HashMap<String, ArrayList<UnBindResult>> unbindResultMap = new HashMap<String, ArrayList<UnBindResult>>();
        List<List<String>> partition = WorkflowSettingServiceImpl.partition(unitIds, 1000);
        for (List<String> list : partition) {
            List<UnBindResult> queryDatas = this.workflowSettingDao.queryDataByType(gridParam.getFormSchemeKey(), gridParam.getPeriod(), list, reportIds, startType.getValue());
            for (UnBindResult data : queryDatas) {
                ArrayList<UnBindResult> list2 = (ArrayList<UnBindResult>)unbindResultMap.get(data.getUnitId());
                if (list2 == null) {
                    list2 = new ArrayList<UnBindResult>();
                }
                list2.add(data);
                unbindResultMap.put(data.getUnitId(), list2);
            }
        }
        for (Map.Entry entry : unbindResultMap.entrySet()) {
            String key = (String)entry.getKey();
            List value = (List)entry.getValue();
            if (value == null) continue;
            if (reportIds != null && reportIds.size() == 0) {
                dataMap.put(key, true);
                continue;
            }
            if (reportIds.size() == value.size()) {
                dataMap.put(key, true);
                continue;
            }
            dataMap.put(key, false);
        }
        return dataMap;
    }

    public static <T> List<List<T>> partition(List<T> list, int size) {
        ArrayList<List<T>> result = new ArrayList<List<T>>();
        if (list == null) {
            throw new NullPointerException("list \u4e0d\u80fd\u4e3a null");
        }
        if (size <= 0) {
            throw new IllegalArgumentException("size \u5fc5\u987b\u5927\u4e8e 0");
        }
        if (list.size() < size) {
            result.add(list);
        } else {
            int r = list.size() / size;
            for (int i = 0; i < r; ++i) {
                List<T> subList = list.subList(i * size, (i + 1) * size);
                result.add(subList);
            }
            if (r * size < list.size()) {
                List<T> subList = list.subList(r * size, list.size());
                result.add(subList);
            }
        }
        return result;
    }

    private int convertStateValue(String preAction) {
        if ("start".equals(preAction)) {
            return 1;
        }
        if ("act_other_start".equals(preAction)) {
            return 3;
        }
        if ("stop".equals(preAction)) {
            return 4;
        }
        return 1;
    }

    private List<GridDataResult> gridDataResultNoChildren(List<IEntityRow> allRows, IGridParam gridParam, boolean selected) {
        BpmQueryEntityData queryEntityData = new BpmQueryEntityData();
        ArrayList<GridDataResult> gridList = new ArrayList<GridDataResult>();
        for (IEntityRow iEntityRow : allRows) {
            List<IEntityRow> directChildrenData;
            String parent;
            GridDataResult gridTree = new GridDataResult();
            String entityKeyData = iEntityRow.getEntityKeyData();
            String title = iEntityRow.getTitle();
            gridTree.setId(entityKeyData);
            gridTree.setTitle(title);
            StatusData queryStatus = this.queryStatus(gridParam.getFormSchemeKey(), entityKeyData, gridParam.getPeriod());
            if (queryStatus != null) {
                gridTree.setStartStatus(queryStatus.getStartStatus());
                Date startTime = queryStatus.getStartTime();
                if (startTime != null) {
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String time = simpleDateFormat.format(startTime);
                    gridTree.setStartTime(time);
                }
            }
            if ((parent = queryEntityData.getParent(gridParam.getFormSchemeKey(), gridParam.getPeriod(), entityKeyData)) != null) {
                gridTree.setParentId(parent);
            }
            if ((directChildrenData = queryEntityData.getDirectChildrenData(gridParam.getFormSchemeKey(), gridParam.getPeriod(), entityKeyData)).size() > 0) {
                gridTree.setLeafNode(false);
            } else {
                gridTree.setLeafNode(true);
            }
            gridTree.set_checked(selected);
            gridList.add(gridTree);
        }
        return gridList;
    }

    private StatusData queryStatus(String formSchemeKey, String unitId, String period) {
        block14: {
            StatusData statusData = new StatusData();
            try {
                List<UploadStateNew> uploadStateList;
                DimensionValueSet dim = new DimensionValueSet();
                dim.setValue("DATATIME", (Object)period);
                String dwMainDimName = this.dimensionUtil.getDwMainDimName(formSchemeKey);
                dim.setValue(dwMainDimName, (Object)unitId);
                FormSchemeDefine formScheme = this.settingUtil.getFormScheme(formSchemeKey);
                WorkFlowType queryStartType = this.queryStartType(formSchemeKey);
                if (WorkFlowType.ENTITY.equals((Object)queryStartType)) {
                    UploadStateNew uploadStateNew = this.queryUploadStateService.queryUploadState(formScheme.getKey(), dim);
                    if (uploadStateNew != null && uploadStateNew.getTaskId() != null) {
                        statusData.setStartStatus(1);
                        statusData.setStartTime(uploadStateNew.getStartTime());
                        return statusData;
                    }
                    String formOrGroupKey = this.nrParameterUtils.getDefaultFormId(formSchemeKey);
                    boolean bindProcess = this.workflow.bindProcess(formSchemeKey, dim, formOrGroupKey);
                    if (bindProcess) {
                        statusData.setStartStatus(2);
                        return statusData;
                    }
                    statusData.setStartStatus(4);
                    return statusData;
                }
                if (!WorkFlowType.FORM.equals((Object)queryStartType) && !WorkFlowType.GROUP.equals((Object)queryStartType)) break block14;
                DimensionValueSet dimensionValueSet = new DimensionValueSet();
                dimensionValueSet.setValue(this.settingUtil.getDwMainDimName(formSchemeKey), (Object)unitId);
                dimensionValueSet.setValue("DATATIME", (Object)period);
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
                if ((uploadStateList = this.queryUploadStateService.queryUploadStates(formScheme.getKey(), dim, keyList, keyList)) != null && uploadStateList.size() > 0) {
                    if (uploadStateList.size() < keyList.size()) {
                        statusData.setStartStatus(3);
                        statusData.setStartTime(uploadStateList.get(0).getStartTime());
                        return statusData;
                    }
                    statusData.setStartStatus(1);
                    statusData.setStartTime(uploadStateList.get(0).getStartTime());
                    return statusData;
                }
                ArrayList<Boolean> isBind = new ArrayList<Boolean>();
                List<String> ids = this.settingUtil.queryFormOrGroupByFormSchemeKey(formSchemeKey);
                for (String formOrGroupKey : ids) {
                    boolean bindProcess = this.workflow.bindProcess(formSchemeKey, dim, formOrGroupKey);
                    isBind.add(bindProcess);
                }
                if (isBind.contains(true)) {
                    statusData.setStartStatus(2);
                    return statusData;
                }
                statusData.setStartStatus(4);
                return statusData;
            }
            catch (Exception e) {
                logger.error(e.getMessage(), (Throwable)e);
            }
        }
        return null;
    }

    @Override
    public List<SearchResult> searchByInput(String inputText) {
        ArrayList<SearchResult> resultList = new ArrayList<SearchResult>();
        try {
            SearchResult searchResult;
            List<TaskDefine> taskDefines = this.getTaskDefines();
            for (TaskDefine taskDefine : taskDefines) {
                String title = taskDefine.getTitle();
                if (title.contains(inputText)) {
                    searchResult = new SearchResult();
                    searchResult.setKey(taskDefine.getKey());
                    searchResult.setTitle(title);
                    resultList.add(searchResult);
                    continue;
                }
                List<FormSchemeDefine> formSchemeList = this.getFormSchemeList(taskDefine.getKey());
                for (FormSchemeDefine formSchemeDefine : formSchemeList) {
                    if (!formSchemeDefine.getTitle().contains(inputText)) continue;
                    SearchResult searchResult2 = new SearchResult();
                    searchResult2.setKey(formSchemeDefine.getKey());
                    searchResult2.setTitle(formSchemeDefine.getTitle());
                    resultList.add(searchResult2);
                }
            }
            List<WorkflowSettingDefine> searchDefine = this.workflowSettingDao.searchDefineByinput(inputText);
            if (searchDefine.size() > 0) {
                for (WorkflowSettingDefine workflowSettingDefine : searchDefine) {
                    searchResult = new SearchResult();
                    searchResult.setKey(workflowSettingDefine.getId());
                    searchResult.setTitle(workflowSettingDefine.getTitle());
                    resultList.add(searchResult);
                }
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), (Throwable)e);
        }
        return resultList;
    }

    @Override
    public int getFormSchemeSizeByWorkflow(String workflowId) {
        if (workflowId == null) {
            return 0;
        }
        List<WorkflowSettingDefine> formSchemeByWorkflow = this.workflowSettingDao.getFormSchemeByWorkflow(workflowId);
        ArrayList<WorkflowSettingDefine> formSchemeTemps = new ArrayList<WorkflowSettingDefine>();
        for (WorkflowSettingDefine workflowSettingDefine : formSchemeByWorkflow) {
            String dataId = workflowSettingDefine.getDataId();
            FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(dataId);
            if (formScheme == null) continue;
            formSchemeTemps.add(workflowSettingDefine);
        }
        return formSchemeTemps.size();
    }

    @Override
    public void getStrategicParticiLog(String formSchemeKey) {
        FormSchemeDefine formScheme = this.settingUtil.getFormScheme(formSchemeKey);
        String taskKey = formScheme.getTaskKey();
        TaskDefine queryTaskDefine = this.settingUtil.queryTasks(taskKey);
        String taskName = queryTaskDefine.getTitle();
        String formSchemeName = formScheme.getTitle();
        try {
            if (formScheme != null) {
                LogHelper.info((String)LogModuleEnum.NRPROCESS.getTitle(), (String)"\u5237\u65b0\u53c2\u4e0e\u8005", (String)("\u5237\u65b0\u53c2\u4e0e\u8005\u6210\u529f, \u4efb\u52a1\u540d\u79f0\uff1a" + taskName + ", \u62a5\u8868\u65b9\u6848\u540d\u79f0\uff1a" + formSchemeName + ";"));
            }
        }
        catch (Exception e) {
            LogHelper.info((String)LogModuleEnum.NRPROCESS.getTitle(), (String)"\u5237\u65b0\u53c2\u4e0e\u8005", (String)("\u5237\u65b0\u53c2\u4e0e\u8005\u5931\u8d25, \u4efb\u52a1\u540d\u79f0\uff1a" + taskName + ", \u62a5\u8868\u65b9\u6848\u540d\u79f0\uff1a" + formSchemeName + ";"));
            logger.error(e.getMessage(), (Throwable)e);
        }
    }

    @Override
    public void autoStartLog(BusinessKey businessKey, List<IEntityRow> rows) {
        FormSchemeDefine formScheme = this.settingUtil.getFormScheme(businessKey.getFormSchemeKey());
        String taskKey = formScheme.getTaskKey();
        String dataContent = null;
        try {
            TaskDefine queryTaskDefine = this.settingUtil.queryTasks(taskKey);
            String taskName = queryTaskDefine.getTitle();
            String formSchemeName = formScheme.getTitle();
            for (IEntityRow row : rows) {
                dataContent = row.getCode() + "|" + row.getTitle();
                LogHelper.info((String)LogModuleEnum.NRPROCESS.getTitle(), (String)"\u81ea\u52a8\u542f\u52a8", (String)("\u81ea\u52a8\u542f\u52a8\u6210\u529f, \u4efb\u52a1\u540d\u79f0\uff1a" + taskName + ", \u62a5\u8868\u65b9\u6848\u540d\u79f0\uff1a" + formSchemeName + ", \u65f6\u671f\uff1a" + businessKey.getPeriod() + ", " + dataContent));
            }
        }
        catch (Exception e) {
            LogHelper.info((String)LogModuleEnum.NRPROCESS.getTitle(), (String)"\u81ea\u52a8\u542f\u52a8", (String)"\u81ea\u52a8\u542f\u52a8\u5931\u8d25");
            logger.error(e.getMessage(), (Throwable)e);
        }
    }

    @Override
    public List<WorkflowParam> getAllWorkflowList() {
        ArrayList<WorkflowParam> paramList = new ArrayList<WorkflowParam>();
        WorkflowParam workflowParam = null;
        workflowParam = new WorkflowParam();
        workflowParam.setId(DEFAULT_KEY);
        workflowParam.setTitle("\u9ed8\u8ba4\u6d41\u7a0b");
        workflowParam.setType(0);
        paramList.add(workflowParam);
        List<WorkFlowDefine> workflowByState = this.settingUtil.getWorkFlowDefine();
        if (workflowByState.size() > 0) {
            for (WorkFlowDefine workFlowDefine : workflowByState) {
                workflowParam = new WorkflowParam();
                workflowParam.setId(workFlowDefine.getId());
                workflowParam.setTitle(workFlowDefine.getTitle());
                workflowParam.setType(1);
                paramList.add(workflowParam);
            }
        }
        if (this.workflowParamList != null && this.workflowParamList.size() > 0) {
            List<WorkflowParam> designWorkflow = this.workflowParamList.get(0).getDesignWorkflow();
            paramList.addAll(designWorkflow);
        }
        return paramList;
    }

    @Override
    public List<ReportData> queryReportGroupData(ReportParam reportParam) {
        ArrayList<ReportData> reports = new ArrayList<ReportData>();
        ReportData reportData = null;
        try {
            DimensionValueSet dimensionValueSet = new DimensionValueSet();
            dimensionValueSet.setValue(this.settingUtil.getDwMainDimName(reportParam.getFormSchemeKey()), (Object)reportParam.getUnitId());
            dimensionValueSet.setValue("DATATIME", (Object)reportParam.getPeriod());
            IConditionCache conditionCache = this.formConditionService.getConditionForms(dimensionValueSet, reportParam.getFormSchemeKey());
            List formGroups = conditionCache.getSeeFormGroups(dimensionValueSet);
            List forms = conditionCache.getSeeForms(dimensionValueSet);
            if (formGroups != null && formGroups.size() > 0) {
                for (String formGroupKey : formGroups) {
                    reportData = new ReportData();
                    FormGroupDefine formGroupDefine = this.settingUtil.getFormGroupDefine(formGroupKey);
                    reportData.setId(formGroupDefine.getKey());
                    reportData.setTitle(formGroupDefine.getTitle());
                    if (reportParam.getUnitId() != null && WorkFlowType.GROUP.equals((Object)this.queryStartType(reportParam.getFormSchemeKey()))) {
                        Date startTime = this.queryStartTime(reportParam, formGroupDefine.getKey());
                        if (startTime != null) {
                            reportData.setStartStatus(1);
                            reportData.setStartTime(startTime);
                        } else {
                            DimensionValueSet dim = new DimensionValueSet();
                            dim.setValue("DATATIME", (Object)reportParam.getPeriod());
                            dim.setValue(this.settingUtil.getDwMainDimName(reportParam.getFormSchemeKey()), (Object)reportParam.getUnitId());
                            boolean bindProcess = this.workflow.bindProcess(reportParam.getFormSchemeKey(), dim, formGroupDefine.getKey());
                            if (bindProcess) {
                                reportData.setStartStatus(2);
                                reportData.setStartTime(startTime);
                            } else {
                                reportData.setStartStatus(4);
                                reportData.setStartTime(startTime);
                            }
                        }
                    }
                    reportData.setChildren(this.queryReportData(reportParam, formGroupDefine.getKey(), forms));
                    reports.add(reportData);
                }
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), (Throwable)e);
        }
        return reports;
    }

    public List<ReportData> queryReportData(ReportParam reportParam, String formGroupKey, List<String> forms) {
        ArrayList<ReportData> reports = new ArrayList<ReportData>();
        ReportData reportData = null;
        try {
            List<FormDefine> allForms = this.settingUtil.queryFormByGroupKey(formGroupKey);
            List collectForms = allForms.stream().filter(e -> forms.contains(e.getKey())).collect(Collectors.toList());
            if (collectForms != null && collectForms.size() > 0) {
                for (FormDefine formDefine : collectForms) {
                    reportData = new ReportData();
                    reportData.setId(formDefine.getKey());
                    reportData.setTitle(formDefine.getTitle());
                    if (reportParam.getUnitId() != null && WorkFlowType.FORM.equals((Object)this.queryStartType(reportParam.getFormSchemeKey()))) {
                        Date startTime = this.queryStartTime(reportParam, formDefine.getKey());
                        if (startTime != null) {
                            reportData.setStartStatus(1);
                            reportData.setStartTime(startTime);
                        } else {
                            DimensionValueSet dim = new DimensionValueSet();
                            dim.setValue("DATATIME", (Object)reportParam.getPeriod());
                            dim.setValue(this.settingUtil.getDwMainDimName(reportParam.getFormSchemeKey()), (Object)reportParam.getUnitId());
                            boolean bindProcess = this.workflow.bindProcess(reportParam.getFormSchemeKey(), dim, formDefine.getKey());
                            if (bindProcess) {
                                reportData.setStartStatus(2);
                                reportData.setStartTime(startTime);
                            } else {
                                reportData.setStartStatus(4);
                                reportData.setStartTime(startTime);
                            }
                        }
                    }
                    reportData.setChildren(null);
                    reports.add(reportData);
                }
            }
        }
        catch (Exception e2) {
            logger.error(e2.getMessage(), (Throwable)e2);
        }
        return reports;
    }

    private Date queryStartTime(ReportParam reportParam, String key) {
        Date startTime = null;
        try {
            List<DimensionValueSet> reportDimension;
            if (reportParam.getUnitId() != null && (reportDimension = this.dimensionUtil.appendReportDimension(reportParam.getFormSchemeKey(), reportParam.getUnitId(), reportParam.getPeriod())) != null && reportDimension.size() > 0) {
                DimensionValueSet dimensionValueSet = reportDimension.get(0);
                UploadStateNew uploadState = this.queryUploadStateService.queryUploadState(reportParam.getFormSchemeKey(), dimensionValueSet, key);
                if (uploadState != null) {
                    startTime = uploadState.getStartTime();
                }
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), (Throwable)e);
        }
        return startTime;
    }

    public StartState operateProcess(StateChangeObj stateChange) {
        StartState operateProcess = this.operateWorkflow.operateProcess(stateChange, this.queryStartType(stateChange.getFormSchemeId()), true);
        return operateProcess;
    }

    @Override
    public void unBindProcess(StateChangeObj stateChangeObj) {
        BpmQueryEntityData queryEntityData = new BpmQueryEntityData();
        WorkFlowType startType = this.workflow.queryStartType(stateChangeObj.getFormSchemeId());
        Set<Object> unitIds = new HashSet();
        HashSet<String> reportIds = new HashSet<String>();
        if (stateChangeObj.isSelectAll()) {
            List<String> entityIds = queryEntityData.getEntityIds(stateChangeObj.getFormSchemeId(), stateChangeObj.getPeriod());
            unitIds.addAll(new HashSet<String>(entityIds));
        } else {
            unitIds = stateChangeObj.getDataObj();
        }
        if (stateChangeObj.isReportAll()) {
            reportIds = this.quertReport(stateChangeObj, startType.getValue());
        } else if (WorkFlowType.ENTITY.equals((Object)startType)) {
            reportIds.add(this.settingUtil.getDefaultFormId(stateChangeObj.getFormSchemeId()));
        } else {
            reportIds = stateChangeObj.getReportList();
        }
        this.workflowSettingDao.insertReleate(stateChangeObj.getFormSchemeId(), stateChangeObj.getPeriod(), unitIds, reportIds, startType.getValue(), stateChangeObj.getSettingId());
        WorkFlowType queryStartType = this.queryStartType(stateChangeObj.getFormSchemeId());
        this.operateWorkflow.operateProcess(stateChangeObj, queryStartType, false);
    }

    private Set<String> quertReport(StateChangeObj stateChange, int startType) {
        HashSet<String> reportIds = new HashSet<String>();
        try {
            if (WorkFlowType.FORM.getValue() == startType) {
                List<FormDefine> allFormDefines = this.settingUtil.queryFormByFormSchemeKey(stateChange.getFormSchemeId());
                for (FormDefine formDefine : allFormDefines) {
                    reportIds.add(formDefine.getKey());
                }
            } else if (WorkFlowType.GROUP.getValue() == startType) {
                List<FormGroupDefine> allFormGroups = this.settingUtil.queryFormGroupByFormSchemeKey(stateChange.getFormSchemeId());
                for (FormGroupDefine formGroupDefine : allFormGroups) {
                    reportIds.add(formGroupDefine.getKey());
                }
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), (Throwable)e);
        }
        return reportIds;
    }

    @Override
    public WorkFlowDefine getWorkflowDefine(String formSchemeKey, String workflowId) {
        WorkFlowDefine define = new WorkFlowDefine();
        try {
            FormSchemeDefine formScheme = this.settingUtil.getFormScheme(formSchemeKey);
            if (formScheme != null) {
                define = this.settingUtil.getWorkFlowDefine(workflowId);
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), (Throwable)e);
        }
        return define;
    }

    @Override
    public WorkFlowType queryStartType(String formSchemeKey) {
        return this.settingUtil.queryStartType(formSchemeKey);
    }

    @Override
    public WorkflowStatus queryFlowType(String formSchemeKey) {
        try {
            FormSchemeDefine formScheme = this.settingUtil.getFormScheme(formSchemeKey);
            if (FlowsType.DEFAULT.equals((Object)formScheme.getFlowsSetting().getFlowsType())) {
                WorkflowSettingDefine workflowDefine = this.getWorkflowDefineByFormSchemeKey(formSchemeKey);
                if (workflowDefine != null && !DEFAULT_KEY.equals(workflowDefine.getWorkflowId())) {
                    return WorkflowStatus.WORKFLOW;
                }
                return WorkflowStatus.DEFAULT;
            }
            if (FlowsType.NOSTARTUP.equals((Object)formScheme.getFlowsSetting().getFlowsType())) {
                return WorkflowStatus.NOSTARTUP;
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), (Throwable)e);
        }
        return null;
    }

    @Override
    public WorkflowStatus queryFlowType(String formSchemeKey, String period, String unitId, String reportId) {
        try {
            FormSchemeDefine formScheme = this.settingUtil.getFormScheme(formSchemeKey);
            if (FlowsType.DEFAULT.equals((Object)formScheme.getFlowsSetting().getFlowsType())) {
                WorkflowSettingDefine workflowDefine = this.getWorkflowDefineByFormSchemeKey(formSchemeKey);
                if (workflowDefine == null || workflowDefine != null && DEFAULT_KEY.equals(workflowDefine.getWorkflowId())) {
                    boolean queryStartType = this.queryStartType(formSchemeKey, period, unitId, reportId);
                    if (queryStartType) {
                        return WorkflowStatus.NOSTARTUP;
                    }
                    return WorkflowStatus.DEFAULT;
                }
                boolean queryStartType = this.queryStartType(formSchemeKey, period, unitId, reportId);
                if (queryStartType) {
                    return WorkflowStatus.NOSTARTUP;
                }
                return WorkflowStatus.WORKFLOW;
            }
            if (FlowsType.NOSTARTUP.equals((Object)formScheme.getFlowsSetting().getFlowsType())) {
                return WorkflowStatus.NOSTARTUP;
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), (Throwable)e);
        }
        return null;
    }

    @Override
    public boolean queryStartType(String formSchemeKey, String period, String unitId, String reportId) {
        WorkFlowType startType = this.queryStartType(formSchemeKey);
        UnBindResult queryDataByType = this.workflowSettingDao.queryDataByType(formSchemeKey, period, unitId, reportId, startType.getValue());
        return queryDataByType != null && queryDataByType.getUnitId() != null;
    }

    @Override
    public void deleteBindData(String formSchemeKey, String period, String unitId, String reportId) {
        try {
            WorkFlowType startType = this.workflow.queryStartType(formSchemeKey);
            this.workflowSettingDao.delBindData(formSchemeKey, period, unitId, reportId, startType.getValue());
        }
        catch (Exception e) {
            logger.error(e.getMessage(), (Throwable)e);
        }
    }

    @Override
    public StartState startDataObjs(StateChangeObj stateChange, AsyncTaskMonitor asyncTaskMonitor) {
        asyncTaskMonitor.progressAndMessage(0.1, "");
        FormSchemeDefine formScheme = this.settingUtil.getFormScheme(stateChange.getFormSchemeId());
        TaskDefine tasks = this.settingUtil.queryTasks(formScheme.getTaskKey());
        StartState operateProcess = null;
        try {
            operateProcess = this.operateWorkflow.operateProcess(stateChange, true, asyncTaskMonitor);
            LogHelper.info((String)LogModuleEnum.NRPROCESS.getTitle(), (String)this.logTitle(stateChange, true), (String)("\u542f\u52a8\u6d41\u7a0b\u6210\u529f, \u4efb\u52a1\u540d\u79f0\uff1a" + tasks.getTitle() + ", \u62a5\u8868\u65b9\u6848\u540d\u79f0\uff1a" + formScheme.getTitle() + ", \u65f6\u671f\uff1a" + stateChange.getPeriod() + ", " + this.logInfo(stateChange)), new HashMap());
        }
        catch (Exception e) {
            LogHelper.info((String)LogModuleEnum.NRPROCESS.getTitle(), (String)this.logTitle(stateChange, true), (String)"\u542f\u52a8\u6d41\u7a0b\u5931\u8d25", new HashMap());
            logger.error(e.getMessage(), (Throwable)e);
        }
        return operateProcess;
    }

    @Override
    public boolean clearDataObjs(StateChangeObj stateChange, AsyncTaskMonitor asyncTaskMonitor) {
        asyncTaskMonitor.progressAndMessage(0.1, "");
        FormSchemeDefine formScheme = this.settingUtil.getFormScheme(stateChange.getFormSchemeId());
        TaskDefine tasks = this.settingUtil.queryTasks(formScheme.getTaskKey());
        StartState operateProcess = new StartState();
        try {
            operateProcess = this.operateWorkflow.operateProcess(stateChange, true, asyncTaskMonitor);
            LogHelper.info((String)LogModuleEnum.NRPROCESS.getTitle(), (String)this.logTitle(stateChange, true), (String)("\u6e05\u9664\u6d41\u7a0b\u6210\u529f, \u4efb\u52a1\u540d\u79f0\uff1a" + tasks.getTitle() + ", \u62a5\u8868\u65b9\u6848\u540d\u79f0\uff1a" + formScheme.getTitle() + ", \u65f6\u671f\uff1a" + stateChange.getPeriod() + ", " + this.logInfo(stateChange)));
        }
        catch (Exception e) {
            LogHelper.info((String)LogModuleEnum.NRPROCESS.getTitle(), (String)this.logTitle(stateChange, true), (String)"\u6e05\u9664\u6d41\u7a0b\u5931\u8d25");
            logger.error(e.getMessage(), (Throwable)e);
        }
        return operateProcess.getStarted();
    }

    @Override
    public boolean unBindProcess(StateChangeObj stateChangeObj, AsyncTaskMonitor asyncTaskMonitor) {
        BpmQueryEntityData queryEntityData = new BpmQueryEntityData();
        WorkFlowType startType = this.workflow.queryStartType(stateChangeObj.getFormSchemeId());
        Set<Object> unitIds = new HashSet();
        Set<Object> reportIds = new HashSet();
        if (stateChangeObj.isSelectAll()) {
            List<String> entityIds = queryEntityData.getEntityIds(stateChangeObj.getFormSchemeId(), stateChangeObj.getPeriod());
            unitIds.addAll(new HashSet<String>(entityIds));
        } else {
            unitIds = stateChangeObj.getDataObj();
        }
        if (stateChangeObj.isReportAll()) {
            reportIds = this.quertReport(stateChangeObj, startType.getValue());
        } else if (WorkFlowType.ENTITY.equals((Object)startType)) {
            String defaultFormId = this.settingUtil.getDefaultFormId(stateChangeObj.getFormSchemeId());
            reportIds.add(defaultFormId);
        } else {
            reportIds = stateChangeObj.getReportList();
        }
        asyncTaskMonitor.progressAndMessage(0.1, "");
        this.insertOrUpdate(stateChangeObj.getFormSchemeId(), stateChangeObj.getPeriod(), new ArrayList<Object>(unitIds), new ArrayList<Object>(reportIds), startType.getValue(), stateChangeObj.getSettingId(), startType);
        asyncTaskMonitor.progressAndMessage(0.4, "");
        StartState operateProcess = this.operateWorkflow.operateProcess(stateChangeObj, false, asyncTaskMonitor);
        return operateProcess.getStarted();
    }

    private void insertOrUpdate(String formSchemeKey, String period, List<String> unitIds, List<String> reportIds, int type, String settingId, WorkFlowType startType) {
        Map<String, List<String>> listMap = this.getBindResult(formSchemeKey, period, unitIds, reportIds, type);
        if (listMap != null && listMap.size() > 0) {
            if (WorkFlowType.ENTITY.equals((Object)startType)) {
                Set<String> unitKeys = listMap.keySet();
                unitIds.removeAll(unitKeys);
                if (unitIds.size() > 0) {
                    this.workflowSettingDao.insertReleate(formSchemeKey, period, new HashSet<String>(unitIds), new HashSet<String>(), type, settingId);
                }
            } else {
                Map<String, List<String>> unBindResult = this.buildUnBindResult(unitIds, reportIds);
                for (Map.Entry<String, List<String>> entry : unBindResult.entrySet()) {
                    HashSet<String> entityIds = new HashSet<String>();
                    String unitKey = entry.getKey();
                    List<String> list = entry.getValue();
                    List<String> bindFormKeys = listMap.get(unitKey);
                    if (bindFormKeys != null) {
                        list.removeAll(bindFormKeys);
                    }
                    if (list.size() <= 0) continue;
                    entityIds.add(unitKey);
                    this.workflowSettingDao.insertReleate(formSchemeKey, period, entityIds, new HashSet<String>(list), type, settingId);
                }
            }
        } else {
            HashSet<String> unitidSet = new HashSet();
            HashSet<String> reportidSet = new HashSet();
            if (unitIds != null && unitIds.size() > 0) {
                unitidSet = new HashSet<String>(unitIds);
            }
            if (reportIds != null && reportIds.size() > 0) {
                reportidSet = new HashSet<String>(reportIds);
            }
            if (unitidSet.size() > 0) {
                this.workflowSettingDao.insertReleate(formSchemeKey, period, unitidSet, reportidSet, type, settingId);
            }
        }
    }

    private Map<String, List<String>> buildUnBindResult(List<String> unitIds, List<String> reportIds) {
        HashMap<String, List<String>> result = new HashMap<String, List<String>>();
        for (String unitId : unitIds) {
            ArrayList<String> list = new ArrayList<String>();
            list.addAll(reportIds);
            result.put(unitId, list);
        }
        return result;
    }

    private Map<String, List<String>> getBindResult(String formSchemeKey, String period, List<String> unitIds, List<String> reportIds, int type) {
        HashMap<String, List<String>> result = new HashMap<String, List<String>>();
        List<UnBindResult> queryDataByType = this.workflowSettingDao.queryDataByType(formSchemeKey, period, unitIds, reportIds, type);
        if (queryDataByType != null && queryDataByType.size() > 0) {
            for (UnBindResult unBindResult : queryDataByType) {
                String unitId = unBindResult.getUnitId();
                String reportId = unBindResult.getReportId();
                ArrayList<String> keys = (ArrayList<String>)result.get(unitId);
                if (keys == null) {
                    keys = new ArrayList<String>();
                    keys.add(reportId);
                    result.put(unitId, keys);
                    continue;
                }
                ((List)result.get(unitId)).add(reportId);
            }
        }
        return result;
    }

    private String logTitle(StateChangeObj stateChange, boolean started) {
        String title = "";
        title = stateChange.isSelectAll() && stateChange.isReportAll() ? (started ? "\u5168\u90e8\u542f\u52a8\u6d41\u7a0b" : "\u5168\u90e8\u6e05\u9664\u6d41\u7a0b") : (stateChange.isSelectAll() || stateChange.isReportAll() ? (started ? "\u542f\u52a8\u90e8\u5206\u6d41\u7a0b" : "\u6e05\u9664\u90e8\u5206\u6d41\u7a0b") : (started ? "\u542f\u52a8\u6d41\u7a0b" : "\u6e05\u9664\u6d41\u7a0b"));
        return title;
    }

    private String logInfo(StateChangeObj stateChange) {
        StringBuffer sb = new StringBuffer();
        BpmQueryEntityData queryEntityData = new BpmQueryEntityData();
        Set<String> dataObj = stateChange.getDataObj();
        Set<String> reportList = stateChange.getReportList();
        List<Object> allRows = new ArrayList();
        if (dataObj != null && dataObj.size() > 0) {
            allRows = queryEntityData.getEntityData(stateChange.getFormSchemeId(), stateChange.getPeriod());
        }
        if (stateChange.isSelectAll()) {
            sb.append("\u6240\u6709\u5355\u4f4d;");
        } else if (dataObj.size() != 1) {
            sb.append(stateChange.getDataObj().size() + "\u5bb6\u5355\u4f4d,");
        } else if (dataObj.size() == 1) {
            for (IEntityRow iEntityRow : allRows) {
                sb.append("\u5355\u4f4d\uff1a" + iEntityRow.getCode() + "|" + iEntityRow.getTitle() + ";");
            }
        }
        if (stateChange.isReportAll()) {
            sb.append("\u6240\u6709\u8d44\u6e90;");
        } else if (reportList != null && reportList.size() != 1) {
            sb.append(reportList.size() + "\u4e2a\u8d44\u6e90;");
        } else if (reportList != null && reportList.size() == 1) {
            Map<String, String> formOrGroupTitle = this.settingUtil.queryFormOrGroupTitle(stateChange.getFormSchemeId(), reportList.iterator().next());
            for (Map.Entry<String, String> codeAndTitle : formOrGroupTitle.entrySet()) {
                sb.append(" \u8d44\u6e90\uff1a" + codeAndTitle.getKey() + "|" + codeAndTitle.getValue() + ";");
            }
        }
        return sb.toString();
    }

    @Override
    public List<WorkflowTree<WorkflowData>> getAllTask(String formSchemeKey, String searchId) {
        ArrayList<WorkflowTree<WorkflowData>> list = new ArrayList<WorkflowTree<WorkflowData>>();
        WorkflowTree<WorkflowData> workflowTree = null;
        try {
            List allTaskGroup = this.runTimeViewController.getAllTaskGroup();
            Map<String, List<TaskDefine>> taskMap = this.getTaskMap(allTaskGroup);
            workflowTree = new WorkflowTree<WorkflowData>();
            workflowTree.setKey(ALLGROUP);
            workflowTree.setTitle(ALLGROUP_TITLE);
            List queryTaskGroupDefine = this.queryTaskGroupDefine(allTaskGroup, taskMap, formSchemeKey, searchId, workflowTree);
            workflowTree.setChildren(queryTaskGroupDefine);
            List<WorkflowTree<WorkflowData>> childTasks = this.queryTaskDefine(taskMap.get(ALLGROUP), formSchemeKey, searchId, workflowTree);
            for (WorkflowTree<WorkflowData> workflowTree2 : childTasks) {
                workflowTree.appendChild(workflowTree2);
            }
            workflowTree.setGroupFlag(true);
            workflowTree.setExpand(true);
            list.add(workflowTree);
        }
        catch (Exception e) {
            logger.error("\u67e5\u8be2\u62a5\u9519");
        }
        return list;
    }

    private List<WorkflowTree<WorkflowData>> queryTaskGroupDefine(List<TaskGroupDefine> allTaskGroup, Map<String, List<TaskDefine>> taskMap, String formSchemeKey, String searchId, WorkflowTree<WorkflowData> workflowTree) {
        ArrayList<WorkflowTree<WorkflowData>> childlist = new ArrayList<WorkflowTree<WorkflowData>>();
        if (allTaskGroup != null && allTaskGroup.size() > 0) {
            List taskGroupHasParent = allTaskGroup.stream().filter(e -> StringUtils.isNotEmpty((String)e.getParentKey())).collect(Collectors.toList());
            List taskGroups = allTaskGroup.stream().filter(e -> StringUtils.isEmpty((String)e.getParentKey())).collect(Collectors.toList());
            for (TaskGroupDefine taskGroupDefine : taskGroups) {
                WorkflowTree<WorkflowData> paramInfo = new WorkflowTree<WorkflowData>();
                paramInfo.setKey(taskGroupDefine.getKey());
                paramInfo.setTitle(taskGroupDefine.getTitle());
                List groups = taskGroupHasParent.stream().filter(e -> e.getParentKey().equals(taskGroupDefine.getKey())).collect(Collectors.toList());
                for (TaskGroupDefine group : groups) {
                    this.appendChildGroup(group, paramInfo, formSchemeKey, searchId, taskMap.get(group.getKey()));
                }
                List<WorkflowTree<WorkflowData>> childTasksIngroup = this.queryTaskDefine(taskMap.get(taskGroupDefine.getKey()), formSchemeKey, searchId, paramInfo);
                for (WorkflowTree<WorkflowData> workflow : childTasksIngroup) {
                    paramInfo.appendChild(workflow);
                }
                paramInfo.setGroupFlag(true);
                childlist.add(paramInfo);
            }
        }
        return childlist;
    }

    private void appendChildGroup(TaskGroupDefine taskGroupDefine, WorkflowTree<WorkflowData> workflowTree, String formSchemeKey, String searchId, List<TaskDefine> taskInGroup) {
        WorkflowTree<WorkflowData> childTreeNode = new WorkflowTree<WorkflowData>();
        childTreeNode.setKey(taskGroupDefine.getKey());
        childTreeNode.setTitle(taskGroupDefine.getTitle());
        childTreeNode.setGroupFlag(true);
        List childTasks = this.queryTaskDefine(taskInGroup, formSchemeKey, searchId, childTreeNode);
        childTreeNode.setChildren(childTasks);
        workflowTree.appendChild(childTreeNode);
    }

    private List<WorkflowTree<WorkflowData>> queryTaskDefine(List<TaskDefine> allRunTimeTasksInGroup, String formSchemeKey, String searchId, WorkflowTree<WorkflowData> workflowTree) {
        ArrayList<WorkflowTree<WorkflowData>> childlist = new ArrayList<WorkflowTree<WorkflowData>>();
        try {
            for (TaskDefine taskDefine : allRunTimeTasksInGroup) {
                FlowsType flowsType;
                TaskDefine queryTaskDefine = this.runTimeViewController.queryTaskDefine(taskDefine.getKey());
                TaskFlowsDefine flowsSetting = queryTaskDefine.getFlowsSetting();
                if (null == flowsSetting || !FlowsType.DEFAULT.equals((Object)(flowsType = flowsSetting.getFlowsType())) && !FlowsType.WORKFLOW.equals((Object)flowsType)) continue;
                WorkflowTree<WorkflowData> paramInfo = new WorkflowTree<WorkflowData>();
                paramInfo.setKey(taskDefine.getKey());
                paramInfo.setTitle(taskDefine.getTitle());
                this.queryFormDefine(taskDefine.getKey(), formSchemeKey, searchId, paramInfo, flowsType);
                if (searchId != null && !searchId.isEmpty() && taskDefine.getKey().equals(searchId)) {
                    paramInfo.setSelected(true);
                    paramInfo.setExpand(true);
                    workflowTree.setExpand(true);
                }
                childlist.add(paramInfo);
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), (Throwable)e);
        }
        return childlist;
    }

    private void queryFormDefine(String taskKey, String formSchemeKey, String searchId, WorkflowTree<WorkflowData> workflowTree, FlowsType flowsType) {
        try {
            TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(taskKey);
            List<FormSchemeDefine> formSchemes = this.getFormSchemeList(taskKey);
            if (formSchemes.size() > 0) {
                for (FormSchemeDefine formScheme : formSchemes) {
                    if (!FlowsType.DEFAULT.equals((Object)flowsType) && !FlowsType.WORKFLOW.equals((Object)flowsType)) continue;
                    WorkflowTree<WorkflowData> paramInfo = new WorkflowTree<WorkflowData>();
                    paramInfo.setKey(formScheme.getKey());
                    paramInfo.setTitle(formScheme.getTitle());
                    paramInfo.setExpand(false);
                    if (searchId != null && !searchId.isEmpty() && formScheme.getKey().equals(searchId)) {
                        paramInfo.setSelected(true);
                        paramInfo.setExpand(true);
                        workflowTree.setExpand(true);
                    }
                    PeriodWrapper currPeriod = this.getCurrPeriod(taskDefine);
                    paramInfo.setPeriod(currPeriod.toString());
                    List<WorkflowTree<WorkflowData>> workflowByFromSchemeKey = this.getWorkflowByFromSchemeKey(formScheme, searchId);
                    for (WorkflowTree<WorkflowData> workflow : workflowByFromSchemeKey) {
                        if (workflow != null && workflow.getKey() != null) {
                            paramInfo.appendChild(workflow);
                        }
                        if (!workflow.isSelected()) continue;
                        workflowTree.setExpand(true);
                        if (formSchemeKey == null || formSchemeKey.isEmpty() || !formScheme.getKey().equals(formSchemeKey)) continue;
                        paramInfo.setExpand(true);
                    }
                    workflowTree.appendChild(paramInfo);
                }
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), (Throwable)e);
        }
    }

    private Map<String, List<TaskDefine>> getTaskMap(List<TaskGroupDefine> allTaskGroup) {
        LinkedHashMap<String, List<TaskDefine>> taskMap = new LinkedHashMap<String, List<TaskDefine>>();
        List allTaskDefines = this.runTimeViewController.getAllTaskDefines();
        ArrayList tempTasksInGroup = new ArrayList();
        for (TaskGroupDefine taskGroupDefine : allTaskGroup) {
            List tasksInGroup = this.runTimeViewController.getAllRunTimeTasksInGroup(taskGroupDefine.getKey());
            taskMap.put(taskGroupDefine.getKey(), tasksInGroup);
            tempTasksInGroup.addAll(tasksInGroup.stream().map(e -> e.getKey()).collect(Collectors.toList()));
        }
        allTaskDefines = allTaskDefines.stream().filter(e -> !tempTasksInGroup.contains(e.getKey())).collect(Collectors.toList());
        taskMap.put(ALLGROUP, allTaskDefines);
        return taskMap;
    }

    @Override
    public Map<String, Map<String, Boolean>> isProcessStop(String formSchemeKey, String period, List<String> unitKeys, List<String> formOrGroupKeys) {
        HashMap<String, Map<String, Boolean>> map = new HashMap<String, Map<String, Boolean>>();
        WorkFlowType workFlowType = this.workflow.queryStartType(formSchemeKey);
        HashMap<String, Boolean> dataMap = new HashMap<String, Boolean>();
        List<UnBindResult> queryDataByType = this.workflowSettingDao.queryDataByType(formSchemeKey, period, unitKeys, formOrGroupKeys, workFlowType.getValue());
        if (queryDataByType != null && queryDataByType.size() > 0) {
            for (UnBindResult data : queryDataByType) {
                String unitId = data.getUnitId();
                String reportId = data.getReportId();
                if (map.get(unitId) == null) {
                    dataMap = new HashMap();
                    dataMap.put(reportId, true);
                    map.put(unitId, dataMap);
                    continue;
                }
                Map stringBooleanMap = (Map)map.get(unitId);
                stringBooleanMap.put(reportId, true);
            }
        }
        return map;
    }
}

