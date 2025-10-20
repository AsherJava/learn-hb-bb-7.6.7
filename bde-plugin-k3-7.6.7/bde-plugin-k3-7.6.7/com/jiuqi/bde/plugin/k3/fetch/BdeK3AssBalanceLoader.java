/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.define.IBdePluginType
 *  com.jiuqi.bde.bizmodel.execute.FetchData
 *  com.jiuqi.bde.bizmodel.execute.intf.BalanceCondition
 *  com.jiuqi.bde.plugin.common.datamodel.assbalance.BaseAssBalanceDataLoader
 */
package com.jiuqi.bde.plugin.k3.fetch;

import com.jiuqi.bde.bizmodel.define.IBdePluginType;
import com.jiuqi.bde.bizmodel.execute.FetchData;
import com.jiuqi.bde.bizmodel.execute.intf.BalanceCondition;
import com.jiuqi.bde.plugin.common.datamodel.assbalance.BaseAssBalanceDataLoader;
import com.jiuqi.bde.plugin.k3.BdeK3PluginType;
import com.jiuqi.bde.plugin.k3.fetch.BdeK3BalanceDataProvider;
import com.jiuqi.bde.plugin.k3.util.K3FetchUtil;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BdeK3AssBalanceLoader
extends BaseAssBalanceDataLoader {
    @Autowired
    private BdeK3PluginType pluginType;
    @Autowired
    private BdeK3BalanceDataProvider dataProvider;

    public IBdePluginType getPluginType() {
        return this.pluginType;
    }

    public FetchData queryData(BalanceCondition condi) {
        this.buildTempTable(condi);
        return this.dataProvider.queryData(condi);
    }

    public Map<String, Integer> loadSubject(BalanceCondition condi) {
        return K3FetchUtil.loadSubject(condi);
    }
}

