/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.define.IBdePluginType
 *  com.jiuqi.bde.bizmodel.execute.FetchData
 *  com.jiuqi.bde.bizmodel.execute.intf.BalanceCondition
 *  com.jiuqi.bde.plugin.common.datamodel.balance.BaseBalanceDataLoader
 */
package com.jiuqi.bde.plugin.cloud_acca.fetch;

import com.jiuqi.bde.bizmodel.define.IBdePluginType;
import com.jiuqi.bde.bizmodel.execute.FetchData;
import com.jiuqi.bde.bizmodel.execute.intf.BalanceCondition;
import com.jiuqi.bde.plugin.cloud_acca.BdeCloudAccaPluginType;
import com.jiuqi.bde.plugin.cloud_acca.fetch.BdeCloudAccaBalanceDataProvider;
import com.jiuqi.bde.plugin.cloud_acca.util.CloudAccaFetchUtil;
import com.jiuqi.bde.plugin.common.datamodel.balance.BaseBalanceDataLoader;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BdeCloudAccaBalanceLoader
extends BaseBalanceDataLoader {
    @Autowired
    private BdeCloudAccaPluginType bdeCloudAccaPluginType;
    @Autowired
    private BdeCloudAccaBalanceDataProvider bdeCloudAccaBalanceDateProvider;

    public IBdePluginType getPluginType() {
        return this.bdeCloudAccaPluginType;
    }

    public FetchData queryData(BalanceCondition condi) {
        this.buildTempTable(condi);
        return this.bdeCloudAccaBalanceDateProvider.queryData(condi);
    }

    public Map<String, Integer> loadSubject(BalanceCondition condi) {
        return CloudAccaFetchUtil.loadSubject(condi);
    }
}

