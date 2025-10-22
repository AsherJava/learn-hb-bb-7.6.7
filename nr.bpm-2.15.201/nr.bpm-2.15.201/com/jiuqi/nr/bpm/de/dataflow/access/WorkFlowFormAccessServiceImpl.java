/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.data.access.common.WorkflowState
 *  com.jiuqi.nr.data.access.exception.AccessException
 *  com.jiuqi.nr.data.access.param.AccessCode
 *  com.jiuqi.nr.data.access.param.FormBatchAccessCache
 *  com.jiuqi.nr.data.access.param.IAccessMessage
 *  com.jiuqi.nr.data.access.param.IBatchAccess
 *  com.jiuqi.nr.data.access.util.DimensionValueSetUtil
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormGroupDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskFlowsDefine
 *  com.jiuqi.nr.definition.internal.impl.FlowsType
 *  com.jiuqi.nr.definition.internal.impl.WorkFlowType
 *  com.jiuqi.nr.workflow2.engine.core.settings.utils.DefaultEngineVersionJudge
 */
package com.jiuqi.nr.bpm.de.dataflow.access;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.bpm.common.UploadStateNew;
import com.jiuqi.nr.bpm.dataflow.service.IQueryUploadStateService;
import com.jiuqi.nr.bpm.de.dataflow.access.WorklfowAccessService;
import com.jiuqi.nr.bpm.de.dataflow.util.DimensionUtil;
import com.jiuqi.nr.data.access.common.WorkflowState;
import com.jiuqi.nr.data.access.exception.AccessException;
import com.jiuqi.nr.data.access.param.AccessCode;
import com.jiuqi.nr.data.access.param.FormBatchAccessCache;
import com.jiuqi.nr.data.access.param.IAccessMessage;
import com.jiuqi.nr.data.access.param.IBatchAccess;
import com.jiuqi.nr.data.access.util.DimensionValueSetUtil;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormGroupDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskFlowsDefine;
import com.jiuqi.nr.definition.internal.impl.FlowsType;
import com.jiuqi.nr.definition.internal.impl.WorkFlowType;
import com.jiuqi.nr.workflow2.engine.core.settings.utils.DefaultEngineVersionJudge;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

@Service
public class WorkFlowFormAccessServiceImpl
extends WorklfowAccessService {
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private IQueryUploadStateService queryUploadStateService;
    @Autowired
    private DimensionUtil dimenUtil;
    @Autowired
    private DefaultEngineVersionJudge defaultEngineVersionJudge;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public int getOrder() {
        return 8;
    }

    public String name() {
        return "upload-form";
    }

    public boolean isEnable(String taskKey, String formSchemeKey) {
        if (!this.defaultEngineVersionJudge.isTaskAndEngineVersion_1_0(taskKey)) {
            return false;
        }
        Assert.notNull((Object)formSchemeKey, "formSchemeKey is must not be null!");
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
        TaskFlowsDefine taskFlowsDefine = formScheme.getFlowsSetting();
        FlowsType flowsType = taskFlowsDefine.getFlowsType();
        if (FlowsType.NOSTARTUP.equals((Object)flowsType)) {
            return false;
        }
        WorkFlowType workFlowType = taskFlowsDefine.getWordFlowType();
        return workFlowType != WorkFlowType.ENTITY;
    }

    public List<String> getCodeList() {
        return new ArrayList<String>(noAccessReasonMap.keySet());
    }

    public AccessCode visible(String formSchemeKey, DimensionCombination masterKey, String formKey) {
        Assert.notNull((Object)formSchemeKey, "formSchemeKey is must not be null!");
        Assert.notNull((Object)masterKey, "masterKey is must not be null!");
        return this.canAccess;
    }

    public AccessCode readable(String formSchemeKey, DimensionCombination masterKey, String formKey) {
        Assert.notNull((Object)formSchemeKey, "formSchemeKey is must not be null!");
        Assert.notNull((Object)masterKey, "masterKey is must not be null!");
        return this.canAccess;
    }

    public AccessCode writeable(String formSchemeKey, DimensionCombination masterKey, String formKey) {
        Assert.notNull((Object)formSchemeKey, "formSchemeKey is must not be null!");
        Assert.notNull((Object)masterKey, "masterKey is must not be null!");
        WorkFlowType workFlowType = this.getWorkFlowType(formSchemeKey);
        formKey = workFlowType == WorkFlowType.FORM ? formKey : this.getFormGroupKey(formKey);
        return this.write(formSchemeKey, masterKey.toDimensionValueSet(), formKey);
    }

    public IAccessMessage getAccessMessage() {
        return code -> (String)this.noAccessReason.apply(code);
    }

    public IBatchAccess getBatchVisible(String formSchemeKey, DimensionCollection masterKeys, List<String> formKeys) {
        Assert.notNull((Object)formSchemeKey, "formSchemeKey is must not be null!");
        Assert.notNull((Object)masterKeys, "masterKeys is must not be null!");
        return (masterKey, formKey) -> this.canAccess;
    }

    public IBatchAccess getBatchReadable(String formSchemeKey, DimensionCollection masterKeys, List<String> formKeys) {
        Assert.notNull((Object)formSchemeKey, "formSchemeKey is must not be null!");
        Assert.notNull((Object)masterKeys, "masterKeys is must not be null!");
        return (masterKey, formKey) -> this.canAccess;
    }

    public IBatchAccess getBatchWriteable(String formSchemeKey, DimensionCollection masterKeys, List<String> formKeys) {
        Assert.notNull((Object)formSchemeKey, "formSchemeKey is must not be null!");
        Assert.notNull((Object)masterKeys, "masterKeys is must not be null!");
        WorkFlowType workFlowType = this.getWorkFlowType(formSchemeKey);
        Map<String, Set<String>> map = null;
        if (workFlowType == WorkFlowType.GROUP) {
            map = this.getFormGroupKey(formKeys);
            formKeys = new ArrayList<String>(map.keySet());
        }
        FormBatchAccessCache cacheAccess = new FormBatchAccessCache(this.name(), formSchemeKey);
        this.initFormCache(cacheAccess, formSchemeKey, masterKeys, formKeys, map);
        return cacheAccess;
    }

    private void initFormCache(FormBatchAccessCache cacheAccess, String formSchemeKey, DimensionCollection masterKeys, List<String> formKeys, Map<String, Set<String>> map) {
        HashMap<DimensionValueSet, Map<String, String>> cacheMap = new HashMap<DimensionValueSet, Map<String, String>>();
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
        try {
            DimensionValueSet mergeDimensionValueSet = DimensionValueSetUtil.mergeDimensionValueSet((DimensionCollection)masterKeys);
            List dimensionSetList = DimensionValueSetUtil.toDimensionValueSetList((DimensionCollection)masterKeys);
            String dwMainDimName = this.dimenUtil.getDwMainDimName(formSchemeKey);
            List<UploadStateNew> uploadStates = this.queryUploadStateService.queryUploadStates(formSchemeKey, mergeDimensionValueSet, formKeys, formKeys);
            if (uploadStates != null && uploadStates.size() > 0) {
                for (UploadStateNew uploadStateNew : uploadStates) {
                    String taskId = uploadStateNew.getTaskId();
                    String preEvent = uploadStateNew.getPreEvent();
                    DimensionValueSet entities = uploadStateNew.getEntities();
                    entities.clearValue("FORMID");
                    String formId = uploadStateNew.getFormId();
                    WorkflowState workFlowState = this.workFlowState(preEvent, taskId, formScheme);
                    Object dw1 = entities.getValue(dwMainDimName);
                    Object datatime1 = entities.getValue("DATATIME");
                    for (DimensionValueSet dimension : dimensionSetList) {
                        String code;
                        Object datatime2 = dimension.getValue("DATATIME");
                        Object dw2 = dimension.getValue(dwMainDimName);
                        if (!Objects.equals(dw1, dw2) || !Objects.equals(datatime1, datatime2)) continue;
                        HashMap<String, String> valueMap = (HashMap<String, String>)cacheMap.get(dimension);
                        if (Objects.isNull(valueMap)) {
                            valueMap = new HashMap<String, String>();
                            cacheMap.put(dimension, valueMap);
                        }
                        if ("1".equals(code = (String)this.accessCodeCompute.apply(String.valueOf(workFlowState.getValue())))) continue;
                        if (map != null) {
                            Set<String> forms = map.get(formId);
                            if (forms == null) continue;
                            for (String formKey : forms) {
                                valueMap.put(formKey, code);
                            }
                            continue;
                        }
                        valueMap.put(formId, code);
                    }
                }
            }
            cacheAccess.setCacheMap(this.clearEmptyValue(cacheMap));
        }
        catch (Exception e) {
            this.logger.error("\u72b6\u6001\u5f15\u64ce-\u6d41\u7a0b\u53ef\u5199\u72b6\u6001\u67e5\u8be2\u5931\u8d25", e);
            throw new AccessException("\u6d41\u7a0b\u53ef\u8bfb\u72b6\u6001\u67e5\u8be2\u5931\u8d25", (Throwable)e);
        }
    }

    private AccessCode write(String formSchemeKey, DimensionValueSet masterKey, String formKey) {
        AccessCode accessCode = new AccessCode(this.name());
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
        DimensionValueSet dimensionValueSet = this.dimenUtil.fliterDimensionValueSet(masterKey, formScheme);
        try {
            UploadStateNew uploadState = this.queryUploadStateService.queryUploadState(formSchemeKey, dimensionValueSet, formKey);
            if (uploadState != null && uploadState.getTaskId() != null) {
                String taskId = uploadState.getTaskId();
                String preEvent = uploadState.getPreEvent();
                WorkflowState workFlowState = this.workFlowState(preEvent, taskId, formScheme);
                accessCode = new AccessCode(this.name(), (String)this.accessCodeCompute.apply(String.valueOf(workFlowState.getValue())));
            }
        }
        catch (Exception e) {
            this.logger.error("\u72b6\u6001\u5f15\u64ce-\u6d41\u7a0b\u53ef\u5199\u72b6\u6001\u67e5\u8be2\u5931\u8d25", e);
            StringBuilder logBuilder = new StringBuilder();
            logBuilder.append("\u6d41\u7a0b\u53ef\u8bfb\u72b6\u6001\u67e5\u8be2\u5931\u8d25\uff01");
            logBuilder.append("formSchemeKey:" + formSchemeKey);
            logBuilder.append(",masterKey:").append(dimensionValueSet);
            logBuilder.append(",formKey:").append(formKey);
            throw new AccessException(logBuilder.toString(), (Throwable)e);
        }
        return accessCode;
    }

    private Map<String, Set<String>> getFormGroupKey(List<String> formKeys) {
        HashMap<String, Set<String>> group2FormKeys = new HashMap<String, Set<String>>();
        for (String formKey : formKeys) {
            List groups = this.runTimeViewController.getFormGroupsByFormKey(formKey);
            for (FormGroupDefine group : groups) {
                HashSet<String> formKeySet = (HashSet<String>)group2FormKeys.get(group.getKey());
                if (formKeySet == null) {
                    formKeySet = new HashSet<String>();
                    group2FormKeys.put(group.getKey(), formKeySet);
                }
                formKeySet.add(formKey);
            }
        }
        return group2FormKeys;
    }

    private String getFormGroupKey(String formKey) {
        List group = this.runTimeViewController.getFormGroupsByFormKey(formKey);
        if (!CollectionUtils.isEmpty(group)) {
            return ((FormGroupDefine)group.stream().findFirst().get()).getKey();
        }
        return formKey;
    }

    private WorkFlowType getWorkFlowType(String formSchemeKey) {
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
        WorkFlowType workFlowType = formScheme.getFlowsSetting().getWordFlowType();
        return workFlowType;
    }
}

