/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.bill.extention.FixedBillBase
 *  com.jiuqi.va.bill.impl.BillModelImpl
 *  com.jiuqi.va.biz.impl.data.DataDeclare
 */
package com.jiuqi.gcreport.lease.leasebill.bill.model;

import com.jiuqi.gcreport.lease.leasebill.bill.impl.LessorBillModelImpl;
import com.jiuqi.gcreport.lease.leasebill.bill.storage.GcLessorBillItemStorage;
import com.jiuqi.gcreport.lease.leasebill.bill.storage.GcLessorBillStorage;
import com.jiuqi.va.bill.extention.FixedBillBase;
import com.jiuqi.va.bill.impl.BillModelImpl;
import com.jiuqi.va.biz.impl.data.DataDeclare;
import org.springframework.stereotype.Component;

@Component
public class LessorBillModelType
extends FixedBillBase {
    public static final String MODELTYPE = "LessorBillModel";

    public String getBizModule() {
        return "GCBILL";
    }

    public String getName() {
        return MODELTYPE;
    }

    public String getTitle() {
        return "\u51fa\u79df\u65b9\u5355\u636e\u6a21\u578b";
    }

    public Class<? extends BillModelImpl> getModelClass() {
        return LessorBillModelImpl.class;
    }

    protected void declareData(DataDeclare<?> dataDeclare) {
        this.declareMasterTable(dataDeclare, GcLessorBillStorage.getCreateDataMode("__default_tenant__"), "\u51fa\u79df\u65b9\u5355\u636e\u4e3b\u8868");
        this.declareDetailTable(dataDeclare, "GC_LESSORBILL", GcLessorBillItemStorage.getCreateDataMode("__default_tenant__"), "\u51fa\u79df\u65b9\u5355\u636e\u5b50\u8868");
    }
}

