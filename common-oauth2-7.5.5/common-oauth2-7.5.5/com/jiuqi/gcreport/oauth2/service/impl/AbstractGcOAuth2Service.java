/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jayway.jsonpath.JsonPath
 *  com.jayway.jsonpath.PathNotFoundException
 *  com.jayway.jsonpath.Predicate
 *  com.jiuqi.np.user.feign.client.NvwaUserClient
 *  com.jiuqi.nvwa.certification.bean.NvwaCertify
 *  com.jiuqi.nvwa.certification.service.INvwaCertifyService
 *  javax.servlet.http.HttpServletResponse
 *  org.apache.commons.lang3.ObjectUtils
 *  org.springframework.http.HttpEntity
 *  org.springframework.http.HttpHeaders
 *  org.springframework.http.MediaType
 *  org.springframework.web.client.RestTemplate
 */
package com.jiuqi.gcreport.oauth2.service.impl;

import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.PathNotFoundException;
import com.jayway.jsonpath.Predicate;
import com.jiuqi.gcreport.oauth2.pojo.CustomParam;
import com.jiuqi.gcreport.oauth2.pojo.GcCertifyExtInfo;
import com.jiuqi.gcreport.oauth2.pojo.GcSsoBuildDTO;
import com.jiuqi.gcreport.oauth2.service.CustomParamService;
import com.jiuqi.gcreport.oauth2.service.GcOAuth2Service;
import com.jiuqi.gcreport.oauth2.service.Oauth2SsoLocationService;
import com.jiuqi.gcreport.oauth2.util.Base64Utils;
import com.jiuqi.gcreport.oauth2.util.NvwaCertifyThreadLocal;
import com.jiuqi.gcreport.oauth2.util.NvwaCertifyUtil;
import com.jiuqi.gcreport.oauth2.util.UrlEncodeUtil;
import com.jiuqi.np.user.feign.client.NvwaUserClient;
import com.jiuqi.nvwa.certification.bean.NvwaCertify;
import com.jiuqi.nvwa.certification.service.INvwaCertifyService;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

public abstract class AbstractGcOAuth2Service
implements GcOAuth2Service {
    protected static final Logger logger = LoggerFactory.getLogger(AbstractGcOAuth2Service.class);
    @Lazy
    @Autowired
    protected INvwaCertifyService nvwaCertifyService;
    @Autowired
    protected Oauth2SsoLocationService ssoLocationService;
    @Autowired
    @Qualifier(value="ignoreSSLRestTemplate")
    protected RestTemplate restTemplate;
    @Autowired
    private CustomParamService customParamService;
    @Lazy
    @Autowired
    private NvwaUserClient userClient;

    @Override
    public String getAuthUrl(NvwaCertify nc) {
        GcCertifyExtInfo extInfo = NvwaCertifyUtil.getExtInfoFromJson(nc);
        String authUrl = this.handleBaseUrlWithUri(nc.getUrl(), extInfo.getAuthUrl());
        StringBuilder sb = new StringBuilder();
        CustomParam customParam = null;
        if (extInfo.getCustomParams() != null && (customParam = extInfo.getCustomParams().get("AUTH_REDIRECT")) != null) {
            authUrl = this.customParamService.getAuthorizationUrl(authUrl, nc, extInfo, customParam, true);
            sb.append(authUrl);
        } else {
            sb.append(authUrl).append("?client_id=").append(nc.getClientid()).append("&response_type=code");
            this.appendRedirectUri(extInfo, sb);
        }
        return sb.toString();
    }

    protected void appendRedirectUri(GcCertifyExtInfo ext, StringBuilder builder) {
        if (ObjectUtils.isNotEmpty((Object)ext.getEncodeRedirectUri()) && ext.getEncodeRedirectUri().booleanValue()) {
            logger.debug("\u5df2\u542f\u7528encodeRedirectUri\uff0c\u56de\u8c03\u5730\u5740\u5c06\u52a0\u5bc6");
            builder.append("&redirect_uri=").append(UrlEncodeUtil.encode(ext.getRedirectUri()));
        } else {
            logger.debug("\u672a\u542f\u7528encodeRedirectUri\uff0c\u56de\u8c03\u5730\u5740\u4f7f\u7528\u539f\u59cb\u4e0d\u53d8");
            builder.append("&redirect_uri=").append(ext.getRedirectUri());
        }
    }

    @Override
    public String getAccessToken(String code) {
        String accessToken = null;
        String responseStr = null;
        NvwaCertify nc = NvwaCertifyThreadLocal.get();
        GcCertifyExtInfo extInfo = NvwaCertifyUtil.getExtInfoFromJson(nc);
        Assert.hasText(extInfo.getTokenUrl(), "\u83b7\u53d6access_token\u63a5\u53e3\u5730\u5740\u4e0d\u80fd\u4e3a\u7a7a");
        String tokenUrl = this.handleBaseUrlWithUri(nc.getUrl(), extInfo.getTokenUrl());
        Map<String, CustomParam> customParams = null;
        CustomParam customParam = null;
        customParams = extInfo.getCustomParams();
        if (customParams != null && (customParam = customParams.get("ACCESS_TOKEN")) != null) {
            responseStr = this.customParamService.getAccessToken(nc, extInfo, customParam, tokenUrl);
        } else {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
            params.add("client_id", nc.getClientid());
            params.add("client_secret", nc.getClientsecret());
            params.add("code", code);
            params.add("grant_type", "authorization_code");
            HttpEntity request = new HttpEntity(params, (MultiValueMap)headers);
            logger.debug("\u6253\u5370\u83b7\u53d6\u8bbf\u95ee\u51ed\u636e\u5730\u5740\uff1a{}", (Object)tokenUrl);
            try {
                responseStr = (String)this.restTemplate.postForObject(tokenUrl, (Object)request, String.class, new HashMap(0));
                logger.debug("\u6253\u5370\u8bbf\u95ee\u51ed\u636e\u8fd4\u56de\u4f53\uff1a{}", (Object)responseStr);
            }
            catch (Exception e) {
                logger.error("\u83b7\u53d6\u8bbf\u95ee\u51ed\u636e\u63a5\u53e3\u62a5\u9519", e);
                throw new RuntimeException(e);
            }
        }
        if (StringUtils.hasText(extInfo.getAccessTokenInFormKey())) {
            return this.getSpecifyFiledVal(responseStr, extInfo.getAccessTokenInFormKey());
        }
        try {
            accessToken = (String)JsonPath.read((String)responseStr, (String)("$." + extInfo.getAccessTokenField()), (Predicate[])new Predicate[0]);
        }
        catch (PathNotFoundException e) {
            logger.info("\u83b7\u53d6\u8bbf\u95ee\u51ed\u636e\u8fd4\u56de\u4f53\u4e2d\u4e0d\u5305\u542b{}\u5b57\u6bb5\uff0c\u6253\u5370\u8fd4\u56de\u4f53\uff1a{}", (Object)extInfo.getAccessTokenField(), (Object)responseStr);
            throw new RuntimeException("\u672a\u83b7\u53d6\u5230\u8bbf\u95ee\u51ed\u636e");
        }
        if (ObjectUtils.isEmpty((Object)accessToken)) {
            logger.info("\u8bbf\u95ee\u51ed\u636e\u4e3a\u7a7a\uff0c\u8bf7\u8054\u7cfb\u8ba4\u8bc1\u4e2d\u5fc3\u5904\u7406");
            throw new RuntimeException("\u8bbf\u95ee\u51ed\u636e\u4e3a\u7a7a\uff0c\u8bf7\u8054\u7cfb\u8ba4\u8bc1\u4e2d\u5fc3\u5904\u7406");
        }
        return accessToken;
    }

    @Override
    public String getLoginUserName(String accessToken) {
        NvwaCertify nc = NvwaCertifyThreadLocal.get();
        GcCertifyExtInfo ext = NvwaCertifyUtil.getExtInfoFromJson(nc);
        logger.info("\u53d6\u51fa\u6269\u5c55\u4fe1\u606f: {}", (Object)ext);
        Assert.hasText(ext.getUserInfoUrl(), "\u7528\u6237\u4fe1\u606f\u63a5\u53e3\u5730\u5740\u4e0d\u80fd\u4e3a\u7a7a");
        String userInfoUrl = this.handleBaseUrlWithUri(nc.getUrl(), ext.getUserInfoUrl());
        String result = this.getUserInfoResult(accessToken, nc, userInfoUrl);
        logger.debug("\u8f93\u51fa\u83b7\u53d6\u5230\u7528\u6237\u4fe1\u606f\u7684\u5185\u5bb9: ", (Object)result);
        String username = null;
        try {
            if (ObjectUtils.isEmpty((Object)ext.getUsernameField()) || "spRoleList".equals(ext.getUsernameField())) {
                username = (String)JsonPath.read((String)result, (String)"$.spRoleList[0]", (Predicate[])new Predicate[0]);
                logger.info("\u4ecespRoleList\u4e2d\u83b7\u53d6\u7528\u6237\u540d\uff1a{}", (Object)username);
            } else {
                username = (String)JsonPath.read((String)result, (String)("$." + ext.getUsernameField()), (Predicate[])new Predicate[0]);
                logger.info("\u4ece{}\u4e2d\u83b7\u53d6\u7528\u6237\u540d\uff1a{}", (Object)ext.getUsernameField(), (Object)username);
            }
        }
        catch (Exception e) {
            logger.warn("\u8bfb\u53d6\u7528\u6237\u4fe1\u606fjson\u53d6\u7528\u6237\u540d\u65f6\u51fa\u73b0\u9519\u8bef\uff0c\u8bf7\u53c2\u8003\u4e0b\u5217\u65e5\u5fd7\u4fee\u6539\u8bfb\u53d6\u7528\u6237\u540d\u5b57\u6bb5", e);
        }
        if (ObjectUtils.isEmpty(username)) {
            logger.info("\u672a\u83b7\u53d6\u5230\u7528\u6237\u540d\uff0c\u8bf7\u68c0\u67e5\u53d6\u7684\u5b57\u6bb5\u662f\u5426\u6b63\u786e\uff0c\u539f\u59cbjson: {}", (Object)result);
            throw new RuntimeException("\u672a\u83b7\u53d6\u5230\u7528\u6237\u540d");
        }
        logger.info("\u6269\u5c55\u53c2\u6570\u4e2d\u662f\u5426\u5305\u542b\u53cd\u67e5\u767b\u5f55\u540d\uff1f {}", (Object)StringUtils.hasText(ext.getReverseLookupUserAttrName()));
        if (StringUtils.hasText(ext.getReverseLookupUserAttrName())) {
            List nvwaUserIds = this.userClient.getUsersByAttrValue(ext.getReverseLookupUserAttrName(), username);
            if (!CollectionUtils.isEmpty(nvwaUserIds)) {
                String loginName = this.userClient.find((String)nvwaUserIds.get(0)).getName();
                logger.info("\u7528\u6237\u6269\u5c55\u5b57\u6bb5{}={}\u53cd\u67e5\u51fa\u7528\u6237\u767b\u5f55\u540d{}", ext.getReverseLookupUserAttrName(), username, loginName);
                username = loginName;
            } else {
                logger.warn("\u7528\u6237\u6269\u5c55\u5b57\u6bb5{}={}\u53cd\u67e5\u7528\u6237\u767b\u5f55\u540d\uff0c\u672a\u627e\u5230\u5bf9\u5e94\u7684\u7528\u6237\uff0c\u5c06\u4ee5\u53d6\u5230\u7684\u7528\u6237\u540d{}\u5c1d\u8bd5\u767b\u5f55!", ext.getReverseLookupUserAttrName(), username, username);
            }
        }
        logger.info("\u83b7\u53d6\u5230\u7528\u6237\u540d\uff1a{}", (Object)username);
        return username;
    }

    protected String getUserInfoResult(String accessToken, NvwaCertify nc, String userInfoUrl) {
        String userInfoResponse = null;
        CustomParam customParam = null;
        GcCertifyExtInfo ext = NvwaCertifyUtil.getExtInfoFromJson(nc);
        if (ext.getCustomParams() != null && (customParam = ext.getCustomParams().get("USER_INFO")) != null) {
            userInfoResponse = this.customParamService.getUserInfo(nc, customParam, accessToken, userInfoUrl);
            return userInfoResponse;
        }
        String getUserInfoUrl = userInfoUrl + "?client_id=" + nc.getClientid() + "&access_token=" + accessToken;
        logger.debug("\u6253\u5370\u83b7\u53d6\u767b\u5f55\u7528\u6237\u540d\u5730\u5740\uff1a{}", (Object)getUserInfoUrl);
        try {
            return (String)this.restTemplate.getForObject(getUserInfoUrl, String.class, new HashMap(0));
        }
        catch (Exception e) {
            logger.error("\u8bf7\u6c42\u7528\u6237\u4fe1\u606f\u63a5\u53e3\u62a5\u9519", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void loginSuccessRedirect(String username, String uiCode, HttpServletResponse response) throws IOException {
        NvwaCertify nc = NvwaCertifyThreadLocal.get();
        GcCertifyExtInfo ext = NvwaCertifyUtil.getExtInfoFromJson(nc);
        GcSsoBuildDTO ssoBuildDto = new GcSsoBuildDTO();
        ssoBuildDto.setFrontAddress(this.handleFrontendUrl(nc));
        ssoBuildDto.setUserName(username);
        if (ext.getJumpType() == null || "go".equals(ext.getJumpType())) {
            ssoBuildDto.setJumpType("go");
        } else {
            ssoBuildDto.setJumpType("app");
        }
        if (ObjectUtils.isNotEmpty((Object)ext.getAppName())) {
            ssoBuildDto.setAppName(ext.getAppName());
        }
        ssoBuildDto.setAddTokenId(true);
        if (ext.getStateIsUiCode().booleanValue()) {
            ssoBuildDto.setUiCode(uiCode);
        }
        String url = this.ssoLocationService.buildCurSsoLocation(ssoBuildDto);
        logger.info("\u7528\u6237{}\u767b\u5f55\u6210\u529f\uff0c\u6b63\u5728\u8df3\u8f6c\uff1a{}", (Object)username, (Object)url);
        response.sendRedirect(url);
    }

    protected String handleFrontendUrl(NvwaCertify nc) {
        String frontUrl = nc.getFrontendURL();
        GcCertifyExtInfo extInfo = NvwaCertifyUtil.getExtInfoFromJson(nc);
        if (extInfo.getHashModeRouter() != null && !extInfo.getHashModeRouter().booleanValue()) {
            return frontUrl;
        }
        if (ObjectUtils.isEmpty((Object)frontUrl)) {
            logger.warn("\u5f53\u524d\u524d\u7aef\u5730\u5740\u672a\u8bbe\u7f6e\uff0c\u5c06\u4f5c\u4e3awar\u90e8\u7f72\u65b9\u5f0f\u76f8\u5bf9\u8def\u5f84\u751f\u6210\u767b\u5f55\u5730\u5740");
            return "/#/";
        }
        frontUrl = frontUrl.indexOf("#") != -1 ? frontUrl.substring(0, frontUrl.indexOf("#") + 1) + "/" : (frontUrl.endsWith("/") ? frontUrl + "#/" : frontUrl + "/#/");
        logger.debug("\u6253\u5370\u5904\u7406\u8fc7\u7684\u524d\u7aef\u5730\u5740: {}", (Object)frontUrl);
        return frontUrl;
    }

    protected String handleBaseUrlWithUri(String baseUrl, String uri) {
        if (ObjectUtils.isEmpty((Object)baseUrl) || ObjectUtils.isNotEmpty((Object)uri) && uri.startsWith("http")) {
            logger.debug("\u6388\u6743\u4e2d\u5fc3\u57fa\u7840\u5730\u5740\u4e3a\u7a7a\u6216uri\u4ee5http\u5f00\u5934\uff0c\u5c06\u76f4\u63a5\u4f7f\u7528uri\uff08{}\uff09\u90e8\u5206", (Object)uri);
            return uri;
        }
        if (baseUrl.endsWith("/")) {
            baseUrl = baseUrl.substring(0, baseUrl.length() - 1);
        }
        if (ObjectUtils.isNotEmpty((Object)uri) && !uri.startsWith("/")) {
            return baseUrl + "/" + uri;
        }
        return baseUrl + uri;
    }

    @Override
    public void todoRedirectDataentry(String username, String appConfigEncodeStr, HttpServletResponse response) throws IOException {
        NvwaCertify nc = NvwaCertifyThreadLocal.get();
        GcSsoBuildDTO ssoBuildDto = new GcSsoBuildDTO();
        ssoBuildDto.setFrontAddress(this.handleFrontendUrl(nc));
        ssoBuildDto.setUserName(username);
        ssoBuildDto.setAddTokenId(true);
        ssoBuildDto.setScope("@nr");
        ssoBuildDto.setAppName("dataentry");
        ssoBuildDto.setAppConfig(Base64Utils.base64Decode(appConfigEncodeStr));
        String url = this.ssoLocationService.buildCurSsoLocation(ssoBuildDto);
        logger.info("\u7528\u6237{}\u767b\u5f55\u6210\u529f\uff0c\u6b63\u5728\u8df3\u8f6c\u6570\u636e\u5f55\u5165\uff1a{}", (Object)username, (Object)url);
        response.sendRedirect(url);
    }

    @Override
    public void agileRedirect(String username, String genUrlBase64, HttpServletResponse response) throws IOException {
        String genUrl = Base64Utils.base64Decode(genUrlBase64);
        NvwaCertify nc = NvwaCertifyThreadLocal.get();
        String url = this.ssoLocationService.buildCurSsoLocationFromGenUrl(genUrl, this.handleFrontendUrl(nc), username);
        logger.info("\u7528\u6237{}\u767b\u5f55\u6210\u529f\uff0c\u6b63\u5728\u7075\u6d3b\u8df3\u8f6c\uff1a{}", (Object)username, (Object)url);
        response.sendRedirect(url);
    }

    private String getSpecifyFiledVal(String str, String specifyField) {
        Assert.hasText(str, "\u5b57\u7b26\u4e32\u4e0d\u80fd\u4e3a\u7a7a");
        String[] keysValues = str.split("&");
        String filedPrefix = specifyField + "=";
        for (int i = 0; i < keysValues.length; ++i) {
            if (!keysValues[i].startsWith(filedPrefix)) continue;
            return keysValues[i].split("=")[1];
        }
        throw new RuntimeException("\u672a\u83b7\u53d6\u5230\u6307\u5b9a\u53c2\u6570[" + specifyField + "]\u7684\u503c\uff0c\u8bf7\u68c0\u67e5");
    }
}

