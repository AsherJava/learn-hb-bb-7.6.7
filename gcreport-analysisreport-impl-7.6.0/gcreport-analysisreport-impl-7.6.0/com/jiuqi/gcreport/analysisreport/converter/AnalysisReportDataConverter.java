/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.gcreport.analysisreport.dto.AnalysisReportDataDTO
 */
package com.jiuqi.gcreport.analysisreport.converter;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.gcreport.analysisreport.dto.AnalysisReportDataDTO;
import com.jiuqi.gcreport.analysisreport.entity.AnalysisReportDataEO;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.BeanUtils;

public class AnalysisReportDataConverter {
    public static AnalysisReportDataEO convertDTO2EO(AnalysisReportDataDTO analysisReportDTO) {
        AnalysisReportDataEO eo = new AnalysisReportDataEO();
        BeanUtils.copyProperties(analysisReportDTO, (Object)eo);
        return eo;
    }

    public static AnalysisReportDataDTO convertEO2DTO(AnalysisReportDataEO analysisReportEO) {
        AnalysisReportDataDTO analysisReportDTO = new AnalysisReportDataDTO();
        BeanUtils.copyProperties((Object)analysisReportEO, analysisReportDTO);
        return analysisReportDTO;
    }

    public static List<AnalysisReportDataDTO> convertEO2DTOs(List<AnalysisReportDataEO> analysisReportEOs) {
        if (CollectionUtils.isEmpty(analysisReportEOs)) {
            return new ArrayList<AnalysisReportDataDTO>();
        }
        List<AnalysisReportDataDTO> dataDTOS = analysisReportEOs.stream().map(eo -> AnalysisReportDataConverter.convertEO2DTO(eo)).collect(Collectors.toList());
        return dataDTOS;
    }
}

