/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.JobContext
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.plantask.extend.job.PlanTaskRunner
 *  com.jiuqi.common.plantask.extend.job.Runner
 *  com.jiuqi.gcreport.archive.api.scheme.vo.ArchiveConfigFormInfo
 *  com.jiuqi.gcreport.archive.api.scheme.vo.ArchiveContext
 *  com.jiuqi.gcreport.common.task.common.TaskPeriodUtils
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.core.context.ContextIdentity
 *  com.jiuqi.np.core.context.ContextUser
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.core.context.impl.NpContextIdentity
 *  com.jiuqi.np.core.context.impl.NpContextImpl
 *  com.jiuqi.np.core.context.impl.NpContextUser
 *  com.jiuqi.np.period.DefaultPeriodAdapter
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.np.user.SystemUser
 *  com.jiuqi.np.user.User
 *  com.jiuqi.np.user.service.SystemUserService
 *  com.jiuqi.np.user.service.UserService
 *  com.jiuqi.nr.definition.controller.IDesignTimeViewController
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormGroupDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.reminder.infer.ReminderRepository
 *  com.jiuqi.nvwa.jobmanager.exception.PlanTaskError
 *  javax.annotation.Resource
 */
package com.jiuqi.gcreport.archive.service.impl;

import com.jiuqi.bi.core.jobs.JobContext;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.plantask.extend.job.PlanTaskRunner;
import com.jiuqi.common.plantask.extend.job.Runner;
import com.jiuqi.gcreport.archive.api.scheme.vo.ArchiveConfigFormInfo;
import com.jiuqi.gcreport.archive.api.scheme.vo.ArchiveContext;
import com.jiuqi.gcreport.archive.entity.ILRunnerEntity;
import com.jiuqi.gcreport.archive.msgsend.ArchiveMessageSenderService;
import com.jiuqi.gcreport.archive.service.GcArchiveService;
import com.jiuqi.gcreport.common.task.common.TaskPeriodUtils;
import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.core.context.ContextIdentity;
import com.jiuqi.np.core.context.ContextUser;
import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.core.context.impl.NpContextIdentity;
import com.jiuqi.np.core.context.impl.NpContextImpl;
import com.jiuqi.np.core.context.impl.NpContextUser;
import com.jiuqi.np.period.DefaultPeriodAdapter;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.np.user.SystemUser;
import com.jiuqi.np.user.User;
import com.jiuqi.np.user.service.SystemUserService;
import com.jiuqi.np.user.service.UserService;
import com.jiuqi.nr.definition.controller.IDesignTimeViewController;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormGroupDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.reminder.infer.ReminderRepository;
import com.jiuqi.nvwa.jobmanager.exception.PlanTaskError;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

@PlanTaskRunner(id="74B28126831F346E8F1E5479C0A7766C", settingPage="archivePluginConfig", name="com.jiuqi.gcreport.archive.service.impl.ArchivePluginRunner", title="\u7535\u5b50\u6863\u6848\u5f52\u6863\u8ba1\u5212\u4efb\u52a1")
public class ArchivePluginRunner
extends Runner {
    private static final Logger logger = LoggerFactory.getLogger(ArchivePluginRunner.class);
    @Autowired
    private GcArchiveService gcArchiveService;
    @Autowired
    private IRunTimeViewController runtimeView;
    @Autowired
    private SystemUserService systemUserService;
    @Resource
    private UserService<User> userService;
    @Autowired
    private ReminderRepository reminderRepository;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private ArchiveMessageSenderService archiveMessageSenderService;
    @Autowired
    private IDesignTimeViewController iDesignTimeViewController;

    protected boolean excute(JobContext jobContext) {
        logger.info("\u5f00\u59cb\u7535\u5b50\u6863\u6848\u5f52\u6863\u8ba1\u5212\u4efb\u52a1");
        this.appendLog("\u6267\u884c\u6d41\u7a0b\u542f\u52a8:\n");
        ILRunnerEntity iLRunnerEntity = (ILRunnerEntity)JsonUtils.readValue((String)jobContext.getJob().getExtendedConfig(), ILRunnerEntity.class);
        logger.info("\u9ad8\u7ea7\u53c2\u6570\u8bbe\u7f6e\u7684\u503c\u4e3aiLRunnerEntity\uff1a{}", (Object)iLRunnerEntity);
        if (iLRunnerEntity == null) {
            this.appendLog("\u9ad8\u7ea7\u8bbe\u7f6e\u53c2\u6570\u4e3a\u7a7a\u3002\n");
            this.appendLog("\u6267\u884c\u5931\u8d25");
            return false;
        }
        User user = this.getUserByUserName(jobContext.getJob().getUser());
        if (user != null && (user instanceof SystemUser || this.reminderRepository.findUserState(user.getId()))) {
            try {
                NpContextHolder.setContext((NpContext)this.buildContext(jobContext.getJob().getUser()));
            }
            catch (JQException e) {
                this.appendLog("\u6267\u884c\u5931\u8d25\n");
                this.appendLog("\u7535\u5b50\u6863\u6848\u5f52\u6863\u8ba1\u5212\u4efb\u52a1\u7528\u6237\u540d\u521d\u59cb\u5316\u5931\u8d25");
                logger.error("\u7535\u5b50\u6863\u6848\u5f52\u6863\u8ba1\u5212\u4efb\u52a1\u6267\u884c\u5931\u8d25\uff0c\u6267\u884c\u7684\u7528\u6237\u540d\u521d\u59cb\u5316\u5931\u8d25");
                return false;
            }
        } else {
            this.appendLog("\u6267\u884c\u5931\u8d25\n");
            this.appendLog("\u8ba1\u5212\u4efb\u52a1\u7528\u6237\u540d\u4e0d\u53ef\u7528");
            logger.error("\u7535\u5b50\u6863\u6848\u5f52\u6863\u8ba1\u5212\u4efb\u52a1\u6267\u884c\u5931\u8d25\uff0c\u6267\u884c\u7684\u7528\u6237\u540d\u4e0d\u53ef\u7528");
            return false;
        }
        try {
            if (StringUtils.isEmpty((String)iLRunnerEntity.getTaskId())) {
                this.appendLog("\u6267\u884c\u5931\u8d25\n");
                this.appendLog("\u8bf7\u9009\u62e9\u4efb\u52a1\u3002\n");
                return false;
            }
            String taskId = iLRunnerEntity.getTaskId();
            String schemeId = iLRunnerEntity.getSchemeId();
            PeriodWrapper periodWrapper = this.initParamPeriod(iLRunnerEntity);
            logger.info("periodWrapper:{}", (Object)periodWrapper.toString());
            String startPeriodString = periodWrapper.toString();
            String endPeriodString = periodWrapper.toString();
            String adjustCode = iLRunnerEntity.getAdjustCode();
            List<String> orgCodeList = iLRunnerEntity.getOrgCodeList();
            List<Map<String, String>> fromKeyListMap = iLRunnerEntity.getFromKeyListMap();
            List<String> formKeyList = fromKeyListMap.stream().map(fromKeyMap -> (String)fromKeyMap.get("formKey")).collect(Collectors.toList());
            String formKeyStr = JsonUtils.writeValueAsString(this.getArchiveConfigFormInfos(formKeyList));
            ArchiveContext context = new ArchiveContext();
            context.setTaskId(taskId);
            context.setSchemeId(schemeId);
            context.setOrgCodeList(orgCodeList);
            context.setStartPeriodString(startPeriodString);
            context.setEndPeriodString(endPeriodString);
            context.setExcelFormInfos(formKeyStr);
            context.setStartAdjustCode(adjustCode);
            context.setEndAdjustCode(adjustCode);
            context.setOrgType(iLRunnerEntity.getOrgType());
            String id = this.gcArchiveService.batchDoActionSave(context);
            this.gcArchiveService.batchDoActionStart(id);
        }
        catch (Exception e) {
            logger.error("\u7535\u5b50\u6863\u6848\u5f52\u6863\u8ba1\u5212\u4efb\u52a1\u6267\u884c\u5931\u8d25", e);
            this.appendLog("\u6267\u884c\u5931\u8d25\n");
            this.appendLog("\u7535\u5b50\u6863\u6848\u5f52\u6863\u6267\u884c\u65e5\u5fd7\uff1a");
            this.appendLog("\u7535\u5b50\u6863\u6848\u5f52\u6863\u6267\u884c\u5931\u8d25\uff1a" + e.getMessage());
            return false;
        }
        PeriodWrapper periodWrapper = new PeriodWrapper();
        try {
            periodWrapper = this.initParamPeriod(iLRunnerEntity);
            DefaultPeriodAdapter periodAdapter = new DefaultPeriodAdapter();
            String periodShowTitle = periodAdapter.getPeriodTitle(periodWrapper);
            String taskId = iLRunnerEntity.getTaskId();
            String taskTitle = this.iDesignTimeViewController.queryTaskDefine(taskId).getTitle();
            this.archiveMessageSenderService.sendSimpleMessage(iLRunnerEntity.getUserList(), periodShowTitle, taskTitle);
        }
        catch (ParseException e) {
            throw new BusinessRuntimeException((Throwable)e);
        }
        this.appendLog("\u6267\u884c\u6210\u529f\n");
        logger.info("\u7535\u5b50\u6863\u6848\u5f52\u6863\u8ba1\u5212\u4efb\u52a1\u6267\u884c\u6210\u529f");
        return true;
    }

    private List<ArchiveConfigFormInfo> getArchiveConfigFormInfos(List<String> formkeys) {
        ArrayList<ArchiveConfigFormInfo> forms = new ArrayList<ArchiveConfigFormInfo>();
        if (CollectionUtils.isEmpty(formkeys)) {
            return forms;
        }
        for (String formKey : formkeys) {
            ArchiveConfigFormInfo formInfo = new ArchiveConfigFormInfo();
            formInfo.setFormKey(formKey);
            FormDefine formDefine = this.runTimeViewController.queryFormById(formKey);
            if (null != formDefine) {
                formInfo.setFormType("form");
            } else {
                FormGroupDefine formGroupDefine = this.runTimeViewController.queryFormGroup(formKey);
                if (null != formGroupDefine) {
                    formInfo.setFormType("group");
                }
            }
            forms.add(formInfo);
        }
        return forms;
    }

    private PeriodWrapper getPeriodForScheme(String schemeId) {
        FormSchemeDefine formScheme = this.runtimeView.getFormScheme(schemeId);
        return TaskPeriodUtils.getCurrentPeriod((int)formScheme.getPeriodType().type());
    }

    private PeriodWrapper initParamPeriod(ILRunnerEntity iLRunnerEntity) throws ParseException {
        PeriodWrapper periodWrapper;
        block4: {
            block3: {
                if (StringUtils.isNotEmpty((String)iLRunnerEntity.getLibraryDate())) {
                    return new PeriodWrapper(iLRunnerEntity.getLibraryDate());
                }
                periodWrapper = this.getPeriodForScheme(iLRunnerEntity.getSchemeId());
                if (iLRunnerEntity.getOffset() >= 0) break block3;
                DefaultPeriodAdapter defaultPeriodAdapter = new DefaultPeriodAdapter();
                for (int index = 0; index < Math.abs(iLRunnerEntity.getOffset()); ++index) {
                    defaultPeriodAdapter.priorPeriod(periodWrapper);
                }
                break block4;
            }
            if (iLRunnerEntity.getOffset() <= 0) break block4;
            DefaultPeriodAdapter defaultPeriodAdapter = new DefaultPeriodAdapter();
            for (int index = 0; index < iLRunnerEntity.getOffset(); ++index) {
                defaultPeriodAdapter.nextPeriod(periodWrapper);
            }
        }
        return periodWrapper;
    }

    public PeriodWrapper getCurrentPeriod(int periodType, Calendar date) {
        int year = date.get(1);
        int month = date.get(2);
        int week = date.get(3);
        int day = date.get(6);
        int dayOfMonth = date.get(5);
        date.get(7);
        int acctYear = year;
        int acctPriod = 1;
        if (1 == periodType) {
            acctPriod = 1;
        } else if (2 == periodType) {
            acctPriod = (month + 1) / 7 + 1;
        } else if (3 == periodType) {
            acctPriod = (month + 1) / 4 + 1;
        } else if (4 == periodType) {
            acctPriod = month + 1;
        } else if (5 == periodType) {
            acctPriod = month * 3 + (dayOfMonth - 1) / 10 + 1;
        } else if (6 == periodType) {
            acctPriod = day;
        } else if (7 == periodType) {
            acctPriod = week;
        } else if (8 == periodType) {
            // empty if block
        }
        return new PeriodWrapper(acctYear, periodType, acctPriod);
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

