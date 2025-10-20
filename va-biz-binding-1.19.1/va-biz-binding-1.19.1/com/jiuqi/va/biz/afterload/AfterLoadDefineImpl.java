/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.afterload;

import com.jiuqi.va.biz.afterload.AfterLoadDefine;
import com.jiuqi.va.biz.afterload.EventOption;
import com.jiuqi.va.biz.impl.model.PluginDefineImpl;
import java.util.ArrayList;
import java.util.List;

public class AfterLoadDefineImpl
extends PluginDefineImpl
implements AfterLoadDefine {
    private List<EventOption> options = new ArrayList<EventOption>();

    @Override
    public List<? extends EventOption> getEventOptions() {
        ArrayList eventOptions = new ArrayList();
        return eventOptions;
    }

    public List<EventOption> getOptions() {
        return this.options;
    }

    public void setOptions(List<EventOption> options) {
        this.options = options;
    }
}

