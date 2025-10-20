/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.common.base;

import com.jiuqi.common.base.env.GcEnvVarProperties;
import com.jiuqi.common.base.env.GcSecurityProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages={"com.jiuqi.common.base"})
@EnableConfigurationProperties(value={GcEnvVarProperties.class, GcSecurityProperties.class})
public class BaseAutoConfiguration {
}

