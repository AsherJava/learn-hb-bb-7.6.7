/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.bill.extention.FixedBillBase
 *  com.jiuqi.va.bill.impl.BillModelImpl
 *  com.jiuqi.va.biz.impl.data.DataDeclare
 */
package com.jiuqi.gcreport.asset.assetbill.bill.model;

import com.jiuqi.gcreport.asset.assetbill.bill.impl.GcCombinedAssetBillModelImpl;
import com.jiuqi.gcreport.asset.assetbill.bill.storage.GcCombinedAssetBillItemStorage;
import com.jiuqi.gcreport.asset.assetbill.bill.storage.GcCombinedAssetBillStorage;
import com.jiuqi.va.bill.extention.FixedBillBase;
import com.jiuqi.va.bill.impl.BillModelImpl;
import com.jiuqi.va.biz.impl.data.DataDeclare;
import org.springframework.stereotype.Component;

@Component
public class GcCombinedAssetBillModelType
extends FixedBillBase {
    public String getBizModule() {
        return "GCBILL";
    }

    public String getName() {
        return "CombinedAssetBillModel";
    }

    public String getTitle() {
        return "\u7ec4\u5408\u8d44\u4ea7\u53f0\u8d26\u6a21\u578b";
    }

    public Class<? extends BillModelImpl> getModelClass() {
        return GcCombinedAssetBillModelImpl.class;
    }

    protected void declareData(DataDeclare<?> dataDeclare) {
        this.declareMasterTable(dataDeclare, GcCombinedAssetBillStorage.getCreateDataMode("__default_tenant__"), "\u7ec4\u5408\u8d44\u4ea7\u5355\u636e\u4e3b\u8868");
        this.declareDetailTable(dataDeclare, "GC_COMBINEDASSETBILL", GcCombinedAssetBillItemStorage.getCreateDataMode("__default_tenant__"), "\u7ec4\u5408\u8d44\u4ea7\u5355\u636e\u5b50\u8868");
    }
}

