/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.exc.InvalidFormatException
 *  com.jiuqi.xlib.utils.CollectionUtils
 *  com.jiuqi.xlib.utils.io.ByteArrayOutputStream
 *  org.apache.poi.openxml4j.exceptions.InvalidFormatException
 *  org.apache.poi.util.LocaleUtil
 *  org.apache.poi.util.Units
 *  org.apache.poi.xwpf.usermodel.IBody
 *  org.apache.poi.xwpf.usermodel.ParagraphAlignment
 *  org.apache.poi.xwpf.usermodel.XWPFDocument
 *  org.apache.poi.xwpf.usermodel.XWPFParagraph
 *  org.apache.poi.xwpf.usermodel.XWPFRun
 *  org.apache.poi.xwpf.usermodel.XWPFStyles
 *  org.openxmlformats.schemas.wordprocessingml.x2006.main.CTBody
 *  org.openxmlformats.schemas.wordprocessingml.x2006.main.CTBookmark
 *  org.openxmlformats.schemas.wordprocessingml.x2006.main.CTDecimalNumber
 *  org.openxmlformats.schemas.wordprocessingml.x2006.main.CTDocDefaults
 *  org.openxmlformats.schemas.wordprocessingml.x2006.main.CTFonts
 *  org.openxmlformats.schemas.wordprocessingml.x2006.main.CTHpsMeasure
 *  org.openxmlformats.schemas.wordprocessingml.x2006.main.CTHyperlink
 *  org.openxmlformats.schemas.wordprocessingml.x2006.main.CTJc
 *  org.openxmlformats.schemas.wordprocessingml.x2006.main.CTLatentStyles
 *  org.openxmlformats.schemas.wordprocessingml.x2006.main.CTLsdException
 *  org.openxmlformats.schemas.wordprocessingml.x2006.main.CTOnOff
 *  org.openxmlformats.schemas.wordprocessingml.x2006.main.CTP
 *  org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPPr
 *  org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPPrGeneral
 *  org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPageNumber
 *  org.openxmlformats.schemas.wordprocessingml.x2006.main.CTR
 *  org.openxmlformats.schemas.wordprocessingml.x2006.main.CTRPr
 *  org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSdtBlock
 *  org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSdtContentBlock
 *  org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSdtDocPart
 *  org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSdtEndPr
 *  org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSdtPr
 *  org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSectPr
 *  org.openxmlformats.schemas.wordprocessingml.x2006.main.CTString
 *  org.openxmlformats.schemas.wordprocessingml.x2006.main.CTStyle
 *  org.openxmlformats.schemas.wordprocessingml.x2006.main.CTStyles
 *  org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTabStop
 *  org.openxmlformats.schemas.wordprocessingml.x2006.main.STFldCharType
 *  org.openxmlformats.schemas.wordprocessingml.x2006.main.STJc
 *  org.openxmlformats.schemas.wordprocessingml.x2006.main.STStyleType
 *  org.openxmlformats.schemas.wordprocessingml.x2006.main.STTabJc
 *  org.openxmlformats.schemas.wordprocessingml.x2006.main.STTabTlc
 *  org.openxmlformats.schemas.wordprocessingml.x2006.main.STTheme
 */
package com.jiuqi.nr.analysisreport.utils;

import com.jiuqi.nr.analysisreport.utils.CustomXWPFDocument;
import com.jiuqi.nr.analysisreport.utils.STOnOffEnum;
import com.jiuqi.xlib.utils.CollectionUtils;
import com.jiuqi.xlib.utils.io.ByteArrayOutputStream;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.imageio.ImageIO;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.util.LocaleUtil;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.IBody;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFStyles;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTBody;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTBookmark;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTDecimalNumber;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTDocDefaults;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTFonts;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTHpsMeasure;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTHyperlink;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTJc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTLatentStyles;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTLsdException;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTOnOff;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTP;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPPrGeneral;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPageNumber;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTR;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTRPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSdtBlock;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSdtContentBlock;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSdtDocPart;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSdtEndPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSdtPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSectPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTString;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTStyle;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTStyles;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTabStop;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STFldCharType;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STJc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STStyleType;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STTabJc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STTabTlc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STTheme;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class TableOfContents {
    private static final Logger log = LoggerFactory.getLogger(TableOfContents.class);
    private CustomXWPFDocument docxDocument = null;
    private CTSdtBlock block = null;
    private Integer tocIndex = null;
    private DOCStyles doc_styles = new DOCStyles();
    private String tabPos = "10066";
    private Boolean isPDF = false;
    static final String TOC = "TOC";
    static final String PAGEREF = "PAGEREF";
    static final String SPACE_H = "\\h";
    static final String SPACE_O = "\\o";
    static final String SPACE_U = "\\u";
    static final String SPACE_Z = "\\z";
    static final String SPACE = " ";
    static final int HISTORY = 1;
    public static final int PER_LINE = 100;
    public static final int PER_CHART = 100;
    public static final int PER_CM = 567;
    public static final int PER_POUND = 20;
    public static final int ONE_LINE = 240;

    public TableOfContents() {
    }

    public static TableOfContents CreateTableOfContents(CustomXWPFDocument docxDocument) {
        return new TableOfContents(docxDocument);
    }

    public static TableOfContents CreateTableOfContents(CustomXWPFDocument docxDocument, boolean isInit) {
        return new TableOfContents(docxDocument, isInit);
    }

    public static TableOfContents CreateTableOfContents(CustomXWPFDocument docxDocument, boolean isInit, boolean isPDF) {
        return new TableOfContents(docxDocument, isInit, isPDF);
    }

    private TableOfContents(CustomXWPFDocument docxDocument) {
        this.docxDocument = docxDocument;
        this.InitTOC();
    }

    private TableOfContents(CustomXWPFDocument docxDocument, boolean isInit) {
        this.docxDocument = docxDocument;
        if (isInit) {
            this.InitTOC();
        }
    }

    private TableOfContents(CustomXWPFDocument docxDocument, boolean isInit, boolean isPDF) {
        this.docxDocument = docxDocument;
        if (isPDF) {
            this.addPDFTocStyle();
        }
        if (isInit) {
            this.InitTOC();
        }
        this.isPDF = isPDF;
    }

    private void addPDFTocStyle() {
        this.addStyle(1, "heading 1");
        this.addStyle(2, "heading 2");
        this.addStyle(3, "heading 3");
        this.addStyle(4, "heading 4");
    }

    public void InitTOC() {
        try {
            TableOfContents.addCustomTOCStyle(this.docxDocument, "TOC1", 1);
            TableOfContents.addCustomTOCStyle(this.docxDocument, "TOC2", 2);
            TableOfContents.addCustomTOCStyle(this.docxDocument, "TOC3", 3);
            TableOfContents.addCustomTOCStyle(this.docxDocument, "TOC4", 4);
        }
        catch (IllegalAccessException | IllegalArgumentException | NoSuchFieldException | SecurityException e2) {
            log.error(e2.getMessage(), e2);
        }
        if (!this.isPDF.booleanValue()) {
            this.InitWordTOC();
        } else {
            this.InitPDFTOC();
        }
    }

    private void InitWordTOC() {
        this.block = this.docxDocument.getDocument().getBody().addNewSdt();
        CTSdtPr sdtPr = this.block.addNewSdtPr();
        CTDecimalNumber id = sdtPr.addNewId();
        try {
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            id.setVal(new BigInteger(String.valueOf(Math.round(random.nextDouble() * 1.0E9))));
        }
        catch (NoSuchAlgorithmException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException("\u83b7\u53d6\u968f\u673a\u6570\u5931\u8d25", e);
        }
        CTSdtDocPart docPart = sdtPr.addNewDocPartObj();
        docPart.addNewDocPartGallery().setVal("Table of Contents");
        docPart.addNewDocPartUnique();
        CTSdtEndPr sdtEndPr = this.block.addNewSdtEndPr();
        CTRPr rPr = sdtEndPr.addNewRPr();
        CTFonts fonts = rPr.addNewRFonts();
        fonts.setAscii("\u5b8b\u4f53");
        fonts.setEastAsia("\u5b8b\u4f53");
        fonts.setHAnsi("\u5b8b\u4f53");
        fonts.setCs("\u5b8b\u4f53");
        rPr.addNewB().setVal((Object)STOnOffEnum.OFF);
        rPr.addNewBCs().setVal((Object)STOnOffEnum.OFF);
        rPr.addNewColor().setVal((Object)"auto");
        rPr.addNewSz().setVal((Object)new BigInteger("32"));
        rPr.addNewSzCs().setVal((Object)new BigInteger("32"));
        CTSdtContentBlock content = this.block.addNewSdtContent();
        CTP p = content.addNewP();
        p.setRsidR("00EF7E24".getBytes(LocaleUtil.CHARSET_1252));
        p.setRsidRDefault("00EF7E24".getBytes(LocaleUtil.CHARSET_1252));
        p.addNewPPr().addNewPStyle().setVal("TOCHeading");
        p.addNewR().addNewT().setStringValue("\u76ee     \u5f55");
        CTPPr pr = p.getPPr();
        CTJc jc = pr.isSetJc() ? pr.getJc() : pr.addNewJc();
        jc.setVal(STJc.CENTER);
        CTRPr pRpr = p.getRArray(0).addNewRPr();
        fonts = pRpr.getRFontsList().size() > 0 ? pRpr.getRFontsArray()[0] : pRpr.addNewRFonts();
        fonts.setAscii("\u5b8b\u4f53");
        fonts.setEastAsia("\u5b8b\u4f53");
        fonts.setHAnsi("\u5b8b\u4f53");
        CTOnOff bold = pRpr.getBList().size() > 0 ? pRpr.getBArray()[0] : pRpr.addNewB();
        bold.setVal((Object)true);
        CTHpsMeasure sz = pRpr.getSzList().size() > 0 ? pRpr.getSzArray()[0] : pRpr.addNewSz();
        sz.setVal((Object)new BigInteger("32"));
        CTP imgCpt = content.addNewP();
        XWPFParagraph paragraphMeau = new XWPFParagraph(imgCpt, (IBody)this.docxDocument);
        XWPFRun runMeau = paragraphMeau.createRun();
        runMeau.addBreak();
        BufferedImage bufferedImage = new BufferedImage(100, 100, 1);
        Graphics paint = bufferedImage.getGraphics();
        paint.setColor(Color.BLACK);
        paint.fillRect(0, 0, 100, 199);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            ImageIO.write((RenderedImage)bufferedImage, "png", (OutputStream)out);
        }
        catch (IOException iOException) {
            // empty catch block
        }
        ByteArrayInputStream img = new ByteArrayInputStream(out.toByteArray());
        CTP pp1 = content.addNewP();
        XWPFParagraph imgpara = new XWPFParagraph(pp1, (IBody)this.docxDocument);
        imgpara.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun run = imgpara.createRun();
        try {
            run.addPicture((InputStream)img, XWPFDocument.PICTURE_TYPE_PNG, "", Units.toEMU((double)(Integer.valueOf(this.tabPos) / 20)), Units.toEMU((double)2.5));
        }
        catch (com.fasterxml.jackson.databind.exc.InvalidFormatException invalidFormatException) {
        }
        catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        catch (InvalidFormatException e) {
            log.error(e.getMessage(), e);
        }
        CTP tabCpt = content.addNewP();
        XWPFParagraph tabImgpara = new XWPFParagraph(tabCpt, (IBody)this.docxDocument);
    }

    private void InitPDFTOC() {
        CTP paragraph = this.docxDocument.getDocument().getBody().addNewP();
        paragraph.addNewPPr().addNewJc().setVal(STJc.CENTER);
        CTR ctRun = paragraph.addNewR();
        CTRPr rpr = ctRun.addNewRPr();
        CTFonts fonts = rpr.addNewRFonts();
        fonts.setAscii("\u5b8b\u4f53");
        fonts.setEastAsia("\u5b8b\u4f53");
        fonts.setHAnsi("\u5b8b\u4f53");
        fonts.setCs("\u5b8b\u4f53");
        rpr.addNewB().setVal((Object)STOnOffEnum.ON);
        rpr.addNewBCs().setVal((Object)STOnOffEnum.ON);
        rpr.addNewColor().setVal((Object)"auto");
        rpr.addNewSz().setVal((Object)new BigInteger("32"));
        rpr.addNewSzCs().setVal((Object)new BigInteger("32"));
        ctRun.addNewT().setStringValue("\u76ee     \u5f55");
        CTP imgCpt = this.docxDocument.getDocument().getBody().addNewP();
        XWPFParagraph paragraphMeau = new XWPFParagraph(imgCpt, (IBody)this.docxDocument);
        XWPFRun runMeau = paragraphMeau.createRun();
        runMeau.addBreak();
        BufferedImage bufferedImage = new BufferedImage(100, 100, 1);
        Graphics paint = bufferedImage.getGraphics();
        paint.setColor(Color.BLACK);
        paint.fillRect(0, 0, 100, 199);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            ImageIO.write((RenderedImage)bufferedImage, "png", (OutputStream)out);
        }
        catch (IOException iOException) {
            // empty catch block
        }
        ByteArrayInputStream img = new ByteArrayInputStream(out.toByteArray());
        CTP pp1 = this.docxDocument.getDocument().getBody().addNewP();
        XWPFParagraph imgpara = new XWPFParagraph(pp1, (IBody)this.docxDocument);
        imgpara.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun run = imgpara.createRun();
        try {
            run.addPicture((InputStream)img, XWPFDocument.PICTURE_TYPE_PNG, "", Units.toEMU((double)(Integer.valueOf(this.tabPos) / 20)), Units.toEMU((double)2.5));
        }
        catch (com.fasterxml.jackson.databind.exc.InvalidFormatException invalidFormatException) {
        }
        catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        catch (InvalidFormatException e) {
            log.error(e.getMessage(), e);
        }
        CTP tabCpt = this.docxDocument.getDocument().getBody().addNewP();
        XWPFParagraph tabImgpara = new XWPFParagraph(tabCpt, (IBody)this.docxDocument);
        this.tocIndex = this.docxDocument.getDocument().getBody().getPList().size();
    }

    public void addStyle(int level, String name) {
        this.doc_styles.add(new DOCStyle(level, name));
    }

    public Boolean hasStyle(String parStyle) {
        return this.doc_styles.contains(parStyle);
    }

    public int getStyleLevel(String id) {
        return this.doc_styles.getLevel(id);
    }

    public void CreateTOC(Map<String, Integer> pageNums) {
        if (!this.isPDF.booleanValue()) {
            this.CreateWordTOC(pageNums);
        } else {
            this.CreatePDFTOC(pageNums);
        }
    }

    public void createTOC(Map<String, Integer> pageNums, List<XWPFParagraph> paragraphs, String type) {
        if (CollectionUtils.isEmpty(paragraphs)) {
            paragraphs = this.docxDocument.getParagraphs();
        }
        boolean begin = false;
        for (XWPFParagraph par : paragraphs) {
            String parStyle = par.getStyle();
            if (parStyle == null || !this.doc_styles.contains(parStyle)) continue;
            List bookmarkList = par.getCTP().getBookmarkStartList();
            try {
                int level = this.doc_styles.getLevel(parStyle);
                String bookmarkId = "";
                for (CTBookmark bookmark : bookmarkList) {
                    if (!bookmark.getName().startsWith("_Toc")) continue;
                    bookmarkId = bookmark.getName();
                    break;
                }
                if ("".equals(par.getText().trim()) || "".equals(bookmarkId)) continue;
                if (type == null) {
                    type = "";
                }
                switch (type) {
                    case "word": {
                        CTSdtContentBlock contentBlock = this.block.getSdtContent();
                        if (!begin) {
                            begin = !begin;
                            this.Create_TOC_BEGIN(contentBlock, par.getText(), bookmarkId, level, pageNums);
                            break;
                        }
                        this.Create_TOC_HyperlinkUnit(contentBlock, par.getText(), bookmarkId, level, pageNums);
                        break;
                    }
                    case "pdf": {
                        TableOfContents tableOfContents = this;
                        Integer n = tableOfContents.tocIndex;
                        Integer n2 = tableOfContents.tocIndex = Integer.valueOf(tableOfContents.tocIndex + 1);
                        CTP paragraph = this.docxDocument.getDocument().getBody().insertNewP(n.intValue());
                        TableOfContents.createTocParagraph(paragraph, level, par.getText(), pageNums.get(bookmarkId));
                        break;
                    }
                    default: {
                        CTP newPar = this.docxDocument.getDocument().getBody().addNewP();
                        TableOfContents.createTocParagraph(newPar, level, par.getText(), pageNums.get(bookmarkId));
                    }
                }
            }
            catch (NumberFormatException e) {
                log.error(e.getMessage(), e);
            }
        }
        if ("word".equals(type)) {
            this.Create_TOC_END(this.block.getSdtContent());
        }
    }

    private void CreateWordTOC(Map<String, Integer> pageNums) {
        if (this.block == null) {
            return;
        }
        CTSdtContentBlock contentBlock = this.block.getSdtContent();
        List paragraphs = this.docxDocument.getParagraphs();
        boolean begin = false;
        for (XWPFParagraph par : paragraphs) {
            String parStyle = par.getStyle();
            if (parStyle == null || !this.doc_styles.contains(parStyle)) continue;
            List bookmarkList = par.getCTP().getBookmarkStartList();
            try {
                int level = this.doc_styles.getLevel(parStyle);
                String bookmarkId = "";
                for (CTBookmark bookmark : bookmarkList) {
                    if (!bookmark.getName().startsWith("_Toc")) continue;
                    bookmarkId = bookmark.getName();
                    break;
                }
                if ("".equals(par.getText().trim()) || "".equals(bookmarkId)) continue;
                if (!begin) {
                    begin = !begin;
                    this.Create_TOC_BEGIN(contentBlock, par.getText(), bookmarkId, level, pageNums);
                    continue;
                }
                this.Create_TOC_HyperlinkUnit(contentBlock, par.getText(), bookmarkId, level, pageNums);
            }
            catch (NumberFormatException e) {
                log.error(e.getMessage(), e);
            }
        }
        this.Create_TOC_END(contentBlock);
    }

    private void CreatePDFTOC(Map<String, Integer> pageNums) {
        List paragraphs = this.docxDocument.getParagraphs();
        for (XWPFParagraph par : paragraphs) {
            String parStyle = par.getStyle();
            if (parStyle == null || !this.doc_styles.contains(parStyle)) continue;
            List bookmarkList = par.getCTP().getBookmarkStartList();
            try {
                int level = this.doc_styles.getLevel(parStyle);
                String bookmarkId = "";
                for (CTBookmark bookmark : bookmarkList) {
                    if (!bookmark.getName().startsWith("_Toc")) continue;
                    bookmarkId = bookmark.getName();
                    break;
                }
                if ("".equals(par.getText().trim()) || "".equals(bookmarkId)) continue;
                TableOfContents tableOfContents = this;
                Integer n = tableOfContents.tocIndex;
                Integer n2 = tableOfContents.tocIndex = Integer.valueOf(tableOfContents.tocIndex + 1);
                CTP paragraph = this.docxDocument.getDocument().getBody().insertNewP(n.intValue());
                TableOfContents.createTocParagraph(paragraph, level, par.getText(), pageNums.get(bookmarkId));
            }
            catch (NumberFormatException e) {
                log.error(e.getMessage(), e);
            }
        }
    }

    private void Create_TOC_BEGIN(CTSdtContentBlock contentBlock, String text, String bookmarkId, int level, Map<String, Integer> pageNums) {
        CTP p = contentBlock.addNewP();
        this.Create_TOC_pStyle(p, level);
        p.addNewR().addNewFldChar().setFldCharType(STFldCharType.BEGIN);
        p.addNewR().addNewInstrText().setStringValue("TOC \\o \"1-4\" \\h \\z \\u");
        p.addNewR().addNewFldChar().setFldCharType(STFldCharType.SEPARATE);
        this.Create_TOC_Hyperlink(p, text, bookmarkId, level, pageNums);
    }

    private void Create_TOC_END(CTSdtContentBlock contentBlock) {
        CTP p = contentBlock.addNewP();
        p.addNewPPr().addNewJc().setVal(STJc.LEFT);
        p.addNewR().addNewFldChar().setFldCharType(STFldCharType.END);
    }

    private void Create_TOC_HyperlinkUnit(CTSdtContentBlock contentBlock, String text, String bookmarkId, int level, Map<String, Integer> pageNums) {
        CTP p = contentBlock.addNewP();
        this.Create_TOC_pStyle(p, level);
        this.Create_TOC_Hyperlink(p, text, bookmarkId, level, pageNums);
    }

    private void Create_TOC_pStyle(CTP p, int level) {
        CTPPr pPr = p.addNewPPr();
        pPr.addNewPStyle().setVal("200" + level);
        CTTabStop tab = pPr.addNewTabs().addNewTab();
        tab.setVal(STTabJc.RIGHT);
        tab.setLeader(STTabTlc.DOT);
        tab.setPos((Object)new BigInteger(this.tabPos));
        pPr.addNewRPr().addNewNoProof();
    }

    private void Create_TOC_Hyperlink(CTP p, String text, String bookmarkId, int level, Map<String, Integer> pageNums) {
        CTHyperlink link = p.addNewHyperlink();
        link.setAnchor(bookmarkId);
        link.setHistory((Object)STOnOffEnum.TRUE);
        this.Create_TOC_Text(link, text);
        this.Create_TOC_Tab(link);
        this.Create_TOC_InstrText(link, bookmarkId, pageNums);
    }

    public void Create_TOC_Section() {
        CTBody body = this.docxDocument.getDocument().getBody();
        XWPFParagraph paragraph = this.docxDocument.createParagraph();
        CTPPr ctpPr = paragraph.getCTP().addNewPPr();
        CTSectPr sectPr = ctpPr.addNewSectPr();
        if (!body.isSetSectPr()) {
            return;
        }
        CTSectPr bodySectPr = body.getSectPr();
        sectPr.setPgSz(bodySectPr.getPgSz());
        sectPr.setPgMar(bodySectPr.getPgMar());
        sectPr.setCols(bodySectPr.getCols());
        sectPr.setDocGrid(bodySectPr.getDocGrid());
    }

    public void Create_TOC_Section(String startNum) {
        CTBody body = this.docxDocument.getDocument().getBody();
        XWPFParagraph paragraph = this.docxDocument.createParagraph();
        CTPPr ctpPr = paragraph.getCTP().addNewPPr();
        CTSectPr sectPr = ctpPr.addNewSectPr();
        if (!body.isSetSectPr()) {
            return;
        }
        CTSectPr bodySectPr = body.getSectPr();
        sectPr.setPgSz(bodySectPr.getPgSz());
        sectPr.setPgMar(bodySectPr.getPgMar());
        if (bodySectPr.isSetCols()) {
            sectPr.setCols(bodySectPr.getCols());
        }
        if (bodySectPr.isSetDocGrid()) {
            sectPr.setDocGrid(bodySectPr.getDocGrid());
        }
        if (startNum != null && !"".equals(startNum)) {
            CTPageNumber numType = sectPr.addNewPgNumType();
            numType.setStart(new BigInteger(startNum));
        }
    }

    private void Create_TOC_Text(CTHyperlink link, String text) {
        link.addNewR().addNewT().setStringValue(text);
    }

    private void Create_TOC_Tab(CTHyperlink link) {
        link.addNewR().addNewTab();
    }

    private void Create_TOC_InstrText(CTHyperlink link, String bookmarkId, Map<String, Integer> pageNums) {
        link.addNewR().addNewFldChar().setFldCharType(STFldCharType.BEGIN);
        link.addNewR().addNewInstrText().setStringValue("PAGEREF " + bookmarkId + SPACE + SPACE_H);
        link.addNewR().addNewFldChar().setFldCharType(STFldCharType.SEPARATE);
        link.addNewR().addNewT().setStringValue(pageNums.get(bookmarkId) + "");
        link.addNewR().addNewFldChar().setFldCharType(STFldCharType.END);
    }

    public static String BookmarkID(int index) {
        String id = String.valueOf(index);
        while (id.length() < 8) {
            id = "0" + id;
        }
        return id;
    }

    public static void Create_TOC_Bookmak_Begin(XWPFParagraph paragraph, int indexParagraph) {
        CTBookmark bookmark = paragraph.getCTP().addNewBookmarkStart();
        bookmark.setName("_Toc" + TableOfContents.BookmarkID(indexParagraph));
        bookmark.setId(BigInteger.valueOf(indexParagraph));
    }

    public static void Create_TOC_Bookmak_End(XWPFParagraph paragraph, int indexParagraph) {
        paragraph.getCTP().addNewBookmarkEnd().setId(BigInteger.valueOf(indexParagraph));
    }

    private static void addCustomTOCStyle(XWPFDocument docxDocument2, String strStyleId, int headingLevel) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        CTLatentStyles ctLatentStyles;
        if (docxDocument2.getStyles() == null) {
            docxDocument2.createStyles();
        }
        Class<XWPFStyles> clazz = XWPFStyles.class;
        Field field = clazz.getDeclaredField("ctStyles");
        field.setAccessible(true);
        CTStyles ctStyles = (CTStyles)field.get(docxDocument2.getStyles());
        if (!ctStyles.isSetDocDefaults()) {
            CTDocDefaults docDefaults = ctStyles.addNewDocDefaults();
            CTRPr rpr = docDefaults.addNewRPrDefault().addNewRPr();
            CTFonts fonts = rpr.addNewRFonts();
            fonts.setAsciiTheme(STTheme.MINOR_H_ANSI);
            fonts.setEastAsiaTheme(STTheme.MINOR_EAST_ASIA);
            fonts.setHAnsiTheme(STTheme.MINOR_H_ANSI);
            fonts.setCstheme(STTheme.MINOR_BIDI);
            docDefaults.addNewPPrDefault();
        }
        CTLatentStyles cTLatentStyles = ctLatentStyles = ctStyles.isSetLatentStyles() ? ctStyles.getLatentStyles() : ctStyles.addNewLatentStyles();
        if (!ctLatentStyles.isSetDefLockedState()) {
            ctLatentStyles.setDefLockedState((Object)STOnOffEnum.X_0);
        }
        if (!ctLatentStyles.isSetDefUIPriority()) {
            ctLatentStyles.setDefUIPriority(new BigInteger("99"));
        }
        if (!ctLatentStyles.isSetDefSemiHidden()) {
            ctLatentStyles.setDefSemiHidden((Object)STOnOffEnum.X_0);
        }
        if (!ctLatentStyles.isSetDefUnhideWhenUsed()) {
            ctLatentStyles.setDefUnhideWhenUsed((Object)STOnOffEnum.X_0);
        }
        if (!ctLatentStyles.isSetDefQFormat()) {
            ctLatentStyles.setDefQFormat((Object)STOnOffEnum.X_0);
        }
        if (!ctLatentStyles.isSetCount()) {
            ctLatentStyles.setCount(new BigInteger("1"));
        } else {
            ctLatentStyles.setCount(ctLatentStyles.getCount().add(new BigInteger("1")));
        }
        CTLsdException ctLsdException = ctLatentStyles.addNewLsdException();
        ctLsdException.setName(strStyleId);
        if (headingLevel == 0) {
            ctLsdException.setUiPriority(new BigInteger("0"));
        } else if (headingLevel == 1) {
            ctLsdException.setUiPriority(new BigInteger("9"));
        } else {
            ctLsdException.setSemiHidden((Object)STOnOffEnum.X_1);
            ctLsdException.setUiPriority(new BigInteger("9"));
            ctLsdException.setUnhideWhenUsed((Object)STOnOffEnum.X_1);
        }
        ctLsdException.setQFormat((Object)STOnOffEnum.X_1);
        CTStyle ctStyle = ctStyles.addNewStyle();
        ctStyle.setStyleId("200" + headingLevel);
        ctStyle.setType(STStyleType.PARAGRAPH);
        CTString styleName = (CTString)CTString.Factory.newInstance();
        styleName.setVal(strStyleId);
        ctStyle.setName(styleName);
        CTOnOff onoffnull = (CTOnOff)CTOnOff.Factory.newInstance();
        ctStyle.setUnhideWhenUsed(onoffnull);
        ctStyle.setQFormat(onoffnull);
        CTPPrGeneral ppr = (CTPPrGeneral)CTPPrGeneral.Factory.newInstance();
        if (ppr.getInd() != null) {
            ppr.getInd().setLeftChars(BigInteger.valueOf((headingLevel - 1) * 100));
        } else {
            ppr.addNewInd().setLeftChars(BigInteger.valueOf((headingLevel - 1) * 100));
        }
        ctStyle.setPPr(ppr);
        CTRPr rPr = (CTRPr)CTRPr.Factory.newInstance();
        CTFonts fonts = rPr.addNewRFonts();
        fonts.setAscii("\u5b8b\u4f53");
        fonts.setEastAsia("\u5b8b\u4f53");
        fonts.setHAnsi("\u5b8b\u4f53");
        rPr.addNewB().setVal((Object)STOnOffEnum.OFF);
        rPr.addNewBCs().setVal((Object)STOnOffEnum.OFF);
        rPr.addNewColor().setVal((Object)"auto");
        switch (headingLevel) {
            case 1: {
                rPr.addNewB().setVal((Object)STOnOffEnum.ON);
                rPr.addNewBCs().setVal((Object)STOnOffEnum.ON);
                rPr.addNewSz().setVal((Object)new BigInteger("24"));
                rPr.addNewSzCs().setVal((Object)new BigInteger("24"));
                break;
            }
            case 2: {
                rPr.addNewB().setVal((Object)STOnOffEnum.ON);
                rPr.addNewBCs().setVal((Object)STOnOffEnum.ON);
                rPr.addNewSz().setVal((Object)new BigInteger("24"));
                rPr.addNewSzCs().setVal((Object)new BigInteger("24"));
                break;
            }
            case 3: {
                rPr.addNewSz().setVal((Object)new BigInteger("24"));
                rPr.addNewSzCs().setVal((Object)new BigInteger("24"));
                break;
            }
            case 4: {
                rPr.addNewSz().setVal((Object)new BigInteger("24"));
                rPr.addNewSzCs().setVal((Object)new BigInteger("24"));
            }
        }
        ctStyle.setRPr(rPr);
    }

    public void setTabPos(String width) {
        this.tabPos = String.valueOf(BigInteger.valueOf(Long.valueOf(width)));
    }

    /*
     * Exception decompiling
     */
    public static Integer getPDFPagenum(CustomXWPFDocument document) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Started 4 blocks at once
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.getStartingBlocks(Op04StructuredStatement.java:412)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:487)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:736)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:850)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
         *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
         *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1055)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:942)
         *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
         *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
         *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:76)
         *     at com.openquartz.jardemo2.BatchJarDecompiler.decompileJar(BatchJarDecompiler.java:77)
         *     at com.openquartz.jardemo2.BatchJarDecompiler.decompileJars(BatchJarDecompiler.java:47)
         *     at com.openquartz.jardemo2.BatchJarDecompiler.main(BatchJarDecompiler.java:116)
         */
        throw new IllegalStateException("Decompilation failed");
    }

    /*
     * Exception decompiling
     */
    private static byte[] convertToPdf(CustomXWPFDocument document) throws IOException {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Started 2 blocks at once
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.getStartingBlocks(Op04StructuredStatement.java:412)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:487)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:736)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:850)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
         *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
         *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1055)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:942)
         *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
         *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
         *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:76)
         *     at com.openquartz.jardemo2.BatchJarDecompiler.decompileJar(BatchJarDecompiler.java:77)
         *     at com.openquartz.jardemo2.BatchJarDecompiler.decompileJars(BatchJarDecompiler.java:47)
         *     at com.openquartz.jardemo2.BatchJarDecompiler.main(BatchJarDecompiler.java:116)
         */
        throw new IllegalStateException("Decompilation failed");
    }

    public static void createTocParagraph(CTP paragraph, int level, String text, int num) {
        CTPPr ppr = paragraph.addNewPPr();
        ppr.addNewPStyle().setVal("200" + level);
        CTTabStop ctTab = ppr.addNewTabs().addNewTab();
        ctTab.setVal(STTabJc.RIGHT);
        ctTab.setLeader(STTabTlc.DOT);
        ctTab.setPos((Object)new BigInteger("10060"));
        ppr.addNewRPr().addNewNoProof();
        paragraph.addNewR().addNewT().setStringValue(text);
        paragraph.addNewR().addNewTab();
        paragraph.addNewR().addNewT().setStringValue("" + num);
    }

    class DOCStyle {
        public int level;
        public String id;
        public String name;

        DOCStyle(int level, String name) {
            this.level = level;
            this.id = "100" + level;
            this.name = name;
        }
    }

    class DOCStyles
    extends ArrayList<DOCStyle> {
        private static final long serialVersionUID = -1059552605023851067L;

        DOCStyles() {
        }

        public boolean contains(String id) {
            for (int i = 0; i < this.size(); ++i) {
                if (!((DOCStyle)this.get((int)i)).id.equals(id)) continue;
                return true;
            }
            return false;
        }

        public int getLevel(String id) {
            for (int i = 0; i < this.size(); ++i) {
                if (!((DOCStyle)this.get((int)i)).id.equals(id)) continue;
                return ((DOCStyle)this.get((int)i)).level;
            }
            return -1;
        }
    }
}

