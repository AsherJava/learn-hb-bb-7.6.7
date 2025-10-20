/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.itextpdf.io.font.FontCache
 *  com.itextpdf.io.font.FontMetrics
 *  com.itextpdf.kernel.colors.Color
 *  com.itextpdf.kernel.colors.DeviceRgb
 *  com.itextpdf.kernel.font.PdfFont
 *  com.itextpdf.kernel.font.PdfFontFactory
 *  com.itextpdf.layout.Document
 *  com.itextpdf.layout.IPropertyContainer
 *  com.itextpdf.layout.borders.Border
 *  com.itextpdf.layout.borders.DashedBorder
 *  com.itextpdf.layout.borders.SolidBorder
 *  com.itextpdf.layout.element.Cell
 *  com.itextpdf.layout.element.Paragraph
 *  com.itextpdf.layout.element.Table
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.print.adapt.PdfHandler
 */
package com.jiuqi.va.query.util;

import com.itextpdf.io.font.FontCache;
import com.itextpdf.io.font.FontMetrics;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.IPropertyContainer;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.borders.DashedBorder;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.print.adapt.PdfHandler;
import com.jiuqi.va.query.print.BorderDTO;
import com.jiuqi.va.query.print.BorderTypeFullEnum;
import com.jiuqi.va.query.print.DocumentPropertyConsts;
import com.jiuqi.va.query.print.FontProp;
import com.jiuqi.va.query.print.GridCellProp;
import com.jiuqi.va.query.print.PropNameConsts;
import com.jiuqi.va.query.print.QueryPrintConst;
import com.jiuqi.va.query.print.TablePrintControl;
import com.jiuqi.va.query.print.domain.tablle.CustomTable;
import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

public class QueryPrintUtil {
    private static final Logger logger = LoggerFactory.getLogger(QueryPrintUtil.class);
    public static final String DEFAULT_COLOR = "#eeeeee";
    private static ConcurrentHashMap<String, PdfFont> fontMap = new ConcurrentHashMap(16);

    private QueryPrintUtil() {
    }

    public static float getDocumentAvailableHeight(Document document) {
        return ((Float)document.getProperty(DocumentPropertyConsts.AVAILABLE_HEIGHT.intValue())).floatValue();
    }

    public static float getDocumentAvailableWidth(Document document) {
        return ((Float)document.getProperty(8193)).floatValue();
    }

    public static void setDocumentAvailableHeight(Document document, float height) {
        document.setProperty(DocumentPropertyConsts.AVAILABLE_HEIGHT.intValue(), (Object)Float.valueOf(height));
    }

    public static float resetDocumentAvailableHeight(Document document) {
        float paddingBottom = document.getTopMargin();
        float paddingTop = document.getBottomMargin();
        float footerHeight = document.getProperty(DocumentPropertyConsts.HASFOOTER.intValue()) == null ? 0.0f : ((Float)document.getProperty(DocumentPropertyConsts.HASFOOTER.intValue())).floatValue();
        float headerHeight = document.getProperty(DocumentPropertyConsts.HASHEADER.intValue()) == null ? 0.0f : ((Float)document.getProperty(DocumentPropertyConsts.HASHEADER.intValue())).floatValue();
        float availableHeight = document.getPdfDocument().getDefaultPageSize().getHeight() - paddingBottom - paddingTop - footerHeight - headerHeight;
        document.setProperty(DocumentPropertyConsts.AVAILABLE_HEIGHT.intValue(), (Object)Float.valueOf(availableHeight));
        return QueryPrintUtil.getDocumentAvailableHeight(document);
    }

    public static float resetDocumentAvailableWidth(Document document) {
        float leftMargin = document.getLeftMargin();
        float rightMargin = document.getRightMargin();
        float availableWidth = document.getPdfDocument().getDefaultPageSize().getWidth() - leftMargin - rightMargin;
        document.setProperty(8193, (Object)Float.valueOf(availableWidth));
        return QueryPrintUtil.getDocumentAvailableWidth(document);
    }

    public static void decreaseDocumentAvailableHeight(Document document, float height) {
        float documentAvailableHeight = QueryPrintUtil.getDocumentAvailableHeight(document);
        document.setProperty(DocumentPropertyConsts.AVAILABLE_HEIGHT.intValue(), (Object)Float.valueOf(documentAvailableHeight - height));
    }

    public static void decreaseDocumentAvailableWidth(Document document, float width) {
        float documentAvailableWidth = QueryPrintUtil.getDocumentAvailableWidth(document);
        document.setProperty(8193, (Object)Float.valueOf(documentAvailableWidth - width));
    }

    public static void setCellFontWithoutSize(Paragraph paragraph, FontProp fontProp) {
        PdfFont font = QueryPrintUtil.changeToPDFFont(fontProp.getFontFamily());
        int colorValue = QueryPrintUtil.colorValue(fontProp.getColor());
        DeviceRgb deviceRgb = new DeviceRgb(new Color(colorValue));
        ((Paragraph)paragraph.setFont(font)).setFontColor((com.itextpdf.kernel.colors.Color)deviceRgb);
        if (fontProp.isBold()) {
            paragraph.setBold();
        }
        if (fontProp.isItalic()) {
            paragraph.setItalic();
        }
    }

    public static PdfFont changeToPDFFont(String fontFamily) {
        PdfFont pdfFont = null;
        try {
            if (CollectionUtils.isEmpty(fontMap)) {
                PdfFont font = PdfFontFactory.createFont((String)"static/fonts/STSong.ttf", (String)"Identity-H");
                fontMap.put("SimSun", font);
                fontMap.put("STSongStd-Light", font);
            }
            if (Objects.nonNull(pdfFont = fontMap.get(fontFamily))) {
                return pdfFont;
            }
            pdfFont = FontCache.getAllPredefinedCidFonts().containsKey(fontFamily) ? PdfFontFactory.createFont((String)fontFamily, (String)"UniGB-UCS2-H") : ("Microsoft YaHei".equals(fontFamily) ? PdfFontFactory.createFont((String)"static/fonts/YaHei.ttf", (String)"Identity-H") : PdfFontFactory.createFont((String)"STSongStd-Light", (String)"UniGB-UCS2-H"));
            fontMap.put(fontFamily, pdfFont);
        }
        catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        return pdfFont;
    }

    public static void clearFontMap() {
        fontMap.clear();
    }

    public static float millimetersToPoints(float value) {
        return value / 25.4f * 72.0f;
    }

    public static Paragraph renderParagraph(GridCellProp cellProp, String str, float fontSize) {
        Paragraph paragraph = new Paragraph(str);
        if (cellProp.isUnderlined()) {
            paragraph.setUnderline();
        }
        if (cellProp.isRemovelined()) {
            paragraph.setLineThrough();
        }
        PdfHandler.setTextAlignment((IPropertyContainer)paragraph, (String)cellProp.getTextAlignment());
        PdfHandler.setVerticalAlignment((IPropertyContainer)paragraph, (String)cellProp.getVerticalAlignment());
        PdfHandler.setHorizontalAlignment((IPropertyContainer)paragraph, (String)cellProp.getHorizontalAlignment());
        QueryPrintUtil.setCellFontWithoutSize(paragraph, cellProp.getFont());
        paragraph.setFontSize(fontSize);
        return paragraph;
    }

    public static float getStringWidth(String fontFamily, String str, float fontSize) {
        PdfFont font = QueryPrintUtil.changeToPDFFont(fontFamily);
        return font.getWidth(str, fontSize);
    }

    public static List<String> splitString(String fontFamily, String str, float fontSize, float maxWidth) {
        PdfFont font = QueryPrintUtil.changeToPDFFont(fontFamily);
        return QueryPrintUtil.splitString(font, str, fontSize, maxWidth);
    }

    public static List<String> splitString(PdfFont font, String str, float fontSize, float maxWidth) {
        return font.splitString(str, fontSize, maxWidth);
    }

    public static List<String> customSplitTxt(String fontFamily, String content, float fontSize, float maxWidth) {
        PdfFont font = QueryPrintUtil.changeToPDFFont(fontFamily);
        return QueryPrintUtil.customSplitTxt(font, content, fontSize, maxWidth);
    }

    public static List<String> customSplitTxt(PdfFont font, String content, float fontSize, float maxWidth) {
        ArrayList<String> splitList = new ArrayList<String>();
        int startPos = 0;
        float tokenLength = 0.0f;
        int length = content.length();
        for (int i = 0; i < length; ++i) {
            char ch = content.charAt(i);
            float currentCharWidth = font.getWidth((int)ch, fontSize);
            if (tokenLength + currentCharWidth >= maxWidth || ch == '\n') {
                if (startPos == i) {
                    splitList.add(content.substring(startPos, startPos + 1));
                    startPos = i + 1;
                    tokenLength = 0.0f;
                    continue;
                }
                splitList.add(content.substring(startPos, i));
                startPos = i;
                tokenLength = currentCharWidth;
                continue;
            }
            tokenLength += currentCharWidth;
        }
        splitList.add(content.substring(startPos));
        ArrayList<String> list = new ArrayList<String>();
        for (String str : splitList) {
            String replace = QueryPrintConst.LF_COMPILE.matcher(str).replaceAll("");
            String tempStr = QueryPrintConst.CR_PATTERN.matcher(replace).replaceAll("");
            if (!StringUtils.hasText(tempStr)) continue;
            list.add(tempStr);
        }
        return list;
    }

    public static float[] calculateAscenderDescender(String fontName) {
        float descender;
        float ascender;
        PdfFont font = QueryPrintUtil.changeToPDFFont(fontName);
        FontMetrics fontMetrics = font.getFontProgram().getFontMetrics();
        if (QueryPrintUtil.checkFontMetrics(fontMetrics)) {
            ascender = (float)fontMetrics.getTypoAscender() * 1.2f;
            descender = (float)fontMetrics.getTypoDescender() * 1.2f;
        } else {
            ascender = fontMetrics.getWinAscender();
            descender = fontMetrics.getWinDescender();
        }
        return new float[]{ascender / 1000.0f, descender / 1000.0f};
    }

    private static boolean checkFontMetrics(FontMetrics fontMetrics) {
        return fontMetrics.getWinAscender() == 0 || fontMetrics.getWinDescender() == 0 || fontMetrics.getTypoAscender() == fontMetrics.getWinAscender() && fontMetrics.getTypoDescender() == fontMetrics.getWinDescender();
    }

    public static float zoomFontSize(String fontName, float fontSize, float height) {
        float textRise;
        float nonBreakablePartMaxDescender;
        float[] floats = QueryPrintUtil.calculateAscenderDescender(fontName);
        float nonBreakablePartMaxAscender = floats[0];
        float textHeight = (nonBreakablePartMaxAscender - (nonBreakablePartMaxDescender = floats[1])) * fontSize + (textRise = 0.0f);
        if (Math.ceil(textHeight) >= (double)height) {
            double round = Math.floor(height / (nonBreakablePartMaxAscender - nonBreakablePartMaxDescender));
            double newValue = (double)(nonBreakablePartMaxAscender - nonBreakablePartMaxDescender) * round + (double)textRise;
            if (Math.ceil(newValue) >= (double)height) {
                round -= 1.0;
            }
            return (float)round;
        }
        return fontSize;
    }

    public static int colorValue(String color) {
        String[] split;
        String tempColor = color;
        if (Objects.nonNull(color) && color.length() > 7) {
            tempColor = "#000000";
            logger.debug("[\u5355\u636e\u6253\u5370]>>>\u989c\u8272\uff1a{} \u4e0d\u6b63\u786e\u8bf7\u68c0\u67e5", (Object)color);
        }
        if (Objects.isNull(tempColor)) {
            tempColor = "#000000";
        }
        tempColor = (split = tempColor.split("#")).length > 1 ? split[split.length - 1] : split[0];
        return Integer.valueOf(tempColor, 16);
    }

    public static Border getBorder(String borderName, String color) {
        String tempColor = color;
        if (!StringUtils.hasText(tempColor) && !StringUtils.hasText(borderName)) {
            DeviceRgb deviceRgb = new DeviceRgb(new Color(QueryPrintUtil.colorValue(DEFAULT_COLOR)));
            return new SolidBorder((com.itextpdf.kernel.colors.Color)deviceRgb, 0.0f, 0.0f);
        }
        if (!StringUtils.hasText(tempColor)) {
            tempColor = DEFAULT_COLOR;
        }
        int colorValue = QueryPrintUtil.colorValue(tempColor);
        DeviceRgb deviceRgb = new DeviceRgb(new Color(colorValue));
        SolidBorder border = new SolidBorder((com.itextpdf.kernel.colors.Color)deviceRgb, 0.0f, 0.0f);
        if (!BorderTypeFullEnum.NONE.getName().equals(borderName)) {
            if (BorderTypeFullEnum.THIN.getName().equals(borderName)) {
                border = new SolidBorder((float)BorderTypeFullEnum.THIN.getWidth());
            } else if (BorderTypeFullEnum.DASHED.getName().equals(borderName)) {
                border = new DashedBorder((float)BorderTypeFullEnum.DASHED.getWidth());
            } else if (BorderTypeFullEnum.DOTTED.getName().equals(borderName)) {
                border = new DashedBorder((float)BorderTypeFullEnum.DOTTED.getWidth());
            } else if (BorderTypeFullEnum.DOUBLE.getName().equals(borderName)) {
                border = new DashedBorder((float)BorderTypeFullEnum.DOUBLE.getWidth());
            }
        }
        if (!StringUtils.hasText(tempColor) || DEFAULT_COLOR.equals(tempColor)) {
            border = Border.NO_BORDER;
        } else {
            border.setColor((com.itextpdf.kernel.colors.Color)deviceRgb);
        }
        return border;
    }

    public static float getFontHeight(PdfFont font, float fontSize) {
        FontMetrics fontMetrics = font.getFontProgram().getFontMetrics();
        float ascent = (float)fontMetrics.getTypoAscender() * fontSize / (float)fontMetrics.getUnitsPerEm();
        float descent = (float)fontMetrics.getTypoDescender() * fontSize / (float)fontMetrics.getUnitsPerEm();
        float leading = fontMetrics.getTypoAscender() - fontMetrics.getTypoDescender() + fontMetrics.getLineGap();
        return ascent - descent + (leading *= fontSize / (float)fontMetrics.getUnitsPerEm());
    }

    public static <T> Map<String, T> getMap(Object object) {
        if (object instanceof Map) {
            return (Map)object;
        }
        return Collections.emptyMap();
    }

    public static <T> Map<Integer, T> getIntMap(Object object) {
        if (object instanceof Map) {
            return (Map)object;
        }
        return Collections.emptyMap();
    }

    public static <T> List<T> getList(Object object) {
        if (object instanceof List) {
            return (List)object;
        }
        return Collections.emptyList();
    }

    public static R dataMap(Object object) {
        HashMap<String, Object> map = new HashMap<String, Object>(16);
        map.put("data", object);
        return R.ok(map);
    }

    public static float convertUnit(float unit) {
        return (float)((double)(unit * 72.0f) / 25.4);
    }

    public static BorderDTO setBorder(Cell cell, GridCellProp cellProp) {
        String bottomBorderColor = cellProp.getBottomBorderColor();
        String leftBorderColor = cellProp.getLeftBorderColor();
        String topBorderColor = cellProp.getTopBorderColor();
        String rightBorderColor = cellProp.getRightBorderColor();
        String topBorderName = cellProp.getTopBorderStyle().getName();
        String leftBorderName = cellProp.getLeftBorderStyle().getName();
        String bottomBorderName = cellProp.getBottomBorderStyle().getName();
        String rightBorderName = cellProp.getRightBorderStyle().getName();
        Border topBorder = QueryPrintUtil.getBorder(topBorderName, topBorderColor);
        Border leftBorder = QueryPrintUtil.getBorder(leftBorderName, leftBorderColor);
        Border bottomBorder = QueryPrintUtil.getBorder(bottomBorderName, bottomBorderColor);
        Border rightBorder = QueryPrintUtil.getBorder(rightBorderName, rightBorderColor);
        cell.setBorderTop(topBorder);
        cell.setBorderRight(rightBorder);
        cell.setBorderBottom(bottomBorder);
        cell.setBorderLeft(leftBorder);
        return new BorderDTO(topBorder, leftBorder, bottomBorder, rightBorder);
    }

    public static CustomTable newTable(TablePrintControl tablePrintControl) {
        return QueryPrintUtil.newHiddenColTable(tablePrintControl, false);
    }

    public static CustomTable newHiddenColTable(TablePrintControl tablePrintControl, boolean showColFlag) {
        float paddingBottom = tablePrintControl.getPaddingBottom();
        float paddingTop = tablePrintControl.getPaddingTop();
        float paddingLeft = tablePrintControl.getPaddingLeft();
        float paddingRight = tablePrintControl.getPaddingRight();
        CustomTable pdfPTable = new CustomTable(tablePrintControl.getHiddenColWidthPercentValue(showColFlag));
        PdfHandler.setPercentWidth((IPropertyContainer)pdfPTable, (float)100.0f);
        PdfHandler.setTextAlignment((IPropertyContainer)pdfPTable, (String)PropNameConsts.TextAlignment.CENTER.name());
        PdfHandler.setHorizontalAlignment((IPropertyContainer)pdfPTable.setFixedLayout(), (String)PropNameConsts.HorizontalAlignEnum.CENTER.name());
        ((Table)pdfPTable.setMargins(paddingTop, paddingRight, paddingBottom, paddingLeft)).setBorder(tablePrintControl.getBorder());
        return pdfPTable;
    }
}

