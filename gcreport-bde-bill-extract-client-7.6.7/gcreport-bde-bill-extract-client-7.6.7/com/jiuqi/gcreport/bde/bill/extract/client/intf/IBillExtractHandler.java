/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.va.bill.intf.BillModel
 */
package com.jiuqi.gcreport.bde.bill.extract.client.intf;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.gcreport.bde.bill.extract.client.intf.BillExtractHandleCtx;
import com.jiuqi.gcreport.bde.bill.extract.client.intf.BillExtractHandleDTO;
import com.jiuqi.gcreport.bde.bill.extract.client.intf.BillExtractHandleParam;
import com.jiuqi.gcreport.bde.bill.extract.client.intf.BillExtractSaveContext;
import com.jiuqi.gcreport.bde.bill.extract.client.intf.BillExtractSaveData;
import com.jiuqi.va.bill.intf.BillModel;
import java.util.List;
import java.util.Map;

public interface IBillExtractHandler {
    public static final String FN_DEFAULT_HANDLER = "DEFAULT";

    public String getModelCode();

    public BillExtractHandleParam parse(BillExtractHandleDTO var1);

    public void doCheck(BillExtractHandleCtx var1);

    public List<Map<String, Object>> listBills(BillExtractHandleCtx var1);

    public void doSave(BillExtractSaveContext var1, BillExtractSaveData var2);

    default public void doSave(BillModel model, BillExtractSaveContext saveContext, BillExtractSaveData saveData) {
        throw new BusinessRuntimeException("\u7f16\u8f91\u72b6\u6001\u53d6\u6570\u6682\u672a\u652f\u6301");
    }
}

