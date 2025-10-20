/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.common.base.http.PageInfo
 *  com.jiuqi.gcreport.analysisreport.api.AnalysisReportDataClient
 *  com.jiuqi.gcreport.analysisreport.dto.AnalysisReportDTO
 *  com.jiuqi.gcreport.analysisreport.dto.AnalysisReportDataDTO
 *  com.jiuqi.gcreport.analysisreport.dto.AnalysisReportGeneratorDocDTO
 *  com.jiuqi.gcreport.analysisreport.dto.AnalysisReportWorkFlowDTO
 *  com.jiuqi.gcreport.analysisreport.dto.AnalysisTempAndRefOrgDTO
 *  com.jiuqi.gcreport.analysisreport.dto.req.ReqAddAnalysisReportDataVersionDTO
 *  com.jiuqi.gcreport.analysisreport.dto.req.ReqOpenAnalysisReportDataDocParamDTO
 *  com.jiuqi.gcreport.analysisreport.dto.req.ReqQueryAnalysisReportDatasDTO
 *  com.jiuqi.gcreport.analysisreport.dto.resp.OpenAnalysisReportDocParamDTO
 *  com.jiuqi.gcreport.analysisreport.dto.resp.RespOpenAnalysisReportDataDocPageDTO
 *  com.jiuqi.nr.analysisreport.internal.AnalysisTemp
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 *  org.springframework.web.bind.annotation.RestController
 *  org.springframework.web.servlet.ModelAndView
 */
package com.jiuqi.gcreport.analysisreport.web;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.common.base.http.PageInfo;
import com.jiuqi.gcreport.analysisreport.api.AnalysisReportDataClient;
import com.jiuqi.gcreport.analysisreport.dto.AnalysisReportDTO;
import com.jiuqi.gcreport.analysisreport.dto.AnalysisReportDataDTO;
import com.jiuqi.gcreport.analysisreport.dto.AnalysisReportGeneratorDocDTO;
import com.jiuqi.gcreport.analysisreport.dto.AnalysisReportWorkFlowDTO;
import com.jiuqi.gcreport.analysisreport.dto.AnalysisTempAndRefOrgDTO;
import com.jiuqi.gcreport.analysisreport.dto.req.ReqAddAnalysisReportDataVersionDTO;
import com.jiuqi.gcreport.analysisreport.dto.req.ReqOpenAnalysisReportDataDocParamDTO;
import com.jiuqi.gcreport.analysisreport.dto.req.ReqQueryAnalysisReportDatasDTO;
import com.jiuqi.gcreport.analysisreport.dto.resp.OpenAnalysisReportDocParamDTO;
import com.jiuqi.gcreport.analysisreport.dto.resp.RespOpenAnalysisReportDataDocPageDTO;
import com.jiuqi.gcreport.analysisreport.service.AnalysisReportDataService;
import com.jiuqi.gcreport.analysisreport.service.AnalysisReportTemplateService;
import com.jiuqi.nr.analysisreport.internal.AnalysisTemp;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@Primary
public class AnalysisReportDataController
implements AnalysisReportDataClient {
    @Autowired
    private AnalysisReportDataService analysisReportDataService;
    @Autowired
    private AnalysisReportTemplateService templateService;

    public BusinessResponseEntity<AnalysisReportDTO> queryAnalysisReportTree() {
        AnalysisReportDTO analysisReportDTO = this.analysisReportDataService.queryAnalysisReportTree();
        return BusinessResponseEntity.ok((Object)analysisReportDTO);
    }

    public BusinessResponseEntity<List<AnalysisTemp>> queryAnalysisTempsByTemplateId(String templateId) {
        List<AnalysisTempAndRefOrgDTO> analysisTempAndRefOrgDTOS = this.templateService.queryAnalysisTempsByTemplateId(templateId);
        List analysisTemps = analysisTempAndRefOrgDTOS.stream().map(AnalysisTempAndRefOrgDTO::getAnalysisTemp).collect(Collectors.toList());
        return BusinessResponseEntity.ok(analysisTemps);
    }

    public BusinessResponseEntity<PageInfo<AnalysisReportDataDTO>> queryAnalysisReportDatas(ReqQueryAnalysisReportDatasDTO queryAnalysisReportDatasDTO) {
        PageInfo<AnalysisReportDataDTO> analysisReportDataDTOS = this.analysisReportDataService.queryAnalysisReportDatas(queryAnalysisReportDatasDTO);
        return BusinessResponseEntity.ok(analysisReportDataDTOS);
    }

    public BusinessResponseEntity<OpenAnalysisReportDocParamDTO> generatorOpenAnalysisReportDocParam(HttpServletRequest request, HttpServletResponse response, ReqOpenAnalysisReportDataDocParamDTO envDTO) throws Exception {
        OpenAnalysisReportDocParamDTO openAnalysisReportDocParamDTO = this.analysisReportDataService.generatorOpenAnalysisReportDocParam(request, response, envDTO);
        return BusinessResponseEntity.ok((Object)openAnalysisReportDocParamDTO);
    }

    public ModelAndView openAnalysisReportDataDocPage(HttpServletRequest request, HttpServletResponse response, String paramsOpenAnalysisReportDocParamDTO) throws Exception {
        RespOpenAnalysisReportDataDocPageDTO documentDTO = this.analysisReportDataService.openAnalysisReportDataDocPage(request, response, paramsOpenAnalysisReportDocParamDTO);
        request.setAttribute("docHtmlCode", (Object)documentDTO.getDocHtmlCode());
        request.setAttribute("docHtmlTitle", (Object)documentDTO.getDocHtmlTitle());
        ModelAndView mv = new ModelAndView("PageOfficeWordIndex");
        return mv;
    }

    public void saveAnalysisReportDataDocPage(HttpServletRequest request, HttpServletResponse response, String params) {
        this.analysisReportDataService.saveAnalysisReportDataDocPage(request, response, params);
    }

    public void queryByLeafTemplateIdLatestConfirmedWord(HttpServletRequest request, HttpServletResponse response, String templateId) {
        this.analysisReportDataService.queryByLeafTemplateIdLatestConfirmedWord(request, response, templateId);
    }

    public BusinessResponseEntity<AnalysisReportGeneratorDocDTO> addAnalysisReportDataVersion(HttpServletRequest request, HttpServletResponse response, ReqAddAnalysisReportDataVersionDTO reportDataVersionDTO) throws Exception {
        AnalysisReportGeneratorDocDTO docDTO = this.analysisReportDataService.addAnalysisReportDataVersion(request, response, reportDataVersionDTO);
        return BusinessResponseEntity.ok((Object)docDTO);
    }

    public BusinessResponseEntity<Boolean> deleteAnalysisReportData(Set<String> analysisReportDataIds) {
        Boolean isSuccess = this.analysisReportDataService.deleteAnalysisReportData(analysisReportDataIds);
        return BusinessResponseEntity.ok((Object)isSuccess);
    }

    public BusinessResponseEntity<Boolean> upAnalysisReportData(String analysisReportDataId) {
        Boolean isSuccess = this.analysisReportDataService.upAnalysisReportData(analysisReportDataId);
        return BusinessResponseEntity.ok((Object)isSuccess);
    }

    public BusinessResponseEntity<Boolean> downAnalysisReportData(String analysisReportDataId) {
        Boolean isSuccess = this.analysisReportDataService.downAnalysisReportData(analysisReportDataId);
        return BusinessResponseEntity.ok((Object)isSuccess);
    }

    public BusinessResponseEntity<Boolean> uploadAnalysisReportData(String analysisReportDataId) {
        Boolean isSuccess = this.analysisReportDataService.uploadAnalysisReportData(analysisReportDataId);
        return BusinessResponseEntity.ok((Object)isSuccess);
    }

    public BusinessResponseEntity<Boolean> rejectAnalysisReportData(String analysisReportDataId) {
        Boolean isSuccess = this.analysisReportDataService.rejectAnalysisReportData(analysisReportDataId);
        return BusinessResponseEntity.ok((Object)isSuccess);
    }

    public BusinessResponseEntity<Boolean> confirmAnalysisReportData(String analysisReportDataId) {
        Boolean isSuccess = this.analysisReportDataService.confirmAnalysisReportData(analysisReportDataId);
        return BusinessResponseEntity.ok((Object)isSuccess);
    }

    public BusinessResponseEntity<Boolean> executeWorkflowAnalysisReportData(String analysisReportDataId, String nodeCode) {
        Boolean isSuccess = this.analysisReportDataService.executeWorkFlowAnalysisReportData(analysisReportDataId, nodeCode);
        return BusinessResponseEntity.ok((Object)isSuccess);
    }

    public BusinessResponseEntity<List<AnalysisReportWorkFlowDTO>> listAnalysisReportWorkFlowButtons(String analysisReportDataId) {
        return BusinessResponseEntity.ok(this.analysisReportDataService.listAnalysisReportWorkFlowButtons(analysisReportDataId));
    }
}

