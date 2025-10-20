/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nvwa.quickreport.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@ComponentScan(basePackages={"com.jiuqi.nvwa.quickreport"})
@PropertySource(value={"classpath:quickreport-whitelist.properties"})
@Configuration
public class NvwaQuickReportStarterConfig {
}

