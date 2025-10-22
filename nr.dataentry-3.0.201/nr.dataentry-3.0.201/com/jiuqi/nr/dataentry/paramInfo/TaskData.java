/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.nr.context.cxt.DsContext
 *  com.jiuqi.nr.context.cxt.DsContextHolder
 *  com.jiuqi.nr.context.cxt.impl.DsContextImpl
 *  com.jiuqi.nr.definition.api.IRunTimeViewController
 *  com.jiuqi.nr.definition.common.FormulaSyntaxStyle
 *  com.jiuqi.nr.definition.common.TaskGatherType
 *  com.jiuqi.nr.definition.controller.ITaskOptionController
 *  com.jiuqi.nr.definition.facade.DesignTaskGroupDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.facade.TaskFlowsDefine
 *  com.jiuqi.nr.definition.facade.TaskOrgLinkDefine
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nr.definition.internal.impl.FillDateType
 *  com.jiuqi.nr.definition.internal.stream.param.TaskOrgLinkListStream
 *  com.jiuqi.nr.definition.planpublish.service.DesignTimePlanPublishService
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.jtable.params.base.EntityViewData
 *  com.jiuqi.nr.jtable.service.IJtableParamService
 *  com.jiuqi.nr.period.util.JacksonUtils
 *  org.apache.commons.lang3.StringUtils
 */
package com.jiuqi.nr.dataentry.paramInfo;

import com.fasterxml.jackson.core.type.TypeReference;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.nr.context.cxt.DsContext;
import com.jiuqi.nr.context.cxt.DsContextHolder;
import com.jiuqi.nr.context.cxt.impl.DsContextImpl;
import com.jiuqi.nr.dataentry.paramInfo.FlowButtonConfig;
import com.jiuqi.nr.dataentry.paramInfo.TaskOrgData;
import com.jiuqi.nr.definition.api.IRunTimeViewController;
import com.jiuqi.nr.definition.common.FormulaSyntaxStyle;
import com.jiuqi.nr.definition.common.TaskGatherType;
import com.jiuqi.nr.definition.controller.ITaskOptionController;
import com.jiuqi.nr.definition.facade.DesignTaskGroupDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.facade.TaskFlowsDefine;
import com.jiuqi.nr.definition.facade.TaskOrgLinkDefine;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.definition.internal.impl.FillDateType;
import com.jiuqi.nr.definition.internal.stream.param.TaskOrgLinkListStream;
import com.jiuqi.nr.definition.planpublish.service.DesignTimePlanPublishService;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.jtable.params.base.EntityViewData;
import com.jiuqi.nr.jtable.service.IJtableParamService;
import com.jiuqi.nr.period.util.JacksonUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class TaskData {
    private String key;
    private String code;
    private String title;
    private String groupName;
    private List<EntityViewData> entitys = new ArrayList<EntityViewData>();
    private Map<String, String> entityTitleMap = new HashMap<String, String>();
    private String measureUnit;
    private int periodType;
    private String fromPeriod;
    private String toPeriod;
    private int periodOffset;
    private FormulaSyntaxStyle formulaStyle;
    private boolean protect;
    private FlowButtonConfig flowButtonConfig;
    private boolean efdcSwitch;
    private List<DesignTaskGroupDefine> designTaskGroupDefines;
    private String taskGroupKeys;
    private boolean submitExplain;
    private boolean forceSubmitExplain;
    private boolean backDescriptionNeedWrite;
    private FillDateType fillingDateType = FillDateType.NONE;
    private int fillingDateDays = 0;
    private List<TaskOrgData> entityScopeList;
    private List<String> showAddErrorDes;
    private TaskGatherType taskGatherType;

    public TaskGatherType getTaskGatherType() {
        return this.taskGatherType;
    }

    public void setTaskGatherType(TaskGatherType taskGatherType) {
        this.taskGatherType = taskGatherType;
    }

    public List<String> getShowAddErrorDes() {
        return this.showAddErrorDes;
    }

    public void setShowAddErrorDes(List<String> showAddErrorDes) {
        this.showAddErrorDes = showAddErrorDes;
    }

    public List<TaskOrgData> getEntityScopeList() {
        return this.entityScopeList;
    }

    public void setEntityScopeList(List<TaskOrgData> entityScopeList) {
        this.entityScopeList = entityScopeList;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGroupName() {
        return this.groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public List<EntityViewData> getEntitys() {
        return this.entitys;
    }

    public void setEntitys(List<EntityViewData> entitys) {
        this.entitys = entitys;
    }

    public Map<String, String> getEntityTitleMap() {
        return this.entityTitleMap;
    }

    public void setEntityTitleMap(Map<String, String> entityTitleMap) {
        this.entityTitleMap = entityTitleMap;
    }

    public String getMeasureUnit() {
        return this.measureUnit;
    }

    public void setMeasureUnit(String measureUnit) {
        this.measureUnit = measureUnit;
    }

    public int getPeriodType() {
        return this.periodType;
    }

    public void setPeriodType(int periodType) {
        this.periodType = periodType;
    }

    public String getFromPeriod() {
        return this.fromPeriod;
    }

    public void setFromPeriod(String fromPeriod) {
        this.fromPeriod = fromPeriod;
    }

    public String getToPeriod() {
        return this.toPeriod;
    }

    public void setToPeriod(String toPeriod) {
        this.toPeriod = toPeriod;
    }

    public int getPeriodOffset() {
        return this.periodOffset;
    }

    public void setPeriodOffset(int periodOffset) {
        this.periodOffset = periodOffset;
    }

    public boolean isProtect() {
        return this.protect;
    }

    public void setProtect(boolean protect) {
        this.protect = protect;
    }

    public FlowButtonConfig getFlowButtonConfig() {
        return this.flowButtonConfig;
    }

    public void setFlowButtonConfig(FlowButtonConfig flowButtonConfig) {
        this.flowButtonConfig = flowButtonConfig;
    }

    public void init(TaskDefine taskDefine) {
        PeriodWrapper fromPeriodWrapper;
        IJtableParamService jtableParamService = (IJtableParamService)BeanUtil.getBean(IJtableParamService.class);
        DesignTimePlanPublishService designTimePlanPublishService = (DesignTimePlanPublishService)BeanUtil.getBean(DesignTimePlanPublishService.class);
        this.key = taskDefine.getKey();
        this.code = taskDefine.getTaskCode();
        this.title = taskDefine.getTitle();
        this.groupName = taskDefine.getGroupName();
        this.measureUnit = taskDefine.getMeasureUnit();
        this.periodType = taskDefine.getPeriodType().type();
        if (StringUtils.isEmpty((String)taskDefine.getFromPeriod())) {
            fromPeriodWrapper = new PeriodWrapper(1970, taskDefine.getPeriodType().type(), 1);
            this.fromPeriod = fromPeriodWrapper.toString();
        } else {
            this.fromPeriod = taskDefine.getFromPeriod();
        }
        if (StringUtils.isEmpty((String)taskDefine.getToPeriod())) {
            fromPeriodWrapper = new PeriodWrapper(9999, taskDefine.getPeriodType().type(), 1);
            this.toPeriod = fromPeriodWrapper.toString();
        } else {
            this.toPeriod = taskDefine.getToPeriod();
        }
        this.periodOffset = taskDefine.getTaskPeriodOffset();
        this.formulaStyle = taskDefine.getFormulaSyntaxStyle();
        IRunTimeViewController iRunTimeViewController = (IRunTimeViewController)BeanUtil.getBean(IRunTimeViewController.class);
        TaskOrgLinkListStream taskOrgLinkListStream = iRunTimeViewController.listTaskOrgLinkStreamByTask(this.key);
        ArrayList<TaskOrgData> taskOrgDataList = new ArrayList<TaskOrgData>();
        IEntityMetaService iEntityMetaService = (IEntityMetaService)BeanUtil.getBean(IEntityMetaService.class);
        for (TaskOrgLinkDefine taskOrgLinkDefine : taskOrgLinkListStream.auth().i18n().getList()) {
            TaskOrgData taskOrgData = new TaskOrgData();
            taskOrgData.setId(taskOrgLinkDefine.getEntity());
            if (StringUtils.isNotEmpty((String)taskOrgLinkDefine.getEntityAlias())) {
                taskOrgData.setTitle(taskOrgLinkDefine.getEntityAlias());
            } else {
                taskOrgData.setTitle(iEntityMetaService.queryEntity(taskOrgLinkDefine.getEntity()).getTitle());
            }
            taskOrgDataList.add(taskOrgData);
        }
        this.entityScopeList = taskOrgDataList;
        try {
            EntityViewData dwEntity;
            this.protect = designTimePlanPublishService.taskIsProtectIng(this.key);
            this.entitys = new ArrayList<EntityViewData>();
            if (this.entityScopeList.size() > 0) {
                dwEntity = jtableParamService.getEntity(this.entityScopeList.get(0).getId());
                DsContext context = DsContextHolder.getDsContext();
                if (context.getContextEntityId() == null) {
                    DsContextImpl dsContext = (DsContextImpl)context;
                    dsContext.setEntityId(this.entityScopeList.get(0).getId());
                }
            } else {
                dwEntity = jtableParamService.getEntity(taskDefine.getDw());
            }
            dwEntity.setMasterEntity(true);
            this.entityTitleMap.put(dwEntity.getKey(), dwEntity.getTitle());
            this.entitys.add(dwEntity);
            EntityViewData dataTimeEntity = jtableParamService.getEntity(taskDefine.getDateTime());
            this.entitys.add(dataTimeEntity);
            this.entityTitleMap.put(dataTimeEntity.getKey(), dataTimeEntity.getTitle());
            if (StringUtils.isNotEmpty((String)taskDefine.getDims())) {
                String[] dimEntityIds;
                for (String dimEntityId : dimEntityIds = taskDefine.getDims().split(";")) {
                    EntityViewData dimEntity = jtableParamService.getEntity(dimEntityId);
                    this.entitys.add(dimEntity);
                    this.entityTitleMap.put(dimEntity.getKey(), dimEntity.getTitle());
                }
            }
            this.efdcSwitch = taskDefine.getEfdcSwitch();
        }
        catch (Exception dwEntity) {
            // empty catch block
        }
        TaskFlowsDefine flowsSetting = taskDefine.getFlowsSetting();
        if (flowsSetting != null) {
            this.submitExplain = flowsSetting.isSubmitExplain();
            this.backDescriptionNeedWrite = flowsSetting.isBackDescriptionNeedWrite();
            this.forceSubmitExplain = flowsSetting.isForceSubmitExplain();
        }
        this.fillingDateType = taskDefine.getFillingDateType();
        this.fillingDateDays = taskDefine.getFillingDateDays();
        ITaskOptionController taskOptionController = (ITaskOptionController)BeanUtil.getBean(ITaskOptionController.class);
        String value = taskOptionController.getValue(this.key, "ALLOW_ADD_ERROR_DES");
        if (Objects.isNull(value) || org.apache.commons.lang3.StringUtils.isBlank((CharSequence)value)) {
            this.showAddErrorDes = new ArrayList<String>();
        } else {
            TypeReference<List<String>> typeReference = new TypeReference<List<String>>(){};
            this.showAddErrorDes = (List)JacksonUtils.jsonToObject((String)value, (TypeReference)typeReference);
        }
        this.taskGatherType = taskDefine.getTaskGatherType();
    }

    public void initWithoutFlowSetting(TaskDefine taskDefine) {
        PeriodWrapper fromPeriodWrapper;
        IJtableParamService jtableParamService = (IJtableParamService)BeanUtil.getBean(IJtableParamService.class);
        DesignTimePlanPublishService designTimePlanPublishService = (DesignTimePlanPublishService)BeanUtil.getBean(DesignTimePlanPublishService.class);
        this.key = taskDefine.getKey();
        this.code = taskDefine.getTaskCode();
        this.title = taskDefine.getTitle();
        this.groupName = taskDefine.getGroupName();
        this.measureUnit = taskDefine.getMeasureUnit();
        this.periodType = taskDefine.getPeriodType().type();
        if (StringUtils.isEmpty((String)taskDefine.getFromPeriod())) {
            fromPeriodWrapper = new PeriodWrapper(1970, taskDefine.getPeriodType().type(), 1);
            this.fromPeriod = fromPeriodWrapper.toString();
        } else {
            this.fromPeriod = taskDefine.getFromPeriod();
        }
        if (StringUtils.isEmpty((String)taskDefine.getToPeriod())) {
            fromPeriodWrapper = new PeriodWrapper(9999, taskDefine.getPeriodType().type(), 1);
            this.toPeriod = fromPeriodWrapper.toString();
        } else {
            this.toPeriod = taskDefine.getToPeriod();
        }
        this.periodOffset = taskDefine.getTaskPeriodOffset();
        this.formulaStyle = taskDefine.getFormulaSyntaxStyle();
        try {
            this.protect = designTimePlanPublishService.taskIsProtectIng(this.key);
            this.entitys = new ArrayList<EntityViewData>();
            EntityViewData dwEntity = jtableParamService.getEntity(taskDefine.getDw());
            dwEntity.setMasterEntity(true);
            this.entityTitleMap.put(dwEntity.getKey(), dwEntity.getTitle());
            this.entitys.add(dwEntity);
            EntityViewData dataTimeEntity = jtableParamService.getEntity(taskDefine.getDateTime());
            this.entitys.add(dataTimeEntity);
            this.entityTitleMap.put(dataTimeEntity.getKey(), dataTimeEntity.getTitle());
            if (StringUtils.isNotEmpty((String)taskDefine.getDims())) {
                String[] dimEntityIds;
                for (String dimEntityId : dimEntityIds = taskDefine.getDims().split(";")) {
                    EntityViewData dimEntity = jtableParamService.getEntity(dimEntityId);
                    this.entitys.add(dimEntity);
                    this.entityTitleMap.put(dimEntity.getKey(), dimEntity.getTitle());
                }
            }
            this.efdcSwitch = taskDefine.getEfdcSwitch();
        }
        catch (Exception dwEntity) {
            // empty catch block
        }
        this.fillingDateType = taskDefine.getFillingDateType();
        this.fillingDateDays = taskDefine.getFillingDateDays();
        IRunTimeViewController iRunTimeViewController = (IRunTimeViewController)BeanUtil.getBean(IRunTimeViewController.class);
        TaskOrgLinkListStream taskOrgLinkListStream = iRunTimeViewController.listTaskOrgLinkStreamByTask(this.key);
        ArrayList<TaskOrgData> taskOrgDataList = new ArrayList<TaskOrgData>();
        IEntityMetaService iEntityMetaService = (IEntityMetaService)BeanUtil.getBean(IEntityMetaService.class);
        for (TaskOrgLinkDefine taskOrgLinkDefine : taskOrgLinkListStream.auth().i18n().getList()) {
            TaskOrgData taskOrgData = new TaskOrgData();
            taskOrgData.setId(taskOrgLinkDefine.getEntity());
            if (StringUtils.isNotEmpty((String)taskOrgLinkDefine.getEntityAlias())) {
                taskOrgData.setTitle(taskOrgLinkDefine.getEntityAlias());
            } else {
                taskOrgData.setTitle(iEntityMetaService.queryEntity(taskOrgLinkDefine.getEntity()).getTitle());
            }
            taskOrgDataList.add(taskOrgData);
        }
        this.entityScopeList = taskOrgDataList;
        ITaskOptionController taskOptionController = (ITaskOptionController)BeanUtil.getBean(ITaskOptionController.class);
        String value = taskOptionController.getValue(this.key, "ALLOW_ADD_ERROR_DES");
        if (Objects.isNull(value) || org.apache.commons.lang3.StringUtils.isBlank((CharSequence)value)) {
            this.showAddErrorDes = new ArrayList<String>();
        } else {
            TypeReference<List<String>> typeReference = new TypeReference<List<String>>(){};
            this.showAddErrorDes = (List)JacksonUtils.jsonToObject((String)value, (TypeReference)typeReference);
        }
    }

    public FormulaSyntaxStyle getFormulaStyle() {
        return this.formulaStyle;
    }

    public void setFormulaStyle(FormulaSyntaxStyle formulaStyle) {
        this.formulaStyle = formulaStyle;
    }

    public boolean isEfdcSwitch() {
        return this.efdcSwitch;
    }

    public void setEfdcSwitch(boolean efdcSwitch) {
        this.efdcSwitch = efdcSwitch;
    }

    public List<DesignTaskGroupDefine> getDesignTaskGroupDefines() {
        return this.designTaskGroupDefines;
    }

    public void setDesignTaskGroupDefines(List<DesignTaskGroupDefine> designTaskGroupDefines) {
        this.designTaskGroupDefines = designTaskGroupDefines;
    }

    public String getTaskGroupKeys() {
        return this.taskGroupKeys;
    }

    public void setTaskGroupKeys(String taskGroupKeys) {
        this.taskGroupKeys = taskGroupKeys;
    }

    public boolean isSubmitExplain() {
        return this.submitExplain;
    }

    public void setSubmitExplain(boolean submitExplain) {
        this.submitExplain = submitExplain;
    }

    public boolean isBackDescriptionNeedWrite() {
        return this.backDescriptionNeedWrite;
    }

    public void setBackDescriptionNeedWrite(boolean backDescriptionNeedWrite) {
        this.backDescriptionNeedWrite = backDescriptionNeedWrite;
    }

    public FillDateType getFillingDateType() {
        return this.fillingDateType;
    }

    public void setFillingDateType(FillDateType fillingDateType) {
        this.fillingDateType = fillingDateType;
    }

    public int getFillingDateDays() {
        return this.fillingDateDays;
    }

    public void setFillingDateDays(int fillingDateDays) {
        this.fillingDateDays = fillingDateDays;
    }

    public boolean isForceSubmitExplain() {
        return this.forceSubmitExplain;
    }

    public void setForceSubmitExplain(boolean forceSubmitExplain) {
        this.forceSubmitExplain = forceSubmitExplain;
    }
}

