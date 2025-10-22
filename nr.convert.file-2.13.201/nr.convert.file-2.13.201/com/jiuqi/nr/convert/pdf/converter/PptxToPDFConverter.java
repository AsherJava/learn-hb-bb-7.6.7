/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.office.excel.spire.SpireHelper
 *  com.spire.presentation.FileFormat
 *  com.spire.presentation.Presentation
 */
package com.jiuqi.nr.convert.pdf.converter;

import com.jiuqi.bi.office.excel.spire.SpireHelper;
import com.jiuqi.nr.convert.pdf.converter.Converter;
import com.spire.presentation.FileFormat;
import com.spire.presentation.Presentation;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFSlide;

public class PptxToPDFConverter
extends Converter {
    private XSLFSlide[] slides;

    public PptxToPDFConverter(InputStream inStream, OutputStream outStream, boolean showMessages, boolean closeStreamsWhenComplete) {
        super(inStream, outStream, showMessages, closeStreamsWhenComplete);
    }

    @Override
    public void convert() throws Exception {
        this.loading();
        SpireHelper.loadSpireLicence();
        Presentation ppt = new Presentation();
        ppt.loadFromStream(this.inStream, FileFormat.AUTO);
        this.processing();
        ppt.saveToFile(this.outStream, FileFormat.PDF);
        this.finished();
    }

    protected Dimension processSlides() throws IOException {
        InputStream iStream = this.inStream;
        XMLSlideShow ppt = new XMLSlideShow(iStream);
        Dimension dimension = ppt.getPageSize();
        return dimension;
    }

    protected int getNumSlides() {
        return this.slides.length;
    }

    protected void drawOntoThisGraphic(int index, Graphics2D graphics) {
        this.slides[index].draw(graphics);
    }

    protected Color getSlideBGColor(int index) {
        return this.slides[index].getBackground().getFillColor();
    }
}

