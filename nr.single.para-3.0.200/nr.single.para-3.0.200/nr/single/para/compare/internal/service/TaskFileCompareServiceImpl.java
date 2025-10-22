/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.bi.util.OrderGenerator
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.nr.datascheme.api.DesignDataScheme
 *  com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService
 *  com.jiuqi.nr.definition.controller.IDesignTimeViewController
 *  com.jiuqi.nr.definition.facade.DesignFormSchemeDefine
 *  com.jiuqi.nr.definition.facade.DesignTaskDefine
 *  com.jiuqi.nr.single.core.common.InOutDataType
 *  com.jiuqi.nr.single.core.file.SingleFileConfigInfo
 *  com.jiuqi.nr.single.core.para.ParaInfo
 *  com.jiuqi.nr.single.core.para.parser.JIOParamParser
 *  com.jiuqi.nr.single.core.para.parser.table.FMRepInfo
 *  com.jiuqi.nr.single.core.para.parser.table.ZBInfo
 *  com.jiuqi.nr.single.core.service.SingleFileHelper
 *  com.jiuqi.nr.single.core.service.SingleFileParserService
 *  com.jiuqi.nr.single.core.util.SinglePathUtil
 *  nr.single.map.data.PathUtil
 *  nr.single.map.data.service.SingleJioFileService
 *  nr.single.map.param.service.SingleParamFileService
 */
package nr.single.para.compare.internal.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.bi.util.OrderGenerator;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.nr.datascheme.api.DesignDataScheme;
import com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService;
import com.jiuqi.nr.definition.controller.IDesignTimeViewController;
import com.jiuqi.nr.definition.facade.DesignFormSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.single.core.common.InOutDataType;
import com.jiuqi.nr.single.core.file.SingleFileConfigInfo;
import com.jiuqi.nr.single.core.para.ParaInfo;
import com.jiuqi.nr.single.core.para.parser.JIOParamParser;
import com.jiuqi.nr.single.core.para.parser.table.FMRepInfo;
import com.jiuqi.nr.single.core.para.parser.table.ZBInfo;
import com.jiuqi.nr.single.core.service.SingleFileHelper;
import com.jiuqi.nr.single.core.service.SingleFileParserService;
import com.jiuqi.nr.single.core.util.SinglePathUtil;
import java.io.FileInputStream;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import nr.single.map.data.PathUtil;
import nr.single.map.data.service.SingleJioFileService;
import nr.single.map.param.service.SingleParamFileService;
import nr.single.para.compare.bean.ParaCompareContext;
import nr.single.para.compare.bean.ParaCompareDataSchemeInfo;
import nr.single.para.compare.bean.ParaCompareOption;
import nr.single.para.compare.bean.ParaCompareResult;
import nr.single.para.compare.bean.ParaCompareTaskInfo;
import nr.single.para.compare.bean.ParaImportResult;
import nr.single.para.compare.definition.CompareInfoDTO;
import nr.single.para.compare.definition.ISingleCompareInfoService;
import nr.single.para.compare.definition.common.CompareContextType;
import nr.single.para.compare.definition.common.CompareDataType;
import nr.single.para.compare.definition.common.CompareStatusType;
import nr.single.para.compare.definition.exception.SingleCompareException;
import nr.single.para.compare.internal.system.SingleParaOptionsService;
import nr.single.para.compare.internal.util.CompareUtil;
import nr.single.para.compare.service.TaskDefineCompareService;
import nr.single.para.compare.service.TaskFileCompareService;
import nr.single.para.parain.bean.exception.SingleParaImportException;
import nr.single.para.parain.controller.SingleParaImportOption;
import nr.single.para.parain.service.IParaImportCommonService;
import nr.single.para.parain.service.ITaskFileImportService;
import nr.single.para.parain.util.IParaImportEventServcie;
import nr.single.para.parain.util.IParaImportFileServcie;
import nr.single.para.parain.util.IParaImportLogServcie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskFileCompareServiceImpl
implements TaskFileCompareService {
    private static final Logger log = LoggerFactory.getLogger(TaskFileCompareServiceImpl.class);
    @Autowired
    private SingleJioFileService jioService;
    @Autowired
    private IDesignTimeViewController viewController;
    @Autowired
    private SingleFileParserService singleParserService;
    @Autowired
    private TaskDefineCompareService taskCompareService;
    @Autowired
    private ISingleCompareInfoService infoService;
    @Autowired
    private ITaskFileImportService importService;
    @Autowired
    private IParaImportFileServcie fileService;
    @Autowired
    private IParaImportCommonService paraCommonService;
    @Autowired
    private IDesignDataSchemeService dataSchemeSevice;
    @Autowired
    private IParaImportLogServcie logService;
    @Autowired(required=false)
    private Map<String, IParaImportEventServcie> eventServiceMap;
    @Autowired
    private SingleFileHelper singleHelper;
    @Autowired
    private SingleParaOptionsService paraOptionService;
    @Autowired
    private SingleParamFileService singleParamService;

    @Override
    public ParaCompareResult findNetTaskBySingle(String fileName, byte[] fileData, AsyncTaskMonitor asyncMonitor) throws Exception {
        return this.findNetTaskBySingle(fileName, fileData, true, asyncMonitor);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public ParaCompareResult findNetTaskBySingle(String fileName, byte[] fileData, boolean matchByTaskCode, AsyncTaskMonitor asyncMonitor) throws Exception {
        ParaCompareResult result = null;
        String filePath = CompareUtil.getCompareFilePath();
        try {
            byte[] fileData2 = fileData;
            String jioFile = CompareUtil.saveToFile(filePath, fileName, fileData);
            if (fileData != null && fileData.length > 10245760) {
                SingleFileConfigInfo singleInfo = null;
                try {
                    singleInfo = this.jioService.getSingleInfoByJioFile(jioFile);
                }
                catch (Exception ex) {
                    log.error(ex.getMessage(), ex);
                }
                if (singleInfo != null && singleInfo.getInOutData().contains(InOutDataType.QYSJ)) {
                    String paramFile = filePath + "\u53c2\u6570_" + fileName;
                    String dataFile = filePath + "\u6570\u636e_" + fileName;
                    String oldJioFile = jioFile;
                    try {
                        this.singleHelper.splitSingleFile(oldJioFile, paramFile, dataFile);
                        jioFile = paramFile;
                        try (FileInputStream soureStream = new FileInputStream(SinglePathUtil.normalize((String)jioFile));){
                            byte[] Buffer = new byte[soureStream.available()];
                            soureStream.read(Buffer, 0, soureStream.available());
                            fileData2 = Buffer;
                        }
                    }
                    finally {
                        PathUtil.deleteFile((String)oldJioFile);
                        PathUtil.deleteFile((String)dataFile);
                    }
                }
            }
            result = this.findNetTaskBySingle(jioFile, matchByTaskCode, asyncMonitor);
            this.addInfo(result, fileName, fileData2);
        }
        finally {
            PathUtil.deleteDir((String)filePath);
        }
        return result;
    }

    @Override
    public ParaCompareResult findNetFormShemesByTaskAndYear(String taskKey, String taskYear, String singletaskTitle, AsyncTaskMonitor asyncMonitor) throws Exception {
        ParaCompareResult result = new ParaCompareResult();
        DesignTaskDefine taskDefine = this.viewController.queryTaskDefine(taskKey);
        List formSchemes = this.viewController.queryFormSchemeByTask(taskKey);
        for (DesignFormSchemeDefine formScheme : formSchemes) {
            ParaCompareTaskInfo findShemeInfo = new ParaCompareTaskInfo();
            findShemeInfo.setFormSchemeKey(formScheme.getKey());
            findShemeInfo.setTaskKey(formScheme.getTaskKey());
            findShemeInfo.setDataSchemeKey(taskDefine.getDataScheme());
            findShemeInfo.setTaskCode(taskDefine.getTaskCode());
            findShemeInfo.setTaskTitle(taskDefine.getTitle());
            if (this.paraOptionService.isTaskFindYear()) {
                ParaInfo para = this.singleParamService.getSingleTaskInfo(taskKey, formScheme.getKey());
                if (para == null || !StringUtils.isNotEmpty((String)taskYear) || !taskYear.equalsIgnoreCase(para.getTaskYear())) continue;
                result.getMathchTasks().add(findShemeInfo);
                continue;
            }
            result.getMathchTasks().add(findShemeInfo);
        }
        if (result.getMathchTasks().size() == 0) {
            ParaCompareTaskInfo netTaskInfo = new ParaCompareTaskInfo();
            netTaskInfo.setFormSchemeKey(null);
            netTaskInfo.setFormSchemeTitle(this.getFormSchemeTitle(singletaskTitle, formSchemes));
            netTaskInfo.setTaskKey(taskDefine.getKey());
            netTaskInfo.setDataSchemeKey(taskDefine.getDataScheme());
            netTaskInfo.setTaskCode(taskDefine.getTaskCode());
            netTaskInfo.setTaskTitle(taskDefine.getTitle());
            result.setNetTaskInfo(netTaskInfo);
        }
        return result;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public ParaCompareResult compareSingleToTasK(String compareKey, String taskKey, String formSchemeKey, String dataSchemeKey, ParaCompareOption option, AsyncTaskMonitor asyncMonitor) throws Exception {
        ParaCompareResult result = new ParaCompareResult();
        result.setCompareId(compareKey);
        CompareInfoDTO compareInfoDTO = new CompareInfoDTO();
        compareInfoDTO.setKey(compareKey);
        List<CompareInfoDTO> list = this.infoService.list(compareInfoDTO);
        if (list != null && list.size() > 0) {
            CompareInfoDTO info = list.get(0);
            info.setTaskKey(taskKey);
            info.setFormSchemeKey(formSchemeKey);
            info.setDataSchemeKey(dataSchemeKey);
            try {
                ObjectMapper mapper = new ObjectMapper();
                info.setOptionData(mapper.writeValueAsString((Object)option));
            }
            catch (Exception e) {
                log.info(e.getMessage());
            }
            this.infoService.update(info);
            String filePath = CompareUtil.getCompareFilePath();
            byte[] oldFileData = null;
            if (StringUtils.isNotEmpty((String)info.getJioData())) {
                oldFileData = info.getJioData().getBytes(StandardCharsets.UTF_8);
            }
            String file = this.fileService.downFile(filePath, info.getJioFile(), oldFileData, info.getJioFileKey());
            try {
                result = this.compareSingleToTaskByFile(result, null, null, taskKey, formSchemeKey, dataSchemeKey, file, option, asyncMonitor);
            }
            finally {
                PathUtil.deleteFile((String)file);
                PathUtil.deleteDir((String)filePath);
            }
        }
        return result;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public ParaCompareResult compareSingleToTasKByType(CompareDataType dataType, String compareDataKey, String compareInfoKey, ParaCompareOption option, AsyncTaskMonitor asyncMonitor) throws Exception {
        ParaCompareResult result = new ParaCompareResult();
        result.setCompareId(compareInfoKey);
        CompareInfoDTO compareInfoDTO = new CompareInfoDTO();
        compareInfoDTO.setKey(compareInfoKey);
        List<CompareInfoDTO> list = this.infoService.list(compareInfoDTO);
        if (list != null && list.size() > 0) {
            CompareInfoDTO info = list.get(0);
            option = this.getOptonFromInfo(option, info);
            String filePath = CompareUtil.getCompareFilePath();
            byte[] oldFileData = null;
            if (StringUtils.isNotEmpty((String)info.getJioData())) {
                oldFileData = info.getJioData().getBytes(StandardCharsets.UTF_8);
            }
            String file = this.fileService.downFile(filePath, info.getJioFile(), oldFileData, info.getJioFileKey());
            try {
                result = this.compareSingleToTaskByFile(result, dataType, compareDataKey, info.getTaskKey(), info.getFormSchemeKey(), info.getDataSchemeKey(), file, option, asyncMonitor);
            }
            finally {
                PathUtil.deleteFile((String)file);
                PathUtil.deleteDir((String)filePath);
            }
        }
        return result;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public ParaCompareResult batchCompareSingleToTasKByType(CompareDataType dataType, List<String> compareDataKeys, String compareInfoKey, ParaCompareOption option, AsyncTaskMonitor asyncMonitor) throws Exception {
        ParaCompareResult result = new ParaCompareResult();
        result.setCompareId(compareInfoKey);
        CompareInfoDTO compareInfoDTO = new CompareInfoDTO();
        compareInfoDTO.setKey(compareInfoKey);
        List<CompareInfoDTO> list = this.infoService.list(compareInfoDTO);
        if (list != null && list.size() > 0) {
            CompareInfoDTO info = list.get(0);
            option = this.getOptonFromInfo(option, info);
            String filePath = CompareUtil.getCompareFilePath();
            byte[] oldFileData = null;
            if (StringUtils.isNotEmpty((String)info.getJioData())) {
                oldFileData = info.getJioData().getBytes(StandardCharsets.UTF_8);
            }
            String file = this.fileService.downFile(filePath, info.getJioFile(), oldFileData, info.getJioFileKey());
            try {
                result = this.compareSingleToTaskByFile2(result, dataType, compareDataKeys, info.getTaskKey(), info.getFormSchemeKey(), info.getDataSchemeKey(), file, option, asyncMonitor);
            }
            finally {
                PathUtil.deleteFile((String)file);
                PathUtil.deleteDir((String)filePath);
            }
        }
        return result;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public ParaCompareResult importSingleToTask(String compareKey, ParaCompareOption option, AsyncTaskMonitor asyncMonitor) throws Exception {
        ParaCompareResult result = new ParaCompareResult();
        SingleParaImportOption importOption = null;
        if (option != null) {
            importOption = new SingleParaImportOption();
            importOption.setUploadTask(option.isUploadBaseParam());
            importOption.setUploadFormScheme(option.isUploadBaseParam());
            importOption.setUploadEnum(option.isUploadBaseParam());
            importOption.setUploadForm(option.isUploadBaseParam());
            importOption.setUploadTaskLink(option.isUploadBaseParam());
            importOption.setUploadQuery(option.isUploadBaseParam());
            importOption.setUploadFormula(option.isUploadFormula());
            importOption.setUploadPrint(option.isUploadPrint());
            importOption.setUploadQuery(option.isUploadQuery());
            importOption.setCorpEntityId(option.getCorpEntityId());
            importOption.setDateEntityId(option.getDateEntityId());
            importOption.setDimEntityIds(option.getDimEntityIds());
            importOption.setFilePrefix(option.getDataPrefix());
            importOption.setEnumPrefix(option.getEnumPrefix());
            importOption.setDataSchemeCode(option.getDataSchemeCode());
            importOption.setDataSchemeTitle(option.getDataSchemeTitle());
            importOption.setTaskCode(option.getTaskCode());
            importOption.setTaskTitle(option.getTaskTitle());
            importOption.setFromPeriod(option.getFromPeriod());
            importOption.setToPeriod(option.getToPeriod());
            importOption.setFormSchemeTitle(option.getFormSchemeTitle());
            importOption.setOverWriteAll(option.isOverWriteAll());
            importOption.setHistoryPara(option.getIsHistoryPara());
        }
        try {
            this.noticeBeforeImportPara(compareKey, importOption);
            String formSchemeKey = this.importService.ImportSingleToFormScheme(compareKey, importOption, asyncMonitor, result.getImportResult());
            result.setMapSchemeKey(result.getImportResult().getMapSchemeKey());
            this.noticeAfterImportPara(formSchemeKey, result.getImportResult(), asyncMonitor);
            try {
                String fileKey;
                log.info("\u751f\u6210\u65e5\u5fd7\u6587\u4ef6");
                if (asyncMonitor != null) {
                    asyncMonitor.progressAndMessage(0.95, "\u751f\u6210\u65e5\u5fd7\u6587\u4ef6");
                }
                if (StringUtils.isNotEmpty((String)(fileKey = this.logService.makeDetailLogFile(result.getImportResult(), asyncMonitor)))) {
                    this.modifyLogtoInfo(compareKey, result.getImportResult().getLogFile(), fileKey);
                    result.getImportResult().setLogFileKey(fileKey);
                }
            }
            catch (Exception e) {
                log.error("\u751f\u6210\u65e5\u5fd7\u6587\u4ef6\u51fa\u9519\uff1a" + e.getMessage(), e);
            }
            result.setFormSchemeKey(formSchemeKey);
            result.setSuccess(true);
        }
        finally {
            if (asyncMonitor != null) {
                asyncMonitor.progressAndMessage(1.0, "\u5bfc\u5165\u5b8c\u6210");
            }
        }
        return result;
    }

    @Override
    public ParaCompareResult batchDelete(String compareKey, AsyncTaskMonitor asyncMonitor) throws Exception {
        ParaCompareResult result = new ParaCompareResult();
        ParaCompareContext compareContext = new ParaCompareContext();
        compareContext.setAsyncMonitor(asyncMonitor);
        compareContext.setComapreResult(result);
        compareContext.onProgress(0.01, "\u5f00\u59cb\u5220\u9664\u6bd4\u8f83\u4fe1\u606f");
        log.info("\u5f00\u59cb\u5220\u9664\u6bd4\u8f83\u4fe1\u606f,\u65f6\u95f4:" + new Date().toString());
        result.setCompareId(compareKey);
        CompareInfoDTO compareInfoDTO = new CompareInfoDTO();
        compareInfoDTO.setKey(compareKey);
        List<CompareInfoDTO> list = this.infoService.list(compareInfoDTO);
        if (list != null && list.size() > 0) {
            CompareInfoDTO info = list.get(0);
            this.taskCompareService.batchDelete(compareContext, compareKey);
            if (StringUtils.isNotEmpty((String)info.getJioFileKey())) {
                this.fileService.deleteFile(info.getJioFileKey());
            }
            this.infoService.delete(info);
        }
        result.setSuccess(true);
        compareContext.onProgress(1.0, "\u5220\u9664\u6bd4\u8f83\u5b8c\u6210");
        log.info("\u5220\u9664\u6bd4\u8f83\u5b8c\u6210,\u65f6\u95f4:" + new Date().toString());
        return result;
    }

    @Override
    public List<ParaCompareResult> batchDeleteByKeys(List<String> compareKeys, AsyncTaskMonitor asyncMonitor) throws Exception {
        ArrayList<ParaCompareResult> list2 = new ArrayList<ParaCompareResult>();
        if (asyncMonitor != null) {
            asyncMonitor.progressAndMessage(0.01, "\u5f00\u59cb\u5220\u9664\u6bd4\u8f83\u4fe1\u606f");
        }
        log.info("\u5f00\u59cb\u5220\u9664\u6bd4\u8f83\u4fe1\u606f,\u65f6\u95f4:" + new Date().toString());
        if (compareKeys.isEmpty()) {
            return list2;
        }
        Instant lashMonth2 = Instant.now().minusMillis(TimeUnit.DAYS.toMillis(40L));
        double posSart = 0.0;
        double posLen = 1.0f / (float)compareKeys.size();
        for (String compareKey : compareKeys) {
            ParaCompareContext compareContext = new ParaCompareContext();
            ParaCompareResult result = new ParaCompareResult();
            compareContext.setAsyncMonitor(asyncMonitor);
            compareContext.setComapreResult(result);
            log.info(compareKey + "\u5f00\u59cb\u5220\u9664\u6bd4\u8f83\u4fe1\u606f,\u65f6\u95f4:" + new Date().toString());
            result.setCompareId(compareKey);
            CompareInfoDTO compareInfoDTO = new CompareInfoDTO();
            compareInfoDTO.setKey(compareKey);
            List<CompareInfoDTO> list = this.infoService.list(compareInfoDTO);
            if (list != null && list.size() > 0) {
                CompareInfoDTO info = list.get(0);
                if (info.getStatus() == CompareStatusType.SCHEME_DELETEING) {
                    if (info.getUpdateTime() != null && info.getUpdateTime().isAfter(lashMonth2)) continue;
                    info.setStatus(CompareStatusType.SCHEME_DELETEED);
                    this.infoService.update(info);
                } else if (info.getStatus() != CompareStatusType.SCHEME_DELETEED) {
                    info.setStatus(CompareStatusType.SCHEME_DELETEING);
                    this.infoService.update(info);
                }
                this.taskCompareService.batchDelete(compareContext, compareKey, posSart, posLen);
                if (StringUtils.isNotEmpty((String)info.getJioFileKey())) {
                    this.fileService.deleteFile(info.getJioFileKey());
                }
                this.infoService.delete(info);
            }
            result.setSuccess(true);
            log.info(compareKey + "\u5220\u9664\u6bd4\u8f83\u5b8c\u6210,\u65f6\u95f4:" + new Date().toString());
            posSart += posLen;
        }
        if (asyncMonitor != null) {
            asyncMonitor.progressAndMessage(1.0, "\u5220\u9664\u6bd4\u8f83\u5b8c\u6210");
        }
        log.info("\u5220\u9664\u6bd4\u8f83\u5b8c\u6210,\u65f6\u95f4:" + new Date().toString());
        return list2;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private ParaCompareResult findNetTaskBySingle(String file, boolean matchTaskCode, AsyncTaskMonitor asyncMonitor) throws Exception {
        ParaCompareResult result = new ParaCompareResult();
        ParaCompareContext compareContext = new ParaCompareContext();
        try {
            result.setCompareId(UUID.randomUUID().toString());
            compareContext.setAsyncMonitor(asyncMonitor);
            compareContext.onProgress(0.01, "\u5f00\u59cb\u67e5\u627e\u6587\u4ef6\u5bf9\u5e94\u7684\u4efb\u52a1");
            log.info("\u5f00\u59cb\u6bd4\u8f83\u6587\u4ef6:" + file + ",\u65f6\u95f4:" + new Date().toString());
            SingleFileConfigInfo singleInfo = null;
            try {
                singleInfo = this.jioService.getSingleInfoByJioFile(file);
            }
            catch (Exception ex) {
                log.error(ex.getMessage(), ex);
            }
            if (null != singleInfo && StringUtils.isNotEmpty((String)singleInfo.getTaskFlag())) {
                DesignTaskDefine task;
                if (!singleInfo.getInOutData().contains(InOutDataType.BBCS)) {
                    throw new Exception("\u6bd4\u8f83\u7684\u6587\u4ef6\u4e2d\u65e0\u53c2\u6570");
                }
                result.setSingleTaskFlag(singleInfo.getTaskFlag());
                result.setSingleFileFlag(singleInfo.getFileFlag());
                result.setSingleTaskTitle(singleInfo.getTaskName());
                result.setSingleTaskYear(singleInfo.getTaskYear());
                result.setTaskFindMode(this.paraOptionService.getTaskFindMode());
                PeriodType periodType = this.paraCommonService.getTaskPeriod(singleInfo.getTaskPeriod());
                result.setSinglePeriodType(periodType);
                if (periodType != PeriodType.CUSTOM) {
                    result.setSingleFromPeriod(singleInfo.getTaskYear() + this.paraCommonService.getPeriodTypeCode(periodType) + "0001");
                    result.setSingleToPeriod(this.paraCommonService.getLasPeriodCodeType(singleInfo.getTaskYear(), periodType));
                }
                List formShemes = this.viewController.queryAllFormSchemeDefine();
                ArrayList<DesignTaskDefine> findTaskList = new ArrayList<DesignTaskDefine>();
                ArrayList<DesignFormSchemeDefine> findFormSchemeList = new ArrayList<DesignFormSchemeDefine>();
                for (DesignFormSchemeDefine designFormSchemeDefine : formShemes) {
                    if (!singleInfo.getTaskFlag().equalsIgnoreCase(designFormSchemeDefine.getTaskPrefix())) continue;
                    task = this.viewController.queryTaskDefine(designFormSchemeDefine.getTaskKey());
                    if (task == null) {
                        log.info("\u5339\u914d\u5230\u62a5\u8868\u65b9\u6848," + designFormSchemeDefine.getFormSchemeCode() + "," + designFormSchemeDefine.getKey() + ",\u6240\u5c5e\u4efb\u52a1\u4e0d\u5b58\u5728\uff1a+" + designFormSchemeDefine.getTaskKey());
                        continue;
                    }
                    findFormSchemeList.add(designFormSchemeDefine);
                }
                if (findFormSchemeList.size() == 0 && matchTaskCode) {
                    Iterator taskDefines = this.viewController.getAllTaskDefines();
                    Iterator iterator = taskDefines.iterator();
                    while (iterator.hasNext()) {
                        task = (DesignTaskDefine)iterator.next();
                        String netTaskCode = task.getTaskCode();
                        netTaskCode = netTaskCode.replaceAll("\\d+", "");
                        String singleTaskCode = singleInfo.getTaskFlag();
                        if (!netTaskCode.equalsIgnoreCase(singleTaskCode = singleTaskCode.replaceAll("\\d+", ""))) continue;
                        findTaskList.add(task);
                    }
                }
                if (findFormSchemeList.size() > 0) {
                    for (DesignFormSchemeDefine designFormSchemeDefine : findFormSchemeList) {
                        task = this.viewController.queryTaskDefine(designFormSchemeDefine.getTaskKey());
                        if (task == null) {
                            log.info("\u5339\u914d\u5230\u62a5\u8868\u65b9\u6848," + designFormSchemeDefine.getFormSchemeCode() + "," + designFormSchemeDefine.getKey() + ",\u6240\u5c5e\u4efb\u52a1\u4e0d\u5b58\u5728\uff1a+" + designFormSchemeDefine.getTaskKey());
                            continue;
                        }
                        ParaCompareTaskInfo findTaskInfo = new ParaCompareTaskInfo();
                        findTaskInfo.setFormSchemeKey(designFormSchemeDefine.getKey());
                        findTaskInfo.setFormSchemeTitle(designFormSchemeDefine.getTitle());
                        findTaskInfo.setTaskKey(designFormSchemeDefine.getTaskKey());
                        findTaskInfo.setDataSchemeKey(task.getDataScheme());
                        findTaskInfo.setTaskCode(task.getTaskCode());
                        findTaskInfo.setTaskTitle(task.getTitle());
                        result.getMathchTasks().add(findTaskInfo);
                    }
                    result.setSuccess(true);
                } else if (findTaskList.size() > 0) {
                    for (DesignTaskDefine designTaskDefine : findTaskList) {
                        ParaCompareTaskInfo findTaskInfo = new ParaCompareTaskInfo();
                        findTaskInfo.setFormSchemeKey(null);
                        findTaskInfo.setTaskKey(designTaskDefine.getKey());
                        findTaskInfo.setDataSchemeKey(designTaskDefine.getDataScheme());
                        findTaskInfo.setTaskCode(designTaskDefine.getTaskCode());
                        findTaskInfo.setTaskTitle(designTaskDefine.getTitle());
                        List formSchemes = this.viewController.queryFormSchemeByTask(designTaskDefine.getKey());
                        boolean hasFindFormScheme = false;
                        if (this.paraOptionService.isTaskFindYear()) {
                            for (DesignFormSchemeDefine formScheme : formSchemes) {
                                ParaInfo para = this.singleParamService.getSingleTaskInfo(designTaskDefine.getKey(), formScheme.getKey());
                                if (para == null || !StringUtils.isNotEmpty((String)singleInfo.getTaskYear()) || !singleInfo.getTaskYear().equalsIgnoreCase(para.getTaskYear())) continue;
                                ParaCompareTaskInfo findShemeInfo = new ParaCompareTaskInfo();
                                findShemeInfo.setFormSchemeKey(formScheme.getKey());
                                findShemeInfo.setFormSchemeTitle(formScheme.getTitle());
                                findShemeInfo.setTaskKey(formScheme.getTaskKey());
                                findShemeInfo.setDataSchemeKey(designTaskDefine.getDataScheme());
                                findShemeInfo.setTaskCode(designTaskDefine.getTaskCode());
                                findShemeInfo.setTaskTitle(designTaskDefine.getTitle());
                                result.getMathchTasks().add(findShemeInfo);
                                hasFindFormScheme = true;
                            }
                            if (!hasFindFormScheme) {
                                result.getMathchTasks().add(findTaskInfo);
                            }
                        } else {
                            result.getMathchTasks().add(findTaskInfo);
                        }
                        if (hasFindFormScheme) continue;
                        findTaskInfo.setFormSchemeTitle(this.getFormSchemeTitle(singleInfo.getTaskName(), formSchemes));
                    }
                    result.setSuccess(true);
                } else {
                    ParaCompareDataSchemeInfo dataSchemeInfo = new ParaCompareDataSchemeInfo();
                    dataSchemeInfo.setDataPrefix(this.getDataSchemePrefix(singleInfo.getFileFlag()));
                    dataSchemeInfo.setDataSchemeCode(dataSchemeInfo.getDataPrefix() + "_" + singleInfo.getTaskFlag());
                    dataSchemeInfo.setDataSchemeTitle(this.getDataSchemeTitle(singleInfo.getTaskName()));
                    result.setDataSchemeInfo(dataSchemeInfo);
                    ParaCompareTaskInfo paraCompareTaskInfo = new ParaCompareTaskInfo();
                    paraCompareTaskInfo.setTaskCode(this.getNetTaskCode(singleInfo.getTaskFlag()));
                    paraCompareTaskInfo.setTaskTitle(this.getNetTaskTitle(singleInfo.getTaskName()));
                    paraCompareTaskInfo.setFormSchemeTitle(singleInfo.getTaskName());
                    result.setNetTaskInfo(paraCompareTaskInfo);
                    result.setSuccess(false);
                    result.setMessage("\u672a\u5339\u914d\u5230\u4efb\u52a1\u6216\u62a5\u8868\u65b9\u6848");
                }
            }
        }
        finally {
            compareContext.onProgress(1.0, "JIO\u67e5\u627e\u4efb\u52a1\u5b8c\u6210");
            PathUtil.deleteFile((String)file);
        }
        return result;
    }

    private String getDataSchemePrefix(String singleFileFlag) {
        String aCode = null;
        if (StringUtils.isEmpty(aCode)) {
            aCode = StringUtils.isNotEmpty((String)singleFileFlag) ? singleFileFlag.substring(0, 1) + OrderGenerator.newOrder().substring(5, 8) : "N" + OrderGenerator.newOrder().substring(5, 8);
            aCode = aCode.substring(0, 4);
            DesignDataScheme dataScheme = this.dataSchemeSevice.getDataSchemeByPrefix(aCode);
            while (null != dataScheme) {
                aCode = StringUtils.isNotEmpty((String)singleFileFlag) ? singleFileFlag.substring(0, 1) + OrderGenerator.newOrder().substring(5, 8) : "N" + OrderGenerator.newOrder().substring(4, 8);
                dataScheme = this.dataSchemeSevice.getDataSchemeByPrefix(aCode);
            }
        }
        return aCode;
    }

    private String getNetTaskCode(String singleTaskCode) {
        String aCode = singleTaskCode;
        DesignTaskDefine task = this.viewController.queryTaskDefineByCode(aCode);
        int num = 1;
        while (null != task) {
            aCode = singleTaskCode + String.valueOf(num);
            task = this.viewController.queryTaskDefineByCode(aCode);
            ++num;
        }
        return aCode;
    }

    private String getNetTaskTitle(String singleTaskTitle) {
        String aCode = singleTaskTitle;
        DesignTaskDefine task = this.viewController.queryTaskDefineByTaskTitle(aCode);
        int num = 1;
        while (null != task) {
            aCode = singleTaskTitle + String.valueOf(num);
            task = this.viewController.queryTaskDefineByTaskTitle(aCode);
            ++num;
        }
        return aCode;
    }

    private String getFormSchemeTitle(String title, List<DesignFormSchemeDefine> formSchemes) {
        String newTitle = title;
        if (StringUtils.isEmpty((String)newTitle)) {
            newTitle = title;
        }
        HashMap<String, DesignFormSchemeDefine> schemeMap = new HashMap<String, DesignFormSchemeDefine>();
        for (DesignFormSchemeDefine scheme : formSchemes) {
            schemeMap.put(scheme.getTitle(), scheme);
        }
        int i = 1;
        while (schemeMap.containsKey(newTitle)) {
            newTitle = title + String.valueOf(i);
            ++i;
        }
        return newTitle;
    }

    private String getDataSchemeTitle(String singleTaskTile) {
        String aCode = "";
        List dataSchemes = this.dataSchemeSevice.getAllDataScheme();
        HashMap dataSchemeDic = new HashMap();
        dataSchemes.forEach(scheme -> dataSchemeDic.put(scheme.getTitle(), scheme));
        if (StringUtils.isEmpty((String)aCode)) {
            aCode = singleTaskTile;
            DesignDataScheme dataScheme = (DesignDataScheme)dataSchemeDic.get(aCode);
            int aIndex = 1;
            while (null != dataScheme) {
                aCode = singleTaskTile + String.valueOf(aIndex);
                dataScheme = (DesignDataScheme)dataSchemeDic.get(aCode);
                ++aIndex;
            }
        }
        return aCode;
    }

    private ParaCompareResult compareSingleToTaskByFile(ParaCompareResult compareResult, CompareDataType dataType, String compareDataKey, String taskKey, String formSchemeKey, String dataSchemeKey, String file, ParaCompareOption option, AsyncTaskMonitor asyncMonitor) throws Exception {
        ArrayList<String> compareDataKeys = new ArrayList<String>();
        if (StringUtils.isNotEmpty((String)compareDataKey)) {
            compareDataKeys.add(compareDataKey);
        }
        return this.compareSingleToTaskByFile2(compareResult, dataType, compareDataKeys, taskKey, formSchemeKey, dataSchemeKey, file, option, asyncMonitor);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private ParaCompareResult compareSingleToTaskByFile2(ParaCompareResult compareResult, CompareDataType dataType, List<String> compareDataKeys, String taskKey, String formSchemeKey, String dataSchemeKey, String file, ParaCompareOption option, AsyncTaskMonitor asyncMonitor) throws Exception {
        ParaCompareContext compareContext = new ParaCompareContext();
        try {
            compareContext.setAsyncMonitor(asyncMonitor);
            compareContext.setComapreResult(compareResult);
            compareContext.setOption(option);
            compareContext.setTaskKey(taskKey);
            compareContext.setFormSchemeKey(formSchemeKey);
            compareContext.setDataSchemeKey(dataSchemeKey);
            compareContext.onProgress(0.01, "\u5f00\u59cb\u6bd4\u8f83\u6587\u4ef6");
            log.info("\u5f00\u59cb\u5bfc\u5165\u6587\u4ef6:" + file + ",\u65f6\u95f4:" + new Date().toString());
            long currentTimeMillis = System.currentTimeMillis();
            long starCurrentTimeMillis = System.currentTimeMillis();
            JIOParamParser jioParaser = this.singleParserService.getParaParaser(file);
            try {
                if (jioParaser.getInOutData().indexOf(InOutDataType.BBCS) < 0 && jioParaser.getInOutData().indexOf(InOutDataType.CSCS) < 0) {
                    throw new Exception("\u5bfc\u5165\u7684\u6587\u4ef6\u4e2d\u65e0\u53c2\u6570");
                }
                compareContext.setJioParser(jioParaser);
                compareContext.setParaInfo(jioParaser.getParaInfo());
                log.info("\u89e3\u6790JIO\u8017\u65f6\uff1a===================" + (System.currentTimeMillis() - currentTimeMillis));
                currentTimeMillis = System.currentTimeMillis();
                compareContext.onProgress(0.05, "\u89e3\u6790JIO\u5b8c\u6210");
                if (dataType == null) {
                    this.taskCompareService.compareTaskDefine(compareContext, compareResult);
                } else {
                    String compareDataKey = null;
                    if (compareDataKeys != null && compareDataKeys.size() == 1) {
                        compareDataKey = compareDataKeys.get(0);
                    }
                    compareContext.setCompareDataType(dataType);
                    if (dataType == CompareDataType.DATA_FMDMFIELD) {
                        this.taskCompareService.compareFMDMDefine(compareContext, compareResult);
                    } else if (dataType == CompareDataType.DATA_ENUM) {
                        if (compareDataKeys == null || compareDataKeys.isEmpty()) {
                            this.taskCompareService.compareEnumListDefine(compareContext, compareResult);
                        } else if (compareDataKeys.size() == 1) {
                            this.taskCompareService.compareEnumDefine(compareContext, compareDataKey, compareResult);
                        } else {
                            this.taskCompareService.compareEnumDefines(compareContext, compareDataKeys, compareResult);
                        }
                    } else if (dataType == CompareDataType.DATA_ENUMITEM) {
                        if (!StringUtils.isEmpty((String)compareDataKey)) {
                            this.taskCompareService.compareEnumDefine(compareContext, compareDataKey, compareResult);
                        }
                    } else if (dataType == CompareDataType.DATA_FORM) {
                        if (compareDataKeys == null || compareDataKeys.isEmpty()) {
                            this.taskCompareService.compareFormListDefine(compareContext, compareResult);
                        } else if (compareDataKeys.size() == 1) {
                            this.taskCompareService.compareFormDefine(compareContext, compareDataKey, compareResult);
                        } else {
                            this.taskCompareService.compareFormDefines(compareContext, compareDataKeys, compareResult);
                        }
                    } else if (dataType == CompareDataType.DATA_REGION) {
                        Integer singleFloatingId = -1;
                        String newTableKey = null;
                        if (option != null && option.getVariableMap() != null) {
                            if (option.getVariableMap().containsKey("singleFloatingId")) {
                                String idCode = option.getVariableMap().get("singleFloatingId");
                                singleFloatingId = Integer.parseInt(idCode);
                            }
                            if (option.getVariableMap().containsKey("newTableKey")) {
                                newTableKey = option.getVariableMap().get("newTableKey");
                            }
                        }
                        this.taskCompareService.compareFormRegionDefine(compareContext, compareDataKey, singleFloatingId, newTableKey, compareResult);
                    } else if (dataType == CompareDataType.DATA_FIELD) {
                        if (StringUtils.isEmpty((String)compareDataKey)) {
                            this.taskCompareService.compareFormListDefine(compareContext, compareResult);
                        } else {
                            this.taskCompareService.compareFormDefine(compareContext, compareDataKey, compareResult);
                        }
                    }
                }
                log.info("\u6bd4\u8f83\u53c2\u6570\u8017\u65f6\uff1a===================" + (System.currentTimeMillis() - currentTimeMillis));
                currentTimeMillis = System.currentTimeMillis();
                compareContext.onProgress(0.95, "\u6bd4\u8f83\u53c2\u6570\u5b8c\u6210");
                log.info("\u6bd4\u8f83\u53c2\u6570\u603b\u8017\u65f6\uff1a===================" + (System.currentTimeMillis() - starCurrentTimeMillis));
            }
            finally {
                PathUtil.deleteDir((String)jioParaser.getFilePath());
            }
        }
        catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            throw ex;
        }
        finally {
            compareContext.onProgress(1.0, "JIO\u6bd4\u8f83\u4efb\u52a1\u5b8c\u6210");
        }
        return compareResult;
    }

    private void addInfo(ParaCompareResult result, String fileName, byte[] jioData) throws Exception {
        String fileKey = this.fileService.uploadFile(fileName, jioData);
        CompareInfoDTO compareInfoDTO = new CompareInfoDTO();
        compareInfoDTO.setKey(result.getCompareId());
        compareInfoDTO.setCode(result.getSingleTaskFlag());
        compareInfoDTO.setJioFile(fileName);
        compareInfoDTO.setJioFileKey(fileKey);
        compareInfoDTO.setStatus(CompareStatusType.STATUS_BEFORCONFIRD);
        compareInfoDTO.setTitle("\u5bfc\u5165\u53c2\u6570" + result.getSingleTaskTitle() + "" + new Date());
        compareInfoDTO.setFileFlag(result.getSingleFileFlag());
        compareInfoDTO.setTaskYear(result.getSingleTaskYear());
        compareInfoDTO.setTaskTitle(result.getSingleTaskTitle());
        compareInfoDTO.setOrder(OrderGenerator.newOrder());
        this.infoService.add(compareInfoDTO);
    }

    private void modifyLogtoInfo(String compareInfoKey, String logFile, String logFileKey) throws SingleCompareException {
        CompareInfoDTO info;
        CompareInfoDTO compareInfoDTO = new CompareInfoDTO();
        compareInfoDTO.setKey(compareInfoKey);
        List<CompareInfoDTO> list = this.infoService.list(compareInfoDTO);
        if (list != null && list.size() > 0 && (info = list.get(0)) != null) {
            info.setLogFile(logFile);
            info.setLogFileKey(logFileKey);
            this.infoService.update(info);
        }
    }

    private ParaCompareOption getOptonFromInfo(ParaCompareOption option, CompareInfoDTO info) {
        ParaCompareOption cmpoareOption = null;
        try {
            if (StringUtils.isNotEmpty((String)info.getOptionData())) {
                ObjectMapper mapper = new ObjectMapper();
                cmpoareOption = (ParaCompareOption)mapper.readValue(info.getOptionData(), ParaCompareOption.class);
            }
        }
        catch (Exception e) {
            log.info(e.getMessage());
        }
        if (cmpoareOption == null) {
            cmpoareOption = new ParaCompareOption();
            cmpoareOption.setCorpEntityId("");
            cmpoareOption.setDateEntityId("");
            cmpoareOption.setDimEntityIds("");
            cmpoareOption.setEnumCompareType(CompareContextType.CONTEXT_TITLE);
            cmpoareOption.setFieldContainForm(true);
            cmpoareOption.setOverWriteAll(false);
            cmpoareOption.setUploadBaseParam(true);
            cmpoareOption.setUploadFormula(true);
            cmpoareOption.setUploadPrint(true);
            cmpoareOption.setUploadQuery(true);
            cmpoareOption.setUseFormLevel(true);
            cmpoareOption.setHistoryPara(true);
        }
        if (option == null) {
            option = cmpoareOption;
        } else {
            if (option.getCompareType() == null) {
                option.setCompareType(cmpoareOption.getCompareType());
            }
            if (StringUtils.isEmpty((String)option.getCorpEntityId())) {
                option.setCorpEntityId(cmpoareOption.getCorpEntityId());
            }
            if (StringUtils.isEmpty((String)option.getDateEntityId())) {
                option.setDateEntityId(cmpoareOption.getDateEntityId());
            }
            if (StringUtils.isEmpty((String)option.getDimEntityIds())) {
                option.setDimEntityIds(cmpoareOption.getDimEntityIds());
            }
            if (option.getCompareType() == null) {
                option.setCompareType(cmpoareOption.getCompareType());
            }
            if (StringUtils.isEmpty((String)option.getDataPrefix())) {
                option.setDataPrefix(cmpoareOption.getDataPrefix());
            }
            if (StringUtils.isEmpty((String)option.getEnumPrefix())) {
                option.setEnumPrefix(cmpoareOption.getEnumPrefix());
            }
            if (StringUtils.isEmpty((String)option.getDataSchemeCode())) {
                option.setDataSchemeCode(cmpoareOption.getDataSchemeCode());
            }
            if (StringUtils.isEmpty((String)option.getDataSchemeTitle())) {
                option.setDataSchemeTitle(cmpoareOption.getDataSchemeTitle());
            }
            if (StringUtils.isEmpty((String)option.getTaskCode())) {
                option.setTaskCode(cmpoareOption.getTaskCode());
            }
            if (StringUtils.isEmpty((String)option.getTaskTitle())) {
                option.setTaskTitle(cmpoareOption.getTaskTitle());
            }
            if (StringUtils.isEmpty((String)option.getFromPeriod())) {
                option.setTaskCode(cmpoareOption.getFromPeriod());
            }
            if (StringUtils.isEmpty((String)option.getToPeriod())) {
                option.setTaskTitle(cmpoareOption.getToPeriod());
            }
            if (StringUtils.isEmpty((String)option.getFormSchemeTitle())) {
                option.setFormSchemeTitle(cmpoareOption.getFormSchemeTitle());
            }
            if (option.getFloatRegionCompareType() == null) {
                option.setFloatRegionCompareType(cmpoareOption.getFloatRegionCompareType());
            }
            option.setHistoryPara(cmpoareOption.getIsHistoryPara());
            option.setUseFormLevel(cmpoareOption.isUseFormLevel());
        }
        return option;
    }

    private void noticeBeforeImportPara(String compareKey, SingleParaImportOption option) throws SingleParaImportException {
        if (this.eventServiceMap != null && !this.eventServiceMap.isEmpty()) {
            for (Map.Entry<String, IParaImportEventServcie> entry : this.eventServiceMap.entrySet()) {
                IParaImportEventServcie eventService = entry.getValue();
                String serviceInfo = "\u5bfc\u5165JIO\u53c2\u6570\uff1a\u5f00\u59cb\uff0c\u901a\u77e5\uff1a" + entry.getKey() + "";
                log.info(serviceInfo);
                eventService.beforeImport(compareKey, option);
            }
        }
    }

    private void noticeAfterImportPara(String formSchemeKey, ParaImportResult result, AsyncTaskMonitor asyncMonitor) {
        if (this.eventServiceMap != null && !this.eventServiceMap.isEmpty()) {
            for (Map.Entry<String, IParaImportEventServcie> entry : this.eventServiceMap.entrySet()) {
                IParaImportEventServcie eventService = entry.getValue();
                String serviceInfo = "\u5bfc\u5165JIO\u53c2\u6570\uff1a\u7ed3\u675f\uff0c\u901a\u77e5\uff1a" + entry.getKey() + "";
                log.info(serviceInfo);
                eventService.afterImport(formSchemeKey, result, asyncMonitor);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public List<String> getSingleEnumCodeInFmdm(String compareKey) throws SingleCompareException {
        ArrayList<String> list = new ArrayList<String>();
        CompareInfoDTO info = this.infoService.getByKey(compareKey);
        if (info != null) {
            try {
                String filePath = CompareUtil.getCompareFilePath();
                byte[] oldFileData = null;
                if (StringUtils.isNotEmpty((String)info.getJioData())) {
                    oldFileData = info.getJioData().getBytes(StandardCharsets.UTF_8);
                }
                String file = this.fileService.downFile(filePath, info.getJioFile(), oldFileData, info.getJioFileKey());
                try {
                    JIOParamParser jioParaser = this.singleParserService.getParaParaser(file);
                    if (jioParaser.getInOutData().indexOf(InOutDataType.BBCS) < 0 && jioParaser.getInOutData().indexOf(InOutDataType.CSCS) < 0) {
                        throw new Exception("\u5bfc\u5165\u7684\u6587\u4ef6\u4e2d\u65e0\u53c2\u6570");
                    }
                    FMRepInfo rep = jioParaser.getParaInfo().getFmRepInfo();
                    for (ZBInfo zb : rep.getDefs().getZbsNoZDM()) {
                        if (!StringUtils.isNotEmpty((String)zb.getEnumId())) continue;
                        list.add(zb.getEnumId());
                    }
                }
                finally {
                    PathUtil.deleteFile((String)file);
                    PathUtil.deleteDir((String)filePath);
                }
            }
            catch (Exception e) {
                log.error(e.getMessage(), e);
                throw new SingleCompareException(e.getMessage(), e);
            }
        }
        return list;
    }
}

