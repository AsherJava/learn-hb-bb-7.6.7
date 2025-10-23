/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.converterconfig;

import com.jiuqi.nr.workflow2.application.ext.form.reject.FormRejectRefreshStatusV2;
import com.jiuqi.nr.workflow2.application.ext.form.reject.FormRejectV2AccessJudge;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages={"com.jiuqi.nr.workflow2.converter"})
public class Workflow2ConverterBeanConfig {
    @Bean
    public FormRejectRefreshStatusV2 getFormRejectRefreshStatusV2() {
        return new FormRejectRefreshStatusV2();
    }

    @Bean
    public FormRejectV2AccessJudge getFormRejectAccessJudge() {
        return new FormRejectV2AccessJudge();
    }
}

