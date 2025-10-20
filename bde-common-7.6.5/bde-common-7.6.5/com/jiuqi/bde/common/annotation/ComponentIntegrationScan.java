/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bde.common.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Scope;
import org.springframework.core.annotation.AliasFor;

@Retention(value=RetentionPolicy.RUNTIME)
@ComponentScan
@Scope(value="singleton")
@ConditionalOnProperty(prefix="jiuqi.bde.server", name={"standalone"}, havingValue="false", matchIfMissing=false)
public @interface ComponentIntegrationScan {
    @AliasFor(annotation=ComponentScan.class)
    public String[] value() default {};

    @AliasFor(annotation=ComponentScan.class)
    public String[] basePackages() default {};
}

