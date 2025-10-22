/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.JobContext
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.plantask.extend.job.PlanTaskRunner
 *  com.jiuqi.common.plantask.extend.job.Runner
 *  com.jiuqi.gcreport.reportdatasync.api.MultilevelSyncClient
 *  com.jiuqi.gcreport.reportdatasync.enums.SyncTypeEnums
 *  com.jiuqi.gcreport.reportdatasync.vo.MultilevelSyncVO
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
 *  com.jiuqi.util.StringUtils
 */
package com.jiuqi.gcreport.reportdatasync.runner;

import com.jiuqi.bi.core.jobs.JobContext;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.plantask.extend.job.PlanTaskRunner;
import com.jiuqi.common.plantask.extend.job.Runner;
import com.jiuqi.gcreport.reportdatasync.api.MultilevelSyncClient;
import com.jiuqi.gcreport.reportdatasync.enums.SyncTypeEnums;
import com.jiuqi.gcreport.reportdatasync.runner.param.MultilevelUploadDataRunnerParam;
import com.jiuqi.gcreport.reportdatasync.vo.MultilevelSyncVO;
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
import com.jiuqi.util.StringUtils;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

@Component(value="multilevelUploadDataRunner")
@PlanTaskRunner(id="dc99fdbb1280499c821a6927db8cbed8", name="multilevelUploadDataRunner", title="\u591a\u7ea7\u90e8\u7f72\u6570\u636e\u4e0a\u4f20\u8ba1\u5212\u4efb\u52a1", settingPage="multilevelUploadDataConfig")
public class MultilevelUploadDataRunner
extends Runner {
    public static final String ID = "dc99fdbb1280499c821a6927db8cbed8";
    public static final String NAME = "multilevelUploadDataRunner";
    public static final String TITLE = "\u591a\u7ea7\u90e8\u7f72\u6570\u636e\u4e0a\u4f20\u8ba1\u5212\u4efb\u52a1";
    public static final String SETTING_PAGE = "multilevelUploadDataConfig";
    @Autowired
    private UserService<User> userService;
    @Autowired
    private SystemUserService systemUserService;
    @Autowired
    private MultilevelSyncClient syncClient;

    public boolean excute(JobContext jobContext) {
        boolean isSuccess;
        String runnerParameter = jobContext.getJob().getExtendedConfig();
        if (ObjectUtils.isEmpty(runnerParameter)) {
            this.appendLog("\u672a\u914d\u7f6e".concat(TITLE).concat("\u8ba1\u5212\u4efb\u52a1\u53c2\u6570\u3002\n"));
            return false;
        }
        this.appendLog("\u6267\u884c\u53c2\u6570\u8be6\u60c5\u3002\n");
        this.appendLog(runnerParameter + "\n");
        MultilevelUploadDataRunnerParam runnerParam = (MultilevelUploadDataRunnerParam)JsonUtils.readValue((String)runnerParameter, MultilevelUploadDataRunnerParam.class);
        String uploadSchemeId = runnerParam.getUploadSchemeId();
        if (StringUtils.isEmpty((String)uploadSchemeId)) {
            this.appendLog("\u672a\u9009\u62e9\u6570\u636e\u4e0a\u4f20\u65b9\u6848");
            return false;
        }
        String userName = jobContext.getJob().getUser();
        if (StringUtils.isEmpty((String)userName)) {
            this.appendLog("\u8ba1\u5212\u4efb\u52a1\u6267\u884c\u5931\u8d25\uff0c\u672a\u8bbe\u7f6e\u7528\u6237\u4fe1\u606f");
            return false;
        }
        User user = this.getUserByUserName(userName);
        if (user == null) {
            this.appendLog("\u8ba1\u5212\u4efb\u52a1\u6267\u884c\u5931\u8d25\uff0c\u6267\u884c\u7684\u7528\u6237\u540d" + userName + "\u4e0d\u53ef\u7528\n");
            return false;
        }
        NpContextHolder.setContext((NpContext)this.buildContext(userName));
        try {
            MultilevelSyncVO syncVO = new MultilevelSyncVO();
            syncVO.setType(SyncTypeEnums.REPORTDATA.getCode());
            syncVO.setInfo(uploadSchemeId);
            isSuccess = (Boolean)this.syncClient.multilevelSync(syncVO).getData();
            this.appendLog("\u6570\u636e\u4e0a\u4f20\u6210\u529f");
        }
        catch (Exception e) {
            this.appendLog("\u6570\u636e\u4e0a\u4f20\u5931\u8d25\uff0c\u539f\u56e0" + e.getMessage());
            isSuccess = false;
        }
        return isSuccess;
    }

    private User getUserByUserName(String userName) {
        if (com.jiuqi.bi.util.StringUtils.isEmpty((String)userName)) {
            return null;
        }
        Optional user = this.userService.findByUsername(userName);
        if (user.isPresent()) {
            return (User)user.get();
        }
        Optional sysUser = this.systemUserService.findByUsername(userName);
        if (sysUser.isPresent()) {
            return (User)sysUser.get();
        }
        return null;
    }

    private NpContextUser buildUserContext(String userName) {
        NpContextUser userContext = new NpContextUser();
        User user = this.getUserByUserName(userName);
        if (user == null) {
            throw new BusinessRuntimeException("\u67e5\u8be2\u4e0d\u5230\u7528\u6237\u4fe1\u606f");
        }
        userContext.setId(user.getId());
        userContext.setName(user.getName());
        userContext.setNickname(user.getNickname());
        userContext.setDescription(user.getDescription());
        return userContext;
    }

    private NpContextIdentity buildIdentityContext(NpContextUser contextUser) {
        NpContextIdentity identity = new NpContextIdentity();
        identity.setId(contextUser.getId());
        identity.setTitle(contextUser.getFullname());
        return identity;
    }

    private NpContextImpl buildContext(String userName) {
        NpContextImpl npContext = (NpContextImpl)NpContextHolder.createEmptyContext();
        npContext.setTenant("__default_tenant__");
        NpContextUser contextUser = this.buildUserContext(userName);
        npContext.setUser((ContextUser)contextUser);
        NpContextIdentity identity = this.buildIdentityContext(contextUser);
        npContext.setIdentity((ContextIdentity)identity);
        return npContext;
    }
}

