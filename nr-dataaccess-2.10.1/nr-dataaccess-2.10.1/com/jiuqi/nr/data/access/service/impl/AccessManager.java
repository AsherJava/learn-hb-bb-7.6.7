/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine
 *  com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeFormSchemePeriodService
 */
package com.jiuqi.nr.data.access.service.impl;

import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.nr.data.access.exception.AccessException;
import com.jiuqi.nr.data.access.param.AccessForm;
import com.jiuqi.nr.data.access.param.AccessFormMerge;
import com.jiuqi.nr.data.access.param.BatchAccessFormMerge;
import com.jiuqi.nr.data.access.param.DimensionCombinationWrapper;
import com.jiuqi.nr.data.access.param.IAccessForm;
import com.jiuqi.nr.data.access.param.IAccessFormMerge;
import com.jiuqi.nr.data.access.service.ITaskDimensionFilter;
import com.jiuqi.nr.data.access.service.TaskDimensionFilterFactory;
import com.jiuqi.nr.data.access.service.impl.AccessMessageManager;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine;
import com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeFormSchemePeriodService;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

public class AccessManager {
    private static final Logger logger = LoggerFactory.getLogger(AccessMessageManager.class);
    protected final IRunTimeViewController runtimeView;
    protected TaskDimensionFilterFactory filterFactory;
    protected IRuntimeFormSchemePeriodService runtimeFormSchemePeriodService;
    protected Set<String> ignoreAccessItems = new HashSet<String>();
    protected List<String> taskKeys;

    protected AccessManager(IRunTimeViewController runtimeView, TaskDimensionFilterFactory filterFactory, IRuntimeFormSchemePeriodService runtimeFormSchemePeriodService) {
        this.runtimeView = runtimeView;
        this.filterFactory = filterFactory;
        this.runtimeFormSchemePeriodService = runtimeFormSchemePeriodService;
    }

    protected AccessManager(IRunTimeViewController runtimeView, TaskDimensionFilterFactory filterFactory, Set<String> ignoreAccessItems, IRuntimeFormSchemePeriodService runtimeFormSchemePeriodService) {
        this.runtimeView = runtimeView;
        this.filterFactory = filterFactory;
        this.ignoreAccessItems = ignoreAccessItems;
        this.runtimeFormSchemePeriodService = runtimeFormSchemePeriodService;
    }

    protected AccessManager(IRunTimeViewController runtimeView, TaskDimensionFilterFactory filterFactory, List<String> taskKeys, Set<String> ignoreAccessItems, IRuntimeFormSchemePeriodService runtimeFormSchemePeriodService) {
        this.runtimeView = runtimeView;
        this.filterFactory = filterFactory;
        this.taskKeys = taskKeys;
        this.ignoreAccessItems = ignoreAccessItems;
        this.runtimeFormSchemePeriodService = runtimeFormSchemePeriodService;
    }

    public List<String> getFormKeys(String zbKey, String formSchemeKey) {
        Collection formKeys = this.runtimeView.getFormKeysByField(zbKey);
        if (CollectionUtils.isEmpty(formKeys)) {
            return Collections.emptyList();
        }
        List formDefine = this.runtimeView.queryFormsById(new ArrayList(formKeys));
        return formDefine.stream().filter(e -> e.getFormScheme().equals(formSchemeKey)).map(IBaseMetaItem::getKey).collect(Collectors.toList());
    }

    public IAccessFormMerge getAccessForm(DimensionCombination masterKey, String zbKey) {
        AccessFormMerge accessFormMerge = new AccessFormMerge();
        accessFormMerge.setMasterKey(masterKey);
        Collection forms = this.runtimeView.getFormKeysByField(zbKey);
        if (CollectionUtils.isEmpty(forms)) {
            return accessFormMerge;
        }
        List<FormDefine> formDefines = this.runtimeView.queryFormsById(new ArrayList(forms));
        if (CollectionUtils.isEmpty(formDefines)) {
            return accessFormMerge;
        }
        formDefines = this.filterFormByTask(formDefines);
        HashMap<String, String> cache = new HashMap<String, String>();
        HashMap<String, Boolean> taskExistCache = new HashMap<String, Boolean>();
        boolean isFilterByTask = !CollectionUtils.isEmpty(this.taskKeys);
        ITaskDimensionFilter taskDimensionFilter = this.filterFactory.getTaskDimensionFilter(isFilterByTask);
        for (FormDefine formDefine : formDefines) {
            Boolean exist;
            String taskKey = (String)cache.get(formDefine.getFormScheme());
            if (taskKey == null) {
                FormSchemeDefine formScheme = this.runtimeView.getFormScheme(formDefine.getFormScheme());
                if (formScheme == null) continue;
                taskKey = formScheme.getTaskKey();
                cache.put(formScheme.getKey(), taskKey);
            }
            if ((exist = (Boolean)taskExistCache.get(taskKey)) == null) {
                exist = taskDimensionFilter.exist(taskKey, masterKey);
                taskExistCache.put(taskKey, exist);
            }
            if (!exist.booleanValue()) continue;
            accessFormMerge.addForm(formDefine.getKey(), taskKey, formDefine.getFormScheme());
        }
        return accessFormMerge;
    }

    private List<FormDefine> filterFormByTask(List<FormDefine> formDefines) {
        if (CollectionUtils.isEmpty(this.taskKeys)) {
            return formDefines;
        }
        ArrayList<FormDefine> result = new ArrayList<FormDefine>();
        for (FormDefine formDefine : formDefines) {
            FormSchemeDefine formScheme = this.runtimeView.getFormScheme(formDefine.getFormScheme());
            if (!this.taskKeys.contains(formScheme.getTaskKey())) continue;
            result.add(formDefine);
        }
        return result;
    }

    public BatchAccessFormMerge getAccessForm(DimensionCollection masterKeys, Collection<String> zbKeys) {
        BatchAccessFormMerge batchAccessFormMerge = new BatchAccessFormMerge();
        batchAccessFormMerge.setMasterKeys(masterKeys);
        batchAccessFormMerge.setAccessFormMerges(new ArrayList<IAccessFormMerge>());
        HashMap<String, Set<Object>> zbMap = new HashMap<String, Set<Object>>();
        HashMap<String, Set<String>> zb2Forms = new HashMap<String, Set<String>>();
        batchAccessFormMerge.setZb2forms(zb2Forms);
        HashMap<String, FormDefine> formDefineMap = new HashMap<String, FormDefine>();
        HashMap<String, String> scheme2TaskCache = new HashMap<String, String>();
        HashMap<String, IAccessForm> form2AccessMap = new HashMap<String, IAccessForm>();
        for (String zbKey : zbKeys) {
            FormDefine formDefine;
            Object form2;
            Collection forms = this.runtimeView.getFormKeysByField(zbKey);
            if (CollectionUtils.isEmpty(forms)) {
                zb2Forms.put(zbKey, Collections.emptySet());
                continue;
            }
            List<FormDefine> formDefines = new ArrayList<FormDefine>();
            for (Object form2 : forms) {
                formDefine = formDefineMap.computeIfAbsent((String)form2, arg_0 -> ((IRunTimeViewController)this.runtimeView).queryFormById(arg_0));
                if (formDefine == null) continue;
                formDefines.add(formDefine);
            }
            if (CollectionUtils.isEmpty(formDefines = this.filterFormByTask(formDefines))) {
                zb2Forms.put(zbKey, Collections.emptySet());
                continue;
            }
            HashSet<IAccessForm> formSet = new HashSet<IAccessForm>();
            form2 = formDefines.iterator();
            while (form2.hasNext()) {
                formDefine = (FormDefine)form2.next();
                IAccessForm accessForm = (IAccessForm)form2AccessMap.get(formDefine.getKey());
                if (accessForm == null) {
                    String taskKey = scheme2TaskCache.computeIfAbsent(formDefine.getFormScheme(), formSchemeKey -> {
                        FormSchemeDefine formScheme = this.runtimeView.getFormScheme(formDefine.getFormScheme());
                        return formScheme.getTaskKey();
                    });
                    accessForm = new AccessForm(formDefine.getKey(), taskKey, formDefine.getFormScheme());
                    form2AccessMap.put(formDefine.getKey(), accessForm);
                }
                if (!CollectionUtils.isEmpty(this.taskKeys) && !this.taskKeys.contains(accessForm.getTaskKey())) continue;
                formSet.add(accessForm);
            }
            if (formSet.isEmpty()) {
                zbMap.put(zbKey, Collections.emptySet());
                zb2Forms.put(zbKey, Collections.emptySet());
                continue;
            }
            zbMap.put(zbKey, formSet);
            zb2Forms.put(zbKey, formSet.stream().map(IAccessForm::getFormKey).collect(Collectors.toSet()));
        }
        List linkTaskKeys = scheme2TaskCache.values().stream().distinct().collect(Collectors.toList());
        if (CollectionUtils.isEmpty(linkTaskKeys)) {
            return batchAccessFormMerge;
        }
        boolean isFilterByTask = !CollectionUtils.isEmpty(this.taskKeys);
        ITaskDimensionFilter taskDimensionFilter = this.filterFactory.getTaskDimensionFilter(isFilterByTask);
        HashMap<DimensionCombinationWrapper, Map<String, AccessFormMerge>> map = new HashMap<DimensionCombinationWrapper, Map<String, AccessFormMerge>>();
        for (String taskKey : linkTaskKeys) {
            HashMap<String, Set> scheme2Period = new HashMap<String, Set>();
            try {
                List formSchemeDefines = this.runtimeView.queryFormSchemeByTask(taskKey);
                for (FormSchemeDefine formSchemeDefine : formSchemeDefines) {
                    List schemePeriodLinkDefines = this.runtimeFormSchemePeriodService.querySchemePeriodLinkByScheme(formSchemeDefine.getKey());
                    for (SchemePeriodLinkDefine sc : schemePeriodLinkDefines) {
                        scheme2Period.computeIfAbsent(sc.getSchemeKey(), k -> new HashSet()).add(sc.getPeriodKey());
                    }
                }
            }
            catch (Exception e) {
                logger.error("\u83b7\u53d6\u62a5\u8868\u65b9\u6848\u5931\u8d25", e);
                throw new AccessException(e);
            }
            DimensionCollection collection = taskDimensionFilter.replace(taskKey, masterKeys);
            batchAccessFormMerge.putTaskMasterKeys(taskKey, collection);
            List filter = collection.getDimensionCombinations();
            for (DimensionCombination fixedDimensionValues : filter) {
                String period = (String)fixedDimensionValues.getValue("DATATIME");
                DimensionCombinationWrapper wrapper = new DimensionCombinationWrapper(fixedDimensionValues);
                Map zbMergeMap = map.computeIfAbsent(wrapper, k -> new HashMap());
                for (String zbKey : zbKeys) {
                    Set formSet;
                    AccessFormMerge merge = (AccessFormMerge)zbMergeMap.get(zbKey);
                    if (merge == null) {
                        merge = new AccessFormMerge();
                        zbMergeMap.put(zbKey, merge);
                        merge.setMasterKeyWrapper(wrapper);
                        batchAccessFormMerge.getAccessFormMerges().add(merge);
                    }
                    if ((formSet = (Set)zbMap.get(zbKey)) == null) continue;
                    for (IAccessForm accessForm : formSet) {
                        Set periods;
                        if (!accessForm.getTaskKey().equals(taskKey) || (periods = (Set)scheme2Period.get(accessForm.getFormSchemeKey())) == null || !periods.contains(period)) continue;
                        merge.addForm(accessForm);
                    }
                }
            }
        }
        batchAccessFormMerge.setDim2Zb2Form(map);
        return batchAccessFormMerge;
    }

    public boolean needIgnoreItem(String itemServiceName) {
        if (CollectionUtils.isEmpty(this.ignoreAccessItems)) {
            return false;
        }
        if (this.ignoreAccessItems.contains("ALL")) {
            return true;
        }
        return this.ignoreAccessItems.contains(itemServiceName);
    }
}

