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
public class GcGotoOffsetEntryAction
extends AbstractGcActionItem {
    protected GcGotoOffsetEntryAction() {
        super("gcGotoOffsetEntryAction", "\u5207\u6362\u5230\u62b5\u9500\u5206\u5f55", "\u5408\u5e76\u5355\u4f4d\u7a7f\u900f\u5230\u8c03\u6574\u62b5\u9500\u5206\u5f55\u754c\u9762", "#icon-16_GJ_A_GC_qiehuandaoheduijiemian");
    }

    public Object execute(GcActionItemEnv actionItemEnv) {
        return null;
    }
}

