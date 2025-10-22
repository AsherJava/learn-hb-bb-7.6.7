/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.plantask.extend.job.PlanTaskRunner
 *  com.jiuqi.common.plantask.extend.job.Runner
 *  com.jiuqi.gcreport.archive.api.scheme.vo.ArchiveContext
 *  com.jiuqi.gcreport.archive.api.scheme.vo.EFSResponseData
 *  com.jiuqi.gcreport.archive.api.scheme.vo.SendArchiveItemVO
 *  com.jiuqi.gcreport.archive.api.scheme.vo.SendArchiveVO
 */
package com.jiuqi.gcreport.archive.service.impl;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.plantask.extend.job.PlanTaskRunner;
import com.jiuqi.common.plantask.extend.job.Runner;
import com.jiuqi.gcreport.archive.api.scheme.vo.ArchiveContext;
import com.jiuqi.gcreport.archive.api.scheme.vo.EFSResponseData;
import com.jiuqi.gcreport.archive.api.scheme.vo.SendArchiveItemVO;
import com.jiuqi.gcreport.archive.api.scheme.vo.SendArchiveVO;
import com.jiuqi.gcreport.archive.common.ArchiveProperties;
import com.jiuqi.gcreport.archive.common.ArchiveResAppEnum;
import com.jiuqi.gcreport.archive.common.ArchiveStatusEnum;
import com.jiuqi.gcreport.archive.dao.ArchiveInfoDao;
import com.jiuqi.gcreport.archive.entity.ArchiveInfoEO;
import com.jiuqi.gcreport.archive.service.GcArchiveService;
import com.jiuqi.gcreport.archive.service.GcNotifyEFSService;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;

@PlanTaskRunner(id="F99D8DF671FB45FD9FBB192FCB603236", settingPage="", name="com.jiuqi.gcreport.archive.service.impl.ArchiveRunner", title="\u5f52\u6863\u53d1\u9001\u5f52\u6863\u4fe1\u606f\u8ba1\u5212\u4efb\u52a1")
public class ArchiveRunner
extends Runner {
    @Autowired
    private GcArchiveService gcArchiveService;
    @Autowired
    private ArchiveProperties archiveProperties;
    @Autowired
    private ArchiveInfoDao archiveInfoDao;
    @Autowired
    private GcNotifyEFSService gcNotifyEFSService;

    public boolean excute(String runnerParameter) {
        this.appendLog("\u5904\u7406\u9700\u8981\u53d1\u9001\u4fe1\u606f\u5230\u6863\u6848\u7cfb\u7edf\u7684\u6863\u6848\n");
        StringBuffer log = new StringBuffer();
        List<ArchiveInfoEO> needSendArchive = this.gcArchiveService.getNeedSendArchive();
        int failedCount = 0;
        List<List<ArchiveInfoEO>> needSendArchiveSplitList = this.splitList(needSendArchive, this.archiveProperties.getSendItemMaxSize());
        if (CollectionUtils.isEmpty(needSendArchiveSplitList)) {
            this.appendLog("\u672c\u6b21\u5904\u7406\u6ca1\u6709\u9700\u8981\u53d1\u9001\u5230\u6863\u6848\u7cfb\u7edf\u7684\u6570\u636e\n");
        } else {
            for (List<ArchiveInfoEO> archiveInfoEOList : needSendArchiveSplitList) {
                failedCount += this.resendArchive(archiveInfoEOList, log);
            }
            log.insert(0, "\u672c\u6b21\u5904\u7406\u9700\u8981\u53d1\u9001\u5230\u6863\u6848\u7cfb\u7edf\u7684\u6570\u636e\u5171" + needSendArchive.size() + "\u6761,\u6210\u529f" + (needSendArchive.size() - failedCount) + "\u6761,\u5931\u8d25" + failedCount + "\u6761");
        }
        return failedCount == 0;
    }

    private int resendArchive(List<ArchiveInfoEO> needSendArchive, StringBuffer log) {
        if (CollectionUtils.isEmpty(needSendArchive)) {
            log.append("\u672c\u6b21\u5904\u7406\u6ca1\u6709\u9700\u8981\u53d1\u9001\u5230\u6863\u6848\u7cfb\u7edf\u7684\u6570\u636e");
            return 0;
        }
        StringBuffer resendLog = new StringBuffer();
        int failedCount = 0;
        ArrayList<SendArchiveItemVO> corporateItem = new ArrayList<SendArchiveItemVO>();
        ArrayList<SendArchiveItemVO> managementItem = new ArrayList<SendArchiveItemVO>();
        SendArchiveVO corporate = new SendArchiveVO(ArchiveResAppEnum.CORPORATE_RES_APP.getResAppCode(), corporateItem);
        SendArchiveVO management = new SendArchiveVO(ArchiveResAppEnum.MANAGEMENT_RES_APP.getResAppCode(), managementItem);
        for (ArchiveInfoEO eo : needSendArchive) {
            SendArchiveItemVO sendArchiveItemVO = (SendArchiveItemVO)JsonUtils.readValue((String)eo.getDataJson(), SendArchiveItemVO.class);
            sendArchiveItemVO.setF_UUID(eo.getId());
            if (ArchiveResAppEnum.CORPORATE_RES_APP.getResAppCode().equals(eo.getResApp())) {
                corporateItem.add(sendArchiveItemVO);
                continue;
            }
            if (!ArchiveResAppEnum.MANAGEMENT_RES_APP.getResAppCode().equals(eo.getResApp())) continue;
            managementItem.add(sendArchiveItemVO);
        }
        ArrayList<EFSResponseData> responseData = new ArrayList<EFSResponseData>();
        try {
            if (!CollectionUtils.isEmpty(corporateItem)) {
                responseData.addAll(this.gcNotifyEFSService.notifyEFSArchive(corporate));
            }
            if (!CollectionUtils.isEmpty(managementItem)) {
                responseData.addAll(this.gcNotifyEFSService.notifyEFSArchive(management));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            log.append("\u53d1\u9001\u5f52\u6863\u4fe1\u606f\u5230\u6863\u6848\u7cfb\u7edf\u51fa\u73b0\u5f02\u5e38\uff1a" + e.getMessage());
            return needSendArchive.size();
        }
        Map responseDataMap = responseData.stream().collect(Collectors.toMap(EFSResponseData::getF_UUID, Function.identity()));
        for (ArchiveInfoEO eo : needSendArchive) {
            EFSResponseData efsResponseData = (EFSResponseData)responseDataMap.get(eo.getId());
            eo.setUpdateDate(new Date());
            if (efsResponseData == null || efsResponseData.getCODE() == null || "1".equals(efsResponseData.getERRCODE())) {
                eo.setStatus(ArchiveStatusEnum.SEND_FAILED.getStatus());
                eo.setRetryCount(eo.getRetryCount() + 1);
                eo.setErrorInfo(efsResponseData == null ? "" : efsResponseData.getMESSAGE());
                ++failedCount;
                resendLog.append(this.getArchiveInfoString(eo) + "\u53d1\u9001\u5f52\u6863\u4fe1\u606f\u5931\u8d25,\u5931\u8d25\u6b21\u6570\uff1a" + eo.getRetryCount() + ",\u5931\u8d25\u539f\u56e0\uff1a" + (efsResponseData == null ? "\u65e0\u8fd4\u56de\u4fe1\u606f" : efsResponseData.getMESSAGE()) + "\n");
                continue;
            }
            if ("0".equals(efsResponseData.getCODE()) || "3".equals(efsResponseData.getERRCODE())) {
                resendLog.append(this.getArchiveInfoString(eo) + "\u53d1\u9001\u5f52\u6863\u4fe1\u606f\u6210\u529f\n");
                eo.setFastdfsFilePath(efsResponseData.getF_EXCELPATH() + ";" + efsResponseData.getF_PDFPATH());
                eo.setStatus(ArchiveStatusEnum.SEND_SUCCESS.getStatus());
                eo.setRetryCount(0);
                eo.setErrorInfo(efsResponseData.getMESSAGE());
                continue;
            }
            if (!"2".equals(efsResponseData.getERRCODE())) continue;
            eo.setStatus(ArchiveStatusEnum.UPLOAD_FAILED.getStatus());
            ++failedCount;
            eo.setErrorInfo(efsResponseData.getMESSAGE());
            resendLog.append(this.getArchiveInfoString(eo) + "\u53d1\u9001\u5f52\u6863\u4fe1\u606f\u5931\u8d25,\u5931\u8d25\u6b21\u6570\uff1a" + (eo.getRetryCount() + 1) + ",\u5931\u8d25\u539f\u56e0\uff1a" + efsResponseData.getMESSAGE() + "  \u8f6c\u5b58\u5931\u8d25\uff0c\u4e0b\u6b21\u6267\u884c\u8ba1\u5212\u4efb\u52a1\u65f6\u91cd\u65b0\u4e0a\u4f20\u5f52\u6863\u6587\u4ef6\uff01\n");
            eo.setRetryCount(0);
        }
        this.archiveInfoDao.updateAll(needSendArchive);
        log.append(resendLog);
        return failedCount;
    }

    private boolean reuploadArchive(List<ArchiveInfoEO> needUploadArchive, StringBuffer log) {
        if (CollectionUtils.isEmpty(needUploadArchive)) {
            log.append("\u672c\u6b21\u5904\u7406\u6ca1\u6709\u9700\u8981\u91cd\u65b0\u4e0a\u4f20ftp\u7684\u6570\u636e");
            return true;
        }
        StringBuffer reuploadLog = new StringBuffer();
        int failedCount = 0;
        for (ArchiveInfoEO eo : needUploadArchive) {
            ArchiveContext archiveContext = (ArchiveContext)JsonUtils.readValue((String)eo.getExportParam(), ArchiveContext.class);
        }
        this.archiveInfoDao.updateAll(needUploadArchive);
        reuploadLog.insert(0, "\u672c\u6b21\u5904\u7406\u9700\u8981\u91cd\u65b0\u4e0a\u4f20ftp\u6570\u636e\u5171" + needUploadArchive.size() + "\u6761,\u6210\u529f" + (needUploadArchive.size() - failedCount) + "\u6761,\u5931\u8d25" + failedCount + "\u6761");
        log.append(reuploadLog);
        return failedCount == 0;
    }

    private List<List<ArchiveInfoEO>> splitList(List<ArchiveInfoEO> list, int len) {
        if (list == null || list.size() == 0 || len < 1) {
            return null;
        }
        ArrayList<List<ArchiveInfoEO>> result = new ArrayList<List<ArchiveInfoEO>>();
        int size = list.size();
        int count = (size + len - 1) / len;
        for (int i = 0; i < count; ++i) {
            List<ArchiveInfoEO> subList = list.subList(i * len, (i + 1) * len > size ? size : len * (i + 1));
            result.add(subList);
        }
        return result;
    }

    private String getArchiveInfoString(ArchiveInfoEO eo) {
        return "[\u5355\u4f4d\uff1a" + eo.getUnitId() + ",\u65f6\u671f\uff1a" + eo.getDefaultPeriod() + ",\u62a5\u8868\u65b9\u6848\uff1a" + eo.getSchemeId() + "]";
    }
}

