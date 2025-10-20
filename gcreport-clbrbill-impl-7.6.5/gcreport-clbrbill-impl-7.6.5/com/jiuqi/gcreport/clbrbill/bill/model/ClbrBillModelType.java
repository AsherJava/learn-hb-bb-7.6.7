/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.bill.extention.FixedBillBase
 *  com.jiuqi.va.biz.impl.data.DataDeclare
 */
package com.jiuqi.gcreport.clbrbill.bill.model;

import com.jiuqi.gcreport.clbrbill.bill.storage.ClbrBillStorage;
import com.jiuqi.va.bill.extention.FixedBillBase;
import com.jiuqi.va.biz.impl.data.DataDeclare;
import org.springframework.stereotype.Component;

@Component
public class ClbrBillModelType
extends FixedBillBase {
    public String getBizModule() {
        return "GCBILL";
    }

    protected void declareData(DataDeclare<?> dataDeclare) {
        this.declareMasterTable(dataDeclare, ClbrBillStorage.getCreateDataMode("__default_tenant__"), "GC_CLBRBILL");
    }

    public String getName() {
        return "ClbrBillModel";
    }

    public String getTitle() {
        return "\u534f\u540c\u5355\u6a21\u578b";
    }
}

