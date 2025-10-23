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
package com.jiuqi.nr.migration.transferdata.dbservice;

import com.jiuqi.nr.data.access.service.DPEFactoryBuilder;
import com.jiuqi.nr.data.access.service.IDataAccessServiceProvider;
import com.jiuqi.nr.dataservice.core.access.DataPermissionEvaluatorFactory;
import com.jiuqi.nr.dataservice.core.common.IProviderStore;
import com.jiuqi.nr.dataservice.core.common.ProviderStore;

public class DBServiceUtil {
    private static final String IGNOREACCESSITEM = "formCondition";

    public static IProviderStore getProviderStore(IDataAccessServiceProvider iDataAccessServiceProvider) {
        DPEFactoryBuilder dpeFactoryBuilder = new DPEFactoryBuilder(iDataAccessServiceProvider);
        dpeFactoryBuilder.ignorePermission(IGNOREACCESSITEM);
        DataPermissionEvaluatorFactory dataPermissionEvaFactory = dpeFactoryBuilder.build();
        return new ProviderStore(dataPermissionEvaFactory);
    }
}

