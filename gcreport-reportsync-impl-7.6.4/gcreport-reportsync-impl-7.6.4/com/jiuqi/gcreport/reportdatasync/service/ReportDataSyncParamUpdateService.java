/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.PageInfo
 *  com.jiuqi.common.expimp.dataimport.common.ImportContext
 *  com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncIssuedLogVO
 *  com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncReceiveLogItemVO
 *  com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncReceiveLogVO
 *  com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncReceiveTaskVO
 *  com.jiuqi.gcreport.reportdatasync.vo.ReportParamSyncTaskOptionVO
 *  com.jiuqi.util.StreamException
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.gcreport.reportdatasync.service;

import com.jiuqi.common.base.http.PageInfo;
import com.jiuqi.common.expimp.dataimport.common.ImportContext;
import com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncIssuedLogVO;
import com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncReceiveLogItemVO;
import com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncReceiveLogVO;
import com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncReceiveTaskVO;
import com.jiuqi.gcreport.reportdatasync.vo.ReportParamSyncTaskOptionVO;
import com.jiuqi.util.StreamException;
import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;

public interface ReportDataSyncParamUpdateService {
    public Boolean addTask(ReportDataSyncReceiveTaskVO var1);

    public Boolean updateTask(ReportDataSyncReceiveTaskVO var1);

    public Boolean addTaskAndCommit(ReportDataSyncReceiveTaskVO var1);

    public PageInfo<ReportDataSyncReceiveTaskVO> listAllTasks(Integer var1, Integer var2);

    public List<ReportDataSyncIssuedLogVO> fetchTargetSyncParamTaskInfos();

    public Boolean fetchTargetSyncParam(ReportDataSyncIssuedLogVO var1);

    public void downloadParamUpateInstructions(HttpServletRequest var1, HttpServletResponse var2, String var3);

    public void downloadParam(HttpServletRequest var1, HttpServletResponse var2, String var3);

    public void downloadParamAndUpateInstructions(HttpServletRequest var1, HttpServletResponse var2, String var3);

    public Boolean updateReportParamsData(String var1, Boolean var2);

    public PageInfo<ReportDataSyncReceiveLogVO> listReceiveTaskLogs(Integer var1, Integer var2);

    public List<ReportDataSyncReceiveLogItemVO> listReceiveLogItemsByLogId(String var1);

    public ReportDataSyncReceiveLogVO queryLatestSyncSuccessReceiveTaskByTaskId(String var1);

    public Boolean importReportParamFile(MultipartFile var1, ImportContext var2) throws IOException, StreamException;

    public Boolean importReportParamFile(MultipartFile var1, MultipartFile var2, ImportContext var3) throws IOException, StreamException;

    public List<ReportParamSyncTaskOptionVO> listAllParamSyncTasks();

    public List<Object> listComparisonResults(String var1);
}

