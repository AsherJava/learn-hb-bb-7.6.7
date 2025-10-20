/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.define.IBdePluginType
 *  com.jiuqi.bde.bizmodel.execute.FetchData
 *  com.jiuqi.bde.bizmodel.execute.intf.BalanceCondition
 *  com.jiuqi.bde.plugin.common.datamodel.balance.BaseBalanceDataLoader
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.dc.mappingscheme.client.dto.DataMappingDefineDTO
 */
package com.jiuqi.bde.plugin.va6_40.fetch;

import com.jiuqi.bde.bizmodel.define.IBdePluginType;
import com.jiuqi.bde.bizmodel.execute.FetchData;
import com.jiuqi.bde.bizmodel.execute.intf.BalanceCondition;
import com.jiuqi.bde.plugin.common.datamodel.balance.BaseBalanceDataLoader;
import com.jiuqi.bde.plugin.va6_40.BdeVa6_40PluginType;
import com.jiuqi.bde.plugin.va6_40.fetch.Va6_40BalanceDataProvider;
import com.jiuqi.bde.plugin.va6_40.util.Va6_40FetchUtil;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.dc.mappingscheme.client.dto.DataMappingDefineDTO;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Va6_40BalanceLoader
extends BaseBalanceDataLoader {
    @Autowired
    private BdeVa6_40PluginType pluginType;
    @Autowired
    private Va6_40BalanceDataProvider dataProvider;

    public IBdePluginType getPluginType() {
        return this.pluginType;
    }

    public FetchData queryData(BalanceCondition condi) {
        DataMappingDefineDTO assistDefine = new DataMappingDefineDTO();
        assistDefine.setItems((List)CollectionUtils.newArrayList());
        this.buildTempTable(condi);
        return this.dataProvider.queryData(condi);
    }

    public Map<String, Integer> loadSubject(BalanceCondition condi) {
        return Va6_40FetchUtil.loadSubject(condi);
    }
}

