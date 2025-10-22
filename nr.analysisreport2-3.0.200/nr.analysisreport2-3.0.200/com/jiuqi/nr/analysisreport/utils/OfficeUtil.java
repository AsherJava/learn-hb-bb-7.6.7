/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.JsonNode
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.nr.analysisreport.font.util.FontFamily
 *  io.netty.util.internal.StringUtil
 *  org.apache.commons.lang3.StringUtils
 *  org.jsoup.Jsoup
 *  org.jsoup.nodes.Document
 *  org.jsoup.nodes.Element
 *  org.jsoup.select.Elements
 */
package com.jiuqi.nr.analysisreport.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.nr.analysisreport.font.util.FontFamily;
import com.jiuqi.nr.analysisreport.utils.CustomXWPFDocument;
import com.jiuqi.nr.analysisreport.utils.STOnOffEnum;
import com.jiuqi.nr.analysisreport.vo.print.PageHeadFoot;
import com.jiuqi.nr.analysisreport.vo.print.PageNumber;
import com.jiuqi.nr.analysisreport.vo.print.ReportPrintSettingVO;
import io.netty.util.internal.StringUtil;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import org.apache.commons.compress.archivers.dump.InvalidFormatException;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.model.XWPFHeaderFooterPolicy;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.TextAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFStyles;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.apache.xmlbeans.XmlException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTBody;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTDecimalNumber;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTDocDefaults;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTDocument1;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTFonts;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTJc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTLatentStyles;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTLsdException;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTOnOff;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTP;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPPrGeneral;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTR;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTRPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTRow;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSectPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSimpleField;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSpacing;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTString;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTStyle;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTStyles;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblWidth;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTcPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTText;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTrPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STHdrFtr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STJc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STLineSpacingRule;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STMerge;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STStyleType;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STTheme;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OfficeUtil {
    public static final String STYLE_FOOTER = "footer";
    public static final String STYLE_HEADER = "header";
    public static final String LANG_ZH_CN = "zh-CN";
    public static final int ONE_UNIT = 567;
    private static Map<Float, Integer> lineHeightTransition;
    private static Logger logger;

    public static void processParagraphs(List<XWPFParagraph> paragraphList, Map<String, Object> param, CustomXWPFDocument doc) {
        if (paragraphList != null && paragraphList.size() > 0) {
            for (XWPFParagraph paragraph : paragraphList) {
                if (paragraph.getSpacingBefore() >= 1000 || paragraph.getSpacingAfter() > 1000) {
                    paragraph.setSpacingBefore(0);
                    paragraph.setSpacingAfter(0);
                }
                paragraph.setIndentationLeft(0);
                paragraph.setIndentationRight(0);
                List<XWPFRun> runs = paragraph.getRuns();
                ArrayList<XWPFRun> allRuns = new ArrayList<XWPFRun>(runs);
                for (XWPFRun run : allRuns) {
                    String text = run.getText(0);
                    if (text == null) continue;
                    boolean isSetText = false;
                    for (Map.Entry<String, Object> entry : param.entrySet()) {
                        String key = entry.getKey();
                        if (text.indexOf(key) == -1) continue;
                        isSetText = true;
                        Object value = entry.getValue();
                        if (value instanceof String) {
                            text = text.replace(key, value.toString());
                            continue;
                        }
                        if (!(value instanceof Map)) continue;
                        text = text.replace(key, "");
                        Map pic = (Map)value;
                        int width = Integer.parseInt(pic.get("width").toString());
                        int height = Integer.parseInt(pic.get("height").toString());
                        int picType = OfficeUtil.getPictureType(pic.get("type").toString());
                        byte[] byteArray = (byte[])pic.get("content");
                        try {
                            ByteArrayInputStream byteInputStream = new ByteArrayInputStream(byteArray);
                            Throwable throwable = null;
                            try {
                                String blipId = doc.addPictureData((InputStream)byteInputStream, picType);
                                doc.createPicture(blipId, doc.getNextPicNameNumber(picType), width, height, paragraph);
                            }
                            catch (Throwable throwable2) {
                                throwable = throwable2;
                                throw throwable2;
                            }
                            finally {
                                if (byteInputStream == null) continue;
                                if (throwable != null) {
                                    try {
                                        byteInputStream.close();
                                    }
                                    catch (Throwable throwable3) {
                                        throwable.addSuppressed(throwable3);
                                    }
                                    continue;
                                }
                                byteInputStream.close();
                            }
                        }
                        catch (Exception e) {
                            logger.error(e.getMessage(), e);
                        }
                    }
                    if (!isSetText) continue;
                    run.setText(text, 0);
                }
            }
        }
    }

    private static int getPictureType(String picType) {
        int res = CustomXWPFDocument.PICTURE_TYPE_PICT;
        if (picType != null) {
            if (picType.equalsIgnoreCase("png")) {
                res = CustomXWPFDocument.PICTURE_TYPE_PNG;
            } else if (picType.equalsIgnoreCase("dib")) {
                res = CustomXWPFDocument.PICTURE_TYPE_DIB;
            } else if (picType.equalsIgnoreCase("emf")) {
                res = CustomXWPFDocument.PICTURE_TYPE_EMF;
            } else if (picType.equalsIgnoreCase("jpg") || picType.equalsIgnoreCase("jpeg")) {
                res = CustomXWPFDocument.PICTURE_TYPE_JPEG;
            } else if (picType.equalsIgnoreCase("wmf")) {
                res = CustomXWPFDocument.PICTURE_TYPE_WMF;
            }
        }
        return res;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static byte[] inputStream2ByteArray(InputStream in, boolean isClose) {
        byte[] byteArray = null;
        try {
            int total = in.available();
            byteArray = new byte[total];
            in.read(byteArray);
        }
        catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        finally {
            if (isClose) {
                try {
                    in.close();
                }
                catch (Exception e2) {
                    logger.error("\u5173\u95ed\u6d41\u5931\u8d25", e2);
                }
            }
        }
        return byteArray;
    }

    public static void simpleFooter(XWPFDocument docx, String text) throws Exception {
        CTP ctp = CTP.Factory.newInstance();
        CTR ctr = ctp.addNewR();
        CTText textt = ctr.addNewT();
        textt.setStringValue(text);
        XWPFParagraph codePara = new XWPFParagraph(ctp, docx);
        codePara.setAlignment(ParagraphAlignment.CENTER);
        codePara.setVerticalAlignment(TextAlignment.CENTER);
        XWPFParagraph[] newparagraphs = new XWPFParagraph[]{codePara};
        CTSectPr sectPr = docx.getDocument().getBody().addNewSectPr();
        XWPFHeaderFooterPolicy headerFooterPolicy = new XWPFHeaderFooterPolicy(docx, sectPr);
        headerFooterPolicy.createFooter(STHdrFtr.DEFAULT, newparagraphs);
    }

    public static void generateTOC(XWPFDocument document) throws InvalidFormatException, FileNotFoundException, IOException {
        String findText = "catalogVar";
        String replaceText = "";
        block0: for (XWPFParagraph p : document.getParagraphs()) {
            for (XWPFRun r : p.getRuns()) {
                int pos;
                String text = r.getText(pos = r.getTextPosition());
                if (text == null || !text.contains(findText)) continue;
                text = text.replace(findText, replaceText);
                r.setText(text, 0);
                OfficeUtil.addField(p, "TOC \\o \"1-3\" \\h \\z \\u");
                continue block0;
            }
        }
    }

    public static List<HashMap<String, String>> getImgStr(String htmlStr) {
        ArrayList<HashMap<String, String>> pics = new ArrayList<HashMap<String, String>>();
        Document doc = Jsoup.parse((String)htmlStr);
        Elements imgs = doc.select("img");
        for (Element img : imgs) {
            HashMap<String, String> map = new HashMap<String, String>();
            if (!"".equals(img.attr("width"))) {
                map.put("width", img.attr("width").substring(0, img.attr("width").length() - 2));
            }
            if (!"".equals(img.attr("height"))) {
                map.put("height", img.attr("height").substring(0, img.attr("height").length() - 2));
            }
            map.put("img", img.toString().substring(0, img.toString().length() - 1) + "/>");
            map.put("img1", img.toString());
            map.put("src", img.attr("src"));
            pics.add(map);
        }
        return pics;
    }

    private static void addField(XWPFParagraph paragraph, String fieldName) {
        CTSimpleField ctSimpleField = paragraph.getCTP().addNewFldSimple();
        ctSimpleField.setInstr(fieldName);
        ctSimpleField.setDirty(true);
        ctSimpleField.addNewR().addNewT().setStringValue("<<fieldName>>");
    }

    public static int convertPixelToPoint(int pixel) {
        return Integer.parseInt(new DecimalFormat("0").format(Units.pixelToPoints(pixel)));
    }

    public static int convertCentimeterToPoint(int pixel) {
        return pixel * 360000 / 12700;
    }

    public static void addCustomHeadingStyle(CustomXWPFDocument docxDocument, String strStyleId, int headingLevel, float lineHeight) throws XmlException, IOException, NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        CTLatentStyles ctLatentStyles;
        if (docxDocument.getStyles() == null) {
            docxDocument.createStyles();
        }
        Class<XWPFStyles> clazz = XWPFStyles.class;
        Field field = clazz.getDeclaredField("ctStyles");
        field.setAccessible(true);
        CTStyles ctStyles = (CTStyles)field.get(docxDocument.getStyles());
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
            ctLatentStyles.setDefLockedState(STOnOffEnum.X_0);
        }
        if (!ctLatentStyles.isSetDefUIPriority()) {
            ctLatentStyles.setDefUIPriority(new BigInteger("99"));
        }
        if (!ctLatentStyles.isSetDefSemiHidden()) {
            ctLatentStyles.setDefSemiHidden(STOnOffEnum.X_0);
        }
        if (!ctLatentStyles.isSetDefUnhideWhenUsed()) {
            ctLatentStyles.setDefUnhideWhenUsed(STOnOffEnum.X_0);
        }
        if (!ctLatentStyles.isSetDefQFormat()) {
            ctLatentStyles.setDefQFormat(STOnOffEnum.X_0);
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
            ctLsdException.setSemiHidden(STOnOffEnum.X_1);
            ctLsdException.setUiPriority(new BigInteger("9"));
            ctLsdException.setUnhideWhenUsed(STOnOffEnum.X_1);
        }
        ctLsdException.setQFormat(STOnOffEnum.X_1);
        CTStyle ctStyle = ctStyles.addNewStyle();
        ctStyle.setStyleId("100" + headingLevel);
        ctStyle.setType(STStyleType.PARAGRAPH);
        CTString styleName = CTString.Factory.newInstance();
        styleName.setVal(strStyleId);
        ctStyle.setName(styleName);
        if (headingLevel > 0 && headingLevel < 5) {
            CTString styleBasedOn = CTString.Factory.newInstance();
            styleBasedOn.setVal(strStyleId);
            ctStyle.setBasedOn(styleBasedOn);
            CTString styleNext = CTString.Factory.newInstance();
            styleNext.setVal(strStyleId);
            ctStyle.setBasedOn(styleNext);
            CTString styleLink = CTString.Factory.newInstance();
            styleLink.setVal(String.valueOf(headingLevel * 10));
            ctStyle.setLink(styleLink);
            CTDecimalNumber styleUIPriority = CTDecimalNumber.Factory.newInstance();
            styleUIPriority.setVal(new BigInteger("9"));
            ctStyle.setUiPriority(styleUIPriority);
        }
        ctStyle.setQFormat(CTOnOff.Factory.newInstance());
        CTPPrGeneral pPr = ctStyle.addNewPPr();
        CTOnOff off = CTOnOff.Factory.newInstance();
        off.setVal(STOnOffEnum.OFF);
        CTOnOff on = CTOnOff.Factory.newInstance();
        on.setVal(STOnOffEnum.ON);
        pPr.setWidowControl(on);
        pPr.setAdjustRightInd(on);
        pPr.setContextualSpacing(on);
        pPr.setMirrorIndents(off);
        pPr.setSnapToGrid(off);
        CTSpacing spacing = pPr.addNewSpacing();
        Integer lineHeightValue = lineHeightTransition.get(Float.valueOf(lineHeight));
        if (lineHeightValue == null) {
            spacing.setLine(new BigInteger("360"));
        } else {
            spacing.setLine(new BigInteger(lineHeightValue.toString()));
        }
        spacing.setLineRule(STLineSpacingRule.AUTO);
        if (headingLevel > 0 && headingLevel < 5) {
            pPr.setKeepNext(CTOnOff.Factory.newInstance());
            pPr.setKeepLines(CTOnOff.Factory.newInstance());
        }
        CTJc jc = CTJc.Factory.newInstance();
        jc.setVal(STJc.LEFT);
        pPr.setJc(jc);
        CTRPr rPr = null;
        CTFonts rFonts = null;
        CTDecimalNumber styleOutLineLvl = null;
        CTOnOff[] bcs = new CTOnOff[]{CTOnOff.Factory.newInstance()};
        switch (headingLevel) {
            case 1: {
                rPr = ctStyle.addNewRPr();
                rFonts = rPr.addNewRFonts();
                styleOutLineLvl = CTDecimalNumber.Factory.newInstance();
                styleOutLineLvl.setVal(new BigInteger("0"));
                pPr.setOutlineLvl(styleOutLineLvl);
                rFonts.setAscii(FontFamily.SIMHEI.getName());
                rFonts.setHAnsi(FontFamily.SIMHEI.getName());
                rFonts.setEastAsia(FontFamily.SIMHEI.getName());
                rPr.setBCsArray(bcs);
                rPr.addNewKern().setVal(new BigInteger("44"));
                rPr.addNewSz().setVal(new BigInteger("32"));
                rPr.addNewSzCs().setVal(new BigInteger("44"));
                break;
            }
            case 2: {
                rPr = ctStyle.addNewRPr();
                rFonts = rPr.addNewRFonts();
                styleOutLineLvl = CTDecimalNumber.Factory.newInstance();
                styleOutLineLvl.setVal(new BigInteger("1"));
                pPr.setOutlineLvl(styleOutLineLvl);
                ctStyle.addNewUnhideWhenUsed();
                rFonts.setAscii(FontFamily.REGULARSCRIPT.getName());
                rFonts.setHAnsi(FontFamily.REGULARSCRIPT.getName());
                rFonts.setEastAsia(FontFamily.REGULARSCRIPT.getName());
                rPr.setBCsArray(bcs);
                rPr.addNewKern().setVal(new BigInteger("44"));
                rPr.addNewSz().setVal(new BigInteger("30"));
                rPr.addNewSzCs().setVal(new BigInteger("44"));
                break;
            }
            case 3: {
                rPr = ctStyle.addNewRPr();
                rFonts = rPr.addNewRFonts();
                styleOutLineLvl = CTDecimalNumber.Factory.newInstance();
                styleOutLineLvl.setVal(new BigInteger("2"));
                pPr.setOutlineLvl(styleOutLineLvl);
                ctStyle.addNewUnhideWhenUsed();
                rFonts.setAscii(FontFamily.SUN.getName());
                rFonts.setHAnsi(FontFamily.SUN.getName());
                rFonts.setEastAsia(FontFamily.SUN.getName());
                rPr.setBCsArray(bcs);
                rPr.addNewB();
                rPr.addNewKern().setVal(new BigInteger("30"));
                rPr.addNewSz().setVal(new BigInteger("24"));
                rPr.addNewSzCs().setVal(new BigInteger("30"));
                break;
            }
            case 4: {
                rPr = ctStyle.addNewRPr();
                rFonts = rPr.addNewRFonts();
                styleOutLineLvl = CTDecimalNumber.Factory.newInstance();
                styleOutLineLvl.setVal(new BigInteger("3"));
                pPr.setOutlineLvl(styleOutLineLvl);
                ctStyle.addNewUnhideWhenUsed();
                rFonts.setAscii(FontFamily.SUN.getName());
                rFonts.setHAnsi(FontFamily.SUN.getName());
                rFonts.setEastAsia(FontFamily.SUN.getName());
                rPr.setBCsArray(bcs);
                rPr.addNewKern().setVal(new BigInteger("30"));
                rPr.addNewSz().setVal(new BigInteger("24"));
                rPr.addNewSzCs().setVal(new BigInteger("30"));
                break;
            }
            default: {
                rPr = ctStyle.addNewRPr();
                rFonts = rPr.addNewRFonts();
                rFonts.setAscii(FontFamily.SUN.getName());
                rFonts.setHAnsi(FontFamily.SUN.getName());
                rFonts.setEastAsia(FontFamily.SUN.getName());
                rPr.setBCsArray(bcs);
                rPr.addNewKern().setVal(new BigInteger("30"));
                rPr.addNewSz().setVal(new BigInteger("24"));
                rPr.addNewSzCs().setVal(new BigInteger("30"));
            }
        }
    }

    public static Map<String, String> createDefaultHeader(CustomXWPFDocument docx, String text) {
        if (StringUtil.isNullOrEmpty((String)text)) {
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        Base64.Decoder decoder = Base64.getDecoder();
        String oddText = "";
        String evenText = "";
        String headerText = "";
        String showHead = "false";
        Boolean showDiffHead = false;
        try {
            String pageHeaderStr;
            JsonNode tableidNode = mapper.readTree(text);
            if (tableidNode.get("pageHeader") != null && (pageHeaderStr = tableidNode.get("pageHeader").textValue()) != null && !"".equals(pageHeaderStr)) {
                Element body;
                Document doc;
                String encodedText;
                showHead = "true";
                JsonNode pageHeaderNode = mapper.readTree(pageHeaderStr);
                if (pageHeaderNode.get("data") != null) {
                    encodedText = pageHeaderNode.get("data").textValue();
                    headerText = new String(decoder.decode(encodedText), "UTF-8");
                    doc = Jsoup.parseBodyFragment((String)headerText);
                    body = doc.body();
                    headerText = body.wholeText();
                }
                if (pageHeaderNode.get("oddData") != null) {
                    encodedText = pageHeaderNode.get("oddData").textValue();
                    oddText = new String(decoder.decode(encodedText), "UTF-8");
                    doc = Jsoup.parseBodyFragment((String)oddText);
                    body = doc.body();
                    oddText = body.wholeText();
                }
                if (pageHeaderNode.get("evenData") != null) {
                    encodedText = pageHeaderNode.get("evenData").textValue();
                    evenText = new String(decoder.decode(encodedText), "UTF-8");
                    doc = Jsoup.parseBodyFragment((String)evenText);
                    body = doc.body();
                    evenText = body.wholeText();
                }
                if (pageHeaderNode.get("showDiffHead") != null) {
                    showDiffHead = pageHeaderNode.get("showDiffHead").booleanValue();
                }
            }
        }
        catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        HashMap<String, String> res = new HashMap<String, String>();
        res.put("headerText", headerText);
        res.put("headerOddText", oddText);
        res.put("headerEvenText", evenText);
        res.put("showHead", showHead);
        res.put("showDiffHead", showDiffHead.toString());
        return res;
    }

    public static Map<String, String> createDefaultHeadFoot(PageHeadFoot pageHeadFoot, boolean isHead) {
        if (pageHeadFoot == null) {
            return new HashMap<String, String>();
        }
        String text = "";
        String oddText = "";
        String evenText = "";
        String show = "false";
        Boolean showDiff = false;
        try {
            if (!StringUtils.isEmpty((CharSequence)pageHeadFoot.getData())) {
                text = OfficeUtil.getDecodeWholeText(pageHeadFoot.getData());
                show = "true";
            }
            if (!StringUtils.isEmpty((CharSequence)pageHeadFoot.getOddData())) {
                oddText = OfficeUtil.getDecodeWholeText(pageHeadFoot.getOddData());
                show = "true";
            }
            if (!StringUtils.isEmpty((CharSequence)pageHeadFoot.getEvenData())) {
                evenText = OfficeUtil.getDecodeWholeText(pageHeadFoot.getEvenData());
                show = "true";
            }
            if (pageHeadFoot.getShowDiff() != null) {
                showDiff = pageHeadFoot.getShowDiff();
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        HashMap<String, String> res = new HashMap<String, String>();
        if (isHead) {
            res.put("headerText", text);
            res.put("headerOddText", oddText);
            res.put("headerEvenText", evenText);
            res.put("showHead", show);
            res.put("showDiffHead", showDiff.toString());
        } else {
            res.put("footerText", text);
            res.put("footerOddText", oddText);
            res.put("footerEvenText", evenText);
            res.put("showFoot", show);
            res.put("showDiffFoot", showDiff.toString());
        }
        return res;
    }

    public static String getDecodeWholeText(String encodedText) throws UnsupportedEncodingException {
        if (StringUtils.isEmpty((CharSequence)encodedText)) {
            return null;
        }
        Base64.Decoder decoder = Base64.getDecoder();
        return new String(decoder.decode(encodedText), "UTF-8");
    }

    public static Map<String, String> createDefaultFooter(CustomXWPFDocument docx, String text) {
        if (StringUtil.isNullOrEmpty((String)text)) {
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        Base64.Decoder decoder = Base64.getDecoder();
        String footerText = "";
        String oddText = "";
        String evenText = "";
        String showFoot = "false";
        Boolean showDiffFoot = false;
        try {
            String pageFooterStr;
            JsonNode tableidNode = mapper.readTree(text);
            if (tableidNode.get("pageFooter") != null && (pageFooterStr = tableidNode.get("pageFooter").textValue()) != null && !"".equals(pageFooterStr)) {
                Element body;
                Document doc;
                String encodedText;
                showFoot = "true";
                JsonNode pageFooterNode = mapper.readTree(pageFooterStr);
                if (pageFooterNode.get("data") != null) {
                    encodedText = pageFooterNode.get("data").textValue();
                    footerText = new String(decoder.decode(encodedText), "UTF-8");
                    doc = Jsoup.parseBodyFragment((String)footerText);
                    body = doc.body();
                    footerText = body.wholeText();
                }
                if (pageFooterNode.get("oddData") != null) {
                    encodedText = pageFooterNode.get("oddData").textValue();
                    oddText = new String(decoder.decode(encodedText), "UTF-8");
                    doc = Jsoup.parseBodyFragment((String)oddText);
                    body = doc.body();
                    oddText = body.wholeText();
                }
                if (pageFooterNode.get("evenData") != null) {
                    encodedText = pageFooterNode.get("evenData").textValue();
                    evenText = new String(decoder.decode(encodedText), "UTF-8");
                    doc = Jsoup.parseBodyFragment((String)evenText);
                    body = doc.body();
                    evenText = body.wholeText();
                }
                if (pageFooterNode.get("showDiffFoot") != null) {
                    showDiffFoot = pageFooterNode.get("showDiffFoot").booleanValue();
                }
            }
        }
        catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        HashMap<String, String> res = new HashMap<String, String>();
        res.put("footerText", footerText);
        res.put("footerOddText", oddText);
        res.put("footerEvenText", evenText);
        res.put("showFoot", showFoot);
        res.put("showDiffFoot", showDiffFoot.toString());
        return res;
    }

    public static Map<String, String> createpageNo(XWPFDocument docx, String text) {
        String originNumber;
        String showDiff;
        String oddshowNumber;
        String evenshowNumber;
        String showNumber;
        STJc.Enum oddEnumType;
        STJc.Enum evenEnumType;
        STJc.Enum enumType;
        Boolean isoddfoot;
        Boolean isevenfoot;
        Boolean istotalfoot;
        block7: {
            if (StringUtil.isNullOrEmpty((String)text)) {
                return null;
            }
            ObjectMapper mapper = new ObjectMapper();
            istotalfoot = false;
            isevenfoot = false;
            isoddfoot = false;
            enumType = STJc.CENTER;
            evenEnumType = STJc.CENTER;
            oddEnumType = STJc.CENTER;
            showNumber = "false";
            evenshowNumber = "false";
            oddshowNumber = "false";
            showDiff = "false";
            originNumber = "1";
            try {
                JsonNode tableidNode = mapper.readTree(text);
                if (tableidNode.get("pageNumber") != null) {
                    String pageNumberStr = tableidNode.get("pageNumber").textValue();
                    if (pageNumberStr != null && !"".equals(pageNumberStr)) {
                        JsonNode pageNumberNode = mapper.readTree(pageNumberStr);
                        originNumber = pageNumberNode.get("originNumber").asText();
                        showDiff = pageNumberNode.get("showDiff").asText();
                        showNumber = pageNumberNode.get("showNumber").get("total").asText();
                        evenshowNumber = pageNumberNode.get("showNumber").get("even").asText();
                        oddshowNumber = pageNumberNode.get("showNumber").get("odd").asText();
                        String numberPosition = pageNumberNode.get("numberPosition").get("total").textValue();
                        String evenPosition = pageNumberNode.get("numberPosition").get("even").textValue();
                        String oddPosition = pageNumberNode.get("numberPosition").get("odd").textValue();
                        if ("true".equals(showDiff)) {
                            istotalfoot = OfficeUtil.getIsfoot(numberPosition);
                            isevenfoot = OfficeUtil.getIsfoot(evenPosition);
                            isoddfoot = OfficeUtil.getIsfoot(oddPosition);
                            enumType = OfficeUtil.getEnumType(numberPosition);
                            evenEnumType = OfficeUtil.getEnumType(evenPosition);
                            oddEnumType = OfficeUtil.getEnumType(oddPosition);
                        } else {
                            istotalfoot = OfficeUtil.getIsfoot(numberPosition);
                            isevenfoot = OfficeUtil.getIsfoot(numberPosition);
                            isoddfoot = OfficeUtil.getIsfoot(numberPosition);
                            evenshowNumber = showNumber;
                            oddshowNumber = showNumber;
                            enumType = OfficeUtil.getEnumType(numberPosition);
                            evenEnumType = OfficeUtil.getEnumType(numberPosition);
                            oddEnumType = OfficeUtil.getEnumType(numberPosition);
                        }
                    }
                    break block7;
                }
                return null;
            }
            catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
        }
        HashMap<String, String> res = new HashMap<String, String>();
        res.put("originNumber", originNumber);
        res.put("showDiff", showDiff);
        res.put("istotalfoot", istotalfoot.toString());
        res.put("isevenfoot", isevenfoot.toString());
        res.put("isoddfoot", isoddfoot.toString());
        res.put("showNumber", showNumber);
        res.put("evenshowNumber", evenshowNumber);
        res.put("oddshowNumber", oddshowNumber);
        res.put("enumType", enumType.toString());
        res.put("evenEnumType", evenEnumType.toString());
        res.put("oddEnumType", oddEnumType.toString());
        return res;
    }

    public static Map<String, String> createPageNo(ReportPrintSettingVO reportPrintSettingVO) {
        if (reportPrintSettingVO == null) {
            return null;
        }
        String isShowNumber = "false";
        Boolean istotalfoot = false;
        Boolean isevenfoot = false;
        Boolean isoddfoot = false;
        STJc.Enum enumType = STJc.CENTER;
        STJc.Enum evenEnumType = STJc.CENTER;
        STJc.Enum oddEnumType = STJc.CENTER;
        String evenshowNumber = "false";
        String oddshowNumber = "false";
        String showDiff = "false";
        String originNumber = "1";
        PageNumber pageNumber = reportPrintSettingVO.getPageNumber();
        if (pageNumber != null) {
            if (StringUtils.isNotEmpty((CharSequence)pageNumber.getOriginNumber())) {
                originNumber = pageNumber.getOriginNumber();
                showDiff = pageNumber.getShowDiff().toString();
                PageNumber.ShowNumber showNumber = pageNumber.getShowNumber();
                evenshowNumber = showNumber.getEven().toString();
                oddshowNumber = showNumber.getOdd().toString();
                isShowNumber = pageNumber.getShowNumber().getTotal().toString();
                PageNumber.NumberPosition pageNumberPosition = pageNumber.getNumberPosition();
                String numberPosition = pageNumberPosition.getTotal();
                String evenPosition = pageNumberPosition.getEven();
                String oddPosition = pageNumberPosition.getOdd();
                if ("true".equals(showDiff)) {
                    istotalfoot = OfficeUtil.getIsfoot(numberPosition);
                    isevenfoot = OfficeUtil.getIsfoot(evenPosition);
                    isoddfoot = OfficeUtil.getIsfoot(oddPosition);
                    enumType = OfficeUtil.getEnumType(numberPosition);
                    evenEnumType = OfficeUtil.getEnumType(evenPosition);
                    oddEnumType = OfficeUtil.getEnumType(oddPosition);
                } else {
                    istotalfoot = OfficeUtil.getIsfoot(numberPosition);
                    isevenfoot = OfficeUtil.getIsfoot(numberPosition);
                    isoddfoot = OfficeUtil.getIsfoot(numberPosition);
                    evenshowNumber = isShowNumber;
                    oddshowNumber = isShowNumber;
                    enumType = OfficeUtil.getEnumType(numberPosition);
                    evenEnumType = OfficeUtil.getEnumType(numberPosition);
                    oddEnumType = OfficeUtil.getEnumType(numberPosition);
                }
            } else {
                pageNumber.setOriginNumber(originNumber);
            }
        } else {
            return new HashMap<String, String>();
        }
        HashMap<String, String> res = new HashMap<String, String>();
        res.put("originNumber", originNumber);
        res.put("showDiff", showDiff);
        res.put("istotalfoot", istotalfoot.toString());
        res.put("isevenfoot", isevenfoot.toString());
        res.put("isoddfoot", isoddfoot.toString());
        res.put("showNumber", isShowNumber);
        res.put("evenshowNumber", evenshowNumber);
        res.put("oddshowNumber", oddshowNumber);
        res.put("enumType", enumType.toString());
        res.put("evenEnumType", evenEnumType.toString());
        res.put("oddEnumType", oddEnumType.toString());
        return res;
    }

    public static boolean getIsfoot(String type) {
        boolean isfoot = false;
        if ("3".equals(type)) {
            isfoot = true;
        } else if ("4".equals(type)) {
            isfoot = true;
        } else if ("5".equals(type)) {
            isfoot = true;
        }
        return isfoot;
    }

    public static STJc.Enum getEnumType(String encodedText) {
        STJc.Enum enumType = STJc.CENTER;
        if (StringUtil.isNullOrEmpty((String)encodedText)) {
            return enumType;
        }
        if ("0".equals(encodedText)) {
            enumType = STJc.LEFT;
        } else if ("1".equals(encodedText)) {
            enumType = STJc.CENTER;
        } else if ("2".equals(encodedText)) {
            enumType = STJc.RIGHT;
        } else if ("3".equals(encodedText)) {
            enumType = STJc.LEFT;
        } else if ("4".equals(encodedText.trim())) {
            enumType = STJc.CENTER;
        } else if ("5".equals(encodedText)) {
            enumType = STJc.RIGHT;
        }
        return enumType;
    }

    public static void mergeCellsHorizontal(XWPFTable table, int row, int fromCell, int toCell) {
        for (int cellIndex = fromCell; cellIndex <= toCell; ++cellIndex) {
            XWPFTableCell cell = table.getRow(row).getCell(cellIndex);
            if (cell == null) {
                return;
            }
            if (cellIndex == fromCell) {
                OfficeUtil.getCellCTTcPr(cell).addNewHMerge().setVal(STMerge.RESTART);
                continue;
            }
            OfficeUtil.getCellCTTcPr(cell).addNewHMerge().setVal(STMerge.CONTINUE);
        }
    }

    public static void mergeCellsVertically(XWPFTable table, int col, int fromRow, int toRow) {
        for (int rowIndex = fromRow; rowIndex <= toRow; ++rowIndex) {
            XWPFTableCell cell = table.getRow(rowIndex).getCell(col);
            if (cell == null) {
                return;
            }
            if (rowIndex == fromRow) {
                OfficeUtil.getCellCTTcPr(cell).addNewVMerge().setVal(STMerge.RESTART);
                continue;
            }
            OfficeUtil.getCellCTTcPr(cell).addNewVMerge().setVal(STMerge.CONTINUE);
        }
    }

    public static void mergeCells(XWPFTable table, int fromRow, int toRow, int fromCol, int toCol) {
        for (int rowIndex = fromRow; rowIndex <= toRow; ++rowIndex) {
            for (int cellIndex = fromCol; cellIndex <= toCol; ++cellIndex) {
                XWPFTableCell cell = table.getRow(rowIndex).getCell(cellIndex);
                if (cell == null) {
                    return;
                }
                if (rowIndex == fromRow) {
                    OfficeUtil.getCellCTTcPr(cell).addNewVMerge().setVal(STMerge.RESTART);
                } else {
                    OfficeUtil.getCellCTTcPr(cell).addNewVMerge().setVal(STMerge.CONTINUE);
                }
                if (cellIndex == fromCol) {
                    OfficeUtil.getCellCTTcPr(cell).addNewHMerge().setVal(STMerge.RESTART);
                    continue;
                }
                OfficeUtil.getCellCTTcPr(cell).addNewHMerge().setVal(STMerge.CONTINUE);
            }
        }
    }

    public static CTTrPr getRowCTTrPr(XWPFTableRow row) {
        CTRow ctr = row.getCtRow();
        CTTrPr trpr = ctr.isSetTrPr() ? ctr.getTrPr() : ctr.addNewTrPr();
        return trpr;
    }

    public static CTTcPr getCellCTTcPr(XWPFTableCell cell) {
        CTTc cttc = cell.getCTTc();
        CTTcPr tcPr = cttc.isSetTcPr() ? cttc.getTcPr() : cttc.addNewTcPr();
        return tcPr;
    }

    public static CTTblWidth getCellTblWidth(CTTcPr mTcpr) {
        if (mTcpr.isSetTcW()) {
            return mTcpr.getTcW() == null ? mTcpr.addNewTcW() : mTcpr.getTcW();
        }
        return mTcpr.addNewTcW();
    }

    public static CTSectPr getCTSectPr(XWPFDocument doc) {
        CTDocument1 document = doc.getDocument();
        CTBody body = document.isSetBody() ? document.getBody() : document.addNewBody();
        CTSectPr sectPr = body.isSetSectPr() ? body.getSectPr() : body.addNewSectPr();
        return sectPr;
    }

    public static ParagraphAlignment getAlignment(Element e) {
        ParagraphAlignment res = ParagraphAlignment.LEFT;
        String style = e.attr("style");
        String typeStyle = "";
        if (!"".equals(style)) {
            String[] tokens;
            for (String token : tokens = style.split(";")) {
                if (token.indexOf(":") <= -1 || token.indexOf("text-align") <= -1) continue;
                typeStyle = token.split(":")[1];
            }
        }
        if (typeStyle.indexOf("center") > -1) {
            res = ParagraphAlignment.CENTER;
        } else if (typeStyle.indexOf("right") > -1) {
            res = ParagraphAlignment.RIGHT;
        } else if (typeStyle.indexOf("justify") > -1) {
            res = ParagraphAlignment.BOTH;
        }
        return res;
    }

    public static ParagraphAlignment getTableAlignment(Element e) {
        ParagraphAlignment res = ParagraphAlignment.LEFT;
        String typeStyle = e.toString();
        if ((typeStyle = typeStyle.replaceAll("\\s*", "")).indexOf("text-align:center") > -1 || typeStyle.indexOf("align=\"center\"") > -1) {
            res = ParagraphAlignment.CENTER;
        } else if (typeStyle.indexOf("text-align:right") > -1 || typeStyle.indexOf("align=\"right\"") > -1) {
            res = ParagraphAlignment.RIGHT;
        }
        return res;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Loose catch block
     */
    public static String CropImage(String base64, int x, int y, int width, int height) throws IOException {
        if (StringUtil.isNullOrEmpty((String)base64)) {
            return "";
        }
        if (x < 0 || y < 0 || width <= 0 || height <= 0) {
            return "";
        }
        InputStream is = null;
        ImageInputStream iis = null;
        Iterator<ImageReader> it = null;
        Base64.Encoder encoder = Base64.getEncoder();
        Base64.Decoder decoder = Base64.getDecoder();
        String lastName = base64.replaceAll("data:image/(.*)?;base64,.*", "$1");
        base64 = base64.replaceAll("data:image/.*?;base64,(.*)", "$1");
        try {
            String string;
            Object object;
            ByteArrayOutputStream stream;
            block33: {
                block34: {
                    stream = new ByteArrayOutputStream();
                    object = null;
                    byte[] byteArray = decoder.decode(base64);
                    is = new ByteArrayInputStream(byteArray);
                    it = ImageIO.getImageReadersByFormatName(lastName);
                    ImageReader reader = it.next();
                    iis = ImageIO.createImageInputStream(is);
                    reader.setInput(iis, true);
                    ImageReadParam param = reader.getDefaultReadParam();
                    Rectangle rect = new Rectangle(x, y, width, height);
                    param.setSourceRegion(rect);
                    BufferedImage bi = reader.read(0, param);
                    ImageIO.write((RenderedImage)bi, lastName, stream);
                    string = new StringBuffer("data:image/").append(lastName).append(";base64,").append(new String(encoder.encode(stream.toByteArray()))).toString();
                    if (stream == null) break block33;
                    if (object == null) break block34;
                    try {
                        stream.close();
                    }
                    catch (Throwable throwable) {
                        ((Throwable)object).addSuppressed(throwable);
                    }
                    break block33;
                }
                stream.close();
            }
            return string;
            catch (Throwable byteArray) {
                try {
                    try {
                        object = byteArray;
                        throw byteArray;
                    }
                    catch (Throwable throwable) {
                        if (stream != null) {
                            if (object != null) {
                                try {
                                    stream.close();
                                }
                                catch (Throwable throwable2) {
                                    ((Throwable)object).addSuppressed(throwable2);
                                }
                            } else {
                                stream.close();
                            }
                        }
                        throw throwable;
                    }
                }
                catch (IOException e) {
                    logger.error(e.getMessage(), e);
                    object = "";
                    return object;
                }
            }
        }
        finally {
            if (is != null) {
                try {
                    is.close();
                }
                catch (Exception e) {
                    logger.info(e.getMessage(), e);
                }
            }
            if (iis != null) {
                try {
                    iis.close();
                }
                catch (Exception e) {
                    logger.info(e.getMessage(), e);
                }
            }
        }
    }

    public static String CropImage(String base64, int width, int height) throws IOException {
        return OfficeUtil.CropImage(base64, 0, 0, width, height);
    }

    public static void setSpaceing(XWPFParagraph paragraph, String lineSpace) {
        if (StringUtil.isNullOrEmpty((String)lineSpace)) {
            return;
        }
        CTP para = paragraph.getCTP();
        CTPPr pPr = para.isSetPPr() ? para.getPPr() : para.addNewPPr();
        CTSpacing spacing = pPr.addNewSpacing();
        BigDecimal space = null;
        if (lineSpace.contains("point")) {
            space = new BigDecimal(lineSpace.replace("point", "")).multiply(new BigDecimal("20"));
            spacing.setLineRule(STLineSpacingRule.EXACT);
        } else if (lineSpace.contains("%")) {
            space = new BigDecimal(lineSpace.replace("%", "")).multiply(new BigDecimal("240")).divide(new BigDecimal("100"));
            spacing.setLineRule(STLineSpacingRule.AUTO);
        } else if (lineSpace.contains("em")) {
            space = new BigDecimal(lineSpace.replace("em", "")).multiply(new BigDecimal("240"));
            spacing.setLineRule(STLineSpacingRule.AUTO);
        } else if (lineSpace.contains("px")) {
            space = new BigDecimal(lineSpace.replace("px", "")).multiply(new BigDecimal("15"));
            spacing.setLineRule(STLineSpacingRule.EXACT);
            space = new BigDecimal(space.setScale(0, 4).intValue());
        }
        spacing.setLine(new BigInteger(space.intValue() + ""));
    }

    static {
        HashMap<Float, Integer> dataMap = new HashMap<Float, Integer>();
        dataMap.put(Float.valueOf(1.0f), 240);
        dataMap.put(Float.valueOf(1.5f), 360);
        dataMap.put(Float.valueOf(1.75f), 420);
        dataMap.put(Float.valueOf(2.0f), 480);
        dataMap.put(Float.valueOf(3.0f), 720);
        dataMap.put(Float.valueOf(4.0f), 960);
        dataMap.put(Float.valueOf(5.0f), 1200);
        lineHeightTransition = Collections.unmodifiableMap(dataMap);
        logger = LoggerFactory.getLogger(OfficeUtil.class);
    }
}

