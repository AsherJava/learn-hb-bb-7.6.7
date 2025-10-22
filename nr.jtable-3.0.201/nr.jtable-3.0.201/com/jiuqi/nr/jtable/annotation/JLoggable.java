/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.jtable.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Retention(value=RetentionPolicy.RUNTIME)
@Target(value={ElementType.METHOD, ElementType.TYPE})
public @interface JLoggable {
    public String modula() default "\u6570\u636e\u5f55\u5165";

    public String value();

    public boolean ignoreArgs() default true;
}

