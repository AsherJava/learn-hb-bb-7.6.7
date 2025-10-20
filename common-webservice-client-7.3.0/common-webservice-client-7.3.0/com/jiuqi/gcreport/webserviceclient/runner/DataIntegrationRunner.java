/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.JobContext
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
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
 *  com.jiuqi.np.period.DefaultPeriodAdapter
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.np.user.User
 *  com.jiuqi.np.user.service.SystemUserService
 *  com.jiuqi.np.user.service.UserService
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.reminder.infer.ReminderRepository
 *  com.jiuqi.nvwa.jobmanager.exception.PlanTaskError
 */
package com.jiuqi.gcreport.webserviceclient.runner;

import com.jiuqi.bi.core.jobs.JobContext;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.plantask.extend.job.PlanTaskRunner;
import com.jiuqi.common.plantask.extend.job.Runner;
import com.jiuqi.gcreport.webserviceclient.task.DataIntegrationAsyncTask;
import com.jiuqi.gcreport.webserviceclient.vo.DataIntegrationtParam;
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
import com.jiuqi.np.user.User;
import com.jiuqi.np.user.service.SystemUserService;
import com.jiuqi.np.user.service.UserService;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

@PlanTaskRunner(id="F99D8DF671FB45FD9FBB192FCB270717", settingPage="dataIntegrationRunnerAdvanceConfig", name="com.jiuqi.gcreport.webserviceclient.runner.DataIntegrationRunner", title="\u6570\u636e\u96c6\u6210\u8ba1\u5212\u4efb\u52a1")
public class DataIntegrationRunner
extends Runner {
    private Logger logger = LoggerFactory.getLogger(DataIntegrationRunner.class);
    @Autowired
    private DataIntegrationAsyncTask dataIntegrationAsyncTask;
    @Autowired
    private SystemUserService systemUserService;
    @Autowired
    private UserService<User> userService;
    @Autowired
    private ReminderRepository reminderRepository;

    public boolean excute(JobContext jobContext) {
        this.logger.info("\u5f00\u59cb\u6570\u636e\u96c6\u6210\u8ba1\u5212\u4efb\u52a1");
        this.appendLog("\u6267\u884c\u6d41\u7a0b\u542f\u52a8:\n");
        DataIntegrationtParam dataIntegrationtParam = (DataIntegrationtParam)JsonUtils.readValue((String)jobContext.getJob().getExtendedConfig(), DataIntegrationtParam.class);
        if (dataIntegrationtParam == null) {
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
                this.appendLog("\u6570\u636e\u96c6\u6210\u8ba1\u5212\u4efb\u52a1\u7528\u6237\u540d\u521d\u59cb\u5316\u5931\u8d25");
                this.logger.error("\u6570\u636e\u96c6\u6210\u8ba1\u5212\u4efb\u52a1\u6267\u884c\u5931\u8d25\uff0c\u6267\u884c\u7684\u7528\u6237\u540d\u521d\u59cb\u5316\u5931\u8d25");
                return false;
            }
        } else {
            this.appendLog("\u6267\u884c\u5931\u8d25\n");
            this.appendLog("\u8ba1\u5212\u4efb\u52a1\u7528\u6237\u540d\u4e0d\u53ef\u7528\uff1a\u7528\u6237\u4e0d\u5b58\u5728\u6216\u8005\u7528\u4e8e\u5904\u4e8e\u9501\u5b9a\u3001\u505c\u7528\u72b6\u6001");
            this.logger.error("\u6570\u636e\u96c6\u6210\u8ba1\u5212\u4efb\u52a1\u6267\u884c\u5931\u8d25\uff0c\u6267\u884c\u7684\u7528\u6237\u540d\u4e0d\u53ef\u7528\uff1a\u7528\u6237\u4e0d\u5b58\u5728\u6216\u8005\u7528\u4e8e\u5904\u4e8e\u9501\u5b9a\u3001\u505c\u7528\u72b6\u6001");
            return false;
        }
        try {
            this.initParamPeriod(dataIntegrationtParam);
            this.dataIntegrationAsyncTask.execute(dataIntegrationtParam, null);
        }
        catch (Exception e) {
            this.logger.error("\u6570\u636e\u96c6\u6210\u8ba1\u5212\u4efb\u52a1\u6267\u884c\u5931\u8d25", e);
            this.appendLog("\u6267\u884c\u5931\u8d25\n");
            this.appendLog("\u6570\u636e\u96c6\u6210\u6267\u884c\u65e5\u5fd7\uff1a" + dataIntegrationtParam.getExecuteMessages().toString() + "\n");
            this.appendLog("\u6570\u636e\u96c6\u6210\u6267\u884c\u5931\u8d25\uff1a" + e.getMessage());
            return false;
        }
        if (dataIntegrationtParam.isStatus()) {
            this.appendLog("\u6267\u884c\u6210\u529f\n");
            this.appendLog("\u6570\u636e\u96c6\u6210\u6267\u884c\u65e5\u5fd7\uff1a" + dataIntegrationtParam.getExecuteMessages().toString());
            this.logger.info("\u6570\u636e\u96c6\u6210\u8ba1\u5212\u4efb\u52a1\u6267\u884c\u6210\u529f");
            return true;
        }
        this.logger.error("\u6570\u636e\u96c6\u6210\u8ba1\u5212\u4efb\u52a1\u6267\u884c\u5931\u8d25");
        this.appendLog("\u6267\u884c\u5931\u8d25\n");
        this.appendLog("\u6570\u636e\u96c6\u6210\u6267\u884c\u65e5\u5fd7\uff1a" + dataIntegrationtParam.getExecuteMessages().toString() + "\n");
        return false;
    }

    private void initParamPeriod(DataIntegrationtParam dataIntegrationtParam) throws ParseException {
        PeriodWrapper periodWrapper;
        if (StringUtils.isNotEmpty((String)dataIntegrationtParam.getLibraryDate())) {
            String dateStr = dataIntegrationtParam.getLibraryDate();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
            Date date = sdf.parse(dateStr);
            Calendar time = Calendar.getInstance();
            time.setTime(date);
            periodWrapper = this.getCurrentPeriod(((IRunTimeViewController)SpringContextUtils.getBean(IRunTimeViewController.class)).getFormScheme(dataIntegrationtParam.getFormSchemeKey()).getPeriodType().type(), time);
        } else {
            periodWrapper = this.getPeriodForScheme(dataIntegrationtParam.getFormSchemeKey());
            if (dataIntegrationtParam.getOffset() < 0) {
                DefaultPeriodAdapter defaultPeriodAdapter = new DefaultPeriodAdapter();
                for (int index = 0; index < Math.abs(dataIntegrationtParam.getOffset()); ++index) {
                    defaultPeriodAdapter.priorPeriod(periodWrapper);
                }
            } else if (dataIntegrationtParam.getOffset() > 0) {
                DefaultPeriodAdapter defaultPeriodAdapter = new DefaultPeriodAdapter();
                for (int index = 0; index < dataIntegrationtParam.getOffset(); ++index) {
                    defaultPeriodAdapter.nextPeriod(periodWrapper);
                }
            }
        }
        dataIntegrationtParam.setPeriodString(periodWrapper.toString());
        dataIntegrationtParam.setDimensionSet(this.getDimensionSet(periodWrapper, dataIntegrationtParam));
        dataIntegrationtParam.setFormKeyList(dataIntegrationtParam.getFromKeyListMap().stream().flatMap(map -> map.values().stream()).collect(Collectors.toList()));
    }

    private PeriodWrapper getPeriodForScheme(String schemeId) {
        return this.getCurrentPeriod(((IRunTimeViewController)SpringContextUtils.getBean(IRunTimeViewController.class)).getFormScheme(schemeId).getPeriodType().type(), null);
    }

    private Map<String, DimensionValue> getDimensionSet(PeriodWrapper periodWrapper, DataIntegrationtParam dataIntegrationtParam) {
        HashMap<String, DimensionValue> dimensionSet = new HashMap<String, DimensionValue>();
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
        orgTypeDimen.setValue(dataIntegrationtParam.getOrgType());
        dimensionSet.put("MD_GCORGTYPE", orgTypeDimen);
        return dimensionSet;
    }

    public PeriodWrapper getCurrentPeriod(int periodType, Calendar date) {
        if (date == null) {
            date = Calendar.getInstance();
        }
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

