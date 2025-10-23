/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mongodb.MongoClient
 *  com.mongodb.MongoClientOptions
 *  com.mongodb.MongoClientURI
 *  com.mongodb.MongoCredential
 *  com.mongodb.ServerAddress
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.np.blob.conf;

import com.jiuqi.np.blob.BlobContainerProvider;
import com.jiuqi.np.blob.conf.NpBlobProperties;
import com.jiuqi.np.blob.impl.BlobContainerFactory;
import com.jiuqi.np.blob.impl.BlobContainerProviderImpl;
import com.jiuqi.np.blob.tenant.MultiTenancyDbBlobContainerFactory;
import com.jiuqi.np.blob.tenant.MultiTenancyFileBlobContainerFactory;
import com.jiuqi.np.blob.tenant.MultiTenancyMongoBlobContainerFactory;
import com.jiuqi.np.blob.tenant.NpContextTenantIdResolver;
import com.jiuqi.np.blob.tenant.TenantIdResolver;
import com.jiuqi.np.blob.util.BeanUtil;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.StringUtils;

@Configuration
@EnableConfigurationProperties(value={NpBlobProperties.class})
@ComponentScan(basePackages={"com.jiuqi.np.blob.impl"})
public class BlobAutoConfiguration {
    @Bean
    public BeanUtil getBeanUtil() {
        return new BeanUtil();
    }

    @Configuration
    @EnableConfigurationProperties(value={NpBlobProperties.class})
    @ConditionalOnExpression(value="'${jiuqi.np.blob.type}'.toUpperCase() == 'MONGO'")
    public static class MultiTenancyMongoDBBlobConfiguration {
        @Autowired
        private MongoProperties mongoProperties;

        @Bean
        @Primary
        public BlobContainerFactory getMultiTenancyBlobContainerFactory() {
            MongoClient mongoClient = null;
            String dataBase = this.mongoProperties.getDatabase();
            if (this.mongoProperties.getUri() != null && this.mongoProperties.getUri().trim().length() > 0) {
                MongoClientURI uri = new MongoClientURI(this.mongoProperties.getUri());
                mongoClient = new MongoClient(uri);
            } else {
                ServerAddress serverAddr = new ServerAddress(this.mongoProperties.getHost(), this.mongoProperties.getPort().intValue());
                String authenticationDatabase = this.mongoProperties.getAuthenticationDatabase();
                if (authenticationDatabase == null || authenticationDatabase.length() == 0) {
                    authenticationDatabase = dataBase;
                }
                MongoCredential credential = MongoCredential.createCredential((String)this.mongoProperties.getUsername(), (String)authenticationDatabase, (char[])this.mongoProperties.getPassword());
                MongoClientOptions options = MongoClientOptions.builder().applicationName("NR").build();
                mongoClient = new MongoClient(serverAddr, credential, options);
            }
            NpContextTenantIdResolver blobTenantIdResolver = new NpContextTenantIdResolver();
            return new MultiTenancyMongoBlobContainerFactory(dataBase, mongoClient, blobTenantIdResolver);
        }

        @ConditionalOnMissingBean
        @Bean(name={"com.jiuqi.np.blob.impl.BlobContainerProviderImpl"})
        public BlobContainerProvider getBlobContainerProvider() {
            return new BlobContainerProviderImpl(this.getMultiTenancyBlobContainerFactory());
        }
    }

    @Configuration
    @EnableConfigurationProperties(value={NpBlobProperties.class})
    @ConditionalOnExpression(value="'${jiuqi.np.blob.type}'.toUpperCase() != 'MONGO'")
    public static class MultiTenancyBlobConfiguration {
        @Autowired
        private NpBlobProperties blobProperties;
        @Autowired
        private JdbcTemplate jdbcTemplate;

        private TenantIdResolver blobTenantIdResolver() {
            return new NpContextTenantIdResolver();
        }

        @Bean
        @Primary
        public BlobContainerFactory getMultiTenancyBlobContainerFactory() {
            switch (this.blobProperties.getDataSourceType().toUpperCase()) {
                case "DB": {
                    return new MultiTenancyDbBlobContainerFactory(this.jdbcTemplate, this.blobTenantIdResolver());
                }
                case "LOCAL": {
                    if (!StringUtils.isEmpty(this.blobProperties.getRootPath())) {
                        this.blobProperties.setRootPath();
                    }
                    return new MultiTenancyFileBlobContainerFactory(this.blobTenantIdResolver());
                }
            }
            return new MultiTenancyDbBlobContainerFactory(this.jdbcTemplate, this.blobTenantIdResolver());
        }

        @ConditionalOnMissingBean
        @Bean(name={"com.jiuqi.np.blob.impl.BlobContainerProviderImpl"})
        public BlobContainerProvider getBlobContainerProvider() {
            return new BlobContainerProviderImpl(this.getMultiTenancyBlobContainerFactory());
        }
    }
}

