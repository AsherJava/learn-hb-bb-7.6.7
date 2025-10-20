/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.validation.ConstraintViolation
 *  javax.validation.Validation
 *  javax.validation.Validator
 *  org.aspectj.lang.JoinPoint
 *  org.aspectj.lang.annotation.Aspect
 *  org.aspectj.lang.annotation.Before
 *  org.aspectj.lang.annotation.Pointcut
 *  org.aspectj.lang.reflect.MethodSignature
 */
package com.jiuqi.dc.base.common.aspect;

import com.jiuqi.dc.base.common.exception.CheckRuntimeException;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ParamCheckAspect {
    @Autowired
    private static Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Pointcut(value="@annotation(com.jiuqi.dc.base.common.annotation.ParamCheck))")
    private void validateMethod() {
    }

    @Before(value="validateMethod()")
    public void before(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        MethodSignature signature = (MethodSignature)joinPoint.getSignature();
        Set constraintViolations = validator.forExecutables().validateParameters(joinPoint.getThis(), signature.getMethod(), args, new Class[0]);
        StringBuilder messages = new StringBuilder();
        for (ConstraintViolation error : constraintViolations) {
            messages.append(error.getMessage()).append("/");
        }
        if (messages.length() > 0) {
            throw new CheckRuntimeException(messages.delete(messages.length() - 1, messages.length()).toString());
        }
    }
}

