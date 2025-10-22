/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.JobContext
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.plantask.extend.job.PlanTaskRunner
 *  com.jiuqi.common.plantask.extend.job.Runner
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.core.context.ContextIdentity
 *  com.jiuqi.np.core.context.ContextUser
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.core.context.impl.NpContextIdentity
 *  com.jiuqi.np.core.context.impl.NpContextImpl
 *  com.jiuqi.np.core.context.impl.NpContextUser
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.np.user.User
 *  com.jiuqi.np.user.service.SystemUserService
 *  com.jiuqi.np.user.service.UserService
 *  com.jiuqi.nr.reminder.infer.ReminderRepository
 *  com.jiuqi.nvwa.jobmanager.exception.PlanTaskError
 *  javax.annotation.Resource
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.gcreport.conversion.batch.audit.runner;

import com.jiuqi.bi.core.jobs.JobContext;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.plantask.extend.job.PlanTaskRunner;
import com.jiuqi.common.plantask.extend.job.Runner;
import com.jiuqi.gcreport.conversion.batch.audit.entity.ConversionBatchAuditRunnerEntity;
import com.jiuqi.gcreport.conversion.batch.audit.service.ConversionBatchAuditService;
import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.core.context.ContextIdentity;
import com.jiuqi.np.core.context.ContextUser;
import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.core.context.impl.NpContextIdentity;
import com.jiuqi.np.core.context.impl.NpContextImpl;
import com.jiuqi.np.core.context.impl.NpContextUser;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.np.user.User;
import com.jiuqi.np.user.service.SystemUserService;
import com.jiuqi.np.user.service.UserService;
import com.jiuqi.nr.reminder.infer.ReminderRepository;
import com.jiuqi.nvwa.jobmanager.exception.PlanTaskError;
import java.sql.Blob;
import java.util.Optional;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

@PlanTaskRunner(id="97B18196831F446E8F1E5579C0A7111E", settingPage="conversionBatchCheckConfig", name="com.jiuqi.gcreport.conversion.batch.audit.runner.ConversionBatchAuditRunner", title="\u5916\u5e01\u7a3d\u6838\u8ba1\u5212\u4efb\u52a1")
public class ConversionBatchAuditRunner
extends Runner {
    private final Logger logger = LoggerFactory.getLogger(((Object)((Object)this)).getClass());
    @Resource
    private ConversionBatchAuditService conversionBatchAuditService;
    @Resource
    private UserService<User> userService;
    @Resource
    private SystemUserService systemUserService;
    @Resource
    private ReminderRepository reminderRepository;
    @Resource
    private JdbcTemplate jdbcTemplate;

    public boolean excute(JobContext jobContext) {
        this.logger.info("\u5f00\u59cb\u5916\u5e01\u7a3d\u6838\u8ba1\u5212\u4efb\u52a1");
        this.appendLog("\u6267\u884c\u6d41\u7a0b\u542f\u52a8:\n");
        try {
            ConversionBatchAuditRunnerEntity entity = (ConversionBatchAuditRunnerEntity)JsonUtils.readValue((String)jobContext.getJob().getExtendedConfig(), ConversionBatchAuditRunnerEntity.class);
            User user = this.getUserByUserName(jobContext.getJob().getUser());
            if (user == null || !"admin".equals(user.getName()) && !this.reminderRepository.findUserState(user.getId())) {
                this.appendLog("\u8ba1\u5212\u4efb\u52a1\u7528\u6237\u540d\u4e0d\u53ef\u7528\n");
                this.appendLog("\u6267\u884c\u5931\u8d25\u3002");
                this.logger.error("\u5916\u5e01\u7a3d\u6838\u8ba1\u5212\u4efb\u52a1\u6267\u884c\u5931\u8d25\uff0c\u6267\u884c\u7684\u7528\u6237\u540d\u4e0d\u53ef\u7528");
                return false;
            }
            NpContextImpl npContext = this.buildContextByUserName(jobContext.getJob().getUser());
            NpContextHolder.setContext((NpContext)npContext);
            entity.setCreateUser(user.getName());
            PeriodWrapper periodWrapper = this.conversionBatchAuditService.getParamPeriod(entity);
            entity.setAcctYear(String.valueOf(periodWrapper.getYear()));
            entity.setAcctPeriod(String.valueOf(periodWrapper.getPeriod()));
            Blob blob = this.conversionBatchAuditService.getFileBlob(entity, periodWrapper);
            if (blob == null) {
                this.appendLog("\u6267\u884c\u5931\u8d25\n");
                this.logger.info("\u5916\u5e01\u7a3d\u6838\u8ba1\u5212\u4efb\u52a1\u6267\u884c\u5931\u8d25");
                return false;
            }
            entity.setFileData(blob);
            entity.setFileName("\u5916\u5e01\u6279\u91cf\u7a3d\u6838" + this.conversionBatchAuditService.getFileNameForEntity() + ".xlsx");
            this.conversionBatchAuditService.addAudit(entity);
        }
        catch (Exception e) {
            this.appendLog("\u6267\u884c\u5931\u8d25\n");
            this.logger.info("\u5916\u5e01\u7a3d\u6838\u8ba1\u5212\u4efb\u52a1\u6267\u884c\u5931\u8d25", e);
            return false;
        }
        this.appendLog("\u6267\u884c\u6210\u529f\n");
        this.logger.info("\u5916\u5e01\u7a3d\u6838\u8ba1\u5212\u4efb\u52a1\u6267\u884c\u6210\u529f");
        return true;
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

    private NpContextImpl buildContextByUserName(String userName) throws JQException {
        NpContextImpl npContext = (NpContextImpl)NpContextHolder.createEmptyContext();
        npContext.setTenant("__default_tenant__");
        NpContextUser contextUser = this.buildUserContextByUserName(userName);
        npContext.setUser((ContextUser)contextUser);
        NpContextIdentity identity = this.buildIdentityContext(contextUser);
        npContext.setIdentity((ContextIdentity)identity);
        return npContext;
    }

    private NpContextUser buildUserContextByUserName(String userName) throws JQException {
        return this.buildUserContext(this.getUserByUserName(userName));
    }

    private NpContextUser buildUserContext(User user) throws JQException {
        NpContextUser userContext = new NpContextUser();
        if (user == null) {
            throw new JQException((ErrorEnum)PlanTaskError.QUERY_USER);
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
}

