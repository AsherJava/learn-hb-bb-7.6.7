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
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

public class SqlInjectionConstraintValidator
implements ConstraintValidator<SqlInjection, String> {
    private static Logger log = LoggerFactory.getLogger(SqlInjectionConstraintValidator.class);

    public void initialize(SqlInjection constraintAnnotation) {
        log.info("initialize constraint");
    }

    public boolean isValid(String source, ConstraintValidatorContext constraintValidatorContext) {
        if (InjectConfig.sqlAnnotationEnable() && StringUtils.hasText(source)) {
            try {
                SqlInjectValidUtil.valid(source);
            }
            catch (BusinessRuntimeException e) {
                return false;
            }
        }
        return true;
    }
}

