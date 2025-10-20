/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.reportdatasync.dto.ReportDataSyncParamFileDTO
 *  com.jiuqi.gcreport.reportdatasync.enums.SyncTypeEnums
 *  com.jiuqi.gcreport.reportdatasync.vo.ReportDataCheckParam
 *  com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncIssuedLogVO
 *  com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncServerInfoVO
 *  com.jiuqi.gcreport.reportdatasync.vo.ReportsyncDataLoadParam
 *  com.jiuqi.va.domain.org.OrgDO
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.gcreport.reportdatasync.scheduler;

import com.jiuqi.gcreport.reportdatasync.dto.ReportDataSyncParamFileDTO;
import com.jiuqi.gcreport.reportdatasync.enums.SyncTypeEnums;
import com.jiuqi.gcreport.reportdatasync.vo.ReportDataCheckParam;
import com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncIssuedLogVO;
import com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncServerInfoVO;
import com.jiuqi.gcreport.reportdatasync.vo.ReportsyncDataLoadParam;
import com.jiuqi.va.domain.org.OrgDO;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.web.multipart.MultipartFile;

public abstract class MultilevelExtendHandler {
    public Boolean rejectReportData(ReportDataSyncServerInfoVO serverInfoVO, String rejectMsgJson, SyncTypeEnums typeEnums) {
        return true;
    }

    public Boolean modifyLoadingResults(ReportDataSyncServerInfoVO serverInfoVO, ReportsyncDataLoadParam reportsyncDataLoadParam) {
        return true;
    }

    public List<OrgDO> getOrgDO(ReportDataSyncServerInfoVO serverInfoVO, ReportDataCheckParam checkParam) {
        return null;
    }

    public Boolean enableDataImport(ReportDataSyncServerInfoVO serverInfoVO, String taskId) {
        return null;
    }

    public List<ReportDataSyncIssuedLogVO> fetchTargetSyncParamTaskInfos(ReportDataSyncServerInfoVO serverInfoVO) {
        return new ArrayList<ReportDataSyncIssuedLogVO>();
    }

    public ReportDataSyncParamFileDTO fetchSyncParamFiles(ReportDataSyncServerInfoVO serverInfoVO, ReportDataSyncIssuedLogVO issuedLogVO) {
        return null;
    }

    public MultipartFile fetchReportData(ReportDataSyncServerInfoVO serverInfoVO, Map<String, String> params) {
        return null;
    }
}

