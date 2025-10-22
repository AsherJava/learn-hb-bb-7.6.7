/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataentry.gather;

import com.jiuqi.nr.dataentry.gather.ActionItem;
import com.jiuqi.nr.dataentry.gather.ActionType;

public interface ActionsHoldItem
extends ActionItem {
    @Override
    default public ActionType getActionType() {
        return ActionType.HOLDGROUP;
    }
}

