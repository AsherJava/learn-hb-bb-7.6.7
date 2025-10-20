/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.define.IBdePluginType
 *  com.jiuqi.bde.bizmodel.define.adaptor.AbstractBdeDataSchemeInit
 */
package com.jiuqi.bde.plugin.eas8.init;

import com.jiuqi.bde.bizmodel.define.IBdePluginType;
import com.jiuqi.bde.bizmodel.define.adaptor.AbstractBdeDataSchemeInit;
import com.jiuqi.bde.plugin.eas8.Eas8PluginType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EasInitHandler
extends AbstractBdeDataSchemeInit {
    @Autowired
    private Eas8PluginType eas8PluginType;

    public IBdePluginType getPluginType() {
        return this.eas8PluginType;
    }
}

