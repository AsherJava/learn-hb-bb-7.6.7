/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.common.plantask.extend.job;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import org.springframework.context.annotation.Scope;
import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;

@Retention(value=RetentionPolicy.RUNTIME)
@Component
@Scope(value="prototype")
public @interface PlanTaskRunner {
    public String id();

    @AliasFor(value="value")
    public String name() default "";

    @AliasFor(value="name")
    public String value() default "";

    public String title();

    public String settingPage() default "";

    public boolean usePlugs() default false;

    public String group() default "\u672a\u5206\u7ec4";

    public int order() default 99;
}

