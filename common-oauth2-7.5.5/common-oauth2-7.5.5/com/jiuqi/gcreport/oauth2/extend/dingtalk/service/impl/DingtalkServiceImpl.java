/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jayway.jsonpath.JsonPath
 *  com.jayway.jsonpath.PathNotFoundException
 *  com.jayway.jsonpath.Predicate
 *  com.jiuqi.np.user.feign.client.NvwaUserAttributeClient
 *  com.jiuqi.np.user.feign.client.NvwaUserClient
 *  com.jiuqi.nvwa.certification.bean.NvwaCertify
 *  com.jiuqi.nvwa.login.domain.NvwaLoginUserDTO
 *  com.jiuqi.nvwa.login.service.NvwaLoginService
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.user.UserDO
 *  com.jiuqi.va.domain.user.UserDTO
 *  com.jiuqi.va.feign.client.AuthUserClient
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 *  org.springframework.http.HttpEntity
 *  org.springframework.http.HttpHeaders
 *  org.springframework.http.HttpMethod
 *  org.springframework.http.MediaType
 *  org.springframework.http.ResponseEntity
 *  org.springframework.web.client.RestTemplate
 */
package com.jiuqi.gcreport.oauth2.extend.dingtalk.service.impl;

import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.PathNotFoundException;
import com.jayway.jsonpath.Predicate;
import com.jiuqi.gcreport.oauth2.dao.NvwaCertifyDaoExtend;
import com.jiuqi.gcreport.oauth2.exception.GcOAuth2Exception;
import com.jiuqi.gcreport.oauth2.extend.dingtalk.DingtalkCertifyExtInfo;
import com.jiuqi.gcreport.oauth2.extend.dingtalk.pojo.DingtalkConfigCombine;
import com.jiuqi.gcreport.oauth2.extend.dingtalk.service.DingtalkService;
import com.jiuqi.gcreport.oauth2.extend.dingtalk.util.DingtalkNvwaCertifyUtil;
import com.jiuqi.gcreport.oauth2.extend.dingtalk.util.DingtalkThreadLocal;
import com.jiuqi.gcreport.oauth2.util.JsonUtils;
import com.jiuqi.gcreport.oauth2.util.UrlEncodeUtil;
import com.jiuqi.np.user.feign.client.NvwaUserAttributeClient;
import com.jiuqi.np.user.feign.client.NvwaUserClient;
import com.jiuqi.nvwa.certification.bean.NvwaCertify;
import com.jiuqi.nvwa.login.domain.NvwaLoginUserDTO;
import com.jiuqi.nvwa.login.service.NvwaLoginService;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.user.UserDO;
import com.jiuqi.va.domain.user.UserDTO;
import com.jiuqi.va.feign.client.AuthUserClient;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

@Service(value="com.jiuqi.gcreport.dingtalk.service.DingtalkService")
public class DingtalkServiceImpl
implements DingtalkService {
    private static final Logger logger = LoggerFactory.getLogger(DingtalkServiceImpl.class);
    @Autowired
    @Lazy
    private NvwaLoginService loginService;
    @Autowired
    @Lazy
    private NvwaUserClient userClient;
    @Autowired
    @Lazy
    private NvwaUserAttributeClient userAttrClient;
    @Autowired
    @Qualifier(value="ignoreSSLRestTemplate")
    @Lazy
    protected RestTemplate restTemplate;
    @Autowired
    @Qualifier(value="com.jiuqi.gcreport.oauth2.dao.impl.NvwaCertifyDaoExtend")
    private NvwaCertifyDaoExtend nvwaCertifyDaoExtend;

    @Override
    public String getAccessToken(String authCode) {
        logger.info("\u9489\u9489\u5355\u70b9\u83b7\u53d6\u6388\u6743token");
        Assert.notNull((Object)authCode, "\u9489\u9489\u83b7\u53d6\u6388\u6743token\u7684authCode\u4e0d\u80fd\u4e3a\u7a7a");
        DingtalkConfigCombine combine = DingtalkThreadLocal.get();
        NvwaCertify nvwaCertify = combine.getNvwaCertify();
        String url = "https://api.dingtalk.com/v1.0/oauth2/userAccessToken";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        LinkedHashMap<String, String> params = new LinkedHashMap<String, String>();
        params.put("clientId", nvwaCertify.getClientid());
        params.put("clientSecret", nvwaCertify.getClientsecret());
        params.put("code", authCode);
        params.put("grantType", "authorization_code");
        String writeValueAsString = JsonUtils.writeValueAsString(params);
        logger.debug("\u9489\u9489\u5355\u70b9\u83b7\u53d6\u6388\u6743token\u8bf7\u6c42\u4f53\uff1a{}", (Object)writeValueAsString);
        HttpEntity request = new HttpEntity((Object)writeValueAsString, (MultiValueMap)headers);
        String responseStr = null;
        try {
            responseStr = (String)this.restTemplate.postForObject(url, (Object)request, String.class, new HashMap(0));
            logger.warn("\u6253\u5370\u8bbf\u95ee\u51ed\u636e\u8fd4\u56de\u4f53\uff1a{}", (Object)responseStr);
            try {
                return (String)JsonPath.read((String)responseStr, (String)"$.accessToken", (Predicate[])new Predicate[0]);
            }
            catch (PathNotFoundException e) {
                logger.warn("\u8bbf\u95ee\u51ed\u636e\u8fd4\u56de\u4f53\u4e0d\u5305\u542baccessToken\u5b57\u6bb5");
            }
        }
        catch (Exception e) {
            logger.warn("\u83b7\u53d6\u8bbf\u95ee\u51ed\u636e\u63a5\u53e3\u62a5\u9519", e);
        }
        return null;
    }

    @Override
    public String getUserInfo(String accessToken) {
        logger.info("\u9489\u9489\u5355\u70b9\u83b7\u53d6\u7528\u6237\u4fe1\u606f");
        Assert.notNull((Object)accessToken, "\u9489\u9489\u83b7\u53d6\u7528\u6237\u4fe1\u606f\u7684accessToken\u4e0d\u80fd\u4e3a\u7a7a");
        DingtalkCertifyExtInfo extInfo = DingtalkThreadLocal.getExtInfo();
        String url = "https://api.dingtalk.com/v1.0/contact/users/{unionId}".replace("{unionId}", extInfo.getUnionId());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("x-acs-dingtalk-access-token", accessToken);
        HttpEntity request = new HttpEntity((MultiValueMap)headers);
        try {
            ResponseEntity response = this.restTemplate.exchange(url, HttpMethod.GET, request, String.class, new Object[0]);
            logger.info("\u6253\u5370\u7528\u6237\u4fe1\u606f\u8fd4\u56de\u4f53\uff1a{}", response.getBody());
            try {
                return (String)JsonPath.read((String)((String)response.getBody()), (String)("$." + extInfo.getUsernameField()), (Predicate[])new Predicate[0]);
            }
            catch (PathNotFoundException e) {
                logger.warn("\u7528\u6237\u4fe1\u606f\u8fd4\u56de\u4f53\u4e0d\u5305\u542b{}\u5b57\u6bb5", (Object)extInfo.getUsernameField());
            }
        }
        catch (Exception e) {
            logger.warn("\u83b7\u53d6\u7528\u6237\u4fe1\u606f\u63a5\u53e3\u62a5\u9519", e);
            throw new GcOAuth2Exception("\u83b7\u53d6\u7528\u6237\u4fe1\u606f\u63a5\u53e3\u62a5\u9519", e);
        }
        return null;
    }

    @Override
    public String getRelatedPeopleUsername(String username) {
        Assert.notNull((Object)username, "\u9489\u9489\u67e5\u627e\u7cfb\u7edf\u5185\u5bf9\u5e94\u7528\u6237\u7684\u7528\u6237\u4fe1\u606f\u4f53\u4e0d\u80fd\u4e3a\u7a7a");
        DingtalkCertifyExtInfo extInfo = DingtalkThreadLocal.getExtInfo();
        boolean hasReverseLookupUserField = StringUtils.hasText(extInfo.getReverseLookupUserField());
        boolean hasReverseLookupUserAttrName = StringUtils.hasText(extInfo.getReverseLookupUserAttrName());
        if (hasReverseLookupUserField) {
            logger.info("\u6269\u5c55\u53c2\u6570\u4e2d\u5305\u542breverseLookupUserField\uff1f {}", (Object)hasReverseLookupUserField);
            if (!"telephone".equals(extInfo.getReverseLookupUserField()) && !"email".equals(extInfo.getReverseLookupUserField())) {
                logger.warn("reverseLookupUserField={}\u4e0d\u652f\u6301\uff0c\u5c06\u4ee5\u53d6\u5230\u7684\u7528\u6237\u540d{}\u5c1d\u8bd5\u767b\u5f55!", (Object)extInfo.getReverseLookupUserField(), (Object)username);
            } else {
                AuthUserClient authUserClient = (AuthUserClient)ApplicationContextRegister.getBean(AuthUserClient.class);
                UserDTO userParam = new UserDTO();
                userParam.setTenantName("__default_tenant__");
                PageVO users = authUserClient.list(userParam);
                for (UserDO userDO : users.getRows()) {
                    if ("telephone".equals(extInfo.getReverseLookupUserField())) {
                        if (userDO.getTelephone() == null || !userDO.getTelephone().equals(username)) continue;
                        username = userDO.getUsername();
                        logger.info("reverseLookupUserField={}\u53cd\u67e5\u51fa\u7528\u6237\u767b\u5f55\u540d{}", (Object)extInfo.getReverseLookupUserField(), (Object)username);
                    } else {
                        if (!"email".equals(extInfo.getReverseLookupUserField()) || userDO.getEmail() == null || !userDO.getEmail().equals(username) && !userDO.getEmail().startsWith(username + "@")) continue;
                        username = userDO.getUsername();
                        logger.info("reverseLookupUserField={}\u53cd\u67e5\u51fa\u7528\u6237\u767b\u5f55\u540d{}", (Object)extInfo.getReverseLookupUserField(), (Object)username);
                    }
                    break;
                }
            }
        } else if (hasReverseLookupUserAttrName) {
            logger.info("\u6269\u5c55\u53c2\u6570\u4e2d\u5305\u542breverseLookupUserAttrName\uff1f {}", (Object)hasReverseLookupUserAttrName);
            List nvwaUserIds = this.userClient.getUsersByAttrValue(extInfo.getReverseLookupUserAttrName(), username);
            if (!CollectionUtils.isEmpty(nvwaUserIds)) {
                String loginName = this.userClient.find((String)nvwaUserIds.get(0)).getName();
                logger.info("\u7528\u6237\u6269\u5c55\u5b57\u6bb5{}={}\u53cd\u67e5\u51fa\u7528\u6237\u767b\u5f55\u540d{}", extInfo.getReverseLookupUserAttrName(), username, loginName);
                username = loginName;
            } else {
                logger.warn("\u7528\u6237\u6269\u5c55\u5b57\u6bb5{}={}\u53cd\u67e5\u7528\u6237\u767b\u5f55\u540d\uff0c\u672a\u627e\u5230\u5bf9\u5e94\u7684\u7528\u6237\uff0c\u5c06\u4ee5\u53d6\u5230\u7684\u7528\u6237\u540d{}\u5c1d\u8bd5\u767b\u5f55!", extInfo.getReverseLookupUserAttrName(), username, username);
            }
        } else {
            logger.info("\u6269\u5c55\u53c2\u6570\u4e2d\u4e0d\u5305\u542breverseLookupUserField\u4e0ereverseLookupUserAttrName\uff0c\u76f4\u63a5\u8fd4\u56de\u6620\u5c04\u5b57\u6bb5({})\u4f5c\u4e3a\u767b\u5f55\u540d", (Object)username);
        }
        logger.info("\u83b7\u53d6\u5230\u7528\u6237\u540d\uff1a{}", (Object)username);
        return username;
    }

    @Override
    public R innerLogin(String username) {
        Assert.notNull((Object)username, "\u5185\u90e8\u767b\u5f55\u672a\u4f20\u9012\u7528\u6237\u540d");
        NvwaLoginUserDTO userDTO = new NvwaLoginUserDTO();
        userDTO.setUsername(username);
        userDTO.setTenant("__default_tenant__");
        userDTO.setCheckPwd(false);
        return this.loginService.tryLogin(userDTO, true);
    }

    @Override
    public String getLoginUrl(NvwaCertify nvwaCertify) {
        DingtalkCertifyExtInfo extInfoFromJson = DingtalkNvwaCertifyUtil.getExtInfoFromJson(nvwaCertify);
        String redirectUriUrlEncoded = UrlEncodeUtil.encode(extInfoFromJson.getRedirectUri());
        String loginUrl = "https://login.dingtalk.com/oauth2/auth?redirect_uri={redirect_uri}&response_type=code&client_id={client_id}&state={state}&scope=openid&prompt=consent".replace("{redirect_uri}", redirectUriUrlEncoded).replace("{client_id}", nvwaCertify.getClientid()).replace("{state}", "jiuqi");
        return loginUrl;
    }
}

