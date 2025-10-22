/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.DataRow
 *  com.jiuqi.bi.dataset.MemoryDataSet
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.definition.common.FieldType
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.nr.common.exception.NotFoundTableDefineException
 *  com.jiuqi.nr.common.util.DataEngineAdapter
 *  com.jiuqi.nr.common.util.DimensionChanger
 *  com.jiuqi.nr.datascheme.api.DataScheme
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationBuilder
 *  com.jiuqi.nr.dataservice.core.log.DataServiceLoggerFactory
 *  com.jiuqi.nr.dataservice.core.log.LogDimensionCollection
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
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
 *  com.jiuqi.nvwa.dataengine.common.DataAccessContext
 *  com.jiuqi.nvwa.dataengine.model.NvwaQueryColumn
 *  com.jiuqi.nvwa.dataengine.model.NvwaQueryModel
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package com.jiuqi.nr.attachment.service.impl;

import com.jiuqi.bi.dataset.DataRow;
import com.jiuqi.bi.dataset.MemoryDataSet;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.definition.common.FieldType;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.nr.attachment.constant.PromptConsts;
import com.jiuqi.nr.attachment.input.CommonParamsDTO;
import com.jiuqi.nr.attachment.input.FilePoolAllFileContext;
import com.jiuqi.nr.attachment.input.FileRelQueryParam;
import com.jiuqi.nr.attachment.input.FileRelateGroupInfo;
import com.jiuqi.nr.attachment.input.FileUploadByFileKeyCtx;
import com.jiuqi.nr.attachment.input.FileUploadByFileKeyInfo;
import com.jiuqi.nr.attachment.input.FileUploadCtx;
import com.jiuqi.nr.attachment.input.FileUploadMsg;
import com.jiuqi.nr.attachment.listener.IFileListener;
import com.jiuqi.nr.attachment.listener.IFileListenerProvider;
import com.jiuqi.nr.attachment.listener.param.FileDelEvent;
import com.jiuqi.nr.attachment.listener.param.FileUploadEvent;
import com.jiuqi.nr.attachment.message.AttachmentRelInfo;
import com.jiuqi.nr.attachment.message.FileInfo;
import com.jiuqi.nr.attachment.output.FilePoolFiles;
import com.jiuqi.nr.attachment.output.FileRelInfo;
import com.jiuqi.nr.attachment.output.ResultInfo;
import com.jiuqi.nr.attachment.provider.param.FileBucketNameParam;
import com.jiuqi.nr.attachment.service.FileOperationService;
import com.jiuqi.nr.attachment.service.FilePoolService;
import com.jiuqi.nr.attachment.service.FileService;
import com.jiuqi.nr.attachment.tools.AttachmentFileAreaService;
import com.jiuqi.nr.attachment.utils.LogHellperUtil;
import com.jiuqi.nr.common.exception.NotFoundTableDefineException;
import com.jiuqi.nr.common.util.DataEngineAdapter;
import com.jiuqi.nr.common.util.DimensionChanger;
import com.jiuqi.nr.datascheme.api.DataScheme;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationBuilder;
import com.jiuqi.nr.dataservice.core.log.DataServiceLoggerFactory;
import com.jiuqi.nr.dataservice.core.log.LogDimensionCollection;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
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
import com.jiuqi.nvwa.dataengine.common.DataAccessContext;
import com.jiuqi.nvwa.dataengine.model.NvwaQueryColumn;
import com.jiuqi.nvwa.dataengine.model.NvwaQueryModel;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FileServiceImpl
implements FileService {
    private static final Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private DataServiceLoggerFactory dataServiceLoggerFactory;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private PeriodEngineService periodEngineService;
    @Autowired
    private FilePoolService filePoolService;
    @Autowired
    private CheckSensitiveWordService checkSensitiveWordService;
    @Autowired
    private CheckUploadFileService checkUploadFileService;
    @Autowired
    private AttachmentFileAreaService attachmentFileAreaService;
    @Autowired
    private DataModelService dataModelService;
    @Autowired
    private DataEngineAdapter dataEngineAdapter;
    @Autowired
    private IRuntimeDataSchemeService runtimeDataSchemeService;
    @Autowired
    private FileOperationService fileOperationService;
    @Autowired
    private INvwaDataAccessProvider nvwaDataAccessProvider;
    @Autowired(required=false)
    private List<IFileListenerProvider> fileListenerProviders;
    private static final String MOUDLE = "\u9644\u4ef6\u670d\u52a1";

    @Override
    public ResultInfo uploadFiles(FileUploadCtx context) {
        ResultInfo judgeGroupKeySameNameRes;
        if (null == context || StringUtils.isEmpty((String)context.getFormSchemeKey()) || null == context.getDimensionCombination() || StringUtils.isEmpty((String)context.getFieldKey()) || null == context.getFileUploadMsgs() || context.getFileUploadMsgs().isEmpty()) {
            throw new IllegalArgumentException("\u53c2\u6570\u9519\u8bef");
        }
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(context.getFormSchemeKey());
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(formScheme.getTaskKey());
        FileBucketNameParam param = new FileBucketNameParam(taskDefine.getDataScheme());
        DimensionValueSet dimension = context.getDimensionCombination().toDimensionValueSet();
        String dwDimensionName = this.entityMetaService.getDimensionName(taskDefine.getDw());
        IPeriodEntityAdapter periodAdapter = this.periodEngineService.getPeriodAdapter();
        String periodDimensionName = periodAdapter.getPeriodDimensionName();
        String targetKey = String.valueOf(dimension.getValue(dwDimensionName));
        String periodCode = String.valueOf(dimension.getValue(periodDimensionName));
        LogDimensionCollection logDimensionCollection = new LogDimensionCollection();
        logDimensionCollection.setDw(taskDefine.getDw(), new String[]{targetKey});
        logDimensionCollection.setPeriod(taskDefine.getDateTime(), periodCode);
        LogHellperUtil logHellperUtil = new LogHellperUtil(this.dataServiceLoggerFactory, MOUDLE);
        ResultInfo fieldCheck = this.fieldCheck(context.getFieldKey(), taskDefine, logHellperUtil, logDimensionCollection);
        if (null != fieldCheck) {
            return fieldCheck;
        }
        ArrayList<String> coveredFileKeys = new ArrayList<String>();
        ArrayList<FileUploadMsg> notCoveredFileUploadMsgs = new ArrayList<FileUploadMsg>();
        List<FileUploadMsg> fileUploadMsgs = context.getFileUploadMsgs();
        ResultInfo checkFileTypeRes = this.checkFileTypeAndName(taskDefine, logHellperUtil, logDimensionCollection, coveredFileKeys, notCoveredFileUploadMsgs, fileUploadMsgs);
        if (checkFileTypeRes != null) {
            return checkFileTypeRes;
        }
        String groupKey = context.getGroupKey();
        String filePoolKey = "";
        boolean openFilepool = this.filePoolService.isOpenFilepool();
        if (openFilepool) {
            IEntityDefine entityDefine = this.entityMetaService.queryEntity(taskDefine.getDw());
            filePoolKey = taskDefine.getKey() + dimension.getValue(entityDefine.getDimensionName());
            ResultInfo judgeFilepoolSameNameRes = this.judgeFilepoolSameName(notCoveredFileUploadMsgs, filePoolKey);
            if (null != judgeFilepoolSameNameRes) {
                logHellperUtil.error(taskDefine.getKey(), logDimensionCollection, "\u9644\u4ef6\u4e0a\u4f20\u5931\u8d25", PromptConsts.fileOprateServiceError(judgeFilepoolSameNameRes.getErrorMessage()));
                return judgeFilepoolSameNameRes;
            }
        } else if (StringUtils.isNotEmpty((String)groupKey) && null != (judgeGroupKeySameNameRes = this.judgeGroupKeySameName(notCoveredFileUploadMsgs, groupKey, taskDefine.getDataScheme(), taskDefine.getKey()))) {
            logHellperUtil.error(taskDefine.getKey(), logDimensionCollection, "\u9644\u4ef6\u4e0a\u4f20\u5931\u8d25", PromptConsts.fileOprateServiceError(judgeGroupKeySameNameRes.getErrorMessage()));
            return judgeGroupKeySameNameRes;
        }
        Map<String, AttachmentRelInfo> attachmentRelInfoMap = null;
        if (!coveredFileKeys.isEmpty()) {
            List<String> noExistFileKeys = this.attachmentFileAreaService.existFile(param, coveredFileKeys, 1);
            coveredFileKeys.removeAll(noExistFileKeys);
            if (!coveredFileKeys.isEmpty()) {
                ResultInfo fileCanDelRes = this.fileCanDel(taskDefine, coveredFileKeys);
                if (null != fileCanDelRes) {
                    logHellperUtil.error(taskDefine.getKey(), logDimensionCollection, "\u9644\u4ef6\u4e0a\u4f20\u5931\u8d25", PromptConsts.fileOprateServiceError(fileCanDelRes.getErrorMessage()));
                    return fileCanDelRes;
                }
                attachmentRelInfoMap = this.delFileAndSendListener(taskDefine, coveredFileKeys);
            }
        }
        if (StringUtils.isEmpty((String)groupKey)) {
            groupKey = UUID.randomUUID().toString();
        }
        ArrayList<String> uploadFileKeys = new ArrayList<String>();
        this.uploadFile(param, fileUploadMsgs, groupKey, filePoolKey, openFilepool, uploadFileKeys);
        this.sendOverlayUploadListener(taskDefine, coveredFileKeys, attachmentRelInfoMap);
        this.writeFileRelTable(taskDefine, groupKey, uploadFileKeys, context.getDimensionCombination(), context.getFormSchemeKey(), context.getFieldKey());
        ResultInfo resultInfo = new ResultInfo();
        resultInfo.setSuccess(true);
        resultInfo.setGroupKey(groupKey);
        logHellperUtil.info(taskDefine.getKey(), logDimensionCollection, "\u9644\u4ef6\u4e0a\u4f20\u6210\u529f", "\u5e26fileKey\u4e0a\u4f20\u9644\u4ef6\u6210\u529f");
        return resultInfo;
    }

    private void uploadFile(FileBucketNameParam param, List<FileUploadMsg> fileUploadMsgs, String groupKey, String filePoolKey, boolean openFilepool, List<String> uploadFileKeys) {
        if (openFilepool) {
            for (FileUploadMsg fileUploadMsg : fileUploadMsgs) {
                HashMap<String, String> expandInfo = new HashMap<String, String>();
                expandInfo.put("secretlevel", fileUploadMsg.getFileSecret());
                expandInfo.put("filePool", filePoolKey);
                expandInfo.put("category", fileUploadMsg.getCategory());
                FileInfo fileInfo = null;
                String fileKey = fileUploadMsg.getFileKey();
                fileInfo = fileUploadMsg.isCovered() && StringUtils.isNotEmpty((String)fileKey) ? this.attachmentFileAreaService.uploadByKey(param, fileUploadMsg.getName(), fileKey, fileUploadMsg.getFile(), expandInfo) : this.attachmentFileAreaService.upload(param, fileUploadMsg.getName(), fileUploadMsg.getFile(), expandInfo);
                uploadFileKeys.add(fileInfo.getKey());
            }
        } else {
            for (FileUploadMsg fileUploadMsg : fileUploadMsgs) {
                HashMap<String, String> expandInfo = new HashMap<String, String>();
                expandInfo.put("fileGroupKey", groupKey);
                expandInfo.put("secretlevel", fileUploadMsg.getFileSecret());
                FileInfo fileInfo = null;
                String fileKey = fileUploadMsg.getFileKey();
                fileInfo = fileUploadMsg.isCovered() && StringUtils.isNotEmpty((String)fileKey) ? this.attachmentFileAreaService.uploadByKey(param, fileUploadMsg.getName(), fileUploadMsg.getFileKey(), fileUploadMsg.getFile(), expandInfo) : this.attachmentFileAreaService.upload(param, fileUploadMsg.getName(), fileUploadMsg.getFile(), expandInfo);
                uploadFileKeys.add(fileInfo.getKey());
            }
        }
    }

    @Nullable
    private ResultInfo checkFileTypeAndName(TaskDefine taskDefine, LogHellperUtil logHellperUtil, LogDimensionCollection logDimensionCollection, List<String> coveredFileKeys, List<FileUploadMsg> notCoveredFileUploadMsgs, List<FileUploadMsg> fileUploadMsgs) {
        for (FileUploadMsg fileUploadMsg : fileUploadMsgs) {
            String fileName = fileUploadMsg.getName();
            ResultInfo checkFileTypeRes = this.checkFileType(fileName = fileName.trim().replace("\n", ""), fileUploadMsg.getSize());
            if (null != checkFileTypeRes) {
                logHellperUtil.error(taskDefine.getKey(), logDimensionCollection, "\u9644\u4ef6\u4e0a\u4f20\u5931\u8d25", PromptConsts.fileOprateServiceError(checkFileTypeRes.getErrorMessage()));
                return checkFileTypeRes;
            }
            ResultInfo checkSensitiveWordRes = this.checkSensitiveWord(fileName);
            if (null != checkSensitiveWordRes) {
                logHellperUtil.error(taskDefine.getKey(), logDimensionCollection, "\u9644\u4ef6\u4e0a\u4f20\u5931\u8d25", PromptConsts.fileOprateServiceError(checkSensitiveWordRes.getErrorMessage()));
                return checkSensitiveWordRes;
            }
            String coveredFileKey = fileUploadMsg.getFileKey();
            if (fileUploadMsg.isCovered() && StringUtils.isNotEmpty((String)coveredFileKey)) {
                coveredFileKeys.add(coveredFileKey);
                continue;
            }
            if (fileUploadMsg.isCovered()) continue;
            notCoveredFileUploadMsgs.add(fileUploadMsg);
        }
        return null;
    }

    @Override
    public ResultInfo uploadFileByFileKey(FileUploadByFileKeyCtx context) {
        ResultInfo judgeGroupKeySameNameRes;
        if (null == context || StringUtils.isEmpty((String)context.getFormSchemeKey()) || null == context.getDimensionCombination() || StringUtils.isEmpty((String)context.getFieldKey()) || null == context.getFileUploadByFileKeyInfos() || context.getFileUploadByFileKeyInfos().isEmpty()) {
            throw new IllegalArgumentException("\u53c2\u6570\u9519\u8bef");
        }
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(context.getFormSchemeKey());
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(formScheme.getTaskKey());
        FileBucketNameParam param = new FileBucketNameParam(taskDefine.getDataScheme());
        DimensionValueSet dimension = context.getDimensionCombination().toDimensionValueSet();
        String dwDimensionName = this.entityMetaService.getDimensionName(taskDefine.getDw());
        IPeriodEntityAdapter periodAdapter = this.periodEngineService.getPeriodAdapter();
        String periodDimensionName = periodAdapter.getPeriodDimensionName();
        String targetKey = String.valueOf(dimension.getValue(dwDimensionName));
        String periodCode = String.valueOf(dimension.getValue(periodDimensionName));
        LogDimensionCollection logDimensionCollection = new LogDimensionCollection();
        logDimensionCollection.setDw(taskDefine.getDw(), new String[]{targetKey});
        logDimensionCollection.setPeriod(taskDefine.getDateTime(), periodCode);
        LogHellperUtil logHellperUtil = new LogHellperUtil(this.dataServiceLoggerFactory, MOUDLE);
        ResultInfo fieldCheck = this.fieldCheck(context.getFieldKey(), taskDefine, logHellperUtil, logDimensionCollection);
        if (null != fieldCheck) {
            return fieldCheck;
        }
        List<FileUploadByFileKeyInfo> fileUploadByFileKeyInfos = context.getFileUploadByFileKeyInfos();
        ResultInfo checkFileTypeRes = this.checkFileTypeAndName(taskDefine, logHellperUtil, logDimensionCollection, fileUploadByFileKeyInfos);
        if (checkFileTypeRes != null) {
            return checkFileTypeRes;
        }
        String groupKey = context.getGroupKey();
        String filePoolKey = "";
        boolean openFilepool = this.filePoolService.isOpenFilepool();
        if (openFilepool) {
            IEntityDefine entityDefine = this.entityMetaService.queryEntity(taskDefine.getDw());
            filePoolKey = taskDefine.getKey() + dimension.getValue(entityDefine.getDimensionName());
            ResultInfo judgeFilepoolSameNameRes = this.judgeFilepoolSameName(fileUploadByFileKeyInfos, filePoolKey);
            if (null != judgeFilepoolSameNameRes) {
                logHellperUtil.error(taskDefine.getKey(), logDimensionCollection, "\u5e26fileKey\u4e0a\u4f20\u9644\u4ef6\u5931\u8d25", PromptConsts.fileOprateServiceError(judgeFilepoolSameNameRes.getErrorMessage()));
                return judgeFilepoolSameNameRes;
            }
        } else if (StringUtils.isNotEmpty((String)groupKey) && null != (judgeGroupKeySameNameRes = this.judgeGroupKeySameName(fileUploadByFileKeyInfos, groupKey, taskDefine.getDataScheme(), taskDefine.getKey()))) {
            logHellperUtil.error(taskDefine.getKey(), logDimensionCollection, "\u9644\u4ef6\u4e0a\u4f20\u5931\u8d25", PromptConsts.fileOprateServiceError(judgeGroupKeySameNameRes.getErrorMessage()));
            return judgeGroupKeySameNameRes;
        }
        return this.uploadFile(fileUploadByFileKeyInfos, param, taskDefine, logHellperUtil, logDimensionCollection, groupKey, filePoolKey, openFilepool, context);
    }

    private ResultInfo uploadFile(List<FileUploadByFileKeyInfo> fileUploadByFileKeyInfos, FileBucketNameParam param, TaskDefine taskDefine, LogHellperUtil logHellperUtil, LogDimensionCollection logDimensionCollection, String groupKey, String filePoolKey, boolean openFilepool, FileUploadByFileKeyCtx context) {
        ArrayList<String> oldFileKeys = new ArrayList<String>();
        for (FileUploadByFileKeyInfo fileUploadByFileKeyInfo : fileUploadByFileKeyInfos) {
            String oldFileKey = fileUploadByFileKeyInfo.getFileKey();
            if (!StringUtils.isNotEmpty((String)oldFileKey)) continue;
            oldFileKeys.add(oldFileKey);
        }
        Map<String, AttachmentRelInfo> attachmentRelInfoMap = null;
        if (!oldFileKeys.isEmpty()) {
            List<String> noExistFileKeys = this.attachmentFileAreaService.existFile(param, oldFileKeys, 1);
            oldFileKeys.removeAll(noExistFileKeys);
            if (!oldFileKeys.isEmpty()) {
                ResultInfo fileCanDelRes = this.fileCanDel(taskDefine, oldFileKeys);
                if (null != fileCanDelRes) {
                    logHellperUtil.error(taskDefine.getKey(), logDimensionCollection, "\u5e26fileKey\u4e0a\u4f20\u9644\u4ef6\u5931\u8d25", PromptConsts.fileOprateServiceError(fileCanDelRes.getErrorMessage()));
                    return fileCanDelRes;
                }
                attachmentRelInfoMap = this.delFileAndSendListener(taskDefine, oldFileKeys);
            }
        }
        if (StringUtils.isEmpty((String)groupKey)) {
            groupKey = UUID.randomUUID().toString();
        }
        List<String> uploadFileKeys = this.uploadFile(param, fileUploadByFileKeyInfos, groupKey, filePoolKey, openFilepool);
        this.sendOverlayUploadListener(taskDefine, oldFileKeys, attachmentRelInfoMap);
        this.writeFileRelTable(taskDefine, groupKey, uploadFileKeys, context.getDimensionCombination(), context.getFormSchemeKey(), context.getFieldKey());
        ResultInfo resultInfo = new ResultInfo();
        resultInfo.setSuccess(true);
        resultInfo.setGroupKey(groupKey);
        logHellperUtil.info(taskDefine.getKey(), logDimensionCollection, "\u5e26fileKey\u4e0a\u4f20\u9644\u4ef6\u6210\u529f", "\u5e26fileKey\u4e0a\u4f20\u9644\u4ef6\u6210\u529f");
        return resultInfo;
    }

    @NotNull
    private List<String> uploadFile(FileBucketNameParam param, List<FileUploadByFileKeyInfo> fileUploadByFileKeyInfos, String groupKey, String filePoolKey, boolean openFilepool) {
        ArrayList<String> uploadFileKeys = new ArrayList<String>();
        if (openFilepool) {
            for (FileUploadByFileKeyInfo fileUploadByFileKeyInfo : fileUploadByFileKeyInfos) {
                HashMap<String, String> expandInfo = new HashMap<String, String>();
                expandInfo.put("secretlevel", fileUploadByFileKeyInfo.getFileSecret());
                expandInfo.put("filePool", filePoolKey);
                expandInfo.put("category", fileUploadByFileKeyInfo.getCategory());
                FileInfo fileInfo = this.attachmentFileAreaService.uploadByKey(param, fileUploadByFileKeyInfo.getName(), fileUploadByFileKeyInfo.getFileKey(), fileUploadByFileKeyInfo.getFile(), expandInfo);
                uploadFileKeys.add(fileInfo.getKey());
            }
        } else {
            for (FileUploadByFileKeyInfo fileUploadByFileKeyInfo : fileUploadByFileKeyInfos) {
                HashMap<String, String> expandInfo = new HashMap<String, String>();
                expandInfo.put("fileGroupKey", groupKey);
                expandInfo.put("secretlevel", fileUploadByFileKeyInfo.getFileSecret());
                FileInfo fileInfo = this.attachmentFileAreaService.uploadByKey(param, fileUploadByFileKeyInfo.getName(), fileUploadByFileKeyInfo.getFileKey(), fileUploadByFileKeyInfo.getFile(), expandInfo);
                uploadFileKeys.add(fileInfo.getKey());
            }
        }
        return uploadFileKeys;
    }

    @Nullable
    private ResultInfo checkFileTypeAndName(TaskDefine taskDefine, LogHellperUtil logHellperUtil, LogDimensionCollection logDimensionCollection, List<FileUploadByFileKeyInfo> fileUploadByFileKeyInfos) {
        for (FileUploadByFileKeyInfo fileUploadByFileKeyInfo : fileUploadByFileKeyInfos) {
            String fileName = fileUploadByFileKeyInfo.getName();
            ResultInfo checkFileTypeRes = this.checkFileType(fileName = fileName.trim().replace("\n", ""), fileUploadByFileKeyInfo.getSize());
            if (null != checkFileTypeRes) {
                logHellperUtil.error(taskDefine.getKey(), logDimensionCollection, "\u5e26fileKey\u4e0a\u4f20\u9644\u4ef6\u5931\u8d25", PromptConsts.fileOprateServiceError(checkFileTypeRes.getErrorMessage()));
                return checkFileTypeRes;
            }
            ResultInfo checkSensitiveWordRes = this.checkSensitiveWord(fileName);
            if (null == checkSensitiveWordRes) continue;
            logHellperUtil.error(taskDefine.getKey(), logDimensionCollection, "\u5e26fileKey\u4e0a\u4f20\u9644\u4ef6\u5931\u8d25", PromptConsts.fileOprateServiceError(checkSensitiveWordRes.getErrorMessage()));
            return checkSensitiveWordRes;
        }
        return null;
    }

    private void writeFileRelTable(TaskDefine taskDefine, String groupKey, List<String> uploadFileKeys, DimensionCombination dimensionCombination, String formSchemeKey, String fieldKey) {
        if (!uploadFileKeys.isEmpty()) {
            FileRelateGroupInfo fileRelateGroupInfo = new FileRelateGroupInfo();
            fileRelateGroupInfo.setDataSchemeKey(taskDefine.getDataScheme());
            fileRelateGroupInfo.setTaskKey(taskDefine.getKey());
            fileRelateGroupInfo.setDimensionCombination(dimensionCombination);
            fileRelateGroupInfo.setFormSchemeKey(formSchemeKey);
            fileRelateGroupInfo.setFieldKey(fieldKey);
            fileRelateGroupInfo.setGroupKey(groupKey);
            fileRelateGroupInfo.setFileKeys(uploadFileKeys);
            this.filePoolService.uploadFilesFromFilePool(fileRelateGroupInfo);
        }
    }

    private void sendOverlayUploadListener(TaskDefine taskDefine, List<String> oldFileKeys, Map<String, AttachmentRelInfo> attachmentRelInfoMap) {
        if (!oldFileKeys.isEmpty() && null != attachmentRelInfoMap && !attachmentRelInfoMap.isEmpty()) {
            List<IFileListener> fileListeners = this.getiFileListeners();
            for (Map.Entry<String, AttachmentRelInfo> groupKeyAttachmentRelInfo : attachmentRelInfoMap.entrySet()) {
                String attRelGroupKey = groupKeyAttachmentRelInfo.getKey();
                AttachmentRelInfo attachmentRelInfo = groupKeyAttachmentRelInfo.getValue();
                for (IFileListener fileListener : fileListeners) {
                    FileUploadEvent fileUploadEvent = new FileUploadEvent(taskDefine.getDataScheme(), taskDefine.getKey(), attachmentRelInfo.getFormSchemeKey(), attachmentRelInfo.getDim(), attachmentRelInfo.getFieldKey(), attRelGroupKey, attachmentRelInfo.getFileKeys());
                    fileListener.afterFileUpload(fileUploadEvent);
                }
            }
        }
    }

    @NotNull
    private List<IFileListener> getiFileListeners() {
        ArrayList<IFileListener> fileListeners = new ArrayList<IFileListener>();
        if (null != this.fileListenerProviders) {
            for (IFileListenerProvider fileListenerProvider : this.fileListenerProviders) {
                IFileListener fileListener = fileListenerProvider.getFileListener(null);
                if (null == fileListener) continue;
                fileListeners.add(fileListener);
            }
        }
        return fileListeners;
    }

    private Map<String, AttachmentRelInfo> delFileAndSendListener(TaskDefine taskDefine, List<String> oldFileKeys) {
        List<IFileListener> fileListeners = this.getiFileListeners();
        Map<String, AttachmentRelInfo> attachmentRelInfoMap = this.getAttachmentRelInfo(taskDefine, oldFileKeys);
        if (!attachmentRelInfoMap.isEmpty()) {
            for (Map.Entry<String, AttachmentRelInfo> groupKeyAttachmentRelInfo : attachmentRelInfoMap.entrySet()) {
                String groupKey = groupKeyAttachmentRelInfo.getKey();
                AttachmentRelInfo attachmentRelInfo = groupKeyAttachmentRelInfo.getValue();
                for (IFileListener fileListener : fileListeners) {
                    FileDelEvent fileDelEvent = new FileDelEvent(taskDefine.getDataScheme(), taskDefine.getKey(), attachmentRelInfo.getFormSchemeKey(), groupKey, attachmentRelInfo.getFileKeys());
                    fileListener.beforeFileDelete(fileDelEvent);
                }
            }
        }
        FileBucketNameParam param = new FileBucketNameParam(taskDefine.getDataScheme());
        this.attachmentFileAreaService.batchDelete(param, oldFileKeys);
        if (!attachmentRelInfoMap.isEmpty()) {
            for (Map.Entry<String, AttachmentRelInfo> groupKeyAttachmentRelInfo : attachmentRelInfoMap.entrySet()) {
                String groupKey = groupKeyAttachmentRelInfo.getKey();
                AttachmentRelInfo attachmentRelInfo = groupKeyAttachmentRelInfo.getValue();
                for (IFileListener fileListener : fileListeners) {
                    FileDelEvent fileDelEvent = new FileDelEvent(taskDefine.getDataScheme(), taskDefine.getKey(), attachmentRelInfo.getFormSchemeKey(), groupKey, attachmentRelInfo.getFileKeys());
                    fileListener.afterFileDelete(fileDelEvent);
                }
            }
        }
        return attachmentRelInfoMap;
    }

    private ResultInfo fileCanDel(TaskDefine taskDefine, List<String> oldFileKeys) {
        for (String oldFileKey : oldFileKeys) {
            boolean fileCanDel = this.filePoolService.judgeFileOverwritten(oldFileKey, taskDefine.getKey());
            if (fileCanDel) continue;
            String errorMsg = "\u9644\u4ef6\uff1a " + oldFileKey + " \u5df2\u5b58\u5728\u4e14\u65e0\u6cd5\u88ab\u5220\u9664";
            ResultInfo resultInfo = new ResultInfo();
            resultInfo.setSuccess(false);
            resultInfo.setErrorMessage(errorMsg);
            return resultInfo;
        }
        return null;
    }

    private ResultInfo judgeGroupKeySameName(List<? extends FileUploadByFileKeyInfo> uploadFileInfos, String groupKey, String dataschemeKey, String taskKey) {
        CommonParamsDTO params = new CommonParamsDTO();
        params.setDataSchemeKey(dataschemeKey);
        params.setTaskKey(taskKey);
        List<FileInfo> groupFiles = this.filePoolService.getFileInfoByGroup(groupKey, params);
        for (FileUploadByFileKeyInfo fileUploadByFileKeyInfo : uploadFileInfos) {
            for (FileInfo groupFile : groupFiles) {
                if (!groupFile.getName().equals(fileUploadByFileKeyInfo.getName()) || !(fileUploadByFileKeyInfo instanceof FileUploadMsg) && groupFile.getKey().equals(fileUploadByFileKeyInfo.getFileKey())) continue;
                String msg = "\u5f53\u524dgroupKey\u5206\u7ec4\u4e0b\u6709\u91cd\u540d\u6587\u4ef6\uff1a" + groupFile.getName();
                ResultInfo resultInfo = new ResultInfo();
                resultInfo.setSuccess(false);
                resultInfo.setErrorMessage(msg);
                return resultInfo;
            }
        }
        return null;
    }

    private ResultInfo judgeFilepoolSameName(List<? extends FileUploadByFileKeyInfo> uploadFileInfos, String filePoolKey) {
        FilePoolAllFileContext filePoolAllFileContext = new FilePoolAllFileContext();
        filePoolAllFileContext.setFilepoolKey(filePoolKey);
        List<FilePoolFiles> filePoolFiles = this.filePoolService.getFilePoolFiles(filePoolAllFileContext);
        for (FileUploadByFileKeyInfo fileUploadByFileKeyInfo : uploadFileInfos) {
            for (FilePoolFiles filePoolFile : filePoolFiles) {
                if (!filePoolFile.getFileName().equals(fileUploadByFileKeyInfo.getName()) || !(fileUploadByFileKeyInfo instanceof FileUploadMsg) && filePoolFile.getFileKey().equals(fileUploadByFileKeyInfo.getFileKey())) continue;
                String msg = "\u9644\u4ef6\u6c60\u6709\u91cd\u540d\u6587\u4ef6\uff1a" + filePoolFile.getFileName();
                ResultInfo resultInfo = new ResultInfo();
                resultInfo.setSuccess(false);
                resultInfo.setErrorMessage(msg);
                return resultInfo;
            }
        }
        return null;
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
        INvwaDataAccess readOnlyDataAccess = this.nvwaDataAccessProvider.createReadOnlyDataAccess(queryModel);
        HashMap<String, AttachmentRelInfo> attachmentRelInfoMap = new HashMap<String, AttachmentRelInfo>();
        try {
            MemoryDataSet dataTable = readOnlyDataAccess.executeQuery(context);
            List columns = queryModel.getColumns();
            this.oprateData(taskDefine, dimensionChanger, groupKeyIndex, periodIndex, fieldKeyIndex, fileKeyIndex, attachmentRelInfoMap, (MemoryDataSet<NvwaQueryColumn>)dataTable, columns);
        }
        catch (Exception e) {
            logger.error(PromptConsts.causeIfError(e.getMessage()), e);
        }
        return attachmentRelInfoMap;
    }

    private void oprateData(TaskDefine taskDefine, DimensionChanger dimensionChanger, int groupKeyIndex, int periodIndex, int fieldKeyIndex, int fileKeyIndex, Map<String, AttachmentRelInfo> attachmentRelInfoMap, MemoryDataSet<NvwaQueryColumn> dataTable, List<NvwaQueryColumn> columns) throws Exception {
        for (int i = 0; i < dataTable.size(); ++i) {
            DataRow data = dataTable.get(i);
            String groupKey = data.getString(groupKeyIndex);
            AttachmentRelInfo attachmentRelInfo = attachmentRelInfoMap.get(groupKey);
            if (null != attachmentRelInfo) {
                List<String> fileKeys = attachmentRelInfo.getFileKeys();
                fileKeys.add(data.getString(fileKeyIndex));
                continue;
            }
            this.putAttRelInfoMap(taskDefine, dimensionChanger, periodIndex, fieldKeyIndex, fileKeyIndex, attachmentRelInfoMap, columns, data, groupKey);
        }
    }

    private void putAttRelInfoMap(TaskDefine taskDefine, DimensionChanger dimensionChanger, int periodIndex, int fieldKeyIndex, int fileKeyIndex, Map<String, AttachmentRelInfo> attachmentRelInfoMap, List<NvwaQueryColumn> columns, DataRow data, String groupKey) throws Exception {
        String period = data.getString(periodIndex);
        SchemePeriodLinkDefine schemePeriodLinkDefine = this.runTimeViewController.querySchemePeriodLinkByPeriodAndTask(period, taskDefine.getKey());
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

    private ResultInfo checkSensitiveWord(String fileName) {
        String fileNameNoSuffix = "";
        int fileNameIndex = fileName.lastIndexOf(".");
        fileNameNoSuffix = fileNameIndex >= 0 ? fileName.substring(0, fileNameIndex) : fileName;
        List sensitiveWordList = this.checkSensitiveWordService.thisWordIsSensitiveWord(fileNameNoSuffix);
        if (sensitiveWordList.size() > 0) {
            StringBuilder errorMsg = new StringBuilder();
            errorMsg.append("\u9644\u4ef6\u540d\u5305\u542b\u654f\u611f\u8bcd\u4fe1\u606f,");
            for (SensitiveWordDaoObject sensitiveWordDaoObject : sensitiveWordList) {
                if (sensitiveWordDaoObject.getSensitiveDescription() == null || sensitiveWordDaoObject.getSensitiveDescription().length() <= 0) continue;
                errorMsg.append(sensitiveWordDaoObject.getSensitiveDescription()).append(";");
            }
            errorMsg.append("\u9644\u4ef6\u540d\u79f0: ").append(fileName);
            ResultInfo resultInfo = new ResultInfo();
            resultInfo.setSuccess(false);
            resultInfo.setErrorMessage(errorMsg.toString());
            return resultInfo;
        }
        return null;
    }

    private ResultInfo checkFileType(String fileName, long fileSize) {
        FileUploadReturnInfo fileUploadReturnInfo = this.checkUploadFileService.checkFileInfo(fileName, Long.valueOf(fileSize), null, null);
        if (StringUtils.isNotEmpty((String)fileUploadReturnInfo.getErrorType())) {
            String errorMsg = String.format("\u4e0a\u4f20\u9644\u4ef6\u7c7b\u578b\u4e0d\u652f\u6301,\u9644\u4ef6\u540d\u79f0: %s", fileName);
            ResultInfo resultInfo = new ResultInfo();
            resultInfo.setSuccess(false);
            resultInfo.setErrorMessage(errorMsg);
            return resultInfo;
        }
        return null;
    }

    private ResultInfo fieldCheck(String fieldKey, TaskDefine taskDefine, LogHellperUtil logHellperUtil, LogDimensionCollection logDimensionCollection) {
        ResultInfo resultInfo;
        FieldType type = null;
        try {
            FieldDefine fieldDefine = this.runTimeViewController.queryFieldDefine(fieldKey);
            if (null != fieldDefine) {
                type = fieldDefine.getType();
            }
        }
        catch (Exception e) {
            logHellperUtil.error(taskDefine.getKey(), logDimensionCollection, "\u9644\u4ef6\u4e0a\u4f20\u5931\u8d25", "\u7cfb\u7edf\u9519\u8bef\uff1a\u67e5\u8be2\u6307\u6807\u9519\u8bef");
            logger.error(PromptConsts.causeIfError(e.getMessage()), e);
            ResultInfo resultInfo2 = new ResultInfo();
            resultInfo2.setSuccess(false);
            resultInfo2.setErrorMessage("\u6307\u6807\u6709\u8bef");
            return resultInfo2;
        }
        if (null == type) {
            resultInfo = new ResultInfo();
            resultInfo.setSuccess(false);
            resultInfo.setErrorMessage("\u6307\u6807\u7c7b\u578b\u4e3a\u7a7a");
            logHellperUtil.error(taskDefine.getKey(), logDimensionCollection, "\u9644\u4ef6\u4e0a\u4f20\u5931\u8d25", "\u4e1a\u52a1\u9519\u8bef\uff1a\u6307\u6807\u7c7b\u578b\u4e3a\u7a7a");
            return resultInfo;
        }
        if (!type.equals((Object)FieldType.FIELD_TYPE_FILE)) {
            resultInfo = new ResultInfo();
            resultInfo.setSuccess(false);
            resultInfo.setErrorMessage("\u6307\u6807\u7c7b\u578b\u975e\u9644\u4ef6\u578b");
            logHellperUtil.error(taskDefine.getKey(), logDimensionCollection, "\u9644\u4ef6\u4e0a\u4f20\u5931\u8d25", "\u4e1a\u52a1\u9519\u8bef\uff1a\u6307\u6807\u7c7b\u578b\u975e\u9644\u4ef6\u578b");
            return resultInfo;
        }
        return null;
    }

    @Override
    public List<FileRelInfo> queryFileRelInfo(FileRelQueryParam param) {
        DataScheme dataScheme = this.runtimeDataSchemeService.getDataScheme(param.getDataSchemeKey());
        String tableName = this.fileOperationService.getCurrentSplitTableName(dataScheme);
        TableModelDefine filePoolTable = this.dataModelService.getTableModelDefineByName(tableName);
        if (null == filePoolTable) {
            throw new NotFoundTableDefineException(new String[]{"\u9644\u4ef6\u5173\u8054\u8868\u672a\u521b\u5efa\uff0c\u8bf7\u91cd\u65b0\u53d1\u5e03\u6570\u636e\u65b9\u6848\uff01"});
        }
        ArrayList<FileRelInfo> fileRelInfos = new ArrayList<FileRelInfo>();
        DimensionCombination dimensionCombination = param.getDimensionCombination();
        String fieldKey = param.getFieldKey();
        String groupKey = param.getGroupKey();
        List<String> fileKeys = param.getFileKeys();
        DimensionChanger dimensionChanger = this.dataEngineAdapter.getDimensionChanger(tableName);
        NvwaQueryModel queryModel = this.buildQueryModel(filePoolTable, dimensionCombination, fieldKey, groupKey, fileKeys, dimensionChanger);
        INvwaDataAccess readOnlyDataAccess = this.nvwaDataAccessProvider.createReadOnlyDataAccess(queryModel);
        DataAccessContext context = new DataAccessContext(this.dataModelService);
        try {
            MemoryDataSet dataTable = readOnlyDataAccess.executeQuery(context);
            List columns = queryModel.getColumns();
            this.oprateData(fileRelInfos, dimensionChanger, (MemoryDataSet<NvwaQueryColumn>)dataTable, columns);
        }
        catch (Exception e) {
            logger.error(PromptConsts.causeIfError(e.getMessage()), e);
            return Collections.emptyList();
        }
        return fileRelInfos;
    }

    private void oprateData(List<FileRelInfo> fileRelInfos, DimensionChanger dimensionChanger, MemoryDataSet<NvwaQueryColumn> dataTable, List<NvwaQueryColumn> columns) {
        for (int i = 0; i < dataTable.size(); ++i) {
            FileRelInfo fileRelInfo = new FileRelInfo();
            DimensionCombinationBuilder builder = new DimensionCombinationBuilder();
            DataRow item = dataTable.get(i);
            for (int j = 0; j < columns.size(); ++j) {
                String dimensionName;
                String columnCode = columns.get(j).getColumnModel().getCode();
                if ("FIELD_KEY".equals(columnCode)) {
                    fileRelInfo.setFieldKey(item.getString(j));
                    continue;
                }
                if ("GROUPKEY".equals(columnCode)) {
                    fileRelInfo.setGroupKey(item.getString(j));
                    continue;
                }
                if ("FILEKEY".equals(columnCode)) {
                    fileRelInfo.setFileKey(item.getString(j));
                    continue;
                }
                if ("ID".equals(columnCode) || "ISDELETE".equals(columnCode) || !StringUtils.isNotEmpty((String)(dimensionName = dimensionChanger.getDimensionName(columnCode)))) continue;
                builder.setValue(dimensionName, null, (Object)item.getString(j));
            }
            fileRelInfo.setDimensionCombination(builder.getCombination());
            fileRelInfos.add(fileRelInfo);
        }
    }

    @NotNull
    private NvwaQueryModel buildQueryModel(TableModelDefine filePoolTable, DimensionCombination dimensionCombination, String fieldKey, String groupKey, List<String> fileKeys, DimensionChanger dimensionChanger) {
        NvwaQueryModel queryModel = new NvwaQueryModel();
        List filePoolFields = this.dataModelService.getColumnModelDefinesByTable(filePoolTable.getID());
        for (ColumnModelDefine filePoolField : filePoolFields) {
            queryModel.getColumns().add(new NvwaQueryColumn(filePoolField));
            if (filePoolField.getCode().equals("ISDELETE")) {
                queryModel.getColumnFilters().put(filePoolField, "0");
                continue;
            }
            if (StringUtils.isNotEmpty((String)groupKey) && filePoolField.getCode().equals("GROUPKEY")) {
                queryModel.getColumnFilters().put(filePoolField, groupKey);
                continue;
            }
            if (null != fileKeys && !fileKeys.isEmpty() && filePoolField.getCode().equals("FILEKEY")) {
                queryModel.getColumnFilters().put(filePoolField, fileKeys);
                continue;
            }
            if (null == dimensionCombination || !StringUtils.isNotEmpty((String)fieldKey)) continue;
            if (filePoolField.getCode().equals("FIELD_KEY")) {
                queryModel.getColumnFilters().put(filePoolField, fieldKey);
                continue;
            }
            String dimensionName = dimensionChanger.getDimensionName(filePoolField);
            if (!StringUtils.isNotEmpty((String)dimensionName) || null == dimensionCombination.getValue(dimensionName)) continue;
            queryModel.getColumnFilters().put(filePoolField, dimensionCombination.getValue(dimensionName));
        }
        return queryModel;
    }
}

