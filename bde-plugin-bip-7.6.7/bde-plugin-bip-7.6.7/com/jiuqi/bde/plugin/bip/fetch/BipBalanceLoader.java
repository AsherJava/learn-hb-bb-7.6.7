/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.define.IBdePluginType
 *  com.jiuqi.bde.plugin.nc6.fetch.Nc6BalanceLoader
 */
package com.jiuqi.bde.plugin.bip.fetch;

import com.jiuqi.bde.bizmodel.define.IBdePluginType;
import com.jiuqi.bde.plugin.bip.BdeBipPluginType;
import com.jiuqi.bde.plugin.bip.fetch.BipBalanceDataProvider;
import com.jiuqi.bde.plugin.nc6.fetch.Nc6BalanceLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class BipBalanceLoader
extends Nc6BalanceLoader {
    @Autowired
    private BdeBipPluginType bdeBipPluginType;
    @Autowired
    @Qualifier(value="com.jiuqi.bde.plugin.bip.fetch.BipBalanceDataProvider")
    private BipBalanceDataProvider dataProvider;

    public IBdePluginType getPluginType() {
        return this.bdeBipPluginType;
    }

    protected BipBalanceDataProvider getBalanceDataProvider() {
        return this.dataProvider;
    }
}

