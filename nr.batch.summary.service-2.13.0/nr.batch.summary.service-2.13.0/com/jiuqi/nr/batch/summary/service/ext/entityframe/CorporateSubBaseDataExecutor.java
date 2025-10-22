/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.nr.batch.summary.storage.entity.SummaryScheme
 *  com.jiuqi.nr.entity.adapter.impl.basedata.data.DefaultBaseDataExecutor
 *  com.jiuqi.nr.entity.adapter.provider.IDataQueryProvider
 *  com.jiuqi.nr.entity.adapter.provider.ProviderMethodEnum
 *  com.jiuqi.nr.entity.param.IEntityQueryParam
 *  com.jiuqi.va.feign.client.BaseDataClient
 *  com.jiuqi.va.feign.client.BaseDataDefineClient
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.batch.summary.service.ext.entityframe;

import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.nr.batch.summary.service.ext.entityframe.CorporateEntityData;
import com.jiuqi.nr.batch.summary.service.ext.entityframe.CorporateSubBaseDataProvider;
import com.jiuqi.nr.batch.summary.service.ext.entityframe.EntityFrameExtendHelper;
import com.jiuqi.nr.batch.summary.storage.entity.SummaryScheme;
import com.jiuqi.nr.entity.adapter.impl.basedata.data.DefaultBaseDataExecutor;
import com.jiuqi.nr.entity.adapter.provider.IDataQueryProvider;
import com.jiuqi.nr.entity.adapter.provider.ProviderMethodEnum;
import com.jiuqi.nr.entity.param.IEntityQueryParam;
import com.jiuqi.va.feign.client.BaseDataClient;
import com.jiuqi.va.feign.client.BaseDataDefineClient;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CorporateSubBaseDataExecutor
extends DefaultBaseDataExecutor {
    @Autowired
    private BaseDataClient baseDataClient;
    @Autowired
    private BaseDataDefineClient baseDataDefineClient;
    @Resource
    private EntityFrameExtendHelper entityFrameExtendHelper;

    public boolean isEnable(IEntityQueryParam queryParam, ProviderMethodEnum providerEnum) {
        if (this.entityFrameExtendHelper.isBatchSummaryEnv(queryParam, providerEnum)) {
            return this.getCorporateEntityData(queryParam) != null;
        }
        return false;
    }

    public IDataQueryProvider getDataQueryProvider(IEntityQueryParam queryParam) {
        CorporateEntityData corporateEntityData = this.getCorporateEntityData(queryParam);
        return new CorporateSubBaseDataProvider(this.baseDataDefineClient, queryParam, this.baseDataClient, corporateEntityData);
    }

    private CorporateEntityData getCorporateEntityData(IEntityQueryParam queryParam) {
        SummaryScheme summaryScheme = this.entityFrameExtendHelper.getSummarySchemeFromEnv((IContext)queryParam.getContext());
        Map<String, CorporateEntityData> corporateColumn2Value = this.entityFrameExtendHelper.getCorporateColumn2Value(summaryScheme);
        for (String dimAttr : corporateColumn2Value.keySet()) {
            CorporateEntityData attrValue = corporateColumn2Value.get(dimAttr);
            if (!attrValue.getEntityCode().equals(queryParam.getEntityId()) && !attrValue.getEntityId().equals(queryParam.getEntityId())) continue;
            return attrValue;
        }
        return null;
    }
}

