/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.bi.util.OrderGenerator
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.definition.common.UUIDUtils
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.nr.common.itree.INode
 *  com.jiuqi.nr.common.itree.ITree
 *  com.jiuqi.nr.definition.auth.DefinitionAuthorityProvider
 *  com.jiuqi.nr.definition.common.FormulaSchemeType
 *  com.jiuqi.nr.definition.common.TaskType
 *  com.jiuqi.nr.definition.controller.IDesignTimeViewController
 *  com.jiuqi.nr.definition.facade.DesignTaskDefine
 *  com.jiuqi.nr.definition.facade.DesignTaskGroupDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.facade.TaskGroupDefine
 *  com.jiuqi.nr.definition.formulamapping.facade.Data
 *  com.jiuqi.nr.definition.formulamapping.facade.TreeObj
 *  com.jiuqi.nr.definition.internal.controller.NRDesignTimeController
 *  com.jiuqi.nr.definition.internal.controller.RunTimeAuthViewController
 *  com.jiuqi.nr.definition.internal.impl.DesignTaskGroupDefineImpl
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.designer.web.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.bi.util.OrderGenerator;
import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.definition.common.UUIDUtils;
import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.nr.common.itree.INode;
import com.jiuqi.nr.common.itree.ITree;
import com.jiuqi.nr.definition.auth.DefinitionAuthorityProvider;
import com.jiuqi.nr.definition.common.FormulaSchemeType;
import com.jiuqi.nr.definition.common.TaskType;
import com.jiuqi.nr.definition.controller.IDesignTimeViewController;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.definition.facade.DesignTaskGroupDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.facade.TaskGroupDefine;
import com.jiuqi.nr.definition.formulamapping.facade.Data;
import com.jiuqi.nr.definition.formulamapping.facade.TreeObj;
import com.jiuqi.nr.definition.internal.controller.NRDesignTimeController;
import com.jiuqi.nr.definition.internal.controller.RunTimeAuthViewController;
import com.jiuqi.nr.definition.internal.impl.DesignTaskGroupDefineImpl;
import com.jiuqi.nr.designer.common.NrDesingerErrorEnum;
import com.jiuqi.nr.designer.planpublish.model.TaskPlanPublishObject;
import com.jiuqi.nr.designer.planpublish.service.TaskPlanPublishExternalService;
import com.jiuqi.nr.designer.web.facade.TaskTreeObj;
import com.jiuqi.nr.designer.web.facade.UReportTask;
import com.jiuqi.nr.designer.web.facade.UReportTaskL;
import com.jiuqi.nr.designer.web.factory.DesignerFactory;
import com.jiuqi.nr.designer.web.rest.vo.ReturnObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Component
public class ReportTaskService {
    private static final Logger log = LoggerFactory.getLogger(ReportTaskService.class);
    @Resource
    private DesignerFactory dsFactory;
    @Autowired
    private DefinitionAuthorityProvider definitionAuthority;
    @Autowired
    private NRDesignTimeController nrDesignTimeController;
    @Autowired
    private RunTimeAuthViewController runTimeAuthViewController;
    @Autowired
    private TaskPlanPublishExternalService taskPlanPublishExternalService;
    @Autowired
    private IDesignTimeViewController iDesignTimeViewController;

    public String getReportTaskInfo(String taskId) throws JQException {
        ObjectMapper mapper = new ObjectMapper();
        String ret = null;
        String taskKey = taskId;
        DesignTaskDefine task = this.dsFactory.getDsCtller().queryTaskDefine(taskKey);
        try {
            ret = mapper.writeValueAsString((Object)new UReportTask(task, this.dsFactory));
        }
        catch (JsonProcessingException e) {
            log.error(e.getMessage(), e);
        }
        return ret;
    }

    public String addReportTask(String para) {
        ObjectMapper mapper = new ObjectMapper();
        String ret = null;
        try {
            UReportTask uTask = (UReportTask)mapper.readValue(para, UReportTask.class);
            uTask.setKey(UUIDUtils.getKey());
            this.dsFactory.getDsCtller().insertTaskDefine(uTask.toDBObject(this.dsFactory));
            ret = "success";
        }
        catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return ret;
    }

    public String editReportTask(String para) throws JQException {
        ObjectMapper mapper = new ObjectMapper();
        String ret = null;
        try {
            UReportTask uTask = (UReportTask)mapper.readValue(para, UReportTask.class);
            this.dsFactory.getDsCtller().updateTaskDefine(uTask.toDBObject(this.dsFactory));
            ret = "{\"retState\":\"success\"}";
        }
        catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return ret;
    }

    public void deleteReportTask(String taskId, boolean delLinkedParam) throws JQException {
        String taskKey = taskId;
        try {
            this.dsFactory.getDsCtller().deleteTaskDefine(taskKey, delLinkedParam);
            this.dsFactory.getDeployCtller().deployTask(taskKey);
        }
        catch (JQException e) {
            throw e;
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_013, (Throwable)e);
        }
    }

    public List<UReportTaskL> getAllReportTasks() throws Exception {
        ArrayList<UReportTaskL> canReadTaskList = new ArrayList<UReportTaskL>();
        List taskList = this.nrDesignTimeController.getAllReportTaskDefines();
        taskList.addAll(this.nrDesignTimeController.getAllTaskDefinesByType(TaskType.TASK_TYPE_SURVEY));
        taskList.stream().sorted(Comparator.comparing(IBaseMetaItem::getOrder).reversed()).forEach(task -> {
            if (this.definitionAuthority.canModeling(task.getKey())) {
                UReportTaskL uTask = new UReportTaskL();
                uTask.setKey(task.getKey());
                uTask.setCode(task.getTaskCode());
                uTask.setTitle(task.getTitle());
                uTask.setCanDesign(this.definitionAuthority.canModeling(task.getKey()));
                uTask.setCreateUserName(task.getCreateUserName());
                uTask.setCreateTime(task.getCreateTime());
                try {
                    TaskPlanPublishObject taskPlanPublishObject = this.taskPlanPublishExternalService.queryPlanPublishOfTask(task.getKey());
                    uTask.setTaskPlanPublishObject(taskPlanPublishObject);
                }
                catch (Exception e) {
                    throw new RuntimeException(e);
                }
                uTask.setType(task.getTaskType().toString());
                canReadTaskList.add(uTask);
            }
        });
        return canReadTaskList;
    }

    public List<UReportTaskL> getReportTasksOfEditor() throws Exception {
        ArrayList<UReportTaskL> canReadTaskList = new ArrayList<UReportTaskL>();
        List taskList = this.nrDesignTimeController.getAllReportTaskDefines();
        taskList.addAll(this.nrDesignTimeController.getAllTaskDefinesByType(TaskType.TASK_TYPE_SURVEY));
        taskList.addAll(this.nrDesignTimeController.getAllTaskDefinesByType(TaskType.TASK_TYPE_ANALYSIS));
        taskList.stream().sorted(Comparator.comparing(IBaseMetaItem::getOrder).reversed()).forEach(task -> {
            if (this.definitionAuthority.canReadTask(task.getKey())) {
                UReportTaskL uTask = new UReportTaskL();
                uTask.setKey(task.getKey());
                uTask.setCode(task.getTaskCode());
                uTask.setTitle(task.getTitle());
                uTask.setCanDesign(this.definitionAuthority.canModeling(task.getKey()));
                uTask.setCreateUserName(task.getCreateUserName());
                uTask.setCreateTime(task.getCreateTime());
                try {
                    uTask.setTaskPlanPublishObject(this.taskPlanPublishExternalService.queryPlanPublishOfTask(task.getKey()));
                }
                catch (Exception e) {
                    throw new RuntimeException(e);
                }
                uTask.setType(task.getTaskType().toString());
                canReadTaskList.add(uTask);
            }
        });
        return canReadTaskList;
    }

    public List<UReportTaskL> getReportTasksByFormulaType(int formulaType) {
        ArrayList<UReportTaskL> canReadTaskList = new ArrayList<UReportTaskL>();
        List taskList = this.nrDesignTimeController.getAllReportTaskDefines();
        taskList.addAll(this.nrDesignTimeController.getAllTaskDefinesByType(TaskType.TASK_TYPE_SURVEY));
        taskList.stream().sorted(Comparator.comparing(IBaseMetaItem::getOrder).reversed()).forEach(task -> {
            if (this.definitionAuthority.canReadTask(task.getKey())) {
                UReportTaskL uTask = new UReportTaskL();
                uTask.setKey(task.getKey());
                uTask.setCode(task.getTaskCode());
                uTask.setTitle(task.getTitle());
                uTask.setCanDesign(this.definitionAuthority.canModeling(task.getKey()));
                uTask.setCreateUserName(task.getCreateUserName());
                uTask.setCreateTime(task.getCreateTime());
                try {
                    uTask.setTaskPlanPublishObject(this.taskPlanPublishExternalService.queryPlanPublishOfTask(task.getKey()));
                }
                catch (Exception e) {
                    throw new RuntimeException(e);
                }
                uTask.setType(task.getTaskType().toString());
                if (formulaType == FormulaSchemeType.FORMULA_SCHEME_TYPE_FINANCIAL.getValue()) {
                    if (task.getEfdcSwitch()) {
                        canReadTaskList.add(uTask);
                    }
                } else {
                    canReadTaskList.add(uTask);
                }
            }
        });
        return canReadTaskList;
    }

    public String getRunTimeAllReportTasks() {
        ObjectMapper mapper = new ObjectMapper();
        String ret = null;
        List taskList = this.runTimeAuthViewController.getAllTaskDefines();
        ArrayList<UReportTaskL> taskList2 = new ArrayList<UReportTaskL>();
        for (TaskDefine task : taskList) {
            UReportTaskL one = new UReportTaskL();
            one.setKey(task.getKey());
            one.setCode(task.getTaskCode());
            one.setTitle(task.getTitle());
            one.setCanDesign(this.definitionAuthority.canModeling(task.getKey()));
            one.setType(task.getTaskType().toString());
            one.setCreateTime(task.getCreateTime());
            one.setCreateUserName(task.getCreateUserName());
            taskList2.add(one);
        }
        try {
            ret = mapper.writeValueAsString(taskList2);
        }
        catch (JsonProcessingException e) {
            log.error(e.getMessage(), e);
        }
        return ret;
    }

    public String getAllTaskGroupArr() {
        ObjectMapper mapper = new ObjectMapper();
        String ret = null;
        List taskGroupDefines = this.nrDesignTimeController.getAllTaskGroup();
        ArrayList<UReportTaskL> taskGroupList = new ArrayList<UReportTaskL>();
        UReportTaskL taskAll = new UReportTaskL();
        taskAll.setKey("");
        taskAll.setTitle("\u5168\u90e8\u4efb\u52a1");
        taskGroupList.add(taskAll);
        HashMap<String, Boolean> authorityMap = new HashMap<String, Boolean>();
        for (DesignTaskGroupDefine taskGroup : taskGroupDefines) {
            Boolean parentGroupAuth;
            Boolean authorityForGroup = StringUtils.hasText(taskGroup.getParentKey()) ? ((parentGroupAuth = (Boolean)authorityMap.get(taskGroup.getParentKey())) != null && !parentGroupAuth.booleanValue() ? Boolean.valueOf(false) : Boolean.valueOf(this.definitionAuthority.canTaskGroupModeling(taskGroup.getKey()))) : Boolean.valueOf(this.definitionAuthority.canTaskGroupModeling(taskGroup.getKey()));
            authorityMap.put(taskGroup.getKey(), authorityForGroup);
            if (!authorityForGroup.booleanValue()) continue;
            UReportTaskL taskArr = new UReportTaskL();
            taskArr.setKey(taskGroup.getKey());
            taskArr.setTitle(taskGroup.getTitle());
            taskGroupList.add(taskArr);
        }
        try {
            ret = mapper.writeValueAsString(taskGroupList);
        }
        catch (JsonProcessingException e) {
            log.error(e.getMessage(), e);
        }
        return ret;
    }

    public List<ITree<TaskTreeObj>> getChildTaskTreeList(String taskGroupKey) throws Exception {
        DesignTaskGroupDefine srcDesignTaskGroupDefine = this.nrDesignTimeController.queryTaskGroupDefine(taskGroupKey);
        DesignTaskGroupDefineImpl designTaskGroupDefine = new DesignTaskGroupDefineImpl();
        designTaskGroupDefine.setParentKey(null);
        designTaskGroupDefine.setTitle(srcDesignTaskGroupDefine.getTitle());
        designTaskGroupDefine.setKey(srcDesignTaskGroupDefine.getKey());
        ArrayList<DesignTaskGroupDefine> taskGroupDefines = new ArrayList<DesignTaskGroupDefine>();
        this.setChildTaskTreeList(taskGroupDefines, (DesignTaskGroupDefine)designTaskGroupDefine);
        ArrayList<ITree<TaskTreeObj>> tree_Task = new ArrayList<ITree<TaskTreeObj>>();
        if (taskGroupDefines.size() > 0) {
            taskGroupDefines.forEach(taskGroupDefine -> {
                if (this.definitionAuthority.canReadTaskGroup(taskGroupDefine.getKey())) {
                    tree_Task.add(this.getGroupTreeNode((DesignTaskGroupDefine)taskGroupDefine, false, false));
                }
            });
        }
        return tree_Task;
    }

    public void setChildTaskTreeList(List<DesignTaskGroupDefine> taskGroupDefines, DesignTaskGroupDefine designTaskGroupDefine) throws Exception {
        taskGroupDefines.add(designTaskGroupDefine);
        List list = this.nrDesignTimeController.getChildTaskGroups(designTaskGroupDefine.getKey(), false);
        if (!CollectionUtils.isEmpty(list)) {
            for (DesignTaskGroupDefine designTaskGroup : list) {
                this.setChildTaskTreeList(taskGroupDefines, designTaskGroup);
            }
        }
    }

    public List<ITree<TaskTreeObj>> getTaskTreeList() {
        List allTaskGroup = this.nrDesignTimeController.getAllTaskGroup();
        ArrayList<ITree<TaskTreeObj>> tree_Task = new ArrayList<ITree<TaskTreeObj>>();
        ArrayList<DesignTaskGroupDefine> defines = new ArrayList<DesignTaskGroupDefine>();
        HashMap<String, Boolean> authorityMap = new HashMap<String, Boolean>();
        for (DesignTaskGroupDefine taskGroup : allTaskGroup) {
            boolean canDesignForGroup;
            boolean authorityForGroup;
            if (StringUtils.hasText(taskGroup.getParentKey())) {
                Boolean parentGroupAuth = (Boolean)authorityMap.get(taskGroup.getParentKey());
                Boolean parentGroupAuthDes = (Boolean)authorityMap.get(taskGroup.getParentKey() + "_des");
                authorityForGroup = parentGroupAuth != null && parentGroupAuth == false ? false : this.definitionAuthority.canTaskGroupModeling(taskGroup.getKey());
                canDesignForGroup = parentGroupAuthDes != null && !parentGroupAuthDes.booleanValue() ? false : this.definitionAuthority.canTaskGroupModeling(taskGroup.getKey());
            } else {
                authorityForGroup = this.definitionAuthority.canTaskGroupModeling(taskGroup.getKey());
                canDesignForGroup = this.definitionAuthority.canTaskGroupModeling(taskGroup.getKey());
            }
            authorityMap.put(taskGroup.getKey(), authorityForGroup);
            authorityMap.put(taskGroup.getKey() + "_des", canDesignForGroup);
            if (!authorityForGroup) continue;
            defines.add(taskGroup);
        }
        if (defines.size() > 0) {
            defines.forEach(taskGroupDefine -> tree_Task.add(this.getGroupTreeNode((DesignTaskGroupDefine)taskGroupDefine, false, false, (Map<String, Boolean>)authorityMap)));
        }
        return tree_Task;
    }

    public void taskTreeListReload(String groupKey, ReturnObject ro) {
        List defines = this.nrDesignTimeController.getAllTaskGroup();
        ArrayList tree_Task = new ArrayList();
        if (defines.size() > 0) {
            defines.forEach(taskGroupDefine -> {
                if (this.definitionAuthority.canReadTaskGroup(taskGroupDefine.getKey())) {
                    if (taskGroupDefine.getKey().equals(groupKey)) {
                        tree_Task.add(this.getGroupTreeNode((DesignTaskGroupDefine)taskGroupDefine, false, true));
                    } else {
                        tree_Task.add(this.getGroupTreeNode((DesignTaskGroupDefine)taskGroupDefine, false, false));
                    }
                }
            });
        }
        ro.setObj(tree_Task);
    }

    public List<ITree<TaskTreeObj>> getTaskTreeListWithTaskCheck(String taskKey) {
        List taskGroupList = this.nrDesignTimeController.getGroupByTask(taskKey);
        List defines = this.nrDesignTimeController.getAllTaskGroup();
        ArrayList<ITree<TaskTreeObj>> tree_Task = new ArrayList<ITree<TaskTreeObj>>();
        if (defines.size() > 0 && taskGroupList.size() > 0) {
            LinkedList<String> taskGroupKeys = new LinkedList<String>();
            for (DesignTaskGroupDefine taskGroup : taskGroupList) {
                taskGroupKeys.add(taskGroup.getKey());
            }
            for (DesignTaskGroupDefine taskGroupDefine : defines) {
                if (!this.definitionAuthority.canReadTaskGroup(taskGroupDefine.getKey())) continue;
                if (taskGroupKeys.contains(taskGroupDefine.getKey())) {
                    tree_Task.add(this.getGroupTreeNode(taskGroupDefine, true, false));
                    continue;
                }
                tree_Task.add(this.getGroupTreeNode(taskGroupDefine, false, false));
            }
            return tree_Task;
        }
        return this.getTaskTreeList();
    }

    private ITree<TaskTreeObj> getGroupTreeNode(DesignTaskGroupDefine taskGroupDefine, boolean isChecked, boolean isExpanded, Map<String, Boolean> authMap) {
        ITree node = new ITree((INode)new TaskTreeObj(taskGroupDefine, authMap.get(taskGroupDefine.getKey() + "_des")));
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

    public List<UReportTaskL> getTaskListForGroup(String taskGroupKey) throws Exception {
        List taskList = this.nrDesignTimeController.getAllTasksInGroup(taskGroupKey, false);
        taskList = taskList.stream().filter(t -> t.getTaskType() != TaskType.TASK_TYPE_ANALYSIS).sorted(Comparator.comparing(IBaseMetaItem::getOrder).reversed()).collect(Collectors.toList());
        ArrayList<UReportTaskL> canReadTaskList = new ArrayList<UReportTaskL>();
        this.taskToUReportTaskL(canReadTaskList, taskList);
        return canReadTaskList;
    }

    public List<UReportTaskL> getAllTaskListForGroup(String taskGroupKey) throws Exception {
        List<Object> taskList = new ArrayList();
        List taskGroupDefines = this.nrDesignTimeController.getChildTaskGroups(taskGroupKey, true);
        for (DesignTaskGroupDefine taskGroup : taskGroupDefines) {
            taskList.addAll(this.nrDesignTimeController.getAllTasksInGroup(taskGroup.getKey(), false));
        }
        taskList = taskList.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<DesignTaskDefine>(Comparator.comparing(p -> p.getKey()))), ArrayList::new));
        taskList = taskList.stream().filter(t -> t.getTaskType() != TaskType.TASK_TYPE_ANALYSIS).sorted(Comparator.comparing(IBaseMetaItem::getOrder).reversed()).collect(Collectors.toList());
        ArrayList<UReportTaskL> canReadTaskList = new ArrayList<UReportTaskL>();
        this.taskToUReportTaskL(canReadTaskList, taskList);
        return canReadTaskList;
    }

    public List<UReportTaskL> getAllRunTaskListForGroup(String taskGroupKey) throws Exception {
        List<Object> taskList = new ArrayList();
        List taskGroupDefines = this.runTimeAuthViewController.getChildTaskGroups(taskGroupKey, true);
        for (TaskGroupDefine taskGroup : taskGroupDefines) {
            taskList.addAll(this.runTimeAuthViewController.getTaskDefinesFromGroup(taskGroup.getKey()));
        }
        taskList = taskList.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<TaskDefine>(Comparator.comparing(p -> p.getKey()))), ArrayList::new));
        taskList = taskList.stream().filter(t -> t.getTaskType() != TaskType.TASK_TYPE_ANALYSIS).sorted(Comparator.comparing(IBaseMetaItem::getOrder).reversed()).collect(Collectors.toList());
        ArrayList<UReportTaskL> canReadTaskList = new ArrayList<UReportTaskL>();
        this.taskToUReportTaskL(canReadTaskList, taskList);
        return canReadTaskList;
    }

    public List<UReportTaskL> getRunTaskListForGroup(String taskGroupKey) throws Exception {
        List allRunTaskList = this.runTimeAuthViewController.getTaskDefinesFromGroup(taskGroupKey);
        allRunTaskList = allRunTaskList.stream().filter(t -> t.getTaskType() != TaskType.TASK_TYPE_ANALYSIS).sorted(Comparator.comparing(IBaseMetaItem::getOrder).reversed()).collect(Collectors.toList());
        ArrayList<UReportTaskL> canReadTaskList = new ArrayList<UReportTaskL>();
        this.taskToUReportTaskR(canReadTaskList, allRunTaskList);
        return canReadTaskList;
    }

    public <T extends TaskDefine> void taskToUReportTaskL(List<UReportTaskL> canReadTaskList, List<T> listT) throws Exception {
        for (TaskDefine task : listT) {
            if (!this.definitionAuthority.canModeling(task.getKey())) continue;
            UReportTaskL one = new UReportTaskL();
            one.setKey(task.getKey());
            one.setCode(task.getTaskCode());
            one.setTitle(task.getTitle());
            one.setCanDesign(this.definitionAuthority.canModeling(task.getKey()));
            one.setCreateUserName(task.getCreateUserName());
            one.setCreateTime(task.getCreateTime());
            one.setTaskPlanPublishObject(this.taskPlanPublishExternalService.queryPlanPublishOfTask(task.getKey()));
            one.setType(task.getTaskType().toString());
            canReadTaskList.add(one);
        }
    }

    public <T extends TaskDefine> void taskToUReportTaskR(List<UReportTaskL> canReadTaskList, List<T> listT) throws Exception {
        for (TaskDefine task : listT) {
            if (!this.definitionAuthority.canReadTask(task.getKey())) continue;
            UReportTaskL one = new UReportTaskL();
            one.setKey(task.getKey());
            one.setCode(task.getTaskCode());
            one.setTitle(task.getTitle());
            one.setCanDesign(this.definitionAuthority.canModeling(task.getKey()));
            one.setCreateUserName(task.getCreateUserName());
            one.setCreateTime(task.getCreateTime());
            one.setTaskPlanPublishObject(this.taskPlanPublishExternalService.queryPlanPublishOfTask(task.getKey()));
            one.setType(task.getTaskType().toString());
            canReadTaskList.add(one);
        }
    }

    public void setGroupForTask(String taskID, List<String> groupsArr) {
        List taskGroupList = this.nrDesignTimeController.getGroupByTask(taskID);
        LinkedHashMap<String, DesignTaskGroupDefine> oldmap = new LinkedHashMap<String, DesignTaskGroupDefine>();
        LinkedList<String> sameItems = new LinkedList<String>();
        LinkedList onlyAItems = new LinkedList();
        LinkedList<String> onlyBItems = new LinkedList<String>();
        for (DesignTaskGroupDefine designTaskGroupDefine : taskGroupList) {
            oldmap.put(designTaskGroupDefine.getKey(), designTaskGroupDefine);
        }
        for (int i = 0; i < groupsArr.size(); ++i) {
            String string = groupsArr.get(i);
            if (oldmap.containsKey(string)) {
                sameItems.add(string);
                continue;
            }
            onlyBItems.add(string);
        }
        for (String string : sameItems) {
            oldmap.remove(string);
        }
        onlyAItems.addAll(oldmap.values());
        if (onlyAItems.size() > 0 && onlyAItems != null) {
            List onlyAItemsKey = onlyAItems.stream().map(IBaseMetaItem::getKey).collect(Collectors.toList());
            for (DesignTaskGroupDefine taskGroupDefine : onlyAItems) {
                this.nrDesignTimeController.removeTaskFromGroup(taskID, taskGroupDefine.getKey());
            }
        }
        if (onlyBItems.size() > 0 && onlyBItems != null) {
            for (String string : onlyBItems) {
                this.nrDesignTimeController.addTaskToGroup(taskID, string);
            }
        }
    }

    public ReturnObject insertTaskGroup(String groupTitle, String parentKey, String groupDescription, ReturnObject ro) {
        if (!StringUtils.hasText(groupTitle = groupTitle.trim())) {
            ro.setMessage("\u65b0\u589e\u5931\u8d25\uff0c\u5206\u7ec4\u540d\u79f0\u4e0d\u80fd\u4e3a\u7a7a\uff01");
            ro.setSuccess(false);
            return ro;
        }
        List taskGroupList = this.nrDesignTimeController.getAllTaskGroup();
        HashSet<String> taskGroupTitle = this.getGroupTitles(taskGroupList, parentKey);
        if (taskGroupTitle.add(groupTitle)) {
            DesignTaskGroupDefine taskGroupDefine = this.nrDesignTimeController.createTaskGroup();
            taskGroupDefine.setKey(UUIDUtils.getKey());
            taskGroupDefine.setTitle(groupTitle);
            taskGroupDefine.setDescription(groupDescription);
            taskGroupDefine.setParentKey(parentKey);
            taskGroupDefine.setOrder(OrderGenerator.newOrder());
            this.nrDesignTimeController.insertTaskGroupDefine(taskGroupDefine);
            ro.setSuccess(true);
            ro.setMessage("\u6dfb\u52a0\u6210\u529f");
            ro.setObj(taskGroupDefine);
            String identityId = NpContextHolder.getContext().getIdentityId();
            if (identityId != null) {
                this.definitionAuthority.grantAllPrivilegesToTaskGroup(taskGroupDefine.getKey());
            }
        } else {
            ro.setMessage("\u6dfb\u52a0\u5931\u8d25,\u5206\u7ec4\u540d\u79f0\u91cd\u590d\uff01");
            ro.setSuccess(false);
        }
        return ro;
    }

    private HashSet<String> getGroupTitles(List<DesignTaskGroupDefine> taskGroupList, String parentKey) {
        HashSet<String> taskGroupTitle = new HashSet<String>();
        for (DesignTaskGroupDefine designTaskGroupDefine : taskGroupList) {
            if (StringUtils.hasText(parentKey)) {
                if (!parentKey.equals(designTaskGroupDefine.getParentKey())) continue;
                taskGroupTitle.add(designTaskGroupDefine.getTitle());
                continue;
            }
            if (StringUtils.hasText(designTaskGroupDefine.getParentKey())) continue;
            taskGroupTitle.add(designTaskGroupDefine.getTitle());
        }
        return taskGroupTitle;
    }

    public ReturnObject updateTaskGroup(String groupKey, String groupTitle, String parentKey, String groupDescription, ReturnObject ro) throws JQException {
        if (!StringUtils.hasText(groupTitle = groupTitle.trim())) {
            ro.setMessage("\u66f4\u65b0\u5931\u8d25,\u5206\u7ec4\u540d\u79f0\u4e0d\u80fd\u4e3a\u7a7a\uff01");
            ro.setSuccess(false);
            return ro;
        }
        List<DesignTaskGroupDefine> taskGroupList = this.nrDesignTimeController.getAllTaskGroup();
        HashSet<String> taskGroupTitle = this.getGroupTitles(taskGroupList = taskGroupList.stream().filter(a -> !a.getKey().equals(groupKey)).collect(Collectors.toList()), parentKey);
        if (taskGroupTitle.add(groupTitle)) {
            DesignTaskGroupDefine taskGroupDefine = this.nrDesignTimeController.queryTaskGroupDefine(groupKey);
            taskGroupDefine.setTitle(groupTitle);
            taskGroupDefine.setDescription(groupDescription);
            taskGroupDefine.setUpdateTime(new Date());
            taskGroupDefine.setParentKey(parentKey);
            this.nrDesignTimeController.updateTaskGroupDefine(taskGroupDefine);
            ro.setSuccess(true);
            ro.setMessage("\u66f4\u65b0\u6210\u529f");
            ro.setObj(taskGroupDefine);
        } else {
            ro.setMessage("\u66f4\u65b0\u5931\u8d25,\u5206\u7ec4\u540d\u79f0\u91cd\u590d\uff01");
            ro.setSuccess(false);
        }
        return ro;
    }

    public void deleteTaskGroup(String taskGroupKey) throws JQException {
        List taskGroupDefineChild = this.nrDesignTimeController.getChildTaskGroups(taskGroupKey, false);
        if (taskGroupDefineChild.size() > 0) {
            throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_213);
        }
        List taskList = this.nrDesignTimeController.getAllTasksInGroup(taskGroupKey, false);
        if (taskList.size() > 0) {
            throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_213);
        }
        this.nrDesignTimeController.deleteTaskGroupDefine(taskGroupKey);
    }

    public List<DesignTaskGroupDefine> getTaskGroupByTask(String taskKey) throws JQException {
        return this.nrDesignTimeController.getGroupByTask(taskKey);
    }

    public List<TreeObj> getEditTaskTree() {
        TreeObj allNode = this.createTaskGroupNodeNone();
        List rootGroup = this.nrDesignTimeController.getChildTaskGroups(null, false);
        List<TreeObj> recursion = this.recursion(rootGroup);
        List allTaskDefines = this.nrDesignTimeController.getAllTaskDefines();
        List collect = this.iDesignTimeViewController.getGroupLink().stream().map(e -> e.getTaskKey()).collect(Collectors.toList());
        for (DesignTaskDefine taskDefine : allTaskDefines) {
            if (!this.definitionAuthority.canReadTask(taskDefine.getKey()) || collect.contains(taskDefine.getKey())) continue;
            TreeObj taskNode = this.createTaskNode((TaskDefine)taskDefine, null);
            recursion.add(taskNode);
        }
        allNode.setChildren(recursion);
        ArrayList<TreeObj> tree = new ArrayList<TreeObj>();
        tree.add(allNode);
        return tree;
    }

    public List<TreeObj> getRuntimeEditTaskTree() {
        TreeObj allNode = this.createTaskGroupNodeNone();
        List rootGroup = this.nrDesignTimeController.getChildTaskGroups(null, false);
        List<TreeObj> recursion = this.recursionRuntime(rootGroup);
        List allTaskDefines = this.runTimeAuthViewController.getAllTaskDefines();
        List collect = this.iDesignTimeViewController.getGroupLink().stream().map(e -> e.getTaskKey()).collect(Collectors.toList());
        for (TaskDefine taskDefine : allTaskDefines) {
            if (collect.contains(taskDefine.getKey())) continue;
            TreeObj taskNode = this.createTaskNode(taskDefine, null);
            recursion.add(taskNode);
        }
        allNode.setChildren(recursion);
        ArrayList<TreeObj> tree = new ArrayList<TreeObj>();
        tree.add(allNode);
        return tree;
    }

    public List<TreeObj> recursionRuntime(List<DesignTaskGroupDefine> designTaskGroupDefines) {
        ArrayList<TreeObj> treeObj = new ArrayList<TreeObj>();
        for (DesignTaskGroupDefine designTaskGroupDefine : designTaskGroupDefines) {
            if (!this.definitionAuthority.canReadTaskGroup(designTaskGroupDefine.getKey())) continue;
            TreeObj taskGroupNode = this.createTaskGroupNode((TaskGroupDefine)designTaskGroupDefine);
            treeObj.add(taskGroupNode);
            List childTaskGroups = this.nrDesignTimeController.getChildTaskGroups(designTaskGroupDefine.getKey(), false);
            if (childTaskGroups.size() > 0) {
                List<TreeObj> recursion = this.recursionRuntime(childTaskGroups);
                taskGroupNode.getChildren().addAll(recursion);
            }
            List allTasksInGroup = this.runTimeAuthViewController.getTaskDefinesFromGroup(designTaskGroupDefine.getKey());
            for (TaskDefine taskData : allTasksInGroup) {
                TreeObj taskNode = this.createTaskNode(taskData, designTaskGroupDefine.getKey());
                taskGroupNode.getChildren().add(taskNode);
            }
        }
        return treeObj;
    }

    public List<TreeObj> recursion(List<DesignTaskGroupDefine> designTaskGroupDefines) {
        ArrayList<TreeObj> treeObj = new ArrayList<TreeObj>();
        for (DesignTaskGroupDefine designTaskGroupDefine : designTaskGroupDefines) {
            if (!this.definitionAuthority.canReadTaskGroup(designTaskGroupDefine.getKey())) continue;
            TreeObj taskGroupNode = this.createTaskGroupNode((TaskGroupDefine)designTaskGroupDefine);
            treeObj.add(taskGroupNode);
            List childTaskGroups = this.nrDesignTimeController.getChildTaskGroups(designTaskGroupDefine.getKey(), false);
            if (childTaskGroups.size() > 0) {
                List<TreeObj> recursion = this.recursion(childTaskGroups);
                taskGroupNode.getChildren().addAll(recursion);
            }
            List allTasksInGroup = this.nrDesignTimeController.getAllTasksInGroup(designTaskGroupDefine.getKey(), false);
            allTasksInGroup = allTasksInGroup.stream().filter(t -> t.getTaskType() != TaskType.TASK_TYPE_ANALYSIS).sorted(Comparator.comparing(IBaseMetaItem::getOrder).reversed()).collect(Collectors.toList());
            for (DesignTaskDefine designTaskDefine : allTasksInGroup) {
                if (!this.definitionAuthority.canReadTask(designTaskDefine.getKey())) continue;
                TreeObj taskNode = this.createTaskNode((TaskDefine)designTaskDefine, designTaskGroupDefine.getKey());
                taskGroupNode.getChildren().add(taskNode);
            }
        }
        return treeObj;
    }

    private TreeObj createTaskNode(TaskDefine taskDefine, String parent) {
        TreeObj obj = new TreeObj();
        obj.setId(taskDefine.getKey());
        obj.setTitle(taskDefine.getTitle());
        obj.setCode(taskDefine.getTaskCode());
        obj.setParentid(parent);
        obj.setIsLeaf(Boolean.valueOf(true));
        obj.setIcons("task");
        obj.setChildren(new ArrayList());
        Data data = new Data();
        data.setKey(taskDefine.getKey());
        data.setCode(taskDefine.getTaskCode());
        data.setParentKey(parent);
        data.setIcon("task");
        data.setTitle(taskDefine.getTitle());
        obj.setData(data);
        return obj;
    }

    private TreeObj createTaskGroupNodeNone() {
        TreeObj obj = new TreeObj();
        obj.setId("allTask");
        obj.setTitle("\u5168\u90e8\u4efb\u52a1");
        obj.setCode("allTask");
        obj.setParentid(null);
        obj.setIcons("group");
        obj.setChildren(new ArrayList());
        obj.setOnlyChildNodes(true);
        Data data = new Data();
        data.setKey("allTask");
        data.setCode("allTask");
        data.setParentKey(null);
        data.setIcon("group");
        data.setTitle("\u5168\u90e8\u4efb\u52a1");
        obj.setData(data);
        return obj;
    }

    private TreeObj createTaskGroupNode(TaskGroupDefine taskGroupDefine) {
        TreeObj obj = new TreeObj();
        obj.setId(taskGroupDefine.getKey());
        obj.setTitle(taskGroupDefine.getTitle());
        obj.setCode(taskGroupDefine.getCode());
        obj.setParentid(taskGroupDefine.getParentKey());
        obj.setIcons("group");
        obj.setChildren(new ArrayList());
        obj.setOnlyChildNodes(true);
        Data data = new Data();
        data.setKey(taskGroupDefine.getKey());
        data.setCode(taskGroupDefine.getCode());
        data.setParentKey(taskGroupDefine.getParentKey());
        data.setIcon("group");
        data.setTitle(taskGroupDefine.getTitle());
        obj.setData(data);
        return obj;
    }

    public void changeTaskGroupOrder(String source, int way) throws JQException {
        DesignTaskGroupDefine targetGroup;
        DesignTaskGroupDefine sourceGroup = this.nrDesignTimeController.queryTaskGroupDefine(source);
        String sourceParentKey = sourceGroup.getParentKey();
        List childTaskGroups = this.nrDesignTimeController.getChildTaskGroups(sourceParentKey, false);
        int currentIndex = -1;
        for (int i = 0; i < childTaskGroups.size(); ++i) {
            DesignTaskGroupDefine groupDefine = (DesignTaskGroupDefine)childTaskGroups.get(i);
            if (!groupDefine.getKey().equals(sourceGroup.getKey())) continue;
            currentIndex = i;
            break;
        }
        if (way > 0) {
            if (currentIndex == 0) {
                throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_216);
            }
            targetGroup = (DesignTaskGroupDefine)childTaskGroups.get(currentIndex - 1);
        } else {
            if (currentIndex == childTaskGroups.size() - 1) {
                throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_217);
            }
            targetGroup = (DesignTaskGroupDefine)childTaskGroups.get(currentIndex + 1);
        }
        if (targetGroup == null) {
            throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_218);
        }
        this.nrDesignTimeController.exchangeTaskGroup(sourceGroup.getKey(), targetGroup.getKey());
    }
}

