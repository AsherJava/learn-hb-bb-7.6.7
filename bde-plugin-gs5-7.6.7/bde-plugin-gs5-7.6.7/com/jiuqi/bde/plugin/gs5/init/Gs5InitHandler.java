/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.define.IBdePluginType
 *  com.jiuqi.bde.bizmodel.define.adaptor.AbstractBdeDataSchemeInit
 */
package com.jiuqi.bde.plugin.gs5.init;

import com.jiuqi.bde.bizmodel.define.IBdePluginType;
import com.jiuqi.bde.bizmodel.define.adaptor.AbstractBdeDataSchemeInit;
import com.jiuqi.bde.plugin.gs5.BdeGs5PluginType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Gs5InitHandler
extends AbstractBdeDataSchemeInit {
    @Autowired
    private BdeGs5PluginType pluginType;

    public IBdePluginType getPluginType() {
        return this.pluginType;
    }
}

