/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.define.IBdePluginType
 *  com.jiuqi.bde.bizmodel.execute.FetchData
 *  com.jiuqi.bde.bizmodel.execute.intf.BalanceCondition
 *  com.jiuqi.bde.plugin.common.datamodel.assbalance.BaseAssBalanceDataLoader
 *  com.jiuqi.common.base.util.CollectionUtils
 */
package com.jiuqi.bde.plugin.sap.fetch;

import com.jiuqi.bde.bizmodel.define.IBdePluginType;
import com.jiuqi.bde.bizmodel.execute.FetchData;
import com.jiuqi.bde.bizmodel.execute.intf.BalanceCondition;
import com.jiuqi.bde.plugin.common.datamodel.assbalance.BaseAssBalanceDataLoader;
import com.jiuqi.bde.plugin.sap.BdeSapPluginType;
import com.jiuqi.bde.plugin.sap.fetch.SapAssBalanceDataProvider;
import com.jiuqi.common.base.util.CollectionUtils;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SapAssBalanceLoader
extends BaseAssBalanceDataLoader {
    @Autowired
    private BdeSapPluginType pluginType;
    @Autowired
    private SapAssBalanceDataProvider dataProvider;

    public IBdePluginType getPluginType() {
        return this.pluginType;
    }

    public Map<String, Integer> loadSubject(BalanceCondition condi) {
        return CollectionUtils.newHashMap();
    }

    public FetchData queryData(BalanceCondition condi) {
        this.buildTempTable(condi);
        return this.dataProvider.queryData(condi);
    }
}

