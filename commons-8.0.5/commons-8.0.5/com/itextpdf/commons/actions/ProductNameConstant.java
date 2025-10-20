/*
 * Decompiled with CFR 0.152.
 */
package com.itextpdf.commons.actions;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public final class ProductNameConstant {
    public static final String ITEXT_CORE = "itext-core";
    public static final String ITEXT_CORE_SIGN = "itext-core-sign";
    public static final String PDF_HTML = "pdfHtml";
    public static final String PDF_SWEEP = "pdfSweep";
    public static final String PDF_OCR_TESSERACT4 = "pdfOcr-tesseract4";
    public static final Set<String> PRODUCT_NAMES = Collections.unmodifiableSet(new HashSet<String>(Arrays.asList("itext-core", "pdfHtml", "pdfSweep", "pdfOcr-tesseract4")));

    private ProductNameConstant() {
    }
}

