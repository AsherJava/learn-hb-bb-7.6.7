/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.JobContext
 *  com.jiuqi.bi.core.jobs.realtime.RealTimeJob
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.asynctask.NpRealTimeTaskExecutor
 *  com.jiuqi.np.asynctask.impl.RealTimeTaskMonitor
 *  com.jiuqi.np.asynctask.util.SimpleParamConverter$SerializationUtils
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.nr.task.api.file.IFileAreaService
 *  com.jiuqi.nr.task.api.file.dto.FileAreaDTO
 *  com.jiuqi.nr.task.api.task.AsyncTaskInfo
 *  com.jiuqi.nr.task.api.task.DownloadInfo
 */
package com.jiuqi.nr.task.form.formcopy.service;

import com.jiuqi.bi.core.jobs.JobContext;
import com.jiuqi.bi.core.jobs.realtime.RealTimeJob;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.asynctask.NpRealTimeTaskExecutor;
import com.jiuqi.np.asynctask.impl.RealTimeTaskMonitor;
import com.jiuqi.np.asynctask.util.SimpleParamConverter;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.nr.task.api.file.IFileAreaService;
import com.jiuqi.nr.task.api.file.dto.FileAreaDTO;
import com.jiuqi.nr.task.api.task.AsyncTaskInfo;
import com.jiuqi.nr.task.api.task.DownloadInfo;
import com.jiuqi.nr.task.form.dto.AsyncFormParamVO;
import com.jiuqi.nr.task.form.dto.FormSyncParamsVO;
import com.jiuqi.nr.task.form.formcopy.IDesignFormCopyManageService;
import com.jiuqi.nr.task.form.formcopy.utils.FormCopyUtil;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

@RealTimeJob(group="ASYNCTASK_PARAM_FORM_SYNC", groupTitle="\u590d\u5236\u62a5\u8868\u540c\u6b65\u53c2\u6570", isolate=true, subject="\u62a5\u8868", tags={"\u77ed\u4efb\u52a1"})
public class FormSyncPullAsync
extends NpRealTimeTaskExecutor {
    private static final Logger log = LoggerFactory.getLogger(FormSyncPullAsync.class);
    private static final String FORM_PULL_SYNC_ASYNC = "FORM_SYNC_PULL_ASYNC";

    public void execute(JobContext jobContext) {
        Object args;
        AsyncFormParamVO asyncFormParamVO = null;
        String argStr = super.getArgs();
        if (StringUtils.hasText(argStr) && (args = SimpleParamConverter.SerializationUtils.deserialize((String)argStr)) instanceof AsyncFormParamVO) {
            asyncFormParamVO = (AsyncFormParamVO)args;
        }
        if (asyncFormParamVO == null) {
            return;
        }
        List<FormSyncParamsVO> paramExportInfo = asyncFormParamVO.getFormSchemeParam();
        String downLoadKey = asyncFormParamVO.getDownLoadKey();
        String instanceId = jobContext.getInstanceId();
        RealTimeTaskMonitor monitor = new RealTimeTaskMonitor(instanceId, FORM_PULL_SYNC_ASYNC, jobContext);
        try {
            StringBuilder allFormCopyMessage = new StringBuilder();
            IDesignFormCopyManageService designTimeFormCopyManageService = (IDesignFormCopyManageService)SpringBeanUtils.getBean(IDesignFormCopyManageService.class);
            designTimeFormCopyManageService.doFormSync(paramExportInfo, (AsyncTaskMonitor)monitor, allFormCopyMessage);
            this.exportFile(allFormCopyMessage.toString(), downLoadKey);
            AsyncTaskInfo taskInfo = new AsyncTaskInfo(monitor.getTaskId(), "FORM_EXT", "\u62a5\u8868\u540c\u6b65");
            taskInfo.setDownloadInfo(new DownloadInfo(downLoadKey, "\u62a5\u8868\u540c\u6b65\u7ed3\u679c.txt"));
            FormCopyUtil.setFinish((AsyncTaskMonitor)monitor, "\u62a5\u8868\u540c\u6b65\u5b8c\u6210", taskInfo);
        }
        catch (Exception e) {
            this.exportFile(e.getMessage(), downLoadKey);
            FormCopyUtil.setError((AsyncTaskMonitor)monitor, e.getMessage(), e);
            log.error(e.getMessage(), e);
        }
    }

    private void exportFile(String resultMessage, String downLoadKey) {
        String tempLocation = FormCopyUtil.getTempLocation();
        File file = new File(tempLocation);
        if (!file.exists()) {
            file.mkdirs();
        }
        String fileName = "\u540c\u6b65\u7ed3\u679c\u4fe1\u606f.txt";
        String filePath = tempLocation.concat(File.separator).concat(fileName);
        try (FileWriter writer = new FileWriter(filePath);){
            writer.write(resultMessage.toString());
            System.out.println("\u6587\u672c\u5df2\u6210\u529f\u5199\u5165\u5230\u6587\u4ef6\u6d41\u4e2d\u3002");
        }
        catch (IOException e) {
            log.error("\u62a5\u8868\u590d\u5236\u5199\u5165\u6587\u4ef6\u65f6\u51fa\u73b0\u9519\u8bef: " + e.getMessage(), e);
        }
        IFileAreaService fileAreaService = (IFileAreaService)SpringBeanUtils.getBean(IFileAreaService.class);
        try (FileInputStream uploadInputStream = new FileInputStream(filePath);){
            fileAreaService.fileUploadByKey(fileName, (InputStream)uploadInputStream, downLoadKey, new FileAreaDTO(true));
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    public String getTaskPoolType() {
        return FORM_PULL_SYNC_ASYNC;
    }
}

