/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob
 *  com.jiuqi.np.asynctask.AsyncTaskManager
 *  com.jiuqi.np.asynctask.NpRealTimeTaskInfo
 *  com.jiuqi.np.asynctask.util.SimpleParamConverter$SerializationUtils
 *  com.jiuqi.nr.common.asynctask.entity.AsyncTaskInfo
 *  io.swagger.annotations.Api
 *  javax.servlet.http.HttpServletResponse
 *  javax.validation.Valid
 *  org.apache.shiro.authz.annotation.RequiresPermissions
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestMethod
 *  org.springframework.web.bind.annotation.RequestParam
 *  org.springframework.web.bind.annotation.ResponseBody
 *  org.springframework.web.bind.annotation.RestController
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.nr.singlequeryimport.controller;

import com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob;
import com.jiuqi.np.asynctask.AsyncTaskManager;
import com.jiuqi.np.asynctask.NpRealTimeTaskInfo;
import com.jiuqi.np.asynctask.util.SimpleParamConverter;
import com.jiuqi.nr.common.asynctask.entity.AsyncTaskInfo;
import com.jiuqi.nr.singlequeryimport.asynctask.UpLoadFileAsyncTaskExecutor;
import com.jiuqi.nr.singlequeryimport.asynctask.UpLoadFileNetWorkAsyncTaskExecutor;
import com.jiuqi.nr.singlequeryimport.bean.BatchQueryConfigInfo;
import com.jiuqi.nr.singlequeryimport.bean.QueryConfigInfo;
import com.jiuqi.nr.singlequeryimport.bean.QueryModel;
import com.jiuqi.nr.singlequeryimport.common.ContrastContext;
import com.jiuqi.nr.singlequeryimport.controller.QueryModleController;
import com.jiuqi.nr.singlequeryimport.dao.QueryModeleDao;
import com.jiuqi.nr.singlequeryimport.service.SingleQueryService;
import io.swagger.annotations.Api;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@Api(tags={"\u7efc\u5408\u67e5\u8be2\u6574\u5408\u5355\u673a\u7248\u67e5\u8be2\u6a21\u677f"})
@RequestMapping(value={"/singleQuery"})
public class SingleQueryController {
    private static final Logger logger = LoggerFactory.getLogger(SingleQueryController.class);
    @Autowired
    QueryModeleDao queryModeleDao;
    @Autowired
    private AsyncTaskManager asyncTaskManager;
    @Autowired
    SingleQueryService singleQueryService;
    @Autowired
    QueryModleController queryModleController;

    @RequestMapping(value={"/upLoad"}, method={RequestMethod.POST})
    @ResponseBody
    @RequiresPermissions(value={"nr:singlequery:manage"})
    AsyncTaskInfo upLoad(@RequestParam(value="file") MultipartFile multipartFile, @RequestParam String taskKey, @RequestParam String formSchemeKey) throws Exception {
        ContrastContext context;
        NpRealTimeTaskInfo npRealTimeTaskInfo = new NpRealTimeTaskInfo();
        npRealTimeTaskInfo.setTaskKey(taskKey);
        npRealTimeTaskInfo.setFormSchemeKey(formSchemeKey);
        String contentType = multipartFile.getContentType();
        String asynTaskID = null;
        if (contentType.contains("xml")) {
            context = new ContrastContext(multipartFile);
            context.setFormSchemeKey(formSchemeKey);
            context.setTaskKey(taskKey);
            npRealTimeTaskInfo.setArgs(SimpleParamConverter.SerializationUtils.serializeToString((Object)context));
            logger.info("\u51b3\u7b97\u67e5\u8be2\u5355\u673a\u7248\u4e0a\u4f20\u6587\u4ef6---->{}", (Object)context.getFileName());
            npRealTimeTaskInfo.setAbstractRealTimeJob((AbstractRealTimeJob)new UpLoadFileAsyncTaskExecutor());
            asynTaskID = this.asyncTaskManager.publishTask(npRealTimeTaskInfo, "UPLOAD_FILE");
        }
        if (contentType.contains("zip")) {
            context = new ContrastContext(multipartFile, taskKey, formSchemeKey);
            npRealTimeTaskInfo.setArgs(SimpleParamConverter.SerializationUtils.serializeToString((Object)context));
            logger.info("\u51b3\u7b97\u67e5\u8be2\u7f51\u7edc\u7248\u4e0a\u4f20\u6587\u4ef6---->{}", (Object)context.getFileName());
            npRealTimeTaskInfo.setAbstractRealTimeJob((AbstractRealTimeJob)new UpLoadFileNetWorkAsyncTaskExecutor());
            asynTaskID = this.asyncTaskManager.publishTask(npRealTimeTaskInfo, "UPLOAD_FILE_NETWORK");
        }
        AsyncTaskInfo asyncTaskInfo = new AsyncTaskInfo();
        asyncTaskInfo.setId(asynTaskID);
        asyncTaskInfo.setUrl("/api/asynctask/query?asynTaskID=");
        return asyncTaskInfo;
    }

    @RequestMapping(value={"/upLoadNetWork"}, method={RequestMethod.POST})
    @ResponseBody
    @RequiresPermissions(value={"nr:singlequery:manage"})
    AsyncTaskInfo upLoadNetWork(@RequestParam(value="file") MultipartFile multipartFile, @RequestParam String taskKey, @RequestParam String formSchemeKey) throws Exception {
        ContrastContext context = new ContrastContext(multipartFile, taskKey, formSchemeKey);
        logger.info("\u51b3\u7b97\u67e5\u8be2\u7f51\u7edc\u7248\u4e0a\u4f20\u6587\u4ef6---->{}", (Object)context.getFileName());
        String asynTaskID = this.asyncTaskManager.publishTask((Object)context, "UPLOAD_FILE_NETWORK");
        AsyncTaskInfo asyncTaskInfo = new AsyncTaskInfo();
        asyncTaskInfo.setId(asynTaskID);
        asyncTaskInfo.setUrl("/api/asynctask/query?asynTaskID=");
        return asyncTaskInfo;
    }

    @RequestMapping(value={"/checkModel"}, method={RequestMethod.POST})
    @ResponseBody
    @RequiresPermissions(value={"nr:singlequery:manage"})
    AsyncTaskInfo checkModel() throws Exception {
        List<QueryModel> queryModels = this.queryModeleDao.get();
        String asynTaskID = this.asyncTaskManager.publishTask(queryModels, "ASYNCTASK_MODELQUERYCHECK");
        AsyncTaskInfo asyncTaskInfo = new AsyncTaskInfo();
        asyncTaskInfo.setId(asynTaskID);
        asyncTaskInfo.setUrl("/api/asynctask/query?asynTaskID=");
        return asyncTaskInfo;
    }

    @RequestMapping(value={"/export"}, method={RequestMethod.POST})
    @ResponseBody
    @RequiresPermissions(value={"nr:singlequery:manage"})
    void export(@RequestBody @Valid QueryConfigInfo queryConfigInfo, HttpServletResponse response) throws Exception {
        this.queryModleController.judgeQueryType(queryConfigInfo);
        this.singleQueryService.export(queryConfigInfo, response);
    }

    @RequestMapping(value={"/batchexport"}, method={RequestMethod.POST})
    @ResponseBody
    @RequiresPermissions(value={"nr:singlequery:manage"})
    void batchexport(@RequestBody @Valid BatchQueryConfigInfo queryConfigInfo, HttpServletResponse response) throws Exception {
        this.singleQueryService.batchExport(queryConfigInfo, response);
    }

    @RequestMapping(value={"/templateExport"}, method={RequestMethod.POST})
    @RequiresPermissions(value={"nr:singlequery:manage"})
    void templateExport(@RequestBody List<String> keyList, HttpServletResponse response) {
        try {
            this.singleQueryService.templateExport(keyList, response);
        }
        catch (Exception e) {
            logger.error(e.getMessage());
        }
    }
}

