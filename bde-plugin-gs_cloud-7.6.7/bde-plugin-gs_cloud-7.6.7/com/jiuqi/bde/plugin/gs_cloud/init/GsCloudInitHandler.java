/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.define.adaptor.AbstractBdeDataSchemeInit
 *  com.jiuqi.dc.mappingscheme.impl.define.IPluginType
 */
package com.jiuqi.bde.plugin.gs_cloud.init;

import com.jiuqi.bde.bizmodel.define.adaptor.AbstractBdeDataSchemeInit;
import com.jiuqi.bde.plugin.gs_cloud.BdeGsCloudPluginType;
import com.jiuqi.dc.mappingscheme.impl.define.IPluginType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GsCloudInitHandler
extends AbstractBdeDataSchemeInit {
    @Autowired
    private BdeGsCloudPluginType pluginType;

    public IPluginType getPluginType() {
        return this.pluginType;
    }
}

