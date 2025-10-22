/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.definition.controller.ITaskOptionController
 *  com.jiuqi.nr.jtable.params.base.EntityViewData
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  com.jiuqi.nr.jtable.util.DimensionValueSetUtil
 *  com.jiuqi.nr.state.common.StateConst
 *  com.jiuqi.nr.state.pojo.StateEntites
 *  com.jiuqi.nr.state.service.IStateSevice
 */
package com.jiuqi.nr.dataentry.readwrite.impl;

import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.dataentry.bean.DimensionCacheKey;
import com.jiuqi.nr.dataentry.readwrite.IBatchDimensionReadWriteAccess;
import com.jiuqi.nr.dataentry.readwrite.IReadWriteAccess;
import com.jiuqi.nr.dataentry.readwrite.ReadWriteAccessDesc;
import com.jiuqi.nr.dataentry.readwrite.ReadWriteAccessItem;
import com.jiuqi.nr.dataentry.readwrite.bean.EntityBatchAuthCache;
import com.jiuqi.nr.dataentry.readwrite.bean.ReadWriteAccessCacheParams;
import com.jiuqi.nr.dataentry.readwrite.impl.ReadWriteAccessCacheManager;
import com.jiuqi.nr.dataentry.util.Consts;
import com.jiuqi.nr.dataentry.util.DataEntryUtil;
import com.jiuqi.nr.definition.controller.ITaskOptionController;
import com.jiuqi.nr.jtable.params.base.EntityViewData;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.util.DimensionValueSetUtil;
import com.jiuqi.nr.state.common.StateConst;
import com.jiuqi.nr.state.pojo.StateEntites;
import com.jiuqi.nr.state.service.IStateSevice;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Deprecated
public class UnitEndFillReadWriteAccessImpl
implements IReadWriteAccess,
IBatchDimensionReadWriteAccess {
    @Autowired
    private IStateSevice stateSevice;
    @Autowired
    private ITaskOptionController taskOptionController;

    @Override
    public String getName() {
        return "unitEndFill";
    }

    @Override
    public boolean isEnable(JtableContext context) {
        String object = this.taskOptionController.getValue(context.getTaskKey(), "ALLOW_STOP_FILING");
        return "1".equals(object);
    }

    @Override
    public ReadWriteAccessDesc readable(ReadWriteAccessItem item, JtableContext context) throws Exception {
        return new ReadWriteAccessDesc(true, "");
    }

    @Override
    public ReadWriteAccessDesc writeable(ReadWriteAccessItem item, JtableContext context) throws Exception {
        Boolean endFill = (Boolean)item.getParams();
        if (endFill.booleanValue()) {
            if (DataEntryUtil.isChinese()) {
                return new ReadWriteAccessDesc(false, "\u7ec8\u6b62\u586b\u62a5\uff0c\u4e0d\u53ef\u5199");
            }
            return new ReadWriteAccessDesc(false, "The filling is terminated and the form is not writable");
        }
        return new ReadWriteAccessDesc(true, "");
    }

    @Override
    public Object initCache(ReadWriteAccessCacheParams readWriteAccessCacheParams) {
        if (readWriteAccessCacheParams.getFormAccessLevel() == Consts.FormAccessLevel.FORM_DATA_WRITE || readWriteAccessCacheParams.getFormAccessLevel() == Consts.FormAccessLevel.FORM_DATA_SYSTEM_WRITE) {
            JtableContext jtableContext = readWriteAccessCacheParams.getJtableContext();
            StateEntites stateEntites = new StateEntites();
            stateEntites.setDims(DimensionValueSetUtil.getDimensionValueSet((Map)jtableContext.getDimensionSet()));
            stateEntites.setFormSchemeKey(jtableContext.getFormSchemeKey());
            stateEntites.setUserId(NpContextHolder.getContext().getUserId());
            Map stateInfo = this.stateSevice.getStateInfo(stateEntites);
            if (null != stateInfo) {
                EntityBatchAuthCache authCache = new EntityBatchAuthCache();
                HashSet<DimensionCacheKey> cacheKeys = new HashSet<DimensionCacheKey>();
                int index = 0;
                boolean adjustDimension = false;
                List<EntityViewData> entityList = readWriteAccessCacheParams.getEntityList();
                ArrayList<String> dimKeys = new ArrayList<String>();
                for (Map.Entry entry : stateInfo.entrySet()) {
                    StateConst state = (StateConst)entry.getValue();
                    if (!state.equals((Object)StateConst.ENDFILL) && !state.equals((Object)StateConst.ENDFILLICON)) continue;
                    DimensionValueSet dimensionValueSet = (DimensionValueSet)entry.getKey();
                    Map dimensionSet = DimensionValueSetUtil.getDimensionSet((DimensionValueSet)dimensionValueSet);
                    if (index == 0) {
                        adjustDimension = ReadWriteAccessCacheManager.checkAdjustDimension(dimensionSet, entityList);
                    }
                    Map<String, DimensionValue> currentDimension = ReadWriteAccessCacheManager.getDimensionValue(adjustDimension, dimensionSet, entityList);
                    if (index == 0) {
                        dimKeys = new ArrayList<String>(currentDimension.keySet());
                    }
                    DimensionCacheKey cacheKey = new DimensionCacheKey(currentDimension);
                    cacheKeys.add(cacheKey);
                    ++index;
                }
                authCache.setDimKeys(dimKeys);
                authCache.setNotWriteEntitys(cacheKeys);
                return authCache;
            }
        }
        return null;
    }

    @Override
    public ReadWriteAccessDesc matchingAccessLevel(Object cacheObj, Consts.FormAccessLevel formAccessLevel, DimensionCacheKey cacheKey, String formKey, EntityViewData dwEntity) {
        if (Consts.FormAccessLevel.FORM_DATA_WRITE == formAccessLevel || Consts.FormAccessLevel.FORM_DATA_SYSTEM_WRITE == formAccessLevel) {
            boolean notCanWrite;
            EntityBatchAuthCache authCache = (EntityBatchAuthCache)cacheObj;
            DimensionCacheKey simpleKey = ReadWriteAccessCacheManager.getSimpleKey(cacheKey, authCache.getDimKeys());
            HashSet<DimensionCacheKey> cacheKeys = authCache.getNotWriteEntitys();
            boolean bl = notCanWrite = cacheKeys == null ? false : cacheKeys.contains(simpleKey);
            if (notCanWrite) {
                return new ReadWriteAccessDesc(false, "\u8be5\u8282\u70b9\u7ec8\u6b62\u586b\u62a5\u4e0d\u53ef\u7f16\u8f91");
            }
            return new ReadWriteAccessDesc(true, "");
        }
        return null;
    }

    @Override
    public String getCacheLevel() {
        return "UNIT";
    }

    @Override
    public String getStatusFormKey(Map<String, DimensionValue> dimensionSet, List<EntityViewData> entityList, String formKey, Consts.FormAccessLevel formAccessLevel, String formSchemeKey, ReadWriteAccessCacheManager accessCacheManager) {
        if (Consts.FormAccessLevel.FORM_DATA_WRITE == formAccessLevel) {
            return ReadWriteAccessCacheManager.getStatusKey(dimensionSet, formKey, entityList);
        }
        return null;
    }
}

