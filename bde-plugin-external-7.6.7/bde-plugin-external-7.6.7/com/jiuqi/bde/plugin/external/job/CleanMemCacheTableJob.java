/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.JobContext
 *  com.jiuqi.np.authz2.service.RoleService
 *  com.jiuqi.np.authz2.service.SystemIdentityService
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.log.BeanUtils
 *  com.jiuqi.nvwa.jobmanager.sysjob.AbstractSysJob
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 */
package com.jiuqi.bde.plugin.external.job;

import com.jiuqi.bde.plugin.external.service.CleanMemCacheTableService;
import com.jiuqi.bi.core.jobs.JobContext;
import com.jiuqi.np.authz2.service.RoleService;
import com.jiuqi.np.authz2.service.SystemIdentityService;
import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.log.BeanUtils;
import com.jiuqi.nvwa.jobmanager.sysjob.AbstractSysJob;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import java.util.Set;

public class CleanMemCacheTableJob
extends AbstractSysJob {
    public static final String ID = "com.jiuqi.bde.memcache.clean";
    public static final String TITLE = "\u3010BDE\u3011\u6e05\u7406\u70ed\u70b9\u6570\u636e\u4efb\u52a1";

    public String getId() {
        return ID;
    }

    public String getTitle() {
        return TITLE;
    }

    public boolean editable() {
        SystemIdentityService systemIdentityService = (SystemIdentityService)BeanUtils.getBean(SystemIdentityService.class);
        RoleService roleService = (RoleService)BeanUtils.getBean(RoleService.class);
        NpContext npContext = NpContextHolder.getContext();
        String identityId = npContext.getIdentityId();
        Set roles = roleService.getIdByIdentity(identityId);
        return systemIdentityService.isAdmin() || roles != null && roles.contains("ffffffff-ffff-ffff-sssd-ffffffffffff");
    }

    public void exec(JobContext context, String config) throws Exception {
        ((CleanMemCacheTableService)ApplicationContextRegister.getBean(CleanMemCacheTableService.class)).doClean(context.getDefaultLogger());
    }

    public String getModelName() {
        return "";
    }
}

