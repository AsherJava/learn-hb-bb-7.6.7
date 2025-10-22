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
import com.jiuqi.nr.data.access.exception.AccessException;
import com.jiuqi.nr.data.access.param.AbstractAccessRes;
import com.jiuqi.nr.data.access.param.AccessCode;
import com.jiuqi.nr.data.access.service.impl.AccessCacheManager;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ZbAccessRes
extends AbstractAccessRes {
    private static final Logger logger = LoggerFactory.getLogger(ZbAccessRes.class);

    public ZbAccessRes(AccessType accessType, DimensionCombination masterKey, String zbKey, AccessCacheManager accessManager) {
        super(accessType, masterKey, zbKey, accessManager);
    }

    @Override
    public AccessCode getAccessCode(DimensionCombination masterKey, String zbKey) throws AccessException {
        AccessCode accessCode;
        switch (this.accessType) {
            case VISIT: {
                accessCode = this.accessManager.zbVisible(masterKey, zbKey);
                break;
            }
            case READ: {
                accessCode = this.accessManager.zbReadable(masterKey, zbKey);
                break;
            }
            case WRITE: {
                accessCode = this.accessManager.zbWriteable(masterKey, zbKey);
                break;
            }
            case SYS_WRITE: {
                accessCode = this.accessManager.zbSysWriteable(masterKey, zbKey);
                break;
            }
            default: {
                throw new NotSupportedException("\u4e0d\u652f\u6301\u7684\u7c7b\u578b");
            }
        }
        if (logger.isDebugEnabled()) {
            logger.debug(String.format("\u72b6\u6001\u5f15\u64ce-getAccessItem-\u6307\u6807[%s]\u6743\u9650 \u53c2\u6570\uff1amasterKey:[%s];zbKey:[%s],\u7ed3\u679c:[%s][%s]", this.accessType.getValue(), masterKey, zbKey, accessCode.getName() == null ? "\u5168\u90e8" : accessCode.getName(), Objects.equals(accessCode.getCode(), "1") ? "\u62e5\u6709\u6743\u9650" : "\u6ca1\u6709\u6743\u9650"));
        }
        return accessCode;
    }
}

