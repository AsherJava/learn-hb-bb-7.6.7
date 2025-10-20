/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.organization.domain.OrgAuthDO
 *  org.aspectj.lang.JoinPoint
 *  org.aspectj.lang.annotation.Aspect
 *  org.aspectj.lang.annotation.Before
 *  org.aspectj.lang.annotation.Pointcut
 */
package com.jiuqi.gcreport.org.impl.cache.listener;

import com.jiuqi.gcreport.org.impl.base.InspectOrgUtils;
import com.jiuqi.va.organization.domain.OrgAuthDO;
import java.util.List;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class OrgAuthControllerPointCut {
    @Pointcut(value="execution (* com.jiuqi.va.organization.service.OrgAuthService.updateDetail(..))")
    public void orgAuthUpdate() {
    }

    @Before(value="orgAuthUpdate()")
    public void afterAddOrg(JoinPoint joinPoint) {
        if (joinPoint.getArgs().length == 0) {
            return;
        }
        Object orgAuths = joinPoint.getArgs()[0];
        if (orgAuths == null) {
            return;
        }
        List auths = (List)orgAuths;
        if (auths.size() == 0) {
            return;
        }
        String orgcategory = ((OrgAuthDO)auths.get(0)).getOrgcategory();
        if (orgcategory.equalsIgnoreCase("MD_ORG")) {
            return;
        }
        InspectOrgUtils.clearGcOrgCache(orgcategory);
    }
}

