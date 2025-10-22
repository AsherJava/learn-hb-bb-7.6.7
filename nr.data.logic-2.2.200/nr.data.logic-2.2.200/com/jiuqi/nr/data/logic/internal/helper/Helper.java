/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.data.access.service.DPEFactoryBuilder
 *  com.jiuqi.nr.data.access.service.IDataAccessServiceProvider
 *  com.jiuqi.nr.dataservice.core.access.DataPermissionEvaluatorFactory
 *  com.jiuqi.nr.dataservice.core.common.IProviderStore
 *  com.jiuqi.nr.dataservice.core.common.ProviderStore
 */
package com.jiuqi.nr.data.logic.internal.helper;

import com.jiuqi.nr.data.access.service.DPEFactoryBuilder;
import com.jiuqi.nr.data.access.service.IDataAccessServiceProvider;
import com.jiuqi.nr.dataservice.core.access.DataPermissionEvaluatorFactory;
import com.jiuqi.nr.dataservice.core.common.IProviderStore;
import com.jiuqi.nr.dataservice.core.common.ProviderStore;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class Helper {
    @Autowired
    private IProviderStore providerStore;
    @Autowired
    private IDataAccessServiceProvider dataAccessServiceProvider;

    public IProviderStore getProviderStore(Set<String> ignoreItems) {
        if (CollectionUtils.isEmpty(ignoreItems)) {
            return this.providerStore;
        }
        DPEFactoryBuilder dpeFactoryBuilder = new DPEFactoryBuilder(this.dataAccessServiceProvider);
        ignoreItems.forEach(arg_0 -> ((DPEFactoryBuilder)dpeFactoryBuilder).ignorePermission(arg_0));
        DataPermissionEvaluatorFactory dataPermissionEvaluatorFactory = dpeFactoryBuilder.build();
        return new ProviderStore(dataPermissionEvaluatorFactory);
    }
}

