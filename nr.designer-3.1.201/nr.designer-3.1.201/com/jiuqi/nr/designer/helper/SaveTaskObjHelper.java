/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.fasterxml.jackson.databind.JsonNode
 *  com.jiuqi.bi.util.OrderGenerator
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.core.context.ContextUser
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.definition.common.UUIDUtils
 *  com.jiuqi.np.definition.exception.DBParaException
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.nr.definition.common.TaskGatherType
 *  com.jiuqi.nr.definition.common.TaskType
 *  com.jiuqi.nr.definition.controller.IDesignTimeViewController
 *  com.jiuqi.nr.definition.exception.NrDefinitionErrorEnum
 *  com.jiuqi.nr.definition.facade.DesignDimensionFilter
 *  com.jiuqi.nr.definition.facade.DesignEntityLinkageDefine
 *  com.jiuqi.nr.definition.facade.DesignSchemePeriodLinkDefine
 *  com.jiuqi.nr.definition.facade.DesignTaskDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.facade.TaskOrgLinkDefine
 *  com.jiuqi.nr.definition.internal.controller.NRDesignTimeController
 *  com.jiuqi.nr.definition.internal.impl.DesignTaskDefineImpl
 *  com.jiuqi.nr.definition.internal.impl.DesignTaskFlowsDefine
 *  com.jiuqi.nr.definition.internal.impl.FillDateType
 *  com.jiuqi.nr.definition.internal.impl.TaskOrgLinkDefineImpl
 *  com.jiuqi.nr.definition.internal.service.TaskOrgLinkService
 *  com.jiuqi.nr.definition.util.ServeCodeService
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.internal.adapter.IPeriodProvider
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.nr.period.modal.IPeriodEntity
 *  com.jiuqi.nr.period.util.JacksonUtils
 */
package com.jiuqi.nr.designer.helper;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.jiuqi.bi.util.OrderGenerator;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.core.context.ContextUser;
import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.definition.common.UUIDUtils;
import com.jiuqi.np.definition.exception.DBParaException;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.nr.definition.common.TaskGatherType;
import com.jiuqi.nr.definition.common.TaskType;
import com.jiuqi.nr.definition.controller.IDesignTimeViewController;
import com.jiuqi.nr.definition.exception.NrDefinitionErrorEnum;
import com.jiuqi.nr.definition.facade.DesignDimensionFilter;
import com.jiuqi.nr.definition.facade.DesignEntityLinkageDefine;
import com.jiuqi.nr.definition.facade.DesignSchemePeriodLinkDefine;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.facade.TaskOrgLinkDefine;
import com.jiuqi.nr.definition.internal.controller.NRDesignTimeController;
import com.jiuqi.nr.definition.internal.impl.DesignTaskDefineImpl;
import com.jiuqi.nr.definition.internal.impl.DesignTaskFlowsDefine;
import com.jiuqi.nr.definition.internal.impl.FillDateType;
import com.jiuqi.nr.definition.internal.impl.TaskOrgLinkDefineImpl;
import com.jiuqi.nr.definition.internal.service.TaskOrgLinkService;
import com.jiuqi.nr.definition.util.ServeCodeService;
import com.jiuqi.nr.designer.common.EntityChangeInfo;
import com.jiuqi.nr.designer.helper.CommonHelper;
import com.jiuqi.nr.designer.helper.SaveSchemePeriodHelper;
import com.jiuqi.nr.designer.service.StepSaveService;
import com.jiuqi.nr.designer.web.facade.EntityLinkageObject;
import com.jiuqi.nr.designer.web.facade.EntityTables;
import com.jiuqi.nr.designer.web.facade.TaskObj;
import com.jiuqi.nr.designer.web.facade.TaskOrgVO;
import com.jiuqi.nr.designer.web.treebean.TaskObject;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.period.internal.adapter.IPeriodProvider;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nr.period.modal.IPeriodEntity;
import com.jiuqi.nr.period.util.JacksonUtils;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class SaveTaskObjHelper {
    @Autowired
    private NRDesignTimeController nrDesignTimeController;
    @Autowired
    private CommonHelper commonHelper;
    @Autowired
    private StepSaveService stepSaveService;
    @Autowired
    private ServeCodeService serveCodeService;
    @Autowired
    private SaveSchemePeriodHelper saveSchemePeriodHelper;
    @Autowired
    private PeriodEngineService periodEngineService;
    @Autowired
    private IEntityMetaService metaService;
    @Autowired
    private IDesignTimeViewController designTimeViewController;
    @Autowired
    private TaskOrgLinkService taskOrgLinkService;

    public TaskDefine saveTaskObject(TaskObject taskObject) throws JQException {
        DesignTaskDefine taskDefine = this.nrDesignTimeController.queryTaskDefine(taskObject.getID());
        if (taskObject.isIsNew()) {
            if (taskDefine != null) {
                throw new JQException((ErrorEnum)NrDefinitionErrorEnum.NRDEFINITION_EXCEPTION_013, "\u4efb\u52a1Key\u3010" + taskObject.getID() + "\u3011\u91cd\u590d\uff0c\u65e0\u6cd5\u65b0\u589e\uff01");
            }
            taskDefine = this.nrDesignTimeController.createTaskDefine();
            if (StringUtils.isEmpty((String)taskObject.getFlowsObject().getSubmitEntityTables())) {
                IEntityDefine entityDefine = this.metaService.queryEntity(taskObject.getDw());
                IPeriodEntity periodEntity = this.periodEngineService.getPeriodAdapter().getPeriodEntity(taskObject.getDatetime());
                taskObject.getFlowsObject().setSubmitEntityTables(entityDefine.getId() + ";" + periodEntity.getKey());
            }
            taskObject.setOwnerLevelAndId(this.serveCodeService.getServeCode());
            this.checkSchemePeriodLink(taskObject, taskDefine, "init");
            this.initTaskDefine(taskObject, taskDefine, null);
            this.setCreateUserAndTime(taskDefine);
            this.nrDesignTimeController.insertTaskDefine(taskDefine);
            this.updateTaskOrgLink(taskObject);
        } else if (taskObject.isIsDirty()) {
            HashMap<String, EntityChangeInfo> entityChangeMap = new HashMap<String, EntityChangeInfo>();
            this.checkSchemePeriodLink(taskObject, taskDefine, "update");
            this.initTaskDefine(taskObject, taskDefine, entityChangeMap);
            taskDefine.setUpdateTime(new Date());
            this.nrDesignTimeController.updateTaskDefine(taskDefine);
            this.saveDimensionFilter(taskObject.getID(), taskObject.getDimensionFilter());
            this.updateTaskOrgLink(taskObject);
        }
        return taskDefine;
    }

    private void updateTaskOrgLink(TaskObject taskObject) {
        try {
            if (taskObject.getOrgListObj() == null || !taskObject.getOrgListObj().isNeedUpdate()) {
                return;
            }
            this.taskOrgLinkService.deleteTaskOrgLinkByTask(taskObject.getID());
            ArrayList<TaskOrgLinkDefineImpl> orgs = new ArrayList<TaskOrgLinkDefineImpl>();
            for (TaskOrgVO org : taskObject.getOrgListObj().getOrgList()) {
                TaskOrgLinkDefineImpl orgLinkDefine = new TaskOrgLinkDefineImpl();
                orgLinkDefine.setKey(StringUtils.isNotEmpty((String)org.getKey()) ? org.getKey() : UUIDUtils.getKey());
                orgLinkDefine.setTask(org.getTaskKey());
                orgLinkDefine.setEntity(org.getEntityId());
                orgLinkDefine.setEntityAlias(org.getEntityAlias());
                orgLinkDefine.setOrder(StringUtils.isNotEmpty((String)org.getOrder()) ? org.getOrder() : OrderGenerator.newOrder());
                orgs.add(orgLinkDefine);
            }
            if (!CollectionUtils.isEmpty(orgs)) {
                this.taskOrgLinkService.insertTaskOrgLink(orgs.toArray(new TaskOrgLinkDefine[orgs.size()]));
            }
        }
        catch (DBParaException e) {
            throw new RuntimeException(e);
        }
    }

    private void saveDimensionFilter(String taskId, String content) {
        JsonNode jsonNode = (JsonNode)JacksonUtils.jsonToObject((String)content, JsonNode.class);
        Map entityValues = (Map)JacksonUtils.mapper.convertValue((Object)jsonNode, (TypeReference)new TypeReference<Map<String, List<String>>>(){});
        if (entityValues == null) {
            return;
        }
        ArrayList<DesignDimensionFilter> list = new ArrayList<DesignDimensionFilter>(8);
        for (Map.Entry stringListEntry : entityValues.entrySet()) {
            DesignDimensionFilter designDimensionFilter = this.designTimeViewController.createDesignDimensionFilter();
            designDimensionFilter.setTaskKey(taskId);
            designDimensionFilter.setEntityId((String)stringListEntry.getKey());
            designDimensionFilter.setList((List)stringListEntry.getValue());
            list.add(designDimensionFilter);
        }
        this.designTimeViewController.saveDimensionFiltersByTaskKey(taskId, list);
    }

    private void checkSchemePeriodLink(TaskObject taskObject, DesignTaskDefine taskDefine, String type) throws JQException {
        if (StringUtils.isNotEmpty((String)taskObject.getFromPeriod()) || StringUtils.isNotEmpty((String)taskObject.getToPeriod())) {
            if (StringUtils.isNotEmpty((String)taskObject.getFromPeriod()) && StringUtils.isNotEmpty((String)taskObject.getToPeriod())) {
                if (!taskObject.getFromPeriod().equals(taskDefine.getFromPeriod()) || !taskObject.getToPeriod().equals(taskDefine.getToPeriod())) {
                    DesignTaskDefineImpl newtask = new DesignTaskDefineImpl();
                    this.initTaskDefine(taskObject, (DesignTaskDefine)newtask, null);
                    this.saveSchemePeriodHelper.reMarkSchemePeriodLink((DesignTaskDefine)newtask, type);
                }
            } else {
                DesignTaskDefineImpl newtask = new DesignTaskDefineImpl();
                this.initTaskDefine(taskObject, (DesignTaskDefine)newtask, null);
                String[] periodCodeRegion = this.periodEngineService.getPeriodAdapter().getPeriodProvider(taskObject.getDatetime()).getPeriodCodeRegion();
                if (StringUtils.isEmpty((String)newtask.getFromPeriod()) && StringUtils.isNotEmpty((String)periodCodeRegion[0])) {
                    newtask.setFromPeriod(periodCodeRegion[0]);
                }
                if (StringUtils.isEmpty((String)newtask.getToPeriod()) && StringUtils.isNotEmpty((String)periodCodeRegion[1])) {
                    newtask.setToPeriod(periodCodeRegion[1]);
                }
                this.saveSchemePeriodHelper.reMarkSchemePeriodLink((DesignTaskDefine)newtask, type);
            }
        }
    }

    private boolean checkRemarkSchemePeriod(TaskObject taskObject, DesignTaskDefine taskDefine) {
        boolean isRemark = false;
        try {
            IPeriodProvider periodProvider = this.periodEngineService.getPeriodAdapter().getPeriodProvider(taskDefine.getDateTime());
            Date taskFormPeriod = null;
            Date taskToPeriod = null;
            taskFormPeriod = StringUtils.isEmpty((String)taskObject.getFromPeriod()) ? periodProvider.getPeriodDateRegion()[0] : periodProvider.getPeriodDateRegion(taskObject.getFromPeriod())[0];
            if (StringUtils.isEmpty((String)taskObject.getToPeriod())) {
                String periodCodeRegion = periodProvider.getPeriodCodeRegion()[1];
                if (null != periodCodeRegion) {
                    taskToPeriod = periodProvider.getPeriodDateRegion(periodCodeRegion)[1];
                }
            } else {
                taskToPeriod = periodProvider.getPeriodDateRegion(taskObject.getToPeriod())[1];
            }
            if (null == taskFormPeriod || null == taskToPeriod) {
                isRemark = true;
                return isRemark;
            }
            List periodLinkDefines = this.designTimeViewController.querySchemePeriodLinkByTask(taskDefine.getKey());
            if (null == periodLinkDefines || periodLinkDefines.size() == 0) {
                isRemark = true;
                return isRemark;
            }
            Date schemeFromPeriod = null;
            Date schemeToPeriod = null;
            for (DesignSchemePeriodLinkDefine periodLinkDefine : periodLinkDefines) {
                Date[] periodDateRegion = periodProvider.getPeriodDateRegion(periodLinkDefine.getPeriodKey());
                if (null == schemeFromPeriod) {
                    schemeFromPeriod = periodDateRegion[0];
                } else if (null != periodDateRegion[0] && periodDateRegion[0].before(schemeFromPeriod)) {
                    schemeFromPeriod = periodDateRegion[0];
                }
                if (null == schemeToPeriod) {
                    schemeToPeriod = periodDateRegion[1];
                    continue;
                }
                if (null == periodDateRegion[1] || !periodDateRegion[1].after(schemeToPeriod)) continue;
                schemeToPeriod = periodDateRegion[1];
            }
            if (null == schemeFromPeriod || null == schemeToPeriod) {
                isRemark = true;
                return isRemark;
            }
            if (taskFormPeriod.after(schemeFromPeriod) || taskToPeriod.before(schemeToPeriod)) {
                isRemark = true;
            }
        }
        catch (Exception e) {
            isRemark = true;
        }
        return isRemark;
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

    private void initTaskDefine(TaskObject taskObject, DesignTaskDefine taskDefine, Map<String, EntityChangeInfo> entityChangeMap) throws JQException {
        taskDefine.setKey(taskObject.getID());
        taskDefine.setTitle(taskObject.getTitle());
        taskDefine.setTaskCode(taskObject.getCode() == null ? OrderGenerator.newOrder() : taskObject.getCode());
        taskDefine.setPeriodType(PeriodType.fromType((int)taskObject.getPeriodType()));
        taskDefine.setTaskPeriodOffset(taskObject.getTaskPeriodOffset());
        taskDefine.setFromPeriod(taskObject.getFromPeriod());
        taskDefine.setTaskGatherType(TaskGatherType.forValue((int)taskObject.getGatherType()));
        taskDefine.setToPeriod(taskObject.getToPeriod());
        taskDefine.setDescription(taskObject.getDescription());
        taskDefine.setMeasureUnit(taskObject.getMeasureUnit());
        taskDefine.setDw(taskObject.getDw());
        taskDefine.setDims(taskObject.getDims());
        taskDefine.setDateTime(taskObject.getDatetime());
        taskDefine.setMasterEntitiesKey(taskObject.getTkEntityKey());
        taskDefine.setFormulaSyntaxStyle(taskObject.getFormulaSyntaxStyle());
        DesignTaskFlowsDefine newTaskFlows = this.commonHelper.initTaskFlowsDefine(taskObject.getFlowsObject());
        taskDefine.setFlowsSetting(newTaskFlows);
        taskDefine.setDueDateOffset(taskObject.getDueDateOffset());
        taskDefine.setFillInAutomaticallyDue(taskObject.getFillInAutomaticallyDue());
        taskDefine.setEntityViewsInEFDC(taskObject.getEntityViewInEfdc());
        taskDefine.setTaskType(TaskType.forValue((int)taskObject.getTaskType()));
        taskDefine.setOwnerLevelAndId(taskObject.getOwnerLevelAndId());
        taskDefine.setEfdcSwitch(taskObject.getEfdcSwitch());
        if (StringUtils.isEmpty((String)taskDefine.getDataScheme())) {
            taskDefine.setDataScheme(taskObject.getDataScheme());
        }
        if (taskObject.getFilterSettingType() != null && taskObject.getFilterSettingType() == 1) {
            taskDefine.setFilterExpression(taskObject.getFilterExpression());
            taskDefine.setFilterTemplate(null);
        } else {
            taskDefine.setFilterTemplate(taskObject.getFilterTemplate());
            taskDefine.setFilterExpression(null);
        }
        taskDefine.setFillingDateType(FillDateType.fromType((int)taskObject.getFillingDateType()));
        taskDefine.setFillingDateDays(taskObject.getFillingDateDays());
    }

    public void saveEntityLinkageObject(TaskObject taskObject) throws JQException {
        DesignEntityLinkageDefine entityLinkageDefine = this.nrDesignTimeController.createEntityLinkageDefine();
        Map<String, EntityTables> hashEntityTable = taskObject.getEntityTablesList();
        EntityTables entityObject = null;
        EntityLinkageObject entityLinkageObject = null;
        for (Map.Entry<String, EntityTables> entry : hashEntityTable.entrySet()) {
            entityObject = entry.getValue();
            entityLinkageObject = entityObject.getEntityLinkageObject();
            if (entityLinkageObject.isIsNew()) {
                entityLinkageDefine.setKey(entityObject.getID());
                entityLinkageDefine.setLinkageCondition(entityLinkageObject.getLinkageCondition());
                entityLinkageDefine.setSlaveEntityKey(entityLinkageObject.getSlaveEntityKey());
                entityLinkageDefine.setMasterEntityKey(entityLinkageObject.getMasterEntityKey());
                entityLinkageDefine.setLinkageCondition(entityLinkageObject.getLinkageCondition());
                entityLinkageDefine.setOwnerLevelAndId(this.serveCodeService.getServeCode());
                this.nrDesignTimeController.insertDesignerEntityeLinkageDefine(entityLinkageDefine);
            }
            if (!entityLinkageObject.isIsDirty()) continue;
            entityLinkageDefine.setKey(entityObject.getID());
            entityLinkageDefine.setLinkageCondition(entityLinkageObject.getLinkageCondition());
            entityLinkageDefine.setSlaveEntityKey(entityLinkageObject.getSlaveEntityKey());
            entityLinkageDefine.setMasterEntityKey(entityLinkageObject.getMasterEntityKey());
            entityLinkageDefine.setLinkageCondition(entityLinkageObject.getLinkageCondition());
            if (this.nrDesignTimeController.queryDesignEntityLinkageDefineByKey(entityObject.getID()) == null) {
                this.nrDesignTimeController.insertDesignerEntityeLinkageDefine(entityLinkageDefine);
                continue;
            }
            this.nrDesignTimeController.updateEntityLinkageDefine(entityLinkageDefine);
        }
    }

    public void conversionTaskObj(TaskObj taskObj) throws Exception {
        TaskObject taskObject = new TaskObject();
        taskObject.setDataScheme(taskObj.getDataScheme());
        taskObject.setDw(taskObj.getDw());
        taskObject.setDims(taskObj.getDims());
        taskObject.setDatetime(taskObj.getDatetime());
        taskObject.setID(taskObj.getID());
        taskObject.setTitle(taskObj.getTitle());
        taskObject.setSystemFields(taskObj.getSystemFields());
        taskObject.setIsDirty(false);
        taskObject.setIsNew(true);
        taskObject.setIsDeleted(false);
        taskObject.setEntityViewInEfdc(taskObj.getEntityViewInEfdc());
        taskObject.setEntityTablesList(this.changeEntityTables(taskObj.getEntityList()));
        taskObject.setFlowsObject(taskObj.getFlowsObj());
        taskObject.setTkEntityKey(taskObj.getTaskFileFix());
        taskObject.setMeasureUnit(taskObj.getMeasureUnit());
        taskObject.setUseFieldAuthority(taskObj.getUseFieldAuthority());
        taskObject.setTaskType(taskObj.getTaskType());
        taskObject.setOwnerLevelAndId(taskObj.getOwnerLevelAndId());
        taskObject.setSameServeCode(this.serveCodeService.isSameServeCode(taskObj.getOwnerLevelAndId()));
        Object[] entityKey = new String[taskObj.getEntityList().size()];
        for (int i = 0; i < taskObj.getEntityList().size(); ++i) {
            entityKey[i] = taskObj.getEntityList().get(i).getID();
        }
        taskObject.setTkEntityKey(StringUtils.join((Object[])entityKey, (String)";"));
        taskObject.setToPeriod(taskObj.getToPeriod());
        taskObject.setFromPeriod(taskObj.getFromPeriod());
        if (taskObj.getFilterSettingType() != null && taskObj.getFilterSettingType() == 2) {
            taskObject.setFilterTemplate(taskObj.getFilterTemplate());
        } else {
            taskObject.setFilterExpression(taskObj.getFilterExpression());
        }
        taskObject.setFillingDateType(taskObj.getFillingDateType());
        taskObject.setFillingDateDays(taskObj.getFillingDateDays());
        taskObject.setOrgListObj(taskObj.getOrgList());
        this.stepSaveService.saveTask(taskObject);
    }

    private HashMap<String, EntityTables> changeEntityTables(List<EntityTables> entityList) {
        HashMap<String, EntityTables> entitymap = new HashMap<String, EntityTables>();
        for (EntityTables entityTables : entityList) {
            entitymap.put(entityTables.getID(), entityTables);
        }
        return entitymap;
    }
}

