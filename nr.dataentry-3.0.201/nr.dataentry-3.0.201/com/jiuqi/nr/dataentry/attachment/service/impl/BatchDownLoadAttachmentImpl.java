/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.security.PathUtils
 *  com.jiuqi.bi.security.SecurityContentException
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.np.util.NpRollbackException
 *  com.jiuqi.nr.attachment.input.CommonParamsDTO
 *  com.jiuqi.nr.attachment.message.FileInfo
 *  com.jiuqi.nr.attachment.service.FileOperationService
 *  com.jiuqi.nr.attachment.service.FilePoolService
 *  com.jiuqi.nr.common.cache.remote.CacheObjectResourceRemote
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.data.access.common.AccessLevel$FormAccessLevel
 *  com.jiuqi.nr.data.access.param.AccessFormParam
 *  com.jiuqi.nr.data.access.param.DimensionAccessFormInfo
 *  com.jiuqi.nr.data.access.param.DimensionAccessFormInfo$AccessFormInfo
 *  com.jiuqi.nr.data.access.service.IDataAccessFormService
 *  com.jiuqi.nr.data.access.util.DimCollectionBuildUtil
 *  com.jiuqi.nr.dataentity.service.DataEntityFullService
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.entity.engine.executors.ExecutorContext
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.engine.setting.AuthorityType
 *  com.jiuqi.nr.entity.service.IEntityDataService
 *  com.jiuqi.nr.file.FileInfo
 *  com.jiuqi.nr.file.FileService
 *  com.jiuqi.nr.jtable.params.base.EntityViewData
 *  com.jiuqi.nr.jtable.params.base.FieldData
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  com.jiuqi.nr.jtable.params.input.EntityQueryByKeyInfo
 *  com.jiuqi.nr.jtable.params.output.EntityData
 *  com.jiuqi.nr.jtable.params.output.SecretLevelItem
 *  com.jiuqi.nr.jtable.service.IJtableEntityService
 *  com.jiuqi.nr.jtable.service.IJtableParamService
 *  com.jiuqi.nr.jtable.service.ISecretLevelService
 *  com.jiuqi.nr.jtable.util.DimensionValueSetUtil
 *  com.jiuqi.nr.jtable.util.JsonUtil
 *  javax.annotation.Resource
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.nr.dataentry.attachment.service.impl;

import com.jiuqi.bi.security.PathUtils;
import com.jiuqi.bi.security.SecurityContentException;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.np.util.NpRollbackException;
import com.jiuqi.nr.attachment.input.CommonParamsDTO;
import com.jiuqi.nr.attachment.message.FileInfo;
import com.jiuqi.nr.attachment.service.FileOperationService;
import com.jiuqi.nr.attachment.service.FilePoolService;
import com.jiuqi.nr.common.cache.remote.CacheObjectResourceRemote;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.data.access.common.AccessLevel;
import com.jiuqi.nr.data.access.param.AccessFormParam;
import com.jiuqi.nr.data.access.param.DimensionAccessFormInfo;
import com.jiuqi.nr.data.access.service.IDataAccessFormService;
import com.jiuqi.nr.data.access.util.DimCollectionBuildUtil;
import com.jiuqi.nr.dataentity.service.DataEntityFullService;
import com.jiuqi.nr.dataentry.attachment.service.IBatchDownLoadAttachment;
import com.jiuqi.nr.dataentry.attachment.service.impl.AttachmentZipOut;
import com.jiuqi.nr.dataentry.bean.BatchDownLoadEnclosureInfo;
import com.jiuqi.nr.dataentry.bean.BatchExportData;
import com.jiuqi.nr.dataentry.paramInfo.BatchReturnInfo;
import com.jiuqi.nr.dataentry.service.ExportExcelNameService;
import com.jiuqi.nr.dataentry.util.BatchExportConsts;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.entity.engine.executors.ExecutorContext;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.engine.setting.AuthorityType;
import com.jiuqi.nr.entity.service.IEntityDataService;
import com.jiuqi.nr.file.FileService;
import com.jiuqi.nr.jtable.params.base.EntityViewData;
import com.jiuqi.nr.jtable.params.base.FieldData;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.params.input.EntityQueryByKeyInfo;
import com.jiuqi.nr.jtable.params.output.EntityData;
import com.jiuqi.nr.jtable.params.output.SecretLevelItem;
import com.jiuqi.nr.jtable.service.IJtableEntityService;
import com.jiuqi.nr.jtable.service.IJtableParamService;
import com.jiuqi.nr.jtable.service.ISecretLevelService;
import com.jiuqi.nr.jtable.util.DimensionValueSetUtil;
import com.jiuqi.nr.jtable.util.JsonUtil;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.annotation.Resource;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(rollbackFor={NpRollbackException.class})
@Service
public class BatchDownLoadAttachmentImpl
implements IBatchDownLoadAttachment {
    public static final Logger logger = LoggerFactory.getLogger(BatchDownLoadAttachmentImpl.class);
    @Autowired
    private IRunTimeViewController iRunTimeViewController;
    @Autowired
    private IJtableParamService jtableParamService;
    @Autowired
    private IJtableEntityService jtableEntityService;
    @Autowired
    private CacheObjectResourceRemote cacheObjectResourceRemote;
    @Autowired
    private ISecretLevelService secretLevelService;
    @Autowired
    private ExportExcelNameService exportExcelNameService;
    @Autowired
    private FileService fileService;
    @Resource
    public IDataAccessFormService dataAccessFormService;
    @Autowired
    private FileOperationService fileOperationService;
    @Autowired
    private FilePoolService filePoolService;
    @Autowired
    private IEntityDataService entityDataService;
    @Autowired
    private DataEntityFullService dataEntityFullService;
    @Autowired
    private IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    @Autowired
    private DimCollectionBuildUtil dimCollectionBuildUtil;

    @Override
    public void batchDownloadAttachments(BatchDownLoadEnclosureInfo batchDownLoadEnclosureInfo, AsyncTaskMonitor asyncTaskMonitor) throws Exception {
        BatchReturnInfo batchReturnInfo = new BatchReturnInfo();
        String batchDownLoad = "attach_file_start";
        asyncTaskMonitor.progressAndMessage(0.05, batchDownLoad);
        this.handlerBatchLoadAttachment(batchDownLoadEnclosureInfo);
        JtableContext jtableContext = batchDownLoadEnclosureInfo.getContext();
        TaskDefine taskDefine = this.iRunTimeViewController.queryTaskDefine(jtableContext.getTaskKey());
        CommonParamsDTO params = new CommonParamsDTO();
        params.setDataSchemeKey(taskDefine.getDataScheme());
        params.setTaskKey(taskDefine.getKey());
        Map<String, List<String>> fileFieldMap = batchDownLoadEnclosureInfo.getFileFieldMap();
        HashMap<String, FieldData> keyFieldMap = new HashMap<String, FieldData>();
        if (fileFieldMap.isEmpty()) {
            List allFileFields = this.jtableParamService.getALLFileField(jtableContext.getFormSchemeKey());
            for (FieldData fieldData : allFileFields) {
                if (!StringUtils.isNotEmpty((String)fieldData.getFormKey())) continue;
                List<Object> formFields = null;
                if (fileFieldMap.containsKey(fieldData.getFormKey())) {
                    formFields = fileFieldMap.get(fieldData.getFormKey());
                } else {
                    formFields = new ArrayList();
                    fileFieldMap.put(fieldData.getFormKey(), formFields);
                }
                formFields.add(fieldData.getFieldKey());
                keyFieldMap.put(fieldData.getFieldKey(), fieldData);
            }
        }
        EntityViewData unitEntity = this.jtableParamService.getDwEntity(jtableContext.getFormSchemeKey());
        String attachFileSettle = "attach_file_settle";
        asyncTaskMonitor.progressAndMessage(0.1, attachFileSettle);
        AccessFormParam accessFormParam = new AccessFormParam();
        accessFormParam.setTaskKey(jtableContext.getTaskKey());
        accessFormParam.setFormAccessLevel(AccessLevel.FormAccessLevel.FORM_READ);
        accessFormParam.setFormSchemeKey(jtableContext.getFormSchemeKey());
        DimensionCollection dimensionValueCollection = this.dimCollectionBuildUtil.buildDimensionCollection(jtableContext.getDimensionSet(), jtableContext.getFormSchemeKey());
        accessFormParam.setCollectionMasterKey(dimensionValueCollection);
        accessFormParam.setFormKeys(batchDownLoadEnclosureInfo.getFormKeys());
        DimensionAccessFormInfo batchAccessForms = this.dataAccessFormService.getBatchAccessForms(accessFormParam);
        List acessFormInfos = batchAccessForms.getAccessForms();
        boolean levelEnable = this.secretLevelService.secretLevelEnable(jtableContext.getTaskKey());
        try (AttachmentZipOut zipOut = this.buildZipOut(batchDownLoadEnclosureInfo);){
            for (DimensionAccessFormInfo.AccessFormInfo accessFormInfo : acessFormInfos) {
                Map dimensionValue = accessFormInfo.getDimensions();
                List forms = accessFormInfo.getFormKeys();
                List dimensionSetList = DimensionValueSetUtil.getDimensionSetList((Map)dimensionValue);
                for (Map dimensionSet : dimensionSetList) {
                    HashMap paramCatch = new HashMap();
                    HashMap<String, String> fileKeyPrefixCatch = new HashMap<String, String>();
                    DimensionValueSet dimensionValueSet = DimensionValueSetUtil.getDimensionValueSet((Map)dimensionSet);
                    String unitKey = ((DimensionValue)dimensionSet.get(unitEntity.getDimensionName())).getValue();
                    EntityQueryByKeyInfo entityQueryByKeyInfo = new EntityQueryByKeyInfo();
                    entityQueryByKeyInfo.setEntityViewKey(unitEntity.getKey());
                    entityQueryByKeyInfo.setEntityKey(unitKey);
                    entityQueryByKeyInfo.setContext(jtableContext);
                    EntityData entityData = this.jtableEntityService.queryEntityDataByKey(entityQueryByKeyInfo).getEntity();
                    String unitTitle = entityData.getTitle();
                    String unitCode = entityData.getCode();
                    for (String string : forms) {
                        if (null != batchDownLoadEnclosureInfo.getFormKeys() && 0 != batchDownLoadEnclosureInfo.getFormKeys().size()) {
                            boolean flag = false;
                            for (String index : batchDownLoadEnclosureInfo.getFormKeys()) {
                                if (!string.equals(index)) continue;
                                flag = true;
                                break;
                            }
                            if (!flag) continue;
                        }
                        if (!fileFieldMap.containsKey(string)) continue;
                        List<String> formFields = fileFieldMap.get(string);
                        for (String formField : formFields) {
                            List groupKeys = this.fileOperationService.getGroupKeys(jtableContext.getFormSchemeKey(), dimensionValueSet, string, formField);
                            for (String groupKey : groupKeys) {
                                List fileInfoList = this.filePoolService.getFileInfoByGroup(groupKey, params);
                                fileInfoList = this.fileOperationService.existFile(fileInfoList, jtableContext.getTaskKey(), dimensionValueSet, string, formField, groupKey);
                                for (FileInfo file : fileInfoList) {
                                    ArrayList<FileInfo> fileInfos;
                                    Map fieldFileCatch;
                                    String prefix = file.getName().substring(0, file.getName().lastIndexOf("."));
                                    if (levelEnable && StringUtils.isNotEmpty((String)file.getSecretlevel())) {
                                        SecretLevelItem secretLevelItem = this.secretLevelService.getSecretLevelItem(file.getSecretlevel());
                                        if (!this.secretLevelService.canAccess(secretLevelItem)) continue;
                                        prefix = prefix + this.exportExcelNameService.getSysSeparator() + this.secretLevelService.getSecretLevelItem(file.getSecretlevel()).getTitle();
                                    }
                                    fileKeyPrefixCatch.put(file.getKey(), prefix);
                                    if (paramCatch.containsKey(string)) {
                                        fieldFileCatch = (Map)paramCatch.get(string);
                                        if (fieldFileCatch.containsKey(formField)) {
                                            ((List)fieldFileCatch.get(formField)).add(file);
                                            continue;
                                        }
                                        fileInfos = new ArrayList();
                                        fileInfos.add(file);
                                        fieldFileCatch.put(formField, fileInfos);
                                        continue;
                                    }
                                    fieldFileCatch = new HashMap();
                                    fileInfos = new ArrayList<FileInfo>();
                                    fileInfos.add(file);
                                    fieldFileCatch.put(formField, fileInfos);
                                    paramCatch.put(string, fieldFileCatch);
                                }
                            }
                        }
                    }
                    if (paramCatch.isEmpty()) continue;
                    for (Map.Entry entry : paramCatch.entrySet()) {
                        String formKey = (String)entry.getKey();
                        Map fieldFileInfo = (Map)entry.getValue();
                        FormDefine formDefine = this.iRunTimeViewController.queryFormById(formKey);
                        for (Map.Entry fieldFileEntry : fieldFileInfo.entrySet()) {
                            String fieldKey = (String)fieldFileEntry.getKey();
                            FieldData fieldData = (FieldData)keyFieldMap.get(fieldKey);
                            String fieldTitle = fieldData.getFieldTitle();
                            String fieldCode = fieldData.getFieldCode();
                            HashMap<String, Integer> fileNmaes = new HashMap<String, Integer>();
                            List fileInfos = (List)fieldFileEntry.getValue();
                            for (FileInfo fileInfo : fileInfos) {
                                String prefix = (String)fileKeyPrefixCatch.get(fileInfo.getKey());
                                String suffix = fileInfo.getName().substring(fileInfo.getName().lastIndexOf("."));
                                Integer count = (Integer)fileNmaes.get(prefix);
                                if (count == null) {
                                    count = 0;
                                    fileNmaes.put(prefix, count);
                                } else {
                                    fileNmaes.put(prefix, count + 1);
                                }
                                count = (Integer)fileNmaes.get(prefix);
                                String fileName = count > 0 ? prefix + "(" + count + ")" + suffix : prefix + suffix;
                                String location = "";
                                if (1 == paramCatch.size() && 1 == fieldFileInfo.size()) {
                                    location = batchDownLoadEnclosureInfo.getFileName() + BatchExportConsts.SEPARATOR + unitTitle + "_" + unitCode + BatchExportConsts.SEPARATOR;
                                } else {
                                    location = batchDownLoadEnclosureInfo.getFileName() + BatchExportConsts.SEPARATOR + unitTitle + "_" + unitCode + BatchExportConsts.SEPARATOR + formDefine.getTitle() + "_" + formDefine.getFormCode();
                                    location = 1 == fieldFileInfo.size() ? location + BatchExportConsts.SEPARATOR : location + BatchExportConsts.SEPARATOR + fieldTitle + "_" + fieldCode + BatchExportConsts.SEPARATOR;
                                }
                                ZipOutputStream zipOutputStream = zipOut.getZipOut();
                                zipOutputStream.putNextEntry(new ZipEntry(location + fileName));
                                this.fileOperationService.downloadFile(fileInfo.getKey(), (OutputStream)zipOutputStream, params);
                                zipOutputStream.closeEntry();
                            }
                        }
                    }
                }
            }
            zipOut.getZipOut().finish();
            asyncTaskMonitor.progressAndMessage(0.6, "attach_file_zip_to_file");
            try (FileInputStream uploadInputStream = new FileInputStream(zipOut.getFile());){
                com.jiuqi.nr.file.FileInfo fileInfo = this.fileService.tempArea().uploadTemp(batchDownLoadEnclosureInfo.getFileName() + ".zip", (InputStream)uploadInputStream);
                this.cacheObjectResourceRemote.create((Object)batchDownLoadEnclosureInfo.getDownLoadKey(), (Object)fileInfo.getKey());
            }
        }
        String objectToJson = JsonUtil.objectToJson((Object)batchReturnInfo);
        if (!asyncTaskMonitor.isFinish()) {
            asyncTaskMonitor.finish("attach_file_success_info", (Object)objectToJson);
        }
    }

    private List<IEntityRow> getEntityDataList(String formSchemeKey, Map<String, DimensionValue> dimensionValueSet, String dimensionName) {
        FormSchemeDefine formSchemeDefine = this.iRunTimeViewController.getFormScheme(formSchemeKey);
        try {
            List entityList = this.jtableParamService.getEntityList(formSchemeDefine.getKey());
            for (EntityViewData entityViewData : entityList) {
                if (!dimensionName.equals(entityViewData.getDimensionName())) continue;
                IEntityQuery iEntityQuery = this.entityDataService.newEntityQuery();
                iEntityQuery.setMasterKeys(DimensionValueSetUtil.getDimensionValueSet(dimensionValueSet));
                EntityViewDefine entityViewDefine = entityViewData.getEntityViewDefine();
                iEntityQuery.setEntityView(entityViewDefine);
                iEntityQuery.setAuthorityOperations(AuthorityType.Read);
                IEntityTable iEntityTable = this.dataEntityFullService.executeEntityReader(iEntityQuery, new ExecutorContext(this.dataDefinitionRuntimeController), entityViewDefine, formSchemeKey).getEntityTable();
                return iEntityTable.getAllRows();
            }
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
        return null;
    }

    private void handlerBatchLoadAttachment(BatchDownLoadEnclosureInfo batchDownLoadEnclosureInfo) {
        NpContext context = NpContextHolder.getContext();
        JtableContext jtableContext = batchDownLoadEnclosureInfo.getContext();
        TaskDefine taskDefine = this.iRunTimeViewController.queryTaskDefine(jtableContext.getTaskKey());
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        String formatDate = dateFormat.format(date);
        String tempLocalhost = "";
        String dirUU = UUID.randomUUID().toString();
        tempLocalhost = StringUtils.isNotEmpty((String)batchDownLoadEnclosureInfo.getLocation()) ? batchDownLoadEnclosureInfo.getLocation() : BatchExportConsts.EXPORTDIR;
        String resultLocation = tempLocalhost + BatchExportConsts.SEPARATOR + context.getUser().getName() + BatchExportConsts.SEPARATOR + formatDate + BatchExportConsts.SEPARATOR + dirUU;
        String fileName = taskDefine.getTitle() + "_" + formatDate;
        batchDownLoadEnclosureInfo.setLocation(resultLocation);
        batchDownLoadEnclosureInfo.setFileName(fileName);
    }

    private AttachmentZipOut buildZipOut(BatchDownLoadEnclosureInfo batchDownLoadEnclosureInfo) throws Exception {
        AttachmentZipOut attachmentZipOut = new AttachmentZipOut();
        PathUtils.validatePathManipulation((String)batchDownLoadEnclosureInfo.getLocation());
        File path = new File(FilenameUtils.normalize(batchDownLoadEnclosureInfo.getLocation()));
        if (!path.exists()) {
            path.mkdirs();
        }
        String zipPath = FilenameUtils.normalize(batchDownLoadEnclosureInfo.getLocation() + BatchExportConsts.SEPARATOR + batchDownLoadEnclosureInfo.getFileName() + ".zip");
        PathUtils.validatePathManipulation((String)zipPath);
        File file = new File(zipPath);
        attachmentZipOut.setFile(file);
        ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(file));
        attachmentZipOut.setZipOut(zos);
        return attachmentZipOut;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void exportToZip(List<BatchExportData> datas, BatchDownLoadEnclosureInfo batchDownLoadEnclosureInfo) {
        BufferedInputStream bis = null;
        try {
            PathUtils.validatePathManipulation((String)batchDownLoadEnclosureInfo.getLocation());
        }
        catch (SecurityContentException e) {
            throw new RuntimeException(e);
        }
        File file = new File(FilenameUtils.normalize(batchDownLoadEnclosureInfo.getLocation()));
        if (!file.exists()) {
            file.mkdirs();
        }
        try {
            PathUtils.validatePathManipulation((String)FilenameUtils.normalize(batchDownLoadEnclosureInfo.getLocation() + BatchExportConsts.SEPARATOR + batchDownLoadEnclosureInfo.getFileName() + ".zip"));
        }
        catch (SecurityContentException e) {
            throw new RuntimeException(e);
        }
        try (FileOutputStream fileOutputStream = new FileOutputStream(FilenameUtils.normalize(batchDownLoadEnclosureInfo.getLocation() + BatchExportConsts.SEPARATOR + batchDownLoadEnclosureInfo.getFileName() + ".zip"));
             ZipOutputStream zos = new ZipOutputStream(fileOutputStream);){
            HashMap<String, Integer> filePaths = new HashMap<String, Integer>();
            for (BatchExportData data : datas) {
                byte[] databytes = data.getData().getData();
                if (null == databytes || databytes.length <= 0) continue;
                byte[] bufs = new byte[0xA00000];
                String filePath = data.getLocation() + data.getData().getFileName();
                String prefix = filePath.substring(0, filePath.lastIndexOf("."));
                String suffix = filePath.substring(filePath.lastIndexOf("."));
                if (null == filePaths || filePaths.size() <= 0) {
                    filePaths.put(filePath, 0);
                } else {
                    int i = 0;
                    for (String key : filePaths.keySet()) {
                        ++i;
                        if (filePath.equals(key)) {
                            int j = (Integer)filePaths.get(key) + 1;
                            filePath = prefix + "(" + j + ")" + suffix;
                            filePaths.put(filePath, j);
                            break;
                        }
                        if (i != filePaths.size()) continue;
                        filePaths.put(filePath, 0);
                    }
                }
                PathUtils.validatePathManipulation((String)filePath);
                zos.putNextEntry(new ZipEntry(filePath));
                ByteArrayInputStream swapStream = new ByteArrayInputStream(data.getData().getData());
                bis = new BufferedInputStream(swapStream, 10240);
                int read = 0;
                while ((read = bis.read(bufs, 0, 10240)) != -1) {
                    zos.write(bufs, 0, read);
                }
            }
            zos.flush();
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
        try {
            PathUtils.validatePathManipulation((String)FilenameUtils.normalize(batchDownLoadEnclosureInfo.getLocation() + BatchExportConsts.SEPARATOR + batchDownLoadEnclosureInfo.getFileName() + ".zip"));
        }
        catch (SecurityContentException e) {
            throw new RuntimeException(e);
        }
        File zipFile = new File(FilenameUtils.normalize(batchDownLoadEnclosureInfo.getLocation() + BatchExportConsts.SEPARATOR + batchDownLoadEnclosureInfo.getFileName() + ".zip"));
        try (FileInputStream uploadInputStream = new FileInputStream(zipFile);){
            com.jiuqi.nr.file.FileInfo fileInfo = this.fileService.tempArea().uploadTemp(batchDownLoadEnclosureInfo.getFileName() + ".zip", (InputStream)uploadInputStream);
            this.cacheObjectResourceRemote.create((Object)batchDownLoadEnclosureInfo.getDownLoadKey(), (Object)fileInfo.getKey());
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
        finally {
            if (null != zipFile && !zipFile.delete()) {
                logger.info("\u6587\u4ef6\u5220\u9664\u5931\u8d25");
            }
        }
    }
}

