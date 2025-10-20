/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.cloud.client.CommonsClientAutoConfiguration
 *  org.springframework.cloud.client.ConditionalOnBlockingDiscoveryEnabled
 *  org.springframework.cloud.client.discovery.simple.SimpleDiscoveryClientAutoConfiguration
 */
package com.jiuqi.nvwa.sf.adapter.spring.naming;

import com.jiuqi.nvwa.sf.adapter.spring.naming.ConditionalOnNodeKeeperDiscoveryEnabled;
import com.jiuqi.nvwa.sf.adapter.spring.naming.NodeKeeperDiscovery;
import com.jiuqi.nvwa.sf.adapter.spring.naming.NodeKeeperDiscoveryAutoConfiguration;
import com.jiuqi.nvwa.sf.adapter.spring.naming.NodeKeeperDiscoveryClient;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.CommonsClientAutoConfiguration;
import org.springframework.cloud.client.ConditionalOnBlockingDiscoveryEnabled;
import org.springframework.cloud.client.discovery.simple.SimpleDiscoveryClientAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
@ConditionalOnBlockingDiscoveryEnabled
@ConditionalOnNodeKeeperDiscoveryEnabled
@AutoConfigureBefore(value={SimpleDiscoveryClientAutoConfiguration.class, CommonsClientAutoConfiguration.class})
@AutoConfigureAfter(value={NodeKeeperDiscoveryAutoConfiguration.class})
@ComponentScan(basePackages={"com.jiuqi.nvwa.sf.adapter.spring.naming"})
public class NodeKeeperDiscoveryClientConfiguration {
    @Bean
    public NodeKeeperDiscoveryClient nodeKeeperDiscoveryClient(NodeKeeperDiscovery nodeKeeperDiscovery) {
        return new NodeKeeperDiscoveryClient(nodeKeeperDiscovery);
    }
}

