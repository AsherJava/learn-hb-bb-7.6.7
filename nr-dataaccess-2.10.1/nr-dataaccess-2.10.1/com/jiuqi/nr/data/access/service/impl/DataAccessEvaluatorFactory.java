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
import com.jiuqi.nr.data.access.service.impl.SimpleDataAccessEvaluator;
import com.jiuqi.nr.dataservice.core.access.DataPermissionEvaluator;
import com.jiuqi.nr.dataservice.core.access.DataPermissionEvaluatorFactory;
import com.jiuqi.nr.dataservice.core.access.EvaluatorParam;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import java.util.Collection;

public class DataAccessEvaluatorFactory
implements DataPermissionEvaluatorFactory {
    private final IDataAccessServiceProvider dataAccessProvider;

    public DataAccessEvaluatorFactory(IDataAccessServiceProvider dataAccessProvider) {
        this.dataAccessProvider = dataAccessProvider;
    }

    public DataPermissionEvaluator createEvaluator(EvaluatorParam evaluatorParam) {
        return new SimpleDataAccessEvaluator(this.dataAccessProvider, evaluatorParam);
    }

    public DataPermissionEvaluator createEvaluator(EvaluatorParam evaluatorParam, DimensionCombination combination, Collection<String> resourcesId) {
        return new SimpleDataAccessEvaluator(this.dataAccessProvider, evaluatorParam, combination, resourcesId);
    }

    public DataPermissionEvaluator createEvaluator(EvaluatorParam evaluatorParam, DimensionCollection collection, Collection<String> resourcesId) {
        return new SimpleDataAccessEvaluator(this.dataAccessProvider, evaluatorParam, collection, resourcesId);
    }
}

