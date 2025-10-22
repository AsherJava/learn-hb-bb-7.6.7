/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.DataRow
 *  com.jiuqi.bi.dataset.MemoryDataSet
 *  com.jiuqi.bi.oss.ObjectInfo
 *  com.jiuqi.bi.oss.ObjectStorageException
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.definition.common.FieldType
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.nr.common.util.DataEngineAdapter
 *  com.jiuqi.nr.common.util.DimensionChanger
 *  com.jiuqi.nr.datascheme.api.DataScheme
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nvwa.dataengine.INvwaDataAccess
 *  com.jiuqi.nvwa.dataengine.INvwaDataAccessProvider
 *  com.jiuqi.nvwa.dataengine.INvwaDataRow
 *  com.jiuqi.nvwa.dataengine.INvwaDataUpdator
 *  com.jiuqi.nvwa.dataengine.INvwaUpdatableDataAccess
 *  com.jiuqi.nvwa.dataengine.common.DataAccessContext
 *  com.jiuqi.nvwa.dataengine.model.NvwaQueryColumn
 *  com.jiuqi.nvwa.dataengine.model.NvwaQueryModel
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 */
package com.jiuqi.nr.attachment.service.impl;

import com.jiuqi.bi.dataset.DataRow;
import com.jiuqi.bi.dataset.MemoryDataSet;
import com.jiuqi.bi.oss.ObjectInfo;
import com.jiuqi.bi.oss.ObjectStorageException;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.definition.common.FieldType;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.nr.attachment.constant.PromptConsts;
import com.jiuqi.nr.attachment.input.CommonParamsDTO;
import com.jiuqi.nr.attachment.input.FileCalculateParam;
import com.jiuqi.nr.attachment.input.FilePoolAllFileContext;
import com.jiuqi.nr.attachment.input.FileUploadInfo;
import com.jiuqi.nr.attachment.listener.IFileListener;
import com.jiuqi.nr.attachment.listener.IFileListenerProvider;
import com.jiuqi.nr.attachment.listener.param.FileUploadEvent;
import com.jiuqi.nr.attachment.message.FileCopyInfo;
import com.jiuqi.nr.attachment.message.FileInfo;
import com.jiuqi.nr.attachment.output.FilePoolFiles;
import com.jiuqi.nr.attachment.provider.param.FileBucketNameParam;
import com.jiuqi.nr.attachment.service.FileCalculateService;
import com.jiuqi.nr.attachment.service.FileOperationService;
import com.jiuqi.nr.attachment.service.FilePoolService;
import com.jiuqi.nr.attachment.tools.AttachmentFileAreaService;
import com.jiuqi.nr.common.util.DataEngineAdapter;
import com.jiuqi.nr.common.util.DimensionChanger;
import com.jiuqi.nr.datascheme.api.DataScheme;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nvwa.dataengine.INvwaDataAccess;
import com.jiuqi.nvwa.dataengine.INvwaDataAccessProvider;
import com.jiuqi.nvwa.dataengine.INvwaDataRow;
import com.jiuqi.nvwa.dataengine.INvwaDataUpdator;
import com.jiuqi.nvwa.dataengine.INvwaUpdatableDataAccess;
import com.jiuqi.nvwa.dataengine.common.DataAccessContext;
import com.jiuqi.nvwa.dataengine.model.NvwaQueryColumn;
import com.jiuqi.nvwa.dataengine.model.NvwaQueryModel;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FileCalculateServiceImpl
implements FileCalculateService {
    private static final Logger logger = LoggerFactory.getLogger(FileCalculateServiceImpl.class);
    @Autowired
    private FilePoolService filePoolService;
    @Autowired
    private FileOperationService fileOperationService;
    @Autowired
    private AttachmentFileAreaService attachmentFileAreaService;
    @Autowired
    private IRunTimeViewController iRunTimeViewController;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private DataModelService dataModelService;
    @Autowired
    private IRuntimeDataSchemeService runtimeDataSchemeService;
    @Autowired
    private INvwaDataAccessProvider iNvwaDataAccessProvider;
    @Autowired
    private DataEngineAdapter dataEngineAdapter;
    @Autowired(required=false)
    private List<IFileListenerProvider> fileListenerProviders;

    @Override
    public String copyFileGroup(String taskKey, String fieldKey, String fromGroupKey, DimensionCombination toDims) {
        String toGroupKey = UUID.randomUUID().toString();
        TaskDefine taskDefine = this.iRunTimeViewController.queryTaskDefine(taskKey);
        String dataSchemeKey = taskDefine.getDataScheme();
        FileBucketNameParam param = new FileBucketNameParam(dataSchemeKey);
        try {
            FieldDefine fieldDefine = this.iRunTimeViewController.queryFieldDefine(fieldKey);
            if (fieldDefine.getType() == FieldType.FIELD_TYPE_FILE) {
                boolean openFilepool = this.filePoolService.isOpenFilepool();
                if (openFilepool) {
                    if (this.copyFile(taskKey, fieldKey, fromGroupKey, toDims, toGroupKey, taskDefine, dataSchemeKey, param, null)) {
                        return toGroupKey;
                    }
                } else {
                    CommonParamsDTO params = new CommonParamsDTO();
                    params.setDataSchemeKey(dataSchemeKey);
                    params.setTaskKey(taskKey);
                    List<FileInfo> fileInfos = this.filePoolService.getFileInfoByGroup(fromGroupKey, false, params);
                    if (fileInfos.isEmpty()) {
                        return toGroupKey;
                    }
                    List<String> fileKeys = fileInfos.stream().map(FileInfo::getKey).collect(Collectors.toList());
                    List<String> copyFileKeys = this.attachmentFileAreaService.batchFileCopy(param, fileKeys);
                    HashMap<String, String> updateInfo = new HashMap<String, String>();
                    updateInfo.put("fileGroupKey", toGroupKey);
                    this.attachmentFileAreaService.batchupdate(param, copyFileKeys, updateInfo);
                    this.insertFileRelTable(taskDefine, null, toDims, fieldKey, toGroupKey, copyFileKeys);
                }
            } else if (fieldDefine.getType() == FieldType.FIELD_TYPE_PICTURE) {
                List<FileInfo> fileInfos = this.attachmentFileAreaService.getFileInfoByProp(param, "fileGroupKey", fromGroupKey, false);
                if (fileInfos.isEmpty()) {
                    return toGroupKey;
                }
                List<String> fileKeys = fileInfos.stream().map(FileInfo::getKey).collect(Collectors.toList());
                List<String> copyFileKeys = this.attachmentFileAreaService.batchFileCopy(param, fileKeys);
                HashMap<String, String> updateInfo = new HashMap<String, String>();
                updateInfo.put("fileGroupKey", toGroupKey);
                this.attachmentFileAreaService.batchupdate(param, copyFileKeys, updateInfo);
                for (int i = 0; i < fileKeys.size(); ++i) {
                    List<FileInfo> thumbnailInfos = this.attachmentFileAreaService.getFileInfoByProp(param, "fileGroupKey", fileKeys.get(i), false);
                    List<String> thumbnailKeys = thumbnailInfos.stream().map(FileInfo::getKey).collect(Collectors.toList());
                    List<String> copyThumbnailKeys = this.attachmentFileAreaService.batchFileCopy(param, thumbnailKeys);
                    HashMap<String, String> thumbnailUpdateInfo = new HashMap<String, String>();
                    thumbnailUpdateInfo.put("fileGroupKey", copyFileKeys.get(i));
                    this.attachmentFileAreaService.batchupdate(param, copyThumbnailKeys, thumbnailUpdateInfo);
                }
            }
        }
        catch (Exception e) {
            logger.error(PromptConsts.causeIfError(e.getMessage()), e);
        }
        return toGroupKey;
    }

    private boolean copyFile(String taskKey, String fieldKey, String fromGroupKey, DimensionCombination toDims, String toGroupKey, TaskDefine taskDefine, String dataSchemeKey, FileBucketNameParam param, String formSchemeKey) throws Exception {
        IEntityDefine entityDefine;
        String toDW;
        List<FileCopyInfo> fromFileInfos = this.getFileInfoByGroup(fromGroupKey, param);
        if (fromFileInfos.isEmpty()) {
            return true;
        }
        String fromDW = "";
        DataAccessContext context = new DataAccessContext(this.dataModelService);
        NvwaQueryModel queryModel = new NvwaQueryModel();
        DataScheme dataScheme = this.runtimeDataSchemeService.getDataScheme(dataSchemeKey);
        String tableName = this.fileOperationService.getCurrentSplitTableName(dataScheme);
        TableModelDefine filePoolTable = this.dataModelService.getTableModelDefineByName(tableName);
        if (null == filePoolTable) {
            logger.error("\u9644\u4ef6\u5173\u8054\u8868\u672a\u521b\u5efa\uff0c\u8bf7\u91cd\u65b0\u53d1\u5e03\u6570\u636e\u65b9\u6848\uff01");
            return true;
        }
        List filePoolFields = this.dataModelService.getColumnModelDefinesByTable(filePoolTable.getID());
        for (ColumnModelDefine filePoolField : filePoolFields) {
            queryModel.getColumns().add(new NvwaQueryColumn(filePoolField));
            if (filePoolField.getCode().equals("GROUPKEY")) {
                queryModel.getColumnFilters().put(filePoolField, fromGroupKey);
                continue;
            }
            if (!filePoolField.getCode().equals("ISDELETE")) continue;
            queryModel.getColumnFilters().put(filePoolField, "0");
        }
        INvwaDataAccess readOnlyDataAccess = this.iNvwaDataAccessProvider.createReadOnlyDataAccess(queryModel);
        MemoryDataSet dataTable = readOnlyDataAccess.executeQuery(context);
        List columns = queryModel.getColumns();
        if (dataTable.size() > 0) {
            DataRow item = dataTable.get(0);
            for (int j = 0; j < columns.size(); ++j) {
                if (!((NvwaQueryColumn)columns.get(j)).getColumnModel().getCode().equals("MDCODE")) continue;
                fromDW = item.getString(j);
                break;
            }
        }
        if (fromDW.equals(toDW = (String)toDims.getValue((entityDefine = this.entityMetaService.queryEntity(taskDefine.getDw())).getDimensionName()))) {
            List<String> fileKeys = fromFileInfos.stream().map(FileInfo::getKey).collect(Collectors.toList());
            this.insertFileRelTable(taskDefine, formSchemeKey, toDims, fieldKey, toGroupKey, fileKeys);
        } else {
            String filePoolKey;
            ArrayList<String> insertFileKeys = new ArrayList<String>();
            ArrayList<FileUploadInfo> fileUploadInfos = new ArrayList<FileUploadInfo>();
            ArrayList<String> updateFilepoolFileKey = new ArrayList<String>();
            this.getFilepoolSameFile(taskKey, toDims, entityDefine.getDimensionName(), param, fromFileInfos, insertFileKeys, fileUploadInfos, updateFilepoolFileKey);
            if (!updateFilepoolFileKey.isEmpty()) {
                filePoolKey = taskKey + toDW;
                HashMap<String, String> updateInfo = new HashMap<String, String>();
                updateInfo.put("filePool", filePoolKey);
                this.attachmentFileAreaService.batchupdate(param, updateFilepoolFileKey, updateInfo);
            }
            if (!fileUploadInfos.isEmpty()) {
                filePoolKey = taskKey + toDW;
                List<FileInfo> fileInfos = this.uploadFilesToFilepool(filePoolKey, fileUploadInfos, param);
                insertFileKeys.addAll(fileInfos.stream().map(FileInfo::getKey).collect(Collectors.toList()));
            }
            this.insertFileRelTable(taskDefine, formSchemeKey, toDims, fieldKey, toGroupKey, insertFileKeys);
        }
        return false;
    }

    private void getFilepoolSameFile(String taskKey, DimensionCombination toDims, String dwName, FileBucketNameParam param, List<FileCopyInfo> fromFileInfos, List<String> insertFileKeys, List<FileUploadInfo> fileUploadInfos, List<String> updateFilepoolFileKey) throws ObjectStorageException {
        FilePoolAllFileContext filePoolAllFileContext = new FilePoolAllFileContext();
        filePoolAllFileContext.setTaskKey(taskKey);
        filePoolAllFileContext.setDimensionCombination(toDims);
        List<FilePoolFiles> toFilePoolFiles = this.filePoolService.getFilePoolFiles(filePoolAllFileContext, false);
        for (FileCopyInfo fromFileInfo : fromFileInfos) {
            boolean sameName = false;
            for (FilePoolFiles toFilePoolFile : toFilePoolFiles) {
                if (toFilePoolFile.getFileName().equals(fromFileInfo.getName()) && (toFilePoolFile.getSize() != fromFileInfo.getSize() || !toFilePoolFile.getMd5().equals(fromFileInfo.getMd5()))) {
                    sameName = true;
                    if (!this.filePoolService.judgeFileOverwritten(toFilePoolFile.getFileKey(), taskKey)) {
                        List<String> copyFileKeys = this.attachmentFileAreaService.batchFileCopy(param, Arrays.asList(fromFileInfo.getKey()));
                        insertFileKeys.add(copyFileKeys.get(0));
                        updateFilepoolFileKey.add(copyFileKeys.get(0));
                        String newFileName = this.rename(taskKey, toFilePoolFiles, fromFileInfo.getName() + "(1)", fromFileInfo.getSize(), fromFileInfo.getMd5(), 1);
                        HashMap<String, String> updateInfo = new HashMap<String, String>();
                        updateInfo.put("name", newFileName);
                        updateInfo.put("filePool", taskKey + toDims.getValue(dwName));
                        this.attachmentFileAreaService.update(param, copyFileKeys.get(0), updateInfo);
                        break;
                    }
                    FileUploadInfo fileUploadInfo = new FileUploadInfo();
                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    this.attachmentFileAreaService.download(param, fromFileInfo.getKey(), outputStream);
                    ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
                    fileUploadInfo.setFile(inputStream);
                    fileUploadInfo.setFileKey(toFilePoolFile.getFileKey());
                    fileUploadInfo.setCovered(true);
                    fileUploadInfo.setSize(fromFileInfo.getSize());
                    fileUploadInfo.setFileSecret(fromFileInfo.getSecretlevel());
                    fileUploadInfo.setName(fromFileInfo.getName());
                    fileUploadInfos.add(fileUploadInfo);
                    break;
                }
                if (!toFilePoolFile.getFileName().equals(fromFileInfo.getName()) || toFilePoolFile.getSize() != fromFileInfo.getSize() || !toFilePoolFile.getMd5().equals(fromFileInfo.getMd5())) continue;
                sameName = true;
                insertFileKeys.add(toFilePoolFile.getFileKey());
                break;
            }
            if (sameName) continue;
            List<String> copyFileKeys = this.attachmentFileAreaService.batchFileCopy(param, Arrays.asList(fromFileInfo.getKey()));
            insertFileKeys.add(copyFileKeys.get(0));
            updateFilepoolFileKey.add(copyFileKeys.get(0));
        }
    }

    @Override
    public String copyFileGroup(FileCalculateParam fileCalculateParam) {
        String taskKey = fileCalculateParam.getTaskKey();
        String formSchemeKey = fileCalculateParam.getFormSchemeKey();
        String fieldKey = fileCalculateParam.getFieldKey();
        String fromGroupKey = fileCalculateParam.getFromGroupKey();
        DimensionCombination toDims = fileCalculateParam.getToDims();
        String toGroupKey = UUID.randomUUID().toString();
        TaskDefine taskDefine = this.iRunTimeViewController.queryTaskDefine(taskKey);
        String dataSchemeKey = taskDefine.getDataScheme();
        FileBucketNameParam param = new FileBucketNameParam(dataSchemeKey);
        try {
            FieldDefine fieldDefine = this.iRunTimeViewController.queryFieldDefine(fieldKey);
            if (fieldDefine.getType() == FieldType.FIELD_TYPE_FILE) {
                boolean openFilepool = this.filePoolService.isOpenFilepool();
                if (openFilepool) {
                    if (this.copyFile(taskKey, fieldKey, fromGroupKey, toDims, toGroupKey, taskDefine, dataSchemeKey, param, formSchemeKey)) {
                        return toGroupKey;
                    }
                } else {
                    CommonParamsDTO params = new CommonParamsDTO();
                    params.setDataSchemeKey(dataSchemeKey);
                    params.setTaskKey(taskKey);
                    List<FileInfo> fileInfos = this.filePoolService.getFileInfoByGroup(fromGroupKey, false, params);
                    if (fileInfos.isEmpty()) {
                        return toGroupKey;
                    }
                    List<String> fileKeys = fileInfos.stream().map(FileInfo::getKey).collect(Collectors.toList());
                    List<String> copyFileKeys = this.attachmentFileAreaService.batchFileCopy(param, fileKeys);
                    HashMap<String, String> updateInfo = new HashMap<String, String>();
                    updateInfo.put("fileGroupKey", toGroupKey);
                    this.attachmentFileAreaService.batchupdate(param, copyFileKeys, updateInfo);
                    this.insertFileRelTable(taskDefine, formSchemeKey, toDims, fieldKey, toGroupKey, copyFileKeys);
                }
            } else if (fieldDefine.getType() == FieldType.FIELD_TYPE_PICTURE) {
                List<FileInfo> fileInfos = this.attachmentFileAreaService.getFileInfoByProp(param, "fileGroupKey", fromGroupKey, false);
                if (fileInfos.isEmpty()) {
                    return toGroupKey;
                }
                List<String> fileKeys = fileInfos.stream().map(FileInfo::getKey).collect(Collectors.toList());
                List<String> copyFileKeys = this.attachmentFileAreaService.batchFileCopy(param, fileKeys);
                HashMap<String, String> updateInfo = new HashMap<String, String>();
                updateInfo.put("fileGroupKey", toGroupKey);
                this.attachmentFileAreaService.batchupdate(param, copyFileKeys, updateInfo);
                for (int i = 0; i < fileKeys.size(); ++i) {
                    List<FileInfo> thumbnailInfos = this.attachmentFileAreaService.getFileInfoByProp(param, "fileGroupKey", fileKeys.get(i), false);
                    List<String> thumbnailKeys = thumbnailInfos.stream().map(FileInfo::getKey).collect(Collectors.toList());
                    List<String> copyThumbnailKeys = this.attachmentFileAreaService.batchFileCopy(param, thumbnailKeys);
                    HashMap<String, String> thumbnailUpdateInfo = new HashMap<String, String>();
                    thumbnailUpdateInfo.put("fileGroupKey", copyFileKeys.get(i));
                    this.attachmentFileAreaService.batchupdate(param, copyThumbnailKeys, thumbnailUpdateInfo);
                }
            }
        }
        catch (Exception e) {
            logger.error(PromptConsts.causeIfError(e.getMessage()), e);
        }
        return toGroupKey;
    }

    private String rename(String taskKey, List<FilePoolFiles> fileInfos, String fileName, long size, String md5, int index) {
        for (FilePoolFiles fileInfo : fileInfos) {
            if (!fileInfo.getFileName().equals(fileName)) continue;
            for (int i = fileName.length() - 1; i >= 0; --i) {
                if (fileName.charAt(i) != '(') continue;
                fileName = fileName.substring(0, i);
                break;
            }
            String newFileKey = fileName + "(" + index + 1 + ")";
            this.rename(taskKey, fileInfos, newFileKey, size, md5, ++index);
        }
        return fileName;
    }

    private void insertFileRelTable(TaskDefine taskDefine, String formSchemeKey, DimensionCombination dimensionCombination, String fieldKey, String groupKey, List<String> fileKeys) {
        if (fileKeys.isEmpty()) {
            return;
        }
        DataAccessContext context = new DataAccessContext(this.dataModelService);
        NvwaQueryModel queryModel = new NvwaQueryModel();
        DataScheme dataScheme = this.runtimeDataSchemeService.getDataScheme(taskDefine.getDataScheme());
        String tableName = this.fileOperationService.getCurrentSplitTableName(dataScheme);
        TableModelDefine filePoolTable = this.dataModelService.getTableModelDefineByName(tableName);
        if (null == filePoolTable) {
            logger.error("\u9644\u4ef6\u5173\u8054\u8868\u672a\u521b\u5efa\uff0c\u8bf7\u91cd\u65b0\u53d1\u5e03\u6570\u636e\u65b9\u6848\uff01");
            return;
        }
        List filePoolFields = this.dataModelService.getColumnModelDefinesByTable(filePoolTable.getID());
        for (ColumnModelDefine filePoolField : filePoolFields) {
            queryModel.getColumns().add(new NvwaQueryColumn(filePoolField));
        }
        DimensionChanger dimensionChanger = this.dataEngineAdapter.getDimensionChanger(filePoolTable.getName());
        DimensionValueSet dimensionValueSet = dimensionCombination.toDimensionValueSet();
        INvwaUpdatableDataAccess updatableDataAccess = this.iNvwaDataAccessProvider.createUpdatableDataAccess(queryModel);
        INvwaDataUpdator iNvwaDataUpdator = null;
        List columns = queryModel.getColumns();
        try {
            iNvwaDataUpdator = updatableDataAccess.openForUpdate(context);
            for (String fileKey : fileKeys) {
                INvwaDataRow iNvwaDataRow = iNvwaDataUpdator.addInsertRow();
                String id = UUID.randomUUID().toString();
                block18: for (int i = 0; i < columns.size(); ++i) {
                    switch (((NvwaQueryColumn)columns.get(i)).getColumnModel().getCode()) {
                        case "ID": {
                            iNvwaDataRow.setValue(i, (Object)id);
                            continue block18;
                        }
                        case "GROUPKEY": {
                            iNvwaDataRow.setValue(i, (Object)groupKey);
                            continue block18;
                        }
                        case "FILEKEY": {
                            iNvwaDataRow.setValue(i, (Object)fileKey);
                            continue block18;
                        }
                        case "FIELD_KEY": {
                            iNvwaDataRow.setValue(i, (Object)fieldKey);
                            continue block18;
                        }
                        case "ISDELETE": {
                            iNvwaDataRow.setValue(i, (Object)"0");
                            continue block18;
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
                FileUploadEvent fileUploadEvent = new FileUploadEvent(dataScheme.getKey(), taskDefine.getKey(), formSchemeKey, dimensionCombination, fieldKey, groupKey, fileKeys);
                fileListener.afterFileUpload(fileUploadEvent);
            }
        }
        catch (Exception e) {
            logger.error(PromptConsts.causeIfError(e.getMessage()), e);
        }
    }

    private List<FileCopyInfo> getFileInfoByGroup(String groupKey, FileBucketNameParam param) {
        DataScheme dataScheme = this.runtimeDataSchemeService.getDataScheme(param.getDataSchemeKey());
        String tableName = this.fileOperationService.getCurrentSplitTableName(dataScheme);
        TableModelDefine filePoolTable = this.dataModelService.getTableModelDefineByName(tableName);
        if (null == filePoolTable) {
            logger.error("\u9644\u4ef6\u5173\u8054\u8868\u672a\u521b\u5efa\uff0c\u8bf7\u91cd\u65b0\u53d1\u5e03\u6570\u636e\u65b9\u6848\uff01");
            return new ArrayList<FileCopyInfo>();
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
            logger.error(PromptConsts.causeIfError(e.getMessage()), e);
        }
        ArrayList<FileCopyInfo> fileCopyInfos = new ArrayList<FileCopyInfo>();
        if (fileKeys.isEmpty()) {
            return fileCopyInfos;
        }
        List<ObjectInfo> objectInfos = this.attachmentFileAreaService.getObjectInfoByKeys(param, fileKeys);
        for (ObjectInfo objectInfo : objectInfos) {
            if (null == objectInfo) continue;
            FileCopyInfo fileCopyInfo = new FileCopyInfo();
            fileCopyInfo.setKey(objectInfo.getKey());
            fileCopyInfo.setName(objectInfo.getName());
            fileCopyInfo.setSize(objectInfo.getSize());
            fileCopyInfo.setMd5(objectInfo.getMd5());
            fileCopyInfos.add(fileCopyInfo);
        }
        return fileCopyInfos;
    }

    private List<FileInfo> uploadFilesToFilepool(String filePoolKey, List<FileUploadInfo> fileUploadInfos, FileBucketNameParam param) {
        ArrayList<FileInfo> fileInfos = new ArrayList<FileInfo>();
        for (FileUploadInfo fileUploadInfo : fileUploadInfos) {
            FileInfo fileInfo;
            HashMap<String, String> expandInfo;
            if (!fileUploadInfo.isCovered()) {
                expandInfo = new HashMap<String, String>();
                expandInfo.put("secretlevel", fileUploadInfo.getFileSecret());
                expandInfo.put("filePool", filePoolKey);
                expandInfo.put("category", fileUploadInfo.getCategory());
                fileInfo = this.attachmentFileAreaService.upload(param, fileUploadInfo.getName(), fileUploadInfo.getFile(), expandInfo);
            } else {
                this.attachmentFileAreaService.delete(param, fileUploadInfo.getFileKey());
                expandInfo = new HashMap();
                expandInfo.put("secretlevel", fileUploadInfo.getFileSecret());
                expandInfo.put("filePool", filePoolKey);
                expandInfo.put("category", fileUploadInfo.getCategory());
                fileInfo = this.attachmentFileAreaService.uploadByKey(param, fileUploadInfo.getName(), fileUploadInfo.getFileKey(), fileUploadInfo.getFile(), expandInfo);
            }
            fileInfos.add(fileInfo);
        }
        return fileInfos;
    }
}

