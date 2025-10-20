/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.cloud.openfeign.EnableFeignClients
 */
package com.jiuqi.va.paramsync.feign.config;

import com.jiuqi.va.paramsync.feign.client.VaParamSyncFeignClient;
import com.jiuqi.va.paramsync.feign.client.VaParamTransferClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@EnableFeignClients(clients={VaParamSyncFeignClient.class, VaParamTransferClient.class})
@PropertySource(value={"classpath:va-paramsync-feign.properties"})
public class VaParamSyncFeignClientConfig {
}

