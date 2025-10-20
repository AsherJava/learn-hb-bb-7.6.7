/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.netty.util.internal.StringUtil
 *  org.apache.poi.xwpf.usermodel.ParagraphAlignment
 *  org.apache.poi.xwpf.usermodel.VerticalAlign
 *  org.apache.poi.xwpf.usermodel.XWPFFooter
 *  org.apache.poi.xwpf.usermodel.XWPFHeader
 *  org.apache.poi.xwpf.usermodel.XWPFParagraph
 *  org.apache.poi.xwpf.usermodel.XWPFRun
 *  org.jsoup.Jsoup
 *  org.jsoup.nodes.Document
 *  org.jsoup.nodes.Element
 *  org.jsoup.nodes.Node
 *  org.jsoup.nodes.TextNode
 *  org.jsoup.select.Elements
 *  org.openxmlformats.schemas.wordprocessingml.x2006.main.CTFldChar
 *  org.openxmlformats.schemas.wordprocessingml.x2006.main.CTFonts
 *  org.openxmlformats.schemas.wordprocessingml.x2006.main.CTHdrFtr
 *  org.openxmlformats.schemas.wordprocessingml.x2006.main.CTJc
 *  org.openxmlformats.schemas.wordprocessingml.x2006.main.CTP
 *  org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPPr
 *  org.openxmlformats.schemas.wordprocessingml.x2006.main.CTParaRPr
 *  org.openxmlformats.schemas.wordprocessingml.x2006.main.CTR
 *  org.openxmlformats.schemas.wordprocessingml.x2006.main.CTRPr
 *  org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSdtBlock
 *  org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSdtContentBlock
 *  org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSdtDocPart
 *  org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSdtPr
 *  org.openxmlformats.schemas.wordprocessingml.x2006.main.CTString
 *  org.openxmlformats.schemas.wordprocessingml.x2006.main.CTText
 *  org.openxmlformats.schemas.wordprocessingml.x2006.main.STFldCharType$Enum
 *  org.openxmlformats.schemas.wordprocessingml.x2006.main.STJc$Enum
 */
package com.jiuqi.nr.analysisreport.utils;

import com.jiuqi.nr.analysisreport.helper.AnalysisReportLogHelper;
import com.jiuqi.nr.analysisreport.utils.CustomXWPFHeaderFooterPolicy;
import com.jiuqi.nr.analysisreport.utils.WordStyle;
import io.netty.util.internal.StringUtil;
import java.math.BigInteger;
import java.util.Map;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.VerticalAlign;
import org.apache.poi.xwpf.usermodel.XWPFFooter;
import org.apache.poi.xwpf.usermodel.XWPFHeader;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTFldChar;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTFonts;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTHdrFtr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTJc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTP;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTParaRPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTR;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTRPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSdtBlock;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSdtContentBlock;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSdtDocPart;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSdtPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTString;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTText;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STFldCharType;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STJc;

class HeadFootPolicyExt {
    public static final String FONTFAMILY = "\u5b8b\u4f53";
    public static final int FONTSIZE = 10;
    protected Boolean showHead = null;
    protected Boolean showFoot = null;
    protected Boolean showDiffHead = null;
    protected Boolean showDiffFoot = null;
    protected String originNumber = "1";
    protected Boolean showDiffNo = null;
    protected Boolean istotalfoot = null;
    protected Boolean isevenfoot = null;
    protected Boolean isoddfoot = null;
    protected Boolean showNumber = null;
    protected Boolean showEvenNumber = null;
    protected Boolean showOddNumber = null;
    protected STJc.Enum enumType = null;
    protected STJc.Enum evenEnumType = null;
    protected STJc.Enum oddEnumType = null;
    protected String headerText = null;
    protected String headerOddText = null;
    protected String headerEvenText = null;
    protected String footerText = null;
    protected String footerOddText = null;
    protected String footerEvenText = null;
    protected CustomXWPFHeaderFooterPolicy headerFooterPolicy = null;

    public HeadFootPolicyExt(CustomXWPFHeaderFooterPolicy headerFooterPolicy, Map<String, String> headerSettings, Map<String, String> footerSettings, Map<String, String> noSettings) {
        this.showHead = Boolean.parseBoolean(headerSettings.get("showHead"));
        this.showFoot = Boolean.parseBoolean(footerSettings.get("showFoot"));
        this.showDiffHead = Boolean.parseBoolean(headerSettings.get("showDiffHead"));
        this.showDiffFoot = Boolean.parseBoolean(footerSettings.get("showDiffFoot"));
        this.originNumber = noSettings.get("originNumber");
        this.showDiffNo = Boolean.parseBoolean(noSettings.get("showDiff"));
        this.istotalfoot = Boolean.parseBoolean(noSettings.get("istotalfoot"));
        this.isevenfoot = Boolean.parseBoolean(noSettings.get("isevenfoot"));
        this.isoddfoot = Boolean.parseBoolean(noSettings.get("isoddfoot"));
        this.showNumber = Boolean.parseBoolean(noSettings.get("showNumber"));
        this.showEvenNumber = Boolean.parseBoolean(noSettings.get("evenshowNumber"));
        this.showOddNumber = Boolean.parseBoolean(noSettings.get("oddshowNumber"));
        this.enumType = STJc.Enum.forString((String)noSettings.get("enumType"));
        this.evenEnumType = STJc.Enum.forString((String)noSettings.get("evenEnumType"));
        this.oddEnumType = STJc.Enum.forString((String)noSettings.get("oddEnumType"));
        this.headerText = headerSettings.get("headerText");
        this.headerOddText = headerSettings.get("headerOddText");
        this.headerEvenText = headerSettings.get("headerEvenText");
        this.footerText = footerSettings.get("footerText");
        this.footerOddText = footerSettings.get("footerOddText");
        this.footerEvenText = footerSettings.get("footerEvenText");
        this.headerFooterPolicy = headerFooterPolicy;
    }

    public void addSdtFooter(XWPFFooter footer, STJc.Enum enumType) {
        CTHdrFtr ftr = footer._getHdrFtr();
        CTSdtBlock sdt = ftr.getSdtList().size() > 0 && ftr.getSdtArray(0) != null ? ftr.getSdtArray(0) : ftr.addNewSdt();
        CTSdtPr sdtPr = sdt.isSetSdtPr() ? sdt.getSdtPr() : sdt.addNewSdtPr();
        CTSdtDocPart sdtDP = sdtPr.getDocPartObj() != null ? sdtPr.getDocPartObj() : sdtPr.addNewDocPartObj();
        CTString str = sdtDP.isSetDocPartGallery() ? sdtDP.getDocPartGallery() : sdtDP.addNewDocPartGallery();
        str.setVal("Page Numbers (Bottom of Page)");
        if (!sdtDP.isSetDocPartUnique()) {
            sdtDP.addNewDocPartUnique();
        }
        CTSdtContentBlock sdtCB = sdt.isSetSdtContent() ? sdt.getSdtContent() : sdt.addNewSdtContent();
        CTP p = sdtCB.getPList().size() > 0 && sdtCB.getPArray(0) != null ? sdtCB.getPArray(0) : sdtCB.addNewP();
        CTPPr ppr = p.isSetPPr() ? p.getPPr() : p.addNewPPr();
        CTJc jc = ppr.isSetJc() ? ppr.getJc() : ppr.addNewJc();
        jc.setVal(enumType);
        CTParaRPr pRpr = ppr.isSetRPr() ? ppr.getRPr() : ppr.addNewRPr();
        CTFonts font = pRpr.addNewRFonts();
        font.setAscii(FONTFAMILY);
        font.setEastAsia(FONTFAMILY);
        font.setHAnsi(FONTFAMILY);
        pRpr.addNewSz().setVal((Object)new BigInteger("20"));
        pRpr.addNewSzCs().setVal((Object)new BigInteger("20"));
        CTR run = p.addNewR();
        CTRPr rpr = run.isSetRPr() ? run.getRPr() : run.addNewRPr();
        font = pRpr.addNewRFonts();
        font.setAscii(FONTFAMILY);
        font.setEastAsia(FONTFAMILY);
        font.setHAnsi(FONTFAMILY);
        pRpr.addNewSz().setVal((Object)new BigInteger("20"));
        pRpr.addNewSzCs().setVal((Object)new BigInteger("20"));
        CTFldChar fldChar = run.addNewFldChar();
        fldChar.setFldCharType(STFldCharType.Enum.forString((String)"begin"));
        run = p.addNewR();
        rpr = run.isSetRPr() ? run.getRPr() : run.addNewRPr();
        font = pRpr.addNewRFonts();
        font.setAscii(FONTFAMILY);
        font.setEastAsia(FONTFAMILY);
        font.setHAnsi(FONTFAMILY);
        pRpr.addNewSz().setVal((Object)new BigInteger("20"));
        pRpr.addNewSzCs().setVal((Object)new BigInteger("20"));
        CTText ctText = run.addNewInstrText();
        ctText.setStringValue("PAGE   \\* MERGEFORMAT");
        this.formatHeadFootRun(run, 20, FONTFAMILY);
        run = p.addNewR();
        rpr = run.isSetRPr() ? run.getRPr() : run.addNewRPr();
        font = pRpr.addNewRFonts();
        font.setAscii(FONTFAMILY);
        font.setEastAsia(FONTFAMILY);
        font.setHAnsi(FONTFAMILY);
        pRpr.addNewSz().setVal((Object)new BigInteger("20"));
        pRpr.addNewSzCs().setVal((Object)new BigInteger("20"));
        fldChar = run.addNewFldChar();
        fldChar.setFldCharType(STFldCharType.Enum.forString((String)"separate"));
        run = p.addNewR();
        rpr = run.isSetRPr() ? run.getRPr() : run.addNewRPr();
        font = pRpr.addNewRFonts();
        font.setAscii(FONTFAMILY);
        font.setEastAsia(FONTFAMILY);
        font.setHAnsi(FONTFAMILY);
        pRpr.addNewSz().setVal((Object)new BigInteger("20"));
        pRpr.addNewSzCs().setVal((Object)new BigInteger("20"));
        rpr.addNewLang().setVal("zh-CN");
        run.addNewT().setStringValue("2");
        run = p.addNewR();
        rpr = run.isSetRPr() ? run.getRPr() : run.addNewRPr();
        font = pRpr.addNewRFonts();
        font.setAscii(FONTFAMILY);
        font.setEastAsia(FONTFAMILY);
        font.setHAnsi(FONTFAMILY);
        pRpr.addNewSz().setVal((Object)new BigInteger("20"));
        pRpr.addNewSzCs().setVal((Object)new BigInteger("20"));
        fldChar = run.addNewFldChar();
        fldChar.setFldCharType(STFldCharType.Enum.forString((String)"end"));
    }

    public void addSdtHeader(XWPFHeader header, STJc.Enum enumType) {
        CTHdrFtr ftr = header._getHdrFtr();
        CTSdtBlock sdt = ftr.getSdtList().size() > 0 && ftr.getSdtArray(0) != null ? ftr.getSdtArray(0) : ftr.addNewSdt();
        CTSdtPr sdtPr = sdt.isSetSdtPr() ? sdt.getSdtPr() : sdt.addNewSdtPr();
        CTSdtDocPart sdtDP = sdtPr.getDocPartObj() != null ? sdtPr.getDocPartObj() : sdtPr.addNewDocPartObj();
        CTString str = sdtDP.isSetDocPartGallery() ? sdtDP.getDocPartGallery() : sdtDP.addNewDocPartGallery();
        str.setVal("Page Numbers (Bottom of Page)");
        if (!sdtDP.isSetDocPartUnique()) {
            sdtDP.addNewDocPartUnique();
        }
        CTSdtContentBlock sdtCB = sdt.isSetSdtContent() ? sdt.getSdtContent() : sdt.addNewSdtContent();
        CTP p = sdtCB.getPList().size() > 0 && sdtCB.getPArray(0) != null ? sdtCB.getPArray(0) : sdtCB.addNewP();
        CTPPr ppr = p.isSetPPr() ? p.getPPr() : p.addNewPPr();
        CTJc jc = ppr.isSetJc() ? ppr.getJc() : ppr.addNewJc();
        jc.setVal(enumType);
        CTParaRPr pRpr = ppr.isSetRPr() ? ppr.getRPr() : ppr.addNewRPr();
        CTFonts font = pRpr.addNewRFonts();
        font.setAscii(FONTFAMILY);
        font.setEastAsia(FONTFAMILY);
        font.setHAnsi(FONTFAMILY);
        pRpr.addNewSz().setVal((Object)new BigInteger("20"));
        pRpr.addNewSzCs().setVal((Object)new BigInteger("20"));
        CTR run = p.addNewR();
        CTRPr rpr = run.isSetRPr() ? run.getRPr() : run.addNewRPr();
        font = pRpr.addNewRFonts();
        font.setAscii(FONTFAMILY);
        font.setEastAsia(FONTFAMILY);
        font.setHAnsi(FONTFAMILY);
        pRpr.addNewSz().setVal((Object)new BigInteger("20"));
        pRpr.addNewSzCs().setVal((Object)new BigInteger("20"));
        CTFldChar fldChar = run.addNewFldChar();
        fldChar.setFldCharType(STFldCharType.Enum.forString((String)"begin"));
        run = p.addNewR();
        rpr = run.isSetRPr() ? run.getRPr() : run.addNewRPr();
        font = pRpr.addNewRFonts();
        font.setAscii(FONTFAMILY);
        font.setEastAsia(FONTFAMILY);
        font.setHAnsi(FONTFAMILY);
        pRpr.addNewSz().setVal((Object)new BigInteger("20"));
        pRpr.addNewSzCs().setVal((Object)new BigInteger("20"));
        CTText ctText = run.addNewInstrText();
        ctText.setStringValue("PAGE   \\* MERGEFORMAT");
        this.formatHeadFootRun(run, 20, FONTFAMILY);
        run = p.addNewR();
        rpr = run.isSetRPr() ? run.getRPr() : run.addNewRPr();
        font = pRpr.addNewRFonts();
        font.setAscii(FONTFAMILY);
        font.setEastAsia(FONTFAMILY);
        font.setHAnsi(FONTFAMILY);
        pRpr.addNewSz().setVal((Object)new BigInteger("20"));
        pRpr.addNewSzCs().setVal((Object)new BigInteger("20"));
        fldChar = run.addNewFldChar();
        fldChar.setFldCharType(STFldCharType.Enum.forString((String)"separate"));
        run = p.addNewR();
        rpr = run.isSetRPr() ? run.getRPr() : run.addNewRPr();
        font = pRpr.addNewRFonts();
        font.setAscii(FONTFAMILY);
        font.setEastAsia(FONTFAMILY);
        font.setHAnsi(FONTFAMILY);
        pRpr.addNewSz().setVal((Object)new BigInteger("20"));
        pRpr.addNewSzCs().setVal((Object)new BigInteger("20"));
        rpr.addNewLang().setVal("zh-CN");
        run.addNewT().setStringValue("2");
        run = p.addNewR();
        rpr = run.isSetRPr() ? run.getRPr() : run.addNewRPr();
        font = pRpr.addNewRFonts();
        font.setAscii(FONTFAMILY);
        font.setEastAsia(FONTFAMILY);
        font.setHAnsi(FONTFAMILY);
        pRpr.addNewSz().setVal((Object)new BigInteger("20"));
        pRpr.addNewSzCs().setVal((Object)new BigInteger("20"));
        fldChar = run.addNewFldChar();
        fldChar.setFldCharType(STFldCharType.Enum.forString((String)"end"));
    }

    public void addParagraphHeader(XWPFHeader header, STJc.Enum enumType, String text) {
        XWPFParagraph paragraph = header.getParagraphs().size() > 0 && header.getParagraphArray(0) != null ? header.getParagraphArray(0) : header.createParagraph();
        this.setHeadAndFootText(text, paragraph);
    }

    public void setHeadAndFootText(String html, XWPFParagraph wordParagraph) {
        try {
            Document doc = Jsoup.parse((String)html);
            Elements paragraphs = doc.select("p");
            wordParagraph.setAlignment(ParagraphAlignment.CENTER);
            boolean isFirstElement = true;
            for (Element element : paragraphs) {
                this.getParagraphElement(wordParagraph, element);
                if (!isFirstElement) {
                    wordParagraph.createRun().addBreak();
                }
                isFirstElement = false;
            }
        }
        catch (Exception e) {
            AnalysisReportLogHelper.log("\u5206\u6790\u62a5\u544a", "\u5206\u6790\u62a5\u544a\u8bbe\u7f6e\u9875\u7709\u9875\u811a\u5f02\u5e38", e);
        }
    }

    private void getParagraphElement(XWPFParagraph paragraph, Element e) {
        for (Node child : e.childNodes()) {
            if (child instanceof Element) {
                this.getParagraphElement(paragraph, (Element)child);
                continue;
            }
            if (!(child instanceof TextNode) || "".equals(((TextNode)child).text())) continue;
            String text = ((TextNode)child).text();
            if (!StringUtil.isNullOrEmpty((String)text)) {
                text = text.replace("&nbsp;", " ");
            }
            this.setWordStyle(paragraph, child, text);
        }
    }

    private void setWordStyle(XWPFParagraph paragraph, Node child, String text) {
        try {
            XWPFRun run = paragraph.createRun();
            run.setText(text);
            WordStyle wStyle = new WordStyle(child);
            wStyle.setWordStyle(run);
            if (child.parentNode() != null && child.parentNode() instanceof Element) {
                String tagName = ((Element)child.parentNode()).tagName();
                if ("sup".equals(tagName)) {
                    run.setSubscript(VerticalAlign.SUPERSCRIPT);
                } else if ("sub".equals(tagName)) {
                    run.setSubscript(VerticalAlign.SUBSCRIPT);
                }
            }
            this.formatHeadFootRun(run, 10, FONTFAMILY);
        }
        catch (Exception exce) {
            AnalysisReportLogHelper.log("\u5206\u6790\u62a5\u544a", "\u5206\u6790\u62a5\u544a\u6bb5\u843d\u6837\u5f0f\u83b7\u53d6\u5f02\u5e38: " + child.outerHtml(), exce);
        }
    }

    public void addParagraphFooter(XWPFFooter footer, STJc.Enum enumType, String text) {
        XWPFParagraph paragraph = footer.getParagraphs().size() > 0 && footer.getParagraphArray(0) != null ? footer.getParagraphArray(0) : footer.createParagraph();
        this.setHeadAndFootText(text, paragraph);
    }

    public void formatHeadFootRun(XWPFRun run, int fontSize, String fontFamily) {
        this.formatHeadFootRun(run.getCTR(), fontSize, fontFamily);
    }

    public void formatHeadFootRun(CTR run, int fontSize, String fontFamily) {
        CTRPr rpr = run.isSetRPr() ? run.getRPr() : run.addNewRPr();
        CTFonts fonts = rpr.getRFontsList().size() > 0 ? rpr.getRFontsArray()[0] : rpr.addNewRFonts();
        fonts.setAscii(fontFamily);
        fonts.setEastAsia(fontFamily);
        fonts.setHAnsi(fontFamily);
        rpr.addNewSz().setVal((Object)new BigInteger(fontSize * 2 + ""));
        rpr.addNewSzCs().setVal((Object)new BigInteger(fontSize * 2 + ""));
    }
}

