/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.define.IBdePluginType
 *  com.jiuqi.bde.bizmodel.execute.FetchData
 *  com.jiuqi.bde.bizmodel.execute.intf.BalanceCondition
 *  com.jiuqi.bde.plugin.common.datamodel.balance.BaseBalanceDataLoader
 */
package com.jiuqi.bde.plugin.nc5.fetch;

import com.jiuqi.bde.bizmodel.define.IBdePluginType;
import com.jiuqi.bde.bizmodel.execute.FetchData;
import com.jiuqi.bde.bizmodel.execute.intf.BalanceCondition;
import com.jiuqi.bde.plugin.common.datamodel.balance.BaseBalanceDataLoader;
import com.jiuqi.bde.plugin.nc5.BdeNc5PluginType;
import com.jiuqi.bde.plugin.nc5.fetch.BdeNc5BalanceDataProvider;
import com.jiuqi.bde.plugin.nc5.util.Nc5FetchUtil;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BdeNc5BalanceLoader
extends BaseBalanceDataLoader {
    @Autowired
    private BdeNc5PluginType pluginType;
    @Autowired
    private BdeNc5BalanceDataProvider balanceDataProvider;

    public IBdePluginType getPluginType() {
        return this.pluginType;
    }

    public FetchData queryData(BalanceCondition condi) {
        return this.balanceDataProvider.queryData(condi);
    }

    public Map<String, Integer> loadSubject(BalanceCondition condi) {
        return Nc5FetchUtil.loadSubject(condi);
    }
}

