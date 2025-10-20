/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.plugin.nc6.penetrate.balance.Nc6AssCflBalanceContentProvider
 */
package com.jiuqi.bde.plugin.bip.penetrate.balance;

import com.jiuqi.bde.plugin.bip.BdeBipPluginType;
import com.jiuqi.bde.plugin.nc6.penetrate.balance.Nc6AssCflBalanceContentProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BipAssCflBalanceContentProvider
extends Nc6AssCflBalanceContentProvider {
    @Autowired
    private BdeBipPluginType bdeBipPluginType;

    public String getPluginType() {
        return this.bdeBipPluginType.getSymbol();
    }

    protected String getErrmessageCondi() {
        return " ";
    }
}

