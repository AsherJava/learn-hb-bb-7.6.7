/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.lettuce.core.ClientOptions
 *  io.lettuce.core.ClientOptions$Builder
 *  io.lettuce.core.RedisClient
 *  io.lettuce.core.SocketOptions
 *  io.lettuce.core.TimeoutOptions
 *  io.lettuce.core.cluster.ClusterClientOptions
 *  io.lettuce.core.cluster.ClusterClientOptions$Builder
 *  io.lettuce.core.cluster.ClusterTopologyRefreshOptions
 *  io.lettuce.core.cluster.ClusterTopologyRefreshOptions$Builder
 *  io.lettuce.core.resource.ClientResources
 *  org.apache.commons.pool2.impl.GenericObjectPoolConfig
 *  org.springframework.data.redis.connection.RedisClusterConfiguration
 *  org.springframework.data.redis.connection.RedisConnectionFactory
 *  org.springframework.data.redis.connection.RedisSentinelConfiguration
 *  org.springframework.data.redis.connection.RedisStandaloneConfiguration
 *  org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration
 *  org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration$LettuceClientConfigurationBuilder
 *  org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
 *  org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration
 */
package com.jiuqi.va.shiro.config.redis;

import com.jiuqi.va.shiro.config.redis.MyRedisProperties;
import com.jiuqi.va.shiro.config.redis.MyReidsClientConfig;
import io.lettuce.core.ClientOptions;
import io.lettuce.core.RedisClient;
import io.lettuce.core.SocketOptions;
import io.lettuce.core.TimeoutOptions;
import io.lettuce.core.cluster.ClusterClientOptions;
import io.lettuce.core.cluster.ClusterTopologyRefreshOptions;
import io.lettuce.core.resource.ClientResources;
import java.time.Duration;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.util.StringUtils;

@Configuration(proxyBeanMethods=false)
@ConditionalOnClass(value={RedisClient.class})
@ConditionalOnProperty(name={"va-auth-shiro.redis.client-type"}, havingValue="lettuce", matchIfMissing=false)
class MyRedisLettuceConfig
extends MyReidsClientConfig {
    @Autowired
    private ClientResources clientResources;

    MyRedisLettuceConfig(MyRedisProperties properties, ObjectProvider<RedisStandaloneConfiguration> standaloneConfigurationProvider, ObjectProvider<RedisSentinelConfiguration> sentinelConfigurationProvider, ObjectProvider<RedisClusterConfiguration> clusterConfigurationProvider) {
        super(properties, standaloneConfigurationProvider, sentinelConfigurationProvider, clusterConfigurationProvider);
    }

    @Override
    public RedisConnectionFactory getConnectionFactory() {
        LettuceClientConfiguration clientConfig = this.getLettuceClientConfiguration(this.clientResources, this.getProperties().getLettuce().getPool());
        LettuceConnectionFactory lcf = this.createLettuceConnectionFactory(clientConfig);
        lcf.afterPropertiesSet();
        return lcf;
    }

    private LettuceConnectionFactory createLettuceConnectionFactory(LettuceClientConfiguration clientConfiguration) {
        if (this.getSentinelConfig() != null) {
            return new LettuceConnectionFactory(this.getSentinelConfig(), clientConfiguration);
        }
        if (this.getClusterConfiguration() != null) {
            return new LettuceConnectionFactory(this.getClusterConfiguration(), clientConfiguration);
        }
        return new LettuceConnectionFactory(this.getStandaloneConfig(), clientConfiguration);
    }

    private LettuceClientConfiguration getLettuceClientConfiguration(ClientResources clientResources, MyRedisProperties.Pool pool) {
        LettuceClientConfiguration.LettuceClientConfigurationBuilder builder = this.createBuilder(pool);
        this.applyProperties(builder);
        if (StringUtils.hasText(this.getProperties().getUrl())) {
            this.customizeConfigurationFromUrl(builder);
        }
        builder.clientOptions(this.createClientOptions());
        builder.clientResources(clientResources);
        return builder.build();
    }

    private LettuceClientConfiguration.LettuceClientConfigurationBuilder createBuilder(MyRedisProperties.Pool pool) {
        if (this.isPoolEnabled(pool)) {
            return new PoolBuilderFactory().createBuilder(pool);
        }
        return LettuceClientConfiguration.builder();
    }

    private LettuceClientConfiguration.LettuceClientConfigurationBuilder applyProperties(LettuceClientConfiguration.LettuceClientConfigurationBuilder builder) {
        MyRedisProperties.Lettuce lettuce;
        if (this.getProperties().isSsl()) {
            builder.useSsl();
        }
        if (this.getProperties().getTimeout() != null) {
            builder.commandTimeout(this.getProperties().getTimeout());
        }
        if (this.getProperties().getLettuce() != null && (lettuce = this.getProperties().getLettuce()).getShutdownTimeout() != null && !lettuce.getShutdownTimeout().isZero()) {
            builder.shutdownTimeout(this.getProperties().getLettuce().getShutdownTimeout());
        }
        if (StringUtils.hasText(this.getProperties().getClientName())) {
            builder.clientName(this.getProperties().getClientName());
        }
        return builder;
    }

    private ClientOptions createClientOptions() {
        ClientOptions.Builder builder = this.initializeClientOptionsBuilder();
        Duration connectTimeout = this.getProperties().getConnectTimeout();
        if (connectTimeout != null) {
            builder.socketOptions(SocketOptions.builder().connectTimeout(connectTimeout).build());
        }
        return builder.timeoutOptions(TimeoutOptions.enabled()).build();
    }

    private ClientOptions.Builder initializeClientOptionsBuilder() {
        if (this.getProperties().getCluster() != null) {
            ClusterClientOptions.Builder builder = ClusterClientOptions.builder();
            MyRedisProperties.Lettuce.Cluster.Refresh refreshProperties = this.getProperties().getLettuce().getCluster().getRefresh();
            ClusterTopologyRefreshOptions.Builder refreshBuilder = ClusterTopologyRefreshOptions.builder().dynamicRefreshSources(refreshProperties.isDynamicRefreshSources());
            if (refreshProperties.getPeriod() != null) {
                refreshBuilder.enablePeriodicRefresh(refreshProperties.getPeriod());
            }
            if (refreshProperties.isAdaptive()) {
                refreshBuilder.enableAllAdaptiveRefreshTriggers();
            }
            return builder.topologyRefreshOptions(refreshBuilder.build());
        }
        return ClientOptions.builder();
    }

    private void customizeConfigurationFromUrl(LettuceClientConfiguration.LettuceClientConfigurationBuilder builder) {
        MyReidsClientConfig.ConnectionInfo connectionInfo = this.parseUrl(this.getProperties().getUrl());
        if (connectionInfo.isUseSsl()) {
            builder.useSsl();
        }
    }

    private static class PoolBuilderFactory {
        private PoolBuilderFactory() {
        }

        LettuceClientConfiguration.LettuceClientConfigurationBuilder createBuilder(MyRedisProperties.Pool properties) {
            return LettucePoolingClientConfiguration.builder().poolConfig(this.getPoolConfig(properties));
        }

        private GenericObjectPoolConfig<?> getPoolConfig(MyRedisProperties.Pool properties) {
            GenericObjectPoolConfig config = new GenericObjectPoolConfig();
            config.setMaxTotal(properties.getMaxActive());
            config.setMaxIdle(properties.getMaxIdle());
            config.setMinIdle(properties.getMinIdle());
            if (properties.getTimeBetweenEvictionRuns() != null) {
                config.setTimeBetweenEvictionRuns(properties.getTimeBetweenEvictionRuns());
            }
            if (properties.getMaxWait() != null) {
                config.setMaxWait(properties.getMaxWait());
            }
            return config;
        }
    }
}

