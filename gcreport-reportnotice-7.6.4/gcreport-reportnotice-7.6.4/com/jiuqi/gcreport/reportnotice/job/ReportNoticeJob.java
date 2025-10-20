/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.reportnotice.job;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Retention(value=RetentionPolicy.RUNTIME)
@Component
@Scope(value="prototype")
public @interface ReportNoticeJob {
    public String code();

    public String classPath();
}

