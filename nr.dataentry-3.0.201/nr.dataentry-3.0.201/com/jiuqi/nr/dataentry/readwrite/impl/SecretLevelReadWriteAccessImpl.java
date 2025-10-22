/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.jtable.params.base.EntityViewData
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  com.jiuqi.nr.jtable.params.output.SecretLevelInfo
 *  com.jiuqi.nr.jtable.service.ISecretLevelService
 */
package com.jiuqi.nr.dataentry.readwrite.impl;

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
import com.jiuqi.nr.jtable.params.base.EntityViewData;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.params.output.SecretLevelInfo;
import com.jiuqi.nr.jtable.service.ISecretLevelService;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Deprecated
public class SecretLevelReadWriteAccessImpl
implements IReadWriteAccess,
IBatchDimensionReadWriteAccess {
    private static final Logger logger = LoggerFactory.getLogger(SecretLevelReadWriteAccessImpl.class);
    @Autowired
    private ISecretLevelService secretLevelService;

    @Override
    public String getName() {
        return "secretLevel";
    }

    @Override
    public boolean isEnable(JtableContext context) {
        return this.secretLevelService.secretLevelEnable(context.getTaskKey());
    }

    @Override
    public ReadWriteAccessDesc readable(ReadWriteAccessItem item, JtableContext context) {
        boolean formReadable = true;
        try {
            if (this.secretLevelService.secretLevelEnable(context.getTaskKey())) {
                SecretLevelInfo secretLevel = this.secretLevelService.getSecretLevel(context);
                formReadable = this.secretLevelService.canAccess(secretLevel.getSecretLevelItem());
            }
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            throw new RuntimeException("\u540e\u53f0\u62a5\u8868\u53ea\u8bfb\u516c\u5f0f\u62a5\u9519\uff0c\u8bf7\u8054\u7cfb\u7ba1\u7406\u5458");
        }
        String unwriteableDesc = "";
        if (!formReadable) {
            unwriteableDesc = DataEntryUtil.isChinese() ? "\u7528\u6237\u5bc6\u7ea7\u4e0d\u80fd\u8bbf\u95ee\u62a5\u8868\u5bc6\u7ea7\uff0c\u62a5\u8868\u4e0d\u53ef\u89c1" : "Users at the security level cannot access the report security level and the form is invisible";
        }
        return new ReadWriteAccessDesc(formReadable, unwriteableDesc);
    }

    @Override
    public ReadWriteAccessDesc writeable(ReadWriteAccessItem item, JtableContext context) {
        return this.readable(item, context);
    }

    @Override
    public Object initCache(ReadWriteAccessCacheParams readWriteAccessCacheParams) {
        EntityBatchAuthCache authCache = new EntityBatchAuthCache();
        if (!this.isEnable(readWriteAccessCacheParams.getJtableContext())) {
            authCache.setIgnoreAuth(true);
            return authCache;
        }
        List querySecretLevels = this.secretLevelService.querySecretLevels(readWriteAccessCacheParams.getJtableContext());
        if (querySecretLevels.size() <= 0) {
            return authCache;
        }
        HashSet<DimensionCacheKey> cacheKeys = new HashSet<DimensionCacheKey>();
        authCache.setNotWriteEntitys(cacheKeys);
        List<EntityViewData> entityList = readWriteAccessCacheParams.getEntityList();
        boolean adjustDimension = ReadWriteAccessCacheManager.checkAdjustDimension(((SecretLevelInfo)querySecretLevels.get(0)).getJtableContext().getDimensionSet(), entityList);
        ArrayList<String> dimKeys = new ArrayList<String>(((SecretLevelInfo)querySecretLevels.get(0)).getJtableContext().getDimensionSet().keySet());
        authCache.setDimKeys(dimKeys);
        boolean hasInit = false;
        for (SecretLevelInfo sercetLevelInfo : querySecretLevels) {
            if (this.secretLevelService.canAccess(sercetLevelInfo.getSecretLevelItem())) continue;
            Map<String, DimensionValue> currentDimension = ReadWriteAccessCacheManager.getDimensionValue(adjustDimension, sercetLevelInfo.getJtableContext().getDimensionSet(), entityList);
            if (!hasInit) {
                dimKeys = new ArrayList<String>(currentDimension.keySet());
                hasInit = true;
            }
            DimensionCacheKey cacheKey = new DimensionCacheKey(currentDimension);
            cacheKeys.add(cacheKey);
        }
        return authCache;
    }

    @Override
    public ReadWriteAccessDesc matchingAccessLevel(Object cacheObj, Consts.FormAccessLevel formAccessLevel, DimensionCacheKey cacheKey, String formKey, EntityViewData dwEntity) {
        EntityBatchAuthCache authCache = (EntityBatchAuthCache)cacheObj;
        if (authCache.isIgnoreAuth()) {
            return null;
        }
        DimensionCacheKey simpleKey = ReadWriteAccessCacheManager.getSimpleKey(cacheKey, authCache.getDimKeys());
        HashSet<DimensionCacheKey> cacheKeys = authCache.getNotWriteEntitys();
        boolean canAccess = cacheKeys == null ? true : !cacheKeys.contains(simpleKey);
        return new ReadWriteAccessDesc(canAccess, canAccess ? "" : "\u7528\u6237\u5bc6\u7ea7\u4e0d\u80fd\u8bbf\u95ee\u62a5\u8868\u5bc6\u7ea7\uff0c\u62a5\u8868\u4e0d\u53ef\u89c1");
    }

    @Override
    public String getCacheLevel() {
        return "UNIT";
    }

    @Override
    public String getStatusFormKey(Map<String, DimensionValue> dimensionSet, List<EntityViewData> entityList, String formKey, Consts.FormAccessLevel formAccessLevel, String formSchemeKey, ReadWriteAccessCacheManager accessCacheManager) {
        return ReadWriteAccessCacheManager.getStatusKey(dimensionSet, formKey, entityList);
    }
}

