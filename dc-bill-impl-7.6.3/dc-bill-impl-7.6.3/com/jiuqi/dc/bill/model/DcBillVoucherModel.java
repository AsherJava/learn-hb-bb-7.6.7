/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.bill.extention.FixedBillBase
 *  com.jiuqi.va.bill.impl.BillModelImpl
 *  com.jiuqi.va.biz.impl.data.DataDeclare
 *  com.jiuqi.va.biz.intf.data.DataPostEvent
 */
package com.jiuqi.dc.bill.model;

import com.jiuqi.dc.bill.event.VoucherHandleEvent;
import com.jiuqi.dc.bill.model.impl.DcBillVoucherModelImpl;
import com.jiuqi.dc.bill.storage.DcVoucherBillItemStorage;
import com.jiuqi.dc.bill.storage.DcVoucherBillStorage;
import com.jiuqi.va.bill.extention.FixedBillBase;
import com.jiuqi.va.bill.impl.BillModelImpl;
import com.jiuqi.va.biz.impl.data.DataDeclare;
import com.jiuqi.va.biz.intf.data.DataPostEvent;
import org.springframework.stereotype.Component;

@Component
public class DcBillVoucherModel
extends FixedBillBase {
    public Class<? extends BillModelImpl> getModelClass() {
        return DcBillVoucherModelImpl.class;
    }

    protected void declareData(DataDeclare<?> dataDeclare) {
        this.declareMasterTable(dataDeclare, DcVoucherBillStorage.getCreateDataMode("__default_tenant__"), "\u5355\u636e\u51ed\u8bc1\u4e3b\u8868");
        this.declareDetailTable(dataDeclare, "DC_BILL_VOUCHER", DcVoucherBillItemStorage.getCreateDataMode("__default_tenant__"), "\u5355\u636e\u51ed\u8bc1\u5b50\u8868");
    }

    protected void ensureBillModel(BillModelImpl model) {
        super.ensureBillModel(model);
        model.getData().registerDataPostEvent((DataPostEvent)new VoucherHandleEvent());
    }

    public String getName() {
        return "DC_BILL_VOUCHER";
    }

    public String getTitle() {
        return "\u5355\u636e\u51ed\u8bc1\u6a21\u578b\uff08\u6807\u51c6\u6838\u7b97\u5e95\u7a3f\uff09";
    }
}

