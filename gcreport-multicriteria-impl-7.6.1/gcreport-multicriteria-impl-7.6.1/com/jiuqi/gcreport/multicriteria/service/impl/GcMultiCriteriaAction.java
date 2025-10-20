/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.common.action.env.GcActionItemEnv
 *  com.jiuqi.gcreport.common.action.impl.AbstractGcActionItem
 */
package com.jiuqi.gcreport.multicriteria.service.impl;

import com.jiuqi.gcreport.common.action.env.GcActionItemEnv;
import com.jiuqi.gcreport.common.action.impl.AbstractGcActionItem;
import org.springframework.stereotype.Component;

@Component
public class GcMultiCriteriaAction
extends AbstractGcActionItem {
    protected GcMultiCriteriaAction() {
        super("gcMultiCriteriaAction", "\u51c6\u5219\u8f6c\u6362", "\u8fdb\u884c\u591a\u51c6\u5219\u8f6c\u6362", "#icon-_GJYxiayifangan");
    }

    public Object execute(GcActionItemEnv actionItemEnv) {
        return null;
    }
}

