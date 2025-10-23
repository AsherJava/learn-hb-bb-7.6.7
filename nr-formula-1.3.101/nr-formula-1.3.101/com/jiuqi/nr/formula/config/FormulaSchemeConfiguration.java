/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.formula.config;

import java.util.Locale;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

@Configuration
@ComponentScan(value={"com.jiuqi.nr.formula"})
public class FormulaSchemeConfiguration {
    @Bean(name={"formulaMessageSource"})
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageBundle = new ResourceBundleMessageSource();
        messageBundle.setUseCodeAsDefaultMessage(true);
        messageBundle.setBasenames("messages/messages", "messages/Formula");
        messageBundle.setDefaultEncoding("UTF-8");
        messageBundle.setDefaultLocale(Locale.SIMPLIFIED_CHINESE);
        return messageBundle;
    }
}

