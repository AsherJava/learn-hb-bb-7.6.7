/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.analysisreport.dto.AnalysisReportRefOrgDTO
 */
package com.jiuqi.gcreport.analysisreport.converter;

import com.jiuqi.gcreport.analysisreport.dto.AnalysisReportRefOrgDTO;
import com.jiuqi.gcreport.analysisreport.entity.AnalysisReportRefOrgEO;
import org.springframework.beans.BeanUtils;

public class AnalysisReportTemplateRefOrgConverter {
    public static AnalysisReportRefOrgEO convertDTO2EO(AnalysisReportRefOrgDTO analysisReportRefOrgDTO) {
        AnalysisReportRefOrgEO eo = new AnalysisReportRefOrgEO();
        BeanUtils.copyProperties(analysisReportRefOrgDTO, (Object)eo);
        return eo;
    }

    public static AnalysisReportRefOrgDTO convertEO2DTO(AnalysisReportRefOrgEO eo) {
        AnalysisReportRefOrgDTO dto = new AnalysisReportRefOrgDTO();
        BeanUtils.copyProperties((Object)eo, dto);
        return dto;
    }
}

