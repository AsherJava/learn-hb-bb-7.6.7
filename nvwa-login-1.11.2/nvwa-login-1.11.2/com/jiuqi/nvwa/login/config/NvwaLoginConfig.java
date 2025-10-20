/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nvwa.login.config;

import com.jiuqi.nvwa.login.config.NvwaLoginProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan(basePackages={"com.jiuqi.nvwa.login"})
@EnableConfigurationProperties(value={NvwaLoginProperties.class})
@PropertySource(value={"classpath:nvwa-login.properties"})
public class NvwaLoginConfig {
}

