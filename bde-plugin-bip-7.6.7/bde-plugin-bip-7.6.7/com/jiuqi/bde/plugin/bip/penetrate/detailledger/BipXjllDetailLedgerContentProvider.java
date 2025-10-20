/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.plugin.nc6.penetrate.detailledger.Nc6XjllDetailLedgerContentProvider
 */
package com.jiuqi.bde.plugin.bip.penetrate.detailledger;

import com.jiuqi.bde.plugin.bip.BdeBipPluginType;
import com.jiuqi.bde.plugin.nc6.penetrate.detailledger.Nc6XjllDetailLedgerContentProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BipXjllDetailLedgerContentProvider
extends Nc6XjllDetailLedgerContentProvider {
    @Autowired
    private BdeBipPluginType pluginType;

    public String getPluginType() {
        return this.pluginType.getSymbol();
    }

    protected String getErrmessageCondi() {
        return " ";
    }
}

