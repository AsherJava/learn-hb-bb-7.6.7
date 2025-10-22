/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.fieldselect.config;

import com.jiuqi.nr.fieldselect.service.IFieldSelectController;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@ComponentScan(basePackages={"com.jiuqi.nr.fieldselect"})
@Configuration
public class FieldSelectConfig {
    public IFieldSelectController getFieldSelectController() {
        return new IFieldSelectController();
    }
}

