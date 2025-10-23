/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.security.HtmlUtils
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.core.model.Result
 *  com.jiuqi.nr.datascheme.api.DesignDataTable
 *  com.jiuqi.nr.datascheme.api.exception.SchemeDataException
 *  com.jiuqi.nr.datascheme.api.service.IDataSchemeAuthService
 *  com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService
 *  com.jiuqi.nr.file.FileAreaService
 *  com.jiuqi.nr.file.FileInfo
 *  com.jiuqi.nr.file.FileService
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  javax.servlet.ServletOutputStream
 *  javax.servlet.http.HttpServletResponse
 *  org.apache.shiro.util.Assert
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestParam
 *  org.springframework.web.bind.annotation.ResponseBody
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.nr.datascheme.web.rest;

import com.jiuqi.bi.security.HtmlUtils;
import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.core.model.Result;
import com.jiuqi.nr.datascheme.api.DesignDataTable;
import com.jiuqi.nr.datascheme.api.exception.SchemeDataException;
import com.jiuqi.nr.datascheme.api.service.IDataSchemeAuthService;
import com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService;
import com.jiuqi.nr.datascheme.common.DataSchemeEnum;
import com.jiuqi.nr.datascheme.internal.service.DataFieldIOService;
import com.jiuqi.nr.file.FileAreaService;
import com.jiuqi.nr.file.FileInfo;
import com.jiuqi.nr.file.FileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbookType;
import org.apache.shiro.util.Assert;
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
@Api(tags={"\u6570\u636e\u65b9\u6848\uff1a\u5bfc\u5165\u5bfc\u51fa"})
public class DataFieldController {
    private static final Logger logger = LoggerFactory.getLogger(DataFieldController.class);
    @Autowired
    private FileService fileService;
    @Autowired
    private DataFieldIOService dataFieldIOService;
    @Autowired
    private IDesignDataSchemeService designDataSchemeService;
    @Autowired
    private IDataSchemeAuthService dataSchemeAuthService;

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @ApiOperation(value="\u5bfc\u5165\u6570\u636e\u6307\u6807")
    @PostMapping(value={"/table/import"})
    @ResponseBody
    public Result<String> importScheme(@RequestParam MultipartFile file, @RequestParam String context) throws JQException {
        Assert.notNull((Object)context, (String)"tableKey must not be null.");
        DesignDataTable dataTable = this.designDataSchemeService.getDataTable(context);
        if (!this.dataSchemeAuthService.canWriteScheme(dataTable.getDataSchemeKey())) {
            throw new JQException((ErrorEnum)DataSchemeEnum.UNAUTHORIZED);
        }
        try (Workbook workbook = WorkbookFactory.create(file.getInputStream());){
            Sheet sheet = workbook.getSheetAt(0);
            Assert.notNull((Object)sheet, (String)"sheet must not be null.");
            try {
                this.dataFieldIOService.imports(context, sheet);
            }
            catch (SchemeDataException e) {
                Result result;
                Throwable throwable;
                ByteArrayOutputStream out;
                block34: {
                    logger.error(e.getMessage(), e);
                    out = new ByteArrayOutputStream();
                    throwable = null;
                    try {
                        sheet.autoSizeColumn(23);
                        workbook.write(out);
                        byte[] bytes = out.toByteArray();
                        FileAreaService area = this.fileService.area("DATASCHEME");
                        FileInfo upload = area.upload(file.getOriginalFilename(), XSSFWorkbookType.XLSX.getExtension(), bytes);
                        String key = upload.getKey();
                        result = Result.failed((Object)key, (String)(e.getMessage() + ",\u70b9\u51fb\u4e0b\u8f7d\u7ee7\u7eed\u4fee\u6539"));
                        if (workbook == null) return result;
                        if (var5_6 != null) {
                        }
                        break block34;
                    }
                    catch (Throwable throwable2) {
                        throwable = throwable2;
                        throw throwable2;
                    }
                    try {
                        workbook.close();
                        return result;
                    }
                    catch (Throwable throwable3) {
                        var5_6.addSuppressed(throwable3);
                        return result;
                    }
                }
                workbook.close();
                return result;
                finally {
                    if (out != null) {
                        if (throwable != null) {
                            try {
                                out.close();
                            }
                            catch (Throwable throwable4) {
                                throwable.addSuppressed(throwable4);
                            }
                        } else {
                            out.close();
                        }
                    }
                }
            }
            Result result = Result.succeed((String)"\u5bfc\u5165\u6210\u529f\uff01");
            return result;
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new Result("410", e.getMessage(), null);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @ApiOperation(value="\u5bfc\u51fa\u6570\u636e\u8868\u4e0b\u6307\u6807")
    @PostMapping(value={"/table/export/"})
    public void exportTable(HttpServletResponse response, @RequestParam String tableKey) throws IOException, JQException {
        DesignDataTable dataTable = this.designDataSchemeService.getDataTable(tableKey);
        if (!this.dataSchemeAuthService.canReadScheme(dataTable.getDataSchemeKey())) {
            throw new JQException((ErrorEnum)DataSchemeEnum.UNAUTHORIZED);
        }
        ServletOutputStream outputStream = null;
        try (XSSFWorkbook wb = new XSSFWorkbook();){
            Sheet sheet = wb.createSheet();
            String fileName = this.dataFieldIOService.export(tableKey, sheet);
            outputStream = response.getOutputStream();
            DataFieldController.extracted(response, fileName);
            wb.write((OutputStream)outputStream);
        }
        finally {
            if (outputStream != null) {
                outputStream.flush();
                outputStream.close();
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @ApiOperation(value="\u5bfc\u51fa\u6570\u636e\u8868\u4e0b\u6307\u6807\u9519\u8bef\u4fe1\u606f\u6587\u4ef6")
    @PostMapping(value={"/table/info/export/"})
    public void exportInfoExcel(HttpServletResponse response, @RequestParam String fileKey) throws IOException {
        ServletOutputStream outputStream = null;
        try {
            FileAreaService area = this.fileService.area("DATASCHEME");
            FileInfo info = area.getInfo(fileKey);
            if (info == null) {
                response.setStatus(410);
                return;
            }
            byte[] download = area.download(fileKey);
            DataFieldController.extracted(response, "\u8be6\u7ec6\u4fe1\u606f");
            outputStream = response.getOutputStream();
            outputStream.write(download);
            area.delete(fileKey, Boolean.valueOf(false));
        }
        finally {
            if (outputStream != null) {
                outputStream.flush();
                outputStream.close();
            }
        }
    }

    protected static void extracted(HttpServletResponse response, String fileName) throws IOException {
        try {
            fileName = URLEncoder.encode(fileName, "utf-8").replace("\\+", "%20");
            fileName = "attachment;filename=" + fileName + "." + XSSFWorkbookType.XLSX.getExtension();
            HtmlUtils.validateHeaderValue((String)fileName);
            response.setHeader("Content-disposition", fileName);
            response.setContentType("application/octet-stream");
            response.addHeader("Access-Control-Expose-Headers", "Content-Disposition");
        }
        catch (Exception e) {
            throw new IOException(e);
        }
    }
}

