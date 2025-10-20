/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.webserviceclient.intf;

import com.jiuqi.gcreport.webserviceclient.vo.RequestGcWsDataParam;

public interface IWsDataHandler {
    public String getKey();

    public String doHandle(RequestGcWsDataParam var1);
}

