/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.asynctask.AsyncThreadExecutor
 *  com.jiuqi.np.asynctask.NpRealTimeTaskInfo
 *  com.jiuqi.np.asynctask.util.SimpleParamConverter$SerializationUtils
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  com.jiuqi.np.definition.common.UUIDUtils
 *  com.jiuqi.nr.definition.controller.IDesignTimeViewController
 *  com.jiuqi.nr.definition.facade.DesignFormSchemeDefine
 *  com.jiuqi.nr.definition.facade.DesignTaskDefine
 *  com.jiuqi.nr.single.core.exception.SingleFileException
 *  com.jiuqi.nr.single.core.util.SinglePathUtil
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  javax.servlet.http.HttpServletRequest
 *  nr.single.map.data.PathUtil
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestMethod
 *  org.springframework.web.bind.annotation.RequestParam
 *  org.springframework.web.multipart.MultipartFile
 */
package nr.single.para.asyn;

import com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.asynctask.AsyncThreadExecutor;
import com.jiuqi.np.asynctask.NpRealTimeTaskInfo;
import com.jiuqi.np.asynctask.util.SimpleParamConverter;
import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.np.definition.common.UUIDUtils;
import com.jiuqi.nr.definition.controller.IDesignTimeViewController;
import com.jiuqi.nr.definition.facade.DesignFormSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.single.core.exception.SingleFileException;
import com.jiuqi.nr.single.core.util.SinglePathUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import nr.single.map.data.PathUtil;
import nr.single.para.asyn.SingleImportAsyncTaskExecutor;
import nr.single.para.common.NrSingleErrorEnum;
import nr.single.para.compare.bean.TaskPeriodDTO;
import nr.single.para.compare.service.TaskFileCompareService;
import nr.single.para.parain.controller.SingleParaImportOption;
import nr.single.para.parain.util.ISingleSchemePeriodUtil;
import nr.single.para.parain.util.SingleSchemePeriodObj;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@JQRestController
@RequestMapping(value={"/single"})
@Api(tags={"JIO\u53c2\u6570\u6587\u4ef6\u5bfc\u5165"})
public class SingleAsynController {
    private static final Logger log = LoggerFactory.getLogger(SingleAsynController.class);
    @Autowired
    private AsyncThreadExecutor asyncTaskManager2;
    @Autowired
    private IDesignTimeViewController viewController;
    @Autowired(required=false)
    private TaskFileCompareService compareService;
    @Autowired
    private ISingleSchemePeriodUtil periodUtil;

    @ApiOperation(value="\u5224\u65ad\u6587\u4ef6\u6807\u8bc6\u662f\u5426\u5b58\u5728")
    @RequestMapping(value={"/JioSercice/queryDefineByFilePrefix/{code}"}, method={RequestMethod.GET})
    public boolean queryDefineByFilePrefix(@PathVariable(value="code") String code) {
        DesignTaskDefine task = this.viewController.queryTaskDefineByFilePrefix(code);
        DesignFormSchemeDefine scheme = this.viewController.queryFormSchemeDefineByFilePrefix(code);
        return null != task || null != scheme;
    }

    @ApiOperation(value="\u5bfc\u5165JIO\u6587\u4ef6\u548c\u521b\u5efa\u6570\u636e\u65b9\u6848")
    @RequestMapping(value={"/JioSercice/UploadFileAdd"}, method={RequestMethod.POST})
    public String uploadJio(@RequestParam(value="file") MultipartFile file, HttpServletRequest req) throws Exception {
        String taskId = req.getParameter("taskId");
        String schemeId = req.getParameter("schemeId");
        String corpEntityId = req.getParameter("corpEntityId");
        String dateEntityId = req.getParameter("dateEntityId");
        String dimEntityIds = req.getParameter("dimEntityIds");
        if (StringUtils.isEmpty((String)dimEntityIds)) {
            dimEntityIds = req.getParameter("dimEntityId");
        }
        String isSelectEntityParam = req.getParameter("isSelectEntity");
        boolean isSelectEntity = false;
        if (StringUtils.isNotEmpty((String)isSelectEntityParam)) {
            isSelectEntity = Boolean.parseBoolean(isSelectEntityParam);
        }
        if (StringUtils.isEmpty((String)corpEntityId) || StringUtils.isEmpty((String)dateEntityId)) {
            log.info("\u5355\u4f4d\u5b9e\u4f53Key-->" + corpEntityId);
            log.info("\u65f6\u671f\u5b9e\u4f53Key-->" + dateEntityId);
            log.info("\u7ef4\u5ea6\u5b9e\u4f53Key-->" + dimEntityIds);
            throw new JQException((ErrorEnum)NrSingleErrorEnum.NRDESINGER_EXCEPTION_000);
        }
        if (!isSelectEntity || StringUtils.isNotEmpty((String)corpEntityId) && StringUtils.isNotEmpty((String)dateEntityId)) {
            return this.uploadJioParaFile(file, taskId, schemeId, corpEntityId, dateEntityId, dimEntityIds, isSelectEntity);
        }
        throw new JQException((ErrorEnum)NrSingleErrorEnum.NRDESINGER_EXCEPTION_000);
    }

    @ApiOperation(value="\u5efa\u6a21\u8bbe\u8ba1\u5f02\u6b65\u5bfc\u5165JIO\u6587\u4ef6")
    @RequestMapping(value={"/JioSercice/UploadFileAsyn"}, method={RequestMethod.POST})
    public String uploadImg(@RequestParam(value="file") MultipartFile file, HttpServletRequest req) throws Exception {
        String taskId = req.getParameter("taskId");
        String schemeId = req.getParameter("schemeId");
        String isImportPrint = req.getParameter("isImportPrint");
        String paraType = req.getParameter("paraType");
        String matchFieldCode = req.getParameter("matchFieldCode");
        String historyFormSchemes = req.getParameter("historyFormSchemes");
        String historyUpdateEnumRef = req.getParameter("isHistoryUpdateEnumRef");
        String filePrefix = req.getParameter("filePrefix");
        if (StringUtils.isNotEmpty((String)taskId) && StringUtils.isNotEmpty((String)schemeId) && StringUtils.isNotEmpty((String)paraType)) {
            int paraTypeValue = Integer.parseInt(paraType);
            return this.uploadJioParaFile(file, taskId, schemeId, isImportPrint, paraTypeValue, matchFieldCode, historyFormSchemes, historyUpdateEnumRef, filePrefix);
        }
        throw new JQException((ErrorEnum)NrSingleErrorEnum.NRDESINGER_EXCEPTION_000);
    }

    @ApiOperation(value="\u66f4\u65b0\u4efb\u52a1\u7684\u5f00\u59cb\u65f6\u95f4\u548c\u7ed3\u675f\u65f6\u95f4")
    @RequestMapping(value={"/JioSercice/updateTaskPeriod"}, method={RequestMethod.POST})
    public String updateTaskPeriod(@RequestBody TaskPeriodDTO taskPeriod) throws Exception {
        String result = "";
        if (StringUtils.isEmpty((String)taskPeriod.getTaskKey())) {
            throw new JQException((ErrorEnum)NrSingleErrorEnum.NRDESINGER_EXCEPTION_020);
        }
        if (StringUtils.isEmpty((String)taskPeriod.getFromPeriod())) {
            throw new JQException((ErrorEnum)NrSingleErrorEnum.NRDESINGER_EXCEPTION_020);
        }
        if (StringUtils.isEmpty((String)taskPeriod.getToPeriod())) {
            throw new JQException((ErrorEnum)NrSingleErrorEnum.NRDESINGER_EXCEPTION_020);
        }
        DesignTaskDefine task = this.viewController.queryTaskDefine(taskPeriod.getTaskKey());
        task.setFromPeriod(taskPeriod.getFromPeriod());
        task.setToPeriod(taskPeriod.getToPeriod());
        this.viewController.updateTaskDefine(task);
        List formSchemes = this.viewController.queryFormSchemeByTask(task.getKey());
        ArrayList<SingleSchemePeriodObj> objs = new ArrayList<SingleSchemePeriodObj>();
        for (DesignFormSchemeDefine scheme : formSchemes) {
            if (null != scheme.getDateTime()) {
                scheme.setDateTime(null);
                this.viewController.updateFormSchemeDefine(scheme);
            }
            SingleSchemePeriodObj obj = new SingleSchemePeriodObj();
            if (!scheme.getKey().equals(((DesignFormSchemeDefine)formSchemes.get(0)).getKey())) continue;
            obj.setScheme(scheme.getKey());
            obj.setStart(task.getFromPeriod());
            obj.setEnd(task.getToPeriod());
            objs.add(obj);
        }
        if (taskPeriod.isUpdateFormSchemePreiod()) {
            this.periodUtil.saveSchemePeriodLinks(objs, task.getDateTime());
        }
        return result;
    }

    private String uploadJioParaFile(MultipartFile file, String taskId, String schemeId, String corpEntityId, String dateEntityId, String dimEntityIds, boolean isSelectEntity) throws Exception {
        SimpleDateFormat sfDate = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String fileName = this.saveToFile(file);
        log.info("\u5355\u4f4d\u5b9e\u4f53Key-->" + corpEntityId);
        log.info("\u65f6\u671f\u5b9e\u4f53Key-->" + dateEntityId);
        log.info("\u7ef4\u5ea6\u5b9e\u4f53Key-->" + dimEntityIds);
        try {
            SingleParaImportOption option = new SingleParaImportOption();
            option.SelectAll();
            option.setAnalPara(true);
            option.setHistoryPara(false);
            String syncTaskID = UUIDUtils.getKey();
            option.setSyncTaskID(syncTaskID);
            option.setCorpEntityId(corpEntityId);
            option.setDateEntityId(dateEntityId);
            option.setDimEntityIds(dimEntityIds);
            option.setSelectEntity(isSelectEntity);
            String asyncTaskId = this.importSingleCreateFormScheme(taskId, schemeId, fileName, option);
            return asyncTaskId;
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrSingleErrorEnum.NRDESINGER_EXCEPTION_002, e.getMessage(), (Throwable)e);
        }
    }

    private String uploadJioParaFile(MultipartFile file, String taskId, String schemeId, String isImportPrint, int paraType, String hisMatchByFiledCodeStr, String historyFormSchemes, String historyUpdateEnumRef, String filePrefix) throws Exception {
        String filePath = System.getProperty("java.io.tmpdir");
        String fileName = this.saveToFile(file);
        log.info("\u4efb\u52a1Key-->" + taskId);
        try {
            boolean isUploadPrint;
            boolean isHistoryUpdateEnumRef = false;
            if (StringUtils.isNotEmpty((String)historyUpdateEnumRef)) {
                isHistoryUpdateEnumRef = Boolean.parseBoolean(historyUpdateEnumRef);
            }
            SingleParaImportOption option = new SingleParaImportOption();
            if (paraType == 0) {
                isUploadPrint = Boolean.parseBoolean(isImportPrint);
                if (isUploadPrint) {
                    option.NotSelectAll();
                    option.setUploadPrint(isUploadPrint);
                } else {
                    option.SelectAll();
                }
                option.setAnalPara(true);
                option.setHistoryPara(false);
            } else if (paraType == 1) {
                option.NotSelectAll();
                option.setAnalPara(true);
                option.setHistoryPara(false);
            } else if (paraType == 2) {
                isUploadPrint = Boolean.parseBoolean(isImportPrint);
                boolean historyMatchCode = Boolean.parseBoolean(hisMatchByFiledCodeStr);
                if (isUploadPrint) {
                    option.NotSelectAll();
                    option.setUploadPrint(isUploadPrint);
                } else {
                    option.SelectAll();
                }
                option.setAnalPara(false);
                option.setHistoryPara(true);
                if (historyMatchCode) {
                    option.setHistoryMatchType(1);
                }
                option.setHistoryFormSchemes(historyFormSchemes);
            }
            option.setFilePrefix(filePrefix);
            option.setAnalPara(paraType <= 1);
            option.setHistoryPara(paraType == 2);
            option.setHistoryUpdateEnumRef(isHistoryUpdateEnumRef);
            String syncTaskID = UUIDUtils.getKey();
            option.setSyncTaskID(syncTaskID);
            String asyncTaskId = this.importSingleToFormScheme(taskId, schemeId, fileName, option);
            return asyncTaskId;
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrSingleErrorEnum.NRDESINGER_EXCEPTION_002, e.getMessage(), (Throwable)e);
        }
    }

    private String saveToFile(MultipartFile file) throws JQException, SingleFileException {
        SimpleDateFormat sfDate = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String fileName = file.getOriginalFilename();
        String filePath = SinglePathUtil.getTempFilePath();
        try {
            filePath = PathUtil.createNewPath((String)filePath, (String)".nr");
            filePath = PathUtil.createNewPath((String)filePath, (String)"AppData");
            filePath = PathUtil.createNewPath((String)filePath, (String)"uploadPara");
            filePath = PathUtil.createNewPath((String)filePath, (String)sfDate.format(new Date()));
            log.info("JIO\u6587\u4ef6\u540d(\u542b\u5b8c\u6574\u8def\u5f84)-->" + filePath + fileName);
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

    private String publishAndExecuteTask(Map<String, Object> args) {
        NpRealTimeTaskInfo npRealTimeTaskInfo = new NpRealTimeTaskInfo();
        npRealTimeTaskInfo.setArgs(SimpleParamConverter.SerializationUtils.serializeToString(args));
        npRealTimeTaskInfo.setAbstractRealTimeJob((AbstractRealTimeJob)new SingleImportAsyncTaskExecutor());
        return this.asyncTaskManager2.executeTask(npRealTimeTaskInfo);
    }

    private String importSingleToFormScheme(String taskKey, String schemeKey, String file, SingleParaImportOption option) throws Exception {
        HashMap<String, Object> args = new HashMap<String, Object>();
        args.put("importType", "0");
        args.put("taskKey", taskKey);
        args.put("schemeKey", schemeKey);
        args.put("file", file);
        args.put("option", option);
        return this.publishAndExecuteTask(args);
    }

    private String importSingleCreateFormScheme(String taskKey, String schemeKey, String file, SingleParaImportOption option) throws Exception {
        HashMap<String, Object> args = new HashMap<String, Object>();
        args.put("importType", "1");
        args.put("taskKey", taskKey);
        args.put("schemeKey", schemeKey);
        args.put("file", file);
        args.put("option", option);
        return this.publishAndExecuteTask(args);
    }
}

