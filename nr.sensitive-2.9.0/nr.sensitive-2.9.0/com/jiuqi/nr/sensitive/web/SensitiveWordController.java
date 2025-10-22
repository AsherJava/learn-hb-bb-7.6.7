/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskManager
 *  com.jiuqi.np.asynctask.TaskState
 *  com.jiuqi.np.core.application.NpApplication
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.nr.common.asynctask.entity.AsyncTaskInfo
 *  com.jiuqi.nr.common.cache.remote.CacheObjectResourceRemote
 *  io.swagger.annotations.ApiOperation
 *  javax.servlet.ServletOutputStream
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 *  javax.validation.Valid
 *  org.springframework.web.bind.annotation.CrossOrigin
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestMethod
 *  org.springframework.web.bind.annotation.RequestParam
 *  org.springframework.web.bind.annotation.RestController
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.nr.sensitive.web;

import com.jiuqi.np.asynctask.AsyncTaskManager;
import com.jiuqi.np.asynctask.TaskState;
import com.jiuqi.np.core.application.NpApplication;
import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.nr.common.asynctask.entity.AsyncTaskInfo;
import com.jiuqi.nr.common.cache.remote.CacheObjectResourceRemote;
import com.jiuqi.nr.sensitive.bean.ExportData;
import com.jiuqi.nr.sensitive.bean.viewObject.SensitiveWordResultInfo;
import com.jiuqi.nr.sensitive.bean.viewObject.SensitiveWordViewObject;
import com.jiuqi.nr.sensitive.common.ResponseResult;
import com.jiuqi.nr.sensitive.common.ResultObject;
import com.jiuqi.nr.sensitive.monitor.SimpleAsyncProgressMonitor;
import com.jiuqi.nr.sensitive.service.SensitiveWordService;
import io.swagger.annotations.ApiOperation;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;
import java.util.UUID;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(value={"/api/v1/sensitiveWord"})
public class SensitiveWordController {
    private static final Logger logger = LoggerFactory.getLogger(SensitiveWordController.class);
    @Autowired
    private SensitiveWordService sensitiveWordService;
    @Autowired
    private CacheObjectResourceRemote cacheObjectResourceRemote;
    @Autowired
    private AsyncTaskManager asyncTaskManager;
    @Autowired
    private NpApplication npApplication;

    @ApiOperation(value="\u67e5\u8be2\u6240\u6709\u654f\u611f\u8bcd\u4fe1\u606f(\u5206\u9875)")
    @RequestMapping(value={"/all-sensitiveWord"}, method={RequestMethod.GET})
    public SensitiveWordResultInfo queryAllSensitiveWord(Integer pageNum, Integer pageRow, Integer sensitiveWordType) {
        SensitiveWordResultInfo sensitiveWordResultInfo = new SensitiveWordResultInfo();
        List<SensitiveWordViewObject> sensitiveWordViewObjectList = this.sensitiveWordService.queryAllSensitiveWord(pageNum, pageRow, sensitiveWordType);
        List<SensitiveWordViewObject> sensitiveWordViewObjectListWithOutLimit = this.sensitiveWordService.queryAllSensitiveWord(-1, -1, sensitiveWordType);
        sensitiveWordResultInfo.setSensitiveWordCount(sensitiveWordViewObjectListWithOutLimit.size());
        sensitiveWordResultInfo.setSensitiveWordViewObjectList(sensitiveWordViewObjectList);
        return sensitiveWordResultInfo;
    }

    @ApiOperation(value="\u6839\u636e\u654f\u611f\u8bcd\u5185\u5bb9\u67e5\u8be2\u654f\u611f\u8bcd\u4fe1\u606f(\u6a21\u7cca\u67e5\u8be2)")
    @RequestMapping(value={"/get-sensitiveWord"}, method={RequestMethod.GET})
    public SensitiveWordResultInfo getSensitiveWord(String sensitiveWordInfo, Integer sensitiveWordType, Integer pageNum, Integer pageRow) {
        SensitiveWordResultInfo sensitiveWordResultInfo = new SensitiveWordResultInfo();
        String sensitiveWordInfoAfter = sensitiveWordInfo.toUpperCase().trim();
        List<SensitiveWordViewObject> sensitiveWordViewObjectList = this.sensitiveWordService.getSensitiveWordWithType(sensitiveWordInfoAfter, sensitiveWordType, pageNum, pageRow);
        List<SensitiveWordViewObject> sensitiveWordViewObjectListWithOutLimit = this.sensitiveWordService.getSensitiveWordWithType(sensitiveWordInfoAfter, sensitiveWordType, -1, -1);
        sensitiveWordResultInfo.setSensitiveWordCount(sensitiveWordViewObjectListWithOutLimit.size());
        sensitiveWordResultInfo.setSensitiveWordViewObjectList(sensitiveWordViewObjectList);
        return sensitiveWordResultInfo;
    }

    @ApiOperation(value="\u65b0\u589e\u654f\u611f\u8bcd\u4fe1\u606f")
    @RequestMapping(value={"/add-sensitiveWord"}, method={RequestMethod.POST})
    public ResponseResult<Boolean> addSensitiveWord(@Valid @RequestBody SensitiveWordViewObject sensitiveWordViewObject) {
        return this.sensitiveWordService.insertSensitiveWord(sensitiveWordViewObject);
    }

    @ApiOperation(value="\u4fee\u6539\u654f\u611f\u8bcd\u4fe1\u606f")
    @RequestMapping(value={"/update-sensitiveWord"}, method={RequestMethod.POST})
    public ResponseResult<Boolean> updateSensitiveWord(@Valid @RequestBody SensitiveWordViewObject sensitiveWordViewObject) {
        return this.sensitiveWordService.updateSensitiveWord(sensitiveWordViewObject);
    }

    @ApiOperation(value="\u5220\u9664\u654f\u611f\u8bcd\u4fe1\u606f")
    @RequestMapping(value={"/delete-sensitiveWord"}, method={RequestMethod.POST})
    public ResponseResult<Boolean> deleteSensitiveWord(@RequestBody List<String> sensitiveWordKeyList) {
        boolean success = this.sensitiveWordService.deleteSensitiveWord(sensitiveWordKeyList);
        if (success) {
            return ResponseResult.success("\u5220\u9664\u6210\u529f", true);
        }
        return ResponseResult.success("\u5220\u9664\u5931\u8d25", false);
    }

    @ApiOperation(value="\u5bfc\u51fa\u654f\u611f\u8bcd\u4fe1\u606f(\u5168\u91cf\u5bfc\u51fa)")
    @RequestMapping(value={"/export-sensitiveWord"}, method={RequestMethod.POST})
    public void exportAllSensitiveWord(HttpServletResponse response, HttpServletRequest request) {
        try {
            ExportData sensitiveWordExportData = this.sensitiveWordService.exportAllSensitiveWord();
            if (null != sensitiveWordExportData) {
                response.setHeader("Access-Control-Expose-Headers", "Content-disposition");
                response.setHeader("Content-disposition", "attachment; filename*=UTF-8''" + URLEncoder.encode(sensitiveWordExportData.getFileName(), "UTF-8"));
                response.setContentType("application/octet-stream");
                ServletOutputStream outputStream = response.getOutputStream();
                outputStream.write(sensitiveWordExportData.getData());
                response.flushBuffer();
            }
        }
        catch (IOException e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
    }

    @ApiOperation(value="\u5bfc\u5165\u654f\u611f\u8bcd\u4fe1\u606f(\u5168\u91cf\u5bfc\u5165)")
    @RequestMapping(value={"/import-sensitiveWord"}, method={RequestMethod.POST})
    public ResultObject importAllSensitiveWord(@RequestParam(value="file") MultipartFile file, HttpServletRequest request) {
        try {
            ResultObject returnInfo = this.sensitiveWordService.importAllSensitiveWord(file);
            return returnInfo;
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            ResultObject resultObject = new ResultObject();
            resultObject.setState(false);
            resultObject.setData(e);
            resultObject.setMessage(e.getMessage());
            return resultObject;
        }
    }

    @CrossOrigin(value={"*"})
    @ApiOperation(value="\u5386\u53f2\u6570\u636e\u6279\u91cf\u68c0\u6d4b")
    @RequestMapping(value={"/batch-checkSensitiveWord"}, method={RequestMethod.POST})
    public AsyncTaskInfo batchCheckSensitiveWord(@RequestBody String downloadKey) {
        try {
            String taskId = UUID.randomUUID().toString();
            SimpleAsyncProgressMonitor asyncTaskMonitor = new SimpleAsyncProgressMonitor(taskId, this.cacheObjectResourceRemote);
            this.npApplication.asyncRun(() -> {
                try {
                    this.sensitiveWordService.batchCheckSensitiveWord(downloadKey, asyncTaskMonitor);
                }
                catch (Exception e) {
                    logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                }
            });
            AsyncTaskInfo asyncTaskInfo = new AsyncTaskInfo();
            asyncTaskInfo.setId(taskId);
            asyncTaskInfo.setProcess(Double.valueOf(0.0));
            asyncTaskInfo.setResult("");
            asyncTaskInfo.setDetail((Object)"");
            asyncTaskInfo.setState(TaskState.PROCESSING);
            asyncTaskInfo.setUrl("/api/v1/dataentry/actions/progress/query?progressId=");
            return asyncTaskInfo;
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    @ApiOperation(value="\u4e0b\u8f7d\u6279\u91cf\u68c0\u6d4b\u7ed3\u679c")
    @RequestMapping(value={"/download-batchCheckInfo"}, method={RequestMethod.POST})
    public void downloadBatchCheckInfo(String downLoadKey, HttpServletResponse response, HttpServletRequest request) {
        Object fileLocation = this.cacheObjectResourceRemote.find((Object)downLoadKey);
        NpContext context = NpContextHolder.getContext();
        File file = new File(fileLocation.toString());
        if (file.exists() && file.isFile()) {
            try (BufferedInputStream ins = new BufferedInputStream(new FileInputStream(file));
                 BufferedOutputStream ous = new BufferedOutputStream((OutputStream)response.getOutputStream());){
                byte[] buffer = new byte[((InputStream)ins).available()];
                ((InputStream)ins).read(buffer);
                response.setContentType("application/octet-stream");
                response.setHeader("Access-Control-Expose-Headers", "Content-disposition");
                response.setHeader("Content-disposition", "attachment; filename*=UTF-8''" + URLEncoder.encode(file.getName(), "UTF-8").replaceAll("\\+", "%20"));
                response.addHeader("Content-Length", "" + file.length());
                ((OutputStream)ous).write(buffer);
                ((OutputStream)ous).flush();
            }
            catch (FileNotFoundException e) {
                logger.error("\u6587\u4ef6\u672a\u627e\u5230\uff1a" + e.getMessage(), e);
            }
            catch (IOException e) {
                logger.error("IO\u5f02\u5e38\uff1a" + e.getMessage(), e);
            }
        }
    }
}

