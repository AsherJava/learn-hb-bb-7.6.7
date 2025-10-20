/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.intf.model;

import com.jiuqi.va.biz.intf.model.DeclarePlugin;
import com.jiuqi.va.biz.intf.model.Model;
import com.jiuqi.va.biz.intf.model.ModelDefine;
import com.jiuqi.va.biz.intf.model.Plugin;
import com.jiuqi.va.biz.intf.model.PluginDefine;
import com.jiuqi.va.biz.intf.value.NamedElement;

public interface PluginType
extends NamedElement {
    @Override
    public String getName();

    public String getTitle();

    public Class<? extends PluginDefine> getPluginDefineClass();

    public Class<? extends Plugin> getPluginClass();

    public Class<? extends Model> getDependModel();

    public String[] getDependPlugins();

    public void initPluginDefine(PluginDefine var1, ModelDefine var2);

    default public void initPluginDefine(PluginDefine pluginDefine, ModelDefine modelDefine, String externalViewName) {
        this.initPluginDefine(pluginDefine, modelDefine);
    }

    public void initPlugin(Plugin var1, PluginDefine var2, Model var3);

    default public boolean canAdd() {
        return true;
    }

    default public void pluginDefineLoaded(PluginDefine pluginDefine, ModelDefine modelDefine) {
    }

    default public void declare(DeclarePlugin declarePlugin) {
    }
}

