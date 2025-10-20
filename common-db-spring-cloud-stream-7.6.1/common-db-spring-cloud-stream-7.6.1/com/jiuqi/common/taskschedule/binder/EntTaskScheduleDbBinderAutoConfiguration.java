/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.cloud.stream.binder.Binder
 *  org.springframework.cloud.stream.binder.rabbit.properties.RabbitExtendedBindingProperties
 */
package com.jiuqi.common.taskschedule.binder;

import com.jiuqi.common.taskschedule.binder.DbMessageChannelBinder;
import com.jiuqi.common.taskschedule.streamdb.db.service.EntDbTaskScheduleService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.stream.binder.Binder;
import org.springframework.cloud.stream.binder.rabbit.properties.RabbitExtendedBindingProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.Nullable;

@Configuration
@ConditionalOnMissingBean(value={Binder.class})
@EnableConfigurationProperties(value={RabbitExtendedBindingProperties.class})
public class EntTaskScheduleDbBinderAutoConfiguration {
    @Bean
    public DbMessageChannelBinder activeMQMessageChannelBinder(@Nullable EntDbTaskScheduleService service, @Nullable RabbitExtendedBindingProperties rabbitExtendedBindingProperties) {
        return new DbMessageChannelBinder(service, rabbitExtendedBindingProperties);
    }
}

