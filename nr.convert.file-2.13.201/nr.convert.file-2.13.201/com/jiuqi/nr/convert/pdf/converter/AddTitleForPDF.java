/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.office.excel.spire.SpireHelper
 *  com.spire.pdf.PdfDocument
 */
package com.jiuqi.nr.convert.pdf.converter;

import com.jiuqi.bi.office.excel.spire.SpireHelper;
import com.jiuqi.nr.convert.pdf.converter.Converter;
import com.spire.pdf.PdfDocument;
import java.io.InputStream;
import java.io.OutputStream;

public class AddTitleForPDF
extends Converter {
    public AddTitleForPDF(InputStream inStream, OutputStream outStream, boolean showMessages, boolean closeStreamsWhenComplete) {
        super(inStream, outStream, showMessages, closeStreamsWhenComplete);
    }

    @Override
    public void convert() throws Exception {
    }

    @Override
    public void convert(String fileTitle) throws Exception {
        this.loading();
        SpireHelper.loadSpireLicence();
        PdfDocument pdf = new PdfDocument();
        pdf.loadFromStream(this.inStream);
        pdf.getDocumentInformation().setTitle(fileTitle);
        this.processing();
        pdf.saveToStream(this.outStream);
        this.finished();
    }
}

