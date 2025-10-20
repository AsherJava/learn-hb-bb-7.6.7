/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.impl.action;

import com.jiuqi.va.biz.impl.action.ActionBase;
import org.springframework.stereotype.Component;

@Component
public class CloseAction
extends ActionBase {
    @Override
    public String getName() {
        return "close";
    }

    @Override
    public String getTitle() {
        return "\u5173\u95ed";
    }

    @Override
    public String getIcon() {
        return "@va/va-iconfont icona-16_GJ_A_VA_guanbi";
    }

    @Override
    public String getActionPriority() {
        return "099";
    }
}

