/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.cloud.client.ConditionalOnDiscoveryEnabled
 *  org.springframework.cloud.client.discovery.EnableDiscoveryClient
 */
package com.jiuqi.budget.autoconfigure;

import org.springframework.cloud.client.ConditionalOnDiscoveryEnabled;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Configuration;

@Configuration(value="BudDiscoveryConfig")
@ConditionalOnDiscoveryEnabled
@EnableDiscoveryClient
public class BudDiscoveryConfig {
}

