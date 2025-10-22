/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.definition.controller.ITaskOptionController
 *  com.jiuqi.nr.jtable.params.base.EntityViewData
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 */
package com.jiuqi.nr.dataentry.readwrite.impl;

import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.dataentry.bean.DimensionCacheKey;
import com.jiuqi.nr.dataentry.bean.FormLockParam;
import com.jiuqi.nr.dataentry.readwrite.IBatchDimensionReadWriteAccess;
import com.jiuqi.nr.dataentry.readwrite.IReadWriteAccess;
import com.jiuqi.nr.dataentry.readwrite.ReadWriteAccessDesc;
import com.jiuqi.nr.dataentry.readwrite.ReadWriteAccessItem;
import com.jiuqi.nr.dataentry.readwrite.bean.FormBatchReadWriteCache;
import com.jiuqi.nr.dataentry.readwrite.bean.FormLockBatchReadWriteResult;
import com.jiuqi.nr.dataentry.readwrite.bean.ReadWriteAccessCacheParams;
import com.jiuqi.nr.dataentry.readwrite.impl.ReadWriteAccessCacheManager;
import com.jiuqi.nr.dataentry.service.IFormLockService;
import com.jiuqi.nr.dataentry.util.Consts;
import com.jiuqi.nr.dataentry.util.DataEntryUtil;
import com.jiuqi.nr.definition.controller.ITaskOptionController;
import com.jiuqi.nr.jtable.params.base.EntityViewData;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Deprecated
public class FormLockReadWriteAccessImpl
implements IReadWriteAccess,
IBatchDimensionReadWriteAccess {
    private static final Logger logger = LoggerFactory.getLogger(FormLockReadWriteAccessImpl.class);
    @Autowired
    private IFormLockService formLockService;
    @Autowired
    private ITaskOptionController taskOptionController;

    @Override
    public String getName() {
        return "formLock";
    }

    @Override
    public boolean isEnable(JtableContext context) {
        String formLockObj = this.taskOptionController.getValue(context.getTaskKey(), "FORM_LOCK");
        return "1".equals(formLockObj) || "2".equals(formLockObj) || "3".equals(formLockObj);
    }

    @Override
    public ReadWriteAccessDesc readable(ReadWriteAccessItem item, JtableContext context) {
        return new ReadWriteAccessDesc(true, "");
    }

    @Override
    public ReadWriteAccessDesc writeable(ReadWriteAccessItem item, JtableContext context) {
        FormLockParam param = new FormLockParam();
        param.setContext(context);
        Boolean isFormLocked = false;
        try {
            isFormLocked = this.formLockService.isFormLocked(param);
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            throw new RuntimeException("\u540e\u53f0\u6570\u636e\u9501\u5b9a\u5224\u65ad\u62a5\u9519\uff0c\u8bf7\u8054\u7cfb\u7ba1\u7406\u5458");
        }
        String unwriteableDesc = "";
        if (isFormLocked.booleanValue()) {
            unwriteableDesc = DataEntryUtil.isChinese() ? "\u6570\u636e\u5df2\u9501\u5b9a\u4e0d\u53ef\u7f16\u8f91" : "The data is locked and cannot be edited";
        }
        return new ReadWriteAccessDesc(isFormLocked == false, unwriteableDesc);
    }

    @Override
    public Object initCache(ReadWriteAccessCacheParams readWriteAccessCacheParams) {
        if (Consts.FormAccessLevel.FORM_DATA_WRITE == readWriteAccessCacheParams.getFormAccessLevel() || Consts.FormAccessLevel.FORM_DATA_SYSTEM_WRITE == readWriteAccessCacheParams.getFormAccessLevel()) {
            List<FormLockBatchReadWriteResult> result = this.formLockService.batchDimension(readWriteAccessCacheParams.getJtableContext());
            FormBatchReadWriteCache batchCache = new FormBatchReadWriteCache();
            HashMap<DimensionCacheKey, HashSet<String>> cacheItems = new HashMap<DimensionCacheKey, HashSet<String>>();
            batchCache.setCanAccess(false);
            batchCache.setCacheMap(cacheItems);
            if (result.size() <= 0) {
                return batchCache;
            }
            List<EntityViewData> entityList = readWriteAccessCacheParams.getEntityList();
            boolean adjustDimension = ReadWriteAccessCacheManager.checkAdjustDimension(result.get(0).getDimensionSet(), entityList);
            ArrayList<String> dimKeys = new ArrayList<String>(result.get(0).getDimensionSet().keySet());
            boolean hasInit = false;
            for (FormLockBatchReadWriteResult item : result) {
                DimensionCacheKey cacheKey;
                HashSet<String> formKeys;
                Map<String, DimensionValue> currentDimension = ReadWriteAccessCacheManager.getDimensionValue(adjustDimension, item.getDimensionSet(), entityList);
                if (!hasInit) {
                    dimKeys = new ArrayList<String>(currentDimension.keySet());
                    hasInit = true;
                }
                if ((formKeys = (HashSet<String>)cacheItems.get(cacheKey = new DimensionCacheKey(currentDimension))) == null) {
                    formKeys = new HashSet<String>();
                    cacheItems.put(cacheKey, formKeys);
                }
                if (!item.isLock()) continue;
                formKeys.add(item.getFormKey());
            }
            batchCache.setDimKeys(dimKeys);
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
            boolean islock = formKeys.contains(formKey);
            return new ReadWriteAccessDesc(!islock, islock ? "\u6570\u636e\u5df2\u9501\u5b9a\u4e0d\u53ef\u7f16\u8f91" : "");
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

