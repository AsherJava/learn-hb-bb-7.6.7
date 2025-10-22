/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.excel.config;

import com.jiuqi.nr.data.excel.config.ConfigProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ApplicationObjectSupport;

@Configuration
@ComponentScan(basePackages={"com.jiuqi.nr.data.excel.service", "com.jiuqi.nr.data.excel.extend", "com.jiuqi.nr.data.excel.export", "com.jiuqi.nr.data.excel.init"})
@EnableConfigurationProperties(value={ConfigProperties.class})
public class DataExcelConfig
extends ApplicationObjectSupport {
}

