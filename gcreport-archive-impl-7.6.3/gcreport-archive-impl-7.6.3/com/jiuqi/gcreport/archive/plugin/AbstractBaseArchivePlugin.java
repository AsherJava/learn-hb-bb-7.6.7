/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.jiuqi.bi.util.tuples.Pair
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.DateUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.gcreport.archive.api.scheme.vo.ArchiveConfigFormInfo
 *  com.jiuqi.gcreport.archive.api.scheme.vo.ArchiveContext
 *  com.jiuqi.gcreport.common.util.DimensionUtils
 *  com.jiuqi.gcreport.org.api.vo.OrgToJsonVO
 *  com.jiuqi.gcreport.org.impl.util.internal.GcOrgBaseTool
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.core.context.ContextUser
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.core.exception.BusinessException
 *  com.jiuqi.np.period.DefaultPeriodAdapter
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.nr.attachment.input.AttachmentQueryInfo
 *  com.jiuqi.nr.attachment.service.FileOperationService
 *  com.jiuqi.nr.common.cache.remote.CacheObjectResourceRemote
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.data.access.util.DimCollectionBuildUtil
 *  com.jiuqi.nr.dataentry.attachment.service.IBatchDownLoadAttachment
 *  com.jiuqi.nr.dataentry.bean.BatchDownLoadEnclosureInfo
 *  com.jiuqi.nr.dataentry.bean.ExportData
 *  com.jiuqi.nr.dataentry.bean.ExportParam
 *  com.jiuqi.nr.dataentry.monitor.SimpleAsyncProgressMonitor
 *  com.jiuqi.nr.dataentry.paramInfo.FormReadWriteAccessData
 *  com.jiuqi.nr.dataentry.readwrite.ReadWriteAccessProvider
 *  com.jiuqi.nr.dataentry.readwrite.bean.ReadWriteAccessCacheParams
 *  com.jiuqi.nr.dataentry.readwrite.impl.ReadWriteAccessCacheManager
 *  com.jiuqi.nr.dataentry.service.IFuncExecuteService
 *  com.jiuqi.nr.dataentry.util.Consts$FormAccessLevel
 *  com.jiuqi.nr.datascheme.adjustment.util.AdjustUtils
 *  com.jiuqi.nr.datascheme.api.core.Ordered
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.definition.api.IRunTimeViewController
 *  com.jiuqi.nr.definition.service.IFormSchemeService
 *  com.jiuqi.nr.definition.web.vo.AdjustPeriodVO
 *  com.jiuqi.nr.file.FileAreaService
 *  com.jiuqi.nr.file.FileInfo
 *  com.jiuqi.nr.file.FileService
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  org.springframework.transaction.annotation.Propagation
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.gcreport.archive.plugin;

import com.fasterxml.jackson.core.type.TypeReference;
import com.jiuqi.bi.util.tuples.Pair;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.DateUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.gcreport.archive.api.scheme.vo.ArchiveConfigFormInfo;
import com.jiuqi.gcreport.archive.api.scheme.vo.ArchiveContext;
import com.jiuqi.gcreport.archive.common.ArchiveProperties;
import com.jiuqi.gcreport.archive.common.ArchiveStatusEnum;
import com.jiuqi.gcreport.archive.common.FileType;
import com.jiuqi.gcreport.archive.dao.ArchiveConfigDao;
import com.jiuqi.gcreport.archive.dao.ArchiveInfoDao;
import com.jiuqi.gcreport.archive.entity.ArchiveConfigEO;
import com.jiuqi.gcreport.archive.entity.ArchiveInfoEO;
import com.jiuqi.gcreport.archive.entity.ArchiveLogEO;
import com.jiuqi.gcreport.archive.entity.ArchiveParamData;
import com.jiuqi.gcreport.archive.plugin.ArchivePlugin;
import com.jiuqi.gcreport.archive.util.ArchiveLogUtil;
import com.jiuqi.gcreport.archive.util.ArchiveUploadFieldUtil;
import com.jiuqi.gcreport.archive.util.HashDataUtil;
import com.jiuqi.gcreport.archive.util.sftp.ArchiveSFTPUtil;
import com.jiuqi.gcreport.common.util.DimensionUtils;
import com.jiuqi.gcreport.org.api.vo.OrgToJsonVO;
import com.jiuqi.gcreport.org.impl.util.internal.GcOrgBaseTool;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.core.context.ContextUser;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.core.exception.BusinessException;
import com.jiuqi.np.period.DefaultPeriodAdapter;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.nr.attachment.input.AttachmentQueryInfo;
import com.jiuqi.nr.attachment.service.FileOperationService;
import com.jiuqi.nr.common.cache.remote.CacheObjectResourceRemote;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.data.access.util.DimCollectionBuildUtil;
import com.jiuqi.nr.dataentry.attachment.service.IBatchDownLoadAttachment;
import com.jiuqi.nr.dataentry.bean.BatchDownLoadEnclosureInfo;
import com.jiuqi.nr.dataentry.bean.ExportData;
import com.jiuqi.nr.dataentry.bean.ExportParam;
import com.jiuqi.nr.dataentry.monitor.SimpleAsyncProgressMonitor;
import com.jiuqi.nr.dataentry.paramInfo.FormReadWriteAccessData;
import com.jiuqi.nr.dataentry.readwrite.ReadWriteAccessProvider;
import com.jiuqi.nr.dataentry.readwrite.bean.ReadWriteAccessCacheParams;
import com.jiuqi.nr.dataentry.readwrite.impl.ReadWriteAccessCacheManager;
import com.jiuqi.nr.dataentry.service.IFuncExecuteService;
import com.jiuqi.nr.dataentry.util.Consts;
import com.jiuqi.nr.datascheme.adjustment.util.AdjustUtils;
import com.jiuqi.nr.datascheme.api.core.Ordered;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.definition.api.IRunTimeViewController;
import com.jiuqi.nr.definition.service.IFormSchemeService;
import com.jiuqi.nr.definition.web.vo.AdjustPeriodVO;
import com.jiuqi.nr.file.FileAreaService;
import com.jiuqi.nr.file.FileInfo;
import com.jiuqi.nr.file.FileService;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public abstract class AbstractBaseArchivePlugin
implements ArchivePlugin {
    @Autowired
    private ArchiveConfigDao archiveConfigDao;
    @Autowired
    private ArchiveInfoDao archiveInfoDao;
    @Autowired
    private ArchiveProperties archiveProperties;
    @Autowired
    private IFuncExecuteService funcExecuteService;
    @Autowired
    private CacheObjectResourceRemote cacheObjectResourceRemote;
    @Autowired
    private IBatchDownLoadAttachment batchDownloadAttachments;
    @Autowired
    private FileService fileService;
    @Autowired
    private DimCollectionBuildUtil dimCollectionBuildUtil;
    @Autowired
    private FileOperationService fileOperationService;
    @Autowired
    private IFormSchemeService formSchemeService;
    @Autowired
    private ReadWriteAccessProvider readWriteAccessProvider;
    @Autowired
    private ReadWriteAccessCacheManager readWriteAccessCacheManager;

    @Override
    public String doAction(ArchiveContext context, ArchiveLogEO logEO, List<ArchiveConfigEO> archiveConfigEOS) {
        String startPeriodString = context.getStartPeriodString();
        String endPeriodString = context.getEndPeriodString();
        String orgType = logEO.getOrgType();
        context.setOrgType(orgType);
        if (StringUtils.isEmpty((String)startPeriodString) || StringUtils.isEmpty((String)endPeriodString)) {
            throw new BusinessRuntimeException("\u5f52\u6863\u65f6\u95f4\u8303\u56f4\u4e0d\u80fd\u4e3a\u7a7a");
        }
        if (StringUtils.isEmpty((String)orgType)) {
            throw new BusinessRuntimeException("\u5f52\u6863\u53e3\u5f84\u4e0d\u80fd\u4e3a\u7a7a");
        }
        PeriodWrapper startPeriodWrapper = new PeriodWrapper(startPeriodString);
        PeriodWrapper endPeriodWrapper = new PeriodWrapper(endPeriodString);
        if (startPeriodWrapper.getType() != endPeriodWrapper.getType()) {
            throw new BusinessRuntimeException("\u5f52\u6863\u8d77\u6b62\u65f6\u95f4\u7c7b\u578b\u4e0d\u76f8\u540c");
        }
        if (startPeriodString.compareTo(endPeriodString) > 0) {
            throw new BusinessRuntimeException("\u5f52\u6863\u5f00\u59cb\u65f6\u95f4\u4e0d\u80fd\u5927\u4e8e\u7ed3\u675f\u65f6\u95f4");
        }
        if (CollectionUtils.isEmpty(archiveConfigEOS)) {
            throw new BusinessRuntimeException("\u62a5\u8868\u65b9\u6848[" + context.getFormSchemeKey() + "]\u6ca1\u6709\u8bbe\u7f6e\u5f52\u6863\u62a5\u8868\u8303\u56f4,\u5f52\u6863\u5931\u8d25\uff01");
        }
        String unit = ((DimensionValue)context.getDimensionSet().get("MD_ORG")).getValue();
        DefaultPeriodAdapter defaultPeriodAdapter = new DefaultPeriodAdapter();
        StringBuffer log = new StringBuffer();
        String formSchemeKey = context.getFormSchemeKey();
        Map<Object, Object> adjustCodeMap = new HashMap();
        if (DimensionUtils.isExistAdjust((String)logEO.getTaskId())) {
            List adjustPeriods = this.formSchemeService.queryAdjustPeriods(formSchemeKey);
            adjustCodeMap = adjustPeriods.stream().filter(AdjustUtils::isAdjustData).sorted(Comparator.comparing(Ordered::getOrder, String::compareTo)).map(AdjustPeriodVO::convertToVO).filter(Objects::nonNull).collect(Collectors.groupingBy(AdjustPeriodVO::getPeriod));
        }
        HashSet<String> periodStrSet = new HashSet<String>();
        while (startPeriodWrapper.compareTo((Object)endPeriodWrapper) <= 0) {
            if (!periodStrSet.contains(startPeriodWrapper.toString())) {
                periodStrSet.add(startPeriodWrapper.toString());
                Map dimensionSetMap = context.getDimensionSet();
                DimensionValue dimensionValue = new DimensionValue();
                dimensionValue.setName("DATATIME");
                dimensionValue.setValue(startPeriodWrapper.toString());
                dimensionSetMap.put("DATATIME", dimensionValue);
                ArchiveParamData archiveParamData = new ArchiveParamData(context.getOrgCode(), startPeriodWrapper.toString());
                List adjustPeriodVOS = (List)adjustCodeMap.get(startPeriodWrapper.toString());
                ArrayList<String> adjustCode = new ArrayList<String>();
                if (adjustPeriodVOS != null) {
                    AdjustPeriodVO periodVo = new AdjustPeriodVO();
                    periodVo.setCode("0");
                    periodVo.setOrder("0");
                    adjustPeriodVOS.add(periodVo);
                    Map<String, String> codeOrderMap = adjustPeriodVOS.stream().collect(Collectors.toMap(AdjustPeriodVO::getCode, AdjustPeriodVO::getOrder));
                    for (AdjustPeriodVO vo : adjustPeriodVOS) {
                        if (startPeriodWrapper.toString().equals(startPeriodString) && codeOrderMap.get(context.getStartAdjustCode()).compareTo(vo.getOrder()) > 0 || startPeriodWrapper.toString().equals(endPeriodString) && codeOrderMap.get(context.getEndAdjustCode()).compareTo(vo.getOrder()) < 0) continue;
                        adjustCode.add(vo.getCode());
                    }
                }
                if (adjustCode.size() > 0) {
                    for (String code : adjustCode) {
                        ArchiveContext copContext = new ArchiveContext();
                        BeanUtils.copyProperties(context, copContext);
                        copContext.setDefaultPeriod(startPeriodWrapper.toString());
                        DimensionValue adjustDim = new DimensionValue();
                        adjustDim.setName("ADJUST");
                        adjustDim.setValue(code);
                        dimensionSetMap.put("ADJUST", adjustDim);
                        this.beforeSingleArchive(copContext, startPeriodWrapper, archiveParamData);
                        String archiveResult = this.archive(copContext, logEO, archiveParamData, archiveConfigEOS.get(0));
                        log.append(archiveResult);
                        this.afterSingleArchive(archiveResult, archiveParamData);
                    }
                } else {
                    ArchiveContext copContext = new ArchiveContext();
                    BeanUtils.copyProperties(context, copContext);
                    copContext.setDefaultPeriod(startPeriodWrapper.toString());
                    this.beforeSingleArchive(copContext, startPeriodWrapper, archiveParamData);
                    String archiveResult = this.archive(copContext, logEO, archiveParamData, archiveConfigEOS.get(0));
                    log.append(archiveResult);
                    this.afterSingleArchive(archiveResult, archiveParamData);
                }
            }
            if (defaultPeriodAdapter.nextPeriod(startPeriodWrapper)) continue;
        }
        return StringUtils.isEmpty((String)log.toString()) ? "\u5355\u4f4d" + unit + "\u4e0a\u4f20\u6587\u4ef6\u5230ftp\u6210\u529f\n" : log.toString() + "\n";
    }

    @Transactional(rollbackFor={Exception.class}, propagation=Propagation.REQUIRES_NEW)
    public String archive(ArchiveContext context, ArchiveLogEO logEO, ArchiveParamData archiveParamData, ArchiveConfigEO archiveConfigEO) {
        String defaultPeriod;
        String unit = ((DimensionValue)context.getDimensionSet().get("MD_ORG")).getValue();
        if (ArchiveUploadFieldUtil.duplicateChecking((JtableContext)context, unit, defaultPeriod = ((DimensionValue)context.getDimensionSet().get("DATATIME")).getValue(), context.getOrgType()) || ArchiveUploadFieldUtil.alreadyHasTask((JtableContext)context, unit, defaultPeriod, logEO.getId())) {
            return "\u5355\u4f4d[" + unit + "]\u65f6\u671f[" + defaultPeriod + "],\u5df2\u7ecf\u5f52\u6863\u8fc7\u4e0d\u80fd\u518d\u6b21\u5f52\u6863\uff01";
        }
        ContextUser user = NpContextHolder.getContext().getUser();
        String username = user == null ? "" : user.getName();
        ArchiveInfoEO eo = ArchiveUploadFieldUtil.initArchiveInfoEO(context, unit, defaultPeriod, username, logEO);
        if (ArchiveUploadFieldUtil.hasNotLockedForm(context, archiveConfigEO = this.filterFormsByPermission(context, archiveConfigEO))) {
            String errorMesage = "\u5355\u4f4d[" + unit + "]\u65f6\u671f[" + defaultPeriod + "\u5b58\u5728\u672a\u4e0a\u62a5\u6216\u8005\u672a\u9501\u5b9a\u7684\u62a5\u8868,\u4e0d\u5141\u8bb8\u5f52\u6863\uff01";
            eo.setErrorInfo(errorMesage);
            eo.setRetryCount(this.archiveProperties.getRetryCount());
            eo.setStatus(ArchiveStatusEnum.UPLOAD_FAILED.getStatus());
            eo.setOrgType(logEO.getOrgType());
            this.archiveInfoDao.save(eo);
            return errorMesage;
        }
        try {
            if (ArchiveUploadFieldUtil.formInfoIsNotEmpty(archiveConfigEO.getExcelFormInfos())) {
                this.initEoFileInfo(context, logEO, unit, ".xlsx", eo, username);
                ExportParam excelExportParam = ArchiveUploadFieldUtil.initExportParam((JtableContext)context, ArchiveUploadFieldUtil.listFormKeys(archiveConfigEO.getExcelFormInfos()), true);
                archiveParamData.setExcelFileName(eo.getFileName());
                archiveParamData.addType(FileType.EXCEL);
                this.uploadExportFileToFTP(excelExportParam, eo.getFilePath(), archiveParamData);
            }
            if (ArchiveUploadFieldUtil.formInfoIsNotEmpty(archiveConfigEO.getPdfFormInfos())) {
                ExportParam pdfExportParam = ArchiveUploadFieldUtil.initExportParam((JtableContext)context, ArchiveUploadFieldUtil.listFormKeys(archiveConfigEO.getPdfFormInfos()), false);
                this.initEoFileInfo(context, logEO, unit, ".pdf", eo, username);
                String filePath = eo.getFilePath().contains(";") ? eo.getFilePath().split(";")[1] : eo.getFilePath();
                String[] fileNameSplit = eo.getFileName().split(";");
                archiveParamData.setPdfFileName(fileNameSplit[fileNameSplit.length - 1]);
                archiveParamData.addType(FileType.PDF);
                this.uploadExportFileToFTP(pdfExportParam, filePath, archiveParamData);
            }
            if (ArchiveUploadFieldUtil.formInfoIsNotEmpty(archiveConfigEO.getAttachmentFormInfos())) {
                String taskId = UUIDUtils.newHalfGUIDStr();
                SimpleAsyncProgressMonitor asyncTaskMonitor = new SimpleAsyncProgressMonitor(taskId, this.cacheObjectResourceRemote);
                BatchDownLoadEnclosureInfo batchDownLoadEnclosureInfo = new BatchDownLoadEnclosureInfo();
                Pair<Integer, String> attachInfo = this.initializeAndQueryAttachments(archiveConfigEO, context, batchDownLoadEnclosureInfo);
                this.handleAttachments(attachInfo, (AsyncTaskMonitor)asyncTaskMonitor, batchDownLoadEnclosureInfo, archiveParamData, logEO, unit, eo);
            }
            eo.setStatus(ArchiveStatusEnum.UPLOAD_SUCCESS.getStatus());
        }
        catch (Exception e) {
            if (eo != null) {
                eo.setStatus(ArchiveStatusEnum.UPLOAD_FAILED.getStatus());
                eo.setErrorInfo(ArchiveLogUtil.getExceptionStackStr(e));
                eo.setRetryCount(this.archiveProperties.getRetryCount());
                eo.setOrgType(logEO.getOrgType());
                this.archiveInfoDao.save(eo);
            }
            return "\u5355\u4f4d[" + unit + "]\u65f6\u671f[" + defaultPeriod + "],\u5f52\u6863\u5931\u8d25" + e.getMessage();
        }
        eo.setOrgType(context.getOrgType());
        this.archiveInfoDao.save(eo);
        archiveParamData.setArchiveInfoId(eo.getId());
        return "";
    }

    private Pair<Integer, String> initializeAndQueryAttachments(ArchiveConfigEO archiveConfigEO, ArchiveContext context, BatchDownLoadEnclosureInfo batchDownLoadEnclosureInfo) {
        String attachmentDownloadKey = UUIDUtils.newHalfGUIDStr();
        batchDownLoadEnclosureInfo.setContext((JtableContext)context);
        batchDownLoadEnclosureInfo.setDownLoadKey(attachmentDownloadKey);
        List attachmentFormInfos = (List)JsonUtils.readValue((String)archiveConfigEO.getAttachmentFormInfos(), (TypeReference)new TypeReference<List<ArchiveConfigFormInfo>>(){});
        List formKeys = attachmentFormInfos.stream().map(ArchiveConfigFormInfo::getFormKey).collect(Collectors.toList());
        batchDownLoadEnclosureInfo.setFormKeys(formKeys);
        AttachmentQueryInfo attachmentQueryInfo = new AttachmentQueryInfo();
        attachmentQueryInfo.setTask(context.getTaskKey());
        attachmentQueryInfo.setDataSchemeKey(((IRunTimeViewController)SpringContextUtils.getBean(IRunTimeViewController.class)).getTask(context.getTaskKey()).getDataScheme());
        attachmentQueryInfo.setFormKey(formKeys);
        attachmentQueryInfo.setPage(false);
        attachmentQueryInfo.setFormscheme(context.getFormSchemeKey());
        DimensionCollection fixedDimensionValues = this.dimCollectionBuildUtil.buildDimensionCollection(context.getDimensionSet(), context.getFormSchemeKey());
        attachmentQueryInfo.setDimensionCollection(fixedDimensionValues);
        Integer totalAttachmentNumber = this.fileOperationService.searchFile(attachmentQueryInfo).getTotalNumber();
        return new Pair((Object)totalAttachmentNumber, (Object)attachmentDownloadKey);
    }

    private ArchiveConfigEO filterFormsByPermission(ArchiveContext context, ArchiveConfigEO originArchiveConfigEO) {
        HashSet<String> formSet = new HashSet<String>();
        ArchiveConfigEO archiveConfigEO = new ArchiveConfigEO();
        BeanUtils.copyProperties((Object)originArchiveConfigEO, (Object)archiveConfigEO);
        List excelFormInfos = (List)JsonUtils.readValue((String)archiveConfigEO.getExcelFormInfos(), (TypeReference)new TypeReference<List<ArchiveConfigFormInfo>>(){});
        List pdfFormInfos = (List)JsonUtils.readValue((String)archiveConfigEO.getPdfFormInfos(), (TypeReference)new TypeReference<List<ArchiveConfigFormInfo>>(){});
        for (ArchiveConfigFormInfo archiveConfigFormInfo : excelFormInfos) {
            formSet.add(archiveConfigFormInfo.getFormKey());
        }
        for (ArchiveConfigFormInfo archiveConfigFormInfo : pdfFormInfos) {
            formSet.add(archiveConfigFormInfo.getFormKey());
        }
        ArrayList formList = new ArrayList(formSet);
        ReadWriteAccessCacheParams readWriteAccessCacheParams = new ReadWriteAccessCacheParams((JtableContext)context, formList, Consts.FormAccessLevel.FORM_READ);
        this.readWriteAccessCacheManager.initCache(readWriteAccessCacheParams);
        FormReadWriteAccessData formReadWriteAccessData = new FormReadWriteAccessData((JtableContext)context, Consts.FormAccessLevel.FORM_READ, formList);
        FormReadWriteAccessData accessForms = this.readWriteAccessProvider.getAccessForms(formReadWriteAccessData, this.readWriteAccessCacheManager);
        HashSet accessFormKeysSet = new HashSet(accessForms.getFormKeys());
        if (!excelFormInfos.isEmpty()) {
            List filteredExcelFormInfos = excelFormInfos.stream().filter(info -> accessFormKeysSet.contains(info.getFormKey())).collect(Collectors.toList());
            archiveConfigEO.setExcelFormInfos(JsonUtils.writeValueAsString(filteredExcelFormInfos));
        }
        if (!pdfFormInfos.isEmpty()) {
            List filteredPdfFormInfos = pdfFormInfos.stream().filter(info -> accessFormKeysSet.contains(info.getFormKey())).collect(Collectors.toList());
            archiveConfigEO.setPdfFormInfos(JsonUtils.writeValueAsString(filteredPdfFormInfos));
        }
        return archiveConfigEO;
    }

    public void handleAttachments(Pair<Integer, String> attachInfo, AsyncTaskMonitor asyncTaskMonitor, BatchDownLoadEnclosureInfo batchDownLoadEnclosureInfo, ArchiveParamData archiveParamData, ArchiveLogEO logEO, String unit, ArchiveInfoEO eo) throws IOException {
        if ((Integer)attachInfo.get_0() <= 0) {
            archiveParamData.setAttachCount(0);
            return;
        }
        try {
            this.batchDownloadAttachments.batchDownloadAttachments(batchDownLoadEnclosureInfo, asyncTaskMonitor);
        }
        catch (Exception e) {
            throw new BusinessException("\u6253\u5305\u9644\u4ef6\u5931\u8d25");
        }
        Object downLoadFileKey = this.cacheObjectResourceRemote.find(attachInfo.get_1());
        FileAreaService fileAreaService = this.fileService.tempArea();
        FileInfo fileInfo = fileAreaService.getInfo((String)downLoadFileKey);
        if (fileInfo == null) {
            return;
        }
        byte[] bytes = fileAreaService.download((String)downLoadFileKey);
        archiveParamData.setAttachCount((Integer)attachInfo.get_0());
        String hashAlgorithm = this.archiveProperties.getHashAlgorithm();
        archiveParamData.setAttachmentDigitalDigest(HashDataUtil.hashData(bytes, hashAlgorithm));
        ByteArrayInputStream stream = new ByteArrayInputStream(bytes);
        File tempDir = FileUtils.getTempDirectory();
        File unzippedDir = new File(tempDir, "unzipped_" + System.currentTimeMillis());
        unzippedDir.mkdirs();
        try {
            ArchiveSFTPUtil.unzip(stream, unzippedDir);
        }
        catch (Exception e) {
            throw new BusinessRuntimeException("\u89e3\u538b\u9644\u4ef6\u5931\u8d25", (Throwable)e);
        }
        unit = this.getMappingCode(unit);
        String baseFilePath = this.archiveProperties.getFtpPathPrefix().endsWith("/") ? this.archiveProperties.getFtpPathPrefix() + "gcreport/" + unit + "/" + DateUtils.format((Date)logEO.getCreateDate(), (String)"yyyy-MM-dd-HH-mm-ss") + "/" : this.archiveProperties.getFtpPathPrefix() + "/gcreport/" + unit + "/" + DateUtils.format((Date)logEO.getCreateDate(), (String)"yyyy-MM-dd-HH-mm-ss") + "/";
        ArrayList<File> allFiles = new ArrayList<File>();
        this.findAllFiles(unzippedDir, allFiles);
        for (File file : allFiles) {
            String relativePath = unzippedDir.toURI().relativize(file.toURI()).getPath();
            String filePath = baseFilePath + relativePath;
            FileInputStream fileStream = new FileInputStream(file);
            ArchiveUploadFieldUtil.upload(fileStream, filePath, (int)file.length(), this.archiveProperties.isSFTP());
            archiveParamData.addAttachmentFile(file.getName(), filePath, (int)file.length(), HashDataUtil.hashData(FileUtils.readFileToByteArray(file), hashAlgorithm));
        }
        eo.setFilePath(archiveParamData.getAllFilePaths());
        eo.setFileName(archiveParamData.getAllFileNames());
    }

    private void findAllFiles(File dir, List<File> allFiles) {
        for (File file : dir.listFiles()) {
            if (file.isDirectory()) {
                this.findAllFiles(file, allFiles);
                continue;
            }
            allFiles.add(file);
        }
    }

    private String getMappingCode(String unit) {
        OrgToJsonVO orgByCode = GcOrgBaseTool.getInstance().getOrgByCode(unit);
        if (orgByCode == null) {
            return unit;
        }
        Object qydmObj = orgByCode.getFieldValue("QYDM");
        if (qydmObj == null || StringUtils.isEmpty((String)qydmObj.toString())) {
            return unit;
        }
        return qydmObj.toString();
    }

    protected abstract void initEoFileInfo(ArchiveContext var1, ArchiveLogEO var2, String var3, String var4, ArchiveInfoEO var5, String var6);

    private void uploadExportFileToFTP(ExportParam exportParam, String remoteFileName, ArchiveParamData archiveParamData) throws IOException {
        ExportData result;
        if ("EXPORT_PDF".equals(exportParam.getType())) {
            exportParam.setPrintCatalog(true);
        }
        if ((result = this.funcExecuteService.export(exportParam)) == null || result.getData() == null) {
            throw new BusinessRuntimeException("\u6ca1\u6709\u751f\u6210\u5f52\u6863\u6587\u4ef6\uff01");
        }
        String hashAlgorithm = this.archiveProperties.getHashAlgorithm();
        String hashResult = HashDataUtil.hashData(result.getData(), hashAlgorithm);
        if (archiveParamData == null) {
            return;
        }
        ByteArrayInputStream stream = new ByteArrayInputStream(result.getData());
        ArchiveUploadFieldUtil.upload(stream, remoteFileName, result.getData().length, this.archiveProperties.isSFTP());
        if ("EXPORT_EXCEL".equals(exportParam.getType())) {
            archiveParamData.setExcelFileDigitalDigest(hashResult);
            archiveParamData.setExcelFileSize(StringUtils.toViewString((Object)result.getData().length));
            archiveParamData.setExcelFileUrl(remoteFileName);
        } else {
            archiveParamData.setPdfFileDigitalDigest(hashResult);
            archiveParamData.setPdfFileSize(StringUtils.toViewString((Object)result.getData().length));
            archiveParamData.setPdfFileUrl(remoteFileName);
        }
    }

    @Override
    public boolean reuploadArchive(ArchiveContext context, String filePath, StringBuffer reuploadLog) {
        try {
            List<ArchiveConfigEO> archiveConfigEOS = this.archiveConfigDao.queryBySchemeId(context.getFormSchemeKey());
            String[] filePaths = filePath.split(";");
            if (CollectionUtils.isEmpty(archiveConfigEOS)) {
                return false;
            }
            ArchiveConfigEO archiveConfigEO = archiveConfigEOS.get(0);
            if (!StringUtils.isEmpty((String)archiveConfigEOS.get(0).getExcelFormInfos())) {
                ExportParam excelExportParam = ArchiveUploadFieldUtil.initExportParam((JtableContext)context, ArchiveUploadFieldUtil.listFormKeys(archiveConfigEO.getExcelFormInfos()), true);
                this.uploadExportFileToFTP(excelExportParam, filePaths[0], null);
            }
            if (!StringUtils.isEmpty((String)archiveConfigEOS.get(0).getPdfFormInfos())) {
                ExportParam pdfExportParam = ArchiveUploadFieldUtil.initExportParam((JtableContext)context, ArchiveUploadFieldUtil.listFormKeys(archiveConfigEO.getPdfFormInfos()), false);
                this.uploadExportFileToFTP(pdfExportParam, filePaths[1], null);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            reuploadLog.append(e.getMessage());
            return false;
        }
        return true;
    }
}

