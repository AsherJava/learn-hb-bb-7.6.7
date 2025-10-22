/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  org.apache.shiro.util.Assert
 */
package com.jiuqi.nr.data.access.service.impl;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.data.access.api.IStateSecretLevelService;
import com.jiuqi.nr.data.access.exception.AccessException;
import com.jiuqi.nr.data.access.param.AccessCode;
import com.jiuqi.nr.data.access.param.BatchMergeAccess;
import com.jiuqi.nr.data.access.param.CanAccessBatchMergeAccess;
import com.jiuqi.nr.data.access.param.IAccessFormMerge;
import com.jiuqi.nr.data.access.param.IAccessMessage;
import com.jiuqi.nr.data.access.param.IBatchAccess;
import com.jiuqi.nr.data.access.param.IBatchAccessFormMerge;
import com.jiuqi.nr.data.access.param.IBatchMergeAccess;
import com.jiuqi.nr.data.access.param.SecretLevel;
import com.jiuqi.nr.data.access.param.UnitBatchAccessCache;
import com.jiuqi.nr.data.access.service.IDataAccessItemService;
import com.jiuqi.nr.data.access.util.DimensionValueSetUtil;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import org.apache.shiro.util.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class SecretLevelAccessServiceImpl
implements IDataAccessItemService {
    @Autowired
    private IStateSecretLevelService stateSecretLevelService;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    public static final String ASSERTINFO1 = "formSchemeKey is must not be null!";
    public static final String ASSERTINFO2 = "masterKey is must not be null!";
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private Function<String, String> noAccessReason = code -> Optional.ofNullable(code).filter(e -> e.equals("1")).orElse("\u7528\u6237\u5bc6\u7ea7\u4e0d\u80fd\u8bbf\u95ee\u62a5\u8868\u5bc6\u7ea7\uff0c\u62a5\u8868\u4e0d\u53ef\u89c1");
    private final AccessCode canAccess = new AccessCode(this.name());
    private final CanAccessBatchMergeAccess canAccessBatchMergeAccess = new CanAccessBatchMergeAccess(this.name());

    @Override
    public String name() {
        return "secretLevel";
    }

    @Override
    public int getOrder() {
        return 7;
    }

    @Override
    public boolean isEnable(String taskKey, String formSchemeKey) {
        Assert.notNull((Object)taskKey, (String)"task is must not be null!");
        return this.stateSecretLevelService.secretLevelEnable(taskKey);
    }

    @Override
    public List<String> getCodeList() {
        return Arrays.asList("2");
    }

    @Override
    public AccessCode visible(String formSchemeKey, DimensionCombination masterKey, String formKey) {
        return this.readable(formSchemeKey, masterKey, formKey);
    }

    @Override
    public AccessCode readable(String formSchemeKey, DimensionCombination masterKey, String formKey) {
        Assert.notNull((Object)formSchemeKey, (String)ASSERTINFO1);
        Assert.notNull((Object)masterKey, (String)ASSERTINFO2);
        AccessCode accessCode = new AccessCode(this.name());
        boolean formReadable = true;
        try {
            SecretLevel secretLevel = this.stateSecretLevelService.getSecretLevel(masterKey.toDimensionValueSet(), formSchemeKey);
            FormSchemeDefine formSchemeDefine = this.runTimeViewController.getFormScheme(formSchemeKey);
            if (this.stateSecretLevelService.secretLevelEnable(formSchemeDefine.getTaskKey())) {
                formReadable = this.stateSecretLevelService.canAccess(secretLevel);
            }
        }
        catch (Exception e) {
            this.logger.error("\u51fa\u9519\u539f\u56e0\uff1a{}", (Object)e.getMessage(), (Object)e);
            throw new AccessException("\u540e\u53f0\u5bc6\u7ea7\u5224\u65ad\u62a5\u9519\uff0c\u8bf7\u8054\u7cfb\u7ba1\u7406\u5458");
        }
        if (!formReadable) {
            accessCode = new AccessCode(this.name(), "2");
        }
        return accessCode;
    }

    @Override
    public AccessCode writeable(String formSchemeKey, DimensionCombination masterKey, String formKey) {
        Assert.notNull((Object)formSchemeKey, (String)ASSERTINFO1);
        Assert.notNull((Object)masterKey, (String)ASSERTINFO2);
        return this.readable(formSchemeKey, masterKey, formKey);
    }

    @Override
    public IAccessMessage getAccessMessage() {
        return code -> this.noAccessReason.apply(code);
    }

    @Override
    public IBatchAccess getBatchVisible(String formSchemeKey, DimensionCollection masterKeys, List<String> formKeys) {
        return this.getBatchReadable(formSchemeKey, masterKeys, formKeys);
    }

    @Override
    public IBatchAccess getBatchReadable(String formSchemeKey, DimensionCollection masterKeys, List<String> formKeys) {
        Assert.notNull((Object)formSchemeKey, (String)ASSERTINFO1);
        Assert.notNull((Object)masterKeys, (String)ASSERTINFO2);
        UnitBatchAccessCache secretBatchCache = new UnitBatchAccessCache(this.name(), formSchemeKey);
        DimensionValueSet dimensionValueSet = DimensionValueSetUtil.mergeDimensionValueSet(masterKeys);
        Map<DimensionValueSet, SecretLevel> securityItems = this.stateSecretLevelService.batchQuerySecretLevels(dimensionValueSet, formSchemeKey);
        FormSchemeDefine formSchemeDefine = this.runTimeViewController.getFormScheme(formSchemeKey);
        String taskKey = formSchemeDefine.getTaskKey();
        boolean formReadable = true;
        for (Map.Entry<DimensionValueSet, SecretLevel> entry : securityItems.entrySet()) {
            if (this.stateSecretLevelService.secretLevelEnable(taskKey)) {
                formReadable = this.stateSecretLevelService.canAccess(entry.getValue());
            }
            if (formReadable) continue;
            secretBatchCache.getCacheMap().put(entry.getKey(), "2");
        }
        return secretBatchCache;
    }

    @Override
    public IBatchAccess getBatchWriteable(String formSchemeKey, DimensionCollection masterKeys, List<String> formKeys) {
        Assert.notNull((Object)formSchemeKey, (String)ASSERTINFO1);
        Assert.notNull((Object)masterKeys, (String)ASSERTINFO2);
        return this.getBatchReadable(formSchemeKey, masterKeys, formKeys);
    }

    @Override
    public AccessCode visible(IAccessFormMerge mergeParam) throws AccessException {
        DimensionCombination masterKey = mergeParam.getMasterKey();
        Set<String> taskKeys = mergeParam.getTaskKeys();
        for (String taskKey : taskKeys) {
            if (!this.stateSecretLevelService.secretLevelEnable(taskKey)) continue;
            Set<String> formSchemeKeys = mergeParam.getFormSchemeKeysByTasKey(taskKey);
            for (String formSchemeKey : formSchemeKeys) {
                SecretLevel secretLevel = this.stateSecretLevelService.getSecretLevel(masterKey.toDimensionValueSet(), formSchemeKey);
                boolean haveAuth = this.stateSecretLevelService.canAccess(secretLevel);
                if (haveAuth) continue;
                return new AccessCode(this.name(), "2");
            }
        }
        return this.canAccess;
    }

    @Override
    public AccessCode readable(IAccessFormMerge mergeParam) throws AccessException {
        return this.visible(mergeParam);
    }

    @Override
    public AccessCode writeable(IAccessFormMerge mergeParam) throws AccessException {
        return this.visible(mergeParam);
    }

    @Override
    public IBatchMergeAccess getBatchVisible(IBatchAccessFormMerge mergeParam) throws AccessException {
        HashMap levelMap = new HashMap();
        List<IAccessFormMerge> accessFormMerges = mergeParam.getAccessFormMerges();
        if (CollectionUtils.isEmpty(accessFormMerges)) {
            return this.canAccessBatchMergeAccess;
        }
        Set<String> taskKeys = mergeParam.getTaskKeys();
        HashMap levelCan = new HashMap();
        HashMap<String, Boolean> taskEnable = new HashMap<String, Boolean>();
        for (String taskKey : taskKeys) {
            if (!this.stateSecretLevelService.secretLevelEnable(taskKey)) {
                taskEnable.put(taskKey, false);
                continue;
            }
            taskEnable.put(taskKey, true);
            DimensionCollection masterKeysByTaskKey = mergeParam.getMasterKeysByTaskKey(taskKey);
            DimensionValueSet valueSet = DimensionValueSetUtil.mergeDimensionValueSet(masterKeysByTaskKey);
            Set<String> formSchemeKeysByTasKey = mergeParam.getFormSchemeKeysByTasKey(taskKey);
            for (String formSchemeKey : formSchemeKeysByTasKey) {
                Map<DimensionValueSet, SecretLevel> dimMap = this.stateSecretLevelService.batchQuerySecretLevels(valueSet, formSchemeKey);
                HashMap canSee = new HashMap();
                for (Map.Entry entry : dimMap.entrySet()) {
                    Boolean can = (Boolean)levelCan.get(entry.getValue());
                    if (can != null) continue;
                    boolean access = this.stateSecretLevelService.canAccess((SecretLevel)entry.getValue());
                    canSee.put(entry.getKey(), access);
                    levelCan.put(entry.getValue(), access);
                }
                levelMap.put(formSchemeKey, canSee);
            }
        }
        if (CollectionUtils.isEmpty(levelMap)) {
            return this.canAccessBatchMergeAccess;
        }
        HashMap<IAccessFormMerge, AccessCode> accessCodeMap = new HashMap<IAccessFormMerge, AccessCode>();
        block3: for (IAccessFormMerge accessFormMerge : accessFormMerges) {
            DimensionCombination masterKey = accessFormMerge.getMasterKey();
            DimensionValueSet set = masterKey.toDimensionValueSet();
            Set<String> mergeTaskKeys = accessFormMerge.getTaskKeys();
            for (String mergeTaskKey : mergeTaskKeys) {
                if (!((Boolean)taskEnable.get(mergeTaskKey)).booleanValue()) continue;
                Set<String> formSchemeKeys = accessFormMerge.getFormSchemeKeysByTasKey(mergeTaskKey);
                for (String string : formSchemeKeys) {
                    Map dimLevelMap = (Map)levelMap.get(string);
                    for (Map.Entry dimLevel : dimLevelMap.entrySet()) {
                        DimensionValueSet key;
                        if (((Boolean)dimLevel.getValue()).booleanValue() || !set.isSubsetOf(key = (DimensionValueSet)dimLevel.getKey())) continue;
                        accessCodeMap.put(accessFormMerge, new AccessCode(this.name(), "2"));
                        continue block3;
                    }
                }
            }
            accessCodeMap.put(accessFormMerge, this.canAccess);
        }
        return new BatchMergeAccess(this.name(), accessCodeMap);
    }

    @Override
    public IBatchMergeAccess getBatchReadable(IBatchAccessFormMerge mergeParam) throws AccessException {
        return this.getBatchVisible(mergeParam);
    }

    @Override
    public IBatchMergeAccess getBatchWriteable(IBatchAccessFormMerge mergeParam) throws AccessException {
        return this.getBatchVisible(mergeParam);
    }
}

