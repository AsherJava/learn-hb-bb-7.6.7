/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.monitor.impl.config;

import com.jiuqi.gcreport.monitor.impl.config.MonitorSceneCollectUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(value={"com.jiuqi.gcreport.monitor.impl", "com.jiuqi.gcreport.monitor.impl.scene"})
public class MonitorRunConfiguration {
    @Bean
    public MonitorSceneCollectUtils getMonitorSceneCollectUtils() {
        return new MonitorSceneCollectUtils();
    }
}

