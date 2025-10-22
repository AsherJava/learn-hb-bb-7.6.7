/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  springfox.documentation.builders.ApiInfoBuilder
 *  springfox.documentation.builders.PathSelectors
 *  springfox.documentation.builders.RequestHandlerSelectors
 *  springfox.documentation.service.ApiInfo
 *  springfox.documentation.service.Contact
 *  springfox.documentation.spi.DocumentationType
 *  springfox.documentation.spring.web.plugins.Docket
 *  springfox.documentation.swagger2.annotations.EnableSwagger2
 */
package com.jiuqi.gcreport.listedcompanyauthz.config;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration(value="ListedCompanySwagger2Configuration")
@EnableSwagger2
@Profile(value={"!prod"})
public class ListedCompanySwagger2Configuration {
    @Bean
    public Docket lc_build_docket() {
        return new Docket(DocumentationType.SWAGGER_2).groupName("\u4e0a\u5e02\u516c\u53f8").apiInfo(this.buildApiInfo()).select().apis(RequestHandlerSelectors.basePackage((String)"com.jiuqi.gcreport.listedcompanyauthz.web")).apis(RequestHandlerSelectors.withClassAnnotation(Api.class)).apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class)).paths(PathSelectors.any()).build();
    }

    private ApiInfo buildApiInfo() {
        return new ApiInfoBuilder().title("\u5408\u5e76\u62a5\u8868\u63a5\u53e3API").description("\u5408\u5e76\u62a5\u8868\u63a5\u53e3API").contact(new Contact("jiuqi", "http://www.jiuqi.com.cn", "jiuqi@jiuqi.com.cn")).version("1.0").build();
    }
}

