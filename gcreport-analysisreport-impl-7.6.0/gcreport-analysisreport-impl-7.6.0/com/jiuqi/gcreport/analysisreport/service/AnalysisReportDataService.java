/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.PageInfo
 *  com.jiuqi.gcreport.analysisreport.dto.AnalysisReportDTO
 *  com.jiuqi.gcreport.analysisreport.dto.AnalysisReportDataDTO
 *  com.jiuqi.gcreport.analysisreport.dto.AnalysisReportGeneratorDocDTO
 *  com.jiuqi.gcreport.analysisreport.dto.AnalysisReportWorkFlowDTO
 *  com.jiuqi.gcreport.analysisreport.dto.req.AnalysisReportDataExportExecutorParamDTO
 *  com.jiuqi.gcreport.analysisreport.dto.req.ReqAddAnalysisReportDataVersionDTO
 *  com.jiuqi.gcreport.analysisreport.dto.req.ReqOpenAnalysisReportDataDocParamDTO
 *  com.jiuqi.gcreport.analysisreport.dto.req.ReqQueryAnalysisReportDatasDTO
 *  com.jiuqi.gcreport.analysisreport.dto.resp.OpenAnalysisReportDocParamDTO
 *  com.jiuqi.gcreport.analysisreport.dto.resp.RespOpenAnalysisReportDataDocPageDTO
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 */
package com.jiuqi.gcreport.analysisreport.service;

import com.jiuqi.common.base.http.PageInfo;
import com.jiuqi.gcreport.analysisreport.dto.AnalysisReportDTO;
import com.jiuqi.gcreport.analysisreport.dto.AnalysisReportDataDTO;
import com.jiuqi.gcreport.analysisreport.dto.AnalysisReportGeneratorDocDTO;
import com.jiuqi.gcreport.analysisreport.dto.AnalysisReportWorkFlowDTO;
import com.jiuqi.gcreport.analysisreport.dto.req.AnalysisReportDataExportExecutorParamDTO;
import com.jiuqi.gcreport.analysisreport.dto.req.ReqAddAnalysisReportDataVersionDTO;
import com.jiuqi.gcreport.analysisreport.dto.req.ReqOpenAnalysisReportDataDocParamDTO;
import com.jiuqi.gcreport.analysisreport.dto.req.ReqQueryAnalysisReportDatasDTO;
import com.jiuqi.gcreport.analysisreport.dto.resp.OpenAnalysisReportDocParamDTO;
import com.jiuqi.gcreport.analysisreport.dto.resp.RespOpenAnalysisReportDataDocPageDTO;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface AnalysisReportDataService {
    public AnalysisReportDTO queryAnalysisReportTree();

    public PageInfo<AnalysisReportDataDTO> queryAnalysisReportDatas(ReqQueryAnalysisReportDatasDTO var1);

    public AnalysisReportGeneratorDocDTO addAnalysisReportDataVersion(HttpServletRequest var1, HttpServletResponse var2, ReqAddAnalysisReportDataVersionDTO var3) throws Exception;

    public Boolean deleteAnalysisReportData(Set<String> var1);

    public Boolean upAnalysisReportData(String var1);

    public Boolean downAnalysisReportData(String var1);

    public Boolean uploadAnalysisReportData(String var1);

    public Boolean rejectAnalysisReportData(String var1);

    public Boolean confirmAnalysisReportData(String var1);

    public OpenAnalysisReportDocParamDTO generatorOpenAnalysisReportDocParam(HttpServletRequest var1, HttpServletResponse var2, ReqOpenAnalysisReportDataDocParamDTO var3) throws Exception;

    public RespOpenAnalysisReportDataDocPageDTO openAnalysisReportDataDocPage(HttpServletRequest var1, HttpServletResponse var2, String var3) throws Exception;

    public void saveAnalysisReportDataDocPage(HttpServletRequest var1, HttpServletResponse var2, String var3);

    public String getUniqueCodeForBusinessCode();

    public void exportFile(HttpServletRequest var1, HttpServletResponse var2, AnalysisReportDataExportExecutorParamDTO var3) throws Exception;

    public Boolean executeWorkFlowAnalysisReportData(String var1, String var2);

    public List<AnalysisReportWorkFlowDTO> listAnalysisReportWorkFlowButtons(String var1);

    public void queryByLeafTemplateIdLatestConfirmedWord(HttpServletRequest var1, HttpServletResponse var2, String var3);
}

