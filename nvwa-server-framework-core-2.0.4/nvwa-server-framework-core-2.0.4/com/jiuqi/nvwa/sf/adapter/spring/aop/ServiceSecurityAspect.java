/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.aspectj.lang.JoinPoint
 *  org.aspectj.lang.ProceedingJoinPoint
 *  org.aspectj.lang.annotation.Aspect
 *  org.aspectj.lang.annotation.Before
 */
package com.jiuqi.nvwa.sf.adapter.spring.aop;

import com.jiuqi.nvwa.sf.adapter.spring.service.security.SFSecurityProvider;
import com.jiuqi.nvwa.sf.adapter.spring.service.security.ServieSecurity;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ServiceSecurityAspect {
    @Autowired
    private SFSecurityProvider securityProvider;

    @Before(value="@annotation(sfSecurity)")
    public void interceptAnnotatedMethod(JoinPoint joinPoint, ServieSecurity sfSecurity) {
        this.securityProvider.authentication(sfSecurity.moduleName(), sfSecurity.headerName());
    }

    @Before(value="@within(sfSecurity)")
    public void interceptAnnotatedClass(ProceedingJoinPoint joinPoint, ServieSecurity sfSecurity) {
        this.securityProvider.authentication(sfSecurity.moduleName(), sfSecurity.headerName());
    }
}

