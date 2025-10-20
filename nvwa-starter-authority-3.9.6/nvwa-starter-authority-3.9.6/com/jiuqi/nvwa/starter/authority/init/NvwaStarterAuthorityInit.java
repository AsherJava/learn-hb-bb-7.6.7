/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.JobManager
 *  com.jiuqi.bi.core.jobs.JobsException
 *  com.jiuqi.bi.core.jobs.model.AbstractScheduleMethod
 *  com.jiuqi.bi.core.jobs.model.JobModel
 *  com.jiuqi.bi.core.jobs.model.schedulemethod.DailyScheduleMethod
 *  com.jiuqi.bi.core.jobs.model.schedulemethod.DayHour
 *  com.jiuqi.bi.core.jobs.model.schedulemethod.SchedulePeroid
 *  com.jiuqi.bi.core.jobs.model.schedulemethod.SimpleScheduleMethod
 *  com.jiuqi.bi.transfer.engine.TransferFactory
 *  com.jiuqi.bi.transfer.engine.TransferFactoryManager
 *  com.jiuqi.bi.transfer.engine.TransferModule
 *  com.jiuqi.bi.transfer.engine.ex.TransferException
 *  com.jiuqi.np.authz2.impl.misc.Authz2Initializer
 *  com.jiuqi.np.authz2.service.SystemIdentityService
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.np.i18n.service.I18NResourceInitService
 *  com.jiuqi.np.user.User
 *  com.jiuqi.np.user.dto.UserDTO
 *  com.jiuqi.np.user.feign.client.NvwaSystemUserClient
 *  com.jiuqi.np.user.feign.client.NvwaUserClient
 *  com.jiuqi.nvwa.authority.i18n.NvwaAuthorityI18NResource
 *  com.jiuqi.nvwa.authority.i18n.NvwaAuthorityI18NResourceManager
 *  com.jiuqi.nvwa.authority.login.service.INvwaLoginLogService
 *  com.jiuqi.nvwa.authority.role.transfer.RoleTransferFactory
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.nvwa.starter.authority.init;

import com.jiuqi.bi.core.jobs.JobManager;
import com.jiuqi.bi.core.jobs.JobsException;
import com.jiuqi.bi.core.jobs.model.AbstractScheduleMethod;
import com.jiuqi.bi.core.jobs.model.JobModel;
import com.jiuqi.bi.core.jobs.model.schedulemethod.DailyScheduleMethod;
import com.jiuqi.bi.core.jobs.model.schedulemethod.DayHour;
import com.jiuqi.bi.core.jobs.model.schedulemethod.SchedulePeroid;
import com.jiuqi.bi.core.jobs.model.schedulemethod.SimpleScheduleMethod;
import com.jiuqi.bi.transfer.engine.TransferFactory;
import com.jiuqi.bi.transfer.engine.TransferFactoryManager;
import com.jiuqi.bi.transfer.engine.TransferModule;
import com.jiuqi.bi.transfer.engine.ex.TransferException;
import com.jiuqi.np.authz2.impl.misc.Authz2Initializer;
import com.jiuqi.np.authz2.service.SystemIdentityService;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.np.i18n.service.I18NResourceInitService;
import com.jiuqi.np.user.User;
import com.jiuqi.np.user.dto.UserDTO;
import com.jiuqi.np.user.feign.client.NvwaSystemUserClient;
import com.jiuqi.np.user.feign.client.NvwaUserClient;
import com.jiuqi.nvwa.authority.i18n.NvwaAuthorityI18NResource;
import com.jiuqi.nvwa.authority.i18n.NvwaAuthorityI18NResourceManager;
import com.jiuqi.nvwa.authority.login.service.INvwaLoginLogService;
import com.jiuqi.nvwa.authority.role.transfer.RoleTransferFactory;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class NvwaStarterAuthorityInit {
    private static final Logger log = LoggerFactory.getLogger(NvwaStarterAuthorityInit.class);
    @Autowired
    private JdbcTemplate jdbc;
    @Autowired
    private INvwaLoginLogService loginLogService;
    @Autowired
    private NvwaUserClient userClient;
    @Autowired
    private I18NResourceInitService i18NResourceInitService;
    @Autowired
    private NvwaAuthorityI18NResource authorityI18NResource;
    @Autowired
    private NvwaAuthorityI18NResourceManager i18NResourceManager;

    public void init() throws Exception {
        Authz2Initializer authz2Initializer = (Authz2Initializer)SpringBeanUtils.getBean(Authz2Initializer.class);
        NvwaSystemUserClient systemUserService = (NvwaSystemUserClient)SpringBeanUtils.getBean(NvwaSystemUserClient.class);
        SystemIdentityService systemIdentityService = (SystemIdentityService)SpringBeanUtils.getBean(SystemIdentityService.class);
        authz2Initializer.initialize();
        for (User sysUser : systemUserService.getUsers()) {
            systemIdentityService.addSystemIdenity(null, sysUser.getId());
        }
    }

    public void initWhenStarted() throws Exception {
        if ((Integer)this.loginLogService.countAll(null).getDatas() == 0) {
            this.copyLogFromOld();
        }
        this.initOnlineUserCountJob();
        this.initAccessCountJob();
        this.initRoleTransfer();
        this.initI18NResource();
    }

    private void initI18NResource() {
        this.i18NResourceInitService.initI18nResource(this.authorityI18NResource.getNameSpace(), this.i18NResourceManager.getAllInitResource(), false, true);
    }

    private void initRoleTransfer() throws TransferException {
        TransferModule moduleExt = new TransferModule("ROLE_TRANSFER_MODULE_ID", "\u89d2\u8272", 1);
        TransferFactoryManager.getInstance().register(moduleExt);
        TransferFactoryManager.getInstance().register((TransferFactory)SpringBeanUtils.getBean(RoleTransferFactory.class));
    }

    private void initAccessCountJob() {
        JobManager jobManager = JobManager.getInstance((String)"AUTHORITY_LOGIN_ACCESS_COUNT");
        JobModel job = null;
        try {
            job = jobManager.getJob("AUTHORITY_LOGIN_ACCESS_COUNT_JOB");
        }
        catch (JobsException jobsException) {
            // empty catch block
        }
        if (job == null) {
            long startTime = System.currentTimeMillis();
            JobModel jobModel = jobManager.createJob("\u5973\u5a32\u7528\u6237\u8bbf\u95ee\u91cf\u8ba1\u7b97\u4efb\u52a1", "AUTHORITY_LOGIN_ACCESS_COUNT_JOB");
            jobModel.setCategory("AUTHORITY_LOGIN_ACCESS_COUNT");
            jobModel.setGuid("AUTHORITY_LOGIN_ACCESS_COUNT_JOB");
            jobModel.setDesc("\u5973\u5a32\u7528\u6237\u8bbf\u95ee\u91cf\u8ba1\u7b97\u4efb\u52a1");
            jobModel.setEnable(true);
            jobModel.setUser(NpContextHolder.getContext().getUserName());
            jobModel.setStartTime(startTime);
            jobModel.setEndTime(4070880000000L);
            jobModel.setFolderGuid("SYS_GROUP_ID");
            jobModel.setLastModifyTime(0L);
            DailyScheduleMethod scheduleMethod = (DailyScheduleMethod)AbstractScheduleMethod.createMethod((String)"daily");
            DayHour executeTime = new DayHour();
            executeTime.setHour(2);
            executeTime.setMinute(0);
            executeTime.setSecond(0);
            scheduleMethod.setExecuteTimeInDay(executeTime);
            jobModel.setScheduleMethod((AbstractScheduleMethod)scheduleMethod);
            try {
                jobManager.addJobModel(jobModel);
            }
            catch (JobsException e) {
                log.error("\u521d\u59cb\u5316\u5973\u5a32\u7528\u6237\u8bbf\u95ee\u91cf\u7edf\u8ba1\u4efb\u52a1\u5931\u8d25", e);
            }
        }
    }

    private void initOnlineUserCountJob() {
        JobManager jobManager = JobManager.getInstance((String)"AUTHORITY_LOGIN_ONLINE_USER_COUNT");
        JobModel job = null;
        try {
            job = jobManager.getJob("AUTHORITY_LOGIN_USER_COUNT_JOB");
        }
        catch (JobsException jobsException) {
            // empty catch block
        }
        if (job == null) {
            long startTime = System.currentTimeMillis();
            JobModel jobModel = jobManager.createJob("\u5973\u5a32\u5728\u7ebf\u7528\u6237/\u4eca\u65e5\u8bbf\u95ee\u91cf\u7edf\u8ba1\u4efb\u52a1", "AUTHORITY_LOGIN_USER_COUNT_JOB");
            jobModel.setCategory("AUTHORITY_LOGIN_ONLINE_USER_COUNT");
            jobModel.setGuid("AUTHORITY_LOGIN_USER_COUNT_JOB");
            jobModel.setDesc("\u5973\u5a32\u5728\u7ebf\u7528\u6237\u7edf\u8ba1\u4efb\u52a1");
            jobModel.setEnable(true);
            jobModel.setUser(NpContextHolder.getContext().getUserName());
            jobModel.setStartTime(startTime);
            jobModel.setEndTime(4070880000000L);
            jobModel.setFolderGuid("SYS_GROUP_ID");
            jobModel.setLastModifyTime(0L);
            SimpleScheduleMethod createMethod = (SimpleScheduleMethod)AbstractScheduleMethod.createMethod((String)"simple");
            createMethod.setRepeat(true);
            createMethod.setExecuteTime(startTime);
            createMethod.setRepeatPeriodType(SchedulePeroid.MINUTE);
            createMethod.setRepeatInterval(3);
            jobModel.setScheduleMethod((AbstractScheduleMethod)createMethod);
            try {
                jobManager.addJobModel(jobModel);
            }
            catch (JobsException e) {
                log.error("\u521d\u59cb\u5316\u5973\u5a32\u5728\u7ebf\u7528\u6237\u7edf\u8ba1\u4efb\u52a1\u5931\u8d25", e);
            }
        }
    }

    private void copyLogFromOld() {
        log.info("\u5f00\u59cb-\u590d\u5236np_logs\u767b\u5f55\u65e5\u5fd7\u81f3nvwa_login_logs\u4e2d...");
        int row = this.jdbc.update("INSERT INTO NVWA_LOGIN_LOGS (L_ID, L_USER_ID, L_USER_NAME, L_CLIENT_AGENT, L_CLIENT_IP, L_LOGIN_TIME, L_TENANT_ID,\n                             L_SERVER_IP, L_TRACE_ID, L_IS_SSO, L_SSO_TYPE)\nSELECT LOGKEY,\n       USERKEY,\n       USERNAME,\n       CLIENTCODE,\n       CLIENTIP,\n       EVENTTIME,\n       TENANTID,\n       SERVERIP,\n       TRACEID,\n       0,\n       NULL\nFROM NP_LOGS\nWHERE MOUDLE = '\u767b\u5f55\u6a21\u5757'\n  AND TITLE = '\u767b\u5f55\u6210\u529f'");
        log.info("\u5b8c\u6210-\u590d\u5236np_logs\u767b\u5f55\u65e5\u5fd7\u81f3nvwa_login_logs, \u4e00\u5171{}\u6761\u6570\u636e", (Object)row);
        log.info("\u5f00\u59cb-\u586b\u5145\u7528\u6237login_time...");
        Map userLoginTimeMap = (Map)this.jdbc.query("SELECT USERKEY, MAX(EVENTTIME)\nFROM NP_LOGS\nWHERE MOUDLE = '\u767b\u5f55\u6a21\u5757'\n  AND TITLE = '\u767b\u5f55\u6210\u529f'\nGROUP BY USERKEY\n", rs -> {
            HashMap<String, Object> ret = new HashMap<String, Object>();
            while (rs.next()) {
                ret.put(rs.getString(1), Optional.ofNullable(rs.getTimestamp(2)).map(Timestamp::toInstant).orElse(null));
            }
            return ret;
        });
        for (Map.Entry entry : userLoginTimeMap.entrySet()) {
            UserDTO userDTO = new UserDTO();
            userDTO.setId((String)entry.getKey());
            userDTO.setName((String)entry.getKey());
            userDTO.setLoginTime((Instant)entry.getValue());
            this.userClient.updateLoginTime(userDTO);
        }
        log.info("\u5b8c\u6210-\u586b\u5145\u7528\u6237login_time");
    }
}

