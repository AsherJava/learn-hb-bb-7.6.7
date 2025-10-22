/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.JobContext
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.plantask.extend.job.PlanTaskRunner
 *  com.jiuqi.common.plantask.extend.job.Runner
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
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.period.DefaultPeriodAdapter
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.np.user.User
 *  com.jiuqi.np.user.service.SystemUserService
 *  com.jiuqi.np.user.service.UserService
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.dataentry.bean.DataEntryContext
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.reminder.infer.ReminderRepository
 *  com.jiuqi.nvwa.jobmanager.exception.PlanTaskError
 *  javax.annotation.Resource
 */
package com.jiuqi.gcreport.intermediatelibrary.runner;

import com.jiuqi.bi.core.jobs.JobContext;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.plantask.extend.job.PlanTaskRunner;
import com.jiuqi.common.plantask.extend.job.Runner;
import com.jiuqi.gcreport.common.task.common.TaskPeriodUtils;
import com.jiuqi.gcreport.intermediatelibrary.entity.ILExtractCondition;
import com.jiuqi.gcreport.intermediatelibrary.entity.ILRunnerEntity;
import com.jiuqi.gcreport.intermediatelibrary.service.IntermediateLibraryService;
import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.core.context.ContextIdentity;
import com.jiuqi.np.core.context.ContextUser;
import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.core.context.impl.NpContextIdentity;
import com.jiuqi.np.core.context.impl.NpContextImpl;
import com.jiuqi.np.core.context.impl.NpContextUser;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.period.DefaultPeriodAdapter;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.np.user.User;
import com.jiuqi.np.user.service.SystemUserService;
import com.jiuqi.np.user.service.UserService;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.dataentry.bean.DataEntryContext;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.reminder.infer.ReminderRepository;
import com.jiuqi.nvwa.jobmanager.exception.PlanTaskError;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

@PlanTaskRunner(id="97B18196831F446E8F1E5579C0A7956E", settingPage="intermediateLibraryConfig", name="com.jiuqi.gcreport.intermediatelibrary.runner.IntermediateLibraryRunner", title="\u6570\u636e\u4ea4\u6362\u4e2d\u5fc3\u8ba1\u5212\u4efb\u52a1")
public class IntermediateLibraryRunner
extends Runner {
    private final Logger logger = LoggerFactory.getLogger(((Object)((Object)this)).getClass());
    @Autowired
    private IntermediateLibraryService intermediateLibraryService;
    @Autowired
    private SystemUserService systemUserService;
    @Autowired
    private IRunTimeViewController runtimeView;
    @Resource
    private UserService<User> userService;
    @Resource
    private IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    @Autowired
    private IEntityViewRunTimeController iRunTimeEntityTableService;
    @Autowired
    private ReminderRepository reminderRepository;

    public boolean excute(JobContext jobContext) {
        this.logger.info("\u5f00\u59cb\u6570\u636e\u4ea4\u6362\u4e2d\u5fc3\u8ba1\u5212\u4efb\u52a1");
        this.appendLog("\u6267\u884c\u6d41\u7a0b\u542f\u52a8:\n");
        ILRunnerEntity iLRunnerEntity = (ILRunnerEntity)JsonUtils.readValue((String)jobContext.getJob().getExtendedConfig(), ILRunnerEntity.class);
        if (iLRunnerEntity == null) {
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
                this.appendLog("\u6570\u636e\u4ea4\u6362\u4e2d\u5fc3\u8ba1\u5212\u4efb\u52a1\u7528\u6237\u540d\u521d\u59cb\u5316\u5931\u8d25");
                this.logger.error("\u6570\u636e\u4ea4\u6362\u4e2d\u5fc3\u8ba1\u5212\u4efb\u52a1\u6267\u884c\u5931\u8d25\uff0c\u6267\u884c\u7684\u7528\u6237\u540d\u521d\u59cb\u5316\u5931\u8d25");
                return false;
            }
        } else {
            this.appendLog("\u6267\u884c\u5931\u8d25\n");
            this.appendLog("\u8ba1\u5212\u4efb\u52a1\u7528\u6237\u540d\u4e0d\u53ef\u7528");
            this.logger.error("\u6570\u636e\u4ea4\u6362\u4e2d\u5fc3\u8ba1\u5212\u4efb\u52a1\u6267\u884c\u5931\u8d25\uff0c\u6267\u884c\u7684\u7528\u6237\u540d\u4e0d\u53ef\u7528");
            return false;
        }
        ILExtractCondition iLExtractCondition = new ILExtractCondition();
        try {
            if (StringUtils.isEmpty((String)iLRunnerEntity.getTaskId())) {
                this.appendLog("\u6267\u884c\u5931\u8d25\n");
                this.appendLog("\u8bf7\u9009\u62e9\u4efb\u52a1\u3002\n");
                return false;
            }
            this.initCondition(iLRunnerEntity, iLExtractCondition);
            if ("push".equals(iLRunnerEntity.getDepartureAction())) {
                iLExtractCondition.setPushType(true);
            } else if ("extract".equals(iLRunnerEntity.getDepartureAction())) {
                iLExtractCondition.setPushType(false);
            }
            this.intermediateLibraryService.programmeHandle(iLExtractCondition);
        }
        catch (Exception e) {
            this.logger.error("\u6570\u636e\u4ea4\u6362\u4e2d\u5fc3\u8ba1\u5212\u4efb\u52a1\u6267\u884c\u5931\u8d25", e);
            this.appendLog("\u6267\u884c\u5931\u8d25\n");
            this.appendLog("\u6570\u636e\u4ea4\u6362\u4e2d\u5fc3\u6267\u884c\u65e5\u5fd7\uff1a" + iLExtractCondition.getLibraryMessages().toString() + "\n");
            this.appendLog("\u6570\u636e\u4ea4\u6362\u4e2d\u5fc3\u6267\u884c\u5931\u8d25\uff1a" + e.getMessage());
            return false;
        }
        this.appendLog("\u6267\u884c\u6210\u529f\n");
        this.appendLog("\u6570\u636e\u4ea4\u6362\u4e2d\u5fc3\u6267\u884c\u65e5\u5fd7\uff1a" + iLExtractCondition.getLibraryMessages().toString());
        this.logger.info("\u6570\u636e\u4ea4\u6362\u4e2d\u5fc3\u8ba1\u5212\u4efb\u52a1\u6267\u884c\u6210\u529f");
        return true;
    }

    private void initCondition(ILRunnerEntity iLRunnerEntity, ILExtractCondition iLExtractCondition) throws ParseException {
        iLExtractCondition.setProgrammeId(iLRunnerEntity.getProgrammeId());
        iLExtractCondition.setFormIdList(iLRunnerEntity.getFromKeyListMap().stream().map(fromKeyMap -> (String)fromKeyMap.get("formKey")).collect(Collectors.toList()));
        iLExtractCondition.setOrgIdList(iLRunnerEntity.getOrgCodeList());
        iLExtractCondition.setIsAllOrgChoose("false");
        iLExtractCondition.setEnvContext(this.initContext(iLRunnerEntity));
        iLExtractCondition.setDimensionSet(iLExtractCondition.getEnvContext().getDimensionSet());
    }

    private DataEntryContext initContext(ILRunnerEntity iLRunnerEntity) throws ParseException {
        DataEntryContext dataEntryContext = new DataEntryContext();
        dataEntryContext.setFormSchemeKey(iLRunnerEntity.getSchemeId());
        dataEntryContext.setTaskKey(iLRunnerEntity.getTaskId());
        dataEntryContext.setDimensionSet(this.getDimensionSet(iLRunnerEntity.getOrgCodeList().get(0), this.initParamPeriod(iLRunnerEntity), iLRunnerEntity.getOrgType()));
        return dataEntryContext;
    }

    private PeriodWrapper initParamPeriod(ILRunnerEntity iLRunnerEntity) throws ParseException {
        PeriodWrapper periodWrapper;
        block4: {
            block3: {
                if (StringUtils.isNotEmpty((String)iLRunnerEntity.getLibraryDate())) {
                    FormSchemeDefine formScheme = this.runtimeView.getFormScheme(iLRunnerEntity.getSchemeId());
                    String dateStr = iLRunnerEntity.getLibraryDate();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
                    Date date = sdf.parse(dateStr);
                    Calendar time = Calendar.getInstance();
                    time.setTime(date);
                    return this.getCurrentPeriod(formScheme.getPeriodType().type(), time);
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

    private PeriodWrapper getPeriodForScheme(String schemeId) {
        FormSchemeDefine formScheme = this.runtimeView.getFormScheme(schemeId);
        return TaskPeriodUtils.getCurrentPeriod((int)formScheme.getPeriodType().type());
    }

    private Map<String, DimensionValue> getDimensionSet(String orgCode, PeriodWrapper periodWrapper, String tableName) {
        HashMap<String, DimensionValue> dimensionSet = new HashMap<String, DimensionValue>();
        DimensionValue orgDimen = new DimensionValue();
        orgDimen.setName("MD_ORG");
        orgDimen.setValue(orgCode);
        dimensionSet.put("MD_ORG", orgDimen);
        DimensionValue periodDimen = new DimensionValue();
        periodDimen.setName("DATATIME");
        periodDimen.setValue(periodWrapper.toString());
        dimensionSet.put("DATATIME", periodDimen);
        DimensionValue currencyDimen = new DimensionValue();
        currencyDimen.setName("MD_CURRENCY");
        currencyDimen.setValue("CNY");
        dimensionSet.put("MD_CURRENCY", currencyDimen);
        DimensionValue orgTypeDimen = new DimensionValue();
        orgTypeDimen.setName("MD_GCORGTYPE");
        orgTypeDimen.setValue(tableName);
        dimensionSet.put("MD_GCORGTYPE", orgTypeDimen);
        return dimensionSet;
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

    private NpContextImpl buildContext(String userName) throws JQException {
        NpContextImpl npContext = (NpContextImpl)NpContextHolder.createEmptyContext();
        npContext.setTenant("__default_tenant__");
        NpContextUser contextUser = this.buildUserContext(userName);
        npContext.setUser((ContextUser)contextUser);
        NpContextIdentity identity = this.buildIdentityContext(contextUser);
        npContext.setIdentity((ContextIdentity)identity);
        return npContext;
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

