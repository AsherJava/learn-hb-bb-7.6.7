/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.query;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

@Configuration
@ComponentScan(basePackages={"com.jiuqi.va.query.**"})
@EntityScan(value={"com.jiuqi.va.query"})
public class UserDefinedAutoConfiguration {
    @Bean(name={"vaQueryMessageSource"})
    public ReloadableResourceBundleMessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageBundle = new ReloadableResourceBundleMessageSource();
        messageBundle.setUseCodeAsDefaultMessage(true);
        messageBundle.setBasename("classpath:messages/queryCore/messages");
        messageBundle.setDefaultEncoding("UTF-8");
        return messageBundle;
    }
}

