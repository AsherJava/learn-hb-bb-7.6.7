/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.authority.login.config.NvwaAuthorityLoginProperties
 */
package com.jiuqi.nvwa.starter.authority.config;

import com.jiuqi.nvwa.authority.login.config.NvwaAuthorityLoginProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages={"com.jiuqi.nvwa.starter.authority"})
@EnableConfigurationProperties(value={NvwaAuthorityLoginProperties.class})
public class NvwaStarterAuthorityConfig {
}

