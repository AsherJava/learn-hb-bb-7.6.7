/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.Assert
 *  javax.validation.ConstraintViolation
 *  javax.validation.Validation
 *  javax.validation.Validator
 *  javax.validation.ValidatorFactory
 */
package com.jiuqi.dc.base.common.utils;

import com.jiuqi.common.base.util.Assert;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

public class BeanValidUtils {
    private static ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();

    public static <T> Map<String, String> validate(T t, Class ... groups) {
        Validator validator = validatorFactory.getValidator();
        Set validateResult = validator.validate(t, groups);
        if (validateResult.isEmpty()) {
            return Collections.emptyMap();
        }
        LinkedHashMap<String, String> errors = new LinkedHashMap<String, String>();
        for (ConstraintViolation violation : validateResult) {
            errors.put(violation.getPropertyPath().toString(), violation.getMessage());
        }
        return errors;
    }

    public static Map<String, String> validateList(Collection<?> collection) {
        Object object;
        Map<String, String> errors;
        Assert.isNotNull(collection);
        Iterator<?> iterator = collection.iterator();
        do {
            if (iterator.hasNext()) continue;
            return Collections.emptyMap();
        } while ((errors = BeanValidUtils.validate(object = iterator.next(), new Class[0])).isEmpty());
        return errors;
    }
}

