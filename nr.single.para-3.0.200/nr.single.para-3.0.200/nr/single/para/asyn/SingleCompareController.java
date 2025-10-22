/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.asynctask.AsyncThreadExecutor
 *  com.jiuqi.np.asynctask.NpRealTimeTaskInfo
 *  com.jiuqi.np.asynctask.dao.AsyncTaskDao
 *  com.jiuqi.np.asynctask.impl.SimpleAsyncTaskMonitor
 *  com.jiuqi.np.asynctask.util.SimpleParamConverter$SerializationUtils
 *  com.jiuqi.np.blob.util.BeanUtil
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  com.jiuqi.nr.single.core.exception.SingleFileException
 *  com.jiuqi.nr.single.core.util.SinglePathUtil
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  javax.servlet.http.HttpServletRequest
 *  nr.single.map.data.PathUtil
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestMethod
 *  org.springframework.web.bind.annotation.RequestParam
 *  org.springframework.web.multipart.MultipartFile
 */
package nr.single.para.asyn;

import com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.asynctask.AsyncThreadExecutor;
import com.jiuqi.np.asynctask.NpRealTimeTaskInfo;
import com.jiuqi.np.asynctask.dao.AsyncTaskDao;
import com.jiuqi.np.asynctask.impl.SimpleAsyncTaskMonitor;
import com.jiuqi.np.asynctask.util.SimpleParamConverter;
import com.jiuqi.np.blob.util.BeanUtil;
import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.nr.single.core.exception.SingleFileException;
import com.jiuqi.nr.single.core.util.SinglePathUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import nr.single.map.data.PathUtil;
import nr.single.para.asyn.SingleImportAsyncTaskExecutor;
import nr.single.para.common.NrSingleErrorEnum;
import nr.single.para.compare.bean.ParaCompareOption;
import nr.single.para.compare.bean.ParaCompareParam;
import nr.single.para.compare.bean.ParaCompareResult;
import nr.single.para.compare.definition.common.CompareDataType;
import nr.single.para.compare.service.TaskFileCompareService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@JQRestController
@RequestMapping(value={"/single"})
@Api(tags={"JIO\u53c2\u6570\u6587\u4ef6\u6bd4\u8f83"})
public class SingleCompareController {
    private static final Logger log = LoggerFactory.getLogger(SingleCompareController.class);
    private static final String JIOFINDTASK = "JIO_FIND_TASK";
    @Autowired
    private AsyncTaskDao asyncTaskDao;
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;
    @Autowired
    private AsyncThreadExecutor asyncTaskManager2;
    @Autowired
    private TaskFileCompareService compareService;

    @ApiOperation(value="\u901a\u8fc7JIO\u53c2\u6570\u6587\u4ef6\u67e5\u627e\u4efb\u52a1\u548c\u62a5\u8868\u65b9\u6848")
    @RequestMapping(value={"/JioCompare/QueryTaskByFile"}, method={RequestMethod.POST})
    public ParaCompareResult QueryTaskByFile(@RequestParam(value="file") MultipartFile file, HttpServletRequest req) throws Exception {
        String fileName = file.getOriginalFilename();
        String matchByTaskCode = req.getParameter("matchByTaskCode");
        boolean isMatchByTaskCode = false;
        if (StringUtils.isNotEmpty((String)matchByTaskCode)) {
            isMatchByTaskCode = Boolean.parseBoolean(matchByTaskCode);
        }
        String asyncTaskId = UUID.randomUUID().toString();
        SimpleAsyncTaskMonitor monitor = new SimpleAsyncTaskMonitor(this.asyncTaskDao, this.applicationEventPublisher, asyncTaskId, JIOFINDTASK);
        ParaCompareResult result = this.compareService.findNetTaskBySingle(fileName, file.getBytes(), isMatchByTaskCode, (AsyncTaskMonitor)monitor);
        result.setAsyncTaskId(asyncTaskId);
        return result;
    }

    @ApiOperation(value="\u901a\u8fc7\u4e4b\u524d\u4e0a\u4f20\u7684JIO\u53c2\u6570\u6587\u4ef6\u4e0e\u7f51\u62a5\u53c2\u6570")
    @RequestMapping(value={"/JioCompare/CompareJioInfos"}, method={RequestMethod.POST})
    public ParaCompareResult CompareJioInfos(@RequestBody ParaCompareParam compareParam) throws Exception {
        return null;
    }

    private String publishAndExecuteTask(Map<String, Object> args) {
        NpRealTimeTaskInfo npRealTimeTaskInfo = new NpRealTimeTaskInfo();
        npRealTimeTaskInfo.setArgs(SimpleParamConverter.SerializationUtils.serializeToString(args));
        npRealTimeTaskInfo.setAbstractRealTimeJob((AbstractRealTimeJob)new SingleImportAsyncTaskExecutor());
        return this.asyncTaskManager2.executeTask(npRealTimeTaskInfo);
    }

    public String compareSingleToTasK(String compareKey, String taskKey, String formSchemeKey, String dataSchemeKey, ParaCompareOption option) throws Exception {
        HashMap<String, Object> args = new HashMap<String, Object>();
        args.put("importType", "10");
        args.put("compareKey", compareKey);
        args.put("taskKey", taskKey);
        args.put("formSchemeKey", formSchemeKey);
        args.put("dataSchemeKey", dataSchemeKey);
        args.put("option", option);
        return this.publishAndExecuteTask(args);
    }

    public ParaCompareResult compareSingleToTasKEx(String compareKey, String taskKey, String formSchemeKey, String dataSchemeKey, ParaCompareOption option) throws Exception {
        TaskFileCompareService taskCompareService = (TaskFileCompareService)BeanUtil.getBean(TaskFileCompareService.class);
        return taskCompareService.compareSingleToTasK(compareKey, taskKey, formSchemeKey, dataSchemeKey, option, null);
    }

    public String ImportSingleToTask(String compareKey, ParaCompareOption option) throws Exception {
        HashMap<String, Object> args = new HashMap<String, Object>();
        args.put("importType", "11");
        args.put("compareKey", compareKey);
        args.put("option", option);
        return this.publishAndExecuteTask(args);
    }

    public String QueryImportReulst(String syncTaskId) {
        String resultCode = this.asyncTaskManager2.queryResult(syncTaskId);
        return resultCode;
    }

    public String batchCompareSingleToTasKByType(CompareDataType dataType, List<String> compareDataKeys, String compareInfoKey, ParaCompareOption option) throws Exception {
        HashMap<String, Object> args = new HashMap<String, Object>();
        args.put("importType", "12");
        args.put("dataType", (Object)dataType);
        args.put("compareDataKeys", compareDataKeys);
        args.put("compareInfoKey", compareInfoKey);
        args.put("option", option);
        return this.publishAndExecuteTask(args);
    }

    public String batchDelete(String compareKey) throws Exception {
        HashMap<String, Object> args = new HashMap<String, Object>();
        args.put("importType", "13");
        args.put("compareKey", compareKey);
        return this.publishAndExecuteTask(args);
    }

    public String batchDeleteByKeys(List<String> compareKeys) throws Exception {
        HashMap<String, Object> args = new HashMap<String, Object>();
        args.put("importType", "14");
        args.put("compareKeys", compareKeys);
        return this.publishAndExecuteTask(args);
    }

    private String saveToFile(MultipartFile file) throws JQException, SingleFileException {
        SimpleDateFormat sfDate = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String fileName = file.getOriginalFilename();
        String filePath = SinglePathUtil.getTempFilePath();
        filePath = PathUtil.createNewPath((String)filePath, (String)".nr");
        filePath = PathUtil.createNewPath((String)filePath, (String)"AppData");
        filePath = PathUtil.createNewPath((String)filePath, (String)"ComparePara");
        filePath = PathUtil.createNewPath((String)filePath, (String)sfDate.format(new Date()));
        log.info("JIO\u6587\u4ef6\u540d(\u542b\u5b8c\u6574\u8def\u5f84)-->" + filePath + fileName);
        try {
            this.uploadFile(file.getBytes(), filePath, fileName);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrSingleErrorEnum.NRDESINGER_EXCEPTION_001, (Throwable)e);
        }
        return filePath + fileName;
    }

    private void uploadFile(byte[] file, String filePath, String fileName) throws SingleFileException, FileNotFoundException, IOException {
        File targetFile = new File(SinglePathUtil.normalize((String)filePath));
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        try (FileOutputStream out = new FileOutputStream(SinglePathUtil.normalize((String)(filePath + fileName)));){
            out.write(file);
            out.flush();
        }
    }
}

