/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.core.config;

import com.jiuqi.np.core.application.NpApplication;
import com.jiuqi.np.core.application.NpApplicationProperties;
import com.jiuqi.np.core.application.NpApplicationSqlHelp;
import com.jiuqi.np.core.application.NvwaRestApiProperties;
import com.jiuqi.np.core.application.impl.DefaultNpApplication;
import com.jiuqi.np.core.application.impl.NpApplicationSqlHelpImpl;
import com.jiuqi.np.core.application.impl.NpTaskExecutor;
import com.jiuqi.np.core.application.impl.NpUserNpContextForUserProviderImpl;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@Configuration
@EnableConfigurationProperties(value={NpApplicationProperties.class, NvwaRestApiProperties.class})
public class CoreAutoConfiguration {
    @Bean
    public NpApplication npApplication() {
        return new DefaultNpApplication();
    }

    @Bean
    public NpTaskExecutor npTaskExecutor() {
        return new NpTaskExecutor();
    }

    @Bean
    @Lazy(value=false)
    public SpringBeanUtils springBeanUtils() {
        return new SpringBeanUtils();
    }

    @Bean
    public NpApplicationSqlHelp npApplicationSqlHelp() {
        return new NpApplicationSqlHelpImpl();
    }

    @Bean
    @ConditionalOnClass(name={"com.jiuqi.np.user.feign.client.NvwaUserClient"})
    public NpUserNpContextForUserProviderImpl npUserNpContextForUserProvider() {
        return new NpUserNpContextForUserProviderImpl();
    }
}

