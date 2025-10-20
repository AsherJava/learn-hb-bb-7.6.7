/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.OrderGenerator
 *  com.jiuqi.np.definition.common.UUIDUtils
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.nr.definition.internal.service;

import com.jiuqi.bi.util.OrderGenerator;
import com.jiuqi.np.definition.common.UUIDUtils;
import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.nr.definition.event.ParamChangeEvent;
import com.jiuqi.nr.definition.facade.DesignTaskGroupDefine;
import com.jiuqi.nr.definition.facade.TaskGroupDefine;
import com.jiuqi.nr.definition.internal.dao.DesignTaskGroupDefineDao;
import com.jiuqi.nr.definition.internal.service.AbstractParamService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
public class DesignTaskGroupDefineService
extends AbstractParamService {
    @Autowired
    private DesignTaskGroupDefineDao taskGroupDao;

    public String insertTaskGroupDefine(DesignTaskGroupDefine define) throws Exception {
        if (!StringUtils.hasText(define.getTitle())) {
            throw new Exception("\u4efb\u52a1\u5206\u7ec4\u6807\u9898\u4e0d\u80fd\u4e3a\u7a7a");
        }
        if (!StringUtils.hasLength(define.getKey())) {
            define.setKey(UUIDUtils.getKey());
        }
        if (define.getUpdateTime() == null) {
            define.setUpdateTime(new Date());
        }
        if (!StringUtils.hasLength(define.getOrder())) {
            define.setOrder(OrderGenerator.newOrder());
        }
        this.taskGroupDao.insert(define);
        this.onTaskGroupChanged(ParamChangeEvent.ChangeType.ADD, Collections.singletonList(define.getKey()));
        return define.getKey();
    }

    public void updateTaskGroupDefine(DesignTaskGroupDefine define) throws Exception {
        if (!StringUtils.hasText(define.getTitle())) {
            throw new Exception("\u4efb\u52a1\u5206\u7ec4\u6807\u9898\u4e0d\u80fd\u4e3a\u7a7a");
        }
        if (!StringUtils.hasLength(define.getKey())) {
            return;
        }
        if (define.getUpdateTime() == null) {
            define.setUpdateTime(new Date());
        }
        if (!StringUtils.hasLength(define.getOrder())) {
            define.setOrder(OrderGenerator.newOrder());
        }
        this.taskGroupDao.update(define);
        this.onTaskGroupChanged(ParamChangeEvent.ChangeType.UPDATE, Collections.singletonList(define.getKey()));
    }

    public void deleteTaskGroupDefine(String taskGroupKey) throws Exception {
        this.taskGroupDao.delete(taskGroupKey);
        HashSet<String> taskGroupKeys = new HashSet<String>();
        taskGroupKeys.add(taskGroupKey);
        this.onTaskGroupChanged(ParamChangeEvent.ChangeType.DELETE, Collections.singletonList(taskGroupKey));
    }

    public List<DesignTaskGroupDefine> queryTaskGroupDefineByGroupId(String id) throws Exception {
        List<DesignTaskGroupDefine> list = this.taskGroupDao.listByGroup(id);
        if (null != list) {
            list.sort(Comparator.comparing(IBaseMetaItem::getOrder));
        }
        return list;
    }

    public List<DesignTaskGroupDefine> queryTaskGroupDefineByGroupId(String id, boolean isRecursion) throws Exception {
        if (!StringUtils.hasLength(id)) {
            List<DesignTaskGroupDefine> defines = this.taskGroupDao.listByGroup(null);
            if (defines != null) {
                defines.sort(Comparator.comparing(IBaseMetaItem::getOrder));
            }
            return defines;
        }
        ArrayList<DesignTaskGroupDefine> list = new ArrayList();
        if (isRecursion) {
            List<DesignTaskGroupDefine> designTaskGroupDefines = this.queryAllTaskGroupDefine().stream().filter(a -> StringUtils.hasText(a.getParentKey())).collect(Collectors.toList());
            this.getChildGroup(id, list, designTaskGroupDefines);
        } else {
            list = this.queryTaskGroupDefineByGroupId(id);
        }
        if (null != list) {
            list.sort(Comparator.comparing(IBaseMetaItem::getOrder));
        }
        return list;
    }

    private void getChildGroup(String parentKey, List<DesignTaskGroupDefine> list, List<DesignTaskGroupDefine> designTaskGroupDefines) throws Exception {
        ArrayList<DesignTaskGroupDefine> childTaskGroupDefine = new ArrayList<DesignTaskGroupDefine>();
        for (DesignTaskGroupDefine designTaskGroupDefine : designTaskGroupDefines) {
            if (!parentKey.equals(designTaskGroupDefine.getParentKey())) continue;
            childTaskGroupDefine.add(designTaskGroupDefine);
        }
        list.addAll(childTaskGroupDefine);
        for (TaskGroupDefine taskGroupDefine : childTaskGroupDefine) {
            this.getChildGroup(taskGroupDefine.getKey(), list, designTaskGroupDefines);
        }
    }

    public DesignTaskGroupDefine queryTaskGroupDefine(String id) throws Exception {
        return this.taskGroupDao.getDefineByKey(id);
    }

    public DesignTaskGroupDefine queryTaskGroupDefineByCode(String code) throws Exception {
        return this.taskGroupDao.queryDefinesByCode(code);
    }

    public List<DesignTaskGroupDefine> queryAllTaskGroupDefine() throws Exception {
        List<DesignTaskGroupDefine> defines = this.taskGroupDao.list();
        if (null != defines) {
            defines.sort(Comparator.comparing(IBaseMetaItem::getOrder));
        }
        return defines;
    }

    public DesignTaskGroupDefine queryTaskGroupDefinesByCode(String code) throws Exception {
        return this.taskGroupDao.queryDefinesByCode(code);
    }

    public List<DesignTaskGroupDefine> getGroupByTask(String taskKey) throws Exception {
        List<DesignTaskGroupDefine> defines = this.taskGroupDao.getAllGroupsFromTask(taskKey);
        if (null != defines) {
            defines.sort(Comparator.comparing(IBaseMetaItem::getOrder));
        }
        return defines;
    }

    @Transactional(rollbackFor={Exception.class})
    public void exchangeTaskGroup(String taskGroupkey1, String taskGroupkey2) throws Exception {
        DesignTaskGroupDefine group1 = this.queryTaskGroupDefine(taskGroupkey1);
        DesignTaskGroupDefine group2 = this.queryTaskGroupDefine(taskGroupkey2);
        if (null != group1 && null != group2) {
            String oldOrder = group1.getOrder();
            group1.setOrder(group2.getOrder());
            group2.setOrder(oldOrder);
            group1.setUpdateTime(new Date());
            group2.setUpdateTime(new Date());
            this.updateTaskGroupDefine(group1);
            this.updateTaskGroupDefine(group2);
        }
        this.onTaskGroupChanged(ParamChangeEvent.ChangeType.UPDATE, Arrays.asList(taskGroupkey1, taskGroupkey2));
    }

    public List<DesignTaskGroupDefine> queryTaskGroupDefines(String[] keys) throws Exception {
        ArrayList<DesignTaskGroupDefine> list = new ArrayList<DesignTaskGroupDefine>();
        for (String key : keys) {
            DesignTaskGroupDefine define = this.taskGroupDao.getDefineByKey(key);
            if (define == null) continue;
            list.add(define);
        }
        return list;
    }

    public void insertTaskGroupDefines(DesignTaskGroupDefine[] defines) throws Exception {
        this.taskGroupDao.insert(defines);
        this.onTaskGroupChanged(ParamChangeEvent.ChangeType.ADD, Arrays.stream(defines).map(IBaseMetaItem::getKey).collect(Collectors.toList()));
    }

    public void updateTaskGroupDefines(DesignTaskGroupDefine[] defines) throws Exception {
        for (DesignTaskGroupDefine define : defines) {
            define.setUpdateTime(new Date());
        }
        this.taskGroupDao.update(defines);
        this.onTaskGroupChanged(ParamChangeEvent.ChangeType.UPDATE, Arrays.stream(defines).map(IBaseMetaItem::getKey).collect(Collectors.toList()));
    }

    public void delete(String[] keys) throws Exception {
        this.taskGroupDao.delete(keys);
        this.onTaskGroupChanged(ParamChangeEvent.ChangeType.DELETE, Arrays.asList(keys));
    }
}

