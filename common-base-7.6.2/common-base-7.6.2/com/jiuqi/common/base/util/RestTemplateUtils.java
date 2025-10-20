/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.jiuqi.np.core.utils.RestTemplateUtil
 *  org.springframework.http.HttpEntity
 *  org.springframework.http.HttpHeaders
 *  org.springframework.http.HttpMethod
 *  org.springframework.http.MediaType
 *  org.springframework.http.ResponseEntity
 *  org.springframework.http.converter.StringHttpMessageConverter
 *  org.springframework.web.client.RestTemplate
 */
package com.jiuqi.common.base.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.np.core.utils.RestTemplateUtil;
import java.util.ArrayList;
import java.util.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.util.MultiValueMap;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestTemplate;

public class RestTemplateUtils {
    private static final Logger logger = LoggerFactory.getLogger(RestTemplateUtils.class);

    private static RestTemplate getSimpleRestTemplate() {
        StringHttpMessageConverter stringHttpMessageConverter = new StringHttpMessageConverter();
        ArrayList<StringHttpMessageConverter> converters = new ArrayList<StringHttpMessageConverter>(1);
        converters.add(stringHttpMessageConverter);
        RestTemplate restTemplate = RestTemplateUtil.generateSsl();
        restTemplate.setMessageConverters(converters);
        return restTemplate;
    }

    public static <T> T getJSON(String url, TypeReference<T> returnType) {
        return RestTemplateUtils.exchangeJSON(url, null, HttpMethod.GET, null, returnType);
    }

    public static <T> T getJSON(String url, HttpHeaders headers, TypeReference<T> returnType) {
        return RestTemplateUtils.exchangeJSON(url, headers, HttpMethod.GET, null, returnType);
    }

    public static <T> T postJSON(String url, Object body, TypeReference<T> returnType) {
        return RestTemplateUtils.exchangeJSON(url, null, HttpMethod.POST, body, returnType);
    }

    public static <T> T postJSON(String url, HttpHeaders headers, Object body, TypeReference<T> returnType) {
        return RestTemplateUtils.exchangeJSON(url, headers, HttpMethod.POST, body, returnType);
    }

    public static <T> T exchangeJSON(String url, HttpHeaders headers, HttpMethod method, Object body, TypeReference<T> returnType) {
        ResponseEntity result;
        HttpEntity httpEntity;
        if (headers == null) {
            headers = new HttpHeaders();
        }
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        if (!ObjectUtils.isEmpty(body)) {
            String json = JsonUtils.writeValueAsString(body);
            httpEntity = new HttpEntity((Object)json, (MultiValueMap)headers);
            if (method == null) {
                method = HttpMethod.POST;
            }
        } else {
            httpEntity = new HttpEntity((MultiValueMap)headers);
            if (method == null) {
                method = HttpMethod.GET;
            }
        }
        try {
            result = RestTemplateUtils.getSimpleRestTemplate().exchange(url, method, httpEntity, String.class, new Object[0]);
        }
        catch (Exception e) {
            throw new BusinessRuntimeException("REST\u8c03\u7528" + method + "\u63a5\u53e3 " + url + " \u51fa\u73b0\u5f02\u5e38\uff01", e);
        }
        if (!ObjectUtils.isEmpty(result.getBody())) {
            logger.info("\u8c03\u7528{}\u63a5\u53e3:{}, \u8fd4\u56dejson:{}", method, url, result.getBody());
            return JsonUtils.readValue((String)result.getBody(), returnType);
        }
        return null;
    }

    public static String exchangeString(String url, HttpHeaders headers, HttpMethod method, String body) {
        ResponseEntity result;
        HttpEntity httpEntity;
        if (!ObjectUtils.isEmpty(body)) {
            httpEntity = new HttpEntity((Object)body, (MultiValueMap)headers);
            if (method == null) {
                method = HttpMethod.POST;
            }
        } else {
            httpEntity = new HttpEntity((MultiValueMap)headers);
            if (method == null) {
                method = HttpMethod.GET;
            }
        }
        try {
            result = RestTemplateUtils.getSimpleRestTemplate().exchange(url, method, httpEntity, String.class, new Object[0]);
        }
        catch (Exception e) {
            throw new BusinessRuntimeException("REST\u8c03\u7528" + method + "\u63a5\u53e3 " + url + " \u51fa\u73b0\u5f02\u5e38\uff01", e);
        }
        if (!ObjectUtils.isEmpty(result.getBody())) {
            logger.info("\u8c03\u7528{}\u63a5\u53e3:{}, \u8fd4\u56deString:{}", method, url, result.getBody());
            return (String)result.getBody();
        }
        return null;
    }

    @Deprecated
    public static String getAPIAuthorization(String appID, String keySecret) {
        return "Basic " + new String(Base64.getEncoder().encode((appID + ":" + keySecret).getBytes()));
    }
}

