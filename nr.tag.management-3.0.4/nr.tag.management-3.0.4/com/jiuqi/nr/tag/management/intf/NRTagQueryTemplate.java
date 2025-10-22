/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.tag.management.intf;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.stereotype.Component;

@Target(value={ElementType.TYPE})
@Retention(value=RetentionPolicy.RUNTIME)
@Component
public @interface NRTagQueryTemplate {
    public String value();
}

