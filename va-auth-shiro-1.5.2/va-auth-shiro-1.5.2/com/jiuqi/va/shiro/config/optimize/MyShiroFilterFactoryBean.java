/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.Filter
 *  javax.servlet.FilterChain
 *  javax.servlet.ServletException
 *  javax.servlet.ServletRequest
 *  javax.servlet.ServletResponse
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 *  org.apache.shiro.mgt.SecurityManager
 *  org.apache.shiro.session.Session
 *  org.apache.shiro.spring.web.ShiroFilterFactoryBean
 *  org.apache.shiro.subject.Subject
 *  org.apache.shiro.util.CollectionUtils
 *  org.apache.shiro.util.Nameable
 *  org.apache.shiro.util.StringUtils
 *  org.apache.shiro.util.ThreadContext
 *  org.apache.shiro.web.config.ShiroFilterConfiguration
 *  org.apache.shiro.web.filter.AccessControlFilter
 *  org.apache.shiro.web.filter.authc.AuthenticationFilter
 *  org.apache.shiro.web.filter.authz.AuthorizationFilter
 *  org.apache.shiro.web.filter.mgt.DefaultFilter
 *  org.apache.shiro.web.filter.mgt.FilterChainManager
 *  org.apache.shiro.web.filter.mgt.FilterChainResolver
 *  org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver
 *  org.apache.shiro.web.mgt.WebSecurityManager
 *  org.apache.shiro.web.servlet.AbstractShiroFilter
 *  org.apache.shiro.web.servlet.OncePerRequestFilter
 *  org.apache.shiro.web.servlet.ShiroHttpServletRequest
 *  org.apache.shiro.web.util.WebUtils
 */
package com.jiuqi.va.shiro.config.optimize;

import com.jiuqi.va.shiro.config.optimize.MyDefaultFilterChainManager;
import com.jiuqi.va.shiro.config.optimize.MyShiroHttpServletRequest;
import com.jiuqi.va.shiro.config.optimize.MyShiroHttpServletResponse;
import com.jiuqi.va.shiro.config.optimize.MyWebUtil;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.CollectionUtils;
import org.apache.shiro.util.Nameable;
import org.apache.shiro.util.StringUtils;
import org.apache.shiro.util.ThreadContext;
import org.apache.shiro.web.config.ShiroFilterConfiguration;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.filter.authc.AuthenticationFilter;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;
import org.apache.shiro.web.filter.mgt.DefaultFilter;
import org.apache.shiro.web.filter.mgt.FilterChainManager;
import org.apache.shiro.web.filter.mgt.FilterChainResolver;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;
import org.apache.shiro.web.mgt.WebSecurityManager;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.apache.shiro.web.servlet.OncePerRequestFilter;
import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.BeanInitializationException;

public class MyShiroFilterFactoryBean
extends ShiroFilterFactoryBean {
    private List<String> globalFilters = new ArrayList<String>();

    public MyShiroFilterFactoryBean() {
        this.globalFilters.add(DefaultFilter.invalidRequest.name());
    }

    public Class getObjectType() {
        return MySpringShiroFilter.class;
    }

    protected AbstractShiroFilter createInstance() throws Exception {
        SecurityManager securityManager = this.getSecurityManager();
        if (securityManager == null) {
            String msg = "SecurityManager property must be set.";
            throw new BeanInitializationException(msg);
        }
        if (!(securityManager instanceof WebSecurityManager)) {
            String msg = "The security manager does not implement the WebSecurityManager interface.";
            throw new BeanInitializationException(msg);
        }
        FilterChainManager manager = this.createFilterChainManager();
        MyPathMatchingFilterChainResolver chainResolver = new MyPathMatchingFilterChainResolver();
        chainResolver.setFilterChainManager(manager);
        return new MySpringShiroFilter((WebSecurityManager)securityManager, (FilterChainResolver)chainResolver, this.getShiroFilterConfiguration());
    }

    private void applyLoginUrlIfNecessary2(Filter filter) {
        AccessControlFilter acFilter;
        String existingLoginUrl;
        String loginUrl = this.getLoginUrl();
        if (StringUtils.hasText((String)loginUrl) && filter instanceof AccessControlFilter && "/login.jsp".equals(existingLoginUrl = (acFilter = (AccessControlFilter)filter).getLoginUrl())) {
            acFilter.setLoginUrl(loginUrl);
        }
    }

    private void applySuccessUrlIfNecessary2(Filter filter) {
        AuthenticationFilter authcFilter;
        String existingSuccessUrl;
        String successUrl = this.getSuccessUrl();
        if (StringUtils.hasText((String)successUrl) && filter instanceof AuthenticationFilter && "/".equals(existingSuccessUrl = (authcFilter = (AuthenticationFilter)filter).getSuccessUrl())) {
            authcFilter.setSuccessUrl(successUrl);
        }
    }

    private void applyUnauthorizedUrlIfNecessary2(Filter filter) {
        AuthorizationFilter authzFilter;
        String existingUnauthorizedUrl;
        String unauthorizedUrl = this.getUnauthorizedUrl();
        if (StringUtils.hasText((String)unauthorizedUrl) && filter instanceof AuthorizationFilter && (existingUnauthorizedUrl = (authzFilter = (AuthorizationFilter)filter).getUnauthorizedUrl()) == null) {
            authzFilter.setUnauthorizedUrl(unauthorizedUrl);
        }
    }

    private void applyGlobalPropertiesIfNecessary2(Filter filter) {
        this.applyLoginUrlIfNecessary2(filter);
        this.applySuccessUrlIfNecessary2(filter);
        this.applyUnauthorizedUrlIfNecessary2(filter);
        if (filter instanceof OncePerRequestFilter) {
            ((OncePerRequestFilter)filter).setFilterOncePerRequest(this.getShiroFilterConfiguration().isFilterOncePerRequest());
        }
    }

    protected FilterChainManager createFilterChainManager() {
        MyDefaultFilterChainManager manager = new MyDefaultFilterChainManager();
        Map defaultFilters = manager.getFilters();
        for (Object filter : defaultFilters.values()) {
            this.applyGlobalPropertiesIfNecessary2((Filter)filter);
        }
        Map filters = this.getFilters();
        if (!CollectionUtils.isEmpty((Map)filters)) {
            for (Map.Entry entry : filters.entrySet()) {
                String name = (String)entry.getKey();
                Filter filter = (Filter)entry.getValue();
                this.applyGlobalPropertiesIfNecessary2(filter);
                if (filter instanceof Nameable) {
                    ((Nameable)filter).setName(name);
                }
                manager.addFilter(name, filter, false);
            }
        }
        manager.setGlobalFilters(this.globalFilters);
        Map chains = this.getFilterChainDefinitionMap();
        if (!CollectionUtils.isEmpty((Map)chains)) {
            for (Map.Entry entry : chains.entrySet()) {
                String url = (String)entry.getKey();
                String chainDefinition = (String)entry.getValue();
                manager.createChain(url, chainDefinition);
            }
        }
        manager.createDefaultChain("/**");
        return manager;
    }

    private static final class MyPathMatchingFilterChainResolver
    extends PathMatchingFilterChainResolver {
        private MyPathMatchingFilterChainResolver() {
        }

        protected String getPathWithinApplication(ServletRequest request) {
            return MyWebUtil.getPathWithinApplication(WebUtils.toHttp((ServletRequest)request));
        }
    }

    private static final class MySpringShiroFilter
    extends AbstractShiroFilter {
        protected MySpringShiroFilter(WebSecurityManager webSecurityManager, FilterChainResolver resolver, ShiroFilterConfiguration filterConfiguration) {
            if (webSecurityManager == null) {
                throw new IllegalArgumentException("WebSecurityManager property cannot be null.");
            }
            this.setSecurityManager(webSecurityManager);
            this.setShiroFilterConfiguration(filterConfiguration);
            if (resolver != null) {
                this.setFilterChainResolver(resolver);
            }
        }

        protected ServletRequest wrapServletRequest(HttpServletRequest orig) {
            return new MyShiroHttpServletRequest(orig, this.getServletContext(), this.isHttpSessions());
        }

        protected ServletResponse wrapServletResponse(HttpServletResponse orig, ShiroHttpServletRequest request) {
            return new MyShiroHttpServletResponse(orig, this.getServletContext(), request);
        }

        protected void updateSessionLastAccessTime(ServletRequest request, ServletResponse response) {
            Subject subject = ThreadContext.getSubject();
            if (subject == null) {
                return;
            }
            Session session = subject.getSession(false);
            if (session == null) {
                return;
            }
            try {
                session.touch();
            }
            catch (Throwable throwable) {
                // empty catch block
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        protected void executeChain(ServletRequest request, ServletResponse response, FilterChain origChain) throws IOException, ServletException {
            try {
                FilterChain chain = this.getExecutionChain(request, response, origChain);
                chain.doFilter(request, response);
            }
            finally {
                ThreadContext.remove();
            }
        }
    }
}

