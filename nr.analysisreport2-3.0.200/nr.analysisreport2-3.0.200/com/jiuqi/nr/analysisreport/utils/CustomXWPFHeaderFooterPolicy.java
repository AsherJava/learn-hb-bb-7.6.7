/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.itextpdf.io.image.ImageData
 *  com.itextpdf.io.image.ImageDataFactory
 *  com.jiuqi.nr.analysisreport.font.util.FontFamily
 *  com.microsoft.schemas.office.word.STHorizontalAnchor
 *  com.microsoft.schemas.office.word.STVerticalAnchor
 *  org.apache.commons.lang3.StringUtils
 *  org.openxmlformats.schemas.wordprocessingml.x2006.main.CTDocRsids
 */
package com.jiuqi.nr.analysisreport.utils;

import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.jiuqi.nr.analysisreport.font.util.FontFamily;
import com.jiuqi.nr.analysisreport.utils.CustomXWPFDocument;
import com.jiuqi.nr.analysisreport.vo.print.PrintStyle;
import com.microsoft.schemas.office.office.CTLock;
import com.microsoft.schemas.office.office.STConnectType;
import com.microsoft.schemas.office.word.CTWrap;
import com.microsoft.schemas.office.word.STHorizontalAnchor;
import com.microsoft.schemas.office.word.STVerticalAnchor;
import com.microsoft.schemas.vml.CTFill;
import com.microsoft.schemas.vml.CTFormulas;
import com.microsoft.schemas.vml.CTGroup;
import com.microsoft.schemas.vml.CTH;
import com.microsoft.schemas.vml.CTHandles;
import com.microsoft.schemas.vml.CTImageData;
import com.microsoft.schemas.vml.CTPath;
import com.microsoft.schemas.vml.CTShape;
import com.microsoft.schemas.vml.CTShapetype;
import com.microsoft.schemas.vml.CTTextPath;
import com.microsoft.schemas.vml.STExt;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Base64;
import java.util.Iterator;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ooxml.POIXMLDocumentPart;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xwpf.usermodel.Document;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFFactory;
import org.apache.poi.xwpf.usermodel.XWPFFooter;
import org.apache.poi.xwpf.usermodel.XWPFHeader;
import org.apache.poi.xwpf.usermodel.XWPFHeaderFooter;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFPictureData;
import org.apache.poi.xwpf.usermodel.XWPFRelation;
import org.apache.xmlbeans.impl.values.XmlValueOutOfRangeException;
import org.openxmlformats.schemas.officeDocument.x2006.sharedTypes.STTrueFalse;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTBody;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTDocRsids;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTHdrFtr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTHdrFtrRef;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTP;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPicture;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTR;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTRPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSectPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSettings;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTString;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.FtrDocument;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.HdrDocument;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STHdrFtr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomXWPFHeaderFooterPolicy {
    private static final Logger log = LoggerFactory.getLogger(CustomXWPFHeaderFooterPolicy.class);
    public static final STHdrFtr.Enum DEFAULT = STHdrFtr.DEFAULT;
    public static final STHdrFtr.Enum EVEN = STHdrFtr.EVEN;
    public static final STHdrFtr.Enum FIRST = STHdrFtr.FIRST;
    private CustomXWPFDocument doc;
    private XWPFHeader firstPageHeader;
    private XWPFFooter firstPageFooter;
    private XWPFHeader evenPageHeader;
    private XWPFFooter evenPageFooter;
    private XWPFHeader defaultHeader;
    private XWPFFooter defaultFooter;
    private String type = "none";
    private String content = "";
    private String title = "";
    private String zoom = "";
    private Boolean erosion = true;
    private String paperWidth = "0";
    private String fontfamily = "";
    private String fontsize = "";
    private String color = "";
    private Boolean translucent = true;
    private String format = "";
    private static final String WATERMARK_DEFAULT_PAPERWIDHT = "0";
    private static final String WATERMARK_DEFAULT_ZOOM = "100%";
    private static final String WATERMARK_DEFAULT_FONTFAMILY = "Microsoft YaHei";
    private static final String WATERMARK_DEFAULT_FONTSIZE = "16";
    private static final String WATERMARK_DEFAULT_COLOR = "#000000";
    private static final String WATERMARK_DEFAULT_FORMAT = "level";

    public CustomXWPFHeaderFooterPolicy(CustomXWPFDocument doc) {
        this(doc, null);
    }

    public CustomXWPFHeaderFooterPolicy(CustomXWPFDocument doc, CTSectPr sectPr) {
        STHdrFtr.Enum type;
        POIXMLDocumentPart relatedPart;
        CTHdrFtrRef ref;
        int i;
        if (sectPr == null) {
            CTBody ctBody = doc.getDocument().getBody();
            sectPr = ctBody.isSetSectPr() ? ctBody.getSectPr() : ctBody.addNewSectPr();
        }
        this.doc = doc;
        for (i = 0; i < sectPr.sizeOfHeaderReferenceArray(); ++i) {
            ref = sectPr.getHeaderReferenceArray(i);
            relatedPart = doc.getRelationById(ref.getId());
            XWPFHeader hdr = null;
            if (relatedPart != null && relatedPart instanceof XWPFHeader) {
                hdr = (XWPFHeader)relatedPart;
            }
            try {
                type = ref.getType();
            }
            catch (XmlValueOutOfRangeException e) {
                type = STHdrFtr.DEFAULT;
            }
            this.assignHeader(hdr, type);
        }
        for (i = 0; i < sectPr.sizeOfFooterReferenceArray(); ++i) {
            ref = sectPr.getFooterReferenceArray(i);
            relatedPart = doc.getRelationById(ref.getId());
            XWPFFooter ftr = null;
            if (relatedPart != null && relatedPart instanceof XWPFFooter) {
                ftr = (XWPFFooter)relatedPart;
            }
            try {
                type = ref.getType();
            }
            catch (XmlValueOutOfRangeException e) {
                type = STHdrFtr.DEFAULT;
            }
            this.assignFooter(ftr, type);
        }
    }

    private void assignFooter(XWPFFooter ftr, STHdrFtr.Enum type) {
        if (type == STHdrFtr.FIRST) {
            this.firstPageFooter = ftr;
        } else if (type == STHdrFtr.EVEN) {
            this.evenPageFooter = ftr;
        } else {
            this.defaultFooter = ftr;
        }
    }

    private void assignHeader(XWPFHeader hdr, STHdrFtr.Enum type) {
        if (type == STHdrFtr.FIRST) {
            this.firstPageHeader = hdr;
        } else if (type == STHdrFtr.EVEN) {
            this.evenPageHeader = hdr;
        } else {
            this.defaultHeader = hdr;
        }
    }

    public XWPFHeader createHeader(STHdrFtr.Enum type) {
        return this.createHeader(type, null);
    }

    public XWPFHeader createHeader(STHdrFtr.Enum type, XWPFParagraph[] pars) {
        XWPFHeader header = this.getHeader(type);
        if (header == null) {
            HdrDocument hdrDoc = HdrDocument.Factory.newInstance();
            XWPFRelation relation = XWPFRelation.HEADER;
            int i = this.getRelationIndex(relation);
            XWPFHeader wrapper = (XWPFHeader)this.doc.createRelationship(relation, XWPFFactory.getInstance(), i);
            wrapper.setXWPFDocument(this.doc);
            CTHdrFtr hdr = this.buildHdr(type, wrapper, pars);
            wrapper.setHeaderFooter(hdr);
            hdrDoc.setHdr(hdr);
            this.assignHeader(wrapper, type);
            header = wrapper;
        } else if (pars != null) {
            int headerIndex = this.getHeaderIndex(type);
            XWPFParagraph paragraph = header.createParagraph();
            this.setWatermarkInParagraphHasHeader(paragraph, headerIndex);
        }
        return header;
    }

    public int getHeaderIndex(STHdrFtr.Enum type) {
        if (STHdrFtr.FIRST == type) {
            return 2;
        }
        if (STHdrFtr.EVEN == type) {
            return 3;
        }
        return 1;
    }

    public XWPFFooter createFooter(STHdrFtr.Enum type) {
        return this.createFooter(type, null);
    }

    public XWPFFooter createFooter(STHdrFtr.Enum type, XWPFParagraph[] pars) {
        XWPFFooter footer = this.getFooter(type);
        if (footer == null) {
            FtrDocument ftrDoc = FtrDocument.Factory.newInstance();
            XWPFRelation relation = XWPFRelation.FOOTER;
            int i = this.getRelationIndex(relation);
            XWPFFooter wrapper = (XWPFFooter)this.doc.createRelationship(relation, XWPFFactory.getInstance(), i);
            wrapper.setXWPFDocument(this.doc);
            CTHdrFtr ftr = this.buildFtr(type, wrapper, pars);
            wrapper.setHeaderFooter(ftr);
            ftrDoc.setFtr(ftr);
            this.assignFooter(wrapper, type);
            footer = wrapper;
        }
        return footer;
    }

    private int getRelationIndex(XWPFRelation relation) {
        int i = 1;
        for (POIXMLDocumentPart.RelationPart rp : this.doc.getRelationParts()) {
            if (!rp.getRelationship().getRelationshipType().equals(relation.getRelation())) continue;
            ++i;
        }
        return i;
    }

    private CTHdrFtr buildFtr(STHdrFtr.Enum type, XWPFHeaderFooter wrapper, XWPFParagraph[] pars) {
        CTHdrFtr ftr = this.buildHdrFtr(pars, wrapper);
        this.setFooterReference(type, wrapper);
        return ftr;
    }

    private CTHdrFtr buildHdr(STHdrFtr.Enum type, XWPFHeaderFooter wrapper, XWPFParagraph[] pars) {
        CTHdrFtr hdr = this.buildHdrFtr(pars, wrapper);
        this.setHeaderReference(type, wrapper);
        return hdr;
    }

    private CTHdrFtr buildHdrFtr(XWPFParagraph[] paragraphs, XWPFHeaderFooter wrapper) {
        CTHdrFtr ftr = wrapper._getHdrFtr();
        if (paragraphs != null) {
            for (int i = 0; i < paragraphs.length; ++i) {
                ftr.addNewP();
                ftr.setPArray(i, paragraphs[i].getCTP());
            }
        }
        return ftr;
    }

    private void setFooterReference(STHdrFtr.Enum type, XWPFHeaderFooter wrapper) {
        CTHdrFtrRef ref = this.doc.getDocument().getBody().getSectPr().addNewFooterReference();
        ref.setType(type);
        ref.setId(this.doc.getRelationId(wrapper));
    }

    private void setHeaderReference(STHdrFtr.Enum type, XWPFHeaderFooter wrapper) {
        CTHdrFtrRef ref = this.doc.getDocument().getBody().getSectPr().addNewHeaderReference();
        ref.setType(type);
        ref.setId(this.doc.getRelationId(wrapper));
    }

    public XWPFHeader getFirstPageHeader() {
        return this.firstPageHeader;
    }

    public XWPFFooter getFirstPageFooter() {
        return this.firstPageFooter;
    }

    public XWPFHeader getOddPageHeader() {
        return this.defaultHeader;
    }

    public XWPFFooter getOddPageFooter() {
        return this.defaultFooter;
    }

    public XWPFHeader getEvenPageHeader() {
        return this.evenPageHeader;
    }

    public XWPFFooter getEvenPageFooter() {
        return this.evenPageFooter;
    }

    public XWPFHeader getDefaultHeader() {
        return this.defaultHeader;
    }

    public XWPFFooter getDefaultFooter() {
        return this.defaultFooter;
    }

    public XWPFHeader getHeader(int pageNumber) {
        if (pageNumber == 1 && this.firstPageHeader != null) {
            return this.firstPageHeader;
        }
        if (pageNumber % 2 == 0 && this.evenPageHeader != null) {
            return this.evenPageHeader;
        }
        return this.defaultHeader;
    }

    public XWPFHeader getHeader(STHdrFtr.Enum type) {
        if (type == STHdrFtr.EVEN) {
            return this.evenPageHeader;
        }
        if (type == STHdrFtr.FIRST) {
            return this.firstPageHeader;
        }
        return this.defaultHeader;
    }

    public XWPFFooter getFooter(int pageNumber) {
        if (pageNumber == 1 && this.firstPageFooter != null) {
            return this.firstPageFooter;
        }
        if (pageNumber % 2 == 0 && this.evenPageFooter != null) {
            return this.evenPageFooter;
        }
        return this.defaultFooter;
    }

    public XWPFFooter getFooter(STHdrFtr.Enum type) {
        if (type == STHdrFtr.EVEN) {
            return this.evenPageFooter;
        }
        if (type == STHdrFtr.FIRST) {
            return this.firstPageFooter;
        }
        return this.defaultFooter;
    }

    public void createWatermark(PrintStyle.Watermark watermark) {
        if (watermark == null || StringUtils.isEmpty((CharSequence)watermark.getType())) {
            return;
        }
        this.content = watermark.getContent() == null ? "" : watermark.getContent();
        String string = this.paperWidth = watermark.getPaperWidth() == null ? WATERMARK_DEFAULT_PAPERWIDHT : watermark.getPaperWidth();
        if ("picture".equals(watermark.getType())) {
            try {
                this.title = StringUtils.isEmpty((CharSequence)watermark.getTitle()) ? "" : watermark.getTitle().replaceAll("(.*)?\\..*", "$1");
                this.zoom = StringUtils.isEmpty((CharSequence)watermark.getZoom()) ? WATERMARK_DEFAULT_ZOOM : watermark.getZoom();
                this.erosion = watermark.getErosion() == null ? true : watermark.getErosion();
                Base64.Decoder decoder = Base64.getDecoder();
                byte[] pictureData = decoder.decode(this.content.replaceAll("data:image/.*?;base64,(.*)", "$1"));
                XWPFParagraph[] pars = new XWPFParagraph[1];
                XWPFHeader header = null;
                pars[0] = new XWPFParagraph(CTP.Factory.newInstance(), this.doc);
                header = this.createHeader(DEFAULT, pars);
                String relationId = header.addPictureData(pictureData, Document.PICTURE_TYPE_PNG);
                this.getPictureWatermarkParagraph(header.getParagraphArray(0).getCTP(), relationId, pictureData, 1);
                byte[] rsidR = header.getParagraphArray(0).getCTP().getRsidR();
                byte[] rsidRDefault = header.getParagraphArray(0).getCTP().getRsidRDefault();
                CTSettings settings = this.doc.getCTSettings(this.doc);
                CTDocRsids docRsids = settings.isSetRsids() ? settings.getRsids() : settings.addNewRsids();
                docRsids.addNewRsidRoot().setVal(rsidR);
                docRsids.addNewRsid().setVal(rsidR);
                docRsids.addNewRsid().setVal(rsidRDefault);
                pars[0] = new XWPFParagraph(CTP.Factory.newInstance(), this.doc);
                header = this.createHeader(FIRST, pars);
                header.addPictureData(pictureData, Document.PICTURE_TYPE_PNG);
                this.getPictureWatermarkParagraph(header.getParagraphArray(0).getCTP(), this.getRelationId(header), pictureData, 2);
                pars[0] = new XWPFParagraph(CTP.Factory.newInstance(), this.doc);
                header = this.createHeader(EVEN, pars);
                header.addPictureData(pictureData, Document.PICTURE_TYPE_PNG);
                this.getPictureWatermarkParagraph(header.getParagraphArray(0).getCTP(), this.getRelationId(header), pictureData, 3);
            }
            catch (InvalidFormatException e) {
                log.error(e.getMessage(), e);
            }
        } else {
            this.fontfamily = StringUtils.isEmpty((CharSequence)watermark.getFontfamily()) ? WATERMARK_DEFAULT_FONTFAMILY : watermark.getFontfamily();
            this.fontsize = StringUtils.isEmpty((CharSequence)watermark.getFontsize()) ? WATERMARK_DEFAULT_FONTSIZE : watermark.getFontsize();
            this.color = StringUtils.isEmpty((CharSequence)watermark.getColor()) ? WATERMARK_DEFAULT_COLOR : watermark.getColor();
            this.translucent = watermark.getTranslucent() == null ? true : watermark.getTranslucent();
            this.format = StringUtils.isEmpty((CharSequence)watermark.getFormat()) ? WATERMARK_DEFAULT_FORMAT : watermark.getFormat();
            XWPFParagraph[] pars = new XWPFParagraph[]{this.getWordWatermarkParagraph(this.content, 1)};
            this.createHeader(DEFAULT, pars);
            byte[] rsidR = pars[0].getCTP().getRsidR();
            byte[] rsidRDefault = pars[0].getCTP().getRsidRDefault();
            CTSettings settings = this.doc.getCTSettings(this.doc);
            CTDocRsids docRsids = settings.isSetRsids() ? settings.getRsids() : settings.addNewRsids();
            docRsids.addNewRsidRoot().setVal(rsidR);
            docRsids.addNewRsid().setVal(rsidR);
            docRsids.addNewRsid().setVal(rsidRDefault);
            pars[0] = this.getWordWatermarkParagraph(this.content, 2);
            this.createHeader(FIRST, pars);
            pars[0] = this.getWordWatermarkParagraph(this.content, 3);
            this.createHeader(EVEN, pars);
        }
    }

    private void setWatermarkInParagraphHasHeader(XWPFParagraph paragraph, int idx) {
        CTString pStyle;
        CTP p = paragraph.getCTP();
        XWPFDocument doc = paragraph.getDocument();
        CTBody ctBody = doc.getDocument().getBody();
        byte[] rsidr = null;
        byte[] rsidrdefault = null;
        if (ctBody.sizeOfPArray() != 0) {
            CTP ctp = ctBody.getPArray(0);
            rsidr = ctp.getRsidR();
            rsidrdefault = ctp.getRsidRDefault();
        }
        p.setRsidP(rsidr);
        p.setRsidRDefault(rsidrdefault);
        CTPPr pPr = p.getPPr();
        if (pPr == null) {
            pPr = p.addNewPPr();
        }
        if ((pStyle = pPr.getPStyle()) == null) {
            pStyle = pPr.addNewPStyle();
        }
        pStyle.setVal("Header");
        CTR r = p.addNewR();
        CTRPr rPr = r.addNewRPr();
        rPr.addNewNoProof();
        CTPicture pict = r.addNewPict();
        CTGroup group = CTGroup.Factory.newInstance();
        CTShapetype shapetype = group.addNewShapetype();
        shapetype.setId("_x0000_t75");
        shapetype.setCoordsize("21600,21600");
        shapetype.setSpt(136.0f);
        shapetype.setAdj("10800");
        shapetype.setPath2("m@7,0l@8,0m@5,21600l@6,21600e");
        CTFormulas formulas = shapetype.addNewFormulas();
        formulas.addNewF().setEqn("sum #0 0 10800");
        formulas.addNewF().setEqn("prod #0 2 1");
        formulas.addNewF().setEqn("sum 21600 0 @1");
        formulas.addNewF().setEqn("sum 0 0 @2");
        formulas.addNewF().setEqn("sum 21600 0 @3");
        formulas.addNewF().setEqn("if @0 @3 0");
        formulas.addNewF().setEqn("if @0 21600 @1");
        formulas.addNewF().setEqn("if @0 0 @2");
        formulas.addNewF().setEqn("if @0 @4 21600");
        formulas.addNewF().setEqn("mid @5 @6");
        formulas.addNewF().setEqn("mid @8 @5");
        formulas.addNewF().setEqn("mid @7 @8");
        formulas.addNewF().setEqn("mid @6 @7");
        formulas.addNewF().setEqn("sum @6 0 @5");
        CTPath path = shapetype.addNewPath();
        path.setTextpathok(STTrueFalse.T);
        path.setConnecttype(STConnectType.CUSTOM);
        path.setConnectlocs("@9,0;@10,10800;@11,21600;@12,10800");
        path.setConnectangles("270,180,90,0");
        CTTextPath shapeTypeTextPath = shapetype.addNewTextpath();
        shapeTypeTextPath.setOn(STTrueFalse.T);
        shapeTypeTextPath.setFitshape(STTrueFalse.T);
        CTHandles handles = shapetype.addNewHandles();
        CTH h = handles.addNewH();
        h.setPosition("#0,bottomRight");
        h.setXrange("6629,14971");
        CTLock lock = shapetype.addNewLock();
        lock.setExt(STExt.EDIT);
        CTShape shape = group.addNewShape();
        shape.setId("PowerPlusWaterMarkObject" + idx);
        shape.setSpid("_x0000_s102" + (4 + idx));
        shape.setType("#_x0000_t136");
        NumberFormat nf = NumberFormat.getIntegerInstance();
        int fontsize = 96;
        int paperWidth = 0;
        try {
            fontsize = nf.parse(this.fontsize).intValue();
            paperWidth = nf.parse(this.paperWidth).intValue();
        }
        catch (ParseException e) {
            log.error(e.getMessage(), e);
        }
        long width = this.content.length() * fontsize;
        if ((double)width > (double)(paperWidth / 254 * 72 * 2) * Math.cos(Math.toDegrees(45.0))) {
            width = width * 4L / 5L;
        }
        width = width > 1584L ? 1584L : width;
        shape.setStyle("position:absolute;margin-left:0;margin-top:0;width:" + width + "pt;height:" + this.fontsize + "pt;rotation:" + (WATERMARK_DEFAULT_FORMAT.equals(this.format) ? 0 : 315) + ";z-index:-251654144;mso-wrap-edited:f;mso-position-horizontal:center;mso-position-horizontal-relative:margin;mso-position-vertical:center;mso-position-vertical-relative:margin");
        shape.setWrapcoords("616 5068 390 16297 39 16921 -39 17155 7265 17545 7186 17467 -39 17467 18904 17467 10507 17467 8710 17545 18904 17077 18787 16843 18358 16297 18279 12554 19178 12476 20701 11774 20779 11228 21131 10059 21248 8811 21248 7563 20975 6316 20935 5380 19490 5146 14022 5068 2616 5068");
        shape.setFillcolor(this.color);
        shape.setStroked(STTrueFalse.FALSE);
        CTTextPath shapeTextPath = shape.addNewTextpath();
        shapeTextPath.setStyle("font-family:" + FontFamily.checkFontFamily((String)this.fontfamily, (FontFamily[])new FontFamily[0]).getValue() + ";font-size:" + this.fontsize + "pt;");
        shapeTextPath.setString(this.content);
        CTFill till = shape.addNewFill();
        if (this.translucent.booleanValue()) {
            till.setOpacity(".5");
        } else {
            till.setOpacity("1");
        }
        CTWrap wrap = shape.addNewWrap();
        wrap.setAnchorx(STHorizontalAnchor.MARGIN);
        wrap.setAnchory(STVerticalAnchor.MARGIN);
        pict.set(group);
    }

    public void createWatermark(Map<String, Object> watermark) {
        this.type = watermark.get("type") == null ? "none" : watermark.get("type").toString();
        this.content = watermark.get("content") == null ? "" : watermark.get("content").toString();
        String string = this.paperWidth = watermark.get("paperWidth") == null ? WATERMARK_DEFAULT_PAPERWIDHT : watermark.get("paperWidth").toString();
        if ("picture".equals(this.type)) {
            try {
                this.title = watermark.get("title") == null ? "" : watermark.get("title").toString().replaceAll("(.*)?\\..*", "$1");
                this.zoom = watermark.get("zoom") == null ? WATERMARK_DEFAULT_ZOOM : watermark.get("zoom").toString();
                this.erosion = watermark.get("erosion") == null ? true : Boolean.parseBoolean((String)watermark.get("erosion"));
                Base64.Decoder decoder = Base64.getDecoder();
                byte[] pictureData = decoder.decode(this.content.replaceAll("data:image/.*?;base64,(.*)", "$1"));
                XWPFParagraph[] pars = new XWPFParagraph[1];
                XWPFHeader header = null;
                pars[0] = new XWPFParagraph(CTP.Factory.newInstance(), this.doc);
                header = this.createHeader(DEFAULT, pars);
                String relationId = header.addPictureData(pictureData, Document.PICTURE_TYPE_PNG);
                this.getPictureWatermarkParagraph(header.getParagraphArray(0).getCTP(), relationId, pictureData, 1);
                byte[] rsidR = header.getParagraphArray(0).getCTP().getRsidR();
                byte[] rsidRDefault = header.getParagraphArray(0).getCTP().getRsidRDefault();
                CTSettings settings = this.doc.getCTSettings(this.doc);
                CTDocRsids docRsids = settings.isSetRsids() ? settings.getRsids() : settings.addNewRsids();
                docRsids.addNewRsidRoot().setVal(rsidR);
                docRsids.addNewRsid().setVal(rsidR);
                docRsids.addNewRsid().setVal(rsidRDefault);
                pars[0] = new XWPFParagraph(CTP.Factory.newInstance(), this.doc);
                header = this.createHeader(FIRST, pars);
                header.addPictureData(pictureData, Document.PICTURE_TYPE_PNG);
                this.getPictureWatermarkParagraph(header.getParagraphArray(0).getCTP(), this.getRelationId(header), pictureData, 2);
                pars[0] = new XWPFParagraph(CTP.Factory.newInstance(), this.doc);
                header = this.createHeader(EVEN, pars);
                header.addPictureData(pictureData, Document.PICTURE_TYPE_PNG);
                this.getPictureWatermarkParagraph(header.getParagraphArray(0).getCTP(), this.getRelationId(header), pictureData, 3);
            }
            catch (InvalidFormatException e) {
                log.error(e.getMessage(), e);
            }
        } else {
            this.fontfamily = watermark.get("fontfamily") == null ? WATERMARK_DEFAULT_FONTFAMILY : watermark.get("fontfamily").toString();
            this.fontsize = watermark.get("fontsize") == null ? WATERMARK_DEFAULT_FONTSIZE : watermark.get("fontsize").toString();
            this.color = watermark.get("color") == null ? WATERMARK_DEFAULT_COLOR : watermark.get("color").toString();
            this.translucent = watermark.get("translucent") == null ? true : Boolean.parseBoolean((String)watermark.get("translucent"));
            this.format = watermark.get("format") == null ? WATERMARK_DEFAULT_FORMAT : watermark.get("format").toString();
            XWPFParagraph[] pars = new XWPFParagraph[]{this.getWordWatermarkParagraph(this.content, 1)};
            this.createHeader(DEFAULT, pars);
            byte[] rsidR = pars[0].getCTP().getRsidR();
            byte[] rsidRDefault = pars[0].getCTP().getRsidRDefault();
            CTSettings settings = this.doc.getCTSettings(this.doc);
            CTDocRsids docRsids = settings.isSetRsids() ? settings.getRsids() : settings.addNewRsids();
            docRsids.addNewRsidRoot().setVal(rsidR);
            docRsids.addNewRsid().setVal(rsidR);
            docRsids.addNewRsid().setVal(rsidRDefault);
            pars[0] = this.getWordWatermarkParagraph(this.content, 2);
            this.createHeader(FIRST, pars);
            pars[0] = this.getWordWatermarkParagraph(this.content, 3);
            this.createHeader(EVEN, pars);
        }
    }

    private void getPictureWatermarkParagraph(CTP p, String rID, byte[] pictureData, int idx) {
        float iWidth = 0.0f;
        float iHeight = 0.0f;
        try {
            float zoom;
            ImageData imageData = ImageDataFactory.create((byte[])pictureData);
            iWidth = imageData.getWidth();
            iHeight = imageData.getHeight();
            if ("auto".equals(this.zoom)) {
                NumberFormat nf = NumberFormat.getNumberInstance();
                Number m = nf.parse(this.paperWidth);
                float pageWidth = m.floatValue();
                zoom = pageWidth / iWidth;
                iWidth = pageWidth;
                iHeight *= zoom;
            } else if (this.zoom != null && this.zoom.contains("%")) {
                String percentValue = this.zoom.replace("%", "").trim();
                NumberFormat nf = NumberFormat.getNumberInstance();
                Number m = nf.parse(percentValue);
                zoom = m.floatValue() / 100.0f;
                iWidth *= zoom;
                iHeight *= zoom;
            }
        }
        catch (Exception e) {
            log.error("\u83b7\u53d6\u56fe\u7247\u5927\u5c0f\u5f02\u5e38", (Object)e.getMessage(), (Object)e);
        }
        byte[] rsidr = this.randomRsidr();
        byte[] rsidrdefault = this.randomRsidr();
        p.setRsidR(rsidr);
        p.setRsidRDefault(rsidrdefault);
        CTPPr pPr = p.addNewPPr();
        pPr.addNewPStyle().setVal("Header");
        CTR r = p.addNewR();
        CTRPr rPr = r.addNewRPr();
        rPr.addNewNoProof();
        CTPicture pict = r.addNewPict();
        CTGroup group = CTGroup.Factory.newInstance();
        CTShapetype shapetype = group.addNewShapetype();
        shapetype.setId("_x0000_t136");
        shapetype.setCoordsize("21600,21600");
        shapetype.setFilled(STTrueFalse.F);
        shapetype.setStroked(STTrueFalse.F);
        shapetype.setSpt(75.0f);
        shapetype.setPreferrelative(STTrueFalse.T);
        shapetype.setPath2("m@4@5l@4@11@9@11@9@5xe");
        CTFormulas formulas = shapetype.addNewFormulas();
        formulas.addNewF().setEqn("if lineDrawn pixelLineWidth 0");
        formulas.addNewF().setEqn("sum @0 1 0");
        formulas.addNewF().setEqn("sum 0 0 @1");
        formulas.addNewF().setEqn("prod @2 1 2");
        formulas.addNewF().setEqn("prod @3 21600 pixelWidth");
        formulas.addNewF().setEqn("prod @3 21600 pixelHeight");
        formulas.addNewF().setEqn("sum @0 0 1");
        formulas.addNewF().setEqn("prod @6 1 2");
        formulas.addNewF().setEqn("prod @7 21600 pixelWidth");
        formulas.addNewF().setEqn("sum @8 21600 0");
        formulas.addNewF().setEqn("prod @7 21600 pixelHeight");
        formulas.addNewF().setEqn("sum @10 21600 0");
        CTPath path = shapetype.addNewPath();
        path.setGradientshapeok(STTrueFalse.T);
        path.setConnecttype(STConnectType.RECT);
        path.setExtrusionok(STTrueFalse.T);
        CTLock lock = shapetype.addNewLock();
        lock.setExt(STExt.EDIT);
        lock.setAspectratio(STTrueFalse.T);
        CTShape shape = group.addNewShape();
        shape.setId("WordPictureWatermark" + idx);
        shape.setSpid("_x0000_s102" + (4 + idx));
        shape.setType("#_x0000_t75");
        shape.setStyle("position:absolute;left:0;text-align:left;margin-left:0;margin-top:0;width:" + iWidth + "pt;height:" + iHeight + "pt;z-index:-251657216;mso-position-horizontal:center;mso-position-horizontal-relative:margin;mso-position-vertical:center;mso-position-vertical-relative:margin");
        shape.setAllowincell(STTrueFalse.F);
        CTImageData imageData = shape.addNewImagedata();
        imageData.setId2(rID);
        if (this.erosion.booleanValue()) {
            imageData.setGain("19661f");
            imageData.setBlacklevel("22938f");
        }
        imageData.setTitle(this.title);
        pict.set(group);
    }

    private XWPFParagraph getWordWatermarkParagraph(String text, int idx) {
        CTP p = CTP.Factory.newInstance();
        byte[] rsidr = this.randomRsidr();
        byte[] rsidrdefault = this.randomRsidr();
        p.setRsidP(rsidr);
        p.setRsidRDefault(rsidrdefault);
        CTPPr pPr = p.addNewPPr();
        pPr.addNewPStyle().setVal("Header");
        CTR r = p.addNewR();
        CTRPr rPr = r.addNewRPr();
        rPr.addNewNoProof();
        CTPicture pict = r.addNewPict();
        CTGroup group = CTGroup.Factory.newInstance();
        CTShapetype shapetype = group.addNewShapetype();
        shapetype.setId("_x0000_t75");
        shapetype.setCoordsize("21600,21600");
        shapetype.setSpt(136.0f);
        shapetype.setAdj("10800");
        shapetype.setPath2("m@7,0l@8,0m@5,21600l@6,21600e");
        CTFormulas formulas = shapetype.addNewFormulas();
        formulas.addNewF().setEqn("sum #0 0 10800");
        formulas.addNewF().setEqn("prod #0 2 1");
        formulas.addNewF().setEqn("sum 21600 0 @1");
        formulas.addNewF().setEqn("sum 0 0 @2");
        formulas.addNewF().setEqn("sum 21600 0 @3");
        formulas.addNewF().setEqn("if @0 @3 0");
        formulas.addNewF().setEqn("if @0 21600 @1");
        formulas.addNewF().setEqn("if @0 0 @2");
        formulas.addNewF().setEqn("if @0 @4 21600");
        formulas.addNewF().setEqn("mid @5 @6");
        formulas.addNewF().setEqn("mid @8 @5");
        formulas.addNewF().setEqn("mid @7 @8");
        formulas.addNewF().setEqn("mid @6 @7");
        formulas.addNewF().setEqn("sum @6 0 @5");
        CTPath path = shapetype.addNewPath();
        path.setTextpathok(STTrueFalse.T);
        path.setConnecttype(STConnectType.CUSTOM);
        path.setConnectlocs("@9,0;@10,10800;@11,21600;@12,10800");
        path.setConnectangles("270,180,90,0");
        CTTextPath shapeTypeTextPath = shapetype.addNewTextpath();
        shapeTypeTextPath.setOn(STTrueFalse.T);
        shapeTypeTextPath.setFitshape(STTrueFalse.T);
        CTHandles handles = shapetype.addNewHandles();
        CTH h = handles.addNewH();
        h.setPosition("#0,bottomRight");
        h.setXrange("6629,14971");
        CTLock lock = shapetype.addNewLock();
        lock.setExt(STExt.EDIT);
        CTShape shape = group.addNewShape();
        shape.setId("PowerPlusWaterMarkObject" + idx);
        shape.setSpid("_x0000_s102" + (4 + idx));
        shape.setType("#_x0000_t136");
        NumberFormat nf = NumberFormat.getIntegerInstance();
        int fontsize = 96;
        int paperWidth = 0;
        try {
            fontsize = nf.parse(this.fontsize).intValue();
            paperWidth = nf.parse(this.paperWidth).intValue();
        }
        catch (ParseException e) {
            log.error(e.getMessage(), e);
        }
        long width = text.length() * fontsize;
        if ((double)width > (double)(paperWidth / 254 * 72 * 2) * Math.cos(Math.toDegrees(45.0))) {
            width = width * 4L / 5L;
        }
        width = width > 1584L ? 1584L : width;
        shape.setStyle("position:absolute;margin-left:0;margin-top:0;width:" + width + "pt;height:" + this.fontsize + "pt;rotation:" + (WATERMARK_DEFAULT_FORMAT.equals(this.format) ? 0 : 315) + ";z-index:-251654144;mso-wrap-edited:f;mso-position-horizontal:center;mso-position-horizontal-relative:margin;mso-position-vertical:center;mso-position-vertical-relative:margin");
        shape.setWrapcoords("616 5068 390 16297 39 16921 -39 17155 7265 17545 7186 17467 -39 17467 18904 17467 10507 17467 8710 17545 18904 17077 18787 16843 18358 16297 18279 12554 19178 12476 20701 11774 20779 11228 21131 10059 21248 8811 21248 7563 20975 6316 20935 5380 19490 5146 14022 5068 2616 5068");
        shape.setFillcolor(this.color);
        shape.setStroked(STTrueFalse.FALSE);
        CTTextPath shapeTextPath = shape.addNewTextpath();
        shapeTextPath.setStyle("font-family:" + FontFamily.checkFontFamily((String)this.fontfamily, (FontFamily[])new FontFamily[0]).getValue() + ";font-size:" + this.fontsize + "pt;");
        shapeTextPath.setString(text);
        CTFill till = shape.addNewFill();
        if (this.translucent.booleanValue()) {
            till.setOpacity(".5");
        } else {
            till.setOpacity("1");
        }
        CTWrap wrap = shape.addNewWrap();
        wrap.setAnchorx(STHorizontalAnchor.MARGIN);
        wrap.setAnchory(STVerticalAnchor.MARGIN);
        pict.set(group);
        return new XWPFParagraph(p, this.doc);
    }

    private byte[] randomRsidr() {
        byte[] result = new byte[4];
        try {
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            for (int i = 0; i < 4; ++i) {
                result[i] = (byte)((byte)Math.round(random.nextDouble() * 16.0) << 4 | (byte)Math.round(Math.random() * 16.0));
            }
            return result;
        }
        catch (NoSuchAlgorithmException e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    public String getRelationId(XWPFHeader header) {
        Iterator<XWPFPictureData> iterator = header.getAllPackagePictures().iterator();
        if (iterator.hasNext()) {
            XWPFPictureData pictureData = iterator.next();
            return header.getRelationId(pictureData);
        }
        return null;
    }
}

