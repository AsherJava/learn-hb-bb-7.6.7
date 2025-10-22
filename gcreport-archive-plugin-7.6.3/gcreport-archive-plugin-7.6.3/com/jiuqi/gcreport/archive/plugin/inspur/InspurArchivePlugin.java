/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.DateUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.gcreport.archive.api.scheme.vo.ArchiveConfigFormInfo
 *  com.jiuqi.gcreport.archive.api.scheme.vo.ArchiveContext
 *  com.jiuqi.gcreport.archive.common.ArchiveProperties
 *  com.jiuqi.gcreport.archive.common.ArchiveStatusEnum
 *  com.jiuqi.gcreport.archive.dao.ArchiveConfigDao
 *  com.jiuqi.gcreport.archive.dao.ArchiveInfoDao
 *  com.jiuqi.gcreport.archive.entity.ArchiveConfigEO
 *  com.jiuqi.gcreport.archive.entity.ArchiveInfoEO
 *  com.jiuqi.gcreport.archive.entity.ArchiveLogEO
 *  com.jiuqi.gcreport.archive.entity.ArchiveParamData
 *  com.jiuqi.gcreport.archive.plugin.ArchivePlugin
 *  com.jiuqi.gcreport.archive.util.ArchiveLogUtil
 *  com.jiuqi.gcreport.archive.util.ArchiveUploadFieldUtil
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 *  com.jiuqi.np.core.context.ContextUser
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.core.utils.RestTemplateUtil
 *  com.jiuqi.np.period.DefaultPeriodAdapter
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.dataentry.bean.ExportData
 *  com.jiuqi.nr.dataentry.bean.ExportParam
 *  com.jiuqi.nr.dataentry.service.IFuncExecuteService
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  org.springframework.http.HttpEntity
 *  org.springframework.http.HttpHeaders
 *  org.springframework.http.HttpMethod
 *  org.springframework.http.MediaType
 *  org.springframework.http.ResponseEntity
 *  org.springframework.transaction.annotation.Propagation
 *  org.springframework.transaction.annotation.Transactional
 *  org.springframework.web.client.RestTemplate
 *  org.springframework.web.util.UriComponentsBuilder
 */
package com.jiuqi.gcreport.archive.plugin.inspur;

import com.fasterxml.jackson.core.type.TypeReference;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.DateUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.gcreport.archive.api.scheme.vo.ArchiveConfigFormInfo;
import com.jiuqi.gcreport.archive.api.scheme.vo.ArchiveContext;
import com.jiuqi.gcreport.archive.common.ArchiveProperties;
import com.jiuqi.gcreport.archive.common.ArchiveStatusEnum;
import com.jiuqi.gcreport.archive.dao.ArchiveConfigDao;
import com.jiuqi.gcreport.archive.dao.ArchiveInfoDao;
import com.jiuqi.gcreport.archive.entity.ArchiveConfigEO;
import com.jiuqi.gcreport.archive.entity.ArchiveInfoEO;
import com.jiuqi.gcreport.archive.entity.ArchiveLogEO;
import com.jiuqi.gcreport.archive.entity.ArchiveParamData;
import com.jiuqi.gcreport.archive.plugin.ArchivePlugin;
import com.jiuqi.gcreport.archive.plugin.inspur.service.InspurArchiveService;
import com.jiuqi.gcreport.archive.util.ArchiveLogUtil;
import com.jiuqi.gcreport.archive.util.ArchiveUploadFieldUtil;
import com.jiuqi.gcreport.archive.utils.SecretUtils;
import com.jiuqi.gcreport.archive.utils.XMLFileUtils;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import com.jiuqi.np.core.context.ContextUser;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.core.utils.RestTemplateUtil;
import com.jiuqi.np.period.DefaultPeriodAdapter;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.dataentry.bean.ExportData;
import com.jiuqi.nr.dataentry.bean.ExportParam;
import com.jiuqi.nr.dataentry.service.IFuncExecuteService;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class InspurArchivePlugin
implements ArchivePlugin {
    @Autowired
    private ArchiveInfoDao archiveInfoDao;
    @Autowired
    private ArchiveProperties archiveProperties;
    @Autowired
    private ArchiveConfigDao archiveConfigDao;
    @Autowired
    private IFuncExecuteService funcExecuteService;
    @Autowired
    private IRunTimeViewController iRunTimeViewController;
    @Autowired(required=false)
    private InspurArchiveService inspurArchiveService;
    private static final Logger LOGGER = LoggerFactory.getLogger(InspurArchivePlugin.class);

    public String getPluginName() {
        return "\u6d6a\u6f6e\u7535\u5b50\u6863\u6848\u63d2\u4ef6";
    }

    public String getPluginCode() {
        return "inspurArchive";
    }

    protected void initEoFileInfo(ArchiveContext context, ArchiveLogEO logEO, String unit, String fileSuffix, ArchiveInfoEO eo, String username) {
        YearPeriodObject yp;
        Map dimensionSet = context.getDimensionSet();
        StringBuffer filePath = new StringBuffer();
        StringBuffer fileName = new StringBuffer();
        String dataTime = ((DimensionValue)dimensionSet.get("DATATIME")).getValue();
        String orgType = context.getOrgType();
        GcOrgCenterService tool = GcOrgPublicTool.getInstance((String)orgType, (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)(yp = new YearPeriodObject(null, dataTime)));
        GcOrgCacheVO unitVO = tool.getOrgByCode(unit);
        if (unitVO == null) {
            throw new BusinessRuntimeException("\u5355\u4f4d[" + unit + "]\u5728\u65f6\u671f[" + dataTime + "]\u4e0b\u4e0d\u5b58\u5728\uff01");
        }
        FormSchemeDefine formScheme = this.iRunTimeViewController.getFormScheme(logEO.getSchemeId());
        String formSchemeTitle = formScheme.getTitle();
        fileName.append(formSchemeTitle).append("-").append(unit).append("-").append(dataTime);
        fileName.append(fileSuffix);
        filePath.append(fileName);
        eo.setFilePath(StringUtils.isEmpty((String)eo.getFilePath()) ? filePath.toString() : eo.getFilePath() + ";" + filePath);
        eo.setFileName(StringUtils.isEmpty((String)eo.getFileName()) ? fileName.toString() : eo.getFileName() + ";" + fileName);
    }

    public String doAction(ArchiveContext context, ArchiveLogEO logEO, List<ArchiveConfigEO> archiveConfigEOS) {
        String startPeriodString = context.getStartPeriodString();
        String endPeriodString = context.getEndPeriodString();
        if (StringUtils.isEmpty((String)startPeriodString) || StringUtils.isEmpty((String)endPeriodString)) {
            throw new BusinessRuntimeException("\u5f52\u6863\u65f6\u95f4\u8303\u56f4\u4e0d\u80fd\u4e3a\u7a7a");
        }
        PeriodWrapper startPeriodWrapper = new PeriodWrapper(startPeriodString);
        PeriodWrapper endPeriodWrapper = new PeriodWrapper(endPeriodString);
        if (startPeriodWrapper.getType() != endPeriodWrapper.getType()) {
            throw new BusinessRuntimeException("\u5f52\u6863\u8d77\u6b62\u65f6\u95f4\u7c7b\u578b\u4e0d\u76f8\u540c");
        }
        if (startPeriodString.compareTo(endPeriodString) > 0) {
            throw new BusinessRuntimeException("\u5f52\u6863\u5f00\u59cb\u65f6\u95f4\u4e0d\u80fd\u5927\u4e8e\u7ed3\u675f\u65f6\u95f4");
        }
        String unit = ((DimensionValue)context.getDimensionSet().get("MD_ORG")).getValue();
        DefaultPeriodAdapter defaultPeriodAdapter = new DefaultPeriodAdapter();
        StringBuffer log = new StringBuffer();
        HashSet<String> periodStrSet = new HashSet<String>();
        while (startPeriodWrapper.compareTo((Object)endPeriodWrapper) <= 0) {
            if (!periodStrSet.contains(startPeriodWrapper.toString())) {
                periodStrSet.add(startPeriodWrapper.toString());
                Map dimensionSetMap = context.getDimensionSet();
                DimensionValue dimensionValue = new DimensionValue();
                dimensionValue.setName("DATATIME");
                dimensionValue.setValue(startPeriodWrapper.toString());
                dimensionSetMap.put("DATATIME", dimensionValue);
                ArchiveContext copContext = new ArchiveContext();
                BeanUtils.copyProperties(context, copContext);
                copContext.setDefaultPeriod(startPeriodWrapper.toString());
                ArchiveParamData archiveParamData = new ArchiveParamData(context.getOrgCode(), startPeriodWrapper.toString());
                try {
                    String archiveResult = this.archive(copContext, startPeriodWrapper, logEO, archiveParamData);
                    log.append(archiveResult);
                }
                catch (Exception e) {
                    LOGGER.error("\u6d6a\u6f6e\u5f52\u6863\u5931\u8d25\uff0c\u539f\u56e0\uff1a" + e.getMessage(), e);
                }
            }
            if (defaultPeriodAdapter.nextPeriod(startPeriodWrapper)) continue;
        }
        return StringUtils.isEmpty((String)log.toString()) ? "\u5355\u4f4d" + unit + "\u4e0a\u4f20\u6587\u4ef6\u6210\u529f\n" : log.toString() + "\n";
    }

    public boolean reuploadArchive(ArchiveContext exportParam, String filePath, StringBuffer reuploadLog) {
        return false;
    }

    @Transactional(rollbackFor={Exception.class}, propagation=Propagation.REQUIRES_NEW)
    public String archive(ArchiveContext context, PeriodWrapper startPeriodWrapper, ArchiveLogEO logEO, ArchiveParamData archiveParamData) {
        String defaultPeriod;
        String unit = ((DimensionValue)context.getDimensionSet().get("MD_ORG")).getValue();
        if (ArchiveUploadFieldUtil.duplicateChecking((JtableContext)context, (String)unit, (String)(defaultPeriod = ((DimensionValue)context.getDimensionSet().get("DATATIME")).getValue()), (String)logEO.getOrgType()) || ArchiveUploadFieldUtil.alreadyHasTask((JtableContext)context, (String)unit, (String)defaultPeriod, (String)logEO.getId())) {
            return "\u5355\u4f4d[" + unit + "]\u65f6\u671f[" + defaultPeriod + "],\u5df2\u7ecf\u5f52\u6863\u8fc7\u4e0d\u80fd\u518d\u6b21\u5f52\u6863\uff01";
        }
        List archiveConfigEOS = this.archiveConfigDao.queryBySchemeId(context.getFormSchemeKey());
        if (CollectionUtils.isEmpty((Collection)archiveConfigEOS)) {
            return "\u62a5\u8868\u65b9\u6848[" + context.getFormSchemeKey() + "]\u6ca1\u6709\u8bbe\u7f6e\u5f52\u6863\u62a5\u8868\u8303\u56f4,\u5f52\u6863\u5931\u8d25\uff01";
        }
        ContextUser user = NpContextHolder.getContext().getUser();
        String username = user == null ? "" : user.getName();
        ArchiveInfoEO eo = ArchiveUploadFieldUtil.initArchiveInfoEO((ArchiveContext)context, (String)unit, (String)defaultPeriod, (String)username, (ArchiveLogEO)logEO);
        ArchiveConfigEO archiveConfigEO = (ArchiveConfigEO)archiveConfigEOS.get(0);
        context.setOrgType(logEO.getOrgType());
        this.getArchiveParamData(archiveParamData, context);
        boolean endFillState = false;
        if (Objects.nonNull(this.inspurArchiveService)) {
            endFillState = this.inspurArchiveService.getUnitEndFillState(context, user);
        }
        if (ArchiveUploadFieldUtil.hasNotLockedForm((ArchiveContext)context, (ArchiveConfigEO)archiveConfigEO) && !endFillState) {
            String errorMesage = "\u5355\u4f4d[" + unit + "]\u65f6\u671f[" + defaultPeriod + "],\u5b58\u5728\u672a\u4e0a\u62a5\u6216\u672a\u9501\u5b9a\u62a5\u8868,\u4e0d\u5141\u8bb8\u5f52\u6863\uff01";
            eo.setErrorInfo(errorMesage);
            eo.setRetryCount(Integer.valueOf(1));
            eo.setStatus(Integer.valueOf(ArchiveStatusEnum.UPLOAD_FAILED.getStatus()));
            this.archiveInfoDao.save((DefaultTableEntity)eo);
            return errorMesage;
        }
        if (StringUtils.isEmpty((String)archiveConfigEO.getPdfFormInfos())) {
            String errorMesage = "\u5355\u4f4d[" + unit + "]\u65f6\u671f[" + defaultPeriod + "],\u751f\u6210pdf\u6587\u4ef6\u672a\u9009\u62a5\u8868\u4fe1\u606f,\u4e0d\u5141\u8bb8\u5f52\u6863\uff01";
            eo.setErrorInfo(errorMesage);
            eo.setRetryCount(Integer.valueOf(1));
            eo.setStatus(Integer.valueOf(ArchiveStatusEnum.UPLOAD_FAILED.getStatus()));
            this.archiveInfoDao.save((DefaultTableEntity)eo);
            return errorMesage;
        }
        StringBuffer errorMessage = new StringBuffer(StringUtils.isEmpty((String)eo.getFileName()) ? "" : eo.getFileName());
        Map<FormDefine, ExportData> pdfFormExportData = this.getExportDataGroupByForm(context, archiveConfigEO.getPdfFormInfos(), false, errorMessage);
        Map<FormDefine, ExportData> excelFormExportData = this.getExportDataGroupByForm(context, archiveConfigEO.getExcelFormInfos(), true, errorMessage);
        archiveParamData.setAttachCount(Integer.valueOf(pdfFormExportData.size()));
        if (pdfFormExportData.isEmpty()) {
            archiveParamData.setAttachCount(Integer.valueOf(excelFormExportData.size()));
        }
        LOGGER.info("\u63a8\u9001ESB\u6570\u91cf\uff1acount=" + archiveParamData.getAttachCount());
        if (pdfFormExportData.isEmpty() && excelFormExportData.isEmpty()) {
            LOGGER.error("\u83b7\u53d6pdf\u548cexcel\u8868\u5355\u4fe1\u606f\u4e3a\u7a7a\uff1bformKey=" + archiveConfigEO.getPdfFormInfos() + ",excel:" + archiveConfigEO.getExcelFormInfos());
            String errorMesage = "\u5355\u4f4d[" + unit + "]\u65f6\u671f[" + defaultPeriod + "],\u6d6a\u6f6e\u5f52\u6863\u5bfc\u51fa\u8868\u5355pdf\u548cexcel\u4fe1\u606f\u4e3a\u7a7a\u3002";
            eo.setErrorInfo(errorMesage);
            eo.setRetryCount(Integer.valueOf(1));
            eo.setStatus(Integer.valueOf(ArchiveStatusEnum.UPLOAD_FAILED.getStatus()));
            this.archiveInfoDao.save((DefaultTableEntity)eo);
            return errorMesage;
        }
        try {
            this.beforeSingleArchive(context, startPeriodWrapper, archiveParamData);
        }
        catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            eo.setErrorInfo(e.getMessage());
            eo.setRetryCount(Integer.valueOf(1));
            eo.setStatus(Integer.valueOf(ArchiveStatusEnum.UPLOAD_FAILED.getStatus()));
            this.archiveInfoDao.save((DefaultTableEntity)eo);
            return e.getMessage();
        }
        try {
            if (!pdfFormExportData.isEmpty()) {
                this.uploadExportFileByFormKeys(pdfFormExportData, eo, context, logEO, archiveParamData, unit, username, false, errorMessage);
            }
            if (!excelFormExportData.isEmpty()) {
                this.uploadExportFileByFormKeys(excelFormExportData, eo, context, logEO, archiveParamData, unit, username, true, errorMessage);
            }
            eo.setStatus(Integer.valueOf(ArchiveStatusEnum.UPLOAD_SUCCESS.getStatus()));
        }
        catch (Exception e) {
            if (eo != null) {
                eo.setStatus(Integer.valueOf(ArchiveStatusEnum.UPLOAD_FAILED.getStatus()));
                eo.setErrorInfo(ArchiveLogUtil.getExceptionStackStr((Throwable)e));
                eo.setRetryCount(Integer.valueOf(this.archiveProperties.getRetryCount()));
                this.archiveInfoDao.save((DefaultTableEntity)eo);
            }
            LOGGER.error("\u6d6a\u6f6e\u5f52\u6863\u5931\u8d25\uff0c\u8be6\u60c5\uff1a{}", (Object)e.getMessage(), (Object)e);
            return "\u5355\u4f4d[" + unit + "]\u65f6\u671f[" + defaultPeriod + "],\u5f52\u6863\u5931\u8d25" + e.getMessage();
        }
        this.archiveInfoDao.save((DefaultTableEntity)eo);
        archiveParamData.setArchiveInfoId(eo.getId());
        return "";
    }

    public void afterArchiveProcessComplete(ArchiveLogEO logEO, StringBuffer logInfo) {
    }

    public void beforeSingleArchive(ArchiveContext context, PeriodWrapper startPeriodWrapper, ArchiveParamData archiveParamData) {
        if (Objects.nonNull(this.inspurArchiveService)) {
            this.inspurArchiveService.beforeSingleArchive(context, startPeriodWrapper, archiveParamData);
        }
    }

    public void afterSingleArchive(String archiveResult, ArchiveParamData archiveParamData) {
        if (Objects.nonNull(this.inspurArchiveService)) {
            this.inspurArchiveService.afterSingleArchive(archiveResult, archiveParamData);
        }
    }

    public void getArchiveParamData(ArchiveParamData archiveParamData, ArchiveContext context) {
        HashMap<String, String> extendParam = archiveParamData.getExtendParam();
        if (null == extendParam) {
            extendParam = new HashMap<String, String>();
        }
        Map dimensionSet = context.getDimensionSet();
        String dataTime = ((DimensionValue)dimensionSet.get("DATATIME")).getValue();
        String orgType = context.getOrgType();
        YearPeriodObject yp = new YearPeriodObject(null, dataTime);
        GcOrgCenterService tool = GcOrgPublicTool.getInstance((String)orgType, (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yp);
        GcOrgCacheVO unitVO = tool.getOrgByCode(context.getOrgCode());
        String billId = this.getBillId(context);
        extendParam.put("BILLID", billId);
        extendParam.put("MD_ORG", unitVO.getCode());
        extendParam.put("ORG_TITLE", unitVO.getTitle());
        extendParam.put("BBLX", unitVO.getBblx());
        extendParam.put("DATATIME", dataTime);
        extendParam.put("QYDM", unitVO.getCode());
        extendParam.put("NAME", unitVO.getTitle());
        extendParam.put("ORGTYPEID", unitVO.getOrgTypeId());
        extendParam.put("CURRENCYID", ObjectUtils.isEmpty(unitVO.getBaseFieldValue("CURRENCYID")) ? "" : String.valueOf(unitVO.getBaseFieldValue("CURRENCYID")));
        extendParam.put("ADJTYPEIDS", ObjectUtils.isEmpty(unitVO.getBaseFieldValue("ADJTYPEIDS")) ? "" : String.valueOf(unitVO.getBaseFieldValue("ADJTYPEIDS")));
        archiveParamData.setExtendParam(extendParam);
        LOGGER.info("\u6d6a\u6f6e\u7535\u5b50\u6863\u6848\u6269\u5c55\u53c2\u6570\uff1a" + ((Object)extendParam).toString());
        if (Objects.nonNull(this.inspurArchiveService)) {
            this.inspurArchiveService.getArchiveParamData(archiveParamData, context);
        }
    }

    private String getBillId(ArchiveContext context) {
        TaskDefine taskDefine = this.iRunTimeViewController.queryTaskDefine(context.getTaskId());
        Map dimensionSet = context.getDimensionSet();
        String dataTime = ((DimensionValue)dimensionSet.get("DATATIME")).getValue();
        String orgType = context.getOrgType();
        YearPeriodObject yp = new YearPeriodObject(null, dataTime);
        GcOrgCenterService tool = GcOrgPublicTool.getInstance((String)orgType, (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yp);
        GcOrgCacheVO unitVO = tool.getOrgByCode(context.getOrgCode());
        String bblx = unitVO.getBblx();
        String currencyCode = String.valueOf(unitVO.getBaseFieldValue("CURRENCYID"));
        String billId = taskDefine.getTaskCode() + "-" + unitVO.getCode() + "-" + bblx + "-" + dataTime + "-" + currencyCode;
        return billId;
    }

    private void uploadExportFileByFormKeys(Map<FormDefine, ExportData> formExportData, ArchiveInfoEO eo, ArchiveContext context, ArchiveLogEO logEO, ArchiveParamData archiveParamData, String unit, String username, boolean isExcel, StringBuffer errorMessage) throws IOException {
        String fileSuffix = isExcel ? ".xlsx" : ".pdf";
        TaskDefine taskDefine = this.iRunTimeViewController.queryTaskDefine(context.getTaskId());
        FormSchemeDefine formSchemeDefine = this.iRunTimeViewController.getFormScheme(context.getFormSchemeKey());
        int count = 0;
        if (this.archiveProperties.getInspurSingleForm().booleanValue()) {
            this.initEoFileInfo(context, logEO, unit, "", eo, username);
            boolean errorFlag = false;
            for (FormDefine formDefine : formExportData.keySet()) {
                ExportData result = formExportData.get(formDefine);
                String fileName = formDefine.getTitle();
                Map<String, String> formInfoData = this.getFormInfoData(taskDefine, formSchemeDefine, formDefine);
                this.sendInspurFile(result.getData(), archiveParamData, formInfoData, fileName, fileSuffix, eo.getCreateDate());
                if (errorFlag) {
                    eo.setErrorInfo(eo.getErrorInfo() + errorMessage.toString());
                    logEO.setLogInfo(logEO.getLogInfo() + errorMessage.toString());
                    LOGGER.error("\u6d6a\u6f6e\u7535\u5b50\u6863\u6848\u63d2\u4ef6\u5f52\u6863\u4e0a\u4f20\u5931\u8d25:" + errorMessage.toString());
                }
                ++count;
            }
        } else {
            this.initEoFileInfo(context, logEO, unit, fileSuffix, eo, username);
            Map<String, String> formInfoData = this.getFormInfoData(taskDefine, formSchemeDefine, null);
            ExportData exportData = (ExportData)formExportData.values().stream().collect(Collectors.toList()).get(0);
            this.sendInspurFile(exportData.getData(), archiveParamData, formInfoData, eo.getFileName().replace(fileSuffix, ""), fileSuffix, eo.getCreateDate());
            count = 1;
        }
        archiveParamData.setAttachCount(Integer.valueOf(count));
        LOGGER.info("\u63a8\u9001\u6d6a\u6f6e\u6570\u91cf\uff1acount=" + count);
        this.afterSingleArchive(errorMessage.toString(), archiveParamData);
    }

    private void sendInspurFile(byte[] exportData, ArchiveParamData archiveParamData, Map<String, String> formInfoData, String fileName, String fileSuffix, Date date) throws IOException {
        this.sendInspurArchiveFile(exportData, archiveParamData, fileName + fileSuffix);
        this.sendInspurXMLFile(archiveParamData, formInfoData, fileName + ".xml", DateUtils.format((Date)date, (String)"yyyy-MM-dd"));
        LOGGER.info("\u6587\u4ef6\u5f52\u6863\u6d6a\u6f6e\u5b8c\u6210\uff1a mdcode=" + archiveParamData.getOrgCode() + " \u8868\u5355\uff1a" + formInfoData.toString());
    }

    private void sendInspurArchiveFile(byte[] exportData, ArchiveParamData archiveParamData, String fileName) throws IOException {
        this.sendArchiveFileMessage(exportData, archiveParamData, fileName, false);
    }

    private void sendInspurXMLFile(ArchiveParamData archiveParamData, Map<String, String> formInfoData, String fileName, String currentDateStr) throws IOException {
        byte[] xmlFile = XMLFileUtils.createXMLFile(archiveParamData, currentDateStr, formInfoData);
        this.sendArchiveFileMessage(xmlFile, archiveParamData, fileName, true);
    }

    private void sendArchiveFileMessage(byte[] exportData, ArchiveParamData archiveParamData, String fileName, boolean isXMLFlag) {
        try {
            String fileData = Base64.getEncoder().encodeToString(exportData);
            if (StringUtils.isEmpty((String)fileData)) {
                LOGGER.error("\u5f52\u6863\u6587\u4ef6\u4e3a\u7a7a\uff0cfileName\uff1a" + fileName + ",\u53c2\u6570\uff1a\u5355\u4f4d\uff1a" + archiveParamData.getOrgCode() + ",\u65f6\u671f\uff1a" + archiveParamData.getDataTime());
                return;
            }
            Map<String, Object> sendBody = this.getSendExportField(fileData, fileName, archiveParamData, isXMLFlag);
            LOGGER.debug("\u53d1\u9001\u6d6a\u6f6e\u5f52\u6863\u63a5\u53e3\u62a5\u6587\u4fe1\u606f:" + sendBody);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl((String)this.archiveProperties.getEfsAddress());
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
            headers.set("X-ECC-Current-Tenant", "10000");
            String json = JsonUtils.writeValueAsString(sendBody);
            HttpEntity httpEntity = new HttpEntity((Object)json, (MultiValueMap)headers);
            RestTemplate restTemplate = RestTemplateUtil.generateSsl();
            ResponseEntity result = restTemplate.exchange(builder.build().toString(), HttpMethod.POST, httpEntity, String.class, new Object[0]);
            Map response = new HashMap();
            if (!ObjectUtils.isEmpty(result.getBody())) {
                LOGGER.debug("\u6d6a\u6f6e\u7535\u5b50\u6863\u6848\u8c03\u7528\u63a5\u53e3:{}, \u8fd4\u56dejson:{}", (Object)builder.build().toString(), result.getBody());
                response = (Map)JsonUtils.readValue((String)((String)result.getBody()), (TypeReference)new TypeReference<Map<String, Object>>(){});
            }
            if (Objects.isNull(response)) {
                LOGGER.error("\u53d1\u9001\u6d6a\u6f6e\u5f52\u6863\u63a5\u53e3\u5f02\u5e38,\u62a5\u6587\u4fe1\u606f\uff1a" + json);
                throw new BusinessRuntimeException("\u53d1\u9001\u6d6a\u6f6e\u5f52\u6863\u63a5\u53e3\u5f02\u5e38\u3002");
            }
            String code = String.valueOf(response.get("code"));
            if (!"200".equals(code)) {
                String message = String.valueOf(response.get("mes"));
                LOGGER.error("\u6d6a\u6f6e\u5f52\u6863\u63a5\u53e3\u63a5\u53d7\u5931\u8d25,\u8bf7\u6c42\u62a5\u6587\u4fe1\u606f\uff1a" + json + " ,\u8fd4\u56de\u62a5\u6587\uff1a" + response);
                throw new BusinessRuntimeException("\u53d1\u9001\u6d6a\u6f6e\u5f52\u6863\u63a5\u53e3\u5f02\u5e38:" + message);
            }
            LOGGER.info("\u53d1\u9001\u6d6a\u6f6e\u5f52\u6863\u63a5\u53e3\u54cd\u5e94\u4fe1\u606f:" + response.toString());
        }
        catch (Exception e) {
            LOGGER.error("\u8bf7\u6c42\u6d6a\u6f6e\u5f52\u6863\u63a5\u53e3\u5f02\u5e38", e);
            throw new BusinessRuntimeException("\u8bf7\u6c42\u6d6a\u6f6e\u5f52\u6863\u63a5\u53e3\u5f02\u5e38", (Throwable)e);
        }
    }

    private Map<String, Object> getSendExportField(String fieldData, String fileName, ArchiveParamData archiveParamData, boolean isXMLFlag) throws UnsupportedEncodingException {
        HashMap<String, Object> sendBodyInfo = new HashMap<String, Object>();
        sendBodyInfo.put("sourceSys", this.archiveProperties.getInspurSourceSys());
        String billId = String.valueOf(archiveParamData.getExtendParam().get("BILLID"));
        sendBodyInfo.put("billId", billId);
        sendBodyInfo.put("checkCode", SecretUtils.encryptByKey(this.archiveProperties.getInspurCheckCode(), this.archiveProperties.getInspurSourceSys() + billId));
        sendBodyInfo.put("mdmCode", String.valueOf(archiveParamData.getExtendParam().get("QYDM")));
        sendBodyInfo.put("fileStream", fieldData);
        sendBodyInfo.put("fileId", UUIDUtils.newUUIDStr());
        sendBodyInfo.put("fileName", fileName);
        String xmlFile = isXMLFlag ? "1" : "0";
        sendBodyInfo.put("xmlFile", xmlFile);
        return sendBodyInfo;
    }

    private Map<String, String> getFormInfoData(TaskDefine taskDefine, FormSchemeDefine formSchemeDefine, FormDefine formDefine) {
        HashMap<String, String> formData = new HashMap<String, String>();
        formData.put("TASK_TITLE", taskDefine.getTitle());
        formData.put("TASK_CODE", taskDefine.getTaskCode());
        formData.put("FORMSCHEME_TITLE", formSchemeDefine.getTitle());
        formData.put("FORMSCHEME_CODE", formSchemeDefine.getFormSchemeCode());
        if (Objects.nonNull(formDefine)) {
            formData.put("FORM_TITLE", formDefine.getTitle());
            formData.put("FORM_CODE", formDefine.getFormCode());
        }
        return formData;
    }

    private Map<FormDefine, ExportData> getExportDataGroupByForm(ArchiveContext context, String formsInfoJsonStr, boolean isExcel, StringBuffer errorMessage) {
        if (StringUtils.isEmpty((String)formsInfoJsonStr)) {
            return CollectionUtils.newHashMap();
        }
        List formInfos = (List)JsonUtils.readValue((String)formsInfoJsonStr, (TypeReference)new TypeReference<List<ArchiveConfigFormInfo>>(){});
        Set formKeys = formInfos.stream().map(ArchiveConfigFormInfo::getFormKey).collect(Collectors.toSet());
        if (CollectionUtils.isEmpty((Collection)formInfos) || CollectionUtils.isEmpty(formKeys)) {
            return CollectionUtils.newHashMap();
        }
        HashMap<FormDefine, ExportData> formDefineExportDataMap = new HashMap<FormDefine, ExportData>();
        if (this.archiveProperties.getInspurSingleForm().booleanValue()) {
            for (String formKey : formKeys) {
                HashSet formKeySet = CollectionUtils.newHashSet();
                formKeySet.add(formKey);
                ExportParam exportParam = ArchiveUploadFieldUtil.initExportParam((JtableContext)context, (Set)formKeySet, (boolean)isExcel);
                exportParam.getContext().setFormKey(formKey);
                exportParam.setAllCorp(false);
                FormDefine formDefine = this.iRunTimeViewController.queryFormById(formKey);
                if (formDefine == null) {
                    LOGGER.error("\u627e\u4e0d\u5230\u8868\u5355\u4fe1\u606f\uff0cformkey=" + formKey);
                    errorMessage.append("\u627e\u4e0d\u5230\u8868\u5355\u4fe1\u606f\uff0cformkey=").append(formKey).append(";");
                    continue;
                }
                ExportData result = null;
                try {
                    result = this.funcExecuteService.export(exportParam);
                }
                catch (Exception e) {
                    String message = "\u5bfc\u51fa\u5f52\u6863\u4fe1\u606f\u62a5\u9519\uff0cformKey\uff1a" + formKey + ",dimension:";
                    for (DimensionValue dimensionValue : context.getDimensionSet().values()) {
                        message = message + dimensionValue.getValue() + ";";
                    }
                    LOGGER.error(message, e);
                    continue;
                }
                if (result == null || result.getData() == null || result.getData().length <= 0) {
                    errorMessage.append("\u8868\u5355\u3010").append(formDefine.getTitle()).append("-").append(formDefine.getFormCode()).append("\u3011\u6ca1\u6709\u751f\u6210\u5f52\u6863\u6587\u4ef6;");
                    continue;
                }
                formDefineExportDataMap.put(formDefine, result);
            }
        } else {
            ExportParam exportParam = ArchiveUploadFieldUtil.initExportParam((JtableContext)context, formKeys, (boolean)isExcel);
            ExportData result = null;
            try {
                result = this.funcExecuteService.export(exportParam);
            }
            catch (Exception e) {
                String message = "\u6d6a\u6f6e\u5f52\u6863\u5bfc\u51fa\u5168\u90e8\u8868\u5355\u4fe1\u606f\u62a5\u9519, dimension:";
                for (DimensionValue dimensionValue : context.getDimensionSet().values()) {
                    message = message + dimensionValue.getValue() + ";";
                }
                LOGGER.error(message, e);
                return CollectionUtils.newHashMap();
            }
            if (result == null || result.getData() == null || result.getData().length <= 0) {
                errorMessage.append("\u6ca1\u6709\u751f\u6210\u5f52\u6863\u6587\u4ef6;");
                return CollectionUtils.newHashMap();
            }
            formDefineExportDataMap.put(null, result);
        }
        return formDefineExportDataMap;
    }
}

