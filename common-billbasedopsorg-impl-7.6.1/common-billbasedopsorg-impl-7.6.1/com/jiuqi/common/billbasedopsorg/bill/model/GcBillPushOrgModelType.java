/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.bill.extention.FixedBillBase
 *  com.jiuqi.va.bill.impl.BillModelImpl
 *  com.jiuqi.va.biz.impl.data.DataDeclare
 */
package com.jiuqi.common.billbasedopsorg.bill.model;

import com.jiuqi.common.billbasedopsorg.bill.impl.GcBillPushOrgModelImpl;
import com.jiuqi.common.billbasedopsorg.bill.storage.GcBillPushOrgStorage;
import com.jiuqi.va.bill.extention.FixedBillBase;
import com.jiuqi.va.bill.impl.BillModelImpl;
import com.jiuqi.va.biz.impl.data.DataDeclare;
import org.springframework.stereotype.Component;

@Component
public class GcBillPushOrgModelType
extends FixedBillBase {
    public String getBizModule() {
        return "GCBILL";
    }

    public String getName() {
        return "GcBillPushOrgModelImpl";
    }

    public String getTitle() {
        return "\u751f\u6210\u5355\u4e2a\u7ec4\u7ec7\u673a\u6784\u5355\u636e\u6a21\u578b";
    }

    public Class<? extends BillModelImpl> getModelClass() {
        return GcBillPushOrgModelImpl.class;
    }

    protected void declareData(DataDeclare<?> dataDeclare) {
        this.declareMasterTable(dataDeclare, GcBillPushOrgStorage.getCreateDataMode("__default_tenant__"), "GC_BILLPUSHORG");
    }
}

