/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nvwa.sf.anno;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value={ElementType.TYPE, ElementType.METHOD})
@Retention(value=RetentionPolicy.RUNTIME)
@Documented
public @interface Licence {
    public String module();

    public String point();
}

