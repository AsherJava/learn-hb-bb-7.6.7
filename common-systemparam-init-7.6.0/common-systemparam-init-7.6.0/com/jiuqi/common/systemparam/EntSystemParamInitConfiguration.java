/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.common.systemparam;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages={"com.jiuqi.common.systemparam"})
public class EntSystemParamInitConfiguration {
    @Bean(value={"vaParamSyncTableResourcesExport"})
    void vaParamSyncTableResourcesExport() {
    }
}

