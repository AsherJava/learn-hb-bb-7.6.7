/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.oss.ObjectInfo
 *  com.jiuqi.bi.util.zip.ZipEntry
 *  com.jiuqi.bi.util.zip.ZipOutputStream
 *  com.jiuqi.np.dataengine.IDataAccessProvider
 *  com.jiuqi.np.dataengine.QueryEnvironment
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IDataQuery
 *  com.jiuqi.np.dataengine.intf.IDataRow
 *  com.jiuqi.np.dataengine.intf.IDataTable
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 *  com.jiuqi.np.definition.common.FieldType
 *  com.jiuqi.np.definition.common.StringUtils
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.period.PeriodUtil
 *  com.jiuqi.nr.attachment.exception.FieldNameIsSensitiveWordException
 *  com.jiuqi.nr.attachment.exception.FileTypeErrorException
 *  com.jiuqi.nr.attachment.input.AombstoneFileInfo
 *  com.jiuqi.nr.attachment.input.BatchDeleteFileInfo
 *  com.jiuqi.nr.attachment.input.ChangeFileCategoryInfo
 *  com.jiuqi.nr.attachment.input.ChangeFileSecretInfo
 *  com.jiuqi.nr.attachment.input.CommonParamsDTO
 *  com.jiuqi.nr.attachment.input.DownLoadFileInfo
 *  com.jiuqi.nr.attachment.input.FilePoolAllFileContext
 *  com.jiuqi.nr.attachment.input.FilePoolUploadContext
 *  com.jiuqi.nr.attachment.input.FileRelateGroupInfo
 *  com.jiuqi.nr.attachment.input.FileUploadByGroupKeyContext
 *  com.jiuqi.nr.attachment.input.FileUploadContext
 *  com.jiuqi.nr.attachment.input.FileUploadInfo
 *  com.jiuqi.nr.attachment.input.RenameInfo
 *  com.jiuqi.nr.attachment.input.SearchContext
 *  com.jiuqi.nr.attachment.message.FileInfo
 *  com.jiuqi.nr.attachment.message.RowDataInfo
 *  com.jiuqi.nr.attachment.output.FileBaseRefInfo
 *  com.jiuqi.nr.attachment.output.FilePoolFiles
 *  com.jiuqi.nr.attachment.output.RowDataValues
 *  com.jiuqi.nr.attachment.service.FileOperationService
 *  com.jiuqi.nr.attachment.service.FilePoolService
 *  com.jiuqi.nr.attachment.utils.FileType
 *  com.jiuqi.nr.common.exception.ExceptionResult
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.data.access.param.IAccessResult
 *  com.jiuqi.nr.data.access.service.IDataAccessService
 *  com.jiuqi.nr.data.access.service.IDataAccessServiceProvider
 *  com.jiuqi.nr.data.access.util.DimCollectionBuildUtil
 *  com.jiuqi.nr.datascheme.api.DataScheme
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationBuilder
 *  com.jiuqi.nr.dataservice.core.dimension.provider.DimensionProviderFactory
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 *  com.jiuqi.nr.entity.exception.UnauthorizedEntityException
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityAuthorityService
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.fileupload.service.FileUploadOssService
 *  com.jiuqi.nr.jtable.aop.JLoggerAspect
 *  com.jiuqi.nr.jtable.exception.DataStatueCheckException
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  com.jiuqi.nr.jtable.params.output.ReturnInfo
 *  com.jiuqi.nr.jtable.service.ISecretLevelService
 *  com.jiuqi.nr.jtable.uniformity.service.IDataStateCheckService
 *  com.jiuqi.nr.jtable.util.DimensionValueSetUtil
 *  com.jiuqi.nr.period.common.utils.PeriodUtils
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.nr.period.modal.IPeriodEntity
 *  javax.annotation.Resource
 *  javax.servlet.http.HttpServletResponse
 */
package com.jiuqi.nr.dataentry.attachment.service.impl;

import com.jiuqi.bi.oss.ObjectInfo;
import com.jiuqi.bi.util.zip.ZipEntry;
import com.jiuqi.bi.util.zip.ZipOutputStream;
import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.dataengine.QueryEnvironment;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IDataQuery;
import com.jiuqi.np.dataengine.intf.IDataRow;
import com.jiuqi.np.dataengine.intf.IDataTable;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.definition.common.FieldType;
import com.jiuqi.np.definition.common.StringUtils;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.period.PeriodUtil;
import com.jiuqi.nr.attachment.exception.FieldNameIsSensitiveWordException;
import com.jiuqi.nr.attachment.exception.FileTypeErrorException;
import com.jiuqi.nr.attachment.input.AombstoneFileInfo;
import com.jiuqi.nr.attachment.input.BatchDeleteFileInfo;
import com.jiuqi.nr.attachment.input.ChangeFileCategoryInfo;
import com.jiuqi.nr.attachment.input.ChangeFileSecretInfo;
import com.jiuqi.nr.attachment.input.CommonParamsDTO;
import com.jiuqi.nr.attachment.input.DownLoadFileInfo;
import com.jiuqi.nr.attachment.input.FilePoolAllFileContext;
import com.jiuqi.nr.attachment.input.FilePoolUploadContext;
import com.jiuqi.nr.attachment.input.FileRelateGroupInfo;
import com.jiuqi.nr.attachment.input.FileUploadByGroupKeyContext;
import com.jiuqi.nr.attachment.input.FileUploadContext;
import com.jiuqi.nr.attachment.input.FileUploadInfo;
import com.jiuqi.nr.attachment.input.RenameInfo;
import com.jiuqi.nr.attachment.input.SearchContext;
import com.jiuqi.nr.attachment.message.FileInfo;
import com.jiuqi.nr.attachment.message.RowDataInfo;
import com.jiuqi.nr.attachment.output.FileBaseRefInfo;
import com.jiuqi.nr.attachment.output.FilePoolFiles;
import com.jiuqi.nr.attachment.output.RowDataValues;
import com.jiuqi.nr.attachment.service.FileOperationService;
import com.jiuqi.nr.attachment.service.FilePoolService;
import com.jiuqi.nr.attachment.utils.FileType;
import com.jiuqi.nr.common.exception.ExceptionResult;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.data.access.param.IAccessResult;
import com.jiuqi.nr.data.access.service.IDataAccessService;
import com.jiuqi.nr.data.access.service.IDataAccessServiceProvider;
import com.jiuqi.nr.data.access.util.DimCollectionBuildUtil;
import com.jiuqi.nr.dataentry.attachment.intf.AttachmentDownloadContext;
import com.jiuqi.nr.dataentry.attachment.intf.DelAllNotReferAttachContext;
import com.jiuqi.nr.dataentry.attachment.intf.ForceDeleteAttachmentContext;
import com.jiuqi.nr.dataentry.attachment.intf.HistoricalAttachmentClearingContext;
import com.jiuqi.nr.dataentry.attachment.intf.IAttachmentChangeFileCategoryContext;
import com.jiuqi.nr.dataentry.attachment.intf.IAttachmentChangeFileSecretContext;
import com.jiuqi.nr.dataentry.attachment.intf.IAttachmentSaveFilesContext;
import com.jiuqi.nr.dataentry.attachment.intf.ReFileNameContext;
import com.jiuqi.nr.dataentry.attachment.message.DownloadInfo;
import com.jiuqi.nr.dataentry.attachment.message.SaveFilesResult;
import com.jiuqi.nr.dataentry.attachment.service.AttachmentOperationService;
import com.jiuqi.nr.dataentry.bean.BatchDownLoadEnclosureInfo;
import com.jiuqi.nr.dataentry.paramInfo.AttachmentInfo;
import com.jiuqi.nr.dataentry.paramInfo.FilesUploadInfo;
import com.jiuqi.nr.dataentry.util.DataEntryUtil;
import com.jiuqi.nr.datascheme.api.DataScheme;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationBuilder;
import com.jiuqi.nr.dataservice.core.dimension.provider.DimensionProviderFactory;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import com.jiuqi.nr.entity.exception.UnauthorizedEntityException;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityAuthorityService;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.fileupload.service.FileUploadOssService;
import com.jiuqi.nr.jtable.aop.JLoggerAspect;
import com.jiuqi.nr.jtable.exception.DataStatueCheckException;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.params.output.ReturnInfo;
import com.jiuqi.nr.jtable.service.ISecretLevelService;
import com.jiuqi.nr.jtable.uniformity.service.IDataStateCheckService;
import com.jiuqi.nr.jtable.util.DimensionValueSetUtil;
import com.jiuqi.nr.period.common.utils.PeriodUtils;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nr.period.modal.IPeriodEntity;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AttachmentOperationServiceImpl
implements AttachmentOperationService {
    private static final Logger logger = LoggerFactory.getLogger(AttachmentOperationServiceImpl.class);
    @Autowired
    private JLoggerAspect loggerAspect;
    @Autowired
    private IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    @Resource
    private IDataAccessProvider dataAccessProvider;
    @Resource
    private IRunTimeViewController runTimeViewController;
    @Resource
    private IEntityViewRunTimeController entityViewRunTimeController;
    @Autowired
    private ISecretLevelService secretLevelService;
    @Autowired
    private FileOperationService fileOperationService;
    @Autowired
    private IDataStateCheckService dataStateCheckService;
    @Autowired
    private FileUploadOssService fileUploadOssService;
    @Autowired
    private FilePoolService filePoolService;
    @Autowired
    private IPeriodEntityAdapter iPeriodEntityAdapter;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private DimensionProviderFactory dimensionProviderFactory;
    @Autowired
    private IDataAccessServiceProvider dataAccessServiceProvider;
    @Autowired
    private IEntityAuthorityService entityAuthorityService;
    @Autowired
    private PeriodEngineService periodEngineService;
    @Autowired
    private IRuntimeDataSchemeService runtimeDataSchemeService;
    @Autowired
    private DimCollectionBuildUtil dimCollectionBuildUtil;

    @Override
    public ReturnInfo uploadFiles(FilesUploadInfo filesUploadInfo) {
        ReturnInfo returnInfo = new ReturnInfo();
        ExceptionResult exceptionResult = this.dataStateCheckService.checkDataState(filesUploadInfo.getContext());
        if (null != exceptionResult) {
            throw new DataStatueCheckException(exceptionResult.getErrorCode(), exceptionResult.getData());
        }
        Map<String, AttachmentInfo> fileUploadInfoMap = filesUploadInfo.getFileUploadInfoMap();
        JtableContext jtableContext = filesUploadInfo.getContext();
        DimensionCombinationBuilder dimensionCombinationBuilder = new DimensionCombinationBuilder();
        Map dimensionSet = jtableContext.getDimensionSet();
        for (String key : dimensionSet.keySet()) {
            dimensionCombinationBuilder.setValue(key, (Object)((DimensionValue)dimensionSet.get(key)).getValue());
        }
        DimensionCombination dimensionCombination = dimensionCombinationBuilder.getCombination();
        try {
            HashSet<String> ignore = new HashSet<String>();
            ignore.add("formCondition");
            IDataAccessService dataAccessService = this.dataAccessServiceProvider.getDataAccessService(jtableContext.getTaskKey(), jtableContext.getFormSchemeKey(), ignore);
            IAccessResult readaccess = dataAccessService.writeable(dimensionCombination, jtableContext.getFormKey());
            if (!readaccess.haveAccess()) {
                returnInfo.setCommitError("\u65e0\u6743\u9650");
                return returnInfo;
            }
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
        FieldType fieldType = null;
        try {
            FieldDefine fieldDefine = this.runTimeViewController.queryFieldDefine(filesUploadInfo.getFieldKey());
            fieldType = fieldDefine.getType();
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(jtableContext.getTaskKey());
        boolean openFilepool = this.filePoolService.isOpenFilepool();
        if (null != fieldType && fieldType.equals((Object)FieldType.FIELD_TYPE_PICTURE) || !openFilepool) {
            List<FileUploadInfo> fileUploadInfos = this.constructionFileUploadInfos(fileUploadInfoMap);
            if (StringUtils.isEmpty((String)filesUploadInfo.getGroupKey())) {
                FileUploadContext fileUploadContext = new FileUploadContext();
                fileUploadContext.setFormSchemeKey(jtableContext.getFormSchemeKey());
                fileUploadContext.setFormKey(jtableContext.getFormKey());
                fileUploadContext.setFieldKey(filesUploadInfo.getFieldKey());
                fileUploadContext.setDimensionCombination(dimensionCombination);
                fileUploadContext.setFileUploadInfos(fileUploadInfos);
                fileUploadContext.setGroupKey(filesUploadInfo.getGroupKey());
                fileUploadContext.setDataSchemeKey(taskDefine.getDataScheme());
                fileUploadContext.setTaskKey(jtableContext.getTaskKey());
                try {
                    String groupKey = this.fileOperationService.uploadFiles(fileUploadContext);
                    returnInfo.setMessage(groupKey);
                }
                catch (FieldNameIsSensitiveWordException | FileTypeErrorException e) {
                    logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                    returnInfo.setCommitError(e.getMessage());
                }
            } else {
                FileUploadByGroupKeyContext fileUploadByGroupKeyContext = new FileUploadByGroupKeyContext();
                fileUploadByGroupKeyContext.setFieldKey(filesUploadInfo.getFieldKey());
                fileUploadByGroupKeyContext.setGroupKey(filesUploadInfo.getGroupKey());
                fileUploadByGroupKeyContext.setFileUploadInfos(fileUploadInfos);
                fileUploadByGroupKeyContext.setDataSchemeKey(taskDefine.getDataScheme());
                fileUploadByGroupKeyContext.setTaskKey(jtableContext.getTaskKey());
                fileUploadByGroupKeyContext.setDimensionCombination(dimensionCombination);
                fileUploadByGroupKeyContext.setFormSchemeKey(jtableContext.getFormSchemeKey());
                fileUploadByGroupKeyContext.setFormKey(jtableContext.getFormKey());
                try {
                    this.fileOperationService.uploadFilesByGroupKey(fileUploadByGroupKeyContext);
                    returnInfo.setMessage(filesUploadInfo.getGroupKey());
                }
                catch (FieldNameIsSensitiveWordException | FileTypeErrorException e) {
                    logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                    returnInfo.setCommitError(e.getMessage());
                }
            }
        } else if (null != fieldType && fieldType.equals((Object)FieldType.FIELD_TYPE_FILE) && openFilepool) {
            FileRelateGroupInfo updateInfo;
            List fileKeys;
            FilePoolUploadContext filePoolUploadContext;
            List<FileUploadInfo> fileUploadInfos;
            HashMap<String, AttachmentInfo> fileUploadInfoMapFor2 = new HashMap<String, AttachmentInfo>();
            HashMap<String, AttachmentInfo> fileUploadInfoMapFor3 = new HashMap<String, AttachmentInfo>();
            HashMap<String, AttachmentInfo> fileUploadInfoMapFor4 = new HashMap<String, AttachmentInfo>();
            HashMap<String, AttachmentInfo> fileUploadInfoMapFor5 = new HashMap<String, AttachmentInfo>();
            HashMap<String, AttachmentInfo> fileUploadInfoMapFor6 = new HashMap<String, AttachmentInfo>();
            HashMap<String, AttachmentInfo> fileUploadInfoMapFor7 = new HashMap<String, AttachmentInfo>();
            for (String fileName : fileUploadInfoMap.keySet()) {
                AttachmentInfo attachmentInfo = fileUploadInfoMap.get(fileName);
                if (2 == attachmentInfo.getUploadType()) {
                    fileUploadInfoMapFor2.put(fileName, attachmentInfo);
                    continue;
                }
                if (3 == attachmentInfo.getUploadType()) {
                    fileUploadInfoMapFor3.put(fileName, attachmentInfo);
                    continue;
                }
                if (4 == attachmentInfo.getUploadType()) {
                    fileUploadInfoMapFor4.put(fileName, attachmentInfo);
                    continue;
                }
                if (5 == attachmentInfo.getUploadType()) {
                    fileUploadInfoMapFor5.put(fileName, attachmentInfo);
                    continue;
                }
                if (6 == attachmentInfo.getUploadType()) {
                    fileUploadInfoMapFor6.put(fileName, attachmentInfo);
                    continue;
                }
                if (7 != attachmentInfo.getUploadType()) continue;
                fileUploadInfoMapFor7.put(fileName, attachmentInfo);
            }
            if (!fileUploadInfoMapFor2.isEmpty()) {
                fileUploadInfos = this.constructionFileUploadInfos(fileUploadInfoMapFor2);
                filePoolUploadContext = new FilePoolUploadContext();
                filePoolUploadContext.setTaskKey(jtableContext.getTaskKey());
                filePoolUploadContext.setDimensionCombination(dimensionCombination);
                filePoolUploadContext.setFileUploadInfos(fileUploadInfos);
                List fileInfos = null;
                try {
                    fileInfos = this.filePoolService.uploadFilesLocally(filePoolUploadContext);
                    FileRelateGroupInfo fileRelateGroupInfo = new FileRelateGroupInfo();
                    fileRelateGroupInfo.setDataSchemeKey(taskDefine.getDataScheme());
                    fileRelateGroupInfo.setTaskKey(jtableContext.getTaskKey());
                    fileRelateGroupInfo.setDimensionCombination(dimensionCombination);
                    fileRelateGroupInfo.setFormSchemeKey(jtableContext.getFormSchemeKey());
                    fileRelateGroupInfo.setFormKey(jtableContext.getFormKey());
                    fileRelateGroupInfo.setFieldKey(filesUploadInfo.getFieldKey());
                    fileRelateGroupInfo.setGroupKey(filesUploadInfo.getGroupKey());
                    List fileKeys2 = fileInfos.stream().map(FileInfo::getKey).collect(Collectors.toList());
                    fileRelateGroupInfo.setFileKeys(fileKeys2);
                    String groupKey = this.filePoolService.uploadFilesFromFilePool(fileRelateGroupInfo);
                    returnInfo.setMessage(groupKey);
                }
                catch (FieldNameIsSensitiveWordException | FileTypeErrorException e) {
                    logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                    returnInfo.setCommitError(e.getMessage());
                }
            }
            if (!fileUploadInfoMapFor3.isEmpty()) {
                fileUploadInfos = this.constructionFileUploadInfos(fileUploadInfoMapFor3);
                filePoolUploadContext = new FilePoolUploadContext();
                filePoolUploadContext.setTaskKey(jtableContext.getTaskKey());
                filePoolUploadContext.setDimensionCombination(dimensionCombination);
                filePoolUploadContext.setFileUploadInfos(fileUploadInfos);
                try {
                    this.filePoolService.uploadFilesLocally(filePoolUploadContext);
                }
                catch (FieldNameIsSensitiveWordException | FileTypeErrorException e) {
                    logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                    returnInfo.setCommitError(e.getMessage());
                }
            }
            if (!fileUploadInfoMapFor4.isEmpty()) {
                FileRelateGroupInfo fileRelateGroupInfo = new FileRelateGroupInfo();
                fileRelateGroupInfo.setDataSchemeKey(taskDefine.getDataScheme());
                fileRelateGroupInfo.setTaskKey(jtableContext.getTaskKey());
                fileRelateGroupInfo.setDimensionCombination(dimensionCombination);
                fileRelateGroupInfo.setFormSchemeKey(jtableContext.getFormSchemeKey());
                fileRelateGroupInfo.setFormKey(jtableContext.getFormKey());
                fileRelateGroupInfo.setFieldKey(filesUploadInfo.getFieldKey());
                fileRelateGroupInfo.setGroupKey(filesUploadInfo.getGroupKey());
                fileKeys = fileUploadInfoMapFor4.values().stream().map(AttachmentInfo::getFileKey).collect(Collectors.toList());
                fileRelateGroupInfo.setFileKeys(fileKeys);
                String groupKey = this.filePoolService.uploadFilesFromFilePool(fileRelateGroupInfo);
                returnInfo.setMessage(groupKey);
            }
            if (!fileUploadInfoMapFor5.isEmpty()) {
                CommonParamsDTO params = new CommonParamsDTO();
                params.setDataSchemeKey(taskDefine.getDataScheme());
                params.setTaskKey(taskDefine.getKey());
                for (String fileName : fileUploadInfoMapFor5.keySet()) {
                    AttachmentInfo attachmentInfo = (AttachmentInfo)fileUploadInfoMapFor5.get(fileName);
                    this.filePoolService.changeFileInfoAfterUpload(attachmentInfo.getFileKey(), attachmentInfo.getFileSecret(), attachmentInfo.getFileCategory(), params);
                }
                returnInfo.setMessage(filesUploadInfo.getGroupKey());
            }
            if (!fileUploadInfoMapFor6.isEmpty()) {
                updateInfo = new FileRelateGroupInfo();
                updateInfo.setDataSchemeKey(taskDefine.getDataScheme());
                updateInfo.setTaskKey(jtableContext.getTaskKey());
                updateInfo.setDimensionCombination(dimensionCombination);
                updateInfo.setFormSchemeKey(jtableContext.getFormSchemeKey());
                updateInfo.setFormKey(jtableContext.getFormKey());
                updateInfo.setFieldKey(filesUploadInfo.getFieldKey());
                updateInfo.setGroupKey(filesUploadInfo.getGroupKey());
                fileKeys = fileUploadInfoMapFor6.values().stream().map(AttachmentInfo::getFileKey).collect(Collectors.toList());
                updateInfo.setFileKeys(fileKeys);
                this.filePoolService.updateCreateTime(updateInfo);
                FileRelateGroupInfo fileRelateGroupInfo = new FileRelateGroupInfo();
                fileRelateGroupInfo.setDataSchemeKey(taskDefine.getDataScheme());
                fileRelateGroupInfo.setTaskKey(jtableContext.getTaskKey());
                fileRelateGroupInfo.setDimensionCombination(dimensionCombination);
                fileRelateGroupInfo.setFormSchemeKey(jtableContext.getFormSchemeKey());
                fileRelateGroupInfo.setFormKey(jtableContext.getFormKey());
                fileRelateGroupInfo.setFieldKey(filesUploadInfo.getFieldKey());
                fileRelateGroupInfo.setGroupKey(filesUploadInfo.getGroupKey());
                fileRelateGroupInfo.setFileKeys(fileKeys);
                String groupKey = this.filePoolService.uploadFilesFromFilePool(fileRelateGroupInfo);
                returnInfo.setMessage(groupKey);
            }
            if (!fileUploadInfoMapFor7.isEmpty()) {
                updateInfo = new FileRelateGroupInfo();
                updateInfo.setDataSchemeKey(taskDefine.getDataScheme());
                updateInfo.setTaskKey(jtableContext.getTaskKey());
                updateInfo.setDimensionCombination(dimensionCombination);
                updateInfo.setFormSchemeKey(jtableContext.getFormSchemeKey());
                updateInfo.setFormKey(jtableContext.getFormKey());
                updateInfo.setFieldKey(filesUploadInfo.getFieldKey());
                updateInfo.setGroupKey(filesUploadInfo.getGroupKey());
                fileKeys = fileUploadInfoMapFor7.values().stream().map(AttachmentInfo::getFileKey).collect(Collectors.toList());
                updateInfo.setFileKeys(fileKeys);
                this.filePoolService.updateCreateTime(updateInfo);
                returnInfo.setMessage(filesUploadInfo.getGroupKey());
            }
        }
        return returnInfo;
    }

    private List<FileUploadInfo> constructionFileUploadInfos(Map<String, AttachmentInfo> fileUploadInfoMap) {
        ArrayList<FileUploadInfo> fileUploadInfos = new ArrayList<FileUploadInfo>();
        try {
            for (String fileName : fileUploadInfoMap.keySet()) {
                AttachmentInfo attachmentInfo = fileUploadInfoMap.get(fileName);
                FileUploadInfo fileUploadInfo = new FileUploadInfo();
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                this.fileUploadOssService.downloadFileFormTemp(attachmentInfo.getOssFileKey(), (OutputStream)outputStream);
                ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
                fileUploadInfo.setFile((InputStream)inputStream);
                ObjectInfo fileInfo = this.fileUploadOssService.getInfo(attachmentInfo.getOssFileKey());
                fileUploadInfo.setName(fileInfo.getName());
                fileUploadInfo.setSize(fileInfo.getSize());
                fileUploadInfo.setFileSecret(attachmentInfo.getFileSecret());
                fileUploadInfo.setCategory(attachmentInfo.getFileCategory());
                fileUploadInfo.setCovered(attachmentInfo.isCovered());
                fileUploadInfo.setFileKey(attachmentInfo.getFileKey());
                fileUploadInfos.add(fileUploadInfo);
            }
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
        return fileUploadInfos;
    }

    @Override
    public SaveFilesResult saveFiles(IAttachmentSaveFilesContext attachmentSaveFilesContext) {
        SaveFilesResult saveFilesResult = new SaveFilesResult(true, "", "");
        try {
            FieldDefine fieldDefine = this.dataDefinitionRuntimeController.queryFieldDefine(attachmentSaveFilesContext.getFieldKey());
            DataLinkDefine dataLinkDefine = this.runTimeViewController.queryDataLinkDefine(attachmentSaveFilesContext.getDataLinkKey());
            List fieldKeysInRegion = this.runTimeViewController.getFieldKeysInRegion(dataLinkDefine.getRegionKey());
            List fieldDefines = this.dataDefinitionRuntimeController.queryFieldDefines((Collection)fieldKeysInRegion);
            ArrayList<FieldDefine> fileFieldDefines = new ArrayList<FieldDefine>();
            for (FieldDefine define : fieldDefines) {
                if (define.getType() != FieldType.FIELD_TYPE_FILE) continue;
                fileFieldDefines.add(define);
            }
            FieldDefine floatOrder = null;
            QueryEnvironment queryEnvironment = new QueryEnvironment();
            IDataQuery dataQuery = this.dataAccessProvider.newDataQuery(queryEnvironment);
            for (FieldDefine fileFieldDefine : fileFieldDefines) {
                dataQuery.addColumn(fileFieldDefine);
            }
            if (null != attachmentSaveFilesContext.getSaveType() && attachmentSaveFilesContext.getSaveType().equals("floatSave")) {
                floatOrder = this.dataDefinitionRuntimeController.queryFieldByCodeInTable("FLOATORDER", fieldDefine.getOwnerTableKey());
                dataQuery.addColumn(floatOrder);
            }
            ExecutorContext executorContext = new ExecutorContext(this.dataDefinitionRuntimeController);
            ReportFmlExecEnvironment environment = new ReportFmlExecEnvironment(this.runTimeViewController, this.dataDefinitionRuntimeController, this.entityViewRunTimeController, attachmentSaveFilesContext.getFormscheme());
            executorContext.setEnv((IFmlExecEnvironment)environment);
            DimensionValueSet dimension = DimensionValueSetUtil.getDimensionValueSet(attachmentSaveFilesContext.getDimensionSet());
            dataQuery.setMasterKeys(dimension);
            IDataTable dataTable = dataQuery.executeQuery(executorContext);
            if (dataTable.findRow(dimension) == null) {
                IDataRow dataRow = dataTable.appendRow(dimension);
                dataRow.setValue(fieldDefine, (Object)attachmentSaveFilesContext.getFileGroupKey());
                if (dataTable.commitChanges(true)) {
                    saveFilesResult.setResult(true);
                    String language = DataEntryUtil.getLanguage();
                    if ("zh".equals(language)) {
                        saveFilesResult.setMessage("\u4fdd\u5b58\u6210\u529f");
                    } else {
                        saveFilesResult.setMessage("Saving succeeded");
                    }
                }
            } else if (dataTable.findRow(dimension).getKeyValue(fieldDefine) == null) {
                IDataRow dataRow = dataTable.findRow(dimension);
                HashSet<String> groupKeys = new HashSet<String>();
                for (FieldDefine fileFieldDefine : fileFieldDefines) {
                    String groupKey = dataRow.getValue(fileFieldDefine).getAsString();
                    if (StringUtils.isNotEmpty((String)groupKey) && !groupKeys.contains(groupKey)) {
                        groupKeys.add(groupKey);
                        continue;
                    }
                    if (!StringUtils.isNotEmpty((String)groupKey) || !groupKeys.contains(groupKey)) continue;
                    saveFilesResult.setResult(false);
                    String language = DataEntryUtil.getLanguage();
                    if ("zh".equals(language)) {
                        saveFilesResult.setMessage("\u4fdd\u5b58\u5931\u8d25");
                    } else {
                        saveFilesResult.setMessage("Save failed");
                    }
                    StringBuilder result = new StringBuilder();
                    result.append("\u9644\u4ef6\u578b\u6307\u6807\u503c\u91cd\u590d\uff0c\u65e0\u6cd5\u4fdd\u5b58\uff0c\u91cd\u590d\u503c\uff1a").append(groupKey).append("\uff1b\u91cd\u590d\u6307\u6807\uff1a").append(fileFieldDefine.getKey()).append(",").append(fileFieldDefine.getTitle()).append("\uff1b\u7ef4\u5ea6\uff1a").append(dimension.toString());
                    logger.error(result.toString());
                    return saveFilesResult;
                }
                dataRow.setValue(fieldDefine, (Object)attachmentSaveFilesContext.getFileGroupKey());
                if (dataTable.commitChanges(true)) {
                    saveFilesResult.setResult(true);
                    String language = DataEntryUtil.getLanguage();
                    if ("zh".equals(language)) {
                        saveFilesResult.setMessage("\u4fdd\u5b58\u6210\u529f");
                    } else {
                        saveFilesResult.setMessage("Saving succeeded");
                    }
                }
            }
        }
        catch (Exception e) {
            saveFilesResult.setResult(false);
            String language = DataEntryUtil.getLanguage();
            if ("zh".equals(language)) {
                saveFilesResult.setMessage("\u4fdd\u5b58\u5931\u8d25");
            } else {
                saveFilesResult.setMessage("Save failed");
            }
            logger.error(e.getMessage(), e);
        }
        return saveFilesResult;
    }

    @Override
    public ReturnInfo removeAttachmentFiles(IAttachmentChangeFileSecretContext context) {
        int deleteNum = 0;
        if (!"".equals(context.getFileKey())) {
            this.recordLog(context.getFileKey(), context, "\u5355\u5143\u683c\u9644\u4ef6\u5220\u9664");
            String[] fileKeys = context.getFileKey().split(";");
            Set fileKeySet = Arrays.stream(fileKeys).collect(Collectors.toSet());
            AombstoneFileInfo aombstoneFileInfo = new AombstoneFileInfo();
            TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(context.getTask());
            aombstoneFileInfo.setDataSchemeKey(taskDefine.getDataScheme());
            aombstoneFileInfo.setTaskKey(context.getTask());
            aombstoneFileInfo.setFormSchemeKey(context.getFormscheme());
            aombstoneFileInfo.setGroupKey(context.getGroupKey());
            if (this.filePoolService.isOpenFilepool()) {
                HashSet<String> fileKeysNotReferences = new HashSet<String>();
                for (String fileKey : fileKeySet) {
                    List references = this.filePoolService.getReferences(fileKey, taskDefine.getDataScheme());
                    if (null != references && !references.isEmpty()) continue;
                    fileKeysNotReferences.add(fileKey);
                }
                if (!fileKeysNotReferences.isEmpty()) {
                    CommonParamsDTO params = new CommonParamsDTO();
                    params.setDataSchemeKey(taskDefine.getDataScheme());
                    params.setTaskKey(taskDefine.getKey());
                    this.fileOperationService.physicalDeleteFile(fileKeysNotReferences, params);
                }
                fileKeySet.removeAll(fileKeysNotReferences);
                if (!fileKeySet.isEmpty()) {
                    aombstoneFileInfo.setFileKeys(fileKeySet);
                    this.fileOperationService.deleteFile(aombstoneFileInfo);
                }
            } else {
                aombstoneFileInfo.setFileKeys(fileKeySet);
                this.fileOperationService.deleteFile(aombstoneFileInfo);
            }
            deleteNum += fileKeys.length;
        }
        ReturnInfo returnInfo = new ReturnInfo();
        String language = DataEntryUtil.getLanguage();
        if ("zh".equals(language)) {
            returnInfo.setMessage("\u6210\u529f\u5220\u9664" + deleteNum + "\u4e2a\u9644\u4ef6\u3002");
        } else {
            returnInfo.setMessage("Successfully deleted" + deleteNum + "Attachments.");
        }
        return returnInfo;
    }

    @Override
    public ReturnInfo batchDeleteFiles(BatchDownLoadEnclosureInfo context) {
        BatchDeleteFileInfo batchDeleteFileInfo = new BatchDeleteFileInfo();
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(context.getContext().getTaskKey());
        batchDeleteFileInfo.setDataSchemeKey(taskDefine.getDataScheme());
        batchDeleteFileInfo.setFormscheme(context.getContext().getFormSchemeKey());
        DimensionCollection dimensionValueCollection = this.dimCollectionBuildUtil.buildDimensionCollection(context.getContext().getDimensionSet(), context.getContext().getFormSchemeKey());
        batchDeleteFileInfo.setDimensionCollection(dimensionValueCollection);
        batchDeleteFileInfo.setFormKeys(context.getFormKeys());
        this.fileOperationService.batchDeleteFile(batchDeleteFileInfo);
        ReturnInfo returnInfo = new ReturnInfo();
        String language = DataEntryUtil.getLanguage();
        if ("zh".equals(language)) {
            returnInfo.setMessage("\u5220\u9664\u6210\u529f");
        } else {
            returnInfo.setMessage("Delete successfully");
        }
        return returnInfo;
    }

    @Override
    public ReturnInfo updateJtableFilesSecret(IAttachmentChangeFileSecretContext context) {
        ChangeFileSecretInfo changeFileSecretInfo = new ChangeFileSecretInfo();
        changeFileSecretInfo.setFileKey(context.getFileKey());
        changeFileSecretInfo.setFileSecret(context.getFileSecret());
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(context.getTask());
        CommonParamsDTO params = new CommonParamsDTO();
        params.setDataSchemeKey(taskDefine.getDataScheme());
        params.setTaskKey(taskDefine.getKey());
        changeFileSecretInfo.setParams(params);
        this.fileOperationService.changeFileInfo(changeFileSecretInfo);
        ReturnInfo returnInfo = new ReturnInfo();
        String language = DataEntryUtil.getLanguage();
        if ("zh".equals(language)) {
            returnInfo.setMessage("\u4fee\u6539\u6210\u529f");
        } else {
            returnInfo.setMessage("Modified successfully");
        }
        return returnInfo;
    }

    @Override
    public ReturnInfo updateFilesCategory(IAttachmentChangeFileCategoryContext context) {
        ChangeFileCategoryInfo changeFileCategoryInfo = new ChangeFileCategoryInfo();
        changeFileCategoryInfo.setFileKey(context.getFileKey());
        changeFileCategoryInfo.setCategory(context.getFileCategory());
        CommonParamsDTO commonParamsDTO = new CommonParamsDTO();
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(context.getTask());
        commonParamsDTO.setDataSchemeKey(taskDefine.getDataScheme());
        commonParamsDTO.setTaskKey(taskDefine.getKey());
        changeFileCategoryInfo.setParams(commonParamsDTO);
        this.filePoolService.changeFileCategory(changeFileCategoryInfo);
        ReturnInfo returnInfo = new ReturnInfo();
        String language = DataEntryUtil.getLanguage();
        if ("zh".equals(language)) {
            returnInfo.setMessage("\u4fee\u6539\u6210\u529f");
        } else {
            returnInfo.setMessage("Modified successfully");
        }
        return returnInfo;
    }

    @Override
    public void downloadAttachmentFiles(AttachmentDownloadContext context, HttpServletResponse response) throws Exception {
        Map<String, DimensionValue> dimensionSet;
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(context.getTask());
        CommonParamsDTO params = new CommonParamsDTO();
        params.setDataSchemeKey(taskDefine.getDataScheme());
        params.setTaskKey(taskDefine.getKey());
        List<DownloadInfo> downloadInfos = context.getDownloadInfos();
        List references = this.filePoolService.getReferences(downloadInfos.get(0).getFileKey(), taskDefine.getDataScheme());
        if (!references.isEmpty()) {
            IEntityDefine entityDefine = this.entityMetaService.queryEntity(taskDefine.getDw());
            String DWDimensionName = entityDefine.getDimensionName();
            ArrayList<Iterator<DownloadInfo>> accessCatchs = new ArrayList<Iterator<DownloadInfo>>();
            for (DownloadInfo downloadInfo : downloadInfos) {
                String accessCatch = downloadInfo.getDw() + downloadInfo.getFileKey();
                if (accessCatchs.contains(accessCatch)) continue;
                DimensionCombinationBuilder dimensionCombinationBuilder = new DimensionCombinationBuilder();
                Map<String, DimensionValue> dimensionSet2 = context.getDimensionSet();
                for (String key : dimensionSet2.keySet()) {
                    if (key.equals(DWDimensionName)) {
                        dimensionCombinationBuilder.setValue(key, (Object)downloadInfo.getDw());
                        continue;
                    }
                    dimensionCombinationBuilder.setValue(key, (Object)dimensionSet2.get(key).getValue());
                }
                DimensionCombination dimensionCombination = dimensionCombinationBuilder.getCombination();
                HashSet ignore = new HashSet();
                ignore.add("formCondition");
                IDataAccessService dataAccessService = this.dataAccessServiceProvider.getDataAccessService(context.getTask(), context.getFormscheme(), (Set)ignore);
                IAccessResult readaccess = dataAccessService.readable(dimensionCombination, downloadInfo.getFormKey());
                if (!readaccess.haveAccess()) {
                    throw new Exception("\u65e0\u6743\u9650");
                }
                accessCatchs.add((Iterator<DownloadInfo>)((Object)accessCatch));
            }
            List fileKeys = downloadInfos.stream().map(DownloadInfo::getFileKey).collect(Collectors.toList());
            Map baseReferencesMap = this.filePoolService.getBaseReferences(fileKeys, taskDefine.getDataScheme());
            for (DownloadInfo downloadInfo : downloadInfos) {
                boolean isInclude = false;
                List fileBaseRefInfos = (List)baseReferencesMap.get(downloadInfo.getFileKey());
                for (FileBaseRefInfo fileBaseRefInfo : fileBaseRefInfos) {
                    if (!downloadInfo.getDw().equals(fileBaseRefInfo.getDw()) || !downloadInfo.getFormKey().equals(fileBaseRefInfo.getFormKey())) continue;
                    isInclude = true;
                    break;
                }
                if (isInclude) continue;
                throw new Exception("\u6570\u636e\u4e0d\u5339\u914d");
            }
        } else {
            IPeriodEntityAdapter periodAdapter = this.periodEngineService.getPeriodAdapter();
            IPeriodEntity periodEntity = periodAdapter.getPeriodEntity(taskDefine.getDateTime());
            dimensionSet = context.getDimensionSet();
            GregorianCalendar period2Calendar = PeriodUtil.period2Calendar((String)dimensionSet.get(periodEntity.getDimensionName()).getValue());
            Date time = period2Calendar.getTime();
            for (DownloadInfo downloadInfo : downloadInfos) {
                FileInfo fileInfo = this.fileOperationService.getFileInfoByKey(downloadInfo.getFileKey(), params);
                String filepoolKey = fileInfo.getFilepoolKey();
                String dw = filepoolKey.replaceFirst(context.getTask(), "");
                if (!downloadInfo.getDw().equals(dw)) {
                    throw new Exception("\u6570\u636e\u4e0d\u5339\u914d");
                }
                boolean canReadEntity = false;
                try {
                    canReadEntity = this.entityAuthorityService.canReadEntity(taskDefine.getDw(), downloadInfo.getDw(), null, time);
                }
                catch (UnauthorizedEntityException e) {
                    logger.error(e.getMessage(), e);
                }
                if (canReadEntity) continue;
                throw new Exception("\u65e0\u6743\u9650");
            }
        }
        DownLoadFileInfo downLoadFileInfo = new DownLoadFileInfo();
        downLoadFileInfo.setTask(context.getTask());
        DimensionCombinationBuilder dimensionCombinationBuilder = new DimensionCombinationBuilder();
        dimensionSet = context.getDimensionSet();
        for (String key : dimensionSet.keySet()) {
            dimensionCombinationBuilder.setValue(key, (Object)dimensionSet.get(key).getValue());
        }
        DimensionCombination dimensionCombination = dimensionCombinationBuilder.getCombination();
        downLoadFileInfo.setDimensionCombination(dimensionCombination);
        downLoadFileInfo.setFormscheme(context.getFormscheme());
        HashSet<String> fileKeySet = new HashSet<String>();
        for (DownloadInfo downloadInfo : downloadInfos) {
            fileKeySet.add(downloadInfo.getFileKey());
        }
        downLoadFileInfo.setFileKeys(fileKeySet);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        String name = this.fileOperationService.downloadFile(downLoadFileInfo, (OutputStream)outputStream);
        try {
            byte[] bytes = outputStream.toByteArray();
            response.setHeader("Access-Control-Expose-Headers", "Content-disposition");
            if (1 == fileKeySet.size()) {
                FileInfo fileInfo = this.fileOperationService.getFileInfoByKey((String)new ArrayList(fileKeySet).get(0), params);
                if (null != fileInfo) {
                    response.setContentType(FileType.valueOfExtension((String)fileInfo.getExtension()).getContentType());
                } else {
                    response.setContentType(FileType.ZIP.getContentType());
                }
                response.setHeader("Content-disposition", "attachment; filename*=UTF-8''" + URLEncoder.encode(name, "UTF-8").replaceAll("\\+", "%20"));
                response.addHeader("Content-Length", "" + bytes.length);
            } else {
                response.setContentType(FileType.ZIP.getContentType());
                response.setHeader("Content-disposition", "attachment; filename*=UTF-8''" + URLEncoder.encode(name, "UTF-8"));
                response.addHeader("Content-Length", "" + bytes.length);
            }
            response.getOutputStream().write(bytes);
            response.flushBuffer();
        }
        catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public ReturnInfo rename(ReFileNameContext reFileNameContext) {
        ReturnInfo returnInfo = new ReturnInfo();
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(reFileNameContext.getTask());
        CommonParamsDTO params = new CommonParamsDTO();
        params.setDataSchemeKey(taskDefine.getDataScheme());
        params.setTaskKey(taskDefine.getKey());
        FileInfo fileInfo = this.fileOperationService.getFileInfoByKey(reFileNameContext.getFileKey(), params);
        String newName = reFileNameContext.getName() + (StringUtils.isNotEmpty((String)fileInfo.getExtension()) ? fileInfo.getExtension() : "");
        FilePoolAllFileContext filePoolAllFileContext = new FilePoolAllFileContext();
        filePoolAllFileContext.setFilepoolKey(fileInfo.getFilepoolKey());
        List filePoolFiles = this.filePoolService.getFilePoolFiles(filePoolAllFileContext);
        String fileName = this.judgeSameName(filePoolFiles, reFileNameContext.getFileKey(), newName, 1);
        if (!fileName.equals(newName)) {
            returnInfo.setMessage(fileName);
            return returnInfo;
        }
        RenameInfo renameInfo = new RenameInfo();
        renameInfo.setTaskKey(reFileNameContext.getTask());
        renameInfo.setFormSchemeKey(reFileNameContext.getFormSchemeKey());
        renameInfo.setFileKey(reFileNameContext.getFileKey());
        renameInfo.setName(newName);
        this.filePoolService.rename(renameInfo);
        returnInfo.setMessage("success");
        return returnInfo;
    }

    private String judgeSameName(List<FilePoolFiles> filePoolFiles, String fileKey, String newName, int index) {
        for (FilePoolFiles filePoolFile : filePoolFiles) {
            String bracketedNum;
            if (filePoolFile.getFileKey().equals(fileKey) || !newName.equals(filePoolFile.getFileName())) continue;
            String name = "";
            String extension = "";
            int fileNameIndex = newName.lastIndexOf(".");
            if (fileNameIndex >= 0) {
                name = newName.substring(0, newName.indexOf(46));
                extension = newName.substring(newName.indexOf(46), newName.length());
            } else {
                name = newName;
            }
            int i = name.lastIndexOf(40);
            int j = name.lastIndexOf(41);
            if (i != -1 && j != -1 && i < j && (bracketedNum = name.substring(i + 1, j)).matches("[0-9]+")) {
                index = Integer.parseInt(bracketedNum);
                name = name.substring(0, i);
            }
            newName = name + "(" + (index + 1) + ")" + extension;
            return this.judgeSameName(filePoolFiles, fileKey, newName, index + 1);
        }
        return newName;
    }

    @Override
    public ReturnInfo historicalAttachmentClearing(HistoricalAttachmentClearingContext context) {
        ArrayList<String> periods;
        HashMap<String, ArrayList<String>> formSchemeKeyPeriodsMap = new HashMap<String, ArrayList<String>>();
        String startDate = context.getPeriodRegionInfo().split("-")[0];
        String endDate = context.getPeriodRegionInfo().split("-")[1];
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(context.getTask());
        List periodList = this.iPeriodEntityAdapter.getPeriodCodeByDataRegion(taskDefine.getDateTime(), startDate, endDate);
        try {
            for (Object period : periodList) {
                SchemePeriodLinkDefine schemePeriodLinkDefine = this.runTimeViewController.querySchemePeriodLinkByPeriodAndTask((String)period, context.getTask());
                periods = (ArrayList<String>)formSchemeKeyPeriodsMap.get(schemePeriodLinkDefine.getSchemeKey());
                if (null == periods) {
                    periods = new ArrayList<String>();
                    periods.add(schemePeriodLinkDefine.getPeriodKey());
                    formSchemeKeyPeriodsMap.put(schemePeriodLinkDefine.getSchemeKey(), periods);
                    continue;
                }
                periods.add(schemePeriodLinkDefine.getPeriodKey());
            }
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
        HashSet<String> deleteFileKeySet = new HashSet<String>();
        for (String formSchemeKey : formSchemeKeyPeriodsMap.keySet()) {
            periods = (List)formSchemeKeyPeriodsMap.get(formSchemeKey);
            for (String period : periods) {
                SearchContext searchContext = new SearchContext();
                searchContext.setDataSchemeKey(taskDefine.getDataScheme());
                searchContext.setTaskKey(context.getTask());
                searchContext.setFormscheme(formSchemeKey);
                Map<String, DimensionValue> dimensionSet = context.getDimensionSet();
                DimensionValue dataTimeDimValue = new DimensionValue();
                dataTimeDimValue.setName("DATATIME");
                dataTimeDimValue.setValue(period);
                dimensionSet.put("DATATIME", dataTimeDimValue);
                DimensionCollection dimensionValueCollection = this.dimCollectionBuildUtil.buildDimensionCollection(dimensionSet, formSchemeKey);
                searchContext.setDimensionCollection(dimensionValueCollection);
                searchContext.setPage(false);
                RowDataValues search = this.filePoolService.search(searchContext);
                List dateStrs = periodList.stream().map(t -> PeriodUtils.getDateStrFromPeriod((String)t)).collect(Collectors.toList());
                for (RowDataInfo rowData : search.getRowDatas()) {
                    String fileKey = rowData.getFileKey();
                    List references = this.filePoolService.getReferences(fileKey, taskDefine.getDataScheme());
                    List collect = references.stream().map(t -> t.getDate()).collect(Collectors.toList());
                    if (!dateStrs.containsAll(collect)) continue;
                    deleteFileKeySet.add(fileKey);
                }
            }
        }
        CommonParamsDTO params = new CommonParamsDTO();
        params.setDataSchemeKey(taskDefine.getDataScheme());
        params.setTaskKey(taskDefine.getKey());
        this.fileOperationService.physicalDeleteFile(deleteFileKeySet, params);
        ReturnInfo returnInfo = new ReturnInfo();
        returnInfo.setMessage("success");
        return returnInfo;
    }

    @Override
    public ReturnInfo forceDeleteAttachment(ForceDeleteAttachmentContext context) {
        HashSet<String> fileKeySet = new HashSet<String>();
        fileKeySet.add(context.getFileKey());
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(context.getTask());
        CommonParamsDTO params = new CommonParamsDTO();
        params.setDataSchemeKey(taskDefine.getDataScheme());
        params.setTaskKey(taskDefine.getKey());
        this.fileOperationService.physicalDeleteFile(fileKeySet, params);
        AombstoneFileInfo aombstoneFileInfo = new AombstoneFileInfo();
        aombstoneFileInfo.setDataSchemeKey(taskDefine.getDataScheme());
        aombstoneFileInfo.setTaskKey(context.getTask());
        aombstoneFileInfo.setFormSchemeKey(context.getFormSchemeKey());
        aombstoneFileInfo.setGroupKey(context.getGroupKey());
        aombstoneFileInfo.setFileKeys(fileKeySet);
        this.fileOperationService.deleteFile(aombstoneFileInfo);
        ReturnInfo returnInfo = new ReturnInfo();
        returnInfo.setMessage("success");
        return returnInfo;
    }

    @Override
    public ReturnInfo deleteAllNotReferencesAttachment(DelAllNotReferAttachContext context) {
        SearchContext searchContext = new SearchContext();
        searchContext.setTaskKey(context.getTask());
        searchContext.setFormscheme(context.getFormscheme());
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(context.getTask());
        searchContext.setDataSchemeKey(taskDefine.getDataScheme());
        DimensionCollection dimensionValueCollection = this.dimCollectionBuildUtil.buildDimensionCollection(context.getDimensionSet(), context.getFormscheme());
        searchContext.setDimensionCollection(dimensionValueCollection);
        searchContext.setNotReferences(true);
        searchContext.setPage(false);
        RowDataValues search = this.filePoolService.search(searchContext);
        Set fileKeys = search.getRowDatas().stream().map(RowDataInfo::getFileKey).collect(Collectors.toSet());
        CommonParamsDTO params = new CommonParamsDTO();
        params.setDataSchemeKey(taskDefine.getDataScheme());
        params.setTaskKey(taskDefine.getKey());
        this.fileOperationService.physicalDeleteFile(fileKeys, params);
        ReturnInfo returnInfo = new ReturnInfo();
        returnInfo.setMessage("success");
        return returnInfo;
    }

    private void recordLog(String fileKeys, IAttachmentChangeFileSecretContext context, String operationTitle) {
        String[] splits;
        JtableContext jtableContext = new JtableContext();
        jtableContext.setDimensionSet(context.getDimensionSet());
        jtableContext.setTaskKey(context.getTask());
        jtableContext.setFormSchemeKey(context.getFormscheme());
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(context.getTask());
        CommonParamsDTO params = new CommonParamsDTO();
        params.setDataSchemeKey(taskDefine.getDataScheme());
        params.setTaskKey(taskDefine.getKey());
        for (String fileKey : splits = fileKeys.split(";")) {
            FileInfo fileInfo = this.fileOperationService.getFileInfoByKey(fileKey, params);
            if (null != fileInfo) {
                StringBuilder title = new StringBuilder();
                title.append(operationTitle).append(";").append(fileInfo.getName());
                if (this.secretLevelService.secretLevelEnable(jtableContext.getTaskKey()) && StringUtils.isNotEmpty((String)fileInfo.getSecretlevel())) {
                    title.append(";").append(this.secretLevelService.getSecretLevelItem(fileInfo.getSecretlevel()).getTitle());
                }
                this.loggerAspect.log(jtableContext, title.toString());
                continue;
            }
            this.loggerAspect.log(jtableContext, operationTitle);
        }
    }

    @Override
    public void downloadFiles(String dataSchemeCode, List<String> fileKeys, HttpServletResponse response) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        String name = this.downloadFile(dataSchemeCode, fileKeys, outputStream);
        try {
            response.setHeader("Access-Control-Expose-Headers", "Content-disposition");
            if (1 == fileKeys.size()) {
                DataScheme dataScheme = this.runtimeDataSchemeService.getDataSchemeByCode(dataSchemeCode);
                CommonParamsDTO params = new CommonParamsDTO();
                params.setDataSchemeKey(dataScheme.getKey());
                FileInfo fileInfo = this.fileOperationService.getFileInfoByKey(fileKeys.get(0), params);
                if (null != fileInfo) {
                    response.setContentType(FileType.valueOfExtension((String)fileInfo.getExtension()).getContentType());
                    response.setHeader("Content-disposition", "attachment; filename*=UTF-8''" + URLEncoder.encode(name, "UTF-8").replace("\\+", "%20"));
                }
            } else {
                response.setContentType(FileType.ZIP.getContentType());
                response.setHeader("Content-disposition", "attachment; filename*=UTF-8''" + URLEncoder.encode(name, "UTF-8"));
            }
            response.getOutputStream().write(outputStream.toByteArray());
            response.flushBuffer();
        }
        catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }

    private String downloadFile(String dataSchemeCode, List<String> fileKeys, OutputStream outputStream) {
        ArrayList allFileInfos = new ArrayList();
        DataScheme dataScheme = this.runtimeDataSchemeService.getDataSchemeByCode(dataSchemeCode);
        CommonParamsDTO params = new CommonParamsDTO();
        params.setDataSchemeKey(dataScheme.getKey());
        List files = this.fileOperationService.getFileInfoByKeys(fileKeys, params);
        if (null != files && !files.isEmpty()) {
            allFileInfos.addAll(files);
        }
        if (allFileInfos.isEmpty()) {
            return "";
        }
        if (allFileInfos.size() == 1) {
            FileInfo file = (FileInfo)allFileInfos.get(0);
            byte[] databytes = this.fileOperationService.downloadFile(file.getKey(), params);
            if (null != databytes) {
                try {
                    outputStream.write(databytes);
                }
                catch (IOException e) {
                    logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                }
            }
            return file.getName();
        }
        try (ZipOutputStream zos = new ZipOutputStream(outputStream);){
            zos.setEncoding("gb2312");
            for (FileInfo file : allFileInfos) {
                byte[] databytes = null;
                databytes = this.fileOperationService.downloadFile(file.getKey(), params);
                if (null != databytes) {
                    break;
                }
                if (null == databytes) continue;
                String fileName = file.getName();
                try {
                    ByteArrayInputStream swapStream = new ByteArrayInputStream(databytes);
                    Throwable throwable = null;
                    try {
                        BufferedInputStream bis = new BufferedInputStream(swapStream, 10240);
                        Throwable throwable2 = null;
                        try {
                            if (databytes.length <= 0) continue;
                            byte[] bufs = new byte[0x6400000];
                            zos.putNextEntry(new ZipEntry(fileName));
                            int read = 0;
                            while ((read = bis.read(bufs, 0, 10240)) != -1) {
                                zos.write(bufs, 0, read);
                            }
                        }
                        catch (Throwable throwable3) {
                            throwable2 = throwable3;
                            throw throwable3;
                        }
                        finally {
                            if (bis == null) continue;
                            if (throwable2 != null) {
                                try {
                                    bis.close();
                                }
                                catch (Throwable throwable4) {
                                    throwable2.addSuppressed(throwable4);
                                }
                                continue;
                            }
                            bis.close();
                        }
                    }
                    catch (Throwable throwable5) {
                        throwable = throwable5;
                        throw throwable5;
                    }
                    finally {
                        if (swapStream == null) continue;
                        if (throwable != null) {
                            try {
                                swapStream.close();
                            }
                            catch (Throwable throwable6) {
                                throwable.addSuppressed(throwable6);
                            }
                            continue;
                        }
                        swapStream.close();
                    }
                }
                catch (Exception e) {
                    logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                }
            }
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
        return "\u9644\u4ef6.zip";
    }
}

