/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.aspectj.lang.JoinPoint
 *  org.aspectj.lang.annotation.Aspect
 *  org.aspectj.lang.annotation.Before
 *  org.aspectj.lang.annotation.Pointcut
 *  org.aspectj.lang.reflect.MethodSignature
 */
package com.jiuqi.nr.dataentry.funcVerificated.aop;

import com.jiuqi.nr.dataentry.exception.DataEntryFunctionException;
import com.jiuqi.nr.dataentry.funcVerificated.annotation.FuncVerificated;
import com.jiuqi.nr.dataentry.service.IFunctionAuthorService;
import com.jiuqi.nr.dataentry.util.ExceptionConsts;
import java.lang.reflect.Method;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component(value="FuncVerificatedAspect")
public class FuncVerificatedAspect {
    @Autowired
    private IFunctionAuthorService functionAuthorService;

    @Pointcut(value="@annotation(com.jiuqi.nr.dataentry.funcVerificated.annotation.FuncVerificated)")
    public void checkMethod() {
    }

    @Before(value="checkMethod()")
    public void wrapMethod(JoinPoint point) throws Throwable {
        Method method = ((MethodSignature)MethodSignature.class.cast(point.getSignature())).getMethod();
        FuncVerificated classFuncVerificated = method.getAnnotation(FuncVerificated.class);
        int queryAuthorByModule = this.functionAuthorService.queryAuthorByModule(classFuncVerificated.value());
        if (queryAuthorByModule < 0) {
            throw new DataEntryFunctionException(ExceptionConsts.EXCEPTION_FUNCTION_ERROR);
        }
    }
}

