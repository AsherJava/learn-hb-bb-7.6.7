/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.archive.api.scheme.vo.ArchiveConfigFormInfo
 *  com.jiuqi.gcreport.archive.api.scheme.vo.ArchiveContext
 *  com.jiuqi.gcreport.nr.impl.uploadstate.util.FormUploadStateTool
 *  com.jiuqi.gcreport.nr.impl.uploadstate.util.UploadStateTool
 *  com.jiuqi.gcreport.nr.impl.uploadstate.vo.DimensionParamsVO
 *  com.jiuqi.np.core.exception.BusinessException
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.nr.bpm.upload.UploadState
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.data.access.api.IStateFormLockService
 *  com.jiuqi.nr.data.access.api.param.LockParam
 *  com.jiuqi.nr.data.access.util.DimCollectionBuildUtil
 *  com.jiuqi.nr.data.access.util.DimensionValueSetUtil
 *  com.jiuqi.nr.dataentry.bean.ExportParam
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 */
package com.jiuqi.gcreport.archive.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.archive.api.scheme.vo.ArchiveConfigFormInfo;
import com.jiuqi.gcreport.archive.api.scheme.vo.ArchiveContext;
import com.jiuqi.gcreport.archive.common.ArchiveStatusEnum;
import com.jiuqi.gcreport.archive.common.UploadStatus;
import com.jiuqi.gcreport.archive.dao.ArchiveInfoDao;
import com.jiuqi.gcreport.archive.dao.ArchiveLogDao;
import com.jiuqi.gcreport.archive.entity.ArchiveConfigEO;
import com.jiuqi.gcreport.archive.entity.ArchiveInfoEO;
import com.jiuqi.gcreport.archive.entity.ArchiveLogEO;
import com.jiuqi.gcreport.archive.util.ftp.ArchiveFTPUtil;
import com.jiuqi.gcreport.archive.util.sftp.ArchiveSFTPUtil;
import com.jiuqi.gcreport.nr.impl.uploadstate.util.FormUploadStateTool;
import com.jiuqi.gcreport.nr.impl.uploadstate.util.UploadStateTool;
import com.jiuqi.gcreport.nr.impl.uploadstate.vo.DimensionParamsVO;
import com.jiuqi.np.core.exception.BusinessException;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.nr.bpm.upload.UploadState;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.data.access.api.IStateFormLockService;
import com.jiuqi.nr.data.access.api.param.LockParam;
import com.jiuqi.nr.data.access.util.DimCollectionBuildUtil;
import com.jiuqi.nr.data.access.util.DimensionValueSetUtil;
import com.jiuqi.nr.dataentry.bean.ExportParam;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.BeanUtils;

public class ArchiveUploadFieldUtil {
    public static String upload(InputStream stream, String remoteFileName, int localSize, boolean isSFTP) {
        UploadStatus uploadStatus = null;
        uploadStatus = !isSFTP ? ArchiveFTPUtil.upload(stream, remoteFileName, localSize) : ArchiveSFTPUtil.upload(remoteFileName, stream);
        switch (uploadStatus) {
            case CREATE_DIRECTORY_FAIL: {
                throw new BusinessRuntimeException("\u521b\u5efa\u76ee\u5f55\u3010" + remoteFileName + "\u3011\u5931\u8d25");
            }
            case UPLOAD_NEW_FILE_FAILED: {
                throw new BusinessRuntimeException("\u4e0a\u4f20\u6587\u4ef6\u3010" + remoteFileName + "\u3011\u5931\u8d25");
            }
            case FILE_EXITS: {
                throw new BusinessRuntimeException("\u6587\u4ef6\u3010" + remoteFileName + "\u3011\u5df2\u5b58\u5728");
            }
            case UPLOAD_FROM_BREAK_FAILED: {
                throw new BusinessRuntimeException("\u6587\u4ef6\u3010" + remoteFileName + "\u3011\u65ad\u70b9\u7eed\u4f20\u5931\u8d25");
            }
            case DELETE_REMOTE_FAILD: {
                throw new BusinessRuntimeException("\u5220\u9664\u3010" + remoteFileName + "\u3011\u6587\u4ef6\u5931\u8d25");
            }
        }
        return uploadStatus.name();
    }

    public static boolean duplicateChecking(JtableContext context, String unit, String defaultPeriod) {
        ArchiveInfoDao archiveInfoDao = (ArchiveInfoDao)SpringContextUtils.getBean(ArchiveInfoDao.class);
        List<ArchiveInfoEO> oldList = archiveInfoDao.queryByUnitAndPeriod(context, unit, defaultPeriod);
        if (!CollectionUtils.isEmpty(oldList)) {
            for (ArchiveInfoEO eo : oldList) {
                if (eo.getStatus() != null && ArchiveStatusEnum.CANCEL_ARCHIVE.getStatus() == eo.getStatus().intValue()) continue;
                return true;
            }
        }
        return false;
    }

    public static boolean duplicateChecking(JtableContext context, String unit, String defaultPeriod, String orgType) {
        ArchiveInfoDao archiveInfoDao = (ArchiveInfoDao)SpringContextUtils.getBean(ArchiveInfoDao.class);
        List<ArchiveInfoEO> oldList = archiveInfoDao.queryByUnitPeriodAndOrgType(context, unit, orgType, defaultPeriod);
        if (!CollectionUtils.isEmpty(oldList)) {
            for (ArchiveInfoEO eo : oldList) {
                if (eo.getStatus() != null && ArchiveStatusEnum.CANCEL_ARCHIVE.getStatus() == eo.getStatus().intValue()) continue;
                return true;
            }
        }
        return false;
    }

    public static ArchiveInfoEO initArchiveInfoEO(ArchiveContext context, String unit, String defaultPeriod, String username, ArchiveLogEO logEO) {
        ArchiveInfoEO eo = new ArchiveInfoEO();
        eo.setUnitId(unit);
        eo.setAdjustCode(context.getDimensionSet().get("ADJUST") == null ? "0" : ((DimensionValue)context.getDimensionSet().get("ADJUST")).getValue());
        eo.setDefaultPeriod(defaultPeriod);
        eo.setTaskId(context.getTaskKey());
        eo.setSchemeId(context.getFormSchemeKey());
        eo.setCreateDate(new Date());
        eo.setUpdateDate(new Date());
        eo.setRetryCount(0);
        eo.setCreateUser(username);
        eo.setExportParam(JsonUtils.writeValueAsString((Object)context));
        eo.setLogId(logEO.getId());
        return eo;
    }

    public static boolean alreadyHasTask(JtableContext context, String unit, String defaultPeriod, String id) {
        ArchiveLogDao archiveLogDao = (ArchiveLogDao)SpringContextUtils.getBean(ArchiveLogDao.class);
        List<ArchiveLogEO> inExecTaskLogByUnit = archiveLogDao.getInExecTaskLogByUnit(context.getTaskKey(), context.getFormSchemeKey(), unit, id);
        List<ArchiveLogEO> archiveLogEOList = inExecTaskLogByUnit;
        if (CollectionUtils.isEmpty(archiveLogEOList)) {
            return false;
        }
        PeriodWrapper defaultPeriodWrapper = new PeriodWrapper(defaultPeriod);
        for (ArchiveLogEO archiveLogEO : archiveLogEOList) {
            String endPeriod;
            String startPeriod = archiveLogEO.getStartPeriod();
            PeriodWrapper startPeriodWrapper = new PeriodWrapper(startPeriod);
            if (defaultPeriodWrapper.getType() != startPeriodWrapper.getType() || defaultPeriod.compareTo(startPeriod) < 0 || defaultPeriod.compareTo(endPeriod = archiveLogEO.getEndPeriod()) > 0) continue;
            return true;
        }
        return false;
    }

    public static ExportParam initExportParam(JtableContext context, Set<String> formKeys, boolean isExcel) {
        String formStr;
        if (context == null) {
            throw new BusinessException("\u8bf7\u9009\u62e9\u76f8\u5173\u5f52\u6863\u53c2\u6570\uff01");
        }
        JtableContext copy = new JtableContext();
        BeanUtils.copyProperties(context, copy);
        ExportParam exportParam = new ExportParam();
        exportParam.setBackground(true);
        exportParam.setExportEmptyTable(true);
        if (isExcel) {
            exportParam.setType("EXPORT_EXCEL");
            formStr = ArchiveUploadFieldUtil.getFormsString(formKeys);
        } else {
            exportParam.setType("EXPORT_PDF");
            formStr = ArchiveUploadFieldUtil.getFormsString(formKeys);
        }
        copy.setFormKey(formStr);
        exportParam.setContext(copy);
        return exportParam;
    }

    public static String getFormsString(Set<String> formKeys) {
        StringBuffer formStr = new StringBuffer();
        for (String formKey : formKeys) {
            formStr.append(formKey + ";");
        }
        formStr.deleteCharAt(formStr.length() - 1);
        return formStr.toString();
    }

    public static boolean hasNotLockedForm(ArchiveContext context, ArchiveConfigEO archiveConfigEO, List<String> formKeys) {
        DimensionParamsVO dimensionParamsVO = new DimensionParamsVO();
        dimensionParamsVO.setOrgId(context.getOrgCode());
        dimensionParamsVO.setPeriodStr(context.getDefaultPeriod());
        dimensionParamsVO.setTaskId(context.getTaskId());
        dimensionParamsVO.setSchemeId(context.getFormSchemeKey());
        Map dimensionSet = context.getDimensionSet();
        dimensionParamsVO.setCurrency(((DimensionValue)dimensionSet.get("MD_CURRENCY")).getValue());
        dimensionParamsVO.setOrgTypeId(((DimensionValue)dimensionSet.get("MD_GCORGTYPE")).getValue());
        dimensionParamsVO.setSelectAdjustCode(dimensionSet.get("ADJUST") == null ? "0" : ((DimensionValue)dimensionSet.get("ADJUST")).getValue());
        UploadState uploadSate = UploadStateTool.getInstance().getUploadSate(dimensionParamsVO, context.getOrgCode());
        switch (uploadSate) {
            case UPLOADED: 
            case CONFIRMED: {
                return false;
            }
        }
        HashSet<String> formSet = new HashSet<String>();
        List excelFormInfos = (List)JsonUtils.readValue((String)archiveConfigEO.getExcelFormInfos(), (TypeReference)new TypeReference<List<ArchiveConfigFormInfo>>(){});
        List pdfFormInfos = (List)JsonUtils.readValue((String)archiveConfigEO.getPdfFormInfos(), (TypeReference)new TypeReference<List<ArchiveConfigFormInfo>>(){});
        for (ArchiveConfigFormInfo archiveConfigFormInfo : excelFormInfos) {
            formSet.add(archiveConfigFormInfo.getFormKey());
        }
        for (ArchiveConfigFormInfo archiveConfigFormInfo : pdfFormInfos) {
            formSet.add(archiveConfigFormInfo.getFormKey());
        }
        if (!CollectionUtils.isEmpty(formKeys)) {
            formSet.retainAll(formKeys);
        }
        DimCollectionBuildUtil dimCollectionBuildUtil = (DimCollectionBuildUtil)SpringContextUtils.getBean(DimCollectionBuildUtil.class);
        IStateFormLockService stateFormLockService = (IStateFormLockService)SpringBeanUtils.getBean(IStateFormLockService.class);
        LockParam lockParamBase = new LockParam();
        lockParamBase.setTaskKey(context.getTaskKey());
        lockParamBase.setFormSchemeKey(context.getFormSchemeKey());
        lockParamBase.setMasterKeys(dimCollectionBuildUtil.buildDimensionCollectionNoFilter(DimensionValueSetUtil.getDimensionValueSet((Map)context.getDimensionSet()), context.getFormSchemeKey()));
        for (String formKey : formSet) {
            boolean isUploaded;
            UploadState uploadState = FormUploadStateTool.getInstance().queryUploadState(dimensionParamsVO, dimensionParamsVO.getOrgId(), formKey);
            boolean bl = isUploaded = UploadState.UPLOADED.equals((Object)uploadState) || UploadState.CONFIRMED.equals((Object)uploadState);
            if (isUploaded) continue;
            ArrayList<String> formKeyParam = new ArrayList<String>(Collections.singleton(formKey));
            lockParamBase.setFormKeys(formKeyParam);
            Boolean locked = stateFormLockService.isFormLocked(lockParamBase);
            if (locked.booleanValue()) continue;
            return true;
        }
        return false;
    }

    public static boolean hasNotLockedForm(ArchiveContext context, ArchiveConfigEO archiveConfigEO) {
        return ArchiveUploadFieldUtil.hasNotLockedForm(context, archiveConfigEO, new ArrayList<String>());
    }

    public static boolean formInfoIsNotEmpty(String formsInfoJsonStr) {
        List excelFormInfos = (List)JsonUtils.readValue((String)formsInfoJsonStr, (TypeReference)new TypeReference<List<ArchiveConfigFormInfo>>(){});
        return !CollectionUtils.isEmpty((Collection)excelFormInfos);
    }

    public static Set<String> listFormKeys(String formsInfoJsonStr) {
        if (StringUtils.isEmpty((String)formsInfoJsonStr)) {
            return CollectionUtils.newHashSet();
        }
        List excelFormInfos = (List)JsonUtils.readValue((String)formsInfoJsonStr, (TypeReference)new TypeReference<List<ArchiveConfigFormInfo>>(){});
        if (CollectionUtils.isEmpty((Collection)excelFormInfos)) {
            return CollectionUtils.newHashSet();
        }
        return excelFormInfos.stream().map(ArchiveConfigFormInfo::getFormKey).collect(Collectors.toSet());
    }
}

