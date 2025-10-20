/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.define.IBdePluginType
 *  com.jiuqi.bde.bizmodel.execute.FetchData
 *  com.jiuqi.bde.bizmodel.execute.intf.BalanceCondition
 *  com.jiuqi.bde.plugin.common.datamodel.assbalance.BaseAssBalanceDataLoader
 *  com.jiuqi.dc.mappingscheme.impl.service.BizDataRefDefineService
 */
package com.jiuqi.bde.plugin.nc5.fetch;

import com.jiuqi.bde.bizmodel.define.IBdePluginType;
import com.jiuqi.bde.bizmodel.execute.FetchData;
import com.jiuqi.bde.bizmodel.execute.intf.BalanceCondition;
import com.jiuqi.bde.plugin.common.datamodel.assbalance.BaseAssBalanceDataLoader;
import com.jiuqi.bde.plugin.nc5.BdeNc5PluginType;
import com.jiuqi.bde.plugin.nc5.fetch.BdeNc5BalanceDataProvider;
import com.jiuqi.bde.plugin.nc5.util.Nc5FetchUtil;
import com.jiuqi.dc.mappingscheme.impl.service.BizDataRefDefineService;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BdeNc5AssBalanceLoader
extends BaseAssBalanceDataLoader {
    @Autowired
    private BdeNc5PluginType pluginType;
    @Autowired
    private BdeNc5BalanceDataProvider balanceDataProvider;
    @Autowired
    private BizDataRefDefineService bizDataDefineService;

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

