/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.bill.extention.FixedBillBase
 *  com.jiuqi.va.bill.impl.BillModelImpl
 *  com.jiuqi.va.biz.impl.data.DataDeclare
 *  com.jiuqi.va.biz.intf.data.DataPostEvent
 */
package com.jiuqi.va.bill.bd.bill.model;

import com.jiuqi.va.bill.bd.bill.common.BackWriteAndUpdateTransEvent;
import com.jiuqi.va.bill.bd.bill.model.RegistrationBillModel;
import com.jiuqi.va.bill.extention.FixedBillBase;
import com.jiuqi.va.bill.impl.BillModelImpl;
import com.jiuqi.va.biz.impl.data.DataDeclare;
import com.jiuqi.va.biz.intf.data.DataPostEvent;
import org.springframework.stereotype.Component;

@Component
public class RegistrationBillModelType
extends FixedBillBase {
    public Class<? extends RegistrationBillModel> getModelClass() {
        return RegistrationBillModel.class;
    }

    public String getName() {
        return "VA_BILLBD_REGISTRATION";
    }

    public String getTitle() {
        return "\u5355\u636e-\u57fa\u7840\u6570\u636e\u767b\u8bb0\u6a21\u578b";
    }

    protected void declareData(DataDeclare<?> dataDeclare) {
    }

    protected void ensureBillModel(BillModelImpl model) {
        model.getData().registerDataPostEvent((DataPostEvent)new BackWriteAndUpdateTransEvent());
        super.ensureBillModel(model);
    }
}

