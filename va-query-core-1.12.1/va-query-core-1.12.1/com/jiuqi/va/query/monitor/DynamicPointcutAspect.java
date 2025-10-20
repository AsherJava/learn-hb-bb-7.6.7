/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.query.sql.vo.QueryParamVO
 *  org.aspectj.lang.ProceedingJoinPoint
 *  org.aspectj.lang.annotation.Around
 *  org.aspectj.lang.annotation.Aspect
 *  org.aspectj.lang.annotation.Pointcut
 */
package com.jiuqi.va.query.monitor;

import com.jiuqi.va.query.monitor.MonitorConfig;
import com.jiuqi.va.query.monitor.MonitorUtils;
import com.jiuqi.va.query.monitor.QueryLogEvent;
import com.jiuqi.va.query.sql.vo.QueryParamVO;
import com.jiuqi.va.query.template.service.TemplateDesignService;
import java.util.HashMap;
import java.util.Map;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class DynamicPointcutAspect {
    @Autowired
    private TemplateDesignService templateDesignService;
    @Autowired
    private MonitorConfig monitorConfig;

    @Pointcut(value="execution(* com.jiuqi.va.query.sql.service.SqlQueryService.execSql(String, int, int, java.util.Map<java.lang.String, java.lang.Object>, boolean))")
    public void execSqlParam() {
    }

    @Pointcut(value="execution(* com.jiuqi.va.query.sql.service.SqlQueryService.execSql(com.jiuqi.va.query.sql.vo.QueryParamVO))")
    public void execSql() {
    }

    @Pointcut(value="execution(* com.jiuqi.va.query.sql.service.SqlQueryService.expandAll(com.jiuqi.va.query.sql.vo.QueryParamVO))")
    public void expandAll() {
    }

    @Around(value="expandAll()")
    public Object expandAll(ProceedingJoinPoint pjp) throws Throwable {
        if (!this.monitorConfig.isEnabled()) {
            return pjp.proceed();
        }
        Object[] args = pjp.getArgs();
        QueryParamVO queryParamVO = (QueryParamVO)args[0];
        QueryLogEvent queryLogEvent = this.getQueryLogEvent(queryParamVO.getQueryTemplate().getId(), queryParamVO.getParams());
        return DynamicPointcutAspect.doBizMethod(pjp, queryLogEvent);
    }

    @Around(value="execSql()")
    public Object execSql(ProceedingJoinPoint pjp) throws Throwable {
        if (!this.monitorConfig.isEnabled()) {
            return pjp.proceed();
        }
        Object[] args = pjp.getArgs();
        QueryParamVO queryParamVO = (QueryParamVO)args[0];
        QueryLogEvent queryLogEvent = this.getQueryLogEvent(queryParamVO.getQueryTemplate().getId(), queryParamVO.getParams());
        return DynamicPointcutAspect.doBizMethod(pjp, queryLogEvent);
    }

    @Around(value="execSqlParam()")
    public Object execSqlParam(ProceedingJoinPoint pjp) throws Throwable {
        if (!this.monitorConfig.isEnabled()) {
            return pjp.proceed();
        }
        Object[] args = pjp.getArgs();
        String templateID = (String)args[0];
        Map<String, Object> params = new HashMap<String, Object>();
        if (args[3] != null) {
            params = (Map)args[3];
        }
        QueryLogEvent queryLogEvent = this.getQueryLogEvent(templateID, params);
        return DynamicPointcutAspect.doBizMethod(pjp, queryLogEvent);
    }

    private static Object doBizMethod(ProceedingJoinPoint pjp, QueryLogEvent queryLogEvent) throws Throwable {
        try {
            Object object = pjp.proceed();
            return object;
        }
        finally {
            MonitorUtils.eventEnd(queryLogEvent);
        }
    }

    private QueryLogEvent getQueryLogEvent(String templateID, Map<String, Object> params) {
        String templateCode = this.templateDesignService.getTemplateCodeByTemplateId(templateID);
        if (params == null) {
            return MonitorUtils.eventStart("query", templateCode, new Object[0]);
        }
        Object[] args1 = new Object[params.size()];
        int i = 0;
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            args1[i++] = entry.getValue();
        }
        return MonitorUtils.eventStart("query", templateCode, args1);
    }
}

