/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.user.UserLoginDTO
 *  org.apache.shiro.authc.AuthenticationException
 *  org.apache.shiro.authc.AuthenticationInfo
 *  org.apache.shiro.authc.AuthenticationToken
 *  org.apache.shiro.authc.SimpleAuthenticationInfo
 *  org.apache.shiro.authz.AuthorizationInfo
 *  org.apache.shiro.authz.Permission
 *  org.apache.shiro.realm.AuthorizingRealm
 *  org.apache.shiro.subject.PrincipalCollection
 */
package com.jiuqi.nvwa.login.shiro;

import com.jiuqi.nvwa.login.domain.NvwaContext;
import com.jiuqi.nvwa.login.shiro.MyAuthenticationToken;
import com.jiuqi.nvwa.login.shiro.MySimpleAuthorizationInfo;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.user.UserLoginDTO;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.Permission;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

public class MyUserRealm
extends AuthorizingRealm {
    private boolean enablePermissions;

    public MyUserRealm(boolean enablePermissions) {
        this.enablePermissions = enablePermissions;
    }

    public boolean supports(AuthenticationToken token) {
        return token.getClass().getName().equals(MyAuthenticationToken.class.getName());
    }

    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        Object obj = principals.getPrimaryPrincipal();
        UserLoginDTO user = (UserLoginDTO)JSONUtil.parseObject((String)((String)obj), UserLoginDTO.class);
        MySimpleAuthorizationInfo info = new MySimpleAuthorizationInfo();
        info.setStringPermissions(user.getPerms());
        info.setMgrFlag(user.getMgrFlag());
        return info;
    }

    protected boolean isPermitted(Permission permission, AuthorizationInfo info) {
        if (this.enablePermissions) {
            MySimpleAuthorizationInfo myInfo;
            String mgrFlag;
            if (info instanceof MySimpleAuthorizationInfo && "super".equals(mgrFlag = (myInfo = (MySimpleAuthorizationInfo)info).getMgrFlag())) {
                return true;
            }
            return super.isPermitted(permission, info);
        }
        return true;
    }

    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        MyAuthenticationToken myToken = (MyAuthenticationToken)token;
        NvwaContext context = myToken.getContext();
        Object pwd = myToken.getCredentials();
        return new SimpleAuthenticationInfo((Object)JSONUtil.toJSONString((Object)context, (String)"##long"), pwd, this.getName());
    }
}

