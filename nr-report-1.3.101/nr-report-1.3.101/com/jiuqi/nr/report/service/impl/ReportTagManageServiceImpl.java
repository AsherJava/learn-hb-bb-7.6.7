/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.nr.definition.api.IDesignTimeReportController
 *  com.jiuqi.nr.definition.api.IDesignTimeViewController
 *  com.jiuqi.nr.definition.facade.DesignFormSchemeDefine
 *  com.jiuqi.nr.definition.facade.DesignReportTagDefine
 *  com.jiuqi.nr.definition.facade.DesignReportTemplateDefine
 *  com.jiuqi.nr.definition.reportTag.common.ReportTagExceptionEnum
 *  com.jiuqi.nr.definition.reportTag.common.ReportTagType
 *  javax.servlet.ServletOutputStream
 *  javax.servlet.http.HttpServletResponse
 *  org.springframework.transaction.annotation.Transactional
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.nr.report.service.impl;

import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.nr.definition.api.IDesignTimeReportController;
import com.jiuqi.nr.definition.api.IDesignTimeViewController;
import com.jiuqi.nr.definition.facade.DesignFormSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignReportTagDefine;
import com.jiuqi.nr.definition.facade.DesignReportTemplateDefine;
import com.jiuqi.nr.definition.reportTag.common.ReportTagExceptionEnum;
import com.jiuqi.nr.definition.reportTag.common.ReportTagType;
import com.jiuqi.nr.report.common.NrReportErrorEnum;
import com.jiuqi.nr.report.dto.ReportTagDTO;
import com.jiuqi.nr.report.service.IReportTagManageService;
import com.jiuqi.nr.report.web.vo.CustomTagVO;
import com.jiuqi.nr.report.web.vo.ExportTagParam;
import java.io.ByteArrayInputStream;
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
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Component
public class ReportTagManageServiceImpl
implements IReportTagManageService {
    @Autowired
    private IDesignTimeReportController reportDesignTimeController;
    @Autowired
    private IDesignTimeViewController designTimeViewController;

    @Override
    public void insertTags(List<ReportTagDTO> list) {
        List reportTagDefines = list.stream().map(ReportTagDTO::toDesignReportTagDefine).collect(Collectors.toList());
        this.reportDesignTimeController.insertReportTag(reportTagDefines);
    }

    @Override
    public List<ReportTagDTO> listAllTagsByRpt(String rptKey) {
        List reportTagDefines = this.reportDesignTimeController.listReportTagByReportTemplate(rptKey);
        return reportTagDefines.stream().map(ReportTagDTO::new).collect(Collectors.toList());
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void deleteTags(List<String> keys) {
        this.reportDesignTimeController.deleteReportTag(keys);
    }

    @Override
    public void deleteTagsByRptKey(String rptKey) {
        this.reportDesignTimeController.deleteReportTagByReportTemplate(rptKey);
    }

    @Override
    public void updateTags(List<CustomTagVO> customTagVOs) {
        List reportTagDefines = customTagVOs.stream().map(t -> t.toCustomTagVOList()).collect(Collectors.toList());
        this.reportDesignTimeController.updateReportTag(reportTagDefines);
    }

    @Override
    public List<ReportTagDTO> filterCustomTagsInRpt(String rptKey, String fileKey) throws JQException {
        if (!StringUtils.hasLength(rptKey) && !StringUtils.hasLength(fileKey)) {
            throw new JQException((ErrorEnum)NrReportErrorEnum.REPORT_ERROR_005);
        }
        if (!StringUtils.hasLength(fileKey)) {
            DesignReportTemplateDefine reportTemplate = this.reportDesignTimeController.getReportTemplate(rptKey);
            fileKey = reportTemplate.getFileKey();
        }
        byte[] reportTemplateFileByte = this.reportDesignTimeController.getReportTemplateFile(fileKey);
        ByteArrayInputStream reportTemplateFileIs = new ByteArrayInputStream(reportTemplateFileByte);
        List reportTagDefineList = this.reportDesignTimeController.filterCustomTagsByReportTemplate((InputStream)reportTemplateFileIs, rptKey);
        return ReportTagDTO.toCustomTagVOList(reportTagDefineList);
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void importTagInfo(MultipartFile file, String rptKeyImport) throws JQException {
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
                    List reportTagDefineList = this.reportDesignTimeController.listReportTagByReportTemplate(rptKeyImport);
                    for (DesignReportTagDefine tag : reportTagDefineList) {
                        if (tagMap.get(tag.getContent()) == null) continue;
                        tag.setType(ReportTagType.getKeyByValue((String)tagMap.get(tag.getContent()).getType()));
                        tag.setExpression(tagMap.get(tag.getContent()).getExpression());
                    }
                    this.reportDesignTimeController.deleteReportTagByReportTemplate(rptKeyImport);
                    this.reportDesignTimeController.insertReportTag(reportTagDefineList);
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

    @Override
    public void exportTagInfo(ExportTagParam exportTagParam, HttpServletResponse response) throws Exception {
        ServletOutputStream outputStream = null;
        DesignFormSchemeDefine formSchemeDefine = this.designTimeViewController.getFormScheme(exportTagParam.getFormSchemeKey());
        String formSchemeTitle = formSchemeDefine.getTitle();
        String exportFileName = formSchemeTitle + "\u3010" + exportTagParam.getFileName() + "\u3011";
        List<CustomTagVO> exportDataList = CustomTagVO.toCustomTagVOList(this.reportDesignTimeController.listReportTagByReportTemplate(exportTagParam.getRptKey()));
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
        fileName = URLEncoder.encode(fileName, "utf-8").replace("\\+", "%20");
        fileName = "attachment;filename=" + fileName + "." + XSSFWorkbookType.XLSX.getExtension();
        response.setHeader("Content-disposition", fileName);
        response.setContentType("application/octet-stream");
        response.addHeader("Access-Control-Expose-Headers", "Content-Disposition");
    }
}

