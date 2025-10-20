/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.define.IBdePluginType
 *  com.jiuqi.bde.bizmodel.execute.FetchData
 *  com.jiuqi.bde.bizmodel.execute.intf.BalanceCondition
 *  com.jiuqi.bde.plugin.common.datamodel.assbalance.BaseAssBalanceDataLoader
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.dc.mappingscheme.impl.service.BizDataRefDefineService
 */
package com.jiuqi.bde.plugin.ebs_r11.fetch;

import com.jiuqi.bde.bizmodel.define.IBdePluginType;
import com.jiuqi.bde.bizmodel.execute.FetchData;
import com.jiuqi.bde.bizmodel.execute.intf.BalanceCondition;
import com.jiuqi.bde.plugin.common.datamodel.assbalance.BaseAssBalanceDataLoader;
import com.jiuqi.bde.plugin.ebs_r11.BdeEbsR11PluginType;
import com.jiuqi.bde.plugin.ebs_r11.fetch.EbsR11BalanceDataProvider;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.dc.mappingscheme.impl.service.BizDataRefDefineService;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EbsR11AssBalanceLoader
extends BaseAssBalanceDataLoader {
    @Autowired
    private BdeEbsR11PluginType ebsR11PluginType;
    @Autowired
    private BizDataRefDefineService bizDataRefDefineService;
    @Autowired
    private EbsR11BalanceDataProvider balanceDataProvider;

    public IBdePluginType getPluginType() {
        return this.ebsR11PluginType;
    }

    public FetchData queryData(BalanceCondition condi) {
        return this.balanceDataProvider.queryData(condi);
    }

    public Map<String, Integer> loadSubject(BalanceCondition condi) {
        return CollectionUtils.newHashMap();
    }
}

