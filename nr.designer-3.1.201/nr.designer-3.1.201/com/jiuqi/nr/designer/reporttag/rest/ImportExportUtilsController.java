/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.security.HtmlUtils
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.nr.definition.facade.DesignReportTagDefine
 *  com.jiuqi.nr.definition.internal.controller.NRDesignTimeController
 *  com.jiuqi.nr.definition.reportTag.common.ReportTagExceptionEnum
 *  com.jiuqi.nr.definition.reportTag.common.ReportTagType
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  javax.servlet.ServletOutputStream
 *  javax.servlet.http.HttpServletResponse
 *  org.springframework.transaction.annotation.Transactional
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestParam
 *  org.springframework.web.bind.annotation.ResponseBody
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.nr.designer.reporttag.rest;

import com.jiuqi.bi.security.HtmlUtils;
import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.nr.definition.facade.DesignReportTagDefine;
import com.jiuqi.nr.definition.internal.controller.NRDesignTimeController;
import com.jiuqi.nr.definition.reportTag.common.ReportTagExceptionEnum;
import com.jiuqi.nr.definition.reportTag.common.ReportTagType;
import com.jiuqi.nr.designer.reporttag.rest.vo.CustomTagVO;
import com.jiuqi.nr.designer.reporttag.rest.vo.ExportTagParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbookType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping(value={"api/v1/designer/"})
@Api(tags={"\u5206\u6790\u62a5\u544a\u6a21\u677f\u81ea\u5b9a\u4e49\u6807\u7b7e\u6a21\u5757"})
public class ImportExportUtilsController {
    @Autowired
    private NRDesignTimeController nrDesignTimeController;

    @ApiOperation(value="\u5bfc\u5165\u81ea\u5b9a\u4e49\u6807\u7b7e\u6570\u636e")
    @PostMapping(value={"import-tag-info"})
    @Transactional(rollbackFor={Exception.class})
    @ResponseBody
    public void importTagInfo(@RequestParam MultipartFile file, @RequestParam String rptKeyImport) throws JQException {
        block24: {
            ArrayList<CustomTagVO> customTagVOList = new ArrayList<CustomTagVO>();
            try (InputStream inputStream = file.getInputStream();){
                XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
                XSSFSheet sheet = workbook.getSheetAt(0);
                XSSFRow row0 = sheet.getRow(0);
                String column0 = row0.getCell(0).getStringCellValue().trim();
                String column1 = row0.getCell(1).getStringCellValue().trim();
                String column2 = row0.getCell(2).getStringCellValue().trim();
                if (column0.equals("\u6807\u7b7e\u5185\u5bb9") && column1.equals("\u8868\u8fbe\u5f0f\u7c7b\u578b") && column2.equals("\u8868\u8fbe\u5f0f")) {
                    for (int i = 1; i < sheet.getPhysicalNumberOfRows(); ++i) {
                        CustomTagVO customTagVO2 = new CustomTagVO();
                        XSSFRow row = sheet.getRow(i);
                        if (row.getCell(0) == null) {
                            customTagVO2.setContent("");
                        } else {
                            customTagVO2.setContent(row.getCell(0).getStringCellValue());
                        }
                        if (row.getCell(1) == null) {
                            customTagVO2.setType("");
                        } else {
                            customTagVO2.setType(row.getCell(1).getStringCellValue());
                        }
                        if (row.getCell(2) == null) {
                            customTagVO2.setExpression("");
                        } else {
                            customTagVO2.setExpression(row.getCell(2).getStringCellValue());
                        }
                        customTagVOList.add(customTagVO2);
                    }
                    Map<String, CustomTagVO> tagMap = customTagVOList.stream().collect(Collectors.toMap(CustomTagVO::getContent, customTagVO -> customTagVO));
                    List reportTagDefineList = this.nrDesignTimeController.queryAllTagsByRptKey(rptKeyImport);
                    for (DesignReportTagDefine tag : reportTagDefineList) {
                        if (tagMap.get(tag.getContent()) == null) continue;
                        tag.setType(ReportTagType.getKeyByValue((String)tagMap.get(tag.getContent()).getType()));
                        tag.setExpression(tagMap.get(tag.getContent()).getExpression());
                    }
                    this.nrDesignTimeController.deleteTagsByRptKey(rptKeyImport);
                    this.nrDesignTimeController.insertTags(reportTagDefineList);
                    break block24;
                }
                throw new JQException((ErrorEnum)ReportTagExceptionEnum.REPORT_TAG_ERROR_010);
            }
            catch (IOException e) {
                throw new JQException((ErrorEnum)ReportTagExceptionEnum.REPORT_TAG_ERROR_009, (Throwable)e);
            }
            catch (NullPointerException e) {
                throw new JQException((ErrorEnum)ReportTagExceptionEnum.REPORT_TAG_ERROR_010, (Throwable)e);
            }
        }
    }

    @ApiOperation(value="\u5bfc\u51fa\u81ea\u5b9a\u4e49\u6807\u7b7e\u6570\u636e")
    @PostMapping(value={"export-tag-info"})
    @ResponseBody
    public void exportTagInfo(@RequestBody ExportTagParam exportTagParam, HttpServletResponse response) throws JQException, IOException {
        ServletOutputStream outputStream = null;
        String exportFileName = exportTagParam.getSchemeTitle() + "\u3010" + exportTagParam.getFileName() + "\u3011";
        List reportTagDefineList = this.nrDesignTimeController.queryAllTagsByRptKey(exportTagParam.getRptKey());
        List<CustomTagVO> exportDataList = CustomTagVO.toCustomTagVOList(reportTagDefineList);
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet();
        sheet.setDefaultColumnWidth(50);
        XSSFCellStyle style = workbook.createCellStyle();
        style.setFillForegroundColor(IndexedColors.GREY_40_PERCENT.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setLocked(true);
        String[] label = new String[]{"\u6807\u7b7e\u5185\u5bb9", "\u8868\u8fbe\u5f0f\u7c7b\u578b", "\u8868\u8fbe\u5f0f"};
        int columnNum = label.length;
        XSSFRow row1 = sheet.createRow(0);
        for (int n = 0; n < columnNum; ++n) {
            XSSFCell cell1 = row1.createCell(n);
            cell1.setCellType(CellType.STRING);
            cell1.setCellValue(label[n]);
            cell1.setCellStyle(style);
        }
        if (!exportDataList.isEmpty()) {
            for (int i = 0; i < exportDataList.size(); ++i) {
                String tagContent = exportDataList.get(i).getContent();
                String exprType = exportDataList.get(i).getType();
                String expr = exportDataList.get(i).getExpression();
                String[] arr = new String[]{tagContent, exprType, expr};
                XSSFRow row = sheet.createRow(i + 1);
                for (int j = 0; j < columnNum; ++j) {
                    XSSFCell cell = row.createCell(j, CellType.STRING);
                    cell.setCellValue(arr[j]);
                }
            }
        }
        try {
            this.extracted(response, exportFileName);
            outputStream = response.getOutputStream();
            workbook.write((OutputStream)outputStream);
        }
        catch (IOException e) {
            throw new JQException((ErrorEnum)ReportTagExceptionEnum.REPORT_TAG_ERROR_008, (Throwable)e);
        }
        finally {
            if (outputStream != null) {
                outputStream.flush();
                outputStream.close();
            }
        }
    }

    private void extracted(HttpServletResponse response, String fileName) throws IOException {
        try {
            fileName = URLEncoder.encode(fileName, "utf-8").replaceAll("\\+", "%20");
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

