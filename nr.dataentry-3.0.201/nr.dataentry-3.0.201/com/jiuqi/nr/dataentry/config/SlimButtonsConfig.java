/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataentry.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SlimButtonsConfig {
    @Value(value="${jiuqi.nvwa.function-slim.dataentry.buttons:}")
    public String buttons;

    public String getButtons() {
        return this.buttons;
    }

    public void setButtons(String buttons) {
        this.buttons = buttons;
    }
}

