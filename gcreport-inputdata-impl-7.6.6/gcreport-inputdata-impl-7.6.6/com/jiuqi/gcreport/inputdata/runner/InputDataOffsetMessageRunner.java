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
 *  com.jiuqi.np.user.User
 *  com.jiuqi.np.user.service.SystemUserService
 *  com.jiuqi.np.user.service.UserService
 *  com.jiuqi.nr.reminder.infer.ReminderRepository
 *  com.jiuqi.nvwa.jobmanager.exception.PlanTaskError
 */
package com.jiuqi.gcreport.inputdata.runner;

import com.jiuqi.bi.core.jobs.JobContext;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.plantask.extend.job.PlanTaskRunner;
import com.jiuqi.common.plantask.extend.job.Runner;
import com.jiuqi.gcreport.inputdata.runner.service.InputDataOffsetRemindService;
import com.jiuqi.gcreport.inputdata.runner.vo.InputDataMessageRemindConfigVO;
import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
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
import com.jiuqi.nr.reminder.infer.ReminderRepository;
import com.jiuqi.nvwa.jobmanager.exception.PlanTaskError;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@PlanTaskRunner(id="9DB8723E6981CA81D7FD7DC43CAA02C9", name="com.jiuqi.gcreport.inputdata.runner.InputDataOffsetMessageRunner", title="\u5185\u90e8\u8868\u6570\u636e\u53d8\u52a8\u63d0\u9192", group="\u5bf9\u8d26\u6d88\u606f\u63d0\u9192/\u5185\u90e8\u8868\u63d0\u9192", settingPage="inputDataOffsetMessageConfig")
public class InputDataOffsetMessageRunner
extends Runner {
    public static final String ID = "9DB8723E6981CA81D7FD7DC43CAA02C9";
    public static final String NAME = "com.jiuqi.gcreport.inputdata.runner.InputDataOffsetMessageRunner";
    public static final String TITLE = "\u5185\u90e8\u8868\u6570\u636e\u53d8\u52a8\u63d0\u9192";
    public static final String SETTING_PAGE = "inputDataOffsetMessageConfig";
    public static final String GROUP = "\u5bf9\u8d26\u6d88\u606f\u63d0\u9192/\u5185\u90e8\u8868\u63d0\u9192";
    private final Logger logger = LoggerFactory.getLogger(((Object)((Object)this)).getClass());
    @Autowired
    private SystemUserService systemUserService;
    @Autowired
    private ReminderRepository reminderRepository;
    @Autowired
    private UserService<User> userService;
    @Autowired
    private InputDataOffsetRemindService inputDataOffsetRemindService;

    protected boolean excute(JobContext jobContext) {
        this.logger.info("\u5f00\u59cb\u5185\u90e8\u8868\u6570\u636e\u53d8\u52a8\u63d0\u9192\u8ba1\u5212\u4efb\u52a1");
        this.appendLog("\u6267\u884c:\n");
        InputDataMessageRemindConfigVO inputDataMessageRemindConfig = (InputDataMessageRemindConfigVO)JsonUtils.readValue((String)jobContext.getJob().getExtendedConfig(), InputDataMessageRemindConfigVO.class);
        if (inputDataMessageRemindConfig == null) {
            this.appendLog("\u9ad8\u7ea7\u8bbe\u7f6e\u53c2\u6570\u4e3a\u7a7a\u3002\n");
            this.appendLog("\u6267\u884c\u5931\u8d25");
            return false;
        }
        User user = this.getUserByUserName(jobContext.getJob().getUser());
        if (user != null && ("admin".equals(user.getName()) || this.reminderRepository.findUserState(user.getId()))) {
            try {
                NpContextHolder.setContext((NpContext)this.buildContext(jobContext.getJob().getUser()));
            }
            catch (JQException e) {
                this.appendLog("\u6267\u884c\u5931\u8d25\n");
                this.appendLog("\u5185\u90e8\u8868\u6570\u636e\u53d8\u52a8\u63d0\u9192\u8ba1\u5212\u4efb\u52a1\u7528\u6237\u540d\u521d\u59cb\u5316\u5931\u8d25");
                this.logger.error("\u5185\u90e8\u8868\u6570\u636e\u53d8\u52a8\u63d0\u9192\u8ba1\u5212\u4efb\u52a1\u6267\u884c\u5931\u8d25\uff0c\u6267\u884c\u7684\u7528\u6237\u540d\u521d\u59cb\u5316\u5931\u8d25");
                return false;
            }
        } else {
            this.appendLog("\u6267\u884c\u5931\u8d25\n");
            this.appendLog("\u8ba1\u5212\u4efb\u52a1\u7528\u6237\u540d\u4e0d\u53ef\u7528");
            this.logger.error("\u5185\u90e8\u8868\u6570\u636e\u53d8\u52a8\u63d0\u9192\u8ba1\u5212\u4efb\u52a1\u6267\u884c\u5931\u8d25\uff0c\u6267\u884c\u7684\u7528\u6237\u540d\u4e0d\u53ef\u7528");
            return false;
        }
        StringBuffer massage = new StringBuffer();
        inputDataMessageRemindConfig.setUserId(user.getId());
        boolean isSuccess = this.inputDataOffsetRemindService.execute(inputDataMessageRemindConfig, massage);
        if (!isSuccess) {
            this.appendLog("\u6267\u884c\u5931\u8d25\n");
            this.appendLog(massage.toString());
            this.logger.error("\u5185\u90e8\u8868\u6570\u636e\u53d8\u52a8\u63d0\u9192\u8ba1\u5212\u4efb\u52a1\u6267\u884c\u5931\u8d25" + massage.toString());
            return false;
        }
        this.appendLog("\u6267\u884c\u6210\u529f\n");
        this.appendLog(massage.toString());
        this.logger.info("\u5185\u90e8\u8868\u6570\u636e\u53d8\u52a8\u63d0\u9192\u8ba1\u5212\u4efb\u52a1\u6267\u884c\u6210\u529f");
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

    private NpContextImpl buildContext(String userName) throws JQException {
        NpContextImpl npContext = (NpContextImpl)NpContextHolder.createEmptyContext();
        npContext.setTenant("__default_tenant__");
        NpContextUser contextUser = this.buildUserContext(userName);
        npContext.setUser((ContextUser)contextUser);
        NpContextIdentity identity = this.buildIdentityContext(contextUser);
        npContext.setIdentity((ContextIdentity)identity);
        return npContext;
    }

    private NpContextUser buildUserContext(String userName) throws JQException {
        NpContextUser userContext = new NpContextUser();
        User user = this.getUserByUserName(userName);
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

