/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.itextpdf.kernel.font.PdfFont
 *  com.itextpdf.kernel.font.PdfFontFactory
 *  com.itextpdf.kernel.geom.Rectangle
 *  com.itextpdf.kernel.pdf.PdfDocument
 *  com.itextpdf.kernel.pdf.PdfPage
 *  com.itextpdf.kernel.pdf.PdfReader
 *  com.itextpdf.kernel.pdf.PdfWriter
 *  com.itextpdf.kernel.pdf.canvas.PdfCanvas
 *  com.itextpdf.layout.Canvas
 *  com.itextpdf.layout.element.ILeafElement
 *  com.itextpdf.layout.element.Paragraph
 *  com.itextpdf.layout.element.Text
 *  com.itextpdf.layout.properties.TextAlignment
 *  com.itextpdf.layout.properties.VerticalAlignment
 *  io.netty.util.internal.StringUtil
 *  org.apache.commons.lang3.StringUtils
 *  org.jsoup.Jsoup
 *  org.jsoup.nodes.Document
 *  org.jsoup.nodes.Element
 *  org.jsoup.nodes.Node
 *  org.jsoup.nodes.TextNode
 *  org.jsoup.select.Elements
 */
package com.jiuqi.nr.analysisreport.utils;

import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.Canvas;
import com.itextpdf.layout.element.ILeafElement;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.VerticalAlignment;
import com.jiuqi.nr.analysisreport.helper.AnalysisReportLogHelper;
import com.jiuqi.nr.analysisreport.utils.OfficeUtil;
import com.jiuqi.nr.analysisreport.vo.print.ReportPrintSettingVO;
import io.netty.util.internal.StringUtil;
import java.io.OutputStream;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PdfSetHeaderFooterNoUtil {
    private static final Logger log = LoggerFactory.getLogger(PdfSetHeaderFooterNoUtil.class);

    public static void createPage(String path, OutputStream out, ReportPrintSettingVO reportPrintSettingVO, int recordPageBeforText) throws Exception {
        try (PdfReader reader = new PdfReader(path);
             PdfWriter writer = new PdfWriter(out);
             PdfDocument pdfDoc = new PdfDocument(reader, writer);){
            Map<String, String> noSettings = OfficeUtil.createPageNo(reportPrintSettingVO);
            Map<String, String> headerSettings = OfficeUtil.createDefaultHeadFoot(reportPrintSettingVO.getPageHeader(), true);
            Map<String, String> footerSettings = OfficeUtil.createDefaultHeadFoot(reportPrintSettingVO.getPageFooter(), false);
            int pagesNum = pdfDoc.getNumberOfPages();
            for (int i = 1; i <= pagesNum; ++i) {
                int currentPage = recordPageBeforText + i;
                PdfPage page = pdfDoc.getPage(i);
                PdfCanvas canvas = new PdfCanvas(page.newContentStreamBefore(), page.getResources(), pdfDoc);
                if (currentPage <= 0) continue;
                boolean isOdd = currentPage % 2 != 0;
                PdfHeaderFooterPageHelper helper = new PdfHeaderFooterPageHelper(noSettings, headerSettings, footerSettings, canvas, page, pdfDoc, currentPage, isOdd);
                helper.create();
            }
        }
        catch (Exception e) {
            log.error("\u5904\u7406PDF\u9875\u7709\u9875\u811a\u5f02\u5e38", e);
            throw e;
        }
    }

    static Paragraph addText(String content) throws Exception {
        PdfFont font = PdfFontFactory.createFont((String)"STSongStd-Light", (String)"UniGB-UCS2-H");
        return (Paragraph)((Paragraph)new Paragraph(content).setFont(font)).setFontSize(10.0f);
    }

    public static void setHeadAndFootText(String html, Canvas canvas, float yPosition) throws Exception {
        if (StringUtils.isEmpty((CharSequence)html)) {
            return;
        }
        PdfFont baseFont = PdfFontFactory.createFont((String)"STSongStd-Light", (String)"UniGB-UCS2-H");
        Document doc = Jsoup.parse((String)html);
        Elements paragraphs = doc.select("p");
        for (Element element : paragraphs) {
            Paragraph paragraph = new Paragraph();
            PdfSetHeaderFooterNoUtil.getParagraphElement(paragraph, element, baseFont);
            canvas.showTextAligned(paragraph, 300.0f, yPosition, TextAlignment.CENTER, VerticalAlignment.BOTTOM);
            yPosition -= 15.0f;
        }
    }

    private static void getParagraphElement(Paragraph paragraph, Element e, PdfFont baseFont) {
        for (Node child : e.childNodes()) {
            String text;
            if (child instanceof Element) {
                PdfSetHeaderFooterNoUtil.getParagraphElement(paragraph, (Element)child, baseFont);
                continue;
            }
            if (!(child instanceof TextNode) || StringUtil.isNullOrEmpty((String)(text = ((TextNode)child).text()))) continue;
            text = text.replace("&nbsp;", " ");
            PdfSetHeaderFooterNoUtil.setWordStyle(paragraph, child, text, baseFont);
        }
    }

    private static void setWordStyle(Paragraph paragraph, Node child, String text, PdfFont baseFont) {
        try {
            Text textElement = (Text)new Text(text).setFont(baseFont);
            WordStyle style = new WordStyle(child);
            if (style.isBold()) {
                textElement.setBold();
            }
            if (style.isItalic()) {
                textElement.setItalic();
            }
            if (style.hasUnderline()) {
                textElement.setUnderline();
            }
            if (child.parentNode() instanceof Element) {
                String tagName = ((Element)child.parentNode()).tagName();
                if ("sup".equals(tagName)) {
                    ((Text)textElement.setFontSize(5.0f)).setTextRise(5.0f);
                } else if ("sub".equals(tagName)) {
                    ((Text)textElement.setFontSize(5.0f)).setTextRise(-4.0f);
                }
            }
            paragraph.add((ILeafElement)textElement);
        }
        catch (Exception e) {
            AnalysisReportLogHelper.log("\u5206\u6790\u62a5\u544a", "\u5206\u6790\u62a5\u544a\u6bb5\u843d\u6837\u5f0f\u83b7\u53d6\u5f02\u5e38: " + child.outerHtml(), e);
        }
    }

    static class WordStyle {
        private final boolean bold;
        private final boolean italic;
        private final boolean underline;

        public WordStyle(Node child) {
            Element element = child instanceof Element ? (Element)child : null;
            this.bold = element != null && (element.tagName().equals("b") || element.tagName().equals("strong") || element.hasAttr("style") && element.attr("style").contains("font-weight:bold"));
            this.italic = element != null && (element.tagName().equals("i") || element.tagName().equals("em") || element.hasAttr("style") && element.attr("style").contains("font-style:italic"));
            this.underline = element != null && (element.tagName().equals("u") || element.hasAttr("style") && element.attr("style").contains("text-decoration:underline"));
        }

        public boolean isBold() {
            return this.bold;
        }

        public boolean isItalic() {
            return this.italic;
        }

        public boolean hasUnderline() {
            return this.underline;
        }
    }

    static class PdfHeaderFooterPageHelper {
        private final Map<String, String> noSettings;
        private final Map<String, String> headerSettings;
        private final Map<String, String> footerSettings;
        private final Canvas canvas;
        private final int currentPage;
        private final boolean isOdd;
        private final Rectangle pageSize;

        public PdfHeaderFooterPageHelper(Map<String, String> noSettings, Map<String, String> headerSettings, Map<String, String> footerSettings, PdfCanvas pdfCanvas, PdfPage page, PdfDocument pdfDoc, int currentPage, boolean isOdd) {
            this.noSettings = noSettings;
            this.headerSettings = headerSettings;
            this.footerSettings = footerSettings;
            this.currentPage = currentPage;
            this.isOdd = isOdd;
            this.pageSize = page.getPageSize();
            this.canvas = new Canvas(pdfCanvas, (Rectangle)pdfDoc.getDefaultPageSize());
        }

        public void create() {
            this.createHeader();
            this.createFooter();
            this.createPageNumber();
        }

        private void createHeader() {
            String headerText = this.getContentByCondition(this.headerSettings, "showHead", "showDiffHead", "headerText", "headerOddText", "headerEvenText");
            if (headerText != null) {
                try {
                    PdfSetHeaderFooterNoUtil.setHeadAndFootText(headerText, this.canvas, 820.0f);
                }
                catch (Exception e) {
                    log.error("\u8bbe\u7f6e\u9875\u7709\u5f02\u5e38", e);
                }
            }
        }

        private void createFooter() {
            String footerText = this.getContentByCondition(this.footerSettings, "showFoot", "showDiffFoot", "footerText", "footerOddText", "footerEvenText");
            if (footerText != null) {
                try {
                    PdfSetHeaderFooterNoUtil.setHeadAndFootText(footerText, this.canvas, 35.0f);
                }
                catch (Exception e) {
                    log.error("\u8bbe\u7f6e\u9875\u811a\u5f02\u5e38", e);
                }
            }
        }

        private void createPageNumber() {
            String positionType;
            boolean showNumber;
            boolean showDiff = "true".equals(this.noSettings.get("showDiff"));
            boolean bl = showDiff ? (this.isOdd ? "true".equals(this.noSettings.get("oddshowNumber")) : "true".equals(this.noSettings.get("evenshowNumber"))) : (showNumber = "true".equals(this.noSettings.get("showNumber")));
            if (!showNumber) {
                return;
            }
            String string = showDiff ? (this.isOdd ? this.noSettings.get("oddEnumType") : this.noSettings.get("evenEnumType")) : (positionType = this.noSettings.get("enumType"));
            boolean isFooter = showDiff ? (this.isOdd ? "true".equals(this.noSettings.get("isoddfoot")) : "true".equals(this.noSettings.get("isevenfoot"))) : "true".equals(this.noSettings.get("istotalfoot"));
            float yPosition = isFooter ? 16.0f : 808.0f;
            try {
                Paragraph paragraph = PdfSetHeaderFooterNoUtil.addText(String.format("\u7b2c%d\u9875", this.currentPage));
                switch (positionType) {
                    case "left": {
                        this.canvas.showTextAligned(paragraph, 20.0f, yPosition, TextAlignment.LEFT, VerticalAlignment.BOTTOM);
                        break;
                    }
                    case "center": {
                        this.canvas.showTextAligned(paragraph, this.pageSize.getWidth() / 2.0f, yPosition, TextAlignment.CENTER, VerticalAlignment.BOTTOM);
                        break;
                    }
                    case "right": {
                        this.canvas.showTextAligned(paragraph, this.pageSize.getWidth() - 20.0f, yPosition, TextAlignment.RIGHT, VerticalAlignment.BOTTOM);
                    }
                }
            }
            catch (Exception e) {
                log.error("\u8bbe\u7f6e\u9875\u7801\u5f02\u5e38", e);
            }
        }

        private String getContentByCondition(Map<String, String> settings, String showKey, String diffKey, String commonContentKey, String oddContentKey, String evenContentKey) {
            if (!"true".equals(settings.get(showKey))) {
                return null;
            }
            if ("true".equals(settings.get(diffKey))) {
                return this.isOdd ? settings.get(oddContentKey) : settings.get(evenContentKey);
            }
            return settings.get(commonContentKey);
        }
    }
}

