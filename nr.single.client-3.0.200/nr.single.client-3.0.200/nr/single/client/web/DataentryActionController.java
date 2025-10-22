/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob
 *  com.jiuqi.bi.oss.ObjectInfo
 *  com.jiuqi.bi.util.OrderGenerator
 *  com.jiuqi.np.asynctask.AsyncTaskManager
 *  com.jiuqi.np.asynctask.AsyncThreadExecutor
 *  com.jiuqi.np.asynctask.NpRealTimeTaskInfo
 *  com.jiuqi.np.asynctask.TaskState
 *  com.jiuqi.np.asynctask.exception.NpAsyncTaskExecption
 *  com.jiuqi.np.asynctask.util.SimpleParamConverter$SerializationUtils
 *  com.jiuqi.nr.common.asynctask.entity.AsyncTaskInfo
 *  com.jiuqi.nr.common.cache.remote.CacheObjectResourceRemote
 *  com.jiuqi.nr.dataentry.bean.ImportResultObject
 *  com.jiuqi.nr.dataentry.bean.UploadParam
 *  com.jiuqi.nr.dataentry.service.IUploadService
 *  com.jiuqi.nr.dataentry.service.IUploadTypeService
 *  com.jiuqi.nr.dataentry.util.BatchExportConsts
 *  com.jiuqi.nr.file.FileService
 *  com.jiuqi.nr.fileupload.service.FileUploadOssService
 *  com.jiuqi.nr.single.core.common.InOutDataType
 *  com.jiuqi.nr.single.core.data.bean.SingleDataFileInfo
 *  com.jiuqi.nr.single.core.data.bean.SingleDataSplictInfo
 *  com.jiuqi.nr.single.core.exception.SingleFileException
 *  com.jiuqi.nr.single.core.internal.file.SingleFileImpl
 *  com.jiuqi.nr.single.core.service.SingleFileHelper
 *  com.jiuqi.nr.single.core.util.SinglePathUtil
 *  com.jiuqi.nr.single.core.util.ZipUtil
 *  com.jiuqi.util.StringUtils
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiImplicitParam
 *  io.swagger.annotations.ApiImplicitParams
 *  io.swagger.annotations.ApiOperation
 *  javax.validation.Valid
 *  nr.single.data.datain.service.ITaskFileReadDataService
 *  nr.single.map.configurations.bean.ISingleMappingConfig
 *  nr.single.map.configurations.bean.SingleConfigInfo
 *  nr.single.map.data.PathUtil
 *  nr.single.map.data.bean.JioImportFileNode
 *  nr.single.map.data.bean.JioImportParam
 *  nr.single.map.data.bean.RepeatEntityNode
 *  nr.single.map.data.bean.RepeatImportParam
 *  nr.single.map.data.exception.SingleDataException
 *  nr.single.map.data.facade.SingleFileTaskInfo
 *  nr.single.map.data.service.SingleJioFileService
 *  nr.single.map.data.service.SingleMappingService
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package nr.single.client.web;

import com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob;
import com.jiuqi.bi.oss.ObjectInfo;
import com.jiuqi.bi.util.OrderGenerator;
import com.jiuqi.np.asynctask.AsyncTaskManager;
import com.jiuqi.np.asynctask.AsyncThreadExecutor;
import com.jiuqi.np.asynctask.NpRealTimeTaskInfo;
import com.jiuqi.np.asynctask.TaskState;
import com.jiuqi.np.asynctask.exception.NpAsyncTaskExecption;
import com.jiuqi.np.asynctask.util.SimpleParamConverter;
import com.jiuqi.nr.common.asynctask.entity.AsyncTaskInfo;
import com.jiuqi.nr.common.cache.remote.CacheObjectResourceRemote;
import com.jiuqi.nr.dataentry.bean.ImportResultObject;
import com.jiuqi.nr.dataentry.bean.UploadParam;
import com.jiuqi.nr.dataentry.service.IUploadService;
import com.jiuqi.nr.dataentry.service.IUploadTypeService;
import com.jiuqi.nr.dataentry.util.BatchExportConsts;
import com.jiuqi.nr.file.FileService;
import com.jiuqi.nr.fileupload.service.FileUploadOssService;
import com.jiuqi.nr.single.core.common.InOutDataType;
import com.jiuqi.nr.single.core.data.bean.SingleDataFileInfo;
import com.jiuqi.nr.single.core.data.bean.SingleDataSplictInfo;
import com.jiuqi.nr.single.core.exception.SingleFileException;
import com.jiuqi.nr.single.core.internal.file.SingleFileImpl;
import com.jiuqi.nr.single.core.service.SingleFileHelper;
import com.jiuqi.nr.single.core.util.SinglePathUtil;
import com.jiuqi.nr.single.core.util.ZipUtil;
import com.jiuqi.util.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.validation.Valid;
import nr.single.client.bean.JIOImportResultObject;
import nr.single.client.bean.JioMatchResult;
import nr.single.client.internal.service.upload.UploadJioDataUtil;
import nr.single.client.internal.service.upload.UploadTypeJioServiceImpl;
import nr.single.client.internal.service.upload.asyn.UploadJioTaskDirAsyncTaskExecutor;
import nr.single.client.service.upload.IUploadJioMappingService;
import nr.single.client.web.SingleClientAsyncTaskExecutor;
import nr.single.data.datain.service.ITaskFileReadDataService;
import nr.single.map.configurations.bean.ISingleMappingConfig;
import nr.single.map.configurations.bean.SingleConfigInfo;
import nr.single.map.data.PathUtil;
import nr.single.map.data.bean.JioImportFileNode;
import nr.single.map.data.bean.JioImportParam;
import nr.single.map.data.bean.RepeatEntityNode;
import nr.single.map.data.bean.RepeatImportParam;
import nr.single.map.data.exception.SingleDataException;
import nr.single.map.data.facade.SingleFileTaskInfo;
import nr.single.map.data.service.SingleJioFileService;
import nr.single.map.data.service.SingleMappingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/api/v1/dataentry/actions"})
@Api(tags={"\u6570\u636e\u5f55\u5165\u52a8\u4f5c\u6269\u5c55"})
public class DataentryActionController {
    private static final Logger logger = LoggerFactory.getLogger(UploadTypeJioServiceImpl.class);
    @Autowired
    private SingleMappingService mappingConfigService;
    @Autowired
    private SingleFileHelper singleHelper;
    @Autowired
    private SingleJioFileService jioService;
    @Autowired
    private IUploadService iUploadService;
    @Autowired
    private FileUploadOssService fileUploadOssService;
    @Autowired
    private FileService fileService;
    @Autowired
    private Map<String, IUploadTypeService> uploadTypeServiceMap;
    @Autowired
    private CacheObjectResourceRemote cacheObjectResourceRemote;
    @Autowired
    private ITaskFileReadDataService jioReadService;
    @Autowired
    private IUploadJioMappingService jioMappingService;
    @Autowired
    private AsyncTaskManager asyncTaskManager;
    @Autowired
    private AsyncThreadExecutor asyncTaskManager2;
    private static final String ERROR_IMPORT_PARAM = "\u5bfc\u5165\u53c2\u6570\u6709\u95ee\u9898!!!";
    private static final String ERROR_FILE_NOTEXIST = "\u6587\u4ef6\u4e0d\u5b58\u5728!!!";

    @GetMapping(value={"/query-mapping-config/{reportSchemeKey}"})
    @ApiOperation(value="\u67e5\u8be2\u6620\u5c04\u65b9\u6848\u914d\u7f6e")
    @ApiImplicitParams(value={@ApiImplicitParam(name="reportSchemeKey", value="\u62a5\u8868\u65b9\u6848key", required=true, dataType="String")})
    public List<JioMatchResult> queryMappingConfig(@PathVariable(value="reportSchemeKey") String reportSchemeKey) {
        List allMappingInReport = this.mappingConfigService.getAllMappingInReport(reportSchemeKey);
        ArrayList<JioMatchResult> resultList = new ArrayList<JioMatchResult>();
        for (SingleConfigInfo singleConfigInfo : allMappingInReport) {
            JioMatchResult one = new JioMatchResult();
            one.setConfigKey(singleConfigInfo.getConfigKey().toString());
            one.setName(singleConfigInfo.getConfigName());
            one.setSingleFileFlag(singleConfigInfo.getFileFlag());
            one.setSingleTaskFlag(singleConfigInfo.getTaskFlag());
            resultList.add(one);
        }
        return resultList;
    }

    @GetMapping(value={"/query-mapping-config-fileflag/{reportSchemeKey}", "/query-mapping-config-fileflag/{reportSchemeKey}/{fileFlag}", "/query-mapping-config-fileflag/{reportSchemeKey}/{fileFlag}/{taskPeriod}"})
    @ApiOperation(value="\u6839\u636e\u6587\u4ef6\u6807\u8bc6\u548c\u4efb\u52a1\u65f6\u671f\u7c7b\u578b\u67e5\u8be2\u6620\u5c04\u65b9\u6848\u914d\u7f6e")
    public List<JioMatchResult> queryMappingConfigByFileFlag(@PathVariable(value="reportSchemeKey") String reportSchemeKey, @PathVariable(value="fileFlag") String fileFlag, @PathVariable(value="taskPeriod", required=false) String taskPeriod) {
        List allMappingInReport = this.mappingConfigService.getAllMappingInReport(reportSchemeKey);
        ArrayList<JioMatchResult> resultList = new ArrayList<JioMatchResult>();
        for (SingleConfigInfo singleConfigInfo : allMappingInReport) {
            if (!StringUtils.isNotEmpty((String)fileFlag) || !fileFlag.equalsIgnoreCase(singleConfigInfo.getFileFlag())) continue;
            boolean isFind = false;
            if (StringUtils.isNotEmpty((String)taskPeriod)) {
                ISingleMappingConfig config = this.mappingConfigService.getConfigByKey(singleConfigInfo.getConfigKey());
                if (config != null && config.getTaskInfo() != null && taskPeriod.equalsIgnoreCase(config.getTaskInfo().getSingleTaskPeriod())) {
                    isFind = true;
                }
            } else {
                isFind = true;
            }
            if (!isFind) continue;
            JioMatchResult one = new JioMatchResult();
            one.setConfigKey(singleConfigInfo.getConfigKey().toString());
            one.setName(singleConfigInfo.getConfigName());
            one.setSingleFileFlag(singleConfigInfo.getFileFlag());
            one.setSingleTaskFlag(singleConfigInfo.getTaskFlag());
            resultList.add(one);
        }
        return resultList;
    }

    @PostMapping(value={"/read-jio-file"})
    @ApiOperation(value="\u83b7\u53d6JIO\u6587\u4ef6\u4fe1\u606f")
    public JioMatchResult readJioFile(@Valid @RequestBody String jioPath) throws SingleDataException {
        JioMatchResult result = new JioMatchResult();
        SingleFileImpl singleFile = new SingleFileImpl();
        try {
            singleFile.infoLoad(jioPath);
            SingleFileTaskInfo taskInfo = this.jioService.getTaskInfoByJioFile(jioPath);
            List inOutDatas = singleFile.getInOutData();
            if (singleFile != null) {
                result.setSingleTaskFlag(taskInfo.getSingleTaskFlag());
                result.setSingleFileFlag(taskInfo.getSingleFileFlag());
                result.setSingleTaskYear(taskInfo.getSingleTaskYear());
                result.setSingleTaskPeriod(taskInfo.getSingleTaskPeriod());
                if (inOutDatas.contains(InOutDataType.BBCS) || inOutDatas.contains(InOutDataType.CXMB) || inOutDatas.contains(InOutDataType.CSCS)) {
                    result.setHasParam(true);
                }
                if (inOutDatas.contains(InOutDataType.QYSJ) || inOutDatas.contains(InOutDataType.WQHZ) || inOutDatas.contains(InOutDataType.SHSM) || inOutDatas.contains(InOutDataType.CSJG)) {
                    result.setHasData(true);
                }
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new SingleDataException(e.getMessage(), (Throwable)e);
        }
        finally {
            singleFile = null;
        }
        return result;
    }

    @PostMapping(value={"/get-jio-unitinfo"})
    @ApiOperation(value="\u83b7\u53d6JIO\u5355\u4f4d\u4fe1\u606f")
    public SingleDataFileInfo getJioUnitInfo(@Valid @RequestBody List<String> pathParams) throws SingleDataException {
        String jioFile = pathParams.get(0);
        String workPath = null;
        if (pathParams.size() > 1) {
            workPath = pathParams.get(1);
        }
        if (StringUtils.isEmpty(workPath)) {
            workPath = BatchExportConsts.ROOT_LOCATION + BatchExportConsts.SEPARATOR + ".nr" + BatchExportConsts.SEPARATOR + "AppData" + BatchExportConsts.SEPARATOR + "jioSplict";
        }
        return this.jioReadService.getFMDMUnits(workPath, jioFile);
    }

    private String publishAndExecuteTask(Map<String, Object> args) {
        NpRealTimeTaskInfo npRealTimeTaskInfo = new NpRealTimeTaskInfo();
        npRealTimeTaskInfo.setArgs(SimpleParamConverter.SerializationUtils.serializeToString(args));
        npRealTimeTaskInfo.setAbstractRealTimeJob((AbstractRealTimeJob)new SingleClientAsyncTaskExecutor());
        return this.asyncTaskManager2.executeTask(npRealTimeTaskInfo);
    }

    @PostMapping(value={"/asyncSplit-jio-file-option"})
    @ApiOperation(value="\u5f02\u6b65\u62c6\u5206JIO\u6587\u4ef6")
    public String splitJioFileAsynByOption(@Valid @RequestBody SingleDataSplictInfo jioSplictInfo) throws SingleDataException {
        HashMap<String, Object> args = new HashMap<String, Object>();
        args.put("opertionType", "1");
        args.put("jioSplictInfo", jioSplictInfo);
        return this.publishAndExecuteTask(args);
    }

    @PostMapping(value={"/asyncSplit-jio-file"})
    @ApiOperation(value="\u5f02\u6b65\u62c6\u5206JIO\u6587\u4ef6")
    public String splitJioFileAsyn(@Valid @RequestBody List<String> pathParams) throws SingleDataException {
        String jioFile = pathParams.get(0);
        String paramFile = pathParams.get(1);
        String dataFile = pathParams.get(2);
        return this.splictJioFiles(jioFile, paramFile, dataFile);
    }

    private String splictJioFiles(String jioFile, String paramFile, String dataFile) {
        HashMap<String, Object> args = new HashMap<String, Object>();
        args.put("opertionType", "0");
        args.put("jioFile", jioFile);
        args.put("paramFile", paramFile);
        args.put("dataFile", dataFile);
        return this.publishAndExecuteTask(args);
    }

    @PostMapping(value={"/split-jio-file"})
    @ApiOperation(value="\u62c6\u5206JIO\u6587\u4ef6")
    public JioMatchResult splitJioFile(@Valid @RequestBody List<String> pathParams) throws SingleDataException {
        JioMatchResult result = new JioMatchResult();
        String jioFile = pathParams.get(0);
        String paramFile = pathParams.get(1);
        String dataFile = pathParams.get(2);
        try {
            SingleFileTaskInfo taskInfo = this.jioService.getTaskInfoByJioFile(jioFile);
            this.singleHelper.splitSingleFile(jioFile, paramFile, dataFile);
            if (taskInfo != null) {
                result.setSingleTaskFlag(taskInfo.getSingleTaskFlag());
                result.setSingleFileFlag(taskInfo.getSingleFileFlag());
                result.setSingleTaskYear(taskInfo.getSingleTaskYear());
                result.setSingleTaskPeriod(taskInfo.getSingleTaskPeriod());
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new SingleDataException(e.getMessage(), (Throwable)e);
        }
        return result;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @PostMapping(value={"/import-jio-zip"})
    @ApiOperation(value="\u5bfc\u5165JIO\u538b\u7f29\u6587\u4ef6")
    public AsyncTaskInfo importJioZip(@Valid @RequestBody UploadParam param) throws Exception {
        if (param != null && param.getVariableMap() != null && param.getVariableMap().containsKey("JioZipFilePath")) {
            String filePath = (String)param.getVariableMap().get("JioZipFilePath");
            String workPath = this.getJioWorkFilePath(param);
            SimpleDateFormat sfDate = new SimpleDateFormat("yyyyMMddHHmmssSSS");
            String fileLocation = sfDate.format(new Date()) + OrderGenerator.newOrder();
            String path = workPath + BatchExportConsts.SEPARATOR + fileLocation + BatchExportConsts.SEPARATOR;
            File pathFile = new File(SinglePathUtil.normalize((String)path));
            if (!pathFile.exists()) {
                pathFile.mkdirs();
            }
            try {
                JioImportParam jioImportParam = null;
                if (param.getVariableMap().containsKey("jioImportFilesParm")) {
                    jioImportParam = UploadJioDataUtil.getJIOImportParam(param, "jioImportFilesParm");
                }
                if (jioImportParam == null && StringUtils.isNotEmpty((String)filePath)) {
                    ObjectInfo obj;
                    File uzipPathFile = new File(SinglePathUtil.normalize((String)(path + "unzip" + BatchExportConsts.SEPARATOR)));
                    if (!uzipPathFile.exists()) {
                        uzipPathFile.mkdirs();
                    }
                    ZipUtil.unzipFile((String)uzipPathFile.getPath(), (String)filePath, (String)"GBK");
                    List files = PathUtil.getFileList((String)uzipPathFile.getPath(), (boolean)true, (String)"jio");
                    ArrayList<JIOImportResultObject> jioImportResults = new ArrayList<JIOImportResultObject>();
                    for (String subfile : files) {
                        File subPathFile = new File(SinglePathUtil.normalize((String)subfile));
                        obj = null;
                        try (FileInputStream inputStream = new FileInputStream(SinglePathUtil.normalize((String)subfile));){
                            obj = this.fileUploadOssService.uploadFileStreamToTemp(subPathFile.getName(), (InputStream)inputStream);
                        }
                        ImportResultObject r = this.queryMaping(param, subPathFile.getParentFile(), subPathFile.getName());
                        JIOImportResultObject jioReulst = (JIOImportResultObject)r;
                        jioReulst.setJioFileName(subPathFile.getName());
                        jioImportResults.add(jioReulst);
                        if (obj != null) {
                            jioReulst.setJioFileKey(obj.getKey());
                        }
                        Files.delete(subPathFile.toPath());
                    }
                    JIOImportResultObject reulst = new JIOImportResultObject();
                    reulst.setJioImportResults(jioImportResults);
                    if (jioImportResults.size() > 0) {
                        reulst.setSuccess(true);
                    } else {
                        reulst.setSuccess(false);
                        reulst.setMessage("\u65e0\u5bf9\u5e94\u7684\u6620\u5c04\u5173\u7cfb\uff01");
                        logger.info("\u65e0\u5bf9\u5e94\u7684\u6620\u5c04\u5173\u7cfb\uff01");
                    }
                    String taskId = UUID.randomUUID().toString();
                    AsyncTaskInfo asyncTaskInfo = new AsyncTaskInfo();
                    asyncTaskInfo.setId(taskId);
                    asyncTaskInfo.setProcess(Double.valueOf(0.0));
                    asyncTaskInfo.setResult("");
                    asyncTaskInfo.setDetail((Object)reulst);
                    asyncTaskInfo.setState(TaskState.FINISHED);
                    obj = asyncTaskInfo;
                    return obj;
                }
                if (jioImportParam != null && jioImportParam.getFileNodes().size() == 0 && StringUtils.isNotEmpty((String)param.getConfigKey())) {
                    String subfile2;
                    File uzipPathFile = new File(SinglePathUtil.normalize((String)(path + "unzip" + BatchExportConsts.SEPARATOR)));
                    if (!uzipPathFile.exists()) {
                        uzipPathFile.mkdirs();
                    }
                    ZipUtil.unzipFile((String)uzipPathFile.getPath(), (String)filePath, (String)"GBK");
                    List files = PathUtil.getFileList((String)uzipPathFile.getPath(), (boolean)true, (String)"jio");
                    ArrayList<JIOImportResultObject> jioImportResults = new ArrayList<JIOImportResultObject>();
                    for (String subfile2 : files) {
                        ObjectInfo fileInfo;
                        JIOImportResultObject jioReulst;
                        File subPathFile;
                        block70: {
                            subPathFile = new File(SinglePathUtil.normalize((String)subfile2));
                            jioReulst = new JIOImportResultObject();
                            fileInfo = null;
                            try {
                                String dataentryUploadArea = "UPLOADTEMP";
                                long fileSizeByte = this.fileService.area(dataentryUploadArea).getAreaConfig().getMaxFileSize();
                                double fileSizeM = (double)fileSizeByte / 1048576.0;
                                if (fileSizeByte < subPathFile.getTotalSpace()) {
                                    String taskId = UUID.randomUUID().toString();
                                    AsyncTaskInfo asyncTaskInfo = new AsyncTaskInfo();
                                    asyncTaskInfo.setId(taskId);
                                    asyncTaskInfo.setProcess(Double.valueOf(1.0));
                                    asyncTaskInfo.setResult("");
                                    asyncTaskInfo.setDetail((Object)("\u6587\u4ef6\u5927\u5c0f\u5927\u4e8e\u914d\u7f6e\u503c" + fileSizeM + "M!!!"));
                                    asyncTaskInfo.setState(TaskState.ERROR);
                                    jioReulst.setJioImportTask(asyncTaskInfo);
                                    break block70;
                                }
                                try (FileInputStream inputStream = new FileInputStream(SinglePathUtil.normalize((String)subPathFile.getPath()));){
                                    fileInfo = this.fileUploadOssService.uploadFileStreamToTemp(subPathFile.getName(), (InputStream)inputStream);
                                }
                            }
                            catch (Exception e) {
                                logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                                String taskId = UUID.randomUUID().toString();
                                AsyncTaskInfo asyncTaskInfo = new AsyncTaskInfo();
                                asyncTaskInfo.setId(taskId);
                                asyncTaskInfo.setProcess(Double.valueOf(1.0));
                                asyncTaskInfo.setResult("");
                                asyncTaskInfo.setDetail((Object)e.getMessage());
                                asyncTaskInfo.setState(TaskState.ERROR);
                                jioReulst.setJioImportTask(asyncTaskInfo);
                            }
                        }
                        Files.delete(subPathFile.toPath());
                        if (fileInfo != null) {
                            UploadParam newParam = new UploadParam();
                            this.copyNewParam(newParam, param);
                            param.setFileKeyOfSOss(fileInfo.getKey());
                            AsyncTaskInfo importInfo = this.iUploadService.upload(newParam);
                            jioReulst.setJioImportTask(importInfo);
                            jioReulst.setJioConfigKey(param.getConfigKey());
                            jioReulst.setJioFileKey(fileInfo.getKey());
                        }
                        jioReulst.setJioFileName(subPathFile.getName());
                        jioImportResults.add(jioReulst);
                    }
                    JIOImportResultObject reulst = new JIOImportResultObject();
                    reulst.setJioImportResults(jioImportResults);
                    subfile2 = this.getSuccessAsyncTaskInfo(reulst);
                    return subfile2;
                }
                if (jioImportParam != null && jioImportParam.getFileNodes().size() > 0) {
                    HashMap<String, RepeatImportParam> RepeatImportParams = new HashMap<String, RepeatImportParam>();
                    HashSet<String> allZdms = new HashSet<String>();
                    for (Object subfile : jioImportParam.getFileNodes()) {
                        String fileFullName = path + subfile.getFileName();
                        try (FileOutputStream outStream = new FileOutputStream(SinglePathUtil.normalize((String)fileFullName));){
                            this.fileUploadOssService.downloadFileFormTemp(subfile.getFileKey(), (OutputStream)outStream);
                        }
                        try {
                            RepeatImportParam fileUnitParam = new RepeatImportParam();
                            fileUnitParam.setFileKey(subfile.getFileKey());
                            fileUnitParam.setFileName(subfile.getFileName());
                            RepeatImportParams.put(subfile.getFileKey(), fileUnitParam);
                            ArrayList fileZdms = new ArrayList();
                            this.jioReadService.readFMDMunits(path, subfile.getFileName(), fileZdms);
                            if (fileZdms.size() <= 0) continue;
                            for (String zdm : fileZdms) {
                                if (allZdms.contains(zdm)) {
                                    RepeatEntityNode entityNode = new RepeatEntityNode();
                                    entityNode.setSingleZdm(zdm);
                                    entityNode.setRepeatMode(0);
                                    fileUnitParam.getEntityNodes().add(entityNode);
                                    continue;
                                }
                                allZdms.add(zdm);
                            }
                        }
                        finally {
                            PathUtil.deleteFile((String)fileFullName);
                        }
                    }
                    allZdms.clear();
                    ArrayList jioImportResults = new ArrayList();
                    for (JioImportFileNode subfile : jioImportParam.getFileNodes()) {
                        UploadParam newParam = new UploadParam();
                        this.copyNewParam(newParam, param);
                        newParam.setFileKeyOfSOss(subfile.getFileKey());
                        newParam.setConfigKey(subfile.getConfigKey());
                        newParam.setFileNameInfo(subfile.getFileName());
                        RepeatImportParam fileUnitParam = null;
                        if (RepeatImportParams.containsKey(subfile.getFileKey())) {
                            fileUnitParam = (RepeatImportParam)RepeatImportParams.get(subfile.getFileKey());
                            HashMap<String, RepeatImportParam> variableMap = newParam.getVariableMap();
                            if (variableMap == null) {
                                variableMap = new HashMap<String, RepeatImportParam>();
                                newParam.setVariableMap(variableMap);
                            }
                            if (fileUnitParam.getEntityNodes().size() > 0) {
                                fileUnitParam.setRepeatByFile(true);
                                variableMap.put("jioRepeatParm_Object", fileUnitParam);
                            }
                        }
                        AsyncTaskInfo importInfo = this.iUploadService.upload(newParam);
                        JIOImportResultObject jioReulst = new JIOImportResultObject();
                        jioReulst.setJioFileName(subfile.getFileName());
                        jioReulst.setJioFileKey(subfile.getFileKey());
                        jioReulst.setJioConfigKey(subfile.getConfigKey());
                        jioReulst.setJioImportTask(importInfo);
                        jioImportResults.add(jioReulst);
                        Thread.sleep(1000L);
                    }
                    JIOImportResultObject reulst = new JIOImportResultObject();
                    reulst.setJioImportResults(jioImportResults);
                    AsyncTaskInfo asyncTaskInfo = this.getSuccessAsyncTaskInfo(reulst);
                    return asyncTaskInfo;
                }
                AsyncTaskInfo asyncTaskInfo = this.getErrorAsyncTaskInfo(ERROR_IMPORT_PARAM);
                return asyncTaskInfo;
            }
            finally {
                PathUtil.deleteDir((String)path);
            }
        }
        return this.getErrorAsyncTaskInfo(ERROR_FILE_NOTEXIST);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @PostMapping(value={"/import-jio-path"})
    @ApiOperation(value="\u5bfc\u5165JIO\u6587\u4ef6\u5939")
    public AsyncTaskInfo importJioPath(@Valid @RequestBody UploadParam param) throws Exception {
        if (param != null && param.getVariableMap() != null && param.getVariableMap().containsKey("JioFilesPath")) {
            String filePath = (String)param.getVariableMap().get("JioFilesPath");
            String workPath = this.getJioWorkFilePath(param);
            SimpleDateFormat sfDate = new SimpleDateFormat("yyyyMMddHHmmssSSS");
            String fileLocation = sfDate.format(new Date()) + OrderGenerator.newOrder();
            String path = workPath + BatchExportConsts.SEPARATOR + fileLocation + BatchExportConsts.SEPARATOR;
            File pathFile = new File(SinglePathUtil.normalize((String)path));
            if (!pathFile.exists()) {
                pathFile.mkdirs();
            }
            try {
                JioImportParam jioImportParam = null;
                if (param.getVariableMap().containsKey("jioImportFilesParm")) {
                    jioImportParam = UploadJioDataUtil.getJIOImportParam(param, "jioImportFilesParm");
                }
                if (jioImportParam == null && StringUtils.isNotEmpty((String)filePath)) {
                    String subfile2;
                    List files = PathUtil.getFileList((String)filePath, (boolean)true, (String)"jio");
                    ArrayList<JIOImportResultObject> jioImportResults = new ArrayList<JIOImportResultObject>();
                    for (String subfile2 : files) {
                        File subPathFile = new File(SinglePathUtil.normalize((String)subfile2));
                        ImportResultObject r = this.queryMaping(param, subfile2);
                        JIOImportResultObject jioReulst = (JIOImportResultObject)r;
                        jioReulst.setJioFileName(subPathFile.getName());
                        jioImportResults.add(jioReulst);
                        jioReulst.setJioFilePath(subfile2);
                    }
                    JIOImportResultObject reulst = new JIOImportResultObject();
                    reulst.setJioImportResults(jioImportResults);
                    if (jioImportResults.size() > 0) {
                        reulst.setSuccess(true);
                    } else {
                        reulst.setSuccess(false);
                        reulst.setMessage("\u65e0\u5bf9\u5e94\u7684\u6620\u5c04\u5173\u7cfb\uff01");
                        logger.info("\u65e0\u5bf9\u5e94\u7684\u6620\u5c04\u5173\u7cfb\uff01");
                    }
                    subfile2 = this.getSuccessAsyncTaskInfo(reulst);
                    return subfile2;
                }
                if (jioImportParam != null && jioImportParam.getFileNodes().size() == 0 && StringUtils.isNotEmpty((String)param.getConfigKey())) {
                    JIOImportResultObject jioReulst;
                    List files = PathUtil.getFileList((String)filePath, (boolean)true, (String)"jio");
                    ArrayList<JIOImportResultObject> jioImportResults = new ArrayList<JIOImportResultObject>();
                    for (String subfile : files) {
                        File subPathFile = new File(SinglePathUtil.normalize((String)subfile));
                        jioReulst = new JIOImportResultObject();
                        if (subPathFile.exists()) {
                            UploadParam newParam = new UploadParam();
                            this.copyNewParam(newParam, param);
                            param.setFilePath(subfile);
                            AsyncTaskInfo importInfo = this.iUploadService.upload(newParam);
                            jioReulst.setJioImportTask(importInfo);
                            jioReulst.setJioConfigKey(param.getConfigKey());
                            jioReulst.setJioFilePath(subfile);
                        }
                        jioReulst.setJioFileName(subPathFile.getName());
                        jioImportResults.add(jioReulst);
                    }
                    JIOImportResultObject reulst = new JIOImportResultObject();
                    reulst.setJioImportResults(jioImportResults);
                    String taskId = UUID.randomUUID().toString();
                    AsyncTaskInfo asyncTaskInfo = new AsyncTaskInfo();
                    asyncTaskInfo.setId(taskId);
                    asyncTaskInfo.setProcess(Double.valueOf(0.0));
                    asyncTaskInfo.setResult("");
                    asyncTaskInfo.setDetail((Object)reulst);
                    asyncTaskInfo.setState(TaskState.FINISHED);
                    jioReulst = asyncTaskInfo;
                    return jioReulst;
                }
                if (jioImportParam != null && jioImportParam.getFileNodes().size() > 0) {
                    HashMap<String, RepeatImportParam> RepeatImportParams = new HashMap<String, RepeatImportParam>();
                    HashSet<String> allZdms = new HashSet<String>();
                    for (Object subfile : jioImportParam.getFileNodes()) {
                        String fileFullName = subfile.getFilePath();
                        try {
                            RepeatImportParam fileUnitParam = new RepeatImportParam();
                            fileUnitParam.setFilePath(subfile.getFilePath());
                            fileUnitParam.setFileName(subfile.getFileName());
                            RepeatImportParams.put(subfile.getFilePath(), fileUnitParam);
                            ArrayList fileZdms = new ArrayList();
                            this.jioReadService.readFMDMunitsByOther(path, subfile.getFilePath(), fileZdms);
                            if (fileZdms.size() <= 0) continue;
                            for (String zdm : fileZdms) {
                                if (allZdms.contains(zdm)) {
                                    RepeatEntityNode entityNode = new RepeatEntityNode();
                                    entityNode.setSingleZdm(zdm);
                                    entityNode.setRepeatMode(0);
                                    fileUnitParam.getEntityNodes().add(entityNode);
                                    continue;
                                }
                                allZdms.add(zdm);
                            }
                        }
                        finally {
                            if (!StringUtils.isEmpty((String)filePath)) continue;
                            PathUtil.deleteFile((String)fileFullName);
                        }
                    }
                    allZdms.clear();
                    ArrayList<JIOImportResultObject> jioImportResults = new ArrayList<JIOImportResultObject>();
                    for (JioImportFileNode subfile : jioImportParam.getFileNodes()) {
                        UploadParam newParam = new UploadParam();
                        this.copyNewParam(newParam, param);
                        newParam.setFilePath(subfile.getFilePath());
                        newParam.setConfigKey(subfile.getConfigKey());
                        newParam.setFileNameInfo(subfile.getFileName());
                        RepeatImportParam fileUnitParam = null;
                        if (RepeatImportParams.containsKey(subfile.getFilePath())) {
                            fileUnitParam = (RepeatImportParam)RepeatImportParams.get(subfile.getFilePath());
                            HashMap<String, RepeatImportParam> variableMap = newParam.getVariableMap();
                            if (variableMap == null) {
                                variableMap = new HashMap<String, RepeatImportParam>();
                                newParam.setVariableMap(variableMap);
                            }
                            if (fileUnitParam.getEntityNodes().size() > 0) {
                                fileUnitParam.setRepeatByFile(true);
                                variableMap.put("jioRepeatParm_Object", fileUnitParam);
                            }
                        }
                        AsyncTaskInfo importInfo = this.iUploadService.upload(newParam);
                        JIOImportResultObject jioReulst = new JIOImportResultObject();
                        jioReulst.setJioFileName(subfile.getFileName());
                        jioReulst.setJioFilePath(subfile.getFilePath());
                        jioReulst.setJioConfigKey(subfile.getConfigKey());
                        jioReulst.setJioImportTask(importInfo);
                        jioImportResults.add(jioReulst);
                        Thread.sleep(1000L);
                    }
                    JIOImportResultObject reulst = new JIOImportResultObject();
                    reulst.setJioImportResults(jioImportResults);
                    AsyncTaskInfo asyncTaskInfo = this.getSuccessAsyncTaskInfo(reulst);
                    return asyncTaskInfo;
                }
                AsyncTaskInfo asyncTaskInfo = this.getErrorAsyncTaskInfo(ERROR_IMPORT_PARAM);
                return asyncTaskInfo;
            }
            finally {
                PathUtil.deleteDir((String)path);
            }
        }
        return this.getErrorAsyncTaskInfo(ERROR_FILE_NOTEXIST);
    }

    @PostMapping(value={"/import-jio-file"})
    @ApiOperation(value="\u5bfc\u5165JIO\u6587\u4ef6")
    public AsyncTaskInfo importJioFile(@Valid @RequestBody UploadParam param) throws SingleDataException {
        if (param != null && param.getVariableMap() != null && param.getVariableMap().containsKey("JioImportFileName")) {
            String sourcefileName = (String)param.getVariableMap().get("JioImportFileName");
            try {
                if (StringUtils.isNotEmpty((String)sourcefileName) && StringUtils.isEmpty((String)param.getConfigKey())) {
                    ArrayList<JIOImportResultObject> jioImportResults = new ArrayList<JIOImportResultObject>();
                    File subPathFile = new File(SinglePathUtil.normalize((String)sourcefileName));
                    ImportResultObject r = this.queryMaping(param, sourcefileName);
                    JIOImportResultObject jioReulst = (JIOImportResultObject)r;
                    jioReulst.setJioFileName(subPathFile.getName());
                    jioImportResults.add(jioReulst);
                    jioReulst.setJioFilePath(sourcefileName);
                    JIOImportResultObject reulst = new JIOImportResultObject();
                    reulst.setJioImportResults(jioImportResults);
                    if (jioImportResults.size() > 0) {
                        reulst.setSuccess(true);
                    } else {
                        reulst.setSuccess(false);
                        reulst.setMessage("\u65e0\u5bf9\u5e94\u7684\u6620\u5c04\u5173\u7cfb\uff01");
                        logger.info("\u65e0\u5bf9\u5e94\u7684\u6620\u5c04\u5173\u7cfb\uff01");
                    }
                    return this.getSuccessAsyncTaskInfo(reulst);
                }
                if (StringUtils.isNotEmpty((String)param.getConfigKey())) {
                    ArrayList<JIOImportResultObject> jioImportResults = new ArrayList<JIOImportResultObject>();
                    File subPathFile = new File(SinglePathUtil.normalize((String)sourcefileName));
                    JIOImportResultObject jioReulst = new JIOImportResultObject();
                    if (subPathFile.exists()) {
                        param.setFilePath(sourcefileName);
                        AsyncTaskInfo importInfo = this.iUploadService.upload(param);
                        jioReulst.setJioImportTask(importInfo);
                        jioReulst.setJioConfigKey(param.getConfigKey());
                        jioReulst.setJioFilePath(sourcefileName);
                    }
                    jioReulst.setJioFileName(subPathFile.getName());
                    jioImportResults.add(jioReulst);
                    JIOImportResultObject reulst = new JIOImportResultObject();
                    reulst.setJioImportResults(jioImportResults);
                    return this.getSuccessAsyncTaskInfo(reulst);
                }
                return this.getErrorAsyncTaskInfo(ERROR_IMPORT_PARAM);
            }
            catch (SingleFileException e1) {
                logger.error(e1.getMessage(), e1);
                throw new SingleDataException(e1.getMessage(), (Throwable)e1);
            }
        }
        return this.getErrorAsyncTaskInfo(ERROR_FILE_NOTEXIST);
    }

    @PostMapping(value={"/import-jio-taskdir"})
    @ApiOperation(value="\u5bfc\u5165JIO\u4efb\u52a1\u76ee\u5f55")
    public AsyncTaskInfo importJioTaskDir(@Valid @RequestBody UploadParam param) throws SingleDataException {
        if (param != null && param.getVariableMap() != null && param.getVariableMap().containsKey("JioImportTaskDir")) {
            String sourceTaskDir = (String)param.getVariableMap().get("JioImportTaskDir");
            try {
                if (StringUtils.isNotEmpty((String)sourceTaskDir) && StringUtils.isEmpty((String)param.getConfigKey())) {
                    ArrayList<JIOImportResultObject> jioImportResults = new ArrayList<JIOImportResultObject>();
                    JIOImportResultObject r = this.queryMapingByTaskDir(param, sourceTaskDir);
                    r.setJioTaskDir(sourceTaskDir);
                    jioImportResults.add(r);
                    JIOImportResultObject reulst = new JIOImportResultObject();
                    reulst.setJioImportResults(jioImportResults);
                    if (jioImportResults.size() > 0) {
                        reulst.setSuccess(true);
                    } else {
                        reulst.setSuccess(false);
                        reulst.setMessage("\u65e0\u5bf9\u5e94\u7684\u6620\u5c04\u5173\u7cfb\uff01");
                        logger.info("\u65e0\u5bf9\u5e94\u7684\u6620\u5c04\u5173\u7cfb\uff01");
                    }
                    return this.getSuccessAsyncTaskInfo(reulst);
                }
                if (StringUtils.isNotEmpty((String)param.getConfigKey())) {
                    ArrayList<JIOImportResultObject> jioImportResults = new ArrayList<JIOImportResultObject>();
                    File subPathDir = new File(SinglePathUtil.normalize((String)sourceTaskDir));
                    JIOImportResultObject jioReulst = new JIOImportResultObject();
                    if (subPathDir.exists()) {
                        AsyncTaskInfo importInfo = this.uploadJIoTaskDir(param);
                        jioReulst.setJioImportTask(importInfo);
                        jioReulst.setJioConfigKey(param.getConfigKey());
                        jioReulst.setJioTaskDir(sourceTaskDir);
                    }
                    jioImportResults.add(jioReulst);
                    JIOImportResultObject reulst = new JIOImportResultObject();
                    reulst.setJioImportResults(jioImportResults);
                    return this.getSuccessAsyncTaskInfo(reulst);
                }
                return this.getErrorAsyncTaskInfo(ERROR_IMPORT_PARAM);
            }
            catch (SingleFileException e1) {
                logger.error(e1.getMessage(), e1);
                throw new SingleDataException(e1.getMessage(), (Throwable)e1);
            }
        }
        return this.getErrorAsyncTaskInfo(ERROR_FILE_NOTEXIST);
    }

    private AsyncTaskInfo getErrorAsyncTaskInfo(String errorCode) {
        String taskId = UUID.randomUUID().toString();
        AsyncTaskInfo asyncTaskInfo = new AsyncTaskInfo();
        asyncTaskInfo.setId(taskId);
        asyncTaskInfo.setProcess(Double.valueOf(1.0));
        asyncTaskInfo.setResult("");
        asyncTaskInfo.setDetail((Object)errorCode);
        asyncTaskInfo.setState(TaskState.ERROR);
        return asyncTaskInfo;
    }

    private AsyncTaskInfo getSuccessAsyncTaskInfo(JIOImportResultObject reulst) {
        String taskId = UUID.randomUUID().toString();
        AsyncTaskInfo asyncTaskInfo = new AsyncTaskInfo();
        asyncTaskInfo.setId(taskId);
        asyncTaskInfo.setProcess(Double.valueOf(0.0));
        asyncTaskInfo.setResult("");
        asyncTaskInfo.setDetail((Object)reulst);
        asyncTaskInfo.setState(TaskState.FINISHED);
        return asyncTaskInfo;
    }

    private AsyncTaskInfo uploadJIoTaskDir(UploadParam UploadParam2) throws NpAsyncTaskExecption {
        NpRealTimeTaskInfo npRealTimeTaskInfo = new NpRealTimeTaskInfo();
        npRealTimeTaskInfo.setTaskKey(UploadParam2.getTaskKey());
        npRealTimeTaskInfo.setFormSchemeKey(UploadParam2.getFormSchemeKey());
        npRealTimeTaskInfo.setArgs(SimpleParamConverter.SerializationUtils.serializeToString((Object)UploadParam2));
        npRealTimeTaskInfo.setAbstractRealTimeJob((AbstractRealTimeJob)new UploadJioTaskDirAsyncTaskExecutor());
        String asynTaskID = this.asyncTaskManager.publishTask(npRealTimeTaskInfo);
        AsyncTaskInfo asyncTaskInfo = new AsyncTaskInfo();
        asyncTaskInfo.setId(asynTaskID);
        asyncTaskInfo.setUrl("/api/asynctask/query?asynTaskID=");
        return asyncTaskInfo;
    }

    private String getJioWorkFilePath(UploadParam param) {
        String workPath = null;
        if (param.getVariableMap().containsKey("JioUploadWorkPath")) {
            workPath = (String)param.getVariableMap().get("JioUploadWorkPath");
        }
        if (StringUtils.isEmpty(workPath)) {
            workPath = BatchExportConsts.UPLOADDIR;
        }
        return workPath;
    }

    private ImportResultObject queryMaping(UploadParam param, File pathFile, String fileName) throws SingleFileException {
        ImportResultObject resultObject = this.queryMaping(param, pathFile.getPath() + BatchExportConsts.SEPARATOR + fileName);
        return resultObject;
    }

    private ImportResultObject queryMaping(UploadParam param, String filePath) throws SingleFileException {
        return this.jioMappingService.queryMappingByFile(filePath, param);
    }

    private JIOImportResultObject queryMapingByTaskDir(UploadParam param, String taskDir) throws SingleFileException {
        return this.jioMappingService.queryMappingByTaskDir(taskDir, param);
    }

    private void copyNewParam(UploadParam newParam, UploadParam oldParam) {
        newParam.setConfigKey(oldParam.getConfigKey());
        newParam.setDimensionSet(oldParam.getDimensionSet());
        newParam.setFileKey(oldParam.getFileKey());
        newParam.setFileKeyOfSOss(oldParam.getFileKeyOfSOss());
        newParam.setFilePath(oldParam.getFilePath());
        newParam.setFileLocation(oldParam.getFileLocation());
        newParam.setFileNameInfo(oldParam.getFileNameInfo());
        newParam.setFileType(oldParam.getFileType());
        newParam.setFormSchemeKey(oldParam.getFormSchemeKey());
        newParam.setFormulaSchemeKey(oldParam.getFormulaSchemeKey());
        newParam.setIsAppending(oldParam.isAppending());
        newParam.setIsMergeSplit(oldParam.isMergeSplit());
        newParam.setSplitMark(oldParam.getSplitMark());
        newParam.setTaskKey(oldParam.getTaskKey());
        newParam.setVariableMap(oldParam.getVariableMap());
    }
}

