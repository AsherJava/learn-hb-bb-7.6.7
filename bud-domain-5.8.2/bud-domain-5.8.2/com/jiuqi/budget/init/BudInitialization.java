/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.budget.init;

import com.jiuqi.budget.init.InitializeType;
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
@Inherited
@Component
public @interface BudInitialization {
    public String initMethod() default "";

    public InitializeType type() default InitializeType.STORAGE;
}

