/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  tk.mybatis.spring.annotation.MapperScan
 */
package com.jiuqi.va.datamodel.config;

import java.util.Locale;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.scheduling.annotation.EnableScheduling;
import tk.mybatis.spring.annotation.MapperScan;

@Configuration
@EnableScheduling
@ComponentScan(basePackages={"com.jiuqi.va.datamodel"})
@MapperScan(basePackages={"com.jiuqi.va.datamodel.dao"})
public class VaDataModelCoreConfig {
    @Bean(name={"vaDataModelCoreMessageSource"})
    MessageSource messageSource() {
        ResourceBundleMessageSource messageBundle = new ResourceBundleMessageSource();
        messageBundle.setBasenames("messages/messages", "messages/VaDataModelCore");
        messageBundle.setDefaultEncoding("UTF-8");
        messageBundle.setDefaultLocale(Locale.SIMPLIFIED_CHINESE);
        return messageBundle;
    }
}

