/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.DataRow
 *  com.jiuqi.bi.dataset.MemoryDataSet
 *  com.jiuqi.bi.oss.ObjectInfo
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.definition.common.FieldType
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.nr.common.util.DataEngineAdapter
 *  com.jiuqi.nr.common.util.DimensionChanger
 *  com.jiuqi.nr.data.access.util.DimensionValueSetUtil
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.DataScheme
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.dataservice.core.common.DataFieldMap
 *  com.jiuqi.nr.dataservice.core.common.DataFieldMappingConverter
 *  com.jiuqi.nr.dataservice.core.common.DimensionMappingConverter
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
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.definition.common.FieldType;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.nr.attachment.constant.PromptConsts;
import com.jiuqi.nr.attachment.exception.FileCopyException;
import com.jiuqi.nr.attachment.exception.OSSOperationException;
import com.jiuqi.nr.attachment.input.FileCopyBaseParam;
import com.jiuqi.nr.attachment.input.FileCopyFixedParam;
import com.jiuqi.nr.attachment.input.FileCopyFloatParam;
import com.jiuqi.nr.attachment.input.FilePoolAllFileContext;
import com.jiuqi.nr.attachment.listener.IFileListener;
import com.jiuqi.nr.attachment.listener.IFileListenerProvider;
import com.jiuqi.nr.attachment.listener.param.FileUploadEvent;
import com.jiuqi.nr.attachment.message.FileRelInfo;
import com.jiuqi.nr.attachment.message.FixedFieldAndGroupInfo;
import com.jiuqi.nr.attachment.message.FloatFieldAndGroupInfo;
import com.jiuqi.nr.attachment.output.FilePoolFiles;
import com.jiuqi.nr.attachment.provider.IFileBucketNameProvider;
import com.jiuqi.nr.attachment.provider.IFileCopyParamProvider;
import com.jiuqi.nr.attachment.provider.param.FileBucketNameParam;
import com.jiuqi.nr.attachment.service.AttachmentIOService;
import com.jiuqi.nr.attachment.service.FileOperationService;
import com.jiuqi.nr.attachment.service.FilePoolService;
import com.jiuqi.nr.attachment.service.FileUploadCheckService;
import com.jiuqi.nr.attachment.service.IFileCopyService;
import com.jiuqi.nr.attachment.tools.impl.OSSOperationServiceImpl;
import com.jiuqi.nr.common.util.DataEngineAdapter;
import com.jiuqi.nr.common.util.DimensionChanger;
import com.jiuqi.nr.data.access.util.DimensionValueSetUtil;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.DataScheme;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.dataservice.core.common.DataFieldMap;
import com.jiuqi.nr.dataservice.core.common.DataFieldMappingConverter;
import com.jiuqi.nr.dataservice.core.common.DimensionMappingConverter;
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
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

public class IFileCopyServiceImpl
implements IFileCopyService {
    private static final Logger logger = LoggerFactory.getLogger(IFileCopyServiceImpl.class);
    private FilePoolService filePoolService;
    private FileOperationService fileOperationService;
    private AttachmentIOService attachmentIOService;
    private IFileBucketNameProvider fileBucketNameProvider;
    private FileUploadCheckService fileUploadCheckService;
    private IRunTimeViewController runTimeViewController;
    private IEntityMetaService entityMetaService;
    private DataModelService dataModelService;
    private IRuntimeDataSchemeService runtimeDataSchemeService;
    private INvwaDataAccessProvider nvwaDataAccessProvider;
    private DataEngineAdapter dataEngineAdapter;
    private List<IFileListenerProvider> fileListenerProviders;
    private IFileCopyParamProvider fileCopyParamProvider;
    private static final int MAX_INSERT_NUM = 5000;

    public IFileCopyServiceImpl setFilePoolService(FilePoolService filePoolService) {
        this.filePoolService = filePoolService;
        return this;
    }

    public IFileCopyServiceImpl setFileOperationService(FileOperationService fileOperationService) {
        this.fileOperationService = fileOperationService;
        return this;
    }

    public IFileCopyServiceImpl setAttachmentIOService(AttachmentIOService attachmentIOService) {
        this.attachmentIOService = attachmentIOService;
        return this;
    }

    public IFileCopyServiceImpl setFileBucketNameProvider(IFileBucketNameProvider fileBucketNameProvider) {
        this.fileBucketNameProvider = fileBucketNameProvider;
        return this;
    }

    public IFileCopyServiceImpl setFileUploadCheckService(FileUploadCheckService fileUploadCheckService) {
        this.fileUploadCheckService = fileUploadCheckService;
        return this;
    }

    public IFileCopyServiceImpl setRunTimeViewController(IRunTimeViewController runTimeViewController) {
        this.runTimeViewController = runTimeViewController;
        return this;
    }

    public IFileCopyServiceImpl setEntityMetaService(IEntityMetaService entityMetaService) {
        this.entityMetaService = entityMetaService;
        return this;
    }

    public IFileCopyServiceImpl setDataModelService(DataModelService dataModelService) {
        this.dataModelService = dataModelService;
        return this;
    }

    public IFileCopyServiceImpl setRuntimeDataSchemeService(IRuntimeDataSchemeService runtimeDataSchemeService) {
        this.runtimeDataSchemeService = runtimeDataSchemeService;
        return this;
    }

    public IFileCopyServiceImpl setNvwaDataAccessProvider(INvwaDataAccessProvider nvwaDataAccessProvider) {
        this.nvwaDataAccessProvider = nvwaDataAccessProvider;
        return this;
    }

    public IFileCopyServiceImpl setDataEngineAdapter(DataEngineAdapter dataEngineAdapter) {
        this.dataEngineAdapter = dataEngineAdapter;
        return this;
    }

    public IFileCopyServiceImpl setFileListenerProviders(List<IFileListenerProvider> fileListenerProviders) {
        this.fileListenerProviders = fileListenerProviders;
        return this;
    }

    public IFileCopyServiceImpl setFileCopyParamProvider(IFileCopyParamProvider fileCopyParamProvider) {
        this.fileCopyParamProvider = fileCopyParamProvider;
        return this;
    }

    @Override
    public List<FixedFieldAndGroupInfo> batchCopyFile(FileCopyFixedParam fileCopyFixedParam) throws FileCopyException {
        List<FixedFieldAndGroupInfo> fixedFieldAndGroupInfos = fileCopyFixedParam.getFixedFieldAndGroupInfos();
        if (CollectionUtils.isEmpty(fixedFieldAndGroupInfos)) {
            return fixedFieldAndGroupInfos;
        }
        HashMap<String, String> fileGroupKeyMap = new HashMap<String, String>();
        HashMap<String, String> picGroupKeyMap = new HashMap<String, String>();
        try {
            HashMap<String, FieldDefine> fieldDefineCatch = new HashMap<String, FieldDefine>();
            for (FixedFieldAndGroupInfo fixedFieldAndGroupInfo : fixedFieldAndGroupInfos) {
                LinkedHashMap<String, String> fieldGroupMap = fixedFieldAndGroupInfo.getFieldGroupMap();
                if (MapUtils.isEmpty(fieldGroupMap)) continue;
                for (Map.Entry<String, String> fieldKeyGroupKey : fieldGroupMap.entrySet()) {
                    FieldDefine fieldDefine;
                    String fieldKey = fieldKeyGroupKey.getKey();
                    String groupKey = fieldKeyGroupKey.getValue();
                    if (!StringUtils.hasText(groupKey)) continue;
                    if (fieldDefineCatch.containsKey(fieldKey)) {
                        fieldDefine = (FieldDefine)fieldDefineCatch.get(fieldKey);
                    } else {
                        fieldDefine = this.runTimeViewController.queryFieldDefine(fieldKey);
                        fieldDefineCatch.put(fieldKey, fieldDefine);
                    }
                    String newGroupKey = UUID.randomUUID().toString();
                    if (FieldType.FIELD_TYPE_FILE == fieldDefine.getType()) {
                        fileGroupKeyMap.put(groupKey, newGroupKey);
                    } else if (FieldType.FIELD_TYPE_PICTURE == fieldDefine.getType()) {
                        picGroupKeyMap.put(groupKey, newGroupKey);
                    }
                    fieldKeyGroupKey.setValue(newGroupKey);
                }
            }
        }
        catch (Exception e) {
            String errMsg = "\u67e5\u8be2\u6307\u6807\u5931\u8d25";
            logger.error(errMsg, e);
            throw new FileCopyException(errMsg, e);
        }
        this.batchCopyFile(fileCopyFixedParam, fileGroupKeyMap, picGroupKeyMap);
        return fixedFieldAndGroupInfos;
    }

    @Override
    public List<FloatFieldAndGroupInfo> batchCopyFile(FileCopyFloatParam fileCopyFloatParam) throws FileCopyException {
        List<FloatFieldAndGroupInfo> floatFieldAndGroupInfos = fileCopyFloatParam.getFloatFieldAndGroupInfos();
        if (CollectionUtils.isEmpty(floatFieldAndGroupInfos)) {
            return floatFieldAndGroupInfos;
        }
        HashMap<String, String> fileGroupKeyMap = new HashMap<String, String>();
        HashMap<String, String> picGroupKeyMap = new HashMap<String, String>();
        try {
            HashMap<String, FieldDefine> fieldDefineCatch = new HashMap<String, FieldDefine>();
            for (FloatFieldAndGroupInfo floatFieldAndGroupInfo : floatFieldAndGroupInfos) {
                LinkedHashMap<String, String> fieldGroupMap = floatFieldAndGroupInfo.getFieldGroupMap();
                if (MapUtils.isEmpty(fieldGroupMap)) continue;
                for (Map.Entry<String, String> fieldKeyGroupKey : fieldGroupMap.entrySet()) {
                    FieldDefine fieldDefine;
                    String fieldKey = fieldKeyGroupKey.getKey();
                    String groupKey = fieldKeyGroupKey.getValue();
                    if (!StringUtils.hasText(groupKey)) continue;
                    if (fieldDefineCatch.containsKey(fieldKey)) {
                        fieldDefine = (FieldDefine)fieldDefineCatch.get(fieldKey);
                    } else {
                        fieldDefine = this.runTimeViewController.queryFieldDefine(fieldKey);
                        fieldDefineCatch.put(fieldKey, fieldDefine);
                    }
                    String newGroupKey = UUID.randomUUID().toString();
                    if (FieldType.FIELD_TYPE_FILE == fieldDefine.getType()) {
                        fileGroupKeyMap.put(groupKey, newGroupKey);
                    } else if (FieldType.FIELD_TYPE_PICTURE == fieldDefine.getType()) {
                        picGroupKeyMap.put(groupKey, newGroupKey);
                    }
                    fieldKeyGroupKey.setValue(newGroupKey);
                }
            }
        }
        catch (Exception e) {
            String errMsg = "\u67e5\u8be2\u6307\u6807\u5931\u8d25";
            logger.error(errMsg, e);
            throw new FileCopyException(errMsg, e);
        }
        this.batchCopyFile(fileCopyFloatParam, fileGroupKeyMap, picGroupKeyMap);
        return floatFieldAndGroupInfos;
    }

    private void batchCopyFile(FileCopyBaseParam fileCopyBaseParam, Map<String, String> fileGroupKeyMap, Map<String, String> picGroupKeyMap) throws FileCopyException {
        TaskDefine fromTaskDefine = this.runTimeViewController.queryTaskDefine(fileCopyBaseParam.getFromTaskKey());
        FileBucketNameParam fromParam = new FileBucketNameParam(fromTaskDefine.getDataScheme());
        TaskDefine toTaskDefine = this.runTimeViewController.queryTaskDefine(fileCopyBaseParam.getToTaskKey());
        FileBucketNameParam toParam = new FileBucketNameParam(toTaskDefine.getDataScheme());
        if (null != this.fileBucketNameProvider && !this.fileBucketNameProvider.getBucketName(fromParam).equals(this.fileBucketNameProvider.getBucketName(toParam))) {
            throw new FileCopyException("\u6e90\u4efb\u52a1\u548c\u76ee\u6807\u4efb\u52a1\u4e0d\u5728\u540c\u4e00\u4e2a\u6587\u4ef6\u5b58\u50a8\u533a\u57df");
        }
        ArrayList<String> copiedFileKeyCatch = new ArrayList<String>();
        try (OSSOperationServiceImpl fromOssOperationService = new OSSOperationServiceImpl(this.fileBucketNameProvider, this.fileUploadCheckService);
             OSSOperationServiceImpl toOssOperationService = new OSSOperationServiceImpl(this.fileBucketNameProvider, this.fileUploadCheckService);){
            fromOssOperationService.init(fromParam);
            toOssOperationService.init(toParam);
            if (MapUtils.isNotEmpty(fileGroupKeyMap)) {
                List<String> copyAttachmentKeys;
                Map<String, FileRelInfo> fileRelInfoMap;
                if (CollectionUtils.isNotEmpty(fileCopyBaseParam.getOldFileGroupKeys())) {
                    this.attachmentIOService.batchMarkDeletion(toTaskDefine.getDataScheme(), fileCopyBaseParam.getToFormSchemeKey(), new HashSet<String>(fileCopyBaseParam.getOldFileGroupKeys()));
                }
                if (!(fileRelInfoMap = this.getFileRelInfo(fromTaskDefine.getDataScheme(), new ArrayList<String>(fileGroupKeyMap.keySet()))).isEmpty() && CollectionUtils.isNotEmpty(copyAttachmentKeys = this.copyAttachment(fileCopyBaseParam, fileGroupKeyMap, fromTaskDefine, toTaskDefine, fromOssOperationService, toOssOperationService, fileRelInfoMap))) {
                    copiedFileKeyCatch.addAll(copyAttachmentKeys);
                }
            }
            if (MapUtils.isNotEmpty(picGroupKeyMap)) {
                List<String> copyPictureKeys;
                Map<String, List<ObjectInfo>> groupObjInfoMap;
                Map<String, List<ObjectInfo>> toGroupObjInfoMap;
                if (CollectionUtils.isNotEmpty(fileCopyBaseParam.getOldPicGroupKeys()) && !(toGroupObjInfoMap = toOssOperationService.batchGetObjectInfoByProp("fileGroupKey", fileCopyBaseParam.getOldPicGroupKeys(), false)).isEmpty()) {
                    Collection<List<ObjectInfo>> objectInfos = toGroupObjInfoMap.values();
                    ArrayList<String> delPicFileKeys = new ArrayList<String>();
                    for (List<ObjectInfo> objectInfo : objectInfos) {
                        if (!CollectionUtils.isNotEmpty(objectInfo)) continue;
                        delPicFileKeys.addAll(objectInfo.stream().map(ObjectInfo::getKey).collect(Collectors.toList()));
                    }
                    toOssOperationService.batchDeletePic(delPicFileKeys);
                }
                if (!(groupObjInfoMap = fromOssOperationService.batchGetObjectInfoByProp("fileGroupKey", new ArrayList<String>(picGroupKeyMap.keySet()), false)).isEmpty() && CollectionUtils.isNotEmpty(copyPictureKeys = this.copyPicture(toOssOperationService, picGroupKeyMap, groupObjInfoMap))) {
                    copiedFileKeyCatch.addAll(copyPictureKeys);
                }
            }
        }
        catch (IOException e) {
            String errMsg = "\u521d\u59cb\u5316oss\u5bf9\u8c61\u5931\u8d25";
            logger.error(errMsg, e);
            this.deleteCopiedFile(toParam, copiedFileKeyCatch);
            throw new FileCopyException(errMsg, e);
        }
        catch (OSSOperationException e) {
            String errMsg = "\u9644\u4ef6\u590d\u5236\u5931\u8d25";
            logger.error(errMsg, e);
            this.deleteCopiedFile(toParam, copiedFileKeyCatch);
            throw new FileCopyException(errMsg, e);
        }
        catch (FileCopyException e) {
            this.deleteCopiedFile(toParam, copiedFileKeyCatch);
            throw new FileCopyException(e.getMessage(), e);
        }
    }

    private void deleteCopiedFile(FileBucketNameParam toParam, List<String> copiedFileKeyCatch) {
        if (CollectionUtils.isNotEmpty(copiedFileKeyCatch)) {
            try (OSSOperationServiceImpl toOssOperationService = new OSSOperationServiceImpl(this.fileBucketNameProvider, this.fileUploadCheckService);){
                toOssOperationService.init(toParam);
                toOssOperationService.batchDelete(copiedFileKeyCatch);
            }
            catch (OSSOperationException | IOException e2) {
                logger.error("\u5220\u9664\u5df2\u590d\u5236\u9644\u4ef6\u5931\u8d25", e2);
            }
        }
    }

    private List<String> copyPicture(OSSOperationServiceImpl toOssOperationService, Map<String, String> picGroupKeyMap, Map<String, List<ObjectInfo>> groupObjInfoMap) throws OSSOperationException {
        ArrayList<String> copiedFileKeyCatch = new ArrayList<String>();
        for (Map.Entry<String, List<ObjectInfo>> groupObjInfo : groupObjInfoMap.entrySet()) {
            String fromGroupKey = groupObjInfo.getKey();
            String toGroupKey = picGroupKeyMap.get(fromGroupKey);
            List<ObjectInfo> objInfo = groupObjInfo.getValue();
            List<String> fileKeys = objInfo.stream().map(ObjectInfo::getKey).collect(Collectors.toList());
            List<String> copyFileKeys = toOssOperationService.batchCopy(fileKeys);
            copiedFileKeyCatch.addAll(copyFileKeys);
            HashMap<String, String> updateInfo = new HashMap<String, String>();
            updateInfo.put("fileGroupKey", toGroupKey);
            toOssOperationService.batchUpdate(copyFileKeys, updateInfo);
            for (int i = 0; i < fileKeys.size(); ++i) {
                List<ObjectInfo> thumbnailInfos = toOssOperationService.getObjectInfoByProp("fileGroupKey", fileKeys.get(i), false);
                List<String> thumbnailKeys = thumbnailInfos.stream().map(ObjectInfo::getKey).collect(Collectors.toList());
                List<String> copyThumbnailKeys = toOssOperationService.batchCopy(thumbnailKeys);
                copiedFileKeyCatch.addAll(copyThumbnailKeys);
                HashMap<String, String> thumbnailUpdateInfo = new HashMap<String, String>();
                thumbnailUpdateInfo.put("fileGroupKey", copyFileKeys.get(i));
                toOssOperationService.batchUpdate(copyThumbnailKeys, thumbnailUpdateInfo);
            }
        }
        return copiedFileKeyCatch;
    }

    private List<String> copyAttachment(FileCopyBaseParam fileCopyBaseParam, Map<String, String> fileGroupKeyMap, TaskDefine fromTaskDefine, TaskDefine toTaskDefine, OSSOperationServiceImpl fromOssOperationService, OSSOperationServiceImpl toOssOperationService, Map<String, FileRelInfo> fileRelInfoMap) throws OSSOperationException, FileCopyException {
        DimensionMappingConverter dimensionMappingConverter = this.fileCopyParamProvider.getDimensionMappingConverter();
        DataFieldMappingConverter dataFieldMappingConverter = this.fileCopyParamProvider.getDataFieldMappingConverter();
        ArrayList<String> copiedFileKeyCatch = new ArrayList<String>();
        boolean openFilepool = this.filePoolService.isOpenFilepool();
        if (openFilepool) {
            List<String> copyAttachmentKeys = this.copyAttachmentOpenFilepool(fileCopyBaseParam, fileGroupKeyMap, fromTaskDefine, toTaskDefine, fromOssOperationService, toOssOperationService, fileRelInfoMap, dimensionMappingConverter, dataFieldMappingConverter);
            if (CollectionUtils.isNotEmpty(copyAttachmentKeys)) {
                copiedFileKeyCatch.addAll(copyAttachmentKeys);
            }
        } else {
            List<String> copyAttachmentKeys = this.copyAttachmentCloseFilepool(toOssOperationService, fileRelInfoMap, dimensionMappingConverter, dataFieldMappingConverter, fileGroupKeyMap);
            if (CollectionUtils.isNotEmpty(copyAttachmentKeys)) {
                copiedFileKeyCatch.addAll(copyAttachmentKeys);
            }
        }
        this.insertFileRelTable(toTaskDefine, fileCopyBaseParam.getToFormSchemeKey(), fileRelInfoMap.values());
        return copiedFileKeyCatch;
    }

    private void replaceNewField(DataFieldMappingConverter dataFieldMappingConverter, Map<String, String> fromFieldtoFieldCatch, FileRelInfo fileRelInfo) {
        String fieldKey = fileRelInfo.getFieldKey();
        if (fromFieldtoFieldCatch.containsKey(fieldKey)) {
            fileRelInfo.setFieldKey(fromFieldtoFieldCatch.get(fieldKey));
        } else {
            DataField fromDataField = this.runtimeDataSchemeService.getDataField(fieldKey);
            DataTable fromDataTable = this.runtimeDataSchemeService.getDataTable(fromDataField.getDataTableKey());
            Map dataFieldMapByTable = dataFieldMappingConverter.getDataFieldMapByTable(fromDataTable.getCode(), Arrays.asList(fromDataField.getCode()));
            if (MapUtils.isNotEmpty(dataFieldMapByTable) && null != dataFieldMapByTable.get(fromDataField.getCode())) {
                DataFieldMap dataFieldMap = (DataFieldMap)dataFieldMapByTable.get(fromDataField.getCode());
                DataTable toDataTable = this.runtimeDataSchemeService.getDataTableByCode(dataFieldMap.getDataTableCode());
                DataField toDataField = this.runtimeDataSchemeService.getDataFieldByTableKeyAndCode(toDataTable.getKey(), dataFieldMap.getFieldCode());
                fromFieldtoFieldCatch.put(fieldKey, toDataField.getKey());
                fileRelInfo.setFieldKey(toDataField.getKey());
            } else {
                fromFieldtoFieldCatch.put(fieldKey, fieldKey);
            }
        }
    }

    private List<String> copyAttachmentCloseFilepool(OSSOperationServiceImpl toOssOperationService, Map<String, FileRelInfo> fileRelInfoMap, DimensionMappingConverter dimensionMappingConverter, DataFieldMappingConverter dataFieldMappingConverter, Map<String, String> fileGroupKeyMap) throws OSSOperationException, FileCopyException {
        ArrayList<String> copiedFileKeyCatch = new ArrayList<String>();
        HashMap<String, String> fromFieldtoFieldCatch = new HashMap<String, String>();
        for (Map.Entry<String, FileRelInfo> groupKeyFileRelInfo : fileRelInfoMap.entrySet()) {
            String fromGroupKey = groupKeyFileRelInfo.getKey();
            FileRelInfo fileRelInfo = groupKeyFileRelInfo.getValue();
            List<String> copyFileKeys = toOssOperationService.batchCopy(new ArrayList<String>(fileRelInfo.getFileKeys()));
            copiedFileKeyCatch.addAll(copyFileKeys);
            fileRelInfo.setFileKeys(new HashSet<String>(copyFileKeys));
            HashMap<String, String> updateInfo = new HashMap<String, String>();
            String toGroupKey = fileGroupKeyMap.get(fromGroupKey);
            fileRelInfo.setGroupKey(toGroupKey);
            updateInfo.put("fileGroupKey", toGroupKey);
            toOssOperationService.batchUpdate(copyFileKeys, updateInfo);
            List targetMasterKey = dimensionMappingConverter.getMappingMasterKey(fileRelInfo.getDimensionValueSet());
            if (targetMasterKey.size() > 1) {
                throw new FileCopyException("\u7ef4\u5ea6\u6620\u5c04\u8f6c\u6362\u5f02\u5e38\uff0c\u7ef4\u5ea6\u6620\u5c04\u8f6c\u6362\u540e\u7ef4\u5ea6\u503c\u96c6\u5408\u5927\u5c0f\u5927\u4e8e1");
            }
            fileRelInfo.setDimensionValueSet((DimensionValueSet)targetMasterKey.get(0));
            if (null == dataFieldMappingConverter) continue;
            this.replaceNewField(dataFieldMappingConverter, fromFieldtoFieldCatch, fileRelInfo);
        }
        return copiedFileKeyCatch;
    }

    private List<String> copyAttachmentOpenFilepool(FileCopyBaseParam fileCopyBaseParam, Map<String, String> fileGroupKeyMap, TaskDefine fromTaskDefine, TaskDefine toTaskDefine, OSSOperationServiceImpl fromOssOperationService, OSSOperationServiceImpl toOssOperationService, Map<String, FileRelInfo> fileRelInfoMap, DimensionMappingConverter dimensionMappingConverter, DataFieldMappingConverter dataFieldMappingConverter) throws FileCopyException, OSSOperationException {
        ArrayList<String> copiedFileKeyCatch = new ArrayList<String>();
        IEntityDefine fromEntityDefine = this.entityMetaService.queryEntity(fromTaskDefine.getDw());
        IEntityDefine toEntityDefine = this.entityMetaService.queryEntity(toTaskDefine.getDw());
        if (fileCopyBaseParam.getFromTaskKey().equals(fileCopyBaseParam.getToTaskKey())) {
            HashMap<String, String> fromFieldtoFieldCatch = new HashMap<String, String>();
            for (Map.Entry<String, FileRelInfo> groupKeyFileRelInfo : fileRelInfoMap.entrySet()) {
                String fromGroupKey = groupKeyFileRelInfo.getKey();
                FileRelInfo fileRelInfo = groupKeyFileRelInfo.getValue();
                String toGroupKey = fileGroupKeyMap.get(fromGroupKey);
                fileRelInfo.setGroupKey(toGroupKey);
                List targetMasterKey = dimensionMappingConverter.getMappingMasterKey(fileRelInfo.getDimensionValueSet());
                if (targetMasterKey.size() > 1) {
                    throw new FileCopyException("\u7ef4\u5ea6\u6620\u5c04\u8f6c\u6362\u5f02\u5e38\uff0c\u7ef4\u5ea6\u6620\u5c04\u8f6c\u6362\u540e\u7ef4\u5ea6\u503c\u96c6\u5408\u5927\u5c0f\u5927\u4e8e1");
                }
                fileRelInfo.setDimensionValueSet((DimensionValueSet)targetMasterKey.get(0));
                if (null != dataFieldMappingConverter) {
                    this.replaceNewField(dataFieldMappingConverter, fromFieldtoFieldCatch, fileRelInfo);
                }
                if (fileRelInfo.getDimensionValueSet().getValue(fromEntityDefine.getDimensionName()).equals(((DimensionValueSet)targetMasterKey.get(0)).getValue(toEntityDefine.getDimensionName()))) continue;
                List<ObjectInfo> fromObjInfos = fromOssOperationService.getObjectInfoByKeys(new ArrayList<String>(fileRelInfo.getFileKeys()), false);
                List<String> newFileKeys = this.fileCopy(fromObjInfos, fileCopyBaseParam.getToTaskKey(), (String)((DimensionValueSet)targetMasterKey.get(0)).getValue(toEntityDefine.getDimensionName()), toOssOperationService, copiedFileKeyCatch);
                fileRelInfo.setFileKeys(new HashSet<String>(newFileKeys));
            }
        } else {
            HashMap<String, String> fromFieldtoFieldCatch = new HashMap<String, String>();
            for (Map.Entry<String, FileRelInfo> groupKeyFileRelInfo : fileRelInfoMap.entrySet()) {
                String fromGroupKey = groupKeyFileRelInfo.getKey();
                FileRelInfo fileRelInfo = groupKeyFileRelInfo.getValue();
                String toGroupKey = fileGroupKeyMap.get(fromGroupKey);
                fileRelInfo.setGroupKey(toGroupKey);
                List targetMasterKey = dimensionMappingConverter.getMappingMasterKey(fileRelInfo.getDimensionValueSet());
                if (targetMasterKey.size() > 1) {
                    throw new FileCopyException("\u7ef4\u5ea6\u6620\u5c04\u8f6c\u6362\u5f02\u5e38\uff0c\u7ef4\u5ea6\u6620\u5c04\u8f6c\u6362\u540e\u7ef4\u5ea6\u503c\u96c6\u5408\u5927\u5c0f\u5927\u4e8e1");
                }
                fileRelInfo.setDimensionValueSet((DimensionValueSet)targetMasterKey.get(0));
                if (null != dataFieldMappingConverter) {
                    this.replaceNewField(dataFieldMappingConverter, fromFieldtoFieldCatch, fileRelInfo);
                }
                List<ObjectInfo> fromObjInfos = fromOssOperationService.getObjectInfoByKeys(new ArrayList<String>(fileRelInfo.getFileKeys()), false);
                List<String> newFileKeys = this.fileCopy(fromObjInfos, fileCopyBaseParam.getToTaskKey(), (String)((DimensionValueSet)targetMasterKey.get(0)).getValue(toEntityDefine.getDimensionName()), toOssOperationService, copiedFileKeyCatch);
                fileRelInfo.setFileKeys(new HashSet<String>(newFileKeys));
            }
        }
        return copiedFileKeyCatch;
    }

    private List<String> fileCopy(List<ObjectInfo> fromObjInfos, String toTaskKey, String toDwValue, OSSOperationServiceImpl toOssOperationService, List<String> copiedFileKeyCatch) throws OSSOperationException {
        ArrayList<String> resFileKeys = new ArrayList<String>();
        String filePoolKey = toTaskKey + toDwValue;
        FilePoolAllFileContext filePoolAllFileContext = new FilePoolAllFileContext();
        filePoolAllFileContext.setFilepoolKey(filePoolKey);
        List<FilePoolFiles> toFilePoolFiles = this.filePoolService.getFilePoolFiles(filePoolAllFileContext, false);
        for (ObjectInfo fromObjInfo : fromObjInfos) {
            boolean sameName = false;
            for (FilePoolFiles toFilePoolFile : toFilePoolFiles) {
                if (toFilePoolFile.getFileName().equals(fromObjInfo.getName()) && (toFilePoolFile.getSize() != fromObjInfo.getSize() || !toFilePoolFile.getMd5().equals(fromObjInfo.getMd5()))) {
                    sameName = true;
                    if (!this.filePoolService.judgeFileOverwritten(toFilePoolFile.getFileKey(), toTaskKey)) {
                        List<String> copyFileKeys = toOssOperationService.batchCopy(Arrays.asList(fromObjInfo.getKey()));
                        resFileKeys.add(copyFileKeys.get(0));
                        copiedFileKeyCatch.add(copyFileKeys.get(0));
                        String newFileName = this.rename(toFilePoolFiles, fromObjInfo.getName() + "(1)", 1);
                        HashMap<String, String> updateInfo = new HashMap<String, String>();
                        updateInfo.put("name", newFileName);
                        updateInfo.put("filePool", filePoolKey);
                        toOssOperationService.update(copyFileKeys.get(0), updateInfo);
                        break;
                    }
                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    toOssOperationService.download(fromObjInfo.getKey(), outputStream);
                    ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
                    toOssOperationService.batchDelete(Arrays.asList(toFilePoolFile.getFileKey()));
                    HashMap<String, String> expandInfo = new HashMap<String, String>();
                    expandInfo.put("secretlevel", toFilePoolFile.getSecretCode());
                    expandInfo.put("filePool", filePoolKey);
                    expandInfo.put("category", toFilePoolFile.getCategoryCode());
                    toOssOperationService.uploadByKey(toFilePoolFile.getFileName(), toFilePoolFile.getFileKey(), inputStream, expandInfo);
                    resFileKeys.add(toFilePoolFile.getFileKey());
                    break;
                }
                if (!toFilePoolFile.getFileName().equals(fromObjInfo.getName()) || toFilePoolFile.getSize() != fromObjInfo.getSize() || !toFilePoolFile.getMd5().equals(fromObjInfo.getMd5())) continue;
                sameName = true;
                resFileKeys.add(toFilePoolFile.getFileKey());
                break;
            }
            if (sameName) continue;
            List<String> copyFileKeys = toOssOperationService.batchCopy(Arrays.asList(fromObjInfo.getKey()));
            HashMap<String, String> updateInfo = new HashMap<String, String>();
            updateInfo.put("filePool", filePoolKey);
            toOssOperationService.update(copyFileKeys.get(0), updateInfo);
            resFileKeys.add(copyFileKeys.get(0));
            copiedFileKeyCatch.add(copyFileKeys.get(0));
        }
        return resFileKeys;
    }

    private String rename(List<FilePoolFiles> fileInfos, String fileName, int index) {
        for (FilePoolFiles fileInfo : fileInfos) {
            if (!fileInfo.getFileName().equals(fileName)) continue;
            for (int i = fileName.length() - 1; i >= 0; --i) {
                if (fileName.charAt(i) != '(') continue;
                fileName = fileName.substring(0, i);
                break;
            }
            String newFileKey = fileName + "(" + index + 1 + ")";
            this.rename(fileInfos, newFileKey, ++index);
        }
        return fileName;
    }

    private Map<String, FileRelInfo> getFileRelInfo(String dataSchemeKey, List<String> fromFileGroupKeys) throws FileCopyException {
        DataScheme dataScheme = this.runtimeDataSchemeService.getDataScheme(dataSchemeKey);
        String tableName = this.fileOperationService.getCurrentSplitTableName(dataScheme);
        TableModelDefine filePoolTable = this.dataModelService.getTableModelDefineByName(tableName);
        if (null == filePoolTable) {
            logger.error("\u9644\u4ef6\u5173\u8054\u8868\u672a\u521b\u5efa\uff0c\u8bf7\u91cd\u65b0\u53d1\u5e03\u6570\u636e\u65b9\u6848\uff01");
            throw new FileCopyException("\u9644\u4ef6\u5173\u8054\u8868\u672a\u521b\u5efa\uff0c\u8bf7\u91cd\u65b0\u53d1\u5e03\u6570\u636e\u65b9\u6848\uff01");
        }
        DimensionChanger dimensionChanger = this.dataEngineAdapter.getDimensionChanger(filePoolTable.getName());
        int groupIndex = 0;
        int fileKeyIndex = 0;
        List filePoolFields = this.dataModelService.getColumnModelDefinesByTable(filePoolTable.getID());
        NvwaQueryModel queryModel = new NvwaQueryModel();
        block12: for (int i = 0; i < filePoolFields.size(); ++i) {
            ColumnModelDefine filePoolField = (ColumnModelDefine)filePoolFields.get(i);
            queryModel.getColumns().add(new NvwaQueryColumn(filePoolField));
            switch (filePoolField.getCode()) {
                case "GROUPKEY": {
                    groupIndex = filePoolFields.indexOf(filePoolField);
                    queryModel.getColumnFilters().put(filePoolField, fromFileGroupKeys);
                    continue block12;
                }
                case "ISDELETE": {
                    queryModel.getColumnFilters().put(filePoolField, "0");
                    continue block12;
                }
                case "FILEKEY": {
                    fileKeyIndex = filePoolFields.indexOf(filePoolField);
                    continue block12;
                }
            }
        }
        HashMap<String, FileRelInfo> result = new HashMap<String, FileRelInfo>();
        INvwaDataAccess readOnlyDataAccess = this.nvwaDataAccessProvider.createReadOnlyDataAccess(queryModel);
        DataAccessContext context = new DataAccessContext(this.dataModelService);
        try {
            MemoryDataSet dataTable = readOnlyDataAccess.executeQuery(context);
            List columns = queryModel.getColumns();
            for (int i = 0; i < dataTable.size(); ++i) {
                DataRow item = dataTable.get(i);
                String groupKey = item.getString(groupIndex);
                FileRelInfo fileRelInfo = (FileRelInfo)result.get(groupKey);
                if (null == fileRelInfo) {
                    fileRelInfo = new FileRelInfo();
                    fileRelInfo.setGroupKey(groupKey);
                    DimensionValueSet dimensionValueSet = new DimensionValueSet();
                    for (int j = 0; j < columns.size(); ++j) {
                        if (((NvwaQueryColumn)columns.get(j)).getColumnModel().getCode().equals("FILEKEY")) {
                            HashSet<String> fileKeys = new HashSet<String>();
                            fileKeys.add(item.getString(j));
                            fileRelInfo.setFileKeys(fileKeys);
                            continue;
                        }
                        if (((NvwaQueryColumn)columns.get(j)).getColumnModel().getCode().equals("FIELD_KEY")) {
                            fileRelInfo.setFieldKey(item.getString(j));
                            continue;
                        }
                        if (((NvwaQueryColumn)columns.get(j)).getColumnModel().getCode().equals("ID") || ((NvwaQueryColumn)columns.get(j)).getColumnModel().getCode().equals("GROUPKEY") || ((NvwaQueryColumn)columns.get(j)).getColumnModel().getCode().equals("ISDELETE")) continue;
                        String dimensionName = dimensionChanger.getDimensionName(((NvwaQueryColumn)columns.get(j)).getColumnModel());
                        dimensionValueSet.setValue(dimensionName, (Object)item.getString(j));
                    }
                    fileRelInfo.setDimensionValueSet(dimensionValueSet);
                    result.put(groupKey, fileRelInfo);
                    continue;
                }
                fileRelInfo.getFileKeys().add(item.getString(fileKeyIndex));
            }
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            throw new FileCopyException("\u67e5\u8be2\u9644\u4ef6\u5173\u8054\u8868\u5931\u8d25", e);
        }
        return result;
    }

    private void insertFileRelTable(TaskDefine taskDefine, String formSchemeKey, Collection<FileRelInfo> fileRelInfos) throws FileCopyException {
        DataAccessContext context = new DataAccessContext(this.dataModelService);
        NvwaQueryModel queryModel = new NvwaQueryModel();
        DataScheme dataScheme = this.runtimeDataSchemeService.getDataScheme(taskDefine.getDataScheme());
        String tableName = this.fileOperationService.getCurrentSplitTableName(dataScheme);
        TableModelDefine filePoolTable = this.dataModelService.getTableModelDefineByName(tableName);
        if (null == filePoolTable) {
            logger.error("\u9644\u4ef6\u5173\u8054\u8868\u672a\u521b\u5efa\uff0c\u8bf7\u91cd\u65b0\u53d1\u5e03\u6570\u636e\u65b9\u6848\uff01");
            throw new FileCopyException("\u9644\u4ef6\u5173\u8054\u8868\u672a\u521b\u5efa\uff0c\u8bf7\u91cd\u65b0\u53d1\u5e03\u6570\u636e\u65b9\u6848\uff01");
        }
        List filePoolFields = this.dataModelService.getColumnModelDefinesByTable(filePoolTable.getID());
        for (ColumnModelDefine filePoolField : filePoolFields) {
            queryModel.getColumns().add(new NvwaQueryColumn(filePoolField));
        }
        DimensionChanger dimensionChanger = this.dataEngineAdapter.getDimensionChanger(filePoolTable.getName());
        INvwaUpdatableDataAccess updatableDataAccess = this.nvwaDataAccessProvider.createUpdatableDataAccess(queryModel);
        List columns = queryModel.getColumns();
        try {
            INvwaDataUpdator nvwaDataUpdator = updatableDataAccess.openForUpdate(context);
            int insertRowNum = 0;
            for (FileRelInfo fileRelInfo : fileRelInfos) {
                for (String fileKey : fileRelInfo.getFileKeys()) {
                    INvwaDataRow iNvwaDataRow = nvwaDataUpdator.addInsertRow();
                    ++insertRowNum;
                    String id = UUID.randomUUID().toString();
                    block19: for (int i = 0; i < columns.size(); ++i) {
                        switch (((NvwaQueryColumn)columns.get(i)).getColumnModel().getCode()) {
                            case "ID": {
                                iNvwaDataRow.setValue(i, (Object)id);
                                continue block19;
                            }
                            case "GROUPKEY": {
                                iNvwaDataRow.setValue(i, (Object)fileRelInfo.getGroupKey());
                                continue block19;
                            }
                            case "FILEKEY": {
                                iNvwaDataRow.setValue(i, (Object)fileKey);
                                continue block19;
                            }
                            case "FIELD_KEY": {
                                iNvwaDataRow.setValue(i, (Object)fileRelInfo.getFieldKey());
                                continue block19;
                            }
                            case "ISDELETE": {
                                iNvwaDataRow.setValue(i, (Object)"0");
                                continue block19;
                            }
                            default: {
                                String dimensionName = dimensionChanger.getDimensionName(((NvwaQueryColumn)columns.get(i)).getColumnModel());
                                iNvwaDataRow.setValue(i, fileRelInfo.getDimensionValueSet().getValue(dimensionName));
                            }
                        }
                    }
                    if (insertRowNum < 5000) continue;
                    nvwaDataUpdator.commitChanges(context);
                    insertRowNum = 0;
                }
            }
            if (insertRowNum > 0) {
                nvwaDataUpdator.commitChanges(context);
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
                for (FileRelInfo fileRelInfo : fileRelInfos) {
                    FileUploadEvent fileUploadEvent = new FileUploadEvent(dataScheme.getKey(), taskDefine.getKey(), formSchemeKey, DimensionValueSetUtil.buildDimensionCombination((DimensionValueSet)fileRelInfo.getDimensionValueSet(), (String)formSchemeKey), fileRelInfo.getFieldKey(), fileRelInfo.getGroupKey(), new ArrayList<String>(fileRelInfo.getFileKeys()));
                    fileListener.afterFileUpload(fileUploadEvent);
                }
            }
        }
        catch (Exception e) {
            logger.error(PromptConsts.causeIfError(e.getMessage()), e);
        }
    }
}

