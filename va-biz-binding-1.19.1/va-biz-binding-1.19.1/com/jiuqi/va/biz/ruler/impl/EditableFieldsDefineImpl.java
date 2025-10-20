/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.ruler.impl;

import com.jiuqi.va.biz.impl.model.PluginDefineImpl;
import java.util.Map;

public class EditableFieldsDefineImpl
extends PluginDefineImpl {
    private Map<String, Object> defineInfo;

    public Map<String, Object> getDefineInfo() {
        return this.defineInfo;
    }

    public void setDefineInfo(Map<String, Object> defineInfo) {
        this.defineInfo = defineInfo;
    }
}

