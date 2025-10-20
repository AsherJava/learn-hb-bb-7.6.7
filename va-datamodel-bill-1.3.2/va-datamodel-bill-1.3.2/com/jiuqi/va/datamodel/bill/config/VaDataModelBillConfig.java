/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.datamodel.bill.config;

import java.util.Locale;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

@Configuration
@ComponentScan(basePackages={"com.jiuqi.va.datamodel.bill"})
public class VaDataModelBillConfig {
    @Bean(name={"vaDataModelBillMessageSource"})
    MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageBundle = new ReloadableResourceBundleMessageSource();
        messageBundle.setUseCodeAsDefaultMessage(true);
        messageBundle.setBasenames("classpath:messages/messages", "classpath:messages/VaDataModelBill");
        messageBundle.setDefaultEncoding("UTF-8");
        messageBundle.setDefaultLocale(Locale.SIMPLIFIED_CHINESE);
        return messageBundle;
    }
}

