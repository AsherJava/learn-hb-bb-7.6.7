/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.cache.NedisCacheProvider
 *  org.springframework.data.redis.connection.RedisConnectionFactory
 *  org.springframework.data.redis.listener.RedisMessageListenerContainer
 *  org.springframework.data.redis.serializer.StringRedisSerializer
 */
package com.jiuqi.nvwa.sf.adapter.spring;

import com.jiuqi.np.cache.NedisCacheProvider;
import com.jiuqi.nvwa.sf.adapter.spring.login.DefaultSFSystemIdentityService;
import com.jiuqi.nvwa.sf.adapter.spring.login.SFLoginCheckManage;
import com.jiuqi.nvwa.sf.adapter.spring.login.SFSystemUserProperties;
import com.jiuqi.nvwa.sf.models.ISFSystemIdentityService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@ComponentScan(basePackages={"com.jiuqi.nvwa.sf.adapter.spring"})
@EnableConfigurationProperties(value={SFSystemUserProperties.class})
public class RestConfig {
    @Bean
    public SFLoginCheckManage sfLoginCheckManage(NedisCacheProvider nedisCacheProvider) {
        return new SFLoginCheckManage(nedisCacheProvider);
    }

    @Bean
    public RedisMessageListenerContainer sfRedisMessageListenerContainer(RedisConnectionFactory factory) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(factory);
        return container;
    }

    @Bean
    @ConditionalOnMissingBean
    public StringRedisSerializer stringRedisSerializer() {
        return new StringRedisSerializer();
    }

    @Bean
    @ConditionalOnMissingBean(value={ISFSystemIdentityService.class})
    public DefaultSFSystemIdentityService defaultSFSystemIdentityService() {
        return new DefaultSFSystemIdentityService();
    }
}

