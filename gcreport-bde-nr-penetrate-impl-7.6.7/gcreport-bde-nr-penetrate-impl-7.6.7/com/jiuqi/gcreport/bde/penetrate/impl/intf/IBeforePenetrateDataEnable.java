/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.bde.penetrate.client.dto.GcFetchPierceDTO
 */
package com.jiuqi.gcreport.bde.penetrate.impl.intf;

import com.jiuqi.gcreport.bde.penetrate.client.dto.GcFetchPierceDTO;

public interface IBeforePenetrateDataEnable {
    public boolean beforeEnable(GcFetchPierceDTO var1);

    public int getOrder();
}

