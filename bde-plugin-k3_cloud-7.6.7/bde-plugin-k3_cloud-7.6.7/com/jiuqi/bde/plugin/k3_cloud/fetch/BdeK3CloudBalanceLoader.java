/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.define.IBdePluginType
 *  com.jiuqi.bde.bizmodel.execute.FetchData
 *  com.jiuqi.bde.bizmodel.execute.intf.BalanceCondition
 *  com.jiuqi.bde.plugin.common.datamodel.balance.BaseBalanceDataLoader
 */
package com.jiuqi.bde.plugin.k3_cloud.fetch;

import com.jiuqi.bde.bizmodel.define.IBdePluginType;
import com.jiuqi.bde.bizmodel.execute.FetchData;
import com.jiuqi.bde.bizmodel.execute.intf.BalanceCondition;
import com.jiuqi.bde.plugin.common.datamodel.balance.BaseBalanceDataLoader;
import com.jiuqi.bde.plugin.k3_cloud.BdeK3CloudPluginType;
import com.jiuqi.bde.plugin.k3_cloud.fetch.BdeK3CloudBalanceDataProvider;
import com.jiuqi.bde.plugin.k3_cloud.util.BdeK3CloudFetchUtil;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BdeK3CloudBalanceLoader
extends BaseBalanceDataLoader {
    @Autowired
    private BdeK3CloudPluginType pluginType;
    @Autowired
    private BdeK3CloudBalanceDataProvider dataProvider;

    public IBdePluginType getPluginType() {
        return this.pluginType;
    }

    public FetchData queryData(BalanceCondition condi) {
        this.buildTempTable(condi);
        return this.dataProvider.queryData(condi);
    }

    public Map<String, Integer> loadSubject(BalanceCondition condi) {
        return BdeK3CloudFetchUtil.loadSubject(condi);
    }
}

