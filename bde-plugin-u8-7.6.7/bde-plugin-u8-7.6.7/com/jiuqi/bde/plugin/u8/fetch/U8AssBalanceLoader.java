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
package com.jiuqi.bde.plugin.u8.fetch;

import com.jiuqi.bde.bizmodel.define.IBdePluginType;
import com.jiuqi.bde.bizmodel.execute.FetchData;
import com.jiuqi.bde.bizmodel.execute.intf.BalanceCondition;
import com.jiuqi.bde.plugin.common.datamodel.assbalance.BaseAssBalanceDataLoader;
import com.jiuqi.bde.plugin.u8.BdeU8PluginType;
import com.jiuqi.bde.plugin.u8.fetch.U8BalanceDataProvider;
import com.jiuqi.bde.plugin.u8.util.BdeU8FetchUtil;
import com.jiuqi.dc.mappingscheme.impl.service.BizDataRefDefineService;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class U8AssBalanceLoader
extends BaseAssBalanceDataLoader {
    @Autowired
    private BdeU8PluginType pluginType;
    @Autowired
    private U8BalanceDataProvider u8BalanceDataProvider;
    @Autowired
    private BizDataRefDefineService bizDataDefineService;

    public IBdePluginType getPluginType() {
        return this.pluginType;
    }

    public FetchData queryData(BalanceCondition condi) {
        this.buildTempTable(condi);
        return this.u8BalanceDataProvider.queryData(condi);
    }

    public Map<String, Integer> loadSubject(BalanceCondition condi) {
        return BdeU8FetchUtil.loadSubject(condi);
    }
}

