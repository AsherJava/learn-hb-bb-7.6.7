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
package com.jiuqi.dc.integration.execute.impl.basedatasync.job;

import com.jiuqi.bi.core.jobs.JobContext;
import com.jiuqi.dc.integration.execute.impl.basedatasync.runner.DcBaseDataSyncRunner;
import com.jiuqi.np.authz2.service.RoleService;
import com.jiuqi.np.authz2.service.SystemIdentityService;
import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.log.BeanUtils;
import com.jiuqi.nvwa.jobmanager.sysjob.AbstractSysJob;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import java.util.Set;

public class DcBaseDataSyncJob
extends AbstractSysJob {
    public static final String ID = "com.jiuqi.dc.basedatasync";
    public static final String TITLE = "\u3010\u5408\u5e76\u591a\u7ef4\u3011\u57fa\u7840\u6570\u636e\u540c\u6b65\u4efb\u52a1";

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
        ((DcBaseDataSyncRunner)((Object)ApplicationContextRegister.getBean(DcBaseDataSyncRunner.class))).excute(config);
    }

    public String getModelName() {
        return "";
    }
}

