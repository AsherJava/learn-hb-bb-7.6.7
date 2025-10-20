/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.cloud.client.serviceregistry.AutoServiceRegistrationAutoConfiguration
 *  org.springframework.cloud.client.serviceregistry.AutoServiceRegistrationConfiguration
 *  org.springframework.cloud.client.serviceregistry.AutoServiceRegistrationProperties
 */
package com.jiuqi.nvwa.sf.adapter.spring.naming.register;

import com.jiuqi.nvwa.sf.adapter.spring.naming.ConditionalOnNodeKeeperDiscoveryEnabled;
import com.jiuqi.nvwa.sf.adapter.spring.naming.NodeKeeperDiscoveryAutoConfiguration;
import com.jiuqi.nvwa.sf.adapter.spring.naming.register.NodeKeeperAutoServiceRegistration;
import com.jiuqi.nvwa.sf.adapter.spring.naming.register.NodeKeeperConfig;
import com.jiuqi.nvwa.sf.adapter.spring.naming.register.NodeKeeperRegistration;
import com.jiuqi.nvwa.sf.adapter.spring.naming.register.NodeKeeperServiceRegistry;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.serviceregistry.AutoServiceRegistrationAutoConfiguration;
import org.springframework.cloud.client.serviceregistry.AutoServiceRegistrationConfiguration;
import org.springframework.cloud.client.serviceregistry.AutoServiceRegistrationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods=false)
@EnableConfigurationProperties
@ConditionalOnNodeKeeperDiscoveryEnabled
@ConditionalOnProperty(value={"spring.cloud.service-registry.auto-registration.enabled"}, matchIfMissing=true)
@AutoConfigureAfter(value={AutoServiceRegistrationConfiguration.class, AutoServiceRegistrationAutoConfiguration.class, NodeKeeperDiscoveryAutoConfiguration.class})
public class NodeKeeperRegistryAutoConfiguration {
    @Bean
    public NodeKeeperServiceRegistry nodeKeeperServiceRegistry() {
        return new NodeKeeperServiceRegistry();
    }

    @Bean
    public NodeKeeperRegistration nodeKeeperRegistration(ApplicationContext context) {
        return new NodeKeeperRegistration(context);
    }

    @Bean
    public NodeKeeperAutoServiceRegistration nodeKeeperAutoServiceRegistration(NodeKeeperServiceRegistry registry, AutoServiceRegistrationProperties properties, NodeKeeperRegistration registration) {
        return new NodeKeeperAutoServiceRegistration(registry, properties, registration);
    }

    @Bean
    public NodeKeeperConfig nodeKeeperConfig(NodeKeeperAutoServiceRegistration nodeKeeperAutoServiceRegistration) {
        return new NodeKeeperConfig(nodeKeeperAutoServiceRegistration);
    }
}

