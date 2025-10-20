/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.http.client.HttpClient
 *  org.apache.http.conn.socket.LayeredConnectionSocketFactory
 *  org.apache.http.conn.ssl.NoopHostnameVerifier
 *  org.apache.http.conn.ssl.SSLConnectionSocketFactory
 *  org.apache.http.impl.client.CloseableHttpClient
 *  org.apache.http.impl.client.HttpClientBuilder
 *  org.apache.http.impl.client.HttpClients
 *  org.apache.http.ssl.SSLContexts
 *  org.apache.http.ssl.TrustStrategy
 *  org.springframework.http.client.ClientHttpRequestFactory
 *  org.springframework.http.client.HttpComponentsClientHttpRequestFactory
 *  org.springframework.transaction.annotation.EnableTransactionManagement
 *  org.springframework.web.client.RestTemplate
 */
package com.jiuqi.gcreport.mobile.approval.autoconfigure;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.ssl.TrustStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableTransactionManagement
@ComponentScans(value={@ComponentScan(basePackages={"com.jiuqi.gcreport.mobile.approval"})})
public class MobileApprovalAutoConfiguration {
    private static final Logger logger = LoggerFactory.getLogger(MobileApprovalAutoConfiguration.class);

    @Bean(value={"ignoreSSLRestTemplate"})
    @ConditionalOnMissingBean(name={"ignoreSSLRestTemplate"})
    public RestTemplate ignoreSSLRestTemplate() {
        try {
            TrustStrategy acceptingTrustStrategy = (x509Certificates, authType) -> true;
            SSLContext sslContext = SSLContexts.custom().loadTrustMaterial(null, acceptingTrustStrategy).build();
            SSLConnectionSocketFactory connectionSocketFactory = new SSLConnectionSocketFactory(sslContext, (HostnameVerifier)new NoopHostnameVerifier());
            HttpClientBuilder httpClientBuilder = HttpClients.custom();
            httpClientBuilder.setSSLSocketFactory((LayeredConnectionSocketFactory)connectionSocketFactory);
            CloseableHttpClient httpClient = httpClientBuilder.build();
            HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
            factory.setHttpClient((HttpClient)httpClient);
            return new RestTemplate((ClientHttpRequestFactory)factory);
        }
        catch (Exception e) {
            logger.error("\u914d\u7f6e\u5ffd\u7565SSL\u7684RestTemplate\u51fa\u73b0\u9519\u8bef", e);
            throw new RuntimeException(e);
        }
    }
}

