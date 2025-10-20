/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.plugin.nc6.penetrate.balance.Nc6XjllBalanceContentProvider
 */
package com.jiuqi.bde.plugin.bip.penetrate.balance;

import com.jiuqi.bde.plugin.bip.BdeBipPluginType;
import com.jiuqi.bde.plugin.nc6.penetrate.balance.Nc6XjllBalanceContentProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BipXjllBalanceContentProvider
extends Nc6XjllBalanceContentProvider {
    @Autowired
    private BdeBipPluginType pluginType;

    public String getPluginType() {
        return this.pluginType.getSymbol();
    }

    protected String getErrmessageCondi() {
        return " ";
    }
}

