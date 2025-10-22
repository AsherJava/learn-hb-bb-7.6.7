/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.OrderGenerator
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.core.context.ContextUser
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.definition.controller.IDataDefinitionDesignTimeController
 *  com.jiuqi.np.definition.internal.log.Log
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.nr.datascheme.api.DesignDataDimension
 *  com.jiuqi.nr.datascheme.api.DesignDataScheme
 *  com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DimensionType
 *  com.jiuqi.nr.definition.common.FormulaSyntaxStyle
 *  com.jiuqi.nr.definition.common.TaskGatherType
 *  com.jiuqi.nr.definition.common.TaskLinkExpressionType
 *  com.jiuqi.nr.definition.common.TaskLinkMatchingType
 *  com.jiuqi.nr.definition.common.TaskType
 *  com.jiuqi.nr.definition.controller.IDesignTimeViewController
 *  com.jiuqi.nr.definition.facade.DesignFormDefine
 *  com.jiuqi.nr.definition.facade.DesignFormSchemeDefine
 *  com.jiuqi.nr.definition.facade.DesignTaskDefine
 *  com.jiuqi.nr.definition.facade.DesignTaskGroupDefine
 *  com.jiuqi.nr.definition.facade.DesignTaskLinkDefine
 *  com.jiuqi.nr.definition.facade.TaskLinkOrgMappingRule
 *  com.jiuqi.nr.definition.internal.impl.DesignTaskFlowsDefine
 *  com.jiuqi.nr.definition.internal.impl.FlowsType
 *  com.jiuqi.nr.definition.internal.impl.FormSchemeExtendPropsDefaultValue
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.nr.period.modal.IPeriodEntity
 *  com.jiuqi.nr.single.core.para.LinkTaskBean
 *  com.jiuqi.nr.single.core.para.ParaInfo
 *  nr.single.map.data.facade.ISingleMapNrController
 *  nr.single.map.data.facade.SingleFileFmdmInfo
 *  nr.single.map.data.facade.SingleFileTaskInfo
 *  nr.single.map.data.facade.SingleMapFormSchemeDefine
 *  tk.mybatis.mapper.util.StringUtil
 */
package nr.single.para.parain.internal.service3;

import com.jiuqi.bi.util.OrderGenerator;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.core.context.ContextUser;
import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.definition.controller.IDataDefinitionDesignTimeController;
import com.jiuqi.np.definition.internal.log.Log;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.nr.datascheme.api.DesignDataDimension;
import com.jiuqi.nr.datascheme.api.DesignDataScheme;
import com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DimensionType;
import com.jiuqi.nr.definition.common.FormulaSyntaxStyle;
import com.jiuqi.nr.definition.common.TaskGatherType;
import com.jiuqi.nr.definition.common.TaskLinkExpressionType;
import com.jiuqi.nr.definition.common.TaskLinkMatchingType;
import com.jiuqi.nr.definition.common.TaskType;
import com.jiuqi.nr.definition.controller.IDesignTimeViewController;
import com.jiuqi.nr.definition.facade.DesignFormDefine;
import com.jiuqi.nr.definition.facade.DesignFormSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.definition.facade.DesignTaskGroupDefine;
import com.jiuqi.nr.definition.facade.DesignTaskLinkDefine;
import com.jiuqi.nr.definition.facade.TaskLinkOrgMappingRule;
import com.jiuqi.nr.definition.internal.impl.DesignTaskFlowsDefine;
import com.jiuqi.nr.definition.internal.impl.FlowsType;
import com.jiuqi.nr.definition.internal.impl.FormSchemeExtendPropsDefaultValue;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nr.period.modal.IPeriodEntity;
import com.jiuqi.nr.single.core.para.LinkTaskBean;
import com.jiuqi.nr.single.core.para.ParaInfo;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import nr.single.map.data.facade.ISingleMapNrController;
import nr.single.map.data.facade.SingleFileFmdmInfo;
import nr.single.map.data.facade.SingleFileTaskInfo;
import nr.single.map.data.facade.SingleMapFormSchemeDefine;
import nr.single.para.compare.bean.ParaImportInfoResult;
import nr.single.para.compare.definition.CompareDataTaskLinkDTO;
import nr.single.para.compare.definition.ISingleCompareDataTaskLinkService;
import nr.single.para.compare.definition.common.CompareDataType;
import nr.single.para.compare.definition.common.CompareUpdateType;
import nr.single.para.compare.internal.system.SingleParaOptionsService;
import nr.single.para.parain.internal.cache.TaskImportContext;
import nr.single.para.parain.service.IDataSchemeImportService;
import nr.single.para.parain.service.IEntityDefineImportService;
import nr.single.para.parain.service.IParaImportCommonService;
import nr.single.para.parain.service.IPeriodDefineImportService;
import nr.single.para.parain.service.ITaskDefineImportService;
import nr.single.para.parain.util.ISingleSchemePeriodUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.util.StringUtil;

@Service
public class TaskDefineImportServiceImpl
implements ITaskDefineImportService {
    private static final Logger log = LoggerFactory.getLogger(TaskDefineImportServiceImpl.class);
    @Autowired
    private IDesignTimeViewController viewController;
    @Autowired
    private IDataDefinitionDesignTimeController dataController;
    @Autowired
    private ISingleMapNrController mapController;
    @Autowired
    private IParaImportCommonService paraCommonService;
    @Autowired
    private IPeriodDefineImportService peroidDefineServcie;
    @Autowired
    private ISingleSchemePeriodUtil schemePeriodUtil;
    @Autowired
    private IDesignDataSchemeService dataSchemeSevice;
    @Autowired
    private IDataSchemeImportService dataSchemeImportServce;
    @Autowired
    private IPeriodEntityAdapter periodAdapter;
    @Autowired
    private ISingleCompareDataTaskLinkService taskLinkDataService;
    @Autowired
    private SingleParaOptionsService paraOptionService;

    @Override
    public void importTaskGroupDefines(TaskImportContext importContext) {
        String taskId = importContext.getTaskKey();
        if (importContext.getIsNewTask().booleanValue()) {
            List taskGroups = this.viewController.getAllTaskGroup();
            ParaInfo para = importContext.getParaInfo();
            String groupName = para.getTaskGroup();
            if (StringUtils.isNotEmpty((String)groupName)) {
                DesignTaskGroupDefine taskGroup = null;
                for (DesignTaskGroupDefine group : taskGroups) {
                    if (!groupName.equals(group.getTitle())) continue;
                    taskGroup = group;
                    break;
                }
                if (null == taskGroup) {
                    taskGroup = this.viewController.createTaskGroup();
                    taskGroup.setTitle(groupName);
                    taskGroup.setOwnerLevelAndId(importContext.getCurServerCode());
                    this.viewController.insertTaskGroupDefine(taskGroup);
                    this.viewController.addTaskToGroup(taskId, taskGroup.getKey());
                }
            }
        }
    }

    @Override
    public String importTaskDefine(TaskImportContext importContext) throws Exception {
        IPeriodEntity periodEntity;
        boolean isAnalTask;
        ParaInfo para = importContext.getParaInfo();
        DesignTaskDefine task = null;
        DesignTaskDefine baseTask = null;
        String analTaskFlag = "";
        boolean bl = isAnalTask = importContext.getBaseImportContext() != null && importContext.getBaseImportContext().getTaskDefine() != null;
        if (isAnalTask) {
            baseTask = importContext.getBaseImportContext().getTaskDefine();
            analTaskFlag = baseTask.getTaskCode();
            analTaskFlag = analTaskFlag + "_AL_" + importContext.getParaInfo().getPrefix();
        }
        Log.info((String)(importContext.getTaskKey() + para.getPrefix()));
        if (StringUtils.isNotEmpty((String)importContext.getTaskKey())) {
            task = this.viewController.queryTaskDefine(importContext.getTaskKey());
        } else {
            task = isAnalTask ? this.viewController.queryTaskDefineByCode(analTaskFlag) : this.viewController.queryTaskDefineByCode(para.getPrefix());
            if (task == null) {
                importContext.setTaskKey(UUID.randomUUID().toString());
            } else {
                importContext.setTaskKey(task.getKey());
            }
        }
        importContext.setIsNewTask(null == task);
        PeriodType periodType = this.paraCommonService.getTaskPeriod(importContext);
        if (importContext.getIsNewTask().booleanValue()) {
            task = this.viewController.createTaskDefine();
            task.setKey(importContext.getTaskKey());
            task.setTaskCode(this.getNetTaskCode(importContext.getImportOption().getTaskCode(), para));
            task.setDataScheme(importContext.getDataSchemeKey());
            task.setGroupName(para.getTaskGroup());
            task.setTitle(this.getNetTaskTitle(importContext.getImportOption().getTaskTitle(), para));
            task.setTaskGatherType(TaskGatherType.TASK_GATHER_MANUAL);
            task.setPeriodType(periodType);
            task.setOrder(OrderGenerator.newOrder());
            task.setFormulaSyntaxStyle(FormulaSyntaxStyle.FORMULA_SYNTAX_STYLE_TRADITION);
            task.setUpdateTime(new Date());
            this.setCreateUserAndTime(task);
            if (isAnalTask) {
                task.setTaskCode(analTaskFlag);
                task.setDataScheme(baseTask.getDataScheme());
                task.setDw(baseTask.getDw());
                task.setDateTime(baseTask.getDateTime());
                task.setDims(baseTask.getDims());
                task.setMeasureUnit(baseTask.getMeasureUnit());
                task.setTaskType(TaskType.TASK_TYPE_ANALYSIS);
                task.setFromPeriod(baseTask.getFromPeriod());
                task.setToPeriod(baseTask.getToPeriod());
                String analDataSchemeKey = this.dataSchemeImportServce.importDataSchemeDefineFromOther(importContext, baseTask.getDataScheme());
                task.setDataScheme(analDataSchemeKey);
            } else {
                task.setMeasureUnit("9493b4eb-6516-48a8-a878-25a63a23e63a;YUAN");
                task.setDw(importContext.getImportOption().getCorpEntityId());
                task.setDateTime(importContext.getImportOption().getDateEntityId());
                task.setDims(importContext.getImportOption().getDimEntityIds());
                if (StringUtils.isNotEmpty((String)task.getDateTime())) {
                    IPeriodEntity periodEntity2 = this.periodAdapter.getPeriodEntity(task.getDateTime());
                    if (periodEntity2 != null && periodEntity2.getType() != PeriodType.CUSTOM) {
                        task.setFromPeriod(para.getTaskYear() + this.paraCommonService.getPeriodTypeCode(periodEntity2.getType()) + "0001");
                        task.setToPeriod(this.paraCommonService.getLasPeriodCodeType(para.getTaskYear(), periodEntity2.getType()));
                    }
                    if (StringUtils.isNotEmpty((String)importContext.getImportOption().getFromPeriod())) {
                        task.setFromPeriod(importContext.getImportOption().getFromPeriod());
                    }
                    if (StringUtils.isNotEmpty((String)importContext.getImportOption().getToPeriod())) {
                        task.setToPeriod(importContext.getImportOption().getToPeriod());
                    }
                }
            }
            DesignTaskFlowsDefine flowObj = new DesignTaskFlowsDefine();
            flowObj.setDesignTableDefines(task.getDw() + ";" + task.getDateTime());
            flowObj.setFlowsType(FlowsType.DEFAULT);
            task.setFlowsSetting(flowObj);
            task.setOwnerLevelAndId(importContext.getCurServerCode());
            try {
                this.viewController.insertTaskDefine(task);
            }
            catch (Exception ex) {
                log.error(ex.getMessage(), ex);
                throw ex;
            }
        } else if (isAnalTask) {
            if (task != null) {
                task.setTaskType(TaskType.TASK_TYPE_ANALYSIS);
            }
        } else if (task != null && StringUtils.isNotEmpty((String)task.getDateTime()) && (periodEntity = this.periodAdapter.getPeriodEntity(task.getDateTime())) != null && periodEntity.getType() != PeriodType.CUSTOM) {
            String startPeriod = para.getTaskYear() + this.paraCommonService.getPeriodTypeCode(periodEntity.getType()) + "0001";
            String endPeriod = this.paraCommonService.getLasPeriodCodeType(para.getTaskYear(), periodEntity.getType());
            if (StringUtils.isNotEmpty((String)importContext.getImportOption().getFromPeriod())) {
                startPeriod = importContext.getImportOption().getFromPeriod();
            }
            if (StringUtils.isNotEmpty((String)importContext.getImportOption().getToPeriod())) {
                endPeriod = importContext.getImportOption().getToPeriod();
            }
            if (StringUtils.isNotEmpty((String)task.getFromPeriod())) {
                if (task.getFromPeriod().compareTo(startPeriod) > 0) {
                    task.setFromPeriod(startPeriod);
                }
            } else {
                task.setFromPeriod(startPeriod);
            }
            if (StringUtils.isNotEmpty((String)task.getToPeriod())) {
                if (task.getToPeriod().compareTo(endPeriod) < 0) {
                    task.setToPeriod(endPeriod);
                }
            } else {
                task.setToPeriod(endPeriod);
            }
            this.viewController.updateTaskDefine(task);
        }
        importContext.setTaskDefine(task);
        this.updateTaskTableGroup(importContext, task, true);
        if (task != null) {
            return task.getKey();
        }
        return null;
    }

    private void setCreateUserAndTime(DesignTaskDefine taskDefine) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        String dateStr = sdf.format(new Date());
        NpContext context = NpContextHolder.getContext();
        ContextUser user = context.getUser();
        String userName = "";
        if (user == null) {
            userName = "\u7ba1\u7406\u5458";
        } else {
            userName = user.getFullname();
            if ("admin".equals(context.getUserName())) {
                userName = "\u7ba1\u7406\u5458";
            }
            if (StringUtils.isEmpty((String)userName)) {
                userName = user.getName();
            }
        }
        taskDefine.setCreateTime(dateStr);
        taskDefine.setCreateUserName(userName);
    }

    private void CheckAddVersionEntity(DesignTaskDefine task) throws Exception {
        IEntityDefineImportService entityDefineServcie = this.dataSchemeImportServce.getEntityDefineServcieByEnityId(task.getDw());
        entityDefineServcie.CheckAddVersionEntity(task);
    }

    private void updateTaskTableGroup(TaskImportContext importContext, DesignTaskDefine task, boolean updateTitle) throws JQException {
    }

    @Override
    public void UpdateContextCache(TaskImportContext importContext) {
        DesignTaskDefine task = null;
        if (StringUtils.isNotEmpty((String)importContext.getTaskKey())) {
            task = this.viewController.queryTaskDefine(importContext.getTaskKey());
            importContext.setTaskDefine(task);
        }
        DesignFormSchemeDefine formScheme = null;
        if (StringUtils.isNotEmpty((String)importContext.getFormSchemeKey())) {
            formScheme = this.viewController.queryFormSchemeDefine(importContext.getFormSchemeKey());
            importContext.getSchemeInfoCache().setFormScheme(formScheme);
        }
    }

    @Override
    public void UpdateTask(TaskImportContext importContext) throws JQException {
        DesignTaskDefine task = importContext.getTaskDefine();
        DesignFormSchemeDefine formScheme = importContext.getSchemeInfoCache().getFormScheme();
        if (StringUtils.isEmpty((String)task.getTaskFilePrefix())) {
            // empty if block
        }
        if (StringUtils.isEmpty((String)task.getMasterEntitiesKey())) {
            if (StringUtils.isNotEmpty((String)formScheme.getMasterEntitiesKey()) && !formScheme.getMasterEntitiesKey().equalsIgnoreCase(FormSchemeExtendPropsDefaultValue.MASTER_ENTITIES_KEY_EXTEND_VALUE)) {
                task.setDw(formScheme.getDw());
                task.setDateTime(formScheme.getDateTime());
                task.setDims(formScheme.getDims());
            }
        } else if (importContext.getIsNewEntity().booleanValue()) {
            // empty if block
        }
        if (importContext.getIsNewTask().booleanValue()) {
            if (!StringUtils.isNotEmpty((String)formScheme.getFromPeriod()) || !formScheme.getFromPeriod().equalsIgnoreCase(FormSchemeExtendPropsDefaultValue.FROM_PERIOD_EXTEND_VALUE)) {
                // empty if block
            }
            if (!StringUtils.isNotEmpty((String)formScheme.getToPeriod()) || !formScheme.getFromPeriod().equalsIgnoreCase(FormSchemeExtendPropsDefaultValue.TO_PERIOD_EXTEND_VALUE)) {
                // empty if block
            }
        }
        if (StringUtils.isEmpty((String)task.getTaskCode())) {
            task.setTaskCode(this.getNetTaskCode(importContext.getImportOption().getTaskCode(), importContext.getParaInfo()));
        }
        if (task.getFlowsSetting() == null) {
            DesignTaskFlowsDefine flowObj = new DesignTaskFlowsDefine();
            flowObj.setDesignTableDefines(task.getDw() + ";" + task.getDateTime());
            flowObj.setFlowsType(FlowsType.DEFAULT);
            task.setFlowsSetting(flowObj);
        }
        task.setFormulaSyntaxStyle(FormulaSyntaxStyle.FORMULA_SYNTAX_STYLE_TRADITION);
        task.setUpdateTime(new Date());
        this.viewController.updateTaskDefine(task);
    }

    @Override
    public void UpdateMapSchemeDefineByTask(TaskImportContext importContext) {
        ParaInfo para;
        DesignTaskDefine task;
        SingleFileTaskInfo mapTask = importContext.getMapScheme().getTaskInfo();
        mapTask.setUpdateTime(new Date());
        DesignFormSchemeDefine formScheme = importContext.getSchemeInfoCache().getFormScheme();
        if (formScheme == null) {
            formScheme = this.viewController.queryFormSchemeDefine(importContext.getFormSchemeKey());
            importContext.getSchemeInfoCache().setFormScheme(formScheme);
        }
        if (null != formScheme) {
            mapTask.setNetFormSchemeFlag(formScheme.getTaskPrefix());
            mapTask.setNetFormSchemeKey(formScheme.getKey());
            mapTask.setNetFormSchemeTitle(formScheme.getTitle());
        }
        if ((task = importContext.getTaskDefine()) == null) {
            task = this.viewController.queryTaskDefine(importContext.getTaskKey());
            importContext.setTaskDefine(task);
        }
        if (null != task) {
            mapTask.setNetTaskFlag(task.getTaskCode());
            mapTask.setNetTaskKey(task.getKey());
            mapTask.setNetTaskTitle(task.getTitle());
        }
        if (null != (para = importContext.getParaInfo())) {
            mapTask.setSingleFileFlag(para.getFileFlag());
            mapTask.setSingleTaskFlag(para.getPrefix());
            mapTask.setSingleTaskTitle(para.getTaskName());
            mapTask.setSingleTaskYear(para.getTaskYear());
            mapTask.setSingleTaskPeriod(para.getTaskType());
            mapTask.setSingleTaskTime(para.getTaskTime());
            mapTask.setParaVersion(para.getTaskVerion());
            mapTask.setSingleFloatOrderField(para.getFloatOrderField());
        }
        SingleFileFmdmInfo fmdmInfo = importContext.getMapScheme().getFmdmInfo();
        if (null != para && null != para.getFmRepInfo() && null != fmdmInfo) {
            fmdmInfo.getZdmFieldCodes().clear();
            List zdms = para.getFmRepInfo().getZdmFields();
            List zbNameList = para.getFmRepInfo().getDefs().getZbNameList();
            for (String zdm : zdms) {
                fmdmInfo.getZdmFieldCodes().add(zdm);
            }
            fmdmInfo.setZdmLength(para.getFmRepInfo().getZDMLength());
            if (para.getFmRepInfo().getBBLXFieldId() > 0) {
                fmdmInfo.setBBLXField((String)zbNameList.get(para.getFmRepInfo().getBBLXFieldId()));
            }
            if (para.getFmRepInfo().getDWDMFieldId() > 0) {
                fmdmInfo.setDWDMField((String)zbNameList.get(para.getFmRepInfo().getDWDMFieldId()));
            }
            if (para.getFmRepInfo().getDWMCFieldId() > 0) {
                fmdmInfo.setDWMCField((String)zbNameList.get(para.getFmRepInfo().getDWMCFieldId()));
            }
            if (para.getFmRepInfo().getSJDMFieldId() > 0) {
                fmdmInfo.setSJDMField((String)zbNameList.get(para.getFmRepInfo().getSJDMFieldId()));
            }
            if (para.getFmRepInfo().getZBDMFieldId() > 0) {
                fmdmInfo.setZBDMField((String)zbNameList.get(para.getFmRepInfo().getZBDMFieldId()));
            }
            if (para.getFmRepInfo().getSNDMFieldId() > 0) {
                fmdmInfo.setSNDMField((String)zbNameList.get(para.getFmRepInfo().getSNDMFieldId()));
            }
            if (para.getFmRepInfo().getXBYSFieldId() > 0) {
                fmdmInfo.setXBYSField((String)zbNameList.get(para.getFmRepInfo().getXBYSFieldId()));
            }
            if (para.getFmRepInfo().getSQFieldId() > 0) {
                fmdmInfo.setPeriodField((String)zbNameList.get(para.getFmRepInfo().getSQFieldId()));
            }
        }
    }

    @Override
    public String importFormSchemeDefine(TaskImportContext importContext) throws Exception {
        String[] periodRanges;
        IPeriodEntity periodEntity;
        DesignFormSchemeDefine mapScheme;
        String taskKey = importContext.getTaskKey();
        String schemeKey = importContext.getFormSchemeKey();
        ParaInfo para = importContext.getParaInfo();
        DesignFormSchemeDefine formScheme = null;
        boolean isAnalTask = importContext.getIsAnalTask();
        DesignTaskDefine taskDefine = importContext.getTaskDefine();
        if (null == taskDefine) {
            taskDefine = this.viewController.queryTaskDefine(taskKey);
            importContext.setTaskDefine(taskDefine);
            if (null == taskDefine) {
                throw new Exception("\u8be5\u4efb\u52a1\u4e0d\u5b58\u5728\uff0c\u5148\u5bfc\u5165!");
            }
        }
        boolean isSameNewScheme = false;
        boolean isSingleNewScheme = false;
        boolean isAllowNewEntity = false;
        boolean isExpandTaskEntity = false;
        boolean isFormsEmpty = false;
        boolean isOnlyOneFormScheme = false;
        List formSchemes = null;
        if (StringUtils.isEmpty((String)schemeKey)) {
            formSchemes = this.viewController.queryFormSchemeByTask(taskKey);
            for (DesignFormSchemeDefine scheme : formSchemes) {
                if (!isAnalTask && !scheme.getTitle().equalsIgnoreCase("\u9ed8\u8ba4\u62a5\u8868\u65b9\u6848")) continue;
                if (StringUtils.isEmpty((String)scheme.getTaskPrefix())) {
                    formScheme = scheme;
                    scheme.setTaskPrefix(importContext.getParaInfo().getPrefix());
                    break;
                }
                if (!scheme.getTaskPrefix().equalsIgnoreCase(importContext.getParaInfo().getPrefix())) {
                    if (this.paraOptionService.isTaskStandardMode()) {
                        formScheme = null;
                        continue;
                    }
                    formScheme = scheme;
                    formScheme.setTaskPrefix(importContext.getParaInfo().getPrefix());
                    continue;
                }
                if (!scheme.getTaskPrefix().equalsIgnoreCase(importContext.getParaInfo().getPrefix())) continue;
                formScheme = scheme;
            }
        } else {
            formScheme = this.viewController.queryFormSchemeDefine(schemeKey);
            if (null != formScheme) {
                if (StringUtils.isNotEmpty((String)formScheme.getTaskPrefix())) {
                    if (!importContext.getParaInfo().getPrefix().equals(formScheme.getTaskPrefix())) {
                        if (this.paraOptionService.isTaskStandardMode()) {
                            formScheme = null;
                            isSameNewScheme = true;
                        } else {
                            formScheme.setTaskPrefix(importContext.getParaInfo().getPrefix());
                        }
                    }
                } else if (this.paraOptionService.isTaskStandardMode()) {
                    isSingleNewScheme = true;
                } else {
                    formScheme.setTaskPrefix(importContext.getParaInfo().getPrefix());
                }
                if (!isSameNewScheme || isSingleNewScheme) {
                    DesignFormDefine form;
                    List links;
                    List forms;
                    String masterCode;
                    boolean isDefaultEntinty = false;
                    if (StringUtils.isNotEmpty((String)formScheme.getMasterEntitiesKey())) {
                        masterCode = formScheme.getMasterEntitiesKey();
                        isDefaultEntinty = (masterCode = masterCode.trim().replace(";", "")).equalsIgnoreCase("d80867cf-ad66-4806-95d7-62a52cc15d7214c2e870-c4dc-4fa8-8384-a48b52207cd1");
                        isDefaultEntinty = isDefaultEntinty || masterCode.equalsIgnoreCase("14c2e870-c4dc-4fa8-8384-a48b52207cd1d80867cf-ad66-4806-95d7-62a52cc15d72");
                        isExpandTaskEntity = false;
                    } else if (StringUtils.isNotEmpty((String)taskDefine.getMasterEntitiesKey())) {
                        masterCode = taskDefine.getMasterEntitiesKey();
                        isDefaultEntinty = (masterCode = masterCode.trim().replace(";", "")).equalsIgnoreCase("d80867cf-ad66-4806-95d7-62a52cc15d7214c2e870-c4dc-4fa8-8384-a48b52207cd1");
                        isDefaultEntinty = isDefaultEntinty || masterCode.equalsIgnoreCase("14c2e870-c4dc-4fa8-8384-a48b52207cd1d80867cf-ad66-4806-95d7-62a52cc15d72");
                        isExpandTaskEntity = true;
                    } else {
                        isDefaultEntinty = true;
                        isExpandTaskEntity = true;
                        isAllowNewEntity = true;
                    }
                    List formSchems = this.viewController.queryFormSchemeByTask(taskDefine.getKey());
                    if (null != formSchems && formSchems.size() == 1) {
                        isOnlyOneFormScheme = true;
                    }
                    if (null != (forms = this.viewController.queryAllFormDefinesByFormScheme(schemeKey)) && forms.size() == 1 && (null == (links = this.viewController.getAllLinksInForm((form = (DesignFormDefine)forms.get(0)).getKey())) || links.size() == 0)) {
                        isFormsEmpty = true;
                    }
                    if (isDefaultEntinty && isFormsEmpty) {
                        isAllowNewEntity = true;
                    }
                }
            } else if (StringUtils.isEmpty((String)taskDefine.getMasterEntitiesKey())) {
                isAllowNewEntity = true;
            } else if ("null;null".equalsIgnoreCase(taskDefine.getMasterEntitiesKey())) {
                isAllowNewEntity = true;
            }
        }
        importContext.setIsNewScheme(null == formScheme);
        if (importContext.getIsNewScheme().booleanValue()) {
            formScheme = this.viewController.createFormSchemeDefine();
            if (StringUtils.isNotEmpty((String)schemeKey) && !isSameNewScheme && !isSingleNewScheme) {
                formScheme.setKey(schemeKey);
            }
            if (importContext.getIsNewTask().booleanValue()) {
                formScheme.setTitle(importContext.getTaskDefine().getTitle());
            } else {
                if (null == formSchemes) {
                    formSchemes = this.viewController.queryFormSchemeByTask(taskKey);
                }
                formScheme.setTitle(this.getSchemeTitle(importContext, para.getTaskName(), formSchemes));
            }
            formScheme.setTaskKey(taskKey);
            formScheme.setOrder(OrderGenerator.newOrder());
            formScheme.setFormSchemeCode(OrderGenerator.newOrder());
            formScheme.setPeriodType(this.paraCommonService.getTaskPeriod(importContext));
            formScheme.setTaskPrefix(importContext.getParaInfo().getPrefix());
            formScheme.setUpdateTime(new Date());
            importContext.getSchemeInfoCache().setFormScheme(formScheme);
            importContext.setFormSchemeKey(formScheme.getKey());
            mapScheme = this.mapController.QueryAndCreateSingleMapDefine(taskKey, formScheme.getKey());
            if (mapScheme != null) {
                mapScheme.setMapSchemeTitle("\u9ed8\u8ba4\u6620\u5c04\u65b9\u6848");
            }
            importContext.setMapScheme((SingleMapFormSchemeDefine)mapScheme);
            if (importContext.getImportOption().isHistoryPara() && formSchemes.size() > 0) {
                importContext.setIsNewEntity(false);
                this.setFormSchemeExpandTask(formScheme);
                this.paraCommonService.UpdatePeriodEntity(importContext);
            } else if (isAnalTask) {
                this.setFormSchemeExpandTask(formScheme);
            } else {
                this.changeFormSchemeEntitys(importContext, isAllowNewEntity, formScheme);
            }
            formScheme.setOwnerLevelAndId(importContext.getCurServerCode());
            this.viewController.insertFormSchemeDefine(formScheme);
        } else {
            if (StringUtils.isEmpty((String)formScheme.getFormSchemeCode())) {
                formScheme.setFormSchemeCode(OrderGenerator.newOrder());
            }
            if (StringUtils.isEmpty((String)formScheme.getFilePrefix()) && StringUtils.isNotEmpty((String)taskDefine.getTaskFilePrefix())) {
                mapScheme = this.viewController.queryFormSchemeDefineByFilePrefix(taskDefine.getTaskFilePrefix());
            }
            if (StringUtils.isEmpty((String)formScheme.getTaskPrefix())) {
                formScheme.setTaskPrefix(importContext.getParaInfo().getPrefix());
            }
            importContext.setFormSchemeKey(formScheme.getKey());
            importContext.getSchemeInfoCache().setFormScheme(formScheme);
            mapScheme = this.mapController.QueryAndCreateSingleMapDefine(taskKey, formScheme.getKey());
            if (mapScheme != null) {
                mapScheme.setMapSchemeTitle("\u9ed8\u8ba4\u6620\u5c04\u65b9\u6848");
            }
            importContext.setMapScheme((SingleMapFormSchemeDefine)mapScheme);
            if (isAllowNewEntity) {
                this.changeFormSchemeEntitys(importContext, isAllowNewEntity, formScheme);
            } else {
                this.updateFMDMEntity(importContext);
                this.paraCommonService.UpdatePeriodEntity(importContext);
            }
            formScheme.setUpdateTime(new Date());
            this.viewController.updateFormSchemeDefine(formScheme);
        }
        para.setNetFileFlag(formScheme.getFilePrefix());
        String fromPeriod = formScheme.getFromPeriod();
        String toPeriod = formScheme.getToPeriod();
        String startPeriod = null;
        String endPeriod = null;
        if (StringUtils.isNotEmpty((String)taskDefine.getDateTime())) {
            IPeriodEntity periodEntity2 = this.periodAdapter.getPeriodEntity(taskDefine.getDateTime());
            if (periodEntity2 != null && periodEntity2.getType() != PeriodType.CUSTOM) {
                startPeriod = para.getTaskYear() + this.paraCommonService.getPeriodTypeCode(periodEntity2.getType()) + "0001";
                endPeriod = this.paraCommonService.getLasPeriodCodeType(para.getTaskYear(), periodEntity2.getType());
            }
            if (StringUtils.isNotEmpty((String)importContext.getImportOption().getFromPeriod())) {
                startPeriod = importContext.getImportOption().getFromPeriod();
            }
            if (StringUtils.isNotEmpty((String)importContext.getImportOption().getToPeriod())) {
                endPeriod = importContext.getImportOption().getToPeriod();
            }
        }
        if (StringUtils.isNotEmpty(startPeriod) && StringUtils.isNotEmpty(endPeriod)) {
            fromPeriod = startPeriod;
            toPeriod = endPeriod;
        } else {
            if (StringUtils.isEmpty((String)fromPeriod) && taskDefine != null) {
                fromPeriod = taskDefine.getFromPeriod();
            }
            if (StringUtils.isEmpty((String)toPeriod) && taskDefine != null) {
                toPeriod = taskDefine.getToPeriod();
            }
        }
        String dateEntityId = null;
        if (StringUtils.isNotEmpty((String)taskDefine.getDateTime())) {
            periodEntity = this.periodAdapter.getPeriodEntity(taskDefine.getDateTime());
            dateEntityId = periodEntity.getKey();
        } else if (StringUtils.isNotEmpty((String)formScheme.getDateTime())) {
            periodEntity = this.periodAdapter.getPeriodEntity(formScheme.getDateTime());
            dateEntityId = periodEntity.getKey();
        }
        if ((StringUtils.isEmpty((String)fromPeriod) || StringUtils.isEmpty((String)toPeriod)) && (periodRanges = this.periodAdapter.getPeriodProvider(dateEntityId).getPeriodCodeRegion()).length > 1) {
            fromPeriod = periodRanges[0];
            toPeriod = periodRanges[1];
        }
        this.schemePeriodUtil.createSchemePeriodLink(importContext, formScheme.getKey(), fromPeriod, toPeriod, dateEntityId);
        return formScheme.getKey();
    }

    private void changeFormSchemeEntitys(TaskImportContext importContext, boolean isNewEntity, DesignFormSchemeDefine formScheme) throws Exception {
        DesignTaskFlowsDefine flowObj;
        DesignTaskDefine taskDefine = importContext.getTaskDefine();
        String taskKey = importContext.getTaskKey();
        importContext.setIsNewEntity(true);
        boolean canChangeTaskInfo = false;
        if (isNewEntity) {
            List formSchemes = this.viewController.queryFormSchemeByTask(taskKey);
            boolean bl = canChangeTaskInfo = null == formSchemes || formSchemes.size() <= 1;
            if (canChangeTaskInfo) {
                if (StringUtils.isEmpty((String)taskDefine.getTitle()) || StringUtils.isNotEmpty((String)taskDefine.getTitle()) && taskDefine.getTitle().contains("\u9ed8\u8ba4\u4efb\u52a1")) {
                    taskDefine.setTitle(this.getNetTaskTitle(importContext.getImportOption().getTaskTitle(), importContext.getParaInfo()));
                }
                this.updateTaskTableGroup(importContext, taskDefine, true);
            }
        }
        String entityViewKey = this.importFMDMEntity(importContext);
        String periodViewKey = this.ImportPeriodEntity(importContext);
        String dimViewKeys = this.importDimEntitys(importContext);
        formScheme.setDw(entityViewKey);
        formScheme.setDateTime(periodViewKey);
        formScheme.setDims(dimViewKeys);
        if (null != formScheme.getFlowsSetting()) {
            flowObj = (DesignTaskFlowsDefine)formScheme.getFlowsSetting();
            flowObj.setDesignTableDefines(formScheme.getDw() + ";" + formScheme.getDateTime());
        } else {
            flowObj = new DesignTaskFlowsDefine();
            flowObj.setDesignTableDefines(formScheme.getDw() + ";" + formScheme.getDateTime());
            flowObj.setFlowsType(FlowsType.DEFAULT);
            formScheme.setFlowsSetting(flowObj);
        }
        if (isNewEntity && canChangeTaskInfo) {
            formScheme.setPeriodType(this.paraCommonService.getTaskPeriod(importContext));
            taskDefine.setDw(formScheme.getDw());
            taskDefine.setDateTime(formScheme.getDateTime());
            taskDefine.setDims(formScheme.getDims());
            taskDefine.setPeriodType(formScheme.getPeriodType());
            if (null != taskDefine.getFlowsSetting()) {
                flowObj = (DesignTaskFlowsDefine)taskDefine.getFlowsSetting();
                flowObj.setDesignTableDefines(formScheme.getDw() + ";" + formScheme.getDateTime());
            } else {
                flowObj = new DesignTaskFlowsDefine();
                flowObj.setDesignTableDefines(formScheme.getDw() + ";" + formScheme.getDateTime());
                flowObj.setFlowsType(FlowsType.DEFAULT);
                taskDefine.setFlowsSetting(flowObj);
            }
            this.setFormSchemeExpandTask(formScheme);
        }
    }

    private void setFormSchemeExpandTask(DesignFormSchemeDefine formScheme) {
        formScheme.setDw(FormSchemeExtendPropsDefaultValue.MASTER_ENTITIES_KEY_EXTEND_VALUE);
        formScheme.setDateTime(FormSchemeExtendPropsDefaultValue.MASTER_ENTITIES_KEY_EXTEND_VALUE);
        formScheme.setDims(FormSchemeExtendPropsDefaultValue.MASTER_ENTITIES_KEY_EXTEND_VALUE);
        formScheme.setPeriodType(FormSchemeExtendPropsDefaultValue.PERIOD_TYPE_EXTEND_VALUE);
        if (null != formScheme.getFlowsSetting()) {
            DesignTaskFlowsDefine flowObj = (DesignTaskFlowsDefine)formScheme.getFlowsSetting();
            flowObj.setFlowsType(FormSchemeExtendPropsDefaultValue.FLOWS_TYPE_EXTEND_VALUE);
            flowObj.setDesignFlowSettingDefine(FormSchemeExtendPropsDefaultValue.FLOW_SETTING_EXTEND_VALUE);
        }
    }

    private String getSchemeTitle(TaskImportContext importContext, String title, List<DesignFormSchemeDefine> formSchemes) {
        String newTitle = importContext.getImportOption().getFormSchemeTitle();
        if (StringUtils.isEmpty((String)newTitle)) {
            newTitle = title;
        }
        HashMap<String, DesignFormSchemeDefine> schemeMap = new HashMap<String, DesignFormSchemeDefine>();
        for (DesignFormSchemeDefine scheme : formSchemes) {
            schemeMap.put(scheme.getTitle(), scheme);
        }
        int i = 1;
        while (schemeMap.containsKey(newTitle)) {
            newTitle = title + String.valueOf(i);
            ++i;
        }
        return newTitle;
    }

    private String getDwEntityId(TaskImportContext importContext) {
        String corpEntityKey = null;
        DesignTaskDefine task = importContext.getTaskDefine();
        if (task != null && StringUtils.isNotEmpty((String)task.getDw())) {
            corpEntityKey = task.getDw();
        } else {
            DesignDataScheme dataScheme = importContext.getDataScheme();
            List dimesions = this.dataSchemeSevice.getDataSchemeDimension(dataScheme.getKey());
            for (DesignDataDimension dim : dimesions) {
                if (dim.getDimensionType() != DimensionType.UNIT) continue;
                corpEntityKey = dim.getDimKey();
                break;
            }
        }
        return corpEntityKey;
    }

    private String importDimEntitys(TaskImportContext importContext) throws Exception {
        return this.dataSchemeImportServce.updateDimEntitys(importContext);
    }

    private String importFMDMEntity(TaskImportContext importContext) throws Exception {
        IEntityDefineImportService entityDefineServcie = this.dataSchemeImportServce.getEntityDefineServcieByEnityId(this.getDwEntityId(importContext));
        return entityDefineServcie.updateCorpEntity(importContext);
    }

    private String updateFMDMEntity(TaskImportContext importContext) throws Exception {
        IEntityDefineImportService entityDefineServcie = this.dataSchemeImportServce.getEntityDefineServcieByEnityId(this.getDwEntityId(importContext));
        return entityDefineServcie.updateCorpEntity(importContext);
    }

    private String ImportPeriodEntity(TaskImportContext importContext) throws Exception {
        return this.peroidDefineServcie.updateDateEntity(importContext);
    }

    @Override
    public void importTaskLinkDefines(TaskImportContext importContext) {
        HashMap<String, CompareDataTaskLinkDTO> oldTaskLinkDic = new HashMap<String, CompareDataTaskLinkDTO>();
        ParaImportInfoResult linksLog = null;
        if (importContext.getCompareInfo() != null) {
            CompareDataTaskLinkDTO taskLinkQueryParam = new CompareDataTaskLinkDTO();
            taskLinkQueryParam.setInfoKey(importContext.getCompareInfo().getKey());
            taskLinkQueryParam.setDataType(CompareDataType.DATA_TASKLINK);
            List<CompareDataTaskLinkDTO> oldTaskLinkList = this.taskLinkDataService.list(taskLinkQueryParam);
            for (CompareDataTaskLinkDTO oldData : oldTaskLinkList) {
                oldTaskLinkDic.put(oldData.getSingleLinkAlias(), oldData);
            }
            if (importContext.getImportResult() != null) {
                linksLog = importContext.getImportResult().getLogInfo(CompareDataType.DATA_TASKLINK, "taskLinks", "\u5173\u8054\u4efb\u52a1");
            }
        }
        List list = this.viewController.queryLinksByCurrentFormScheme(importContext.getFormSchemeKey());
        List taskLinkList = importContext.getParaInfo().getTaskLinkList();
        for (LinkTaskBean singleLink : taskLinkList) {
            DesignFormSchemeDefine theFormScheme;
            DesignTaskLinkDefine tasklink;
            boolean isNew;
            CompareDataTaskLinkDTO oldData = null;
            if (oldTaskLinkDic.containsKey(singleLink.getLinkNumber())) {
                oldData = (CompareDataTaskLinkDTO)oldTaskLinkDic.get(singleLink.getLinkNumber());
                if (linksLog != null) {
                    ParaImportInfoResult linkLog = new ParaImportInfoResult();
                    linkLog.copyForm(oldData);
                    linkLog.setSuccess(true);
                    linksLog.addItem(linkLog);
                }
                if (oldData.getUpdateType() == CompareUpdateType.UPDATE_UNOVER || oldData.getUpdateType() == CompareUpdateType.UPDATE_IGNORE) continue;
            }
            boolean bl = isNew = (tasklink = this.viewController.queryLinkByCurrentFormSchemeAndNum(importContext.getFormSchemeKey(), singleLink.getLinkNumber())) == null;
            if (isNew) {
                tasklink = this.viewController.createTaskLinkDefine();
            }
            tasklink.setCurrentFormSchemeKey(importContext.getTaskKey());
            tasklink.setDescription("\u5173\u8054\u4efb\u52a1\uff1a" + singleLink.getLinkTaskFlag());
            tasklink.setLinkAlias(singleLink.getLinkNumber());
            tasklink.setCurrentFormSchemeKey(importContext.getFormSchemeKey());
            tasklink.setCurrentTaskFormula(singleLink.getCurFomula());
            tasklink.setRelatedTaskFormula(singleLink.getLinkFomula());
            tasklink.setRelatedTaskCode(singleLink.getLinkTaskFlag());
            TaskLinkOrgMappingRule rule = null;
            if (tasklink.getOrgMappingRules() != null && !tasklink.getOrgMappingRules().isEmpty()) {
                rule = (TaskLinkOrgMappingRule)tasklink.getOrgMappingRules().get(0);
            } else {
                rule = new TaskLinkOrgMappingRule();
                rule.setTaskLinkKey(tasklink.getKey());
                rule.setMatchingType(TaskLinkMatchingType.MATCHING_TYPE_PRIMARYKEY);
                ArrayList<TaskLinkOrgMappingRule> ruleList = new ArrayList<TaskLinkOrgMappingRule>();
                ruleList.add(rule);
                tasklink.setOrgMappingRule(ruleList);
            }
            if (importContext.getTaskDefine() != null) {
                rule.setTargetEntity(importContext.getTaskDefine().getDw());
            }
            if (StringUtils.isNotEmpty((String)singleLink.getCurFomula()) && StringUtils.isNotEmpty((String)singleLink.getLinkFomula())) {
                tasklink.setMatchingType(TaskLinkMatchingType.FORM_TYPE_EXPRESSION);
                tasklink.setExpressionType(TaskLinkExpressionType.EQUALS);
                rule.setMatchingType(TaskLinkMatchingType.FORM_TYPE_EXPRESSION);
                rule.setExpressionType(TaskLinkExpressionType.EQUALS);
                rule.setSourceFormula(singleLink.getLinkFomula());
                rule.setTargetFormula(singleLink.getCurFomula());
            }
            if ((theFormScheme = this.viewController.queryFormSchemeDefineByTaskPrefix(singleLink.getLinkTaskFlag())) != null) {
                tasklink.setRelatedTaskKey(theFormScheme.getTaskKey());
                tasklink.setRelatedFormSchemeKey(theFormScheme.getKey());
                tasklink.setTitle(theFormScheme.getTitle());
                DesignTaskDefine task = this.viewController.queryTaskDefine(theFormScheme.getTaskKey());
                if (task != null) {
                    tasklink.setDescription(task.getTitle());
                    rule.setSourceEntity(task.getDw());
                }
            }
            if (isNew) {
                this.viewController.insertTaskLinkDefine(tasklink);
                continue;
            }
            this.viewController.updateTaskLinkDefine(tasklink);
        }
        list = this.viewController.queryLinksByRelatedTaskCode(importContext.getParaInfo().getPrefix());
        if (list != null) {
            DesignFormSchemeDefine thisFormScheme = this.viewController.queryFormSchemeDefine(importContext.getFormSchemeKey());
            DesignTaskDefine thisTask = null;
            if (thisFormScheme != null) {
                thisTask = this.viewController.queryTaskDefine(thisFormScheme.getTaskKey());
            }
            for (DesignTaskLinkDefine taskLink : list) {
                if (taskLink.getRelatedTaskKey() != null) continue;
                TaskLinkOrgMappingRule rule = null;
                if (taskLink.getOrgMappingRules() != null && !taskLink.getOrgMappingRules().isEmpty()) {
                    rule = (TaskLinkOrgMappingRule)taskLink.getOrgMappingRules().get(0);
                } else {
                    rule = new TaskLinkOrgMappingRule();
                    rule.setTaskLinkKey(taskLink.getKey());
                    rule.setMatchingType(TaskLinkMatchingType.MATCHING_TYPE_PRIMARYKEY);
                    ArrayList<TaskLinkOrgMappingRule> ruleList = new ArrayList<TaskLinkOrgMappingRule>();
                    ruleList.add(rule);
                    taskLink.setOrgMappingRule(ruleList);
                }
                if (thisTask != null) {
                    rule.setSourceEntity(thisTask.getDw());
                }
                taskLink.setRelatedTaskKey(importContext.getTaskKey());
                taskLink.setRelatedFormSchemeKey(importContext.getFormSchemeKey());
                if (thisFormScheme != null) {
                    taskLink.setTitle(thisFormScheme.getTitle());
                    if (thisTask != null) {
                        taskLink.setDescription(thisTask.getTitle());
                    }
                }
                this.viewController.updateTaskLinkDefine(taskLink);
            }
        }
    }

    private String GetTaskFileFlag2(TaskImportContext importContext) {
        String aCode = "";
        ParaInfo para = importContext.getParaInfo();
        if (importContext.getDataScheme() != null) {
            aCode = importContext.getDataScheme().getPrefix();
        }
        if (StringUtils.isEmpty((String)aCode)) {
            if (importContext.getImportOption() != null && StringUtils.isNotEmpty((String)importContext.getImportOption().getFilePrefix()) && importContext.getImportOption().getFilePrefix().length() == 4) {
                aCode = importContext.getImportOption().getFilePrefix().toUpperCase();
            }
            if (StringUtils.isEmpty((String)aCode)) {
                aCode = StringUtils.isNotEmpty((String)para.getFileFlag()) ? para.getFileFlag().substring(0, 1) + OrderGenerator.newOrder().substring(5, 8) : "N" + OrderGenerator.newOrder().substring(5, 8);
                aCode = aCode.substring(0, 4);
                DesignTaskDefine task = this.viewController.queryTaskDefineByFilePrefix(aCode);
                DesignFormSchemeDefine scheme = this.viewController.queryFormSchemeDefineByFilePrefix(aCode);
                while (null != task || null != scheme) {
                    aCode = StringUtils.isNotEmpty((String)para.getFileFlag()) ? para.getFileFlag().substring(0, 1) + OrderGenerator.newOrder().substring(5, 8) : "N" + OrderGenerator.newOrder().substring(4, 8);
                    task = this.viewController.queryTaskDefineByFilePrefix(aCode);
                    scheme = this.viewController.queryFormSchemeDefineByFilePrefix(aCode);
                }
            }
        }
        para.setNetFileFlag(aCode);
        return aCode;
    }

    private String getNetTaskCode(String taskCode, ParaInfo para) {
        String aCode = taskCode;
        if (StringUtil.isEmpty((String)aCode)) {
            aCode = para.getPrefix();
        }
        DesignTaskDefine task = this.viewController.queryTaskDefineByCode(aCode);
        int num = 1;
        while (null != task) {
            aCode = para.getPrefix() + String.valueOf(num);
            task = this.viewController.queryTaskDefineByCode(aCode);
            ++num;
        }
        para.setNetTaskCode(aCode);
        return aCode;
    }

    private String getNetTaskTitle(String taskTitle, ParaInfo para) {
        String aCode = taskTitle;
        if (StringUtil.isEmpty((String)aCode)) {
            aCode = para.getTaskName();
        }
        DesignTaskDefine task = this.viewController.queryTaskDefineByTaskTitle(aCode);
        int num = 1;
        while (null != task) {
            aCode = para.getTaskName() + String.valueOf(num);
            task = this.viewController.queryTaskDefineByTaskTitle(aCode);
            ++num;
        }
        para.setNetTaskTitle(aCode);
        return aCode;
    }

    @Override
    public String getDataSchemKeyByTask(TaskImportContext importContext, String taskKey) {
        DesignFormSchemeDefine fromScheme;
        String dataSchemeKey = importContext.getDataSchemeKey();
        if (StringUtils.isNotEmpty((String)importContext.getTaskKey())) {
            DesignTaskDefine task = importContext.getTaskDefine();
            if (task == null) {
                task = this.viewController.queryTaskDefine(importContext.getTaskKey());
                importContext.setTaskDefine(task);
            }
            dataSchemeKey = task.getDataScheme();
        } else if (StringUtils.isNotEmpty((String)importContext.getFormSchemeKey()) && (fromScheme = this.viewController.queryFormSchemeDefine(importContext.getFormSchemeKey())) != null) {
            DesignTaskDefine task = this.viewController.queryTaskDefine(fromScheme.getTaskKey());
            importContext.setTaskDefine(task);
            dataSchemeKey = task.getDataScheme();
        }
        if (StringUtils.isNotEmpty((String)dataSchemeKey)) {
            DesignDataScheme dataScheme = this.dataSchemeSevice.getDataScheme(dataSchemeKey);
            importContext.setDataScheme(dataScheme);
            importContext.setDataSchemeKey(dataSchemeKey);
        }
        return dataSchemeKey;
    }

    @Override
    public void updateTaskEntitys(TaskImportContext importContext) throws Exception {
        this.updateFMDMEntity(importContext);
    }
}

