/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.front;

import com.jiuqi.va.biz.front.FrontPluginDefine;

public interface FrontPluginDefineFactory {
    public String getName();

    public Class<? extends FrontPluginDefine> getType();
}

