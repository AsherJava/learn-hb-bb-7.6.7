/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.bill.action;

import com.jiuqi.va.bill.impl.BillActionBase;
import org.springframework.stereotype.Component;

@Component
public class PreviousBrowseAction
extends BillActionBase {
    public String getName() {
        return "bill-previous";
    }

    public String getTitle() {
        return "\u4e0a\u5f20";
    }

    public String getIcon() {
        return "@va/va-iconfont icona-16_GJ_A_VA_shangyizhang";
    }

    public String getActionPriority() {
        return "007";
    }
}

