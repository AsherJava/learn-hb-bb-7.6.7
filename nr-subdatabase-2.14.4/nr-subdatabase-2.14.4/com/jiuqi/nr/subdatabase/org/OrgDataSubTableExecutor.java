/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.entity.adapter.impl.org.data.DefaultOrgDataExecutor
 *  com.jiuqi.nr.entity.adapter.provider.IDataModifyProvider
 *  com.jiuqi.nr.entity.adapter.provider.IDataQueryProvider
 *  com.jiuqi.nr.entity.adapter.provider.ProviderMethodEnum
 *  com.jiuqi.nr.entity.param.IEntityQueryParam
 */
package com.jiuqi.nr.subdatabase.org;

import com.jiuqi.nr.entity.adapter.impl.org.data.DefaultOrgDataExecutor;
import com.jiuqi.nr.entity.adapter.provider.IDataModifyProvider;
import com.jiuqi.nr.entity.adapter.provider.IDataQueryProvider;
import com.jiuqi.nr.entity.adapter.provider.ProviderMethodEnum;
import com.jiuqi.nr.entity.param.IEntityQueryParam;
import com.jiuqi.nr.subdatabase.facade.SubDataBaseEntityIdProvider;
import com.jiuqi.nr.subdatabase.org.ext.OrgDataSubTableModifyProvider;
import com.jiuqi.nr.subdatabase.org.ext.OrgDataSubTableQueryProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class OrgDataSubTableExecutor
extends DefaultOrgDataExecutor {
    @Autowired(required=false)
    private SubDataBaseEntityIdProvider subDataBaseEntityIdProvider;

    public boolean isEnable(IEntityQueryParam queryParam, ProviderMethodEnum providerEnum) {
        return super.isEnable(queryParam, providerEnum);
    }

    public double getOrder() {
        return 5.0;
    }

    public IDataQueryProvider getDataQueryProvider(IEntityQueryParam queryParam) {
        if (this.subDataBaseEntityIdProvider == null) {
            return super.getDataQueryProvider(queryParam);
        }
        String entityId = queryParam.getEntityId();
        String subDataBaseEntityId = this.subDataBaseEntityIdProvider.getSubDataBaseEntityId(entityId, queryParam.getContext());
        if (!StringUtils.hasText(subDataBaseEntityId) || subDataBaseEntityId.equals(entityId)) {
            return super.getDataQueryProvider(queryParam);
        }
        return new OrgDataSubTableQueryProvider(this.getOrgDataSource(), queryParam, subDataBaseEntityId);
    }

    public IDataModifyProvider getDataModifyProvider() {
        if (this.subDataBaseEntityIdProvider == null) {
            return super.getDataModifyProvider();
        }
        return new OrgDataSubTableModifyProvider(this.systemIdentityService, this.orgAdapterClient, this.orgDataCheck, this.orgDataClient, this.subDataBaseEntityIdProvider, this.orgAuthService);
    }
}

