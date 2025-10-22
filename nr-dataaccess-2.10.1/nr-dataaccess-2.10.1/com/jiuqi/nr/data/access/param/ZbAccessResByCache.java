/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.exception.NotSupportedException
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 */
package com.jiuqi.nr.data.access.param;

import com.jiuqi.np.definition.exception.NotSupportedException;
import com.jiuqi.nr.data.access.common.AccessType;
import com.jiuqi.nr.data.access.exception.AccessException;
import com.jiuqi.nr.data.access.param.AbstractAccessRes;
import com.jiuqi.nr.data.access.param.AccessCode;
import com.jiuqi.nr.data.access.param.BatchAccessFormMerge;
import com.jiuqi.nr.data.access.param.IAccessFormMerge;
import com.jiuqi.nr.data.access.param.IBatchMergeAccess;
import com.jiuqi.nr.data.access.param.ZbAccessCaches;
import com.jiuqi.nr.data.access.service.impl.AccessCacheManager;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Supplier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ZbAccessResByCache
extends AbstractAccessRes {
    private static final Logger logger = LoggerFactory.getLogger(ZbAccessResByCache.class);
    private final DimensionCollection masterKeys;
    private final List<String> zbKeys;
    private final AccessType accessType;
    private final AccessCacheManager accessManager;
    private ZbAccessCaches accessCaches;

    public ZbAccessResByCache(AccessType accessType, DimensionCollection masterKeys, DimensionCombination masterKey, String zbKey, List<String> zbKeys, ZbAccessCaches accessCaches, AccessCacheManager accessManager) {
        super(accessType, masterKey, zbKey, accessManager);
        this.accessManager = accessManager;
        this.accessType = accessType;
        this.zbKeys = zbKeys;
        this.masterKeys = masterKeys;
        this.accessCaches = accessCaches;
    }

    @Override
    public AccessCode getAccessCode(DimensionCombination masterKey, String zbKey) throws AccessException {
        AccessCode accessCode = this.accessCaches == null ? this.getAccessCodeFromDb(this.accessManager, masterKey, zbKey) : this.getAccessCodeFormCache(masterKey, zbKey);
        if (logger.isDebugEnabled()) {
            logger.debug(String.format("\u72b6\u6001\u5f15\u64ce-getAccessItem-\u62a5\u8868\u6279\u91cf[%s]\u6743\u9650 \u53c2\u6570\uff1amasterKey:[%s];zbKey:[%s],\u7ed3\u679c:[%s][%s]", this.accessType.getValue(), masterKey, zbKey, accessCode.getName() == null ? "\u5168\u90e8" : accessCode.getName(), Objects.equals(accessCode.getCode(), "1") ? "\u62e5\u6709\u6743\u9650" : "\u6ca1\u6709\u6743\u9650"));
        }
        return accessCode;
    }

    private AccessCode getAccessCodeFormCache(DimensionCombination masterKey, String zbKey) {
        BatchAccessFormMerge batchAccessFormMerge = this.accessCaches.getBatchAccessFormMerge();
        Set<String> forms = batchAccessFormMerge.getFormKeysByZbKey(zbKey);
        if (forms == null) {
            throw new IllegalArgumentException("\u8bf7\u68c0\u67e5\u53c2\u6570,\u4f20\u5165\u7684\u6307\u6807key\u4e0d\u518d\u6279\u91cf\u5224\u65ad\u6743\u9650\u7684\u5217\u8868\u4e2d");
        }
        if (forms.isEmpty()) {
            AccessCode accessCode = new AccessCode("\u8be5\u6307\u6807\u6ca1\u6709\u62a5\u8868\u4f7f\u7528", "1");
            logger.debug("\u4f20\u5165\u6307\u6807\u8be5\u6ca1\u6709\u62a5\u8868\u4f7f\u7528,\u9ed8\u8ba4\u6709\u6743\u9650");
            return accessCode;
        }
        IAccessFormMerge merge = batchAccessFormMerge.getAccessFormMerge(masterKey, zbKey);
        if (merge == null) {
            AccessCode accessCode = new AccessCode(null, "1");
            logger.debug("\u4f20\u5165\u60c5\u666f\u4e0e\u6307\u6807\u6240\u5728\u4efb\u52a1\u5173\u8054\u60c5\u666f\u5339\u914d\u4e0d\u4e0a,\u7ef4\u5ea6:{},\u6307\u6807:{}", (Object)masterKey, (Object)zbKey);
            return accessCode;
        }
        AccessCode accessCode = null;
        for (Map.Entry<String, IBatchMergeAccess> entry : this.accessCaches.getBatchAccessMap().entrySet()) {
            IBatchMergeAccess batchAccess = entry.getValue();
            accessCode = batchAccess.getAccessCode(merge);
            if (accessCode == null || accessCode.getCode().equals("1")) continue;
            if (logger.isDebugEnabled()) {
                logger.debug(String.format("\u72b6\u6001\u5f15\u64ce-[%s]-\u62a5\u8868\u6279\u91cf\u6743\u9650\u67e5\u8be2-\u6743\u9650\u540d[%s]-\u76f4\u63a5\u83b7\u53d6\u63a5\u53e3 \u53c2\u6570:masterKey:[%s];zbKey:[%s];\u7ed3\u679c:[%s]", new Object[]{this.accessType, entry.getKey(), masterKey, zbKey, accessCode.getCode()}));
            }
            return accessCode;
        }
        if (accessCode == null) {
            accessCode = new AccessCode("all");
        }
        if (logger.isDebugEnabled()) {
            logger.debug(String.format("\u72b6\u6001\u5f15\u64ce-[%s]-\u62a5\u8868\u6279\u91cf\u6743\u9650\u67e5\u8be2-\u6743\u9650\u540d[%s]-\u76f4\u63a5\u83b7\u53d6\u63a5\u53e3 \u53c2\u6570:masterKey:[%s];zbKey:[%s];\u7ed3\u679c:[%s]", new Object[]{"", this.accessType, masterKey, zbKey, accessCode.getCode()}));
        }
        return accessCode;
    }

    private AccessCode getAccessCodeFromDb(AccessCacheManager accessManager, DimensionCombination masterKey, String zbKey) {
        AccessCode accessCode = new AccessCode("");
        switch (this.accessType) {
            case VISIT: {
                this.accessCaches = accessManager.zbVisible(this.masterKeys, this.zbKeys, masterKey, zbKey);
                break;
            }
            case READ: {
                this.accessCaches = accessManager.zbReadable(this.masterKeys, this.zbKeys, masterKey, zbKey);
                break;
            }
            case WRITE: {
                this.accessCaches = accessManager.zbWriteable(this.masterKeys, this.zbKeys, masterKey, zbKey);
                break;
            }
            case SYS_WRITE: {
                this.accessCaches = accessManager.zbSysWriteable(this.masterKeys, this.zbKeys, masterKey, zbKey);
                break;
            }
            default: {
                throw new NotSupportedException("\u4e0d\u652f\u6301\u7684\u7c7b\u578b\uff01");
            }
        }
        if (this.accessCaches != null) {
            accessCode = this.accessCaches.getAccessCode();
        }
        return accessCode;
    }

    public Supplier<ZbAccessCaches> zbAccessCachesSupplier() {
        return () -> this.accessCaches;
    }
}

