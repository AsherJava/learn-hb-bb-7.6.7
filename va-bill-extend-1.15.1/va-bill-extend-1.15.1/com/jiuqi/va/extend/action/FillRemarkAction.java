/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.bill.impl.BillActionBase
 */
package com.jiuqi.va.extend.action;

import com.jiuqi.va.bill.impl.BillActionBase;
import org.springframework.stereotype.Component;

@Component
public class FillRemarkAction
extends BillActionBase {
    public String getName() {
        return "fill-remark";
    }

    public String getTitle() {
        return "\u586b\u62a5\u8bf4\u660e";
    }

    public String getIcon() {
        return "@va/va-iconfont icona-16_GJ_A_VA_tianbaoshuoming";
    }

    public String getActionPriority() {
        return "022";
    }
}

