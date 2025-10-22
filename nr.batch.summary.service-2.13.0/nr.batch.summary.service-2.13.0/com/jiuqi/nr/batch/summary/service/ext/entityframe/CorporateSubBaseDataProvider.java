/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.entity.adapter.impl.basedata.data.BaseDataQuery
 *  com.jiuqi.nr.entity.engine.result.EntityResultSet
 *  com.jiuqi.nr.entity.param.IEntityQueryParam
 *  com.jiuqi.va.feign.client.BaseDataClient
 *  com.jiuqi.va.feign.client.BaseDataDefineClient
 */
package com.jiuqi.nr.batch.summary.service.ext.entityframe;

import com.jiuqi.nr.batch.summary.service.ext.entityframe.CorporateEntityData;
import com.jiuqi.nr.batch.summary.service.ext.entityframe.CorporateSubBaseDataRowData;
import com.jiuqi.nr.entity.adapter.impl.basedata.data.BaseDataQuery;
import com.jiuqi.nr.entity.engine.result.EntityResultSet;
import com.jiuqi.nr.entity.param.IEntityQueryParam;
import com.jiuqi.va.feign.client.BaseDataClient;
import com.jiuqi.va.feign.client.BaseDataDefineClient;

public class CorporateSubBaseDataProvider
extends BaseDataQuery {
    private CorporateEntityData corporateEntityData;

    public CorporateSubBaseDataProvider(BaseDataDefineClient baseDataDefineClient, IEntityQueryParam queryParam, BaseDataClient baseDataClient, CorporateEntityData corporateEntityData) {
        super(baseDataDefineClient, queryParam, baseDataClient);
        this.corporateEntityData = corporateEntityData;
    }

    public EntityResultSet getAllData() {
        return new CorporateSubBaseDataRowData(1, this.corporateEntityData);
    }
}

