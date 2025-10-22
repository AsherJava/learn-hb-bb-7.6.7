/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob
 *  com.jiuqi.bi.oss.ObjectInfo
 *  com.jiuqi.bi.security.PathUtils
 *  com.jiuqi.bi.security.SecurityContentException
 *  com.jiuqi.bi.util.OrderGenerator
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.asynctask.AsyncTaskManager
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.asynctask.NpRealTimeTaskInfo
 *  com.jiuqi.np.asynctask.TaskState
 *  com.jiuqi.np.asynctask.util.SimpleParamConverter$SerializationUtils
 *  com.jiuqi.np.core.application.NpApplication
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.definition.common.Consts
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.np.i18n.helper.I18nHelper
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.nr.common.asynctask.entity.AsyncTaskInfo
 *  com.jiuqi.nr.common.cache.remote.CacheObjectResourceRemote
 *  com.jiuqi.nr.common.constant.ErrorCode
 *  com.jiuqi.nr.common.exception.ExceptionCodeCost
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.data.access.service.IDataAccessServiceProvider
 *  com.jiuqi.nr.data.access.util.DataAccesslUtil
 *  com.jiuqi.nr.data.access.util.DimensionValueSetUtil
 *  com.jiuqi.nr.data.common.CommonMessage
 *  com.jiuqi.nr.data.common.Message
 *  com.jiuqi.nr.data.common.param.CommonParams
 *  com.jiuqi.nr.data.common.param.ImportFileDataRange
 *  com.jiuqi.nr.data.common.service.dto.ImportCancledResult
 *  com.jiuqi.nr.data.excel.param.UploadParam
 *  com.jiuqi.nr.data.excel.param.bean.ExcelImportResultItem
 *  com.jiuqi.nr.data.excel.param.bean.ImportResultItem
 *  com.jiuqi.nr.data.excel.param.bean.ImportResultObject
 *  com.jiuqi.nr.data.excel.service.IUploadExcelService
 *  com.jiuqi.nr.data.excel.service.impl.UploadExcelBaseService
 *  com.jiuqi.nr.data.excel.service.impl.UploadMultiServiceImpl
 *  com.jiuqi.nr.data.text.param.TextParams
 *  com.jiuqi.nr.data.text.param.TextType
 *  com.jiuqi.nr.data.text.service.ImpTextService
 *  com.jiuqi.nr.dataentity.service.DataEntityFullService
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationBuilder
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.controller.ITaskOptionController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.entity.engine.executors.ExecutorContext
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.engine.setting.AuthorityType
 *  com.jiuqi.nr.entity.exception.UnauthorizedEntityException
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityAuthorityService
 *  com.jiuqi.nr.entity.service.IEntityDataService
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.file.FileInfo
 *  com.jiuqi.nr.file.FileService
 *  com.jiuqi.nr.fileupload.service.FileUploadOssService
 *  com.jiuqi.nr.io.params.base.TableContext
 *  com.jiuqi.nr.io.params.input.OptTypes
 *  com.jiuqi.nr.io.params.output.ImportInformations
 *  com.jiuqi.nr.io.service.DataFileImportService
 *  com.jiuqi.nr.io.service.IoQualifier
 *  com.jiuqi.nr.io.util.FileUtil
 *  com.jiuqi.nr.jtable.aop.JLoggerAspect
 *  com.jiuqi.nr.jtable.exception.NotFoundFormSchemeException
 *  com.jiuqi.nr.jtable.params.base.EntityViewData
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  com.jiuqi.nr.jtable.service.IJtableEntityService
 *  com.jiuqi.nr.jtable.service.IJtableParamService
 *  com.jiuqi.nr.jtable.util.DimensionValueSetUtil
 *  com.jiuqi.nr.jtable.util.JsonUtil
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService
 *  javax.annotation.Resource
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.nr.dataentry.internal.service;

import com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob;
import com.jiuqi.bi.oss.ObjectInfo;
import com.jiuqi.bi.security.PathUtils;
import com.jiuqi.bi.security.SecurityContentException;
import com.jiuqi.bi.util.OrderGenerator;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.asynctask.AsyncTaskManager;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.asynctask.NpRealTimeTaskInfo;
import com.jiuqi.np.asynctask.TaskState;
import com.jiuqi.np.asynctask.util.SimpleParamConverter;
import com.jiuqi.np.core.application.NpApplication;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.np.i18n.helper.I18nHelper;
import com.jiuqi.np.log.LogHelper;
import com.jiuqi.nr.common.asynctask.entity.AsyncTaskInfo;
import com.jiuqi.nr.common.cache.remote.CacheObjectResourceRemote;
import com.jiuqi.nr.common.constant.ErrorCode;
import com.jiuqi.nr.common.exception.ExceptionCodeCost;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.data.access.service.IDataAccessServiceProvider;
import com.jiuqi.nr.data.access.util.DataAccesslUtil;
import com.jiuqi.nr.data.common.CommonMessage;
import com.jiuqi.nr.data.common.Message;
import com.jiuqi.nr.data.common.param.CommonParams;
import com.jiuqi.nr.data.common.param.ImportFileDataRange;
import com.jiuqi.nr.data.common.service.dto.ImportCancledResult;
import com.jiuqi.nr.data.excel.param.bean.ImportResultItem;
import com.jiuqi.nr.data.excel.param.bean.ImportResultObject;
import com.jiuqi.nr.data.excel.service.IUploadExcelService;
import com.jiuqi.nr.data.excel.service.impl.UploadExcelBaseService;
import com.jiuqi.nr.data.excel.service.impl.UploadMultiServiceImpl;
import com.jiuqi.nr.data.text.param.TextParams;
import com.jiuqi.nr.data.text.param.TextType;
import com.jiuqi.nr.data.text.service.ImpTextService;
import com.jiuqi.nr.dataentity.service.DataEntityFullService;
import com.jiuqi.nr.dataentry.asynctask.UploadAsyncTaskExecutor;
import com.jiuqi.nr.dataentry.bean.AbstractImportResultItem;
import com.jiuqi.nr.dataentry.bean.AsyncUploadParam;
import com.jiuqi.nr.dataentry.bean.ExcelImportResultItem;
import com.jiuqi.nr.dataentry.bean.IRepeatEntityNode;
import com.jiuqi.nr.dataentry.bean.IRepeatFormNode;
import com.jiuqi.nr.dataentry.bean.IRepeatImportParam;
import com.jiuqi.nr.dataentry.bean.ImportCancledResultParam;
import com.jiuqi.nr.dataentry.bean.JIOFormImportResult;
import com.jiuqi.nr.dataentry.bean.JIOUnitImportResult;
import com.jiuqi.nr.dataentry.bean.LogInfo;
import com.jiuqi.nr.dataentry.bean.ReportImportResultObject;
import com.jiuqi.nr.dataentry.bean.UploadParam;
import com.jiuqi.nr.dataentry.internal.service.ExportExcelNameServiceImpl;
import com.jiuqi.nr.dataentry.monitor.SimpleAsyncProgressMonitor;
import com.jiuqi.nr.dataentry.monitor.UploadAsyncMonitor;
import com.jiuqi.nr.dataentry.options.DataentryOptionsUtil;
import com.jiuqi.nr.dataentry.paramInfo.BatchCalculateInfo;
import com.jiuqi.nr.dataentry.paramInfo.BatchReturnInfo;
import com.jiuqi.nr.dataentry.service.IBatchCalculateService;
import com.jiuqi.nr.dataentry.service.IUploadService;
import com.jiuqi.nr.dataentry.service.IUploadTypeService;
import com.jiuqi.nr.dataentry.util.BatchExportConsts;
import com.jiuqi.nr.dataentry.util.Consts;
import com.jiuqi.nr.dataentry.util.DataEntryUtil;
import com.jiuqi.nr.dataentry.util.LoggerUtil;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationBuilder;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.controller.ITaskOptionController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.entity.engine.executors.ExecutorContext;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.engine.setting.AuthorityType;
import com.jiuqi.nr.entity.exception.UnauthorizedEntityException;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityAuthorityService;
import com.jiuqi.nr.entity.service.IEntityDataService;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.file.FileInfo;
import com.jiuqi.nr.file.FileService;
import com.jiuqi.nr.fileupload.service.FileUploadOssService;
import com.jiuqi.nr.io.params.base.TableContext;
import com.jiuqi.nr.io.params.input.OptTypes;
import com.jiuqi.nr.io.params.output.ImportInformations;
import com.jiuqi.nr.io.service.DataFileImportService;
import com.jiuqi.nr.io.service.IoQualifier;
import com.jiuqi.nr.io.util.FileUtil;
import com.jiuqi.nr.jtable.aop.JLoggerAspect;
import com.jiuqi.nr.jtable.exception.NotFoundFormSchemeException;
import com.jiuqi.nr.jtable.params.base.EntityViewData;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.service.IJtableEntityService;
import com.jiuqi.nr.jtable.service.IJtableParamService;
import com.jiuqi.nr.jtable.util.DimensionValueSetUtil;
import com.jiuqi.nr.jtable.util.JsonUtil;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UploadServiecImpl
implements IUploadService {
    private static final Logger logger = LoggerFactory.getLogger(UploadServiecImpl.class);
    @Autowired
    private Map<String, IUploadTypeService> uploadTypeServiceMap;
    @Resource
    private IRunTimeViewController controller;
    @Autowired
    private NpApplication npApplication;
    @Resource
    private AsyncTaskManager asyncTaskManager;
    @Autowired
    private CacheObjectResourceRemote cacheObjectResourceRemote;
    @Autowired
    private IBatchCalculateService batchCalculateService;
    @Autowired
    private IJtableEntityService jtableEntityService;
    @Autowired
    private DataFileImportService txtImpService;
    @Autowired
    private ImpTextService impTextService;
    @Autowired
    private JLoggerAspect jLoggerAspect;
    @Autowired
    private IEntityViewRunTimeController iEntityViewRunTimeController;
    @Autowired
    private IDataDefinitionRuntimeController iDataDefinitionRuntimeController;
    @Autowired
    private IJtableParamService jtableParamService;
    @Autowired
    private IEntityMetaService iEntityMetaService;
    @Autowired
    private IEntityDataService iEntityDataService;
    @Autowired
    private FileService fileService;
    @Autowired
    private ExportExcelNameServiceImpl exportExcelNameService;
    @Autowired
    private DataentryOptionsUtil dataentryOptionsUtil;
    @Autowired
    private IRunTimeViewController runtimeViewController;
    @Autowired
    private PeriodEngineService periodEngineService;
    @Autowired
    private FileUploadOssService fileUploadOssService;
    @Autowired
    private ITaskOptionController iTaskOptionController;
    @Autowired
    private DataEntityFullService dataEntityFullService;
    @Autowired
    private DataAccesslUtil dataAccesslUtil;
    @Autowired
    private IEntityDataService entityDataService;
    @Autowired
    private IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    @Autowired
    private INvwaSystemOptionService iNvwaSystemOptionService;
    @Autowired
    @Qualifier(value="nr")
    private I18nHelper i18nHelper;
    @Autowired(required=false)
    private IoQualifier ioQualifier;
    @Autowired
    private UploadMultiServiceImpl uploadMultiServiceImpl;
    @Autowired
    private IDataAccessServiceProvider iDataAccessServiceProvider;
    @Autowired
    private UploadExcelBaseService uploadExcelBaseService;
    @Autowired
    private IUploadExcelService uploadExcelService;
    @Autowired
    private IEntityAuthorityService iEntityAuthorityService;
    @Autowired
    private IPeriodEntityAdapter periodEntityAdapter;
    static Map<String, String> FILETYPEMAP = new CaseInsensitiveMap<String>();

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public AsyncTaskInfo upload(MultipartFile multipartFile, UploadParam param) {
        if (param.getVariableMap() == null) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            param.setVariableMap(map);
        }
        String fileName = multipartFile.getOriginalFilename();
        File sourcefile = new File(fileName);
        String[] split = (fileName = sourcefile.getName()).split("\\.");
        String suffix = split[split.length - 1];
        if (suffix.equals("et")) {
            suffix = "xlsx";
        }
        String taskId = UUID.randomUUID().toString();
        if (suffix.equals("jio") && StringUtils.isEmpty((String)param.getConfigKey())) {
            com.jiuqi.nr.dataentry.bean.ImportResultObject importResultObject = this.getMappingConfig(multipartFile, param);
            AsyncTaskInfo asyncTaskInfo = new AsyncTaskInfo();
            asyncTaskInfo.setId(taskId);
            asyncTaskInfo.setProcess(Double.valueOf(0.0));
            asyncTaskInfo.setResult("");
            asyncTaskInfo.setDetail((Object)importResultObject);
            asyncTaskInfo.setState(TaskState.FINISHED);
            return asyncTaskInfo;
        }
        FileInfo fileInfo = null;
        try (InputStream ins = multipartFile.getInputStream();){
            String dataentryUploadArea = "UPLOADTEMP";
            long fileSizeByte = this.fileService.area(dataentryUploadArea).getAreaConfig().getMaxFileSize();
            double fileSizeM = (double)fileSizeByte / 1048576.0;
            if (fileSizeByte < multipartFile.getSize()) {
                AsyncTaskInfo asyncTaskInfo = new AsyncTaskInfo();
                asyncTaskInfo.setId(taskId);
                asyncTaskInfo.setProcess(Double.valueOf(1.0));
                asyncTaskInfo.setResult("");
                asyncTaskInfo.setDetail((Object)("\u6587\u4ef6\u5927\u5c0f\u5927\u4e8e\u914d\u7f6e\u503c" + fileSizeM + "M!!!"));
                asyncTaskInfo.setState(TaskState.ERROR);
                AsyncTaskInfo asyncTaskInfo2 = asyncTaskInfo;
                return asyncTaskInfo2;
            }
            fileInfo = this.fileService.tempArea().uploadTemp(fileName, ins);
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            AsyncTaskInfo asyncTaskInfo = new AsyncTaskInfo();
            asyncTaskInfo.setId(taskId);
            asyncTaskInfo.setProcess(Double.valueOf(1.0));
            asyncTaskInfo.setResult("");
            asyncTaskInfo.setDetail((Object)e.getMessage());
            asyncTaskInfo.setState(TaskState.ERROR);
            return asyncTaskInfo;
        }
        JtableContext jtableContext = new JtableContext();
        jtableContext.setDimensionSet(param.getDimensionSet());
        jtableContext.setFormSchemeKey(param.getFormSchemeKey());
        jtableContext.setFormulaSchemeKey(param.getFormulaSchemeKey());
        jtableContext.setTaskKey(param.getTaskKey());
        param.setFileKey(fileInfo.getKey());
        AsyncUploadParam importParam = new AsyncUploadParam();
        importParam.setParam(param);
        importParam.setSuffix(suffix);
        NpRealTimeTaskInfo npRealTimeTaskInfo = new NpRealTimeTaskInfo();
        npRealTimeTaskInfo.setTaskKey(importParam.getParam().getTaskKey());
        npRealTimeTaskInfo.setArgs(SimpleParamConverter.SerializationUtils.serializeToString((Object)importParam));
        npRealTimeTaskInfo.setAbstractRealTimeJob((AbstractRealTimeJob)new UploadAsyncTaskExecutor());
        String asyncTaskId = this.asyncTaskManager.publishTask(npRealTimeTaskInfo);
        StringBuilder actionLogInfo = LoggerUtil.buildMessing(jtableContext, null, "\u5bfc\u5165\u6587\u4ef6", this.runtimeViewController, this.jtableParamService, this.periodEngineService, this.jtableEntityService);
        LogInfo logInfo = new LogInfo();
        logInfo.setActionName("\u5bfc\u5165\u6587\u4ef6");
        logInfo.setLogInfo(actionLogInfo.toString() + "\u6587\u4ef6\u5185\u5bb9\u5927\u5c0f\u4e3a\uff1a" + multipartFile.getSize() + "byte,\u6587\u4ef6\u540d\u79f0\u4e3a\uff1a" + fileName);
        LogHelper.info((String)"\u6570\u636e\u5f55\u5165", (String)logInfo.getActionName(), (String)logInfo.getLogInfo());
        AsyncTaskInfo asyncTaskInfo = new AsyncTaskInfo();
        asyncTaskInfo.setId(asyncTaskId);
        asyncTaskInfo.setUrl("/api/asynctask/simpleQuery?asynTaskID=");
        return asyncTaskInfo;
    }

    @Override
    public AsyncTaskInfo upload(UploadParam param) {
        String fileName = param.getFileNameInfo();
        try {
            PathUtils.validatePathManipulation((String)fileName);
        }
        catch (SecurityContentException e) {
            throw new RuntimeException(e);
        }
        String[] split = fileName.split("\\.");
        String suffix = split[split.length - 1];
        if (suffix.equalsIgnoreCase("et")) {
            suffix = "xlsx";
        }
        String taskId = UUID.randomUUID().toString();
        if (suffix.equalsIgnoreCase("jio") && StringUtils.isEmpty((String)param.getConfigKey())) {
            com.jiuqi.nr.dataentry.bean.ImportResultObject importResultObject = this.getMappingConfig(param);
            AsyncTaskInfo asyncTaskInfo = new AsyncTaskInfo();
            asyncTaskInfo.setId(taskId);
            asyncTaskInfo.setProcess(Double.valueOf(0.0));
            asyncTaskInfo.setResult("");
            asyncTaskInfo.setDetail((Object)importResultObject);
            asyncTaskInfo.setState(TaskState.FINISHED);
            return asyncTaskInfo;
        }
        ObjectInfo objectInfo = null;
        if (!StringUtils.isNotEmpty((String)param.getFilePath())) {
            objectInfo = this.fileUploadOssService.getInfo(param.getFileKeyOfSOss());
            try {
                String dataentryUploadArea = "UPLOADTEMP";
                long fileSizeByte = this.fileService.area(dataentryUploadArea).getAreaConfig().getMaxFileSize();
                double fileSizeM = (double)fileSizeByte / 1048576.0;
                if (fileSizeByte < objectInfo.getSize()) {
                    AsyncTaskInfo asyncTaskInfo = new AsyncTaskInfo();
                    asyncTaskInfo.setId(taskId);
                    asyncTaskInfo.setProcess(Double.valueOf(1.0));
                    asyncTaskInfo.setResult("");
                    asyncTaskInfo.setDetail((Object)("\u6587\u4ef6\u5927\u5c0f\u5927\u4e8e\u914d\u7f6e\u503c" + fileSizeM + "M!!!"));
                    asyncTaskInfo.setState(TaskState.ERROR);
                    return asyncTaskInfo;
                }
            }
            catch (Exception e) {
                logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                AsyncTaskInfo asyncTaskInfo = new AsyncTaskInfo();
                asyncTaskInfo.setId(taskId);
                asyncTaskInfo.setProcess(Double.valueOf(1.0));
                asyncTaskInfo.setResult("");
                asyncTaskInfo.setDetail((Object)e.getMessage());
                asyncTaskInfo.setState(TaskState.ERROR);
                return asyncTaskInfo;
            }
        }
        JtableContext jtableContext = new JtableContext();
        jtableContext.setDimensionSet(param.getDimensionSet());
        jtableContext.setFormSchemeKey(param.getFormSchemeKey());
        jtableContext.setFormulaSchemeKey(param.getFormulaSchemeKey());
        jtableContext.setTaskKey(param.getTaskKey());
        param.setFileKey(param.getFileKeyOfSOss());
        AsyncUploadParam importParam = new AsyncUploadParam();
        importParam.setParam(param);
        importParam.setSuffix(suffix);
        NpRealTimeTaskInfo npRealTimeTaskInfo = new NpRealTimeTaskInfo();
        npRealTimeTaskInfo.setTaskKey(importParam.getParam().getTaskKey());
        npRealTimeTaskInfo.setArgs(SimpleParamConverter.SerializationUtils.serializeToString((Object)importParam));
        npRealTimeTaskInfo.setAbstractRealTimeJob((AbstractRealTimeJob)new UploadAsyncTaskExecutor());
        String asyncTaskId = this.asyncTaskManager.publishTask(npRealTimeTaskInfo);
        StringBuilder actionLogInfo = LoggerUtil.buildMessing(jtableContext, null, "\u5bfc\u5165\u6587\u4ef6", this.runtimeViewController, this.jtableParamService, this.periodEngineService, this.jtableEntityService);
        LogInfo logInfo = new LogInfo();
        logInfo.setActionName("\u5bfc\u5165\u6587\u4ef6");
        logInfo.setLogInfo(actionLogInfo.toString() + "\u6587\u4ef6\u5185\u5bb9\u5927\u5c0f\u4e3a\uff1a" + (objectInfo == null ? "0" : Long.valueOf(objectInfo.getSize())) + "byte,\u6587\u4ef6\u540d\u79f0\u4e3a\uff1a" + fileName);
        LogHelper.info((String)"\u6570\u636e\u5f55\u5165", (String)logInfo.getActionName(), (String)logInfo.getLogInfo());
        AsyncTaskInfo asyncTaskInfo = new AsyncTaskInfo();
        asyncTaskInfo.setId(asyncTaskId);
        asyncTaskInfo.setUrl("/api/asynctask/simpleQuery?asynTaskID=");
        return asyncTaskInfo;
    }

    @Override
    public AsyncTaskInfo uploadFile(UploadParam param, String suffix, File file) {
        String taskId = UUID.randomUUID().toString();
        SimpleAsyncProgressMonitor asyncTaskMonitor = new SimpleAsyncProgressMonitor(taskId, this.cacheObjectResourceRemote);
        this.npApplication.asyncRun(() -> {
            try {
                this.uploadAsync(asyncTaskMonitor, param, suffix, file);
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
        return asyncTaskInfo;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void uploadAsync(AsyncTaskMonitor asyncTaskMonitor, UploadParam param, String suffix, File file) {
        if (asyncTaskMonitor.isCancel()) {
            asyncTaskMonitor.canceled("stop_execute", (Object)"stop_execute");
            LogHelper.info((String)"\u5bfc\u5165", (String)"\u53d6\u6d88\u4efb\u52a1\u6267\u884c", (String)"");
            return;
        }
        try {
            PathUtils.validatePathManipulation((String)file.getPath());
        }
        catch (SecurityContentException e2) {
            throw new RuntimeException(e2);
        }
        String importSuccess = "import_finish_info";
        String importError = "import_fail_info";
        com.jiuqi.nr.dataentry.bean.ImportResultObject resultObject = null;
        ReportImportResultObject resultCsv = null;
        BatchReturnInfo batchReturnInfo = new BatchReturnInfo();
        String dataentryImportTypeObj = this.iTaskOptionController.getValue(param.getTaskKey(), "IMPORT_FILE_TYPE");
        String importType = param.getFileType();
        if ("JIO".equalsIgnoreCase(suffix)) {
            importType = "JIO";
        } else if ("TXT".equalsIgnoreCase(suffix)) {
            importType = "TXT";
        } else if ("CSV".equalsIgnoreCase(suffix)) {
            importType = "CSV";
        } else if ("XLS".equalsIgnoreCase(suffix) || "XLSX".equalsIgnoreCase(suffix)) {
            importType = "EXCEL";
        }
        if (StringUtils.isEmpty((String)dataentryImportTypeObj) || !dataentryImportTypeObj.contains(importType)) {
            resultObject = new com.jiuqi.nr.dataentry.bean.ImportResultObject(false, "\u4efb\u52a1\u9009\u9879\u672a\u5f00\u542f" + importType + "\u5bfc\u5165\u914d\u7f6e\uff01");
            ExcelImportResultItem item = new ExcelImportResultItem();
            String errorCode = ErrorCode.FILEERROR.getErrorCodeMsg();
            if (StringUtils.isNotEmpty((String)this.i18nHelper.getMessage("FILEERROR"))) {
                errorCode = this.i18nHelper.getMessage("FILEERROR");
            }
            Object message = "\u5bfc\u5165\u6587\u4ef6\u7c7b\u578b\u4e0d\u7b26\uff01";
            if (StringUtils.isNotEmpty((String)this.i18nHelper.getMessage("IMPORTFILETYPEERROR"))) {
                message = this.i18nHelper.getMessage("IMPORTFILETYPEERROR");
            }
            item.setErrorCode(errorCode);
            item.setErrorInfo((String)message);
            resultObject.getFails().add(item);
        }
        if ("TXT".equalsIgnoreCase(param.getFileType()) || "TXT".equalsIgnoreCase(suffix) || "CSV".equalsIgnoreCase(param.getFileType()) || "CSV".equalsIgnoreCase(suffix) || "JSON".equalsIgnoreCase(param.getFileType()) || "JSON".equalsIgnoreCase(suffix)) {
            DimensionValueSet dimensionSet = new DimensionValueSet();
            Map<String, DimensionValue> dimSetMap = param.getDimensionSet();
            if (null != dimSetMap) {
                for (String key : dimSetMap.keySet()) {
                    if ("".equals(dimSetMap.get(key).getValue())) continue;
                    dimensionSet.setValue(key, (Object)dimSetMap.get(key).getValue());
                }
            }
            TableContext tableContext = new TableContext(param.getTaskKey(), param.getFormSchemeKey(), null, dimensionSet, OptTypes.FORMSCHEME, ".txt");
            if ("TXT".equalsIgnoreCase(param.getFileType()) || "TXT".equalsIgnoreCase(suffix)) {
                if (param.getSplitMark().size() == 1) {
                    tableContext.setSplit(param.getSplitMark().get(0).replace("\\t", "\t"));
                } else if (param.isMergeSplit()) {
                    StringBuilder sp = new StringBuilder();
                    for (String s : param.getSplitMark()) {
                        sp.append(s.replace("\\t", "\t"));
                    }
                    tableContext.setSplit(sp.toString());
                } else if (param.getSplitMark().size() > 1) {
                    tableContext.setSplit(param.getSplitMark().get(0).replace("\\t", "\t"));
                    ArrayList<String> splitGather = new ArrayList<String>();
                    for (int i = 1; i < param.getSplitMark().size(); ++i) {
                        splitGather.add(param.getSplitMark().get(i).replace("\\t", "\t"));
                    }
                    tableContext.setSplitGather(splitGather);
                }
            } else if ("CSV".equalsIgnoreCase(param.getFileType()) || "CSV".equalsIgnoreCase(suffix)) {
                tableContext.setFileType(".csv");
            } else {
                tableContext.setFileType(".json");
            }
            if (param.isAppending()) {
                tableContext.setFloatImpOpt(0);
            }
            tableContext.setIoQualifier(this.ioQualifier);
            tableContext.setValidEntityExist(true);
            boolean success = true;
            try {
                List skipDataInfos;
                JIOUnitImportResult item;
                ArrayList<JIOUnitImportResult> jioUnitImportResults;
                DimensionCollection buildDimensionCollection = com.jiuqi.nr.data.access.util.DimensionValueSetUtil.buildDimensionCollection(param.getDimensionSet(), (String)tableContext.getFormSchemeKey());
                TextParams params = new TextParams(tableContext.getFormSchemeKey(), null, buildDimensionCollection, tableContext.getFileType().equals(".csv") ? TextType.TEXTTYPE_CSV : TextType.TEXTTYPE_TXT, tableContext.getSplit(), asyncTaskMonitor, null);
                params.setFloatImpOpt(tableContext.getFloatImpOpt());
                if (file.getName().endsWith(".zip") || file.getName().endsWith(".ZIP")) {
                    String destDirPath = file.getParent() + "/" + UUID.randomUUID().toString().replace("-", "");
                    FileUtil.unZip((File)file, (String)destDirPath);
                    params.setFilePath(destDirPath);
                } else {
                    params.setFilePath(file.getAbsolutePath());
                }
                if (param.getSplitMark() != null && param.getSplitMark().size() == 1) {
                    params.setSplit(param.getSplitMark().get(0).replace("\\t", "\t"));
                } else if (param.isMergeSplit()) {
                    StringBuilder sp = new StringBuilder();
                    for (String s : param.getSplitMark()) {
                        sp.append(s.replace("\\t", "\t"));
                    }
                    params.setSplit(sp.toString());
                } else if (param.getSplitMark() != null && param.getSplitMark().size() > 1) {
                    tableContext.setSplit(param.getSplitMark().get(0).replace("\\t", "\t"));
                    ArrayList<String> splitGather = new ArrayList<String>();
                    for (int i = 1; i < param.getSplitMark().size(); ++i) {
                        splitGather.add(param.getSplitMark().get(i).replace("\\t", "\t"));
                    }
                    params.setSplitGather(splitGather);
                }
                CommonParams commonParams = new CommonParams();
                if (("".equals(param.getAutoCacl()) || Objects.equals(Consts.ASYNC_PARAM_AUTOCACL_NULL, param.getAutoCacl())) && this.dataentryOptionsUtil.autoCaclUpload() || "1".equals(param.getAutoCacl())) {
                    UploadAsyncMonitor uploadAsyncMonitor = new UploadAsyncMonitor(asyncTaskMonitor, 0.7, 0.0);
                    commonParams.setMonitor((AsyncTaskMonitor)uploadAsyncMonitor);
                } else {
                    commonParams.setMonitor(asyncTaskMonitor);
                }
                Message uploadTextData = this.impTextService.uploadTextData(params, commonParams);
                if (asyncTaskMonitor.isCancel()) {
                    ImportCancledResult importCancledResult = ((CommonMessage)uploadTextData.getMessage()).getImportCancledResult();
                    importCancledResult.setProgress(asyncTaskMonitor.getLastProgress());
                    ImportFileDataRange dataRange = importCancledResult.getImportFileDataRange();
                    ImportCancledResultParam cancledResultParam = new ImportCancledResultParam(String.valueOf(importCancledResult.getSuccessFiles().size()), dataRange == null ? "\u6587\u4ef6" : dataRange.getTitle());
                    asyncTaskMonitor.canceled(JsonUtil.objectToJson((Object)cancledResultParam), (Object)JsonUtil.objectToJson((Object)importCancledResult));
                    return;
                }
                Map dealFileData = (Map)((CommonMessage)uploadTextData.getMessage()).getDetail();
                List errorDataInfos = (List)dealFileData.get("error_data");
                resultCsv = new ReportImportResultObject();
                resultCsv.setSuccess(true);
                resultCsv.setMessage("\u5bfc\u5165\u6210\u529f");
                resultCsv.setImportType(tableContext.getFileType());
                if (("".equals(param.getAutoCacl()) || Objects.equals(Consts.ASYNC_PARAM_AUTOCACL_NULL, param.getAutoCacl())) && this.dataentryOptionsUtil.autoCaclUpload() || "1".equals(param.getAutoCacl()) && (errorDataInfos == null || errorDataInfos.isEmpty())) {
                    UploadAsyncMonitor uploadAsyncMonitor = new UploadAsyncMonitor(asyncTaskMonitor, 0.3, 0.7);
                    BatchCalculateInfo batchCalculateInfo = new BatchCalculateInfo();
                    batchCalculateInfo.setDimensionSet(param.getDimensionSet());
                    batchCalculateInfo.setFormSchemeKey(param.getFormSchemeKey());
                    batchCalculateInfo.setFormulaSchemeKey(param.getFormulaSchemeKey());
                    batchCalculateInfo.setTaskKey(param.getTaskKey());
                    batchCalculateInfo.setVariableMap(param.getVariableMap());
                    this.jLoggerAspect.log(batchCalculateInfo.getContext(), "\u6570\u636e\u6267\u884c\u81ea\u52a8\u8fd0\u7b97");
                    this.batchCalculateService.batchCalculateForm(batchCalculateInfo, uploadAsyncMonitor);
                }
                if (asyncTaskMonitor.isCancel()) {
                    asyncTaskMonitor.canceled("", (Object)"");
                    return;
                }
                FormSchemeDefine formSchemeDefine = this.runtimeViewController.getFormScheme(param.getFormSchemeKey());
                DimensionValue periodDimensionValue = param.getDimensionSet().get("DATATIME");
                String periodValue = periodDimensionValue.getValue();
                Date[] dates = DataEntryUtil.parseFromPeriod(periodValue);
                Date queryVersionStartDate = null;
                Date queryVersionEndDate = null;
                queryVersionStartDate = com.jiuqi.np.definition.common.Consts.DATE_VERSION_MIN_VALUE;
                queryVersionEndDate = com.jiuqi.np.definition.common.Consts.DATE_VERSION_MAX_VALUE;
                if (dates != null && dates.length >= 2) {
                    queryVersionStartDate = dates[0];
                    queryVersionEndDate = dates[1];
                }
                Set entityKeys = null;
                try {
                    entityKeys = this.iEntityAuthorityService.getCanReadEntityKeys(formSchemeDefine.getDw(), queryVersionStartDate, queryVersionEndDate);
                    resultCsv.setNetUnitNum(entityKeys.size());
                }
                catch (UnauthorizedEntityException e3) {
                    throw new RuntimeException(e3);
                }
                HashSet<String> failedDws = new HashSet<String>();
                if (null != errorDataInfos && !errorDataInfos.isEmpty()) {
                    jioUnitImportResults = new ArrayList<JIOUnitImportResult>();
                    HashMap<String, Integer> map = new HashMap<String, Integer>();
                    for (Object info : errorDataInfos) {
                        ArrayList<JIOFormImportResult> fr = new ArrayList<JIOFormImportResult>();
                        item = new JIOUnitImportResult();
                        item.setMessage(info.getMessage());
                        String unitCode = info.getUnitCode();
                        if (unitCode.contains("|")) {
                            failedDws.add(info.getUnitCode().split("\\|")[0]);
                            item.setUnitCode(info.getUnitCode().split("\\|")[0]);
                            item.setUnitTitle(info.getUnitCode().split("\\|")[1]);
                        } else {
                            failedDws.add(info.getUnitCode());
                            item.setUnitCode(info.getUnitCode());
                            item.setUnitTitle(info.getUnitCode());
                        }
                        JIOFormImportResult jfr = new JIOFormImportResult();
                        jfr.setFormCode(info.getFormCode());
                        jfr.setMessage(info.getMessage());
                        jfr.setFormTitle(info.getFormTitle());
                        jfr.setFormKey(info.getFormKey());
                        fr.add(jfr);
                        item.setFormResults(fr);
                        if (map.containsKey(item.getUnitCode())) {
                            ((JIOUnitImportResult)jioUnitImportResults.get((Integer)map.get(item.getUnitCode()))).getFormResults().add(jfr);
                            continue;
                        }
                        map.put(item.getUnitCode(), jioUnitImportResults.size());
                        jioUnitImportResults.add(item);
                    }
                    success = false;
                    resultCsv.setSuccess(false);
                    resultCsv.setMessage("\u5bfc\u5165\u5931\u8d25");
                    resultCsv.setErrorUnits(jioUnitImportResults);
                }
                if (null != (skipDataInfos = (List)dealFileData.get("skip_data")) && !skipDataInfos.isEmpty()) {
                    jioUnitImportResults = new ArrayList();
                    HashMap<String, Integer> map = new HashMap<String, Integer>();
                    for (ImportInformations info : skipDataInfos) {
                        item = new JIOUnitImportResult();
                        ArrayList<JIOFormImportResult> fr = new ArrayList<JIOFormImportResult>();
                        item.setMessage(info.getMessage());
                        String unitCode = info.getUnitCode();
                        if (unitCode.contains("|")) {
                            item.setUnitCode(info.getUnitCode().split("\\|")[0]);
                            item.setUnitTitle(info.getUnitCode().split("\\|")[1]);
                        } else {
                            item.setUnitCode(info.getUnitCode());
                            item.setUnitTitle(info.getUnitCode());
                        }
                        JIOFormImportResult jfr = new JIOFormImportResult();
                        jfr.setFormCode(info.getFormCode());
                        jfr.setMessage(info.getMessage());
                        jfr.setFormTitle(info.getFormTitle());
                        jfr.setFormKey(info.getFormKey());
                        fr.add(jfr);
                        item.setFormResults(fr);
                        if (map.containsKey(item.getUnitCode())) {
                            ((JIOUnitImportResult)jioUnitImportResults.get((Integer)map.get(item.getUnitCode()))).getFormResults().add(jfr);
                            continue;
                        }
                        map.put(item.getUnitCode(), jioUnitImportResults.size());
                        jioUnitImportResults.add(item);
                    }
                    resultCsv.setSuccessUnits(jioUnitImportResults);
                }
                List allDws = ((CommonMessage)uploadTextData.getMessage()).getSuccessDW();
                resultCsv.setErrorUnitNum(failedDws.size());
                resultCsv.setSuccesssUnitNum(allDws == null ? 0 : (int)allDws.stream().filter(e -> !failedDws.contains(e)).count());
                resultCsv.setImportType(tableContext.getFileType());
                asyncTaskMonitor.progressAndMessage(1.0, "success");
                String objectToJson = JsonUtil.objectToJson((Object)resultCsv);
                if (success) {
                    if (!asyncTaskMonitor.isFinish()) {
                        asyncTaskMonitor.finish(importSuccess, (Object)objectToJson);
                    }
                } else {
                    asyncTaskMonitor.error(importError, null, objectToJson);
                }
                return;
            }
            catch (Exception e4) {
                logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e4.getMessage(), e4);
                resultCsv = new ReportImportResultObject();
                AbstractImportResultItem item = new AbstractImportResultItem();
                item.setErrorInfo(e4.getMessage());
                ArrayList<com.jiuqi.nr.dataentry.bean.ImportResultItem> fails = new ArrayList<com.jiuqi.nr.dataentry.bean.ImportResultItem>();
                fails.add(item);
                resultCsv.setFails(fails);
                this.cacheObjectResourceRemote.create((Object)(asyncTaskMonitor.getTaskId() + "_result"), (Object)resultCsv);
                asyncTaskMonitor.error(importError, (Throwable)e4, e4.getMessage());
                return;
            }
        }
        if (FILETYPEMAP.containsKey(suffix)) {
            IUploadTypeService specificReal = this.uploadTypeServiceMap.get(FILETYPEMAP.get(suffix));
            resultObject = this.beforeImport(file, param);
            if (resultObject == null) {
                if (suffix.equalsIgnoreCase("jio")) {
                    UploadAsyncMonitor uploadAsyncMonitor = new UploadAsyncMonitor(asyncTaskMonitor, 0.95, 0.0);
                    if (param.getVariableMap().containsKey("jioNeedSelectImport") && ((Boolean)param.getVariableMap().get("jioNeedSelectImport")).booleanValue()) {
                        IRepeatImportParam iRepeatImportParam = new IRepeatImportParam();
                        List unitCodes = (List)param.getVariableMap().get("unitCodes");
                        ArrayList<IRepeatEntityNode> entityNodes = new ArrayList<IRepeatEntityNode>();
                        if (unitCodes.size() > 0) {
                            for (String unitCode : unitCodes) {
                                IRepeatEntityNode iRepeatEntityNode = new IRepeatEntityNode();
                                iRepeatEntityNode.setSingleZdm(unitCode);
                                iRepeatEntityNode.setRepeatMode(1);
                                entityNodes.add(iRepeatEntityNode);
                            }
                        }
                        ArrayList<IRepeatFormNode> formNodes = new ArrayList<IRepeatFormNode>();
                        List formCodes = (List)param.getVariableMap().get("formCodes");
                        if (formCodes.size() > 0) {
                            for (String formCode : formCodes) {
                                IRepeatFormNode iRepeatFormNode = new IRepeatFormNode();
                                iRepeatFormNode.setFormKey(formCode);
                                iRepeatFormNode.setRepeatMode(1);
                                formNodes.add(iRepeatFormNode);
                            }
                        }
                        iRepeatImportParam.setEntityNodes(entityNodes);
                        iRepeatImportParam.setFormNodes(formNodes);
                        param.getVariableMap().put("jioSelectImportParm", JsonUtil.objectToJson((Object)iRepeatImportParam));
                        param.getVariableMap().put("jioSelectImportParmSourceType", "1");
                    }
                    ReportImportResultObject reportImportResultObject = (ReportImportResultObject)specificReal.upload(file, param, uploadAsyncMonitor);
                    if (asyncTaskMonitor.isCancel()) {
                        if (!asyncTaskMonitor.isFinish()) {
                            asyncTaskMonitor.canceled("", (Object)"");
                        }
                        return;
                    }
                    reportImportResultObject.getErrorUnits().sort((o1, o2) -> {
                        if (o1.getMessage() == null && o2.getMessage() == null) {
                            return 0;
                        }
                        if (o1.getMessage() == null) {
                            return 1;
                        }
                        if (o2.getMessage() == null) {
                            return -1;
                        }
                        return o1.getMessage().compareTo(o2.getMessage());
                    });
                    resultObject = reportImportResultObject;
                } else {
                    com.jiuqi.nr.data.excel.param.UploadParam pramsss = new com.jiuqi.nr.data.excel.param.UploadParam();
                    pramsss.setAppending(false);
                    pramsss.setFilePath(file.getAbsolutePath());
                    DimensionValueSet dimensionValueSet = DimensionValueSetUtil.getDimensionValueSet(param.getDimensionSet());
                    DimensionCombinationBuilder builder = new DimensionCombinationBuilder(dimensionValueSet);
                    pramsss.setDimensionSet(builder.getCombination());
                    pramsss.setFormSchemeKey(param.getFormSchemeKey());
                    pramsss.setAppending(param.isAppending());
                    CommonParams commonParams = new CommonParams();
                    if (("".equals(param.getAutoCacl()) || Consts.ASYNC_PARAM_AUTOCACL_NULL == param.getAutoCacl()) && this.dataentryOptionsUtil.autoCaclUpload() || "1".equals(param.getAutoCacl())) {
                        commonParams.setMonitor((AsyncTaskMonitor)new UploadAsyncMonitor(asyncTaskMonitor, 0.7, 0.0));
                    } else {
                        commonParams.setMonitor(asyncTaskMonitor);
                    }
                    ArrayList<com.jiuqi.nr.dataentry.bean.ImportResultItem> failss = new ArrayList<com.jiuqi.nr.dataentry.bean.ImportResultItem>();
                    try {
                        List relationDimensions = null;
                        Message message = this.uploadExcelService.upload(pramsss, commonParams);
                        if (asyncTaskMonitor.isCancel()) {
                            ImportCancledResult importCancledResult = ((CommonMessage)message.getMessage()).getImportCancledResult();
                            importCancledResult.setProgress(asyncTaskMonitor.getLastProgress());
                            ImportFileDataRange dataRange = importCancledResult.getImportFileDataRange();
                            ImportCancledResultParam cancledResultParam = new ImportCancledResultParam(String.valueOf(importCancledResult.getSuccessFiles().size()), dataRange == null ? "\u6587\u4ef6" : dataRange.getTitle());
                            asyncTaskMonitor.canceled(JsonUtil.objectToJson((Object)cancledResultParam), (Object)JsonUtil.objectToJson((Object)importCancledResult));
                            return;
                        }
                        resultObject = new com.jiuqi.nr.dataentry.bean.ImportResultObject();
                        if (message != null) {
                            ImportResultObject o = (ImportResultObject)((CommonMessage)message.getMessage()).getDetail();
                            if (o != null) {
                                relationDimensions = o.getRelationDimensions();
                                List ol = o.getFails();
                                if (ol != null && !ol.isEmpty()) {
                                    for (ImportResultItem item : ol) {
                                        com.jiuqi.nr.data.excel.param.bean.ExcelImportResultItem i = (com.jiuqi.nr.data.excel.param.bean.ExcelImportResultItem)item;
                                        ExcelImportResultItem e5 = new ExcelImportResultItem(i.getFormName(), i.getSheetName(), i.getFileName(), i.getErrorInfo(), i.getErrorCode());
                                        failss.add(e5);
                                    }
                                }
                                resultObject.setFails(failss);
                                resultObject.setFmdmed(o.isFmdmed());
                                resultObject.setImportType(o.getImportType());
                                resultObject.setLocation(o.getLocation());
                                resultObject.setMessage(o.getMessage());
                                resultObject.setSuccess(o.isSuccess());
                                if (o.getLocation() != null) {
                                    File excelfile = new File(FilenameUtils.normalize(o.getLocation()));
                                    ObjectInfo objectInfo = null;
                                    try (FileInputStream uploadInputStream = new FileInputStream(excelfile);){
                                        objectInfo = this.fileUploadOssService.uploadFileStreamToTemp(excelfile.getName(), (InputStream)uploadInputStream);
                                        resultObject.setLocation(objectInfo.getKey());
                                    }
                                    catch (Exception e6) {
                                        logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e6.getMessage(), e6);
                                    }
                                    finally {
                                        if (null != excelfile && !excelfile.delete()) {
                                            logger.info("\u6587\u4ef6\u5220\u9664\u5931\u8d25");
                                        }
                                    }
                                }
                            } else {
                                resultObject.setSuccess(true);
                            }
                        } else {
                            resultObject.setSuccess(true);
                        }
                        if (("".equals(param.getAutoCacl()) || Consts.ASYNC_PARAM_AUTOCACL_NULL == param.getAutoCacl()) && this.dataentryOptionsUtil.autoCaclUpload() || "1".equals(param.getAutoCacl())) {
                            UploadAsyncMonitor uploadAsyncMonitor = new UploadAsyncMonitor(asyncTaskMonitor, 0.3, 0.7);
                            Map<String, DimensionValue> mergeDimension = null;
                            mergeDimension = relationDimensions != null ? DimensionValueSetUtil.mergeDimension((List)relationDimensions) : param.getDimensionSet();
                            BatchCalculateInfo batchCalculateInfo = new BatchCalculateInfo();
                            batchCalculateInfo.setDimensionSet(mergeDimension);
                            batchCalculateInfo.setFormSchemeKey(param.getFormSchemeKey());
                            batchCalculateInfo.setFormulaSchemeKey(param.getFormulaSchemeKey());
                            batchCalculateInfo.setTaskKey(param.getTaskKey());
                            batchCalculateInfo.setVariableMap(param.getVariableMap());
                            this.jLoggerAspect.log(batchCalculateInfo.getContext(), "\u6570\u636e\u6267\u884c\u81ea\u52a8\u8fd0\u7b97");
                            this.batchCalculateService.batchCalculateForm(batchCalculateInfo, uploadAsyncMonitor);
                        }
                    }
                    catch (Exception e1) {
                        logger.error(e1.getMessage());
                    }
                }
            }
        } else {
            resultObject = new com.jiuqi.nr.dataentry.bean.ImportResultObject(false, "\u672a\u77e5\u9519\u8bef");
        }
        this.cacheObjectResourceRemote.create((Object)(asyncTaskMonitor.getTaskId() + "_result"), (Object)resultObject);
        String objectToJson = JsonUtil.objectToJson((Object)resultObject);
        if (resultObject.isSuccess()) {
            if (!asyncTaskMonitor.isFinish()) {
                asyncTaskMonitor.finish(importSuccess, (Object)resultObject);
            }
        } else {
            asyncTaskMonitor.error(importError, null, objectToJson);
        }
    }

    private com.jiuqi.nr.dataentry.bean.ImportResultObject beforeImport(File file, UploadParam param) {
        String fileName = file.getName().substring(0, file.getName().lastIndexOf("."));
        if (file.getName().endsWith(".xls") || file.getName().endsWith(".xlsx") || file.getName().endsWith(".et")) {
            FormSchemeDefine formSchemeDefine = this.controller.getFormScheme(param.getFormSchemeKey());
            EntityViewData targetEntityInfo = this.jtableParamService.getDwEntity(formSchemeDefine.getKey());
            if (param.getDimensionSet().get(targetEntityInfo.getDimensionName()).getValue().equals("") || param.getDimensionSet().get(targetEntityInfo.getDimensionName()).getValue() == null) {
                EntityViewDefine entityViewDefine = targetEntityInfo.getEntityViewDefine();
                IEntityDefine iEntityDefine = this.iEntityMetaService.queryEntity(entityViewDefine.getEntityId());
                IEntityTable entityTable = null;
                try {
                    IEntityQuery iEntityQuery = this.iEntityDataService.newEntityQuery();
                    iEntityQuery.setEntityView(entityViewDefine);
                    entityTable = this.dataEntityFullService.executeEntityReader(iEntityQuery, new ExecutorContext(this.iDataDefinitionRuntimeController), entityViewDefine, formSchemeDefine.getKey()).getEntityTable();
                    List iEntityRows = entityTable.getAllRows();
                    ArrayList<IEntityRow> entityList = new ArrayList<IEntityRow>();
                    for (IEntityRow iEntityRow : iEntityRows) {
                        if (!fileName.equals(iEntityRow.getTitle()) && !fileName.equals(iEntityRow.getCode())) continue;
                        entityList.add(iEntityRow);
                    }
                    String sysSeparator = this.exportExcelNameService.getSysSeparator();
                    if (entityList.size() == 0 && fileName.contains(sysSeparator)) {
                        String[] fileNameArray;
                        for (String fileNameInfo : fileNameArray = fileName.split(sysSeparator)) {
                            for (IEntityRow iEntityRow : iEntityRows) {
                                if (!fileNameInfo.equals(iEntityRow.getTitle()) && !fileNameInfo.equals(iEntityRow.getCode())) continue;
                                entityList.add(iEntityRow);
                            }
                        }
                        if (entityList.size() > 1) {
                            entityList.clear();
                            for (String fileNameInfo : fileNameArray) {
                                for (IEntityRow iEntityRow : iEntityRows) {
                                    if (!fileNameInfo.equals(iEntityRow.getCode())) continue;
                                    entityList.add(iEntityRow);
                                }
                            }
                        }
                    }
                    if (entityList.size() != 1) {
                        ExcelImportResultItem excelImportResultItem = new ExcelImportResultItem();
                        excelImportResultItem.setFileName(fileName);
                        String errorCode = ErrorCode.FILEERROR.getErrorCodeMsg();
                        if (StringUtils.isNotEmpty((String)this.i18nHelper.getMessage("FILEERROR"))) {
                            errorCode = this.i18nHelper.getMessage("FILEERROR");
                        }
                        String message = "\u6587\u4ef6\u540d\u79f0\u672a\u5339\u914d\u5230\u5355\u4f4d\u6216\u5355\u4f4d\u4e0d\u552f\u4e00";
                        if (StringUtils.isNotEmpty((String)this.i18nHelper.getMessage("FILENAMEUNITCODEERROR"))) {
                            message = this.i18nHelper.getMessage("FILENAMEUNITCODEERROR");
                        }
                        excelImportResultItem.setErrorCode(errorCode);
                        excelImportResultItem.setErrorInfo(message);
                        com.jiuqi.nr.dataentry.bean.ImportResultObject importResultObject = new com.jiuqi.nr.dataentry.bean.ImportResultObject(false, message);
                        importResultObject.getFails().add(excelImportResultItem);
                        return importResultObject;
                    }
                    param.getDimensionSet().get(targetEntityInfo.getDimensionName()).setValue(((IEntityRow)entityList.get(0)).getEntityKeyData());
                }
                catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
        return null;
    }

    @Override
    public com.jiuqi.nr.dataentry.bean.ImportResultObject getMappingConfig(MultipartFile multipartFile, UploadParam param) {
        com.jiuqi.nr.dataentry.bean.ImportResultObject resultObject = null;
        String fileName = multipartFile.getOriginalFilename();
        try {
            PathUtils.validatePathManipulation((String)fileName);
        }
        catch (SecurityContentException e) {
            throw new RuntimeException(e);
        }
        File sourcefile = new File(fileName);
        fileName = sourcefile.getName();
        String[] split = fileName.split("\\.");
        String suffix = split[split.length - 1];
        SimpleDateFormat sfDate = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String fileLocation = sfDate.format(new Date()) + OrderGenerator.newOrder();
        String path = BatchExportConsts.UPLOADDIR + BatchExportConsts.SEPARATOR + fileLocation + BatchExportConsts.SEPARATOR;
        try {
            PathUtils.validatePathManipulation((String)path);
        }
        catch (SecurityContentException e) {
            throw new RuntimeException(e);
        }
        File pathFile = new File(path);
        if (!pathFile.exists()) {
            pathFile.mkdirs();
        }
        File file = new File(pathFile.getPath() + BatchExportConsts.SEPARATOR + fileName);
        try {
            multipartFile.transferTo(file);
        }
        catch (IllegalStateException e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
        catch (IOException e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
        param.setFileLocation(fileLocation);
        JtableContext jtableContext = new JtableContext();
        jtableContext.setDimensionSet(param.getDimensionSet());
        jtableContext.setFormSchemeKey(param.getFormSchemeKey());
        jtableContext.setFormulaSchemeKey(param.getFormulaSchemeKey());
        jtableContext.setTaskKey(param.getTaskKey());
        String taskId = UUID.randomUUID().toString();
        SimpleAsyncProgressMonitor asyncTaskMonitor = new SimpleAsyncProgressMonitor(taskId, this.cacheObjectResourceRemote);
        String finalFileName = fileName;
        if (FILETYPEMAP.containsKey(suffix)) {
            IUploadTypeService specificReal = this.uploadTypeServiceMap.get(FILETYPEMAP.get(suffix));
            resultObject = this.beforeImport(file, param);
            if (resultObject == null) {
                resultObject = specificReal.upload(file, param, asyncTaskMonitor);
            }
        } else {
            resultObject = new com.jiuqi.nr.dataentry.bean.ImportResultObject(false, "\u672a\u77e5\u9519\u8bef");
        }
        try (FileInputStream ins = new FileInputStream(file);){
            FileInfo fileInfo = this.fileService.tempArea().uploadTemp(fileName, (InputStream)ins);
            resultObject.setFileKey(fileInfo.getKey());
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
        return resultObject;
    }

    private void addPathsToNodes(List<IRepeatEntityNode> nodes) {
        HashMap<String, IRepeatEntityNode> nodeMap = new HashMap<String, IRepeatEntityNode>();
        for (IRepeatEntityNode node : nodes) {
            nodeMap.put(node.getSingleZdm(), node);
        }
        for (IRepeatEntityNode node : nodes) {
            node.setPath(this.calculatePath(node, nodeMap));
        }
    }

    private List<String> calculatePath(IRepeatEntityNode node, Map<String, IRepeatEntityNode> nodeMap) {
        IRepeatEntityNode parent;
        ArrayList<String> path = new ArrayList<String>();
        path.add(node.getSingleZdm());
        String currentParentCode = node.getSingleParent();
        while (currentParentCode != null && !currentParentCode.isEmpty() && (parent = nodeMap.get(currentParentCode)) != null) {
            path.add(0, parent.getSingleZdm());
            currentParentCode = parent.getSingleParent();
        }
        return path;
    }

    @Override
    public boolean singleImportCheck(UploadParam param) {
        String[] fileNameArray;
        String importFlag = this.iNvwaSystemOptionService.get("nr-data-entry-group", "SINGLE_IMPORT_CHECK");
        if (importFlag.equals("0")) {
            return true;
        }
        boolean checkFlag = false;
        FormSchemeDefine formScheme = null;
        try {
            formScheme = this.controller.getFormScheme(param.getFormSchemeKey());
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            throw new NotFoundFormSchemeException(ExceptionCodeCost.NOTFOUND_FORMSHCEME_BYKEY, new String[]{param.getFormSchemeKey()});
        }
        IEntityDefine queryEntity = this.iEntityMetaService.queryEntity(formScheme.getDw());
        String unitName = "";
        String value = param.getDimensionSet().get(queryEntity.getDimensionName()).getValue();
        List<IEntityRow> entityRows = this.getEntityDataList(param.getFormSchemeKey(), param.getDimensionSet());
        List entityDatas = entityRows.stream().filter(t -> t.getCode().equals(value)).collect(Collectors.toList());
        if (entityDatas.size() > 0) {
            unitName = ((IEntityRow)entityDatas.get(0)).getTitle();
        }
        String sysSeparator = this.exportExcelNameService.getSysSeparator();
        for (String fileNameInfo : fileNameArray = param.getFileNameInfo().substring(0, param.getFileNameInfo().lastIndexOf(".")).split(sysSeparator)) {
            if (!fileNameInfo.equals(value) && !fileNameInfo.equals(unitName)) continue;
            checkFlag = true;
        }
        return checkFlag;
    }

    private List<IEntityRow> getEntityDataList(String formSchemeKey, Map<String, DimensionValue> dimensionValueSet) {
        IEntityTable iEntityTable = null;
        FormSchemeDefine formSchemeDefine = this.controller.getFormScheme(formSchemeKey);
        try {
            String entityId = this.dataAccesslUtil.contextEntityId(formSchemeDefine.getDw());
            EntityViewData entityViewData = this.jtableParamService.getEntity(entityId);
            IEntityQuery iEntityQuery = this.entityDataService.newEntityQuery();
            iEntityQuery.setMasterKeys(DimensionValueSetUtil.getDimensionValueSet(dimensionValueSet));
            EntityViewDefine entityViewDefine = entityViewData.getEntityViewDefine();
            iEntityQuery.setEntityView(entityViewDefine);
            iEntityQuery.setAuthorityOperations(AuthorityType.Read);
            iEntityTable = this.dataEntityFullService.executeEntityReader(iEntityQuery, new ExecutorContext(this.dataDefinitionRuntimeController), entityViewDefine, formSchemeKey).getEntityTable();
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
        if (iEntityTable != null) {
            return iEntityTable.getAllRows();
        }
        return new ArrayList<IEntityRow>();
    }

    @Override
    public com.jiuqi.nr.dataentry.bean.ImportResultObject getMappingConfig(UploadParam param) {
        com.jiuqi.nr.dataentry.bean.ImportResultObject resultObject = null;
        String fileName = param.getFileNameInfo();
        File sourcefile = new File(fileName);
        fileName = sourcefile.getName();
        String[] split = fileName.split("\\.");
        String suffix = split[split.length - 1];
        SimpleDateFormat sfDate = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String fileLocation = sfDate.format(new Date()) + OrderGenerator.newOrder();
        String path = BatchExportConsts.UPLOADDIR + BatchExportConsts.SEPARATOR + fileLocation + BatchExportConsts.SEPARATOR;
        File pathFile = new File(path);
        if (!pathFile.exists()) {
            pathFile.mkdirs();
        }
        File file = new File(pathFile.getPath() + BatchExportConsts.SEPARATOR + fileName);
        try (FileOutputStream fileOutputStream = new FileOutputStream(file);){
            this.fileUploadOssService.downloadFileFormTemp(param.getFileKeyOfSOss(), (OutputStream)fileOutputStream);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        param.setFileLocation(fileLocation);
        JtableContext jtableContext = new JtableContext();
        jtableContext.setDimensionSet(param.getDimensionSet());
        jtableContext.setFormSchemeKey(param.getFormSchemeKey());
        jtableContext.setFormulaSchemeKey(param.getFormulaSchemeKey());
        jtableContext.setTaskKey(param.getTaskKey());
        String taskId = UUID.randomUUID().toString();
        SimpleAsyncProgressMonitor asyncTaskMonitor = new SimpleAsyncProgressMonitor(taskId, this.cacheObjectResourceRemote);
        String finalFileName = fileName;
        if (FILETYPEMAP.containsKey(suffix)) {
            IUploadTypeService specificReal = this.uploadTypeServiceMap.get(FILETYPEMAP.get(suffix));
            resultObject = this.beforeImport(file, param);
            if (resultObject == null) {
                resultObject = specificReal.upload(file, param, asyncTaskMonitor);
            }
        } else {
            resultObject = new com.jiuqi.nr.dataentry.bean.ImportResultObject(false, "\u672a\u77e5\u9519\u8bef");
        }
        if (!file.delete()) {
            logger.info("\u6587\u4ef6\u5220\u9664\u5931\u8d25");
        }
        if (!pathFile.delete()) {
            logger.info("\u6587\u4ef6\u5220\u9664\u5931\u8d25");
        }
        return resultObject;
    }

    @Override
    public com.jiuqi.nr.dataentry.bean.ImportResultObject importResult(String id) {
        Object object = this.cacheObjectResourceRemote.find((Object)(id + "_result"));
        if (null != object) {
            return (com.jiuqi.nr.dataentry.bean.ImportResultObject)object;
        }
        return null;
    }

    static {
        FILETYPEMAP.put("xls", "upload_type_excel");
        FILETYPEMAP.put("zip", "upload_type_zip");
        FILETYPEMAP.put("jio", "upload_type_jio");
        FILETYPEMAP.put("xlsx", "upload_type_excel");
        FILETYPEMAP.put("et", "upload_type_excel");
    }

    private static class CaseInsensitiveMap<V>
    extends HashMap<String, V> {
        private static final long serialVersionUID = 1L;

        private CaseInsensitiveMap() {
        }

        @Override
        public V get(Object key) {
            return super.get(StringUtils.upperCase((String)((String)key)));
        }

        @Override
        public V put(String key, V value) {
            return super.put(StringUtils.upperCase((String)key), value);
        }

        @Override
        public boolean containsKey(Object key) {
            return super.containsKey(StringUtils.upperCase((String)((String)key)));
        }

        @Override
        public V remove(Object key) {
            return super.remove(StringUtils.upperCase((String)((String)key)));
        }
    }
}

