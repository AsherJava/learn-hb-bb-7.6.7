/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.efdc.pojo.EfdcInfo
 */
package com.jiuqi.gcreport.bde.fetch.impl.intf;

import com.jiuqi.nr.efdc.pojo.EfdcInfo;

public interface IBeforeFetchDataChecker {
    public void doCheck(EfdcInfo var1);

    public void doBatchCheck(EfdcInfo var1);

    public int getOrder();
}

