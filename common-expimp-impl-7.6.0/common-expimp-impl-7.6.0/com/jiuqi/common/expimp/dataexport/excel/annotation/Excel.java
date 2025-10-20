/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.common.expimp.dataexport.excel.annotation;

import com.jiuqi.common.expimp.converters.ExpImpConverter;
import com.jiuqi.common.expimp.converters.impl.DefaultExpImpConverter;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value={ElementType.TYPE})
@Retention(value=RetentionPolicy.RUNTIME)
@Documented
public @interface Excel {
    public String title() default "\u9875\u7b7e1";

    public Class<? extends ExpImpConverter> converter() default DefaultExpImpConverter.class;
}

