/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletRequest
 *  org.apache.shiro.mgt.SecurityManager
 *  org.apache.shiro.session.mgt.eis.SessionDAO
 *  org.apache.shiro.util.ThreadContext
 */
package com.jiuqi.va.shiro.config.extend;

import com.jiuqi.va.shiro.config.extend.MyShiroAuthenticationAdaptor;
import java.io.Serializable;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.util.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class MyShiroAuthenticationAdaptorRunner {
    @Autowired(required=false)
    private List<MyShiroAuthenticationAdaptor> adaptorList;
    @Autowired
    private SessionDAO sessionDAO;
    @Autowired
    private SecurityManager securityManager;

    public String execute(HttpServletRequest request) {
        if (this.adaptorList == null || this.adaptorList.isEmpty()) {
            return null;
        }
        SecurityManager sManager = ThreadContext.getSecurityManager();
        if (sManager == null) {
            ThreadContext.bind((SecurityManager)this.securityManager);
        }
        StringBuilder sBuilder = new StringBuilder();
        String tokenid = null;
        for (MyShiroAuthenticationAdaptor adaptor : this.adaptorList) {
            try {
                tokenid = adaptor.getTokenId(request);
                if (!StringUtils.hasLength(tokenid)) continue;
                this.sessionDAO.readSession((Serializable)((Object)tokenid));
                break;
            }
            catch (Exception e) {
                tokenid = null;
                sBuilder.append("[").append(e.getMessage()).append("]");
            }
        }
        if (sBuilder.length() > 0) {
            request.setAttribute("javax.servlet.error.message", (Object)sBuilder.toString());
        }
        return tokenid;
    }
}

