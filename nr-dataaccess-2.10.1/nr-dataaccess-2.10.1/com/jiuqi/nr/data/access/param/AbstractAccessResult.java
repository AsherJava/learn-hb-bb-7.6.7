/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.definition.internal.BeanUtil
 */
package com.jiuqi.nr.data.access.param;

import com.jiuqi.nr.data.access.common.AccessType;
import com.jiuqi.nr.data.access.param.AccessCode;
import com.jiuqi.nr.data.access.param.AccessParam;
import com.jiuqi.nr.data.access.param.IAccessResult;
import com.jiuqi.nr.data.access.service.impl.AccessCacheManager;
import com.jiuqi.nr.data.access.service.impl.AccessMessageManager;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.definition.internal.BeanUtil;

public abstract class AbstractAccessResult
implements IAccessResult {
    protected AccessParam param;
    private String taskKey;
    private String formSchemeKey;
    private DimensionCombination masterKey;
    private String formKey;
    private AccessCode accessCode;
    protected AccessType accessType;
    protected AccessCacheManager accessManager;

    public AbstractAccessResult(String taskKey, String formSchemeKey, DimensionCombination masterKey, String formKey, AccessType accessType, AccessCacheManager accessManager) {
        this.taskKey = taskKey;
        this.formSchemeKey = formSchemeKey;
        this.masterKey = masterKey;
        this.formKey = formKey;
        this.accessType = accessType;
        this.accessManager = accessManager;
    }

    public AbstractAccessResult(AccessParam param, String taskKey, String formSchemeKey, DimensionCombination masterKey, String formKey, AccessType accessType, AccessCacheManager accessManager) {
        this.param = param;
        this.taskKey = taskKey;
        this.formSchemeKey = formSchemeKey;
        this.masterKey = masterKey;
        this.formKey = formKey;
        this.accessType = accessType;
        this.accessManager = accessManager;
    }

    @Override
    public boolean haveAccess() throws Exception {
        if (this.accessCode == null) {
            this.accessCode = this.getAccessCode(this.taskKey, this.formSchemeKey, this.masterKey, this.formKey);
        }
        return this.accessCode.getCode() == "1";
    }

    @Override
    public String getMessage() throws Exception {
        if (this.accessCode == null) {
            this.accessCode = this.getAccessCode(this.taskKey, this.formSchemeKey, this.masterKey, this.formKey);
        }
        if (this.accessCode.getCode() == "1") {
            return null;
        }
        AccessMessageManager accessMessageManager = (AccessMessageManager)BeanUtil.getBean(AccessMessageManager.class);
        return accessMessageManager.getMessage(this.accessCode.getName(), this.accessCode.getCode());
    }

    public abstract AccessCode getAccessCode(String var1, String var2, DimensionCombination var3, String var4) throws Exception;
}

