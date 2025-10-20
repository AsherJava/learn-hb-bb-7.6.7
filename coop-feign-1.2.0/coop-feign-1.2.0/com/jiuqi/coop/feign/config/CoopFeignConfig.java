/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.cloud.openfeign.EnableFeignClients
 */
package com.jiuqi.coop.feign.config;

import com.jiuqi.coop.feign.client.BillCastFeignClient;
import com.jiuqi.coop.feign.client.ExternalClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@EnableFeignClients(clients={BillCastFeignClient.class, ExternalClient.class})
@ComponentScan(basePackages={"com.jiuqi.coop"})
@PropertySource(value={"classpath:coop-feign.properties"})
public class CoopFeignConfig {
}

