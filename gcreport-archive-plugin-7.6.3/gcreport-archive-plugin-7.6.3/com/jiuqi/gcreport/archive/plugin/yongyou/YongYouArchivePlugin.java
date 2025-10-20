/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.DateUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.gcreport.archive.api.scheme.vo.ArchiveContext
 *  com.jiuqi.gcreport.archive.common.ArchiveProperties
 *  com.jiuqi.gcreport.archive.common.FileType
 *  com.jiuqi.gcreport.archive.entity.ArchiveAttachFileInfo
 *  com.jiuqi.gcreport.archive.entity.ArchiveInfoEO
 *  com.jiuqi.gcreport.archive.entity.ArchiveLogEO
 *  com.jiuqi.gcreport.archive.entity.ArchiveParamData
 *  com.jiuqi.gcreport.archive.plugin.AbstractBaseArchivePlugin
 *  com.jiuqi.gcreport.archive.util.ArchiveCsvUtil
 *  com.jiuqi.gcreport.archive.util.ArchiveUploadFieldUtil
 *  com.jiuqi.gcreport.common.task.vo.Scheme
 *  com.jiuqi.gcreport.common.task.web.TaskConditionBoxController
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.OrgToJsonVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 *  com.jiuqi.gcreport.org.impl.util.internal.GcOrgBaseTool
 *  com.jiuqi.np.core.context.ContextUser
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.period.PeriodConsts
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.va.domain.org.OrgDTO
 *  com.jiuqi.va.organization.service.OrgDataService
 *  org.springframework.transaction.annotation.Propagation
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.gcreport.archive.plugin.yongyou;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.DateUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.gcreport.archive.api.scheme.vo.ArchiveContext;
import com.jiuqi.gcreport.archive.common.ArchiveProperties;
import com.jiuqi.gcreport.archive.common.FileType;
import com.jiuqi.gcreport.archive.entity.ArchiveAttachFileInfo;
import com.jiuqi.gcreport.archive.entity.ArchiveInfoEO;
import com.jiuqi.gcreport.archive.entity.ArchiveLogEO;
import com.jiuqi.gcreport.archive.entity.ArchiveParamData;
import com.jiuqi.gcreport.archive.plugin.AbstractBaseArchivePlugin;
import com.jiuqi.gcreport.archive.plugin.yongyou.dao.IdocCaptureBillDataAttachDao;
import com.jiuqi.gcreport.archive.plugin.yongyou.dao.IdocCaptureBillDataDao;
import com.jiuqi.gcreport.archive.plugin.yongyou.dao.IdocCaptureReadyCheckDao;
import com.jiuqi.gcreport.archive.plugin.yongyou.entity.IdocCaptureBillAttachEo;
import com.jiuqi.gcreport.archive.plugin.yongyou.entity.IdocCaptureBillDataEo;
import com.jiuqi.gcreport.archive.plugin.yongyou.entity.IdocCaptureReadyCheckEo;
import com.jiuqi.gcreport.archive.plugin.yongyou.enums.YongYouReadyStatus;
import com.jiuqi.gcreport.archive.plugin.yongyou.utils.YongYouPeriodTypeUtil;
import com.jiuqi.gcreport.archive.util.ArchiveCsvUtil;
import com.jiuqi.gcreport.archive.util.ArchiveUploadFieldUtil;
import com.jiuqi.gcreport.common.task.vo.Scheme;
import com.jiuqi.gcreport.common.task.web.TaskConditionBoxController;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.OrgToJsonVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import com.jiuqi.gcreport.org.impl.util.internal.GcOrgBaseTool;
import com.jiuqi.np.core.context.ContextUser;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.period.PeriodConsts;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.va.domain.org.OrgDTO;
import com.jiuqi.va.organization.service.OrgDataService;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
public class YongYouArchivePlugin
extends AbstractBaseArchivePlugin {
    @Autowired
    private ArchiveProperties archiveProperties;
    @Autowired
    private IRunTimeViewController iRunTimeViewController;
    @Autowired
    private IdocCaptureBillDataDao idocCaptureBillDataDao;
    @Autowired
    private IdocCaptureBillDataAttachDao idocCaptureBillDataAttachDao;
    @Autowired
    private OrgDataService orgDataService;
    @Autowired
    private IdocCaptureReadyCheckDao idocCaptureReadyCheckDao;
    public static final String PREPARE_INFO_ID = "prepareInfoId";
    private final Logger logger = LoggerFactory.getLogger(YongYouArchivePlugin.class);

    public String getPluginName() {
        return "\u7528\u53cb\u7535\u5b50\u6863\u6848\u63d2\u4ef6";
    }

    public String getPluginCode() {
        return "YongYou";
    }

    @Transactional(propagation=Propagation.REQUIRES_NEW, rollbackFor={Exception.class})
    public void beforeSingleArchive(ArchiveContext context, PeriodWrapper startPeriodWrapper, ArchiveParamData archiveParamData) {
        IdocCaptureReadyCheckEo idocCaptureReadyCheckEo = new IdocCaptureReadyCheckEo();
        idocCaptureReadyCheckEo.setBbOrgCode(context.getOrgCode());
        OrgDTO gcOrgParam = new OrgDTO();
        gcOrgParam.setCode(context.getOrgCode());
        idocCaptureReadyCheckEo.setOrgName(this.orgDataService.get(gcOrgParam).getName());
        idocCaptureReadyCheckEo.setOrgCode(this.getMappingCode(context.getOrgCode()));
        idocCaptureReadyCheckEo.setTs(DateUtils.format((Date)new Date(), (String)"yyyy-MM-dd HH:mm:ss"));
        idocCaptureReadyCheckEo.setReadyStatus(StringUtils.toViewString((Object)YongYouReadyStatus.PREPARING.getValue()));
        idocCaptureReadyCheckEo.setPeriod(YongYouPeriodTypeUtil.getPeriodTypeName(startPeriodWrapper.getType()));
        PeriodWrapper periodWrapper = new PeriodWrapper(archiveParamData.getDataTime());
        idocCaptureReadyCheckEo.setAccountMonth(StringUtils.toViewString((Object)YongYouPeriodTypeUtil.getMonth(periodWrapper.getPeriod(), periodWrapper.getType())));
        idocCaptureReadyCheckEo.setAccountYear(StringUtils.toViewString((Object)startPeriodWrapper.getYear()));
        idocCaptureReadyCheckEo.setArchiveType(YongYouPeriodTypeUtil.getArchiveType(archiveParamData.getDataTime()));
        idocCaptureReadyCheckEo.setId(UUIDUtils.newUUIDStr());
        idocCaptureReadyCheckEo.setPeriodIndex(startPeriodWrapper.getPeriod());
        this.idocCaptureReadyCheckDao.save(idocCaptureReadyCheckEo);
        HashMap<String, String> extendParams = new HashMap<String, String>();
        extendParams.put(PREPARE_INFO_ID, idocCaptureReadyCheckEo.getId());
        archiveParamData.setExtendParam(extendParams);
    }

    @Transactional(propagation=Propagation.REQUIRES_NEW, rollbackFor={Exception.class})
    public void afterSingleArchive(String archiveResult, ArchiveParamData archiveParamData) {
        String prepareInfoId = StringUtils.toViewString(archiveParamData.getExtendParam().get(PREPARE_INFO_ID));
        IdocCaptureReadyCheckEo idocCaptureReadyCheckEo = this.idocCaptureReadyCheckDao.get(prepareInfoId);
        if (idocCaptureReadyCheckEo == null) {
            return;
        }
        String beforeMappingOrgCode = idocCaptureReadyCheckEo.getBbOrgCode();
        String afterMappingOrgCode = idocCaptureReadyCheckEo.getOrgCode();
        String orgName = idocCaptureReadyCheckEo.getOrgName();
        if (!"".equals(archiveResult)) {
            idocCaptureReadyCheckEo.setErrorMsg(archiveResult);
            idocCaptureReadyCheckEo.setTs(DateUtils.format((Date)new Date(), (String)"yyyy-MM-dd HH:mm:ss"));
            idocCaptureReadyCheckEo.setId(prepareInfoId);
            this.idocCaptureReadyCheckDao.update(idocCaptureReadyCheckEo);
            this.logger.error(archiveResult);
            return;
        }
        idocCaptureReadyCheckEo.setReadyStatus(StringUtils.toViewString((Object)YongYouReadyStatus.COMPLETED.getValue()));
        idocCaptureReadyCheckEo.setTs(DateUtils.format((Date)new Date(), (String)"yyyy-MM-dd HH:mm:ss"));
        idocCaptureReadyCheckEo.setId(prepareInfoId);
        this.idocCaptureReadyCheckDao.update(idocCaptureReadyCheckEo);
        IdocCaptureBillDataEo idocCaptureBillDataEo = new IdocCaptureBillDataEo();
        BeanUtils.copyProperties(archiveParamData, (Object)idocCaptureBillDataEo);
        idocCaptureBillDataEo.setBbOrgCode(beforeMappingOrgCode);
        idocCaptureBillDataEo.setOrgCode(afterMappingOrgCode);
        idocCaptureBillDataEo.setOrgName(orgName);
        idocCaptureBillDataEo.setDocType(YongYouPeriodTypeUtil.getArchiveType(archiveParamData.getDataTime()));
        idocCaptureBillDataEo.setStoreType(2);
        idocCaptureBillDataEo.setTs(DateUtils.format((Date)new Date(), (String)"yyyy-MM-dd HH:mm:ss"));
        PeriodWrapper periodWrapper = new PeriodWrapper(archiveParamData.getDataTime());
        idocCaptureBillDataEo.setPeriod(YongYouPeriodTypeUtil.getPeriodTypeName(periodWrapper.getType()));
        idocCaptureBillDataEo.setPeriodIndex(periodWrapper.getPeriod());
        idocCaptureBillDataEo.setTitle(YongYouPeriodTypeUtil.getPeriodTitleName(periodWrapper.getType()));
        idocCaptureBillDataEo.setPk(archiveParamData.getArchiveInfoId());
        idocCaptureBillDataEo.setAbstracts(orgName);
        if (archiveParamData.getOutputType().get(0) == FileType.EXCEL) {
            idocCaptureBillDataEo.setFileSize(archiveParamData.getExcelFileSize());
            idocCaptureBillDataEo.setFileUrl(archiveParamData.getExcelFileUrl());
            idocCaptureBillDataEo.setSrcFileName(archiveParamData.getExcelFileName());
            idocCaptureBillDataEo.setDigitalDigest(archiveParamData.getExcelFileDigitalDigest());
        } else {
            idocCaptureBillDataEo.setFileSize(archiveParamData.getPdfFileSize());
            idocCaptureBillDataEo.setFileUrl(archiveParamData.getPdfFileUrl());
            idocCaptureBillDataEo.setSrcFileName(archiveParamData.getPdfFileName());
            idocCaptureBillDataEo.setDigitalDigest(archiveParamData.getPdfFileDigitalDigest());
        }
        ContextUser user = NpContextHolder.getContext().getUser();
        String username = user == null ? "" : user.getName();
        idocCaptureBillDataEo.setOwner(username);
        idocCaptureBillDataEo.setDocDate(DateUtils.format((Date)new Date(), (String)DateUtils.DEFAULT_DATE_FORMAT));
        idocCaptureBillDataEo.setBillMaker(username);
        idocCaptureBillDataEo.setId(UUIDUtils.newUUIDStr());
        idocCaptureBillDataEo.setAccountYear(StringUtils.toViewString((Object)periodWrapper.getYear()));
        idocCaptureBillDataEo.setAccountMonth(YongYouPeriodTypeUtil.getMonth(periodWrapper.getPeriod(), periodWrapper.getType()));
        if (idocCaptureBillDataEo.getAttachCount() == null) {
            idocCaptureBillDataEo.setAttachCount(0);
        }
        this.idocCaptureBillDataDao.save(idocCaptureBillDataEo);
        if (archiveParamData.getAttachCount() != null && archiveParamData.getAttachCount() >= 1) {
            for (ArchiveAttachFileInfo fileInfo : archiveParamData.getAttachmentFiles()) {
                IdocCaptureBillAttachEo idocCaptureBillAttachEo = new IdocCaptureBillAttachEo();
                idocCaptureBillAttachEo.setId(UUIDUtils.newUUIDStr());
                idocCaptureBillAttachEo.setDocType(YongYouPeriodTypeUtil.getArchiveType(archiveParamData.getDataTime()));
                idocCaptureBillAttachEo.setTs(DateUtils.format((Date)new Date(), (String)"yyyy-MM-dd HH:mm:ss"));
                idocCaptureBillAttachEo.setAttachType(0);
                idocCaptureBillAttachEo.setFileName(fileInfo.getFileName());
                idocCaptureBillAttachEo.setFileUrl(fileInfo.getFileUrl());
                idocCaptureBillAttachEo.setFileSize(fileInfo.getFileSize());
                idocCaptureBillAttachEo.setBillPk(archiveParamData.getArchiveInfoId());
                idocCaptureBillAttachEo.setDigitalDigest(fileInfo.getDigitalDigest());
                idocCaptureBillAttachEo.setBbOrgCode(beforeMappingOrgCode);
                idocCaptureBillAttachEo.setOrgCode(afterMappingOrgCode);
                idocCaptureBillAttachEo.setOrgName(orgName);
                this.idocCaptureBillDataAttachDao.save(idocCaptureBillAttachEo);
            }
        }
    }

    protected void initEoFileInfo(ArchiveContext context, ArchiveLogEO logEO, String unit, String fileSuffix, ArchiveInfoEO eo, String username) {
        Map dimensionSet = context.getDimensionSet();
        String orgType = logEO.getOrgType();
        StringBuffer filePath = new StringBuffer();
        StringBuffer fileName = new StringBuffer();
        String dataTime = ((DimensionValue)dimensionSet.get("DATATIME")).getValue();
        YearPeriodObject yp = new YearPeriodObject(null, dataTime);
        GcOrgCenterService tool = GcOrgPublicTool.getInstance((String)orgType, (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yp);
        GcOrgCacheVO unitVO = tool.getOrgByCode(unit);
        if (unitVO == null) {
            throw new BusinessRuntimeException("\u5355\u4f4d[" + unit + "]\u5728\u65f6\u671f[" + dataTime + "]\u4e0b\u4e0d\u5b58\u5728\uff01");
        }
        String orgCode = this.getMappingCode(unit);
        PeriodWrapper periodWrapper = new PeriodWrapper(dataTime);
        String bblx = PeriodConsts.typeToTitle((int)periodWrapper.getType()) + "\u62a5";
        FormSchemeDefine formScheme = this.iRunTimeViewController.getFormScheme(logEO.getSchemeId());
        String formSchemeTitle = formScheme.getTitle();
        filePath.append(this.archiveProperties.getFtpPathPrefix()).append("gcreport/").append(orgCode).append("/").append(DateUtils.format((Date)logEO.getCreateDate(), (String)"yyyy-MM-dd-HH-mm-ss")).append("/");
        fileName.append(formSchemeTitle).append("-").append(orgCode).append("-").append("CNY").append("-").append(dataTime).append("-").append(unitVO.getTitle()).append("-").append(bblx);
        fileName.append(fileSuffix);
        filePath.append(fileName);
        eo.setFilePath(StringUtils.isEmpty((String)eo.getFilePath()) ? filePath.toString() : eo.getFilePath() + ";" + filePath.toString());
        eo.setFileName(StringUtils.isEmpty((String)eo.getFileName()) ? fileName.toString() : eo.getFileName() + ";" + fileName.toString());
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

    private void uploadCsvFile(ArchiveLogEO logEO, List<ArchiveInfoEO> archiveInfoEOList) {
        Map<String, List<ArchiveInfoEO>> unit2ArchiveInfoMap = archiveInfoEOList.stream().collect(Collectors.groupingBy(item -> this.getMappingCode(item.getUnitId())));
        String[] header = new String[]{"UUID", "PDF\u6587\u4ef6\u540d", "\u7ec4\u7ec7\u673a\u6784\u4ee3\u7801", "\u7ec4\u7ec7\u673a\u6784\u540d\u79f0", "\u4f1a\u8ba1\u5e74\u6708", "\u62a5\u8868\u65b9\u6848\u540d\u79f0", "\u5e01\u79cd\u4ee3\u7801", "\u5f52\u6863\u4eba", "\u5f52\u6863\u65f6\u95f4", "\u6863\u6848\u63cf\u8ff0", "\u66f4\u65b0\u6807\u5fd7"};
        for (String mappingUnitCode : unit2ArchiveInfoMap.keySet()) {
            List<ArchiveInfoEO> unitArchiveInfoEOList = unit2ArchiveInfoMap.get(mappingUnitCode);
            ArrayList<String[]> rowDataList = new ArrayList<String[]>(unitArchiveInfoEOList.size());
            for (ArchiveInfoEO archiveInfoEO : unitArchiveInfoEOList) {
                FormSchemeDefine formScheme = this.iRunTimeViewController.getFormScheme(logEO.getSchemeId());
                TaskConditionBoxController taskConditionBoxController = (TaskConditionBoxController)SpringContextUtils.getBean(TaskConditionBoxController.class);
                Scheme scheme = taskConditionBoxController.convertSchemeDefinToScheme(formScheme);
                GcOrgCenterService tool = GcOrgPublicTool.getInstance((String)logEO.getOrgType(), (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)new YearPeriodObject(null, archiveInfoEO.getDefaultPeriod()));
                GcOrgCacheVO unitVO = tool.getOrgByCode(archiveInfoEO.getUnitId());
                String[] rowData = new String[]{archiveInfoEO.getId(), archiveInfoEO.getFileName(), mappingUnitCode, unitVO.getTitle(), archiveInfoEO.getDefaultPeriod(), formScheme.getTitle(), "CNY", archiveInfoEO.getCreateUser(), DateUtils.format((Date)archiveInfoEO.getCreateDate(), (String)"yyyy-MM-dd HH:mm:ss"), "", "0"};
                rowDataList.add(rowData);
            }
            File csv = ArchiveCsvUtil.createCsv((String[])header, rowDataList);
            if (csv == null) continue;
            String timeStr = DateUtils.format((Date)logEO.getCreateDate(), (String)"yyyy-MM-dd-HH-mm-ss");
            String fileName = this.archiveProperties.getFtpPathPrefix() + "/gcreport/" + mappingUnitCode + "/" + timeStr + "/" + mappingUnitCode + "-" + timeStr + "-ArchiveInfo.csv";
            this.upload(csv, fileName, this.archiveProperties.isSFTP());
        }
    }

    private void upload(File csv, String fileName, boolean isSFTP) {
        try {
            ArchiveUploadFieldUtil.upload((InputStream)new FileInputStream(csv), (String)fileName, (int)((int)csv.length()), (boolean)isSFTP);
        }
        catch (Exception e) {
            throw new BusinessRuntimeException((Throwable)e);
        }
        ArchiveCsvUtil.deleteTempFile((File)csv);
    }
}

