/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.context.ContextExtension
 *  com.jiuqi.np.core.context.ContextIdentity
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.va.domain.user.UserLoginDTO
 *  com.jiuqi.va.mapper.common.VaContextPorvider
 *  com.jiuqi.va.mapper.domain.TenantDO
 */
package com.jiuqi.nvwa.login.shiro;

import com.jiuqi.np.core.context.ContextExtension;
import com.jiuqi.np.core.context.ContextIdentity;
import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.va.domain.user.UserLoginDTO;
import com.jiuqi.va.mapper.common.VaContextPorvider;
import com.jiuqi.va.mapper.domain.TenantDO;
import org.springframework.stereotype.Component;

@Component
public class MyContextProvider
implements VaContextPorvider {
    public TenantDO getContext() {
        NpContext npContext = NpContextHolder.getContext();
        if (npContext != null && npContext.getUserName() != null) {
            ContextIdentity contextIdentity;
            ContextExtension ctxExt;
            UserLoginDTO userLoginDTO = new UserLoginDTO();
            userLoginDTO.setTenantName(npContext.getTenant());
            userLoginDTO.setUsername(npContext.getUserName());
            userLoginDTO.setLoginDate(npContext.getLoginDate());
            if (npContext.getOrganization() != null) {
                userLoginDTO.setLoginUnit(npContext.getOrganization().getCode());
            }
            if ((ctxExt = npContext.getDefaultExtension()) != null) {
                ctxExt.apply(entry -> userLoginDTO.addExtInfo((String)entry.getKey(), entry.getValue()));
            }
            userLoginDTO.setId(npContext.getUserId());
            if (npContext.getUserId().startsWith("sys_user_")) {
                userLoginDTO.setMgrFlag("super");
            }
            if ((contextIdentity = npContext.getIdentity()) != null) {
                userLoginDTO.addExtInfo("contextIdentity", (Object)contextIdentity.getId());
            }
            return userLoginDTO;
        }
        return null;
    }

    public void cleanContext() {
        NpContextHolder.clearContext();
    }
}

