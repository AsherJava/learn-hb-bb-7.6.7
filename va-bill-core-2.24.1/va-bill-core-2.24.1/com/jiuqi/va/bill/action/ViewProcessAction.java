/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.bill.action;

import com.jiuqi.va.bill.impl.BillActionBase;
import com.jiuqi.va.bill.intf.BillModel;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class ViewProcessAction
extends BillActionBase {
    public String getName() {
        return "bill-workflow";
    }

    public String getTitle() {
        return "\u67e5\u770b\u6d41\u7a0b";
    }

    public String getIcon() {
        return "@va/va-iconfont icona-16_GJ_A_VA_chakanliucheng";
    }

    public String getActionPriority() {
        return "019";
    }

    @Override
    public void execute(BillModel model, Map<String, Object> params) {
    }
}

