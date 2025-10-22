/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.nodekeeper.DistributionManager
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.core.context.ContextIdentity
 *  com.jiuqi.np.core.context.ContextUser
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.core.context.impl.NpContextIdentity
 *  com.jiuqi.np.core.context.impl.NpContextImpl
 *  com.jiuqi.np.core.context.impl.NpContextUser
 *  com.jiuqi.np.user.User
 *  com.jiuqi.np.user.service.SystemUserService
 *  com.jiuqi.np.user.service.UserService
 *  javax.servlet.http.HttpServletRequest
 *  org.springframework.web.context.request.RequestAttributes
 *  org.springframework.web.context.request.RequestContextHolder
 *  org.springframework.web.context.request.ServletRequestAttributes
 */
package com.jiuqi.gcreport.financialcheckImpl.taskscheduling;

import com.jiuqi.bi.core.nodekeeper.DistributionManager;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.core.context.ContextIdentity;
import com.jiuqi.np.core.context.ContextUser;
import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.core.context.impl.NpContextIdentity;
import com.jiuqi.np.core.context.impl.NpContextImpl;
import com.jiuqi.np.core.context.impl.NpContextUser;
import com.jiuqi.np.user.User;
import com.jiuqi.np.user.service.SystemUserService;
import com.jiuqi.np.user.service.UserService;
import java.util.Date;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
public class UserInfoUtil {
    private static final String IP_HEADER_FORWARDED_FOR = "X-Forwarded-For";
    private static final String IP_HEADER_REMOTE_ADDR = "X-Real-IP";
    @Autowired
    private UserService<User> userService;
    @Value(value="${jiuqi.np.user.system[0].name:admin}")
    protected String adminName;
    @Autowired
    private SystemUserService systemUserService;

    public void putUserInfoToNpContext(String username) {
        NpContextHolder.setContext((NpContext)this.getNpContext(username));
    }

    public void putUserInfoToNpContext() {
        NpContextHolder.setContext((NpContext)this.getNpContext(this.adminName));
    }

    private NpContext getNpContext(String userName) {
        User user = this.getUserByUserName(userName);
        NpContextImpl context = (NpContextImpl)NpContextHolder.createEmptyContext();
        context.setTenant("__default_tenant__");
        context.setIdentity(this.getContextIdentity(user));
        context.setUser(this.getContextUser(user));
        context.setIp(UserInfoUtil.getClientIpAddress(RequestContextHolder.getRequestAttributes()));
        context.setLocale(LocaleContextHolder.getLocale());
        context.setLoginDate(new Date());
        return context;
    }

    private ContextIdentity getContextIdentity(User user) {
        NpContextIdentity idEntity = new NpContextIdentity();
        idEntity.setId(user.getId());
        idEntity.setTitle(user.getFullname());
        idEntity.setOrgCode(user.getOrgCode());
        return idEntity;
    }

    private ContextUser getContextUser(User user) {
        NpContextUser userContext = new NpContextUser();
        userContext.setId(user.getId());
        userContext.setName(user.getName());
        userContext.setOrgCode(user.getOrgCode());
        userContext.setNickname(user.getNickname());
        userContext.setDescription(user.getDescription());
        return userContext;
    }

    private static String getClientIpAddress(RequestAttributes requestAttr) {
        if (!(requestAttr instanceof ServletRequestAttributes)) {
            return DistributionManager.getInstance().self().getIp();
        }
        ServletRequestAttributes attributes = (ServletRequestAttributes)requestAttr;
        HttpServletRequest request = attributes.getRequest();
        String requestHeader = request.getHeader(IP_HEADER_FORWARDED_FOR);
        if (StringUtils.isNotEmpty((String)requestHeader) && !"unknown".equalsIgnoreCase(requestHeader)) {
            int index = requestHeader.indexOf(",");
            if (index != -1) {
                return requestHeader.substring(0, index);
            }
            return requestHeader;
        }
        requestHeader = request.getHeader(IP_HEADER_REMOTE_ADDR);
        if (StringUtils.isNotEmpty((String)requestHeader) && !"unknown".equalsIgnoreCase(requestHeader)) {
            return requestHeader;
        }
        return request.getRemoteAddr();
    }

    private User getUserByUserName(String userName) {
        Optional user = this.userService.findByUsername(userName);
        if (user.isPresent()) {
            return (User)user.get();
        }
        Optional sysUser = this.systemUserService.findByUsername(userName);
        return sysUser.orElse(null);
    }
}

