/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.JobContext
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.core.context.ContextIdentity
 *  com.jiuqi.np.core.context.ContextUser
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.core.context.impl.NpContextIdentity
 *  com.jiuqi.np.core.context.impl.NpContextImpl
 *  com.jiuqi.np.core.context.impl.NpContextUser
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.np.user.User
 *  com.jiuqi.np.user.internal.service.UserServieImpl
 *  com.jiuqi.np.user.service.SystemUserService
 *  com.jiuqi.np.user.service.UserService
 *  com.jiuqi.nvwa.jobmanager.exception.PlanTaskError
 */
package com.jiuqi.common.plantask.extend.utils;

import com.jiuqi.bi.core.jobs.JobContext;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.core.context.ContextIdentity;
import com.jiuqi.np.core.context.ContextUser;
import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.core.context.impl.NpContextIdentity;
import com.jiuqi.np.core.context.impl.NpContextImpl;
import com.jiuqi.np.core.context.impl.NpContextUser;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.np.user.User;
import com.jiuqi.np.user.internal.service.UserServieImpl;
import com.jiuqi.np.user.service.SystemUserService;
import com.jiuqi.np.user.service.UserService;
import com.jiuqi.nvwa.jobmanager.exception.PlanTaskError;
import java.util.Objects;
import java.util.Optional;

public class ChangeContextUtils {
    private static UserService userService;
    private static SystemUserService systemUserService;

    public static void buildContext(JobContext jobContext) throws JQException {
        ChangeContextUtils.initUserBean();
        String userName = jobContext.getJob().getUser();
        NpContextImpl npContext = (NpContextImpl)NpContextHolder.createEmptyContext();
        npContext.setTenant("__default_tenant__");
        NpContextUser contextUser = ChangeContextUtils.buildUserContext(userName);
        npContext.setUser((ContextUser)contextUser);
        NpContextIdentity identity = ChangeContextUtils.buildIdentityContext(contextUser);
        npContext.setIdentity((ContextIdentity)identity);
        NpContextHolder.setContext((NpContext)npContext);
    }

    private static NpContextUser buildUserContext(String userName) throws JQException {
        NpContextUser userContext = new NpContextUser();
        User user = ChangeContextUtils.getUserByUserName(userName);
        if (user == null) {
            throw new JQException((ErrorEnum)PlanTaskError.QUERY_USER);
        }
        userContext.setId(user.getId());
        userContext.setName(user.getName());
        userContext.setNickname(user.getNickname());
        userContext.setDescription(user.getDescription());
        return userContext;
    }

    private static User getUserByUserName(String userName) {
        if (StringUtils.isEmpty((String)userName)) {
            return null;
        }
        Optional user = userService.findByUsername(userName);
        if (user.isPresent()) {
            return (User)user.get();
        }
        Optional sysUser = systemUserService.findByUsername(userName);
        return sysUser.orElse(null);
    }

    private static NpContextIdentity buildIdentityContext(NpContextUser contextUser) {
        NpContextIdentity identity = new NpContextIdentity();
        identity.setId(contextUser.getId());
        identity.setTitle(contextUser.getFullname());
        return identity;
    }

    private static void initUserBean() {
        if (Objects.isNull(userService)) {
            userService = (UserService)SpringBeanUtils.getBean(UserServieImpl.class);
        }
        if (Objects.isNull(systemUserService)) {
            systemUserService = (SystemUserService)SpringBeanUtils.getBean(SystemUserService.class);
        }
    }
}

