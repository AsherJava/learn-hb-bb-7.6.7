/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.datav.dashboard.factory.AbstractResourceWidgetConfigFactory
 *  com.jiuqi.nvwa.datav.dashboard.manager.ResourceWidgetConfigFactoryManager
 */
package com.jiuqi.nr.zbquery.config;

import com.jiuqi.nr.zbquery.dashboard.DashBoardRefWidgetConfigFactory;
import com.jiuqi.nvwa.datav.dashboard.factory.AbstractResourceWidgetConfigFactory;
import com.jiuqi.nvwa.datav.dashboard.manager.ResourceWidgetConfigFactoryManager;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

@Configuration
@Lazy(value=false)
@ComponentScan(value={"com.jiuqi.nr.zbquery"})
public class ZBQueryConfig {
    public ZBQueryConfig() {
        try {
            ResourceWidgetConfigFactoryManager.getInstance().registerFactory((AbstractResourceWidgetConfigFactory)new DashBoardRefWidgetConfigFactory());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Bean(name={"zbqueryMessageSource"})
    @Lazy(value=false)
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageBundle = new ReloadableResourceBundleMessageSource();
        messageBundle.setUseCodeAsDefaultMessage(true);
        messageBundle.setBasename("classpath:i18n/zbquery");
        messageBundle.setDefaultEncoding("UTF-8");
        return messageBundle;
    }
}

