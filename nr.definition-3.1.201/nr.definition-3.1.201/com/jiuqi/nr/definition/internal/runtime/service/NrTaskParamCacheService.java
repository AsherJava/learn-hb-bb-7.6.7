/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.cache.NedisCache
 *  com.jiuqi.np.cache.NedisCacheManager
 *  com.jiuqi.np.cache.NedisCacheProvider
 *  com.jiuqi.np.definition.deploy.DeployItem
 *  com.jiuqi.np.definition.internal.service.RuntimeDefinitionRefreshListener
 *  com.jiuqi.nr.datascheme.api.service.IdMutexProvider
 *  com.jiuqi.nr.datascheme.api.service.IdMutexProvider$Mutex
 *  com.jiuqi.nr.period.internal.adapter.IPeriodProvider
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.nr.period.modal.IPeriodRow
 */
package com.jiuqi.nr.definition.internal.runtime.service;

import com.jiuqi.np.cache.NedisCache;
import com.jiuqi.np.cache.NedisCacheManager;
import com.jiuqi.np.cache.NedisCacheProvider;
import com.jiuqi.np.definition.deploy.DeployItem;
import com.jiuqi.np.definition.internal.service.RuntimeDefinitionRefreshListener;
import com.jiuqi.nr.datascheme.api.service.IdMutexProvider;
import com.jiuqi.nr.definition.deploy.DeployParams;
import com.jiuqi.nr.definition.event.FormSchemePeriodChangeEvent;
import com.jiuqi.nr.definition.event.ParamChangeEvent;
import com.jiuqi.nr.definition.event.TaskChangeEvent;
import com.jiuqi.nr.definition.event.TaskGroupChangeEvent;
import com.jiuqi.nr.definition.event.TaskGroupLinkChangeEvent;
import com.jiuqi.nr.definition.facade.BigDataDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.facade.TaskFlowsDefine;
import com.jiuqi.nr.definition.facade.TaskGroupDefine;
import com.jiuqi.nr.definition.facade.TaskGroupLinkDefine;
import com.jiuqi.nr.definition.facade.TaskOrgLinkDefine;
import com.jiuqi.nr.definition.internal.dao.DesignBigDataTableDao;
import com.jiuqi.nr.definition.internal.dao.DesignFormSchemeDefineDao;
import com.jiuqi.nr.definition.internal.dao.RunTimeBigDataTableDao;
import com.jiuqi.nr.definition.internal.dao.RunTimeFormSchemeDefineDao;
import com.jiuqi.nr.definition.internal.dao.RunTimeSchemePeriodLinkDao;
import com.jiuqi.nr.definition.internal.dao.RunTimeTaskDefineDao;
import com.jiuqi.nr.definition.internal.dao.RunTimeTaskGroupDefineDao;
import com.jiuqi.nr.definition.internal.dao.RunTimeTaskGroupLinkDao;
import com.jiuqi.nr.definition.internal.impl.DesignBigDataTable;
import com.jiuqi.nr.definition.internal.impl.DesignTaskFlowsDefine;
import com.jiuqi.nr.definition.internal.impl.RunTimeBigDataTable;
import com.jiuqi.nr.definition.internal.impl.RunTimeTaskDefineImpl;
import com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeFormSchemePeriodService;
import com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeTaskGroupService;
import com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeTaskService;
import com.jiuqi.nr.definition.internal.runtime.dto.TaskDTO;
import com.jiuqi.nr.definition.internal.runtime.service.RuntimeDefinitionChangeListener;
import com.jiuqi.nr.definition.internal.service.TaskOrgLinkService;
import com.jiuqi.nr.definition.service.ITaskFlowExtendService;
import com.jiuqi.nr.period.internal.adapter.IPeriodProvider;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nr.period.modal.IPeriodRow;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service
public class NrTaskParamCacheService
implements IRuntimeTaskGroupService,
IRuntimeTaskService,
IRuntimeFormSchemePeriodService,
RuntimeDefinitionChangeListener,
RuntimeDefinitionRefreshListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(NrTaskParamCacheService.class);
    @Autowired
    private RunTimeSchemePeriodLinkDao schemePeriodLinkDao;
    @Autowired
    private RunTimeTaskDefineDao taskDefineDao;
    @Autowired
    private RunTimeTaskGroupLinkDao taskGroupLinkDao;
    @Autowired
    private RunTimeTaskGroupDefineDao taskGroupDefineDao;
    @Autowired
    private RunTimeBigDataTableDao bigDataDao;
    @Autowired
    private DesignBigDataTableDao designBigDataTableDao;
    @Autowired
    private DesignFormSchemeDefineDao designFormSchemeDefineDao;
    @Autowired
    private RunTimeFormSchemeDefineDao runTimeFormSchemeDefineDao;
    @Autowired
    private PeriodEngineService periodEngineService;
    @Autowired(required=false)
    private ITaskFlowExtendService taskFlowExtendService;
    @Autowired
    private TaskOrgLinkService taskOrgLinkService;
    private final NedisCache schemePeriodCache;
    private final NedisCache taskDefineCache;
    private final NedisCache taskGroupLinkCache;
    private final NedisCache taskGroupDefineCache;
    private final IdMutexProvider idMutexProvider = new IdMutexProvider();
    private static final String LOADED_FLAG = "LOADED_FLAG";

    public NrTaskParamCacheService(NedisCacheProvider cacheProvider) {
        NedisCacheManager cacheManager = cacheProvider.getCacheManager("NR_DEFINITION_CACHE_CONFIGURATION");
        this.schemePeriodCache = cacheManager.getCache("FORM_SCHEME_PERIOD");
        this.taskDefineCache = cacheManager.getCache("TASK_DEFINE");
        this.taskGroupLinkCache = cacheManager.getCache("TASK_GROUP_LINK");
        this.taskGroupDefineCache = cacheManager.getCache("TASK_GROUP_DEFINE");
    }

    public void onClearCache() {
        this.schemePeriodCache.clear();
        this.taskDefineCache.clear();
        this.taskGroupLinkCache.clear();
        this.taskGroupDefineCache.clear();
        LOGGER.info("\u4efb\u52a1\u7f13\u5b58\u670d\u52a1\uff1a\u6e05\u7406\u5168\u90e8\u4efb\u52a1\u7f13\u5b58");
    }

    @Override
    public void onDeploy(DeployParams deployParams) {
        DeployItem formScheme = deployParams.getFormScheme();
        Set keys = formScheme.getRuntimeUninDesignTimeKeys();
        if (CollectionUtils.isEmpty(keys)) {
            return;
        }
        this.taskDefineCache.clear();
    }

    @Override
    public void refreshTaskCache() {
        this.taskDefineCache.clear();
    }

    @Override
    public List<SchemePeriodLinkDefine> querySchemePeriodLinkByScheme(String scheme) {
        if (!StringUtils.hasText(scheme)) {
            return Collections.emptyList();
        }
        Cache.ValueWrapper wrapper = this.schemePeriodCache.get(scheme);
        List defines = null == wrapper ? this.loadSchemePeriod(scheme) : (List)wrapper.get();
        return null == defines ? Collections.emptyList() : new ArrayList(defines);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private List<SchemePeriodLinkDefine> loadSchemePeriod(String scheme) {
        IdMutexProvider.Mutex mutex = this.idMutexProvider.getMutex("period".concat(scheme));
        synchronized (mutex) {
            Cache.ValueWrapper wrapper = this.schemePeriodCache.get(scheme);
            if (null != wrapper) {
                return (List)wrapper.get();
            }
            List<SchemePeriodLinkDefine> defines = this.schemePeriodLinkDao.queryByScheme(scheme);
            if (null == defines) {
                defines = Collections.emptyList();
            } else {
                FormSchemeDefine formScheme = this.designFormSchemeDefineDao.getDefineByKey(scheme);
                if (null == formScheme) {
                    formScheme = this.runTimeFormSchemeDefineDao.getDefineByKey(scheme);
                }
                if (null == formScheme) {
                    defines = Collections.emptyList();
                } else {
                    TaskDefine taskDefine = this.queryTaskDefine(formScheme.getTaskKey());
                    IPeriodProvider periodProvider = this.periodEngineService.getPeriodAdapter().getPeriodProvider(taskDefine.getDateTime());
                    List periodItems = periodProvider.getPeriodItems();
                    HashMap<String, SchemePeriodLinkDefine> map = new HashMap<String, SchemePeriodLinkDefine>();
                    for (SchemePeriodLinkDefine define : defines) {
                        map.put(define.getPeriodKey(), define);
                    }
                    defines = new ArrayList();
                    for (IPeriodRow periodItem : periodItems) {
                        SchemePeriodLinkDefine define = (SchemePeriodLinkDefine)map.get(periodItem.getCode());
                        if (null == define) continue;
                        defines.add(define);
                    }
                }
            }
            this.schemePeriodCache.put(scheme, defines);
            return defines;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private List<SchemePeriodLinkDefine> loadAllSchemePeriod(String scheme) {
        if (this.schemePeriodCache.exists(LOADED_FLAG)) {
            return Collections.emptyList();
        }
        NedisCache nedisCache = this.schemePeriodCache;
        synchronized (nedisCache) {
            if (this.schemePeriodCache.exists(LOADED_FLAG)) {
                return this.querySchemePeriodLinkByScheme(scheme);
            }
            List<SchemePeriodLinkDefine> list = this.schemePeriodLinkDao.list();
            HashMap map = new HashMap();
            for (SchemePeriodLinkDefine schemePeriodLinkDefine : list) {
                map.computeIfAbsent(schemePeriodLinkDefine.getSchemeKey(), k -> new ArrayList()).add(schemePeriodLinkDefine);
            }
            for (Map.Entry entry : map.entrySet()) {
                this.schemePeriodCache.put((String)entry.getKey(), entry.getValue());
            }
            this.schemePeriodCache.put(LOADED_FLAG, (Object)LOADED_FLAG);
            return map.getOrDefault(scheme, Collections.emptyList());
        }
    }

    @Override
    public TaskGroupDefine queryTaskGroup(String taskGroupKey) {
        if (!StringUtils.hasText(taskGroupKey)) {
            return null;
        }
        Cache.ValueWrapper wrapper = this.taskGroupDefineCache.get(taskGroupKey);
        if (null == wrapper) {
            if (this.taskGroupDefineCache.exists(LOADED_FLAG)) {
                return null;
            }
            this.loadTaskGroups();
            return this.queryTaskGroup(taskGroupKey);
        }
        return (TaskGroupDefine)wrapper.get();
    }

    @Override
    public List<TaskGroupDefine> getChildTaskGroups(String taskGroupKey, boolean isRecursion) {
        if (isRecursion) {
            List<TaskGroupDefine> groups = this.getChildTaskGroups(taskGroupKey, false);
            if (CollectionUtils.isEmpty(groups)) {
                return Collections.emptyList();
            }
            ArrayList<TaskGroupDefine> result = new ArrayList<TaskGroupDefine>(groups);
            for (TaskGroupDefine group : groups) {
                result.addAll(this.getChildTaskGroups(group.getKey(), true));
            }
            return result;
        }
        if (!StringUtils.hasText(taskGroupKey)) {
            return this.getAllTaskGroupDefines().stream().filter(g -> !StringUtils.hasText(g.getParentKey()) || "00000000-0000-0000-0000-000000000000".equals(g.getParentKey())).collect(Collectors.toList());
        }
        Cache.ValueWrapper wrapper = this.taskGroupDefineCache.get(this.parentIndex(taskGroupKey));
        if (null == wrapper) {
            if (this.taskGroupDefineCache.exists(LOADED_FLAG)) {
                return Collections.emptyList();
            }
            this.loadTaskGroups();
            return this.getChildTaskGroups(taskGroupKey, false);
        }
        return (List)wrapper.get();
    }

    @Override
    public List<TaskGroupDefine> getAllTaskGroupDefines() {
        Cache.ValueWrapper wrapper = this.taskGroupDefineCache.get(LOADED_FLAG);
        if (null == wrapper) {
            this.loadTaskGroups();
            return this.getAllTaskGroupDefines();
        }
        return (List)wrapper.get();
    }

    @Override
    public List<TaskGroupDefine> getAllGroupsFromTask(String taskKey) {
        if (!StringUtils.hasText(taskKey)) {
            return Collections.emptyList();
        }
        Cache.ValueWrapper wrapper = this.taskGroupLinkCache.get(this.taskLinkIndex(taskKey));
        if (null == wrapper) {
            if (this.taskGroupLinkCache.exists(LOADED_FLAG)) {
                return Collections.emptyList();
            }
            this.loadTaskGroupLinks();
            return this.getAllGroupsFromTask(taskKey);
        }
        List groupLinkDefines = (List)wrapper.get();
        if (CollectionUtils.isEmpty(groupLinkDefines)) {
            return Collections.emptyList();
        }
        ArrayList<TaskGroupDefine> result = new ArrayList<TaskGroupDefine>();
        for (TaskGroupLinkDefine define : groupLinkDefines) {
            TaskGroupDefine group;
            if ("00000000-0000-0000-0000-000000000000".equals(define.getGroupKey()) || null == (group = this.queryTaskGroup(define.getGroupKey()))) continue;
            result.add(group);
        }
        return result;
    }

    private String parentIndex(String key) {
        return "parent" + key;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void loadTaskGroups() {
        NedisCache nedisCache = this.taskGroupDefineCache;
        synchronized (nedisCache) {
            if (this.taskGroupDefineCache.exists(LOADED_FLAG)) {
                return;
            }
            List<TaskGroupDefine> list = this.taskGroupDefineDao.list();
            HashMap<String, List> map = new HashMap<String, List>();
            for (TaskGroupDefine taskGroupDefine : list) {
                map.computeIfAbsent(taskGroupDefine.getParentKey(), k -> new ArrayList()).add(taskGroupDefine);
                this.taskGroupDefineCache.put(taskGroupDefine.getKey(), (Object)taskGroupDefine);
            }
            for (Map.Entry entry : map.entrySet()) {
                this.taskGroupDefineCache.put(this.parentIndex((String)entry.getKey()), entry.getValue());
            }
            this.taskGroupDefineCache.put(LOADED_FLAG, list);
        }
    }

    private String groupLinkIndex(String groupKey) {
        return "group" + groupKey;
    }

    private String taskLinkIndex(String taskKey) {
        return "task" + taskKey;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void loadTaskGroupLinks() {
        NedisCache nedisCache = this.taskGroupLinkCache;
        synchronized (nedisCache) {
            if (this.taskGroupLinkCache.exists(LOADED_FLAG)) {
                return;
            }
            List<TaskGroupLinkDefine> list = this.taskGroupLinkDao.list();
            HashMap<String, List> group = new HashMap<String, List>();
            HashMap<String, List> task = new HashMap<String, List>();
            for (TaskGroupLinkDefine taskGroupLinkDefine : list) {
                group.computeIfAbsent(taskGroupLinkDefine.getGroupKey(), k -> new ArrayList()).add(taskGroupLinkDefine);
                task.computeIfAbsent(taskGroupLinkDefine.getTaskKey(), k -> new ArrayList()).add(taskGroupLinkDefine);
            }
            for (Map.Entry entry : group.entrySet()) {
                this.taskGroupLinkCache.put(this.groupLinkIndex((String)entry.getKey()), entry.getValue());
            }
            for (Map.Entry entry : task.entrySet()) {
                this.taskGroupLinkCache.put(this.taskLinkIndex((String)entry.getKey()), entry.getValue());
            }
            this.taskGroupLinkCache.put(LOADED_FLAG, (Object)LOADED_FLAG);
        }
    }

    private String codeIndex(String code) {
        return "code" + code;
    }

    private String prefixIndex(String prefix) {
        return "prefix" + prefix;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void loadTasks() {
        NedisCache nedisCache = this.taskDefineCache;
        synchronized (nedisCache) {
            if (this.taskDefineCache.exists(LOADED_FLAG)) {
                return;
            }
            List<TaskDefine> list = this.taskDefineDao.list();
            Set taskKeys = this.runTimeFormSchemeDefineDao.list().stream().map(FormSchemeDefine::getTaskKey).collect(Collectors.toSet());
            list.removeIf(i -> !taskKeys.contains(i.getKey()));
            ArrayList<TaskDTO> tasks = new ArrayList<TaskDTO>();
            for (TaskDefine define : list) {
                TaskDTO task = new TaskDTO(define, this::getFlowDefine, this.taskFlowExtendService);
                tasks.add(task);
                this.taskDefineCache.put(task.getKey(), (Object)task);
                if (StringUtils.hasText(task.getTaskCode())) {
                    this.taskDefineCache.put(this.codeIndex(task.getTaskCode()), (Object)task);
                }
                if (!StringUtils.hasText(task.getTaskFilePrefix())) continue;
                this.taskDefineCache.put(this.prefixIndex(task.getTaskFilePrefix()), (Object)task);
            }
            this.taskDefineCache.put(LOADED_FLAG, tasks);
        }
    }

    @Deprecated
    private void loadFlowDefines(List<TaskDefine> taskDefines) {
        List<DesignBigDataTable> designBigDataTables = this.designBigDataTableDao.queryigDataDefine("FLOWSETTING");
        LinkedHashMap<String, DesignTaskFlowsDefine> flowSettings = new LinkedHashMap<String, DesignTaskFlowsDefine>(taskDefines.size());
        for (BigDataDefine bigDataDefine : designBigDataTables) {
            flowSettings.put(bigDataDefine.getKey(), DesignTaskFlowsDefine.bytesToTaskFlowsData(bigDataDefine.getData()));
        }
        List<RunTimeBigDataTable> runTimeBigDataTables = this.bigDataDao.queryigDataDefine("FLOWSETTING");
        for (BigDataDefine bigDataDefine : runTimeBigDataTables) {
            flowSettings.put(bigDataDefine.getKey(), DesignTaskFlowsDefine.bytesToTaskFlowsData(bigDataDefine.getData()));
        }
        for (TaskDefine taskDefine : taskDefines) {
            ((RunTimeTaskDefineImpl)taskDefine).setFlowsSetting((TaskFlowsDefine)flowSettings.get(taskDefine.getKey()));
        }
    }

    private TaskFlowsDefine getFlowDefine(String taskKey) {
        BigDataDefine bigData = this.bigDataDao.queryigDataDefine(taskKey, "FLOWSETTING");
        if (null == bigData) {
            bigData = this.designBigDataTableDao.queryigDataDefine(taskKey, "FLOWSETTING");
        }
        if (null == bigData) {
            return null;
        }
        return DesignTaskFlowsDefine.bytesToTaskFlowsData(bigData.getData());
    }

    @Override
    public List<TaskDefine> getAllTaskDefines() {
        Cache.ValueWrapper wrapper = this.taskDefineCache.get(LOADED_FLAG);
        if (null == wrapper) {
            this.loadTasks();
            return this.getAllTaskDefines();
        }
        return (List)wrapper.get();
    }

    @Override
    public TaskDefine queryTaskDefine(String taskKey) {
        if (!StringUtils.hasText(taskKey)) {
            return null;
        }
        Cache.ValueWrapper wrapper = this.taskDefineCache.get(taskKey);
        if (null == wrapper) {
            if (this.taskDefineCache.exists(LOADED_FLAG)) {
                return null;
            }
            this.loadTasks();
            return this.queryTaskDefine(taskKey);
        }
        return (TaskDefine)wrapper.get();
    }

    @Override
    public TaskDefine queryTaskDefineByCode(String code) {
        if (!StringUtils.hasText(code)) {
            return null;
        }
        Cache.ValueWrapper wrapper = this.taskDefineCache.get(this.codeIndex(code));
        if (null == wrapper) {
            if (this.taskDefineCache.exists(LOADED_FLAG)) {
                return null;
            }
            this.loadTasks();
            return this.queryTaskDefineByCode(code);
        }
        return (TaskDefine)wrapper.get();
    }

    @Override
    public TaskDefine queryTaskDefineByFilePrefix(String filePrefix) {
        if (!StringUtils.hasText(filePrefix)) {
            return null;
        }
        Cache.ValueWrapper wrapper = this.taskDefineCache.get(this.prefixIndex(filePrefix));
        if (null == wrapper) {
            if (this.taskDefineCache.exists(LOADED_FLAG)) {
                return null;
            }
            this.loadTasks();
            return this.queryTaskDefineByFilePrefix(filePrefix);
        }
        return (TaskDefine)wrapper.get();
    }

    @Override
    public List<TaskDefine> queryTaskDefinesInGroup(String taskGroupKey) {
        if (!StringUtils.hasText(taskGroupKey)) {
            ArrayList<TaskDefine> root = new ArrayList<TaskDefine>();
            List<TaskDefine> all = this.getAllTaskDefines();
            for (TaskDefine task : all) {
                List<TaskGroupDefine> groups = this.getAllGroupsFromTask(task.getKey());
                if (!CollectionUtils.isEmpty(groups)) continue;
                root.add(task);
            }
            return root;
        }
        Cache.ValueWrapper wrapper = this.taskGroupLinkCache.get(this.groupLinkIndex(taskGroupKey));
        if (null == wrapper) {
            if (this.taskGroupLinkCache.exists(LOADED_FLAG)) {
                return Collections.emptyList();
            }
            this.loadTaskGroupLinks();
            return this.queryTaskDefinesInGroup(taskGroupKey);
        }
        List groupLinkDefines = (List)wrapper.get();
        if (CollectionUtils.isEmpty(groupLinkDefines)) {
            return Collections.emptyList();
        }
        ArrayList<TaskDefine> result = new ArrayList<TaskDefine>();
        for (TaskGroupLinkDefine define : groupLinkDefines) {
            TaskDefine task = this.queryTaskDefine(define.getTaskKey());
            if (null == task) continue;
            result.add(task);
        }
        return result;
    }

    @Override
    public List<TaskDefine> queryTaskDefinesByDataScheme(String dataScheme) {
        return this.getAllTaskDefines().stream().filter(t -> t.getDataScheme().equals(dataScheme)).collect(Collectors.toList());
    }

    @Override
    public List<TaskDefine> queryTaskDefinesByCaliber(String caliber) {
        List<TaskOrgLinkDefine> allTaskOrgLinks = this.taskOrgLinkService.listAll();
        if (CollectionUtils.isEmpty(allTaskOrgLinks)) {
            return Collections.emptyList();
        }
        List taskKeys = allTaskOrgLinks.stream().filter(taskOrgLink -> taskOrgLink.getEntity().equals(caliber)).map(TaskOrgLinkDefine::getTask).collect(Collectors.toList());
        return this.getAllTaskDefines().stream().filter(t -> taskKeys.contains(t.getKey())).collect(Collectors.toList());
    }

    @Component
    public class FormSchemePeriodChangeListener
    implements ApplicationListener<FormSchemePeriodChangeEvent> {
        @Override
        public void onApplicationEvent(FormSchemePeriodChangeEvent event) {
            NrTaskParamCacheService.this.schemePeriodCache.clear();
        }
    }

    @Component
    public class TaskChangeListener
    implements ApplicationListener<TaskChangeEvent> {
        @Override
        public void onApplicationEvent(TaskChangeEvent event) {
            List<TaskDefine> tasks = event.getTasks();
            if (ParamChangeEvent.ChangeType.DELETE == event.getType()) {
                for (TaskDefine task : tasks) {
                    List<FormSchemeDefine> schemes = NrTaskParamCacheService.this.runTimeFormSchemeDefineDao.listByTask(task.getKey());
                    if (CollectionUtils.isEmpty(schemes)) {
                        NrTaskParamCacheService.this.taskDefineCache.evict(task.getKey());
                        NrTaskParamCacheService.this.taskDefineCache.evict(NrTaskParamCacheService.this.codeIndex(task.getKey()));
                        NrTaskParamCacheService.this.taskDefineCache.evict(NrTaskParamCacheService.this.prefixIndex(task.getKey()));
                        continue;
                    }
                    NrTaskParamCacheService.this.taskDefineCache.put(task.getKey(), (Object)task);
                    NrTaskParamCacheService.this.taskDefineCache.put(NrTaskParamCacheService.this.codeIndex(task.getKey()), (Object)task);
                    NrTaskParamCacheService.this.taskDefineCache.put(NrTaskParamCacheService.this.prefixIndex(task.getKey()), (Object)task);
                }
                NrTaskParamCacheService.this.taskGroupLinkCache.clear();
            } else {
                NrTaskParamCacheService.this.taskDefineCache.clear();
            }
        }
    }

    @Component
    public class TaskGroupLinkChangeListener
    implements ApplicationListener<TaskGroupLinkChangeEvent> {
        @Override
        public void onApplicationEvent(TaskGroupLinkChangeEvent event) {
            NrTaskParamCacheService.this.taskGroupLinkCache.clear();
        }
    }

    @Component
    public class TaskGroupChangeListener
    implements ApplicationListener<TaskGroupChangeEvent> {
        @Override
        public void onApplicationEvent(TaskGroupChangeEvent event) {
            NrTaskParamCacheService.this.taskGroupDefineCache.clear();
            if (ParamChangeEvent.ChangeType.DELETE == event.getType()) {
                NrTaskParamCacheService.this.taskGroupLinkCache.clear();
            }
        }
    }
}

