/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.alibaba.cloud.nacos.NacosDiscoveryProperties
 *  com.alibaba.cloud.nacos.registry.NacosAutoServiceRegistration
 *  com.jiuqi.bi.authz.licence.MachineCodeGenerator$MachineInfo
 *  com.jiuqi.bi.core.nodekeeper.DistributionManager
 *  com.jiuqi.bi.core.nodekeeper.ServiceNodeStateHolder
 *  org.springframework.cloud.client.ConditionalOnDiscoveryEnabled
 */
package com.jiuqi.nvwa.sf.adapter.spring.naming.nacos;

import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import com.alibaba.cloud.nacos.registry.NacosAutoServiceRegistration;
import com.jiuqi.bi.authz.licence.MachineCodeGenerator;
import com.jiuqi.bi.core.nodekeeper.DistributionManager;
import com.jiuqi.bi.core.nodekeeper.ServiceNodeStateHolder;
import com.jiuqi.nvwa.sf.Framework;
import com.jiuqi.nvwa.sf.adapter.spring.naming.nacos.NacosConfig;
import com.jiuqi.nvwa.sf.adapter.spring.naming.nacos.NacosCustomProperties;
import com.jiuqi.nvwa.sf.adapter.spring.naming.nacos.NacosMetaModuleStatePublisher;
import com.jiuqi.nvwa.sf.operator.FrameworkOperator;
import java.util.Map;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.client.ConditionalOnDiscoveryEnabled;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnDiscoveryEnabled
@ConditionalOnProperty(value={"spring.cloud.nacos.discovery.server-addr"})
public class NvwaNacosConfig {
    @Bean
    @ConditionalOnClass(name={"com.alibaba.cloud.nacos.registry.NacosAutoServiceRegistration"})
    @ConfigurationProperties(value="spring.cloud.service-registry.auto-registration")
    public NacosConfig nacosConfig(NacosAutoServiceRegistration nacosAutoServiceRegistration) {
        return new NacosConfig(nacosAutoServiceRegistration);
    }

    @Bean
    @ConditionalOnClass(name={"com.alibaba.cloud.nacos.NacosDiscoveryProperties"})
    public NacosCustomProperties nacosCustomProperties(NacosDiscoveryProperties nacosProperties, ApplicationContext context) {
        Map metadata = nacosProperties.getMetadata();
        metadata.put("context-path", context.getEnvironment().getProperty("server.servlet.context-path", "/"));
        metadata.put("machine-name", DistributionManager.getInstance().getMachineName());
        metadata.put("module-state", ServiceNodeStateHolder.getState().name());
        try {
            Framework.getInstance().loadProductFile();
            MachineCodeGenerator.MachineInfo machineInfo = FrameworkOperator.getMachineCode(Framework.getInstance().getProductId());
            metadata.put("machine-code", machineInfo.getMachineCode());
        }
        catch (Exception e) {
            LoggerFactory.getLogger(this.getClass()).error("\u673a\u5668\u7801\u83b7\u53d6\u5931\u8d25\uff1a" + e.getMessage(), e);
        }
        return new NacosCustomProperties();
    }

    @Bean
    @ConditionalOnClass(value={NacosCustomProperties.class})
    @ConfigurationProperties(value="spring.cloud.nacos.discovery")
    public NacosMetaModuleStatePublisher nacosMetaModuleStatePublisher(NacosDiscoveryProperties nacosProperties, ApplicationEventPublisher applicationEventPublisher) {
        return new NacosMetaModuleStatePublisher(applicationEventPublisher, nacosProperties);
    }
}

