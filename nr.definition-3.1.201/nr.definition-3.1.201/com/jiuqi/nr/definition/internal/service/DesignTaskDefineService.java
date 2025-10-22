/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.OrderGenerator
 *  com.jiuqi.np.definition.common.UUIDUtils
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 */
package com.jiuqi.nr.definition.internal.service;

import com.jiuqi.bi.util.OrderGenerator;
import com.jiuqi.np.definition.common.UUIDUtils;
import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.nr.definition.common.TaskType;
import com.jiuqi.nr.definition.config.EnableTask2Config;
import com.jiuqi.nr.definition.config.ParamMaxNumberConfig;
import com.jiuqi.nr.definition.event.ParamChangeEvent;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.definition.facade.DesignTaskGroupDefine;
import com.jiuqi.nr.definition.internal.dao.DesignTaskDefineDao;
import com.jiuqi.nr.definition.internal.dao.DesignTaskGroupDefineDao;
import com.jiuqi.nr.definition.internal.dao.DesignTaskGroupLinkDao;
import com.jiuqi.nr.definition.internal.impl.DesignTaskGroupLink;
import com.jiuqi.nr.definition.internal.service.AbstractParamService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class DesignTaskDefineService
extends AbstractParamService {
    @Autowired
    private DesignTaskDefineDao taskDao;
    @Autowired
    private DesignTaskGroupDefineDao taskGroupDao;
    @Autowired
    private DesignTaskGroupLinkDao groupLinkDao;
    @Autowired
    private ParamMaxNumberConfig paramMaxNumberConfig;
    @Autowired
    private EnableTask2Config enableTask2Config;

    public String insertTaskDefine(DesignTaskDefine define) throws Exception {
        int count;
        int maxNumber;
        if (define.getKey() == null) {
            define.setKey(UUIDUtils.getKey());
        }
        if (define.getUpdateTime() == null) {
            define.setUpdateTime(new Date());
        }
        if (!StringUtils.hasText(define.getVersion())) {
            if (this.enableTask2Config.createNewVersion()) {
                define.setVersion("2.0");
            } else {
                define.setVersion("1.0");
            }
        }
        if ("2.0".equals(define.getVersion())) {
            define.setEfdcSwitch(true);
        }
        if ((maxNumber = this.paramMaxNumberConfig.getTaskMaxNumber()) != 0 && (count = this.count()) >= maxNumber) {
            throw new RuntimeException("\u8d85\u8fc7\u4efb\u52a1\u4e0a\u9650\uff0c\u6700\u5927:" + maxNumber);
        }
        this.taskDao.insert(define);
        this.onTaskChanged(ParamChangeEvent.ChangeType.ADD, define, null);
        return define.getKey();
    }

    public int count() {
        return this.taskDao.count(null);
    }

    public void updateTaskDefine(DesignTaskDefine define) throws Exception {
        if (define.getUpdateTime() == null) {
            define.setUpdateTime(new Date());
        }
        DesignTaskDefine oldDefine = this.taskDao.getDefineByKey(define.getKey());
        this.taskDao.update(define);
        this.onTaskChanged(ParamChangeEvent.ChangeType.UPDATE, define, oldDefine);
    }

    public void updateTaskExpression(DesignTaskDefine define) throws Exception {
        DesignTaskDefine oldDefine = this.taskDao.getDefineByKey(define.getKey());
        this.taskDao.updateDwExpression(define);
        this.onTaskChanged(ParamChangeEvent.ChangeType.UPDATE, define, oldDefine);
    }

    public List<DesignTaskDefine> queryTaskDefineByGroupId(String id) throws Exception {
        return this.taskDao.listByGroup(id);
    }

    public void delete(String key) throws Exception {
        DesignTaskDefine define = this.taskDao.getDefineByKey(key);
        if (null != define) {
            this.taskDao.delete(key);
            this.onTaskChanged(ParamChangeEvent.ChangeType.DELETE, null, define);
        }
        this.groupLinkDao.deleteLinkByTask(key);
    }

    public DesignTaskDefine queryTaskDefinesByCode(String code) throws Exception {
        return this.taskDao.queryTaskDefinesByCode(code);
    }

    public List<DesignTaskDefine> queryTaskDefines(String[] keys) throws Exception {
        ArrayList<DesignTaskDefine> list = new ArrayList<DesignTaskDefine>();
        for (String key : keys) {
            DesignTaskDefine define = this.taskDao.getDefineByKey(key);
            if (define == null) continue;
            list.add(define);
        }
        return list;
    }

    public List<DesignTaskDefine> getAllTasksInGroup(String taskGroupKey, boolean isRecursion) throws Exception {
        List<DesignTaskDefine> list = this.getAllTasksInGroupOrder(taskGroupKey);
        if (isRecursion) {
            List<DesignTaskGroupDefine> groupList = this.queryTaskGroupDefineByGroupId(taskGroupKey, isRecursion);
            for (DesignTaskGroupDefine group : groupList) {
                List<DesignTaskDefine> list2 = this.getAllTasksInGroupOrder(taskGroupKey);
                list.addAll(list2);
            }
        }
        list.sort(Comparator.comparing(IBaseMetaItem::getOrder));
        return list;
    }

    private List<DesignTaskGroupDefine> queryTaskGroupDefineByGroupId(String id, boolean isRecursion) throws Exception {
        List<DesignTaskGroupDefine> list = this.taskGroupDao.listByGroup(id);
        list.sort(Comparator.comparing(IBaseMetaItem::getOrder));
        if (isRecursion) {
            for (DesignTaskGroupDefine define : list) {
                this.getChildGroup(define.getKey(), list);
            }
        }
        return list;
    }

    private void getChildGroup(String id, List<DesignTaskGroupDefine> list) throws Exception {
        List<DesignTaskGroupDefine> list2 = this.taskGroupDao.listByGroup(id);
        list.sort(Comparator.comparing(IBaseMetaItem::getOrder));
        list.addAll(list2);
        for (DesignTaskGroupDefine define : list2) {
            this.getChildGroup(define.getKey(), list);
        }
    }

    public void addTaskToGroup(String taskKey, String taskGroupKey) throws Exception {
        DesignTaskDefine define = this.taskDao.getDefineByKey(taskKey);
        if (define == null) {
            throw new Exception("\u4e0d\u5b58\u5728ID\u4e3a\u3010" + taskKey + "\u3011\u7684\u8868\u5355");
        }
        DesignTaskGroupDefine group = this.taskGroupDao.getDefineByKey(taskGroupKey);
        if (group == null) {
            throw new Exception("\u4e0d\u5b58\u5728ID\u4e3a\u3010" + taskGroupKey + "\u3011\u7684\u4efb\u52a1\u5206\u7ec4");
        }
        DesignTaskGroupLink link = new DesignTaskGroupLink();
        link.setTaskKey(taskKey);
        link.setGroupKey(taskGroupKey);
        link.setOrder(OrderGenerator.newOrder());
        this.groupLinkDao.insert(link);
        this.onTaskGroupLinkChanged(ParamChangeEvent.ChangeType.ADD, Collections.singletonList(taskGroupKey));
    }

    public void RemoveTaskFromGroup(String taskKey, String taskGroupKey) throws Exception {
        DesignTaskDefine define = this.taskDao.getDefineByKey(taskKey);
        if (define == null) {
            throw new Exception("\u4e0d\u5b58\u5728ID\u4e3a\u3010" + taskKey + "\u3011\u7684\u8868\u5355");
        }
        DesignTaskGroupDefine group = this.taskGroupDao.getDefineByKey(taskGroupKey);
        if (group == null) {
            throw new Exception("\u4e0d\u5b58\u5728ID\u4e3a\u3010" + taskGroupKey + "\u3011\u7684\u4efb\u52a1\u5206\u7ec4");
        }
        DesignTaskGroupLink link = new DesignTaskGroupLink();
        link.setTaskKey(taskKey);
        link.setGroupKey(taskGroupKey);
        this.groupLinkDao.deleteLink(link);
        this.onTaskGroupLinkChanged(ParamChangeEvent.ChangeType.DELETE, Collections.singletonList(taskGroupKey));
    }

    public List<DesignTaskDefine> checkTaskNameAvailable(String taskKey, String taskName) throws Exception {
        return this.taskDao.checkTaskNameAvailable(taskKey, taskName);
    }

    public List<DesignTaskDefine> getByDataSchemeKey(String dataSchemeKey) {
        return this.taskDao.getByDataSchemeKey(dataSchemeKey);
    }

    public List<DesignTaskGroupLink> getGroupLinkByTaskKey(String taskKey) {
        return this.groupLinkDao.getGroupLinkByTaskKey(taskKey);
    }

    public List<DesignTaskGroupLink> getGroupLinkByGroupKey(String groupKey) {
        return this.groupLinkDao.getGroupLinkByGroupKey(groupKey);
    }

    public void updateTaskGroupLink(DesignTaskGroupLink[] arr) throws Exception {
        for (DesignTaskGroupLink designTaskGroupLink : arr) {
            this.groupLinkDao.update(designTaskGroupLink, new String[]{"groupKey", "taskKey"}, new String[]{designTaskGroupLink.getGroupKey(), designTaskGroupLink.getTaskKey()});
        }
        this.onTaskGroupLinkChanged(ParamChangeEvent.ChangeType.UPDATE, Arrays.stream(arr).map(DesignTaskGroupLink::getGroupKey).collect(Collectors.toList()));
    }

    public List<DesignTaskDefine> getRelFtTasks(String filterTemplateID) {
        return this.taskDao.getRelFtTasks(filterTemplateID);
    }

    public void delete(String[] keys) throws Exception {
        for (String key : keys) {
            this.delete(key);
        }
    }

    public DesignTaskDefine queryTaskDefine(String id) throws Exception {
        return this.taskDao.getDefineByKey(id);
    }

    public DesignTaskDefine queryTaskDefineByCode(String code) throws Exception {
        return this.taskDao.queryTaskDefinesByCode(code);
    }

    public DesignTaskDefine queryTaskDefineByfilePrefix(String filePrefix) throws Exception {
        return this.taskDao.queryTaskDefinesByfilePrefix(filePrefix);
    }

    public DesignTaskDefine queryTaskDefineByTaskTitle(String taskTitle) throws Exception {
        return this.taskDao.queryTaskDefineByTaskTitle(taskTitle);
    }

    public List<DesignTaskDefine> queryAllTaskDefine() throws Exception {
        return this.taskDao.list();
    }

    public List<DesignTaskDefine> queryAllTaskDefinesByType(TaskType type) throws Exception {
        return this.taskDao.queryTaskDefinesByType(type);
    }

    public List<DesignTaskDefine> getAllTasksInGroup(String taskGroupKey) throws Exception {
        return this.getAllTasksInGroupOrder(taskGroupKey);
    }

    private List<DesignTaskDefine> getAllTasksInGroupOrder(String taskGroupKey) throws Exception {
        List<DesignTaskDefine> list = this.taskDao.getAllTasksInGroup(taskGroupKey);
        List<DesignTaskGroupLink> groupLinkByGroupKey = this.groupLinkDao.getGroupLinkByGroupKey(taskGroupKey);
        Map<String, DesignTaskGroupLink> taskKeyToTaskGroupLink = groupLinkByGroupKey.stream().collect(Collectors.toMap(DesignTaskGroupLink::getTaskKey, a -> a, (k1, k2) -> k1));
        for (DesignTaskDefine designTaskDefine : list) {
            DesignTaskGroupLink designTaskGroupLink = taskKeyToTaskGroupLink.get(designTaskDefine.getKey());
            if (designTaskGroupLink == null || !StringUtils.hasLength(designTaskGroupLink.getOrder())) continue;
            designTaskDefine.setOrder(designTaskGroupLink.getOrder());
        }
        list.sort((t1, t2) -> {
            String order = t2.getOrder();
            if (!StringUtils.hasLength(order)) {
                order = t2.getTaskCode();
            }
            return order.compareTo(t1.getOrder());
        });
        return list;
    }

    public List<DesignTaskGroupLink> getGroupLink() {
        return this.groupLinkDao.getAllGroupLink();
    }

    public List<DesignTaskDefine> listTaskByDataScheme(String dataScheme) {
        return this.taskDao.getByDataSchemeKey(dataScheme);
    }
}

