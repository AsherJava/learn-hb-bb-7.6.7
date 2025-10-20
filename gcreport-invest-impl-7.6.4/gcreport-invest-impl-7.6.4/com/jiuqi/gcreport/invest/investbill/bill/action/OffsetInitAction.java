/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.bill.impl.BillActionBase
 *  com.jiuqi.va.bill.intf.BillConsts
 *  com.jiuqi.va.bill.intf.BillModel
 */
package com.jiuqi.gcreport.invest.investbill.bill.action;

import com.jiuqi.gcreport.invest.investbill.bill.impl.FairValueModelImpl;
import com.jiuqi.va.bill.impl.BillActionBase;
import com.jiuqi.va.bill.intf.BillConsts;
import com.jiuqi.va.bill.intf.BillModel;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class OffsetInitAction
extends BillActionBase {
    public String getName() {
        return "bill-offset-init";
    }

    public String getTitle() {
        return "\u62b5\u9500\u5206\u5f55\u521d\u59cb";
    }

    public String getIcon() {
        return null;
    }

    public void execute(BillModel model, Map<String, Object> params) {
        System.out.println(model.getData());
    }

    public String[] getModelParams() {
        return BillConsts.ACTION_PARAM_DEFINE;
    }

    public String getActionPriority() {
        return "000";
    }

    public Class<? extends BillModel> getDependModel() {
        return FairValueModelImpl.class;
    }
}

