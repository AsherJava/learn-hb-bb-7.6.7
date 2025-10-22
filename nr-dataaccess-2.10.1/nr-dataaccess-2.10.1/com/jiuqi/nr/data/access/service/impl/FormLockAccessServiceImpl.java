/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.definition.controller.ITaskOptionController
 *  org.apache.shiro.util.Assert
 *  org.apache.shiro.util.CollectionUtils
 */
package com.jiuqi.nr.data.access.service.impl;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.data.access.api.IStateFormLockService;
import com.jiuqi.nr.data.access.api.param.LockParam;
import com.jiuqi.nr.data.access.common.FormLockAuthType;
import com.jiuqi.nr.data.access.exception.AccessException;
import com.jiuqi.nr.data.access.param.AccessCode;
import com.jiuqi.nr.data.access.param.BatchMergeAccess;
import com.jiuqi.nr.data.access.param.CanAccessBatchMergeAccess;
import com.jiuqi.nr.data.access.param.FormLockBatchAccess;
import com.jiuqi.nr.data.access.param.FormLockBatchReadWriteResult;
import com.jiuqi.nr.data.access.param.IAccessForm;
import com.jiuqi.nr.data.access.param.IAccessFormMerge;
import com.jiuqi.nr.data.access.param.IAccessMessage;
import com.jiuqi.nr.data.access.param.IBatchAccess;
import com.jiuqi.nr.data.access.param.IBatchAccessFormMerge;
import com.jiuqi.nr.data.access.param.IBatchMergeAccess;
import com.jiuqi.nr.data.access.service.IDataAccessItemService;
import com.jiuqi.nr.data.access.util.DimCollectionBuildUtil;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.definition.controller.ITaskOptionController;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.shiro.util.Assert;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FormLockAccessServiceImpl
implements IDataAccessItemService {
    @Autowired
    private ITaskOptionController taskOptionController;
    @Autowired
    private IStateFormLockService formLockService;
    @Autowired
    DimCollectionBuildUtil dimCollectionBuildUtil;
    private final AccessCode canAccess = new AccessCode(this.name());
    private final CanAccessBatchMergeAccess canAccessBatchMergeAccess = new CanAccessBatchMergeAccess(this.name());
    private static final String ASSERTINFO1 = "formSchemeKey is must not be null!";
    private static final String ASSERTINFO2 = "masterKey is must not be null!";
    private static final String ASSERTINFO3 = "masterKeys is must not be null!";
    private final Function<String, String> noAccessReason = code -> Optional.ofNullable(code).filter(e -> e.equals("1")).orElse("\u62a5\u8868\u88ab\u9501\u5b9a\u4e0d\u53ef\u7f16\u8f91");

    @Override
    public String name() {
        return "formLock";
    }

    @Override
    public int getOrder() {
        return 5;
    }

    @Override
    public boolean isEnable(String taskKey, String formSchemeKey) {
        Assert.notNull((Object)taskKey, (String)"taskKey is must not be null!");
        String formLockObj = this.taskOptionController.getValue(taskKey, "FORM_LOCK");
        return FormLockAuthType.getTypeByCode(formLockObj) != FormLockAuthType.DISABLED;
    }

    @Override
    public List<String> getCodeList() {
        return Arrays.asList("2");
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
        Assert.notNull((Object)formSchemeKey, (String)ASSERTINFO1);
        Assert.notNull((Object)masterKey, (String)ASSERTINFO2);
        Assert.notNull((Object)formKey, (String)"formKey is must not be null!");
        AccessCode accessCode = new AccessCode(this.name());
        LockParam lockParam = new LockParam();
        lockParam.setFormKeys(Stream.of(formKey).collect(Collectors.toList()));
        lockParam.setFormSchemeKey(formSchemeKey);
        DimensionCollection dimCollection = this.dimCollectionBuildUtil.buildCollectionByCombination(masterKey);
        lockParam.setMasterKeys(dimCollection);
        boolean formLocked = this.formLockService.isFormLocked(lockParam);
        if (!formLocked) {
            return accessCode;
        }
        accessCode = new AccessCode(this.name(), "2");
        return accessCode;
    }

    @Override
    public IAccessMessage getAccessMessage() {
        return code -> this.noAccessReason.apply(code);
    }

    @Override
    public IBatchAccess getBatchVisible(String formSchemeKey, DimensionCollection masterKeys, List<String> formKeys) {
        Assert.notNull((Object)formSchemeKey, (String)ASSERTINFO1);
        Assert.notNull((Object)masterKeys, (String)ASSERTINFO3);
        return (masterKey, formKey) -> new AccessCode(this.name());
    }

    @Override
    public IBatchAccess getBatchReadable(String formSchemeKey, DimensionCollection masterKeys, List<String> formKeys) {
        Assert.notNull((Object)formSchemeKey, (String)ASSERTINFO1);
        Assert.notNull((Object)masterKeys, (String)ASSERTINFO3);
        return (masterKey, formKey) -> new AccessCode(this.name());
    }

    @Override
    public IBatchAccess getBatchWriteable(String formSchemeKey, DimensionCollection masterKeys, List<String> formKeys) {
        Assert.notNull((Object)formSchemeKey, (String)ASSERTINFO1);
        Assert.notNull((Object)masterKeys, (String)ASSERTINFO3);
        Assert.isTrue((!CollectionUtils.isEmpty(formKeys) ? 1 : 0) != 0, (String)"formKeys is must not be null!");
        LockParam lockParam = new LockParam();
        lockParam.setFormKeys(formKeys);
        lockParam.setFormSchemeKey(formSchemeKey);
        lockParam.setMasterKeys(masterKeys);
        List<FormLockBatchReadWriteResult> formLockBatchResults = this.formLockService.batchDimension(lockParam);
        FormLockBatchAccess formLockBatchAccess = new FormLockBatchAccess();
        List<FormLockBatchReadWriteResult> lockResult = formLockBatchResults.stream().filter(FormLockBatchReadWriteResult::isLock).collect(Collectors.toList());
        formLockBatchAccess.setResultList(lockResult);
        return formLockBatchAccess;
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
        DimensionCombination masterKey = mergeParam.getMasterKey();
        Assert.notNull((Object)masterKey, (String)ASSERTINFO2);
        Set<String> taskKeys = mergeParam.getTaskKeys();
        if (org.springframework.util.CollectionUtils.isEmpty(taskKeys)) {
            return this.canAccess;
        }
        for (String taskKey : taskKeys) {
            if (!this.isEnable(taskKey, null)) continue;
            Set<IAccessForm> accessForms = mergeParam.getAccessFormsByTaskKey(taskKey);
            for (IAccessForm accessForm : accessForms) {
                LockParam lockParam = new LockParam();
                lockParam.setFormKeys(Stream.of(accessForm.getFormKey()).collect(Collectors.toList()));
                lockParam.setFormSchemeKey(accessForm.getFormSchemeKey());
                DimensionCollection dimCollection = this.dimCollectionBuildUtil.buildCollectionByCombination(masterKey);
                lockParam.setMasterKeys(dimCollection);
                boolean formLocked = this.formLockService.isFormLocked(lockParam);
                if (!formLocked) continue;
                return new AccessCode(this.name(), "2");
            }
        }
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
        if (org.springframework.util.CollectionUtils.isEmpty(taskKeys)) {
            return this.canAccessBatchMergeAccess;
        }
        HashMap<String, Map> cache = new HashMap<String, Map>();
        HashMap<String, Boolean> taskEnable = new HashMap<String, Boolean>();
        for (String taskKey : taskKeys) {
            boolean enable = this.isEnable(taskKey, null);
            taskEnable.put(taskKey, enable);
            if (!enable) continue;
            DimensionCollection masterKeysByTaskKey = mergeParam.getMasterKeysByTaskKey(taskKey);
            Set<String> formSchemeKeys = mergeParam.getFormSchemeKeysByTasKey(taskKey);
            for (String formSchemeKey : formSchemeKeys) {
                Set<IAccessForm> formSet = mergeParam.getAccessFormsByFormSchemeKey(formSchemeKey);
                List<String> formKeys = formSet.stream().map(IAccessForm::getFormKey).collect(Collectors.toList());
                LockParam lockParam = new LockParam();
                lockParam.setFormKeys(formKeys);
                lockParam.setFormSchemeKey(formSchemeKey);
                lockParam.setMasterKeys(masterKeysByTaskKey);
                List<FormLockBatchReadWriteResult> formLockBatchResults = this.formLockService.batchDimension(lockParam);
                Map formResMap = cache.computeIfAbsent(formSchemeKey, k -> new HashMap());
                for (FormLockBatchReadWriteResult formLockBatchResult : formLockBatchResults) {
                    List list = formResMap.computeIfAbsent(formLockBatchResult.getFormKey(), k -> new ArrayList());
                    list.add(formLockBatchResult);
                }
            }
        }
        HashMap<IAccessFormMerge, AccessCode> codeCache = new HashMap<IAccessFormMerge, AccessCode>();
        List<IAccessFormMerge> accessFormMerges = mergeParam.getAccessFormMerges();
        block3: for (IAccessFormMerge accessFormMerge : accessFormMerges) {
            DimensionCombination masterKey = accessFormMerge.getMasterKey();
            DimensionValueSet masterDim = masterKey.toDimensionValueSet();
            Set<String> itemTasks = accessFormMerge.getTaskKeys();
            for (String itemTask : itemTasks) {
                if (!taskEnable.getOrDefault(itemTask, false).booleanValue()) continue;
                Set<IAccessForm> formSet = accessFormMerge.getAccessFormsByTaskKey(itemTask);
                for (IAccessForm iAccessForm : formSet) {
                    List list;
                    Map scheme2Form = (Map)cache.get(iAccessForm.getFormSchemeKey());
                    if (scheme2Form == null || (list = (List)scheme2Form.get(iAccessForm.getFormKey())) == null) continue;
                    for (FormLockBatchReadWriteResult item : list) {
                        boolean lock = item.isLock();
                        if (!lock || !masterDim.isSubsetOf(item.getDimensionValueSet())) continue;
                        codeCache.put(accessFormMerge, new AccessCode(this.name(), "2"));
                        continue block3;
                    }
                }
            }
            codeCache.put(accessFormMerge, this.canAccess);
        }
        return new BatchMergeAccess(this.name(), codeCache);
    }
}

