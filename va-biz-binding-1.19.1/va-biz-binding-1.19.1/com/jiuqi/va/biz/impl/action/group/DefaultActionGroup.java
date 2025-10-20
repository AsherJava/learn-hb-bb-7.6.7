/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.impl.action.group;

import com.jiuqi.va.biz.intf.action.group.ActionGroup;
import org.springframework.stereotype.Component;

@Component
public class DefaultActionGroup
implements ActionGroup {
    public static final String ID = "00";

    @Override
    public String getName() {
        return "default-group";
    }

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public String getTitle() {
        return "\u9ed8\u8ba4\u5206\u7ec4";
    }
}

