/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.view.intf;

import com.jiuqi.va.biz.intf.value.Convert;
import java.util.Map;
import java.util.UUID;

public interface Control {
    public Map<String, Object> getProps();

    default public UUID getId() {
        return Convert.cast(this.getProps().get("id"), UUID.class);
    }

    default public String getType() {
        return Convert.cast(this.getProps().get("type"), String.class);
    }

    default public boolean isWizard() {
        return "v-wizard".equals(this.getType());
    }
}

