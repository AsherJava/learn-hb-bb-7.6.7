/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.billcore.common.GcBillModelImpl
 */
package com.jiuqi.gcreport.asset.assetbill.bill.impl;

import com.jiuqi.gcreport.billcore.common.GcBillModelImpl;

public abstract class GcAbstractAssetBillModelImpl
extends GcBillModelImpl {
    public void edit() {
        this.getMaster().setValue("BUTTONACTION", (Object)"EDIT");
        super.edit();
    }
}

