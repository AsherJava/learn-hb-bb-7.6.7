/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.define.IBdePluginType
 *  com.jiuqi.bde.bizmodel.execute.FetchData
 *  com.jiuqi.bde.bizmodel.execute.intf.BalanceCondition
 *  com.jiuqi.bde.plugin.common.datamodel.assbalance.BaseAssBalanceDataLoader
 */
package com.jiuqi.bde.plugin.gs_cloud.fetch;

import com.jiuqi.bde.bizmodel.define.IBdePluginType;
import com.jiuqi.bde.bizmodel.execute.FetchData;
import com.jiuqi.bde.bizmodel.execute.intf.BalanceCondition;
import com.jiuqi.bde.plugin.common.datamodel.assbalance.BaseAssBalanceDataLoader;
import com.jiuqi.bde.plugin.gs_cloud.BdeGsCloudPluginType;
import com.jiuqi.bde.plugin.gs_cloud.fetch.BdeGsCloudBalanceDataProvider;
import com.jiuqi.bde.plugin.gs_cloud.util.GsCloudFetchUtil;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BdeGsCloudAssBalanceLoader
extends BaseAssBalanceDataLoader {
    @Autowired
    private BdeGsCloudBalanceDataProvider dataProvider;
    @Autowired
    private BdeGsCloudPluginType pluginType;

    public IBdePluginType getPluginType() {
        return this.pluginType;
    }

    public FetchData queryData(BalanceCondition condi) {
        this.buildTempTable(condi);
        return this.dataProvider.queryData(condi);
    }

    public Map<String, Integer> loadSubject(BalanceCondition condi) {
        return GsCloudFetchUtil.loadSubject(condi);
    }
}

