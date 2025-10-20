/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 *  javax.servlet.ServletException
 *  javax.servlet.ServletRequest
 *  javax.servlet.ServletResponse
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 *  org.apache.shiro.util.ThreadContext
 *  org.apache.shiro.web.filter.authc.FormAuthenticationFilter
 *  org.apache.shiro.web.util.WebUtils
 */
package com.jiuqi.va.shiro.config;

import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import com.jiuqi.va.shiro.config.MyShiroAfterFilterRunner;
import com.jiuqi.va.shiro.config.VaAuthShiroConfig;
import com.jiuqi.va.shiro.config.optimize.MyWebUtil;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.shiro.util.ThreadContext;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

public class MyFormAuthenticationFilter
extends FormAuthenticationFilter {
    private static Logger logger = LoggerFactory.getLogger(MyFormAuthenticationFilter.class);
    private MyShiroAfterFilterRunner filterRunner;

    protected boolean preHandle(ServletRequest servletRequest, ServletResponse response) throws Exception {
        boolean isfromFeign;
        ShiroUtil.cleanContext();
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        String tenantName = request.getHeader("VaTenantName");
        if (StringUtils.hasText(tenantName)) {
            ThreadContext.put((Object)"SECURITY_TENANT_KEY", (Object)tenantName);
        }
        if (isfromFeign = "true".equals(request.getHeader("FeignClient"))) {
            String trustCode = request.getHeader("TrustCode");
            if (!StringUtils.hasText(trustCode) || !trustCode.equals(VaAuthShiroConfig.getTrustCode())) {
                logger.error("The trust code verification failed.");
                return this.runAfterFilter(false);
            }
            ThreadContext.put((Object)"NONE_AUTH_KEY", (Object)"true");
            return this.runAfterFilter(true);
        }
        boolean flag = super.preHandle((ServletRequest)request, response);
        return this.runAfterFilter(flag);
    }

    private boolean runAfterFilter(boolean flag) {
        if (this.filterRunner == null) {
            this.filterRunner = (MyShiroAfterFilterRunner)ApplicationContextRegister.getBean(MyShiroAfterFilterRunner.class);
        }
        if (this.filterRunner != null) {
            flag = this.filterRunner.execute(flag);
        }
        return flag;
    }

    protected void redirectToLogin(ServletRequest servletRequest, ServletResponse servletResponse) throws IOException {
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        HttpServletResponse response = (HttpServletResponse)servletResponse;
        boolean isBackend = false;
        HttpServletRequest req = request;
        if (StringUtils.hasText(req.getHeader("x-request-from")) && req.getHeader("x-request-from").equalsIgnoreCase("backend")) {
            isBackend = true;
        }
        if (isBackend) {
            try {
                request.getRequestDispatcher("/loginTimeout").forward((ServletRequest)request, (ServletResponse)response);
            }
            catch (ServletException e) {
                e.printStackTrace();
            }
        } else {
            Object errorMessage = servletRequest.getAttribute("javax.servlet.error.message");
            if (null == errorMessage || !StringUtils.hasLength(errorMessage.toString())) {
                errorMessage = "Unauthorized";
            }
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=utf-8");
            response.sendError(401, errorMessage.toString());
        }
        ShiroUtil.logout();
    }

    protected String getPathWithinApplication(ServletRequest request) {
        return MyWebUtil.getPathWithinApplication(WebUtils.toHttp((ServletRequest)request));
    }
}

