/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.impl.model;

import com.jiuqi.va.biz.intf.model.Model;
import com.jiuqi.va.biz.intf.model.Plugin;
import com.jiuqi.va.biz.intf.model.PluginDefine;

public class PluginImpl
implements Plugin {
    private Model model;
    private PluginDefine define;

    public Model getModel() {
        return this.model;
    }

    void setModel(Model model) {
        this.model = model;
    }

    @Override
    public PluginDefine getDefine() {
        return this.define;
    }

    void setDefine(PluginDefine define) {
        this.define = define;
    }
}

