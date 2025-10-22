/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.finalaccountsaudit.test.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@ComponentScan(basePackages={"com.jiuqi.nr.finalaccountsaudit"})
@Configuration
public class AuditTestConfig {
    public String getVersion() {
        return "version";
    }
}

