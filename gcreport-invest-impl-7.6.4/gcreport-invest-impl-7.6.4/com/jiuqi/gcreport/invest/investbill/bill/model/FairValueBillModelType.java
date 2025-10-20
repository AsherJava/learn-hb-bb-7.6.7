/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.bill.extention.FixedBillBase
 *  com.jiuqi.va.bill.impl.BillModelImpl
 *  com.jiuqi.va.biz.impl.data.DataDeclare
 */
package com.jiuqi.gcreport.invest.investbill.bill.model;

import com.jiuqi.gcreport.invest.investbill.bill.impl.FairValueModelImpl;
import com.jiuqi.gcreport.invest.investbill.bill.storage.GcFairValueBillStorage;
import com.jiuqi.gcreport.invest.investbill.bill.storage.GcFairValueFixedItemBillStorage;
import com.jiuqi.gcreport.invest.investbill.bill.storage.GcFairValueOtherItemBillStorage;
import com.jiuqi.va.bill.extention.FixedBillBase;
import com.jiuqi.va.bill.impl.BillModelImpl;
import com.jiuqi.va.biz.impl.data.DataDeclare;
import org.springframework.stereotype.Component;

@Component
public class FairValueBillModelType
extends FixedBillBase {
    public String getBizModule() {
        return "GCBILL";
    }

    public String getName() {
        return "FairValueBillModel";
    }

    public String getTitle() {
        return "\u516c\u5141\u4ef7\u503c\u5355\u636e\u6a21\u578b";
    }

    public Class<? extends BillModelImpl> getModelClass() {
        return FairValueModelImpl.class;
    }

    protected void declareData(DataDeclare<?> dataDeclare) {
        this.declareMasterTable(dataDeclare, GcFairValueBillStorage.getCreateDataMode("__default_tenant__"), "\u516c\u5141\u4ef7\u503c\u5355\u636e\u4e3b\u8868");
        this.declareDetailTable(dataDeclare, "GC_FVCHBILL", GcFairValueFixedItemBillStorage.getCreateDataMode("__default_tenant__"), "\u516c\u5141\u4ef7\u503c\u56fa\u5b9a/\u65e0\u5f62\u8d44\u4ea7\u8868");
        this.declareDetailTable(dataDeclare, "GC_FVCHBILL", GcFairValueOtherItemBillStorage.getCreateDataMode("__default_tenant__"), "\u516c\u5141\u4ef7\u503c\u5176\u5b83\u8d44\u4ea7\u7c7b\u8868");
    }
}

