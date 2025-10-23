/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.zb.scheme.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(value={"com.jiuqi.nr.zb.scheme"})
public class ZbSchemeAutoConfiguration {
    public ZbSchemeAutoConfiguration() {
        System.out.println("_____________zbScheme___________________");
    }
}

