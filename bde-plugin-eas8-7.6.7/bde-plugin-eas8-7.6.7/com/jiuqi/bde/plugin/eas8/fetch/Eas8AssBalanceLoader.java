/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.define.IBdePluginType
 *  com.jiuqi.bde.bizmodel.execute.FetchData
 *  com.jiuqi.bde.bizmodel.execute.intf.BalanceCondition
 *  com.jiuqi.bde.plugin.common.datamodel.assbalance.BaseAssBalanceDataLoader
 */
package com.jiuqi.bde.plugin.eas8.fetch;

import com.jiuqi.bde.bizmodel.define.IBdePluginType;
import com.jiuqi.bde.bizmodel.execute.FetchData;
import com.jiuqi.bde.bizmodel.execute.intf.BalanceCondition;
import com.jiuqi.bde.plugin.common.datamodel.assbalance.BaseAssBalanceDataLoader;
import com.jiuqi.bde.plugin.eas8.Eas8PluginType;
import com.jiuqi.bde.plugin.eas8.fetch.Eas8BalanceDataProvider;
import com.jiuqi.bde.plugin.eas8.util.Eas8FetchUtil;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Eas8AssBalanceLoader
extends BaseAssBalanceDataLoader {
    @Autowired
    private Eas8PluginType eas8PluginType;
    @Autowired
    private Eas8BalanceDataProvider dataProvider;

    public IBdePluginType getPluginType() {
        return this.eas8PluginType;
    }

    public FetchData queryData(BalanceCondition condi) {
        this.buildTempTable(condi);
        return this.dataProvider.queryData(condi);
    }

    public Map<String, Integer> loadSubject(BalanceCondition condi) {
        return Eas8FetchUtil.loadSubject(condi);
    }
}

