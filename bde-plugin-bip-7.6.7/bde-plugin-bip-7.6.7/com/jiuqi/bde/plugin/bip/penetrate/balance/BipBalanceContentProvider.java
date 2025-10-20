/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.plugin.nc6.penetrate.balance.Nc6BalanceContentProvider
 */
package com.jiuqi.bde.plugin.bip.penetrate.balance;

import com.jiuqi.bde.plugin.bip.BdeBipPluginType;
import com.jiuqi.bde.plugin.nc6.penetrate.balance.Nc6BalanceContentProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BipBalanceContentProvider
extends Nc6BalanceContentProvider {
    @Autowired
    private BdeBipPluginType bdeBipPluginType;

    public String getPluginType() {
        return this.bdeBipPluginType.getSymbol();
    }

    protected String getErrmessageCondi() {
        return " ";
    }
}

