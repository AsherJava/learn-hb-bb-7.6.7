/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.cache.config.CacheType
 *  com.jiuqi.np.definition.config.DefinitionCacheManagerConfiguration
 */
package com.jiuqi.nr.task.form.config;

import com.jiuqi.np.cache.config.CacheType;
import com.jiuqi.np.definition.config.DefinitionCacheManagerConfiguration;
import com.jiuqi.nr.task.form.config.FormImportCacheConfiguration;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

@Configuration
@ComponentScan(value={"com.jiuqi.nr.task.form"})
public class FormDesignConfiguration {
    @Value(value="${np.cache.type:rediscaffeine}")
    private String cacheType;

    @Bean(name={"formMessageSource"})
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageBundle = new ResourceBundleMessageSource();
        messageBundle.setUseCodeAsDefaultMessage(true);
        messageBundle.setBasenames("messages/messages", "messages/Form");
        messageBundle.setDefaultEncoding("UTF-8");
        messageBundle.setDefaultLocale(Locale.SIMPLIFIED_CHINESE);
        return messageBundle;
    }

    @Bean
    public DefinitionCacheManagerConfiguration definitionCacheManagerConfiguration() {
        return new DefinitionCacheManagerConfiguration();
    }

    @Bean
    public FormImportCacheConfiguration formImportCacheConfiguration() {
        for (CacheType value : CacheType.values()) {
            if (!value.getName().equals(this.cacheType)) continue;
            return new FormImportCacheConfiguration(value);
        }
        return new FormImportCacheConfiguration(CacheType.rediscaffeine);
    }
}

