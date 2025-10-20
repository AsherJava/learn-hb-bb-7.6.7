/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.authz.licence.MachineCodeGenerator$MachineInfo
 *  com.jiuqi.bi.core.nodekeeper.DistributionManager
 *  com.jiuqi.bi.core.nodekeeper.ServiceNodeStateHolder
 *  com.netflix.appinfo.EurekaInstanceConfig
 *  org.springframework.cloud.client.ConditionalOnDiscoveryEnabled
 *  org.springframework.cloud.netflix.eureka.EurekaInstanceConfigBean
 */
package com.jiuqi.nvwa.sf.adapter.spring.naming.eureka;

import com.jiuqi.bi.authz.licence.MachineCodeGenerator;
import com.jiuqi.bi.core.nodekeeper.DistributionManager;
import com.jiuqi.bi.core.nodekeeper.ServiceNodeStateHolder;
import com.jiuqi.nvwa.sf.Framework;
import com.jiuqi.nvwa.sf.adapter.spring.naming.eureka.EurekaCustomProperties;
import com.jiuqi.nvwa.sf.adapter.spring.naming.eureka.EurekaMetaModuleStatePublisher;
import com.jiuqi.nvwa.sf.operator.FrameworkOperator;
import com.netflix.appinfo.EurekaInstanceConfig;
import java.util.Map;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.SearchStrategy;
import org.springframework.cloud.client.ConditionalOnDiscoveryEnabled;
import org.springframework.cloud.netflix.eureka.EurekaInstanceConfigBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnDiscoveryEnabled
@ConditionalOnProperty(value={"eureka.client.service-url.defaultZone"})
public class NvwaEurekaConfig {
    @Bean
    @ConditionalOnClass(name={"org.springframework.cloud.netflix.eureka.EurekaInstanceConfigBean"})
    @ConditionalOnMissingBean(value={EurekaInstanceConfig.class}, search=SearchStrategy.CURRENT)
    public EurekaCustomProperties eurekaCustomProperties(EurekaInstanceConfigBean eurekaInstanceConfigBean, ApplicationContext context) {
        Map metadata = eurekaInstanceConfigBean.getMetadataMap();
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
        return new EurekaCustomProperties();
    }

    @Bean
    @ConditionalOnClass(value={EurekaCustomProperties.class})
    public EurekaMetaModuleStatePublisher eurekaMetaModuleStatePublisher() {
        return new EurekaMetaModuleStatePublisher();
    }
}

