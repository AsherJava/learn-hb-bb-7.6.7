/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.nr.datascheme.api.DesignDataDimension
 *  com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService
 *  com.jiuqi.nr.definition.api.IDesignTimeViewController
 *  com.jiuqi.nr.definition.auth.DefinitionAuthorityProvider
 *  com.jiuqi.nr.definition.common.TaskLinkMatchingType
 *  com.jiuqi.nr.definition.facade.DesignFormSchemeDefine
 *  com.jiuqi.nr.definition.facade.DesignTaskDefine
 *  com.jiuqi.nr.definition.facade.DesignTaskLinkDefine
 *  com.jiuqi.nr.definition.facade.TaskLinkDefine
 *  com.jiuqi.nr.definition.facade.TaskLinkOrgMappingRule
 *  com.jiuqi.nr.definition.facade.TaskOrgLinkDefine
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.model.IEntityAttribute
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.model.IEntityModel
 *  com.jiuqi.nr.entity.model.IEntityRefer
 *  com.jiuqi.nr.entity.service.IEntityDataService
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.common.utils.PeriodUtils
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.nr.period.modal.IPeriodEntity
 *  com.jiuqi.nr.task.api.common.Constants$DataStatus
 *  com.jiuqi.util.OrderGenerator
 *  com.jiuqi.util.StringUtils
 */
package com.jiuqi.nr.formula.service.impl;

import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.nr.datascheme.api.DesignDataDimension;
import com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService;
import com.jiuqi.nr.definition.api.IDesignTimeViewController;
import com.jiuqi.nr.definition.auth.DefinitionAuthorityProvider;
import com.jiuqi.nr.definition.common.TaskLinkMatchingType;
import com.jiuqi.nr.definition.facade.DesignFormSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.definition.facade.DesignTaskLinkDefine;
import com.jiuqi.nr.definition.facade.TaskLinkDefine;
import com.jiuqi.nr.definition.facade.TaskLinkOrgMappingRule;
import com.jiuqi.nr.definition.facade.TaskOrgLinkDefine;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.model.IEntityAttribute;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.entity.model.IEntityRefer;
import com.jiuqi.nr.entity.service.IEntityDataService;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.formula.dto.TaskDataDTO;
import com.jiuqi.nr.formula.dto.TaskLinkDTO;
import com.jiuqi.nr.formula.service.ITaskLinkService;
import com.jiuqi.nr.formula.web.vo.PeriodTypeVO;
import com.jiuqi.nr.formula.web.vo.TaskLinkDimEntityVO;
import com.jiuqi.nr.formula.web.vo.TaskLinkDimMappingVO;
import com.jiuqi.nr.formula.web.vo.TaskLinkEntityBaseVO;
import com.jiuqi.nr.formula.web.vo.TaskLinkEntityQueryVO;
import com.jiuqi.nr.formula.web.vo.TaskLinkOrgMappingVO;
import com.jiuqi.nr.formula.web.vo.TaskLinkSettingVO;
import com.jiuqi.nr.formula.web.vo.TaskLinkVO;
import com.jiuqi.nr.period.common.utils.PeriodUtils;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nr.period.modal.IPeriodEntity;
import com.jiuqi.nr.task.api.common.Constants;
import com.jiuqi.util.OrderGenerator;
import com.jiuqi.util.StringUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class TaskLinkServiceImpl
implements ITaskLinkService {
    @Autowired
    private IDesignTimeViewController designTimeViewController;
    @Autowired
    private DefinitionAuthorityProvider definitionAuthorityProvider;
    @Autowired
    private IPeriodEntityAdapter periodAdapter;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    @Autowired
    private IEntityViewRunTimeController entityViewRunTimeController;
    @Autowired
    private IEntityDataService entityDataService;
    @Autowired
    private IDesignDataSchemeService designDataSchemeService;

    @Override
    public TaskLinkSettingVO getTaskLinkSetting(String formScheme) {
        TaskLinkSettingVO taskLinkSetting = new TaskLinkSettingVO(this.listTaskLinks(formScheme));
        DesignFormSchemeDefine formSchemeDefine = this.designTimeViewController.getFormScheme(formScheme);
        taskLinkSetting.setCurrentTaskEntities(this.buildTaskEntityBase(formSchemeDefine.getTaskKey()));
        taskLinkSetting.setCurrentTaskDimEntities(this.buildTaskDimEntityBase(formSchemeDefine.getTaskKey()));
        return taskLinkSetting;
    }

    @Override
    public List<TaskLinkVO> listTaskLinks(String formScheme) {
        List taskLinkDefines = this.designTimeViewController.listTaskLinkByFormScheme(formScheme);
        if (CollectionUtils.isEmpty(taskLinkDefines)) {
            return Collections.emptyList();
        }
        List linkDTOS = taskLinkDefines.stream().sorted(Comparator.comparing(TaskLinkDefine::getLinkAlias)).map(TaskLinkDTO::defineToDto).collect(Collectors.toList());
        ArrayList<TaskLinkVO> linkVOs = new ArrayList<TaskLinkVO>(linkDTOS.size());
        DesignFormSchemeDefine formSchemeDefine = this.designTimeViewController.getFormScheme(formScheme);
        DesignTaskDefine currentTask = this.designTimeViewController.getTask(formSchemeDefine.getTaskKey());
        IPeriodEntity periodEntity = this.periodAdapter.getPeriodEntity(currentTask.getDateTime());
        boolean period13 = PeriodUtils.isPeriod13((String)periodEntity.getCode(), (PeriodType)periodEntity.getPeriodType());
        for (TaskLinkDTO linkDTO : linkDTOS) {
            TaskLinkVO instance = TaskLinkVO.getInstance(linkDTO);
            this.setTaskLinkOrgMapping(instance, linkDTO);
            this.setTaskLinkDimMapping(instance, linkDTO);
            String relatedFormScheme = linkDTO.getRelatedFormScheme();
            DesignFormSchemeDefine relatedFormSchemeDefine = this.designTimeViewController.getFormScheme(relatedFormScheme);
            if (relatedFormSchemeDefine != null) {
                instance.setReferFormSchemeTitle(relatedFormSchemeDefine.getTitle());
                DesignTaskDefine task = this.designTimeViewController.getTask(relatedFormSchemeDefine.getTaskKey());
                if (task != null) {
                    instance.setReferTaskTitle(task.getTitle());
                    instance.setReferPeriodType(task.getDateTime());
                    instance.setReferTaskKey(task.getKey());
                }
            } else {
                instance.setDeleted(true);
            }
            instance.setCurrentPeriodType(currentTask.getDateTime());
            instance.setCurrent13Y(period13);
            linkVOs.add(instance);
        }
        return linkVOs;
    }

    private void setTaskLinkOrgMapping(TaskLinkVO taskLinkVO, TaskLinkDTO taskLinkDTO) {
        List<TaskLinkOrgMappingVO> taskLinkOrgMappingVO = taskLinkDTO.getTaskLinkOrgRules().stream().map(TaskLinkOrgMappingVO::new).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(taskLinkOrgMappingVO)) {
            taskLinkVO.setTaskLinkOrgMapping(new ArrayList<TaskLinkOrgMappingVO>());
            return;
        }
        for (TaskLinkOrgMappingVO orgMappingVO : taskLinkOrgMappingVO) {
            orgMappingVO.setTargetEntityTitle(this.getEntityTitle(orgMappingVO.getTargetEntity()));
            orgMappingVO.setSourceEntityTitle(this.getEntityTitle(orgMappingVO.getSourceEntity()));
        }
        taskLinkVO.setTaskLinkOrgMapping(taskLinkOrgMappingVO);
    }

    private String getEntityTitle(String entityId) {
        if (!StringUtils.isEmpty((String)entityId)) {
            IEntityDefine entityDefine = this.entityMetaService.queryEntity(entityId);
            if (entityDefine != null) {
                return entityDefine.getTitle();
            }
            return null;
        }
        return null;
    }

    private void setTaskLinkDimMapping(TaskLinkVO taskLinkVO, TaskLinkDTO taskLinkDTO) {
        List<TaskLinkDimMappingVO> taskLinkDimMappings = taskLinkDTO.getTaskLinkDimRules().stream().map(TaskLinkDimMappingVO::new).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(taskLinkDimMappings)) {
            taskLinkVO.setTaskLinkDimMapping(new ArrayList<TaskLinkDimMappingVO>());
            return;
        }
        for (TaskLinkDimMappingVO orgMappingVO : taskLinkDimMappings) {
            IEntityDefine entityDefine = this.entityMetaService.queryEntity(orgMappingVO.getDimKey());
            if (entityDefine == null) continue;
            IEntityTable entityTable = this.getEntityTable(entityDefine);
            orgMappingVO.setDimName(entityDefine.getTitle());
            orgMappingVO.setDimDataName(entityTable.findByEntityKey(orgMappingVO.getDimData()).getTitle());
        }
        taskLinkVO.setTaskLinkDimMapping(taskLinkDimMappings);
    }

    @Override
    public List<TaskDataDTO> getTaskList() {
        List taskDefines = this.designTimeViewController.listAllTask();
        ArrayList<TaskDataDTO> taskData = new ArrayList<TaskDataDTO>();
        for (DesignTaskDefine taskDefine : taskDefines) {
            if (!this.definitionAuthorityProvider.canReadTask(taskDefine.getKey())) continue;
            TaskDataDTO data = new TaskDataDTO();
            data.setKey(taskDefine.getKey());
            data.setTitle(taskDefine.getTitle());
            data.setCode(taskDefine.getTaskCode());
            data.setChildren(this.getScheme(taskDefine));
            taskData.add(data);
        }
        return taskData;
    }

    private List<TaskDataDTO> getScheme(DesignTaskDefine taskDefine) {
        List schemeDefines = this.designTimeViewController.listFormSchemeByTask(taskDefine.getKey());
        if (CollectionUtils.isEmpty(schemeDefines)) {
            return Collections.emptyList();
        }
        ArrayList<TaskDataDTO> res = new ArrayList<TaskDataDTO>();
        for (DesignFormSchemeDefine schemeDefine : schemeDefines) {
            TaskDataDTO data = new TaskDataDTO();
            data.setKey(schemeDefine.getKey());
            data.setTitle(schemeDefine.getTitle());
            data.setCode(schemeDefine.getFormSchemeCode());
            data.setParent(taskDefine.getKey());
            data.setParentTitle(taskDefine.getTitle());
            res.add(data);
        }
        return res;
    }

    @Override
    public TaskLinkEntityQueryVO queryRelatedTaskEntities(String taskKey) {
        TaskLinkEntityQueryVO entityQueryVO = new TaskLinkEntityQueryVO();
        DesignTaskDefine task = this.designTimeViewController.getTask(taskKey);
        if (task == null) {
            return entityQueryVO;
        }
        entityQueryVO.setTaskId(taskKey);
        entityQueryVO.setEntities(this.buildTaskEntityBase(taskKey));
        entityQueryVO.setDimEntities(this.buildTaskLinkDimsEntities(task));
        return entityQueryVO;
    }

    private List<TaskLinkEntityBaseVO> buildTaskEntityBase(String TaskKey) {
        ArrayList<TaskLinkEntityBaseVO> taskEntityBaseObj = new ArrayList<TaskLinkEntityBaseVO>();
        List taskOrgLinkDefines = this.designTimeViewController.listTaskOrgLinkByTask(TaskKey);
        for (TaskOrgLinkDefine taskOrgLinkDefine : taskOrgLinkDefines) {
            IEntityDefine entityDefine = this.entityMetaService.queryEntity(taskOrgLinkDefine.getEntity());
            if (entityDefine == null) continue;
            TaskLinkEntityBaseVO taskEntityBaseVO = new TaskLinkEntityBaseVO(entityDefine.getId(), entityDefine.getTitle(), entityDefine.getCode());
            taskEntityBaseObj.add(taskEntityBaseVO);
        }
        return taskEntityBaseObj;
    }

    private List<TaskLinkEntityBaseVO> buildTaskDimEntityBase(String taskKey) {
        String[] dimKeys;
        ArrayList<TaskLinkEntityBaseVO> dimEntities = new ArrayList<TaskLinkEntityBaseVO>();
        DesignTaskDefine task = this.designTimeViewController.getTask(taskKey);
        String dims = task.getDims();
        if (StringUtils.isEmpty((String)dims)) {
            return dimEntities;
        }
        for (String dimKey : dimKeys = dims.split(";")) {
            IEntityDefine entityDefine = this.entityMetaService.queryEntity(dimKey);
            if (entityDefine == null) continue;
            TaskLinkEntityBaseVO baseVO = new TaskLinkEntityBaseVO();
            baseVO.setEntityId(entityDefine.getId());
            baseVO.setEntityTitle(entityDefine.getTitle());
            baseVO.setEntityCode(entityDefine.getCode());
            dimEntities.add(baseVO);
        }
        return dimEntities;
    }

    private List<TaskLinkDimEntityVO> buildTaskLinkDimsEntities(DesignTaskDefine task) {
        ArrayList<TaskLinkDimEntityVO> dimList = new ArrayList<TaskLinkDimEntityVO>();
        String dims = task.getDims();
        if (!StringUtils.isEmpty((String)dims)) {
            String[] dimKeys;
            for (String currentDimKey : dimKeys = dims.split(";")) {
                IEntityDefine entityDefine = this.entityMetaService.queryEntity(currentDimKey);
                if (!this.isNeedChoose(entityDefine, task.getKey(), task.getDataScheme())) continue;
                TaskLinkDimEntityVO relatedTaskDimVO = new TaskLinkDimEntityVO(entityDefine.getId(), entityDefine.getTitle(), entityDefine.getCode());
                ArrayList<TaskLinkEntityBaseVO> data = new ArrayList<TaskLinkEntityBaseVO>();
                List entityRows = this.getEntityTable(entityDefine).getAllRows();
                for (IEntityRow entityRow : entityRows) {
                    TaskLinkEntityBaseVO relatedTaskDimData = new TaskLinkEntityBaseVO(entityRow.getEntityKeyData(), entityRow.getTitle(), entityRow.getCode());
                    data.add(relatedTaskDimData);
                }
                relatedTaskDimVO.setData(data);
                dimList.add(relatedTaskDimVO);
            }
        }
        return dimList;
    }

    private boolean isNeedChoose(IEntityDefine dimEntity, String taskKey, String dataSchemeKey) {
        if (dimEntity == null) {
            return false;
        }
        List taskOrgLinkDefines = this.designTimeViewController.listTaskOrgLinkByTask(taskKey);
        IEntityDefine unitEntity = null;
        if (taskOrgLinkDefines.size() == 1) {
            unitEntity = this.entityMetaService.queryEntity(((TaskOrgLinkDefine)taskOrgLinkDefines.get(0)).getEntity());
        } else if (taskOrgLinkDefines.size() > 1) {
            unitEntity = this.entityMetaService.queryEntity("MD_ORG@ORG");
        }
        if (unitEntity != null) {
            List entityRefer = this.entityMetaService.getEntityRefer(unitEntity.getId());
            IEntityModel entityModel = this.entityMetaService.getEntityModel(unitEntity.getId());
            List entityRefers = entityRefer.stream().filter(e -> e.getReferEntityId().equals(dimEntity.getId())).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(entityRefers)) {
                if (entityRefers.size() == 1) {
                    IEntityAttribute attribute = entityModel.getAttribute(((IEntityRefer)entityRefers.get(0)).getOwnField());
                    return attribute.isMultival();
                }
                List dataSchemeDimension = this.designDataSchemeService.getDataSchemeDimension(dataSchemeKey);
                Optional<DesignDataDimension> dimension = dataSchemeDimension.stream().filter(e -> e.getDimKey().equals(dimEntity.getId())).findAny();
                if (dimension.isPresent()) {
                    String dimAttribute = dimension.get().getDimAttribute();
                    if (StringUtils.isNotEmpty((String)dimAttribute)) {
                        IEntityAttribute attribute = entityModel.getAttribute(dimAttribute);
                        return attribute.isMultival();
                    }
                    for (IEntityRefer refer : entityRefers) {
                        if (!entityModel.getAttribute(refer.getOwnField()).isMultival()) continue;
                        return true;
                    }
                }
            } else {
                return true;
            }
        }
        return false;
    }

    private IEntityTable getEntityTable(IEntityDefine entityDefine) {
        try {
            ExecutorContext executorContext = new ExecutorContext(this.dataDefinitionRuntimeController);
            EntityViewDefine entityViewDefine = this.entityViewRunTimeController.buildEntityView(entityDefine.getId());
            IEntityQuery entityQuery = this.entityDataService.newEntityQuery();
            entityQuery.setEntityView(entityViewDefine);
            return entityQuery.executeReader((IContext)executorContext);
        }
        catch (Exception e) {
            throw new RuntimeException("\u67e5\u627e\u60c5\u666f\u51fa\u9519", e);
        }
    }

    @Override
    public void saveTaskLinks(List<TaskLinkVO> datas) {
        if (CollectionUtils.isEmpty(datas)) {
            return;
        }
        Map<Constants.DataStatus, List<TaskLinkVO>> map = datas.stream().collect(Collectors.groupingBy(TaskLinkDTO::getStatus));
        List<TaskLinkVO> insertDto = map.getOrDefault(Constants.DataStatus.NEW, Collections.emptyList());
        DesignTaskLinkDefine[] insertDefines = TaskLinkVO.toDefine(insertDto, () -> this.designTimeViewController.initTaskLink());
        if (insertDefines != null) {
            this.checkTaskLink(insertDefines);
            this.designTimeViewController.insertTaskLink(insertDefines);
        }
        List deletesDto = map.getOrDefault(Constants.DataStatus.DELETE, Collections.emptyList());
        String[] strings = (String[])deletesDto.stream().map(TaskLinkDTO::getKey).toArray(String[]::new);
        this.designTimeViewController.deleteTaskLink(strings);
        List<TaskLinkVO> modifyDto = map.getOrDefault(Constants.DataStatus.MODIFY, Collections.emptyList());
        DesignTaskLinkDefine[] modifyDefines = TaskLinkVO.toDefine(modifyDto, () -> this.designTimeViewController.initTaskLink());
        if (modifyDefines != null) {
            this.checkTaskLink(modifyDefines);
            this.designTimeViewController.updateTaskLink(modifyDefines);
        }
    }

    private void checkTaskLink(DesignTaskLinkDefine[] needCheckTaskLinks) {
        for (DesignTaskLinkDefine needCheckTaskLink : needCheckTaskLinks) {
            if (!CollectionUtils.isEmpty(needCheckTaskLink.getOrgMappingRules())) continue;
            DesignFormSchemeDefine currentFormScheme = this.designTimeViewController.getFormScheme(needCheckTaskLink.getCurrentFormSchemeKey());
            DesignFormSchemeDefine relatedFormScheme = this.designTimeViewController.getFormScheme(needCheckTaskLink.getRelatedFormSchemeKey());
            DesignTaskDefine currentTask = this.designTimeViewController.getTask(currentFormScheme.getTaskKey());
            DesignTaskDefine relatedTask = this.designTimeViewController.getTask(relatedFormScheme.getTaskKey());
            TaskLinkOrgMappingRule mappingDefine = new TaskLinkOrgMappingRule();
            mappingDefine.setTaskLinkKey(needCheckTaskLink.getKey());
            mappingDefine.setSourceEntity(relatedTask.getDw());
            mappingDefine.setTargetEntity(currentTask.getDw());
            mappingDefine.setMatchingType(needCheckTaskLink.getMatchingType());
            mappingDefine.setOrder(OrderGenerator.newOrder());
            if (TaskLinkMatchingType.FORM_TYPE_EXPRESSION.equals((Object)needCheckTaskLink.getMatchingType())) {
                mappingDefine.setTargetFormula(needCheckTaskLink.getCurrentFormula());
                mappingDefine.setSourceFormula(needCheckTaskLink.getRelatedFormula());
                mappingDefine.setExpressionType(needCheckTaskLink.getExpressionType());
            }
            needCheckTaskLink.setOrgMappingRule(Collections.singletonList(mappingDefine));
        }
    }

    @Override
    public PeriodTypeVO getPeriodType(String schemeKey) {
        DesignFormSchemeDefine formScheme = this.designTimeViewController.getFormScheme(schemeKey);
        if (formScheme == null) {
            return null;
        }
        DesignTaskDefine task = this.designTimeViewController.getTask(formScheme.getTaskKey());
        if (task != null) {
            String periodType = task.getDateTime();
            IPeriodEntity periodEntity = this.periodAdapter.getPeriodEntity(periodType);
            if (periodEntity == null) {
                return null;
            }
            PeriodTypeVO periodTypeVO = new PeriodTypeVO();
            periodTypeVO.setPeriodType(periodType);
            periodTypeVO.setIs13Y(PeriodUtils.isPeriod13((String)periodEntity.getCode(), (PeriodType)periodEntity.getPeriodType()));
            return periodTypeVO;
        }
        return null;
    }
}

