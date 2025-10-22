/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.DateCommonFormatEnum
 *  com.jiuqi.common.base.util.DateUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeTaskService
 *  okhttp3.MediaType
 *  okhttp3.MultipartBody
 *  okhttp3.MultipartBody$Builder
 *  okhttp3.OkHttpClient
 *  okhttp3.Request
 *  okhttp3.Request$Builder
 *  okhttp3.RequestBody
 *  okhttp3.Response
 *  org.apache.commons.lang3.StringUtils
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.gcreport.reportdatasync.runner.systemhook.gzwcloud.impl;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.DateCommonFormatEnum;
import com.jiuqi.common.base.util.DateUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.reportdatasync.runner.context.ReportDataSyncRunnerContext;
import com.jiuqi.gcreport.reportdatasync.runner.param.ReportDataSyncRunnerParam;
import com.jiuqi.gcreport.reportdatasync.runner.systemhook.ReportDataSyncRunnerSystemHook;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeTaskService;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class GzwCloudReportDataSyncRunnerSystemHookImpl
implements ReportDataSyncRunnerSystemHook {
    private final Logger LOGGER = LoggerFactory.getLogger(GzwCloudReportDataSyncRunnerSystemHookImpl.class);
    @Value(value="${project.gzwcloud.reportdatasync.systemhook.tenant:10000}")
    private String tenant = "10000";
    @Value(value="${project.gzwcloud.reportdatasync.systemhook.apikey:17f537ef-0476-de04-ce62-50c08ab1eff9}")
    private String apikey = "17f537ef-0476-de04-ce62-50c08ab1eff9";

    @Override
    public void pushHook(ReportDataSyncRunnerContext context, File jioFile) throws IOException {
        ReportDataSyncRunnerParam reportDataSyncRunnerParam = context.getReportDataSyncRunnerParam();
        String taskId = reportDataSyncRunnerParam.getTaskId();
        IRuntimeTaskService taskService = (IRuntimeTaskService)SpringContextUtils.getBean(IRuntimeTaskService.class);
        TaskDefine taskDefine = taskService.queryTaskDefine(taskId);
        if (taskDefine == null) {
            throw new BusinessRuntimeException("\u627e\u4e0d\u5230\u5bf9\u5e94id[" + taskId + "]\u4e0b\u7684\u62a5\u8868\u4efb\u52a1\u5b9a\u4e49\u3002");
        }
        PeriodWrapper periodWrapper = new PeriodWrapper(context.getPeriodValue());
        String year = String.valueOf(periodWrapper.getYear());
        String period = org.apache.commons.lang3.StringUtils.leftPad((String)String.valueOf(periodWrapper.getPeriod()), (int)2, (String)"0");
        String starttime = DateUtils.format((Date)context.getStarttime(), (DateCommonFormatEnum)DateCommonFormatEnum.FULL_DIGIT_BY_NONE);
        String endtime = DateUtils.format((Date)context.getEndtime(), (DateCommonFormatEnum)DateCommonFormatEnum.FULL_DIGIT_BY_NONE);
        String version = starttime;
        String taskCode = context.getMappingConfig().getTaskInfo().getSingleTaskFlag();
        String url = reportDataSyncRunnerParam.getSystemHookUrl() + "/api/ba/rc/v1.0/webapi/jioReceive";
        context.getLogs().add("\u63a5\u53e3URL\uff1a" + url);
        context.getLogs().add("\u63a5\u53e3\u8bf7\u6c42\u53c2\u6570\uff1a".concat(" X-ECC-Current-Tenant:").concat(this.tenant).concat(", Authorization:Bearer ").concat(this.apikey).concat(", taskId:").concat(taskId).concat(", taskCode:").concat(taskCode).concat(", version:").concat(version).concat(", starttime:").concat(starttime).concat(", endtime:").concat(endtime).concat(", year:").concat(String.valueOf(year)).concat(", period:").concat(String.valueOf(period)));
        MultipartBody requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM).addFormDataPart("taskId", taskCode).addFormDataPart("version", version).addFormDataPart("starttime", starttime).addFormDataPart("endtime", endtime).addFormDataPart("year", year).addFormDataPart("period", period).addFormDataPart("Jio", jioFile.getName(), RequestBody.create((MediaType)MediaType.parse((String)"application/octet-stream"), (File)jioFile)).build();
        Request request = new Request.Builder().addHeader("Content-Type", "application/json").addHeader("X-ECC-Current-Tenant", this.tenant).addHeader("Authorization", "Bearer " + this.apikey).url(url).post((RequestBody)requestBody).build();
        Response response = new OkHttpClient().newBuilder().connectTimeout(600L, TimeUnit.SECONDS).readTimeout(600L, TimeUnit.SECONDS).writeTimeout(600L, TimeUnit.SECONDS).build().newCall(request).execute();
        String responseBody = response.body().string();
        this.LOGGER.info(responseBody);
        context.getLogs().add("\u63a5\u53e3\u54cd\u5e94\u62a5\u6587\uff1a" + responseBody);
        Result result = (Result)JsonUtils.readValue((byte[])responseBody.getBytes(), Result.class);
        String flag = result.getFlag();
        if (!"success".equals(flag)) {
            throw new BusinessRuntimeException("\u63a8\u9001\u5931\u8d25\uff0c\u8be6\u60c5\uff1a" + result.getMessage());
        }
        context.getLogs().add("\u63a8\u9001\u6210\u529f\u3002");
    }

    @Override
    public String getHookName() {
        return "HOOK_GZW_CLOUD";
    }

    @Override
    public String getHookTitle() {
        return "\u63a8\u9001JIO\u5305\u5230CLOUD\u62a5\u8868\u63a5\u53e3";
    }

    @Override
    public String getHookDescription() {
        return "Cloud\u62a5\u8868\u63d0\u4f9b\u63a5\u53e3\uff0c\u4e45\u5176Web\u7aef\u8c03\u7528\u6b64\u63a5\u53e3\uff0c\u63a8\u9001JIO\u5305\u5230CLOUD\u62a5\u8868\uff0c\u62a5\u8868\u63a5\u6536\u7ec4\u7ec7\u548c\u6570\u636e\uff0c\u4e0d\u63a5\u6536\u4efb\u52a1\u548c\u62a5\u8868\u53c2\u6570\uff0c\u4efb\u52a1\u548c\u62a5\u8868\u53c2\u6570\u9ed8\u8ba4\u7ebf\u4e0b\u5df2\u7ecf\u540c\u6b65\u63a5\u6536\u3002\u524d\u63d0\u662f\u4e45\u5176JIO\u5305\u7684\u662f\u5168\u91cf\u7684\uff0c\u5305\u62ec\u5168\u90e8\u7ec4\u7ec7\u7684\u62a5\u8868\u6570\u636e\u3002";
    }

    private File transferToFile(MultipartFile multipartFile) throws IOException {
        String originalFilename = multipartFile.getOriginalFilename();
        List filenameArr = StringUtils.split((String)originalFilename, (String)".");
        File file = File.createTempFile((String)filenameArr.get(0), (String)filenameArr.get(1));
        multipartFile.transferTo(file);
        file.deleteOnExit();
        return file;
    }

    public static class Result {
        private String flag;
        private String message;

        public String getFlag() {
            return this.flag;
        }

        public void setFlag(String flag) {
            this.flag = flag;
        }

        public String getMessage() {
            return this.message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}

