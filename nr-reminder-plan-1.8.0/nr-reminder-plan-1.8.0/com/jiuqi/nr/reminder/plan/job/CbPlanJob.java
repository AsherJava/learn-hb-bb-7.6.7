/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.asynctask.util.SimpleParamConverter$SerializationUtils
 *  com.jiuqi.np.core.context.ContextIdentity
 *  com.jiuqi.np.core.context.ContextUser
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.core.context.impl.NpContextIdentity
 *  com.jiuqi.np.core.context.impl.NpContextImpl
 *  com.jiuqi.np.core.context.impl.NpContextUser
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.np.user.User
 *  com.jiuqi.np.user.feign.client.NvwaUserClient
 *  com.jiuqi.np.user.service.SystemUserService
 *  org.quartz.Job
 *  org.quartz.JobDataMap
 *  org.quartz.JobExecutionContext
 *  org.quartz.JobKey
 */
package com.jiuqi.nr.reminder.plan.job;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.asynctask.util.SimpleParamConverter;
import com.jiuqi.np.core.context.ContextIdentity;
import com.jiuqi.np.core.context.ContextUser;
import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.core.context.impl.NpContextIdentity;
import com.jiuqi.np.core.context.impl.NpContextImpl;
import com.jiuqi.np.core.context.impl.NpContextUser;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.np.user.User;
import com.jiuqi.np.user.feign.client.NvwaUserClient;
import com.jiuqi.np.user.service.SystemUserService;
import com.jiuqi.nr.reminder.plan.CbPlanDTO;
import com.jiuqi.nr.reminder.plan.service.impl.CbPlanServiceImpl;
import java.util.Optional;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CbPlanJob
implements Job {
    public static final String ID = "com.jiuqi.nr.cb.job";
    private static final Logger logger = LoggerFactory.getLogger(CbPlanJob.class);
    public static final String CONTEXT = "context";

    public void execute(JobExecutionContext context) {
        logger.info("\u50ac\u62a5\u8ba1\u5212\u5f00\u59cb\u6267\u884c");
        JobKey key = context.getJobDetail().getKey();
        String guid = key.getName();
        CbPlanServiceImpl bean = (CbPlanServiceImpl)SpringBeanUtils.getBean(CbPlanServiceImpl.class);
        CbPlanDTO cbPlanDTO = bean.queryByPlanId(guid);
        if (cbPlanDTO != null) {
            String execUser = cbPlanDTO.getExecUser();
            NpContextImpl npContext = this.buildContext(execUser);
            if (npContext != null) {
                try {
                    String contextStr;
                    Object deserialize;
                    JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
                    Object contextObj = jobDataMap.get((Object)CONTEXT);
                    if (contextObj != null && (deserialize = SimpleParamConverter.SerializationUtils.deserialize((String)(contextStr = contextObj.toString()))) instanceof NpContext) {
                        NpContext contextSrc = (NpContext)deserialize;
                        npContext.setIdentity(contextSrc.getIdentity());
                        npContext.setIp(contextSrc.getIp());
                        npContext.setTenant(contextSrc.getTenant());
                        npContext.setLocale(contextSrc.getLocale());
                        npContext.setDeviceMsg(contextSrc.getDeviceMsg());
                        npContext.setOrganization(contextSrc.getOrganization());
                    }
                }
                catch (Exception e) {
                    logger.info("\u7f3a\u5931\u4e0a\u4e0b\u6587\u90e8\u5206\u4fe1\u606f:" + guid, e);
                }
                NpContextHolder.setContext((NpContext)npContext);
                bean.execPlan(guid);
                NpContextHolder.clearContext();
                logger.info("\u50ac\u62a5\u8ba1\u5212\u6267\u884c\u7ed3\u675f\uff0c\u5177\u4f53\u65e5\u5fd7\u5728\u50ac\u62a5\u7ba1\u7406\u67e5\u770b");
            } else {
                logger.info("\u7f3a\u5931\u4e0a\u4e0b\u6587\uff0c\u50ac\u62a5\u8ba1\u5212\u4e2d\u6b62\u6267\u884c");
            }
        } else {
            logger.info("\u672a\u5728\u50ac\u62a5\u8ba1\u5212\u4e2d\u627e\u5230\u76f8\u5e94\u8ba1\u5212:" + guid + "\uff0c\u50ac\u62a5\u8ba1\u5212\u4e2d\u6b62\u6267\u884c");
        }
    }

    private NpContextImpl buildContext(String userId) {
        NpContextImpl npContext = (NpContextImpl)NpContextHolder.createEmptyContext();
        npContext.setTenant("__default_tenant__");
        NpContextUser contextUser = this.buildUserContext(userId);
        if (contextUser == null) {
            return null;
        }
        npContext.setUser((ContextUser)contextUser);
        NpContextIdentity identity = this.buildIdentityContext(contextUser);
        npContext.setIdentity((ContextIdentity)identity);
        return npContext;
    }

    private NpContextIdentity buildIdentityContext(NpContextUser contextUser) {
        NpContextIdentity identity = new NpContextIdentity();
        identity.setId(contextUser.getId());
        identity.setTitle(contextUser.getFullname());
        return identity;
    }

    private NpContextUser buildUserContext(String userName) {
        NpContextUser userContext = new NpContextUser();
        User user = this.getUserByUserName(userName);
        if (user == null) {
            return null;
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
        Optional sysUser = ((SystemUserService)SpringBeanUtils.getBean(SystemUserService.class)).find(userName);
        if (sysUser.isPresent()) {
            return (User)sysUser.get();
        }
        return ((NvwaUserClient)SpringBeanUtils.getBean(NvwaUserClient.class)).find(userName);
    }
}

