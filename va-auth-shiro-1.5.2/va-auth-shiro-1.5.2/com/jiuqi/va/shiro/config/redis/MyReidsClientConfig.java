/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.data.redis.connection.RedisClusterConfiguration
 *  org.springframework.data.redis.connection.RedisConnectionFactory
 *  org.springframework.data.redis.connection.RedisNode
 *  org.springframework.data.redis.connection.RedisPassword
 *  org.springframework.data.redis.connection.RedisSentinelConfiguration
 *  org.springframework.data.redis.connection.RedisStandaloneConfiguration
 */
package com.jiuqi.va.shiro.config.redis;

import com.jiuqi.va.shiro.config.redis.MyRedisProperties;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

public abstract class MyReidsClientConfig {
    private static final boolean COMMONS_POOL2_AVAILABLE = ClassUtils.isPresent("org.apache.commons.pool2.ObjectPool", MyReidsClientConfig.class.getClassLoader());
    private final MyRedisProperties properties;
    private final RedisStandaloneConfiguration standaloneConfiguration;
    private final RedisSentinelConfiguration sentinelConfiguration;
    private final RedisClusterConfiguration clusterConfiguration;

    protected MyReidsClientConfig(MyRedisProperties properties, ObjectProvider<RedisStandaloneConfiguration> standaloneConfigurationProvider, ObjectProvider<RedisSentinelConfiguration> sentinelConfigurationProvider, ObjectProvider<RedisClusterConfiguration> clusterConfigurationProvider) {
        this.properties = properties;
        this.standaloneConfiguration = standaloneConfigurationProvider.getIfAvailable();
        this.sentinelConfiguration = sentinelConfigurationProvider.getIfAvailable();
        this.clusterConfiguration = clusterConfigurationProvider.getIfAvailable();
    }

    public RedisConnectionFactory getConnectionFactory() {
        return null;
    }

    protected final RedisStandaloneConfiguration getStandaloneConfig() {
        if (this.standaloneConfiguration != null) {
            return this.standaloneConfiguration;
        }
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
        if (StringUtils.hasText(this.properties.getUrl())) {
            ConnectionInfo connectionInfo = this.parseUrl(this.properties.getUrl());
            config.setHostName(connectionInfo.getHostName());
            config.setPort(connectionInfo.getPort());
            config.setUsername(connectionInfo.getUsername());
            config.setPassword(RedisPassword.of((String)connectionInfo.getPassword()));
        } else {
            config.setHostName(this.properties.getHost());
            config.setPort(this.properties.getPort());
            config.setUsername(this.properties.getUsername());
            config.setPassword(RedisPassword.of((String)this.properties.getPassword()));
        }
        config.setDatabase(this.properties.getDatabase());
        return config;
    }

    protected final RedisSentinelConfiguration getSentinelConfig() {
        if (this.sentinelConfiguration != null) {
            return this.sentinelConfiguration;
        }
        MyRedisProperties.Sentinel sentinelProperties = this.properties.getSentinel();
        if (sentinelProperties != null) {
            RedisSentinelConfiguration config = new RedisSentinelConfiguration();
            config.master(sentinelProperties.getMaster());
            config.setSentinels(this.createSentinels(sentinelProperties));
            config.setUsername(this.properties.getUsername());
            if (this.properties.getPassword() != null) {
                config.setPassword(RedisPassword.of((String)this.properties.getPassword()));
            }
            config.setSentinelUsername(sentinelProperties.getUsername());
            if (sentinelProperties.getPassword() != null) {
                config.setSentinelPassword(RedisPassword.of((String)sentinelProperties.getPassword()));
            }
            config.setDatabase(this.properties.getDatabase());
            return config;
        }
        return null;
    }

    protected final RedisClusterConfiguration getClusterConfiguration() {
        if (this.clusterConfiguration != null) {
            return this.clusterConfiguration;
        }
        if (this.properties.getCluster() == null) {
            return null;
        }
        MyRedisProperties.Cluster clusterProperties = this.properties.getCluster();
        RedisClusterConfiguration config = new RedisClusterConfiguration(clusterProperties.getNodes());
        if (clusterProperties.getMaxRedirects() != null) {
            config.setMaxRedirects(clusterProperties.getMaxRedirects().intValue());
        }
        config.setUsername(this.properties.getUsername());
        if (this.properties.getPassword() != null) {
            config.setPassword(RedisPassword.of((String)this.properties.getPassword()));
        }
        return config;
    }

    protected final MyRedisProperties getProperties() {
        return this.properties;
    }

    protected boolean isPoolEnabled(MyRedisProperties.Pool pool) {
        Boolean enabled = pool.getEnabled();
        return enabled != null ? enabled : COMMONS_POOL2_AVAILABLE;
    }

    private List<RedisNode> createSentinels(MyRedisProperties.Sentinel sentinel) {
        ArrayList<RedisNode> nodes = new ArrayList<RedisNode>();
        for (String node : sentinel.getNodes()) {
            try {
                nodes.add(RedisNode.fromString((String)node));
            }
            catch (RuntimeException ex) {
                throw new IllegalStateException("Invalid redis sentinel property '" + node + "'", ex);
            }
        }
        return nodes;
    }

    protected ConnectionInfo parseUrl(String url) {
        try {
            URI uri = new URI(url);
            String scheme = uri.getScheme();
            if (!"redis".equals(scheme) && !"rediss".equals(scheme)) {
                throw new RuntimeException(url);
            }
            boolean useSsl = "rediss".equals(scheme);
            String username = null;
            String password = null;
            if (uri.getUserInfo() != null) {
                String candidate = uri.getUserInfo();
                int index = candidate.indexOf(58);
                if (index >= 0) {
                    username = candidate.substring(0, index);
                    password = candidate.substring(index + 1);
                } else {
                    password = candidate;
                }
            }
            return new ConnectionInfo(uri, useSsl, username, password);
        }
        catch (URISyntaxException ex) {
            throw new RuntimeException(url, ex);
        }
    }

    static class ConnectionInfo {
        private final URI uri;
        private final boolean useSsl;
        private final String username;
        private final String password;

        ConnectionInfo(URI uri, boolean useSsl, String username, String password) {
            this.uri = uri;
            this.useSsl = useSsl;
            this.username = username;
            this.password = password;
        }

        boolean isUseSsl() {
            return this.useSsl;
        }

        String getHostName() {
            return this.uri.getHost();
        }

        int getPort() {
            return this.uri.getPort();
        }

        String getUsername() {
            return this.username;
        }

        String getPassword() {
            return this.password;
        }
    }
}

