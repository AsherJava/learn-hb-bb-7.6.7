/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.cache.NedisCacheProvider
 *  com.jiuqi.np.definition.common.UUIDUtils
 *  com.jiuqi.np.definition.exception.DBParaException
 *  com.jiuqi.np.definition.internal.service.RuntimeDefinitionCache
 *  com.jiuqi.np.definition.internal.service.RuntimeDefinitionRefreshListener
 *  com.jiuqi.util.OrderGenerator
 *  tk.mybatis.mapper.util.Assert
 */
package com.jiuqi.nr.definition.internal.service;

import com.jiuqi.np.cache.NedisCacheProvider;
import com.jiuqi.np.definition.common.UUIDUtils;
import com.jiuqi.np.definition.exception.DBParaException;
import com.jiuqi.np.definition.internal.service.RuntimeDefinitionCache;
import com.jiuqi.np.definition.internal.service.RuntimeDefinitionRefreshListener;
import com.jiuqi.nr.definition.api.IDesignTimeViewController;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.definition.facade.TaskOrgLinkDefine;
import com.jiuqi.nr.definition.internal.dao.TaskOrgLinkDao;
import com.jiuqi.nr.definition.internal.impl.TaskOrgLinkDefineImpl;
import com.jiuqi.util.OrderGenerator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.util.Assert;

@Service
public class TaskOrgLinkService
implements RuntimeDefinitionRefreshListener {
    @Autowired
    private IDesignTimeViewController designTimeViewController;
    @Autowired
    private TaskOrgLinkDao taskOrgLinkDao;
    private final RuntimeDefinitionCache<TaskOrgLinkDefine> cache;
    private static final String ALL_TASK_ORG_LINK = "ALL_TASK_ORG_LINK";

    @Autowired
    public TaskOrgLinkService(NedisCacheProvider cacheProvider) {
        if (cacheProvider == null) {
            throw new IllegalArgumentException("'cacheProvider' must not be null.");
        }
        this.cache = new RuntimeDefinitionCache(cacheProvider.getCacheManager("NR_DEFINITION_CACHE_CONFIGURATION"), TaskOrgLinkDefine.class);
    }

    public List<TaskOrgLinkDefine> listAll() {
        Cache.ValueWrapper wrapper = this.cache.getKVIndexValue(ALL_TASK_ORG_LINK);
        if (Objects.nonNull(wrapper)) {
            return (List)wrapper.get();
        }
        return (List)RuntimeDefinitionCache.synchronizedRun((Object)this, () -> {
            Cache.ValueWrapper revalueWrapper = this.cache.getKVIndexValue(ALL_TASK_ORG_LINK);
            if (Objects.nonNull(revalueWrapper)) {
                return (List)revalueWrapper.get();
            }
            return this.loadTaskOrgLinks();
        });
    }

    private List<TaskOrgLinkDefine> loadTaskOrgLinks() {
        List<TaskOrgLinkDefine> list = this.taskOrgLinkDao.list();
        if (CollectionUtils.isEmpty(list)) {
            this.cache.putKVIndex(ALL_TASK_ORG_LINK, null);
            return null;
        }
        List<TaskOrgLinkDefine> sortedList = list.stream().sorted(Comparator.comparing(TaskOrgLinkDefine::getOrder)).collect(Collectors.toList());
        this.cache.putKVIndex(ALL_TASK_ORG_LINK, sortedList);
        return sortedList;
    }

    public TaskOrgLinkDefine getByKey(String key) {
        Assert.notNull((Object)key, (String)"The key must not be null");
        return this.taskOrgLinkDao.getByKey(key);
    }

    public List<TaskOrgLinkDefine> getByTask(String task) {
        Cache.ValueWrapper valueWrapper = this.cache.getKVIndexValue(task);
        if (Objects.nonNull(valueWrapper)) {
            return (List)valueWrapper.get();
        }
        return (List)RuntimeDefinitionCache.synchronizedRun((Object)this, () -> {
            Cache.ValueWrapper revalueWrapper = this.cache.getKVIndexValue(task);
            if (Objects.nonNull(revalueWrapper)) {
                return (List)revalueWrapper.get();
            }
            return this.loadCacheByTask(task);
        });
    }

    private List<TaskOrgLinkDefine> loadCacheByTask(String taskKey) {
        List<TaskOrgLinkDefine> linkDefines = this.taskOrgLinkDao.getByTask(taskKey);
        if (CollectionUtils.isEmpty(linkDefines)) {
            DesignTaskDefine task = this.designTimeViewController.getTask(taskKey);
            if (task == null) {
                return Collections.emptyList();
            }
            TaskOrgLinkDefine taskOrgLinkDefine = this.buildByTask(task);
            this.designTimeViewController.insertTaskOrgLink(new TaskOrgLinkDefine[]{taskOrgLinkDefine});
            linkDefines.add(taskOrgLinkDefine);
            return linkDefines;
        }
        List<TaskOrgLinkDefine> sortedList = linkDefines.stream().sorted(Comparator.comparing(TaskOrgLinkDefine::getOrder)).collect(Collectors.toList());
        this.cache.putKVIndex(taskKey, sortedList);
        return sortedList;
    }

    private TaskOrgLinkDefine buildByTask(DesignTaskDefine taskDefine) {
        TaskOrgLinkDefineImpl taskOrgLink = new TaskOrgLinkDefineImpl();
        taskOrgLink.setTask(taskDefine.getKey());
        taskOrgLink.setEntity(taskDefine.getDw());
        taskOrgLink.setKey(UUIDUtils.getKey());
        taskOrgLink.setOrder(OrderGenerator.newOrder());
        taskOrgLink.setEntityAlias(null);
        return taskOrgLink;
    }

    public TaskOrgLinkDefine getByTaskAndEntity(String task, String entity) {
        Assert.notNull((Object)task, (String)"The task must not be null");
        Assert.notNull((Object)entity, (String)"The entity must not be null");
        TaskOrgLinkDefine taskOrgLink = this.taskOrgLinkDao.getByTaskAndEntity(task, entity);
        if (null != taskOrgLink) {
            return taskOrgLink;
        }
        return null;
    }

    public String getEntityAlias(String task, String entity) {
        TaskOrgLinkDefine taskOrg = this.getByTaskAndEntity(task, entity);
        if (null != taskOrg) {
            return taskOrg.getEntityAlias();
        }
        return null;
    }

    public void insertTaskOrgLink(TaskOrgLinkDefine[] orgLinkDefines) throws DBParaException {
        this.taskOrgLinkDao.insert(orgLinkDefines);
    }

    public void deleteTaskOrgLink(String[] keys) throws DBParaException {
        this.taskOrgLinkDao.delete(keys);
        this.cache.clear();
    }

    public void deleteTaskOrgLinkByTask(String task) throws DBParaException {
        this.taskOrgLinkDao.deleteByTask(task);
        this.cache.removeIndexs(Arrays.asList(task));
    }

    public void updateTaskOrgLink(TaskOrgLinkDefine[] orgLinkDefines) throws DBParaException {
        Set tasks = Arrays.stream(orgLinkDefines).map(TaskOrgLinkDefine::getTask).collect(Collectors.toSet());
        this.taskOrgLinkDao.update(orgLinkDefines);
        this.cache.removeIndexs(new ArrayList(tasks));
    }

    public void onClearCache() {
        this.cache.clear();
    }
}

