/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.bill.extention.FixedBillBase
 *  com.jiuqi.va.bill.impl.BillModelImpl
 *  com.jiuqi.va.biz.impl.data.DataDeclare
 */
package com.jiuqi.gcreport.invest.investbill.bill.model;

import com.jiuqi.gcreport.invest.investbill.bill.impl.InvestBillModelImpl;
import com.jiuqi.gcreport.invest.investbill.bill.storage.GcInvestBillItemStorage;
import com.jiuqi.gcreport.invest.investbill.bill.storage.GcInvestBillStorage;
import com.jiuqi.va.bill.extention.FixedBillBase;
import com.jiuqi.va.bill.impl.BillModelImpl;
import com.jiuqi.va.biz.impl.data.DataDeclare;
import org.springframework.stereotype.Component;

@Component
public class InvestBillModelType
extends FixedBillBase {
    public String getBizModule() {
        return "GCBILL";
    }

    public String getName() {
        return "InvestBillModel";
    }

    public String getTitle() {
        return "\u6295\u8d44\u5355\u636e\u6a21\u578b";
    }

    public Class<? extends BillModelImpl> getModelClass() {
        return InvestBillModelImpl.class;
    }

    protected void declareData(DataDeclare<?> dataDeclare) {
        this.declareMasterTable(dataDeclare, GcInvestBillStorage.getCreateDataMode("__default_tenant__"), "\u6295\u8d44\u5355\u636e\u4e3b\u8868");
        this.declareDetailTable(dataDeclare, "GC_INVESTBILL", GcInvestBillItemStorage.getCreateDataMode("__default_tenant__"), "\u6295\u8d44\u5355\u636e\u5b50\u8868");
    }
}

