/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.JobContext
 *  com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob
 *  com.jiuqi.bi.core.jobs.realtime.RealTimeJob
 *  com.jiuqi.np.asynctask.impl.RealTimeTaskMonitor
 *  com.jiuqi.np.asynctask.util.SimpleParamConverter$SerializationUtils
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  org.json.JSONObject
 */
package com.jiuqi.nr.singlequeryimport.asynctask;

import com.jiuqi.bi.core.jobs.JobContext;
import com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob;
import com.jiuqi.bi.core.jobs.realtime.RealTimeJob;
import com.jiuqi.np.asynctask.impl.RealTimeTaskMonitor;
import com.jiuqi.np.asynctask.util.SimpleParamConverter;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.nr.singlequeryimport.common.ContrastContext;
import com.jiuqi.nr.singlequeryimport.service.SingleQueryService;
import com.jiuqi.nr.singlequeryimport.service.impl.UpLoadFileNetWorkServiceImpl;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RealTimeJob(group="UPLOAD_FILE_NETWORK", groupTitle="\u7f51\u7edc\u7248\u6a21\u677f\u5bfc\u5165")
public class UpLoadFileNetWorkAsyncTaskExecutor
extends AbstractRealTimeJob {
    private static final Logger logger = LoggerFactory.getLogger(UpLoadFileNetWorkAsyncTaskExecutor.class);
    private static final String ASYNCTASK_UPLOAD_FILE_NETWORK = "ASYNCTASK_UPLOAD_FILE_NETWORK";
    SingleQueryService singleQueryService;
    UpLoadFileNetWorkServiceImpl queryModleService;

    public void execute(JobContext jobContext) {
        this.singleQueryService = (SingleQueryService)SpringBeanUtils.getBean(SingleQueryService.class);
        this.queryModleService = (UpLoadFileNetWorkServiceImpl)SpringBeanUtils.getBean(UpLoadFileNetWorkServiceImpl.class);
        StringBuilder info = new StringBuilder();
        String taskId = jobContext.getInstanceId();
        AbstractRealTimeJob job = jobContext.getRealTimeJob();
        Map params = job.getParams();
        RealTimeTaskMonitor monitor = new RealTimeTaskMonitor(taskId, ASYNCTASK_UPLOAD_FILE_NETWORK, jobContext);
        try {
            if (params != null && params.containsKey("NR_ARGS")) {
                ContrastContext context = (ContrastContext)SimpleParamConverter.SerializationUtils.deserialize((String)((String)params.get("NR_ARGS")));
                logger.info("\u51b3\u7b97\u67e5\u8be2\u7f51\u7edc\u7248\u4e0a\u4f20\u6587\u4ef6\u5b57\u7b26\u96c6\u7c7b\u578b--->" + Charset.defaultCharset());
                ByteArrayInputStream input = new ByteArrayInputStream(context.getFile());
                ZipInputStream zipInputStream = new ZipInputStream((InputStream)new BufferedInputStream(input), Charset.forName("UTF-8"));
                int size = context.getSize();
                if (size == 0) {
                    monitor.finish("\u4efb\u52a1\u5b8c\u6210", (Object)"\u6ca1\u6709\u53ef\u4ee5\u4e0a\u4f20\u7684\u6a21\u677f");
                    return;
                }
                ZipEntry ze = null;
                int index = 0;
                double progress = new BigDecimal(1.0f / (float)size).setScale(5, RoundingMode.HALF_UP).doubleValue();
                while ((ze = zipInputStream.getNextEntry()) != null) {
                    String line;
                    ++index;
                    logger.info("\u6587\u4ef6\u540d\uff1a" + ze.getName());
                    BufferedReader br = new BufferedReader(new InputStreamReader((InputStream)zipInputStream, Charset.forName("UTF-8")));
                    while ((line = br.readLine()) != null) {
                        JSONObject model = new JSONObject(line);
                        this.queryModleService.saveNetWorkMyModle(model, context.getTaskKey(), context.getFormSchemeKey());
                    }
                    monitor.progressAndMessage((double)index * progress, "");
                }
                zipInputStream.closeEntry();
                input.close();
                monitor.finish("\u4efb\u52a1\u5b8c\u6210", (Object)info);
            } else {
                String missingParamError = "\u7f3a\u5c11\u5fc5\u8981\u7684\u4efb\u52a1\u53c2\u6570-REALTIME_TASK_PARAMSKEY_ARGS";
                logger.error(missingParamError);
                monitor.error("\u4efb\u52a1\u5931\u8d25", (Throwable)new Exception(missingParamError));
            }
        }
        catch (Exception e) {
            monitor.error("\u4efb\u52a1\u5931\u8d25", (Throwable)e, e.getMessage());
            logger.error(e.getMessage());
        }
    }
}

