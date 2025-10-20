/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.webserviceclient.utils;

import java.security.cert.X509Certificate;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class TrustAllTrustManager
implements TrustManager,
X509TrustManager {
    @Override
    public X509Certificate[] getAcceptedIssuers() {
        return null;
    }

    @Override
    public void checkServerTrusted(X509Certificate[] certs, String authType) {
    }

    @Override
    public void checkClientTrusted(X509Certificate[] certs, String authType) {
    }
}

