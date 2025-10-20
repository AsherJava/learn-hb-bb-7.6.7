/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.intf.action;

import com.jiuqi.va.biz.intf.action.Action;
import com.jiuqi.va.biz.intf.model.Model;
import com.jiuqi.va.biz.intf.value.NamedContainer;
import java.util.List;

public interface ActionManager
extends NamedContainer<Action> {
    public List<Action> getActionList(Class<? extends Model> var1);

    public List<Action> getAllActionList();
}

