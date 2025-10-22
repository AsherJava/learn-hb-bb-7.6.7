/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.engine.config;

import com.jiuqi.nr.data.engine.config.DataGatherConfig;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(value={DataGatherConfig.class})
@ComponentScan(basePackages={"com.jiuqi.nr.data.engine"})
public class DataEngineConfiguration {
}

