/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.base.common.storage.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import org.springframework.context.annotation.Scope;
import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;

@Retention(value=RetentionPolicy.RUNTIME)
@Component
@Scope(value="singleton")
public @interface DcStorage {
    @AliasFor(annotation=Component.class)
    public String value() default "";

    public int order() default 0x7FFFFFFF;
}

