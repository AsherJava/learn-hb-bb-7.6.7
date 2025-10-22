/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.var.Variable
 *  javax.servlet.http.HttpServletRequest
 *  org.aspectj.lang.JoinPoint
 *  org.aspectj.lang.annotation.After
 *  org.aspectj.lang.annotation.Aspect
 *  org.aspectj.lang.annotation.Before
 *  org.aspectj.lang.annotation.Pointcut
 *  org.springframework.web.context.request.RequestContextHolder
 *  org.springframework.web.context.request.ServletRequestAttributes
 */
package com.jiuqi.nr.context.aspect;

import com.jiuqi.np.dataengine.var.Variable;
import com.jiuqi.nr.context.cxt.DsContext;
import com.jiuqi.nr.context.cxt.DsContextHolder;
import com.jiuqi.nr.context.cxt.impl.DsContextImpl;
import com.jiuqi.nr.context.infc.INRContext;
import com.jiuqi.nr.context.infc.impl.NRContext;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component(value="NRContextAspect")
public class NRContextAspect {
    private static final Logger logger = LoggerFactory.getLogger(NRContextAspect.class);

    @Pointcut(value="@annotation(com.jiuqi.nr.context.annotation.NRContextBuild)")
    public void checkMethod() {
    }

    @Before(value="checkMethod()")
    public void beforeMethod(JoinPoint point) {
        Object[] requestObjs = point.getArgs();
        try {
            DsContext context = DsContextHolder.getDsContext();
            DsContextImpl dsContext = (DsContextImpl)context;
            for (Object obj : requestObjs) {
                if (obj instanceof INRContext) {
                    INRContext nrContext = (INRContext)obj;
                    NRContext nrContextInfo = new NRContext(nrContext);
                    dsContext.setEntityId(nrContextInfo.getContextEntityId());
                    dsContext.setFilterExpression(nrContextInfo.getContextFilterExpression());
                    continue;
                }
                if (!(obj instanceof HttpServletRequest)) continue;
                String contextEntityId = ((HttpServletRequest)obj).getParameter("contextEntityId");
                String filterExpression = ((HttpServletRequest)obj).getParameter("contextFilterExpression");
                if (!StringUtils.hasLength(dsContext.getContextEntityId()) && StringUtils.hasLength(contextEntityId)) {
                    dsContext.setEntityId(contextEntityId);
                }
                if (StringUtils.hasLength(dsContext.getContextFilterExpression()) || !StringUtils.hasLength(filterExpression)) continue;
                dsContext.setFilterExpression(filterExpression);
            }
            if (!StringUtils.hasLength(dsContext.getContextEntityId()) && !StringUtils.hasLength(dsContext.getContextFilterExpression())) {
                HttpServletRequest servletRequest = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
                String contextEntityId = servletRequest.getParameter("contextEntityId");
                String filterExpression = servletRequest.getParameter("contextFilterExpression");
                if (StringUtils.hasLength(contextEntityId)) {
                    dsContext.setEntityId(contextEntityId);
                }
                if (StringUtils.hasLength(filterExpression)) {
                    dsContext.setFilterExpression(filterExpression);
                }
            }
        }
        catch (Exception e) {
            logger.error("\u8bf7\u6c42\u53c2\u6570\u89e3\u6790\u5f02\u5e38\uff1a" + e.getMessage());
        }
        for (Object obj : requestObjs) {
            List<Variable> variables;
            if (!(obj instanceof INRContext) || CollectionUtils.isEmpty(variables = ((INRContext)obj).getVariables())) continue;
            DsContext context = DsContextHolder.getDsContext();
            DsContextImpl dsContext = (DsContextImpl)context;
            dsContext.setVariables(variables);
            break;
        }
    }

    @After(value="checkMethod()")
    public void afterMethod(JoinPoint point) {
        DsContextHolder.clearContext();
    }
}

