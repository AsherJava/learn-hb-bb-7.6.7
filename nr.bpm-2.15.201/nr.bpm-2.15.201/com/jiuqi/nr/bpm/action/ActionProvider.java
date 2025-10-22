/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.action;

import com.jiuqi.nr.bpm.action.ActionBase;
import java.util.List;

public interface ActionProvider {
    public ActionBase getActionBaseByID(String var1);

    public ActionBase getActionBaseByCode(String var1);

    public List<ActionBase> getAllActionBase();
}

