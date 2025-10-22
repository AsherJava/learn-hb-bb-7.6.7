/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.JsonNode
 *  com.fasterxml.jackson.databind.node.ObjectNode
 *  com.jiuqi.bde.common.certify.service.RequestCertifyService
 *  com.jiuqi.gcreport.bde.common.utils.BdeClientUtil
 *  com.jiuqi.va.domain.common.JSONUtil
 *  org.springframework.http.HttpEntity
 *  org.springframework.http.HttpHeaders
 *  org.springframework.http.HttpMethod
 *  org.springframework.http.MediaType
 *  org.springframework.http.ResponseEntity
 *  org.springframework.web.client.RestTemplate
 *  org.springframework.web.util.UriComponents
 *  org.springframework.web.util.UriComponentsBuilder
 */
package com.jiuqi.gcreport.sdk.rest;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.jiuqi.bde.common.certify.service.RequestCertifyService;
import com.jiuqi.gcreport.bde.common.utils.BdeClientUtil;
import com.jiuqi.va.domain.common.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

abstract class AbstractBdeRestRequest {
    @Qualifier(value="bdeFetchRestTemplate")
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private RequestCertifyService certifyService;

    AbstractBdeRestRequest() {
    }

    protected <T> String exchangeJSON(String url, T param, HttpMethod httpMethod) {
        String address = this.certifyService.getNvwaUrl();
        if (!StringUtils.hasText(address)) {
            throw new RuntimeException("BDE\u5730\u5740\u4e3a\u7a7a\uff0c\u8bf7\u914d\u7f6eBDE\u5730\u5740\u3002");
        }
        String sendUrl = address + url;
        return this.exchangeStr(sendUrl, param, httpMethod);
    }

    protected <T> String getForMap(String path, MultiValueMap<String, String> params) {
        String address = this.certifyService.getNvwaUrl();
        if (!StringUtils.hasText(address)) {
            throw new RuntimeException("BDE\u5730\u5740\u4e3a\u7a7a\uff0c\u8bf7\u914d\u7f6eBDE\u5730\u5740\u3002");
        }
        UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl((String)(address + path)).queryParams(params).build();
        return this.exchangeStr(uriComponents.toUriString(), null, HttpMethod.GET);
    }

    private <T> String exchangeStr(String url, T body, HttpMethod httpMethod) {
        ResponseEntity resultStr;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity entity = new HttpEntity(body, (MultiValueMap)headers);
        try {
            resultStr = this.restTemplate.exchange(url, httpMethod, entity, String.class, new Object[0]);
        }
        catch (Exception e) {
            throw new RuntimeException("\u53d6\u6570\u5730\u5740\u8fde\u63a5\u8d85\u65f6:" + this.getDetailExceptionMessage(e), e);
        }
        if (resultStr == null) {
            throw new RuntimeException("\u8c03\u7528BDE\u53d6\u6570\u8bf7\u6c42\u5f02\u5e38\uff01");
        }
        ObjectNode resultObj = JSONUtil.parseObject((String)((String)resultStr.getBody()));
        if (BdeClientUtil.getBoolean((JsonNode)resultObj.get("success")).booleanValue()) {
            return BdeClientUtil.getString((JsonNode)resultObj.get("data"));
        }
        throw new RuntimeException(BdeClientUtil.getString((JsonNode)resultObj.get("errorMessage")));
    }

    private String getDetailExceptionMessage(Exception e) {
        if (e.getCause() == null) {
            return e.getMessage();
        }
        Throwable cause = e.getCause();
        while (cause.getCause() != null) {
            cause = cause.getCause();
        }
        return cause.getMessage();
    }
}

