/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonAutoDetect
 *  com.fasterxml.jackson.annotation.JsonAutoDetect$Visibility
 */
package com.jiuqi.va.biz.impl.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.jiuqi.va.biz.intf.model.PluginDefine;

@JsonAutoDetect(getterVisibility=JsonAutoDetect.Visibility.NONE, isGetterVisibility=JsonAutoDetect.Visibility.NONE, fieldVisibility=JsonAutoDetect.Visibility.ANY)
public class PluginDefineImpl
implements PluginDefine {
    private String type;
    private transient int state;
    private static final int PLUGIN_STATE_LOCK = 3;

    @Override
    public String getType() {
        return this.type;
    }

    protected void setType(String type) {
        this.requireNotLocked();
        this.type = type;
    }

    @Override
    public boolean isLocked() {
        return this.state == 3;
    }

    void setState(int state) {
        this.requireNotLocked();
        this.state = state;
    }
}

