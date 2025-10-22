/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.authz2.service.SystemIdentityService
 *  com.jiuqi.np.core.context.ContextUser
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.nr.definition.internal.BeanUtil
 */
package com.jiuqi.nr.unit.treecommon.utils;

import com.jiuqi.np.authz2.service.SystemIdentityService;
import com.jiuqi.np.core.context.ContextUser;
import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.nr.definition.internal.BeanUtil;

public class NRSystemUtils {
    private NRSystemUtils() {
    }

    public static boolean isSystemIdentity(String userId) {
        SystemIdentityService identityService = (SystemIdentityService)BeanUtil.getBean(SystemIdentityService.class);
        return identityService.isSystemIdentity(userId);
    }

    public static String getCurrentUserId() {
        ContextUser user = NRSystemUtils.getCurrentUser();
        if (user != null) {
            return user.getId();
        }
        return null;
    }

    public static ContextUser getCurrentUser() {
        NpContext context = NpContextHolder.getContext();
        return context.getUser();
    }
}

