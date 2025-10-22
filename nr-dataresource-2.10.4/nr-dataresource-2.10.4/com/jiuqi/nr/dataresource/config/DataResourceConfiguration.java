/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataresource.config;

import java.util.Locale;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

@ComponentScan(value={"com.jiuqi.nr.dataresource.authority", "com.jiuqi.nr.dataresource.dao", "com.jiuqi.nr.dataresource.service.impl", "com.jiuqi.nr.dataresource.loader.impl", "com.jiuqi.nr.dataresource.util", "com.jiuqi.nr.dataresource.web", "com.jiuqi.nr.dataresource.i18n"})
@Configuration
public class DataResourceConfiguration {
    @Bean(name={"DataResourceMessageSource"})
    public MessageSource dataResourceMessageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:dataresource/messages");
        messageSource.setUseCodeAsDefaultMessage(true);
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setDefaultLocale(Locale.SIMPLIFIED_CHINESE);
        messageSource.setCacheSeconds(10);
        return messageSource;
    }
}

