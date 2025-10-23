/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.JobContext
 *  com.jiuqi.bi.core.jobs.JobExecutionException
 *  com.jiuqi.bi.core.jobs.JobExecutor
 *  com.jiuqi.bi.core.jobs.model.JobModel
 *  com.jiuqi.bi.core.jobs.model.JobParameter
 *  com.jiuqi.bi.core.nodekeeper.DistributionManager
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.asynctask.util.SimpleParamConverter$SerializationUtils
 *  com.jiuqi.np.core.context.ContextIdentity
 *  com.jiuqi.np.core.context.ContextOrganization
 *  com.jiuqi.np.core.context.ContextUser
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.core.context.impl.NpContextIdentity
 *  com.jiuqi.np.core.context.impl.NpContextImpl
 *  com.jiuqi.np.core.context.impl.NpContextUser
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.np.user.User
 *  com.jiuqi.np.user.service.SystemUserService
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nvwa.login.domain.NvwaContextOrg
 *  com.jiuqi.va.domain.org.OrgDO
 *  com.jiuqi.va.domain.org.OrgDTO
 *  com.jiuqi.va.feign.client.OrgDataClient
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 *  javax.servlet.http.HttpServletRequest
 *  org.quartz.Trigger
 *  org.springframework.web.context.request.RequestAttributes
 *  org.springframework.web.context.request.RequestContextHolder
 *  org.springframework.web.context.request.ServletRequestAttributes
 */
package com.jiuqi.nr.workflow2.schedule.bi.jobs.executor;

import com.jiuqi.bi.core.jobs.JobContext;
import com.jiuqi.bi.core.jobs.JobExecutionException;
import com.jiuqi.bi.core.jobs.JobExecutor;
import com.jiuqi.bi.core.jobs.model.JobModel;
import com.jiuqi.bi.core.jobs.model.JobParameter;
import com.jiuqi.bi.core.nodekeeper.DistributionManager;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.asynctask.util.SimpleParamConverter;
import com.jiuqi.np.core.context.ContextIdentity;
import com.jiuqi.np.core.context.ContextOrganization;
import com.jiuqi.np.core.context.ContextUser;
import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.core.context.impl.NpContextIdentity;
import com.jiuqi.np.core.context.impl.NpContextImpl;
import com.jiuqi.np.core.context.impl.NpContextUser;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.np.user.User;
import com.jiuqi.np.user.service.SystemUserService;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.workflow2.schedule.bean.utils.ProcessScheduleBeanUtils;
import com.jiuqi.nr.workflow2.schedule.bi.jobs.monitor.IProcessStartupAsyncMonitor;
import com.jiuqi.nr.workflow2.schedule.bi.jobs.monitor.ProcessStartupAsyncMonitor;
import com.jiuqi.nvwa.login.domain.NvwaContextOrg;
import com.jiuqi.va.domain.org.OrgDO;
import com.jiuqi.va.domain.org.OrgDTO;
import com.jiuqi.va.feign.client.OrgDataClient;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import java.text.ParseException;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import org.quartz.Trigger;
import org.slf4j.LoggerFactory;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public abstract class ProcessJobExecutor
extends JobExecutor {
    protected static final int progressWeight = 100;
    protected static final String adminUserName = "admin";
    private static final String IP_HEADER_FORWARDED_FOR = "X-Forwarded-For";
    private static final String IP_HEADER_REMOTE_ADDR = "X-Real-IP";

    public void execute(JobContext context) throws JobExecutionException {
        ProcessStartupAsyncMonitor monitor = new ProcessStartupAsyncMonitor(context, LoggerFactory.getLogger(((Object)((Object)this)).getClass()), 100);
        JobParameter npContextStr = this.findJobParameter(context.getJob(), "NP_CONTEXT");
        NpContextHolder.setContext((NpContext)this.getNpContext(npContextStr));
        this.execute(context, monitor);
        NpContextHolder.clearContext();
    }

    protected JobParameter findJobParameter(JobModel jobModel, String name) {
        List parameters = jobModel.getParameters();
        return parameters.stream().filter(p -> p.getName().equals(name)).findFirst().orElse(null);
    }

    protected NpContext getNpContext(JobParameter npContextStr) {
        User oneSystemUser = this.getOneSystemUser();
        assert (oneSystemUser != null);
        NpContextImpl npContext = (NpContextImpl)NpContextHolder.createEmptyContext();
        npContext.setTenant("__default_tenant__");
        npContext.setUser(this.getContextUser(oneSystemUser));
        npContext.setIdentity(this.getContextIdentity(oneSystemUser));
        npContext.setIp(this.getClientIpAddress(RequestContextHolder.getRequestAttributes()));
        npContext.setLocale(LocaleContextHolder.getLocale());
        npContext.setOrganization(this.getContextOrganization(oneSystemUser));
        if (npContextStr != null) {
            Object deserialize = SimpleParamConverter.SerializationUtils.deserialize((String)npContextStr.getDefaultValue());
            NpContext oriNpContext = (NpContext)deserialize;
            npContext.setDeviceMsg(oriNpContext.getDeviceMsg());
        }
        return npContext;
    }

    private ContextOrganization getContextOrganization(User user) {
        OrgDataClient orgDataClient = (OrgDataClient)ApplicationContextRegister.getBean(OrgDataClient.class);
        OrgDTO orgDTO = new OrgDTO();
        orgDTO.setCode(user.getOrgCode());
        OrgDO orgDO = orgDataClient.get(orgDTO);
        NvwaContextOrg nvwaContextOrg = new NvwaContextOrg();
        if (orgDO != null && !orgDO.isEmpty()) {
            nvwaContextOrg.setCode(orgDO.getCode());
            nvwaContextOrg.setName(orgDO.getName());
            nvwaContextOrg.setId(orgDO.getId().toString());
            return nvwaContextOrg;
        }
        return nvwaContextOrg;
    }

    private ContextIdentity getContextIdentity(User user) {
        NpContextIdentity idEntity = new NpContextIdentity();
        idEntity.setId(user.getId());
        idEntity.setTitle(user.getFullname());
        idEntity.setOrgCode(user.getOrgCode());
        return idEntity;
    }

    private ContextUser getContextUser(User user) {
        NpContextUser userContext = new NpContextUser();
        userContext.setId(user.getId());
        userContext.setName(user.getName());
        userContext.setOrgCode(user.getOrgCode());
        userContext.setNickname(user.getNickname());
        userContext.setDescription(user.getDescription());
        return userContext;
    }

    private User getOneSystemUser() {
        SystemUserService systemUserService = (SystemUserService)SpringBeanUtils.getBean(SystemUserService.class);
        Optional sysUser = ((SystemUserService)SpringBeanUtils.getBean(SystemUserService.class)).find(adminUserName);
        if (sysUser.isPresent()) {
            return (User)sysUser.get();
        }
        List allSystemUsers = systemUserService.getAllUsers();
        if (allSystemUsers != null && !allSystemUsers.isEmpty()) {
            return (User)allSystemUsers.get(0);
        }
        return null;
    }

    private String getClientIpAddress(RequestAttributes requestAttr) {
        if (!(requestAttr instanceof ServletRequestAttributes)) {
            return DistributionManager.getInstance().getIp().replace("-", "@");
        }
        ServletRequestAttributes attributes = (ServletRequestAttributes)requestAttr;
        HttpServletRequest request = attributes.getRequest();
        String requestHeader = request.getHeader(IP_HEADER_FORWARDED_FOR);
        if (StringUtils.isNotEmpty((String)requestHeader) && !"unknown".equalsIgnoreCase(requestHeader)) {
            int index = requestHeader.indexOf(",");
            if (index != -1) {
                return requestHeader.substring(0, index);
            }
            return requestHeader;
        }
        requestHeader = request.getHeader(IP_HEADER_REMOTE_ADDR);
        if (StringUtils.isNotEmpty((String)requestHeader) && !"unknown".equalsIgnoreCase(requestHeader)) {
            return requestHeader;
        }
        return request.getRemoteAddr();
    }

    abstract void execute(JobContext var1, ProcessStartupAsyncMonitor var2) throws JobExecutionException;

    protected Trigger createExecuteTrigger(TaskDefine taskDefine, String period, IProcessStartupAsyncMonitor monitor) {
        Trigger trigger = null;
        try {
            trigger = ProcessScheduleBeanUtils.getPeriodTriggerService().buildStartupTrigger(taskDefine, period);
        }
        catch (ParseException e) {
            monitor.error("\u65f6\u671f\u504f\u79fb\u89e3\u6790\u5931\u8d25\uff1a" + e.getMessage(), e);
        }
        return trigger;
    }
}

