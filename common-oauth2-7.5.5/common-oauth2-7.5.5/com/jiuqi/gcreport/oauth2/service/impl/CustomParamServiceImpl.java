/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.certification.bean.NvwaCertify
 *  javax.servlet.http.HttpServletRequest
 *  org.apache.commons.lang3.StringUtils
 *  org.json.JSONObject
 *  org.springframework.http.HttpEntity
 *  org.springframework.http.HttpHeaders
 *  org.springframework.http.HttpMethod
 *  org.springframework.http.HttpStatus
 *  org.springframework.http.InvalidMediaTypeException
 *  org.springframework.http.MediaType
 *  org.springframework.http.ResponseEntity
 *  org.springframework.web.client.RestTemplate
 *  org.springframework.web.context.request.RequestContextHolder
 *  org.springframework.web.context.request.ServletRequestAttributes
 */
package com.jiuqi.gcreport.oauth2.service.impl;

import com.jiuqi.gcreport.oauth2.exception.GcOAuth2Exception;
import com.jiuqi.gcreport.oauth2.pojo.CustomField;
import com.jiuqi.gcreport.oauth2.pojo.CustomFieldType;
import com.jiuqi.gcreport.oauth2.pojo.CustomParam;
import com.jiuqi.gcreport.oauth2.pojo.GcCertifyExtInfo;
import com.jiuqi.gcreport.oauth2.service.CustomParamService;
import com.jiuqi.gcreport.oauth2.util.JsonUtils;
import com.jiuqi.gcreport.oauth2.util.UrlEncodeUtil;
import com.jiuqi.nvwa.certification.bean.NvwaCertify;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.InvalidMediaTypeException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Service
public class CustomParamServiceImpl
implements CustomParamService {
    private static final Logger logger = LoggerFactory.getLogger(CustomParamServiceImpl.class);
    private static final String CONTENT_TYPE = "content-type";
    private static final String REDIRECT_URI = "redirect_uri";
    @Lazy
    @Autowired
    @Qualifier(value="ignoreSSLRestTemplate")
    protected RestTemplate restTemplate;

    @Override
    public String getAuthorizationUrl(String authUrl, NvwaCertify nvwaCertify, GcCertifyExtInfo extInfo, CustomParam customParam, boolean withRedirectUri) {
        List<CustomField> urlParameters = customParam.getUrlParameters();
        StringBuilder sb = new StringBuilder(authUrl);
        if (urlParameters != null) {
            Iterator<CustomField> iterator = urlParameters.iterator();
            sb.append("?");
            while (iterator.hasNext()) {
                CustomField next = iterator.next();
                String key = next.getFieldName();
                String value = this.getCustomFieldTrueValue(next, CustomParamServiceImpl.getHttpServletRequest(), nvwaCertify);
                if (REDIRECT_URI.equalsIgnoreCase(key)) {
                    if (!withRedirectUri) continue;
                    if (extInfo.getEncodeRedirectUri().booleanValue()) {
                        sb.append(key).append("=").append(UrlEncodeUtil.encode(value));
                    } else {
                        sb.append(key).append("=").append(value);
                    }
                    sb.append("&");
                    continue;
                }
                sb.append(key).append("=").append(value);
                sb.append("&");
            }
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }

    @Override
    public String getAccessToken(NvwaCertify nvwaCertify, GcCertifyExtInfo extInfo, CustomParam customParam, String tokenUrl) {
        String responseStr = null;
        String method = customParam.getMethod();
        HttpServletRequest request = CustomParamServiceImpl.getHttpServletRequest();
        boolean isFormUrlEncoded = false;
        HttpHeaders headers = this.setCustomHeader(nvwaCertify, request, customParam);
        if (MediaType.APPLICATION_FORM_URLENCODED.equals((Object)headers.getContentType())) {
            isFormUrlEncoded = true;
        }
        if (customParam.getReqAcessTokenUseBasicAuth()) {
            headers.setBasicAuth(nvwaCertify.getClientid(), nvwaCertify.getClientsecret());
        }
        tokenUrl = this.setCustomUrlParameters(tokenUrl, nvwaCertify, customParam.getUrlParameters());
        if (method == null || "POST".equalsIgnoreCase(method)) {
            HttpEntity requestEntity;
            if (isFormUrlEncoded) {
                MultiValueMap<String, String> formBody = this.setCustomFormBody(nvwaCertify, customParam.getFormBody());
                requestEntity = new HttpEntity(formBody, (MultiValueMap)headers);
            } else {
                String jsonString = this.setCustomJsonBody(nvwaCertify, customParam.getJsonBody());
                requestEntity = new HttpEntity((Object)jsonString, (MultiValueMap)headers);
            }
            try {
                logger.info("\u4ee5\u81ea\u5b9a\u4e49\u53c2\u6570\u65b9\u5f0f\u83b7\u53d6\u8bbf\u95ee\u51ed\u636e,\u8bf7\u6c42\u53c2\u6570: METHOD={}, URL={}, IS_FORM={}, BODY={}", method, tokenUrl, isFormUrlEncoded, requestEntity.getBody());
                responseStr = (String)this.restTemplate.postForObject(tokenUrl, (Object)requestEntity, String.class, new HashMap(0));
            }
            catch (Exception e) {
                logger.warn("\u4ee5\u81ea\u5b9a\u4e49\u53c2\u6570\u65b9\u5f0fPOST\u8bf7\u6c42\u83b7\u53d6\u8bbf\u95ee\u51ed\u636e\u63a5\u53e3\u62a5\u9519", e);
                throw new GcOAuth2Exception("\u4ee5\u81ea\u5b9a\u4e49\u53c2\u6570\u65b9\u5f0fPOST\u8bf7\u6c42\u83b7\u53d6\u8bbf\u95ee\u51ed\u636e\u63a5\u53e3\u62a5\u9519", e);
            }
        }
        if ("GET".equalsIgnoreCase(method)) {
            ResponseEntity response;
            HttpEntity requestEntity = new HttpEntity((MultiValueMap)headers);
            logger.info("\u4ee5\u81ea\u5b9a\u4e49\u53c2\u6570\u65b9\u5f0f\u83b7\u53d6\u8bbf\u95ee\u51ed\u636e,\u8bf7\u6c42\u53c2\u6570: METHOD={}, URL={}", (Object)method, (Object)tokenUrl);
            try {
                response = this.restTemplate.exchange(tokenUrl, HttpMethod.GET, requestEntity, String.class, new HashMap(0));
            }
            catch (Exception e) {
                logger.warn("\u4ee5\u81ea\u5b9a\u4e49\u53c2\u6570\u65b9\u5f0fGET\u8bf7\u6c42\u83b7\u53d6\u8bbf\u95ee\u51ed\u636e\u63a5\u53e3\u62a5\u9519", e);
                throw new GcOAuth2Exception("\u4ee5\u81ea\u5b9a\u4e49\u53c2\u6570\u65b9\u5f0fGET\u8bf7\u6c42\u83b7\u53d6\u8bbf\u95ee\u51ed\u636e\u63a5\u53e3\u62a5\u9519", e);
            }
            if (HttpStatus.OK.equals((Object)response.getStatusCode())) {
                responseStr = (String)response.getBody();
            }
        }
        logger.info("\u6253\u5370\u8bbf\u95ee\u51ed\u636e\u8fd4\u56de\u4f53\uff1a{}", (Object)responseStr);
        return responseStr;
    }

    private String setCustomUrlParameters(String url, NvwaCertify nvwaCertify, List<CustomField> urlParameters) {
        StringBuilder sb = new StringBuilder(url);
        if (!CollectionUtils.isEmpty(urlParameters)) {
            HttpServletRequest request = CustomParamServiceImpl.getHttpServletRequest();
            Iterator<CustomField> iterator = urlParameters.iterator();
            sb.append("?");
            while (iterator.hasNext()) {
                CustomField next = iterator.next();
                String key = next.getFieldName();
                String value = this.getCustomFieldTrueValue(next, request, nvwaCertify);
                sb.append(key).append("=").append(value);
                sb.append("&");
            }
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }

    private MultiValueMap<String, String> setCustomFormBody(NvwaCertify nvwaCertify, List<CustomField> list) {
        LinkedMultiValueMap<String, String> body = new LinkedMultiValueMap<String, String>();
        if (!CollectionUtils.isEmpty(list)) {
            for (CustomField next : list) {
                String key = next.getFieldName();
                String value = this.getCustomFieldTrueValue(next, CustomParamServiceImpl.getHttpServletRequest(), nvwaCertify);
                body.add(key, value);
            }
        }
        return body;
    }

    private String setCustomJsonBody(NvwaCertify nvwaCertify, List<CustomField> list) {
        LinkedHashMap<String, String> body = new LinkedHashMap<String, String>();
        if (!CollectionUtils.isEmpty(list)) {
            for (CustomField next : list) {
                String key = next.getFieldName();
                String value = this.getCustomFieldTrueValue(next, CustomParamServiceImpl.getHttpServletRequest(), nvwaCertify);
                body.put(key, value);
            }
        }
        return JsonUtils.writeValueAsString(body);
    }

    private HttpHeaders setCustomHeader(NvwaCertify nvwaCertify, HttpServletRequest request, CustomParam customParam) {
        HttpHeaders headers = new HttpHeaders();
        List<CustomField> customHeaders = customParam.getHeaders();
        if (customHeaders != null && customHeaders.size() > 0) {
            for (CustomField next : customHeaders) {
                String headerName = next.getFieldName();
                String headerValue = this.getCustomFieldTrueValue(next, request, nvwaCertify);
                if (CONTENT_TYPE.equalsIgnoreCase(headerName) && StringUtils.isNotBlank((CharSequence)headerValue)) {
                    MediaType mediaType;
                    try {
                        mediaType = MediaType.valueOf((String)headerValue);
                    }
                    catch (InvalidMediaTypeException e) {
                        logger.warn("\u81ea\u5b9a\u4e49\u8bf7\u6c42\u5934\u7684\u503c\u4e0d\u5408\u6cd5\uff0c\u8df3\u8fc7\u3002headName={},headerValue={}", (Object)headerName, (Object)headerValue);
                        continue;
                    }
                    headers.setContentType(mediaType);
                    continue;
                }
                headers.add(headerName, headerValue);
            }
        }
        return headers;
    }

    private String getCustomFieldTrueValue(CustomField cf, HttpServletRequest request, NvwaCertify nvwaCertify) {
        if (cf.getFieldType() == null || CustomFieldType.LITERAL.equals((Object)cf.getFieldType())) {
            return cf.getFieldValue();
        }
        String referenceKey = cf.getFieldValue();
        if (CustomFieldType.REQUEST_HEADER.equals((Object)cf.getFieldType())) {
            return request.getHeader(referenceKey);
        }
        if (CustomFieldType.REQUEST_PARAMETER.equals((Object)cf.getFieldType())) {
            return request.getParameter(referenceKey);
        }
        referenceKey = referenceKey.replace("-", "").replace("_", "");
        if (CustomFieldType.NVWA_CERTIFY.equals((Object)cf.getFieldType())) {
            if ("clientid".equalsIgnoreCase(referenceKey)) {
                return nvwaCertify.getClientid();
            }
            if ("clientsecret".equalsIgnoreCase(referenceKey)) {
                return nvwaCertify.getClientsecret();
            }
            logger.warn("NVWA_CERTIFY\u4e2d\u53d6\u503c\u3010{}\u3011\u5931\u8d25: \u3010NVWA_CERTIFY\u3011\u5f15\u7528\u7c7b\u578b\u4ec5\u652f\u6301CLIENT_ID\u4e0eCLIENT_SECRET,\u9ed8\u8ba4\u53d6\u7a7a\u503c", (Object)cf.getFieldValue());
        }
        if (CustomFieldType.EXT_INFO.equals((Object)cf.getFieldType())) {
            String extraInfo = nvwaCertify.getExtraInfo();
            JSONObject extInfo = new JSONObject(extraInfo);
            Iterator it = extInfo.keys();
            while (it.hasNext()) {
                String key = (String)it.next();
                String temp = key.replace("-", "").replace("_", "");
                if (!StringUtils.equalsIgnoreCase((CharSequence)temp, (CharSequence)referenceKey)) continue;
                return extInfo.getString(key);
            }
            logger.warn("EXT_INFO\u4e2d\u53d6\u503c\u3010{}\u3011\u5931\u8d25: \u6269\u5c55\u4fe1\u606f\u4e2d\u4e0d\u5305\u542b\u8be5key,\u9ed8\u8ba4\u53d6\u7a7a\u503c", (Object)cf.getFieldValue());
        }
        return null;
    }

    @Override
    public String getUserInfo(NvwaCertify nvwaCertify, CustomParam customParam, String accessToken, String userInfoUrl) {
        String responseStr = null;
        String method = customParam.getMethod();
        HttpServletRequest request = CustomParamServiceImpl.getHttpServletRequest();
        boolean isFormUrlEncoded = false;
        HttpHeaders headers = this.setCustomHeader(nvwaCertify, request, customParam);
        if (MediaType.APPLICATION_FORM_URLENCODED.equals((Object)headers.getContentType())) {
            isFormUrlEncoded = true;
        }
        if (customParam.getReqUserInfoUseBearerAuth()) {
            headers.setBearerAuth(accessToken);
        }
        userInfoUrl = this.setCustomUrlParameters(userInfoUrl, nvwaCertify, customParam.getUrlParameters());
        if (method == null || "POST".equalsIgnoreCase(method)) {
            HttpEntity requestEntity;
            if (isFormUrlEncoded) {
                MultiValueMap<String, String> formBody = this.setCustomFormBody(nvwaCertify, customParam.getFormBody());
                requestEntity = new HttpEntity(formBody, (MultiValueMap)headers);
            } else {
                String jsonString = this.setCustomJsonBody(nvwaCertify, customParam.getJsonBody());
                requestEntity = new HttpEntity((Object)jsonString, (MultiValueMap)headers);
            }
            try {
                logger.info("\u4ee5\u81ea\u5b9a\u4e49\u53c2\u6570\u65b9\u5f0f\u83b7\u53d6\u7528\u6237\u4fe1\u606f,\u8bf7\u6c42\u53c2\u6570: METHOD={}, URL={}, IS_FORM={}, BODY={}", method, userInfoUrl, isFormUrlEncoded, requestEntity.getBody());
                responseStr = (String)this.restTemplate.postForObject(userInfoUrl, (Object)requestEntity, String.class, new HashMap(0));
            }
            catch (Exception e) {
                logger.warn("\u4ee5\u81ea\u5b9a\u4e49\u53c2\u6570\u65b9\u5f0fPOST\u8bf7\u6c42\u83b7\u53d6\u7528\u6237\u4fe1\u606f\u63a5\u53e3\u62a5\u9519", e);
                throw new GcOAuth2Exception("\u4ee5\u81ea\u5b9a\u4e49\u53c2\u6570\u65b9\u5f0fPOST\u8bf7\u6c42\u83b7\u53d6\u7528\u6237\u4fe1\u606f\u63a5\u53e3\u62a5\u9519", e);
            }
        }
        if ("GET".equalsIgnoreCase(method)) {
            ResponseEntity response;
            HttpEntity requestEntity = new HttpEntity((MultiValueMap)headers);
            logger.info("\u4ee5\u81ea\u5b9a\u4e49\u53c2\u6570\u65b9\u5f0f\u83b7\u53d6\u7528\u6237\u4fe1\u606f,\u8bf7\u6c42\u53c2\u6570: METHOD={}, URL={}", (Object)method, (Object)userInfoUrl);
            try {
                response = this.restTemplate.exchange(userInfoUrl, HttpMethod.GET, requestEntity, String.class, new HashMap(0));
            }
            catch (Exception e) {
                logger.warn("\u4ee5\u81ea\u5b9a\u4e49\u53c2\u6570\u65b9\u5f0fGET\u8bf7\u6c42\u83b7\u53d6\u7528\u6237\u4fe1\u606f\u63a5\u53e3\u62a5\u9519", e);
                throw new GcOAuth2Exception("\u4ee5\u81ea\u5b9a\u4e49\u53c2\u6570\u65b9\u5f0fGET\u8bf7\u6c42\u83b7\u53d6\u7528\u6237\u4fe1\u606f\u63a5\u53e3\u62a5\u9519", e);
            }
            if (HttpStatus.OK.equals((Object)response.getStatusCode())) {
                responseStr = (String)response.getBody();
            } else {
                logger.warn("\u4ee5\u81ea\u5b9a\u4e49\u53c2\u6570\u65b9\u5f0fGET\u8bf7\u6c42\u83b7\u53d6\u7528\u6237\u4fe1\u606f\u63a5\u53e3\uff0c\u8fd4\u56de\u72b6\u6001\u7801\u4e0d\u4e3a\u6b63\u786e\u3002STATUS_CODE={}", (Object)response.getStatusCodeValue());
            }
        }
        logger.info("\u6253\u5370\u7528\u6237\u4fe1\u606f\u8fd4\u56de\u4f53\uff1a{}", (Object)responseStr);
        return responseStr;
    }

    protected static HttpServletRequest getHttpServletRequest() {
        return ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
    }
}

