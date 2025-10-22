/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  org.springframework.http.HttpEntity
 *  org.springframework.http.HttpHeaders
 *  org.springframework.http.HttpMethod
 *  org.springframework.http.ResponseEntity
 *  org.springframework.web.client.RestTemplate
 */
package com.jiuqi.gcreport.mobile.approval.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.gcreport.mobile.approval.entity.cnoocEncryptReqEntity;
import com.jiuqi.gcreport.mobile.approval.entity.cnoocEncryptResEntity;
import com.jiuqi.gcreport.mobile.approval.service.CnoocMobileService;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestTemplate;

@Component
public class CnoocMobileServiceImpl
implements CnoocMobileService {
    private static final Logger logger = LoggerFactory.getLogger(CnoocMobileServiceImpl.class);
    private static final String TODO_HEADERS_CONTENT_TYPE = "Content-Type";
    private static final String TODO_HEADERS_CONTENT_TYPE_VALUE = "application/json;charset=UTF-8";
    private static final String ENCODE_URL = "https://mdev.cnooc.com.cn/app/openApi/encryptUrl";
    private static final String BASIC_AUTH_USERNAME = "zb_cwbb_custom";
    private static final String BASIC_AUTH_PASSWORD = "Devzbcwbb@2021";
    private static ObjectMapper mapper = new ObjectMapper();
    @Autowired
    @Qualifier(value="ignoreSSLRestTemplate")
    private RestTemplate ignoreSSLRestTemplate;

    @Override
    public String getEncodeUrl(String url) {
        logger.info("\u5f85\u52a0\u5bc6\u7684url\u4e3a\uff1a" + url);
        cnoocEncryptReqEntity cnoocReqEntity = new cnoocEncryptReqEntity();
        ArrayList<String> params = new ArrayList<String>();
        params.add(url);
        cnoocReqEntity.setUrllist(params);
        String json = CnoocMobileServiceImpl.writeValueAsString(cnoocReqEntity);
        HttpHeaders headers = new HttpHeaders();
        headers.add(TODO_HEADERS_CONTENT_TYPE, TODO_HEADERS_CONTENT_TYPE_VALUE);
        headers.setBasicAuth(BASIC_AUTH_USERNAME, BASIC_AUTH_PASSWORD);
        HttpEntity httpEntity = new HttpEntity((Object)json, (MultiValueMap)headers);
        ResponseEntity response = this.ignoreSSLRestTemplate.exchange(ENCODE_URL, HttpMethod.POST, httpEntity, (ParameterizedTypeReference)new ParameterizedTypeReference<List<cnoocEncryptResEntity>>(){}, new Object[0]);
        if (!ObjectUtils.isEmpty(response.getBody())) {
            logger.info("url\u52a0\u5bc6\u63a5\u53e3:{}, \u8fd4\u56dejson:{}", (Object)ENCODE_URL, response.getBody());
            List responseBody = (List)response.getBody();
            return ((cnoocEncryptResEntity)responseBody.get(0)).getEncryptedUrl();
        }
        logger.error("\u52a0\u5bc6\u63a5\u53e3\u8fd4\u56de\u4e3a\u7a7a");
        return null;
    }

    public static String writeValueAsString(Object obj) {
        try {
            return mapper.writeValueAsString(obj);
        }
        catch (Exception e) {
            throw new BusinessRuntimeException((Throwable)e);
        }
    }
}

