/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.http.HttpRequest
 *  org.springframework.http.client.ClientHttpRequestExecution
 *  org.springframework.http.client.ClientHttpRequestInterceptor
 *  org.springframework.http.client.ClientHttpResponse
 */
package com.jiuqi.gcreport.oauth2.util;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

public class RestTemplateLogger
implements ClientHttpRequestInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(RestTemplateLogger.class);

    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        logger.debug("=== RestTemplate Request Details ===");
        logger.debug("URI: " + request.getURI());
        logger.debug("Method: " + request.getMethod());
        logger.debug("Headers: " + request.getHeaders());
        logger.debug("Request Body: " + new String(body, StandardCharsets.UTF_8));
        logger.debug("=============================");
        return execution.execute(request, body);
    }
}

