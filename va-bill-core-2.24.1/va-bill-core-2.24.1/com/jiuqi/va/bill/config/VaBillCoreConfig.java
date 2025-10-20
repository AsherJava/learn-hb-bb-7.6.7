/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.github.benmanes.caffeine.cache.Cache
 *  com.github.benmanes.caffeine.cache.Caffeine
 *  com.jiuqi.va.domain.common.EnvConfig
 *  org.springframework.http.MediaType
 *  org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
 *  org.springframework.web.client.RestTemplate
 *  tk.mybatis.spring.annotation.MapperScan
 */
package com.jiuqi.va.bill.config;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.jiuqi.va.domain.common.EnvConfig;
import java.util.ArrayList;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import tk.mybatis.spring.annotation.MapperScan;

@Configuration
@ComponentScan(basePackages={"com.jiuqi.va.bill"})
@MapperScan(basePackages={"com.jiuqi.va.bill.dao"})
@PropertySource(value={"classpath:va-bizAttachmentServer-feign.properties", "classpath:va-bill-core-feign.properties"})
public class VaBillCoreConfig {
    private static boolean redisEnable = true;

    @Bean(name={"feedbackRestTemplate"})
    @ConditionalOnMissingBean(name={"feedbackRestTemplate"})
    public RestTemplate restTemplate() {
        RestTemplate feedbackRestTemplate = new RestTemplate();
        feedbackRestTemplate.getMessageConverters().add(new WxMappingJackson2HttpMessageConverter());
        return feedbackRestTemplate;
    }

    @Bean(name={"billCoreMessageSource"})
    public ReloadableResourceBundleMessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageBundle = new ReloadableResourceBundleMessageSource();
        messageBundle.setUseCodeAsDefaultMessage(true);
        messageBundle.setBasenames("classpath:messages/messages", "classpath:messages/VaBillCore");
        messageBundle.setDefaultEncoding("UTF-8");
        messageBundle.setDefaultLocale(Locale.SIMPLIFIED_CHINESE);
        return messageBundle;
    }

    @Bean(name={"billCaffeineCache"})
    public Cache<String, Object> caffeineCache() {
        return Caffeine.newBuilder().expireAfterAccess(3L, TimeUnit.MINUTES).initialCapacity(1).build();
    }

    @Autowired
    public void setRedisEnable(Environment environment) {
        redisEnable = EnvConfig.getRedisEnable((Environment)environment);
    }

    public static boolean isRedisEnable() {
        return redisEnable;
    }

    public class WxMappingJackson2HttpMessageConverter
    extends MappingJackson2HttpMessageConverter {
        public WxMappingJackson2HttpMessageConverter() {
            ArrayList<MediaType> mediaTypes = new ArrayList<MediaType>();
            mediaTypes.add(MediaType.APPLICATION_JSON);
            mediaTypes.add(MediaType.APPLICATION_OCTET_STREAM);
            mediaTypes.add(MediaType.TEXT_HTML);
            mediaTypes.add(MediaType.TEXT_PLAIN);
            mediaTypes.add(MediaType.TEXT_XML);
            mediaTypes.add(MediaType.APPLICATION_STREAM_JSON);
            mediaTypes.add(MediaType.APPLICATION_ATOM_XML);
            mediaTypes.add(MediaType.APPLICATION_FORM_URLENCODED);
            mediaTypes.add(MediaType.APPLICATION_PDF);
            this.setSupportedMediaTypes(mediaTypes);
        }
    }
}

