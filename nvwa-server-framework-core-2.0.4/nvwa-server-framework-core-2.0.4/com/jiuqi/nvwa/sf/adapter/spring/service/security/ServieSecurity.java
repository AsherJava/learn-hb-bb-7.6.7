/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nvwa.sf.adapter.spring.service.security;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(value=RetentionPolicy.RUNTIME)
@Target(value={ElementType.METHOD, ElementType.TYPE})
public @interface ServieSecurity {
    public static final String HEADER_NAME = "sf-security";
    public static final String MODULE_NAME = "sf-common";

    public String moduleName() default "sf-common";

    public String headerName() default "sf-security";
}

