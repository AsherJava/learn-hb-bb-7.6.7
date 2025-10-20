/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.validation.ConstraintValidator
 *  javax.validation.ConstraintValidatorContext
 */
package com.jiuqi.common.base.security.inject;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.security.inject.InjectConfig;
import com.jiuqi.common.base.security.inject.SqlInjectValidUtil;
import com.jiuqi.common.base.security.inject.SqlInjection;
import com.jiuqi.common.base.util.CollectionUtils;
import java.lang.annotation.Annotation;
import java.util.Collection;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class SqlInjectionConstraintValidatorForCollection
implements ConstraintValidator<SqlInjection, Collection<String>> {
    public void initialize(SqlInjection constraintAnnotation) {
        super.initialize((Annotation)constraintAnnotation);
    }

    public boolean isValid(Collection<String> value, ConstraintValidatorContext context) {
        if (!InjectConfig.sqlAnnotationEnable() || CollectionUtils.isEmpty(value)) {
            return true;
        }
        for (String str : value) {
            try {
                SqlInjectValidUtil.valid(str);
            }
            catch (BusinessRuntimeException e) {
                return false;
            }
        }
        return true;
    }
}

