/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.DataRow
 *  com.jiuqi.bi.dataset.MemoryDataSet
 *  com.jiuqi.bi.oss.ObjectInfo
 *  com.jiuqi.bi.oss.ObjectStorageException
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.definition.common.FieldType
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.nr.common.exception.NotFoundTableDefineException
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.common.util.DataEngineAdapter
 *  com.jiuqi.nr.common.util.DimensionChanger
 *  com.jiuqi.nr.data.access.api.IStateSecretLevelService
 *  com.jiuqi.nr.data.access.param.IAccessResult
 *  com.jiuqi.nr.data.access.param.IBatchAccessResult
 *  com.jiuqi.nr.data.access.param.SecretLevel
 *  com.jiuqi.nr.data.access.service.IDataAccessService
 *  com.jiuqi.nr.data.access.service.IDataAccessServiceProvider
 *  com.jiuqi.nr.data.access.util.DimCollectionBuildUtil
 *  com.jiuqi.nr.datascheme.api.DataDimension
 *  com.jiuqi.nr.datascheme.api.DataScheme
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DimensionType
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationBuilder
 *  com.jiuqi.nr.dataservice.core.log.DataServiceLoggerFactory
 *  com.jiuqi.nr.dataservice.core.log.LogDimensionCollection
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.engine.setting.AuthorityType
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityDataService
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.fileupload.FileUploadReturnInfo
 *  com.jiuqi.nr.fileupload.service.CheckUploadFileService
 *  com.jiuqi.nr.period.common.utils.PeriodUtils
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
 *  com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService
 *  org.jetbrains.annotations.NotNull
 */
package com.jiuqi.nr.attachment.service.impl;

import com.jiuqi.bi.dataset.DataRow;
import com.jiuqi.bi.dataset.MemoryDataSet;
import com.jiuqi.bi.oss.ObjectInfo;
import com.jiuqi.bi.oss.ObjectStorageException;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.definition.common.FieldType;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.nr.attachment.constant.PromptConsts;
import com.jiuqi.nr.attachment.enue.FileTypes;
import com.jiuqi.nr.attachment.exception.FieldNameIsSensitiveWordException;
import com.jiuqi.nr.attachment.exception.FileException;
import com.jiuqi.nr.attachment.exception.FileTypeErrorException;
import com.jiuqi.nr.attachment.input.AombstoneFileInfo;
import com.jiuqi.nr.attachment.input.ChangeFileCategoryInfo;
import com.jiuqi.nr.attachment.input.CommonParamsDTO;
import com.jiuqi.nr.attachment.input.FilePoolAllFileContext;
import com.jiuqi.nr.attachment.input.FilePoolContext;
import com.jiuqi.nr.attachment.input.FilePoolUploadContext;
import com.jiuqi.nr.attachment.input.FileRelateGroupInfo;
import com.jiuqi.nr.attachment.input.FileUploadInfo;
import com.jiuqi.nr.attachment.input.FileUploadRelevanceInfo;
import com.jiuqi.nr.attachment.input.RenameInfo;
import com.jiuqi.nr.attachment.input.SearchContext;
import com.jiuqi.nr.attachment.listener.IFileListener;
import com.jiuqi.nr.attachment.listener.IFileListenerProvider;
import com.jiuqi.nr.attachment.listener.param.FileDelEvent;
import com.jiuqi.nr.attachment.listener.param.FileUpdateEvent;
import com.jiuqi.nr.attachment.listener.param.FileUploadEvent;
import com.jiuqi.nr.attachment.message.AttachmentRelInfo;
import com.jiuqi.nr.attachment.message.FieldAndDimInfo;
import com.jiuqi.nr.attachment.message.FileInfo;
import com.jiuqi.nr.attachment.message.FileRefInfo;
import com.jiuqi.nr.attachment.message.RowDataInfo;
import com.jiuqi.nr.attachment.message.SearchFileInfo;
import com.jiuqi.nr.attachment.output.FileBaseRefInfo;
import com.jiuqi.nr.attachment.output.FileInfosAndGroup;
import com.jiuqi.nr.attachment.output.FilePoolFiles;
import com.jiuqi.nr.attachment.output.ReferencesInfo;
import com.jiuqi.nr.attachment.output.RowDataValues;
import com.jiuqi.nr.attachment.provider.param.FileBucketNameParam;
import com.jiuqi.nr.attachment.service.FileCategoryService;
import com.jiuqi.nr.attachment.service.FileOperationService;
import com.jiuqi.nr.attachment.service.FilePoolService;
import com.jiuqi.nr.attachment.tools.AttachmentFileAreaService;
import com.jiuqi.nr.attachment.utils.FileInfoBuilder;
import com.jiuqi.nr.attachment.utils.FileOperationUtils;
import com.jiuqi.nr.attachment.utils.LogHellperUtil;
import com.jiuqi.nr.common.exception.NotFoundTableDefineException;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.common.util.DataEngineAdapter;
import com.jiuqi.nr.common.util.DimensionChanger;
import com.jiuqi.nr.data.access.api.IStateSecretLevelService;
import com.jiuqi.nr.data.access.param.IAccessResult;
import com.jiuqi.nr.data.access.param.IBatchAccessResult;
import com.jiuqi.nr.data.access.param.SecretLevel;
import com.jiuqi.nr.data.access.service.IDataAccessService;
import com.jiuqi.nr.data.access.service.IDataAccessServiceProvider;
import com.jiuqi.nr.data.access.util.DimCollectionBuildUtil;
import com.jiuqi.nr.datascheme.api.DataDimension;
import com.jiuqi.nr.datascheme.api.DataScheme;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DimensionType;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationBuilder;
import com.jiuqi.nr.dataservice.core.log.DataServiceLoggerFactory;
import com.jiuqi.nr.dataservice.core.log.LogDimensionCollection;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.engine.setting.AuthorityType;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityDataService;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.fileupload.FileUploadReturnInfo;
import com.jiuqi.nr.fileupload.service.CheckUploadFileService;
import com.jiuqi.nr.period.common.utils.PeriodUtils;
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
import com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FilePoolServiceImpl
implements FilePoolService {
    private static final Logger logger = LoggerFactory.getLogger(FilePoolServiceImpl.class);
    @Autowired
    private FileOperationService fileOperationService;
    @Autowired
    private IRunTimeViewController iRunTimeViewController;
    @Autowired
    private CheckSensitiveWordService checkSensitiveWordService;
    @Autowired
    private AttachmentFileAreaService attachmentFileAreaService;
    @Autowired
    private DataModelService dataModelService;
    @Autowired
    private INvwaDataAccessProvider iNvwaDataAccessProvider;
    @Autowired
    private IEntityDataService entityDataService;
    @Autowired
    private IEntityViewRunTimeController entityViewRunTimeController;
    @Autowired
    private IDataDefinitionRuntimeController runtimeController;
    @Autowired
    private IDataAccessServiceProvider iDataAccessServiceProvider;
    @Autowired
    private IStateSecretLevelService stateSecretLevelService;
    @Autowired
    private IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    @Autowired
    private IRuntimeDataSchemeService runtimeDataSchemeService;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private DataEngineAdapter dataEngineAdapter;
    @Autowired
    private INvwaSystemOptionService iNvwaSystemOptionService;
    @Autowired
    private DataServiceLoggerFactory dataServiceLoggerFactory;
    @Autowired
    private PeriodEngineService periodEngineService;
    @Autowired
    private CheckUploadFileService checkUploadFileService;
    @Autowired
    private FileCategoryService fileCategoryService;
    @Autowired(required=false)
    private List<IFileListenerProvider> fileListenerProviders;
    @Autowired
    private DimCollectionBuildUtil dimCollectionBuildUtil;
    private static final String MOUDLE = "\u9644\u4ef6\u670d\u52a1";

    @Override
    public boolean isOpenFilepool() {
        String isOpenFilepool = this.iNvwaSystemOptionService.get("attachmentManagement", "OPEN_FILEPOOL");
        return !"0".equals(isOpenFilepool);
    }

    @Override
    public boolean isOpenFileCategory() {
        String isOpenFilecategory = this.iNvwaSystemOptionService.get("attachmentManagement", "ATTACHMENT_OPEN_CATEGORY");
        return this.isOpenFilepool() && "1".equals(isOpenFilecategory);
    }

    @Override
    public List<FileInfo> uploadFilesLocally(FilePoolUploadContext filePoolUploadContext) {
        DimensionValueSet dimension = filePoolUploadContext.getDimensionCombination().toDimensionValueSet();
        TaskDefine taskDefine = this.iRunTimeViewController.queryTaskDefine(filePoolUploadContext.getTaskKey());
        String dwDimensionName = this.entityMetaService.getDimensionName(taskDefine.getDw());
        IPeriodEntityAdapter periodAdapter = this.periodEngineService.getPeriodAdapter();
        String periodDimensionName = periodAdapter.getPeriodDimensionName();
        String targetKey = String.valueOf(dimension.getValue(dwDimensionName));
        String periodCode = String.valueOf(dimension.getValue(periodDimensionName));
        LogDimensionCollection logDimensionCollection = new LogDimensionCollection();
        logDimensionCollection.setDw(taskDefine.getDw(), new String[]{targetKey});
        logDimensionCollection.setPeriod(taskDefine.getDateTime(), periodCode);
        LogHellperUtil logHellperUtil = new LogHellperUtil(this.dataServiceLoggerFactory, MOUDLE);
        IEntityDefine entityDefine = this.entityMetaService.queryEntity(taskDefine.getDw());
        String filePoolKey = filePoolUploadContext.getTaskKey() + dimension.getValue(entityDefine.getDimensionName());
        FilePoolAllFileContext filePoolAllFileContext = new FilePoolAllFileContext();
        filePoolAllFileContext.setFilepoolKey(filePoolKey);
        List<FilePoolFiles> filePoolFiles = this.getFilePoolFiles(filePoolAllFileContext);
        ArrayList<String> coveredFileKeys = new ArrayList<String>();
        List<FileUploadInfo> fileUploadInfos = filePoolUploadContext.getFileUploadInfos();
        for (FileUploadInfo fileUploadInfo : fileUploadInfos) {
            String fileName = fileUploadInfo.getName();
            FileUploadReturnInfo fileUploadReturnInfo = this.checkUploadFileService.checkFileInfo(fileName = fileName.trim().replace("\n", ""), Long.valueOf(fileUploadInfo.getSize()), null, null);
            if (StringUtils.isNotEmpty((String)fileUploadReturnInfo.getErrorType())) {
                String msg = "\u4e0a\u4f20\u6587\u4ef6\u7c7b\u578b\u9519\u8bef\uff1a" + fileName;
                logHellperUtil.error(taskDefine.getKey(), logDimensionCollection, "\u9644\u4ef6\u4e0a\u4f20\u9644\u4ef6\u6c60\u5931\u8d25", PromptConsts.fileOprateServiceError(msg));
                throw new FileTypeErrorException(msg);
            }
            String fileNameNoSuffix = "";
            int fileNameIndex = fileName.lastIndexOf(".");
            fileNameNoSuffix = fileNameIndex >= 0 ? fileName.substring(0, fileNameIndex) : fileName;
            List sensitiveWordList = this.checkSensitiveWordService.thisWordIsSensitiveWord(fileNameNoSuffix);
            if (sensitiveWordList.size() > 0) {
                StringBuilder msg = new StringBuilder();
                msg.append("\u6587\u4ef6\u540d\u5305\u542b\u654f\u611f\u8bcd\u4fe1\u606f,");
                for (SensitiveWordDaoObject sensitiveWordDaoObject : sensitiveWordList) {
                    if (sensitiveWordDaoObject.getSensitiveDescription() == null || sensitiveWordDaoObject.getSensitiveDescription().length() <= 0) continue;
                    msg.append(sensitiveWordDaoObject.getSensitiveDescription());
                    msg.append(";");
                }
                msg.append("\u8bf7\u4fee\u6539\u540e\u4e0a\u4f20\uff01");
                logHellperUtil.error(taskDefine.getKey(), logDimensionCollection, "\u9644\u4ef6\u4e0a\u4f20\u9644\u4ef6\u6c60\u5931\u8d25", PromptConsts.fileOprateServiceError(msg.toString()));
                throw new FieldNameIsSensitiveWordException(msg.toString());
            }
            if (!fileUploadInfo.isCovered()) {
                for (FilePoolFiles filePoolFile : filePoolFiles) {
                    if (!filePoolFile.getFileName().equals(fileName) || filePoolFile.getFileKey().equals(fileUploadInfo.getFileKey())) continue;
                    String msg = "\u9644\u4ef6\u6c60\u6709\u91cd\u540d\u6587\u4ef6\uff1a" + fileName;
                    logHellperUtil.error(taskDefine.getKey(), logDimensionCollection, "\u9644\u4ef6\u4e0a\u4f20\u9644\u4ef6\u6c60\u5931\u8d25", PromptConsts.fileOprateServiceError(msg));
                    throw new FileException(msg);
                }
            }
            String coveredFileKey = fileUploadInfo.getFileKey();
            if (!fileUploadInfo.isCovered() || !StringUtils.isNotEmpty((String)coveredFileKey)) continue;
            coveredFileKeys.add(coveredFileKey);
        }
        Map<String, AttachmentRelInfo> attachmentRelInfoMap = null;
        ArrayList<IFileListener> fileListeners = new ArrayList<IFileListener>();
        FileBucketNameParam param = new FileBucketNameParam(taskDefine.getDataScheme());
        if (!coveredFileKeys.isEmpty()) {
            List<String> noExistFileKeys = this.attachmentFileAreaService.existFile(param, coveredFileKeys, 1);
            coveredFileKeys.removeAll(noExistFileKeys);
            if (!coveredFileKeys.isEmpty()) {
                for (String coveredFileKey : coveredFileKeys) {
                    if (this.judgeFileOverwritten(coveredFileKey, filePoolUploadContext.getTaskKey())) continue;
                    String msg = "\u9644\u4ef6\u88ab\u4e0d\u53ef\u4fee\u6539\u6307\u6807\u5f15\u7528\uff0c\u65e0\u6cd5\u8986\u76d6\u4e0a\u4f20";
                    logHellperUtil.error(taskDefine.getKey(), logDimensionCollection, "\u9644\u4ef6\u4e0a\u4f20\u9644\u4ef6\u6c60\u5931\u8d25", PromptConsts.fileOprateServiceError(msg));
                    throw new FileException(msg);
                }
                attachmentRelInfoMap = this.delFileAndSendListener(taskDefine, coveredFileKeys, fileListeners);
            }
        }
        ArrayList<FileInfo> fileInfos = new ArrayList<FileInfo>();
        for (FileUploadInfo fileUploadInfo : fileUploadInfos) {
            FileInfo fileInfo;
            HashMap<String, String> expandInfo = new HashMap<String, String>();
            expandInfo.put("secretlevel", fileUploadInfo.getFileSecret());
            expandInfo.put("filePool", filePoolKey);
            expandInfo.put("category", fileUploadInfo.getCategory());
            String fileKey = fileUploadInfo.getFileKey();
            if (fileUploadInfo.isCovered() && StringUtils.isNotEmpty((String)fileKey)) {
                fileInfo = this.attachmentFileAreaService.uploadByKey(param, fileUploadInfo.getName(), fileKey, fileUploadInfo.getFile(), expandInfo);
                fileInfos.add(fileInfo);
                continue;
            }
            fileInfo = this.attachmentFileAreaService.upload(param, fileUploadInfo.getName(), fileUploadInfo.getFile(), expandInfo);
            fileInfos.add(fileInfo);
        }
        if (!coveredFileKeys.isEmpty() && null != attachmentRelInfoMap && !attachmentRelInfoMap.isEmpty()) {
            for (String attRelGroupKey : attachmentRelInfoMap.keySet()) {
                AttachmentRelInfo attachmentRelInfo = attachmentRelInfoMap.get(attRelGroupKey);
                for (IFileListener fileListener : fileListeners) {
                    FileUploadEvent fileUploadEvent = new FileUploadEvent(taskDefine.getDataScheme(), taskDefine.getKey(), attachmentRelInfo.getFormSchemeKey(), attachmentRelInfo.getDim(), attachmentRelInfo.getFieldKey(), attRelGroupKey, attachmentRelInfo.getFileKeys());
                    fileListener.afterFileUpload(fileUploadEvent);
                }
            }
        }
        logHellperUtil.info(taskDefine.getKey(), logDimensionCollection, "\u9644\u4ef6\u4e0a\u4f20\u9644\u4ef6\u6c60\u6210\u529f", "\u9644\u4ef6\u4e0a\u4f20\u9644\u4ef6\u6c60\u6210\u529f");
        return fileInfos;
    }

    private Map<String, AttachmentRelInfo> delFileAndSendListener(TaskDefine taskDefine, List<String> coveredFileKeys, List<IFileListener> fileListeners) {
        Map<String, AttachmentRelInfo> attachmentRelInfoMap;
        if (null != this.fileListenerProviders) {
            for (IFileListenerProvider fileListenerProvider : this.fileListenerProviders) {
                IFileListener fileListener = fileListenerProvider.getFileListener(null);
                if (null == fileListener) continue;
                fileListeners.add(fileListener);
            }
        }
        if (!(attachmentRelInfoMap = this.getAttachmentRelInfo(taskDefine, coveredFileKeys)).isEmpty()) {
            for (String groupKey : attachmentRelInfoMap.keySet()) {
                AttachmentRelInfo attachmentRelInfo = attachmentRelInfoMap.get(groupKey);
                for (IFileListener fileListener : fileListeners) {
                    FileDelEvent fileDelEvent = new FileDelEvent(taskDefine.getDataScheme(), taskDefine.getKey(), attachmentRelInfo.getFormSchemeKey(), groupKey, attachmentRelInfo.getFileKeys());
                    fileListener.beforeFileDelete(fileDelEvent);
                }
            }
        }
        FileBucketNameParam param = new FileBucketNameParam(taskDefine.getDataScheme());
        this.attachmentFileAreaService.batchDelete(param, coveredFileKeys);
        if (!attachmentRelInfoMap.isEmpty()) {
            for (String groupKey : attachmentRelInfoMap.keySet()) {
                AttachmentRelInfo attachmentRelInfo = attachmentRelInfoMap.get(groupKey);
                for (IFileListener fileListener : fileListeners) {
                    FileDelEvent fileDelEvent = new FileDelEvent(taskDefine.getDataScheme(), taskDefine.getKey(), attachmentRelInfo.getFormSchemeKey(), groupKey, attachmentRelInfo.getFileKeys());
                    fileListener.afterFileDelete(fileDelEvent);
                }
            }
        }
        return attachmentRelInfoMap;
    }

    private Map<String, AttachmentRelInfo> getAttachmentRelInfo(TaskDefine taskDefine, List<String> coveredFileKeys) {
        DataAccessContext context = new DataAccessContext(this.dataModelService);
        NvwaQueryModel queryModel = new NvwaQueryModel();
        DataScheme dataScheme = this.runtimeDataSchemeService.getDataScheme(taskDefine.getDataScheme());
        String tableName = this.fileOperationService.getCurrentSplitTableName(dataScheme);
        TableModelDefine filePoolTable = this.dataModelService.getTableModelDefineByName(tableName);
        if (null == filePoolTable) {
            throw new NotFoundTableDefineException(new String[]{"\u9644\u4ef6\u5173\u8054\u8868\u672a\u521b\u5efa\uff0c\u8bf7\u91cd\u65b0\u53d1\u5e03\u6570\u636e\u65b9\u6848\uff01"});
        }
        DimensionChanger dimensionChanger = this.dataEngineAdapter.getDimensionChanger(filePoolTable.getName());
        int groupKeyIndex = 0;
        int periodIndex = 0;
        int fieldKeyIndex = 0;
        int fileKeyIndex = 0;
        List filePoolFields = this.dataModelService.getColumnModelDefinesByTable(filePoolTable.getID());
        block16: for (int i = 0; i < filePoolFields.size(); ++i) {
            ColumnModelDefine columnModelDefine = (ColumnModelDefine)filePoolFields.get(i);
            String columnModelCode = columnModelDefine.getCode();
            queryModel.getColumns().add(new NvwaQueryColumn(columnModelDefine));
            switch (columnModelCode) {
                case "FILEKEY": {
                    queryModel.getColumnFilters().put(columnModelDefine, coveredFileKeys);
                    fileKeyIndex = i;
                    continue block16;
                }
                case "ISDELETE": {
                    queryModel.getColumnFilters().put(columnModelDefine, "0");
                    continue block16;
                }
                case "GROUPKEY": {
                    groupKeyIndex = i;
                    continue block16;
                }
                case "PERIOD": {
                    periodIndex = i;
                    continue block16;
                }
                case "FIELD_KEY": {
                    fieldKeyIndex = i;
                }
            }
        }
        INvwaDataAccess readOnlyDataAccess = this.iNvwaDataAccessProvider.createReadOnlyDataAccess(queryModel);
        HashMap<String, AttachmentRelInfo> attachmentRelInfoMap = new HashMap<String, AttachmentRelInfo>();
        try {
            MemoryDataSet dataTable = readOnlyDataAccess.executeQuery(context);
            List columns = queryModel.getColumns();
            for (int i = 0; i < dataTable.size(); ++i) {
                DataRow data = dataTable.get(i);
                String groupKey = data.getString(groupKeyIndex);
                AttachmentRelInfo attachmentRelInfo = (AttachmentRelInfo)attachmentRelInfoMap.get(groupKey);
                if (null != attachmentRelInfo) {
                    List<String> fileKeys = attachmentRelInfo.getFileKeys();
                    fileKeys.add(data.getString(fileKeyIndex));
                    continue;
                }
                this.putAttachmentRelInfoMap(taskDefine, dimensionChanger, periodIndex, fieldKeyIndex, fileKeyIndex, attachmentRelInfoMap, columns, data, groupKey);
            }
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
        return attachmentRelInfoMap;
    }

    private void putAttachmentRelInfoMap(TaskDefine taskDefine, DimensionChanger dimensionChanger, int periodIndex, int fieldKeyIndex, int fileKeyIndex, Map<String, AttachmentRelInfo> attachmentRelInfoMap, List<NvwaQueryColumn> columns, DataRow data, String groupKey) throws Exception {
        String period = data.getString(periodIndex);
        SchemePeriodLinkDefine schemePeriodLinkDefine = this.iRunTimeViewController.querySchemePeriodLinkByPeriodAndTask(period, taskDefine.getKey());
        if (null != schemePeriodLinkDefine) {
            DimensionCombinationBuilder dimBuilder = new DimensionCombinationBuilder();
            String periodDimName = dimensionChanger.getDimensionName(columns.get(periodIndex).getColumnModel());
            if (StringUtils.isNotEmpty((String)periodDimName)) {
                dimBuilder.setValue(periodDimName, (Object)period);
            }
            for (int j = 0; j < columns.size(); ++j) {
                String dimensionName;
                String columnModelCode = columns.get(j).getColumnModel().getCode();
                if (columnModelCode.equals("ID") || columnModelCode.equals("ISDELETE") || columnModelCode.equals("PERIOD") || columnModelCode.equals("FIELD_KEY") || columnModelCode.equals("GROUPKEY") || columnModelCode.equals("FILEKEY") || !StringUtils.isNotEmpty((String)(dimensionName = dimensionChanger.getDimensionName(columns.get(j).getColumnModel())))) continue;
                dimBuilder.setValue(dimensionName, (Object)data.getString(j));
            }
            String formSchemeKey = schemePeriodLinkDefine.getSchemeKey();
            ArrayList<String> fileKeys = new ArrayList<String>();
            fileKeys.add(data.getString(fileKeyIndex));
            AttachmentRelInfo attachmentRelInfo = new AttachmentRelInfo(dimBuilder.getCombination(), groupKey, formSchemeKey, data.getString(fieldKeyIndex), fileKeys);
            attachmentRelInfoMap.put(groupKey, attachmentRelInfo);
        }
    }

    @Override
    public String uploadFilesFromFilePool(FileRelateGroupInfo fileRelateGroupInfo) {
        List filePoolFields;
        TableModelDefine filePoolTable;
        String tableName;
        DataScheme dataScheme;
        NvwaQueryModel queryModel;
        DataAccessContext context;
        DimensionValueSet dimension = fileRelateGroupInfo.getDimensionCombination().toDimensionValueSet();
        TaskDefine taskDefine = this.iRunTimeViewController.queryTaskDefine(fileRelateGroupInfo.getTaskKey());
        String dwDimensionName = this.entityMetaService.getDimensionName(taskDefine.getDw());
        IPeriodEntityAdapter periodAdapter = this.periodEngineService.getPeriodAdapter();
        String periodDimensionName = periodAdapter.getPeriodDimensionName();
        String targetKey = String.valueOf(dimension.getValue(dwDimensionName));
        String periodCode = String.valueOf(dimension.getValue(periodDimensionName));
        LogDimensionCollection logDimensionCollection = new LogDimensionCollection();
        logDimensionCollection.setDw(taskDefine.getDw(), new String[]{targetKey});
        logDimensionCollection.setPeriod(taskDefine.getDateTime(), periodCode);
        LogHellperUtil logHellperUtil = new LogHellperUtil(this.dataServiceLoggerFactory, MOUDLE);
        String groupKey = "";
        List<String> fileKeys = fileRelateGroupInfo.getFileKeys();
        if (StringUtils.isEmpty((String)fileRelateGroupInfo.getGroupKey())) {
            groupKey = UUID.randomUUID().toString();
        } else {
            groupKey = fileRelateGroupInfo.getGroupKey();
            context = new DataAccessContext(this.dataModelService);
            queryModel = new NvwaQueryModel();
            dataScheme = this.runtimeDataSchemeService.getDataScheme(fileRelateGroupInfo.getDataSchemeKey());
            tableName = this.fileOperationService.getCurrentSplitTableName(dataScheme);
            filePoolTable = this.dataModelService.getTableModelDefineByName(tableName);
            if (null == filePoolTable) {
                logHellperUtil.error(taskDefine.getKey(), logDimensionCollection, "\u9644\u4ef6\u548c\u6307\u6807\u5173\u8054\u5931\u8d25", PromptConsts.fileOprateServiceError("\u9644\u4ef6\u5173\u8054\u8868\u672a\u521b\u5efa\uff0c\u8bf7\u91cd\u65b0\u53d1\u5e03\u6570\u636e\u65b9\u6848\uff01"));
                throw new NotFoundTableDefineException(new String[]{"\u9644\u4ef6\u5173\u8054\u8868\u672a\u521b\u5efa\uff0c\u8bf7\u91cd\u65b0\u53d1\u5e03\u6570\u636e\u65b9\u6848\uff01"});
            }
            filePoolFields = this.dataModelService.getColumnModelDefinesByTable(filePoolTable.getID());
            for (ColumnModelDefine filePoolField : filePoolFields) {
                queryModel.getColumns().add(new NvwaQueryColumn(filePoolField));
                switch (filePoolField.getCode()) {
                    case "GROUPKEY": {
                        queryModel.getColumnFilters().put(filePoolField, groupKey);
                        break;
                    }
                    case "FILEKEY": {
                        queryModel.getColumnFilters().put(filePoolField, fileKeys);
                        break;
                    }
                    case "ISDELETE": {
                        queryModel.getColumnFilters().put(filePoolField, "0");
                    }
                }
            }
            INvwaDataAccess readOnlyDataAccess = this.iNvwaDataAccessProvider.createReadOnlyDataAccess(queryModel);
            try {
                MemoryDataSet dataTable = readOnlyDataAccess.executeQuery(context);
                List columns = queryModel.getColumns();
                for (int i = 0; i < columns.size(); ++i) {
                    if (!((NvwaQueryColumn)columns.get(i)).getColumnModel().getCode().equals("FILEKEY")) continue;
                    for (int j = 0; j < dataTable.size(); ++j) {
                        DataRow item = dataTable.get(j);
                        fileKeys.remove(item.getString(i));
                    }
                    break;
                }
            }
            catch (Exception e) {
                logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            }
        }
        if (fileKeys.isEmpty()) {
            return groupKey;
        }
        context = new DataAccessContext(this.dataModelService);
        queryModel = new NvwaQueryModel();
        dataScheme = this.runtimeDataSchemeService.getDataScheme(fileRelateGroupInfo.getDataSchemeKey());
        tableName = this.fileOperationService.getCurrentSplitTableName(dataScheme);
        filePoolTable = this.dataModelService.getTableModelDefineByName(tableName);
        if (null == filePoolTable) {
            logHellperUtil.error(taskDefine.getKey(), logDimensionCollection, "\u9644\u4ef6\u548c\u6307\u6807\u5173\u8054\u5931\u8d25", PromptConsts.fileOprateServiceError("\u9644\u4ef6\u5173\u8054\u8868\u672a\u521b\u5efa\uff0c\u8bf7\u91cd\u65b0\u53d1\u5e03\u6570\u636e\u65b9\u6848\uff01"));
            throw new NotFoundTableDefineException(new String[]{"\u9644\u4ef6\u5173\u8054\u8868\u672a\u521b\u5efa\uff0c\u8bf7\u91cd\u65b0\u53d1\u5e03\u6570\u636e\u65b9\u6848\uff01"});
        }
        filePoolFields = this.dataModelService.getColumnModelDefinesByTable(filePoolTable.getID());
        for (ColumnModelDefine filePoolField : filePoolFields) {
            queryModel.getColumns().add(new NvwaQueryColumn(filePoolField));
        }
        DimensionChanger dimensionChanger = this.dataEngineAdapter.getDimensionChanger(filePoolTable.getName());
        DimensionValueSet dimensionValueSet = fileRelateGroupInfo.getDimensionCombination().toDimensionValueSet();
        INvwaUpdatableDataAccess updatableDataAccess = this.iNvwaDataAccessProvider.createUpdatableDataAccess(queryModel);
        INvwaDataUpdator iNvwaDataUpdator = null;
        List columns = queryModel.getColumns();
        try {
            iNvwaDataUpdator = updatableDataAccess.openForUpdate(context);
            for (String fileKey : fileKeys) {
                INvwaDataRow iNvwaDataRow = iNvwaDataUpdator.addInsertRow();
                String id = UUID.randomUUID().toString();
                block33: for (int i = 0; i < columns.size(); ++i) {
                    switch (((NvwaQueryColumn)columns.get(i)).getColumnModel().getCode()) {
                        case "ID": {
                            iNvwaDataRow.setValue(i, (Object)id);
                            continue block33;
                        }
                        case "GROUPKEY": {
                            iNvwaDataRow.setValue(i, (Object)groupKey);
                            continue block33;
                        }
                        case "FILEKEY": {
                            iNvwaDataRow.setValue(i, (Object)fileKey);
                            continue block33;
                        }
                        case "FIELD_KEY": {
                            iNvwaDataRow.setValue(i, (Object)fileRelateGroupInfo.getFieldKey());
                            continue block33;
                        }
                        case "ISDELETE": {
                            iNvwaDataRow.setValue(i, (Object)"0");
                            continue block33;
                        }
                        default: {
                            String dimensionName = dimensionChanger.getDimensionName(((NvwaQueryColumn)columns.get(i)).getColumnModel());
                            iNvwaDataRow.setValue(i, dimensionValueSet.getValue(dimensionName));
                        }
                    }
                }
            }
            iNvwaDataUpdator.commitChanges(context);
            ArrayList<IFileListener> fileListeners = new ArrayList<IFileListener>();
            if (null != this.fileListenerProviders) {
                for (IFileListenerProvider fileListenerProvider : this.fileListenerProviders) {
                    IFileListener fileListener = fileListenerProvider.getFileListener(null);
                    if (null == fileListener) continue;
                    fileListeners.add(fileListener);
                }
            }
            for (IFileListener fileListener : fileListeners) {
                FileUploadEvent fileUploadEvent = new FileUploadEvent(dataScheme.getKey(), taskDefine.getKey(), fileRelateGroupInfo.getFormSchemeKey(), fileRelateGroupInfo.getDimensionCombination(), fileRelateGroupInfo.getFieldKey(), groupKey, fileKeys);
                fileListener.afterFileUpload(fileUploadEvent);
            }
            logHellperUtil.info(taskDefine.getKey(), logDimensionCollection, "\u9644\u4ef6\u548c\u6307\u6807\u5173\u8054\u6210\u529f", "\u9644\u4ef6\u548c\u6307\u6807\u5173\u8054\u6210\u529f");
        }
        catch (Exception e) {
            logHellperUtil.error(taskDefine.getKey(), logDimensionCollection, "\u9644\u4ef6\u548c\u6307\u6807\u5173\u8054\u5931\u8d25", PromptConsts.fileOprateSystemError(e.getMessage()));
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
        return groupKey;
    }

    @Override
    public String uploadFilesAndRelevance(FileUploadRelevanceInfo fileUploadRelevanceInfo) {
        DimensionValueSet dimension = fileUploadRelevanceInfo.getDimensionCombination().toDimensionValueSet();
        TaskDefine taskDefine = this.iRunTimeViewController.queryTaskDefine(fileUploadRelevanceInfo.getTaskKey());
        String dwDimensionName = this.entityMetaService.getDimensionName(taskDefine.getDw());
        IPeriodEntityAdapter periodAdapter = this.periodEngineService.getPeriodAdapter();
        String periodDimensionName = periodAdapter.getPeriodDimensionName();
        String targetKey = String.valueOf(dimension.getValue(dwDimensionName));
        String periodCode = String.valueOf(dimension.getValue(periodDimensionName));
        LogDimensionCollection logDimensionCollection = new LogDimensionCollection();
        logDimensionCollection.setDw(taskDefine.getDw(), new String[]{targetKey});
        logDimensionCollection.setPeriod(taskDefine.getDateTime(), periodCode);
        LogHellperUtil logHellperUtil = new LogHellperUtil(this.dataServiceLoggerFactory, MOUDLE);
        IEntityDefine entityDefine = this.entityMetaService.queryEntity(taskDefine.getDw());
        String filePoolKey = fileUploadRelevanceInfo.getTaskKey() + dimension.getValue(entityDefine.getDimensionName());
        FilePoolAllFileContext filePoolAllFileContext = new FilePoolAllFileContext();
        filePoolAllFileContext.setFilepoolKey(filePoolKey);
        List<FilePoolFiles> filePoolFiles = this.getFilePoolFiles(filePoolAllFileContext);
        try {
            ArrayList<String> fileKeys = new ArrayList<String>();
            FileBucketNameParam param = new FileBucketNameParam(fileUploadRelevanceInfo.getDataSchemeKey());
            List<FileUploadInfo> fileUploadInfos = fileUploadRelevanceInfo.getFileUploadInfos();
            for (FileUploadInfo fileUploadInfo : fileUploadInfos) {
                FileInfo fileInfo;
                HashMap<String, String> expandInfo;
                String msg;
                boolean canSkipJudgeName = false;
                if (!fileUploadInfo.isCovered()) {
                    for (FilePoolFiles filePoolFile : filePoolFiles) {
                        if (!filePoolFile.getFileName().equals(fileUploadInfo.getName())) continue;
                        if (this.judgeFileOverwritten(filePoolFile.getFileKey(), fileUploadRelevanceInfo.getTaskKey())) {
                            canSkipJudgeName = true;
                            fileUploadInfo.setFileKey(filePoolFile.getFileKey());
                            fileUploadInfo.setCovered(true);
                            break;
                        }
                        msg = "\u9644\u4ef6\u6c60\u6709\u91cd\u540d\u6587\u4ef6\u4e14\u65e0\u6cd5\u8986\u76d6";
                        logHellperUtil.error(taskDefine.getKey(), logDimensionCollection, "\u9644\u4ef6\u4e0a\u4f20\u5931\u8d25", PromptConsts.fileOprateServiceError(msg));
                        throw new FileException(msg);
                    }
                }
                String fileName = fileUploadInfo.getName();
                FileUploadReturnInfo fileUploadReturnInfo = this.checkUploadFileService.checkFileInfo(fileName = fileName.trim().replace("\n", ""), Long.valueOf(fileUploadInfo.getSize()), null, null);
                if (StringUtils.isNotEmpty((String)fileUploadReturnInfo.getErrorType())) {
                    msg = "\u4e0a\u4f20\u6587\u4ef6\u7c7b\u578b\u9519\u8bef";
                    logHellperUtil.error(taskDefine.getKey(), logDimensionCollection, "\u9644\u4ef6\u4e0a\u4f20\u5931\u8d25", PromptConsts.fileOprateServiceError(msg));
                    throw new FileTypeErrorException(msg);
                }
                String fileNameNoSuffix = "";
                int fileNameIndex = fileName.lastIndexOf(".");
                fileNameNoSuffix = fileNameIndex >= 0 ? fileName.substring(0, fileNameIndex) : fileName;
                List sensitiveWordList = this.checkSensitiveWordService.thisWordIsSensitiveWord(fileNameNoSuffix);
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
                if (!fileUploadInfo.isCovered()) {
                    expandInfo = new HashMap<String, String>();
                    expandInfo.put("secretlevel", fileUploadInfo.getFileSecret());
                    expandInfo.put("filePool", filePoolKey);
                    expandInfo.put("category", fileUploadInfo.getCategory());
                    fileInfo = this.attachmentFileAreaService.upload(param, fileName, fileUploadInfo.getFile(), expandInfo);
                    fileKeys.add(fileInfo.getKey());
                    continue;
                }
                if (!canSkipJudgeName && !this.judgeFileOverwritten(fileUploadInfo.getFileKey(), fileUploadRelevanceInfo.getTaskKey())) {
                    String msg3 = "\u9644\u4ef6\u88ab\u4e0d\u53ef\u4fee\u6539\u6307\u6807\u5f15\u7528\uff0c\u65e0\u6cd5\u8986\u76d6\u4e0a\u4f20";
                    logHellperUtil.error(taskDefine.getKey(), logDimensionCollection, "\u9644\u4ef6\u4e0a\u4f20\u5931\u8d25", PromptConsts.fileOprateServiceError(msg3));
                    throw new FileException(msg3);
                }
                this.attachmentFileAreaService.delete(param, fileUploadInfo.getFileKey());
                expandInfo = new HashMap();
                expandInfo.put("secretlevel", fileUploadInfo.getFileSecret());
                expandInfo.put("filePool", filePoolKey);
                expandInfo.put("category", fileUploadInfo.getCategory());
                fileInfo = this.attachmentFileAreaService.uploadByKey(param, fileName, fileUploadInfo.getFileKey(), fileUploadInfo.getFile(), expandInfo);
                fileKeys.add(fileInfo.getKey());
            }
            if (fileKeys.isEmpty()) {
                return fileUploadRelevanceInfo.getGroupKey();
            }
            FileRelateGroupInfo fileRelateGroupInfo = new FileRelateGroupInfo();
            fileRelateGroupInfo.setDataSchemeKey(fileUploadRelevanceInfo.getDataSchemeKey());
            fileRelateGroupInfo.setTaskKey(fileUploadRelevanceInfo.getTaskKey());
            fileRelateGroupInfo.setDimensionCombination(fileUploadRelevanceInfo.getDimensionCombination());
            fileRelateGroupInfo.setFormSchemeKey(fileUploadRelevanceInfo.getFormSchemeKey());
            fileRelateGroupInfo.setFormKey(fileUploadRelevanceInfo.getFormKey());
            fileRelateGroupInfo.setFieldKey(fileUploadRelevanceInfo.getFieldKey());
            fileRelateGroupInfo.setGroupKey(fileUploadRelevanceInfo.getGroupKey());
            fileRelateGroupInfo.setFileKeys(fileKeys);
            String groupKey = this.uploadFilesFromFilePool(fileRelateGroupInfo);
            logHellperUtil.info(taskDefine.getKey(), logDimensionCollection, "\u9644\u4ef6\u4e0a\u4f20\u6210\u529f", "\u9644\u4ef6\u4e0a\u4f20\u6210\u529f");
            return groupKey;
        }
        catch (Exception e) {
            logHellperUtil.error(taskDefine.getKey(), logDimensionCollection, "\u9644\u4ef6\u4e0a\u4f20\u5931\u8d25", PromptConsts.fileOprateServiceError(e.getMessage()));
            throw e;
        }
    }

    @Override
    public void tombstoneFile(AombstoneFileInfo aombstoneFileInfo) {
        LogDimensionCollection logDimensionCollection = new LogDimensionCollection();
        LogHellperUtil logHellperUtil = new LogHellperUtil(this.dataServiceLoggerFactory, MOUDLE);
        DataAccessContext context = new DataAccessContext(this.dataModelService);
        NvwaQueryModel queryModel = new NvwaQueryModel();
        DataScheme dataScheme = this.runtimeDataSchemeService.getDataScheme(aombstoneFileInfo.getDataSchemeKey());
        String tableName = this.fileOperationService.getCurrentSplitTableName(dataScheme);
        TableModelDefine filePoolTable = this.dataModelService.getTableModelDefineByName(tableName);
        if (null == filePoolTable) {
            logHellperUtil.error(aombstoneFileInfo.getTaskKey(), null, "\u9644\u4ef6\u5220\u9664\u5931\u8d25", PromptConsts.fileOprateServiceError("\u9644\u4ef6\u5173\u8054\u8868\u672a\u521b\u5efa\uff0c\u8bf7\u91cd\u65b0\u53d1\u5e03\u6570\u636e\u65b9\u6848\uff01"));
            throw new NotFoundTableDefineException(new String[]{"\u9644\u4ef6\u5173\u8054\u8868\u672a\u521b\u5efa\uff0c\u8bf7\u91cd\u65b0\u53d1\u5e03\u6570\u636e\u65b9\u6848\uff01"});
        }
        List filePoolFields = this.dataModelService.getColumnModelDefinesByTable(filePoolTable.getID());
        HashSet<String> filepoolFileKeys = new HashSet<String>();
        HashSet<String> notFilepoolFileKeys = new HashSet<String>();
        FileBucketNameParam param = new FileBucketNameParam(aombstoneFileInfo.getDataSchemeKey());
        List<FileInfo> fileInfos = this.attachmentFileAreaService.getFileInfoByKeys(param, new ArrayList<String>(aombstoneFileInfo.getFileKeys()));
        for (String string : aombstoneFileInfo.getFileKeys()) {
            boolean exist = false;
            for (FileInfo fileInfo : fileInfos) {
                if (!string.equals(fileInfo.getKey())) continue;
                exist = true;
                String filepoolKey = fileInfo.getFilepoolKey();
                if (StringUtils.isNotEmpty((String)filepoolKey)) {
                    filepoolFileKeys.add(string);
                    break;
                }
                notFilepoolFileKeys.add(string);
                break;
            }
            if (exist) continue;
            filepoolFileKeys.add(string);
        }
        if (!filepoolFileKeys.isEmpty()) {
            List filepoolFileKeyList = filepoolFileKeys.stream().collect(Collectors.toList());
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
                queryModel.getColumnFilters().put(filePoolField, "0");
            }
            DimensionChanger dimensionChanger = this.dataEngineAdapter.getDimensionChanger(filePoolTable.getName());
            DimensionCombinationBuilder dimensionCombinationBuilder = new DimensionCombinationBuilder();
            ArrayList<String> dimNameCatch = new ArrayList<String>();
            List columns = queryModel.getColumns();
            INvwaUpdatableDataAccess updatableDataAccess = this.iNvwaDataAccessProvider.createUpdatableDataAccess(queryModel);
            try {
                INvwaUpdatableDataSet iNvwaDataRows = updatableDataAccess.executeQueryForUpdate(context);
                for (int i = 0; i < iNvwaDataRows.size(); ++i) {
                    INvwaDataRow row = iNvwaDataRows.getRow(i);
                    for (int j = 0; j < columns.size(); ++j) {
                        List dataSchemeDimension;
                        String dimensionName;
                        if ("FIELD_KEY".equals(((NvwaQueryColumn)columns.get(j)).getColumnModel().getCode()) || "ID".equals(((NvwaQueryColumn)columns.get(j)).getColumnModel().getCode()) || "GROUPKEY".equals(((NvwaQueryColumn)columns.get(j)).getColumnModel().getCode()) || "FILEKEY".equals(((NvwaQueryColumn)columns.get(j)).getColumnModel().getCode()) || "ISDELETE".equals(((NvwaQueryColumn)columns.get(j)).getColumnModel().getCode()) || !StringUtils.isNotEmpty((String)(dimensionName = dimensionChanger.getDimensionName(((NvwaQueryColumn)columns.get(j)).getColumnModel().getCode()))) || dimNameCatch.contains(dimensionName)) continue;
                        dimensionCombinationBuilder.setValue(dimensionName, null, (Object)row.getValue(j).toString());
                        dimNameCatch.add(dimensionName);
                        if ("MDCODE".equals(((NvwaQueryColumn)columns.get(j)).getColumnModel().getCode())) {
                            dataSchemeDimension = this.runtimeDataSchemeService.getDataSchemeDimension(aombstoneFileInfo.getDataSchemeKey(), DimensionType.UNIT);
                            logDimensionCollection.setDw(((DataDimension)dataSchemeDimension.get(0)).getDimKey(), new String[]{row.getValue(j).toString()});
                            continue;
                        }
                        if (!"PERIOD".equals(((NvwaQueryColumn)columns.get(j)).getColumnModel().getCode())) continue;
                        dataSchemeDimension = this.runtimeDataSchemeService.getDataSchemeDimension(aombstoneFileInfo.getDataSchemeKey(), DimensionType.PERIOD);
                        logDimensionCollection.setPeriod(((DataDimension)dataSchemeDimension.get(0)).getDimKey(), row.getValue(j).toString());
                    }
                }
                iNvwaDataRows.deleteAll();
                iNvwaDataRows.commitChanges(context);
            }
            catch (Exception e) {
                logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                logHellperUtil.error(aombstoneFileInfo.getTaskKey(), logDimensionCollection, "\u9644\u4ef6\u5220\u9664\u5931\u8d25", PromptConsts.fileOprateSystemError(e.getMessage()));
            }
        }
        try {
            if (!notFilepoolFileKeys.isEmpty()) {
                AombstoneFileInfo aombstoneFileInfo1 = new AombstoneFileInfo();
                aombstoneFileInfo1.setDataSchemeKey(aombstoneFileInfo.getDataSchemeKey());
                aombstoneFileInfo1.setGroupKey(aombstoneFileInfo.getGroupKey());
                aombstoneFileInfo1.setFileKeys(notFilepoolFileKeys);
                this.fileOperationService.deleteFile(aombstoneFileInfo1);
            }
            logHellperUtil.info(aombstoneFileInfo.getTaskKey(), logDimensionCollection, "\u9644\u4ef6\u5220\u9664\u6210\u529f", "\u9644\u4ef6\u5220\u9664\u6210\u529f");
        }
        catch (Exception e) {
            logHellperUtil.error(aombstoneFileInfo.getTaskKey(), logDimensionCollection, "\u9644\u4ef6\u5220\u9664\u5931\u8d25", PromptConsts.fileOprateSystemError(e.getMessage()));
        }
    }

    @Override
    public void markDelFileByGroupKey(Set<String> groupKeys) {
        this.markDelFileByGroupKey(null, groupKeys);
    }

    @Override
    public void markDelFileByGroupKey(String dataSchemeKey, Set<String> groupKeys) {
        if (groupKeys.isEmpty()) {
            return;
        }
        LogHellperUtil logHellperUtil = new LogHellperUtil(this.dataServiceLoggerFactory, MOUDLE);
        List<TableModelDefine> filepoolTables = this.queryFileRelTable(dataSchemeKey, logHellperUtil);
        try {
            for (TableModelDefine filepoolTable : filepoolTables) {
                List filePoolFields = this.dataModelService.getColumnModelDefinesByTable(filepoolTable.getID());
                DataAccessContext context = new DataAccessContext(this.dataModelService);
                NvwaQueryModel queryModel = new NvwaQueryModel();
                for (ColumnModelDefine filePoolField : filePoolFields) {
                    if (filePoolField.getCode().equals("GROUPKEY")) {
                        queryModel.getColumnFilters().put(filePoolField, new ArrayList<String>(groupKeys));
                        continue;
                    }
                    if (!filePoolField.getCode().equals("ISDELETE")) continue;
                    queryModel.getColumns().add(new NvwaQueryColumn(filePoolField));
                }
                if (queryModel.getColumns().isEmpty()) continue;
                INvwaUpdatableDataAccess updatableDataAccess = this.iNvwaDataAccessProvider.createUpdatableDataAccess(queryModel);
                INvwaUpdatableDataSet iNvwaDataRows = updatableDataAccess.executeQueryForUpdate(context);
                for (int i = 0; i < iNvwaDataRows.size(); ++i) {
                    INvwaDataRow row = iNvwaDataRows.getRow(i);
                    row.setValue(0, (Object)"1");
                }
                iNvwaDataRows.commitChanges(context);
            }
            logHellperUtil.info(null, null, "\u6839\u636e\u5206\u7ec4\u6807\u8bb0\u5220\u9664\u9644\u4ef6\u6210\u529f", "\u6839\u636e\u5206\u7ec4\u6807\u8bb0\u5220\u9664\u9644\u4ef6\u6210\u529f");
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            logHellperUtil.error(null, null, "\u6839\u636e\u5206\u7ec4\u6807\u8bb0\u5220\u9664\u9644\u4ef6\u5931\u8d25", PromptConsts.fileOprateSystemError(e.getMessage()));
        }
    }

    @NotNull
    private List<TableModelDefine> queryFileRelTable(String dataSchemeKey, LogHellperUtil logHellperUtil) {
        ArrayList<TableModelDefine> filepoolTables = new ArrayList<TableModelDefine>();
        if (null == dataSchemeKey) {
            List tableModelDefines = this.dataModelService.getTableModelDefines();
            for (TableModelDefine tableModelDefine : tableModelDefines) {
                String tableName = tableModelDefine.getName();
                if (!tableName.contains("NR_FILE_")) continue;
                filepoolTables.add(tableModelDefine);
            }
            if (filepoolTables.isEmpty()) {
                logHellperUtil.error(null, null, "\u6839\u636e\u5206\u7ec4\u6807\u8bb0\u5220\u9664\u9644\u4ef6\u5931\u8d25", PromptConsts.fileOprateServiceError("\u9644\u4ef6\u5173\u8054\u8868\u672a\u521b\u5efa\uff0c\u8bf7\u91cd\u65b0\u53d1\u5e03\u6570\u636e\u65b9\u6848\uff01"));
                throw new NotFoundTableDefineException(new String[]{"\u9644\u4ef6\u5173\u8054\u8868\u672a\u521b\u5efa\uff0c\u8bf7\u91cd\u65b0\u53d1\u5e03\u6570\u636e\u65b9\u6848\uff01"});
            }
        } else {
            DataScheme dataScheme = this.runtimeDataSchemeService.getDataScheme(dataSchemeKey);
            String tableName = this.fileOperationService.getCurrentSplitTableName(dataScheme);
            TableModelDefine filePoolTable = this.dataModelService.getTableModelDefineByName(tableName);
            if (null == filePoolTable) {
                logHellperUtil.error(null, null, "\u6839\u636e\u5206\u7ec4\u6807\u8bb0\u5220\u9664\u9644\u4ef6\u5931\u8d25", PromptConsts.fileOprateServiceError("\u9644\u4ef6\u5173\u8054\u8868\u672a\u521b\u5efa\uff0c\u8bf7\u91cd\u65b0\u53d1\u5e03\u6570\u636e\u65b9\u6848\uff01"));
                throw new NotFoundTableDefineException(new String[]{"\u9644\u4ef6\u5173\u8054\u8868\u672a\u521b\u5efa\uff0c\u8bf7\u91cd\u65b0\u53d1\u5e03\u6570\u636e\u65b9\u6848\uff01"});
            }
            filepoolTables.add(filePoolTable);
        }
        return filepoolTables;
    }

    @Override
    public void rename(RenameInfo renameInfo) {
        LogHellperUtil logHellperUtil = new LogHellperUtil(this.dataServiceLoggerFactory, MOUDLE);
        try {
            HashMap<String, String> updateInfo = new HashMap<String, String>();
            updateInfo.put("name", renameInfo.getName());
            TaskDefine taskDefine = this.iRunTimeViewController.queryTaskDefine(renameInfo.getTaskKey());
            FileBucketNameParam param = new FileBucketNameParam(taskDefine.getDataScheme());
            this.attachmentFileAreaService.update(param, renameInfo.getFileKey(), updateInfo);
            ArrayList<IFileListener> fileListeners = new ArrayList<IFileListener>();
            if (null != this.fileListenerProviders) {
                for (IFileListenerProvider fileListenerProvider : this.fileListenerProviders) {
                    IFileListener fileListener = fileListenerProvider.getFileListener(null);
                    if (null == fileListener) continue;
                    fileListeners.add(fileListener);
                }
            }
            for (IFileListener fileListener : fileListeners) {
                FileUpdateEvent fileUpdateEvent = new FileUpdateEvent(taskDefine.getDataScheme(), renameInfo.getTaskKey(), renameInfo.getFormSchemeKey(), renameInfo.getFileKey());
                fileListener.afterFileUpdate(fileUpdateEvent);
            }
            logHellperUtil.info(renameInfo.getTaskKey(), null, "\u9644\u4ef6\u91cd\u547d\u540d\u6210\u529f", "\u9644\u4ef6\u91cd\u547d\u540d\u6210\u529f");
        }
        catch (ObjectStorageException e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            logHellperUtil.error(renameInfo.getTaskKey(), null, "\u9644\u4ef6\u91cd\u547d\u540d\u5931\u8d25", PromptConsts.fileOprateSystemError(e.getMessage()));
        }
    }

    @Override
    public void updateCreateTime(FileRelateGroupInfo updateInfo) {
        LogHellperUtil logHellperUtil = new LogHellperUtil(this.dataServiceLoggerFactory, MOUDLE);
        HashMap<String, String> updateInfoMap = new HashMap<String, String>();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String createTime = simpleDateFormat.format(new Date());
        updateInfoMap.put("createtime", createTime);
        FileBucketNameParam param = new FileBucketNameParam(updateInfo.getDataSchemeKey());
        this.attachmentFileAreaService.batchupdate(param, updateInfo.getFileKeys(), updateInfoMap);
        logHellperUtil.info(updateInfo.getTaskKey(), null, "\u66f4\u65b0\u9644\u4ef6\u4e0a\u4f20\u65f6\u95f4\u6210\u529f", "\u66f4\u65b0\u9644\u4ef6\u4e0a\u4f20\u65f6\u95f4\u6210\u529f");
    }

    @Override
    public void changeFileCategory(ChangeFileCategoryInfo changeFileCategoryInfo) {
        LogHellperUtil logHellperUtil = new LogHellperUtil(this.dataServiceLoggerFactory, MOUDLE);
        try {
            FileBucketNameParam param = new FileBucketNameParam(changeFileCategoryInfo.getParams().getDataSchemeKey());
            ObjectInfo objectInfo = this.attachmentFileAreaService.getObjectInfo(param, changeFileCategoryInfo.getFileKey());
            if (StringUtils.isNotEmpty((String)((String)objectInfo.getExtProp().get("category")))) {
                HashMap<String, String> updateInfo = new HashMap<String, String>();
                updateInfo.put("category", changeFileCategoryInfo.getCategory());
                this.attachmentFileAreaService.update(param, changeFileCategoryInfo.getFileKey(), updateInfo);
            } else {
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                this.attachmentFileAreaService.download(param, changeFileCategoryInfo.getFileKey(), outputStream);
                ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
                this.attachmentFileAreaService.delete(param, changeFileCategoryInfo.getFileKey());
                HashMap<String, String> expandInfo = new HashMap<String, String>();
                if (StringUtils.isNotEmpty((String)((String)objectInfo.getExtProp().get("secretlevel")))) {
                    expandInfo.put("secretlevel", (String)objectInfo.getExtProp().get("secretlevel"));
                }
                expandInfo.put("filePool", (String)objectInfo.getExtProp().get("filePool"));
                expandInfo.put("category", changeFileCategoryInfo.getCategory());
                this.attachmentFileAreaService.uploadByKey(param, objectInfo.getName(), objectInfo.getKey(), objectInfo.getOwner(), objectInfo.getCreateTime(), inputStream, expandInfo);
            }
            logHellperUtil.info(null, null, "\u9644\u4ef6\u7c7b\u522b\u4fee\u6539\u6210\u529f", "\u9644\u4ef6\u7c7b\u522b\u4fee\u6539\u6210\u529f");
        }
        catch (ObjectStorageException e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            logHellperUtil.error(null, null, "\u9644\u4ef6\u7c7b\u522b\u4fee\u6539\u5931\u8d25", PromptConsts.fileOprateSystemError(e.getMessage()));
        }
    }

    @Override
    public void changeFileInfoAfterUpload(String fileKey, String fileSecret, String category, CommonParamsDTO params) {
        LogHellperUtil logHellperUtil = new LogHellperUtil(this.dataServiceLoggerFactory, MOUDLE);
        FileBucketNameParam param = new FileBucketNameParam(params.getDataSchemeKey());
        ObjectInfo objectInfo = this.attachmentFileAreaService.getObjectInfo(param, fileKey);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        this.attachmentFileAreaService.download(param, fileKey, outputStream);
        ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
        this.attachmentFileAreaService.delete(param, fileKey);
        HashMap<String, String> expandInfo = new HashMap<String, String>();
        expandInfo.put("filePool", (String)objectInfo.getExtProp().get("filePool"));
        if (StringUtils.isEmpty((String)fileSecret)) {
            expandInfo.put("secretlevel", (String)objectInfo.getExtProp().get("secretlevel"));
        } else {
            expandInfo.put("secretlevel", fileSecret);
        }
        if (StringUtils.isEmpty((String)category)) {
            expandInfo.put("category", (String)objectInfo.getExtProp().get("category"));
        } else {
            expandInfo.put("category", category);
        }
        this.attachmentFileAreaService.uploadByKey(param, objectInfo.getName(), objectInfo.getKey(), inputStream, expandInfo);
        logHellperUtil.info(params.getTaskKey(), null, "\u9644\u4ef6\u4e0a\u4f20\u6210\u529f", "\u9644\u4ef6\u4e0a\u4f20\u6210\u529f");
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public RowDataValues search(SearchContext searchContext) {
        RowDataValues rowDataValues = new RowDataValues();
        ArrayList<RowDataInfo> rowDataInfos = new ArrayList<RowDataInfo>();
        TaskDefine taskDefine = this.iRunTimeViewController.queryTaskDefine(searchContext.getTaskKey());
        IEntityDefine entityDefine = this.entityMetaService.queryEntity(taskDefine.getDw());
        String dwDimensionName = entityDefine.getDimensionName();
        FileBucketNameParam param = new FileBucketNameParam(searchContext.getDataSchemeKey());
        boolean isOpenSecretLevel = this.stateSecretLevelService.secretLevelEnable(searchContext.getTaskKey());
        boolean openFileCategory = this.isOpenFileCategory();
        if (!searchContext.isNotReferences()) {
            List<Object> formKeys = new ArrayList<String>();
            IDataAccessService dataAccessService = this.iDataAccessServiceProvider.getDataAccessService(searchContext.getTaskKey(), searchContext.getFormscheme());
            if (StringUtils.isEmpty((String)searchContext.getFieldKey())) {
                if (StringUtils.isEmpty((String)searchContext.getFormKey())) {
                    List formDefineList = this.iRunTimeViewController.queryAllFormDefinesByFormScheme(searchContext.getFormscheme());
                    formKeys = formDefineList.stream().map(IBaseMetaItem::getKey).collect(Collectors.toList());
                } else {
                    formKeys.add(searchContext.getFormKey());
                }
            }
            DimensionCollection dimensionCollection = searchContext.getDimensionCollection();
            List dimensionCombinations = dimensionCollection.getDimensionCombinations();
            HashMap<Boolean, List<Object>> writeAccessGroupKeyMap = new HashMap<Boolean, List<Object>>();
            HashMap<String, SearchFileInfo> writeAccessDWMap = new HashMap<String, SearchFileInfo>();
            if (StringUtils.isNotEmpty((String)searchContext.getFieldKey())) {
                try {
                    for (DimensionCombination dimensionCombination : dimensionCombinations) {
                        IAccessResult readaccess = dataAccessService.readable(dimensionCombination, searchContext.getFormKey());
                        if (!readaccess.haveAccess()) continue;
                        DimensionValueSet dimensionValueSet = dimensionCombination.toDimensionValueSet();
                        List<String> groupKeyList = null;
                        if (StringUtils.isEmpty((String)searchContext.getGroupKey())) {
                            groupKeyList = this.fileOperationService.getGroupKeys(searchContext.getFormscheme(), dimensionValueSet, searchContext.getFormKey(), searchContext.getFieldKey());
                        } else {
                            groupKeyList = new ArrayList();
                            groupKeyList.add(searchContext.getGroupKey());
                        }
                        IAccessResult iAccessResult = dataAccessService.writeable(dimensionCombination, searchContext.getFormKey());
                        if (writeAccessGroupKeyMap.containsKey(iAccessResult.haveAccess())) {
                            List canWriteGroupKeys = (List)writeAccessGroupKeyMap.get(iAccessResult.haveAccess());
                            canWriteGroupKeys.addAll(groupKeyList);
                            for (String groupKey : groupKeyList) {
                                writeAccessDWMap.put(groupKey, new SearchFileInfo(dimensionValueSet.getValue(dwDimensionName).toString(), searchContext.getFormKey()));
                            }
                            continue;
                        }
                        writeAccessGroupKeyMap.put(iAccessResult.haveAccess(), groupKeyList);
                        for (String groupKey : groupKeyList) {
                            writeAccessDWMap.put(groupKey, new SearchFileInfo(dimensionValueSet.getValue(dwDimensionName).toString(), searchContext.getFormKey()));
                        }
                    }
                }
                catch (Exception e) {
                    logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                }
            } else {
                IBatchAccessResult readAccess = dataAccessService.getReadAccess(dimensionCollection, formKeys);
                IBatchAccessResult writeAccess = dataAccessService.getWriteAccess(dimensionCollection, formKeys);
                try {
                    for (DimensionCombination dimensionCombination : dimensionCombinations) {
                        for (String string : formKeys) {
                            IAccessResult access = readAccess.getAccess(dimensionCombination, string);
                            if (!access.haveAccess()) continue;
                            List allFieldKeys = this.iRunTimeViewController.getFieldKeysInForm(string);
                            List fieldDefines = this.dataDefinitionRuntimeController.queryFieldDefines((Collection)allFieldKeys);
                            for (FieldDefine fieldDefine : fieldDefines) {
                                if (!fieldDefine.getType().equals((Object)FieldType.FIELD_TYPE_FILE)) continue;
                                IAccessResult formWriteAccess = writeAccess.getAccess(dimensionCombination, string);
                                DimensionValueSet dimensionValueSet = dimensionCombination.toDimensionValueSet();
                                List<Object> groupKeysList = null;
                                if (StringUtils.isEmpty((String)searchContext.getGroupKey())) {
                                    groupKeysList = this.fileOperationService.getGroupKeys(searchContext.getFormscheme(), dimensionValueSet, string, fieldDefine.getKey());
                                } else {
                                    groupKeysList = new ArrayList<String>();
                                    groupKeysList.add(searchContext.getGroupKey());
                                }
                                boolean canWrite = formWriteAccess.haveAccess();
                                if (writeAccessGroupKeyMap.containsKey(canWrite)) {
                                    List canWriteGroupKeys = (List)writeAccessGroupKeyMap.get(canWrite);
                                    canWriteGroupKeys.addAll(groupKeysList);
                                    for (String string2 : groupKeysList) {
                                        writeAccessDWMap.put(string2, new SearchFileInfo(dimensionValueSet.getValue(dwDimensionName).toString(), string));
                                    }
                                    continue;
                                }
                                writeAccessGroupKeyMap.put(canWrite, groupKeysList);
                                for (String string3 : groupKeysList) {
                                    writeAccessDWMap.put(string3, new SearchFileInfo(dimensionValueSet.getValue(dwDimensionName).toString(), string));
                                }
                            }
                        }
                    }
                }
                catch (Exception e) {
                    logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                }
            }
            String defaultFileCategoryCode = null;
            if (openFileCategory) {
                defaultFileCategoryCode = this.fileCategoryService.getDefaultFileCategoryCode();
            }
            HashMap<String, SecretLevel> secretLevelCatch = new HashMap<String, SecretLevel>();
            HashMap<String, String> categoryTitleCatch = new HashMap<String, String>();
            for (Boolean canWrite : writeAccessGroupKeyMap.keySet()) {
                List list = (List)writeAccessGroupKeyMap.get(canWrite);
                ArrayList<String> fileKeys = new ArrayList<String>();
                ArrayList<Object> resGroupKeys = new ArrayList<Object>();
                DataAccessContext context = new DataAccessContext(this.dataModelService);
                DataScheme dataScheme = this.runtimeDataSchemeService.getDataScheme(searchContext.getDataSchemeKey());
                String tableName = this.fileOperationService.getCurrentSplitTableName(dataScheme);
                TableModelDefine filePoolTable = this.dataModelService.getTableModelDefineByName(tableName);
                if (null == filePoolTable) {
                    throw new NotFoundTableDefineException(new String[]{"\u9644\u4ef6\u5173\u8054\u8868\u672a\u521b\u5efa\uff0c\u8bf7\u91cd\u65b0\u53d1\u5e03\u6570\u636e\u65b9\u6848\uff01"});
                }
                List filePoolFields = this.dataModelService.getColumnModelDefinesByTable(filePoolTable.getID());
                NvwaQueryModel queryModel = new NvwaQueryModel();
                for (ColumnModelDefine filePoolField : filePoolFields) {
                    queryModel.getColumns().add(new NvwaQueryColumn(filePoolField));
                    if (filePoolField.getCode().equals("GROUPKEY")) {
                        queryModel.getColumnFilters().put(filePoolField, list);
                        continue;
                    }
                    if (!filePoolField.getCode().equals("ISDELETE")) continue;
                    queryModel.getColumnFilters().put(filePoolField, "0");
                }
                INvwaDataAccess readOnlyDataAccess = this.iNvwaDataAccessProvider.createReadOnlyDataAccess(queryModel);
                MemoryDataSet dataTable = null;
                try {
                    void var33_68;
                    dataTable = readOnlyDataAccess.executeQuery(context);
                    List list2 = queryModel.getColumns();
                    boolean bl = false;
                    while (var33_68 < dataTable.size()) {
                        DataRow item = dataTable.get((int)var33_68);
                        for (int j = 0; j < list2.size(); ++j) {
                            if (((NvwaQueryColumn)list2.get(j)).getColumnModel().getCode().equals("FILEKEY")) {
                                String fileKey = item.getString(j);
                                fileKeys.add(fileKey);
                                continue;
                            }
                            if (!((NvwaQueryColumn)list2.get(j)).getColumnModel().getCode().equals("GROUPKEY")) continue;
                            String groupkey = item.getString(j);
                            resGroupKeys.add(groupkey);
                        }
                        ++var33_68;
                    }
                }
                catch (Exception exception) {
                    logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + exception.getMessage(), exception);
                }
                List<FileInfo> list3 = this.attachmentFileAreaService.getFileInfoByKeys(param, fileKeys);
                for (String fileKey : fileKeys) {
                    boolean isFind = false;
                    for (FileInfo fileInfo : list3) {
                        if (!fileKey.equals(fileInfo.getKey())) continue;
                        isFind = true;
                        if (isOpenSecretLevel) {
                            SecretLevel secretLevel = (SecretLevel)secretLevelCatch.get(fileInfo.getSecretlevel());
                            if (null == secretLevel) {
                                secretLevel = this.stateSecretLevelService.getSecretLevelItem(fileInfo.getSecretlevel());
                                secretLevelCatch.put(fileInfo.getSecretlevel(), secretLevel);
                            }
                            if (!this.stateSecretLevelService.canAccess(secretLevel)) continue;
                        }
                        if (openFileCategory) {
                            String fileCategory;
                            String string = fileCategory = StringUtils.isEmpty((String)fileInfo.getCategory()) ? defaultFileCategoryCode : fileInfo.getCategory();
                            if (StringUtils.isNotEmpty((String)searchContext.getCategory()) && !fileCategory.equals(searchContext.getCategory())) continue;
                        }
                        RowDataInfo rowDataInfo = new RowDataInfo();
                        rowDataInfo.setFileKey(fileInfo.getKey());
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
                        if (openFileCategory) {
                            String categoryTitle;
                            if (StringUtils.isEmpty((String)fileInfo.getCategory())) {
                                categoryTitle = (String)categoryTitleCatch.get(defaultFileCategoryCode);
                                if (StringUtils.isEmpty((String)categoryTitle)) {
                                    categoryTitle = this.fileCategoryService.getFileCategoryTitle(defaultFileCategoryCode);
                                    categoryTitleCatch.put(defaultFileCategoryCode, categoryTitle);
                                }
                                rowDataInfo.setCategory(categoryTitle);
                            } else {
                                categoryTitle = (String)categoryTitleCatch.get(fileInfo.getCategory());
                                if (StringUtils.isEmpty((String)categoryTitle)) {
                                    categoryTitle = this.fileCategoryService.getFileCategoryTitle(fileInfo.getCategory());
                                    categoryTitleCatch.put(fileInfo.getCategory(), categoryTitle);
                                }
                                rowDataInfo.setCategory(categoryTitle);
                            }
                        }
                        rowDataInfo.setWriteable(canWrite);
                        rowDataInfo.setGroupKey((String)resGroupKeys.get(fileKeys.indexOf(fileKey)));
                        rowDataInfo.setDw(((SearchFileInfo)writeAccessDWMap.get(resGroupKeys.get(fileKeys.indexOf(fileKey)))).getDW());
                        rowDataInfo.setFormKey(((SearchFileInfo)writeAccessDWMap.get(resGroupKeys.get(fileKeys.indexOf(fileKey)))).getFormKey());
                        if (StringUtils.isNotEmpty((String)fileInfo.getFilepoolKey())) {
                            rowDataInfo.setFilepool(true);
                        }
                        rowDataInfos.add(rowDataInfo);
                        break;
                    }
                    if (isFind || openFileCategory && StringUtils.isNotEmpty((String)searchContext.getCategory())) continue;
                    RowDataInfo rowDataInfo = new RowDataInfo();
                    rowDataInfo.setFileKey(fileKey);
                    String language = NpContextHolder.getContext().getLocale().getLanguage();
                    if (StringUtils.isEmpty((String)language) || "zh".equals(language)) {
                        rowDataInfo.setName("\u5df2\u5220\u9664");
                    } else {
                        rowDataInfo.setName("Deleted");
                    }
                    Date createTime = new Date();
                    rowDataInfo.setCreatetime(createTime);
                    rowDataInfo.setSize(0L);
                    if (isOpenSecretLevel) {
                        rowDataInfo.setConfidential("---");
                    }
                    if (this.isOpenFileCategory()) {
                        rowDataInfo.setCategory("---");
                    }
                    rowDataInfo.setWriteable(canWrite);
                    rowDataInfo.setGroupKey((String)resGroupKeys.get(fileKeys.indexOf(fileKey)));
                    rowDataInfo.setDw(((SearchFileInfo)writeAccessDWMap.get(resGroupKeys.get(fileKeys.indexOf(fileKey)))).getDW());
                    rowDataInfo.setFormKey(((SearchFileInfo)writeAccessDWMap.get(resGroupKeys.get(fileKeys.indexOf(fileKey)))).getFormKey());
                    rowDataInfo.setFilepool(true);
                    rowDataInfos.add(rowDataInfo);
                }
            }
        } else {
            String defaultFileCategoryCode = null;
            if (openFileCategory) {
                defaultFileCategoryCode = this.fileCategoryService.getDefaultFileCategoryCode();
            }
            HashMap<String, SecretLevel> secretLevelCatch = new HashMap<String, SecretLevel>();
            HashMap<String, String> categoryTitleCatch = new HashMap<String, String>();
            DimensionCollection dimensionCollection = searchContext.getDimensionCollection();
            List dimensionCombinations = dimensionCollection.getDimensionCombinations();
            for (DimensionCombination dimensionCombination : dimensionCombinations) {
                DimensionValueSet dimensionValueSet = dimensionCombination.toDimensionValueSet();
                List<FileInfo> fileInfos = this.getUnReferFilePoolAttachments(searchContext.getDataSchemeKey(), searchContext.getTaskKey(), dimensionCombination);
                for (FileInfo fileInfo : fileInfos) {
                    if (isOpenSecretLevel) {
                        void var21_42;
                        SecretLevel secretLevel = (SecretLevel)secretLevelCatch.get(fileInfo.getSecretlevel());
                        if (null == secretLevel) {
                            SecretLevel secretLevel2 = this.stateSecretLevelService.getSecretLevelItem(fileInfo.getSecretlevel());
                            secretLevelCatch.put(fileInfo.getSecretlevel(), secretLevel2);
                        }
                        if (!this.stateSecretLevelService.canAccess((SecretLevel)var21_42)) continue;
                    }
                    if (openFileCategory) {
                        String string;
                        String string4 = string = StringUtils.isEmpty((String)fileInfo.getCategory()) ? defaultFileCategoryCode : fileInfo.getCategory();
                        if (StringUtils.isNotEmpty((String)searchContext.getCategory()) && !string.equals(searchContext.getCategory())) continue;
                    }
                    RowDataInfo rowDataInfo = new RowDataInfo();
                    rowDataInfo.setFileKey(fileInfo.getKey());
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
                    if (openFileCategory) {
                        String categoryTitle;
                        if (StringUtils.isEmpty((String)fileInfo.getCategory())) {
                            categoryTitle = (String)categoryTitleCatch.get(defaultFileCategoryCode);
                            if (StringUtils.isEmpty((String)categoryTitle)) {
                                categoryTitle = this.fileCategoryService.getFileCategoryTitle(defaultFileCategoryCode);
                                categoryTitleCatch.put(defaultFileCategoryCode, categoryTitle);
                            }
                            rowDataInfo.setCategory(categoryTitle);
                        } else {
                            categoryTitle = (String)categoryTitleCatch.get(fileInfo.getCategory());
                            if (StringUtils.isEmpty((String)categoryTitle)) {
                                categoryTitle = this.fileCategoryService.getFileCategoryTitle(fileInfo.getCategory());
                                categoryTitleCatch.put(fileInfo.getCategory(), categoryTitle);
                            }
                            rowDataInfo.setCategory(categoryTitle);
                        }
                    }
                    rowDataInfo.setWriteable(true);
                    rowDataInfo.setDw(dimensionValueSet.getValue(dwDimensionName).toString());
                    rowDataInfos.add(rowDataInfo);
                }
            }
        }
        this.filterFiles(rowDataValues, rowDataInfos, searchContext.getTypes(), searchContext.getSearchInfo(), searchContext.getOrder(), searchContext.getSortBy(), searchContext.isPage(), searchContext.getCurrentPage(), searchContext.getPageSize());
        return rowDataValues;
    }

    private List<FileInfo> getUnReferFilePoolAttachments(String dataSchemeKey, String taskKey, DimensionCombination dimensionCombination) {
        DimensionValueSet dimension = dimensionCombination.toDimensionValueSet();
        TaskDefine taskDefine = this.iRunTimeViewController.queryTaskDefine(taskKey);
        IEntityDefine entityDefine = this.entityMetaService.queryEntity(taskDefine.getDw());
        String filePoolKey = taskKey + dimension.getValue(entityDefine.getDimensionName());
        ArrayList<FileInfo> fileInfoResult = new ArrayList<FileInfo>();
        ArrayList<String> fileKeys = new ArrayList<String>();
        FileBucketNameParam param = new FileBucketNameParam(dataSchemeKey);
        List<FileInfo> fileInfos = this.attachmentFileAreaService.getFileInfoByProp(param, "filePool", filePoolKey);
        for (FileInfo fileInfo : fileInfos) {
            fileInfoResult.add(fileInfo);
            fileKeys.add(fileInfo.getKey());
        }
        ArrayList removeFileInfoResult = new ArrayList();
        DataAccessContext context = new DataAccessContext(this.dataModelService);
        DataScheme dataScheme = this.runtimeDataSchemeService.getDataScheme(dataSchemeKey);
        String tableName = this.fileOperationService.getCurrentSplitTableName(dataScheme);
        TableModelDefine filePoolTable = this.dataModelService.getTableModelDefineByName(tableName);
        if (null == filePoolTable) {
            throw new NotFoundTableDefineException(new String[]{"\u9644\u4ef6\u5173\u8054\u8868\u672a\u521b\u5efa\uff0c\u8bf7\u91cd\u65b0\u53d1\u5e03\u6570\u636e\u65b9\u6848\uff01"});
        }
        List filePoolFields = this.dataModelService.getColumnModelDefinesByTable(filePoolTable.getID());
        NvwaQueryModel queryModel = new NvwaQueryModel();
        for (ColumnModelDefine filePoolField : filePoolFields) {
            queryModel.getColumns().add(new NvwaQueryColumn(filePoolField));
            if (filePoolField.getCode().equals("FILEKEY")) {
                queryModel.getColumnFilters().put(filePoolField, fileKeys);
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
                    String fileKey;
                    if (!((NvwaQueryColumn)columns.get(j)).getColumnModel().getCode().equals("FILEKEY") || !fileKeys.contains(fileKey = item.getString(j))) continue;
                    removeFileInfoResult.add(fileInfoResult.get(fileKeys.indexOf(fileKey)));
                }
            }
            fileInfoResult.removeAll(removeFileInfoResult);
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
        return fileInfoResult;
    }

    private void filterFiles(RowDataValues rowDataValues, List<RowDataInfo> rowDataInfos, List<String> types, String searchInfo, String order, String sortBy, boolean page, Integer currentPage, Integer pageSize) {
        Object fileSuffix;
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
            String fileName;
            rowDatasCopy = new ArrayList();
            if (null != types && !types.isEmpty() && !"*".equals(types.get(0))) {
                block1: for (RowDataInfo rowDataInfo : rowDataInfos) {
                    int fileNameIndex;
                    fileName = rowDataInfo.getName();
                    if (!fileName.contains(searchInfo) || (fileNameIndex = fileName.lastIndexOf(".")) < 0) continue;
                    String fileSuffix2 = fileName.substring(fileNameIndex);
                    for (String string : types) {
                        if (!fileSuffix2.contains(string)) continue;
                        rowDatasCopy.add(rowDataInfo);
                        continue block1;
                    }
                }
            } else if (null != types && !types.isEmpty() && "*".equals(types.get(0))) {
                for (RowDataInfo rowDataInfo : rowDataInfos) {
                    fileName = rowDataInfo.getName();
                    if (!fileName.contains(searchInfo)) continue;
                    boolean flag = true;
                    int fileNameIndex = fileName.lastIndexOf(".");
                    if (fileNameIndex >= 0) {
                        fileSuffix = fileName.substring(fileNameIndex);
                        for (String type : FileTypes.ALL.getType()) {
                            if (!((String)fileSuffix).contains(type)) continue;
                            flag = false;
                            break;
                        }
                    }
                    if (!flag) continue;
                    rowDatasCopy.add(rowDataInfo);
                }
            } else {
                for (RowDataInfo rowDataInfo : rowDataInfos) {
                    if (!rowDataInfo.getName().contains(searchInfo)) continue;
                    rowDatasCopy.add(rowDataInfo);
                }
            }
            rowDataInfos.clear();
            rowDataInfos.addAll(rowDatasCopy);
        } else {
            rowDatasCopy = new ArrayList();
            if (null != types && !types.isEmpty() && !"*".equals(types.get(0))) {
                block6: for (RowDataInfo rowDataInfo : rowDataInfos) {
                    String fileName = rowDataInfo.getName();
                    int fileNameIndex = fileName.lastIndexOf(".");
                    if (fileNameIndex < 0) continue;
                    String fileSuffix3 = fileName.substring(fileNameIndex);
                    for (String string : types) {
                        if (!fileSuffix3.contains(string)) continue;
                        rowDatasCopy.add(rowDataInfo);
                        continue block6;
                    }
                }
            } else if (null != types && !types.isEmpty() && "*".equals(types.get(0))) {
                for (RowDataInfo rowDataInfo : rowDataInfos) {
                    boolean flag = true;
                    String fileName = rowDataInfo.getName();
                    int fileNameIndex = fileName.lastIndexOf(".");
                    if (fileNameIndex >= 0) {
                        fileSuffix = fileName.substring(fileNameIndex);
                        for (String type : FileTypes.ALL.getType()) {
                            if (!((String)fileSuffix).contains(type)) continue;
                            flag = false;
                            break;
                        }
                    }
                    if (!flag) continue;
                    rowDatasCopy.add(rowDataInfo);
                }
            } else {
                rowDatasCopy.addAll(rowDataInfos);
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

    @Override
    public List<ReferencesInfo> getReferences(String fileKey, String dataSchemeKey) {
        ArrayList<ReferencesInfo> referencesInfos = new ArrayList<ReferencesInfo>();
        DataAccessContext context = new DataAccessContext(this.dataModelService);
        NvwaQueryModel queryModel = new NvwaQueryModel();
        DataScheme dataScheme = this.runtimeDataSchemeService.getDataScheme(dataSchemeKey);
        String tableName = this.fileOperationService.getCurrentSplitTableName(dataScheme);
        TableModelDefine filePoolTable = this.dataModelService.getTableModelDefineByName(tableName);
        if (null == filePoolTable) {
            throw new NotFoundTableDefineException(new String[]{"\u9644\u4ef6\u5173\u8054\u8868\u672a\u521b\u5efa\uff0c\u8bf7\u91cd\u65b0\u53d1\u5e03\u6570\u636e\u65b9\u6848\uff01"});
        }
        List filePoolFields = this.dataModelService.getColumnModelDefinesByTable(filePoolTable.getID());
        for (ColumnModelDefine filePoolField : filePoolFields) {
            queryModel.getColumns().add(new NvwaQueryColumn(filePoolField));
            if (filePoolField.getCode().equals("FILEKEY")) {
                queryModel.getColumnFilters().put(filePoolField, fileKey);
                continue;
            }
            if (!filePoolField.getCode().equals("ISDELETE")) continue;
            queryModel.getColumnFilters().put(filePoolField, "0");
        }
        HashMap<String, IEntityTable> entityTableMap = new HashMap<String, IEntityTable>();
        INvwaDataAccess readOnlyDataAccess = this.iNvwaDataAccessProvider.createReadOnlyDataAccess(queryModel);
        MemoryDataSet dataTable = null;
        ArrayList<FileRefInfo> fileRefInfos = new ArrayList<FileRefInfo>();
        try {
            String DWCode;
            dataTable = readOnlyDataAccess.executeQuery(context);
            List columns = queryModel.getColumns();
            for (int i = 0; i < dataTable.size(); ++i) {
                FileRefInfo fileRefInfo = new FileRefInfo();
                DataRow item = dataTable.get(i);
                for (int j = 0; j < columns.size(); ++j) {
                    if (((NvwaQueryColumn)columns.get(j)).getColumnModel().getCode().equals("PERIOD")) {
                        String dateCode = item.getString(j);
                        String dateStrFromPeriod = PeriodUtils.getDateStrFromPeriod((String)dateCode);
                        fileRefInfo.setDate(dateStrFromPeriod);
                        continue;
                    }
                    if (((NvwaQueryColumn)columns.get(j)).getColumnModel().getCode().equals("MDCODE")) {
                        DWCode = item.getString(j);
                        fileRefInfo.setDWCode(DWCode);
                        continue;
                    }
                    if (!((NvwaQueryColumn)columns.get(j)).getColumnModel().getCode().equals("FIELD_KEY")) continue;
                    String fieldKey = item.getString(j);
                    FieldDefine fieldDefine = this.iRunTimeViewController.queryFieldDefine(fieldKey);
                    fileRefInfo.setFieldKey(fieldDefine.getKey());
                    fileRefInfo.setFieldName(fieldDefine.getTitle());
                }
                fileRefInfos.add(fileRefInfo);
            }
            for (FileRefInfo fileRefInfo : fileRefInfos) {
                List dataLinkDefines = this.iRunTimeViewController.getDataLinksByField(fileRefInfo.getFieldKey());
                for (DataLinkDefine dataLinkDefine : dataLinkDefines) {
                    DataRegionDefine dataRegionDefine = this.iRunTimeViewController.queryDataRegionDefine(dataLinkDefine.getRegionKey());
                    List formSchemeDefines = this.iRunTimeViewController.queryFormSchemeByForm(dataRegionDefine.getFormKey());
                    for (FormSchemeDefine formSchemeDefine : formSchemeDefines) {
                        ReferencesInfo referencesInfo = new ReferencesInfo();
                        referencesInfo.setDate(fileRefInfo.getDate());
                        referencesInfo.setDWName(fileRefInfo.getDWCode());
                        referencesInfo.setZBName(fileRefInfo.getFieldName());
                        FormDefine formDefine = this.iRunTimeViewController.queryFormById(dataRegionDefine.getFormKey());
                        referencesInfo.setFormName(formDefine.getTitle());
                        referencesInfo.setTaskName(formSchemeDefine.getTaskKey());
                        referencesInfos.add(referencesInfo);
                    }
                }
            }
            for (ReferencesInfo referencesInfo : referencesInfos) {
                String taskKey = referencesInfo.getTaskName();
                TaskDefine taskDefine = this.iRunTimeViewController.queryTaskDefine(taskKey);
                referencesInfo.setTaskName(taskDefine.getTitle());
                DWCode = referencesInfo.getDWName();
                if (entityTableMap.containsKey(taskDefine.getDw())) {
                    IEntityTable iEntityTable = (IEntityTable)entityTableMap.get(taskDefine.getDw());
                    IEntityRow row = iEntityTable.findByEntityKey(DWCode);
                    referencesInfo.setDWName(row.getTitle());
                    continue;
                }
                IEntityQuery iEntityQuery = this.entityDataService.newEntityQuery();
                EntityViewDefine entityView = this.entityViewRunTimeController.buildEntityView(taskDefine.getDw());
                iEntityQuery.setEntityView(entityView);
                iEntityQuery.setAuthorityOperations(AuthorityType.None);
                ExecutorContext executorContext = new ExecutorContext(this.runtimeController);
                IEntityTable iEntityTable = iEntityQuery.executeReader((IContext)executorContext);
                entityTableMap.put(taskDefine.getDw(), iEntityTable);
                IEntityRow row = iEntityTable.findByEntityKey(DWCode);
                referencesInfo.setDWName(row.getTitle());
            }
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
        return referencesInfos;
    }

    @Override
    public Map<String, List<FileBaseRefInfo>> getBaseReferences(List<String> fileKeys, String dataSchemeKey) {
        HashMap<String, List<FileBaseRefInfo>> fileBaseRefMap = new HashMap<String, List<FileBaseRefInfo>>();
        DataAccessContext context = new DataAccessContext(this.dataModelService);
        NvwaQueryModel queryModel = new NvwaQueryModel();
        DataScheme dataScheme = this.runtimeDataSchemeService.getDataScheme(dataSchemeKey);
        String tableName = this.fileOperationService.getCurrentSplitTableName(dataScheme);
        TableModelDefine filePoolTable = this.dataModelService.getTableModelDefineByName(tableName);
        if (null == filePoolTable) {
            throw new NotFoundTableDefineException(new String[]{"\u9644\u4ef6\u5173\u8054\u8868\u672a\u521b\u5efa\uff0c\u8bf7\u91cd\u65b0\u53d1\u5e03\u6570\u636e\u65b9\u6848"});
        }
        int fileKeyColumnIndex = 0;
        int dwColumnIndex = 0;
        int fieldKeyColumnIndex = 0;
        List filePoolFields = this.dataModelService.getColumnModelDefinesByTable(filePoolTable.getID());
        for (ColumnModelDefine filePoolField : filePoolFields) {
            queryModel.getColumns().add(new NvwaQueryColumn(filePoolField));
            switch (filePoolField.getCode()) {
                case "FILEKEY": {
                    fileKeyColumnIndex = filePoolFields.indexOf(filePoolField);
                    queryModel.getColumnFilters().put(filePoolField, fileKeys);
                    break;
                }
                case "ISDELETE": {
                    queryModel.getColumnFilters().put(filePoolField, "0");
                    break;
                }
                case "MDCODE": {
                    dwColumnIndex = filePoolFields.indexOf(filePoolField);
                    break;
                }
                case "FIELD_KEY": {
                    fieldKeyColumnIndex = filePoolFields.indexOf(filePoolField);
                }
            }
        }
        INvwaDataAccess readOnlyDataAccess = this.iNvwaDataAccessProvider.createReadOnlyDataAccess(queryModel);
        try {
            HashMap<String, List> fileRefInfoMap = new HashMap<String, List>();
            MemoryDataSet dataTable = readOnlyDataAccess.executeQuery(context);
            for (int i = 0; i < dataTable.size(); ++i) {
                DataRow item = dataTable.get(i);
                String fileKey = item.getString(fileKeyColumnIndex);
                List fileRefInfos = fileRefInfoMap.computeIfAbsent(fileKey, k -> new ArrayList());
                FileRefInfo fileRefInfo = new FileRefInfo();
                fileRefInfo.setDWCode(item.getString(dwColumnIndex));
                fileRefInfo.setFieldKey(item.getString(fieldKeyColumnIndex));
                fileRefInfos.add(fileRefInfo);
            }
            Set fileKeySets = fileRefInfoMap.keySet();
            for (String fileKey : fileKeySets) {
                List fileRefInfoList = (List)fileRefInfoMap.get(fileKey);
                ArrayList<FileBaseRefInfo> fileBaseRefInfos = new ArrayList<FileBaseRefInfo>();
                for (FileRefInfo fileRefInfo : fileRefInfoList) {
                    Collection formKeys = this.iRunTimeViewController.getFormKeysByField(fileRefInfo.getFieldKey());
                    for (String formKey : formKeys) {
                        FileBaseRefInfo fileBaseRefInfo = new FileBaseRefInfo(fileRefInfo.getDWCode(), formKey);
                        fileBaseRefInfos.add(fileBaseRefInfo);
                    }
                }
                fileBaseRefMap.put(fileKey, fileBaseRefInfos);
            }
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
        return fileBaseRefMap;
    }

    @Override
    public RowDataValues getFileInfoByFilePool(FilePoolContext filePoolContext) {
        DimensionValueSet dimension = filePoolContext.getDimensionCombination().toDimensionValueSet();
        TaskDefine taskDefine = this.iRunTimeViewController.queryTaskDefine(filePoolContext.getTaskKey());
        IEntityDefine entityDefine = this.entityMetaService.queryEntity(taskDefine.getDw());
        String filePoolKey = filePoolContext.getTaskKey() + dimension.getValue(entityDefine.getDimensionName());
        ArrayList<FileInfo> fileInfoResult = new ArrayList<FileInfo>();
        ArrayList<String> fileKeys = new ArrayList<String>();
        FileBucketNameParam param = new FileBucketNameParam(filePoolContext.getDataSchemeKey());
        List<FileInfo> fileInfos = this.attachmentFileAreaService.getFileInfoByProp(param, "filePool", filePoolKey);
        for (FileInfo fileInfo : fileInfos) {
            fileInfoResult.add(fileInfo);
            fileKeys.add(fileInfo.getKey());
        }
        ArrayList removeFileInfoResult = new ArrayList();
        DataAccessContext context = new DataAccessContext(this.dataModelService);
        DataScheme dataScheme = this.runtimeDataSchemeService.getDataScheme(filePoolContext.getDataSchemeKey());
        String tableName = this.fileOperationService.getCurrentSplitTableName(dataScheme);
        TableModelDefine filePoolTable = this.dataModelService.getTableModelDefineByName(tableName);
        if (null == filePoolTable) {
            throw new NotFoundTableDefineException(new String[]{"\u9644\u4ef6\u5173\u8054\u8868\u672a\u521b\u5efa\uff0c\u8bf7\u91cd\u65b0\u53d1\u5e03\u6570\u636e\u65b9\u6848\uff01"});
        }
        List filePoolFields = this.dataModelService.getColumnModelDefinesByTable(filePoolTable.getID());
        NvwaQueryModel queryModel = new NvwaQueryModel();
        if (filePoolContext.isNotReferences()) {
            for (ColumnModelDefine filePoolField : filePoolFields) {
                queryModel.getColumns().add(new NvwaQueryColumn(filePoolField));
                if (filePoolField.getCode().equals("FILEKEY")) {
                    queryModel.getColumnFilters().put(filePoolField, fileKeys);
                    continue;
                }
                if (!filePoolField.getCode().equals("ISDELETE")) continue;
                queryModel.getColumnFilters().put(filePoolField, "0");
            }
            INvwaDataAccess readOnlyDataAccess = this.iNvwaDataAccessProvider.createReadOnlyDataAccess(queryModel);
            try {
                MemoryDataSet dataTable = readOnlyDataAccess.executeQuery(context);
                List columns = queryModel.getColumns();
                for (int i = 0; i < dataTable.size(); ++i) {
                    DataRow item = dataTable.get(i);
                    for (int j = 0; j < columns.size(); ++j) {
                        String fileKey;
                        if (!((NvwaQueryColumn)columns.get(j)).getColumnModel().getCode().equals("FILEKEY") || !fileKeys.contains(fileKey = item.getString(j))) continue;
                        removeFileInfoResult.add(fileInfoResult.get(fileKeys.indexOf(fileKey)));
                    }
                }
                fileInfoResult.removeAll(removeFileInfoResult);
            }
            catch (Exception e) {
                logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            }
        }
        RowDataValues rowDataValues = new RowDataValues();
        ArrayList<RowDataInfo> rowDataInfos = new ArrayList<RowDataInfo>();
        boolean openFileCategory = this.isOpenFileCategory();
        String defaultFileCategoryCode = null;
        if (openFileCategory) {
            defaultFileCategoryCode = this.fileCategoryService.getDefaultFileCategoryCode();
        }
        HashMap<String, SecretLevel> secretLevelCatch = new HashMap<String, SecretLevel>();
        HashMap<String, String> categoryTitleCatch = new HashMap<String, String>();
        boolean isOpenSecretLevel = this.stateSecretLevelService.secretLevelEnable(filePoolContext.getTaskKey());
        for (FileInfo fileInfo : fileInfoResult) {
            if (isOpenSecretLevel) {
                SecretLevel secretLevel = (SecretLevel)secretLevelCatch.get(fileInfo.getSecretlevel());
                if (null == secretLevel) {
                    secretLevel = this.stateSecretLevelService.getSecretLevelItem(fileInfo.getSecretlevel());
                    secretLevelCatch.put(fileInfo.getSecretlevel(), secretLevel);
                }
                if (!this.stateSecretLevelService.canAccess(secretLevel)) continue;
            }
            if (openFileCategory) {
                String fileCategory;
                String string = fileCategory = StringUtils.isEmpty((String)fileInfo.getCategory()) ? defaultFileCategoryCode : fileInfo.getCategory();
                if (StringUtils.isNotEmpty((String)filePoolContext.getCategory()) && !fileCategory.equals(filePoolContext.getCategory())) continue;
            }
            RowDataInfo rowDataInfo = new RowDataInfo();
            rowDataInfo.setFileKey(fileInfo.getKey());
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
            if (openFileCategory) {
                String categoryTitle;
                if (StringUtils.isEmpty((String)fileInfo.getCategory())) {
                    categoryTitle = (String)categoryTitleCatch.get(defaultFileCategoryCode);
                    if (StringUtils.isEmpty((String)categoryTitle)) {
                        categoryTitle = this.fileCategoryService.getFileCategoryTitle(defaultFileCategoryCode);
                        categoryTitleCatch.put(defaultFileCategoryCode, categoryTitle);
                    }
                    rowDataInfo.setCategory(categoryTitle);
                } else {
                    categoryTitle = (String)categoryTitleCatch.get(fileInfo.getCategory());
                    if (StringUtils.isEmpty((String)categoryTitle)) {
                        categoryTitle = this.fileCategoryService.getFileCategoryTitle(fileInfo.getCategory());
                        categoryTitleCatch.put(fileInfo.getCategory(), categoryTitle);
                    }
                    rowDataInfo.setCategory(categoryTitle);
                }
            }
            rowDataInfo.setWriteable(true);
            rowDataInfo.setDw(dimension.getValue(entityDefine.getDimensionName()).toString());
            rowDataInfos.add(rowDataInfo);
        }
        this.filterFiles(rowDataValues, rowDataInfos, filePoolContext.getTypes(), filePoolContext.getSearchInfo(), filePoolContext.getOrder(), filePoolContext.getSortBy(), filePoolContext.isPage(), filePoolContext.getCurrentPage(), filePoolContext.getPageSize());
        return rowDataValues;
    }

    @Override
    public List<FilePoolFiles> getFilePoolFiles(FilePoolAllFileContext filePoolAllFileContext) {
        String filePoolKey = "";
        if (StringUtils.isNotEmpty((String)filePoolAllFileContext.getFilepoolKey())) {
            filePoolKey = filePoolAllFileContext.getFilepoolKey();
        } else {
            DimensionValueSet dimension = filePoolAllFileContext.getDimensionCombination().toDimensionValueSet();
            TaskDefine taskDefine = this.iRunTimeViewController.queryTaskDefine(filePoolAllFileContext.getTaskKey());
            IEntityDefine entityDefine = this.entityMetaService.queryEntity(taskDefine.getDw());
            filePoolKey = filePoolAllFileContext.getTaskKey() + dimension.getValue(entityDefine.getDimensionName());
        }
        ArrayList<FilePoolFiles> filePoolFilesList = new ArrayList<FilePoolFiles>();
        FileBucketNameParam param = new FileBucketNameParam(filePoolAllFileContext.getDataSchemeKey());
        List<ObjectInfo> prop = this.attachmentFileAreaService.getObjectInfoByProp(param, "filePool", filePoolKey);
        for (ObjectInfo objectInfo : prop) {
            FilePoolFiles filePoolFiles = new FilePoolFiles();
            filePoolFiles.setFileKey(objectInfo.getKey());
            filePoolFiles.setFileName(objectInfo.getName());
            filePoolFiles.setSize(objectInfo.getSize());
            filePoolFiles.setMd5(objectInfo.getMd5());
            filePoolFiles.setSecretCode((String)objectInfo.getExtProp().get("secretlevel"));
            filePoolFiles.setCategoryCode((String)objectInfo.getExtProp().get("category"));
            filePoolFilesList.add(filePoolFiles);
        }
        return filePoolFilesList;
    }

    @Override
    public List<FilePoolFiles> getFilePoolFiles(FilePoolAllFileContext filePoolAllFileContext, boolean exist) {
        String filePoolKey = "";
        if (StringUtils.isNotEmpty((String)filePoolAllFileContext.getFilepoolKey())) {
            filePoolKey = filePoolAllFileContext.getFilepoolKey();
        } else {
            DimensionValueSet dimension = filePoolAllFileContext.getDimensionCombination().toDimensionValueSet();
            TaskDefine taskDefine = this.iRunTimeViewController.queryTaskDefine(filePoolAllFileContext.getTaskKey());
            IEntityDefine entityDefine = this.entityMetaService.queryEntity(taskDefine.getDw());
            filePoolKey = filePoolAllFileContext.getTaskKey() + dimension.getValue(entityDefine.getDimensionName());
        }
        ArrayList<FilePoolFiles> filePoolFilesList = new ArrayList<FilePoolFiles>();
        FileBucketNameParam param = new FileBucketNameParam(filePoolAllFileContext.getDataSchemeKey());
        List<ObjectInfo> prop = this.attachmentFileAreaService.getObjectInfoByProp(param, "filePool", filePoolKey);
        for (ObjectInfo objectInfo : prop) {
            FilePoolFiles filePoolFiles = new FilePoolFiles();
            filePoolFiles.setFileKey(objectInfo.getKey());
            filePoolFiles.setFileName(objectInfo.getName());
            filePoolFiles.setSize(objectInfo.getSize());
            filePoolFiles.setMd5(objectInfo.getMd5());
            filePoolFiles.setSecretCode((String)objectInfo.getExtProp().get("secretlevel"));
            filePoolFiles.setCategoryCode((String)objectInfo.getExtProp().get("category"));
            filePoolFilesList.add(filePoolFiles);
        }
        return filePoolFilesList;
    }

    @Override
    public List<FileInfo> getFileInfoByGroup(String groupKey, String dataSchemeKey) {
        return this.getFileInfoByGroup(groupKey, dataSchemeKey, true);
    }

    @Override
    public List<FileInfo> getFileInfoByGroup(String groupKey, CommonParamsDTO params) {
        return this.getFileInfoByGroup(groupKey, true, params);
    }

    @Override
    public List<FileInfo> getFileInfoByGroup(String groupKey, String dataSchemeKey, boolean exist) {
        ArrayList<FileInfo> fileInfos = new ArrayList<FileInfo>();
        if (StringUtils.isEmpty((String)groupKey) || StringUtils.isEmpty((String)dataSchemeKey)) {
            return fileInfos;
        }
        DataScheme dataScheme = this.runtimeDataSchemeService.getDataScheme(dataSchemeKey);
        String tableName = this.fileOperationService.getCurrentSplitTableName(dataScheme);
        TableModelDefine filePoolTable = this.dataModelService.getTableModelDefineByName(tableName);
        if (null == filePoolTable) {
            throw new NotFoundTableDefineException(new String[]{"\u9644\u4ef6\u5173\u8054\u8868\u672a\u521b\u5efa\uff0c\u8bf7\u91cd\u65b0\u53d1\u5e03\u6570\u636e\u65b9\u6848\uff01"});
        }
        List<String> fileKeys = this.queryFileKey(groupKey, filePoolTable);
        this.queryFileInfo(exist, fileInfos, dataScheme, fileKeys);
        return fileInfos;
    }

    private void queryFileInfo(boolean exist, List<FileInfo> fileInfos, DataScheme dataScheme, List<String> fileKeys) {
        FileBucketNameParam param = new FileBucketNameParam(dataScheme.getKey());
        List<FileInfo> fileInfoByKeys = this.attachmentFileAreaService.getFileInfoByKeys(param, fileKeys, exist);
        for (String fileKey : fileKeys) {
            boolean isFind = false;
            for (FileInfo fileInfoByKey : fileInfoByKeys) {
                if (!fileKey.equals(fileInfoByKey.getKey())) continue;
                isFind = true;
                fileInfos.add(fileInfoByKey);
                break;
            }
            if (isFind) continue;
            this.buildFileInfo(fileInfos, fileKey);
        }
    }

    @NotNull
    private List<String> queryFileKey(String groupKey, TableModelDefine filePoolTable) {
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
        return fileKeys;
    }

    @Override
    public List<FileInfo> getFileInfoByGroup(String groupKey, boolean exist, CommonParamsDTO params) {
        ArrayList<FileInfo> fileInfos = new ArrayList<FileInfo>();
        if (StringUtils.isEmpty((String)groupKey) || null == params || StringUtils.isEmpty((String)params.getDataSchemeKey())) {
            return fileInfos;
        }
        DataScheme dataScheme = this.runtimeDataSchemeService.getDataScheme(params.getDataSchemeKey());
        String tableName = this.fileOperationService.getCurrentSplitTableName(dataScheme);
        TableModelDefine filePoolTable = this.dataModelService.getTableModelDefineByName(tableName);
        if (null == filePoolTable) {
            throw new NotFoundTableDefineException(new String[]{"\u9644\u4ef6\u5173\u8054\u8868\u672a\u521b\u5efa\uff0c\u8bf7\u91cd\u65b0\u53d1\u5e03\u6570\u636e\u65b9\u6848\uff01"});
        }
        List<String> fileKeys = this.queryFileKey(groupKey, filePoolTable);
        this.queryFileInfo(exist, fileInfos, dataScheme, fileKeys);
        return fileInfos;
    }

    @Override
    public List<FileInfosAndGroup> getFileInfoByGroup(List<String> groupKeys, String dataSchemeKey) {
        ArrayList<FileInfosAndGroup> fileInfosAndGroups = new ArrayList<FileInfosAndGroup>();
        if (StringUtils.isEmpty((String)dataSchemeKey)) {
            return fileInfosAndGroups;
        }
        ArrayList<String> checkGroupKeys = new ArrayList<String>();
        for (String groupKey : groupKeys) {
            if (!StringUtils.isNotEmpty((String)groupKey)) continue;
            checkGroupKeys.add(groupKey);
        }
        if (checkGroupKeys.isEmpty()) {
            return fileInfosAndGroups;
        }
        DataScheme dataScheme = this.runtimeDataSchemeService.getDataScheme(dataSchemeKey);
        Map<String, List<String>> groupKeyAndFileKeys = this.queryGroupFileMap(checkGroupKeys, dataScheme);
        ArrayList<String> allFileKeys = new ArrayList<String>();
        for (List<String> fileKeyList : groupKeyAndFileKeys.values()) {
            allFileKeys.addAll(fileKeyList);
        }
        FileBucketNameParam param = new FileBucketNameParam(dataScheme.getKey());
        ArrayList<FileInfo> allFileInfos = new ArrayList<FileInfo>(this.attachmentFileAreaService.getFileInfoByKeys(param, allFileKeys, false));
        for (Map.Entry<String, List<String>> groupKeyFileKeys : groupKeyAndFileKeys.entrySet()) {
            String groupKey = groupKeyFileKeys.getKey();
            List<String> fileKeys = groupKeyFileKeys.getValue();
            for (FileInfo allFileInfo : allFileInfos) {
                if (!StringUtils.isEmpty((String)allFileInfo.getFileGroupKey()) || !fileKeys.contains(allFileInfo.getKey())) continue;
                allFileInfo.setFileGroupKey(groupKey);
            }
        }
        HashMap<String, List<FileInfo>> groupFileInfos = new HashMap<String, List<FileInfo>>();
        for (FileInfo fileInfoByKey : allFileInfos) {
            List<FileInfo> fileInfos;
            if (null == groupFileInfos.get(fileInfoByKey.getFileGroupKey())) {
                fileInfos = new ArrayList<FileInfo>();
                fileInfos.add(fileInfoByKey);
                groupFileInfos.put(fileInfoByKey.getFileGroupKey(), fileInfos);
                continue;
            }
            fileInfos = (List)groupFileInfos.get(fileInfoByKey.getFileGroupKey());
            fileInfos.add(fileInfoByKey);
        }
        this.buildResult(fileInfosAndGroups, groupKeyAndFileKeys, groupFileInfos);
        return fileInfosAndGroups;
    }

    private void buildResult(List<FileInfosAndGroup> fileInfosAndGroups, Map<String, List<String>> groupKeyAndFileKeys, Map<String, List<FileInfo>> groupFileInfos) {
        for (Map.Entry<String, List<String>> groupKeyFileKeys : groupKeyAndFileKeys.entrySet()) {
            String groupKey = groupKeyFileKeys.getKey();
            List<String> fileKeys = groupKeyFileKeys.getValue();
            FileInfosAndGroup fileInfosAndGroup = new FileInfosAndGroup();
            fileInfosAndGroup.setGroupKey(groupKey);
            ArrayList<FileInfo> fileInfos = new ArrayList<FileInfo>();
            fileInfosAndGroup.setFileInfos(fileInfos);
            fileInfosAndGroups.add(fileInfosAndGroup);
            List<FileInfo> currentFileInfos = groupFileInfos.get(groupKey);
            for (String fileKey : fileKeys) {
                boolean isFind = this.findFile(fileInfos, currentFileInfos, fileKey);
                if (isFind) continue;
                this.buildFileInfo(fileInfos, fileKey);
            }
        }
    }

    private void buildFileInfo(List<FileInfo> fileInfos, String fileKey) {
        FileInfo fileInfo = new FileInfo();
        fileInfo.setKey(fileKey);
        String language = NpContextHolder.getContext().getLocale().getLanguage();
        if (StringUtils.isEmpty((String)language) || "zh".equals(language)) {
            fileInfo.setName("\u5df2\u5220\u9664");
        } else {
            fileInfo.setName("Deleted");
        }
        Date createTime = new Date();
        fileInfo.setCreateTime(createTime);
        fileInfo.setSize(0L);
        fileInfos.add(fileInfo);
    }

    private boolean findFile(List<FileInfo> fileInfos, List<FileInfo> currentFileInfos, String fileKey) {
        boolean isFind = false;
        if (null != currentFileInfos) {
            for (FileInfo currentFileInfo : currentFileInfos) {
                if (!fileKey.equals(currentFileInfo.getKey())) continue;
                isFind = true;
                fileInfos.add(currentFileInfo);
                break;
            }
        }
        return isFind;
    }

    @NotNull
    private Map<String, List<String>> queryGroupFileMap(List<String> checkGroupKeys, DataScheme dataScheme) {
        HashMap<String, List<String>> groupKeyAndFileKeys = new HashMap<String, List<String>>();
        String tableName = this.fileOperationService.getCurrentSplitTableName(dataScheme);
        TableModelDefine filePoolTable = this.dataModelService.getTableModelDefineByName(tableName);
        if (null == filePoolTable) {
            throw new NotFoundTableDefineException(new String[]{"\u9644\u4ef6\u5173\u8054\u8868\u672a\u521b\u5efa\uff0c\u8bf7\u91cd\u65b0\u53d1\u5e03\u6570\u636e\u65b9\u6848\uff01"});
        }
        List filePoolFields = this.dataModelService.getColumnModelDefinesByTable(filePoolTable.getID());
        DataAccessContext context = new DataAccessContext(this.dataModelService);
        NvwaQueryModel queryModel = new NvwaQueryModel();
        ColumnModelDefine groupKeyColum = null;
        ColumnModelDefine fileKeyColum = null;
        for (ColumnModelDefine filePoolField : filePoolFields) {
            switch (filePoolField.getCode()) {
                case "GROUPKEY": {
                    groupKeyColum = filePoolField;
                    queryModel.getColumns().add(new NvwaQueryColumn(filePoolField));
                    queryModel.getColumnFilters().put(filePoolField, checkGroupKeys);
                    break;
                }
                case "FILEKEY": {
                    fileKeyColum = filePoolField;
                    queryModel.getColumns().add(new NvwaQueryColumn(filePoolField));
                    break;
                }
                case "ISDELETE": {
                    queryModel.getColumnFilters().put(filePoolField, "0");
                }
            }
        }
        INvwaDataAccess readOnlyDataAccess = this.iNvwaDataAccessProvider.createReadOnlyDataAccess(queryModel);
        try {
            INvwaDataSet iNvwaDataRows = readOnlyDataAccess.executeQueryWithRowKey(context);
            for (int i = 0; i < iNvwaDataRows.size(); ++i) {
                List<String> fileKeys;
                INvwaDataRow row = iNvwaDataRows.getRow(i);
                Object groupKey = row.getValue(groupKeyColum);
                Object fileKey = row.getValue(fileKeyColum);
                if (null == groupKeyAndFileKeys.get((String)groupKey)) {
                    fileKeys = new ArrayList<String>();
                    fileKeys.add((String)fileKey);
                    groupKeyAndFileKeys.put((String)groupKey, fileKeys);
                    continue;
                }
                fileKeys = (List)groupKeyAndFileKeys.get((String)groupKey);
                fileKeys.add((String)fileKey);
            }
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
        return groupKeyAndFileKeys;
    }

    @Override
    public List<FileInfosAndGroup> getFileInfoByGroup(List<String> groupKeys, CommonParamsDTO params) {
        ArrayList<FileInfosAndGroup> fileInfosAndGroups = new ArrayList<FileInfosAndGroup>();
        if (null == groupKeys || groupKeys.isEmpty() || null == params || StringUtils.isEmpty((String)params.getDataSchemeKey())) {
            return fileInfosAndGroups;
        }
        ArrayList<String> checkGroupKeys = new ArrayList<String>();
        for (String groupKey : groupKeys) {
            if (!StringUtils.isNotEmpty((String)groupKey)) continue;
            checkGroupKeys.add(groupKey);
        }
        if (checkGroupKeys.isEmpty()) {
            return fileInfosAndGroups;
        }
        DataScheme dataScheme = this.runtimeDataSchemeService.getDataScheme(params.getDataSchemeKey());
        Map<String, List<String>> groupKeyAndFileKeys = this.queryGroupFileMap(checkGroupKeys, dataScheme);
        ArrayList<String> allFileKeys = new ArrayList<String>();
        for (List<String> fileKeyList : groupKeyAndFileKeys.values()) {
            allFileKeys.addAll(fileKeyList);
        }
        FileBucketNameParam param = new FileBucketNameParam(dataScheme.getKey());
        ArrayList<FileInfo> allFileInfos = new ArrayList<FileInfo>(this.attachmentFileAreaService.getFileInfoByKeys(param, allFileKeys, false));
        this.buildFileInfo(fileInfosAndGroups, groupKeyAndFileKeys, allFileInfos);
        return fileInfosAndGroups;
    }

    private void buildFileInfo(List<FileInfosAndGroup> fileInfosAndGroups, Map<String, List<String>> groupKeyAndFileKeys, List<FileInfo> allFileInfos) {
        ArrayList<FileInfo> cloneAllFileInfos = new ArrayList<FileInfo>();
        for (Map.Entry<String, List<String>> groupKeyFileKeys : groupKeyAndFileKeys.entrySet()) {
            String groupKey = groupKeyFileKeys.getKey();
            List<String> fileKeys = groupKeyFileKeys.getValue();
            for (FileInfo allFileInfo : allFileInfos) {
                if (!fileKeys.contains(allFileInfo.getKey())) continue;
                cloneAllFileInfos.add(FileInfoBuilder.newFileInfo(allFileInfo, groupKey, ""));
            }
        }
        HashMap<String, List> groupFileInfos = new HashMap<String, List>();
        for (FileInfo fileInfoByKey : cloneAllFileInfos) {
            List fileInfos = groupFileInfos.computeIfAbsent(fileInfoByKey.getFileGroupKey(), k -> new ArrayList());
            fileInfos.add(fileInfoByKey);
        }
        for (String groupKey : groupKeyAndFileKeys.keySet()) {
            FileInfosAndGroup fileInfosAndGroup = new FileInfosAndGroup();
            fileInfosAndGroup.setGroupKey(groupKey);
            ArrayList<FileInfo> fileInfos = new ArrayList<FileInfo>();
            fileInfosAndGroup.setFileInfos(fileInfos);
            fileInfosAndGroups.add(fileInfosAndGroup);
            List<String> fileKeys = groupKeyAndFileKeys.get(groupKey);
            List currentFileInfos = (List)groupFileInfos.get(groupKey);
            for (String fileKey : fileKeys) {
                boolean isFind = this.findFile(fileInfos, currentFileInfos, fileKey);
                if (isFind) continue;
                this.buildFileInfo(fileInfos, fileKey);
            }
        }
    }

    @Override
    public boolean judgeFileOverwritten(String fileKey, String taskKey) {
        DataAccessContext context = new DataAccessContext(this.dataModelService);
        NvwaQueryModel queryModel = new NvwaQueryModel();
        TaskDefine taskDefine = this.iRunTimeViewController.queryTaskDefine(taskKey);
        DataScheme dataScheme = this.runtimeDataSchemeService.getDataScheme(taskDefine.getDataScheme());
        String tableName = this.fileOperationService.getCurrentSplitTableName(dataScheme);
        TableModelDefine filePoolTable = this.dataModelService.getTableModelDefineByName(tableName);
        if (null == filePoolTable) {
            throw new NotFoundTableDefineException(new String[]{"\u9644\u4ef6\u5173\u8054\u8868\u672a\u521b\u5efa\uff0c\u8bf7\u91cd\u65b0\u53d1\u5e03\u6570\u636e\u65b9\u6848\uff01"});
        }
        List filePoolFields = this.dataModelService.getColumnModelDefinesByTable(filePoolTable.getID());
        for (ColumnModelDefine filePoolField : filePoolFields) {
            queryModel.getColumns().add(new NvwaQueryColumn(filePoolField));
            if (filePoolField.getCode().equals("FILEKEY")) {
                queryModel.getColumnFilters().put(filePoolField, fileKey);
                continue;
            }
            if (!filePoolField.getCode().equals("ISDELETE")) continue;
            queryModel.getColumnFilters().put(filePoolField, "0");
        }
        INvwaDataAccess readOnlyDataAccess = this.iNvwaDataAccessProvider.createReadOnlyDataAccess(queryModel);
        MemoryDataSet dataTable = null;
        try {
            HashMap<String, FieldAndDimInfo> formSchemeKeyAndDimension = new HashMap<String, FieldAndDimInfo>();
            DimensionChanger dimensionChanger = this.dataEngineAdapter.getDimensionChanger(tableName);
            dataTable = readOnlyDataAccess.executeQuery(context);
            List columns = queryModel.getColumns();
            for (int i = 0; i < dataTable.size(); ++i) {
                DataRow item = dataTable.get(i);
                String formSchemeKey = "";
                for (int j = 0; j < columns.size(); ++j) {
                    if (!((NvwaQueryColumn)columns.get(j)).getColumnModel().getCode().equals("PERIOD")) continue;
                    String dateCode = item.getString(j);
                    SchemePeriodLinkDefine schemePeriodLinkDefine = this.iRunTimeViewController.querySchemePeriodLinkByPeriodAndTask(dateCode, taskKey);
                    formSchemeKey = schemePeriodLinkDefine.getSchemeKey();
                    break;
                }
                if (!StringUtils.isNotEmpty((String)formSchemeKey)) continue;
                if (null == formSchemeKeyAndDimension.get(formSchemeKey)) {
                    FieldAndDimInfo fieldAndDimInfo = new FieldAndDimInfo();
                    formSchemeKeyAndDimension.put(formSchemeKey, fieldAndDimInfo);
                }
                FieldAndDimInfo fieldAndDimInfo = (FieldAndDimInfo)formSchemeKeyAndDimension.get(formSchemeKey);
                Map<String, List<DimensionCombinationBuilder>> fieldDimMap = fieldAndDimInfo.getFieldDimMap();
                List<DimensionCombinationBuilder> combinationBuilders = null;
                for (int j = 0; j < columns.size(); ++j) {
                    if (!((NvwaQueryColumn)columns.get(j)).getColumnModel().getCode().equals("FIELD_KEY")) continue;
                    String fieldKey = item.getString(j);
                    if (null == fieldDimMap.get(fieldKey)) {
                        combinationBuilders = new ArrayList<DimensionCombinationBuilder>();
                        fieldDimMap.put(fieldKey, combinationBuilders);
                        break;
                    }
                    combinationBuilders = fieldDimMap.get(fieldKey);
                    break;
                }
                DimensionCombinationBuilder dimensionCombinationBuilder = new DimensionCombinationBuilder();
                Map<String, Set<String>> dimNameAndDimVals = fieldAndDimInfo.getDimNameAndDimVals();
                for (int j = 0; j < columns.size(); ++j) {
                    if (((NvwaQueryColumn)columns.get(j)).getColumnModel().getCode().equals("FIELD_KEY") || ((NvwaQueryColumn)columns.get(j)).getColumnModel().getCode().equals("ID") || ((NvwaQueryColumn)columns.get(j)).getColumnModel().getCode().equals("GROUPKEY") || ((NvwaQueryColumn)columns.get(j)).getColumnModel().getCode().equals("FILEKEY") || ((NvwaQueryColumn)columns.get(j)).getColumnModel().getCode().equals("ISDELETE")) continue;
                    String data = item.getString(j);
                    String dimensionName = dimensionChanger.getDimensionName(((NvwaQueryColumn)columns.get(j)).getColumnModel());
                    dimensionCombinationBuilder.setValue(dimensionName, null, (Object)data);
                    Set<String> dimVals = dimNameAndDimVals.get(dimensionName);
                    if (null == dimVals) {
                        dimVals = new HashSet<String>();
                        dimVals.add(data);
                        dimNameAndDimVals.put(dimensionName, dimVals);
                        continue;
                    }
                    dimVals.add(data);
                }
                if (null == combinationBuilders) continue;
                combinationBuilders.add(dimensionCombinationBuilder);
            }
            for (String formSchemeKey : formSchemeKeyAndDimension.keySet()) {
                IDataAccessService dataAccessService = this.iDataAccessServiceProvider.getDataAccessService(taskKey, formSchemeKey);
                FieldAndDimInfo fieldAndDimInfo = (FieldAndDimInfo)formSchemeKeyAndDimension.get(formSchemeKey);
                Map<String, Set<String>> dimNameAndDimVals = fieldAndDimInfo.getDimNameAndDimVals();
                HashMap<String, DimensionValue> dimensionSet = new HashMap<String, DimensionValue>();
                for (String dimName : dimNameAndDimVals.keySet()) {
                    DimensionValue dimensionValue = new DimensionValue();
                    dimensionValue.setName(dimName);
                    dimensionValue.setValue(dimNameAndDimVals.get(dimName).stream().collect(Collectors.joining(";")));
                    dimensionSet.put(dimName, dimensionValue);
                }
                DimensionCollection collection = this.dimCollectionBuildUtil.buildDimensionCollection(dimensionSet, formSchemeKey);
                Set<String> strings = fieldAndDimInfo.getFieldDimMap().keySet();
                for (String fieldkey : strings) {
                    Collection formKeysByField = this.iRunTimeViewController.getFormKeysByField(fieldkey);
                    ArrayList<String> formKeys = new ArrayList<String>();
                    for (String formKey : formKeysByField) {
                        List formSchemeDefines = this.iRunTimeViewController.queryFormSchemeByForm(formKey);
                        if (!formSchemeDefines.stream().map(IBaseMetaItem::getKey).collect(Collectors.toList()).contains(formSchemeKey)) continue;
                        formKeys.add(formKey);
                    }
                    IBatchAccessResult batchWriteAccess = dataAccessService.getWriteAccess(collection, formKeys);
                    List<DimensionCombinationBuilder> combinationBuilders = fieldAndDimInfo.getFieldDimMap().get(fieldkey);
                    for (DimensionCombinationBuilder dimensionCombinationBuilder : combinationBuilders) {
                        for (String formKey : formKeys) {
                            IAccessResult access = batchWriteAccess.getAccess(dimensionCombinationBuilder.getCombination(), formKey);
                            if (access.haveAccess()) continue;
                            return false;
                        }
                    }
                }
            }
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
        return true;
    }
}

