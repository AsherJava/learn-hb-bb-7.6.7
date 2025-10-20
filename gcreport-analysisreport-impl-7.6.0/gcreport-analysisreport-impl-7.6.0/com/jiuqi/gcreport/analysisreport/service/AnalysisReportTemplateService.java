/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.analysisreport.dto.AnalysisReportDTO
 *  com.jiuqi.gcreport.analysisreport.dto.AnalysisReportRefOrgDTO
 *  com.jiuqi.gcreport.analysisreport.dto.AnalysisTempAndRefOrgDTO
 *  com.jiuqi.gcreport.analysisreport.enums.AnalysisReportRefTemplateType
 */
package com.jiuqi.gcreport.analysisreport.service;

import com.jiuqi.gcreport.analysisreport.dto.AnalysisReportDTO;
import com.jiuqi.gcreport.analysisreport.dto.AnalysisReportRefOrgDTO;
import com.jiuqi.gcreport.analysisreport.dto.AnalysisTempAndRefOrgDTO;
import com.jiuqi.gcreport.analysisreport.entity.AnalysisReportEO;
import com.jiuqi.gcreport.analysisreport.enums.AnalysisReportRefTemplateType;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface AnalysisReportTemplateService {
    public List<AnalysisReportRefOrgDTO> queryRefOrgsByTemplateId(String var1);

    public AnalysisReportEO queryTemplateByTemplateId(String var1);

    public List<AnalysisTempAndRefOrgDTO> queryAnalysisTempsByTemplateId(String var1);

    public List<AnalysisTempAndRefOrgDTO> queryAnalysisTempsByTemplateId(AnalysisReportEO var1);

    public List<AnalysisTempAndRefOrgDTO> getAnalysisTempsByRefIds(String var1, AnalysisReportRefTemplateType var2, List<String> var3);

    public Object queryRefAnalysisReportTree();

    public List<AnalysisReportDTO> queryItemsByParentId(String var1);

    public List<AnalysisReportDTO> queryItemsByParentIdContainSelf(String var1);

    public List<AnalysisReportDTO> listAnalysisReport(boolean var1);

    public boolean saveAnalysisReport(AnalysisReportDTO var1) throws Exception;

    public AnalysisReportDTO queryAnalysisReportTree(boolean var1);

    public List<Map<String, Object>> queryAnalysisReportLeafTemplates();

    public boolean upAnalysisReport(String var1);

    public boolean downAnalysisReport(String var1);

    public boolean deleteAnalysisReport(Set<String> var1);
}

