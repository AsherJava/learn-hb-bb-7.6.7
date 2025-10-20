/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.bill.action;

import com.jiuqi.va.bill.impl.BillActionBase;
import org.springframework.stereotype.Component;

@Component
public class LastBrowseAction
extends BillActionBase {
    public String getName() {
        return "bill-last";
    }

    public String getTitle() {
        return "\u672b\u5f20";
    }

    public String getIcon() {
        return "@va/va-iconfont icona-16_GJ_A_VA_mozhang";
    }

    public String getActionPriority() {
        return "009";
    }
}

