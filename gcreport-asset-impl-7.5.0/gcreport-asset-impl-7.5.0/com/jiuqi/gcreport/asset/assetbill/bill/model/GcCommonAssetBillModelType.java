/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.bill.extention.FixedBillBase
 *  com.jiuqi.va.bill.impl.BillModelImpl
 *  com.jiuqi.va.biz.impl.data.DataDeclare
 */
package com.jiuqi.gcreport.asset.assetbill.bill.model;

import com.jiuqi.gcreport.asset.assetbill.bill.impl.GcCommonAssetBillModelImpl;
import com.jiuqi.gcreport.asset.assetbill.bill.storage.GcCommonAssetBillStorage;
import com.jiuqi.va.bill.extention.FixedBillBase;
import com.jiuqi.va.bill.impl.BillModelImpl;
import com.jiuqi.va.biz.impl.data.DataDeclare;
import org.springframework.stereotype.Component;

@Component
public class GcCommonAssetBillModelType
extends FixedBillBase {
    public String getBizModule() {
        return "GCBILL";
    }

    public String getName() {
        return "CommonAssetBillModel";
    }

    public String getTitle() {
        return "\u5e38\u89c4\u8d44\u4ea7\u53f0\u8d26\u6a21\u578b";
    }

    public Class<? extends BillModelImpl> getModelClass() {
        return GcCommonAssetBillModelImpl.class;
    }

    protected void declareData(DataDeclare<?> dataDeclare) {
        this.declareMasterTable(dataDeclare, GcCommonAssetBillStorage.getCreateDataMode("__default_tenant__"), "\u5e38\u89c4\u8d44\u4ea7\u5355\u636e\u8868");
    }
}

