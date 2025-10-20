/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.http.client.ClientHttpRequestFactory
 *  org.springframework.http.client.SimpleClientHttpRequestFactory
 *  org.springframework.web.client.RestTemplate
 */
package com.jiuqi.np.core.utils;

import com.jiuqi.np.core.httpclient.NvwaSslClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

public class RestTemplateUtil {
    public static RestTemplate generateSsl() {
        return new RestTemplate((ClientHttpRequestFactory)new NvwaSslClientHttpRequestFactory());
    }

    public static RestTemplate generateSsl(int readTimeout, int connectTimeout) {
        return new RestTemplate((ClientHttpRequestFactory)new NvwaSslClientHttpRequestFactory(readTimeout, connectTimeout));
    }

    public static RestTemplate generateUnSsl() {
        return new RestTemplate();
    }

    public static RestTemplate generateUnSsl(int readTimeout, int connectTimeout) {
        SimpleClientHttpRequestFactory simpleClientHttpRequestFactory = new SimpleClientHttpRequestFactory();
        simpleClientHttpRequestFactory.setReadTimeout(readTimeout);
        simpleClientHttpRequestFactory.setConnectTimeout(connectTimeout);
        return new RestTemplate((ClientHttpRequestFactory)simpleClientHttpRequestFactory);
    }
}

