/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.va.domain.org.OrgDTO
 *  org.aspectj.lang.JoinPoint
 *  org.aspectj.lang.annotation.Aspect
 *  org.aspectj.lang.annotation.Before
 *  org.aspectj.lang.annotation.Pointcut
 */
package com.jiuqi.gcreport.org.impl.callback;

import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.org.impl.base.GcOrgCodeConfig;
import com.jiuqi.gcreport.org.impl.base.InspectOrgUtils;
import com.jiuqi.gcreport.org.impl.cache.dao.FGcOrgTypeVersionDao;
import com.jiuqi.va.domain.org.OrgDTO;
import java.util.ArrayList;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

@Aspect
@Component
public class OrgModifyDaoPointCut {
    @Pointcut(value="execution (* com.jiuqi.va.organization.dao.VaOrgDataDao.add(..))")
    public void orgAdd() {
    }

    @Pointcut(value="execution (* com.jiuqi.va.organization.dao.VaOrgDataDao.update(..))")
    public void orgUpdate() {
    }

    @Pointcut(value="execution (* com.jiuqi.va.organization.service.impl.help.OrgDataModifyService.add(..))")
    public void serviceOrgAdd() {
    }

    @Pointcut(value="execution (* com.jiuqi.va.organization.service.impl.help.OrgDataModifyService.update(..))")
    public void serviceOrgUpdate() {
    }

    @Before(value="orgAdd()")
    public void beforeAdd(JoinPoint joinPoint) {
        if (!InspectOrgUtils.enableMergeUnit()) {
            return;
        }
        Object orgAuths = joinPoint.getArgs()[0];
        OrgDTO orgDTO = (OrgDTO)orgAuths;
        orgDTO.putIfAbsent((Object)"CURRENCYID".toLowerCase(), (Object)"CNY");
        this.processOrg(joinPoint);
    }

    @Before(value="orgUpdate()")
    public void beforeUpdate(JoinPoint joinPoint) {
        this.processOrg(joinPoint);
    }

    private void processOrg(JoinPoint joinPoint) {
        if (!InspectOrgUtils.enableMergeUnit()) {
            return;
        }
        Object orgAuths = joinPoint.getArgs()[0];
        OrgDTO orgDTO = (OrgDTO)orgAuths;
        if (StringUtils.isEmpty((String)orgDTO.getParents())) {
            return;
        }
        GcOrgCodeConfig gcOrgCodeConfig = ((FGcOrgTypeVersionDao)SpringContextUtils.getBean(FGcOrgTypeVersionDao.class)).getGcOrgCodeConfig();
        String gcParentsByOldParents = InspectOrgUtils.getGcParentsByOldParents(orgDTO.getParents(), gcOrgCodeConfig);
        orgDTO.put("gcparents", (Object)gcParentsByOldParents);
    }

    @Before(value="serviceOrgAdd()")
    public void serviceOrgAdd(JoinPoint joinPoint) {
        if (!InspectOrgUtils.enableMergeUnit()) {
            return;
        }
        Object orgAuths = joinPoint.getArgs()[0];
        OrgDTO orgDTO = (OrgDTO)orgAuths;
        orgDTO.putIfAbsent((Object)"CURRENCYID".toLowerCase(), (Object)"CNY");
        String currency = (String)orgDTO.get((Object)"CURRENCYID".toLowerCase());
        if (ObjectUtils.isEmpty(orgDTO.get((Object)("CURRENCYIDS".toLowerCase() + "_show")))) {
            orgDTO.put("hasMultiValues", (Object)true);
            ArrayList<String> list = new ArrayList<String>();
            list.add("CNY");
            if (!currency.equals("CNY")) {
                list.add(currency);
            }
            orgDTO.put("CURRENCYIDS".toLowerCase(), list);
            orgDTO.put("CURRENCYIDS".toLowerCase() + "_show", list);
        }
        this.processOrg(joinPoint);
    }

    @Before(value="serviceOrgUpdate()")
    public void serviceOrgUpdate(JoinPoint joinPoint) {
        this.processOrg(joinPoint);
    }
}

