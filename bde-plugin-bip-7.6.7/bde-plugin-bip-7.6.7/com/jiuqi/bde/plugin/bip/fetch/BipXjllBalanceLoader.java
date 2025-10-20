/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.define.IBdePluginType
 *  com.jiuqi.bde.plugin.nc6.fetch.Nc6XjllBalanceLoader
 */
package com.jiuqi.bde.plugin.bip.fetch;

import com.jiuqi.bde.bizmodel.define.IBdePluginType;
import com.jiuqi.bde.plugin.bip.BdeBipPluginType;
import com.jiuqi.bde.plugin.nc6.fetch.Nc6XjllBalanceLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BipXjllBalanceLoader
extends Nc6XjllBalanceLoader {
    @Autowired
    private BdeBipPluginType bdeBipPluginType;

    public IBdePluginType getPluginType() {
        return this.bdeBipPluginType;
    }

    protected String getErrmessageCondi() {
        return " ";
    }
}

