/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.cloud.openfeign.EnableFeignClients
 */
package com.jiuqi.va.query;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@EnableFeignClients(value={"com.jiuqi.va.query"})
@PropertySource(value={"classpath:va-query-feign.properties"})
@ComponentScan(basePackages={"com.jiuqi.va.query"})
public class VaQueryFeignConfig {
}

