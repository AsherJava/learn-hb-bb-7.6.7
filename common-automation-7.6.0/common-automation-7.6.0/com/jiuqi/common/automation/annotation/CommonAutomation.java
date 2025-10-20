/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.common.automation.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value={ElementType.TYPE})
@Retention(value=RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface CommonAutomation {
    public String name() default "";

    public String title() default "";

    public String path() default "/";
}

