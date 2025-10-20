/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.plugin.nc6.fetch.Nc6BalanceDataProvider
 */
package com.jiuqi.bde.plugin.bip.fetch;

import com.jiuqi.bde.plugin.nc6.fetch.Nc6BalanceDataProvider;
import org.springframework.stereotype.Component;

@Component
public class BipBalanceDataProvider
extends Nc6BalanceDataProvider {
    protected String getErrmessageCondi() {
        return " ";
    }
}

