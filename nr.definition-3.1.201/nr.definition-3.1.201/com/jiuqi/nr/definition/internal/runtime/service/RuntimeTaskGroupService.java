/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.cache.NedisCacheProvider
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.np.definition.internal.service.RuntimeDefinitionCache
 *  com.jiuqi.np.definition.internal.service.RuntimeDefinitionRefreshListener
 */
package com.jiuqi.nr.definition.internal.runtime.service;

import com.jiuqi.np.cache.NedisCacheProvider;
import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.np.definition.internal.service.RuntimeDefinitionCache;
import com.jiuqi.np.definition.internal.service.RuntimeDefinitionRefreshListener;
import com.jiuqi.nr.definition.deploy.DeployParams;
import com.jiuqi.nr.definition.facade.DesignTaskGroupDefine;
import com.jiuqi.nr.definition.facade.TaskGroupDefine;
import com.jiuqi.nr.definition.internal.dao.DesignTaskGroupDefineDao;
import com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeTaskGroupService;
import com.jiuqi.nr.definition.internal.runtime.service.RuntimeDefinitionChangeListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;
import org.springframework.util.StringUtils;

@Deprecated
public class RuntimeTaskGroupService
implements IRuntimeTaskGroupService,
RuntimeDefinitionChangeListener,
RuntimeDefinitionRefreshListener {
    private static final Logger log = LoggerFactory.getLogger(RuntimeTaskGroupService.class);
    private static final String IDX_NAME_TASKGROUP = "group";
    private static final String IDX_NAME_ALL = "all";
    private static final String IDX_NAME_TASK = "task";
    private DesignTaskGroupDefineDao taskGroupDao;
    private RuntimeDefinitionCache<TaskGroupDefine> cache;
    private final RuntimeFormGroupCacheLoader cacheLoader = new RuntimeFormGroupCacheLoader();

    private static String createTaskGroupIndexName(String taskGroupKey) {
        return IDX_NAME_TASKGROUP.concat("::").concat(taskGroupKey);
    }

    private static String createTaskIndexName(String taskKey) {
        return IDX_NAME_TASK.concat("::").concat(taskKey);
    }

    public RuntimeTaskGroupService(DesignTaskGroupDefineDao taskGroupDao, NedisCacheProvider cacheProvider) {
        if (taskGroupDao == null) {
            throw new IllegalArgumentException("'taskDao' must not be null.");
        }
        if (cacheProvider == null) {
            throw new IllegalArgumentException("'cacheProvider' must not be null.");
        }
        this.taskGroupDao = taskGroupDao;
        this.cache = new RuntimeDefinitionCache(cacheProvider.getCacheManager("NR_DEFINITION_CACHE_CONFIGURATION"), TaskGroupDefine.class);
    }

    private List<TaskGroupDefine> loadCache() {
        List<TaskGroupDefine> taskGroupDefines = this.loadAllTaskGroups();
        ArrayList<String> allTaskGroupKeys = new ArrayList<String>();
        for (TaskGroupDefine taskGroup : taskGroupDefines) {
            String taskGroupKey = taskGroup.getKey();
            allTaskGroupKeys.add(taskGroupKey);
        }
        this.cache.putObjects(taskGroupDefines);
        this.cache.putKVIndex(IDX_NAME_ALL, allTaskGroupKeys);
        return taskGroupDefines;
    }

    private List<TaskGroupDefine> loadAllTaskGroups() {
        ArrayList<TaskGroupDefine> taskGroupDefines = new ArrayList<TaskGroupDefine>();
        try {
            List<DesignTaskGroupDefine> defines = this.taskGroupDao.list();
            if (defines != null) {
                defines.sort(Comparator.comparing(IBaseMetaItem::getOrder));
            }
            taskGroupDefines.addAll(defines);
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            return new ArrayList<TaskGroupDefine>();
        }
        return taskGroupDefines;
    }

    @Override
    public TaskGroupDefine queryTaskGroup(String taskGroupKey) {
        if (taskGroupKey == null) {
            return null;
        }
        Cache.ValueWrapper valueWrapper = this.cache.getObject(taskGroupKey);
        if (valueWrapper != null) {
            return (TaskGroupDefine)valueWrapper.get();
        }
        return (TaskGroupDefine)RuntimeDefinitionCache.synchronizedRun((Object)this, () -> {
            Cache.ValueWrapper revalueWrapper = this.cache.getObject(taskGroupKey);
            if (revalueWrapper != null) {
                return (DesignTaskGroupDefine)revalueWrapper.get();
            }
            List<TaskGroupDefine> taskGroups = this.loadCache();
            TaskGroupDefine groupDefine = taskGroups.stream().filter(t -> t.getKey().equals(taskGroupKey)).findFirst().orElse(null);
            if (groupDefine == null) {
                this.cache.putNullObject(taskGroupKey);
            }
            return groupDefine;
        });
    }

    @Override
    public List<TaskGroupDefine> getChildTaskGroups(String taskGroupKey, boolean isRecursion) {
        ArrayList<TaskGroupDefine> taskGroups = new ArrayList();
        if (!StringUtils.hasText(taskGroupKey)) {
            List<TaskGroupDefine> allTaskGroupDefines = this.getAllTaskGroupDefines();
            return allTaskGroupDefines.stream().filter(a -> !StringUtils.hasText(a.getParentKey())).collect(Collectors.toList());
        }
        if (isRecursion) {
            List<TaskGroupDefine> allTaskGroupDefines = this.getAllTaskGroupDefines().stream().filter(a -> StringUtils.hasText(a.getParentKey())).collect(Collectors.toList());
            this.getChildGroup(taskGroupKey, taskGroups, allTaskGroupDefines);
        } else {
            taskGroups = this.getChildTaskGroups(taskGroupKey);
        }
        return taskGroups;
    }

    private List<TaskGroupDefine> getChildTaskGroups(String taskGroupKey) {
        if (taskGroupKey == null) {
            return Collections.emptyList();
        }
        Cache.ValueWrapper valueWrapper = this.cache.getKVIndexValue(RuntimeTaskGroupService.createTaskGroupIndexName(taskGroupKey));
        if (Objects.isNull(valueWrapper)) {
            return (List)RuntimeDefinitionCache.synchronizedRun((Object)this, () -> {
                Cache.ValueWrapper revalueWrapper = this.cache.getKVIndexValue(RuntimeTaskGroupService.createTaskGroupIndexName(taskGroupKey));
                if (Objects.nonNull(revalueWrapper)) {
                    List taskGroups = ((List)revalueWrapper.get()).stream().map(this::queryTaskGroup).collect(Collectors.toList());
                    return taskGroups.stream().filter(Objects::nonNull).collect(Collectors.toList());
                }
                return this.cacheLoader.loadCacheByTaskGroup(taskGroupKey);
            });
        }
        List taskGroups = ((List)valueWrapper.get()).stream().map(this::queryTaskGroup).collect(Collectors.toList());
        return taskGroups.stream().filter(Objects::nonNull).collect(Collectors.toList());
    }

    private void getChildGroup(String parentKey, List<TaskGroupDefine> list, List<TaskGroupDefine> allTaskGroupDefines) {
        ArrayList<TaskGroupDefine> childTaskGroupDefine = new ArrayList<TaskGroupDefine>();
        for (TaskGroupDefine taskGroupDefine : allTaskGroupDefines) {
            if (!parentKey.equals(taskGroupDefine.getParentKey())) continue;
            childTaskGroupDefine.add(taskGroupDefine);
        }
        list.addAll(childTaskGroupDefine);
        for (TaskGroupDefine define : childTaskGroupDefine) {
            this.getChildGroup(define.getKey(), list, allTaskGroupDefines);
        }
    }

    @Override
    public List<TaskGroupDefine> getAllTaskGroupDefines() {
        Cache.ValueWrapper valueWrapper = this.cache.getKVIndexValue(IDX_NAME_ALL);
        if (valueWrapper == null) {
            return (List)this.cache.synchronizedRun(() -> {
                Cache.ValueWrapper revalueWrapper = this.cache.getKVIndexValue(IDX_NAME_ALL);
                if (Objects.nonNull(revalueWrapper)) {
                    return this.getTaskGroupDefine(revalueWrapper);
                }
                return this.loadCache();
            });
        }
        return this.getTaskGroupDefine(valueWrapper);
    }

    @Override
    public List<TaskGroupDefine> getAllGroupsFromTask(String taskKey) {
        if (taskKey == null) {
            return Collections.emptyList();
        }
        Cache.ValueWrapper valueWrapper = this.cache.getKVIndexValue(RuntimeTaskGroupService.createTaskIndexName(taskKey));
        if (Objects.isNull(valueWrapper)) {
            return (List)RuntimeDefinitionCache.synchronizedRun((Object)this, () -> {
                Cache.ValueWrapper revalueWrapper = this.cache.getKVIndexValue(RuntimeTaskGroupService.createTaskIndexName(taskKey));
                if (Objects.nonNull(revalueWrapper)) {
                    List taskGroups = ((List)revalueWrapper.get()).stream().map(this::queryTaskGroup).collect(Collectors.toList());
                    return taskGroups.stream().filter(Objects::nonNull).collect(Collectors.toList());
                }
                return this.cacheLoader.loadCacheByTask(taskKey);
            });
        }
        List taskGroups = ((List)valueWrapper.get()).stream().map(this::queryTaskGroup).collect(Collectors.toList());
        return taskGroups.stream().filter(Objects::nonNull).collect(Collectors.toList());
    }

    private List<TaskGroupDefine> getTaskGroupDefine(Cache.ValueWrapper revalueWrapper) {
        List allTaskGroupKeys = (List)revalueWrapper.get();
        Map cachedTaskGroups = this.cache.getObjects((Collection)allTaskGroupKeys);
        ArrayList<TaskGroupDefine> taskGroupDefines = new ArrayList<TaskGroupDefine>(cachedTaskGroups.size());
        for (Cache.ValueWrapper v : cachedTaskGroups.values()) {
            if (v == null) {
                return (List)this.cache.synchronizedRun(() -> this.loadCache());
            }
            taskGroupDefines.add((DesignTaskGroupDefine)v.get());
        }
        return taskGroupDefines;
    }

    public void onClearCache() {
        this.cache.clear();
    }

    @Override
    public void onDeploy(DeployParams deployParams) {
        this.cache.clear();
    }

    private class RuntimeFormGroupCacheLoader {
        private RuntimeFormGroupCacheLoader() {
        }

        private List<TaskGroupDefine> loadCacheByTaskGroup(String taskGroupKey) {
            ArrayList<TaskGroupDefine> taskGroups = null;
            try {
                taskGroups = new ArrayList<TaskGroupDefine>(RuntimeTaskGroupService.this.taskGroupDao.listByGroup(taskGroupKey));
            }
            catch (Exception e) {
                log.error(e.getMessage(), e);
                return new ArrayList<TaskGroupDefine>();
            }
            taskGroups.sort(Comparator.comparing(IBaseMetaItem::getOrder));
            List formGroupIds = taskGroups.stream().map(IBaseMetaItem::getKey).collect(Collectors.toList());
            RuntimeTaskGroupService.this.cache.putKVIndex(RuntimeTaskGroupService.createTaskGroupIndexName(taskGroupKey), formGroupIds);
            return taskGroups;
        }

        private List<TaskGroupDefine> loadCacheByTask(String taskKey) {
            ArrayList<Object> taskGroups = new ArrayList();
            try {
                taskGroups = new ArrayList<DesignTaskGroupDefine>(RuntimeTaskGroupService.this.taskGroupDao.getAllGroupsFromTask(taskKey));
            }
            catch (Exception e) {
                log.error(e.getMessage(), e);
                return new ArrayList<TaskGroupDefine>();
            }
            taskGroups.sort(Comparator.comparing(IBaseMetaItem::getOrder));
            List formGroupIds = taskGroups.stream().map(IBaseMetaItem::getKey).collect(Collectors.toList());
            RuntimeTaskGroupService.this.cache.putKVIndex(RuntimeTaskGroupService.createTaskIndexName(taskKey), formGroupIds);
            return taskGroups;
        }
    }
}

