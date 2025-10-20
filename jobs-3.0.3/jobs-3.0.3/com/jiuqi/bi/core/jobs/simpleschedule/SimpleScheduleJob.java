/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.core.jobs.simpleschedule;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value={ElementType.TYPE})
@Retention(value=RetentionPolicy.RUNTIME)
@Documented
public @interface SimpleScheduleJob {
    public String groupTitle() default "";

    public String startTime() default "";

    public int intervalTime() default 0;

    public boolean enabled() default true;

    public String cron() default "";
}

