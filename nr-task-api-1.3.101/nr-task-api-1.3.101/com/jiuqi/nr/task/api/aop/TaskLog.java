/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.task.api.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(value=RetentionPolicy.RUNTIME)
@Target(value={ElementType.METHOD})
public @interface TaskLog {
    public String operation();

    public boolean recordResult() default true;

    public boolean recordParam() default true;
}

