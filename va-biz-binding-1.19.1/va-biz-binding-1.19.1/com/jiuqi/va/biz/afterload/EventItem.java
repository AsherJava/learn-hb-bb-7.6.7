/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.afterload;

import com.jiuqi.va.biz.afterload.EventOption;
import com.jiuqi.va.biz.intf.model.Model;
import com.jiuqi.va.biz.intf.model.ModelDefine;
import java.util.Map;
import java.util.Set;

public interface EventItem {
    public String getName();

    public String getTitle();

    public boolean enable(Model var1);

    public EventOption execute(Model var1);

    default public int getSortNum() {
        return 0;
    }

    default public Map<String, Set<String>> getTriggerFields(ModelDefine modelDefine) {
        return null;
    }
}

