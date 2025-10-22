/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 */
package com.jiuqi.nr.data.access.service;

import com.jiuqi.nr.data.access.exception.AccessException;
import com.jiuqi.nr.data.access.param.AccessCode;
import com.jiuqi.nr.data.access.param.IAccessFormMerge;
import com.jiuqi.nr.data.access.param.IBatchAccess;
import com.jiuqi.nr.data.access.param.IBatchAccessFormMerge;
import com.jiuqi.nr.data.access.param.IBatchMergeAccess;
import com.jiuqi.nr.data.access.service.IDataAccessItemBaseService;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import java.util.List;

public interface IDataAccessItemService
extends IDataAccessItemBaseService {
    public AccessCode visible(String var1, DimensionCombination var2, String var3) throws AccessException;

    public AccessCode readable(String var1, DimensionCombination var2, String var3) throws AccessException;

    public AccessCode writeable(String var1, DimensionCombination var2, String var3) throws AccessException;

    default public AccessCode sysWriteable(String formSchemeKey, DimensionCombination masterKey, String formKey) throws AccessException {
        return this.writeable(formSchemeKey, masterKey, formKey);
    }

    public IBatchAccess getBatchVisible(String var1, DimensionCollection var2, List<String> var3) throws AccessException;

    public IBatchAccess getBatchReadable(String var1, DimensionCollection var2, List<String> var3) throws AccessException;

    public IBatchAccess getBatchWriteable(String var1, DimensionCollection var2, List<String> var3) throws AccessException;

    default public IBatchAccess getSysBatchWriteable(String formSchemeKey, DimensionCollection masterKeys, List<String> formKeys) throws AccessException {
        return this.getBatchWriteable(formSchemeKey, masterKeys, formKeys);
    }

    default public AccessCode visible(IAccessFormMerge mergeParam) throws AccessException {
        return null;
    }

    default public AccessCode readable(IAccessFormMerge mergeParam) throws AccessException {
        return null;
    }

    default public AccessCode writeable(IAccessFormMerge mergeParam) throws AccessException {
        return null;
    }

    default public AccessCode sysWriteable(IAccessFormMerge mergeParam) throws AccessException {
        return this.writeable(mergeParam);
    }

    default public IBatchMergeAccess getBatchVisible(IBatchAccessFormMerge mergeParam) throws AccessException {
        return null;
    }

    default public IBatchMergeAccess getBatchReadable(IBatchAccessFormMerge mergeParam) throws AccessException {
        return null;
    }

    default public IBatchMergeAccess getBatchWriteable(IBatchAccessFormMerge mergeParam) throws AccessException {
        return null;
    }

    default public IBatchMergeAccess getSysBatchWriteable(IBatchAccessFormMerge mergeParam) throws AccessException {
        return this.getBatchWriteable(mergeParam);
    }
}

