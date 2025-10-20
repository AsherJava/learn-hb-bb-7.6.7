/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.bill.action;

import com.jiuqi.va.bill.impl.BillActionBase;
import com.jiuqi.va.bill.intf.BillModel;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class BillResDownloadAction
extends BillActionBase {
    public String getName() {
        return "bill-res-download";
    }

    public String getTitle() {
        return "\u8d44\u6e90\u4e0b\u8f7d";
    }

    public String getActionPriority() {
        return "028";
    }

    @Override
    public void execute(BillModel model, Map<String, Object> params) {
    }
}

