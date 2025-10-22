/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.convert.pdf.converter;

import com.jiuqi.nr.convert.pdf.converter.Converter;
import java.io.InputStream;
import java.io.OutputStream;

public class OdtToPDF
extends Converter {
    public OdtToPDF(InputStream inStream, OutputStream outStream, boolean showMessages, boolean closeStreamsWhenComplete) {
        super(inStream, outStream, showMessages, closeStreamsWhenComplete);
    }

    @Override
    public void convert() throws Exception {
        throw new UnsupportedOperationException();
    }
}

