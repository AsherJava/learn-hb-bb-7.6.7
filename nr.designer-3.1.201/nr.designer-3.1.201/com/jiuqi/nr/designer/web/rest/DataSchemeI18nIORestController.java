/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.internal.controller.NRDesignTimeController
 *  com.jiuqi.nr.definition.internal.service.DesignReportTemplateService
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  javax.servlet.ServletOutputStream
 *  javax.servlet.http.HttpServletResponse
 *  org.apache.shiro.authz.annotation.RequiresPermissions
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestMapping
 */
package com.jiuqi.nr.designer.web.rest;

import com.jiuqi.nr.definition.internal.controller.NRDesignTimeController;
import com.jiuqi.nr.definition.internal.service.DesignReportTemplateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value={"api/v1/designer/"})
@Api(tags={"\u5206\u6790\u62a5\u544a\u6a21\u677f\u4e0b\u8f7d\u670d\u52a1"})
class DataSchemeI18nIORestController {
    @Autowired
    private NRDesignTimeController nrDesignTimeController;
    @Autowired
    private DesignReportTemplateService designReportTemplateService;

    DataSchemeI18nIORestController() {
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @ApiOperation(value="\u4e0b\u8f7d\u5206\u6790\u62a5\u8868\u6a21\u677f\u9644\u4ef6")
    @PostMapping(value={"report/template/download/{fileKey}"})
    @RequiresPermissions(value={"nr:task_report:manage"})
    public void doExport(HttpServletResponse response, @PathVariable String fileKey) throws IOException {
        ServletOutputStream outputStream = null;
        try {
            String originalFileName = this.designReportTemplateService.getReportTemplateFileInfo(fileKey).getName();
            DataSchemeI18nIORestController.extracted(response, originalFileName);
            outputStream = response.getOutputStream();
            this.nrDesignTimeController.getReportTemplateFile(fileKey, (OutputStream)outputStream);
        }
        finally {
            if (outputStream != null) {
                outputStream.flush();
                outputStream.close();
            }
        }
    }

    protected static void extracted(HttpServletResponse response, String fileName) throws IOException {
        fileName = URLEncoder.encode(fileName, "utf-8").replaceAll("\\+", "%20");
        fileName = "attachment;filename=" + fileName;
        response.setHeader("Content-disposition", fileName);
        response.setContentType("application/octet-stream");
        response.addHeader("Access-Control-Expose-Headers", "Content-Disposition");
    }
}

