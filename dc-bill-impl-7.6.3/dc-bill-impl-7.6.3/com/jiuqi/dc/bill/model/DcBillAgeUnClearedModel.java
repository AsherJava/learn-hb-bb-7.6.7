/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.bill.extention.FixedBillBase
 *  com.jiuqi.va.bill.impl.BillModelImpl
 *  com.jiuqi.va.biz.impl.data.DataDeclare
 */
package com.jiuqi.dc.bill.model;

import com.jiuqi.dc.bill.model.impl.DcBillAgeUnClearedModelImpl;
import com.jiuqi.dc.bill.storage.DcAgeUnclearedBillItemStorage;
import com.jiuqi.dc.bill.storage.DcAgeUnclearedBillStorage;
import com.jiuqi.va.bill.extention.FixedBillBase;
import com.jiuqi.va.bill.impl.BillModelImpl;
import com.jiuqi.va.biz.impl.data.DataDeclare;
import org.springframework.stereotype.Component;

@Component
public class DcBillAgeUnClearedModel
extends FixedBillBase {
    public Class<? extends BillModelImpl> getModelClass() {
        return DcBillAgeUnClearedModelImpl.class;
    }

    protected void declareData(DataDeclare<?> dataDeclare) {
        this.declareMasterTable(dataDeclare, DcAgeUnclearedBillStorage.getCreateDataMode("__default_tenant__"), "\u5355\u636e\u8d26\u9f84\u672a\u6e05\u9879\u4e3b\u8868");
        this.declareDetailTable(dataDeclare, "DC_BILL_AGEUNCLEARED", DcAgeUnclearedBillItemStorage.getCreateDataMode("__default_tenant__"), "\u5355\u636e\u8d26\u9f84\u672a\u6e05\u9879\u5b50\u8868");
    }

    public String getName() {
        return "DC_BILL_AGEUNCLEARED";
    }

    public String getTitle() {
        return "\u5355\u636e\u8d26\u9f84\u672a\u6e05\u9879\u6a21\u578b";
    }
}

