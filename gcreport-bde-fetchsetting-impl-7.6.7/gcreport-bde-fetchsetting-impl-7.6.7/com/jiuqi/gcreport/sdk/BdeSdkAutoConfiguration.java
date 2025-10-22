/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  okhttp3.ConnectionPool
 *  okhttp3.OkHttpClient
 *  org.springframework.http.client.ClientHttpRequestFactory
 *  org.springframework.http.client.OkHttp3ClientHttpRequestFactory
 *  org.springframework.transaction.annotation.EnableTransactionManagement
 *  org.springframework.web.client.RestTemplate
 */
package com.jiuqi.gcreport.sdk;

import java.util.concurrent.TimeUnit;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages={"com.jiuqi.gcreport.sdk"})
public class BdeSdkAutoConfiguration {
    @Value(value="${jiuqi.bde.rest.connection-timeout:10}")
    private Integer connectionTimeOut;
    @Value(value="${jiuqi.bde.rest.read-timeout:600}")
    private Integer readTimeout;
    @Value(value="${jiuqi.bde.rest.write-timeout:600}")
    private Integer writeTimeout;
    @Value(value="${jiuqi.bde.rest.pool-size:20}")
    private Integer poolSize;
    @Value(value="${jiuqi.bde.rest.keep-alive-duration:300}")
    private Integer keepAliveDuration;

    @Bean(name={"bdeFetchRestTemplate"})
    public RestTemplate bdeRestTemplate() {
        return this.createRestTemplate();
    }

    public RestTemplate createRestTemplate() {
        ClientHttpRequestFactory factory = this.httpRequestFactory();
        RestTemplate restTemplate = new RestTemplate(factory);
        return restTemplate;
    }

    public ClientHttpRequestFactory httpRequestFactory() {
        return new OkHttp3ClientHttpRequestFactory(this.okHttpConfigClient());
    }

    public OkHttpClient okHttpConfigClient() {
        return new OkHttpClient().newBuilder().connectionPool(new ConnectionPool(this.poolSize.intValue(), (long)this.keepAliveDuration.intValue(), TimeUnit.SECONDS)).connectTimeout((long)this.connectionTimeOut.intValue(), TimeUnit.SECONDS).readTimeout((long)this.readTimeout.intValue(), TimeUnit.SECONDS).writeTimeout((long)this.writeTimeout.intValue(), TimeUnit.SECONDS).hostnameVerifier((hostname, session) -> true).build();
    }
}

