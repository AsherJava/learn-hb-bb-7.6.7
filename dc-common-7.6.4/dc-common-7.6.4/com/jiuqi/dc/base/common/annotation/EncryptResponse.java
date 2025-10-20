/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.base.common.annotation;

import com.jiuqi.dc.base.common.enums.EncryptType;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value={ElementType.METHOD})
@Retention(value=RetentionPolicy.RUNTIME)
@Documented
public @interface EncryptResponse {
    public EncryptType encryptType() default EncryptType.BASE64;
}

