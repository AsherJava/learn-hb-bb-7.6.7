/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.JobContext
 *  com.jiuqi.bi.core.jobs.JobExecutionException
 *  com.jiuqi.bi.core.jobs.JobExecutor
 *  com.jiuqi.bi.core.jobs.JobsException
 *  com.jiuqi.bi.core.jobs.defaultlog.Logger
 *  com.jiuqi.bi.core.jobs.oss.JobByteResult
 *  com.jiuqi.bi.core.jobs.oss.JobFileResult
 *  com.jiuqi.bi.monitor.IProgressMonitor
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.asynctask.dao.AsyncTaskDao
 *  com.jiuqi.np.asynctask.impl.SimpleAsyncTaskMonitor
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.core.context.ContextIdentity
 *  com.jiuqi.np.core.context.ContextUser
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.core.context.impl.NpContextIdentity
 *  com.jiuqi.np.core.context.impl.NpContextImpl
 *  com.jiuqi.np.core.context.impl.NpContextUser
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.np.user.User
 *  com.jiuqi.np.user.service.SystemUserService
 *  com.jiuqi.np.user.service.UserService
 *  com.jiuqi.nvwa.jobmanager.exception.PlanTaskError
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 *  org.apache.commons.lang3.StringUtils
 *  org.json.JSONObject
 */
package nr.midstore.core.internal.job.service;

import com.jiuqi.bi.core.jobs.JobContext;
import com.jiuqi.bi.core.jobs.JobExecutionException;
import com.jiuqi.bi.core.jobs.JobExecutor;
import com.jiuqi.bi.core.jobs.JobsException;
import com.jiuqi.bi.core.jobs.oss.JobByteResult;
import com.jiuqi.bi.core.jobs.oss.JobFileResult;
import com.jiuqi.bi.monitor.IProgressMonitor;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.asynctask.dao.AsyncTaskDao;
import com.jiuqi.np.asynctask.impl.SimpleAsyncTaskMonitor;
import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.core.context.ContextIdentity;
import com.jiuqi.np.core.context.ContextUser;
import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.core.context.impl.NpContextIdentity;
import com.jiuqi.np.core.context.impl.NpContextImpl;
import com.jiuqi.np.core.context.impl.NpContextUser;
import com.jiuqi.np.log.LogHelper;
import com.jiuqi.np.user.User;
import com.jiuqi.np.user.service.SystemUserService;
import com.jiuqi.np.user.service.UserService;
import com.jiuqi.nvwa.jobmanager.exception.PlanTaskError;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Optional;
import java.util.UUID;
import nr.midstore.core.definition.bean.MidstoreResultObject;
import nr.midstore.core.definition.bean.MistoreWorkFailInfo;
import nr.midstore.core.definition.bean.MistoreWorkFormInfo;
import nr.midstore.core.definition.bean.MistoreWorkResultObject;
import nr.midstore.core.definition.bean.MistoreWorkUnitInfo;
import nr.midstore.core.definition.common.ExchangeModeType;
import nr.midstore.core.definition.common.PublishStateType;
import nr.midstore.core.definition.db.MidstoreException;
import nr.midstore.core.definition.dto.MidstoreSchemeDTO;
import nr.midstore.core.definition.dto.MidstoreSchemeInfoDTO;
import nr.midstore.core.definition.service.IMidstoreSchemeInfoService;
import nr.midstore.core.definition.service.IMidstoreSchemeService;
import nr.midstore.core.util.IMidstoreDimensionService;
import nr.midstore.core.util.IMidstoreReadWriteService;
import nr.midstore.core.util.auth.IMidstoreFormDataAccess;
import nr.midstore.core.work.service.IMidstoreExcuteGetService;
import nr.midstore.core.work.service.IMidstoreExcutePostService;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class MidstoreWorkJobExecutor
extends JobExecutor {
    private static final Logger logger = LoggerFactory.getLogger(MidstoreWorkJobExecutor.class);
    @Autowired
    private IMidstoreSchemeService midstoreSchemeSevice;
    @Autowired
    private IMidstoreSchemeInfoService schemeInfoSevice;
    @Autowired
    private IMidstoreExcuteGetService getDataSerivce;
    @Autowired
    private IMidstoreExcutePostService postDataSerivce;
    @Autowired
    private AsyncTaskDao asyncTaskDao;
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;
    @Autowired
    private UserService<User> userService;
    @Autowired
    private SystemUserService systemUserService;
    @Autowired(required=false)
    private IMidstoreFormDataAccess formAccessService;
    @Autowired
    private IMidstoreReadWriteService readWriteService;
    @Autowired
    private IMidstoreDimensionService dimensionService;
    private static final String MIDSTOREJOBWORKNAME = "Midsotre-Work-GetData";

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void execute(JobContext jobContext) throws JobExecutionException {
        com.jiuqi.bi.core.jobs.defaultlog.Logger logger2 = jobContext.getDefaultLogger();
        if (StringUtils.isEmpty((CharSequence)jobContext.getJob().getExtendedConfig())) {
            logger2.info("\u672a\u5e26\u6709\u6267\u884c\u53c2\u6570");
            return;
        }
        JSONObject object = new JSONObject(jobContext.getJob().getExtendedConfig());
        String schemeKey = (String)object.get("midstoreScheme");
        if (StringUtils.isEmpty((CharSequence)schemeKey)) {
            logger.info("\u4ea4\u6362\u65b9\u6848\u4e3a\u7a7a");
            logger2.info("\u4ea4\u6362\u65b9\u6848\u4e3a\u7a7a");
            return;
        }
        if (this.midstoreSchemeSevice == null) {
            this.midstoreSchemeSevice = (IMidstoreSchemeService)ApplicationContextRegister.getBean(IMidstoreSchemeService.class);
        }
        if (this.schemeInfoSevice == null) {
            this.schemeInfoSevice = (IMidstoreSchemeInfoService)ApplicationContextRegister.getBean(IMidstoreSchemeInfoService.class);
        }
        if (this.getDataSerivce == null) {
            this.getDataSerivce = (IMidstoreExcuteGetService)ApplicationContextRegister.getBean(IMidstoreExcuteGetService.class);
        }
        if (this.postDataSerivce == null) {
            this.postDataSerivce = (IMidstoreExcutePostService)ApplicationContextRegister.getBean(IMidstoreExcutePostService.class);
        }
        if (this.asyncTaskDao == null) {
            this.asyncTaskDao = (AsyncTaskDao)ApplicationContextRegister.getBean(AsyncTaskDao.class);
        }
        if (this.applicationEventPublisher == null) {
            this.applicationEventPublisher = (ApplicationEventPublisher)ApplicationContextRegister.getBean(ApplicationEventPublisher.class);
        }
        if (this.userService == null) {
            this.userService = (UserService)ApplicationContextRegister.getBean(UserService.class);
        }
        if (this.systemUserService == null) {
            this.systemUserService = (SystemUserService)ApplicationContextRegister.getBean(SystemUserService.class);
        }
        String user = jobContext.getJob().getUser();
        NpContextImpl npContext = null;
        if (StringUtils.isNotEmpty((CharSequence)user)) {
            try {
                npContext = this.buildContext(user);
                NpContextHolder.setContext((NpContext)npContext);
            }
            catch (JQException e) {
                LogHelper.warn((String)"\u8ba1\u5212\u4efb\u52a1", (String)"\u6267\u884c\u8ba1\u5212\u4efb\u52a1", (String)("\u8bbe\u7f6e\u6267\u884c\u4eba\u2018" + user + "\u2019\u7684\u4fe1\u606f\u65f6\u53d1\u751f\u4e86\u5f02\u5e38\uff1a" + e.getMessage()));
            }
        } else {
            logger2.info("\u6ca1\u6709\u6307\u5b9a\u6267\u884c\u7528\u6237");
            throw new JobExecutionException("\u6ca1\u6709\u6307\u5b9a\u6267\u884c\u7528\u6237");
        }
        try {
            SimpleAsyncTaskMonitor sMonitor;
            String taskId;
            MidstoreSchemeDTO scheme = this.midstoreSchemeSevice.getByKey(schemeKey);
            if (scheme == null) {
                logger2.info("\u4ea4\u6362\u65b9\u6848\u4e0d\u5b58\u5728\uff1a" + schemeKey);
                return;
            }
            logger2.info("\u4ea4\u6362\u65b9\u6848\uff1a" + scheme.getTitle() + ",\u8fdb\u884c" + (Object)((Object)scheme.getExchangeMode()) == null ? scheme.getExchangeMode().getTitle() : "");
            MidstoreSchemeInfoDTO shcemeInfo = this.schemeInfoSevice.getBySchemeKey(schemeKey);
            if (shcemeInfo == null) {
                logger2.info("\u4ea4\u6362\u65b9\u6848\u6269\u5c55\u4fe1\u606f\u4e0d\u5b58\u5728\uff1a" + schemeKey);
                return;
            }
            if (shcemeInfo.getPublishState() == PublishStateType.PUBLISHSTATE_NONE) {
                String msg = "\u4ea4\u6362\u65b9\u6848\u672a\u53d1\u5e03\uff1a" + scheme.getTitle();
                logger2.info(msg);
                this.updateExcutePlanInfo(shcemeInfo, msg);
                return;
            }
            if (shcemeInfo.getPublishState() == PublishStateType.PUBLISHSTATE_FAIL) {
                String msg = "\u4ea4\u6362\u65b9\u6848\u53d1\u5e03\u5931\u8d25\uff1a" + schemeKey + "," + scheme.getCode() + "," + scheme.getTitle();
                logger2.info(msg);
                this.updateExcutePlanInfo(shcemeInfo, msg);
                return;
            }
            logger2.info("\u4efb\u52a1\u5f00\u59cb");
            logger2.info("\u4ea4\u6362\u65b9\u6848\uff1a" + scheme.getTitle());
            IProgressMonitor monitor = jobContext.getMonitor();
            monitor.prompt("\u5f00\u59cb\u8fd0\u884c");
            monitor.startTask(jobContext.getInstanceId(), new int[]{10, 60, 30});
            monitor.stepIn();
            MidstoreResultObject result = null;
            if (scheme.getExchangeMode() == ExchangeModeType.EXCHANGE_GET) {
                taskId = UUID.randomUUID().toString();
                sMonitor = new SimpleAsyncTaskMonitor(this.asyncTaskDao, this.applicationEventPublisher, taskId, MIDSTOREJOBWORKNAME);
                try {
                    result = this.getDataSerivce.excuteGetDataByUser(scheme.getKey(), user, (AsyncTaskMonitor)sMonitor);
                }
                catch (MidstoreException e) {
                    logger2.error("\u6570\u636e\u63d0\u53d6\u51fa " + e.getMessage(), (Throwable)e);
                }
            } else if (scheme.getExchangeMode() == ExchangeModeType.EXCHANGE_POST) {
                taskId = UUID.randomUUID().toString();
                sMonitor = new SimpleAsyncTaskMonitor(this.asyncTaskDao, this.applicationEventPublisher, taskId, MIDSTOREJOBWORKNAME);
                try {
                    result = this.postDataSerivce.excutePostDataByUser(scheme.getKey(), user, (AsyncTaskMonitor)sMonitor);
                }
                catch (MidstoreException e) {
                    logger2.error(e.getMessage(), (Throwable)e);
                }
            }
            String workTitle = scheme.getTitle() + "-" + scheme.getExchangeMode().getTitle();
            if (result != null) {
                MistoreWorkResultObject workResult = null;
                if (result.getWorkResults() != null && result.getWorkResults().size() > 0) {
                    workResult = result.getWorkResults().get(0);
                }
                if (result.isSuccess()) {
                    logger2.info(workTitle + "\uff0c\u6210\u529f\u5b8c\u6210");
                } else {
                    logger2.info(workTitle + "\uff0c\u5931\u8d25\u4fe1\u606f\uff1a" + result.getMessage());
                }
                if (workResult != null) {
                    logger2.info(workTitle + "\uff0c\u62a5\u8868\u65f6\u671f:" + workResult.getNrPeriodCode() + "\uff0c\u4e2d\u95f4\u5e93\u65f6\u671f:" + workResult.getMidstorePeriodCode());
                    logger2.info(scheme.getExchangeMode().getTitle() + "\u5355\u4f4d\u603b\u6570\uff1a" + workResult.getUnitCount());
                }
                if (workResult != null && workResult.getSuccessUnits() != null) {
                    logger2.info("\u6210\u529f\u5355\u4f4d\u6570\uff1a" + workResult.getSuccessUnits().size());
                }
                if (workResult != null && workResult.getFailUnits() != null) {
                    logger2.info("\u5931\u8d25\u5355\u4f4d\u6570\uff1a" + workResult.getFailUnits().size());
                }
                if (workResult != null && workResult.getFailInfoList() != null) {
                    for (MistoreWorkFailInfo failInfo : workResult.getFailInfoList()) {
                        logger2.info(failInfo.getMessage() + ":");
                        for (MistoreWorkUnitInfo unitInfo : failInfo.getUnitInfos().values()) {
                            logger2.info("  " + unitInfo.getUnitCode() + " " + (StringUtils.isNotEmpty((CharSequence)unitInfo.getUnitTitle()) ? unitInfo.getUnitTitle() : ""));
                            if (unitInfo.getFormInfos() != null && unitInfo.getFormInfos().size() > 0) {
                                for (MistoreWorkFormInfo formInfo : unitInfo.getFormInfos().values()) {
                                    logger2.info("    " + formInfo.getFormCode() + " " + (StringUtils.isNotEmpty((CharSequence)formInfo.getFormTitle()) ? formInfo.getFormTitle() : "") + "  " + formInfo.getMessage());
                                }
                                continue;
                            }
                            if (unitInfo.getTableInfos() == null || unitInfo.getTableInfos().size() <= 0) continue;
                            for (MistoreWorkFormInfo tableInfo : unitInfo.getTableInfos().values()) {
                                logger2.info("    " + tableInfo.getFormCode() + " " + (StringUtils.isNotEmpty((CharSequence)tableInfo.getFormTitle()) ? tableInfo.getFormTitle() : "") + "  " + tableInfo.getMessage());
                            }
                        }
                    }
                }
                if (workResult != null && workResult.getSuccessUnits().size() > 0) {
                    logger2.info("\u6210\u529f\u5355\u4f4d\u5217\u8868\uff1a");
                    for (MistoreWorkUnitInfo unitInfo : workResult.getSuccessUnits().values()) {
                        logger2.info("  " + unitInfo.getUnitCode() + " " + (StringUtils.isNotEmpty((CharSequence)unitInfo.getUnitTitle()) ? unitInfo.getUnitTitle() : ""));
                    }
                }
            }
            monitor.finishTask(jobContext.getInstanceId());
            jobContext.setResult(100, "\u4efb\u52a1\u5b8c\u6210");
            try {
                shcemeInfo.setExcutePlanInfo(workTitle + "-\u5b8c\u6210\uff01");
                this.schemeInfoSevice.update(shcemeInfo);
            }
            catch (MidstoreException e) {
                logger.error(e.getMessage(), e);
            }
        }
        finally {
            if (npContext != null) {
                NpContextHolder.clearContext();
            }
        }
    }

    private void updateExcutePlanInfo(MidstoreSchemeInfoDTO shcemeInfo, String info) {
        try {
            shcemeInfo.setExcutePlanInfo(info);
            this.schemeInfoSevice.update(shcemeInfo);
        }
        catch (MidstoreException e) {
            logger.error(e.getMessage(), e);
        }
    }

    private void modifyPlanInfo(MidstoreSchemeInfoDTO shcemeInfo, String Message) {
        try {
            shcemeInfo.setCleanPlanInfo(Message);
            this.schemeInfoSevice.update(shcemeInfo);
        }
        catch (MidstoreException e) {
            logger.error(e.getMessage(), e);
        }
    }

    private void saveResult(JobContext context) throws JobsException, UnsupportedEncodingException {
        ArrayList<Object> jobResults = new ArrayList<Object>();
        JobFileResult jobFileResult = new JobFileResult("\u4efb\u52a1\u751f\u6210\u7684\u6587\u4ef6-1", "D:/tt.txt");
        jobFileResult.setExtName("txt");
        jobResults.add(jobFileResult);
        JobByteResult jobByteResult = new JobByteResult("\u4efb\u52a1\u751f\u6210\u7684Byte-1.txt", "\u4efb\u52a1\u751f\u6210\u7684Byte-1".getBytes("UTF-8"));
        jobResults.add(jobByteResult);
        context.saveResultFile(jobResults);
    }

    private MidstoreSchemeInfoDTO getMidstoreJobConf(JobContext jobContext) {
        byte[] buffer = Base64.getDecoder().decode(jobContext.getJob().getExtendedConfig());
        ByteArrayInputStream bufferInput = new ByteArrayInputStream(buffer);
        MidstoreSchemeInfoDTO midstoreSchemeInfo = null;
        try (ObjectInputStream input = new ObjectInputStream(bufferInput);){
            midstoreSchemeInfo = (MidstoreSchemeInfoDTO)input.readObject();
        }
        catch (IOException | ClassNotFoundException e) {
            logger.info(e.getMessage());
        }
        return midstoreSchemeInfo;
    }

    public NpContextImpl buildContext(String userName) throws JQException {
        NpContextImpl npContext = (NpContextImpl)NpContextHolder.createEmptyContext();
        npContext.setTenant("__default_tenant__");
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
        identity.setOrgCode(contextUser.getOrgCode());
        return identity;
    }

    protected NpContextUser buildUserContext(String userName) throws JQException {
        NpContextUser userContext = new NpContextUser();
        User user = this.getUserByUserName(userName);
        if (user == null) {
            throw new JQException((ErrorEnum)PlanTaskError.QUERY_USER);
        }
        userContext.setId(user.getId());
        userContext.setName(user.getName());
        userContext.setNickname(user.getNickname());
        userContext.setDescription(user.getDescription());
        userContext.setOrgCode(user.getOrgCode());
        return userContext;
    }

    public User getUserByUserName(String userName) {
        if (StringUtils.isEmpty((CharSequence)userName)) {
            return null;
        }
        User userFound = null;
        if (this.systemUserService == null) {
            logger.info("\u7cfb\u7edf\u7528\u6237\u670d\u52a1\u63a5\u53e3systemUserService\u4e3a\u7a7a");
        } else {
            Optional sysuser = this.systemUserService.findByUsername(userName);
            if (sysuser.isPresent()) {
                userFound = (User)sysuser.get();
            } else if (this.userService == null) {
                logger.info("\u666e\u901a\u7528\u6237\u670d\u52a1\u63a5\u53e3userService\u4e3a\u7a7a");
            } else {
                Optional user = this.userService.findByUsername(userName);
                if (user.isPresent()) {
                    userFound = (User)user.get();
                }
            }
        }
        return userFound;
    }
}

