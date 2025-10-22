/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.DataRow
 *  com.jiuqi.bi.dataset.MemoryDataSet
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.cache.internal.redis.IRedisLock
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.definition.common.FieldType
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.nr.common.exception.NotFoundTableDefineException
 *  com.jiuqi.nr.common.util.DataEngineAdapter
 *  com.jiuqi.nr.common.util.DimensionChanger
 *  com.jiuqi.nr.data.access.api.IStateSecretLevelService
 *  com.jiuqi.nr.datascheme.api.DataScheme
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.dataservice.core.log.DataServiceLoggerFactory
 *  com.jiuqi.nr.dataservice.core.log.LogDimensionCollection
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
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
 *  com.jiuqi.nvwa.dataengine.util.DataEngineUtil
 *  com.jiuqi.nvwa.definition.common.NrdbHelper
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  net.coobird.thumbnailator.Thumbnails
 *  org.jetbrains.annotations.NotNull
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.jdbc.datasource.DataSourceUtils
 */
package com.jiuqi.nr.attachment.service.impl;

import com.jiuqi.bi.dataset.DataRow;
import com.jiuqi.bi.dataset.MemoryDataSet;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.cache.internal.redis.IRedisLock;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.definition.common.FieldType;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.nr.attachment.input.AombstoneFileInfo;
import com.jiuqi.nr.attachment.input.CommonParamsDTO;
import com.jiuqi.nr.attachment.input.DeleteMarkFileInfo;
import com.jiuqi.nr.attachment.input.FileRelateGroupInfo;
import com.jiuqi.nr.attachment.input.FileUploadByGroupKeyContext;
import com.jiuqi.nr.attachment.input.FileUploadInfo;
import com.jiuqi.nr.attachment.listener.IFileListener;
import com.jiuqi.nr.attachment.listener.IFileListenerProvider;
import com.jiuqi.nr.attachment.listener.param.FileBatchDelEvent;
import com.jiuqi.nr.attachment.message.FileInfo;
import com.jiuqi.nr.attachment.output.FailedFileInfo;
import com.jiuqi.nr.attachment.output.FileImportResult;
import com.jiuqi.nr.attachment.output.ReferencesInfo;
import com.jiuqi.nr.attachment.provider.param.FileBucketNameParam;
import com.jiuqi.nr.attachment.service.AttachmentIOService;
import com.jiuqi.nr.attachment.service.FileOperationService;
import com.jiuqi.nr.attachment.service.FilePoolService;
import com.jiuqi.nr.attachment.tools.AttachmentFileAreaService;
import com.jiuqi.nr.attachment.utils.FileOperationUtils;
import com.jiuqi.nr.attachment.utils.LogHellperUtil;
import com.jiuqi.nr.common.exception.NotFoundTableDefineException;
import com.jiuqi.nr.common.util.DataEngineAdapter;
import com.jiuqi.nr.common.util.DimensionChanger;
import com.jiuqi.nr.data.access.api.IStateSecretLevelService;
import com.jiuqi.nr.datascheme.api.DataScheme;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.dataservice.core.log.DataServiceLoggerFactory;
import com.jiuqi.nr.dataservice.core.log.LogDimensionCollection;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
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
import com.jiuqi.nvwa.dataengine.util.DataEngineUtil;
import com.jiuqi.nvwa.definition.common.NrdbHelper;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.sql.DataSource;
import net.coobird.thumbnailator.Thumbnails;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Service;

@Service
public class AttachmentIOServiceImpl
implements AttachmentIOService {
    private static final Logger logger = LoggerFactory.getLogger(AttachmentIOServiceImpl.class);
    @Autowired
    private FileOperationService fileOperationService;
    @Autowired
    private IRunTimeViewController iRunTimeViewController;
    @Autowired
    private CheckSensitiveWordService checkSensitiveWordService;
    @Autowired
    private AttachmentFileAreaService attachmentFileAreaService;
    @Autowired
    private FilePoolService filePoolService;
    @Autowired
    private IRuntimeDataSchemeService runtimeDataSchemeService;
    @Autowired
    private DataModelService dataModelService;
    @Autowired
    private INvwaDataAccessProvider iNvwaDataAccessProvider;
    @Autowired
    private DataEngineAdapter dataEngineAdapter;
    @Autowired
    private IRedisLock loca;
    @Autowired
    private DataServiceLoggerFactory dataServiceLoggerFactory;
    @Autowired
    private PeriodEngineService periodEngineService;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private CheckUploadFileService checkUploadFileService;
    @Autowired
    private IStateSecretLevelService stateSecretLevelService;
    @Autowired
    private NrdbHelper nrdbHelper;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired(required=false)
    private List<IFileListenerProvider> fileListenerProviders;
    private static final String MOUDLE = "\u9644\u4ef6\u5bfc\u5165\u670d\u52a1";
    public static final String LOCAK_NAME = "delete_mark_file_";

    @Override
    public FileImportResult uploadByGroup(FileUploadByGroupKeyContext fileUploadByGroupKeyContext) {
        FieldDefine fieldDefine = null;
        try {
            fieldDefine = this.iRunTimeViewController.queryFieldDefine(fileUploadByGroupKeyContext.getFieldKey());
            if (null == fieldDefine) {
                throw new Exception("\u6307\u6807\u4e3a\u7a7a");
            }
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
        ArrayList<String> fileKeys = new ArrayList<String>();
        FileImportResult fileImportResult = this.upload(fileUploadByGroupKeyContext, fileKeys, fieldDefine);
        try {
            if (!fileKeys.isEmpty()) {
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
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            fileImportResult.setSuccess(false);
        }
        return fileImportResult;
    }

    private FileImportResult upload(FileUploadByGroupKeyContext fileUploadByGroupKeyContext, List<String> fileKeys, FieldDefine fieldDefine) {
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
        FileImportResult fileImportResult = new FileImportResult();
        fileImportResult.setSuccess(true);
        ArrayList<FailedFileInfo> failedFileInfoList = new ArrayList<FailedFileInfo>();
        fileImportResult.setFailedFileInfoList(failedFileInfoList);
        FileBucketNameParam param = new FileBucketNameParam(fileUploadByGroupKeyContext.getDataSchemeKey());
        String groupKey = fileUploadByGroupKeyContext.getGroupKey();
        List<FileUploadInfo> fileUploadInfos = fileUploadByGroupKeyContext.getFileUploadInfos();
        this.delSameKeyFile(param, fileUploadInfos);
        boolean isOpenSecretLevel = this.stateSecretLevelService.secretLevelEnable(fileUploadByGroupKeyContext.getTaskKey());
        boolean openFileCategory = this.filePoolService.isOpenFileCategory();
        try {
            for (FileUploadInfo fileUploadInfo : fileUploadInfos) {
                String fileName = fileUploadInfo.getName();
                if (this.checkFileType(fileUploadByGroupKeyContext, fieldDefine, taskDefine, logHellperUtil, targetKey, periodCode, logDimensionCollection, fileImportResult, failedFileInfoList, fileUploadInfo, fileName = fileName.trim().replace("\n", "")) || this.checkFileName(taskDefine, logHellperUtil, logDimensionCollection, fileImportResult, failedFileInfoList, fileUploadInfo, fileName)) continue;
                byte[] picBytes = null;
                if (null != fieldDefine && fieldDefine.getType().equals((Object)FieldType.FIELD_TYPE_PICTURE)) {
                    picBytes = FileOperationUtils.convertByteArray(fileUploadInfo.getFile());
                    fileUploadInfo.getFile().close();
                    ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(picBytes);
                    fileUploadInfo.setFile(byteArrayInputStream);
                }
                HashMap<String, String> expandInfo = new HashMap<String, String>();
                expandInfo.put("fileGroupKey", groupKey);
                if (isOpenSecretLevel && StringUtils.isNotEmpty((String)fileUploadInfo.getFileSecret())) {
                    expandInfo.put("secretlevel", fileUploadInfo.getFileSecret());
                }
                if (openFileCategory && StringUtils.isNotEmpty((String)fileUploadInfo.getCategory())) {
                    expandInfo.put("category", fileUploadInfo.getCategory());
                }
                FileInfo fileInfo = fileUploadInfo.isJioImportAttachFileKey() ? this.attachmentFileAreaService.uploadByKey(param, fileName, fileUploadInfo.getFileKey(), fileUploadInfo.getFile(), expandInfo) : this.attachmentFileAreaService.upload(param, fileName, fileUploadInfo.getFile(), expandInfo);
                if (null != fieldDefine && fieldDefine.getType().equals((Object)FieldType.FIELD_TYPE_FILE)) {
                    fileKeys.add(fileInfo.getKey());
                    continue;
                }
                if (null == fieldDefine || !fieldDefine.getType().equals((Object)FieldType.FIELD_TYPE_PICTURE) || null == picBytes) continue;
                this.uploadThumbnail(param, fileUploadInfo, picBytes, fileInfo);
            }
            logHellperUtil.info(taskDefine.getKey(), logDimensionCollection, "\u9644\u4ef6\u5bfc\u5165\u6210\u529f", "\u9644\u4ef6\u5bfc\u5165\u6210\u529f");
        }
        catch (IOException e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            logHellperUtil.error(taskDefine.getKey(), logDimensionCollection, "\u9644\u4ef6\u5bfc\u5165\u5931\u8d25", "\u7cfb\u7edf\u9519\u8bef\uff1a" + e.getMessage());
            fileImportResult.setSuccess(false);
            fileImportResult.setErrorMsg(e.getMessage());
        }
        return fileImportResult;
    }

    private void uploadThumbnail(FileBucketNameParam param, FileUploadInfo fileUploadInfo, byte[] picBytes, FileInfo fileInfo) throws IOException {
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
                }
                catch (Throwable thumbnailExpandInfo2) {
                    throwable = thumbnailExpandInfo2;
                    throw thumbnailExpandInfo2;
                }
                finally {
                    if (smallInputStream != null) {
                        if (throwable != null) {
                            try {
                                ((InputStream)smallInputStream).close();
                            }
                            catch (Throwable thumbnailExpandInfo2) {
                                throwable.addSuppressed(thumbnailExpandInfo2);
                            }
                        } else {
                            ((InputStream)smallInputStream).close();
                        }
                    }
                }
            } else {
                smallInputStream = new ByteArrayInputStream(picBytes);
                throwable = null;
                try {
                    thumbnailExpandInfo = new HashMap<String, String>();
                    thumbnailExpandInfo.put("fileGroupKey", fileInfo.getKey());
                    this.attachmentFileAreaService.upload(param, fileName, smallInputStream, thumbnailExpandInfo);
                }
                catch (Throwable throwable2) {
                    throwable = throwable2;
                    throw throwable2;
                }
                finally {
                    if (smallInputStream != null) {
                        if (throwable != null) {
                            try {
                                ((InputStream)smallInputStream).close();
                            }
                            catch (Throwable throwable3) {
                                throwable.addSuppressed(throwable3);
                            }
                        } else {
                            ((InputStream)smallInputStream).close();
                        }
                    }
                }
            }
        } else {
            try (ByteArrayInputStream smallInputStream = new ByteArrayInputStream(picBytes);){
                HashMap<String, String> expandInfo = new HashMap<String, String>();
                expandInfo.put("fileGroupKey", fileInfo.getKey());
                this.attachmentFileAreaService.upload(param, fileName, smallInputStream, expandInfo);
            }
        }
    }

    private boolean checkFileName(TaskDefine taskDefine, LogHellperUtil logHellperUtil, LogDimensionCollection logDimensionCollection, FileImportResult fileImportResult, List<FailedFileInfo> failedFileInfoList, FileUploadInfo fileUploadInfo, String fileName) {
        String fileNameNoSuffix = "";
        int fileNameIndex = fileName.lastIndexOf(".");
        fileNameNoSuffix = fileNameIndex >= 0 ? fileName.substring(0, fileNameIndex) : fileName;
        List sensitiveWordList = this.checkSensitiveWordService.thisWordIsSensitiveWord(fileNameNoSuffix);
        if (sensitiveWordList.size() > 0) {
            String errMsg = "\u6587\u4ef6\u540d\u5305\u542b\u654f\u611f\u8bcd\u4fe1\u606f";
            StringBuilder msg = new StringBuilder();
            msg.append(errMsg).append(",");
            for (SensitiveWordDaoObject sensitiveWordDaoObject : sensitiveWordList) {
                if (sensitiveWordDaoObject.getSensitiveDescription() == null || sensitiveWordDaoObject.getSensitiveDescription().length() <= 0) continue;
                msg.append(sensitiveWordDaoObject.getSensitiveDescription());
                msg.append(";");
            }
            msg.append("\u8bf7\u4fee\u6539\u540e\u4e0a\u4f20\uff01");
            logger.error("\u9644\u4ef6\u5bfc\u5165\u670d\u52a1\uff0c\u51fa\u9519\u539f\u56e0\uff1a" + msg);
            logHellperUtil.error(taskDefine.getKey(), logDimensionCollection, "\u9644\u4ef6\u5bfc\u5165\u5931\u8d25", "\u4e1a\u52a1\u9519\u8bef\uff1a" + msg);
            fileImportResult.setSuccess(false);
            FailedFileInfo failedFileInfo = new FailedFileInfo(fileUploadInfo.getFileKey(), fileName, errMsg, msg.toString());
            failedFileInfoList.add(failedFileInfo);
            return true;
        }
        return false;
    }

    private boolean checkFileType(FileUploadByGroupKeyContext fileUploadByGroupKeyContext, FieldDefine fieldDefine, TaskDefine taskDefine, LogHellperUtil logHellperUtil, String targetKey, String periodCode, LogDimensionCollection logDimensionCollection, FileImportResult fileImportResult, List<FailedFileInfo> failedFileInfoList, FileUploadInfo fileUploadInfo, String fileName) {
        FileUploadReturnInfo fileUploadReturnInfo = this.checkUploadFileService.checkFileInfo(fileName, Long.valueOf(fileUploadInfo.getSize()), null, null);
        if (StringUtils.isNotEmpty((String)fileUploadReturnInfo.getErrorType())) {
            StringBuilder msg = new StringBuilder();
            msg.append(fileUploadReturnInfo.getMessage());
            msg.append(" \u6307\u6807key\uff1a").append(fileUploadByGroupKeyContext.getFieldKey());
            msg.append("\uff0c\u6307\u6807title\uff1a").append(null != fieldDefine ? fieldDefine.getTitle() : null);
            msg.append("\uff0c\u5355\u4f4dcode\uff1a").append(targetKey);
            msg.append("\uff0c\u65f6\u671fcode\uff1a").append(periodCode);
            msg.append("\uff0c\u9644\u4ef6\u540d\u79f0\uff1a").append(fileName);
            logger.error("\u9644\u4ef6\u5bfc\u5165\u670d\u52a1\uff0c\u51fa\u9519\u539f\u56e0\uff1a" + msg.toString());
            logHellperUtil.error(taskDefine.getKey(), logDimensionCollection, "\u9644\u4ef6\u5bfc\u5165\u5931\u8d25", "\u4e1a\u52a1\u9519\u8bef\uff1a" + msg.toString());
            fileImportResult.setSuccess(false);
            FailedFileInfo failedFileInfo = new FailedFileInfo(fileUploadInfo.getFileKey(), fileName, fileUploadReturnInfo.getMessage(), msg.toString());
            failedFileInfoList.add(failedFileInfo);
            return true;
        }
        return false;
    }

    private void delSameKeyFile(FileBucketNameParam param, List<FileUploadInfo> fileUploadInfos) {
        ArrayList<String> oldFileKeys = new ArrayList<String>();
        for (FileUploadInfo fileUploadInfo : fileUploadInfos) {
            String oldFileKey;
            if (!fileUploadInfo.isJioImportAttachFileKey() || !StringUtils.isNotEmpty((String)(oldFileKey = fileUploadInfo.getFileKey()))) continue;
            oldFileKeys.add(oldFileKey);
        }
        if (!oldFileKeys.isEmpty()) {
            List<String> noExistFileKeys = this.attachmentFileAreaService.existFile(param, oldFileKeys, 1);
            oldFileKeys.removeAll(noExistFileKeys);
            this.attachmentFileAreaService.batchDelete(param, oldFileKeys);
        }
    }

    @Override
    public List<FileInfo> getFileByGroup(String groupKey, String dataSchemeKey) {
        CommonParamsDTO params = new CommonParamsDTO();
        params.setDataSchemeKey(dataSchemeKey);
        return this.getFileByGroup(groupKey, params);
    }

    @Override
    public List<FileInfo> getFileByGroup(String groupKey, CommonParamsDTO params) {
        DataScheme dataScheme = this.runtimeDataSchemeService.getDataScheme(params.getDataSchemeKey());
        String tableName = this.fileOperationService.getCurrentSplitTableName(dataScheme);
        TableModelDefine filePoolTable = this.dataModelService.getTableModelDefineByName(tableName);
        if (null == filePoolTable) {
            throw new NotFoundTableDefineException(new String[]{"\u9644\u4ef6\u5173\u8054\u8868\u672a\u521b\u5efa\uff0c\u8bf7\u91cd\u65b0\u53d1\u5e03\u6570\u636e\u65b9\u6848\uff01"});
        }
        ArrayList<String> fileKeys = new ArrayList<String>();
        List filePoolFields = this.dataModelService.getColumnModelDefinesByTable(filePoolTable.getID());
        DataAccessContext context = new DataAccessContext(this.dataModelService);
        NvwaQueryModel queryModel = new NvwaQueryModel();
        for (ColumnModelDefine filePoolField : filePoolFields) {
            queryModel.getColumns().add(new NvwaQueryColumn(filePoolField));
            if (filePoolField.getCode().equals("GROUPKEY")) {
                queryModel.getColumnFilters().put(filePoolField, groupKey);
                continue;
            }
            if (!filePoolField.getCode().equals("ISDELETE")) continue;
            queryModel.getColumnFilters().put(filePoolField, "0");
        }
        INvwaDataAccess readOnlyDataAccess = this.iNvwaDataAccessProvider.createReadOnlyDataAccess(queryModel);
        MemoryDataSet dataTable = null;
        try {
            dataTable = readOnlyDataAccess.executeQuery(context);
            List columns = queryModel.getColumns();
            for (int i = 0; i < dataTable.size(); ++i) {
                DataRow item = dataTable.get(i);
                for (int j = 0; j < columns.size(); ++j) {
                    if (!((NvwaQueryColumn)columns.get(j)).getColumnModel().getCode().equals("FILEKEY")) continue;
                    String fileKey = item.getString(j);
                    fileKeys.add(fileKey);
                }
            }
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
        FileBucketNameParam param = new FileBucketNameParam(params.getDataSchemeKey());
        return this.attachmentFileAreaService.getFileInfoByKeys(param, fileKeys);
    }

    @Override
    public List<FileInfo> getFileByGroup(String groupKey, String fieldKey, CommonParamsDTO params) {
        FieldDefine fieldDefine = null;
        try {
            fieldDefine = this.iRunTimeViewController.queryFieldDefine(fieldKey);
            if (null == fieldDefine) {
                throw new Exception("\u6307\u6807\u4e3a\u7a7a");
            }
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
        if (null == fieldDefine || fieldDefine.getType().equals((Object)FieldType.FIELD_TYPE_FILE)) {
            return this.getFileByGroup(groupKey, params);
        }
        if (fieldDefine.getType().equals((Object)FieldType.FIELD_TYPE_PICTURE)) {
            return this.fileOperationService.getFileInfoByGroup(groupKey, params);
        }
        return Collections.emptyList();
    }

    @Override
    public byte[] download(String fileKey) {
        return this.download(fileKey, new CommonParamsDTO());
    }

    @Override
    public byte[] download(String fileKey, CommonParamsDTO params) {
        LogHellperUtil logHellperUtil = new LogHellperUtil(this.dataServiceLoggerFactory, MOUDLE);
        FileBucketNameParam param = new FileBucketNameParam(params.getDataSchemeKey());
        byte[] bytes = this.attachmentFileAreaService.download(param, fileKey);
        logHellperUtil.info(params.getTaskKey(), null, "\u9644\u4ef6\u4e0b\u8f7d\u6210\u529f", "\u9644\u4ef6\u4e0b\u8f7d\u6210\u529f");
        return bytes;
    }

    @Override
    public void download(String fileKey, OutputStream outputStream) {
        this.download(fileKey, new CommonParamsDTO(), outputStream);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void download(String fileKey, CommonParamsDTO params, OutputStream outputStream) {
        LogHellperUtil logHellperUtil = new LogHellperUtil(this.dataServiceLoggerFactory, MOUDLE);
        try {
            FileBucketNameParam param = new FileBucketNameParam(params.getDataSchemeKey());
            this.attachmentFileAreaService.download(param, fileKey, outputStream);
        }
        finally {
            logHellperUtil.info(params.getTaskKey(), null, "\u9644\u4ef6\u4e0b\u8f7d\u7ed3\u675f", "\u9644\u4ef6\u4e0b\u8f7d\u7ed3\u675f");
        }
    }

    @Override
    public void markDeletion(String dataSchemeKey, DimensionCombination dimensionCombination, String fieldKey) {
        DataScheme dataScheme = this.runtimeDataSchemeService.getDataScheme(dataSchemeKey);
        String tableName = this.fileOperationService.getCurrentSplitTableName(dataScheme);
        TableModelDefine filePoolTable = this.dataModelService.getTableModelDefineByName(tableName);
        DimensionChanger dimensionChanger = this.dataEngineAdapter.getDimensionChanger(tableName);
        if (null == filePoolTable) {
            throw new NotFoundTableDefineException(new String[]{"\u9644\u4ef6\u5173\u8054\u8868\u672a\u521b\u5efa\uff0c\u8bf7\u91cd\u65b0\u53d1\u5e03\u6570\u636e\u65b9\u6848\uff01"});
        }
        DimensionValueSet dimension = dimensionCombination.toDimensionValueSet();
        DataAccessContext context = new DataAccessContext(this.dataModelService);
        NvwaQueryModel queryModel = new NvwaQueryModel();
        List filePoolFields = this.dataModelService.getColumnModelDefinesByTable(filePoolTable.getID());
        HashMap<ColumnModelDefine, String> keyValue = new HashMap<ColumnModelDefine, String>();
        for (ColumnModelDefine filePoolField : filePoolFields) {
            String dimensionName;
            if (filePoolField.getCode().equals("FIELD_KEY")) {
                keyValue.put(filePoolField, fieldKey);
                continue;
            }
            if (filePoolField.getCode().equals("ISDELETE")) {
                queryModel.getColumns().add(new NvwaQueryColumn(filePoolField));
                continue;
            }
            if (filePoolField.getCode().equals("FIELD_KEY") || filePoolField.getCode().equals("ID") || filePoolField.getCode().equals("GROUPKEY") || filePoolField.getCode().equals("FILEKEY") || filePoolField.getCode().equals("ISDELETE") || null == dimension.getValue(dimensionName = dimensionChanger.getDimensionName(filePoolField))) continue;
            keyValue.put(filePoolField, dimension.getValue(dimensionName).toString());
        }
        INvwaUpdatableDataAccess updatableDataAccess = this.iNvwaDataAccessProvider.createUpdatableDataAccess(queryModel);
        try {
            INvwaDataUpdator iNvwaDataUpdator = updatableDataAccess.openForUpdate(context);
            INvwaDataRow iNvwaDataRow = iNvwaDataUpdator.addUpdateRow();
            iNvwaDataRow.setValue(0, (Object)"1");
            keyValue.forEach((arg_0, arg_1) -> ((INvwaDataRow)iNvwaDataRow).setKeyValue(arg_0, arg_1));
            iNvwaDataUpdator.commitChanges(context);
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
    }

    @Override
    public void batchMarkDeletion(String dataSchemeKey, DimensionCollection dimensionCollection, String fieldKey) {
        logger.info("\u6279\u91cf\u6807\u8bb0\u5220\u9664\u5f00\u59cb");
        DataScheme dataScheme = this.runtimeDataSchemeService.getDataScheme(dataSchemeKey);
        String tableName = this.fileOperationService.getCurrentSplitTableName(dataScheme);
        TableModelDefine filePoolTable = this.dataModelService.getTableModelDefineByName(tableName);
        if (null == filePoolTable) {
            throw new NotFoundTableDefineException(new String[]{"\u9644\u4ef6\u5173\u8054\u8868\u672a\u521b\u5efa\uff0c\u8bf7\u91cd\u65b0\u53d1\u5e03\u6570\u636e\u65b9\u6848\uff01"});
        }
        List filePoolFields = this.dataModelService.getColumnModelDefinesByTable(filePoolTable.getID());
        DimensionChanger dimensionChanger = this.dataEngineAdapter.getDimensionChanger(tableName);
        List dimensionCombinations = dimensionCollection.getDimensionCombinations();
        boolean enableNrdb = this.nrdbHelper.isEnableNrdb();
        if (enableNrdb) {
            this.markDelByEngine(fieldKey, filePoolFields, dimensionChanger, dimensionCombinations);
        } else {
            this.markDelBySQL(fieldKey, tableName, filePoolFields, dimensionChanger, dimensionCombinations);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void markDelBySQL(String fieldKey, String tableName, List<ColumnModelDefine> filePoolFields, DimensionChanger dimensionChanger, List<DimensionCombination> dimensionCombinations) {
        LinkedHashSet<ColumnModelDefine> columns = new LinkedHashSet<ColumnModelDefine>();
        ArrayList<List<String>> conditionalValueList = new ArrayList<List<String>>();
        for (DimensionCombination dimensionCombination : dimensionCombinations) {
            DimensionValueSet dimension = dimensionCombination.toDimensionValueSet();
            ArrayList<String> values = new ArrayList<String>();
            for (ColumnModelDefine filePoolField : filePoolFields) {
                String dimensionName;
                if (filePoolField.getCode().equals("FIELD_KEY")) {
                    columns.add(filePoolField);
                    values.add(fieldKey);
                    continue;
                }
                if (filePoolField.getCode().equals("FIELD_KEY") || filePoolField.getCode().equals("ID") || filePoolField.getCode().equals("GROUPKEY") || filePoolField.getCode().equals("FILEKEY") || filePoolField.getCode().equals("ISDELETE") || null == dimension.getValue(dimensionName = dimensionChanger.getDimensionName(filePoolField))) continue;
                columns.add(filePoolField);
                values.add(dimension.getValue(dimensionName).toString());
            }
            conditionalValueList.add(values);
        }
        logger.info("\u6279\u91cf\u6807\u8bb0\u5220\u9664\u8fdb\u884c\u4e2d\uff0c\u6761\u4ef6\u7ec4\u5408\u6570\uff1a" + conditionalValueList.size());
        Connection conn = null;
        try {
            conn = DataSourceUtils.getConnection((DataSource)this.jdbcTemplate.getDataSource());
            this.executeSql(conn, tableName, columns, conditionalValueList, true);
            logger.info("\u6279\u91cf\u6807\u8bb0\u5220\u9664\u5b8c\u6210");
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
        finally {
            if (null != conn) {
                DataSourceUtils.releaseConnection((Connection)conn, (DataSource)this.jdbcTemplate.getDataSource());
            }
        }
    }

    private void markDelByEngine(String fieldKey, List<ColumnModelDefine> filePoolFields, DimensionChanger dimensionChanger, List<DimensionCombination> dimensionCombinations) {
        logger.info("\u6279\u91cf\u6807\u8bb0\u5220\u9664\u8fdb\u884c\u4e2d\uff0csql\u6267\u884c\u6b21\u6570\uff1a" + dimensionCombinations.size());
        try {
            DataAccessContext context = new DataAccessContext(this.dataModelService);
            for (DimensionCombination dimensionCombination : dimensionCombinations) {
                NvwaQueryModel queryModel = new NvwaQueryModel();
                DimensionValueSet dimension = dimensionCombination.toDimensionValueSet();
                for (ColumnModelDefine filePoolField : filePoolFields) {
                    String dimensionName;
                    if (filePoolField.getCode().equals("ISDELETE")) {
                        queryModel.getColumns().add(new NvwaQueryColumn(filePoolField));
                        continue;
                    }
                    if (filePoolField.getCode().equals("FIELD_KEY")) {
                        queryModel.getColumnFilters().put(filePoolField, fieldKey);
                        continue;
                    }
                    if (filePoolField.getCode().equals("FIELD_KEY") || filePoolField.getCode().equals("ID") || filePoolField.getCode().equals("GROUPKEY") || filePoolField.getCode().equals("FILEKEY") || filePoolField.getCode().equals("ISDELETE") || null == dimension.getValue(dimensionName = dimensionChanger.getDimensionName(filePoolField))) continue;
                    queryModel.getColumnFilters().put(filePoolField, dimension.getValue(dimensionName));
                }
                INvwaUpdatableDataAccess updatableDataAccess = this.iNvwaDataAccessProvider.createUpdatableDataAccess(queryModel);
                INvwaUpdatableDataSet iNvwaDataRows = updatableDataAccess.executeQueryForUpdate(context);
                for (int i = 0; i < iNvwaDataRows.size(); ++i) {
                    INvwaDataRow row = iNvwaDataRows.getRow(i);
                    row.setValue(0, (Object)"1");
                }
                iNvwaDataRows.commitChanges(context);
            }
            logger.info("\u6279\u91cf\u6807\u8bb0\u5220\u9664\u5b8c\u6210");
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
    }

    @Override
    public void batchMarkDeletion(String dataSchemeKey, String formSchemeKey, DimensionCollection dimensionCollection, String fieldKey) {
        List dimensionCombinations = dimensionCollection.getDimensionCombinations();
        this.batchMarkDeletion(dataSchemeKey, formSchemeKey, dimensionCombinations, fieldKey);
    }

    @Override
    public void batchMarkDeletion(String dataSchemeKey, String formSchemeKey, List<DimensionCombination> dimensionCombinations, String fieldKey) {
        logger.info("\u6279\u91cf\u6807\u8bb0\u5220\u9664\u5f00\u59cb");
        DataScheme dataScheme = this.runtimeDataSchemeService.getDataScheme(dataSchemeKey);
        String tableName = this.fileOperationService.getCurrentSplitTableName(dataScheme);
        TableModelDefine filePoolTable = this.dataModelService.getTableModelDefineByName(tableName);
        if (null == filePoolTable) {
            throw new NotFoundTableDefineException(new String[]{"\u9644\u4ef6\u5173\u8054\u8868\u672a\u521b\u5efa\uff0c\u8bf7\u91cd\u65b0\u53d1\u5e03\u6570\u636e\u65b9\u6848\uff01"});
        }
        List filePoolFields = this.dataModelService.getColumnModelDefinesByTable(filePoolTable.getID());
        DimensionChanger dimensionChanger = this.dataEngineAdapter.getDimensionChanger(tableName);
        ArrayList<IFileListener> fileListeners = new ArrayList<IFileListener>();
        if (null != this.fileListenerProviders) {
            for (IFileListenerProvider iFileListenerProvider : this.fileListenerProviders) {
                IFileListener fileListener = iFileListenerProvider.getFileListener(null);
                fileListeners.add(fileListener);
            }
        }
        FormSchemeDefine formScheme = this.iRunTimeViewController.getFormScheme(formSchemeKey);
        for (IFileListener fileListener : fileListeners) {
            for (DimensionCombination dimensionCombination : dimensionCombinations) {
                FileBatchDelEvent fileBatchDelEvent = new FileBatchDelEvent(dataSchemeKey, formScheme.getTaskKey(), formScheme.getKey(), dimensionCombination, Arrays.asList(fieldKey), null);
                fileListener.beforeBatchFileDelete(fileBatchDelEvent);
            }
        }
        boolean bl = this.nrdbHelper.isEnableNrdb();
        if (bl) {
            this.markDelByEngine(fieldKey, filePoolFields, dimensionChanger, dimensionCombinations);
        } else {
            this.markDelBySQL(fieldKey, tableName, filePoolFields, dimensionChanger, dimensionCombinations);
        }
        for (IFileListener fileListener : fileListeners) {
            for (DimensionCombination dimensionCombination : dimensionCombinations) {
                FileBatchDelEvent fileBatchDelEvent = new FileBatchDelEvent(dataSchemeKey, formScheme.getTaskKey(), formScheme.getKey(), dimensionCombination, Arrays.asList(fieldKey), null);
                fileListener.afterBatchFileDelete(fileBatchDelEvent);
            }
        }
    }

    private void executeSql(Connection conn, String tableName, LinkedHashSet<ColumnModelDefine> columns, List<List<String>> conditionalValueList, boolean isMark) throws SQLException {
        StringBuilder sql = new StringBuilder();
        sql.append("update ").append(tableName).append(" t set t.").append("ISDELETE");
        if (isMark) {
            sql.append("='1'");
        } else {
            sql.append("='0'");
        }
        sql.append(" where");
        for (ColumnModelDefine column : columns) {
            sql.append(" t.").append(column.getCode()).append("=? and");
        }
        sql.delete(sql.length() - 4, sql.length());
        ArrayList<Object[]> batchValues = new ArrayList<Object[]>();
        for (List<String> conditionalValues : conditionalValueList) {
            Object[] valuess = new Object[conditionalValues.size()];
            int index = 0;
            for (String conditionalValue : conditionalValues) {
                valuess[index++] = conditionalValue;
            }
            batchValues.add(valuess);
        }
        DataEngineUtil.batchUpdate((Connection)conn, (String)sql.toString(), batchValues);
    }

    @Override
    public void batchMarkDeletion(String dataSchemeKey, String formSchemeKey, Set<String> groupKeys) {
        FileBatchDelEvent fileBatchDelEvent;
        ArrayList<IFileListener> fileListeners = new ArrayList<IFileListener>();
        if (null != this.fileListenerProviders) {
            for (IFileListenerProvider fileListenerProvider : this.fileListenerProviders) {
                IFileListener fileListener = fileListenerProvider.getFileListener(null);
                fileListeners.add(fileListener);
            }
        }
        FormSchemeDefine formScheme = this.iRunTimeViewController.getFormScheme(formSchemeKey);
        for (IFileListener fileListener : fileListeners) {
            fileBatchDelEvent = new FileBatchDelEvent(dataSchemeKey, formScheme.getTaskKey(), formScheme.getKey(), null, null, new ArrayList<String>(groupKeys));
            fileListener.beforeBatchFileDelete(fileBatchDelEvent);
        }
        this.filePoolService.markDelFileByGroupKey(dataSchemeKey, groupKeys);
        for (IFileListener fileListener : fileListeners) {
            fileBatchDelEvent = new FileBatchDelEvent(dataSchemeKey, formScheme.getTaskKey(), formScheme.getKey(), null, null, new ArrayList<String>(groupKeys));
            fileListener.afterBatchFileDelete(fileBatchDelEvent);
        }
    }

    @Override
    public void remark(String dataSchemeKey, List<String> groupKeys) {
        DataScheme dataScheme = this.runtimeDataSchemeService.getDataScheme(dataSchemeKey);
        String tableName = this.fileOperationService.getCurrentSplitTableName(dataScheme);
        TableModelDefine filePoolTable = this.dataModelService.getTableModelDefineByName(tableName);
        if (null == filePoolTable) {
            throw new NotFoundTableDefineException(new String[]{"\u9644\u4ef6\u5173\u8054\u8868\u672a\u521b\u5efa\uff0c\u8bf7\u91cd\u65b0\u53d1\u5e03\u6570\u636e\u65b9\u6848\uff01"});
        }
        DataAccessContext context = new DataAccessContext(this.dataModelService);
        NvwaQueryModel queryModel = new NvwaQueryModel();
        List filePoolFields = this.dataModelService.getColumnModelDefinesByTable(filePoolTable.getID());
        int index = 0;
        for (ColumnModelDefine filePoolField : filePoolFields) {
            queryModel.getColumns().add(new NvwaQueryColumn(filePoolField));
            if (filePoolField.getCode().equals("GROUPKEY")) {
                queryModel.getColumnFilters().put(filePoolField, groupKeys);
                continue;
            }
            if (!filePoolField.getCode().equals("ISDELETE")) continue;
            index = filePoolFields.indexOf(filePoolField);
        }
        INvwaUpdatableDataAccess updatableDataAccess = this.iNvwaDataAccessProvider.createUpdatableDataAccess(queryModel);
        try {
            INvwaUpdatableDataSet iNvwaDataRows = updatableDataAccess.executeQueryForUpdate(context);
            for (int i = 0; i < iNvwaDataRows.size(); ++i) {
                INvwaDataRow row = iNvwaDataRows.getRow(i);
                row.setValue(index, (Object)"0");
            }
            iNvwaDataRows.commitChanges(context);
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
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
            logHellperUtil.error(null, null, "\u6807\u8bb0\u9644\u4ef6\u5220\u9664\u5931\u8d25", "\u7cfb\u7edf\u9519\u8bef\uff1a" + e.getMessage());
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
                this.deleteFile(aombstoneFileInfo);
            }
            progress += step;
            if (null == monitor) continue;
            monitor.progressAndMessage(progress, "");
        }
    }

    @NotNull
    private Map<String, List<String>> buildGroupFilesMap(String dataSchemeKey) throws Exception {
        DataScheme dataScheme = this.runtimeDataSchemeService.getDataScheme(dataSchemeKey);
        String tableName = this.fileOperationService.getCurrentSplitTableName(dataScheme);
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
            switch (filePoolField.getCode()) {
                case "ISDELETE": {
                    queryModel.getColumnFilters().put(filePoolField, "1");
                    break;
                }
                case "GROUPKEY": {
                    groupCol = filePoolField;
                    break;
                }
                case "FILEKEY": {
                    fileCol = filePoolField;
                }
            }
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
        String tableName = this.fileOperationService.getCurrentSplitTableName(dataScheme);
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

    private void deleteFile(AombstoneFileInfo aombstoneFileInfo) {
        Set<String> fileKeys = aombstoneFileInfo.getFileKeys();
        ArrayList<String> fileKeyList = new ArrayList<String>(fileKeys);
        DataAccessContext context = new DataAccessContext(this.dataModelService);
        NvwaQueryModel queryModel = new NvwaQueryModel();
        DataScheme dataScheme = this.runtimeDataSchemeService.getDataScheme(aombstoneFileInfo.getDataSchemeKey());
        String tableName = this.fileOperationService.getCurrentSplitTableName(dataScheme);
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
        FileBucketNameParam param = new FileBucketNameParam(aombstoneFileInfo.getDataSchemeKey());
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
}

