/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.spire.pdf.PdfDocument
 *  com.spire.pdf.PdfPageBase
 *  com.spire.pdf.graphics.PdfBrush
 *  com.spire.pdf.graphics.PdfFontBase
 *  com.spire.pdf.graphics.PdfRGBColor
 *  com.spire.pdf.graphics.PdfSolidBrush
 *  com.spire.pdf.graphics.PdfStringFormat
 *  com.spire.pdf.graphics.PdfTextAlignment
 *  com.spire.pdf.graphics.PdfTilingBrush
 *  com.spire.pdf.graphics.PdfTrueTypeFont
 *  org.apache.poi.ooxml.POIXMLDocumentPart
 *  org.apache.poi.ooxml.POIXMLRelation
 *  org.apache.poi.xssf.usermodel.XSSFRelation
 *  org.apache.poi.xssf.usermodel.XSSFSheet
 *  org.apache.poi.xssf.usermodel.XSSFWorkbook
 */
package com.jiuqi.bi.office.excel.watermark;

import com.jiuqi.bi.office.excel.spire.SpireHelper;
import com.jiuqi.bi.office.excel.watermark.Watermark;
import com.jiuqi.bi.util.Rect;
import com.jiuqi.bi.util.StringUtils;
import com.spire.pdf.PdfDocument;
import com.spire.pdf.PdfPageBase;
import com.spire.pdf.graphics.PdfBrush;
import com.spire.pdf.graphics.PdfFontBase;
import com.spire.pdf.graphics.PdfRGBColor;
import com.spire.pdf.graphics.PdfSolidBrush;
import com.spire.pdf.graphics.PdfStringFormat;
import com.spire.pdf.graphics.PdfTextAlignment;
import com.spire.pdf.graphics.PdfTilingBrush;
import com.spire.pdf.graphics.PdfTrueTypeFont;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Dimension2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.imageio.ImageIO;
import org.apache.poi.ooxml.POIXMLDocumentPart;
import org.apache.poi.ooxml.POIXMLRelation;
import org.apache.poi.xssf.usermodel.XSSFRelation;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class WatermarkHelper {
    @Deprecated
    public static void setWatermarkForExcel(Watermark watermark, InputStream inputStream, OutputStream outputStream) throws IOException {
        try (XSSFWorkbook workbook = new XSSFWorkbook(inputStream);){
            WatermarkHelper.setWatermarkForExcel(watermark, workbook);
            workbook.write(outputStream);
        }
    }

    public static void setWatermarkForExcel(Watermark watermark, XSSFWorkbook xssfWorkbook) throws IOException {
        if (StringUtils.isEmpty(watermark.getContent())) {
            return;
        }
        int pictureIdx = WatermarkHelper.newPicture(watermark, xssfWorkbook);
        for (int i = 0; i < xssfWorkbook.getNumberOfSheets(); ++i) {
            XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(i);
            WatermarkHelper.add2Sheet(xssfWorkbook, xssfSheet, pictureIdx);
        }
    }

    public static void add2Sheet(XSSFWorkbook xssfWorkbook, XSSFSheet xssfSheet, int pictureIdx) {
        String relationId = xssfSheet.addRelation(null, (POIXMLRelation)XSSFRelation.IMAGES, (POIXMLDocumentPart)xssfWorkbook.getAllPictures().get(pictureIdx)).getRelationship().getId();
        xssfSheet.getCTWorksheet().addNewPicture().setId(relationId);
    }

    public static int newPicture(Watermark watermark, XSSFWorkbook xssfWorkbook) throws IOException {
        try (ByteArrayOutputStream os = new ByteArrayOutputStream();){
            ImageIO.write((RenderedImage)WatermarkHelper.drawText(watermark), "png", os);
            int n = xssfWorkbook.addPicture(os.toByteArray(), 6);
            return n;
        }
    }

    private static BufferedImage drawText(Watermark watermark) {
        Font font = new Font(watermark.getFontFamily(), 1, (int)Math.ceil(4.0 * (double)watermark.getSize() / 3.0));
        Color color = new Color(Integer.parseInt(watermark.getColor().substring(1, 3), 16), Integer.parseInt(watermark.getColor().substring(3, 5), 16), Integer.parseInt(watermark.getColor().substring(5, 7), 16), watermark.getTransparency() * 255 / 10);
        Color backColor = new Color(Integer.parseInt(watermark.getBgColor().substring(1, 7), 16));
        String[] multiLineContent = watermark.getMultiLineContent();
        Rect drawPanelRect = watermark.getDrawPanelRect();
        BufferedImage img = new BufferedImage(drawPanelRect.right, drawPanelRect.bottom, 2);
        Graphics2D loGraphic = img.createGraphics();
        loGraphic.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        FontMetrics loFontMetrics = loGraphic.getFontMetrics(font);
        int liStrHeight = loFontMetrics.getHeight();
        loGraphic.setColor(backColor);
        loGraphic.fillRect(0, 0, drawPanelRect.right, drawPanelRect.bottom);
        loGraphic.translate(0, 0);
        loGraphic.rotate(Math.toRadians(-15.0));
        loGraphic.translate(0, 0);
        loGraphic.setFont(font);
        loGraphic.setColor(color);
        for (int i = 0; i < multiLineContent.length; ++i) {
            loGraphic.drawString(multiLineContent[i], 0.0f, (float)(drawPanelRect.bottom - (multiLineContent.length - i) * (liStrHeight + 3)));
        }
        loGraphic.dispose();
        return img;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void setWatermarkForPDF(Watermark watermark, InputStream pdf_is, OutputStream pdf_new_os) {
        SpireHelper.loadSpireLicence();
        Font font = new Font(watermark.getFontFamily(), 1, watermark.getSize());
        PdfTrueTypeFont trueTypeFont = new PdfTrueTypeFont(font);
        try {
            PdfDocument pdf = new PdfDocument();
            pdf.loadFromStream(pdf_is);
            if (StringUtils.isNotEmpty(watermark.getContent())) {
                for (Object page : pdf.getPages()) {
                    PdfPageBase pageBase = (PdfPageBase)page;
                    WatermarkHelper.insertWatermark(pageBase, watermark, trueTypeFont);
                }
            }
            pdf.saveToStream(pdf_new_os);
        }
        finally {
            trueTypeFont.dispose();
        }
    }

    private static void insertWatermark(PdfPageBase page, Watermark watermark, PdfTrueTypeFont trueTypeFont) {
        Dimension dimension2D = new Dimension();
        Rect rect = watermark.getOriginRect();
        int textWidth = rect.right;
        int textHeight = rect.bottom + 50;
        int panelWidth = (int)page.getCanvas().getClientSize().getWidth();
        int panelHeight = (int)page.getCanvas().getClientSize().getHeight();
        int numOfCol = panelWidth / textWidth;
        int compensateNum = 10;
        if (numOfCol > 1) {
            panelWidth /= panelWidth / textWidth;
        } else {
            compensateNum = (panelWidth - textWidth) / 2 + 50;
            if (compensateNum < 0) {
                compensateNum = 0;
            }
        }
        if (panelHeight / textHeight > 1) {
            panelHeight /= panelHeight / textHeight;
        }
        ((Dimension2D)dimension2D).setSize(panelWidth, panelHeight);
        PdfTilingBrush brush = new PdfTilingBrush((Dimension2D)dimension2D);
        brush.getGraphics().setTransparency((float)watermark.getTransparency() / 10.0f);
        brush.getGraphics().save();
        brush.getGraphics().translateTransform((double)compensateNum, (double)(panelHeight / 2));
        brush.getGraphics().rotateTransform(-15.0f);
        Color color = new Color(Integer.parseInt(watermark.getColor().substring(1, 3), 16), Integer.parseInt(watermark.getColor().substring(3, 5), 16), Integer.parseInt(watermark.getColor().substring(5, 7), 16));
        brush.getGraphics().drawString(watermark.getContent().replaceAll("<br>", "\n"), (PdfFontBase)trueTypeFont, (PdfBrush)new PdfSolidBrush(new PdfRGBColor(color)), 0.0f, 0.0f, new PdfStringFormat(PdfTextAlignment.Left));
        brush.getGraphics().restore();
        brush.getGraphics().setTransparency(1.0f);
        Rectangle2D.Float loRect = new Rectangle2D.Float();
        loRect.setFrame(new Point2D.Float(0.0f, 0.0f), page.getCanvas().getClientSize());
        page.getCanvas().drawRectangle((PdfBrush)brush, (Rectangle2D)loRect);
    }
}

