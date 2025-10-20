/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.va.bill.intf.BillModel
 */
package com.jiuqi.gcreport.bde.bill.extract.client.intf.single;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.gcreport.bde.bill.extract.client.intf.single.BillSingleExtractHandleContext;
import com.jiuqi.gcreport.bde.bill.extract.client.intf.single.BillSingleExtractSaveData;
import com.jiuqi.va.bill.intf.BillModel;
import java.util.Map;

public interface IBillSingleExtractHandler {
    public static final String FN_DEFAULT_HANDLER = "DEFAULT";

    public String getModelCode();

    public BillSingleExtractHandleContext parse(BillModel var1, Map<String, Object> var2);

    public void doCheck(BillSingleExtractHandleContext var1);

    public BillSingleExtractSaveData fetchData(BillModel var1, BillSingleExtractHandleContext var2);

    default public void doSave(BillModel model, BillSingleExtractHandleContext saveContext, BillSingleExtractSaveData saveData) {
        throw new BusinessRuntimeException("\u7f16\u8f91\u72b6\u6001\u53d6\u6570\u6682\u672a\u652f\u6301");
    }
}

