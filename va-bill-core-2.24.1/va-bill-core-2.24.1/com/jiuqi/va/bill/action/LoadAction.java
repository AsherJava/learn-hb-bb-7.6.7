/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.bill.action;

import com.jiuqi.va.bill.impl.BillActionBase;
import com.jiuqi.va.bill.intf.BillConsts;
import com.jiuqi.va.bill.intf.BillModel;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class LoadAction
extends BillActionBase {
    public String getName() {
        return "bill-load";
    }

    public String getTitle() {
        return "\u52a0\u8f7d";
    }

    public String getIcon() {
        return null;
    }

    public boolean isInner() {
        return true;
    }

    @Override
    public void execute(BillModel model, Map<String, Object> params) {
        if (model.editing()) {
            model.save();
        }
        model.loadByCode((String)params.get("code"));
    }

    @Override
    public String[] getModelParams() {
        return BillConsts.ACTION_PARAM_EDIT;
    }
}

