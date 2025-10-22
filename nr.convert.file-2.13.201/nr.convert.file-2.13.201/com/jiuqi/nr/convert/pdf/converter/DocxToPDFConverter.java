/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.office.excel.spire.SpireHelper
 *  com.spire.doc.Document
 *  com.spire.doc.FileFormat
 */
package com.jiuqi.nr.convert.pdf.converter;

import com.jiuqi.bi.office.excel.spire.SpireHelper;
import com.jiuqi.nr.convert.pdf.converter.Converter;
import com.spire.doc.Document;
import com.spire.doc.FileFormat;
import java.io.InputStream;
import java.io.OutputStream;

public class DocxToPDFConverter
extends Converter {
    public DocxToPDFConverter(InputStream inStream, OutputStream outStream, boolean showMessages, boolean closeStreamsWhenComplete) {
        super(inStream, outStream, showMessages, closeStreamsWhenComplete);
    }

    @Override
    public void convert() throws Exception {
        this.loading();
        SpireHelper.loadSpireLicence();
        Document document = new Document();
        document.loadFromStream(this.inStream, FileFormat.Auto);
        this.processing();
        document.saveToStream(this.outStream, FileFormat.PDF);
        this.finished();
    }

    @Override
    public void convert(String fileTitle) throws Exception {
        this.loading();
        SpireHelper.loadSpireLicence();
        Document document = new Document();
        document.loadFromStream(this.inStream, FileFormat.Auto);
        document.getBuiltinDocumentProperties().setTitle(fileTitle);
        this.processing();
        document.saveToStream(this.outStream, FileFormat.PDF);
        this.finished();
    }
}

