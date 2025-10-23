/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.exception.NotSupportedException
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 */
package com.jiuqi.nr.data.access.param;

import com.jiuqi.np.definition.exception.NotSupportedException;
import com.jiuqi.nr.data.access.common.AccessType;
import com.jiuqi.nr.data.access.param.AbstractAccessResult;
import com.jiuqi.nr.data.access.param.AccessCode;
import com.jiuqi.nr.data.access.param.AccessParam;
import com.jiuqi.nr.data.access.service.impl.AccessCacheManager;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AccessResult
extends AbstractAccessResult {
    private static final Logger logger = LoggerFactory.getLogger(AccessResult.class);

    public AccessResult(String taskKey, String formSchemeKey, DimensionCombination masterKey, String formKey, AccessType accessType, AccessCacheManager accessManager) {
        super(taskKey, formSchemeKey, masterKey, formKey, accessType, accessManager);
    }

    public AccessResult(AccessParam param, String taskKey, String formSchemeKey, DimensionCombination masterKey, String formKey, AccessType accessType, AccessCacheManager accessManager) {
        super(param, taskKey, formSchemeKey, masterKey, formKey, accessType, accessManager);
    }

    @Override
    public AccessCode getAccessCode(String taskKey, String formSchemeKey, DimensionCombination masterKey, String formKey) throws Exception {
        AccessCode accessCode = new AccessCode(null);
        switch (this.accessType) {
            case VISIT: {
                accessCode = this.accessManager.visible(this.param, taskKey, formSchemeKey, masterKey, formKey);
                break;
            }
            case READ: {
                accessCode = this.accessManager.readable(this.param, taskKey, formSchemeKey, masterKey, formKey);
                break;
            }
            case WRITE: {
                accessCode = this.accessManager.writeable(this.param, taskKey, formSchemeKey, masterKey, formKey);
                break;
            }
            case SYS_WRITE: {
                accessCode = this.accessManager.sysWriteable(this.param, taskKey, formSchemeKey, masterKey, formKey);
                break;
            }
            default: {
                throw new NotSupportedException("\u4e0d\u652f\u6301\u7684\u7c7b\u578b");
            }
        }
        if (logger.isDebugEnabled()) {
            logger.debug(String.format("\u72b6\u6001\u5f15\u64ce-getAccessItem-\u62a5\u8868\u53ef\u89c1\u6743\u9650 \u53c2\u6570\uff1ataskKey:[%s];formSchemeKey:[%s];masterKey:[%s];formKey:[%s],\u7ed3\u679c:[%s][%s]", taskKey, formSchemeKey, masterKey, formKey, accessCode.getName() == null ? "\u5168\u90e8" : accessCode.getName(), accessCode.getCode() == "1" ? "\u62e5\u6709\u6743\u9650" : "\u6ca1\u6709\u6743\u9650"));
        }
        return accessCode;
    }
}

