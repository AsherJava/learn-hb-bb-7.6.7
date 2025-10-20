/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.cache.NedisCacheProvider
 */
package com.jiuqi.nvwa.sf.adapter.spring.naming;

import com.jiuqi.np.cache.NedisCacheProvider;
import com.jiuqi.nvwa.sf.adapter.spring.naming.ConditionalOnNodeKeeperDiscoveryEnabled;
import com.jiuqi.nvwa.sf.adapter.spring.naming.NodeKeeperDiscovery;
import com.jiuqi.nvwa.sf.adapter.spring.naming.cache.NodeKeepCacheManagerConfiguration;
import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnNodeKeeperDiscoveryEnabled
public class NodeKeeperDiscoveryAutoConfiguration {
    @Bean
    public NodeKeepCacheManagerConfiguration nodeKeepCacheManagerConfiguration() {
        return new NodeKeepCacheManagerConfiguration();
    }

    @Bean
    public NodeKeeperDiscovery nodeKeeperDiscovery(DataSource dataSource, NedisCacheProvider cacheProvider) {
        return new NodeKeeperDiscovery(dataSource, cacheProvider);
    }
}

