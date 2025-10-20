/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.cloud.client.ConditionalOnDiscoveryEnabled
 *  org.springframework.cloud.client.ConditionalOnReactiveDiscoveryEnabled
 *  org.springframework.cloud.client.ReactiveCommonsClientAutoConfiguration
 *  org.springframework.cloud.client.discovery.composite.reactive.ReactiveCompositeDiscoveryClientAutoConfiguration
 */
package com.jiuqi.nvwa.sf.adapter.spring.naming.reactive;

import com.jiuqi.nvwa.sf.adapter.spring.naming.ConditionalOnNodeKeeperDiscoveryEnabled;
import com.jiuqi.nvwa.sf.adapter.spring.naming.NodeKeeperDiscovery;
import com.jiuqi.nvwa.sf.adapter.spring.naming.NodeKeeperDiscoveryAutoConfiguration;
import com.jiuqi.nvwa.sf.adapter.spring.naming.reactive.NodeKeeperReactiveDiscoveryClient;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.client.ConditionalOnDiscoveryEnabled;
import org.springframework.cloud.client.ConditionalOnReactiveDiscoveryEnabled;
import org.springframework.cloud.client.ReactiveCommonsClientAutoConfiguration;
import org.springframework.cloud.client.discovery.composite.reactive.ReactiveCompositeDiscoveryClientAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods=false)
@ConditionalOnDiscoveryEnabled
@ConditionalOnReactiveDiscoveryEnabled
@ConditionalOnNodeKeeperDiscoveryEnabled
@AutoConfigureAfter(value={NodeKeeperDiscoveryAutoConfiguration.class, ReactiveCompositeDiscoveryClientAutoConfiguration.class})
@AutoConfigureBefore(value={ReactiveCommonsClientAutoConfiguration.class})
@ComponentScan(basePackages={"com.jiuqi.nvwa.sf.adapter.spring.naming"})
public class NodeKeeperReactiveDiscoveryClientConfiguration {
    @Bean
    @ConditionalOnMissingBean
    public NodeKeeperReactiveDiscoveryClient nodeKeeperReactiveDiscoveryClient(NodeKeeperDiscovery nodeKeeperDiscovery) {
        return new NodeKeeperReactiveDiscoveryClient(nodeKeeperDiscovery);
    }
}

