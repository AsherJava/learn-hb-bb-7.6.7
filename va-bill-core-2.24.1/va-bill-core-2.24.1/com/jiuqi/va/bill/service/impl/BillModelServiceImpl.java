/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.biz.intf.data.DataAccess
 *  com.jiuqi.va.feign.client.BillCodeClient
 */
package com.jiuqi.va.bill.service.impl;

import com.jiuqi.va.bill.intf.BillModelService;
import com.jiuqi.va.biz.intf.data.DataAccess;
import com.jiuqi.va.feign.client.BillCodeClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BillModelServiceImpl
implements BillModelService {
    @Autowired
    private BillCodeClient billCode;
    @Autowired
    private DataAccess dataAccess;

    @Override
    public BillCodeClient getBillCode() {
        return this.billCode;
    }

    @Override
    public DataAccess getDataAccess() {
        return this.dataAccess;
    }
}

