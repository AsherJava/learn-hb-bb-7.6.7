/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.define.IBdePluginType
 *  com.jiuqi.bde.plugin.nc6.init.NC6XInitHandle
 */
package com.jiuqi.bde.plugin.bip.init;

import com.jiuqi.bde.bizmodel.define.IBdePluginType;
import com.jiuqi.bde.plugin.bip.BdeBipPluginType;
import com.jiuqi.bde.plugin.nc6.init.NC6XInitHandle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BipInitHandle
extends NC6XInitHandle {
    @Autowired
    private BdeBipPluginType bdeBipPluginType;

    public IBdePluginType getPluginType() {
        return this.bdeBipPluginType;
    }
}

