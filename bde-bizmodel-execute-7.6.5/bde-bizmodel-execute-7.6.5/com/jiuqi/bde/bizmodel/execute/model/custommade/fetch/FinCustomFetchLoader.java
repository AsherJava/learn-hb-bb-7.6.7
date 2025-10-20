/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.define.FetchResult
 *  com.jiuqi.bde.bizmodel.define.IBdePluginType
 *  com.jiuqi.bde.bizmodel.define.IBizDataModelLoader
 *  com.jiuqi.bde.bizmodel.impl.model.service.BizModelService
 *  com.jiuqi.bde.common.constant.BizDataModelEnum
 *  com.jiuqi.bde.common.constant.ComputationModelEnum
 *  com.jiuqi.dc.mappingscheme.impl.service.DataSourceService
 */
package com.jiuqi.bde.bizmodel.execute.model.custommade.fetch;

import com.jiuqi.bde.bizmodel.define.FetchResult;
import com.jiuqi.bde.bizmodel.define.IBdePluginType;
import com.jiuqi.bde.bizmodel.define.IBizDataModelLoader;
import com.jiuqi.bde.bizmodel.execute.model.custommade.fetch.CustomFetchCondi;
import com.jiuqi.bde.bizmodel.impl.model.service.BizModelService;
import com.jiuqi.bde.common.constant.BizDataModelEnum;
import com.jiuqi.bde.common.constant.ComputationModelEnum;
import com.jiuqi.dc.mappingscheme.impl.service.DataSourceService;
import java.util.Collection;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

@Deprecated
abstract class FinCustomFetchLoader
implements IBizDataModelLoader<CustomFetchCondi, Collection<FetchResult>> {
    @Autowired
    protected BizModelService bizModelService;
    @Autowired
    protected DataSourceService dataSourceService;

    FinCustomFetchLoader() {
    }

    public String getBizDataModelCode() {
        return BizDataModelEnum.CUSTOMFETCHMODEL.getCode();
    }

    public String getComputationModelCode() {
        return ComputationModelEnum.CUSTOMFETCH.getCode();
    }

    public IBdePluginType getPluginType() {
        return null;
    }

    public abstract List<FetchResult> loadData(CustomFetchCondi var1);
}

