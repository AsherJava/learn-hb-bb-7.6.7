/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.bill.impl.BillModelImpl
 *  com.jiuqi.va.biz.impl.data.DataImpl
 *  com.jiuqi.va.biz.impl.model.ModelImpl
 *  com.jiuqi.va.biz.impl.model.PluginTypeBase
 *  com.jiuqi.va.biz.intf.data.DataPostEvent
 *  com.jiuqi.va.biz.intf.model.Model
 *  com.jiuqi.va.biz.intf.model.ModelDefine
 *  com.jiuqi.va.biz.intf.model.Plugin
 *  com.jiuqi.va.biz.intf.model.PluginDefine
 */
package com.jiuqi.va.extend.plugin;

import com.jiuqi.va.bill.impl.BillModelImpl;
import com.jiuqi.va.biz.impl.data.DataImpl;
import com.jiuqi.va.biz.impl.model.ModelImpl;
import com.jiuqi.va.biz.impl.model.PluginTypeBase;
import com.jiuqi.va.biz.intf.data.DataPostEvent;
import com.jiuqi.va.biz.intf.model.Model;
import com.jiuqi.va.biz.intf.model.ModelDefine;
import com.jiuqi.va.biz.intf.model.Plugin;
import com.jiuqi.va.biz.intf.model.PluginDefine;
import com.jiuqi.va.extend.plugin.event.VaBillBackWriteEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BackWriteRulePluginType
extends PluginTypeBase {
    public static final String NAME = "backWriteRule";
    public static final String TITLE = "\u53cd\u5199\u89c4\u5219";
    public static final String FORMULA_TYPE = "backWrite";
    @Autowired
    private VaBillBackWriteEvent vaBillBackWriteEvent;

    public String getName() {
        return NAME;
    }

    public String getTitle() {
        return TITLE;
    }

    public String[] getDependPlugins() {
        return new String[]{"ruler"};
    }

    public Class<? extends ModelImpl> getDependModel() {
        return BillModelImpl.class;
    }

    public void initPluginDefine(PluginDefine pluginDefine, ModelDefine modelDefine) {
        super.initPluginDefine(pluginDefine, modelDefine);
    }

    public void initPlugin(Plugin plugin, PluginDefine pluginDefine, Model model) {
        super.initPlugin(plugin, pluginDefine, model);
        DataImpl data = (DataImpl)model.getPlugins().get(DataImpl.class);
        data.registerDataPostEvent((DataPostEvent)this.vaBillBackWriteEvent);
    }

    public boolean canAdd() {
        return false;
    }
}

