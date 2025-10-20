/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.R
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PostMapping
 */
package com.jiuqi.va.biz.global;

import com.jiuqi.va.biz.utils.R;
import java.lang.reflect.Method;
import org.aopalliance.aop.Advice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.ClassFilter;
import org.springframework.aop.MethodMatcher;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractPointcutAdvisor;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Component
public class ControllerInject
extends AbstractPointcutAdvisor {
    private static final String ERROR_MESSAGE = "\u672a\u5904\u7406\u7684\u5f02\u5e38";
    private static final long serialVersionUID = 1L;
    private static final Logger logger = LoggerFactory.getLogger(ControllerInject.class);
    private Advice advice = invocation -> {
        try {
            return invocation.proceed();
        }
        catch (Exception e) {
            Class<?> returnType = invocation.getMethod().getReturnType();
            if (R.class.isAssignableFrom(returnType)) {
                return R.error(e);
            }
            if (com.jiuqi.va.domain.common.R.class.isAssignableFrom(returnType)) {
                logger.error(ERROR_MESSAGE, e);
                return com.jiuqi.va.domain.common.R.error((String)e.getMessage());
            }
            logger.error(ERROR_MESSAGE, e);
            throw e;
        }
        catch (Throwable e) {
            logger.error(ERROR_MESSAGE, e);
            throw e;
        }
    };

    @Override
    public Pointcut getPointcut() {
        return new Pointcut(){
            private ClassFilter classFilter = new ClassFilter(){

                @Override
                public boolean matches(Class<?> clazz) {
                    String className = clazz.getName();
                    return className.startsWith("com.jiuqi") && className.endsWith("Controller");
                }
            };
            MethodMatcher methodMatcher = new MethodMatcher(){

                @Override
                public boolean matches(Method method, Class<?> targetClass, Object ... args) {
                    throw new UnsupportedOperationException();
                }

                @Override
                public boolean matches(Method method, Class<?> targetClass) {
                    return method.getAnnotation(PostMapping.class) != null || method.getAnnotation(GetMapping.class) != null;
                }

                @Override
                public boolean isRuntime() {
                    return false;
                }
            };

            @Override
            public ClassFilter getClassFilter() {
                return this.classFilter;
            }

            @Override
            public MethodMatcher getMethodMatcher() {
                return this.methodMatcher;
            }
        };
    }

    @Override
    public Advice getAdvice() {
        return this.advice;
    }
}

