/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  feign.codec.Encoder
 *  feign.form.ContentType
 *  feign.form.MultipartFormContentProcessor
 *  feign.form.multipart.Writer
 *  feign.form.spring.SpringFormEncoder
 *  org.springframework.cloud.client.loadbalancer.LoadBalanced
 *  org.springframework.cloud.openfeign.EnableFeignClients
 *  org.springframework.cloud.openfeign.FeignClientProperties
 *  org.springframework.cloud.openfeign.support.AbstractFormWriter
 *  org.springframework.cloud.openfeign.support.HttpMessageConverterCustomizer
 *  org.springframework.cloud.openfeign.support.PageableSpringEncoder
 *  org.springframework.web.client.RestTemplate
 */
package com.jiuqi.va.feign.config;

import com.jiuqi.va.feign.client.AuthRoleClient;
import com.jiuqi.va.feign.client.AuthUserClient;
import com.jiuqi.va.feign.client.TenantInfoClient;
import com.jiuqi.va.feign.client.VaLogClient;
import com.jiuqi.va.feign.config.FeignSpringEncoder;
import feign.codec.Encoder;
import feign.form.ContentType;
import feign.form.MultipartFormContentProcessor;
import feign.form.multipart.Writer;
import feign.form.spring.SpringFormEncoder;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClientProperties;
import org.springframework.cloud.openfeign.support.AbstractFormWriter;
import org.springframework.cloud.openfeign.support.HttpMessageConverterCustomizer;
import org.springframework.cloud.openfeign.support.PageableSpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.client.RestTemplate;

@Configuration
@Lazy(value=false)
@EnableFeignClients(clients={AuthRoleClient.class, AuthUserClient.class, TenantInfoClient.class, VaLogClient.class})
@ComponentScan(basePackages={"com.jiuqi.va.feign.config"})
@PropertySource(value={"classpath:va-feign-common.properties"})
public class VaFeignCommonConfig
implements InitializingBean {
    @Autowired(required=false)
    private FeignClientProperties feignClientProperties;
    @Autowired(required=false)
    private SpringDataWebProperties springDataWebProperties;
    @Autowired(required=false)
    private ObjectFactory<HttpMessageConverters> messageConverters;
    private static String exceptionLevel = "WARN";

    @Value(value="${feignClient.global.exception.level:WARN}")
    public void setCachePub(String globalExceptionLevel) {
        exceptionLevel = globalExceptionLevel;
    }

    public static String getExceptionLevel() {
        return exceptionLevel;
    }

    @Primary
    @Bean
    @LoadBalanced
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    @Primary
    Encoder feignEncoderPageable(ObjectProvider<AbstractFormWriter> formWriterProvider, ObjectProvider<HttpMessageConverterCustomizer> customizers) {
        AbstractFormWriter formWriter = formWriterProvider.getIfAvailable();
        FeignSpringEncoder springEncoder = null;
        springEncoder = formWriter != null ? new FeignSpringEncoder(new SpringPojoFormEncoder(formWriter), this.messageConverters, customizers) : new FeignSpringEncoder(new SpringFormEncoder(), this.messageConverters, customizers);
        PageableSpringEncoder encoder = new PageableSpringEncoder((Encoder)springEncoder);
        if (this.springDataWebProperties != null) {
            encoder.setPageParameter(this.springDataWebProperties.getPageable().getPageParameter());
            encoder.setSizeParameter(this.springDataWebProperties.getPageable().getSizeParameter());
            encoder.setSortParameter(this.springDataWebProperties.getSort().getSortParameter());
        }
        return encoder;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (this.feignClientProperties != null && !this.feignClientProperties.isDefaultToProperties()) {
            throw new RuntimeException("We need to control exception output information, so the [feign.client.default-to-properties] must be true!");
        }
    }

    private class SpringPojoFormEncoder
    extends SpringFormEncoder {
        SpringPojoFormEncoder(AbstractFormWriter formWriter) {
            MultipartFormContentProcessor processor = (MultipartFormContentProcessor)this.getContentProcessor(ContentType.MULTIPART);
            processor.addFirstWriter((Writer)formWriter);
        }
    }
}

