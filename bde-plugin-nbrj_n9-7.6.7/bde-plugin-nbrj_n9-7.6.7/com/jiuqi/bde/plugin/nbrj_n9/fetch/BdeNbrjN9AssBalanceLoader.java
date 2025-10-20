/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.define.IBdePluginType
 *  com.jiuqi.bde.bizmodel.execute.FetchData
 *  com.jiuqi.bde.bizmodel.execute.intf.BalanceCondition
 *  com.jiuqi.bde.plugin.common.datamodel.assbalance.BaseAssBalanceDataLoader
 */
package com.jiuqi.bde.plugin.nbrj_n9.fetch;

import com.jiuqi.bde.bizmodel.define.IBdePluginType;
import com.jiuqi.bde.bizmodel.execute.FetchData;
import com.jiuqi.bde.bizmodel.execute.intf.BalanceCondition;
import com.jiuqi.bde.plugin.common.datamodel.assbalance.BaseAssBalanceDataLoader;
import com.jiuqi.bde.plugin.nbrj_n9.BdeNbrjN9PluginType;
import com.jiuqi.bde.plugin.nbrj_n9.fetch.BdeNbrjN9BalanceDataProvider;
import com.jiuqi.bde.plugin.nbrj_n9.util.NbrjN9FetchUtil;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BdeNbrjN9AssBalanceLoader
extends BaseAssBalanceDataLoader {
    @Autowired
    private BdeNbrjN9PluginType pluginType;
    @Autowired
    private BdeNbrjN9BalanceDataProvider bdeNbrjN9BalanceDataProvider;

    public IBdePluginType getPluginType() {
        return this.pluginType;
    }

    public FetchData queryData(BalanceCondition condi) {
        this.buildTempTable(condi);
        return this.bdeNbrjN9BalanceDataProvider.queryData(condi);
    }

    public Map<String, Integer> loadSubject(BalanceCondition condi) {
        return NbrjN9FetchUtil.loadSubject(condi);
    }
}

