/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  springfox.documentation.RequestHandler
 *  springfox.documentation.builders.ApiInfoBuilder
 *  springfox.documentation.builders.PathSelectors
 *  springfox.documentation.service.ApiInfo
 *  springfox.documentation.service.Contact
 *  springfox.documentation.spi.DocumentationType
 *  springfox.documentation.spring.web.plugins.Docket
 *  springfox.documentation.swagger2.annotations.EnableSwagger2
 */
package com.jiuqi.gcreport.listedcompanyauthz.config;

import java.util.function.Predicate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration(value="Swagger2Configuration")
@EnableSwagger2
@Profile(value={"!prod"})
public class Swagger2Configuration {
    private final Predicate<RequestHandler> predicate = new Predicate<RequestHandler>(){

        @Override
        public boolean test(RequestHandler input) {
            Class declaringClass = input.getHandlerMethod().getBeanType();
            for (Object url : input.getPatternsCondition().getPatterns()) {
                if (String.valueOf(url).indexOf("tree-child") < 0) continue;
                return true;
            }
            return false;
        }
    };

    @Bean
    public Docket buildDocket_ca() {
        return new Docket(DocumentationType.SWAGGER_2).groupName("\u5408\u5e76\u62a5\u8868").apiInfo(this.buildApiInfo()).select().apis(this.predicate).paths(PathSelectors.any()).build();
    }

    private ApiInfo buildApiInfo() {
        return new ApiInfoBuilder().title("\u5408\u5e76\u62a5\u8868\u63a5\u53e32API").description("\u5408\u5e76\u62a5\u8868\u63a5\u53e32API").termsOfServiceUrl("http://www.jiuqi.com.cn/22").contact(new Contact("jiuqi2", "http://www.jiuqi.com.cn2", "admin1@jiuqi.com.cn")).version("1.0").build();
    }
}

