/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.define.IBdePluginType
 *  com.jiuqi.bde.plugin.nc6.BdeNc6MappingProvider
 */
package com.jiuqi.bde.plugin.bip;

import com.jiuqi.bde.bizmodel.define.IBdePluginType;
import com.jiuqi.bde.plugin.bip.BdeBipPluginType;
import com.jiuqi.bde.plugin.nc6.BdeNc6MappingProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BdeBipFieldMappingProvider
extends BdeNc6MappingProvider {
    @Autowired
    private BdeBipPluginType bdeBipPluginType;

    public IBdePluginType getPluginType() {
        return this.bdeBipPluginType;
    }
}

