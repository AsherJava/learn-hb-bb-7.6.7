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
 *  com.jiuqi.nr.data.access.util.DimCollectionBuildUtil
 *  com.jiuqi.nr.data.access.util.DimensionValueSetUtil
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
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
import com.jiuqi.nr.bpm.de.dataflow.service.impl.SingleRejectFormActions;
import com.jiuqi.nr.bpm.de.dataflow.util.DimensionUtil;
import com.jiuqi.nr.data.access.common.WorkflowState;
import com.jiuqi.nr.data.access.exception.AccessException;
import com.jiuqi.nr.data.access.param.AccessCode;
import com.jiuqi.nr.data.access.param.FormBatchAccessCache;
import com.jiuqi.nr.data.access.param.IAccessMessage;
import com.jiuqi.nr.data.access.param.IBatchAccess;
import com.jiuqi.nr.data.access.util.DimCollectionBuildUtil;
import com.jiuqi.nr.data.access.util.DimensionValueSetUtil;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
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
public class WorkFlowUnitAccessServiceImpl
extends WorklfowAccessService {
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private DimCollectionBuildUtil dimCollectionBuildUtil;
    @Autowired
    private IQueryUploadStateService queryUploadStateService;
    @Autowired
    public SingleRejectFormActions singleRejectFormActions;
    @Autowired
    private DimensionUtil dimenUtil;
    @Autowired
    private DefaultEngineVersionJudge defaultEngineVersionJudge;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public String name() {
        return "upload-unit";
    }

    public int getOrder() {
        return 8;
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
        WorkFlowType workFlowType = formScheme.getFlowsSetting().getWordFlowType();
        return workFlowType == WorkFlowType.ENTITY;
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
        FormBatchAccessCache cacheAccess = new FormBatchAccessCache(this.name(), formSchemeKey);
        this.initUnitCache(cacheAccess, formSchemeKey, masterKeys, formKeys);
        return cacheAccess;
    }

    private void initUnitCache(FormBatchAccessCache cacheAccess, String formSchemeKey, DimensionCollection masterKeys, List<String> formKeys) {
        Map cacheMap = cacheAccess.getCacheMap();
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
        boolean allowFormBack = formScheme.getFlowsSetting().isAllowFormBack();
        try {
            Map<Object, Object> accessMap = new HashMap();
            DimensionValueSet mergeDimensionValueSet = DimensionValueSetUtil.mergeDimensionValueSet((DimensionCollection)masterKeys);
            List dimensionSetList = DimensionValueSetUtil.toDimensionValueSetList((DimensionCollection)masterKeys);
            List<UploadStateNew> uploadStates = this.queryUploadStateService.queryUploadStates(formSchemeKey, mergeDimensionValueSet, formKeys, formKeys);
            if (uploadStates != null && uploadStates.size() > 0) {
                String dwMainDimName = this.dimenUtil.getDwMainDimName(formSchemeKey);
                for (UploadStateNew uploadStateNew : uploadStates) {
                    String taskId = uploadStateNew.getTaskId();
                    String preEvent = uploadStateNew.getPreEvent();
                    DimensionValueSet entities = uploadStateNew.getEntities();
                    Object dw1 = entities.getValue(dwMainDimName);
                    Object datatime1 = entities.getValue("DATATIME");
                    for (DimensionValueSet dim : dimensionSetList) {
                        HashMap<String, String> valueMap;
                        Object datatime2 = dim.getValue("DATATIME");
                        Object dw2 = dim.getValue(dwMainDimName);
                        if (!Objects.equals(dw1, dw2) || !Objects.equals(datatime1, datatime2)) continue;
                        entities.clearValue("FORMID");
                        WorkflowState workFlowState = this.workFlowState(preEvent, taskId, formScheme);
                        String code = (String)this.accessCodeCompute.apply(String.valueOf(workFlowState.getValue()));
                        if (allowFormBack) {
                            DimensionCollection dimCollection = this.dimCollectionBuildUtil.buildDimensionCollection(entities, formSchemeKey);
                            accessMap = this.batchAccess(formScheme, dimCollection, formKeys);
                        }
                        if (Objects.isNull(valueMap = (HashMap<String, String>)cacheMap.get(dim))) {
                            valueMap = new HashMap<String, String>();
                            cacheMap.put(dim, valueMap);
                        }
                        if ("1".equals(code)) continue;
                        if (!CollectionUtils.isEmpty(accessMap)) {
                            for (String formKey : formKeys) {
                                String accessCode = (String)accessMap.get(formKey);
                                if ("1".equals(accessCode)) continue;
                                valueMap.put(formKey, code);
                            }
                            continue;
                        }
                        for (String formKey : formKeys) {
                            valueMap.put(formKey, code);
                        }
                    }
                }
            }
            cacheAccess.setCacheMap(this.clearEmptyValue(cacheMap));
        }
        catch (Exception e) {
            this.logger.error("\u6d41\u7a0b\u53ef\u8bfb\u72b6\u6001\u67e5\u8be2\u5931\u8d25", e);
            throw new AccessException("\u6d41\u7a0b\u53ef\u8bfb\u72b6\u6001\u67e5\u8be2\u5931\u8d25", (Throwable)e);
        }
    }

    private AccessCode write(String formSchemeKey, DimensionValueSet masterKey, String formKey) {
        AccessCode accessCode = new AccessCode(this.name());
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
        boolean allowFormBack = formScheme.getFlowsSetting().isAllowFormBack();
        try {
            DimensionValueSet dimensionValueSet = this.dimenUtil.fliterDimensionValueSet(masterKey, formScheme);
            UploadStateNew uploadState = this.queryUploadStateService.queryUploadState(formSchemeKey, dimensionValueSet, formKey);
            if (uploadState != null && uploadState.getTaskId() != null) {
                String taskId = uploadState.getTaskId();
                String preEvent = uploadState.getPreEvent();
                WorkflowState workFlowState = this.workFlowState(preEvent, taskId, formScheme);
                String accCode = (String)this.accessCodeCompute.apply(String.valueOf(workFlowState.getValue()));
                if (this.getCodeList().contains(accCode)) {
                    String code = "";
                    if (allowFormBack) {
                        code = this.access(formScheme, masterKey, formKey);
                    } else {
                        accessCode = new AccessCode(this.name(), accCode);
                    }
                    if (!code.equals("1") || this.getCodeList().contains(code)) {
                        accessCode = new AccessCode(this.name(), accCode);
                    }
                } else {
                    accessCode = new AccessCode(this.name());
                }
            }
        }
        catch (Exception e) {
            this.logger.error("\u72b6\u6001\u5f15\u64ce-\u6d41\u7a0b\u53ef\u5199\u72b6\u6001\u67e5\u8be2\u5931\u8d25", e);
            StringBuilder logBuilder = new StringBuilder();
            logBuilder.append("\u6d41\u7a0b\u53ef\u8bfb\u72b6\u6001\u67e5\u8be2\u5931\u8d25\uff01");
            logBuilder.append("formSchemeKey:" + formSchemeKey);
            logBuilder.append(",masterKey:").append(masterKey);
            logBuilder.append(",formKey:").append(formKey);
            throw new AccessException(logBuilder.toString(), (Throwable)e);
        }
        return accessCode;
    }

    public String access(FormSchemeDefine formScheme, DimensionValueSet masterKey, String formKey) {
        boolean write = this.singleRejectFormActions.reportReadOnly(formScheme, masterKey, formKey);
        if (write) {
            return "1";
        }
        return "2";
    }

    public Map<String, String> batchAccess(FormSchemeDefine formScheme, DimensionCollection masterKey, List<String> formKeys) {
        Assert.notNull((Object)formScheme, "formSchemeKey is must not be null!");
        Assert.notNull((Object)masterKey, "masterKey is must not be null!");
        HashMap<String, String> result = new HashMap<String, String>();
        Set<String> rejectFormKeys = this.queryRejectFormKeys(formScheme, masterKey);
        formKeys.stream().forEach(key -> {
            if (rejectFormKeys != null && rejectFormKeys.size() > 0) {
                if (rejectFormKeys.contains(key)) {
                    result.put((String)key, "1");
                } else {
                    result.put((String)key, "2");
                }
            } else {
                result.put((String)key, "2");
            }
        });
        return result;
    }

    private Set<String> queryRejectFormKeys(FormSchemeDefine formScheme, DimensionCollection masterKey) {
        ArrayList<DimensionValueSet> dims = new ArrayList<DimensionValueSet>();
        if (!formScheme.getFlowsSetting().getDesignFlowSettingDefine().isAllowFormBack()) {
            return new HashSet<String>();
        }
        List dimensionCombinations = masterKey.getDimensionCombinations();
        for (DimensionCombination dimensionCombination : dimensionCombinations) {
            DimensionValueSet dimensionValueSet = dimensionCombination.toDimensionValueSet();
            dims.add(dimensionValueSet);
        }
        DimensionValueSet dimensionValueSet = DimensionValueSetUtil.mergeDimensionValueSet(dims);
        return this.singleRejectFormActions.queryFormKeysByAction(dimensionValueSet, formScheme.getKey(), "single_form_reject");
    }
}

