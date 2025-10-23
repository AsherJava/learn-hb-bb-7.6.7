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
import com.jiuqi.nr.data.access.param.AbstractAccessResult;
import com.jiuqi.nr.data.access.param.AccessCache;
import com.jiuqi.nr.data.access.param.AccessCaches;
import com.jiuqi.nr.data.access.param.AccessCode;
import com.jiuqi.nr.data.access.param.IBatchAccess;
import com.jiuqi.nr.data.access.service.impl.AccessCacheManager;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

public class AccessResultByCache
extends AbstractAccessResult {
    private static final Logger logger = LoggerFactory.getLogger(AccessResultByCache.class);
    private Map<String, IBatchAccess> batchAccessMap;
    private DimensionCollection masterKeys;
    private List<String> formKeys;
    private AccessType accessType;
    private AccessCacheManager accessManager;

    public AccessResultByCache(String taskKey, String formSchemeKey, DimensionCombination masterKey, String formKey, Map<String, IBatchAccess> batchAccessMap, DimensionCollection masterKeys, List<String> formKeys, AccessType accessType, AccessCacheManager accessCacheManager) {
        super(taskKey, formSchemeKey, masterKey, formKey, accessType, accessCacheManager);
        this.batchAccessMap = batchAccessMap;
        this.masterKeys = masterKeys;
        this.formKeys = formKeys;
        this.accessType = accessType;
        this.accessManager = accessCacheManager;
    }

    @Override
    public AccessCode getAccessCode(String taskKey, String formSchemeKey, DimensionCombination masterKey, String formKey) throws Exception {
        AccessCode accessCode = CollectionUtils.isEmpty(this.batchAccessMap) ? this.getAccessCodeFromDb(this.accessManager, taskKey, formSchemeKey, masterKey, formKey) : this.getAccessCodeFormCache(this.accessManager, masterKey, formKey);
        if (logger.isDebugEnabled()) {
            logger.debug(String.format("\u72b6\u6001\u5f15\u64ce-getAccessItem-\u62a5\u8868\u6279\u91cf\u53ef\u5199\u6743\u9650 \u53c2\u6570\uff1ataskKey:[%s];formSchemeKey:[%s];masterKey:[%s];formKey:[%s],\u7ed3\u679c:[%s][%s]", taskKey, formSchemeKey, masterKey, formKey, accessCode.getName() == null ? "\u5168\u90e8" : accessCode.getName(), accessCode.getCode() == "1" ? "\u62e5\u6709\u6743\u9650" : "\u6ca1\u6709\u6743\u9650"));
        }
        return accessCode;
    }

    private AccessCode getAccessCodeFromDb(AccessCacheManager accessManager, String taskKey, String formSchemeKey, DimensionCombination masterKey, String formKey) throws Exception {
        AccessCode accessCode = new AccessCode("");
        AccessCaches accessCache = null;
        AccessCache cache = new AccessCache();
        switch (this.accessType) {
            case VISIT: {
                accessCache = accessManager.visible(taskKey, formSchemeKey, this.masterKeys, masterKey, this.formKeys, formKey);
                break;
            }
            case READ: {
                accessCache = accessManager.readable(taskKey, formSchemeKey, this.masterKeys, masterKey, this.formKeys, formKey);
                break;
            }
            case WRITE: {
                accessCache = accessManager.writeable(taskKey, formSchemeKey, this.masterKeys, masterKey, this.formKeys, formKey);
                break;
            }
            case SYS_WRITE: {
                accessCache = accessManager.sysWriteable(taskKey, formSchemeKey, this.masterKeys, masterKey, this.formKeys, formKey);
                break;
            }
            default: {
                throw new NotSupportedException("\u4e0d\u652f\u6301\u7684\u7c7b\u578b\uff01");
            }
        }
        if (accessCache != null && !CollectionUtils.isEmpty(accessCache.getBatchAccessMap())) {
            this.batchAccessMap.putAll(accessCache.getBatchAccessMap());
            accessCode = accessCache.getAccessCode();
        }
        return accessCode;
    }

    private AccessCode getAccessCodeFormCache(AccessCacheManager accessManager, DimensionCombination masterKey, String formKey) throws Exception {
        AccessCode accessCode = new AccessCode("");
        for (Map.Entry<String, IBatchAccess> entry : this.batchAccessMap.entrySet()) {
            IBatchAccess batchAccess = entry.getValue();
            accessCode = batchAccess.getAccessCode(masterKey, formKey);
            if (accessCode.getCode().equals("1")) continue;
            return accessCode;
        }
        return accessCode;
    }
}

