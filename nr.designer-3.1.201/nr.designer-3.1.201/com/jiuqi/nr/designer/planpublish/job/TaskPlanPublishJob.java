/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
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
 *  com.jiuqi.np.user.User
 *  com.jiuqi.np.user.service.SystemUserService
 *  com.jiuqi.np.user.service.UserService
 *  com.jiuqi.nr.definition.controller.IDesignTimeViewController
 *  com.jiuqi.nr.definition.planpublish.dao.TaskPlanPublishDao
 *  com.jiuqi.nr.definition.planpublish.entity.TaskPlanPublish
 *  com.jiuqi.nvwa.jobmanager.exception.PlanTaskError
 *  org.quartz.Job
 *  org.quartz.JobDataMap
 *  org.quartz.JobExecutionContext
 *  org.quartz.JobExecutionException
 */
package com.jiuqi.nr.designer.planpublish.job;

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
import com.jiuqi.np.user.User;
import com.jiuqi.np.user.service.SystemUserService;
import com.jiuqi.np.user.service.UserService;
import com.jiuqi.nr.definition.controller.IDesignTimeViewController;
import com.jiuqi.nr.definition.planpublish.dao.TaskPlanPublishDao;
import com.jiuqi.nr.definition.planpublish.entity.TaskPlanPublish;
import com.jiuqi.nr.designer.common.NrDesignLogHelper;
import com.jiuqi.nr.designer.planpublish.common.PublishStatus;
import com.jiuqi.nr.designer.planpublish.service.TaskPlanPublishService;
import com.jiuqi.nvwa.jobmanager.exception.PlanTaskError;
import java.util.Optional;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class TaskPlanPublishJob
implements Job {
    private static final Logger log = LoggerFactory.getLogger(TaskPlanPublishJob.class);
    @Autowired
    private TaskPlanPublishDao taskPlanPublishDao;
    @Autowired
    private TaskPlanPublishService taskPlanPublishService;
    @Autowired
    private IDesignTimeViewController designTimeViewService;
    @Autowired
    private UserService<User> userService;
    @Autowired
    private SystemUserService systemUserService;

    public void execute(JobExecutionContext context) throws JobExecutionException {
        block7: {
            String logTitle = "\u53d1\u5e03\u4efb\u52a1";
            String taskTitle = "\u672a\u77e5";
            try {
                JobDataMap jobDataMap = context.getMergedJobDataMap();
                String taskKey = (String)jobDataMap.get("taskKey");
                taskTitle = this.designTimeViewService.queryTaskDefine(taskKey).getTitle();
                TaskPlanPublish taskPlanPublish = this.taskPlanPublishDao.queryWorkPlan(taskKey);
                String planKey = (String)jobDataMap.get("planKey");
                String userName = (String)jobDataMap.get("userName");
                NpContextImpl npContext = null;
                if (!StringUtils.isEmpty((String)userName)) {
                    try {
                        npContext = this.buildContext(userName);
                        NpContextHolder.setContext((NpContext)npContext);
                    }
                    catch (JQException e) {
                        log.error(e.getMessage(), e);
                        NrDesignLogHelper.log("\u8ba1\u5212\u4efb\u52a1", "\u8bbe\u7f6e\u6267\u884c\u4eba\u2018" + userName + "\u2019\u7684\u4fe1\u606f\u65f6\u53d1\u751f\u4e86\u5f02\u5e38\uff1a" + e.getMessage() + "\u3002", NrDesignLogHelper.LOGLEVEL_ERROR);
                    }
                }
                if (taskPlanPublish == null || !planKey.equals(taskPlanPublish.getKey())) break block7;
                String publishStatus = taskPlanPublish.getPublishStatus();
                if (!PublishStatus.BRFORE_PUBLISH.toString().equals(publishStatus)) break block7;
                String deployTaskID = (String)jobDataMap.get("deployTaskID");
                try {
                    this.taskPlanPublishDao.updatePublishStatus(planKey, PublishStatus.PUBLISHING.toString());
                    this.taskPlanPublishService.planPublishTask(taskKey, deployTaskID);
                    NrDesignLogHelper.log(logTitle, taskTitle, NrDesignLogHelper.LOGLEVEL_INFO);
                }
                catch (Exception e) {
                    this.taskPlanPublishService.afterTaskPublish(planKey, taskKey, true, e.getMessage());
                    NrDesignLogHelper.log(logTitle, taskTitle, NrDesignLogHelper.LOGLEVEL_ERROR);
                    throw new JobExecutionException(e.getMessage());
                }
                this.taskPlanPublishService.afterTaskPublish(planKey, taskKey, false, null);
            }
            catch (Exception e) {
                NrDesignLogHelper.log(logTitle, taskTitle, NrDesignLogHelper.LOGLEVEL_ERROR);
                throw new JobExecutionException(e.getMessage());
            }
        }
    }

    private NpContextImpl buildContext(String userName) throws JQException {
        NpContextImpl npContext = (NpContextImpl)NpContextHolder.createEmptyContext();
        NpContextUser contextUser = this.buildUserContext(userName);
        npContext.setUser((ContextUser)contextUser);
        NpContextIdentity identity = this.buildIdentityContext(contextUser);
        npContext.setIdentity((ContextIdentity)identity);
        return npContext;
    }

    private NpContextIdentity buildIdentityContext(NpContextUser contextUser) throws JQException {
        NpContextIdentity identity = new NpContextIdentity();
        identity.setId(contextUser.getId());
        identity.setTitle(contextUser.getFullname());
        return identity;
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
}

