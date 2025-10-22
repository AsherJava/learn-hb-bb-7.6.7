/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.aspectj.lang.ProceedingJoinPoint
 *  org.aspectj.lang.annotation.Around
 *  org.aspectj.lang.annotation.Pointcut
 *  org.aspectj.lang.reflect.MethodSignature
 */
package com.jiuqi.nr.jtable.aop;

import com.jiuqi.nr.jtable.annotation.ActionBeforeAfterExe;
import com.jiuqi.nr.jtable.service.IActionBeforeAfterExe;
import java.util.Map;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;

public class ActionBeforeAfterAspect {
    @Autowired
    private Map<String, IActionBeforeAfterExe> beforAfterExeMap;

    @Pointcut(value="@annotation(com.jiuqi.nr.jtable.annotation.ActionBeforeAfterExe)")
    public void checkMethod() {
    }

    @Around(value="checkMethod()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        Object[] args;
        Object result = null;
        MethodSignature signature = (MethodSignature)point.getSignature();
        ActionBeforeAfterExe annotation = signature.getMethod().getAnnotation(ActionBeforeAfterExe.class);
        String code = annotation.value();
        IActionBeforeAfterExe iActionBeforeAfterExe = this.beforAfterExeMap.get(code);
        boolean proceed = iActionBeforeAfterExe.before(args = point.getArgs());
        if (proceed) {
            result = point.proceed();
        }
        iActionBeforeAfterExe.after(result);
        return result;
    }
}

