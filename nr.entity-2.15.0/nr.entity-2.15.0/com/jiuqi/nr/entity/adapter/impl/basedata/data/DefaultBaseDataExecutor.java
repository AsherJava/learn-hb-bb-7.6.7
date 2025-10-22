/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.authz2.service.SystemIdentityService
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  com.jiuqi.va.basedata.auth.service.VaBaseDataAuthService
 *  com.jiuqi.va.feign.client.BaseDataClient
 *  com.jiuqi.va.feign.client.BaseDataDefineClient
 *  com.jiuqi.va.feign.client.DataModelClient
 */
package com.jiuqi.nr.entity.adapter.impl.basedata.data;

import com.jiuqi.np.authz2.service.SystemIdentityService;
import com.jiuqi.nr.entity.adapter.executor.IExtendDataExecutor;
import com.jiuqi.nr.entity.adapter.impl.basedata.BaseDataAdapterImpl;
import com.jiuqi.nr.entity.adapter.impl.basedata.BaseDataDataCheck;
import com.jiuqi.nr.entity.adapter.impl.basedata.data.BaseDataDefineQuery;
import com.jiuqi.nr.entity.adapter.impl.basedata.data.BaseDataModifier;
import com.jiuqi.nr.entity.adapter.impl.basedata.data.BaseDataQuery;
import com.jiuqi.nr.entity.adapter.provider.IDataModifyProvider;
import com.jiuqi.nr.entity.adapter.provider.IDataQueryProvider;
import com.jiuqi.nr.entity.adapter.provider.IDefineQueryProvider;
import com.jiuqi.nr.entity.adapter.provider.ProviderMethodEnum;
import com.jiuqi.nr.entity.param.IEntityQueryParam;
import com.jiuqi.nvwa.definition.service.DataModelService;
import com.jiuqi.va.basedata.auth.service.VaBaseDataAuthService;
import com.jiuqi.va.feign.client.BaseDataClient;
import com.jiuqi.va.feign.client.BaseDataDefineClient;
import com.jiuqi.va.feign.client.DataModelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component(value="defaultBaseDataExecutor")
public class DefaultBaseDataExecutor
implements IExtendDataExecutor<BaseDataAdapterImpl> {
    @Autowired
    private BaseDataDefineClient baseDataDefineClient;
    @Autowired
    private DataModelClient vaDataModelClient;
    @Autowired
    private DataModelService dataModelService;
    @Autowired
    private BaseDataClient baseDataClient;
    @Autowired
    private SystemIdentityService systemIdentityService;
    @Autowired
    private BaseDataDataCheck basDataDataCheck;
    @Autowired
    private VaBaseDataAuthService baseDataAuthService;

    @Override
    public boolean isEnable(IEntityQueryParam queryParam, ProviderMethodEnum providerEnum) {
        return true;
    }

    @Override
    public double getOrder() {
        return 10.0;
    }

    @Override
    public IDataQueryProvider getDataQueryProvider(IEntityQueryParam queryParam) {
        return new BaseDataQuery(this.baseDataDefineClient, queryParam, this.baseDataClient);
    }

    @Override
    public IDataModifyProvider getDataModifyProvider() {
        return new BaseDataModifier(this.baseDataClient, this.baseDataDefineClient, this.systemIdentityService, this.basDataDataCheck, this.baseDataAuthService);
    }

    @Override
    public IDefineQueryProvider getOrgDefineProvider() {
        return new BaseDataDefineQuery(this.baseDataDefineClient, this.vaDataModelClient, this.dataModelService);
    }
}

