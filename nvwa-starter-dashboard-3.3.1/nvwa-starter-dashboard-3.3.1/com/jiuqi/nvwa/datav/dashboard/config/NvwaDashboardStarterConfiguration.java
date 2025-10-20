/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nvwa.datav.dashboard.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value={"classpath:dashboard-whitelist.properties"})
@ComponentScan(value={"com.jiuqi.nvwa.datav", "com.jiuqi.nvwa.dataanalyze"})
public class NvwaDashboardStarterConfiguration {
}

