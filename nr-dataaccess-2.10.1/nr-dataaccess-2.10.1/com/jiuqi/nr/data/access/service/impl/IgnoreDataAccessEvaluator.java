/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataservice.core.access.AuthType
 *  com.jiuqi.nr.dataservice.core.access.DataPermission
 *  com.jiuqi.nr.dataservice.core.access.DataPermissionException
 *  com.jiuqi.nr.dataservice.core.access.DataPermissionResource
 *  com.jiuqi.nr.dataservice.core.access.EvaluatorParam
 *  com.jiuqi.nr.dataservice.core.access.MergeDataPermission
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 */
package com.jiuqi.nr.data.access.service.impl;

import com.jiuqi.nr.data.access.service.IDataAccessServiceProvider;
import com.jiuqi.nr.data.access.service.impl.SimpleDataAccessEvaluator;
import com.jiuqi.nr.dataservice.core.access.AuthType;
import com.jiuqi.nr.dataservice.core.access.DataPermission;
import com.jiuqi.nr.dataservice.core.access.DataPermissionException;
import com.jiuqi.nr.dataservice.core.access.DataPermissionResource;
import com.jiuqi.nr.dataservice.core.access.EvaluatorParam;
import com.jiuqi.nr.dataservice.core.access.MergeDataPermission;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class IgnoreDataAccessEvaluator
extends SimpleDataAccessEvaluator {
    private Set<String> ignoreItems = null;
    private boolean ignoreAll = false;

    public IgnoreDataAccessEvaluator(IDataAccessServiceProvider dataAccessProvider, EvaluatorParam evaluatorParam) {
        super(dataAccessProvider, evaluatorParam);
    }

    public IgnoreDataAccessEvaluator(IDataAccessServiceProvider dataAccessProvider, EvaluatorParam evaluatorParam, DimensionCollection dimensionCollection, Collection<String> resourcesIds) {
        super(dataAccessProvider, evaluatorParam, dimensionCollection, resourcesIds);
    }

    public IgnoreDataAccessEvaluator(IDataAccessServiceProvider dataAccessProvider, EvaluatorParam evaluatorParam, DimensionCombination dimensionCombination, Collection<String> resourcesIds) {
        super(dataAccessProvider, evaluatorParam, dimensionCombination, resourcesIds);
    }

    public void setIgnoreItems(Set<String> ignoreItems) {
        this.ignoreItems = ignoreItems;
    }

    public void setIgnoreAll(boolean ignoreAll) {
        this.ignoreAll = ignoreAll;
    }

    @Override
    public boolean haveAccess(DimensionCombination masterKey, String resourcesId, AuthType authType) throws DataPermissionException {
        return super.haveAccess(masterKey, resourcesId, authType, this.ignoreItems);
    }

    @Override
    public boolean haveAccess(DimensionCombination masterKey, String resourcesId, AuthType authType, Set<String> ignoreItems) throws DataPermissionException {
        if (this.ignoreAll) {
            return true;
        }
        if (ignoreItems == null) {
            ignoreItems = new HashSet<String>();
        }
        if (this.ignoreItems != null) {
            ignoreItems.addAll(this.ignoreItems);
        }
        return super.haveAccess(masterKey, resourcesId, authType, ignoreItems);
    }

    @Override
    public DataPermission haveAccess(DimensionCollection collection, Collection<String> resourcesIds, AuthType authType) throws DataPermissionException {
        return this.haveAccess(collection, resourcesIds, authType, null);
    }

    @Override
    public DataPermission haveAccess(DimensionCollection collection, Collection<String> resourcesIds, AuthType authType, Set<String> ignoreItems) throws DataPermissionException {
        if (this.ignoreAll) {
            DataPermission dataPermission = new DataPermission();
            dataPermission.setResourceIds(resourcesIds);
            ArrayList<DataPermissionResource> accessRes = new ArrayList<DataPermissionResource>();
            for (DimensionCombination dimensionCombination : collection.getDimensionCombinations()) {
                for (String resourcesId : resourcesIds) {
                    DataPermissionResource res = new DataPermissionResource();
                    res.setDimensionCombination(dimensionCombination);
                    res.setResourceId(resourcesId);
                    accessRes.add(res);
                }
            }
            dataPermission.setAccessResources(accessRes);
            return dataPermission;
        }
        if (ignoreItems == null) {
            ignoreItems = new HashSet<String>();
        }
        if (this.ignoreItems != null) {
            ignoreItems.addAll(this.ignoreItems);
        }
        return super.haveAccess(collection, resourcesIds, authType, ignoreItems);
    }

    @Override
    public MergeDataPermission mergeAccess(DimensionCollection collection, Collection<String> resourcesIds, AuthType authType) throws DataPermissionException {
        return this.mergeAccess(collection, resourcesIds, authType, null);
    }

    @Override
    public MergeDataPermission mergeAccess(DimensionCollection collection, Collection<String> resourcesIds, AuthType authType, Set<String> ignoreItems) throws DataPermissionException {
        if (this.ignoreAll) {
            throw new UnsupportedOperationException("\u5ffd\u7565\u6240\u6709\u6743\u9650\u5224\u65ad\u6682\u4e0d\u652f\u6301\uff01");
        }
        if (ignoreItems == null) {
            ignoreItems = new HashSet<String>();
        }
        if (this.ignoreItems != null) {
            ignoreItems.addAll(this.ignoreItems);
        }
        return super.mergeAccess(collection, resourcesIds, authType, ignoreItems);
    }
}

