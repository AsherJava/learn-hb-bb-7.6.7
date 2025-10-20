/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.common.paramcheck.service;

import com.jiuqi.nr.common.paramcheck.common.ParamType;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Retention(value=RetentionPolicy.RUNTIME)
@Target(value={ElementType.TYPE})
public @interface CheckParam {
    public String name();

    public String title();

    public ParamType type() default ParamType.CUSTOM_PARAM;
}

