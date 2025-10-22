/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.oss.ObjectInfo
 *  com.jiuqi.bi.oss.ObjectStorageException
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.bi.util.zip.ZipEntry
 *  com.jiuqi.bi.util.zip.ZipOutputStream
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.cache.internal.redis.IRedisLock
 *  com.jiuqi.np.dataengine.IDataAccessProvider
 *  com.jiuqi.np.dataengine.QueryEnvironment
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IDataQuery
 *  com.jiuqi.np.dataengine.intf.IDataTable
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 *  com.jiuqi.np.definition.common.FieldType
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.controller.SubDatabaseTableNamesProvider
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.nr.common.exception.NotFoundTableDefineException
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.common.util.DataEngineAdapter
 *  com.jiuqi.nr.common.util.DimensionChanger
 *  com.jiuqi.nr.data.access.api.IStateSecretLevelService
 *  com.jiuqi.nr.data.access.param.IAccessResult
 *  com.jiuqi.nr.data.access.param.IBatchAccessResult
 *  com.jiuqi.nr.data.access.param.IBatchZBAccessResult
 *  com.jiuqi.nr.data.access.param.SecretLevel
 *  com.jiuqi.nr.data.access.service.IDataAccessService
 *  com.jiuqi.nr.data.access.service.IDataAccessServiceProvider
 *  com.jiuqi.nr.data.access.util.DimCollectionBuildUtil
 *  com.jiuqi.nr.data.access.util.DimensionValueSetUtil
 *  com.jiuqi.nr.datascheme.api.DataScheme
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationBuilder
 *  com.jiuqi.nr.dataservice.core.log.DataServiceLoggerFactory
 *  com.jiuqi.nr.dataservice.core.log.LogDimensionCollection
 *  com.jiuqi.nr.definition.common.DataRegionKind
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.fileupload.FileUploadReturnInfo
 *  com.jiuqi.nr.fileupload.service.CheckUploadFileService
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.nr.sensitive.bean.daoObject.SensitiveWordDaoObject
 *  com.jiuqi.nr.sensitive.service.CheckSensitiveWordService
 *  com.jiuqi.nvwa.dataengine.INvwaDataAccess
 *  com.jiuqi.nvwa.dataengine.INvwaDataAccessProvider
 *  com.jiuqi.nvwa.dataengine.INvwaDataRow
 *  com.jiuqi.nvwa.dataengine.INvwaDataSet
 *  com.jiuqi.nvwa.dataengine.INvwaDataUpdator
 *  com.jiuqi.nvwa.dataengine.INvwaUpdatableDataAccess
 *  com.jiuqi.nvwa.dataengine.INvwaUpdatableDataSet
 *  com.jiuqi.nvwa.dataengine.common.DataAccessContext
 *  com.jiuqi.nvwa.dataengine.model.NvwaQueryColumn
 *  com.jiuqi.nvwa.dataengine.model.NvwaQueryModel
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  javax.annotation.Resource
 *  net.coobird.thumbnailator.Thumbnails
 *  org.jetbrains.annotations.NotNull
 */
package com.jiuqi.nr.attachment.service.impl;

import com.jiuqi.bi.oss.ObjectInfo;
import com.jiuqi.bi.oss.ObjectStorageException;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.bi.util.zip.ZipEntry;
import com.jiuqi.bi.util.zip.ZipOutputStream;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.cache.internal.redis.IRedisLock;
import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.dataengine.QueryEnvironment;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IDataQuery;
import com.jiuqi.np.dataengine.intf.IDataTable;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.definition.common.FieldType;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.controller.SubDatabaseTableNamesProvider;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.nr.attachment.constant.PromptConsts;
import com.jiuqi.nr.attachment.exception.FieldNameIsSensitiveWordException;
import com.jiuqi.nr.attachment.exception.FileException;
import com.jiuqi.nr.attachment.exception.FileTypeErrorException;
import com.jiuqi.nr.attachment.input.AombstoneFileInfo;
import com.jiuqi.nr.attachment.input.AttachmentQueryByFieldInfo;
import com.jiuqi.nr.attachment.input.AttachmentQueryInfo;
import com.jiuqi.nr.attachment.input.BatchDeleteFileInfo;
import com.jiuqi.nr.attachment.input.ChangeFileSecretInfo;
import com.jiuqi.nr.attachment.input.CommonParamsDTO;
import com.jiuqi.nr.attachment.input.DeleteMarkFileInfo;
import com.jiuqi.nr.attachment.input.DownLoadFileInfo;
import com.jiuqi.nr.attachment.input.FileRelateGroupInfo;
import com.jiuqi.nr.attachment.input.FileUploadByGroupKeyContext;
import com.jiuqi.nr.attachment.input.FileUploadContext;
import com.jiuqi.nr.attachment.input.FileUploadInfo;
import com.jiuqi.nr.attachment.listener.IFileListener;
import com.jiuqi.nr.attachment.listener.IFileListenerProvider;
import com.jiuqi.nr.attachment.listener.param.FileBatchDelEvent;
import com.jiuqi.nr.attachment.listener.param.FileDelEvent;
import com.jiuqi.nr.attachment.listener.param.FileUploadEvent;
import com.jiuqi.nr.attachment.message.FileInfo;
import com.jiuqi.nr.attachment.message.RowDataInfo;
import com.jiuqi.nr.attachment.output.ReferencesInfo;
import com.jiuqi.nr.attachment.output.RowDataValues;
import com.jiuqi.nr.attachment.provider.param.FileBucketNameParam;
import com.jiuqi.nr.attachment.service.FileBatchOperationService;
import com.jiuqi.nr.attachment.service.FileOperationService;
import com.jiuqi.nr.attachment.service.FilePoolService;
import com.jiuqi.nr.attachment.service.impl.FileBatchOperationServiceImpl;
import com.jiuqi.nr.attachment.tools.AttachmentFileAreaService;
import com.jiuqi.nr.attachment.utils.FileOperationUtils;
import com.jiuqi.nr.attachment.utils.LogHellperUtil;
import com.jiuqi.nr.common.exception.NotFoundTableDefineException;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.common.util.DataEngineAdapter;
import com.jiuqi.nr.common.util.DimensionChanger;
import com.jiuqi.nr.data.access.api.IStateSecretLevelService;
import com.jiuqi.nr.data.access.param.IAccessResult;
import com.jiuqi.nr.data.access.param.IBatchAccessResult;
import com.jiuqi.nr.data.access.param.IBatchZBAccessResult;
import com.jiuqi.nr.data.access.param.SecretLevel;
import com.jiuqi.nr.data.access.service.IDataAccessService;
import com.jiuqi.nr.data.access.service.IDataAccessServiceProvider;
import com.jiuqi.nr.data.access.util.DimCollectionBuildUtil;
import com.jiuqi.nr.data.access.util.DimensionValueSetUtil;
import com.jiuqi.nr.datascheme.api.DataScheme;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationBuilder;
import com.jiuqi.nr.dataservice.core.log.DataServiceLoggerFactory;
import com.jiuqi.nr.dataservice.core.log.LogDimensionCollection;
import com.jiuqi.nr.definition.common.DataRegionKind;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.fileupload.FileUploadReturnInfo;
import com.jiuqi.nr.fileupload.service.CheckUploadFileService;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nr.sensitive.bean.daoObject.SensitiveWordDaoObject;
import com.jiuqi.nr.sensitive.service.CheckSensitiveWordService;
import com.jiuqi.nvwa.dataengine.INvwaDataAccess;
import com.jiuqi.nvwa.dataengine.INvwaDataAccessProvider;
import com.jiuqi.nvwa.dataengine.INvwaDataRow;
import com.jiuqi.nvwa.dataengine.INvwaDataSet;
import com.jiuqi.nvwa.dataengine.INvwaDataUpdator;
import com.jiuqi.nvwa.dataengine.INvwaUpdatableDataAccess;
import com.jiuqi.nvwa.dataengine.INvwaUpdatableDataSet;
import com.jiuqi.nvwa.dataengine.common.DataAccessContext;
import com.jiuqi.nvwa.dataengine.model.NvwaQueryColumn;
import com.jiuqi.nvwa.dataengine.model.NvwaQueryModel;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import net.coobird.thumbnailator.Thumbnails;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FileOperationServiceImpl
implements FileOperationService {
    private static final Logger logger = LoggerFactory.getLogger(FileOperationServiceImpl.class);
    @Autowired
    private AttachmentFileAreaService attachmentFileAreaService;
    @Autowired
    private IRunTimeViewController iRunTimeViewController;
    @Autowired
    private IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    @Resource
    private IEntityViewRunTimeController entityViewRunTimeController;
    @Resource
    private IDataAccessProvider dataAccessProvider;
    @Autowired
    private CheckSensitiveWordService checkSensitiveWordService;
    @Autowired
    private IStateSecretLevelService stateSecretLevelService;
    @Autowired
    private IDataAccessServiceProvider iDataAccessServiceProvider;
    @Autowired
    private FilePoolService filePoolService;
    @Autowired
    private DataModelService dataModelService;
    @Autowired
    private INvwaDataAccessProvider iNvwaDataAccessProvider;
    @Autowired
    private IRuntimeDataSchemeService runtimeDataSchemeService;
    @Autowired
    private DataServiceLoggerFactory dataServiceLoggerFactory;
    @Autowired
    private PeriodEngineService periodEngineService;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private CheckUploadFileService checkUploadFileService;
    @Autowired
    private SubDatabaseTableNamesProvider subTableNamesProvider;
    @Autowired
    private DataEngineAdapter dataEngineAdapter;
    @Autowired
    private DimCollectionBuildUtil dimCollectionBuildUtil;
    @Autowired(required=false)
    private List<IFileListenerProvider> fileListenerProviders;
    @Autowired
    private IRedisLock loca;
    public static final String LOCAK_NAME = "delete_mark_file_";
    private static final String MOUDLE = "\u9644\u4ef6\u670d\u52a1";
    private final Map<DimensionCombination, FileBatchOperationService> fileBatchOperationServiceMap = new LinkedHashMap<DimensionCombination, FileBatchOperationService>();

    @Override
    public String uploadFiles(FileUploadContext fileUploadContext) {
        List<Object> groupKeys = null;
        if (StringUtils.isEmpty((String)fileUploadContext.getGroupKey())) {
            groupKeys = this.getGroupKeys(fileUploadContext.getFormSchemeKey(), fileUploadContext.getDimensionCombination().toDimensionValueSet(), fileUploadContext.getFormKey(), fileUploadContext.getFieldKey());
        } else {
            groupKeys = new ArrayList<String>();
            groupKeys.add(fileUploadContext.getGroupKey());
        }
        String groupKey = "";
        groupKey = groupKeys.isEmpty() || StringUtils.isEmpty((String)((String)groupKeys.get(0))) ? UUID.randomUUID().toString() : (String)groupKeys.get(0);
        FileUploadByGroupKeyContext fileUploadByGroupKeyContext = new FileUploadByGroupKeyContext(fileUploadContext.getFieldKey(), groupKey, fileUploadContext.getFileUploadInfos(), fileUploadContext.getDataSchemeKey(), fileUploadContext.getTaskKey(), fileUploadContext.getDimensionCombination(), fileUploadContext.getFormSchemeKey(), fileUploadContext.getFormKey());
        this.uploadFilesByGroupKey(fileUploadByGroupKeyContext);
        return groupKey;
    }

    @Override
    public void uploadFilesByGroupKey(FileUploadByGroupKeyContext fileUploadByGroupKeyContext) {
        String msg;
        DimensionValueSet dimension = fileUploadByGroupKeyContext.getDimensionCombination().toDimensionValueSet();
        TaskDefine taskDefine = this.iRunTimeViewController.queryTaskDefine(fileUploadByGroupKeyContext.getTaskKey());
        String dwDimensionName = this.entityMetaService.getDimensionName(taskDefine.getDw());
        IPeriodEntityAdapter periodAdapter = this.periodEngineService.getPeriodAdapter();
        String periodDimensionName = periodAdapter.getPeriodDimensionName();
        String targetKey = String.valueOf(dimension.getValue(dwDimensionName));
        String periodCode = String.valueOf(dimension.getValue(periodDimensionName));
        LogDimensionCollection logDimensionCollection = new LogDimensionCollection();
        logDimensionCollection.setDw(taskDefine.getDw(), new String[]{targetKey});
        logDimensionCollection.setPeriod(taskDefine.getDateTime(), periodCode);
        LogHellperUtil logHellperUtil = new LogHellperUtil(this.dataServiceLoggerFactory, MOUDLE);
        String groupKey = fileUploadByGroupKeyContext.getGroupKey();
        FileBucketNameParam param = new FileBucketNameParam(fileUploadByGroupKeyContext.getDataSchemeKey());
        FieldDefine fieldDefine = null;
        try {
            fieldDefine = this.iRunTimeViewController.queryFieldDefine(fileUploadByGroupKeyContext.getFieldKey());
        }
        catch (Exception e) {
            logHellperUtil.error(taskDefine.getKey(), logDimensionCollection, "\u9644\u4ef6\u4e0a\u4f20\u5931\u8d25", "\u7cfb\u7edf\u9519\u8bef\uff1a\u67e5\u8be2\u6307\u6807\u9519\u8bef");
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            return;
        }
        ArrayList<String> coveredFileKeys = new ArrayList<String>();
        CommonParamsDTO params = new CommonParamsDTO();
        params.setDataSchemeKey(taskDefine.getDataScheme());
        params.setTaskKey(taskDefine.getKey());
        List<FileInfo> groupFiles = this.filePoolService.getFileInfoByGroup(groupKey, params);
        List<FileUploadInfo> fileUploadInfos = fileUploadByGroupKeyContext.getFileUploadInfos();
        for (FileUploadInfo fileUploadInfo : fileUploadInfos) {
            Iterator<Object> fileName = fileUploadInfo.getName();
            FileUploadReturnInfo fileUploadReturnInfo = this.checkUploadFileService.checkFileInfo((String)((Object)(fileName = ((String)((Object)fileName)).trim().replace("\n", ""))), Long.valueOf(fileUploadInfo.getSize()), null, null);
            if (StringUtils.isNotEmpty((String)fileUploadReturnInfo.getErrorType())) {
                msg = "\u4e0a\u4f20\u6587\u4ef6\u7c7b\u578b\u9519\u8bef\uff1a" + fileName;
                logHellperUtil.error(taskDefine.getKey(), logDimensionCollection, "\u9644\u4ef6\u4e0a\u4f20\u5931\u8d25", PromptConsts.fileOprateServiceError(msg));
                throw new FileTypeErrorException(msg);
            }
            Object fileNameNoSuffix = "";
            int fileNameIndex = ((String)((Object)fileName)).lastIndexOf(".");
            fileNameNoSuffix = fileNameIndex >= 0 ? ((String)((Object)fileName)).substring(0, fileNameIndex) : fileName;
            List sensitiveWordList = this.checkSensitiveWordService.thisWordIsSensitiveWord((String)fileNameNoSuffix);
            if (sensitiveWordList.size() > 0) {
                StringBuilder msg2 = new StringBuilder();
                msg2.append("\u6587\u4ef6\u540d\u5305\u542b\u654f\u611f\u8bcd\u4fe1\u606f,");
                for (SensitiveWordDaoObject sensitiveWordDaoObject : sensitiveWordList) {
                    if (sensitiveWordDaoObject.getSensitiveDescription() == null || sensitiveWordDaoObject.getSensitiveDescription().length() <= 0) continue;
                    msg2.append(sensitiveWordDaoObject.getSensitiveDescription());
                    msg2.append(";");
                }
                msg2.append("\u8bf7\u4fee\u6539\u540e\u4e0a\u4f20\uff01");
                logHellperUtil.error(taskDefine.getKey(), logDimensionCollection, "\u9644\u4ef6\u4e0a\u4f20\u5931\u8d25", PromptConsts.fileOprateServiceError(msg2.toString()));
                throw new FieldNameIsSensitiveWordException(msg2.toString());
            }
            if (!groupFiles.isEmpty() && !fileUploadInfo.isCovered()) {
                for (FileInfo groupFile : groupFiles) {
                    if (!groupFile.getName().equals(fileName)) continue;
                    String msg3 = "\u5f53\u524dgroupKey\u5206\u7ec4\u4e0b\u6709\u91cd\u540d\u6587\u4ef6\uff1a" + fileName;
                    logHellperUtil.error(taskDefine.getKey(), logDimensionCollection, "\u9644\u4ef6\u4e0a\u4f20\u5931\u8d25", PromptConsts.fileOprateServiceError(msg3));
                    throw new FileException(msg3);
                }
            }
            String coveredFileKey = fileUploadInfo.getFileKey();
            if (!fileUploadInfo.isCovered() || !StringUtils.isNotEmpty((String)coveredFileKey)) continue;
            coveredFileKeys.add(coveredFileKey);
        }
        ArrayList<IFileListener> fileListeners = new ArrayList<IFileListener>();
        if (!coveredFileKeys.isEmpty()) {
            List<String> noExistFileKeys = this.attachmentFileAreaService.existFile(param, coveredFileKeys, 1);
            coveredFileKeys.removeAll(noExistFileKeys);
            if (!coveredFileKeys.isEmpty()) {
                FileDelEvent fileDelEvent;
                for (String coveredFileKey : coveredFileKeys) {
                    if (this.filePoolService.judgeFileOverwritten(coveredFileKey, fileUploadByGroupKeyContext.getTaskKey())) continue;
                    msg = "\u9644\u4ef6\u88ab\u4e0d\u53ef\u4fee\u6539\u6307\u6807\u5f15\u7528\uff0c\u65e0\u6cd5\u8986\u76d6\u4e0a\u4f20";
                    logHellperUtil.error(taskDefine.getKey(), logDimensionCollection, "\u9644\u4ef6\u4e0a\u4f20\u5931\u8d25", PromptConsts.fileOprateServiceError(msg));
                    throw new FileException(msg);
                }
                if (null != this.fileListenerProviders) {
                    for (IFileListenerProvider fileListenerProvider : this.fileListenerProviders) {
                        IFileListener fileListener = fileListenerProvider.getFileListener(null);
                        if (null == fileListener) continue;
                        fileListeners.add(fileListener);
                    }
                }
                if (null != fieldDefine && fieldDefine.getType().equals((Object)FieldType.FIELD_TYPE_FILE)) {
                    for (IFileListener fileListener : fileListeners) {
                        fileDelEvent = new FileDelEvent(fileUploadByGroupKeyContext.getDataSchemeKey(), fileUploadByGroupKeyContext.getTaskKey(), fileUploadByGroupKeyContext.getFormSchemeKey(), groupKey, coveredFileKeys);
                        fileListener.beforeFileDelete(fileDelEvent);
                    }
                }
                this.attachmentFileAreaService.batchDelete(param, coveredFileKeys);
                if (null != fieldDefine && fieldDefine.getType().equals((Object)FieldType.FIELD_TYPE_FILE)) {
                    for (IFileListener fileListener : fileListeners) {
                        fileDelEvent = new FileDelEvent(fileUploadByGroupKeyContext.getDataSchemeKey(), fileUploadByGroupKeyContext.getTaskKey(), fileUploadByGroupKeyContext.getFormSchemeKey(), groupKey, coveredFileKeys);
                        fileListener.afterFileDelete(fileDelEvent);
                    }
                }
            }
        }
        ArrayList<String> fileKeys = new ArrayList<String>();
        try {
            for (FileUploadInfo fileUploadInfo : fileUploadInfos) {
                byte[] picBytes = null;
                if (null != fieldDefine && fieldDefine.getType().equals((Object)FieldType.FIELD_TYPE_PICTURE)) {
                    picBytes = FileOperationUtils.convertByteArray(fileUploadInfo.getFile());
                    fileUploadInfo.getFile().close();
                    ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(picBytes);
                    fileUploadInfo.setFile(byteArrayInputStream);
                }
                FileInfo fileInfo = null;
                HashMap<String, String> expandInfo = new HashMap<String, String>();
                expandInfo.put("fileGroupKey", groupKey);
                expandInfo.put("secretlevel", fileUploadInfo.getFileSecret());
                if (fileUploadInfo.isCovered()) {
                    fileInfo = this.attachmentFileAreaService.uploadByKey(param, fileUploadInfo.getName(), fileUploadInfo.getFileKey(), fileUploadInfo.getFile(), expandInfo);
                } else {
                    fileInfo = this.attachmentFileAreaService.upload(param, fileUploadInfo.getName(), fileUploadInfo.getFile(), expandInfo);
                    fileKeys.add(fileInfo.getKey());
                }
                if (null == fieldDefine || !fieldDefine.getType().equals((Object)FieldType.FIELD_TYPE_PICTURE) || null == picBytes) continue;
                String fileName = fileUploadInfo.getName();
                long size = fileUploadInfo.getSize();
                if (size > 20480L) {
                    HashMap<String, String> thumbnailExpandInfo;
                    ByteArrayInputStream smallInputStream;
                    Throwable throwable;
                    float proportion = 0.1f;
                    if (size < 512000L && (proportion = (float)(1.0 - (double)((float)(size - 20480L) / 50000.0f) * 0.1)) < 0.1f) {
                        proportion = 0.1f;
                    }
                    ByteArrayOutputStream thumbnailsOPT = null;
                    try {
                        throwable = null;
                        try (ByteArrayInputStream inputStream = new ByteArrayInputStream(picBytes);
                             ByteArrayOutputStream outPutStream2 = new ByteArrayOutputStream();){
                            Thumbnails.of((InputStream[])new InputStream[]{inputStream}).scale((double)proportion).toOutputStream((OutputStream)outPutStream2);
                            thumbnailsOPT = outPutStream2;
                        }
                        catch (Throwable outPutStream2) {
                            throwable = outPutStream2;
                            throw outPutStream2;
                        }
                    }
                    catch (IOException e) {
                        thumbnailsOPT = null;
                        logger.error("\u751f\u6210\u56fe\u7247\u7f29\u7565\u56fe\u5931\u8d25\u5c06\u4f7f\u7528\u539f\u56fe\uff1a" + e.getMessage(), e);
                    }
                    if (null != thumbnailsOPT) {
                        smallInputStream = new ByteArrayInputStream(thumbnailsOPT.toByteArray());
                        throwable = null;
                        try {
                            thumbnailExpandInfo = new HashMap();
                            thumbnailExpandInfo.put("fileGroupKey", fileInfo.getKey());
                            this.attachmentFileAreaService.upload(param, fileName, smallInputStream, thumbnailExpandInfo);
                            continue;
                        }
                        catch (Throwable thumbnailExpandInfo2) {
                            throwable = thumbnailExpandInfo2;
                            throw thumbnailExpandInfo2;
                        }
                        finally {
                            if (smallInputStream == null) continue;
                            if (throwable != null) {
                                try {
                                    ((InputStream)smallInputStream).close();
                                }
                                catch (Throwable thumbnailExpandInfo2) {
                                    throwable.addSuppressed(thumbnailExpandInfo2);
                                }
                                continue;
                            }
                            ((InputStream)smallInputStream).close();
                            continue;
                        }
                    }
                    smallInputStream = new ByteArrayInputStream(picBytes);
                    throwable = null;
                    try {
                        thumbnailExpandInfo = new HashMap<String, String>();
                        thumbnailExpandInfo.put("fileGroupKey", fileInfo.getKey());
                        this.attachmentFileAreaService.upload(param, fileName, smallInputStream, thumbnailExpandInfo);
                        continue;
                    }
                    catch (Throwable throwable2) {
                        throwable = throwable2;
                        throw throwable2;
                    }
                    finally {
                        if (smallInputStream == null) continue;
                        if (throwable != null) {
                            try {
                                ((InputStream)smallInputStream).close();
                            }
                            catch (Throwable throwable3) {
                                throwable.addSuppressed(throwable3);
                            }
                            continue;
                        }
                        ((InputStream)smallInputStream).close();
                        continue;
                    }
                }
                ByteArrayInputStream smallInputStream = new ByteArrayInputStream(picBytes);
                Throwable throwable = null;
                try {
                    HashMap<String, String> thumbnailExpandInfo = new HashMap<String, String>();
                    thumbnailExpandInfo.put("fileGroupKey", fileInfo.getKey());
                    this.attachmentFileAreaService.upload(param, fileName, smallInputStream, thumbnailExpandInfo);
                }
                catch (Throwable throwable4) {
                    throwable = throwable4;
                    throw throwable4;
                }
                finally {
                    if (smallInputStream == null) continue;
                    if (throwable != null) {
                        try {
                            ((InputStream)smallInputStream).close();
                        }
                        catch (Throwable throwable5) {
                            throwable.addSuppressed(throwable5);
                        }
                        continue;
                    }
                    ((InputStream)smallInputStream).close();
                }
            }
            if (!coveredFileKeys.isEmpty() && !fileListeners.isEmpty()) {
                for (IFileListener fileListener : fileListeners) {
                    FileUploadEvent fileUploadEvent = new FileUploadEvent(fileUploadByGroupKeyContext.getDataSchemeKey(), fileUploadByGroupKeyContext.getTaskKey(), fileUploadByGroupKeyContext.getFormSchemeKey(), fileUploadByGroupKeyContext.getDimensionCombination(), fileUploadByGroupKeyContext.getFieldKey(), groupKey, coveredFileKeys);
                    fileListener.afterFileUpload(fileUploadEvent);
                }
            }
        }
        catch (IOException e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            logHellperUtil.error(taskDefine.getKey(), logDimensionCollection, "\u9644\u4ef6\u4e0a\u4f20\u5931\u8d25", PromptConsts.fileOprateSystemError(e.getMessage()));
            return;
        }
        if (null != fieldDefine && fieldDefine.getType().equals((Object)FieldType.FIELD_TYPE_FILE) && !fileKeys.isEmpty()) {
            FileRelateGroupInfo fileRelateGroupInfo = new FileRelateGroupInfo();
            fileRelateGroupInfo.setDataSchemeKey(fileUploadByGroupKeyContext.getDataSchemeKey());
            fileRelateGroupInfo.setTaskKey(fileUploadByGroupKeyContext.getTaskKey());
            fileRelateGroupInfo.setDimensionCombination(fileUploadByGroupKeyContext.getDimensionCombination());
            fileRelateGroupInfo.setFormSchemeKey(fileUploadByGroupKeyContext.getFormSchemeKey());
            fileRelateGroupInfo.setFormKey(fileUploadByGroupKeyContext.getFormKey());
            fileRelateGroupInfo.setFieldKey(fileUploadByGroupKeyContext.getFieldKey());
            fileRelateGroupInfo.setGroupKey(fileUploadByGroupKeyContext.getGroupKey());
            fileRelateGroupInfo.setFileKeys(fileKeys);
            this.filePoolService.uploadFilesFromFilePool(fileRelateGroupInfo);
        }
        logHellperUtil.info(taskDefine.getKey(), logDimensionCollection, "\u9644\u4ef6\u4e0a\u4f20\u6210\u529f", "\u9644\u4ef6\u4e0a\u4f20\u6210\u529f");
    }

    @Override
    public void changeFileInfo(ChangeFileSecretInfo changeFileSecretInfo) {
        LogHellperUtil logHellperUtil = new LogHellperUtil(this.dataServiceLoggerFactory, MOUDLE);
        try {
            FileBucketNameParam param = new FileBucketNameParam(changeFileSecretInfo.getParams().getDataSchemeKey());
            ObjectInfo objectInfo = this.attachmentFileAreaService.getObjectInfo(param, changeFileSecretInfo.getFileKey());
            if (StringUtils.isNotEmpty((String)((String)objectInfo.getExtProp().get("secretlevel")))) {
                HashMap<String, String> expandInfo = new HashMap<String, String>();
                expandInfo.put("secretlevel", changeFileSecretInfo.getFileSecret());
                this.attachmentFileAreaService.update(param, changeFileSecretInfo.getFileKey(), expandInfo);
            } else {
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                this.attachmentFileAreaService.download(param, changeFileSecretInfo.getFileKey(), outputStream);
                ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
                this.attachmentFileAreaService.delete(param, changeFileSecretInfo.getFileKey());
                HashMap<String, String> expandInfo = new HashMap<String, String>();
                expandInfo.put("fileGroupKey", (String)objectInfo.getExtProp().get("fileGroupKey"));
                expandInfo.put("secretlevel", changeFileSecretInfo.getFileSecret());
                if (StringUtils.isNotEmpty((String)((String)objectInfo.getExtProp().get("filePool")))) {
                    expandInfo.put("filePool", (String)objectInfo.getExtProp().get("filePool"));
                }
                if (StringUtils.isNotEmpty((String)((String)objectInfo.getExtProp().get("category")))) {
                    expandInfo.put("category", (String)objectInfo.getExtProp().get("category"));
                }
                this.attachmentFileAreaService.uploadByKey(param, objectInfo.getName(), objectInfo.getKey(), objectInfo.getOwner(), objectInfo.getCreateTime(), inputStream, expandInfo);
            }
            logHellperUtil.info(changeFileSecretInfo.getParams().getTaskKey(), null, "\u9644\u4ef6\u5bc6\u7ea7\u4fee\u6539\u6210\u529f", "\u9644\u4ef6\u5bc6\u7ea7\u4fee\u6539\u6210\u529f");
        }
        catch (ObjectStorageException e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            logHellperUtil.error(changeFileSecretInfo.getParams().getTaskKey(), null, "\u9644\u4ef6\u5bc6\u7ea7\u4fee\u6539\u5931\u8d25", PromptConsts.fileOprateSystemError(e.getMessage()));
        }
    }

    @Override
    public void batchUpdateGroupKey(List<String> fileKeys, String newGroupKey, CommonParamsDTO params) {
        FileBucketNameParam param = new FileBucketNameParam(params.getDataSchemeKey());
        HashMap<String, String> updateInfo = new HashMap<String, String>();
        updateInfo.put("fileGroupKey", newGroupKey);
        this.attachmentFileAreaService.batchupdate(param, fileKeys, updateInfo);
    }

    @Override
    public void deleteFile(AombstoneFileInfo aombstoneFileInfo) {
        LogHellperUtil logHellperUtil = new LogHellperUtil(this.dataServiceLoggerFactory, MOUDLE);
        try {
            FileDelEvent fileDelEvent;
            boolean bl;
            DataAccessContext context = new DataAccessContext(this.dataModelService);
            NvwaQueryModel queryModel = new NvwaQueryModel();
            DataScheme dataScheme = this.runtimeDataSchemeService.getDataScheme(aombstoneFileInfo.getDataSchemeKey());
            String tableName = this.getCurrentSplitTableName(dataScheme);
            TableModelDefine filePoolTable = this.dataModelService.getTableModelDefineByName(tableName);
            if (null == filePoolTable) {
                throw new NotFoundTableDefineException(new String[]{"\u9644\u4ef6\u5173\u8054\u8868\u672a\u521b\u5efa\uff0c\u8bf7\u91cd\u65b0\u53d1\u5e03\u6570\u636e\u65b9\u6848\uff01"});
            }
            DimensionChanger dimensionChanger = this.dataEngineAdapter.getDimensionChanger(filePoolTable.getName());
            ArrayList<String> fileKeys = new ArrayList<String>(aombstoneFileInfo.getFileKeys());
            List filePoolFields = this.dataModelService.getColumnModelDefinesByTable(filePoolTable.getID());
            for (ColumnModelDefine filePoolField : filePoolFields) {
                queryModel.getColumns().add(new NvwaQueryColumn(filePoolField));
                if (StringUtils.isNotEmpty((String)aombstoneFileInfo.getGroupKey()) && filePoolField.getCode().equals("GROUPKEY")) {
                    queryModel.getColumnFilters().put(filePoolField, aombstoneFileInfo.getGroupKey());
                    continue;
                }
                if (!filePoolField.getCode().equals("FILEKEY")) continue;
                queryModel.getColumnFilters().put(filePoolField, fileKeys);
            }
            HashMap<String, Set> dimNameValues = new HashMap<String, Set>();
            HashSet<String> fieldKeys = new HashSet<String>();
            List columns = queryModel.getColumns();
            INvwaUpdatableDataAccess updatableDataAccess = this.iNvwaDataAccessProvider.createUpdatableDataAccess(queryModel);
            INvwaUpdatableDataSet iNvwaDataRows = updatableDataAccess.executeQueryForUpdate(context);
            for (int i = 0; i < iNvwaDataRows.size(); ++i) {
                INvwaDataRow row = iNvwaDataRows.getRow(i);
                for (int j = 0; j < columns.size(); ++j) {
                    String dimensionName;
                    if ("FIELD_KEY".equals(((NvwaQueryColumn)columns.get(j)).getColumnModel().getCode())) {
                        fieldKeys.add((String)row.getValue(j));
                        continue;
                    }
                    if ("ID".equals(((NvwaQueryColumn)columns.get(j)).getColumnModel().getCode()) || "GROUPKEY".equals(((NvwaQueryColumn)columns.get(j)).getColumnModel().getCode()) || "FILEKEY".equals(((NvwaQueryColumn)columns.get(j)).getColumnModel().getCode()) || "ISDELETE".equals(((NvwaQueryColumn)columns.get(j)).getColumnModel().getCode()) || !StringUtils.isNotEmpty((String)(dimensionName = dimensionChanger.getDimensionName(((NvwaQueryColumn)columns.get(j)).getColumnModel().getCode())))) continue;
                    String dimValue = (String)row.getValue(j);
                    Set dimValues = dimNameValues.computeIfAbsent(dimensionName, k -> new HashSet());
                    dimValues.add(dimValue);
                }
            }
            HashMap<String, DimensionValue> dimensionSet = new HashMap<String, DimensionValue>();
            for (Map.Entry entry : dimNameValues.entrySet()) {
                String dimName = (String)entry.getKey();
                Set dimValues = (Set)entry.getValue();
                DimensionValue dimensionValue = new DimensionValue();
                dimensionValue.setName(dimName);
                dimensionValue.setValue(String.join((CharSequence)";", dimValues));
                dimensionSet.put(dimName, dimensionValue);
            }
            DimensionCollection dimensionCollection = this.dimCollectionBuildUtil.buildDimensionCollection(dimensionSet, aombstoneFileInfo.getFormSchemeKey());
            boolean bl2 = false;
            IDataAccessService dataAccessService = this.iDataAccessServiceProvider.getDataAccessService(aombstoneFileInfo.getTaskKey(), aombstoneFileInfo.getFormSchemeKey());
            IBatchZBAccessResult zbWriteAccess = dataAccessService.getZBWriteAccess(dimensionCollection, new ArrayList(fieldKeys));
            block6: for (int i = 0; i < iNvwaDataRows.size(); ++i) {
                INvwaDataRow row = iNvwaDataRows.getRow(i);
                DimensionCombinationBuilder dimensionCombinationBuilder = new DimensionCombinationBuilder();
                String fieldKey = "";
                for (int j = 0; j < columns.size(); ++j) {
                    String dimensionName;
                    if ("FIELD_KEY".equals(((NvwaQueryColumn)columns.get(j)).getColumnModel().getCode())) {
                        fieldKey = (String)row.getValue(j);
                        continue;
                    }
                    if ("ID".equals(((NvwaQueryColumn)columns.get(j)).getColumnModel().getCode()) || "GROUPKEY".equals(((NvwaQueryColumn)columns.get(j)).getColumnModel().getCode()) || "FILEKEY".equals(((NvwaQueryColumn)columns.get(j)).getColumnModel().getCode()) || "ISDELETE".equals(((NvwaQueryColumn)columns.get(j)).getColumnModel().getCode()) || !StringUtils.isNotEmpty((String)(dimensionName = dimensionChanger.getDimensionName(((NvwaQueryColumn)columns.get(j)).getColumnModel().getCode())))) continue;
                    String dimValue = (String)row.getValue(j);
                    dimensionCombinationBuilder.setValue(dimensionName, (Object)dimValue);
                }
                IAccessResult access = zbWriteAccess.getAccess(dimensionCombinationBuilder.getCombination(), fieldKey);
                if (!access.haveAccess()) continue;
                bl = true;
                for (int j = 0; j < columns.size(); ++j) {
                    if (!"ISDELETE".equals(((NvwaQueryColumn)columns.get(j)).getColumnModel().getCode())) continue;
                    row.setValue(j, (Object)"1");
                    continue block6;
                }
            }
            if (!bl) {
                logHellperUtil.info(aombstoneFileInfo.getTaskKey(), null, "\u65e0\u6743\u9650\uff0c\u65e0\u9644\u4ef6\u5220\u9664", "\u65e0\u6743\u9650\uff0c\u65e0\u9644\u4ef6\u5220\u9664");
                return;
            }
            ArrayList<IFileListener> fileListeners = new ArrayList<IFileListener>();
            if (null != this.fileListenerProviders) {
                for (IFileListenerProvider fileListenerProvider : this.fileListenerProviders) {
                    IFileListener fileListener = fileListenerProvider.getFileListener(null);
                    if (null == fileListener) continue;
                    fileListeners.add(fileListener);
                }
            }
            for (IFileListener fileListener : fileListeners) {
                fileDelEvent = new FileDelEvent(aombstoneFileInfo.getDataSchemeKey(), aombstoneFileInfo.getTaskKey(), aombstoneFileInfo.getFormSchemeKey(), aombstoneFileInfo.getGroupKey(), fileKeys);
                fileListener.beforeFileDelete(fileDelEvent);
            }
            iNvwaDataRows.commitChanges(context);
            for (IFileListener fileListener : fileListeners) {
                fileDelEvent = new FileDelEvent(aombstoneFileInfo.getDataSchemeKey(), aombstoneFileInfo.getTaskKey(), aombstoneFileInfo.getFormSchemeKey(), aombstoneFileInfo.getGroupKey(), fileKeys);
                fileListener.afterFileDelete(fileDelEvent);
            }
            logHellperUtil.info(aombstoneFileInfo.getTaskKey(), null, "\u9644\u4ef6\u5220\u9664\u6210\u529f", "\u9644\u4ef6\u5220\u9664\u6210\u529f");
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            logHellperUtil.error(aombstoneFileInfo.getTaskKey(), null, "\u9644\u4ef6\u5220\u9664\u5931\u8d25", PromptConsts.fileOprateSystemError(e.getMessage()));
        }
    }

    @Override
    public void physicalDeleteFile(Set<String> fileKeys) {
        this.physicalDeleteFile(fileKeys, new CommonParamsDTO());
    }

    @Override
    public void physicalDeleteFile(Set<String> fileKeys, CommonParamsDTO params) {
        LogHellperUtil logHellperUtil = new LogHellperUtil(this.dataServiceLoggerFactory, MOUDLE);
        FileBucketNameParam param = new FileBucketNameParam(params.getDataSchemeKey());
        for (String fileKey : fileKeys) {
            this.attachmentFileAreaService.delete(param, fileKey);
            List<FileInfo> fileInfos = this.getFileInfoByGroup(fileKey, params);
            if (null == fileInfos || fileInfos.isEmpty()) continue;
            for (FileInfo fileInfo : fileInfos) {
                this.attachmentFileAreaService.delete(param, fileInfo.getKey());
            }
        }
        logHellperUtil.info(null, null, "\u9644\u4ef6\u7269\u7406\u5220\u9664\u6210\u529f", "\u9644\u4ef6\u7269\u7406\u5220\u9664\u6210\u529f");
    }

    @Override
    public void physicalDeletePicture(List<String> groupKeys, CommonParamsDTO params) {
        LogHellperUtil logHellperUtil = new LogHellperUtil(this.dataServiceLoggerFactory, MOUDLE);
        FileBucketNameParam param = new FileBucketNameParam(params.getDataSchemeKey());
        this.attachmentFileAreaService.batchDeletePic(param, groupKeys);
        logHellperUtil.info(null, null, "\u6279\u91cf\u7269\u7406\u5220\u9664\u56fe\u7247\u6210\u529f", "\u6279\u91cf\u7269\u7406\u5220\u9664\u56fe\u7247\u6210\u529f");
    }

    @Override
    public void batchDeleteFile(BatchDeleteFileInfo batchDeleteFileInfo) {
        LogHellperUtil logHellperUtil = new LogHellperUtil(this.dataServiceLoggerFactory, MOUDLE);
        FormSchemeDefine formScheme = this.iRunTimeViewController.getFormScheme(batchDeleteFileInfo.getFormscheme());
        try {
            this.getGroupKeysAndDelete(batchDeleteFileInfo.getDataSchemeKey(), formScheme, batchDeleteFileInfo.getDimensionCollection(), batchDeleteFileInfo.getFormKeys());
            logHellperUtil.info(formScheme.getTaskKey(), null, "\u9644\u4ef6\u6279\u91cf\u5220\u9664\u6210\u529f", "\u9644\u4ef6\u6279\u91cf\u5220\u9664\u6210\u529f");
        }
        catch (Exception e) {
            logHellperUtil.error(formScheme.getTaskKey(), null, "\u9644\u4ef6\u6279\u91cf\u5220\u9664\u5931\u8d25", PromptConsts.fileOprateSystemError(e.getMessage()));
        }
    }

    private Set<String> getGroupKeysAndDelete(String dataSchemeKey, FormSchemeDefine formScheme, DimensionCollection dimensionCollection, List<String> formKeys) {
        HashSet<String> groupKeys = new HashSet<String>();
        QueryEnvironment queryEnvironment = new QueryEnvironment();
        ExecutorContext executorContext = new ExecutorContext(this.dataDefinitionRuntimeController);
        ReportFmlExecEnvironment environment = new ReportFmlExecEnvironment(this.iRunTimeViewController, this.dataDefinitionRuntimeController, this.entityViewRunTimeController, formScheme.getKey());
        executorContext.setEnv((IFmlExecEnvironment)environment);
        DimensionValueSet dimension = DimensionValueSetUtil.mergeDimensionValueSet((DimensionCollection)dimensionCollection);
        if (null == formKeys || 0 == formKeys.size()) {
            formKeys = this.iRunTimeViewController.queryAllFormKeysByFormScheme(formScheme.getKey());
        }
        try {
            for (String formKey : formKeys) {
                List regions = this.iRunTimeViewController.getAllRegionsInForm(formKey);
                for (DataRegionDefine region : regions) {
                    this.queryAndDel(dataSchemeKey, formScheme, groupKeys, queryEnvironment, executorContext, dimension, region);
                }
            }
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
        return groupKeys;
    }

    private void queryAndDel(String dataSchemeKey, FormSchemeDefine formScheme, Set<String> groupKeys, QueryEnvironment queryEnvironment, ExecutorContext executorContext, DimensionValueSet dimension, DataRegionDefine region) throws Exception {
        HashSet<String> deleteGroupKeys = new HashSet<String>();
        IDataQuery dataQuery = this.dataAccessProvider.newDataQuery(queryEnvironment);
        dataQuery.setMasterKeys(dimension);
        List fieldKeys = this.iRunTimeViewController.getFieldKeysInRegion(region.getKey());
        List fieldDefines = this.dataDefinitionRuntimeController.queryFieldDefines((Collection)fieldKeys);
        int fieldCount = 0;
        for (FieldDefine fieldDefine : fieldDefines) {
            if (!fieldDefine.getType().equals((Object)FieldType.FIELD_TYPE_FILE)) continue;
            dataQuery.addColumn(fieldDefine);
            ++fieldCount;
        }
        IDataTable dataTable = dataQuery.executeQuery(executorContext);
        for (int i = 0; i < dataTable.getTotalCount(); ++i) {
            for (int j = 0; j < fieldCount; ++j) {
                String groupKey = dataTable.getItem(i).getAsString(j);
                if (!StringUtils.isNotEmpty((String)groupKey)) continue;
                dataTable.getItem(i).setValue(j, null);
                deleteGroupKeys.add(groupKey);
            }
        }
        if (!deleteGroupKeys.isEmpty()) {
            FileBatchDelEvent fileBatchDelEvent;
            groupKeys.addAll(deleteGroupKeys);
            ArrayList<IFileListener> fileListeners = new ArrayList<IFileListener>();
            if (null != this.fileListenerProviders) {
                for (IFileListenerProvider fileListenerProvider : this.fileListenerProviders) {
                    IFileListener fileListener = fileListenerProvider.getFileListener(null);
                    fileListeners.add(fileListener);
                }
            }
            for (IFileListener fileListener : fileListeners) {
                fileBatchDelEvent = new FileBatchDelEvent(dataSchemeKey, formScheme.getTaskKey(), formScheme.getKey(), null, null, new ArrayList<String>(deleteGroupKeys));
                fileListener.beforeBatchFileDelete(fileBatchDelEvent);
            }
            dataTable.commitChanges(true);
            for (IFileListener fileListener : fileListeners) {
                fileBatchDelEvent = new FileBatchDelEvent(dataSchemeKey, formScheme.getTaskKey(), formScheme.getKey(), null, null, new ArrayList<String>(deleteGroupKeys));
                fileListener.afterBatchFileDelete(fileBatchDelEvent);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void deleteMarkFile(DeleteMarkFileInfo deleteMarkFileInfo, AsyncTaskMonitor monitor) {
        LogHellperUtil logHellperUtil = new LogHellperUtil(this.dataServiceLoggerFactory, MOUDLE);
        double progress = 0.2;
        if (null != monitor) {
            monitor.progressAndMessage(progress, "");
        }
        while (null == this.loca.lock(LOCAK_NAME + deleteMarkFileInfo.getDataSchemeKey())) {
        }
        try {
            String dataSchemeKey = deleteMarkFileInfo.getDataSchemeKey();
            Map<String, List<String>> groupFilesMap = this.buildGroupFilesMap(dataSchemeKey);
            this.delMarkFile(monitor, progress, dataSchemeKey, groupFilesMap);
            logHellperUtil.info(null, null, "\u6807\u8bb0\u9644\u4ef6\u5220\u9664\u6210\u529f", "\u6807\u8bb0\u9644\u4ef6\u5220\u9664\u6210\u529f");
            if (null != monitor) {
                monitor.finish("success", null);
            }
        }
        catch (Exception e) {
            logHellperUtil.error(null, null, "\u6807\u8bb0\u9644\u4ef6\u5220\u9664\u5931\u8d25", PromptConsts.fileOprateSystemError(e.getMessage()));
            if (null != monitor) {
                monitor.error("error", null);
            }
        }
        finally {
            this.loca.unLock(LOCAK_NAME + deleteMarkFileInfo.getDataSchemeKey());
        }
    }

    private void delMarkFile(AsyncTaskMonitor monitor, double progress, String dataSchemeKey, Map<String, List<String>> groupFilesMap) {
        Set<String> groupKeys = groupFilesMap.keySet();
        double step = 0.75 / (double)groupKeys.size();
        for (String groupKey : groupKeys) {
            AombstoneFileInfo aombstoneFileInfo;
            HashSet<String> referencesFileKeys = new HashSet<String>();
            HashSet<String> noReferencesFileKeys = new HashSet<String>();
            List<String> fileKeys = groupFilesMap.get(groupKey);
            for (String fileKey : fileKeys) {
                List<ReferencesInfo> references = this.filePoolService.getReferences(fileKey, dataSchemeKey);
                if (references.isEmpty()) {
                    noReferencesFileKeys.add(fileKey);
                    continue;
                }
                referencesFileKeys.add(fileKey);
            }
            if (!referencesFileKeys.isEmpty()) {
                aombstoneFileInfo = new AombstoneFileInfo();
                aombstoneFileInfo.setGroupKey(groupKey);
                aombstoneFileInfo.setFileKeys(referencesFileKeys);
                aombstoneFileInfo.setDataSchemeKey(dataSchemeKey);
                this.tombstoneFile(aombstoneFileInfo);
            }
            if (!noReferencesFileKeys.isEmpty()) {
                aombstoneFileInfo = new AombstoneFileInfo();
                aombstoneFileInfo.setGroupKey(groupKey);
                aombstoneFileInfo.setFileKeys(noReferencesFileKeys);
                aombstoneFileInfo.setDataSchemeKey(dataSchemeKey);
                this.physicalDeleteFile(aombstoneFileInfo);
            }
            progress += step;
            if (null == monitor) continue;
            monitor.progressAndMessage(progress, "");
        }
    }

    @NotNull
    private Map<String, List<String>> buildGroupFilesMap(String dataSchemeKey) throws Exception {
        DataScheme dataScheme = this.runtimeDataSchemeService.getDataScheme(dataSchemeKey);
        String tableName = this.getCurrentSplitTableName(dataScheme);
        TableModelDefine filePoolTable = this.dataModelService.getTableModelDefineByName(tableName);
        if (null == filePoolTable) {
            throw new NotFoundTableDefineException(new String[]{"\u9644\u4ef6\u5173\u8054\u8868\u672a\u521b\u5efa\uff0c\u8bf7\u91cd\u65b0\u53d1\u5e03\u6570\u636e\u65b9\u6848\uff01"});
        }
        DataAccessContext context = new DataAccessContext(this.dataModelService);
        NvwaQueryModel queryModel = new NvwaQueryModel();
        List filePoolFields = this.dataModelService.getColumnModelDefinesByTable(filePoolTable.getID());
        ColumnModelDefine groupCol = null;
        ColumnModelDefine fileCol = null;
        for (ColumnModelDefine filePoolField : filePoolFields) {
            queryModel.getColumns().add(new NvwaQueryColumn(filePoolField));
            if (filePoolField.getCode().equals("ISDELETE")) {
                queryModel.getColumnFilters().put(filePoolField, "1");
                continue;
            }
            if (filePoolField.getCode().equals("GROUPKEY")) {
                groupCol = filePoolField;
                continue;
            }
            if (!filePoolField.getCode().equals("FILEKEY")) continue;
            fileCol = filePoolField;
        }
        HashMap<String, List<String>> groupFilesMap = new HashMap<String, List<String>>();
        INvwaDataAccess readOnlyDataAccess = this.iNvwaDataAccessProvider.createReadOnlyDataAccess(queryModel);
        INvwaDataSet iNvwaDataRows = readOnlyDataAccess.executeQueryWithRowKey(context);
        for (int i = 0; i < iNvwaDataRows.size(); ++i) {
            INvwaDataRow row = iNvwaDataRows.getRow(i);
            String groupKey = (String)row.getValue(groupCol);
            String fileKey = (String)row.getValue(fileCol);
            ArrayList<String> fileKeys = (ArrayList<String>)groupFilesMap.get(groupKey);
            if (null != fileKeys) {
                fileKeys.add(fileKey);
                continue;
            }
            fileKeys = new ArrayList<String>();
            fileKeys.add(fileKey);
            groupFilesMap.put(groupKey, fileKeys);
        }
        return groupFilesMap;
    }

    private void tombstoneFile(AombstoneFileInfo aombstoneFileInfo) {
        DataAccessContext context = new DataAccessContext(this.dataModelService);
        NvwaQueryModel queryModel = new NvwaQueryModel();
        DataScheme dataScheme = this.runtimeDataSchemeService.getDataScheme(aombstoneFileInfo.getDataSchemeKey());
        String tableName = this.getCurrentSplitTableName(dataScheme);
        TableModelDefine filePoolTable = this.dataModelService.getTableModelDefineByName(tableName);
        if (null == filePoolTable) {
            throw new NotFoundTableDefineException(new String[]{"\u9644\u4ef6\u5173\u8054\u8868\u672a\u521b\u5efa\uff0c\u8bf7\u91cd\u65b0\u53d1\u5e03\u6570\u636e\u65b9\u6848"});
        }
        List filePoolFields = this.dataModelService.getColumnModelDefinesByTable(filePoolTable.getID());
        ArrayList<String> filepoolFileKeyList = new ArrayList<String>(aombstoneFileInfo.getFileKeys());
        for (ColumnModelDefine filePoolField : filePoolFields) {
            queryModel.getColumns().add(new NvwaQueryColumn(filePoolField));
            if (StringUtils.isNotEmpty((String)aombstoneFileInfo.getGroupKey()) && filePoolField.getCode().equals("GROUPKEY")) {
                queryModel.getColumnFilters().put(filePoolField, aombstoneFileInfo.getGroupKey());
                continue;
            }
            if (filePoolField.getCode().equals("FILEKEY")) {
                queryModel.getColumnFilters().put(filePoolField, filepoolFileKeyList);
                continue;
            }
            if (!filePoolField.getCode().equals("ISDELETE")) continue;
            queryModel.getColumnFilters().put(filePoolField, "1");
        }
        INvwaUpdatableDataAccess updatableDataAccess = this.iNvwaDataAccessProvider.createUpdatableDataAccess(queryModel);
        try {
            INvwaDataUpdator iNvwaDataUpdator = updatableDataAccess.openForUpdate(context);
            iNvwaDataUpdator.deleteAll();
            iNvwaDataUpdator.commitChanges(context);
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
    }

    private void physicalDeleteFile(AombstoneFileInfo aombstoneFileInfo) {
        Set<String> fileKeys = aombstoneFileInfo.getFileKeys();
        ArrayList<String> fileKeyList = new ArrayList<String>(fileKeys);
        DataAccessContext context = new DataAccessContext(this.dataModelService);
        NvwaQueryModel queryModel = new NvwaQueryModel();
        DataScheme dataScheme = this.runtimeDataSchemeService.getDataScheme(aombstoneFileInfo.getDataSchemeKey());
        String tableName = this.getCurrentSplitTableName(dataScheme);
        TableModelDefine filePoolTable = this.dataModelService.getTableModelDefineByName(tableName);
        if (null == filePoolTable) {
            throw new NotFoundTableDefineException(new String[]{"\u9644\u4ef6\u5173\u8054\u8868\u672a\u521b\u5efa\uff0c\u8bf7\u91cd\u65b0\u53d1\u5e03\u6570\u636e\u65b9\u6848\uff01"});
        }
        List filePoolFields = this.dataModelService.getColumnModelDefinesByTable(filePoolTable.getID());
        for (ColumnModelDefine filePoolField : filePoolFields) {
            queryModel.getColumns().add(new NvwaQueryColumn(filePoolField));
            if (StringUtils.isNotEmpty((String)aombstoneFileInfo.getGroupKey()) && filePoolField.getCode().equals("GROUPKEY")) {
                queryModel.getColumnFilters().put(filePoolField, aombstoneFileInfo.getGroupKey());
                continue;
            }
            if (!filePoolField.getCode().equals("FILEKEY")) continue;
            queryModel.getColumnFilters().put(filePoolField, fileKeyList);
        }
        INvwaUpdatableDataAccess updatableDataAccess = this.iNvwaDataAccessProvider.createUpdatableDataAccess(queryModel);
        try {
            INvwaDataUpdator iNvwaDataUpdator = updatableDataAccess.openForUpdate(context);
            iNvwaDataUpdator.deleteAll();
            iNvwaDataUpdator.commitChanges(context);
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
        FileBucketNameParam param = new FileBucketNameParam(dataScheme.getKey());
        ArrayList<String> deleteFileKeys = new ArrayList<String>();
        List<FileInfo> fileInfos = this.attachmentFileAreaService.getFileInfoByKeys(param, fileKeyList);
        for (FileInfo fileInfo : fileInfos) {
            String filepoolKey = fileInfo.getFilepoolKey();
            if (!StringUtils.isEmpty((String)filepoolKey)) continue;
            deleteFileKeys.add(fileInfo.getKey());
        }
        if (!deleteFileKeys.isEmpty()) {
            this.attachmentFileAreaService.batchDelete(param, deleteFileKeys);
        }
    }

    @Override
    public List<String> getGroupKeys(String formscheme, DimensionValueSet dimensionValueSet, String formKey, String fieldKey) {
        ArrayList<String> groupKeys = new ArrayList<String>();
        QueryEnvironment queryEnvironment = new QueryEnvironment();
        ExecutorContext executorContext = new ExecutorContext(this.dataDefinitionRuntimeController);
        ReportFmlExecEnvironment environment = new ReportFmlExecEnvironment(this.iRunTimeViewController, this.dataDefinitionRuntimeController, this.entityViewRunTimeController, formscheme);
        executorContext.setEnv((IFmlExecEnvironment)environment);
        List dataRegionDefineList = this.iRunTimeViewController.getAllRegionsInForm(formKey);
        boolean flag = false;
        try {
            for (DataRegionDefine dataRegionDefine : dataRegionDefineList) {
                List fieldKeys = this.iRunTimeViewController.getFieldKeysInRegion(dataRegionDefine.getKey());
                List fieldDefines = this.dataDefinitionRuntimeController.queryFieldDefines((Collection)fieldKeys);
                for (FieldDefine fieldDefine : fieldDefines) {
                    String groupKey;
                    int i;
                    IDataTable dataTable;
                    IDataQuery dataQuery;
                    if (!fieldDefine.getKey().equals(fieldKey)) continue;
                    flag = true;
                    if (dataRegionDefine.getRegionKind() == DataRegionKind.DATA_REGION_SIMPLE) {
                        dataQuery = this.dataAccessProvider.newDataQuery(queryEnvironment);
                        dataQuery.addColumn(fieldDefine);
                        dataQuery.setMasterKeys(dimensionValueSet);
                        dataTable = dataQuery.executeQuery(executorContext);
                        for (i = 0; i < dataTable.getTotalCount(); ++i) {
                            groupKey = dataTable.getItem(i).getAsString(0);
                            if (!StringUtils.isNotEmpty((String)groupKey)) continue;
                            groupKeys.add(groupKey);
                        }
                    } else {
                        dataQuery = this.dataAccessProvider.newDataQuery(queryEnvironment);
                        dataQuery.addColumn(fieldDefine);
                        if (null != dimensionValueSet.getValue("RECORDKEY")) {
                            String recordKey = dimensionValueSet.getValue("RECORDKEY").toString();
                            String[] split = recordKey.split("#\\^\\$");
                            dimensionValueSet.setValue("RECORDKEY", (Object)split[split.length - 1]);
                        }
                        dataQuery.setMasterKeys(dimensionValueSet);
                        dataTable = dataQuery.executeQuery(executorContext);
                        for (i = 0; i < dataTable.getTotalCount(); ++i) {
                            groupKey = dataTable.getItem(i).getAsString(fieldDefine);
                            if (!StringUtils.isNotEmpty((String)groupKey)) continue;
                            groupKeys.add(groupKey);
                        }
                    }
                    break;
                }
                if (!flag) continue;
                break;
            }
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
        return groupKeys;
    }

    @Override
    public RowDataValues searchFile(AttachmentQueryInfo attachmentQueryInfo) {
        RowDataValues rowDataValues = new RowDataValues();
        ArrayList<RowDataInfo> rowDataInfos = new ArrayList<RowDataInfo>();
        IDataAccessService dataAccessService = this.iDataAccessServiceProvider.getDataAccessService(attachmentQueryInfo.getTask(), attachmentQueryInfo.getFormscheme());
        boolean isOpenSecretLevel = this.stateSecretLevelService.secretLevelEnable(attachmentQueryInfo.getTask());
        HashMap<String, SecretLevel> secretLevelCatch = new HashMap<String, SecretLevel>();
        List<Object> formKeys = new ArrayList();
        if (null == attachmentQueryInfo.getFormKey() || attachmentQueryInfo.getFormKey().isEmpty()) {
            List formDefineList = this.iRunTimeViewController.queryAllFormDefinesByFormScheme(attachmentQueryInfo.getFormscheme());
            formKeys = formDefineList.stream().map(IBaseMetaItem::getKey).collect(Collectors.toList());
        } else {
            formKeys = attachmentQueryInfo.getFormKey();
        }
        TaskDefine taskDefine = this.iRunTimeViewController.queryTaskDefine(attachmentQueryInfo.getTask());
        IEntityDefine entityDefine = this.entityMetaService.queryEntity(taskDefine.getDw());
        String dwDimensionName = entityDefine.getDimensionName();
        CommonParamsDTO params = new CommonParamsDTO();
        params.setDataSchemeKey(taskDefine.getDataScheme());
        params.setTaskKey(taskDefine.getKey());
        DimensionCollection dimensionCollection = attachmentQueryInfo.getDimensionCollection();
        IBatchAccessResult readAccess = dataAccessService.getReadAccess(dimensionCollection, formKeys);
        IBatchAccessResult writeAccess = dataAccessService.getWriteAccess(dimensionCollection, formKeys);
        List dimensionCombinations = dimensionCollection.getDimensionCombinations();
        for (DimensionCombination dimensionCombination : dimensionCombinations) {
            DimensionValueSet dimensionValueSet = dimensionCombination.toDimensionValueSet();
            for (String string : formKeys) {
                IAccessResult access = readAccess.getAccess(dimensionCombination, string);
                try {
                    if (!access.haveAccess()) continue;
                    List allFieldKeys = this.iRunTimeViewController.getFieldKeysInForm(string);
                    ArrayList<String> fieldKeys = new ArrayList<String>();
                    List fieldDefines = this.dataDefinitionRuntimeController.queryFieldDefines((Collection)allFieldKeys);
                    for (FieldDefine fieldDefine : fieldDefines) {
                        if (!fieldDefine.getType().equals((Object)FieldType.FIELD_TYPE_FILE)) continue;
                        fieldKeys.add(fieldDefine.getKey());
                    }
                    IAccessResult formWriteAccess = writeAccess.getAccess(dimensionCombination, string);
                    for (String fieldKey : fieldKeys) {
                        List<String> groupKeys = this.getGroupKeys(attachmentQueryInfo.getFormscheme(), dimensionValueSet, string, fieldKey);
                        for (String groupKey : groupKeys) {
                            List<FileInfo> fileInfoByGroup = this.filePoolService.getFileInfoByGroup(groupKey, params);
                            for (FileInfo fileInfo : fileInfoByGroup) {
                                if (isOpenSecretLevel) {
                                    SecretLevel secretLevel = (SecretLevel)secretLevelCatch.get(fileInfo.getSecretlevel());
                                    if (null == secretLevel) {
                                        secretLevel = this.stateSecretLevelService.getSecretLevelItem(fileInfo.getSecretlevel());
                                        secretLevelCatch.put(fileInfo.getSecretlevel(), secretLevel);
                                    }
                                    if (!this.stateSecretLevelService.canAccess(secretLevel)) continue;
                                }
                                RowDataInfo rowDataInfo = new RowDataInfo();
                                rowDataInfo.setFileKey(fileInfo.getKey());
                                rowDataInfo.setGroupKey(groupKey);
                                rowDataInfo.setName(fileInfo.getName());
                                rowDataInfo.setCreatetime(fileInfo.getCreateTime());
                                rowDataInfo.setSize(fileInfo.getSize());
                                rowDataInfo.setCreator(fileInfo.getCreater());
                                if (isOpenSecretLevel) {
                                    SecretLevel secretLevel = (SecretLevel)secretLevelCatch.get(fileInfo.getSecretlevel());
                                    if (null == secretLevel) {
                                        secretLevel = this.stateSecretLevelService.getSecretLevelItem(fileInfo.getSecretlevel());
                                        secretLevelCatch.put(fileInfo.getSecretlevel(), secretLevel);
                                    }
                                    rowDataInfo.setConfidential(secretLevel.getTitle());
                                }
                                rowDataInfo.setWriteable(formWriteAccess.haveAccess());
                                rowDataInfo.setDw(dimensionValueSet.getValue(dwDimensionName).toString());
                                rowDataInfo.setFormKey(string);
                                rowDataInfos.add(rowDataInfo);
                            }
                        }
                    }
                }
                catch (Exception e) {
                    logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                }
            }
        }
        this.filterFiles(rowDataValues, rowDataInfos, attachmentQueryInfo.getSearchInfo(), attachmentQueryInfo.getOrder(), attachmentQueryInfo.getSortBy(), attachmentQueryInfo.isPage(), attachmentQueryInfo.getCurrentPage(), attachmentQueryInfo.getPageSize());
        return rowDataValues;
    }

    @Override
    public RowDataValues searchFileByField(AttachmentQueryByFieldInfo attachmentQueryByFieldInfo) {
        RowDataValues rowDataValues = new RowDataValues();
        ArrayList<RowDataInfo> rowDataInfos = new ArrayList<RowDataInfo>();
        IDataAccessService dataAccessService = this.iDataAccessServiceProvider.getDataAccessService(attachmentQueryByFieldInfo.getTask(), attachmentQueryByFieldInfo.getFormscheme());
        boolean isOpenSecretLevel = this.stateSecretLevelService.secretLevelEnable(attachmentQueryByFieldInfo.getTask());
        HashMap<String, SecretLevel> secretLevelCatch = new HashMap<String, SecretLevel>();
        TaskDefine taskDefine = this.iRunTimeViewController.queryTaskDefine(attachmentQueryByFieldInfo.getTask());
        IEntityDefine entityDefine = this.entityMetaService.queryEntity(taskDefine.getDw());
        String dwDimensionName = entityDefine.getDimensionName();
        CommonParamsDTO params = new CommonParamsDTO();
        params.setDataSchemeKey(taskDefine.getDataScheme());
        params.setTaskKey(taskDefine.getKey());
        DimensionCollection dimensionCollection = attachmentQueryByFieldInfo.getDimensionCollection();
        List dimensionCombinations = dimensionCollection.getDimensionCombinations();
        for (DimensionCombination dimensionCombination : dimensionCombinations) {
            DimensionValueSet dimensionValueSet = dimensionCombination.toDimensionValueSet();
            IAccessResult formReadAccess = dataAccessService.readable(dimensionCombination, attachmentQueryByFieldInfo.getFormKey());
            try {
                if (!formReadAccess.haveAccess()) continue;
                IAccessResult formWriteAccess = dataAccessService.writeable(dimensionCombination, attachmentQueryByFieldInfo.getFormKey());
                List<Object> groupKeys = null;
                if (StringUtils.isEmpty((String)attachmentQueryByFieldInfo.getGroupKey())) {
                    groupKeys = this.getGroupKeys(attachmentQueryByFieldInfo.getFormscheme(), dimensionValueSet, attachmentQueryByFieldInfo.getFormKey(), attachmentQueryByFieldInfo.getFieldKey());
                } else {
                    groupKeys = new ArrayList<String>();
                    groupKeys.add(attachmentQueryByFieldInfo.getGroupKey());
                }
                for (String string : groupKeys) {
                    List<FileInfo> fileInfoByGroup = this.filePoolService.getFileInfoByGroup(string, params);
                    for (FileInfo fileInfo : fileInfoByGroup) {
                        if (isOpenSecretLevel) {
                            SecretLevel secretLevel = (SecretLevel)secretLevelCatch.get(fileInfo.getSecretlevel());
                            if (null == secretLevel) {
                                secretLevel = this.stateSecretLevelService.getSecretLevelItem(fileInfo.getSecretlevel());
                                secretLevelCatch.put(fileInfo.getSecretlevel(), secretLevel);
                            }
                            if (!this.stateSecretLevelService.canAccess(secretLevel)) continue;
                        }
                        RowDataInfo rowDataInfo = new RowDataInfo();
                        rowDataInfo.setFileKey(fileInfo.getKey());
                        rowDataInfo.setGroupKey(string);
                        rowDataInfo.setName(fileInfo.getName());
                        rowDataInfo.setCreatetime(fileInfo.getCreateTime());
                        rowDataInfo.setSize(fileInfo.getSize());
                        rowDataInfo.setCreator(fileInfo.getCreater());
                        if (isOpenSecretLevel) {
                            SecretLevel secretLevel = (SecretLevel)secretLevelCatch.get(fileInfo.getSecretlevel());
                            if (null == secretLevel) {
                                secretLevel = this.stateSecretLevelService.getSecretLevelItem(fileInfo.getSecretlevel());
                                secretLevelCatch.put(fileInfo.getSecretlevel(), secretLevel);
                            }
                            rowDataInfo.setConfidential(secretLevel.getTitle());
                        }
                        rowDataInfo.setWriteable(formWriteAccess.haveAccess());
                        rowDataInfo.setDw(dimensionValueSet.getValue(dwDimensionName).toString());
                        rowDataInfo.setFormKey(attachmentQueryByFieldInfo.getFormKey());
                        rowDataInfos.add(rowDataInfo);
                    }
                }
            }
            catch (Exception e) {
                logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            }
        }
        this.filterFiles(rowDataValues, rowDataInfos, attachmentQueryByFieldInfo.getSearchInfo(), attachmentQueryByFieldInfo.getOrder(), attachmentQueryByFieldInfo.getSortBy(), attachmentQueryByFieldInfo.isPage(), attachmentQueryByFieldInfo.getCurrentPage(), attachmentQueryByFieldInfo.getPageSize());
        return rowDataValues;
    }

    private void filterFiles(RowDataValues rowDataValues, List<RowDataInfo> rowDataInfos, String searchInfo, String order, String sortBy, boolean page, Integer currentPage, Integer pageSize) {
        ArrayList<RowDataInfo> rowDatasCopy = new ArrayList<RowDataInfo>();
        HashSet<String> fileKeys = new HashSet<String>();
        for (RowDataInfo rowDataInfo : rowDataInfos) {
            if (fileKeys.contains(rowDataInfo.getFileKey())) continue;
            fileKeys.add(rowDataInfo.getFileKey());
            rowDatasCopy.add(rowDataInfo);
        }
        rowDataInfos.clear();
        rowDataInfos.addAll(rowDatasCopy);
        if (StringUtils.isNotEmpty((String)searchInfo)) {
            rowDatasCopy = new ArrayList();
            for (RowDataInfo rowDataInfo : rowDataInfos) {
                if (!rowDataInfo.getName().contains(searchInfo)) continue;
                rowDatasCopy.add(rowDataInfo);
            }
            rowDataInfos.clear();
            rowDataInfos.addAll(rowDatasCopy);
        }
        FileOperationUtils.sortFiles(order, sortBy, rowDataInfos);
        long totalSize = 0L;
        for (RowDataInfo rowDataInfo : rowDataInfos) {
            rowDataInfo.setIndex(rowDataInfos.indexOf(rowDataInfo) + 1);
            totalSize += rowDataInfo.getSize();
        }
        int totalNumber = rowDataInfos.size();
        if (page && totalNumber > 0) {
            rowDataInfos = totalNumber >= currentPage * pageSize ? rowDataInfos.subList((currentPage - 1) * pageSize, currentPage * pageSize) : (totalNumber == (currentPage - 1) * pageSize ? (currentPage == 1 ? rowDataInfos.subList(0, totalNumber) : rowDataInfos.subList((currentPage - 2) * pageSize, totalNumber)) : rowDataInfos.subList((currentPage - 1) * pageSize, totalNumber));
        }
        rowDataValues.setRowDatas(rowDataInfos);
        rowDataValues.setTotalNumber(totalNumber);
        rowDataValues.setTotalSize(totalSize);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public String downloadFile(DownLoadFileInfo downLoadFileInfo, OutputStream outputStream) {
        DimensionValueSet dimension = downLoadFileInfo.getDimensionCombination().toDimensionValueSet();
        TaskDefine taskDefine = this.iRunTimeViewController.queryTaskDefine(downLoadFileInfo.getTask());
        String dwdimensionName = this.entityMetaService.getDimensionName(taskDefine.getDw());
        IPeriodEntityAdapter periodAdapter = this.periodEngineService.getPeriodAdapter();
        String periodDimensionName = periodAdapter.getPeriodDimensionName();
        String targetKey = String.valueOf(dimension.getValue(dwdimensionName));
        String periodCode = String.valueOf(dimension.getValue(periodDimensionName));
        LogDimensionCollection logDimensionCollection = new LogDimensionCollection();
        logDimensionCollection.setDw(taskDefine.getDw(), new String[]{targetKey});
        logDimensionCollection.setPeriod(taskDefine.getDateTime(), periodCode);
        LogHellperUtil logHellperUtil = new LogHellperUtil(this.dataServiceLoggerFactory, MOUDLE);
        FileBucketNameParam param = new FileBucketNameParam(taskDefine.getDataScheme());
        List<FileInfo> files = this.getFiles(downLoadFileInfo, param);
        if (files.isEmpty()) {
            return "error";
        }
        String separator_code = "_";
        boolean secretLevelEnable = this.stateSecretLevelService.secretLevelEnable(downLoadFileInfo.getTask());
        if (files.size() == 1) {
            FileInfo file = files.get(0);
            byte[] databytes = this.attachmentFileAreaService.download(param, file.getKey());
            try {
                outputStream.write(databytes);
                String fileName = file.getName();
                int length = fileName.length();
                String extension = file.getExtension();
                if (StringUtils.isNotEmpty((String)file.getSecretlevel()) && secretLevelEnable) {
                    fileName = file.getName().substring(0, length - extension.length()) + separator_code + this.stateSecretLevelService.getSecretLevelItem(file.getSecretlevel()).getTitle() + extension;
                }
                logHellperUtil.info(taskDefine.getKey(), logDimensionCollection, "\u9644\u4ef6\u4e0b\u8f7d\u6210\u529f", "\u9644\u4ef6\u4e0b\u8f7d\u6210\u529f");
                return fileName;
            }
            catch (IOException e) {
                logHellperUtil.error(taskDefine.getKey(), logDimensionCollection, "\u9644\u4ef6\u4e0b\u8f7d\u5931\u8d25", PromptConsts.fileOprateSystemError(e.getMessage()));
                logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                return "error";
            }
        }
        try (ZipOutputStream zos = new ZipOutputStream(outputStream);){
            zos.setEncoding("gb2312");
            for (FileInfo file : files) {
                SecretLevel secretLevelItem = this.stateSecretLevelService.getSecretLevelItem(file.getSecretlevel());
                byte[] databytes = this.attachmentFileAreaService.download(param, file.getKey());
                String fileName = file.getName();
                int length = fileName.length();
                String extension = file.getExtension();
                if (StringUtils.isNotEmpty((String)file.getSecretlevel()) && secretLevelEnable) {
                    fileName = file.getName().substring(0, length - extension.length()) + separator_code + secretLevelItem.getTitle() + extension;
                }
                if (null == databytes || databytes.length <= 0) continue;
                byte[] bufs = new byte[0x6400000];
                zos.putNextEntry(new ZipEntry(fileName));
                ByteArrayInputStream swapStream = new ByteArrayInputStream(databytes);
                Throwable throwable2 = null;
                try {
                    BufferedInputStream bis = new BufferedInputStream(swapStream, 10240);
                    Throwable throwable3 = null;
                    try {
                        int read = 0;
                        while ((read = bis.read(bufs, 0, 10240)) != -1) {
                            zos.write(bufs, 0, read);
                        }
                    }
                    catch (Throwable throwable4) {
                        throwable3 = throwable4;
                        throw throwable4;
                    }
                    finally {
                        if (bis == null) continue;
                        if (throwable3 != null) {
                            try {
                                bis.close();
                            }
                            catch (Throwable throwable5) {
                                throwable3.addSuppressed(throwable5);
                            }
                            continue;
                        }
                        bis.close();
                    }
                }
                catch (Throwable throwable6) {
                    throwable2 = throwable6;
                    throw throwable6;
                }
                finally {
                    if (swapStream == null) continue;
                    if (throwable2 != null) {
                        try {
                            swapStream.close();
                        }
                        catch (Throwable throwable7) {
                            throwable2.addSuppressed(throwable7);
                        }
                        continue;
                    }
                    swapStream.close();
                }
            }
            String zipName = "\u9644\u4ef6.zip";
            SecretLevel enclosureSecert = null;
            for (FileInfo file : files) {
                SecretLevel secretLevelItem = this.stateSecretLevelService.getSecretLevelItem(file.getSecretlevel());
                if (!StringUtils.isNotEmpty((String)file.getSecretlevel()) || !secretLevelEnable || null != enclosureSecert && !this.stateSecretLevelService.compareSercetLevel(secretLevelItem, enclosureSecert)) continue;
                enclosureSecert = secretLevelItem;
            }
            if (null != enclosureSecert && secretLevelEnable) {
                String secretTitle = enclosureSecert.getTitle();
                zipName = "\u9644\u4ef6" + separator_code + secretTitle + ".zip";
            }
            logHellperUtil.info(taskDefine.getKey(), logDimensionCollection, "\u9644\u4ef6\u4e0b\u8f7d\u6210\u529f", "\u9644\u4ef6\u4e0b\u8f7d\u6210\u529f");
            String string = zipName;
            return string;
        }
        catch (Exception e) {
            logHellperUtil.error(taskDefine.getKey(), logDimensionCollection, "\u9644\u4ef6\u4e0b\u8f7d\u5931\u8d25", PromptConsts.fileOprateSystemError(e.getMessage()));
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            return "error";
        }
    }

    @Override
    public byte[] downloadFile(String fileKey, CommonParamsDTO params) {
        FileBucketNameParam param = new FileBucketNameParam(params.getDataSchemeKey());
        return this.attachmentFileAreaService.download(param, fileKey);
    }

    @Override
    public void downloadFile(String fileKey, OutputStream outputStream, CommonParamsDTO params) {
        FileBucketNameParam param = new FileBucketNameParam(params.getDataSchemeKey());
        this.attachmentFileAreaService.download(param, fileKey, outputStream);
    }

    private List<FileInfo> getFiles(DownLoadFileInfo downLoadFileInfo, FileBucketNameParam param) {
        ArrayList<FileInfo> fileInfos = new ArrayList<FileInfo>();
        for (String fileKey : downLoadFileInfo.getFileKeys()) {
            FileInfo fileInfo = this.attachmentFileAreaService.getFileInfo(param, fileKey);
            if (fileInfo == null) continue;
            fileInfos.add(fileInfo);
        }
        boolean secretLevelEnable = this.stateSecretLevelService.secretLevelEnable(downLoadFileInfo.getTask());
        if (secretLevelEnable) {
            ArrayList<FileInfo> returnFileInfos = new ArrayList<FileInfo>();
            DimensionCombination dimensionCombination = downLoadFileInfo.getDimensionCombination();
            DimensionValueSet dimension = dimensionCombination.toDimensionValueSet();
            SecretLevel secretLevel = this.stateSecretLevelService.getSecretLevel(dimension, downLoadFileInfo.getFormscheme());
            for (FileInfo file : fileInfos) {
                boolean canAccess = true;
                if (StringUtils.isNotEmpty((String)file.getSecretlevel()) && secretLevel != null) {
                    SecretLevel secretLevelItem = this.stateSecretLevelService.getSecretLevelItem(file.getSecretlevel());
                    boolean bl = canAccess = this.stateSecretLevelService.compareSercetLevel(secretLevel, secretLevelItem) && this.stateSecretLevelService.canAccess(secretLevelItem);
                }
                if (!canAccess) continue;
                returnFileInfos.add(file);
            }
            return returnFileInfos;
        }
        return fileInfos;
    }

    @Override
    public void downLoadThumbnail(String fileKey, OutputStream outputStream, CommonParamsDTO params) {
        String groupKey = fileKey;
        FileInfo thumbnailFileInfo = null;
        List<FileInfo> fileInfoByGroup = this.getFileInfoByGroup(groupKey, params);
        Iterator<FileInfo> iterator = fileInfoByGroup.iterator();
        while (iterator.hasNext()) {
            FileInfo info;
            thumbnailFileInfo = info = iterator.next();
        }
        if (null != thumbnailFileInfo) {
            FileBucketNameParam param = new FileBucketNameParam(params.getDataSchemeKey());
            this.attachmentFileAreaService.download(param, thumbnailFileInfo.getKey(), outputStream);
        }
    }

    @Override
    public String getFilePath(String fileKey, String tenant, CommonParamsDTO params) {
        FileBucketNameParam param = new FileBucketNameParam(params.getDataSchemeKey());
        return this.attachmentFileAreaService.getPath(param, fileKey, tenant);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public FileBatchOperationService getBatchOperationService(DimensionCombination dimensionCombination) {
        Map<DimensionCombination, FileBatchOperationService> map = this.fileBatchOperationServiceMap;
        synchronized (map) {
            FileBatchOperationService fileBatchOperationService = this.fileBatchOperationServiceMap.get(dimensionCombination);
            if (null != fileBatchOperationService) {
                return fileBatchOperationService;
            }
            fileBatchOperationService = new FileBatchOperationServiceImpl(this, dimensionCombination);
            this.fileBatchOperationServiceMap.put(dimensionCombination, fileBatchOperationService);
            return fileBatchOperationService;
        }
    }

    @Override
    public List<FileInfo> getFileInfoByGroup(String groupKey) {
        return this.getFileInfoByGroup(groupKey, new CommonParamsDTO());
    }

    @Override
    public List<FileInfo> getFileInfoByGroup(String groupKey, CommonParamsDTO params) {
        FileBucketNameParam param = new FileBucketNameParam(params.getDataSchemeKey());
        return this.attachmentFileAreaService.getFileInfoByProp(param, "fileGroupKey", groupKey);
    }

    @Override
    public List<FileInfo> getFileOrPicInfoByGroup(String groupKey, CommonParamsDTO params) {
        List<FileInfo> fileInfos = this.filePoolService.getFileInfoByGroup(groupKey, params);
        List fileKeys = fileInfos.stream().map(FileInfo::getKey).collect(Collectors.toList());
        List<FileInfo> fileInfoByGroup = this.getFileInfoByGroup(groupKey, params);
        for (FileInfo fileInfo : fileInfoByGroup) {
            if (fileKeys.contains(fileInfo.getKey())) continue;
            fileInfos.add(fileInfo);
        }
        return fileInfos;
    }

    @Override
    public FileInfo getFileInfoByKey(String fileKey) {
        return this.getFileInfoByKey(fileKey, new CommonParamsDTO());
    }

    @Override
    public FileInfo getFileInfoByKey(String fileKey, CommonParamsDTO params) {
        FileBucketNameParam param = new FileBucketNameParam(params.getDataSchemeKey());
        return this.attachmentFileAreaService.getFileInfo(param, fileKey);
    }

    @Override
    public List<FileInfo> getFileInfoByKeys(List<String> fileKeys, CommonParamsDTO params) {
        FileBucketNameParam param = new FileBucketNameParam(params.getDataSchemeKey());
        return this.attachmentFileAreaService.getFileInfoByKeys(param, fileKeys);
    }

    @Override
    public String getCurrentSplitTableName(DataScheme dataScheme) {
        String tableName = "NR_FILE_" + dataScheme.getBizCode();
        if (null != this.subTableNamesProvider) {
            List allTaskDefines = this.iRunTimeViewController.getAllTaskDefines();
            for (TaskDefine taskDefine : allTaskDefines) {
                String libraryTableName;
                if (!taskDefine.getDataScheme().equals(dataScheme.getKey()) || (libraryTableName = this.subTableNamesProvider.getSubDatabaseTableName(taskDefine.getKey(), tableName)).isEmpty()) continue;
                return libraryTableName;
            }
        }
        return tableName;
    }

    @Override
    public List<String> existFile(List<String> fileKeys, CommonParamsDTO params) {
        FileBucketNameParam param = new FileBucketNameParam(params.getDataSchemeKey());
        return this.attachmentFileAreaService.existFile(param, fileKeys);
    }

    @Override
    public List<FileInfo> existFile(List<FileInfo> fileInfos, String taskKey, DimensionValueSet dimensionValueSet, String formKey, String fieldKey, String groupKey) {
        TaskDefine taskDefine = this.iRunTimeViewController.queryTaskDefine(taskKey);
        FileBucketNameParam param = new FileBucketNameParam(taskDefine.getDataScheme());
        List<String> fileKeys = fileInfos.stream().map(FileInfo::getKey).collect(Collectors.toList());
        List<String> noExistFileKeys = this.attachmentFileAreaService.existFile(param, fileKeys);
        ArrayList<FileInfo> existFileInfos = new ArrayList<FileInfo>();
        if (null != noExistFileKeys && !noExistFileKeys.isEmpty()) {
            LogHellperUtil logHellperUtil = new LogHellperUtil(this.dataServiceLoggerFactory, MOUDLE);
            String dwDimensionName = this.entityMetaService.getDimensionName(taskDefine.getDw());
            IPeriodEntityAdapter periodAdapter = this.periodEngineService.getPeriodAdapter();
            String periodDimensionName = periodAdapter.getPeriodDimensionName();
            String targetKey = String.valueOf(dimensionValueSet.getValue(dwDimensionName));
            String periodCode = String.valueOf(dimensionValueSet.getValue(periodDimensionName));
            LogDimensionCollection logDimensionCollection = new LogDimensionCollection();
            logDimensionCollection.setDw(taskDefine.getDw(), new String[]{targetKey});
            logDimensionCollection.setPeriod(taskDefine.getDateTime(), periodCode);
            StringBuilder noExistFileKeyBuilder = new StringBuilder();
            noExistFileKeyBuilder.append("\u62a5\u8868\uff1a").append(formKey).append("\uff0c");
            noExistFileKeyBuilder.append("\u6307\u6807\uff1a").append(fieldKey).append("\uff0c");
            noExistFileKeyBuilder.append("groupKey\uff1a").append(groupKey).append("\uff0c");
            noExistFileKeyBuilder.append("\u5df2\u5220\u9664\u9644\u4ef6\uff1a");
            for (String noExistFileKey : noExistFileKeys) {
                noExistFileKeyBuilder.append(noExistFileKey).append("\uff0c");
            }
            noExistFileKeyBuilder.delete(noExistFileKeyBuilder.length() - 1, noExistFileKeyBuilder.length());
            logHellperUtil.error(taskKey, logDimensionCollection, "\u6279\u91cf\u9644\u4ef6\u4e0b\u8f7d", "\u5b58\u5728\u5df2\u5220\u9664\u9644\u4ef6\uff0c" + noExistFileKeyBuilder.toString());
            for (FileInfo fileInfo : fileInfos) {
                if (noExistFileKeys.contains(fileInfo.getKey())) continue;
                existFileInfos.add(fileInfo);
            }
            return existFileInfos;
        }
        return fileInfos;
    }
}

