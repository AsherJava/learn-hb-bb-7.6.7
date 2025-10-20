/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.impl.model;

import com.jiuqi.va.biz.impl.model.ModelImpl;
import com.jiuqi.va.biz.impl.model.PluginDefineImpl;
import com.jiuqi.va.biz.impl.model.PluginImpl;
import com.jiuqi.va.biz.intf.model.Model;
import com.jiuqi.va.biz.intf.model.ModelDefine;
import com.jiuqi.va.biz.intf.model.Plugin;
import com.jiuqi.va.biz.intf.model.PluginDefine;
import com.jiuqi.va.biz.intf.model.PluginType;

public abstract class PluginTypeBase
implements PluginType {
    public Class<? extends PluginDefineImpl> getPluginDefineClass() {
        return PluginDefineImpl.class;
    }

    public Class<? extends PluginImpl> getPluginClass() {
        return PluginImpl.class;
    }

    public Class<? extends ModelImpl> getDependModel() {
        return ModelImpl.class;
    }

    @Override
    public String[] getDependPlugins() {
        return null;
    }

    @Override
    public void initPluginDefine(PluginDefine pluginDefine, ModelDefine modelDefine) {
        PluginDefineImpl pluginDefineImpl = (PluginDefineImpl)pluginDefine;
        pluginDefineImpl.setType(this.getName());
    }

    @Override
    public void initPlugin(Plugin plugin, PluginDefine pluginDefine, Model model) {
        PluginImpl pluginImpl = (PluginImpl)plugin;
        pluginImpl.setModel(model);
        pluginImpl.setDefine(pluginDefine);
    }
}

