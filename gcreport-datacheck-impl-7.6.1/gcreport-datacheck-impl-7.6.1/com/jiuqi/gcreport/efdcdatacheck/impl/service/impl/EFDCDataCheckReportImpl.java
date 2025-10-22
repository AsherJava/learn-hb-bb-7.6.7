/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.common.file.dto.CommonFileDTO
 *  com.jiuqi.common.file.service.CommonFileService
 *  com.jiuqi.gcreport.common.mongodb.GCBlobContainerManager
 *  com.jiuqi.gcreport.common.pdf.ConstantProperties
 *  com.jiuqi.gcreport.common.pdf.DownloadFileUtil
 *  com.jiuqi.gcreport.common.pdf.DownloadZipDTO
 *  com.jiuqi.gcreport.common.pdf.WordUtil
 *  com.jiuqi.gcreport.common.util.DimensionUtils
 *  com.jiuqi.gcreport.common.util.JsonUtils
 *  com.jiuqi.gcreport.common.util.UUIDOrderUtils
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 *  com.jiuqi.gcreport.efdcdatacheck.client.vo.EfdcCheckConfigFormInfo
 *  com.jiuqi.gcreport.efdcdatacheck.client.vo.EfdcCheckCreateReportVO
 *  com.jiuqi.gcreport.efdcdatacheck.client.vo.EfdcCheckReportLogVO
 *  com.jiuqi.gcreport.efdcdatacheck.client.vo.EfdcCheckUserVo
 *  com.jiuqi.gcreport.efdcdatacheck.client.vo.GcBatchEfdcCheckInfo
 *  com.jiuqi.gcreport.efdcdatacheck.client.vo.GcEfdcShareFileVO
 *  com.jiuqi.gcreport.i18n.util.GcI18nUtil
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 *  com.jiuqi.np.core.context.ContextUser
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.np.user.service.UserService
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nvwa.login.domain.NvwaLoginUserDTO
 *  com.jiuqi.nvwa.login.service.NvwaLoginService
 *  com.jiuqi.nvwa.workbench.share.bean.UserBase
 *  com.jiuqi.va.domain.common.R
 *  javax.annotation.Resource
 *  javax.servlet.ServletOutputStream
 *  javax.servlet.http.HttpServletResponse
 *  javax.validation.constraints.NotNull
 *  org.springframework.transaction.annotation.Transactional
 *  sun.misc.BASE64Decoder
 */
package com.jiuqi.gcreport.efdcdatacheck.impl.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.common.file.dto.CommonFileDTO;
import com.jiuqi.common.file.service.CommonFileService;
import com.jiuqi.gcreport.common.mongodb.GCBlobContainerManager;
import com.jiuqi.gcreport.common.pdf.ConstantProperties;
import com.jiuqi.gcreport.common.pdf.DownloadFileUtil;
import com.jiuqi.gcreport.common.pdf.DownloadZipDTO;
import com.jiuqi.gcreport.common.pdf.WordUtil;
import com.jiuqi.gcreport.common.util.DimensionUtils;
import com.jiuqi.gcreport.common.util.JsonUtils;
import com.jiuqi.gcreport.common.util.UUIDOrderUtils;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;
import com.jiuqi.gcreport.efdcdatacheck.client.vo.EfdcCheckConfigFormInfo;
import com.jiuqi.gcreport.efdcdatacheck.client.vo.EfdcCheckCreateReportVO;
import com.jiuqi.gcreport.efdcdatacheck.client.vo.EfdcCheckReportLogVO;
import com.jiuqi.gcreport.efdcdatacheck.client.vo.EfdcCheckUserVo;
import com.jiuqi.gcreport.efdcdatacheck.client.vo.GcBatchEfdcCheckInfo;
import com.jiuqi.gcreport.efdcdatacheck.client.vo.GcEfdcShareFileVO;
import com.jiuqi.gcreport.efdcdatacheck.impl.dao.DataCheckConfigDAO;
import com.jiuqi.gcreport.efdcdatacheck.impl.dao.EfdcCheckReportDAO;
import com.jiuqi.gcreport.efdcdatacheck.impl.dao.EfdcCheckReportShareDAO;
import com.jiuqi.gcreport.efdcdatacheck.impl.entity.DataCheckConfigEO;
import com.jiuqi.gcreport.efdcdatacheck.impl.entity.EfdcCheckReportLogEO;
import com.jiuqi.gcreport.efdcdatacheck.impl.entity.EfdcCheckReportLogShareEO;
import com.jiuqi.gcreport.efdcdatacheck.impl.service.EFDCDataCheckExportService;
import com.jiuqi.gcreport.efdcdatacheck.impl.service.EFDCDataCheckReportService;
import com.jiuqi.gcreport.efdcdatacheck.impl.service.impl.EFDCDataCheckImpl;
import com.jiuqi.gcreport.i18n.util.GcI18nUtil;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import com.jiuqi.np.core.context.ContextUser;
import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.np.user.service.UserService;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nvwa.login.domain.NvwaLoginUserDTO;
import com.jiuqi.nvwa.login.service.NvwaLoginService;
import com.jiuqi.nvwa.workbench.share.bean.UserBase;
import com.jiuqi.va.domain.common.R;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import sun.misc.BASE64Decoder;

@Service
public class EFDCDataCheckReportImpl
implements EFDCDataCheckReportService {
    private static final Logger LOGGER = LoggerFactory.getLogger(EFDCDataCheckReportImpl.class);
    @Resource
    private EfdcCheckReportDAO dao;
    @Autowired
    private ConstantProperties pro;
    @Value(value="${np.container.type:local}")
    private String mongoContainerType;
    @Autowired
    private GCBlobContainerManager gcBlobContainerMongo;
    @Autowired
    private EFDCDataCheckExportService dataCheckExportService;
    @Autowired
    private DataCheckConfigDAO dataCheckConfigDAO;
    @Autowired
    private UserService userService;
    @Autowired
    private NvwaLoginService nvwaLoginService;
    @Autowired
    private CommonFileService commonFileService;
    @Autowired
    private EfdcCheckReportShareDAO reportShareDAO;

    @Override
    public List<EfdcCheckReportLogVO> queryRecordsByCondition(Map<String, Object> conditionMap) {
        List<EfdcCheckReportLogEO> itemPOs = this.dao.findAllByConditions(conditionMap);
        List uniqueIdList = itemPOs.stream().collect(Collectors.collectingAndThen(Collectors.toMap(DefaultTableEntity::getId, e -> e, (e1, e2) -> e1), map -> map.values().stream().collect(Collectors.toList())));
        return uniqueIdList.stream().map(itemPO -> this.convertE2V((EfdcCheckReportLogEO)((Object)itemPO))).collect(Collectors.toList());
    }

    @Override
    public List<EfdcCheckReportLogVO> queryRecordsByPageCondition(Integer pageSize, Integer pageNum, Map<String, Object> conditionMap) {
        List<EfdcCheckReportLogEO> itemPOs = this.dao.findAllByPageAndConditions(pageSize, pageNum, conditionMap);
        List uniqueIdList = itemPOs.stream().collect(Collectors.collectingAndThen(Collectors.toMap(DefaultTableEntity::getId, e -> e, (e1, e2) -> e1), map -> map.values().stream())).sorted(Comparator.comparing(EfdcCheckReportLogEO::getCreateDate).reversed()).collect(Collectors.toList());
        return uniqueIdList.stream().map(itemPO -> this.convertE2V((EfdcCheckReportLogEO)((Object)itemPO))).collect(Collectors.toList());
    }

    private EfdcCheckReportLogVO convertE2V(EfdcCheckReportLogEO itemPO) {
        EfdcCheckReportLogVO itemVO = new EfdcCheckReportLogVO();
        BeanUtils.copyProperties((Object)itemPO, itemVO);
        String fileName = itemVO.getFileName();
        fileName = fileName.replaceAll("_[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}", "");
        itemVO.setFileName(fileName);
        return itemVO;
    }

    @Override
    public File viewPdf(@NotNull String id) throws IOException {
        EfdcCheckReportLogEO eo = (EfdcCheckReportLogEO)this.dao.get((Serializable)((Object)id));
        if (eo == null) {
            return null;
        }
        return this.downloadFromOSS(id, eo.getFileName());
    }

    private File downloadFromOSS(String fileKey, String fileName) throws IOException {
        File tofilePath = new File(this.pro.getTempFilePath());
        if (!tofilePath.exists()) {
            tofilePath.mkdirs();
        }
        String downFileName = fileName.replaceAll("\\_[a-z0-9-]+", "");
        File file = new File(this.pro.getTempFilePath() + downFileName);
        if (file.exists()) {
            int index = 1;
            String fileNameWithoutSuffix = downFileName.substring(0, downFileName.lastIndexOf(46));
            String suffix = downFileName.substring(downFileName.lastIndexOf(46));
            while (file.exists()) {
                file = new File(this.pro.getTempFilePath() + fileNameWithoutSuffix + "(" + index++ + ")" + suffix);
            }
        }
        CommonFileDTO ossFile = this.commonFileService.queryOssFileByFileKey(fileKey);
        FileUtils.copyInputStreamToFile(ossFile.getInputStream(), file);
        return file;
    }

    @Override
    public DownloadZipDTO batchDownload(Set<String> ids, String zipName) {
        List<EfdcCheckReportLogEO> eoList = this.dao.findCheckReportByRecids(ids);
        DownloadZipDTO zipto = new DownloadZipDTO();
        String zipPath = this.pro.getTempFilePath() + zipName;
        zipName = zipName + this.pro.getZipSuffix();
        Boolean iscomplete = false;
        ArrayList<File> files = new ArrayList<File>();
        try {
            for (EfdcCheckReportLogEO eo : eoList) {
                File file2 = this.downloadFromOSS(eo.getId(), eo.getFileName());
                if (file2.length() == 0L) {
                    file2.delete();
                    continue;
                }
                files.add(file2);
                String givenPath = this.pro.getTempFilePath() + file2.getName();
                DownloadFileUtil.copyFile((String)givenPath, (String)zipPath, (String)file2.getName());
            }
            iscomplete = WordUtil.fileToZip((String)zipPath, (String)this.pro.getTempFilePath(), (String)zipName);
            zipto.setIscomplete(iscomplete);
            zipto.setZipfilename(zipName);
            if (!CollectionUtils.isEmpty(files)) {
                files.stream().forEach(file -> file.delete());
            }
        }
        catch (Exception e) {
            throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.efdcDataCheck.downloadFaile") + e.getMessage());
        }
        return zipto;
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public String batchDeleteByRecids(Set<String> recids) {
        if (recids == null || recids.size() == 0) {
            String message = String.format(GcI18nUtil.getMessage((String)"gc.efdcDataCheck.deleteEFDCReportFaile"), new Object[0]);
            throw new BusinessRuntimeException(message);
        }
        String userName = NpContextHolder.getContext().getUserName();
        try {
            StringBuilder sb = new StringBuilder();
            List<EfdcCheckReportLogEO> eoList = this.dao.findCheckReportByRecids(recids);
            for (EfdcCheckReportLogEO eo : eoList) {
                if (!userName.equals(eo.getCreateUser())) {
                    recids.remove(eo.getId());
                    String fileName = eo.getFileName();
                    sb.append("[").append(fileName.replaceAll("\\_[a-z0-9-]+", "")).append("]\u7531\u7528\u6237[").append(eo.getCreateUser()).append("]\u751f\u6210;\n");
                }
                this.commonFileService.deleteOssFile(eo.getId());
            }
            this.dao.batchDeleteByRecids(recids);
            if (!StringUtils.isEmpty((String)sb.toString())) {
                sb.append("\u5982\u9700\u5220\u9664\u8bf7\u8054\u7cfb\u5bf9\u5e94\u7528\u6237\u3002");
                return sb.toString();
            }
        }
        catch (Exception e) {
            throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.efdcDataCheck.deleteReconciliationFaile", (Object[])new Object[]{e.getMessage()}));
        }
        return "\u5220\u9664\u6210\u529f";
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void clearMongoFiles(String dateStr) throws Exception {
    }

    @Override
    public BusinessResponseEntity<String> createEfdcDataCheckReport(EfdcCheckCreateReportVO efdcCheckCreateReportVO) {
        try {
            efdcCheckCreateReportVO.setPeriodType("");
            NpContext context = NpContextHolder.getContext();
            GcBatchEfdcCheckInfo gcBatchEfdcCheckInfo = this.buildBatchCheckInfo(efdcCheckCreateReportVO, false);
            if ("choose_form".equals(efdcCheckCreateReportVO.getForm()) && (efdcCheckCreateReportVO.getFormKeyData() == null || efdcCheckCreateReportVO.getFormKeyData().size() == 0)) {
                return BusinessResponseEntity.error((String)GcI18nUtil.getMessage((String)"gc.efdcDataCheck.createEfdcReportFaile"));
            }
            StringBuffer log = new StringBuffer();
            String asynTaskKey = UUIDOrderUtils.newUUIDStr();
            EFDCDataCheckImpl checkResultInfo = new EFDCDataCheckImpl();
            checkResultInfo.batchEfdcDataCheck(asynTaskKey, gcBatchEfdcCheckInfo);
            if (checkResultInfo.getCheckZbCount() == 0) {
                log.append(checkResultInfo.getLog().append(GcI18nUtil.getMessage((String)"gc.efdcDataCheck.efdcZbNumber"))).append("\n");
            }
            if (log != null && !StringUtils.isEmpty((String)log.toString())) {
                String logString = log.toString();
                return BusinessResponseEntity.error((String)((logString.indexOf(GcI18nUtil.getMessage((String)"gc.efdcDataCheck.efdcZbNumber")) != -1 ? GcI18nUtil.getMessage((String)"gc.efdcDataCheck.createEFDCReportsFaile") : GcI18nUtil.getMessage((String)"gc.efdcDataCheck.createEFDCReportsSuccess")) + logString));
            }
            if (Arrays.asList(efdcCheckCreateReportVO.getFileType()).contains("PDF")) {
                log.append(this.dataCheckExportService.planTaskCheckResultPdf(context, asynTaskKey, checkResultInfo, gcBatchEfdcCheckInfo)).append("\n");
            }
            if (Arrays.asList(efdcCheckCreateReportVO.getFileType()).contains("EXCEL")) {
                log.append(this.dataCheckExportService.planTaskCheckResultExcel(asynTaskKey, checkResultInfo, gcBatchEfdcCheckInfo)).append("\n");
            }
            if (log != null && !StringUtils.isEmpty((String)log.toString())) {
                String logString = log.toString();
                return BusinessResponseEntity.error((String)((logString.indexOf(GcI18nUtil.getMessage((String)"gc.efdcDataCheck.efdcZbNumber")) != -1 ? GcI18nUtil.getMessage((String)"gc.efdcDataCheck.createEFDCReportsFaile") : GcI18nUtil.getMessage((String)"gc.efdcDataCheck.createEFDCReportsSuccess")) + logString));
            }
        }
        catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return BusinessResponseEntity.error((String)(GcI18nUtil.getMessage((String)"gc.efdcDataCheck.createEFDCReportsFaile") + e.getMessage()));
        }
        return BusinessResponseEntity.ok((Object)GcI18nUtil.getMessage((String)"gc.efdcDataCheck.createEFDCReportsSuccess"));
    }

    @Override
    public GcBatchEfdcCheckInfo buildBatchCheckInfo(EfdcCheckCreateReportVO efdcCheckCreateReportVO, boolean isPlanTask) {
        List orgCodeList = efdcCheckCreateReportVO.getOrgCodeList();
        String periodStr = efdcCheckCreateReportVO.getPeriodString();
        String groupMode = efdcCheckCreateReportVO.getGroupMode();
        String taskId = efdcCheckCreateReportVO.getTaskId();
        String schemeId = efdcCheckCreateReportVO.getSchemeId();
        String adjustCode = efdcCheckCreateReportVO.getAdjustCode();
        String orgType = efdcCheckCreateReportVO.getOrgType();
        Boolean includeUncharged = efdcCheckCreateReportVO.getIncludeUncharged();
        List<String> formKeys = efdcCheckCreateReportVO.getFormKeyData();
        IRunTimeViewController runTimeViewController = (IRunTimeViewController)SpringContextUtils.getBean(IRunTimeViewController.class);
        FormSchemeDefine schemeDefine = runTimeViewController.getFormScheme(schemeId);
        if (null == schemeDefine) {
            throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.efdcDataCheck.notFoundReportScheme"));
        }
        YearPeriodObject yp = new YearPeriodObject(null, periodStr);
        GcOrgCenterService tool = GcOrgPublicTool.getInstance((String)orgType, (GcAuthorityType)GcAuthorityType.ACCESS, (YearPeriodObject)yp);
        PeriodWrapper periodWrapper = new PeriodWrapper(periodStr);
        HashSet<String> orignOrgIds = new HashSet<String>();
        Set<String> orgIds = this.getAllOrgIdContainsSelf(tool, orgCodeList, orignOrgIds);
        List currOrgVersionAllOrgs = tool.listAllOrgByParentIdContainsSelf(null);
        List currOrgVersionAllOrgIds = currOrgVersionAllOrgs.stream().map(GcOrgCacheVO::getId).collect(Collectors.toList());
        List dimensionOrgIds = new ArrayList<String>(orgIds).stream().sorted((o1, o2) -> {
            if (currOrgVersionAllOrgIds.indexOf(o1) > currOrgVersionAllOrgIds.indexOf(o2)) {
                return 1;
            }
            return -1;
        }).collect(Collectors.toList());
        formKeys = this.checkOrgLengthAndForm(formKeys, orgIds, schemeId, isPlanTask);
        Map<String, Set<String>> reportZbDataMap = this.parseReportZbData(efdcCheckCreateReportVO, formKeys);
        StringBuffer org = new StringBuffer(512);
        for (String uuid : dimensionOrgIds) {
            org.append(uuid).append(";");
        }
        org.setLength(org.length() - 1);
        ContextUser user = NpContextHolder.getContext().getUser();
        if (DimensionUtils.isExistAdjust((String)taskId) && StringUtils.isEmpty((String)adjustCode)) {
            adjustCode = "0";
            efdcCheckCreateReportVO.setAdjustCode(adjustCode);
        }
        Map dimensionSetMap = DimensionUtils.buildDimensionMap((String)taskId, (String)"CNY", (String)periodStr, (String)orgType, (String)org.toString(), (String)adjustCode);
        GcBatchEfdcCheckInfo batchCalculateInfo = new GcBatchEfdcCheckInfo();
        batchCalculateInfo.setDimensionSet(dimensionSetMap);
        batchCalculateInfo.setTaskKey(taskId);
        batchCalculateInfo.setFormSchemeKey(schemeId);
        batchCalculateInfo.setDbTask(false);
        batchCalculateInfo.setReportZbDataMap(reportZbDataMap);
        batchCalculateInfo.setOrgIds(orgIds);
        batchCalculateInfo.setOrignOrgIds(orignOrgIds);
        batchCalculateInfo.setPeriodTitle(periodWrapper.toTitleString());
        batchCalculateInfo.setGroupByReport("groupByReport".equals(groupMode));
        batchCalculateInfo.setFormKeys(formKeys);
        batchCalculateInfo.setUserName(user.getName());
        batchCalculateInfo.setIncludeUncharged(includeUncharged);
        if (!StringUtils.isEmpty((String)efdcCheckCreateReportVO.getFileName())) {
            batchCalculateInfo.setFileName(efdcCheckCreateReportVO.getFileName());
        }
        return batchCalculateInfo;
    }

    private Set<String> getAllOrgIdContainsSelf(GcOrgCenterService tool, List<String> orgCodeList, Set<String> orignOrgIds) {
        HashSet<String> filterOrgSet = new HashSet<String>();
        for (String orgCode : orgCodeList) {
            List orgCacheVOS;
            if (filterOrgSet.contains(orgCode) || ObjectUtils.isEmpty(orgCacheVOS = tool.listAllOrgByParentIdContainsSelf(orgCode))) continue;
            orignOrgIds.add(orgCode);
            filterOrgSet.addAll(orgCacheVOS.stream().map(GcOrgCacheVO::getCode).collect(Collectors.toSet()));
        }
        return filterOrgSet;
    }

    private List<String> checkOrgLengthAndForm(List<String> formKeys, Set<String> orgIds, String schemeId, boolean isPlanTask) {
        List<DataCheckConfigEO> dataCheckConfigEOs = this.dataCheckConfigDAO.queryBySchemeId(schemeId);
        if (CollectionUtils.isEmpty(dataCheckConfigEOs)) {
            throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.efdcDataCheck.noSetEfdcScheme"));
        }
        String formsInfoJsonStr = dataCheckConfigEOs.get(0).getFormsInfo();
        if (!isPlanTask && dataCheckConfigEOs.get(0).getOrgMaxLength() != 0 && dataCheckConfigEOs.get(0).getOrgMaxLength() < orgIds.size()) {
            throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.efdcDataCheck.unitsNumberExceedsThreshold", (Object[])new Object[]{String.valueOf(dataCheckConfigEOs.get(0).getOrgMaxLength())}));
        }
        List formInfos = (List)JsonUtils.readValue((String)formsInfoJsonStr, (TypeReference)new TypeReference<List<EfdcCheckConfigFormInfo>>(){});
        if (CollectionUtils.isEmpty((Collection)formInfos)) {
            throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.efdcDataCheck.noEFDCReports"));
        }
        Set formKeySet = formInfos.stream().map(EfdcCheckConfigFormInfo::getFormKey).collect(Collectors.toSet());
        if (formKeySet.contains(UUIDUtils.emptyUUIDStr())) {
            return formKeys;
        }
        IRunTimeViewController runTimeViewController = (IRunTimeViewController)SpringContextUtils.getBean(IRunTimeViewController.class);
        HashSet<String> formInfoSet = new HashSet<String>();
        for (EfdcCheckConfigFormInfo form : formInfos) {
            if ("group".equals(form.getFormType())) {
                HashSet<String> groupKeys = new HashSet<String>();
                groupKeys.add(form.getFormKey());
                formInfoSet.addAll(this.getGroupAllChildForm(runTimeViewController, groupKeys));
                continue;
            }
            formInfoSet.add(form.getFormKey());
        }
        if (formKeys.contains(UUIDUtils.emptyUUIDStr())) {
            return new ArrayList<String>(formInfoSet);
        }
        return formKeys.stream().filter(item -> formInfoSet.contains(item)).collect(Collectors.toList());
    }

    private Set<String> getGroupAllChildForm(IRunTimeViewController runTimeViewController, Set<String> groupKeys) {
        HashSet<String> result = new HashSet<String>();
        try {
            for (String groupKey : groupKeys) {
                List formGroupDefines;
                List formDefines = runTimeViewController.getAllFormsInGroup(groupKey);
                if (!CollectionUtils.isEmpty((Collection)formDefines)) {
                    result.addAll(formDefines.stream().map(IBaseMetaItem::getKey).collect(Collectors.toSet()));
                }
                if (CollectionUtils.isEmpty((Collection)(formGroupDefines = runTimeViewController.getChildFormGroups(groupKey)))) continue;
                result.addAll(this.getGroupAllChildForm(runTimeViewController, formGroupDefines.stream().map(IBaseMetaItem::getKey).collect(Collectors.toSet())));
            }
            return result;
        }
        catch (Exception e) {
            throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.efdcDataCheck.filterReportsFaile"), (Throwable)e);
        }
    }

    @Override
    public void downloadExcel(String id, HttpServletResponse response) {
        response.setContentType("application/octet-stream");
        EfdcCheckReportLogEO reportLogEO = (EfdcCheckReportLogEO)this.dao.get((Serializable)((Object)id));
        File file = null;
        try {
            file = this.downloadFromOSS(reportLogEO.getId(), reportLogEO.getFileName());
            if (file == null || file.length() == 0L) {
                if (file != null) {
                    file.delete();
                }
                throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.efdcDataCheck.downloadEmptyFileFaile"));
            }
            String fileName = reportLogEO.getFileName();
            fileName = fileName.replaceAll("\\_[a-z0-9-]+", "");
            fileName = URLEncoder.encode(fileName, "UTF-8");
            response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
            response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
            try (FileInputStream fis = new FileInputStream(file);
                 BufferedInputStream bis = new BufferedInputStream(fis);
                 ServletOutputStream os = response.getOutputStream();){
                byte[] buffer = new byte[1024];
                int i = bis.read(buffer);
                while (i != -1) {
                    os.write(buffer, 0, i);
                    i = bis.read(buffer);
                }
            }
        }
        catch (BusinessRuntimeException e) {
            throw new BusinessRuntimeException((Throwable)e);
        }
        catch (Exception e) {
            throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.efdcDataCheck.downloadExecelFileFaile"), (Throwable)e);
        }
        finally {
            if (file != null) {
                file.delete();
            }
        }
    }

    @Override
    public String efdcCheckUser(EfdcCheckUserVo efdcCheckUserVo) {
        BASE64Decoder decoder = new BASE64Decoder();
        String passWord = null;
        try {
            passWord = new String(decoder.decodeBuffer(efdcCheckUserVo.getPassWord()));
        }
        catch (IOException e) {
            throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.efdcDataCheck.parsePasswordFaile"), (Throwable)e);
        }
        NvwaLoginUserDTO nvwaLoginUser = new NvwaLoginUserDTO();
        nvwaLoginUser.setUsername(efdcCheckUserVo.getUserName());
        nvwaLoginUser.setPwd(passWord);
        nvwaLoginUser.setTenant(efdcCheckUserVo.getTenant());
        R loginResult = this.nvwaLoginService.tryLogin(nvwaLoginUser, false);
        if (loginResult == null) {
            throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.efdcDataCheck.checkFaile"));
        }
        if (loginResult.getCode() != 0) {
            throw new BusinessRuntimeException(loginResult.getMsg());
        }
        return GcI18nUtil.getMessage((String)"gc.efdcDataCheck.checkSuccess");
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public String shareFile(GcEfdcShareFileVO gcEfdcShareFileVO) {
        String userName = NpContextHolder.getContext().getUserName();
        List users = gcEfdcShareFileVO.getUsers();
        List fileIds = gcEfdcShareFileVO.getFileIds();
        List<EfdcCheckReportLogEO> checkReportByRecids = this.dao.findCheckReportByRecids(new HashSet<String>(fileIds));
        ArrayList<EfdcCheckReportLogShareEO> addBatchReport = new ArrayList<EfdcCheckReportLogShareEO>();
        ArrayList<EfdcCheckReportLogShareEO> updateBatchReport = new ArrayList<EfdcCheckReportLogShareEO>();
        for (UserBase user : users) {
            if (!this.userService.exists(user.getName())) {
                throw new BusinessRuntimeException("\u672a\u627e\u5230" + user.getName() + "\u7528\u6237\uff0c\u5171\u4eab\u5931\u8d25");
            }
            if (userName.equals(user.getName())) continue;
            List<EfdcCheckReportLogShareEO> logShareEOS = this.reportShareDAO.queryShareEoByUserAndFileKeys(user.getName(), fileIds);
            Map<String, EfdcCheckReportLogShareEO> fileKey2EoMap = logShareEOS.stream().collect(Collectors.toMap(EfdcCheckReportLogShareEO::getFileKey, a -> a));
            for (EfdcCheckReportLogEO checkReport : checkReportByRecids) {
                if (checkReport.getCreateUser().equals(user.getName())) continue;
                if (fileKey2EoMap.containsKey(checkReport.getId())) {
                    EfdcCheckReportLogShareEO efdcCheckReportLogShareEO = fileKey2EoMap.get(checkReport.getId());
                    efdcCheckReportLogShareEO.setSharedDate(new Date());
                    updateBatchReport.add(efdcCheckReportLogShareEO);
                    continue;
                }
                EfdcCheckReportLogShareEO eo = new EfdcCheckReportLogShareEO();
                eo.setId(UUIDUtils.newUUIDStr());
                eo.setSharedDate(new Date());
                eo.setSharedUser(user.getName());
                eo.setFileKey(checkReport.getId());
                addBatchReport.add(eo);
            }
        }
        if (!CollectionUtils.isEmpty(updateBatchReport)) {
            this.reportShareDAO.updateBatch(updateBatchReport);
        }
        if (!CollectionUtils.isEmpty(addBatchReport)) {
            this.reportShareDAO.addBatch(addBatchReport);
        }
        return "\u5171\u4eab\u6210\u529f";
    }

    private Map<String, Set<String>> parseReportZbData(EfdcCheckCreateReportVO efdcCheckCreateReportVO, List<String> formKeys) {
        Map reportZbDataMap = efdcCheckCreateReportVO.getReportZbData();
        HashSet<String> formKeySet = new HashSet<String>(formKeys);
        for (Map.Entry entry : reportZbDataMap.entrySet()) {
            String reportGuid = (String)entry.getKey();
            if (!formKeySet.contains(reportGuid)) continue;
            HashSet<String> zbSet = new HashSet<String>();
            Set zbIds = (Set)entry.getValue();
            for (String zbId : zbIds) {
                zbSet.add(zbId);
            }
            reportZbDataMap.put(reportGuid, zbSet);
        }
        return reportZbDataMap;
    }
}

