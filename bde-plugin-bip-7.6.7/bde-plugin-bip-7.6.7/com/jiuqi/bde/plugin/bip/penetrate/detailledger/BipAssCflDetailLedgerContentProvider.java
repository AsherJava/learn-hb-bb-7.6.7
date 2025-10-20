/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.plugin.nc6.penetrate.detailledger.Nc6AssCflDetailLedgerContentProvider
 */
package com.jiuqi.bde.plugin.bip.penetrate.detailledger;

import com.jiuqi.bde.plugin.bip.BdeBipPluginType;
import com.jiuqi.bde.plugin.nc6.penetrate.detailledger.Nc6AssCflDetailLedgerContentProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BipAssCflDetailLedgerContentProvider
extends Nc6AssCflDetailLedgerContentProvider {
    @Autowired
    private BdeBipPluginType bdeBipPluginType;

    public String getPluginType() {
        return this.bdeBipPluginType.getSymbol();
    }

    protected String getErrmessageCondi() {
        return " ";
    }
}

