/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.define.IBdePluginType
 *  com.jiuqi.bde.bizmodel.execute.FetchData
 *  com.jiuqi.bde.bizmodel.execute.intf.BalanceCondition
 *  com.jiuqi.bde.plugin.common.datamodel.balance.BaseBalanceDataLoader
 *  com.jiuqi.common.base.util.CollectionUtils
 */
package com.jiuqi.bde.plugin.ebs_r12.fetch;

import com.jiuqi.bde.bizmodel.define.IBdePluginType;
import com.jiuqi.bde.bizmodel.execute.FetchData;
import com.jiuqi.bde.bizmodel.execute.intf.BalanceCondition;
import com.jiuqi.bde.plugin.common.datamodel.balance.BaseBalanceDataLoader;
import com.jiuqi.bde.plugin.ebs_r12.BdeEbsR12PluginType;
import com.jiuqi.bde.plugin.ebs_r12.fetch.BdeEbsR12BalanceDataProvider;
import com.jiuqi.common.base.util.CollectionUtils;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BdeEbsR12BalanceLoader
extends BaseBalanceDataLoader {
    @Autowired
    private BdeEbsR12PluginType ebsR12PluginType;
    @Autowired
    private BdeEbsR12BalanceDataProvider ebsR12BalanceDataProvider;

    public IBdePluginType getPluginType() {
        return this.ebsR12PluginType;
    }

    public FetchData queryData(BalanceCondition condi) {
        return this.ebsR12BalanceDataProvider.queryData(condi);
    }

    public Map<String, Integer> loadSubject(BalanceCondition condi) {
        return CollectionUtils.newHashMap();
    }
}

