/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.dc.mappingscheme.client.dto.DataSchemeDTO
 *  org.apache.commons.lang3.math.NumberUtils
 *  org.aspectj.lang.JoinPoint
 *  org.aspectj.lang.annotation.Aspect
 *  org.aspectj.lang.annotation.Before
 *  org.aspectj.lang.annotation.Pointcut
 */
package com.jiuqi.dc.mappingscheme.impl.aspect;

import com.jiuqi.dc.mappingscheme.client.dto.DataSchemeDTO;
import java.util.Optional;
import org.apache.commons.lang3.math.NumberUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Configuration;

@Aspect
@Configuration
public class DataSchemeCheckAspect {
    public static final int MAX_SCHEME_NUM = 99;
    public static final int MIN_SCHEME_NUM = 1;

    @Pointcut(value="@annotation(com.jiuqi.dc.mappingscheme.impl.annotation.DataSchemeCheck)")
    private void validateMethod() {
    }

    @Before(value="validateMethod()")
    public void validateParameters(JoinPoint joinPoint) {
        Object[] args;
        for (Object arg : args = joinPoint.getArgs()) {
            int schemeNum;
            if (!(arg instanceof DataSchemeDTO) || (schemeNum = Optional.of(arg).map(e -> ((DataSchemeDTO)e).getCode()).map(e -> NumberUtils.toInt((String)e, (int)NumberUtils.INTEGER_ZERO)).orElse(NumberUtils.INTEGER_ZERO).intValue()) <= 99 && schemeNum >= 1) continue;
            throw new IllegalArgumentException("\u6570\u636e\u6620\u5c04\u65b9\u6848\u4ee3\u7801\u4e0d\u89c4\u8303!");
        }
    }
}

