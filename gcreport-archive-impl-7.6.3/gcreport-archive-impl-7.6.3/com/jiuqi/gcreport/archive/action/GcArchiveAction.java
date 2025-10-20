/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.common.action.env.GcActionItemEnv
 *  com.jiuqi.gcreport.common.action.impl.AbstractGcActionItem
 */
package com.jiuqi.gcreport.archive.action;

import com.jiuqi.gcreport.common.action.env.GcActionItemEnv;
import com.jiuqi.gcreport.common.action.impl.AbstractGcActionItem;
import org.springframework.stereotype.Component;

@Component
public class GcArchiveAction
extends AbstractGcActionItem {
    protected GcArchiveAction() {
        super("gcArchiveAction", "\u62a5\u8868\u5f52\u6863", "\u5408\u5e76\u62a5\u8868\u7535\u5b50\u6863\u6848\u5f52\u6863", "#icon-16_DH_A_GC_hbbbgz");
    }

    public Object execute(GcActionItemEnv actionItemEnv) {
        return null;
    }
}

