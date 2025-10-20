/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.grid.CellField
 *  com.jiuqi.grid.GridCell
 *  com.jiuqi.grid.GridCellProperty
 *  com.jiuqi.grid.GridData
 *  com.jiuqi.grid.GridFieldList
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IExpressionEvaluator
 *  com.jiuqi.np.grid.CellDataProperty
 *  com.jiuqi.np.grid.CellDataPropertyIntf
 *  com.jiuqi.np.grid.Font
 *  com.jiuqi.np.grid.NumberCellProperty
 *  com.jiuqi.nvwa.grid2.Grid2CellField
 *  com.jiuqi.nvwa.grid2.Grid2Data
 *  com.jiuqi.nvwa.grid2.Grid2FieldList
 *  com.jiuqi.nvwa.grid2.GridCellAddedData
 *  com.jiuqi.nvwa.grid2.GridCellData
 *  com.jiuqi.nvwa.grid2.GridEnums
 *  com.jiuqi.nvwa.grid2.GridEnums$DataType
 *  com.jiuqi.nvwa.grid2.GridEnums$GridBorderStyle
 *  com.jiuqi.nvwa.grid2.graphics.ImageData
 *  com.jiuqi.nvwa.grid2.graphics.ImageDescriptor
 *  com.jiuqi.nvwa.grid2.graphics.impl.ImageDescriptorImpl
 *  com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService
 *  com.jiuqi.util.JqLib
 *  com.jiuqi.util.StringUtils
 *  com.jiuqi.xg.process.Border
 *  com.jiuqi.xg.process.GraphicalFactoryManager
 *  com.jiuqi.xg.process.IDrawElement
 *  com.jiuqi.xg.process.obj.PageDrawObject
 *  com.jiuqi.xg.process.obj.TextDrawObject
 *  com.jiuqi.xg.process.obj.TextTemplateObject
 *  com.jiuqi.xg.process.table.obj.TableDrawObject
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.jiuqi.nr.definition.facade.print.common.other;

import com.jiuqi.grid.CellField;
import com.jiuqi.grid.GridCell;
import com.jiuqi.grid.GridCellProperty;
import com.jiuqi.grid.GridData;
import com.jiuqi.grid.GridFieldList;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IExpressionEvaluator;
import com.jiuqi.np.grid.CellDataProperty;
import com.jiuqi.np.grid.CellDataPropertyIntf;
import com.jiuqi.np.grid.Font;
import com.jiuqi.np.grid.NumberCellProperty;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.print.WordLabelDefine;
import com.jiuqi.nr.definition.facade.print.common.define.DefaultPageNumberGenerateStrategy;
import com.jiuqi.nr.definition.facade.print.common.define.IPageNumberGenerateStrategy;
import com.jiuqi.nr.definition.facade.print.common.define.element.ReportLabelDrawObject;
import com.jiuqi.nr.definition.facade.print.common.define.element.WordLabelDrawObject;
import com.jiuqi.nr.definition.facade.print.common.interactor.PaginateInteractorBase;
import com.jiuqi.nr.definition.facade.print.common.other.GraphicalObjectPropertyCloneUtil;
import com.jiuqi.nr.definition.facade.print.common.param.IPrintParamBase;
import com.jiuqi.nr.definition.facade.print.common.parse.ParseContext;
import com.jiuqi.nr.definition.facade.print.common.parse.WordLabelParseExecuter;
import com.jiuqi.nr.definition.facade.print.core.FontConvertUtil;
import com.jiuqi.nr.definition.facade.print.core.ReportLabelTemplateObject;
import com.jiuqi.nr.definition.facade.print.core.WordLabelTemplateObject;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.definition.internal.impl.print.WordLabelDefineImpl;
import com.jiuqi.nvwa.grid2.Grid2CellField;
import com.jiuqi.nvwa.grid2.Grid2Data;
import com.jiuqi.nvwa.grid2.Grid2FieldList;
import com.jiuqi.nvwa.grid2.GridCellAddedData;
import com.jiuqi.nvwa.grid2.GridCellData;
import com.jiuqi.nvwa.grid2.GridEnums;
import com.jiuqi.nvwa.grid2.graphics.ImageData;
import com.jiuqi.nvwa.grid2.graphics.ImageDescriptor;
import com.jiuqi.nvwa.grid2.graphics.impl.ImageDescriptorImpl;
import com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService;
import com.jiuqi.util.JqLib;
import com.jiuqi.util.StringUtils;
import com.jiuqi.xg.process.Border;
import com.jiuqi.xg.process.GraphicalFactoryManager;
import com.jiuqi.xg.process.IDrawElement;
import com.jiuqi.xg.process.obj.PageDrawObject;
import com.jiuqi.xg.process.obj.TextDrawObject;
import com.jiuqi.xg.process.obj.TextTemplateObject;
import com.jiuqi.xg.process.table.obj.TableDrawObject;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PrintUtil {
    private static final Logger logger = LoggerFactory.getLogger(PrintUtil.class);

    private static int getCellLineSpace() {
        INvwaSystemOptionService systemOptionService = BeanUtil.getBean(INvwaSystemOptionService.class);
        String value = systemOptionService.get("other-group", "GRID_LINE_SPACE");
        if (!StringUtils.isEmpty((String)value)) {
            try {
                return Integer.parseInt(value);
            }
            catch (Exception e) {
                logger.error("\u83b7\u53d6\u8868\u683c\u6587\u5b57\u884c\u95f4\u8ddd\u5931\u8d25", e);
            }
        }
        return 0;
    }

    public static String replaceAll(String source) {
        try {
            if (StringUtils.isEmpty((String)source)) {
                return "";
            }
            int length = source.length();
            if (length > 7) {
                int beginIndex = source.indexOf("<D>");
                int endIndex = source.indexOf("</D>");
                if (-1 != beginIndex && -1 != endIndex) {
                    String begin = source.substring(0, beginIndex);
                    String repleaseString = source.substring(beginIndex + 3, endIndex);
                    String end = source.substring(endIndex + 4, source.length());
                    repleaseString = "sys_year".equals(repleaseString) ? "{#\u65e5\u671f:yyyy}" : ("DWMC".equals(repleaseString) || "QYMC".equals(repleaseString) ? "{[sys_unittitle]}" : ("R$(YF,2)".equals(repleaseString) ? "{[cur_time]}" : "<D>" + repleaseString + "</D>"));
                    source = begin + repleaseString + end;
                }
            }
            source = source.replace("%d", "{#PageNumber}");
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return source;
    }

    public static WordLabelDefine[] getDefaultWordLabels() {
        WordLabelDefine[] wordLabels = new WordLabelDefine[3];
        WordLabelDefineImpl define = new WordLabelDefineImpl();
        define.setElement(0);
        define.setHorizontalPos(1);
        define.setVerticalPos(0);
        define.setText("{#RPTMAINTITLE}");
        define.setScope(0);
        Font font = new Font();
        font.setBold(true);
        font.setSize(20);
        define.setFont(font);
        wordLabels[0] = define;
        define = new WordLabelDefineImpl();
        define.setElement(1);
        define.setHorizontalPos(0);
        define.setVerticalPos(0);
        define.setText("{[SYS_UNITTITLE]}");
        define.setScope(0);
        font = new Font();
        font.setBold(false);
        font.setSize(9);
        define.setFont(font);
        wordLabels[1] = define;
        define = new WordLabelDefineImpl();
        define.setElement(1);
        define.setHorizontalPos(2);
        define.setVerticalPos(0);
        define.setText("{[CUR_PERIODSTR]}");
        define.setScope(0);
        define.setFont(font);
        wordLabels[2] = define;
        return wordLabels;
    }

    public static GridData grid2DataToGridData(Grid2Data n, GridData o) {
        if (null == o) {
            int options = 3;
            o = new GridData(options);
        }
        o.setColCount(n.getColumnCount());
        o.setRowCount(n.getRowCount());
        o.setScrollTopCol(n.getHeaderColumnCount());
        o.setScrollTopRow(n.getHeaderRowCount());
        o.setScrollBottomCol(n.getFooterColumnCount());
        o.setScrollBottomRow(n.getFooterRowCount());
        for (int i = 0; i < n.getRowCount(); ++i) {
            o.setRowHeights(i, n.getRowHeight(i));
            o.setRowVisible(i, !n.isRowHidden(i));
            o.setRowAutoSize(i, n.isRowAutoHeight(i));
            for (int j = 0; j < n.getColumnCount(); ++j) {
                if (i == 0) {
                    o.setColWidths(j, n.getColumnWidth(j));
                    o.setColVisible(j, !n.isColumnHidden(j));
                    o.setColAutoSize(j, n.isColumnAutoWidth(j));
                }
                GridCellProperty cell = o.getCellForChange(j, i);
                cell.beginUpdate();
                PrintUtil.copyCellData((GridCell)cell, n.getGridCellData(j, i), 0);
                cell.endUpdate();
            }
        }
        Grid2FieldList gfl = n.merges();
        Grid2CellField cf = null;
        if (gfl != null) {
            for (int i = 0; i < gfl.count(); ++i) {
                cf = gfl.get(i);
                o.mergeCells(cf.left, cf.top, cf.right, cf.bottom);
            }
        }
        return o;
    }

    public static Grid2Data gridDataToGrid2Data(GridData n, Grid2Data o) {
        if (null == o) {
            o = new Grid2Data();
        }
        o.setColumnCount(n.getColCount());
        o.setRowCount(n.getRowCount());
        o.setHeaderColumnCount(n.getScrollTopCol());
        o.setHeaderRowCount(n.getScrollTopRow());
        o.setFooterColumnCount(n.getScrollBottomCol());
        o.setFooterRowCount(n.getScrollBottomRow());
        for (int i = 0; i < n.getRowCount(); ++i) {
            o.setRowHeight(i, n.getRowHeights(i));
            o.setRowHidden(i, !n.getRowVisible(i));
            o.setRowAutoHeight(i, n.getRowAutoSize(i));
            for (int j = 0; j < n.getColCount(); ++j) {
                if (i == 0) {
                    o.setColumnWidth(j, n.getColWidths(j));
                    o.setColumnHidden(j, !n.getColVisible(j));
                    o.setColumnAutoWidth(j, n.getColAutoSize(j));
                }
                PrintUtil.copyCellData(n.getCell(j, i), o.getGridCellData(j, i), 1);
            }
        }
        GridFieldList gfl = n.merges();
        CellField cf = null;
        if (gfl != null) {
            for (int i = 0; i < gfl.count(); ++i) {
                cf = gfl.get(i);
                o.mergeCells(cf.left, cf.top, cf.right, cf.bottom);
            }
        }
        return o;
    }

    private static void copyCellData(GridCell c, GridCellData d, int direction) {
        if (direction == 0) {
            Object object;
            c.setBackStyle(d.getBackGroundStyle());
            if (d.getBackGroundColor() != -1) {
                c.setBackColor(d.getBackGroundColor());
                if (0 == c.getBackStyle()) {
                    c.setBackStyle(1);
                }
            }
            if (d.getForeGroundColor() != -1) {
                c.setFontColor(d.getForeGroundColor());
            }
            c.setFontSize(d.getFontSize());
            c.setFontName(d.getFontName());
            c.setFontBold((d.getFontStyle() & 2) != 0);
            c.setFontItalic((d.getFontStyle() & 4) != 0);
            c.setFontStrikeOut((d.getFontStyle() & 0x10) != 0);
            c.setFontUnderLine((d.getFontStyle() & 8) != 0);
            c.setSilverHead(d.isSilverHead());
            if (d.getRightBorderColor() != -1) {
                c.setREdgeColor(d.getRightBorderColor());
            }
            if (d.getRightBorderStyle() == GridEnums.GridBorderStyle.AUTO.getStyle() || d.getRightBorderStyle() == GridEnums.GridBorderStyle.NONE.getStyle() || d.getRightBorderStyle() == GridEnums.GridBorderStyle.SOLID.getStyle()) {
                c.setREdgeStyle(d.getRightBorderStyle() + 1);
            } else if (d.getRightBorderStyle() == GridEnums.GridBorderStyle.DASH.getStyle() || d.getRightBorderStyle() == GridEnums.GridBorderStyle.DOUBLE.getStyle()) {
                c.setREdgeStyle(d.getRightBorderStyle() + 2);
            } else if (d.getRightBorderStyle() == GridEnums.GridBorderStyle.BOLD.getStyle()) {
                c.setREdgeStyle(d.getRightBorderStyle() + 3);
            }
            if (d.getBottomBorderColor() != -1) {
                c.setBEdgeColor(d.getBottomBorderColor());
            }
            if (d.getBottomBorderStyle() == GridEnums.GridBorderStyle.AUTO.getStyle() || d.getBottomBorderStyle() == GridEnums.GridBorderStyle.NONE.getStyle() || d.getBottomBorderStyle() == GridEnums.GridBorderStyle.SOLID.getStyle()) {
                c.setBEdgeStyle(d.getBottomBorderStyle() + 1);
            } else if (d.getBottomBorderStyle() == GridEnums.GridBorderStyle.DASH.getStyle() || d.getBottomBorderStyle() == GridEnums.GridBorderStyle.DOUBLE.getStyle()) {
                c.setBEdgeStyle(d.getBottomBorderStyle() + 2);
            } else if (d.getBottomBorderStyle() == GridEnums.GridBorderStyle.BOLD.getStyle()) {
                c.setBEdgeStyle(d.getBottomBorderStyle() + 3);
            }
            c.setCanSelect(d.isSelectable());
            c.setCanModify(d.isEditable());
            c.setWrapLine(d.isWrapLine());
            c.setIndent(d.getIndent());
            c.setVertAlign(d.getVertAlign());
            c.setHorzAlign(d.getHorzAlign());
            c.setVertText(d.isVertText());
            c.setCssClass(d.getEditText());
            if (GridEnums.DataType.HotLink.ordinal() == d.getDataType()) {
                c.setDataType(GridEnums.DataType.Text.ordinal());
                c.setShowText(d.getEditText());
            } else {
                c.setDataType(d.getDataType());
                c.setShowText(d.getShowText());
            }
            if (d.getDataType() == GridEnums.getIntValue((Enum)GridEnums.DataType.Number)) {
                String formatter = d.getFormatter();
                if (!StringUtils.isEmpty((String)formatter)) {
                    String[] formatters = formatter.split("\\.");
                    if (formatters.length == 2) {
                        int length = formatters[1].length();
                        if (formatters[1].endsWith("%")) {
                            length = formatters[1].length() + 1;
                        }
                        CellDataProperty cdp = new CellDataProperty(c.getDataType(), c.getDataFlag(), c.getEditMode(), c.getDataFormat(), c.getDataProperty());
                        NumberCellProperty numberCellProperty = new NumberCellProperty((CellDataPropertyIntf)cdp);
                        numberCellProperty.setDecimal(length);
                        c.setDataFlag((int)numberCellProperty.getCellDataProperty().getDataFlag());
                        if (formatters[0].contains(",")) {
                            numberCellProperty.setThoundsMarks(true);
                        }
                        if (formatters[1].endsWith("%")) {
                            numberCellProperty.setIsPercent(true);
                        }
                        c.setDataProperty((int)numberCellProperty.getCellDataProperty().getDataProperty());
                    } else {
                        NumberCellProperty numberCellProperty;
                        CellDataProperty cdp;
                        if (formatters[0].contains(",")) {
                            cdp = new CellDataProperty(c.getDataType(), c.getDataFlag(), c.getEditMode(), c.getDataFormat(), c.getDataProperty());
                            numberCellProperty = new NumberCellProperty((CellDataPropertyIntf)cdp);
                            numberCellProperty.setThoundsMarks(true);
                            c.setDataProperty((int)numberCellProperty.getCellDataProperty().getDataProperty());
                        }
                        if (formatters[0].endsWith("%")) {
                            cdp = new CellDataProperty(c.getDataType(), c.getDataFlag(), c.getEditMode(), c.getDataFormat(), c.getDataProperty());
                            numberCellProperty = new NumberCellProperty((CellDataPropertyIntf)cdp);
                            numberCellProperty.setIsPercent(true);
                            c.setDataProperty((int)numberCellProperty.getCellDataProperty().getDataProperty());
                        }
                    }
                }
            } else if (d.getDataType() == GridEnums.getIntValue((Enum)GridEnums.DataType.Graphic) && null != (object = d.getCellData("IMG_DATA")) && object instanceof ImageDescriptor) {
                ImageDescriptor imageDescriptor = (ImageDescriptor)object;
                String imageName = imageDescriptor.getImageId();
                try {
                    c.setImageData(imageDescriptor.getImageData().getBytes(), imageName.substring(imageName.lastIndexOf(".") + 1, imageName.length()));
                }
                catch (IOException e) {
                    logger.error(e.getMessage(), e);
                }
            }
            c.setMultiLine(d.isMultiLine());
            c.setFitFontSize(d.isFitFontSize());
            GridCellAddedData data = null;
            String addDataStr = c.getScript();
            JSONObject obj = null;
            if (addDataStr != null) {
                try {
                    data = new GridCellAddedData(addDataStr);
                }
                catch (JSONException e) {
                    // empty catch block
                }
            }
            if (data == null) {
                data = new GridCellAddedData();
            }
            if (d.getDataExString() != null) {
                try {
                    obj = new JSONObject(d.getDataExString());
                    data.setDataEx(obj);
                }
                catch (JSONException e) {
                    // empty catch block
                }
            }
            c.setScript(data.toString());
            c.setlineSpace(PrintUtil.getCellLineSpace());
        } else if (direction == 1) {
            byte[] imageDataBytes;
            d.setBackGroundColor(c.getBackColor());
            d.setBackGroundStyle(c.getBackStyle());
            d.setForeGroundColor(c.getFontColor());
            d.setFontSize(c.getFontSize());
            d.setFontName(c.getFontName());
            int style = 0;
            if (c.getFontBold()) {
                style |= 2;
            }
            if (c.getFontItalic()) {
                style |= 4;
            }
            if (c.getFontStrikeOut()) {
                style |= 0x10;
            }
            if (c.getFontUnderLine()) {
                style |= 8;
            }
            d.setFontStyle(style);
            if (c.getREdgeColor() == -16777201) {
                d.setRightBorderColor(0xD1D1D1);
            } else {
                d.setRightBorderColor(c.getREdgeColor());
            }
            if (c.getREdgeStyle() == 0 || c.getREdgeStyle() == 1 || c.getREdgeStyle() == 2) {
                d.setRightBorderStyle(c.getREdgeStyle() - 1);
            } else if (c.getREdgeStyle() == 4 || c.getREdgeStyle() == 10) {
                d.setRightBorderStyle(c.getREdgeStyle() - 2);
            } else if (c.getREdgeStyle() == 7) {
                d.setRightBorderStyle(c.getREdgeStyle() - 3);
            }
            if (c.getBEdgeColor() == -16777201) {
                d.setBottomBorderColor(0xD1D1D1);
            } else {
                d.setBottomBorderColor(c.getBEdgeColor());
            }
            if (c.getBEdgeStyle() == 0 || c.getBEdgeStyle() == 1 || c.getBEdgeStyle() == 2) {
                d.setBottomBorderStyle(c.getBEdgeStyle() - 1);
            } else if (c.getBEdgeStyle() == 4 || c.getBEdgeStyle() == 10) {
                d.setBottomBorderStyle(c.getBEdgeStyle() - 2);
            } else if (c.getBEdgeStyle() == 7) {
                d.setBottomBorderStyle(c.getBEdgeStyle() - 3);
            }
            d.setSelectable(c.getCanSelect());
            d.setEditable(c.getCanModify());
            d.setWrapLine(c.getWrapLine());
            d.setIndent(c.getIndent());
            d.setVertAlign(c.getVertAlign());
            d.setHorzAlign(c.getHorzAlign());
            d.setVertText(c.getVertText());
            d.setShowText(c.getShowText());
            d.setEditText(c.getCssClass());
            d.setDataType(c.getDataType());
            d.setSilverHead(c.getSilverHead());
            d.setMultiLine(c.getMultiLine());
            d.setFitFontSize(c.getFitFontSize());
            if (c.getDataType() == GridEnums.getIntValue((Enum)GridEnums.DataType.Graphic) && null != (imageDataBytes = c.getImageData())) {
                String imageType = c.getImageType();
                ImageDescriptorImpl imageDescriptorImpl = new ImageDescriptorImpl();
                ImageData imageData = new ImageData(imageDataBytes);
                imageDescriptorImpl.setImageData(imageData);
                imageDescriptorImpl.setImageId("grid\u8f6c\u6362\u65e0\u540d\u79f0." + imageType.toLowerCase());
                d.setCellData("IMG_DATA", (Object)imageDescriptorImpl);
            }
            GridCellAddedData data = null;
            String addDataStr = c.getScript();
            JSONObject obj = null;
            if (addDataStr != null) {
                try {
                    data = new GridCellAddedData(addDataStr);
                }
                catch (JSONException jSONException) {
                    // empty catch block
                }
            }
            if (data == null) {
                data = new GridCellAddedData();
            }
            if ((obj = data.getDataEx()) != null) {
                try {
                    d.setDataExFromString(obj.toString());
                }
                catch (JSONException jSONException) {
                    // empty catch block
                }
            }
        }
    }

    public static int getTotalPageNumber(IDrawElement drawElement, IPageNumberGenerateStrategy pageNumberGenerateStrategy) {
        if (pageNumberGenerateStrategy != null && pageNumberGenerateStrategy.getTotalPageNumber() > 0) {
            return pageNumberGenerateStrategy.getTotalPageNumber();
        }
        PageDrawObject parent = (PageDrawObject)drawElement.getParent();
        return parent.getParent().getPageCount();
    }

    public static int getCurrentPageNumber(int pageIndex) {
        return pageIndex + 1;
    }

    public static int getPageNumber(int pageIndex, IPageNumberGenerateStrategy pageNumberGenerateStrategy) {
        int currentPageNumber = pageIndex + 1;
        if (pageNumberGenerateStrategy != null) {
            currentPageNumber += pageNumberGenerateStrategy.getCurrentPageNumberOffset();
        }
        return currentPageNumber;
    }

    public static ParseContext createLabelParseContext(String reportGuid, int totalCount, int pageIndex, IPageNumberGenerateStrategy pageNumberGenerateStrategy, IPrintParamBase param, ExecutorContext executorContext, IExpressionEvaluator iExpressionEvaluator) {
        ParseContext parseContext = new ParseContext();
        parseContext.setPageNum(PrintUtil.getPageNumber(pageIndex, pageNumberGenerateStrategy), PrintUtil.getCurrentPageNumber(pageIndex), totalCount);
        parseContext.setRptOrderNum(null == pageNumberGenerateStrategy ? 1 : pageNumberGenerateStrategy.getTemplatePageOrderNumber());
        parseContext.setExecutorContext(executorContext);
        parseContext.setExpressionEvaluator(iExpressionEvaluator);
        parseContext.setPagePatternParser(pageNumberGenerateStrategy);
        if (reportGuid != null) {
            IRunTimeViewController runTimeFormDefineService = BeanUtil.getBean(IRunTimeViewController.class);
            FormDefine formDefine = runTimeFormDefineService.queryFormById(reportGuid);
            parseContext.setFormDefine(formDefine);
            Map<String, String> measureUnitMap = param.getMeasureMap();
            if (measureUnitMap != null && measureUnitMap.size() != 0) {
                String measureUnit = measureUnitMap.keySet().toArray()[0] + "," + measureUnitMap.get(measureUnitMap.keySet().toArray()[0]);
                if (!formDefine.getMeasureUnit().equals(measureUnit)) {
                    parseContext.setFQuanUnit(measureUnit);
                }
            }
        }
        if (param != null) {
            parseContext.setDimensionValueSet(param.getDimensionValueSet());
            parseContext.setFormKey(param.getFormKey());
            parseContext.setFormSchemeKey(param.getFormSchemeKey());
            parseContext.setFormulaSchemeKey(param.getFormulaSchemeKey());
        }
        return parseContext;
    }

    public static boolean isNumber(String value) {
        return PrintUtil.isInteger(value) || PrintUtil.isDouble(value);
    }

    public static boolean isInteger(String value) {
        try {
            Integer.parseInt(value);
            return true;
        }
        catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isDouble(String value) {
        try {
            Double.parseDouble(value);
            return value.contains(".");
        }
        catch (NumberFormatException e) {
            return false;
        }
    }

    public static String formatDecimal(GridCell cell, String processedContent) {
        int decimal;
        String strData = cell.getScript();
        String value = null;
        if (strData != null) {
            try {
                GridCellAddedData data = new GridCellAddedData(strData);
                JSONObject obj = data.getDataEx();
                value = obj.getString("Cell_Decimal");
            }
            catch (Exception obj) {
                // empty catch block
            }
        }
        if (!JqLib.isEmpty(value) && (decimal = Integer.valueOf(value).intValue()) >= 0) {
            String formatStr = "0";
            if (decimal > 0) {
                formatStr = formatStr + ".";
                while (decimal > 0) {
                    formatStr = formatStr + "0";
                    --decimal;
                }
            }
            double number = Double.valueOf(processedContent);
            processedContent = new DecimalFormat(formatStr).format(number);
        }
        return processedContent;
    }

    public static boolean isLastPage(int pageIndex, int pageCount) {
        return pageIndex == pageCount - 1;
    }

    public static boolean isOddPage(int pageIndex) {
        return pageIndex % 2 == 0;
    }

    public static boolean isEvenPage(int pageIndex) {
        return (pageIndex & 1) == 1;
    }

    public static void addExtraProperty(WordLabelDrawObject labelDrawObject, int pageIndex, String unParsedExp, IPageNumberGenerateStrategy pageNumberGenerateStrategy) {
        labelDrawObject.setProperty("isPageNum", "true");
        labelDrawObject.setProperty("exp", unParsedExp);
        labelDrawObject.setProperty("currPage", "" + PrintUtil.getCurrentPageNumber(pageIndex));
    }

    public static void addExtraProperty(ReportLabelDrawObject labelDrawObject, int pageIndex, String unParsedExp, IPageNumberGenerateStrategy pageNumberGenerateStrategy) {
        labelDrawObject.setProperty("isPageNum", "true");
        labelDrawObject.setProperty("exp", unParsedExp);
        labelDrawObject.setProperty("currPage", "" + PrintUtil.getCurrentPageNumber(pageIndex));
    }

    public static String executeparse(int pageIndex, WordLabelTemplateObject labelObject, WordLabelDrawObject labelDrawObject, String unParsedExp, PaginateInteractorBase paginateInteractor) {
        if (null != paginateInteractor.getReportGuid()) {
            if (!paginateInteractor.getReportGuid().equals(labelObject.getReportGuid())) {
                paginateInteractor.setReportGuid(labelObject.getReportGuid());
                paginateInteractor.getPatternAndValue().clear();
            }
        } else {
            paginateInteractor.setReportGuid(labelObject.getReportGuid());
            paginateInteractor.getPatternAndValue().clear();
        }
        if (null == paginateInteractor.getPageNumberGenerateStrategy()) {
            paginateInteractor.setPageNumberGenerateStrategy(new DefaultPageNumberGenerateStrategy());
        }
        ParseContext parseContext = PrintUtil.createLabelParseContext(paginateInteractor.getParam().getFormKey(), PrintUtil.getTotalPageNumber((IDrawElement)labelDrawObject, paginateInteractor.getPageNumberGenerateStrategy()), pageIndex, paginateInteractor.getPageNumberGenerateStrategy(), paginateInteractor.getParam(), paginateInteractor.getExecutorContext(), paginateInteractor.getExpressionEvaluator());
        parseContext.setSplit(labelObject.isSplitPrint());
        WordLabelParseExecuter instance = WordLabelParseExecuter.getInstance();
        String processedContent = instance.execute(parseContext, unParsedExp, paginateInteractor.getPatternAndValue());
        return processedContent;
    }

    public static String executeparse(int pageIndex, ReportLabelTemplateObject labelObject, ReportLabelDrawObject labelDrawObject, String unParsedExp, PaginateInteractorBase paginateInteractor) {
        if (null != paginateInteractor.getReportGuid()) {
            if (!paginateInteractor.getReportGuid().equals(labelObject.getReportGuid())) {
                paginateInteractor.setReportGuid(labelObject.getReportGuid());
                paginateInteractor.getPatternAndValue().clear();
            }
        } else {
            paginateInteractor.setReportGuid(labelObject.getReportGuid());
            paginateInteractor.getPatternAndValue().clear();
        }
        if (null == paginateInteractor.getPageNumberGenerateStrategy()) {
            paginateInteractor.setPageNumberGenerateStrategy(new DefaultPageNumberGenerateStrategy());
        }
        ParseContext parseContext = PrintUtil.createLabelParseContext(paginateInteractor.getParam().getFormKey(), PrintUtil.getTotalPageNumber((IDrawElement)labelDrawObject, paginateInteractor.getPageNumberGenerateStrategy()), pageIndex, paginateInteractor.getPageNumberGenerateStrategy(), paginateInteractor.getParam(), paginateInteractor.getExecutorContext(), paginateInteractor.getExpressionEvaluator());
        parseContext.setSplit(labelObject.isSplitPrint());
        WordLabelParseExecuter instance = WordLabelParseExecuter.getInstance();
        String processedContent = instance.execute(parseContext, unParsedExp, paginateInteractor.getPatternAndValue());
        return processedContent;
    }

    public static String executeparse(int pageIndex, TextTemplateObject labelObject, TextDrawObject labelDrawObject, String unParsedExp, PaginateInteractorBase paginateInteractor) {
        if (null == paginateInteractor.getPageNumberGenerateStrategy()) {
            paginateInteractor.setPageNumberGenerateStrategy(new DefaultPageNumberGenerateStrategy());
        }
        ParseContext parseContext = PrintUtil.createLabelParseContext(paginateInteractor.getParam().getFormKey(), PrintUtil.getTotalPageNumber((IDrawElement)labelDrawObject, paginateInteractor.getPageNumberGenerateStrategy()), pageIndex, paginateInteractor.getPageNumberGenerateStrategy(), paginateInteractor.getParam(), paginateInteractor.getExecutorContext(), paginateInteractor.getExpressionEvaluator());
        WordLabelParseExecuter instance = WordLabelParseExecuter.getInstance();
        return instance.execute(parseContext, unParsedExp, paginateInteractor.getPatternAndValue());
    }

    public static void drawLeftLabel(WordLabelDrawObject labelDrawObject, String content, int pageIndex) {
        String leftText = content.substring(0, content.length() / 2);
        PageDrawObject page = (PageDrawObject)labelDrawObject.getParent();
        TextDrawObject textDrawObj = PrintUtil.createDrawObject("text");
        GraphicalObjectPropertyCloneUtil.TextPropertyClone(labelDrawObject, textDrawObj);
        double paperWidth = 0.0;
        int orientation = page.getOrientation();
        if (orientation == 512) {
            paperWidth = page.getPaper().getWidth();
        } else if (orientation == 256) {
            paperWidth = page.getPaper().getHeight();
        }
        double actualWidth = FontConvertUtil.getActualWidth(labelDrawObject.getFont(), leftText);
        textDrawObj.setID("rpt_title" + labelDrawObject.getID() + pageIndex);
        textDrawObj.setContent(leftText);
        textDrawObj.setX(paperWidth - page.getMargins()[3] - actualWidth);
        textDrawObj.setY(labelDrawObject.getY());
        textDrawObj.setWidth(actualWidth);
        textDrawObj.setHeight(labelDrawObject.getHeight());
        textDrawObj.setHorizonAlignment(131072);
        page.add((IDrawElement)textDrawObj);
    }

    public static void drawLeftLabel(ReportLabelDrawObject labelDrawObject, String content, int pageIndex) {
        String leftText = content.substring(0, content.length() / 2);
        PageDrawObject page = (PageDrawObject)labelDrawObject.getParent();
        TextDrawObject textDrawObj = PrintUtil.createDrawObject("text");
        GraphicalObjectPropertyCloneUtil.TextPropertyClone(labelDrawObject, textDrawObj);
        double paperWidth = 0.0;
        int orientation = page.getOrientation();
        if (orientation == 512) {
            paperWidth = page.getPaper().getWidth();
        } else if (orientation == 256) {
            paperWidth = page.getPaper().getHeight();
        }
        double actualWidth = FontConvertUtil.getActualWidth(labelDrawObject.getFont(), leftText);
        textDrawObj.setID("rpt_title" + labelDrawObject.getID() + pageIndex);
        textDrawObj.setContent(leftText);
        textDrawObj.setX(paperWidth - page.getMargins()[3] - actualWidth);
        textDrawObj.setY(labelDrawObject.getY());
        textDrawObj.setWidth(actualWidth);
        textDrawObj.setHeight(labelDrawObject.getHeight());
        textDrawObj.setHorizonAlignment(131072);
        page.add((IDrawElement)textDrawObj);
    }

    public static void drawRightLabel(WordLabelDrawObject labelDrawObject, String content, int pageIndex) {
        String rightText = content.substring(content.length() / 2);
        PageDrawObject page = (PageDrawObject)labelDrawObject.getParent();
        TextDrawObject textDrawObj = PrintUtil.createDrawObject("text");
        GraphicalObjectPropertyCloneUtil.TextPropertyClone(labelDrawObject, textDrawObj);
        textDrawObj.setID("rpt_title" + labelDrawObject.getID() + pageIndex);
        textDrawObj.setContent(rightText);
        textDrawObj.setX(page.getMargins()[2]);
        textDrawObj.setY(labelDrawObject.getY());
        textDrawObj.setWidth(FontConvertUtil.getActualWidth(labelDrawObject.getFont(), rightText));
        textDrawObj.setHeight(labelDrawObject.getHeight());
        textDrawObj.setHorizonAlignment(16384);
        page.add((IDrawElement)textDrawObj);
    }

    public static void drawRightLabel(ReportLabelDrawObject labelDrawObject, String content, int pageIndex) {
        String rightText = content.substring(content.length() / 2);
        PageDrawObject page = (PageDrawObject)labelDrawObject.getParent();
        TextDrawObject textDrawObj = PrintUtil.createDrawObject("text");
        GraphicalObjectPropertyCloneUtil.TextPropertyClone(labelDrawObject, textDrawObj);
        textDrawObj.setID("rpt_title" + labelDrawObject.getID() + pageIndex);
        textDrawObj.setContent(rightText);
        textDrawObj.setX(page.getMargins()[2]);
        textDrawObj.setY(labelDrawObject.getY());
        textDrawObj.setWidth(FontConvertUtil.getActualWidth(labelDrawObject.getFont(), rightText));
        textDrawObj.setHeight(labelDrawObject.getHeight());
        textDrawObj.setHorizonAlignment(16384);
        page.add((IDrawElement)textDrawObj);
    }

    public static void resetTableBottomLineBorderStyle(TableDrawObject tableDrawObj, int style) {
        GridData gridData = tableDrawObj.getGridData();
        int row = gridData.getRowCount() - 1;
        for (int col = 0; col < gridData.getColCount(); ++col) {
            GridCell cell = gridData.getCell(col, row);
            if (cell.getBEdgeStyle() != 0 && cell.getBEdgeStyle() != style) continue;
            cell.setBEdgeStyle(2);
            cell.setBEdgeColor(0);
            gridData.setCell(cell);
        }
    }

    public static void resetTableBottomLineBorderStyle(TableDrawObject tableDrawObj) {
        GridData gridData = tableDrawObj.getGridData();
        int row = gridData.getRowCount() - 1;
        for (int col = 0; col < gridData.getColCount(); ++col) {
            GridCell cell = gridData.getCell(col, row);
            if (cell.getBEdgeStyle() != 0 && cell.getBEdgeStyle() != 1) continue;
            cell.setBEdgeStyle(2);
            cell.setBEdgeColor(0);
            gridData.setCell(cell);
        }
    }

    public static void setTableOutSideBorder(int pageIndex, TableDrawObject tableDrawObjcet, Border border, boolean oddLoRc, boolean evenLoRc) {
        if (PrintUtil.isOddPage(pageIndex)) {
            if (oddLoRc) {
                border.setLeftBorderVisible(false);
                border.setRightBorderVisible(true);
                GridData gridData = tableDrawObjcet.getGridData();
                int col = gridData.getColCount() - 1;
                GridFieldList merges = gridData.merges();
                for (int row = 0; row < gridData.getRowCount(); ++row) {
                    GridCell cell = gridData.getCell(col, row);
                    CellField mergeRect = merges.getMergeRect(col, row);
                    if (mergeRect != null) {
                        GridCell cell1 = gridData.getCell(mergeRect.left, mergeRect.top);
                        cell1.setREdgeStyle(2);
                        cell1.setREdgeColor(0);
                    }
                    gridData.setCell(cell);
                }
            } else {
                border.setLeftBorderVisible(false);
                border.setRightBorderVisible(false);
            }
        } else if (evenLoRc) {
            border.setLeftBorderVisible(true);
            border.setRightBorderVisible(false);
            GridData gridData = tableDrawObjcet.getGridData();
            int col = 0;
            GridFieldList merges = gridData.merges();
            for (int row = 0; row < gridData.getRowCount(); ++row) {
                GridCell cell = gridData.getCell(col, row);
                CellField mergeRect = merges.getMergeRect(col, row);
                if (mergeRect != null) {
                    GridCell cell1 = gridData.getCell(mergeRect.left, mergeRect.top);
                    cell1.setLEdgeStyle(2);
                    cell1.setLEdgeColor(0);
                }
                gridData.setCell(cell);
            }
        } else {
            border.setLeftBorderVisible(false);
            border.setRightBorderVisible(false);
        }
    }

    public static void autoLineToNoneStyle(TableDrawObject tableDrawObj) {
        GridData gridData = tableDrawObj.getGridData();
        for (int row = 0; row < gridData.getRowCount(); ++row) {
            for (int col = 0; col < gridData.getColCount(); ++col) {
                GridCell cell = gridData.getCell(col, row);
                if (cell.getTEdgeStyle() == 0) {
                    cell.setTEdgeStyle(1);
                }
                if (cell.getBEdgeStyle() == 0) {
                    cell.setBEdgeStyle(1);
                }
                if (cell.getLEdgeStyle() == 0) {
                    cell.setLEdgeStyle(1);
                }
                if (cell.getREdgeStyle() == 0) {
                    cell.setREdgeStyle(1);
                }
                gridData.setCell(cell);
            }
        }
    }

    private static TextDrawObject createDrawObject(String kind) {
        return (TextDrawObject)GraphicalFactoryManager.getDrawObjectFactory((String)"REPORT_PRINT_NATURE").create(kind);
    }
}

