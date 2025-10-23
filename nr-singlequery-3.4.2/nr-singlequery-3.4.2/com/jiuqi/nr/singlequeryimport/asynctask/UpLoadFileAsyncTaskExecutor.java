/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
 *  com.jiuqi.bi.core.jobs.JobContext
 *  com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob
 *  com.jiuqi.bi.core.jobs.realtime.RealTimeJob
 *  com.jiuqi.np.asynctask.impl.RealTimeTaskMonitor
 *  com.jiuqi.np.asynctask.util.SimpleParamConverter$SerializationUtils
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  org.json.JSONArray
 *  org.json.JSONObject
 */
package com.jiuqi.nr.singlequeryimport.asynctask;

import com.google.gson.Gson;
import com.jiuqi.bi.core.jobs.JobContext;
import com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob;
import com.jiuqi.bi.core.jobs.realtime.RealTimeJob;
import com.jiuqi.np.asynctask.impl.RealTimeTaskMonitor;
import com.jiuqi.np.asynctask.util.SimpleParamConverter;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.nr.singlequeryimport.bean.ModalDefine;
import com.jiuqi.nr.singlequeryimport.common.ContrastContext;
import com.jiuqi.nr.singlequeryimport.service.QueryModleService;
import com.jiuqi.nr.singlequeryimport.service.SingleQueryService;
import com.jiuqi.nr.singlequeryimport.utils.XmlToJsonUtil;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RealTimeJob(group="UPLOAD_FILE", groupTitle="\u5355\u673a\u7248\u6a21\u677f\u5bfc\u5165")
public class UpLoadFileAsyncTaskExecutor
extends AbstractRealTimeJob {
    private static final Logger logger = LoggerFactory.getLogger(UpLoadFileAsyncTaskExecutor.class);
    private static final String ASYNCTASK_UPLOAD_FILE = "ASYNCTASK_UPLOAD_FILE";
    SingleQueryService singleQueryService;
    QueryModleService queryModleService;

    public void execute(JobContext jobContext) {
        this.singleQueryService = (SingleQueryService)SpringBeanUtils.getBean(SingleQueryService.class);
        this.queryModleService = (QueryModleService)SpringBeanUtils.getBean(QueryModleService.class);
        StringBuffer info = new StringBuffer();
        String taskId = jobContext.getInstanceId();
        AbstractRealTimeJob job = jobContext.getRealTimeJob();
        Map params = job.getParams();
        RealTimeTaskMonitor monitor = new RealTimeTaskMonitor(taskId, ASYNCTASK_UPLOAD_FILE, jobContext);
        try {
            if (params != null && params.containsKey("NR_ARGS")) {
                double progress;
                Gson gson = new Gson();
                ContrastContext context = (ContrastContext)SimpleParamConverter.SerializationUtils.deserialize((String)((String)params.get("NR_ARGS")));
                logger.info("\u51b3\u7b97\u67e5\u8be2\u4e0a\u4f20\u6587\u4ef6\u5b57\u7b26\u96c6\u7c7b\u578b--->" + Charset.defaultCharset());
                String file = new String(context.getFile(), StandardCharsets.UTF_8);
                JSONObject fileJson = XmlToJsonUtil.xml2Json(file);
                JSONObject queryModelGroup = fileJson.getJSONObject("QueryData").getJSONObject("QueryModalGroups").getJSONObject("QueryModalGroup");
                String formSchemeTitle = queryModelGroup.getString("Title");
                Object queryModalGroup = queryModelGroup.get("QueryModalGroup");
                logger.info("\u51b3\u7b97\u67e5\u8be2\u4e0a\u4f20\u6587\u4ef6\u5f00\u59cb\u89e3\u6790\u6a21\u677f");
                if (queryModalGroup instanceof JSONObject) {
                    Object modalDefines = ((JSONObject)queryModalGroup).getJSONObject("ModalDefines").get("ModalDefine");
                    if (modalDefines instanceof JSONObject) {
                        ModalDefine modalDefine = (ModalDefine)gson.fromJson(modalDefines.toString(), ModalDefine.class);
                        StringBuffer result = this.queryModleService.saveMyModle(modalDefine, null, formSchemeTitle, context);
                        info = null == result ? info : info.append(result);
                    } else {
                        int size = ((JSONArray)modalDefines).length();
                        int num = 1;
                        progress = new BigDecimal(1.0f / (float)size).setScale(5, RoundingMode.HALF_UP).doubleValue();
                        for (int i = 0; i < size; ++i) {
                            monitor.progressAndMessage(progress * (double)num, "");
                            ++num;
                            String title = ((JSONObject)queryModalGroup).getString("Title");
                            JSONObject jsonObject1 = ((JSONArray)modalDefines).getJSONObject(i);
                            ModalDefine modalDefine2 = (ModalDefine)gson.fromJson(jsonObject1.toString(), ModalDefine.class);
                            StringBuffer result = this.queryModleService.saveMyModle(modalDefine2, title, formSchemeTitle, context);
                            info = null == result ? info : info.append(result);
                        }
                    }
                }
                if (queryModalGroup instanceof JSONArray) {
                    JSONArray queryModal = queryModelGroup.getJSONArray("QueryModalGroup");
                    int size = queryModal.length();
                    int num = 1;
                    progress = new BigDecimal(1.0f / (float)size).setScale(5, RoundingMode.HALF_UP).doubleValue();
                    for (Object modalDefines : queryModal) {
                        JSONObject defines = (JSONObject)modalDefines;
                        String title = defines.has("Title") ? defines.getString("Title") : null;
                        Object modalDefine = defines.getJSONObject("ModalDefines").get("ModalDefine");
                        if (modalDefine instanceof JSONObject) {
                            ModalDefine define = (ModalDefine)gson.fromJson(modalDefine.toString(), ModalDefine.class);
                            StringBuffer result = this.queryModleService.saveMyModle(define, title, formSchemeTitle, context);
                            StringBuffer stringBuffer = info = null == result ? info : info.append(result);
                        }
                        if (modalDefine instanceof JSONArray) {
                            for (int i = 0; i < ((JSONArray)modalDefine).length(); ++i) {
                                JSONObject jsonObject1 = ((JSONArray)modalDefine).getJSONObject(i);
                                ModalDefine modalDefine2 = (ModalDefine)gson.fromJson(jsonObject1.toString(), ModalDefine.class);
                                StringBuffer result = this.queryModleService.saveMyModle(modalDefine2, title, formSchemeTitle, context);
                                info = null == result ? info : info.append(result);
                            }
                        }
                        monitor.progressAndMessage(progress * (double)num, "");
                        ++num;
                    }
                }
                monitor.finish("\u5bfc\u5165\u6210\u529f", (Object)info);
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

