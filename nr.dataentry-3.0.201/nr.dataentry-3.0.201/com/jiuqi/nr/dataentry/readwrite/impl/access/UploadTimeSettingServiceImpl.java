/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.data.access.common.AccessLevel
 *  com.jiuqi.nr.data.access.exception.AccessException
 *  com.jiuqi.nr.data.access.param.AccessCode
 *  com.jiuqi.nr.data.access.param.BatchMergeAccess
 *  com.jiuqi.nr.data.access.param.CanAccessBatchMergeAccess
 *  com.jiuqi.nr.data.access.param.IAccessFormMerge
 *  com.jiuqi.nr.data.access.param.IAccessMessage
 *  com.jiuqi.nr.data.access.param.IBatchAccess
 *  com.jiuqi.nr.data.access.param.IBatchAccessFormMerge
 *  com.jiuqi.nr.data.access.param.IBatchMergeAccess
 *  com.jiuqi.nr.data.access.param.UnitBatchAccessCache
 *  com.jiuqi.nr.data.access.service.IDataAccessItemService
 *  com.jiuqi.nr.data.access.util.DimensionValueSetUtil
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.dataservice.core.dimension.FixedDimensionValue
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.impl.FillDateType
 *  com.jiuqi.nr.definition.internal.impl.FillInAutomaticallyDue
 *  com.jiuqi.nr.definition.internal.impl.FillInAutomaticallyDue$Type
 *  com.jiuqi.nr.time.setting.bean.MsgReturn
 *  com.jiuqi.nr.time.setting.de.DeSetTimeProvide
 */
package com.jiuqi.nr.dataentry.readwrite.impl.access;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.data.access.common.AccessLevel;
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
import com.jiuqi.nr.dataservice.core.dimension.FixedDimensionValue;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.impl.FillDateType;
import com.jiuqi.nr.definition.internal.impl.FillInAutomaticallyDue;
import com.jiuqi.nr.time.setting.bean.MsgReturn;
import com.jiuqi.nr.time.setting.de.DeSetTimeProvide;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class UploadTimeSettingServiceImpl
implements IDataAccessItemService {
    @Autowired
    private DeSetTimeProvide deSetTimeProvide;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    private final AccessCode canAccess = new AccessCode(this.name());
    private final CanAccessBatchMergeAccess canAccessBatchMergeAccess = new CanAccessBatchMergeAccess(this.name());

    public int getOrder() {
        return -1;
    }

    public AccessCode visible(String formSchemeKey, DimensionCombination masterKey, String formKey) {
        return this.canAccess;
    }

    public AccessCode readable(String formSchemeKey, DimensionCombination masterKey, String formKey) {
        return this.canAccess;
    }

    public AccessCode writeable(String formSchemeKey, DimensionCombination masterKey, String formKey) {
        MsgReturn compareSetTime = this.deSetTimeProvide.compareSetTime(formSchemeKey, masterKey.toDimensionValueSet());
        if (compareSetTime.isDisabled()) {
            if ("\u586b\u62a5\u65f6\u95f4\u5df2\u7ed3\u675f".equals(compareSetTime.getMsg())) {
                return new AccessCode(this.name(), "UPLOAD_TIME_END");
            }
            if ("\u586b\u62a5\u65f6\u95f4\u672a\u5f00\u59cb".equals(compareSetTime.getMsg())) {
                return new AccessCode(this.name(), "UPLOAD_TIME_NOT_START");
            }
        }
        return this.canAccess;
    }

    public IBatchAccess getBatchVisible(String formSchemeKey, DimensionCollection masterKeys, List<String> formKeys) {
        return (masterKey, formKey) -> this.canAccess;
    }

    public IBatchAccess getBatchReadable(String formSchemeKey, DimensionCollection masterKeys, List<String> formKeys) {
        return (masterKey, formKey) -> this.canAccess;
    }

    public IBatchAccess getBatchWriteable(String formSchemeKey, DimensionCollection masterKeys, List<String> formKeys) {
        UnitBatchAccessCache unitBatchAccessCache = new UnitBatchAccessCache(this.name(), formSchemeKey);
        Map cacheMap = unitBatchAccessCache.getCacheMap();
        List combiNations = masterKeys.getDimensionCombinations();
        ArrayList<DimensionValueSet> dimensionValueSets = new ArrayList<DimensionValueSet>();
        for (DimensionCombination combiNation : combiNations) {
            dimensionValueSets.add(combiNation.toDimensionValueSet());
        }
        DimensionValueSet dimensionValueSet = DimensionValueSetUtil.mergeDimensionValueSet(dimensionValueSets);
        Map<String, AccessCode> stringAccessCodeMap = this.batchQueryTimeSetting(formSchemeKey, dimensionValueSet);
        for (DimensionCombination combiNation : combiNations) {
            FixedDimensionValue dwDimensionValue = combiNation.getDWDimensionValue();
            Object value = dwDimensionValue.getValue();
            AccessCode accessCode = stringAccessCodeMap.get(value.toString());
            if (accessCode == null) continue;
            cacheMap.put(combiNation.toDimensionValueSet(), accessCode.getCode());
        }
        return unitBatchAccessCache;
    }

    public String name() {
        return "uploadTimeSetting";
    }

    public boolean isEnable(String taskKey, String formSchemeKey) {
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(taskKey);
        if (taskDefine != null) {
            FillInAutomaticallyDue fillInAutomaticallyDue = taskDefine.getFillInAutomaticallyDue();
            FillDateType fillingDateType = taskDefine.getFillingDateType();
            return fillInAutomaticallyDue != null && FillInAutomaticallyDue.Type.CLOSE.getValue() != fillInAutomaticallyDue.getType() || !FillDateType.NONE.equals((Object)fillingDateType);
        }
        return false;
    }

    public IAccessMessage getAccessMessage() {
        return code -> {
            if (code.equals("UPLOAD_TIME_END")) {
                return "\u586b\u62a5\u65f6\u95f4\u5df2\u7ed3\u675f";
            }
            return "\u586b\u62a5\u65f6\u95f4\u672a\u5f00\u59cb";
        };
    }

    public AccessLevel getLevel() {
        return AccessLevel.UNIT;
    }

    public AccessCode visible(IAccessFormMerge mergeParam) throws AccessException {
        return this.canAccess;
    }

    public AccessCode readable(IAccessFormMerge mergeParam) throws AccessException {
        return this.canAccess;
    }

    public AccessCode writeable(IAccessFormMerge mergeParam) throws AccessException {
        Set taskKeys = mergeParam.getTaskKeys();
        for (String taskKey : taskKeys) {
            if (!this.isEnable(taskKey, null)) continue;
            DimensionCombination masterKey = mergeParam.getMasterKey();
            Set formSchemeKeysByTasKey = mergeParam.getFormSchemeKeysByTasKey(taskKey);
            for (String formSchemeKey : formSchemeKeysByTasKey) {
                MsgReturn compareSetTime = this.deSetTimeProvide.compareSetTime(formSchemeKey, masterKey.toDimensionValueSet());
                if (!compareSetTime.isDisabled()) continue;
                if ("\u586b\u62a5\u65f6\u95f4\u5df2\u7ed3\u675f".equals(compareSetTime.getMsg())) {
                    return new AccessCode(this.name(), "UPLOAD_TIME_END");
                }
                if (!"\u586b\u62a5\u65f6\u95f4\u672a\u5f00\u59cb".equals(compareSetTime.getMsg())) continue;
                return new AccessCode(this.name(), "UPLOAD_TIME_NOT_START");
            }
        }
        return this.canAccess;
    }

    public IBatchMergeAccess getBatchVisible(IBatchAccessFormMerge mergeParam) throws AccessException {
        return this.canAccessBatchMergeAccess;
    }

    public IBatchMergeAccess getBatchReadable(IBatchAccessFormMerge mergeParam) throws AccessException {
        return this.canAccessBatchMergeAccess;
    }

    public IBatchMergeAccess getBatchWriteable(IBatchAccessFormMerge mergeParam) throws AccessException {
        List accessFormMerges = mergeParam.getAccessFormMerges();
        if (CollectionUtils.isEmpty(accessFormMerges)) {
            return this.canAccessBatchMergeAccess;
        }
        HashMap<IAccessFormMerge, AccessCode> codeMap = new HashMap<IAccessFormMerge, AccessCode>();
        for (IAccessFormMerge accessFormMerge : accessFormMerges) {
            AccessCode writeable = this.writeable(accessFormMerge);
            codeMap.put(accessFormMerge, writeable);
        }
        return new BatchMergeAccess(this.name(), codeMap);
    }

    private Map<String, AccessCode> batchQueryTimeSetting(String formSchemeKey, DimensionValueSet dimension) {
        HashMap<String, AccessCode> cacheMap = new HashMap<String, AccessCode>();
        Map stringMsgReturnMap = this.deSetTimeProvide.batchCompareSetTime(formSchemeKey, dimension);
        for (Map.Entry entry : stringMsgReturnMap.entrySet()) {
            String unitKey = (String)entry.getKey();
            MsgReturn msgReturn = (MsgReturn)entry.getValue();
            if (!msgReturn.isDisabled()) continue;
            if ("\u586b\u62a5\u65f6\u95f4\u5df2\u7ed3\u675f".equals(msgReturn.getMsg())) {
                cacheMap.put(unitKey, new AccessCode(this.name(), "UPLOAD_TIME_END"));
            }
            if (!"\u586b\u62a5\u65f6\u95f4\u672a\u5f00\u59cb".equals(msgReturn.getMsg())) continue;
            cacheMap.put(unitKey, new AccessCode(this.name(), "UPLOAD_TIME_NOT_START"));
        }
        return cacheMap;
    }
}

