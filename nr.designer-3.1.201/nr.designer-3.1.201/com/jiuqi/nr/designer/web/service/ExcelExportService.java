/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringHelper
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.definition.common.FieldType
 *  com.jiuqi.np.definition.common.UUIDUtils
 *  com.jiuqi.np.definition.facade.DesignFieldDefine
 *  com.jiuqi.np.definition.facade.DesignTableDefine
 *  com.jiuqi.np.definition.facade.UniversalFieldDefine
 *  com.jiuqi.nr.common.cache.remote.CacheObjectResourceRemote
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.definition.facade.DesignDataLinkDefine
 *  com.jiuqi.nr.definition.facade.DesignFormDefine
 *  com.jiuqi.nr.definition.facade.DesignFormFoldingDefine
 *  com.jiuqi.nr.definition.facade.DesignFormSchemeDefine
 *  com.jiuqi.nr.definition.facade.DesignFormulaDefine
 *  com.jiuqi.nr.definition.facade.DesignFormulaSchemeDefine
 *  com.jiuqi.nr.definition.internal.controller.NRDesignTimeController
 *  com.jiuqi.nr.definition.internal.service.DesignFormFoldingService
 *  com.jiuqi.nr.definition.validation.CompareType
 *  com.jiuqi.nvwa.grid2.Grid2Data
 *  com.jiuqi.nvwa.grid2.GridCellData
 *  javax.servlet.ServletOutputStream
 *  javax.servlet.http.HttpServletResponse
 *  org.apache.poi.hssf.util.HSSFColor$HSSFColorPredefined
 *  org.apache.poi.ss.usermodel.BorderStyle
 *  org.apache.poi.ss.usermodel.CellStyle
 *  org.apache.poi.ss.usermodel.FillPatternType
 *  org.apache.poi.ss.usermodel.Font
 *  org.apache.poi.ss.usermodel.HorizontalAlignment
 *  org.apache.poi.ss.usermodel.VerticalAlignment
 *  org.apache.poi.ss.util.CellRangeAddress
 *  org.apache.poi.xssf.usermodel.DefaultIndexedColorMap
 *  org.apache.poi.xssf.usermodel.IndexedColorMap
 *  org.apache.poi.xssf.usermodel.XSSFCell
 *  org.apache.poi.xssf.usermodel.XSSFCellStyle
 *  org.apache.poi.xssf.usermodel.XSSFColor
 *  org.apache.poi.xssf.usermodel.XSSFFont
 *  org.apache.poi.xssf.usermodel.XSSFRow
 *  org.apache.poi.xssf.usermodel.XSSFSheet
 *  org.apache.poi.xssf.usermodel.XSSFWorkbook
 */
package com.jiuqi.nr.designer.web.service;

import com.jiuqi.bi.util.StringHelper;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.definition.common.FieldType;
import com.jiuqi.np.definition.common.UUIDUtils;
import com.jiuqi.np.definition.facade.DesignFieldDefine;
import com.jiuqi.np.definition.facade.DesignTableDefine;
import com.jiuqi.np.definition.facade.UniversalFieldDefine;
import com.jiuqi.nr.common.cache.remote.CacheObjectResourceRemote;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.facade.DesignDataLinkDefine;
import com.jiuqi.nr.definition.facade.DesignFormDefine;
import com.jiuqi.nr.definition.facade.DesignFormFoldingDefine;
import com.jiuqi.nr.definition.facade.DesignFormSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignFormulaDefine;
import com.jiuqi.nr.definition.facade.DesignFormulaSchemeDefine;
import com.jiuqi.nr.definition.internal.controller.NRDesignTimeController;
import com.jiuqi.nr.definition.internal.service.DesignFormFoldingService;
import com.jiuqi.nr.definition.validation.CompareType;
import com.jiuqi.nr.designer.common.Grid2DataSeralizeToGeGe;
import com.jiuqi.nr.designer.common.NrDesingerErrorEnum;
import com.jiuqi.nr.designer.util.OssUploadUtil;
import com.jiuqi.nr.designer.web.rest.vo.ExcelExportVO;
import com.jiuqi.nvwa.grid2.Grid2Data;
import com.jiuqi.nvwa.grid2.GridCellData;
import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.DefaultIndexedColorMap;
import org.apache.poi.xssf.usermodel.IndexedColorMap;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class ExcelExportService {
    private static final Logger log = LoggerFactory.getLogger(ExcelExportService.class);
    private static final String VIEW_TYPE_VT_DEFAULT_VALUE = "1";
    private static final String VIEW_TYPE_VT_TITLE = "2";
    private static final String VIEW_TYPE_VT_FIELD_TYPE = "3";
    private static final String VIEW_TYPE_VT_GATHER_TYPE = "4";
    private static final String VIEW_TYPE_VT_FORMULA = "6";
    private static final String VIEW_TYPE_VT_TABLE = "8";
    private static final String VIEW_TYPE_VT_FINANCE_FORMULA = "9";
    private static final String VIEW_TYPE_VT_DATALINK_POS = "10";
    private static final String VIEW_TYPE_VT_DATALINK_DIM = "11";
    private static final String VIEW_TYPE_VT_FIELD_DEFAULT_VALUE = "12";
    private static final String VIEW_TYPE_VT_ANALYSIS_FORM = "13";
    private static final Map<Integer, String> fileTypeMap = new HashMap<Integer, String>();
    private static final HashMap<Integer, String> gatherTypeMap;
    public static final String ROOT_LOCATION;
    public static final String EXPORTDIR;
    @Autowired
    private CacheObjectResourceRemote cacheObjectResourceRemote;
    @Autowired
    private NRDesignTimeController nrDesignTimeController;
    @Autowired
    private DesignFormFoldingService formFoldingService;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void exportFormStyle(ExcelExportVO excelExportObj, HttpServletResponse res) throws Exception {
        String[] formKeys = excelExportObj.getFormKeys();
        String schemeId = excelExportObj.getSchemeId();
        String viewType = excelExportObj.getViewType();
        String formulaSchemeId = excelExportObj.getFormulaSchemeId();
        String showForm = excelExportObj.getShowForm();
        if (VIEW_TYPE_VT_FINANCE_FORMULA.equals(viewType)) {
            return;
        }
        HashSet<String> sheetNameSet = new HashSet<String>();
        DesignFormSchemeDefine formSchemeDefine = this.nrDesignTimeController.queryFormSchemeDefine(schemeId);
        String fileName = formSchemeDefine != null ? formSchemeDefine.getTitle() + "_\u8868\u6837" : "\u8868\u6837";
        res.setCharacterEncoding("utf-8");
        res.setContentType("application/vnd.ms-excel");
        res.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8") + ".xlsx");
        ServletOutputStream out = res.getOutputStream();
        try {
            XSSFWorkbook workbook = new XSSFWorkbook();
            for (int i = 0; i < formKeys.length; ++i) {
                String formId = formKeys[i];
                DesignFormDefine formDefine = this.nrDesignTimeController.queryFormById(formId);
                Grid2Data styleData = Grid2Data.bytesToGrid((byte[])formDefine.getBinaryData());
                String sheetName = this.getsheetName(showForm, formDefine);
                int number = 1;
                while (!sheetNameSet.add(sheetName)) {
                    sheetName = sheetName.split("_")[0] + "_" + number++;
                }
                this.exportExcel(workbook, out, styleData, i, sheetName, formId, viewType, formulaSchemeId);
            }
            workbook.write((OutputStream)out);
            out.flush();
        }
        finally {
            if (out != null) {
                try {
                    out.close();
                }
                catch (IOException e) {
                    log.error(e.getMessage(), e);
                }
            }
        }
    }

    private String getsheetName(String showForm, DesignFormDefine formDefine) {
        String sheetName = formDefine.getTitle();
        if (showForm.indexOf(VIEW_TYPE_VT_DEFAULT_VALUE) > -1) {
            sheetName = "";
        }
        if ('1' == showForm.charAt(0)) {
            sheetName = sheetName + formDefine.getFormCode() + "|";
        }
        if ('1' == showForm.charAt(1)) {
            sheetName = sheetName + formDefine.getTitle() + "|";
        }
        if ('1' == showForm.charAt(2)) {
            sheetName = sheetName + formDefine.getSerialNumber();
        }
        if (sheetName.endsWith("|")) {
            sheetName = sheetName.substring(0, sheetName.length() - 1);
        }
        return sheetName;
    }

    public void exportExcel(XSSFWorkbook workbook, ServletOutputStream out, Grid2Data styleData, int sheetCount, String sheetName, String formId, String viewType, String formulaSchemeId) throws JQException {
        List linkList = this.nrDesignTimeController.getAllLinksInForm(formId);
        List formFoldings = this.formFoldingService.getByFormKey(formId);
        HashMap<String, DesignDataLinkDefine> linkXYMap = new HashMap<String, DesignDataLinkDefine>();
        this.getLinkMap(linkList, linkXYMap);
        HashMap<String, String> formulaMap = new HashMap<String, String>();
        if (VIEW_TYPE_VT_FORMULA.equals(viewType)) {
            Map<String, DesignDataLinkDefine> LinkExpressionkMap = linkList.stream().collect(Collectors.toMap(DataLinkDefine::getLinkExpression, l -> l, (v1, v2) -> v2));
            this.createFormulaMap(formId, formulaSchemeId, LinkExpressionkMap, formulaMap);
        }
        XSSFSheet sheet = workbook.createSheet();
        workbook.setSheetName(sheetCount, sheetName);
        sheet.setDefaultColumnWidth(15);
        HashMap<String, XSSFCellStyle> xssfCellStyles = new HashMap<String, XSSFCellStyle>();
        HashMap<String, XSSFFont> xssfCellFonts = new HashMap<String, XSSFFont>();
        int colCount = styleData.getColumnCount();
        int rowCount = styleData.getRowCount();
        for (int h = 1; h < rowCount; ++h) {
            XSSFRow row = sheet.createRow(h - 1);
            int rowHeight = styleData.getRowHeight(h);
            row.setHeightInPoints((float)(rowHeight * 72 / 96));
            for (int l2 = 1; l2 < colCount; ++l2) {
                GridCellData cellData = styleData.getGridCellData(l2, h);
                this.setCellRange(cellData, sheet, h, l2);
                sheet.setColumnWidth(l2 - 1, (int)((double)styleData.getColumnWidth(l2) / 8.0 * 256.0));
                XSSFCell cell = row.createCell(l2 - 1);
                XSSFCellStyle style = this.getXssCellStyle(workbook, cellData, xssfCellStyles, xssfCellFonts);
                cell.setCellStyle((CellStyle)style);
                String excelPositionString = h + "_" + l2;
                DesignDataLinkDefine dataLink = (DesignDataLinkDefine)linkXYMap.get(excelPositionString);
                DesignFieldDefine fieldDefine = null;
                if (dataLink != null) {
                    fieldDefine = this.nrDesignTimeController.queryFieldDefine(dataLink.getLinkExpression());
                }
                String showText = cellData.getShowText();
                block12 : switch (viewType) {
                    case "1": {
                        break;
                    }
                    case "2": {
                        if (fieldDefine == null) break;
                        showText = fieldDefine.getTitle();
                        break;
                    }
                    case "3": {
                        if (fieldDefine == null) break;
                        switch (fieldDefine.getValueType()) {
                            case FIELD_VALUE_PARENT_UNIT: {
                                showText = "\u4e0a\u7ea7\u4ee3\u7801";
                                break block12;
                            }
                            case FIELD_VALUE_UNIT_CODE: {
                                showText = "\u5355\u4f4d\u4ee3\u7801";
                                break block12;
                            }
                            case FIELD_VALUE_UNIT_NAME: {
                                showText = "\u5355\u4f4d\u540d\u79f0";
                                break block12;
                            }
                            case FIELD_VALUE_UNIT_TYPE: {
                                showText = "\u5355\u4f4d\u7c7b\u578b";
                                break block12;
                            }
                        }
                        if (StringHelper.notNull((String)fieldDefine.getEntityKey())) {
                            showText = "\u4e0b\u62c9\u7c7b\u578b";
                            break;
                        }
                        if (dataLink.getDataValidation() != null) {
                            for (String dataValidation : dataLink.getDataValidation()) {
                                if (dataValidation.indexOf(CompareType.MOBILEPHONE.getTitle()) <= -1) continue;
                                showText = "\u624b\u673a\u53f7\u7c7b\u578b";
                                break;
                            }
                        }
                        if (fieldDefine.getType() == FieldType.FIELD_TYPE_DECIMAL) {
                            showText = fileTypeMap.get(fieldDefine.getType().getValue()) + "(" + fieldDefine.getSize() + "," + fieldDefine.getFractionDigits() + ")";
                            break;
                        }
                        if (fieldDefine.getType() == FieldType.FIELD_TYPE_INTEGER || fieldDefine.getType() == FieldType.FIELD_TYPE_TEXT) {
                            showText = fileTypeMap.get(fieldDefine.getType().getValue()) + "(" + fieldDefine.getSize() + ")";
                            break;
                        }
                        showText = fileTypeMap.get(fieldDefine.getType().getValue()) != null ? fileTypeMap.get(fieldDefine.getType().getValue()) : "";
                        break;
                    }
                    case "4": {
                        if (fieldDefine == null) break;
                        showText = gatherTypeMap.get(fieldDefine.getGatherType().getValue());
                        break;
                    }
                    case "6": {
                        String leftCellCode;
                        String expression;
                        if (dataLink == null || !StringHelper.notNull((String)(expression = (String)formulaMap.get(dataLink.getRowNum() + "_" + dataLink.getColNum())))) break;
                        String cellLinkGuid = "[" + dataLink.getUniqueCode() + "]";
                        if (expression.length() > cellLinkGuid.length() && cellLinkGuid.equals(leftCellCode = expression.substring(0, cellLinkGuid.length()))) {
                            expression = expression.substring(cellLinkGuid.length());
                        }
                        showText = this.showFormula(expression);
                        break;
                    }
                    case "8": {
                        if (fieldDefine == null) break;
                        DesignTableDefine tableDefine = this.nrDesignTimeController.queryTableDefine(fieldDefine.getOwnerTableKey());
                        showText = tableDefine.getCode() + "[" + fieldDefine.getCode() + "]";
                        break;
                    }
                    case "10": {
                        if (dataLink == null) break;
                        if (StringHelper.notNull((String)dataLink.getLinkExpression())) {
                            showText = "[" + dataLink.getRowNum() + "," + dataLink.getColNum() + "]";
                            break;
                        }
                        showText = "!\u6620\u5c04\u6307\u6807\u4e0d\u5b58\u5728";
                        break;
                    }
                    case "11": {
                        if (dataLink == null) break;
                        showText = dataLink.getBindingExpression() == null ? "" : dataLink.getBindingExpression();
                        break;
                    }
                    case "12": {
                        if (fieldDefine == null) break;
                        showText = fieldDefine.getDefaultValue() == null ? "" : fieldDefine.getDefaultValue();
                        break;
                    }
                    case "13": {
                        showText = "\u5206\u6790\u8868";
                        break;
                    }
                }
                cell.setCellValue(showText);
            }
        }
        sheet.setRowSumsBelow(false);
        if (!CollectionUtils.isEmpty(formFoldings)) {
            for (DesignFormFoldingDefine formFolding : formFoldings) {
                sheet.groupRow(formFolding.getStartIdx() - 1, formFolding.getEndIdx() - 1);
                sheet.setRowGroupCollapsed(formFolding.getStartIdx() - 1, formFolding.isFolding());
            }
        }
    }

    private XSSFCellStyle getXssCellStyle(XSSFWorkbook workbook, GridCellData cellData, Map<String, XSSFCellStyle> xssfCellStyles, Map<String, XSSFFont> xssfCellFonts) {
        XSSFCellStyle style = xssfCellStyles.get(this.getCellDataKey(cellData));
        if (null != style && null != xssfCellFonts.get(this.getCellDataFontKey(cellData))) {
            return style;
        }
        style = new XSSFCellStyle(workbook.getStylesSource());
        this.setBorderStyle(style, cellData);
        XSSFFont font = xssfCellFonts.get(this.getCellDataFontKey(cellData));
        if (null == font) {
            font = workbook.createFont();
            this.setFontStyle(font, cellData);
            xssfCellFonts.put(this.getCellFontKey(font), font);
        }
        style.setFont((Font)font);
        xssfCellStyles.put(this.getCellStyleKey(style), style);
        return style;
    }

    private String getCellFontKey(XSSFFont font) {
        StringBuffer sbf = new StringBuffer();
        sbf.append(font.getXSSFColor().getARGBHex());
        sbf.append(font.getFontName());
        sbf.append(font.getFontHeightInPoints());
        sbf.append(font.getBold());
        sbf.append(font.getItalic());
        sbf.append(font.getUnderline());
        sbf.append(font.getStrikeout());
        return sbf.toString();
    }

    private String getCellDataFontKey(GridCellData cellData) {
        StringBuffer sbf = new StringBuffer();
        XSSFColor fontColor = new XSSFColor(ExcelExportService.hex2Rgb(Grid2DataSeralizeToGeGe.intToHtmlColor(cellData.getForeGroundColor(), "#000000")), (IndexedColorMap)new DefaultIndexedColorMap());
        sbf.append(fontColor.getARGBHex());
        int fontStyle = cellData.getFontStyle();
        sbf.append(cellData.getFontName());
        sbf.append(cellData.getFontSize() * 72 / 96);
        if ((2 & fontStyle) == 2) {
            sbf.append(true);
        }
        if ((4 & fontStyle) == 4) {
            sbf.append(true);
        }
        if ((8 & fontStyle) == 8) {
            sbf.append(1);
        }
        if ((0x10 & fontStyle) == 16) {
            sbf.append(true);
        }
        return sbf.toString();
    }

    private String getCellStyleKey(XSSFCellStyle style) {
        StringBuffer sbf = new StringBuffer();
        sbf.append(style.getBorderBottom());
        sbf.append(style.getBorderRight());
        sbf.append(style.getBottomBorderXSSFColor().getARGBHex());
        sbf.append(style.getRightBorderXSSFColor().getARGBHex());
        sbf.append(style.getRotation());
        sbf.append(style.getWrapText());
        sbf.append(style.getIndention());
        sbf.append(style.getLocked());
        sbf.append(style.getAlignment());
        sbf.append(style.getVerticalAlignment());
        sbf.append(style.getFillPattern());
        sbf.append(null == style.getFillForegroundXSSFColor() ? "" : style.getFillForegroundXSSFColor().getARGBHex());
        return sbf.toString();
    }

    private String getCellDataKey(GridCellData cellData) {
        StringBuffer sbf = new StringBuffer();
        int vertAlign = cellData.getVertAlign();
        int horzAlign = cellData.getHorzAlign();
        int backGroundStyle = cellData.getBackGroundStyle();
        XSSFColor backStyleColor = new XSSFColor(ExcelExportService.hex2Rgb("#C8CBCC"), (IndexedColorMap)new DefaultIndexedColorMap());
        XSSFColor backGroundColor = new XSSFColor(ExcelExportService.hex2Rgb(Grid2DataSeralizeToGeGe.intToHtmlColor(cellData.getBackGroundColor(), "#ffffff")), (IndexedColorMap)new DefaultIndexedColorMap());
        XSSFColor bottomBorderColor = new XSSFColor(new Color(cellData.getBottomBorderColor()), (IndexedColorMap)new DefaultIndexedColorMap());
        XSSFColor rightBorderColor = new XSSFColor(new Color(cellData.getRightBorderColor()), (IndexedColorMap)new DefaultIndexedColorMap());
        BorderStyle excelBottomBorderStyle = this.formBorder2ExcelBorder(cellData.getBottomBorderStyle());
        BorderStyle excelRightBorderStyle = this.formBorder2ExcelBorder(cellData.getRightBorderStyle());
        sbf.append(excelBottomBorderStyle);
        sbf.append(excelRightBorderStyle);
        XSSFColor deaultBorderColor = new XSSFColor(new Color(217, 217, 217), (IndexedColorMap)new DefaultIndexedColorMap());
        if (cellData.getBottomBorderColor() < 0) {
            sbf.append(deaultBorderColor.getARGBHex());
        } else {
            sbf.append(bottomBorderColor.getARGBHex());
        }
        if (cellData.getRightBorderColor() < 0) {
            sbf.append(deaultBorderColor.getARGBHex());
        } else {
            sbf.append(rightBorderColor.getARGBHex());
        }
        if (cellData.isVertText()) {
            sbf.append(255);
        } else {
            sbf.append(0);
        }
        sbf.append(cellData.isWrapLine());
        sbf.append(cellData.getIndent());
        sbf.append(cellData.isEditable());
        if (horzAlign == 1) {
            sbf.append(HorizontalAlignment.forInt((int)1));
        } else if (horzAlign == 3) {
            sbf.append(HorizontalAlignment.forInt((int)2));
        } else if (horzAlign == 2) {
            sbf.append(HorizontalAlignment.forInt((int)3));
        }
        if (vertAlign == 1) {
            sbf.append(VerticalAlignment.forInt((int)0));
        } else if (vertAlign == 3) {
            sbf.append(VerticalAlignment.forInt((int)1));
        } else if (vertAlign == 2) {
            sbf.append(VerticalAlignment.forInt((int)2));
        }
        if (backGroundStyle == 0) {
            sbf.append(FillPatternType.SOLID_FOREGROUND);
            XSSFColor silverBackGroundColor = new XSSFColor(new Color(232, 232, 232), (IndexedColorMap)new DefaultIndexedColorMap());
            byte[] wRgb = new byte[]{0, 0, 0};
            String savedRgbStr = new String(backGroundColor.getRGB());
            String wRgbStr = new String(wRgb);
            boolean silverHead = cellData.isSilverHead();
            if (savedRgbStr.equals(wRgbStr) && silverHead) {
                sbf.append(silverBackGroundColor.getARGBHex());
            } else {
                sbf.append(backGroundColor.getARGBHex());
            }
        } else if (backGroundStyle == 11) {
            sbf.append(FillPatternType.THIN_HORZ_BANDS);
            sbf.append(backStyleColor.getARGBHex());
        } else if (backGroundStyle == 8) {
            sbf.append(FillPatternType.THIN_VERT_BANDS);
            sbf.append(backStyleColor.getARGBHex());
        } else if (backGroundStyle == 10) {
            sbf.append(FillPatternType.THIN_BACKWARD_DIAG);
            sbf.append(backStyleColor.getARGBHex());
        } else if (backGroundStyle == 9) {
            sbf.append(FillPatternType.THIN_FORWARD_DIAG);
            sbf.append(backStyleColor.getARGBHex());
        } else if (backGroundStyle == 4) {
            sbf.append(FillPatternType.SQUARES);
            sbf.append(backStyleColor.getARGBHex());
        } else if (backGroundStyle == 5) {
            sbf.append(FillPatternType.DIAMONDS);
            sbf.append(backStyleColor.getARGBHex());
        }
        return sbf.toString();
    }

    private void createFormulaMap(String formId, String formulaSchemeId, Map<String, DesignDataLinkDefine> LinkExpressionkMap, Map<String, String> formulaMap) throws JQException {
        List formulaList = this.nrDesignTimeController.getAllFormulasInForm(formulaSchemeId, formId);
        List fieldList = this.nrDesignTimeController.getAllFieldsByLinksInForm(formId);
        Map<String, DesignFieldDefine> fieldCodeMap = fieldList.stream().collect(Collectors.toMap(UniversalFieldDefine::getCode, f -> f, (n, o) -> n));
        for (DesignFormulaDefine formula : formulaList) {
            DesignFieldDefine field;
            String expression;
            if (formula == null || !formula.getUseCalculate() || !StringUtils.isNotEmpty((String)(expression = formula.getExpression())) || expression.indexOf("=") == -1) continue;
            String leftCode = expression.substring(0, expression.indexOf("=")).trim();
            String patt1 = "^[A-Z]+[0-9]+$";
            String patt2 = "^\\[[0-9]+\\,[0-9]+\\]$";
            if (Pattern.matches(patt1, leftCode)) {
                int[] tmpPos = this.tryParsePos(expression.toCharArray());
                if (tmpPos == null || tmpPos.length < 2) continue;
                formulaMap.put(tmpPos[1] + "_" + tmpPos[0], expression);
                continue;
            }
            if (Pattern.matches(patt2, leftCode)) {
                String y = leftCode.substring(leftCode.indexOf("[") + 1, leftCode.indexOf(","));
                String x = leftCode.substring(leftCode.indexOf(",") + 1, leftCode.indexOf("]"));
                formulaMap.put(y + "_" + x, expression);
                continue;
            }
            String fieldCode = "";
            if (leftCode.indexOf("!") != -1) {
                fieldCode = leftCode.substring(leftCode.indexOf("!") + 1);
            } else {
                if (leftCode.indexOf("[") < 0 || leftCode.indexOf("]") < 0) continue;
                fieldCode = leftCode.substring(leftCode.indexOf("[") + 1, leftCode.indexOf("]"));
            }
            if ((field = fieldCodeMap.get(fieldCode)) == null) continue;
            DesignDataLinkDefine link = LinkExpressionkMap.get(field.getKey());
            formulaMap.put(link.getRowNum() + "_" + link.getColNum(), expression);
        }
    }

    private String showFormula(String formulaStr) {
        if (((formulaStr = formulaStr.trim()).charAt(0) == '=' || formulaStr.charAt(0) == '\uff1d') && formulaStr.length() > 1) {
            formulaStr = formulaStr.substring(1);
        }
        return formulaStr;
    }

    public int[] tryParsePos(char[] cellSign) {
        int[] arrayOfInt = new int[]{0, 0, 0};
        int i = 0;
        for (int j = 0; j < cellSign.length; ++j) {
            int k = cellSign[j];
            if (k >= 65 && k <= 90) {
                if (arrayOfInt[1] > 0) {
                    return null;
                }
                arrayOfInt[0] = arrayOfInt[0] * 26 + k - 65 + 1;
                continue;
            }
            if (k >= 48 && k <= 57) {
                arrayOfInt[1] = arrayOfInt[1] * 10 + k - 48;
                continue;
            }
            if (k >= 97 && k <= 122) {
                if (arrayOfInt[1] > 0) {
                    return null;
                }
                arrayOfInt[0] = arrayOfInt[0] * 26 + k - 97 + 1;
                continue;
            }
            if (k == 36) {
                switch (i) {
                    case 0: {
                        if (arrayOfInt[1] > 0) {
                            return null;
                        }
                        arrayOfInt[2] = arrayOfInt[2] | (arrayOfInt[0] == 0 ? 1 : 2);
                        break;
                    }
                    case 1: {
                        if (arrayOfInt[0] == 0 || arrayOfInt[1] > 0) {
                            return null;
                        }
                        arrayOfInt[2] = arrayOfInt[2] | 2;
                        break;
                    }
                    default: {
                        return null;
                    }
                }
                ++i;
                continue;
            }
            return null;
        }
        if (arrayOfInt[0] <= 0 || arrayOfInt[1] <= 0) {
            return null;
        }
        if (arrayOfInt[0] > 16384 || arrayOfInt[1] > 0x100000) {
            return null;
        }
        return arrayOfInt;
    }

    private void getLinkMap(List<DesignDataLinkDefine> linkList, Map<String, DesignDataLinkDefine> linkMap) {
        if (linkList != null) {
            for (DesignDataLinkDefine dataLinkDefine : linkList) {
                linkMap.put(dataLinkDefine.getPosY() + "_" + dataLinkDefine.getPosX(), dataLinkDefine);
            }
        }
    }

    private void setCellRange(GridCellData cellData, XSSFSheet sheet, int row, int col) {
        int colSpan = cellData.getColSpan();
        int rowSpan = cellData.getRowSpan();
        CellRangeAddress region = null;
        if (colSpan > 1 && rowSpan == 1) {
            int needRangeRow4Begin = row - 1;
            int needRangeRow4Last = row - 1;
            int needRangeCol4Begin = col - 1;
            int needRangeCol4Last = col - 1 + colSpan - 1;
            region = new CellRangeAddress(needRangeRow4Begin, needRangeRow4Last, needRangeCol4Begin, needRangeCol4Last);
        } else if (rowSpan > 1 && colSpan == 1) {
            int needRangeRow4Begin = row - 1;
            int needRangeRow4Last = row - 1 + rowSpan - 1;
            int needRangeCol4Begin = col - 1;
            int needRangeCol4Last = col - 1;
            region = new CellRangeAddress(needRangeRow4Begin, needRangeRow4Last, needRangeCol4Begin, needRangeCol4Last);
        } else if (colSpan > 1 && rowSpan > 1) {
            int needRangeRow4Begin = row - 1;
            int needRangeRow4Last = row - 1 + rowSpan - 1;
            int needRangeCol4Begin = col - 1;
            int needRangeCol4Last = col - 1 + colSpan - 1;
            region = new CellRangeAddress(needRangeRow4Begin, needRangeRow4Last, needRangeCol4Begin, needRangeCol4Last);
        }
        if (region != null) {
            sheet.addMergedRegion(region);
        }
    }

    private void setFontStyle(XSSFFont font, GridCellData cellData) {
        XSSFColor fontColor = new XSSFColor(ExcelExportService.hex2Rgb(Grid2DataSeralizeToGeGe.intToHtmlColor(cellData.getForeGroundColor(), "#000000")), (IndexedColorMap)new DefaultIndexedColorMap());
        byte[] wRgb = new byte[]{-1, -1, -1};
        String savedRgbStr = new String(fontColor.getRGB());
        String wRgbStr = new String(wRgb);
        font.setColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());
        if (!savedRgbStr.equals(wRgbStr)) {
            font.setColor(fontColor);
        }
        int fontStyle = cellData.getFontStyle();
        font.setFontName(cellData.getFontName());
        font.setFontHeightInPoints((short)(cellData.getFontSize() * 72 / 96));
        if ((2 & fontStyle) == 2) {
            font.setBold(true);
        }
        if ((4 & fontStyle) == 4) {
            font.setItalic(true);
        }
        if ((8 & fontStyle) == 8) {
            font.setUnderline((byte)1);
        }
        if ((0x10 & fontStyle) == 16) {
            font.setStrikeout(true);
        }
    }

    private void setBorderStyle(XSSFCellStyle style, GridCellData cellData) {
        int vertAlign = cellData.getVertAlign();
        int horzAlign = cellData.getHorzAlign();
        int backGroundStyle = cellData.getBackGroundStyle();
        XSSFColor backStyleColor = new XSSFColor(ExcelExportService.hex2Rgb("#C8CBCC"), (IndexedColorMap)new DefaultIndexedColorMap());
        XSSFColor backGroundColor = new XSSFColor(ExcelExportService.hex2Rgb(Grid2DataSeralizeToGeGe.intToHtmlColor(cellData.getBackGroundColor(), "#ffffff")), (IndexedColorMap)new DefaultIndexedColorMap());
        XSSFColor bottomBorderColor = new XSSFColor(new Color(cellData.getBottomBorderColor()), (IndexedColorMap)new DefaultIndexedColorMap());
        XSSFColor rightBorderColor = new XSSFColor(new Color(cellData.getRightBorderColor()), (IndexedColorMap)new DefaultIndexedColorMap());
        BorderStyle excelBottomBorderStyle = this.formBorder2ExcelBorder(cellData.getBottomBorderStyle());
        BorderStyle excelRightBorderStyle = this.formBorder2ExcelBorder(cellData.getRightBorderStyle());
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setBorderBottom(excelBottomBorderStyle);
        style.setBorderRight(excelRightBorderStyle);
        XSSFColor deaultBorderColor = new XSSFColor(new Color(217, 217, 217), (IndexedColorMap)new DefaultIndexedColorMap());
        if (cellData.getBottomBorderColor() < 0) {
            style.setBottomBorderColor(deaultBorderColor);
        } else {
            style.setBottomBorderColor(bottomBorderColor);
        }
        if (cellData.getRightBorderColor() < 0) {
            style.setRightBorderColor(deaultBorderColor);
        } else {
            style.setRightBorderColor(rightBorderColor);
        }
        if (cellData.isVertText()) {
            style.setRotation((short)255);
        }
        if (cellData.isWrapLine()) {
            style.setWrapText(true);
        }
        style.setIndention((short)cellData.getIndent());
        if (cellData.isEditable()) {
            style.setLocked(true);
        }
        if (horzAlign == 1) {
            style.setAlignment(HorizontalAlignment.forInt((int)1));
        } else if (horzAlign == 3) {
            style.setAlignment(HorizontalAlignment.forInt((int)2));
        } else if (horzAlign == 2) {
            style.setAlignment(HorizontalAlignment.forInt((int)3));
        }
        if (vertAlign == 1) {
            style.setVerticalAlignment(VerticalAlignment.forInt((int)0));
        } else if (vertAlign == 3) {
            style.setVerticalAlignment(VerticalAlignment.forInt((int)1));
        } else if (vertAlign == 2) {
            style.setVerticalAlignment(VerticalAlignment.forInt((int)2));
        }
        if (backGroundStyle == 0) {
            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            XSSFColor silverBackGroundColor = new XSSFColor(new Color(232, 232, 232), (IndexedColorMap)new DefaultIndexedColorMap());
            byte[] wRgb = new byte[]{0, 0, 0};
            String savedRgbStr = new String(backGroundColor.getRGB());
            String wRgbStr = new String(wRgb);
            boolean silverHead = cellData.isSilverHead();
            if (savedRgbStr.equals(wRgbStr) && silverHead) {
                style.setFillForegroundColor(silverBackGroundColor);
            } else {
                style.setFillForegroundColor(backGroundColor);
            }
        } else if (backGroundStyle == 11) {
            style.setFillPattern(FillPatternType.THIN_HORZ_BANDS);
            style.setFillForegroundColor(backStyleColor);
        } else if (backGroundStyle == 8) {
            style.setFillPattern(FillPatternType.THIN_VERT_BANDS);
            style.setFillForegroundColor(backStyleColor);
        } else if (backGroundStyle == 10) {
            style.setFillPattern(FillPatternType.THIN_BACKWARD_DIAG);
            style.setFillForegroundColor(backStyleColor);
        } else if (backGroundStyle == 9) {
            style.setFillPattern(FillPatternType.THIN_FORWARD_DIAG);
            style.setFillForegroundColor(backStyleColor);
        } else if (backGroundStyle == 4) {
            style.setFillPattern(FillPatternType.SQUARES);
            style.setFillForegroundColor(backStyleColor);
        } else if (backGroundStyle == 5) {
            style.setFillPattern(FillPatternType.DIAMONDS);
            style.setFillForegroundColor(backStyleColor);
        }
    }

    private static Color hex2Rgb(String colorStr) {
        return new Color(Integer.valueOf(colorStr.substring(1, 3), 16), Integer.valueOf(colorStr.substring(3, 5), 16), Integer.valueOf(colorStr.substring(5, 7), 16));
    }

    private BorderStyle formBorder2ExcelBorder(int formBottomBorderStyle) {
        switch (formBottomBorderStyle) {
            case -1: {
                return BorderStyle.THIN;
            }
            case 0: {
                return BorderStyle.NONE;
            }
            case 1: {
                return BorderStyle.THIN;
            }
            case 2: {
                return BorderStyle.DOTTED;
            }
            case 4: {
                return BorderStyle.MEDIUM;
            }
            case 8: {
                return BorderStyle.DOUBLE;
            }
        }
        return BorderStyle.THIN;
    }

    private void exportEfdcWithStyle(String[] formKeys, String showForm, String efdcScheme, FileOutputStream out) throws JQException, IOException {
        DesignFormulaSchemeDefine formulaSchemeDefine = this.nrDesignTimeController.queryFormulaSchemeDefine(efdcScheme.toString());
        String fileName = "\u8d22\u52a1\u516c\u5f0f";
        if (formulaSchemeDefine != null) {
            fileName = formulaSchemeDefine.getTitle() + "_\u8d22\u52a1\u516c\u5f0f";
        }
        XSSFWorkbook workbook = new XSSFWorkbook();
        HashSet<String> sheetNameSet = new HashSet<String>();
        for (int i = 0; i < formKeys.length; ++i) {
            String formId = formKeys[i];
            DesignFormDefine formDefine = this.nrDesignTimeController.queryFormById(formId);
            Grid2Data styleData = Grid2Data.bytesToGrid((byte[])formDefine.getBinaryData());
            List FormulaDefineList = this.nrDesignTimeController.getAllFormulasInForm(efdcScheme, formId);
            String sheetName = this.getsheetName(showForm, formDefine);
            int number = 1;
            while (!sheetNameSet.add(sheetName)) {
                sheetName = sheetName.split("_")[0] + "_" + number++;
            }
            try {
                this.exportExcelForEfdc(workbook, styleData, FormulaDefineList, i, sheetName, formId);
                continue;
            }
            catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }
        workbook.write((OutputStream)out);
        out.flush();
        if (out != null) {
            try {
                out.close();
            }
            catch (IOException e) {
                log.error(e.getMessage(), e);
            }
        }
    }

    private void exportExcelForEfdc(XSSFWorkbook workbook, Grid2Data styleData, List<DesignFormulaDefine> formulaDefineList, int sheetCount, String sheetName, String formId) throws Exception {
        HashMap<String, String> efdcFormulaPositionMap = new HashMap<String, String>();
        this.getefdcFormulaPosition(formId, efdcFormulaPositionMap, formulaDefineList);
        XSSFSheet sheet = workbook.createSheet();
        workbook.setSheetName(sheetCount, sheetName);
        sheet.setDefaultColumnWidth(15);
        int colCount = styleData.getColumnCount();
        int rowCount = styleData.getRowCount();
        for (int h = 1; h < rowCount; ++h) {
            XSSFRow row = sheet.createRow(h - 1);
            int rowHeight = styleData.getRowHeight(h);
            row.setHeightInPoints((float)(rowHeight * 72 / 96));
            for (int l = 1; l < colCount; ++l) {
                GridCellData cellData = styleData.getGridCellData(l, h);
                this.setCellRange(cellData, sheet, h, l);
                sheet.setColumnWidth(l - 1, (int)((double)styleData.getColumnWidth(l) / 8.0 * 256.0));
                XSSFCell cell = row.createCell(l - 1);
                XSSFCellStyle style = workbook.createCellStyle();
                this.setBorderStyle(style, cellData);
                XSSFFont font = workbook.createFont();
                this.setFontStyle(font, cellData);
                style.setFont((Font)font);
                cell.setCellStyle((CellStyle)style);
                cell.setCellValue(cellData.getShowText());
                String excelPositionString = h + "_" + l;
                for (Map.Entry formula : efdcFormulaPositionMap.entrySet()) {
                    String formulaPosition = (String)formula.getKey();
                    String formulaExpression = (String)formula.getValue();
                    if (!excelPositionString.equals(formulaPosition)) continue;
                    cell.setCellValue(formulaExpression);
                }
            }
        }
    }

    private void getefdcFormulaPosition(String formId, Map<String, String> efdcFormulaPositionMap, List<DesignFormulaDefine> formulaDefineList) {
        for (DesignFormulaDefine designFormulaDefine : formulaDefineList) {
            String formula = designFormulaDefine.getExpression();
            if (formula.startsWith("//")) continue;
            String pos = formula.trim().split("]")[0].substring(1);
            try {
                int x = Integer.parseInt(pos.split(",")[0]);
                int y = Integer.parseInt(pos.split(",")[1]);
                DesignDataLinkDefine dataLinkDefine = this.nrDesignTimeController.queryDataLinkDefineByColRow(formId, y, x);
                if (dataLinkDefine == null) continue;
                int form2ExcelX = dataLinkDefine.getPosX();
                int form2ExcelY = dataLinkDefine.getPosY();
                String mapKey = form2ExcelY + "_" + form2ExcelX;
                efdcFormulaPositionMap.put(mapKey, formula);
            }
            catch (Exception exception) {}
        }
    }

    public void exportParamAsync(ExcelExportVO excelExportObj, AsyncTaskMonitor monitor) throws Exception {
        monitor.progressAndMessage(0.05, "\u5f00\u59cb\u5bfc\u51fa");
        DesignFormSchemeDefine designFormSchemeDefine = this.nrDesignTimeController.queryFormSchemeDefine(excelExportObj.getSchemeId());
        String fileName = designFormSchemeDefine.getTitle() + "_\u8868\u6837.xlsx";
        StringBuffer filePath = new StringBuffer();
        NpContext context = NpContextHolder.getContext();
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String formatDate = dateFormat.format(date);
        String dirUU = UUIDUtils.getKey();
        filePath.append(EXPORTDIR).append(File.separator).append(context.getUser().getName()).append(File.separator).append(formatDate).append(File.separator).append(dirUU);
        String resultLocation = filePath.toString();
        try {
            this.exportAsync(resultLocation, excelExportObj, monitor, fileName);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_171, (Throwable)e);
        }
        monitor.progressAndMessage(0.95, "");
        monitor.finish("excel\u5bfc\u51fa\u5b8c\u6210", (Object)"excel\u5bfc\u51fa\u5b8c\u6210");
    }

    private void exportAsync(String resultLocation, ExcelExportVO excelExportObj, AsyncTaskMonitor monitor, String fileName) throws Exception {
        String[] formKeys = excelExportObj.getFormKeys();
        String viewType = excelExportObj.getViewType();
        String formulaSchemeId = excelExportObj.getFormulaSchemeId();
        String showForm = excelExportObj.getShowForm();
        HashSet<String> sheetNameSet = new HashSet<String>();
        File file = new File(resultLocation);
        if (!file.exists()) {
            file.mkdirs();
        }
        try (FileOutputStream fos = new FileOutputStream(resultLocation.concat(File.separator).concat(fileName));){
            if (VIEW_TYPE_VT_FINANCE_FORMULA.equals(viewType)) {
                this.exportEfdcWithStyle(formKeys, showForm, excelExportObj.getEfdcScheme(), fos);
            } else {
                XSSFWorkbook workbook = new XSSFWorkbook();
                for (int i = 0; i < formKeys.length; ++i) {
                    String formId = formKeys[i];
                    DesignFormDefine formDefine = this.nrDesignTimeController.queryFormById(formId);
                    Grid2Data styleData = Grid2Data.bytesToGrid((byte[])formDefine.getBinaryData());
                    String sheetName = this.getsheetName(showForm, formDefine);
                    int number = 1;
                    while (!sheetNameSet.add(sheetName)) {
                        sheetName = sheetName.split("_")[0] + "_" + number++;
                    }
                    this.exportExcel(workbook, null, styleData, i, sheetName, formId, viewType, formulaSchemeId);
                }
                workbook.write((OutputStream)fos);
            }
            try (FileInputStream uploadInputStream = new FileInputStream(resultLocation.concat(File.separator).concat(fileName));){
                OssUploadUtil.upload(fileName, uploadInputStream, excelExportObj.getDownLoadKey());
            }
            fos.flush();
        }
    }

    static {
        fileTypeMap.put(0, "\u901a\u7528\u7c7b\u578b");
        fileTypeMap.put(1, "\u6d6e\u70b9\u578b");
        fileTypeMap.put(2, "\u5b57\u7b26\u578b");
        fileTypeMap.put(3, "\u6574\u6570\u7c7b\u578b");
        fileTypeMap.put(4, "\u5e03\u5c14\u7c7b\u578b");
        fileTypeMap.put(5, "\u65e5\u671f\u7c7b\u578b");
        fileTypeMap.put(19, "\u65f6\u95f4\u7c7b\u578b");
        fileTypeMap.put(6, "\u65e5\u671f\u65f6\u95f4\u7c7b\u578b");
        fileTypeMap.put(7, "UUID\u7c7b\u578b");
        fileTypeMap.put(8, "\u6570\u503c\u578b");
        fileTypeMap.put(9, "\u65f6\u95f4\u6233");
        fileTypeMap.put(16, "\u6587\u672c\u7c7b\u578b");
        fileTypeMap.put(17, "\u56fe\u7247\u7c7b\u578b");
        fileTypeMap.put(22, "\u9644\u4ef6\u7c7b\u578b");
        gatherTypeMap = new HashMap();
        gatherTypeMap.put(0, "\u4e0d\u6c47\u603b");
        gatherTypeMap.put(1, "\u7d2f\u52a0\u6c47\u603b");
        gatherTypeMap.put(2, "\u8ba1\u6570\u6c47\u603b");
        gatherTypeMap.put(3, "\u5e73\u5747\u503c\u6c47\u603b");
        gatherTypeMap.put(4, "\u6700\u5c0f\u503c\u6c47\u603b");
        gatherTypeMap.put(5, "\u6700\u5927\u503c\u6c47\u603b");
        gatherTypeMap.put(6, "\u91cd\u7b97\u6307\u6807\u8ba1\u7b97\u8868\u8fbe\u5f0f");
        gatherTypeMap.put(7, "\u53bb\u91cd\u8ba1\u6570\u6c47\u603b");
        gatherTypeMap.put(8, "\u7f57\u5217\u6c47\u603b");
        gatherTypeMap.put(9, "\u53bb\u91cd\u7f57\u5217\u6c47\u603b");
        gatherTypeMap.put(10, "\u672b\u7ea7\u7f57\u5217\u6c47\u603b");
        ROOT_LOCATION = System.getProperty("java.io.tmpdir");
        EXPORTDIR = ROOT_LOCATION + File.separator + ".nr" + File.separator + "AppData" + File.separator + "export";
    }
}

