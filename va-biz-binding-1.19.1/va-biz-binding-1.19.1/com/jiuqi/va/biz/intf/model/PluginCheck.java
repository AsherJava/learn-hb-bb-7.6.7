/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.intf.model;

import com.jiuqi.va.biz.domain.PluginCheckResultVO;
import com.jiuqi.va.biz.intf.model.ModelDefine;
import com.jiuqi.va.biz.intf.model.PluginDefine;
import com.jiuqi.va.biz.intf.value.NamedElement;

public interface PluginCheck
extends NamedElement {
    public PluginCheckResultVO checkPlugin(PluginDefine var1, ModelDefine var2);

    public Class<? extends PluginDefine> getPluginDefine();
}

