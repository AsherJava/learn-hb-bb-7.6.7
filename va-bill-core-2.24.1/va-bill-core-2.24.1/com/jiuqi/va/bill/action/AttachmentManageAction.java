/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.bill.action;

import com.jiuqi.va.bill.impl.BillActionBase;
import com.jiuqi.va.bill.intf.BillModel;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class AttachmentManageAction
extends BillActionBase {
    public String getName() {
        return "bill-attachment-manage";
    }

    public String getTitle() {
        return "\u9644\u4ef6\u7ba1\u7406";
    }

    public String getActionPriority() {
        return "027";
    }

    @Override
    public void execute(BillModel model, Map<String, Object> params) {
    }
}

