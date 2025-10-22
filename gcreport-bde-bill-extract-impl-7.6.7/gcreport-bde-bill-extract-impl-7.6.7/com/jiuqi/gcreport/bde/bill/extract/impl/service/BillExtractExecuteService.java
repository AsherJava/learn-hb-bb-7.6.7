/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.bill.intf.BillModel
 */
package com.jiuqi.gcreport.bde.bill.extract.impl.service;

import com.jiuqi.gcreport.bde.bill.extract.impl.intf.BillExtractHandleMessage;
import com.jiuqi.va.bill.intf.BillModel;

public interface BillExtractExecuteService {
    public void doExecute(BillExtractHandleMessage var1);

    public void doExecute(BillModel var1, BillExtractHandleMessage var2);
}

