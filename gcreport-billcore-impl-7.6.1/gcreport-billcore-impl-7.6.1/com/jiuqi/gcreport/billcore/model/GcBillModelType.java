/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.bill.extention.FixedBillBase
 */
package com.jiuqi.gcreport.billcore.model;

import com.jiuqi.va.bill.extention.FixedBillBase;

public abstract class GcBillModelType
extends FixedBillBase {
    public String getBizModule() {
        return "GCBILL";
    }

    public String getName() {
        return "GcBillModel";
    }

    public String getTitle() {
        return "\u57fa\u7c7b-\u5408\u5e76\u62a5\u8868\u5355\u636e\u6a21\u578b";
    }
}

