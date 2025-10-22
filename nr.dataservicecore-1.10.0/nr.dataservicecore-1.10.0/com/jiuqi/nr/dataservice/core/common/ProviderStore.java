/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataservice.core.common;

import com.jiuqi.nr.dataservice.core.access.DataPermissionEvaluatorFactory;
import com.jiuqi.nr.dataservice.core.common.IProviderStore;

public class ProviderStore
implements IProviderStore {
    private final DataPermissionEvaluatorFactory dataPermissionEvaluatorFactory;

    public ProviderStore(DataPermissionEvaluatorFactory dataPermissionEvaluatorFactory) {
        this.dataPermissionEvaluatorFactory = dataPermissionEvaluatorFactory;
    }

    @Override
    public DataPermissionEvaluatorFactory getDataPermissionEvaluatorFactory() {
        return this.dataPermissionEvaluatorFactory;
    }
}

