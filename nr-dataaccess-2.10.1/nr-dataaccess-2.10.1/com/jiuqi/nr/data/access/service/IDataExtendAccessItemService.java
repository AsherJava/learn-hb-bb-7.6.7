/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 */
package com.jiuqi.nr.data.access.service;

import com.jiuqi.nr.data.access.param.AccessCode;
import com.jiuqi.nr.data.access.param.AccessItem;
import com.jiuqi.nr.data.access.param.FormBatchAccessCache;
import com.jiuqi.nr.data.access.param.IBatchAccess;
import com.jiuqi.nr.data.access.service.IDataAccessItemBaseService;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import java.util.List;

public interface IDataExtendAccessItemService
extends IDataAccessItemBaseService {
    public AccessCode visible(AccessItem var1, String var2, DimensionCombination var3, String var4);

    public AccessCode readable(AccessItem var1, String var2, DimensionCombination var3, String var4);

    public AccessCode writeable(AccessItem var1, String var2, DimensionCombination var3, String var4);

    default public AccessCode sysWriteable(AccessItem param, String formSchemeKey, DimensionCombination masterKey, String formKey) {
        return this.writeable(param, formSchemeKey, masterKey, formKey);
    }

    default public IBatchAccess getBatchVisible(String formSchemeKey, DimensionCollection masterKeys, List<String> formKeys) {
        return new FormBatchAccessCache(this.name(), formSchemeKey);
    }

    default public IBatchAccess getBatchReadable(String formSchemeKey, DimensionCollection masterKeys, List<String> formKeys) {
        return new FormBatchAccessCache(this.name(), formSchemeKey);
    }

    default public IBatchAccess getBatchWriteable(String formSchemeKey, DimensionCollection masterKeys, List<String> formKeys) {
        return new FormBatchAccessCache(this.name(), formSchemeKey);
    }

    default public IBatchAccess getSysBatchWriteable(String formSchemeKey, DimensionCollection masterKeys, List<String> formKeys) {
        return this.getBatchWriteable(formSchemeKey, masterKeys, formKeys);
    }

    default public boolean isServerAccess() {
        return false;
    }
}

