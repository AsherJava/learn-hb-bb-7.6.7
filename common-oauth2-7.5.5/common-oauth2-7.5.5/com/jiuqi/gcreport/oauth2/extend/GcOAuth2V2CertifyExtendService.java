/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jayway.jsonpath.JsonPath
 *  com.jayway.jsonpath.PathNotFoundException
 *  com.jayway.jsonpath.Predicate
 *  com.jiuqi.np.authz2.service.RoleService
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.np.user.User
 *  com.jiuqi.np.user.entity.UserEntity
 *  com.jiuqi.np.user.feign.client.NvwaUserClient
 *  com.jiuqi.np.user.service.PasswordService
 *  com.jiuqi.np.user.service.UserService
 *  com.jiuqi.nvwa.certification.bean.NvwaCertify
 *  com.jiuqi.nvwa.certification.dto.ThirdAuthorizeDTO
 *  com.jiuqi.nvwa.certification.extend.INvwaCertifyExtendService
 *  com.jiuqi.nvwa.framework.nros.service.IRouteService
 *  com.jiuqi.nvwa.intergration.sdk.cer.dto.NvwaTokenDTO
 *  com.jiuqi.nvwa.intergration.sdk.cer.dto.NvwaUserDTO
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.user.UserLoginDTO
 *  javax.servlet.http.HttpServletRequest
 *  org.apache.commons.collections4.CollectionUtils
 *  org.apache.commons.lang3.ObjectUtils
 *  org.apache.commons.lang3.StringUtils
 *  org.springframework.http.HttpEntity
 *  org.springframework.http.HttpHeaders
 *  org.springframework.http.MediaType
 *  org.springframework.web.client.RestTemplate
 *  org.springframework.web.context.request.RequestContextHolder
 *  org.springframework.web.context.request.ServletRequestAttributes
 */
package com.jiuqi.gcreport.oauth2.extend;

import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.PathNotFoundException;
import com.jayway.jsonpath.Predicate;
import com.jiuqi.gcreport.oauth2.cache.OAuth2CertifyServiceCache;
import com.jiuqi.gcreport.oauth2.exception.GcOAuth2Exception;
import com.jiuqi.gcreport.oauth2.exception.UserNotFoundException;
import com.jiuqi.gcreport.oauth2.pojo.CustomParam;
import com.jiuqi.gcreport.oauth2.pojo.GcCertifyExtInfo;
import com.jiuqi.gcreport.oauth2.service.BindUserContextService;
import com.jiuqi.gcreport.oauth2.service.CustomParamService;
import com.jiuqi.gcreport.oauth2.service.NvwaUiSchemeExtendService;
import com.jiuqi.gcreport.oauth2.util.Base64Utils;
import com.jiuqi.gcreport.oauth2.util.FingerprintUtil;
import com.jiuqi.gcreport.oauth2.util.NvwaCertifyUtil;
import com.jiuqi.gcreport.oauth2.util.UrlEncodeUtil;
import com.jiuqi.np.authz2.service.RoleService;
import com.jiuqi.np.log.LogHelper;
import com.jiuqi.np.user.User;
import com.jiuqi.np.user.entity.UserEntity;
import com.jiuqi.np.user.feign.client.NvwaUserClient;
import com.jiuqi.np.user.service.PasswordService;
import com.jiuqi.np.user.service.UserService;
import com.jiuqi.nvwa.certification.bean.NvwaCertify;
import com.jiuqi.nvwa.certification.dto.ThirdAuthorizeDTO;
import com.jiuqi.nvwa.certification.extend.INvwaCertifyExtendService;
import com.jiuqi.nvwa.framework.nros.service.IRouteService;
import com.jiuqi.nvwa.intergration.sdk.cer.dto.NvwaTokenDTO;
import com.jiuqi.nvwa.intergration.sdk.cer.dto.NvwaUserDTO;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.user.UserLoginDTO;
import java.time.Instant;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.Assert;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class GcOAuth2V2CertifyExtendService
implements INvwaCertifyExtendService {
    protected static final Logger logger = LoggerFactory.getLogger(GcOAuth2V2CertifyExtendService.class);
    protected static final String LOG_MODULE_NAME = "\u5408\u5e76-OAUTH2\u5355\u70b9\u767b\u5f55";
    protected static final String LOG_TITLE_ADD_USER = "\u901a\u8fc7\u5355\u70b9\u767b\u5f55\u65b0\u589e\u7528\u6237\u3010%s\u3011\u6210\u529f";
    @Value(value="${server.servlet.context-path:}")
    protected String contextPath;
    @Lazy
    @Autowired
    @Qualifier(value="ignoreSSLRestTemplate")
    protected RestTemplate restTemplate;
    @Lazy
    @Autowired
    protected UserService<User> userService;
    @Lazy
    @Autowired
    protected RoleService roleService;
    @Lazy
    @Autowired
    protected PasswordService passwordService;
    @Lazy
    @Autowired
    protected NvwaUiSchemeExtendService nvwaUiSchemeExtendService;
    @Lazy
    @Autowired
    private IRouteService iRouteService;
    @Lazy
    @Autowired
    private BindUserContextService bindUserContextService;
    @Lazy
    @Autowired
    private CustomParamService customParamService;
    @Lazy
    @Autowired
    private NvwaUserClient userClient;
    @Autowired
    private OAuth2CertifyServiceCache oAuth2CertifyServiceCache;

    public int getOrder() {
        return 101;
    }

    public String getType() {
        return "GC_OAUTH2_V2";
    }

    public String getTitle() {
        return "\u5408\u5e76\u62a5\u8868OAUTH2";
    }

    public boolean ssoPageCacheTokenId() {
        return false;
    }

    public String ssoPageCacheKey() {
        return "code";
    }

    public boolean ssoPageLoginRedirectUrlMode(NvwaCertify nvwaCertify) {
        return true;
    }

    public boolean ssoPageFullInterception(NvwaCertify nvwaCertify) {
        GcCertifyExtInfo ext = NvwaCertifyUtil.getExtInfoFromJson(nvwaCertify);
        return ext.getBlockLoginPage();
    }

    public ThirdAuthorizeDTO ssoPageThirdAuthorizeInfo(NvwaCertify nvwaCertify) {
        ThirdAuthorizeDTO thirdPageLoginDTO = new ThirdAuthorizeDTO();
        GcCertifyExtInfo extInfo = NvwaCertifyUtil.getExtInfoFromJson(nvwaCertify);
        StringBuilder authAddr = new StringBuilder();
        String baseUrl = GcOAuth2V2CertifyExtendService.joinUrl(nvwaCertify.getUrl(), extInfo.getAuthUrl());
        CustomParam customParam = null;
        if (extInfo.getCustomParams() != null && (customParam = extInfo.getCustomParams().get("AUTH_REDIRECT")) != null) {
            String authUrl = this.customParamService.getAuthorizationUrl(baseUrl, nvwaCertify, extInfo, customParam, false);
            authAddr.append(authUrl);
        } else {
            authAddr.append(baseUrl);
            authAddr.append("?response_type=code&client_id=").append(nvwaCertify.getClientid());
        }
        thirdPageLoginDTO.setAuthorizeAddress(authAddr.toString());
        thirdPageLoginDTO.setRedirectUrlKey("redirect_uri");
        return thirdPageLoginDTO;
    }

    public String ssoUnifiedLoginPage(NvwaCertify nvwaCertify) {
        GcCertifyExtInfo ext = NvwaCertifyUtil.getExtInfoFromJson(nvwaCertify);
        if (ext.getBlockLoginPage().booleanValue()) {
            if (StringUtils.isNotBlank((CharSequence)ext.getThirdPartyLoginPage())) {
                return ext.getThirdPartyLoginPage();
            }
            return this.getPrintMsgUrl(ext.getLoginPageBlockedTips(), nvwaCertify);
        }
        return null;
    }

    public String ssoRedirectUrlEncrypt(NvwaCertify nvwaCertify, String redirectUri) {
        GcCertifyExtInfo ext = NvwaCertifyUtil.getExtInfoFromJson(nvwaCertify);
        if (ext.getEncodeRedirectUri().booleanValue()) {
            return UrlEncodeUtil.encode(redirectUri);
        }
        return redirectUri;
    }

    public NvwaTokenDTO verifyTicket(NvwaCertify nvwaCertify, String ticketId, String redirectUri) {
        GcCertifyExtInfo extInfo = NvwaCertifyUtil.getExtInfoFromJson(nvwaCertify);
        try {
            Assert.hasText(ticketId, "\u6388\u6743\u7801\u4e0d\u80fd\u4e3a\u7a7a");
        }
        catch (IllegalArgumentException e) {
            logger.warn(e.getMessage(), e);
            return this.validateTicketOnException(e.getMessage(), nvwaCertify);
        }
        try {
            String accessToken = this.getAccessToken(nvwaCertify, extInfo, ticketId);
            String userInfoUrl = GcOAuth2V2CertifyExtendService.joinUrl(nvwaCertify.getUrl(), extInfo.getUserInfoUrl());
            String userInfoJsonStr = this.getUserInfo(nvwaCertify, extInfo, accessToken, userInfoUrl);
            String username = this.getUsernameInsideUserInfo(extInfo, userInfoJsonStr);
            boolean userExists = this.userService.exists(username);
            logger.info("\u5355\u70b9\u7528\u6237\u3010{}\u3011\u662f\u5426\u5b58\u5728: {}\uff0c\u5f53\u524d\u81ea\u52a8\u6dfb\u52a0\u7528\u6237\u6a21\u5f0f\u5f00\u542f\u72b6\u6001: {}", username, userExists, extInfo.getAutoAddUser());
            if (!userExists) {
                if (extInfo.getAutoAddUser().booleanValue()) {
                    this.addUser(username, userInfoJsonStr);
                } else {
                    throw new GcOAuth2Exception(String.format(extInfo.getUserNotFoundTipsTemplate(), username));
                }
            }
            NvwaUserDTO user = new NvwaUserDTO();
            user.setName(username);
            NvwaTokenDTO nvwaTokenDTO = new NvwaTokenDTO();
            nvwaTokenDTO.setTicketId(ticketId);
            nvwaTokenDTO.setUser(user);
            if (extInfo.getStateIsUiCode().booleanValue()) {
                String uiCode = GcOAuth2V2CertifyExtendService.getHttpServletRequest().getParameter("state");
                if (!this.nvwaUiSchemeExtendService.hasUiSchemeAuthority(username, uiCode)) {
                    logger.warn("\u7528\u6237[{}]\u6ca1\u6709\u754c\u9762\u65b9\u6848[{}]\u7684\u6743\u9650\uff0c\u5df2\u505a\u63d0\u793a\u3002", (Object)username, (Object)uiCode);
                    throw new GcOAuth2Exception(extInfo.getNoUiSchemePermissionsTips());
                }
                HashMap<String, String> userExtInfo = new HashMap<String, String>();
                userExtInfo.put("UI_SCHEME_ID", uiCode);
                user.setExtInfo(userExtInfo);
            }
            if (extInfo.getCheckFuncRoutes().booleanValue() && this.hasNoRoutes(username)) {
                logger.warn("\u7528\u6237[{}]\u6ca1\u6709\u529f\u80fd\u83dc\u5355\u7684\u6743\u9650\uff0c\u5df2\u505a\u63d0\u793a\u3002", (Object)username);
                throw new GcOAuth2Exception(String.format(extInfo.getHasNoFuncRoutesTipsTemplate(), username));
            }
            return nvwaTokenDTO;
        }
        catch (Exception e) {
            logger.warn(e.getMessage(), e);
            return this.validateTicketOnException(e.getMessage(), nvwaCertify);
        }
    }

    private boolean hasNoRoutes(String username) {
        boolean result = true;
        try {
            this.bindUserContextService.bindUser(username);
        }
        catch (UserNotFoundException e) {
            throw new GcOAuth2Exception("\u67e5\u8be2\u7528\u6237\u529f\u80fd\u8df3\u7531\u8fc7\u7a0b\u4e2d\uff0c\u7ed1\u5b9a\u4e0a\u4e0b\u6587\u7528\u6237\uff0c\u53d1\u73b0\u7528\u6237\u4e0d\u5b58\u5728\u3002");
        }
        List userRoutes = this.iRouteService.getUserRotes(false);
        if (CollectionUtils.isNotEmpty((Collection)userRoutes)) {
            result = false;
        }
        this.bindUserContextService.unbind();
        return result;
    }

    public void loginSuccess(String nvwToken, String ticketId, NvwaCertify nvwaCertify) {
        super.loginSuccess(nvwToken, ticketId, nvwaCertify);
        UserLoginDTO user = ShiroUtil.getUser();
        String fingerprint = FingerprintUtil.genFingerprint(user.getUsername(), this.contextPath);
        this.oAuth2CertifyServiceCache.putCerCode(fingerprint, nvwaCertify.getCode());
    }

    protected NvwaTokenDTO validateTicketOnException(String tips, NvwaCertify nc) {
        NvwaTokenDTO error = new NvwaTokenDTO();
        String tipsUrl = this.getPrintMsgUrl(tips, nc);
        error.setFailUrl(tipsUrl);
        return error;
    }

    protected void addUser(String username, String userInfo) {
        UserEntity ue = new UserEntity();
        ue.setName(username);
        ue.setNickname(username);
        Instant now = Instant.now();
        ue.setCreateTime(now);
        ue.setModifyTime(now);
        ue.setEnabled(Boolean.valueOf(true));
        ue.setCreator("sys_user_admin");
        String userId = this.userService.create((User)ue);
        String title = String.format(LOG_TITLE_ADD_USER, username);
        LogHelper.info((String)LOG_MODULE_NAME, (String)title, (String)("\u65b0\u589e\u7528\u6237\u8bf7\u6c42\u5bf9\u8c61:\n" + ue));
        String randomPassword = UUID.randomUUID().toString().replace("-", "@").substring(0, 9);
        this.passwordService.create(userId, randomPassword);
        this.roleService.grant("ffffffff-ffff-ffff-bbbb-ffffffffffff", userId);
    }

    protected String getAccessToken(NvwaCertify nvwaCertify, GcCertifyExtInfo extInfo, String ticketId) {
        String accessToken = null;
        String responseStr = null;
        String tokenUrl = GcOAuth2V2CertifyExtendService.joinUrl(nvwaCertify.getUrl(), extInfo.getTokenUrl());
        Map<String, CustomParam> customParams = null;
        CustomParam customParam = null;
        customParams = extInfo.getCustomParams();
        if (customParams != null && (customParam = customParams.get("ACCESS_TOKEN")) != null) {
            responseStr = this.customParamService.getAccessToken(nvwaCertify, extInfo, customParam, tokenUrl);
        } else {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
            params.add("client_id", nvwaCertify.getClientid());
            params.add("client_secret", nvwaCertify.getClientsecret());
            params.add("code", ticketId);
            params.add("grant_type", "authorization_code");
            HttpEntity request = new HttpEntity(params, (MultiValueMap)headers);
            logger.info("\u6253\u5370\u83b7\u53d6\u8bbf\u95ee\u51ed\u636e\u5730\u5740\uff1a{}", (Object)tokenUrl);
            try {
                responseStr = (String)this.restTemplate.postForObject(tokenUrl, (Object)request, String.class, new HashMap(0));
                logger.info("\u6253\u5370\u8bbf\u95ee\u51ed\u636e\u8fd4\u56de\u4f53\uff1a{}", (Object)responseStr);
            }
            catch (PathNotFoundException e) {
                logger.warn("\u83b7\u53d6\u8bbf\u95ee\u51ed\u636e\u8fd4\u56de\u4f53\u4e2d\u4e0d\u5305\u542baccess_token\u5b57\u6bb5");
                throw new GcOAuth2Exception("\u83b7\u53d6\u8bbf\u95ee\u51ed\u636e\u8fd4\u56de\u4f53\u4e2d\u4e0d\u5305\u542baccess_token\u5b57\u6bb5");
            }
            catch (Exception e) {
                logger.warn("\u83b7\u53d6\u8bbf\u95ee\u51ed\u636e\u63a5\u53e3\u62a5\u9519", e);
                throw new GcOAuth2Exception("\u83b7\u53d6\u8bbf\u95ee\u51ed\u636e\u63a5\u53e3\u62a5\u9519", e);
            }
        }
        if (StringUtils.isNotBlank((CharSequence)extInfo.getAccessTokenInFormKey())) {
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

    protected String getUserInfo(NvwaCertify nvwaCertify, GcCertifyExtInfo extInfo, String accessToken, String userInfoUrl) {
        String userInfoResponse = null;
        CustomParam customParam = null;
        if (extInfo.getCustomParams() != null && (customParam = extInfo.getCustomParams().get("USER_INFO")) != null) {
            userInfoResponse = this.customParamService.getUserInfo(nvwaCertify, customParam, accessToken, userInfoUrl);
        } else {
            String getUserInfoUrl = userInfoUrl + "?client_id=" + nvwaCertify.getClientid() + "&access_token=" + accessToken;
            logger.info("\u6253\u5370\u83b7\u53d6\u7528\u6237\u4fe1\u606f\u5730\u5740\uff1a{}", (Object)getUserInfoUrl);
            try {
                userInfoResponse = (String)this.restTemplate.getForObject(getUserInfoUrl, String.class, new HashMap(0));
            }
            catch (Exception e) {
                logger.warn("\u8bf7\u6c42\u7528\u6237\u4fe1\u606f\u63a5\u53e3\u62a5\u9519", e);
                throw new GcOAuth2Exception("\u8bf7\u6c42\u7528\u6237\u4fe1\u606f\u63a5\u53e3\u62a5\u9519", e);
            }
        }
        logger.info("\u6253\u5370\u83b7\u53d6\u7528\u6237\u4fe1\u606f\u54cd\u5e94\u4f53: {}", (Object)userInfoResponse);
        return userInfoResponse;
    }

    protected String getUsernameInsideUserInfo(GcCertifyExtInfo extInfo, String userInfo) {
        String username = null;
        try {
            if (ObjectUtils.isEmpty((Object)extInfo.getUsernameField()) || "spRoleList".equals(extInfo.getUsernameField())) {
                username = (String)JsonPath.read((String)userInfo, (String)"$.spRoleList[0]", (Predicate[])new Predicate[0]);
                logger.info("\u4ecespRoleList\u4e2d\u83b7\u53d6\u7528\u6237\u540d\uff1a{}", (Object)username);
            } else {
                username = (String)JsonPath.read((String)userInfo, (String)("$." + extInfo.getUsernameField()), (Predicate[])new Predicate[0]);
                logger.info("\u4ece\u5b57\u6bb5[{}]\u4e2d\u83b7\u53d6\u7528\u6237\u540d\uff1a{}", (Object)extInfo.getUsernameField(), (Object)username);
            }
        }
        catch (PathNotFoundException e) {
            logger.warn("\u8bfb\u53d6\u7528\u6237\u4fe1\u606fjson\u53d6\u7528\u6237\u540d\u51fa\u9519", e);
            throw new GcOAuth2Exception("\u8bfb\u53d6\u7528\u6237\u4fe1\u606fjson\u53d6\u7528\u6237\u540d\u51fa\u9519", e);
        }
        if (StringUtils.isNotBlank((CharSequence)extInfo.getReverseLookupUserAttrName())) {
            List nvwaUserIds = this.userClient.getUsersByAttrValue(extInfo.getReverseLookupUserAttrName(), username);
            if (!CollectionUtils.isEmpty((Collection)nvwaUserIds)) {
                String loginName = this.userClient.find((String)nvwaUserIds.get(0)).getName();
                logger.info("\u7528\u6237\u6269\u5c55\u5b57\u6bb5{}={}\u53cd\u67e5\u51fa\u7528\u6237\u767b\u5f55\u540d{}", extInfo.getReverseLookupUserAttrName(), username, loginName);
                username = loginName;
            } else {
                logger.warn("\u7528\u6237\u6269\u5c55\u5b57\u6bb5{}={}\u53cd\u67e5\u7528\u6237\u767b\u5f55\u540d\uff0c\u672a\u627e\u5230\u5bf9\u5e94\u7684\u7528\u6237\uff0c\u5c06\u4ee5\u53d6\u5230\u7684\u7528\u6237\u540d{}\u5c1d\u8bd5\u767b\u5f55!", extInfo.getReverseLookupUserAttrName(), username, username);
            }
        }
        logger.info("\u83b7\u53d6\u5230\u7528\u6237\u540d\uff1a{}", (Object)username);
        return username;
    }

    public static HttpServletRequest getHttpServletRequest() {
        return ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
    }

    protected String getRequestBasePath() {
        StringBuilder sb = new StringBuilder();
        HttpServletRequest request = GcOAuth2V2CertifyExtendService.getHttpServletRequest();
        String protocol = request.getHeader("X-Forwarded-Proto");
        if (StringUtils.isBlank((CharSequence)protocol)) {
            protocol = "http";
        }
        String host = request.getHeader("Host");
        Assert.hasText(host, "\u8bf7\u6c42\u5934\u4e2d\u4e0d\u5305\u542bHost\uff0c\u8bf7\u8bbe\u7f6e\u540e\u518d\u8bd5\u3002");
        sb.append(protocol).append("://").append(host);
        if (!"".equals(this.contextPath) && !"/".equals(this.contextPath)) {
            sb.append(this.contextPath.trim());
        }
        return sb.toString();
    }

    protected String getPrintMsgUrl(String msg, NvwaCertify nc) {
        GcCertifyExtInfo ext;
        if (msg == null) {
            msg = "null";
        }
        if (StringUtils.isNotBlank((CharSequence)(ext = NvwaCertifyUtil.getExtInfoFromJson(nc)).getUnionUserLoginFailTips())) {
            msg = ext.getUnionUserLoginFailTips();
        }
        msg = UrlEncodeUtil.encode(Base64Utils.base64Encode(msg));
        StringBuilder buf = new StringBuilder(this.getRequestBasePath()).append("/anon/gcoauth2tips?msg=").append(msg);
        return buf.toString();
    }

    private static String joinUrl(String baseUrl, String uri) {
        String joined = null;
        joined = uri != null && uri.startsWith("http") ? uri : baseUrl + uri;
        return joined;
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

