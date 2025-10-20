/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.common.action.env.GcActionItemEnv
 *  com.jiuqi.gcreport.common.action.impl.AbstractGcActionItem
 */
package com.jiuqi.gcreport.journalsingle.action;

import com.jiuqi.gcreport.common.action.env.GcActionItemEnv;
import com.jiuqi.gcreport.common.action.impl.AbstractGcActionItem;
import org.springframework.stereotype.Component;

@Component
public class GcJournalAddAction
extends AbstractGcActionItem {
    protected GcJournalAddAction() {
        super("gcJournalAddAction", "\u5355\u6237\u8c03\u6574", "\u5f55\u5165\u65e5\u8bb0\u8d26\u6570\u636e", "#icon-_ZSYshujuluru");
    }

    public Object execute(GcActionItemEnv actionItemEnv) {
        return null;
    }
}

