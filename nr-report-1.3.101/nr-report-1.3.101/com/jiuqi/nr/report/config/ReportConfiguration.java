/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.report.config;

import java.util.Locale;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

@Configuration
@ComponentScan(basePackages={"com.jiuqi.nr.report"})
public class ReportConfiguration {
    @Bean(name={"reportMessageSource"})
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageBundle = new ResourceBundleMessageSource();
        messageBundle.setUseCodeAsDefaultMessage(true);
        messageBundle.setBasenames("messages/messages", "messages/Report");
        messageBundle.setDefaultEncoding("UTF-8");
        messageBundle.setDefaultLocale(Locale.SIMPLIFIED_CHINESE);
        return messageBundle;
    }
}

