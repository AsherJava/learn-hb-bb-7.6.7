/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataentry.gather.ActionItemImpl
 *  com.jiuqi.nr.dataentry.gather.ActionType
 */
package com.jiuqi.gcreport.common.action.impl;

import com.jiuqi.gcreport.common.action.env.GcActionItemEnv;
import com.jiuqi.nr.dataentry.gather.ActionItemImpl;
import com.jiuqi.nr.dataentry.gather.ActionType;

public abstract class AbstractGcActionItem
extends ActionItemImpl {
    protected AbstractGcActionItem(String code, String title, String desc, String icon) {
        super(code, title, desc, icon);
    }

    protected AbstractGcActionItem(String code, String title, ActionType actionType, String desc) {
        super(code, title, desc, actionType);
    }

    public abstract Object execute(GcActionItemEnv var1);

    public boolean isVisible(String visibleContextJson) {
        return true;
    }

    public boolean isEnable(String enableContextJson) {
        return true;
    }
}

