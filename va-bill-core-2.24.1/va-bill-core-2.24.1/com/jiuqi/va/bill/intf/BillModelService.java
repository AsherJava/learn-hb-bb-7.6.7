/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.biz.intf.data.DataAccess
 *  com.jiuqi.va.feign.client.BillCodeClient
 */
package com.jiuqi.va.bill.intf;

import com.jiuqi.va.biz.intf.data.DataAccess;
import com.jiuqi.va.feign.client.BillCodeClient;

public interface BillModelService {
    public BillCodeClient getBillCode();

    public DataAccess getDataAccess();
}

