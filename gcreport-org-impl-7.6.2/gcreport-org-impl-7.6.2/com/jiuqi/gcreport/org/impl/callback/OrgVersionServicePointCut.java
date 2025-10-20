/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.org.api.enums.EventChangeTypeEnum
 *  com.jiuqi.gcreport.org.api.event.GcOrgBaseEvent
 *  com.jiuqi.gcreport.org.api.event.GcOrgVersionChangeEvent
 *  com.jiuqi.gcreport.org.api.vo.OrgTypeVO
 *  com.jiuqi.va.domain.org.OrgVersionDO
 *  org.aspectj.lang.JoinPoint
 *  org.aspectj.lang.annotation.Aspect
 *  org.aspectj.lang.annotation.Before
 *  org.aspectj.lang.annotation.Pointcut
 */
package com.jiuqi.gcreport.org.impl.callback;

import com.jiuqi.gcreport.org.api.enums.EventChangeTypeEnum;
import com.jiuqi.gcreport.org.api.event.GcOrgBaseEvent;
import com.jiuqi.gcreport.org.api.event.GcOrgVersionChangeEvent;
import com.jiuqi.gcreport.org.api.vo.OrgTypeVO;
import com.jiuqi.gcreport.org.impl.cache.service.impl.GcOrgCacheManage;
import com.jiuqi.va.domain.org.OrgVersionDO;
import java.util.ArrayList;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class OrgVersionServicePointCut {
    @Autowired
    private GcOrgCacheManage manager;

    @Pointcut(value="execution (* com.jiuqi.va.organization.service.impl.OrgVersionServiceImpl.add(..))")
    public void versionAdd() {
    }

    @Pointcut(value="execution (* com.jiuqi.va.organization.service.impl.OrgVersionServiceImpl.split(..))")
    public void versionSplit() {
    }

    @Pointcut(value="execution (* com.jiuqi.va.organization.service.impl.OrgVersionServiceImpl.update(..))")
    public void versionUpdate() {
    }

    @Pointcut(value="execution (* com.jiuqi.va.organization.service.impl.OrgVersionServiceImpl.remove(..))")
    public void versionRemove() {
    }

    @Before(value="versionAdd()")
    public void versionAdd(JoinPoint joinPoint) {
        this.clearCache(joinPoint);
    }

    @Before(value="versionSplit()")
    public void versionSplit(JoinPoint joinPoint) {
        this.clearCache(joinPoint);
    }

    @Before(value="versionUpdate()")
    public void versionUpdate(JoinPoint joinPoint) {
        this.clearCache(joinPoint);
    }

    @Before(value="versionRemove()")
    public void versionRemove(JoinPoint joinPoint) {
        this.clearCache(joinPoint);
    }

    private void clearCache(JoinPoint joinPoint) {
        Object obj = joinPoint.getArgs()[0];
        OrgVersionDO orgVerDO = (OrgVersionDO)obj;
        OrgTypeVO type = new OrgTypeVO();
        type.setName(orgVerDO.getCategoryname());
        GcOrgVersionChangeEvent event = new GcOrgVersionChangeEvent(EventChangeTypeEnum.UPDATE, type, new ArrayList());
        this.manager.clearTVCache((GcOrgBaseEvent<?>)event);
    }
}

