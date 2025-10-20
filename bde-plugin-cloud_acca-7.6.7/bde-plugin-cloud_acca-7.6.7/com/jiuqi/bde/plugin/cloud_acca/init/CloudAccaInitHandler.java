/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.define.IBdePluginType
 *  com.jiuqi.bde.bizmodel.define.adaptor.AbstractBdeDataSchemeInit
 */
package com.jiuqi.bde.plugin.cloud_acca.init;

import com.jiuqi.bde.bizmodel.define.IBdePluginType;
import com.jiuqi.bde.bizmodel.define.adaptor.AbstractBdeDataSchemeInit;
import com.jiuqi.bde.plugin.cloud_acca.BdeCloudAccaPluginType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CloudAccaInitHandler
extends AbstractBdeDataSchemeInit {
    @Autowired
    private BdeCloudAccaPluginType bdeCloudAccaPluginType;

    public IBdePluginType getPluginType() {
        return this.bdeCloudAccaPluginType;
    }
}

