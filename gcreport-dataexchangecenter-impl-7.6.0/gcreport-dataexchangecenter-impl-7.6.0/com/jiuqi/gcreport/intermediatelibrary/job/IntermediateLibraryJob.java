/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.intermediatelibrary.job;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Retention(value=RetentionPolicy.RUNTIME)
@Component
@Scope(value="prototype")
public @interface IntermediateLibraryJob {
    public String classPath();
}

