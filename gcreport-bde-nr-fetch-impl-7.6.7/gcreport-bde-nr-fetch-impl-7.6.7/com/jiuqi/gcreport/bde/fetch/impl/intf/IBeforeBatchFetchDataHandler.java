/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.efdc.pojo.EfdcInfo
 */
package com.jiuqi.gcreport.bde.fetch.impl.intf;

import com.jiuqi.nr.efdc.pojo.EfdcInfo;
import java.util.List;

public interface IBeforeBatchFetchDataHandler {
    public String getTitle();

    public List<EfdcInfo> rewriteEfdcInfo(String var1, List<EfdcInfo> var2);

    public int getOrder();
}

