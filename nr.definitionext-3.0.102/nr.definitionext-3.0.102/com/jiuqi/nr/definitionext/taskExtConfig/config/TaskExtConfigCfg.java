/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definitionext.taskExtConfig.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@ComponentScan(basePackages={"com.jiuqi.nr.definitionext"})
@Configuration
public class TaskExtConfigCfg {
    public String getVersion() {
        return "version";
    }
}

