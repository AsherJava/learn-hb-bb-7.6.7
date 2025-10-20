/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.bde.bill.extract.client.intf.single.BillSingleExtractHandleContext
 *  com.jiuqi.gcreport.bde.bill.extract.client.intf.single.BillSingleExtractSaveData
 *  com.jiuqi.gcreport.bde.bill.extract.client.intf.single.IBillSingleExtractHandler
 *  com.jiuqi.gcreport.bde.bill.extract.client.intf.single.IBillSingleExtractHandlerGather
 *  com.jiuqi.va.bill.impl.BillActionBase
 *  com.jiuqi.va.bill.intf.BillConsts
 *  com.jiuqi.va.bill.intf.BillModel
 */
package com.jiuqi.gcreport.bde.bill.extract.impl.action;

import com.jiuqi.gcreport.bde.bill.extract.client.intf.single.BillSingleExtractHandleContext;
import com.jiuqi.gcreport.bde.bill.extract.client.intf.single.BillSingleExtractSaveData;
import com.jiuqi.gcreport.bde.bill.extract.client.intf.single.IBillSingleExtractHandler;
import com.jiuqi.gcreport.bde.bill.extract.client.intf.single.IBillSingleExtractHandlerGather;
import com.jiuqi.va.bill.impl.BillActionBase;
import com.jiuqi.va.bill.intf.BillConsts;
import com.jiuqi.va.bill.intf.BillModel;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GcBillSingleExtractAction
extends BillActionBase {
    @Autowired
    private IBillSingleExtractHandlerGather handlerGather;

    public String getName() {
        return "ExtractAction";
    }

    public String getTitle() {
        return "BDE\u53d6\u6570";
    }

    public void execute(BillModel model, Map<String, Object> params) {
        String modelDefineName = model.getDefine().getName();
        IBillSingleExtractHandler billExtractHandler = this.handlerGather.getHandlerByModel(modelDefineName);
        BillSingleExtractHandleContext context = billExtractHandler.parse(model, params);
        billExtractHandler.doCheck(context);
        BillSingleExtractSaveData fetchResultDTO = billExtractHandler.fetchData(model, context);
        billExtractHandler.doSave(model, context, fetchResultDTO);
    }

    public String[] getModelParams() {
        return BillConsts.ACTION_PARAM_DEFINE;
    }
}

