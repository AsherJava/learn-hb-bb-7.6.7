/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  org.springframework.http.HttpEntity
 *  org.springframework.http.HttpHeaders
 *  org.springframework.http.HttpMethod
 *  org.springframework.http.HttpStatus
 *  org.springframework.http.MediaType
 *  org.springframework.http.ResponseEntity
 *  org.springframework.web.client.RestTemplate
 */
package com.jiuqi.gcreport.aidocaudit.util;

import com.jiuqi.common.base.BusinessRuntimeException;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Component
public class ApiKeyCache {
    private static final Logger logger = LoggerFactory.getLogger(ApiKeyCache.class);
    @Value(value="${jiuqi.gcreport.aidocaudit.scoresystem.url:10.2.45.188:8880}")
    private String ipAndPort;
    private final RestTemplate restTemplate = new RestTemplate();
    private static volatile String token;
    private final Object lock = new Object();

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public String getToken() {
        if (token == null) {
            Object object = this.lock;
            synchronized (object) {
                if (token == null) {
                    token = this.refreshToken();
                }
            }
        }
        return token;
    }

    public String refreshToken() {
        String loginUrl = "http://" + this.ipAndPort + "/api/user/login";
        HashMap<String, String> loginBody = new HashMap<String, String>();
        loginBody.put("user_name", "dev");
        loginBody.put("user_pwd", "QZDev.506");
        loginBody.put("tnn", "insidedemo");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity requestEntity = new HttpEntity(loginBody, (MultiValueMap)headers);
        try {
            Map response;
            ResponseEntity responseEntity = this.restTemplate.exchange(loginUrl, HttpMethod.POST, requestEntity, Map.class, new Object[0]);
            if (responseEntity.getStatusCode() == HttpStatus.OK && (response = (Map)responseEntity.getBody()) != null && (Integer)response.get("code") == 1) {
                return (String)((Map)response.get("data")).get("api_key");
            }
        }
        catch (Exception e) {
            logger.error("Token\u5237\u65b0\u5931\u8d25", e);
        }
        throw new BusinessRuntimeException("\u65e0\u6cd5\u83b7\u53d6\u6709\u6548Token");
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void invalidateToken() {
        Object object = this.lock;
        synchronized (object) {
            token = null;
        }
    }
}

