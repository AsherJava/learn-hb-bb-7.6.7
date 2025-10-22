/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.PageInfo
 *  com.jiuqi.common.expimp.dataexport.excel.common.ExportExcelSheet
 *  com.jiuqi.common.reportsync.vo.ReportDataSyncParams
 *  com.jiuqi.gcreport.reportdatasync.dto.ReportDataSyncParamFileDTO
 *  com.jiuqi.gcreport.reportdatasync.dto.ReportDataSyncParamProgressDataDTO
 *  com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncIssuedLogItemVO
 *  com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncIssuedLogVO
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.gcreport.reportdatasync.service;

import com.jiuqi.common.base.http.PageInfo;
import com.jiuqi.common.expimp.dataexport.excel.common.ExportExcelSheet;
import com.jiuqi.common.reportsync.vo.ReportDataSyncParams;
import com.jiuqi.gcreport.reportdatasync.dto.ReportDataSyncParamFileDTO;
import com.jiuqi.gcreport.reportdatasync.dto.ReportDataSyncParamProgressDataDTO;
import com.jiuqi.gcreport.reportdatasync.enums.RetryParamSyncBatchType;
import com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncIssuedLogItemVO;
import com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncIssuedLogVO;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;

public interface ReportDataSyncParamSyncService {
    public Boolean uploadParamsUpdateInstructions(String var1);

    public Boolean paramSync(String var1);

    public PageInfo<ReportDataSyncIssuedLogVO> listXfLogs(String var1, Integer var2, Integer var3);

    public void downloadParamUpateInstructions(HttpServletRequest var1, HttpServletResponse var2, String var3);

    public void downloadParam(HttpServletRequest var1, HttpServletResponse var2, String var3);

    public void downloadParamAndUpateInstructions(HttpServletRequest var1, HttpServletResponse var2, String var3);

    public List<ReportDataSyncIssuedLogItemVO> listXfLogItemsByLogId(String var1);

    public PageInfo<ReportDataSyncIssuedLogItemVO> listXfLogItemsByLogId(String var1, Integer var2, Integer var3);

    public ReportDataSyncIssuedLogVO queryLatestSyncParamTaskByTaskId(String var1);

    public ExportExcelSheet downloadParamUpateLogs(String var1);

    public Boolean retryParamSyncByLogItemIds(List<String> var1);

    public Boolean retryParamSyncByLogIdAndBatchType(String var1, RetryParamSyncBatchType var2, ReportDataSyncParamProgressDataDTO var3);

    public List<ReportDataSyncIssuedLogVO> fetchSyncParamTaskInfos();

    public ReportDataSyncParamFileDTO fetchSyncParamFiles(ReportDataSyncIssuedLogVO var1);

    public Boolean addParamSyncScheme(MultipartFile var1, ReportDataSyncParams var2);

    public Boolean updateParamSyncScheme(MultipartFile var1, ReportDataSyncParams var2);

    public Boolean deleteParamSyncScheme(String var1);

    public List<ReportDataSyncParams> listAllParamSyncScheme();
}

