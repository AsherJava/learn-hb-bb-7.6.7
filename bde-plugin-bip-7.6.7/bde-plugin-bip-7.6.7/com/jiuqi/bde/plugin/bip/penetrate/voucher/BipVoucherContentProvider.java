/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.plugin.nc6.penetrate.voucher.Nc6VoucherContentProvider
 */
package com.jiuqi.bde.plugin.bip.penetrate.voucher;

import com.jiuqi.bde.plugin.bip.BdeBipPluginType;
import com.jiuqi.bde.plugin.nc6.penetrate.voucher.Nc6VoucherContentProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BipVoucherContentProvider
extends Nc6VoucherContentProvider {
    @Autowired
    private BdeBipPluginType bdeBipPluginType;

    public String getPluginType() {
        return this.bdeBipPluginType.getSymbol();
    }
}

