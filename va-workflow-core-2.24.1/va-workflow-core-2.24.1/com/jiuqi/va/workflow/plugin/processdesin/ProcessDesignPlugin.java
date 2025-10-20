/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.biz.intf.model.Plugin
 *  com.jiuqi.va.biz.intf.model.PluginDefine
 */
package com.jiuqi.va.workflow.plugin.processdesin;

import com.jiuqi.va.biz.intf.model.Plugin;
import com.jiuqi.va.biz.intf.model.PluginDefine;
import com.jiuqi.va.workflow.plugin.processdesin.ProcessDesignPluginDefine;

public class ProcessDesignPlugin
implements Plugin {
    private ProcessDesignPluginDefine processDesignPluginDefine;

    public PluginDefine getDefine() {
        return this.processDesignPluginDefine;
    }

    public void setProcessDesignPluginDefine(ProcessDesignPluginDefine processDesignPluginDefine) {
        this.processDesignPluginDefine = processDesignPluginDefine;
    }
}

