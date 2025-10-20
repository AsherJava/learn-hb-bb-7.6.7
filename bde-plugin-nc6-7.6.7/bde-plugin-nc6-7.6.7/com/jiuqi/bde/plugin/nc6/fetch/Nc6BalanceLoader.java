/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.define.IBdePluginType
 *  com.jiuqi.bde.bizmodel.execute.FetchData
 *  com.jiuqi.bde.bizmodel.execute.intf.BalanceCondition
 *  com.jiuqi.bde.plugin.common.datamodel.balance.BaseBalanceDataLoader
 */
package com.jiuqi.bde.plugin.nc6.fetch;

import com.jiuqi.bde.bizmodel.define.IBdePluginType;
import com.jiuqi.bde.bizmodel.execute.FetchData;
import com.jiuqi.bde.bizmodel.execute.intf.BalanceCondition;
import com.jiuqi.bde.plugin.common.datamodel.balance.BaseBalanceDataLoader;
import com.jiuqi.bde.plugin.nc6.BdeNc6PluginType;
import com.jiuqi.bde.plugin.nc6.fetch.Nc6BalanceDataProvider;
import com.jiuqi.bde.plugin.nc6.util.Nc6FetchUtil;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class Nc6BalanceLoader
extends BaseBalanceDataLoader {
    @Autowired
    private BdeNc6PluginType pluginType;
    @Qualifier(value="com.jiuqi.bde.plugin.nc6.fetch.Nc6BalanceDataProvider")
    @Autowired
    private Nc6BalanceDataProvider dataProvider;

    public IBdePluginType getPluginType() {
        return this.pluginType;
    }

    public FetchData queryData(BalanceCondition condi) {
        this.buildTempTable(condi);
        return this.getBalanceDataProvider().queryData(condi);
    }

    public Map<String, Integer> loadSubject(BalanceCondition condi) {
        return Nc6FetchUtil.loadSubject(condi);
    }

    protected Nc6BalanceDataProvider getBalanceDataProvider() {
        return this.dataProvider;
    }
}

