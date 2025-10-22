/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.analysisreport.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.lang.reflect.Field;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFSettings;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlToken;
import org.openxmlformats.schemas.drawingml.x2006.main.CTNonVisualDrawingProps;
import org.openxmlformats.schemas.drawingml.x2006.main.CTPositiveSize2D;
import org.openxmlformats.schemas.drawingml.x2006.wordprocessingDrawing.CTInline;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSettings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomXWPFDocument
extends XWPFDocument
implements Serializable {
    private static final Logger log = LoggerFactory.getLogger(CustomXWPFDocument.class);
    transient InputStream in;
    private static final long serialVersionUID = 242209821967626779L;

    public CustomXWPFDocument(InputStream in) throws IOException {
        super(in);
        this.in = in;
    }

    public void drop() {
        if (this.in != null) {
            try {
                this.in.close();
            }
            catch (IOException iOException) {
                // empty catch block
            }
        }
    }

    public CustomXWPFDocument() {
    }

    public CustomXWPFDocument(OPCPackage pkg) throws IOException {
        super(pkg);
    }

    public XWPFSettings getSettings(CustomXWPFDocument document) {
        try {
            Class<?> clazz = this.getClass().getSuperclass();
            Field field = clazz.getDeclaredField("settings");
            field.setAccessible(true);
            return (XWPFSettings)field.get(document);
        }
        catch (NoSuchFieldException e) {
            log.error(e.getMessage(), e);
        }
        catch (SecurityException e) {
            log.error(e.getMessage(), e);
        }
        catch (IllegalArgumentException e) {
            log.error(e.getMessage(), e);
        }
        catch (IllegalAccessException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    public CTSettings getCTSettings(CustomXWPFDocument document) {
        try {
            XWPFSettings settings = this.getSettings(document);
            Class<?> clazz = settings.getClass();
            Field field = clazz.getDeclaredField("ctSettings");
            field.setAccessible(true);
            return (CTSettings)field.get(settings);
        }
        catch (NoSuchFieldException e) {
            log.error(e.getMessage(), e);
        }
        catch (SecurityException e) {
            log.error(e.getMessage(), e);
        }
        catch (IllegalArgumentException e) {
            log.error(e.getMessage(), e);
        }
        catch (IllegalAccessException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    public void createPicture(String blipId, int ind, int width, int height, XWPFParagraph paragraph) {
        int EMU = 9525;
        CTInline inline = paragraph.createRun().getCTR().addNewDrawing().addNewInline();
        String picXml = "<a:graphic xmlns:a=\"http://schemas.openxmlformats.org/drawingml/2006/main\">   <a:graphicData uri=\"http://schemas.openxmlformats.org/drawingml/2006/picture\">      <pic:pic xmlns:pic=\"http://schemas.openxmlformats.org/drawingml/2006/picture\">         <pic:nvPicPr>            <pic:cNvPr id=\"" + ind + "\" name=\"Generated\"/>            <pic:cNvPicPr/>         </pic:nvPicPr>         <pic:blipFill>            <a:blip r:embed=\"" + blipId + "\" xmlns:r=\"http://schemas.openxmlformats.org/officeDocument/2006/relationships\"/>            <a:stretch>               <a:fillRect/>            </a:stretch>         </pic:blipFill>         <pic:spPr>            <a:xfrm>               <a:off x=\"0\" y=\"0\"/>               <a:ext cx=\"" + (width *= 9525) + "\" cy=\"" + (height *= 9525) + "\"/>            </a:xfrm>            <a:prstGeom prst=\"rect\">               <a:avLst/>            </a:prstGeom>         </pic:spPr>      </pic:pic>   </a:graphicData></a:graphic>";
        inline.addNewGraphic().addNewGraphicData();
        XmlToken xmlToken = null;
        try {
            xmlToken = XmlToken.Factory.parse(picXml);
        }
        catch (XmlException xe) {
            log.error(xe.getMessage(), xe);
        }
        inline.set(xmlToken);
        inline.setDistT(0L);
        inline.setDistB(0L);
        inline.setDistL(0L);
        inline.setDistR(0L);
        CTPositiveSize2D extent = inline.addNewExtent();
        extent.setCx(width);
        extent.setCy(height);
        CTNonVisualDrawingProps docPr = inline.addNewDocPr();
        docPr.setId(ind);
        docPr.setName("\u56fe\u7247" + ind);
        docPr.setDescr("\u6d4b\u8bd5");
    }

    public void createPictureUnitCm(String blipId, int ind, int width, int height, XWPFParagraph paragraph) {
        CTInline inline = paragraph.createRun().getCTR().addNewDrawing().addNewInline();
        String picXml = "<a:graphic xmlns:a=\"http://schemas.openxmlformats.org/drawingml/2006/main\">   <a:graphicData uri=\"http://schemas.openxmlformats.org/drawingml/2006/picture\">      <pic:pic xmlns:pic=\"http://schemas.openxmlformats.org/drawingml/2006/picture\">         <pic:nvPicPr>            <pic:cNvPr id=\"" + ind + "\" name=\"Generated\"/>            <pic:cNvPicPr/>         </pic:nvPicPr>         <pic:blipFill>            <a:blip r:embed=\"" + blipId + "\" xmlns:r=\"http://schemas.openxmlformats.org/officeDocument/2006/relationships\"/>            <a:stretch>               <a:fillRect/>            </a:stretch>         </pic:blipFill>         <pic:spPr>            <a:xfrm>               <a:off x=\"0\" y=\"0\"/>               <a:ext cx=\"" + width + "\" cy=\"" + height + "\"/>            </a:xfrm>            <a:prstGeom prst=\"rect\">               <a:avLst/>            </a:prstGeom>         </pic:spPr>      </pic:pic>   </a:graphicData></a:graphic>";
        inline.addNewGraphic().addNewGraphicData();
        XmlToken xmlToken = null;
        try {
            xmlToken = XmlToken.Factory.parse(picXml);
        }
        catch (XmlException xe) {
            log.error(xe.getMessage(), xe);
        }
        inline.set(xmlToken);
        inline.setDistT(0L);
        inline.setDistB(0L);
        inline.setDistL(0L);
        inline.setDistR(0L);
        CTPositiveSize2D extent = inline.addNewExtent();
        extent.setCx(width);
        extent.setCy(height);
        CTNonVisualDrawingProps docPr = inline.addNewDocPr();
        docPr.setId(ind);
        docPr.setName("\u56fe\u7247" + ind);
    }
}

