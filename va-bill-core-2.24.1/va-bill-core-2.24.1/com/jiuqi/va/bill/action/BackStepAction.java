/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.biz.intf.action.ActionRequest
 *  com.jiuqi.va.biz.intf.action.ActionResponse
 *  com.jiuqi.va.biz.intf.model.Model
 */
package com.jiuqi.va.bill.action;

import com.jiuqi.va.bill.impl.BillActionBase;
import com.jiuqi.va.bill.impl.BillModelImpl;
import com.jiuqi.va.bill.intf.BillModel;
import com.jiuqi.va.biz.intf.action.ActionRequest;
import com.jiuqi.va.biz.intf.action.ActionResponse;
import com.jiuqi.va.biz.intf.model.Model;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class BackStepAction
extends BillActionBase {
    public String getName() {
        return "bill-back-step";
    }

    public String getTitle() {
        return "\u4e0a\u4e00\u6b65";
    }

    public boolean isInner() {
        return true;
    }

    public boolean before(Model model, ActionRequest request, ActionResponse response) {
        BillModelImpl billModel = (BillModelImpl)model;
        String curView = String.valueOf(request.getParams().get("schemeCode"));
        String nextView = String.valueOf(request.getParams().get("nextView"));
        billModel.getContext().setContextValue("curView", curView);
        billModel.getContext().setContextValue("nextView", nextView);
        return super.before(model, request, response);
    }

    @Override
    public void execute(BillModel model, Map<String, Object> params) {
    }
}

