/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.impl.action;

import com.jiuqi.va.biz.impl.action.ActionBase;
import com.jiuqi.va.biz.intf.model.Model;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class NullAction
extends ActionBase {
    @Override
    public String getName() {
        return "biz-null";
    }

    @Override
    public String getTitle() {
        return "\u7a7a\u52a8\u4f5c";
    }

    @Override
    public boolean isInner() {
        return true;
    }

    @Override
    public void execute(Model model, Map<String, Object> params) {
    }
}

