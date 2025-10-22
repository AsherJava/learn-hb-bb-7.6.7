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
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.definition.common.FieldType
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.nr.common.exception.NotFoundTableDefineException
 *  com.jiuqi.nr.common.util.DataEngineAdapter
 *  com.jiuqi.nr.common.util.DimensionChanger
 *  com.jiuqi.nr.data.engine.gather.GatherDataTable
 *  com.jiuqi.nr.data.engine.gather.param.FieldAndGroupKeyInfo
 *  com.jiuqi.nr.data.engine.gather.param.FileSumContext
 *  com.jiuqi.nr.data.engine.gather.param.FileSumInfo
 *  com.jiuqi.nr.data.engine.gather.util.FileCalculateService
 *  com.jiuqi.nr.datascheme.api.DataScheme
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationBuilder
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.entity.engine.executors.ExecutorContext
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.engine.setting.AuthorityType
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityDataService
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nvwa.dataengine.INvwaDataAccess
 *  com.jiuqi.nvwa.dataengine.INvwaDataAccessProvider
 *  com.jiuqi.nvwa.dataengine.INvwaDataRow
 *  com.jiuqi.nvwa.dataengine.INvwaDataUpdator
 *  com.jiuqi.nvwa.dataengine.INvwaUpdatableDataAccess
 *  com.jiuqi.nvwa.dataengine.INvwaUpdatableDataSet
 *  com.jiuqi.nvwa.dataengine.common.DataAccessContext
 *  com.jiuqi.nvwa.dataengine.exception.IncorrectQueryException
 *  com.jiuqi.nvwa.dataengine.model.NvwaQueryColumn
 *  com.jiuqi.nvwa.dataengine.model.NvwaQueryModel
 *  com.jiuqi.nvwa.dataengine.util.DataEngineUtil
 *  com.jiuqi.nvwa.definition.common.NrdbHelper
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.jdbc.datasource.DataSourceUtils
 */
package com.jiuqi.nr.attachment.service.impl;

import com.jiuqi.bi.dataset.DataRow;
import com.jiuqi.bi.dataset.MemoryDataSet;
import com.jiuqi.bi.oss.ObjectInfo;
import com.jiuqi.bi.oss.ObjectStorageException;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.definition.common.FieldType;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.nr.attachment.constant.PromptConsts;
import com.jiuqi.nr.attachment.input.CommonParamsDTO;
import com.jiuqi.nr.attachment.input.FilePoolAllFileContext;
import com.jiuqi.nr.attachment.input.FileUploadInfo;
import com.jiuqi.nr.attachment.listener.IFileListener;
import com.jiuqi.nr.attachment.listener.IFileListenerProvider;
import com.jiuqi.nr.attachment.listener.param.FileBatchDelEvent;
import com.jiuqi.nr.attachment.listener.param.FileUploadEvent;
import com.jiuqi.nr.attachment.message.FileCopyInfo;
import com.jiuqi.nr.attachment.message.FileInfo;
import com.jiuqi.nr.attachment.output.FilePoolFiles;
import com.jiuqi.nr.attachment.param.FileSumUploadInfo;
import com.jiuqi.nr.attachment.param.FileSumUploadParam;
import com.jiuqi.nr.attachment.param.ProcessingFileParam;
import com.jiuqi.nr.attachment.provider.param.FileBucketNameParam;
import com.jiuqi.nr.attachment.service.FileOperationService;
import com.jiuqi.nr.attachment.service.FilePoolService;
import com.jiuqi.nr.attachment.tools.AttachmentFileAreaService;
import com.jiuqi.nr.common.exception.NotFoundTableDefineException;
import com.jiuqi.nr.common.util.DataEngineAdapter;
import com.jiuqi.nr.common.util.DimensionChanger;
import com.jiuqi.nr.data.engine.gather.GatherDataTable;
import com.jiuqi.nr.data.engine.gather.param.FieldAndGroupKeyInfo;
import com.jiuqi.nr.data.engine.gather.param.FileSumContext;
import com.jiuqi.nr.data.engine.gather.param.FileSumInfo;
import com.jiuqi.nr.data.engine.gather.util.FileCalculateService;
import com.jiuqi.nr.datascheme.api.DataScheme;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationBuilder;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.entity.engine.executors.ExecutorContext;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.engine.setting.AuthorityType;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityDataService;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nvwa.dataengine.INvwaDataAccess;
import com.jiuqi.nvwa.dataengine.INvwaDataAccessProvider;
import com.jiuqi.nvwa.dataengine.INvwaDataRow;
import com.jiuqi.nvwa.dataengine.INvwaDataUpdator;
import com.jiuqi.nvwa.dataengine.INvwaUpdatableDataAccess;
import com.jiuqi.nvwa.dataengine.INvwaUpdatableDataSet;
import com.jiuqi.nvwa.dataengine.common.DataAccessContext;
import com.jiuqi.nvwa.dataengine.exception.IncorrectQueryException;
import com.jiuqi.nvwa.dataengine.model.NvwaQueryColumn;
import com.jiuqi.nvwa.dataengine.model.NvwaQueryModel;
import com.jiuqi.nvwa.dataengine.util.DataEngineUtil;
import com.jiuqi.nvwa.definition.common.NrdbHelper;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;

@ConditionalOnMissingBean(value={FileCalculateService.class})
public class FileSumServiceImpl
implements FileCalculateService {
    private static final Logger logger = LoggerFactory.getLogger(FileSumServiceImpl.class);
    @Autowired
    private IRunTimeViewController iRunTimeViewController;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private IEntityDataService entityDataService;
    @Autowired
    private IEntityViewRunTimeController entityViewRunTimeController;
    @Autowired
    private IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    @Autowired
    private FilePoolService filePoolService;
    @Autowired
    private FileOperationService fileOperationService;
    @Autowired
    private AttachmentFileAreaService attachmentFileAreaService;
    @Autowired
    private DataModelService dataModelService;
    @Autowired
    private IRuntimeDataSchemeService runtimeDataSchemeService;
    @Autowired
    private INvwaDataAccessProvider iNvwaDataAccessProvider;
    @Autowired
    private DataEngineAdapter dataEngineAdapter;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private NrdbHelper nrdbHelper;
    @Autowired(required=false)
    private List<IFileListenerProvider> fileListenerProviders;
    protected static final String COVER_UPLOAD = "coverUpload";
    protected static final String SKIP_UPLOAD = "skipUpload";
    protected static final String RENAME_UPLOAD = "renameUpload";

    public List<FileSumInfo> sumFileGroup(FileSumContext fileSumContext) {
        List<FileSumInfo> results = new ArrayList<FileSumInfo>();
        List fileSumInfos = fileSumContext.getFileSumInfos();
        if (fileSumInfos.isEmpty()) {
            return results;
        }
        List fieldAndGroupKeyInfos = ((FileSumInfo)fileSumInfos.get(0)).getFieldAndGroupKeyInfos();
        if (fieldAndGroupKeyInfos.isEmpty()) {
            return results;
        }
        Set<String> fieldKeySet = ((FieldAndGroupKeyInfo)fieldAndGroupKeyInfos.get(0)).getFieldGroupMap().keySet();
        if (fieldKeySet.isEmpty()) {
            return results;
        }
        String taskKey = fileSumContext.getTaskKey();
        TaskDefine taskDefine = this.iRunTimeViewController.queryTaskDefine(taskKey);
        IEntityDefine entityDefine = this.entityMetaService.queryEntity(taskDefine.getDw());
        IEntityQuery entityQuery = this.entityDataService.newEntityQuery();
        EntityViewDefine entityView = this.entityViewRunTimeController.buildEntityView(entityDefine.getId());
        DimensionValueSet masterKeys = new DimensionValueSet();
        masterKeys.setValue("DATATIME", ((FileSumInfo)fileSumInfos.get(0)).getFromDims().getValue("DATATIME"));
        entityQuery.setMasterKeys(masterKeys);
        entityQuery.setEntityView(entityView);
        entityQuery.setAuthorityOperations(AuthorityType.None);
        ExecutorContext executorContext = new ExecutorContext(this.dataDefinitionRuntimeController);
        IEntityTable entityTables = null;
        try {
            entityTables = entityQuery.executeFullBuild((IContext)executorContext);
            GatherDataTable gatherDataTable = fileSumContext.getGatherDataTable();
            results = gatherDataTable.getGatherTableDefine().isFixed() ? this.sumFixFile(fileSumContext, taskDefine, entityDefine, entityTables) : this.sumFloatFile(fileSumContext, fieldKeySet, taskDefine, entityDefine, entityTables);
        }
        catch (Exception e) {
            logger.error(PromptConsts.causeIfError(e.getMessage()), e);
        }
        return results;
    }

    private List<FileSumInfo> sumFixFile(FileSumContext fileSumContext, TaskDefine taskDefine, IEntityDefine entityDefine, IEntityTable entityTables) {
        ArrayList<FileSumInfo> results = new ArrayList<FileSumInfo>();
        HashMap<DimensionCombination, List> fromAndToDws = new HashMap<DimensionCombination, List>();
        List fileSumInfos = fileSumContext.getFileSumInfos();
        for (FileSumInfo fileSumInfo : fileSumInfos) {
            DimensionValueSet fromDims = fileSumInfo.getFromDims();
            String fromDW = (String)fromDims.getValue(entityDefine.getDimensionName());
            IEntityRow fromEntityRow = entityTables.findByEntityKey(fromDW);
            String toDw = entityTables.findByEntityKey(fromEntityRow.getParentEntityKey()).getEntityKeyData();
            DimensionValueSet dimensionValueSet = new DimensionValueSet(fromDims);
            dimensionValueSet.setValue(entityDefine.getDimensionName(), (Object)toDw);
            DimensionCombinationBuilder dimensionCombinationBuilder = new DimensionCombinationBuilder(dimensionValueSet);
            DimensionCombination toDim = dimensionCombinationBuilder.getCombination();
            fromAndToDws.computeIfAbsent(toDim, k -> new ArrayList()).add(fileSumInfo);
        }
        for (Map.Entry entry : fromAndToDws.entrySet()) {
            ProcessingFileParam processingFileParam = new ProcessingFileParam();
            processingFileParam.setDataSchemeKey(taskDefine.getDataScheme());
            processingFileParam.setTaskKey(taskDefine.getKey());
            processingFileParam.setFormSchemeKey(fileSumContext.getFormSchemeKey());
            processingFileParam.setTargetDim((DimensionCombination)entry.getKey());
            processingFileParam.setFileSumInfos((List)entry.getValue());
            List<FileSumInfo> res = this.processingFile(processingFileParam);
            if (null == res) continue;
            results.addAll(res);
        }
        return results;
    }

    protected List<FileSumInfo> processingFile(ProcessingFileParam param) {
        return null;
    }

    protected List<FileInfo> getFileInfoByGroup(String dataSchemeKey, String taskKey, String groupKey) {
        CommonParamsDTO params = new CommonParamsDTO();
        params.setDataSchemeKey(dataSchemeKey);
        params.setTaskKey(taskKey);
        return this.filePoolService.getFileInfoByGroup(groupKey, params);
    }

    protected byte[] downloadFile(String fileKey, String dataSchemeKey, String taskKey) {
        CommonParamsDTO params = new CommonParamsDTO();
        params.setDataSchemeKey(dataSchemeKey);
        params.setTaskKey(taskKey);
        return this.fileOperationService.downloadFile(fileKey, params);
    }

    protected void downloadFile(String fileKey, OutputStream outputStream, String dataSchemeKey, String taskKey) {
        CommonParamsDTO params = new CommonParamsDTO();
        params.setDataSchemeKey(dataSchemeKey);
        params.setTaskKey(taskKey);
        this.fileOperationService.downloadFile(fileKey, outputStream, params);
    }

    protected void deleteFile(ProcessingFileParam param) {
        Set fieldKeys = ((FieldAndGroupKeyInfo)param.getFileSumInfos().get(0).getFieldAndGroupKeyInfos().get(0)).getFieldGroupMap().keySet();
        this.batchMarkDeletion(param.getDataSchemeKey(), param.getTaskKey(), param.getFormSchemeKey(), param.getTargetDim(), new ArrayList<String>(fieldKeys));
    }

    protected Map<String, String> uploadFile(FileSumUploadParam fileSumUploadParam) {
        HashMap<String, String> fieldKeyAndGroupKeyMap = new HashMap<String, String>();
        TaskDefine taskDefine = this.iRunTimeViewController.queryTaskDefine(fileSumUploadParam.getTaskKey());
        FileBucketNameParam param = new FileBucketNameParam(taskDefine.getDataScheme());
        ArrayList<DimensionCombination> toDims = new ArrayList<DimensionCombination>();
        ArrayList<String> fieldKeys = new ArrayList<String>();
        ArrayList<String> toGroupKeys = new ArrayList<String>();
        ArrayList<List<String>> fileKeysLists = new ArrayList<List<String>>();
        boolean openFilepool = this.filePoolService.isOpenFilepool();
        if (openFilepool) {
            this.openFilepoolUploadFile(fileSumUploadParam, fieldKeyAndGroupKeyMap, taskDefine, param, toDims, fieldKeys, toGroupKeys, fileKeysLists);
        } else {
            this.closeFilepoolUploadFile(fileSumUploadParam, param, toDims, fieldKeys, toGroupKeys, fileKeysLists);
        }
        if (!fieldKeys.isEmpty()) {
            this.batchInsertFileRelTable(taskDefine, fileSumUploadParam.getFormSchemeKey(), toDims, fieldKeys, toGroupKeys, fileKeysLists);
        }
        return fieldKeyAndGroupKeyMap;
    }

    private void closeFilepoolUploadFile(FileSumUploadParam fileSumUploadParam, FileBucketNameParam param, List<DimensionCombination> toDims, List<String> fieldKeys, List<String> toGroupKeys, List<List<String>> fileKeysLists) {
        Map<String, List<FileSumUploadInfo>> fileSumUploadInfoMap = fileSumUploadParam.getFileSumUploadInfos();
        for (Map.Entry<String, List<FileSumUploadInfo>> fieldFileSumUploadInfos : fileSumUploadInfoMap.entrySet()) {
            toDims.add(fileSumUploadParam.getTargetDim());
            fieldKeys.add(fieldFileSumUploadInfos.getKey());
            String groupKey = UUID.randomUUID().toString();
            toGroupKeys.add(groupKey);
            ArrayList<String> fileKeys = new ArrayList<String>();
            List<FileSumUploadInfo> fileSumUploadInfos = fieldFileSumUploadInfos.getValue();
            for (FileSumUploadInfo fileSumUploadInfo : fileSumUploadInfos) {
                HashMap<String, String> expandInfo = new HashMap<String, String>();
                expandInfo.put("secretlevel", fileSumUploadInfo.getFileSecret());
                expandInfo.put("fileGroupKey", groupKey);
                expandInfo.put("category", fileSumUploadInfo.getCategory());
                FileInfo fileInfo = this.attachmentFileAreaService.upload(param, fileSumUploadInfo.getName(), fileSumUploadInfo.getFile(), expandInfo);
                fileKeys.add(fileInfo.getKey());
            }
            fileKeysLists.add(fileKeys);
        }
    }

    private void openFilepoolUploadFile(FileSumUploadParam fileSumUploadParam, Map<String, String> fieldKeyAndGroupKeyMap, TaskDefine taskDefine, FileBucketNameParam param, List<DimensionCombination> toDims, List<String> fieldKeys, List<String> toGroupKeys, List<List<String>> fileKeysLists) {
        IEntityDefine entityDefine = this.entityMetaService.queryEntity(taskDefine.getDw());
        Map<String, List<FileSumUploadInfo>> fileSumUploadInfoMap = fileSumUploadParam.getFileSumUploadInfos();
        for (Map.Entry<String, List<FileSumUploadInfo>> fieldFileSumUploadInfos : fileSumUploadInfoMap.entrySet()) {
            toDims.add(fileSumUploadParam.getTargetDim());
            fieldKeys.add(fieldFileSumUploadInfos.getKey());
            String groupKey = UUID.randomUUID().toString();
            toGroupKeys.add(groupKey);
            ArrayList<String> fileKeys = new ArrayList<String>();
            String filePoolKey = taskDefine.getKey() + fileSumUploadParam.getTargetDim().getValue(entityDefine.getDimensionName());
            FilePoolAllFileContext filePoolAllFileContext = new FilePoolAllFileContext();
            filePoolAllFileContext.setFilepoolKey(filePoolKey);
            List<FilePoolFiles> filePoolFiles = this.filePoolService.getFilePoolFiles(filePoolAllFileContext);
            List<FileSumUploadInfo> fileSumUploadInfos = fieldFileSumUploadInfos.getValue();
            for (FileSumUploadInfo fileSumUploadInfo : fileSumUploadInfos) {
                boolean sameName = false;
                String sameFileKey = "";
                for (FilePoolFiles filePoolFile : filePoolFiles) {
                    if (!filePoolFile.getFileName().equals(fileSumUploadInfo.getName())) continue;
                    sameName = true;
                    sameFileKey = filePoolFile.getFileKey();
                    break;
                }
                if (sameName && SKIP_UPLOAD.equals(fileSumUploadParam.getSameNameProcessMode())) continue;
                if (sameName && COVER_UPLOAD.equals(fileSumUploadParam.getSameNameProcessMode())) {
                    this.coverUploadFile(fileSumUploadParam, taskDefine, param, fileKeys, filePoolKey, filePoolFiles, fileSumUploadInfo, sameFileKey);
                    continue;
                }
                if (sameName) continue;
                HashMap<String, String> expandInfo = new HashMap<String, String>();
                expandInfo.put("secretlevel", fileSumUploadInfo.getFileSecret());
                expandInfo.put("filePool", filePoolKey);
                expandInfo.put("category", fileSumUploadInfo.getCategory());
                FileInfo fileInfo = this.attachmentFileAreaService.upload(param, fileSumUploadInfo.getName(), fileSumUploadInfo.getFile(), expandInfo);
                fileKeys.add(fileInfo.getKey());
            }
            fileKeysLists.add(fileKeys);
            fieldKeyAndGroupKeyMap.put(fieldFileSumUploadInfos.getKey(), groupKey);
        }
    }

    private void coverUploadFile(FileSumUploadParam fileSumUploadParam, TaskDefine taskDefine, FileBucketNameParam param, List<String> fileKeys, String filePoolKey, List<FilePoolFiles> filePoolFiles, FileSumUploadInfo fileSumUploadInfo, String sameFileKey) {
        boolean fileCanDel = this.filePoolService.judgeFileOverwritten(sameFileKey, taskDefine.getKey());
        if (!fileCanDel && RENAME_UPLOAD.equals(fileSumUploadParam.getCoverUploadProcessMode())) {
            String newName = this.rename(filePoolFiles, fileSumUploadInfo.getName() + "(1)", 1);
            HashMap<String, String> expandInfo = new HashMap<String, String>();
            expandInfo.put("secretlevel", fileSumUploadInfo.getFileSecret());
            expandInfo.put("filePool", filePoolKey);
            expandInfo.put("category", fileSumUploadInfo.getCategory());
            FileInfo fileInfo = this.attachmentFileAreaService.upload(param, newName, fileSumUploadInfo.getFile(), expandInfo);
            fileKeys.add(fileInfo.getKey());
        } else if (fileCanDel) {
            this.attachmentFileAreaService.delete(param, sameFileKey);
            HashMap<String, String> expandInfo = new HashMap<String, String>();
            expandInfo.put("secretlevel", fileSumUploadInfo.getFileSecret());
            expandInfo.put("filePool", filePoolKey);
            expandInfo.put("category", fileSumUploadInfo.getCategory());
            FileInfo fileInfo = this.attachmentFileAreaService.uploadByKey(param, fileSumUploadInfo.getName(), sameFileKey, fileSumUploadInfo.getFile(), expandInfo);
            fileKeys.add(fileInfo.getKey());
        }
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

    private List<FileSumInfo> sumFloatFile(FileSumContext fileSumContext, Set<String> fieldKeySet, TaskDefine taskDefine, IEntityDefine entityDefine, IEntityTable entityTables) {
        ArrayList<FileSumInfo> results = new ArrayList<FileSumInfo>();
        ArrayList<String> deleteFieldKeys = new ArrayList<String>();
        ArrayList<DimensionCombination> deleteDims = new ArrayList<DimensionCombination>();
        ArrayList<FieldDefine> fieldDefines = new ArrayList<FieldDefine>();
        try {
            for (String fieldKey : fieldKeySet) {
                FieldDefine fieldDefine = this.iRunTimeViewController.queryFieldDefine(fieldKey);
                fieldDefines.add(fieldDefine);
                if (fieldDefine.getType() != FieldType.FIELD_TYPE_FILE) continue;
                deleteFieldKeys.add(fieldKey);
            }
        }
        catch (Exception e) {
            logger.error(PromptConsts.causeIfError(e.getMessage()), e);
        }
        ArrayList<String> fieldKeys = new ArrayList<String>();
        ArrayList<DimensionCombination> toDims = new ArrayList<DimensionCombination>();
        ArrayList<String> toGroupKeys = new ArrayList<String>();
        ArrayList<List<String>> fileKeysLists = new ArrayList<List<String>>();
        String dataSchemeKey = taskDefine.getDataScheme();
        String taskKey = taskDefine.getKey();
        FileBucketNameParam param = new FileBucketNameParam(dataSchemeKey);
        List fileSumInfos = fileSumContext.getFileSumInfos();
        boolean openFilepool = this.filePoolService.isOpenFilepool();
        if (openFilepool) {
            this.openFilepoolSumFile(entityDefine, entityTables, results, deleteDims, fieldDefines, fieldKeys, toDims, toGroupKeys, fileKeysLists, dataSchemeKey, taskKey, param, fileSumInfos);
        } else {
            this.closeFilepoolSumFile(entityDefine, entityTables, results, deleteDims, fieldDefines, fieldKeys, toDims, toGroupKeys, fileKeysLists, dataSchemeKey, taskKey, param, fileSumInfos);
        }
        if (!fieldKeys.isEmpty()) {
            for (DimensionCombination deleteDim : deleteDims) {
                this.batchMarkDeletion(dataSchemeKey, taskKey, fileSumContext.getFormSchemeKey(), deleteDim, deleteFieldKeys);
            }
            this.batchInsertFileRelTable(taskDefine, fileSumContext.getFormSchemeKey(), toDims, fieldKeys, toGroupKeys, fileKeysLists);
        }
        return results;
    }

    private void closeFilepoolSumFile(IEntityDefine entityDefine, IEntityTable entityTables, List<FileSumInfo> results, List<DimensionCombination> deleteDims, List<FieldDefine> fieldDefines, List<String> fieldKeys, List<DimensionCombination> toDims, List<String> toGroupKeys, List<List<String>> fileKeysLists, String dataSchemeKey, String taskKey, FileBucketNameParam param, List<FileSumInfo> fileSumInfos) {
        CommonParamsDTO params = new CommonParamsDTO();
        params.setDataSchemeKey(dataSchemeKey);
        params.setTaskKey(taskKey);
        for (FileSumInfo fileSumInfo : fileSumInfos) {
            FileSumInfo result = new FileSumInfo();
            ArrayList<FieldAndGroupKeyInfo> fieldAndGroupKeyInfoResults = new ArrayList<FieldAndGroupKeyInfo>();
            DimensionCombination toDim = this.buildToDim(entityDefine, entityTables, deleteDims, fileSumInfo, result);
            for (FieldAndGroupKeyInfo fieldAndGroupKeyInfo : fileSumInfo.getFieldAndGroupKeyInfos()) {
                FieldAndGroupKeyInfo fieldAndGroupKeyInfoResult = new FieldAndGroupKeyInfo();
                fieldAndGroupKeyInfoResult.setBizKey(fieldAndGroupKeyInfo.getBizKey());
                LinkedHashMap<String, String> fieldGroupMapResult = new LinkedHashMap<String, String>();
                LinkedHashMap fieldGroupMap = fieldAndGroupKeyInfo.getFieldGroupMap();
                for (FieldDefine fieldDefine : fieldDefines) {
                    String fromGroupKey = (String)fieldGroupMap.get(fieldDefine.getKey());
                    if (StringUtils.isEmpty((String)fromGroupKey)) {
                        fieldGroupMapResult.put(fieldDefine.getKey(), fromGroupKey);
                        continue;
                    }
                    if (fieldDefine.getType() == FieldType.FIELD_TYPE_FILE) {
                        this.fileCopy(fieldKeys, toDims, toGroupKeys, fileKeysLists, param, params, toDim, fieldGroupMapResult, fieldDefine, fromGroupKey);
                        continue;
                    }
                    if (fieldDefine.getType() != FieldType.FIELD_TYPE_PICTURE) continue;
                    this.pictureCopy(fieldGroupMapResult, fieldDefine, fromGroupKey, param);
                }
                fieldAndGroupKeyInfoResult.setFieldGroupMap(fieldGroupMapResult);
                fieldAndGroupKeyInfoResults.add(fieldAndGroupKeyInfoResult);
            }
            result.setFieldAndGroupKeyInfos(fieldAndGroupKeyInfoResults);
            results.add(result);
        }
    }

    private DimensionCombination buildToDim(IEntityDefine entityDefine, IEntityTable entityTables, List<DimensionCombination> deleteDims, FileSumInfo fileSumInfo, FileSumInfo result) {
        DimensionValueSet fromDims = fileSumInfo.getFromDims();
        result.setFromDims(fromDims);
        String fromDW = (String)fromDims.getValue(entityDefine.getDimensionName());
        IEntityRow fromEntityRow = entityTables.findByEntityKey(fromDW);
        String toDw = entityTables.findByEntityKey(fromEntityRow.getParentEntityKey()).getEntityKeyData();
        DimensionValueSet dimensionValueSet = new DimensionValueSet(fromDims);
        dimensionValueSet.setValue(entityDefine.getDimensionName(), (Object)toDw);
        DimensionCombinationBuilder dimensionCombinationBuilder = new DimensionCombinationBuilder(dimensionValueSet);
        DimensionCombination toDim = dimensionCombinationBuilder.getCombination();
        boolean canAdd = true;
        for (DimensionCombination deleteDim : deleteDims) {
            if (0 != deleteDim.toDimensionValueSet().compareTo(toDim.toDimensionValueSet())) continue;
            canAdd = false;
            break;
        }
        if (canAdd) {
            deleteDims.add(toDim);
        }
        return toDim;
    }

    private void fileCopy(List<String> fieldKeys, List<DimensionCombination> toDims, List<String> toGroupKeys, List<List<String>> fileKeysLists, FileBucketNameParam param, CommonParamsDTO params, DimensionCombination toDim, LinkedHashMap<String, String> fieldGroupMapResult, FieldDefine fieldDefine, String fromGroupKey) {
        List<FileInfo> fileInfos = this.filePoolService.getFileInfoByGroup(fromGroupKey, false, params);
        if (fileInfos.isEmpty()) {
            fieldGroupMapResult.put(fieldDefine.getKey(), fromGroupKey);
            return;
        }
        List<String> fileKeys = fileInfos.stream().map(FileInfo::getKey).collect(Collectors.toList());
        List<String> copyFileKeys = this.attachmentFileAreaService.batchFileCopy(param, fileKeys);
        String toGroupKey = UUID.randomUUID().toString();
        fieldGroupMapResult.put(fieldDefine.getKey(), toGroupKey);
        HashMap<String, String> updateInfo = new HashMap<String, String>();
        updateInfo.put("fileGroupKey", toGroupKey);
        this.attachmentFileAreaService.batchupdate(param, copyFileKeys, updateInfo);
        fieldKeys.add(fieldDefine.getKey());
        toDims.add(toDim);
        toGroupKeys.add(toGroupKey);
        fileKeysLists.add(copyFileKeys);
    }

    private void openFilepoolSumFile(IEntityDefine entityDefine, IEntityTable entityTables, List<FileSumInfo> results, List<DimensionCombination> deleteDims, List<FieldDefine> fieldDefines, List<String> fieldKeys, List<DimensionCombination> toDims, List<String> toGroupKeys, List<List<String>> fileKeysLists, String dataSchemeKey, String taskKey, FileBucketNameParam param, List<FileSumInfo> fileSumInfos) {
        for (FileSumInfo fileSumInfo : fileSumInfos) {
            FileSumInfo result = new FileSumInfo();
            ArrayList<FieldAndGroupKeyInfo> fieldAndGroupKeyInfoResults = new ArrayList<FieldAndGroupKeyInfo>();
            DimensionValueSet fromDims = fileSumInfo.getFromDims();
            result.setFromDims(fromDims);
            String fromDW = (String)fromDims.getValue(entityDefine.getDimensionName());
            IEntityRow fromEntityRow = entityTables.findByEntityKey(fromDW);
            String toDw = entityTables.findByEntityKey(fromEntityRow.getParentEntityKey()).getEntityKeyData();
            DimensionValueSet dimensionValueSet = new DimensionValueSet(fromDims);
            dimensionValueSet.setValue(entityDefine.getDimensionName(), (Object)toDw);
            DimensionCombinationBuilder dimensionCombinationBuilder = new DimensionCombinationBuilder(dimensionValueSet);
            DimensionCombination toDim = dimensionCombinationBuilder.getCombination();
            boolean canAdd = true;
            for (DimensionCombination deleteDim : deleteDims) {
                if (0 != deleteDim.toDimensionValueSet().compareTo(toDim.toDimensionValueSet())) continue;
                canAdd = false;
                break;
            }
            if (canAdd) {
                deleteDims.add(toDim);
            }
            FilePoolAllFileContext filePoolAllFileContext = new FilePoolAllFileContext();
            filePoolAllFileContext.setTaskKey(taskKey);
            filePoolAllFileContext.setDimensionCombination(toDim);
            List<FilePoolFiles> toFilePoolFiles = this.filePoolService.getFilePoolFiles(filePoolAllFileContext, false);
            this.copyFile(fieldDefines, fieldKeys, toDims, toGroupKeys, fileKeysLists, dataSchemeKey, taskKey, param, fileSumInfo, fieldAndGroupKeyInfoResults, toDw, toDim, toFilePoolFiles);
            result.setFieldAndGroupKeyInfos(fieldAndGroupKeyInfoResults);
            results.add(result);
        }
    }

    private void copyFile(List<FieldDefine> fieldDefines, List<String> fieldKeys, List<DimensionCombination> toDims, List<String> toGroupKeys, List<List<String>> fileKeysLists, String dataSchemeKey, String taskKey, FileBucketNameParam param, FileSumInfo fileSumInfo, List<FieldAndGroupKeyInfo> fieldAndGroupKeyInfoResults, String toDw, DimensionCombination toDim, List<FilePoolFiles> toFilePoolFiles) {
        for (FieldAndGroupKeyInfo fieldAndGroupKeyInfo : fileSumInfo.getFieldAndGroupKeyInfos()) {
            FieldAndGroupKeyInfo fieldAndGroupKeyInfoResult = new FieldAndGroupKeyInfo();
            fieldAndGroupKeyInfoResult.setBizKey(fieldAndGroupKeyInfo.getBizKey());
            LinkedHashMap<String, String> fieldGroupMapResult = new LinkedHashMap<String, String>();
            LinkedHashMap fieldGroupMap = fieldAndGroupKeyInfo.getFieldGroupMap();
            for (FieldDefine fieldDefine : fieldDefines) {
                String fromGroupKey = (String)fieldGroupMap.get(fieldDefine.getKey());
                if (StringUtils.isEmpty((String)fromGroupKey)) {
                    fieldGroupMapResult.put(fieldDefine.getKey(), fromGroupKey);
                    continue;
                }
                if (fieldDefine.getType() == FieldType.FIELD_TYPE_FILE) {
                    this.fileCopy(fieldKeys, toDims, toGroupKeys, fileKeysLists, dataSchemeKey, taskKey, param, toDw, toDim, toFilePoolFiles, fieldGroupMapResult, fieldDefine, fromGroupKey);
                    continue;
                }
                if (fieldDefine.getType() != FieldType.FIELD_TYPE_PICTURE) continue;
                this.pictureCopy(fieldGroupMapResult, fieldDefine, fromGroupKey, param);
            }
            fieldAndGroupKeyInfoResult.setFieldGroupMap(fieldGroupMapResult);
            fieldAndGroupKeyInfoResults.add(fieldAndGroupKeyInfoResult);
        }
    }

    private void fileCopy(List<String> fieldKeys, List<DimensionCombination> toDims, List<String> toGroupKeys, List<List<String>> fileKeysLists, String dataSchemeKey, String taskKey, FileBucketNameParam param, String toDw, DimensionCombination toDim, List<FilePoolFiles> toFilePoolFiles, LinkedHashMap<String, String> fieldGroupMapResult, FieldDefine fieldDefine, String fromGroupKey) {
        String filePoolKey;
        List<FileCopyInfo> fromFileInfos = this.getFileInfoByGroup(fromGroupKey, dataSchemeKey, param);
        if (fromFileInfos.isEmpty()) {
            fieldGroupMapResult.put(fieldDefine.getKey(), fromGroupKey);
            return;
        }
        ArrayList<String> insertFileKeys = new ArrayList<String>();
        ArrayList<FileUploadInfo> fileUploadInfos = new ArrayList<FileUploadInfo>();
        ArrayList<String> updateFilepoolFileKey = new ArrayList<String>();
        for (FileCopyInfo fromFileInfo : fromFileInfos) {
            boolean sameName = this.judgeSameName(taskKey, param, toFilePoolFiles, toDw, insertFileKeys, fileUploadInfos, updateFilepoolFileKey, fromFileInfo);
            if (sameName) continue;
            List<String> copyFileKeys = this.attachmentFileAreaService.batchFileCopy(param, Arrays.asList(fromFileInfo.getKey()));
            insertFileKeys.add(copyFileKeys.get(0));
            updateFilepoolFileKey.add(copyFileKeys.get(0));
        }
        if (!updateFilepoolFileKey.isEmpty()) {
            filePoolKey = taskKey + toDw;
            HashMap<String, String> updateInfo = new HashMap<String, String>();
            updateInfo.put("filePool", filePoolKey);
            this.attachmentFileAreaService.batchupdate(param, updateFilepoolFileKey, updateInfo);
        }
        if (!fileUploadInfos.isEmpty()) {
            filePoolKey = taskKey + toDw;
            List<FileInfo> fileInfos = this.uploadFilesToFilepool(filePoolKey, fileUploadInfos, param);
            insertFileKeys.addAll(fileInfos.stream().map(FileInfo::getKey).collect(Collectors.toList()));
        }
        fieldKeys.add(fieldDefine.getKey());
        toDims.add(toDim);
        String toGroupKey = UUID.randomUUID().toString();
        fieldGroupMapResult.put(fieldDefine.getKey(), toGroupKey);
        toGroupKeys.add(toGroupKey);
        fileKeysLists.add(insertFileKeys);
    }

    private boolean judgeSameName(String taskKey, FileBucketNameParam param, List<FilePoolFiles> toFilePoolFiles, String toDw, List<String> insertFileKeys, List<FileUploadInfo> fileUploadInfos, List<String> updateFilepoolFileKey, FileCopyInfo fromFileInfo) {
        boolean sameName = false;
        for (FilePoolFiles toFilePoolFile : toFilePoolFiles) {
            if (toFilePoolFile.getFileName().equals(fromFileInfo.getName()) && (toFilePoolFile.getSize() != fromFileInfo.getSize() || !toFilePoolFile.getMd5().equals(fromFileInfo.getMd5()))) {
                sameName = true;
                if (!this.filePoolService.judgeFileOverwritten(toFilePoolFile.getFileKey(), taskKey)) {
                    List<String> copyFileKeys = this.attachmentFileAreaService.batchFileCopy(param, Arrays.asList(fromFileInfo.getKey()));
                    insertFileKeys.add(copyFileKeys.get(0));
                    updateFilepoolFileKey.add(copyFileKeys.get(0));
                    String newFileName = this.rename(taskKey, toFilePoolFiles, fromFileInfo.getName() + "(1)", fromFileInfo.getSize(), fromFileInfo.getMd5(), 1);
                    try {
                        HashMap<String, String> updateInfo = new HashMap<String, String>();
                        updateInfo.put("name", newFileName);
                        updateInfo.put("filePool", taskKey + toDw);
                        this.attachmentFileAreaService.update(param, copyFileKeys.get(0), updateInfo);
                    }
                    catch (ObjectStorageException e) {
                        logger.error(PromptConsts.causeIfError(e.getMessage()), e);
                    }
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
        return sameName;
    }

    private void pictureCopy(LinkedHashMap<String, String> fieldGroupMapResult, FieldDefine fieldDefine, String fromGroupKey, FileBucketNameParam param) {
        List<FileInfo> fileInfos = this.attachmentFileAreaService.getFileInfoByProp(param, "fileGroupKey", fromGroupKey, false);
        if (fileInfos.isEmpty()) {
            fieldGroupMapResult.put(fieldDefine.getKey(), fromGroupKey);
            return;
        }
        List<String> fileKeys = fileInfos.stream().map(FileInfo::getKey).collect(Collectors.toList());
        List<String> copyFileKeys = this.attachmentFileAreaService.batchFileCopy(param, fileKeys);
        String toGroupKey = UUID.randomUUID().toString();
        fieldGroupMapResult.put(fieldDefine.getKey(), toGroupKey);
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

    public List<FileCopyInfo> getFileInfoByGroup(String groupKey, String dataSchemeKey, FileBucketNameParam param) {
        DataScheme dataScheme = this.runtimeDataSchemeService.getDataScheme(dataSchemeKey);
        String tableName = this.fileOperationService.getCurrentSplitTableName(dataScheme);
        TableModelDefine filePoolTable = this.dataModelService.getTableModelDefineByName(tableName);
        if (null == filePoolTable) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a\u9644\u4ef6\u5173\u8054\u8868\u672a\u521b\u5efa\uff0c\u8bf7\u91cd\u65b0\u53d1\u5e03\u4efb\u52a1\uff01");
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

    private void batchMarkDeletion(String dataSchemeKey, String taskKey, String formSchemeKey, DimensionCombination dimensionCombination, List<String> fieldKeys) {
        ArrayList<IFileListener> fileListeners = new ArrayList<IFileListener>();
        if (null != this.fileListenerProviders) {
            for (IFileListenerProvider fileListenerProvider : this.fileListenerProviders) {
                IFileListener fileListener = fileListenerProvider.getFileListener(null);
                fileListeners.add(fileListener);
            }
        }
        for (IFileListener fileListener : fileListeners) {
            FileBatchDelEvent fileBatchDelEvent = new FileBatchDelEvent(dataSchemeKey, taskKey, formSchemeKey, dimensionCombination, fieldKeys, null);
            fileListener.beforeBatchFileDelete(fileBatchDelEvent);
        }
        boolean enableNrdb = this.nrdbHelper.isEnableNrdb();
        if (enableNrdb) {
            this.batchMark(dataSchemeKey, dimensionCombination, fieldKeys);
        } else {
            this.batchMarkBySQL(dataSchemeKey, dimensionCombination, fieldKeys);
        }
        for (IFileListener fileListener : fileListeners) {
            FileBatchDelEvent fileBatchDelEvent = new FileBatchDelEvent(dataSchemeKey, taskKey, formSchemeKey, dimensionCombination, fieldKeys, null);
            fileListener.afterBatchFileDelete(fileBatchDelEvent);
        }
    }

    private void batchMark(String dataSchemeKey, DimensionCombination dimensionCombination, List<String> fieldKeys) {
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
        int index = 0;
        for (ColumnModelDefine filePoolField : filePoolFields) {
            String dimensionName;
            queryModel.getColumns().add(new NvwaQueryColumn(filePoolField));
            if (filePoolField.getCode().equals("FIELD_KEY")) {
                queryModel.getColumnFilters().put(filePoolField, fieldKeys);
                continue;
            }
            if (filePoolField.getCode().equals("ISDELETE")) {
                index = filePoolFields.indexOf(filePoolField);
                continue;
            }
            if (filePoolField.getCode().equals("FIELD_KEY") || filePoolField.getCode().equals("ID") || filePoolField.getCode().equals("GROUPKEY") || filePoolField.getCode().equals("FILEKEY") || filePoolField.getCode().equals("ISDELETE") || null == dimension.getValue(dimensionName = dimensionChanger.getDimensionName(filePoolField))) continue;
            queryModel.getColumnFilters().put(filePoolField, dimension.getValue(dimensionName));
        }
        INvwaUpdatableDataAccess updatableDataAccess = this.iNvwaDataAccessProvider.createUpdatableDataAccess(queryModel);
        try {
            INvwaUpdatableDataSet iNvwaDataRows = updatableDataAccess.executeQueryForUpdate(context);
            for (int i = 0; i < iNvwaDataRows.size(); ++i) {
                INvwaDataRow row = iNvwaDataRows.getRow(i);
                row.setValue(index, (Object)"1");
            }
            iNvwaDataRows.commitChanges(context);
        }
        catch (Exception e) {
            logger.error(PromptConsts.causeIfError(e.getMessage()), e);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void batchMarkBySQL(String dataSchemeKey, DimensionCombination dimensionCombination, List<String> fieldKeys) {
        logger.info("\u6279\u91cf\u6807\u8bb0\u5220\u9664\u5f00\u59cb");
        DataScheme dataScheme = this.runtimeDataSchemeService.getDataScheme(dataSchemeKey);
        String tableName = this.fileOperationService.getCurrentSplitTableName(dataScheme);
        TableModelDefine filePoolTable = this.dataModelService.getTableModelDefineByName(tableName);
        List filePoolFields = this.dataModelService.getColumnModelDefinesByTable(filePoolTable.getID());
        DimensionChanger dimensionChanger = this.dataEngineAdapter.getDimensionChanger(tableName);
        if (null == filePoolTable) {
            throw new NotFoundTableDefineException(new String[]{"\u9644\u4ef6\u5173\u8054\u8868\u672a\u521b\u5efa\uff0c\u8bf7\u91cd\u65b0\u53d1\u5e03\u6570\u636e\u65b9\u6848\uff01"});
        }
        LinkedHashSet<ColumnModelDefine> columns = new LinkedHashSet<ColumnModelDefine>();
        ArrayList<List<String>> conditionalValueList = new ArrayList<List<String>>();
        for (String fieldKey : fieldKeys) {
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
            this.executeSql(conn, tableName, columns, conditionalValueList);
            logger.info("\u6279\u91cf\u6807\u8bb0\u5220\u9664\u5b8c\u6210");
        }
        catch (Exception e) {
            logger.error(PromptConsts.causeIfError(e.getMessage()), e);
        }
        finally {
            if (null != conn) {
                DataSourceUtils.releaseConnection((Connection)conn, (DataSource)this.jdbcTemplate.getDataSource());
            }
        }
    }

    private void executeSql(Connection conn, String tableName, LinkedHashSet<ColumnModelDefine> columns, List<List<String>> conditionalValueList) throws SQLException {
        StringBuilder sql = new StringBuilder();
        sql.append("update ").append(tableName).append(" t set t.").append("ISDELETE").append("='1'");
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

    private void batchInsertFileRelTable(TaskDefine taskDefine, String formSchemeKey, List<DimensionCombination> dims, List<String> fieldKeys, List<String> groupKeys, List<List<String>> fileKeysLists) {
        DataAccessContext context = new DataAccessContext(this.dataModelService);
        NvwaQueryModel queryModel = new NvwaQueryModel();
        DataScheme dataScheme = this.runtimeDataSchemeService.getDataScheme(taskDefine.getDataScheme());
        String tableName = this.fileOperationService.getCurrentSplitTableName(dataScheme);
        TableModelDefine filePoolTable = this.dataModelService.getTableModelDefineByName(tableName);
        if (null == filePoolTable) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a\u9644\u4ef6\u5173\u8054\u8868\u672a\u521b\u5efa\uff0c\u8bf7\u91cd\u65b0\u53d1\u5e03\u4efb\u52a1\uff01");
            return;
        }
        ArrayList<IFileListener> fileListeners = new ArrayList<IFileListener>();
        if (null != this.fileListenerProviders) {
            for (IFileListenerProvider iFileListenerProvider : this.fileListenerProviders) {
                IFileListener fileListener = iFileListenerProvider.getFileListener(null);
                if (null == fileListener) continue;
                fileListeners.add(fileListener);
            }
        }
        List filePoolFields = this.dataModelService.getColumnModelDefinesByTable(filePoolTable.getID());
        for (ColumnModelDefine filePoolField : filePoolFields) {
            queryModel.getColumns().add(new NvwaQueryColumn(filePoolField));
        }
        DimensionChanger dimensionChanger = this.dataEngineAdapter.getDimensionChanger(filePoolTable.getName());
        INvwaUpdatableDataAccess updatableDataAccess = this.iNvwaDataAccessProvider.createUpdatableDataAccess(queryModel);
        INvwaDataUpdator iNvwaDataUpdator = null;
        List columns = queryModel.getColumns();
        try {
            int count = 0;
            iNvwaDataUpdator = updatableDataAccess.openForUpdate(context);
            for (int j = 0; j < dims.size(); ++j) {
                DimensionCombination dimensionCombination = dims.get(j);
                DimensionValueSet dimensionValueSet = dimensionCombination.toDimensionValueSet();
                String fieldKey = fieldKeys.get(j);
                String groupKey = groupKeys.get(j);
                List<String> fileKeys = fileKeysLists.get(j);
                for (String fileKey : fileKeys) {
                    this.buildInsertData(dimensionChanger, iNvwaDataUpdator, columns, dimensionValueSet, fieldKey, groupKey, fileKey);
                    if (++count < 500) continue;
                    iNvwaDataUpdator.commitChanges(context);
                    count = 0;
                }
                for (IFileListener fileListener : fileListeners) {
                    FileUploadEvent fileUploadEvent = new FileUploadEvent(dataScheme.getKey(), taskDefine.getKey(), formSchemeKey, dimensionCombination, fieldKey, groupKey, fileKeys);
                    fileListener.afterFileUpload(fileUploadEvent);
                }
            }
            if (count > 0) {
                iNvwaDataUpdator.commitChanges(context);
            }
        }
        catch (Exception e) {
            logger.error(PromptConsts.causeIfError(e.getMessage()), e);
        }
    }

    private void buildInsertData(DimensionChanger dimensionChanger, INvwaDataUpdator iNvwaDataUpdator, List<NvwaQueryColumn> columns, DimensionValueSet dimensionValueSet, String fieldKey, String groupKey, String fileKey) throws IncorrectQueryException {
        INvwaDataRow iNvwaDataRow = iNvwaDataUpdator.addInsertRow();
        String id = UUID.randomUUID().toString();
        block14: for (int i = 0; i < columns.size(); ++i) {
            switch (columns.get(i).getColumnModel().getCode()) {
                case "ID": {
                    iNvwaDataRow.setValue(i, (Object)id);
                    continue block14;
                }
                case "GROUPKEY": {
                    iNvwaDataRow.setValue(i, (Object)groupKey);
                    continue block14;
                }
                case "FILEKEY": {
                    iNvwaDataRow.setValue(i, (Object)fileKey);
                    continue block14;
                }
                case "FIELD_KEY": {
                    iNvwaDataRow.setValue(i, (Object)fieldKey);
                    continue block14;
                }
                case "ISDELETE": {
                    iNvwaDataRow.setValue(i, (Object)"0");
                    continue block14;
                }
                default: {
                    String dimensionName = dimensionChanger.getDimensionName(columns.get(i).getColumnModel());
                    iNvwaDataRow.setValue(i, dimensionValueSet.getValue(dimensionName));
                }
            }
        }
    }
}

