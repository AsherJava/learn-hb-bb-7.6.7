/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.bill.action;

import com.jiuqi.va.bill.impl.BillActionBase;
import com.jiuqi.va.bill.intf.BillModel;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class SublistExportAction
extends BillActionBase {
    public String getName() {
        return "sublist-export";
    }

    public String getTitle() {
        return "\u5bfc\u51fa\uff08\u5b50\u8868\uff09";
    }

    @Override
    public void execute(BillModel model, Map<String, Object> params) {
    }

    public String getActionPriority() {
        return "025";
    }
}

