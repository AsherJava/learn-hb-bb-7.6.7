/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.http.client.HttpClient
 *  org.apache.http.conn.socket.LayeredConnectionSocketFactory
 *  org.apache.http.conn.ssl.NoopHostnameVerifier
 *  org.apache.http.conn.ssl.SSLConnectionSocketFactory
 *  org.apache.http.impl.client.HttpClientBuilder
 *  org.apache.http.impl.client.HttpClients
 *  org.apache.http.ssl.SSLContexts
 *  org.apache.http.ssl.TrustStrategy
 *  org.springframework.http.client.ClientHttpRequestFactory
 *  org.springframework.http.client.HttpComponentsClientHttpRequestFactory
 *  org.springframework.web.client.RestTemplate
 *  org.springframework.web.util.DefaultUriBuilderFactory
 *  org.springframework.web.util.DefaultUriBuilderFactory$EncodingMode
 *  org.springframework.web.util.UriTemplateHandler
 */
package com.jiuqi.gcreport.oauth2.config;

import com.jiuqi.gcreport.oauth2.exception.GcOAuth2Exception;
import com.jiuqi.gcreport.oauth2.extend.GcOAuth2V2CertifyExtendService;
import com.jiuqi.gcreport.oauth2.service.GcOAuth2Service;
import com.jiuqi.gcreport.oauth2.service.GcRenderErrorService;
import com.jiuqi.gcreport.oauth2.service.impl.GcOAuth2ServiceImpl;
import com.jiuqi.gcreport.oauth2.service.impl.GcRenderErrorServiceImpl;
import com.jiuqi.gcreport.oauth2.util.RestTemplateLogger;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.ssl.TrustStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.UriTemplateHandler;

@Configuration
@ComponentScan(value={"com.jiuqi.gcreport.oauth2.**"})
@PropertySource(value={"classpath:oauth2-login.properties"})
public class OAuth2AutoConfig {
    private static final Logger logger = LoggerFactory.getLogger(OAuth2AutoConfig.class);

    @Bean(value={"ignoreSSLRestTemplate"})
    public RestTemplate ignoreSSLRestTemplate() {
        try {
            logger.info("use gcreport oauth2 module init ignoreSSLRestTemplate !");
            TrustStrategy acceptingTrustStrategy = (x509Certificates, authType) -> true;
            SSLContext sslContext = SSLContexts.custom().loadTrustMaterial(null, acceptingTrustStrategy).build();
            SSLConnectionSocketFactory connectionSocketFactory = new SSLConnectionSocketFactory(sslContext, (HostnameVerifier)new NoopHostnameVerifier());
            HttpClientBuilder httpClientBuilder = HttpClients.custom();
            httpClientBuilder.setSSLSocketFactory((LayeredConnectionSocketFactory)connectionSocketFactory);
            RestTemplate restTemplate = new RestTemplate((ClientHttpRequestFactory)new HttpComponentsClientHttpRequestFactory((HttpClient)httpClientBuilder.build()));
            restTemplate.getInterceptors().add(new RestTemplateLogger());
            DefaultUriBuilderFactory defaultUriBuilderFactory = new DefaultUriBuilderFactory();
            defaultUriBuilderFactory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.VALUES_ONLY);
            restTemplate.setUriTemplateHandler((UriTemplateHandler)defaultUriBuilderFactory);
            logger.info("init ignoreSSLRestTemplate succeed !");
            return restTemplate;
        }
        catch (Exception e) {
            logger.error("\u914d\u7f6e\u5ffd\u7565SSL\u7684RestTemplate\u51fa\u73b0\u9519\u8bef", e);
            throw new GcOAuth2Exception("\u914d\u7f6e\u5ffd\u7565SSL\u7684RestTemplate\u51fa\u73b0\u9519\u8bef", e);
        }
    }

    @Bean
    @ConditionalOnMissingBean(value={GcOAuth2Service.class})
    public GcOAuth2Service gcOAuth2Service() {
        return new GcOAuth2ServiceImpl();
    }

    @Bean
    @ConditionalOnMissingBean(value={GcOAuth2V2CertifyExtendService.class})
    public GcOAuth2V2CertifyExtendService gcOAuth2CertifyExtendServiceV2() {
        return new GcOAuth2V2CertifyExtendService();
    }

    @Bean
    @ConditionalOnMissingBean(value={GcRenderErrorService.class})
    public GcRenderErrorService GcRenderErrorService() {
        return new GcRenderErrorServiceImpl();
    }
}

