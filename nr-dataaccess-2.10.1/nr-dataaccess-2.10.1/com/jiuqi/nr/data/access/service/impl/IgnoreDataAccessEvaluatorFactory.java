/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataservice.core.access.DataPermissionEvaluator
 *  com.jiuqi.nr.dataservice.core.access.DataPermissionEvaluatorFactory
 *  com.jiuqi.nr.dataservice.core.access.EvaluatorParam
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 */
package com.jiuqi.nr.data.access.service.impl;

import com.jiuqi.nr.data.access.service.IDataAccessServiceProvider;
import com.jiuqi.nr.data.access.service.impl.IgnoreDataAccessEvaluator;
import com.jiuqi.nr.dataservice.core.access.DataPermissionEvaluator;
import com.jiuqi.nr.dataservice.core.access.DataPermissionEvaluatorFactory;
import com.jiuqi.nr.dataservice.core.access.EvaluatorParam;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import java.util.Collection;
import java.util.Set;

public class IgnoreDataAccessEvaluatorFactory
implements DataPermissionEvaluatorFactory {
    private Set<String> ignoreItems = null;
    private boolean ignoreAll = false;
    private IDataAccessServiceProvider dataAccessProvider;

    public void setDataAccessProvider(IDataAccessServiceProvider dataAccessProvider) {
        this.dataAccessProvider = dataAccessProvider;
    }

    public void setIgnoreItems(Set<String> ignoreItems) {
        this.ignoreItems = ignoreItems;
    }

    public void setIgnoreAll(boolean ignoreAll) {
        this.ignoreAll = ignoreAll;
    }

    public DataPermissionEvaluator createEvaluator(EvaluatorParam evaluatorParam) {
        IgnoreDataAccessEvaluator accessEvaluator = new IgnoreDataAccessEvaluator(this.dataAccessProvider, evaluatorParam);
        accessEvaluator.setIgnoreAll(this.ignoreAll);
        accessEvaluator.setIgnoreItems(this.ignoreItems);
        return accessEvaluator;
    }

    public DataPermissionEvaluator createEvaluator(EvaluatorParam evaluatorParam, DimensionCombination combination, Collection<String> resourcesId) {
        IgnoreDataAccessEvaluator accessEvaluator = new IgnoreDataAccessEvaluator(this.dataAccessProvider, evaluatorParam, combination, resourcesId);
        accessEvaluator.setIgnoreAll(this.ignoreAll);
        accessEvaluator.setIgnoreItems(this.ignoreItems);
        return accessEvaluator;
    }

    public DataPermissionEvaluator createEvaluator(EvaluatorParam evaluatorParam, DimensionCollection collection, Collection<String> resourcesId) {
        IgnoreDataAccessEvaluator accessEvaluator = new IgnoreDataAccessEvaluator(this.dataAccessProvider, evaluatorParam, collection, resourcesId);
        accessEvaluator.setIgnoreAll(this.ignoreAll);
        accessEvaluator.setIgnoreItems(this.ignoreItems);
        return accessEvaluator;
    }
}

