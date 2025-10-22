/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bi.integration;

import com.jiuqi.nr.bi.integration.IChartServices;
import com.jiuqi.nr.bi.integration.IReportServices;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@ComponentScan(basePackages={"com.jiuqi.nr.bi.integration"})
@Configuration
public class BIIntegrationConfig {
    public IChartServices getChartServices() {
        return new IChartServices();
    }

    public IReportServices getReportServices() {
        return new IReportServices();
    }
}

