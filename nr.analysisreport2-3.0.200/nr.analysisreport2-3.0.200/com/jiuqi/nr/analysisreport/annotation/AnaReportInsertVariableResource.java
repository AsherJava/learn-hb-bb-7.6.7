/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.analysisreport.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.stereotype.Component;

@Target(value={ElementType.TYPE})
@Retention(value=RetentionPolicy.RUNTIME)
@Documented
@Component
@Inherited
public @interface AnaReportInsertVariableResource {
    public String name();

    public String pluginName();

    public String pluginType();

    public int order();
}

