/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.define.IBdePluginType
 *  com.jiuqi.bde.bizmodel.define.adaptor.AbstractBdeDataSchemeInit
 *  com.jiuqi.dc.mappingscheme.impl.service.DataSourceService
 */
package com.jiuqi.bde.plugin.sap.init;

import com.jiuqi.bde.bizmodel.define.IBdePluginType;
import com.jiuqi.bde.bizmodel.define.adaptor.AbstractBdeDataSchemeInit;
import com.jiuqi.bde.plugin.sap.BdeSapPluginType;
import com.jiuqi.dc.mappingscheme.impl.service.DataSourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SapInitHandler
extends AbstractBdeDataSchemeInit {
    @Autowired
    private BdeSapPluginType pluginType;
    @Autowired
    protected DataSourceService dataSourceService;

    public IBdePluginType getPluginType() {
        return this.pluginType;
    }
}

