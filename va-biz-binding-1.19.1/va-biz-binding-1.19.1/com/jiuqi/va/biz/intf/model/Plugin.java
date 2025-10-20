/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.intf.model;

import com.jiuqi.va.biz.intf.model.PluginDefine;
import com.jiuqi.va.biz.intf.value.TypedElement;

public interface Plugin
extends TypedElement {
    @Override
    default public String getType() {
        return this.getDefine().getType();
    }

    public PluginDefine getDefine();
}

