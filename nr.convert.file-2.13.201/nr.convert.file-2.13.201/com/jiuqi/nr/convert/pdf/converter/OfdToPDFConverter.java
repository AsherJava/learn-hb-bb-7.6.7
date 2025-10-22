/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.office.excel.spire.SpireHelper
 *  com.spire.pdf.conversion.OfdConverter
 */
package com.jiuqi.nr.convert.pdf.converter;

import com.jiuqi.bi.office.excel.spire.SpireHelper;
import com.jiuqi.nr.convert.pdf.converter.Converter;
import com.spire.pdf.conversion.OfdConverter;
import java.io.InputStream;
import java.io.OutputStream;

public class OfdToPDFConverter
extends Converter {
    @Override
    public void convert() throws Exception {
        this.loading();
        SpireHelper.loadSpireLicence();
        OfdConverter converter = new OfdConverter(this.inStream);
        this.processing();
        converter.toPdf(this.outStream);
        this.finished();
    }

    public OfdToPDFConverter(InputStream inStream, OutputStream outStream, boolean showMessages, boolean closeStreamsWhenComplete) {
        super(inStream, outStream, showMessages, closeStreamsWhenComplete);
    }
}

