/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.intf.model;

import com.jiuqi.va.biz.intf.model.Model;
import com.jiuqi.va.biz.intf.model.PluginType;
import com.jiuqi.va.biz.intf.value.NamedManager;
import java.util.List;
import java.util.Set;

public interface PluginManager
extends NamedManager<PluginType> {
    public List<PluginType> getPluginList(Class<? extends Model> var1);

    public Set<String> getDependPlugins(String var1);
}

