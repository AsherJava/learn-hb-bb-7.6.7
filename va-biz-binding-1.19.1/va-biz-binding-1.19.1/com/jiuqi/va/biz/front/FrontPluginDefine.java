/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonAutoDetect
 *  com.fasterxml.jackson.annotation.JsonAutoDetect$Visibility
 */
package com.jiuqi.va.biz.front;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.jiuqi.va.biz.front.FrontModelDefine;
import com.jiuqi.va.biz.intf.model.ModelDefine;
import com.jiuqi.va.biz.intf.model.PluginDefine;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

@JsonAutoDetect(getterVisibility=JsonAutoDetect.Visibility.NONE, isGetterVisibility=JsonAutoDetect.Visibility.NONE, fieldVisibility=JsonAutoDetect.Visibility.ANY)
public class FrontPluginDefine {
    String type;
    protected final transient FrontModelDefine frontModelDefine;

    public FrontPluginDefine() {
        this.frontModelDefine = null;
    }

    public FrontPluginDefine(FrontModelDefine frontModelDefine, PluginDefine pluginDefine) {
        this.frontModelDefine = frontModelDefine;
        this.type = pluginDefine.getType();
    }

    protected void initialize() {
    }

    protected Map<String, Set<String>> getTableFields(ModelDefine modelDefine) {
        return Collections.emptyMap();
    }

    public String getType() {
        return this.type;
    }
}

