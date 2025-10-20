/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.analysisreport.dto.AnalysisReportDTO
 */
package com.jiuqi.gcreport.analysisreport.converter;

import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.analysisreport.dto.AnalysisReportDTO;
import com.jiuqi.gcreport.analysisreport.entity.AnalysisReportEO;
import java.util.List;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

public class AnalysisReportTemplateConverter {
    public static AnalysisReportEO convertDTO2EO(AnalysisReportDTO analysisReportDTO) {
        AnalysisReportEO eo = new AnalysisReportEO();
        BeanUtils.copyProperties(analysisReportDTO, (Object)eo);
        List refIds = analysisReportDTO.getRefIds();
        if (!CollectionUtils.isEmpty(refIds)) {
            String refIdsStr = StringUtils.join((Object[])refIds.toArray(), (String)",");
            eo.setRefIds(refIdsStr);
        }
        return eo;
    }

    public static AnalysisReportDTO convertEO2DTO(AnalysisReportEO analysisReportEO) {
        AnalysisReportDTO analysisReportDTO = new AnalysisReportDTO();
        BeanUtils.copyProperties((Object)analysisReportEO, analysisReportDTO);
        String refIdsStr = analysisReportEO.getRefIds();
        if (!ObjectUtils.isEmpty(refIdsStr)) {
            List refIds = StringUtils.split((String)refIdsStr, (String)",");
            analysisReportDTO.setRefIds(refIds);
            analysisReportDTO.setRefTitles(analysisReportEO.getRefTitles());
        }
        return analysisReportDTO;
    }
}

