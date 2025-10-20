/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.bill.action;

import com.jiuqi.va.bill.impl.BillActionBase;
import com.jiuqi.va.bill.intf.BillModel;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class TileSubtablesAction
extends BillActionBase {
    public String getName() {
        return "bill-tile-subtable";
    }

    public String getTitle() {
        return "\u5e73\u94fa\u4ece\u8868";
    }

    public String getActionPriority() {
        return "018";
    }

    public String getIcon() {
        return "";
    }

    @Override
    public void execute(BillModel model, Map<String, Object> params) {
    }
}

