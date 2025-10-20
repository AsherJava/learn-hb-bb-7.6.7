/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.afterload;

import com.jiuqi.va.biz.afterload.AfterLoadDefineImpl;
import com.jiuqi.va.biz.afterload.AfterLoadEventImpl;
import com.jiuqi.va.biz.impl.model.PluginDefineImpl;
import com.jiuqi.va.biz.impl.model.PluginTypeBase;
import com.jiuqi.va.biz.intf.model.Model;
import com.jiuqi.va.biz.intf.model.ModelDefine;
import com.jiuqi.va.biz.intf.model.Plugin;
import com.jiuqi.va.biz.intf.model.PluginDefine;
import org.springframework.stereotype.Component;

@Component
public class AfterLoadEventPluginType
extends PluginTypeBase {
    public static final String NAME = "afterLoadEvent";
    public static final String TITLE = "\u5355\u636e\u524d\u7aef\u6253\u5f00\u540e\u4e8b\u4ef6";

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public String getTitle() {
        return TITLE;
    }

    @Override
    public boolean canAdd() {
        return false;
    }

    @Override
    public void pluginDefineLoaded(PluginDefine pluginDefine, ModelDefine modelDefine) {
        super.pluginDefineLoaded(pluginDefine, modelDefine);
    }

    @Override
    public void initPlugin(Plugin plugin, PluginDefine pluginDefine, Model model) {
        super.initPlugin(plugin, pluginDefine, model);
    }

    @Override
    public Class<? extends PluginDefineImpl> getPluginDefineClass() {
        return AfterLoadDefineImpl.class;
    }

    public Class<? extends AfterLoadEventImpl> getPluginClass() {
        return AfterLoadEventImpl.class;
    }
}

