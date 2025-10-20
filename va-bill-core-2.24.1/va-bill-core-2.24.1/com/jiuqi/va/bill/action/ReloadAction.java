/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.bill.action;

import com.jiuqi.va.bill.impl.BillActionBase;
import com.jiuqi.va.bill.intf.BillModel;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class ReloadAction
extends BillActionBase {
    public String getName() {
        return "bill-reload";
    }

    public String getTitle() {
        return "\u5237\u65b0";
    }

    public String getIcon() {
        return "@va/va-iconfont icona-16_GJ_A_VA_shuaxin";
    }

    public String getActionPriority() {
        return "005";
    }

    @Override
    public void execute(BillModel model, Map<String, Object> params) {
        String billCode = model.getMaster().getString("BILLCODE");
        model.loadByCode(billCode);
    }
}

