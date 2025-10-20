/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.context.ContextExtension
 *  com.jiuqi.np.core.context.ContextIdentity
 *  com.jiuqi.np.core.context.ContextOrganization
 *  com.jiuqi.np.core.context.ContextUser
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.core.context.impl.NpContextImpl
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.feign.util.RequestContextUtil
 *  com.jiuqi.va.shiro.config.MyShiroAfterFilter
 *  org.apache.shiro.SecurityUtils
 *  org.apache.shiro.util.ThreadContext
 */
package com.jiuqi.nvwa.login.shiro;

import com.jiuqi.np.core.context.ContextExtension;
import com.jiuqi.np.core.context.ContextIdentity;
import com.jiuqi.np.core.context.ContextOrganization;
import com.jiuqi.np.core.context.ContextUser;
import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.core.context.impl.NpContextImpl;
import com.jiuqi.nvwa.login.config.NvwaLoginProperties;
import com.jiuqi.nvwa.login.domain.NvwaContext;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.feign.util.RequestContextUtil;
import com.jiuqi.va.shiro.config.MyShiroAfterFilter;
import java.io.Serializable;
import java.util.Map;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.util.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@Component
public class MyAuthenticationAfterFilter
implements MyShiroAfterFilter {
    @Autowired(required=false)
    private NvwaLoginProperties nvwaLoginProperties;

    public boolean execute() {
        NpContextImpl npContext = new NpContextImpl();
        NvwaContext context = this.getNvwaContext();
        if (context == null) {
            npContext.setTenant((String)ThreadContext.get((Object)"SECURITY_TENANT_KEY"));
            return true;
        }
        npContext.setTenant(context.getTenantName());
        npContext.setLoginDate(context.getLoginDate());
        npContext.setUser((ContextUser)context.getConetxtUser());
        npContext.setOrganization((ContextOrganization)context.getContextOrg());
        npContext.setIdentity((ContextIdentity)context.getContextIdentity());
        npContext.setLocale(LocaleContextHolder.getLocale());
        npContext.setTimeZone(LocaleContextHolder.getTimeZone());
        String ipHeaderName = null;
        if (null != this.nvwaLoginProperties) {
            ipHeaderName = this.nvwaLoginProperties.getClientIpHeader();
        }
        npContext.setIp(RequestContextUtil.getIpAddr(ipHeaderName));
        npContext.setDeviceMsg(RequestContextUtil.getHeader((String)"User-Agent"));
        Map<String, Object> extInfo = context.getExtInfo();
        if (extInfo != null) {
            ContextExtension ctxExt = npContext.getDefaultExtension();
            extInfo.entrySet().stream().forEach(o -> ctxExt.put((String)o.getKey(), (Serializable)((Object)o.getValue().toString())));
        }
        NpContextHolder.setContext((NpContext)npContext);
        return true;
    }

    private NvwaContext getNvwaContext() {
        NvwaContext nvwaCtx = null;
        Object object = null;
        try {
            object = SecurityUtils.getSubject().getPrincipal();
        }
        catch (Exception exception) {
            // empty catch block
        }
        if (object != null) {
            nvwaCtx = (NvwaContext)JSONUtil.parseObject((String)((String)object), NvwaContext.class);
        }
        return nvwaCtx;
    }

    public void executeForFailure() {
        NpContextHolder.clearContext();
    }

    public int getOrderNum() {
        return Integer.MIN_VALUE;
    }
}

