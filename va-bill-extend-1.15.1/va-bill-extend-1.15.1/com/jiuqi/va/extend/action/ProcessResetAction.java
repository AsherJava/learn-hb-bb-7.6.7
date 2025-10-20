/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.bill.impl.BillActionBase
 *  com.jiuqi.va.bill.intf.BillModel
 *  com.jiuqi.va.bill.intf.BillState
 */
package com.jiuqi.va.extend.action;

import com.jiuqi.va.bill.impl.BillActionBase;
import com.jiuqi.va.bill.intf.BillModel;
import com.jiuqi.va.bill.intf.BillState;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class ProcessResetAction
extends BillActionBase {
    public String getName() {
        return "bill-process-reset";
    }

    public String getTitle() {
        return "\u6d41\u7a0b\u91cd\u7f6e";
    }

    public String getActionPriority() {
        return "099";
    }

    public void execute(BillModel model, Map<String, Object> params) {
        if (BillState.AUDITPASSED.getValue() != model.getMaster().getInt("BILLSTATE")) {
            return;
        }
        model.getData().edit();
        model.getMaster().setValue("BILLSTATE", (Object)BillState.SAVED);
        model.getData().save();
    }
}

