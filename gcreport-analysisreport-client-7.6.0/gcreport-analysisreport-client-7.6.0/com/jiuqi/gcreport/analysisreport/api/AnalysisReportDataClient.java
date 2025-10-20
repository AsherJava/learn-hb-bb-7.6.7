/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.common.base.http.PageInfo
 *  com.jiuqi.nr.analysisreport.internal.AnalysisTemp
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 *  org.springframework.cloud.openfeign.FeignClient
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestParam
 *  org.springframework.web.servlet.ModelAndView
 */
package com.jiuqi.gcreport.analysisreport.api;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.common.base.http.PageInfo;
import com.jiuqi.gcreport.analysisreport.dto.AnalysisReportDTO;
import com.jiuqi.gcreport.analysisreport.dto.AnalysisReportDataDTO;
import com.jiuqi.gcreport.analysisreport.dto.AnalysisReportGeneratorDocDTO;
import com.jiuqi.gcreport.analysisreport.dto.AnalysisReportWorkFlowDTO;
import com.jiuqi.gcreport.analysisreport.dto.req.ReqAddAnalysisReportDataVersionDTO;
import com.jiuqi.gcreport.analysisreport.dto.req.ReqOpenAnalysisReportDataDocParamDTO;
import com.jiuqi.gcreport.analysisreport.dto.req.ReqQueryAnalysisReportDatasDTO;
import com.jiuqi.gcreport.analysisreport.dto.resp.OpenAnalysisReportDocParamDTO;
import com.jiuqi.nr.analysisreport.internal.AnalysisTemp;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@FeignClient(contextId="com.jiuqi.gcreport.analysisreport.api.AnalysisReportDataClient", name="${custom.service-name.gcreport:gcreport-service}", url="${custom.service-url.gcreport:}", primary=false)
public interface AnalysisReportDataClient {
    public static final String API_PATH = "/api/gcreport/v1/analysisreport/datamanage";
    public static final String API_PATH_SAVE_ANALYSIS_REPORT_DATA_DOC_PAGE = "/api/gcreport/v1/analysisreport/datamanage/saveAnalysisReportDataDocPage";

    @GetMapping(value={"/api/gcreport/v1/analysisreport/datamanage/queryAnalysisReportTree"})
    public BusinessResponseEntity<AnalysisReportDTO> queryAnalysisReportTree();

    @GetMapping(value={"/api/gcreport/v1/analysisreport/datamanage/queryAnalysisTempsByTemplateId"})
    public BusinessResponseEntity<List<AnalysisTemp>> queryAnalysisTempsByTemplateId(@RequestParam(value="templateId") String var1);

    @PostMapping(value={"/api/gcreport/v1/analysisreport/datamanage/queryAnalysisReportDatas"})
    public BusinessResponseEntity<PageInfo<AnalysisReportDataDTO>> queryAnalysisReportDatas(@RequestBody ReqQueryAnalysisReportDatasDTO var1);

    @PostMapping(value={"/api/gcreport/v1/analysisreport/datamanage/addAnalysisReportDataVersion"})
    public BusinessResponseEntity<AnalysisReportGeneratorDocDTO> addAnalysisReportDataVersion(HttpServletRequest var1, HttpServletResponse var2, @RequestBody ReqAddAnalysisReportDataVersionDTO var3) throws Exception;

    @RequestMapping(value={"/api/gcreport/v1/analysisreport/datamanage/generatorOpenAnalysisReportDocParam"})
    public BusinessResponseEntity<OpenAnalysisReportDocParamDTO> generatorOpenAnalysisReportDocParam(HttpServletRequest var1, HttpServletResponse var2, @RequestBody ReqOpenAnalysisReportDataDocParamDTO var3) throws Exception;

    @RequestMapping(value={"/api/gcreport/v1/analysisreport/datamanage/openAnalysisReportDataDocPage"})
    public ModelAndView openAnalysisReportDataDocPage(HttpServletRequest var1, HttpServletResponse var2, @RequestParam(value="paramsOpenAnalysisReportDocParamDTO", required=false) String var3) throws Exception;

    @RequestMapping(value={"/api/gcreport/v1/analysisreport/datamanage/saveAnalysisReportDataDocPage"})
    public void saveAnalysisReportDataDocPage(HttpServletRequest var1, HttpServletResponse var2, @RequestParam(value="params", required=false) String var3);

    @PostMapping(value={"/api/gcreport/v1/analysisreport/datamanage/queryByLeafTemplateIdLatestConfirmedWord/{templateId}"})
    public void queryByLeafTemplateIdLatestConfirmedWord(HttpServletRequest var1, HttpServletResponse var2, @PathVariable(value="templateId") String var3);

    @PostMapping(value={"/api/gcreport/v1/analysisreport/datamanage/delete"})
    public BusinessResponseEntity<Boolean> deleteAnalysisReportData(@RequestBody Set<String> var1);

    @PostMapping(value={"/api/gcreport/v1/analysisreport/datamanage/up"})
    public BusinessResponseEntity<Boolean> upAnalysisReportData(@RequestParam(value="id") String var1);

    @PostMapping(value={"/api/gcreport/v1/analysisreport/datamanage/down"})
    public BusinessResponseEntity<Boolean> downAnalysisReportData(@RequestParam(value="id") String var1);

    @PostMapping(value={"/api/gcreport/v1/analysisreport/datamanage/upload"})
    public BusinessResponseEntity<Boolean> uploadAnalysisReportData(@RequestParam(value="id") String var1);

    @PostMapping(value={"/api/gcreport/v1/analysisreport/datamanage/reject"})
    public BusinessResponseEntity<Boolean> rejectAnalysisReportData(@RequestParam(value="id") String var1);

    @PostMapping(value={"/api/gcreport/v1/analysisreport/datamanage/confirm"})
    public BusinessResponseEntity<Boolean> confirmAnalysisReportData(@RequestParam(value="id") String var1);

    @PostMapping(value={"/api/gcreport/v1/analysisreport/datamanage/executeWorkflow"})
    public BusinessResponseEntity<Boolean> executeWorkflowAnalysisReportData(@RequestParam(value="id") String var1, @RequestParam(value="nodeCode") String var2);

    @PostMapping(value={"/api/gcreport/v1/analysisreport/datamanage/listWorkflowButtons"})
    public BusinessResponseEntity<List<AnalysisReportWorkFlowDTO>> listAnalysisReportWorkFlowButtons(@RequestParam(value="id") String var1);
}

