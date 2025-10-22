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
 */
package com.jiuqi.gcreport.reportdatasync.runner;

import com.jiuqi.bi.core.jobs.JobContext;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.plantask.extend.job.PlanTaskRunner;
import com.jiuqi.common.plantask.extend.job.Runner;
import com.jiuqi.gcreport.reportdatasync.runner.param.ReportDataSyncRunnerParam;
import com.jiuqi.gcreport.reportdatasync.runner.service.ReportDataSyncRunnerService;
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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

@Component(value="reportDataSyncRunner")
@PlanTaskRunner(id="666666EE4AE84BBBB53E888B7EC74888", name="reportDataSyncRunner", title="\u56fd\u8d44\u59d4JIO\u62a5\u8868\u6570\u636e\u63a8\u9001\u7b2c\u4e09\u65b9\u8ba1\u5212\u4efb\u52a1", settingPage="reportDataSyncConfig")
public class ReportDataSyncRunner
extends Runner {
    public static final String ID = "666666EE4AE84BBBB53E888B7EC74888";
    public static final String NAME = "reportDataSyncRunner";
    public static final String TITLE = "\u56fd\u8d44\u59d4JIO\u62a5\u8868\u6570\u636e\u63a8\u9001\u7b2c\u4e09\u65b9\u8ba1\u5212\u4efb\u52a1";
    public static final String SETTING_PAGE = "reportDataSyncConfig";
    @Autowired
    private ReportDataSyncRunnerService reportDataSyncRunnerService;
    @Autowired
    private UserService<User> userService;
    @Autowired
    private SystemUserService systemUserService;

    public boolean excute(JobContext jobContext) {
        boolean isSuccess;
        String runnerParameter = jobContext.getJob().getExtendedConfig();
        if (ObjectUtils.isEmpty(runnerParameter)) {
            this.appendLog("\u672a\u914d\u7f6e".concat(TITLE).concat("\u8ba1\u5212\u4efb\u52a1\u53c2\u6570\u3002\n"));
            return false;
        }
        this.appendLog("\u6267\u884c\u53c2\u6570\u8be6\u60c5\u3002\n");
        this.appendLog(runnerParameter + "\n");
        ReportDataSyncRunnerParam reportDataSyncRunnerParam = (ReportDataSyncRunnerParam)JsonUtils.readValue((String)runnerParameter, ReportDataSyncRunnerParam.class);
        if (ObjectUtils.isEmpty(reportDataSyncRunnerParam.getTaskId())) {
            this.appendLog("\u4efb\u52a1\u4e0d\u5141\u8bb8\u4e3a\u7a7a\uff0c\u8bf7\u8054\u7cfb\u7ba1\u7406\u5458\u3002\n");
            return false;
        }
        if (ObjectUtils.isEmpty(reportDataSyncRunnerParam.getPeriodType())) {
            this.appendLog("\u65f6\u671f\u914d\u7f6e\u4e0d\u5141\u8bb8\u4e3a\u7a7a\uff0c\u8bf7\u8054\u7cfb\u7ba1\u7406\u5458\u3002");
            return false;
        }
        if (ObjectUtils.isEmpty(reportDataSyncRunnerParam.getSystemHookName())) {
            this.appendLog("\u63a5\u53e3\u9879\u4e0d\u5141\u8bb8\u4e3a\u7a7a\uff0c\u8bf7\u8054\u7cfb\u7ba1\u7406\u5458\u3002\n");
            return false;
        }
        if (ObjectUtils.isEmpty(reportDataSyncRunnerParam.getPeriodType())) {
            this.appendLog("\u63a5\u53e3\u9879URL\u4e0d\u5141\u8bb8\u4e3a\u7a7a\uff0c\u8bf7\u8054\u7cfb\u7ba1\u7406\u5458\u3002\n");
            return false;
        }
        List<String> logs = Collections.synchronizedList(new ArrayList());
        String userName = jobContext.getJob().getUser();
        try {
            User user = this.getUserByUserName(userName);
            if (user == null) {
                this.appendLog("\u8ba1\u5212\u4efb\u52a1\u6267\u884c\u5931\u8d25\uff0c\u6267\u884c\u7684\u7528\u6237\u540d" + userName + "\u4e0d\u53ef\u7528\n");
                return false;
            }
            NpContextHolder.setContext((NpContext)this.buildContext(userName));
            this.reportDataSyncRunnerService.runner(jobContext, reportDataSyncRunnerParam, logs);
            isSuccess = true;
        }
        catch (Exception e) {
            logs.add(e.getMessage());
            isSuccess = false;
        }
        logs.stream().forEach(log -> this.appendLog(log + "\n"));
        return isSuccess;
    }

    private User getUserByUserName(String userName) {
        if (StringUtils.isEmpty((String)userName)) {
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

