/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.context.cxt.DsContext
 *  com.jiuqi.nr.context.cxt.DsContextHolder
 *  com.jiuqi.nr.context.cxt.Extension
 *  com.jiuqi.nr.data.access.common.WorkflowState
 *  com.jiuqi.nr.data.access.exception.AccessException
 *  com.jiuqi.nr.data.access.param.AccessCode
 *  com.jiuqi.nr.data.access.param.BatchMergeAccess
 *  com.jiuqi.nr.data.access.param.CanAccessBatchMergeAccess
 *  com.jiuqi.nr.data.access.param.IAccessForm
 *  com.jiuqi.nr.data.access.param.IAccessFormMerge
 *  com.jiuqi.nr.data.access.param.IBatchAccess
 *  com.jiuqi.nr.data.access.param.IBatchAccessFormMerge
 *  com.jiuqi.nr.data.access.param.IBatchMergeAccess
 *  com.jiuqi.nr.data.access.service.IDataAccessItemService
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.definition.api.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 */
package com.jiuqi.nr.bpm.de.dataflow.access;

import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.bpm.upload.UploadState;
import com.jiuqi.nr.context.cxt.DsContext;
import com.jiuqi.nr.context.cxt.DsContextHolder;
import com.jiuqi.nr.context.cxt.Extension;
import com.jiuqi.nr.data.access.common.WorkflowState;
import com.jiuqi.nr.data.access.exception.AccessException;
import com.jiuqi.nr.data.access.param.AccessCode;
import com.jiuqi.nr.data.access.param.BatchMergeAccess;
import com.jiuqi.nr.data.access.param.CanAccessBatchMergeAccess;
import com.jiuqi.nr.data.access.param.IAccessForm;
import com.jiuqi.nr.data.access.param.IAccessFormMerge;
import com.jiuqi.nr.data.access.param.IBatchAccess;
import com.jiuqi.nr.data.access.param.IBatchAccessFormMerge;
import com.jiuqi.nr.data.access.param.IBatchMergeAccess;
import com.jiuqi.nr.data.access.service.IDataAccessItemService;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.definition.api.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.util.CollectionUtils;

public abstract class WorklfowAccessService
implements IDataAccessItemService {
    protected static final String NOACCESS_PREFIX = "NO_AC_";
    protected final AccessCode canAccess = new AccessCode(this.name());
    protected final CanAccessBatchMergeAccess canAccessBatchMergeAccess = new CanAccessBatchMergeAccess(this.name());
    protected IRunTimeViewController runTimeViewController;
    protected static Map<String, String> noAccessReasonMap = new HashMap<String, String>(){
        {
            this.put(WorklfowAccessService.NOACCESS_PREFIX + WorkflowState.SUBMITED.getValue(), "\u6570\u636e\u5df2\u9001\u5ba1\u4e0d\u53ef\u7f16\u8f91");
            this.put(WorklfowAccessService.NOACCESS_PREFIX + WorkflowState.UPLOADED.getValue(), "\u6570\u636e\u5df2\u4e0a\u62a5\u4e0d\u53ef\u7f16\u8f91");
            this.put(WorklfowAccessService.NOACCESS_PREFIX + WorkflowState.CONFIRMED.getValue(), "\u6570\u636e\u5df2\u786e\u8ba4\u4e0d\u53ef\u7f16\u8f91");
        }
    };
    protected Function<String, String> noAccessReason = code -> Optional.ofNullable(code).filter(e -> e.equals("1")).orElse(noAccessReasonMap.get(code));
    protected Function<String, String> accessCodeCompute = value -> {
        switch (value) {
            case "1": {
                if (this.isIgnore()) {
                    return "1";
                }
                return NOACCESS_PREFIX + WorkflowState.SUBMITED.getValue();
            }
            case "2": {
                if (this.isIgnore()) {
                    return "1";
                }
                return NOACCESS_PREFIX + WorkflowState.UPLOADED.getValue();
            }
            case "3": {
                if (this.isIgnore()) {
                    return "1";
                }
                return NOACCESS_PREFIX + WorkflowState.CONFIRMED.getValue();
            }
            case "UPLOAD_TIME_END": {
                if (this.isIgnore()) {
                    return "1";
                }
                return "NO_AC_UPLOAD_TIME_END";
            }
            case "UPLOAD_TIME_NOT_START": {
                if (this.isIgnore()) {
                    return "1";
                }
                return "NO_AC_UPLOAD_TIME_NOT_START";
            }
        }
        return "1";
    };

    public String group() {
        return "upload";
    }

    protected Map<DimensionValueSet, Map<String, String>> clearEmptyValue(Map<DimensionValueSet, Map<String, String>> cacheMap) {
        Set distinctKey = cacheMap.keySet().stream().filter(key -> !CollectionUtils.isEmpty((Map)cacheMap.get(key))).collect(Collectors.toSet());
        HashMap<DimensionValueSet, Map<String, String>> tmpCacheMap = new HashMap<DimensionValueSet, Map<String, String>>();
        cacheMap.forEach((k, v) -> {
            if (distinctKey.contains(k)) {
                tmpCacheMap.put((DimensionValueSet)k, (Map<String, String>)v);
            }
        });
        return tmpCacheMap;
    }

    protected WorkflowState workFlowState(String action, String task, FormSchemeDefine formSchemeDefine) {
        switch (action) {
            case "start": {
                return WorkflowState.ORIGINAL_UPLOAD;
            }
            case "act_upload": 
            case "cus_upload": 
            case "act_cancel_confirm": {
                return WorkflowState.UPLOADED;
            }
            case "act_submit": 
            case "cus_submit": {
                return WorkflowState.SUBMITED;
            }
            case "act_reject": 
            case "cus_reject": {
                return WorkflowState.REJECTED;
            }
            case "act_confirm": 
            case "cus_confirm": {
                return WorkflowState.CONFIRMED;
            }
            case "act_return": 
            case "cus_return": {
                return WorkflowState.RETURNED;
            }
            case "act_retrieve": {
                if ("tsk_upload".equals(task)) {
                    return this.retrieveState(formSchemeDefine);
                }
                if ("tsk_submit".equals(task) || "tsk_start".equals(task)) {
                    return WorkflowState.ORIGINAL_UPLOAD;
                }
                if (!"tsk_audit".equals(task)) break;
                return WorkflowState.UPLOADED;
            }
        }
        return null;
    }

    protected WorkflowState retrieveState(FormSchemeDefine formScheme) {
        boolean submit = formScheme.getFlowsSetting().isUnitSubmitForCensorship();
        if (submit) {
            return WorkflowState.SUBMITED;
        }
        return WorkflowState.ORIGINAL_UPLOAD;
    }

    public AccessCode visible(IAccessFormMerge mergeParam) throws AccessException {
        return this.canAccess;
    }

    public AccessCode readable(IAccessFormMerge mergeParam) throws AccessException {
        return this.canAccess;
    }

    public IBatchMergeAccess getBatchVisible(IBatchAccessFormMerge mergeParam) throws AccessException {
        return this.canAccessBatchMergeAccess;
    }

    public IBatchMergeAccess getBatchReadable(IBatchAccessFormMerge mergeParam) throws AccessException {
        return this.canAccessBatchMergeAccess;
    }

    public AccessCode writeable(IAccessFormMerge mergeParam) throws AccessException {
        if (this.runTimeViewController == null) {
            this.runTimeViewController = (IRunTimeViewController)SpringBeanUtils.getBean(IRunTimeViewController.class);
        }
        Set formSchemeKeys = mergeParam.getFormSchemeKeys();
        for (String formSchemeKey : formSchemeKeys) {
            FormSchemeDefine formSchemeDefine = this.runTimeViewController.getFormScheme(formSchemeKey);
            if (!this.isEnable(formSchemeDefine.getTaskKey(), formSchemeKey)) continue;
            Set forms = mergeParam.getAccessFormsByFormSchemeKey(formSchemeKey);
            for (IAccessForm form : forms) {
                AccessCode writeable = this.writeable(formSchemeKey, mergeParam.getMasterKey(), form.getFormKey());
                if ("1".equals(writeable.getCode())) continue;
                return writeable;
            }
        }
        return this.canAccess;
    }

    public IBatchMergeAccess getBatchWriteable(IBatchAccessFormMerge mergeParam) throws AccessException {
        List accessFormMerges = mergeParam.getAccessFormMerges();
        if (CollectionUtils.isEmpty(accessFormMerges)) {
            return this.canAccessBatchMergeAccess;
        }
        Set taskKeys = mergeParam.getTaskKeys();
        HashMap<String, IBatchAccess> iBatchAccessMap = new HashMap<String, IBatchAccess>();
        for (String taskKey : taskKeys) {
            Set formSchemeKeysByTasKey = mergeParam.getFormSchemeKeysByTasKey(taskKey);
            if (CollectionUtils.isEmpty(formSchemeKeysByTasKey)) continue;
            DimensionCollection masterKeysByTaskKey = mergeParam.getMasterKeysByTaskKey(taskKey);
            Set formsByTaskKey = mergeParam.getAccessFormsByTaskKey(taskKey);
            List forms = formsByTaskKey.stream().map(IAccessForm::getFormKey).collect(Collectors.toList());
            for (String formSchemeKey : formSchemeKeysByTasKey) {
                if (!this.isEnable(taskKey, formSchemeKey)) continue;
                IBatchAccess batchWriteable = this.getBatchWriteable(formSchemeKey, masterKeysByTaskKey, forms);
                iBatchAccessMap.put(formSchemeKey, batchWriteable);
            }
        }
        HashMap<IAccessFormMerge, AccessCode> codeMap = new HashMap<IAccessFormMerge, AccessCode>();
        block2: for (IAccessFormMerge accessFormMerge : accessFormMerges) {
            Set accTasks = accessFormMerge.getTaskKeys();
            for (String accTask : accTasks) {
                Set formSchemeKeysByTasKey = accessFormMerge.getFormSchemeKeysByTasKey(accTask);
                for (String formSchemeKey : formSchemeKeysByTasKey) {
                    if (!this.isEnable(accTask, formSchemeKey)) continue;
                    Set accessFormsByFormSchemeKey = accessFormMerge.getAccessFormsByFormSchemeKey(formSchemeKey);
                    IBatchAccess iBatchAccess = (IBatchAccess)iBatchAccessMap.get(formSchemeKey);
                    for (IAccessForm iAccessForm : accessFormsByFormSchemeKey) {
                        AccessCode accessCode = iBatchAccess.getAccessCode(accessFormMerge.getMasterKey(), iAccessForm.getFormKey());
                        if ("1".equals(accessCode.getCode())) continue;
                        codeMap.put(accessFormMerge, new AccessCode(this.name(), accessCode.getCode()));
                        continue block2;
                    }
                }
            }
            codeMap.put(accessFormMerge, this.canAccess);
        }
        return new BatchMergeAccess(this.name(), codeMap);
    }

    private boolean isIgnore() {
        String prefix = "IGNORE_WORKFLOW_STATE_ITEM";
        DsContext dsContext = DsContextHolder.getDsContext();
        Extension extension = dsContext.getExtension();
        Set keys = extension.getKeys();
        return keys.contains(prefix + "_" + UploadState.CONFIRMED.toString()) || keys.contains(prefix + "_" + UploadState.REJECTED.toString()) || keys.contains(prefix + "_" + UploadState.UPLOADED.toString()) || keys.contains(prefix + "_" + UploadState.SUBMITED.toString()) || keys.contains(prefix + "_" + UploadState.ORIGINAL_UPLOAD.toString());
    }
}

