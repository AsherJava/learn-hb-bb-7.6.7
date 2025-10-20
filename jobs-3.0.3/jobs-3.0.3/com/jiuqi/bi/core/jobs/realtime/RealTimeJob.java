/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.core.jobs.realtime;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value={ElementType.TYPE})
@Retention(value=RetentionPolicy.RUNTIME)
@Documented
public @interface RealTimeJob {
    public String group() default "";

    public String groupTitle() default "";

    public boolean hasProgress() default true;

    public boolean cancellable() default false;

    public boolean rollback() default false;

    public boolean isolate() default false;

    public String subject() default "\u9ed8\u8ba4[\u5373\u65f6\u4efb\u52a1]";

    public String[] tags() default {};

    public int defaultMaxConcurrency() default 0;
}

