/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.definition.common.FormType
 *  com.jiuqi.nr.definition.common.TaskGatherType
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.controller.ITaskOptionController
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 *  com.jiuqi.nr.definition.option.internal.NonLeafNodeReadOnlyOption
 *  com.jiuqi.nr.entity.engine.executors.ExecutorContext
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityDataService
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 */
package com.jiuqi.nr.data.access.service.impl;

import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.nr.data.access.common.AccessType;
import com.jiuqi.nr.data.access.exception.AccessException;
import com.jiuqi.nr.data.access.param.AccessCode;
import com.jiuqi.nr.data.access.param.BatchMergeAccess;
import com.jiuqi.nr.data.access.param.CanAccessBatchMergeAccess;
import com.jiuqi.nr.data.access.param.FormBatchAccessCache;
import com.jiuqi.nr.data.access.param.IAccessForm;
import com.jiuqi.nr.data.access.param.IAccessFormMerge;
import com.jiuqi.nr.data.access.param.IAccessMessage;
import com.jiuqi.nr.data.access.param.IBatchAccess;
import com.jiuqi.nr.data.access.param.IBatchAccessFormMerge;
import com.jiuqi.nr.data.access.param.IBatchMergeAccess;
import com.jiuqi.nr.data.access.service.IDataAccessItemService;
import com.jiuqi.nr.data.access.util.DataAccesslUtil;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.definition.common.FormType;
import com.jiuqi.nr.definition.common.TaskGatherType;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.controller.ITaskOptionController;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import com.jiuqi.nr.definition.option.internal.NonLeafNodeReadOnlyOption;
import com.jiuqi.nr.entity.engine.executors.ExecutorContext;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityDataService;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class NonLeafNodeReadOnlyAccessServiceImpl
implements IDataAccessItemService {
    private static final Logger logger = LoggerFactory.getLogger(NonLeafNodeReadOnlyAccessServiceImpl.class);
    @Autowired(required=false)
    private NonLeafNodeReadOnlyOption option;
    @Autowired
    private ITaskOptionController taskOptionController;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private IEntityDataService dataService;
    @Autowired
    private IEntityViewRunTimeController viewAdapter;
    @Autowired
    private IDataDefinitionRuntimeController tbRtCtl;
    @Autowired
    private DataAccesslUtil dataAccesslUtil;
    private static final String NOLEAF_SUM_MSG = "\u6c47\u603b\u5355\u4f4d\u4e0d\u53ef\u5199";
    private final AccessCode canAccess = new AccessCode(this.name());
    private final CanAccessBatchMergeAccess canAccessBatchMergeAccess = new CanAccessBatchMergeAccess(this.name());
    private static final String ERROR_INFO = "\u975e\u53f6\u5b50\u5355\u4f4d\u4e0b\u7684\u62a5\u8868\u8bfb\u5199\u6743\u9650\u5224\u65ad\u5f02\u5e38\uff01";

    @Override
    public String name() {
        return "nonLeafNodeReadOnly";
    }

    @Override
    public int getOrder() {
        return 9;
    }

    @Override
    public boolean isEnable(String taskKey, String formSchemeKey) {
        String value;
        if (this.option != null && "1".equals(value = this.taskOptionController.getValue(taskKey, this.option.getKey()))) {
            return true;
        }
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(taskKey);
        if (taskDefine != null) {
            TaskGatherType taskGatherType = taskDefine.getTaskGatherType();
            return taskGatherType == TaskGatherType.TASK_GATHER_AUTO;
        }
        return false;
    }

    @Override
    public IAccessMessage getAccessMessage() {
        return code -> Optional.ofNullable(code).filter(e -> e.equals("1")).orElse(NOLEAF_SUM_MSG);
    }

    @Override
    public AccessCode visible(String formSchemeKey, DimensionCombination masterKey, String formKey) {
        return this.canAccess;
    }

    @Override
    public AccessCode readable(String formSchemeKey, DimensionCombination masterKey, String formKey) {
        return this.canAccess;
    }

    @Override
    public AccessCode writeable(String formSchemeKey, DimensionCombination masterKey, String formKey) {
        if (StringUtils.isEmpty((String)formKey)) {
            return this.canAccess;
        }
        FormDefine formDefine = this.runTimeViewController.queryFormById(formKey);
        if (formDefine == null) {
            return this.canAccess;
        }
        if (FormType.FORM_TYPE_NEWFMDM.equals((Object)formDefine.getFormType())) {
            return this.canAccess;
        }
        Integer childCount = this.getChileCountByFormScheme(formSchemeKey, masterKey);
        if (childCount != null && childCount > 0) {
            return new AccessCode(this.name(), "2");
        }
        return this.canAccess;
    }

    private Integer getChileCountByFormScheme(String formSchemeKey, DimensionCombination masterKey) {
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
        if (formScheme == null) {
            return null;
        }
        String dwDimensionName = this.dataAccesslUtil.getDwDimensionName(formScheme.getDw());
        Object value = masterKey.getValue(dwDimensionName);
        if (value == null) {
            return null;
        }
        Object periodValue = masterKey.getValue("DATATIME");
        DimensionValueSet deDimensionValueSet = new DimensionValueSet();
        if (periodValue != null) {
            deDimensionValueSet.setValue("DATATIME", periodValue);
        }
        try {
            IEntityTable entityTable = this.getiEntityTable(formScheme, deDimensionValueSet);
            return entityTable.getDirectChildCount(value.toString());
        }
        catch (Exception e) {
            logger.error(ERROR_INFO, e);
            return null;
        }
    }

    private Map<DimensionValueSet, Integer> getChileCountByFormScheme(String formSchemeKey, List<DimensionCombination> dimensionCombinations) {
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
        HashMap<DimensionValueSet, Integer> countMap = new HashMap<DimensionValueSet, Integer>();
        if (formScheme == null) {
            return countMap;
        }
        String entityId = this.dataAccesslUtil.contextEntityId(formScheme.getDw());
        IEntityDefine entityDefine = this.entityMetaService.queryEntity(entityId);
        if (entityDefine == null) {
            return countMap;
        }
        if (CollectionUtils.isEmpty(dimensionCombinations)) {
            return countMap;
        }
        Object period = dimensionCombinations.get(0).getValue("DATATIME");
        if (period == null) {
            throw new AccessException("\u7f3a\u5c11\u65f6\u671f\u7ef4\u5ea6");
        }
        DimensionValueSet deDimensionValueSet = new DimensionValueSet();
        String dwDimensionName = this.dataAccesslUtil.getDwDimensionName(entityId);
        try {
            HashSet<String> codes = new HashSet<String>();
            IEntityTable entityTable = this.getiEntityTable(formScheme, deDimensionValueSet);
            for (DimensionCombination dimensionCombination : dimensionCombinations) {
                Object value = dimensionCombination.getValue(dwDimensionName);
                if (value == null || codes.contains(value.toString())) continue;
                DimensionValueSet dimensionValueSet = new DimensionValueSet();
                codes.add(value.toString());
                dimensionValueSet.setValue(dwDimensionName, value);
                dimensionValueSet.setValue("DATATIME", period);
                int childCount = entityTable.getDirectChildCount(value.toString());
                countMap.put(dimensionValueSet, childCount);
            }
            return countMap;
        }
        catch (Exception e) {
            logger.error(ERROR_INFO, e);
            return countMap;
        }
    }

    @Override
    public AccessCode sysWriteable(String formSchemeKey, DimensionCombination masterKey, String formKey) {
        return this.canAccess;
    }

    @Override
    public IBatchAccess getBatchVisible(String formSchemeKey, DimensionCollection masterKeys, List<String> formKeys) {
        return (masterKey, formKey) -> this.canAccess;
    }

    @Override
    public IBatchAccess getBatchReadable(String formSchemeKey, DimensionCollection masterKeys, List<String> formKeys) {
        return (masterKey, formKey) -> this.canAccess;
    }

    @Override
    public IBatchAccess getBatchWriteable(String formSchemeKey, DimensionCollection masterKeys, List<String> formKeys) {
        if (CollectionUtils.isEmpty(formKeys)) {
            return (masterKey, formKey) -> new AccessCode(this.name());
        }
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
        if (formScheme == null) {
            return (masterKey, formKey) -> new AccessCode(this.name());
        }
        String entityId = this.dataAccesslUtil.contextEntityId(formScheme.getDw());
        IEntityDefine entityDefine = this.entityMetaService.queryEntity(entityId);
        if (entityDefine == null) {
            return (masterKey, formKey) -> new AccessCode(this.name());
        }
        String dimensionName = entityDefine.getDimensionName();
        List dimensionCombinations = masterKeys.getDimensionCombinations();
        if (CollectionUtils.isEmpty(dimensionCombinations)) {
            return (masterKey, formKey) -> new AccessCode(this.name());
        }
        Object period = ((DimensionCombination)dimensionCombinations.get(0)).getValue("DATATIME");
        DimensionValueSet deDimensionValueSet = new DimensionValueSet();
        if (period != null) {
            deDimensionValueSet.setValue("DATATIME", period);
        }
        HashMap<String, FormType> typeMap = new HashMap<String, FormType>();
        for (FormDefine formDefine : this.runTimeViewController.queryFormsById(formKeys)) {
            typeMap.put(formDefine.getKey(), formDefine.getFormType());
        }
        if (typeMap.isEmpty()) {
            return (masterKey, formKey) -> new AccessCode(this.name());
        }
        FormBatchAccessCache formBatchAccessCache = new FormBatchAccessCache(this.name(), formSchemeKey);
        formBatchAccessCache.setAccessType(AccessType.WRITE);
        HashMap<DimensionValueSet, Map<String, String>> cacheMap = new HashMap<DimensionValueSet, Map<String, String>>();
        try {
            IEntityTable entityTable = this.getiEntityTable(formScheme, deDimensionValueSet);
            for (DimensionCombination dimensionCombination : dimensionCombinations) {
                Object keyValue = dimensionCombination.getValue(dimensionName);
                if (keyValue == null) continue;
                Map formCodeMap = cacheMap.computeIfAbsent(dimensionCombination.toDimensionValueSet(), k -> new HashMap());
                int childCount = entityTable.getDirectChildCount(keyValue.toString());
                for (String formKey2 : formKeys) {
                    if (FormType.FORM_TYPE_NEWFMDM.equals(typeMap.get(formKey2))) {
                        formCodeMap.put(formKey2, "1");
                        continue;
                    }
                    formCodeMap.put(formKey2, childCount > 0 ? "2" : "1");
                }
            }
            formBatchAccessCache.setCacheMap(cacheMap);
            return formBatchAccessCache;
        }
        catch (Exception e) {
            logger.error(ERROR_INFO, e);
            return (masterKey, formKey) -> new AccessCode(this.name());
        }
    }

    private IEntityTable getiEntityTable(FormSchemeDefine formScheme, DimensionValueSet dimensionValueSet) throws Exception {
        String entityId = this.dataAccesslUtil.contextEntityId(formScheme.getDw());
        EntityViewDefine viewByFormSchemeKey = this.runTimeViewController.getViewByFormSchemeKey(formScheme.getKey());
        EntityViewDefine entityView = this.viewAdapter.buildEntityView(entityId, viewByFormSchemeKey.getRowFilterExpression());
        ReportFmlExecEnvironment reportFmlExecEnvironment = new ReportFmlExecEnvironment(this.runTimeViewController, this.tbRtCtl, this.viewAdapter, formScheme.getKey());
        IEntityQuery query = this.dataService.newEntityQuery();
        query.setEntityView(entityView);
        query.setMasterKeys(dimensionValueSet);
        ExecutorContext executorContext = new ExecutorContext(this.tbRtCtl);
        executorContext.setPeriodView(formScheme.getDateTime());
        executorContext.setEnv((IFmlExecEnvironment)reportFmlExecEnvironment);
        return query.executeReader((IContext)executorContext);
    }

    @Override
    public IBatchAccess getSysBatchWriteable(String formSchemeKey, DimensionCollection masterKeys, List<String> formKeys) {
        return (masterKey, formKey) -> this.canAccess;
    }

    @Override
    public AccessCode visible(IAccessFormMerge mergeParam) throws AccessException {
        return this.canAccess;
    }

    @Override
    public AccessCode readable(IAccessFormMerge mergeParam) throws AccessException {
        return this.canAccess;
    }

    @Override
    public AccessCode writeable(IAccessFormMerge mergeParam) throws AccessException {
        Set<IAccessForm> accessForms = mergeParam.getAccessForms();
        if (CollectionUtils.isEmpty(accessForms)) {
            return this.canAccess;
        }
        Set<String> taskKeys = mergeParam.getTaskKeys();
        HashSet<String> formSchemeKeySet = new HashSet<String>();
        for (String taskKey : taskKeys) {
            boolean enable = this.isEnable(taskKey, null);
            if (!enable) continue;
            Set<String> formSchemeKeys = mergeParam.getFormSchemeKeysByTasKey(taskKey);
            block1: for (String formSchemeKey : formSchemeKeys) {
                Set<IAccessForm> formSet = mergeParam.getAccessFormsByFormSchemeKey(formSchemeKey);
                for (IAccessForm iAccessForm : formSet) {
                    String formKey = iAccessForm.getFormKey();
                    FormDefine formDefine = this.runTimeViewController.queryFormById(formKey);
                    if (formDefine == null || formDefine.getFormType() == FormType.FORM_TYPE_NEWFMDM) continue;
                    formSchemeKeySet.add(formSchemeKey);
                    continue block1;
                }
            }
        }
        for (String formSchemeKey : formSchemeKeySet) {
            Integer childCount = this.getChileCountByFormScheme(formSchemeKey, mergeParam.getMasterKey());
            if (childCount == null || childCount <= 0) continue;
            return new AccessCode(this.name(), "2");
        }
        return this.canAccess;
    }

    @Override
    public AccessCode sysWriteable(IAccessFormMerge mergeParam) throws AccessException {
        return this.canAccess;
    }

    @Override
    public IBatchMergeAccess getBatchVisible(IBatchAccessFormMerge mergeParam) throws AccessException {
        return this.canAccessBatchMergeAccess;
    }

    @Override
    public IBatchMergeAccess getBatchReadable(IBatchAccessFormMerge mergeParam) throws AccessException {
        return this.canAccessBatchMergeAccess;
    }

    @Override
    public IBatchMergeAccess getBatchWriteable(IBatchAccessFormMerge mergeParam) throws AccessException {
        Set<String> taskKeys = mergeParam.getTaskKeys();
        if (CollectionUtils.isEmpty(taskKeys)) {
            return this.canAccessBatchMergeAccess;
        }
        HashMap<String, Map> resMap = new HashMap<String, Map>();
        HashMap<String, Boolean> enableMap = new HashMap<String, Boolean>();
        for (String taskKey : taskKeys) {
            boolean enable = this.isEnable(taskKey, null);
            enableMap.put(taskKey, enable);
            if (!enable) continue;
            DimensionCollection collection = mergeParam.getMasterKeysByTaskKey(taskKey);
            Set<String> formSchemeKeysByTasKey = mergeParam.getFormSchemeKeysByTasKey(taskKey);
            List dimensionCombinations = collection.getDimensionCombinations();
            for (String formSchemeKey : formSchemeKeysByTasKey) {
                Map<DimensionValueSet, Integer> map = this.getChileCountByFormScheme(formSchemeKey, dimensionCombinations);
                Map formScheme2ResMap = resMap.computeIfAbsent(formSchemeKey, k -> new HashMap());
                formScheme2ResMap.putAll(map);
            }
        }
        HashMap<IAccessFormMerge, AccessCode> codeMap = new HashMap<IAccessFormMerge, AccessCode>();
        List<IAccessFormMerge> accessFormMerges = mergeParam.getAccessFormMerges();
        block2: for (IAccessFormMerge accessFormMerge : accessFormMerges) {
            DimensionCombination masterKey = accessFormMerge.getMasterKey();
            DimensionValueSet set = masterKey.toDimensionValueSet();
            Set<String> tasks = accessFormMerge.getTaskKeys();
            for (String task : tasks) {
                if (!((Boolean)enableMap.get(task)).booleanValue()) continue;
                Set<String> formSchemeKeysByTasKey = accessFormMerge.getFormSchemeKeysByTasKey(task);
                for (String formSchemeKey : formSchemeKeysByTasKey) {
                    Map dimMap = (Map)resMap.get(formSchemeKey);
                    for (Map.Entry dimEntry : dimMap.entrySet()) {
                        Integer value = (Integer)dimEntry.getValue();
                        if (value == null || value <= 0 || !set.isSubsetOf((DimensionValueSet)dimEntry.getKey())) continue;
                        codeMap.put(accessFormMerge, new AccessCode(this.name(), "2"));
                        continue block2;
                    }
                }
            }
            codeMap.put(accessFormMerge, this.canAccess);
        }
        return new BatchMergeAccess(this.name(), codeMap);
    }

    @Override
    public IBatchMergeAccess getSysBatchWriteable(IBatchAccessFormMerge mergeParam) throws AccessException {
        return this.canAccessBatchMergeAccess;
    }
}

