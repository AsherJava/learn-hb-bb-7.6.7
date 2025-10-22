/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class EnableTask2Config {
    private boolean enable;
    private String create;

    @Value(value="${jiuqi.nr.task2.enable:false}")
    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    @Value(value="${jiuqi.nr.task2.create:2.0}")
    public void setCreate(String create) {
        this.create = create;
    }

    public boolean isEnable() {
        return this.enable;
    }

    public boolean createNewVersion() {
        return "2.0".equals(this.create);
    }
}

