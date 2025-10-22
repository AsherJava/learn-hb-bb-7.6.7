/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.datav.dashboard.exception.DashboardException
 *  com.jiuqi.nvwa.datav.dashboard.factory.AbstractResourceWidgetConfigFactory
 *  com.jiuqi.nvwa.datav.dashboard.manager.ResourceWidgetConfigFactoryManager
 */
package com.jiuqi.nr.arenvwadashboard.config;

import com.jiuqi.nr.arenvwadashboard.expand.AnalysisReportRefWidgetConfigFactory;
import com.jiuqi.nvwa.datav.dashboard.exception.DashboardException;
import com.jiuqi.nvwa.datav.dashboard.factory.AbstractResourceWidgetConfigFactory;
import com.jiuqi.nvwa.datav.dashboard.manager.ResourceWidgetConfigFactoryManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@Configuration
@Lazy(value=false)
@ComponentScan(basePackages={"com.jiuqi.nr.arenvwadashboard.expand"})
public class AreNvwaDashboardConfiguration {
    private static final Logger logger = LoggerFactory.getLogger(AreNvwaDashboardConfiguration.class);

    public AreNvwaDashboardConfiguration() {
        try {
            ResourceWidgetConfigFactoryManager.getInstance().registerFactory((AbstractResourceWidgetConfigFactory)new AnalysisReportRefWidgetConfigFactory());
        }
        catch (DashboardException e) {
            logger.error("\u5206\u6790\u62a5\u544a\u62d3\u5c55\u7ec4\u4ef6\u6ce8\u518c\u5931\u8d25", (Object)e.getMessage());
            e.printStackTrace();
        }
    }
}

