/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob
 *  com.jiuqi.np.asynctask.AsyncTaskManager
 *  com.jiuqi.np.asynctask.NpRealTimeTaskInfo
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.np.definition.facade.IDimensionFilter
 *  com.jiuqi.nr.common.itree.ITree
 *  com.jiuqi.nr.datascheme.adjustment.util.AdjustUtils
 *  com.jiuqi.nr.datascheme.api.DesignDataScheme
 *  com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService
 *  com.jiuqi.nr.definition.api.IDesignTimeViewController
 *  com.jiuqi.nr.definition.api.IRunTimeViewController
 *  com.jiuqi.nr.definition.auth.DefinitionAuthorityProvider
 *  com.jiuqi.nr.definition.exception.NrDefinitionRuntimeException
 *  com.jiuqi.nr.definition.facade.DesignDimensionFilter
 *  com.jiuqi.nr.definition.facade.DesignFormSchemeDefine
 *  com.jiuqi.nr.definition.facade.DesignTaskDefine
 *  com.jiuqi.nr.definition.facade.DesignTaskGroupDefine
 *  com.jiuqi.nr.definition.internal.dao.DesignTaskGroupLinkDao
 *  com.jiuqi.nr.definition.internal.dao.ProgressLoadingDao
 *  com.jiuqi.nr.definition.internal.impl.DesignTaskGroupLink
 *  com.jiuqi.nr.definition.internal.impl.ProgressLoadingImpl
 *  com.jiuqi.nr.definition.internal.service.DesignTaskDefineService
 *  com.jiuqi.nr.definition.paramcheck.IDesignParamCheckService
 *  com.jiuqi.nr.definition.planpublish.dao.TaskPlanPublishDao
 *  com.jiuqi.nr.definition.planpublish.entity.TaskPlanPublish
 *  com.jiuqi.nr.entity.component.tree.service.EntityTreeService
 *  com.jiuqi.nr.entity.component.tree.vo.TreeNode
 *  com.jiuqi.nr.entity.component.tree.vo.TreeParam
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.task.api.common.Constants$ActionType
 *  com.jiuqi.nr.task.api.common.FileDownload
 *  com.jiuqi.nr.task.api.status.ReleaseStatus
 *  com.jiuqi.nr.task.api.tree.TreeData
 *  com.jiuqi.nr.task.api.tree.UITreeNode
 *  com.jiuqi.util.OrderGenerator
 *  com.jiuqi.va.domain.basedata.BaseDataDO
 *  com.jiuqi.va.domain.basedata.BaseDataDTO
 *  com.jiuqi.va.domain.basedata.BaseDataOption$OrderType
 *  com.jiuqi.va.domain.basedata.handle.BaseDataSortDTO
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.feign.client.BaseDataClient
 *  com.jiuqi.xlib.runtime.Assert
 *  javax.servlet.http.HttpServletResponse
 *  org.jetbrains.annotations.NotNull
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.nr.task.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob;
import com.jiuqi.np.asynctask.AsyncTaskManager;
import com.jiuqi.np.asynctask.NpRealTimeTaskInfo;
import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.np.definition.facade.IDimensionFilter;
import com.jiuqi.nr.common.itree.ITree;
import com.jiuqi.nr.datascheme.adjustment.util.AdjustUtils;
import com.jiuqi.nr.datascheme.api.DesignDataScheme;
import com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService;
import com.jiuqi.nr.definition.api.IDesignTimeViewController;
import com.jiuqi.nr.definition.api.IRunTimeViewController;
import com.jiuqi.nr.definition.auth.DefinitionAuthorityProvider;
import com.jiuqi.nr.definition.exception.NrDefinitionRuntimeException;
import com.jiuqi.nr.definition.facade.DesignDimensionFilter;
import com.jiuqi.nr.definition.facade.DesignFormSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.definition.facade.DesignTaskGroupDefine;
import com.jiuqi.nr.definition.internal.dao.DesignTaskGroupLinkDao;
import com.jiuqi.nr.definition.internal.dao.ProgressLoadingDao;
import com.jiuqi.nr.definition.internal.impl.DesignTaskGroupLink;
import com.jiuqi.nr.definition.internal.impl.ProgressLoadingImpl;
import com.jiuqi.nr.definition.internal.service.DesignTaskDefineService;
import com.jiuqi.nr.definition.paramcheck.IDesignParamCheckService;
import com.jiuqi.nr.definition.planpublish.dao.TaskPlanPublishDao;
import com.jiuqi.nr.definition.planpublish.entity.TaskPlanPublish;
import com.jiuqi.nr.entity.component.tree.service.EntityTreeService;
import com.jiuqi.nr.entity.component.tree.vo.TreeNode;
import com.jiuqi.nr.entity.component.tree.vo.TreeParam;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.task.api.common.Constants;
import com.jiuqi.nr.task.api.common.FileDownload;
import com.jiuqi.nr.task.api.status.ReleaseStatus;
import com.jiuqi.nr.task.api.tree.TreeData;
import com.jiuqi.nr.task.api.tree.UITreeNode;
import com.jiuqi.nr.task.auth.IAuthCheckService;
import com.jiuqi.nr.task.dto.FormSchemeStatusDTO;
import com.jiuqi.nr.task.dto.TaskDetailDTO;
import com.jiuqi.nr.task.dto.TaskDimension;
import com.jiuqi.nr.task.dto.taskOrderMoveDTO;
import com.jiuqi.nr.task.exception.TaskException;
import com.jiuqi.nr.task.exception.TaskGroupRuntimeException;
import com.jiuqi.nr.task.internal.action.service.ActionService;
import com.jiuqi.nr.task.internal.async.DeleteTaskExecutor;
import com.jiuqi.nr.task.service.IFormSchemeService;
import com.jiuqi.nr.task.service.ITaskService;
import com.jiuqi.nr.task.service.IValidateTimeService;
import com.jiuqi.nr.task.tools.TaskTools;
import com.jiuqi.nr.task.web.vo.DwTaskOptionVO;
import com.jiuqi.nr.task.web.vo.TaskDimensionVO;
import com.jiuqi.nr.task.web.vo.TaskItemVO;
import com.jiuqi.nr.task.web.vo.TaskOrgListVO;
import com.jiuqi.nr.task.web.vo.TaskParamVO;
import com.jiuqi.nr.task.web.vo.TaskPublishInfo;
import com.jiuqi.nr.task.web.vo.TaskResourceSearchVO;
import com.jiuqi.util.OrderGenerator;
import com.jiuqi.va.domain.basedata.BaseDataDO;
import com.jiuqi.va.domain.basedata.BaseDataDTO;
import com.jiuqi.va.domain.basedata.BaseDataOption;
import com.jiuqi.va.domain.basedata.handle.BaseDataSortDTO;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.feign.client.BaseDataClient;
import com.jiuqi.xlib.runtime.Assert;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service
public class TaskServiceImpl
implements ITaskService {
    private static final Logger logger = LoggerFactory.getLogger(TaskServiceImpl.class);
    @Autowired
    private IDesignTimeViewController designTimeViewController;
    @Autowired
    private DefinitionAuthorityProvider definitionAuthority;
    @Autowired
    private TaskTools taskTools;
    @Autowired
    private IFormSchemeService formSchemeService;
    @Autowired
    private IDesignDataSchemeService designDataSchemeService;
    @Autowired
    private IEntityMetaService iEntityMetaService;
    @Autowired
    private IAuthCheckService authCheckService;
    @Autowired
    private IValidateTimeService validateTimeService;
    @Autowired
    private BaseDataClient baseDataClient;
    @Autowired
    private EntityTreeService entityTreeService;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private IDesignParamCheckService paramCheckService;
    @Autowired
    private DesignTaskGroupLinkDao groupLinkDao;
    @Autowired(required=false)
    private ActionService actionService;
    @Autowired
    private TaskPlanPublishDao taskPlanPublishDao;
    @Autowired
    ProgressLoadingDao progressLoadingDao;
    @Autowired
    private AsyncTaskManager asyncTaskManager;
    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private DesignTaskDefineService designTaskDefineService;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public TaskParamVO initTask(String dataScheme) {
        TaskParamVO taskParam = new TaskParamVO();
        DesignDataScheme dataSchemeDefine = this.designDataSchemeService.getDataScheme(dataScheme);
        List dimensions = this.designDataSchemeService.getDataSchemeDimension(dataScheme);
        dimensions.removeIf(x -> AdjustUtils.isAdjust((String)x.getDimKey()));
        this.taskTools.setDimToTaskParam(dimensions, taskParam);
        taskParam.setPeriodOffset(0);
        taskParam.setCode(OrderGenerator.newOrder());
        taskParam.setTitle(this.taskTools.getNewTaskTitle());
        taskParam.setDataScheme(dataScheme);
        taskParam.setDataSchemeTitle(this.designDataSchemeService.getDataScheme(dataScheme).getTitle());
        this.setTaskOrgList(taskParam);
        return taskParam;
    }

    private void setTaskOrgList(TaskParamVO taskParam) {
        ArrayList<TaskOrgListVO> orglist = new ArrayList<TaskOrgListVO>();
        IEntityDefine entityDefine = taskParam.getOrgDimScope().size() >= 1 ? this.iEntityMetaService.queryEntity(taskParam.getOrgDimScope().get(0).getEntityID()) : this.iEntityMetaService.queryEntity(taskParam.getDw().getEntityID());
        TaskOrgListVO taskOrgListVO = new TaskOrgListVO();
        taskOrgListVO.setOrgName(entityDefine.getTitle());
        taskOrgListVO.setEntityId(entityDefine.getId());
        taskOrgListVO.setOrder(OrderGenerator.newOrder());
        orglist.add(taskOrgListVO);
        taskParam.setOrgList(orglist);
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public TaskItemVO insertTask(TaskDetailDTO taskDetail) {
        DesignTaskDefine newTask = this.buildNewTaskDefine(taskDetail);
        this.designTimeViewController.insertTask(newTask);
        if (StringUtils.hasText(taskDetail.getGroup()) && !taskDetail.getGroup().equals("00000000-0000-0000-0000-000000000000")) {
            if (!this.definitionAuthority.canTaskGroupModeling(taskDetail.getGroup())) {
                throw new NrDefinitionRuntimeException((ErrorEnum)TaskException.NO_AUTHORITY);
            }
            DesignTaskGroupLink taskGroupLink = this.designTimeViewController.initTaskGroupLink();
            taskGroupLink.setTaskKey(newTask.getKey());
            taskGroupLink.setGroupKey(taskDetail.getGroup());
            this.designTimeViewController.insertTaskGroupLink(new DesignTaskGroupLink[]{taskGroupLink});
        }
        taskDetail.setKey(newTask.getKey());
        String formSchemeKey = this.formSchemeService.insertDefaultFormScheme(taskDetail.getKey());
        this.validateTimeService.insertDefaultSchemePeriodLink(formSchemeKey, taskDetail.getStarterPeriod(), taskDetail.getEndPeriod(), taskDetail.getDateTime());
        String identityId = NpContextHolder.getContext().getIdentityId();
        if (identityId == null) {
            throw new NrDefinitionRuntimeException((ErrorEnum)TaskException.NO_AUTHORITY);
        }
        this.definitionAuthority.grantAllPrivileges(newTask.getKey());
        this.definitionAuthority.grantAllPrivilegesToFormScheme(formSchemeKey);
        return this.getTaskItemVO(newTask);
    }

    public DesignTaskDefine buildNewTaskDefine(TaskDetailDTO taskDetail) {
        DesignTaskDefine newTask = this.designTimeViewController.initTask();
        newTask.setTitle(taskDetail.getTitle());
        newTask.setTaskCode(taskDetail.getCode());
        newTask.setDataScheme(taskDetail.getDataScheme());
        newTask.setDw(taskDetail.getDw().getEntityId());
        ArrayList<String> dimKeys = new ArrayList<String>();
        for (TaskDimension taskDimension : taskDetail.getDims()) {
            dimKeys.add(taskDimension.getEntityId());
        }
        newTask.setDims(dimKeys.stream().collect(Collectors.joining(";")));
        newTask.setPeriodType(taskDetail.getPeriodType());
        newTask.setTaskPeriodOffset(taskDetail.getPeriodOffset().intValue());
        newTask.setFromPeriod(taskDetail.getStarterPeriod());
        newTask.setToPeriod(taskDetail.getEndPeriod());
        newTask.setMeasureUnit(taskDetail.getMeasureUnit());
        newTask.setDateTime(taskDetail.getDateTime());
        newTask.setTaskGatherType(taskDetail.getGatherType());
        newTask.setFilterExpression(taskDetail.getDw().getFilterWay() == 0 ? taskDetail.getDw().getExpression() : null);
        newTask.setFilterTemplate(taskDetail.getDw().getFilterWay() == 1 ? taskDetail.getDw().getExpression() : null);
        newTask.setEfdcSwitch(true);
        this.taskTools.insertTaskOrgLink(taskDetail.getTaskOrgList(), newTask.getKey());
        this.taskTools.insertDimFilter(taskDetail.getDims(), newTask.getKey());
        return newTask;
    }

    @Override
    public TaskParamVO getTask(String taskKey) {
        Assert.notNull((Object)taskKey, (String)"taskKey must not be null.");
        this.authCheckService.checkTaskAuth(taskKey);
        DesignTaskDefine task = this.designTimeViewController.getTask(taskKey);
        TaskParamVO taskParam = new TaskParamVO();
        taskParam.setKey(taskKey);
        taskParam.setTitle(task.getTitle());
        taskParam.setDataScheme(task.getDataScheme());
        taskParam.setDataSchemeTitle(this.designDataSchemeService.getDataScheme(task.getDataScheme()).getTitle());
        List dimensions = this.designDataSchemeService.getDataSchemeDimension(task.getDataScheme());
        this.taskTools.setTaskOrgList(task, taskParam, dimensions);
        this.taskTools.setDims(task, taskParam, dimensions);
        this.taskTools.setPeriod(task, taskParam, dimensions);
        taskParam.setVersion(task.getVersion());
        return taskParam;
    }

    @Override
    public TaskItemVO updateTask(TaskDetailDTO taskDetail) {
        DesignTaskDefine task = this.designTimeViewController.getTask(taskDetail.getKey());
        task.setTaskCode(taskDetail.getCode());
        task.setTitle(taskDetail.getTitle());
        task.setDw(taskDetail.getDw().getEntityId());
        task.setTaskPeriodOffset(taskDetail.getPeriodOffset().intValue());
        this.taskTools.updateSchemeValidateTime(taskDetail.getStarterPeriod(), taskDetail.getEndPeriod(), task, false);
        this.taskTools.updateDimFilter(taskDetail.getDims(), taskDetail.getKey());
        this.taskTools.updateTaskOrgLink(taskDetail.getTaskOrgList(), taskDetail.getKey());
        this.designTimeViewController.updateTask(task);
        return this.getTaskItemVO(task);
    }

    @Override
    public String deleteTask(String taskKey) {
        Assert.notNull((Object)taskKey, (String)"taskKey must not be null.");
        this.authCheckService.checkTaskAuth(taskKey);
        NpRealTimeTaskInfo info = new NpRealTimeTaskInfo();
        info.setTaskKey(taskKey);
        info.setAbstractRealTimeJob((AbstractRealTimeJob)new DeleteTaskExecutor());
        return this.asyncTaskManager.publishTask(info);
    }

    @Override
    public List<TaskItemVO> queryTask(String groupID) {
        return this.listCanShowTasks(groupID);
    }

    private List<TaskItemVO> listCanShowTasks(String groupID) {
        ArrayList<TaskItemVO> authedTasks = new ArrayList<TaskItemVO>();
        List tasks = "00000000-0000-0000-0000-000000000000".equals(groupID) ? this.designTimeViewController.listAllTask() : this.designTimeViewController.listTaskByTaskGroup(groupID);
        tasks.stream().sorted(Comparator.comparing(IBaseMetaItem::getOrder).reversed()).forEach(task -> {
            if (this.definitionAuthority.canModeling(task.getKey())) {
                authedTasks.add(this.getTaskItemVO((DesignTaskDefine)task));
            }
        });
        return authedTasks;
    }

    @NotNull
    private TaskItemVO getTaskItemVO(DesignTaskDefine task) {
        TaskItemVO taskParam = new TaskItemVO();
        taskParam.setKey(task.getKey());
        taskParam.setCode(task.getTaskCode());
        taskParam.setTitle(task.getTitle());
        taskParam.setOrder(task.getOrder());
        taskParam.setUpdateTime(this.sdf.format(task.getUpdateTime()));
        taskParam.setUpdateUser(task.getCreateUserName());
        taskParam.setDateTime(task.getDateTime());
        taskParam.setVersion(task.getVersion());
        if ("1.0".equals(task.getVersion())) {
            taskParam.setStatus(this.queryTaskStatus(task.getKey()));
        }
        taskParam.setActions(this.actionService.listActions(Constants.ActionType.TASK, task.getKey()));
        return taskParam;
    }

    @Override
    public void moveTask(taskOrderMoveDTO moveDTO) {
        Assert.hasText((String)moveDTO.getSourceKey(), (String)"sourceKey must not be null.");
        Assert.hasText((String)moveDTO.getTargetKey(), (String)"sourceKey must not be null.");
        String taskGroupKey = moveDTO.getTaskGroupKey();
        if (StringUtils.hasText(taskGroupKey) && !"00000000-0000-0000-0000-000000000000".equals(taskGroupKey)) {
            List groupLinkByGroupKey = this.groupLinkDao.getGroupLinkByGroupKey(taskGroupKey);
            DesignTaskGroupLink targetTaskGroupLink = null;
            DesignTaskGroupLink sourceTaskGroupLink = null;
            for (DesignTaskGroupLink designTaskGroupLink : groupLinkByGroupKey) {
                if (moveDTO.getSourceKey().equals(designTaskGroupLink.getTaskKey())) {
                    sourceTaskGroupLink = designTaskGroupLink;
                }
                if (!moveDTO.getTargetKey().equals(designTaskGroupLink.getTaskKey())) continue;
                targetTaskGroupLink = designTaskGroupLink;
            }
            if (sourceTaskGroupLink != null && targetTaskGroupLink != null) {
                String sourceTaskOrder = sourceTaskGroupLink.getOrder();
                sourceTaskGroupLink.setOrder(targetTaskGroupLink.getOrder());
                targetTaskGroupLink.setOrder(sourceTaskOrder);
                DesignTaskGroupLink[] updateTaskGroupLink = new DesignTaskGroupLink[]{sourceTaskGroupLink, targetTaskGroupLink};
                this.designTimeViewController.updateTaskGroupLink(updateTaskGroupLink);
            }
        } else {
            DesignTaskDefine originTask = this.designTimeViewController.getTask(moveDTO.getSourceKey());
            DesignTaskDefine targetTask = this.designTimeViewController.getTask(moveDTO.getTargetKey());
            if (null != originTask && null != targetTask) {
                String oldOrder = originTask.getOrder();
                originTask.setOrder(targetTask.getOrder());
                targetTask.setOrder(oldOrder);
                this.designTimeViewController.updateTask(originTask);
                this.designTimeViewController.updateTask(targetTask);
            }
        }
    }

    @Override
    public void changeTaskGroup(String taskKey, List<String> groupKeys) {
        Assert.notNull((Object)taskKey, (String)"taskKey must not be null.");
        Assert.notNull(groupKeys, (String)"groupKeys must not be null.");
        groupKeys = groupKeys.stream().filter(a -> StringUtils.hasText(a.trim()) && !a.equals("00000000-0000-0000-0000-000000000000")).collect(Collectors.toList());
        List originTaskGroups = this.designTimeViewController.listTaskGroupByTask(taskKey);
        HashSet<String> originTaskGroupLinks = new HashSet<String>();
        LinkedHashSet<String> needInsertTaskGroupLinks = new LinkedHashSet<String>();
        LinkedHashSet<String> needDeleteTaskGroupLinks = new LinkedHashSet<String>();
        if (originTaskGroups != null) {
            for (DesignTaskGroupDefine originTaskGroup : originTaskGroups) {
                originTaskGroupLinks.add(originTaskGroup.getKey());
            }
        }
        needInsertTaskGroupLinks.addAll(this.taskTools.judgeAdd(originTaskGroupLinks, groupKeys));
        needDeleteTaskGroupLinks.addAll(this.taskTools.judgeDelete(originTaskGroupLinks, groupKeys));
        if (needInsertTaskGroupLinks.size() > 0) {
            this.taskTools.doTaskGroupLinksAction(taskKey, needInsertTaskGroupLinks, "insert");
        }
        if (needDeleteTaskGroupLinks.size() > 0) {
            this.taskTools.doTaskGroupLinksAction(taskKey, needDeleteTaskGroupLinks, "delete");
        }
    }

    @Override
    public void checkTaskCode(String taskKey, String taskCode) {
        DesignTaskDefine task;
        if (!StringUtils.hasText(taskKey)) {
            task = this.designTimeViewController.initTask();
            task.setTaskCode(taskCode);
        } else {
            task = this.designTimeViewController.getTask(taskKey);
            if (task != null) {
                task.setTaskCode(taskCode);
            } else {
                task = this.designTimeViewController.initTask();
                task.setTaskCode(taskCode);
            }
        }
        this.paramCheckService.checkTaskCode(task);
    }

    @Override
    public List<TaskResourceSearchVO> fuzzySearchTask(String keyWord) {
        String path;
        if (!StringUtils.hasLength(keyWord)) {
            throw new TaskGroupRuntimeException("\u4efb\u52a1\u5206\u7ec4\u6a21\u7cca\u641c\u7d22\u5173\u952e\u5b57\u4e0d\u80fd\u4e3a\u7a7a");
        }
        ArrayList<TaskResourceSearchVO> resources = new ArrayList<TaskResourceSearchVO>();
        String keyWordLowerCase = keyWord.toLowerCase();
        List allTasks = this.designTimeViewController.listAllTask();
        List associatedTasks = allTasks.stream().filter(task -> (task.getTitle().toLowerCase().contains(keyWordLowerCase) || task.getTaskCode().toLowerCase().contains(keyWordLowerCase)) && this.definitionAuthority.canModeling(task.getKey())).collect(Collectors.toList());
        Map<String, String> taskOrderMap = allTasks.stream().collect(Collectors.toMap(IBaseMetaItem::getKey, IBaseMetaItem::getOrder, (k1, k2) -> k1));
        List allTaskGroupDefines = this.designTimeViewController.listAllTaskGroup();
        Map<String, DesignTaskGroupDefine> keyToTaskGroupMap = allTaskGroupDefines.stream().collect(Collectors.toMap(IBaseMetaItem::getKey, a -> a));
        HashMap<String, String> taskGroupKeyToPathMap = new HashMap<String, String>();
        for (DesignTaskDefine task2 : associatedTasks) {
            List underGroups = this.designTimeViewController.listTaskGroupByTask(task2.getKey());
            if (underGroups.isEmpty()) {
                TaskResourceSearchVO taskSearchResourceVo = new TaskResourceSearchVO(task2);
                path = "\u5168\u90e8\u4efb\u52a1/" + task2.getTitle();
                taskGroupKeyToPathMap.put("00000000-0000-0000-0000-000000000000", "\u5168\u90e8\u4efb\u52a1");
                taskSearchResourceVo.setPath(path);
                taskSearchResourceVo.setGroup("00000000-0000-0000-0000-000000000000");
                resources.add(taskSearchResourceVo);
                continue;
            }
            for (DesignTaskGroupDefine group : underGroups) {
                TaskResourceSearchVO taskSearchResourceVo = new TaskResourceSearchVO(task2);
                String prePath = "\u5168\u90e8\u4efb\u52a1/" + this.getPath(group, keyToTaskGroupMap);
                taskGroupKeyToPathMap.put(group.getKey(), prePath);
                taskSearchResourceVo.setPath(prePath + "/" + task2.getTitle());
                taskSearchResourceVo.setGroup(group.getKey());
                resources.add(taskSearchResourceVo);
            }
        }
        List<DesignFormSchemeDefine> formSchemes = this.designTimeViewController.listAllFormScheme();
        formSchemes = formSchemes.stream().filter(formScheme -> (formScheme.getTitle().toLowerCase().contains(keyWordLowerCase) || formScheme.getFormSchemeCode().toLowerCase().contains(keyWordLowerCase)) && this.definitionAuthority.canModeling(formScheme.getKey())).collect(Collectors.toList());
        formSchemes.forEach(a -> {
            if (!StringUtils.hasLength((String)taskOrderMap.get(a.getTaskKey()))) {
                taskOrderMap.put(a.getTaskKey(), a.getOrder());
            }
        });
        formSchemes.sort((o1, o2) -> ((String)taskOrderMap.get(o2.getTaskKey())).compareTo((String)taskOrderMap.get(o1.getTaskKey())));
        for (DesignFormSchemeDefine formScheme2 : formSchemes) {
            TaskResourceSearchVO formSchemeSearchVO = new TaskResourceSearchVO(formScheme2);
            path = "";
            List underGroups = this.designTimeViewController.listTaskGroupByTask(formScheme2.getTaskKey());
            DesignTaskDefine task3 = this.designTimeViewController.getTask(formScheme2.getTaskKey());
            if (null != task3 && "1.0".equals(task3.getVersion())) continue;
            if (underGroups.isEmpty()) {
                if (task3 == null) continue;
                path = "\u5168\u90e8\u4efb\u52a1/" + task3.getTitle() + "/" + formScheme2.getTitle();
                formSchemeSearchVO.setPath(path);
                formSchemeSearchVO.setGroup("00000000-0000-0000-0000-000000000000");
                resources.add(formSchemeSearchVO);
                continue;
            }
            for (DesignTaskGroupDefine group : underGroups) {
                formSchemeSearchVO = new TaskResourceSearchVO(formScheme2);
                String taskGroupPrePath = (String)taskGroupKeyToPathMap.get(group.getKey());
                if (StringUtils.hasLength(taskGroupPrePath)) {
                    if (task3 != null) {
                        path = taskGroupPrePath + "/" + task3.getTitle() + "/" + formScheme2.getTitle();
                    }
                } else {
                    String prePath = "\u5168\u90e8\u4efb\u52a1/" + this.getPath(group, keyToTaskGroupMap);
                    taskGroupKeyToPathMap.put(group.getKey(), prePath);
                    if (task3 != null) {
                        path = prePath + "/" + task3.getTitle() + "/" + formScheme2.getTitle();
                    }
                }
                formSchemeSearchVO.setPath(path);
                formSchemeSearchVO.setGroup(group.getKey());
                resources.add(formSchemeSearchVO);
            }
        }
        return resources;
    }

    private String getPath(DesignTaskGroupDefine define, Map<String, DesignTaskGroupDefine> keyToTaskGroupMap) {
        String path = define.getTitle();
        if (StringUtils.hasLength(define.getParentKey())) {
            String fatherPath = this.getPath(keyToTaskGroupMap.get(define.getParentKey()), keyToTaskGroupMap);
            path = fatherPath + "/" + path;
        }
        return path;
    }

    @Override
    public boolean checkTask(String taskKey) {
        return !this.runTimeViewController.listFormSchemeByTask(taskKey).isEmpty();
    }

    @Override
    public List<UITreeNode<TreeData>> treeDataSelect(String tableName, String parent) {
        if (tableName == null) {
            return Collections.emptyList();
        }
        BaseDataSortDTO sortDTO = new BaseDataSortDTO();
        sortDTO.setColumn("ver");
        sortDTO.setOrder(BaseDataOption.OrderType.ASC);
        BaseDataDTO baseDataDTO = new BaseDataDTO();
        baseDataDTO.setTableName(tableName);
        baseDataDTO.setOrderBy(Collections.singletonList(sortDTO));
        PageVO page = this.baseDataClient.list(baseDataDTO);
        if (page != null && page.getTotal() > 0) {
            LinkedHashMap<String, UITreeNode> baseDataDOMap = new LinkedHashMap<String, UITreeNode>();
            baseDataDOMap.put("-", new UITreeNode());
            page.getRows().forEach(x -> baseDataDOMap.put(x.getCode(), this.convertNode((BaseDataDO)x)));
            for (UITreeNode value : baseDataDOMap.values()) {
                if (!baseDataDOMap.containsKey(value.getParent())) continue;
                UITreeNode temp = (UITreeNode)baseDataDOMap.get(value.getParent());
                temp.setLeaf(false);
                temp.addChildren(value);
            }
            UITreeNode treeDataUITreeNode = (UITreeNode)baseDataDOMap.get(parent);
            return treeDataUITreeNode.getChildren();
        }
        return Collections.emptyList();
    }

    private UITreeNode<TreeData> convertNode(BaseDataDO baseDataDO) {
        UITreeNode uiTreeNode = new UITreeNode();
        uiTreeNode.setParent(baseDataDO.getParentcode());
        uiTreeNode.setKey(baseDataDO.getCode());
        uiTreeNode.setTitle(baseDataDO.getName());
        uiTreeNode.setOrder(baseDataDO.getVer().toString());
        return uiTreeNode;
    }

    @Override
    public List<ITree<TreeNode>> unitTreeInit(TreeParam param) {
        return this.entityTreeService.initTree(param);
    }

    @Override
    public List<ITree<TreeNode>> unitTreeChildren(TreeParam param) {
        return this.entityTreeService.getChildrenNodes(param);
    }

    @Override
    public List<ITree<TreeNode>> locationTreeNode(TreeParam param) {
        return this.entityTreeService.locationTreeNode(param);
    }

    @Override
    public boolean checkTaskTitle(TaskParamVO taskParam) {
        DesignTaskDefine taskByTitle = this.designTimeViewController.getTaskByTitle(taskParam.getTitle().trim());
        if (StringUtils.hasText(taskParam.getKey())) {
            return taskByTitle != null && !taskByTitle.getKey().equals(taskParam.getKey());
        }
        return taskByTitle != null;
    }

    @Override
    public DwTaskOptionVO getDwTaskOption(String taskKey) {
        DwTaskOptionVO res = new DwTaskOptionVO();
        DesignTaskDefine task = this.designTimeViewController.getTask(taskKey);
        res.setDw(new TaskDimensionVO(true, task.getDw(), null, task.getFilterExpression()));
        List designDimensionFilter = this.designTimeViewController.listDimensionFilterByTask(taskKey);
        Map<String, DesignDimensionFilter> dimFilterMap = designDimensionFilter.stream().collect(Collectors.toMap(IDimensionFilter::getEntityId, v -> v));
        ArrayList<TaskDimensionVO> dimsVO = new ArrayList<TaskDimensionVO>();
        if (task.getDims() == null) {
            return res;
        }
        List dimKeys = Arrays.stream(task.getDims().split(";")).collect(Collectors.toList());
        for (String dimKey : dimKeys) {
            IEntityDefine iEntityDefine = this.iEntityMetaService.queryEntity(dimKey);
            if (iEntityDefine == null) continue;
            TaskDimensionVO taskDimension = dimFilterMap.get(dimKey) != null && !CollectionUtils.isEmpty(dimFilterMap.get(dimKey).getList()) ? new TaskDimensionVO(dimKey, iEntityDefine.getTitle(), false, JSONUtil.toJSONString((Object)dimFilterMap.get(dimKey).getList())) : new TaskDimensionVO(dimKey, iEntityDefine.getTitle(), false, null);
            dimsVO.add(taskDimension);
        }
        res.setDims(dimsVO);
        return res;
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void saveDwTaskOption(DwTaskOptionVO dwTaskOptionVO) throws Exception {
        DesignTaskDefine task = this.designTimeViewController.getTask(dwTaskOptionVO.getTaskKey());
        TaskDimensionVO dw = dwTaskOptionVO.getDw();
        task.setFilterExpression(dw.getFilterMessage());
        this.designTaskDefineService.updateTaskExpression(task);
        List<TaskDimensionVO> dims = dwTaskOptionVO.getDims();
        if (!CollectionUtils.isEmpty(dims)) {
            ArrayList<TaskDimension> dimensions = new ArrayList<TaskDimension>(dims.size());
            for (TaskDimensionVO dimensionVO : dims) {
                TaskDimension taskDimension;
                if (dimensionVO.getFilterMessage() != null) {
                    ObjectMapper objectMapper = new ObjectMapper();
                    try {
                        taskDimension = new TaskDimension(dimensionVO, (List)objectMapper.readValue(dimensionVO.getFilterMessage(), (TypeReference)new TypeReference<List<String>>(){}));
                    }
                    catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    taskDimension = new TaskDimension(dimensionVO);
                }
                dimensions.add(taskDimension);
            }
            this.taskTools.updateDimFilter(dimensions, dwTaskOptionVO.getTaskKey());
        }
    }

    @Override
    public FormSchemeStatusDTO queryTaskStatus(String taskKey) {
        FormSchemeStatusDTO schemeStatusDTO = new FormSchemeStatusDTO();
        try {
            TaskPlanPublish query = this.taskPlanPublishDao.getByTask(taskKey);
            if (query != null) {
                if (ReleaseStatus.PROTECT_END.toString().equals(query.getPublishStatus())) {
                    List taskPlanPublishes = this.taskPlanPublishDao.listTask(taskKey);
                    Optional<TaskPlanPublish> findStatus = taskPlanPublishes.stream().filter(e -> !ReleaseStatus.PROTECT_END.toString().equals(e.getPublishStatus()) && !ReleaseStatus.PROTECTING.toString().equals(e.getPublishStatus())).findFirst();
                    if (findStatus.isPresent()) {
                        schemeStatusDTO.setStatus(ReleaseStatus.getInstance((String)findStatus.get().getPublishStatus()));
                    } else {
                        schemeStatusDTO.setStatus(ReleaseStatus.NEVER_PUBLISH);
                    }
                } else {
                    schemeStatusDTO.setStatus(ReleaseStatus.getInstance((String)query.getPublishStatus()));
                }
                schemeStatusDTO.setPublishTime(query.getUpdateTime());
            } else {
                schemeStatusDTO.setStatus(ReleaseStatus.NEVER_PUBLISH);
            }
        }
        catch (Exception e2) {
            logger.error(e2.getMessage(), e2);
        }
        return schemeStatusDTO;
    }

    @Override
    public TaskPublishInfo queryTaskPublishInfo(String taskKey) {
        TaskPublishInfo info = new TaskPublishInfo();
        try {
            ProgressLoadingImpl impl = this.progressLoadingDao.queryProgressLoadingBytaskId(taskKey);
            if (impl != null) {
                BeanUtils.copyProperties(impl, info);
            }
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        return info;
    }

    @Override
    public void exportPublishError(String taskKey, HttpServletResponse response) {
        try {
            ProgressLoadingImpl progress = this.progressLoadingDao.queryProgressLoadingBytaskId(taskKey);
            if (progress != null) {
                DesignTaskDefine task = this.designTimeViewController.getTask(taskKey);
                String status = progress.getOperStatus() == 1 ? "\u5931\u8d25" : "\u8b66\u544a";
                FileDownload.exportTxtFile((HttpServletResponse)response, (String)progress.getStackinfos(), (String)String.format("\u53d1\u5e03%s\u8be6\u60c5-%s.txt", status, task.getTitle()));
            }
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

