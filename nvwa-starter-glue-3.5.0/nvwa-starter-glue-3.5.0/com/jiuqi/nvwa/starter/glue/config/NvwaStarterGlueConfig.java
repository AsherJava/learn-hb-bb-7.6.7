/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.glue.common.Log4jMDCUtil
 *  org.springframework.integration.config.GlobalChannelInterceptor
 *  org.springframework.messaging.Message
 *  org.springframework.messaging.MessageChannel
 *  org.springframework.messaging.support.ChannelInterceptor
 */
package com.jiuqi.nvwa.starter.glue.config;

import com.jiuqi.nvwa.glue.common.Log4jMDCUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.config.GlobalChannelInterceptor;
import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.ChannelInterceptor;

@Configuration
@ComponentScan(basePackages={"com.jiuqi.nvwa.starter.glue.**"})
public class NvwaStarterGlueConfig {
    Logger logger = LoggerFactory.getLogger(NvwaStarterGlueConfig.class);

    @Bean
    @GlobalChannelInterceptor(patterns={"GLUE_DATA"})
    public ChannelInterceptor glueProductorInterceptor() {
        return new ChannelInterceptor(){

            public Message<?> preSend(Message<?> message, MessageChannel channel) {
                Log4jMDCUtil.bindTraceId((String)((String)message.getHeaders().get((Object)"X-NVWA-TRACE-ID", String.class)), (boolean)true);
                NvwaStarterGlueConfig.this.logger.info(message.getHeaders().toString());
                return message;
            }
        };
    }

    @Bean
    @GlobalChannelInterceptor(patterns={"glueConsumer-*", "GLUE_DATA*"})
    public ChannelInterceptor glueCustomerInterceptor() {
        return new ChannelInterceptor(){

            public boolean preReceive(MessageChannel channel) {
                NvwaStarterGlueConfig.this.logger.debug("==========\u6d88\u8d39\u524d\u6267\u884c=======");
                return true;
            }

            public void afterReceiveCompletion(@Nullable Message<?> message, MessageChannel channel, @Nullable Exception ex) {
                NvwaStarterGlueConfig.this.logger.debug("==========\u6d88\u8d39\u5b8c\u6267\u884c=======");
            }
        };
    }
}

