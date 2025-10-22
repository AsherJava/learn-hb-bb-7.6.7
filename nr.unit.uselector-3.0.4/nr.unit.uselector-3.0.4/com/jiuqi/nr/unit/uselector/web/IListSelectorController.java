/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.context.annotation.NRContextBuild
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  javax.annotation.Resource
 *  javax.servlet.ServletOutputStream
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 *  javax.validation.Valid
 *  org.springframework.web.bind.annotation.CrossOrigin
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestParam
 *  org.springframework.web.bind.annotation.ResponseBody
 *  org.springframework.web.bind.annotation.RestController
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.nr.unit.uselector.web;

import com.jiuqi.nr.context.annotation.NRContextBuild;
import com.jiuqi.nr.unit.uselector.dataio.IExcelWriteWorker;
import com.jiuqi.nr.unit.uselector.filter.listselect.FImportedConditions;
import com.jiuqi.nr.unit.uselector.filter.listselect.FTableDataSet;
import com.jiuqi.nr.unit.uselector.web.service.IListSelectorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(value={"/api/v2/unit-selector/list-filter-data"})
@Api(tags={"\u5355\u4f4d\u9009\u62e9\u5668-\u5217\u8868\u7b5b\u9009\u5355\u4f4dAPI"})
public class IListSelectorController {
    @Resource
    private IListSelectorService service;

    @NRContextBuild
    @ResponseBody
    @CrossOrigin(value={"*"})
    @ApiOperation(value="\u5bfc\u5165\u7b5b\u9009\u6761\u4ef6\u540e\uff0c\u7b5b\u9009\u6570\u636e")
    @PostMapping(value={"/import-conditions"})
    public FTableDataSet importFilterData(HttpServletRequest request, @RequestParam(value="file") MultipartFile file) {
        String selector = request.getParameter("selectorKey");
        return this.service.importConditions(selector, file);
    }

    @NRContextBuild
    @ResponseBody
    @CrossOrigin(value={"*"})
    @ApiOperation(value="\u7f16\u8f91\u7b5b\u9009\u6761\u4ef6\u540e\uff0c\u7b5b\u9009\u6570\u636e")
    @PostMapping(value={"/edit-conditions"})
    public FTableDataSet editFilterData(@Valid @RequestBody FImportedConditions rowData) {
        return this.service.editConditions(rowData);
    }

    @NRContextBuild
    @ApiOperation(value="\u5355\u4f4d\u9009\u62e9\u5668-\u5bfc\u51fa\u7b5b\u9009\u6a21\u677f")
    @GetMapping(value={"/export-condition-template"})
    public void exportConditionTemplate(@RequestParam(name="selector") String selector, HttpServletResponse response) {
        try {
            this.writeExcel(response, this.service.exportConditionTemplate(selector));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @NRContextBuild
    @ApiOperation(value="\u5355\u4f4d\u9009\u62e9\u5668-\u5bfc\u51fa\u5339\u914d\u7ed3\u679c")
    @PostMapping(value={"/export-result-set"})
    public void exportResultSet(@Valid @RequestBody FImportedConditions exportLevelParam, HttpServletResponse response) {
        try {
            this.writeExcel(response, this.service.exportResultSet(exportLevelParam));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void writeExcel(HttpServletResponse response, IExcelWriteWorker writeWorker) throws Exception {
        Workbook wb = writeWorker.createWorkbook();
        this.setExportFilename(response, writeWorker.getFileName());
        ServletOutputStream os = response.getOutputStream();
        wb.write((OutputStream)os);
        os.flush();
    }

    private void setExportFilename(HttpServletResponse response, String fileName) {
        try {
            fileName = URLEncoder.encode(fileName, "UTF-8");
            response.setContentType("application/octet-stream;charset=UTF-8");
            response.setHeader("Content-Disposition", "attachment;filename*=UTF-8''" + fileName);
            response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
            response.setHeader("Pragma", "No-cache");
            response.setHeader("Cache-Control", "No-cache");
            response.setDateHeader("Expires", 0L);
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}

