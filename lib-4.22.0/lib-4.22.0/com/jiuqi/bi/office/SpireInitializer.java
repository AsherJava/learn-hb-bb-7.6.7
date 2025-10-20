/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.office;

import java.lang.reflect.Method;

public class SpireInitializer {
    public static final String SPIRE_KEY = "GZOKXRephAFO/BsR6QEAaHR/X2mVRzeLPiivVnYngulY2X2QhW24T5cjUrO4TN4gZgtnHq6pg3BVQIheGszlqPA40/9leKbMu1le9V9ulc06uib3kD9jxnOxNOe8KLqxVwDNlvNZPedFfYepkpO3Qed0duSbGMyVkE9AeAUz900Y5J9RNIR66xIPtmL4NHonKb2LRQ11n9nzUrHjLdG2Iw9Fc+mqTVJaZ32sO+K8bpO9XifKL5sNpibN5g4ExG5wSuGs3o7cUq7/nRdNFDs6sICwQrXLe7XXGyMTmonGM+kXbxR/xWNV/hxuNqoQ9WN4pDpW0GWRd8I8chrdehUUYT7+X9/l7urYzx/HQecyughyPCO0xLWGTqrmIvhgoCT+N+QnFof/6+NMJ/wC9xTY+c9PNgS2VMah78LwgLRrSTtQR+wk1K3sCiIY915amt1KLElhOCg5w6J+0khPDFB4eT06spMBOWTBibv+VsVGWV0ScyEYOyo0z581Mzxt4rkbIgJnlBxYrGbhlpJlJcCce3brTgEAOXhhe4fUAuIZM6FWLjUKsjY/0pa5kEIg1ke+DEfRGdvlz8bLQWD3PnaaBA4XVaAB3DcHgMvZZATXX6nKs7jFpUI2UfjypTNT1jKFQKGtWBi5dTzyN8e+YhnntJ/ZS+8Oi/MS0Z6Bwyc68NfmNT9ehIx4I6PQcANuQmL71Dledd/7agSZ+SvINDY7DjBBlY9+y0NeJHMZID1jIm+j18T/6Rb57J9yQUbfdx9vbqbECkX5X2H1seo3XFwJdm7qnRpsfXLVV/AYduZeThB5D3SjEcRpUaevRVMU5ZEbO7gHDPYmcGNaHvmJrsYT/XImzdrjSkEm7ORApkGO/xzrdGjliTHmGxa7C6Ml4XpeLgTK+wU5fqMjWIihLajKcEp8qMGpnEEvB8MLVVcJe5o1MMxgZDbjFhf2DTUL6Fvz01YLyzCmhPG0KLtCF2+WJR0g9R8qbECPs6+wSGPwltB+sdOIpddinqSYpgrJ/4qRtW0VkVTqeOjux7XPDT6HPyJdSI8UEGR7yrL8kmRTW9Lcq1oudUIpSKKyqbS2oNaLwu3OELT8+vQ56osrX1z7oQQ0jQiaCItjAFERKaBoX6DspO1luqntjScZkdjykame4vg0s9AF14Gk3RLVsBMv0UO1cIgY2NCQZKDtMkwrUv/WSMuI5X3BJqd3emu+QYo+UOVaOEzl5WHpVyuUgMby2yCslNf7gq+jGQ379PJk1NOzJar9Xyc1TtLyYZvHZZ03z7CZ2v6nyMmF6AQEAgAiTkvTMN2Q8QuhbWbiWgVX7aVHpjfInUjgh6loJwhwS2ikqqI6C/0W5U0wNwrI/xNHxlKV2rjhJFGGhkltHy8A+OFaXrAhUnJUr0Hd+Em26I0OM6lgjXHZpNqt9B+i4znQ60bWjUIhIfCEXez7gmDFkQ8OD+R0OTWCXGPArmXERs26HQKSU/xQSCR692EsU0kGTzPzX7jk/0p25u1DoVLj3gihwQ+rRRbJGlUdyuErtfhxEd+lT7w8SWYlOyxzxX3yoyvHh9ew9wOVI/A8jlFoLZVDKqkFcGHHR7C2i8i22uY5dJpI8NlqWYnmVCgprlkPhT9oc683XHrL2fkFEqZCkjWPatWb1gDZsCIM5bD4XE5U5psBucooheJSnIz5aNngDni1EszMjWqFtgC4Ry6FN2zXstA7JMJLXH7ouDqqFxvF";
    private static boolean SPIRE_INITIALIZED = false;

    public static synchronized void initialize() {
        if (SPIRE_INITIALIZED) {
            return;
        }
        SpireInitializer.loadLicenseKey("com.spire.license.LicenseProvider", SPIRE_KEY);
        SpireInitializer.loadLicenseKey("com.spire.doc.license.LicenseProvider", SPIRE_KEY);
        SpireInitializer.loadLicenseKey("com.spire.xls.license.LicenseProvider", SPIRE_KEY);
        SpireInitializer.loadLicenseKey("com.spire.presentation.license.LicenseProvider", SPIRE_KEY);
        SpireInitializer.loadLicenseKey("com.spire.pdf.license.LicenseProvider", SPIRE_KEY);
        SpireInitializer.loadLicenseKey("com.spire.ocr.license.LicenseProvider", SPIRE_KEY);
        SpireInitializer.loadLicenseKey("com.spire.barcode.license.LicenseProvider", SPIRE_KEY);
        SPIRE_INITIALIZED = true;
    }

    private static void loadLicenseKey(String licenseClassName, String licenseKey) {
        try {
            Class<?> clazz = Class.forName(licenseClassName);
            Method method = clazz.getMethod("setLicenseKey", String.class);
            method.invoke(null, licenseKey);
        }
        catch (Exception exception) {
            // empty catch block
        }
    }
}

