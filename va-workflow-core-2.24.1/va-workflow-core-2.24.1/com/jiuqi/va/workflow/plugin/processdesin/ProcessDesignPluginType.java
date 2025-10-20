/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.biz.intf.model.Model
 *  com.jiuqi.va.biz.intf.model.ModelDefine
 *  com.jiuqi.va.biz.intf.model.Plugin
 *  com.jiuqi.va.biz.intf.model.PluginDefine
 *  com.jiuqi.va.biz.intf.model.PluginType
 */
package com.jiuqi.va.workflow.plugin.processdesin;

import com.jiuqi.va.biz.intf.model.Model;
import com.jiuqi.va.biz.intf.model.ModelDefine;
import com.jiuqi.va.biz.intf.model.Plugin;
import com.jiuqi.va.biz.intf.model.PluginDefine;
import com.jiuqi.va.biz.intf.model.PluginType;
import com.jiuqi.va.workflow.model.impl.WorkflowModelImpl;
import com.jiuqi.va.workflow.plugin.processdesin.ProcessDesignPlugin;
import com.jiuqi.va.workflow.plugin.processdesin.ProcessDesignPluginDefine;
import org.springframework.stereotype.Component;

@Component
public class ProcessDesignPluginType
implements PluginType {
    public static final String NAME = "processDesignPlugin";
    public static final String TITLE = "\u6d41\u7a0b\u8bbe\u8ba1\u5668";

    public String getName() {
        return NAME;
    }

    public String getTitle() {
        return TITLE;
    }

    public Class<? extends ProcessDesignPluginDefine> getPluginDefineClass() {
        return ProcessDesignPluginDefine.class;
    }

    public Class<? extends ProcessDesignPlugin> getPluginClass() {
        return ProcessDesignPlugin.class;
    }

    public Class<? extends WorkflowModelImpl> getDependModel() {
        return WorkflowModelImpl.class;
    }

    public String[] getDependPlugins() {
        return null;
    }

    public void initPluginDefine(PluginDefine pluginDefine, ModelDefine modelDefine) {
        ((ProcessDesignPluginDefine)pluginDefine).setType(this.getName());
    }

    public void initPlugin(Plugin plugin, PluginDefine pluginDefine, Model model) {
        ((ProcessDesignPlugin)plugin).setProcessDesignPluginDefine((ProcessDesignPluginDefine)pluginDefine);
    }
}

