/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.pool2.impl.GenericObjectPool
 *  org.apache.commons.pool2.impl.GenericObjectPoolConfig
 *  org.springframework.data.redis.connection.RedisClusterConfiguration
 *  org.springframework.data.redis.connection.RedisConnectionFactory
 *  org.springframework.data.redis.connection.RedisSentinelConfiguration
 *  org.springframework.data.redis.connection.RedisStandaloneConfiguration
 *  org.springframework.data.redis.connection.jedis.JedisClientConfiguration
 *  org.springframework.data.redis.connection.jedis.JedisClientConfiguration$JedisClientConfigurationBuilder
 *  org.springframework.data.redis.connection.jedis.JedisConnection
 *  org.springframework.data.redis.connection.jedis.JedisConnectionFactory
 *  redis.clients.jedis.Jedis
 *  redis.clients.jedis.JedisPoolConfig
 */
package com.jiuqi.va.shiro.config.redis;

import com.jiuqi.va.shiro.config.redis.MyRedisProperties;
import com.jiuqi.va.shiro.config.redis.MyReidsClientConfig;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.PropertyMapper;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnection;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.util.StringUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;

@Configuration(proxyBeanMethods=false)
@ConditionalOnClass(value={GenericObjectPool.class, JedisConnection.class, Jedis.class})
@ConditionalOnProperty(name={"va-auth-shiro.redis.client-type"}, havingValue="jedis", matchIfMissing=false)
class MyRedisJedisConfig
extends MyReidsClientConfig {
    MyRedisJedisConfig(MyRedisProperties properties, ObjectProvider<RedisStandaloneConfiguration> standaloneConfigurationProvider, ObjectProvider<RedisSentinelConfiguration> sentinelConfiguration, ObjectProvider<RedisClusterConfiguration> clusterConfiguration) {
        super(properties, standaloneConfigurationProvider, sentinelConfiguration, clusterConfiguration);
    }

    @Override
    public RedisConnectionFactory getConnectionFactory() {
        JedisConnectionFactory jcf = this.createJedisConnectionFactory();
        jcf.afterPropertiesSet();
        return jcf;
    }

    private JedisConnectionFactory createJedisConnectionFactory() {
        JedisClientConfiguration clientConfiguration = this.getJedisClientConfiguration();
        if (this.getSentinelConfig() != null) {
            return new JedisConnectionFactory(this.getSentinelConfig(), clientConfiguration);
        }
        if (this.getClusterConfiguration() != null) {
            return new JedisConnectionFactory(this.getClusterConfiguration(), clientConfiguration);
        }
        return new JedisConnectionFactory(this.getStandaloneConfig(), clientConfiguration);
    }

    private JedisClientConfiguration getJedisClientConfiguration() {
        JedisClientConfiguration.JedisClientConfigurationBuilder builder = this.applyProperties(JedisClientConfiguration.builder());
        MyRedisProperties.Pool pool = this.getProperties().getJedis().getPool();
        if (this.isPoolEnabled(pool)) {
            this.applyPooling(pool, builder);
        }
        if (StringUtils.hasText(this.getProperties().getUrl())) {
            this.customizeConfigurationFromUrl(builder);
        }
        return builder.build();
    }

    private JedisClientConfiguration.JedisClientConfigurationBuilder applyProperties(JedisClientConfiguration.JedisClientConfigurationBuilder builder) {
        PropertyMapper map = PropertyMapper.get().alwaysApplyingWhenNonNull();
        map.from(this.getProperties().isSsl()).whenTrue().toCall(() -> ((JedisClientConfiguration.JedisClientConfigurationBuilder)builder).useSsl());
        map.from(this.getProperties().getTimeout()).to(arg_0 -> ((JedisClientConfiguration.JedisClientConfigurationBuilder)builder).readTimeout(arg_0));
        map.from(this.getProperties().getConnectTimeout()).to(arg_0 -> ((JedisClientConfiguration.JedisClientConfigurationBuilder)builder).connectTimeout(arg_0));
        map.from(this.getProperties().getClientName()).whenHasText().to(arg_0 -> ((JedisClientConfiguration.JedisClientConfigurationBuilder)builder).clientName(arg_0));
        return builder;
    }

    private void applyPooling(MyRedisProperties.Pool pool, JedisClientConfiguration.JedisClientConfigurationBuilder builder) {
        builder.usePooling().poolConfig((GenericObjectPoolConfig)this.jedisPoolConfig(pool));
    }

    private JedisPoolConfig jedisPoolConfig(MyRedisProperties.Pool pool) {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(pool.getMaxActive());
        config.setMaxIdle(pool.getMaxIdle());
        config.setMinIdle(pool.getMinIdle());
        if (pool.getTimeBetweenEvictionRuns() != null) {
            config.setTimeBetweenEvictionRuns(pool.getTimeBetweenEvictionRuns());
        }
        if (pool.getMaxWait() != null) {
            config.setMaxWait(pool.getMaxWait());
        }
        return config;
    }

    private void customizeConfigurationFromUrl(JedisClientConfiguration.JedisClientConfigurationBuilder builder) {
        MyReidsClientConfig.ConnectionInfo connectionInfo = this.parseUrl(this.getProperties().getUrl());
        if (connectionInfo.isUseSsl()) {
            builder.useSsl();
        }
    }
}

