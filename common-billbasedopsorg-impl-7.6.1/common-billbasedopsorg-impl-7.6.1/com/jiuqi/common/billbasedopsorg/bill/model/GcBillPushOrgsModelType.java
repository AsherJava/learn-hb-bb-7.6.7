/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.bill.extention.FixedBillBase
 *  com.jiuqi.va.bill.impl.BillModelImpl
 *  com.jiuqi.va.biz.impl.data.DataDeclare
 */
package com.jiuqi.common.billbasedopsorg.bill.model;

import com.jiuqi.common.billbasedopsorg.bill.impl.GcBillPushOrgsModelImpl;
import com.jiuqi.common.billbasedopsorg.bill.storage.GcBillPushOrgsItemStorage;
import com.jiuqi.common.billbasedopsorg.bill.storage.GcBillPushOrgsStorage;
import com.jiuqi.va.bill.extention.FixedBillBase;
import com.jiuqi.va.bill.impl.BillModelImpl;
import com.jiuqi.va.biz.impl.data.DataDeclare;
import org.springframework.stereotype.Component;

@Component
public class GcBillPushOrgsModelType
extends FixedBillBase {
    public String getBizModule() {
        return "GCBILL";
    }

    public String getName() {
        return "GcBillPushOrgsModelImpl";
    }

    public String getTitle() {
        return "\u6279\u91cf\u751f\u6210\u7ec4\u7ec7\u673a\u6784\u5355\u636e\u6a21\u578b";
    }

    public Class<? extends BillModelImpl> getModelClass() {
        return GcBillPushOrgsModelImpl.class;
    }

    protected void declareData(DataDeclare<?> dataDeclare) {
        this.declareMasterTable(dataDeclare, GcBillPushOrgsStorage.getCreateDataMode("__default_tenant__"), "\u6279\u91cf\u751f\u6210\u7ec4\u7ec7\u673a\u6784\u4e3b\u8868");
        this.declareDetailTable(dataDeclare, "GC_BILLPUSHORGS", GcBillPushOrgsItemStorage.getCreateDataMode("__default_tenant__"), "\u6279\u91cf\u751f\u6210\u7ec4\u7ec7\u673a\u6784\u5b50\u8868");
    }
}

