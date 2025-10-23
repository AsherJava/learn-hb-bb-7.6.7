/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.definition.internal.BeanUtil
 */
package com.jiuqi.nr.data.access.param;

import com.jiuqi.nr.data.access.common.AccessType;
import com.jiuqi.nr.data.access.exception.AccessException;
import com.jiuqi.nr.data.access.param.AccessCode;
import com.jiuqi.nr.data.access.param.IAccessResult;
import com.jiuqi.nr.data.access.service.impl.AccessCacheManager;
import com.jiuqi.nr.data.access.service.impl.AccessMessageManager;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.definition.internal.BeanUtil;
import java.util.Objects;

public abstract class AbstractAccessRes
implements IAccessResult {
    protected AccessType accessType;
    protected AccessCacheManager accessManager;
    protected DimensionCombination masterKey;
    protected String zbKey;
    protected AccessCode accessCode;

    public AbstractAccessRes(AccessType accessType, DimensionCombination masterKey, String zbKey, AccessCacheManager accessManager) {
        this.accessType = accessType;
        this.accessManager = accessManager;
        this.masterKey = masterKey;
        this.zbKey = zbKey;
    }

    @Override
    public boolean haveAccess() {
        if (this.accessCode == null) {
            this.accessCode = this.getAccessCode(this.masterKey, this.zbKey);
        }
        return Objects.equals(this.accessCode.getCode(), "1");
    }

    @Override
    public String getMessage() throws Exception {
        if (this.accessCode == null) {
            this.accessCode = this.getAccessCode(this.masterKey, this.zbKey);
        }
        if (Objects.equals(this.accessCode.getCode(), "1")) {
            return null;
        }
        AccessMessageManager accessMessageManager = (AccessMessageManager)BeanUtil.getBean(AccessMessageManager.class);
        return accessMessageManager.getMessage(this.accessCode.getName(), this.accessCode.getCode());
    }

    public abstract AccessCode getAccessCode(DimensionCombination var1, String var2) throws AccessException;
}

