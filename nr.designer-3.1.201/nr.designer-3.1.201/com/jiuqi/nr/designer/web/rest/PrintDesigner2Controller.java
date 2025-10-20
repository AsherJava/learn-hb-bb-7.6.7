/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.print.dto.ReportLabelDTO
 *  com.jiuqi.nr.print.service.IReportPrintDesignService
 *  com.jiuqi.nr.print.web.vo.ReportLabelVO
 *  io.swagger.annotations.Api
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.nr.designer.web.rest;

import com.jiuqi.nr.print.dto.ReportLabelDTO;
import com.jiuqi.nr.print.service.IReportPrintDesignService;
import com.jiuqi.nr.print.web.vo.ReportLabelVO;
import io.swagger.annotations.Api;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags={"\u6253\u5370\u8bbe\u8ba1\u5668"})
public class PrintDesigner2Controller {
    @Autowired
    private IReportPrintDesignService reportPrintDesignService;

    @GetMapping(value={"designer/print/model/{designerId}", "viewer/print/model/{designerId}"})
    public Map<String, Object> getAttribute(@PathVariable String designerId) {
        return this.reportPrintDesignService.getAttribute(designerId);
    }

    @PostMapping(value={"designer/print/{designerId}/updateReportLabel"})
    public List<ReportLabelVO> updateReportLabel(@RequestBody List<ReportLabelVO> reportLabels, @PathVariable String designerId) {
        ReportLabelDTO[] labels;
        List result;
        if (null == reportLabels) {
            reportLabels = Collections.emptyList();
        }
        if (null == (result = this.reportPrintDesignService.updateReportLabel(labels = (ReportLabelDTO[])reportLabels.stream().map(ReportLabelVO::toReportLabel).toArray(ReportLabelDTO[]::new), designerId))) {
            return Collections.emptyList();
        }
        return result.stream().map(ReportLabelVO::new).collect(Collectors.toList());
    }
}

