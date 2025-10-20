/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.biz.intf.model.Plugin
 *  com.jiuqi.va.biz.intf.model.PluginDefine
 */
package com.jiuqi.va.workflow.plugin.processparam;

import com.jiuqi.va.biz.intf.model.Plugin;
import com.jiuqi.va.biz.intf.model.PluginDefine;
import com.jiuqi.va.workflow.plugin.processparam.ProcessParamPluginDefine;

public class ProcessParamPlugin
implements Plugin {
    private ProcessParamPluginDefine processParamPluginDefine;

    public PluginDefine getDefine() {
        return this.processParamPluginDefine;
    }

    public void setProcessParamPluginDefine(ProcessParamPluginDefine processParamPluginDefine) {
        this.processParamPluginDefine = processParamPluginDefine;
    }
}

