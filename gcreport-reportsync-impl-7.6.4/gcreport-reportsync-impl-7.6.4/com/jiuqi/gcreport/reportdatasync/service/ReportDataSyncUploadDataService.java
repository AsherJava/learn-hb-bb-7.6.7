/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.PageInfo
 *  com.jiuqi.common.expimp.dataimport.common.ImportContext
 *  com.jiuqi.gcreport.reportdatasync.vo.ReportDataCheckParam
 *  com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncRejectParams
 *  com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncUploadCheckParamVO
 *  com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncUploadDataTaskVO
 *  com.jiuqi.gcreport.reportdatasync.vo.ReportDataUploadToFtpVO
 *  com.jiuqi.va.domain.org.OrgDO
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.gcreport.reportdatasync.service;

import com.jiuqi.common.base.http.PageInfo;
import com.jiuqi.common.expimp.dataimport.common.ImportContext;
import com.jiuqi.gcreport.reportdatasync.vo.ReportDataCheckParam;
import com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncRejectParams;
import com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncUploadCheckParamVO;
import com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncUploadDataTaskVO;
import com.jiuqi.gcreport.reportdatasync.vo.ReportDataUploadToFtpVO;
import com.jiuqi.va.domain.org.OrgDO;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import org.springframework.web.multipart.MultipartFile;

public interface ReportDataSyncUploadDataService {
    public Boolean saveUploadDataTask(ReportDataSyncUploadDataTaskVO var1);

    public Boolean saveUploadDataTaskAndCommit(ReportDataSyncUploadDataTaskVO var1);

    public PageInfo<ReportDataSyncUploadDataTaskVO> listAllExecutingTask(Integer var1, Integer var2);

    public Boolean dataLoading(String var1, boolean var2);

    public Boolean stopTask(String var1);

    public PageInfo<ReportDataSyncUploadDataTaskVO> listAllFinishedTask(Integer var1, Integer var2);

    public Boolean rejectTask(ReportDataSyncRejectParams var1);

    public String checkUploadReportData(ReportDataSyncUploadCheckParamVO var1);

    public String uploadJioReportData(MultipartFile var1, ReportDataSyncUploadDataTaskVO var2);

    public Boolean importReportDataFile(MultipartFile var1, ImportContext var2) throws IOException;

    public Boolean importReportDataFile(MultipartFile var1, ReportDataSyncUploadDataTaskVO var2, ImportContext var3) throws IOException;

    public List<OrgDO> getOrgDO(ReportDataCheckParam var1);

    public Boolean importFromFtp(ReportDataUploadToFtpVO var1);

    public Boolean fetchReportData(Map<String, String> var1);
}

