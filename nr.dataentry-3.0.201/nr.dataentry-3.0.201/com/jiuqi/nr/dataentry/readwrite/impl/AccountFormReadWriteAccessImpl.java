/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.jtable.params.base.EntityViewData
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  com.jiuqi.nr.jtable.util.DimensionValueSetUtil
 */
package com.jiuqi.nr.dataentry.readwrite.impl;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.dataentry.bean.DimensionCacheKey;
import com.jiuqi.nr.dataentry.readwrite.IBatchDimensionReadWriteAccess;
import com.jiuqi.nr.dataentry.readwrite.IReadWriteAccess;
import com.jiuqi.nr.dataentry.readwrite.ReadWriteAccessDesc;
import com.jiuqi.nr.dataentry.readwrite.ReadWriteAccessItem;
import com.jiuqi.nr.dataentry.readwrite.bean.BatchAccountDataReadWriteResult;
import com.jiuqi.nr.dataentry.readwrite.bean.FormBatchReadWriteCache;
import com.jiuqi.nr.dataentry.readwrite.bean.ReadWriteAccessCacheParams;
import com.jiuqi.nr.dataentry.readwrite.impl.ReadWriteAccessCacheManager;
import com.jiuqi.nr.dataentry.service.IAccountDataReadOnlyService;
import com.jiuqi.nr.dataentry.util.Consts;
import com.jiuqi.nr.jtable.params.base.EntityViewData;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.util.DimensionValueSetUtil;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Deprecated
public class AccountFormReadWriteAccessImpl
implements IReadWriteAccess,
IBatchDimensionReadWriteAccess {
    @Autowired
    private IAccountDataReadOnlyService accountDataReadOnlyService;

    @Override
    public ReadWriteAccessDesc readable(ReadWriteAccessItem item, JtableContext context) throws Exception {
        return new ReadWriteAccessDesc(true, "");
    }

    @Override
    public ReadWriteAccessDesc writeable(ReadWriteAccessItem item, JtableContext context) throws Exception {
        try {
            DimensionValueSet masterKey = DimensionValueSetUtil.getDimensionValueSet((Map)context.getDimensionSet());
            Boolean readOnly = this.accountDataReadOnlyService.readOnly(masterKey, context.getFormKey());
            if (readOnly.booleanValue()) {
                return new ReadWriteAccessDesc(false, "\u53f0\u8d26\u8868\u5386\u53f2\u6570\u636e\u53ea\u8bfb");
            }
            return new ReadWriteAccessDesc(true, "");
        }
        catch (Exception e) {
            return new ReadWriteAccessDesc(false, "\u53f0\u8d26\u8868\u53ea\u8bfb\u6743\u9650\u5224\u65ad\u5f02\u5e38");
        }
    }

    @Override
    public String getName() {
        return "accountData";
    }

    @Override
    public Boolean IsBreak() {
        return false;
    }

    @Override
    public Object initCache(ReadWriteAccessCacheParams readWriteAccessCacheParams) throws Exception {
        if (Consts.FormAccessLevel.FORM_DATA_WRITE == readWriteAccessCacheParams.getFormAccessLevel() || Consts.FormAccessLevel.FORM_DATA_SYSTEM_WRITE == readWriteAccessCacheParams.getFormAccessLevel()) {
            FormBatchReadWriteCache batchCache = new FormBatchReadWriteCache();
            HashMap<DimensionCacheKey, HashSet<String>> cacheItems = new HashMap<DimensionCacheKey, HashSet<String>>();
            batchCache.setCanAccess(false);
            batchCache.setCacheMap(cacheItems);
            JtableContext context = readWriteAccessCacheParams.getJtableContext();
            DimensionValueSet dimensionValueSet = DimensionValueSetUtil.getDimensionValueSet((JtableContext)context);
            List<BatchAccountDataReadWriteResult> res = this.accountDataReadOnlyService.batchReadOnlyAccResult(dimensionValueSet, readWriteAccessCacheParams.getFormKeys());
            Set formKeys = null;
            for (BatchAccountDataReadWriteResult re : res) {
                DimensionCacheKey dimensionCacheKey = new DimensionCacheKey(DimensionValueSetUtil.getDimensionSet((DimensionValueSet)re.getMasterKey()));
                formKeys = cacheItems.containsKey(dimensionCacheKey) ? (Set)cacheItems.get(dimensionCacheKey) : new HashSet();
                formKeys.add(re.getFormKey());
            }
            return batchCache;
        }
        return null;
    }

    @Override
    public ReadWriteAccessDesc matchingAccessLevel(Object cacheObj, Consts.FormAccessLevel formAccessLevel, DimensionCacheKey cacheKey, String formKey, EntityViewData dwEntity) {
        if (Consts.FormAccessLevel.FORM_DATA_WRITE == formAccessLevel || Consts.FormAccessLevel.FORM_DATA_SYSTEM_WRITE == formAccessLevel) {
            HashSet<String> formKeys;
            FormBatchReadWriteCache batchReadWriteCache = (FormBatchReadWriteCache)cacheObj;
            DimensionCacheKey simpleKey = ReadWriteAccessCacheManager.getSimpleKey(cacheKey, batchReadWriteCache.getDimKeys());
            Map<DimensionCacheKey, HashSet<String>> cacheItems = batchReadWriteCache.getCacheMap();
            HashSet<String> hashSet = formKeys = cacheItems == null ? null : cacheItems.get(simpleKey);
            if (formKeys == null) {
                return null;
            }
            boolean canWrite = formKeys.contains(formKey);
            return new ReadWriteAccessDesc(!canWrite, canWrite ? "\u6570\u636e\u4e3a\u5386\u53f2\u7248\u672c\u4e0d\u53ef\u7f16\u8f91" : "");
        }
        return null;
    }

    @Override
    public String getCacheLevel() {
        return "FORM";
    }

    @Override
    public String getStatusFormKey(Map<String, DimensionValue> dimensionSet, List<EntityViewData> entityList, String formKey, Consts.FormAccessLevel formAccessLevel, String formSchemeKey, ReadWriteAccessCacheManager accessCacheManager) {
        if (Consts.FormAccessLevel.FORM_DATA_WRITE == formAccessLevel) {
            return ReadWriteAccessCacheManager.getStatusKey(dimensionSet, formKey, entityList);
        }
        return null;
    }
}

