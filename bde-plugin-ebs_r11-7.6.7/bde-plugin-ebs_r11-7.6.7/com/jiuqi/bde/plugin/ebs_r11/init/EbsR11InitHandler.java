/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.define.IBdePluginType
 *  com.jiuqi.bde.bizmodel.define.adaptor.AbstractBdeDataSchemeInit
 */
package com.jiuqi.bde.plugin.ebs_r11.init;

import com.jiuqi.bde.bizmodel.define.IBdePluginType;
import com.jiuqi.bde.bizmodel.define.adaptor.AbstractBdeDataSchemeInit;
import com.jiuqi.bde.plugin.ebs_r11.BdeEbsR11PluginType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EbsR11InitHandler
extends AbstractBdeDataSchemeInit {
    @Autowired
    private BdeEbsR11PluginType ebsR11PluginType;

    public IBdePluginType getPluginType() {
        return this.ebsR11PluginType;
    }
}

