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
import java.util.List;

public abstract class AbstractZbAccessResult
implements IAccessResult {
    protected AccessParam param;
    private String taskKey;
    private String formSchemeKey;
    private DimensionCombination masterKey;
    private AccessCode noAccessItem;
    private List<String> formKeys;
    protected AccessType accessType;
    protected AccessCacheManager accessManager;

    public AbstractZbAccessResult(String taskKey, String formSchemeKey, DimensionCombination masterKey, String zbKey, AccessType accessType, AccessCacheManager accessCacheManager) {
        this.taskKey = taskKey;
        this.formSchemeKey = formSchemeKey;
        this.masterKey = masterKey;
        this.accessType = accessType;
        this.accessManager = accessCacheManager;
        this.formKeys = accessCacheManager.getFormKeys(zbKey, formSchemeKey);
    }

    public AbstractZbAccessResult(AccessParam param, String taskKey, String formSchemeKey, DimensionCombination masterKey, String zbKey, AccessType accessType, AccessCacheManager accessCacheManager) {
        this.param = param;
        this.taskKey = taskKey;
        this.formSchemeKey = formSchemeKey;
        this.masterKey = masterKey;
        this.accessType = accessType;
        this.accessManager = accessCacheManager;
        this.formKeys = accessCacheManager.getFormKeys(zbKey, formSchemeKey);
    }

    @Override
    public boolean haveAccess() throws Exception {
        if (this.noAccessItem == null) {
            for (String formKey : this.formKeys) {
                AccessCode accessItem = this.getAccessCode(this.taskKey, this.formSchemeKey, this.masterKey, formKey);
                if (accessItem.getCode() == "1") continue;
                this.noAccessItem = accessItem;
                return false;
            }
        }
        return this.noAccessItem == null;
    }

    @Override
    public String getMessage() throws Exception {
        if (this.noAccessItem == null) {
            for (String formKey : this.formKeys) {
                AccessCode accessItem = this.getAccessCode(this.taskKey, this.formSchemeKey, this.masterKey, formKey);
                if (accessItem.getCode() == "1") continue;
                this.noAccessItem = accessItem;
                AccessMessageManager accessMessageManager = (AccessMessageManager)BeanUtil.getBean(AccessMessageManager.class);
                return accessMessageManager.getMessage(accessItem.getName(), accessItem.getCode());
            }
        }
        if (this.noAccessItem == null) {
            return null;
        }
        AccessMessageManager accessMessageManager = (AccessMessageManager)BeanUtil.getBean(AccessMessageManager.class);
        return accessMessageManager.getMessage(this.noAccessItem.getName(), this.noAccessItem.getCode());
    }

    public abstract AccessCode getAccessCode(String var1, String var2, DimensionCombination var3, String var4) throws Exception;
}

