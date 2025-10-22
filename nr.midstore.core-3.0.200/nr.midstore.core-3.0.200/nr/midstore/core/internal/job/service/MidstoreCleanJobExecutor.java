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
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.UUID;
import nr.midstore.core.definition.common.PublishStateType;
import nr.midstore.core.definition.db.MidstoreException;
import nr.midstore.core.definition.dto.MidstoreSchemeDTO;
import nr.midstore.core.definition.dto.MidstoreSchemeInfoDTO;
import nr.midstore.core.definition.service.IMidstoreSchemeInfoService;
import nr.midstore.core.definition.service.IMidstoreSchemeService;
import nr.midstore.core.internal.job.service.MidstoreWorkJobExecutor;
import nr.midstore.core.work.service.IMidstoreExcuteCleanService;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class MidstoreCleanJobExecutor
extends JobExecutor {
    private static final Logger logger = LoggerFactory.getLogger(MidstoreWorkJobExecutor.class);
    @Autowired
    private IMidstoreSchemeService midstoreSchemeSevice;
    @Autowired
    private IMidstoreSchemeInfoService schemeInfoSevice;
    @Autowired
    private IMidstoreExcuteCleanService cleanDataService;
    @Autowired
    private AsyncTaskDao asyncTaskDao;
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;
    private static final String MIDSTOREJOBCLEANDATANAME = "Midsotre-Work-CleanData";

    public void execute(JobContext context) throws JobExecutionException {
        MidstoreSchemeDTO scheme;
        com.jiuqi.bi.core.jobs.defaultlog.Logger logger2 = context.getDefaultLogger();
        JSONObject object = new JSONObject(context.getJob().getExtendedConfig());
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
        if (this.cleanDataService == null) {
            this.cleanDataService = (IMidstoreExcuteCleanService)ApplicationContextRegister.getBean(IMidstoreExcuteCleanService.class);
        }
        if (this.asyncTaskDao == null) {
            this.asyncTaskDao = (AsyncTaskDao)ApplicationContextRegister.getBean(AsyncTaskDao.class);
        }
        if (this.applicationEventPublisher == null) {
            this.applicationEventPublisher = (ApplicationEventPublisher)ApplicationContextRegister.getBean(ApplicationEventPublisher.class);
        }
        if ((scheme = this.midstoreSchemeSevice.getByKey(schemeKey)) == null) {
            logger2.info("\u4ea4\u6362\u65b9\u6848\u4e0d\u5b58\u5728\uff1a" + schemeKey);
            return;
        }
        logger2.info("\u4ea4\u6362\u65b9\u6848\uff1a" + scheme.getTitle() + ",\u8fdb\u884c" + scheme.getExchangeMode().getTitle());
        MidstoreSchemeInfoDTO shcemeInfo = this.schemeInfoSevice.getBySchemeKey(schemeKey);
        if (shcemeInfo == null) {
            logger2.info("\u4ea4\u6362\u65b9\u6848\u6269\u5c55\u4fe1\u606f\u4e0d\u5b58\u5728\uff1a" + schemeKey);
            return;
        }
        if (shcemeInfo.getPublishState() == PublishStateType.PUBLISHSTATE_NONE) {
            String msg = "\u4ea4\u6362\u65b9\u6848\u672a\u53d1\u5e03\uff1a" + schemeKey;
            logger2.info(msg);
            this.updateCleanPlanInfo(shcemeInfo, msg);
            return;
        }
        if (shcemeInfo.getPublishState() == PublishStateType.PUBLISHSTATE_FAIL) {
            String msg = "\u4ea4\u6362\u65b9\u6848\u53d1\u5e03\u5931\u8d25\uff1a" + schemeKey + "," + scheme.getCode() + "," + scheme.getTitle();
            logger2.info(msg);
            this.updateCleanPlanInfo(shcemeInfo, msg);
            return;
        }
        if (!shcemeInfo.isAutoClean()) {
            String msg = "\u4ea4\u6362\u65b9\u6848\u4e0d\u542f\u7528\u81ea\u52a8\u6e05\u7406\uff1a" + scheme.getTitle();
            logger2.info(msg);
            this.updateCleanPlanInfo(shcemeInfo, msg);
            return;
        }
        IProgressMonitor monitor = context.getMonitor();
        monitor.prompt("\u5f00\u59cb\u8fd0\u884c");
        monitor.startTask(context.getInstanceId(), new int[]{10, 60, 30});
        monitor.stepIn();
        if (shcemeInfo.isAutoClean()) {
            logger2.info("\u5f00\u59cb\u6267\u884c\u6e05\u7406");
            String taskId = UUID.randomUUID().toString();
            SimpleAsyncTaskMonitor sMonitor = new SimpleAsyncTaskMonitor(this.asyncTaskDao, this.applicationEventPublisher, taskId, MIDSTOREJOBCLEANDATANAME);
            try {
                this.cleanDataService.excuteCleanData(scheme.getKey(), (AsyncTaskMonitor)sMonitor);
            }
            catch (MidstoreException e) {
                logger2.error(e.getMessage(), (Throwable)e);
            }
            logger2.info("\u5b8c\u6210\u6267\u884c\u6e05\u7406");
        } else {
            logger2.info("\u4e0d\u81ea\u52a8\u7528\u6e05\u7406");
        }
        monitor.finishTask(context.getInstanceId());
        context.setResult(100, "\u4efb\u52a1\u5b8c\u6210");
        this.updateCleanPlanInfo(shcemeInfo, scheme.getTitle() + "\u6e05\u7406\u5b8c\u6210\uff01");
    }

    private void updateCleanPlanInfo(MidstoreSchemeInfoDTO shcemeInfo, String info) {
        try {
            shcemeInfo.setExcutePlanInfo(info);
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
}

