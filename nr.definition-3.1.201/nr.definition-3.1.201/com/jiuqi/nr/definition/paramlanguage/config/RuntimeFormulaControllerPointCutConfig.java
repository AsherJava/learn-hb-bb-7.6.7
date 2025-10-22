/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.paramlanguage.config;

import com.jiuqi.nr.definition.paramlanguage.aop.RuntimeFormulaControllerPointCut;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy
@ConditionalOnProperty(value={"paramlanguage.open"}, havingValue="true")
@ComponentScan
public class RuntimeFormulaControllerPointCutConfig {
    @Bean
    public RuntimeFormulaControllerPointCut GetRuntimeFormulaControllerPointCut() {
        return new RuntimeFormulaControllerPointCut();
    }
}

