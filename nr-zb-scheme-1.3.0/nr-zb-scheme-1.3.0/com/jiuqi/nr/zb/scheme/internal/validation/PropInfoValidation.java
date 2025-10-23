/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.validation.Constraint
 *  javax.validation.Payload
 */
package com.jiuqi.nr.zb.scheme.internal.validation;

import com.jiuqi.nr.zb.scheme.internal.validation.PropInfoValidator;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy={PropInfoValidator.class})
@Target(value={ElementType.FIELD, ElementType.METHOD})
@Retention(value=RetentionPolicy.RUNTIME)
public @interface PropInfoValidation {
    public String message() default "Invalid value";

    public Class<?>[] groups() default {};

    public Class<? extends Payload>[] payload() default {};
}

