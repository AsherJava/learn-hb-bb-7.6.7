/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.http.client.SimpleClientHttpRequestFactory
 */
package com.jiuqi.np.core.httpclient;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import org.springframework.http.client.SimpleClientHttpRequestFactory;

public class NvwaSslClientHttpRequestFactory
extends SimpleClientHttpRequestFactory {
    public NvwaSslClientHttpRequestFactory() {
    }

    public NvwaSslClientHttpRequestFactory(int readTimeout, int connectTimeout) {
        super.setReadTimeout(readTimeout);
        super.setConnectTimeout(connectTimeout);
    }

    protected void prepareConnection(HttpURLConnection connection, String httpMethod) throws IOException {
        if (connection instanceof HttpsURLConnection) {
            this.prepareHttpsConnection((HttpsURLConnection)connection);
        }
        super.prepareConnection(connection, httpMethod);
    }

    private void prepareHttpsConnection(HttpsURLConnection connection) {
        connection.setHostnameVerifier(new SkipHostnameVerifier());
        try {
            connection.setSSLSocketFactory(this.createSslSocketFactory());
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    private SSLSocketFactory createSslSocketFactory() throws Exception {
        SSLContext context = SSLContext.getInstance("TLS");
        context.init(null, new TrustManager[]{new SkipX509TrustManager()}, new SecureRandom());
        return context.getSocketFactory();
    }

    private static class SkipX509TrustManager
    implements X509TrustManager {
        private SkipX509TrustManager() {
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }

        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }
    }

    private class SkipHostnameVerifier
    implements HostnameVerifier {
        private SkipHostnameVerifier() {
        }

        @Override
        public boolean verify(String s, SSLSession sslSession) {
            return true;
        }
    }
}

