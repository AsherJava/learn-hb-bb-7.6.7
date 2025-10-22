/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.definition.controller.ITaskOptionController
 *  org.apache.shiro.util.Assert
 */
package com.jiuqi.nr.data.access.service.impl;

import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.data.access.api.IStateEndUploadService;
import com.jiuqi.nr.data.access.common.AccessLevel;
import com.jiuqi.nr.data.access.common.AccessType;
import com.jiuqi.nr.data.access.common.StateConst;
import com.jiuqi.nr.data.access.exception.AccessException;
import com.jiuqi.nr.data.access.param.AccessCode;
import com.jiuqi.nr.data.access.param.BatchMergeAccess;
import com.jiuqi.nr.data.access.param.CanAccessBatchMergeAccess;
import com.jiuqi.nr.data.access.param.IAccessFormMerge;
import com.jiuqi.nr.data.access.param.IAccessMessage;
import com.jiuqi.nr.data.access.param.IBatchAccess;
import com.jiuqi.nr.data.access.param.IBatchAccessFormMerge;
import com.jiuqi.nr.data.access.param.IBatchMergeAccess;
import com.jiuqi.nr.data.access.param.UnitBatchAccessCache;
import com.jiuqi.nr.data.access.service.IDataAccessItemService;
import com.jiuqi.nr.data.access.util.DimensionValueSetUtil;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.definition.controller.ITaskOptionController;
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
public class EndUploadAccessServiceImpl
implements IDataAccessItemService {
    @Autowired
    private ITaskOptionController taskOptionController;
    @Autowired
    private IStateEndUploadService endUploadService;
    private final AccessCode canAccess = new AccessCode(this.name());
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final Function<String, String> noAccessReason = code -> Optional.ofNullable(code).filter(e -> e.equals("1")).orElse("\u8be5\u8282\u70b9\u7ec8\u6b62\u586b\u62a5\u4e0d\u53ef\u7f16\u8f91");

    @Override
    public String name() {
        return "endUpload";
    }

    @Override
    public int getOrder() {
        return 2;
    }

    @Override
    public boolean isEnable(String taskKey, String formSchemeKey) {
        Assert.notNull((Object)taskKey, (String)"task is must not be null!");
        String object = this.taskOptionController.getValue(taskKey, "ALLOW_STOP_FILING");
        return "1".equals(object);
    }

    @Override
    public AccessLevel getLevel() {
        return AccessLevel.UNIT;
    }

    @Override
    public List<String> getCodeList() {
        return Arrays.asList("2");
    }

    @Override
    public AccessCode visible(String formSchemeKey, DimensionCombination masterKey, String formKey) {
        Assert.notNull((Object)formSchemeKey, (String)"formSchemeKey is must not be null!");
        Assert.notNull((Object)masterKey, (String)"masterKey is must not be null!");
        return this.canAccess;
    }

    @Override
    public AccessCode readable(String formSchemeKey, DimensionCombination masterKey, String formKey) {
        Assert.notNull((Object)formSchemeKey, (String)"formSchemeKey is must not be null!");
        Assert.notNull((Object)masterKey, (String)"masterKey is must not be null!");
        return this.canAccess;
    }

    @Override
    public AccessCode writeable(String formSchemeKey, DimensionCombination masterKey, String formKey) {
        Assert.notNull((Object)formSchemeKey, (String)"formSchemeKey is must not be null!");
        Assert.notNull((Object)masterKey, (String)"masterKey is must not be null!");
        AccessCode accessCode = new AccessCode(this.name());
        Map<DimensionValueSet, StateConst> res = this.endUploadService.getStatesInfo(formSchemeKey, masterKey.toDimensionValueSet(), NpContextHolder.getContext().getUserId());
        if (CollectionUtils.isEmpty(res)) {
            if (this.logger.isDebugEnabled()) {
                this.logger.debug("\u672a\u67e5\u8be2\u5230\u8bb0\u5f55");
            }
            return accessCode;
        }
        Optional<StateConst> value = res.values().stream().findFirst();
        if (!value.isPresent()) {
            return accessCode;
        }
        StateConst stateConst = value.get();
        if (stateConst.equals((Object)StateConst.ENDFILL) || stateConst.equals((Object)StateConst.ENDFILLICON)) {
            accessCode = new AccessCode(this.name(), "2");
        }
        return accessCode;
    }

    @Override
    public IAccessMessage getAccessMessage() {
        return code -> this.noAccessReason.apply(code);
    }

    @Override
    public IBatchAccess getBatchVisible(String formSchemeKey, DimensionCollection masterKeys, List<String> formKeys) {
        Assert.notNull((Object)formSchemeKey, (String)"formSchemeKey is must not be null!");
        Assert.notNull((Object)masterKeys, (String)"masterKeys is must not be null!");
        return (masterKey, formKey) -> this.canAccess;
    }

    @Override
    public IBatchAccess getBatchReadable(String formSchemeKey, DimensionCollection masterKeys, List<String> formKeys) {
        Assert.notNull((Object)formSchemeKey, (String)"formSchemeKey is must not be null!");
        Assert.notNull((Object)masterKeys, (String)"masterKeys is must not be null!");
        return (masterKey, formKey) -> this.canAccess;
    }

    @Override
    public IBatchAccess getBatchWriteable(String formSchemeKey, DimensionCollection masterKeys, List<String> formKeys) {
        Assert.notNull((Object)formSchemeKey, (String)"formSchemeKey is must not be null!");
        Assert.notNull((Object)masterKeys, (String)"masterKeys is must not be null!");
        UnitBatchAccessCache endUploadBatchAccess = new UnitBatchAccessCache(this.name(), formSchemeKey);
        endUploadBatchAccess.setAccessType(AccessType.WRITE);
        Map<DimensionValueSet, String> cacheMap = endUploadBatchAccess.getCacheMap();
        DimensionValueSet dimensionValueSet = DimensionValueSetUtil.mergeDimensionValueSet(masterKeys);
        Map<DimensionValueSet, StateConst> res = this.endUploadService.getStatesInfo(formSchemeKey, dimensionValueSet, NpContextHolder.getContext().getUserId());
        for (Map.Entry<DimensionValueSet, StateConst> entry : res.entrySet()) {
            DimensionValueSet dimesion = entry.getKey();
            StateConst value = entry.getValue();
            if (!value.equals((Object)StateConst.ENDFILL) && !value.equals((Object)StateConst.ENDFILLICON)) continue;
            cacheMap.put(dimesion, "2");
        }
        return endUploadBatchAccess;
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
        Set<String> taskKeys = mergeParam.getTaskKeys();
        if (CollectionUtils.isEmpty(taskKeys)) {
            return this.canAccess;
        }
        for (String taskKey : taskKeys) {
            boolean enable = this.isEnable(taskKey, null);
            if (!enable) continue;
            Set<String> formSchemeKeys = mergeParam.getFormSchemeKeysByTasKey(taskKey);
            DimensionCombination masterKey = mergeParam.getMasterKey();
            for (String formSchemeKey : formSchemeKeys) {
                StateConst stateConst;
                Map<DimensionValueSet, StateConst> res = this.endUploadService.getStatesInfo(formSchemeKey, masterKey.toDimensionValueSet(), NpContextHolder.getContext().getUserId());
                Optional<StateConst> value = res.values().stream().findFirst();
                if (!value.isPresent() || !(stateConst = value.get()).equals((Object)StateConst.ENDFILL) && !stateConst.equals((Object)StateConst.ENDFILLICON)) continue;
                return new AccessCode(this.name(), "2");
            }
        }
        return this.canAccess;
    }

    @Override
    public IBatchMergeAccess getBatchVisible(IBatchAccessFormMerge mergeParam) throws AccessException {
        return merge -> this.canAccess;
    }

    @Override
    public IBatchMergeAccess getBatchReadable(IBatchAccessFormMerge mergeParam) throws AccessException {
        return merge -> this.canAccess;
    }

    @Override
    public IBatchMergeAccess getBatchWriteable(IBatchAccessFormMerge mergeParam) throws AccessException {
        List<IAccessFormMerge> accessFormMerges = mergeParam.getAccessFormMerges();
        Set<String> taskKeys = mergeParam.getTaskKeys();
        if (CollectionUtils.isEmpty(taskKeys)) {
            return new CanAccessBatchMergeAccess(this.name());
        }
        HashMap<String, Map<DimensionValueSet, StateConst>> map = new HashMap<String, Map<DimensionValueSet, StateConst>>();
        for (String taskKey : taskKeys) {
            if (!this.isEnable(taskKey, null)) continue;
            DimensionCollection masterKeysByTaskKey = mergeParam.getMasterKeysByTaskKey(taskKey);
            DimensionValueSet dimensionValueSet = DimensionValueSetUtil.mergeDimensionValueSet(masterKeysByTaskKey);
            Set<String> formSchemeKeys = mergeParam.getFormSchemeKeysByTasKey(taskKey);
            for (String formSchemeKey : formSchemeKeys) {
                Map<DimensionValueSet, StateConst> res = this.endUploadService.getStatesInfo(formSchemeKey, dimensionValueSet, NpContextHolder.getContext().getUserId());
                map.put(formSchemeKey, res);
            }
        }
        HashMap<IAccessFormMerge, AccessCode> cache = new HashMap<IAccessFormMerge, AccessCode>();
        AccessCode accessCode = new AccessCode(this.name(), "2");
        block2: for (IAccessFormMerge accessFormMerge : accessFormMerges) {
            DimensionCombination masterKey = accessFormMerge.getMasterKey();
            DimensionValueSet masterSet = masterKey.toDimensionValueSet();
            for (String taskKey : taskKeys) {
                if (!this.isEnable(taskKey, null)) continue;
                Set<String> formSchemeKeys = accessFormMerge.getFormSchemeKeysByTasKey(taskKey);
                for (String formSchemeKey : formSchemeKeys) {
                    Map stateConstMap = (Map)map.get(formSchemeKey);
                    if (CollectionUtils.isEmpty(stateConstMap)) continue;
                    for (Map.Entry setState : stateConstMap.entrySet()) {
                        DimensionValueSet key = (DimensionValueSet)setState.getKey();
                        StateConst value = (StateConst)((Object)setState.getValue());
                        if (!value.equals((Object)StateConst.ENDFILL) && !value.equals((Object)StateConst.ENDFILLICON) || !masterSet.isSubsetOf(key)) continue;
                        cache.put(accessFormMerge, accessCode);
                        continue block2;
                    }
                }
            }
            cache.put(accessFormMerge, this.canAccess);
        }
        return new BatchMergeAccess(this.name(), cache);
    }
}

