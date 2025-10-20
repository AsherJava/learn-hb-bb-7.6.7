/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.define.adaptor.AbstractBdeDataSchemeInit
 *  com.jiuqi.dc.mappingscheme.impl.define.IPluginType
 */
package com.jiuqi.bde.plugin.k3.init;

import com.jiuqi.bde.bizmodel.define.adaptor.AbstractBdeDataSchemeInit;
import com.jiuqi.bde.plugin.k3.BdeK3PluginType;
import com.jiuqi.dc.mappingscheme.impl.define.IPluginType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class K3InitHandele
extends AbstractBdeDataSchemeInit {
    @Autowired
    private BdeK3PluginType pluginType;

    public IPluginType getPluginType() {
        return this.pluginType;
    }
}

