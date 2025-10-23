/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dafafill.config;

import java.util.Locale;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

@Configuration
@ComponentScan(value={"com.jiuqi.nr.dafafill"})
public class DataFillConfig {
    @Bean(name={"DataFillMessageSource"})
    public MessageSource dataFillMessageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:datafill/messages");
        messageSource.setUseCodeAsDefaultMessage(true);
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setDefaultLocale(Locale.SIMPLIFIED_CHINESE);
        messageSource.setCacheSeconds(10);
        return messageSource;
    }
}

