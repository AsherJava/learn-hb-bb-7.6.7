/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.biz.impl.data.DataImpl
 *  com.jiuqi.va.biz.impl.model.ModelImpl
 *  com.jiuqi.va.biz.intf.data.DataPostEvent
 *  com.jiuqi.va.biz.intf.model.Model
 *  com.jiuqi.va.biz.intf.model.Plugin
 *  com.jiuqi.va.biz.intf.model.PluginDefine
 *  com.jiuqi.va.biz.scene.impl.ScenePluginType
 */
package com.jiuqi.va.bill.plugin;

import com.jiuqi.va.bill.impl.BillModelImpl;
import com.jiuqi.va.bill.plugin.event.SuperEditDataPosEvent;
import com.jiuqi.va.biz.impl.data.DataImpl;
import com.jiuqi.va.biz.impl.model.ModelImpl;
import com.jiuqi.va.biz.intf.data.DataPostEvent;
import com.jiuqi.va.biz.intf.model.Model;
import com.jiuqi.va.biz.intf.model.Plugin;
import com.jiuqi.va.biz.intf.model.PluginDefine;
import com.jiuqi.va.biz.scene.impl.ScenePluginType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ScenePluginTypeImpl
extends ScenePluginType {
    @Autowired(required=false)
    private SuperEditDataPosEvent superEditDataPosEvent;

    public Class<? extends ModelImpl> getDependModel() {
        return BillModelImpl.class;
    }

    public void initPlugin(Plugin plugin, PluginDefine pluginDefine, Model model) {
        super.initPlugin(plugin, pluginDefine, model);
        if (this.superEditDataPosEvent != null) {
            DataImpl data = (DataImpl)model.getPlugins().get(DataImpl.class);
            data.registerDataPostEvent((DataPostEvent)this.superEditDataPosEvent);
        }
    }
}

