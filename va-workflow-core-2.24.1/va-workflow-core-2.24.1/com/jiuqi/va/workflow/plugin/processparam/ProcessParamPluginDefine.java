/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.biz.intf.model.PluginDefine
 *  com.jiuqi.va.domain.workflow.ProcessParam
 */
package com.jiuqi.va.workflow.plugin.processparam;

import com.jiuqi.va.biz.intf.model.PluginDefine;
import com.jiuqi.va.domain.workflow.ProcessParam;
import java.util.List;

public class ProcessParamPluginDefine
implements PluginDefine {
    private String type;
    private List<ProcessParam> processParam;

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<ProcessParam> getProcessParam() {
        return this.processParam;
    }

    public void setProcessParam(List<ProcessParam> processParam) {
        this.processParam = processParam;
    }
}

