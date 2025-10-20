/*
 * Decompiled with CFR 0.152.
 */
package com.itextpdf.commons.actions.data;

import com.itextpdf.commons.actions.data.ProductData;

public final class CommonsProductData {
    static final String COMMONS_PUBLIC_PRODUCT_NAME = "Commons";
    static final String COMMONS_PRODUCT_NAME = "commons";
    static final String COMMONS_VERSION = "8.0.5";
    static final String MINIMAL_COMPATIBLE_LICENSEKEY_VERSION = "4.1.0";
    static final int COMMONS_COPYRIGHT_SINCE = 2000;
    static final int COMMONS_COPYRIGHT_TO = 2024;
    private static final ProductData COMMONS_PRODUCT_DATA = new ProductData("Commons", "commons", "8.0.5", "4.1.0", 2000, 2024);

    private CommonsProductData() {
    }

    public static ProductData getInstance() {
        return COMMONS_PRODUCT_DATA;
    }
}

