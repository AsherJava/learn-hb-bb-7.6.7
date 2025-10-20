/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.context.ContextIdentity
 *  com.jiuqi.np.core.context.ContextUser
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.core.context.impl.NpContextIdentity
 *  com.jiuqi.np.core.context.impl.NpContextImpl
 *  com.jiuqi.np.core.context.impl.NpContextUser
 *  com.jiuqi.np.user.User
 *  com.jiuqi.np.user.service.UserService
 */
package com.jiuqi.gcreport.oauth2.service.impl;

import com.jiuqi.gcreport.oauth2.exception.UserNotFoundException;
import com.jiuqi.gcreport.oauth2.service.BindUserContextService;
import com.jiuqi.np.core.context.ContextIdentity;
import com.jiuqi.np.core.context.ContextUser;
import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.core.context.impl.NpContextIdentity;
import com.jiuqi.np.core.context.impl.NpContextImpl;
import com.jiuqi.np.core.context.impl.NpContextUser;
import com.jiuqi.np.user.User;
import com.jiuqi.np.user.service.UserService;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BindUserContextServiceImpl
implements BindUserContextService {
    @Autowired
    private UserService<User> userService;

    @Override
    public void bindUser(String username) {
        NpContextHolder.clearContext();
        NpContextImpl npContext = (NpContextImpl)NpContextHolder.createEmptyContext();
        npContext.setTenant("__default_tenant__");
        NpContextUser ctxUser = new NpContextUser();
        User user = this.userService.getByUsername(username);
        if (!Objects.nonNull(user)) {
            throw new UserNotFoundException("\u7ed1\u5b9a\u4e0a\u4e0b\u6587\uff0c\u7528\u6237[" + username + "]\u5728\u4e1a\u52a1\u7cfb\u7edf\u4e2d\u4e0d\u5b58\u5728\uff0c\u8bf7\u8054\u7cfb\u7ba1\u7406\u5458\u5904\u7406\u3002");
        }
        ctxUser.setId(user.getId());
        ctxUser.setName(user.getName());
        ctxUser.setNickname(user.getFullname());
        ctxUser.setDescription(user.getDescription());
        npContext.setUser((ContextUser)ctxUser);
        NpContextIdentity identity = new NpContextIdentity();
        identity.setId(ctxUser.getId());
        identity.setTitle(ctxUser.getFullname());
        npContext.setIdentity((ContextIdentity)identity);
        NpContextHolder.setContext((NpContext)npContext);
    }

    @Override
    public void unbind() {
        NpContextHolder.clearContext();
    }
}

