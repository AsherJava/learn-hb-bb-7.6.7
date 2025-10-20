/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.EnvConfig
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 *  javax.servlet.http.Cookie
 *  javax.servlet.http.HttpServletRequest
 *  org.apache.shiro.session.InvalidSessionException
 *  org.apache.shiro.session.Session
 *  org.apache.shiro.session.UnknownSessionException
 *  org.apache.shiro.session.mgt.DefaultSessionManager
 *  org.apache.shiro.session.mgt.SessionFactory
 *  org.apache.shiro.session.mgt.SessionKey
 *  org.apache.shiro.session.mgt.SessionValidationScheduler
 *  org.apache.shiro.session.mgt.SimpleSessionFactory
 *  org.apache.shiro.util.AntPathMatcher
 *  org.apache.shiro.util.PatternMatcher
 *  org.apache.shiro.util.StringUtils
 *  org.apache.shiro.web.session.mgt.WebSessionManager
 *  org.apache.shiro.web.util.WebUtils
 */
package com.jiuqi.va.shiro.config;

import com.jiuqi.va.domain.common.EnvConfig;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import com.jiuqi.va.shiro.config.VaAuthShiroConfig;
import com.jiuqi.va.shiro.config.extend.MyShiroAuthenticationAdaptorRunner;
import com.jiuqi.va.shiro.config.optimize.MyWebUtil;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.DefaultSessionManager;
import org.apache.shiro.session.mgt.SessionFactory;
import org.apache.shiro.session.mgt.SessionKey;
import org.apache.shiro.session.mgt.SessionValidationScheduler;
import org.apache.shiro.session.mgt.SimpleSessionFactory;
import org.apache.shiro.util.AntPathMatcher;
import org.apache.shiro.util.PatternMatcher;
import org.apache.shiro.util.StringUtils;
import org.apache.shiro.web.session.mgt.WebSessionManager;
import org.apache.shiro.web.util.WebUtils;

public class MySessionManager
extends DefaultSessionManager
implements WebSessionManager {
    private static final String AUTHORIZATION = "Authorization";
    private MyShiroAuthenticationAdaptorRunner adaptorRunner = null;
    private PatternMatcher pathMatcher = null;
    private Set<String> anonPathRule = null;
    private int sessionDelayUpdateTime = 30000;
    private String sessionidRequestParameter = "nv_t";
    private boolean cookieEnabled = false;

    public MySessionManager() {
        this.setSessionFactory((SessionFactory)new SimpleSessionFactory());
        this.setDeleteInvalidSessions(true);
        this.pathMatcher = new AntPathMatcher();
        this.anonPathRule = new HashSet<String>();
    }

    public void addAnonPathRule(String pathRule) {
        this.anonPathRule.add(pathRule);
    }

    public Set<String> getAnonPathRule() {
        return this.anonPathRule;
    }

    public void setPathMatcher(PatternMatcher pathMatcher) {
        this.pathMatcher = pathMatcher;
    }

    public void setDelayUpdateTime(int sessionDelayUpdateTime) {
        this.sessionDelayUpdateTime = sessionDelayUpdateTime;
    }

    public void setSessionidRequestParameter(String sessionidRequestParameter) {
        this.sessionidRequestParameter = sessionidRequestParameter;
    }

    public void setCookieEnabled(boolean cookieEnable) {
        this.cookieEnabled = cookieEnable;
    }

    public SessionValidationScheduler getSessionValidationScheduler() {
        if (EnvConfig.getRedisEnable()) {
            return new SessionValidationScheduler(){

                public boolean isEnabled() {
                    return true;
                }

                public void enableSessionValidation() {
                }

                public void disableSessionValidation() {
                }
            };
        }
        return this.sessionValidationScheduler;
    }

    protected Session retrieveSession(SessionKey sessionKey) throws UnknownSessionException {
        Object sessionid = sessionKey.getSessionId();
        if (sessionid == null && WebUtils.isWeb((Object)sessionKey)) {
            Cookie[] cookies;
            HttpServletRequest request = WebUtils.getHttpRequest((Object)sessionKey);
            boolean isAnon = false;
            String contextPath = request.getContextPath();
            String uri = request.getRequestURI();
            if (StringUtils.hasText((String)contextPath) && !contextPath.equals("/")) {
                uri = uri.substring(contextPath.length());
            }
            if (uri.contains("/anon/") || uri.startsWith("/sso/")) {
                isAnon = true;
            } else if (uri.contains("/authc/")) {
                isAnon = false;
            } else {
                for (String string : this.anonPathRule) {
                    if (string.contains("/anon/") || string.startsWith("/sso/")) continue;
                    if (!string.contains("*") && string.equals(uri)) {
                        isAnon = true;
                        break;
                    }
                    if (!this.pathMatcher.matches(string, uri)) continue;
                    isAnon = true;
                    break;
                }
            }
            if (isAnon) {
                String trustCode;
                if (!(!"true".equals(request.getHeader("FeignClient")) || StringUtils.hasText((String)(trustCode = request.getHeader("TrustCode"))) && trustCode.equals(VaAuthShiroConfig.getTrustCode()))) {
                    throw new UnknownSessionException("403 Request Forbidden");
                }
                ShiroUtil.cleanContext();
                return null;
            }
            sessionid = request.getHeader(AUTHORIZATION);
            if (sessionid == null) {
                sessionid = request.getParameter(this.sessionidRequestParameter);
            }
            if (sessionid == null) {
                sessionid = request.getParameter("JTOKENID");
            }
            if (sessionid == null) {
                sessionid = request.getParameter("jtokenid");
            }
            if (this.cookieEnabled && (sessionid == null || sessionid.toString().isEmpty()) && (cookies = request.getCookies()) != null) {
                for (Cookie cookie : cookies) {
                    if (!cookie.getName().equals(this.sessionidRequestParameter)) continue;
                    sessionid = cookie.getValue();
                    break;
                }
            }
            StringBuilder sBuilder = new StringBuilder();
            sBuilder.append("Could not find session with ID [");
            if (sessionid != null) {
                try {
                    sBuilder.append(sessionid);
                    Session session = this.sessionDAO.readSession((Serializable)sessionid);
                    this.doValidate(session);
                    return session;
                }
                catch (InvalidSessionException invalidSessionException) {
                    sessionid = null;
                }
            }
            if (sessionid == null) {
                if (this.adaptorRunner == null) {
                    this.adaptorRunner = (MyShiroAuthenticationAdaptorRunner)ApplicationContextRegister.getBean(MyShiroAuthenticationAdaptorRunner.class);
                }
                sessionid = this.adaptorRunner.execute(request);
            }
            if (sessionid != null) {
                try {
                    sBuilder.append(",  ").append(sessionid);
                    Session session = this.sessionDAO.readSession((Serializable)sessionid);
                    this.doValidate(session);
                    return session;
                }
                catch (InvalidSessionException invalidSessionException) {
                    sessionid = null;
                }
            }
            if (sessionid == null) {
                sBuilder.append("]");
                throw new UnknownSessionException(sBuilder.toString());
            }
        }
        if (sessionid == null) {
            return null;
        }
        Session session = this.retrieveSessionFromDataSource((Serializable)sessionid);
        if (session == null) {
            String msg = "Could not find session with ID [" + sessionid + "]";
            throw new UnknownSessionException(msg);
        }
        return session;
    }

    protected void applyGlobalSessionTimeout(Session session) {
        if (!EnvConfig.getRedisEnable()) {
            session.setTimeout(this.getGlobalSessionTimeout());
            this.onChange(session);
        }
    }

    public boolean isServletContainerSessions() {
        return true;
    }

    public void touch(SessionKey key) throws InvalidSessionException {
        if (key == null) {
            throw new NullPointerException("SessionKey argument cannot be null.");
        }
        Session session = this.retrieveSession(key);
        if (session == null) {
            throw new UnknownSessionException("Unable to locate required Session instance based on SessionKey [" + key + "].");
        }
        long diffTime = System.currentTimeMillis() - session.getLastAccessTime().getTime();
        if (diffTime > (long)this.sessionDelayUpdateTime) {
            session.touch();
            this.onChange(session);
        }
    }

    protected void onChange(Session session) {
        HttpServletRequest request = MyWebUtil.getRequest();
        if (request != null) {
            String noRefresh = request.getHeader("noUpSession");
            if (!StringUtils.hasText((String)noRefresh)) {
                noRefresh = request.getParameter("noUpSession");
            }
            if (StringUtils.hasText((String)noRefresh) && "true".equalsIgnoreCase(noRefresh)) {
                return;
            }
        }
        this.sessionDAO.update(session);
    }
}

