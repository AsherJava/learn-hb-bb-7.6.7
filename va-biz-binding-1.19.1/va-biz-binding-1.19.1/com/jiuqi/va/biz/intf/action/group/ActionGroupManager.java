/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.intf.action.group;

import com.jiuqi.va.biz.intf.action.group.ActionGroup;
import com.jiuqi.va.biz.intf.value.NamedContainer;

public interface ActionGroupManager
extends NamedContainer<ActionGroup> {
    public ActionGroup getActionGroupById(String var1);
}

