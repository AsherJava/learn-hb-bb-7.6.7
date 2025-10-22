/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.common.action.env.GcActionItemEnv
 *  com.jiuqi.gcreport.common.action.impl.AbstractGcActionItem
 */
package com.jiuqi.gcreport.inputdata.action;

import com.jiuqi.gcreport.common.action.env.GcActionItemEnv;
import com.jiuqi.gcreport.common.action.impl.AbstractGcActionItem;
import org.springframework.stereotype.Component;

@Component
public class GcViewOneEntryDetailsAction
extends AbstractGcActionItem {
    protected GcViewOneEntryDetailsAction() {
        super("gcViewOneEntryDetailsAction", "\u67e5\u770b\u8bb0\u5f55\u5173\u8054\u6570\u636e", "\u9009\u4e2d\u7684\u8bb0\u5f55\u4e3a\u5df2\u62b5\u9500\u65f6\uff0c\u67e5\u770b\u5206\u5f55\u660e\u7ec6-\u672a\u62b5\u9500\u65f6\uff0c\u6309\u5355\u4f4d\u548c\u89c4\u5219\u8fdb\u884c\u7b5b\u9009", "#icon-_GJZshujuchaxun");
    }

    public Object execute(GcActionItemEnv actionItemEnv) {
        return null;
    }
}

