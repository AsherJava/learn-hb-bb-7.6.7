/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.PageInfo
 *  com.jiuqi.common.reportsync.param.ReportDataParam
 *  com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncUploadDataTaskVO
 *  com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncUploadLogQueryParamVO
 *  com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncUploadLogVO
 *  com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncUploadSchemeVO
 *  com.jiuqi.gcreport.reportdatasync.vo.ReportDataUploadToFtpVO
 *  com.jiuqi.gcreport.reportdatasync.vo.ReportsyncDataLoadParam
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 */
package com.jiuqi.gcreport.reportdatasync.service;

import com.jiuqi.common.base.http.PageInfo;
import com.jiuqi.common.reportsync.param.ReportDataParam;
import com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncUploadDataTaskVO;
import com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncUploadLogQueryParamVO;
import com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncUploadLogVO;
import com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncUploadSchemeVO;
import com.jiuqi.gcreport.reportdatasync.vo.ReportDataUploadToFtpVO;
import com.jiuqi.gcreport.reportdatasync.vo.ReportsyncDataLoadParam;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface ReportDataSyncUploadService {
    public String saveUploadScheme(ReportDataSyncUploadSchemeVO var1);

    public Boolean deleteUploadScheme(String var1);

    public String updateUploadScheme(ReportDataSyncUploadSchemeVO var1);

    public String queryUploadSchemePeriodTitle(String var1);

    public Boolean uploadReportData(ReportDataParam var1);

    public Boolean isExistInputTable(String var1);

    public Boolean rejectReportData(ReportDataSyncUploadDataTaskVO var1);

    public ReportDataSyncUploadSchemeVO getUploadSchemeById(String var1);

    public List<ReportDataSyncUploadSchemeVO> listAllUploadScheme();

    public List<ReportDataSyncUploadSchemeVO> listAllUploadSchemeTree();

    public List<ReportDataSyncUploadSchemeVO> listAllSchemeGroupTree();

    public PageInfo<ReportDataSyncUploadLogVO> listUploadLogsBySchemeId(ReportDataSyncUploadLogQueryParamVO var1);

    public void downloadReportData(HttpServletRequest var1, HttpServletResponse var2, String var3);

    public Boolean modifyLoadingResults(ReportsyncDataLoadParam var1);

    public Boolean uploadToFtp(ReportDataUploadToFtpVO var1);

    public List<Map<String, String>> listAllMappingScheme();
}

