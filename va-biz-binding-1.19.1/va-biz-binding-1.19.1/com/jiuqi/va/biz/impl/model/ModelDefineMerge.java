/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.impl.model;

import com.jiuqi.va.biz.impl.model.ModelDefineImpl;
import com.jiuqi.va.biz.impl.model.PluginDefineMerge;
import com.jiuqi.va.biz.intf.model.PluginDefine;

public class ModelDefineMerge {
    public static void merge(ModelDefineImpl target, ModelDefineImpl base) {
        if (base.getMetaInfo() != null) {
            target.setMetaInfo(base.getMetaInfo());
        }
        base.getPlugins().stream().forEach(o -> {
            PluginDefine plugin = target.getPlugins().find(o.getType());
            if (plugin == null) {
                target.addPlugin((PluginDefine)o);
            } else {
                PluginDefineMerge.merge(plugin, o);
            }
        });
    }
}

