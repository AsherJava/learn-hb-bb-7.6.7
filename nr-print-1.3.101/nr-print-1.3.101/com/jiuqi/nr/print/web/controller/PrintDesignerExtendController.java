/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.nr.task.api.aop.TaskLog
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  javax.servlet.ServletOutputStream
 *  javax.servlet.http.HttpServletResponse
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestMethod
 *  org.springframework.web.bind.annotation.RequestParam
 *  org.springframework.web.bind.annotation.RestController
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.nr.print.web.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.nr.print.dto.ReportLabelDTO;
import com.jiuqi.nr.print.exception.PrintDesignException;
import com.jiuqi.nr.print.service.IPrintDesignExtendService;
import com.jiuqi.nr.print.service.IPrintTemplateIOService;
import com.jiuqi.nr.print.service.IReportPrintDesignService;
import com.jiuqi.nr.print.web.vo.PrintTableGridVO;
import com.jiuqi.nr.print.web.vo.ReportLabelVO;
import com.jiuqi.nr.task.api.aop.TaskLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@Api(tags={"\u6253\u5370\u8bbe\u8ba1\u6269\u5c55"})
@RequestMapping(value={"api/v2/print/"})
public class PrintDesignerExtendController {
    private static final Logger logger = LoggerFactory.getLogger(PrintDesignerExtendController.class);
    @Autowired
    private IPrintTemplateIOService printTemplateIOService;
    @Autowired
    private IReportPrintDesignService reportPrintDesignService;
    @Autowired
    private IPrintDesignExtendService printDesignExtendService;

    @ApiOperation(value="\u53d1\u5e03\u6253\u5370\u6a21\u677f")
    @RequestMapping(value={"deploy/{designerId}/{schemeId}/{formId}"}, method={RequestMethod.GET})
    @TaskLog(operation="\u53d1\u5e03\u6253\u5370\u6a21\u677f")
    public void deployTemplate(@PathVariable String designerId, @PathVariable String schemeId, @PathVariable String formId) throws Exception {
        this.reportPrintDesignService.deployTemplate(designerId, schemeId, formId);
    }

    @ApiOperation(value="\u5224\u65ad\u6253\u5370\u6a21\u677f\u662f\u5426\u4fdd\u5b58")
    @RequestMapping(value={"template/is-save/{designerId}"}, method={RequestMethod.GET})
    public boolean templateIsSave(@PathVariable String designerId) {
        return this.printDesignExtendService.templateIsSave(designerId);
    }

    @ApiOperation(value="\u83b7\u53d6\u6253\u5370\u8868\u683c\u5bf9\u8c61")
    @RequestMapping(value={"get/grid/{designerId}/{elementId}"}, method={RequestMethod.GET})
    public String getTableGrid(@PathVariable String designerId, @PathVariable String elementId) throws JsonProcessingException {
        return this.reportPrintDesignService.getTableGrid(designerId, elementId);
    }

    @ApiOperation(value="\u66f4\u65b0\u9ad8\u7ea7\u8bbe\u7f6e")
    @RequestMapping(value={"update/grid"}, method={RequestMethod.POST})
    @TaskLog(operation="\u66f4\u65b0\u9ad8\u7ea7\u8bbe\u7f6e")
    public void updateTableGrid(@RequestBody PrintTableGridVO tableGrid) throws IOException {
        this.reportPrintDesignService.updateTableGrid(tableGrid.toPrintTableGrid());
    }

    @ApiOperation(value="\u83b7\u53d6\u9875\u9762\u7eb8\u5f20\u5c5e\u6027")
    @RequestMapping(value={"model/{designerId}"}, method={RequestMethod.GET})
    public Map<String, Object> getAttribute(@PathVariable String designerId) {
        return this.reportPrintDesignService.getAttribute(designerId);
    }

    @ApiOperation(value="\u66f4\u65b0\u6807\u7b7e")
    @RequestMapping(value={"/{designerId}/updateReportLabel"}, method={RequestMethod.POST})
    @TaskLog(operation="\u66f4\u65b0\u6807\u7b7e")
    public List<ReportLabelVO> updateReportLabel(@RequestBody List<ReportLabelVO> reportLabels, @PathVariable String designerId) {
        ReportLabelDTO[] labels;
        List<ReportLabelDTO> result;
        if (null == reportLabels) {
            reportLabels = Collections.emptyList();
        }
        if (null == (result = this.reportPrintDesignService.updateReportLabel(labels = (ReportLabelDTO[])reportLabels.stream().map(ReportLabelVO::toReportLabel).toArray(ReportLabelDTO[]::new), designerId))) {
            return Collections.emptyList();
        }
        return result.stream().map(ReportLabelVO::new).collect(Collectors.toList());
    }

    @ApiOperation(value="\u6253\u5370\u6a21\u677f\u5bfc\u5165")
    @PostMapping(value={"template/import"})
    @TaskLog(operation="\u6253\u5370\u6a21\u677f\u5bfc\u5165")
    public void templateImport(@RequestParam String designerId, @RequestBody MultipartFile file) throws JQException {
        try {
            this.printTemplateIOService.printTemplateImport(file, designerId);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)PrintDesignException.TEMPLATE_IMPORT_FAIL, e.getMessage());
        }
    }

    @ApiOperation(value="\u6253\u5370\u6a21\u677f\u589e\u91cf\u5bfc\u5165")
    @PostMapping(value={"template/increment/import"})
    @TaskLog(operation="\u6253\u5370\u6a21\u677f\u5bfc\u5165")
    public void templateIncrementImport(@RequestParam String designerId, @RequestBody MultipartFile file) throws JQException {
        try {
            this.printTemplateIOService.printTemplateIncrementImport(file, designerId);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)PrintDesignException.TEMPLATE_IMPORT_FAIL, e.getMessage());
        }
    }

    @ApiOperation(value="\u6253\u5370\u6a21\u677f\u5bfc\u51fa")
    @GetMapping(value={"template/export/{designerId}"})
    public void templateExport(@PathVariable String designerId, HttpServletResponse response) throws JQException {
        try (ServletOutputStream outputStream = response.getOutputStream();){
            String template = this.printTemplateIOService.printTemplateExport(designerId);
            this.analysisHeader(response, "printTemplateExport");
            outputStream.write(template.getBytes());
            outputStream.flush();
        }
        catch (Exception e) {
            logger.error("\u6a21\u677f\u5bfc\u51fa\u5931\u8d25" + e.getMessage());
            throw new JQException((ErrorEnum)PrintDesignException.TEMPLATE_EXPORT_FAIL, e.getMessage());
        }
    }

    @ApiOperation(value="\u6253\u5370\u6a21\u677f\u5bfc\u51fa\u540d\u79f0\u83b7\u53d6")
    @GetMapping(value={"template/export/name/{designerId}"})
    public String templateExportName(@PathVariable String designerId) throws JQException {
        String fileName = "";
        try {
            fileName = this.printTemplateIOService.printTemplateExportName(designerId);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)PrintDesignException.TEMPLATE_EXPORT_FAIL, e.getMessage());
        }
        return fileName;
    }

    private void analysisHeader(HttpServletResponse response, String fileName) {
        try {
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/xml");
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8") + ".xml");
        }
        catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    @ApiOperation(value="\u83b7\u53d6\u516c\u5f0f\u7f16\u8f91\u5668\u53c2\u6570")
    @GetMapping(value={"formula/editor/params/{designerId}"})
    public Map<String, Object> getFormulaEditorParams(@PathVariable String designerId) {
        return this.reportPrintDesignService.getFormulaEditorParams(designerId);
    }
}

