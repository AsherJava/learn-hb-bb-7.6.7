/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.core.restapi.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value={ElementType.TYPE})
@Retention(value=RetentionPolicy.RUNTIME)
@Documented
public @interface PermissionsResourceGroup {
    public String value();

    public String code() default "";

    public String prodLine() default "\u5973\u5a32";
}

