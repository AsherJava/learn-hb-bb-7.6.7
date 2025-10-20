/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.validation.Constraint
 *  javax.validation.Payload
 */
package com.jiuqi.common.base.security.inject;

import com.jiuqi.common.base.security.inject.SqlInjectionConstraintValidator;
import com.jiuqi.common.base.security.inject.SqlInjectionConstraintValidatorForCollection;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Retention(value=RetentionPolicy.RUNTIME)
@Constraint(validatedBy={SqlInjectionConstraintValidator.class, SqlInjectionConstraintValidatorForCollection.class})
@Target(value={ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
public @interface SqlInjection {
    public String message() default "Contains illegal characters";

    public Class<?>[] groups() default {};

    public Class<? extends Payload>[] payload() default {};
}

