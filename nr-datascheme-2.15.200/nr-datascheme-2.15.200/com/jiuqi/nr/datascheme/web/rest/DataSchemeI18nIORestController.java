/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.nr.datascheme.api.DesignDataScheme
 *  com.jiuqi.nr.datascheme.api.service.IDataSchemeAuthService
 *  com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  javax.servlet.ServletOutputStream
 *  javax.servlet.http.HttpServletResponse
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestParam
 *  org.springframework.web.bind.annotation.ResponseBody
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.nr.datascheme.web.rest;

import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.nr.datascheme.api.DesignDataScheme;
import com.jiuqi.nr.datascheme.api.service.IDataSchemeAuthService;
import com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService;
import com.jiuqi.nr.datascheme.common.DataSchemeEnum;
import com.jiuqi.nr.datascheme.i18n.IDesignDataSchemeI18nService;
import com.jiuqi.nr.datascheme.web.rest.DataFieldController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.io.IOException;
import java.io.OutputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping(value={"api/v1/datascheme/"})
@Api(tags={"\u6570\u636e\u65b9\u6848\u591a\u8bed\u8a00\u670d\u52a1"})
class DataSchemeI18nIORestController {
    private static final Logger logger = LoggerFactory.getLogger(DataSchemeI18nIORestController.class);
    @Autowired
    private IDesignDataSchemeI18nService i18nService;
    @Autowired
    private IDesignDataSchemeService designDataSchemeService;
    @Autowired
    private IDataSchemeAuthService dataSchemeAuthService;

    DataSchemeI18nIORestController() {
    }

    @ApiOperation(value="\u6309\u6570\u636e\u65b9\u6848\u5bfc\u5165\u591a\u8bed\u8a00\u53c2\u6570")
    @PostMapping(value={"i18n/import"})
    @ResponseBody
    public String doImport(@RequestParam MultipartFile file, @RequestParam String context) throws JQException {
        if (!this.dataSchemeAuthService.canWriteScheme(context)) {
            throw new JQException((ErrorEnum)DataSchemeEnum.UNAUTHORIZED);
        }
        try (Workbook workbook = WorkbookFactory.create(file.getInputStream());){
            Sheet sheet = workbook.getSheetAt(0);
            this.i18nService.doImport(context, sheet);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return "\u5bfc\u5165\u5931\u8d25";
        }
        return "\u5bfc\u5165\u6210\u529f\uff01";
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @ApiOperation(value="\u6309\u6570\u636e\u65b9\u6848\u5bfc\u51fa\u591a\u8bed\u8a00\u53c2\u6570")
    @PostMapping(value={"i18n/export"})
    public void doExport(HttpServletResponse response, @RequestParam String schemeKey) throws IOException, JQException {
        if (!this.dataSchemeAuthService.canWriteScheme(schemeKey)) {
            throw new JQException((ErrorEnum)DataSchemeEnum.UNAUTHORIZED);
        }
        ServletOutputStream outputStream = null;
        try (XSSFWorkbook wb = new XSSFWorkbook();){
            Sheet sheet = wb.createSheet();
            this.i18nService.doExport(schemeKey, sheet);
            outputStream = response.getOutputStream();
            DesignDataScheme dataScheme = this.designDataSchemeService.getDataScheme(schemeKey);
            DataFieldController.extracted(response, dataScheme.getTitle());
            wb.write((OutputStream)outputStream);
        }
        finally {
            if (outputStream != null) {
                outputStream.flush();
                outputStream.close();
            }
        }
    }
}

